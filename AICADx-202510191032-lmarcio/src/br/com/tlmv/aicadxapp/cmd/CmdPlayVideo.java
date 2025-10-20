/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdPlayVideo.java
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
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdPlayVideo extends CmdBase
{
//Private
	private Hashtable map = null;
	
	/* Methodes */
	
	private void init() {
		this.map = new Hashtable ();
		
		//HELP - VIDEO_TUTORIAL
		//
		this.map.put(AppDefs.ACTION_HELP_VIDEOTUTORIAL101, AppDefs.HLP_PLAYVIDEO_VIDEOTUTORIAL101);
		this.map.put(AppDefs.ACTION_HELP_VIDEOTUTORIAL105, AppDefs.HLP_PLAYVIDEO_VIDEOTUTORIAL105);
		this.map.put(AppDefs.ACTION_HELP_VIDEOTUTORIAL110, AppDefs.HLP_PLAYVIDEO_VIDEOTUTORIAL110);
		this.map.put(AppDefs.ACTION_HELP_VIDEOTUTORIAL120, AppDefs.HLP_PLAYVIDEO_VIDEOTUTORIAL120);
		
	}
	
//Public
	
	public CmdPlayVideo(String actionCommand) {
		super(actionCommand, false, true);
		
		this.init();
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
			AppMain app = AppMain.getApp();
			AppCtx ctx = app.getCtx();
			
			//PREPARE_COMMAND
			//
			ArrayList<String> lsCmd = new ArrayList<String>();

			String strWorkDir = ctx.getVideosDir();
			String strExternalCommand = AppDefs.RUN_VIDEOPLAYER;

			String strCmd = String.format("%s/%s", strWorkDir, strExternalCommand);
			lsCmd.add(strCmd);

			String strVideoName = getVideo(this.getCommandName());
			lsCmd.add(strVideoName);
			
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
	
	public String getVideo(String actionCommand) {
		String strResult = AppDefs.HLP_PLAYVIDEO_VIDEOTUTORIAL101;
		
		if( this.map.containsKey(actionCommand) ) {
			strResult = (String)this.map.get(actionCommand);
		}
		return strResult;
	}
	
}
