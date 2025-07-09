/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdNew.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.OpenSaveDatabaseFrame;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdNew extends CmdBase
{
//Public

	public CmdNew() {
		super(AppDefs.ACTION_FILE_NEW);
	}

	/* Methodes */

	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("New...");

		result = new InputParamVO();
		return result;
	}
	
	/* THREADS */
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		MainPanel panel = this.getFrm().getMainPanel();
		
		AppCadMain cad = this.getCad();
		
		int rscode = cad.newCadDocumentDef();
		if(rscode == AppDefs.RSOK) {
			CadDocumentDef newDoc = cad.getCurrDocumentDef();
			String strActiveDatabase = newDoc.getName();
			
			CompCommandPrompt commandPrompt = panel.getCommandPrompt();
			commandPrompt.setCommandPromptFocus(false);
				
			OpenSaveDatabaseFrame frm = new OpenSaveDatabaseFrame(panel);
			frm.init(true, false, false, false, false, false, true, strActiveDatabase, true, panel);
			frm.show();
		}
	}

}
