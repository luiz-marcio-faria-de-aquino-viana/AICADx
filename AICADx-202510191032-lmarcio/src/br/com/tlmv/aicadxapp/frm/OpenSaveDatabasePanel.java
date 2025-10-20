/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * OpenDatabasePanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

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
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class OpenSaveDatabasePanel extends BasePanel
{
//Private
	private ArrayList<SchemaRecord> lsSchemaRecord = new ArrayList<SchemaRecord>();

	private String strActiveDatabase = "";

	private boolean bActiveDatabaseEditable = true;
	
	private int rscode = AppDefs.RSCODE_OPENSAVEDATABASE_NONE;

	//FROM_CONTROLS
	//
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
		
		String schemaName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + strDatabaseFile;
		String fileName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + strDatabaseFile + "." + AppDefs.EXT_AIX;
		
		if( !db.existSchema(schemaName) ) {
			CadDocumentDef oNewDoc = cad.newCadDocumentDef();
			if(oNewDoc != null) {
				oNewDoc.setName(strDatabaseFile);
				oNewDoc.setFileName(fileName);
				//
				db.createSchema(schemaName);
				db.save(schemaName, oNewDoc);
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
		String resSelectDatabase = r.getString(R.LBL_SELECTDATABASE);
		this.lblSelectDatabase = FormControlUtil.newLabel(resSelectDatabase, xp, yp, AppDefs.LABEL_W800, AppDefs.LABEL_H20, true);
		this.add(this.lblSelectDatabase);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		this.lstSelectDatabase = FormControlUtil.newList(this, model, xp, yp, AppDefs.LIST_W800, AppDefs.LIST_H500, true);
		this.lstSelectDatabase.addListSelectionListener(this);
		this.add(this.lstSelectDatabase);
		yp += AppDefs.LIST_H500 + AppDefs.SPACE_H5;
		
		String resActiveDatabase = r.getString(R.LBL_ACTIVEDATABASE);
		this.lblActiveDatabase = FormControlUtil.newLabel(resActiveDatabase, xp, yp, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblActiveDatabase);
		xp += (AppDefs.LABEL_W200 + AppDefs.SPACE_H5);
		
		this.txtActiveDatabase = FormControlUtil.newTextField(this.strActiveDatabase, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtActiveDatabase);
		xp = insets.left + 5;
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		//Button
		//
		if( this.bBtnNew ) {
			String resNew = r.getString(R.BTN_NOVO);
			this.btnNew = FormControlUtil.newButton(resNew, AppDefs.RSCODE_OPENSAVEDATABASE_NEW, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnNew);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		//if( this.bBtnOpen ) {
			String resOpen = r.getString(R.BTN_ABRIR);
			this.btnOpen = FormControlUtil.newButton(resOpen, AppDefs.RSCODE_OPENSAVEDATABASE_OPEN, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnOpen);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		//}
		
		if( this.bBtnSave ) {
			String resSave = r.getString(R.BTN_GRAVAR);
			this.btnSave = FormControlUtil.newButton(resSave, AppDefs.RSCODE_OPENSAVEDATABASE_SAVE, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnSave);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		//if( this.bBtnSaveAs ) {
			String resSaveAs = r.getString(R.BTN_GRAVAR_COMO);
			this.btnSaveAs = FormControlUtil.newButton(resSaveAs, AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnSaveAs);
			xp += (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		//}
		
		xp = (insets.left + 5) + AppDefs.LIST_W800 - AppDefs.BUTTON_W125;

		String resFechar = r.getString(R.BTN_FECHAR);
		this.btnFechar = FormControlUtil.newButton(resFechar, AppDefs.RSCODE_OPENSAVEDATABASE_FECHAR, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnFechar);
		xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		
		//if( this.bBtnDrop ) {
			String resDrop = r.getString(R.BTN_APAGAR);
			this.btnDrop = FormControlUtil.newButton(resDrop, AppDefs.RSCODE_OPENSAVEDATABASE_DROP, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnDrop);
			xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		//}
		
		if( this.bBtnRename ) {
			String resRename = r.getString(R.BTN_RENOMEAR);
			this.btnRename = FormControlUtil.newButton(resRename, AppDefs.RSCODE_OPENSAVEDATABASE_RENAME, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnRename);
			xp -= (AppDefs.BUTTON_W125 + AppDefs.SPACE_W5);
		}
		
		if( this.bBtnCopy ) {
			String resCopy = r.getString(R.BTN_COPIAR);
			this.btnCopy = FormControlUtil.newButton(resCopy, AppDefs.RSCODE_OPENSAVEDATABASE_COPY, xp, yp, AppDefs.BUTTON_W125, AppDefs.BUTTON_H20, true, this);
			this.add(this.btnCopy);
		}
	}
	
//Public 
	
	public OpenSaveDatabasePanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(
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
		this.bBtnNew = bBtnNew; 
		this.bBtnSave = bBtnSave; 
		this.bBtnSaveAs = bBtnSaveAs; 
		this.bBtnOpen = bBtnOpen; 
		this.bBtnCopy = bBtnCopy; 
		this.bBtnRename = bBtnRename;
		this.bBtnDrop = bBtnDrop;
		
		this.strActiveDatabase = strActiveDatabase;
		this.bActiveDatabaseEditable = bActiveDatabaseEditable;
		
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

		String tmpActiveDatabase = this.txtActiveDatabase.getText();
		this.strActiveDatabase = FileUtil.checkDatabaseName( tmpActiveDatabase );

		if( "".equals(this.strActiveDatabase) )
			errmsg += r.getString(R.ERR_DATABASE);
				
		if(errmsg != "") {
			String resCamposObrigatorios = r.getString(R.ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS);
			AppError.showErrorBox(this.getParentFrame(), resCamposObrigatorios, errmsg, this.getClass());
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
			this.parentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));

			this.parentFrame.dispose();
		}
	}

	public void doActionOpen(ActionEvent e) 
	{
		this.strActiveDatabase = (String)this.lstSelectDatabase.getSelectedValue();
		if(this.strActiveDatabase == null) return;

		this.loadDatabase(this.strActiveDatabase);

		rscode = AppDefs.RSCODE_OPENSAVEDATABASE_OPEN;			
		this.parentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));
			
		this.parentFrame.dispose();
	}

	public void doActionSave(ActionEvent e) 
	{
		if( this.validateForm() ) {
			this.saveDatabase(this.strActiveDatabase);

			rscode = AppDefs.RSCODE_OPENSAVEDATABASE_SAVE;			
			this.parentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));

			this.parentFrame.dispose();
		}
	}

	public void doActionSaveAs(ActionEvent e) 
	{
		if( this.validateForm() ) {
			this.saveDatabase(this.strActiveDatabase);

			rscode = AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS;			
			this.parentFrame.actionResultListener(new ResultEvent(rscode, this.strActiveDatabase));

			this.parentFrame.dispose();
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
		this.parentFrame.actionResultListener(new ResultEvent(rscode, null));

		this.parentFrame.dispose();
	}
	
	/* ACTION_EVENTS */
	
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
		this.parentFrame.actionResultListener(new ResultEvent(rscode, null));

		this.parentFrame.dispose();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String strSchemaName = (String)this.lstSelectDatabase.getSelectedValue();
		if(strSchemaName == null) return;
		
		int szPrefix = AppDefs.DEF_SCHEMAPREFIX_DEFAULT.length(); 
		
		String strActiveDatabase = strSchemaName.substring(szPrefix);		
		this.txtActiveDatabase.setText(strActiveDatabase);
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
