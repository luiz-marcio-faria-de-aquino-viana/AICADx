/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupFile.java
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

public class PopupFile implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: FILE
			//
			this.mnu = new JMenu(AppDefs.MNU_FILE);
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_NEW,
				AppDefs.ACTION_FILE_NEW,
				listener) );
	
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_OPEN,
				AppDefs.ACTION_FILE_OPEN,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_CLOSE,
				AppDefs.ACTION_FILE_CLOSE,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_SAVE,
				AppDefs.ACTION_FILE_SAVE,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_SAVEAS,
				AppDefs.ACTION_FILE_SAVEAS,
				listener) );
	
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_DXFIN,
				AppDefs.ACTION_FILE_DXFIN,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_DXFOUT,
				AppDefs.ACTION_FILE_DXFOUT,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_INSERTIMAGE,
				AppDefs.ACTION_FILE_INSERTIMAGE,
				listener) );
			
			mnu.add(new JSeparator());
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_SETUP,
				AppDefs.ACTION_FILE_SETUP,
				listener) );
	
			mnu.add(new JSeparator());
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_PRINT,
				AppDefs.ACTION_FILE_PRINT,
				listener) );
	
			mnu.add(new JSeparator());
	
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_FILE_PURGEALL,
			//	AppDefs.MNU_FILE_PURGEALL,
			//	listener) );
	
			//mnu.add(new JSeparator());
		
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_EXIT,
				AppDefs.ACTION_FILE_EXIT,
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
