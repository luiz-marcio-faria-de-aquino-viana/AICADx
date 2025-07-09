/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainPanel.java
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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.ICmdBase;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.comp.CompDocumentProperty;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.model.SchemaRecordModel;
import br.com.tlmv.aicadxapp.frm.view.CompModel3DView;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.samples.MainSample;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.LayerExplorerResultVO;

public class MainPanel extends JPanel implements ActionListener, ItemListener, ListSelectionListener, ResultListener 
{
//Private
	private static MainFrame gParentFrame = null;
	private static MainPanel gPanel = null;
	
	/* PANELS */

	private JPanel panContent = null;

	private JTabbedPane tabMainPanel = null; 

	/* COMPONENTS */
	
	private CompModelPlanView viewPlan = null; 

	private CompModel3DView view3D = null; 

	/* COMPONENTS */
	
	private CompCommandPrompt commandPrompt = null;

	private CompDocumentProperty objectProperty = null;

	/* VARS */

	private Hashtable<String,ICmdBase> mapCommand = null;
	
	private String currAction = AppDefs.ACTION_NONE;
	
	/* Methodes */
	
	private void initContentPanel()
	{
		this.panContent = new JPanel();
	
		BorderLayout layout1 = new BorderLayout();
		this.panContent.setLayout(layout1);
		
		this.add(this.panContent, BorderLayout.CENTER);
	}	

	/* TABBED PANNELS */
	
	private void initTabPanel()
	{
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();

		ViewTable tbl = doc.getViewTable();

		Insets insets = this.getInsets();

		int x = insets.left + 10;
		int y = insets.top + 10;

		int w = 1024;
		int h = 768;
		
		this.tabMainPanel = new JTabbedPane();
		this.tabMainPanel.setBounds(x, y, w, h);
		this.tabMainPanel.setTabPlacement(JTabbedPane.BOTTOM);
		this.panContent.add(this.tabMainPanel, BorderLayout.CENTER);
		
		// *** View: Plan
		this.viewPlan = (CompModelPlanView)tbl.newPlanView("Plan", AppDefs.NULL_INT);
		this.viewPlan.setBounds(x, y, w, h);
		String nPlanView = this.viewPlan.getName();
		char kPlanView = nPlanView.charAt(1);
		
		this.tabMainPanel.addTab(nPlanView, this.viewPlan);
		this.tabMainPanel.setMnemonicAt(0, kPlanView);
		
		// *** View: 3D Model
		this.view3D = (CompModel3DView)tbl.new3DView("3D");
		this.view3D.setBounds(x, y, w, h);	
		String nView3D = this.view3D.getName();
		char kView3D = nView3D.charAt(1);
		
		this.tabMainPanel.addTab(nView3D, this.view3D);
		this.tabMainPanel.setMnemonicAt(1, kView3D);
	}	
	
	private void initObjProperty()
	{
		this.objectProperty = new CompDocumentProperty(this);
		this.panContent.add(this.objectProperty, BorderLayout.WEST);
	}
	
	private void initCmdPrompt()
	{
		this.commandPrompt = new CompCommandPrompt(this.viewPlan, AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_MAXSIZE);
		this.panContent.add(this.commandPrompt, BorderLayout.SOUTH);
	}

	private void initCmdList()
	{
		this.mapCommand = new Hashtable<String,ICmdBase>();		
		int sz = AppDefs.ARR_COMMAND.length;
		for(int i = 0; i < sz; i++) {
			ICmdBase oCmd = AppDefs.ARR_COMMAND[i];			
			String action = oCmd.getCommandName();
			
			this.mapCommand.put(action, oCmd);
		}
	}
	
	private void initSampleData()
	{
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();

		(new MainSample()).initSampleData(AppDefs.DEBUG_LEVEL99, doc);
	}
	
//Public 
	
	public MainPanel()
	{
		super();
		
		MainPanel.gPanel = this;
	}
	
	public void init(MainFrame parent)
	{
		MainPanel.gParentFrame = parent;		
		this.initForm();
	}
	
	/* Methodes */
	
	public void initForm()
	{
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
	
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		this.initCmdList();
		this.initContentPanel();
		this.initTabPanel();
		this.initCmdPrompt();
		this.initObjProperty();
		//
		this.initSampleData();
	}
	
	public void stopAll()
	{
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();

		ViewTable tbl = doc.getViewTable();

		ArrayList<CompView> lsView = tbl.getAllView();
		for(CompView oView : lsView) {
			oView.resetAll();
		}
	}
	
	public void stopAll(CadDocumentDef doc)
	{
		ViewTable tbl = doc.getViewTable();

		ArrayList<CompView> lsView = tbl.getAllView();
		for(CompView oView : lsView) {
			oView.resetAll();
		}
	}
	
