/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SetupFrame.java
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
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public class SetupFrame extends BaseFrame
{
//Private
	private ResultListener resultListener = null;
		
//Public
	
	public SetupFrame(BaseFrame parentFrame)
	{
		super(parentFrame, R.TIT_SETUPFRAME, AppDefs.DEFAULT_FRAME_POSX, AppDefs.DEFAULT_FRAME_POSY, AppDefs.SETUP_FRAME_WIDTH, AppDefs.SETUP_FRAME_HEIGHT);
	}

	/* Methodes */
	
	public void init()
	{
		super.init();
	}
	
	public void init(ResultListener resultListener)
	{
		this.resultListener = resultListener;

		this.init();
	}
	
	/* ABSTRACT */	
	
	public void createMainPanel()
	{
		CadProjectDef oCurrProject = doc.getCurrProjectDef();

		this.panel = new SetupPanel(this);
		
		SetupPanel panel = (SetupPanel)this.panel;
		panel.init(oCurrProject);

		Container c = getContentPane();
		c.add(panel);
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
