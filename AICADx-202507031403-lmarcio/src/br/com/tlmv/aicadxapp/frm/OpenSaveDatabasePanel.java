/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * OpenDatabasePanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.model.SchemaRecordModel;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class OpenSaveDatabasePanel extends JPanel implements ListSelectionListener, ActionListener, ResultListener
{
//Private
	private static OpenSaveDatabaseFrame gParentFrame = null;
	private static OpenSaveDatabasePanel gPanel = null;

	private ResultListener listener = null;

	private ArrayList<SchemaRecord> lsSchemaRecord = new ArrayList<SchemaRecord>();
	private String strActiveDatabase = "";
	
	private JLabel lblSelectDatabase = null;
	private JLabel lblActiveDatabase = null;
	
	private JList lstSelectDatabase = null; 
	private JTextField txtActiveDatabase = null;
	
	private JButton btnNew = null;
	private JButton btnSave = null;
	private JButton btnSaveAs = null;
	private JButton btnOpen = null;
	private JButton btnCopy = null;
	private JButton btnRename = null;
	private JButton btnDrop = null;
	private JButton btnFechar = null;

	private boolean bBtnNew = true; 
	private boolean bBtnSave = true; 
	private boolean bBtnSaveAs = true;
	private boolean bBtnOpen = true;
	private boolean bBtnCopy = true;
	private boolean bBtnRename = true;
	private boolean bBtnDrop = true;

	private boolean bActiveDatabaseEditable = false;
	
	private int rscode = AppDefs.RSCODE_OPENSAVEDATABASE_NONE;
	
	private ArrayList<SchemaRecord> loadAllSchema()
	{
		AppMain app = AppMain.getApp();

		AppDatabase db = app.getDb();
				
		ArrayList<SchemaRecord> lsResult = db.listAll();
		return lsResult;
	}
	
	private void loadDatabase(String strDatabaseFile)
	{
		AppMain app = AppMain.getApp();

		AppCadMain cad = AppCadMain.getCad();
		
		AppDatabase db = app.getDb();
		
		String schemaName = strDatabaseFile;

		String fileName = strDatabaseFile + "." + AppDefs.EXT_AIX;

		if( db.existSchema(schemaName) ) {
			CadDocumentDef doc = cad.getCurrDocumentDef();		
			doc = cad.getCurrDocumentDef();
			doc.setName(strDatabaseFile);
			doc.setFileName(fileName);
			
			db.load(schemaName, doc);
		}
	}
	
	private void saveDatabase(String strDatabaseFile)
	{
		AppMain app = AppMain.getApp();

		AppCadMain cad = AppCadMain.getCad();
		
		AppDatabase db = app.getDb();
		
		CadDocumentDef doc = cad.getCurrDocumentDef();		

		String schemaName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + strDatabaseFile;
		String fileName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + strDatabaseFile + "." + AppDefs.EXT_AIX;

		if( !db.existSchema(schemaName) ) {
			doc = cad.getCurrDocumentDef();
			doc.setName(strDatabaseFile);
			doc.setFileName(fileName);
			//
			db.createSchema(schemaName);			
		}
		db.save(schemaName, doc);
	}
	
	private void createDatabase(String strDatabaseFile)
	{
		AppMain app = AppMain.getApp();

		AppCadMain cad = AppCadMain.getCad();
		
		AppDatabase db = app.getDb();
		
		CadDocumentDef doc = cad.getCurrDocumentDef();

		String schemaName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + strDatabaseFile;
		String fileName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + strDatabaseFile + "." + AppDefs.EXT_AIX;
		
		if( !db.existSchema(schemaName) ) {
			int rscode = cad.newCadDocumentDef();
			if(rscode != AppDefs.RSOK) {
				doc = cad.getCurrDocumentDef();
				doc.setName(strDatabaseFile);
				doc.setFileName(fileName);
				//
				db.createSchema(schemaName);
				db.save(schemaName, doc);
			}
		}
	}
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		this.lsSchemaRecord = this.loadAllSchema();
		SchemaRecordModel model = new SchemaRecordModel(this.lsSchemaRecord); 
		
		Insets insets = this.getInsets();

		int xp = insets.left + 5;
		int yp = insets.top + 5;
		
		//FormFields
		//
		this.lblSelectDatabase = FormControlUtil.newLabel("Select Database:", xp, yp, AppDefs.LABEL_W800, AppDefs.LABEL_H20, true);
		this.add(this.lblSelectDatabase);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		this.lstSelectDatabase = FormControlUtil.newList(this, model, xp, yp, AppDefs.LIST_W800, AppDefs.LIST_H500, true);
		this.lstSelectDatabase.addListSelectionListener(this);
		this.add(this.lstSelectDatabase);
		yp += AppDefs.LIST_H500 + AppDefs.SPACE_H5;
		
		this.lblActiveDatabase = FormControlUtil.newLabel("New/Active Database:", xp, yp, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblActiveDatabase);
		xp += (AppDefs.LABEL_W200 + AppDefs.SPACE_H5);
		
		this.txtActiveDatabase = FormControlUtil.newTextField(this.strActiveDatabase, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, this.bActiveDatabaseEditable);
		this.add(this.txtActiveDatabase);
		xp = insets.left + 5;
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		//Button
		//
		if( bBtnNew ) {
			this.btnNew = FormControlUtil.newButton("Create", AppDefs.RSCODE_OPENSAVEDATABASE_NEW, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnNew);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( bBtnOpen ) {
			this.btnOpen = FormControlUtil.newButton("Open", AppDefs.RSCODE_OPENSAVEDATABASE_OPEN, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnOpen);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( bBtnSave ) {
			this.btnSave = FormControlUtil.newButton("Quick Save", AppDefs.RSCODE_OPENSAVEDATABASE_SAVE, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnSave);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( bBtnSaveAs ) {
			this.btnSaveAs = FormControlUtil.newButton("Save", AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnSaveAs);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		xp = (insets.left + 5) + AppDefs.LIST_W800 - AppDefs.BUTTON_W125;

		this.btnFechar = FormControlUtil.newButton("Fechar", AppDefs.RSCODE_OPENSAVEDATABASE_FECHAR, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnFechar);
		xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		
		if( bBtnDrop ) {
			this.btnDrop = FormControlUtil.newButton("Drop", AppDefs.RSCODE_OPENSAVEDATABASE_DROP, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnDrop);
			xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( bBtnRename ) {
			this.btnRename = FormControlUtil.newButton("Rename...", AppDefs.RSCODE_OPENSAVEDATABASE_RENAME, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnRename);
			xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( bBtnCopy ) {
			this.btnCopy = FormControlUtil.newButton("Copy...", AppDefs.RSCODE_OPENSAVEDATABASE_COPY, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnCopy);
		}
	}
	
//Public 
	
	public OpenSaveDatabasePanel()
	{
		super();
		
		OpenSaveDatabasePanel.gPanel = this;
	}
	
	public void init(
		OpenSaveDatabaseFrame parentFrame, 
		boolean bBtnNew, 
		boolean bBtnSave, 
		boolean bBtnSaveAs, 
		boolean bBtnOpen, 
		boolean bBtnCopy, 
		boolean bBtnRename,
		boolean bBtnDrop,
		String strActiveDatabase,
		boolean bActiveDatabaseEditable,
		ResultListener resultListener)
	{
		OpenSaveDatabasePanel.gParentFrame = parentFrame;
		this.bBtnNew = bBtnNew; 
		this.bBtnSave = bBtnSave; 
		this.bBtnSaveAs = bBtnSaveAs; 
		this.bBtnOpen = bBtnOpen; 
		this.bBtnCopy = bBtnCopy; 
		this.bBtnRename = bBtnRename;
		this.bBtnDrop = bBtnDrop;
		this.strActiveDatabase = strActiveDatabase;
		this.bActiveDatabaseEditable = bActiveDatabaseEditable;
		this.listener = resultListener;
		
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
		
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		String tmpActiveDatabase = this.txtActiveDatabase.getText();
		this.strActiveDatabase = FileUtil.checkDatabaseName( tmpActiveDatabase );

		if( "".equals(this.strActiveDatabase) )
			errmsg += "Database Name";
				
		if(errmsg != "") {
			AppError.showErrorBox(this, "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}

		return true;
	}
	
	/* Actions */
	
	public void doActionNew(ActionEvent e) 
	{
		if( this.validateForm() ) {
			this.createDatabase(this.strActiveDatabase);

			rscode = AppDefs.RSCODE_OPENSAVEDATABASE_NEW;
			gParentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));

			OpenSaveDatabasePanel.gParentFrame.dispose();
		}
	}

	public void doActionOpen(ActionEvent e) 
	{
		this.strActiveDatabase = (String)this.lstSelectDatabase.getSelectedValue();
		if(this.strActiveDatabase == null) return;

		this.loadDatabase(this.strActiveDatabase);

		rscode = AppDefs.RSCODE_OPENSAVEDATABASE_OPEN;			
		gParentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));
			
		OpenSaveDatabasePanel.gParentFrame.dispose();
	}

	public void doActionSave(ActionEvent e) 
	{
		if( this.validateForm() ) {
			this.saveDatabase(this.strActiveDatabase);

			rscode = AppDefs.RSCODE_OPENSAVEDATABASE_SAVE;			
			gParentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));

			OpenSaveDatabasePanel.gParentFrame.dispose();
		}
	}

	public void doActionSaveAs(ActionEvent e) 
	{
		if( this.validateForm() ) {
			this.saveDatabase(this.strActiveDatabase);

			rscode = AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS;			
			gParentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));

			OpenSaveDatabasePanel.gParentFrame.dispose();
		}
	}

	public void doActionDrop(ActionEvent e) 
	{
		String strSchemaName = (String)this.lstSelectDatabase.getSelectedValue();
		if(strSchemaName == null) return;
		
		AppMain app = AppMain.getApp();
		
		AppDatabase db = app.getDb();
		
		db.dropSchema(strSchemaName);
		
		this.lsSchemaRecord = this.loadAllSchema();
		SchemaRecordModel model = new SchemaRecordModel(this.lsSchemaRecord); 

		this.lstSelectDatabase.setModel(model);
	}
		
	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_OPENSAVEDATABASE_FECHAR;
		gParentFrame.actionResultListener(new ResultEvent(rscode, null));

		OpenSaveDatabasePanel.gParentFrame.dispose();
	}
	
	/* Listeners */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmdAction = StringUtil.safeInt(e.getActionCommand());
		
		if(cmdAction == AppDefs.RSCODE_OPENSAVEDATABASE_NEW) {
			doActionNew(e);
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEDATABASE_OPEN) {
			doActionOpen(e);
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEDATABASE_SAVE) {
			doActionSave(e);
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS) {
			doActionSaveAs(e);
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEDATABASE_DROP) {
			doActionDrop(e);			
		}
		else if(cmdAction == AppDefs.RSCODE_OPENSAVEDATABASE_FECHAR) {
			doActionFechar(e);
		}
	}
	
	@Override
	public void actionResultListener(ResultEvent e) {
		String strSchemaName = (String)e.getEventData();
		
		AppMain app = AppMain.getApp();

		AppCadMain cad = AppCadMain.getCad();
		
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		AppDatabase db = app.getDb();		
		db.createSchema(strSchemaName);
		db.save(strSchemaName, doc);
		
		rscode = AppDefs.RSCODE_OPENSAVEDATABASE_NEW;
		gParentFrame.actionResultListener(new ResultEvent(rscode, null));

		OpenSaveDatabasePanel.gParentFrame.dispose();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String strSchemaName = (String)this.lstSelectDatabase.getSelectedValue();
		if(strSchemaName == null) return;
		
		int szPrefix = AppDefs.DEF_SCHEMAPREFIX_DEFAULT.length(); 
		
		String strActiveDatabase = strSchemaName.substring(szPrefix);		
		this.txtActiveDatabase.setText(strActiveDatabase);
	}

	/* Getters/Setters */

	public int getRSCode() {
		return rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}

	public String getActiveDatabase() {
		return strActiveDatabase;
	}

	public void setActiveDatabase(String strActiveDatabase) {
		this.strActiveDatabase = strActiveDatabase;
	}
	
}
