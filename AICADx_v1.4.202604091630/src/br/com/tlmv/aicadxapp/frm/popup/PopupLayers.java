/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupLayers.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
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

public class PopupLayers implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: LAYERS
			//
			this.mnu = new JMenu(AppDefs.MNU_LAYERS);

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYERSEXPLORER,
				AppDefs.ACTION_LAYERS_LAYEREXPLORER,
				listener) );

			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYERASEALL,
				AppDefs.ACTION_LAYERS_LAYERASEALL,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYEXPLODEALL,
				AppDefs.ACTION_LAYERS_LAYEXPLODEALL,
				listener) );
			
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYERLOCKALL,
				AppDefs.ACTION_LAYERS_LAYERLOCKALL,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYERUNLOCKALL,
				AppDefs.ACTION_LAYERS_LAYERUNLOCKALL,
				listener) );

			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYISOLACAMADA,
				AppDefs.ACTION_LAYERS_LAYISOLACAMADA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYEROFF,
				AppDefs.ACTION_LAYERS_LAYEROFF,
				listener) );
				
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_ALLLAYEROFF,
				AppDefs.ACTION_LAYERS_ALLLAYEROFF,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_ALLLAYERON,
				AppDefs.ACTION_LAYERS_ALLLAYERON,
				listener) );
				
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_TOPOGRAFIA,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_TOPOGRAFIA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_ARQUITETURA,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_ARQUITETURA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_ELETRICA,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_ELETRICA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_ESGOTO,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_ESGOTO,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_DRENAGEM,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_DRENAGEM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_HIDRAULICA,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_HIDRAULICA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_GAS,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_GAS,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_LAYDISCIPLINEOFF_TRANSMAR,
				AppDefs.ACTION_LAYERS_LAYDISCIPLINEOFF_TRANSMAR,
				listener) );
			
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_ALLLAYERDISCIPLINEOFF,
				AppDefs.ACTION_LAYERS_ALLLAYERDISCIPLINEOFF,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_LAYERS_ALLLAYERDISCIPLINEON,
				AppDefs.ACTION_LAYERS_ALLLAYERDISCIPLINEON,
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
