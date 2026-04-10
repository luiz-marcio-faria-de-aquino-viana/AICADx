/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseLayerDefDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/06/2025
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
import br.com.tlmv.aicadxapp.dao.record.CadLayerDefRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public abstract class BaseLayerDefDao {
//Private
	private String tableName;
	private String tableFileName;
	private BaseDao dao;
	//
	private Hashtable mapDataTable = null;
	
//Public
	
	/* Constructors */
	
	public BaseLayerDefDao(BaseDao dao)
	{
		this.init(
			AppDefs.OBJTYPE_NONE,
			dao );
	}
	
	public BaseLayerDefDao(int objType, BaseDao dao)
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
		
	public abstract CadLayerDefRecord selectByPk(String objVer, String schemaName, Integer objectId);
	
	public abstract ArrayList<CadLayerDefRecord> selectAll(String objVer, String schemaName);

	public abstract Integer insert(
			int objectId,
			int objType,
			String isDeleted,
			String layerName,
			String reference,
			String colorVal,
			String ltypeName,
			double lineWeight );

	public abstract Integer update(
			int objectId,
			String isDeleted,
			String layerName,
			String reference,
			String colorVal,
			String ltypeName,
			double lineWeight );

	public abstract Integer insertOrUpdate(
			int objectId,
			int objType,
			String isDeleted,
			String layerName,
			String reference,
			String colorVal,
			String ltypeName,
			double lineWeight );
	
	/* DATA_TABLE */
	
	public ArrayList<CadLayerDefRecord> listAllData(String objVer)
	{
		ArrayList<CadLayerDefRecord> lsObj = new ArrayList<CadLayerDefRecord>();
	
		Collection<CadLayerDefRecord> col = this.mapDataTable.values();
		for(CadLayerDefRecord obj : col) {
			if( objVer.equals(obj.getObjVer()) ) {
				lsObj.add(obj);
			}
		}
		return lsObj;
	}
	
	public CadLayerDefRecord addData(CadLayerDefRecord obj)
	{
		String key = obj.getKey();
		
		if( !this.mapDataTable.containsKey(key) ) {
			this.mapDataTable.put(key, obj);
			return obj;
		}
		return null;
	}
	
	public CadLayerDefRecord updData(CadLayerDefRecord obj)
	{
		String key = obj.getKey();
		
		if( this.mapDataTable.containsKey(key) ) {
			this.mapDataTable.remove(key);
		}
		this.mapDataTable.put(key, obj);
		
		return obj;
	}
	
	public CadLayerDefRecord getData(String objVer, int objectId)
	{
		String key = DbUtil.toSqlKey(objVer, objVer);
		
		if( !this.mapDataTable.containsKey(key) ) {
			CadLayerDefRecord obj = (CadLayerDefRecord)this.mapDataTable.get(key);
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
			
			ArrayList<CadLayerDefRecord> lsDataRecord = FileUtil.loadLayerDefRecord(fullFileName);
			for(CadLayerDefRecord obj : lsDataRecord) {
				this.addData(obj);
			}
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean saveData(ProjectRepoVO projectRepo) {
		if( AppDefs.NULL_STR.equals(this.tableFileName) ) return false;
		
		boolean result = false;

		try {
			String fullFileName = String.format(
				projectRepo.getNosqlFullFileNameMask(),
				this.tableName);
			
			ArrayList<CadLayerDefRecord> lsDataRecord = this.listAllData(null);
			FileUtil.writeLayerDefRecord(fullFileName, lsDataRecord);
			
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
