/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadEntity.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public abstract class CadEntity extends CadObject implements ICadEntity
{
//Private
	private CadLayerDef layer = null;
	private boolean bSelected = false;

    //DRAW_CACHE
	private DrawCache drawCache2d = null; 
	private DrawCache osnapCache3d = null; 
	
//Public

	public CadEntity(int objType, CadBlockDef blkDef, CadLayerDef layer) {
		super(objType, blkDef);
		this.init(layer);
	}

	/* Methodes */
	
	@Override
	public void init(CadLayerDef layer) 
	{
		this.layer = layer;
		this.bSelected = false;
	}
	
	@Override
	public void init(ICadEntity other) {
		CadEntity oEnt = (CadPoint)other;
		
		this.init(oEnt.layer);
	}
	
	@Override
	public void reset()
	{
		this.bSelected = false;		
	}
	
	@Override
	public String toString() {
		String str = String.format("Codigo:%s;Tipo:%s;Camada:%s", 
			Integer.toString( this.getObjectId() ),
			this.getObjTypeStr(),
			this.getLayer().getName());
		return str;
	}
	
	/* SELECTxxx - Color, Line Style, Hatch Pattern, Font Style, Dimension Style */
	
	@Override
	public Color selectColor(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt)
	{
		CadLayerDef oLayer = this.layer;
		
		ColorVO oColor = oLayer.getColor();
		
		Color c = oColor.getColor();
		
		if( bDragMode ) {
			c = AppDefs.DRAGOBJECTCOLOR_SELECTMODE;
		}
		else if( bSelected ){
	    	c = AppDefs.SELECTOBJECTCOLOR_SELECTMODE;
		}
		else if( bHover ){
	    	c = AppDefs.HOVEROBJECTCOLOR_SELECTMODE;
		}
		else if( bSelEnt ){
	    	c = AppDefs.CURRENTSELECTENTITYCOLOR;
		}

		return c;
	}
	
	@Override
	public Stroke selectLtype(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt)
	{
		CadLayerDef oLayer = this.layer;
		
		BorderStrokeVO oLtype = oLayer.getLtype();
		
		if( bDragMode ) {
			oLtype = AppDefs.DRAGOBJECTLTYPE_SELECTMODE;
		}
		else if( bSelected ){
			oLtype = AppDefs.SELECTOBJECTLTYPE_SELECTMODE;
		}
		else if( bHover ){
			oLtype = AppDefs.HOVEROBJECTLTYPE_SELECTMODE;
		}
		else if( bSelEnt ){
			oLtype = AppDefs.CURRENTSELECTENTITYLTYPE;
		}

		Stroke b = oLtype.getLtype();		
		return b;
	}
	
	/* OPERATIONS */
	
	@Override
	public abstract ICadEntity duplicate();

	@Override
	public abstract ICadEntity copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);

	@Override
	public abstract ICadEntity moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
	
	@Override
	public abstract ICadEntity scaleTo(double refDistMcs, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	@Override
	public abstract ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	@Override
	public abstract ICadEntity offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist);

	/* DEBUG */

	@Override
	public abstract ArrayList<ItemDataVO> toPropertyList();
	
	@Override
	public abstract String toStr();
	
	@Override
	public abstract void debug(int debugLevel);
	
    /* DRAWCACHE */	

	@Override
	public void createAllDrawCache() {
    	this.drawCache2d = this.createDrawCache2d();
    	this.osnapCache3d = this.createOsnapCache();		
	}

	@Override
	public abstract DrawCache createDrawCache2d();

	@Override
	public abstract DrawCache createDrawCache3d();

	@Override
	public abstract DrawCache createOsnapCache();
	
	/* REDRAW */
	
	@Override
	public void redraw2d(ICadViewBase view2d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(this.isDeleted()) return;
	
	    MainPanel panel = MainPanel.getMainPanel();
	    String action = panel.getCurrAction();
		
	    boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(view2d, pt2dMcs, sclFact, false);
	
		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);
		
		Color c = this.selectColor(bDragMode, bSelected, bHover, bSelEnt);
		
    	if(this.drawCache2d == null) return;
    	
		this.drawCache2d.redraw2d(view2d, c, b, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, action, g);
    }	
	
	@Override
	public abstract void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep);
	
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity)
	{
		if(pt2dMcs == null) return false;
		
    	if( !this.isVisible() ) return false;    	
    	if( this.isSelected() ) return true;

    	if(this.drawCache2d == null) return false;
    	
    	boolean bResult = this.drawCache2d.select2d(view2d, pt2dMcs, sclFact, bSelectEntity);
    	return bResult;
	}

	@Override
	public abstract boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	/* TOOLTIP */
	
	public boolean showTooltip2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, Graphics g)
	{
		if(pt2dMcs == null) return false;		

		if( !this.isVisible() ) return false;    

		boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected ) {
			bHover = this.select2d(view2d, pt2dMcs, sclFact, false);
			if( bHover ) {
		    	GeomVector2d axisY = GeomUtil.axisY2d();
		    	
		    	double fontSz = AppDefs.TOOLTIP_FONTHEIGHT;
		    	
		    	double lineHeight = 1.5 * fontSz;
		    	
		    	int maxCharsPerRow = AppDefs.TOOLTIP_MAXCHARPERROW; 
		
		    	int maxNumRows = AppDefs.TOOLTIP_MAXNUMROW;
		    	
		    	double topMargin = lineHeight;
		    	double bottomMargin = lineHeight;
		
		    	double leftMargin = 1.5 * fontSz;
		    	double rightMargin = 1.5 * fontSz;
		
		    	double vertGap = 2.0 * fontSz;
		    	double horizGap = 2.0 * fontSz;
		    	
		       	String str = this.toStr();
		
		       	String[] arrStr = StringUtil.split(str, ';');
		       	int szArrStr = arrStr.length;

		       	int nRows = Math.min(maxNumRows, szArrStr);
		       			       	
		       	ArrayList<String> lsStr = new ArrayList<String>();
		       	int maxChars = StringUtil.getTooltipStringList(arrStr, nRows, maxCharsPerRow, lsStr); 
		       	int szLsStr = lsStr.size();
		       	
		       	GeomPoint2d pt2dScr = view2d.fromMcsToScr(pt2dMcs);       	
		       	double xScr = pt2dScr.getX();
		       	double yScr = pt2dScr.getY();
		       	
		       	double w = leftMargin + (maxChars * fontSz) + rightMargin;
		       	double h = topMargin + (szLsStr * lineHeight) + bottomMargin;
		       	
		       	double xMinScr = xScr + horizGap;
		       	double yMinScr = yScr + vertGap;
		       	
		       	double xMaxScr = xMinScr + (AppDefs.TOOLTIP_SIZEADJUSTMENT * w);
		       	double yMaxScr = yMinScr + (AppDefs.TOOLTIP_SIZEADJUSTMENT * h);
		
		       	GeomPoint2d ptMin2dScr = new GeomPoint2d(xMinScr, yMinScr);
		       	GeomPoint2d ptMax2dScr = new GeomPoint2d(xMaxScr, yMaxScr);
		       	
		       	Color oldcol = GeomUtil.setColor(g, AppDefs.TOOLTIP_BGCOLOR);

		       	//FILL_TOOLTIP_AREA
		       	DrawUtil.fillRectangleScr(view2d, ptMin2dScr, ptMax2dScr, g);
		       	
		       	GeomUtil.setColor(g, AppDefs.TOOLTIP_COLOR);
				
		       	//DRAW_TOOLTIP_BORDER
		       	DrawUtil.drawRectangleScr(view2d, ptMin2dScr, ptMax2dScr, g);

		       	//DRAW_TOOLTIP_TEXT
		       	double xInsScr = xMinScr + leftMargin;
		       	double yInsScr = yMaxScr - topMargin;
		       	
		       	GeomPoint2d ptIns2dScr = new GeomPoint2d(xInsScr, yInsScr);
		       	for(String outStr : lsStr) {
		       		DrawUtil.drawTextScr(view2d, outStr, ptIns2dScr, fontSz, g);
		       		ptIns2dScr = ptIns2dScr.otherMoveTo(axisY, - lineHeight);
		       	}
		   		
		       	GeomUtil.setColor(g, oldcol);
		
		       	return true;
			}
		}
		return false;
	}

	public boolean showTooltip3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, Graphics g)
	{
		return false;
	}
	
	/* TO_SHAPE */

	@Override
	public abstract ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	@Override
	public abstract ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	@Override
	public abstract ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	@Override
	public abstract ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs);
	
	/* OSNAP */
	
	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapMode, GeomPoint2d pt2dMcs, Graphics g)
	{
    	if( !this.isVisible() ) return null;

    	GeomPoint3d ptResult = null;
    	
    	if(this.osnapCache3d == null) return null;
    	
		ptResult = this.osnapCache3d.osnap3d(view2d, osnapMode, pt2dMcs, g);
    	return ptResult;
	}
	
	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapMode, GeomPoint2d pt2dMcs)
	{
    	if( !this.isVisible() ) return null;

    	ArrayList<GeomPoint3d> lsResult = null;
    	
    	if(this.osnapCache3d == null) return null;
    	
		lsResult = this.osnapCache3d.osnap3d(view2d, osnapMode, pt2dMcs);
    	return lsResult;
	}

	/* CENTROID */
	
	@Override
	public abstract GeomPoint3d centroid();

	/* VALID */

	@Override
	public boolean isValid()
	{
		return true;
	}
	
	/* UTILITIES */

	@Override
	public boolean isVisible()
	{
    	if(this.isDeleted()) return false;
    	
    	boolean layerOn = this.layer.isLayerOn();
    	if( !layerOn ) return false;
    	
    	return true;
	}

	@Override
	public boolean isInside(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs)
	{
		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptIMcs, ptFMcs);
		GeomPoint2d ptMin2d = arr[0];
		GeomPoint2d ptMax2d = arr[1];
		
		GeomDimension2d oEnvelop = this.getEnvelop2d();
		GeomPoint2d ptEnvelopMin2d = oEnvelop.getPtMin();
		GeomPoint2d ptEnvelopMax2d = oEnvelop.getPtMax();
		
		boolean bInside = GeomUtil.checkIfRectAInsideRectB(ptEnvelopMin2d, ptEnvelopMax2d, ptMin2d, ptMax2d);
		if( !bInside )
			bInside = GeomUtil.checkIfRectAInsideRectB(ptMin2d, ptMax2d, ptEnvelopMin2d, ptEnvelopMax2d);
		return bInside;
	}
	
	@Override
	public boolean search(int objType, String searchBy)
	{
		if( this.isDeleted() ) return false;
		
		if( (objType == AppDefs.OBJTYPE_NONE) || (objType == AppDefs.OBJTYPE_ALL) || (objType == AppDefs.OBJTYPE_ANY) ) {
			if( (searchBy == null) || ( "".equals(searchBy) ) ) {
				return true;
			}
			else {
				String strSearchString = this.getSearchString().toUpperCase();
				String strSearchBy = searchBy.toUpperCase();
				
				if( strSearchString.contains( strSearchBy ) ) {
					return true;
				}
			}
		}
		else {
			if(objType == this.getObjType()) {
				if( (searchBy == null) || ( "".equals(searchBy) ) ) {
					return true;
				}
				else {
					String strSearchString = this.getSearchString().toUpperCase();
					String strSearchBy = searchBy.toUpperCase();
					
					if( strSearchString.contains( strSearchBy ) ) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public abstract void load(AppDatabase db, String schemaName, CadDocumentDef doc);

	@Override
	public abstract void save(AppDatabase db, String schemaName, CadDocumentDef doc);

	/* Getters/Setters */

	@Override
	public abstract GeomDimension3d getEnvelop3d();

	@Override
	public abstract GeomDimension2d getEnvelop2d();
	
	@Override	
	public GeomPoint3d getExternalPoint3d(GeomPoint3d ptRef3d) {
		return null;
	}

	@Override	
	public GeomPoint2d getExternalPoint2d(GeomPoint2d ptRef2d){
		return null;
	}

	@Override
	public String getSearchString() {
		String searchString = "^" + 
			"CODIGO=" + Integer.toString( this.getObjectId() ) + "^" + 
			"CAMADA=" + this.layer.getName() + "^" + 
			"REFERENCIA=" + this.layer.getReference();
		return searchString;
	}

	public DrawCache getDrawCache2d() {
		return this.drawCache2d;
	}

	public DrawCache getOsnapCache3d() {
		return osnapCache3d;
	}

	public CadLayerDef getLayer() {
		return this.layer;
	}

	public void setLayer(CadLayerDef layer) {
		this.layer = layer;
	}

	public boolean isSelected() {
		return bSelected;
	}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

}
