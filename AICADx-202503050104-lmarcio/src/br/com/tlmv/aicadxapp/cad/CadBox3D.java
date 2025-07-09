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
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadBox3D extends CadEntity 
{
//Private
    private GeomPoint3d ptMin;
    private GeomPoint3d ptMax;
    private double altura;
    
//Public

    public CadBox3D(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BOX3D, oLayer);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
		this.init(ptMin.getX(), ptMin.getY(), 0.0, ptMax.getX(), ptMax.getY(), altura);
	}
	
	private void init(GeomPoint3d ptMin, GeomPoint3d ptMax) {
		this.init(ptMin.getX(), ptMin.getY(), ptMin.getZ(), ptMax.getX(), ptMax.getY(), ptMax.getZ());
	}

	public void init(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		this.ptMin = new GeomPoint3d(xMin, yMin, zMin);
		this.ptMax = new GeomPoint3d(xMax, yMax, zMax);
		//
		this.altura = zMax - zMin;
    }
	
	private void init(CadBox3D otherRectangle) {
		GeomPoint3d ptTmpPtMin = otherRectangle.ptMin;
		GeomPoint3d ptTmpPtMax = otherRectangle.ptMax;
		
		this.init(ptTmpPtMin.getX(), ptTmpPtMin.getY(), ptTmpPtMin.getZ(), ptTmpPtMax.getX(), ptTmpPtMax.getY(), ptTmpPtMax.getZ());
	}
	
	/* CREATE */
	
	public static CadBox3D create(CadLayerDef oLayer, GeomPoint2d ptMin, GeomPoint2d ptMax, double altura) {
    	CadBox3D o = new CadBox3D(oLayer);
    	o.init(ptMin, ptMax, altura);
    	return o;
    }
	
	public static CadBox3D create(CadLayerDef oLayer, GeomPoint3d ptMin, GeomPoint3d ptMax, double altura) {
		GeomPoint2d ptMin2d = new GeomPoint2d(ptMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(ptMax);
		
		CadBox3D o = new CadBox3D(oLayer);
    	o.init(ptMin2d, ptMax2d, altura);
    	return o;
    }
	
	public static CadBox3D create(CadLayerDef oLayer, GeomPoint3d ptMin, GeomPoint3d ptMax) {
    	CadBox3D o = new CadBox3D(oLayer);
    	o.init(ptMin, ptMax);
    	return o;
    }

	public static CadBox3D create(CadLayerDef oLayer, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
    	CadBox3D o = new CadBox3D(oLayer);
    	o.init(xMin, yMin, zMin, xMax, yMax, zMax);
    	return o;
    }
	
	public static CadBox3D create(CadBox3D otherRectangle) {
    	CadBox3D o = new CadBox3D(otherRectangle.getLayer());
    	o.init(otherRectangle);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadBox3D duplicate()
	{
		CadBox3D other = CadBox3D.create(this);
		return other;
	}
	
	public CadBox3D copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadBox3D otherRectangle = CadBox3D.create(this);
		otherRectangle.moveTo(ptIMcs, ptFMcs);
		return otherRectangle;
	}

	public CadBox3D moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	
	public CadBox3D scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);

    	ScaleData2dVO oMin = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrigMin2dMcs, ptOrigMin2dMcs);
    	this.ptMin = new GeomPoint3d(oMin.getPtDest());
		
    	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);

    	ScaleData2dVO oMax = GeomUtil.scaleToPt2d(ptI2dMcs, ptF2dMcs, ptOrigMin2dMcs, ptOrigMax2dMcs);
    	this.ptMax = new GeomPoint3d(oMax.getPtDest());

    	return this;
	}
	
	public CadBox3D offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadBox3D oOffset = copyTo(ptIMcs, ptFMcs);
		return oOffset;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"(XMin: %s; YMin: %s; ZMin: %s)-(XMax: %s; YMax: %s; ZMax: %s); ", 
			nf6.format(this.ptMin.getX()), 
			nf6.format(this.ptMin.getY()), 
			nf6.format(this.ptMin.getZ()),
			nf6.format(this.ptMax.getX()), 
			nf6.format(this.ptMax.getY()), 
			nf6.format(this.ptMax.getZ()) );
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

        GeomPoint2d ptDestMin2dMcs = new GeomPoint2d(this.ptMin);
        GeomPoint2d ptDestMax2dMcs = new GeomPoint2d(this.ptMax);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);
	        	MoveData2dVO oMin = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMin2dMcs);
	        	ptDestMin2dMcs = oMin.getPtDest();
	        	
	        	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);
	        	MoveData2dVO oMax = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMax2dMcs);
	        	ptDestMax2dMcs = oMax.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);
	        	ScaleData2dVO oMin = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMin2dMcs, ptOrigMin2dMcs);
	        	ptDestMin2dMcs = oMin.getPtDest();
	        	
	        	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);
	        	ScaleData2dVO oMax = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMin2dMcs, ptOrigMax2dMcs);
	        	ptDestMax2dMcs = oMax.getPtDest();
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

        GeomPoint3d ptDestMin3dMcs = new GeomPoint3d(
        	this.ptMin.getX(),
        	this.ptMin.getY(),
        	0.0);

        GeomPoint3d ptDestMax3dMcs = new GeomPoint3d(
        	this.ptMax.getX(),
        	this.ptMax.getY(),
        	this.altura);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);
	        	MoveData2dVO oMin = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMin2dMcs);
	        	ptDestMin3dMcs = new GeomPoint3d(oMin.getPtDest());
	        	
	        	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);
	        	MoveData2dVO oMax = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMax2dMcs);
	        	ptDestMax3dMcs = new GeomPoint3d(oMax.getPtDest());
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);
	        	ScaleData2dVO oMin = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMin2dMcs, ptOrigMin2dMcs);
	        	ptDestMin3dMcs = new GeomPoint3d(oMin.getPtDest());
	        	
	        	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);
	        	ScaleData2dVO oMax = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigMin2dMcs, ptOrigMax2dMcs);
	        	ptDestMax3dMcs = new GeomPoint3d(oMax.getPtDest());
	        }
        }
        
        prep.addBox(v, c, ptDestMin3dMcs, ptDestMax3dMcs);
        
        //GeomUtil.setColor(g, oldcol);
    }
    
	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
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

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	double xMinMcs = this.ptMin.getX();
    	double yMinMcs = this.ptMin.getY();
    	//
    	double xMaxMcs = this.ptMax.getX();
    	double yMaxMcs = this.ptMax.getY();
    	
    	GeomPoint2d pt2d0 = new GeomPoint2d(xMinMcs, yMinMcs);
    	GeomPoint2d pt2d1 = new GeomPoint2d(xMaxMcs, yMinMcs);
    	GeomPoint2d pt2d2 = new GeomPoint2d(xMaxMcs, yMaxMcs);
    	GeomPoint2d pt2d3 = new GeomPoint2d(xMinMcs, yMaxMcs);

    	ArrayList<GeomPoint2d> lsPtEndpoint = new ArrayList<GeomPoint2d>();    	
    	lsPtEndpoint.add(pt2d0);
    	lsPtEndpoint.add(pt2d1);
    	lsPtEndpoint.add(pt2d2);
    	lsPtEndpoint.add(pt2d3);
		
    	//MIDDLE
    	//
    	GeomPoint2d pt2dMid0 = GeomUtil.midPointOf(pt2d0, pt2d1);
    	GeomPoint2d pt2dMid1 = GeomUtil.midPointOf(pt2d1, pt2d2);
    	GeomPoint2d pt2dMid2 = GeomUtil.midPointOf(pt2d2, pt2d3);
    	GeomPoint2d pt2dMid3 = GeomUtil.midPointOf(pt2d3, pt2d0);
    	
    	ArrayList<GeomPoint2d> lsPtMiddle = new ArrayList<GeomPoint2d>();    	
    	lsPtMiddle.add(pt2dMid0);
    	lsPtMiddle.add(pt2dMid1);
    	lsPtMiddle.add(pt2dMid2);
    	lsPtMiddle.add(pt2dMid3);
    	
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
		GeomPoint3d ptResult = GeomUtil.midPointOf(ptMin, ptMax);
		return ptResult;
	}
    
    /* Getters/Setters */

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
