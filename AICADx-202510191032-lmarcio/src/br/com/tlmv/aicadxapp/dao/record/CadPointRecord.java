/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPointRecord.java
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

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_point (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    numero_pipe numeric(20,6) NOT NULL,
//    from_object_id numeric(20,6) NOT NULL,
//    to_object_id numeric(20,6) NOT NULL,
//    diameter numeric(20,6) NOT NULL,
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
public class CadPointRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE IF EXISTS #SCHEMA_NAME#.cad_point ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_point ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "pt_x numeric(20,6) NOT NULL, " +
		    "pt_y numeric(20,6) NOT NULL, " +
		    "pt_z numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_point " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_point " +
		"WHERE reference = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_point " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_point( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_point'), ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_point set " +
		    "reference = ?, " +
		    "pt_x = ?, " +
		    "pt_y = ?, " +
		    "pt_z = ?, " +
			"is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_point') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_point') ";

//Private
	private double ptX;
	private double ptY;
	private double ptZ;
    
//Public
    
    public CadPointRecord()
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
    
    public CadPointRecord(CadPoint o)
    {
    	CadLayerDef oLayer = o.getLayer();

    	GeomPoint3d oPt = o.getPt();
    	
    	String isDeleted = ( o.isDeleted() ) ? "S" : "N";
    	
    	this.init(
			-1,
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			oLayer.getReference(),
			oPt.getX(),
			oPt.getY(),
			oPt.getZ(),
			isDeleted);
    }
    
    public CadPointRecord(ResultSet rs)
    {
    	this.init(rs);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		double ptX,
		double ptY,
		double ptZ,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

    	this.ptX = ptX;
    	this.ptY = ptY;
    	this.ptZ = ptZ;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setPtX( o.getNextDbl() );
		this.setPtY( o.getNextDbl() );
		this.setPtZ( o.getNextDbl() );
		this.setIsEntityObject(AppDefs.DEF_VALUES_SIM);
		this.setIsDeleted( o.getNextStr() );
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		String reference = this.getReference();

		AppCadMain cad = AppCadMain.getCad();

		CadDocumentDef doc = cad.getCurrDocumentDef();

		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		CadPoint o = CadPoint.create(
			oBlkDef,
			oLayer, 
			this.getPtX(), 
			this.getPtY(), 
			this.getPtZ() );
		o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

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
