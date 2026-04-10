/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import br.com.tlmv.aicadxapp.AppConfig;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.dao.nosql.NoSqlDao;
import br.com.tlmv.aicadxapp.dao.postgresql.PgDao;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.dao.sqlite.SqliteDao;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public abstract class BaseDao 
{
//Private Static
	private static long gSeqNum = AppDefs.SEQ_OID;
		
//Private
	private ProjectRepoVO projectRepo;
	
	private DatabaseConnectionVO dbConn;

	private Connection conn;
	
//Public
	
	/* Constructors */
	
	public BaseDao(ProjectRepoVO projectRepo, DatabaseConnectionVO dbConn)
	{
		this.projectRepo = projectRepo;
		this.dbConn = dbConn;
	}
	
	/* Methodes */
	
	public static BaseDao create(ProjectRepoVO projectRepo, String driverName, boolean bCreateIfNotExist) 
	{
		AppMain app = AppMain.getApp();
		AppConfig cfg = app.getConfig();  
		
		BaseDao dao = null;

		System.out.println("Database: " + driverName);

		if( AppDefs.DEF_DATABASE_DRIVER_NOSQL.equals(driverName) ) {
			String strUrl = String.format(AppDefs.DEF_DBCONN_NOSQL_URL, projectRepo.getNosqlFullFileNameMask()); 

			DatabaseConnectionVO dbConn = new DatabaseConnectionVO(	
				AppDefs.DEF_DBCONN_NOSQL_DATABASETYPE,
				AppDefs.DEF_DBCONN_NOSQL_DSNAME,
				AppDefs.DEF_DBCONN_NOSQL_DRIVER,
				strUrl,
				AppDefs.DEF_DBCONN_NOSQL_USER,
				AppDefs.DEF_DBCONN_NOSQL_PWD );			
			dao = new NoSqlDao(projectRepo, dbConn);
		}
		else if( AppDefs.DEF_DATABASE_DRIVER_SQLIGHT.equals(driverName) ) {
			String strUrl = String.format(AppDefs.DEF_DBCONN_SQLITE_URL, projectRepo.getSqliteFullFileName()); 

			DatabaseConnectionVO dbConn = new DatabaseConnectionVO(	
				AppDefs.DEF_DBCONN_SQLITE_DATABASETYPE,
				AppDefs.DEF_DBCONN_SQLITE_DSNAME,
				AppDefs.DEF_DBCONN_SQLITE_DRIVER,
				strUrl,
				AppDefs.DEF_DBCONN_SQLITE_USER,
				AppDefs.DEF_DBCONN_SQLITE_PWD );			
			dao = new SqliteDao(projectRepo, dbConn);
		}
		else if( AppDefs.DEF_DATABASE_DRIVER_POSTGRESQL.equals(driverName) ) {
			DatabaseConnectionVO dbConn = cfg.getDatabaseConnection();
			dao = new PgDao(projectRepo, dbConn);
		}
		return dao;
	}
	
	//OPEN/CLOSE
	//
	public abstract boolean open();
		
	public abstract boolean close();
	
	public abstract boolean isOpenned();
	
	//SCHEMA
	//
	public abstract boolean dropSchema(String schemaName);
	
	public abstract boolean createSchema(String schemaName);
	
	public abstract boolean initSchema(String schemaName);
	
	public abstract SchemaRecord selectSchemaByPk(String catalogName, String schemaName);
	
	public abstract ArrayList<SchemaRecord> selectAllSchema(String catalogName, String prefix);
	
	public abstract String selectLastObjVer(String schemaName);
	
	//CREATE: ENTITIES
	//
	public abstract BaseEntityDao createEntityDao_basicCadObjects(int entType);
	public abstract BaseEntityDao createEntityDao_3DCadObjects(int entType);
	public abstract BaseEntityDao createEntityDao_basicBimObjects(int entType);
	public abstract BaseEntityDao createEntityDao_arquiteturaBimObjects(int entType); 
	public abstract BaseEntityDao createEntityDao_drenagemBimObjects(int entType); 
	public abstract BaseEntityDao createEntityDao_eletricaBimObjects(int entType); 
	public abstract BaseEntityDao createEntityDao_gasBimObjects(int entType); 
	
	//CREATE: POINTS
	//
	public abstract BasePointDao createPtDao_basicCadObjects(int entType);
	public abstract BasePointDao createPtDao_basicBimObjects(int entType); 
	public abstract BasePointDao createPtDao_arquiteturaBimObjects(int entType); 
	public abstract BasePointDao createPtDao_drenagemBimObjects(int entType); 
	public abstract BasePointDao createPtDao_eletricaBimObjects(int entType); 

	//CREATE: OBJECT_DATA
	//
	public abstract BaseODataDao createODataDao_drenagemBimObjects(int entType); 
	public abstract BaseODataDao createODataDao_eletricaBimObjects(int entType);
	
	//CREATE: OBJECT_DATA
	//
	public abstract BaseObjDefDao createObjDefDao_allObjDef(int entType);
	
	//CREATE
	//
	public BaseEntityDao create(int entType) {
		BaseEntityDao entDao = null;
		
		entDao = this.createEntityDao_basicCadObjects(entType);
		if(entDao != null) return entDao;
		
		entDao = this.createEntityDao_3DCadObjects(entType);
		if(entDao != null) return entDao;

		entDao = this.createEntityDao_basicBimObjects(entType);
		if(entDao != null) return entDao;

		entDao = this.createEntityDao_arquiteturaBimObjects(entType);
		if(entDao != null) return entDao;

		entDao = this.createEntityDao_drenagemBimObjects(entType);
		if(entDao != null) return entDao;

		entDao = this.createEntityDao_eletricaBimObjects(entType);
		if(entDao != null) return entDao;

		entDao = this.createEntityDao_gasBimObjects(entType);		
		return entDao;
	}

	public BasePointDao createPtDao(int entType) 
	{
		BasePointDao ptDao = null;
		
		ptDao = this.createPtDao_basicCadObjects(entType); 
		if(ptDao != null) return ptDao;

		ptDao = this.createPtDao_basicBimObjects(entType);
		if(ptDao != null) return ptDao;

		ptDao = this.createPtDao_arquiteturaBimObjects(entType); 
		if(ptDao != null) return ptDao;

		ptDao = this.createPtDao_drenagemBimObjects(entType);
		if(ptDao != null) return ptDao;

		ptDao = this.createPtDao_eletricaBimObjects(entType);
		return ptDao;
	}
	
	public BaseODataDao createODataDao(int entType)
	{
		BaseODataDao odataDao = null;
		
		odataDao = this.createODataDao_drenagemBimObjects(entType); 
		if(odataDao != null) return odataDao;

		odataDao = this.createODataDao_eletricaBimObjects(entType); 
		return odataDao;
	}
	
	public BaseObjDefDao createObjDefDao(int entType)
	{
		BaseObjDefDao objDefDao = null;
		
		objDefDao = this.createObjDefDao_allObjDef(entType); 
		return objDefDao;
	}
	
	//SEQUENCE
	//
	public static long nextVal()
	{
		BaseDao.gSeqNum += 1;
		return BaseDao.gSeqNum;
	}

	public static long currVal()
	{
		return BaseDao.gSeqNum;
	}

	/* DATA_ACCESS_OBJECT (DAO) - CACHE */
	
	public abstract Hashtable getMapDao();
	
	public abstract boolean persistData(String objVer, BaseEntityDao entDao);
	
	public abstract boolean persistData(String objVer, BasePointDao entDao);
	
	public abstract boolean persistData(String objVer, BaseODataDao entDao);

	public abstract boolean persistData(String objVer, BaseObjDefDao entDao);

	public abstract boolean persistAllData(String objVer);
	
	public abstract boolean retrieveAllData(String objVer);
	
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

	public ProjectRepoVO getProjectRepo() {
		return projectRepo;
	}

	public void setProjectRepo(ProjectRepoVO projectRepo) {
		this.projectRepo = projectRepo;
	}
	
}
