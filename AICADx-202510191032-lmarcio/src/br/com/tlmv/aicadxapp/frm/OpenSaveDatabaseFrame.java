/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * OpenDatabaseFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.Container;
import java.awt.event.WindowEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public class OpenSaveDatabaseFrame extends BaseFrame
{
//Private
	private boolean bBtnNew = false;
	private boolean bBtnSave = false;
	private boolean bBtnSaveAs = false;
	private boolean bBtnOpen = false;
	private boolean bBtnCopy = false;
	private boolean bBtnRename = false;
	private boolean bBtnDrop = false;
	
	private String strActiveDatabase = "";
	private boolean bActiveDatabaseEditable = false;

	private ResultListener resultListener = null;
	
//Public
	
	public OpenSaveDatabaseFrame(BaseFrame parentFrame)
	{
		super(parentFrame, R.TIT_OPENSAVEDATABASEFRAME, AppDefs.DEFAULT_FRAME_POSX, AppDefs.DEFAULT_FRAME_POSY, AppDefs.OPENSAVE_DATABASE_FRAME_WIDTH, AppDefs.OPENSAVE_DATABASE_FRAME_HEIGHT);
	}

	public void init(
		boolean bBtnNew,
		boolean bBtnSave, 
		boolean bBtnSaveAs,
		boolean bBtnOpen,
		boolean bBtnCopy,
		boolean bBtnRename,
		boolean bBtnDrop,
		String strActiveDatabase,
		boolean bActiveDatabaseEditable)
	{
		this.bBtnNew = bBtnNew;
		this.bBtnSave = bBtnSave; 
		this.bBtnSaveAs = bBtnSaveAs;
		this.bBtnOpen = bBtnOpen;
		this.bBtnCopy = bBtnCopy;
		this.bBtnRename = bBtnRename;
		this.bBtnDrop = bBtnDrop;

		this.strActiveDatabase = strActiveDatabase;
		this.bActiveDatabaseEditable = bActiveDatabaseEditable;
		
		super.init();
	}

	public void init(
		boolean bBtnNew,
		boolean bBtnSave, 
		boolean bBtnSaveAs,
		boolean bBtnOpen,
		boolean bBtnCopy,
		boolean bBtnRename,
		boolean bBtnDrop,
		String strActiveDatabase,
		boolean bActiveDatabaseEditable,
		ResultListener resultListener)
	{
		this.resultListener = resultListener;

		this.init(
			this.bBtnNew,
			this.bBtnSave, 
			this.bBtnSaveAs,
			this.bBtnOpen,
			this.bBtnCopy,
			this.bBtnRename,
			this.bBtnDrop,
			this.strActiveDatabase,
			this.bActiveDatabaseEditable);
	}
	
	/* ABSTRACT */	
	
	@Override
	public void createMainPanel() 
	{
		OpenSaveDatabasePanel openDatabasePanel = new OpenSaveDatabasePanel(this);
		openDatabasePanel.init(
				this.bBtnNew, 
				this.bBtnSave, 
				this.bBtnSaveAs, 
				this.bBtnOpen, 
				this.bBtnCopy, 
				this.bBtnRename,
				this.bBtnDrop,
				this.strActiveDatabase,
				this.bActiveDatabaseEditable,
				this.resultListener);

		Container c = getContentPane();
		c.add(openDatabasePanel);		
		this.show();
	}

	/* LISTENER */

	@Override
	public void actionResultListener(ResultEvent e) 
	{
		if(resultListener != null)
			resultListener.actionResultListener(e);
	}
	
	/* Getters/Setters */
	
	public ResultListener getResultListener() {
		return this.resultListener;
	}
	
}
