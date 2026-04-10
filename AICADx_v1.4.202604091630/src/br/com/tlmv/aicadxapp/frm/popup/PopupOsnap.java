/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupOsnap.java
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

public class PopupOsnap implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		this.mnu = new JMenu(AppDefs.MNU_OSNAP);

		return AppDefs.RSOK;
	}
	
	public int createPopupMenu(JPopupMenu mnuPopup, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: OSNAP
			//
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_LAYERFILTER,
				AppDefs.MNU_OSNAP_LAYERFILTER,
				listener) );
			
			mnuPopup.add(new JSeparator());
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_ENDPOINTSNAP,
				AppDefs.MNU_OSNAP_ENDPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_MIDPOINTSNAP,
				AppDefs.MNU_OSNAP_MIDPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_INTPOINTSNAP,
				AppDefs.MNU_OSNAP_INTPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_PERPPOINTSNAP,
				AppDefs.MNU_OSNAP_PERPPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_NEARPOINTSNAP,
				AppDefs.MNU_OSNAP_NEARPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_CENTERPOINTSNAP,
				AppDefs.MNU_OSNAP_CENTERPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_QUADPOINTSNAP,
				AppDefs.MNU_OSNAP_QUADPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_TANPOINTSNAP,
				AppDefs.MNU_OSNAP_TANPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_POINTSNAP,
				AppDefs.MNU_OSNAP_POINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_INSPOINTSNAP,
				AppDefs.MNU_OSNAP_INSPOINTSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_PARALLELSNAP,
				AppDefs.MNU_OSNAP_PARALLELSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_45DSNAP,
				AppDefs.MNU_OSNAP_45DSNAP,
				listener) );
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_SNAPOFF,
				AppDefs.MNU_OSNAP_SNAPOFF,
				listener) );
			
			mnuPopup.add(new JSeparator());
			
			mnuPopup.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_OSNAP_OBJECTSNAP,
				AppDefs.MNU_OSNAP_OBJECTSNAP,
				listener) );

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
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
