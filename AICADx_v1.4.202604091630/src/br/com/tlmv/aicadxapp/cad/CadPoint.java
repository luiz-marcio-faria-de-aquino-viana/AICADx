/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPoint.java
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
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadPointRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadPoint extends CadEntity
{
//Private
    GeomPoint3d pt;
	
//Public
    
    public CadPoint() {
		super(AppDefs.OBJTYPE_ARC, null, null, null, 0.0, false);		
    }

    public CadPoint(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_POINT, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d pt) {
		this.init(pt.getX(), pt.getY(), 0.0);
	}
	
	private void init(GeomPoint3d pt) {
		this.init(pt.getX(), pt.getY(), pt.getZ());
	}

	public void init(double x, double y, double z) {
    	this.pt = new GeomPoint3d(x, y, z);
		
		this.createAllDrawCache();
    }
	
	@Override
	public void init(ICadObject o) {
		CadPoint other = (CadPoint)o;
		this.init(other.getPt());
	}

	/* CREATE */
		
	public static CadPoint create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d pt) {
    	CadPoint o = new CadPoint(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pt);
    	return o;
    }
	
	public static CadPoint create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d pt) {
    	CadPoint o = new CadPoint(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pt);
    	return o;
    }

	public static CadPoint create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double x, double y, double z) {
    	CadPoint o = new CadPoint(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(x, y, z);
    	return o;
    }

	public static CadPoint create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double x, double y, double z, boolean bLocked) {
    	CadPoint o = new CadPoint(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(x, y, z);
    	return o;
    }
	
	public static CadPoint create(CadPoint other) {
		CadPoint o = new CadPoint(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadPoint create(CadBlockDef blkDef, CadPoint other) {
		CadPoint o = new CadPoint(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadPoint create(CadBlockDef oBlkDef, CadPointRecord oRec) {
		CadDocumentDef doc = oBlkDef.getDocument();

		String reference = oRec.getReference();
		String levelName = oRec.getLevelName();

		// LAYER_TABLE
		//
		LayerTable oLayTbl = doc.getLayerTable();
		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		// LEVEL_TABLE
		//
		LevelTable oLevelTbl = doc.getLevelTable();
		CadLevel oLevel = oLevelTbl.getLevel(levelName);

		CadPoint o = CadPoint.create(
			oBlkDef,
			oLayer, 
			oLevel,
			oRec.getPtX(), 
			oRec.getPtY(), 
			oRec.getPtZ() );
		o.setObjectId(oRec.getObjectId());
		
	    return o;
	}
	
	/* OPERATIONS */
	
	@Override
	public CadPoint duplicate()
	{
		CadPoint other = CadPoint.create(this);
		return other;
	}
	
	@Override
	public CadPoint duplicate(CadBlockDef blkDef)
	{
		CadPoint other = CadPoint.create(blkDef, this);
		return other;
	}

	@Override
	public CadPoint copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPoint other = CadPoint.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadPoint moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.pt = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	@Override
	public CadPoint scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.pt = new GeomPoint3d(o.getPtDest());
		return this;
	}
    
	@Override
	public CadPoint mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.pt = GeomUtil.mirror(this.pt, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadPoint offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPoint oPoint = copyTo(ptIMcs, ptFMcs);
		return oPoint;
	}
	
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.pt.toPropertyList("Pt.", true) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String strLayerName = this.getLayer().getName();
		
		String str = String.format(
			"ObjectId:%s;ObjType:%s;Layer:%s;[X:%s;Y:%s;Z:%s]; ", 
			this.getObjectId(),
			this.getObjType(),
			strLayerName,
			nf6.format(this.pt.getX()), 
			nf6.format(this.pt.getY()), 
			nf6.format(this.pt.getZ()) );
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
		DrawCache cache = new DrawCache();

		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);

		GeomVector3d axisX = GeomUtil.axisX3d();
		GeomVector3d axisY = GeomUtil.axisY3d();
		
		double pointSize = AppDefs.POINT_SIZE * AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		double hPointSize = (pointSize / 2.0);
		
		GeomPoint3d pt0_I = this.pt.otherMoveTo(axisY, hPointSize);
		GeomPoint3d pt0_F = this.pt.otherMoveTo(axisY, - hPointSize);

		GeomPoint3d pt1_I = this.pt.otherMoveTo(axisX, hPointSize);
		GeomPoint3d pt1_F = this.pt.otherMoveTo(axisX, - hPointSize);
		
		LineStringEntityDrawCache oLine1 = new LineStringEntityDrawCache(); 
		oLine1.addPoint3d(pt0_I);
		oLine1.addPoint3d(pt0_F);
		cache.addItem(oLine1);			
		
		LineStringEntityDrawCache oLine2 = new LineStringEntityDrawCache(); 
		oLine2.addPoint3d(pt1_I);
		oLine2.addPoint3d(pt1_F);
		cache.addItem(oLine2);			

		return cache;
	}

	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}
	
	@Override
	public DrawCache createOsnapCache()
	{
		DrawCache osnapCache = new DrawCache();

		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.pt) );

		return osnapCache;
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

        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.pt);
        
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
		        	CadPoint other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.pt);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPoint other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.pt);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadPoint other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.pt);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPoint other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.pt);
		        }
	        }        
        }
        
    	DrawUtil.drawPointMcs(v, ptDest2dMcs, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
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
		
        GeomPoint2d ptPoint2dMcs = new GeomPoint2d(this.pt);
        
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;
        
        double dist = ptPoint2dMcs.distTo(pt2dMcs); 
        if(dist <= distMax) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return this.isSelected();
	}
	
	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity)
	{
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
    	lsPtNodepoint.add(this.pt);
		
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
    	lsPtNodepoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.pt) );
    	return lsPtNodepoint;
	}
	
	/* CENTROID */
	
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.pt);
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
			new Double( this.pt.getX() ),
			new Double( this.pt.getY() ),
			new Double( this.pt.getZ() )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadPointRecord entRec = new CadPointRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );

		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomDimension3d oDim = new GeomDimension3d(this.pt, this.pt); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oDim = new GeomDimension2d(this.pt, this.pt); 
		return oDim;
	}

    public GeomPoint3d getPt() {
        return this.pt;
    }

}
