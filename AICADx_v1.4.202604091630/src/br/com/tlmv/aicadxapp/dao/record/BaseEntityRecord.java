/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseEntityRecord.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public abstract class BaseEntityRecord extends BaseObjectRecord
{
//Private Static
	public static final String sqlDrop =
		"DROP TABLE #SCHEMA_NAME#%s ";

	public static final String BASE_ENTITY_CREATE =
		"CREATE TABLE %s ( " +
			"oid 				#SQLTYPE_INT# NOT NULL, " +
			//
			"object_id 			#SQLTYPE_INT# NOT NULL, " +
			"obj_type 			#SQLTYPE_INT# NOT NULL, " +
			"obj_type_str 		#SQLTYPE_STR# NOT NULL, " +
			"obj_ver 			#SQLTYPE_STR# NOT NULL, " +
			//
			"cad_refentity_id 	#SQLTYPE_STR# NOT NULL, " +
			//
			"is_entity_object 	#SQLTYPE_BOOL# NOT NULL, " +
			"is_deleted 		#SQLTYPE_BOOL# NOT NULL, " +
			"is_locked 			#SQLTYPE_BOOL# NOT NULL, " +
			//
			"reference 			#SQLTYPE_STR# NOT NULL, " +
			"level_name 		#SQLTYPE_STR# NOT NULL, " +
			//
			"z_level		 	#SQLTYPE_DBL# NOT NULL, " +
			//
			"%s ) ";

	public static final String BASE_ENTITY_SELECT_BYPK =
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
			"is_locked, " +
			//
			"reference, " +
			"level_name, " +
			//
			"z_level, " +
			//
			"%s " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND object_id = ? " +
		  "AND is_deleted = 'N' ";

	public static final String BASE_ENTITY_SELECT_ALL =
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
			"is_locked, " +
			//
			"reference, " +
			"level_name, " +
			//
			"z_level, " +
			//
			"%s " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND is_deleted = 'N' " +
		"ORDER BY obj_ver, object_id ";

	public static final String BASE_ENTITY_SELECT_BY_LAYREF =
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
			"is_locked, " +
			//
			"reference, " +
			"level_name, " +
			//
			"z_level, " +
			//
			"%s " +
		"FROM #SCHEMA_NAME#%s " +
		"WHERE obj_ver = ? " +
		  "AND reference = ? " +
		  "AND is_deleted = 'N' " +
		"ORDER BY obj_ver, object_id ";

	public static final String BASE_ENTITY_INSERT =
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
			"is_locked, " +
			//
			"reference, " +
			"level_name, " +
			//
			"z_level, " +
			//
		    "%s " +
		") VALUES ( " +
			"?, " +
			"?, ?, ?, ?, " +
			"?, " +
			"?, ?, ?, " +
			"?, ?, " +
			"?, " +
			"%s ) ";

	public static final String BASE_ENTITY_UPDATE =
		"UPDATE #SCHEMA_NAME#%s SET " +
		    "is_deleted = ?, " +
			"is_locked = ?, " +
			//
			"reference = ?, " +
			"level_name = ?, " +
			//
			"z_level, " +
			//
		    "%s " +
		"WHERE obj_ver = ? " + 
		  "AND object_id = ? ";
		
	public static final String BASE_ENTITY_NEXT_SEQ = 
		"SELECT nextval('#SCHEMA_NAME#seq_%s') ";

	public static final String BASE_ENTITY_CURR_SEQ = 
		"SELECT currval('#SCHEMA_NAME#seq_%s') ";
	
//Private	
	private String strIsLocked;
	//
	private String reference;
	private String levelName;
	//
	private double zLevel;
    
//Public
    
    public BaseEntityRecord()
    {
    	this.initEntity(
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
    		AppDefs.DEF_VALUES_NAO,
    		//
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_DBL );
    }
    
    /* Methodes */

    public void initEntity(
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
	    double zLevel )
    {
    	super.initObj(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		AppDefs.DEF_VALUES_SIM,
    	    strIsDeleted );
    	
    	this.strIsLocked = strIsLocked;
    	//
    	this.reference = reference;
    	this.levelName = levelName;
    	//
    	this.zLevel = zLevel;
    	
    }
    
    public void initEntity(DbUtil o)
    {
    	super.initObj(o);

    	this.setIsLocked( o.getNextStr() );
		//
		this.setReference( o.getNextStr() );
		this.setLevelName( o.getNextStr() );
		//
		this.setZLevel( o.getNextDbl() );		
		
    }
    
    public void initEntity(CadEntity o)
    {
    	super.initObj(o, o.getCadRefEntityId());

    	CadLayerDef oLayer = o.getLayer();
    	CadLevel oLevel = o.getLevel();

    	this.strIsLocked = StringUtil.fromBoolToStr( o.isLocked() );
    	//
    	this.reference = oLayer.getReference();
    	this.levelName = oLevel.getLevelLocalName();
    	//
    	this.zLevel = o.getZLevel();
    	
    }
    	
	/* TO_CADxxx */

	public abstract CadObject toCadObject(CadBlockDef oBlkDef);
    
	public CadObject toCadObject(CadBlockDef oBlkDef, Class c)
	{
		CadEntity o = null;
		
		try {
			CadDocumentDef doc = oBlkDef.getDocument();
		
			o = (CadEntity)c.newInstance();
			o.initEntity(this, doc, oBlkDef);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return o;		
	}

	/* SQL */
    
	@Override
	public abstract String getSqlTableName();

	@Override
	public abstract SqlColumnVO[] getSqlColumn();
	
	/* UTILITIES */
	
	public CadLayerDef getCadLayerDef(CadDocumentDef doc) {
		LayerTable tbl = doc.getLayerTable();
		
		CadLayerDef oLayer = tbl.getLayerDefByRef(this.getReference());
		if(oLayer == null)
			oLayer = doc.getDefaultLayerDef();
		return oLayer;
	}
	
	public CadLevel getCadLevel(CadDocumentDef doc) {
		LevelTable tbl = doc.getLevelTable();
		
		CadLevel oLevel = tbl.getLevel(this.getLevelName());
		if(oLevel == null)
			oLevel = doc.getDefaultLevel();
		return oLevel;
	}
	
    /* Getters/Setters */
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getIsLocked() {
		return strIsLocked;
	}

	public void setIsLocked(String strIsLocked) {
		this.strIsLocked = strIsLocked;
	}

	public double getZLevel() {
		return zLevel;
	}

	public void setZLevel(double zLevel) {
		this.zLevel = zLevel;
	}
	
}
