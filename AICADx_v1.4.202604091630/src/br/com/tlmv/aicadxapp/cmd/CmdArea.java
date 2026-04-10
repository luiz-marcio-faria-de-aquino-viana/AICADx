/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdArea.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
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
import br.com.tlmv.aicadxapp.cad.CadArea;
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
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdArea extends CmdBase
{
//Private

	/* Methodes */
	
	private ArrayList<PromptOptionVO> getPromptOptionAreaType()
	{
		/* PromptOption
		*/
		PromptOptionVO optAmbiente = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_ROOM, this.getR().getString( R.CMD_OPT_AREATYPE_ROOM ), "A", false);
		PromptOptionVO optApartamento = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_APARTMENT, this.getR().getString( R.CMD_OPT_AREATYPE_APARTMENT ), "P", true);
		PromptOptionVO optVaranda = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BALCONY, this.getR().getString( R.CMD_OPT_AREATYPE_BALCONY ), "V", false);
		PromptOptionVO optAreaComum = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BUILDINGCOMMOM, this.getR().getString( R.CMD_OPT_AREATYPE_BUILDINGCOMMOM ), "C", false);
		PromptOptionVO optAreaInterna = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BUILDINGINTERNAL, this.getR().getString( R.CMD_OPT_AREATYPE_BUILDINGINTERNAL ), "I", false);
		PromptOptionVO optAreaExterna = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BUILDINGEXTERNAL, this.getR().getString( R.CMD_OPT_AREATYPE_BUILDINGEXTERNAL ), "E", false);
		PromptOptionVO optEstacionamento = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_PARKING, this.getR().getString( R.CMD_OPT_AREATYPE_PARKING ), "S", false);
		PromptOptionVO optTerreno = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_TERRAIN, this.getR().getString( R.CMD_OPT_AREATYPE_TERRAIN ), "T", false);

		/* ListOfPromptOptions
		*/
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();

		lsPromptOptions.add(optAmbiente);
		lsPromptOptions.add(optApartamento);
		lsPromptOptions.add(optVaranda);
		lsPromptOptions.add(optAreaComum);
		lsPromptOptions.add(optAreaInterna);
		lsPromptOptions.add(optAreaExterna);
		lsPromptOptions.add(optEstacionamento);
		lsPromptOptions.add(optTerreno);

		return lsPromptOptions;
	}	
	
//Public
	
	public CmdArea() {
		super(AppDefs.ACTION_DRAW1_AREA, true, true);		
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>();
		ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>();

		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_DRAW_AREA ) );

		ArrayList<PromptOptionVO> lsPromptOptions = this.getPromptOptionAreaType();
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString( R.CMD_PRT_CHOICE_AREA_TYPE ) );
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, this.getR().getString( R.CMD_PRT_AREA_NAME ) );
		if(strName == null) return null;

		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_START_POINT ) );
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);

		GeomPoint2d pt02d = new GeomPoint2d(ptI2d);
		GeomPoint3d pt03d = new GeomPoint3d(ptI3d);		
		
		lsPts2d.add(ptI2d);		
		lsPts3d.add(ptI3d);		

		for( ; ; ) {
			GeomPoint2d ptF2d = PromptUtil.getSecondPoint2d(this, ptI2d, lsPts2d, this.getR().getString( R.CMD_PRT_NEXT_POINT ) );
			if(ptF2d == null) break;
		
			GeomPoint3d ptF3d = new GeomPoint3d(ptF2d);

			lsPts2d.add(ptF2d);
			lsPts3d.add(ptF3d);
			
			ptI2d = ptF2d;
		}						
		lsPts2d.add(pt02d);
		lsPts3d.add(pt03d);
		
		result = new InputParamVO();
		result.initKeyArea(oKeyword, strName, lsPts3d);
		
		return result;
	}	
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//LIST OF TEXTOS/GEOMPOINT3D
			//
			PromptOptionVO oKeyword = oParam.getKeyword();
			String text = oParam.getText();
			ArrayList<GeomPoint3d> lsPts3d_orig = oParam.getLsPts3d(); 

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			ArrayList<GeomPoint3d> lsPts3d = GeomUtil.toLevelFromLsPts3d(lsPts3d_orig, oLevel); 
			
			//CADPOLYGON
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_AREAS);
			
			CadArea oArea = CadArea.create(currBlockDef, oLayer, oLevel, oKeyword.getOptionId(), text, lsPts3d);
			currBlockDef.addEntity(oArea);
		}
	}

}
