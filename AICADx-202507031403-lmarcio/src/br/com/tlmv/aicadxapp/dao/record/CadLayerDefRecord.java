/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadLayerDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_layer_def (
//	    oid numeric(10,0) NOT NULL,
//	    object_id numeric(10,0) NOT NULL,
//	    obj_type numeric(10,0) NOT NULL,
//	    obj_type_str character varying(256) NOT NULL,
//	    layer_name character varying(256) NOT NULL,
//	    reference character varying(256) NOT NULL,
//	    color_name character(256) NOT NULL,
//	    ltype_name character varying(256) NOT NULL,
//	    line_weight numeric(20,6) NOT NULL,
//	    is_layer_on character(1) NOT NULL,
//	    is_entity_object character(1) NOT NULL,
//	    is_deleted character(1) NOT NULL
//);
//
public class CadLayerDefRecord extends BaseObjectRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_layer_def ";
	
	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_layer_def( " +
			"oid numeric(10,0) NOT NULL, " +
			"object_id numeric(10,0) NOT NULL, " +
			"obj_type numeric(10,0) NOT NULL, " +
			"obj_type_str character varying(256) NOT NULL, " +
			"layer_name character varying(256) NOT NULL, " +
			"reference character varying(256) NOT NULL, " +
		    "color_name character(256) NOT NULL, " +
		    "ltype_name character varying(256) NOT NULL, " +
		    "line_weight numeric(20,6) NOT NULL, " +
		    "is_layer_on character(1) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
	
	public static final String sqlSelectByPk = 
		"SELECT " +
			"oid, " +
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"layer_name, " +
			"reference, " +
		    "color_name, " +
		    "ltype_name, " +
		    "line_weight, " +
		    "is_layer_on, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_layer_def " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
			"oid, " +
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"layer_name, " +
			"reference, " +
		    "color_name, " +
		    "ltype_name, " +
		    "line_weight, " +
		    "is_layer_on, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_layer_def " +
		"ORDER BY layer_name ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_layer_def( " +
			"oid, " +
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"layer_name, " +
			"reference, " +
		    "color_name, " +
		    "ltype_name, " +
		    "line_weight, " +
		    "is_layer_on, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_layer_def set " +
			"layer_name = ?, " +
			"reference = ?, " +
		    "color_name = ?, " +
		    "ltype_name = ?, " +
		    "line_weight = ?, " +
		    "is_layer_on = ?, " +
		    "is_entity_object = ?, " +
			"is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_layer_def') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_layer_def') ";

//Private
	private String layerName;
	private String reference;
	private String colorName;
	private String ltypeName;
	private double lineWeight;
	private String isLayerOn;
	
//Public
    
    public CadLayerDefRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }

    public CadLayerDefRecord(ResultSet rs)
    {
    	this.init(rs);
    }    
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String layerName,
		String reference,
		String colorName,
		String ltypeName,
		double lineWeight,
		String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, AppDefs.DEF_VALUES_NAO, isDeleted);

		this.layerName = layerName;
		this.reference = reference;
		this.colorName = colorName;
		this.ltypeName = ltypeName;
		this.lineWeight = lineWeight;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setLayerName( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setColorName( o.getNextStr() );
		this.setLtypeName( o.getNextStr() );
		this.setLineWeight( o.getNextDbl() );
		this.setIsDeleted( o.getNextStr() );
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
	    return null;
	}

    /* Getters/Setters */

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getIsLayerOn() {
		return isLayerOn;
	}

	public void setIsLayerOn(String isLayerOn) {
		this.isLayerOn = isLayerOn;
	}

	public String getLtypeName() {
		return ltypeName;
	}

	public void setLtypeName(String ltypeName) {
		this.ltypeName = ltypeName;
	}

	public double getLineWeight() {
		return lineWeight;
	}

	public void setLineWeight(double lineWeight) {
		this.lineWeight = lineWeight;
	}
    
}
