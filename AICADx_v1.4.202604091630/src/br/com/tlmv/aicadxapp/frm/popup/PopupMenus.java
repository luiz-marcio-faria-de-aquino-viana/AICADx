/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupMenus.java
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
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class PopupMenus implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: MENUS
			//
			this.mnu = new JMenu(AppDefs.MNU_MENUS);
	
			if( AppDefs.MNU_ENABLED_ARQMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_ARQMENU,
					AppDefs.ACTION_MENUS_ARQMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_FORMENU ) {
				//mnu.add(FormControlUtil.newMenuItem(
				//	AppDefs.MNU_MENUS_FORMENU,
				//	AppDefs.ACTION_MENUS_FORMENU,
				//	listener) );
			}
			
			if( AppDefs.MNU_ENABLED_FUMENU ) {
				//mnu.add(FormControlUtil.newMenuItem(
				//	AppDefs.MNU_MENUS_FUMENU,
				//	AppDefs.ACTION_MENUS_FUMENU,
				//	listener) );
			}
			
			if( AppDefs.MNU_ENABLED_EEMENU ) {
				//mnu.add(FormControlUtil.newMenuItem(
				//	AppDefs.MNU_MENUS_EEMENU,
				//	AppDefs.ACTION_MENUS_EEMENU,
				//	listener) );
			}
			
			if( AppDefs.MNU_ENABLED_ELMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_ELMENU,
					AppDefs.ACTION_MENUS_ELMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_ESMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_ESMENU,
					AppDefs.ACTION_MENUS_ESMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_APMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_APMENU,
					AppDefs.ACTION_MENUS_APMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_RPDMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_RPDMENU,
					AppDefs.ACTION_MENUS_RPDMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_HMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_HMENU,
					AppDefs.ACTION_MENUS_HMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_INCMENU ) {
				//mnu.add(FormControlUtil.newMenuItem(
				//	AppDefs.MNU_MENUS_INCMENU,
				//	AppDefs.ACTION_MENUS_INCMENU,
				//	listener) );
			}
			
			if( AppDefs.MNU_ENABLED_GMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_GMENU,
					AppDefs.ACTION_MENUS_GMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_IEMENU ) {
				//mnu.add(FormControlUtil.newMenuItem(
				//	AppDefs.MNU_MENUS_IEMENU,
				//	AppDefs.ACTION_MENUS_IEMENU,
				//	listener) );
			}
			
			if( AppDefs.MNU_ENABLED_TEMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_TEMENU,
					AppDefs.ACTION_MENUS_TEMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_ARMENU ) {
				//mnu.add(FormControlUtil.newMenuItem(
				//	AppDefs.MNU_MENUS_ARMENU,
				//	AppDefs.ACTION_MENUS_ARMENU,
				//	listener) );
			}
			
			if( AppDefs.MNU_ENABLED_TOPOMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_TOPOMENU,
					AppDefs.ACTION_MENUS_TOPOMENU,
					listener) );
			}
			
			if( AppDefs.MNU_ENABLED_TMARMENU ) {
				mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_MENUS_TMARMENU,
					AppDefs.ACTION_MENUS_TMARMENU,
					listener) );
			}
	
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
