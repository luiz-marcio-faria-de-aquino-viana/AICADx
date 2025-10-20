/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppDatabase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.CadArea;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.CadPolyline;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.ImageTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ObjectDataTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAlinhamentoEstacaDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAlinhamentoEstacaPointDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadMemoriaCalculoItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPerfilItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class AppDatabase 
{
//Private
	private BaseDao dao = null;
	
//Public

	public AppDatabase() {
		this.dao = BaseDao.create();
		
		if(this.dao != null) {
			boolean bOpen = this.dao.open();
			if( !bOpen ) {
				String errmsg = "ERR: Nao foipossivel abrir o banco de dados de documentos";
				AppError.showCmdError(errmsg, this.getClass());
			}
		}
	}

	/* Methodes */

	public ArrayList<SchemaRecord> listAll() 
	{
		DatabaseConnectionVO dbConn = this.dao.getDbConn();
		String dsName = dbConn.getDsName();
		
		ArrayList<SchemaRecord> lsSchema = this.dao.selectAllSchema(dsName, AppDefs.DEF_SCHEMAPREFIX_DEFAULT);
		return lsSchema;
	}	
	
	public boolean existSchema(String schemaName) 
	{
		DatabaseConnectionVO dbConn = this.dao.getDbConn();
		String dsName = dbConn.getDsName();		
		
		SchemaRecord oSchema = this.dao.selectSchemaByPk(dsName, schemaName);
		if(oSchema != null) {
			return true;
		}
		return false;
	}
	
	public void createSchema(String schemaName) 
	{
		if( !this.existSchema(schemaName) ) {
			this.dao.initSchema(schemaName);
		}
	}
	
	public void dropSchema(String schemaName) 
	{
		boolean bResult = this.dao.dropSchema(schemaName);
		if( !bResult ) {
			//TODO:
		}
	}

	/* LOAD/SAVE DATA */

	public void afterLoadProcess(String schemaName, CadDocumentDef doc)
	{
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		//BASEPOINT - POLYGON
		//
		BasePointDao cadPolygonPointDao = this.dao.createPtDao(AppDefs.OBJTYPE_POLYGON_GEOMPOINT);

		ArrayList<CadEntity> lsPolygon = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_POLYGON);
		for(CadEntity oEnt : lsPolygon) {
			CadPolygon oPolygon = (CadPolygon)oEnt;
			
			int refEntityId = oPolygon.getObjectId();
			
			ArrayList<BasePointRecord> lsPolylinePoint = cadPolygonPointDao.selectByRefEntityId(schemaName, refEntityId);
			oPolygon.loadAllPts(lsPolylinePoint);
		}		
		
		//BASEPOINT - POLYLINE
		//
		BasePointDao cadPolylinePointDao = this.dao.createPtDao(AppDefs.OBJTYPE_POLYLINE_GEOMPOINT);

		ArrayList<CadEntity> lsPolyline = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_POLYLINE);
		for(CadEntity oEnt : lsPolyline) {
			CadPolyline oPolyline = (CadPolyline)oEnt;
			
			int refEntityId = oPolyline.getObjectId();
			
			ArrayList<BasePointRecord> lsPolylinePoint = cadPolylinePointDao.selectByRefEntityId(schemaName, refEntityId);
			oPolyline.loadAllPts(lsPolylinePoint);				
		}		

		//BASEPOINT - ARQUITETURA
		//
		BasePointDao cadAreaPointDao = this.dao.createPtDao(AppDefs.OBJTYPE_AREA_GEOMPOINT);

		ArrayList<CadEntity> lsArea = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_BIMAREA);
		for(CadEntity oEnt : lsArea) {
			CadArea oArea = (CadArea)oEnt;
			
			int refEntityId = oArea.getObjectId();
			
			ArrayList<BasePointRecord> lsAreaPoint = cadAreaPointDao.selectByRefEntityId(schemaName, refEntityId);
			oArea.loadAllPts(lsAreaPoint);				
		}		

		//BIMOBJECTS - DRENAGEM
		//
		ArrayList<CadEntity> lsCI = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
		for(CadEntity oEnt : lsCI) {
			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
			
			int proximaCI = oCI.getProximaCI();
			if(proximaCI != AppDefs.NULL_INT) {
				CadCaixaInspecaoDrenagem oProximaCI = (CadCaixaInspecaoDrenagem)oBlk.getEntity(proximaCI);
				if(oProximaCI != null) {
					oCI.setProximo(oProximaCI);
					oProximaCI.addAnterior(oCI);
				}
			}
		}

		//BASEPOINT - DRENAGEM
		//
