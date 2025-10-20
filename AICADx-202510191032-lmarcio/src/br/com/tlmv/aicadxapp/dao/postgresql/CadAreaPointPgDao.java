/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAreaPgDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
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
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadAreaPointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadAreaRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLayerDefRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPointRecord;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAnotacaoCaixaInspecaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoPointDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadCaixaInspecaoDrenagemRecord;

public class CadAreaPointPgDao extends BasePointDao
{
//Public
	
	/* Constructors */
	
	public CadAreaPointPgDao(BaseDao dao)
	{
		super(dao);
	}
	
	/* Methodes */
	
	@Override
	public BasePointRecord selectByPk(String schemaName, long oid)
	{
		CadAreaPointRecord result = null;
		
		BaseDao dao = this.getDao();
		
		try {
			String sql = StringUtil.replace(CadAreaRecord.sqlSelectByPk, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, oid);
			
			ResultSet rs = stmt.executeQuery();
			if( rs.next() )
				result = new CadAreaPointRecord(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public ArrayList<BasePointRecord> selectAll(String schemaName)
	{
		ArrayList<BasePointRecord> lsResult = new ArrayList<BasePointRecord>();
		
		BaseDao dao = this.getDao();
		
		try {
			String sql = StringUtil.replace(CadAreaPointRecord.sqlSelectAll, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				CadAreaPointRecord o = new CadAreaPointRecord(rs);
				lsResult.add(o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}
	
	@Override
	public ArrayList<BasePointRecord> selectByRefEntityId(String schemaName, int refEntityId) 
	{
		ArrayList<BasePointRecord> lsResult = new ArrayList<BasePointRecord>();
		
		BaseDao dao = this.getDao();
					
		try {
			String sql = StringUtil.replace(CadAreaPointRecord.sqlSelectByRefEntityId, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);

			Connection conn = this.getDao().getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, refEntityId);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				CadAreaPointRecord o = new CadAreaPointRecord(rs);
				lsResult.add(o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}

	@Override
	public Integer insert(String schemaName, BasePointRecord o) {
		CadAreaPointRecord oEnt = (CadAreaPointRecord)o;		
		
		BaseDao dao = this.getDao();
		Integer result = -1;
		
		try {
			String sql = StringUtil.replace(CadAreaPointRecord.sqlInsert, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
			
			Connection conn = this.getDao().getConn();
			
			Date dataAtual = new Date();
			
			int n = 1;

			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(n++, oEnt.getCadRefEntityId());
			stmt.setDouble(n++, oEnt.getPtX());
			stmt.setDouble(n++, oEnt.getPtY());
			stmt.setDouble(n++, oEnt.getPtZ());
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
	public Integer update(String schemaName, BasePointRecord o) {
		CadAreaPointRecord oEnt = (CadAreaPointRecord)o;		
		
		BaseDao dao = this.getDao();
		Integer result = -1;
		
		try {
			String sql = StringUtil.replace(CadAreaPointRecord.sqlUpdate, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
			
			Connection conn = this.getDao().getConn();
			
			Date dataAtual = new Date();
			
			int n = 1;
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setDouble(n++, oEnt.getPtX());
			stmt.setDouble(n++, oEnt.getPtY());
			stmt.setDouble(n++, oEnt.getPtZ());
			stmt.setLong(n++, oEnt.getCadRefEntityId());
			int nrows = stmt.executeUpdate();
			
			if(nrows > 0)
				result = oEnt.getCadRefEntityId();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Integer insertOrUpdate(String schemaName, BasePointRecord o) {
		CadAreaPointRecord oEnt = (CadAreaPointRecord)o;		
		
		Integer result = -1;

		try {
			BasePointRecord oEntRec = this.selectByPk(schemaName, oEnt.getOid());
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
