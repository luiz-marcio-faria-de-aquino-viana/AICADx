/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadBox3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/02/2025
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
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadBox3dRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadBox3d extends CadEntity 
{
//Private
    private GeomPoint3d ptMin;
    private GeomPoint3d ptMax;
    //
    private double altura;    
    
//Public

    public CadBox3d(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_BOX3D, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
    public void init(GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
		this.init(ptMin.getX(), ptMin.getY(), ptMax.getX(), ptMax.getY(), altura);
	}
	
	public void init(GeomPoint3d ptMin, GeomPoint3d ptMax, double altura) {
		this.init(ptMin.getX(), ptMin.getY(), ptMax.getX(), ptMax.getY(), altura);
	}

	public void init(double xMin, double yMin, double xMax, double yMax, double altura) {
		double zLevel = this.getZLevel();
		
		this.ptMin = new GeomPoint3d(xMin, yMin, zLevel);
		this.ptMax = new GeomPoint3d(xMax, yMax, zLevel);
		//
		this.altura = altura;
		
		this.createAllDrawCache();
    }
	
	@Override
	public void init(ICadObject o) {
		CadBox3d other = (CadBox3d)o;

		GeomPoint3d ptTmpPtMin = other.getPtMin();
		GeomPoint3d ptTmpPtMax = other.getPtMax();
		
		this.init(
			ptTmpPtMin.getX(), 
			ptTmpPtMin.getY(),
			//
			ptTmpPtMax.getX(), 
			ptTmpPtMax.getY(),
			//
			other.getAltura() );
	}
	
	/* CREATE */
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
    	CadBox3d o = new CadBox3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptMin, ptMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptMin, GeomPoint3d ptMax, double altura) {
		GeomPoint2d ptMin2d = new GeomPoint2d(ptMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(ptMax);
		
		double zLevel = ptMin.getZ();
		
		CadBox3d o = new CadBox3d(oBlkDef, oLayer, oLevel, zLevel, false);
    	o.init(ptMin2d, ptMax2d, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xMin, double yMin, double xMax, double yMax, double zLevel, double altura) {
    	CadBox3d o = new CadBox3d(oBlkDef, oLayer, oLevel, zLevel, false);
    	o.init(xMin, yMin, xMax, yMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xMin, double yMin, double xMax, double yMax, double zLevel, double altura, boolean bLocked) {
    	CadBox3d o = new CadBox3d(oBlkDef, oLayer, oLevel, zLevel, bLocked);
    	o.init(xMin, yMin, xMax, yMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBox3d other) {
    	CadBox3d o = new CadBox3d(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadBox3d create(CadBlockDef blkDef, CadBox3d other) {
    	CadBox3d o = new CadBox3d(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadBox3d duplicate()
	{
		CadBox3d other = CadBox3d.create(this);
		return other;
	}
	
	@Override
	public CadBox3d duplicate(CadBlockDef blkDef)
	{
		CadBox3d other = CadBox3d.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadBox3d copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadBox3d otherRectangle = CadBox3d.create(this);
		otherRectangle.moveTo(ptIMcs, ptFMcs);
		return otherRectangle;
	}

	@Override
	public CadBox3d moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);
    	MoveData2dVO oMin = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigMin2dMcs);
    	this.ptMin = new GeomPoint3d(oMin.getPtDest());

    	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);
    	MoveData2dVO oMax = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigMax2dMcs);
    	this.ptMax = new GeomPoint3d(oMax.getPtDest());

		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		GeomPoint3d ptCentroid = GeomUtil.midPointOf(this.ptMin, this.ptMax);
		
		GeomPoint3d newPtCentroid = GeomUtil.mirror(ptCentroid, ptI2dMcs, ptF2dMcs);

		double xMin = this.ptMin.getX();
		double yMin = this.ptMin.getY();

		double xMax = this.ptMax.getX();
		double yMax = this.ptMax.getY();
		
		double width = xMax - xMin;
		double height = yMax - yMin;
	
		double hWidth = width / 2.0;
		double hHeight = height / 2.0;

		double newXMin = newPtCentroid.getX() - hWidth;
		double newYMin = newPtCentroid.getY() - hHeight;
		double newZMin = newPtCentroid.getZ();

		double newXMax = newPtCentroid.getX() + hWidth;
		double newYMax = newPtCentroid.getY() + hHeight;
		double newZMax = newPtCentroid.getZ();
		
		this.ptMin = new GeomPoint3d(newXMin, newYMin, newZMin);
		this.ptMax = new GeomPoint3d(newXMax, newYMax, newZMax);
		
		this.createAllDrawCache();
		return this;
	}

	@Override
	public CadBox3d scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);

    	ScaleData2dVO oMin = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigMin2dMcs);
    	this.ptMin = new GeomPoint3d(oMin.getPtDest());
		
    	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);

    	ScaleData2dVO oMax = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigMax2dMcs);
    	this.ptMax = new GeomPoint3d(oMax.getPtDest());
		
		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public CadBox3d offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadBox3d oOffset = copyTo(ptIMcs, ptFMcs);
		return oOffset;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptMin.toPropertyList("Pt.Min", true) );
		lsProperty.addAll( this.ptMax.toPropertyList("Pt.Max", true) );
		//
		lsProperty.add( new ItemDataVO("Altura", nf3.format(this.altura), true) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XMin: %s; YMin: %s; ZMin: %s)-(XMax: %s; YMax: %s; ZMax: %s); ", 
			nf6.format(this.ptMin.getX()), 
			nf6.format(this.ptMin.getY()), 
			nf6.format(this.ptMin.getZ()),
			//
			nf6.format(this.ptMax.getX()), 
			nf6.format(this.ptMax.getY()), 
			nf6.format(this.ptMax.getZ()),
			//
			nf6.format(this.altura) );
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
		LineStringEntityDrawCache oLine = new LineStringEntityDrawCache(); 

		double xMin = this.ptMin.getX();
		double yMin = this.ptMin.getY();
		
		double xMax = this.ptMax.getX();
		double yMax = this.ptMax.getY();
		
		oLine.addPoint3d( new GeomPoint3d(xMin, yMin, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMax, yMin, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMax, yMax, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMin, yMax, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMin, yMin, 0.0) );
		
		DrawCache cache = new DrawCache();
		cache.addItem(oLine);
		return cache;
	}

	@Override
	public DrawCache createDrawCache3d()
	{
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		DrawCache osnapCache = new DrawCache();

    	//ENDPOINT
    	//
    	double xMinMcs = this.ptMin.getX();
    	double yMinMcs = this.ptMin.getY();
    	double zMinMcs = this.ptMin.getZ();
    	//
    	double xMaxMcs = this.ptMax.getX();
    	double yMaxMcs = this.ptMax.getY();
    	double zMaxMcs = this.ptMin.getZ() + this.altura;
    	
    	GeomPoint3d pt3d0 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMinMcs, yMinMcs, zMinMcs);
    	GeomPoint3d pt3d1 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMaxMcs, yMinMcs, zMinMcs);
    	GeomPoint3d pt3d2 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMaxMcs, yMaxMcs, zMinMcs);
    	GeomPoint3d pt3d3 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMinMcs, yMaxMcs, zMinMcs);

    	osnapCache.addOsnapItem( pt3d0 );
    	osnapCache.addOsnapItem( pt3d1 );
    	osnapCache.addOsnapItem( pt3d2 );
    	osnapCache.addOsnapItem( pt3d3 );
    	
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, this.ptMin, this.ptMax);
    	osnapCache.addOsnapItem( pt3dMid );

    	GeomPoint3d pt3dMid0 = GeomUtil.midPointOf(pt3d0, pt3d1);
    	GeomPoint3d pt3dMid1 = GeomUtil.midPointOf(pt3d1, pt3d2);
    	GeomPoint3d pt3dMid2 = GeomUtil.midPointOf(pt3d2, pt3d3);
    	GeomPoint3d pt3dMid3 = GeomUtil.midPointOf(pt3d3, pt3d0);

    	osnapCache.addOsnapItem( pt3dMid0 );
    	osnapCache.addOsnapItem( pt3dMid1 );
    	osnapCache.addOsnapItem( pt3dMid2 );
    	osnapCache.addOsnapItem( pt3dMid3 );
		
    	//CENTER
    	//
    	GeomPoint3d ptCenter3d = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, pt3d0, pt3d2);
    	osnapCache.addOsnapItem( ptCenter3d );
    	
		return osnapCache;
	}

    /* DRAWING */
    
	@Override
    public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
    {
    	if( !this.isVisible() ) return;    	

    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        double zPtMin = this.getZLevelElevation() + this.getZLevel();
        double zPtMax = zPtMin + this.getAltura();
        
        GeomPoint3d ptDestMin3dMcs = new GeomPoint3d(
        	this.ptMin.getX(),
        	this.ptMin.getY(),
        	zPtMin );

        GeomPoint3d ptDestMax3dMcs = new GeomPoint3d(
        	this.ptMax.getX(),
        	this.ptMax.getY(),
        	zPtMax );
                
        GeomVector3d axisZ = GeomUtil.axisZ3d();
        prep.addBox2Pt(v, this, c, ptDestMin3dMcs, ptDestMax3dMcs, axisZ);
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
		GeomPoint3d ptResult = GeomUtil.midPointOf(ptMin, ptMax);
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
			new Double( this.ptMin.getX() ),
			new Double( this.ptMin.getY() ),
			new Double( this.ptMin.getZ() ),
			//
			new Double( this.ptMax.getX() ),
			new Double( this.ptMax.getY() ),
			new Double( this.ptMax.getZ() ),
			//
			new Double( this.altura ),
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadBox3dRecord entRec = new CadBox3dRecord(this); 
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
		GeomPoint3d ptMin3d = new GeomPoint3d(this.ptMin);
		GeomPoint3d ptMax3d = new GeomPoint3d(this.ptMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint2d ptMin2d = new GeomPoint2d(this.ptMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(this.ptMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}

    public GeomPoint3d getPtMin() {
        return this.ptMin;
    }

    public GeomPoint3d getPtMax() {
        return this.ptMax;
    }

	public double getAltura() {
		return altura;
	}

}
