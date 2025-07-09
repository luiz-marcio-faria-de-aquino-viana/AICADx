/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DocumentViewModelEx2.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class DocumentViewModelEx2 extends DefaultTreeModel 
{
//Private

	private static final long serialVersionUID = 202504041459L;

	private GroupItemDataVO[] arrGroupItemData = AppDefs.ARR_DOCVIEW_GROUPS;
	
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
		int viewType = AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
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
		int viewType = AppDefs.DOCVIEW_GRP_SECTIONVIEWS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
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
		int viewType = AppDefs.DOCVIEW_GRP_ELEVATIONVIEWS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
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
		int viewType = AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
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
		int viewType = AppDefs.DOCVIEW_GRP_3DVIEWS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
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
		int viewType = AppDefs.DOCVIEW_GRP_LAYERS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
		ArrayList<CadLayerDef> ls = CadUtil.selectAllLayer(this.doc);
		for(CadLayerDef oLayerDef : ls) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oLayerDef.getObjectId()), oLayerDef.getName());
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadImages()
	{
		int viewType = AppDefs.DOCVIEW_GRP_IMAGES_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
		ArrayList<CadImageDef> ls = CadUtil.selectAllImage(this.doc);
		for(CadImageDef oImageDef : ls) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oImageDef.getObjectId()), oImageDef.getName());
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadBlocks()
	{
		int viewType = AppDefs.DOCVIEW_GRP_BLOCKS_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
		ArrayList<CadBlockDef> ls = CadUtil.selectAllBlock(this.doc);
		for(CadBlockDef oBlockDef : ls) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oBlockDef.getObjectId()), oBlockDef.getName());
			oGroupItemData.addItemData(oItemData);
		}
	}
	
	private void loadShapes()
	{
		int viewType = AppDefs.DOCVIEW_GRP_SHAPES_VAL;
		GroupItemDataVO oGroupItemData = getGroupItemData(viewType);
				
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
		
		int sz = this.arrGroupItemData.length;
		for(int i = 0; i < sz; i++) {
			GroupItemDataVO oGroupItemData = this.arrGroupItemData[i];
			
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

	/* Getters/Setters */

	public GroupItemDataVO getGroupItemData(int groupItemDataId)
	{
		int pos = groupItemDataId - AppDefs.DOCVIEW_GRP_LEVELS_VAL;
		
		GroupItemDataVO oGroupItemData = AppDefs.ARR_DOCVIEW_GROUPS[pos];
		return oGroupItemData;
	}
		
}
