/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadArcRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadArcRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_arc";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptcenter_x", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptcenter_y", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptcenter_z", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("radius", 		AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("start_angle", 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("end_angle", 	AppDefs.TAG_SQLTYPE_DBL)	
		
	};
	
//Private
	private double ptCenterX;
	private double ptCenterY;
	private double ptCenterZ;
	private double radius;
	private double startAngle;
	private double endAngle;
	
//Public
    
    public CadArcRecord()
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

    public CadArcRecord(CadArc o)
    {
    	this.init(o);
    }
    
    public CadArcRecord(ResultSet rs)
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
		double ptCenterX,
		double ptCenterY,
		double ptCenterZ,
		double radius,
		double startAngle,
		double endAngle )
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

    	this.ptCenterX = ptCenterX;
    	this.ptCenterY = ptCenterY;
    	this.ptCenterZ = ptCenterZ;
    	//
        this.radius = radius;
        //
        this.startAngle = startAngle; 
        this.endAngle = endAngle; 
    }
    
    public void init(CadArc o)
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
    		
    	GeomPoint3d oPtCenter = o.getPtCenter();
    	
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
			oPtCenter.getX(),
			oPtCenter.getY(),
			oPtCenter.getZ(),
			//
			o.getRadius(),
			//
			o.getStartAngle(),
			o.getEndAngle() );
        
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
		this.setPtCenterX( o.getNextDbl() );
		this.setPtCenterY( o.getNextDbl() );
		this.setPtCenterZ( o.getNextDbl() );
		//
		this.setRadius( o.getNextDbl() );
		//
		this.setStartAngle( o.getNextDbl() );
		this.setEndAngle( o.getNextDbl() );
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadArc o = new CadArc(
    		oBlkDef, 
    		super.getCadLayerDef(doc),
    		super.getCadLevel(doc),
    		super.getZLevel(), 
    		false );
    	
    	o.init(
			this.getPtCenterX(), 
			this.getPtCenterY(), 
			this.getPtCenterZ(),
			this.getRadius(),
			this.getStartAngle(),
			this.getEndAngle() );
		o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

	public double getPtCenterX() {
		return ptCenterX;
	}

	public void setPtCenterX(double ptCenterX) {
		this.ptCenterX = ptCenterX;
	}

	public double getPtCenterY() {
		return ptCenterY;
	}

	public void setPtCenterY(double ptCenterY) {
		this.ptCenterY = ptCenterY;
	}

	public double getPtCenterZ() {
		return ptCenterZ;
	}

	public void setPtCenterZ(double ptCenterZ) {
		this.ptCenterZ = ptCenterZ;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(double startAngle) {
		this.startAngle = startAngle;
	}

	public double getEndAngle() {
		return endAngle;
	}

	public void setEndAngle(double endAngle) {
		this.endAngle = endAngle;
	}
    
}
