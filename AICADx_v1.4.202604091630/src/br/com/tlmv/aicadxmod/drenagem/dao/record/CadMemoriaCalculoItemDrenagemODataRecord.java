/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMemoriaCalculoItemDrenagemODataRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/06/2025
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;

public class CadMemoriaCalculoItemDrenagemODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_memoria_calculo_item_drenagem_odata";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("row_id", 							AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("pos", 								AppDefs.TAG_SQLTYPE_INT),
		  //
		new SqlColumnVO("numero_ci", 						AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("codigo_local_medicao", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("coef_manning", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pv", 								AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("local_id", 						AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("local", 							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("estaca", 							AppDefs.TAG_SQLTYPE_STR),
		  //
		new SqlColumnVO("cota_terreno", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("fundo", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("nivel_agua", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_externa", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_local", 						AppDefs.TAG_SQLTYPE_DBL),	  
		new SqlColumnVO("area_total", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_total_imp", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("coef_imper", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("coef_distr", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("coef_distr_final", 				AppDefs.TAG_SQLTYPE_DBL),
		  //
	    new SqlColumnVO("tempo_conc",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("declividade", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("dimensoes", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("comprimento", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("observacao", 						AppDefs.TAG_SQLTYPE_BIGSTR),
		new SqlColumnVO("item_anterior_id", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("indice_pluviometrico", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("coef_defluv", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("defl_local", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("defl_escoar", 						AppDefs.TAG_SQLTYPE_DBL),
		  //
	    new SqlColumnVO("f", 								AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("declividade_greide", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("altura_agua", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("yd", 								AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("prof_mont_jus", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("velocidade", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("tempo_percurso", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("tempo_total", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("vazao", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("vazaoAcumulada",					AppDefs.TAG_SQLTYPE_DBL), 
		  //
	    new SqlColumnVO("cotaEntrada", 						AppDefs.TAG_SQLTYPE_DBL), 
		new SqlColumnVO("cotaSaida", 						AppDefs.TAG_SQLTYPE_DBL), 
		new SqlColumnVO("tipo_secao_tubulacao", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("categoria_tubulacao_id", 			AppDefs.TAG_SQLTYPE_INT), 
		new SqlColumnVO("descricao_categoria_tubulacao", 	AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("qtd_tubulacao", 					AppDefs.TAG_SQLTYPE_INT), 
		new SqlColumnVO("diametro_tubulacao", 				AppDefs.TAG_SQLTYPE_DBL), 
		new SqlColumnVO("diametro", 						AppDefs.TAG_SQLTYPE_DBL), 
		new SqlColumnVO("is_root", 							AppDefs.TAG_SQLTYPE_BOOL),
		new SqlColumnVO("is_finish", 						AppDefs.TAG_SQLTYPE_BOOL) 
		
	};
			
//Private
	private int rowId;										// [automatico] 
	private int pos;										// 0, 1, 2, 3, 4... 					(array position)
	//
	private int numeroCI;									// Identificador da Caixa de Inspecao (ou Poco de Visita)
	private int iCodigoLocalMedicao;						// = IDFLOCAL_SANTACRUZ
	private double coefManning;								// = COEFMANNING_SECAO_CIRCULAR
	private String pv;										// PV-A2.1
	private int localId;									// 1001 - RUA DR. MARIO MACHADO
	private String local;									// RUA DR. MARIO MACHADO
	private String estaca;									// 2 + 1.70 m
	//
	private double cotaTerreno;								// 2.841 m
	private double fundo;									// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
	private double nivelAgua;								// NivelAgua = Fundo + AlturaAgua
	private double areaExterna;								// AreaExterna = SOMA(AreaTotal_Anterior)
	private double areaLocal;								// 0.220 ha
	private double areaTotal;								// AreaTotal = AreaTotal[n-1] + Area
	private double areaTotalImp;							// AreaTotalImp = 0.0 ha
	private double coefImper;								// 0.80
	private double coefDistr;								// CoefDistr = AreaTotal ^ ( -0.15 )
	private double coefDistrFinal;							// CoefDistrFinal = SOMA(CoefDistr)
	//
	private double tempoConc;								// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
	private double declividade;								// 0.00160
	private double dimensoesMeter;							// = DiametroTubulacao ( 0.60 m )
	private double comprimento;								// 30 m
	private String observacao; 
	private String isRoot;									// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
	private String isFinish;								// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	private int itemAnteriorId;
	private double indicePluviometrico;						// IndicePluviometrico[CAMPO_GRANDE] = 891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
	private double coefDefluv;
	//
	private double deflLocal;								// DeflLocal = Area * CoefDistr * IndicePluviometrico * CoefDefluv * 2.78
	private double deflEscoar;								// DeflEscoar = DeflLocal
	private double f;										// F = (CoefManning * DeflEscoar / 1000.0) / (SQRT(Declividade) * (Dimensoes ^ (8 / 3))
	private double declividadeGreide;						// DeclividadeGreide = (CotaTerreno[n-1] - CotaTerreno[n]) / Comprimento
	private double alturaAgua;
	private double yd;										// Y/D = (AlturaAgua / Dimensoes) * 100.0
	private double profMontJus;								// ProfMontJus = CotaTerreno - Fundo
	private double velocidade;
	private double tempoPercurso;							// TempoPercurso = Comprimento / (Velocidade / 60.0)
	private double tempoTotal;								// TempoTotal = TempoTotal[n-1] + TempoPercurso	
	//
	private double vazao;
	private double vazaoAcumulada; 
	private double cotaEntrada; 
	private double cotaSaida; 
	private String tipoSecaoTubulacao;
	private int categoriaTubulacaoId; 
    private String descricaoCategoriaTubulacao;
	private int qtdTubulacao; 
	private double diametroTubulacaoMeter; 
	private double diametroMeter; 
	
//Public
	
	public CadMemoriaCalculoItemDrenagemODataRecord()
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
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT,
			//
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
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
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
			AppDefs.NULL_STR,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR );
	}
	
	public CadMemoriaCalculoItemDrenagemODataRecord(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
	}
	
	public CadMemoriaCalculoItemDrenagemODataRecord(String cadRefEntityId, CadMemoriaCalculoItemDrenagemOData o)
	{
		CadMemoriaCalculoItemDrenagemOData oItemAnterior = o.getItemAnterior();

		int itemAnteriorId = -1;
		if(oItemAnterior != null) {
			itemAnteriorId = oItemAnterior.getObjectId();
		}
		
		String strIsRoot = StringUtil.fromBoolToStr( o.isRoot() );
		
		String strIsFinish = StringUtil.fromBoolToStr( o.isFinish() );
				
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );

		this.init(
			AppDefs.NULL_LNG,
			//
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			o.getObjVer(),
			//
			cadRefEntityId,
			//
			strIsDeleted,
			//
			o.getRowId(), 
			o.getPos(),
			//
			o.getNumeroCI(),
			o.getCodigoLocalMedicao(),
			o.getCoefManning(),
			o.getPv(),
			o.getLocalId(),
			o.getLocal(),
			o.getEstaca(),
			//
			o.getCotaTerreno(),
			o.getFundo(),
			o.getNivelAgua(),
			o.getAreaExterna(),
			o.getAreaLocal(),
			o.getAreaTotal(),
			o.getAreaTotalImp(),
			o.getCoefImper(),
			o.getCoefDistr(),
			o.getCoefDistrFinal(),
			//
			o.getTempoConc(),
			o.getDeclividade(),
			o.getDimensoesMeter(),
			o.getComprimento(),
			o.getObservacao(),
			itemAnteriorId,
			o.getIndicePluviometrico(),
			o.getCoefDefluv(),
			o.getDeflLocal(),
			o.getDeflEscoar(),
			//
			o.getF(),
			o.getDeclividadeGreide(),
			o.getAlturaAgua(),
			o.getYd(),
			o.getProfMontJus(),
			o.getVelocidade(),
			o.getTempoPercurso(),
			o.getTempoTotal(),
			o.getVazao(),
			o.getVazaoAcumulada(), 
			//
			o.getCotaEntrada(),
			o.getCotaSaida(),
			o.getTipoSecaoTubulacao(),
			o.getCategoriaTubulacaoId(),
			o.getDescricaoCategoriaTubulacao(),
			o.getQtdTubulacao(),
			o.getDiametroTubulacaoMeter(), 
			o.getDiametroMeter(),
			strIsRoot,			
			strIsFinish );
		
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
		//
		int rowId,										// [automatico] 
		int pos,										// 0, 1, 2, 3, 4... 					(array position)
		//
		int numeroCI,									// Identificador da Caixa de Inspecao (ou Poco de Visita)
		int iCodigoLocalMedicao,						// = IDFLOCAL_SANTACRUZ
		double coefManning,								// = COEFMANNING_SECAO_CIRCULAR
		String pv,										// PV-A2.1
		int localId,									// 1001 - RUA DR. MARIO MACHADO
		String local,									// RUA DR. MARIO MACHADO
		String estaca,									// 2 + 1.70 m
		//
		double cotaTerreno,								// 2.841 m
		double fundo,									// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
		double nivelAgua,								// NivelAgua = Fundo + AlturaAgua
		double areaExterna,								// AreaExterna = SOMA(AreaTotal_Anterior)
		double areaLocal,								// 0.220 ha
		double areaTotal,								// AreaTotal = AreaTotal[n-1] + Area
		double areaTotalImp,							// AreaTotalImp = 0.0 ha
		double coefImper,								// 0.80
		double coefDistr,								// CoefDistr = AreaTotal ^ ( -0.15 )
		double coefDistrFinal,							// CoefDistrFinal = SOMA(CoefDistr)
		//
		double tempoConc,								// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
		double declividade,								// 0.00160
		double dimensoesMeter,							// 0.60
		double comprimento,								// 30 m
		String observacao, 
		int itemAnteriorId,
		double indicePluviometrico,						// IndicePluviometrico[CAMPO_GRANDE] = 891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
		double coefDefluv,
		double deflLocal,								// DeflLocal = Area * CoefDistr * IndicePluviometrico * CoefDefluv * 2.78
		double deflEscoar,								// DeflEscoar = DeflLocal
		//
		double f,										// F = (CoefManning * DeflEscoar / 1000.0) / (SQRT(Declividade) * (Dimensoes ^ (8 / 3))
		double declividadeGreide,						// DeclividadeGreide = (CotaTerreno[n-1] - CotaTerreno[n]) / Comprimento
		double alturaAgua,
		double yd,										// Y/D = (AlturaAgua / Dimensoes) * 100.0
		double profMontJus,								// ProfMontJus = CotaTerreno - Fundo
		double velocidade,
		double tempoPercurso,							// TempoPercurso = Comprimento / (Velocidade / 60.0)
		double tempoTotal,								// TempoTotal = TempoTotal[n-1] + TempoPercurso
		double vazao,
		double vazaoAcumulada, 
		//
		double cotaEntrada,
		double cotaSaida,
		String tipoSecaoTubulacao,
		int categoriaTubulacaoId,
	    String descricaoCategoriaTubulacao,
		int qtdTubulacao,
		double diametroTubulacaoMeter, 
		double diametroMeter,
		String strIsRoot,								// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho			
		String strIsFinish )							// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	{
    	super.initObj(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		AppDefs.DEF_VALUES_NAO,
    		strIsDeleted );

		this.rowId = rowId; 
		this.pos = pos;
		//
	    this.numeroCI = numeroCI;
		this.iCodigoLocalMedicao = iCodigoLocalMedicao;
		this.coefManning = coefManning;
		this.pv = pv;
		this.localId = localId;
		this.local = local;
		this.estaca = estaca;
		//
		this.cotaTerreno = cotaTerreno;
		this.fundo = fundo;
		this.nivelAgua = nivelAgua;
		this.areaExterna = areaExterna;
		this.areaLocal = areaLocal;
		this.areaTotal = areaTotal;
		this.areaTotalImp = areaTotalImp;
		this.coefImper = coefImper;
		this.coefDistr = coefDistr;
		this.coefDistrFinal = coefDistrFinal;
		//
		this.tempoConc = tempoConc;
		this.declividade = declividade;
		this.dimensoesMeter = dimensoesMeter;
		this.comprimento = comprimento;
		this.observacao = observacao;
		this.itemAnteriorId = itemAnteriorId;
		this.indicePluviometrico = indicePluviometrico;
		this.coefDefluv = coefDefluv;
		this.deflLocal = deflLocal;
		this.deflEscoar = deflEscoar;
		//
		this.f = f;
		this.declividadeGreide = declividadeGreide;
		this.alturaAgua = alturaAgua;
		this.yd = yd;
		this.profMontJus = profMontJus;
		this.velocidade = velocidade;
		this.tempoPercurso = tempoPercurso;
		this.tempoTotal = tempoTotal;	
		this.vazao = vazao;
		this.vazaoAcumulada = vazaoAcumulada;
		//
		this.cotaEntrada = cotaEntrada;
		this.cotaSaida = cotaSaida;
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	    this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
		this.qtdTubulacao = qtdTubulacao;
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
		this.diametroMeter = diametroMeter;
		this.isRoot = strIsRoot;
		this.isFinish = strIsFinish;
	}
	
	@Override
	public void init(DbUtil o)
	{
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE3_MASC);

		super.initObj(o);
		
		this.setRowId( o.getNextInt() );														// [automatico] 
		this.setPos( o.getNextInt() );															// 0, 1, 2, 3, 4... (array position)
		//
	    this.setNumeroCI( o.getNextInt() );														// Identificador da Caixa de Inspecao (ou Poco de Visita)
		this.setCodigoLocalMedicao( o.getNextInt() );											// = IDFLOCAL_SANTACRUZ
		this.setCoefManning( o.getNextDbl() );													// = COEFMANNING_SECAO_CIRCULAR
		this.setPv( o.getNextStr() );															// PV-A2.1
		this.setLocalId( o.getNextInt() );														// 1001 - RUA DR. MARIO MACHADO
		this.setLocal( o.getNextStr() );														// RUA DR. MARIO MACHADO
		this.setEstaca( o.getNextStr() );														// 2 + 1.70 m
		//
		this.setCotaTerreno( o.getNextDbl() );													// 2.841 m
		this.setFundo( o.getNextDbl() );														// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
		this.setNivelAgua( o.getNextDbl() );													// NivelAgua = Fundo + AlturaAgua
		this.setAreaExterna( o.getNextDbl() );													// 0.220 ha
		this.setAreaLocal( o.getNextDbl() );													// 0.220 ha
		this.setAreaTotal( o.getNextDbl() );													// AreaTotal = AreaTotal[n-1] + Area
		this.setAreaTotalImp( o.getNextDbl() );													// AreaTotalImp = 0.0 ha
		this.setCoefImper( o.getNextDbl() );													// 0.80
		this.setCoefDistr( o.getNextDbl() );													// CoefDistr = AreaTotal ^ ( -0.15 )
		this.setCoefDistrFinal( o.getNextDbl() );												// CoefDistrFinal = SOMA(CoefDistr)
		//
		this.setTempoConc( o.getNextDbl() );													// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
		this.setDeclividade( o.getNextDbl() );													// 0.00160
		this.setDimensoesMeter( o.getNextDbl() );												// 0.60
		this.setComprimento( o.getNextDbl() );													// 30 m
		this.setObservacao( o.getNextStr() );
		this.setItemAnteriorId( o.getNextInt() ); 
		this.setIndicePluviometrico( o.getNextDbl() );
		this.setCoefDefluv( o.getNextDbl() );
		this.setDeflLocal( o.getNextDbl() );
		this.setDeflEscoar( o.getNextDbl() );
		//
		this.setF( o.getNextDbl() );
		this.setDeclividadeGreide( o.getNextDbl() );
		this.setAlturaAgua( o.getNextDbl() );
		this.setYd( o.getNextDbl() );
		this.setProfMontJus( o.getNextDbl() );
		this.setVelocidade( o.getNextDbl() );
		this.setTempoPercurso( o.getNextDbl() );
		this.setTempoTotal( o.getNextDbl() );
		this.setVazao( o.getNextDbl() );
		this.setVazaoAcumulada( o.getNextDbl() );
		//
		this.setCotaEntrada( o.getNextDbl() );
		this.setCotaSaida( o.getNextDbl() );
		this.setTipoSecaoTubulacao( o.getNextStr() );
		this.setCategoriaTubulacaoId( o.getNextInt() );
	    this.setDescricaoCategoriaTubulacao( o.getNextStr() );
		this.setQtdTubulacao( o.getNextInt() );
		this.setDiametroTubulacaoMeter( o.getNextDbl() ); 
		this.setDiametroMeter( o.getNextDbl() );
		this.setIsRoot( o.getNextStr() );														// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho			
		this.setIsFinish( o.getNextStr() );														// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}
	
	/* Getters/Setters */

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public int getCodigoLocalMedicao() {
		return iCodigoLocalMedicao;
	}

	public void setCodigoLocalMedicao(int iCodigoLocalMedicao) {
		this.iCodigoLocalMedicao = iCodigoLocalMedicao;
	}

	public double getCoefManning() {
		return coefManning;
	}

	public void setCoefManning(double coefManning) {
		this.coefManning = coefManning;
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

	public double getCotaTerreno() {
		return cotaTerreno;
	}

	public void setCotaTerreno(double cotaTerreno) {
		this.cotaTerreno = cotaTerreno;
	}

	public double getFundo() {
		return fundo;
	}

	public void setFundo(double fundo) {
		this.fundo = fundo;
	}

	public double getNivelAgua() {
		return nivelAgua;
	}

	public void setNivelAgua(double nivelAgua) {
		this.nivelAgua = nivelAgua;
	}

	public double getCoefImper() {
		return coefImper;
	}

	public void setCoefImper(double coefImper) {
		this.coefImper = coefImper;
	}

	public double getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(double areaTotal) {
		this.areaTotal = areaTotal;
	}

	public double getCoefDistr() {
		return coefDistr;
	}

	public void setCoefDistr(double coefDistr) {
		this.coefDistr = coefDistr;
	}

	public double getTempoConc() {
		return tempoConc;
	}

	public void setTempoConc(double tempoConc) {
		this.tempoConc = tempoConc;
	}

	public double getDeclividade() {
		return declividade;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}

	public double getDimensoesMeter() {
		return dimensoesMeter;
	}

	public void setDimensoesMeter(double dimensoesMeter) {
		this.dimensoesMeter = dimensoesMeter;
	}

	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getItemAnteriorId() {
		return itemAnteriorId;
	}

	public void setItemAnteriorId(int itemAnteriorId) {
		this.itemAnteriorId = itemAnteriorId;
	}

	public double getIndicePluviometrico() {
		return indicePluviometrico;
	}

	public void setIndicePluviometrico(double indicePluviometrico) {
		this.indicePluviometrico = indicePluviometrico;
	}

	public double getCoefDefluv() {
		return coefDefluv;
	}

	public void setCoefDefluv(double coefDefluv) {
		this.coefDefluv = coefDefluv;
	}

	public double getDeflLocal() {
		return deflLocal;
	}

	public void setDeflLocal(double deflLocal) {
		this.deflLocal = deflLocal;
	}

	public double getDeflEscoar() {
		return deflEscoar;
	}

	public void setDeflEscoar(double deflEscoar) {
		this.deflEscoar = deflEscoar;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public double getDeclividadeGreide() {
		return declividadeGreide;
	}

	public void setDeclividadeGreide(double declividadeGreide) {
		this.declividadeGreide = declividadeGreide;
	}

	public double getAlturaAgua() {
		return alturaAgua;
	}

	public void setAlturaAgua(double alturaAgua) {
		this.alturaAgua = alturaAgua;
	}

	public double getYd() {
		return yd;
	}

	public void setYd(double yd) {
		this.yd = yd;
	}

	public double getProfMontJus() {
		return profMontJus;
	}

	public void setProfMontJus(double profMontJus) {
		this.profMontJus = profMontJus;
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public double getTempoPercurso() {
		return tempoPercurso;
	}

	public void setTempoPercurso(double tempoPercurso) {
		this.tempoPercurso = tempoPercurso;
	}

	public double getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(double tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

	public double getAreaTotalImp() {
		return areaTotalImp;
	}

	public void setAreaTotalImp(double areaTotalImp) {
		this.areaTotalImp = areaTotalImp;
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

	public double getAreaExterna() {
		return areaExterna;
	}

	public void setAreaExterna(double areaExterna) {
		this.areaExterna = areaExterna;
	}

	public double getAreaLocal() {
		return areaLocal;
	}

	public void setAreaLocal(double areaLocal) {
		this.areaLocal = areaLocal;
	}

	public double getCoefDistrFinal() {
		return coefDistrFinal;
	}

	public void setCoefDistrFinal(double coefDistrFinal) {
		this.coefDistrFinal = coefDistrFinal;
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

	public double getDiametroMeter() {
		return diametroMeter;
	}

	public void setDiametroMeter(double diametroMeter) {
		this.diametroMeter = diametroMeter;
	}

}
