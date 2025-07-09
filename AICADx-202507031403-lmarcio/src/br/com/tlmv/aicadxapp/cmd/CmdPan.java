/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdPan.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
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
		super(AppDefs.ACTION_ZOOM_PAN);
	}

	/* Methodes */
	
	@Override
	public void initCommand() { }

	@Override
	public void finishCommand() {
		MainPanel panel = this.getFrm().getMainPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Move Screen to New Center...");

		MainPanel panel = frm.getMainPanel();
		
		ICompView v = panel.getCurrView();

		ICadViewBase cv = v.getCadViewBase();

		GeomPlan2d planMcs = cv.getPlanMcs2d();
    	
    	GeomPoint2d ptOldCenterMcs = planMcs.getPtCenter(); 
		
		GeomPoint2d ptNewCenterMcs = PromptUtil.zoomPan(ptOldCenterMcs, "New center: ");
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
		MainPanel panel = MainPanel.getPanel();

		ICompView v = panel.getCurrView();
		
		ICadViewBase cv = v.getCadViewBase();
		cv.moveToMcs(vDir2d, dist);
	}

}
