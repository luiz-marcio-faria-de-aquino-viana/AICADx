/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdTroncoCone3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadTroncoCone3d;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdTroncoCone3D extends CmdBase
{
//Public
	
	public CmdTroncoCone3D() {
		super(AppDefs.ACTION_DRAW1_TRONCOCONE3D);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Tronco de Cone 3D...");

		GeomPoint2d ptCenter2d = PromptUtil.getCenter2d(null, null, "Center point: ");
		if(ptCenter2d == null) return null;
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		double topRadius = PromptUtil.getRadius(ptCenter2d, "Top Radius: ");
		PromptUtil.promptDist(topRadius);

		double baseRadius = PromptUtil.getRadius(ptCenter2d, "Base Radius: ");
		PromptUtil.promptDist(baseRadius);

		String strAltura = PromptUtil.getText("Altura: ");

		double altura = StringUtil.safeDbl(nf3, strAltura);
		PromptUtil.promptAltura(altura);

		result = new InputParamVO();
		result.initCircle(ptCenter3d, topRadius, baseRadius, altura);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptCenter3d = oParam.getPtCenter(); 
			double topRadius = oParam.getTopRadius(); 
			double baseRadius = oParam.getBaseRadius(); 
			double altura = oParam.getHeight(); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadTroncoCone3d oTroncoCone = CadTroncoCone3d.create(oLayer, ptCenter3d, topRadius, baseRadius, altura);
			currBlockDef.addEntity(oTroncoCone);
	
			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
