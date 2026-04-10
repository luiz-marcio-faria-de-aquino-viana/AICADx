/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadColunaEsgoto.java
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

package br.com.tlmv.aicadxmod.esgoto.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.ICadEntity;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
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
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.esgoto.dao.record.CadColunaEsgotoRecord;

public class CadColunaEsgoto extends CadEntity 
{
//Private
    private GeomPoint3d ptCenter;
    //
    private String identificadorColuna;
    private double insideRadius;    
    private double outsideRadius;    
    private double thickness;    
    private double altura;    
    
//Public

    public CadColunaEsgoto(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODESCOLUNA, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter, double zH, String identificadorColuna, double insideRadius, double outsideRadius, double thickness, double altura) {
		this.init(ptCenter.getX(), ptCenter.getY(), zH, identificadorColuna, insideRadius, outsideRadius, thickness, altura);
	}
	
	private void init(GeomPoint3d ptCenter, String identificadorColuna, double insideRadius, double outsideRadius, double thickness, double altura) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), identificadorColuna, insideRadius, outsideRadius, thickness, altura);
	}

	public void init(
		double xCenter, 
		double yCenter, 
		double zCenter, 
		String identificadorColuna,
		double insideRadius, 
		double outsideRadius, 
		double thickness, 
		double altura) 
	{
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
	    this.identificadorColuna = identificadorColuna;
		this.insideRadius = insideRadius;
		this.outsideRadius = outsideRadius;		
		this.thickness = thickness;
		this.altura = altura;

		this.createAllDrawCache();
	}
	
	@Override
	public void init(ICadObject o) {
		CadColunaEsgoto other = (CadColunaEsgoto)o;

		GeomPoint3d ptCenter = new GeomPoint3d(other.ptCenter);
		String identificadorColuna = other.getIdentificadorColuna();
		double insideRadius = other.insideRadius;
		double outsideRadius = other.outsideRadius;		
		double thickness = other.thickness;
		double altura = other.altura;
		
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), identificadorColuna, insideRadius, outsideRadius, thickness, altura);
	}
	
	/* CREATE */
	
	public static CadColunaEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptCenter, double zH, String identificadorColuna, double insideRadius, double outsideRadius, double thickness, double altura) {
    	CadColunaEsgoto o = new CadColunaEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptCenter, zH, identificadorColuna, insideRadius, outsideRadius, thickness, altura);
    	return o;
    }
	
	public static CadColunaEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptCenter, String identificadorColuna, double insideRadius, double outsideRadius, double thickness, double altura) {
		CadColunaEsgoto o = new CadColunaEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptCenter, identificadorColuna, insideRadius, outsideRadius, thickness, altura);
    	return o;
    }
	
	public static CadColunaEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, String identificadorColuna, double insideRadius, double outsideRadius, double thickness, double altura) {
    	CadColunaEsgoto o = new CadColunaEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(xCenter, yCenter, zCenter, identificadorColuna, insideRadius, outsideRadius, thickness, altura);
    	return o;
    }
	
	public static CadColunaEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, String identificadorColuna, double insideRadius, double outsideRadius, double thickness, double altura, boolean bLocked) {
    	CadColunaEsgoto o = new CadColunaEsgoto(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(xCenter, yCenter, zCenter, identificadorColuna, insideRadius, outsideRadius, thickness, altura);
    	return o;
    }
	
	public static CadColunaEsgoto create(CadColunaEsgoto other) {
    	CadColunaEsgoto o = new CadColunaEsgoto(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadColunaEsgoto create(CadBlockDef blkDef, CadColunaEsgoto other) {
    	CadColunaEsgoto o = new CadColunaEsgoto(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadColunaEsgoto duplicate()
	{
		CadColunaEsgoto other = CadColunaEsgoto.create(this);
		return other;
	}
	
	@Override
	public CadColunaEsgoto duplicate(CadBlockDef blkDef)
	{
		CadColunaEsgoto other = CadColunaEsgoto.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadColunaEsgoto copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadColunaEsgoto other = CadColunaEsgoto.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadColunaEsgoto moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);

    	MoveData2dVO oNewCenter = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptCenter2d);
    	this.ptCenter = new GeomPoint3d(oNewCenter.getPtDest());

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
	public CadColunaEsgoto scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO oNewCenter = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptCenter2d);
    	this.ptCenter = new GeomPoint3d(oNewCenter.getPtDest());		
    	this.insideRadius = this.insideRadius * oNewCenter.getScale();
    	this.outsideRadius = this.outsideRadius * oNewCenter.getScale();

		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public CadColunaEsgoto offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadColunaEsgoto oOffset = copyTo(ptIMcs, ptFMcs);
		return oOffset;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptCenter.toPropertyList("Pt.Center", true) );
		//
		lsProperty.add( new ItemDataVO("Ident.Coluna", this.identificadorColuna, true) );
		lsProperty.add( new ItemDataVO("Inside Radius (m)", nf3.format(this.insideRadius), true) );
		lsProperty.add( new ItemDataVO("Outside Radius (m)", nf3.format(this.outsideRadius), false) );
		lsProperty.add( new ItemDataVO("Thickness (m)", nf3.format(this.thickness), true) );
		lsProperty.add( new ItemDataVO("Altura (m)", nf3.format(this.altura), true) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"XCenter:%s;" +
			"YCenter:%s;" +
			"ZCenter:%s;" + 
			"IdentificadorColuna:%s;" +
			"InsideRadius:%s;" +
			"OutsideRadius:%s;" + 
			"Thickness:%s;" + 
			"Altura:%s; ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			this.identificadorColuna,
			nf6.format(this.insideRadius),
			nf6.format(this.outsideRadius),
			nf6.format(this.thickness),
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

	public LineStringEntityDrawCache createDrawCache2d_circle2d(LineStringEntityDrawCache oCilinder, GeomPoint3d ptCenterMcs, double radiusMcs)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = (2.0 * Math.PI) / numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt0Mcs = xPtCenterMcs + radiusMcs;
    	double yPt0Mcs = yPtCenterMcs;
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0Mcs, yPt0Mcs);
    	oCilinder.addPoint3d( new GeomPoint3d(vDirMcs.getXF(), vDirMcs.getYF(), 0.0) );
    	for(int i = 0; i < numsegs; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);
    		oCilinder.addPoint3d( new GeomPoint3d(vNextDirMcs.getXF(), vNextDirMcs.getYF(), 0.0) );
    		
        	vDirMcs = vNextDirMcs;
    	}		
		return oCilinder;
	}
	
	public DrawCache createOsnapCache_center(DrawCache osnapCache, GeomPoint3d ptCenterMcs)
	{
    	double zp = ptCenterMcs.getZ();
    	
    	GeomPoint2d ptCenter = new GeomPoint2d(ptCenterMcs);    	
    	GeomVector2d vAxisX = new GeomVector2d(1.0, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptCenter, vAxisX);
    	
    	//CENTER
    	//
    	osnapCache.addOsnapItem( AppDefs.OSNAPMODE_CENTER, new GeomPoint3d(ptCenterMcs) );

		return osnapCache;
	}
	
	public DrawCache createOsnapCache_circle2d(DrawCache osnapCache, GeomPoint3d ptCenterMcs, double radiusMcs)
	{
    	double zp = ptCenterMcs.getZ();
    	
    	GeomPoint2d ptCenter = new GeomPoint2d(ptCenterMcs);    	
    	GeomVector2d vAxisX = new GeomVector2d(radiusMcs, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptCenter, vAxisX);
    	
    	//CENTER
    	//
    	osnapCache.addOsnapItem( AppDefs.OSNAPMODE_CENTER, new GeomPoint3d(ptCenterMcs) );

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
		
	@Override
	public DrawCache createDrawCache2d()
	{
		DrawCache cache = new DrawCache();

		LineStringEntityDrawCache oCilinder = new LineStringEntityDrawCache(); 
		this.createDrawCache2d_circle2d(oCilinder, this.ptCenter, this.insideRadius);
		cache.addItem(oCilinder);
		
		oCilinder = new LineStringEntityDrawCache(); 
		this.createDrawCache2d_circle2d(oCilinder, this.ptCenter, this.outsideRadius);
		cache.addItem(oCilinder);

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

    	this.createOsnapCache_center(osnapCache, this.ptCenter);
    	//
    	this.createOsnapCache_circle2d(osnapCache, this.ptCenter, this.insideRadius);
    	this.createOsnapCache_circle2d(osnapCache, this.ptCenter, this.outsideRadius);

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
				
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		super.redraw2d(v, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, g);
        
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
    public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
    {
    	if( !this.isVisible() ) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint3d ptDestMin3dMcs = new GeomPoint3d(
        	this.ptCenter.getX(),
        	this.ptCenter.getY(),
        	this.ptCenter.getZ() );

        GeomVector3d axisZ = GeomUtil.axisZ3d();
        
        prep.addCilinder(v, this, c, ptDestMin3dMcs, axisZ, this.altura, this.insideRadius, true, true);
        prep.addCilinder(v, this, c, ptDestMin3dMcs, axisZ, this.altura, this.outsideRadius, true, true);
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
			new String( identificadorColuna ),
			new Double( insideRadius ), 
			new Double( outsideRadius ),    
			new Double( thickness ),    
			new Double( altura )    
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadColunaEsgotoRecord entRec = new CadColunaEsgotoRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );

		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	/* UTILITIES */

	@Override
	public GeomPoint3d nearestConexao(GeomPoint3d ptRef) {
		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef );

		GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );
		
		GeomVector2d vDir = new GeomVector2d(ptCenter2d, ptRef2d);
		GeomVector2d uDir = vDir.otherUnit(); 
		
		double dRaioExterno = this.outsideRadius;
		GeomPoint2d ptResult2d = ptCenter2d.otherMoveTo(uDir, dRaioExterno);
		
		GeomPoint3d ptResult = new GeomPoint3d( ptResult2d );
		return ptResult;
	}

	@Override
	public GeomPoint3d nearestConexaoEntrada(GeomPoint3d ptRef) {
		GeomPoint3d ptResult = this.nearestConexao(ptRef);
		return ptResult;
	}

	@Override
	public GeomPoint3d nearestConexaoSaida(GeomPoint3d ptRef) {
		GeomPoint3d ptResult = this.nearestConexao(ptRef);
		return ptResult;
	}
	
    /* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		double xPtCenter = this.ptCenter.getX();
		double yPtCenter = this.ptCenter.getY();
		double zPtCenter = this.ptCenter.getZ();
		
		double xPtMin = xPtCenter - this.outsideRadius;
		double yPtMin = yPtCenter - this.outsideRadius;
		double zPtMin = zPtCenter;
		
		double xPtMax = xPtCenter + this.outsideRadius;
		double yPtMax = yPtCenter + this.outsideRadius;
		double zPtMax = zPtCenter + this.altura;
		
		GeomPoint3d ptMin3d = new GeomPoint3d(xPtMin, yPtMin, zPtMin);
		GeomPoint3d ptMax3d = new GeomPoint3d(xPtMax, yPtMax, zPtMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		double xPtCenter = this.ptCenter.getX();
		double yPtCenter = this.ptCenter.getY();
		
		double xPtMin = xPtCenter - this.outsideRadius;
		double yPtMin = yPtCenter - this.outsideRadius;
		
		double xPtMax = xPtCenter + this.outsideRadius;
		double yPtMax = yPtCenter + this.outsideRadius;
		
		GeomPoint2d ptMin2d = new GeomPoint2d(xPtMin, yPtMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(xPtMax, yPtMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"IDENTIFICADOR=" + this.identificadorColuna + "^" +
			"ALTURA=" + Double.toString( this.altura ) + "^" +
			"INSIDERAIO=" + Double.toString( this.insideRadius ) + "^" +
			"INSIDEDIAMETRO=" + Double.toString( 2.0 * this.insideRadius ) + "^" +
			"OUTSIDERAIO=" + Double.toString( this.outsideRadius ) + "^" +
			"OUTSIDEDIAMETRO=" + Double.toString( 2.0 * this.outsideRadius );
		return searchString;
	}

	public GeomPoint3d getPtCenter() {
        return this.ptCenter;
    }

    public double getInsideRadius() {
		return insideRadius;
	}

	public void setInsideRadius(double insideRadius) {
		this.insideRadius = insideRadius;
	}

	public double getOutsideRadius() {
		return outsideRadius;
	}

	public void setOutsideRadius(double outsideRadius) {
		this.outsideRadius = outsideRadius;
	}

	public void setPtCenter(GeomPoint3d ptCenter) {
		this.ptCenter = ptCenter;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getAltura() {
		return altura;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public String getIdentificadorColuna() {
		return identificadorColuna;
	}

	public void setIdentificadorColuna(String identificadorColuna) {
		this.identificadorColuna = identificadorColuna;
	}

}
