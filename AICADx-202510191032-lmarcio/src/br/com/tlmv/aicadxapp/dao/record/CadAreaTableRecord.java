/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAreaTableRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadAreaTable;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_area_table (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    ptins_x numeric(20,6) NOT NULL,
//    ptins_y numeric(20,6) NOT NULL,
//    ptins_z numeric(20,6) NOT NULL,
//	  area_type numeric(10,0) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadAreaTableRecord extends BaseEntityRecord
{
//Public Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_area_table ";
	
	public static final String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_area_table ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "ptins_x numeric(20,6) NOT NULL, " +
		    "ptins_y numeric(20,6) NOT NULL, " +
		    "ptins_z numeric(20,6) NOT NULL, " +
		    "area_type numeric(10,0) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "area_type, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_area_table " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "area_type, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_area_table " +
		"WHERE reference = ? ";
	
	public static final String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "area_type, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_area_table " +
		"ORDER BY object_id ";
			
	public static final String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_area_table( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "area_type, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (nextval('#SCHEMA_NAME#.seq_cad_area_table'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_area_table SET " +
		    "reference = ?, " +
		    "ptins_x = ?, " +
		    "ptins_y = ?, " +
		    "ptins_z = ?, " +
		    "area_type = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";		

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_area_table') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_area_table') ";
	
//Private
	private int areaType;
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	
//Public
    
    public CadAreaTableRecord()
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
    		AppDefs.NULL_INT,
    		AppDefs.DEF_VALUES_NAO);
    }

    public CadAreaTableRecord(CadAreaTable o)
    {
    	this.init(o);
    }
    
    public CadAreaTableRecord(ResultSet rs)
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
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		int areaType,
		String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.ptInsX = ptInsX;
		this.ptInsY = ptInsY;
		this.ptInsZ = ptInsZ;
		this.areaType = areaType;
    }
    
    public void init(CadAreaTable o)
    {
    	CadLayerDef oLayer = o.getLayer();

    	GeomPoint3d oPtIns = o.getPtIns();
    	
    	String isDeleted = ( o.isDeleted() ) ? "S" : "N";
    	
    	this.init(
			-1,
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			oLayer.getReference(),
			oPtIns.getX(),
			oPtIns.getY(),
			oPtIns.getZ(),
			o.getAreaType(),
			isDeleted);
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		this.setAreaType( o.getNextInt() );
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

		CadAreaTable o = CadAreaTable.create(
			oBlkDef,
			oLayer, 
			this.getAreaType(), 
			this.getPtInsX(), 
			this.getPtInsY(), 
			this.getPtInsZ() );
		o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
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
    
}
