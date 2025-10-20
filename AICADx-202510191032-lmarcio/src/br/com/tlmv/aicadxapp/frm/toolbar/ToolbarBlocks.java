/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarBlocks.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/05/2025
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarBlocks implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARBLOCKS
			//
			JButton btnInsertShape = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_BLOCKS_INSERTSHAPE, AppDefs.ACTION_BLOCKS_INSERTSHAPE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_BLOCKS_INSERTSHAPE);
			mnuToolbar.add(btnInsertShape);

			JButton btnCreateShape = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_BLOCKS_CREATESHAPE, AppDefs.ACTION_BLOCKS_CREATESHAPE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_BLOCKS_CREATESHAPE);
			mnuToolbar.add(btnCreateShape);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
