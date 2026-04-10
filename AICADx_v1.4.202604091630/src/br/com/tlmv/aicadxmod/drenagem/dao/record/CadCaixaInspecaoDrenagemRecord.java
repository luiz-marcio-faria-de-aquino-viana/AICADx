/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadCaixaInspecaoDrenagemRecord.java
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

package br.com.tlmv.aicadxmod.drenagem.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CadCaixaInspecaoDrenagemRecord extends BaseEntityRecord 
{
//Public

	/* SQL */

	@Override
	public String getSqlTableName() {
		return sqlTableName;
	}
	
	@Override
	public SqlColumnVO[] getSqlColumn() {
		return sqlColumn;
	}

//Public Static
	
	public static final String sqlTableName = "cad_caixa_inspecao_drenagem";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptins_x", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_y", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_z", 							AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("tipo_ci", 							AppDefs.TAG_SQLTYPE_STR),			// _ESGOTO_ / _APLUVIAL_
		new SqlColumnVO("subtipo_ci", 						AppDefs.TAG_SQLTYPE_STR),			// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_
		new SqlColumnVO("numero_ci", 						AppDefs.TAG_SQLTYPE_INT),			// [id_caixa_inspecao]
		new SqlColumnVO("proxima_ci", 						AppDefs.TAG_SQLTYPE_INT),			// [id_proxima_caixa_inspecao]
		new SqlColumnVO("num_estaca", 						AppDefs.TAG_SQLTYPE_INT),			// NumEstaca = 2
		new SqlColumnVO("dist_estaca", 						AppDefs.TAG_SQLTYPE_DBL),			// DistEstaca = 1.70 m
		new SqlColumnVO("pv", 								AppDefs.TAG_SQLTYPE_STR),			// PV-A2.1
		new SqlColumnVO("local_id", 						AppDefs.TAG_SQLTYPE_INT),			// 1001 - RUA DR. MARIO MACHADO
		new SqlColumnVO("local", 							AppDefs.TAG_SQLTYPE_STR),			// RUA DR. MARIO MACHADO
		new SqlColumnVO("estaca", 							AppDefs.TAG_SQLTYPE_STR),			// 2 + 1.70 m
	    //
	    new SqlColumnVO("area_externa", 					AppDefs.TAG_SQLTYPE_DBL),			// AreaExterna = SOMA(AreaTotal_Anterior)
		new SqlColumnVO("area_local",  						AppDefs.TAG_SQLTYPE_DBL),			// AreaLocal = 0.220 ha
		new SqlColumnVO("area_total", 						AppDefs.TAG_SQLTYPE_DBL),			// AreaTotal = AreaExterna + AreaLocal
		new SqlColumnVO("area_total_imp", 					AppDefs.TAG_SQLTYPE_DBL),			// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
		new SqlColumnVO("diametro", 						AppDefs.TAG_SQLTYPE_DBL),			// Diametro = 0.60
		new SqlColumnVO("vazao", 							AppDefs.TAG_SQLTYPE_DBL),			// Vazao
		new SqlColumnVO("vazao_acumulada", 					AppDefs.TAG_SQLTYPE_DBL),			// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
		new SqlColumnVO("tipo_secao_tubulacao", 			AppDefs.TAG_SQLTYPE_STR),			// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
		new SqlColumnVO("categoria_tubulacao_id", 			AppDefs.TAG_SQLTYPE_INT),			// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
		new SqlColumnVO("descricao_categoria_tubulacao", 	AppDefs.TAG_SQLTYPE_STR),			// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
	    //
	    new SqlColumnVO("diametro_tubulacao", 				AppDefs.TAG_SQLTYPE_DBL),			// DiametroTubulacao = 250 mm (0.25 m)
		new SqlColumnVO("qtd_tubulacao", 					AppDefs.TAG_SQLTYPE_INT),			// QtdTubulacao = 1, 2, 3, 4...
		new SqlColumnVO("declividade", 						AppDefs.TAG_SQLTYPE_DBL),			// Declividade = 0.00160
		new SqlColumnVO("coef_imper", 						AppDefs.TAG_SQLTYPE_DBL),			// CoefImper = 0.80
		new SqlColumnVO("profundidade", 					AppDefs.TAG_SQLTYPE_DBL),			// Profundidade = -0.60 m
		new SqlColumnVO("compr_tubulacao", 					AppDefs.TAG_SQLTYPE_DBL),			// ComprTubulacao = 50.0 m
		new SqlColumnVO("compr_horiz_tubulacao", 			AppDefs.TAG_SQLTYPE_DBL),			// ComprHorizTubulacao = 40.0 m
		new SqlColumnVO("compr_vert_tubulacao", 			AppDefs.TAG_SQLTYPE_DBL),			// ComprVertTubulacao = 30.0 m
		new SqlColumnVO("ct", 								AppDefs.TAG_SQLTYPE_DBL),			// CotaTerreno = 2.841 m
		new SqlColumnVO("cb", 								AppDefs.TAG_SQLTYPE_DBL),			// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
	    //
		new SqlColumnVO("cota_entrada", 					AppDefs.TAG_SQLTYPE_DBL),			// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m
		new SqlColumnVO("cota_saida",						AppDefs.TAG_SQLTYPE_DBL),			// CotaSaida = Fundo + (Diametro / 2.0) = 1.966
		new SqlColumnVO("fixed_diametro", 					AppDefs.TAG_SQLTYPE_BOOL),			// FIXED_PARAM: Diametro
		new SqlColumnVO("fixed_qtd_tubulacao", 				AppDefs.TAG_SQLTYPE_BOOL),			// FIXED_PARAM: QtdTubulacao
		new SqlColumnVO("fixed_diametro_tubulacao", 		AppDefs.TAG_SQLTYPE_BOOL),			// FIXED_PARAM: DiametroTubulacao
		new SqlColumnVO("fixed_declividade", 				AppDefs.TAG_SQLTYPE_BOOL),			// FIXED_PARAM: Declividade
		new SqlColumnVO("fixed_profundidade", 				AppDefs.TAG_SQLTYPE_BOOL),			// FIXED_PARAM: Profundidade
		new SqlColumnVO("fixed_ct", 						AppDefs.TAG_SQLTYPE_BOOL),			// FIXED_PARAM: CT
		new SqlColumnVO("is_root", 							AppDefs.TAG_SQLTYPE_BOOL),			// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
		new SqlColumnVO("is_finish", 						AppDefs.TAG_SQLTYPE_BOOL)			// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
		
	};
	
