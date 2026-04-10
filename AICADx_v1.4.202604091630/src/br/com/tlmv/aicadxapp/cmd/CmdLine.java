/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdLine.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/02/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdLine extends CmdBase
{
//Public

	public CmdLine() {
		super(AppDefs.ACTION_DRAW1_LINE, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_DRAW_LINE) );

		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_START_POINT));
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);

		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(this, pt2dI, this.getR().getString(R.CMD_PRT_END_POINT));
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
			
		result = new InputParamVO();
		result.initLine(pt3dI, pt3dF);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d pt3dI_orig = oParam.getPt0(); 
			GeomPoint3d pt3dF_orig = oParam.getPt1(); 

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			oLevel.debug(AppDefs.DEBUG_LEVEL29);
			
			GeomPoint3d pt3dI = GeomUtil.toLevelFromPt3d(pt3dI_orig, oLevel); 
			GeomPoint3d pt3dF = GeomUtil.toLevelFromPt3d(pt3dF_orig, oLevel); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadLine oLine = CadLine.create(currBlockDef, oLayer, oLevel, pt3dI, pt3dF);
			currBlockDef.addEntity(oLine);			

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
