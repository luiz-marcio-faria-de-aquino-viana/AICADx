/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPiso.java
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
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
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;

public class CadPiso extends CadEntity 
{
//Private
    private int tipo;
    private double espessura;
    private ArrayList<GeomPoint3d> lsPts;
    private GeomPoint3d ptCentroid;
    //
    private CadAcabamentoPisoDef oAcabamento;    
    
//Public

    public CadPiso(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BIMPISO, oLayer);
    }
	
	/* Methodes */
	
	private void init(int tipo, double espessura, double altura, ArrayList<GeomPoint2d> lsPts2d, CadAcabamentoPisoDef oAcabamento) {
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.from2dTo3d(lsPts2d, altura);
		this.init(tipo, espessura, lsPts3d, oAcabamento);
	}

	public void init(int tipo, double espessura, ArrayList<GeomPoint3d> lsPts3d, CadAcabamentoPisoDef oAcabamento) {
	    this.tipo = tipo;
	    this.espessura = espessura;
		this.lsPts = new ArrayList<GeomPoint3d>(lsPts3d);
		this.oAcabamento = oAcabamento;
		//
		this.ptCentroid = GeomUtil.centroidOf3d(lsPts3d);
    }
	
	private void init(CadPiso other) {
	    this.init(
	    	other.tipo,
	    	other.espessura,
	    	other.lsPts,
	    	other.oAcabamento);
	}
	
	/* CREATE */
	
	public static CadPiso create(CadLayerDef oLayer, int tipo, double espessura, double altura, ArrayList<GeomPoint2d> lsPts2d, CadAcabamentoPisoDef oAcabamento) {
    	CadPiso o = new CadPiso(oLayer);
    	o.init(tipo, espessura, altura, lsPts2d, oAcabamento);
    	return o;
    }
	
	public static CadPiso create(CadLayerDef oLayer, int tipo, double espessura, ArrayList<GeomPoint3d> lsPts3d, CadAcabamentoPisoDef oAcabamento) {
    	CadPiso o = new CadPiso(oLayer);
    	o.init(tipo, espessura, lsPts3d, oAcabamento);
    	return o;
    }
	
	public static CadPiso create(CadPiso other) {
    	CadPiso o = new CadPiso(other.getLayer());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadPiso duplicate()
	{
		CadPiso other = CadPiso.create(this);
		return other;
	}
	
	public CadPiso copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPiso other = CadPiso.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	public CadPiso moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
    	return this;
	}
	
	public CadPiso scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	ScaleData3dVO o = GeomUtil.scaleToPt3d(ptIMcs, ptFMcs, this.ptCentroid, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
    	return this;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		String str = super.toStr();		
		str += String.format(
			"Tipo:%s;Espessura:%s;Centroid:%s;", 
		    tipo,
		    espessura,
		    ptCentroid.toStr());
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWING */
	
	public void redraw3d_3dView(ICadViewBase v, Color c, ArrayList<GeomPoint3d> lsPtsMcs, PrepareDrawUtil prep)
	{
		double espessuraPiso = this.espessura;

		double espessuraAcabamento = this.espessura + this.oAcabamento.getLargura();
		
		Color corAcabamento = this.oAcabamento.getColor();
		
		/* DRAW_WALL */

		ArrayList<GeomPoint3d> lsBasePtsMcs = GeomUtil.from3dTo3d(lsPtsMcs, 0.0);
		ArrayList<GeomPoint3d> lsElevPtsMcs = GeomUtil.from3dTo3d(lsPtsMcs, espessuraPiso);
		ArrayList<GeomPoint3d> lsAcabamentoPtsMcs = GeomUtil.from3dTo3d(lsPtsMcs, espessuraAcabamento);
		
		//DRAW - LEFT/RIGHT SIDE
        prep.addFace(v, c, lsBasePtsMcs);
        prep.addFace(v, c, lsElevPtsMcs);
        prep.addExternalFace(v, c, lsBasePtsMcs, lsElevPtsMcs);
        //
        prep.addFace(v, corAcabamento, lsAcabamentoPtsMcs);
        prep.addExternalFace(v, corAcabamento, lsElevPtsMcs, lsAcabamentoPtsMcs);
	}
    
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
        
        if(ptBase2dMcs != null) 
        {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

            if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	CadPiso oPiso = this.duplicate();
	        	oPiso.moveTo(ptBase3dMcs, pt3dMcs);
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	CadPiso oPiso = this.duplicate();
	        	oPiso.scaleTo(ptBase3dMcs, pt3dMcs);
	        }
        }
        
        ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
        DrawUtil.drawPolygonMcs(v, lsPts2d, g);
        
        if(bSelected || bHover) {
            GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);
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
        
        if(ptBase2dMcs != null) 
        {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

            if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	CadPiso oPiso = this.duplicate();
	        	oPiso.moveTo(ptBase3dMcs, pt3dMcs);
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	CadPiso oPiso = this.duplicate();
	        	oPiso.scaleTo(ptBase3dMcs, pt3dMcs);
	        }
        }
        
        redraw3d_3dView(v, c, this.lsPts, prep);
        
        //GeomUtil.setColor(g, oldcol);
    }
    
	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
		if(this.isDeleted()) return false;
		if(this.isSelected()) return true;

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distBox = boxSz / 2.0;

        ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
        GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);

        double distCenter = GeomUtil.maxDistOf2d(ptCentroid2d, lsPts2d);

        double maxDist = distCenter + distBox;
        
        double dist = ptCentroid2d.distTo(pt2dMcs);
		if(dist < maxDist) {
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
    	ArrayList<GeomPoint2d> lsPtEndpoint = GeomUtil.from3dTo2d(this.lsPts);    	
		
    	//CENTER
    	//
    	ArrayList<GeomPoint2d> lsPtCenter = new ArrayList<GeomPoint2d>();
    	
    	GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);
    	lsPtCenter.add(ptCentroid2d);
    	
    	GeomPoint2d ptResult = null;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCentroid);
		return ptResult;
	}
    
    /* Getters/Setters */

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getEspessura() {
		return espessura;
	}

	public void setEspessura(double espessura) {
		this.espessura = espessura;
	}

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public void setPtCentroid(GeomPoint3d ptCentroid) {
		this.ptCentroid = ptCentroid;
	}

	public CadAcabamentoPisoDef getAcabamento() {
		return oAcabamento;
	}

	public void setoAcabamento(CadAcabamentoPisoDef oAcabamento) {
		this.oAcabamento = oAcabamento;
	}

    public ArrayList<GeomPoint3d> getLsPts() {
        return this.lsPts;
    }

    public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}
    
}
