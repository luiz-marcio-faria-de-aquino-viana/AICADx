/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInserePontoEletrico.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/09/2025
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

package br.com.tlmv.aicadxmod.eletrica.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.vo.PontoEletricoVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdInserePontoEletrico extends CmdBase
{
//Public
	
	public CmdInserePontoEletrico() {
		super(AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("ELETRICA: Adding new Electrical Elements...");
		
		String strNomeQuadro = "";
		if( AppDefs.ACTION_EL1_QDLF_INSERE_QUADRO_DISTRIBUICAO.equals(super.getCommandName()) ) {
			strNomeQuadro = PromptUtil.getText(this, "Nome do quadro:");
		}
		
		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(this, null, "Insert point: ");
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(this, ptIns2d, "Rotation: ");
		if(ptDir2d == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);
		
		result = new InputParamVO();
		result.initPointAndRotation(ptIns3d, ptDir3d, strNomeQuadro);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptIns3d = oParam.getPt0(); 
	
			GeomPoint3d ptDir3d = oParam.getPt1(); 
	
			String strNomeQuadro = oParam.getText();
			
			// LEVEL
			//
			double zLevel = 0.0;
			
			CadLevel oLevel = GeomUtil.getCurrLevel();    	
			if(oLevel != null) {
				zLevel = oLevel.getZLevel();
			}
			
			//CADSHAPE
			//
			//-- LAYER
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_PONTOS);

			//-- PONTO_ELETRICO
			EletricaModule mod = this.getApp().getElModule();
			PontoEletricoVO currPontoEletrico = mod.getCurrPontoEletrico();
			if(currPontoEletrico == null) return;		

			String shapeFileName = currPontoEletrico.getFamilia();
			String shapeName = FileUtil.getFileNameEx(shapeFileName);			
			
			//-- SHAPE
			ShapeTable shapeTable = this.getDoc().getShapeTable();
						
			Shape oShape = shapeTable.getShape(shapeName);
			if(oShape != null) {
				//PtIns3d
				//
				double xPtIns3d = ptIns3d.getX();
				double yPtIns3d = ptIns3d.getY();
				double zPtIns3d = zLevel + oShape.getDefaultZ();

				GeomPoint3d ptIns3d_final = new GeomPoint3d(xPtIns3d, yPtIns3d, zPtIns3d);

				//PtDir3d
				//
				double xPtDir3d = ptDir3d.getX();
				double yPtDir3d = ptDir3d.getY();
				double zPtDir3d = zLevel + oShape.getDefaultZ();

				GeomPoint3d ptDir3d_final = new GeomPoint3d(xPtDir3d, yPtDir3d, zPtDir3d);
				
				GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d_final);
				GeomPoint2d ptDir2d = new GeomPoint2d(ptDir3d_final);
				
				GeomVector2d vDir = new GeomVector2d(ptIns2d, ptDir2d);
				double rotateRad = vDir.angleToAxisX();
	
				double rotate = GeomUtil.convertRadToDegrees(rotateRad);
	
				CadPontoEletrica o = CadPontoEletrica.create(currBlockDef, oLayer, oLevel, ptIns3d_final, rotate, oShape);
				currBlockDef.addEntity(o);
				
				if( AppDefs.ACTION_EL1_QDLF_INSERE_QUADRO_DISTRIBUICAO.equals(super.getCommandName()) ) {
					CadParamEletricoOData oParamEletrico = o.getParamEletricoAt(0);
					oParamEletrico.setNomeQuadro(strNomeQuadro);
				}
			}
		}
	}
	
}
