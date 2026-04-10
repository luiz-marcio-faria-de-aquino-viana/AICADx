/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPan.java
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

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdPan extends CmdBase
{
//Public

	public CmdPan() {
		super(AppDefs.ACTION_ZOOM_PAN, false, false);
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
		
		PromptUtil.prompt("Move Screen to New Center...");

		MainPanel panel = (MainPanel)frm.getPanel();
		
		ICompView v = panel.getCurrView();

		ICadViewBase cv = v.getCadViewBase();

		GeomPlan2d planMcs = cv.getPlanMcs2d();
    	
    	GeomPoint2d ptOldCenterMcs = planMcs.getPtCenter(); 
		
		GeomPoint2d ptNewCenterMcs = PromptUtil.zoomPan(this, ptOldCenterMcs, "New center: ");
		if(ptNewCenterMcs == null) return null;

		GeomPoint3d ptOldCenterMcs3d = new GeomPoint3d(ptOldCenterMcs);
		GeomPoint3d ptNewCenterMcs3d = new GeomPoint3d(ptNewCenterMcs);
		
		result = new InputParamVO();
		result.initVDir(ptOldCenterMcs3d, ptNewCenterMcs3d);

		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		//GEOMVECTOR3D
		//
		GeomVector3d vDir3d = oParam.getVDir();

		GeomVector2d vDir2d = GeomUtil.vector3dToVector2d(vDir3d);
		double dist = vDir2d.mod();
		
		//PAN
		//
		MainPanel panel = (MainPanel)this.getFrm().getPanel();

		ICompView v = panel.getCurrView();
		
		ICadViewBase cv = v.getCadViewBase();
		cv.moveToMcs(vDir2d, dist);
	}

}
