/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdSetaFiacaoEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
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

import java.util.ArrayList;

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
import br.com.tlmv.aicadxapp.vo.MidLineSegment2dVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletroduto3DEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class CmdSetaFiacaoEletrica extends CmdBase
{
//Public

	public CmdSetaFiacaoEletrica() {
		super(AppDefs.ACTION_EL2_SETA_FIACAO, false, false);
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
		
		PromptUtil.prompt("ELETRICA: Insere Seta de Fiacao... ");
		
		int[] arr = {
			AppDefs.OBJTYPE_MODELELETRODUTO,
			AppDefs.OBJTYPE_MODELELETRODUTO3D
		};
		
		CadEntity ent1 = PromptUtil.selectObject(this, arr, "Selecione o eletroduto: ");
		if(ent1 == null) return null;

		int objType = ent1.getObjType();

		CadPontoEletrica oPontoEle1 = null;
		CadPontoEletrica oPontoEle2 = null;
		
		GeomPoint2d ptMid2d = null; 
		if(objType == AppDefs.OBJTYPE_MODELELETRODUTO) {
			CadEletrodutoEletrica oEnt1 = (CadEletrodutoEletrica)ent1;
			
			oPontoEle1 = (CadPontoEletrica)oEnt1.getEntI();
			oPontoEle2 = (CadPontoEletrica)oEnt1.getEntF();
			
			// ELETRODUTO - MIDDLE_POINT
			//
			GeomPoint2d ptI2d = new GeomPoint2d( oPontoEle1.getPtIns() );
			GeomPoint2d ptF2d = new GeomPoint2d( oPontoEle2.getPtIns() );

			ptMid2d = GeomUtil.midPointOf(ptI2d, ptF2d);
		}
		else if(objType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
			CadEletroduto3DEletrica oEnt1 = (CadEletroduto3DEletrica)ent1;
			
			oPontoEle1 = (CadPontoEletrica)oEnt1.getEntI();
			oPontoEle2 = (CadPontoEletrica)oEnt1.getEntF();
			
			// ELETRODUTO - MIDDLE_POINT
			//
			GeomPoint2d ptI2d = new GeomPoint2d( oPontoEle1.getPtIns() );
			GeomPoint2d ptF2d = new GeomPoint2d( oPontoEle2.getPtIns() );

			ArrayList<GeomPoint3d> lsPts3d = oEnt1.getLsPts();
			ArrayList<GeomPoint2d> lsPts2d = GeomUtil.copyPt3dTo2dList(lsPts3d);
			
			MidLineSegment2dVO oMidSeg = GeomUtil.midSegmentOfLineString(ptI2d, ptF2d, lsPts2d);
			ptMid2d = oMidSeg.getPtMid();
		}
		if(ptMid2d == null) return null;

		// ELETRODUTO - SETA_FIACAO
		//
		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(this, ptMid2d, "Ponto de insercao da seta de fiacao: ");
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		result = new InputParamVO();
		result.initEntity(ent1, ptIns3d);
		return result;
	}
	
	@Override
	public void doCommand() 
	{
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadEntity ent1 = oParam.getEnt1(); 

			int objType = ent1.getObjType();
			if(objType == AppDefs.OBJTYPE_MODELELETRODUTO) {
				CadEletrodutoEletrica oEnt1 = (CadEletrodutoEletrica)ent1;
				GeomPoint3d ptIns3d = oParam.getPt0(); 
				
				oEnt1.setTipoIndicadorFiacao(AppDefs.DEF_POSFIA_ELETRODUTO_INDICADORFIOS);
				oEnt1.setPtInsIndicadorFiacao(ptIns3d);
			}
			else if(objType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
				CadEletroduto3DEletrica oEnt1 = (CadEletroduto3DEletrica)ent1;
				GeomPoint3d ptIns3d = oParam.getPt0(); 
				
				oEnt1.setTipoIndicadorFiacao(AppDefs.DEF_POSFIA_ELETRODUTO_INDICADORFIOS);
				oEnt1.setPtInsIndicadorFiacao(ptIns3d);
			}
			
			this.refreshAll();
			
			oParam = this.promptInputParam(this.getFrm(), oParam);
		}

	}

}
