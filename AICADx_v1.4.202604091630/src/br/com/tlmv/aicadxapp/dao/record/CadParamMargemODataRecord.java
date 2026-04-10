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

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadParamMargemOData;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadParamMargemODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_param_margem_odata";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("parm_num", 				AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("titulo_projeto",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("disciplina",				AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("numero_desenho", 			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("descricao_desenho",		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("responsavel_tecnico",		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("escala",					AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("data_emissao",				AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("numero_revisao",			AppDefs.TAG_SQLTYPE_STR)	
		
	};
		
//Private
	private int parmNum = AppDefs.NULL_INT;
	private String tituloProjeto = AppDefs.NULL_STR;
	private String disciplina = AppDefs.NULL_STR;
	private String numeroDesenho = AppDefs.NULL_STR;
	private String descricaoDesenho = AppDefs.NULL_STR;
	private String responsavelTecnico = AppDefs.NULL_STR;
	private String escala = AppDefs.NULL_STR;
	private String dataEmissao = AppDefs.NULL_STR;
	private String numeroRevisao = AppDefs.NULL_STR;
	  
//Public
    
    public CadParamMargemODataRecord()
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
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR);
    }

    public CadParamMargemODataRecord(CadParamMargemOData o)
    {
    	this.init(o);
    }

    public CadParamMargemODataRecord(ResultSet rs)
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
		String tituloProjeto,
		String disciplina,
		String numeroDesenho,
		String descricaoDesenho,
		String responsavelTecnico,
		String escala,
		String dataEmissao,
		String numeroRevisao )
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
    	this.tituloProjeto = tituloProjeto;
    	this.disciplina = disciplina;
    	this.numeroDesenho = numeroDesenho;
    	this.descricaoDesenho = descricaoDesenho;
    	this.responsavelTecnico = responsavelTecnico;
    	this.escala = escala;
    	this.dataEmissao = dataEmissao;
    	this.numeroRevisao = numeroRevisao;
	}
    
    public void init(CadParamMargemOData o)
    {
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
		 	StringUtil.fromBoolToStr( o.isDeleted() ) );

    	this.parmNum = o.getParmNum();
    	this.tituloProjeto = o.getTituloProjeto();
    	this.disciplina = o.getDisciplina();
    	this.numeroDesenho = o.getNumeroDesenho();
    	this.descricaoDesenho = o.getDescricaoDesenho();
    	this.responsavelTecnico = o.getResponsavelTecnico();
    	this.escala = o.getEscala();
    	this.dataEmissao = o.getDataEmissao();
    	this.numeroRevisao = o.getNumeroRevisao();
    }
    
    @Override
    public void init(DbUtil o)
    {
		super.initObj(o);
		
    	this.setParmNum( o.getNextInt() );
    	this.setTituloProjeto( o.getNextStr() );
    	this.setDisciplina( o.getNextStr() );
    	this.setNumeroDesenho( o.getNextStr() );
    	this.setDescricaoDesenho( o.getNextStr() );
    	this.setResponsavelTecnico( o.getNextStr() );
    	this.setEscala( o.getNextStr() );
    	this.setDataEmissao( o.getNextStr() );
    	this.setNumeroRevisao( o.getNextStr() );
    }
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"ParmNum:%s;" +
			"TituloProjeto:%s;" +
			"Disciplina:%s;" +
			"NumeroDesenho:%s;" +
    		"DescricaoDesenho:%s;" +
    		"ResponsavelTecnico:%s;" +
    		"Escala:%s;" +
    		"DataEmissao:%s;" +
    		"NumeroRevisao:%s ",
			this.getParmNum(),
	    	this.getParmNum(),
	    	this.getTituloProjeto(),
	    	this.getDisciplina(),
	    	this.getNumeroDesenho(),
	    	this.getDescricaoDesenho(),
	    	this.getResponsavelTecnico(),
	    	this.getEscala(),
	    	this.getDataEmissao(),
	    	this.getNumeroRevisao() );
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
		CadDocumentDef doc = oBlkDef.getDocument();
		
		CadParamMargemOData o = new CadParamMargemOData(
    		doc,
    		this.getCadRefEntityId(),
    		//
    		this.parmNum,
    		this.tituloProjeto,
    		this.disciplina,
    		this.numeroDesenho,
    		this.descricaoDesenho,
    		this.responsavelTecnico,
    		this.escala,
    		this.dataEmissao,
    		this.numeroRevisao );
	    return o;
	}

    /* Getters/Setters */
    
	public int getParmNum() {
		return parmNum;
	}

	public void setParmNum(int parmNum) {
		this.parmNum = parmNum;
	}

	public String getTituloProjeto() {
		return tituloProjeto;
	}

	public void setTituloProjeto(String tituloProjeto) {
		this.tituloProjeto = tituloProjeto;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getNumeroDesenho() {
		return numeroDesenho;
	}

	public void setNumeroDesenho(String numeroDesenho) {
		this.numeroDesenho = numeroDesenho;
	}

	public String getDescricaoDesenho() {
		return descricaoDesenho;
	}

	public void setDescricaoDesenho(String descricaoDesenho) {
		this.descricaoDesenho = descricaoDesenho;
	}

	public String getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public void setResponsavelTecnico(String responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}

	public String getEscala() {
		return escala;
	}

	public void setEscala(String escala) {
		this.escala = escala;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getNumeroRevisao() {
		return numeroRevisao;
	}

	public void setNumeroRevisao(String numeroRevisao) {
		this.numeroRevisao = numeroRevisao;
	}
	
}
