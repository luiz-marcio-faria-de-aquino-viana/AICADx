/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
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
