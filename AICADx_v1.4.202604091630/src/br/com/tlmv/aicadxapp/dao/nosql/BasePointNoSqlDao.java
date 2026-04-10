/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BasePointNoSqlDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/12/2025
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

package br.com.tlmv.aicadxapp.dao.nosql;

import java.util.ArrayList;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;

public abstract class BasePointNoSqlDao extends BasePointDao
{
//Public
	
	/* Constructors */
	
	public BasePointNoSqlDao(int objType, BaseDao dao)
	{
		super(objType, dao);
	}
	
	/* Methodes */
	
	/* BASIC_OPERATIONS */
	
	@Override
	public BasePointRecord selectByPk(String objVer, String schemaName, String tableName, Long oid)
	{
		BasePointRecord result = (BasePointRecord)this.getData(objVer, oid);
		return result;
	}

	@Override
	public ArrayList<BasePointRecord> selectAll(String objVer, String schemaName, String tableName)
	{
		ArrayList<BasePointRecord> lsResult = this.listAllData(objVer);
		return lsResult;
	}
	
	@Override
	public ArrayList<BasePointRecord> selectByRefEntityId(String objVer, String schemaName, String tableName, String refEntityId) 
	{
		ArrayList<BasePointRecord> lsResult = new ArrayList<BasePointRecord>();

		ArrayList<BasePointRecord> lsAllData = this.listAllData(objVer);
		for(BasePointRecord o : lsAllData) {
			if( refEntityId == null ) {
				lsResult.add(o);				
			}
			else if( refEntityId.equals( o.getCadRefEntityId() ) ) {
				lsResult.add(o);
			}
		}
		return lsResult;		
	}
	
	@Override
	public Integer insert(String objVer, String schemaName, String tableName, BasePointRecord o) {
		int result = -1;

		o.setOid( BaseDao.nextVal() );
		o.setObjVer(objVer);
		
		if(this.addData(o) != null) {
			result = (int)o.getOid();
		}
		return result;
	}

	@Override
	public Integer update(String objVer, String schemaName, String tableName, BasePointRecord o) {
		int result = -1;

		o.setObjVer(objVer);

		if(this.updData(o) != null) {
			result = (int)o.getOid();
		}
		return result;
	}

	@Override
	public Integer insertOrUpdate(String objVer, String schemaName, String tableName, BasePointRecord o) {
		Integer result = -1;

		BasePointRecord oEntRec = this.selectByPk(objVer, schemaName, tableName, o.getOid());
		if(oEntRec == null) {
			result = this.insert(objVer, schemaName, tableName, o);
		}
		else {
			result = this.update(objVer, schemaName, tableName, o);
		}
		return result;		
	}
	
}
