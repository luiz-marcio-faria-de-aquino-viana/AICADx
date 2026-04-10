/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadParede.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxmod.arquitetura.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomSection3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadParedePointRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadParedeRecord;

public class CadParede extends CadEntity 
{
//Private
    private int tipo;
    private double altura;
    private double largura; 
    private GeomPoint3d ptI;
    private GeomPoint3d ptF;
    private GeomSection3d secao;
    //private ArrayList<GeomPoint3d> lsPtsSecao;
    //
    private ArrayList<CadAcabamentoParedeDef> lsAcabamento = null;
    private ArrayList<CadParede> lsParede = null;
    
//Public

    public CadParede(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_BIMPAREDE, oBlkDef, oLayer, oLevel, zLevel, bLocked);
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
		//PROPRIEDADES: TIPO / ALTURA / LARGURA
		//
		this.tipo = tipo;
	    this.altura = altura;
	    this.largura = largura; 

		//LOCATION: PTI / PTF
		//
		this.ptI = new GeomPoint3d(xI, yI, zI);
    	this.ptF = new GeomPoint3d(xF, yF, zF);

		//WALL_SECTION: LS_PTS_SECAO
		//
    	this.secao = new GeomSection3d();
    	//
    	GeomPoint3d ptBaseI = new GeomPoint3d(xI, yI, zI);
    	GeomPoint3d ptBaseF = new GeomPoint3d(xF, yF, zF);
    	GeomPoint3d ptTopI = new GeomPoint3d(xI, yI, zI + altura);
    	GeomPoint3d ptTopF = new GeomPoint3d(xF, yF, zF + altura);
    	//
    	this.secao = new GeomSection3d();
    	this.secao.insert(ptBaseI);
    	this.secao.insert(ptBaseF);
    	this.secao.insert(ptTopF);
    	this.secao.insert(ptTopI);

    	//WALL_FINISHING: LS_ACABAMENTO
    	//
        this.lsAcabamento = new ArrayList<CadAcabamentoParedeDef>();

