/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadParamEletricoOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
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

import java.text.NumberFormat;
import java.util.ArrayList;

import org.w3c.dom.Node;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class CadParamEletricoOData extends CadObject
{
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
    
    public CadParamEletricoOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_PARAMELETRICO_ODATA, doc, null);
    	
        this.init(
	    	AppDefs.NULL_INT,
	    	AppDefs.FIA_S_CARGA,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_DBL,
	    	AppDefs.NULL_DBL,
	    	AppDefs.FIA_S_FN,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR );
    }
    
    public CadParamEletricoOData(
		CadDocumentDef doc,
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
    	String comando,
    	String strIsDeleted) 
    {
    	super(AppDefs.OBJTYPE_PARAMELETRICO_ODATA, doc, null);

    	this.init(
        	parmNum,
        	tipo,
        	nomeQuadro,
        	quadroOrigem,
        	nomeCalha,
        	desvio,
        	potencia,
        	potenciaDemandada,
        	sistema,
        	circuito,
        	comando,
        	strIsDeleted);
    }

    public CadParamEletricoOData(CadParamEletricoOData other)
    {
    	super.initObj(AppDefs.OBJTYPE_PARAMELETRICO_ODATA, other.getDocument(), null, other.getCadRefEntityId());

    	this.init(other);
    }
    
    /* Methodes */
    
    public void init(
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
    	String comando,
    	String strIsDeleted)
    {
    	boolean bDeleted = AppDefs.DEF_VALUES_SIM.equals( strIsDeleted );
    	
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
    	this.setDeleted( bDeleted );
    }

	@Override
	public void init(ICadObject o) {
        CadParamEletricoOData other = (CadParamEletricoOData)o;

        String strIsDeleted = StringUtil.fromBoolToStr( other.isDeleted() ); 
    	
        this.init(
        	other.getParmNum(),
        	other.getTipo(),
        	other.getNomeQuadro(),
        	other.getQuadroOrigem(),
        	other.getNomeCalha(),
        	other.getDesvio(),
        	other.getPotencia(),
        	other.getPotenciaDemandada(),
        	other.getSistema(),
        	other.getCircuito(),
        	other.getComando(),
        	strIsDeleted );
    }

    //INIT_BY
    
    public void initQuadro(int oid, String nomeQuadro, String quadroOrigem, String sistema)
    {
        this.tipo = AppDefs.FIA_S_QUADRO;
        this.nomeQuadro = nomeQuadro;
        this.quadroOrigem = quadroOrigem;
        this.sistema = sistema;
    }
    
    public void initCarga(int oid, String quadroOrigem, double potencia, String sistema)
    {
        this.tipo = AppDefs.FIA_S_CARGA;
        this.quadroOrigem = quadroOrigem;
        this.potencia = potencia;
        this.sistema = sistema;
    }
    
    public void initIluminacao(int oid, String quadroOrigem, double potencia, String sistema)
    {
        this.tipo = AppDefs.FIA_S_ILUMINACAO;
        this.quadroOrigem = quadroOrigem;
        this.potencia = potencia;
        this.sistema = sistema;
    }
    
    public void initCaixa(int oid, String quadroOrigem)
    {
        this.tipo = AppDefs.FIA_S_CAIXA;
        this.quadroOrigem = quadroOrigem;
    }
    
    public void initDesvio(int oid, String quadroOrigem, String desvio)
    {
        this.tipo = AppDefs.FIA_S_DESVIO;
        this.desvio = desvio;
        this.quadroOrigem = quadroOrigem;
    }
    
    public void initCalha(int oid, String quadroOrigem, String nomeCalha)
    {
        this.tipo = AppDefs.FIA_S_DESVIO;
        this.nomeCalha = nomeCalha;
        this.quadroOrigem = quadroOrigem;
    }
    
    public void initComando(int oid, String quadroOrigem)
    {
        this.tipo = AppDefs.FIA_S_COMANDO;
        this.quadroOrigem = quadroOrigem;
    }

	/* CREATE */
    
    public static CadParamEletricoOData create(
		CadDocumentDef doc,    		
    	String cadRefEntityId,
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
    	String comando,
    	String strIsDeleted)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
    	
        o.init(
        	parmNum,
        	tipo,
        	nomeQuadro,
        	quadroOrigem,
        	nomeCalha,
        	desvio,
        	potencia,
        	potenciaDemandada,
        	sistema,
        	circuito,
        	comando,
        	strIsDeleted);
        return o;
    }

    //INIT_BY
    
    public static CadParamEletricoOData createQuadro(CadDocumentDef doc, int oid, String nomeQuadro, String quadroOrigem, String sistema)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_QUADRO;
        o.nomeQuadro = nomeQuadro;
        o.quadroOrigem = quadroOrigem;
        o.sistema = sistema;
        return o;
    }
    
    public static CadParamEletricoOData createCarga(CadDocumentDef doc, int oid, String quadroOrigem, double potencia, String sistema)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_CARGA;
        o.quadroOrigem = quadroOrigem;
        o.potencia = potencia;
        o.sistema = sistema;
        return o;
    }
    
    public static CadParamEletricoOData createIluminacao(CadDocumentDef doc, int oid, String quadroOrigem, double potencia, String sistema)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_ILUMINACAO;
        o.quadroOrigem = quadroOrigem;
        o.potencia = potencia;
        o.sistema = sistema;
        return o;
    }
    
    public static CadParamEletricoOData createCaixa(CadDocumentDef doc, int oid, String quadroOrigem)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_CAIXA;
        o.quadroOrigem = quadroOrigem;
        return o;
    }
    
    public static CadParamEletricoOData createDesvio(CadDocumentDef doc, int oid, String quadroOrigem, String desvio)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_DESVIO;
        o.desvio = desvio;
        o.quadroOrigem = quadroOrigem;
        return o;
    }
    
    public static CadParamEletricoOData createCalha(CadDocumentDef doc, int oid, String quadroOrigem, String nomeCalha)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_DESVIO;
        o.nomeCalha = nomeCalha;
        o.quadroOrigem = quadroOrigem;
        return o;
    }
    
    public static CadParamEletricoOData createComando(CadDocumentDef doc, int oid, String quadroOrigem)
    {
    	CadParamEletricoOData o = new CadParamEletricoOData(doc);
        o.tipo = AppDefs.FIA_S_COMANDO;
        o.quadroOrigem = quadroOrigem;
        return o;
    }
    
    /* LOAD_FROM_DATA */
    
	public void loadFrom(Node nParamEletrico)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);

		String strParmNum = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_PARMNUM); 
		String strTipo = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_TIPO); 
		String strNomeQuadro = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_NOME_QUADRO); 
		String strQuadroOrigem = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_QUADRO_ORIGEM); 
		String strNomeCalha = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_NOME_CALHA); 
		String strDesvio = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_DESVIO); 
		String strPotencia = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_POTENCIA); 
		String strPotenciaDemandada = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_POTENCIA_DEMANDADA); 
		String strSistema = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_SISTEMA); 
		String strCircuito = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_CIRCUITO); 
		String strComando = XmlUtil.getAttrAsStringByName(nParamEletrico, AppDefs.SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_COMANDO); 

		this.parmNum = StringUtil.safeInt(strParmNum);
		this.tipo = strTipo;
		this.nomeQuadro = strNomeQuadro;
		this.quadroOrigem = strQuadroOrigem;
		this.nomeCalha = strNomeCalha;
		this.desvio = strDesvio;
		this.potencia = StringUtil.safeDbl(nf6, strPotencia);
		this.potenciaDemandada = StringUtil.safeDbl(nf6, strPotenciaDemandada);
		this.sistema = strSistema;
		this.circuito = strCircuito;
	    this.comando = strComando;
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

	    if( AppDefs.FIA_S_QUADRO.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Nome Quadro(" + this.parmNum + ")", this.getNomeQuadro()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
			lsProperty.add( new ItemDataVO("Potencia(" + this.parmNum + ")", nf3.format( this.getPotencia() )) );
			lsProperty.add( new ItemDataVO("Potencia Demandada(" + this.parmNum + ")", nf3.format( this.getPotenciaDemandada() )) );
			lsProperty.add( new ItemDataVO("Sistema(" + this.parmNum + ")", this.getSistema()) );
			lsProperty.add( new ItemDataVO("Circuito(" + this.parmNum + ")", this.getCircuito()) );
			lsProperty.add( new ItemDataVO("Comando(" + this.parmNum + ")", this.getComando()) );
	    }
	    else if( AppDefs.FIA_S_CARGA.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
			lsProperty.add( new ItemDataVO("Potencia(" + this.parmNum + ")", nf3.format( this.getPotencia() )) );
			lsProperty.add( new ItemDataVO("Sistema(" + this.parmNum + ")", this.getSistema()) );
			lsProperty.add( new ItemDataVO("Circuito(" + this.parmNum + ")", this.getCircuito()) );
			lsProperty.add( new ItemDataVO("Comando(" + this.parmNum + ")", this.getComando()) );
	    }
	    else if( AppDefs.FIA_S_ILUMINACAO.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
			lsProperty.add( new ItemDataVO("Potencia(" + this.parmNum + ")", nf3.format( this.getPotencia() )) );
			lsProperty.add( new ItemDataVO("Sistema(" + this.parmNum + ")", this.getSistema()) );
			lsProperty.add( new ItemDataVO("Circuito(" + this.parmNum + ")", this.getCircuito()) );
			lsProperty.add( new ItemDataVO("Comando(" + this.parmNum + ")", this.getComando()) );
	    }
	    else if( AppDefs.FIA_S_CAIXA.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
	    }
	    else if( AppDefs.FIA_S_DESVIO.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Desvio(" + this.parmNum + ")", this.getDesvio()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
	    }
	    else if( AppDefs.FIA_S_CALHA.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Nome Calha(" + this.parmNum + ")", this.getNomeCalha()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
	    }
	    else if( AppDefs.FIA_S_COMANDO.equals(this.tipo) ) {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
			lsProperty.add( new ItemDataVO("Comando(" + this.parmNum + ")", this.getComando()) );
	    }		
	    else {
			lsProperty.add( new ItemDataVO("Tipo(" + this.parmNum + ")", this.getTipo()) );
			lsProperty.add( new ItemDataVO("Nome Quadro(" + this.parmNum + ")", this.getNomeQuadro()) );
			lsProperty.add( new ItemDataVO("Quadro Origem(" + this.parmNum + ")", this.getQuadroOrigem()) );
			lsProperty.add( new ItemDataVO("Nome Calha(" + this.parmNum + ")", this.getNomeCalha()) );
			lsProperty.add( new ItemDataVO("Desvio(" + this.parmNum + ")", this.getDesvio()) );
			lsProperty.add( new ItemDataVO("Potencia(" + this.parmNum + ")", nf3.format( this.getPotencia() )) );
			lsProperty.add( new ItemDataVO("Potencia Demandada(" + this.parmNum + ")", nf3.format( this.getPotenciaDemandada() )) );
			lsProperty.add( new ItemDataVO("Sistema(" + this.parmNum + ")", this.getSistema()) );
			lsProperty.add( new ItemDataVO("Circuito(" + this.parmNum + ")", this.getCircuito()) );
			lsProperty.add( new ItemDataVO("Comando(" + this.parmNum + ")", this.getComando()) );
	    }
		return lsProperty;
	}

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"ParmNum:%s;Tipo:%s;NomeQuadro:%s;QuadroOrigem:%s;NomeCalha:%s;Desvio:%s;Potencia:%s;PotenciaDemandada:%s;Sistema:%s;Circuito:%s;Comando:%s;",
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
