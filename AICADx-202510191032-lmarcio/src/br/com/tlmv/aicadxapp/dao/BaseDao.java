/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BaseDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppConfig;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.dao.postgresql.PgDao;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.dao.record.SequenceRecord;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;

public abstract class BaseDao 
{
//Private
	private DatabaseConnectionVO dbConn;

	private Connection conn;
	
//Public
	
	/* Constructors */
	
	public BaseDao(DatabaseConnectionVO dbConn)
	{
		this.dbConn = dbConn;
	}
	
	/* Methodes */
	
	public static BaseDao create() 
	{
		AppMain app = AppMain.getApp();
		AppConfig cfg = app.getConfig();  
		
		DatabaseConnectionVO dbConn = cfg.getDatabaseConnection();
		String driver = dbConn.getDriver();
		
		BaseDao dao = null;

		if( AppDefs.DEF_DATABASE_DRIVER_NOSQL.equals(driver) ) {
			//System.out.println("Database: NOSQL");
			
			//TODO:
		}
		else if( AppDefs.DEF_DATABASE_DRIVER_SQLIGHT.equals(driver) ) {
			//System.out.println("Database: SQLIGHT");
			
			//TODO:
		}
		else if( AppDefs.DEF_DATABASE_DRIVER_POSTGRESQL.equals(driver) ) {
			//System.out.println("Database: POSTGRESQL");
			
			dao = new PgDao(dbConn);
		}
		return dao;
	}
	
	//OPEN/CLOSE
	//
	public abstract boolean open();
		
	public abstract boolean close();
	
	//SCHEMA
	//
	public abstract boolean dropSchema(String schemaName);
	
	public abstract boolean createSchema(String schemaName);
	
	public abstract boolean initSchema(String schemaName);
	
	public abstract SchemaRecord selectSchemaByPk(String catalogName, String schemaName);
	
	public abstract ArrayList<SchemaRecord> selectAllSchema(String catalogName, String prefix);
	
	//CREATE
	//
	public abstract CadEntityBaseDao create(int entType);
	
	public abstract BasePointDao createPtDao(int entType);
	
	public abstract BaseODataDao createODataDao(int entType);
	
	//SEQUENCE
	//
	public abstract SequenceRecord nextVal(String schemaName, String seqName);

	public abstract SequenceRecord currVal(String schemaName, String seqName);
	
	/* Getters/Setters */

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public DatabaseConnectionVO getDbConn() {
		return dbConn;
	}

	public void setDbConn(DatabaseConnectionVO dbConn) {
		this.dbConn = dbConn;
	}
	
}
