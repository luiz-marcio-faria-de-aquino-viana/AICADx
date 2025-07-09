/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdSnapOnOff.java
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
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdSnapOnOff extends CmdBase
{
//Public

	public CmdSnapOnOff() {
		super(AppDefs.ACTION_SETTINGS_SNAPONOFF);
	}

	/* Methodes */
	
	@Override
	public void initCommand() { }

	@Override
	public void finishCommand() { 
		MainPanel panel = this.getFrm().getMainPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Snap...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() 
	{
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		//if(oParam == null) return;
		
		MainPanel panel = this.getFrm().getMainPanel();
		
		ICompView v = panel.getCurrView();

		int snapmode = (v.getSnapmode() == AppDefs.SNAPMODE_ON) ? AppDefs.SNAPMODE_OFF : AppDefs.SNAPMODE_ON;
		v.setSnapmode(snapmode);
		
		String str = (snapmode == AppDefs.SNAPMODE_ON) ? "[ SNAP ON ]" : "[ SNAP OFF ]"; 
		PromptUtil.prompt(str);
	}

}
