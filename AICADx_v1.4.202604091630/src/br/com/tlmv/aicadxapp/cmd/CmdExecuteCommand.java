/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

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
	public boolean initCommand() { return true; }

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
