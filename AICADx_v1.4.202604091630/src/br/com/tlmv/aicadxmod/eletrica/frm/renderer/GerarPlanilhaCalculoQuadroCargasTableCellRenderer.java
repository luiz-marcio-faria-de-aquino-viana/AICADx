/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GerarPlanilhaCalculoQuadroCargasTableCellRenderer.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/01/2026
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

package br.com.tlmv.aicadxmod.eletrica.frm.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadCircuitoQuadroCargasEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadQuadroCargasEletrica;
import br.com.tlmv.aicadxmod.eletrica.frm.GerarPlanilhaCalculoQuadroCargasPanel;
import br.com.tlmv.aicadxmod.eletrica.model.QuadroCargasModel;

public class GerarPlanilhaCalculoQuadroCargasTableCellRenderer implements TableCellRenderer
{
//Private
	private GerarPlanilhaCalculoQuadroCargasPanel parent = null;
	
//Public
	
	public GerarPlanilhaCalculoQuadroCargasTableCellRenderer(GerarPlanilhaCalculoQuadroCargasPanel parent)
	{
		this.parent = parent; 
	}
	
	/* Getters/Setters */
	
	@Override
	public Component getTableCellRendererComponent(
		JTable table, 
		Object value, 
		boolean isSelected, 
		boolean hasFocus,
		int row, 
		int column) 
	{
		JPanel panel = new JPanel();
		
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		
		CadQuadroCargasEletrica oQuadroCargas = this.parent.getQuadroCargasEletrica();
		
		QuadroCargasModel oQuadroCargasModel = this.parent.getModel();

		CadCircuitoQuadroCargasEletricaOData oQuadroCargasItem = oQuadroCargasModel.getQuadroCargasItemAt(row);		
		
		ColunaTabelaVO oCol = (ColunaTabelaVO)oQuadroCargasModel.getHeaderAt(column);
		int dprec = oCol.getDprec();
		
		Class c = oCol.getDataType();
			
		String className = c.getSimpleName();		

		String warnmsg = String.format("Row:%s;Col:%s;Value:%s;Class:%s;", row, column, value, className);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());		
		
		Color color = AppDefs.ELQUADROCARGAS_EDITABLECOLUMN_COLOR1;
		if( !oCol.isEditable() )
			color = AppDefs.ELQUADROCARGAS_STATICCOLUMN_COLOR1;
		
		panel.setBackground(color);
		
		boolean bVisible = true;
		
		if( "Boolean".equals(className) ) {
			Boolean bVal = false;
			try {
				if( bVisible && value != null) 
					bVal = (Boolean)value;
			}
			catch(Exception e) { 
				bVal = false;
			}

			JCheckBox chk = FormControlUtil.newCheckBox(bVal, 20, 20, true, true);
			panel.add(chk, BorderLayout.CENTER);
		}
		else if( "String".equals(className) ) {
			String strVal = new String("");
			try {
				if(  bVisible && value != null) 
					strVal = (String)value;
			}
			catch(Exception e) { 
				strVal = "???";
			}
			
			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			panel.add(lbl, BorderLayout.CENTER);
		}
		else if( "Integer".equals(className) ) {
			String strVal = new String("");
			try {
				if( bVisible && value != null)
					strVal = Integer.toString((Integer)value);
			}
			catch(Exception e) { 
				strVal = "";
			}

			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			panel.add(lbl, BorderLayout.CENTER);
		}
		else if( "Long".equals(className) ) {
			String strVal = new String("");
			try {
				if( bVisible && value != null) { 
						strVal = Long.toString((Long)value);
				}
			}
			catch(Exception e) { 
				strVal = "";
			}

			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			panel.add(lbl, BorderLayout.CENTER);
		}
		else if( "Double".equals(className) ) {
			String strVal = new String("");
			try {
				if( bVisible && value != null) {
					strVal = StringUtil.toStrValue((Double)value, dprec);
				}
			}
			catch(Exception e) { 
				strVal = "";
			}

			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			panel.add(lbl, BorderLayout.CENTER);
		}
		
		return panel;
	}

}
