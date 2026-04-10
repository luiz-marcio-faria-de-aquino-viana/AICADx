/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMetadataRevisaoOData.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class CadMetadataRevisaoOData extends CadObject
{
//Private
	private int revisaoId = AppDefs.NULL_INT;
	private int numeroRevisao = AppDefs.NULL_INT;
    private String dataRevisao = AppDefs.NULL_STR;
    private String horaRevisao = AppDefs.NULL_STR;
    private String descricao = AppDefs.NULL_STR;
    private String nomeAutor = AppDefs.NULL_STR;
    private String telefoneAutor = AppDefs.NULL_STR;
    private String emailAutor = AppDefs.NULL_STR;
    private String publicado = AppDefs.DEF_VALUES_NAO;
	
//Public
    
    public CadMetadataRevisaoOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_PARAMMARGEM_ODATA, doc, null);
    	
        this.init(
	    	AppDefs.NULL_INT,
	    	AppDefs.NULL_INT,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.NULL_STR,
	    	AppDefs.DEF_VALUES_NAO);
    }
    
    public CadMetadataRevisaoOData(
		CadDocumentDef doc,
    	int revisaoId,
    	int numeroRevisao,
    	String dataRevisao,
    	String horaRevisao,
    	String descricao,
    	String nomeAutor,
    	String telefoneAutor,
    	String emailAutor,
    	String publicado,
    	String strIsDeleted) 
    {
    	super(AppDefs.OBJTYPE_PARAMMARGEM_ODATA, doc, null);

    	this.init(
	    	revisaoId,
	    	numeroRevisao,
	    	dataRevisao,
	    	horaRevisao,
	    	descricao,
	    	nomeAutor,
	    	telefoneAutor,
	    	emailAutor,
	    	publicado,
        	strIsDeleted);
    }

    public CadMetadataRevisaoOData(CadMetadataRevisaoOData other)
    {
    	super(AppDefs.OBJTYPE_PARAMMARGEM_ODATA, other.getDocument(), null);

    	String strIsDeleted = ( other.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 
    	
        this.init(
    		other.revisaoId,
    		other.numeroRevisao,
    		other.dataRevisao,
    		other.horaRevisao,
    		other.descricao,
    		other.nomeAutor,
    		other.telefoneAutor,
    		other.emailAutor,
    		other.publicado,
        	strIsDeleted );
    }
    
    /* Methodes */
    
    public void init(
    	int revisaoId,
    	int numeroRevisao,
    	String dataRevisao,
    	String horaRevisao,
    	String descricao,
    	String nomeAutor,
    	String telefoneAutor,
    	String emailAutor,
    	String publicado,
    	String strIsDeleted )
    {
    	boolean bDeleted = AppDefs.DEF_VALUES_SIM.equals( strIsDeleted );
    	
    	this.revisaoId = revisaoId;
    	this.numeroRevisao = numeroRevisao;
    	this.dataRevisao = dataRevisao;
    	this.horaRevisao = horaRevisao;
    	this.descricao = descricao;
    	this.nomeAutor = nomeAutor;
    	this.telefoneAutor = telefoneAutor;
    	this.emailAutor = emailAutor;
    	this.publicado = publicado;

        this.setDeleted( bDeleted );
    }

	@Override
	public void init(ICadObject other) {
		//TODO:
	}
	
	/* CREATE */
    
    public static CadMetadataRevisaoOData create(
		CadDocumentDef doc,
		int revisaoId,
		int numeroRevisao,
		String dataRevisao,
		String horaRevisao,
		String descricao,
		String nomeAutor,
		String telefoneAutor,
		String emailAutor,
		String publicado )
    {
    	CadMetadataRevisaoOData o = new CadMetadataRevisaoOData(doc);
    	
        o.init(
	    	revisaoId,
	    	numeroRevisao,
	    	dataRevisao,
	    	horaRevisao,
	    	descricao,
	    	nomeAutor,
	    	telefoneAutor,
	    	emailAutor,
	    	publicado,
			AppDefs.DEF_VALUES_NAO );
        return o;
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

		lsProperty.add( new ItemDataVO("RevisaoId", Integer.toString( this.revisaoId ) ) );
		lsProperty.add( new ItemDataVO("NumeroRevisao", Integer.toString( this.numeroRevisao ) ) );
		lsProperty.add( new ItemDataVO("DataRevisao", this.dataRevisao) );
		lsProperty.add( new ItemDataVO("HoraRevisao", this.horaRevisao) );
		lsProperty.add( new ItemDataVO("Descricao", this.descricao) );
		lsProperty.add( new ItemDataVO("NomeAutor", this.nomeAutor) );
		lsProperty.add( new ItemDataVO("TelefoneAutor", this.telefoneAutor) );
		lsProperty.add( new ItemDataVO("EmailAutor", this.emailAutor) );
		lsProperty.add( new ItemDataVO("Publicado", this.publicado ) );
		return lsProperty;
	}

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
	    	"revisaoId:%s;" +
	    	"numeroRevisao:%s;" +
	    	"dataRevisao:%s;" +
	    	"horaRevisao:%s;" +
	    	"descricao:%s;" +
	    	"nomeAutor:%s;" +
	    	"telefoneAutor:%s;" +
	    	"emailAutor:%s;" +
	    	"publicado:%s",			
	    	this.revisaoId,
	    	this.numeroRevisao,
	    	this.dataRevisao,
	    	this.horaRevisao,
	    	this.descricao,
	    	this.nomeAutor,
	    	this.telefoneAutor,
	    	this.emailAutor,
	    	this.publicado );
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

	public int getRevisaoId() {
		return revisaoId;
	}

	public void setRevisaoId(int revisaoId) {
		this.revisaoId = revisaoId;
	}

	public int getNumeroRevisao() {
		return numeroRevisao;
	}

	public void setNumeroRevisao(int numeroRevisao) {
		this.numeroRevisao = numeroRevisao;
	}

	public String getDataRevisao() {
		return dataRevisao;
	}

	public void setDataRevisao(String dataRevisao) {
		this.dataRevisao = dataRevisao;
	}

	public String getHoraRevisao() {
		return horaRevisao;
	}

	public void setHoraRevisao(String horaRevisao) {
		this.horaRevisao = horaRevisao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public String getTelefoneAutor() {
		return telefoneAutor;
	}

	public void setTelefoneAutor(String telefoneAutor) {
		this.telefoneAutor = telefoneAutor;
	}

	public String getEmailAutor() {
		return emailAutor;
	}

	public void setEmailAutor(String emailAutor) {
		this.emailAutor = emailAutor;
	}

	public String getPublicado() {
		return publicado;
	}

	public void setPublicado(String publicado) {
		this.publicado = publicado;
	}
	
}
