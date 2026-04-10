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

public class ToolbarFile implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: FILE
			//
			JButton btnNew = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_NEW, AppDefs.ACTION_FILE_NEW, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_NEW);
			mnuToolbar.add(btnNew);

			JButton btnOpen = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_OPEN, AppDefs.ACTION_FILE_OPEN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_OPEN);
			mnuToolbar.add(btnOpen);

			JButton btnClose = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_CLOSE, AppDefs.ACTION_FILE_CLOSE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_CLOSE);
			mnuToolbar.add(btnClose);

			JButton btnSave = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_SAVE, AppDefs.ACTION_FILE_SAVE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SAVE);
			mnuToolbar.add(btnSave);

			JButton btnSaveAs = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_SAVEAS, AppDefs.ACTION_FILE_SAVEAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SAVEAS);
			mnuToolbar.add(btnSaveAs);
			
			JButton btnLoadDbase = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_LOADDBASE, AppDefs.ACTION_FILE_LOADDBASE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_LOADDBASE);
			mnuToolbar.add(btnLoadDbase);

			JButton btnSaveDbase = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_SAVEDBASE, AppDefs.ACTION_FILE_SAVEDBASE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SAVEDBASE);
			mnuToolbar.add(btnSaveDbase);
			
			JButton btnLoadNoSql = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_LOADNOSQL, AppDefs.ACTION_FILE_LOADNOSQL, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_LOADNOSQL);
			mnuToolbar.add(btnLoadNoSql);

			JButton btnSaveNoSql = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_SAVENOSQL, AppDefs.ACTION_FILE_SAVENOSQL, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SAVENOSQL);
			mnuToolbar.add(btnSaveNoSql);
			
			JButton btnLoadSample = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_LOADSAMPLE, AppDefs.ACTION_FILE_LOADSAMPLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_LOADSAMPLE);
			mnuToolbar.add(btnLoadSample);

			JButton btnDxfIn = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_DXFIN, AppDefs.ACTION_FILE_DXFIN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_DXFIN);
			mnuToolbar.add(btnDxfIn);

			JButton btnDxfOut = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_DXFOUT, AppDefs.ACTION_FILE_DXFOUT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_DXFOUT);
			mnuToolbar.add(btnDxfOut);

			JButton btnIfcIn = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_IFCIN, AppDefs.ACTION_FILE_IFCIN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_IFCIN);
			mnuToolbar.add(btnIfcIn);

			JButton btnIfcOut = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_IFCOUT, AppDefs.ACTION_FILE_IFCOUT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_IFCOUT);
			mnuToolbar.add(btnIfcOut);

			JButton btnInsertImage = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_INSERTIMAGE, AppDefs.ACTION_FILE_INSERTIMAGE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_INSERTIMAGE);
			mnuToolbar.add(btnInsertImage);

			JButton btnSetup = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_SETUP, AppDefs.ACTION_FILE_SETUP, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_SETUP);
			mnuToolbar.add(btnSetup);

			JButton btnMargem = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_MARGEM, AppDefs.ACTION_FILE_MARGEM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_MARGEM);
			mnuToolbar.add(btnMargem);

			JButton btnPropMargem = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_PROPMARGEM, AppDefs.ACTION_FILE_PROPMARGEM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_PROPMARGEM);
			mnuToolbar.add(btnPropMargem);

			JButton btnPrint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_PRINT, AppDefs.ACTION_FILE_PRINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_PRINT);
			mnuToolbar.add(btnPrint);

			JButton btnExit = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_FILE_EXIT, AppDefs.ACTION_FILE_EXIT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_FILE_EXIT);
			mnuToolbar.add(btnExit);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
