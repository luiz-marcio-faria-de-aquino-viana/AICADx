/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadArc.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/01/2025
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
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.CadArcRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCircleRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadArc extends CadEntity 
{
//Private
    GeomPoint3d ptCenter;
    double startAngle;
    double endAngle;
    double radius;
    
//Public

    public CadArc(CadBlockDef oBlkDef, CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_ARC, oBlkDef, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter, double radio, double startAngle, double endAngle) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, radio, startAngle, endAngle);
	}
	
	private void init(GeomPoint3d ptCenter, double radio, double startAngle, double endAngle) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radio, startAngle, endAngle);
	}

	public void init(double xCenter, double yCenter, double zCenter, double radius, double startAngle, double endAngle) {
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.radius = radius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
		
		this.createAllDrawCache();
    }

	public void init(CadArc otherArc) {
		GeomPoint3d ptTmpCenter = otherArc.ptCenter;
		double tmpRadius = otherArc.radius;
		double tmpStartAngle = otherArc.startAngle;
		double tmpEndAngle = otherArc.endAngle;
		
		this.init(ptTmpCenter.getX(), ptTmpCenter.getY(), ptTmpCenter.getZ(), tmpRadius, tmpStartAngle, tmpEndAngle);
    }
	
	/* CREATE */
	
	public static CadArc create(CadBlockDef oBlkDef, CadLayerDef oLayer, GeomPoint2d ptCenter, double radius, double startAngle, double endAngle) {
    	CadArc o = new CadArc(oBlkDef, oLayer);
    	o.init(ptCenter, radius, startAngle, endAngle);
    	return o;
    }
	
	public static CadArc create(CadBlockDef oBlkDef, CadLayerDef oLayer, GeomPoint3d ptCenter, double radius, double startAngle, double endAngle) {
    	CadArc o = new CadArc(oBlkDef, oLayer);
    	o.init(ptCenter, radius, startAngle, endAngle);
    	return o;
    }

	public static CadArc create(CadBlockDef oBlkDef, CadLayerDef oLayer, double xCenter, double yCenter, double zCenter, double radius, double startAngle, double endAngle) {
    	CadArc o = new CadArc(oBlkDef, oLayer);
    	o.init(xCenter, yCenter, zCenter, radius, startAngle, endAngle);
    	return o;
    }
    
	public static CadArc create(CadArc otherArc) {
    	CadArc o = new CadArc(otherArc.getBlkDef(), otherArc.getLayer());
    	o.init(otherArc);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadArc duplicate()
	{
		CadArc other = CadArc.create(this);
		return other;
	}	
	
	@Override
	public CadArc copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadArc otherArc = CadArc.create(this);
		otherArc.moveTo(ptIMcs, ptFMcs);
		return otherArc;
	}

	@Override
	public CadArc moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
		
		GeomVector2d vIF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs);
		
		GeomVector2d axisX = new GeomVector2d(1.0, 0); 
		
		double angle_A = GeomUtil.convertRadToDegrees( axisX.angleTo(vIF2dMcs) );		

		double angle_I = this.startAngle - angle_A;
		double angle_F = this.endAngle - angle_A;
		
		this.startAngle = (AppDefs.MATHVAL_360d + angle_A) - angle_F;
		this.endAngle = (AppDefs.MATHVAL_360d + angle_A) - angle_I;
		
    	this.ptCenter = GeomUtil.mirror(this.ptCenter, ptI2dMcs, ptF2dMcs);

    	this.createAllDrawCache();
		return this;
	}
	
	@Override
	public CadArc scaleTo(double refDistMcs, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDistMcs, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());
        this.radius = this.radius * o.getScale();

    	this.createAllDrawCache();
		return this;
	}
	
	@Override
	public CadArc offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadArc oArc = copyTo(ptIMcs, ptFMcs);
		return oArc;
	}

	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptCenter.toPropertyList("Pt.Center") );
		//
		lsProperty.add( new ItemDataVO("Radius", nf3.format(this.radius)) );
		//
		lsProperty.add( new ItemDataVO("Start Angle", nf3.format(this.startAngle)) );
		lsProperty.add( new ItemDataVO("End Angle", nf3.format(this.endAngle)) );
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XCenter: %s; YCenter: %s; ZCenter: %s; Radius: %s; StartAngle: %s; EndAngle: %s); ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.radius),
			nf6.format(this.startAngle),
			nf6.format(this.endAngle) );
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
		LineStringEntityDrawCache oArc = new LineStringEntityDrawCache(); 

    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;
    	
    	double startAngleRad = GeomUtil.convertDegreesToRad( this.startAngle );
    	double endAngleRad = GeomUtil.convertDegreesToRad( this.endAngle );

    	double angleDiffRad = endAngleRad - startAngleRad;

    	double stepAngleRad = angleDiffRad /numsegs;
    	
    	double xPtCenterMcs = this.ptCenter.getX();
    	double yPtCenterMcs = this.ptCenter.getY();
    	
    	double xPt0Mcs = xPtCenterMcs + radius;
    	double yPt0Mcs = yPtCenterMcs;
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0Mcs, yPt0Mcs);
    	vDirMcs.selfRotateToRad(startAngleRad);
    	oArc.addPoint3d( new GeomPoint3d(vDirMcs.getXF(), vDirMcs.getYF(), 0.0) );
    	for(int i = 0; i < numsegs; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);
    		oArc.addPoint3d( new GeomPoint3d(vNextDirMcs.getXF(), vNextDirMcs.getYF(), 0.0) );
    		
        	vDirMcs = vNextDirMcs;
    	}		

		DrawCache cache = new DrawCache();
		cache.addItem(oArc);
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
    	
    	//ENDPOINT
    	//
    	GeomVector2d vStartPoint = vDir.otherRotateToDegrees(this.startAngle);
    	GeomVector2d vEndPoint = vDir.otherRotateToDegrees(this.endAngle);
    	
    	osnapCache.addOsnapItem( AppDefs.OSNAPMODE_ENDPOINT, new GeomPoint3d(vStartPoint.getXF(), vStartPoint.getYF(), zp) );
    	osnapCache.addOsnapItem( AppDefs.OSNAPMODE_ENDPOINT, new GeomPoint3d(vEndPoint.getXF(), vEndPoint.getYF(), zp) );
		
    	//MIDDLE
    	//
    	double middleAngle = this.startAngle + (this.endAngle - this.startAngle) / 2.0;    	
    	GeomVector2d vMiddlePoint = vDir.otherRotateToDegrees(middleAngle);
    	
    	osnapCache.addOsnapItem( AppDefs.OSNAPMODE_MIDDLE, new GeomPoint3d(vMiddlePoint.getXF(), vMiddlePoint.getYF(), zp) );

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
    	double d0_I = 0.0;
    	double d0_F = 0.0 + AppDefs.MATHPREC_MIN;
    	
    	double d90_I = 90.0 - AppDefs.MATHPREC_MIN;
    	double d90_F = 90.0 + AppDefs.MATHPREC_MIN;
    	
    	double d180_I = 180.0 - AppDefs.MATHPREC_MIN;
    	double d180_F = 180.0 + AppDefs.MATHPREC_MIN;
    	
    	double d270_I = 270.0 - AppDefs.MATHPREC_MIN;
    	double d270_F = 270.0 + AppDefs.MATHPREC_MIN;
    	
    	if( (this.startAngle > d0_I) && (this.startAngle <= d0_F) )
    		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt0d.getXF(), vPt0d.getYF(), zp) );
    	if( (this.startAngle <= d90_F) && (this.endAngle > d90_I) )
    		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt90d.getXF(), vPt90d.getYF(), zp) );
    	if( (this.startAngle <= d180_F) && (this.endAngle > d180_I) )
    		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt180d.getXF(), vPt180d.getYF(), zp) );
    	if( (this.startAngle <= d270_F) && (this.endAngle > d270_I) )
    		osnapCache.addOsnapItem(  AppDefs.OSNAPMODE_QUADRANT, new GeomPoint3d(vPt270d.getXF(), vPt270d.getYF(), zp) );

		return osnapCache;
	}

    /* DRAWING */

	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
		if(this.isDeleted()) return;

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
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */

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

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCenter);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		BaseDao dao = db.getDao();
		CadEntityBaseDao entDao = dao.create(this.getObjType()); 
		CadArcRecord entRec = new CadArcRecord(this); 
		entDao.insertOrUpdate(schemaName, (BaseEntityRecord) entRec);		
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
			"DIAMETRO=" + Double.toString( 2.0 * this.radius ) +
			"ANGULO=" + Double.toString( this.startAngle ) +
			"ANGULO=" + Double.toString( this.endAngle );
		return searchString;
	}

	public GeomPoint3d getPtCenter() {
        return this.ptCenter;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getStartAngle() {
        return this.startAngle;
    }

    public double getEndAngle() {
        return this.endAngle;
    }

}
