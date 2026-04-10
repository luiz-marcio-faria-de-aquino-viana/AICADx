/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMemoriaCalculoItemDrenagemOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/05/2025
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

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;

public class CadMemoriaCalculoItemDrenagemOData extends CadObject
{
//Private
	private int rowId;							// [automatico]
	private int pos;							// 0, 1, 2, 3, 4... 					(array position)
	private int numeroCI;						// Identificador da Caixa de Inspecao (ou Poco de Visita)
	private int iCodigoLocalMedicao;			// = IDFLOCAL_SANTACRUZ
	private double coefManning;					// = COEFMANNING_SECAO_CIRCULAR
	private String pv;							// PV-A2.1
	private int localId;						// 1001 - RUA DR. MARIO MACHADO
	private String local;						// RUA DR. MARIO MACHADO
	private String estaca;						// 2 + 1.70 m
	private double cotaTerreno;					// 2.841 m
	private double fundo;						// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
	private double profundidade;				// Profundidade = (CotaTerreno - CotaFundo) ou 1,0 metro (Minimo)
	private double nivelAgua;					// NivelAgua = Fundo + AlturaAgua
	private double areaExterna;					// Area = 1.0 ha
	private double areaLocal;					// Area = 0.220 ha
	private double areaTotal;					// AreaTotal = AreaTotal[n-1] + Area
	private double areaTotalImp;				// AreaTotalImp = 0.0 ha
	private double coefImper;					// 0.80
	private double coefDistr;					// CoefDistr = AreaTotal ^ ( -0.15 )
	private double coefDistrFinal;				// Se CoefDistr < 1.0 Entao: CoefDistrFinal = 1.0; Senao: CoefDistrFinal = 1.0 / CoefDistr 
	private double tempoConc;					// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
	private double declividade;					// 0.00160
	private double dimensoesMeter;				// = DiametroTubulacao ( 40mm )
	private double comprimentoHoriz;			// 30.0 m
	private double comprimentoVert;				// 0.25 m
	private double comprimento;					// = SQRT(30.0 x 30.0 + 0.25.0 x 0.25) = 30.0001 m
	private String observacao; 
	private boolean bRoot;						// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
	private boolean bFinish;					// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
	private CadMemoriaCalculoItemDrenagemOData oItemAnterior;
	
	/* CALCULATED_FIELDS */
	
	//
	// IndicePluviometrico
	//
	// [CAMPO_GRANDE] 		=  891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
	// [SANTA_CRUZ]   		=  711.30 * (CorfManning_SecaoCircular ^ 0.186) / (TempoConcentracao +  7.00) ^ 0.687
	// [ARARUAMA]     		=  709.00 * (CoefManning_SecaoCircular ^ 0.104) / (TempoConcentracao +  8.00) ^ 0.721
	// [mendanha]      		=  844.78 * (CoefManning_SecaoCircular ^ 0.177) / (TempoConcentracao + 12.00) ^ 0.698
	// [JARDIM_BOTANICO]	= 1239.00 * (CoefManning_SecaoCircular ^ 0.150) / (TempoConcentracao + 20.00) ^ 0.740
	// [JACAREPAGUA]		=   71.11 / (TempoConcentracao / 60.0 + 0.17) ^ 0.7897
	// [VIA11]				= 1423.00 * (CoefManning_SecaoCircular ^ 0.196) / (TempoConcentracao + 14.58) ^ 0.796
	// [IRAJA]				= 5986.27 * (CoefManning_SecaoCircular ^ 0.157) / (TempoConcentracao + 29.70) ^ 1.050
	//
	private double indicePluviometrico;			// IndicePluviometrico[CAMPO_GRANDE] = 891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
	//
	// CoefDefluv
	//
	// SE(CoefImper = 0.4) ENTAO: 0.029 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
	// ELSE: SE(CoefImper = 0.5) ENTAO: 0.036 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
	// ELSE: SE(CoefImper = 0.6) ENTAO: 0.043 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
	// ELSE: SE(CoefImper = 0.7) ENTAO: 0.051 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
	// ELSE: SE(CoefImper = 0.8) ENTAO: 0.058 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
	// ELSE: 0
	//
	private double coefDefluv;
	private double deflLocal;					// DeflLocal = Area * CoefDistr * IndicePluviometrico * CoefDefluv * 2.78
	private double deflEscoar;					// DeflEscoar = DeflLocal
	private double f;							// F = (CoefManning * DeflEscoar / 1000.0) / (SQRT(Declividade) * (Dimensoes ^ (8 / 3))
	private double declividadeGreide;			// DeclividadeGreide = (CotaTerreno[n-1] - CotaTerreno[n]) / Comprimento
	//
	// AlturaAgua
	//
	// SE(Dimensoes > 0) ENTAO: ((AlturaAgua[n-1] / 1000.0) / Velocidade) / Dimensoes
	// ELSE: ( ARR_TBLCOL_FATOR_DRENAGEM[F] ou 2,0 ) * Dimensoes
	//
	private double alturaAgua;
	private double yd;							// Y/D = (AlturaAgua / Dimensoes) * 100.0
	private double profMontJus;					// ProfMontJus = CotaTerreno - Fundo
	//
	// Velocidade
	//
	// SE(Dimensoes > 0) ENTAO: (0.58 / (CoefManning_SecaoRetangular ^ 0.75)) * (DeflEscoar / 1000.0) ^ (1 / 4) * Declividade ^ ( 3 / 8 )
	// ELSE: (0.61 / (CoefManning_SecaoCircular ^ 0.75)) * (DeflEscoar / 1000.0) ^ (1 / 4) * Declividade ^ (3 / 8))
	//
	private double velocidade;
	private double tempoPercurso;				// TempoPercurso = Comprimento / (Velocidade / 60.0)
	private double tempoTotal;					// TempoTotal = TempoTotal[n-1] + TempoPercurso
	//
	// Area Secao Molhada / Vazao / Vazao Acumulada
	//
	private double areaSecaoMolhada;
	private double vazao;
	private double vazaoAcumulada;  
	//
	// Cota Entrada / Cota Saida
	//	
	private double cotaEntrada;
	private double cotaSaida;
	//
	// Tubulacao
	//
	private String tipoSecaoTubulacao;
	private int categoriaTubulacaoId;
    private String descricaoCategoriaTubulacao;
	private int qtdTubulacao;
	private double diametroTubulacaoMeter;
	//
	// CI
	//
	private double diametroMeter;				// diametro da tampa da caixa de inspecao ( 0.60 metros )
	
//Public
	
    public CadMemoriaCalculoItemDrenagemOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA, doc, null);

        this.init(
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
	    	AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_STR,
    		AppDefs.NULL_INT,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_STR,
	    	AppDefs.DEF_VALUES_NAO,
	    	AppDefs.DEF_VALUES_NAO,    		
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_INT,
	    	AppDefs.NULL_STR,
    		null,
    		AppDefs.DEF_VALUES_NAO );
    }

    public CadMemoriaCalculoItemDrenagemOData(CadMemoriaCalculoItemDrenagemOData other)
    {
    	super(AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA, other.getDocument(), null);

    	this.init(other);
    }

    /* Methodes */

	public void init(
	    int rowId, 
		int pos, 							// 0, 1, 2, 3, 4... 				(array position)
		int numeroCI,						// Identificador da Caixa de Inspecao (ou Poco de Visita) 
		int iCodigoLocalMedicao,			// = IDFLOCAL_SANTACRUZ
		double coefManning,					// = COEFMANNING_SECAO_CIRCULAR
		String pv,							// PV-A2.1
		int localId,						// 1001 - RUA DR. MARIO MACHADO
		String local,						// RUA DR. MARIO MACHADO
		String estaca,						// 2 + 1.70 m
		double cotaTerreno,					// 2.841 m
		double areaLocal,					// 0.220 ha
		double coefImper,					// 0.80
		double declividade,					// 0.00160
		double dimensoesMeter,				// = DiametroTubulacao ( 0.40 m )
		double comprimento,					// = SQRT(30.0 x 30.0 + 0.25.0 x 0.25) = 30.0001 m
		String observacao,
		String strIsRoot,					// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
		String strIsFinish,					// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
		//
		String tipoSecaoTubulacao,
		int categoriaTubulacaoId,
	    String descricaoCategoriaTubulacao,
		//
		CadMemoriaCalculoItemDrenagemOData oItemAnterior,
		String strIsDeleted)
	{
    	boolean bRoot = AppDefs.DEF_VALUES_SIM.equals( strIsRoot );
    	
    	boolean bFinish = AppDefs.DEF_VALUES_SIM.equals( strIsFinish );
    	
    	boolean bDeleted = AppDefs.DEF_VALUES_SIM.equals( strIsDeleted );
    	
		this.rowId = this.getRowId();
		this.pos = pos;
		this.numeroCI = numeroCI;
		this.iCodigoLocalMedicao = iCodigoLocalMedicao;
		this.coefManning = coefManning;
		this.pv = pv;
		this.localId = localId;
		this.local = local;
		this.estaca = estaca;
		this.cotaTerreno = cotaTerreno;
		this.areaLocal = areaLocal;
		this.coefImper = coefImper;
		this.declividade = declividade;
		this.dimensoesMeter = dimensoesMeter;
		this.comprimento = comprimento;
		this.observacao = observacao;
		this.bRoot = bRoot;
		this.bFinish = bFinish;
		//
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	    this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	    //
		this.oItemAnterior = oItemAnterior;
		this.setEntityObject(false);
		this.setDeleted(bDeleted);
	}

	public void init(
		int rowId,
		int pos,
		int numeroCI,
		int iCodigoLocalMedicao,
		double coefManning,
		String pv,
		int localId,
		String local,
		String estaca,
		double cotaTerreno,
		double fundo,
		double nivelAgua,
		double areaExterna,
		double areaLocal,
		double areaTotal,
		double areaTotalImp,
		double coefImper,
		double coefDistr,
		double coefDistrFinal,
		double tempoConc,
		double declividade,
		double dimensoesMeter,
		double comprimentoHoriz,
		double comprimentoVert,
		double comprimento,
		String observacao, 
		String strIsRoot,
		String strIsFinish,
		CadMemoriaCalculoItemDrenagemOData oItemAnterior,
		String strIsDeleted,
		double indicePluviometrico,
		double coefDefluv,
		double deflLocal,
		double deflEscoar,
		double f,
		double declividadeGreide,
		double alturaAgua,
		double yd,
		double profMontJus,
		double velocidade,
		double tempoPercurso,
		double tempoTotal,
		double areaSecaoMolhada,
		double vazao,
		double vazaoAcumulada,  
		double cotaEntrada,
		double cotaSaida,
		String tipoSecaoTubulacao,		
	    int categoriaTubulacaoId,
	    String descricaoCategoriaTubulacao,
		int qtdTubulacao,
		double diametroTubulacaoMeter,
		double diametroMeter )
	{
    	boolean bRoot = AppDefs.DEF_VALUES_SIM.equals( strIsRoot );
    	
    	boolean bFinish = AppDefs.DEF_VALUES_SIM.equals( strIsFinish );
    	
    	boolean bDeleted = AppDefs.DEF_VALUES_SIM.equals( strIsDeleted );
    	
		this.rowId = rowId;
		this.pos = pos;
		this.numeroCI = numeroCI;
		this.coefManning = coefManning;
		this.iCodigoLocalMedicao = iCodigoLocalMedicao;
		this.pv = pv;
		this.localId = localId;
		this.local = local;
		this.estaca = estaca;
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
		this.tempoConc = tempoConc;
		this.declividade = declividade;
		this.dimensoesMeter = dimensoesMeter;
		this.comprimentoHoriz = comprimentoHoriz;
		this.comprimentoVert = comprimentoVert;
		this.comprimento = comprimento;
		this.observacao = observacao; 
		this.bRoot = bRoot;
		this.bFinish = bFinish;
		this.oItemAnterior = oItemAnterior;
		this.setEntityObject(false);
		this.setDeleted(bDeleted);
		this.indicePluviometrico = indicePluviometrico;
		this.coefDefluv = coefDefluv;
		this.deflLocal = deflLocal;
		this.deflEscoar = deflEscoar;
		this.f = f;
		this.declividadeGreide = declividadeGreide;
		this.alturaAgua = alturaAgua;
		this.yd = yd;
		this.profMontJus = profMontJus;
		this.velocidade = velocidade;
		this.tempoPercurso = tempoPercurso;
		this.tempoTotal = tempoTotal;
		this.areaSecaoMolhada = areaSecaoMolhada;
		this.vazao = vazao;
		this.vazaoAcumulada = vazaoAcumulada;  
		this.cotaEntrada = cotaEntrada;
		this.cotaSaida = cotaSaida;
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
	    this.categoriaTubulacaoId = categoriaTubulacaoId;
	    this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
		this.qtdTubulacao = qtdTubulacao;
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
		this.diametroMeter = diametroMeter;
	}
	
	@Override
	public void init(ICadObject o) {
		CadMemoriaCalculoItemDrenagemOData other = (CadMemoriaCalculoItemDrenagemOData)o; 

    	String strIsRoot = StringUtil.fromBoolToStr( other.isRoot() ); 
    	
    	String strIsFinish = StringUtil.fromBoolToStr( other.isFinish() ); 
    	
    	String strIsDeleted = StringUtil.fromBoolToStr( other.isDeleted() ); 
    	
        this.init(
    		other.getRowId(),
    		other.getPos(),
    		other.getNumeroCI(),
    		other.getCodigoLocalMedicao(),
    		other.getCoefManning(),
    		other.getPv(),
    		other.getLocalId(),
    		other.getLocal(),
    		other.getEstaca(),
    		other.getCotaTerreno(),
    		other.getAreaLocal(),
    		other.getCoefImper(),
    		other.getDeclividade(),
    		other.getDimensoesMeter(),
    		other.getComprimento(),
    		other.getObservacao(),
    		strIsRoot,
    		strIsFinish,
    		//
    		other.getTipoSecaoTubulacao(),
    		other.getCategoriaTubulacaoId(),
    		other.getDescricaoCategoriaTubulacao(),
    		//
    		other.getItemAnterior(),
    		strIsDeleted);
    }
	
	/* TO_OBJECTARRAY */
	
	public Object[] toObjectArray() {
		ArrayList<Object> lsObj = new ArrayList<Object>();
		
		Class c = this.getClass();
		Field[] lsField = c.getDeclaredFields();
		
		int szLsField = lsField.length;
		for(int j = 0; j < szLsField; j++) {
			Field f = lsField[j];
			
			String strName = f.getName();
			//System.out.println(strName);

			Object obj = null;
			try {
				obj = f.get(this);
			}
			catch(Exception e) { }

			lsObj.add(obj);
		}

		Object[] arr = ListUtil.toArray(lsObj);
		return arr; 
	}
	
	public Object[] toObjectArray(ColunaTabelaVO[] arrColunaDef) {
		ArrayList<Object> lsObj = new ArrayList<Object>();

		for(int i = 0; i < arrColunaDef.length; i++) {
			ColunaTabelaVO oCol = arrColunaDef[i];
			
			Object obj = toValueByName(oCol.getColumnName());
			lsObj.add(obj);
		}

		Object[] arr = ListUtil.toArray(lsObj);
		return arr;
	}
	
	public Object toValueAt(int pos) {
		Class c = this.getClass();
		
		Field[] lsField = c.getDeclaredFields();
		int szLsField = lsField.length;
		
		int n = 0;
		for(int j = 0; j < szLsField; j++) {
			Field f = lsField[j];
			
			String strName = f.getName();
			//System.out.println(strName);

			Object obj = null;
			try {
				if(n == pos) {
					obj = f.get(this);
					return obj;
				}
				pos += 1;
			}
			catch(Exception e) { }
		}
		return null;
	}
	
	public String toStrValueByName(String methodName, int dprec) {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String strVal = "";

		Class c = this.getClass();
				
		Field[] lsField = c.getDeclaredFields();
		int szLsField = lsField.length;
		for(int j = 0; j < szLsField; j++) {
			Field f = lsField[j];
			
			String strName = f.getName();	

			Class cType = f.getType();
			String cName = cType.getName();
			
			if(strName.compareToIgnoreCase(methodName) == 0) {
				Object obj = null;
				try {
					obj = f.get(this);
					strVal = obj.toString();
					
					if( "boolean".equals(cName) ) {				// Boolean
						boolean bVal = f.getBoolean(this);
						strVal = ( bVal ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 
					}
					else if( "byte".equals(cName) ) {			// Byte
						byte iVal = f.getByte(this);						
						strVal = nf0.format(iVal); 
					}
					else if( "char".equals(cName) ) {			// Char
						char chVal = f.getChar(this);						
						strVal = "" + chVal; 						
					}
					else if( "double".equals(cName) ) {			// Double
						double dVal = f.getDouble(this);
						
						if(dprec == AppDefs.DEF_DECPREC_DBL3)
							strVal = nf3.format( dVal ); 												
						else if(dprec == AppDefs.DEF_DECPREC_DBL6)
							strVal = nf6.format( dVal ); 												
					}
					else if( "float".equals(cName) ) {			// Float
						double dVal = f.getDouble(this);						
						
						if(dprec == AppDefs.DEF_DECPREC_DBL3)
							strVal = nf3.format( dVal ); 												
						else if(dprec == AppDefs.DEF_DECPREC_DBL6)
							strVal = nf6.format( dVal ); 												
					}
					else if( "int".equals(cName) ) {			// Int
						int iVal = f.getInt(this);						
						strVal = nf0.format(iVal); 
					}
					else if( "long".equals(cName) ) {			// Long
						long lVal = f.getLong(this);						
						strVal = nf0.format(lVal); 
					}
					else if( "short".equals(cName) ) {			// Short
						short sVal = f.getShort(this);						
						strVal = nf0.format(sVal); 
					}					
					else if( "Date".equals(cName) ) {			// Date
						Date dtVal = (Date)obj;
						strVal = df.format(dtVal); 
					}					
					else if( "String".equals(cName) ) {			// String
						strVal = (String)obj; 
					}					
					return strVal;
				}
				catch(Exception e) { }
			}
		}
		return strVal;
	}
	
	public Object toValueByName(String methodName) {
		Class c = this.getClass();
				
		Field[] lsField = c.getDeclaredFields();
		int szLsField = lsField.length;
		for(int j = 0; j < szLsField; j++) {
			Field f = lsField[j];
			
			String strName = f.getName();	
			if(strName.compareToIgnoreCase(methodName) == 0) {
				Object obj = null;
				try {
					obj = f.get(this);
					return obj;
				}
				catch(Exception e) { }
			}
		}
		return null;
	}
	
	public void setValueByName(String methodName, Object oVal) {
		Class c = this.getClass();
		
		Field[] lsField = c.getDeclaredFields();
		int szLsField = lsField.length;
		for(int j = 0; j < szLsField; j++) {
			Field f = lsField[j];
			
			String strName = f.getName();
			//System.out.println(strName);
			
			if(strName.compareToIgnoreCase(methodName) == 0) {
				Object obj = null;
				try {
					f.set(this, oVal);
				}
				catch(Exception e) { }
			}
		}
	}
	
	/* CREATE */
    
    public static CadMemoriaCalculoItemDrenagemOData create(
		CadDocumentDef doc,
	    int rowId, 
		int pos, 							// 0, 1, 2, 3, 4... 				(array position)
		int numeroCI,						// Identificador da Caixa de Inspecao (ou Poco de Visita) 
		int iCodigoLocalMedicao,			// = IDFLOCAL_SANTACRUZ
		double coefManning,					// = COEFMANNING_SECAO_CIRCULAR
		String pv,							// PV-A2.1
		int localId,						// 1001 - RUA DR. MARIO MACHADO
		String local,						// RUA DR. MARIO MACHADO
		String estaca,						// 2 + 1.70 m
		double cotaTerreno,					// 2.841 m
		double areaLocal,					// 0.220 ha
		double coefImper,					// 0.80
		double declividade,					// 0.00160
		double dimensoesMeter,				// = DiametroTubulacao ( 0.40 m )
		double comprimento,					// = SQRT(30.0 x 30.0 + 0.25.0 x 0.25) = 30.0001 m
		String observacao,
		String strIsRoot,					// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
		String strIsFinish,					// Caixa de Inspecao (ou Poco de Visita) Final do Trecho		
		//
		String tipoSecaoTubulacao,
		int categoriaTubulacaoId,
	    String descricaoCategoriaTubulacao,
	    //
		CadMemoriaCalculoItemDrenagemOData oItemAnterior,
		String strIsDeleted)
    {
    	CadMemoriaCalculoItemDrenagemOData o = new CadMemoriaCalculoItemDrenagemOData(doc);
    	
        o.init(
    	    rowId, 
    		pos, 							// 0, 1, 2, 3, 4... 				(array position)
    		numeroCI,						// Identificador da Caixa de Inspecao (ou Poco de Visita) 
    		iCodigoLocalMedicao,			// = IDFLOCAL_SANTACRUZ
    		coefManning,					// = COEFMANNING_SECAO_CIRCULAR
    		pv,								// PV-A2.1
    		localId,						// 1001 - RUA DR. MARIO MACHADO
    		local,							// RUA DR. MARIO MACHADO
    		estaca,							// 2 + 1.70 m
    		cotaTerreno,					// 2.841 m
    		areaLocal,						// 0.220 ha
    		coefImper,						// 0.80
    		declividade,					// 0.00160
    		dimensoesMeter,					// = DiametroTubulacao ( 0.40 m )
    		comprimento,					// 30 m
    		observacao,
    		strIsRoot,						// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
    		strIsFinish,					// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
    		//
    		tipoSecaoTubulacao,
    		categoriaTubulacaoId,
    	    descricaoCategoriaTubulacao,
    	    //
    		oItemAnterior,
    		strIsDeleted);
        return o;
    }
    
    public static CadMemoriaCalculoItemDrenagemOData create(
    	CadDocumentDef doc,
		int rowid,
		int pos, 							// 0, 1, 2, 3, 4... 				(array position)
		int numeroCI,						// Identificador da Caixa de Inspecao (ou Poco de Visita)
		int iCodigoLocalMedicao,			// = IDFLOCAL_SANTACRUZ
		double coefManning,					// = COEFMANNING_SECAO_CIRCULAR
		String pv,							// PV-A2.1
		int localId,						// 1001 - RUA DR. MARIO MACHADO
		String local,						// RUA DR. MARIO MACHADO
		String estaca,						// 2 + 1.70 m
		double cotaTerreno,					// 2.841 m
		double fundo,
		double nivelAgua,
		double areaExterna,					// 1.0 ha
		double areaLocal,					// 0.220 ha
		double areaTotal,
		double areaTotalImp,
		double coefImper,					// 0.80
		double coefDistr,
		double coefDistrFinal,				// Se CoefDistr < 1.0 Entao: CoefDistrFinal = 1.0; Senao: CoefDistrFinal = 1.0 / CoefDistr
		double tempoConc,
		double declividade,					// 0.00160
		double dimensoesMeter,				// = DiametroTubulacao ( 0.40 m )
		double comprimentoHoriz,			// 30.0 m
		double comprimentoVert,				// 0.25 m
		double comprimento,					// = SQRT(30.0 x 30.0 + 0.25.0 x 0.25) = 30.0001 m
		String observacao, 
		String strIsRoot,					// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
		String strIsFinish,					// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
		CadMemoriaCalculoItemDrenagemOData oItemAnterior,
		String strIsDeleted,
		double indicePluviometrico,
		double coefDefluv,
		double deflLocal,
		double deflEscoar,
		double f,
		double declividadeGreide,
		double alturaAgua,
		double yd,
		double profMontJus,
		double velocidade,
		double tempoPercurso,
		double tempoTotal,
		double areaSecaoMolhada,
		double vazao,
		double vazaoAcumulada,  
		double cotaEntrada,
		double cotaSaida,
		String tipoSecaoTubulacao,		
		int categoriaTubulacaoId,
	    String descricaoCategoriaTubulacao,
		int qtdTubulacao,
		double diametroTubulacaoMeter,
		double diametroMeter)
    {
    	CadMemoriaCalculoItemDrenagemOData o = new CadMemoriaCalculoItemDrenagemOData(doc);    
    	
    	o.init(
			rowid,
			pos,
			numeroCI,
			iCodigoLocalMedicao,
			coefManning,
			pv,
			localId,
			local,
			estaca,
			cotaTerreno,
			fundo,
			nivelAgua,
			areaExterna,
			areaLocal,
			areaTotal,
			areaTotalImp,
			coefImper,
			coefDistr,
			coefDistrFinal,
			tempoConc,
			declividade,
			dimensoesMeter,
			comprimentoHoriz,
			comprimentoVert,
			comprimento,
			observacao, 
			strIsRoot,
			strIsFinish,
			oItemAnterior,
			strIsDeleted,
			indicePluviometrico,
			coefDefluv,
			deflLocal,
			deflEscoar,
			f,
			declividadeGreide,
			alturaAgua,
			yd,
			profMontJus,
			velocidade,
			tempoPercurso,
			tempoTotal,
			areaSecaoMolhada,
			vazao,
			vazaoAcumulada,  
			cotaEntrada,
			cotaSaida,
			tipoSecaoTubulacao,
		    categoriaTubulacaoId,
		    descricaoCategoriaTubulacao,
			qtdTubulacao,
			diametroTubulacaoMeter,
			diametroMeter);
		return o;
    }
	
    public static CadMemoriaCalculoItemDrenagemOData create(CadMemoriaCalculoItemDrenagemOData other)
    {
    	CadMemoriaCalculoItemDrenagemOData o = new CadMemoriaCalculoItemDrenagemOData(other.getDocument());

    	String strIsRoot = ( other.isRoot() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 

    	String strIsFinish = ( other.isFinish() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 

    	String strIsDeleted = ( other.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 
    	
        o.init(
    	    other.getRowId(), 
    	    other.getPos(), 							// 0, 1, 2, 3, 4... 				(array position)
    	    other.getNumeroCI(),						// Identificador da Caixa de Inspecao (ou Poco de Visita) 
    	    other.getCodigoLocalMedicao(),				// = IDFLOCAL_SANTACRUZ
    	    other.getCoefManning(),						// = COEFMANNING_SECAO_CIRCULAR
    	    other.getPv(),								// PV-A2.1
    	    other.getLocalId(),							// 1001 - RUA DR. MARIO MACHADO
    	    other.getLocal(),							// RUA DR. MARIO MACHADO
    	    other.getEstaca(),							// 2 + 1.70 m
    	    other.getCotaTerreno(),						// 2.841 m
    	    other.getAreaLocal(),						// 0.220 ha
    	    other.getCoefImper(),						// 0.80
    	    other.getDeclividade(),						// 0.00160
    	    other.getDimensoesMeter(),					// = DiametroTubulacao ( 0.40 m )
    	    other.getComprimento(),						// = SQRT(30.0 x 30.0 + 0.25.0 x 0.25) = 30.0001 m
    	    other.getObservacao(),
    		strIsRoot,									// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
    		strIsFinish,								// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
    		//
    		other.getTipoSecaoTubulacao(),
    		other.getCategoriaTubulacaoId(),
    		other.getDescricaoCategoriaTubulacao(),
    	    //
    	    other.getItemAnterior(),
    	    strIsDeleted );
        return o;
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
    
	/* RESET */

	@Override
	public void reset() {
		// TODO:
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

    	String strIsRoot = ( this.isRoot() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 

    	String strIsFinish = ( this.isFinish() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 

    	String strIsDeleted = ( this.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 

		String str = String.format(
			"CadRefEntityId:%s; " +
			"Rowid:%s; " +
			"Pos:%s; " +
			"NumeroCI:%s; " +
			"CodigoLocalMedicao:%s; " +
			"CoefManning:%s; " +
			"PV:%s; " +
			"LocalId:%s; " +
			"Local:%s; " +
			"Estaca:%s; " +
			"CotaTerreno:%s; " +
			"Fundo:%s; " +
			"NivelAgua:%s; " +
			"AreaExterna:%s; " +
			"AreaLocal:%s; " +
			"AreaTotal:%s; " +
			"AreaTotalImp:%s; " +
			"CoefImper:%s; " +
			"CoefDistr:%s; " +
			"CoefDistrFinal:%s; " +
			"TempoConc:%s; " +
			"Declividade:%s; " +
			"Dimensoes:%s; " +
			"ComprimentoHoriz:%s; " +
			"ComprimentoVert:%s; " +
			"Comprimento:%s; " +
			"Observacao:%s; " +
			"IsRoot:%s; " +
			"IsFinish:%s; " +
			"IsDeleted:%s; " +
			"indicePluviometrico:%s; " +
			"coefDefluv:%s; " +
			"deflLocal:%s; " +
			"deflEscoar:%s; " +
			"f:%s; " +
			"declividadeGreide:%s; " +
			"alturaAgua:%s; " +
			"yd:%s; " +
			"profMontJus:%s; " +
			"velocidade:%s; " +
			"tempoPercurso:%s; " +
			"tempoTotal:%s; " +
			"areaSecaoMolhada:%s; " +
			"vazao:%s; " +
			"vazaoAcumulada:%s; " +  
			"cotaEntrada:%s; " +
			"cotaSaida:%s; " +
			"tipoSecaoTubulacao:%s; " +
			"categoriaTubulacaoId:%s; " +
			"descricaoCategoriaTubulacao:%s; " +
			"qtdTubulacao:%s; " +
			"diametroTubulacao:%s; " +
			"diametro:%s; ",
			super.getCadRefEntityId(),
			this.rowId,							// [automatico]
			this.pos,							// 0, 1, 2, 3, 4... 					(array position)
			this.numeroCI,						// Identificador da Caixa de Inspecao (ou Poco de Visita)
			this.iCodigoLocalMedicao,			// = IDFLOCAL_SANTACRUZ
			this.coefManning,					// = COEFMANNING_SECAO_CIRCULAR
			this.pv,							// PV-A2.1
			this.localId,						// 1001 - RUA DR. MARIO MACHADO
			this.local,							// RUA DR. MARIO MACHADO
			this.estaca,						// 2 + 1.70 m
			this.cotaTerreno,					// 2.841 m
			this.fundo,							// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
			this.nivelAgua,						// NivelAgua = Fundo + AlturaAgua
			this.areaExterna,					// 1.0 ha
			this.areaLocal,						// 0.220 ha
			this.areaTotal,						// AreaTotal = AreaTotal[n-1] + Area
			this.areaTotalImp,					// AreaTotalImp = 0.0 ha
			this.coefImper,						// 0.80
			this.coefDistr,						// CoefDistr = AreaTotal ^ ( -0.15 )
			this.coefDistrFinal,				// Se CoefDistr < 1.0 Entao: CoefDistrFinal = 1.0; Senao: CoefDistrFinal = 1.0 / CoefDistr
			this.tempoConc,						// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
			this.declividade,					// 0.00160
			this.dimensoesMeter,				// = DiametroTubulacao ( 0.40 m )
			this.comprimentoHoriz,				// 30.0 m
			this.comprimentoVert,				// 0.25 m
			this.comprimento,					// = SQRT(30.0 x 30.0 + 0.25.0 x 0.25) = 30.0001 m
			this.observacao, 
			strIsRoot, 
			strIsFinish,
			strIsDeleted,
			this.indicePluviometrico,
			this.coefDefluv,
			this.deflLocal,
			this.deflEscoar,
			this.f,
			this.declividadeGreide,
			this.alturaAgua,
			this.yd,
			this.profMontJus,
			this.velocidade,
			this.tempoPercurso,
			this.tempoTotal,
			this.areaSecaoMolhada,
			this.vazao,
			this.vazaoAcumulada,  
			this.cotaEntrada,
			this.cotaSaida,
			this.tipoSecaoTubulacao,
		    this.categoriaTubulacaoId,
		    this.descricaoCategoriaTubulacao,
			this.qtdTubulacao,
			this.diametroTubulacaoMeter,
			this.diametroMeter);
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */
	
	public String getPv() {
		return pv;
	}
	public void setPv(String pv) {
		this.pv = pv;
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
	public double getCoef() {
		return coefImper;
	}
	public void setCoef(double coefImper) {
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
	public double getDeclividadeGreide() {
		return declividadeGreide;
	}
	public void setDeclividadeGreide(double declividadeGreide) {
		this.declividadeGreide = declividadeGreide;
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
	public double getComprimento() {
		return comprimento;
	}
	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
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
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getCodigoLocalMedicao() {
		return iCodigoLocalMedicao;
	}

	public double getCoefManning() {
		return coefManning;
	}

	public int getLocalId() {
		return localId;
	}

	public double getCoefImper() {
		return coefImper;
	}

	public int getPos() {
		return pos;
	}

	public CadMemoriaCalculoItemDrenagemOData getItemAnterior() {
		return oItemAnterior;
	}

	public double getF() {
		return f;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public CadMemoriaCalculoItemDrenagemOData getoItemAnterior() {
		return oItemAnterior;
	}

	public void setItemAnterior(CadMemoriaCalculoItemDrenagemOData oItemAnterior) {
		this.oItemAnterior = oItemAnterior;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void setCodigoLocalMedicao(int iCodigoLocalMedicao) {
		this.iCodigoLocalMedicao = iCodigoLocalMedicao;
	}

	public void setCoefManning(double coefManning) {
		this.coefManning = coefManning;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	public void setCoefImper(double coefImper) {
		this.coefImper = coefImper;
	}

	public void setF(double f) {
		this.f = f;
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

	public double getAreaTotalImp() {
		return areaTotalImp;
	}

	public void setAreaTotalImp(double areaTotalImp) {
		this.areaTotalImp = areaTotalImp;
	}

	public double getCoefDistrFinal() {
		return coefDistrFinal;
	}

	public void setCoefDistrFinal(double coefDistrFinal) {
		this.coefDistrFinal = coefDistrFinal;
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

	public String getDescricaoCategoriaTubulacao() {
		return descricaoCategoriaTubulacao;
	}

	public void setDescricaoCategoriaTubulacao(String descricaoCategoriaTubulacao) {
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	}

	public int getCategoriaTubulacaoId() {
		return categoriaTubulacaoId;
	}

	public void setCategoriaTubulacaoId(int categoriaTubulacaoId) {
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	}

	public String getTipoSecaoTubulacao() {
		return tipoSecaoTubulacao;
	}

	public void setTipoSecaoTubulacao(String tipoSecaoTubulacao) {
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getDiametroMeter() {
		return diametroMeter;
	}

	public void setDiametro(double diametroMeter) {
		this.diametroMeter = diametroMeter;
	}

	public double getAreaSecaoMolhada() {
		return areaSecaoMolhada;
	}

	public void setAreaSecaoMolhada(double areaSecaoMolhada) {
		this.areaSecaoMolhada = areaSecaoMolhada;
	}

	public double getComprimentoHoriz() {
		return comprimentoHoriz;
	}

	public void setComprimentoHoriz(double comprimentoHoriz) {
		this.comprimentoHoriz = comprimentoHoriz;
	}

	public double getComprimentoVert() {
		return comprimentoVert;
	}

	public void setComprimentoVert(double comprimentoVert) {
		this.comprimentoVert = comprimentoVert;
	}
	
}
