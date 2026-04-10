/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GerarPlanilhaCalculoDrenagemFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/04/2025
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

package br.com.tlmv.aicadxmod.drenagem.frm;

import java.awt.Container;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;

public class GerarPlanilhaCalculoDrenagemFrame extends BaseFrame
{
//Private
	private CadMemoriaCalculoDrenagem oMemoriaCalculoDrenagem = null;

	private ResultListener resultListener = null;
	
//Public
	
	public GerarPlanilhaCalculoDrenagemFrame(BaseFrame parentFrame)
	{
		super(
			parentFrame,
			R.TIT_GERARPLANILHACALCULODRENAGEMFRAME,
			AppDefs.DEFAULT_FRAME_POSX,
			AppDefs.DEFAULT_FRAME_POSY,
			AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_WIDTH, 
			AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_HEIGHT);
	}
	
	/* Methodes */

	public void init(CadMemoriaCalculoDrenagem o)
	{
		this.oMemoriaCalculoDrenagem = o;
		
		super.init();
		
		this.setResizable(true);
	}

	public void init(ResultListener resultListener, CadMemoriaCalculoDrenagem o)
	{
		this.resultListener = resultListener;
		
		this.init(o);
	}
	
	/* ABSTRACT */	
	
	@Override
	public void createMainPanel() 
	{
		GerarPlanilhaCalculoDrenagemPanel panel = new GerarPlanilhaCalculoDrenagemPanel(this);
		panel.init(this.oMemoriaCalculoDrenagem);
		
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

	public CadMemoriaCalculoDrenagem getMemoriaCalculoDrenagem() {
		return oMemoriaCalculoDrenagem;
	}

	public void setMemoriaCalculoDrenagem(CadMemoriaCalculoDrenagem oMemoriaCalculoDrenagem) {
		this.oMemoriaCalculoDrenagem = oMemoriaCalculoDrenagem;
	}
	
}