//Private
	long oid;
	//
	int objectId;
	int objType;
	String objTypeStr;
	String objVer;
	//
	String cadRefEntityId;
    //
	String strIsDeleted;
	String strIsLocked;	
	//
	String reference;
	String levelName;
	//
    double zLevel;
	//
    double ptInsX;
    double ptInsY;
    double ptInsZ;
    //
    String tipoCI;								// _ESGOTO_ / _APLUVIAL_
    String subtipoCI;							// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
    int numeroCI;								// [id_caixa_inspecao]
    int proximaCI;								// [id_proxima_caixa_inspecao]
    int numEstaca;								// NumEstaca = 2
    double distEstaca;							// DistEstaca = 1.70 m
	String pv;									// PV-A2.1
	int localId;								// 1001 - RUA DR. MARIO MACHADO
	String local;								// RUA DR. MARIO MACHADO
	String estaca;								// 2 + 1.70 m
    //
	double areaExterna;							// AreaExterna = SOMA(AreaTotal_Anterior)
	double areaLocal;							// AreaLocal = 0.220 ha
	double areaTotal;							// AreaTotal = AreaExterna + AreaLocal
	double areaTotalImp;						// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
	double diametroMeter;						// Diametro = 0.60
    double vazao;								// VazaoCalculada
    double vazaoAcumulada;    					// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
    String tipoSecaoTubulacao;					// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
    int categoriaTubulacaoId;					// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
    String descricaoCategoriaTubulacao;			// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
    //
    double diametroTubulacaoMeter;				// DiametroTubulacao = 250 mm (0.25 m)
    int qtdTubulacao;							// QtdTubulacao = 1, 2, 3, 4...
    double declividade;							// Declividade = 0.00160
	double coefImper;							// CoefImper = 0.80
    double profundidade;						// Profundidade = -0.60 m
    double comprTubulacao;						// ComprTubulacao = 50.0 m
    double comprHorizTubulacao;					// ComprHorizTubulacao = 40.0 m
    double comprVertTubulacao;					// ComprVertTubulacao = 30.0 m
    double ct;									// CotaTerreno = 2.841 m
    double cb;									// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
    //
    double cotaEntrada;							// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
    double cotaSaida;							// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
    String fixedDiametro;						// FIXED_PARAM: Diametro
    String fixedQtdTubulacao;					// FIXED_PARAM: QtdTubulacao
    String fixedDiametroTubulacao;				// FIXED_PARAM: DiametroTubulacao
    String fixedDeclividade;					// FIXED_PARAM: Declividade
    String fixedProfundidade;					// FIXED_PARAM: Profundidade
    String fixedCT;								// FIXED_PARAM: CT
    String isRoot;								// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
    String isFinish;							// Caixa de Inspecao (ou Poco de Visita) Final do Trecho

