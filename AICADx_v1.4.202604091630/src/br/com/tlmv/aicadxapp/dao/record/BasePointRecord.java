/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BasePointRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
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

import java.io.Serializable;
import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;

public class BasePointRecord implements Serializable
{
//Private Static
	public static final String BASE_POINT_CREATE =
		"CREATE TABLE #SCHEMA_NAME#%s ( " +
			"oid 				#SQLTYPE_INT# NOT NULL, " +
			//
			"cad_refentity_id 	#SQLTYPE_STR# NOT NULL, " +
			"obj_ver 			#SQLTYPE_STR# NOT NULL, " +
			//
		    "pt_x 				#SQLTYPE_DBL# NOT NULL, " +
		    "pt_y 				#SQLTYPE_DBL# NOT NULL, " +
		    "pt_z 				#SQLTYPE_DBL# NOT NULL ) ";

	public static final String BASE_POINT_SELECT_BYPK =
		"SELECT " +
			"oid, " +
			//
			"cad_refentity_id, " +
			"obj_ver, " +
			//
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND oid = ? ";

	public static final String BASE_POINT_SELECT_ALL =
		"SELECT " +
			"oid, " +
			//
			"cad_refentity_id, " +
			"obj_ver, " +
			//
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		"ORDER BY obj_ver, cad_refentity_id, oid ";

	public static final String BASE_POINT_SELECT_BY_REFENTID =
		"SELECT " +
			"oid, " +
			//
			"cad_refentity_id, " +
			"obj_ver, " +
			//
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND cad_refentity_id = ? " +
		  "AND is_deleted = 'N' " +
		"ORDER BY obj_ver, cad_refentity_id, oid ";

	public static final String BASE_POINT_INSERT =
		"INSERT INTO #SCHEMA_NAME#%s( " +
			"oid, " +
		    //
			"cad_refentity_id, " +
		    "obj_ver, " +
		    //
		    "pt_x, " +
		    "pt_y, " +
		    "pt_z " +
		") VALUES (?, " +
			"?, ?, " +
			"?, ?, ? ) ";

	public static final String BASE_POINT_UPDATE =
		"UPDATE #SCHEMA_NAME#%s SET " +
		    "cad_refentity_id = ?, " +
		    "obj_ver = ?, " +
		    //
		    "pt_x = ?, " +
		    "pt_y = ?, " +
		    "pt_z = ? " +
		"WHERE obj_ver = ? " + 
		  "AND oid = ? ";
		
	public static final String BASE_POINT_NEXT_SEQ = 
		"SELECT nextval('#SCHEMA_NAME#seq_%s') ";

	public static final String BASE_POINT_CURR_SEQ = 
		"SELECT currval('#SCHEMA_NAME#seq_%s') ";
		
//Private
    private long oid;
	//
    private String cadRefEntityId;    
    private String objVer;
	//
    private double ptX;
    private double ptY;
    private double ptZ;
    
//Public
	
	public BasePointRecord()
	{
		this.init(
			AppDefs.NULL_LNG, 
			//
			AppDefs.NULL_INTSTR, 
			AppDefs.NULL_STR, 
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL);
	}
	
	public BasePointRecord(
		String cadRefEntityId,
	    String objVer,
		GeomPoint3d oPt)
	{
		this.init(cadRefEntityId, objVer, oPt);
	}
	
	/* Methodes */

	public void init(
		String cadRefEntityId,
	    String objVer,
		GeomPoint3d oPt)
	{
		double ptX = oPt.getX();
		double ptY = oPt.getY();
		double ptZ = oPt.getZ();
		
		this.init(
			AppDefs.NULL_LNG, 
			//
			cadRefEntityId, 
			objVer,
			//
			ptX,
			ptY,
			ptZ);
	}
		
	public void init(
		long oid,
		//
	    String cadRefEntityId,
	    String objVer,
		//
	    double ptX,
	    double ptY,
	    double ptZ)
	{
		this.oid = oid;
		//
	    this.cadRefEntityId = cadRefEntityId;
	    this.objVer = objVer;
		//
	    this.ptX = ptX;
	    this.ptY = ptY;
	    this.ptZ = ptZ;
	}
	
	public void init(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);

		this.setOid( o.getNextLng() );
		//
		this.setCadRefEntityId( o.getNextStr() );
		this.setObjVer( o.getNextStr() );
		//
		this.setPtX( o.getNextDbl() );
		this.setPtY( o.getNextDbl() );
		this.setPtZ( o.getNextDbl() );
	}
	
	/* TO_GEOMxxx */

	public GeomPoint3d toGeomPoint3d() {
		GeomPoint3d oPt = new GeomPoint3d(
			this.ptX, 
			this.ptY, 
			this.ptZ );
	    return oPt;
	}

	/* Getters/Setters */
	
	public String getKey() {
		String key = DbUtil.toSqlKey(this.getObjVer(), this.getOid());
		return key;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getCadRefEntityId() {
		return cadRefEntityId;
	}

	public void setCadRefEntityId(String cadRefEntityId) {
		this.cadRefEntityId = cadRefEntityId;
	}

	public double getPtX() {
		return ptX;
	}

	public void setPtX(double ptX) {
		this.ptX = ptX;
	}

	public double getPtY() {
		return ptY;
	}

	public void setPtY(double ptY) {
		this.ptY = ptY;
	}

	public double getPtZ() {
		return ptZ;
	}

	public void setPtZ(double ptZ) {
		this.ptZ = ptZ;
	}

	public String getObjVer() {
		return objVer;
	}

	public void setObjVer(String objVer) {
		this.objVer = objVer;
	}

}
