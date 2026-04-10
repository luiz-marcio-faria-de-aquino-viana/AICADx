/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAlinhamentoEstacaDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/09/2025
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

package br.com.tlmv.aicadxmod.drenagem.cad;

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
import br.com.tlmv.aicadxapp.cad.ICadEntity;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAlinhamentoEstacaDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAlinhamentoEstacaPointDrenagemODataRecord;

public class CadAlinhamentoEstacaDrenagem extends CadEntity 
{
//Private
	private CadCaixaInspecaoDrenagem oCIRaiz = null;
	private CadCaixaInspecaoDrenagem oCIFinish = null;
	private int numeroCIRaiz = AppDefs.NULL_INT;
	private int numeroCIFinish = AppDefs.NULL_INT;
	private boolean bEstacaDireita = true;
	private boolean bEstacaEsquerda = false;
	private int numeroInicialEstaca = AppDefs.NULL_INT;

	private ArrayList<CadAlinhamentoEstacaPointDrenagemOData> lsPts = null;
	
    //DIST_ALINHAMENTO
	private double distAlinhamentoEstacaEstaca = AppDefs.DEF_DEFAULT_DRENAGEM_DISTALINHAMENTOESTACAESTACA;    
    
    //FONT_SIZE
    private double fontSzMili = AppDefs.FONTSZ_SMALL;
    
    //POINT_SIZE
    private double ptSzMili = AppDefs.POINT_SIZE;

    /* Methodes */

	private int calculaNumEstaca(ArrayList<CadAlinhamentoEstacaPointDrenagemOData> lsPts)
	{
		int n = this.numeroInicialEstaca - 1;
		for(CadAlinhamentoEstacaPointDrenagemOData oPt : lsPts) {
			if( oPt.isEstaca() )
				n += 1;
		}
		return n;
	}
	
	private double calculaPontosEntreCaixasInspecaoSobreEixo(ArrayList<CadAlinhamentoEstacaPointDrenagemOData> lsPts, CadCaixaInspecaoDrenagem oCIInicio, CadCaixaInspecaoDrenagem oCIFinal, double dResto)
	{
		CadDocumentDef doc = this.getDocument();
		
		String cadRefEntityId = Integer.toString( this.getObjectId() );
		
		GeomPoint3d ptCIInicio = new GeomPoint3d( oCIInicio.getPtIns() );
		GeomPoint3d ptCIFinal = new GeomPoint3d( oCIFinal.getPtIns() );
		
		GeomVector3d vDir = new GeomVector3d(ptCIInicio, ptCIFinal);

		double d = vDir.mod();
		if(d < dResto) {
			dResto = dResto - d;
			return dResto;
		}
		
		int numEstaca = this.calculaNumEstaca( this.lsPts );
		
		// PONTO_PRIMEIRA_ESTACA (OU CI ATUAL)
		//
		GeomPoint3d ptAtual = ptCIInicio.otherMoveTo(vDir, dResto);
		boolean bCI = false;
		boolean bEstaca = true;
		if(dResto < AppDefs.MATHPREC_MIN) {
			bCI = true;
		}

		numEstaca += 1;
		CadAlinhamentoEstacaPointDrenagemOData oPtAtual = new CadAlinhamentoEstacaPointDrenagemOData(doc);
		
		oPtAtual.init(
			cadRefEntityId,
		    ptAtual,
		    vDir,
		    vDir,
		    numEstaca,
		    0.0,
		    bCI,
		    bEstaca);
		oPtAtual.debug(AppDefs.DEBUG_LEVEL32);
		
		this.lsPts.add(oPtAtual);		

		d = d - dResto;
		
		// PONTOS_ESTACAS_INTERMEDIARIAS
		//
		int n = (int)Math.floor(d / this.distAlinhamentoEstacaEstaca);
		for(int i = 0; i < n; i++) {
			GeomPoint3d ptProximo = ptAtual.otherMoveTo(vDir, this.distAlinhamentoEstacaEstaca);

			bCI = false;
			bEstaca = true;

			numEstaca += 1;
			CadAlinhamentoEstacaPointDrenagemOData oPtProximo = new CadAlinhamentoEstacaPointDrenagemOData(doc);
			
			oPtProximo.init(
				cadRefEntityId,
			    ptProximo,
			    vDir,
			    vDir,
			    numEstaca,
			    0.0,
			    bCI,
			    bEstaca);
			oPtProximo.debug(AppDefs.DEBUG_LEVEL32);
			
			this.lsPts.add(oPtProximo);		

			d = d - dResto;
			
			ptAtual = ptProximo;
		}
		
		// PONTO_PROXIMA_CI
		//
		bCI = true;
		bEstaca = false;
		double dCIFinal = ptAtual.distTo(ptCIFinal);
		double dFinal = this.distAlinhamentoEstacaEstaca - dCIFinal;
		if(dFinal < AppDefs.MATHPREC_MIN) {
			dFinal = 0.0;
			bEstaca = true;
			numEstaca += 1;
			dCIFinal = 0.0;
		}
		
		CadCaixaInspecaoDrenagem oCIProximo = oCIFinal.getProximo();
		if(oCIProximo == null) {
			CadAlinhamentoEstacaPointDrenagemOData oPtFinal = new CadAlinhamentoEstacaPointDrenagemOData(doc);
			
			oPtFinal.init(
				cadRefEntityId,
				ptCIFinal,
			    vDir,
			    vDir,
			    numEstaca,
			    dCIFinal,
			    bCI,
			    bEstaca);
			oPtFinal.debug(AppDefs.DEBUG_LEVEL32);
			
			this.lsPts.add(oPtFinal);			
			
			oCIFinal.setEstaca(numEstaca, dCIFinal);
		}
		else {
			GeomPoint3d ptCIProximo = new GeomPoint3d( oCIProximo.getPtIns() );
			GeomVector3d vDirProximo = new GeomVector3d(ptCIFinal, ptCIProximo);
			
			CadAlinhamentoEstacaPointDrenagemOData oPtFinal = new CadAlinhamentoEstacaPointDrenagemOData(doc);
			
			oPtFinal.init(
				cadRefEntityId,
				ptCIFinal,
				vDir,
				vDirProximo,
			    numEstaca,
			    0.0,
			    bCI,
			    bEstaca);
			oPtFinal.debug(AppDefs.DEBUG_LEVEL32);
			
			this.lsPts.add(oPtFinal);			
			
			oCIFinal.setEstaca(numEstaca, dCIFinal);
		}
		return dFinal;
	}
    
