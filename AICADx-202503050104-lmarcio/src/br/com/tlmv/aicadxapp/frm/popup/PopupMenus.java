/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupMenus.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
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

public class PopupMenus implements PopupBase 
{
//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: MENUS
			//
			JMenu mnu = new JMenu(AppDefs.MNU_MENUS);
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_ARQMENU,
				AppDefs.MNU_MENUS_ARQMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_FORMENU,
				AppDefs.MNU_MENUS_FORMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_FUMENU,
				AppDefs.MNU_MENUS_FUMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_EEMENU,
				AppDefs.MNU_MENUS_EEMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_ELMENU,
				AppDefs.MNU_MENUS_ELMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_ESMENU,
				AppDefs.MNU_MENUS_ESMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_HMENU,
				AppDefs.MNU_MENUS_HMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_INCMENU,
				AppDefs.MNU_MENUS_INCMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_GMENU,
				AppDefs.MNU_MENUS_GMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_IEMENU,
				AppDefs.MNU_MENUS_IEMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_TEMENU,
				AppDefs.MNU_MENUS_TEMENU,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MENUS_ARMENU,
				AppDefs.MNU_MENUS_ARMENU,
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

}
