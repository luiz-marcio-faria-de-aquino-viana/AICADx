/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadDocumentDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2025
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
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadDocumentDefRecord extends BaseObjectRecord
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
	public static final String sqlTableName = "cad_document_def";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("document_name", 				AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("document_file", 				AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("default_cad_layer_def_id", 	AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("curr_cad_layer_def_id",		AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("default_cad_block_def_id",		AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("curr_cad_block_def_id",		AppDefs.TAG_SQLTYPE_INT)	
		
	};
	
//Private
	private String documentName;
	private String documentFile;
	private int defaultCadLayerDefId;
	private int currCadLayerDefId;
    private int defaultCadBlockDefId;
    private int currCadBlockDefId;
    
//Public
    
    public CadDocumentDefRecord()
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
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT );
    }

    public CadDocumentDefRecord(CadDocumentDef o)
    {
    	this.init(o);
    }
    
    public CadDocumentDefRecord(ResultSet rs)
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
		String documentName,
		String documentFile,
		int defaultCadLayerDefId,
		int currCadLayerDefId,
	    int defaultCadBlockDefId,
	    int currCadBlockDefId )
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

    	this.documentName = documentName;
    	this.documentFile = documentFile;
    	this.defaultCadLayerDefId = defaultCadLayerDefId;
    	this.currCadLayerDefId = currCadLayerDefId;
    	this.defaultCadBlockDefId = defaultCadBlockDefId;
    	this.currCadBlockDefId = currCadBlockDefId;
    }

    public void init(CadDocumentDef o)
    {
    	ProjectRepoVO repo = o.getProjectRepo();
    	
    	CadLayerDef oDefaultLayer = o.getDefaultLayerDef();
    	CadLayerDef oCurrLayer = o.getCurrLayerDef();
    	
    	CadBlockDef oDefaultBlock = o.getDefaultBlockDef();
    	CadBlockDef oCurrBlock = o.getCurrBlockDef();
    	
    	String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );

		String strDocumentName = repo.getName(); 
		String strDocumentFile = repo.getSafeName();
		int iDefaultCadLayerDefId = oDefaultLayer.getObjectId();
		int iCurrCadLayerDefId = oCurrLayer.getObjectId();
		int iDefaultCadBlockDefId = oDefaultBlock.getObjectId();
		int iCurrCadBlockDefId = oCurrBlock.getObjectId();
    	
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
    		strDocumentName,
    		strDocumentFile,
    		iDefaultCadLayerDefId,
    		iCurrCadLayerDefId,
    		iDefaultCadBlockDefId,
    		iCurrCadBlockDefId );
        
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initObj(o);

    	this.setDocumentName( o.getNextStr() );
    	this.setDocumentFile( o.getNextStr() );
    	this.setDefaultCadLayerDefId( o.getNextInt() );
    	this.setCurrCadLayerDefId( o.getNextInt() );
    	this.setDefaultCadBlockDefId( o.getNextInt() );
    	this.setCurrCadBlockDefId( o.getNextInt() );
    	
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}

    /* Getters/Setters */
    
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(String documentFile) {
		this.documentFile = documentFile;
	}
	public int getDefaultCadLayerDefId() {
		return defaultCadLayerDefId;
	}
	public void setDefaultCadLayerDefId(int defaultCadLayerDefId) {
		this.defaultCadLayerDefId = defaultCadLayerDefId;
	}
	public int getCurrCadLayerDefId() {
		return currCadLayerDefId;
	}
	public void setCurrCadLayerDefId(int currCadLayerDefId) {
		this.currCadLayerDefId = currCadLayerDefId;
	}
	public int getDefaultCadBlockDefId() {
		return defaultCadBlockDefId;
	}
	public void setDefaultCadBlockDefId(int defaultCadBlockDefId) {
		this.defaultCadBlockDefId = defaultCadBlockDefId;
	}
	public int getCurrCadBlockDefId() {
		return currCadBlockDefId;
	}
	public void setCurrCadBlockDefId(int currCadBlockDefId) {
		this.currCadBlockDefId = currCadBlockDefId;
	}
    
}
