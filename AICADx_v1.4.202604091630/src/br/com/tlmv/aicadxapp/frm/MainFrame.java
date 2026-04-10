/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MainFrame.java
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

import javax.swing.JPanel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public class MainFrame extends BaseFrame
{
//Private
	private static MainFrame gMainFrame = null;

	private BaseFrameMenu mnu = null;

	private ResultListener resultListener = null;
	
	/* THREADS */
	
	private Thread keyPressedThread = null;
	
	private boolean bKeyPressedRunning = false;
	
//Public

	public MainFrame()
	{
		super(
			null,
			R.TIT_MAINFRAME,
			AppDefs.DEFAULT_FRAME_POSX, 
			AppDefs.DEFAULT_FRAME_POSY,
			AppDefs.MAIN_FRAME_WIDTH, 
			AppDefs.MAIN_FRAME_HEIGHT);
		
		MainFrame.gMainFrame = this;

		this.init(this);
	}
	
	/* Methodes */

	public void init()
	{
		super.init();
		
		this.initCopyright();

		this.mnu = new BaseFrameMenu(this, this.getPanel());
	}
	
	public void init(ResultListener resultListener)
	{
		this.resultListener = resultListener;
		
		this.init();
	}	

	/* COPYRIGHT */
	
	public void initCopyright()
	{
		MainPanel panel = (MainPanel)this.panel;
		
		CompCommandPrompt cmd = panel.getCommandPrompt();
		
		String strAppNomeVersao = String.format(
			"%s %s", 
			AppDefs.APP_NAME, 
			AppDefs.APP_VERSAO);
		
		cmd.writeMessage(AppDefs.APP_COPYRIGHT);
		cmd.writeMessage(strAppNomeVersao); 
		cmd.writeMessage(AppDefs.APP_AUTHOR_NAME);
		cmd.writeMessage(AppDefs.APP_AUTHOR_REGISTRO);
		cmd.writeMessage(AppDefs.APP_AUTHOR_EMAIL);
		cmd.writeMessage(AppDefs.APP_AUTHOR_TELEFONE);
		cmd.writeMessage("");
	}
	
	/* SHOW/HIDE */
	
	public void showPopupMenu(int x, int y)
	{
		this.mnu.showPopupMenu(x, y);
	}
	
	public void showToolbarControl(int toolbarId, boolean bVisible)
	{
		if(this.mnu == null) return;
		
		if(toolbarId == AppDefs.TOOLBARCTRL_BASIC) {
			this.mnu.getToolbarControlBasic().setVisible( bVisible );
		}
		else if(toolbarId == AppDefs.TOOLBARCTRL_ALL) {
			for(JPanel o : this.mnu.getLsToolbarCtrl()) {
				o.setVisible( bVisible );
			}
		}
	}
	
	/* ABSTRACT */	
	
	@Override
	public void createMainPanel()
	{
		this.panel = new MainPanel(this);
		
		this.updateTitle(null);

		MainPanel panel = (MainPanel)this.panel;
		panel.init(this);
		
		this.setResizable(true);
		this.setExtendedState(MAXIMIZED_BOTH);

		Container c = getContentPane();
		c.add(panel);
		this.show();
	}

	/* LISTENER */

	@Override
	public void actionResultListener(ResultEvent e) 
	{
		if(this.resultListener != null)
			this.resultListener.actionResultListener(e);
	}

	/* Getters/Setters */

	public static MainFrame getMainFrame() {
		return gMainFrame;
	}

	public BaseFrameMenu getMnu() {
		return mnu;
	}
	
}
