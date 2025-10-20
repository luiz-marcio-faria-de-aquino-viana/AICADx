/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAreaPointRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_area_point (
//    oid numeric(10,0) NOT NULL,
//    cad_refentity_id numeric(10,0) NOT NULL,
//    pt_x numeric(20,6) NOT NULL,
//    pt_y numeric(20,6) NOT NULL,
//    pt_z numeric(20,6) NOT NULL );
//
public class CadAreaPointRecord extends BasePointRecord
{
//Public Static
	public static String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_area_point ";
	
	public static String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_area_point ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "cad_refentity_id numeric(10,0) NOT NULL, " +
		    "pt_x numeric(20,6) NOT NULL, " +
		    "pt_y numeric(20,6) NOT NULL, " +
		    "pt_z numeric(20,6) NOT NULL) ";
			
	public static String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "cad_refentity_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#.cad_area_point " +
		"WHERE oid = ? ";
	
	public static String sqlSelectByRefEntityId =
		"SELECT " +
		    "oid, " +
		    "cad_refentity_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#.cad_area_point " +
		"WHERE cad_refentity_id = ? " +
		"ORDER BY oid ";
	
	public static String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "cad_refentity_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#.cad_area_point " +
		"ORDER BY cad_refentity_id, oid ";
			
	public static String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_area_point( " +
		    "oid, " +
		    "cad_refentity_id, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z) " +
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_area_point'), ?, ?, ?, ?) ";
	
	public static String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_area_point SET " +
		    "cad_refentity_id = ?, " +
		    "pt_x = ?, " +
		    "pt_y = ?, " +
		    "pt_z = ? " +
		"WHERE oid = ? ";		

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_area_point') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_area_point') ";	
	
//Public
    
    public CadAreaPointRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL);
    }
	
	public CadAreaPointRecord(int cadRefEntityId, GeomPoint3d oPt)
	{
		super(cadRefEntityId, oPt);
	}
	
	public CadAreaPointRecord(ResultSet rs)
	{
		super.init(rs);
	}
    
}
