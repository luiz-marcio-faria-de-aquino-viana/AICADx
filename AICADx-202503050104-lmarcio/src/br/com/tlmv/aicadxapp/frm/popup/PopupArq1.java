/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupArq1.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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

public class PopupArq1 implements PopupBase 
{
//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: ARCHITECTURE_COMMANDS
			//
			JMenu mnu = new JMenu(AppDefs.MNU_ARQ1);
						
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_PISO,
				AppDefs.ACTION_ARQ1_PISO,
				listener) );

			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ARQ1_PAREDE,
					AppDefs.ACTION_ARQ1_PAREDE,
					listener) );

			mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_ARQ1_AMBIENTE1,
			//	AppDefs.ACTION_ARQ1_AMBIENTE1,
			//	listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_AMBIENTE2,
				AppDefs.ACTION_ARQ1_AMBIENTE2,
				listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_ARQ1_AMBIENTE3,
			//	AppDefs.ACTION_ARQ1_AMBIENTE3,
			//	listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_MALHA,
				AppDefs.ACTION_ARQ1_MALHA,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_PORTA,
				AppDefs.ACTION_ARQ1_PORTA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_PDUPLA,
				AppDefs.ACTION_ARQ1_PDUPLA,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_JANELA,
				AppDefs.ACTION_ARQ1_JANELA,
				listener) );
	
			mnubar.add(mnu);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARQ1_AREA,
				AppDefs.ACTION_ARQ1_AREA,
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

}