//		BaseODataDao cadAlinhamentoEstacaPointDrenagemDao = this.dao.createODataDao(AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA);
//
//		ArrayList<CadEntity> lsDrAlinhamentoEstaca = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA);
//		for(CadObject oEnt : lsDrAlinhamentoEstaca) {
//			CadAlinhamentoEstacaDrenagem oAlinhamento = (CadAlinhamentoEstacaDrenagem)oEnt;
//			
//			int refEntityId = oAlinhamento.getObjectId();
//			
//			ArrayList<BaseObjectRecord> lsAlinhamentoOData = cadAlinhamentoEstacaPointDrenagemDao.selectByRefEntityId(schemaName, refEntityId);
//			oAlinhamento.loadAllItens(lsAlinhamentoOData);
//		}		

		//BASEPOINT - DRENAGEM
		//
		BasePointDao cadAreaContribuicaoPointDrenagemDao = this.dao.createPtDao(AppDefs.OBJTYPE_AREACONTRIBUICAO_GEOMPOINT);

		ArrayList<CadEntity> lsDrAreaContrib = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO);
		for(CadEntity oEnt : lsDrAreaContrib) {
			CadAreaContribuicaoDrenagem oAreaContrib = (CadAreaContribuicaoDrenagem)oEnt;
			
			int refEntityId = oAreaContrib.getObjectId();
			
			ArrayList<BasePointRecord> lsAreaContribPoint = cadAreaContribuicaoPointDrenagemDao.selectByRefEntityId(schemaName, refEntityId);
			oAreaContrib.loadAllPts(lsAreaContribPoint);				
		}		

		//MEMORIACALCULO - DRENAGEM
		//
		BaseODataDao cadMemoriaCalculoDrenagemItemDao = this.dao.createODataDao(AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA);

		ArrayList<CadEntity> lsDrMemoriaCalculo = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRMEMORIACALCULO);
		for(CadEntity oEnt : lsDrMemoriaCalculo) {
			CadMemoriaCalculoDrenagem oMemoriaCalculo = (CadMemoriaCalculoDrenagem)oEnt;
			
			int refEntityId = oMemoriaCalculo.getObjectId();
			
			ArrayList<BaseObjectRecord> lsDrMemoriaCalculoItem = cadMemoriaCalculoDrenagemItemDao.selectByRefEntityId(schemaName, refEntityId);
			oMemoriaCalculo.loadAllItens(lsDrMemoriaCalculoItem);				
		}		

		//PERFIL - DRENAGEM
		//
		BaseODataDao cadPerfilDrenagemItemDao = this.dao.createODataDao(AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA);

		ArrayList<CadEntity> lsDrPerfilDrenagem = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRPERFILDRENAGEM);
		for(CadEntity oEnt : lsDrPerfilDrenagem) {
			CadPerfilDrenagem oPerfilDrenagem = (CadPerfilDrenagem)oEnt;
			
			int refEntityId = oPerfilDrenagem.getObjectId();
			
			ArrayList<BaseObjectRecord> lsDrPerfilDrenagemItem = cadPerfilDrenagemItemDao.selectByRefEntityId(schemaName, refEntityId);
			oPerfilDrenagem.loadAllItens(lsDrPerfilDrenagemItem);				
		}		

		//PARAM - ELETRICA
		//
		BaseODataDao cadParamEletricoDao = this.dao.createODataDao(AppDefs.OBJTYPE_PARAMELETRICO_ODATA);

		ArrayList<CadEntity> lsPontoEletrica = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODELINSEREPONTO);
		for(CadEntity oEnt : lsPontoEletrica) {
			CadPontoEletrica oPontoEletrica = (CadPontoEletrica)oEnt;
			
			int refEntityId = oPontoEletrica.getObjectId();
			
			ArrayList<BaseObjectRecord> lsParamEletricoOData = cadParamEletricoDao.selectByRefEntityId(schemaName, refEntityId);
			oPontoEletrica.loadAllItens(lsParamEletricoOData);				
		}		
	}
	
	//LOAD_DATA
	//
	public void load(String schemaName, CadDocumentDef doc) 
	{
		//OBJECT_DATA_TABLE
		ObjectDataTable objectDataTable = doc.getObjectDataTable();
		//objectDataTable.save(schemaName);
		
		//SHAPE_TABLE
		ShapeTable shapeTable = doc.getShapeTable();
		//shapeTable.save(schemaName);

		//IMAGE_DEF_TABLE
		ImageTable imageTable = doc.getImageTable();
		//imageTable.save(schemaName);

		//VIEW_TABLE
		ViewTable viewTable = doc.getViewTable();
		//viewTable.save(schemaName);

		//LAYER_DEF_TABLE
		LayerTable layerTable = doc.getLayerTable();
		//layerTable.save(schemaName);
		
		//LOAD_ENTITIES
		//
		//CADENTITIES
		CadEntityBaseDao cadArcDao = this.dao.create(AppDefs.OBJTYPE_ARC);
		ArrayList<BaseObjectRecord> lsArc = cadArcDao.selectAll(schemaName);
		doc.loadAll(lsArc);
		//
		CadEntityBaseDao cadBox3dDao = this.dao.create(AppDefs.OBJTYPE_BOX3D);
		ArrayList<BaseObjectRecord> lsBox3d = cadBox3dDao.selectAll(schemaName);
		doc.loadAll(lsBox3d);
		//
		CadEntityBaseDao cadCilinder3dDao = this.dao.create(AppDefs.OBJTYPE_CILINDER3D);
		ArrayList<BaseObjectRecord> lsCilinder3d = cadCilinder3dDao.selectAll(schemaName);
		doc.loadAll(lsCilinder3d);
		//
		CadEntityBaseDao cadCircleDao = this.dao.create(AppDefs.OBJTYPE_CIRCLE);
		ArrayList<BaseObjectRecord> lsCircle = cadCircleDao.selectAll(schemaName);
		doc.loadAll(lsCircle);
		//
		CadEntityBaseDao cadCone3dDao = this.dao.create(AppDefs.OBJTYPE_CONE3D);
		ArrayList<BaseObjectRecord> lsCone3d = cadCone3dDao.selectAll(schemaName);
		doc.loadAll(lsCone3d);
		//
		CadEntityBaseDao cadLineDao = this.dao.create(AppDefs.OBJTYPE_LINE);
		ArrayList<BaseObjectRecord> lsLine = cadLineDao.selectAll(schemaName);
		doc.loadAll(lsLine);
		//
		CadEntityBaseDao cadPipeDao = this.dao.create(AppDefs.OBJTYPE_BIMPIPE);
		ArrayList<BaseObjectRecord> lsPipe = cadPipeDao.selectAll(schemaName);
		doc.loadAll(lsPipe);
		//
		CadEntityBaseDao cadPointDao = this.dao.create(AppDefs.OBJTYPE_POINT);
		ArrayList<BaseObjectRecord> lsPoint = cadPointDao.selectAll(schemaName);
		doc.loadAll(lsPoint);
		//
		CadEntityBaseDao cadPolygonDao = this.dao.create(AppDefs.OBJTYPE_POLYGON);
		ArrayList<BaseObjectRecord> lsPolygon = cadPolygonDao.selectAll(schemaName);
		doc.loadAll(lsPolygon);
		//
		CadEntityBaseDao cadPolylineDao = this.dao.create(AppDefs.OBJTYPE_POLYLINE);
		ArrayList<BaseObjectRecord> lsPolyline = cadPolylineDao.selectAll(schemaName);
		doc.loadAll(lsPolyline);
		//
		//CadEntityBaseDao cadShapeDao = this.dao.create(AppDefs.OBJTYPE_SHAPE);
		//ArrayList<BaseObjectRecord> lsShape = cadShapeDao.selectAll(schemaName);
		//doc.loadAll(lsShape);
		//
		CadEntityBaseDao cadSphereDao = this.dao.create(AppDefs.OBJTYPE_SPHERE3D);
		ArrayList<BaseObjectRecord> lsSphere3d = cadSphereDao.selectAll(schemaName);
		doc.loadAll(lsSphere3d);
		//
		CadEntityBaseDao cadTextDao = this.dao.create(AppDefs.OBJTYPE_TEXT);
		ArrayList<BaseObjectRecord> lsText = cadTextDao.selectAll(schemaName);
		doc.loadAll(lsText);
		//
		CadEntityBaseDao cadTorus3dDao = this.dao.create(AppDefs.OBJTYPE_TORUS3D);
		ArrayList<BaseObjectRecord> lsTorus3d = cadTorus3dDao.selectAll(schemaName);
		doc.loadAll(lsTorus3d);
		//
		CadEntityBaseDao cadTroncoCone3dDao = this.dao.create(AppDefs.OBJTYPE_TRONCOCONE3D);
		ArrayList<BaseObjectRecord> lsTroncoCone3d = cadTroncoCone3dDao.selectAll(schemaName);
		doc.loadAll(lsTroncoCone3d);
		//
		CadEntityBaseDao cadAreaDao = this.dao.create(AppDefs.OBJTYPE_BIMAREA);
		ArrayList<BaseObjectRecord> lsArea = cadAreaDao.selectAll(schemaName);
		doc.loadAll(lsArea);
		
		//BIMOBJECTS - ARQUITETURA		
//		CadEntityBaseDao cadParedeDao = this.dao.create(AppDefs.OBJTYPE_BIMPAREDE);
//		ArrayList<BaseObjectRecord> lsParede = cadParedeDao.selectAll(schemaName);
//		doc.loadAll(lsParede);
		//
//		CadEntityBaseDao cadPortaDao = this.dao.create(AppDefs.OBJTYPE_BIMPORTA);
//		ArrayList<BaseObjectRecord> lsPorta = cadPortaDao.selectAll(schemaName);
//		doc.loadAll(lsPorta);
		//
//		CadEntityBaseDao cadPDuplaDao = this.dao.create(AppDefs.OBJTYPE_BIMPDUPLA);
//		ArrayList<BaseObjectRecord> lsPDupla = cadPDuplaDao.selectAll(schemaName);
//		doc.loadAll(lsPDupla);
		//
//		CadEntityBaseDao cadJanelaDao = this.dao.create(AppDefs.OBJTYPE_BIMJANELA);
//		ArrayList<BaseObjectRecord> lsJanela = cadJanelaDao.selectAll(schemaName);
//		doc.loadAll(lsJanela);
		//
//		CadEntityBaseDao cadPontoArquiteturaDao = this.dao.create(AppDefs.OBJTYPE_MODARQINSEREPONTO);
//		ArrayList<BaseObjectRecord> lsPontoArquitetura = cadPontoArquiteturaDao.selectAll(schemaName);
//		doc.loadAll(lsPontoArquitetura);
//		
//		//BIMOBJECTS - AGUAS_PLUVIAIS		
//		CadEntityBaseDao cadCaixaInspecaoAPluvialDao = this.dao.create(AppDefs.OBJTYPE_MODAPCAIXAINSPECAO);
//		ArrayList<BaseObjectRecord> lsCaixaInspecaoAPluvial = cadCaixaInspecaoAPluvialDao.selectAll(schemaName);
//		doc.loadAll(lsCaixaInspecaoAPluvial);
//		
//		//BIMOBJECTS - ESGOTO		
//		CadEntityBaseDao cadCaixaInspecaoEsgotoDao = this.dao.create(AppDefs.OBJTYPE_MODESANOTACAOCAIXAINSPECAO);
//		ArrayList<BaseObjectRecord> lsCaixaInspecaoEsgoto = cadCaixaInspecaoEsgotoDao.selectAll(schemaName);
//		doc.loadAll(lsCaixaInspecaoEsgoto);
//		//
//		CadEntityBaseDao cadPontoEsgotoDao = this.dao.create(AppDefs.OBJTYPE_MODESGINSEREPONTO);
//		ArrayList<BaseObjectRecord> lsPontoEsgoto = cadPontoEsgotoDao.selectAll(schemaName);
//		doc.loadAll(lsPontoEsgoto);

		//BIMOBJECTS - DRENAGEM		
		CadEntityBaseDao cadCaixaInspecaoDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
		ArrayList<BaseObjectRecord> lsCaixaInspecaoDrenagem = cadCaixaInspecaoDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsCaixaInspecaoDrenagem);
		//
		CadEntityBaseDao cadPontoDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRPONTODRENAGEM);
		ArrayList<BaseObjectRecord> lsPontoDrenagem = cadPontoDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsPontoDrenagem);
		//
		CadEntityBaseDao cadAnotacaoCaixaInspecaoDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRANOTACAOCAIXAINSPECAO);
		ArrayList<BaseObjectRecord> lsAnotacaoCaixaInspecaoDrenagem = cadAnotacaoCaixaInspecaoDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsAnotacaoCaixaInspecaoDrenagem);
		//
		CadEntityBaseDao cadAreaContribuicaoDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO);
		ArrayList<BaseObjectRecord> lsAreaContribuicaoDrenagem = cadAreaContribuicaoDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsAreaContribuicaoDrenagem);
		//
		CadEntityBaseDao cadMemoriaCalculoDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRMEMORIACALCULO);
		ArrayList<BaseObjectRecord> lsMemoriaCalculoDrenagem = cadMemoriaCalculoDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsMemoriaCalculoDrenagem);
		//
		CadEntityBaseDao cadPerfilDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRPERFILDRENAGEM);
		ArrayList<BaseObjectRecord> lsPerfilDrenagem = cadPerfilDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsPerfilDrenagem);
		//
		CadEntityBaseDao cadAlinhamentoEstacaDrenagemDao = this.dao.create(AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA);
		ArrayList<BaseObjectRecord> lsAlinhamentoEstacaDrenagem = cadAlinhamentoEstacaDrenagemDao.selectAll(schemaName);
		doc.loadAll(lsAlinhamentoEstacaDrenagem);

		//BIMOBJECTS - ELETRICA		
		CadEntityBaseDao cadPontoEletricaDao = this.dao.create(AppDefs.OBJTYPE_MODELINSEREPONTO);
		ArrayList<BaseObjectRecord> lsPontoEletrica = cadPontoEletricaDao.selectAll(schemaName);
		doc.loadAll(lsPontoEletrica);

		CadEntityBaseDao cadEletrodutoEletricaDao = this.dao.create(AppDefs.OBJTYPE_MODELELETRODUTO);
		ArrayList<BaseObjectRecord> lsEletrodutoEletrica = cadEletrodutoEletricaDao.selectAll(schemaName);
		doc.loadAll(lsEletrodutoEletrica);

		//BIMOBJECTS - GAS		
		CadEntityBaseDao cadPontoGasDao = this.dao.create(AppDefs.OBJTYPE_MODGINSEREPONTO);
		ArrayList<BaseObjectRecord> lsPontoGas = cadPontoGasDao.selectAll(schemaName);
		doc.loadAll(lsPontoGas);
		//
		CadEntityBaseDao cadRectangleDao = this.dao.create(AppDefs.OBJTYPE_RECTANGLE);
		ArrayList<BaseObjectRecord> lsRectangle = cadRectangleDao.selectAll(schemaName);
		doc.loadAll(lsRectangle);

		//PROJECT_DEF_REFERENCE
		CadProjectDef projectDef = doc.getCurrProjectDef();
		//this.save_CadProjectDef(schemaName, projectDef);

		//AFTERLOAD_PROCESS
		//
		this.afterLoadProcess(schemaName, doc);

		//FINAL_PROCESS
		//
		CadEntityBaseDao cadAreaTableDao = this.dao.create(AppDefs.OBJTYPE_BIMAREATABLE);
		ArrayList<BaseObjectRecord> lsAreaTable = cadAreaTableDao.selectAll(schemaName);
		doc.loadAll(lsAreaTable);
	}	

	//SAVE_DATA
	//
	public void save_CadProjectDef(String schemaName, CadProjectDef projectDef)
	{
		//TODO:
	}
	
	public void save(String schemaName, CadDocumentDef doc) 
	{
		//OBJECT_DATA_TABLE
		ObjectDataTable objectDataTable = doc.getObjectDataTable();
		//objectDataTable.save(schemaName);
		
		//SHAPE_TABLE
		ShapeTable shapeTable = doc.getShapeTable();
		//shapeTable.save(schemaName);

		//IMAGE_DEF_TABLE
		ImageTable imageTable = doc.getImageTable();
		//imageTable.save(schemaName);

		//VIEW_TABLE
		ViewTable viewTable = doc.getViewTable();
		//viewTable.save(schemaName);

		//LAYER_DEF_TABLE
		LayerTable layerTable = doc.getLayerTable();
		//layerTable.save(schemaName);
		
		//BLOCK_DEF_TABLE
		BlockTable blockTable = doc.getBlockTable();
		ArrayList<CadBlockDef> lsBlkDef = blockTable.getAllBlockDef();
		for(CadBlockDef oBlkDef : lsBlkDef) {
			ArrayList<CadEntity> lsEnt = oBlkDef.findAllEntity();
			for(CadEntity oEnt : lsEnt) {
				oEnt.save(this, schemaName, doc);
			}
		}

		//PROJECT_DEF_REFERENCE
		CadProjectDef projectDef = doc.getCurrProjectDef();
		//this.save_CadProjectDef(schemaName, projectDef);
	}

	/* Getters/Setters */
	
	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
}
