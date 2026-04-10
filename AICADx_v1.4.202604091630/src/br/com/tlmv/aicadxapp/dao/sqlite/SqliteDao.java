/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * SqliteDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/03/2025
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

package br.com.tlmv.aicadxapp.dao.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseObjDefDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.postgresql.CadAreaPointPgDao;
import br.com.tlmv.aicadxapp.dao.postgresql.CadPolygonPointPgDao;
import br.com.tlmv.aicadxapp.dao.postgresql.CadPolylinePointPgDao;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadParedePointPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.postgresql.CadPisoPointPgDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.sqlite.CadJanelaSqliteDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.sqlite.CadPDuplaSqliteDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.sqlite.CadParedeSqliteDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.sqlite.CadPisoSqliteDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.sqlite.CadPontoArquiteturaSqliteDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.sqlite.CadPortaSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadAlinhamentoEstacaPointDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadMemoriaCalculoItemDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.postgresql.CadPerfilItemDrenagemODataPgDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadAlinhamentoEstacaDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadAnotacaoCaixaInspecaoDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadAreaContribuicaoDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadCaixaInspecaoDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadEixoDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadMemoriaCalculoDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadPerfilDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.drenagem.dao.sqlite.CadPontoDrenagemSqliteDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadCircuitoQuadroCargasEletricaODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadEletroduto3DPointEletricaPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadFioEletricoEletricaODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadImportaFiacaoEletrodutoEletricaODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.postgresql.CadParamEletricoODataPgDao;
import br.com.tlmv.aicadxmod.eletrica.dao.sqlite.CadDiagramaUnifilarEletricaSqliteDao;
import br.com.tlmv.aicadxmod.eletrica.dao.sqlite.CadEletroduto3DEletricaSqliteDao;
import br.com.tlmv.aicadxmod.eletrica.dao.sqlite.CadEletrodutoEletricaSqliteDao;
import br.com.tlmv.aicadxmod.eletrica.dao.sqlite.CadPontoEletricaSqliteDao;
import br.com.tlmv.aicadxmod.eletrica.dao.sqlite.CadQuadroCargasEletricaSqliteDao;
import br.com.tlmv.aicadxmod.eletrica.dao.sqlite.CadTabelaFiacaoEletricaSqliteDao;
import br.com.tlmv.aicadxmod.gas.dao.postgresql.CadPontoGasPgDao;

public class SqliteDao extends BaseDao 
{
//Private
	private boolean bOpenned = false;
	
//Public
	
	/* Constructors */
	
	public SqliteDao(ProjectRepoVO projectRepo, DatabaseConnectionVO dbConn)
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
			
			Connection conn = DriverManager.getConnection( dbConn.getUrl() );
			this.setConn(conn);
			
			this.bOpenned = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return this.bOpenned;
	}
		
	@Override
	public boolean close() {
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
	public boolean dropSchema(String schemaName) {
		boolean result = false;
		
		String sql = StringUtil.replace(SchemaRecord.sqlDropSchema, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ));
		
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
	public boolean createSchema(String schemaName) {
		boolean result = false;
		
		String sql = StringUtil.replace(SchemaRecord.sqlCreateSchema, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ));

		String[] arr = StringUtil.split(sql, ';');
		
		try {
			Connection conn = this.getConn();

			for(String currSql : arr) {
				PreparedStatement stmt = conn.prepareStatement(currSql);
				stmt.executeUpdate();
			}
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@Override
	public boolean initSchema(String schemaName) {
		boolean result = false;
		
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();

		String templateDbFileModBase = ctx.getTemplateDbFileModBaseSqLite();
		
		String sqlTemplateDbModBase = FileUtil.readData(templateDbFileModBase, AppDefs.DEF_COMMENT_MARK);

		String sqlModBase = StringUtil.replace(sqlTemplateDbModBase, AppDefs.TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME, ( AppDefs.NULL_SCHEMA.equals(schemaName) ? "" : schemaName + "." ));
		
		String[] arr = StringUtil.split(sqlModBase, ';');
		
		try {
			Connection conn = this.getConn();
			
			for(String currSql : arr) {
				if( "".equals(currSql) ) continue;
				
				PreparedStatement stmtModBase = conn.prepareStatement(currSql);
				stmtModBase.execute();
			}			
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public SchemaRecord selectSchemaByPk(String catalogName, String schemaName) {
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
	public ArrayList<SchemaRecord> selectAllSchema(String catalogName, String prefix) {
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
		
		if(entType == AppDefs.OBJTYPE_ARC) {
			entDao = new CadArcSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CIRCLE) {
			entDao = new CadCircleSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ELLIPSE) {
			entDao = new CadEllipseSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_LINE) {
			entDao = new CadLineSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POINT) {
			entDao = new CadPointSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYGON) {
			entDao = new CadPolygonSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYLINE) {
			entDao = new CadPolylineSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_RECTANGLE) {
			entDao = new CadRectangleSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TEXT) {
			entDao = new CadTextSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_INSERTBLOCK) {
			entDao = new CadInsertBlockSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_INSERTIMAGE) {
			entDao = new CadInsertImageSqliteDao(this);
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
			entDao = new CadBox3dSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CILINDER3D) {
			entDao = new CadCilinder3dSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CONE3D) {
			entDao = new CadCone3dSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TORUS3D) {
			entDao = new CadTorus3dSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TRONCOCONE3D) {
			entDao = new CadTroncoCone3dSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_SPHERE3D) {
			entDao = new CadSphere3dSqliteDao(this);
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
			entDao = new CadLevelSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREA) {
			entDao = new CadAreaSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREATABLE) {
			entDao = new CadAreaTableSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_BIMPIPE) {
			entDao = new CadPipeSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODARQINSEREPONTO) {
			entDao = new CadPontoArquiteturaSqliteDao(this);
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
			entDao = new CadPisoSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPAREDE) {
			entDao = new CadParedeSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPORTA) {
			entDao = new CadPortaSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPDUPLA) {
			entDao = new CadPDuplaSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMJANELA) {
			entDao = new CadJanelaSqliteDao(this);
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
			entDao = new CadAlinhamentoEstacaDrenagemSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO) {
			entDao = new CadAreaContribuicaoDrenagemSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRANOTACAOCAIXAINSPECAO) {
			entDao = new CadAnotacaoCaixaInspecaoDrenagemSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
			entDao = new CadCaixaInspecaoDrenagemSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDREIXODRENAGEM) {
			entDao = new CadEixoDrenagemSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRMEMORIACALCULO) {
			entDao = new CadMemoriaCalculoDrenagemSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRPERFILDRENAGEM) {
			entDao = new CadPerfilDrenagemSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRPONTODRENAGEM) {
			entDao = new CadPontoDrenagemSqliteDao(this);
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
			entDao = new CadPontoEletricaSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO) {
			entDao = new CadEletrodutoEletricaSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
			entDao = new CadEletroduto3DEletricaSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELQUADROCARGAS) {
			entDao = new CadQuadroCargasEletricaSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODELDIAGRAMAUNIFILAR) {
			entDao = new CadDiagramaUnifilarEletricaSqliteDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODELTABELAFIACAO) {
			entDao = new CadTabelaFiacaoEletricaSqliteDao(this);
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
	
	/* CREATE - POINTS */
	
	//CREATE_POINT: BASIC_CAD_OBJECTS
	//
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
			objDefDao = new CadBlockDefSqliteDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_IMAGE_DEF) {
			objDefDao = new CadImageDefSqliteDao(this);
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