//Public
	
	public CadCaixaInspecaoDrenagemRecord()
	{
		this.init(
			AppDefs.NULL_LNG, 
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_INTSTR,
		    //
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			//
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
		    //
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
		    //
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
		    //
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO );
		
	}
	
	public CadCaixaInspecaoDrenagemRecord(CadCaixaInspecaoDrenagem o)
	{
		this.init(o);
	}
	
	public CadCaixaInspecaoDrenagemRecord(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
	}
	
	/* Methodes */
	
	public void init(
		long oid,
		//
		int objectId,
		int objType,
		String objTypeStr,
		String objVer,
		//
		String cadRefEntityId,
	    //
		String strIsDeleted,
		String strIsLocked,		
		//
		String reference,
		String levelName,
		//
	    double zLevel,
		//
	    double ptInsX,
	    double ptInsY,
	    double ptInsZ,
	    //
	    String tipoCI,								// _ESGOTO_ / _APLUVIAL_
	    String subtipoCI,							// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
	    int numeroCI,								// [id_caixa_inspecao]
	    int proximaCI,								// [id_proxima_caixa_inspecao]
	    int numEstaca,								// NumEstaca = 2
	    double distEstaca,							// DistEstaca = 1.70 m
		String pv,									// PV-A2.1
		int localId,								// 1001 - RUA DR. MARIO MACHADO
		String local,								// RUA DR. MARIO MACHADO
		String estaca,								// 2 + 1.70 m
	    //
		double areaExterna,							// AreaExterna = SOMA(AreaTotal_Anterior)
		double areaLocal,							// AreaLocal = 0.220 ha
		double areaTotal,							// AreaTotal = AreaExterna + AreaLocal
		double areaTotalImp,						// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
		double diametroMeter,						// Diametro = 0.60
	    double vazao,								// VazaoCalculada
	    double vazaoAcumulada,    					// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
	    String tipoSecaoTubulacao,					// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
	    int categoriaTubulacaoId, 					// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
	    String descricaoCategoriaTubulacao,			// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
	    //
	    double diametroTubulacaoMeter,				// DiametroTubulacao = 250 mm (0.25 m)
	    int qtdTubulacao,							// QtdTubulacao = 1, 2, 3, 4...
	    double declividade,							// Declividade = 0.00160
		double coefImper,							// CoefImper = 0.80
	    double profundidade,						// Profundidade = -0.60 m
	    double comprTubulacao,						// ComprTubulacao = 50.0 m
	    double comprHorizTubulacao,					// ComprHorizTubulacao = 40.0 m
	    double comprVertTubulacao,					// ComprVertTubulacao = 30.0 m
	    double ct,									// CotaTerreno = 2.841 m
	    double cb,									// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
	    //
	    double cotaEntrada,							// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
	    double cotaSaida,							// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
	    String fixedDiametro,						// FIXED_PARAM: Diametro
	    String fixedQtdTubulacao,					// FIXED_PARAM: QtdTubulacao
	    String fixedDiametroTubulacao,				// FIXED_PARAM: DiametroTubulacao
	    String fixedDeclividade,					// FIXED_PARAM: Declividade
	    String fixedProfundidade,					// FIXED_PARAM: Profundidade
	    String fixedCT,								// FIXED_PARAM: CT
	    String isRoot,								// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
	    String isFinish )							// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	{
    	super.initEntity(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		strIsDeleted,
    		strIsLocked,
    		//
    		reference, 
    		levelName,
    		//
    		zLevel );
		
	    this.ptInsX = ptInsX;
	    this.ptInsY = ptInsY;
	    this.ptInsZ = ptInsZ;
	    //
	    this.tipoCI = tipoCI;												// _ESGOTO_ / _APLUVIAL_
	    this.subtipoCI = subtipoCI;											// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
	    this.numeroCI = numeroCI;											// [id_caixa_inspecao]
	    this.proximaCI = proximaCI;											// [id_proxima_caixa_inspecao]
	    this.numEstaca = numEstaca;											// NumEstaca = 2
	    this.distEstaca = distEstaca;										// DistEstaca = 1.70 m
		this.pv = pv;														// PV-A2.1
		this.localId = localId;												// 1001 - RUA DR. MARIO MACHADO
		this.local = local;													// RUA DR. MARIO MACHADO
		this.estaca = estaca;												// 2 + 1.70 m
	    //
		this.areaExterna = areaExterna;										// AreaExterna = SOMA(AreaTotal_Anterior)
		this.areaLocal = areaLocal;											// AreaLocal = 0.220 ha
		this.areaTotal = areaTotal;											// AreaTotal = AreaExterna + AreaLocal
		this.areaTotalImp = areaTotalImp;									// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
	    this.diametroMeter = diametroMeter;									// Diametro = 0.60
	    this.vazao = vazao;													// VazaoCalculada
	    this.vazaoAcumulada = vazaoAcumulada;    							// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
	    this.tipoSecaoTubulacao = tipoSecaoTubulacao;						// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
	    this.categoriaTubulacaoId = categoriaTubulacaoId; 					// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
	    this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;		// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
	    //
	    this.diametroTubulacaoMeter = diametroTubulacaoMeter;				// DiametroTubulacao = 250 mm (0.25 m)
	    this.qtdTubulacao = qtdTubulacao;									// QtdTubulacao = 1, 2, 3, 4...
	    this.declividade = declividade;										// Declividade = 0.00160
		this.coefImper = coefImper;											// CoefImper = 0.80
	    this.profundidade = profundidade;									// Profundidade = -0.60 m
	    this.comprTubulacao = comprTubulacao;								// ComprTubulacao = 50.0 m
	    this.comprHorizTubulacao = comprHorizTubulacao;						// ComprHorizTubulacao = 40.0 m
	    this.comprVertTubulacao = comprVertTubulacao;						// ComprVertTubulacao = 30.0 m
	    this.ct = ct;														// CotaTerreno = 2.841 m
	    this.cb = cb;														// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
	    //
	    this.cotaEntrada = cotaEntrada;										// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
	    this.cotaSaida = cotaSaida;											// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
	    this.fixedDiametro = fixedDiametro;									// FIXED_PARAM: Diametro
	    this.fixedQtdTubulacao = fixedQtdTubulacao;							// FIXED_PARAM: QtdTubulacao
	    this.fixedDiametroTubulacao = fixedDiametroTubulacao;				// FIXED_PARAM: DiametroTubulacao
	    this.fixedDeclividade = fixedDeclividade;							// FIXED_PARAM: Declividade
	    this.fixedProfundidade = fixedProfundidade;							// FIXED_PARAM: Profundidade
	    this.fixedCT = fixedCT;												// FIXED_PARAM: CT
	    this.isRoot = isRoot;												// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
	    this.isFinish = isFinish;											// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	}
	
	public void init(CadCaixaInspecaoDrenagem o)
	{
    	// LAYER_DEF
    	//
    	CadLayerDef oLayer = o.getLayer();
    	String reference = oLayer.getReference(); 
		
    	// LEVEL
    	//
    	CadLevel oLevel = o.getLevel();
    	String levelName = AppDefs.DEFAULT_LEVELNAME;
    	if(oLevel != null)
        	levelName = oLevel.getLevelLocalName();
    		
		GeomPoint3d ptIns = o.getPtIns();
		
		double ptInsX = ptIns.getX();
		double ptInsY = ptIns.getY();
		double ptInsZ = ptIns.getZ();

		String strIsFixedDiametro = StringUtil.fromBoolToStr( o.isFixedDiametro() );
		String strIsFixedQtdTubulacao = StringUtil.fromBoolToStr( o.isFixedQtdTubulacao() );
		String strIsFixedDiametroTubulacao = StringUtil.fromBoolToStr( o.isFixedDiametroTubulacao() );
		String strIsFixedDeclividade = StringUtil.fromBoolToStr( o.isFixedDeclividade() );
		String strIsFixedProfundidade = StringUtil.fromBoolToStr( o.isFixedProfundidade() );
		//
		String strIsFixedCT = StringUtil.fromBoolToStr( o.isFixedCT() );
		String strIsRoot = StringUtil.fromBoolToStr( o.isRoot() );
		String strIsFinish = StringUtil.fromBoolToStr( o.isFinish() );
	    //
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
		String strIsLocked = StringUtil.fromBoolToStr( o.isLocked() );
		
		this.init(
			AppDefs.NULL_LNG,
			//
			o.getObjectId(), 
			o.getObjType(), 
			o.getObjTypeStr(), 
			o.getObjVer(),
		    //
			o.getCadRefEntityId(),
		    //
			strIsDeleted,
			strIsLocked, 
			//
			reference, 
			levelName,
			//
			o.getZLevel(),
			//
		    ptInsX,
		    ptInsY,
		    ptInsZ,
		    //
		    o.getTipoCI(),
		    o.getSubtipoCI(),
		    o.getNumeroCI(),
		    o.getProximaCI(),
		    o.getNumEstaca(),
		    o.getDistEstaca(),
		    o.getPv(),
		    o.getLocalId(),
		    o.getLocal(),
		    o.getEstaca(),
			//
		    o.getAreaExterna(),
		    o.getAreaLocal(),
		    o.getAreaTotal(),
		    o.getAreaTotalImp(),
		    o.getDiametroMeter(),
		    o.getVazao(),
		    o.getVazaoAcumulada(),
		    o.getTipoSecaoTubulacao(),
		    o.getCategoriaTubulacaoId(),
		    o.getDescricaoCategoriaTubulacao(),
			//
		    o.getDiametroTubulacaoMeter(),
		    o.getQtdTubulacao(),
		    o.getDeclividade(),
		    o.getCoefImper(),
		    o.getProfundidade(),
		    o.getComprTubulacao(),
		    o.getComprHorizTubulacao(),
		    o.getComprVertTubulacao(),
		    o.getCt(),
		    o.getCb(),
			//
		    o.getCotaEntrada(),
		    o.getCotaSaida(),
			strIsFixedDiametro,
			strIsFixedQtdTubulacao,
			strIsFixedDiametroTubulacao,
			strIsFixedDeclividade,
			strIsFixedProfundidade,
			strIsFixedCT,
			strIsRoot,
			strIsFinish );
		
	}
	
    @Override
	public void init(DbUtil o)
	{
		this.initObj(o);

		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setTipoCI( o.getNextStr() );
		this.setSubtipoCI( o.getNextStr() );
		this.setNumeroCI( o.getNextInt() );
		this.setProximaCI( o.getNextInt() );
		this.setNumEstaca( o.getNextInt() );
		this.setDistEstaca( o.getNextDbl() );
		this.setPv( o.getNextStr() );
		this.setLocalId( o.getNextInt() );
		this.setLocal( o.getNextStr() );
		this.setEstaca( o.getNextStr() );
		//
		this.setAreaExterna( o.getNextDbl() );
		this.setAreaLocal( o.getNextDbl() );
		this.setAreaTotal( o.getNextDbl() );
		this.setAreaTotalImp( o.getNextDbl() );
		this.setDiametroMeter( o.getNextDbl() );
		this.setVazao( o.getNextDbl() );
		this.setVazaoAcumulada( o.getNextDbl() );
		this.setTipoSecaoTubulacao( o.getNextStr() );
		this.setCategoriaTubulacaoId( o.getNextInt() );
		this.setDescricaoCategoriaTubulacao( o.getNextStr() );
		//
		this.setDiametroTubulacaoMeter( o.getNextDbl() );
		this.setQtdTubulacao( o.getNextInt() );
		this.setDeclividade( o.getNextDbl() );
		this.setCoefImper( o.getNextDbl() );
		this.setProfundidade( o.getNextDbl() );
		this.setComprTubulacao( o.getNextDbl() );
		this.setComprHorizTubulacao( o.getNextDbl() );
		this.setComprVertTubulacao( o.getNextDbl() );
		this.setCt( o.getNextDbl() );
		this.setCb( o.getNextDbl() );
		//
		this.setCotaEntrada( o.getNextDbl() );
		this.setCotaSaida( o.getNextDbl() );		
	    this.setFixedDiametro( o.getNextStr() );
	    this.setFixedQtdTubulacao( o.getNextStr() );
	    this.setFixedDiametroTubulacao( o.getNextStr() );
	    this.setFixedDeclividade( o.getNextStr() );
	    this.setFixedProfundidade( o.getNextStr() );
	    this.setFixedCT( o.getNextStr() );
	    this.setIsRoot( o.getNextStr() );
	    this.setIsFinish( o.getNextStr() );
	}
	
	/* TO_CADxxx */
	
	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadCaixaInspecaoDrenagem o = (CadCaixaInspecaoDrenagem)super.toCadObject(oBlkDef, this.getClass());

		String strIsFixedDiametro = StringUtil.fromBoolToStr( o.isFixedDiametro() );
		String strIsFixedQtdTubulacao = StringUtil.fromBoolToStr( o.isFixedQtdTubulacao() );
		String strIsFixedDiametroTubulacao = StringUtil.fromBoolToStr( o.isFixedDiametroTubulacao() );
		String strIsFixedDeclividade = StringUtil.fromBoolToStr( o.isFixedDeclividade() );
		String strIsFixedProfundidade = StringUtil.fromBoolToStr( o.isFixedProfundidade() );
		//
		String strIsFixedCT = StringUtil.fromBoolToStr( o.isFixedCT() );
		String strIsRoot = StringUtil.fromBoolToStr( o.isRoot() );
		String strIsFinish = StringUtil.fromBoolToStr( o.isFinish() );
	    //
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
		String strIsLocked = StringUtil.fromBoolToStr( o.isLocked() );
		
    	o.init(
			this.ptInsX, 
			this.ptInsY, 
			this.ptInsZ,
		    //
			this.getTipoCI(),
			this.getSubtipoCI(),
			this.getNumeroCI(),
			this.getProximaCI(),
			this.getNumEstaca(),
			this.getDistEstaca(),
			this.getPv(),
			this.getLocalId(),
			this.getLocal(),
			this.getEstaca(),
			//
			this.getAreaExterna(),
			this.getAreaLocal(),
			this.getAreaTotal(),
			this.getAreaTotalImp(),
			this.getDiametroMeter(),
			this.getVazao(),
			this.getVazaoAcumulada(),
			this.getTipoSecaoTubulacao(),
			this.getCategoriaTubulacaoId(),
			this.getDescricaoCategoriaTubulacao(),
			//
			this.getDiametroTubulacaoMeter(),
			this.getQtdTubulacao(),
			this.getDeclividade(),
			this.getCoefImper(),
			this.getProfundidade(),
			this.getComprTubulacao(),
			this.getComprHorizTubulacao(),
			this.getComprVertTubulacao(),
			this.getCt(),
			this.getCb(),
			//
			this.getCotaEntrada(),
			this.getCotaSaida(),
			strIsFixedDiametro,
			strIsFixedQtdTubulacao,
			strIsFixedDiametroTubulacao,
			strIsFixedDeclividade,
			strIsFixedProfundidade,
			strIsFixedCT,
			strIsRoot,
			strIsFinish );
	    return o;
	}
	
	/* Getters/Setters */

	public double getPtInsX() {
		return ptInsX;
	}

	public void setPtInsX(double ptInsX) {
		this.ptInsX = ptInsX;
	}

	public double getPtInsY() {
		return ptInsY;
	}

	public void setPtInsY(double ptInsY) {
		this.ptInsY = ptInsY;
	}

	public double getPtInsZ() {
		return ptInsZ;
	}

	public void setPtInsZ(double ptInsZ) {
		this.ptInsZ = ptInsZ;
	}

	public String getTipoCI() {
		return tipoCI;
	}

	public void setTipoCI(String tipoCI) {
		this.tipoCI = tipoCI;
	}

	public String getSubtipoCI() {
		return subtipoCI;
	}

	public void setSubtipoCI(String subtipoCI) {
		this.subtipoCI = subtipoCI;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public int getProximaCI() {
		return proximaCI;
	}

	public void setProximaCI(int proximaCI) {
		this.proximaCI = proximaCI;
	}

	public double getDiametroMeter() {
		return diametroMeter;
	}

	public void setDiametroMeter(double diametroMeter) {
		this.diametroMeter = diametroMeter;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getDeclividade() {
		return declividade;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
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

	public double getVazao() {
		return vazao;
	}

	public void setVazao(double vazao) {
		this.vazao = vazao;
	}

	public double getVazaoAcumulada() {
		return vazaoAcumulada;
	}

	public void setVazaoAcumulada(double vazaoAcumulada) {
		this.vazaoAcumulada = vazaoAcumulada;
	}

	public int getQtdTubulacao() {
		return qtdTubulacao;
	}

	public void setQtdTubulacao(int qtdTubulacao) {
		this.qtdTubulacao = qtdTubulacao;
	}

	public double getDiametroTubulacaoMeter() {
		return diametroTubulacaoMeter;
	}

	public void setDiametroTubulacaoMeter(double diametroTubulacaoMeter) {
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
	}

	public double getCoefImper() {
		return coefImper;
	}

	public void setCoefImper(double coefImper) {
		this.coefImper = coefImper;
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

	public double getCt() {
		return ct;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public double getCb() {
		return cb;
	}

	public void setCb(double cb) {
		this.cb = cb;
	}

	public double getCotaEntrada() {
		return cotaEntrada;
	}

	public void setCotaEntrada(double cotaEntrada) {
		this.cotaEntrada = cotaEntrada;
	}

	public double getCotaSaida() {
		return cotaSaida;
	}

	public void setCotaSaida(double cotaSaida) {
		this.cotaSaida = cotaSaida;
	}

	public String getFixedDiametro() {
		return fixedDiametro;
	}

	public void setFixedDiametro(String fixedDiametro) {
		this.fixedDiametro = fixedDiametro;
	}

	public String getFixedQtdTubulacao() {
		return fixedQtdTubulacao;
	}

	public void setFixedQtdTubulacao(String fixedQtdTubulacao) {
		this.fixedQtdTubulacao = fixedQtdTubulacao;
	}

	public String getFixedDiametroTubulacao() {
		return fixedDiametroTubulacao;
	}

	public void setFixedDiametroTubulacao(String fixedDiametroTubulacao) {
		this.fixedDiametroTubulacao = fixedDiametroTubulacao;
	}

	public String getFixedDeclividade() {
		return fixedDeclividade;
	}

	public void setFixedDeclividade(String fixedDeclividade) {
		this.fixedDeclividade = fixedDeclividade;
	}

	public String getFixedProfundidade() {
		return fixedProfundidade;
	}

	public void setFixedProfundidade(String fixedProfundidade) {
		this.fixedProfundidade = fixedProfundidade;
	}

	public String getFixedCT() {
		return fixedCT;
	}

	public void setFixedCT(String fixedCT) {
		this.fixedCT = fixedCT;
	}

	public double getAreaExterna() {
		return areaExterna;
	}

	public double getAreaLocal() {
		return areaLocal;
	}

	public double getAreaTotal() {
		return areaTotal;
	}

	public void setAreaExterna(double areaExterna) {
		this.areaExterna = areaExterna;
	}

	public void setAreaLocal(double areaLocal) {
		this.areaLocal = areaLocal;
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

	public String getTipoSecaoTubulacao() {
		return tipoSecaoTubulacao;
	}

	public void setTipoSecaoTubulacao(String tipoSecaoTubulacao) {
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
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

	public String getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	
}
