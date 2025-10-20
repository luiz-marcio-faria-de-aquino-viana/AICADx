/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdSetup.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.SetupFrame;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdSetup extends CmdBase
{
//Public

	public CmdSetup() {
		super(AppDefs.ACTION_FILE_SETUP, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Setup...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() 
	{
		MainPanel panel = (MainPanel)this.getFrm().getPanel();
		
		CompCommandPrompt commandPrompt = panel.getCommandPrompt();
		commandPrompt.setCommandPromptFocus(false);
			
		SetupFrame setupFrm = new SetupFrame(this.getFrm());
		setupFrm.init(panel);
		setupFrm.show();
	}

}
