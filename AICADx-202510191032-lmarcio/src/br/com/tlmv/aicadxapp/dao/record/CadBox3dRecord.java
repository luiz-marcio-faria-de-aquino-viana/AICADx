/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadBox3dRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadCilinder3d;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_box3d (
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
//    altura numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadBox3dRecord extends BaseEntityRecord
{
//Public Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_box3d ";

	public static final String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_box3d ( " +
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
		    "altura numeric(20,6) NOT NULL, " +
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
		    "altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_box3d " +
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
		    "altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_box3d " +
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
		    "altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_box3d ";
			
	public static final String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_box3d( " +
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
		    "altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (nextval('#SCHEMA_NAME#.seq_cad_box3d'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static final String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_box3d SET " +
		    "reference = ?, " +
		    "ptmin_x = ?, " +
		    "ptmin_y = ?, " +
		    "ptmin_z = ?, " +
		    "ptmax_x = ?, " +
		    "ptmax_y = ?, " +
		    "ptmax_z = ?, " +
		    "altura = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";		
	
	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_box3d') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_box3d') ";
	
//Private
	private double ptMinX;
	private double ptMinY;
	private double ptMinZ;
	//
	private double ptMaxX;
	private double ptMaxY;
	private double ptMaxZ;
	//
	private double altura;
	
//Public
    
    public CadBox3dRecord()
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
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }

    public CadBox3dRecord(CadBox3d o)
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
			o.getAltura(),
			isDeleted);
    }
    
    public CadBox3dRecord(ResultSet rs)
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
		double altura,
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
    	//
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
		this.setPtMinX( o.getNextDbl() );
		this.setPtMinY( o.getNextDbl() );
		this.setPtMinZ( o.getNextDbl() );
		this.setPtMaxX( o.getNextDbl() );
		this.setPtMaxY( o.getNextDbl() );
		this.setPtMaxZ( o.getNextDbl() );
		this.setAltura( o.getNextDbl() );
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

		CadBox3d o = CadBox3d.create(
			oBlkDef,
			oLayer, 
			this.getPtMinX(), 
			this.getPtMinY(), 
			this.getPtMinZ(), 
			this.getPtMaxX(), 
			this.getPtMaxY(), 
			this.getPtMaxZ(),
			this.getAltura() );
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

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
    
}
