/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.frm.comp;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cmp.CmpCadLevel;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.model.LevelComboModel;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.LevelVO;

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
	private ArrayList<CadLevel> lsLevel = null;
	private ArrayList<String> lsCommandHistory = null;
	private int maxSzCommandHistory = 0;

	//RESULT_LISTENER
	//
	private ArrayList<ResultListener> lsResListener = null;

	private ArrayList<KeyResultListener> lsKeyListener = null;

	//DETAIL_LEVEL_VARS
	//
	private String detailLevel = AppDefs.DEF_DETAILLEVEL_HIGH;
	
	//LEVEL_VARS
	//
	private String levelName = AppDefs.DEFAULT_LEVELNAME;
	
	//TEXTMODE_VARS
	//
	private int textmode = AppDefs.TEXTMODE_NONE;
	private String textVal = "";

	//CURRENT_SELECTION_VARS
	//
	private ItemDataVO oCurrDetailLevel = null;
	private ItemDataVO oCurrScale = null;
	//
	private LevelVO oCurrLevel = null;
	
	//COMMAND_PROMPT_CONTROL_VARS
	//
	private String strCommandPrompt;

	private DefaultListModel modelCommandHistory;

	//PANEL_COMPONENTS
	//	
	private JLabel lblLevel;
	private JComboBox cbxLevel;
	//
	private JLabel lblDetailLevel;
	private JComboBox cbxDetailLevel;
	//
	private JLabel lblProjectScale;
	private JComboBox cbxProjectScale;
	//
	private JList lstCommandHistory;
	private JLabel lblCommandPrompt;
	private JTextField txtCommandPrompt;
	
	/* THREADS */
	
	private Thread keyPressedThread = null;
	private boolean bKeyPressedRunning = false;
	
	private boolean bCommandPromptFocus = true;
	
	/* Methodes */
	
	private ArrayList<CadLevel> loadAllLevels()
	{
		MainFrame mainFrame = (MainFrame)this.getParentFrame();
		MainPanel panel = (MainPanel)mainFrame.getPanel();
		
		CadDocumentDef doc = panel.getCurrDocumentDef();
		
		LevelTable levelTable = doc.getLevelTable();

		ArrayList<CadLevel> lsResult = new ArrayList<CadLevel>();
		lsResult = levelTable.getAllLevel();

		CmpCadLevel c = new CmpCadLevel(true);
		lsResult.sort(c);
		
		return lsResult;
	}

    public void resetAllLevels()
    {
    	if(this.cbxLevel == null) return;
    	
    	this.lsLevel = this.loadAllLevels();
    	
    	LevelComboModel model = new LevelComboModel(this.lsLevel);
		this.cbxLevel.setModel(model);
    }		
	
    private int selectDetailLevel(String detailLevel)
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
	
    private int selectLevel(String levelName)
    {
    	int sz = this.lsLevel.size();
    	int pos = -1;
    	for(int i = 0; i < sz; i++) {
    		CadLevel oLevel = this.lsLevel.get(i);
    		String strLevelName = oLevel.getLevelLocalName();
    		
    		String warnmsg = "Comp: " + levelName + " = " + strLevelName;
    		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL31, warnmsg, this.getClass());
    		
    		if( strLevelName.equals(levelName) ) {
    			return i;
    		}
    	}

    	if(pos == -1)
    		pos = 0;
    	return pos;
    }	

