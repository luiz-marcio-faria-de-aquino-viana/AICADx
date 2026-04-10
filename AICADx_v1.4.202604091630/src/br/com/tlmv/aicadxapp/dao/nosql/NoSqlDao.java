/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * NoSqlDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/12/2025
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
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseObjDefDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadJanelaNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadPDuplaNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadParedeNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadParedePointNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadPisoNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadPisoPointNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadPontoArquiteturaNoSqlDao;
import br.com.tlmv.aicadxmod.arquitetura.dao.nosql.CadPortaNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadAlinhamentoEstacaDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadAlinhamentoEstacaPointDrenagemODataNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadAnotacaoCaixaInspecaoDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadAreaContribuicaoDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadCaixaInspecaoDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadEixoDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadMemoriaCalculoDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadMemoriaCalculoItemDrenagemODataNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadPerfilDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadPerfilItemDrenagemODataNoSqlDao;
import br.com.tlmv.aicadxmod.drenagem.dao.nosql.CadPontoDrenagemNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadCircuitoQuadroCargasEletricaODataNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadDiagramaUnifilarEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadEletroduto3DEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadEletroduto3DPointEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadEletrodutoEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadFioEletricoEletricaODataNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadImportaFiacaoEletrodutoEletricaODataNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadParamEletricoODataNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadPontoEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadQuadroCargasEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.eletrica.dao.nosql.CadTabelaFiacaoEletricaNoSqlDao;
import br.com.tlmv.aicadxmod.gas.dao.nosql.CadPontoGasNoSqlDao;

public class NoSqlDao extends BaseDao 
{
//Private
	private Hashtable mapDao = null;
	private boolean bOpenned = false;

//Public
	
	/* Constructors */
	
	public NoSqlDao(ProjectRepoVO projectRepo, DatabaseConnectionVO dbConn)
	{
		super(projectRepo, dbConn);
		
		this.mapDao = new Hashtable();
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

		//TODO:
		
		return result;
	}
	
	@Override
	public boolean createSchema(String schemaName) {
		boolean result = false;

		//TODO:
		
		return result;
	}
	
	@Override
	public boolean initSchema(String schemaName) {
		boolean result = false;

		//TODO:
		
		return result;
	}

	@Override
	public SchemaRecord selectSchemaByPk(String catalogName, String schemaName) {
		SchemaRecord result = null;

		//TODO:
		
		return result;
	}
	
	@Override
	public ArrayList<SchemaRecord> selectAllSchema(String catalogName, String prefix) {
		ArrayList<SchemaRecord> lsResult = new ArrayList<SchemaRecord>();

		//TODO:
		
		return lsResult;
	}
	
