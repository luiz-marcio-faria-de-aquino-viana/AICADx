/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupFile.java
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
				AppDefs.MNU_FILE_LOADDBASE,
				AppDefs.ACTION_FILE_LOADDBASE,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_SAVEDBASE,
				AppDefs.ACTION_FILE_SAVEDBASE,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_LOADNOSQL,
				AppDefs.ACTION_FILE_LOADNOSQL,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_SAVENOSQL,
				AppDefs.ACTION_FILE_SAVENOSQL,
				listener) );
			
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_LOADSAMPLE,
				AppDefs.ACTION_FILE_LOADSAMPLE,
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
				AppDefs.MNU_FILE_IFCIN,
				AppDefs.ACTION_FILE_IFCIN,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_IFCOUT,
				AppDefs.ACTION_FILE_IFCOUT,
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
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_MARGEM,
				AppDefs.ACTION_FILE_MARGEM,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_FILE_PROPMARGEM,
				AppDefs.ACTION_FILE_PROPMARGEM,
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
