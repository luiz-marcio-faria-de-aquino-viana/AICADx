/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadCaixaInspecaoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
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
import br.com.tlmv.aicadxapp.cad.CadPipe;
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
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadCaixaInspecaoDrenagemRecord;

public class CadCaixaInspecaoDrenagem extends CadEntity 
{
//Private Static
	private static int gSeqId = 0;
	
//Private
    private GeomPoint3d ptIns = new GeomPoint3d(0.0, 0.0, 0.0);    
    //
    private String tipoCI = AppDefs.DEF_TIPOCI_DRENAGEM;					// _ESGOTO_ / _APLUVIAL_ / _DRENAGEM_
    private String subtipoCI = AppDefs.DEF_SUBTIPOCI_DRENAGEM_NORMAL;		// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ / _REDE_PUBLICA_
    private int numeroCI = this.getObjectId();								// [id_caixa_inspecao]
    private int proximaCI = -1;												// [id_proxima_caixa_inspecao]
    private int numEstaca = 0;							// NumEstaca = 2
    private double distEstaca = 0.0;					// DistEstaca = 1.70 m
	private String pv = "PV-?";							// PV-A2.1
	private int localId = -1;							// 1001 - RUA DR. MARIO MACHADO
	private String local = "";							// RUA DR. MARIO MACHADO
	private String estaca = "0+0.00m";					// 2+1.70 m
	//
	private double areaExterna = 0.0;					// AreaExterna = SOMA(AreaTotal_Anterior)
	private double areaLocal = 0.0;						// AreaLocal = 0.220 ha
	private double areaTotal = 0.0;						// AreaTotal = AreaExterna + AreaLocal
	private double areaTotalImp = 0.0;					// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
    private double diametroMeter = 						// Diametro = 0.60
    	DrenagemCalc.DEF_DRENAGEM_DIAMCI_60CM;
    private double vazao = 0.0;							// VazaoCalculada
    private double vazaoAcumulada = 0.0;				// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
    private String tipoSecaoTubulacao =					// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
		DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR;				    
    private int categoriaTubulacaoId = 					// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
    private String descricaoCategoriaTubulacao =		// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
    //
    private double diametroTubulacaoMeter =				// DiametroTubulacao = 250 mm (0.25 m)
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDiamNominalMeter();
	private int qtdTubulacao = 1;						// QtdTubulacao = 1, 2, 3, 4...
    private double declividade = 						// Declividade = 0.00160
    	DrenagemCalc.DEF_DRENAGEM_DECLIVIDADEMINCI * 100.0;
	private double coefImper = 							// CoefImper = 0.80
		DrenagemCalc.COEFIMPER_ALTO.getDblVal();
    private double profundidade = 						// Profundidade = -0.60 m; valores negativos
    	- DrenagemCalc.DEF_DRENAGEM_PROFCI_100CM;
    private double comprTubulacao = 0.0;				// ComprTubulacao = 50.0 m
    private double comprHorizTubulacao = 0.0;			// ComprHorizTubulacao = 40.0 m
    private double comprVertTubulacao = 0.0;			// ComprVertTubulacao = 30.0 m
    private double ct = 0.0;							// CotaTerreno = 2.841 m
    private double cb = 								// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
    	- DrenagemCalc.DEF_DRENAGEM_PROFCI_100CM;
    //
    private double cotaEntrada = -0.6;					// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
    private double cotaSaida = -0.6;					// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
    private boolean bFixedDiametro = false;				// FIXED_PARAM: Diametro
    private boolean bFixedQtdTubulacao = false;			// FIXED_PARAM: QtdTubulacao
    private boolean bFixedDiametroTubulacao = false;	// FIXED_PARAM: DiametroTubulacao
    private boolean bFixedDeclividade = false;			// FIXED_PARAM: Declividade
    private boolean bFixedProfundidade = false;			// FIXED_PARAM: Profundidade
    private boolean bFixedCT = false;					// FIXED_PARAM: CT
    private boolean bRoot = false;						// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
    private boolean bFinish = false;					// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
    
    private CadCaixaInspecaoDrenagem proximo = null;
    private ArrayList<CadCaixaInspecaoDrenagem> lsAnterior = null;
    
    /* Methodes */
    
    private CadCaixaInspecaoDrenagem findCIByNumeroCI(ArrayList<CadCaixaInspecaoDrenagem> lsCI, int numeroCI)
    {
    	for(CadCaixaInspecaoDrenagem o : lsCI) {
    		if(o.numeroCI == numeroCI) {
    			return o;
    		}
    	}
    	return null;
    }
    
//Public

