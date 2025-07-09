/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadTextRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadSphere3d;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

public class CadTextRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_text ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_text ( " +
			"oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "text_str character varying(256) NOT NULL, " +
		    "ptins_x numeric(20,6) NOT NULL, " +
		    "ptins_y numeric(20,6) NOT NULL, " +
		    "ptins_z numeric(20,6) NOT NULL, " +
		    "height numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
			"oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "text_str, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "height, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_text " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference = 
		"SELECT " +
			"oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "text_str, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "height, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_text " +
		"WHERE reference = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
			"oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "text_str, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "height, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_text " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_text( " +
			"oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "text_str, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "height, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_text'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_text set " +
		    "reference = ?, " +
		    "text_str = ?, " +
		    "ptins_x = ?, " +
		    "ptins_y = ?, " +
		    "ptins_z = ?, " +
		    "height = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_text') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_text') ";
					
//Private
	private String textStr;
	private double ptX;
	private double ptY;
	private double ptZ;
	private double height;
	
//Public
    
    public CadTextRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }

    public CadTextRecord(ResultSet rs)
    {
    	this.init(rs);
    }
    
    public CadTextRecord(CadText o)
    {
    	CadLayerDef oLayer = o.getLayer();
    	
    	String reference = oLayer.getReference();

    	GeomPoint3d oPt = o.getPt();
    	
    	double ptX = oPt.getX();
    	double ptY = oPt.getY();
    	double ptZ = oPt.getZ();
    	
    	String isDeleted = ( o.isDeleted() ) ? "S" : "N";
    	
    	this.init(
    		-1,
    		o.getObjectId(),
    		o.getObjType(),
    		o.getObjTypeStr(),
    		reference,
    		o.getText(),
    		ptX,
    		ptY,
    		ptZ,
    		o.getHeight(),
    		isDeleted);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		String textStr,
		double ptX,
		double ptY,
		double ptZ,
		double height,
		String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

    	this.textStr = textStr;
    	this.ptX = ptX;
    	this.ptY = ptY;
    	this.ptZ = ptZ;
    	this.height = height;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setTextStr( o.getNextStr() );
		this.setPtX( o.getNextDbl() );
		this.setPtY( o.getNextDbl() );
		this.setPtZ( o.getNextDbl() );
		this.setHeight( o.getNextDbl() );
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
		
		CadText o = CadText.create(
			oLayer, 
			this.getTextStr(), 
			this.getPtX(), 
			this.getPtY(), 
			this.getPtZ(), 
			this.getHeight() );
	    return o;
	}

    /* Getters/Setters */

	public String getTextStr() {
		return textStr;
	}

	public void setTextStr(String textStr) {
		this.textStr = textStr;
	}

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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
    
}