    private void calculaPontosSobreEixo()
    {
	    this.lsPts = new ArrayList<CadAlinhamentoEstacaPointDrenagemOData>();
	    
    	CadCaixaInspecaoDrenagem oCIAtual = this.oCIRaiz;
    	oCIAtual.setEstaca(0, 0.0);
    	
    	double dResto = 0.0;

    	CadCaixaInspecaoDrenagem oCIProxima = oCIAtual.getProximo();
    	while(oCIProxima != null) {
    		int numeroCIAtual = oCIAtual.getObjectId();
    		if(numeroCIAtual == this.numeroCIFinish) break;

    		dResto = calculaPontosEntreCaixasInspecaoSobreEixo(this.lsPts, oCIAtual, oCIProxima, dResto);
    		
    		oCIAtual = oCIProxima;
        	oCIProxima = oCIAtual.getProximo();
    	}
    }
    
//Public

    public CadAlinhamentoEstacaDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
    public void init(CadCaixaInspecaoDrenagem oCIRaiz, CadCaixaInspecaoDrenagem oCIFinish, int numeroInicialEstacao) 
	{
		this.oCIRaiz = oCIRaiz;
		this.oCIFinish = oCIFinish;
		this.numeroCIRaiz = oCIRaiz.getNumeroCI(); 
		this.numeroCIFinish = oCIFinish.getNumeroCI(); 
		this.bEstacaDireita = true;
		this.bEstacaEsquerda = false;
		this.numeroInicialEstaca = numeroInicialEstacao;
		
		this.calculaPontosSobreEixo();
	}
    
	@Override
	public void init(ICadObject o) {
		CadAlinhamentoEstacaDrenagem other = (CadAlinhamentoEstacaDrenagem)o; 
		this.init(other.oCIRaiz, other.oCIFinish, other.getNumeroInicialEstaca());
	}
	
	/* LISTA_ITENS */
	
