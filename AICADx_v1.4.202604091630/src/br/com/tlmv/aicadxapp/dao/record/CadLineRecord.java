/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadLineRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadLineRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_line";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("pti_x", 					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("pti_y",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("pti_z",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptf_x",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptf_y",					AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptf_z",					AppDefs.TAG_SQLTYPE_DBL)	
		
	};
		
//Private
	private double ptIX;
	private double ptIY;
	private double ptIZ;
	//
	private double ptFX;
	private double ptFY;
	private double ptFZ;
	
//Public
    
    public CadLineRecord()
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
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL );
			
    }
    
    public CadLineRecord(CadLine o)
    {
    	this.init(o);
    }
    
    public CadLineRecord(ResultSet rs)
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
		double ptIX,
		double ptIY,
		double ptIZ,
		//
		double ptFX,
		double ptFY,
		double ptFZ )
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

    	this.ptIX = ptIX;
    	this.ptIY = ptIY;
    	this.ptIZ = ptIZ;
    	//
    	this.ptFX = ptFX;
    	this.ptFY = ptFY;
    	this.ptFZ = ptFZ;
    }
    
    public void init(CadLine o)
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
    		
    	GeomPoint3d oPtI = o.getPtI();
    	GeomPoint3d oPtF = o.getPtF();
    	
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
			oPtI.getX(),
			oPtI.getY(),
			oPtI.getZ(),
			//
			oPtF.getX(),
			oPtF.getY(),
			oPtF.getZ() );
    	
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
		this.setPtIX( o.getNextDbl() );
		this.setPtIY( o.getNextDbl() );
		this.setPtIZ( o.getNextDbl() );
		//
		this.setPtFX( o.getNextDbl() );
		this.setPtFY( o.getNextDbl() );
		this.setPtFZ( o.getNextDbl() );

    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadLine o = new CadLine(
			oBlkDef, 
    		super.getCadLayerDef(doc), 
    		super.getCadLevel(doc), 
    		super.getZLevel(),
    		false );

    	o.init(
			this.getPtIX(), 
			this.getPtIY(), 
			this.getPtIZ(), 
			//
			this.getPtFX(),
			this.getPtFY(),
			this.getPtFZ() );
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
    
}
