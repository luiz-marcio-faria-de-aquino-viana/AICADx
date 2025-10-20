/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarDraw3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
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

public class ToolbarDraw3D implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARDRAW3D
			//
			JButton btnBox3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_BOX3D, AppDefs.ACTION_DRAW3D_BOX3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_BOX3D);
			mnuToolbar.add(btnBox3D);

			JButton btnCilinder3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_CILINDER3D, AppDefs.ACTION_DRAW3D_CILINDER3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_CILINDER3D);
			mnuToolbar.add(btnCilinder3D);

			JButton btnCone3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_CONE3D, AppDefs.ACTION_DRAW3D_CONE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_CONE3D);
			mnuToolbar.add(btnCone3D);

			JButton btnTroncoCone3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_TRONCOCONE3D, AppDefs.ACTION_DRAW3D_TRONCOCONE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_TRONCOCONE3D);
			mnuToolbar.add(btnTroncoCone3D);

			JButton btnTorus3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_TORUS3D, AppDefs.ACTION_DRAW3D_TORUS3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_TORUS3D);
			mnuToolbar.add(btnTorus3D);

			JButton btnSphere3D = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW3D_SPHERE3D, AppDefs.ACTION_DRAW3D_SPHERE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW3D_SPHERE3D);
			mnuToolbar.add(btnSphere3D);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