	public synchronized ArrayList<CadAlinhamentoEstacaPointDrenagemOData> getLsItem()
	{
		return this.lsPts;
	}
	
	public synchronized int getSzLsItem()
	{
		int sz = this.lsPts.size();
		return sz;
	}
	
	public synchronized CadAlinhamentoEstacaPointDrenagemOData getItemAt(int pos)
	{
		int sz = this.lsPts.size();
		if(pos < sz) {
			CadAlinhamentoEstacaPointDrenagemOData o = this.lsPts.get(pos);
			return o;
		}
		return null;
	}

	/* CREATE */
	
	public static CadAlinhamentoEstacaDrenagem create(
		CadBlockDef oBlkDef, 			
		CadLayerDef oLayer,
		CadLevel oLevel,
		CadCaixaInspecaoDrenagem oCIRaiz,
		CadCaixaInspecaoDrenagem oCIFinish,
		int numeroInicialEstaca)
	{
    	CadAlinhamentoEstacaDrenagem o = new CadAlinhamentoEstacaDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(oCIRaiz, oCIFinish, numeroInicialEstaca);
    	return o;
    }
	
	public static CadAlinhamentoEstacaDrenagem create(
		CadBlockDef oBlkDef, 			
		CadLayerDef oLayer,
		CadLevel oLevel,
		CadCaixaInspecaoDrenagem oCIRaiz,
		CadCaixaInspecaoDrenagem oCIFinish,
		int numeroInicialEstaca,
		boolean bLocked)
	{
    	CadAlinhamentoEstacaDrenagem o = new CadAlinhamentoEstacaDrenagem(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(oCIRaiz, oCIFinish, numeroInicialEstaca);
    	return o;
    }
	
	public static CadAlinhamentoEstacaDrenagem create(CadAlinhamentoEstacaDrenagem o)
	{
    	CadAlinhamentoEstacaDrenagem other = new CadAlinhamentoEstacaDrenagem(o.getBlkDef(), o.getLayer(), o.getLevel(), 0.0, false);
    	other.init(o);
    	return other;
    }
	
	public static CadAlinhamentoEstacaDrenagem create(CadBlockDef blkDef, CadAlinhamentoEstacaDrenagem o)
	{
    	CadAlinhamentoEstacaDrenagem other = new CadAlinhamentoEstacaDrenagem(blkDef, o.getLayer(), o.getLevel(), 0.0, false);
    	other.init(o);
    	return other;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadAlinhamentoEstacaDrenagem duplicate()
	{
		CadAlinhamentoEstacaDrenagem other = CadAlinhamentoEstacaDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadAlinhamentoEstacaDrenagem duplicate(CadBlockDef blkDef)
	{
		CadAlinhamentoEstacaDrenagem other = CadAlinhamentoEstacaDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadAlinhamentoEstacaDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return this;
	}

	@Override
	public CadAlinhamentoEstacaDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
    	return this;
	}
	
	@Override
	public CadAlinhamentoEstacaDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadAlinhamentoEstacaDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    	
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		String str = String.format(
			"PtIns:%s;" +
			"Perfil:%s;" +
			"QtdCaixas:%s;");
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

	public void redraw2d_linhaEixo(ICadViewBase v, double sclFact, Graphics g)
	{
		BorderStrokeVO oBorderStroke1 = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_CENTER];
		Stroke b1 = oBorderStroke1.getLtype();

		BorderStrokeVO oBorderStroke2 = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		Stroke b2 = oBorderStroke2.getLtype();
		
	    double fontSz = fontSzMili * sclFact;
	    double lineSz = 1.5 * fontSz;

	    double ptSz = ptSzMili;

    	if(this.lsPts == null) return;

        int sz = this.lsPts.size();
        if(sz > 0) {
        	CadAlinhamentoEstacaPointDrenagemOData oPtAtual = this.lsPts.get(0);
        	CadAlinhamentoEstacaPointDrenagemOData oPtProximo = null;
	        for(int i = 1; i < sz; i++) {
	        	oPtProximo = this.lsPts.get(i);

	        	// LINHA-EIXO
	        	//
	        	GeomPoint2d ptAtual = new GeomPoint2d( oPtAtual.getPtEixo() );
	        	GeomPoint2d ptProximo = new GeomPoint2d( oPtProximo.getPtEixo() );
	        	
	    		Stroke oldltype = GeomUtil.setLtype(g, b1);
				
	        	DrawUtil.drawLineMcs(v, ptAtual, ptProximo, g);

	    		oldltype = GeomUtil.setLtype(g, b2);
				
	        	// LINHA-ESTACA (DIREITA)
	        	//
	            if( this.bEstacaDireita ) {
		    		GeomPoint2d ptAtualDireita = new GeomPoint2d( oPtAtual.getPtEstacaDireita() );
		    		GeomPoint2d ptProximoDireita = new GeomPoint2d( oPtProximo.getPtEstacaDireita() );
		        	
		        	DrawUtil.drawLineMcs(v, ptAtualDireita, ptProximoDireita, g);
		            
		        	DrawUtil.drawPointMcs(v, ptAtualDireita, ptSz, AppDefs.POINT_TYPE_X, g);
	
		        	if( oPtAtual.isEstaca() ) {
			        	GeomVector2d vTextDireita = new GeomVector2d(ptAtual, ptAtualDireita);	        	
			        	GeomPoint2d ptTextDireita = ptAtualDireita.otherMoveTo(vTextDireita, lineSz);
			        	
			        	String strEstacaDireita = String.format("%s+0.00", oPtAtual.getNumEstaca()); 
			            DrawUtil.drawTextMcs(v, strEstacaDireita, ptTextDireita, fontSz, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		        	}
	            }
	            
	        	// LINHA-ESTACA (ESQUERDA)
	        	//
	            if( this.bEstacaEsquerda ) {
		        	GeomPoint2d ptAtualEsquerda = new GeomPoint2d( oPtAtual.getPtEstacaEsquerda() );
		        	GeomPoint2d ptProximoEsquerda = new GeomPoint2d( oPtProximo.getPtEstacaEsquerda() );
		        	
		        	DrawUtil.drawLineMcs(v, ptAtualEsquerda, ptProximoEsquerda, g);
	
		        	DrawUtil.drawPointMcs(v, ptProximoEsquerda, ptSz, AppDefs.POINT_TYPE_X, g);
	
		        	if( oPtAtual.isEstaca() ) {
			        	GeomVector2d vTextEsquerda = new GeomVector2d(ptAtual, ptAtualEsquerda);
			        	GeomPoint2d ptTextEsquerda = ptAtualEsquerda.otherMoveTo(vTextEsquerda, lineSz);
			        	
			        	String strEstacaEsquerda = String.format("%s+0.00", oPtAtual.getNumEstaca()); 
			            DrawUtil.drawTextMcs(v, strEstacaEsquerda, ptTextEsquerda, fontSz, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		        	}
	            }
	            
	        	GeomUtil.setLtype(g, oldltype);
	        	
	        	oPtAtual = oPtProximo;
	        }
        }        
	}
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {		
    	if( !this.isVisible() ) return;
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
        
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
        
        CadCaixaInspecaoDrenagem oCI = this.oCIRaiz;
        
        GeomPoint2d ptIns = new GeomPoint2d( oCI.getPtIns() );

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
		        	/* nothing todo */
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	/* nothing todo */
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	/* nothing todo */
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	/* nothing todo */
		        }
	        }
        }

        this.redraw2d_linhaEixo(v, sclFact, g);
        
        GeomUtil.setColor(g, oldcol);

        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */
	
	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
    	if(this.isSelected()) return true;

		if(pt2dMcs == null) return false;
		
    	//TODO:
        
        return this.isSelected();
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

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();
    	lsPtNodepoint.add(new GeomPoint3d(0.0, 0.0, 0.0));    	
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return null;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();
    	lsPtNodepoint.add(new GeomPoint3d(0.0, 0.0, 0.0));    	
    	return lsPtNodepoint;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(0.0, 0.0, 0.0);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	public synchronized void loadAllItens(ArrayList<BaseObjectRecord> lsItens)
	{
		this.lsPts = new ArrayList<CadAlinhamentoEstacaPointDrenagemOData>();
		
		for(BaseObjectRecord obj : lsItens) {
			CadAlinhamentoEstacaPointDrenagemODataRecord oRec = (CadAlinhamentoEstacaPointDrenagemODataRecord)obj;

		    GeomPoint3d ptEixo = new GeomPoint3d(
		    	oRec.getPtEixoX(), 
		    	oRec.getPtEixoY(), 
		    	oRec.getPtEixoZ() );

        	GeomPoint3d ptEstacaDireita = new GeomPoint3d(
		    	oRec.getPtEstacaDireitaX(), 
		    	oRec.getPtEstacaDireitaY(), 
		    	oRec.getPtEstacaDireitaZ() );
        	
        	GeomPoint3d ptEstacaEsquerda = new GeomPoint3d(
		    	oRec.getPtEstacaEsquerdaX(), 
		    	oRec.getPtEstacaEsquerdaY(), 
		    	oRec.getPtEstacaEsquerdaZ() );
		    
		    GeomVector3d vDirAtual = new GeomVector3d(
		    	oRec.getPtDirAtualXI(), 
		    	oRec.getPtDirAtualYI(), 
		    	oRec.getPtDirAtualZI(), 
		    	oRec.getPtDirAtualXF(), 
		    	oRec.getPtDirAtualYF(), 
		    	oRec.getPtDirAtualZF() ); 
		    
		    GeomVector3d vDirProximo = new GeomVector3d(
		    	oRec.getPtDirProximoXI(), 
		    	oRec.getPtDirProximoYI(), 
		    	oRec.getPtDirProximoZI(), 
		    	oRec.getPtDirProximoXF(), 
		    	oRec.getPtDirProximoYF(), 
		    	oRec.getPtDirProximoZF() ); 

		    boolean bCI = AppDefs.DEF_VALUES_SIM.equals( oRec.getIsCI() );

		    boolean bEstaca = AppDefs.DEF_VALUES_SIM.equals( oRec.getIsEstaca() );

		    CadAlinhamentoEstacaPointDrenagemOData oPt = CadAlinhamentoEstacaPointDrenagemOData.create(
	    		this.getBlkDef(),
	    		oRec.getCadRefEntityId(),
		    	ptEixo,
	        	ptEstacaDireita,
	        	ptEstacaEsquerda,
		    	vDirAtual,
		    	vDirProximo,
			    oRec.getNumEstaca(),
			    oRec.getDistancia(),
		    	bCI,
		    	bEstaca);
		    oPt.setObjectId( oRec.getObjectId() );
		    
			this.lsPts.add(oPt);
		}
	}

	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lsdata(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odDao = dao.createODataDao(AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA); 

		String strCadRefEntityId = Integer.toString(this.getObjectId());
		
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			CadAlinhamentoEstacaPointDrenagemOData oItem = (CadAlinhamentoEstacaPointDrenagemOData)this.lsPts.get(i);
			oItem.setCadRefEntityId(strCadRefEntityId);
			oItem.setObjVer(objVer);

			GeomPoint3d ptEixo = oItem.getPtEixo();
			
			GeomPoint3d ptEstacaDireita = oItem.getPtEstacaDireita();
			
			GeomPoint3d ptEstacaEsquerda = oItem.getPtEstacaEsquerda();
			
			GeomVector3d vDirAtual = oItem.getVDirAtual();
			
			GeomVector3d vDirProximo = oItem.getVDirProximo();
			
			Object[] arrVal = {
				new Integer( oItem.getAlinhamentoEstacaDrenagemId() ),
				//
				new Double( ptEixo.getX() ),
				new Double( ptEixo.getY() ),
				new Double( ptEixo.getZ() ),
				//
				new Double( ptEstacaDireita.getX() ),
				new Double( ptEstacaDireita.getY() ),
				new Double( ptEstacaDireita.getZ() ),
				//
				new Double( ptEstacaDireita.getX() ),
				new Double( ptEstacaDireita.getY() ),
				new Double( ptEstacaDireita.getZ() ),
				//
				new Double( ptEstacaEsquerda.getX() ),
				new Double( ptEstacaEsquerda.getY() ),
				new Double( ptEstacaEsquerda.getZ() ),
				//
				new Double( ptEstacaEsquerda.getX() ),
				new Double( ptEstacaEsquerda.getY() ),
				new Double( ptEstacaEsquerda.getZ() ),
				//
				new Double( vDirAtual.getXI() ),
				new Double( vDirAtual.getYI() ),
				new Double( vDirAtual.getZI() ),
				//
				new Double( vDirAtual.getXF() ),
				new Double( vDirAtual.getYF() ),
				new Double( vDirAtual.getZF() ),
				//
				new Double( vDirProximo.getXI() ),
				new Double( vDirProximo.getYI() ),
				new Double( vDirProximo.getZI() ),
				//
				new Double( vDirProximo.getXF() ),
				new Double( vDirProximo.getYF() ),
				new Double( vDirProximo.getZF() ),
				//
				new Integer( oItem.getNumEstaca() ),
				new Double( oItem.getDistancia() ),
				new Boolean( oItem.isCI() ),
				new Boolean( oItem.isEstaca() )
			};
			
			CadAlinhamentoEstacaPointDrenagemODataRecord odataRec = new CadAlinhamentoEstacaPointDrenagemODataRecord(strCadRefEntityId, oItem);
			int rscode = odDao.insertOrUpdate(
				objVer,
				schemaName,
				odataRec, 
				arrVal );
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new Integer( numeroCIRaiz ),
			new Integer( numeroCIFinish ),
			new Boolean( bEstacaDireita ),
			new Boolean( bEstacaEsquerda ),
			new Integer( numeroInicialEstaca )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadAlinhamentoEstacaDrenagemRecord entRec = new CadAlinhamentoEstacaDrenagemRecord(this); 
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
		
		bResult = this.save_lsdata(objVer, dao, schemaName, doc);
		if( !bResult ) return false;

		return bResult;
	}
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
    	ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
    	if(this.isDeleted()) return lsDxfCadEntity;
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		lsDxfCadEntity.addAll( lsCadEntity2d );

		ArrayList<DxfCadEntity> lsCadEntity3d = toDxfR12_view3d();
		lsDxfCadEntity.addAll( lsCadEntity3d );
		
		return lsDxfCadEntity;
	}

