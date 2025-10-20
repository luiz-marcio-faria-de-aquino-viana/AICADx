/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarZoom.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.toolbar;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarDraw1 implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARDRAW1
			//
			JButton btnOffset = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_OFFSET, AppDefs.ACTION_DRAW1_OFFSET, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_OFFSET);
			mnuToolbar.add(btnOffset);

			JButton btnLine = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_LINE, AppDefs.ACTION_DRAW1_LINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_LINE);
			mnuToolbar.add(btnLine);

			JButton btnArc = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_ARC, AppDefs.ACTION_DRAW1_ARCSCA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_ARC);
			mnuToolbar.add(btnArc);

			JButton btnCircle = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_CIRCLE, AppDefs.ACTION_DRAW1_CIRCLECR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_CIRCLE);
			mnuToolbar.add(btnCircle);

			JButton btnRectangle = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_RECTANGLE, AppDefs.ACTION_DRAW1_RECTANGLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_RECTANGLE);
			mnuToolbar.add(btnRectangle);

			JButton btnPolygon = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_POLYGON, AppDefs.ACTION_DRAW1_POLYGON, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POLYGON);
			mnuToolbar.add(btnPolygon);

			JButton btnPolyline = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_POLYLINE, AppDefs.ACTION_DRAW1_POLYLINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POLYLINE);
			mnuToolbar.add(btnPolyline);

			JButton btnPoint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_POINT, AppDefs.ACTION_DRAW1_POINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POINT);
			mnuToolbar.add(btnPoint);

			JButton btnText = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_TEXT, AppDefs.ACTION_DRAW1_TEXT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_TEXT);
			mnuToolbar.add(btnText);

			JButton btnArea = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_AREA, AppDefs.ACTION_DRAW1_AREA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREA);
			mnuToolbar.add(btnArea);

			JButton btnAreaTable = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_AREATABLE, AppDefs.ACTION_DRAW1_AREATABLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREATABLE);
			mnuToolbar.add(btnAreaTable);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
