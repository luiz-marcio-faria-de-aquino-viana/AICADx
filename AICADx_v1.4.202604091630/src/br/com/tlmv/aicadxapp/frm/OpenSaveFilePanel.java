/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * OpenSaveFilePanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
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

package br.com.tlmv.aicadxapp.frm;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppConfig;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppSqliteDb;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.model.FileDataModel;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxapp.vo.FileDataVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class OpenSaveFilePanel extends BasePanel
{
//Private
	private ArrayList<FileDataVO> lsFile = new ArrayList<FileDataVO>();

	private String strRepoDir = "";

	private String strActiveProject = "";
	
	private int rscode = AppDefs.RSCODE_OPENSAVEFILE_NONE;

	//FROM_CONTROLS
	//
	private JLabel lblSelectProject = null;
	private JLabel lblActiveProject = null;
	
	private JList lstSelectProject = null; 
	private JTextField txtActiveProject = null;
	
	private JButton btnSaveAs = null;
	private JButton btnOpen = null;
	private JButton btnFechar = null;

	private boolean bBtnSaveAs = true;
	private boolean bBtnOpen = true;
	
	private ArrayList<FileDataVO> loadAllFile(String strRepoDir)
	{
		ArrayList<FileDataVO> lsResult = new ArrayList<FileDataVO>();
		
		File repoDir = new File(strRepoDir);
		
		File[] arrFile = repoDir.listFiles();
		int szArrFile = arrFile.length;
		for(int i = 0; i < szArrFile; i++) {
			File f = arrFile[i];

			if( f.isDirectory() ) {
				String fullFileName = f.getAbsolutePath();
				String fileName = f.getName();
				String extension = FileUtil.getFileExtension(fileName);
				Date dataModificacao = new Date( f.lastModified() );
				boolean bFile = false;			
				
				FileDataVO oFile = new FileDataVO(
					fullFileName,
					fileName,
					extension,
					dataModificacao,
					bFile);
				lsResult.add( oFile );
			}					
		}
		return lsResult;
	}
	
	private boolean openFile(ProjectRepoVO projectRepo, CadDocumentDef doc)
	{
		AppMain app = AppMain.getApp();
		
		AppConfig cfg = app.getConfig();

		DatabaseConnectionVO dbConn = cfg.getDatabaseConnection();
		
		//String dbaseDriver = dbConn.getDriver();
		String dataFileDriver = AppDefs.DEF_DATABASE_DRIVER_SQLIGHT;
		//String noSqlDriver = AppDefs.DEF_DATABASE_DRIVER_NOSQL;
		
		AppSqliteDb db = new AppSqliteDb(projectRepo, dataFileDriver, false);
		BaseDao dao = db.initDataFile();
		if(dao == null) return false;
		
		boolean bResult = db.openDataFile( dao );
		if( bResult ) {
			String objVer = dao.selectLastObjVer(AppDefs.NULL_SCHEMA);
			
			bResult = db.loadDataFile(objVer, dao, doc);
			db.closeDataFile(dao);
		}
		return bResult;
	}
	
	private boolean saveFile(ProjectRepoVO projectRepo, CadDocumentDef doc)
	{
		AppMain app = AppMain.getApp();
		AppConfig cfg = app.getConfig();

		DatabaseConnectionVO dbConn = cfg.getDatabaseConnection();
		
		String dataFileDriver = AppDefs.DEF_DATABASE_DRIVER_SQLIGHT;
		
		AppSqliteDb db = new AppSqliteDb(projectRepo, dataFileDriver, true);
		BaseDao dao = db.initDataFile();
		if(dao == null) return false;
		
		boolean bResult = db.existDataFile(dao);
		if( !bResult ) {
			bResult = db.createDataFile(dao);
			if( !bResult ) return false;
		}
		else {
			bResult = db.openDataFile(dao);
			if( !bResult ) return false;
		}

		String objVer = AppDefs.NULL_INTSTR;
		
		bResult = db.saveDataFile(objVer, dao, doc);
		db.closeDataFile(dao);		
		return bResult;
	}
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		FileDataModel model = new FileDataModel( this.lsFile ); 
		
		Insets insets = this.getInsets();

		int xp = insets.left + 5;
		int yp = insets.top + 5;
		
		//FormFields
		//
		String resSelectDatabase = r.getString(R.LBL_SELECTPROJECT);
		this.lblSelectProject = FormControlUtil.newLabel(resSelectDatabase, xp, yp, AppDefs.LABEL_W800, AppDefs.LABEL_H20, true);
		this.add(this.lblSelectProject);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		this.lstSelectProject = FormControlUtil.newList(this, model, xp, yp, AppDefs.LIST_W800, AppDefs.LIST_H500, true);
		this.lstSelectProject.addListSelectionListener(this);
		this.add(this.lstSelectProject);
		yp += AppDefs.LIST_H500 + AppDefs.SPACE_H5;
		
		String resActiveProject = r.getString(R.LBL_ACTIVEPROJECT);
		this.lblActiveProject = FormControlUtil.newLabel(resActiveProject, xp, yp, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblActiveProject);
		xp += (AppDefs.LABEL_W200 + AppDefs.SPACE_H5);
		
		this.txtActiveProject = FormControlUtil.newTextField(this.strActiveProject, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtActiveProject);
		xp = insets.left + 5;
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		//Button
		//
		if( this.bBtnOpen ) {
			String resOpen = r.getString(R.BTN_ABRIR);
			this.btnOpen = FormControlUtil.newButton(resOpen, AppDefs.RSCODE_OPENSAVEFILE_OPEN, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnOpen);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( this.bBtnSaveAs ) {
			String resSaveAs = r.getString(R.BTN_GRAVAR_COMO);
			this.btnSaveAs = FormControlUtil.newButton(resSaveAs, AppDefs.RSCODE_OPENSAVEFILE_SAVEAS, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnSaveAs);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		xp = (insets.left + 5) + AppDefs.LIST_W800 - AppDefs.BUTTON_W125;

		String resFechar = r.getString(R.BTN_FECHAR);
		this.btnFechar = FormControlUtil.newButton(resFechar, AppDefs.RSCODE_OPENSAVEFILE_FECHAR, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnFechar);
		xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
	}
	
//Public 
	
	public OpenSaveFilePanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(
		boolean bBtnSaveAs, 
		boolean bBtnOpen, 
		String strActiveProject,
		ResultListener resultListener)
	{
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		this.bBtnSaveAs = bBtnSaveAs; 
		this.bBtnOpen = bBtnOpen; 
		
		this.strRepoDir = ctx.getRepositoryDir();

		this.strActiveProject = strActiveProject;
		
		this.lsFile = this.loadAllFile(strRepoDir);

		initForm();
	}
	
	/* Methodes */

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Validate */
		
	public boolean validateForm() 
	{
		String errmsg = "";
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		String tmpActiveProject = this.txtActiveProject.getText();
		this.strActiveProject = FileUtil.checkProjectName( tmpActiveProject );

		if( "".equals(this.strActiveProject) )
			errmsg += r.getString(R.ERR_PROJECT);
				
		if(errmsg != "") {
			String resCamposObrigatorios = r.getString(R.ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
			AppError.showErrorBox(this.getParentFrame(), resCamposObrigatorios, errmsg, this.getClass());
			return false;
		}

		return true;
	}
	
	/* Actions */
	
	public void doActionOpen(ActionEvent e) 
	{
		MainPanel panel = MainPanel.getMainPanel();

		CadDocumentDef doc = panel.getCurrDocumentDef();
		if(doc == null) {
			PromptUtil.prompt("ERR: Nenhum documento selecionado");
			return;
		}
		
		if( this.validateForm() ) 
		{		
			CadDocumentDef oNewDoc = cad.newCadDocumentDef();
			if(oNewDoc != null) {
				String strProjectName = this.strActiveProject;
	
				ProjectRepoVO projectRepo = new ProjectRepoVO(strProjectName);		
				oNewDoc.setProjectRepo(projectRepo);
				if( !projectRepo.existProjectDir() ) return;
				
	    		ViewTable viewTbl = oNewDoc.getViewTable();
	    		String strViewName = StringUtil.generateViewName(this.r, strProjectName, AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL);
				
	    		CompView oNewView = viewTbl.newPlanView(strViewName, 0);
	    		panel.addNewView(oNewDoc, oNewView);
				
				boolean bResult = this.openFile(projectRepo, oNewDoc);
				if( !bResult ) {
					PromptUtil.prompt("ERR: Falha na leitura do banco de dados do documento");
					return;				
				}
				
	    		int rscode = AppDefs.RSCODE_OPENSAVEFILE_OPEN;			
	    		this.parentFrame.actionResultListener(new ResultEvent(rscode, projectRepo));    			
	    		this.parentFrame.dispose();
			}
		}
	}

	public void doActionSaveAs(ActionEvent e) 
	{
		MainPanel panel = MainPanel.getMainPanel();

		CadDocumentDef doc = panel.getCurrDocumentDef();
		if(doc == null) {
			PromptUtil.prompt("ERR: Nenhum documento selecionado");
			return;
		}

		if( this.validateForm() ) {
			String strProjectName = this.strActiveProject;			

			ProjectRepoVO projectRepo = new ProjectRepoVO(strProjectName);		
			doc.setProjectRepo(projectRepo);
			
			if( !projectRepo.existProjectDir() ) {
				projectRepo.createProjectDir();
			}

			boolean bResult = this.saveFile(projectRepo, doc);
			if( !bResult ) {
				PromptUtil.prompt("ERR: Falha na gravacao do banco de dados do documento");
				return;				
			}
			
			rscode = AppDefs.RSCODE_OPENSAVEFILE_SAVEAS;			
			this.parentFrame.actionResultListener(new ResultEvent(rscode, projectRepo));
			this.parentFrame.dispose();
		}
	}
	
	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_OPENSAVEFILE_FECHAR;
		this.parentFrame.actionResultListener(new ResultEvent(rscode, null));
	
		this.parentFrame.dispose();
	}

	/* ACTION_EVENTS */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmdAction = StringUtil.safeInt(e.getActionCommand());
		
		if(cmdAction == AppDefs.RSCODE_OPENSAVEFILE_OPEN) {
			doActionOpen(e);
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEFILE_SAVEAS) {
			doActionSaveAs(e);
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEFILE_FECHAR) {
			doActionFechar(e);
		}
	}
	
	@Override
	public void actionResultListener(ResultEvent e) {
		/* nothing todo! */
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		FileDataVO fileData = (FileDataVO)this.lstSelectProject.getSelectedValue();

		String tmpProjectName = fileData.getFileName();
		if(tmpProjectName == null) return;

		this.strActiveProject = tmpProjectName;		
		this.txtActiveProject.setText(tmpProjectName);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) { }

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) { }
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) { }

	@Override
	public void textValueChanged(TextEvent e) { }
	
	/* COMPONENT_EVENTS */

	@Override
	public void componentResized(ComponentEvent e) { };

	@Override
	public void componentMoved(ComponentEvent e) { };

	@Override
	public void componentShown(ComponentEvent e) { };

	@Override
	public void componentHidden(ComponentEvent e) { };

	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) { }

	/* Getters/Setters */

	public int getRSCode() {
		return this.rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}

	public String getStrActiveProject() {
		return strActiveProject;
	}

	public void setStrActiveProject(String strActiveProject) {
		this.strActiveProject = strActiveProject;
	}
	
}