	public void refreshAll()
	{
		if(this.viewPlan != null)
			this.viewPlan.repaint();
		
		if(this.view3D != null)
			this.view3D.repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	/* Components */
	
	public CompView getCurrView()
	{
		int pos = this.tabMainPanel.getSelectedIndex();
		if(pos != -1) {
			CompView v = (CompView)this.tabMainPanel.getComponentAt(pos);
			return v;
		}
		return null;
	}
	
	public CompView addNewView(CadDocumentDef doc, CompView oNewView)
	{
		String nPlanView = oNewView.getName();
		char kPlanView = nPlanView.charAt(0);
			
		this.tabMainPanel.addTab(nPlanView, oNewView);
		this.tabMainPanel.setMnemonicAt(0, kPlanView);
		
		this.objectProperty.updateDocumentView(doc);
			
		return oNewView;
	}
	
	/* Actions */
	
	public void doActionCommand(String action)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		if( this.mapCommand.containsKey(action) ) {
			ICmdBase oCmd = this.mapCommand.get(action);
			oCmd.executeCommand(app, frm, cad, doc);
		}
		else {
			String warnmsg = "ERR: Command invalid or not implemented.";
			PromptUtil.prompt(warnmsg);
		}
	}
	
	/* ActionCommand */
	
	public void doFileNewFileXML(ActionEvent e)
	{
		//TODO:
	}

	public void doFileLoadFromRepoXML(ActionEvent e)
	{
		//TODO:
	}

	/* Listeners */

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		if ( !e.getValueIsAdjusting() )
		{
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	        if ( !lsm.isSelectionEmpty() ) 
	        {
	        	//TODO:

	        	lsm.clearSelection();
	        }
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.currAction = e.getActionCommand();
		this.doActionCommand(this.currAction);
	}

	@Override
	public void itemStateChanged(ItemEvent e) { }
	
	@Override
	public void actionResultListener(ResultEvent e) 
	{
		AppCadMain cad = AppCadMain.getCad();

		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		int rscode = e.getEventType();
		
		if(rscode == AppDefs.RSCODE_LAYEREXPLORER_FECHAR)
		{
			ArrayList<LayerExplorerResultVO> ls = (ArrayList<LayerExplorerResultVO>)e.getEventData();
			
			for(LayerExplorerResultVO o : ls) {
				Boolean bActive =  o.getActive();
				String strName = o.getName();
				
				BorderStrokeVO oLtype = o.getLtype();
				//System.out.println(oLtype.getName());
				
				ColorVO oColor = o.getColor();
				Boolean bLayerOn = o.getLayerOnOff();

				LayerTable oLayerTbl = doc.getLayerTable();

				CadLayerDef oLayer = oLayerTbl.getLayerDefByName(strName);
				oLayer.setLtype(oLtype);
				oLayer.setColor(oColor);
				oLayer.setLayerOn(bLayerOn);
				
				if( bActive )
					doc.setCurrLayerDef(oLayer);
			}
			
			this.refreshAll();
		}		
		else if(rscode == AppDefs.RSCODE_COMPDOCUMENTPROPERTY_DOCUMENTO_SELECIONADO)
		{
			String docName = (String)e.getEventData();

			cad.setCurrDocument(docName);

			//CadDocumentDef docSelc = cad.getCurrDocumentDef();

			//boolean achou = false;

			int sz = this.tabMainPanel.getTabCount();
			for(int i = 0; i < sz; i++) {
				String tabName = this.tabMainPanel.getTitleAt(i);
				if(tabName.compareTo(docName) == 0) {
					//achou = true;
					this.tabMainPanel.setSelectedIndex(i);
					break;
				}
			}
		}
		else if(rscode == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_GRAVAR)
		{
			//TODO:
		}
		else if(rscode == AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARPERFIL)
		{
			//TODO:
		}
		else if(rscode == AppDefs.RSCODE_SETUP_OK)
		{
			CadProjectDef o = (CadProjectDef)e.getEventData();
			doc.updateCurrProjectDef(o);
			
			this.refreshAll();
		}
		else if( (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_NEW) ||
				 (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_OPEN) ||			
				 (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_SAVE) ||			
				 (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS) )
		{
			String strActiveDocument = (String)e.getEventData();
			MainPanel.gParentFrame.updateTitle(strActiveDocument);			
		}
			
		this.commandPrompt.setCommandPromptFocus(true);				
	}

	/* Getters/Setters */

	public static MainFrame getParentFrame() {
		return gParentFrame;
	}

	public static MainPanel getPanel() {
		return gPanel;
	}

	public CompCommandPrompt getCommandPrompt() {
		return commandPrompt;
	}

	public CompDocumentProperty getObjectProperty() {
		return objectProperty;
	}

	public String getCurrAction() {
		return currAction;
	}

	public void setCurrAction(String currAction) {
		this.currAction = currAction;
	}
	
}
