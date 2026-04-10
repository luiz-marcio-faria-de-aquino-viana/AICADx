/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseDb.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
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

package br.com.tlmv.aicadxapp;

import java.io.File;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadArcRecord;
import br.com.tlmv.aicadxapp.dao.record.CadAreaPointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadAreaRecord;
import br.com.tlmv.aicadxapp.dao.record.CadAreaTableRecord;
import br.com.tlmv.aicadxapp.dao.record.CadBox3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCilinder3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCircleRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCone3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadEllipseRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLevelRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLineRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPipeRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolygonPointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolygonRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolylinePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolylineRecord;
import br.com.tlmv.aicadxapp.dao.record.CadRectangleRecord;
import br.com.tlmv.aicadxapp.dao.record.CadShapeRecord;
import br.com.tlmv.aicadxapp.dao.record.CadSphere3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadTextRecord;
import br.com.tlmv.aicadxapp.dao.record.CadTorus3dRecord;
import br.com.tlmv.aicadxapp.dao.record.CadTroncoCone3dRecord;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.LevelVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.apluvial.dao.record.CadCaixaInspecaoAPluvialRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadJanelaRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadPDuplaRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadParedePointRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadParedeRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadPisoPointRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadPisoRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadPontoArquiteturaRecord;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadPortaRecord;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAlinhamentoEstacaDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAlinhamentoEstacaPointDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAnotacaoCaixaInspecaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoPointDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadCaixaInspecaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadEixoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadMemoriaCalculoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadMemoriaCalculoItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPerfilDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPerfilItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPontoDrenagemRecord;
import br.com.tlmv.aicadxmod.eletrica.cad.CadQuadroCargasEletrica;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadDiagramaUnifilarEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadEletroduto3DEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadEletrodutoEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadFioEletricoEletricaODataRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadImportaFiacaoEletrodutoEletricaODataRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadParamEletricoODataRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadPontoEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadQuadroCargasEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadTabelaFiacaoEletricaRecord;
import br.com.tlmv.aicadxmod.esgoto.dao.record.CadCaixaInspecaoEsgotoRecord;
import br.com.tlmv.aicadxmod.esgoto.dao.record.CadPontoEsgotoRecord;
import br.com.tlmv.aicadxmod.gas.dao.record.CadPontoGasRecord;

public class BaseDb 
{
//Private
	private ProjectRepoVO projectRepo = null;
	private boolean bCreateIfNotExist = false; 
	//
	private BaseDao dbaseDao = null;
	private String dbaseDriver = null;
	
	/* Methodes */
	
	// *** LOAD_DATA ***
	//
	private void loadData_lspts(int objType, String objVer, int objPointType, BaseDao dao, String schemaName, String sqlTableName, CadDocumentDef doc, CadBlockDef oBlk)
	{
		BasePointDao ptDao = dao.createPtDao(objPointType);

		CadEntity[] arr = oBlk.findAllEntityByObjType(objType);
		for(CadEntity oEnt : arr) {
			String cadRefEntityId = Integer.toString( oEnt.getObjectId() );
			
			ArrayList<BasePointRecord> lsPts = ptDao.selectByRefEntityId(
				objVer, 
				schemaName, 
				sqlTableName, 
				cadRefEntityId );
			oEnt.loadAllPts(lsPts);
		}		
	}
	
	private void loadData_lsdata(int objType, String objVer, int objODataType, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk, BaseObjectRecord oRef)
	{
		BaseODataDao odataDao = dao.createODataDao(objODataType);

		CadEntity[] arr = oBlk.findAllEntityByObjType(objType);
		for(CadEntity oEnt : arr) {
			String cadRefEntityId = Integer.toString( oEnt.getObjectId() );
			
			ArrayList<BaseObjectRecord> lsOData = odataDao.selectByRefEntityId(
				objVer, 
				schemaName, 
				cadRefEntityId, 
				oRef );	
			oEnt.loadAllOData(lsOData);
		}
	}
	
