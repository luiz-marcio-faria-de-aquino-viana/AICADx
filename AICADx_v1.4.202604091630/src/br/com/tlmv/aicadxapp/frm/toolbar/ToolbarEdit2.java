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

public class ToolbarEdit2 implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBAREDIT2
			//
			JButton btnUndo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_UNDO, AppDefs.ACTION_EDIT2_UNDO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_UNDO);
			mnuToolbar.add(btnUndo);

			JButton btnRedo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_REDO, AppDefs.ACTION_EDIT2_REDO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_REDO);
			mnuToolbar.add(btnRedo);

			JButton btnMatchProp = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_MATCHPROP, AppDefs.ACTION_EDIT2_MATCHPROP, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_MATCHPROP);
			mnuToolbar.add(btnMatchProp);

			JButton btnErase = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_ERASE, AppDefs.ACTION_EDIT2_ERASE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_ERASE);
			mnuToolbar.add(btnErase);

			JButton btnDxfExplode = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_EXPLODE, AppDefs.ACTION_EDIT2_EXPLODE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_EXPLODE);
			mnuToolbar.add(btnDxfExplode);

			JButton btnUniline = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_UNILINE, AppDefs.ACTION_EDIT2_UNILINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_UNILINE);
			mnuToolbar.add(btnUniline);

			JButton btnCopy = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_COPY, AppDefs.ACTION_EDIT2_COPY, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_COPY);
			mnuToolbar.add(btnCopy);

			JButton btnRectArray = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_RECT_ARRAY, AppDefs.ACTION_EDIT2_RECT_ARRAY, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_RECT_ARRAY);
			mnuToolbar.add(btnRectArray);

			JButton btnPolarArray = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_POLAR_ARRAY, AppDefs.ACTION_EDIT2_POLAR_ARRAY, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_POLAR_ARRAY);
			mnuToolbar.add(btnPolarArray);

			JButton btnMirror = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_MIRROR, AppDefs.ACTION_EDIT2_MIRROR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_MIRROR);
			mnuToolbar.add(btnMirror);

			JButton btnMove = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_MOVE, AppDefs.ACTION_EDIT2_MOVE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_MOVE);
			mnuToolbar.add(btnMove);

			JButton btnScale = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_SCALE, AppDefs.ACTION_EDIT2_SCALE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_SCALE);
			mnuToolbar.add(btnScale);

			JButton btnSelect = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_SELECT, AppDefs.ACTION_EDIT2_SELECT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_SELECT);
			mnuToolbar.add(btnSelect);

			//JButton btnRotate = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EDIT2_ROTATE, AppDefs.ACTION_EDIT2_ROTATE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_ROTATE);
			//mnuToolbar.add(btnRotate);
						
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