	/* DXFR12_VIEW2D */
	
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsEntity2d = new ArrayList<DxfCadEntity>(); 
    			
		CadDocumentDef doc = this.getDocument();

		LayerTable tbl = doc.getLayerTable();
		
        CadLayerDef oLayer_alinhamento = tbl.getLayerDefByRef(AppDefs.LAYER_RPD_ALINHAMENTO);
        
        double sclFact = AppDefs.MCSPLAN_SCALEFACTOR;

	    double fontSz = fontSzMili * sclFact;
	    double lineSz = 1.5 * fontSz;

	    double ptSz = ptSzMili;

    	if(this.lsPts == null) return lsEntity2d;

        int sz = this.lsPts.size();
        if(sz > 0) {
        	CadAlinhamentoEstacaPointDrenagemOData oPtAtual = this.lsPts.get(0);
        	CadAlinhamentoEstacaPointDrenagemOData oPtProximo = null;
	        for(int i = 1; i < sz; i++) {
	        	oPtProximo = this.lsPts.get(i);

	        	// LINHA-EIXO
	        	//
	        	GeomPoint2d ptAtual = new GeomPoint2d( oPtAtual.getPtEixo() );
	        	GeomPoint2d ptProximo = new GeomPoint2d( oPtProximo.getPtEixo() );
	        	
	        	lsEntity2d.addAll( DxfUtil.toDxfLine(oLayer_alinhamento, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_CENTER, ptAtual, ptProximo) );
				
	        	// LINHA-ESTACA (DIREITA)
	        	//
	            if( this.bEstacaDireita ) {
		    		GeomPoint2d ptAtualDireita = new GeomPoint2d( oPtAtual.getPtEstacaDireita() );
		    		GeomPoint2d ptProximoDireita = new GeomPoint2d( oPtProximo.getPtEstacaDireita() );
		        	
		        	lsEntity2d.addAll( DxfUtil.toDxfLine(oLayer_alinhamento, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_HIDDEN, ptAtualDireita, ptProximoDireita) );
		            
		        	lsEntity2d.addAll( DxfUtil.toDxfPoint(oLayer_alinhamento, ptAtualDireita) );
		        	
		        	if( oPtAtual.isEstaca() ) {
			        	GeomVector2d vTextDireita = new GeomVector2d(ptAtual, ptAtualDireita);	        	
			        	GeomPoint2d ptTextDireita = ptAtualDireita.otherMoveTo(vTextDireita, lineSz);
			        	
			        	String strEstacaDireita = String.format("%s+0.00", oPtAtual.getNumEstaca());
			        	lsEntity2d.addAll( DxfUtil.toDxfText(
		        			oLayer_alinhamento, 
		        			strEstacaDireita, 
		        			ptTextDireita, 
		        			fontSz, 
		        			0.0, 
		        			AppDefs.HORIZALIGN_CENTER, 
		        			AppDefs.VERTALIGN_MIDDLE) );
		        	}
	            }
	            
	        	// LINHA-ESTACA (ESQUERDA)
	        	//
	            if( this.bEstacaEsquerda ) {
		        	GeomPoint2d ptAtualEsquerda = new GeomPoint2d( oPtAtual.getPtEstacaEsquerda() );
		        	GeomPoint2d ptProximoEsquerda = new GeomPoint2d( oPtProximo.getPtEstacaEsquerda() );
		        	
		        	lsEntity2d.addAll( DxfUtil.toDxfLine(oLayer_alinhamento, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_HIDDEN, ptAtualEsquerda, ptProximoEsquerda) );
		            
		        	lsEntity2d.addAll( DxfUtil.toDxfPoint(oLayer_alinhamento, ptAtualEsquerda) );
		        	
		        	if( oPtAtual.isEstaca() ) {
			        	GeomVector2d vTextEsquerda = new GeomVector2d(ptAtual, ptAtualEsquerda);
			        	GeomPoint2d ptTextEsquerda = ptAtualEsquerda.otherMoveTo(vTextEsquerda, lineSz);
			        	
			        	String strEstacaEsquerda = String.format("%s+0.00", oPtAtual.getNumEstaca()); 
			        	lsEntity2d.addAll( DxfUtil.toDxfText(
		        			oLayer_alinhamento, 
		        			strEstacaEsquerda, 
		        			ptTextEsquerda, 
		        			fontSz, 
		        			0.0, 
		        			AppDefs.HORIZALIGN_CENTER, 
		        			AppDefs.VERTALIGN_MIDDLE) );
		        	}
	            }
	        	
	        	oPtAtual = oPtProximo;
	        }
        }        
        
