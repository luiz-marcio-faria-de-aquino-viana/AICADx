/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdMove.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 11/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdMove extends CmdBase
{
//Public

	public CmdMove() {
		super(AppDefs.ACTION_EDIT2_MOVE);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Move object...");
		
		MainPanel panel = frm.getMainPanel();

		ICompView v = panel.getCurrView();

		CadEntity oEnt = PromptUtil.selectObject("Select object: ");
		if(oEnt == null) return null;

		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(null, "Base point: ");
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		v.setDragmode(AppDefs.DRAGMODE_DRAGOBJECT);

		oEnt.setSelected(true);

		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(pt2dI, "Second point: ");
		
		oEnt.setSelected(false);

		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
		
		v.setDragmode(AppDefs.DRAGMODE_NONE);

		oEnt.setSelected(false);

		result = new InputParamVO();
		result.initEntity(oEnt, pt3dI, pt3dF);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		//CADPOINT
		//
		CadEntity oEnt = oParam.getEnt1();
		oEnt.reset();

		GeomPoint3d ptI = oParam.getPt0();
		GeomPoint3d ptF = oParam.getPt1();
		
		if(oEnt != null) {
			oEnt.moveTo(ptI, ptF);
		}
	}

}
