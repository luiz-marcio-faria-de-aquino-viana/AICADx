/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdTorus3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadTorus3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdTorus3D extends CmdBase
{
//Public
	
	public CmdTorus3D() {
		super(AppDefs.ACTION_DRAW3D_TORUS3D, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Torus 3D...");

		GeomPoint2d ptCenter2d = PromptUtil.getCenter2d(null, null, "Center point: ");
		if(ptCenter2d == null) return null;
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		double radius = PromptUtil.getRadius(ptCenter2d, "Radius: ");
		PromptUtil.promptDist(radius);

		double torusRadius = PromptUtil.getRadius(ptCenter2d, "Torus Radius: ");
		PromptUtil.promptDist(torusRadius);

		result = new InputParamVO();
		result.initTorus(ptCenter3d, radius, torusRadius);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptCenter3d = oParam.getPtCenter(); 
			double radius = oParam.getRadius(); 
			double torusRadius = oParam.getTorusRadius(); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadTorus3d oTorus = CadTorus3d.create(currBlockDef, oLayer, ptCenter3d, radius, torusRadius);
			currBlockDef.addEntity(oTorus);
	
			//oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
