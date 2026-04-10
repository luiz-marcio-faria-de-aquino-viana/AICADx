/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadBlockDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadBlockDefRecord extends BaseObjectRecord
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
	
	public static final String sqlTableName = "cad_block_def";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("tipo", 			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("name", 			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("full_file_name", 	AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("file_name", 		AppDefs.TAG_SQLTYPE_STR)		

	};
	
//Private
	private Integer tipo;	
	private String nameStr;
	private String fullFileNameStr;
	private String fileNameStr;
	
//Public
    
    public CadBlockDefRecord()
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
    		AppDefs.NULL_STR );
    }

    public CadBlockDefRecord(CadBlockDef o)
    {
    	this.init(o);
    }
    
    public CadBlockDefRecord(ResultSet rs)
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
		String isDeleted,
		//
		Integer tipo,	
		String nameStr,
		String fullFileNameStr,
		String fileNameStr )
    {
    	super.initObj(oid, objectId, objType, objTypeStr, objVer, cadRefEntityId, AppDefs.DEF_VALUES_NAO, isDeleted);

    	this.tipo = tipo;
    	this.nameStr = nameStr;
		this.fullFileNameStr = fullFileNameStr;
		this.fileNameStr = fileNameStr;
    }

    public void init(CadBlockDef o)
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
    		o.getTipo(),	
    		o.getName(),
    		o.getFullFileName(),
    		o.getFileName() );
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initObj(o);

    	this.setTipo( o.getNextInt() );	
    	this.setNameStr( o.getNextStr() );
    	this.setFullFileNameStr( o.getNextStr() );
    	this.setFileNameStr( o.getNextStr() );

    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadBlockDef o = new CadBlockDef(doc);

    	o.init(
			this.getTipo(),
			this.getNameStr(),
			this.getFullFileNameStr() );
		o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public String getFullFileNameStr() {
		return fullFileNameStr;
	}

	public void setFullFileNameStr(String fullFileNameStr) {
		this.fullFileNameStr = fullFileNameStr;
	}

	public String getFileNameStr() {
		return fileNameStr;
	}

	public void setFileNameStr(String fileNameStr) {
		this.fileNameStr = fileNameStr;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
    
}
