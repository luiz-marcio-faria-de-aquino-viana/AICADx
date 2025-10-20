/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/01/2025
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

public class PopupView implements PopupBase 
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
			JMenu mnu = new JMenu(AppDefs.MNU_DISPLAY);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_REDRAW,
				AppDefs.MNU_DISPLAY_REDRAW,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_REGEN,
				AppDefs.MNU_DISPLAY_REGEN,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_PAN,
				AppDefs.MNU_DISPLAY_PAN,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMRT,
				AppDefs.MNU_DISPLAY_ZOOMRT,
				listener) );
			
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
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMDYNAMIC,
				AppDefs.MNU_DISPLAY_ZOOMDYNAMIC,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMEXTEND,
				AppDefs.MNU_DISPLAY_ZOOMEXTEND,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMPREVIOUS,
				AppDefs.MNU_DISPLAY_ZOOMPREVIOUS,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMALL,
				AppDefs.MNU_DISPLAY_ZOOMALL,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMVIEWMAX,
				AppDefs.MNU_DISPLAY_ZOOMVIEWMAX,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMLEFT,
				AppDefs.MNU_DISPLAY_ZOOMLEFT,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_ZOOMCENTER,
				AppDefs.MNU_DISPLAY_ZOOMCENTER,
				listener) );
			
			mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_VIEWTOP,
			//	AppDefs.MNU_DISPLAY_VIEWTOP,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DISPLAY_VIEWBOTTOM,
			//	AppDefs.MNU_DISPLAY_VIEWBOTTOM,
			//	listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_VIEWFRONT,
				AppDefs.MNU_DISPLAY_VIEWFRONT,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_VIEWBACK,
				AppDefs.MNU_DISPLAY_VIEWBACK,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_VIEWLEFT,
				AppDefs.MNU_DISPLAY_VIEWLEFT,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_VIEWRIGHT,
				AppDefs.MNU_DISPLAY_VIEWRIGHT,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_RTPAN,
				AppDefs.MNU_DISPLAY_RTPAN,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_RTZOOM,
				AppDefs.MNU_DISPLAY_RTZOOM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_RTSPHERE,
				AppDefs.MNU_DISPLAY_RTSPHERE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_RTROTX,
				AppDefs.MNU_DISPLAY_RTROTX,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_RTROTY,
				AppDefs.MNU_DISPLAY_RTROTY,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_PLAN,
				AppDefs.MNU_DISPLAY_PLAN,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_SPLITVIEW,
				AppDefs.MNU_DISPLAY_SPLITVIEW,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DISPLAY_JOINVIEW,
				AppDefs.MNU_DISPLAY_JOINVIEW,
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
