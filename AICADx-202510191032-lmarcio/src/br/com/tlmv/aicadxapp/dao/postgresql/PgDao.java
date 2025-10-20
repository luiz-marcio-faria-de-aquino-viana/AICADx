/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PgDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.dao.record.SequenceRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAlinhamentoEstacaDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAlinhamentoEstacaPointDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAnotacaoCaixaInspecaoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAreaContribuicaoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAreaContribuicaoPointDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadCaixaInspecaoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadMemoriaCalculoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadMemoriaCalculoItemDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPerfilDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPerfilItemDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPontoDrenagemPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadEletrodutoEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadParamEletricoODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadPontoEletricaPgDao;
import br.com.tlmv.aicadxmod.gas.dao.postgresql.CadPontoGasPgDao;

public class PgDao extends BaseDao
{
//Public
	
	/* Constructors */
	
	public PgDao(DatabaseConnectionVO dbConn)
	{
		super(dbConn);
	}
	
	/* Methodes */
	
	public boolean open()
	{
		try 
		{
			DatabaseConnectionVO dbConn = this.getDbConn();

			//System.out.println("User:" + dbConn.getUser());
			//System.out.println("Password:" + dbConn.getPwd());
			//System.out.println("URL:" + dbConn.getUrl());
			//System.out.println("\n");
			
			Properties props = new Properties();
			props.setProperty("user", dbConn.getUser());
			props.setProperty("password", dbConn.getPwd());
			props.setProperty("ssl", "false");
			this.setConn( DriverManager.getConnection(dbConn.getUrl(), props) );
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
		
	public boolean close()
	{
		try 
		{
			Connection conn = this.getConn();
			
			if(conn != null)
				conn.close();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//SCHEMA
	//
	
	@Override
	public boolean dropSchema(String schemaName)
	{
		boolean result = false;
		
		String sql = StringUtil.replace(SchemaRecord.sqlDropSchema, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@Override
	public boolean createSchema(String schemaName)
	{
		boolean result = false;
		
		String sql = StringUtil.replace(SchemaRecord.sqlCreateSchema, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@Override
	public boolean initSchema(String schemaName)
	{
		boolean result = false;
		
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();

		String templateDbFile = ctx.getTemplateDbFile();

		String sqlTemplateDb = FileUtil.readData(templateDbFile);
		
		String sql = StringUtil.replace(sqlTemplateDb, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			result = stmt.execute();
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public SchemaRecord selectSchemaByPk(String catalogName, String schemaName)
	{
		SchemaRecord result = null;
		
		String sql = SchemaRecord.sqlSelectByPk;
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, catalogName);
			stmt.setString(2, schemaName);
			
			ResultSet rs = stmt.executeQuery();
			if( rs.next() )
				result = new SchemaRecord(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@Override
	public ArrayList<SchemaRecord> selectAllSchema(String catalogName, String prefix)
	{
		ArrayList<SchemaRecord> lsResult = new ArrayList<SchemaRecord>();
		
		String sql = SchemaRecord.sqlSelectAll;
		
		try {
			String strSchemaPrefix = prefix + "%";
			
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, catalogName);
			stmt.setString(2, strSchemaPrefix);
			
			ResultSet rs = stmt.executeQuery();
			while( rs.next() ) {
				SchemaRecord o = new SchemaRecord(rs);
				lsResult.add(o);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return lsResult;
	}

	//CREATE
	//
	public CadEntityBaseDao create(int entType)
	{
		CadEntityBaseDao entDao = null;
		
		/* LOAD_ENTITIES */
		
		//CADENTITIES
		//
		if(entType == AppDefs.OBJTYPE_ARC) {
			entDao = new CadArcPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BOX3D) {
			entDao = new CadBox3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CILINDER3D) {
			entDao = new CadCilinder3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CIRCLE) {
			entDao = new CadCirclePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CONE3D) {
			entDao = new CadCone3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_LINE) {
			entDao = new CadLinePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPIPE) {
			entDao = new CadPipePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POINT) {
			entDao = new CadPointPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYGON) {
			entDao = new CadPolygonPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYLINE) {
			entDao = new CadPolylinePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_RECTANGLE) {
			entDao = new CadRectanglePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TEXT) {
			entDao = new CadTextPgDao(this);
		}
		//
		//CadEntityBaseDao cadShapeDao = this.dao.create(AppDefs.OBJTYPE_SHAPE);
		//ArrayList<BaseObjectRecord> lsShape = cadShapeDao.selectAll(schemaName);
		//doc.loadAll(lsShape);
		//
		else if(entType == AppDefs.OBJTYPE_TORUS3D) {
			entDao = new CadTorus3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TRONCOCONE3D) {
			entDao = new CadTroncoCone3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_SPHERE3D) {
			entDao = new CadSphere3dPgDao(this);
		}
		//
		//BIMOBJECTS - ARQUITETURA		
		else if(entType == AppDefs.OBJTYPE_BIMAREA) {
			entDao = new CadAreaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREATABLE) {
			entDao = new CadAreaTablePgDao(this);
		}
		//CadEntityBaseDao cadParedeDao = this.dao.create(AppDefs.OBJTYPE_BIMPAREDE);
		//ArrayList<BaseObjectRecord> lsParede = cadParedeDao.selectAll(schemaName);
		//doc.loadAll(lsParede);
		//
		//CadEntityBaseDao cadPortaDao = this.dao.create(AppDefs.OBJTYPE_BIMPORTA);
		//ArrayList<BaseObjectRecord> lsPorta = cadPortaDao.selectAll(schemaName);
		//doc.loadAll(lsPorta);
		//
		//CadEntityBaseDao cadPDuplaDao = this.dao.create(AppDefs.OBJTYPE_BIMPDUPLA);
		//ArrayList<BaseObjectRecord> lsPDupla = cadPDuplaDao.selectAll(schemaName);
		//doc.loadAll(lsPDupla);
		//
		//CadEntityBaseDao cadJanelaDao = this.dao.create(AppDefs.OBJTYPE_BIMJANELA);
		//ArrayList<BaseObjectRecord> lsJanela = cadJanelaDao.selectAll(schemaName);
		//doc.loadAll(lsJanela);
		//
		//CadEntityBaseDao cadPontoArquiteturaDao = this.dao.create(AppDefs.OBJTYPE_MODARQINSEREPONTO);
		//ArrayList<BaseObjectRecord> lsPontoArquitetura = cadPontoArquiteturaDao.selectAll(schemaName);
		//doc.loadAll(lsPontoArquitetura);
		//
		//BIMOBJECTS - AGUAS_PLUVIAIS		
		//CadEntityBaseDao cadCaixaInspecaoAPluvialDao = this.dao.create(AppDefs.OBJTYPE_MODAPCAIXAINSPECAO);
		//ArrayList<BaseObjectRecord> lsCaixaInspecaoAPluvial = cadCaixaInspecaoAPluvialDao.selectAll(schemaName);
		//doc.loadAll(lsCaixaInspecaoAPluvial);
		//
		//BIMOBJECTS - ESGOTO		
		//CadEntityBaseDao cadCaixaInspecaoEsgotoDao = this.dao.create(AppDefs.OBJTYPE_MODESANOTACAOCAIXAINSPECAO);
		//ArrayList<BaseObjectRecord> lsCaixaInspecaoEsgoto = cadCaixaInspecaoEsgotoDao.selectAll(schemaName);
		//doc.loadAll(lsCaixaInspecaoEsgoto);
		//
		//CadEntityBaseDao cadPontoEsgotoDao = this.dao.create(AppDefs.OBJTYPE_MODESGINSEREPONTO);
		//ArrayList<BaseObjectRecord> lsPontoEsgoto = cadPontoEsgotoDao.selectAll(schemaName);
		//doc.loadAll(lsPontoEsgoto);
		//
		//BIMOBJECTS - DRENAGEM		
		else if(entType == AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA) {
			entDao = new CadAlinhamentoEstacaDrenagemPgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO) {
			entDao = new CadAreaContribuicaoDrenagemPgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRANOTACAOCAIXAINSPECAO) {
			entDao = new CadAnotacaoCaixaInspecaoDrenagemPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
			entDao = new CadCaixaInspecaoDrenagemPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRPONTODRENAGEM) {
			entDao = new CadPontoDrenagemPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRMEMORIACALCULO) {
			entDao = new CadMemoriaCalculoDrenagemPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRPERFILDRENAGEM) {
			entDao = new CadPerfilDrenagemPgDao(this);
		}
		//BIMOBJECTS - ELETRICA		
		else if(entType == AppDefs.OBJTYPE_MODELINSEREPONTO) {
			entDao = new CadPontoEletricaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO) {
			entDao = new CadEletrodutoEletricaPgDao(this);
		}
		//BIMOBJECTS - GAS		
		else if(entType == AppDefs.OBJTYPE_MODGINSEREPONTO) {
			entDao = new CadPontoGasPgDao(this);
		}
		return entDao;
	}

	@Override
	public BasePointDao createPtDao(int entType) 
	{
		BasePointDao ptDao = null;
		
		/* LOAD_ENTITIES */
		
		//CADENTITIES
		//
		if(entType == AppDefs.OBJTYPE_AREACONTRIBUICAO_GEOMPOINT) {
			ptDao = new CadAreaContribuicaoPointDrenagemPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_AREA_GEOMPOINT) {
			ptDao = new CadAreaPointPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYGON_GEOMPOINT) {
			ptDao = new CadPolygonPointPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYLINE_GEOMPOINT) {
			ptDao = new CadPolylinePointPgDao(this);
		}
		return ptDao;
	}
	
	@Override
	public BaseODataDao createODataDao(int entType)
	{
		BaseODataDao odataDao = null;
		
		/* LOAD_ENTITIES */
		
		//CADENTITIES
		//
		if(entType == AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA) {
			odataDao = new CadMemoriaCalculoItemDrenagemODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA) {
			odataDao = new CadPerfilItemDrenagemODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA) {
			odataDao = new CadAlinhamentoEstacaPointDrenagemODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_PARAMELETRICO_ODATA) {
			odataDao = new CadParamEletricoODataPgDao(this);
		}
		
		return odataDao;
	}

	//SEQUENCE
	//

	@Override
	public SequenceRecord nextVal(String schemaName, String seqName)
	{
		SequenceRecord result = null;
		
		String sql = StringUtil.replace(SequenceRecord.sqlNextVal, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
		sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SEQUENCE_NAME, seqName);
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if( rs.next() )
				result = new SequenceRecord(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public SequenceRecord currVal(String schemaName, String seqName)
	{
		SequenceRecord result = null;
		
		String sql = StringUtil.replace(SequenceRecord.sqlCurrVal, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
		sql = StringUtil.replace(sql, AppDefs.TEMPLATEDBFILE_TAGPARM_SEQUENCE_NAME, seqName);
			
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery();
			if( rs.next() )
				result = new SequenceRecord(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
			
}
