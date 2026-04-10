/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdLevantamentoCargasEletrico.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/11/2025
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

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdLevantamentoCargasEletrico extends CmdBase
{
//Public
	
	public CmdLevantamentoCargasEletrico() {
		super(AppDefs.ACTION_EL2_LEVANTAMENTO_CARGAS, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("ELETRICA: Levantamento de Cargas...");

		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODELINSEREPONTO, "Selecione quadro de distribuicao: ");
		if(ent1 != null) {
			CadPontoEletrica oEnt1 = (CadPontoEletrica)ent1;

			int szLsParamEletrico = oEnt1.getSzLsParamEletrico();
			if(szLsParamEletrico > 0) {
				CadParamEletricoOData oParametroEletrico1 = oEnt1.getParamEletricoAt(0);

				String strTip = oParametroEletrico1.getTipo();				
				if( AppDefs.FIA_S_QUADRO.equals(strTip) ) {
					result = new InputParamVO();
					result.initEntity(oEnt1);
				}
			}
		}
		return result;
	}
	
	@Override
	public void doCommand() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//CADENTITY
			//
			CadPontoEletrica oEnt1 = (CadPontoEletrica)oParam.getEnt1();			
            ArrayList<CadParamEletricoOData> oLsParam1 = oEnt1.getLsParamEletrico();
            int szLsParam1 = oLsParam1.size();
            if(szLsParam1 > 0) {
            	CadParamEletricoOData oParam1 = oLsParam1.get(0);
            	String strQdr1 = oParam1.getNomeQuadro();
				double dCargaTotal = 0.0;
			
				CadEntity[] arrPonto = currBlockDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODELINSEREPONTO);
				for(CadEntity ent : arrPonto) {
                	CadPontoEletrica oElem1 = (CadPontoEletrica)ent; 
                	
                    ArrayList<CadParamEletricoOData> oLsParam2 = oElem1.getLsParamEletrico();
                    for(CadParamEletricoOData oParam2 : oLsParam2) {
                    	String strOrg2 = oParam2.getQuadroOrigem();
                    	if( strQdr1.equals(strOrg2) ) {                    	
	                        double dPot2 = oParam2.getPotencia();
	                        double dDem2 = oParam2.getPotenciaDemandada();
	
	                        if(dDem2 > 0) {
	                        	dCargaTotal += dDem2;
	                        }
	                        else if(dPot2 > 0) {
	                        	dCargaTotal += dPot2;
	                        }
                    	}
                    }
                }

				//RESULT
				//
				oParam1.setPotencia(dCargaTotal);
				oParam1.setPotenciaDemandada(dCargaTotal);
				
				String str = String.format("Quadro: %s; Potencia: %s;", 
					strQdr1,
					nf0.format( dCargaTotal ) );					
				PromptUtil.prompt(str);

            }
			
		}
		
	}
	
}
