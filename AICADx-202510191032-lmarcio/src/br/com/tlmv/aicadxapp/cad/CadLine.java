/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadLine.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

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
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLineRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfEntry;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadLine extends CadEntity 
{
//Private
    GeomPoint3d ptI;
    GeomPoint3d ptF;
	
//Public

    public CadLine(CadBlockDef oBlkDef, CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_LINE, oBlkDef, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptI, GeomPoint2d ptF) {
		this.init(ptI.getX(), ptI.getY(), 0.0, ptF.getX(), ptF.getY(), 0.0);
	}
	
	private void init(GeomPoint3d ptI, GeomPoint3d ptF) {
		this.init(ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ());
	}

	public void init(double xI, double yI, double zI, double xF, double yF, double zF) {
    	this.ptI = new GeomPoint3d(xI, yI, zI);
    	this.ptF = new GeomPoint3d(xF, yF, zF);

		this.createAllDrawCache();
	}
	
	private void init(CadLine otherLine) {
		GeomPoint3d ptTmpPtI = otherLine.ptI;
		GeomPoint3d ptTmpPtF = otherLine.ptF;
		
		this.init(ptTmpPtI.getX(), ptTmpPtI.getY(), ptTmpPtI.getZ(), ptTmpPtF.getX(), ptTmpPtF.getY(), ptTmpPtF.getZ());
	}
	
	/* CREATE */
	
	public static CadLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, GeomPoint2d ptI, GeomPoint2d ptF) {
    	CadLine o = new CadLine(oBlkDef, oLayer);
    	o.init(ptI, ptF);
    	return o;
    }
	
	public static CadLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, GeomPoint3d ptI, GeomPoint3d ptF) {
    	CadLine o = new CadLine(oBlkDef, oLayer);
    	o.init(ptI, ptF);
    	return o;
    }

	public static CadLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, double xI, double yI, double zI, double xF, double yF, double zF) {
    	CadLine o = new CadLine(oBlkDef, oLayer);
    	o.init(xI, yI, zI, xF, yF, zF);
    	return o;
    }
	
	public static CadLine create(CadLine otherLine) {
    	CadLine o = new CadLine(otherLine.getBlkDef(), otherLine.getLayer());
    	o.init(otherLine);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadLine duplicate()
	{
		CadLine other = CadLine.create(this);
		return other;
	}
	
	@Override
	public CadLine copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadLine otherLine = CadLine.create(this);
		otherLine.moveTo(ptIMcs, ptFMcs);
		return otherLine;
	}

	@Override
	public CadLine moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
    	MoveData2dVO oI = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());

    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
    	MoveData2dVO oF = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public CadLine scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);

    	ScaleData2dVO oI = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());
		
    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);

    	ScaleData2dVO oF = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

		this.createAllDrawCache();
    	return this;
	}
	
    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptI = GeomUtil.mirror(this.ptI, ptI2dMcs, ptF2dMcs);
		this.ptF = GeomUtil.mirror(this.ptF, ptI2dMcs, ptF2dMcs);

		this.createAllDrawCache();
		return this;
	}
	
	@Override
	public CadLine offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadLine oLine = copyTo(ptIMcs, ptFMcs);
		return oLine;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptI.toPropertyList("Pt.Inicial") );
		lsProperty.addAll( this.ptF.toPropertyList("Pt.Final") );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XI: %s; YI: %s; ZI: %s)-(XF: %s; YF: %s; ZF: %s); ", 
			nf6.format(this.ptI.getX()), 
			nf6.format(this.ptI.getY()), 
			nf6.format(this.ptI.getZ()),
			nf6.format(this.ptF.getX()), 
			nf6.format(this.ptF.getY()), 
			nf6.format(this.ptF.getZ()) );
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

		oLine.addPoint3d(this.ptI);
		oLine.addPoint3d(this.ptF);
		
		DrawCache cache = new DrawCache();
		cache.addItem(oLine);
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

		//ENDPOINT
    	//
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptI) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, this.ptI, this.ptF);
		osnapCache.addOsnapItem( pt3dMid );

		return osnapCache;
	}
	
    /* DRAWING */

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
		ArrayList<ShapeOper2d> lsShapeOper2d = new ArrayList<ShapeOper2d>();
		
		GeomPoint2d ptBase = new GeomPoint2d(ptBase2dMcs);
		double xBase = ptBase.getX();
		double yBase = ptBase.getY();
		
		GeomPoint2d ptI = new GeomPoint2d(this.ptI); 
		double xI = ptI.getX();
		double yI = ptI.getY();
		
		double dXI = xI - xBase;
		double dYI = yI - yBase;

		GeomPoint2d ptF = new GeomPoint2d(this.ptF); 
		double xF = ptF.getX();
		double yF = ptF.getY();

		double dXF = xF - xI;
		double dYF = yF - yI;

		ShapeOper2d oper2d = null;
		
		if(dXI > AppDefs.MATHPREC_MIN) {
			GeomPoint2d ptBaseToI = new GeomPoint2d(dXI, dYI);
			GeomPoint2d ptIToF = new GeomPoint2d(dXF, dYF);
			
			oper2d = new ShapeOper2d(false, ptBaseToI);
			lsShapeOper2d.add(oper2d);

			oper2d = new ShapeOper2d(true, ptIToF);
			lsShapeOper2d.add(oper2d);
		}
		else {
			GeomPoint2d ptIToF = new GeomPoint2d(dXF, dYF);
			
			oper2d = new ShapeOper2d(true, ptIToF);
			lsShapeOper2d.add(oper2d);
		}

		// SHAPE_RESULT
		//
		ShapeResult shpResult = new ShapeResult(
			lsShapeOper2d,
			ptBase,
			ptF );
		return shpResult;
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
		GeomPoint3d ptResult = GeomUtil.midPointOf(this.ptI, this.ptF);
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
		CadLineRecord entRec = new CadLineRecord(this); 
		entDao.insertOrUpdate(schemaName, (BaseEntityRecord) entRec);		
	}
	
	/* TO_CADxxx */
	
	public CadObject toCadObject(CadBlockDef oBlkDef, BaseObjectRecord rec) {
		CadLineRecord oRec = (CadLineRecord)rec; 
		String reference = oRec.getReference();
	
		AppCadMain cad = AppCadMain.getCad();
	
		CadDocumentDef doc = cad.getCurrDocumentDef();
	
		LayerTable oLayTbl = doc.getLayerTable();
	
		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);
	
		CadLine o = CadLine.create(
			oBlkDef,
			oLayer, 
			oRec.getPtIX(), 
			oRec.getPtIY(), 
			oRec.getPtIZ(), 
			oRec.getPtFX(), 
			oRec.getPtFY(), 
			oRec.getPtFZ() );
	    return o;
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
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		//NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingEnUs(3);

		//DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		AppCadMain cad = AppCadMain.getCad();
		
		CadDocumentDef doc = cad.getCurrDocumentDef();
	
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			this.getObjectId(), 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_LINE); 
		
		//HANDLE
		//
		String dxfHandleVal = Integer.toHexString( this.getObjectId() );
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		//
		//CadLayerDef oLayer = this.getLayer();
		CadLayerDef oLayer = doc.getCurrLayerDef();
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);

		//START_POINT
		//
		String dxfStartPointXVal = nf6.format( this.ptI.getX() );
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format( this.ptI.getY() );
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format( this.ptI.getZ() );
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);

		//END_POINT
		//
		String dxfEndPointXVal = nf6.format( this.ptF.getX() );
		DxfEntry oDxfEndPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_X, dxfEndPointXVal);
		oDxfCadEntity.add(oDxfEndPointX);
		
		String dxfEndPointYVal = nf6.format( this.ptF.getY() );
		DxfEntry oDxfEndPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_Y, dxfEndPointYVal);
		oDxfCadEntity.add(oDxfEndPointY);
		
		String dxfEndPointZVal = nf6.format( this.ptF.getZ() );
		DxfEntry oDxfEndPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_Z, dxfEndPointZVal);
		oDxfCadEntity.add(oDxfEndPointZ);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
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
        GeomPoint3d ptI3dMcs = new GeomPoint3d(this.getPtI());
        GeomPoint3d ptF3dMcs = new GeomPoint3d(this.getPtF());

		GeomPoint3d[] arr = GeomUtil.maxMinPointOf(ptI3dMcs, ptF3dMcs);		
		
		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.getPtF());

		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);		
		
		GeomPoint2d ptMin2d = new GeomPoint2d(arr[0]);
		GeomPoint2d ptMax2d = new GeomPoint2d(arr[1]);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}

    public GeomPoint3d getPtI() {
        return this.ptI;
    }

    public GeomPoint3d getPtF() {
        return this.ptF;
    }

}
