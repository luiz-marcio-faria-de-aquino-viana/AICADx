/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadCircuitoQuadroCargasEletricaOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/01/2025
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

package br.com.tlmv.aicadxmod.eletrica.cad;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.eletrica.calc.EletricaCalc;
import br.com.tlmv.aicadxmod.eletrica.vo.DimensionamentoCircuito;
import br.com.tlmv.aicadxmod.eletrica.vo.DistribuicaoFaseVO;

public class CadCircuitoQuadroCargasEletricaOData extends CadObject
{
//Private
	private CadDocumentDef doc = null;
	private CadBlockDef blkDef = null;
	//
	private int rowId = AppDefs.NULL_INT;
	private String nomeQuadro = AppDefs.NULL_STR;
	private String numeroCircuito = AppDefs.NULL_STR;
	private String descricaoCircuito = AppDefs.NULL_STR;
	private String sistemaFase = AppDefs.NULL_STR;
	private int qtdCargaCircuito = 0;
	private double cargaCircuito = 0.0;
	private int qtdCargaPainel = 0;
	private double cargaPainel = 0.0;
	private int qtdCargaIluminacao = 0;
	private double cargaIluminacao = 0.0;
	private int qtdCargaTomada = 0;
	private double cargaTomada = 0.0;
	private int qtdCargaMotor = 0;
	private double cargaMotor = 0.0;
	private int qtdCargaRaioX = 0;
	private double cargaRaioX = 0.0;
	private int qtdCargaAquecimento = 0;
	private double cargaAquecimento = 0.0;
	private int qtdCargaOutra = 0;
	private double cargaOutra = 0.0;
	private double alimentadorCircuito = 0.0;
	private double alimentadorProtecaoCircuito = 0.0;
	private double disjuntorCircuito = 0.0;
	private String faseCircuito = AppDefs.NULL_STR;
	
	private ArrayList<CadPontoEletrica> lsPontoEletrico = null;

	//DIMENSIONAMENTO CIRCUITO/FASE
    private DistribuicaoFaseVO oDistribuicaoFase = null;
    private DimensionamentoCircuito oDimensionamentoCircuito = null;	
	
	/* Methodes */
	
	private void resetAllPontoEletrica()
	{
		this.sistemaFase = AppDefs.NULL_STR;
		//
		this.qtdCargaCircuito = 0;
		this.cargaCircuito = 0.0;
		this.alimentadorCircuito = 0.0;
		this.alimentadorProtecaoCircuito = 0.0;
		this.disjuntorCircuito = 0.0;
		this.faseCircuito = AppDefs.NULL_STR;
		this.qtdCargaPainel = 0;
		this.cargaPainel = 0.0;
		this.qtdCargaIluminacao = 0;
		this.cargaIluminacao = 0.0;
		this.qtdCargaTomada = 0;
		this.cargaTomada = 0.0;
		this.qtdCargaMotor = 0;
		this.cargaMotor = 0.0;
		this.qtdCargaRaioX = 0;
		this.cargaRaioX = 0.0;
		this.qtdCargaAquecimento = 0;
		this.cargaAquecimento = 0.0;
		this.qtdCargaOutra = 0;
		this.cargaOutra = 0.0;		
	}
	
