/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ControleBacklistTableCellEditor.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/08/2025
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

package br.com.tlmv.aicadxmod.transmar.frm.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.transmar.cad.CadControleBacklistItemTransMarOData;
import br.com.tlmv.aicadxmod.transmar.cad.CadControleBacklistTransMar;
import br.com.tlmv.aicadxmod.transmar.frm.ControleBacklistTransMarPanel;
import br.com.tlmv.aicadxmod.transmar.model.ControleBacklistModel;

public class ControleBacklistTableCellEditor implements TableCellEditor, ActionListener, ItemListener
{
//Private
	private ControleBacklistTableCellResultListener listener = null;
	private ControleBacklistTransMarPanel parent = null;
	
	private JPanel panel = null;
	
	private int rownum = -1;
	private int colnum = -1;
	private Object oldVal = null;
	private Object newVal = null;
	
//Public
	
	public ControleBacklistTableCellEditor(ControleBacklistTransMarPanel parent, ControleBacklistTableCellResultListener listener)
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
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		this.panel = new JPanel();
		
		BorderLayout layout = new BorderLayout();
		this.panel.setLayout(layout);

		this.rownum = row;
		this.colnum = column;
		this.oldVal = value;
		this.newVal = null;
		
		CadControleBacklistTransMar oControleBacklist = this.parent.getControleBacklist();
		
		ControleBacklistModel oControleBacklistModel = this.parent.getControleBacklistModel();

		CadControleBacklistItemTransMarOData oCadControleBacklistItem = oControleBacklistModel.getControleBacklistItemAt(row);		

		ColunaTabelaVO oCol = (ColunaTabelaVO)oControleBacklistModel.getHeaderAt(column);
		int dprec = oCol.getDprec();
		
		Class c = oCol.getDataType();
		
		String className = c.getSimpleName();

