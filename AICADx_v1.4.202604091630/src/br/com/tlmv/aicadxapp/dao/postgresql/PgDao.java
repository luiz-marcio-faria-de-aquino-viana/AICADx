/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.dao.postgresql;

import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseObjDefDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadJanelaPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadPDuplaPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadParedePgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadParedePointPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadPisoPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadPisoPointPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadPontoArquiteturaPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadPortaPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAlinhamentoEstacaDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAlinhamentoEstacaPointDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAnotacaoCaixaInspecaoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAreaContribuicaoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadCaixaInspecaoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadEixoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadMemoriaCalculoDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadMemoriaCalculoItemDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPerfilDrenagemPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPerfilItemDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPontoDrenagemPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadCircuitoQuadroCargasEletricaODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadDiagramaUnifilarEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadEletroduto3DEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadEletroduto3DPointEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadEletrodutoEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadFioEletricoEletricaODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadImportaFiacaoEletrodutoEletricaODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadParamEletricoODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadPontoEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadQuadroCargasEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadTabelaFiacaoEletricaPgDao;
import br.com.tlmv.aicadxmod.gas.dao.postgresql.CadPontoGasPgDao;

public class PgDao extends BaseDao
{
//Private
	private boolean bOpenned = false;

//Public
	
	/* Constructors */
	
	public PgDao(ProjectRepoVO projectRepo, DatabaseConnectionVO dbConn)
	{
		super(projectRepo, dbConn);
	}
	
	/* Methodes */
	
	@Override
	public boolean open()
	{
		this.bOpenned = false;
		
		try 
		{
			DatabaseConnectionVO dbConn = this.getDbConn();

			System.out.println("URL:" + dbConn.getUrl());
			System.out.println("\n");
			
			Security.setProperty("crypto.policy", "unlimited");
			
			Properties props = new Properties();
			props.setProperty("user", dbConn.getUser());
			props.setProperty("password", dbConn.getPwd());
			props.setProperty("ssl", "false");
			this.setConn( DriverManager.getConnection(dbConn.getUrl(), props) );
			
			this.bOpenned = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return this.bOpenned;
	}
		
	@Override
	public boolean close()
	{
		try {
			Connection conn = this.getConn();
			
			if(conn != null)
				conn.close();
			this.bOpenned = false;

			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isOpenned()
	{
		return this.bOpenned;
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

		String templateDbFileModBase = ctx.getTemplateDbFileModBasePgSql();
		
		String sqlTemplateDbModBase = FileUtil.readData(templateDbFileModBase, AppDefs.DEF_COMMENT_MARK);

		String sqlModBase = StringUtil.replace(sqlTemplateDbModBase, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, schemaName);
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmtModBase = conn.prepareStatement(sqlModBase);
			stmtModBase.execute();

			result = true;
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
	
	@Override
	public String selectLastObjVer(String schemaName)
	{
		String strResult = AppDefs.NULL_INTSTR;
		
		String sql = StringUtil.replace(
			SchemaRecord.sqlLastObjVer, 
			AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, 
			( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ) 
		);
		
		try {
			Connection conn = this.getConn();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if( rs.next() ) {
				strResult = rs.getString(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return strResult;
	}
	
	/* CREATE - ENTITIES */

	//CREATE: BASIC_CAD_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_basicCadObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_PROJECT_DEF) {
			entDao = new CadProjectDefPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ARC) {
			entDao = new CadArcPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CIRCLE) {
			entDao = new CadCirclePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ELLIPSE) {
			entDao = new CadEllipsePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_LINE) {
			entDao = new CadLinePgDao(this);
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
		else if(entType == AppDefs.OBJTYPE_SHAPE) {
			entDao = new CadShapePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TEXT) {
			entDao = new CadTextPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_INSERTBLOCK) {
			entDao = new CadInsertBlockPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_INSERTIMAGE) {
			entDao = new CadInsertImagePgDao(this);
		}
		return entDao;
	}

	//CREATE: 3D_CAD_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_3DCadObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_BOX3D) {
			entDao = new CadBox3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CILINDER3D) {
			entDao = new CadCilinder3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CONE3D) {
			entDao = new CadCone3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TORUS3D) {
			entDao = new CadTorus3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TRONCOCONE3D) {
			entDao = new CadTroncoCone3dPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_SPHERE3D) {
			entDao = new CadSphere3dPgDao(this);
		}
		return entDao;
	}
		
	//CREATE: BASIC_BIM_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_basicBimObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_BIMLEVEL) {
			entDao = new CadLevelPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREA) {
			entDao = new CadAreaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREATABLE) {
			entDao = new CadAreaTablePgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_BIMPIPE) {
			entDao = new CadPipePgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODARQINSEREPONTO) {
			entDao = new CadPontoArquiteturaPgDao(this);
		}		
		return entDao;
	}

	//CREATE: ARQUITETURA_BIM_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_arquiteturaBimObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_BIMPISO) {
			entDao = new CadPisoPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPAREDE) {
			entDao = new CadParedePgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPORTA) {
			entDao = new CadPortaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPDUPLA) {
			entDao = new CadPDuplaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMJANELA) {
			entDao = new CadJanelaPgDao(this);
		}
		return entDao;
	}
	
