/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadTorus3dRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.CadTorus3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_torus3d (
//  oid numeric(10,0) NOT NULL,
//  object_id numeric(10,0) NOT NULL,
//  obj_type numeric(10,0) NOT NULL,
//  obj_type_str character varying(256) NOT NULL,
//  reference character varying(256) NOT NULL,
//  ptcenter_x numeric(20,6) NOT NULL,
//  ptcenter_y numeric(20,6) NOT NULL,
//  ptcenter_z numeric(20,6) NOT NULL,
//  radius numeric(20,6) NOT NULL,
//  torus_radius numeric(20,6) NOT NULL,
//  is_entity_object character(1) NOT NULL,
//  is_deleted character(1) NOT NULL
//);
//
public class CadTorus3dRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_torus3d ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_torus3d ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "ptcenter_x numeric(20,6) NOT NULL, " +
		    "ptcenter_y numeric(20,6) NOT NULL, " +
		    "ptcenter_z numeric(20,6) NOT NULL, " +
		    "radius numeric(20,6) NOT NULL, " +
		    "torus_radius numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
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
		    "torus_radius, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_torus3d " +
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
		    "torus_radius, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_torus3d " +
		"ORDER BY object_id ";
	
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
		    "torus_radius, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_torus3d " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_torus3d( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptcenter_x, " +
		    "ptcenter_y, " +
		    "ptcenter_z, " +
		    "radius, " +
		    "torus_radius, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_torus3d'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_torus3d set " +
		    "reference = ?, " +
		    "ptcenter_x = ?, " +
		    "ptcenter_y = ?, " +
		    "ptcenter_z = ?, " +
		    "radius = ?, " +
		    "torus_radius = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_torus3d') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_torus3d') ";
			
//Private
	private double ptCenterX;
	private double ptCenterY;
	private double ptCenterZ;
	private double radius;
	private double torusRadius;
	
//Public
    
    public CadTorus3dRecord()
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

    public CadTorus3dRecord(CadTorus3d o)
    {
    	CadLayerDef oLayer = o.getLayer();
    	
    	String reference = oLayer.getReference();
    	
    	GeomPoint3d ptCenter = o.getPtCenter();
    	
    	double ptCenterX = ptCenter.getX();
    	double ptCenterY = ptCenter.getY();
    	double ptCenterZ = ptCenter.getZ();
    	
    	this.init(
    		-1,
    		o.getObjectId(),
    		o.getObjType(),
    		o.getObjTypeStr(),
    		reference,
        	ptCenterX,
        	ptCenterY,
        	ptCenterZ,
        	o.getRadius(),
        	o.getTorusRadius(),
        	( o.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
    }
    
    public CadTorus3dRecord(ResultSet rs)
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
		double ptCenterX,
		double ptCenterY,
		double ptCenterZ,
		double radius,
		double torusRadius,
		String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.ptCenterX = ptCenterX;
		this.ptCenterY = ptCenterY;
		this.ptCenterZ = ptCenterZ;
		this.radius = radius;
		this.torusRadius = torusRadius;
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
		this.setTorusRadius( o.getNextDbl() );
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
		
		CadTorus3d o = CadTorus3d.create(
			oBlkDef,
			oLayer, 
			this.getPtCenterX(), 
			this.getPtCenterY(), 
			this.getPtCenterZ(), 
			this.getRadius(),
			this.getTorusRadius() );
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

	public double getTorusRadius() {
		return torusRadius;
	}

	public void setTorusRadius(double torusRadius) {
		this.torusRadius = torusRadius;
	}
    
}
