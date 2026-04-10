/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdMirror.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
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
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.UndoTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdMirror extends CmdBase
{
//Private
	
	private ArrayList<PromptOptionVO> getPromptOptionMirrorMode()
	{
		/* PromptOption
		*/
		PromptOptionVO optMirrorModeDuplicateYes = new PromptOptionVO(
			AppDefs.OPT_MIRRORMODE_DUPLICATE_YES_VAL, this.getR().getString( R.CMD_OPT_MIRRORMODE_DUPLICATE_YES ), "Y", true);

		PromptOptionVO optMirrorModeDuplicateNo = new PromptOptionVO(
				AppDefs.OPT_MIRRORMODE_DUPLICATE_NO_VAL, this.getR().getString( R.CMD_OPT_MIRRORMODE_DUPLICATE_NO ), "N", false);

		/* ListOfPromptOptions
		*/
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();

		lsPromptOptions.add(optMirrorModeDuplicateYes);
		lsPromptOptions.add(optMirrorModeDuplicateNo);
		
		return lsPromptOptions;
	}	
		
//Public

	public CmdMirror() {
		super(AppDefs.ACTION_EDIT2_MIRROR, true, true);
	}	
	
	/* Methodes */

	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		MainPanel panel = (MainPanel)frm.getPanel();

		ICompView v = panel.getCurrView();

		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_EDIT_MIRROR) );
		
		int[] arrObjType = { AppDefs.OBJTYPE_ANY };
		Hashtable currSelectioSet = this.promptSelectioSet_basic(panel, currBlockDef, arrObjType);
		if(currSelectioSet.size() == 0) return null;

		ArrayList<CadEntity> lsEntities = super.getSelectionSet(currSelectioSet);

		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_FIRST_POINT));
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		v.setDragmode(AppDefs.DRAGMODE_DRAGOBJECT);

		updateSelectionSet(currBlockDef, currSelectioSet, true);
		panel.refreshAll();
		
		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_POINT));

		updateSelectionSet(currBlockDef, currSelectioSet, false);
		panel.refreshAll();

		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);

		v.setDragmode(AppDefs.DRAGMODE_NONE);
		
		ArrayList<PromptOptionVO> lsPromptOptions = this.getPromptOptionMirrorMode();

		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString(R.CMD_PRT_CHOICE_SELECTION_TYPE), true);

		result = new InputParamVO();
		result.initEntity(oKeyword, lsEntities, pt3dI, pt3dF);
		
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
				currBlockDef.beginTrans();
					
				GeomPoint3d ptI = oParam.getPt0();
				GeomPoint3d ptF = oParam.getPt1();
		
				PromptOptionVO oKeyword = oParam.getKeyword();
				
				for(CadEntity oEnt : lsEntities) {
					if( oEnt.isDeleted() ) continue;
					oEnt.setSelected(false);
					
					if( oKeyword.getOptionId() == AppDefs.OPT_MIRRORMODE_DUPLICATE_YES_VAL ) {
						CadEntity other = (CadEntity)oEnt.duplicate(currBlockDef); 
						other.mirror(ptI, ptF);
	
						currBlockDef.addEntity(other);
					}
					else {
						Object oldEnt = oEnt.duplicate();
						
						oEnt.mirror(ptI, ptF);
	
						Object newEnt = oEnt.duplicate();
						currBlockDef.saveTrans(oldEnt, newEnt);
					}				
				}
				
				currBlockDef.endTrans();
			}
			
		}
		
	}

}
