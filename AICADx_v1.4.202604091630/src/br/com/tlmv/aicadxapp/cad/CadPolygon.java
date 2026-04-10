/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPolygon.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/09/2025
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
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolygonPointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolygonRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;

public class CadPolygon extends CadEntity 
{
//Private
    private ArrayList<GeomPoint3d> lsPts;
    private GeomPoint3d ptCentroid;
    
//Public

    public CadPolygon(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_POLYGON, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(double altura, ArrayList<GeomPoint2d> lsPts2d) {
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.from2dTo3d(lsPts2d, altura);
		this.init(lsPts3d);
	}

	public void init(ArrayList<GeomPoint3d> lsPts3d) {
		this.lsPts = new ArrayList<GeomPoint3d>(lsPts3d);
		this.ptCentroid = GeomUtil.centroidOfPolygon3d(lsPts3d);
		
		this.createAllDrawCache();
    }
	
	@Override
	public void init(ICadObject o) {
		CadPolygon other = (CadPolygon)o;
	    this.init(other.lsPts);
	}
	
	/* CREATE */
		
	public static CadPolygon create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel) {
		CadPolygon o = new CadPolygon(oBlkDef, oLayer, oLevel, 0.0, false);
    	return o;
    }
	
	public static CadPolygon create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double altura, ArrayList<GeomPoint2d> lsPts2d) {
    	CadPolygon o = new CadPolygon(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(altura, lsPts2d);
    	return o;
    }
	
	public static CadPolygon create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, ArrayList<GeomPoint3d> lsPts3d) {
    	CadPolygon o = new CadPolygon(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(lsPts3d);
    	return o;
    }
	
	public static CadPolygon create(CadPolygon other) {
    	CadPolygon o = new CadPolygon(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadPolygon create(CadBlockDef blkDef, CadPolygon other) {
    	CadPolygon o = new CadPolygon(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPolygon duplicate()
	{
		CadPolygon other = CadPolygon.create(this);
		return other;
	}
	
	@Override
	public CadPolygon duplicate(CadBlockDef blkDef)
	{
		CadPolygon other = CadPolygon.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadPolygon copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPolygon other = CadPolygon.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadPolygon moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		this.lsPts = newLsPts;
		
		this.createAllDrawCache();
		return this;
	}

	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);

		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	GeomPoint3d oPtDest3d = GeomUtil.mirror(oPt3d, ptI2dMcs, ptF2dMcs);;
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		this.lsPts = newLsPts;

		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public CadPolygon scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	ScaleData3dVO o = GeomUtil.scaleToPt3dByRefDist(refDist, ptIMcs, ptFMcs, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		this.lsPts = newLsPts;
		
		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public CadPolygon offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPolygon oPolygon = copyTo(ptIMcs, ptFMcs);
		return oPolygon;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);
		String strCoords = 	ListUtil.toStrArray(this.lsPts);

		lsProperty.addAll( this.ptCentroid.toPropertyList("Pt.Centroid", false) );
		lsProperty.add( new ItemDataVO("Pontos", strCoords, false) );		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		String str = String.format(
			"Centroid:%s;", 
		    ptCentroid.toStr());
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
	public DrawCache createDrawCache2d() 
	{
		DrawCache cache = new DrawCache();

		LineStringEntityDrawCache oLine = new LineStringEntityDrawCache(); 
		int sz = this.lsPts.size();
		if(sz > 0) {
			for(GeomPoint3d oPtAtual : this.lsPts) {
				oLine.addPoint3d(oPtAtual);			
			}
			GeomPoint3d oClosePt  = new GeomPoint3d( this.lsPts.get(0) );
			oLine.addPoint3d(oClosePt);
			cache.addItem(oLine);
		}
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

		LineStringEntityDrawCache oLine = new LineStringEntityDrawCache(); 
		GeomPoint3d oPtAnterior = null;
		for(GeomPoint3d oPtAtual : this.lsPts) {
			//ENDPOINT
	    	//
			osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, oPtAtual) );
			
	    	//MIDDLE
	    	//
			if(oPtAnterior != null) {
		    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, oPtAnterior, oPtAtual);
				osnapCache.addOsnapItem( pt3dMid );
			}
		}
		
    	//CENTER
    	//
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.centroid()) );

		return osnapCache;
	}

    /* DRAWING */
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;
    	
        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		super.redraw2d(v, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, g) ;
        
        if(bSelected || bHover) {
    		Stroke oldltype = GeomUtil.setLtype(g, b);
    		
    		Color oldcol = GeomUtil.setColor(g, c);		

    		GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);
        	DrawUtil.drawPointMcs(v, ptCentroid2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);

            GeomUtil.setColor(g, oldcol);

            GeomUtil.setLtype(g, oldltype);
        }
    }
	
	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */

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

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCentroid);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	public boolean loadAllPts(ArrayList<BasePointRecord> lsPts)
	{
		boolean bResult = false;
		
		try {
			this.lsPts = new ArrayList<GeomPoint3d>();
			for(BasePointRecord oPtRec : lsPts) {
				GeomPoint3d oPt3d = oPtRec.toGeomPoint3d();
				this.lsPts.add(oPt3d);
			}
			this.ptCentroid = GeomUtil.centroidOf3d(this.lsPts);		
	
			this.createAllDrawCache();
			
			bResult = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}
		
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lspts(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BasePointDao ptDao = dao.createPtDao(AppDefs.OBJTYPE_POLYGON_GEOMPOINT); 

		String cadRefEntityId = Integer.toString( this.getObjectId() );
		
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			BasePointRecord ptRec = new CadPolygonPointRecord(cadRefEntityId, objVer, this.lsPts.get(i));
			
			int rscode = ptDao.insertOrUpdate(objVer, schemaName, CadPolygonPointRecord.sqlTableName, ptRec);  
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new Double( ptCentroid.getX() ),
			new Double( ptCentroid.getY() ),
			new Double( ptCentroid.getZ() )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadPolygonRecord entRec = new CadPolygonRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );
		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = this.save_entity(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_lspts(objVer, dao, schemaName, doc);
		if( !bResult ) return false;

		return bResult;
	}
	
    /* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomPoint3d[] arr = GeomUtil.maxMinPointOfArray3d(this.lsPts);		
		
		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint3d[] arr = GeomUtil.maxMinPointOfArray3d(this.lsPts);		
		
		GeomPoint3d ptMin2d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax2d = new GeomPoint3d(arr[1]);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public void setPtCentroid(GeomPoint3d ptCentroid) {
		this.ptCentroid = ptCentroid;
	}

    public ArrayList<GeomPoint3d> getLsPts() {
        return this.lsPts;
    }

    public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}
    
}
