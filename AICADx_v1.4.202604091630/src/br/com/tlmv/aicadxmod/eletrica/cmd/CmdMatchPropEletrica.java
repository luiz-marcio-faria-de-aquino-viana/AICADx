/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdTrocaCircuito.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
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

package br.com.tlmv.aicadxmod.eletrica.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdMatchPropEletrica extends CmdBase
{
//Public

	public CmdMatchPropEletrica() {
		super(AppDefs.ACTION_EL1_MATCHPROP_PONTOELETRICA, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_ELE1_MATCHPROP));

		CadEntity ent1 = PromptUtil.selectObject(
			this, 
			AppDefs.OBJTYPE_MODELINSEREPONTO,
			this.getR().getString(R.CMD_PRT_SELECT_ELECTRICAL_OBJECT) );
		if(ent1 == null) return null;
		
		result = new InputParamVO();
		result.initEntity(ent1);

		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		CadPontoEletrica oEnt1 = (CadPontoEletrica)oParam.getEnt1();
		
		CadParamEletricoOData oParametroEletrico1 = oEnt1.getParamEletricoAt(0);
		
		CadEntity ent2 = PromptUtil.selectObject(
			this, 
			AppDefs.OBJTYPE_MODELINSEREPONTO, 
			this.getR().getString(R.CMD_PRT_COPYTO_ELECTRICAL_OBJECT) );

		while(ent2 != null) {
			CadPontoEletrica oEnt2 = (CadPontoEletrica)ent2;
			
			int sz = oEnt2.getSzLsParamEletrico();
			for(int i = 0; i < sz; i++) {
				CadParamEletricoOData oParametroEletrico2 = oEnt2.getParamEletricoAt(i);
				oParametroEletrico2.init(oParametroEletrico1);					
			}
			
			ent2 = PromptUtil.selectObject(
				this, 
				AppDefs.OBJTYPE_MODELINSEREPONTO, 
				this.getR().getString(R.CMD_PRT_SELECT_ELECTRICAL_OBJECT) );
		}
	}
	
}
