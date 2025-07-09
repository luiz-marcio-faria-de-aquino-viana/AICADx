/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadLine.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/01/2025
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
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import javafx.scene.layout.BorderStrokeStyle;

public class CadArc extends CadEntity 
{
//Private
    GeomPoint3d ptCenter;
    double radio;
    double startAngle;
    double endAngle;
    
//Public

    public CadArc(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_ARC, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter, double radio, double startAngle, double endAngle) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, radio, startAngle, endAngle);
	}
	
	private void init(GeomPoint3d ptCenter, double radio, double startAngle, double endAngle) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radio, startAngle, endAngle);
	}

	public void init(double xCenter, double yCenter, double zCenter, double radio, double startAngle, double endAngle) {
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.radio = radio;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
    }

	public void init(CadArc otherArc) {
		GeomPoint3d ptTmpCenter = otherArc.ptCenter;
		double tmpRadio = otherArc.radio;
		double tmpStartAngle = otherArc.startAngle;
		double tmpEndAngle = otherArc.endAngle;
		
		this.init(ptTmpCenter.getX(), ptTmpCenter.getY(), ptTmpCenter.getZ(), tmpRadio, tmpStartAngle, tmpEndAngle);
    }
	
	/* CREATE */
	
	public static CadArc create(CadLayerDef oLayer, GeomPoint2d ptCenter, double radio, double startAngle, double endAngle) {
    	CadArc o = new CadArc(oLayer);
    	o.init(ptCenter, radio, startAngle, endAngle);
    	return o;
    }
	
	public static CadArc create(CadLayerDef oLayer, GeomPoint3d ptCenter, double radio, double startAngle, double endAngle) {
    	CadArc o = new CadArc(oLayer);
    	o.init(ptCenter, radio, startAngle, endAngle);
    	return o;
    }

	public static CadArc create(CadLayerDef oLayer, double xCenter, double yCenter, double zCenter, double radio, double startAngle, double endAngle) {
    	CadArc o = new CadArc(oLayer);
    	o.init(xCenter, yCenter, zCenter, radio, startAngle, endAngle);
    	return o;
    }
    
	public static CadArc create(CadArc otherArc) {
    	CadArc o = new CadArc(otherArc.getLayer());
    	o.init(otherArc);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadArc duplicate()
	{
		CadArc other = CadArc.create(this);
		return other;
	}	
	
	public CadArc copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadArc otherArc = CadArc.create(this);
		otherArc.moveTo(ptIMcs, ptFMcs);
		return otherArc;
	}

	public CadArc moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigCenter2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	public CadArc scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs, ptOrig2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());
        this.radio = this.radio * o.getScale();
		return this;
	}
	
	public CadArc offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadArc oArc = copyTo(ptIMcs, ptFMcs);
		return oArc;
	}

	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"(XCenter: %s; YCenter: %s; ZCenter: %s; Radio: %s; StartAngle: %s; EndAngle: %s); ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.radio),
			nf6.format(this.startAngle),
			nf6.format(this.endAngle) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWING */

    public void redraw2d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	if(this.isDeleted()) return;
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double radius = this.radio;

        double startAngleRad = GeomUtil.convertDegreesToRad(this.startAngle);
        double endAngleRad = GeomUtil.convertDegreesToRad(this.endAngle);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);
	        	MoveData2dVO o = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigCenter2dMcs);
	        	ptDestCenter2dMcs = o.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);
	        	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigCenter2dMcs, ptOrigCenter2dMcs);
	        	ptDestCenter2dMcs = o.getPtDest();
	            radius = this.radio * o.getScale();
	        }
        }        
        
    	DrawUtil.drawArcMcs(v, ptDestCenter2dMcs, radius, startAngleRad, endAngleRad, g);
        
        if(bSelected || bHover) {
            GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);
        	DrawUtil.drawPointMcs(v, ptCenter2d, AppDefs.POINT_SIZE, g);
        }

        GeomUtil.setColor(g, oldcol);

		GeomUtil.setLtype(g, oldltype);		
    }

    public void redraw3d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	if(this.isDeleted()) return;
    	
        //boolean bSelected = this.isSelected();
		//boolean bHover = false;
		//if( !bSelected )
		//	bHover = this.select2d(v, pt2dMcs, false);
		//Color c = super.selectColor(bDragMode, bSelected, bHover);
    	
    	Color c = super.selectColor(bDragMode, false, false);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double radius = this.radio;

        double startAngleRad = GeomUtil.convertDegreesToRad(this.startAngle);
        double endAngleRad = GeomUtil.convertDegreesToRad(this.endAngle);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);
	        	MoveData2dVO o = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigCenter2dMcs);
	        	ptDestCenter2dMcs = o.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);
	        	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigCenter2dMcs, ptOrigCenter2dMcs);
	        	ptDestCenter2dMcs = o.getPtDest();
	            radius = this.radio * o.getScale();
	        }
        }        
                
        //PTCENTER3D
        double xPtDestCenter3dMcs = ptDestCenter2dMcs.getX();
        double yPtDestCenter3dMcs = ptDestCenter2dMcs.getY();
        double zPtDestCenter3dMcs = this.ptCenter.getZ();
        
        GeomPoint3d ptDestCenter3dMcs = new GeomPoint3d(
    		xPtDestCenter3dMcs,
    		yPtDestCenter3dMcs,
    		zPtDestCenter3dMcs);
        
    	DrawUtil.drawArcProj(v, ptDestCenter3dMcs, radius, startAngleRad, endAngleRad, g);

        GeomUtil.setColor(g, oldcol);
    }
    
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
    	if( !this.isVisible() ) return false;
    	
    	if( this.isSelected() ) return true;
    	
    	GeomPlan2d planMcs = view2d.getPlanMcs2d();
    	
        GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMin = this.radio - (boxSz / 2.0);
        double distMax = this.radio + (boxSz / 2.0);
        
        double dist = ptCenter2dMcs.distTo(pt2dMcs); 

        if( (dist >= distMin) && (dist <= distMax) )
        {
        	GeomVector2d axisX = planMcs.getAxisX();
        	
        	GeomVector2d v = new GeomVector2d(ptCenter2dMcs, pt2dMcs);
        	double angRad = axisX.angleTo(v);

        	double angDegrees = GeomUtil.convertRadToDegrees(angRad);
        	
            if( (angDegrees >= this.startAngle) && (angDegrees <= this.endAngle) ) {
            	if( bSelectEntity ) {
            		this.setSelected(true);
            	}
            	return true;
            }
        }
        return false;
	}

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	GeomVector2d vAxisX = new GeomVector2d(this.radio, 0.0);

    	//ENDPOINT
    	//
    	GeomVector2d vStartPoint = vAxisX.otherRotateToDegrees(this.startAngle);
    	GeomVector2d vEndPoint = vAxisX.otherRotateToDegrees(this.endAngle);
    	
    	ArrayList<GeomPoint2d> lsPtEndpoint = new ArrayList<GeomPoint2d>();    	
    	lsPtEndpoint.add( new GeomPoint2d(vStartPoint.getXF(), vStartPoint.getYF()) );
    	lsPtEndpoint.add( new GeomPoint2d(vEndPoint.getXF(), vEndPoint.getYF()) );
		
    	//MIDDLE
    	//
    	double middleAngle = startAngle + (endAngle - startAngle) / 2.0;
    	
    	GeomVector2d vMiddlePoint = vAxisX.otherRotateToDegrees(middleAngle);
    	
    	ArrayList<GeomPoint2d> lsPtMiddle = new ArrayList<GeomPoint2d>();    	
    	lsPtMiddle.add( new GeomPoint2d(vMiddlePoint.getXF(), vMiddlePoint.getYF()) );

    	//CENTER
    	//
    	ArrayList<GeomPoint2d> lsPtCenter = new ArrayList<GeomPoint2d>(); 
    	lsPtCenter.add(new GeomPoint2d(this.ptCenter));

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vAxisX.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vAxisX.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vAxisX.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vAxisX.otherRotateToDegrees(270.0);
    	
    	//QUADRANT_RANGES
    	double d0_I = 0.0;
    	double d0_F = 0.0 + AppDefs.MATHPREC_MIN;
    	
    	double d90_I = 90.0 - AppDefs.MATHPREC_MIN;
    	double d90_F = 90.0 + AppDefs.MATHPREC_MIN;
    	
    	double d180_I = 180.0 - AppDefs.MATHPREC_MIN;
    	double d180_F = 180.0 + AppDefs.MATHPREC_MIN;
    	
    	double d270_I = 270.0 - AppDefs.MATHPREC_MIN;
    	double d270_F = 270.0 + AppDefs.MATHPREC_MIN;
    	
    	ArrayList<GeomPoint2d> lsPtQuadrant = new ArrayList<GeomPoint2d>();    	
    	if( (this.startAngle > d0_I) && (this.startAngle <= d0_F) )
    		lsPtQuadrant.add( new GeomPoint2d(vPt0d.getXF(), vPt0d.getYF()) );
    	if( (this.startAngle <= d90_F) && (this.endAngle > d90_I) )
    		lsPtQuadrant.add( new GeomPoint2d(vPt90d.getXF(), vPt90d.getYF()) );
    	if( (this.startAngle <= d180_F) && (this.endAngle > d180_I) )
    		lsPtQuadrant.add( new GeomPoint2d(vPt180d.getXF(), vPt180d.getYF()) );
    	if( (this.startAngle <= d270_F) && (this.endAngle > d270_I) )
    		lsPtQuadrant.add( new GeomPoint2d(vPt270d.getXF(), vPt270d.getYF()) );
    	
    	GeomPoint2d ptResult = null;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_QUADRANT, pt2dMcs, lsPtQuadrant, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCenter);
		return ptResult;
	}
	
    /* Getters/Setters */

    public GeomPoint3d getPtCenter() {
        return this.ptCenter;
    }

    public double getRadio() {
        return this.radio;
    }

    public double getStartAngle() {
        return this.startAngle;
    }

    public double getEndAngle() {
        return this.endAngle;
    }

}
