/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseObjectRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 16/06/2025
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
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public abstract class BaseObjectRecord implements Serializable
{
//Private Static
	public static final String BASE_OBJECT_CREATE =
		"CREATE TABLE %s ( " +
			"oid 				#SQLTYPE_INT# NOT NULL, " +
			//
			"object_id 			#SQLTYPE_INT# NOT NULL, " +
			"obj_type 			#SQLTYPE_INT# NOT NULL, " +
			"obj_type_str 		#SQLTYPE_STR# NOT NULL, " +
			"obj_ver 			#SQLTYPE_STR# NOT NULL, " +
			//
			"cad_refentity_id	#SQLTYPE_STR# NOT NULL, " +
			//
			"is_entity_object 	#SQLTYPE_BOOL# NOT NULL, " +
			"is_deleted 		#SQLTYPE_BOOL# NOT NULL, " +
			//
			"%s ) ";

	public static final String BASE_OBJECT_SELECT_BYPK =
		"SELECT " +
			"oid, " +
			//
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"obj_ver, " +
			//
			"cad_refentity_id, " +
			//
			"is_entity_object, " +
			"is_deleted, " +
			//
			"%s " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND object_id = ? " +
		  "AND is_deleted = 'N' ";

	public static final String BASE_OBJECT_SELECT_ALL =
		"SELECT " +
			"oid, " +
			//
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"obj_ver, " +
			//
			"cad_refentity_id, " +
			//
			"is_entity_object, " +
			"is_deleted, " +
			//
			"%s " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND is_deleted = 'N' " +
		"ORDER BY obj_ver, object_id ";

	public static final String BASE_OBJECT_SELECT_BYREFID =
		"SELECT " +
			"oid, " +
			//
			"object_id, " +
			"obj_type, " +
			"obj_type_str, " +
			"obj_ver, " +
			//
			"cad_refentity_id, " +
			//
			"is_entity_object, " +
			"is_deleted, " +
			//
			"%s " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND reference = ? " +
		  "AND is_deleted = 'N' " +
		"ORDER BY obj_ver, object_id ";

	public static final String BASE_OBJECT_INSERT =
		"INSERT INTO #SCHEMA_NAME#%s( " +
			"oid, " +
			//
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "obj_ver, " +
		    //
			"cad_refentity_id, " +
		    //
		    "is_entity_object, " +
		    "is_deleted, " +
			//
		    "%s " +
		") VALUES ( " +
			"?, " +
			"?, ?, ?, ?, " +
			"?, " +
			"?, ?, " +
			"%s ) ";

	public static final String BASE_OBJECT_UPDATE =
		"UPDATE #SCHEMA_NAME#%s SET " +
		    "obj_ver = ?, " +
		    //
		    "cad_refentity_id = ?, " +
		    //
		    "is_deleted = ?, " +
		    //
		    "%s " +
		"WHERE obj_ver = ? " + 
		  "AND object_id = ? ";
		
	public static final String BASE_OBJECT_NEXT_SEQ = 
		"SELECT nextval('#SCHEMA_NAME#seq_%s') ";

	public static final String BASE_OBJECT_CURR_SEQ = 
		"SELECT currval('#SCHEMA_NAME#seq_%s') ";
		
//Private
	private long oid;
	//
	private int objectId;
	private int objType;
	private String objTypeStr;
	private String objVer;
	//
	private String cadRefEntityId;
	//
	private String strIsEntityObject;
    private String strIsDeleted;
    
//Public
    
    public BaseObjectRecord()
    {
    	this.initObj(
    		AppDefs.NULL_LNG, 
    		//
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT,
    		AppDefs.NULL_STR, 
    		AppDefs.NULL_STR, 
    		//
    		AppDefs.NULL_STR, 
    		//
    		AppDefs.DEF_VALUES_NAO,
    		AppDefs.DEF_VALUES_NAO );
    }
    
    /* Methodes */

    public abstract void init(DbUtil o);

    /* INIT_OBJECT */
    
    public void initObj(
		long oid,
		//
		int objectId,
		int objType,
		String objTypeStr,
		String objVer,
		//
		String cadRefEntityId,
		//
		String strIsEntityObject,
	    String strIsDeleted )
    {
    	this.oid = oid;
    	//
    	this.objectId = objectId;
		this.objType = objType;
		this.objTypeStr = objTypeStr;
		this.objVer = objVer;
		//
		this.cadRefEntityId = cadRefEntityId;
		//
		this.strIsEntityObject = strIsEntityObject;
	    this.strIsDeleted = strIsDeleted;
    }

    public void initObj(DbUtil o)
    {
		this.setOid( o.getNextLng() );
		//
		this.setObjectId( o.getNextInt() );
		this.setObjType( o.getNextInt() );
    	this.setObjTypeStr( o.getNextStr() );
    	this.setObjVer( o.getNextStr() );
    	//
	    this.setCadRefEntityId( o.getNextStr() );
	    //
	    this.setIsEntityObject( o.getNextStr() );
	    this.setIsDeleted( o.getNextStr() );
    }

	public void initObj(CadObject o, String cadRefEntityId)
	{
		String strIsEntityObject = StringUtil.fromBoolToStr( o.isEntityObject() );
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );

	    this.initObj(
			AppDefs.NULL_LNG,
			//
			o.getObjectId(), 
			o.getObjType(), 
			o.getObjTypeStr(),
	    	o.getObjVer(),
			//
	    	cadRefEntityId,
			//
			strIsEntityObject,
		    strIsDeleted );		
	}
	
	/* TO_CADxxx */

	public abstract CadObject toCadObject(CadBlockDef oBlkDef);

	public CadObject toCadObject(CadBlockDef oBlkDef, Class c)
	{
		CadObject o = null;
		
		try {
			CadDocumentDef doc = oBlkDef.getDocument();
		
			o = (CadObject)c.newInstance();
			o.initObj(this, doc, oBlkDef);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return o;		
	}

	/* SQL */
    
	public abstract String getSqlTableName();

	public abstract SqlColumnVO[] getSqlColumn();

	public String getSqlFields()
	{
		StringBuilder sb = new StringBuilder();
		
		SqlColumnVO[] cols = this.getSqlColumn();
		int sz = cols.length - 1;
		for(int i = 0; i <= sz; i++) {
			SqlColumnVO o = cols[i];
			
			String colName = o.getColName();
			sb.append(colName);
			if(i != sz)
				sb.append(", ");
			else
				sb.append(" ");
		}
		return sb.toString();
	}
	
	public String getSqlUpdate()
	{
		StringBuilder sb = new StringBuilder();
		
		SqlColumnVO[] cols = this.getSqlColumn();
		int sz = cols.length;
		for(int i = 0; i < sz; i++) {
			SqlColumnVO o = cols[i];
			
			String colName = o.getColName();
			sb.append(colName);
			if(i != sz)
				sb.append(" = ?, ");
			else
				sb.append(" = ? ");
		}
		return sb.toString();
	}
	
    /* Getters/Setters */
	
	public String getKey()
	{
		String key = DbUtil.toSqlKey(				
			this.getObjVer(),
			this.getObjectId() );
		return key;
	}
    
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	public int getObjType() {
		return objType;
	}
	public void setObjType(int objType) {
		this.objType = objType;
	}
	public String getIsDeleted() {
		return strIsDeleted;
	}
	public void setIsDeleted(String strIsDeleted) {
		this.strIsDeleted = strIsDeleted;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getObjTypeStr() {
		return objTypeStr;
	}

	public void setObjTypeStr(String objTypeStr) {
		this.objTypeStr = objTypeStr;
	}

	public String getObjVer() {
		return objVer;
	}

	public void setObjVer(String objVer) {
		this.objVer = objVer;
	}

	public String getIsEntityObject() {
		return strIsEntityObject;
	}

	public void setIsEntityObject(String strIsEntityObject) {
		this.strIsEntityObject = strIsEntityObject;
	}

	public String getCadRefEntityId() {
		return cadRefEntityId;
	}

	public void setCadRefEntityId(String cadRefEntityId) {
		this.cadRefEntityId = cadRefEntityId;
	}

	public String getStrIsEntityObject() {
		return strIsEntityObject;
	}

	public void setStrIsEntityObject(String strIsEntityObject) {
		this.strIsEntityObject = strIsEntityObject;
	}

	public String getStrIsDeleted() {
		return strIsDeleted;
	}

	public void setStrIsDeleted(String strIsDeleted) {
		this.strIsDeleted = strIsDeleted;
	}
    
}
