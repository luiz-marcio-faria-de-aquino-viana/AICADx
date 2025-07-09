/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPoint.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadPoint extends CadEntity 
{
//Private
    GeomPoint3d pt;
	
//Public

    public CadPoint(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_POINT, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d pt) {
		this.init(pt.getX(), pt.getY(), 0.0);
	}
	
	private void init(GeomPoint3d pt) {
		this.init(pt.getX(), pt.getY(), pt.getZ());
	}

	public void init(double x, double y, double z) {
    	this.pt = new GeomPoint3d(x, y, z);
    }
	
	public void init(ICadEntity other) {
		CadPoint oEnt = (CadPoint)other;
		this.init(oEnt.pt);
	}

	/* CREATE */
		
	public static CadPoint create(CadLayerDef oLayer, GeomPoint2d pt) {
    	CadPoint o = new CadPoint(oLayer);
    	o.init(pt);
    	return o;
    }
	
	public static CadPoint create(CadLayerDef oLayer, GeomPoint3d pt) {
    	CadPoint o = new CadPoint(oLayer);
    	o.init(pt);
    	return o;
    }

	public static CadPoint create(CadLayerDef oLayer, double x, double y, double z) {
    	CadPoint o = new CadPoint(oLayer);
    	o.init(x, y, z);
    	return o;
    }
	
	public static CadPoint create(CadPoint other) {
		CadPoint o = new CadPoint(other.getLayer());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadPoint duplicate()
	{
		CadPoint other = CadPoint.create(this);
		return other;
	}

	public CadPoint copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPoint other = CadPoint.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	public CadPoint moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.pt = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	public CadPoint scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);

    	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs, ptOrig2dMcs);
    	this.pt = new GeomPoint3d(o.getPtDest());
		return this;
	}
    
	public CadPoint mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomVector3d vIF = new GeomVector3d(ptIMcs, ptFMcs);
		GeomVector3d nIF = vIF.otherNorm();

		GeomVector3d vIToPt = new GeomVector3d(ptIMcs, this.pt);
		double dist = nIF.dotProd(vIToPt);

		double dist_mirror = -2.0 * dist;
		
		GeomVector3d vMirrorPt = nIF.otherMult(dist_mirror);
		
		this.pt = new GeomPoint3d(vMirrorPt.getXF(), vMirrorPt.getYF(), vMirrorPt.getZF());
		return this;
	}
	
	public CadPoint offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPoint oPoint = copyTo(ptIMcs, ptFMcs);
		return oPoint;
	}
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"[X:%s;Y:%s;Z:%s]; ", 
			nf6.format(this.pt.getX()), 
			nf6.format(this.pt.getY()), 
			nf6.format(this.pt.getZ()) );
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

        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.pt);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);
	        	MoveData2dVO o = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrig2dMcs);
	        	ptDest2dMcs = o.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);
	        	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrig2dMcs, ptOrig2dMcs);
	        	ptDest2dMcs = o.getPtDest();
	        }
        }        
        
    	DrawUtil.drawPointMcs(v, ptDest2dMcs, AppDefs.POINT_SIZE, g);

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, PrepareDrawUtil prep) 
	{
    	if(this.isDeleted()) return;
    	
        //boolean bSelected = this.isSelected();
		//boolean bHover = false;
		//if( !bSelected )
		//	bHover = this.select2d(v, pt2dMcs, false);
		//Color c = super.selectColor(bDragMode, bSelected, bHover);
    	
    	Color c = super.selectColor(bDragMode, false, false);

		//Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.pt);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);
	        	MoveData2dVO o = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrig2dMcs);
	        	ptDest2dMcs = o.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);
	        	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrig2dMcs, ptOrig2dMcs);
	        	ptDest2dMcs = o.getPtDest();
	        }
        }        

        //PT3D
        double xPtDest3dMcs = ptDest2dMcs.getX();
        double yPtDest3dMcs = ptDest2dMcs.getY();
        double zPtDest3dMcs = this.pt.getZ();
        
        GeomPoint3d ptDest3dMcs = new GeomPoint3d(
            xPtDest3dMcs,
            yPtDest3dMcs,
            zPtDest3dMcs);
    	//DrawUtil.drawPointProj(v, ptDest3dMcs, AppDefs.POINT_SIZE, g);

        //GeomUtil.setColor(g, oldcol);
	}
    
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
    	if(this.isDeleted()) return false;
    	if(this.isSelected()) return true;
    	
        GeomPoint2d ptPoint2dMcs = new GeomPoint2d(this.pt);
        
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;
        
        double dist = ptPoint2dMcs.distTo(pt2dMcs); 
        if(dist <= distMax) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return this.isSelected();
	}
    
	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	GeomPoint2d pt2d = new GeomPoint2d(this.pt);
    	
    	ArrayList<GeomPoint2d> lsPtNodepoint = new ArrayList<GeomPoint2d>();    	
    	lsPtNodepoint.add(pt2d);
		
    	GeomPoint2d ptResult = null;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.pt);
		return ptResult;
	}

	/* Getters/Setters */

    public GeomPoint3d getPt() {
        return this.pt;
    }

}
