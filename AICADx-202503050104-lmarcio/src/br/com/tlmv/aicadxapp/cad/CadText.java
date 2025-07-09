/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadText.java
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

public class CadText extends CadEntity 
{
//Private
	String text;
    GeomPoint3d pt;
    double height;
    
//Public

    public CadText(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_TEXT, oLayer);
    }
	
	/* Methodes */
	
	private void init(String text, GeomPoint2d pt, double height) {
		this.init(text, pt.getX(), pt.getY(), 0.0, height);
	}
	
	private void init(String text, GeomPoint3d pt, double height) {
		this.init(text, pt.getX(), pt.getY(), pt.getZ(), height);
	}

	public void init(String text, double x, double y, double z, double height) {
		this.text = text;
    	this.pt = new GeomPoint3d(x, y, z);
    	this.height = height;
    }
	
	private void init(CadText otherText) {
		String tmpText = otherText.text;
		GeomPoint3d ptTmpPt = otherText.pt;
		double tmpHeight = otherText.height;
		
		this.init(tmpText, ptTmpPt.getX(), ptTmpPt.getY(), ptTmpPt.getZ(), tmpHeight);
	}

	/* CREATE */
		
	public static CadText create(CadLayerDef oLayer, String text, GeomPoint2d pt, double height) {
    	CadText o = new CadText(oLayer);
    	o.init(text, pt, height);
    	return o;
    }
	
	public static CadText create(CadLayerDef oLayer, String text, GeomPoint3d pt, double height) {
    	CadText o = new CadText(oLayer);
    	o.init(text, pt, height);
    	return o;
    }

	public static CadText create(CadLayerDef oLayer, String text, double x, double y, double z, double height) {
    	CadText o = new CadText(oLayer);
    	o.init(text, x, y, z, height);
    	return o;
    }
	
	public static CadText create(CadText otherText) {
		CadText o = new CadText(otherText.getLayer());
    	o.init(otherText);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadText duplicate()
	{
		CadText other = CadText.create(this);
		return other;
	}
	
	public CadText copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadText otherText = CadText.create(this);
		otherText.moveTo(ptIMcs, ptFMcs);
		return otherText;
	}

	public CadText moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.pt = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	public CadText scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.pt);

    	ScaleData2dVO o = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs, ptOrig2dMcs);
    	this.pt = new GeomPoint3d(o.getPtDest());
		this.height = this.height * o.getScale();
		return this;
	}
	
	public CadText offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadText oText = copyTo(ptIMcs, ptFMcs);
		return oText;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"(Text: %s; X: %s; Y: %s; Z: %s; Height: %s); ",
			this.text,
			nf6.format(this.pt.getX()), 
			nf6.format(this.pt.getY()), 
			nf6.format(this.pt.getZ()),
			nf6.format(this.height) );
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
        double textHeightMcs = this.height;
        
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
	            textHeightMcs = textHeightMcs * o.getScale();
	        }
        }        
        
    	DrawUtil.drawTextMcs(v, this.text, ptDest2dMcs, textHeightMcs, g);
        
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptDest2dMcs, AppDefs.POINT_SIZE, g);
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
        
        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.pt);
        double textHeightMcs = this.height;
        
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
	            textHeightMcs = textHeightMcs * o.getScale();
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

        DrawUtil.drawTextProj(v, this.text, ptDest3dMcs, textHeightMcs, g);
        
        GeomUtil.setColor(g, oldcol);
    }
    
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
    	if(this.isDeleted()) return false;
    	if(this.isSelected()) return true;
    	
        int textLen = this.text.length();
        
        double textHeightMcs = this.height;        
        double textWidthMcs = 0.75 * textHeightMcs;
        
        double h = textHeightMcs;
        double w = (textLen * textWidthMcs);
        
        double xPtMcs = this.pt.getX();
        double yPtMcs = this.pt.getY();

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;
        
        double xMin = xPtMcs - distMax;
        double yMin = yPtMcs - distMax;
        
        double xMax = xPtMcs + w + distMax;
        double yMax = yPtMcs + h + distMax;
        
        double xPt2dMcs = pt2dMcs.getX();
        double yPt2dMcs = pt2dMcs.getY();
        
        if( ( (xPt2dMcs >= xMin) && (xPt2dMcs <= xMax) ) &&
    		( (yPt2dMcs >= yMin) && (yPt2dMcs <= yMax) ) )
        {
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

    public String getText() {
    	return this.text;
    }
    
    public GeomPoint3d getPt() {
        return this.pt;
    }

    public double getHeight() {
    	return this.height;
    }

}
