/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPipe.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
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
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLineRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPipeRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPointRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadPipe extends CadEntity 
{
//Private
    private GeomPoint3d ptI;
    private GeomPoint3d ptF;    
    private int numeroPipe;
    private int fromObjId;
    private int toObjId;
    private double diameter;
    	
//Public

    public CadPipe(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BIMPIPE, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptI, GeomPoint2d ptF, double diameter) {
		this.init(ptI.getX(), ptI.getY(), 0.0, ptF.getX(), ptF.getY(), 0.0, AppDefs.NULL_INT, AppDefs.NULL_INT, diameter);
	}
	
	private void init(GeomPoint3d ptI, GeomPoint3d ptF, double diameter) {
		this.init(ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ(), AppDefs.NULL_INT, AppDefs.NULL_INT, diameter);
	}
	
	private void init(GeomPoint2d ptI, GeomPoint2d ptF, int fromObjId, int toObjId, double diameter) {
		this.init(ptI.getX(), ptI.getY(), 0.0, ptF.getX(), ptF.getY(), 0.0, fromObjId, toObjId, diameter);
	}
	
	private void init(GeomPoint3d ptI, GeomPoint3d ptF, int fromObjId, int toObjId, double diameter) {
		this.init(ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ(), fromObjId, toObjId, diameter);
	}

	public void init(double xI, double yI, double zI, double xF, double yF, double zF, int fromObjId, int toObjId, double diameter) {
    	this.ptI = new GeomPoint3d(xI, yI, zI);
    	this.ptF = new GeomPoint3d(xF, yF, zF);
    	this.numeroPipe = this.getObjectId();
    	this.fromObjId = fromObjId;
    	this.toObjId = toObjId;
    	this.diameter = diameter;
    }
	
	private void init(CadPipe other) {
		GeomPoint3d ptTmpPtI = other.ptI;
		GeomPoint3d ptTmpPtF = other.ptF;
    	int tmpFromObjId = other.fromObjId;
    	int tmpToObjId = other.toObjId;
		double tmpDiameter = other.diameter;
		
		this.init(ptTmpPtI.getX(), ptTmpPtI.getY(), ptTmpPtI.getZ(), ptTmpPtF.getX(), ptTmpPtF.getY(), ptTmpPtF.getZ(), tmpFromObjId, tmpToObjId, tmpDiameter);
	}
	
	/* CREATE */
	
	public static CadPipe create(CadLayerDef oLayer, GeomPoint2d ptI, GeomPoint2d ptF, double diameter) {
    	CadPipe o = new CadPipe(oLayer);
    	o.init(ptI, ptF, diameter);
    	return o;
    }
	
	public static CadPipe create(CadLayerDef oLayer, GeomPoint3d ptI, GeomPoint3d ptF, double diameter) {
    	CadPipe o = new CadPipe(oLayer);
    	o.init(ptI, ptF, diameter);
    	return o;
    }
	
	public static CadPipe create(CadLayerDef oLayer, GeomPoint2d ptI, GeomPoint2d ptF, int fromObjId, int toObjId, double diameter) {
    	CadPipe o = new CadPipe(oLayer);
    	o.init(ptI, ptF, fromObjId, toObjId, diameter);
    	return o;
    }
	
	public static CadPipe create(CadLayerDef oLayer, GeomPoint3d ptI, GeomPoint3d ptF, int fromObjId, int toObjId, double diameter) {
    	CadPipe o = new CadPipe(oLayer);
    	o.init(ptI, ptF, fromObjId, toObjId, diameter);
    	return o;
    }

	public static CadPipe create(CadLayerDef oLayer, double xI, double yI, double zI, double xF, double yF, double zF, int fromObjId, int toObjId, double diameter) {
    	CadPipe o = new CadPipe(oLayer);
    	o.init(xI, yI, zI, xF, yF, zF, fromObjId, toObjId, diameter);
    	return o;
    }
	
	public static CadPipe create(CadPipe other) {
    	CadPipe o = new CadPipe(other.getLayer());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPipe duplicate()
	{
		CadPipe other = CadPipe.create(this);
		return other;
	}
	
	@Override
	public CadPipe copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPipe otherLine = CadPipe.create(this);
		otherLine.moveTo(ptIMcs, ptFMcs);
		return otherLine;
	}

	@Override
	public CadPipe moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
    	MoveData2dVO oI = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());

    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
    	MoveData2dVO oF = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

    	return this;
	}
	
	@Override
	public CadPipe scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);

    	ScaleData2dVO oI = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());
		
    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);

    	ScaleData2dVO oF = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

    	return this;
	}
	
    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptI = GeomUtil.mirror(this.ptI, ptI2dMcs, ptF2dMcs);
		this.ptF = GeomUtil.mirror(this.ptF, ptI2dMcs, ptF2dMcs);

		return this;
	}
	
	@Override
	public CadPipe offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPipe oLine = copyTo(ptIMcs, ptFMcs);
		return oLine;
	}
    
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		double diam = this.diameter * 1000.0;
		
		lsProperty.addAll( this.ptI.toPropertyList("Pt.Inicial") );
		lsProperty.addAll( this.ptF.toPropertyList("Pt.Final") );
		//
		lsProperty.add( new ItemDataVO("Tubulacao", Integer.toString(this.numeroPipe)) );
		lsProperty.add( new ItemDataVO("Diametro", nf3.format(diam)) );
		//
		if(this.fromObjId != AppDefs.NULL_INT)
			lsProperty.add( new ItemDataVO("CI Anterior", Integer.toString(this.fromObjId)) );
		if(this.toObjId != AppDefs.NULL_INT)
			lsProperty.add( new ItemDataVO("CI Proxima", Integer.toString(this.toObjId)) );
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

    /* DRAWING */
	
	public void redraw2d_planView(ICadViewBase v, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, Graphics g)
	{
		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
		
		double hDiam = this.diameter / 2.0;

		GeomPoint2d ptI2dMcs_left = ptI2dMcs.otherMoveTo(nIF2d, hDiam);
		GeomPoint2d ptI2dMcs_right = ptI2dMcs.otherMoveTo(nIF2d, - hDiam);

		GeomPoint2d ptF2dMcs_left = ptF2dMcs.otherMoveTo(nIF2d, hDiam);
		GeomPoint2d ptF2dMcs_right = ptF2dMcs.otherMoveTo(nIF2d, - hDiam);
		
        DrawUtil.drawLineMcs(v, ptI2dMcs_left, ptF2dMcs_left, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_left, ptF2dMcs_right, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_right, ptI2dMcs_right, g);
        DrawUtil.drawLineMcs(v, ptI2dMcs_right, ptI2dMcs_left, g);
	}
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if(this.isDeleted()) return;
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);
		
		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);

		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.ptF);
        
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
		        	CadPipe other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPipe other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadPipe other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			            ptDestI2dMcs = new GeomPoint2d(other.ptI);
			            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPipe other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }
	        }
        }
        
        redraw2d_planView(v, ptDestI2dMcs, ptDestF2dMcs, g);

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
	
	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep)
	{
    	if(this.isDeleted()) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

    	GeomVector3d vDir3d = new GeomVector3d(this.ptI, this.ptF);
    	double d = vDir3d.mod();
    	
        double radius = this.diameter / 2.0;
        
        prep.addCilinder(v, this, c, this.ptI, vDir3d, d, radius);
	}
	        
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if(pt2dMcs == null) return false;
		
    	if(this.isDeleted()) return false;
    	if(this.isSelected()) return true;
    	
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.ptF);
        
        GeomVector2d vIToF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs);
        GeomVector2d uIToF2dMcs = vIToF2dMcs.otherUnit();
        
        GeomVector2d vIToPt2dMcs = new GeomVector2d(ptI2dMcs, pt2dMcs);

    	GeomPoint2d[] arrMaxMinPtMcs = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);
    	GeomPoint2d ptMinMcs = arrMaxMinPtMcs[0];
    	GeomPoint2d ptMaxMcs = arrMaxMinPtMcs[1];
    	
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;

    	double xMinMcs = ptMinMcs.getX() - distMax;
    	double yMinMcs = ptMinMcs.getY() - distMax;
    	
    	double xMaxMcs = ptMaxMcs.getX() + distMax;
    	double yMaxMcs = ptMaxMcs.getY() + distMax;

    	double xMcs = pt2dMcs.getX();
    	double yMcs = pt2dMcs.getY();

    	if( ( (xMcs >= xMinMcs) && (xMcs <= xMaxMcs) ) & 
    		( (yMcs >= yMinMcs) && (yMcs <= yMaxMcs) ) ) 
    	{
            GeomVector3d vZ = uIToF2dMcs.vectProd(vIToPt2dMcs);
            double dZ = Math.abs(vZ.getZOrig());
            if(dZ <= distMax) {
            	if( bSelectEntity ) {
            		this.setSelected(true);
            	}
            	return true;
            }
    	}
        return false;
	}

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ArrayList<ShapeOper2d> toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
	{
		return null;
	}

	/* OSNAP */

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(this.ptI) );
    	lsPtEndpoint.add( new GeomPoint3d(this.ptF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(this.ptI, this.ptF);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
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
		//TODO:
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		BaseDao dao = db.getDao();
		CadEntityBaseDao entDao = dao.create(this.getObjType()); 
		CadPipeRecord entRec = new CadPipeRecord(this); 
		entDao.insertOrUpdate(schemaName, (BaseEntityRecord) entRec);
	}
	
	/* TO_CADxxx */
	
	public CadObject toCadObject(BaseObjectRecord rec) {
		CadPipeRecord oRec = (CadPipeRecord)rec; 
		String reference = oRec.getReference();
	
		AppCadMain cad = AppCadMain.getCad();
	
		CadDocumentDef doc = cad.getCurrDocumentDef();
	
		LayerTable oLayTbl = doc.getLayerTable();
	
		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);
		
		CadPipe o = CadPipe.create(
			oLayer, 
			oRec.getPtIX(), 
			oRec.getPtIY(), 
			oRec.getPtIZ(), 
			oRec.getPtFX(), 
			oRec.getPtFY(), 
			oRec.getPtFZ(),
			oRec.getFromObjectId(),
			oRec.getToObjectId(),
			oRec.getDiameter() );
	    return o;
	}

	/* Getters/Setters */
	
	@Override
	public GeomDimension2d getEnvelop() {
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

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public int getNumeroPipe() {
		return numeroPipe;
	}

	public int getFromObjId() {
		return fromObjId;
	}

	public int getToObjId() {
		return toObjId;
	}

}
