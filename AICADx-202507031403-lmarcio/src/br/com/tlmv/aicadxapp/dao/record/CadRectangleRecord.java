/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadRectangleRecord.java
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

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadCone3d;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_rectangle (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    ptmin_x numeric(20,6) NOT NULL,
//    ptmin_y numeric(20,6) NOT NULL,
//    ptmin_z numeric(20,6) NOT NULL,
//    ptmax_x numeric(20,6) NOT NULL,
//    ptmax_y numeric(20,6) NOT NULL,
//    ptmax_z numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadRectangleRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_rectangle ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_rectangle ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "ptmin_x numeric(20,6) NOT NULL, " +
		    "ptmin_y numeric(20,6) NOT NULL, " +
		    "ptmin_z numeric(20,6) NOT NULL, " +
		    "ptmax_x numeric(20,6) NOT NULL, " +
		    "ptmax_y numeric(20,6) NOT NULL, " +
		    "ptmax_z numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptmin_x, " +
		    "ptmin_y, " +
		    "ptmin_z, " +
		    "ptmax_x, " +
		    "ptmax_y, " +
		    "ptmax_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_rectangle " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptmin_x, " +
		    "ptmin_y, " +
		    "ptmin_z, " +
		    "ptmax_x, " +
		    "ptmax_y, " +
		    "ptmax_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_rectangle " +
		"WHERE reference = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptmin_x, " +
		    "ptmin_y, " +
		    "ptmin_z, " +
		    "ptmax_x, " +
		    "ptmax_y, " +
		    "ptmax_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_rectangle " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_rectangle( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptmin_x, " +
		    "ptmin_y, " +
		    "ptmin_z, " +
		    "ptmax_x, " +
		    "ptmax_y, " +
		    "ptmax_z, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_rectangle'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_rectangle set " +
		    "reference, " +
		    "ptmin_x, " +
		    "ptmin_y, " +
		    "ptmin_z, " +
		    "ptmax_x, " +
		    "ptmax_y, " +
		    "ptmax_z, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_rectangle') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_rectangle') ";
			
//Private
	private double ptMinX;
	private double ptMinY;
	private double ptMinZ;
	//
	private double ptMaxX;
	private double ptMaxY;
	private double ptMaxZ;
	
//Public
    
    public CadRectangleRecord()
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
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }

    public CadRectangleRecord(CadRectangle o)
    {
    	CadLayerDef oLayer = o.getLayer();

    	GeomPoint3d oPtMin = o.getPtMin();
    	GeomPoint3d oPtMax = o.getPtMax();
    	
    	String isDeleted = ( o.isDeleted() ) ? "S" : "N";
    	
    	this.init(
			-1,
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			oLayer.getReference(),
			oPtMin.getX(),
			oPtMin.getY(),
			oPtMin.getZ(),
			oPtMax.getX(),
			oPtMax.getY(),
			oPtMax.getZ(),
			isDeleted);
    }

    public CadRectangleRecord(ResultSet rs)
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
		double ptMinX,
		double ptMinY,
		double ptMinZ,
		double ptMaxX,
		double ptMaxY,
		double ptMaxZ,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

    	this.ptMinX = ptMinX;
    	this.ptMinY = ptMinY;
    	this.ptMinZ = ptMinZ;
    	//
    	this.ptMaxX = ptMaxX;
    	this.ptMaxY = ptMaxY;
    	this.ptMaxZ = ptMaxZ;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setPtMinX( o.getNextDbl() );
		this.setPtMinY( o.getNextDbl() );
		this.setPtMinZ( o.getNextDbl() );
		this.setPtMaxX( o.getNextDbl() );
		this.setPtMaxY( o.getNextDbl() );
		this.setPtMaxZ( o.getNextDbl() );
		this.setIsEntityObject(AppDefs.DEF_VALUES_SIM);
		this.setIsDeleted( o.getNextStr() );
    }
		
	/* TO_CADxxx */

	public CadObject toCadObject() {
		String reference = this.getReference();

		AppCadMain cad = AppCadMain.getCad();

		CadDocumentDef doc = cad.getCurrDocumentDef();

		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		CadRectangle o = CadRectangle.create(
			oLayer, 
			this.getPtMinX(), 
			this.getPtMinY(), 
			this.getPtMinZ(), 
			this.getPtMaxX(), 
			this.getPtMaxY(), 
			this.getPtMaxZ() );
		o.setObjectId(this.getObjectId());
	    return o;
	}
	
    /* Getters/Setters */

	public double getPtMinX() {
		return ptMinX;
	}

	public void setPtMinX(double ptMinX) {
		this.ptMinX = ptMinX;
	}

	public double getPtMinY() {
		return ptMinY;
	}

	public void setPtMinY(double ptMinY) {
		this.ptMinY = ptMinY;
	}

	public double getPtMinZ() {
		return ptMinZ;
	}

	public void setPtMinZ(double ptMinZ) {
		this.ptMinZ = ptMinZ;
	}

	public double getPtMaxX() {
		return ptMaxX;
	}

	public void setPtMaxX(double ptMaxX) {
		this.ptMaxX = ptMaxX;
	}

	public double getPtMaxY() {
		return ptMaxY;
	}

	public void setPtMaxY(double ptMaxY) {
		this.ptMaxY = ptMaxY;
	}

	public double getPtMaxZ() {
		return ptMaxZ;
	}

	public void setPtMaxZ(double ptMaxZ) {
		this.ptMaxZ = ptMaxZ;
	}
    
}