	//CREATE: DRENAGEM_BIM_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_drenagemBimObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA) {
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
		else if(entType == AppDefs.OBJTYPE_MODDREIXODRENAGEM) {
			entDao = new CadEixoDrenagemPgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRMEMORIACALCULO) {
			entDao = new CadMemoriaCalculoDrenagemPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRPERFILDRENAGEM) {
			entDao = new CadPerfilDrenagemPgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRPONTODRENAGEM) {
			entDao = new CadPontoDrenagemPgDao(this);
		}
		return entDao;
	}
	
	//CREATE: ELETRICA_BIM_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_eletricaBimObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_MODELINSEREPONTO) {
			entDao = new CadPontoEletricaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO) {
			entDao = new CadEletrodutoEletricaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
			entDao = new CadEletroduto3DEletricaPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELQUADROCARGAS) {
			entDao = new CadQuadroCargasEletricaPgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODELDIAGRAMAUNIFILAR) {
			entDao = new CadDiagramaUnifilarEletricaPgDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODELTABELAFIACAO) {
			entDao = new CadTabelaFiacaoEletricaPgDao(this);
		}		
		return entDao;
	}
	
	//CREATE: GAS_BIM_OBJECTS
	//
	@Override
	public BaseEntityDao createEntityDao_gasBimObjects(int entType) 
	{
		BaseEntityDao entDao = null;
		
		if(entType == AppDefs.OBJTYPE_MODGINSEREPONTO) {
			entDao = new CadPontoGasPgDao(this);
		}
		return entDao;
	}
	
	/* METHODES - POINTS */
	
	//CREATE_POINT: BASIC_CAD_OBJECTS
	//
	public BasePointDao createPtDao_basicCadObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_POLYGON_GEOMPOINT) {
			ptDao = new CadPolygonPointPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYLINE_GEOMPOINT) {
			ptDao = new CadPolylinePointPgDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: BASIC_BIM_OBJECTS
	//
	public BasePointDao createPtDao_basicBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_AREA_GEOMPOINT) {
			ptDao = new CadAreaPointPgDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: ARQUITETURA_BIM_OBJECTS
	//
	public BasePointDao createPtDao_arquiteturaBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_BIMPISO_GEOMPOINT) {
			ptDao = new CadPisoPointPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPAREDE_GEOMPOINT) {
			ptDao = new CadParedePointPgDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: DRENAGEM_BIM_OBJECTS
	//
	public BasePointDao createPtDao_drenagemBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_AREACONTRIBUICAO_GEOMPOINT) {
			ptDao = new CadAreaPointPgDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: ELETRICA_BIM_OBJECTS
	//
	public BasePointDao createPtDao_eletricaBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_ELETRODUTO3D_GEOMPOINT) {
			ptDao = new CadEletroduto3DPointEletricaPgDao(this);
		}
		return ptDao;
	}

	/* CREATE - OBJECT_DATA */
	
	//CREATE_ODATA: DRENAGEM_BIM_OBJECTS
	//
	public BaseODataDao createODataDao_drenagemBimObjects(int entType) 
	{
		BaseODataDao odataDao = null;
		
		if(entType == AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA) {
			odataDao = new CadMemoriaCalculoItemDrenagemODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA) {
			odataDao = new CadPerfilItemDrenagemODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA) {
			odataDao = new CadAlinhamentoEstacaPointDrenagemODataPgDao(this);
		}
		return odataDao;
	}
	
	//CREATE_ODATA: ELETRICA_BIM_OBJECTS
	//
	public BaseODataDao createODataDao_eletricaBimObjects(int entType) 
	{
		BaseODataDao odataDao = null;
		
		if(entType == AppDefs.OBJTYPE_PARAMELETRICO_ODATA) {
			odataDao = new CadParamEletricoODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA) {
			odataDao = new CadImportaFiacaoEletrodutoEletricaODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA) {
			odataDao = new CadFioEletricoEletricaODataPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CIRCQDRCARGASELETRICO_ODATA) {
			odataDao = new CadCircuitoQuadroCargasEletricaODataPgDao(this);
		}
		return odataDao;
	}
	
	/* METHODES - OBJDEF */
	
	//CREATE_OBJDEF: ALL_OBJDEF
	//
	@Override
	public BaseObjDefDao createObjDefDao_allObjDef(int entType)
	{
		BaseObjDefDao objDefDao = null;
		
		if(entType == AppDefs.OBJTYPE_BLOCK_DEF) {
			objDefDao = new CadBlockDefPgDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_IMAGE_DEF) {
			objDefDao = new CadImageDefPgDao(this);
		}
		return objDefDao;
	}

	//PERSIST_DATA
	//
	@Override
	public boolean persistData(String objVer, BaseEntityDao entDao) {
		return false;
	}
	
	@Override
	public boolean persistData(String objVer, BasePointDao entDao) {
		return false;
	}
	
	@Override
	public boolean persistData(String objVer, BaseODataDao entDao) {
		return false;
	}
	
	@Override
	public boolean persistData(String objVer, BaseObjDefDao entDao) {
		return false;
	}
	
	@Override
	public boolean persistAllData(String objVer) {
		return false;
	}	
	
	@Override
	public boolean retrieveAllData(String objVer) {
		return false;
	}

	/* Getters/Setters */
	
	@Override
	public Hashtable getMapDao() {
		return null;
	}
			
}
