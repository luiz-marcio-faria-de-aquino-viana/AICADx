/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdLineIntersec.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/11/2025
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
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdLineIntersec extends CmdBase
{
//Public

	public CmdLineIntersec() {
		super(AppDefs.ACTION_DRAW1_LINEINTERSEC, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Line (INTersec)...");

		CadEntity oEnt1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_LINE, "Select object: ");
		if(oEnt1 == null) return null;

		GeomPoint2d ptRef = PromptUtil.getFirstPoint2d(this, null, "Start point: ");
		if(ptRef == null) return null;
		
		GeomPoint3d ptRef3d = new GeomPoint3d(ptRef);

		GeomPoint2d ptDir = PromptUtil.getSecondPoint2d(this, ptRef, "End point: ");
		if(ptDir == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir);

		result = new InputParamVO();
		result.initEntity(oEnt1, ptRef3d, ptDir3d);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptRef3d_orig = oParam.getPt0(); 
			GeomPoint3d ptDir3d_orig = oParam.getPt1();

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());			
			
			GeomPoint3d ptRef3d = GeomUtil.toLevelFromPt3d(ptRef3d_orig, oLevel); 
			GeomPoint3d ptDir3d = GeomUtil.toLevelFromPt3d(ptDir3d_orig, oLevel); 
			
			GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d ); 
			GeomPoint2d ptDir2d = new GeomPoint2d( ptDir3d ); 
			
			CadLine oLine = (CadLine)oParam.getEnt1();
			GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
			GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

			GeomPoint2d ptIntersec2d = GeomUtil.dirIntersectionOf(
				ptRef2d, 
				ptDir2d, 
				ptI2d, 
				ptF2d, 
				false);
			
			if(ptIntersec2d == null) {
				PromptUtil.prompt("Err: Nao foi encontrado o ponto perpendicular a reta.");
				return;
			}

			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			GeomPoint3d ptIntersec3d = new GeomPoint3d(
				ptIntersec2d.getX(),
				ptIntersec2d.getY(),
				ptRef3d.getZ() );
			CadLine oNewLine = CadLine.create(currBlockDef, oLayer, oLevel, ptRef3d, ptIntersec3d);
			currBlockDef.addEntity(oNewLine);

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
