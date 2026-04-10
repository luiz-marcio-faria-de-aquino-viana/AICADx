/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadInsertBlockRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/06/2025
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
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadInsertBlockRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_insert_block";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("block_name", 		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("ptins_x", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptins_y", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptins_z", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("scale_x",			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("scale_y",			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("scale_z",			AppDefs.TAG_SQLTYPE_DBL)	
		
	};
	
//Private
	private String blockName;
	//
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	//
	private double scaleX;
	private double scaleY;
	private double scaleZ;
	
//Public
    
    public CadInsertBlockRecord()
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
    		//
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		//
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL );
    }
    
    public CadInsertBlockRecord(CadInsertBlock o)
    {
    	this.init(o);
    }
    
    public CadInsertBlockRecord(ResultSet rs)
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
		String blockName,
		//
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		//
		double scaleX,
		double scaleY,
		double scaleZ )
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

    	this.blockName = blockName;
    	//
    	this.ptInsX = ptInsX;
    	this.ptInsY = ptInsY;
    	this.ptInsZ = ptInsZ;
    	//
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.scaleZ = scaleZ;
    }
    
    public void init(CadInsertBlock o)
    {
    	// LAYER_DEF
    	//
    	CadLayerDef oLayer = o.getLayer();
    	String reference = oLayer.getReference(); 
		
    	// LEVEL
    	//
    	CadLevel oLevel = o.getLevel();
    	String levelName = AppDefs.DEFAULT_LEVELNAME;
    	if(oLevel != null) {
        	levelName = oLevel.getLevelLocalName();
    	}
    	
    	String blkName = o.getBlockName(); 

    	GeomPoint3d oPtIns = o.getPtIns();
    	
    	String isDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
    	
    	String isLocked = StringUtil.fromBoolToStr( o.isLocked() );
    	
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
			isDeleted,
			isLocked,
			//
			reference,
			levelName,
			//
			o.getZLevel(),
			//
			blkName,
			//
			oPtIns.getX(),
			oPtIns.getY(),
			oPtIns.getZ(),
			//
			o.getScaleX(),
			o.getScaleY(),
			o.getScaleZ() );
    	
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
		this.setBlockName( o.getNextStr() );
		//
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setScaleX( o.getNextDbl() );
		this.setScaleY( o.getNextDbl() );
		this.setScaleZ( o.getNextDbl() );

    }
    	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadInsertBlock o = new CadInsertBlock(
			oBlkDef, 
    		super.getCadLayerDef(doc), 
    		super.getCadLevel(doc), 
    		super.getZLevel(),
    		false );

    	o.init(
    		this.getBlockName(), 
			//
			this.getPtInsX(), 
			this.getPtInsY(), 
			this.getPtInsZ(),
			//
			this.getScaleX(),
			this.getScaleY(),
			this.getScaleZ() );
    	o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
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

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(double scaleZ) {
		this.scaleZ = scaleZ;
	}
    
}
