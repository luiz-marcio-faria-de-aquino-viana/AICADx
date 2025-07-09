/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupEditCommands.java
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

public class PopupEdit2 implements PopupBase 
{
//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {	
			//MENU: EDIT2
			//
			JMenu mnu = new JMenu(AppDefs.MNU_EDIT2);

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_ERASE,
				AppDefs.ACTION_EDIT2_ERASE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_COPY,
				AppDefs.ACTION_EDIT2_COPY,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_MOVE,
				AppDefs.ACTION_EDIT2_MOVE,
				listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_MIRROR,
			//	AppDefs.MNU_EDIT2_MIRROR,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_STRETCH,
			//	AppDefs.MNU_EDIT2_STRETCH,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_COPYCHANGE,
			//	AppDefs.MNU_EDIT2_COPYCHANGE,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_ARRAYRECT,
			//	AppDefs.MNU_EDIT2_ARRAYRECT,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_ARRAYPOLAR,
			//	AppDefs.MNU_EDIT2_ARRAYPOLAR,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_CHAMFER,
			//	AppDefs.MNU_EDIT2_CHAMFER,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_FILLET,
			//	AppDefs.MNU_EDIT2_FILLET,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_UNILINE,
			//	AppDefs.MNU_EDIT2_UNILINE,
			//	listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_SCALE,
				AppDefs.ACTION_EDIT2_SCALE,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_EDIT2_ROTATE,
				AppDefs.ACTION_EDIT2_ROTATE,
				listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_BREAK1POINT,
			//	AppDefs.MNU_EDIT2_BREAK1POINT,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_BREAK1POINTSELC,
			//	AppDefs.MNU_EDIT2_BREAK1POINTSELC,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_BREAK2POINT,
			//	AppDefs.MNU_EDIT2_BREAK2POINT,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_BREAK2POINTSELC,
			//	AppDefs.MNU_EDIT2_BREAK2POINTSELC,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_TRIM,
			//	AppDefs.MNU_EDIT2_TRIM,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_EXTEND,
			//	AppDefs.MNU_EDIT2_EXTEND,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_EXPLODE,
			//	AppDefs.MNU_EDIT2_EXPLODE,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_EXPLODEATTR,
			//	AppDefs.MNU_EDIT2_EXPLODEATTR,
			//	listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_SPLINETOPLINE,
			//	AppDefs.MNU_EDIT2_SPLINETOPLINE,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_EDIT2_SPLINETOPLINEALL,
			//	AppDefs.MNU_EDIT2_SPLINETOPLINEALL,
			//	listener) );

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

}
