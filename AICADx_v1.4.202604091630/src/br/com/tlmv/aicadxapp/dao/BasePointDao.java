/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BasePointDao.java
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

package br.com.tlmv.aicadxapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public abstract class BasePointDao 
{
//Public Static
	public static final String sqlCreate =
		"oid 				#SQLTYPE_INT# NOT NULL, " +
		//
		"cad_refentity_id 	#SQLTYPE_STR# NOT NULL, " +
		"obj_ver 			#SQLTYPE_STR# NOT NULL, " +
		//
	    "pt_x 				#SQLTYPE_DBL# NOT NULL, " +
	    "pt_y 				#SQLTYPE_DBL# NOT NULL, " +
	    "pt_z 				#SQLTYPE_DBL# NOT NULL ";

	public static final String sqlFields =
		"oid, " +
		//
		"cad_refentity_id, " +
		"obj_ver, " +
		//
	    "pt_x, " +
	    "pt_y, " +
	    "pt_z ";
	
	public static final String sqlUpdate =
		"oid = ?, " +
		//
		"cad_refentity_id = ?, " +
		"obj_ver = ?, " +
		//
	    "pt_x = ?, " +
	    "pt_y = ?, " +
	    "pt_z = ? ";
		
	public static final String sqlParams =
		 "?, ?, ?, ?, ?, ? ";
	
//Private
	private String tableName;
	private String tableFileName;

	private BaseDao dao;

	private Hashtable mapDataTable = null;
	
//Public
	
	/* Constructors */
	
	public BasePointDao(BaseDao dao)
	{
		this.init(
			AppDefs.OBJTYPE_NONE,
			dao );
	}
	
	public BasePointDao(int objType, BaseDao dao)
	{
		this.init(
			objType,
			dao );
	}
	
	/* Methodes */
	
	public void init(int objType, BaseDao dao) 
	{
		String objTypeStr = StringUtil.toLowerCase( CadUtil.getObjTypeStr(objType) );
		
		this.tableName = objTypeStr;
		this.tableFileName = AppDefs.DEF_PROJECTPREFIX_DEFAULT + this.tableName + AppDefs.DEF_PROJECTFILEEXT_DEFAULT;

		this.dao = dao;

		this.mapDataTable = new Hashtable();		
	}
		
	public BasePointRecord selectByPk(String objVer, String schemaName, String tableName, Long oid)
	{
		BasePointRecord result = null;
		
		try {
			String sql = String.format(BasePointRecord.BASE_POINT_SELECT_BYPK, tableName);
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
	
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int n = 1;
			
			stmt.setString(n++, objVer);
			stmt.setLong(n++, oid);
			
			ResultSet rs = stmt.executeQuery();
			if( rs.next() ) {
				result = new BasePointRecord();
				result.init(rs);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	public ArrayList<BasePointRecord> selectAll(String objVer, String schemaName, String tableName)
	{
		ArrayList<BasePointRecord> lsResult = new ArrayList<BasePointRecord>();
		
		try {
			String sql = String.format(BasePointRecord.BASE_POINT_SELECT_ALL, tableName);
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int n = 1;
			
			stmt.setString(n++, objVer);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				BasePointRecord oEnt = new BasePointRecord();
				oEnt.init(rs);

				lsResult.add(oEnt);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}
	
	public ArrayList<BasePointRecord> selectByRefEntityId(String objVer, String schemaName, String tableName, String refEntityId) 
	{
		ArrayList<BasePointRecord> lsResult = new ArrayList<BasePointRecord>();
		
		try {
			String sql = String.format(BasePointRecord.BASE_POINT_SELECT_BY_REFENTID, tableName);
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			int n = 1;
			
			stmt.setString(n++, objVer);
			stmt.setString(n++, refEntityId);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				BasePointRecord oEnt = new BasePointRecord();
				oEnt.init(rs);

				lsResult.add(oEnt);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}

	public Integer insert(String objVer, String schemaName, String tableName, BasePointRecord oEnt) 
	{
		Integer result = AppDefs.NULL_INT;
		
		try {
			String sql = String.format(BasePointRecord.BASE_POINT_INSERT, tableName);
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
			
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int n = 1;

			long oid = BaseDao.nextVal();
			stmt.setLong(n++, oid);
			//
			stmt.setString(n++, oEnt.getCadRefEntityId());
			stmt.setString(n++, oEnt.getObjVer());
			//
			stmt.setDouble(n++, oEnt.getPtX());
			stmt.setDouble(n++, oEnt.getPtY());
			stmt.setDouble(n++, oEnt.getPtZ());
			
			stmt.executeUpdate();
						
			result = (int)oid;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Integer update(String objVer, String schemaName, String tableName, BasePointRecord oEnt) 
	{
		Integer result = AppDefs.NULL_INT;
		
		try {
			String sql = String.format(BasePointRecord.BASE_POINT_INSERT, tableName);
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
			
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			int n = 1;
			
			stmt.setString(n++, oEnt.getCadRefEntityId());
			stmt.setString(n++, oEnt.getObjVer());
			//
			stmt.setDouble(n++, oEnt.getPtX());
			stmt.setDouble(n++, oEnt.getPtY());
			stmt.setDouble(n++, oEnt.getPtZ());
			
			stmt.setString(n++, oEnt.getObjVer());
			
			long oid = oEnt.getOid();
			stmt.setLong(n++, oid);
			
			result = (int)oid;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Integer insertOrUpdate(String objVer, String schemaName, String tableName, BasePointRecord oEnt)  
	{
		Integer result = AppDefs.NULL_INT;
		
		try {
			long oid = oEnt.getOid();
			
			BasePointRecord oEntRec = this.selectByPk(objVer, schemaName, tableName, oid);
			if(oEntRec == null) {
				result = this.insert(objVer, schemaName, tableName, oEnt);
			}
			else {
				result = this.update(objVer, schemaName, tableName, oEnt);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return result;		
	}
	
	public long nextSeq(String schemaName, String tableName)
	{
		long result = AppDefs.NULL_LNG;
		
		try {
			String sql = String.format(BasePointRecord.BASE_POINT_NEXT_SEQ, tableName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if( rs.next() ) {
				result = rs.getLong(0);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	public long currSeq(String schemaName, String tableName)
	{
		long result = AppDefs.NULL_LNG;

		try {
			String sql = String.format(BasePointRecord.BASE_POINT_CURR_SEQ, tableName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if( rs.next() ) {
				result = rs.getLong(0);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
		
	/* DATA_TABLE */
	
	public ArrayList<BasePointRecord> listAllData(String objVer)
	{
		ArrayList<BasePointRecord> lsObj = new ArrayList<BasePointRecord>();
	
		Collection<BasePointRecord> col = this.mapDataTable.values();
		for(BasePointRecord obj : col) {
			if( objVer == null ) {
				lsObj.add(obj);				
			}
			else if( objVer.equals( obj.getObjVer() ) ) {
				lsObj.add(obj);
			}
		}
		return lsObj;
	}
	
	public BasePointRecord addData(BasePointRecord obj)
	{
		String key = obj.getKey();
		
		if( !this.mapDataTable.containsKey(key) ) {
			this.mapDataTable.put(key, obj);
			return obj;
		}
		return null;
	}
	
	public BasePointRecord updData(BasePointRecord obj)
	{
		String key = obj.getKey();
		
		if( this.mapDataTable.containsKey(key) ) {
			this.mapDataTable.remove(key);
		}
		this.mapDataTable.put(key, obj);

		return obj;
	}
	
	public BasePointRecord getData(String objVer, long oid)
	{
		String key = DbUtil.toSqlKey(objVer, oid);
		
		if( !this.mapDataTable.containsKey(key) ) {
			BasePointRecord obj = (BasePointRecord)this.mapDataTable.get(key);
			return obj;
		}
		return null;
	}
	
	/* LOAD/SAVE */
	
	public boolean loadData(String objVer, ProjectRepoVO projectRepo) {
		if( AppDefs.NULL_STR.equals(this.tableFileName) ) return false;
		
		boolean result = false;

		try {
			String fullFileName = String.format(
				projectRepo.getNosqlFullFileNameMask(),
				this.tableName);
			
			ArrayList<BasePointRecord> lsDataRecord = FileUtil.loadPointRecord(fullFileName);
			for(BasePointRecord obj : lsDataRecord) {
				if( objVer.equals(obj.getObjVer()) ) {
					this.addData(obj);
				}
			}
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean saveData(String objVer, ProjectRepoVO projectRepo) {
		if( AppDefs.NULL_STR.equals(this.tableFileName) ) return false;
		
		boolean result = false;

		try {
			String fullFileName = String.format(
				projectRepo.getNosqlFullFileNameMask(),
				this.tableName);
			
			ArrayList<BasePointRecord> lsDataRecord = this.listAllData(null);
			FileUtil.writePointRecord(fullFileName, lsDataRecord);
			
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/* Getters/Setters */
	
	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableFileName() {
		return tableFileName;
	}

	public void setTableFileName(String tableFileName) {
		this.tableFileName = tableFileName;
	}

	public Hashtable getMapData() {
		return mapDataTable;
	}

	public void setMapData(Hashtable mapDataTable) {
		this.mapDataTable = mapDataTable;
	}

}
