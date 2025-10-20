/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadShapeRecord.java
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

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadShape;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;

//
//CREATE TABLE #SCHEMA_NAME#.cad_shape (
//    oid numeric(10,0) NOT NULL,
//    object_id numeric(10,0) NOT NULL,
//    obj_type numeric(10,0) NOT NULL,
//    obj_type_str character varying(256) NOT NULL,
//    reference character varying(256) NOT NULL,
//    shapeName character varying(256) NOT NULL,
//    shapeFile character varying(4096) NOT NULL,
//    ptins_x numeric(20,6) NOT NULL,
//    ptins_y numeric(20,6) NOT NULL,
//    ptins_z numeric(20,6) NOT NULL,
//    rotate numeric(20,6) NOT NULL,		-- object rotation angle around axis-Z (degrees)
//    is_entity_object character(1) NOT NULL,
//    is_deleted character(1) NOT NULL
//);
//
public class CadShapeRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_shape ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_shape ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "shapeName character varying(256) NOT NULL, " +
		    "shapeFile character varying(4096) NOT NULL, " +
		    "ptins_x numeric(20,6) NOT NULL, " +
		    "ptins_y numeric(20,6) NOT NULL, " +
		    "ptins_z numeric(20,6) NOT NULL, " +
		    "rotate numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "shapeName, " +
		    "shapeFile, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "rotate, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_shape " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "shapeName, " +
		    "shapeFile, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "rotate, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_shape " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_shape( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "shapeName, " +
		    "shapeFile, " +
		    "ptins_x, " +
		    "ptins_y, " +
		    "ptins_z, " +
		    "rotate, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_shape set " +
		    "reference = ?, " +
		    "shapeName = ?, " +
		    "shapeFile = ?, " +
		    "ptins_x = ?, " +
		    "ptins_y = ?, " +
		    "ptins_z = ?, " +
		    "rotate = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_shape') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_shape') ";
			
//Private
	private String fileName;
	private String name;
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	private double rotate;
	
//Public
    
    public CadShapeRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		String fileName,
		String name,
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		double rotate,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);

		this.fileName = fileName;
		this.name = name;
		this.ptInsX = ptInsX;
		this.ptInsY = ptInsY;
		this.ptInsZ = ptInsZ;
		this.rotate = rotate;
    }
	
	/* TO_CADxxx */
	
    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		String reference = this.getReference();
	
		AppCadMain cad = AppCadMain.getCad();
	
		CadDocumentDef doc = cad.getCurrDocumentDef();
	
		LayerTable oLayTbl = doc.getLayerTable();
		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);
		
		ShapeTable oShapeTbl = doc.getShapeTable();
		Shape oShape = oShapeTbl.getShape(this.getName());

		CadShape o = null;
		if(oShape != null) {
			o = CadShape.create(
				oBlkDef,
				oLayer, 
				this.getPtInsX(), 
				this.getPtInsY(), 
				this.getPtInsZ(), 
				this.getRotate(),
				oShape);
		}
	    return o;
	}
	
    /* Getters/Setters */

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}
    
}
