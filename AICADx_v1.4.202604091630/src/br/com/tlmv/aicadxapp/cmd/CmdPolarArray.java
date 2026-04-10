/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPolarArray.java
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
import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.UndoTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdPolarArray extends CmdBase
{
//Private Static
	private static int gNumElem = 5;
	
//Public
	
	public CmdPolarArray() {
		super(AppDefs.ACTION_EDIT2_POLAR_ARRAY, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		MainPanel panel = (MainPanel)frm.getPanel();

		ICompView v = panel.getCurrView();

		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		String errmsg = "";
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_EDIT_POLAR_ARRAY) );
		
		//SELECT_OBJECTS
		//
		int[] arrObjType = { AppDefs.OBJTYPE_ANY };
		Hashtable currSelectioSet = this.promptSelectioSet_basic(panel, currBlockDef, arrObjType);
		if(currSelectioSet.size() == 0) return null;

		ArrayList<CadEntity> lsEntities = super.getSelectionSet(currSelectioSet);
		
		//CENTER_POINT
		//
		GeomPoint2d ptCenter2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_CENTER_POINT) );
		if(ptCenter2d == null) return null;
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		//START_POINT
		//
		GeomPoint2d ptStartPoint2d = PromptUtil.getSecondPoint2d(this, ptCenter2d, this.getR().getString(R.CMD_PRT_START_POINT) );
		if(ptStartPoint2d == null) return null;
		
		GeomPoint3d ptStartPoint3d = new GeomPoint3d(ptStartPoint2d);

		Double radius = ptCenter2d.distTo(ptStartPoint2d);
		if(radius == null) return null;

		PromptUtil.promptDist(radius);
		
		GeomPoint2d ptEndPoint2d = PromptUtil.getEndPoint2d(this, ptCenter2d, ptStartPoint2d, "End point: ");
		if(ptEndPoint2d == null) return null;
		
		GeomPoint3d ptEndPoint3d = new GeomPoint3d(ptEndPoint2d);

        double startAngleRad = GeomUtil.angleFromAxisX(ptCenter2d, ptStartPoint2d);
        double endAngleRad = GeomUtil.angleFromAxisX(ptCenter2d, ptEndPoint2d);
        if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
        	endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;

		double startAngleDegrees = GeomUtil.convertRadToDegrees(startAngleRad);
		double endAngleDegrees = GeomUtil.convertRadToDegrees(endAngleRad);

		PromptUtil.promptAngles(startAngleDegrees, endAngleDegrees);
		
		//NUMBER_OF_ELEMENTS
		//
		String lblNumElem = String.format(
			this.getR().getString(R.CMD_PRT_NUMBER_OF_ELEM),
			CmdPolarArray.gNumElem );
		
		String strNumElem = PromptUtil.getText(this, lblNumElem );

		int numElem = CmdPolarArray.gNumElem;
		if( !StringUtil.isEmpty( strNumElem ) ) {
			numElem = StringUtil.safeInt(strNumElem);
			if(numElem < 1) {
				errmsg = this.getR().getString(R.ERR_NUMBER_OF_ELEM_HIGHT_THAN_ZERO);
				PromptUtil.prompt(errmsg);
				return null;
			}
		}		
		CmdPolarArray.gNumElem = numElem;
		
		result = new InputParamVO();
		result.initEntity(
			lsEntities, 
			ptCenter3d, 
			ptStartPoint3d,
			ptEndPoint3d,
			startAngleRad,
			endAngleRad,
			numElem );
		return result;
	}
		
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//CADPOINT
			//
			ArrayList<CadEntity> lsEntities = oParam.getLsEntities();
			int sz = lsEntities.size();
			if(sz > 0) {

				CadEntity oEnt0 = lsEntities.get(0);

				GeomPoint3d ptCentroid0 = new GeomPoint3d( oEnt0.centroid() );

				double xCen0 = ptCentroid0.getX();
				double yCen0 = ptCentroid0.getY();
				
				GeomPoint3d ptCenter3d = new GeomPoint3d( oParam.getPt0() );
				double zH = ptCenter3d.getZ();
				
				GeomPoint2d ptCenter2d = new GeomPoint2d( ptCenter3d );
				
				double xCen1 = ptCenter2d.getX();
				double yCen1 = ptCenter2d.getY();

				double xVectCen0ToCen1 = xCen1 - xCen0;
				double yVectCen0ToCen1 = yCen1 - yCen0;

				GeomPoint2d ptStartPoint2d = new GeomPoint2d( oParam.getPt1() );
				
				int numElem = oParam.getNumElem();

				double startAngRad = oParam.getStartAngle();
				double endAngRad = oParam.getEndAngle();
				
				int nDivElem = numElem - 1;
				double stepAngRad = 0.0;
				if(nDivElem > 0) {
					stepAngRad = (endAngRad - startAngRad) / nDivElem;
				}
				
				currBlockDef.beginTrans();
								
				GeomVector2d vDir2d = new GeomVector2d(ptCenter2d, ptStartPoint2d);
				for(int currElem = 0; currElem < numElem; currElem++) {
					double x0 = vDir2d.getXF();
					double y0 = vDir2d.getYF();
					
					GeomPoint3d pt0 = new GeomPoint3d(x0 + xVectCen0ToCen1, y0 + yVectCen0ToCen1, zH);
					
					for(CadEntity oEnt : lsEntities) {
						if( oEnt.isDeleted() ) continue;
						if( oEnt.isSelected() )
							oEnt.setSelected(false);

						CadEntity other = (CadEntity)oEnt.copyTo(ptCenter3d, pt0);
						currBlockDef.addEntity(other);
					}
					
					vDir2d.selfRotateToRad(stepAngRad);					
				}
				
				currBlockDef.endTrans();
			}
			
		}
		
	}

}
