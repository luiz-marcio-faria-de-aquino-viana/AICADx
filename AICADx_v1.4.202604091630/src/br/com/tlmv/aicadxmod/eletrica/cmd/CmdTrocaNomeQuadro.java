/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdTrocaNomeQuadro.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/09/2025
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
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdTrocaNomeQuadro extends CmdBase
{
//Public

	public CmdTrocaNomeQuadro() {
		super(AppDefs.ACTION_EL2_TROCA_NOME_QUADRO, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("ELETRICA: Setting Electrical Target Panel..");

		String strNomeQuadro = "";
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODELINSEREPONTO, "Electrical element for reference: ");
		if(ent1 != null) {
			CadPontoEletrica oEnt1 = (CadPontoEletrica)ent1;
			
			CadParamEletricoOData oParametroEletrico1 = oEnt1.getParamEletricoAt(0);
			if( AppDefs.FIA_S_QUADRO.equals(oParametroEletrico1.getTipo()) ) {
				strNomeQuadro = oParametroEletrico1.getNomeQuadro();
			}
			else {
				strNomeQuadro = oParametroEletrico1.getQuadroOrigem();
			}
			result = new InputParamVO();
			result.initEntity(oEnt1, strNomeQuadro);
		}			
		else {
			strNomeQuadro = PromptUtil.getText(this, "Novo nome do quadro: ");
			if( (strNomeQuadro == null) || ( "".equals(strNomeQuadro) ) ) return null;
		}
		
		result = new InputParamVO();
		result.initEntity(null, strNomeQuadro);

		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		String strNomeQuadro = oParam.getStrVal();
		
		CadEntity ent2 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODELINSEREPONTO, "Setting electrical element: ");
		while(ent2 != null) {
			CadPontoEletrica oEnt2 = (CadPontoEletrica)ent2;
			
			int sz = oEnt2.getSzLsParamEletrico();
			for(int i = 0; i < sz; i++) {
				CadParamEletricoOData oParametroEletrico2 = oEnt2.getParamEletricoAt(i);

				if( AppDefs.FIA_S_QUADRO.equals(oParametroEletrico2.getTipo()) ) {
					oParametroEletrico2.setNomeQuadro(strNomeQuadro);
				}
			}
			
			this.refreshAll();
			
			ent2 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODELINSEREPONTO, "Setting another electrical element: ");
		}
	}
	
}
