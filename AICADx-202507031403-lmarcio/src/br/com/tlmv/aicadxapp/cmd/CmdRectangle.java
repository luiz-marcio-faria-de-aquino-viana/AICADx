/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdRectangle.java
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
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdRectangle extends CmdBase
{
//Public

	public CmdRectangle() {
		super(AppDefs.ACTION_DRAW1_RECTANGLE);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Rectangle...");

		GeomPoint2d pt2dI = PromptUtil.getFirstCorner2d(null, "First corner: ");
		if(pt2dI == null) return null;

		GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(pt2dI, "Second corner: ");
		if(pt2dF == null) return null;
		
		GeomPoint3d ptMin3d = GeomPoint3d.lowerLeftCornerFrom(pt2dI, pt2dF);
		GeomPoint3d ptMax3d = GeomPoint3d.upperRightCornerFrom(pt2dI, pt2dF);
			
		result = new InputParamVO();
		result.initRectangle(ptMin3d, ptMax3d);
		
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
			
			//CADRECTANGLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadRectangle oRectangle = CadRectangle.create(oLayer, ptMin3d, ptMax3d);
			currBlockDef.addEntity(oRectangle);
			
			oParam = this.promptInputParam(this.getFrm(), oParam);			
		}
	}

}
