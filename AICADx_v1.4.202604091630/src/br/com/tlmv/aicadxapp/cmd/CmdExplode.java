/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdExplode.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 11/01/2026
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
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdExplode extends CmdBase
{
//Public

	public CmdExplode() {
		super(AppDefs.ACTION_EDIT2_EXPLODE, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("DXF Explode object...");
		
		CadEntity oEnt = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, "Select object: ");
		if(oEnt == null) return null;
		
		result = new InputParamVO();
		result.initEntity(oEnt);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		CadBlockDef oCurrBlkDef = this.getDoc().getCurrBlockDef();
		
		BlockTable blkTbl = this.getDoc().getBlockTable();

		//CADPOINT
		//
		CadInsertBlock oEnt = (CadInsertBlock)oParam.getEnt1();
		String blkName = oEnt.getBlockName();
		GeomPoint3d ptIns = new GeomPoint3d( oEnt.getPtIns() );

		GeomPoint3d ptBase = new GeomPoint3d(0.0, 0.0, 0.0);
		
		CadBlockDef oBlkDef = blkTbl.getBlockDef(blkName);
		if(oBlkDef != null) {
			CadEntity[] arr = oBlkDef.findAllEntity();
			for(CadEntity ent : arr) {
				CadEntity oNewEnt = (CadEntity)ent.duplicate(oCurrBlkDef);
				oNewEnt.moveTo(ptBase, ptIns);
				oCurrBlkDef.addEntity(oNewEnt);
			}
			oEnt.setDeleted(true);		
		}

	}

}
