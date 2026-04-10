/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPontoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/04/2025
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

import br.com.tlmv.aicadxapp.AppCadMain;
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPontoDrenagemRecord;

public class CadPontoDrenagem extends CadEntity
{
//Private
    private Shape shape = null;
    private GeomPoint3d ptIns = null;
    private double rotate = 0.0;
    //
    private int proxEntId = AppDefs.NULL_INT;
    private CadEntity proxEnt = null;
    //
    private String tipoSecaoTubulacao = DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR;
    private int categoriaTubulacaoId = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
    private String descricaoCategoriaTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
    private int qtdTubulacao = 1;
    private double diametroTubulacaoMeter = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDiamNominalMeter();
    //
    private double largura = 0.0;
    private double altura = 0.0;
    private double profundidade = 0.0;
    //
    private double ct = 0.0;
    private double cb = 0.0;
    private double cotaSaida = 0.0;
    //
    private double declividade = AppDefs.DEF_DEFAULT_DRENAGEM_DECLIVIDADEMINIMA;
    //
    private double comprTubulacao = 0.0;
    private double comprHorizTubulacao = 0.0;
    private double comprVertTubulacao = 0.0;
    
    /* Methodes */
    
    private void reCalcCotaTerreno()
    {
    	double diameterMeter = this.diametroTubulacaoMeter;
    	double radiusMeter = diameterMeter / 2.0;
    	//
    	this.ct = this.ptIns.getZ();
    	this.cb = this.ct + this.profundidade;
    	this.cotaSaida = this.cb + radiusMeter;
    }
    
    private void reCalcComprTubulacao()
    {
    	double angleRad = GeomUtil.convertDegreesToRad(this.rotate); 
    	
    	double xPtIns = this.ptIns.getX();
    	double yPtIns = this.ptIns.getY();
    	double zPtIns = this.cotaSaida;

    	double xPtSaida = xPtIns + this.altura * Math.cos(angleRad);
    	double yPtSaida = yPtIns + this.altura * Math.sin(angleRad);
    	double zPtSaida = zPtIns;

    	GeomPoint3d ptCI = null;
    	double zH = 0.0;

    	if( this.proxEnt.getObjType() == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO ) {
	    	CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)this.proxEnt;
	    	ptCI = new GeomPoint3d(oCI.getPtIns());
	    	zH = oCI.getCotaEntrada();
    	}
    	else if( this.proxEnt.getObjType() == AppDefs.OBJTYPE_MODDRPONTODRENAGEM ) {
	    	CadPontoDrenagem oPonto = (CadPontoDrenagem)this.proxEnt;
	    	ptCI = new GeomPoint3d(oPonto.getPtIns());
	    	zH = oPonto.getCotaSaida();
    	}
    	
    	double xPtCI = ptCI.getX();
    	double yPtCI = ptCI.getY();
    	double zPtCI = zH;
    	
    	GeomPoint3d ptSaida3d = new GeomPoint3d(xPtSaida, yPtSaida, zPtSaida);
    	GeomPoint2d ptSaida2d = new GeomPoint2d(xPtSaida, yPtSaida);

    	GeomPoint3d ptCI3d = new GeomPoint3d(xPtCI, yPtCI, zPtCI);
    	GeomPoint2d ptCI2d = new GeomPoint2d(xPtCI, yPtCI);

        this.comprTubulacao = ptSaida3d.distTo(ptCI3d);
        this.comprHorizTubulacao = ptSaida2d.distTo(ptCI2d);

        double valCos = 0.0; 
        double arcCos = 0.0;
        if(this.comprTubulacao > AppDefs.MATHPREC_MIN) {
        	valCos = this.comprHorizTubulacao / this.comprTubulacao; 
        	arcCos = Math.acos(valCos);
        }
        
    	this.declividade = Math.sin(arcCos);
        this.comprVertTubulacao = this.comprTubulacao * this.declividade;
    }
    