	@Override
	public String selectLastObjVer(String schemaName)
	{
		String strResult = AppDefs.NULL_INTSTR;

		//TODO:
		
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
			entDao = new CadArcNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CIRCLE) {
			entDao = new CadCircleNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ELLIPSE) {
			entDao = new CadEllipseNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_LINE) {
			entDao = new CadLineNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POINT) {
			entDao = new CadPointNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYGON) {
			entDao = new CadPolygonNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYLINE) {
			entDao = new CadPolylineNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_RECTANGLE) {
			entDao = new CadRectangleNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TEXT) {
			entDao = new CadTextNoSqlDao(this);
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
			entDao = new CadBox3dNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CILINDER3D) {
			entDao = new CadCilinder3dNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CONE3D) {
			entDao = new CadCone3dNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TORUS3D) {
			entDao = new CadTorus3dNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_TRONCOCONE3D) {
			entDao = new CadTroncoCone3dNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_SPHERE3D) {
			entDao = new CadSphere3dNoSqlDao(this);
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
			entDao = new CadLevelNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREA) {
			entDao = new CadAreaNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMAREATABLE) {
			entDao = new CadAreaTableNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_BIMPIPE) {
			entDao = new CadPipeNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODARQINSEREPONTO) {
			entDao = new CadPontoArquiteturaNoSqlDao(this);
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
			entDao = new CadPisoNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPAREDE) {
			entDao = new CadParedeNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPORTA) {
			entDao = new CadPortaNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPDUPLA) {
			entDao = new CadPDuplaNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMJANELA) {
			entDao = new CadJanelaNoSqlDao(this);
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
			entDao = new CadAlinhamentoEstacaDrenagemNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO) {
			entDao = new CadAreaContribuicaoDrenagemNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRANOTACAOCAIXAINSPECAO) {
			entDao = new CadAnotacaoCaixaInspecaoDrenagemNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
			entDao = new CadCaixaInspecaoDrenagemNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDREIXODRENAGEM) {
			entDao = new CadEixoDrenagemNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRMEMORIACALCULO) {
			entDao = new CadMemoriaCalculoDrenagemNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODDRPERFILDRENAGEM) {
			entDao = new CadPerfilDrenagemNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODDRPONTODRENAGEM) {
			entDao = new CadPontoDrenagemNoSqlDao(this);
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
			entDao = new CadPontoEletricaNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO) {
			entDao = new CadEletrodutoEletricaNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
			entDao = new CadEletroduto3DEletricaNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_MODELQUADROCARGAS) {
			entDao = new CadQuadroCargasEletricaNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODELDIAGRAMAUNIFILAR) {
			entDao = new CadDiagramaUnifilarEletricaNoSqlDao(this);
		}		
		else if(entType == AppDefs.OBJTYPE_MODELTABELAFIACAO) {
			entDao = new CadTabelaFiacaoEletricaNoSqlDao(this);
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
			entDao = new CadPontoGasNoSqlDao(this);
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
			ptDao = new CadPolygonPointNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_POLYLINE_GEOMPOINT) {
			ptDao = new CadPolylinePointNoSqlDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: BASIC_BIM_OBJECTS
	//
	public BasePointDao createPtDao_basicBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_AREA_GEOMPOINT) {
			ptDao = new CadAreaPointNoSqlDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: ARQUITETURA_BIM_OBJECTS
	//
	public BasePointDao createPtDao_arquiteturaBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_BIMPISO_GEOMPOINT) {
			ptDao = new CadPisoPointNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_BIMPAREDE_GEOMPOINT) {
			ptDao = new CadParedePointNoSqlDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: DRENAGEM_BIM_OBJECTS
	//
	public BasePointDao createPtDao_drenagemBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_AREACONTRIBUICAO_GEOMPOINT) {
			ptDao = new CadAreaPointNoSqlDao(this);
		}
		return ptDao;
	}
	
	//CREATE_POINT: ELETRICA_BIM_OBJECTS
	//
	public BasePointDao createPtDao_eletricaBimObjects(int entType) 
	{
		BasePointDao ptDao = null;
		
		if(entType == AppDefs.OBJTYPE_ELETRODUTO3D_GEOMPOINT) {
			ptDao = new CadEletroduto3DPointEletricaNoSqlDao(this);
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
			odataDao = new CadMemoriaCalculoItemDrenagemODataNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA) {
			odataDao = new CadPerfilItemDrenagemODataNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA) {
			odataDao = new CadAlinhamentoEstacaPointDrenagemODataNoSqlDao(this);
		}
		return odataDao;
	}
	
	//CREATE_ODATA: ELETRICA_BIM_OBJECTS
	//
	public BaseODataDao createODataDao_eletricaBimObjects(int entType) 
	{
		BaseODataDao odataDao = null;
		
		if(entType == AppDefs.OBJTYPE_PARAMELETRICO_ODATA) {
			odataDao = new CadParamEletricoODataNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA) {
			odataDao = new CadImportaFiacaoEletrodutoEletricaODataNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA) {
			odataDao = new CadFioEletricoEletricaODataNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_CIRCQDRCARGASELETRICO_ODATA) {
			odataDao = new CadCircuitoQuadroCargasEletricaODataNoSqlDao(this);
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
			objDefDao = new CadBlockDefNoSqlDao(this);
		}
		else if(entType == AppDefs.OBJTYPE_IMAGE_DEF) {
			objDefDao = new CadImageDefNoSqlDao(this);
		}
		return objDefDao;
	}

	//CREATE
	//
	@Override
	public BaseEntityDao create(int entType) {
		BaseEntityDao entDao = null;
		
		if( this.mapDao.containsKey(entType) ) {
			entDao = (BaseEntityDao)this.mapDao.get(entType);
			return entDao;
		}
		else {
			entDao = super.create(entType);
			if(entDao != null) {
				this.mapDao.put(entType, entDao);
			}
		}
		return entDao;
	}

	@Override
	public BasePointDao createPtDao(int entType) 
	{
		BasePointDao ptDao = null;
		
		if( this.mapDao.containsKey(entType) ) {
			ptDao = (BasePointDao)this.mapDao.get(entType);
			return ptDao;
		}		
		else {
			ptDao = super.createPtDao(entType);
			if(ptDao != null) {
				this.mapDao.put(entType, ptDao);
			}
		}
		return ptDao;
	}
	
	@Override
	public BaseODataDao createODataDao(int entType)
	{
		BaseODataDao odataDao = null;
		
		if( this.mapDao.containsKey(entType) ) {
			odataDao = (BaseODataDao)this.mapDao.get(entType);
			return odataDao;
		}		
		else {
			odataDao = super.createODataDao(entType);
			if(odataDao != null) {
				this.mapDao.put(entType, odataDao);
			}
		}
		return odataDao;
	}
	
	@Override
	public BaseObjDefDao createObjDefDao(int entType)
	{
		BaseObjDefDao objDefDao = null;
		
		if( this.mapDao.containsKey(entType) ) {
			objDefDao = (BaseObjDefDao)this.mapDao.get(entType);
			return objDefDao;
		}		
		else {
			objDefDao = super.createObjDefDao(entType);
			if(objDefDao != null) {
				this.mapDao.put(entType, objDefDao);
			}
		}
		return objDefDao;
	}

	//PERSIST_DATA
	//
	@Override
	public boolean persistData(String objVer, BaseEntityDao entDao) {
		boolean bResult = entDao.saveData(objVer, this.getProjectRepo()); 
		return bResult;
	}
	
	@Override
	public boolean persistData(String objVer, BasePointDao ptDao) {
		boolean bResult = ptDao.saveData(objVer, this.getProjectRepo()); 
		return bResult;
	}
	
	@Override
	public boolean persistData(String objVer, BaseODataDao odataDao) {
		boolean bResult = odataDao.saveData(objVer, this.getProjectRepo()); 
		return bResult;
	}

	@Override
	public boolean persistData(String objVer, BaseObjDefDao defDao) {
		boolean bResult = defDao.saveData(objVer, this.getProjectRepo()); 
		return bResult;
	}
	
	@Override
	public boolean persistAllData(String objVer) {
		Collection col = this.mapDao.values();
		Iterator iter = col.iterator();
		while( iter.hasNext() ) {
			Object dao = iter.next();
			
			if( BaseEntityDao.class.isInstance( dao )  ) {
				BaseEntityDao entDao = (BaseEntityDao)dao;
				if( !persistData(objVer, entDao) )
					return false;
			}
			else if( BasePointDao.class.isInstance( dao ) ) {
				BasePointDao ptDao = (BasePointDao)dao;
				if( !persistData(objVer, ptDao) )
					return false;
			}
			else if( BaseODataDao.class.isInstance( dao ) ) {
				BaseODataDao odataDao = (BaseODataDao)dao;
				if( !persistData(objVer, odataDao) )
					return false;
			}
			
		}
		return true;
	}	
	
	@Override
	public boolean retrieveAllData(String objVer) {
		int sz = AppDefs.ARR_OBJTYPE_VAL.length;
		for(int i = 0; i < sz; i++) {
			int entType = AppDefs.ARR_OBJTYPE_VAL[i]; 
			if(entType < AppDefs.OBJTYPE_APPCADMAIN) continue;

			if( ( entType > AppDefs.OBJTYPE_ODATA ) && ( entType < AppDefs.OBJTYPE_ODATA_ENDSEQ ) ) {
				BaseODataDao dao = this.createODataDao(entType);
				if(dao == null) continue;
				
				dao.loadData(objVer, this.getProjectRepo());								
			}
			else if( ( entType > AppDefs.OBJTYPE_GEOMPOINT ) && ( entType < AppDefs.OBJTYPE_GEOMPOINT_ENDSEQ ) ) {
				BasePointDao dao = this.createPtDao(entType);
				if(dao == null) continue;
				
				dao.loadData(objVer, this.getProjectRepo());				
			}
			else {
				BaseEntityDao dao = this.create(entType);
				if(dao == null) continue;
				
				dao.loadData(objVer, this.getProjectRepo());
			}
		}
		return true;
	}
	
	/* Getters/Setters */
	
	@Override
	public Hashtable getMapDao() {
		return this.mapDao;
	}
			
}
