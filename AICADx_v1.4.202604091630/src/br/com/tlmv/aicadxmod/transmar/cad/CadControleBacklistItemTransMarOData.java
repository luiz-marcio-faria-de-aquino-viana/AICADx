/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadContentorTransMarOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/08/2025
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

package br.com.tlmv.aicadxmod.transmar.cad;

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
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;

public class CadControleBacklistItemTransMarOData extends CadObject 
{
//Private
    private int cdAtendimento;
    private Date dtAtendimento;
    private int cdEmbarcacao;
    private String nmEmbarcacao;
    private int cdCliente;
    private String nmCliente;
    private int cdTipo;
    private String dsTipo;
    private int cdContentor;
    private String dsContentor;    
    private double comprimento;
    private double largura;
    private double altura;
    private double peso;
    private String observacao;
    private boolean isRecebido;
    private Date dtRecebido;
    private boolean isEntregue;
    private Date dtEntregue;
    private String justificativa;
    
//Public

    public CadControleBacklistItemTransMarOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_PARAMCONTENTOR_ODATA, doc, null);
    }
	
	/* Methodes */
	
	private void init(
	    int cdAtendimento,
	    Date dtAtendimento,
	    int cdEmbarcacao,
	    String nmEmbarcacao,
	    int cdCliente,
	    String nmCliente,
	    int cdTipo,
	    String dsTipo,
	    int cdContentor,
	    String dsContentor,    
	    double comprimento,
	    double largura,
	    double altura,
	    double peso,
	    String observacao,
	    boolean isRecebido,
	    Date dtRecebido,
	    boolean isEntregue,
	    Date dtEntregue,
	    String justificativa) 
	{
	    this.cdAtendimento = cdAtendimento;
	    this.dtAtendimento = dtAtendimento;
	    this.cdEmbarcacao = cdEmbarcacao;
	    this.nmEmbarcacao = nmEmbarcacao;
	    this.cdCliente = cdCliente;
	    this.nmCliente = nmCliente;
	    this.cdTipo = cdTipo;
	    this.dsTipo = dsTipo;
	    this.cdContentor = cdContentor;
	    this.dsContentor = dsContentor;    
	    this.comprimento = comprimento;
	    this.largura = largura;
	    this.altura = altura;
	    this.peso = peso;
	    this.observacao = observacao;
	    this.isRecebido = isRecebido;
	    this.dtRecebido = dtRecebido;
	    this.isEntregue = isEntregue;
	    this.dtEntregue = dtEntregue;
	    this.justificativa = justificativa;
	}
	
	@Override
	public void init(ICadObject o) {
		CadControleBacklistItemTransMarOData other = (CadControleBacklistItemTransMarOData)o;

		this.init(
		    other.getCdAtendimento(),
		    other.getDtAtendimento(),
		    other.getCdEmbarcacao(),
		    other.getNmEmbarcacao(),
		    other.getCdCliente(),
		    other.getNmCliente(),
		    other.getCdTipo(),
		    other.getDsTipo(),
		    other.getCdContentor(),
		    other.getDsContentor(),    
		    other.getComprimento(),
		    other.getLargura(),
		    other.getAltura(),
		    other.getPeso(),
		    other.getObservacao(),
		    other.isRecebido(),
		    other.getDtRecebido(),
		    other.isEntregue(),
		    other.getDtEntregue(),
		    other.getJustificativa() );
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
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
	
	public static CadControleBacklistItemTransMarOData create(
		CadDocumentDef doc,
	    int cdAtendimento,
	    Date dtAtendimento,
	    int cdEmbarcacao,
	    String nmEmbarcacao,
	    int cdCliente,
	    String nmCliente,
	    int cdTipo,
	    String dsTipo,
	    int cdContentor,
	    String dsContentor,    
	    double comprimento,
	    double largura,
	    double altura,
	    double peso,
	    String observacao,
	    boolean isRecebido,
	    Date dtRecebido,
	    boolean isEntregue,
	    Date dtEntregue,
	    String justificativa) 
	{
		CadControleBacklistItemTransMarOData o = new CadControleBacklistItemTransMarOData(doc);
    	o.init(
    		cdAtendimento,
		    dtAtendimento,
		    cdEmbarcacao,
		    nmEmbarcacao,
		    cdCliente,
		    nmCliente,
		    cdTipo,
		    dsTipo,
		    cdContentor,
		    dsContentor,    
		    comprimento,
		    largura,
		    altura,
		    peso,
		    observacao,
		    isRecebido,
		    dtRecebido,
		    isEntregue,
		    dtEntregue,
		    justificativa);
    	return o;
    }

	public static CadControleBacklistItemTransMarOData create(CadControleBacklistItemTransMarOData other)
	{		
		CadControleBacklistItemTransMarOData o = new CadControleBacklistItemTransMarOData(other.getDocument());
    	o.init(
		    other.getCdAtendimento(),
		    other.getDtAtendimento(),
		    other.getCdEmbarcacao(),
		    other.getNmEmbarcacao(),
		    other.getCdCliente(),
		    other.getNmCliente(),
		    other.getCdTipo(),
		    other.getDsTipo(),
		    other.getCdContentor(),
		    other.getDsContentor(),
		    other.getComprimento(),
		    other.getLargura(),
		    other.getAltura(),
		    other.getPeso(),
		    other.getObservacao(),
		    other.isRecebido(),
		    other.getDtRecebido(),
		    other.isEntregue(),
		    other.getDtEntregue(),
		    other.getJustificativa() );
    	return o;
    }
	
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

	    String strIsRecebido = ( this.isRecebido() ? "S" : "N" );

		String strIsEntregue = ( this.isEntregue() ? "S" : "N" );
		
		String str = String.format(
			"CdAtendimento:%s;" +
			"DtAtendimento:%s;" +
			"CdEmbarcacao:%s;" +
			"NmEmbarcacao:%s;" +
			"CdCliente:%s;" +
			"NmCliente:%s;" +
			"CdTipo:%s;" +
			"DsTipo:%s;" +
			"CdContentor:%s;" +
			"DsContentor:%s;" +
			"Comprimento:%s;" +
			"Largura:%s;" +
			"Altura:%s;" +
			"Peso:%s;" +
			"Observacao:%s;" +
			"IsRecebido:%s;" +
			"DtRecebido:%s;" +
			"IsEntregue:%s;" +
			"DtEntrega:%s;" +
			"Justificativa:%s;",
		    this.getCdAtendimento(),
		    df.format( this.getDtAtendimento() ),
		    this.getCdEmbarcacao(),
		    this.getNmEmbarcacao(),
		    this.getCdCliente(),
		    this.getNmCliente(),
		    this.getCdTipo(),
		    this.getDsTipo(),
		    this.getCdContentor(),
		    this.getDsContentor(),
		    nf6.format( this.getComprimento() ),
		    nf6.format( this.getLargura() ),
		    nf6.format( this.getAltura() ),
		    nf6.format( this.getPeso() ),
		    this.getObservacao(),
		    strIsRecebido,
		    df.format( this.getDtRecebido() ),
		    strIsEntregue,
		    df.format( this.getDtEntregue() ),
		    this.getJustificativa());
		return str;
	}
	
	@Override
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
	
    /* Getters/Setters */
	
	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public int getCdAtendimento() {
		return cdAtendimento;
	}

	public void setCdAtendimento(int cdAtendimento) {
		this.cdAtendimento = cdAtendimento;
	}

	public int getCdEmbarcacao() {
		return cdEmbarcacao;
	}

	public void setCdEmbarcacao(int cdEmbarcacao) {
		this.cdEmbarcacao = cdEmbarcacao;
	}

	public String getNmEmbarcacao() {
		return nmEmbarcacao;
	}

	public void setNmEmbarcacao(String nmEmbarcacao) {
		this.nmEmbarcacao = nmEmbarcacao;
	}

	public int getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(int cdCliente) {
		this.cdCliente = cdCliente;
	}

	public String getNmCliente() {
		return nmCliente;
	}

	public void setNmCliente(String nmCliente) {
		this.nmCliente = nmCliente;
	}

	public int getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(int cdTipo) {
		this.cdTipo = cdTipo;
	}

	public String getDsTipo() {
		return dsTipo;
	}

	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}

	public int getCdContentor() {
		return cdContentor;
	}

	public void setCdContentor(int cdContentor) {
		this.cdContentor = cdContentor;
	}

	public String getDsContentor() {
		return dsContentor;
	}

	public void setDsContentor(String dsContentor) {
		this.dsContentor = dsContentor;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public boolean isRecebido() {
		return isRecebido;
	}

	public void setRecebido(boolean isRecebido) {
		this.isRecebido = isRecebido;
	}

	public Date getDtRecebido() {
		return dtRecebido;
	}

	public void setDtRecebido(Date dtRecebido) {
		this.dtRecebido = dtRecebido;
	}

	public boolean isEntregue() {
		return isEntregue;
	}

	public void setEntregue(boolean isEntregue) {
		this.isEntregue = isEntregue;
	}

	public Date getDtEntregue() {
		return dtEntregue;
	}

	public void setDtEntregue(Date dtEntregue) {
		this.dtEntregue = dtEntregue;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Date getDtAtendimento() {
		return dtAtendimento;
	}

	public void setDtAtendimento(Date dtAtendimento) {
		this.dtAtendimento = dtAtendimento;
	}

}
