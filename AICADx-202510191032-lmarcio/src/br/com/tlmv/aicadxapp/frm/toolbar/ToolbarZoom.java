/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.frm.toolbar;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
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