	private void loadData_entity(int objType, String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, BaseEntityRecord oRef)
	{
		//ENTITY
		BaseEntityDao entDao = dao.create(objType);
		ArrayList<BaseObjectRecord> ls = entDao.selectAll(
			objVer, 
			schemaName, 
			oRef );
		doc.loadAll(ls);
	}

	//REDE_PUBLICA_DRENAGEM - CAIXA_INSPECAO (CI)
	//
	private void loadData_postProcessing_caixaInspecaoDrenagem(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk)
	{
		CadEntity[] lsCI = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
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
	}
	
	//ELETRICA - QUADRO_CARGAS
	//
	private void loadData_postProcessing_quadroCargasEletrica(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk)
	{
		CadEntity[] lsQuadroCargas = oBlk.findAllEntityByObjType(AppDefs.OBJTYPE_MODELQUADROCARGAS);
		for(CadEntity oEnt : lsQuadroCargas) {
			CadQuadroCargasEletrica oQuadroCargas = (CadQuadroCargasEletrica)oEnt;
			oQuadroCargas.reCalcQuadroCargas();
		}		
	}
	
	/* *** LOAD_DATA *** */
	
	private void loadData_basicPostProcessing(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{		
		//BASE
		this.loadData_lspts(AppDefs.OBJTYPE_POLYGON, objVer, AppDefs.OBJTYPE_POLYGON_GEOMPOINT, dao, schemaName, CadPolygonPointRecord.sqlTableName, doc, oBlk);
		this.loadData_lspts(AppDefs.OBJTYPE_POLYLINE, objVer, AppDefs.OBJTYPE_POLYLINE_GEOMPOINT, dao, schemaName, CadPolylinePointRecord.sqlTableName, doc, oBlk);
	}
	
	private void loadData_areaTablePostProcessing(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{		
		//AREA_TABLE
		this.loadData_entity(AppDefs.OBJTYPE_BIMAREATABLE, objVer, dao, schemaName, doc, new CadAreaTableRecord() );
	}
	
	private void loadData_arquiteturaPostProcessing(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{		
		//ARQUITETURA
		this.loadData_lspts(AppDefs.OBJTYPE_BIMAREA, objVer, AppDefs.OBJTYPE_AREA_GEOMPOINT, dao, schemaName, CadAreaPointRecord.sqlTableName, doc, oBlk);
		this.loadData_lspts(AppDefs.OBJTYPE_BIMPISO, objVer, AppDefs.OBJTYPE_BIMPISO_GEOMPOINT, dao, schemaName, CadPisoPointRecord.sqlTableName, doc, oBlk);
		this.loadData_lspts(AppDefs.OBJTYPE_BIMPAREDE, objVer, AppDefs.OBJTYPE_BIMPAREDE_GEOMPOINT, dao, schemaName, CadParedePointRecord.sqlTableName, doc, oBlk);
	}
	
	private void loadData_drenagemPostProcessing(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{		
		//REDE_PUBLICA_DRENAGEM - LSPTS
		this.loadData_lspts(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO, objVer, AppDefs.OBJTYPE_AREACONTRIBUICAO_GEOMPOINT, dao, schemaName, CadAreaContribuicaoPointDrenagemRecord.sqlTableName, doc, oBlk);

		//REDE_PUBLICA_DRENAGEM - ODATA
		this.loadData_lsdata(AppDefs.OBJTYPE_MODDRMEMORIACALCULO, objVer, AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA, dao, schemaName, doc, oBlk, new CadMemoriaCalculoItemDrenagemODataRecord() );
		this.loadData_lsdata(AppDefs.OBJTYPE_MODDRPERFILDRENAGEM, objVer, AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA, dao, schemaName, doc, oBlk, new CadPerfilItemDrenagemODataRecord() );
		this.loadData_lsdata(AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA, objVer, AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA, dao, schemaName, doc, oBlk, new CadAlinhamentoEstacaPointDrenagemODataRecord() );
		
		//OUTROS
		this.loadData_postProcessing_caixaInspecaoDrenagem(objVer, dao, schemaName, doc, oBlk);
	}
	
	private void loadData_eletricaPostProcessing(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{		
		//ELETRICA - LSPTS
		this.loadData_lspts(AppDefs.OBJTYPE_MODELELETRODUTO3D, objVer, AppDefs.OBJTYPE_ELETRODUTO3D_GEOMPOINT, dao, schemaName, CadEletroduto3DEletricaRecord.sqlTableName, doc, oBlk);

		//ELETRICA - ODATA
		this.loadData_lsdata(AppDefs.OBJTYPE_MODELINSEREPONTO, objVer, AppDefs.OBJTYPE_PARAMELETRICO_ODATA, dao, schemaName, doc, oBlk, new CadParamEletricoODataRecord() );
		//
		this.loadData_lsdata(AppDefs.OBJTYPE_MODELELETRODUTO3D, objVer, AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA, dao, schemaName, doc, oBlk, new CadImportaFiacaoEletrodutoEletricaODataRecord() );
		this.loadData_lsdata(AppDefs.OBJTYPE_MODELELETRODUTO3D, objVer, AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA, dao, schemaName, doc, oBlk, new CadFioEletricoEletricaODataRecord() );
		//
		this.loadData_lsdata(AppDefs.OBJTYPE_MODELELETRODUTO, objVer, AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA, dao, schemaName, doc, oBlk, new CadImportaFiacaoEletrodutoEletricaODataRecord() );
		this.loadData_lsdata(AppDefs.OBJTYPE_MODELELETRODUTO, objVer, AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA, dao, schemaName, doc, oBlk, new CadFioEletricoEletricaODataRecord() );
		
		//OUTROS
		this.loadData_postProcessing_quadroCargasEletrica(objVer, dao, schemaName, doc, oBlk);
		this.loadData_entity(AppDefs.OBJTYPE_MODELTABELAFIACAO, objVer, dao, schemaName, doc, new CadTabelaFiacaoEletricaRecord() );
		
	}
	
	private void loadData_levels(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		//BIMLEVEL
		BaseEntityDao cadLevelDao = dao.create(AppDefs.OBJTYPE_BIMLEVEL);

		ArrayList<BaseObjectRecord> lsLevelRec = cadLevelDao.selectAll(
			objVer, 
			schemaName, 
			new CadLevelRecord() );

		ArrayList<LevelVO> lsNewListaNiveis = doc.loadAllCadLevels(lsLevelRec);
		doc.updateAllCadLevels(lsNewListaNiveis);		
	}

	private void loadData_basicCadObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		loadData_entity(AppDefs.OBJTYPE_ARC, objVer, dao, schemaName, doc, new CadArcRecord());
		loadData_entity(AppDefs.OBJTYPE_CIRCLE, objVer, dao, schemaName, doc, new CadCircleRecord());
		loadData_entity(AppDefs.OBJTYPE_ELLIPSE, objVer, dao, schemaName, doc, new CadEllipseRecord());
		loadData_entity(AppDefs.OBJTYPE_LINE, objVer, dao, schemaName, doc, new CadLineRecord());
		loadData_entity(AppDefs.OBJTYPE_POINT, objVer, dao, schemaName, doc, new CadPointRecord());
		loadData_entity(AppDefs.OBJTYPE_RECTANGLE, objVer, dao, schemaName, doc, new CadRectangleRecord());
		loadData_entity(AppDefs.OBJTYPE_POLYGON, objVer, dao, schemaName, doc, new CadPolygonRecord());
		loadData_entity(AppDefs.OBJTYPE_POLYLINE, objVer, dao, schemaName, doc, new CadPolylineRecord());
		loadData_entity(AppDefs.OBJTYPE_SHAPE, objVer, dao, schemaName, doc, new CadShapeRecord());
		loadData_entity(AppDefs.OBJTYPE_TEXT, objVer, dao, schemaName, doc, new CadTextRecord());
		
	}

	private void loadData_3DCadObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		loadData_entity(AppDefs.OBJTYPE_BOX3D, objVer, dao, schemaName, doc, new CadBox3dRecord());
		loadData_entity(AppDefs.OBJTYPE_CILINDER3D, objVer, dao, schemaName, doc, new CadCilinder3dRecord());
		loadData_entity(AppDefs.OBJTYPE_CONE3D, objVer, dao, schemaName, doc, new CadCone3dRecord());
		loadData_entity(AppDefs.OBJTYPE_SPHERE3D, objVer, dao, schemaName, doc, new CadSphere3dRecord());
		loadData_entity(AppDefs.OBJTYPE_TORUS3D, objVer, dao, schemaName, doc, new CadTorus3dRecord());
		loadData_entity(AppDefs.OBJTYPE_TRONCOCONE3D, objVer, dao, schemaName, doc, new CadTroncoCone3dRecord());

	}
	
	private void loadData_basicBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		loadData_entity(AppDefs.OBJTYPE_BIMLEVEL, objVer, dao, schemaName, doc, new CadLevelRecord());
		loadData_entity(AppDefs.OBJTYPE_BIMAREA, objVer, dao, schemaName, doc, new CadAreaRecord());
		loadData_entity(AppDefs.OBJTYPE_BIMPIPE, objVer, dao, schemaName, doc, new CadPipeRecord());
		
	}
	
	private void loadData_arquiteturaBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		loadData_entity(AppDefs.OBJTYPE_BIMPISO, objVer, dao, schemaName, doc, new CadPisoRecord());
		loadData_entity(AppDefs.OBJTYPE_BIMPAREDE, objVer, dao, schemaName, doc, new CadParedeRecord());
		loadData_entity(AppDefs.OBJTYPE_BIMPORTA, objVer, dao, schemaName, doc, new CadPortaRecord());
		loadData_entity(AppDefs.OBJTYPE_BIMPDUPLA, objVer, dao, schemaName, doc, new CadPDuplaRecord());
		loadData_entity(AppDefs.OBJTYPE_BIMJANELA, objVer, dao, schemaName, doc, new CadJanelaRecord());
		
		//PONTOS - ARQUITETURA
		loadData_entity(AppDefs.OBJTYPE_MODARQINSEREPONTO, objVer, dao, schemaName, doc, new CadPontoArquiteturaRecord());

	}
	
	private void loadData_aguasPluviaisBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		//CAIXA_INSPECAO - AGUAS_PLUVIAIS		
		loadData_entity(AppDefs.OBJTYPE_MODAPCAIXAINSPECAO, objVer, dao, schemaName, doc, new CadCaixaInspecaoAPluvialRecord());
	}
	
