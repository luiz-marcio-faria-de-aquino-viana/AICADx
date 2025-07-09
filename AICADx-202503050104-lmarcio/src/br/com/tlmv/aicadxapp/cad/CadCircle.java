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

public class CadCircle extends CadEntity 
{
//Private
    GeomPoint3d ptCenter;
    double radio;
    
//Public

    public CadCircle(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_CIRCLE, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter, double radio) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, radio);
	}
	
	private void init(GeomPoint3d ptCenter, double radio) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radio);
	}

	public void init(double xCenter, double yCenter, double zCenter, double radio) {
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.radio = radio;
    }
	
	private void init(CadCircle otherCircle) {
		GeomPoint3d ptTmpCenter = otherCircle.ptCenter;
		double tmpRadio = otherCircle.radio;
		
		this.init(ptTmpCenter.getX(), ptTmpCenter.getY(), ptTmpCenter.getZ(), tmpRadio);
	}
	
	/* CREATE */
	
	public static CadCircle create(CadLayerDef oLayer, GeomPoint2d ptCenter, double radio) {
    	CadCircle o = new CadCircle(oLayer);
    	o.init(ptCenter, radio);
    	return o;
    }
	
	public static CadCircle create(CadLayerDef oLayer, GeomPoint3d ptCenter, double radio) {
    	CadCircle o = new CadCircle(oLayer);
    	o.init(ptCenter, radio);
    	return o;
    }

	public static CadCircle create(CadLayerDef oLayer, double xCenter, double yCenter, double zCenter, double radio) {
    	CadCircle o = new CadCircle(oLayer);
    	o.init(xCenter, yCenter, zCenter, radio);
    	return o;
    }
	
	public static CadCircle create(CadCircle otherCircle) {
    	CadCircle o = new CadCircle(otherCircle.getLayer());
    	o.init(otherCircle);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadCircle duplicate()
	{
		CadCircle other = CadCircle.create(this);
		return other;
	}	
	
	public CadCircle copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadCircle otherCircle = CadCircle.create(this);
		otherCircle.moveTo(ptIMcs, ptFMcs);
		return otherCircle;
	}

	public CadCircle moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigCenter2dMcs = new GeomPoint2d(this.ptCenter);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigCenter2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	public CadCircle scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs, ptOrig2dMcs);
    	this.ptCenter = new GeomPoint3d(o.getPtDest());
        this.radio = this.radio * o.getScale();
		return this;
	}
	
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
		
		String str = super.toStr();
		str += String.format(
			"(XCenter: %s; YCenter: %s; ZCenter: %s; Radio: %s); ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.radio) );
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
        
    	DrawUtil.drawCircleMcs(v, ptDestCenter2dMcs, radius, g);
        
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
        
    	DrawUtil.drawCircleProj(v, ptDestCenter3dMcs, radius, g);

        GeomUtil.setColor(g, oldcol);
    }
    
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
    	if(this.isDeleted()) return false;
    	if(this.isSelected()) return true;
    	
        GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMin = this.radio - (boxSz / 2.0);
        double distMax = this.radio + (boxSz / 2.0);
        
        double dist = ptCenter2dMcs.distTo(pt2dMcs); 

        if( (dist >= distMin) && (dist <= distMax) ) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return false;
	}

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	GeomVector2d vAxisX = new GeomVector2d(this.radio, 0.0);

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
    	
    	ArrayList<GeomPoint2d> lsPtQuadrant = new ArrayList<GeomPoint2d>();    	
		lsPtQuadrant.add( new GeomPoint2d(vPt0d.getXF(), vPt0d.getYF()) );
		lsPtQuadrant.add( new GeomPoint2d(vPt90d.getXF(), vPt90d.getYF()) );
		lsPtQuadrant.add( new GeomPoint2d(vPt180d.getXF(), vPt180d.getYF()) );
		lsPtQuadrant.add( new GeomPoint2d(vPt270d.getXF(), vPt270d.getYF()) );
    	
    	GeomPoint2d ptResult = null;
    	
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

}
