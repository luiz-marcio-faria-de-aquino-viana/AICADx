/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdGridOnOff.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
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
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdGridOnOff extends CmdBase
{
//Public

	public CmdGridOnOff() {
		super(AppDefs.ACTION_SETTINGS_GRIDONOFF, false, false);
	}

	/* Methodes */
	
	@Override
	public void initCommand() { }

	@Override
	public void finishCommand() { 
		MainPanel panel = (MainPanel)this.getFrm().getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Grid...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() 
	{
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		//if(oParam == null) return;
		
		MainPanel panel = (MainPanel)this.getFrm().getPanel();
		ICompView v = panel.getCurrView();

		int gridmode = v.getGridmode();
		if(gridmode == AppDefs.GRIDMODE_ON) {
			gridmode = AppDefs.GRIDMODE_OFF;
		}
		else {
			gridmode = AppDefs.GRIDMODE_ON;
		}
		v.setGridmode(gridmode);
		
		String str = (gridmode == AppDefs.GRIDMODE_ON) ? "[ GRID ON ]" : "[ GRID OFF ]"; 
		PromptUtil.prompt(str);
	}

}
