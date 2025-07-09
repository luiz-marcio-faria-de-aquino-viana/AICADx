/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPolylineRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/06/2025
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
//CREATE TABLE cad_polyline (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    ptcentroid_x numeric(20,6) NOT NULL,
//    ptcentroid_y numeric(20,6) NOT NULL,
//    ptcentroid_z numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadPolylineRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_polyline ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_polyline ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "ptcentroid_x numeric(20,6) NOT NULL, " +
		    "ptcentroid_y numeric(20,6) NOT NULL, " +
		    "ptcentroid_z numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcentroid_x, " +
		    "ptcentroid_y, " +
		    "ptcentroid_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_polyline " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcentroid_x, " +
		    "ptcentroid_y, " +
		    "ptcentroid_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_polyline " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_polyline( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcentroid_x, " +
		    "ptcentroid_y, " +
		    "ptcentroid_z, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_polyline set " +
		    "reference = ?, " +
		    "ptcentroid_x = ?, " +
		    "ptcentroid_y = ?, " +
		    "ptcentroid_z = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_polyline') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_polyline') ";
	
//Private
	private double ptCentroidX;
	private double ptCentroidY;
	private double ptCentroidZ;
    
//Public
    
    public CadPolylineRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR, 
    		AppDefs.NULL_STR, 
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
		double ptCentroidX,
		double ptCentroidY,
		double ptCentroidZ,
		String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.ptCentroidX = ptCentroidX;
		this.ptCentroidY = ptCentroidY;
		this.ptCentroidZ = ptCentroidZ;
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
	    return null;
	}

    /* Getters/Setters */

	public double getPtCentroidX() {
		return ptCentroidX;
	}

	public void setPtCentroidX(double ptCentroidX) {
		this.ptCentroidX = ptCentroidX;
	}

	public double getPtCentroidY() {
		return ptCentroidY;
	}

	public void setPtCentroidY(double ptCentroidY) {
		this.ptCentroidY = ptCentroidY;
	}

	public double getPtCentroidZ() {
		return ptCentroidZ;
	}

	public void setPtCentroidZ(double ptCentroidZ) {
		this.ptCentroidZ = ptCentroidZ;
	}
    
}
