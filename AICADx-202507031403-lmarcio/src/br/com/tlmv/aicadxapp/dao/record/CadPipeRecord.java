/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPipeRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/06/2025
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
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;

//
//CREATE TABLE cad_pipe (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    pti_x numeric(20,6) NOT NULL,
//    pti_y numeric(20,6) NOT NULL,
//    pti_z numeric(20,6) NOT NULL,
//    ptf_x numeric(20,6) NOT NULL,
//    ptf_y numeric(20,6) NOT NULL,
//    ptf_z numeric(20,6) NOT NULL,
//    numero_pipe numeric(10,0) NOT NULL,
//    from_object_id numeric(10,0) NOT NULL,
//    to_object_id numeric(10,0) NOT NULL,
//    diameter numeric(20,6) NOT NULL,
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadPipeRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_pipe ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_pipe ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "numero_pipe numeric(10,0) NOT NULL, " +
		    "from_object_id numeric(10,0) NOT NULL, " +
		    "to_object_id numeric(10,0) NOT NULL, " +
		    "diameter numeric(20,6) NOT NULL, " +
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
		    "numero_pipe, " +
		    "from_object_id, " +
		    "to_object_id, " +
		    "diameter, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_pipe " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectByReference = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "numero_pipe, " +
		    "from_object_id, " +
		    "to_object_id, " +
		    "diameter, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_pipe " +
		"WHERE reference = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "numero_pipe, " +
		    "from_object_id, " +
		    "to_object_id, " +
		    "diameter, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_pipe " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_pipe( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "numero_pipe, " +
		    "from_object_id, " +
		    "to_object_id, " +
		    "diameter, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (nextval('#SCHEMA_NAME#.seq_cad_pipe'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_pipe SET " +
		    "reference = ?, " +
		    "numero_pipe = ?, " +
		    "from_object_id = ?, " +
		    "to_object_id = ?, " +
		    "diameter = ?, " +
		    "pti_x = ?, " +
		    "pti_y = ?, " +
		    "pti_z = ?, " +
		    "ptf_x = ?, " +
		    "ptf_y = ?, " +
		    "ptf_z = ?, " +
			"is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.seq_cad_pipe') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.seq_cad_pipe') ";
		
//Private
	private int numeroPipe;
	private int fromObjectId;
	private int toObjectId;
	private double diameter;
	private double ptIX;
	private double ptIY;
	private double ptIZ;
	private double ptFX;
	private double ptFY;
	private double ptFZ;
	
//Public
    
    public CadPipeRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    public CadPipeRecord(CadPipe o)
    {
    	CadLayerDef oLayer = o.getLayer();
    	
    	String strIsDeleted = ( o.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO;
    	
    	GeomPoint3d ptI = o.getPtI();
    	GeomPoint3d ptF = o.getPtF();

    	double ptIX = ptI.getX();
    	double ptIY = ptI.getY();
    	double ptIZ = ptI.getZ();
    	
    	double ptFX = ptF.getX();
    	double ptFY = ptF.getY();
    	double ptFZ = ptF.getZ();
    	
    	this.init(
			-1,
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			oLayer.getReference(),
			o.getNumeroPipe(),
			o.getFromObjId(),
			o.getToObjId(),
			o.getDiameter(),
			ptIX,
			ptIY,
			ptIZ,
			ptFX,
			ptFY,
			ptFZ,
			strIsDeleted );
    }
    
    public CadPipeRecord(ResultSet rs)
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
		int numeroPipe,
		int fromObjectId,
		int toObjectId,
		double diameter,
		double ptIX,
		double ptIY,
		double ptIZ,
		double ptFX,
		double ptFY,
		double ptFZ,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.numeroPipe = numeroPipe;
		this.fromObjectId = fromObjectId;
		this.toObjectId = toObjectId;
		this.diameter = diameter;
    	this.ptIX = ptIX;
    	this.ptIY = ptIY;
    	this.ptIZ = ptIZ;
    	this.ptFX = ptFX;
    	this.ptFY = ptFY;
    	this.ptFZ = ptFZ;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
		this.setObjTypeStr( o.getNextStr() );
		this.setReference( o.getNextStr() );
		this.setNumeroPipe( o.getNextInt() );
		this.setFromObjectId( o.getNextInt() );
		this.setToObjectId( o.getNextInt() );
		this.setDiameter( o.getNextDbl() );
		this.setPtIX( o.getNextDbl() );
		this.setPtIY( o.getNextDbl() );
		this.setPtIZ( o.getNextDbl() );
		this.setPtFX( o.getNextDbl() );
		this.setPtFY( o.getNextDbl() );
		this.setPtFZ( o.getNextDbl() );
		this.setIsDeleted( o.getNextStr() );
    }
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
		String reference = this.getReference();

		AppCadMain cad = AppCadMain.getCad();

		CadDocumentDef doc = cad.getCurrDocumentDef();

		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		CadPipe o = CadPipe.create(
			oLayer, 
			this.getPtIX(), 
			this.getPtIY(), 
			this.getPtIZ(), 
			this.getPtFX(), 
			this.getPtFY(), 
			this.getPtFZ(), 
			this.getFromObjectId(),
			this.getToObjectId(),
			this.getDiameter() );
		o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

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

	public int getNumeroPipe() {
		return numeroPipe;
	}

	public void setNumeroPipe(int numeroPipe) {
		this.numeroPipe = numeroPipe;
	}

	public int getFromObjectId() {
		return fromObjectId;
	}

	public void setFromObjectId(int fromObjectId) {
		this.fromObjectId = fromObjectId;
	}

	public int getToObjectId() {
		return toObjectId;
	}

	public void setToObjectId(int toObjectId) {
		this.toObjectId = toObjectId;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
    
}
