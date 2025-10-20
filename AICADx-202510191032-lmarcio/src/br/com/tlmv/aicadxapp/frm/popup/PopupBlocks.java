/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupBlocks.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/01/2025
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

public class PopupBlocks implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: BLOCKS
			//
			this.mnu = new JMenu(AppDefs.MNU_BLOCKS);
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_BLOCKS_INSERTSHAPE,
				AppDefs.ACTION_BLOCKS_INSERTSHAPE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_BLOCKS_CREATESHAPE,
				AppDefs.ACTION_BLOCKS_CREATESHAPE,
				listener) );

//			mnu.add(new JSeparator());
//
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_CARIMBO,
//				AppDefs.MNU_BLOCKS_CARIMBO,
//				listener) );
//			
//			mnu.add(new JSeparator());
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_DETALHESTIPICOS,
//				AppDefs.MNU_BLOCKS_DETALHESTIPICOS,
//				listener) );
//			
//			mnu.add(new JSeparator());
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_ISOMETRICO,
//				AppDefs.MNU_BLOCKS_ISOMETRICO,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_DETALHAMENTO,
//				AppDefs.MNU_BLOCKS_DETALHAMENTO,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_INSERTBLOCK,
//				AppDefs.MNU_BLOCKS_INSERTBLOCK,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_CHANGEBLOCK,
//				AppDefs.MNU_BLOCKS_CHANGEBLOCK,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_WBLOCK,
//				AppDefs.MNU_BLOCKS_WBLOCK,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_BLOCK,
//				AppDefs.MNU_BLOCKS_BLOCK,
//				listener) );
//			
//			mnu.add(new JSeparator());
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_EDITATTR,
//				AppDefs.MNU_BLOCKS_EDITATTR,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_COPYATTR,
//				AppDefs.MNU_BLOCKS_COPYATTR,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_COPYATTR,
//				AppDefs.MNU_BLOCKS_COPYATTR,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_EDITATTRGLOBALLY,
//				AppDefs.MNU_BLOCKS_EDITATTRGLOBALLY,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_DEFINEATTR,
//				AppDefs.MNU_BLOCKS_DEFINEATTR,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_EXPLODEATTR,
//				AppDefs.MNU_BLOCKS_EXPLODEATTR,
//				listener) );
//			
//			mnu.add(new JSeparator());
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_INSERINDOMARGENS,
//				AppDefs.MNU_BLOCKS_INSERINDOMARGENS,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_COLOCANDOCARIMBOS,
//				AppDefs.MNU_BLOCKS_COLOCANDOCARIMBOS,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_VISTAMODELO,
//				AppDefs.MNU_BLOCKS_VISTAMODELO,
//				listener) );
//			
//			mnu.add(new JSeparator());
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_XREFMANAGER,
//				AppDefs.MNU_BLOCKS_XREFMANAGER,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_XREFRELOAD,
//				AppDefs.MNU_BLOCKS_XREFRELOAD,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_XREFBIND,
//				AppDefs.MNU_BLOCKS_XREFBIND,
//				listener) );
//			
//			mnu.add(new JSeparator());
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_CONTABLOCOS,
//				AppDefs.MNU_BLOCKS_CONTABLOCOS,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_CONTABLOCOSBYNOME,
//				AppDefs.MNU_BLOCKS_CONTABLOCOSBYNOME,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_CONTABLOCOAREASELEC,
//				AppDefs.MNU_BLOCKS_CONTABLOCOAREASELEC,
//				listener) );
//			
//			mnu.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_BLOCKS_CONTABLOCOSBYNOMEAREASELEC,
//				AppDefs.MNU_BLOCKS_CONTABLOCOSBYNOMEAREASELEC,
//				listener) );
	
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
