/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerExplorerPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellEditor;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellRenderer;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.LayerExplorerResultVO;

public class LayerExplorerPanel extends JPanel implements ActionListener, LayerTableCellResultListener
{
//Private
	private static LayerExplorerFrame gParentFrame = null;
	private static LayerExplorerPanel gPanel = null;
	
	private ArrayList<CadLayerDef> lsLayer = null;		
	private CadLayerDef oDefaultLayer = null;
	private CadLayerDef oCurrLayer = null;
	
	private Object[][] arrTableData = null;
	
	private JLabel lblLayerTable = null;
	private JTable tblLayerTable = null;
	private JButton btnFechar = null;

	private ResultListener resultListener = null;

	private int rscode = AppDefs.RSCODE_LAYEREXPLORER_NONE;
	
	/* Methodes */
	
	private Object[][] loadLayers()
	{
		ArrayList<Object[]> lsResult = new ArrayList<Object[]>();
		
		int nRows = this.lsLayer.size();
		int nCols = AppDefs.HDR_TBLLAYEREXPLORER.length;
		
		for(int i = 0; i < this.lsLayer.size(); i++)
		{
			CadLayerDef o = (CadLayerDef)lsLayer.get(i);
			if( !o.isDeleted() ) {
				Object[] arr = o.toArrayData(oCurrLayer.getName());				
				lsResult.add(arr);
			}
		}
		
		this.arrTableData = lsResult.toArray(new Object[nRows][nCols]);
		return this.arrTableData;
	}
	
	private ArrayList<LayerExplorerResultVO> loadResult() 
	{
		ArrayList<LayerExplorerResultVO> lsResult = new ArrayList<LayerExplorerResultVO>();

		LayerExplorerResultVO oLayerExplorer = null;
		
		TableModel model = this.tblLayerTable.getModel(); 

		for(int row = 0; row < model.getRowCount(); row++) {
			Boolean bActive = (Boolean)model.getValueAt(row, AppDefs.LAYEREXPLORER_LIST_ACTIVE);
			String name = (String)model.getValueAt(row, AppDefs.LAYEREXPLORER_LIST_NAME);
			BorderStrokeVO oLtype = (BorderStrokeVO)model.getValueAt(row, AppDefs.LAYEREXPLORER_LIST_LTYPE);
			ColorVO oColor = (ColorVO)model.getValueAt(row, AppDefs.LAYEREXPLORER_LIST_COLOR);
			Boolean bLayerOn = (Boolean)model.getValueAt(row, AppDefs.LAYEREXPLORER_LIST_LAYERONOFF);
			
			oLayerExplorer = new LayerExplorerResultVO(
				bActive,
				name,
				oLtype,
				oColor,
				bLayerOn); 
			lsResult.add(oLayerExplorer);
		}
		
		return lsResult;
	}
	
	private void initCellEditorAndRender(JTable oTbl)
	{
		LayerTableCellRenderer renderer = new LayerTableCellRenderer();
		LayerTableCellEditor editor = new LayerTableCellEditor(this, this);

		oTbl.setRowHeight(30);
				
        TableColumnModel model = oTbl.getColumnModel();
		int sz = model.getColumnCount();
		for(int i = 0; i < sz; i++) {
			TableColumn oCol = model.getColumn(i);
			oCol.setCellRenderer(renderer);
			oCol.setCellEditor(editor);

			if(i == AppDefs.LAYEREXPLORER_LIST_ACTIVE) {
				oCol.setMinWidth(75);
				oCol.setMaxWidth(75);
				oCol.setWidth(75);
			}
			else if(i == AppDefs.LAYEREXPLORER_LIST_NAME) {
				oCol.setMinWidth(300);
			}
			else if(i == AppDefs.LAYEREXPLORER_LIST_LTYPE) {
				oCol.setMinWidth(150);
				oCol.setMaxWidth(150);
				oCol.setWidth(150);
			}
			else if(i == AppDefs.LAYEREXPLORER_LIST_COLOR) {
				oCol.setMinWidth(75);
				oCol.setMaxWidth(75);
				oCol.setWidth(75);
			}
			else if(i == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF) {
				oCol.setMinWidth(75);
				oCol.setMaxWidth(75);
				oCol.setWidth(75);
			}
		}
	}
	
	private void resetForm()
	{
		this.loadLayers();
		
		TableModel model = new javax.swing.table.DefaultTableModel(this.arrTableData, AppDefs.HDR_TBLLAYEREXPLORER); 
		this.tblLayerTable.setModel(model);		
	}
	
