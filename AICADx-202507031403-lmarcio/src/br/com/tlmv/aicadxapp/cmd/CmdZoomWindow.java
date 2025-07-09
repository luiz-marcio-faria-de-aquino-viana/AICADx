/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdZoomWindow.java
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdZoomWindow extends CmdBase
{
//Public
	
	public CmdZoomWindow() {
		super(AppDefs.ACTION_ZOOM_ZOOMWINDOW);
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
		
		PromptUtil.prompt("Zoom window...");

    	GeomPoint2d ptFirstCornerMcs = PromptUtil.zoomFirstCorner2d(null, "First corner: ");
		if(ptFirstCornerMcs == null) return null;
		
		GeomPoint2d ptSecondCornerMcs = PromptUtil.zoomSecondCorner2d(ptFirstCornerMcs, "Second corner: ");
		if(ptSecondCornerMcs == null) return null;

		GeomPoint3d ptMin3d = GeomPoint3d.lowerLeftCornerFrom(ptFirstCornerMcs, ptSecondCornerMcs);
		GeomPoint3d ptMax3d = GeomPoint3d.upperRightCornerFrom(ptFirstCornerMcs, ptSecondCornerMcs);
		
		result = new InputParamVO();
		result.initRectangle(ptMin3d, ptMax3d);

		return result;
	}
		
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		//if(oParam == null) return;
		
		//GEOMPOINT3D
		//
		GeomPoint2d ptMin2d = new GeomPoint2d(oParam.getPtMin());
		GeomPoint2d ptMax2d = new GeomPoint2d(oParam.getPtMax());
		
		//ZOOM_WINDOW
		//
		MainPanel panel = MainPanel.getPanel();

		ICompView v = panel.getCurrView();
		
		ICadViewBase cv = v.getCadViewBase();
		cv.zoomWindowMcs(ptMin2d, ptMax2d);
	}

}
