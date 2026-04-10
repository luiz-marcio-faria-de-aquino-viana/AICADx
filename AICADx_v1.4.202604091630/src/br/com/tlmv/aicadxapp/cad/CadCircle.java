/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadLine.java
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
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadCircleRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadCircle extends CadEntity 
{
//Private
    GeomPoint3d ptCenter;
    double radius;
    
//Public

    public CadCircle() {
		super(AppDefs.OBJTYPE_CIRCLE, null, null, null, 0.0, false);		
    }

    public CadCircle(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_CIRCLE, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
    public void init(GeomPoint2d ptCenter, double radius) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, radius);
	}
	
	public void init(GeomPoint3d ptCenter, double radius) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radius);
	}

	public void init(double xCenter, double yCenter, double zCenter, double radius) {
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.radius = radius;

		this.createAllDrawCache();
    }
	
	@Override
	public void init(ICadObject o) {
		CadCircle other = (CadCircle)o;

		GeomPoint3d ptTmpCenter = other.ptCenter;
		double tmpRadius = other.radius;
		
		this.init(ptTmpCenter.getX(), ptTmpCenter.getY(), ptTmpCenter.getZ(), tmpRadius);
	}
	
	/* CREATE */
	
	public static CadCircle create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptCenter, double radius) {
    	CadCircle o = new CadCircle(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptCenter, radius);
    	return o;
    }
	
	public static CadCircle create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptCenter, double radius) {
    	CadCircle o = new CadCircle(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptCenter, radius);
    	return o;
    }

	public static CadCircle create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, double radius, boolean bLocked) {
    	CadCircle o = new CadCircle(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(xCenter, yCenter, zCenter, radius);
    	return o;
    }
	
	public static CadCircle create(CadCircle other) {
    	CadCircle o = new CadCircle(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadCircle create(CadBlockDef blkDef, CadCircle other) {
    	CadCircle o = new CadCircle(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadCircle duplicate()
	{
		CadCircle other = CadCircle.create(this);
		return other;
	}	
	
	@Override
	public CadCircle duplicate(CadBlockDef blkDef)
	{
		CadCircle other = CadCircle.create(blkDef, this);
		return other;
	}	
	
	@Override
	public CadCircle copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadCircle otherCircle = CadCircle.create(this);
		otherCircle.moveTo(ptIMcs, ptFMcs);
		return otherCircle;
	}

	@Override
	public CadCircle moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigCenter2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());

		this.createAllDrawCache();
		return this;
	}

    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptCenter = GeomUtil.mirror(this.ptCenter, ptI2dMcs, ptF2dMcs);

		this.createAllDrawCache();
		return this;
	}
	
	@Override
	public CadCircle scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());
        this.radius = this.radius * o.getScale();

		this.createAllDrawCache();
		return this;
	}
	
	@Override
	public CadCircle offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadCircle oCircle = copyTo(ptIMcs, ptFMcs);
		return oCircle;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XCenter: %s; YCenter: %s; ZCenter: %s; Radio: %s); ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.radius) );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptCenter.toPropertyList("Pt.Center", true) );
		//
		lsProperty.add( new ItemDataVO("Radius", nf3.format(this.radius), true) );
		
		return lsProperty;
	}

    /* DRAWCACHE */

	@Override
	public DrawCache createDrawCache2d()
	{
		LineStringEntityDrawCache oCircle = new LineStringEntityDrawCache(); 

    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = (2.0 * Math.PI) / numsegs;
    	double startAngleRad = 0.0;
    	
    	double xPtCenterMcs = this.ptCenter.getX();
    	double yPtCenterMcs = this.ptCenter.getY();
    	
    	double xPt0Mcs = xPtCenterMcs + this.radius;
    	double yPt0Mcs = yPtCenterMcs;
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0Mcs, yPt0Mcs);
    	GeomPoint3d ptI = new GeomPoint3d(vDirMcs.getXF(), vDirMcs.getYF(), 0.0);

    	oCircle.addPoint3d( new GeomPoint3d(ptI.getX(), ptI.getY(), 0.0) );
    	for(int i = 0; i < numsegs; i++) {
        	startAngleRad += stepAngleRad;

        	GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(startAngleRad);
        	GeomPoint3d ptF = new GeomPoint3d(vNextDirMcs.getXF(), vNextDirMcs.getYF(), 0.0);

        	oCircle.addPoint3d( new GeomPoint3d(ptF.getX(), ptF.getY(), 0.0) );

        	ptI = ptF;
    	}		

		DrawCache cache = new DrawCache();
		cache.addItem(oCircle);
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

    	double zp = this.ptCenter.getZ();
    	
    	GeomPoint2d ptCenter = new GeomPoint2d(this.ptCenter);    	
    	GeomVector2d vAxisX = new GeomVector2d(this.radius, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptCenter, vAxisX);
    	
    	//CENTER
    	//
    	osnapCache.addOsnapItem( AppDefs.OSNAPMODE_CENTER, new GeomPoint3d(this.ptCenter) );

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vDir.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vDir.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vDir.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vDir.otherRotateToDegrees(270.0);
    	
    	//QUADRANT_RANGES
		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt0d.getXF(), vPt0d.getYF(), zp) );
		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt90d.getXF(), vPt90d.getYF(), zp) );
		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt180d.getXF(), vPt180d.getYF(), zp) );
		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt270d.getXF(), vPt270d.getYF(), zp) );

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
    		
            GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);
        	DrawUtil.drawPointMcs(v, ptCenter2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);

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
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCenter);
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
			new Double( this.ptCenter.getX() ),
			new Double( this.ptCenter.getY() ),
			new Double( this.ptCenter.getZ() ),
			//
			new Double( this.radius )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadCircleRecord entRec = new CadCircleRecord(this); 
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
		ArrayList<DxfCadEntity> lsDxfCadEntity = DxfUtil.toDxfCircle(
			this.getLayer(),
			//PTCENTER
			this.ptCenter.getX(),
			this.ptCenter.getY(), 
			this.ptCenter.getZ(),
			//RADIUS
			this.getRadius()
		);		
		return lsDxfCadEntity;
	}

	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}
	
    /* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		double xPtCenter = this.ptCenter.getX();
		double yPtCenter = this.ptCenter.getY();
		double zPtCenter = this.ptCenter.getZ();
		
		double xPtMin = xPtCenter - this.radius;
		double yPtMin = yPtCenter - this.radius;
		double zPtMin = zPtCenter - this.radius;
		
		double xPtMax = xPtCenter + this.radius;
		double yPtMax = yPtCenter + this.radius;
		double zPtMax = zPtCenter + this.radius;
		
		GeomPoint3d ptMin3d = new GeomPoint3d(xPtMin, yPtMin, zPtMin);
		GeomPoint3d ptMax3d = new GeomPoint3d(xPtMax, yPtMax, zPtMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		double xPtCenter = this.ptCenter.getX();
		double yPtCenter = this.ptCenter.getY();
		
		double xPtMin = xPtCenter - this.radius;
		double yPtMin = yPtCenter - this.radius;
		
		double xPtMax = xPtCenter + this.radius;
		double yPtMax = yPtCenter + this.radius;
		
		GeomPoint2d ptMin2d = new GeomPoint2d(xPtMin, yPtMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(xPtMax, yPtMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" + 
			"RAIO=" + Double.toString( this.radius ) +
			"DIAMETRO=" + Double.toString( 2.0 * this.radius );
		return searchString;
	}

	public GeomPoint3d getPtCenter() {
        return this.ptCenter;
    }

    public double getRadius() {
        return this.radius;
    }

}
