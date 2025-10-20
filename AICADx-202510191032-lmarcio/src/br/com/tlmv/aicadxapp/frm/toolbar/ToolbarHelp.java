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
import javax.swing.JPanel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarHelp implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARHELP
			//
			JButton btnHelpCommandosBasicos = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_COMANDOSBASICOS, AppDefs.ACTION_HELP_COMANDOSBASICOS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_COMANDOSBASICOS);
			mnuToolbar.add(btnHelpCommandosBasicos);

			JButton btnHelpVideoTutorial101 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL101, AppDefs.ACTION_HELP_VIDEOTUTORIAL101, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL101);
			mnuToolbar.add(btnHelpVideoTutorial101);

			JButton btnHelpVideoTutorial105 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL105, AppDefs.ACTION_HELP_VIDEOTUTORIAL105, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL105);
			mnuToolbar.add(btnHelpVideoTutorial105);

			JButton btnHelpVideoTutorial110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL110, AppDefs.ACTION_HELP_VIDEOTUTORIAL110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL110);
			mnuToolbar.add(btnHelpVideoTutorial110);

			JButton btnHelpVideoTutorial120 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL120, AppDefs.ACTION_HELP_VIDEOTUTORIAL120, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL120);
			mnuToolbar.add(btnHelpVideoTutorial120);
			
			JButton btnAbout = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_ABOUT, AppDefs.ACTION_HELP_ABOUT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_ABOUT);
			mnuToolbar.add(btnAbout);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
