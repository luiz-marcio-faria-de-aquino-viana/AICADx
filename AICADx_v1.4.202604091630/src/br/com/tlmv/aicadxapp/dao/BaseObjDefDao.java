/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseObjDefDao.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public abstract class BaseObjDefDao 
{
//Private
	private String tableName;
	private String tableFileName;
	private BaseDao dao;
	//
	private Hashtable mapDataTable = null;
	
//Public
	
	/* Constructors */
	
	public BaseObjDefDao(BaseDao dao)
	{
		this.init(
			AppDefs.OBJTYPE_NONE,
			dao );
	}
	
	public BaseObjDefDao(int objType, BaseDao dao)
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
		
	public abstract BaseObjectRecord selectByPk(String objVer, String schemaName, Integer objectId);
	
	public abstract ArrayList<BaseObjectRecord> selectAll(String objVer, String schemaName);

	public abstract Integer insert(String schemaName, BaseObjectRecord o);

	public abstract Integer update(String schemaName, BaseObjectRecord o);

	public abstract Integer insertOrUpdate(String schemaName, BaseObjectRecord o);
	
	/* DATA_TABLE */
	
	public ArrayList<BaseObjectRecord> listAllData()
	{
		ArrayList<BaseObjectRecord> lsObj = new ArrayList<BaseObjectRecord>();
	
		Collection<BaseObjectRecord> col = this.mapDataTable.values();
		for(BaseObjectRecord obj : col) {
			lsObj.add(obj);
		}
		return lsObj;
	}
	
	public BaseObjectRecord addData(BaseObjectRecord obj)
	{
		if( !this.mapDataTable.containsKey(obj.getObjectId()) ) {
			this.mapDataTable.put(obj.getObjectId(), obj);

			return obj;
		}
		return null;
	}
	
	public BaseObjectRecord updData(BaseObjectRecord obj)
	{
		if( this.mapDataTable.containsKey(obj.getObjectId()) ) {
			this.mapDataTable.remove(obj.getObjectId());
		}
		this.mapDataTable.put(obj.getObjectId(), obj);			
		return obj;
	}
	
	public BaseObjectRecord getData(int objectId)
	{
		if( !this.mapDataTable.containsKey(objectId) ) {
			BaseObjectRecord obj = (BaseObjectRecord)this.mapDataTable.get(objectId);

			return obj;
		}
		return null;
	}
	
	/* LOAD/SAVE */
	
	public boolean loadData(ProjectRepoVO projectRepo) {
		if( AppDefs.NULL_STR.equals(this.tableFileName) ) return false;
		
		boolean result = false;

		try {
			String fullFileName = String.format(
				projectRepo.getNosqlFullFileNameMask(),
				this.tableName);
			
			ArrayList<BaseObjectRecord> lsDataRecord = FileUtil.loadDataRecord(fullFileName);
			for(BaseObjectRecord obj : lsDataRecord) {
				this.addData(obj);
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
			
			ArrayList<BaseObjectRecord> lsDataRecord = this.listAllData();
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
