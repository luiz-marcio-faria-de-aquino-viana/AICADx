/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdUndo.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/02/2025
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
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.UndoItemVO;

public class CmdUndo extends CmdBase
{
//Public

	public CmdUndo() {
		super(AppDefs.ACTION_EDIT2_UNDO, false, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_EDIT_UNDO ) );

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() {
		if( !AppDefs.ENABLE_UNDO_REDO ) {
			PromptUtil.prompt( this.getR().getString( R.ERR_COMANDO_INVALIDO_NAO_IMPLEMENTADO ) );
			return;
		}
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
	
			ArrayList<UndoItemVO> lsItem = currBlockDef.undo();
			int n = 0;
			for(UndoItemVO oItem : lsItem) {
				int operType = oItem.getOperType();
				if(operType == AppDefs.OPERTYPE_UNDO_MSPACE_VAL) {
					CadObject newObj = (CadObject)oItem.getNewObj();
					int objectId = newObj.getObjectId();
		
					CadObject oldObj = (CadObject)oItem.getOldObj();
					if(oldObj == null) {
						CadObject currObj = currBlockDef.getEntity(objectId);
						if(currObj != null) {
							currObj.setDeleted(true);
						}
					}
					else {
						CadObject currObj = currBlockDef.getEntity(objectId);
						if(currObj != null) {
							currObj.init(oldObj);
						}
					}
					n += 1;
				}		
			}
			
			String str = String.format(this.getR().getString( R.CMD_PRT_NUMBER_OF_AFECTED_ENTITIES ), n);
			PromptUtil.prompt(str);
		}		
	}

}
