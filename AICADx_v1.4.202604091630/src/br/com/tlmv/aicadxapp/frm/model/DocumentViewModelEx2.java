/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DocumentViewModelEx2.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
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

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class DocumentViewModelEx2 extends DefaultTreeModel 
{
//Private

	private static final long serialVersionUID = 202504041459L;
	
	private DefaultMutableTreeNode root = null;

	private CadDocumentDef doc = null;
	
	/* Methodes */
	
	public void loadAll()
	{
		this.loadLevels();
		this.loadPlanViews();
		this.loadSectionViews();
		this.loadElevationViews();
		this.loadDetailViews();
		this.load3DViews();
		this.loadImages();
		this.loadBlocks();
		this.loadShapes();
		this.loadLayers();
		this.loadSheets();
	}
	
	private void loadLevels()
	{
		//TODO:
	}
	
	private void loadPlanViews()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CompView> ls = CadUtil.selectAllViewByViewType(this.doc, viewType);
		for(ICompView oView : ls) {
			String viewName = oView.getName();
			int viewId = viewName.hashCode();
			
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(viewId), viewName);
			oGroupItemData.addItemData(oItemData);
		}
	}
		
	private void loadSectionViews()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_SECTIONVIEWS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CompView> ls = CadUtil.selectAllViewByViewType(this.doc, viewType);
		for(ICompView oView : ls) {
			String viewName = oView.getName();
			int viewId = viewName.hashCode();
			
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(viewId), viewName);
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadElevationViews()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_ELEVATIONVIEWS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CompView> ls = CadUtil.selectAllViewByViewType(this.doc, viewType);
		for(ICompView oView : ls) {
			String viewName = oView.getName();
			int viewId = viewName.hashCode();
			
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(viewId), viewName);
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadDetailViews()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CompView> ls = CadUtil.selectAllViewByViewType(this.doc, viewType);
		for(ICompView oView : ls) {
			String viewName = oView.getName();
			int viewId = viewName.hashCode();
			
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(viewId), viewName);
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void load3DViews()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_3DVIEWS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CompView> ls = CadUtil.selectAllViewByViewType(this.doc, viewType);
		for(ICompView oView : ls) {
			String viewName = oView.getName();
			int viewId = viewName.hashCode();
			
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(viewId), viewName);
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadLayers()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_LAYERS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CadLayerDef> ls = CadUtil.selectAllLayer(this.doc);
		for(CadLayerDef oLayerDef : ls) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oLayerDef.getObjectId()), oLayerDef.getName());
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadImages()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_IMAGES_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CadImageDef> ls = CadUtil.selectAllImage(this.doc);
		for(CadImageDef oImageDef : ls) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oImageDef.getObjectId()), oImageDef.getName());
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadBlocks()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_BLOCKS_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<CadBlockDef> ls = CadUtil.selectAllBlock(this.doc);
		for(CadBlockDef oBlockDef : ls) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oBlockDef.getObjectId()), oBlockDef.getName());
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadShapes()
	{
		R r = AppMain.getResource();
		
		int viewType = AppDefs.DOCVIEW_GRP_SHAPES_VAL;
		GroupItemDataVO oGroupItemData = r.getGroupItemData(viewType);
				
		ArrayList<Shape> ls = CadUtil.selectAllShape(this.doc);
		for(Shape oShape : ls) {
			String shapeName = oShape.getName();
			int shapeId = shapeName.hashCode();

			ItemDataVO oItemData = new ItemDataVO(Integer.toString(shapeId), shapeName);
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadSheets()
	{
		//TODO:
	}
	
//Public
	
	public DocumentViewModelEx2(DefaultMutableTreeNode root, CadDocumentDef doc)
	{
		super(root);
		
		this.init(root, doc);		
	}
	
	/* Methodes */
	
	public void init(DefaultMutableTreeNode root, CadDocumentDef doc)
	{
		this.doc = doc;
		this.loadAll();
		
		this.root = root;
		
		R r = AppMain.getResource();		
		int sz = r.getSzGroupItemData();
		
		for(int i = 0; i < sz; i++) {
			int pos = AppDefs.DOCVIEW_GRP_LEVELS_VAL + i;
			
			GroupItemDataVO oGroupItemData = r.getGroupItemData(pos);
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(oGroupItemData.getDescricao());
			root.add(node);
			
			int szLsItemData = oGroupItemData.szLsItemData();
			for(int j = 0; j < szLsItemData; j++) {
				ItemDataVO oItemData = oGroupItemData.getItemDataAt(j);
				
				DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(oItemData.getDescricao());
				node.add(newChild);
			}
		}
	}
		
}
