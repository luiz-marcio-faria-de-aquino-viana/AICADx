/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdParedeAbertura.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;

public class CmdParedeAbertura extends CmdBase
{
//Public

	public CmdParedeAbertura() {
		super(AppDefs.ACTION_ARQ1_ABERTURA, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Openning...");

        MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();

		CadEntity oEnt = PromptUtil.selectObject(this, AppDefs.OBJTYPE_BIMPAREDE, "Select wall: ");
		if(oEnt == null) return null;

		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
			CadParede oParede = (CadParede)oEnt;

			GeomPoint2d ptI2d = new GeomPoint2d(oParede.getPtI());
			GeomPoint2d ptF2d = new GeomPoint2d(oParede.getPtF());
			
			GeomVector2d vIF2d = new GeomVector2d(ptI2d, ptF2d);
			GeomPoint2d ptLocation2d = PromptUtil.getPointAtDir2d(this, ptI2d, vIF2d, "Set the openning location: ");
			if(ptLocation2d == null) return null;

			GeomPoint3d ptLocation3d = new GeomPoint3d(ptLocation2d);
			
			result = new InputParamVO();
			result.initEntity(oEnt, ptLocation3d);
		}

		v.repaintAll();
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadEntity oEnt = oParam.getEnt1();
			oEnt.reset();
			
			GeomPoint3d ptIns3d = oParam.getPt0(); 	
			GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
				CadParede oParede = (CadParede)oEnt;

				GeomPoint2d ptI2d = new GeomPoint2d(oParede.getPtI());
				//GeomPoint2d ptF2d = new GeomPoint2d(oParede.getPtF());
				
				double dist = ptI2d.distTo(ptIns2d);

				oParede.addAbertura(dist, AppDefs.OPENNINGWIDTH_80CM, AppDefs.OPENNINGFLOORHEIGHT_110CM, AppDefs.OPENNINGHEIGHT_100CM);
			}

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
