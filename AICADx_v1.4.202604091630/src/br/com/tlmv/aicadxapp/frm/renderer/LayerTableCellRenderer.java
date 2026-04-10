/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * LayerTableCellRenderer.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/01/2025
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

package br.com.tlmv.aicadxapp.frm.renderer;

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
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;

public class LayerTableCellRenderer implements TableCellRenderer
{
//Public
	
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
		
		if(column == AppDefs.LAYEREXPLORER_LIST_ACTIVE)
		{
			Boolean bVal = (Boolean)value;
			
			JCheckBox chk = FormControlUtil.newCheckBox(bVal, 20, 20, true, true);
			panel.add(chk, BorderLayout.CENTER);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_NAME)
		{
			String strVal = (String)value;
						
			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			panel.add(lbl, BorderLayout.CENTER);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LTYPE)
		{
			BorderStrokeVO oLtype = (BorderStrokeVO)value; 
			String strVal = oLtype.getName();
			
			JLabel lbl = FormControlUtil.newLabel(strVal, 150, 20, true);
			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 10);
			lbl.setFont(f);
			lbl.setHorizontalAlignment(JLabel.LEFT);
			lbl.setVerticalAlignment(JLabel.CENTER);
			panel.add(lbl, BorderLayout.CENTER);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_COLOR)
		{
			ColorVO oColor = (ColorVO)value;
			
			Color c = oColor.getColor();
			panel.setBackground(c);
		}
		else if(column == AppDefs.LAYEREXPLORER_LIST_LAYERONOFF)
		{
			Boolean bVal = (Boolean)value;
			
			JCheckBox chk = FormControlUtil.newCheckBox(bVal, 20, 20, true, true);
			panel.add(chk, BorderLayout.CENTER);
		}
		
		return panel;
	}

}