	public ArrayList<CadPontoEletrica> processaAllPontoEletrica()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		for(CadPontoEletrica oEnt : this.lsPontoEletrico)
		{
			int sz = oEnt.getSzLsParamEletrico();
			if(sz > 0) {
				for(int i = 0; i < sz; i++) {
					CadParamEletricoOData oParam = oEnt.getParamEletricoAt(i);
	
					int objectId = oEnt.getObjectId();
					
				    String tip = oParam.getTipo();
				    String org = StringUtil.toUpperCase( oParam.getQuadroOrigem() );
				    double pot = oParam.getPotencia();
				    double dem = oParam.getPotenciaDemandada();
				    String fas = oParam.getSistema();
				    String cir = StringUtil.toUpperCase( oParam.getCircuito() );
				    String cmd = oParam.getComando();
	
					if( (org.compareToIgnoreCase( this.nomeQuadro ) == 0) && (cir.compareToIgnoreCase( this.numeroCircuito ) == 0) ) 
					{
						// Elemento_Pertence (Quadro-Circuito)
						//
						if( StringUtil.isEmpty(this.sistemaFase) ) {
							if( !StringUtil.isEmpty(fas) ) {
								this.sistemaFase = fas;
							}
						}
						
						if( AppDefs.FIA_S_QUADRO.equals(tip) ) {
							// Tipo_Quadro
							this.qtdCargaCircuito += 1;
							this.qtdCargaPainel += 1;

							if(dem > AppDefs.MATHPREC_MIN) {
								this.cargaCircuito += dem;								
								this.cargaPainel += dem;								
							}
							else {
								this.cargaCircuito += pot;								
								this.cargaPainel += pot;								
							}
						}
						else if( AppDefs.FIA_S_CARGA.equals(tip) ) {
							// Tipo_Carga
							this.qtdCargaCircuito += 1;
							this.cargaCircuito += pot;								

							this.qtdCargaTomada += 1;
							this.cargaTomada += pot;													
						}
						else if( AppDefs.FIA_S_ILUMINACAO.equals(tip) ) {
							// Tipo_Iluminacao
							this.qtdCargaCircuito += 1;
							this.cargaCircuito += pot;								

							this.qtdCargaIluminacao += 1;
							this.cargaIluminacao += pot;						
						}
						else if( AppDefs.FIA_S_MOTOR.equals(tip) ) {
							this.qtdCargaCircuito += 1;
							this.cargaCircuito += pot;								

							this.qtdCargaMotor += 1;
							this.cargaMotor += pot;
						}
						else if( AppDefs.FIA_S_RAIOX.equals(tip) ) {
							this.qtdCargaCircuito += 1;
							this.cargaCircuito += pot;								

							this.qtdCargaRaioX += 1;
							this.cargaRaioX += pot;
						}
						else if( AppDefs.FIA_S_AQUECIMENTO.equals(tip) ) {
							this.qtdCargaCircuito += 1;
							this.cargaCircuito += pot;								

							this.qtdCargaAquecimento += 1;
							this.cargaAquecimento += pot;
						}
						else if( ( AppDefs.FIA_S_COMANDO.equals(tip) ) 		||
								 ( AppDefs.FIA_S_CAMPAINHA.equals(tip) ) 	||
								 ( AppDefs.FIA_S_CAIXA.equals(tip) ) 		||
								 ( AppDefs.FIA_S_DESVIO.equals(tip) ) 		||
								 ( AppDefs.FIA_S_CALHA.equals(tip) ) )
						{
							/* nothing todo! */
						}
						else {
							String str = String.format("ERR: Tipo de ponto eletrico invalido (Id: %s; Tipo: %s)", 
								nf0.format(objectId),
								tip );
							PromptUtil.prompt(str);
						}
					}
				}
			}
		}
		return this.lsPontoEletrico;
	}
	
	public void dimensionaCircuito(double vfase, double btmin, double temp, double fred) 
	{
	    EletricaCalc calc = new EletricaCalc(this.doc);

        double ftmp = calc.getFatorCorrecaoPorTemperatura(temp);
	    
        String cir1 = this.numeroCircuito;
        String fas1 = this.sistemaFase;
        double pot1 = this.cargaCircuito;
        double vf1 = 0.0;
        double cor1 = 0.0;
        int nf1 = -1;

        if( ( AppDefs.FIA_S_FN.equals(fas1) ) || 
        	( AppDefs.FIA_S_FNT.equals(fas1) ) ) {
            vf1 = vfase / Math.sqrt(3.0);
            cor1 = (pot1 / vf1) / (ftmp * fred);
            nf1 = 1;
        }
        else if( ( AppDefs.FIA_S_2F.equals(fas1)   ) || 
        		 ( AppDefs.FIA_S_2FT.equals(fas1)  ) || 
        		 ( AppDefs.FIA_S_2FN.equals(fas1)  ) || 
        		 ( AppDefs.FIA_S_2FNT.equals(fas1) ) ) {
            vf1 = vfase;
            cor1 = (pot1 / vf1) / (ftmp * fred);
            nf1 = 2;
        }
        else if( ( AppDefs.FIA_S_3F.equals(fas1)   ) || 
        		 ( AppDefs.FIA_S_3FT.equals(fas1)  ) || 
        		 ( AppDefs.FIA_S_3FN.equals(fas1)  ) || 
        		 ( AppDefs.FIA_S_3FNT.equals(fas1) ) ) {
            vf1 = vfase;
            cor1 = (pot1 / (vf1 * Math.sqrt(3.0))) / (ftmp * fred);
            nf1 = 3;
        }

        double prt1 = calc.getProtecao(cor1);

        double bit1 = calc.getBitolaCondutor(nf1, cor1, prt1);
        if (bit1 < btmin)
            bit1 = btmin;
        
    	this.alimentadorCircuito = bit1;
    	this.disjuntorCircuito = prt1;

    	//DIMENSIONAMENTO CIRCUITO/FASE
        this.oDistribuicaoFase = new DistribuicaoFaseVO(nf1, pot1, cir1);
        this.oDimensionamentoCircuito = new DimensionamentoCircuito(cir1, pot1, vf1, cor1, bit1, prt1, "?", fas1)	;        
	}
    		
