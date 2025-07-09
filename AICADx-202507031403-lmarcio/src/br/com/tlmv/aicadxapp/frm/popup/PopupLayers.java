/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupLayers.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
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
	
			//mnu.add(new JSeparator());

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_ISOLACAMADA,
			//	AppDefs.MNU_LAYERS_ISOLACAMADA,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_CONGELACAMADA,
			//	AppDefs.MNU_LAYERS_CONGELACAMADA,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_CONGELATUDO,
			//	AppDefs.MNU_LAYERS_CONGELATUDO,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_DESCONGELATUDO,
			//	AppDefs.MNU_LAYERS_DESCONGELATUDO,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_DESLIGACAMADA,
			//	AppDefs.MNU_LAYERS_DESLIGACAMADA,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_LIGATUDO,
			//	AppDefs.MNU_LAYERS_LIGATUDO,
			//	listener) );
			
			//mnu.add(new JSeparator());

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_APAGAOBJCAMADA,
			//	AppDefs.MNU_LAYERS_APAGAOBJCAMADA,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_EXPLODEPLINECAMADA,
			//	AppDefs.MNU_LAYERS_EXPLODEPLINECAMADA,
			//	listener) );
			
			//mnu.add(new JSeparator());

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEARQ,
			//	AppDefs.MNU_LAYERS_FREEZEARQ,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEFOR,
			//	AppDefs.MNU_LAYERS_FREEZEFOR,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEEL,
			//	AppDefs.MNU_LAYERS_FREEZEEL,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEES,
			//	AppDefs.MNU_LAYERS_FREEZEES,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEH,
			//	AppDefs.MNU_LAYERS_FREEZEH,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEINC,
			//	AppDefs.MNU_LAYERS_FREEZEINC,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEG,
			//	AppDefs.MNU_LAYERS_FREEZEG,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZETE,
			//	AppDefs.MNU_LAYERS_FREEZETE,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEIE,
			//	AppDefs.MNU_LAYERS_FREEZEIE,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_FREEZEAR,
			//	AppDefs.MNU_LAYERS_FREEZEAR,
			//	listener) );
			
			//mnu.add(new JSeparator());

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_CONGELAINSTALACOES,
			//	AppDefs.MNU_LAYERS_CONGELAINSTALACOES,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_DESCONGELAINSTALACOES,
			//	AppDefs.MNU_LAYERS_DESCONGELAINSTALACOES,
			//	listener) );
			
			//mnu.add(new JSeparator());

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_TRANCACAMADA,
			//	AppDefs.MNU_LAYERS_TRANCACAMADA,
			//	listener) );

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_LAYERS_DESTRANCACAMADA,
			//	AppDefs.MNU_LAYERS_DESTRANCACAMADA,
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
