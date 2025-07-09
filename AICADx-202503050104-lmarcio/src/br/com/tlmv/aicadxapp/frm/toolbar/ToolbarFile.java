/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarZoom.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
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
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarFile implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: FILE
			//
			JButton btnNew = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_NEW, AppDefs.ACTION_FILE_NEW, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_NEW);
			mnuToolbar.add(btnNew);

			JButton btnOpen = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_OPEN, AppDefs.ACTION_FILE_OPEN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_OPEN);
			mnuToolbar.add(btnOpen);

			JButton btnClose = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_CLOSE, AppDefs.ACTION_FILE_CLOSE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_CLOSE);
			mnuToolbar.add(btnClose);

			JButton btnSave = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_SAVE, AppDefs.ACTION_FILE_SAVE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SAVE);
			mnuToolbar.add(btnSave);

			JButton btnSaveAs = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_SAVEAS, AppDefs.ACTION_FILE_SAVEAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SAVEAS);
			mnuToolbar.add(btnSaveAs);

			JButton btnPrint = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_PRINT, AppDefs.ACTION_FILE_PRINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_PRINT);
			mnuToolbar.add(btnPrint);

			JButton btnExit = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_FILE_EXIT, AppDefs.ACTION_FILE_EXIT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_EXIT);
			mnuToolbar.add(btnExit);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
