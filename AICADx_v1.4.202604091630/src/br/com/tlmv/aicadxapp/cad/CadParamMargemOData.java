/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadParamMargemOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/01/2026
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

package br.com.tlmv.aicadxapp.cad;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.w3c.dom.Node;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class CadParamMargemOData extends CadObject
{
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
    
    public CadParamMargemOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_PARAMMARGEM_ODATA, doc, null);
    	
        this.init(
	    	AppDefs.NULL_INT,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR );
    }
    
    public CadParamMargemOData(
		CadDocumentDef doc,
		String cadRefEntityId,
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
    	super(AppDefs.OBJTYPE_PARAMMARGEM_ODATA, doc, null, cadRefEntityId);

        this.init(
        	parmNum,
        	tituloProjeto,
        	disciplina,
        	numeroDesenho,
        	descricaoDesenho,
        	responsavelTecnico,
        	escala,
        	dataEmissao,
        	numeroRevisao );
    }

    public CadParamMargemOData(CadParamMargemOData other)
    {
    	super( AppDefs.OBJTYPE_PARAMMARGEM_ODATA, 
    		   other.getDocument(), 
    		   null, 
    		   other.getCadRefEntityId() );

        this.init(
        	other.getParmNum(),
        	other.getTituloProjeto(),
        	other.getDisciplina(),
        	other.getNumeroDesenho(),
        	other.getDescricaoDesenho(),
        	other.getResponsavelTecnico(),
        	other.getEscala(),
        	other.getDataEmissao(),
        	other.getNumeroRevisao() );
    }
    
    /* Methodes */
    
    public void init(
    	int parmNum,
        String tituloProjeto,
        String disciplina,
        String numeroDesenho,
        String descricaoDesenho,
        String responsavelTecnico,
        String escala,
        String dataEmissao,
        String numeroRevisao)
    {
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

	@Override
	public void init(ICadObject other) {
		//TOD:
	}

	/* CREATE */
    
    public static CadParamMargemOData create(
		CadDocumentDef doc,    		
    	String cadRefEntityId,
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
    	CadParamMargemOData o = new CadParamMargemOData(
    		doc,
    		cadRefEntityId,
    		//
        	parmNum,
            tituloProjeto,
            disciplina,
            numeroDesenho,
            descricaoDesenho,
            responsavelTecnico,
            escala,
            dataEmissao,
            numeroRevisao );
        return o;
    }

    /* LOAD_FROM_DATA */
    
	public void loadFrom(Node nParamEletrico)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);

		String strParmNum = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_PARMNUM); 
		String strTituloProjeto = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_TITULOPROJETO); 
		String strDisciplina = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_DISCIPLINA); 
		String strNumeroDesenho = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_NUMERODESENHO); 
		String strDescricaoDesenho = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_DESCRICAODESENHO); 
		String strResponsavelTecnico = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_RESPONSAVELTECNICO); 
		String strEscala = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_ESCALA); 
		String strDataEmissao = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_DATAEMISSAO); 
		String strNumeroRevisao = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMMARGEM_NUMEROREVISAO); 
		
		this.parmNum = StringUtil.safeInt(strNumeroRevisao);
		this.tituloProjeto = strTituloProjeto;
		this.disciplina = strDisciplina;
		this.numeroDesenho = strNumeroDesenho;
		this.responsavelTecnico = strResponsavelTecnico;
		this.escala = strEscala;
		this.dataEmissao = strDataEmissao;
		this.numeroRevisao = strNumeroRevisao;
	}
	
	/* RESET */

	@Override
	public void reset() {
		// TODO:
	}
    
	/* DEBUG */

	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();			

		lsProperty.add( new ItemDataVO("Projeto", this.tituloProjeto) );
		lsProperty.add( new ItemDataVO("Disciplina", this.disciplina) );
		lsProperty.add( new ItemDataVO("No.Desenho", this.numeroDesenho) );
		lsProperty.add( new ItemDataVO("Resp.Tecnico", this.responsavelTecnico) );
		lsProperty.add( new ItemDataVO("Escala", this.escala) );
		lsProperty.add( new ItemDataVO("Dt.Emissao", this.dataEmissao) );
		lsProperty.add( new ItemDataVO("No.Revisao", this.numeroRevisao) );
		return lsProperty;
	}

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"ParmNum:%s;" +
			"TituloProjeto:%s;" +
			"Disciplina:%s;" +
			"NumeroDesenho:%s;" +
			"ResponsavelTecnico:%s;" +
			"Escala:%s;" +
			"DataEmissao:%s;" +
			"NumeroRevisao:%s;",
			this.getParmNum(),
			this.tituloProjeto,
			this.disciplina,
			this.numeroDesenho,
			this.responsavelTecnico,
			this.escala,
			this.dataEmissao,
			this.numeroRevisao );
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
