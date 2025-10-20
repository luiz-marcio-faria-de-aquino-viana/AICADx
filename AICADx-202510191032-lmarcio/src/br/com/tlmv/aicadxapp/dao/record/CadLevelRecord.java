/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadLevelRecord.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;

//
//CREATE TABLE cad_level (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    levelName character varying(256) NOT NULL,
//    levelText character varying(256) NOT NULL,
//    pti_x numeric(20,6) NOT NULL,
//    pti_y numeric(20,6) NOT NULL,
//    pti_z numeric(20,6) NOT NULL,
//    ptf_x numeric(20,6) NOT NULL,
//    ptf_y numeric(20,6) NOT NULL,
//    ptf_z numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadLevelRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_level ";
	
	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_level ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "levelName character varying(256) NOT NULL, " +
		    "levelText character varying(256) NOT NULL, " +
		    "pti_x numeric(20,6) NOT NULL, " +
		    "pti_y numeric(20,6) NOT NULL, " +
		    "pti_z numeric(20,6) NOT NULL, " +
		    "ptf_x numeric(20,6) NOT NULL, " +
		    "ptf_y numeric(20,6) NOT NULL, " +
		    "ptf_z numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "levelName, " +
		    "levelText, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_level " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "levelName, " +
		    "levelText, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_level " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_level( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "levelName, " +
		    "levelText, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_level set " +
		    "reference = ?, " +
		    "levelName = ?, " +
		    "levelText = ?, " +
		    "pti_x = ?, " +
		    "pti_y = ?, " +
		    "pti_z = ?, " +
		    "ptf_x = ?, " +
		    "ptf_y = ?, " +
		    "ptf_z = ?, " +
			"is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_level') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_level') ";
	
//Private
	private String levelName;
	private String levelText;
	private double ptIX;
	private double ptIY;
	private double ptIZ;
	private double ptFX;
	private double ptFY;
	private double ptFZ;
	
//Public
    
    public CadLevelRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
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
		String levelName,
		String levelText,
		double ptIX,
		double ptIY,
		double ptIZ,
		double ptFX,
		double ptFY,
		double ptFZ,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.levelName = levelName;
		this.levelText = levelText;
		this.ptIX = ptIX;
		this.ptIY = ptIY;
		this.ptIZ = ptIZ;
		this.ptFX = ptFX;
		this.ptFY = ptFY;
		this.ptFZ = ptFZ;
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}

    /* Getters/Setters */

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelText() {
		return levelText;
	}

	public void setLevelText(String levelText) {
		this.levelText = levelText;
	}

	public double getPtIX() {
		return ptIX;
	}

	public void setPtIX(double ptIX) {
		this.ptIX = ptIX;
	}

	public double getPtIY() {
		return ptIY;
	}

	public void setPtIY(double ptIY) {
		this.ptIY = ptIY;
	}

	public double getPtIZ() {
		return ptIZ;
	}

	public void setPtIZ(double ptIZ) {
		this.ptIZ = ptIZ;
	}

	public double getPtFX() {
		return ptFX;
	}

	public void setPtFX(double ptFX) {
		this.ptFX = ptFX;
	}

	public double getPtFY() {
		return ptFY;
	}

	public void setPtFY(double ptFY) {
		this.ptFY = ptFY;
	}

	public double getPtFZ() {
		return ptFZ;
	}

	public void setPtFZ(double ptFZ) {
		this.ptFZ = ptFZ;
	}
    
}
