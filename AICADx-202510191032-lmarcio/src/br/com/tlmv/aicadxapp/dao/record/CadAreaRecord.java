/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAreaRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadArea;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

//
//CREATE TABLE cad_area (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//	  ptcentroid_x numeric(20,6) NOT NULL,
//	  ptcentroid_y numeric(20,6) NOT NULL,
//	  ptcentroid_z numeric(20,6) NOT NULL,
//    area_type numeric(10,0) NOT NULL,
//    name_str character varying(256) NOT NULL,
//    area numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadAreaRecord extends BaseEntityRecord
{
//Public Static
	public static String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#.cad_area ";
	
	public static String sqlCreate =
		"CREATE TABLE #SCHEMA_NAME#.cad_area ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
			"ptcentroid_x numeric(20,6) NOT NULL, " +
			"ptcentroid_y numeric(20,6) NOT NULL, " +
		  	"ptcentroid_z numeric(20,6) NOT NULL, " +
		    "area_type numeric(10,0) NOT NULL, " +
		    "name_str character varying(256) NOT NULL, " +
		    "area numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static String sqlSelectByPk =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
			"ptcentroid_x, " +
			"ptcentroid_y, " +
		  	"ptcentroid_z, " +
		    "area_type, " +
		    "name_str, " +
		    "area, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_area " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference =
		"SELECT " +
			"oid, " +
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"reference, " +
			"numero_ci, " +
			"ptcentroid_x, " +
			"ptcentroid_y, " +
			"ptcentroid_z, " +
			"area_type, " +
			"name_str, " +
			"area, " +
			"is_entity_object, " +
			"is_deleted " +
		"FROM #SCHEMA_NAME#.cad_area_contribuicao_drenagem " +
		"WHERE reference = ? ";
	
	public static String sqlSelectAll =
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
			"ptcentroid_x, " +
			"ptcentroid_y, " +
			"ptcentroid_z, " +
		    "area_type, " +
		    "name_str, " +
		    "area, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_area " +
		"ORDER BY object_id ";
			
	public static String sqlInsert =
		"INSERT INTO #SCHEMA_NAME#.cad_area( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
			"ptcentroid_x, " +
			"ptcentroid_y, " +
			"ptcentroid_z, " +
		    "area_type, " +
		    "name_str, " +
		    "area, " +
		    "is_entity_object, " +
		    "is_deleted " +
		") VALUES (nextval('#SCHEMA_NAME#.seq_cad_area'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public static String sqlUpdate =
		"UPDATE #SCHEMA_NAME#.cad_area SET " +
		    "reference = ?, " +
			"ptcentroid_x = ?, " +
			"ptcentroid_y = ?, " +
			"ptcentroid_z = ?, " +
		    "area_type = ?, " +
		    "name_str = ?, " +
		    "area = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";		

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_area') ";
	
	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_area') ";
					
//Private
    private double ptCentroidX;
    private double ptCentroidY;
    private double ptCentroidZ;
	private int areaType;
	private String nameStr;
	private double area;
    
//Public
    
    public CadAreaRecord()
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
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }

	public CadAreaRecord(ResultSet rs)
	{
		this.init(rs);
	}
    
    public CadAreaRecord(CadArea o)
    {
    	CadLayerDef oLayer = o.getLayer();

    	String reference = oLayer.getReference();

		GeomPoint3d ptCentroid = o.getPtCentroid();
		
	    this.ptCentroidX = ptCentroid.getX();
	    this.ptCentroidY = ptCentroid.getY();
	    this.ptCentroidZ = ptCentroid.getZ();
    	
    	String strIsDeleted = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
    	
    	this.init(
    		-1, 
    		o.getObjectId(),
    		o.getObjType(),
    		o.getObjTypeStr(),
    		oLayer.getReference(),
		    this.ptCentroidX,
		    this.ptCentroidY,
		    this.ptCentroidZ,
    		o.getAreaType(),
    		o.getName(),
    		o.getArea(),
    	    strIsDeleted );
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
		int areaType,
		String nameStr,
		double area,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

	    this.ptCentroidX = ptCentroidX;
	    this.ptCentroidY = ptCentroidY;
	    this.ptCentroidZ = ptCentroidZ;
		this.areaType = areaType;
		this.nameStr = nameStr;
		this.area = area;
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
		this.setAreaType( o.getNextInt() );
		this.setNameStr( o.getNextStr() );
		this.setArea( o.getNextDbl() );
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
		
		CadArea o = CadArea.create(
			oBlkDef,
			oLayer, 
			this.getAreaType(),
			this.getNameStr() );
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

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

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