    public CadCaixaInspecaoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super( AppDefs.OBJTYPE_MODDRCAIXAINSPECAO,
    		   oBlkDef, 
    		   oLayer, 
    		   oLevel, 
    		   zLevel,
    		   bLocked ); 
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0);
	}
	
	private void init(GeomPoint3d ptCenter) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ());
	}

	public void init(
		double xCenter, 
		double yCenter, 
		double zCenter) 
	{
		int numPv = CadCaixaInspecaoDrenagem.nextSeqId();
		
		String tipoCI = AppDefs.DEF_TIPOCI_DRENAGEM;							// _ESGOTO_ / _APLUVIAL_
		String subtipoCI = AppDefs.DEF_SUBTIPOCI_DRENAGEM_NORMAL;				// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
		int numeroCI = this.getObjectId();										// [id_caixa_inspecao]
		int proximaCI = AppDefs.NULL_INT;										// [id_proxima_caixa_inspecao]
		int numEstaca = 0;														// NumEstaca = 2
		double distEstaca = 0.0;												// DistEstaca = 1.70 m
		String pv = String.format("PV-%s", numPv);								// PV-A2.1
		int localId = DrenagemCalc.IDFLOCAL_SANTACRUZ_VAL;						// 1001 - RUA DR. MARIO MACHADO
		String local = DrenagemCalc.IDFLOCAL_SANTACRUZ_STR;						// RUA DR. MARIO MACHADO
		String estaca = "0 + 0.00m";											// Estaca = 2 + 1.70 m
		//
		double areaExterna = 0.0;												// AreaExterna = SOMA(AreaTotal_Anterior)
		double areaLocal = 0.2;													// AreaLocal = 0.220 ha
		double areaTotal = areaExterna + areaLocal;								// AreaTotal = AreaExterna + AreaLocal
		double areaTotalImp = 0.0;												// areaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
		double diametroTampaMeter = DrenagemCalc.DEF_DRENAGEM_DIAMCI_60CM;		// Diametro = 0.60 
		double vazaoCalculada = 0.0;											// VazaoCalculada
		double vazaoAcumulada = 0.0;											// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada
		String tipoSecaoTubulacao = DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR;	// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
		int categoriaTubulacaoId =												// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD 
			DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
		String descricaoCategoriaTubulacao = 									//  = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
			DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
	    //
		double diametroTubulacaoMili =											// DiametroTubulacao = 250 mm (0.25 m) 
			DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDiamNominalMeter();
		double diametroTubulacaoMeter = (diametroTubulacaoMili / 1000.0);
		double hDiametroTubulacaoMeter = diametroTubulacaoMeter / 2.0;			// RaioTubulacao (metros) = 0.25 m
		int qtdTubulacao = 1;													// QtdTubulacao = 1, 2, 3, 4...
		double declividade = 													// Declividade = 0.005 m/m = 0.5 %
			( DrenagemCalc.DEF_DRENAGEM_DECLIVIDADEMINCI * 100.0 );
		double coefImper = DrenagemCalc.COEFIMPER_ALTO.getDblVal();				// CoefImper = 0.80
		double profundidade = ( - DrenagemCalc.DEF_DRENAGEM_PROFCI_100CM );		// Profundidade = -1.0 m (VALOR NEGATIVO = COTA_FUNDO - COTA_TERRENO)
		double comprTubulacao = 0.0;											// ComprTubulacao = 50.0 m
		double comprHorizTubulacao = 0.0;										// ComprHorizTubulacao = 40.0 m
		double comprVertTubulacao = 0.0;										// ComprVertTubulacao = 30.0 m
		double ct = zCenter;													// CotaTerreno = 2.841 m
		double cb = ( ct + profundidade );										// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
		//
		double cotaEntrada = ct - hDiametroTubulacaoMeter;						// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
		double cotaSaida = cb + hDiametroTubulacaoMeter;						// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao

		this.init(
			xCenter, 
			yCenter, 
			zCenter,
			//
			tipoCI,																	// _ESGOTO_ / _APLUVIAL_
			subtipoCI, 																// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
			numeroCI,																// [id_caixa_inspecao]
			proximaCI,																// [id_proxima_caixa_inspecao]
			numEstaca,																// NumEstaca = 2
			distEstaca,																// DistEstaca = 1.70 m
			pv,																		// PV-A2.1
			localId,																// 1001 - RUA DR. MARIO MACHADO
			local,																	// RUA DR. MARIO MACHADO
			estaca,																	// Estaca = 2 + 1.70 m
			//
			areaExterna,															// AreaExterna = SOMA(AreaTotal_Anterior)
			areaLocal,																// AreaLocal = 0.220 ha
			areaTotal,																// AreaTotal = AreaExterna + AreaLocal
			areaTotalImp,															// areaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
			diametroTampaMeter,														// Diametro = 0.60 
			vazaoCalculada,															// VazaoCalculada
			vazaoAcumulada,															// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada
			tipoSecaoTubulacao,														// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
			categoriaTubulacaoId,													// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD 
			descricaoCategoriaTubulacao, 											//  = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
			//
			diametroTubulacaoMili,													// DiametroTubulacao = 250 mm (0.25 m) 
			qtdTubulacao,															// QtdTubulacao = 1, 2, 3, 4...
			declividade,		 													// Declividade = 0.005 m/m = 0.5 %
			coefImper,																// CoefImper = 0.80
			profundidade,															// Profundidade = -1.0 m (VALOR NEGATIVO = COTA_FUNDO - COTA_TERRENO)
			comprTubulacao,															// ComprTubulacao = 50.0 m
			comprHorizTubulacao,													// ComprHorizTubulacao = 40.0 m
			comprVertTubulacao,														// ComprVertTubulacao = 30.0 m
			ct,																		// CotaTerreno = 2.841 m
			cb,																		// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
			//
			cotaEntrada,															// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
			cotaSaida,																// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
			//
		    AppDefs.DEF_VALUES_NAO,													// FIXED_PARAM: Diametro
		    AppDefs.DEF_VALUES_NAO,													// FIXED_PARAM: QtdTubulacao
		    AppDefs.DEF_VALUES_NAO,													// FIXED_PARAM: DiametroTubulacao
		    AppDefs.DEF_VALUES_NAO,													// FIXED_PARAM: Declividade
		    AppDefs.DEF_VALUES_NAO,													// FIXED_PARAM: Profundidade
		    AppDefs.DEF_VALUES_NAO,													// FIXED_PARAM: CT
		    AppDefs.DEF_VALUES_NAO,													// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
		    AppDefs.DEF_VALUES_NAO );												// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	    this.debug(AppDefs.DEBUG_LEVEL32);
		
	    this.proximo = null;	    
	    this.lsAnterior = new ArrayList<CadCaixaInspecaoDrenagem>();
    }
	
	public void init(
	    double ptInsX,
	    double ptInsY,
	    double ptInsZ,
	    //
	    String tipoCI,															// _ESGOTO_ / _APLUVIAL_
	    String subtipoCI,														// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
	    int numeroCI,															// [id_caixa_inspecao]
	    int proximaCI,															// [id_proxima_caixa_inspecao]
	    int numEstaca,															// NumEstaca = 2
	    double distEstaca,														// DistEstaca = 1.70 m
		String pv,																// PV-A2.1
		int localId,															// 1001 - RUA DR. MARIO MACHADO
		String local,															// RUA DR. MARIO MACHADO
		String estaca,															// 2 + 1.70 m
	    //
		double areaExterna,														// AreaExterna = SOMA(AreaTotal_Anterior)
		double areaLocal,														// AreaLocal = 0.220 ha
		double areaTotal,														// AreaTotal = AreaExterna + AreaLocal
		double areaTotalImp,													// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
		double diametroMeter,													// Diametro = 0.60
	    double vazao,															// VazaoCalculada
	    double vazaoAcumulada,    												// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
	    String tipoSecaoTubulacao,												// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
	    int categoriaTubulacaoId, 												// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
	    String descricaoCategoriaTubulacao,										// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
	    //
	    double diametroTubulacaoMeter,											// DiametroTubulacao = 250 mm (0.25 m)
	    int qtdTubulacao,														// QtdTubulacao = 1, 2, 3, 4...
	    double declividade,														// Declividade = 0.00160
		double coefImper,														// CoefImper = 0.80
	    double profundidade,													// Profundidade = -0.60 m
	    double comprTubulacao,													// ComprTubulacao = 50.0 m
	    double comprHorizTubulacao,												// ComprHorizTubulacao = 40.0 m
	    double comprVertTubulacao,												// ComprVertTubulacao = 30.0 m
	    double ct,																// CotaTerreno = 2.841 m
	    double cb,																// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
	    //
	    double cotaEntrada,														// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
	    double cotaSaida,														// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
	    String strFixedDiametro,												// FIXED_PARAM: Diametro
	    String strFixedQtdTubulacao,											// FIXED_PARAM: QtdTubulacao
	    String strFixedDiametroTubulacao,										// FIXED_PARAM: DiametroTubulacao
	    String strFixedDeclividade,												// FIXED_PARAM: Declividade
	    String strFixedProfundidade,											// FIXED_PARAM: Profundidade
	    String strFixedCT,														// FIXED_PARAM: CT
	    String strIsRoot,														// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
	    String strIsFinish )													// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	{
		this.ptIns = new GeomPoint3d(ptInsX, ptInsY, ptInsZ);
	    //
		this.tipoCI = tipoCI;
	    this.subtipoCI = subtipoCI; 
	    this.numeroCI = numeroCI;
	    this.proximaCI = proximaCI;
	    this.numEstaca = numEstaca;
	    this.distEstaca = distEstaca;
	    this.pv = pv;
	    this.localId = localId;
	    this.local = local;
	    this.estaca = estaca;
	    //
		this.areaExterna = areaExterna;
		this.areaLocal = areaLocal;
		this.areaTotal = areaTotal;
		this.areaTotalImp = areaTotalImp;
		this.diametroMeter = diametroMeter;
		this.vazao = vazao;
		this.vazaoAcumulada = vazaoAcumulada;  
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
		this.categoriaTubulacaoId = categoriaTubulacaoId;
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	    //
	    this.diametroTubulacaoMeter = diametroTubulacaoMeter;
	    this.qtdTubulacao = qtdTubulacao;
	    this.declividade = declividade;
	    this.coefImper = coefImper;
	    this.profundidade = profundidade;
	    this.comprTubulacao = comprTubulacao;
	    this.comprHorizTubulacao = comprHorizTubulacao;
	    this.comprVertTubulacao = comprVertTubulacao;
	    this.ct = ct;
	    this.cb = cb;
	    //
	    this.cotaEntrada = cotaEntrada;
	    this.cotaSaida = cotaSaida;
	    this.bFixedDiametro = StringUtil.fromStrToBool( strFixedDiametro );
	    this.bFixedQtdTubulacao = StringUtil.fromStrToBool( strFixedQtdTubulacao );
	    this.bFixedDiametroTubulacao = StringUtil.fromStrToBool( strFixedDiametroTubulacao );
	    this.bFixedDeclividade = StringUtil.fromStrToBool( strFixedDeclividade );
	    this.bFixedProfundidade = StringUtil.fromStrToBool( strFixedProfundidade );
	    this.bFixedCT = StringUtil.fromStrToBool( strFixedCT );
	    this.bRoot = StringUtil.fromStrToBool( strIsRoot );
	    this.bFinish = StringUtil.fromStrToBool( strIsFinish );
	    
	    this.proximo = null;	    
	    this.lsAnterior = new ArrayList<CadCaixaInspecaoDrenagem>();
    }
	
	@Override
	public void init(ICadObject o) {
		CadCaixaInspecaoDrenagem other = (CadCaixaInspecaoDrenagem)o; 

		GeomPoint3d ptTmpIns = other.ptIns;
		double tmpDiam = other.diametroMeter;
		
		this.init(ptTmpIns.getX(), ptTmpIns.getY(), ptTmpIns.getZ());
	}
	
	@Override
	public String toString() {
		String str = String.format("Codigo:%s;PV:%s;Estaca:%s;Tipo:%s;Camada:%s", 
			Integer.toString( this.getObjectId() ),
			this.pv,
			this.estaca,
			this.getObjTypeStr(),
			this.getLayer().getName());
		return str;
	}
	
	/* CREATE */
	
	public static CadCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptIns) {
    	CadCaixaInspecaoDrenagem o = new CadCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns);
    	return o;
    }
	
	public static CadCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptIns) {
    	CadCaixaInspecaoDrenagem o = new CadCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns);
    	return o;
    }

	public static CadCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter) {
    	CadCaixaInspecaoDrenagem o = new CadCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(xCenter, yCenter, zCenter);
    	return o;
    }

	public static CadCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, boolean bLocked) {
    	CadCaixaInspecaoDrenagem o = new CadCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(xCenter, yCenter, zCenter);
    	return o;
    }
	
	public static CadCaixaInspecaoDrenagem create(CadCaixaInspecaoDrenagem other) {
    	CadCaixaInspecaoDrenagem o = new CadCaixaInspecaoDrenagem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadCaixaInspecaoDrenagem create(CadBlockDef blkDef, CadCaixaInspecaoDrenagem other) {
    	CadCaixaInspecaoDrenagem o = new CadCaixaInspecaoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadCaixaInspecaoDrenagemRecord oRec) {
		CadDocumentDef doc = oBlkDef.getDocument();

		String reference = oRec.getReference();
		String levelName = oRec.getLevelName();

		// LAYER_TABLE
		//
		LayerTable oLayTbl = doc.getLayerTable();
		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		// LEVEL_TABLE
		//
		LevelTable oLevelTbl = doc.getLevelTable();
		CadLevel oLevel = oLevelTbl.getLevel(levelName);

		CadCaixaInspecaoDrenagem o = CadCaixaInspecaoDrenagem.create(
			oBlkDef,
			oLayer, 
			oLevel,
			oRec.getPtInsX(), 
			oRec.getPtInsY(), 
			oRec.getPtInsZ() );
		o.setObjectId(oRec.getObjectId());
		
	    return o;
	}
	
	/* OPERATIONS */
	
	@Override
	public CadCaixaInspecaoDrenagem duplicate()
	{
		CadCaixaInspecaoDrenagem other = CadCaixaInspecaoDrenagem.create(this);
		return other;
	}	
	
	@Override
	public CadCaixaInspecaoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadCaixaInspecaoDrenagem other = CadCaixaInspecaoDrenagem.create(blkDef, this);
		return other;
	}	
	
	@Override
	public CadCaixaInspecaoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadCaixaInspecaoDrenagem other = CadCaixaInspecaoDrenagem.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadCaixaInspecaoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadCaixaInspecaoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadCaixaInspecaoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadCaixaInspecaoDrenagem o = copyTo(ptIMcs, ptFMcs);
		return o;
	}
    
	/* UPDATE */
	
	public void clearProximaCI()
	{
		this.proximaCI = AppDefs.NULL_INT;
		this.proximo = null;
	}
	
	public int updateProximaCI(CadCaixaInspecaoDrenagem ent2)
	{
		this.proximaCI = ent2.getObjectId();
		this.proximo = ent2;
		return this.proximaCI;
	}

	/* TO/FROM PIPES */
	
	public ArrayList<CadPipe> findAllCadPipeFrom()
	{
		ArrayList<CadPipe> lsPipe = new ArrayList<CadPipe>();
		
		CadBlockDef blk = this.getBlkDef();

		CadEntity[] arr = blk.findAllEntityByObjType(AppDefs.OBJTYPE_BIMPIPE);
		for(CadEntity oEnt : arr) {
			CadPipe oPipe = (CadPipe)oEnt;
			
			int numeroFromCI = oPipe.getFromObjId();
			if(numeroFromCI == this.numeroCI) {
				CadCaixaInspecaoDrenagem oFromCI = (CadCaixaInspecaoDrenagem)blk.getEntity(numeroFromCI);
				if(oFromCI != null) {
					lsPipe.add(oPipe);
				}
			}
		}
		return lsPipe;
	}
	
	public ArrayList<CadPipe> findAllCadPipeTo()
	{
		ArrayList<CadPipe> lsPipe = new ArrayList<CadPipe>();
		
		CadBlockDef blk = this.getBlkDef();

		CadEntity[] arr = blk.findAllEntityByObjType(AppDefs.OBJTYPE_BIMPIPE);
		for(CadEntity oEnt : arr) {
			CadPipe oPipe = (CadPipe)oEnt;
			
			int numeroToCI = oPipe.getToObjId();
			if(numeroToCI == this.numeroCI) {
				CadCaixaInspecaoDrenagem oToCI = (CadCaixaInspecaoDrenagem)blk.getEntity(numeroToCI);
				if(oToCI != null) {
					lsPipe.add(oPipe);
				}
			}
		}
		return lsPipe;
	}
	
	public ArrayList<CadPipe> findAllCadPipe()
	{
		ArrayList<CadPipe> lsPipe = new ArrayList<CadPipe>();

		CadBlockDef blk = this.getBlkDef();

		CadEntity[] arr = blk.findAllEntityByObjType(AppDefs.OBJTYPE_BIMPIPE);
		for(CadEntity oEnt : arr) {
			CadPipe oPipe = (CadPipe)oEnt;
			
			int numeroFromCI = oPipe.getFromObjId();
			if(numeroFromCI == this.numeroCI) {
				CadCaixaInspecaoDrenagem oFromCI = (CadCaixaInspecaoDrenagem)blk.getEntity(numeroFromCI);
				if(oFromCI != null) {
					lsPipe.add(oPipe);
				}
			}
			
			int numeroToCI = oPipe.getToObjId();
			if(numeroToCI == this.numeroCI) {
				CadCaixaInspecaoDrenagem oToCI = (CadCaixaInspecaoDrenagem)blk.getEntity(numeroToCI);
				if(oToCI != null) {
					lsPipe.add(oPipe);
				}
			}
		}
		return lsPipe;
	}
	
	/* DATA_UPDATE */
	
	public void updateData(CadMemoriaCalculoItemDrenagemOData oItem)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		int numPv = CadCaixaInspecaoDrenagem.nextSeqId();
		
		//this.ptIns = new GeomPoint3d(xCenter, yCenter, zCenter);
	    //
		//this.tipoCI = AppDefs.DEF_TIPOCI_DRENAGEM;										// _ESGOTO_ / _APLUVIAL_
	    //this.subtipoCI = AppDefs.DEF_SUBTIPOCI_DRENAGEM_NORMAL;							// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
	    //this.numeroCI = this.getObjectId();												// [id_caixa_inspecao]
	    //this.proximaCI = AppDefs.NULL_INT;												// [id_proxima_caixa_inspecao]
	    //
		this.pv = oItem.getPv();
		this.localId = oItem.getLocalId();
		this.local = oItem.getLocal();
	    //this.numEstaca = 0;
	    //this.distEstaca = 0;
		//this.estaca = String.format("%s + %s", nf0.format( this.numEstaca ), nf3.format( this.distEstaca ));
		this.areaExterna = oItem.getAreaExterna();
		this.areaLocal = oItem.getAreaLocal();
		this.areaTotal = oItem.getAreaTotal();
		this.areaTotalImp = oItem.getAreaTotalImp();
	    //this.diametro = oItem.getDiametroCaixa();
	    this.vazao = oItem.getVazao();
	    this.vazaoAcumulada = oItem.getVazaoAcumulada();  
	    this.tipoSecaoTubulacao = oItem.getTipoSecaoTubulacao();
	    this.categoriaTubulacaoId = oItem.getCategoriaTubulacaoId();
	    this.descricaoCategoriaTubulacao = oItem.getDescricaoCategoriaTubulacao();
	    this.diametroTubulacaoMeter = oItem.getDiametroTubulacaoMeter();
	    this.qtdTubulacao = oItem.getQtdTubulacao();
	    this.declividade = oItem.getDeclividade();
		this.coefImper = oItem.getCoefImper();
	    this.profundidade = oItem.getProfMontJus();
	    //
	    this.comprTubulacao = oItem.getComprimento();
	    //
	    if( Math.abs(this.declividade) < AppDefs.MATHPREC_MIN ) {
	    	this.comprHorizTubulacao = this.comprTubulacao;
		    this.comprVertTubulacao = 0.0;
	    }
	    else {
	    	this.comprHorizTubulacao = this.comprTubulacao / this.declividade;
		    this.comprVertTubulacao = this.comprTubulacao * this.declividade;
	    }
	    //
	    this.ct = oItem.getCotaTerreno();
	    this.cb = oItem.getFundo();
	    this.cotaEntrada = oItem.getCotaEntrada();
	    this.cotaSaida = oItem.getCotaSaida();
	    //
	    this.bRoot = oItem.isRoot();
	    this.bFinish = oItem.isFinish();
	    //
	    this.proximo = null;	    
	    this.lsAnterior = new ArrayList<CadCaixaInspecaoDrenagem>();
	}
	
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ItemDataVO oItem_tipoCI = ListUtil.findItemDataById(this.tipoCI, AppDefs.ARR_TIPOCI);
		ItemDataVO oItem_subtipoCI = ListUtil.findItemDataById(this.subtipoCI, AppDefs.ARR_SUBTIPOCI);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);
		
		String strIsRoot = ( this.isRoot() ) ? AppDefs.DEF_VALUES_NAO : AppDefs.DEF_VALUES_SIM;
		
		String strIsFinish = ( this.isFinish() ) ? AppDefs.DEF_VALUES_NAO : AppDefs.DEF_VALUES_SIM;
		
		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		//
		lsProperty.add( new ItemDataVO("Tipo de CI", oItem_tipoCI.getDescricao(), false) );
		lsProperty.add( new ItemDataVO("Subtipo de CI", oItem_subtipoCI.getDescricao(), false) );
		lsProperty.add( new ItemDataVO("Numero CI", Integer.toString(this.numeroCI), false) );
		lsProperty.add( new ItemDataVO("Proxima CI", Integer.toString(this.proximaCI), false) );
		lsProperty.add( new ItemDataVO("Raiz Rede", strIsRoot, false) );
		lsProperty.add( new ItemDataVO("Final Trecho", strIsFinish, false) );
		lsProperty.add( new ItemDataVO("PV", this.pv, true) );
		lsProperty.add( new ItemDataVO("Local", this.local, true) );
		lsProperty.add( new ItemDataVO("Estaca", this.estaca, false) );
		//
		lsProperty.add( new ItemDataVO("Area Externa", nf3.format(this.areaExterna), false) );
		lsProperty.add( new ItemDataVO("Area Local", nf3.format(this.areaLocal), true) );
		lsProperty.add( new ItemDataVO("Area Total", nf3.format(this.areaTotal), false) );
		lsProperty.add( new ItemDataVO("Imp. Area Total", nf3.format(this.areaTotalImp), false) );
		lsProperty.add( new ItemDataVO("Diametro", nf3.format(this.diametroMeter), false) );		
		lsProperty.add( new ItemDataVO("Vazao", nf3.format(this.vazao), false) );		
		lsProperty.add( new ItemDataVO("Vazao Acumulada", nf3.format(this.vazaoAcumulada), false) );		
		lsProperty.add( new ItemDataVO("Categoria Tubulacao", this.descricaoCategoriaTubulacao, false) );
		lsProperty.add( new ItemDataVO("Quantidade Tubulacao", nf3.format(this.qtdTubulacao), false) );		
		//
		lsProperty.add( new ItemDataVO("Diametro Tubulacao", nf3.format(this.diametroTubulacaoMeter), false) );		
		lsProperty.add( new ItemDataVO("Declividade", nf3.format(this.declividade), false) );	
		lsProperty.add( new ItemDataVO("Coef.Imper.", nf3.format(this.coefImper), false) );	
		lsProperty.add( new ItemDataVO("Profundidade", nf3.format(this.profundidade), false) );		
		lsProperty.add( new ItemDataVO("ComprTubulacao", nf3.format(this.comprTubulacao), false) );		
		lsProperty.add( new ItemDataVO("ComprHorizTubulacao", nf3.format(this.comprHorizTubulacao), false) );		
		lsProperty.add( new ItemDataVO("ComprVertTubulacao", nf3.format(this.comprVertTubulacao), false) );		
		lsProperty.add( new ItemDataVO("Cota Topo", nf3.format(this.ct), false) );
		lsProperty.add( new ItemDataVO("Cota Fundo", nf3.format(this.cb), false) );
		lsProperty.add( new ItemDataVO("Cota Entrada", nf3.format(this.cotaEntrada), false) );		
		lsProperty.add( new ItemDataVO("Cota Saida", nf3.format(this.cotaSaida), false) );		
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String strIsRoot = ( this.isRoot() ) ? AppDefs.DEF_VALUES_NAO : AppDefs.DEF_VALUES_SIM;
		
		String strIsFinish = ( this.isFinish() ) ? AppDefs.DEF_VALUES_NAO : AppDefs.DEF_VALUES_SIM;
		
		String str = String.format(
			"ObjectId:%s;" +
			//
			"X:%s;" +
			"Y:%s;" +
			"Z:%s;" +
			//
			"TipoCI:%s;" +
			"SubtipoCI:%s;" +
			"NumeroCI:%s;" +
			"ProximaCI:%s;" +
			"RaizRede:%s;" +	
			"FinalTrecho:%s;" +
			"PV:%s;" +
			"Local:%s;" +
			"Estaca:%s;" +
			//
			"AreaExterna:%s;" +
			"AreaLocal:%s;" +
			"AreaTotal:%s;" +
			"AreaTotalImp:%s;" +
			"Diametro:%s;" +		
			"Profundidade:%s;" +
			"Declividade:%s;" +
			"Coef.Imper:%s;" +	
			"Vazao:%s;" +
			"VazaoAcumulada:%s;" +
			"CategoriaTubulacaoId:%s;" +
			"DescricaoCategoriaTubulacao:%s;" +
			"QtdTubulacao:%s;" +
			//
			"DiametroTubulacao:%s;" +
			"ComprTubulacao:%s;" +		
			"ComprHorizTubulacao:%s;" +		
			"ComprVertTubulacao:%s;" +	
			"CotaTopo:%s;" +
			"CotaBase:%s;" +
			"CotaEntrada:%s;" + 
			"CotaSaida:%s;", 
			Integer.toString(this.getObjectId()),		
			//
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()),
			//
			this.tipoCI,
		    this.subtipoCI,
		    nf6.format(this.numeroCI),
		    nf6.format(this.proximaCI),
		    strIsRoot,
			strIsFinish,
		    this.pv,
		    this.local,
		    this.estaca,
			//
		    nf6.format(this.areaExterna),
		    nf6.format(this.areaLocal),
		    nf6.format(this.areaTotal),
		    nf6.format(this.areaTotalImp),
		    nf6.format(this.diametroMeter),
		    nf6.format(this.profundidade),
		    nf6.format(this.declividade),
		    nf6.format(this.coefImper),
		    nf6.format(this.vazao),
		    nf6.format(this.vazaoAcumulada),
		    this.categoriaTubulacaoId,
		    this.descricaoCategoriaTubulacao,
		    nf6.format(this.qtdTubulacao),
			//
		    nf6.format(this.diametroTubulacaoMeter),
			nf6.format(this.comprTubulacao),
			nf6.format(this.comprHorizTubulacao),
			nf6.format(this.comprVertTubulacao),
		    nf6.format(this.ct),
		    nf6.format(this.cb),
		    nf6.format(this.cotaEntrada),
		    nf6.format(this.cotaSaida) );		
		return str;
	}
	
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

    public void redraw2d_lowDetailView_planView(ICadViewBase v, Color c, Stroke b, GeomPoint2d ptIns2dMcs, double sclFact, double radiusExtMcs, double thicknessMcs, Graphics g) 
    {    	
    	double dRadiusIntMcs = radiusExtMcs - thicknessMcs;
    	
		Color oldcol = GeomUtil.setColor(g, c);		

		Stroke oldltype = GeomUtil.setLtype(g, b);
    	
		BorderStrokeVO bs = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		GeomUtil.setLtype(g, bs.getLtype());

    	DrawUtil.drawCircleMcs(v, ptIns2dMcs, dRadiusIntMcs, g);
    	
        GeomUtil.setLtype(g, oldltype);
		
    	DrawUtil.drawCircleMcs(v, ptIns2dMcs, radiusExtMcs, g);
		
        GeomUtil.setLtype(g, oldltype);
    	
		GeomUtil.setColor(g, oldcol);		
    }

    public void redraw2d_highDetailView_planView(ICadViewBase v, Color c, Stroke b, GeomPoint2d ptIns2dMcs, GeomVector2d vDir, double sclFact, double radiusExtMcs, double thicknessMcs, double widthMcs, double heightMcs, double boxThicknessMcs, Graphics g) 
    {
    	GeomVector2d uDir = vDir.otherUnit();
    	GeomVector2d nDir = uDir.otherNorm();
    	
    	double dRadiusIntMcs = radiusExtMcs - thicknessMcs;

    	double dHeightMcs = (2.0 * boxThicknessMcs) + heightMcs;
    	
    	double dWidthMcs = (2.0 * boxThicknessMcs) + widthMcs;
    	double dWidthMcs2 = dWidthMcs / 2.0;
    	
    	double dHeightIntMcs = heightMcs;
    	
    	double dWidthIntMcs = widthMcs;
    	double dWidthIntMcs2 = dWidthIntMcs / 2.0;

    	//EXTERNAL
    	GeomPoint2d pt1 = ptIns2dMcs.otherMoveTo(uDir, radiusExtMcs + (2.0 * boxThicknessMcs));
    	GeomPoint2d pt2 = pt1.otherMoveTo(nDir,   dWidthMcs2);
    	GeomPoint2d pt3 = pt2.otherMoveTo(uDir, - dHeightMcs);
    	GeomPoint2d pt4 = pt3.otherMoveTo(nDir, - dWidthMcs);
    	GeomPoint2d pt5 = pt4.otherMoveTo(uDir,   dHeightMcs);
    	
    	//INTERNAL
    	GeomPoint2d pt1_int = ptIns2dMcs.otherMoveTo(uDir, radiusExtMcs + boxThicknessMcs);
    	GeomPoint2d pt2_int = pt1_int.otherMoveTo(nDir,   dWidthIntMcs2);
    	GeomPoint2d pt3_int = pt2_int.otherMoveTo(uDir, - dHeightIntMcs);
    	GeomPoint2d pt4_int = pt3_int.otherMoveTo(nDir, - dWidthIntMcs);
    	GeomPoint2d pt5_int = pt4_int.otherMoveTo(uDir,   dHeightIntMcs);
    	
		Color oldcol = GeomUtil.setColor(g, c);		

		Stroke oldltype = GeomUtil.setLtype(g, b);

		BorderStrokeVO bs = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		GeomUtil.setLtype(g, bs.getLtype());

    	//EXTERNAL
    	DrawUtil.drawLineMcs(v, pt1, pt2, g);
    	DrawUtil.drawLineMcs(v, pt2, pt3, g);
    	DrawUtil.drawLineMcs(v, pt3, pt4, g);
    	DrawUtil.drawLineMcs(v, pt4, pt5, g);
    	DrawUtil.drawLineMcs(v, pt5, pt1, g);

    	//INTERNAL
    	DrawUtil.drawLineMcs(v, pt1_int, pt2_int, g);
    	DrawUtil.drawLineMcs(v, pt2_int, pt3_int, g);
    	DrawUtil.drawLineMcs(v, pt3_int, pt4_int, g);
    	DrawUtil.drawLineMcs(v, pt4_int, pt5_int, g);
    	DrawUtil.drawLineMcs(v, pt5_int, pt1_int, g);
		
    	DrawUtil.drawCircleMcs(v, ptIns2dMcs, dRadiusIntMcs, g);
    	
        GeomUtil.setLtype(g, oldltype);
		
    	DrawUtil.drawCircleMcs(v, ptIns2dMcs, radiusExtMcs, g);
    	
		GeomUtil.setColor(g, oldcol);		
    }

    public void redraw2d_netLink_planView(ICadViewBase v, Color c, GeomPoint2d ptIns2dMcs, double sclFact, Graphics g) 
    {
		AppCadMain cad = AppCadMain.getCad();

		double arrowLengthSz = AppDefs.ARROWLENGTHSZ_SMALL * sclFact;
		double arrowWidthSz = AppDefs.ARROWWIDTHSZ_SMALL * sclFact;
		double arrowPointSz = AppDefs.ARROWPOINTSZ_SMALL * sclFact;
		
    	if(this.proximaCI != AppDefs.NULL_INT) {
    		CadDocumentDef doc = v.getDoc();
    		
    		LayerTable oTbl = doc.getLayerTable();
    		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_LIGACOES);
    		if( oLayer.isLayerOn() ) {
        		CadBlockDef oBlk = doc.getCurrBlockDef();        		
	    		CadEntity ent2 = oBlk.getEntity(this.proximaCI);
	    		if(ent2 != null) {
		    		if(ent2.getObjType() == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
		    			CadCaixaInspecaoDrenagem oEnt2 = (CadCaixaInspecaoDrenagem)ent2; 
		    			
		    			GeomPoint2d ptProximo2dMcs = new GeomPoint2d(oEnt2.ptIns);
		    			
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

        Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		AppCadMain cad = AppCadMain.getCad();
		
        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptInsDest2dMcs = new GeomPoint2d(this.ptIns);
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
		        	CadCaixaInspecaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadCaixaInspecaoDrenagem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }        
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadCaixaInspecaoDrenagem other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
			            radius = other.diametroMeter / 2.0;
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadCaixaInspecaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }
        
        String strDetailLevel = v.getDetailLevel();
        if( AppDefs.DEF_DETAILLEVEL_HIGH.equals(strDetailLevel) ) {
        	double dDiametroExternoTampaoMeter = this.diametroMeter;
        	double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
        	
        	double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
        	
        	double dDiametroTubulacaoMeter = this.diametroTubulacaoMeter / 1000.0; 

        	double dWidthTubulacaoMeter = 
        		this.qtdTubulacao * (DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM + dDiametroTubulacaoMeter) + 
        		DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM; 

        	double dWidthMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
        	double dWidthMeter2 = dWidthMeter / 2.0;
        	
        	double dHeightMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
        	double dHeightMeter2 = dHeightMeter / 2.0;
        	
        	double dWidthFinalMeter = dWidthMeter2 + dWidthMeter + dWidthMeter2;
        	double dHeightFinalMeter = dHeightMeter2 + dHeightMeter + dHeightMeter2;
            	
        	double dThicknessFinalMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_CAIXA_15CM;
        	
        	GeomVector2d vDir2d = new GeomVector2d(ptInsDest2dMcs.getX(), ptInsDest2dMcs.getY(), ptInsDest2dMcs.getX() + 1.0, ptInsDest2dMcs.getY());
        	CadCaixaInspecaoDrenagem oProximaCI = this.proximo;
        	if(oProximaCI != null) {
        		GeomPoint2d ptDir = new GeomPoint2d( oProximaCI.ptIns );
        		vDir2d = new GeomVector2d(ptInsDest2dMcs, ptDir);
        	}
        	this.redraw2d_highDetailView_planView(v, c, b, ptInsDest2dMcs, vDir2d, sclFact, dRaioExternoTampaoMeter, dEspessuraTampaoMeter, dWidthFinalMeter, dHeightFinalMeter, dThicknessFinalMeter, g);        	
        }
        else {
        	double dDiametroExternoTampaoMeter = this.diametroMeter;
        	double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
        	
        	double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
        	
	    	this.redraw2d_lowDetailView_planView(v, c, b, ptInsDest2dMcs, sclFact, dRaioExternoTampaoMeter, dEspessuraTampaoMeter, g);
	    }
    	
        this.redraw2d_netLink_planView(v, c, ptInsDest2dMcs, sclFact, g);
        
        if(bSelected || bHover) {
            GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);
        	DrawUtil.drawPointMcs(v, ptIns2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
        }
    }

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
    	if( !this.isVisible() ) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);
    	
    	GeomVector2d axisX = GeomUtil.axisX2d();
    	
    	double dDiametroExternoTampaoMeter = this.diametroMeter;
    	double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
    	
    	double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
    	
    	//double dDiametroTubulacaoMeter = this.diametroTubulacao / 1000.0;
    	double dDiametroTubulacaoMeter = this.diametroTubulacaoMeter;

    	double dWidthTubulacaoMeter = 
    		this.qtdTubulacao * (DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM + dDiametroTubulacaoMeter) + 
    		DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM; 

    	//WIDTH
    	//
    	double dWidthMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
    	double dWidthMeter2 = dWidthMeter / 2.0;
    	
    	double dWidthFinalMeter = dWidthMeter2 + dWidthMeter + dWidthMeter2;
    	double dWidthFinalMeter2 = dWidthFinalMeter / 2.0;
    	
    	//HEIGHT
    	//
        double dProfundidade = Math.abs( this.profundidade );		// profundidade = valor positivo
        
    	double dHeightMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
    	double dHeightMeter2 = dHeightMeter / 2.0;
    	
    	double dHeightFinalMeter = dHeightMeter2 + dHeightMeter + dHeightMeter2;
    	double dHeightFinalMeter2 = dHeightFinalMeter / 2.0;
        	
    	double dThicknessFinalMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_CAIXA_15CM;

        double dCT = this.getCt();
        double dCB = this.getCb();

        double dProfundidadeTampao = dProfundidade * 0.2;			// valor negativo
        if(dProfundidadeTampao > 1.0) {
        	dProfundidadeTampao = 1.0;
        }
        
        double dProfundidadeCaixa = dProfundidade - dProfundidadeTampao;

        GeomVector3d axisZ = new GeomVector3d(
        	this.ptIns.getX(), 
        	this.ptIns.getY(), 
        	dCT, 
        	this.ptIns.getX(), 
        	this.ptIns.getY(), 
        	dCT + 1.0); 
        
        GeomPoint3d ptTopTampao3d = new GeomPoint3d(this.ptIns.getX(), this.ptIns.getY(), dCT);        
        GeomPoint3d ptBaseTampao3d = ptTopTampao3d.otherMoveTo(axisZ, - dProfundidadeTampao);        
    	
    	GeomVector3d vDir3d = null;
    	CadCaixaInspecaoDrenagem oProximaCI = this.proximo;
    	if(oProximaCI != null) {
    		GeomPoint3d ptInsProximo = new GeomPoint3d( oProximaCI.getPtIns() );
    		
            GeomPoint3d ptBaseTampaoProximo3d = new GeomPoint3d(ptInsProximo.getX(), ptInsProximo.getY(), ptBaseTampao3d.getZ());             	
    		vDir3d = new GeomVector3d(ptBaseTampao3d, ptBaseTampaoProximo3d);
    	}
    	else {
            GeomPoint3d ptBaseTampaoProximo3d = new GeomPoint3d(this.ptIns.getX() + 1.0, this.ptIns.getY(), ptBaseTampao3d.getZ());             	
    		vDir3d = new GeomVector3d(ptBaseTampao3d, ptBaseTampaoProximo3d);
    	}
    	
    	double dDistToCenter = dWidthFinalMeter2 - (dRaioExternoTampaoMeter + dThicknessFinalMeter);
        GeomPoint3d ptTopCaixa3d = ptBaseTampao3d.otherMoveTo(vDir3d, - dDistToCenter);
    	GeomPoint3d ptBaseCaixa3d = ptTopCaixa3d.otherMoveTo(axisZ, - dProfundidadeCaixa);
        
        GeomVector2d vDir2d = new GeomVector2d(vDir3d);
        
        GeomVector2d uDir2d = vDir2d.otherUnit();
        double rotateRad = axisX.angleTo(uDir2d);
        
        prep.addCilinder(view3d, this, c, ptBaseTampao3d, axisZ, dProfundidadeTampao, dRaioExternoTampaoMeter, true, true);
        prep.addBox(view3d, this, c, ptBaseCaixa3d, dWidthFinalMeter, dHeightFinalMeter, dProfundidadeCaixa, rotateRad, null);
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

    	double zp = this.ptIns.getZ();
    	
    	GeomPoint2d ptIns = new GeomPoint2d(this.ptIns);    	
        double radius = this.diametroMeter / 2.0;
        
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
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_QUADRANT, pt2dMcs, lsPtQuadrant, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	double zp = this.ptIns.getZ();
    	
    	GeomPoint2d ptIns = new GeomPoint2d(this.ptIns);    	
        double radius = this.diametroMeter / 2.0;
        
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
	
	/* SEQID */
	
	public static void setSeqId(int seqId) {
		CadCaixaInspecaoDrenagem.gSeqId = seqId;
	}
	
	public static int nextSeqId() {
		CadCaixaInspecaoDrenagem.gSeqId += 1;
		return CadCaixaInspecaoDrenagem.gSeqId;
	}
	
	public static int currSeqId() {
		return CadCaixaInspecaoDrenagem.gSeqId;
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
		
	    String strIsFixedDiametro = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    String strIsFixedQtdTubulacao = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    String strIsFixedDiametroTubulacao = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    String strIsFixedDeclividade = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    String strIsFixedProfundidade = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    String strIsFixedCT = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    //
	    String strIsRoot = StringUtil.fromBoolToStr(this.bFixedDiametro);
	    String strIsFinish = StringUtil.fromBoolToStr(this.bFixedDiametro);
		
		Object[] arrVal = {
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
			new String( tipoCI ),
			new String( subtipoCI ),
			new Integer( numeroCI ),
			new Integer( proximaCI ),
			new Integer( numEstaca ),
			new Double( distEstaca ),
			new String( pv ),
			new Integer( localId ),
			new String( local ),
			new String( estaca ),
			//
			new Double(	areaExterna ),
			new Double(	areaLocal ),
			new Double(	areaTotal ),
			new Double(	areaTotalImp ),
			new Double(	diametroMeter ),
			new Double(	vazao ),
			new Double(	vazaoAcumulada ),  
		    new String( tipoSecaoTubulacao ),				    
		    new Integer( categoriaTubulacaoId ),
			new String( descricaoCategoriaTubulacao ),
			//
			new Double( diametroTubulacaoMeter ),
			new Integer( qtdTubulacao ),
			new Double( declividade ),
			new Double( coefImper ),
			new Double( profundidade ),
			new Double( comprTubulacao ),
			new Double( comprHorizTubulacao ),
			new Double( comprVertTubulacao ),
			new Double( ct ),
			new Double( cb ),
			new Double( cotaEntrada ),
			new Double( cotaSaida ),
		    //
		    new String( strIsFixedDiametro ),
		    new String( strIsFixedQtdTubulacao ),
		    new String( strIsFixedDiametroTubulacao ),
		    new String( strIsFixedDeclividade ),
		    new String( strIsFixedProfundidade ),
		    new String( strIsFixedCT ),
		    new String( strIsRoot ),
		    new String( strIsFinish )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadCaixaInspecaoDrenagemRecord entRec = new CadCaixaInspecaoDrenagemRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );

		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
		
	/* DXFR12_VIEW2D */

	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
    	ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
    	if(this.isDeleted()) return lsDxfCadEntity;
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		if(lsCadEntity2d != null)
			lsDxfCadEntity.addAll( lsCadEntity2d );

		ArrayList<DxfCadEntity> lsCadEntity3d = toDxfR12_view3d();
		if(lsCadEntity3d != null)
			lsDxfCadEntity.addAll( lsCadEntity3d );
		
		return lsDxfCadEntity;
	}

	/* DXFR12_VIEW2D */
	
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsEntity2d = new ArrayList<DxfCadEntity>(); 
    			
		CadDocumentDef doc = this.getDocument();
		
		LayerTable tbl = doc.getLayerTable();
		
        CadLayerDef oLayer_pontos_lowDetailLevel = tbl.getLayerDefByRef(AppDefs.LAYER_RPD_PONTOS);

        CadLayerDef oLayer_pontos_highDetailLevel = tbl.getLayerDefByRef(AppDefs.LAYER_RPD_PONTOS_HD);

        CadLayerDef oLayer_ligacoes = tbl.getLayerDefByRef(AppDefs.LAYER_RPD_LIGACOES);
        
        double sclFact = AppDefs.MCSPLAN_SCALEFACTOR;

        double radiusExtMcs = this.diametroMeter / 2.0;
        
    	double thicknessMcs = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;

    	double dRadiusIntMcs = radiusExtMcs - thicknessMcs;

        GeomPoint2d ptIns2dMcs = new GeomPoint2d(this.ptIns);
        double radius = this.diametroMeter / 2.0;
        
        //DETAILLEVEL_HIGH
        //
        double dDiametroExternoTampaoMeter = this.diametroMeter;
        double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
        	
        double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
        	
        double dDiametroTubulacaoMeter = this.diametroTubulacaoMeter / 1000.0; 

        double dWidthTubulacaoMeter = 
        		this.qtdTubulacao * (DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM + dDiametroTubulacaoMeter) + 
        		DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM; 

        double dWidthMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
        double dWidthMeter2 = dWidthMeter / 2.0;
        	
        double dHeightMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
        double dHeightMeter2 = dHeightMeter / 2.0;
        	
        double dWidthFinalMeter = dWidthMeter2 + dWidthMeter + dWidthMeter2;
        double dHeightFinalMeter = dHeightMeter2 + dHeightMeter + dHeightMeter2;
            	
        double dThicknessFinalMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_CAIXA_15CM;
       	
        GeomVector2d vDir2d = new GeomVector2d(ptIns2dMcs.getX(), ptIns2dMcs.getY(), ptIns2dMcs.getX() + 1.0, ptIns2dMcs.getY());
        CadCaixaInspecaoDrenagem oProximaCI = this.proximo;
        if(oProximaCI != null) {
        	GeomPoint2d ptDir = new GeomPoint2d( oProximaCI.ptIns );
        	vDir2d = new GeomVector2d(ptIns2dMcs, ptDir);
        }

        GeomVector3d vDir3d = new GeomVector3d( vDir2d );
        lsEntity2d.addAll( DxfUtil.toDxfCaixaInspecaoDrenagem_highDetailView_planView(oLayer_pontos_highDetailLevel, this, this.ptIns, vDir3d, dRadiusIntMcs, radiusExtMcs, thicknessMcs, radiusExtMcs, dRadiusIntMcs, thicknessMcs) );

        //DETAILLEVEL_LOW
        //
      	//double dDiametroExternoTampaoMeter = this.diametroMeter;
       	//double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
        	
       	//double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
        	
    	lsEntity2d.addAll( DxfUtil.toDxfCaixaInspecaoDrenagem_lowDetailView_planView(oLayer_pontos_lowDetailLevel, this, this.ptIns, dRadiusIntMcs, radiusExtMcs, thicknessMcs) );
    	
    	lsEntity2d.addAll( DxfUtil.toDxfCaixaInspecaoDrenagem_netLink_planView(oLayer_ligacoes, this, this.ptIns, sclFact) );
    	
    	return lsEntity2d;
	}

	/* DXFR12_VIEW3D */

	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d() {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 
		
		CadDocumentDef doc = this.getDocument();
		
		LayerTable tbl = doc.getLayerTable();
		
		CadLayerDef oLayer = tbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS_3D);

    	GeomVector2d axisX = GeomUtil.axisX2d();
    	
    	double dDiametroExternoTampaoMeter = this.diametroMeter;
    	double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
    	
    	double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
    	
    	//double dDiametroTubulacaoMeter = this.diametroTubulacao / 1000.0;
    	double dDiametroTubulacaoMeter = this.diametroTubulacaoMeter;

    	double dWidthTubulacaoMeter = 
    		this.qtdTubulacao * (DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM + dDiametroTubulacaoMeter) + 
    		DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM; 

    	//WIDTH
    	//
    	double dWidthMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
    	double dWidthMeter2 = dWidthMeter / 2.0;
    	
    	double dWidthFinalMeter = dWidthMeter2 + dWidthMeter + dWidthMeter2;
    	double dWidthFinalMeter2 = dWidthFinalMeter / 2.0;
    	
    	//HEIGHT
    	//
        double dProfundidade = Math.abs( this.profundidade );		// profundidade = valor positivo
        
    	double dHeightMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
    	double dHeightMeter2 = dHeightMeter / 2.0;
    	
    	double dHeightFinalMeter = dHeightMeter2 + dHeightMeter + dHeightMeter2;
    	double dHeightFinalMeter2 = dHeightFinalMeter / 2.0;
        	
    	double dThicknessFinalMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_CAIXA_15CM;

        double dCT = this.getCt();
        double dCB = this.getCb();

        double dProfundidadeTampao = dProfundidade * 0.2;			// valor negativo
        if(dProfundidadeTampao > 1.0) {
        	dProfundidadeTampao = 1.0;
        }
        
        double dProfundidadeCaixa = dProfundidade - dProfundidadeTampao;

        GeomVector3d axisZ = new GeomVector3d(
        	this.ptIns.getX(), 
        	this.ptIns.getY(), 
        	dCT, 
        	this.ptIns.getX(), 
        	this.ptIns.getY(), 
        	dCT + 1.0); 
        
        GeomPoint3d ptTopTampao3d = new GeomPoint3d(this.ptIns.getX(), this.ptIns.getY(), dCT);        
        GeomPoint3d ptBaseTampao3d = ptTopTampao3d.otherMoveTo(axisZ, - dProfundidadeTampao);        
    	
    	GeomVector3d vDir3d = null;
    	CadCaixaInspecaoDrenagem oProximaCI = this.proximo;
    	if(oProximaCI != null) {
    		GeomPoint3d ptInsProximo = new GeomPoint3d( oProximaCI.getPtIns() );
    		
            GeomPoint3d ptBaseTampaoProximo3d = new GeomPoint3d(ptInsProximo.getX(), ptInsProximo.getY(), ptBaseTampao3d.getZ());             	
    		vDir3d = new GeomVector3d(ptBaseTampao3d, ptBaseTampaoProximo3d);
    	}
    	else {
            GeomPoint3d ptBaseTampaoProximo3d = new GeomPoint3d(this.ptIns.getX() + 1.0, this.ptIns.getY(), ptBaseTampao3d.getZ());             	
    		vDir3d = new GeomVector3d(ptBaseTampao3d, ptBaseTampaoProximo3d);
    	}
    	
    	double dDistToCenter = dWidthFinalMeter2 - (dRaioExternoTampaoMeter + dThicknessFinalMeter);
        GeomPoint3d ptTopCaixa3d = ptBaseTampao3d.otherMoveTo(vDir3d, - dDistToCenter);
    	GeomPoint3d ptBaseCaixa3d = ptTopCaixa3d.otherMoveTo(axisZ, - dProfundidadeCaixa);
        
        GeomVector2d vDir2d = new GeomVector2d(vDir3d);
        
        GeomVector2d uDir2d = vDir2d.otherUnit();
        double rotateRad = axisX.angleTo(uDir2d);

        lsCadEntity3d.addAll( DxfUtil.toDxfCilinder(oLayer, this, ptBaseTampao3d, axisZ, dProfundidadeTampao, dRaioExternoTampaoMeter, true, true) );
        lsCadEntity3d.addAll( DxfUtil.toDxfBox(oLayer, this, ptBaseCaixa3d, dWidthFinalMeter, dHeightFinalMeter, dProfundidadeCaixa, rotateRad, null) );

        return lsCadEntity3d;
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
	public GeomPoint3d getExternalPoint3d(GeomPoint3d ptRef3d) {
		GeomPoint2d ptExt2d = this.getExternalPoint2d(new GeomPoint2d( ptRef3d ));
		double dCB = this.getCb();

        GeomPoint3d ptExtBase3d = new GeomPoint3d(ptExt2d.getX(), ptExt2d.getY(), dCB);
        return ptExtBase3d;
	}

	@Override	
	public GeomPoint2d getExternalPoint2d(GeomPoint2d ptRef2d){
    	double dDiametroExternoTampaoMeter = this.diametroMeter;
    	double dEspessuraTampaoMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM;
    	
    	double dRaioExternoTampaoMeter = dDiametroExternoTampaoMeter / 2.0;
    	
    	//double dDiametroTubulacaoMeter = this.diametroTubulacao / 1000.0;
    	double dDiametroTubulacaoMeter = this.diametroTubulacaoMeter;

    	double dWidthTubulacaoMeter = 
    		this.qtdTubulacao * 
    		(DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM + dDiametroTubulacaoMeter) + 
    		DrenagemCalc.DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM; 

    	//WIDTH
    	//
    	double dWidthMeter = Math.max(dDiametroExternoTampaoMeter, dWidthTubulacaoMeter);
    	double dWidthMeter2 = dWidthMeter / 2.0;
    	
    	double dWidthFinalMeter = dWidthMeter2 + dWidthMeter + dWidthMeter2;
    	double dWidthFinalMeter2 = dWidthFinalMeter / 2.0;
    	
    	//PT-INS
    	//
        GeomPoint2d ptIns2d = new GeomPoint2d( this.ptIns );        
    	GeomVector2d vDir2d = null;

    	CadCaixaInspecaoDrenagem oProximaCI = this.proximo;
    	if(oProximaCI != null) {
    		GeomPoint2d ptInsProximo = new GeomPoint2d( oProximaCI.getPtIns() );
    		vDir2d = new GeomVector2d(ptIns2d, ptInsProximo);
    	}
    	else {
            GeomPoint2d ptInsProximo = new GeomPoint2d(ptIns2d.getX() + 1.0, ptIns2d.getY() );             	
    		vDir2d = new GeomVector2d(ptIns2d, ptInsProximo);
    	}

    	GeomVector2d uDir2d = vDir2d.otherUnit();

    	GeomVector2d axisX = GeomUtil.axisX2d();    	
    	double angRad = axisX.angleTo(uDir2d);

    	//PT-CENTER
    	//double dThicknessFinalMeter = DrenagemCalc.DEF_DRENAGEM_ESPESSURA_CAIXA_15CM;

    	//double dDistToCenter = dWidthFinalMeter2 - (dRaioExternoTampaoMeter + dThicknessFinalMeter);
        //GeomPoint2d ptTopCaixa2d = ptIns2d.otherMoveTo(uDir2d, - dDistToCenter);
        
        GeomVector2d vPipeDir2d = new GeomVector2d(ptRef2d, ptIns2d);
        GeomVector2d uPipeDir2d = vPipeDir2d.otherUnit();
        
        double angPipeRad = uDir2d.angleTo(uPipeDir2d);
        double d = dWidthFinalMeter2 / Math.cos(angPipeRad);
        
        GeomPoint2d ptExt = ptIns2d.otherMoveTo(uPipeDir2d, d);
        return ptExt;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"TIPO=" + this.tipoCI + "^" +
			"SUBTIPO=" + this.subtipoCI + "^" +
			"PV=" + this.pv + "^" +
			"AREA_EXTERNA=" + Double.toString( this.areaExterna ) + "^" +
			"AREA_LOCAL=" + Double.toString( this.areaLocal ) + "^" +
			"AREA_TOTAL=" + Double.toString( this.areaTotal ) + "^" +
			"AREA_TOTAL_IMP=" + Double.toString( this.areaTotalImp ) + "^" +
			"COTA_TERRENO=" + Double.toString( this.ct ) + "^" +
			"COTA_FUNDO=" + Double.toString( this.cb ) + "^" +
			"PROFUNDIDADE=" + Double.toString( this.profundidade ) + "^" +
			"DECLIVIDADE=" + Double.toString( this.declividade ) + "^" +
			"RAIO=" + Double.toString( this.diametroMeter / 2.0 ) + "^" +
			"DIAMETRO=" + Double.toString( this.diametroMeter );
		return searchString;
	}

	public GeomPoint3d getPtIns() {
        return this.ptIns;
    }

    public double getDiametroMeter() {
        return this.diametroMeter;
    }

	public String getTipoCI() {
		return tipoCI;
	}

	public String getSubtipoCI() {
		return subtipoCI;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public int getProximaCI() {
		return proximaCI;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public double getDeclividade() {
		return declividade;
	}

	public double getVazao() {
		return vazao;
	}

	public double getVazaoAcumulada() {
		return vazaoAcumulada;
	}

	public int getQtdTubulacao() {
		return qtdTubulacao;
	}

	public double getDiametroTubulacaoMeter() {
		return diametroTubulacaoMeter;
	}

	public double getCotaEntrada() {
		return cotaEntrada;
	}

	public double getCotaSaida() {
		return cotaSaida;
	}

	public double getCt() {
		return ct;
	}

	public double getCb() {
		return cb;
	}

	public CadCaixaInspecaoDrenagem getProximo() {
		return proximo;
	}

    public void setProximo(CadCaixaInspecaoDrenagem proximo)
    {
        this.proximo = proximo;
    }

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public void setTipoCI(String tipoCI) {
		this.tipoCI = tipoCI;
	}

	public void setSubtipoCI(String subtipoCI) {
		this.subtipoCI = subtipoCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public void setProximaCI(int proximaCI) {
		this.proximaCI = proximaCI;
	}

	public void setDiametroMeter(double diametroMeter) {
		this.diametroMeter = diametroMeter;
	}

	public void setVazao(double vazao) {
		this.vazao = vazao;
	}

	public void setVazaoAcumulada(double vazaoAcumulada) {
		this.vazaoAcumulada = vazaoAcumulada;
	}

	public void setQtdTubulacao(int qtdTubulacao) {
		this.qtdTubulacao = qtdTubulacao;
	}

	public void setDiametroTubulacaoMeter(double diametroTubulacaoMeter) {
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getComprTubulacao() {
		return comprTubulacao;
	}

	public void setComprTubulacao(double comprTubulacao) {
		this.comprTubulacao = comprTubulacao;
	}

	public double getComprHorizTubulacao() {
		return comprHorizTubulacao;
	}

	public void setComprHorizTubulacao(double comprHorizTubulacao) {
		this.comprHorizTubulacao = comprHorizTubulacao;
	}

	public double getComprVertTubulacao() {
		return comprVertTubulacao;
	}

	public void setComprVertTubulacao(double comprVertTubulacao) {
		this.comprVertTubulacao = comprVertTubulacao;
	}

	public void setCotaEntrada(double cotaEntrada) {
		this.cotaEntrada = cotaEntrada;
	}

	public void setCotaSaida(double cotaSaida) {
		this.cotaSaida = cotaSaida;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public void setCb(double cb) {
		this.cb = cb;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEstaca() {
		return estaca;
	}

	public void setEstaca(String estaca) {
		this.estaca = estaca;
	}

	public double getCoefImper() {
		return coefImper;
	}

	public void setCoefImper(double coefImper) {
		this.coefImper = coefImper;
	}

	public int getNumEstaca() {
		return numEstaca;
	}

	public void setNumEstaca(int numEstaca) {
		this.numEstaca = numEstaca;
	}

	public double getDistEstaca() {
		return distEstaca;
	}

	public void setDistEstaca(double distEstaca) {
		this.distEstaca = distEstaca;
	}

	public boolean isFixedDiametro() {
		return bFixedDiametro;
	}

	public void setFixedDiametro(boolean bFixedDiametro) {
		this.bFixedDiametro = bFixedDiametro;
	}

	public boolean isFixedQtdTubulacao() {
		return bFixedQtdTubulacao;
	}

	public void setFixedQtdTubulacao(boolean bFixedQtdTubulacao) {
		this.bFixedQtdTubulacao = bFixedQtdTubulacao;
	}

	public boolean isFixedDiametroTubulacao() {
		return bFixedDiametroTubulacao;
	}

	public void setFixedDiametroTubulacao(boolean bFixedDiametroTubulacao) {
		this.bFixedDiametroTubulacao = bFixedDiametroTubulacao;
	}

	public boolean isFixedProfundidade() {
		return bFixedProfundidade;
	}

	public void setFixedProfundidade(boolean bFixedProfundidade) {
		this.bFixedProfundidade = bFixedProfundidade;
	}

	public boolean isFixedCT() {
		return bFixedCT;
	}

	public void setFixedCT(boolean bFixedCT) {
		this.bFixedCT = bFixedCT;
	}

	public boolean isFixedDeclividade() {
		return bFixedDeclividade;
	}

	public void setFixedDeclividade(boolean bFixedDeclividade) {
		this.bFixedDeclividade = bFixedDeclividade;
	}

	public void setAnterior(ArrayList<CadCaixaInspecaoDrenagem> lsAnterior) {
		this.lsAnterior = lsAnterior;
	}
	
	public ArrayList<CadCaixaInspecaoDrenagem> getLsAnterior() {
		return lsAnterior;
	}

    public void addAnterior(CadCaixaInspecaoDrenagem caixaInspecao)
    {
    	int numeroCI = caixaInspecao.numeroCI;
    	
        CadCaixaInspecaoDrenagem o = findCIByNumeroCI(this.lsAnterior, numeroCI);    	
    	if(o == null) {
    		this.lsAnterior.add(caixaInspecao);
    	}
    }

    public void removeAnterior(CadCaixaInspecaoDrenagem oCI)
    {
    	int numeroCI = oCI.numeroCI;
    	
        CadCaixaInspecaoDrenagem o = findCIByNumeroCI(this.lsAnterior, numeroCI);    	
    	if(o == null) {
    		this.lsAnterior.add(oCI);
    	}
    }

	public boolean isRoot() {
		return bRoot;
	}

	public void setRoot(boolean bRoot) {
		this.bRoot = bRoot;
	}

	public boolean isFinish() {
		return bFinish;
	}

	public void setFinish(boolean bFinish) {
		this.bFinish = bFinish;
	}

	public double getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(double areaTotal) {
		this.areaTotal = areaTotal;
	}

	public double getAreaTotalImp() {
		return areaTotalImp;
	}

	public void setAreaTotalImp(double areaTotalImp) {
		this.areaTotalImp = areaTotalImp;
	}

	public double getAreaExterna() {
		return areaExterna;
	}

	public double getAreaLocal() {
		return areaLocal;
	}

	public void setAreaExterna(double areaExterna) {
		this.areaExterna = areaExterna;
	}

	public void setAreaLocal(double areaLocal) {
		this.areaLocal = areaLocal;
	}

	public int getCategoriaTubulacaoId() {
		return categoriaTubulacaoId;
	}

	public void setCategoriaTubulacaoId(int categoriaTubulacaoId) {
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	}

	public String getDescricaoCategoriaTubulacao() {
		return descricaoCategoriaTubulacao;
	}

	public void setDescricaoCategoriaTubulacao(String descricaoCategoriaTubulacao) {
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	}

	public String getTipoSecaoTubulacao() {
		return tipoSecaoTubulacao;
	}

	public void setTipoSecaoTubulacao(String tipoSecaoTubulacao) {
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
	}
	
	public void setEstaca(int numEstaca, double distEstaca) {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf2 = FormatUtil.newNumberFormatWithoutGroupingPtBr(2);
		
		this.setNumEstaca(numEstaca);
		this.setDistEstaca(distEstaca);
		
		String strEstaca = String.format("%s+%sm", nf0.format( this.numEstaca ), nf2.format( this.distEstaca ));
		this.setEstaca(strEstaca);		
	}
	
}
