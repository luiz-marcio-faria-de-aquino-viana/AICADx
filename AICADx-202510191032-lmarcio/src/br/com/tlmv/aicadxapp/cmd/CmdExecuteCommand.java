/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdExecuteCommand.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/09/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdExecuteCommand extends CmdBase
{
//Private
	private String workDir = null;
	private String externalCommand = null;
	private String[] args = null;
	
//Public
	
	public CmdExecuteCommand(String actionCommand, String workDir, String externalCommand) {
		this(actionCommand, workDir, externalCommand, new String[0]);
	}
	
	public CmdExecuteCommand(String actionCommand, String workDir, String externalCommand, String[] args) {
		super(actionCommand, false, true);
		
		this.workDir = workDir;
		this.externalCommand = externalCommand;
		this.args = args;
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
		
		PromptUtil.prompt("Execute external command...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		try {
			//PREPARE_COMMAND
			//
			String strCmd = String.format("%s/%s", this.workDir, this.externalCommand);

			ArrayList<String> lsCmd = new ArrayList<String>();
			lsCmd.add(strCmd);
			for(String cArg : this.args) {
				lsCmd.add(cArg);				
			}
			
			//EXECUTE_COMMAND
			//
			ProcessBuilder processBuilder = new ProcessBuilder(lsCmd);
			processBuilder.start();
		}
		catch(Exception e) {
			PromptUtil.prompt("Err: Falha na execucao do comando externo.");
			
			e.printStackTrace();
		}
	}

	/* Getters/Setters */
	
	public String getWorkDir() {
		return workDir;
	}

	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public String getExternalCommand() {
		return externalCommand;
	}

	public void setExternalCommand(String externalCommand) {
		this.externalCommand = externalCommand;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
