/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadCilinder3dRecord.java
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

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadCilinder3d;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_cilinder3d (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    ptcenter_x numeric(20,6) NOT NULL,
//    ptcenter_y numeric(20,6) NOT NULL,
//    ptcenter_z numeric(20,6) NOT NULL,
//    radius numeric(20,6) NOT NULL,
//    altura numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadCilinder3dRecord extends BaseEntityRecord
{
//Public Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_cilinder3d ";
	
	public static final String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_cilinder3d ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "ptcenter_x numeric(20,6) NOT NULL, " +
			"ptcenter_y numeric(20,6) NOT NULL, " +
			"ptcenter_z numeric(20,6) NOT NULL, " +
			"radius numeric(20,6) NOT NULL, " +
			"altura numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL " +
		") ";
			
	public static final String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcenter_x, " +
			"ptcenter_y, " +
			"ptcenter_z, " +
			"radius, " +
			"altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_cilinder3d " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcenter_x, " +
			"ptcenter_y, " +
			"ptcenter_z, " +
			"radius, " +
			"altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_cilinder3d " +
		"WHERE reference = ? ";
	
	public static final String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcenter_x, " +
			"ptcenter_y, " +
			"ptcenter_z, " +
			"radius, " +
			"altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_cilinder3d ";
			
	public static final String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_cilinder3d( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcenter_x, " +
			"ptcenter_y, " +
			"ptcenter_z, " +
			"radius, " +
			"altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (nextval('#SCHEMA_NAME#.seq_cad_cilinder3d'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_cilinder3d SET " +
		    "reference = ?, " +
		    "ptcenter_x = ?, " +
			"ptcenter_y = ?, " +
			"ptcenter_z = ?, " +
			"radius = ?, " +
			"altura = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";		
	
	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_cilinder3d') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_cilinder3d') ";
	
//Private
	private double ptCenterX;
	private double ptCenterY;
	private double ptCenterZ;
	private double radius;
	private double altura;
	
//Public
    
    public CadCilinder3dRecord()
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
    		AppDefs.DEF_VALUES_NAO);
    }

    public CadCilinder3dRecord(ResultSet rs)
    {
    	this.init(rs);
    }
    
    public CadCilinder3dRecord(CadCilinder3d o)
    {
    	CadLayerDef oLayer = o.getLayer();

    	GeomPoint3d oPtCenter = o.getPtCenter();
    	
    	String isDeleted = ( o.isDeleted() ) ? "S" : "N";
    	
    	this.init(
    		AppDefs.NULL_LNG, 
    		o.getObjectId(), 
    		o.getObjType(), 
    		o.getObjTypeStr(),
    		oLayer.getReference(),
			oPtCenter.getX(),
			oPtCenter.getY(),
			oPtCenter.getZ(),
			o.getRadius(),
			o.getAltura(),
			isDeleted);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		double ptCenterX,
		double ptCenterY,
		double ptCenterZ,
		double radius,
		double altura,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

    	this.ptCenterX = ptCenterX;
    	this.ptCenterY = ptCenterY;
    	this.ptCenterZ = ptCenterZ;
    	this.radius = radius;
    	this.altura = altura;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setPtCenterX( o.getNextDbl() );
		this.setPtCenterY( o.getNextDbl() );
		this.setPtCenterZ( o.getNextDbl() );
		this.setRadius( o.getNextDbl() );
		this.setAltura( o.getNextDbl() );
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

		CadCilinder3d o = CadCilinder3d.create(
			oLayer, 
			this.getPtCenterX(), 
			this.getPtCenterY(), 
			this.getPtCenterZ(), 
			this.getRadius(),
			this.getAltura() );
		o.setObjectId(this.getObjectId());
	    return o;
	}
	
    /* Getters/Setters */

	public double getPtCenterX() {
		return ptCenterX;
	}

	public void setPtCenterX(double ptCenterX) {
		this.ptCenterX = ptCenterX;
	}

	public double getPtCenterY() {
		return ptCenterY;
	}

	public void setPtCenterY(double ptCenterY) {
		this.ptCenterY = ptCenterY;
	}

	public double getPtCenterZ() {
		return ptCenterZ;
	}

	public void setPtCenterZ(double ptCenterZ) {
		this.ptCenterZ = ptCenterZ;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
    
}
