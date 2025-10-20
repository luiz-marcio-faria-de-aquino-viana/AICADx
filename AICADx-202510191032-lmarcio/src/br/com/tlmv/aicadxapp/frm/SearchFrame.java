/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MessageFrame.java
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

public class SearchFrame extends BaseFrame
{
//Private
	private ResultListener resultListener = null;
	
//Public
	
	public SearchFrame(BaseFrame parentFrame)
	{
		super(
			parentFrame, 
			R.TIT_MESSAGEFRAME_INFORMACAO,
			AppDefs.DEFAULT_FRAME_POSX,
			AppDefs.DEFAULT_FRAME_POSY,
			AppDefs.SEARCH_FRAME_WIDTH, 
			AppDefs.SEARCH_FRAME_HEIGHT);
	}
	
	/* Methodes */

	public void init()
	{
		this.frameTitle = R.TIT_SEARCHFRAME;

		this.setTitle(this.frameTitle);
		
		super.init();
		
		this.setResizable(true);
	}

	public void init(ResultListener resultListener)
	{
		this.resultListener = resultListener;
		
		this.init();
	}

	/* ABSTRACT */
	
	@Override
	public void createMainPanel() 
	{
		SearchPanel searchPanel = new SearchPanel(this);
		searchPanel.init();

		Container c = getContentPane();
		c.add(searchPanel);
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
