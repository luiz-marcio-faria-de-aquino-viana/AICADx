/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PopupDisplay.java
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

public class PopupDisplay implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: DISPLAY
			//
			this.mnu = new JMenu(AppDefs.MNU_DISPLAY);
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_REDRAW,
			//	AppDefs.MNU_DISPLAY_REDRAW,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_REGEN,
			//	AppDefs.MNU_DISPLAY_REGEN,
			//	listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_PAN,
				AppDefs.MNU_DISPLAY_PAN,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMTOCENTER,
				AppDefs.MNU_DISPLAY_ZOOMTOCENTER,
				listener) );
			
			mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMRT,
			//	AppDefs.MNU_DISPLAY_ZOOMRT,
			//	listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMIN,
				AppDefs.MNU_DISPLAY_ZOOMIN,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMOUT,
				AppDefs.MNU_DISPLAY_ZOOMOUT,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMWINDOW,
				AppDefs.MNU_DISPLAY_ZOOMWINDOW,
				listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMDYNAMIC,
			//	AppDefs.MNU_DISPLAY_ZOOMDYNAMIC,
			//	listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMEXTEND,
				AppDefs.MNU_DISPLAY_ZOOMEXTEND,
				listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMPREVIOUS,
			//	AppDefs.MNU_DISPLAY_ZOOMPREVIOUS,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMALL,
			//	AppDefs.MNU_DISPLAY_ZOOMALL,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMVIEWMAX,
			//	AppDefs.MNU_DISPLAY_ZOOMVIEWMAX,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMLEFT,
			//	AppDefs.MNU_DISPLAY_ZOOMLEFT,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_ZOOMCENTER,
			//	AppDefs.MNU_DISPLAY_ZOOMCENTER,
			//	listener) );
			
			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_RTPAN,
			//	AppDefs.MNU_DISPLAY_RTPAN,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_RTZOOM,
			//	AppDefs.MNU_DISPLAY_RTZOOM,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_RTSPHERE,
			//	AppDefs.MNU_DISPLAY_RTSPHERE,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_RTROTX,
			//	AppDefs.MNU_DISPLAY_RTROTX,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_RTROTY,
			//	AppDefs.MNU_DISPLAY_RTROTY,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_PLAN,
			//	AppDefs.MNU_DISPLAY_PLAN,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_SPLITVIEW,
			//	AppDefs.MNU_DISPLAY_SPLITVIEW,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_JOINVIEW,
			//	AppDefs.MNU_DISPLAY_JOINVIEW,
			//	listener) );
			
			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_3DVIEW,
				AppDefs.ACTION_ZOOM_3DVIEW,
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
