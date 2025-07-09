/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SchemaRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;

public class SchemaRecord extends BaseObjectRecord
{
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
    	this.init(rs);
    }    
    
    /* Methodes */
    
    public void init(
		long oid,
		String catalogName,
		String schemaName)
    {
    	super.init(oid, AppDefs.NULL_INT, AppDefs.OBJTYPE_NONE, AppDefs.ARR_OBJTYPE_STR[0], AppDefs.DEF_VALUES_NAO, AppDefs.DEF_VALUES_NAO);

		this.catalogName = catalogName;
		this.schemaName = schemaName;
    }
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
		this.setCatalogName( o.getNextStr() );
		this.setSchemaName( o.getNextStr() );
    }

	@Override
	public String toString() {
		return this.schemaName;
	}
	
	/* TO_CADxxx */

	public CadObject toCadObject() {
	    return null;
	}

    /* Getters/Setters */

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
