/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad.utils;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.ImageTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CadUtil 
{
//Public

	public static CadDocumentDef getSelectDoc(CadDocumentDef selcDoc)
	{
		if(selcDoc != null)
			return selcDoc;

		AppCadMain cad = AppCadMain.getCad();		
		CadDocumentDef doc = cad.getCurrDocumentDef();
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

	
	public static double calcAreaTotalFromCadAreaContribuicaoByNumeroCI(int numeroCI, ArrayList<CadEntity> lsArea) {
		double dAreaLocal = 0.0;
		for(CadEntity oEnt1 : lsArea) {
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
	
}