//Public
    
    public CadCircuitoQuadroCargasEletricaOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_CIRCQDRCARGASELETRICO_ODATA, doc, null);
    	
        this.init(
        	AppDefs.NULL_INTSTR,
	    	AppDefs.NULL_INT,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.DEF_VALUES_NAO);
    }

    public CadCircuitoQuadroCargasEletricaOData(CadCircuitoQuadroCargasEletricaOData other)
    {
    	super(AppDefs.OBJTYPE_CIRCQDRCARGASELETRICO_ODATA, other.getDocument(), null);
    	
    	this.init(other);
    }
    
    /* Methodes */
    
    public void init(
		String cadRefEntityId,
		int rowId,		
		String nomeQuadro,
		String numeroCircuito,
		String descricaoCircuito,
		String sistemaFase,
    	String strIsDeleted) 
    {    	
    	boolean bDeleted = AppDefs.DEF_VALUES_SIM.equals( strIsDeleted );
    	super.setDeleted( bDeleted );

    	super.setCadRefEntityId( cadRefEntityId ); 
    	
    	this.rowId = rowId;
    	this.nomeQuadro = nomeQuadro;
		this.numeroCircuito = numeroCircuito;
		this.descricaoCircuito = descricaoCircuito;
		this.sistemaFase = sistemaFase;
    	
		this.lsPontoEletrico = new ArrayList<CadPontoEletrica>();
    }
		
	@Override
	public void init(ICadObject o) {
		CadCircuitoQuadroCargasEletricaOData other = (CadCircuitoQuadroCargasEletricaOData)o;
			
		String strIsDeleted = StringUtil.fromBoolToStr( other.isDeleted() ); 
		
	    this.init(
	    	other.getCadRefEntityId(),
	    	other.getRowId(),
	    	other.getNomeQuadro(),
	    	other.getNumeroCircuito(),
	    	other.getDescricaoCircuito(),
	    	other.getSistemaFase(),
	    	strIsDeleted );
	}
    
    /* CREATExxx */
    
    public static CadCircuitoQuadroCargasEletricaOData create(
		CadDocumentDef doc,
		String cadRefEntityId,
		int rowId,
		String nomeQuadro,
		String numeroCircuito,
		String descricaoCircuito,
		String sistemaFase,
    	String strIsDeleted) 
    {
    	CadCircuitoQuadroCargasEletricaOData o = new CadCircuitoQuadroCargasEletricaOData(doc);
    	o.init(
			cadRefEntityId,
    		rowId,
    		nomeQuadro,
    		numeroCircuito,
    		descricaoCircuito,
    		sistemaFase,
    		strIsDeleted );
    	return o;
    }
    
    /* LIST - PONTO_ELETRICO */
    
    public synchronized int getSzLsPontoEletrico()
    {
    	int sz = this.lsPontoEletrico.size();
    	return sz;
    }
    
    public synchronized void addPontoEletrico(CadPontoEletrica o)
    {
    	this.lsPontoEletrico.add(o);
    }
    
    public synchronized CadPontoEletrica getPontoEletricoAt(int pos)
    {
    	CadPontoEletrica oResult = null;

    	int sz = this.lsPontoEletrico.size();
    	if( (pos > 0) && (pos < sz) ) {
    		oResult = this.lsPontoEletrico.get(pos);
    	}
    	return oResult;
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
		String strVal = "";

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

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
					//strVal = obj.toString();
					
					if( "boolean".equals(cName) ) {						// Boolean
						boolean bVal = f.getBoolean(this);
						strVal = ( bVal ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 
					}
					else if( "byte".equals(cName) ) {					// Byte
						byte iVal = f.getByte(this);	
						if(iVal > 0)
							strVal = StringUtil.toStrValue(iVal, 0); 
					}
					else if( "char".equals(cName) ) {					// Char
						char chVal = f.getChar(this);						
						strVal = "" + chVal; 						
					}
					else if( "double".equals(cName) ) {					// Double
						double dVal = f.getDouble(this);
						if(dVal > AppDefs.MATHPREC_MIN)
							strVal = StringUtil.toStrValue(dVal, dprec); 
					}
					else if( "float".equals(cName) ) {					// Float
						double dVal = f.getDouble(this);						
						if(dVal > AppDefs.MATHPREC_MIN)
							strVal = StringUtil.toStrValue(dVal, dprec); 
					}
					else if( "int".equals(cName) ) {					// Int
						int iVal = f.getInt(this);						
						if(iVal > 0)
							strVal = StringUtil.toStrValue(iVal, 0); 
					}
					else if( "long".equals(cName) ) {					// Long
						long lVal = f.getLong(this);						
						if(lVal > 0)
							strVal = StringUtil.toStrValue(lVal, 0); 
					}
					else if( "short".equals(cName) ) {					// Short
						short sVal = f.getShort(this);						
						if(sVal > 0)
							strVal = StringUtil.toStrValue(sVal, dprec);
					}					
					else if( "java.util.Date".equals(cName) ) {			// Date
						Date dtVal = (Date)obj;
						strVal = df.format(dtVal); 
					}					
					else if( "java.lang.String".equals(cName) ) {		// String
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
			if(strName.compareToIgnoreCase(methodName) == 0) {
				Object obj = null;
				try {
					f.set(this, oVal);
				}
				catch(Exception e) { }
			}
		}
	}

	/* RESET */

	@Override
	public void reset() {
		// TODO:
	}
    
	/* DEBUG */

	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();	

		lsProperty.add( new ItemDataVO("RowId", nf0.format(this.rowId)) );
		lsProperty.add( new ItemDataVO("Quadro", this.nomeQuadro) );
		lsProperty.add( new ItemDataVO("Circuito", this.numeroCircuito) );
		lsProperty.add( new ItemDataVO("Descr.Circ.", this.descricaoCircuito) );
		lsProperty.add( new ItemDataVO("Sist.Fase.", this.sistemaFase) );
		lsProperty.add( new ItemDataVO("Alimentador (mm2)", nf1.format( this.alimentadorCircuito ) ) );
		lsProperty.add( new ItemDataVO("Alim.Protecao (mm2)", nf1.format( this.alimentadorProtecaoCircuito ) ) );
		lsProperty.add( new ItemDataVO("Disjuntor (A)", nf0.format( this.disjuntorCircuito ) ) );
		lsProperty.add( new ItemDataVO("faseCircuito (A)", this.faseCircuito ) );
		lsProperty.add( new ItemDataVO("Qtd.Carga Total", nf0.format( this.qtdCargaCircuito ) ) );
		lsProperty.add( new ItemDataVO("Carga Total (VA)", nf3.format( this.cargaCircuito ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Paineis", nf0.format( this.qtdCargaPainel ) ) );
		lsProperty.add( new ItemDataVO("Carga Paineis (VA)", nf3.format( this.cargaPainel ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Iluminacao", nf0.format( this.qtdCargaIluminacao ) ) );
		lsProperty.add( new ItemDataVO("Carga Iluminacao (VA)", nf3.format( this.cargaIluminacao ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Tomada", nf0.format( this.qtdCargaTomada ) ) );
		lsProperty.add( new ItemDataVO("Carga Tomada (VA)", nf3.format( this.cargaTomada ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Motor", nf0.format( this.qtdCargaMotor ) ) );
		lsProperty.add( new ItemDataVO("Carga Motor (VA)", nf3.format( this.cargaMotor ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Raio-X", nf0.format( this.qtdCargaRaioX ) ) );
		lsProperty.add( new ItemDataVO("Carga Raio-X (VA)", nf3.format( this.cargaRaioX ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Aquec.", nf0.format( this.qtdCargaAquecimento ) ) );
		lsProperty.add( new ItemDataVO("Carga Aquec. (VA)", nf3.format( this.cargaAquecimento ) ) );
		lsProperty.add( new ItemDataVO("Qtd.Outra", nf0.format( this.qtdCargaOutra ) ) );
		lsProperty.add( new ItemDataVO("Carga Outra (VA)", nf3.format( this.cargaOutra ) ) );

		return lsProperty;
	}

	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);

		NumberFormat nf1 = FormatUtil.newNumberFormatEnUs(1);

		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"RowId:%s;" +
			"Quadro:%s;" +
			"Circuito:%s;" +
			"Descr.Circ.:%s;" +
			"Sist.Fase.:%s;" +
			"Alimentador (mm2):%s;" +
			"Alim.Protecao (mm2):%s;" +
			"Disjuntor (A):%s;" +
			"FaseCircuito (A):%s;" +
			"Qtd.Carga Total:%s;" +
			"Carga Total (VA):%s;" +
			"Qtd.Paineis:%s;" +
			"Carga Paineis (VA):%s;" +
			"Qtd.Iluminacao:%s;" +
			"Carga Iluminacao (VA):%s;" +
			"Qtd.Tomada:%s;" +
			"Carga Tomada (VA):%s;" +
			"Qtd.Motor:%s;" +
			"Carga Motor (VA):%s;" +
			"Qtd.Raio-X:%s;" +
			"Carga Raio-X (VA):%s;" +
			"Qtd.Aquec.:%s;" +
			"Carga Aquec. (VA):%s;" +
			"Qtd.Outra:%s;" +
			"Carga Outra (VA):%s;",
			this.rowId,
			this.nomeQuadro,
			this.numeroCircuito,
			this.descricaoCircuito,
			this.sistemaFase,
			nf1.format( this.alimentadorCircuito ),
			nf1.format( this.alimentadorProtecaoCircuito ),
			nf0.format( this.disjuntorCircuito ),
			this.faseCircuito,
			nf0.format( this.qtdCargaCircuito ),
			nf3.format( this.cargaCircuito ),
			nf0.format( this.qtdCargaPainel ),
			nf3.format( this.cargaPainel ),
			nf0.format( this.qtdCargaIluminacao ),
			nf3.format( this.cargaIluminacao ),
			nf0.format( this.qtdCargaTomada ),
			nf3.format( this.cargaTomada ),
			nf0.format( this.qtdCargaMotor ),
			nf3.format( this.cargaMotor ),
			nf0.format( this.qtdCargaRaioX ),
			nf3.format( this.cargaRaioX ),
			nf0.format( this.qtdCargaAquecimento ),
			nf3.format( this.cargaAquecimento ),
			nf0.format( this.qtdCargaOutra ),
			nf3.format( this.cargaOutra ) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
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

	/* RECALC - QUADRO_CARGAS */
	
	public void reCalcCircuito(double vfase, double btmin, double temp, double fred)
	{
		this.resetAllPontoEletrica();
		this.processaAllPontoEletrica();
		this.dimensionaCircuito(vfase, btmin, temp, fred);
	}
    
    /* Getters/Setters */

	public int getRowId() {
		return this.rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
    
	public String getNomeQuadro() {
		return nomeQuadro;
	}
	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getNumeroCircuito() {
		return numeroCircuito;
	}

	public void setNumeroCircuito(String numeroCircuito) {
		this.numeroCircuito = numeroCircuito;
	}

	public String getDescricaoCircuito() {
		return descricaoCircuito;
	}

	public void setDescricaoCircuito(String descricaoCircuito) {
		this.descricaoCircuito = descricaoCircuito;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public int getQtdCargaCircuito() {
		return qtdCargaCircuito;
	}

	public void setQtdCargaCircuito(int qtdCargaCircuito) {
		this.qtdCargaCircuito = qtdCargaCircuito;
	}

	public Double getCargaCircuito() {
		return cargaCircuito;
	}

	public void setCargaCircuito(Double cargaCircuito) {
		this.cargaCircuito = cargaCircuito;
	}

	public Double getAlimentadorCircuito() {
		return alimentadorCircuito;
	}

	public void setAlimentadorCircuito(Double alimentadorCircuito) {
		this.alimentadorCircuito = alimentadorCircuito;
	}

	public Double getAlimentadorProtecaoCircuito() {
		return alimentadorProtecaoCircuito;
	}

	public void setAlimentadorProtecaoCircuito(Double alimentadorProtecaoCircuito) {
		this.alimentadorProtecaoCircuito = alimentadorProtecaoCircuito;
	}

	public Double getDisjuntorCircuito() {
		return disjuntorCircuito;
	}

	public void setDisjuntorCircuito(Double disjuntorCircuito) {
		this.disjuntorCircuito = disjuntorCircuito;
	}

	public int getQtdCargaIluminacao() {
		return qtdCargaIluminacao;
	}

	public void setQtdCargaIluminacao(int qtdCargaIluminacao) {
		this.qtdCargaIluminacao = qtdCargaIluminacao;
	}

	public double getCargaIluminacao() {
		return cargaIluminacao;
	}

	public void setCargaIluminacao(double cargaIluminacao) {
		this.cargaIluminacao = cargaIluminacao;
	}

	public int getQtdCargaTomada() {
		return qtdCargaTomada;
	}

	public void setQtdCargaTomada(int qtdCargaTomada) {
		this.qtdCargaTomada = qtdCargaTomada;
	}

	public double getCargaTomada() {
		return cargaTomada;
	}

	public void setCargaTomada(double cargaTomada) {
		this.cargaTomada = cargaTomada;
	}

	public int getQtdCargaMotor() {
		return qtdCargaMotor;
	}

	public void setQtdCargaMotor(int qtdCargaMotor) {
		this.qtdCargaMotor = qtdCargaMotor;
	}

	public double getCargaMotor() {
		return cargaMotor;
	}

	public void setCargaMotor(double cargaMotor) {
		this.cargaMotor = cargaMotor;
	}

	public int getQtdCargaRaioX() {
		return qtdCargaRaioX;
	}

	public void setQtdCargaRaioX(int qtdCargaRaioX) {
		this.qtdCargaRaioX = qtdCargaRaioX;
	}

	public double getCargaRaioX() {
		return cargaRaioX;
	}

	public void setCargaRaioX(double cargaRaioX) {
		this.cargaRaioX = cargaRaioX;
	}

	public int getQtdCargaAquecimento() {
		return qtdCargaAquecimento;
	}

	public void setQtdCargaAquecimento(int qtdCargaAquecimento) {
		this.qtdCargaAquecimento = qtdCargaAquecimento;
	}

	public double getCargaAquecimento() {
		return cargaAquecimento;
	}

	public void setCargaAquecimento(double cargaAquecimento) {
		this.cargaAquecimento = cargaAquecimento;
	}

	public int getQtdCargaOutra() {
		return qtdCargaOutra;
	}

	public void setQtdCargaOutra(int qtdCargaOutra) {
		this.qtdCargaOutra = qtdCargaOutra;
	}

	public double getCargaOutra() {
		return cargaOutra;
	}

	public void setCargaOutra(double cargaOutra) {
		this.cargaOutra = cargaOutra;
	}

	public String getFaseCircuito() {
		return faseCircuito;
	}

	public void setFaseCircuito(String faseCircuito) {
		this.faseCircuito = faseCircuito;
	}

	public DistribuicaoFaseVO getDistribuicaoFase() {
		return oDistribuicaoFase;
	}

	public void setDistribuicaoData(DistribuicaoFaseVO oDistribuicaoFase) {
		this.oDistribuicaoFase = oDistribuicaoFase;
	}

	public DimensionamentoCircuito getDimensionamentoCircuito() {
		return oDimensionamentoCircuito;
	}

	public void setDimensionamentoCircuito(DimensionamentoCircuito oDimensionamentoCircuito) {
		this.oDimensionamentoCircuito = oDimensionamentoCircuito;
	}

	public int getQtdCargaPainel() {
		return qtdCargaPainel;
	}

	public void setQtdCargaPainel(int qtdCargaPainel) {
		this.qtdCargaPainel = qtdCargaPainel;
	}

	public double getCargaPainel() {
		return cargaPainel;
	}

	public void setCargaPainel(double cargaPainel) {
		this.cargaPainel = cargaPainel;
	}

	public void setCargaCircuito(double cargaCircuito) {
		this.cargaCircuito = cargaCircuito;
	}

	public void setAlimentadorCircuito(double alimentadorCircuito) {
		this.alimentadorCircuito = alimentadorCircuito;
	}

	public void setAlimentadorProtecaoCircuito(double alimentadorProtecaoCircuito) {
		this.alimentadorProtecaoCircuito = alimentadorProtecaoCircuito;
	}

	public void setDisjuntorCircuito(double disjuntorCircuito) {
		this.disjuntorCircuito = disjuntorCircuito;
	}

	public int getQtdCondutorFaseCircuito() {
        int fios = 0;
        
        if( AppDefs.FIA_S_FN.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_2F.equals( this.sistemaFase ) ) {
            fios += 2;        	
        }
        else if( AppDefs.FIA_S_2FN.equals( this.sistemaFase ) ) {
            fios += 2;        	
        }
        else if( AppDefs.FIA_S_2FT.equals( this.sistemaFase ) ) {
            fios += 2;        	
        }
        else if( AppDefs.FIA_S_2FNT.equals( this.sistemaFase ) ) {
            fios += 2;        	
        }
        else if( AppDefs.FIA_S_3F.equals( this.sistemaFase ) ) {
            fios += 3;        	
        }
        else if( AppDefs.FIA_S_3FN.equals( this.sistemaFase ) ) {
            fios += 3;        	
        }
        else if( AppDefs.FIA_S_3FNT.equals( this.sistemaFase ) ) {
            fios += 3;        	
        }
        return fios;
	}

	public int getQtdCondutorNeutroCircuito() {
        int fios = 0;
        
        if( AppDefs.FIA_S_FN.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_2F.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_2FN.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_2FT.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_2FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_3F.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_3FN.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_3FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        return fios;
	}

	public int getQtdCondutorTerraCircuito() {
        int fios = 0;
        
        if( AppDefs.FIA_S_FN.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_2F.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_2FN.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_2FT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_2FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        else if( AppDefs.FIA_S_3F.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_3FN.equals( this.sistemaFase ) ) {
            /* nothing todo! */        	
        }
        else if( AppDefs.FIA_S_3FNT.equals( this.sistemaFase ) ) {
            fios += 1;        	
        }
        return fios;
	}

}
