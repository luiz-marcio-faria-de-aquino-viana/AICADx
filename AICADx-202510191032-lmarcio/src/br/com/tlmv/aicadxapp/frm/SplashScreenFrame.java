/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SplashScreenFrame.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public class SplashScreenFrame extends BaseFrame
{
//Private
	private ResultListener resultListener = null;
	
//Public
	
	public SplashScreenFrame()
	{
		super(
			null, 
			R.TIT_SPLASHSCREENFRAME,
			AppDefs.DEFAULT_FRAME_POSX,
			AppDefs.DEFAULT_FRAME_POSY,
			AppDefs.SPLASHSCREEN_FRAME_WIDTH, 
			AppDefs.SPLASHSCREEN_FRAME_HEIGHT);
	}
	
	/* Methodes */

	public void init()
	{
		this.setTitle(this.frameTitle);
		
		super.init();
		
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setAutoRequestFocus(true);
	}

	public void init(ResultListener resultListener)
	{
		this.resultListener = resultListener;
	}

	/* ABSTRACT */
	
	@Override
	public void createMainPanel() 
	{
		SplashScreenPanel splashScreenPanel = new SplashScreenPanel(this);
		splashScreenPanel.init();

		Container c = getContentPane();
		c.add(splashScreenPanel);
		this.show();
	}

	/* LISTENER */

	@Override
	public void actionResultListener(ResultEvent e) 
	{
		if(resultListener != null)
			resultListener.actionResultListener(e);
	}
	
}
