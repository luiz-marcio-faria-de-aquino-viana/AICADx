/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadJanela.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 18/02/2025
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

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
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

public class CadJanela extends CadEntity 
{
//Private
    private int tipo;
    private double altura;
    private double largura; 
    private double espessura; 
	private CadParede oParede;
    private double dist;
    private int direcao;
    private double alturaPiso;
    //
    private CadAcabamentoJanelaDef oAcabamento;
	
//Public

    public CadJanela(CadLayerDef oLayer) 
    {
    	super(AppDefs.OBJTYPE_BIMJANELA, oLayer);
    }
	
	/* Methodes */
	
	public void init(
    	int tipo,
    	double altura,
    	double largura, 
    	double espessura, 
    	CadParede oParede,
    	double dist,
    	int direcao,
        double alturaPiso,
    	CadAcabamentoJanelaDef oAcabamento) 
	{
	    this.tipo = tipo;
	    this.altura = altura;
	    this.largura = largura; 
	    this.espessura = espessura; 
    	this.oParede = oParede;
    	this.dist = dist;
    	this.direcao = direcao; 
        this.alturaPiso = alturaPiso;
	    //
	    this.oAcabamento = oAcabamento;
    }
	
	public void init(CadJanela other) 
	{
		this.init(
			other.tipo,
			other.altura,
			other.largura, 
			other.espessura,
			other.oParede,
			other.dist,
			other.direcao,
		    other.alturaPiso,
			other.oAcabamento);
	}

	/* CREATE */
		
	public static CadJanela create(
			CadLayerDef oLayer, 
			int tipo,
	    	double altura,
	    	double largura, 
	    	double espessura, 
	    	CadParede oParede,
	    	double dist,
	    	int direcao,
	        double alturaPiso,
	    	CadAcabamentoJanelaDef oAcabamento) 
	{
		CadJanela other = new CadJanela(oLayer); 
		other.init(
			tipo,
			altura,
			largura, 
			espessura,
			oParede,
			dist,
			direcao,
		    alturaPiso,
			oAcabamento);
		return other;
    }
	
	public static CadJanela create(CadJanela other)
	{
		CadJanela o = new CadJanela(other.getLayer());
		o.init(other);
		return o;
	}

	/* OPERATIONS */
	
	public CadJanela duplicate()
	{
		CadJanela other = CadJanela.create(this);
		return other;
	}

