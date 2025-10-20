/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadPipeRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/06/2025
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
//	  numero_pipe numeric(10,0) NOT NULL,
//	  from_object_id numeric(10,0) NOT NULL,
//	  to_object_id numeric(10,0) NOT NULL,
//	  section_type character varying(256) NOT NULL,
//	  pipe_category character varying(256) NOT NULL,
//    diameter numeric(20,6) NOT NULL,
//	  width numeric(20,6) NOT NULL,
//	  height numeric(20,6) NOT NULL,
//	  thickness numeric(20,6) NOT NULL,
//	  max_pipe_segment_length numeric(20,6) NOT NULL,
//	  external_diameter numeric(20,6) NOT NULL,
//	  external_width numeric(20,6) NOT NULL,
//	  external_height numeric(20,6) NOT NULL,
//	  pti_x numeric(10,0) NOT NULL,
//	  pti_y numeric(10,0) NOT NULL,
//	  pti_z numeric(10,0) NOT NULL,
//	  ptf_x numeric(10,0) NOT NULL,
//	  ptf_y numeric(10,0) NOT NULL,
//	  ptf_z numeric(10,0) NOT NULL,
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
		    "section_type character varying(256) NOT NULL, "+
		    "pipe_category character varying(256) NOT NULL, " +
			"diameter numeric(20,6) NOT NULL, " +
			"width numeric(20,6) NOT NULL, " +
			"height numeric(20,6) NOT NULL, " +
			"thickness numeric(20,6) NOT NULL, " +
			"max_pipe_segment_length numeric(20,6) NOT NULL, " +
			"external_diameter numeric(20,6) NOT NULL, " +
			"external_width numeric(20,6) NOT NULL, " +
			"external_height numeric(20,6) NOT NULL, " +
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
		    "section_type, "+
		    "pipe_category, " +
		    "diameter, " +
			"width, " +
			"height, " +
			"thickness, " +
			"max_pipe_segment_length, " +
			"external_diameter, " +
			"external_width, " +
			"external_height, " +
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
		    "section_type, "+
		    "pipe_category, " +
		    "diameter, " +
			"width, " +
			"height, " +
			"thickness, " +
			"max_pipe_segment_length, " +
			"external_diameter, " +
			"external_width, " +
			"external_height, " +
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
		    "section_type, "+
		    "pipe_category, " +
		    "diameter, " +
			"width, " +
			"height, " +
			"thickness, " +
			"max_pipe_segment_length, " +
			"external_diameter, " +
			"external_width, " +
			"external_height, " +
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
		    "section_type, "+
		    "pipe_category, " +
		    "diameter, " +
			"width, " +
			"height, " +
			"thickness, " +
			"max_pipe_segment_length, " +
			"external_diameter, " +
			"external_width, " +
			"external_height, " +
		    "pti_x, " +
		    "pti_y, " +
		    "pti_z, " +
		    "ptf_x, " +
		    "ptf_y, " +
		    "ptf_z, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES ("
		+ "nextval('#SCHEMA_NAME#.seq_cad_pipe'), "
		+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		+ "?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_pipe SET " +
		    "reference = ?, " +
		    "numero_pipe = ?, " +
		    "from_object_id = ?, " +
		    "to_object_id = ?, " +
		    "section_type = ?, "+
		    "pipe_category = ?, " +
		    "diameter = ?, " +
			"width = ?, " +
			"height = ?, " +
			"thickness = ?, " +
			"max_pipe_segment_length = ?, " +
			"external_diameter = ?, " +
			"external_width = ?, " +
			"external_height = ?, " +
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
	private int numeroPipe				= AppDefs.NULL_INT;
	private int fromObjectId			= AppDefs.NULL_INT;
	private int toObjectId				= AppDefs.NULL_INT;
    private String sectionType			= AppDefs.NULL_STR;
    private String pipeCategory			= AppDefs.NULL_STR;
	private double diameter				= AppDefs.NULL_DBL;			// 600mm
    private double width				= AppDefs.NULL_DBL;			// 600mm
    private double height				= AppDefs.NULL_DBL;			// 300mm
    private double thickness			= AppDefs.NULL_DBL; 		// 50mm
	private double maxPipeSegmentLength = AppDefs.NULL_DBL; 		// 6.0 meters
    
	/* PIPE_EXTERNAL_DIMENSION */
    private double externalDiameter		= AppDefs.NULL_DBL;			// 600mm + 50mm = 650mm
    private double externalWidth		= AppDefs.NULL_DBL;			// 600mm + 50mm = 650mm
    private double externalHeight		= AppDefs.NULL_DBL;			// 300mm + 50mm = 350mm

    /* PIPE_START_POINT */
	private double ptIX					= AppDefs.NULL_DBL;
	private double ptIY					= AppDefs.NULL_DBL;
	private double ptIZ					= AppDefs.NULL_DBL;
    
	/* PIPE_END_POINT */
	private double ptFX					= AppDefs.NULL_DBL;
	private double ptFY					= AppDefs.NULL_DBL;
	private double ptFZ					= AppDefs.NULL_DBL;
	
