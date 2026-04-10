/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdIfcOut.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2026
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

import java.awt.FileDialog;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdIfcOut extends CmdBase
{
//Public
	
	public CmdIfcOut() {
		super(AppDefs.ACTION_FILE_IFCOUT, false, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		String cdir = ctx.getHomeDir();		
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_FILE_IFCOUT ) );

		String strTitle = this.getR().getString( R.CMD_PRT_SELECT_IFC_FILE_TO_SAVE );

		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle(strTitle);
		dlg.setMode(FileDialog.SAVE);
		dlg.setDirectory(cdir);
		dlg.setModal(true);
		dlg.show();
		
		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fileName, this.getClass());

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fullFileName, this.getClass());
		
		result = new InputParamVO();
		result.initFileName(fullFileName);
		return result;
	}
		
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();

		//TODO:
	}
	
	/* Getters/Setters */
	
}
