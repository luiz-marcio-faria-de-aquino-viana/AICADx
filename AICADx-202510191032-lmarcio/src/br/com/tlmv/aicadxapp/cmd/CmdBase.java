/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdBase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 08/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public abstract class CmdBase implements ICmdBase, Runnable
{
//Private
	private AppMain app = null;
	private MainFrame frm = null;
	private AppCadMain cad = null;
	private CadDocumentDef doc = null;

	private boolean bShowToolbarControl = true;
	private boolean bCancelActiveCommand = true;

	private String cmdName = null;

	/* Threads */
	
	private Thread thread = null;
	private boolean bRunning = false;
	
//Public
	
	public CmdBase(String cmdName, boolean bShowToolbarControl, boolean bCancelActiveCommand) {
		this.bShowToolbarControl = bShowToolbarControl;
		this.bCancelActiveCommand = bCancelActiveCommand;

		this.cmdName = cmdName;
	}

	/* Methodes */
	
	@Override
	public String getCommandName() {
		return this.cmdName;
	}
	
	@Override
	public void initCommand() {
		MainPanel panel = (MainPanel)this.frm.getPanel();
		
		if( this.bCancelActiveCommand )
			panel.stopAll(this.doc);

		this.frm.showToolbarControl(AppDefs.TOOLBARCTRL_BASIC, this.bShowToolbarControl);		
	}

	@Override
	public int executeCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc)
	{
		this.app = app;
		this.frm = frm;
		this.cad = cad;
		this.doc = doc;
		
		this.thread = new Thread(this);
		this.thread.start();
		
		return AppDefs.RSOK;
	}
	
	@Override
	public void finishCommand() {
		MainPanel panel = (MainPanel)this.getFrm().getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();

		this.frm.showToolbarControl(AppDefs.TOOLBARCTRL_ALL, false);
	}

	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args)
	{
		this.app = app;
		this.frm = frm;
		this.cad = cad;
		this.doc = doc;

		//TODO:
	}
	
	/* RUNNABLE */
	
	@Override
	public void run() {
		this.bRunning = true;

		this.initCommand();
		
		this.doCommand();
		
		this.finishCommand();

		this.bRunning = false;		
	}

	/* ABSTRACT */
	
	@Override
	public abstract InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam);
	
	@Override
	public abstract void doCommand();
	
	/* Getters/Setters */

	public String getCmdName() {
		return cmdName;
	}

	public AppMain getApp() {
		return app;
	}

	public MainFrame getFrm() {
		return frm;
	}

	public AppCadMain getCad() {
		return cad;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public boolean isRunning() {
		return bRunning;
	}
		
}
