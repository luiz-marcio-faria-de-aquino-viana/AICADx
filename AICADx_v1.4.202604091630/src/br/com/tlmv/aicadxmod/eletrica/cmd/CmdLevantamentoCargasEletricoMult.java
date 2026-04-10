/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdLevantamentoCargasEletricoMult.java
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
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.vo.CargaQuadroVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdLevantamentoCargasEletricoMult extends CmdBase
{
//Public
	
	public CmdLevantamentoCargasEletricoMult() {
		super(AppDefs.ACTION_EL2_LEVANTAMENTO_CARGAS_MULT, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("ELETRICA: Levantamento de Cargas (Multiplo)...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			ArrayList<CadEntity> lsQdr1 = new ArrayList<CadEntity>();
			Hashtable map = new Hashtable();

			CadEntity[] arrEnt1 = currBlockDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODELINSEREPONTO);
			
			// OBTEM_LISTA_QUADROS
			//
			for(CadEntity ent1 : arrEnt1) {
				CadPontoEletrica oEnt1 = (CadPontoEletrica)ent1; 
				int objectId1 = oEnt1.getObjectId();
				
				String strObjectId1 = Integer.toString(objectId1);
				
	            ArrayList<CadParamEletricoOData> oLsParam1 = oEnt1.getLsParamEletrico();
	            int szLsParam1 = oLsParam1.size();
	            if(szLsParam1 > 0) {
	            	CadParamEletricoOData oParam1 = oLsParam1.get(0);
	            	String strTip1 = oParam1.getTipo();
				
	            	if( AppDefs.FIA_S_QUADRO.equals( strTip1 ) ) {
	                	String strQdr1 = oParam1.getNomeQuadro();

	                	if( !map.containsKey( strQdr1 ) ) {
	                		lsQdr1.add(oEnt1);

	                		CargaQuadroVO newQdr1 = new CargaQuadroVO(strQdr1); 
	                		map.put(strQdr1, newQdr1);
                	}
	                	else {
	                		String warnmsg = String.format("*ALERTA* Quadro duplicado: [ Id:%s; Tipo:%s; Quadro:%s ] ", strObjectId1, strTip1, strQdr1);
	                		PromptUtil.prompt(warnmsg);
	                	}
	            	}
	            }
			}
						
			// TOTALIZA_POTENCIA_DAS_CARGAS
			//
			for(CadEntity ent1 : arrEnt1) {
				CadPontoEletrica oEnt1 = (CadPontoEletrica)ent1; 
				int objectId1 = oEnt1.getObjectId();
				
				String strObjectId1 = Integer.toString(objectId1);

				ArrayList<CadParamEletricoOData> oLsParam1 = oEnt1.getLsParamEletrico();
	            int szLsParam1 = oLsParam1.size();
	            if(szLsParam1 > 0) {
	            	CadParamEletricoOData oParam1 = oLsParam1.get(0);
                	String strTip1 = oParam1.getTipo();
                	String strOrg1 = oParam1.getQuadroOrigem();

            		CargaQuadroVO currQdr1 = null; 

            		if( map.containsKey( strOrg1 ) ) {
                		currQdr1 = (CargaQuadroVO)map.get(strOrg1);
                    	currQdr1.add( oEnt1 );
    				}
                	else {
                		String warnmsg = String.format("*ALERTA* Ponto eletrico com origem invalida: [ Id:%s; Tipo:%s; Origem:%s ] ", strObjectId1, strTip1, strOrg1);
                		PromptUtil.prompt(warnmsg);
                	}
	            }
			}
			
			// TOTALIZA_POTENCIA
			//

			//TODO:
			
		}
		
	}
	
}
