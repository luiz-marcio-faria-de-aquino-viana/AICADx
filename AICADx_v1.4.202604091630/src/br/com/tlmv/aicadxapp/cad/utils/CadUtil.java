/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/04/2025
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

package br.com.tlmv.aicadxapp.cad.utils;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.ImageTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CadUtil 
{
//Public

	public static String getObjTypeStr(int objType) {
		String objTypeStr = AppDefs.NULL_STR;		
		int sz = AppDefs.ARR_OBJTYPE_STR.length;

		int pos = objType - AppDefs.OBJTYPE_NONE;
		if( (pos >= 0) && (pos < sz) ) {
			objTypeStr = AppDefs.ARR_OBJTYPE_STR[objType - AppDefs.OBJTYPE_NONE];
		}
		return objTypeStr;
	}
	
	public static CadDocumentDef getSelectDoc(CadDocumentDef selcDoc)
	{
		if(selcDoc != null)
			return selcDoc;

		MainPanel panel = MainPanel.getMainPanel();
		CadDocumentDef doc = panel.getCurrDocumentDef();
		return doc;
	}
	
	public static ArrayList<CadAreaContribuicaoDrenagem> findCadAreaContribuicaoByNumeroCI(int numeroCI, ArrayList<CadEntity> lsArea) {
		ArrayList<CadAreaContribuicaoDrenagem> lsResult = new ArrayList<CadAreaContribuicaoDrenagem>();
		for(CadEntity oEnt1 : lsArea) {
			CadAreaContribuicaoDrenagem oArea = (CadAreaContribuicaoDrenagem)oEnt1;
			int areaNumeroCI = oArea.getCI().getNumeroCI();
			if(numeroCI == areaNumeroCI) {
				lsResult.add(oArea);
			}
		}
		return lsResult;
	}
	
	public static double calcAreaExternaFromCIAnterior(CadCaixaInspecaoDrenagem oCI)
	{
		double dAreaExterna = 0.0;
		
		ArrayList<CadCaixaInspecaoDrenagem> lsCIAnterior = oCI.getLsAnterior();
		for(CadCaixaInspecaoDrenagem oCIAnterior : lsCIAnterior) {
			dAreaExterna += oCIAnterior.getAreaTotal();
		}
		return dAreaExterna;
	}

	
	public static double calcAreaTotalFromCadAreaContribuicaoByNumeroCI(int numeroCI, CadEntity[] arrArea) {
		double dAreaLocal = 0.0;
		for(CadEntity oEnt1 : arrArea) {
			CadAreaContribuicaoDrenagem oArea = (CadAreaContribuicaoDrenagem)oEnt1;
			int areaNumeroCI = oArea.getCI().getNumeroCI();
			if(numeroCI == areaNumeroCI) {
				double area = oArea.getAreaHectare();
				dAreaLocal += area;
			}
		}
		return dAreaLocal;
	}
	
	//PROJECT_DEFINITIONS
	//
	public static CadProjectDef selectProjectDef(CadDocumentDef selcDoc)
	{
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);

		CadProjectDef oProjDef = doc.getCurrProjectDef();
		return oProjDef;
	}
	
	//IMAGES
	//
	public static CadImageDef selectImageByImageName(CadDocumentDef selcDoc, String imageName)
	{
		CadImageDef oResult = null;
		
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ImageTable tbl = doc.getImageTable();		
		if( tbl.hasImageDef(imageName) ) {
			oResult = tbl.getImageDef(imageName); 
		}
		return oResult;
	}

	public static ArrayList<CadImageDef> selectAllImage(CadDocumentDef selcDoc)
	{
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ImageTable tbl = doc.getImageTable();
		ArrayList<CadImageDef> lsResult = tbl.getAllImageDef();
		return lsResult;
	}
	
	//BLOCKS
	//
	public static CadBlockDef selectBlockByBlockName(CadDocumentDef selcDoc, String blockName)
	{
		CadBlockDef oResult = null;
		
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		BlockTable blk = doc.getBlockTable();		
		if( blk.hasBlockDef(blockName) ) {
			oResult = blk.getBlockDef(blockName); 
		}
		return oResult;
	}

	public static ArrayList<CadBlockDef> selectAllBlock(CadDocumentDef selcDoc)
	{
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		BlockTable tbl = doc.getBlockTable();
		ArrayList<CadBlockDef> lsResult = tbl.getAllBlockDef();
		return lsResult;
	}
	
	//LAYERS
	//
	public static CadLayerDef selectLayerByLayerName(CadDocumentDef selcDoc, String layerName)
	{
		CadLayerDef oResult = null;
		
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		LayerTable tbl = doc.getLayerTable();		
		if( tbl.hasLayerDef(layerName) ) {
			oResult = tbl.getLayerDef(layerName); 
		}
		return oResult;
	}

	public static ArrayList<CadLayerDef> selectAllLayer(CadDocumentDef selcDoc)
	{
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		LayerTable tbl = doc.getLayerTable();
		ArrayList<CadLayerDef> lsResult = tbl.getAllLayerDef();
		return lsResult;
	}
	
	//SHAPES
	//
	public static Shape selectShapeByShapeName(CadDocumentDef selcDoc, String shapeName)
	{
		Shape oResult = null;
		
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ShapeTable tbl = doc.getShapeTable();		
		if( tbl.hasShape(shapeName) ) {
			oResult = tbl.getShape(shapeName); 
		}
		return oResult;
	}

	public static ArrayList<Shape> selectAllShape(CadDocumentDef selcDoc)
	{
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ShapeTable tbl = doc.getShapeTable();
		ArrayList<Shape> lsResult = tbl.getAllShape();
		return lsResult;
	}
	
	//VIEWS
	//
	public static ICompView selectViewByViewName(CadDocumentDef selcDoc, String viewName)
	{
		ICompView oResult = null;
		
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ViewTable tbl = doc.getViewTable();		
		if( tbl.hasView(viewName) ) {
			oResult = tbl.getView(viewName); 
		}
		return oResult;
	}

	public static ArrayList<CompView> selectAllView(CadDocumentDef selcDoc)
	{
		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ViewTable tbl = doc.getViewTable();
		ArrayList<CompView> lsResult = tbl.getAllView();
		return lsResult;
	}

	public static ArrayList<CompView> selectAllViewByViewType(CadDocumentDef selcDoc, int viewType)
	{
		ArrayList<CompView> lsResult = new ArrayList<CompView>();

		CadDocumentDef doc = CadUtil.getSelectDoc(selcDoc);
		ViewTable tbl = doc.getViewTable();
		
		ArrayList<CompView> ls = tbl.getAllView();
		for(CompView oCurrView : ls) {
			int currViewType = oCurrView.getViewType();
			if(currViewType == viewType) {
				lsResult.add(oCurrView);
			}
		}
		return lsResult;
	}
	
	/* GENERAL OPERATIONS */

	public static int getEntityTableSz(ArrayList<Integer> ls) {
		int sz = ls.size();
		return sz;
	}

	public static void clearEntityTable(Hashtable<Integer,Integer> map, ArrayList<Integer> ls) {
		map.clear();
		ls.clear();
	}
	
	public static boolean addEntity(Hashtable<Integer,Integer> map, ArrayList<Integer> ls, CadEntity oEnt) {
		Integer objectId = oEnt.getObjectId();
		if( !map.containsKey(objectId) ) {
			map.put(objectId, objectId);
			ls.add(objectId);
		}
		return false;
	}
	
	public static boolean existEntity(Hashtable<Integer,Integer> map, Integer objectId) {
		if( map.containsKey(objectId) ) {
			return true;
		}
		return false;
	}
	
	public static CadEntity getEntity(Hashtable<Integer,CadEntity> entityTable, Hashtable<Integer,Integer> map, Integer objectId) {
		if( map.containsKey(objectId) ) {
			CadEntity oEnt = entityTable.get(objectId);
			return oEnt;
		}
		return null;
	}
	
	public static CadEntity getEntityAt(Hashtable<Integer,CadEntity> entityTable, ArrayList<Integer> ls, Integer pos) {
		int sz = ls.size();
		if(pos < sz) {
			Integer objectId = ls.get(pos);
			CadEntity oEnt = entityTable.get(objectId);
			return oEnt;
		}
		return null;
	}
	
	public static CadEntity removeEntity(Hashtable<Integer,CadEntity> entityTable, Hashtable<Integer,Integer> map, Integer objectId) {
		if( map.containsKey(objectId) ) {
			CadEntity oEnt = entityTable.get(objectId);
			oEnt.setDeleted(true);
			return oEnt;
		}
		return null;
	}
	
	/* FINDxxx - OBJECTID_LIST */

	public static CadEntity[] findAllEntityByObjType(Hashtable<Integer,CadEntity> entityTable, ArrayList<Integer> ls, int objType) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(Integer objectId : ls) {
			CadEntity oEnt = entityTable.get(objectId);
			if(oEnt.isDeleted()) continue;
			
			if( oEnt.search(objType, null) ) {
				lsResult.add(oEnt);
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	
	
	public static CadEntity[] findAllEntityByLevelName(Hashtable<Integer,CadEntity> entityTable, ArrayList<Integer> ls, int objType, String levelName, boolean bIncludeDeleted) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(Integer objectId : ls) {
			CadEntity oEnt = entityTable.get(objectId);
			if( !bIncludeDeleted ) {
				if( oEnt.isDeleted() ) continue;
			}
			
			if( oEnt.search(objType, null) ) {
				CadLevel oLevel = oEnt.getLevel();
				if(oLevel != null) {
					String strLevelName = oLevel.getLevelLocalName();
		
					if( levelName.equalsIgnoreCase(strLevelName) )
						lsResult.add(oEnt);
				}
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByLayerName(Hashtable<Integer,CadEntity> entityTable, ArrayList<Integer> ls, int objType, String layerName, boolean bIncludeDeleted) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(Integer objectId : ls) {
			CadEntity oEnt = entityTable.get(objectId);
			if( !bIncludeDeleted ) {
				if( oEnt.isDeleted() ) continue;
			}
			
			if( oEnt.search(objType, null) ) {
				CadLayerDef oLayer = oEnt.getLayer();
				if(oLayer != null) {
					String strLayerName = oLayer.getName();
		
					if( layerName.equalsIgnoreCase(strLayerName) )
						lsResult.add(oEnt);
				}
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByObjTypeAndSearchString(Hashtable<Integer,CadEntity> entityTable, ArrayList<Integer> ls, int objType, String searchString) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(Integer objectId : ls) {
			CadEntity oEnt = entityTable.get(objectId);
			if(oEnt.isDeleted()) continue;

			if( oEnt.search(objType, searchString) ) {
				lsResult.add(oEnt);
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllHostedEntityByObjectId(Hashtable<Integer,CadEntity> entityTable, ArrayList<Integer> ls, int objectId) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		CadParede oHost = null;
		for(Integer hostedObjectId : ls) {
			CadEntity oEnt = entityTable.get(hostedObjectId);

			int currObjType = oEnt.getObjType();
			if(currObjType == AppDefs.OBJTYPE_BIMJANELA) {
				CadJanela o = (CadJanela)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPORTA) {
				CadPorta o = (CadPorta)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPDUPLA) {
				CadPDupla o = (CadPDupla)oEnt;
				oHost = o.getParede();
			}

			if(oHost.getObjectId() == objectId)
				lsResult.add(oEnt);
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}
	
	/* FINDxxx - ENTITY_LIST */
	
	public static CadEntity[] findAllEntity(ArrayList<CadEntity> lsEntity) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : lsEntity) {
			if( oEnt.isDeleted() ) continue;
			
			lsResult.add(oEnt);
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByObjType(ArrayList<CadEntity> lsEntity, int objType) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : lsEntity) {
			if( oEnt.isDeleted() ) continue;
			
			if( oEnt.search(objType, null) ) {
				lsResult.add(oEnt);
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByObjType(ArrayList<CadEntity> lsEntity, int[] arrObjType) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : lsEntity) {
			if( oEnt.isDeleted() ) continue;
			
			if( oEnt.isObjtypeOf(arrObjType) ) {
				lsResult.add(oEnt);
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByLevelName(ArrayList<CadEntity> lsEntity, int objType, String levelName, boolean bIncludeDeleted) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : lsEntity) {
			if( !bIncludeDeleted ) {
				if( oEnt.isDeleted() ) continue;
			}
			
			if( oEnt.search(objType, null) ) {
				CadLevel oLevel = oEnt.getLevel();
				if(oLevel != null) {
					String strLevelName = oLevel.getLevelLocalName();
		
					if( levelName.equalsIgnoreCase(strLevelName) )
						lsResult.add(oEnt);
				}
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByLayerName(ArrayList<CadEntity> lsEntity, int objType, String layerName, boolean bIncludeDeleted) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : lsEntity) {
			if( !bIncludeDeleted ) {
				if( oEnt.isDeleted() ) continue;
			}
			
			if( oEnt.search(objType, null) ) {
				CadLayerDef oLayer = oEnt.getLayer();
				if(oLayer != null) {
					String strLayerName = oLayer.getName();
		
					if( layerName.equalsIgnoreCase(strLayerName) )
						lsResult.add(oEnt);
				}
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllEntityByObjType(ArrayList<CadEntity> lsEntity, int objType, String searchString) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : lsEntity) {
			if( oEnt.isDeleted() ) continue;
			
			if( oEnt.search(objType, searchString) ) {
				lsResult.add(oEnt);
			}
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	public static CadEntity[] findAllHostedEntityByObjectId(ArrayList<CadEntity> lsEntity, int objectId) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		

		CadParede oHost = null;
		for(CadEntity oEnt : lsEntity) {
			if( oEnt.isDeleted() ) continue;
			
			int currObjType = oEnt.getObjType();
			if(currObjType == AppDefs.OBJTYPE_BIMJANELA) {
				CadJanela o = (CadJanela)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPORTA) {
				CadPorta o = (CadPorta)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPDUPLA) {
				CadPDupla o = (CadPDupla)oEnt;
				oHost = o.getParede();
			}

			if(oHost.getObjectId() == objectId)
				lsResult.add(oEnt);
		}
		
		//ARR_RESULT
		CadEntity[] arrResult = CadUtil.toArrEntities(lsResult);
		return arrResult;
	}	

	/* TO_ARRAY */
	
	public static CadEntity[] toArrEntities(ArrayList<CadEntity> lsEntity)
	{
		int sz = lsEntity.size();
		CadEntity[] arr = lsEntity.toArray(new CadEntity[sz]);
		return arr;
	}
	
}
