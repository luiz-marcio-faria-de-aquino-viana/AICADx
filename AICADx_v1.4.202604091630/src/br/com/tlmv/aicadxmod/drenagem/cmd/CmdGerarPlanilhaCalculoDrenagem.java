/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdGerarPlanilhaCalculoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/06/2025
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

package br.com.tlmv.aicadxmod.drenagem.cmd;

import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.frm.GerarPlanilhaCalculoDrenagemFrame;

public class CmdGerarPlanilhaCalculoDrenagem extends CmdBase
{
//Public

	public CmdGerarPlanilhaCalculoDrenagem() {
		super(AppDefs.ACTION_RDP1_GERAR_PLANILHA_CALCULO_CI, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_RPD_GERAR_PLANILHA_CALCULO) );

		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_INSERT_POINT) );
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);
		
		result = new InputParamVO();
		result.initPoint(ptI3d);

		return result;
	}

	@Override
	public void doCommand() 
	{
		Date dataAtualHora = new Date();
		
		Date dataAtual = new Date(dataAtualHora.getYear(), dataAtualHora.getMonth(), dataAtualHora.getDate());
		
		CadDocumentDef doc = this.getDoc();
		
		CadBlockDef currBlockDef = doc.getCurrBlockDef();		

		DrenagemCalc calc = new DrenagemCalc();
		
		//GEOMPOINT3D
		//
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		GeomPoint3d ptIns_orig = oParam.getPt0();

		//TO_LEVEL
		//
		GeomPoint3d ptIns = GeomUtil.toLevelFromPt3d(ptIns_orig); 
		
		//CADMEMORIACALCULO
		//
		CadMemoriaCalculoDrenagem o = calc.createMemoriaCalculoDrenagem(doc, ptIns);
		calc.reCalculaRedeDrenagem(doc, o, true);
		currBlockDef.addEntity(o);
		
		//INIT_FORM
		//
        MainPanel panel = MainPanel.getMainPanel();
		
		CompCommandPrompt commandPrompt = panel.getCommandPrompt();
		commandPrompt.setCommandPromptFocus(false);
		
		GerarPlanilhaCalculoDrenagemFrame gerarPlanilhaCalculoDrenagemFrm = new GerarPlanilhaCalculoDrenagemFrame(this.getFrm());
		gerarPlanilhaCalculoDrenagemFrm.init(panel, o);
		gerarPlanilhaCalculoDrenagemFrm.show();
	}

}