    	return lsEntity2d;
	}

	/* DXFR12_VIEW3D */
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 
        return lsCadEntity3d;
	}

    /* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomPoint3d ptMin = new GeomPoint3d(this.oCIRaiz.getPtIns()); 
		GeomPoint3d ptMax = new GeomPoint3d(this.oCIFinish.getPtIns());
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin, ptMax); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint2d ptMin = new GeomPoint2d(this.oCIRaiz.getPtIns()); 
		GeomPoint2d ptMax = new GeomPoint2d(this.oCIFinish.getPtIns());
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin, ptMax); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString();
		return searchString;
	}

	public int getNumeroCIRaiz() {
		return numeroCIRaiz;
	}

	public void setNumeroCIRaiz(int numeroCIRaiz) {
		this.numeroCIRaiz = numeroCIRaiz;
	}

	public CadCaixaInspecaoDrenagem getCIRaiz() {
		return oCIRaiz;
	}

	public void setCIRaiz(CadCaixaInspecaoDrenagem oCIRaiz) {
		this.oCIRaiz = oCIRaiz;
	}

	public CadCaixaInspecaoDrenagem getCIFinish() {
		return oCIFinish;
	}

	public void setCIFinish(CadCaixaInspecaoDrenagem oCIFinish) {
		this.oCIFinish = oCIFinish;
	}

	public boolean isEstacaDireita() {
		return bEstacaDireita;
	}

	public void setEstacaDireita(boolean bEstacaDireita) {
		this.bEstacaDireita = bEstacaDireita;
	}

	public boolean isEstacaEsquerda() {
		return bEstacaEsquerda;
	}

	public void setEstacaEsquerda(boolean bEstacaEsquerda) {
		this.bEstacaEsquerda = bEstacaEsquerda;
	}

	public int getNumeroInicialEstaca() {
		return numeroInicialEstaca;
	}

	public void setNumeroInicialEstaca(int numeroInicialEstaca) {
		this.numeroInicialEstaca = numeroInicialEstaca;
	}

}
