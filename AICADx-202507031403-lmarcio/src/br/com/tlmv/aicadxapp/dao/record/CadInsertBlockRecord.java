/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadInsertBlockRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/06/2025
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
//CREATE TABLE cad_insert_block (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    blockName character varying(256) NOT NULL,
//    ptins_x numeric(20,6) NOT NULL,
//    ptins_y numeric(20,6) NOT NULL,
//    ptins_z numeric(20,6) NOT NULL,
//    scale_x numeric(20,6) NOT NULL,
//    scale_y numeric(20,6) NOT NULL,
//    scale_z numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadInsertBlockRecord extends BaseEntityRecord
{
//Public Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_insert_block ";
	
	public static final String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_insert_block ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "blockName character varying(256) NOT NULL, " +
		    "ptins_x numeric(20,6) NOT NULL, " +
		    "ptins_y numeric(20,6) NOT NULL, " +
		    "ptins_z numeric(20,6) NOT NULL, " +
		    "scale_x numeric(20,6) NOT NULL, " +
		    "scale_y numeric(20,6) NOT NULL, " +
		    "scale_z numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "blockName, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "scale_x, " +
		    "scale_y, " +
		    "scale_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_insert_block " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "blockName, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "scale_x, " +
		    "scale_y, " +
		    "scale_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_insert_block ";
			
	public static final String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_insert_block( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "blockName, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "scale_x, " +
		    "scale_y, " +
		    "scale_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_insert_block SET " +
		    "obj_type = ?, " +
		    "obj_type_str = ?, " +
		    "reference = ?, " +
		    "blockName = ?, " +
		    "ptins_x = ?, " +
		    "ptins_y = ?, " +
		    "ptins_z = ?, " +
		    "scale_x = ?, " +
		    "scale_y = ?, " +
		    "scale_z = ?, " +
		    "is_entity_object = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";		

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_insert_block') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_insert_block') ";
	
//Private
	private String blockName;
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	private double scaleX;
	private double scaleY;
	private double scaleZ;
	
//Public
    
    public CadInsertBlockRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		String blockName,
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		double scaleX,
		double scaleY,
		double scaleZ,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

    	this.blockName = blockName;
    	this.ptInsX = ptInsX;
    	this.ptInsY = ptInsY;
    	this.ptInsZ = ptInsZ;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.scaleZ = scaleZ;
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
	    return null;
	}

    /* Getters/Setters */

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public double getPtInsX() {
		return ptInsX;
	}

	public void setPtInsX(double ptInsX) {
		this.ptInsX = ptInsX;
	}

	public double getPtInsY() {
		return ptInsY;
	}

	public void setPtInsY(double ptInsY) {
		this.ptInsY = ptInsY;
	}

	public double getPtInsZ() {
		return ptInsZ;
	}

	public void setPtInsZ(double ptInsZ) {
		this.ptInsZ = ptInsZ;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(double scaleZ) {
		this.scaleZ = scaleZ;
	}
    
}
