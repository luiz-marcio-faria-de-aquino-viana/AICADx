/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ToolbarDraw3D.java
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

public class ToolbarDraw3D implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARDRAW3D
			//
			JButton btnBox3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_BOX3D, AppDefs.ACTION_DRAW3D_BOX3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_BOX3D);
			mnuToolbar.add(btnBox3D);

			JButton btnCilinder3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_CILINDER3D, AppDefs.ACTION_DRAW3D_CILINDER3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_CILINDER3D);
			mnuToolbar.add(btnCilinder3D);

			JButton btnCone3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_CONE3D, AppDefs.ACTION_DRAW3D_CONE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_CONE3D);
			mnuToolbar.add(btnCone3D);

			JButton btnTroncoCone3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_TRONCOCONE3D, AppDefs.ACTION_DRAW3D_TRONCOCONE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_TRONCOCONE3D);
			mnuToolbar.add(btnTroncoCone3D);

			JButton btnTorus3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_TORUS3D, AppDefs.ACTION_DRAW3D_TORUS3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_TORUS3D);
			mnuToolbar.add(btnTorus3D);

			JButton btnSphere3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_SPHERE3D, AppDefs.ACTION_DRAW3D_SPHERE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_SPHERE3D);
			mnuToolbar.add(btnSphere3D);

			JButton btnFace3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_FACE3D, AppDefs.ACTION_DRAW3D_FACE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_FACE3D);
			mnuToolbar.add(btnFace3D);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
