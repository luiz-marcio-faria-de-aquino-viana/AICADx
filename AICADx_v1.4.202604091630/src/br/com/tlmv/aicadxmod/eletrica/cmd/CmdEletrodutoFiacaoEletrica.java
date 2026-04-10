/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdEletrodutoFiacaoEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
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
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletroduto3DEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class CmdEletrodutoFiacaoEletrica extends CmdBase
{
//Public

	public CmdEletrodutoFiacaoEletrica() {
		super(AppDefs.ACTION_EL2_ELETRODUTO_FIACAO, false, false);
	}

	/* Methodes */
	
	@Override
	public boolean initCommand() { return true; }

	@Override
	public void finishCommand() { 
		MainPanel panel = (MainPanel)this.getFrm().getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("ELETRICA: Insere Fiacao sobre Eletroduto... ");
		
		int[] arr = {
			AppDefs.OBJTYPE_MODELELETRODUTO,
			AppDefs.OBJTYPE_MODELELETRODUTO3D
		};
		
		CadEntity ent1 = PromptUtil.selectObject(this, arr, "Selecione o eletroduto: ");
		if(ent1 == null) return null;

		result = new InputParamVO();
		result.initEntity(ent1);		
		return result;
	}
	
	@Override
	public void doCommand() 
	{
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			EletricaModule oEleMod = this.getApp().getElModule();
			
			CadEntity ent1 = oParam.getEnt1(); 
			GeomPoint3d ptIns3d = new GeomPoint3d(0.0, 0.0, 0.0); 
			//int numIndicadorFiacao = oEleMod.nextFiaSeqNum();

			int objType = ent1.getObjType();
			if(objType == AppDefs.OBJTYPE_MODELELETRODUTO) {
				CadEletrodutoEletrica oEnt1 = (CadEletrodutoEletrica)ent1;
				ptIns3d = oParam.getPt0(); 
				
				oEnt1.setTipoIndicadorFiacao(AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO);
				oEnt1.setPtInsIndicadorFiacao(ptIns3d);
				oEnt1.setNumIndicadorFiacao(AppDefs.NULL_INT);
			}
			else if(objType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
				CadEletroduto3DEletrica oEnt1 = (CadEletroduto3DEletrica)ent1;
				ptIns3d = oParam.getPt0(); 
				
				oEnt1.setTipoIndicadorFiacao(AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO);
				oEnt1.setPtInsIndicadorFiacao(ptIns3d);
				oEnt1.setNumIndicadorFiacao(AppDefs.NULL_INT);
			}
			
			this.refreshAll();
			
			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
		
	}

}
