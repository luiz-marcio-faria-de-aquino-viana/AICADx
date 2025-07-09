/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerTableCellEditor.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.LayerExplorerPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import javafx.scene.layout.BorderStrokeStyle;

public class LayerTableCellEditor implements TableCellEditor, ActionListener, ItemListener
{
//Private
	private LayerTableCellResultListener listener = null;
	private LayerExplorerPanel parent = null;
	
	private int rownum = -1;
	private int colnum = -1;
	private Object oldVal = null;
	private Object newVal = null;
	
//Public
	
	public LayerTableCellEditor(LayerExplorerPanel parent, LayerTableCellResultListener listener)
	{
		this.parent = parent;
		this.listener = listener;
	}
	
	/* Methodes */
	
	@Override
	public Component getTableCellEditorComponent(
		JTable table, 
		Object value, 
		boolean isSelected, 
		int row, 
		int column) 
	{
		JPanel panel = new JPanel();
		
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);

		this.rownum = row;
		this.colnum = column;
		this.oldVal = value;
		this.newVal = value;
		
		if(column == AppDefs.LAYEREXPLORER_LIST_ACTIVE)
		{
			Boolean bAction = (Boolean)value;
			
			JCheckBox chk = FormControlUtil.newCheckBox(bAction, 20, 20, true, true);
			chk.setActionCommand(Integer.toString(AppDefs.LAYEREXPLORER_LIST_ACTIVE));
			chk.addActionListener(this);
			panel.add(chk, BorderLayout.CENTER);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_NAME)
		{
			String strVal = (String)value;
						
			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			lbl.setHorizontalAlignment(JLabel.LEFT);
			panel.add(lbl, BorderLayout.CENTER);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LTYPE)
		{
			BorderStrokeVO oLtype = (BorderStrokeVO)value;
			String strVal = oLtype.getName();
			
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);

			JComboBox cbx = FormControlUtil.newComboBox(AppDefs.ARR_LTYPE_TABLE, row, column, true, AppDefs.DEF_CBX_ACTION_LAYEREXPLORER_LTYPE);
			FormControlUtil.setCbxByText(cbx, AppDefs.ARR_LTYPE_TABLE, strVal);
			cbx.setFont(f);
			cbx.addItemListener(this);
			panel.add(cbx, BorderLayout.CENTER);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_COLOR)
		{
			ColorVO oColor = (ColorVO)value;			
			Color cDefaultVal = (Color)oColor.getColor();				
			Color cResVal = JColorChooser.showDialog(panel, "Color Chooser", cDefaultVal);
			if(cResVal != null) {
				panel.setBackground(cResVal);
				
				oColor = new ColorVO(cResVal);
				this.newVal = oColor;
			}
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF)
		{
			Boolean bLayerOn = (Boolean)value;
			
			JCheckBox chk = FormControlUtil.newCheckBox(bLayerOn, 20, 20, true, true);
			chk.setActionCommand(Integer.toString(AppDefs.LAYEREXPLORER_LIST_LAYERONOFF));
			chk.addActionListener(this);
			panel.add(chk, BorderLayout.CENTER);			
		}
		
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return this.newVal;
	}
	
	@Override
	public boolean isCellEditable(EventObject anEvent) 
	{
		boolean bResult = false;
		
		JTable oTable = (JTable)anEvent.getSource(); 

		int column = oTable.getSelectedColumn();
		if(column == AppDefs.LAYEREXPLORER_LIST_ACTIVE) {
			//bResult = true;
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_NAME) {
			//TODO:
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LTYPE) {
			bResult = true;
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_COLOR) {
			bResult = true;
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF) {
			bResult = true;
		}
		return bResult;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		boolean bResult = false;
		
		JTable oTable = (JTable)anEvent.getSource(); 

		int column = oTable.getSelectedColumn();
		if(column == AppDefs.LAYEREXPLORER_LIST_ACTIVE) {
			//bResult = true;
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_NAME) {
			//TODO:
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LTYPE) {
			bResult = true;
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_COLOR) {
			bResult = true;
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF) {
			bResult = true;
		}
		return bResult;
	}

	@Override
	public boolean stopCellEditing() {
		if(this.listener != null) {
			LayerTableCellResultEvent oResult = new LayerTableCellResultEvent(
				rownum,
				colnum,
				oldVal,
				newVal); 
			this.listener.actionLayerTableCellResultListener(oResult);
		}
		return true;
	}

	@Override
	public void cancelCellEditing() { }

	@Override
	public void addCellEditorListener(CellEditorListener l) { }

	@Override
	public void removeCellEditorListener(CellEditorListener l) { }
	
	/* LISTENERS */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmd = StringUtil.safeInt(e.getActionCommand());
		
		if(cmd == AppDefs.LAYEREXPLORER_LIST_ACTIVE) {
			JCheckBox chk = (JCheckBox)e.getSource();
			this.newVal = chk.isSelected();
		}
		else if(cmd == AppDefs.LAYEREXPLORER_LIST_NAME) {
			//TODO:
		}
		else if(cmd == AppDefs.LAYEREXPLORER_LIST_LTYPE) {
			//TODO:
		}
		else if(cmd == AppDefs.LAYEREXPLORER_LIST_COLOR) {
			//TODO:
		}
		else if(cmd == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF) {
			JCheckBox chk = (JCheckBox)e.getSource();
			this.newVal = chk.isSelected();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getStateChange() == ItemEvent.SELECTED) 
		{			
			JComboBox cbx = (JComboBox)e.getSource();
			
	    	if( AppDefs.DEF_CBX_ACTION_LAYEREXPLORER_LTYPE.equals(cbx.getActionCommand()) ) {
	    		BorderStrokeVO oLtype = (BorderStrokeVO)cbx.getSelectedItem();
	    		this.newVal = oLtype;
			}
		}
	}

	/* Getters/Setters */

	public LayerTableCellResultListener getListener() {
		return listener;
	}

	public void setListener(LayerTableCellResultListener listener) {
		this.listener = listener;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public int getColnum() {
		return colnum;
	}

	public void setColnum(int colnum) {
		this.colnum = colnum;
	}

	public Object getOldVal() {
		return oldVal;
	}

	public void setOldVal(Object oldVal) {
		this.oldVal = oldVal;
	}

	public Object getNewVal() {
		return newVal;
	}

	public void setNewVal(Object newVal) {
		this.newVal = newVal;
	}
	
}
