/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdSaveNoSql.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/12/2025
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

import br.com.tlmv.aicadxapp.AppConfig;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.AppNoSqlDb;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.OpenSaveNoSqlFrame;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class CmdSaveNoSql extends CmdBase
{
//Private
	
	private boolean saveNoSql(ProjectRepoVO projectRepo, CadDocumentDef doc)
	{
		AppMain app = AppMain.getApp();
		AppConfig cfg = app.getConfig();
		
		String nosqlDriver = AppDefs.DEF_DATABASE_DRIVER_NOSQL;

		AppNoSqlDb db = new AppNoSqlDb(projectRepo, nosqlDriver, true);				
		BaseDao dao = db.initNoSql();
		if(dao == null) return false;
		
		boolean bResult = projectRepo.existProjectDir();
		if( bResult ) {
			String objVer = UuidUtil.generateVersionNumber();
			
			bResult = db.saveNoSql(objVer, dao, doc);
		}
		return bResult;
	}
		
//Public

	public CmdSaveNoSql() {
		super(AppDefs.ACTION_FILE_SAVENOSQL, false, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Save to NoSql Database...");

		result = new InputParamVO();
		return result;
	}
	
	/* THREADS */
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		MainPanel panel = (MainPanel)this.getFrm().getPanel();
		
		CadDocumentDef doc = this.getDoc();
		
		ProjectRepoVO projectRepo = doc.getProjectRepo();

		String strActiveDatabase = projectRepo.getSafeName();
		
		boolean bResult = projectRepo.existProjectDir();
		if( !bResult ) {
			CompCommandPrompt commandPrompt = panel.getCommandPrompt();
			commandPrompt.setCommandPromptFocus(false);
				
			OpenSaveNoSqlFrame frm = new OpenSaveNoSqlFrame(this.getFrm());
			frm.init(
				true,
				false,
				strActiveDatabase,
				panel);
			frm.show();
		}
		else {
			bResult = this.saveNoSql(projectRepo, doc);
			if( !bResult ) {
				PromptUtil.prompt("ERR: Falha na gravacao do arquivo de dados do documento");
				return;				
			}
		}
	}

}