	private void loadData_esgotoBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		//CAIXA_INSPECAO - ESGOTO		
		loadData_entity(AppDefs.OBJTYPE_MODESANOTACAOCAIXAINSPECAO, objVer, dao, schemaName, doc, new CadCaixaInspecaoEsgotoRecord());

		//PONTOS - ESGOTO
		loadData_entity(AppDefs.OBJTYPE_MODESINSEREPONTO, objVer, dao, schemaName, doc, new CadPontoEsgotoRecord());
	}

	private void loadData_drenagemBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		//CAIXA_INSPECAO - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO, objVer, dao, schemaName, doc, new CadCaixaInspecaoDrenagemRecord());

		//EIXOS - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDREIXODRENAGEM, objVer, dao, schemaName, doc, new CadEixoDrenagemRecord());

		//PONTOS - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRPONTODRENAGEM, objVer, dao, schemaName, doc, new CadPontoDrenagemRecord());

		//ANOTACAO_CAIXA_INSPECAO - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRANOTACAOCAIXAINSPECAO, objVer, dao, schemaName, doc, new CadAnotacaoCaixaInspecaoDrenagemRecord());

		//AREA_CONTRIBUICAO - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO, objVer, dao, schemaName, doc, new CadAreaContribuicaoDrenagemRecord());

		//MEMORIA_CALCULO - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRMEMORIACALCULO, objVer, dao, schemaName, doc, new CadMemoriaCalculoDrenagemRecord());

		//PERFIL - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRPERFILDRENAGEM, objVer, dao, schemaName, doc, new CadPerfilDrenagemRecord());

		//ALINHAMENTO_ESTACA - DRENAGEM		
		loadData_entity(AppDefs.OBJTYPE_MODDRALINHAMENTOESTACA, objVer, dao, schemaName, doc, new CadAlinhamentoEstacaDrenagemRecord());

	}
	
	private void loadData_eletricaBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		//PONTOS - ELETRICA		
		loadData_entity(AppDefs.OBJTYPE_MODELINSEREPONTO, objVer, dao, schemaName, doc, new CadPontoEletricaRecord());

		//ELETRODUTOS - ELETRICA		
		loadData_entity(AppDefs.OBJTYPE_MODELELETRODUTO, objVer, dao, schemaName, doc, new CadEletrodutoEletricaRecord());

		//ELETRODUTOS3D - ELETRICA
		loadData_entity(AppDefs.OBJTYPE_MODELELETRODUTO3D, objVer, dao, schemaName, doc, new CadEletrodutoEletricaRecord());

		//QUADROCARGAS - ELETRICA
		loadData_entity(AppDefs.OBJTYPE_MODELQUADROCARGAS, objVer, dao, schemaName, doc, new CadQuadroCargasEletricaRecord());

		//DIAGRAMAUNIFILAR - ELETRICA
		loadData_entity(AppDefs.OBJTYPE_MODELDIAGRAMAUNIFILAR, objVer, dao, schemaName, doc, new CadDiagramaUnifilarEletricaRecord());

	}
	
	public void loadData_gasBimObjects(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc, CadBlockDef oBlk) 
	{
		//PONTOS - GAS		
		loadData_entity(AppDefs.OBJTYPE_MODGINSEREPONTO, objVer, dao, schemaName, doc, new CadPontoGasRecord());

	}
			
