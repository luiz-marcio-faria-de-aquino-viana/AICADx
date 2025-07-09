/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarArq1.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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

public class ToolbarArq1 implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: TOOLBARARQ1
			//
			JButton btnPiso = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_PISO, AppDefs.ACTION_ARQ1_PISO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PISO);
			mnuToolbar.add(btnPiso);
			
			JButton btnParede = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_PAREDE, AppDefs.ACTION_ARQ1_PAREDE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PAREDE);
			mnuToolbar.add(btnParede);
			
			//JButton btnAmbiente1 = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_AMBIENTE1, AppDefs.ACTION_ARQ1_AMBIENTE1, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AMBIENTE1);
			//mnuToolbar.add(btnAmbiente1);
			
			JButton btnAmbiente2 = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_AMBIENTE2, AppDefs.ACTION_ARQ1_AMBIENTE2, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AMBIENTE2);
			mnuToolbar.add(btnAmbiente2);
			
			//JButton btnAmbiente3 = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_AMBIENTE3, AppDefs.ACTION_ARQ1_AMBIENTE3, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AMBIENTE3);
			//mnuToolbar.add(btnAmbiente1);
			
			//JButton btnMalha = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_MALHA, AppDefs.ACTION_ARQ1_MALHA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_MALHA);
			//mnuToolbar.add(btnMalha);
			
			JButton btnPorta = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_PORTA, AppDefs.ACTION_ARQ1_PORTA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PORTA);
			mnuToolbar.add(btnPorta);

			JButton btnPortaDupla = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_PDUPLA, AppDefs.ACTION_ARQ1_PDUPLA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PDUPLA);
			mnuToolbar.add(btnPortaDupla);
			
			JButton btnJanela = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_JANELA, AppDefs.ACTION_ARQ1_JANELA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_JANELA);
			mnuToolbar.add(btnJanela);
			
			JButton btnArea = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICONMNU_ARQ1_AREA, AppDefs.ACTION_ARQ1_AREA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AREA);
			mnuToolbar.add(btnArea);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
