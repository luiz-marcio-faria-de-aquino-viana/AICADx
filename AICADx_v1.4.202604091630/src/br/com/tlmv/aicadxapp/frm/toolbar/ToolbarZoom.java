/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ToolbarZoom.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
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

package br.com.tlmv.aicadxapp.frm.toolbar;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarZoom implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();

			//MENU: TOOLBARZOOM
			//
			JButton btnPan = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_PAN, AppDefs.ACTION_ZOOM_PAN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_PAN);
			mnuToolbar.add(btnPan);
			
			JButton btnZoomToCenter = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_ZOOMTOCENTER, AppDefs.ACTION_ZOOM_ZOOMTOCENTER, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMTOCENTER);
			mnuToolbar.add(btnZoomToCenter);
			
			JButton btnZoomIn = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_ZOOMIN, AppDefs.ACTION_ZOOM_ZOOMIN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMIN);
			mnuToolbar.add(btnZoomIn);

			JButton btnZoomOut = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_ZOOMOUT, AppDefs.ACTION_ZOOM_ZOOMOUT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMOUT);
			mnuToolbar.add(btnZoomOut);
			
			JButton btnZoomWindow = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_ZOOMWINDOW, AppDefs.ACTION_ZOOM_ZOOMWINDOW, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMWINDOW);
			mnuToolbar.add(btnZoomWindow);

			JButton btnZoomAll = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_ZOOMALL, AppDefs.ACTION_ZOOM_ZOOMALL, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMALL);
			mnuToolbar.add(btnZoomAll);

			JButton btnZoomExt = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_ZOOMEXT, AppDefs.ACTION_ZOOM_ZOOMEXT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMEXT);
			mnuToolbar.add(btnZoomExt);

			mnuToolbar.add( FormControlUtil.newLabel("", 16, 15, false) );

			JButton btnZoom3DView = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_3DVIEW, AppDefs.ACTION_ZOOM_3DVIEW, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_3DVIEW);
			mnuToolbar.add(btnZoom3DView);

			JButton btnZoom3DViewArea = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_3DVIEWAREA, AppDefs.ACTION_ZOOM_3DVIEWAREA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_3DVIEWAREA);
			mnuToolbar.add(btnZoom3DViewArea);
			
			mnuToolbar.add( FormControlUtil.newLabel("", 16, 15, false) );

			JButton btnZoomViewTop = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_VIEWTOP, AppDefs.ACTION_ZOOM_VIEWTOP, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_VIEWTOP);
			mnuToolbar.add(btnZoomViewTop);

			JButton btnZoomViewFront = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_VIEWFRONT, AppDefs.ACTION_ZOOM_VIEWFRONT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_VIEWFRONT);
			mnuToolbar.add(btnZoomViewFront);

			JButton btnZoomViewBack = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_VIEWBACK, AppDefs.ACTION_ZOOM_VIEWBACK, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_VIEWBACK);
			mnuToolbar.add(btnZoomViewBack);

			JButton btnZoomViewLeft = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_VIEWLEFT, AppDefs.ACTION_ZOOM_VIEWLEFT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_VIEWLEFT);
			mnuToolbar.add(btnZoomViewLeft);

			JButton btnZoomViewRight = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_VIEWRIGHT, AppDefs.ACTION_ZOOM_VIEWRIGHT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_VIEWRIGHT);
			mnuToolbar.add(btnZoomViewRight);

			JButton btnZoomViewBottom = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ZOOM_VIEWBOTTOM, AppDefs.ACTION_ZOOM_VIEWBOTTOM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_VIEWBOTTOM);
			mnuToolbar.add(btnZoomViewBottom);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