//Public

    public CadPontoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRPONTODRENAGEM, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(
		CadEntity proxEnt, 
		GeomPoint2d pt, 
		double rotate,
		Shape shape,
	    double largura,
	    double altura,
	    double profundidade)
	{
		this.init(
			proxEnt, 
			pt.getX(), 
			pt.getY(), 
			0.0, 
			rotate,
			shape,
			largura,
			altura,
			profundidade);
	}
	
	private void init(
		CadEntity proxEnt, 
		GeomPoint3d pt, 
		double rotate,
		Shape shape,
	    double largura,
	    double altura,
	    double profundidade) 
	{
		this.init(
			proxEnt, 
			pt.getX(), 
			pt.getY(), 
			pt.getZ(), 
			rotate,
			shape,
		    largura,
		    altura,
		    profundidade);
	}

	public void init(
		CadEntity proxEnt, 
		double x, 
		double y, 
		double z, 
		double rotate,
		Shape shape,
	    double largura,
	    double altura,
	    double profundidade) 
	{
		this.proxEnt = proxEnt;
		//
		if(this.proxEnt != null) {
			this.proxEntId = this.proxEnt.getObjectId();
		}
		//
    	this.ptIns = new GeomPoint3d(x, y, z);
    	this.rotate = rotate;
    	this.shape = new Shape(shape);
    	//
    	this.largura = largura;
    	this.altura = altura;
    	this.profundidade = profundidade;
    	//
    	this.reCalcCotaTerreno();
    	this.reCalcComprTubulacao();
    }
	
	@Override
	public void init(ICadObject o) {
		CadPontoDrenagem other = (CadPontoDrenagem)o;
		
		this.init(
			other.proxEnt, 
			other.ptIns,
			other.rotate,
			other.shape, 
			other.largura, 
			other.altura, 
			other.profundidade);
	}

	/* CREATE */
		
	public static CadPontoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		CadEntity proxEnt, 
		GeomPoint2d pt, 
		double rotate,
		Shape shape,
		double largura, 
		double altura,
		double profundidade) 
	{
    	CadPontoDrenagem o = new CadPontoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			proxEnt, 
    		pt, 
    		rotate,
    		shape,
    		largura, 
    		altura,
    		profundidade);
    	return o;
    }
	
	public static CadPontoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		CadEntity proxEnt, 
		GeomPoint3d pt, 
		double rotate,
		Shape shape,
		double largura, 
		double altura,
		double profundidade) 
	{
    	CadPontoDrenagem o = new CadPontoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			proxEnt, 
    		pt, 
    		rotate,
    		shape,
    		largura, 
    		altura,
    		profundidade);
    	return o;
    }

	public static CadPontoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,		
		CadEntity proxEnt, 
		double x, 
		double y, 
		double z, 
		double rotate,
		Shape shape,
		double largura, 
		double altura,
		double profundidade)
	{
    	CadPontoDrenagem o = new CadPontoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			proxEnt, 
    		x, 
    		y, 
    		z, 
    		rotate,
    		shape,
    		largura, 
    		altura,
    		profundidade);
    	return o;
    }

	public static CadPontoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,		
		CadEntity proxEnt, 
		double x, 
		double y, 
		double z, 
		double rotate,
		Shape shape,
		double largura, 
		double altura,
		double profundidade,
		boolean bLocked )
	{
    	CadPontoDrenagem o = new CadPontoDrenagem(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(
			proxEnt, 
    		x, 
    		y, 
    		z, 
    		rotate,
    		shape,
    		largura, 
    		altura,
    		profundidade );
    	return o;
    }
	
	public static CadPontoDrenagem create(CadPontoDrenagem other) {
		CadPontoDrenagem o = new CadPontoDrenagem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadPontoDrenagem create(CadBlockDef blkDef, CadPontoDrenagem other) {
		CadPontoDrenagem o = new CadPontoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPontoDrenagem duplicate()
	{
		CadPontoDrenagem other = CadPontoDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadPontoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadPontoDrenagem other = CadPontoDrenagem.create(blkDef, this);
		return other;
	}

	@Override
	public CadPontoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPontoDrenagem other = CadPontoDrenagem.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadPontoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
    	this.reCalcCotaTerreno();
    	this.reCalcComprTubulacao();
		return this;
	}
	
	@Override
	public CadPontoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
    	this.reCalcCotaTerreno();
    	this.reCalcComprTubulacao();
		return this;
	}
    
	@Override
	public CadPontoDrenagem mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
    	this.reCalcCotaTerreno();
    	this.reCalcComprTubulacao();
		return this;
	}
	
	@Override
	public CadPontoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPontoDrenagem oPoint = copyTo(ptIMcs, ptFMcs);
    	this.reCalcCotaTerreno();
    	this.reCalcComprTubulacao();
		return oPoint;
	}
	
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		int iNumeroCI = this.proxEnt.getObjectId();
		
		String strName = this.shape.getName();
		String strFileName = this.shape.getFileName();
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		//
		lsProperty.add( new ItemDataVO("Rotate", nf3.format(this.rotate), true) );
		//
		lsProperty.add( new ItemDataVO("Numero CI", nf0.format(iNumeroCI), false) );
		//
		lsProperty.add( new ItemDataVO("Categoria Tubulacao", this.descricaoCategoriaTubulacao, false) );
		lsProperty.add( new ItemDataVO("Quantidade Tubulacao", nf3.format(this.qtdTubulacao), false) );
		lsProperty.add( new ItemDataVO("Diametro Tubulacao", nf3.format(this.diametroTubulacaoMeter), false) );
		lsProperty.add( new ItemDataVO("Declividade", nf3.format(this.declividade), false) );
		lsProperty.add( new ItemDataVO("Profundidade", nf3.format(this.profundidade), false) );
		lsProperty.add( new ItemDataVO("ComprTubulacao", nf3.format(this.comprTubulacao), false) );
		lsProperty.add( new ItemDataVO("ComprHorizTubulacao", nf3.format(this.comprHorizTubulacao), false) );
		lsProperty.add( new ItemDataVO("ComprVertTubulacao", nf3.format(this.comprVertTubulacao), false) );
		//
		lsProperty.add( new ItemDataVO("Cota Topo", nf3.format(this.ct), false) );
		lsProperty.add( new ItemDataVO("Cota Fundo", nf3.format(this.cb), false) );
		lsProperty.add( new ItemDataVO("Cota Saida", nf3.format(this.cotaSaida), false) );		
		//
		lsProperty.add( new ItemDataVO("Name", strName, false) );
		lsProperty.add( new ItemDataVO("FileName", strFileName, false) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String strLayerName = this.getLayer().getName();		

		int iNumeroCI = this.proxEnt.getObjectId();
		
		String strName = this.shape.getName();
		String strFileName = this.shape.getFileName();
		
		String str = String.format(
			"ObjectId:%s;" +
			"ObjType:%s;" +
			"Layer:%s;" +
			"NumeroCI:%s;" +
		    "TipoSecaoTubulacao:%s;" +
		    "CategoriaTubulacaoId:%s;" +
			"DescricaoCategoriaTubulacao:%s;" +
			"QtdTubulacao:%s;" +
			"DiametroTubulacao:%s;" +
			"Declividade:%s;" +
			"Profundidade:%s;" +
			"ComprTubulacao:%s;" +
			"ComprHorizTubulacao:%s;" +
			"ComprVertTubulacao:%s;" +
			"CT:%s;" +
			"CB:%s;" +
			"CotaSaida:%s;" +
			"Name:%s;" +
			"FileName:%s;" +
			"PtIns:[X:%s;Y:%s;Z:%s];" +
			"Rotate:%s",
			this.getObjectId(),
			this.getObjType(),
			strLayerName,
			nf0.format(iNumeroCI),
		    this.tipoSecaoTubulacao,
		    nf0.format(this.categoriaTubulacaoId),
			this.descricaoCategoriaTubulacao,
			nf0.format(this.qtdTubulacao),			
			nf3.format(this.diametroTubulacaoMeter),
			nf3.format(this.declividade),
			nf3.format(this.profundidade),
			nf3.format(this.comprTubulacao),
			nf3.format(this.comprHorizTubulacao),
			nf3.format(this.comprVertTubulacao),
			nf3.format(this.ct),
			nf3.format(this.cb),
			nf3.format(this.cotaSaida),
			strName,
			strFileName,
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()), 
			nf6.format(this.rotate) );
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
	
    public void redraw2d_netLink_planView(ICadViewBase v, Color c, GeomPoint2d ptIns2dMcs, double sclFact, Graphics g) 
    {
		AppCadMain cad = AppCadMain.getCad();

		double arrowLengthSz = AppDefs.ARROWLENGTHSZ_SMALL * sclFact;
		double arrowWidthSz = AppDefs.ARROWWIDTHSZ_SMALL * sclFact;
		double arrowPointSz = AppDefs.ARROWPOINTSZ_SMALL * sclFact;
		
    	if(this.proxEnt != null) {
    		CadDocumentDef doc = v.getDoc();
    		
    		LayerTable oTbl = doc.getLayerTable();
    		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_LIGACOES);
    		if( oLayer.isLayerOn() ) {
	    		if(this.proxEnt != null) {
		    		if(this.proxEnt.getObjType() == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
		    			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)this.proxEnt;
		    			
		    			GeomPoint2d ptProximo2dMcs = new GeomPoint2d(oCI.getPtIns());
		    			
		    			GeomVector2d vDir2d = new GeomVector2d(ptIns2dMcs, ptProximo2dMcs);
		    			
		    			GeomVector2d uDir2d = vDir2d.otherUnit(); 

		    			ColorVO c1 = oLayer.getColor();
		    			
			    		Color oldcol1 = GeomUtil.setColor(g, c1.getColor());
	
		    			DrawUtil.drawLineMcs(v, ptIns2dMcs, ptProximo2dMcs, g);

		    			//GeomUtil.setColor(g, Color.GREEN);

		    			DrawUtil.drawArrowMcs(v, ptIns2dMcs, arrowLengthSz, arrowWidthSz, arrowPointSz, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uDir2d, g);
		    			
		    			GeomUtil.setColor(g, oldcol1);
		    		}
		    		else if(this.proxEnt.getObjType() == AppDefs.OBJTYPE_MODDRPONTODRENAGEM) {
		    			CadPontoDrenagem oCI = (CadPontoDrenagem)this.proxEnt;
		    			
		    			GeomPoint2d ptProximo2dMcs = new GeomPoint2d(oCI.getPtIns());
		    			
		    			GeomVector2d vDir2d = new GeomVector2d(ptIns2dMcs, ptProximo2dMcs);
		    			
		    			GeomVector2d uDir2d = vDir2d.otherUnit(); 

		    			ColorVO c1 = oLayer.getColor();
		    			
			    		Color oldcol1 = GeomUtil.setColor(g, c1.getColor());
	
		    			DrawUtil.drawLineMcs(v, ptIns2dMcs, ptProximo2dMcs, g);

		    			//GeomUtil.setColor(g, Color.GREEN);

		    			DrawUtil.drawArrowMcs(v, ptIns2dMcs, arrowLengthSz, arrowWidthSz, arrowPointSz, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uDir2d, g);
		    			
		    			GeomUtil.setColor(g, oldcol1);
		    		}
	    		}
    		}
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

        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.ptIns);
        
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
		        	CadPontoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPontoDrenagem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadPontoDrenagem other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPontoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }
        
    	DrawUtil.drawShape2dMcs(v, ptDest2dMcs, this.shape.getPlanView2d(), sclFact, this.rotate, g);
    	
        this.redraw2d_netLink_planView(v, c, ptDest2dMcs, sclFact, g); 

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
	
	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
    	if( !this.isVisible() ) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

    	GeomShape3d shape3d = this.shape.getModelView3d();
    	
        prep.addShape3dMcs(v, this, c, this.ptIns, shape3d, sclFact, this.rotate, null);
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptPoint2dMcs = new GeomPoint2d(this.ptIns);
        
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
	
	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity)
	{
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
    	GeomPoint3d pt3d = new GeomPoint3d(this.ptIns);
    	
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(pt3d);
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	GeomPoint3d pt3d = new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.ptIns);
    	
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(pt3d);
    	return lsPtNodepoint;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptIns);
		return ptResult;
	}
	
	/* LOAD/SAVE */

	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
	    String shapeName = shape.getName();
	    String shapeFileName = shape.getFileName();
	    double shapeDefaultZ = shape.getDefaultZ();

		GeomPoint3d ptIns = this.getPtIns();

		Object[] arrVal = {
			new String( shapeName ),
			new String( shapeFileName ),
			new Double( shapeDefaultZ ),
			//
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
			new Double( this.rotate ),
	        
	    	//PROPRIEDADES
			new Integer( this.getProxEntId() ),
			new String( this.getTipoSecaoTubulacao() ),
			new Integer( this.getCategoriaTubulacaoId() ),
			new String( this.getDescricaoCategoriaTubulacao() ),
			new Integer( this.getQtdTubulacao() ),
			new Double( this.getDiametroTubulacaoMeter() ),
			new Double( this.getLargura() ),
			new Double( this.getAltura() ),
			new Double( this.getProfundidade() ),
			new Double( this.getCt() ),
			//
			new Double( this.getCb() ),
			new Double( this.getCotaSaida() ),
			new Double( this.getDeclividade() ),
			new Double( this.getComprTubulacao() ),
			new Double( this.getComprHorizTubulacao() ),
			new Double( this.getComprVertTubulacao() )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadPontoDrenagemRecord entRec = new CadPontoDrenagemRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );

		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomDimension3d oDim = new GeomDimension3d(this.ptIns, this.ptIns); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oDim = new GeomDimension2d(this.ptIns, this.ptIns); 
		return oDim;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" + 
			"NOME=" + this.shape.getName();
		return searchString;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	public String getTipoSecaoTubulacao() {
		return tipoSecaoTubulacao;
	}

	public int getCategoriaTubulacaoId() {
		return categoriaTubulacaoId;
	}

	public String getDescricaoCategoriaTubulacao() {
		return descricaoCategoriaTubulacao;
	}

	public int getQtdTubulacao() {
		return qtdTubulacao;
	}

	public double getDiametroTubulacaoMeter() {
		return diametroTubulacaoMeter;
	}

	public double getLargura() {
		return largura;
	}

	public double getAltura() {
		return altura;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public double getCt() {
		return ct;
	}

	public double getCb() {
		return cb;
	}

	public double getCotaSaida() {
		return cotaSaida;
	}

	public void setTipoSecaoTubulacao(String tipoSecaoTubulacao) {
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
	}

	public void setCategoriaTubulacaoId(int categoriaTubulacaoId) {
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	}

	public void setDescricaoCategoriaTubulacao(String descricaoCategoriaTubulacao) {
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	}

	public void setQtdTubulacao(int qtdTubulacao) {
		this.qtdTubulacao = qtdTubulacao;
	}

	public void setDiametroTubulacaoMeter(double diametroTubulacaoMeter) {
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public void setCb(double cb) {
		this.cb = cb;
	}

	public void setCotaSaida(double cotaSaida) {
		this.cotaSaida = cotaSaida;
	}

	public double getDeclividade() {
		return declividade;
	}

	public double getComprTubulacao() {
		return comprTubulacao;
	}

	public double getComprHorizTubulacao() {
		return comprHorizTubulacao;
	}

	public double getComprVertTubulacao() {
		return comprVertTubulacao;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}

	public void setComprTubulacao(double comprTubulacao) {
		this.comprTubulacao = comprTubulacao;
	}

	public void setComprHorizTubulacao(double comprHorizTubulacao) {
		this.comprHorizTubulacao = comprHorizTubulacao;
	}

	public void setComprVertTubulacao(double comprVertTubulacao) {
		this.comprVertTubulacao = comprVertTubulacao;
	}

	public int getProxEntId() {
		return proxEntId;
	}

	public void setProxEntId(int proxEntId) {
		this.proxEntId = proxEntId;
	}

	public CadEntity getProxEnt() {
		return proxEnt;
	}

	public void setProxEnt(CadEntity proxEnt) {
		this.proxEnt = proxEnt;
	}

}
