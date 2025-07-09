/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadBox3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/02/2025
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

    public CadBox3d(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BOX3D, oLayer);
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
	
	public static CadBox3d create(CadLayerDef oLayer, GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
    	CadBox3d o = new CadBox3d(oLayer);
    	o.init(ptMin, ptMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadLayerDef oLayer, GeomPoint3d ptMin, GeomPoint3d ptMax, double altura) {
		GeomPoint2d ptMin2d = new GeomPoint2d(ptMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(ptMax);
		
		CadBox3d o = new CadBox3d(oLayer);
    	o.init(ptMin2d, ptMax2d, altura);
    	return o;
    }
	
	public static CadBox3d create(CadLayerDef oLayer, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax, double altura) {
    	CadBox3d o = new CadBox3d(oLayer);
    	o.init(xMin, yMin, zMin, xMax, yMax, zMax, altura);
    	return o;
    }
	
	public static CadBox3d create(CadBox3d otherRectangle) {
    	CadBox3d o = new CadBox3d(otherRectangle.getLayer());
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

		Stroke oldltype = GeomUtil.setLtype(g, b);
				
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestMin2dMcs = new GeomPoint2d(this.ptMin);
        GeomPoint2d ptDestMax2dMcs = new GeomPoint2d(this.ptMax);
        
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
		        	CadBox3d other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestMin2dMcs = new GeomPoint2d(other.ptMin);
		        	ptDestMax2dMcs = new GeomPoint2d(other.ptMax);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadBox3d other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestMin2dMcs = new GeomPoint2d(other.ptMin);
		        	ptDestMax2dMcs = new GeomPoint2d(other.ptMax);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadBox3d other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDestMin2dMcs = new GeomPoint2d(other.ptMin);
			        	ptDestMax2dMcs = new GeomPoint2d(other.ptMax);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadBox3d other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDestMin2dMcs = new GeomPoint2d(other.ptMin);
		        	ptDestMax2dMcs = new GeomPoint2d(other.ptMax);
		        }
	        }
        }
        
        DrawUtil.drawRectangleMcs(v, ptDestMin2dMcs, ptDestMax2dMcs, g);
        
        if(bSelected || bHover) {
            GeomPoint2d ptCentroid2d = GeomUtil.midPointOf(ptDestMin2dMcs, ptDestMax2dMcs);
        	DrawUtil.drawPointMcs(v, ptCentroid2d, AppDefs.POINT_SIZE, g);
        }

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
    
	@Override
    public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
    {
    	if(this.isDeleted()) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        MainPanel panel = MainPanel.getPanel();
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
        prep.addBox(v, this, c, ptDestMin3dMcs, ptDestMax3dMcs, axisZ);
    }
    
	/* SELECT */
	
	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if(pt2dMcs == null) return false;
		
		if(this.isDeleted()) return false;
		if(this.isSelected()) return true;

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distMax = boxSz / 2.0;

	    double xMcs = pt2dMcs.getX();
	    double yMcs = pt2dMcs.getY();

	    // LIMITES
	    double xMin = ptMin.getX() - distMax;
	    double yMin = ptMin.getY() - distMax;

	    double xMax = ptMax.getX() + distMax;
	    double yMax = ptMax.getY() + distMax;

	    // FAIXAS
	    double xMin_faixaInf = ptMin.getX() - distMax;
	    double xMin_faixaSup = ptMin.getX() + distMax;
	    //
	    double yMin_faixaInf = ptMin.getY() - distMax;
	    double yMin_faixaSup = ptMin.getY() + distMax;
	    //
	    double xMax_faixaInf = ptMax.getX() - distMax;
	    double xMax_faixaSup = ptMax.getX() + distMax;
	    //
	    double yMax_faixaInf = ptMax.getY() - distMax;
	    double yMax_faixaSup = ptMax.getY() + distMax;
	    
	    //LINE #1: (xMin, yMin)-(xMax, yMin)
		if( ( (xMcs >= xMin) && (xMcs <= xMax) ) &&
			( (yMcs >= yMin_faixaInf) && (yMcs <= yMin_faixaSup) ) ) 
		{
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
	    //LINE #2: (xMin, yMax)-(xMax, yMax)
		else if( ( (xMcs >= xMin) && (xMcs <= xMax) ) &&
				 ( (yMcs >= yMax_faixaInf) && (yMcs <= yMax_faixaSup) ) ) 
		{
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
	    //LINE #3: (xMin, yMin)-(xMin, yMax)
		else if( ( (xMcs >= xMin_faixaInf) && (xMcs <= xMin_faixaSup) ) &&
				 ( (yMcs >= yMin) && (yMcs <= yMax) ) ) 
		{
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
	    //LINE #4: (xMax, yMin)-(xMax, yMax)
		else if( ( (xMcs >= xMax_faixaInf) && (xMcs <= xMax_faixaSup) ) &&
				 ( (yMcs >= yMin) && (yMcs <= yMax) ) ) 
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
    	double xMinMcs = this.ptMin.getX();
    	double yMinMcs = this.ptMin.getY();
    	double zMinMcs = this.ptMin.getZ();
    	//
    	double xMaxMcs = this.ptMax.getX();
    	double yMaxMcs = this.ptMax.getY();
    	double zMaxMcs = this.ptMin.getZ() + this.altura;
    	
    	GeomPoint3d pt3d0 = new GeomPoint3d(xMinMcs, yMinMcs, zMinMcs);
    	GeomPoint3d pt3d1 = new GeomPoint3d(xMaxMcs, yMinMcs, zMinMcs);
    	GeomPoint3d pt3d2 = new GeomPoint3d(xMaxMcs, yMaxMcs, zMaxMcs);
    	GeomPoint3d pt3d3 = new GeomPoint3d(xMinMcs, yMaxMcs, zMaxMcs);

    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add(pt3d0);
    	lsPtEndpoint.add(pt3d1);
    	lsPtEndpoint.add(pt3d2);
    	lsPtEndpoint.add(pt3d3);
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid0 = GeomUtil.midPointOf(pt3d0, pt3d1);
    	GeomPoint3d pt3dMid1 = GeomUtil.midPointOf(pt3d1, pt3d2);
    	GeomPoint3d pt3dMid2 = GeomUtil.midPointOf(pt3d2, pt3d3);
    	GeomPoint3d pt3dMid3 = GeomUtil.midPointOf(pt3d3, pt3d0);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid0);
    	lsPtMiddle.add(pt3dMid1);
    	lsPtMiddle.add(pt3dMid2);
    	lsPtMiddle.add(pt3dMid3);
		
    	//CENTER
    	//
    	GeomPoint3d ptCenter3d = GeomUtil.midPointOf(pt3d0, pt3d2);
    	
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>();    	
    	lsPtCenter.add(ptCenter3d);
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
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
	public GeomDimension2d getEnvelop() {
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
