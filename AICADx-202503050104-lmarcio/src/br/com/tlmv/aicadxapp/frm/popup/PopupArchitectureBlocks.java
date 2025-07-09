/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupArchitectureBlocks.java
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

public class PopupArchitectureBlocks implements PopupBase 
{
//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: ARCHITECTURE_BLOCKS
			//
			JMenu mnu = new JMenu(AppDefs.MNU_ARCHITECTURE_BLOCKS);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BIDE,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BIDE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_VASO,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_VASO,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_VASOCAIXAACOPLADA,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_VASOCAIXAACOPLADA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVATORIOGRANDE,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVATORIOGRANDE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVATORIOPEQUENO,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVATORIOPEQUENO,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVATORIOBANCA,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVATORIOBANCA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_CHUVEIRO,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_CHUVEIRO,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_MICTORIO,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_MICTORIO,
				listener) );
	
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_PIASIMPLES,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_PIASIMPLES,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_PIADUPLA,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_PIADUPLA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_FOGAO4BOCAS,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_FOGAO4BOCAS,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_FOGAO6BOCAS,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_FOGAO6BOCAS,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_GELADEIRA,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_GELADEIRA,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVADOURAROUPA,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_LAVADOURAROUPA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_TANQUE,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_TANQUE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_AQUECEDOR,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_AQUECEDOR,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BOILER,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BOILER,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BOMBARECALQUE,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BOMBARECALQUE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BOMBAAGUASSERVIDAS,
				AppDefs.MNU_ARCHITECTURE_BLOCKS_BOMBAAGUASSERVIDAS,
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
