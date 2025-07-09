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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarEdit2 implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: TOOLBAREDIT2
			//
			JButton btnErase = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_EDIT2_ERASE, AppDefs.ACTION_EDIT2_ERASE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_ERASE);
			mnuToolbar.add(btnErase);

			JButton btnCopy = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_EDIT2_COPY, AppDefs.ACTION_EDIT2_COPY, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_COPY);
			mnuToolbar.add(btnCopy);

			JButton btnMirror = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_EDIT2_MIRROR, AppDefs.ACTION_EDIT2_MIRROR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_MIRROR);
			mnuToolbar.add(btnMirror);

			JButton btnMove = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_EDIT2_MOVE, AppDefs.ACTION_EDIT2_MOVE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_MOVE);
			mnuToolbar.add(btnMove);

			JButton btnScale = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_EDIT2_SCALE, AppDefs.ACTION_EDIT2_SCALE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_SCALE);
			mnuToolbar.add(btnScale);

			JButton btnRotate = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_EDIT2_ROTATE, AppDefs.ACTION_EDIT2_ROTATE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EDIT2_ROTATE);
			mnuToolbar.add(btnRotate);
						
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
