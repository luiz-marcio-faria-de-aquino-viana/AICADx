/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPipeLine;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadPipeLineRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_pipe_line";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("numero_pipe", 				AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("from_object_id",			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("to_object_id",				AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("section_type", 			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("pipe_category",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("diameter",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("width",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("height",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("thickness",				AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("max_pipe_segment_length",	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("external_diameter",		AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("external_width",			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("external_height",			AppDefs.TAG_SQLTYPE_DBL)	
		
	};
	
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
	
//Public
    
    public CadPipeLineRecord()
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
    		AppDefs.NULL_DBL );
    }
    
    public CadPipeLineRecord(CadPipeLine o)
    {
    	this.init(o);
    }
    
    public CadPipeLineRecord(ResultSet rs)
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
	    double externalHeight )
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
    	
    }
    
    public void init(CadPipeLine o)
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
		    o.getExternalHeightMili() );
    	
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
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
		
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
		CadPipeLine o = new CadPipeLine(
			oBlkDef, 
			super.getCadLayerDef(doc), 
			super.getCadLevel(doc), 
			super.getZLevel(), 
			false );
		
    	o.init(
    		this.getFromObjectId(),
        	this.getToObjectId(),
    	    this.getSectionType(),
    	    this.getPipeCategory(),
        	this.getDiameter(),
        	this.getWidth(),
        	this.getHeight(),
        	this.getThickness(),
        	this.getMaxPipeSegmentLength() );
	    return o;
	}

    /* Getters/Setters */

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
