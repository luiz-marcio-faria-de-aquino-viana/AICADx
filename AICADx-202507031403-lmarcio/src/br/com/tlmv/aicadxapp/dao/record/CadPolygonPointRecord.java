/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPolygonPointRecord.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadObject;

//
//CREATE TABLE cad_polygon_point (
//    oid numeric(10,0) NOT NULL,
//    cad_polygon_id numeric(10,0) NOT NULL,
//    pt_x numeric(20,6) NOT NULL,
//    pt_y numeric(20,6) NOT NULL,
//    pt_z numeric(20,6) NOT NULL
//);
//
public class CadPolygonPointRecord extends BaseObjectRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_polygon_point ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_polygon_point ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "cad_polygon_id numeric(10,0) NOT NULL, " +
		    "pt_x numeric(20,6) NOT NULL, " +
		    "pt_y numeric(20,6) NOT NULL, " +
		    "pt_z numeric(20,6) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "cad_polygon_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#.cad_polygon_point " +
		"WHERE oid = ? ";
	
	public static final String sqlSelectByCadPolygonId = 
		"SELECT " +
		    "oid, " +
		    "cad_polygon_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#.cad_polygon_point " +
		"WHERE cad_polygon_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "cad_polygon_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#.cad_polygon_point " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_polygon_point( " +
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
		"UPDATE #SCHEMA_NAME#.cad_polygon_point set " +
		    "reference = ?, " +
		    "ptcentroid_x = ?, " +
		    "ptcentroid_y = ?, " +
		    "ptcentroid_z = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_polygon_point') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_polygon_point') ";
		
//Private
	private int cadPolygonId;
	private double ptX;
	private double ptY;
	private double ptZ;
    
//Public
    
    public CadPolygonPointRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int cadPolygonId,
		double ptX,
		double ptY,
		double ptZ)
    {
    	super.init(oid, AppDefs.NULL_INT, AppDefs.OBJTYPE_NONE, AppDefs.ARR_OBJTYPE_STR[0], AppDefs.DEF_VALUES_NAO, AppDefs.DEF_VALUES_NAO);

		this.cadPolygonId = cadPolygonId;
		this.ptX = ptX;
		this.ptY = ptY;
		this.ptZ = ptZ;
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
	    return null;
	}

    /* Getters/Setters */

	public int getCadPolygonId() {
		return cadPolygonId;
	}

	public void setCadPolygonId(int cadPolygonId) {
		this.cadPolygonId = cadPolygonId;
	}

	public double getPtX() {
		return ptX;
	}

	public void setPtX(double ptX) {
		this.ptX = ptX;
	}

	public double getPtY() {
		return ptY;
	}

	public void setPtY(double ptY) {
		this.ptY = ptY;
	}

	public double getPtZ() {
		return ptZ;
	}

	public void setPtZ(double ptZ) {
		this.ptZ = ptZ;
	}
    
}
