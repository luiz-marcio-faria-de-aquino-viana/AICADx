/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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
		
		MainPanel panel = (MainPanel)this.panel;
		panel.init(this);
		
		this.setResizable(true);
		this.setExtendedState(MAXIMIZED_BOTH);

		this.updateTitle(null);

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
