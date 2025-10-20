/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainPanel.java
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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.ICmdBase;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.comp.CompDocumentProperty;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.frm.view.CompModel3DView;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.samples.MainSample;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;
import br.com.tlmv.aicadxapp.vo.LayerExplorerResultVO;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdGerarPlanilhaCalculoDrenagem;

public class MainPanel extends BasePanel 
{
//Private
	private static MainPanel gMainPanel = null;
	
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
		
		this.tabMainPanel = FormControlUtil.newTabPanel(x, y, w, h, JTabbedPane.BOTTOM); 
		this.tabMainPanel.addChangeListener(this);
		this.panContent.add(this.tabMainPanel, BorderLayout.CENTER);
		
		/* View
		 */
		String strViewPlan = doc.getName();
		this.viewPlan = (CompModelPlanView)FormControlUtil.newTabPanel(this.tabMainPanel, tbl, r, strViewPlan, AppDefs.NULL_INT, x, y, w, h, AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL); 

		//String str3DView = doc.getName();
		//this.view3D = (CompModel3DView)FormControlUtil.newTabPanel(this.tabMainPanel, tbl, r, str3DView, AppDefs.NULL_INT, x, y, w, h, AppDefs.DOCVIEW_GRP_3DVIEWS_VAL); 
	}	
	
	private void initObjProperty()
	{
		this.objectProperty = new CompDocumentProperty(this.getParentFrame(), this);
		this.panContent.add(this.objectProperty, BorderLayout.WEST);
	}
	
	private void initCmdPrompt()
	{
		this.commandPrompt = new CompCommandPrompt(this.getParentFrame(), this.viewPlan, AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_MAXSIZE);
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
	
	public MainPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(MainFrame parent)
	{
		this.addComponentListener(this);
		
		this.initForm();
		
		MainPanel.gMainPanel = this;
	}
	
	/* Methodes */
	
	public void initForm()
	{
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
		ViewTable tbl = this.doc.getViewTable();

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
	
	/* DOCUMENTS_AND_VIEWS */

	public void selectTabByName(String docName) {
		int sz = this.tabMainPanel.getTabCount();
		for(int i = 0; i < sz; i++) {
			String tabFullName = this.tabMainPanel.getTitleAt(i);
			String tabDocName = StringUtil.getHead(tabFullName, '|');
			if(tabDocName.compareTo(docName) == 0) {
				this.tabMainPanel.setSelectedIndex(i);
				break;
			}
		}
	}	
	
	public int closeTabbedPanel() {
		int result = AppDefs.RSERR;

		int pos = this.tabMainPanel.getSelectedIndex();
		if(pos != -1) {
			CompView v = (CompView)this.tabMainPanel.getComponentAt(pos);
			CadDocumentDef selcDoc = v.getDoc();

			this.closeView(selcDoc);
			this.tabMainPanel.remove(pos);
			result = AppDefs.RSOK;
		}
		return result;
	}
	
	public int closeAllTabbedPanel(String name) {
		int sz = this.tabMainPanel.getTabCount();
		if(sz < 1) return AppDefs.RSERR;
		
		int pos = 0;
		while(pos < sz) {
			CompView v = (CompView)this.tabMainPanel.getComponentAt(pos);
			
			CadDocumentDef selcDoc = v.getDoc();
			String selcName = selcDoc.getName();
			if(name.compareToIgnoreCase(selcName) == 0) {
				this.closeView(selcDoc);
				this.tabMainPanel.remove(pos);
				sz = sz - 1;
			}
			else {
				pos += 1;
			}
		}
		return AppDefs.RSOK;
	}
	
	public void closeView(CadDocumentDef doc) {
		String name = doc.getName();
		
		ViewTable tbl = this.doc.getViewTable();
		tbl.closeView(name);
	}

	/* Events */
	
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
		R r = AppMain.getResource();
		
		int iViewType = oNewView.getViewType();
		GroupItemDataVO oGrpItemData = r.getGroupItemData(iViewType);
		if(oGrpItemData != null) {	
			String nPlanView = doc.getName() + "|" + oGrpItemData.getDescricao();
			char kPlanView = nPlanView.charAt(0);
				
			this.tabMainPanel.addTab(nPlanView, oNewView);
			this.tabMainPanel.setMnemonicAt(0, kPlanView);
			
			this.objectProperty.updateDocumentView(doc);
			
			CompCommandPrompt oCmd = this.getCommandPrompt();
			oCmd.addListener(oNewView);
			return oNewView;
		}			
		return null;
	}
	
	/* Actions */
	
	public void doActionCommand(String action)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = (MainFrame)this.parentFrame;
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

	/* ACTION_EVENTS */

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
		int rscode = e.getEventType();		
		if(rscode == AppDefs.RSCODE_LAYEREXPLORER_FECHAR) {
//			ArrayList<LayerExplorerResultVO> ls = (ArrayList<LayerExplorerResultVO>)e.getEventData();
//			
//			for(LayerExplorerResultVO o : ls) {
//				Boolean bActive =  o.getActive();
//				String strName = o.getName();
//				
//				BorderStrokeVO oLtype = o.getLtype();
//				//System.out.println(oLtype.getName());
//				
//				ColorVO oColor = o.getColor();
//				Boolean bLayerOn = o.getLayerOnOff();
//
//				LayerTable oLayerTbl = doc.getLayerTable();
//
//				CadLayerDef oLayer = oLayerTbl.getLayerDefByName(strName);
//				oLayer.setLtype(oLtype);
//				oLayer.setColor(oColor);
//				oLayer.setLayerOn(bLayerOn);
//				
//				if( bActive )
//					this.doc.setCurrLayerDef(oLayer);
//			}
//			
//			this.refreshAll();
		}		
		else if(rscode == AppDefs.RSCODE_COMPDOCUMENTPROPERTY_DOCUMENTO_SELECIONADO) {
			String docName = (String)e.getEventData();
			this.cad.setCurrDocument(docName);
			
			this.selectTabByName(docName);
			
			CadBlockDef oBlk = this.cad.getCurrBlockDef();
			
			GeomDimension3d oDim = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);
			oDim.getPtCentroid();
		}
		else if(rscode == AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_OK) {
			CmdGerarPlanilhaCalculoDrenagem cmd = new CmdGerarPlanilhaCalculoDrenagem();
			cmd.executeCommand(AppMain.getApp(), (MainFrame)this.getParentFrame(), cad, doc);
		}
		else if(rscode == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_OK) {
			//TODO:
		}
		else if(rscode == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_RECALCULAR) {
			//TODO:
		}
		else if(rscode == AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARPERFIL) {
			//TODO:
		}
		else if(rscode == AppDefs.RSCODE_SETUP_OK) {
			CadProjectDef o = (CadProjectDef)e.getEventData();
			this.doc.updateCurrProjectDef(o);
			
			this.refreshAll();
		}
		else if( (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_NEW) ||
				 (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_OPEN) ||			
				 (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_SAVE) ||			
				 (rscode == AppDefs.RSCODE_OPENSAVEDATABASE_SAVEAS) ) {
			String strActiveDocument = (String)e.getEventData();
			this.parentFrame.updateTitle(strActiveDocument);			
		}
		else if( (rscode == AppDefs.RSCODE_SEARCH_ZOOMTOITEM) ||
				 (rscode == AppDefs.RSCODE_SEARCH_ZOOMTOALL) ) {
			if(this.viewPlan != null) {
				GeomDimension2d oGeomDim = (GeomDimension2d)e.getEventData();
				if(oGeomDim != null) {
					GeomPoint2d ptMin2d = new GeomPoint2d( oGeomDim.getPtMin() );
					GeomPoint2d ptMax2d = new GeomPoint2d( oGeomDim.getPtMax() );
					
					ICadViewBase v = this.viewPlan.getCadViewBase();
					v.zoomWindowMcs(ptMin2d, ptMax2d);

					this.refreshAll();
				}
			}
		}
		
		this.commandPrompt.setCommandPromptFocus(true);				
	}

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) { }

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) { }

	@Override
	public void textValueChanged(TextEvent e) { }

	/* COMPONENT_EVENTS */
	
	@Override
	public void componentResized(ComponentEvent e) { }

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }
	
	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) {
		int pos = this.tabMainPanel.getSelectedIndex();
		if(pos != -1) {
			String tabName = this.tabMainPanel.getTitleAt(pos);
			
			String docName = StringUtil.getHead(tabName, '|');			
			this.cad.setCurrDocument(docName);
		}
	}
		
	/* Getters/Setters */

	public static MainPanel getMainPanel() {
		return gMainPanel;
	}

	public static void setMainPanel(MainPanel gMainPanel) {
		MainPanel.gMainPanel = gMainPanel;
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
