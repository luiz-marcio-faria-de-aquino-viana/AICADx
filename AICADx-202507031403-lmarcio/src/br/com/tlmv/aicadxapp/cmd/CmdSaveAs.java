/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdSaveAs.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.OpenSaveDatabaseFrame;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdSaveAs extends CmdBase
{
//Public

	public CmdSaveAs() {
		super(AppDefs.ACTION_FILE_SAVEAS);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Save As...");

		result = new InputParamVO();
		return result;
	}
	
	/* THREADS */
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		MainPanel panel = this.getFrm().getMainPanel();
		
		CadDocumentDef doc = this.getDoc();
		String strActiveDatabase = AppDefs.DEF_SCHEMACOPYPREFIX_DEFAULT + doc.getName();
		
		CompCommandPrompt commandPrompt = panel.getCommandPrompt();
		commandPrompt.setCommandPromptFocus(false);
			
		OpenSaveDatabaseFrame frm = new OpenSaveDatabaseFrame(panel);
		frm.init(false, false, true, false, false, false, true, strActiveDatabase, true, panel);
		frm.show();
	}

}
