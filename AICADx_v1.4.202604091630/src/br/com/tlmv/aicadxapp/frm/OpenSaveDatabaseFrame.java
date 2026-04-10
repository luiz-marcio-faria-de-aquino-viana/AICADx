/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.frm;

import java.awt.Container;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public class OpenSaveDatabaseFrame extends BaseFrame
{
//Private
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
		boolean bBtnSaveAs,
		boolean bBtnOpen,
		boolean bBtnCopy,
		boolean bBtnRename,
		boolean bBtnDrop,
		String strActiveDatabase,
		boolean bActiveDatabaseEditable,
		ResultListener resultListener)
	{
		this.bBtnSaveAs = bBtnSaveAs;
		this.bBtnOpen = bBtnOpen;
		this.bBtnCopy = bBtnCopy;
		this.bBtnRename = bBtnRename;
		this.bBtnDrop = bBtnDrop;

		this.strActiveDatabase = strActiveDatabase;
		this.bActiveDatabaseEditable = bActiveDatabaseEditable;
		
		this.resultListener = resultListener;

		super.init();		
	}
	
	/* ABSTRACT */	
	
	@Override
	public void createMainPanel() 
	{
		OpenSaveDatabasePanel openDatabasePanel = new OpenSaveDatabasePanel(this);
		openDatabasePanel.init(
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
