/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadBlockDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;

//
//CREATE TABLE cad_block_def (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    name_str character varying(256) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadBlockDefRecord extends BaseEntityRecord
{
//Public Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_block_def ";
	
	public static final String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_block_def ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "name_str character varying(256) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "name_str, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_block_def " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "name_str, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_block_def ";
			
	public static final String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_block_def( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "name_str, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_block_def SET " +
		    "reference = ?, " +
		    "name_str = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";
	
	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_block_def') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_block_def') ";
	
//Private
	private String nameStr;
	
//Public
    
    public CadBlockDefRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		String nameStr,
		String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.nameStr = nameStr;
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
	    return null;
	}

    /* Getters/Setters */

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}
    
}