	public CadJanela copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadJanela other = CadJanela.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	public CadJanela moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.oParede.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.oParede.getPtF());

        GeomVector2d vIF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
        GeomVector2d uIF2dMcs = vIF2dMcs.otherUnit();

        GeomPoint2d ptBase2dMcs = new GeomPoint2d(ptIMcs);
        GeomPoint2d ptCurr2dMcs = new GeomPoint2d(ptFMcs);

        GeomVector2d vBaseToCurr2dMcs = new GeomVector2d(ptBase2dMcs, ptCurr2dMcs);
        this.dist = uIF2dMcs.dotProd(vBaseToCurr2dMcs);
        
		return this;
	}
	
	public CadJanela scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		this.moveTo(ptIMcs, ptFMcs);
		return this;        
	}
    
	public CadJanela mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return this;
	}
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = super.toStr();
		str += String.format(
			"Tipo:%s;Altura:%s;Largura:%s;Espessura:%s;Parede:%s;Dist:%s;Direcao:%s;AlturaPiso:%s;Acabamento:%s;", 
		    this.tipo,
		    nf6.format(this.altura),
    		nf6.format(this.largura), 
    		nf6.format(this.espessura), 
			this.oParede.getObjectId(),
			nf6.format(this.dist),
			this.direcao,
			nf6.format(this.alturaPiso),				
			this.oAcabamento.getObjectId());
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWING */

	public void redraw2d_planView(ICadViewBase v, GeomPoint2d ptIns2dMcs, GeomVector2d uDir2dMcs, double larguraParedeMcs, double larguraJanelaMcs, double alturaJanelaMcs, double espessuraJanelaMcs, int direcao, double alturaPisoJanelaMcs, Graphics g)
	{
		GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();
		
		double hLarguraParedeMcs = larguraParedeMcs / 2.0;
		
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIns2dMcs);
		GeomPoint2d ptF2dMcs = ptIns2dMcs.otherMoveTo(uDir2dMcs, larguraJanelaMcs);
		
		GeomPoint2d ptI_left_2dMcs = ptI2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);
		GeomPoint2d ptI_right_2dMcs = ptI2dMcs.otherMoveTo(nDir2dMcs, - hLarguraParedeMcs);
		
		GeomPoint2d ptF_left_2dMcs = ptF2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);
		GeomPoint2d ptF_right_2dMcs = ptF2dMcs.otherMoveTo(nDir2dMcs, - hLarguraParedeMcs);
		
		//REMOVE_WALL_LINES
		//
		ArrayList<GeomPoint2d> lsPts2dMcs = new ArrayList<GeomPoint2d>(); 
		
		lsPts2dMcs.add(ptI_left_2dMcs);
		lsPts2dMcs.add(ptI_right_2dMcs);
		lsPts2dMcs.add(ptF_right_2dMcs);
		lsPts2dMcs.add(ptF_left_2dMcs);

		Color oldcol1 = GeomUtil.setColor(g, AppDefs.BACKGROUNDCOLOR);

		DrawUtil.fillPolygonMcs(v, lsPts2dMcs, g);

		GeomUtil.setColor(g, oldcol1);
		
		//DRAW_CLOSEWALL_LINES
		//
        DrawUtil.drawLineMcs(v, ptI_left_2dMcs, ptI_right_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptF_left_2dMcs, ptF_right_2dMcs, g);

        Color oldcol2 = GeomUtil.setColor(g, this.oAcabamento.getCorGuarnicao());
        
		//DRAW_BATENTE
		//
        double espessuraBatenteMcs = this.oAcabamento.getEspessuraBatente();
        double hComprimentoBatenteMcs = (larguraParedeMcs - this.espessura) / 2.0; 
		
        double hEspessuraBatenteMcs = espessuraBatenteMcs / 2.0;
        
        DrawUtil.drawLineMcs(v, ptI_left_2dMcs, ptF_left_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptI_right_2dMcs, ptF_right_2dMcs, g);
        
        //INICIAL
		GeomPoint2d ptBatenteI0_2dMcs = new GeomPoint2d(ptI_right_2dMcs);
		GeomPoint2d ptBatenteI1_2dMcs = new GeomPoint2d(ptI_left_2dMcs);
		GeomPoint2d ptBatenteI2_2dMcs = ptBatenteI1_2dMcs.otherMoveTo(uDir2dMcs, espessuraBatenteMcs);
		GeomPoint2d ptBatenteI3_2dMcs = ptBatenteI2_2dMcs.otherMoveTo(nDir2dMcs, - hComprimentoBatenteMcs);
		GeomPoint2d ptBatenteI4_2dMcs = ptBatenteI3_2dMcs.otherMoveTo(uDir2dMcs, - hEspessuraBatenteMcs);
		GeomPoint2d ptBatenteI5_2dMcs = ptBatenteI4_2dMcs.otherMoveTo(nDir2dMcs, - this.espessura);
		GeomPoint2d ptBatenteI6_2dMcs = ptBatenteI5_2dMcs.otherMoveTo(uDir2dMcs, hEspessuraBatenteMcs);
		GeomPoint2d ptBatenteI7_2dMcs = ptBatenteI6_2dMcs.otherMoveTo(nDir2dMcs, - hComprimentoBatenteMcs);
		
        DrawUtil.drawLineMcs(v, ptBatenteI0_2dMcs, ptBatenteI1_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI1_2dMcs, ptBatenteI2_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI2_2dMcs, ptBatenteI3_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI3_2dMcs, ptBatenteI4_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI4_2dMcs, ptBatenteI5_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI5_2dMcs, ptBatenteI6_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI6_2dMcs, ptBatenteI7_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteI7_2dMcs, ptBatenteI0_2dMcs, g);

        //FINAL
		GeomPoint2d ptBatenteF0_2dMcs = new GeomPoint2d(ptF_right_2dMcs);
		GeomPoint2d ptBatenteF1_2dMcs = new GeomPoint2d(ptF_left_2dMcs);
		GeomPoint2d ptBatenteF2_2dMcs = ptBatenteF1_2dMcs.otherMoveTo(uDir2dMcs, - espessuraBatenteMcs);
		GeomPoint2d ptBatenteF3_2dMcs = ptBatenteF2_2dMcs.otherMoveTo(nDir2dMcs, - hComprimentoBatenteMcs);
		GeomPoint2d ptBatenteF4_2dMcs = ptBatenteF3_2dMcs.otherMoveTo(uDir2dMcs, hEspessuraBatenteMcs);
		GeomPoint2d ptBatenteF5_2dMcs = ptBatenteF4_2dMcs.otherMoveTo(nDir2dMcs, - this.espessura);
		GeomPoint2d ptBatenteF6_2dMcs = ptBatenteF5_2dMcs.otherMoveTo(uDir2dMcs, - hEspessuraBatenteMcs);
		GeomPoint2d ptBatenteF7_2dMcs = ptBatenteF6_2dMcs.otherMoveTo(nDir2dMcs, - hComprimentoBatenteMcs);
		
        DrawUtil.drawLineMcs(v, ptBatenteF0_2dMcs, ptBatenteF1_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF1_2dMcs, ptBatenteF2_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF2_2dMcs, ptBatenteF3_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF3_2dMcs, ptBatenteF4_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF4_2dMcs, ptBatenteF5_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF5_2dMcs, ptBatenteF6_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF6_2dMcs, ptBatenteF7_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptBatenteF7_2dMcs, ptBatenteF0_2dMcs, g);
        
        GeomUtil.setColor(g, oldcol2);
        
		//DRAW_GUARNICOES
		//
        double larguraGuarnicaoMcs = this.oAcabamento.getLarguraGuarnicao();
        //double alturaGuarnicaoMcs = this.oAcabamento.getAlturaGuarnicao();
        double espessuraGuarnicaoMcs = this.oAcabamento.getEspessuraGuarnicao();        

        double larguraTotalGuarnicaMcs = 2.0 * larguraGuarnicaoMcs + this.largura;
        
        //RIGHT
		GeomPoint2d ptGuarnicao3_right_2dMcs = ptI_right_2dMcs.otherMoveTo(uDir2dMcs, - larguraGuarnicaoMcs);
		GeomPoint2d ptGuarnicao0_right_2dMcs = ptGuarnicao3_right_2dMcs.otherMoveTo(nDir2dMcs, - espessuraGuarnicaoMcs);
		GeomPoint2d ptGuarnicao1_right_2dMcs = ptGuarnicao0_right_2dMcs.otherMoveTo(uDir2dMcs, larguraTotalGuarnicaMcs);
		GeomPoint2d ptGuarnicao2_right_2dMcs = ptGuarnicao1_right_2dMcs.otherMoveTo(nDir2dMcs, espessuraGuarnicaoMcs);

        DrawUtil.drawLineMcs(v, ptGuarnicao0_right_2dMcs, ptGuarnicao1_right_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptGuarnicao1_right_2dMcs, ptGuarnicao2_right_2dMcs, g);        
        DrawUtil.drawLineMcs(v, ptGuarnicao2_right_2dMcs, ptGuarnicao3_right_2dMcs, g);        
        DrawUtil.drawLineMcs(v, ptGuarnicao3_right_2dMcs, ptGuarnicao0_right_2dMcs, g);        
		
        //LEFT
		GeomPoint2d ptGuarnicao3_left_2dMcs = ptI_left_2dMcs.otherMoveTo(uDir2dMcs, - larguraGuarnicaoMcs);
		GeomPoint2d ptGuarnicao0_left_2dMcs = ptGuarnicao3_left_2dMcs.otherMoveTo(nDir2dMcs, espessuraGuarnicaoMcs);
		GeomPoint2d ptGuarnicao1_left_2dMcs = ptGuarnicao0_left_2dMcs.otherMoveTo(uDir2dMcs, larguraTotalGuarnicaMcs);
		GeomPoint2d ptGuarnicao2_left_2dMcs = ptGuarnicao1_left_2dMcs.otherMoveTo(nDir2dMcs, - espessuraGuarnicaoMcs);

        DrawUtil.drawLineMcs(v, ptGuarnicao0_left_2dMcs, ptGuarnicao1_left_2dMcs, g);
        DrawUtil.drawLineMcs(v, ptGuarnicao1_left_2dMcs, ptGuarnicao2_left_2dMcs, g);        
        DrawUtil.drawLineMcs(v, ptGuarnicao2_left_2dMcs, ptGuarnicao3_left_2dMcs, g);        
        DrawUtil.drawLineMcs(v, ptGuarnicao3_left_2dMcs, ptGuarnicao0_left_2dMcs, g);        
        
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

        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.oParede.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.oParede.getPtF());

        GeomVector2d vIF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
        GeomVector2d uIF2dMcs = vIF2dMcs.otherUnit();

        GeomPoint2d ptIns2dMcs = ptI2dMcs.otherMoveTo(uIF2dMcs, this.dist);

        if(ptBase2dMcs != null) 
        {        
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(ptIns2dMcs);
	        	MoveData2dVO oI = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs);
	        	ptIns2dMcs = oI.getPtDest();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(ptIns2dMcs);
	        	MoveData2dVO oI = GeomUtil.moveToPt2d(ptBase2dMcs, pt2dMcs, ptOrigI2dMcs);
	        	ptIns2dMcs = oI.getPtDest();
	        }
        }

        //DRAW JANELA
        //
        double distMcs_4px = v.fromScrToMcs(4.0);
        
        double larguraParedeMcs = this.oParede.getLargura() + distMcs_4px;
        double larguraJanelaMcs = this.largura;
        double alturaJanelaMcs = this.altura;
        double espessuraJanelaMcs = this.espessura;
        
        int direcao = this.direcao;

        double alturaPisoJanelaMcs = this.alturaPiso;
        
        for(CadAcabamentoParedeDef o : this.oParede.getLsAcabamento()) {
        	larguraParedeMcs += 2 * o.getLargura();
        }
        
    	this.redraw2d_planView(v, ptIns2dMcs, uIF2dMcs, larguraParedeMcs, larguraJanelaMcs, alturaJanelaMcs, espessuraJanelaMcs, direcao, alturaPisoJanelaMcs, g);
        
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
    
	public void redraw3d_3dView(
		ICadViewBase v, 
		Color c,
		GeomPoint3d ptIns3dMcs, 
		GeomVector3d uDir3dMcs, 
		double oldLarguraParedeMcs, 
		double alturaParedeMcs, 		
		double larguraJanelaMcs, 
		double alturaJanelaMcs, 
		double espessuraJanelaMcs, 
		double alturaPisoJanelaMcs,
		double larguraGuarnicaoMcs,
		double alturaGuarnicaoMcs,
		double espessuraGuarnicaoMcs,
		int direcao,
		PrepareDrawUtil prep)
	{
		GeomVector3d nDir3dMcs = uDir3dMcs.otherNorm();
		
		double larguraParedeMcs = oldLarguraParedeMcs + (2.0 * AppDefs.FACE_WALLDIST);
		
		double hLarguraParede = larguraParedeMcs / 2.0;

		double larguraTotal = (2 * larguraGuarnicaoMcs) + larguraJanelaMcs;
		
		double alturaTotal = alturaJanelaMcs + alturaGuarnicaoMcs;

		double alturaJanela = alturaJanelaMcs + this.alturaPiso;
		
		GeomPlan3d planMcs = v.getPlanMcs3d();
		
		GeomVector3d axisZ = planMcs.getAxisZ();

		Color c1 = this.oAcabamento.getCorGuarnicao();

		Color c2 = this.oAcabamento.getCorJanela();
		
		/* DRAW_DOOR */

		//BASE_PAREDE
		GeomPoint3d ptBaseI3dMcs_left = ptIns3dMcs.otherMoveTo(nDir3dMcs, hLarguraParede);
		GeomPoint3d ptBaseF3dMcs_left = ptBaseI3dMcs_left.otherMoveTo(uDir3dMcs, larguraJanelaMcs);

		GeomPoint3d ptBaseI3dMcs_right = ptIns3dMcs.otherMoveTo(nDir3dMcs, - hLarguraParede);
		GeomPoint3d ptBaseF3dMcs_right = ptBaseI3dMcs_right.otherMoveTo(uDir3dMcs, larguraJanelaMcs);
		
		//BASE_JANELA
		GeomPoint3d ptBaseWinI3dMcs_left = ptBaseI3dMcs_left.otherMoveTo(axisZ, alturaPisoJanelaMcs);
		GeomPoint3d ptBaseWinF3dMcs_left = ptBaseF3dMcs_left.otherMoveTo(axisZ, alturaPisoJanelaMcs);

		GeomPoint3d ptBaseWinI3dMcs_right = ptBaseI3dMcs_right.otherMoveTo(axisZ, alturaPisoJanelaMcs);
		GeomPoint3d ptBaseWinF3dMcs_right = ptBaseF3dMcs_right.otherMoveTo(axisZ, alturaPisoJanelaMcs);

		//ELEV
		GeomPoint3d ptElevI3dMcs_left = ptBaseI3dMcs_left.otherMoveTo(axisZ, alturaJanela);
		GeomPoint3d ptElevF3dMcs_left = ptBaseF3dMcs_left.otherMoveTo(axisZ, alturaJanela);

		GeomPoint3d ptElevI3dMcs_right = ptBaseI3dMcs_right.otherMoveTo(axisZ, alturaJanela);
		GeomPoint3d ptElevF3dMcs_right = ptBaseF3dMcs_right.otherMoveTo(axisZ, alturaJanela);

        //Color oldcol = GeomUtil.setColor(g, c1);
        
		/* DRAW_GUARNICAO - LEFT SIDE */
        GeomPoint3d pt1_left = new GeomPoint3d(ptBaseWinI3dMcs_left);
        GeomPoint3d pt2_left = pt1_left.otherMoveTo(uDir3dMcs, - larguraGuarnicaoMcs);
        GeomPoint3d pt3_left = pt2_left.otherMoveTo(axisZ, alturaTotal);
        GeomPoint3d pt4_left = pt3_left.otherMoveTo(uDir3dMcs, larguraTotal);
        GeomPoint3d pt5_left = pt4_left.otherMoveTo(axisZ, - alturaTotal);
        GeomPoint3d pt6_left = pt5_left.otherMoveTo(uDir3dMcs, larguraGuarnicaoMcs);

        ArrayList<GeomPoint3d> lsPts3d_base_left = new ArrayList<GeomPoint3d>(); 
        lsPts3d_base_left.add(pt1_left);
        lsPts3d_base_left.add(pt2_left);
        lsPts3d_base_left.add(pt3_left);
        lsPts3d_base_left.add(pt4_left);
        lsPts3d_base_left.add(pt5_left);
        lsPts3d_base_left.add(pt6_left);
        
        ArrayList<GeomPoint3d> lsPts3d_extern_left = GeomUtil.from3dTo3d(lsPts3d_base_left, nDir3dMcs, espessuraGuarnicaoMcs);

        prep.addExternalFace(v, c1, lsPts3d_base_left, lsPts3d_extern_left);

        //===
        
        pt1_left = new GeomPoint3d(ptBaseWinI3dMcs_left);
        pt2_left = pt1_left.otherMoveTo(uDir3dMcs, - larguraGuarnicaoMcs);
        pt3_left = pt2_left.otherMoveTo(axisZ, - alturaGuarnicaoMcs);
        pt4_left = pt3_left.otherMoveTo(uDir3dMcs, larguraTotal);
        pt5_left = pt4_left.otherMoveTo(axisZ, alturaGuarnicaoMcs);

        lsPts3d_base_left = new ArrayList<GeomPoint3d>(); 
        lsPts3d_base_left.add(pt1_left);
        lsPts3d_base_left.add(pt2_left);
        lsPts3d_base_left.add(pt3_left);
        lsPts3d_base_left.add(pt4_left);
        lsPts3d_base_left.add(pt5_left);
        
        lsPts3d_extern_left = GeomUtil.from3dTo3d(lsPts3d_base_left, nDir3dMcs, espessuraGuarnicaoMcs);

        prep.addExternalFace(v, c1, lsPts3d_base_left, lsPts3d_extern_left);        
        
		/* DRAW_GUARNICAO - RIGHT SIDE */
        GeomPoint3d pt1_right = new GeomPoint3d(ptBaseWinI3dMcs_right);
        GeomPoint3d pt2_right = pt1_right.otherMoveTo(uDir3dMcs, - larguraGuarnicaoMcs);
        GeomPoint3d pt3_right = pt2_right.otherMoveTo(axisZ, alturaTotal);
        GeomPoint3d pt4_right = pt3_right.otherMoveTo(uDir3dMcs, larguraTotal);
        GeomPoint3d pt5_right = pt4_right.otherMoveTo(axisZ, - alturaTotal);
        GeomPoint3d pt6_right = pt5_right.otherMoveTo(uDir3dMcs, larguraGuarnicaoMcs);

        ArrayList<GeomPoint3d> lsPts3d_base_right = new ArrayList<GeomPoint3d>(); 
        lsPts3d_base_right.add(pt1_right);
        lsPts3d_base_right.add(pt2_right);
        lsPts3d_base_right.add(pt3_right);
        lsPts3d_base_right.add(pt4_right);
        lsPts3d_base_right.add(pt5_right);
        lsPts3d_base_right.add(pt6_right);
        
        ArrayList<GeomPoint3d> lsPts3d_extern_right = GeomUtil.from3dTo3d(lsPts3d_base_right, nDir3dMcs, - espessuraGuarnicaoMcs);

        prep.addExternalFace(v, c1, lsPts3d_base_right, lsPts3d_extern_right);        

        //===
        
        pt1_right = new GeomPoint3d(ptBaseWinI3dMcs_right);
        pt2_right = pt1_right.otherMoveTo(uDir3dMcs, - larguraGuarnicaoMcs);
        pt3_right = pt2_right.otherMoveTo(axisZ, alturaGuarnicaoMcs);
        pt4_right = pt3_right.otherMoveTo(uDir3dMcs, larguraTotal);
        pt5_right = pt4_right.otherMoveTo(axisZ, - alturaGuarnicaoMcs);

        lsPts3d_base_right = new ArrayList<GeomPoint3d>(); 
        lsPts3d_base_right.add(pt1_right);
        lsPts3d_base_right.add(pt2_right);
        lsPts3d_base_right.add(pt3_right);
        lsPts3d_base_right.add(pt4_right);
        lsPts3d_base_right.add(pt5_right);
        
        lsPts3d_extern_right = GeomUtil.from3dTo3d(lsPts3d_base_right, nDir3dMcs, - espessuraGuarnicaoMcs);

        prep.addExternalFace(v, c1, lsPts3d_base_right, lsPts3d_extern_right);        
        	        
        //GeomUtil.setColor(g, c2);
		
        //WINDOW_OPPENING - LEFT
        ArrayList<GeomPoint3d> lsPts3d_win_left = new ArrayList<GeomPoint3d>(); 
        lsPts3d_win_left.add(ptBaseWinI3dMcs_left);
        lsPts3d_win_left.add(ptBaseWinF3dMcs_left);
        lsPts3d_win_left.add(ptElevF3dMcs_left);
        lsPts3d_win_left.add(ptElevI3dMcs_left);

        prep.addFace(v, c2, lsPts3d_win_left);
        
        //WINDOW_OPPENING - RIGHT
        ArrayList<GeomPoint3d> lsPts3d_win_right = new ArrayList<GeomPoint3d>(); 
        lsPts3d_win_right.add(ptBaseWinI3dMcs_right);
        lsPts3d_win_right.add(ptBaseWinF3dMcs_right);
        lsPts3d_win_right.add(ptElevF3dMcs_right);
        lsPts3d_win_right.add(ptElevI3dMcs_right);

        prep.addFace(v, c2, lsPts3d_win_right);

        //oldcol = GeomUtil.setColor(g, c1);
        
		//DRAW - LEFT/RIGHT SIDE
        //DrawUtil.drawLineProj(v, ptBaseWinI3dMcs_left, ptElevF3dMcs_left, g);
        //DrawUtil.drawLineProj(v, ptBaseWinF3dMcs_left, ptElevI3dMcs_left, g);

        //DrawUtil.drawLineProj(v, ptBaseWinI3dMcs_right, ptElevF3dMcs_right, g);
        //DrawUtil.drawLineProj(v, ptBaseWinF3dMcs_right, ptElevI3dMcs_right, g);
        
        //GeomUtil.setColor(g, oldcol);
	}
	
	public void redraw3d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, PrepareDrawUtil prep) 
	{
		if(this.isDeleted()) return;
	
		double larguraParede = this.oParede.getLarguraTotal();
		double alturaParede = this.oParede.getAltura();
		//
		double larguraJanela = this.largura;
		double alturaJanela = this.altura;
		double espessuraJanela = this.espessura;
		double alturaPisoJanela = this.alturaPiso;
		//
		double larguraGuarnicao = this.oAcabamento.getLarguraGuarnicao();
		double alturaGuarnicao = this.oAcabamento.getAlturaGuarnicao();
		double espessuraGuarnicao = this.oAcabamento.getEspessuraGuarnicao();
		//
		int direcao = this.direcao;
		
	    //boolean bSelected = this.isSelected();
		//boolean bHover = false;
		//if( !bSelected )
		//	bHover = this.select2d(v, pt2dMcs, false);
		//Color c = super.selectColor(bDragMode, bSelected, bHover);
	
		Color c = super.selectColor(bDragMode, false, false);
	
		//Color oldcol = GeomUtil.setColor(g, c);		
	
	    MainPanel panel = MainPanel.getPanel();
	    String action = panel.getCurrAction();
	
	    GeomPoint3d ptI3dMcs = new GeomPoint3d(this.oParede.getPtI());
	    GeomPoint3d ptF3dMcs = new GeomPoint3d(this.oParede.getPtF());
	
	    GeomVector3d vIF3dMcs = new GeomVector3d(ptI3dMcs, ptF3dMcs); 
	    GeomVector3d uIF3dMcs = vIF3dMcs.otherUnit();
	    
	    GeomPoint3d ptIns3dMcs = ptI3dMcs.otherMoveTo(uIF3dMcs, this.dist);
	
	    if(ptBase2dMcs != null) 
	    {        
	        GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
	        GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);
	
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	MoveData3dVO oI = GeomUtil.moveToPt3d(ptBase3dMcs, pt3dMcs, ptIns3dMcs);
	        	ptIns3dMcs = new GeomPoint3d(oI.getPtDest());
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	ScaleData3dVO oI = GeomUtil.scaleToPt3d(ptBase3dMcs, pt3dMcs, ptIns3dMcs, ptIns3dMcs);
	        	ptIns3dMcs = new GeomPoint3d(oI.getPtDest());
	        }
	    }
	    
	    redraw3d_3dView(
			v,
			c,
			ptIns3dMcs, 
			uIF3dMcs, 
			larguraParede, 
			alturaParede, 		
			larguraJanela, 
			alturaJanela, 
			espessuraJanela, 
			alturaPisoJanela,
			larguraGuarnicao,
			alturaGuarnicao,
			espessuraGuarnicao,
			direcao,
			prep);
	    
	    //GeomUtil.setColor(g, oldcol);
	}

	/* SELECT */

	public boolean select2d(ICadViewBase v, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
    	if( this.isDeleted() ) return false;
    	if( this.isSelected() ) return true;

        //MainPanel panel = MainPanel.getPanel();
        //String action = panel.getCurrAction();

        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.oParede.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.oParede.getPtF());

        GeomVector2d vDir2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
        GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();

        GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();

        GeomPoint2d ptIns2dMcs = ptI2dMcs.otherMoveTo(uDir2dMcs, this.dist);

        //DOOR_ISSELECTED
        //
        double distMcs_4px = v.fromScrToMcs(4.0);
        
        double larguraParedeMcs = this.oParede.getLargura() + distMcs_4px;
        double larguraJanelaMcs = this.largura;
        //double alturaJanelaMcs = this.altura;
        //double espessuraJanelaMcs = this.espessura;
        
        //int direcao = this.direcao;

        //double alturaPisoJanelaMcs = this.alturaPiso;
        
        for(CadAcabamentoParedeDef o : this.oParede.getLsAcabamento()) {
        	larguraParedeMcs += 2 * o.getLargura();
        }

        double hLarguraParedeMcs = larguraParedeMcs / 2.0;
        
		GeomPoint2d pt0_2dMcs = new GeomPoint2d(ptIns2dMcs);
		GeomPoint2d pt1_2dMcs = ptIns2dMcs.otherMoveTo(uDir2dMcs, larguraJanelaMcs);
		
		GeomPoint2d pt0_left_2dMcs = pt0_2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);
		GeomPoint2d pt1_left_2dMcs = pt1_2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);

		GeomPoint2d pt0_right_2dMcs = pt0_left_2dMcs.otherMoveTo(nDir2dMcs, - larguraParedeMcs);
		GeomPoint2d pt1_right_2dMcs = pt1_left_2dMcs.otherMoveTo(nDir2dMcs, - larguraParedeMcs);
    	
		ArrayList<GeomPoint2d> lsPts = new ArrayList<GeomPoint2d>();

		lsPts.add(pt0_left_2dMcs);
		lsPts.add(pt1_left_2dMcs);
		lsPts.add(pt0_right_2dMcs);
		lsPts.add(pt1_right_2dMcs);
		
		GeomPoint2d ptCentroid = GeomUtil.centroidOf2d(lsPts);
		double radius = larguraJanelaMcs;
			
        double boxSz = v.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = radius + boxSz / 2.0;
        
        double dist = ptCentroid.distTo(pt2dMcs); 
        if(dist <= distMax) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return this.isSelected();
	}
    
	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase v, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

        //MainPanel panel = MainPanel.getPanel();
        //String action = panel.getCurrAction();

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint2d> lsPtEndpoint = new ArrayList<GeomPoint2d>();    	
		
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.oParede.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.oParede.getPtF());

        GeomVector2d vDir2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
        GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();

        GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();

        GeomPoint2d ptIns2dMcs = ptI2dMcs.otherMoveTo(uDir2dMcs, this.dist);

        //PONTOS JANELA
        //
        double distMcs_4px = v.fromScrToMcs(4.0);
        
        double larguraParedeMcs = this.oParede.getLargura() + distMcs_4px;
        double larguraJanelaMcs = this.largura;
        //double alturaJanelaMcs = this.altura;
        double espessuraJanelaMcs = this.espessura;
        
        int direcao = this.direcao;

        //double alturaPisoJanelaMcs = this.alturaPiso;
        
        for(CadAcabamentoParedeDef o : this.oParede.getLsAcabamento()) {
        	larguraParedeMcs += 2 * o.getLargura();
        }

        double hLarguraParedeMcs = larguraParedeMcs / 2.0;
        
		GeomPoint2d pt0_2dMcs = new GeomPoint2d(ptIns2dMcs);
		GeomPoint2d pt1_2dMcs = ptIns2dMcs.otherMoveTo(uDir2dMcs, larguraJanelaMcs);
		
		GeomPoint2d pt0_left_2dMcs = pt0_2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);
		GeomPoint2d pt1_left_2dMcs = pt1_2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);

		GeomPoint2d pt0_right_2dMcs = pt0_2dMcs.otherMoveTo(nDir2dMcs, - hLarguraParedeMcs);
		GeomPoint2d pt1_right_2dMcs = pt1_2dMcs.otherMoveTo(nDir2dMcs, - hLarguraParedeMcs);

		//Endpoint
		lsPtEndpoint.add(pt0_left_2dMcs);
		lsPtEndpoint.add(pt1_left_2dMcs);
		lsPtEndpoint.add(pt0_right_2dMcs);
		lsPtEndpoint.add(pt1_right_2dMcs);
		
        if(direcao == AppDefs.WINDOWDIR_PT0) {
            //DOOR_LINES
            //
    		GeomPoint2d ptPorta0_2dMcs = new GeomPoint2d(pt0_right_2dMcs); 
    		GeomPoint2d ptPorta1_2dMcs = ptPorta0_2dMcs.otherMoveTo(nDir2dMcs, - larguraJanelaMcs);
    		GeomPoint2d ptPorta2_2dMcs = ptPorta1_2dMcs.otherMoveTo(uDir2dMcs, espessuraJanelaMcs);
    		GeomPoint2d ptPorta3_2dMcs = ptPorta2_2dMcs.otherMoveTo(nDir2dMcs, larguraJanelaMcs);

    		/* OSMODE */

    		//Endpoint
    		//lsPtEndpoint.add(ptPorta0_2dMcs);
    		lsPtEndpoint.add(ptPorta1_2dMcs);
    		lsPtEndpoint.add(ptPorta2_2dMcs);
    		lsPtEndpoint.add(ptPorta3_2dMcs);
    	}
    	else if(direcao == AppDefs.WINDOWDIR_PT1) {
            //DOOR_LINES
            //
    		GeomPoint2d ptPorta0_2dMcs = new GeomPoint2d(pt1_right_2dMcs); 
    		GeomPoint2d ptPorta1_2dMcs = ptPorta0_2dMcs.otherMoveTo(nDir2dMcs, - larguraJanelaMcs);
    		GeomPoint2d ptPorta2_2dMcs = ptPorta1_2dMcs.otherMoveTo(uDir2dMcs, - espessuraJanelaMcs);
    		GeomPoint2d ptPorta3_2dMcs = ptPorta2_2dMcs.otherMoveTo(nDir2dMcs, larguraJanelaMcs);

    		/* OSMODE */

    		//Endpoint
    		//lsPtEndpoint.add(ptPorta0_2dMcs);
    		lsPtEndpoint.add(ptPorta1_2dMcs);
    		lsPtEndpoint.add(ptPorta2_2dMcs);
    		lsPtEndpoint.add(ptPorta3_2dMcs);
    	}
        
    	GeomPoint2d ptResult = GeomUtil.osnap2d(v, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
        double alturaJanelaMcs = this.altura;
        double larguraJanelaMcs = this.largura;
        double alturaPisoMcs = this.alturaPiso;

        double hAlturaJanelaMcs = alturaJanelaMcs / 2.0;
        double hLarguraJanelaMcs = larguraJanelaMcs / 2.0;
		
        double zH = alturaPisoMcs + hLarguraJanelaMcs;
        
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.oParede.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.oParede.getPtF());

        GeomVector2d vDir2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
        GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();

        GeomPoint2d ptIns2dMcs = ptI2dMcs.otherMoveTo(uDir2dMcs, this.dist);

		GeomPoint2d ptMid2dMcs = ptIns2dMcs.otherMoveTo(uDir2dMcs, hLarguraJanelaMcs);

		GeomPoint3d ptResult = new GeomPoint3d(ptMid2dMcs, zH);
		return ptResult;
	}

	/* Getters/Setters */

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

	public CadParede getoParede() {
		return oParede;
	}

	public void setoParede(CadParede oParede) {
		this.oParede = oParede;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public int getDirecao() {
		return direcao;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}

	public double getEspessura() {
		return espessura;
	}

	public void setEspessura(double espessura) {
		this.espessura = espessura;
	}

	public CadAcabamentoJanelaDef getoAcabamento() {
		return oAcabamento;
	}

	public void setoAcabamento(CadAcabamentoJanelaDef oAcabamento) {
		this.oAcabamento = oAcabamento;
	}

	public double getAlturaPiso() {
		return alturaPiso;
	}

	public void setAlturaPiso(double alturaPiso) {
		this.alturaPiso = alturaPiso;
	}

}
