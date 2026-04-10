/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupTools.java
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

public class PopupTools implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: TOOLS
			//
			this.mnu = new JMenu(AppDefs.MNU_TOOLS);
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_IMPORTTEXTSMALL,
				AppDefs.MNU_TOOLS_IMPORTTEXTSMALL,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_IMPORTTEXTMEDIUM,
				AppDefs.MNU_TOOLS_IMPORTTEXTMEDIUM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_IMPORTTEXTBIG,
				AppDefs.MNU_TOOLS_IMPORTTEXTBIG,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_TOOLS_IMPORTTEXT,
					AppDefs.MNU_TOOLS_IMPORTTEXT,
					listener) );
				
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_INDICADORES,
				AppDefs.MNU_TOOLS_INDICADORES,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_LEGENDA,
				AppDefs.MNU_TOOLS_LEGENDA,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_LEADER,
				AppDefs.MNU_TOOLS_LEADER,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO01,
				AppDefs.MNU_TOOLS_APTO01,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO02,
				AppDefs.MNU_TOOLS_APTO02,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO03,
				AppDefs.MNU_TOOLS_APTO03,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO04,
				AppDefs.MNU_TOOLS_APTO04,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO05,
				AppDefs.MNU_TOOLS_APTO05,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO06,
				AppDefs.MNU_TOOLS_APTO06,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO07,
				AppDefs.MNU_TOOLS_APTO07,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO08,
				AppDefs.MNU_TOOLS_APTO08,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO09,
				AppDefs.MNU_TOOLS_APTO09,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO10,
				AppDefs.MNU_TOOLS_APTO10,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_APTO,
				AppDefs.MNU_TOOLS_APTO,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_CONSTRUTOR,
				AppDefs.MNU_TOOLS_CONSTRUTOR,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_PREENCHIMENTO,
				AppDefs.MNU_TOOLS_PREENCHIMENTO,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_SERIE,
				AppDefs.MNU_TOOLS_SERIE,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_AREA,
				AppDefs.MNU_TOOLS_AREA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_DISTANCE,
				AppDefs.MNU_TOOLS_DISTANCE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOOLS_LIST,
				AppDefs.MNU_TOOLS_LIST,
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
