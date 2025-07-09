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

public class CadLine extends CadEntity 
{
//Private
    GeomPoint3d ptI;
    GeomPoint3d ptF;
	
//Public

    public CadLine(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_LINE, oLayer);
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
    }
	
	private void init(CadLine otherLine) {
		GeomPoint3d ptTmpPtI = otherLine.ptI;
		GeomPoint3d ptTmpPtF = otherLine.ptF;
		
		this.init(ptTmpPtI.getX(), ptTmpPtI.getY(), ptTmpPtI.getZ(), ptTmpPtF.getX(), ptTmpPtF.getY(), ptTmpPtF.getZ());
	}
	
	/* CREATE */
	
	public static CadLine create(CadLayerDef oLayer, GeomPoint2d ptI, GeomPoint2d ptF) {
    	CadLine o = new CadLine(oLayer);
    	o.init(ptI, ptF);
    	return o;
    }
	
	public static CadLine create(CadLayerDef oLayer, GeomPoint3d ptI, GeomPoint3d ptF) {
    	CadLine o = new CadLine(oLayer);
    	o.init(ptI, ptF);
    	return o;
    }

	public static CadLine create(CadLayerDef oLayer, double xI, double yI, double zI, double xF, double yF, double zF) {
    	CadLine o = new CadLine(oLayer);
    	o.init(xI, yI, zI, xF, yF, zF);
    	return o;
    }
	
	public static CadLine create(CadLine otherLine) {
    	CadLine o = new CadLine(otherLine.getLayer());
    	o.init(otherLine);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadLine duplicate()
	{
		CadLine other = CadLine.create(this);
		return other;
	}
	
	public CadLine copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadLine otherLine = CadLine.create(this);
		otherLine.moveTo(ptIMcs, ptFMcs);
		return otherLine;
	}

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

    	return this;
	}
	
	public CadLine scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);

    	ScaleData2dVO oI = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrigI2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());
		
    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);

    	ScaleData2dVO oF = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrigI2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

    	return this;
	}
	
	public CadLine mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return this;
	}
	
	public CadLine offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadLine oLine = copyTo(ptIMcs, ptFMcs);
		return oLine;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"(XI: %s; YI: %s; ZI: %s)-(XF: %s; YF: %s; ZF: %s); ", 
			nf6.format(this.ptI.getX()), 
			nf6.format(this.ptI.getY()), 
			nf6.format(this.ptI.getZ()),
			nf6.format(this.ptF.getX()), 
			nf6.format(this.ptF.getY()), 
			nf6.format(this.ptF.getZ()) );
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

        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.ptF);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
	        	MoveData2dVO oI = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs);
	        	ptDestI2dMcs = oI.getPtDest();
	        	
	        	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
	        	MoveData2dVO oF = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigF2dMcs);
	        	ptDestF2dMcs = oF.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
	        	ScaleData2dVO oI = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs, ptOrigI2dMcs);
	        	ptDestI2dMcs = oI.getPtDest();
	        	
	        	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
	        	ScaleData2dVO oF = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs, ptOrigF2dMcs);
	        	ptDestF2dMcs = oF.getPtDest();
	        }
        }
        
        DrawUtil.drawLineMcs(v, ptDestI2dMcs, ptDestF2dMcs, g);

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

        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.ptF);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
	        	MoveData2dVO oI = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs);
	        	ptDestI2dMcs = oI.getPtDest();
	        	
	        	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
	        	MoveData2dVO oF = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigF2dMcs);
	        	ptDestF2dMcs = oF.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
	        	ScaleData2dVO oI = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs, ptOrigI2dMcs);
	        	ptDestI2dMcs = oI.getPtDest();
	        	
	        	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
	        	ScaleData2dVO oF = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs, ptOrigF2dMcs);
	        	ptDestF2dMcs = oF.getPtDest();
	        }
        }
        
        //PTI3D
        double xPtDestI3dMcs = ptDestI2dMcs.getX();
        double yPtDestI3dMcs = ptDestI2dMcs.getY();
        double zPtDestI3dMcs = this.ptI.getZ();
        
        GeomPoint3d ptDestI3dMcs = new GeomPoint3d(
            xPtDestI3dMcs,
            yPtDestI3dMcs,
            zPtDestI3dMcs);
        
        //PTF3D
        double xPtDestF3dMcs = ptDestF2dMcs.getX();
        double yPtDestF3dMcs = ptDestF2dMcs.getY();
        double zPtDestF3dMcs = this.ptF.getZ();
        
        GeomPoint3d ptDestF3dMcs = new GeomPoint3d(
            xPtDestF3dMcs,
            yPtDestF3dMcs,
            zPtDestF3dMcs);
        
        DrawUtil.drawLineProj(v, ptDestI3dMcs, ptDestF3dMcs, g);

        GeomUtil.setColor(g, oldcol);
    }
	        
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
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

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	GeomPoint2d pt2dI = new GeomPoint2d(this.ptI);
    	GeomPoint2d pt2dF = new GeomPoint2d(this.ptF);

    	ArrayList<GeomPoint2d> lsPtEndpoint = new ArrayList<GeomPoint2d>();    	
    	lsPtEndpoint.add( new GeomPoint2d(pt2dI) );
    	lsPtEndpoint.add( new GeomPoint2d(pt2dF) );
		
    	//MIDDLE
    	//
    	GeomPoint2d pt2dMid = GeomUtil.midPointOf(pt2dI, pt2dF);
    	
    	ArrayList<GeomPoint2d> lsPtMiddle = new ArrayList<GeomPoint2d>();    	
    	lsPtMiddle.add(pt2dMid);
    	
    	GeomPoint2d ptResult = null;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = GeomUtil.midPointOf(this.ptI, this.ptF);
		return ptResult;
	}

	/* Getters/Setters */

    public GeomPoint3d getPtI() {
        return this.ptI;
    }

    public GeomPoint3d getPtF() {
        return this.ptF;
    }

}
