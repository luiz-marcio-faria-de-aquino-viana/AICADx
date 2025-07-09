/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadTorus3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 11/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
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

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCone3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadTorus3dRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadTorus3d extends CadEntity 
{
//Private
    private GeomPoint3d ptCenter;
    private double radius;  
    //
    private double torusRadius;    
    
//Public

    public CadTorus3d(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_TORUS3D, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter, double radius, double torusRadius) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, radius, torusRadius);
	}
	
	private void init(GeomPoint3d ptCenter, double radius, double torusRadius) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radius, torusRadius);
	}

	public void init(double xCenter, double yCenter, double zCenter, double radius, double torusRadius) {
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.radius = radius;
		this.torusRadius = torusRadius;
    }
	
	private void init(CadTorus3d other) {
		GeomPoint3d ptCenter = new GeomPoint3d(other.ptCenter);
		double radius = other.radius;
		double torusRadius = other.torusRadius;
		
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radius, torusRadius);
	}
	
	/* CREATE */
	
	public static CadTorus3d create(CadLayerDef oLayer, GeomPoint2d ptCenter, double radius, double torusRadius) {
    	CadTorus3d o = new CadTorus3d(oLayer);
    	o.init(ptCenter, radius, torusRadius);
    	return o;
    }
	
	public static CadTorus3d create(CadLayerDef oLayer, GeomPoint3d ptCenter, double radius, double torusRadius) {
		CadTorus3d o = new CadTorus3d(oLayer);
    	o.init(ptCenter, radius, torusRadius);
    	return o;
    }
	
	public static CadTorus3d create(CadLayerDef oLayer, double xCenter, double yCenter, double zCenter, double radius, double torusRadius) {
    	CadTorus3d o = new CadTorus3d(oLayer);
    	o.init(xCenter, yCenter, zCenter, radius, torusRadius);
    	return o;
    }
	
	public static CadTorus3d create(CadTorus3d other) {
    	CadTorus3d o = new CadTorus3d(other.getLayer());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadTorus3d duplicate()
	{
		CadTorus3d other = CadTorus3d.create(this);
		return other;
	}
	
	@Override
	public CadTorus3d copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadTorus3d other = CadTorus3d.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadTorus3d moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);

    	MoveData2dVO oNewCenter = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptCenter2d);
    	this.ptCenter = new GeomPoint3d(oNewCenter.getPtDest());
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptCenter = GeomUtil.mirror(this.ptCenter, ptI2dMcs, ptF2dMcs);
		return this;
	}

	@Override
	public CadTorus3d scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO oNewCenter = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptCenter2d);
    	this.ptCenter = new GeomPoint3d(oNewCenter.getPtDest());
    	this.radius = this.radius * oNewCenter.getScale();
    	this.torusRadius = this.torusRadius * oNewCenter.getScale();
    	return this;
	}
	
	@Override
	public CadTorus3d offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadTorus3d oOffset = copyTo(ptIMcs, ptFMcs);
		return oOffset;
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
		lsProperty.add( new ItemDataVO("Torus Radius", nf3.format(this.torusRadius)) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"XCenter:%s;YCenter:%s;ZCenter:%s;Radius:%s;TorusRadius:%s; ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.radius),
			nf6.format(this.torusRadius) );
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

        GeomPoint2d ptDestCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double radius = this.radius;
        double torusRadius = this.torusRadius;
        
        double radiusInf = radius - torusRadius;
        double radiusSup = radius + torusRadius;
        
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
		        	CadTorus3d other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadTorus3d other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadTorus3d other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadTorus3d other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        }
	        }
        }
        
        DrawUtil.drawCircleMcs(v, ptDestCenter2dMcs, radius, g);

        DrawUtil.drawCircleMcs(v, ptDestCenter2dMcs, radiusInf, g);

        DrawUtil.drawCircleMcs(v, ptDestCenter2dMcs, radiusSup, g);

        DrawUtil.drawRadialLinesMcs(v, ptDestCenter2dMcs, radiusInf, radiusSup, g);
        
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptDestCenter2dMcs, AppDefs.POINT_SIZE, g);
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

        GeomPoint3d ptDestCenter3dMcs = new GeomPoint3d(
        	this.ptCenter.getX(),
        	this.ptCenter.getY(),
        	this.ptCenter.getZ() );

        GeomVector3d axisZ = GeomUtil.axisZ3d();
        prep.addTorus(v, this, c, ptDestCenter3dMcs, this.radius, this.torusRadius, axisZ);
    }
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if(pt2dMcs == null) return false;
		
    	if(this.isDeleted()) return false;
    	if(this.isSelected()) return true;
    	
        GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMin = this.radius - (boxSz / 2.0);
        double distMax = this.radius + (boxSz / 2.0);
        
        double dist = ptCenter2dMcs.distTo(pt2dMcs); 

        if( (dist >= distMin) && (dist <= distMax) ) {
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

    	double zp = this.ptCenter.getZ();
    	
    	GeomPoint2d ptCenter = new GeomPoint2d(this.ptCenter);    	
    	GeomVector2d vAxisX = new GeomVector2d(this.radius, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptCenter, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add(new GeomPoint3d(this.ptCenter));

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vDir.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vDir.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vDir.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vDir.otherRotateToDegrees(270.0);
    	
    	ArrayList<GeomPoint3d> lsPtQuadrant = new ArrayList<GeomPoint3d>();    	
		lsPtQuadrant.add( new GeomPoint3d(vPt0d.getXF(), vPt0d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt90d.getXF(), vPt90d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt180d.getXF(), vPt180d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt270d.getXF(), vPt270d.getYF(), zp) );
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_QUADRANT, pt2dMcs, lsPtQuadrant, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
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
		CadTorus3dRecord entRec = new CadTorus3dRecord(this); 
		entDao.insertOrUpdate(schemaName, (BaseEntityRecord) entRec);		
	}
	
    /* Getters/Setters */

	@Override
	public GeomDimension2d getEnvelop() {
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

	public GeomPoint3d getPtCenter() {
        return this.ptCenter;
    }

    public double getRadius() {
        return this.radius;
    }

	public double getTorusRadius() {
		return torusRadius;
	}

}
