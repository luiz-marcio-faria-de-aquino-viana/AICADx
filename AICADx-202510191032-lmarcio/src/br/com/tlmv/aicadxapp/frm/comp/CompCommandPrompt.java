/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompCommandPrompt.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.comp;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class CompCommandPrompt extends BasePanel implements Runnable	
{
//Private Static
	private static final long serialVersionUID = -3412810584111194621L;
	
//Private
	private ArrayList<String> lsCommandHistory;
	private int maxSzCommandHistory;
	
	private ArrayList<KeyResultListener> lsListener = null;

	//DETAIL_LEVEL_VARS
	//
	private String detailLevel = AppDefs.DEF_DETAILLEVEL_HIGH;
	
	//TEXTMODE_VARS
	//
	private int textmode = AppDefs.TEXTMODE_NONE;
	private String textVal = "";

	//CURRENT_SELECTION_VARS
	//
	ItemDataVO oCurrDetailLevel = null;
	ItemDataVO oCurrScale = null;
	
	//COMMAND_PROMPT_CONTROL_VARS
	//
	private String strCommandPrompt;

	private DefaultListModel modelCommandHistory;

	//PANEL_COMPONENTS
	//
	private JLabel lblDetailLevel;
	private JComboBox cbxDetailLevel;
	private JLabel lblProjectScale;
	private JComboBox cbxProjectScale;
	private JList lstCommandHistory;
	private JLabel lblCommandPrompt;
	private JTextField txtCommandPrompt;
	
	/* THREADS */
	
	private Thread keyPressedThread = null;
	private boolean bKeyPressedRunning = false;
	
	private boolean bCommandPromptFocus = true;
	
	/* Methodes */
	
    public int selectDetailLevel(String detailLevel)
    {
    	int sz = AppDefs.ARR_DETAIL_LEVEL.length;
    	int pos = -1;
    	for(int i = 0; i < sz; i++) {
    		ItemDataVO oItemData = AppDefs.ARR_DETAIL_LEVEL[i];
    		String strDetailLevel = oItemData.getItemDataId();
    		
    		if( strDetailLevel.equals(detailLevel) ) {
    			pos = i;
    			break;
    		}
    	}

    	if(pos == -1)
    		pos = sz - 1;
    	return pos;
    }	
	
    private int selectProjectScale(double newScale)
    {
    	int sz = AppDefs.ARR_PROJECT_SCALE.length;
    	int pos = -1;
    	for(int i = 0; i < sz; i++) {
    		ItemDataVO oItemData = AppDefs.ARR_PROJECT_SCALE[i];
    		double itemScale = oItemData.getDblVal();
    		
    		if(newScale <= itemScale) {
    			pos = i;
    			break;
    		}
    	}

    	if(pos == -1)
    		pos = sz - 1;
    	return pos;
    }

//Public
	
	public CompCommandPrompt(BaseFrame parentFrame, KeyResultListener listener, int maxSzCommandHistory)
	{
		super(parentFrame);
		
		this.init(listener, maxSzCommandHistory);
	}

	/* Methodes */
	
	public void init(KeyResultListener listener, int maxSzCommandHistory)
	{
		AppCadMain cad = AppCadMain.getCad();		
		CadDocumentDef doc = cad.getCurrDocumentDef();
		CadProjectDef projDef = doc.getCurrProjectDef();

		this.lsCommandHistory = new ArrayList<String>();
		this.maxSzCommandHistory = maxSzCommandHistory;

		this.modelCommandHistory = new DefaultListModel();

		this.lsListener = new ArrayList<KeyResultListener>();
		this.lsListener.add( listener );
		
		Dimension d = new Dimension(1024, 150);
		
		this.setSize(d);
		this.setPreferredSize(this.getSize());
		
		this.initForm();

	    this.updateProjectScale(projDef.getEscala());
	    this.updateDetailLevel(this.detailLevel);
		
		this.startThread();
	}
	
	public void initForm()
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		JPanel topPanel = new JPanel();
		
		FlowLayout layout0 = new java.awt.FlowLayout(FlowLayout.RIGHT);
		topPanel.setLayout(layout0);
		
		String resDetailLevel = r.getString(r.LBL_DETAIL_LEVEL);
		this.lblDetailLevel = FormControlUtil.newLabelEx1(topPanel, resDetailLevel, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.cbxDetailLevel = FormControlUtil.newComboBoxEx1(topPanel, AppDefs.ARR_DETAIL_LEVEL, AppDefs.COMBO_W150, AppDefs.COMBO_H20, true, AppDefs.RSCODE_COMPCOMMANDPROMPT_DETAILLEVEL_CHANGE, this);	
		
		String resScale = r.getString(r.LBL_SCALE);		
		this.lblProjectScale = FormControlUtil.newLabelEx1(topPanel, resScale, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.cbxProjectScale = FormControlUtil.newComboBoxEx1(topPanel, AppDefs.ARR_PROJECT_SCALE, AppDefs.COMBO_W150, AppDefs.COMBO_H20, true, AppDefs.RSCODE_COMPCOMMANDPROMPT_SCALE_CHANGE, this);	
		
		this.add(topPanel, BorderLayout.NORTH);
		
		int szLsCommandHistory = this.lsCommandHistory.size();
		for(String str : this.lsCommandHistory) {
			this.modelCommandHistory.addElement(str);
		}
		
		this.lstCommandHistory = FormControlUtil.newListEx(this, this.modelCommandHistory, true, BorderLayout.CENTER, this);
		
		JPanel localPanel = new JPanel();
		
		BorderLayout layout1 = new BorderLayout();
		localPanel.setLayout(layout1);
		
		String resCommand = r.getString(r.LBL_COMMAND);
		this.lblCommandPrompt = FormControlUtil.newLabelEx(localPanel, resCommand, true, BorderLayout.WEST);
		this.txtCommandPrompt = FormControlUtil.newTextFieldEx(localPanel, this.strCommandPrompt, true, true, BorderLayout.CENTER, this);
		
		this.add(localPanel, BorderLayout.SOUTH);
	}
	
	/* THREADS */
	
	public void startThread()
	{
//		if(this.keyPressedThread == null) {
//			this.bKeyPressedRunning = true;
//			
//			this.keyPressedThread = new Thread(this);
//			this.keyPressedThread.start();
//		}
	}
	
	public void stopThread()
	{
//		if(this.keyPressedThread != null) {
//			this.bKeyPressedRunning = false;
//			this.keyPressedThread = null;
//		}
	}
	
    /* RESET_TEXTMODE_VARS */
    
    public void resetTextModeVars()
    {
    	this.textmode = AppDefs.TEXTMODE_NONE;
    	this.addCommandPromptToCommandHistory(true);
    }
	
    /* DETAIL_LEVEL */
    
//    public void updateDetailLevel(String detailLevel)
//    {
//    	int sz = AppDefs.ARR_DETAIL_LEVEL.length;
//    	int pos = -1;
//    	for(int i = 0; i < sz; i++) {
//    		ItemDataVO oItemData = AppDefs.ARR_DETAIL_LEVEL[i];
//    		String strDetailLevel = oItemData.getItemDataId();
//    		
//    		if( strDetailLevel.equals(detailLevel) ) {
//    			pos = i;
//    			break;
//    		}
//    	}
//
//    	if(pos == -1)
//    		pos = sz - 1;
//    	
//    	//RESET_DETAIL_LEVEL
//    	//
//    	this.cbxDetailLevel.setSelectedIndex(pos);
//
//		ItemDataVO oItemData = AppDefs.ARR_DETAIL_LEVEL[pos];
//		String itemDetailLevel = oItemData.getItemDataId();
//    	
//		ResultEvent oEvent = new ResultEvent(
//			AppDefs.EVENTTYPE_DETAILLEVELCHANGE,
//			itemDetailLevel);
//		this.dispatchEvent(oEvent); 				
//    }
	
    /* PROJECT_SCALE */
    
//    public void updateProjectScale(double newScale)
//    {
//    	int sz = AppDefs.ARR_PROJECT_SCALE.length;
//    	int pos = -1;
//    	for(int i = 0; i < sz; i++) {
//    		ItemDataVO oItemData = AppDefs.ARR_PROJECT_SCALE[i];
//    		double itemScale = oItemData.getDblVal();
//    		
//    		if(newScale <= itemScale) {
//    			pos = i;
//    			break;
//    		}
//    	}
//
//    	if(pos == -1)
//    		pos = sz - 1;
//    	
//    	//RESET_PROJECT_SCALE
//    	//
//    	this.cbxProjectScale.setSelectedIndex(pos);
//
//		ItemDataVO oItemData = AppDefs.ARR_PROJECT_SCALE[pos];
//		double itemScale = oItemData.getDblVal();
//    	
//		ResultEvent oEvent = new ResultEvent(
//			AppDefs.EVENTTYPE_SCLCHANGE,
//			itemScale);
//		this.dispatchEvent(oEvent); 				
//    }
    
    public void updateDetailLevel(String detailLevel)
    {
    	//RESET_DETAIL_LEVEL
    	//
    	int pos = selectDetailLevel(detailLevel);
    	this.cbxDetailLevel.setSelectedIndex(pos);

    	this.oCurrDetailLevel = AppDefs.ARR_DETAIL_LEVEL[pos];
		String itemDetailLevel = this.oCurrDetailLevel.getItemDataId();
    	
		ResultEvent oEvent = new ResultEvent(
			AppDefs.EVENTTYPE_DETAILLEVELCHANGE,
			itemDetailLevel);
		this.dispatchEvent(oEvent); 				
    }
	
    public void updateProjectScale(double newScale)
    {
    	//RESET_PROJECT_SCALE
    	//
    	int pos = selectProjectScale(newScale);
    	this.cbxProjectScale.setSelectedIndex(pos);
    	
    	this.oCurrScale = AppDefs.ARR_PROJECT_SCALE[pos];
		double itemScale = this.oCurrScale.getDblVal();
    	
		ResultEvent oEvent = new ResultEvent(
			AppDefs.EVENTTYPE_SCLCHANGE,
			itemScale);
		this.dispatchEvent(oEvent); 				
    }
    
	/* COMMAND_HISTORY */
	
	public synchronized void selectLastCommandHistory()
	{
		int pos = this.lsCommandHistory.size() - 1;
		this.lstCommandHistory.setSelectedIndex(pos);
		//this.lstCommandHistory.setSelectionInterval(pos, pos);		
	}

	public synchronized void addToCommandHistory(String str)
	{
		this.modelCommandHistory.addElement(str);

		this.lsCommandHistory.add(str);
		
		int sz = this.lsCommandHistory.size();
		if(sz >= this.maxSzCommandHistory) {
			this.modelCommandHistory.remove(0);
			this.lsCommandHistory.remove(0);
		}
	}
	
	public void addCommandPromptToCommandHistory(boolean bClearCommandPrompt)
	{
		String str = this.getCommandPromptValue();
		this.addToCommandHistory(str);
		
		if(bClearCommandPrompt)
			this.clearCommandPromptValue();
	}
	
	/* COMMAND_PROMPT */
	
	public synchronized String getCommandPromptValue()
	{
		this.strCommandPrompt = this.txtCommandPrompt.getText();
		return this.strCommandPrompt;
	}
	
	public synchronized void setCommandPromptValue(String str)
	{
		this.strCommandPrompt = str;
		this.txtCommandPrompt.setText(this.strCommandPrompt);
	}
	
	public synchronized void clearCommandPromptValue()
	{
		this.strCommandPrompt = "";
		this.txtCommandPrompt.setText(this.strCommandPrompt);
	}

	/* WRITE_MESSAGE */
	
	public void writeMessage(String str)
	{
		this.addToCommandHistory(str);
		this.selectLastCommandHistory();
	}

	public void writeAllMessage(ArrayList<String> lsStr)
	{
		for(String str : lsStr) {
			this.addToCommandHistory(str);
		}
		this.selectLastCommandHistory();
	}
	
	/* LISTENER LIST */
	
	public void addListener(KeyResultListener listener ) {
		this.lsListener.add(listener);
	}
	
	public int szLsListener() {
		return this.lsListener.size();
	}
	
	public void dispatchEvent(ResultEvent e) {
		for(KeyResultListener oListener : this.lsListener) {
			oListener.actionKeyResultListener(e);
		}
	}

	/* ACTION_EVENTS */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int action = StringUtil.safeInt(e.getActionCommand());
		if(action == AppDefs.RSCODE_COMPCOMMANDPROMPT_SCALE_CHANGE) {
			ItemDataVO oItemData = (ItemDataVO)this.cbxProjectScale.getSelectedItem();
			
			ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_SCLCHANGE,
				oItemData.getDblVal());
			this.dispatchEvent(oEvent); 				
		}
		else if(action == AppDefs.RSCODE_COMPCOMMANDPROMPT_DETAILLEVEL_CHANGE) {
			ItemDataVO oItemData = (ItemDataVO)this.cbxDetailLevel.getSelectedItem();
			
			ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_DETAILLEVELCHANGE,
				oItemData.getItemDataId());
			this.dispatchEvent(oEvent); 				
		}
		else {
			Object o = e.getSource();
	
			if( this.txtCommandPrompt.equals(o) ) {
				this.textVal = getCommandPromptValue();
				this.addCommandPromptToCommandHistory(true);
				
				this.textmode = AppDefs.TEXTMODE_NONE;
				
				ResultEvent oEvent = new ResultEvent(
					AppDefs.EVENTTYPE_CMDENTER,
					this.textVal);
				this.dispatchEvent(oEvent); 				
			}
		}
	}
	
	@Override
	public void textValueChanged(TextEvent e) 
	{
		if(e.getID() == TextEvent.TEXT_VALUE_CHANGED)
		{
			TextField txt = (TextField)e.getSource();		
			this.strCommandPrompt = txt.getText();
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) 
	{
		JScrollBar panel = (JScrollBar)e.getSource();
		
		int pos = this.lsCommandHistory.size() - 1;
		this.lstCommandHistory.setSelectedIndex(pos);

		panel.setValue(pos * AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_CELLHEIGHT);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) { }

	@Override
	public void itemStateChanged(ItemEvent e) { }

	@Override
	public void actionResultListener(ResultEvent e) { }

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) { }
	
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
	
	/* Threads */

	@Override
	public void run() 
	{
		this.bKeyPressedRunning = true;
		
		while( this.bKeyPressedRunning ) {
			if( bCommandPromptFocus ) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				this.txtCommandPrompt.requestFocus();
			}
			
			try {
				Thread.sleep(AppDefs.SCREENCONTEXT_TIMEOUT);
			}
			catch(Exception e) { }
		}
		this.bKeyPressedRunning = false;
	}
		
	/* Getters/Setters */

	public ArrayList<String> getLsCommandHistory() {
		return lsCommandHistory;
	}

	public void setLsCommandHistory(ArrayList<String> lsCommandHistory) {
		this.lsCommandHistory = lsCommandHistory;
	}

	public String getStrCommandPrompt() {
		return strCommandPrompt;
	}

	public void setStrCommandPrompt(String strCommandPrompt) {
		this.strCommandPrompt = strCommandPrompt;
	}

	public int getTextmode() {
		return textmode;
	}

	public void setTextmode(int textmode) {
		this.textmode = textmode;
	}

	public String getTextVal() {
		return textVal;
	}

	public void setTextVal(String textVal) {
		this.textVal = textVal;
	}

	public boolean isCommandPromptFocus() {
		return bCommandPromptFocus;
	}

	public void setCommandPromptFocus(boolean bCommandPromptFocus) {
		this.bCommandPromptFocus = bCommandPromptFocus;
	}

	public ItemDataVO getoCurrDetailLevel() {
		return oCurrDetailLevel;
	}

	public void setoCurrDetailLevel(ItemDataVO oCurrDetailLevel) {
		this.oCurrDetailLevel = oCurrDetailLevel;
	}

	public ItemDataVO getoCurrScale() {
		return oCurrScale;
	}

	public void setoCurrScale(ItemDataVO oCurrScale) {
		this.oCurrScale = oCurrScale;
	}
	
}
