/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPontoEletricaRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxmod.eletrica.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class CadPontoEletricaRecord extends BaseEntityRecord 
{
//Public

	/* SQL */

	@Override
	public String getSqlTableName() {
		return sqlTableName;
	}
	
	@Override
	public SqlColumnVO[] getSqlColumn() {
		return sqlColumn;
	}

//Public Static
	public static final String sqlTableName = "cad_ponto_eletrica";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("shape_name", 				AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("shape_file_name", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("shape_default_z",          AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("ptins_x", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_y", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_z", 					AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("rotate",					AppDefs.TAG_SQLTYPE_DBL)
			
	};
	
//Private
	private String shapeName;
	private String shapeFileName;
	private double shapeDefaultZ;
	//
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	//
	private double rotate;
    
//Public
	
	public CadPontoEletricaRecord()
	{
		this.init(
			AppDefs.NULL_LNG,
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_INTSTR,
			//
			AppDefs.DEF_VALUES_NAO,
			AppDefs.DEF_VALUES_NAO,
			//
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL );
	}

	public CadPontoEletricaRecord(CadPontoEletrica o)
	{
    	this.init(o);
	}

	public CadPontoEletricaRecord(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);
		
    	this.init(o);
	}
	
	/* Methodes */

	public void init(
		long oid,
		//
		int objectId,
		int objType,
		String objTypeStr,
		String objVer,
		//
		String cadRefEntityId,
		//
		String strIsDeleted,
		String strIsLocked,
		//
		String reference,
		String levelName,
		//
		double zLevel,
		//
		String shapeName,
		String shapeFileName,
		double shapeDefaultZ,
		//
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		//
		double rotate )
	{
    	super.initEntity(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		strIsDeleted,
    		strIsLocked,
    		//
    		reference, 
    		levelName,
    		//
    		zLevel );

		this.shapeName = shapeName;
		this.shapeFileName = shapeFileName;
		this.shapeDefaultZ = shapeDefaultZ;
		//
		this.ptInsX = ptInsX;
		this.ptInsY = ptInsY;
		this.ptInsZ = ptInsZ;
		//
		this.rotate = rotate;
	}
	
	public void init(CadPontoEletrica o)
	{
    	// LAYER_DEF
    	//
    	CadLayerDef oLayer = o.getLayer();
    	String reference = oLayer.getReference(); 
		
    	// LEVEL
    	//
    	CadLevel oLevel = o.getLevel();
    	String levelName = AppDefs.DEFAULT_LEVELNAME;
    	if(oLevel != null)
        	levelName = oLevel.getLevelLocalName();
    		
		GeomPoint3d ptIns = o.getPtIns();

		double ptInsX = ptIns.getX();
		double ptInsY = ptIns.getY();
		double ptInsZ = ptIns.getZ();

		Shape oShape = o.getShape();
		
		String shapeName = oShape.getName();
		String shapeFileName = oShape.getFileName(); 
		double shapeDefaultZ = oShape.getDefaultZ();
		
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
		
		String strIsLocked = StringUtil.fromBoolToStr( o.isLocked() );

		this.init(
			AppDefs.NULL_LNG,
			//
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			o.getObjVer(),
			//
			o.getCadRefEntityId(),
			//
			strIsDeleted,
			strIsLocked,
			//
			reference,
			levelName,
			//
			o.getZLevel(),
			//
			shapeName,
			shapeFileName,
			shapeDefaultZ,
			//
			ptInsX,
			ptInsY,
			ptInsZ,
			//
			o.getRotate() );
		
	}
	
	@Override
	public void init(DbUtil o)
	{
		super.initEntity(o);

		this.setShapeName( o.getNextStr() );
		this.setShapeFileName( o.getNextStr() );
		this.setShapeDefaultZ( o.getNextDbl() );
		//
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setRotate( o.getNextDbl() );
	}

	/* TO_CADxxx */
	
	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();

		CadPontoEletrica o = null;
		
		ShapeTable oShapeTbl = doc.getShapeTable();
		Shape oShape = oShapeTbl.getShape( this.getShapeName() );
		if(oShape != null) {
			o = (CadPontoEletrica)super.toCadObject(oBlkDef, this.getClass());
	    	o.init(
				this.getPtInsX(), 
				this.getPtInsY(), 
				this.getPtInsZ(), 
				//
				this.getRotate(),
	    		//
	    		oShape );
		}
	    return o;
	}
	
	/* Getters/Setters */

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

	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
	}

	public String getShapeFileName() {
		return shapeFileName;
	}

	public void setShapeFileName(String shapeFileName) {
		this.shapeFileName = shapeFileName;
	}

	public double getShapeDefaultZ() {
		return shapeDefaultZ;
	}

	public void setShapeDefaultZ(double shapeDefaultZ) {
		this.shapeDefaultZ = shapeDefaultZ;
	}

}
