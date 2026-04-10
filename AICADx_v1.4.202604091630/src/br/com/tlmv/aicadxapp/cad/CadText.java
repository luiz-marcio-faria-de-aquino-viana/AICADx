/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadText.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/01/2025
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
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadTextRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadText extends CadEntity 
{
//Private
	String text;
    GeomPoint3d ptIns;
    double height;
    
//Public

    public CadText() {
		super(AppDefs.OBJTYPE_TEXT, null, null, null, 0.0, false);		
    }

    public CadText(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_TEXT, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
    public void init(String text, GeomPoint2d pt, double height) {
		this.init(text, pt.getX(), pt.getY(), 0.0, height);
	}
	
	public void init(String text, GeomPoint3d pt, double height) {
		this.init(text, pt.getX(), pt.getY(), pt.getZ(), height);
	}

	public void init(String text, double x, double y, double z, double height) {
		this.text = text;
    	this.ptIns = new GeomPoint3d(x, y, z);
    	this.height = height;
    }
	
	@Override
	public void init(ICadObject o) {
		CadText other = (CadText)o;

		String tmpText = other.text;
		GeomPoint3d ptTmpPt = other.ptIns;
		double tmpHeight = other.height;
		
		this.init(tmpText, ptTmpPt.getX(), ptTmpPt.getY(), ptTmpPt.getZ(), tmpHeight);
	}

	/* CREATE */
		
	public static CadText create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String text, GeomPoint2d pt, double height) {
    	CadText o = new CadText(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(text, pt, height);
    	return o;
    }
	
	public static CadText create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String text, GeomPoint3d pt, double height) {
    	CadText o = new CadText(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(text, pt, height);
    	return o;
    }

	public static CadText create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String text, double x, double y, double z, double height) {
    	CadText o = new CadText(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(text, x, y, z, height);
    	return o;
    }

	public static CadText create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String text, double x, double y, double z, double height, boolean bLocked) {
    	CadText o = new CadText(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(text, x, y, z, height);
    	return o;
    }
	
	public static CadText create(CadText other) {
		CadText o = new CadText(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadText create(CadBlockDef blkDef, CadText other) {
		CadText o = new CadText(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadText duplicate()
	{
		CadText other = CadText.create(this);
		return other;
	}
	
	@Override
	public CadText duplicate(CadBlockDef blkDef)
	{
		CadText other = CadText.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadText copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadText otherText = CadText.create(this);
		otherText.moveTo(ptIMcs, ptFMcs);
		return otherText;
	}

	@Override
	public CadText moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}

	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadText scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		this.height = this.height * o.getScale();
		return this;
	}
	
	@Override
	public CadText offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadText oText = copyTo(ptIMcs, ptFMcs);
		return oText;
	}
    
	/* DEBUG */
    
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.", true) );
		//
		lsProperty.add( new ItemDataVO("Text", this.text, true) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(Text: %s; X: %s; Y: %s; Z: %s; Height: %s); ",
			this.text,
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()),
			nf6.format(this.height) );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWCACHE */
	
	@Override
	public DrawCache createDrawCache2d() {
		return null;
	}
	
	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		return null;
	}

    /* DRAWING */
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;

		boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();
        
        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.ptIns);
        double textHeightMcs = this.height * sclFact;
        
        if( bDragMode ) 
        {        
	        if(ptBase2dMcs != null) 
	        {        
	            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
	            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

	            GeomVector3d vDir3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs);

		        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
		        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
		        {
		        	CadText other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadText other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadText other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadText other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }
        
    	DrawUtil.drawTextMcs(v, this.text, ptDest2dMcs, textHeightMcs, g);
        
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptDest2dMcs, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
        }
        
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}

	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;

    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        int textLen = this.text.length();
        
        double textHeightMcs = this.height;        
        double textWidthMcs = 0.75 * textHeightMcs;
        
        double h = textHeightMcs;
        double w = (textLen * textWidthMcs);
        
        double xPtMcs = this.ptIns.getX();
        double yPtMcs = this.ptIns.getY();

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;
        
        double xMin = xPtMcs - distMax;
        double yMin = yPtMcs - distMax;
        
        double xMax = xPtMcs + w + distMax;
        double yMax = yPtMcs + h + distMax;
        
        double xPt2dMcs = pt2dMcs.getX();
        double yPt2dMcs = pt2dMcs.getY();
        
        if( ( (xPt2dMcs >= xMin) && (xPt2dMcs <= xMax) ) &&
    		( (yPt2dMcs >= yMin) && (yPt2dMcs <= yMax) ) )
        {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return false;
	}

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
	{
		return null;
	}
    
	/* OSNAP */

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add( new GeomPoint3d(this.ptIns) );
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.ptIns) );
    	return lsPtNodepoint;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptIns);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}
	
	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new String( text ),
			//
			new Double( ptIns.getX() ),
			new Double( ptIns.getY() ),
			new Double( ptIns.getZ() ),
			//
			new Double( height )
				
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadTextRecord entRec = new CadTextRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );
		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
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
		lsDxfCadEntity.addAll( lsCadEntity2d );
		
		return lsDxfCadEntity;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();

		CadDocumentDef doc = this.getDocument();
		
		CadLayerDef oLayer = doc.getCurrLayerDef();

		double scl = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
		String strText = this.getText();
		double textHeight = this.getHeight() * scl;
		double textRotation = 0.0;
		
		ArrayList<DxfCadEntity> oDxfCadEntity = DxfUtil.toDxfText(
			oLayer, 
			strText, 
			this.ptIns, 
			textHeight, 
			textRotation, 
			AppDefs.DXF_HORIZALIGN_LEFT, 
			AppDefs.DXF_VERTALIGN_BOTTOM);
		lsCadEntity2d.addAll( oDxfCadEntity );
		
		return lsCadEntity2d;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}
		
    /* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomDimension3d oDim = new GeomDimension3d(this.ptIns, this.ptIns); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oDim = new GeomDimension2d(this.ptIns, this.ptIns); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"TEXTO=" + this.text +
			"ALTURA=" + Double.toString( this.height );
		return searchString;
	}

    public String getText() {
    	return this.text;
    }
    
    public GeomPoint3d getPt() {
        return this.ptIns;
    }

    public double getHeight() {
    	return this.height;
    }

}
