/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupEditCommands.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/01/2025
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

package br.com.tlmv.aicadxapp.frm.popup;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class PopupEdit2 implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {	
			//MENU: EDIT2
			//
			this.mnu = new JMenu(AppDefs.MNU_EDIT2);

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_UNDO,
				AppDefs.ACTION_EDIT2_UNDO,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_REDO,
				AppDefs.ACTION_EDIT2_REDO,
				listener) );

			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_ERASE,
				AppDefs.ACTION_EDIT2_ERASE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_EXPLODE,
				AppDefs.ACTION_EDIT2_EXPLODE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_UNILINE,
				AppDefs.ACTION_EDIT2_UNILINE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_MATCHPROP,
				AppDefs.ACTION_EDIT2_MATCHPROP,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_COPY,
				AppDefs.ACTION_EDIT2_COPY,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_RECT_ARRAY,
				AppDefs.ACTION_EDIT2_RECT_ARRAY,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_POLAR_ARRAY,
				AppDefs.ACTION_EDIT2_POLAR_ARRAY,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_MIRROR,
				AppDefs.ACTION_EDIT2_MIRROR,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_MOVE,
				AppDefs.ACTION_EDIT2_MOVE,
				listener) );

			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_SCALE,
				AppDefs.ACTION_EDIT2_SCALE,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_ROTATE,
				AppDefs.ACTION_EDIT2_ROTATE,
				listener) );
				
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_SELECT,
				AppDefs.ACTION_EDIT2_SELECT,
				listener) );

			mnubar.add(mnu);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createPopupMenu(JPopupMenu mnuPopup, ActionListener listener) 
	{
		return AppDefs.RSOK;		
	}
	
	public boolean isVisible()
	{
		boolean bVisible = this.mnu.isVisible();
		return bVisible;
	}
	
	public void setVisible(boolean bVisible)
	{
		this.mnu.setVisible(bVisible);
	}

}
