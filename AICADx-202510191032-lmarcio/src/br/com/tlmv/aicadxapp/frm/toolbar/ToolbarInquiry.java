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

public class ToolbarInquiry implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARINQUIRY
			//
			JButton btnDist = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_INQUIRY_DISTANCE, AppDefs.ACTION_INQUIRY_DIST, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_INQUIRY_DISTANCE);
			mnuToolbar.add(btnDist);

			JButton btnSearch = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_INQUIRY_SEARCH, AppDefs.ACTION_INQUIRY_SEARCH, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_INQUIRY_SEARCH);
			mnuToolbar.add(btnSearch);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
