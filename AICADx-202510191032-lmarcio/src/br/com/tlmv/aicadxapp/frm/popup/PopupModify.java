/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupModify.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
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

public class PopupModify implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: MODIFY
			//
			this.mnu = new JMenu(AppDefs.MNU_MODIFY);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_MATCHPROP,
				AppDefs.MNU_MODIFY_MATCHPROP,
				listener) );
	
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_CHANGE,
				AppDefs.MNU_MODIFY_CHANGE,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_EDITPOLYLINE,
				AppDefs.MNU_MODIFY_EDITPOLYLINE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_EDITHATCH,
				AppDefs.MNU_MODIFY_EDITHATCH,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_EDITTEXT,
				AppDefs.MNU_MODIFY_EDITTEXT,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_ADJUSTTEXT,
				AppDefs.MNU_MODIFY_ADJUSTTEXT,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_MODIFY_CHANGETEXT,
				AppDefs.MNU_MODIFY_CHANGETEXT,
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
