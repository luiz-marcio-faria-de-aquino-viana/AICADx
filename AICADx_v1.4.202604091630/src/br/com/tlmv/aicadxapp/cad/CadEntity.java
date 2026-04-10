/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public abstract class CadEntity extends CadObject implements ICadEntity
{
//Private
	private CadLayerDef layer = null;
	private CadLevel level = null;
	private double zLevel = 0.0;
	private boolean bSelected = false;
	private boolean bLocked = false;

    //DRAW_CACHE
	private DrawCache drawCache2d = null; 
	private DrawCache osnapCache3d = null; 
	
//Public
	
    public CadEntity() {
		super(AppDefs.OBJTYPE_NONE, null, null);		

		this.initEntity(
    		null, 
    		null, 
    		0.0, 
    		false);
    }

	public CadEntity(
		int objType, 
		CadBlockDef blkDef, 
		CadLayerDef layer,
		CadLevel level, 
		double zLevel,
		boolean bLocked) 
	{
		super(objType, ( (blkDef != null) ? blkDef.getDocument() : null ), blkDef);
		this.initEntity(layer, level, zLevel, bLocked);
	}

	/* Methodes */

	public void initEntity(
		int objType, 
		CadBlockDef blkDef, 
		CadLayerDef layer,
		CadLevel level, 
		double zLevel,
		boolean bLocked) 
	{
		super.initObj(objType, ( (blkDef != null) ? blkDef.getDocument() : null ), blkDef);
		this.initEntity(layer, level, zLevel, bLocked);
	}
	
	@Override
	public void initEntity(
		CadLayerDef layer,
		CadLevel level,
		double zLevel,
		boolean bLocked ) 
	{
		this.layer = layer;
		this.level = level;
		this.zLevel = zLevel;
		this.bSelected = false;
		this.bLocked = bLocked;
	}

	@Override
	public void initEntity(BaseEntityRecord oRec, CadDocumentDef doc, CadBlockDef oBlkDef) {
		super.initObj(oRec.getObjType(), doc, oBlkDef);

		//CadLayerDef
		//
		LayerTable layTbl = doc.getLayerTable();

    	CadLayerDef oLayer = layTbl.getLayerDefByRef( oRec.getReference() );
    	if(oLayer == null) {
    		oLayer = doc.getDefaultLayerDef();
    	}

    	//CadLevel
		//
		LevelTable levelTbl = doc.getLevelTable();

		CadLevel oLevel = levelTbl.getLevel( oRec.getLevelName() );    	
    	if(oLevel == null) {
    		oLevel = doc.getDefaultLevel();
		}

    	//Z-Level
    	//
    	double zLevel = oRec.getZLevel();
    	
    	//LOCKED
    	//
		boolean bLocked = AppDefs.DEF_VALUES_SIM.equals( oRec.getIsLocked() );

		this.initEntity(
			oLayer,
			oLevel,
			zLevel,
			bLocked ); 
	}
	
	@Override
	public void initEntity(ICadEntity other) {
		CadEntity oEnt = (CadPoint)other;
		
		this.initEntity(
			oEnt.getLayer(), 
			oEnt.getLevel(), 
			oEnt.getZLevel(),
			oEnt.isLocked() );
	}
	
	@Override
	public abstract void init(ICadObject other);
	
	@Override
	public void reset()
	{
		this.bSelected = false;		
		this.bLocked = false;
	}
	
	@Override
	public String toString() {
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(3);
		
		String strIsLocked = ( bLocked ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO ); 
		
		String str = String.format(
			"Codigo:%s;" + 
			"Tipo:%s;" + 
			"Camada:%s;" + 
			"Level:%s;" + 
			"Z-Level:%s;" + 
			"Locked:%s", 
			Integer.toString( this.getObjectId() ),
			this.getObjTypeStr(),
			this.getLayer().getName(),
			this.getLevel().getLevelLocalName(),
			nf6.format( this.getZLevel() ),
			strIsLocked);
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
		else if( bHover ){
	    	c = AppDefs.HOVEROBJECTCOLOR_SELECTMODE;
		}
//		else if( bSelEnt ) {
//			if( bSelected ) {
//		    	c = AppDefs.SELECTOBJECTCOLOR_SELECTMODE;
//			}
//			else {
//				c = AppDefs.CURRENTSELECTENTITYCOLOR;
//			}
//		}
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
		else if( bHover ){
			oLtype = AppDefs.HOVEROBJECTLTYPE_SELECTMODE;
		}
//		else if( bSelEnt ){
//			if( bSelected ) {
//				oLtype = AppDefs.SELECTOBJECTLTYPE_SELECTMODE;
//			}
//			else {
//				oLtype = AppDefs.CURRENTSELECTENTITYLTYPE;
//			}
//		}

		Stroke b = oLtype.getLtype();		
		return b;
	}
	
	/* OPERATIONS */

	@Override
	public boolean isLocked() {
		return this.bLocked;
	}
	
	@Override
	public void setLocked(boolean bLocked) {
		this.bLocked = bLocked;
	}
	
	@Override
	public boolean lock() {
		this.bSelected = false;

		this.bLocked = true;
		return this.bLocked;
	}

	@Override
	public boolean unlock() {
		this.bSelected = false;

		this.bLocked = false;
		return this.bLocked;
	}

	@Override
	public abstract ICadEntity duplicate();
	
	@Override
	public abstract ICadEntity duplicate(CadBlockDef blkDef);

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
		if( !this.isVisible() ) return;    
	
	    MainPanel panel = MainPanel.getMainPanel();
	    String action = panel.getCurrAction();
		
	    boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(view2d, pt2dMcs, sclFact, false);
	
		Stroke b = this.selectLtype(bDragMode, bSelected, bHover, bSelEnt);
		
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
		if( this.isLocked() ) return false;

    	if( !this.isVisible() ) return false;    	

		if(pt2dMcs == null) return false;
		
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
		if( this.isLocked() ) return false;
		
		if( !this.isVisible() ) return false;    

		if(pt2dMcs == null) return false;		

		boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected ) {
			bHover = this.select2d(view2d, pt2dMcs, sclFact, false);
			if( bHover ) {
				this.redraw2d(view2d, 0.0, pt2dMcs, pt2dMcs, sclFact, false, true, g);
				
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
		if( this.isLocked() ) return null;
		
		if( !this.isVisible() ) return null;

    	if( this.osnapCache3d == null ) return null;
    	
    	GeomPoint3d ptResult = null;
    	
		ptResult = this.osnapCache3d.osnap3d(view2d, osnapMode, pt2dMcs, g);
    	return ptResult;
	}
	
	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapMode, GeomPoint2d pt2dMcs)
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;
    	
    	if(this.osnapCache3d == null) return null;

    	ArrayList<GeomPoint3d> lsResult = null;
    	
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
    
		boolean bVisible = false;
		
    	CadDocumentDef doc = this.getDocument();

    	LayerTable layTbl = doc.getLayerTable();
    	
    	CadLayerDef oLayer = this.layer;
    	if(oLayer != null) {
    		String reference = oLayer.getReference();
    		
    		CadLayerDef oCurrLayer = layTbl.getLayerDefByRef(reference);
    		if(oCurrLayer != null) {
    			bVisible = oCurrLayer.isLayerOn();
    		}
    	}
    	return bVisible;
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
		//if( !bInside )
		//	bInside = GeomUtil.checkIfRectAInsideRectB(ptMin2d, ptMax2d, ptEnvelopMin2d, ptEnvelopMax2d);
		return bInside;
	}

	@Override
	public boolean isCrossingLine(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs)
	{
		GeomDimension2d oEnvelop = this.getEnvelop2d();
		GeomPoint2d ptEnvelopMin2d = oEnvelop.getPtMin();
		GeomPoint2d ptEnvelopMax2d = oEnvelop.getPtMax();
		
		boolean bCrossingLine = GeomUtil.checkIfRectACrossingLine(ptEnvelopMin2d, ptEnvelopMax2d, ptIMcs, ptFMcs);
		return bCrossingLine;
	}

	@Override
	public boolean isCrossing(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs)
	{
		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptIMcs, ptFMcs);
		GeomPoint2d ptMin2d = arr[0];
		GeomPoint2d ptMax2d = arr[1];
		
		double xPtMin = ptMin2d.getX();
		double yPtMin = ptMin2d.getY();
		
		double xPtMax = ptMax2d.getX();
		double yPtMax = ptMax2d.getY();

		GeomPoint2d pt0 = new GeomPoint2d(xPtMin, yPtMin);
		GeomPoint2d pt1 = new GeomPoint2d(xPtMax, yPtMin);
		GeomPoint2d pt2 = new GeomPoint2d(xPtMax, yPtMax);
		GeomPoint2d pt3 = new GeomPoint2d(xPtMin, yPtMax);

		boolean bResult = this.isInside(ptMin2d, ptMax2d);
		if( bResult ) return true;
		
		bResult = this.isCrossingLine(pt0, pt1);
		if( bResult ) return true;

		bResult = this.isCrossingLine(pt1, pt2);
		if( bResult ) return true;

		bResult = this.isCrossingLine(pt2, pt3);
		if( bResult ) return true;

		bResult = this.isCrossingLine(pt3, pt0);
		if( bResult ) return true;
	
		return false;
	}

	@Override
	public boolean isObjtypeOf(int[] arrSelectObjType)
	{    	
		int selectObjType = this.getObjType();
		
		int sz = arrSelectObjType.length;
		for(int i = 0; i < sz; i++) {
			int objtype = arrSelectObjType[i];
			if( (objtype == AppDefs.OBJTYPE_ANY) || (objtype == selectObjType) )
				return true;
		}
		return false;
	}	

	@Override
	public boolean search(int objType, String searchBy)
	{
		int[] arrObjType = { objType };
		
		boolean bResult = search(arrObjType, searchBy);
		return bResult;
	}
	
	@Override
	public boolean search(int[] arrObjType, String searchBy)
	{
		if( this.isLocked() ) return false;		
		
		if( this.isDeleted() ) return false;
		
		int objType = this.getObjType();
		if( (objType == AppDefs.OBJTYPE_NONE) || 
			(objType == AppDefs.OBJTYPE_ALL) || 
			(objType == AppDefs.OBJTYPE_ANY) ) {
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
			if( isObjtypeOf(arrObjType) ) {
				if( (searchBy == null) || 
					( "".equals(searchBy) ) ) {
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
	
	@Override
	public GeomPoint3d nearestPoint(GeomPoint3d ptRef) {
		return null;
	}
	
	@Override
	public GeomPoint3d nearestConexao(GeomPoint3d ptRef) {
		return null;
	}
	
	@Override
	public GeomPoint3d nearestConexaoEntrada(GeomPoint3d ptRef){
		return null;
	}

	@Override
	public GeomPoint3d nearestConexaoSaida(GeomPoint3d ptRef){
		return null;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public abstract boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc);

	@Override
	public abstract boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc);
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		if(lsCadEntity2d != null)
			lsDxfCadEntity.addAll( lsCadEntity2d );

		return lsDxfCadEntity;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>(); 
		
    	if( this.drawCache2d == null ) return null;
    	
		if( !this.isVisible() ) return null;    
		
	    MainPanel panel = MainPanel.getMainPanel();
	    String action = panel.getCurrAction();
		
	    lsCadEntity2d.addAll( this.drawCache2d.toDxfR12_view2d(layer) );
	    return lsCadEntity2d;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}

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
		String strLevelName = "?"; 
		if(this.level != null)
			strLevelName = this.level.getLevelLocalName();
		
		String searchString = "^" + 
			"CODIGO=" + Integer.toString( this.getObjectId() ) + "^" + 
			"CAMADA=" + this.layer.getName() + "^" + 
			"REFERENCIA=" + this.layer.getReference() + "^" +
			"LEVELNAME=" + strLevelName;
		return searchString;
	}

	public double getZLevelElevation() {
		double zLevelElevation = 0.0;
		
		if(this.level != null) {
			zLevelElevation = this.level.getZLevel();
		}
		return zLevelElevation;
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

	public CadLevel getLevel() {
		if(this.level == null) {
			CadDocumentDef doc = this.getDocument();

			CadLevel oLevel = doc.getCurrLevel();
			return oLevel;
		}
		return this.level;
	}

	public void setLevel(CadLevel level) {
		this.level = level;
	}

	public double getZLevel() {
		return zLevel;
	}

	public void setZLevel(double zLevel) {
		this.zLevel = zLevel;
	}
	
	public String getSign()
	{
		String strSign = (this.getZLevel() < 0) ? "-" : "+";
		return strSign;
	}		

}