		String warnmsg = String.format("Row:%s;Col:%s;Value:%s;Class:%s;", row, column, value, className);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());		

		Color color = AppDefs.DRMEMORIACALCULO_ROOTITEM_COLOR1;

		this.panel.setBackground(color);
				
		if( "Boolean".equals(className) ) {
			Boolean bVal = false;
			try {
				bVal = (Boolean)value;
			}
			catch(Exception e) { 
				bVal = false;
			}

			String cmdAction = String.format("CHK^%s^%s", this.rownum, this.colnum);
			
			JCheckBox chk = FormControlUtil.newCheckBox(bVal, 20, 20, true, true);
			chk.setActionCommand(cmdAction);
			chk.addActionListener(this);
			this.panel.add(chk, BorderLayout.CENTER);
		}
		else if( "String".equals(className) ) {
			String strVal = new String("");
			try {
				strVal = (String)value;
			}
			catch(Exception e) { 
				strVal = "???";
			}

			String cmdAction = String.format("TXT^%s^%s", this.rownum, this.colnum);
			
			JTextField txt = FormControlUtil.newTextField(strVal, 0, 0, 150, 20, true, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			txt.setActionCommand(cmdAction);
			txt.setFont(f);
			txt.setHorizontalAlignment(JLabel.LEFT);
			txt.addActionListener(this);
			this.panel.add(txt, BorderLayout.CENTER);
		}
		else if( "Integer".equals(className) ) {
			String strVal = new String("");
			try {
				strVal = Integer.toString((Integer)value);
			}
			catch(Exception e) { 
				strVal = "";
			}

			String cmdAction = String.format("TXT^%s^%s", this.rownum, this.colnum);
			
			JTextField txt = FormControlUtil.newTextField(strVal, 0, 0, 150, 20, true, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			txt.setActionCommand(cmdAction);
			txt.setFont(f);
			txt.setHorizontalAlignment(JLabel.LEFT);
			txt.addActionListener(this);
			this.panel.add(txt, BorderLayout.CENTER);
		}
		else if( "Long".equals(className) ) {
			String strVal = new String("");
			try {
				strVal = Long.toString((Long)value);
			}
			catch(Exception e) { 
				strVal = "";
			}

			String cmdAction = String.format("TXT^%s^%s", this.rownum, this.colnum);
			
			JTextField txt = FormControlUtil.newTextField(strVal, 0, 0, 150, 20, true, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			txt.setActionCommand(cmdAction);
			txt.setFont(f);
			txt.setHorizontalAlignment(JLabel.LEFT);
			txt.addActionListener(this);
			this.panel.add(txt, BorderLayout.CENTER);
		}
		else if( "Double".equals(className) ) {
			String strVal = new String("");
			try {
				if(dprec == AppDefs.DEF_DECPREC_DBL3)
					strVal = nf3.format((Double)value);
				else if(dprec == AppDefs.DEF_DECPREC_DBL6)
					strVal = nf6.format((Double)value);
			}
			catch(Exception e) { 
				strVal = "";
			}

			String cmdAction = String.format("TXT^%s^%s", this.rownum, this.colnum);
			
			JTextField txt = FormControlUtil.newTextField(strVal, 0, 0, 150, 20, true, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			txt.setActionCommand(cmdAction);
			txt.setFont(f);
			txt.setHorizontalAlignment(JLabel.LEFT);
			txt.addActionListener(this);
			this.panel.add(txt, BorderLayout.CENTER);
		}
		
		return this.panel;
	}

	@Override
	public Object getCellEditorValue() {
		return this.newVal;
	}
	
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		JTable oTable = (JTable)anEvent.getSource(); 
		
		ControleBacklistModel oControleBacklistModel = this.parent.getControleBacklistModel();

		int row = oTable.getSelectedRow();
		if(row == -1) return false;
		
		int column = oTable.getSelectedColumn();
		if(column == -1) return false;
		
		ColunaTabelaVO oCol = (ColunaTabelaVO)oControleBacklistModel.getHeaderAt(column);
		boolean bResult = oCol.isEditable();
		
		CadControleBacklistItemTransMarOData oControleBacklistItem = oControleBacklistModel.getControleBacklistItemAt(row);
		//if( oControleBacklistItem.isFinish() && !oCol.isFinishVisible() )
		//	bResult = false;

		String warnmsg = String.format("IS_EDITABLE(COL=%s):%s;", column, ( bResult ) ? "TRUE" : "FALSE" );
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());
		
		return bResult;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		JTable oTable = (JTable)anEvent.getSource(); 

		ControleBacklistModel oControleBacklistModel = this.parent.getControleBacklistModel();

		int row = oTable.getSelectedRow();
		if(row == -1) return false;

		int column = oTable.getSelectedColumn();
		if(column != -1) return false;

		ColunaTabelaVO oCol = (ColunaTabelaVO)oControleBacklistModel.getHeaderAt(column);
		boolean bResult = oCol.isEditable();
		
//		CadControleBacklistItemTransMarOData oControleBacklistItem = oControleBacklistModel.getControleBacklistModelAt(row);
//		if( oControleBacklistModel.isFinish() && !oCol.isFinishVisible() )
//			bResult = false;

		String warnmsg = String.format("IS_EDITABLE(COL=%s):%s;", column, ( bResult ) ? "TRUE" : "FALSE" );
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

		return bResult;
	}

	@Override
	public boolean stopCellEditing() {
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
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String cmd = e.getActionCommand();
		//System.out.println(cmd);

		String[] arr = StringUtil.split(cmd, '^');

		String strType = arr[0];

		String strNewVal = "";
		Boolean bNewVal = false;
		
		this.rownum = StringUtil.safeInt(arr[1]); 
		this.colnum = StringUtil.safeInt(arr[2]); 

		if( "CHK".equals(strType) ) {
			JCheckBox chk = (JCheckBox)e.getSource();
			bNewVal = chk.isSelected();
		}
		else if( "TXT".equals(strType) ) {
			JTextField txt = (JTextField)e.getSource();
			strNewVal = txt.getText();
		}

		ColunaTabelaVO oCol = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO[this.colnum];		
		
		Class c = oCol.getDataType();

		String className = c.getSimpleName();
		if( "Boolean".equals(className) ) {
			this.newVal = bNewVal;
		}
		else if( "String".equals(className) ) {
			this.newVal = strNewVal;
		}
		else if( "Integer".equals(className) ) {
			this.newVal = StringUtil.safeInt(strNewVal);
		}
		else if( "Long".equals(className) ) {
			this.newVal = StringUtil.safeLng(strNewVal);
		}
		else if( "Double".equals(className) ) {
			this.newVal = StringUtil.safeDbl(nf3, strNewVal);
		}
		
		if(this.listener != null) {
			ControleBacklistTableCellResultEvent oResult = new ControleBacklistTableCellResultEvent(
				this.rownum,
				this.colnum,
				this.oldVal,
				this.newVal); 
			this.listener.actionControleBacklistTableCellResultListener(oResult);
		}
		
		this.panel.removeAll();
		this.panel.setVisible(false);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getStateChange() == ItemEvent.SELECTED) {
//			JComboBox cbx = (JComboBox)e.getSource();
//			
//	    	if( AppDefs.DEF_CBX_ACTION_LAYEREXPLORER_LTYPE.equals(cbx.getActionCommand()) ) {
//	    		BorderStrokeVO oLtype = (BorderStrokeVO)cbx.getSelectedItem();
//	    		this.newVal = oLtype;
//			}
		}
	}

	/* Getters/Setters */

	public ControleBacklistTableCellResultListener getListener() {
		return listener;
	}

	public void setListener(ControleBacklistTableCellResultListener listener) {
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