    	//WALL_CONNECTED: LS_PAREDE
    	//
        this.lsParede = new ArrayList<CadParede>();
    }
	
	@Override
	public void init(ICadObject o) {
		CadParede other = (CadParede)o; 
	
	    int tmpTipo = other.tipo;
	    double tmpAltura = other.altura;
	    double tmpLargura = other.largura; 
		GeomPoint3d ptTmpPtI = other.ptI;
		GeomPoint3d ptTmpPtF = other.ptF;
		
		this.init(
			tmpTipo,
			tmpAltura,
			tmpLargura, 
			ptTmpPtI.getX(), 
			ptTmpPtI.getY(), 
			ptTmpPtI.getZ(), 
			ptTmpPtF.getX(), 
			ptTmpPtF.getY(), 
			ptTmpPtF.getZ());
		
		//WALL_SECTION: LS_PTS_SECAO
		//
    	this.secao = new GeomSection3d(other.secao);
		
    	//WALL_CONNECTED: LS_PAREDE
    	//
		for(CadAcabamentoParedeDef oAcabamentoOrig : other.lsAcabamento) {
			CadAcabamentoParedeDef oAcabamento2Dest = new CadAcabamentoParedeDef(oAcabamentoOrig); 		
			this.addAcabamentoDef(oAcabamento2Dest);
		}
	}
	
	/* CREATE */
	
	public static CadParede create(
		CadBlockDef oBlkDef,
		CadLayerDef oLayer, 
		CadLevel oLevel,
		int tipo,
	    double altura,
	    double largura, 
		GeomPoint2d ptI, 
		GeomPoint2d ptF) 
	{
    	CadParede o = new CadParede(oBlkDef, oLayer, oLevel, 0.0, false);

    	o.init(
		    tipo,
		    altura,
		    largura, 
			ptI, 
			ptF); 
    	return o;
    }
	
	public static CadParede create(
		CadBlockDef oBlkDef,
		CadLayerDef oLayer, 
		CadLevel oLevel,
		int tipo,
	    double altura,
	    double largura, 
		GeomPoint3d ptI, 
		GeomPoint3d ptF) 
	{
    	CadParede o = new CadParede(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
		    tipo,
		    altura,
		    largura, 
			ptI, 
			ptF); 
    	return o;
    }
	
	public static CadParede create(
		CadBlockDef oBlkDef,
		CadLayerDef oLayer, 
		CadLevel oLevel,		
		int tipo,
	    double altura,
	    double largura, 
		double xI, 
		double yI, 
		double zI, 
		double xF, 
		double yF, 
		double zF,
		boolean bLocked) 
	{
    	CadParede o = new CadParede(oBlkDef, oLayer, oLevel, 0.0, bLocked);

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
    	CadParede o = new CadParede(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadParede create(CadBlockDef blkDef, CadParede other) {
    	CadParede o = new CadParede(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }

	/* ADDING_FEATURES */
	
    public void addPtSecao(int idx, GeomPoint3d pt3d)
    {
		this.secao.insert(idx, pt3d);
    }
	
    public void addAbertura(double dist, double larguraBase, double alturaBase, double altura)
    {
		GeomVector3d vIF3d = new GeomVector3d(this.ptI, this.ptF); 
		GeomVector3d uIF3d = vIF3d.otherUnit();

    	this.secao.insert(dist, larguraBase, alturaBase, altura, uIF3d, this.ptI);
    }
	
    public void addAcabamentoDef(CadAcabamentoParedeDef o)
    {
    	this.lsAcabamento.add(o);
    }
	
    public void addParede(CadParede o)
    {
    	this.lsParede.add(o);
    }
	
	/* OPERATIONS */
	
	@Override
	public CadParede duplicate()
	{
		CadParede other = CadParede.create(this);
		
		for(CadAcabamentoParedeDef oAcabamentoOrig : this.lsAcabamento) {
			CadAcabamentoParedeDef oAcabamento2Dest = new CadAcabamentoParedeDef(oAcabamentoOrig); 		
			other.addAcabamentoDef(oAcabamento2Dest);
		}
		
		return other;
	}
	
	@Override
	public CadParede duplicate(CadBlockDef blkDef)
	{
		CadParede other = CadParede.create(blkDef, this);
		
		for(CadAcabamentoParedeDef oAcabamentoOrig : this.lsAcabamento) {
			CadAcabamentoParedeDef oAcabamento2Dest = new CadAcabamentoParedeDef(oAcabamentoOrig); 		
			other.addAcabamentoDef(oAcabamento2Dest);
		}
		
		return other;
	}
	
	@Override
	public CadParede copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadParede other = CadParede.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
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
	
	@Override
	public CadParede scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);

    	ScaleData2dVO oI = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());
		
    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);

    	ScaleData2dVO oF = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

    	return this;
	}
	
	@Override
	public CadParede mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptI = GeomUtil.mirror(this.ptI, ptI2dMcs, ptF2dMcs);
		this.ptF = GeomUtil.mirror(this.ptF, ptI2dMcs, ptF2dMcs);

    	return this;
	}
	
	@Override
	public CadParede offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadParede oParede = copyTo(ptIMcs, ptFMcs);
		return oParede;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ItemDataVO oTipoParede = ListUtil.findItemDataById(Integer.toString(this.tipo), AppDefs.ARR_WALLTYPE);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.add( new ItemDataVO("Tipo", oTipoParede.getDescricao(), false) );
		//
		lsProperty.addAll( this.ptI.toPropertyList("Pt.Inicial", true) );
		lsProperty.addAll( this.ptF.toPropertyList("Pt.Final", true) );
		//
		lsProperty.add( new ItemDataVO("Altura (m)", nf3.format(this.altura), true) );
		lsProperty.add( new ItemDataVO("Largura (m)", nf3.format(this.largura), true) );

		for(CadAcabamentoParedeDef oAcabamento : lsAcabamento) {
			lsProperty.add( new ItemDataVO("Nome", oAcabamento.getNome(), false) );			
			//lsProperty.add( new ItemDataVO("Largura", nf3.format(oAcabamento.getLargura())) );			
		}
		
		for(CadParede oParede : this.lsParede) {
			lsProperty.add( new ItemDataVO("Parede", Integer.toString(oParede.getObjectId()), false) );
			//lsProperty.add( new ItemDataVO("Largura", nf3.format(oParede.getLargura())) );						
		}
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
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
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
    /* DRAWCACHE */

	@Override
	public DrawCache createDrawCache2d() {
		return null;
	}
	
	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		return null;
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
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);
		
		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);

		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.ptF);
        
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
		        	CadParede other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestI2dMcs = new GeomPoint2d(other.ptI);
		        	ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadParede other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadParede other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			            ptDestI2dMcs = new GeomPoint2d(other.ptI);
			            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadParede other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }
	        }
        }
        
        redraw2d_planView(v, ptDestI2dMcs, ptDestF2dMcs, g);
        
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	public void redraw3d_3dView_ORIG20250322(ICadViewBase v, Color c, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, PrepareDrawUtil prep)
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
        prep.addFace(v, this, c, ptBaseI3dMcs_left, ptBaseF3dMcs_left, ptElevF3dMcs_left, ptElevI3dMcs_left, axisZ);
        prep.addFace(v, this, c, ptBaseI3dMcs_right, ptBaseF3dMcs_right, ptElevF3dMcs_right, ptElevI3dMcs_right, axisZ);
	
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
	        prep.addFace(v, this, colorAcabamento, ptBaseI3dMcs_acabamento_left, ptBaseF3dMcs_acabamento_left, ptElevF3dMcs_acabamento_left, ptElevI3dMcs_acabamento_left, axisZ);
	        prep.addFace(v, this, colorAcabamento, ptBaseI3dMcs_acabamento_left, ptBaseF3dMcs_acabamento_left, ptElevF3dMcs_acabamento_left, ptElevI3dMcs_acabamento_left, axisZ);
        	//
	        prep.addFace(v, this, colorAcabamento, ptBaseI3dMcs_left, ptBaseI3dMcs_acabamento_left, ptElevI3dMcs_acabamento_left, ptElevI3dMcs_left, axisZ);
	        prep.addFace(v, this, colorAcabamento, ptBaseF3dMcs_left, ptBaseF3dMcs_acabamento_left, ptElevF3dMcs_acabamento_left, ptElevF3dMcs_left, axisZ);
			
			//DRAW - RIGHT SIDE
	        prep.addFace(v, this, colorAcabamento, ptBaseI3dMcs_acabamento_right, ptBaseF3dMcs_acabamento_right, ptElevF3dMcs_acabamento_right, ptElevI3dMcs_acabamento_right, axisZ);
	        prep.addFace(v, this, colorAcabamento, ptBaseI3dMcs_acabamento_right, ptBaseF3dMcs_acabamento_right, ptElevF3dMcs_acabamento_right, ptElevI3dMcs_acabamento_right, axisZ);
        	//
	        prep.addFace(v, this, colorAcabamento, ptBaseI3dMcs_right, ptBaseI3dMcs_acabamento_right, ptElevI3dMcs_acabamento_right, ptElevI3dMcs_right, axisZ);
	        prep.addFace(v, this, colorAcabamento, ptBaseF3dMcs_right, ptBaseF3dMcs_acabamento_right, ptElevF3dMcs_acabamento_right, ptElevF3dMcs_right, axisZ);
	        
	        //GeomUtil.setColor(g, oldcol);
		}
	}

	public void redraw3d_3dView(ICadViewBase v, Color c, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, PrepareDrawUtil prep)
	{
		GeomVector3d vIF3d = new GeomVector3d(ptI3dMcs, ptF3dMcs); 
		
		GeomVector2d vIF2d = new GeomVector2d( vIF3d );

		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
		
		GeomVector3d axisZ = GeomUtil.axisZ3d();
		
		double hLargura = this.largura / 2.0;
		
		int sz = this.lsAcabamento.size();
		
		/* DRAW_WALL */
		
		GeomSection3d secao_left = new GeomSection3d(this.secao, new GeomVector3d( nIF2d ), hLargura);
		GeomSection3d secao_right = new GeomSection3d(this.secao, new GeomVector3d( nIF2d ), - hLargura);

		if(sz == 0) {
			//DRAW - LEFT/RIGHT SIDE
			prep.addFace(v, this, c, secao_left.getLsPts(), axisZ);
	        prep.addFace(v, this, c, secao_right.getLsPts(), axisZ);

	        prep.addExternalFace(v, this, c, secao_left.getLsPts(), secao_right.getLsPts(), axisZ);
		}
		else {
			/* DRAW_FINISH */
	        double hL = hLargura;
			for(CadAcabamentoParedeDef o : this.lsAcabamento) {
				//int tipoAcabamento = o.getTipo();
				double larguraAcabamento = o.getLargura();
				Color colorAcabamento = o.getColor();
				
				hL = hL + (larguraAcabamento / 2.0);
	
				GeomSection3d secao_acabamento_left = new GeomSection3d(secao_left, new GeomVector3d( nIF2d ), hL);
				GeomSection3d secao_acabamento_right = new GeomSection3d(secao_right, new GeomVector3d( nIF2d ), - hL);
				
				prep.addFace(v, this, colorAcabamento, secao_acabamento_left.getLsPts(), axisZ);
		        prep.addFace(v, this, colorAcabamento, secao_acabamento_right.getLsPts(), axisZ);

		        prep.addExternalFace(v, this, c, secao_acabamento_left.getLsPts(), secao_acabamento_right.getLsPts(), axisZ);
			}
		}
	}
	
	@Override
    public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
    {
    	if( !this.isVisible() ) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        GeomPoint3d ptDestI3dMcs = new GeomPoint3d(this.ptI);
        GeomPoint3d ptDestF3dMcs = new GeomPoint3d(this.ptF);
        
        redraw3d_3dView(v, c, ptDestI3dMcs, ptDestF3dMcs, prep);
    }

    /* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
    	if(this.isSelected()) return true;
    	
    	if(pt2dMcs == null) return false;
		
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
	
	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
	{
		return null;
	}

	/* OSNAP */

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(this.ptI) );
    	lsPtEndpoint.add( new GeomPoint3d(this.ptF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(this.ptI, this.ptF);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptI) );
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, this.ptI, this.ptF);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtEndpoint);
    	lsResult.addAll(lsPtMiddle);
    	return lsResult;
	}

	/* CENTROID */
	
	@Override
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
	
	/* LOAD/SAVE */
	
	public void loadAllItens(ArrayList<BasePointRecord> lsPts)
	{
		this.secao = new GeomSection3d();
		for(BasePointRecord oPtRec : lsPts) {
			GeomPoint3d oPt3d = oPtRec.toGeomPoint3d();
			this.secao.insert(oPt3d);			
		}
		this.createAllDrawCache();
	}
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lspts(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BasePointDao ptDao = dao.createPtDao(AppDefs.OBJTYPE_BIMPAREDE_GEOMPOINT); 
		
		String cadRefEntityId = Integer.toString(this.getObjectId());
		
		ArrayList<GeomPoint3d> lsPts3d = this.secao.getLsPts();
		int szLsPts = lsPts3d.size();
		for(int i = 0; i < szLsPts; i++) {
			GeomPoint3d oPt = lsPts3d.get(i);
			
			BasePointRecord ptRec = new BasePointRecord(cadRefEntityId, objVer, oPt);
			int rscode = ptDao.insertOrUpdate(
				objVer,
				schemaName, 
				CadParedePointRecord.sqlTableName,
				(BasePointRecord) ptRec );
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		int paredeId = this.getObjectId();
		
		Object[] arrVal = {
			new Integer( paredeId ),
			new Integer( tipo ),
			new Double( altura ),
			new Double( largura ), 
			new Double( ptI.getX() ),
			new Double( ptI.getY() ),
			new Double( ptI.getZ() ),
			new Double( ptF.getX() ),
			new Double( ptF.getY() ),
			new Double( ptF.getZ() )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 

		CadParedeRecord entRec = new CadParedeRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );
		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = this.save_entity(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_lspts(objVer, dao, schemaName, doc);
		if( !bResult ) return false;

		return bResult;
	}

	/* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
        GeomPoint3d ptI3dMcs = new GeomPoint3d(this.getPtI());
        GeomPoint3d ptF3dMcs = new GeomPoint3d(this.getPtF());

        GeomVector3d vDir3dMcs = new GeomVector3d(ptI3dMcs, ptF3dMcs); 
        GeomVector3d uDir3dMcs = vDir3dMcs.otherUnit();

        GeomVector3d nDir3dMcs = uDir3dMcs.otherNorm();

        //DOOR_ISSELECTED
        //
        double larguraParedeMcs = this.getLargura();
        
        for(CadAcabamentoParedeDef o : this.getLsAcabamento()) {
        	larguraParedeMcs += 2 * o.getLargura();
        }

        double hLarguraParedeMcs = larguraParedeMcs / 2.0;
        
		GeomPoint3d pt0_3dMcs = new GeomPoint3d(ptI3dMcs);
		GeomPoint3d pt1_3dMcs = new GeomPoint3d(ptF3dMcs);
		
		GeomPoint3d pt0_left_3dMcs = pt0_3dMcs.otherMoveTo(nDir3dMcs, hLarguraParedeMcs);
		GeomPoint3d pt1_left_3dMcs = pt1_3dMcs.otherMoveTo(nDir3dMcs, hLarguraParedeMcs);

		GeomPoint3d pt0_right_3dMcs = pt0_left_3dMcs.otherMoveTo(nDir3dMcs, - hLarguraParedeMcs);
		GeomPoint3d pt1_right_3dMcs = pt1_left_3dMcs.otherMoveTo(nDir3dMcs, - hLarguraParedeMcs);
    	
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();

		lsPts.add(pt0_left_3dMcs);
		lsPts.add(pt1_left_3dMcs);
		lsPts.add(pt0_right_3dMcs);
		lsPts.add(pt1_right_3dMcs);
		
		GeomPoint3d[] arr = GeomUtil.maxMinPointOfArray3d(lsPts);		
		
		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.getPtF());

        GeomVector2d vDir2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
        GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();

        GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();

        //DOOR_ISSELECTED
        //
        double larguraParedeMcs = this.getLargura();
        
        for(CadAcabamentoParedeDef o : this.getLsAcabamento()) {
        	larguraParedeMcs += 2 * o.getLargura();
        }

        double hLarguraParedeMcs = larguraParedeMcs / 2.0;
        
		GeomPoint2d pt0_2dMcs = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d pt1_2dMcs = new GeomPoint2d(ptF2dMcs);
		
		GeomPoint2d pt0_left_2dMcs = pt0_2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);
		GeomPoint2d pt1_left_2dMcs = pt1_2dMcs.otherMoveTo(nDir2dMcs, hLarguraParedeMcs);

		GeomPoint2d pt0_right_2dMcs = pt0_left_2dMcs.otherMoveTo(nDir2dMcs, - hLarguraParedeMcs);
		GeomPoint2d pt1_right_2dMcs = pt1_left_2dMcs.otherMoveTo(nDir2dMcs, - hLarguraParedeMcs);
    	
		ArrayList<GeomPoint2d> lsPts = new ArrayList<GeomPoint2d>();

		lsPts.add(pt0_left_2dMcs);
		lsPts.add(pt1_left_2dMcs);
		lsPts.add(pt0_right_2dMcs);
		lsPts.add(pt1_right_2dMcs);
		
		GeomPoint2d[] arr = GeomUtil.maxMinPointOfArray2d(lsPts);		
		
		GeomPoint2d ptMin2d = new GeomPoint2d(arr[0]);
		GeomPoint2d ptMax2d = new GeomPoint2d(arr[1]);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"TIPO=" + AppDefs.ARR_WALLTYPE[this.tipo].getDescricao() + "^" +
			"LARGURA=" + Double.toString( this.largura ) + "^" +
			"ALTURA=" + Double.toString( this.altura );
		return searchString;
	}

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
	
	public GeomSection3d getSecao() {
		return secao;
	}

	public void setSecao(GeomSection3d secao) {
		this.secao = secao;
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