//Public

	public BaseDb(ProjectRepoVO projectRepo, String dbaseDriver, boolean bCreateIfNotExist) {
		this.init(projectRepo, dbaseDriver, bCreateIfNotExist);
	}

	/* Methodes */
	
	public void init(ProjectRepoVO projectRepo, String dbaseDriver, boolean bCreateIfNotExist)
	{
		this.projectRepo = projectRepo;
		this.dbaseDriver = dbaseDriver;
		this.bCreateIfNotExist = bCreateIfNotExist; 
	}

	/* *** PROJECT_REPO *** */
	
	// EXIST_PROJECTREPO
	public boolean existProjectRepo(ProjectRepoVO projectRepo) 
	{
		AppMain app = AppMain.getApp();
		AppCtx ctx = app.getCtx();
		
		String strProjectDir = projectRepo.getProjectDir();				

		File f = new File( strProjectDir );		
		if( f.exists() && f.isDirectory() ) {
			return true;
		}
		return false;
	}
	
	// CREATE_PROJECTREPO
	public void createProjectRepo(ProjectRepoVO projectRepo)
	{
		if( !this.existProjectRepo(projectRepo) ) {
			projectRepo.createProjectDir();
		}
	}	

	/* *** DBASE *** */
	
	//INIT_DBASE - DAO INITIALIZATION
	public BaseDao initDbase()
	{
		this.dbaseDao = BaseDao.create(
			this.projectRepo, 
			this.dbaseDriver, 
			this.bCreateIfNotExist);
		return this.dbaseDao;
	}
	
	// LISTALL_DBASE
	public ArrayList<SchemaRecord> listAllDbase(BaseDao dao) 
	{
		DatabaseConnectionVO dbConn = dao.getDbConn();
		String dsName = dbConn.getDsName();
		
		ArrayList<SchemaRecord> lsSchema = dao.selectAllSchema(dsName, AppDefs.DEF_SCHEMAPREFIX_DEFAULT);
		return lsSchema;
	}	
	
	// EXIST_DBASE
	public boolean existDbase(BaseDao dao, String schemaName) 
	{
		DatabaseConnectionVO dbConn = this.dbaseDao.getDbConn();
		String dsName = dbConn.getDsName();		
		
		SchemaRecord oSchema = dao.selectSchemaByPk(dsName, schemaName);
		if(oSchema != null) {
			return true;
		}
		return false;
	}
	
	// CREATE_DBASE
	public boolean createDbase(BaseDao dao, String schemaName) 
	{
		boolean bResult = dao.initSchema(schemaName); 
		if( !bResult ) {
			String errmsg = "ERR: Nao foi possivel criar o esquema no banco de dados de documentos";
			AppError.showCmdError(errmsg, this.getClass());					
		}
		return bResult;
	}
	
	// OPEN_DBASE
	public boolean openDbase(BaseDao dao, String schemaName) 
	{
		boolean bResult = dao.open();
		if( !bResult ) {
			String errmsg = "ERR: Nao foi possivel abrir o banco de dados de documentos";
			AppError.showCmdError(errmsg, this.getClass());
		}
		return bResult;
	}
	
	// CLOSE_DBASE
	public boolean closeDbase(BaseDao dao) 
	{
		boolean bResult = dao.close();
		if( !bResult ) {
			String errmsg = "ERR: Nao foi possivel fechar o banco de dados de documentos";
			AppError.showCmdError(errmsg, this.getClass());
		}
		return bResult;
	}
	
	// DROP_DBASE
	public void dropDbase(BaseDao dao, String schemaName) 
	{
		boolean bResult = dao.dropSchema(schemaName);
		if( !bResult ) {
			String errmsg = "ERR: Falha ao remover o esquema no banco dados de documentos";
			AppError.showCmdError(errmsg, this.getClass());					
		}
	}
		
	// LOAD_DBASE
	public boolean loadDbase(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {		
			CadBlockDef oBlk = doc.getCurrBlockDef();
				
			//LOADDBASE_OBJECTS
			//
			this.loadData_levels(objVer, dao, schemaName, doc, oBlk);
			//
			this.loadData_basicCadObjects(objVer, dao, schemaName, doc, oBlk); 
			this.loadData_3DCadObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_basicBimObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_arquiteturaBimObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_aguasPluviaisBimObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_esgotoBimObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_drenagemBimObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_eletricaBimObjects(objVer, dao, schemaName, doc, oBlk);
			this.loadData_gasBimObjects(objVer, dao, schemaName, doc, oBlk);
	
			//LOADDBASE_POSTPROCESSING
			//
			this.loadData_basicPostProcessing(objVer, dao, schemaName, doc, oBlk);
			this.loadData_areaTablePostProcessing(objVer, dao, schemaName, doc, oBlk);
			//
			this.loadData_arquiteturaPostProcessing(objVer, dao, schemaName, doc, oBlk);
			this.loadData_drenagemPostProcessing(objVer, dao, schemaName, doc, oBlk);
			this.loadData_eletricaPostProcessing(objVer, dao, schemaName, doc, oBlk);
			
			bResult = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return bResult;
	}	

	//SAVE_DBASE
	public boolean saveDbase(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {
			bResult = doc.save(objVer, dao, schemaName, doc);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}
	
	/* *** DATA_FILE *** */
	
	//INIT_DATAFILE - DAO INITIALIZATION
	public BaseDao initDataFile()
	{
		this.dbaseDao = BaseDao.create(
			this.projectRepo, 
			this.dbaseDriver, 
			this.bCreateIfNotExist);
		return this.dbaseDao;
	}
	
	// LISTALL_DATAFILE
	public ArrayList<File> listAllDataFile(BaseDao dataFileDao) 
	{
		ArrayList<File> lsResult = new ArrayList<File>();
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		String repoDir = ctx.getRepositoryDir();						
		
		File f_repoDir = new File(repoDir);
		
		File[] arrRepo = f_repoDir.listFiles();
		int szArrRepo = arrRepo.length;
		for(int i = 0; i < szArrRepo; i++) {
			File f_dir = arrRepo[i];
			if( f_dir.isDirectory() ) {
				lsResult.add( f_repoDir );
			}
		}
		return lsResult;
	}	

	//EXIST_DATAFILE
	public boolean existDataFile(BaseDao dataFileDao) 
	{
		ProjectRepoVO projectRepo = dataFileDao.getProjectRepo();
		String strDataFileName = projectRepo.getSqliteFullFileName();						

		File f = new File( strDataFileName );		
		if( ( f.exists() ) && ( f.isFile() ) ) {
			return true;
		}
		return false;
	}
	
	//BACKUP_DATAFILE
	public String backupDataFile(BaseDao dataFileDao) 
	{
		ProjectRepoVO projectRepo = dataFileDao.getProjectRepo();
		String strDataFileName = projectRepo.getSqliteFullFileName();						
		String strBkpFileName = projectRepo.getSqliteBkpFullFileName();

		FileUtil.copyFile(strDataFileName, strBkpFileName);
		return strBkpFileName;
	}
	
	//CREATE_DATAFILE
	public boolean createDataFile(BaseDao dataFileDao) 
	{
		boolean bResult = this.dbaseDao.open();
		if( bResult ) {
			bResult = this.dbaseDao.initSchema(AppDefs.NULL_SCHEMA); 
			if( !bResult ) {
				String errmsg = "ERR: Nao foi possivel criar o esquema no arquivo de dados de documentos";
				AppError.showCmdError(errmsg, this.getClass());					
			}
		}
		else {
			String errmsg = "ERR: Nao foi possivel abrir o arquivo de dados de documentos";
			AppError.showCmdError(errmsg, this.getClass());
		}
		return bResult;
	}
	
	//OPEN_DATAFILE
	public boolean openDataFile(BaseDao dataFileDao) 
	{
		boolean bResult = existDataFile(dataFileDao);
		if( bResult ) {
			bResult = this.dbaseDao.open();
			if( !bResult ) {
				String errmsg = "ERR: Nao foi possivel abrir o arquivo de dados de documentos";
				AppError.showCmdError(errmsg, this.getClass());
			}
		}
		return bResult;
	}
	
	//CLOSE_DATAFILE
	public boolean closeDataFile(BaseDao dataFileDao) 
	{
		boolean bResult = dataFileDao.close();
		if( !bResult ) {
			String errmsg = "ERR: Falha ao fechar o arquivo de dados de documentos";
			AppError.showCmdError(errmsg, this.getClass());
		}
		return bResult;
	}
	
	//LOAD_DATAFILE
	public boolean loadDataFile(String objVer, BaseDao dataFileDao, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {			
			CadBlockDef oBlk = doc.getCurrBlockDef();
			
			String schemaName = AppDefs.NULL_SCHEMA;
			
			//LOADDBASE_OBJECTS
			//
			this.loadData_levels(objVer, dataFileDao, schemaName, doc, oBlk);
			//
			this.loadData_basicCadObjects(objVer, dataFileDao, schemaName, doc, oBlk); 
			this.loadData_3DCadObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_basicBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_arquiteturaBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_aguasPluviaisBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_esgotoBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_drenagemBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_eletricaBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_gasBimObjects(objVer, dataFileDao, schemaName, doc, oBlk);
	
			//LOADDBASE_POSTPROCESSING
			//
			this.loadData_basicPostProcessing(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_areaTablePostProcessing(objVer, dataFileDao, schemaName, doc, oBlk);
			//
			this.loadData_arquiteturaPostProcessing(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_drenagemPostProcessing(objVer, dataFileDao, schemaName, doc, oBlk);
			this.loadData_eletricaPostProcessing(objVer, dataFileDao, schemaName, doc, oBlk);
			
			bResult = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return bResult;
	}	
	
	//SAVE_DATAFILE
	public boolean saveDataFile(String objVer, BaseDao dataFileDao, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {
			bResult = doc.save(objVer, dataFileDao, AppDefs.NULL_SCHEMA, doc);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}
	
	/* *** NOSQL *** */
	
	//INIT_NOSQL - DAO INITIALIZATION
	public BaseDao initNoSql()
	{
		this.dbaseDao = BaseDao.create(
			this.projectRepo, 
			this.dbaseDriver, 
			this.bCreateIfNotExist);
		return this.dbaseDao;
	}
	
	// LISTALL_NOSQL
	public ArrayList<File> listAllNoSql(BaseDao noSqlDao) 
	{
		ArrayList<File> lsResult = new ArrayList<File>();
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		String repoDir = ctx.getRepositoryDir();						
		
		File f_repoDir = new File(repoDir);
		
		File[] arrRepo = f_repoDir.listFiles();
		int szArrRepo = arrRepo.length;
		for(int i = 0; i < szArrRepo; i++) {
			File f_dir = arrRepo[i];
			if( f_dir.isDirectory() ) {
				lsResult.add( f_repoDir );
			}
		}
		return lsResult;
	}	

	//LOAD_NOSQL
	public boolean loadNoSql(String objVer, BaseDao noSqlDao, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {			
			CadBlockDef oBlk = doc.getCurrBlockDef();
			
			String schemaName = AppDefs.NULL_SCHEMA;

			if( noSqlDao.retrieveAllData(objVer) ) {
				//LOADNOSQL_OBJECTS
				//
				this.loadData_levels(objVer, noSqlDao, schemaName, doc, oBlk);
				//
				this.loadData_basicCadObjects(objVer, noSqlDao, schemaName, doc, oBlk); 
				this.loadData_3DCadObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_basicBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_arquiteturaBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_aguasPluviaisBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_esgotoBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_drenagemBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_eletricaBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_gasBimObjects(objVer, noSqlDao, schemaName, doc, oBlk);
				
				//LOADNOSQL_POSTPROCESSING
				//
				this.loadData_basicPostProcessing(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_areaTablePostProcessing(objVer, noSqlDao, schemaName, doc, oBlk);
				//
				this.loadData_arquiteturaPostProcessing(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_drenagemPostProcessing(objVer, noSqlDao, schemaName, doc, oBlk);
				this.loadData_eletricaPostProcessing(objVer, noSqlDao, schemaName, doc, oBlk);
						
				bResult = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return bResult;
	}	

	//SAVEALL_ENTITIES_NOSQL
	public boolean saveAllEntitiesNoSql(String objVer, BaseDao noSqlDao, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {			
			BlockTable blockTable = doc.getBlockTable();

			ArrayList<CadBlockDef> lsBlkDef = blockTable.getAllBlockDef();
			for(CadBlockDef oBlkDef : lsBlkDef) {
				CadEntity[] arrEnt = oBlkDef.findAllEntity();
				for(CadEntity oEnt : arrEnt) {
					if( oEnt.isDeleted() ) continue;
					
					oEnt.save(objVer, noSqlDao, AppDefs.NULL_SCHEMA, doc);
				}
			}			
			bResult = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return bResult;
	}

	//SAVE_NOSQL
	public boolean saveNoSql(String objVer, BaseDao noSqlDao, CadDocumentDef doc) 
	{
		boolean bResult = false;
		
		try {
			bResult = doc.save(objVer, noSqlDao, AppDefs.NULL_SCHEMA, doc);
			if( bResult ) {
				noSqlDao.persistAllData(objVer);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}
			
	/* Getters/Setters */
	
	public ProjectRepoVO getProjectRepo() {
		return projectRepo;
	}

	public void setProjectRepo(ProjectRepoVO projectRepo) {
		this.projectRepo = projectRepo;
	}

	public BaseDao getDbaseDao() {
		return dbaseDao;
	}

	public void setDbaseDao(BaseDao dbaseDao) {
		this.dbaseDao = dbaseDao;
	}

	public String getDbaseDriver() {
		return dbaseDriver;
	}

	public void setDbaseDriver(String dbaseDriver) {
		this.dbaseDriver = dbaseDriver;
	}
	
}
