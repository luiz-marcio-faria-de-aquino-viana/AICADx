/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPorta.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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

package br.com.tlmv.aicadxmod.arquitetura.cmd;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.EntSelVO;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public class CmdPorta extends CmdBase
{
//Private Static
	private static double gDefaultDoorDist = AppDefs.DEF_ARQ_PORTA_BONECA_10CM;
	private static double gDefaultDoorHeight = AppDefs.DOORHEIGHT_210CM;
	private static double gDefaultDoorWidth = AppDefs.DOORWIDTH_80CM;
	private static double gDefaultDoorWeight = AppDefs.DOORWEIGHT_30MM;
	
//Public

	public CmdPorta() {
		super(AppDefs.ACTION_ARQ1_PORTA, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_ARQ1_PORTA) );

        MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();

		int[] arrObjType = { AppDefs.OBJTYPE_BIMPAREDE };
		EntSelVO oEntSel = PromptUtil.selectEntSel(this, arrObjType, this.getR().getString(R.CMD_PRT_SELECT_PAREDE));
		if(oEntSel != null) {
			CadParede oParede = (CadParede)oEntSel.getEnt1();
			if(oParede == null) return null;
			
			GeomPoint2d ptRef = oEntSel.getPtIns2d();
	
			double larguraTotalParede = oParede.getLarguraTotal();
			double hLarguraTotalParede = larguraTotalParede / 2.0;
			
			GeomPoint2d ptI2d0 = new GeomPoint2d(oParede.getPtI());
			GeomPoint2d ptF2d0 = new GeomPoint2d(oParede.getPtF());
	
			double dPtRefToPtI = ptRef.distTo(ptI2d0);
			double dPtRefToPtF = ptRef.distTo(ptF2d0);
	
			GeomVector2d vIF2d0 = new GeomVector2d(ptI2d0, ptF2d0);
			GeomVector2d uIF2d0 = vIF2d0.otherUnit();
			GeomVector2d nIF2d0 = uIF2d0.otherNorm();
			
			GeomPoint2d ptI2d = ptI2d0;
			GeomPoint2d ptF2d = ptF2d0;
			if(dPtRefToPtF < dPtRefToPtI) {
				ptI2d = ptF2d0;
				ptF2d = ptI2d0;
			}
			GeomVector2d vIF2d = new GeomVector2d(ptI2d, ptF2d);
			GeomVector2d uIF2d = vIF2d.otherUnit();
			
			GeomPoint2d ptLocationTmp2d = PromptUtil.getPointAtDir2d(this, ptI2d, vIF2d, this.getR().getString(R.CMD_PRT_DOOR_LOCATION));
			GeomVector2d vPtIToPtLocation = new GeomVector2d(ptI2d0, ptLocationTmp2d);
	
			double dist = uIF2d0.dotProd( vPtIToPtLocation );
	
			GeomPoint2d ptLocation2d0 = ptI2d0.otherMoveTo(uIF2d0, dist);
			
			GeomPoint2d ptDir2d0 = PromptUtil.getSecondPoint2d(this, ptLocation2d0, this.getR().getString(R.CMD_PRT_DOOR_OPPENINGSIDE));
			GeomVector2d vPtLocationToPtDir0 = new GeomVector2d(ptLocation2d0, ptDir2d0);
			
			double dH = uIF2d0.dotProd(vPtLocationToPtDir0);
			double dV = nIF2d0.dotProd(vPtLocationToPtDir0);
	
			GeomPoint2d ptLocation2d1 = new GeomPoint2d(ptLocation2d0);
	
			int doorDir = AppDefs.DOORDIR_PT3;
			if( (dH < 0.0) && (dV >= 0.0) ) {
				ptLocation2d1 = ptLocation2d0.otherMoveTo(uIF2d0, - CmdPorta.gDefaultDoorWidth);
				doorDir = AppDefs.DOORDIR_PT2;
			}
			else if( (dH < 0.0) && (dV < 0.0) ) {
				ptLocation2d1 = ptLocation2d0.otherMoveTo(uIF2d0, - CmdPorta.gDefaultDoorWidth);
				doorDir = AppDefs.DOORDIR_PT1;
			}
			else if( (dH >= 0.0) && (dV < 0.0) ) {
				doorDir = AppDefs.DOORDIR_PT0;
			}
	
			GeomPoint3d ptLocation3d1 = new GeomPoint3d( ptLocation2d1 );
	
			result = new InputParamVO();
			result.initEntity(oParede, ptLocation3d1, doorDir);
		}
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
	
			//INPUT_PARAM
			//
			CadEntity oEnt = oParam.getEnt1();
			oEnt.reset();
			
			GeomPoint3d ptIns3d_orig = oParam.getPt0(); 
			
			Integer doorDir = oParam.getIntVal();

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d ptIns3d = GeomUtil.toLevelFromPt3d(ptIns3d_orig, oLevel); 
			
			GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);
	
			if(oEnt != null) {
				if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
					CadParede oParede = (CadParede)oEnt;
	
					GeomPoint2d ptI2d = new GeomPoint2d(oParede.getPtI());
					GeomPoint2d ptF2d = new GeomPoint2d(oParede.getPtF());
					
					double dist = ptI2d.distTo(ptIns2d);
					
					//CADPORTA
					//
					LayerTable oTbl = this.getDoc().getLayerTable();
	
					CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PORTA);
					
					CadPorta oPorta = CadPorta.create(
						currBlockDef,
						oLayer,
						oLevel,
						AppDefs.DOORTYPE_BASICA,
				    	CmdPorta.gDefaultDoorHeight,
				    	CmdPorta.gDefaultDoorWidth, 
				    	CmdPorta.gDefaultDoorWeight,
				    	oParede,
				    	dist,
				    	doorDir,
				    	AppDefs.DOORFINISHDEF_WOOD);
					currBlockDef.addEntity(oPorta);
				}

			}

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}

	}

}
