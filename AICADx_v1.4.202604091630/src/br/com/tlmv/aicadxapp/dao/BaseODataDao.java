/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseODataDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/06/2025
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
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public abstract class BaseODataDao 
{
//Private
	private String tableName;
	private String tableFileName;
	private BaseDao dao;
	//
	private Hashtable mapDataTable = null;
	
//Public
	
	/* Constructors */
	
	public BaseODataDao(BaseDao dao)
	{
		this.init(
			AppDefs.OBJTYPE_NONE,
			dao );
	}
	
	public BaseODataDao(int objType, BaseDao dao)
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
		//
		this.mapDataTable = new Hashtable();		
	}
		
	public BaseObjectRecord selectByPk(String objVer, String schemaName, Integer objectId, BaseObjectRecord oRef)
	{
		BaseObjectRecord result = null;
		
		try {
			Class c = oRef.getClass();
			
			String sql = String.format(
				BaseObjectRecord.BASE_OBJECT_SELECT_BYPK, 
				oRef.getSqlFields(), 
				oRef.getSqlTableName() );
			
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
	
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			int n = 1;
			
			stmt.setString(n++, objVer);
			stmt.setInt(n++, objectId);
			
			ResultSet rs = stmt.executeQuery();
			if( rs.next() ) {
				DbUtil o = new DbUtil(rs);
				
				result = (BaseObjectRecord)c.newInstance();
				result.init(o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	public ArrayList<BaseObjectRecord> selectAll(String objVer, String schemaName, BaseObjectRecord oRef)
	{
		ArrayList<BaseObjectRecord> lsResult = new ArrayList<BaseObjectRecord>();
				
		BaseDao dao = this.getDao();
		try {
			Class c = oRef.getClass();
			
			String sql = String.format(
				BaseObjectRecord.BASE_OBJECT_SELECT_ALL, 
				oRef.getSqlFields(), 
				oRef.getSqlTableName() );
			
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
	
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			int n = 1;
			
			stmt.setString(n++, objVer);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				DbUtil o = new DbUtil(rs);
				
				BaseObjectRecord oEnt = (BaseObjectRecord)c.newInstance();
				oEnt.init(o);
	
				lsResult.add(oEnt);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}
	
	public ArrayList<BaseObjectRecord> selectByRefEntityId(String objVer, String schemaName, String refEntityId, BaseObjectRecord oRef)
	{
		ArrayList<BaseObjectRecord> lsResult = new ArrayList<BaseObjectRecord>();
		
		try {
			Class c = oRef.getClass();
			
			String sql = String.format(
				BaseObjectRecord.BASE_OBJECT_SELECT_BYREFID, 
				oRef.getSqlFields(), 
				oRef.getSqlTableName() );
			
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
			
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			int n = 1;
			
			stmt.setString(n++, objVer);
			stmt.setString(n++, refEntityId);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				DbUtil o = new DbUtil(rs);
				
				BaseObjectRecord oEnt = (BaseObjectRecord)c.newInstance();
				oEnt.init(o);
	
				lsResult.add(oEnt);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}
	
	public Integer insert(String objVer, String schemaName, BaseObjectRecord o, Object[] arrVal)
	{
		Integer result = AppDefs.NULL_INT;

		try {
			Class c = o.getClass();
			
			String tableParms = DbUtil.toSqlParms(arrVal);
			
			String sql = String.format(
				BaseObjectRecord.BASE_OBJECT_INSERT, 
				o.getSqlFields(), 
				o.getSqlTableName(),
				tableParms );
			
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );
			
			BaseObjectRecord oEnt = (BaseObjectRecord)o;	
			oEnt.setOid( BaseDao.nextVal() );
			oEnt.setObjVer(objVer);
			
			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int n = 1;
			
			//DATA
			//
			stmt.setLong(n++, oEnt.getOid());
			//
			stmt.setInt(n++, oEnt.getObjectId());
			stmt.setInt(n++, oEnt.getObjType());
			stmt.setString(n++, oEnt.getObjTypeStr());			
			stmt.setString(n++, oEnt.getObjVer());			
			//
			stmt.setString(n++, oEnt.getCadRefEntityId());
			//
			stmt.setString(n++, oEnt.getIsEntityObject());
			stmt.setString(n++, oEnt.getIsDeleted());
			//
			int szArrVal = arrVal.length;
			for(int i = 0; i < szArrVal; i++) {
				stmt.setObject(n++, arrVal[i]);				
			}
	
			stmt.executeUpdate();
			
			result = (int)oEnt.getOid();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Integer update(String objVer, String schemaName, BaseObjectRecord o, Object[] arrVal)
	{
		Integer result = AppDefs.NULL_INT;
		
		try {
			Class c = o.getClass();
			
			BaseObjectRecord oEnt = (BaseObjectRecord)o;		
			
			String sql = String.format(
				BaseObjectRecord.BASE_OBJECT_UPDATE, 
				o.getSqlTableName(), 
				o.getSqlUpdate() );
			
			sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) );

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int n = 1;
			
			//DATA
			//
			stmt.setString(n++, oEnt.getObjVer());
			//
			stmt.setString(n++, oEnt.getCadRefEntityId());
			//
			stmt.setString(n++, oEnt.getIsDeleted());
			//
			int szArrVal = arrVal.length;
			for(int i = 0; i < szArrVal; i++) {
				stmt.setObject(n++, arrVal[i]);				
			}
	
			//QUERYSTRING
			//
			stmt.setString(n++, oEnt.getObjVer());
			stmt.setInt(n++, oEnt.getObjectId());
	
			int nrows = stmt.executeUpdate();
			
			if(nrows > 0)
				result = (int)oEnt.getOid();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Integer insertOrUpdate(String objVer, String schemaName, BaseObjectRecord o, Object[] arrVal)
	{
		Integer result = AppDefs.NULL_INT;
	
		try {
			BaseObjectRecord oEnt = (BaseObjectRecord)o;		
			int objectId = oEnt.getObjectId();
			
			BaseObjectRecord oEntRec = this.selectByPk(objVer, schemaName, objectId, o);
			if(oEntRec == null) {
				result = this.insert(objVer, schemaName, o, arrVal);
			}
			else {
				result = this.update(objVer, schemaName, o, arrVal);
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
			String sql = String.format(BaseEntityRecord.BASE_ENTITY_NEXT_SEQ, tableName);

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
			String sql = String.format(BaseEntityRecord.BASE_ENTITY_CURR_SEQ, tableName);

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
	
	public ArrayList<BaseObjectRecord> listAllData(String objVer)
	{
		ArrayList<BaseObjectRecord> lsObj = new ArrayList<BaseObjectRecord>();
	
		Collection<BaseObjectRecord> col = this.mapDataTable.values();
		for(BaseObjectRecord obj : col) {
			if(objVer == null) {
				lsObj.add(obj);				
			}
			else if( objVer.equals( obj.getObjVer() ) ) {
				lsObj.add(obj);
			}
		}
		return lsObj;
	}
	
	public BaseObjectRecord addData(BaseObjectRecord obj)
	{
		String key = obj.getKey();				
		
		if( !this.mapDataTable.containsKey(key) ) {
			this.mapDataTable.put(key, obj);

			return obj;
		}
		return null;
	}
	
	public BaseObjectRecord updData(BaseObjectRecord obj)
	{
		String key = obj.getKey();				
		
		if( this.mapDataTable.containsKey(key) ) {
			this.mapDataTable.remove(key);
		}
		this.mapDataTable.put(key, obj);			
		return obj;
	}
	
	public BaseObjectRecord getData(String objVer, int objectId)
	{
		String key = DbUtil.toSqlKey(objVer, objectId);				
		
		if( !this.mapDataTable.containsKey(key) ) {
			BaseObjectRecord obj = (BaseObjectRecord)this.mapDataTable.get(key);

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
			
			ArrayList<BaseObjectRecord> lsDataRecord = FileUtil.loadDataRecord(fullFileName);
			for(BaseObjectRecord obj : lsDataRecord) {
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
			
			ArrayList<BaseObjectRecord> lsDataRecord = this.listAllData(null);
			FileUtil.writeDataRecord(fullFileName, lsDataRecord);
			
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
