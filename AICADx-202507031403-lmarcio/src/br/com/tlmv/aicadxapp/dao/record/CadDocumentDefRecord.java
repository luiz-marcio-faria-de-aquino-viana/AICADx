/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadDocumentDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadObject;

//
//CREATE TABLE cad_document_def (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    document_name character varying(256) NOT NULL,
//    document_file character varying(256) NOT NULL,
//    default_cad_layer_def_id numeric(10,0) NOT NULL,
//    curr_cad_layer_def_id numeric(10,0) NOT NULL,
//    default_cad_block_def_id numeric(10,0) NOT NULL,
//    curr_cad_block_def_id numeric(10,0) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadDocumentDefRecord extends BaseObjectRecord
{
//Public Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_document_def ";
	
	public static final String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_document_def ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "document_name character varying(256) NOT NULL, " +
		    "document_file character varying(256) NOT NULL, " +
		    "default_cad_layer_def_id numeric(10,0) NOT NULL, " +
		    "curr_cad_layer_def_id numeric(10,0) NOT NULL, " +
		    "default_cad_block_def_id numeric(10,0) NOT NULL, " +
		    "curr_cad_block_def_id numeric(10,0) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "document_name, " +
		    "document_file, " +
		    "default_cad_layer_def_id, " +
		    "curr_cad_layer_def_id, " +
		    "default_cad_block_def_id, " +
		    "curr_cad_block_def_id, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_document_def " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "document_name, " +
		    "document_file, " +
		    "default_cad_layer_def_id, " +
		    "curr_cad_layer_def_id, " +
		    "default_cad_block_def_id, " +
		    "curr_cad_block_def_id, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_document_def ";
			
	public static final String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_document_def( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "document_name, " +
		    "document_file, " +
		    "default_cad_layer_def_id, " +
		    "curr_cad_layer_def_id, " +
		    "default_cad_block_def_id, " +
		    "curr_cad_block_def_id, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_document_def SET " +
		    "obj_type = ?, " +
		    "obj_type_str = ?, " +
		    "document_name = ?, " +
		    "document_file = ?, " +
		    "default_cad_layer_def_id = ?, " +
		    "curr_cad_layer_def_id = ?, " +
		    "default_cad_block_def_id = ?, " +
		    "curr_cad_block_def_id = ?, " +
		    "is_entity_object = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";		
	
	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_document_def') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_document_def') ";
	
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
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
    		AppDefs.NULL_INT,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String documentName,
		String documentFile,
		int defaultCadLayerDefId,
		int currCadLayerDefId,
	    int defaultCadBlockDefId,
	    int currCadBlockDefId,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, AppDefs.DEF_VALUES_NAO, isDeleted);

    	this.documentName = documentName;
    	this.documentFile = documentFile;
    	this.defaultCadLayerDefId = defaultCadLayerDefId;
    	this.currCadLayerDefId = currCadLayerDefId;
    	this.defaultCadBlockDefId = defaultCadBlockDefId;
    	this.currCadBlockDefId = currCadBlockDefId;
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
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
