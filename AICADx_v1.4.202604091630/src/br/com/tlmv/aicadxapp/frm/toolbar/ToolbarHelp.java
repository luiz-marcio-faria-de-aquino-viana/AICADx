/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

			//JButton btnHelpVideoTutorial101 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL101, AppDefs.ACTION_HELP_VIDEOTUTORIAL101, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL101);
			//mnuToolbar.add(btnHelpVideoTutorial101);

			//JButton btnHelpVideoTutorial105 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL105, AppDefs.ACTION_HELP_VIDEOTUTORIAL105, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL105);
			//mnuToolbar.add(btnHelpVideoTutorial105);

			//JButton btnHelpVideoTutorial110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL110, AppDefs.ACTION_HELP_VIDEOTUTORIAL110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL110);
			//mnuToolbar.add(btnHelpVideoTutorial110);

			//JButton btnHelpVideoTutorial120 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL120, AppDefs.ACTION_HELP_VIDEOTUTORIAL120, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL120);
			//mnuToolbar.add(btnHelpVideoTutorial120);
			
			//JButton btnHelpVideoTutorial125 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL125, AppDefs.ACTION_HELP_VIDEOTUTORIAL125, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL125);
			//mnuToolbar.add(btnHelpVideoTutorial125);
			
			//JButton btnHelpVideoTutorial130 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL130, AppDefs.ACTION_HELP_VIDEOTUTORIAL130, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL130);
			//mnuToolbar.add(btnHelpVideoTutorial130);
			
			//JButton btnHelpVideoTutorial135 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL135, AppDefs.ACTION_HELP_VIDEOTUTORIAL135, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL135);
			//mnuToolbar.add(btnHelpVideoTutorial135);
			
			//JButton btnHelpVideoTutorial140 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_VIDEOTUTORIAL140, AppDefs.ACTION_HELP_VIDEOTUTORIAL140, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_VIDEOTUTORIAL140);
			//mnuToolbar.add(btnHelpVideoTutorial140);

			//JButton btnAbout = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HELP_ABOUT, AppDefs.ACTION_HELP_ABOUT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HELP_ABOUT);
			//mnuToolbar.add(btnAbout);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
