/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdTroncoCone3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/05/2025
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
import br.com.tlmv.aicadxapp.cad.CadTroncoCone3d;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdTroncoCone3D extends CmdBase
{
//Public
	
	public CmdTroncoCone3D() {
		super(AppDefs.ACTION_DRAW3D_TRONCOCONE3D, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Tronco de Cone 3D...");

		GeomPoint2d ptCenter2d = PromptUtil.getCenter2d(this, null, null, "Center point: ");
		if(ptCenter2d == null) return null;
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		Double topRadius = PromptUtil.getRadius(this, ptCenter2d, "Top Radius: ");
		if(topRadius == null) return null;

		PromptUtil.promptDist(topRadius);

		Double baseRadius = PromptUtil.getRadius(this, ptCenter2d, "Base Radius: ");
		if(baseRadius == null) return null;

		PromptUtil.promptDist(baseRadius);

		String strAltura = PromptUtil.getText(this, "Altura: ");

		Double altura = StringUtil.safeDbl(nf3, strAltura);
		if(altura == null) return null;

		PromptUtil.promptAltura(altura);

		result = new InputParamVO();
		result.initCircle(ptCenter3d, topRadius, baseRadius, altura);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptCenter3d_orig = oParam.getPtCenter(); 
			double topRadius = oParam.getTopRadius(); 
			double baseRadius = oParam.getBaseRadius(); 
			double altura = oParam.getHeight(); 

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());

			GeomPoint3d ptCenter3d = GeomUtil.toLevelFromPt3d(ptCenter3d_orig, oLevel); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadTroncoCone3d oTroncoCone = CadTroncoCone3d.create(currBlockDef, oLayer, oLevel, ptCenter3d, topRadius, baseRadius, altura);
			currBlockDef.addEntity(oTroncoCone);
		}
	}

}
