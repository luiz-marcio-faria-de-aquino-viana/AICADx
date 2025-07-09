/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupOsnap.java
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

public class PopupOsnap implements PopupBase 
{
//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
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

}
