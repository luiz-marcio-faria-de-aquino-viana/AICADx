/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdSaveDbase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
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
import br.com.tlmv.aicadxapp.AppSqlDb;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.OpenSaveDatabaseFrame;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class CmdSaveDbase extends CmdBase
{
//Private 
	
	private boolean existDatabase(ProjectRepoVO projectRepo)
	{
		AppMain app = AppMain.getApp();
		AppConfig cfg = app.getConfig();
		
		DatabaseConnectionVO dbConn = cfg.getDatabaseConnection();
		
		String dbaseDriver = dbConn.getDriver();

		AppSqlDb db = new AppSqlDb(projectRepo, dbaseDriver, true);				
		BaseDao dao = db.initDbase();
		if(dao == null) return false;
		
		String schemaName = projectRepo.getDbaseName();		

		boolean bResult = db.openDbase(dao, schemaName);
		if( bResult ) {
			bResult = db.existDbase(dao, schemaName);
			db.closeDbase(dao);
		}
		return bResult;
	}

	private boolean saveDatabase(ProjectRepoVO projectRepo, CadDocumentDef doc)
	{
		AppMain app = AppMain.getApp();
		AppConfig cfg = app.getConfig();
		
		DatabaseConnectionVO dbConn = cfg.getDatabaseConnection();
		
		String dbaseDriver = dbConn.getDriver();

		AppSqlDb db = new AppSqlDb(projectRepo, dbaseDriver, true);				
		BaseDao dao = db.initDbase();
		if(dao == null) return false;
		
		String schemaName = projectRepo.getDbaseName();		

		boolean bResult = db.openDbase(dao, schemaName);
		if( bResult ) {
			bResult = db.existDbase(dao, schemaName);
			if( bResult ) {
				String objVer = UuidUtil.generateVersionNumber();
				
				bResult = db.saveDbase(objVer, dao, schemaName, doc);
				db.closeDbase(dao);				
			}
		}
		return bResult;
	}
	
//Public

	public CmdSaveDbase() {
		super(AppDefs.ACTION_FILE_SAVEDBASE, false, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Save to Database...");

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
		if(doc == null) return;
		
		ProjectRepoVO projectRepo = doc.getProjectRepo();

		String strActiveDatabase = projectRepo.getSafeName();

		boolean bResult = this.existDatabase(projectRepo);
		if( !bResult ) {
			CompCommandPrompt commandPrompt = panel.getCommandPrompt();
			commandPrompt.setCommandPromptFocus(false);
				
			OpenSaveDatabaseFrame frm = new OpenSaveDatabaseFrame(this.getFrm());
			frm.init(
				true,
				false,
				false,
				false,
				true,
				strActiveDatabase,
				true,
				panel);
			frm.show();
		}
		else {
			bResult = this.saveDatabase(projectRepo, doc);
			if( !bResult ) {
				PromptUtil.prompt("ERR: Falha na gravacao do banco de dados do documento");
				return;				
			}
		}
	}

}
