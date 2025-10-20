/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdScale.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 11/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdScale extends CmdBase
{
//Public
	
	public CmdScale() {
		super(AppDefs.ACTION_EDIT2_SCALE, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Scale object...");
		
		MainPanel panel = (MainPanel)frm.getPanel();

		ICompView v = panel.getCurrView();

		CadEntity oEnt = PromptUtil.selectObject("Select object: ");
		if(oEnt == null) return null;

		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(null, "Base point: ");
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		GeomPoint2d pt2dRef = PromptUtil.getSecondPoint2d(pt2dI, "Reference point: ");
		if(pt2dRef == null) return null;
		
		GeomPoint3d pt3dRef = new GeomPoint3d(pt2dRef);
	
		double dist = pt3dI.distTo(pt3dRef);
		
		v.setDragmode(AppDefs.DRAGMODE_DRAGOBJECT);

		oEnt.setSelected(true);

		GeomPoint2d pt2dF = PromptUtil.getScalePoint2d(dist, pt2dI, "Scale point: ");
		
		oEnt.setSelected(false);

		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);

		v.setDragmode(AppDefs.DRAGMODE_NONE);

		oEnt.setSelected(false);

		result = new InputParamVO();
		result.initEntity(dist, oEnt, pt3dI, pt3dF);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		//CADPOINT
		//
		CadEntity oEnt = oParam.getEnt1();
		oEnt.reset();

		GeomPoint3d ptI = oParam.getPt0();
		GeomPoint3d ptF = oParam.getPt1();
		
		double refDist = oParam.getDist();
		
		if(oEnt != null) {
			oEnt.scaleTo(refDist, ptI, ptF);
		}
	}

}
