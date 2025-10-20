/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdText.java
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

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdText extends CmdBase
{
//Public
	
	public CmdText() {
		super(AppDefs.ACTION_DRAW1_TEXT, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Text...");
		
		GeomPoint2d pt2d = PromptUtil.getPoint2d(null, "Pick point: ");
		if(pt2d == null) return null;
		
		GeomPoint3d pt3d = new GeomPoint3d(pt2d);
		
		double textHeight = 0.0;

		if(oParam != null)
			textHeight = oParam.getTextHeight();

		if(textHeight < AppDefs.MATHPREC_MIN) {
			textHeight = PromptUtil.getTextHeight("Height: ");
			if(textHeight == AppDefs.MATHPREC_MIN) return null;		
		}
		
		String text = PromptUtil.getText("Text: ");
		if(text == null) return null;		
		
		result = new InputParamVO();
		result.initText(pt3d, textHeight, text);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		LayerTable oTbl = this.getDoc().getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_TEXTOS);
		
		GeomPoint3d pt3d = null;
		double textHeight = 0.0;
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			//GEOMPOINT3D
			//
			pt3d = oParam.getPt0();
			textHeight = oParam.getTextHeight();
			
			//CADTEXT
			//
			CadText oText = CadText.create(currBlockDef, oLayer, oParam.getText(), pt3d, textHeight);
			currBlockDef.addEntity(oText);
			
			//oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
