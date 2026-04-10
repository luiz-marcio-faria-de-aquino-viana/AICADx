/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdArc.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdArc extends CmdBase
{
//Public
	
	public CmdArc() {
		super(AppDefs.ACTION_DRAW1_ARCSCA, true, true);
	}
	
	/* Methodes */

	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_DRAW_ARC ) );

		GeomPoint2d ptStartPoint2d = PromptUtil.getStartPoint2d(this, null, null, this.getR().getString( R.CMD_PRT_START_POINT ) );
		if(ptStartPoint2d == null) return null;
		
		GeomPoint2d ptCenter2d = PromptUtil.getCenter2d(this, ptStartPoint2d, null, this.getR().getString( R.CMD_PRT_CENTER_POINT ) );
		if(ptCenter2d == null) return null;

		Double radius = ptCenter2d.distTo(ptStartPoint2d);
		if(radius == null) return null;

		PromptUtil.promptDist(radius);
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		GeomPoint2d ptEndPoint2d = PromptUtil.getEndPoint2d(this, ptCenter2d, ptStartPoint2d, this.getR().getString( R.CMD_PRT_END_POINT ) );
		if(ptEndPoint2d == null) return null;

        double startAngleRad = GeomUtil.angleFromAxisX(ptCenter2d, ptStartPoint2d);
        double endAngleRad = GeomUtil.angleFromAxisX(ptCenter2d, ptEndPoint2d);
        if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
        	endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;

		double startAngleDegrees = GeomUtil.convertRadToDegrees(startAngleRad);
		double endAngleDegrees = GeomUtil.convertRadToDegrees(endAngleRad);

		PromptUtil.promptAngles(startAngleDegrees, endAngleDegrees);
		
		result = new InputParamVO();
		result.initArc(ptCenter3d, radius, startAngleDegrees, endAngleDegrees);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptCenter3d_orig = oParam.getPtCenter(); 
			double radius = oParam.getRadius(); 
			double startAngle = oParam.getStartAngle();
			double endAngle = oParam.getEndAngle();		

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d ptCenter3d = GeomUtil.toLevelFromPt3d(ptCenter3d_orig, oLevel); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadArc oArc = CadArc.create(currBlockDef, oLayer, oLevel, ptCenter3d, radius, startAngle, endAngle);
			currBlockDef.addEntity(oArc);

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}
	
}
