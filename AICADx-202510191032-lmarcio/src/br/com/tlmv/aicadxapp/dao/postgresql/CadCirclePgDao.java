/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadCirclePgDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCircleRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLayerDefRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLineRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPointRecord;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class CadCirclePgDao extends CadEntityBaseDao
{
//Public
	
	/* Constructors */
	
	public CadCirclePgDao(BaseDao dao)
	{
		super(dao);
	}
	
	/* Methodes */
	
	@Override
	public BaseObjectRecord selectByPk(String schemaName, Integer objectId)
	{
		BaseEntityRecord result = null;
		
		BaseDao dao = this.getDao();
		
		try {
			String sql = StringUtil.replace(CadCircleRecord.sqlSelectByPk, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, objectId);
			
			ResultSet rs = stmt.executeQuery();
			if( rs.next() )
				result = new CadCircleRecord(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public ArrayList<BaseObjectRecord> selectAll(String schemaName)
	{
		ArrayList<BaseObjectRecord> lsResult = new ArrayList<BaseObjectRecord>();
		
		BaseDao dao = this.getDao();
		
		try {
			String sql = StringUtil.replace(CadCircleRecord.sqlSelectAll, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				CadCircleRecord o = new CadCircleRecord(rs);
				lsResult.add(o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}
	
	@Override
	public ArrayList<BaseObjectRecord> selectByRefEntityId(String schemaName, String refEntity) 
	{
		ArrayList<BaseObjectRecord> lsResult = new ArrayList<BaseObjectRecord>();
		
		BaseDao dao = this.getDao();
					
		try {
			String sql = StringUtil.replace(CadCircleRecord.sqlSelectByReference, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, refEntity);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				CadCircleRecord o = new CadCircleRecord(rs);
				lsResult.add(o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}

	@Override
	public Integer insert(String schemaName, BaseObjectRecord o) {
		CadCircleRecord oEnt = (CadCircleRecord)o;		
		
		BaseDao dao = this.getDao();
		Integer result = -1;
		
		try {
			String sql = StringUtil.replace(CadCircleRecord.sqlInsert, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
			
			Connection conn = this.getDao().getConn();
			
			Date dataAtual = new Date();
			
			int n = 1;

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(n++, oEnt.getObjectId());
			stmt.setInt(n++, oEnt.getObjType());
			stmt.setString(n++, oEnt.getObjTypeStr());			
			stmt.setString(n++, oEnt.getReference());
			stmt.setDouble(n++, oEnt.getPtCenterX());
			stmt.setDouble(n++, oEnt.getPtCenterY());
			stmt.setDouble(n++, oEnt.getPtCenterZ());
			stmt.setDouble(n++, oEnt.getRadius());
			stmt.setString(n++, oEnt.getIsEntityObject());
			stmt.setString(n++, oEnt.getIsDeleted());
			stmt.executeUpdate();
			
			//result = this.currVal(conn);
			result = 0;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Integer update(String schemaName, BaseObjectRecord o) {
		CadCircleRecord oEnt = (CadCircleRecord)o;		
		
		BaseDao dao = this.getDao();
		Integer result = -1;
		
		try {
			String sql = StringUtil.replace(CadCircleRecord.sqlUpdate, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
			
			Connection conn = this.getDao().getConn();
			
			Date dataAtual = new Date();
			
			int n = 1;
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(n++, oEnt.getReference());
			stmt.setDouble(n++, oEnt.getPtCenterX());
			stmt.setDouble(n++, oEnt.getPtCenterY());
			stmt.setDouble(n++, oEnt.getPtCenterZ());
			stmt.setDouble(n++, oEnt.getRadius());
			stmt.setString(n++, oEnt.getIsDeleted());
			stmt.setLong(n++, oEnt.getObjectId());
			int nrows = stmt.executeUpdate();
			
			if(nrows > 0)
				result = oEnt.getObjectId();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Integer insertOrUpdate(String schemaName, BaseObjectRecord o) {
		CadCircleRecord oEnt = (CadCircleRecord)o;		
		
		Integer result = -1;

		try {
			BaseObjectRecord oEntRec = this.selectByPk(schemaName, oEnt.getObjectId());
			if(oEntRec == null) {
				result = this.insert(schemaName, o);
			}
			else {
				result = this.update(schemaName, o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return result;		
	}

}
