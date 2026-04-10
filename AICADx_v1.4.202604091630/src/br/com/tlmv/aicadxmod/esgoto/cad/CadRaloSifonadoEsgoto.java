/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadRaloSifonadoEsgoto.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/03/2026
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

package br.com.tlmv.aicadxmod.esgoto.cad;

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
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadRaloSifonadoEsgoto extends CadEntity 
{
//Private Static
	private static String gTextoPadrao 					= "RS";			// texto padrao = "RS"
	private static double gDiametroMeter				=  0.15;		// diametro = 15cm (0,15m)
	private static double gProfundidadeMeter 			= -0.3;			// profundidade = -30cm (-0,3m)
	//
	private static double gDiamConexaoEntradaMeter		=  0.04;		// diametro conexao (entrada) = 40mm (0,04m)
	private static double gDistConexaoEntradaHorizMeter	=  0.02;		// distancia conexao (entrada) = 20mm (0,02m)
	private static double gDistConexaoEntradaTopoMeter	=  0.003;		// distancia topo (entrada) = 3mm (0,003m)
	//
	private static double gDiamConexaoSaidaMeter		=  0.075;		// diametro conexao (saida) = 75mm (0,075m)
	private static double gDistConexaoSaidaHorizMeter	=  0.02;		// distancia conexao (saida) = 20mm (0,02m)
	private static double gDistConexaoSaidaFundoMeter	=  0.003;		// distancia fundo (saida) = 3mm (0,003m)
	//
	private static double gLarguraTampoMeter			=  0.15;		// largura tampo = 15cm (0,15m)
	private static double gComprimentoTampoMeter		=  0.15;		// comprimento tampo = 15cm (0,15m)
	private static double gEspesuraTampoMeter			=  0.005;		// espesuraTampo = 5mm (0,005m)
	//
	private static int gNumEntrada 						=  5;			// numero entrada = 5
	private static int gNumSaida 						=  1;			// numero saida = 1
	
//Private
    private GeomPoint3d ptIns 					= AppDefs.NULL_GEOMPOINT3D;   
    private GeomPoint3d ptDir 					= AppDefs.NULL_GEOMPOINT3D;   
    private int numeroRS						= AppDefs.NULL_INT;
    private String texto 						= AppDefs.NULL_STR;
    private double diametroMeter 				= AppDefs.NULL_DBL;
    private double profundidadeMeter	 		= AppDefs.NULL_DBL;
    private double diametroEntradaMeter 		= AppDefs.NULL_DBL;
    private double diametroSaidaMeter 			= AppDefs.NULL_DBL;
    private double larguraTampoMeter			= AppDefs.NULL_DBL;
	private double comprimentoTampoMeter		= AppDefs.NULL_DBL;
	private double espesuraTampoMeter			= AppDefs.NULL_DBL;
    private int numEntradas 					= CadRaloSifonadoEsgoto.gNumEntrada;
    private int numSaidas 						= CadRaloSifonadoEsgoto.gNumSaida;

    private ArrayList<GeomPoint3d> lsPtEntrada	= null;
    private GeomPoint3d ptSaida					= AppDefs.NULL_GEOMPOINT3D;
    
    /* Methodes */
    
    private GeomPoint3d createConexaoSaida(GeomPoint3d ptIns, GeomPoint3d ptDir) {
    	GeomPoint2d ptIns2d = new GeomPoint2d( ptIns ); 
    	GeomPoint2d ptDir2d = new GeomPoint2d( ptDir ); 
    	
		GeomVector2d vDir2d = new GeomVector2d(ptIns2d, ptDir2d); 
		GeomVector2d uDir2d = vDir2d.otherUnit(); 

		double dCotaTopoRS = this.ptIns.getZ();
		double dRaioRS = (this.diametroMeter / 2.0);        
		double dProfundidadeRS = Math.abs( this.profundidadeMeter ); 

		double dRaioSaida = this.diametroSaidaMeter / 2.0;
        double dZpSaida = dCotaTopoRS - (dProfundidadeRS - CadRaloSifonadoEsgoto.gDistConexaoSaidaFundoMeter - dRaioSaida);

		GeomPoint2d pt0_orig = ptIns2d.otherMoveTo(uDir2d, dRaioRS);

		double xPt0 = pt0_orig.getX();
		double yPt0 = pt0_orig.getY();
		double zPt0 = dZpSaida;

		GeomPoint3d pt0 = new GeomPoint3d(xPt0, yPt0, zPt0);
		return pt0;
    }
    
    private ArrayList<GeomPoint3d> createConexaoEntrada(GeomPoint3d ptIns, GeomPoint3d ptDir, int numEntradas, int numSaida) {
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>(); 

    	GeomPoint2d ptIns2d = new GeomPoint2d( ptIns );     	
    	GeomPoint2d ptDir2d = new GeomPoint2d( ptDir ); 
    	
		GeomVector2d vDir2d = new GeomVector2d(ptIns2d, ptDir2d); 
		GeomVector2d uDir2d = vDir2d.otherUnit(); 

		double dCotaTopoRS = this.ptIns.getZ();
		double dRaioRS = (this.diametroMeter / 2.0);        

		double dRaioEntrada = this.diametroEntradaMeter / 2.0;
        double dZpEntrada = dCotaTopoRS - (CadRaloSifonadoEsgoto.gDistConexaoEntradaTopoMeter + dRaioEntrada);

		int n = numEntradas + numSaida; 
		
		double stepAngRad = AppDefs.MATHVAL_2PI / n;
		GeomVector2d uDir0 = uDir2d.otherRotateToRad(stepAngRad);
		for(int i = 1; i < n; i++) {
			GeomPoint2d pt0_orig = ptIns2d.otherMoveTo(uDir0, dRaioRS);

			double xPt0 = pt0_orig.getX();
			double yPt0 = pt0_orig.getY();
			double zPt0 = dZpEntrada;

			GeomPoint3d pt0 = new GeomPoint3d(xPt0, yPt0, zPt0);
			lsResult.add(pt0);
			
			uDir0.selfRotateToRad(stepAngRad);
		}
		return lsResult;
    }
    
//Public

    public CadRaloSifonadoEsgoto(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODESRALOSIFONADO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptIns2d, GeomPoint2d ptDir2d) {
		
		//PT-INS
		//
		double xPtIns = ptIns2d.getX(); 
		double yPtIns = ptIns2d.getY(); 
		double zPtIns = 0.0; 

		//PT-DIR
		//
		double xPtDir = ptDir2d.getX(); 
		double yPtDir = ptDir2d.getY(); 
		double zPtDir = 0.0; 
		
		this.init(
			xPtIns, 
			yPtIns, 
			zPtIns,
			//
			xPtDir, 
			yPtDir, 
			zPtDir,
			//
			CadRaloSifonadoEsgoto.gTextoPadrao, 
			CadRaloSifonadoEsgoto.gDiametroMeter,
			CadRaloSifonadoEsgoto.gProfundidadeMeter,
			CadRaloSifonadoEsgoto.gDiamConexaoEntradaMeter,
			CadRaloSifonadoEsgoto.gDiamConexaoSaidaMeter,
			CadRaloSifonadoEsgoto.gLarguraTampoMeter,
			CadRaloSifonadoEsgoto.gComprimentoTampoMeter,
			CadRaloSifonadoEsgoto.gEspesuraTampoMeter,
			CadRaloSifonadoEsgoto.gNumEntrada );
	}
	
	private void init(GeomPoint3d ptIns3d, GeomPoint3d ptDir3d) {
		double xPtIns = ptIns3d.getX(); 
		double yPtIns = ptIns3d.getY(); 
		double zPtIns = ptIns3d.getZ(); 
		//
		double xPtDir = ptDir3d.getX(); 
		double yPtDir = ptDir3d.getY(); 
		double zPtDir = ptDir3d.getZ(); 
		
		this.init(
			xPtIns, 
			yPtIns, 
			zPtIns,
			//
			xPtDir, 
			yPtDir, 
			zPtDir,
			//
			CadRaloSifonadoEsgoto.gTextoPadrao, 
			CadRaloSifonadoEsgoto.gDiametroMeter,
			CadRaloSifonadoEsgoto.gProfundidadeMeter,
			CadRaloSifonadoEsgoto.gDiamConexaoEntradaMeter,
			CadRaloSifonadoEsgoto.gDiamConexaoSaidaMeter,
			CadRaloSifonadoEsgoto.gLarguraTampoMeter,
			CadRaloSifonadoEsgoto.gComprimentoTampoMeter,
			CadRaloSifonadoEsgoto.gEspesuraTampoMeter,
			CadRaloSifonadoEsgoto.gNumEntrada );
	}

	public void init(
		GeomPoint3d ptIns3d, 
		GeomPoint3d ptDir3d,
		//
		String texto, 
		double diametroMeter,
		double profundidadeMeter,
		double diamEntradaMeter,
		double diamSaidaMeter,
		double larguraTampoMeter,
		double comprimentoTampoMeter,
		double espesuraTampoMeter,
		int numEntradas ) 
	{
		this.init(
			ptIns3d.getX(), 
			ptIns3d.getY(), 
			ptIns3d.getZ(), 
			//
			ptDir3d.getX(), 
			ptDir3d.getY(), 
			ptDir3d.getZ(), 
			//
			texto,
			diametroMeter,
			profundidadeMeter,
			diamEntradaMeter,
			diamSaidaMeter,
			larguraTampoMeter,
			comprimentoTampoMeter,
			espesuraTampoMeter,
			numEntradas ); 
    }
	
	public void init(
		double xPtIns, 
		double yPtIns, 
		double zPtIns,
		//
		double xPtDir, 
		double yPtDir, 
		double zPtDir,
		//
		String texto, 
		double diametroMeter,
		double profundidadeMeter,
		double diamEntradaMeter,
		double diamSaidaMeter,
		double larguraTampoMeter,
		double comprimentoTampoMeter,
		double espesuraTampoMeter,
		int numEntradas )
	{
		this.numeroRS = this.getObjectId();
		//
		this.ptIns = new GeomPoint3d(xPtIns, yPtIns, zPtIns);
		this.ptDir = new GeomPoint3d(xPtDir, yPtDir, zPtDir);
		//
		this.texto = texto;
		this.diametroMeter = diametroMeter;
		this.profundidadeMeter = profundidadeMeter;
		this.diametroEntradaMeter = diamEntradaMeter;
		this.diametroSaidaMeter = diamSaidaMeter;
		this.larguraTampoMeter = larguraTampoMeter;
		this.comprimentoTampoMeter = comprimentoTampoMeter;
		this.espesuraTampoMeter = espesuraTampoMeter;
		this.numEntradas = numEntradas;
		
	    this.ptSaida = this.createConexaoSaida(this.ptIns, this.ptDir);
	    this.lsPtEntrada = this.createConexaoEntrada(this.ptIns, this.ptDir, this.numEntradas, this.numSaidas);
    }
	
	@Override
	public void init(ICadObject o) {
		CadRaloSifonadoEsgoto other = (CadRaloSifonadoEsgoto)o;

		GeomPoint3d ptIns3d = new GeomPoint3d( other.getPtIns() );
		GeomPoint3d ptDir3d = new GeomPoint3d( other.getPtDir() );

		this.init(
			ptIns3d.getX(), 
			ptIns3d.getY(), 
			ptIns3d.getZ(), 
			//
			ptDir3d.getX(), 
			ptDir3d.getY(), 
			ptDir3d.getZ(), 
			//
			other.texto,
			other.diametroMeter,
			other.profundidadeMeter,
			other.diametroEntradaMeter,
			other.diametroSaidaMeter,
			other.larguraTampoMeter,
			other.comprimentoTampoMeter,
			other.espesuraTampoMeter,
			other.numEntradas );
	}
	
	/* CREATE */
	
	public static CadRaloSifonadoEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptIns, GeomPoint2d ptDir) {
    	CadRaloSifonadoEsgoto o = new CadRaloSifonadoEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns, ptDir);
    	return o;
    }
	
	public static CadRaloSifonadoEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptIns, GeomPoint3d ptDir) {
    	CadRaloSifonadoEsgoto o = new CadRaloSifonadoEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns, ptDir);
    	return o;
    }

	public static CadRaloSifonadoEsgoto create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		//
		double xPtIns, 
		double yPtIns, 
		double zPtIns,
		//
		double xPtDir, 
		double yPtDir, 
		double zPtDir) 
	{
    	CadRaloSifonadoEsgoto o = new CadRaloSifonadoEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);

    	o.init(
    		xPtIns, 
    		yPtIns, 
    		zPtIns,
    		//
    		xPtDir, 
    		yPtDir, 
    		zPtDir,
    		//
    		CadRaloSifonadoEsgoto.gTextoPadrao,
    		CadRaloSifonadoEsgoto.gDiametroMeter,
    		CadRaloSifonadoEsgoto.gProfundidadeMeter,
    		CadRaloSifonadoEsgoto.gDiamConexaoEntradaMeter,
    		CadRaloSifonadoEsgoto.gDiamConexaoSaidaMeter,
    		CadRaloSifonadoEsgoto.gLarguraTampoMeter,
			CadRaloSifonadoEsgoto.gComprimentoTampoMeter,
			CadRaloSifonadoEsgoto.gEspesuraTampoMeter,
			CadRaloSifonadoEsgoto.gNumEntrada );
    	return o;
    }

	public static CadRaloSifonadoEsgoto create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		//
		double xPtIns, 
		double yPtIns, 
		double zPtIns,
		//
		double xPtDir, 
		double yPtDir, 
		double zPtDir,
		//
		boolean bLocked) 
	{
    	CadRaloSifonadoEsgoto o = new CadRaloSifonadoEsgoto(
    		oBlkDef, 
    		oLayer, 
    		oLevel, 
    		0.0, 
    		bLocked);
    	
    	o.init(
    		xPtIns, 
    		yPtIns, 
    		zPtIns, 
    		//
    		xPtDir, 
    		yPtDir, 
    		zPtDir,
    		//
    		CadRaloSifonadoEsgoto.gTextoPadrao,
    		CadRaloSifonadoEsgoto.gDiametroMeter,
    		CadRaloSifonadoEsgoto.gProfundidadeMeter,
    		CadRaloSifonadoEsgoto.gDiamConexaoEntradaMeter,
    		CadRaloSifonadoEsgoto.gDiamConexaoSaidaMeter,
    		CadRaloSifonadoEsgoto.gLarguraTampoMeter,
			CadRaloSifonadoEsgoto.gComprimentoTampoMeter,
			CadRaloSifonadoEsgoto.gEspesuraTampoMeter,
			CadRaloSifonadoEsgoto.gNumEntrada );
    	return o;
    }
	
	public static CadRaloSifonadoEsgoto create(CadRaloSifonadoEsgoto other) {
    	CadRaloSifonadoEsgoto o = new CadRaloSifonadoEsgoto(
    		other.getBlkDef(), 
    		other.getLayer(), 
    		other.getLevel(), 
    		other.getZLevel(), 
    		other.isLocked() );
    	o.init(other);
    	return o;
    }
	
	public static CadRaloSifonadoEsgoto create(
		CadBlockDef blkDef, 
		CadRaloSifonadoEsgoto other) 
	{
    	CadRaloSifonadoEsgoto o = new CadRaloSifonadoEsgoto(
    		blkDef, 
    		other.getLayer(), 
    		other.getLevel(), 
    		other.getZLevel(), 
    		other.isLocked() );
    	o.init(other);
    	return o;
    }
	
	/* UTILITIES */

	@Override
	public GeomPoint3d nearestConexao(GeomPoint3d ptRef) {
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>(this.lsPtEntrada);
		lsPts.add(this.ptSaida);
		
		GeomPoint3d ptResult = DrawUtil.nearestPoint(ptRef, lsPts);
		return ptResult;
	}
	
	@Override
	public GeomPoint3d nearestConexaoEntrada(GeomPoint3d ptRef) {
		GeomPoint3d ptResult = DrawUtil.nearestPoint(ptRef, this.lsPtEntrada);
		return ptResult;
	}
	
	@Override
	public GeomPoint3d nearestConexaoSaida(GeomPoint3d ptRef) {
		GeomPoint3d ptResult = new GeomPoint3d(this.ptSaida);
		return ptResult;
	}

	/* OPERATIONS */
	
	@Override
	public CadRaloSifonadoEsgoto duplicate()
	{
		CadRaloSifonadoEsgoto other = CadRaloSifonadoEsgoto.create(this);
		return other;
	}	
	
	@Override
	public CadRaloSifonadoEsgoto duplicate(CadBlockDef blkDef)
	{
		CadRaloSifonadoEsgoto other = CadRaloSifonadoEsgoto.create(blkDef, this);
		return other;
	}	
	
	@Override
	public CadRaloSifonadoEsgoto copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadRaloSifonadoEsgoto other = CadRaloSifonadoEsgoto.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadRaloSifonadoEsgoto moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptInsOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptInsOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}

    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadRaloSifonadoEsgoto scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptInsOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptInsOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
        //this.diametro = this.diametro * o.getScale();			;; diametro da CI depende da profundidade (nao_aplicavel)
		return this;
	}
	
	@Override
	public CadRaloSifonadoEsgoto offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadRaloSifonadoEsgoto o = copyTo(ptIMcs, ptFMcs);
		return o;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		lsProperty.addAll( this.ptDir.toPropertyList("Pt.Dir.", true) );
		lsProperty.add( new ItemDataVO("Numero RS", nf0.format(this.numeroRS), false) );
		lsProperty.add( new ItemDataVO("Texto", this.texto, false) );
		lsProperty.add( new ItemDataVO("Diametro (m)", nf3.format( this.diametroMeter ), false) );
		lsProperty.add( new ItemDataVO("Profundidade (m)", nf3.format( this.profundidadeMeter ), false) );
		lsProperty.add( new ItemDataVO("Diam.Entrada (m)", nf3.format( this.diametroEntradaMeter ), false) );
		lsProperty.add( new ItemDataVO("Diam.Saida (m)", nf3.format( this.diametroSaidaMeter ), false) );
		lsProperty.add( new ItemDataVO("Larg.Tampo (m)", nf3.format( this.larguraTampoMeter ), false) );
		lsProperty.add( new ItemDataVO("Compr.Tampo (m)", nf3.format( this.comprimentoTampoMeter ), false) );
		lsProperty.add( new ItemDataVO("Esp.Tampo (m)", nf3.format( this.espesuraTampoMeter ), false) );
		lsProperty.add( new ItemDataVO("Num.Entradas", nf0.format( this.numEntradas ), false) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"(Pt.Ins.- X:%s;Y:%s;Z:%s);" + 
			"(Pt.Dir.- X:%s;Y:%s;Z:%s);" +
			"NumeroRS:%s;" +
			"Texto:%s;" +
			"Diametro:%s;" +
			"Profundidade:%s;" + 
			"Diam.Entrada:%s;" +
			"Diam.Saida:%s;" +
			"LarguraTampo:%s;" +
			"ComprimentoTampo:%s;" +
			"EspesuraTampo:%s;" +
			"NumeroEntradas:%s; ", 
			nf3.format(this.ptIns.getX()), 
			nf3.format(this.ptIns.getY()), 
			nf3.format(this.ptIns.getZ()),
			//
			nf3.format(this.ptDir.getX()), 
			nf3.format(this.ptDir.getY()), 
			nf3.format(this.ptDir.getZ()),
			//
			nf0.format(this.numeroRS),
			this.texto,
			nf3.format(this.diametroMeter),
		    nf3.format(this.profundidadeMeter),
			nf3.format(this.diametroEntradaMeter),
			nf3.format(this.diametroSaidaMeter),
			nf3.format(this.larguraTampoMeter),
			nf3.format(this.comprimentoTampoMeter),
			nf3.format(this.espesuraTampoMeter),
			nf0.format(this.numEntradas) );
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

        GeomPoint2d ptInsDest2dMcs = new GeomPoint2d(this.ptIns);
        GeomPoint2d ptDirDest2dMcs = new GeomPoint2d(this.ptDir);
        double radius = this.diametroMeter / 2.0;
        
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
		        	CadRaloSifonadoEsgoto other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadRaloSifonadoEsgoto other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }        
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadRaloSifonadoEsgoto other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
			            radius = other.diametroMeter / 2.0;
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadRaloSifonadoEsgoto other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }
        
        //DRAW
        //
        GeomVector2d vDir = new GeomVector2d(ptInsDest2dMcs, ptDirDest2dMcs);
        GeomVector2d uDir = vDir.otherUnit(); 

        GeomVector2d uDir45d = vDir.otherRotateToRad( AppDefs.MATHVAL_H3PI );
        GeomPoint2d ptDir45d = ptInsDest2dMcs.otherMoveTo(uDir45d, radius);

        GeomVector2d uDir315d = vDir.otherRotateToRad( AppDefs.MATHVAL_5H3PI );
        GeomPoint2d ptDir315d = ptInsDest2dMcs.otherMoveTo(uDir315d, radius);
        
    	DrawUtil.drawCircleMcs(v, ptInsDest2dMcs, radius, g);
        
    	DrawUtil.drawLineMcs(v, ptDir45d, ptDir315d, g);

    	int n = this.numEntradas + 1;
    	
    	double stepAngRad = AppDefs.MATHVAL_2PI / n;
		GeomVector2d uDir0 = uDir.otherRotateToRad(stepAngRad); 
    	for(int i = 0; i < n; i++) {
    		GeomPoint2d pt0 = ptInsDest2dMcs.otherMoveTo(uDir0, radius);
        	DrawUtil.drawPointMcs(v, pt0, AppDefs.POINT_SIZE / 2.0, AppDefs.POINT_TYPE_CROSS, g);
    		
    		uDir0.selfRotateToRad(stepAngRad);
    	}

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
    	if( !this.isVisible() ) return;    	

    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        //DRAW: BASE_CILINDER
        //
        double alturaMeter = Math.abs( this.profundidadeMeter );
        double raioExterno = this.diametroMeter / 2.0;
        
    	double xPtIns = this.ptIns.getX();
    	double yPtIns = this.ptIns.getY();
    	double zPtIns = this.ptIns.getZ() - alturaMeter;        
        
        GeomPoint3d ptDestMin3dMcs = new GeomPoint3d(
        	xPtIns,
        	yPtIns,
        	zPtIns);

        GeomVector3d axisZ = GeomUtil.axisZ3d();
        
        prep.addCilinder(
        	view3d, 
        	this, 
        	c, 
        	ptDestMin3dMcs, 
        	axisZ, 
        	alturaMeter, 
        	raioExterno, 
        	true, 
        	true);

        //DRAW: TOP_BOX
        //
        GeomVector3d vDir = new GeomVector3d(this.getPtIns(), this.getPtDir());
        GeomVector3d uDir = vDir.otherUnit(); 

        GeomVector2d uDir2d = new GeomVector2d( uDir );
        double angRad = uDir2d.angleToAxisX();

    	double xPtTampo = this.ptIns.getX();
    	double yPtTampo = this.ptIns.getY();
    	double zPtTampo = this.ptIns.getZ();        
        
        GeomPoint3d ptTampo3dMcs = new GeomPoint3d(xPtTampo, yPtTampo, zPtTampo);
        
        prep.addBox(
        	view3d, 
        	this, 
        	c, 
        	ptTampo3dMcs, 
        	this.getLarguraTampoMeter(), 
        	this.getComprimentoTampoMeter(), 
        	this.getEspesuraTampoMeter(), 
        	angRad, 
        	null);
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;

    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptIns2dMcs = new GeomPoint2d(this.ptIns);

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double radius = this.diametroMeter / 2.0;
        
        double distMin = radius - (boxSz / 2.0);
        double distMax = radius + (boxSz / 2.0);
        
        double dist = ptIns2dMcs.distTo(pt2dMcs); 

        if( (dist >= distMin) && (dist <= distMax) ) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
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

    	GeomPoint2d ptIns = new GeomPoint2d(this.ptIns);    	
    	GeomPoint2d ptDir = new GeomPoint2d(this.ptDir);    	
        double radius = this.diametroMeter / 2.0;
        
        double zp = this.ptIns.getZ();
        
    	GeomVector2d vAxisX = new GeomVector2d(radius, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptIns, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add(new GeomPoint3d(this.ptIns));

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vDir.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vDir.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vDir.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vDir.otherRotateToDegrees(270.0);
    	
    	ArrayList<GeomPoint3d> lsPtQuadrant = new ArrayList<GeomPoint3d>();    	
		lsPtQuadrant.add( new GeomPoint3d(vPt0d.getXF(), vPt0d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt90d.getXF(), vPt90d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt180d.getXF(), vPt180d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt270d.getXF(), vPt270d.getYF(), zp) );
    	
        //POINT
        //
    	GeomVector2d vDir0 = new GeomVector2d(ptIns, ptDir);
        GeomVector2d uDir0 = vDir0.otherUnit(); 

    	ArrayList<GeomPoint3d> lsPtNode = new ArrayList<GeomPoint3d>();    	

    	int n = this.numEntradas + 1;
    	
    	double stepAngRad = AppDefs.MATHVAL_2PI / n;
		uDir0.selfRotateToRad(stepAngRad); 
    	for(int i = 0; i < n; i++) {
    		GeomPoint2d pt0 = ptIns.otherMoveTo(uDir0, radius);
    		lsPtNode.add( new GeomPoint3d(pt0.getX(), pt0.getY(), zp) );
    		
    		uDir0.selfRotateToRad(stepAngRad);
    	}
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_QUADRANT, pt2dMcs, lsPtQuadrant, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNode, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	GeomPoint2d ptIns = new GeomPoint2d(this.ptIns);    	
        double radius = this.diametroMeter / 2.0;
        
        double zp = this.ptIns.getZ();
        
    	GeomVector2d vAxisX = new GeomVector2d(radius, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptIns, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add(new GeomPoint3d(AppDefs.OSNAPMODE_CENTER, this.ptIns));

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vDir.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vDir.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vDir.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vDir.otherRotateToDegrees(270.0);
    	
    	ArrayList<GeomPoint3d> lsPtQuadrant = new ArrayList<GeomPoint3d>();    	
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt0d.getXF(), vPt0d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt90d.getXF(), vPt90d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt180d.getXF(), vPt180d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt270d.getXF(), vPt270d.getYF(), zp) );
    	
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtCenter);
    	lsResult.addAll(lsPtQuadrant);
    	return lsResult;
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
		return false;
	}

	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		double xPtIns = this.ptIns.getX();
		double yPtIns = this.ptIns.getY();
		double zPtIns = this.ptIns.getZ();

        double radius = this.diametroMeter / 2.0;

        double xPtMin = xPtIns - radius;
		double yPtMin = yPtIns - radius;
		double zPtMin = zPtIns - radius;
		
		double xPtMax = xPtIns + radius;
		double yPtMax = yPtIns + radius;
		double zPtMax = zPtIns + radius;
		
		GeomPoint3d ptMin3d = new GeomPoint3d(xPtMin, yPtMin, zPtMin);
		GeomPoint3d ptMax3d = new GeomPoint3d(xPtMax, yPtMax, zPtMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		double xPtIns = this.ptIns.getX();
		double yPtIns = this.ptIns.getY();

        double radius = this.diametroMeter / 2.0;

        double xPtMin = xPtIns - radius;
		double yPtMin = yPtIns - radius;
		
		double xPtMax = xPtIns + radius;
		double yPtMax = yPtIns + radius;
		
		GeomPoint2d ptMin2d = new GeomPoint2d(xPtMin, yPtMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(xPtMax, yPtMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		String searchString = super.getSearchString() + "^" +
			"RS=" + nf0.format( this.numeroRS ) + "^" +
			"TEXTO=" + this.texto + "^" +
			"DIAMETRO_ENTRADA=" + Double.toString( this.diametroEntradaMeter ) + "^" +
			"DIAMETRO_SAIDA=" + Double.toString( this.diametroSaidaMeter ) + "^" +
			"PROFUNDIDADE=" + Double.toString( this.profundidadeMeter );
		return searchString;
	}

	public GeomPoint3d getPtIns() {
        return this.ptIns;
    }

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public GeomPoint3d getPtDir() {
		return ptDir;
	}

	public void setPtDir(GeomPoint3d ptDir) {
		this.ptDir = ptDir;
	}

	public int getNumeroRS() {
		return numeroRS;
	}

	public void setNumeroRS(int numeroRS) {
		this.numeroRS = numeroRS;
	}

	public int getNumEntradas() {
		return numEntradas;
	}

	public void setNumEntradas(int numEntradas) {
		this.numEntradas = numEntradas;
	}

	public double getProfundidadeMeter() {
		return profundidadeMeter;
	}

	public void setProfundidadeMeter(double profundidadeMeter) {
		this.profundidadeMeter = profundidadeMeter;
	}

	public double getLarguraTampoMeter() {
		return larguraTampoMeter;
	}

	public void setLarguraTampoMeter(double larguraTampoMeter) {
		this.larguraTampoMeter = larguraTampoMeter;
	}

	public double getComprimentoTampoMeter() {
		return comprimentoTampoMeter;
	}

	public void setComprimentoTampoMeter(double comprimentoTampoMeter) {
		this.comprimentoTampoMeter = comprimentoTampoMeter;
	}

	public double getEspesuraTampoMeter() {
		return espesuraTampoMeter;
	}

	public void setEspesuraTampoMeter(double espesuraTampoMeter) {
		this.espesuraTampoMeter = espesuraTampoMeter;
	}

	public double getDiametroEntradaMeter() {
		return diametroEntradaMeter;
	}

	public void setDiametroEntradaMeter(double diametroEntradaMeter) {
		this.diametroEntradaMeter = diametroEntradaMeter;
	}

	public double getDiametroSaidaMeter() {
		return diametroSaidaMeter;
	}

	public void setDiametroSaidaMeter(double diametroSaidaMeter) {
		this.diametroSaidaMeter = diametroSaidaMeter;
	}

	public ArrayList<GeomPoint3d> getLsPtEntrada() {
		return lsPtEntrada;
	}

	public void setLsPtEntrada(ArrayList<GeomPoint3d> lsPtEntrada) {
		this.lsPtEntrada = lsPtEntrada;
	}

	public GeomPoint3d getPtSaida() {
		return ptSaida;
	}

	public void setPtSaida(GeomPoint3d ptSaida) {
		this.ptSaida = ptSaida;
	}

	public double getDiametroMeter() {
		return diametroMeter;
	}

	public void setDiametroMeter(double diametroMeter) {
		this.diametroMeter = diametroMeter;
	}

	public int getNumSaidas() {
		return numSaidas;
	}

	public void setNumSaidas(int numSaidas) {
		this.numSaidas = numSaidas;
	}

}
