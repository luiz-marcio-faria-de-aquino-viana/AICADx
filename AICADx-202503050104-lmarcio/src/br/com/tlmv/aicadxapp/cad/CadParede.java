/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadParede.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
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
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadParede extends CadEntity 
{
//Private
    private int tipo;
    private double altura;
    private double largura; 
    private GeomPoint3d ptI;
    private GeomPoint3d ptF;
    //
    private ArrayList<CadAcabamentoParedeDef> lsAcabamento = null;
    private ArrayList<CadParede> lsParede = null;
    
//Public

    public CadParede(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BIMPAREDE, oLayer);
    }
	
	/* Methodes */
	
	private void init(
	    int tipo,
	    double altura,
	    double largura, 
		GeomPoint2d ptI, 
		GeomPoint2d ptF) 
	{
		this.init(
		    tipo,
		    altura,
		    largura, 
			ptI.getX(), 
			ptI.getY(), 
			0.0, 
			ptF.getX(), 
			ptF.getY(), 
			0.0);
	}
	
	private void init(
	    int tipo,
	    double altura,
	    double largura, 
		GeomPoint3d ptI, 
		GeomPoint3d ptF) 
	{
		this.init(
		    tipo,
		    altura,
		    largura, 
			ptI.getX(), 
			ptI.getY(), 
			ptI.getZ(), 
			ptF.getX(), 
			ptF.getY(), 
			ptF.getZ());
	}

	public void init(
	    int tipo,
	    double altura,
	    double largura, 
		double xI, 
		double yI, 
		double zI, 
		double xF, 
		double yF, 
		double zF) 
	{
	    this.tipo = tipo;
	    this.altura = altura;
	    this.largura = largura; 
	    //
		this.ptI = new GeomPoint3d(xI, yI, zI);
    	this.ptF = new GeomPoint3d(xF, yF, zF);
    	//
        this.lsAcabamento = new ArrayList<CadAcabamentoParedeDef>();
        this.lsParede = new ArrayList<CadParede>();
    }
	
	private void init(CadParede other) {
	    int tipo = other.tipo;
	    double altura = other.altura;
	    double largura = other.largura; 
		GeomPoint3d ptTmpPtI = other.ptI;
		GeomPoint3d ptTmpPtF = other.ptF;
		
		this.init(
			tipo,
			altura,
			largura, 
			ptTmpPtI.getX(), 
			ptTmpPtI.getY(), 
			ptTmpPtI.getZ(), 
			ptTmpPtF.getX(), 
			ptTmpPtF.getY(), 
			ptTmpPtF.getZ());
	}
	
	/* CREATE */
	
	public static CadParede create(
		CadLayerDef oLayer, 
		int tipo,
	    double altura,
	    double largura, 
		GeomPoint2d ptI, 
		GeomPoint2d ptF) 
	{
    	CadParede o = new CadParede(oLayer);

    	o.init(
		    tipo,
		    altura,
		    largura, 
			ptI, 
			ptF); 
    	return o;
    }
	
	public static CadParede create(
		CadLayerDef oLayer, 
		int tipo,
	    double altura,
	    double largura, 
		GeomPoint3d ptI, 
		GeomPoint3d ptF) 
	{
    	CadParede o = new CadParede(oLayer);
    	o.init(
		    tipo,
		    altura,
		    largura, 
			ptI, 
			ptF); 
    	return o;
    }
	
	public static CadParede create(
		CadLayerDef oLayer, 
		int tipo,
	    double altura,
	    double largura, 
		double xI, 
		double yI, 
		double zI, 
		double xF, 
		double yF, 
		double zF) 
	{
    	CadParede o = new CadParede(oLayer);

    	o.init(
		    tipo,
		    altura,
		    largura, 
		    xI, 
		    yI, 
		    zI, 
		    xF, 
		    yF, 
		    zF);
    	return o;
    }
	
	public static CadParede create(CadParede other) {
    	CadParede o = new CadParede(other.getLayer());
    	o.init(other);
    	return o;
    }

	/* ADDING_FEATURES */
	
    public void addAcabamentoDef(CadAcabamentoParedeDef o)
    {
    	this.lsAcabamento.add(o);
    }
	
    public void addParede(CadParede o)
    {
    	this.lsParede.add(o);
    }
	
	/* OPERATIONS */
	
	public CadParede duplicate()
	{
		CadParede other = CadParede.create(this);
		
		for(CadAcabamentoParedeDef oAcabamentoOrig : this.lsAcabamento) {
			CadAcabamentoParedeDef oAcabamento2Dest = new CadAcabamentoParedeDef(oAcabamentoOrig); 		
			other.addAcabamentoDef(oAcabamento2Dest);
		}
		
		return other;
	}
	
	public CadParede copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadParede other = CadParede.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	public CadParede moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	
	public CadParede scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	
	public CadParede mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return this;
	}
	
	public CadParede offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadParede oParede = copyTo(ptIMcs, ptFMcs);
		return oParede;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"Tipo:%s;Altura:%s;Largura:%s;Pontos:(XI: %s; YI: %s; ZI: %s)-(XF: %s; YF: %s; ZF: %s); ", 
		    this.tipo,
		    this.altura,
		    this.largura, 
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

	public void redraw2d_planView(ICadViewBase v, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, Graphics g)
	{
		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
		
		double hLargura = this.largura / 2.0;

		GeomPoint2d ptI2dMcs_left = ptI2dMcs.otherMoveTo(nIF2d, hLargura);
		GeomPoint2d ptI2dMcs_right = ptI2dMcs.otherMoveTo(nIF2d, - hLargura);

		GeomPoint2d ptF2dMcs_left = ptF2dMcs.otherMoveTo(nIF2d, hLargura);
		GeomPoint2d ptF2dMcs_right = ptF2dMcs.otherMoveTo(nIF2d, - hLargura);
		
        DrawUtil.drawLineMcs(v, ptI2dMcs_left, ptF2dMcs_left, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_left, ptF2dMcs_right, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_right, ptI2dMcs_right, g);
        DrawUtil.drawLineMcs(v, ptI2dMcs_right, ptI2dMcs_left, g);

		for(CadAcabamentoParedeDef o : this.lsAcabamento) {
			int tipoAcabamento = o.getTipo();
			double larguraAcabamento = o.getLargura();
			Color colorAcabamento = o.getColor();
			
			Color oldcol = GeomUtil.setColor(g, colorAcabamento);
			
			//ACABAMENTO-LEFT
			//
			GeomPoint2d ptI2dMcs_acabamento_left = ptI2dMcs_left.otherMoveTo(nIF2d, larguraAcabamento);
			GeomPoint2d ptF2dMcs_acabamento_left = ptF2dMcs_left.otherMoveTo(nIF2d, larguraAcabamento);

	        DrawUtil.drawLineMcs(v, ptI2dMcs_acabamento_left, ptF2dMcs_acabamento_left, g);
	        DrawUtil.drawLineMcs(v, ptI2dMcs_left, ptI2dMcs_acabamento_left, g);
	        DrawUtil.drawLineMcs(v, ptF2dMcs_left, ptF2dMcs_acabamento_left, g);
			
			//ACABAMENTO-RIGHT
			//
			GeomPoint2d ptI2dMcs_acabamento_right = ptI2dMcs_right.otherMoveTo(nIF2d, - larguraAcabamento);
			GeomPoint2d ptF2dMcs_acabamento_right = ptF2dMcs_right.otherMoveTo(nIF2d, - larguraAcabamento);

	        DrawUtil.drawLineMcs(v, ptI2dMcs_acabamento_right, ptF2dMcs_acabamento_right, g);
	        DrawUtil.drawLineMcs(v, ptI2dMcs_right, ptI2dMcs_acabamento_right, g);
	        DrawUtil.drawLineMcs(v, ptF2dMcs_right, ptF2dMcs_acabamento_right, g);
	        
	        GeomUtil.setColor(g, oldcol);
		}
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
        
        redraw2d_planView(v, ptDestI2dMcs, ptDestF2dMcs, g);
        
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	public void redraw3d_3dView(ICadViewBase v, Color c, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, PrepareDrawUtil prep)
	{
		GeomVector3d vIF3d = new GeomVector3d(ptI3dMcs, ptF3dMcs); 
		
		GeomVector3d uIF3d = vIF3d.otherUnit();
		GeomVector3d nIF3d = uIF3d.otherNorm();
		
		double hLargura = this.largura / 2.0;
		
		/* DRAW_WALL */

		//BASE
		GeomPoint3d ptBaseI3dMcs_left = ptI3dMcs.otherMoveTo(nIF3d, hLargura);
		GeomPoint3d ptBaseF3dMcs_left = ptF3dMcs.otherMoveTo(nIF3d, hLargura);

		GeomPoint3d ptBaseI3dMcs_right = ptI3dMcs.otherMoveTo(nIF3d, - hLargura);
		GeomPoint3d ptBaseF3dMcs_right = ptF3dMcs.otherMoveTo(nIF3d, - hLargura);

		//ELEV
		GeomPlan3d planMcs = v.getPlanMcs3d();
		
		GeomVector3d axisZ = planMcs.getAxisZ();

		GeomPoint3d ptElevI3dMcs_left = ptBaseI3dMcs_left.otherMoveTo(axisZ, this.altura);
		GeomPoint3d ptElevF3dMcs_left = ptBaseF3dMcs_left.otherMoveTo(axisZ, this.altura);

		GeomPoint3d ptElevI3dMcs_right = ptBaseI3dMcs_right.otherMoveTo(axisZ, this.altura);
		GeomPoint3d ptElevF3dMcs_right = ptBaseF3dMcs_right.otherMoveTo(axisZ, this.altura);
		
		//DRAW - LEFT/RIGHT SIDE
        prep.addFace(v, c, ptBaseI3dMcs_left, ptBaseF3dMcs_left, ptElevF3dMcs_left, ptElevI3dMcs_left);
        prep.addFace(v, c, ptBaseI3dMcs_right, ptBaseF3dMcs_right, ptElevF3dMcs_right, ptElevI3dMcs_right);
	
		/* DRAW_FINISH */

        double hL = hLargura;
		for(CadAcabamentoParedeDef o : this.lsAcabamento) {
			int tipoAcabamento = o.getTipo();
			double larguraAcabamento = o.getLargura();
			Color colorAcabamento = o.getColor();
			
			//Color oldcol = GeomUtil.setColor(g, colorAcabamento);

			hL = hL + (larguraAcabamento / 2.0);
			
			//BASE
			GeomPoint3d ptBaseI3dMcs_acabamento_left = ptBaseI3dMcs_left.otherMoveTo(nIF3d, hL);
			GeomPoint3d ptBaseF3dMcs_acabamento_left = ptBaseF3dMcs_left.otherMoveTo(nIF3d, hL);

			GeomPoint3d ptBaseI3dMcs_acabamento_right = ptBaseI3dMcs_right.otherMoveTo(nIF3d, - hL);
			GeomPoint3d ptBaseF3dMcs_acabamento_right = ptBaseF3dMcs_right.otherMoveTo(nIF3d, - hL);

			//ELEV
			GeomPoint3d ptElevI3dMcs_acabamento_left = ptBaseI3dMcs_acabamento_left.otherMoveTo(axisZ, this.altura);
			GeomPoint3d ptElevF3dMcs_acabamento_left = ptBaseF3dMcs_acabamento_left.otherMoveTo(axisZ, this.altura);

			GeomPoint3d ptElevI3dMcs_acabamento_right = ptBaseI3dMcs_acabamento_right.otherMoveTo(axisZ, this.altura);
			GeomPoint3d ptElevF3dMcs_acabamento_right = ptBaseF3dMcs_acabamento_right.otherMoveTo(axisZ, this.altura);
			
			//DRAW - LEFT SIDE
	        prep.addFace(v, colorAcabamento, ptBaseI3dMcs_acabamento_left, ptBaseF3dMcs_acabamento_left, ptElevF3dMcs_acabamento_left, ptElevI3dMcs_acabamento_left);
	        prep.addFace(v, colorAcabamento, ptBaseI3dMcs_acabamento_left, ptBaseF3dMcs_acabamento_left, ptElevF3dMcs_acabamento_left, ptElevI3dMcs_acabamento_left);
        	//
	        prep.addFace(v, colorAcabamento, ptBaseI3dMcs_left, ptBaseI3dMcs_acabamento_left, ptElevI3dMcs_acabamento_left, ptElevI3dMcs_left);
	        prep.addFace(v, colorAcabamento, ptBaseF3dMcs_left, ptBaseF3dMcs_acabamento_left, ptElevF3dMcs_acabamento_left, ptElevF3dMcs_left);
			
			//DRAW - RIGHT SIDE
	        prep.addFace(v, colorAcabamento, ptBaseI3dMcs_acabamento_right, ptBaseF3dMcs_acabamento_right, ptElevF3dMcs_acabamento_right, ptElevI3dMcs_acabamento_right);
	        prep.addFace(v, colorAcabamento, ptBaseI3dMcs_acabamento_right, ptBaseF3dMcs_acabamento_right, ptElevF3dMcs_acabamento_right, ptElevI3dMcs_acabamento_right);
        	//
	        prep.addFace(v, colorAcabamento, ptBaseI3dMcs_right, ptBaseI3dMcs_acabamento_right, ptElevI3dMcs_acabamento_right, ptElevI3dMcs_right);
	        prep.addFace(v, colorAcabamento, ptBaseF3dMcs_right, ptBaseF3dMcs_acabamento_right, ptElevF3dMcs_acabamento_right, ptElevF3dMcs_right);
	        
	        //GeomUtil.setColor(g, oldcol);
		}
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

        GeomPoint3d ptDestI3dMcs = new GeomPoint3d(this.ptI);
        GeomPoint3d ptDestF3dMcs = new GeomPoint3d(this.ptF);
        
        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
	        	MoveData2dVO oI = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs);
	        	ptDestI3dMcs = new GeomPoint3d(oI.getPtDest());
	        	
	        	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
	        	MoveData2dVO oF = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigF2dMcs);
	        	ptDestF3dMcs = new GeomPoint3d(oF.getPtDest());
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
	        	ScaleData2dVO oI = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs, ptOrigI2dMcs);
	        	ptDestI3dMcs = new GeomPoint3d(oI.getPtDest());
	        	
	        	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
	        	ScaleData2dVO oF = GeomUtil.scaleToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs, ptOrigF2dMcs);
	        	ptDestF3dMcs = new GeomPoint3d(oF.getPtDest());
	        }
        }
        
        redraw3d_3dView(v, c, ptDestI3dMcs, ptDestF3dMcs, prep);
        
        //GeomUtil.setColor(g, oldcol);
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
        double alturaParedeMcs = this.altura;

        double hAlturaParedeMcs = alturaParedeMcs / 2.0;
        
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.getPtF());

		GeomPoint2d ptMid2dMcs = GeomUtil.midPointOf(ptI2dMcs, ptF2dMcs);
		
		GeomPoint3d ptResult = new GeomPoint3d(ptMid2dMcs, hAlturaParedeMcs);
		return ptResult;
	}

	/* Getters/Setters */

    public GeomPoint3d getPtI() {
        return this.ptI;
    }

    public void setPtI(GeomPoint3d ptI) {
		this.ptI = ptI;
	}

	public GeomPoint3d getPtF() {
        return this.ptF;
    }

	public void setPtF(GeomPoint3d ptF) {
		this.ptF = ptF;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public ArrayList<CadAcabamentoParedeDef> getLsAcabamento() {
		return lsAcabamento;
	}

	public void setLsAcabamento(ArrayList<CadAcabamentoParedeDef> lsAcabamento) {
		this.lsAcabamento = lsAcabamento;
	}

	public ArrayList<CadParede> getLsParede() {
		return lsParede;
	}

	public void setLsParede(ArrayList<CadParede> lsParede) {
		this.lsParede = lsParede;
	}
	
	public double getLarguraTotal()
	{
		double larguraTotal = this.largura;		//LarguraParede
		
		for(CadAcabamentoParedeDef o : this.lsAcabamento) {
			double larguraAcabamento = o.getLargura();
			
			larguraTotal = larguraTotal + larguraAcabamento;
		}
		return larguraTotal;
	}

}