	private void initForm()
	{
		this.setLayout(null);

		this.loadLayers();
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W10;
		int yp = insets.top + AppDefs.SPACE_H10;
		
		int w = AppDefs.LAYEREXPLORER_FRAME_WIDTH - (AppDefs.SPACE_W10 + AppDefs.SPACE_W10);
		int h = AppDefs.LAYEREXPLORER_FRAME_HEIGHT - (AppDefs.SPACE_H50 + AppDefs.SPACE_H10 + AppDefs.LABEL_H20 + AppDefs.SPACE_H10 + AppDefs.BUTTON_H20 + AppDefs.SPACE_H10);
		
		this.lblLayerTable = FormControlUtil.newLabel("Layer list: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblLayerTable);
		yp = yp + AppDefs.LABEL_H20 + AppDefs.SPACE_H10;

		this.tblLayerTable = FormControlUtil.newTable(this, AppDefs.HDR_TBLLAYEREXPLORER, this.arrTableData, xp, yp, w, h, true);
		this.initCellEditorAndRender(this.tblLayerTable);
		yp = yp + h + AppDefs.SPACE_H10;
		xp = AppDefs.LAYEREXPLORER_FRAME_WIDTH - (AppDefs.BUTTON_W125 + AppDefs.SPACE_W10);
		
		this.btnFechar = FormControlUtil.newButton("Fechar", AppDefs.RSCODE_LAYEREXPLORER_FECHAR, xp, yp, AppDefs.BUTTON_W125, AppDefs.SPACE_H20, true, this);
		this.add(btnFechar);
	}
	
//Public 
	
	public LayerExplorerPanel()
	{
		super();
		
		LayerExplorerPanel.gPanel = this;
	}
	
	public void init(
		LayerExplorerFrame parentFrame, 
		ArrayList<CadLayerDef> lsLayer,
		CadLayerDef oDefaultLayer,
		CadLayerDef oCurrLayer,
		ResultListener resultListener)
	{
		LayerExplorerPanel.gParentFrame = parentFrame;
		
		this.lsLayer = lsLayer;
		this.oDefaultLayer = oDefaultLayer;
		this.oCurrLayer = oCurrLayer;
		
		this.resultListener = resultListener;
		
		initForm();
	}
	
	/* Methodes */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionAdicionar(ActionEvent e) 
	{
		//rscode = AppDefs.RSCODE_LAYEREXPLORER_ADICIONAR;
		//gParentFrame.actionResultListener(new ResultEvent(rscode, null));
	
		//LayerExplorerPanel.gParentFrame.dispose();
	}

	public void doActionRemover(ActionEvent e) 
	{
		int pos = this.tblLayerTable.getSelectedRow();
		if(pos != -1) {
			CadLayerDef oSrcLayer = this.lsLayer.get(pos);

			String strSrcLayer = oSrcLayer.getName();
			if( !AppDefs.LAYER_0.equals(strSrcLayer) ) {
				oSrcLayer.setDeleted(true);
				this.resetForm();
			}
		}
	}

	public void doActionFechar(ActionEvent e) 
	{
		ArrayList<LayerExplorerResultVO> lsResult = this.loadResult(); 
		
		rscode = AppDefs.RSCODE_LAYEREXPLORER_FECHAR;
		gParentFrame.actionResultListener(new ResultEvent(rscode, lsResult));
	
		LayerExplorerPanel.gParentFrame.dispose();
	}

	/* CLEAR_ACTIVE_LAYERS */
	
	public void clearActiveLayers(int rownum)
	{
		TableModel model = this.tblLayerTable.getModel(); 
		int sz = model.getRowCount();
		for(int i = 0; i < sz; i++) {
			if(rownum != i) {
				model.setValueAt(false, i, AppDefs.LAYEREXPLORER_LIST_ACTIVE);
			}
			else {
				model.setValueAt(true, i, AppDefs.LAYEREXPLORER_LIST_ACTIVE);
			}
		}
	}
	
	/* Listeners */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = StringUtil.safeInt(e.getActionCommand());

		if(action == AppDefs.RSCODE_LAYEREXPLORER_ADICIONAR) {
			doActionAdicionar(e);						
		}
		else if(action == AppDefs.RSCODE_LAYEREXPLORER_REMOVER) {
			doActionRemover(e);						
		}
		else if(action == AppDefs.RSCODE_LAYEREXPLORER_FECHAR) {
			doActionFechar(e);
		}
	}

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) 
	{
		TableModel model = this.tblLayerTable.getModel(); 

		int row = e.getRownum();
		int column = e.getColnum();

		if(column == AppDefs.LAYEREXPLORER_LIST_ACTIVE) {
			//this.clearActiveLayers(row);
			
			Boolean bResult = (Boolean)e.getNewval();
			model.setValueAt(bResult, row, column);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_NAME) {
			//TODO:
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LTYPE) {
			BorderStrokeVO oLtype = (BorderStrokeVO)e.getNewval();
			model.setValueAt(oLtype, row, column);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_COLOR) {
			ColorVO c = (ColorVO)e.getNewval();
			model.setValueAt(c, row, column);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF) {
			Boolean bResult = (Boolean)e.getNewval();
			model.setValueAt(bResult, row, column);
		}
	}

	/* Getters/Setters */

	public static LayerExplorerFrame getParentFrame() {
		return gParentFrame;
	}

	public static LayerExplorerPanel getPanel() {
		return gPanel;
	}

	public int getRSCode() {
		return rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}

	public ArrayList<CadLayerDef> getLsLayer() {
		return lsLayer;
	}

	public void setLsLayer(ArrayList<CadLayerDef> lsLayer) {
		this.lsLayer = lsLayer;
	}

	public CadLayerDef getoCurrLayer() {
		return oCurrLayer;
	}

	public void setoCurrLayer(CadLayerDef oCurrLayer) {
		this.oCurrLayer = oCurrLayer;
	}

	public CadLayerDef getDefaultLayer() {
		return oDefaultLayer;
	}

	public void setDefaultLayer(CadLayerDef oDefaultLayer) {
		this.oDefaultLayer = oDefaultLayer;
	}
	
}
