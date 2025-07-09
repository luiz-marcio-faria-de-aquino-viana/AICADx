/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdBox3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdBox3D extends CmdBase
{
//Public
	
	public CmdBox3D() {
		super(AppDefs.ACTION_DRAW1_BOX3D);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Box 3D...");

		GeomPoint2d pt2dI = PromptUtil.getFirstCorner2d(null, "First corner: ");
		if(pt2dI == null) return null;
		
		GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(pt2dI, "Second corner: ");
		if(pt2dF == null) return null;

		double height = PromptUtil.getTextHeight("Height: ");
		if(height == 0.0) return null;
		
		GeomPoint3d ptMin3d = GeomPoint3d.lowerLeftCornerFrom(pt2dI, pt2dF);
		GeomPoint3d ptMax3d = GeomPoint3d.upperRightCornerFrom(pt2dI, pt2dF);
			
		result = new InputParamVO();
		result.initRectangle(ptMin3d, ptMax3d, height);
		
		return result;
	}	
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptMin3d = oParam.getPtMin();

			GeomPoint3d ptMax3d = oParam.getPtMax();
		
			double height = oParam.getHeight();
			
			//CADBOX3D
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadBox3d oBox3D = CadBox3d.create(oLayer, ptMin3d, ptMax3d, height);
			currBlockDef.addEntity(oBox3D);
			
			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
		
	}

}
