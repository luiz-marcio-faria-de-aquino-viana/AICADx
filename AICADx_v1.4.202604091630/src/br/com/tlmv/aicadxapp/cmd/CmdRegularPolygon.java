/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPolygon.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/02/2025
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
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdRegularPolygon extends CmdBase
{
// Private Static
	private static int gNumVertices = AppDefs.DEF_REGPOLYGON_NUMVER;  
	
//Private

	/* Methodes */
	
	private ArrayList<PromptOptionVO> getPromptOptionRegPolygon()
	{
		/* PromptOption
		*/
		PromptOptionVO optRegPolygonInterior = new PromptOptionVO(
			AppDefs.OPT_REGPOLYGON_INTERIOR_VAL, this.getR().getString( R.CMD_OPT_REGPOLYGON_INTERIOR ), "I", true);

		PromptOptionVO optRegPolygonExterior = new PromptOptionVO(
			AppDefs.OPT_REGPOLYGON_EXTERIOR_VAL, this.getR().getString( R.CMD_OPT_REGPOLYGON_EXTERIOR ), "E", false);

		/* ListOfPromptOptions
		*/
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();

		lsPromptOptions.add(optRegPolygonInterior);
		lsPromptOptions.add(optRegPolygonExterior);

		return lsPromptOptions;
	}	
	
//Public

	public CmdRegularPolygon() {
		super(AppDefs.ACTION_DRAW1_REGULAR_POLYGON, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_DRAW_REGULAR_POLYGON ) );
		
		//CENTER_POINT
		//
		GeomPoint2d ptCenter2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_CENTER_POINT ) );
		if(ptCenter2d == null) return null;

		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		//RADIUS
		//
		GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(this, ptCenter2d, this.getR().getString( R.CMD_PRT_RADIUS ) );
		if(ptDir2d == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);
		
		double radius = ptCenter2d.distTo(ptDir2d);
		PromptUtil.promptDist(radius);

		//NUMBER_OF_VERTICES (DEFAULT = 5 - PENTAGONO)
		//
		String strPromptNumVer = String.format(
			this.getR().getString( R.CMD_PRT_NUMBER_OF_VERTICES ),
			CmdRegularPolygon.gNumVertices );

		String strNumVert = PromptUtil.getText(this, strPromptNumVer );
		if( !StringUtil.isEmpty(strNumVert) ) {
			int numVert = StringUtil.safeInt(strNumVert);
			if(numVert > 0) {
				CmdRegularPolygon.gNumVertices = numVert;
			}
		}

		//REGULAR_POLYGON (INTERIOR / EXTERIOR)
		//
		ArrayList<PromptOptionVO> lsPromptOptions = this.getPromptOptionRegPolygon();

		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString(R.CMD_PRT_CHOICE_REGULAR_POLYGON_TYPE), true);

		result = new InputParamVO();
		result.initPolygon(oKeyword, ptCenter3d, ptDir3d, radius, CmdRegularPolygon.gNumVertices);
		return result;
	}

	/* THREADS */
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//LIST OF GEOMPOINT3D
			//
			PromptOptionVO oKeyword = oParam.getKeyword();
			GeomPoint3d ptCenter3d = new GeomPoint3d( oParam.getPt0() );		
			GeomPoint3d ptDir3d = new GeomPoint3d( oParam.getPt1() );		
			double radiusInt = oParam.getRadius();		
			int numVertices = oParam.getIntVal();		

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			double zH = oLevel.getZLevelElevation();
			
			//CALCULATION
			//
			GeomPoint2d ptCenter2d = new GeomPoint2d( ptCenter3d );
			GeomPoint2d ptDir2d = new GeomPoint2d( ptDir3d );

			GeomVector2d vDir2d = new GeomVector2d(ptCenter2d, ptDir2d);
			GeomVector2d uDir2d = vDir2d.otherUnit();

			double stepAng = AppDefs.MATHVAL_2PI / numVertices;
			double stepAng2 = stepAng / 2.0;

			double radius = radiusInt;
			if(oKeyword.getOptionId() == AppDefs.OPT_REGPOLYGON_EXTERIOR_VAL) {
				if(stepAng2 > AppDefs.MATHPREC_MIN) {
					radius = radiusInt / Math.cos(stepAng2);
				}
			}
			
			ArrayList<GeomPoint3d> lsPts3d = oParam.getLsPts3d(); 

			GeomPoint2d pt0 = ptCenter2d.otherMoveTo(uDir2d, radius);
			lsPts3d.add( new GeomPoint3d( pt0, zH ) );

			for(int i = 0; i < numVertices; i++) {
				uDir2d.selfRotateToRad(stepAng);
				
				pt0 = ptCenter2d.otherMoveTo(uDir2d, radius);
				lsPts3d.add( new GeomPoint3d( pt0, zH ) );
			}
	
			//CADPOLYGON
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadPolygon oPolygon = CadPolygon.create(currBlockDef, oLayer, oLevel, lsPts3d);
			currBlockDef.addEntity(oPolygon);
		}
		
	}

}
