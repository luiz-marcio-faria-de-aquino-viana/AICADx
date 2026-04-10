/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PropriedadePontoEletricaFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/01/2026
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

package br.com.tlmv.aicadxmod.eletrica.frm;

import java.awt.Container;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class PropriedadePontoEletricaFrame extends BaseFrame
{
//Private
	private CadDocumentDef doc = null;
	
	private CadPontoEletrica oEnt1 = null;
	
	private ResultListener resultListener = null;
	
//Public
	
	public PropriedadePontoEletricaFrame(BaseFrame parentFrame)
	{
		super(
			parentFrame,
			R.TIT_PROPRIEDADESPONTOELETRICAFRAME,
			AppDefs.DEFAULT_FRAME_POSX,
			AppDefs.DEFAULT_FRAME_POSY,
			AppDefs.PROPRIEDADE_PONTO_ELETRICA_FRAME_WIDTH, 
			AppDefs.PROPRIEDADE_PONTO_ELETRICA_FRAME_HEIGHT);
	}
	
	/* Methodes */

	public void init(CadDocumentDef doc, CadPontoEletrica oEnt1)
	{
		this.doc = doc;
		this.oEnt1 = oEnt1;
		
		super.init();
	}

	public void init(ResultListener resultListener, CadDocumentDef doc, CadPontoEletrica oEnt1)
	{
		this.resultListener = resultListener;
		
		this.init(doc, oEnt1);
	}

	@Override
	public void createMainPanel() 
	{
		PropriedadePontoEletricaPanel panel = new PropriedadePontoEletricaPanel(this);
		panel.init(this.doc, this.oEnt1);

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

	public CadPontoEletrica getEnt1() {
		return oEnt1;
	}

	public void setEnt1(CadPontoEletrica oEnt1) {
		this.oEnt1 = oEnt1;
	}
	
}