//Public
    
    public CadPipeRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,     		
    		AppDefs.NULL_STR,     		
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
		    /* PIPE_EXTERNAL_DIMENSION */
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
		    /* PIPE_START_POINT */
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
		    /* PIPE_END_POINT */
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		//
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
			//
			o.getNumeroPipe(),
			o.getFromObjId(),
			o.getToObjId(),
			o.getSectionType(),
			o.getPipeCategory(),
			o.getDiameterMili(),
		    o.getWidthMili(),
		    o.getHeightMili(),
		    o.getThicknessMili(),
		    o.getMaxPipeSegmentLength(),
		    /* PIPE_EXTERNAL_DIMENSION */
		    o.getExternalDiameterMili(),
		    o.getExternalWidthMili(),
		    o.getExternalHeightMili(),
		    /* PIPE_START_POINT */
			ptIX,
			ptIY,
			ptIZ,
		    /* PIPE_END_POINT */
			ptFX,
			ptFY,
			ptFZ,
			//
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
		//
		int numeroPipe,
		int fromObjectId,
		int toObjectId,
	    String sectionType,
	    String pipeCategory,
		double diameter,
	    double width,
	    double height,
	    double thickness,
	    double maxPipeSegmentLength,
	    /* PIPE_EXTERNAL_DIMENSION */
	    double externalDiameter,
	    double externalWidth,
	    double externalHeight,
	    /* PIPE_START_POINT */
		double ptIX,
		double ptIY,
		double ptIZ,
	    /* PIPE_END_POINT */
		double ptFX,
		double ptFY,
		double ptFZ,
		//
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.numeroPipe = numeroPipe;
		this.fromObjectId = fromObjectId;
		this.toObjectId = toObjectId;
		this.sectionType = sectionType;
		this.pipeCategory = pipeCategory;
		this.diameter = diameter;
	    this.width = width;
	    this.height = height;
	    this.thickness = thickness;
	    this.maxPipeSegmentLength = maxPipeSegmentLength;
	    /* PIPE_EXTERNAL_DIMENSION */
	    this.externalDiameter = externalDiameter;
	    this.externalWidth = externalWidth;
	    this.externalHeight = externalHeight;
	    /* PIPE_START_POINT */
    	this.ptIX = ptIX;
    	this.ptIY = ptIY;
    	this.ptIZ = ptIZ;
	    /* PIPE_END_POINT */
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
		//
		this.setNumeroPipe( o.getNextInt() );
		this.setFromObjectId( o.getNextInt() );
		this.setToObjectId( o.getNextInt() );
		this.setSectionType( o.getNextStr() );
		this.setPipeCategory( o.getNextStr() );
		this.setDiameter( o.getNextDbl() );
		this.setWidth( o.getNextDbl() );
		this.setHeight( o.getNextDbl() );
		this.setThickness( o.getNextDbl() );
		this.setMaxPipeSegmentLength( o.getNextDbl() );
	    /* PIPE_EXTERNAL_DIMENSION */
		this.setExternalDiameter( o.getNextDbl() );
		this.setExternalWidth( o.getNextDbl() );
		this.setExternalHeight( o.getNextDbl() );
	    /* PIPE_START_POINT */
		this.setPtIX( o.getNextDbl() );
		this.setPtIY( o.getNextDbl() );
		this.setPtIZ( o.getNextDbl() );
	    /* PIPE_END_POINT */
		this.setPtFX( o.getNextDbl() );
		this.setPtFY( o.getNextDbl() );
		this.setPtFZ( o.getNextDbl() );
		//
		this.setIsDeleted( o.getNextStr() );
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		AppCadMain cad = AppCadMain.getCad();

		CadDocumentDef doc = cad.getCurrDocumentDef();

		LayerTable oLayTbl = doc.getLayerTable();

		int objectId = this.getObjectId();
		
		String reference = this.getReference();

		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		GeomPoint3d ptTmpPtI = new GeomPoint3d(this.getPtIX(), this.getPtIY(), this.getPtIZ());

		GeomPoint3d ptTmpPtF = new GeomPoint3d(this.getPtFX(), this.getPtFY(), this.getPtFZ());
		
    	CadPipe o = new CadPipe(oBlkDef, oLayer);
    	o.init(
    		ptTmpPtI.getX(),
    		ptTmpPtI.getY(),
    		ptTmpPtI.getZ(),
    		ptTmpPtF.getX(),
    		ptTmpPtF.getY(),
    		ptTmpPtF.getZ(),
    		this.getFromObjectId(),
        	this.getToObjectId(),
    	    this.getSectionType(),
    	    this.getPipeCategory(),
        	this.getDiameter(),
        	this.getWidth(),
        	this.getHeight(),
        	this.getThickness(),
        	this.getMaxPipeSegmentLength() );
		o.setNumeroPipe(objectId);
		o.setObjectId(objectId);

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

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getPipeCategory() {
		return pipeCategory;
	}

	public void setPipeCategory(String pipeCategory) {
		this.pipeCategory = pipeCategory;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public double getExternalDiameter() {
		return externalDiameter;
	}

	public void setExternalDiameter(double externalDiameter) {
		this.externalDiameter = externalDiameter;
	}

	public double getExternalWidth() {
		return externalWidth;
	}

	public void setExternalWidth(double externalWidth) {
		this.externalWidth = externalWidth;
	}

	public double getExternalHeight() {
		return externalHeight;
	}

	public void setExternalHeight(double externalHeight) {
		this.externalHeight = externalHeight;
	}

	public double getMaxPipeSegmentLength() {
		return maxPipeSegmentLength;
	}

	public void setMaxPipeSegmentLength(double maxPipeSegmentLength) {
		this.maxPipeSegmentLength = maxPipeSegmentLength;
	}
    
}
