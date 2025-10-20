/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BasicFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 06/07/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public abstract class BaseFrame extends JFrame implements WindowListener, ResultListener
{
//Private
	protected JFrame parentFrame = null;
	
	protected BaseFrame thisFrame = null;
	protected BasePanel panel = null;
	
	protected AppCadMain cad = null;
	
	protected CadDocumentDef doc = null;
	
	protected R r = null;

	protected String frameTitle = AppDefs.NULL_STR;
	
	protected int framePosX = AppDefs.DEFAULT_FRAME_WIDTH;
	protected int framePosY = AppDefs.DEFAULT_FRAME_HEIGHT;

	protected int frameWidth = AppDefs.DEFAULT_FRAME_WIDTH;
	protected int frameHeight = AppDefs.DEFAULT_FRAME_HEIGHT;

//Public
	
	public BaseFrame(JFrame parentFrame, String resTitle, int defaultFramePosX, int defaultFramePosY, int defaultFrameWidth, int defaultFrameHeight)
	{
		this.parentFrame = parentFrame;
		this.thisFrame = this;
		this.panel = null;
		
		this.cad = AppCadMain.getCad();
				
		this.doc = this.cad.getCurrDocumentDef();

		this.r = AppMain.getResource();
		
		this.frameTitle = resTitle;

		this.framePosX = defaultFramePosX;
		this.framePosY = defaultFramePosY;

		this.frameWidth = defaultFrameWidth;
		this.frameHeight = defaultFrameHeight;
	}
	
	/* Methodes */

	public void init()
	{
		String title = this.r.getString(this.frameTitle);
		setTitle(title);
		
		setSize(this.frameWidth, this.frameHeight);
		
		setLocation(this.framePosX, this.framePosY);

		setResizable(false);

		FormControlUtil.loadIcon(this);
		
		addWindowListener(this);

		this.createMainPanel();
	}
	
	public void resizePanel(int x_parent, int y_parent, int w_parent, int h_parent)
	{
		int x = (int)( ((double)w_parent / 2.0) - ((double)this.frameWidth / 2.0) );
		int y = (int)( ((double)h_parent / 2.0) - ((double)this.frameHeight / 2.0) );
		
		setSize(this.frameWidth, this.frameHeight);
		
		setLocation(x, y);
		
		addWindowListener(this);
	}
	
	public void updateTitle(String str)
	{
		String appTitle = AppDefs.APP_NAME + " - " + AppDefs.APP_VERSAO;
		if(str != null)
			appTitle += (" - " + str);		
		setTitle(appTitle);		
	}

	/* Abstract */
	
	public abstract void createMainPanel();
	
	/* Events */
	
	@Override
	public void windowClosing(WindowEvent e) 
	{
		this.dispose();
	}

	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowClosed(WindowEvent e) { }

	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowOpened(WindowEvent e) { }

	/* Listeners */
	
	@Override
	public abstract void actionResultListener(ResultEvent e);
	
	/* Getters/Setters  */

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public BaseFrame getThisFrame() {
		return thisFrame;
	}
	
	public BasePanel getPanel() {
		return panel;
	}
	
	public AppCadMain getCad() {
		return cad;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public R getR() {
		return r;
	}

	public String getFrameTitle() {
		return frameTitle;
	}

	public int getFramePosX() {
		return framePosX;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

}
