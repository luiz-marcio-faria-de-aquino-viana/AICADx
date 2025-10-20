/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPolylineRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/09/2025
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
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPolyline;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

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
	
	public static final String sqlSelectByReference = 
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
		"WHERE reference = ? ";
	
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
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_polyline'), ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_polyline set " +
		    "reference = ?, " +
		    "ptcentroid_x = ?, " +
		    "ptcentroid_y = ?, " +
		    "ptcentroid_z = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_polyline') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_polyline') ";
	
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

    public CadPolylineRecord(ResultSet rs)
    {
    	this.init(rs);
    }
    
    public CadPolylineRecord(CadPolyline o)
    {
    	CadLayerDef oLayer = o.getLayer();
    	
    	GeomPoint3d oPtCentroid = new GeomPoint3d(o.getPtCentroid());
    	double ptCentroidX = oPtCentroid.getX(); 
    	double ptCentroidY = oPtCentroid.getY(); 
    	double ptCentroidZ = oPtCentroid.getZ(); 
    	
    	this.init(
    		-1L, 
    		o.getObjectId(), 
    		o.getObjType(), 
    		o.getObjTypeStr(), 
    		oLayer.getReference(), 
    		ptCentroidX,
    		ptCentroidY,
    		ptCentroidZ,
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
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);

		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setPtCentroidX( o.getNextDbl() );
		this.setPtCentroidY( o.getNextDbl() );
		this.setPtCentroidZ( o.getNextDbl() );
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
		
		CadPolyline o = new CadPolyline(oBlkDef, oLayer);
		o.setObjectId(this.getObjectId());
		return o;
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
