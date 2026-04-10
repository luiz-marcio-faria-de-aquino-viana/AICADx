/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupSettings.java
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

public class PopupSettings implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: SETTINGS
			//
			this.mnu = new JMenu(AppDefs.MNU_SETTINGS);

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_SETTINGS_GRIDONOFF,
				AppDefs.ACTION_SETTINGS_GRIDONOFF,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_SETTINGS_SNAPONOFF,
				AppDefs.ACTION_SETTINGS_SNAPONOFF,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_SETTINGS_ORTHOONOFF,
				AppDefs.ACTION_SETTINGS_ORTHOONOFF,
				listener) );
		
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_SETTINGS_LOCK,
				AppDefs.ACTION_SETTINGS_LOCK,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_SETTINGS_UNLOCKALL,
				AppDefs.ACTION_SETTINGS_UNLOCKALL,
				listener) );

//			mnu.add(new JSeparator());
//		
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_DRAWINGAIDS,
//				AppDefs.MNU_SETTINGS_DRAWINGAIDS,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_UCSICONONOFF,
//				AppDefs.MNU_SETTINGS_UCSICONONOFF,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_UCSENTITY,
//				AppDefs.MNU_SETTINGS_UCSENTITY,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_UCSORIGIN,
//				AppDefs.MNU_SETTINGS_UCSORIGIN,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_UCSROTATION,
//				AppDefs.MNU_SETTINGS_UCSROTATION,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_UCSWORLD,
//				AppDefs.MNU_SETTINGS_UCSWORLD,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_MIRRTEXT,
//				AppDefs.MNU_SETTINGS_MIRRTEXT,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_PICKBOX1,
//				AppDefs.MNU_SETTINGS_PICKBOX1,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_PICKBOX5,
//				AppDefs.MNU_SETTINGS_PICKBOX5,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_DIMDEC3,
//				AppDefs.MNU_SETTINGS_DIMDEC3,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_DIMDEC2,
//				AppDefs.MNU_SETTINGS_DIMDEC2,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_DIMDEC1,
//				AppDefs.MNU_SETTINGS_DIMDEC1,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_DIMDEC0,
//				AppDefs.MNU_SETTINGS_DIMDEC0,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_DIMDEC,
//				AppDefs.MNU_SETTINGS_DIMDEC,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_OBJECTSNAP,
//				AppDefs.MNU_SETTINGS_OBJECTSNAP,
//				listener) );
//			
//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_TBCONFIG,
//				AppDefs.MNU_SETTINGS_TBCONFIG,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_PROMPTHISTORYWINDOW,
//				AppDefs.MNU_SETTINGS_PROMPTHISTORYWINDOW,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_STATUSBAR,
//				AppDefs.MNU_SETTINGS_STATUSBAR,
//				listener) );
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_SETTINGS_SCROLLBAR,
//				AppDefs.MNU_SETTINGS_SCROLLBAR,
//				listener) );
	
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
