/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupEditBasic.java
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

public class PopupEdit1 implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: EDIT
			//
			this.mnu = new JMenu(AppDefs.MNU_EDIT1);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_UNDO,
				AppDefs.MNU_EDIT1_UNDO,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_REDO,
				AppDefs.MNU_EDIT1_REDO,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_SELECTALL,
				AppDefs.MNU_EDIT1_SELECTALL,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_CUT,
				AppDefs.MNU_EDIT1_CUT,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_PAST,
				AppDefs.MNU_EDIT1_PAST,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_PAST_SPETIAL,
				AppDefs.MNU_EDIT1_PAST_SPETIAL,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT1_DELETE,
				AppDefs.MNU_EDIT1_DELETE,
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
