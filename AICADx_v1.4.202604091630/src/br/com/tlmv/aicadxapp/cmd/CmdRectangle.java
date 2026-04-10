/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdRectangle.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
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

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdRectangle extends CmdBase
{
//Public

	public CmdRectangle() {
		super(AppDefs.ACTION_DRAW1_RECTANGLE, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Rectangle...");

		GeomPoint2d pt2dI = PromptUtil.getFirstCorner2d(this, null, "First corner: ");
		if(pt2dI == null) return null;

		GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, "Second corner: ");
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
			GeomPoint3d ptMin3d_orig = oParam.getPtMin();
			GeomPoint3d ptMax3d_orig = oParam.getPtMax();

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d ptMin3d = GeomUtil.toLevelFromPt3d(ptMin3d_orig, oLevel); 
			GeomPoint3d ptMax3d = GeomUtil.toLevelFromPt3d(ptMax3d_orig, oLevel); 
			
			//CADRECTANGLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadRectangle oRectangle = CadRectangle.create(currBlockDef, oLayer, oLevel, ptMin3d, ptMax3d);
			currBlockDef.addEntity(oRectangle);

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);			
		}
	}

}
