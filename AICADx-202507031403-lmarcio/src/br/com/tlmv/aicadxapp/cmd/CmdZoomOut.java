/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdZoomOut.java
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
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdZoomOut extends CmdBase
{
//Public
	
	public CmdZoomOut() {
		super(AppDefs.ACTION_ZOOM_ZOOMOUT);
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
		
		PromptUtil.prompt("Zoom out...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		//if(oParam == null) return;
		
		//ZOOMOUT
		//
		MainPanel panel = MainPanel.getPanel();

		ICompView v = panel.getCurrView();
		
		ICadViewBase cv = v.getCadViewBase();
		cv.zoomScaleMcs(AppDefs.ZOOMMODE_ZOOMOUT_SCALEFACT);
	}

}
