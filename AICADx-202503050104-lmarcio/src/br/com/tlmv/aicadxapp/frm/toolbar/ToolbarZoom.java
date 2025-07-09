/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarZoom.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.toolbar;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarZoom implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: TOOLBARZOOM
			//
			JButton btnPan = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_PAN, AppDefs.ACTION_ZOOM_PAN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_PAN);
			mnuToolbar.add(btnPan);
			
			JButton btnZoomIn = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_ZOOMIN, AppDefs.ACTION_ZOOM_ZOOMIN, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMIN);
			mnuToolbar.add(btnZoomIn);

			JButton btnZoomOut = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_ZOOMOUT, AppDefs.ACTION_ZOOM_ZOOMOUT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMOUT);
			mnuToolbar.add(btnZoomOut);
			
			JButton btnZoomWindow = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_ZOOMWINDOW, AppDefs.ACTION_ZOOM_ZOOMWINDOW, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMWINDOW);
			mnuToolbar.add(btnZoomWindow);

			JButton btnZoomExt = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_ZOOMEXT, AppDefs.ACTION_ZOOM_ZOOMEXT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_ZOOMEXT);
			mnuToolbar.add(btnZoomExt);

			JLabel lblSeparator = FormControlUtil.newLabel("", 16, 15, false);
			mnuToolbar.add(lblSeparator);

			JButton btnZoomMoveForward = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_MOVEFORWARD, AppDefs.ACTION_ZOOM_MOVEFORWARD, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_MOVEFORWARD);
			mnuToolbar.add(btnZoomMoveForward);

			JButton btnZoomMoveBackward = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_MOVEBACKWARD, AppDefs.ACTION_ZOOM_MOVEBACKWARD, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_MOVEBACKWARD);
			mnuToolbar.add(btnZoomMoveBackward);

			JButton btnZoomTurnLeft = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_TURNLEFT, AppDefs.ACTION_ZOOM_TURNLEFT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_TURNLEFT);
			mnuToolbar.add(btnZoomTurnLeft);

			JButton btnZoomTurnRight = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_ZOOM_TURNRIGHT, AppDefs.ACTION_ZOOM_TURNRIGHT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ZOOM_TURNRIGHT);
			mnuToolbar.add(btnZoomTurnRight);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
