/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInsereEletrodutoEletrica.java
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

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class CmdInsereEletrodutoEletrica extends CmdBase
{
//Public
	
	public CmdInsereEletrodutoEletrica(String actionCommand) {
		super(actionCommand, true, true);
	}
	
	/* Methodes */
		
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_ELE1_ADDELETRICALCONDUITS));

		//PT-I
		//
		CadEntity ent10 = (CadPontoEletrica)PromptUtil.selectObject(
			this, 
			AppDefs.OBJTYPE_MODELINSEREPONTO, 
			this.getR().getString(R.CMD_PRT_SELECT_ELECTRICAL_OBJECT_FROM) );
		if(ent10 == null) return null;

		CadPontoEletrica oEnt10 = (CadPontoEletrica)ent10;
		GeomPoint3d ptIns10 = oEnt10.getPtIns();
		
		//PT-F
		//
		CadEntity ent20 = (CadPontoEletrica)PromptUtil.selectObject(
			this, 
			AppDefs.OBJTYPE_MODELINSEREPONTO, 
			this.getR().getString(R.CMD_PRT_SELECT_ELECTRICAL_OBJECT_TO) );
		if(ent20 == null) return null;

		CadPontoEletrica oEnt20 = (CadPontoEletrica)ent20;
		GeomPoint3d ptIns20 = oEnt20.getPtIns();
		
		result = new InputParamVO();

		if( ( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PAREDE.equals(super.getCommandName()) ) ||
			( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_APARENTE.equals(super.getCommandName()) ) ) {
			result.initEntity(oEnt10, oEnt20);
		}
		else {
			double z10 = ptIns10.getZ();
			double z20 = ptIns20.getZ();

			if(z10 <= z20) {
				result.initEntity(oEnt10, oEnt20);
			}
			else {
				result.initEntity(oEnt20, oEnt10);
			}
		}		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//PARAMS
			//
			CadPontoEletrica ent1 = (CadPontoEletrica)oParam.getEnt1(); 
			CadPontoEletrica ent2 = (CadPontoEletrica)oParam.getEnt2(); 

			// LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel();
	
			//LAYER
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_TETO);
			
			if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PISO.equals(super.getCommandName()) ) {
				oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_PISO);
			}
			else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PAREDE.equals(super.getCommandName()) ) {
				oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_PAREDE);
			}
			else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_APARENTE.equals(super.getCommandName()) ) {
				oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_APARENTE);
			}		
			
			CadEletrodutoEletrica o = CadEletrodutoEletrica.create(currBlockDef, oLayer, oLevel, ent1, ent2);
			currBlockDef.addEntity(o);
			
			this.refreshAll();
			
			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
