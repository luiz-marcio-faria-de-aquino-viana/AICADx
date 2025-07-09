/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupArchitectureCommands.java
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

public class PopupArchitectureCommands implements PopupBase 
{
//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: ARCHITECTURE_COMMANDS
			//
			JMenu mnu = new JMenu(AppDefs.MNU_ARCHITECTURE_COMMANDS);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PILARRETANGULAR,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PILARRETANGULAR,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PILARCIRCULAR,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PILARCIRCULAR,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE5CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE5CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE10CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE10CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE15CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE15CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE20CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE20CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE25CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE25CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PAREDE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE5CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE5CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE10CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE10CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE15CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE15CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE20CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE20CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE25CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE25CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE5CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE5CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE10CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE10CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE15CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE15CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE20CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE20CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE25CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE25CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE5CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE5CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE10CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE10CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE15CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE15CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE20CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE20CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE25CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE25CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA5CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA5CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA10CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA10CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA15CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA15CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA20CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA20CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA25CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA25CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_MALHA,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_BONECA,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_BONECA,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_CORTAPAREDES,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_CORTAPAREDES,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES60CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES60CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES70CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES70CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES80CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES80CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES90CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES90CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X60CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X60CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X70CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X70CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X80CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X80CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X90CM,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X90CM,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTADUPLA,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTACORRER,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_PORTACORRER,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_COMMANDS_JANELA,
				AppDefs.MNU_ARCHITECTURE_COMMANDS_JANELA,
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
