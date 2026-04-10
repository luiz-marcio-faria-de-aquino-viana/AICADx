/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadParamEletricoODataRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 25/06/2025
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

package br.com.tlmv.aicadxmod.eletrica.dao.record;

import java.sql.ResultSet;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CadParamEletricoODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_param_eletrico_odata";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("parm_num", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("tipo",	 				AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("nome_quadro", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("quadro_origem", 		AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("nome_calha", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("desvio", 				AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("potencia", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("potencia_demandada", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("sistema", 				AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("circuito", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("comando", 				AppDefs.TAG_SQLTYPE_STR)

	};
		
//Private
	private int parmNum = AppDefs.NULL_INT;
    private String tipo = AppDefs.FIA_S_CARGA;
    private String nomeQuadro = AppDefs.NULL_STR;
    private String quadroOrigem = AppDefs.NULL_STR;
    private String nomeCalha = AppDefs.NULL_STR;
    private String desvio = AppDefs.NULL_STR;
    private double potencia = AppDefs.NULL_DBL;
    private double potenciaDemandada = AppDefs.NULL_DBL;
    private String sistema = AppDefs.FIA_S_FN;
    private String circuito = AppDefs.NULL_STR;
    private String comando = AppDefs.NULL_STR;
	  
//Public
    
    public CadParamEletricoODataRecord()
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
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR);
    }

    public CadParamEletricoODataRecord(CadParamEletricoOData o)
    {
		this.init(o);
    }
    
    public CadParamEletricoODataRecord(ResultSet rs)
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
		//
		int parmNum,
	    String tipo,
	    String nomeQuadro,
	    String quadroOrigem,
	    String nomeCalha,
	    String desvio,
	    double potencia,
	    double potenciaDemandada,
	    String sistema,
	    String circuito,
	    String comando )
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

    	this.parmNum = parmNum;
    	this.tipo = tipo;
    	this.nomeQuadro = nomeQuadro;
    	this.quadroOrigem = quadroOrigem;
    	this.nomeCalha = nomeCalha;
    	this.desvio = desvio;
    	this.potencia = potencia;
    	this.potenciaDemandada = potenciaDemandada;
    	this.sistema = sistema;
    	this.circuito = circuito;
    	this.comando = comando;
	}
    
    public void init(CadParamEletricoOData o)
    {
    	String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
    	
		super.initObj(
			AppDefs.NULL_LNG, 
			//
			o.getObjectId(), 
			o.getObjType(), 
			o.getObjTypeStr(), 
			o.getObjVer(), 
			//
			o.getCadRefEntityId(),
			//
			AppDefs.DEF_VALUES_NAO, 
			strIsDeleted );

    	this.parmNum = o.getParmNum();
    	this.tipo = o.getTipo();
    	this.nomeQuadro = o.getNomeQuadro();
    	this.quadroOrigem = o.getQuadroOrigem();
    	this.nomeCalha = o.getNomeCalha();
    	this.desvio = o.getDesvio();
    	this.potencia = o.getPotencia();
    	this.potenciaDemandada = o.getPotenciaDemandada();
    	this.sistema = o.getSistema();
    	this.circuito = o.getCircuito();
    	this.comando = o.getComando();
    }
    
    @Override
    public void init(DbUtil o)
    {
		super.initObj(o);
		
    	this.setParmNum( o.getNextInt() );
    	this.setTipo( o.getNextStr() );
    	this.setNomeQuadro( o.getNextStr() );
    	this.setQuadroOrigem( o.getNextStr() );
    	this.setNomeCalha( o.getNextStr() );
    	this.setDesvio( o.getNextStr() );
    	this.setPotencia( o.getNextDbl() );
    	this.setPotenciaDemandada( o.getNextDbl() );
    	this.setSistema( o.getNextStr() );
    	this.setCircuito( o.getNextStr() );
    	this.setComando( o.getNextStr() );
    }
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"CadRefEntityId:%s;" +
			"ParmNum:%s;" +
			"Tipo:%s;" +
			"NomeQuadro:%s;" +
			"QuadroOrigem:%s;" +
			"NomeCalha:%s;" +
			"Desvio:%s;" +
			"Potencia:%s;" +
			"PotenciaDemandada:%s;" +
			"Sistema:%s;" +
			"Circuito:%s;" +
			"Comando:%s;",
			this.getCadRefEntityId(),
			this.getParmNum(),
			this.getTipo(),
			this.getNomeQuadro(),
			this.getQuadroOrigem(),
			this.getNomeCalha(),
			this.getDesvio(),
			nf3.format( this.getPotencia() ),
			nf3.format( this.getPotenciaDemandada() ),
			this.getSistema(),
			this.getCircuito(),
			this.getComando() );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadParamEletricoOData o = CadParamEletricoOData.create(
			oBlkDef.getDocument(),
			//
			this.getCadRefEntityId(),
			this.getParmNum(),
			this.getTipo(),
			this.getNomeQuadro(),
			this.getQuadroOrigem(),
			this.getNomeCalha(),
			this.getDesvio(),
			this.getPotencia(),
			this.getPotenciaDemandada(),
			this.getSistema(),
			this.getCircuito(),
			this.getComando(),
			this.getIsDeleted() );
		o.setObjectId(this.getObjectId());
		return o;
	}

    /* Getters/Setters */
    
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNomeQuadro() {
		return nomeQuadro;
	}
	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}
	public String getQuadroOrigem() {
		return quadroOrigem;
	}
	public void setQuadroOrigem(String quadroOrigem) {
		this.quadroOrigem = quadroOrigem;
	}
	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getDesvio() {
		return desvio;
	}
	public void setDesvio(String desvio) {
		this.desvio = desvio;
	}
	public String getNomeCalha() {
		return nomeCalha;
	}
	public void setNomeCalha(String nomeCalha) {
		this.nomeCalha = nomeCalha;
	}

	public double getPotencia() {
		return potencia;
	}

	public void setPotencia(double potencia) {
		this.potencia = potencia;
	}

	public double getPotenciaDemandada() {
		return potenciaDemandada;
	}

	public void setPotenciaDemandada(double potenciaDemandada) {
		this.potenciaDemandada = potenciaDemandada;
	}

	public int getParmNum() {
		return parmNum;
	}

	public void setParmNum(int parmNum) {
		this.parmNum = parmNum;
	}
	
}
