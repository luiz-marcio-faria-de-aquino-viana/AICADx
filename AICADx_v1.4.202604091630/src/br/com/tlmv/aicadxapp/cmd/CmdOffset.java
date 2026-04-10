/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdOffset.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 25/02/2025
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

package br.com.tlmv.aicadxapp.cmd;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPiso;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public class CmdOffset extends CmdBase
{
//Private Static
	private static double gDist = 0.5;		// distance = 0.5 metros (default)
	
//Public

	public CmdOffset() {
		super(AppDefs.ACTION_DRAW1_OFFSET, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);
		
		MainPanel panel = (MainPanel)frm.getPanel();
		ICompView v = panel.getCurrView();

		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_DRAW_OFFSET) );
		
		if(oParam == null) {
			String str = String.format( this.getR().getString(R.CMD_PRT_OFFSET_DISTANCE) , nf3.format(CmdOffset.gDist));
			Double distTmp = PromptUtil.getDouble(this, str);
			if(distTmp != null) {
				CmdOffset.gDist = distTmp;
			}
		}
		
		CadEntity oEnt = PromptUtil.selectObject(this, this.getR().getString(R.CMD_PRT_SELECT_OBJECT) );
		if(oEnt == null) return null;

		GeomDimension2d oDim2d = oEnt.getEnvelop2d();
		if(oDim2d == null) return null;
		
		GeomPoint2d ptI2d = oDim2d.getPtCentroid();
	
		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);
		
		GeomPoint2d ptF2d = PromptUtil.getSecondPoint2d(this, ptI2d, "Second point: ");
		if(ptF2d == null) return null;
		
		GeomPoint3d ptF3d = new GeomPoint3d(ptF2d);
		
		result = new InputParamVO();
		result.initEntity(oEnt, ptI3d, ptF3d, CmdOffset.gDist);
		
		return result;
	}

	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//CADPOINT
			//
			CadEntity oEnt = oParam.getEnt1();
			GeomPoint3d ptI_orig = oParam.getPt0();
			GeomPoint3d ptF_orig = oParam.getPt1();
			double dist = oParam.getDblVal();
	
			//TO_LEVEL
			//
			GeomPoint3d ptI = GeomUtil.toLevelFromPt3d(ptI_orig); 
			GeomPoint3d ptF = GeomUtil.toLevelFromPt3d(ptF_orig); 
			
			GeomVector3d vIF = new GeomVector3d(ptI, ptF); 
			GeomVector3d uIF = vIF.otherUnit(); 
			
			CadEntity oNewEnt = (CadEntity)oEnt.offsetTo(ptI, uIF, dist);
			currBlockDef.addEntity(oNewEnt);

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
