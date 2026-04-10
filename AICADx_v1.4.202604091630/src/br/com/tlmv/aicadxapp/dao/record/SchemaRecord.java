/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * SchemaRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/06/2025
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
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class SchemaRecord extends BaseObjectRecord
{
//Public	
	
	@Override
	public String getSqlTableName() {
		return null;
	}

	@Override
	public SqlColumnVO[] getSqlColumn() {
		return null;
	}

//Public Static
	public static final String sqlDropSchema = 
		"DROP SCHEMA #SCHEMA_NAME# CASCADE ";

	public static final String sqlCreateSchema = 
		"CREATE SCHEMA #SCHEMA_NAME# ";
	
	public static final String sqlSelectByPk = 
		"SELECT " +
			"round(random() * 1000000 + 1) as oid, " +
			"catalog_name, " +
			"schema_name " +
		"FROM information_schema.schemata " +
		"WHERE catalog_name = ? " +
		  "AND schema_name = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
			"round(random() * 1000000 + 1) as oid, " +
			"catalog_name, " +
			"schema_name " +
		"FROM information_schema.schemata " +
		"WHERE catalog_name = ? " +
		  "and schema_name like ? ";
	
	public static final String sqlLastObjVer =
		"SELECt " +
			"MAX(obj_ver) AS ult_obj_ver " +
		"FROM #SCHEMA_NAME#cad_project_def ";
	
//Private
	private String catalogName;
	private String schemaName;
	
//Public
    
    public SchemaRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR);
    }

    public SchemaRecord(ResultSet rs)
    {
		DbUtil o = new DbUtil(rs);
		
    	this.init(o);
    }    
    
    /* Methodes */
    
    public void init(
		long oid,
		String catalogName,
		String schemaName)
    {
    	super.initObj(
			oid,
			//
    		AppDefs.NULL_INT, 
    		AppDefs.OBJTYPE_NONE, 
    		AppDefs.ARR_OBJTYPE_STR[0], 
    		AppDefs.NULL_STR, 
			//
    		AppDefs.NULL_INTSTR, 
			//
    		AppDefs.DEF_VALUES_NAO, 
    		AppDefs.DEF_VALUES_NAO );

		this.catalogName = catalogName;
		this.schemaName = schemaName;
    }
    
    @Override
    public void init(DbUtil o)
    {
		this.setOid( o.getNextLng() );
		this.setCatalogName( o.getNextStr() );
		this.setSchemaName( o.getNextStr() );
    }

	@Override
	public String toString() {
		return this.schemaName;
	}
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}

    /* Getters/Setters */
    
    public String getProjectName() {
    	String strResult = "";
    	
    	int szProjectPrefix = AppDefs.DEF_PROJECTPREFIX_DEFAULT.length();
    	
    	int szSchemaName = this.schemaName.length();
    	if(szSchemaName > szProjectPrefix) {
    		strResult = this.schemaName.substring(szProjectPrefix);
    	}
    	return strResult;
    }

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
    	
}