//Public
	
	public CompCommandPrompt(BaseFrame parentFrame, ResultListener resListener, KeyResultListener keyListener, int maxSzCommandHistory)
	{
		super(parentFrame);
		
		this.init(resListener, keyListener, maxSzCommandHistory);
	}

	/* Methodes */
	
	public void init(ResultListener resListener, KeyResultListener keyListener, int maxSzCommandHistory)
	{
		MainFrame mainFrame = (MainFrame)this.getParentFrame();
		MainPanel panel = (MainPanel)mainFrame.getPanel();

		CadDocumentDef doc = panel.getCurrDocumentDef();
		
		CadProjectDef projDef = doc.getCurrProjectDef();

		this.lsCommandHistory = new ArrayList<String>();
		this.maxSzCommandHistory = maxSzCommandHistory;

		this.modelCommandHistory = new DefaultListModel();

		//RESULT_LISTENER
		//
		this.lsResListener = new ArrayList<ResultListener>();
		this.lsResListener.add( resListener );

		this.lsKeyListener = new ArrayList<KeyResultListener>();
		this.lsKeyListener.add( keyListener );
		
		Dimension d = new Dimension(1024, 150);
		
		this.setSize(d);
		this.setPreferredSize(this.getSize());
		
		this.lsLevel = this.loadAllLevels();
		
		this.initForm();

	    this.updateProjectScale(projDef.getEscala());
	    this.updateDetailLevel(this.detailLevel);
	    this.updateLevel(this.levelName);
		
		this.startThread();
	}
	
	public void initForm()
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		JPanel topPanel = new JPanel();
		
		FlowLayout layout0 = new java.awt.FlowLayout(FlowLayout.RIGHT);
		topPanel.setLayout(layout0);

		LevelComboModel model = new LevelComboModel( this.lsLevel );
		
		String resLevel = r.getString(r.LBL_LEVEL);
		this.lblLevel = FormControlUtil.newLabelEx1(topPanel, resLevel, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.cbxLevel = FormControlUtil.newComboBoxEx1(topPanel, model, AppDefs.COMBO_W150, AppDefs.COMBO_H20, true, AppDefs.RSCODE_COMPCOMMANDPROMPT_LEVEL_CHANGE, this);	
		this.selectLevel(AppDefs.DEFAULT_LEVELNAME);
		
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
		//TODO:
	}
	
	public void stopThread()
	{
		//TODO:
	}
	
    /* RESET_TEXTMODE_VARS */
    
    public void resetTextModeVars()
    {
    	this.textmode = AppDefs.TEXTMODE_NONE;
    	this.addCommandPromptToCommandHistory(true);
    }
	
    /* UPDATE_DETAIL_LEVEL */
    
    public void updateDetailLevel(String detailLevel)
    {
    	String warnmsg = "DetailLevel: " + detailLevel;
    	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());
    	
    	//RESET_DETAIL_LEVEL
    	//
    	int pos = selectDetailLevel(detailLevel);
    	if(pos != -1) {
	    	warnmsg = "DetailLevelPos: " + Integer.toString( pos );
	    	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());

	    	this.cbxDetailLevel.setSelectedIndex(pos);
	
	    	this.oCurrDetailLevel = AppDefs.ARR_DETAIL_LEVEL[pos];
			String itemDetailLevel = this.oCurrDetailLevel.getItemDataId();
	    	
			ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_DETAILLEVELCHANGE,
				itemDetailLevel);
			this.dispatchResEvent(oEvent); 				
    	}
    }
    
    /* UPDATE_LEVEL */
    
    public void updateLevel()
    {
    	String warnmsg = "LevelName: " + this.levelName;
    	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());

    	if(this.cbxLevel == null) return;

    	//RESET_LEVEL
    	//
    	this.resetAllLevels();

    	int sz = this.lsLevel.size();
    	if(sz == 0) return;
    	
    	int pos = 0;

    	warnmsg = "LevelPos: " + Integer.toString( pos );
        AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());
        	
    	this.cbxLevel.setSelectedIndex(pos);

    	if(AppDefs.DEBUG_LEVEL == AppDefs.DEBUG_LEVEL30) {
    		CadLevel oLevel = (CadLevel)this.cbxLevel.getSelectedItem();
    		System.out.println( "Level: " + oLevel.getLabel() );
    	}
        	
		ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_LEVELCHANGE,
				this.levelName);
		this.dispatchResEvent(oEvent);
    }

    public void updateLevel(String levelName)
    {
    	String warnmsg = "LevelName: " + levelName;
    	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());

    	if(this.cbxLevel == null) return;

    	//RESET_LEVEL
    	//
    	this.resetAllLevels();
    	
    	int pos = selectLevel(levelName);
    	if(pos != -1) {
        	warnmsg = "LevelPos: " + Integer.toString( pos );
        	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());
        	
        	this.cbxLevel.setSelectedIndex(pos);

        	if(AppDefs.DEBUG_LEVEL == AppDefs.DEBUG_LEVEL30) {
        		CadLevel oLevel = (CadLevel)this.cbxLevel.getSelectedItem();
        		System.out.println( "Level: " + oLevel.getLabel() );
        	}
        	
    		ResultEvent oEvent = new ResultEvent(
    				AppDefs.EVENTTYPE_LEVELCHANGE,
    				levelName);
    		this.dispatchResEvent(oEvent);
    	}
    }

    /* UPDATE_PROJECT_SCALE */
    
    public void updateProjectScale(double newScale)
    {
    	String warnmsg = "NewScale: " + newScale;
    	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());
    	
    	//RESET_PROJECT_SCALE
    	//
    	int pos = selectProjectScale(newScale);
    	if(pos != -1) {
        	warnmsg = "NewScalePos: " + Integer.toString( pos );
        	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());
        	
        	this.cbxProjectScale.setSelectedIndex(pos);
	    	
	    	this.oCurrScale = AppDefs.ARR_PROJECT_SCALE[pos];
			double itemScale = this.oCurrScale.getDblVal();
	    	
        	warnmsg = "NewScaleVal: " + Double.toString( itemScale );
        	AppError.showCmdWarn(AppDefs.DEBUG_LEVEL30, warnmsg, this.getClass());
        	
			ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_SCLCHANGE,
				itemScale);
			this.dispatchResEvent(oEvent); 				
    	}
    }
    
	/* COMMAND_HISTORY */
	
	public synchronized void selectLastCommandHistory()
	{
		int pos = this.lsCommandHistory.size() - 1;
    	if(pos != -1) {
        	this.lstCommandHistory.setSelectedIndex(pos);
        	//this.lstCommandHistory.setSelectionInterval(pos, pos);
    	}
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
	
	/* RES_LISTENER_LIST */
	
	public void dispatchResEvent(ResultEvent e) {
		for(ResultListener oListener : this.lsResListener) {
			oListener.actionResultListener(e);
		}
	}
	
	public void addResListener(ResultListener resListener ) {
		this.lsResListener.add(resListener);
	}
	
	public int szLsResListener() {
		return this.lsResListener.size();
	}
	
	/* KEY_LISTENER_LIST 
	 */
	public void dispatchKeyEvent(ResultEvent e) {
		for(KeyResultListener oListener : this.lsKeyListener) {
			oListener.actionKeyResultListener(e);
		}
	}
	
	public void addKeyListener(KeyResultListener keyListener ) {
		this.lsKeyListener.add(keyListener);
	}
	
	public int szLsKeyListener() {
		return this.lsKeyListener.size();
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
			this.dispatchResEvent(oEvent); 				
		}
		else if(action == AppDefs.RSCODE_COMPCOMMANDPROMPT_DETAILLEVEL_CHANGE) {
			ItemDataVO oItemData = (ItemDataVO)this.cbxDetailLevel.getSelectedItem();
			
			ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_DETAILLEVELCHANGE,
				oItemData.getItemDataId());
			this.dispatchResEvent(oEvent); 				
		}
		else if(action == AppDefs.RSCODE_COMPCOMMANDPROMPT_LEVEL_CHANGE) {
			CadLevel oLevel = (CadLevel)this.cbxLevel.getSelectedItem();
			oLevel.debug(AppDefs.DEBUG_LEVEL29);
			
			ResultEvent oEvent = new ResultEvent(
				AppDefs.EVENTTYPE_LEVELCHANGE,
				oLevel.getLevelLocalName());
			this.dispatchResEvent(oEvent); 				
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
				this.dispatchKeyEvent(oEvent); 				
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

	public ItemDataVO getCurrDetailLevel() {
		return oCurrDetailLevel;
	}

	public void setCurrDetailLevel(ItemDataVO oCurrDetailLevel) {
		this.oCurrDetailLevel = oCurrDetailLevel;
	}

	public ItemDataVO getCurrScale() {
		return oCurrScale;
	}

	public void setCurrScale(ItemDataVO oCurrScale) {
		this.oCurrScale = oCurrScale;
	}

	public LevelVO getCurrLevel() {
		return oCurrLevel;
	}

	public void setCurrLevel(LevelVO oCurrLevel) {
		this.oCurrLevel = oCurrLevel;
	}
	
}
