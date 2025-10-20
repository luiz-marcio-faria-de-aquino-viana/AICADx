/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
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
import br.com.tlmv.aicadxapp.dao.record.CadBox3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLineRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadBox3d extends CadEntity 
{
//Private
    private GeomPoint3d ptMin;
    private GeomPoint3d ptMax;
    private double altura;    
    
//Public

    public CadBox3d(CadBlockDef oBlkDef, CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BOX3D, oBlkDef, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
		this.init(ptMin.getX(), ptMin.getY(), 0.0, ptMax.getX(), ptMax.getY(), 0.0, altura);
	}
	
	private void init(GeomPoint3d ptMin, GeomPoint3d ptMax, double altura) {
		this.init(ptMin.getX(), ptMin.getY(), ptMin.getZ(), ptMax.getX(), ptMax.getY(), ptMax.getZ(), altura);
	}

	public void init(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax, double altura) {
		this.ptMin = new GeomPoint3d(xMin, yMin, zMin);
		this.ptMax = new GeomPoint3d(xMax, yMax, zMin + altura);
		//
		this.altura = altura;
		
		this.createAllDrawCache();
    }
	
	private void init(CadBox3d otherRectangle) {
		GeomPoint3d ptTmpPtMin = otherRectangle.ptMin;
		GeomPoint3d ptTmpPtMax = otherRectangle.ptMax;
		
		this.init(
			ptTmpPtMin.getX(), 
			ptTmpPtMin.getY(), 
			ptTmpPtMin.getZ(), 
			ptTmpPtMax.getX(), 
			ptTmpPtMax.getY(), 
			ptTmpPtMax.getZ(),
			otherRectangle.altura);
	}
	
	/* CREATE */
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
    	CadBox3d o = new CadBox3d(oBlkDef, oLayer);
    	o.init(ptMin, ptMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, GeomPoint3d ptMin, GeomPoint3d ptMax, double altura) {
		GeomPoint2d ptMin2d = new GeomPoint2d(ptMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(ptMax);
		
		CadBox3d o = new CadBox3d(oBlkDef, oLayer);
    	o.init(ptMin2d, ptMax2d, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax, double altura) {
    	CadBox3d o = new CadBox3d(oBlkDef, oLayer);
    	o.init(xMin, yMin, zMin, xMax, yMax, zMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBox3d otherRectangle) {
    	CadBox3d o = new CadBox3d(otherRectangle.getBlkDef(), otherRectangle.getLayer());
    	o.init(otherRectangle);
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

		lsProperty.addAll( this.ptMin.toPropertyList("Pt.Min") );
		lsProperty.addAll( this.ptMax.toPropertyList("Pt.Max") );
		//
		lsProperty.add( new ItemDataVO("Altura", nf3.format(this.altura)) );
		
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
			nf6.format(this.ptMax.getX()), 
			nf6.format(this.ptMax.getY()), 
			nf6.format(this.ptMax.getZ()) );
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
    	if(this.isDeleted()) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint3d ptDestMin3dMcs = new GeomPoint3d(
        	this.ptMin.getX(),
        	this.ptMin.getY(),
        	this.ptMin.getZ() );

        GeomPoint3d ptDestMax3dMcs = new GeomPoint3d(
        	this.ptMax.getX(),
        	this.ptMax.getY(),
        	this.ptMax.getZ() );
                
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		BaseDao dao = db.getDao();
		CadEntityBaseDao entDao = dao.create(this.getObjType()); 
		CadBox3dRecord entRec = new CadBox3dRecord(this); 
		entDao.insertOrUpdate(schemaName, (BaseEntityRecord) entRec);		
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
