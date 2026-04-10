/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdImportarBacklistTransMar.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/08/2025
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

package br.com.tlmv.aicadxmod.transmar.cmd;

import java.awt.FileDialog;
import java.io.FilenameFilter;
import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.filter.BaseFilenameFilter;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.transmar.frm.ControleBacklistTransMarFrame;

public class CmdImportarBacklistTransMar extends CmdBase
{
//Private
	
	private int loadBacklist(String fullFileName, boolean bOnlyDataEntry)
	{
		PromptUtil.prompt("Importando Backlist...");
		
		return AppDefs.RSOK;
	}
	
//Public

	public CmdImportarBacklistTransMar() {
		super(AppDefs.ACTION_TMAR1_IMPORTAR_BACKLIST, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("MARITIMO: Importar Backlist (XLS/XLSX)...");
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		String cdir = ctx.getHomeDir();		
		
		FilenameFilter ff = new BaseFilenameFilter(AppDefs.EXT_XLS); 
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle("Select Backlist File");
		dlg.setDirectory(cdir);
		dlg.setAutoRequestFocus(true);
		dlg.setFilenameFilter(ff);
		dlg.setAlwaysOnTop(true);
		dlg.setMode(FileDialog.LOAD);
		dlg.setMultipleMode(false);
		dlg.setModal(true);
		dlg.show();

		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL22, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL22, fileName, this.getClass());

		if( StringUtil.isEmpty(fileName) || StringUtil.isEmpty(dirName) ) 
			return null;

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL22, fullFileName, this.getClass());

		result = new InputParamVO();
		result.initDirName(dirName);

		return result;
	}

	@Override
	public void doCommand() 
	{
		this.getFrm().showToolbarControl(AppDefs.TOOLBARCTRL_BASIC, true);
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		String dirName = oParam.getDirName();

		String fileName = oParam.getFileName();
		
		String fullFileName = dirName + "/" + fileName;
				
		int rscode = this.loadBacklist(fullFileName, true);
		if(rscode == AppDefs.RSOK) {
			String warnmsg = String.format("Backlist carregado com sucesso (=%s).", dirName);
			PromptUtil.prompt(warnmsg);

			//LOAD_FORM
			//
			ControleBacklistTransMarFrame oFrm = new ControleBacklistTransMarFrame(this.getFrm()); 
			oFrm.init();
			oFrm.show();
		}
	}

	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args) {
		super.doExecuteCommand(app, frm, cad, doc, args);

		String dirName = AppDefs.DEF_BACKLIST_DIRNAME;

		String fileName = AppDefs.DEF_BACKLIST_FILENAME1;
		
		String fullFileName = dirName + "/" + fileName;
				
		int rscode = this.loadBacklist(fullFileName, true);
		if(rscode == AppDefs.RSOK) {
			String warnmsg = String.format("Backlist carregado com sucesso (=%s).", dirName);
			PromptUtil.prompt(warnmsg);

			//LOAD_FORM
			//
			ControleBacklistTransMarFrame oFrm = new ControleBacklistTransMarFrame(this.getFrm()); 
			oFrm.init();
			oFrm.show();
		}
	}

}
