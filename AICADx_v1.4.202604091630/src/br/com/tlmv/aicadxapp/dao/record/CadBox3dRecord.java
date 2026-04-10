/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadBox3dRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_box3d";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptmin_x", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptmin_y", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptmin_z", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptmax_x", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptmax_y", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptmax_z", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("altura",	AppDefs.TAG_SQLTYPE_DBL)	
		
	};
		
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
    		//
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		//
    		AppDefs.NULL_DBL );
    }

    public CadBox3dRecord(CadBox3d o)
    {
    	this.init(o);
    }
    
    public CadBox3dRecord(ResultSet rs)
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
		double ptMinX,
		double ptMinY,
		double ptMinZ,
		//
		double ptMaxX,
		double ptMaxY,
		double ptMaxZ,
		//
		double altura )
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

    public void init(CadBox3d o)
    {
    	// LAYER_DEF
    	//
    	CadLayerDef oLayer = o.getLayer();
    	String reference = oLayer.getReference(); 
		
    	// LEVEL
    	//
    	String levelName = AppDefs.DEFAULT_LEVELNAME;
    	
    	CadLevel oLevel = o.getLevel();
    	if(oLevel != null)
        	levelName = oLevel.getLevelLocalName();
    		
    	GeomPoint3d oPtMin = o.getPtMin();
    	GeomPoint3d oPtMax = o.getPtMax();
    	
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
			oPtMin.getX(),
			oPtMin.getY(),
			oPtMin.getZ(),
			//
			oPtMax.getX(),
			oPtMax.getY(),
			oPtMax.getZ(),
			//
			o.getAltura() );
    }

    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
		this.setPtMinX( o.getNextDbl() );
		this.setPtMinY( o.getNextDbl() );
		this.setPtMinZ( o.getNextDbl() );
		//
		this.setPtMaxX( o.getNextDbl() );
		this.setPtMaxY( o.getNextDbl() );
		this.setPtMaxZ( o.getNextDbl() );
		//
		this.setAltura( o.getNextDbl() );
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadBox3d o = new CadBox3d(
    		oBlkDef, 
    		super.getCadLayerDef(doc),
    		super.getCadLevel(doc),
    		super.getZLevel(), 
    		false );

    	o.init(
			this.getPtMinX(), 
			this.getPtMinY(), 
			//
			this.getPtMaxX(), 
			this.getPtMaxY(),
			//
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
