/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdRectArray.java
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
import br.com.tlmv.aicadxapp.cad.tables.UndoTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdRectArray extends CmdBase
{
//Private Static
	private static int gNumRows = 2;
	private static int gNumCols = 4;

	private static double gRowDist = 0.5;
	private static double gColDist = 0.5;
	
//Public
	
	public CmdRectArray() {
		super(AppDefs.ACTION_EDIT2_RECT_ARRAY, true, true);
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
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_EDIT_RECT_ARRAY) );
		
		//SELECT_OBJECTS
		//
		int[] arrObjType = { AppDefs.OBJTYPE_ANY };
		Hashtable currSelectioSet = this.promptSelectioSet_basic(panel, currBlockDef, arrObjType);
		if(currSelectioSet.size() == 0) return null;

		ArrayList<CadEntity> lsEntities = super.getSelectionSet(currSelectioSet);

		//START_POINT
		//
		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_START_POINT) );
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_CORNER_OR_ENTER) );
		GeomPoint3d pt3dF = null;
		if(pt2dF != null) {
			pt3dF = new GeomPoint3d( pt2dF );
		}

		//NUMBER_OF_ROWS
		//
		String lblNumRows = String.format(
			this.getR().getString(R.CMD_PRT_NUMBER_OF_ROWS),
			CmdRectArray.gNumRows );
		
		String strNumRows = PromptUtil.getText(this, lblNumRows );

		int numRows = CmdRectArray.gNumRows;
		if( !StringUtil.isEmpty( strNumRows ) ) {
			numRows = StringUtil.safeInt(strNumRows);
			if(numRows < 1) {
				errmsg = this.getR().getString(R.ERR_NUMBER_OF_ROWS_HIGHT_THAN_ZERO);
				PromptUtil.prompt(errmsg);
				return null;
			}
		}		
		CmdRectArray.gNumRows = numRows;

		//NUMBER_OF_COLS
		//
		String lblNumCols = String.format(
			this.getR().getString(R.CMD_PRT_NUMBER_OF_COLS),
			CmdRectArray.gNumCols );
				
		String strNumCols = PromptUtil.getText(this, this.getR().getString(lblNumCols) );

		int numCols = CmdRectArray.gNumCols;
		if( !StringUtil.isEmpty( strNumCols ) ) {
			numCols = StringUtil.safeInt(strNumCols);
			if(numCols < 1) {
				errmsg = this.getR().getString(R.ERR_NUMBER_OF_COLS_HIGHT_THAN_ZERO);
				PromptUtil.prompt(errmsg);
				return null;
			}
		}
		CmdRectArray.gNumCols = numCols;

		if(pt3dF == null) {
		
			//ROW_DISTANCE
			//
			String lblRowDist = String.format(
				this.getR().getString(R.CMD_PRT_DISTANCE_BETWEEN_ROWS),
				nf3.format( CmdRectArray.gRowDist ) );
					
			double rowDist = CmdRectArray.gRowDist;
			String strRowDist = PromptUtil.getText(this, lblRowDist );
			if( !StringUtil.isEmpty( strRowDist ) ) {
				rowDist = StringUtil.safeDbl(nf3, strRowDist);
				if(rowDist <= AppDefs.MATHPREC_MIN) {
					errmsg = this.getR().getString(R.ERR_DISTANCE_BETWEEN_ROWS);
					PromptUtil.prompt(errmsg);
					return null;
				}
			}	
			CmdRectArray.gRowDist = rowDist;
			
			//COL_DISTANCE
			//
			String lblColDist = String.format(
				this.getR().getString(R.CMD_PRT_DISTANCE_BETWEEN_COLS),
				nf3.format( CmdRectArray.gColDist ) );
					
			double colDist = CmdRectArray.gColDist;
			String strColDist = PromptUtil.getText(this, lblColDist );
			if( !StringUtil.isEmpty( strColDist ) ) {
				colDist = StringUtil.safeDbl(nf3, strColDist);
				if(colDist <= AppDefs.MATHPREC_MIN) {
					errmsg = this.getR().getString(R.ERR_DISTANCE_BETWEEN_COLS);
					PromptUtil.prompt(errmsg);
					return null;
				}
			}	
			CmdRectArray.gColDist = colDist;
		}
		else {
			double xI = pt3dI.getX();
			double yI = pt3dI.getY();
			//double zI = pt3dI.getZ();
			
			double xF = pt3dF.getX();
			double yF = pt3dF.getY();
			//double zF = pt3dF.getZ();
				
			int nDivRow = CmdRectArray.gNumRows - 1;
			if(nDivRow > 0) {
				CmdRectArray.gRowDist = (yF - yI) / nDivRow;
			}

			int nDivCol = CmdRectArray.gNumCols - 1;
			if(nDivCol > 0) {
				CmdRectArray.gColDist = (xF - xI) / nDivCol;
			}
		}
		
		result = new InputParamVO();
		result.initEntity(
			lsEntities, 
			pt3dI, 
			pt3dF, 
			CmdRectArray.gNumRows,
			CmdRectArray.gNumCols,
			CmdRectArray.gRowDist,
			CmdRectArray.gColDist );		
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

				GeomPoint3d ptCentroid0 = oEnt0.centroid();

				double xCen0 = ptCentroid0.getX();
				double yCen0 = ptCentroid0.getY();
				
				GeomPoint3d ptI = oParam.getPt0();
				
				double xI = ptI.getX();
				double yI = ptI.getY();
				double zI = ptI.getZ();

				double xVectCen0ToI = xI - xCen0;
				double yVectCen0ToI = yI - yCen0;
				
				int numRows = oParam.getNumRows();
				int numCols = oParam.getNumCols();

				double rowDist = oParam.getRowDist();
				double colDist = oParam.getColDist();
				
				currBlockDef.beginTrans();
								
				double y0 = yI;
				for(int currRow = 0; currRow < numRows; currRow++) {
					double x0 = xI;

					for(int currCol = 0; currCol < numCols; currCol++) {
						GeomPoint3d pt0 = new GeomPoint3d(x0 + xVectCen0ToI, y0 + yVectCen0ToI, zI);
						
						for(CadEntity oEnt : lsEntities) {
							if( oEnt.isDeleted() ) continue;
							if( oEnt.isSelected() )
								oEnt.setSelected(false);

							CadEntity other = (CadEntity)oEnt.copyTo(ptI, pt0);
							currBlockDef.addEntity(other);
						}
						x0 += colDist;
					}
					y0 += rowDist;
				}
				
				currBlockDef.endTrans();
			}
			
		}
		
	}

}
