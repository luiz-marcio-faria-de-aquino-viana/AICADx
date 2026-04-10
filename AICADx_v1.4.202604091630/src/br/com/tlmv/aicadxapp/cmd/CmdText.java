/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
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
			textHeight = PromptUtil.getTextHeight(this, "Height: ");
			if(textHeight == AppDefs.MATHPREC_MIN) return null;		
		}
		
		String text = PromptUtil.getText(this, "Text: ");
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

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			//CADTEXT
			//
			CadText oText = CadText.create(currBlockDef, oLayer, oLevel, oParam.getText(), pt3d, textHeight);
			currBlockDef.addEntity(oText);
		}
	}

}
