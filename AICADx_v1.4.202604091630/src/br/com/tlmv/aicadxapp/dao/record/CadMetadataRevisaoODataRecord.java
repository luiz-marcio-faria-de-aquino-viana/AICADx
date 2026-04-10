/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMetadataRevisaoODataRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadMetadataRevisaoOData;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadMetadataRevisaoODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_metadata_revisao_odata";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("revisao_id", 			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("numero_revisao",		AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("data_revisao",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("hora_revisao", 		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("descricao",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("nome_autor",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("telefone_autor",		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("email_autor",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("publicado",			AppDefs.TAG_SQLTYPE_BOOL)	
		
	};
		
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
    
    public CadMetadataRevisaoODataRecord()
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
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR );
    }

    public CadMetadataRevisaoODataRecord(CadMetadataRevisaoOData o)
    {
		this.init(o);    	
    }
    
    public CadMetadataRevisaoODataRecord(ResultSet rs)
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

		this.revisaoId = revisaoId;
		this.numeroRevisao = numeroRevisao;
		this.dataRevisao = dataRevisao;
		this.horaRevisao = horaRevisao;
		this.descricao = descricao;
		this.nomeAutor = nomeAutor;
		this.telefoneAutor = telefoneAutor;
		this.emailAutor = emailAutor;
		this.publicado = publicado;
	}
	
    public void init(CadMetadataRevisaoOData o)
    {
    	String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
    	
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
			//
			o.getRevisaoId(),
			o.getNumeroRevisao(),
			o.getDataRevisao(),
			o.getHoraRevisao(),
			o.getDescricao(),
			o.getNomeAutor(),
			o.getTelefoneAutor(),
			o.getEmailAutor(),
			o.getPublicado() );
    }

    @Override
    public void init(DbUtil o)
    {
		super.initObj(o);
		
		this.setRevisaoId( o.getNextInt() );
		this.setNumeroRevisao( o.getNextInt() );
		this.setDataRevisao( o.getNextStr() );
		this.setHoraRevisao( o.getNextStr() );
		this.setDescricao( o.getNextStr() );
		this.setNomeAutor( o.getNextStr() );
		this.setTelefoneAutor( o.getNextStr() );
		this.setEmailAutor( o.getNextStr() );
		this.setPublicado( o.getNextStr() );
    }
    
	/* DEBUG */

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"revisaoId:%s;" +
			"numeroRevisao:%s;" +
			"dataRevisao:%s;" +
			"horaRevisao:%s;" +
			"descricao:%s;" +
			"nomeAutor:%s;" +
			"telefoneAutor:%s;" +
			"emailAutor:%s;" +
			"publicado:%s ",
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
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadMetadataRevisaoOData o = new CadMetadataRevisaoOData(doc);

		o.init(
			this.revisaoId,
			this.numeroRevisao,
			this.dataRevisao,
			this.horaRevisao,
			this.descricao,
			this.nomeAutor,
			this.telefoneAutor,
			this.emailAutor,
			this.publicado,
			this.getStrIsDeleted() );
		o.setObjectId(this.getObjectId());
		return o;
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
