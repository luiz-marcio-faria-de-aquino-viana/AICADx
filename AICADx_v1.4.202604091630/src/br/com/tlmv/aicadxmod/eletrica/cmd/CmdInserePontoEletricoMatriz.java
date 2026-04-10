/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInserePontoEletricoMatriz.java
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

package br.com.tlmv.aicadxmod.eletrica.cmd;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.UndoTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.vo.PontoEletricoVO;

public class CmdInserePontoEletricoMatriz extends CmdBase
{
//Private Static
	private static int gNumRows = 2;
	private static int gNumCols = 4;
	
//Public
	
	public CmdInserePontoEletricoMatriz() {
		super(AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO_MATRIZ, true, true);
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
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_ELE1_INSERE_PONTO_ELETRICO_MATRIZ) );
		
		//START_POINT
		//
		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_FIRST_CORNER) );
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		//END_POINT
		//
		GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_CORNER) );
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
		
		GeomPoint3d ptMin3d = GeomPoint3d.lowerLeftCornerFrom(pt2dI, pt2dF);
		GeomPoint3d ptMax3d = GeomPoint3d.upperRightCornerFrom(pt2dI, pt2dF);
		
		//NUMBER_OF_ROWS
		//
		String lblNumRows = String.format(
			this.getR().getString(R.CMD_PRT_NUMBER_OF_ROWS),
			CmdInserePontoEletricoMatriz.gNumRows );
		
		String strNumRows = PromptUtil.getText(this, lblNumRows );

		int numRows = CmdInserePontoEletricoMatriz.gNumRows;
		if( !StringUtil.isEmpty( strNumRows ) ) {
			numRows = StringUtil.safeInt(strNumRows);
			if(numRows < 1) {
				errmsg = this.getR().getString(R.ERR_NUMBER_OF_ROWS_HIGHT_THAN_ZERO);
				PromptUtil.prompt(errmsg);
				return null;
			}
		}		
		CmdInserePontoEletricoMatriz.gNumRows = numRows;

		//NUMBER_OF_COLS
		//
		String lblNumCols = String.format(
			this.getR().getString(R.CMD_PRT_NUMBER_OF_COLS),
			CmdInserePontoEletricoMatriz.gNumCols );
				
		String strNumCols = PromptUtil.getText(this, this.getR().getString(lblNumCols) );

		int numCols = CmdInserePontoEletricoMatriz.gNumCols;
		if( !StringUtil.isEmpty( strNumCols ) ) {
			numCols = StringUtil.safeInt(strNumCols);
			if(numCols < 1) {
				errmsg = this.getR().getString(R.ERR_NUMBER_OF_COLS_HIGHT_THAN_ZERO);
				PromptUtil.prompt(errmsg);
				return null;
			}
		}
		CmdInserePontoEletricoMatriz.gNumCols = numCols;

		result = new InputParamVO();
		result.initEntity(
			ptMin3d, 
			ptMax3d, 
			CmdInserePontoEletricoMatriz.gNumRows,
			CmdInserePontoEletricoMatriz.gNumCols );		
		return result;
	}
		
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//CADPOINT
			//
			GeomPoint2d pt2dI = new GeomPoint2d( oParam.getPt0() );
			GeomPoint2d pt2dF = new GeomPoint2d( oParam.getPt1() );
			int numRows = oParam.getNumRows();
			int numCols = oParam.getNumCols();
			
			double xI = pt2dI.getX();
			double yI = pt2dI.getY();

			double xF = pt2dF.getX();
			double yF = pt2dF.getY();

			int nDivRow = numRows + 1;
			double rowDist = (yF - yI) / nDivRow;

			int nDivCol = numCols + 1;
			double colDist = (xF - xI) / nDivCol;
			
			// LEVEL
			//
			double zLevel = 0.0;
			
			CadLevel oLevel = GeomUtil.getCurrLevel();    	
			if(oLevel != null) {
				zLevel = oLevel.getZLevel();
			}
			
			// LAYER
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_PONTOS);

			// PONTO_ELETRICO
			EletricaModule mod = this.getApp().getElModule();
			PontoEletricoVO currPontoEletrico = mod.getCurrPontoEletrico();
			if(currPontoEletrico == null) return;		

			String shapeFileName = currPontoEletrico.getFamilia();
			String shapeName = FileUtil.getFileNameEx(shapeFileName);			
			
			double zH = currPontoEletrico.getAlturaPadrao();
			
			double rotate = 0.0;
			
			//-- SHAPE
			ShapeTable shapeTable = this.getDoc().getShapeTable();
						
			Shape oShape = shapeTable.getShape(shapeName);
			if(oShape != null) {
				currBlockDef.beginTrans();
								
				double y0 = yI + rowDist;
				for(int currRow = 0; currRow < numRows; currRow++) {
					double x0 = xI + colDist;

					for(int currCol = 0; currCol < numCols; currCol++) {
						GeomPoint3d pt0 = new GeomPoint3d(x0, y0, zH);
						
						CadPontoEletrica o = CadPontoEletrica.create(
							currBlockDef, 
							oLayer, 
							oLevel, 
							pt0, 
							rotate, 
							oShape);
						currBlockDef.addEntity(o);

						x0 += colDist;
					}
					y0 += rowDist;
				}
				
				currBlockDef.endTrans();
			}
			
		}
		
	}

}
