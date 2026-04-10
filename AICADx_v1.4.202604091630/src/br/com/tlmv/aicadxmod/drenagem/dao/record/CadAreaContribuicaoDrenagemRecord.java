/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAreaContribuicaoDrenagemRecord.java
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

package br.com.tlmv.aicadxmod.drenagem.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CadAreaContribuicaoDrenagemRecord extends BaseEntityRecord 
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
	
	public static final String sqlTableName = "cad_area_contribuicao_drenagem";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("numero_ci", 			AppDefs.TAG_SQLTYPE_INT),
	    //
	    new SqlColumnVO("ptcentroid_x", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptcentroid_y", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptcentroid_z", 		AppDefs.TAG_SQLTYPE_DBL),
		//
	    new SqlColumnVO("area_type", 			AppDefs.TAG_SQLTYPE_INT),
	    new SqlColumnVO("name_str", 			AppDefs.TAG_SQLTYPE_STR),
	    new SqlColumnVO("area", 				AppDefs.TAG_SQLTYPE_DBL)
		
	};
	
//Private
	private int numeroCI;
    private double ptCentroidX;
    private double ptCentroidY;
    private double ptCentroidZ;
    private int areaType;
    private String nameStr;
    private double area;
    
//Public
	
	public CadAreaContribuicaoDrenagemRecord()
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
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL );
	}
	
	public CadAreaContribuicaoDrenagemRecord(CadAreaContribuicaoDrenagem o)
	{
		this.init(o);
	}

	public CadAreaContribuicaoDrenagemRecord(ResultSet rs)
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
		int numeroCI,
	    double ptCentroidX,
	    double ptCentroidY,
	    double ptCentroidZ,
	    int areaType,
	    String nameStr,
	    double area )
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
	
	    this.numeroCI = numeroCI;
	    this.ptCentroidX = ptCentroidX;
	    this.ptCentroidY = ptCentroidY;
	    this.ptCentroidZ = ptCentroidZ;
	    this.areaType = areaType;
		this.nameStr = nameStr;
	    this.area = area;
	}
	
	public void init(CadAreaContribuicaoDrenagem o)
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
    		
		CadCaixaInspecaoDrenagem oCI = o.getCI();

		GeomPoint3d ptCentroid = o.getPtCentroid();
		
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
			oCI.getNumeroCI(),
			ptCentroid.getX(),
			ptCentroid.getY(),
			ptCentroid.getZ(),
		    o.getAreaType(),
		    o.getName(),
		    o.getAreaMetro2() );
	    
	}
	
    @Override
	public void init(DbUtil o)
	{
		this.initObj(o);
		
		this.setNumeroCI( o.getNextInt() );
		this.setPtCentroidX( o.getNextDbl() );
		this.setPtCentroidY( o.getNextDbl() );
		this.setPtCentroidZ( o.getNextDbl() );
		this.setAreaType( o.getNextInt() );
		this.setNameStr( o.getNextStr() );
		this.setArea( o.getNextDbl() );
	}
	
	/* TO_CADxxx */
	
	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oBlkDef.getEntity(this.numeroCI);
		
		CadAreaContribuicaoDrenagem o = (CadAreaContribuicaoDrenagem)super.toCadObject(oBlkDef, this.getClass());
		o.init(
			this.areaType, 
			this.nameStr, 
			oCI ); 
	    return o;
	}

	/* Getters/Setters */

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public double getPtCentroidX() {
		return ptCentroidX;
	}

	public void setPtCentroidX(double ptCentroidX) {
		this.ptCentroidX = ptCentroidX;
	}

	public double getPtCentroidY() {
		return ptCentroidY;
	}

	public void setPtCentroidY(double ptCentroidY) {
		this.ptCentroidY = ptCentroidY;
	}

	public double getPtCentroidZ() {
		return ptCentroidZ;
	}

	public void setPtCentroidZ(double ptCentroidZ) {
		this.ptCentroidZ = ptCentroidZ;
	}

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

}
