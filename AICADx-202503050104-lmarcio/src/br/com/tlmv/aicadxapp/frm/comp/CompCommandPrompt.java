/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompCommandPrompt.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.comp;

import javax.swing.JTextField;
import javax.swing.ListModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class CompCommandPrompt extends JPanel implements Runnable, TextListener, AdjustmentListener, ActionListener
{
//Private Static
	private static final long serialVersionUID = -3412810584111194621L;
	
//Private
	private ArrayList<String> lsCommandHistory;
	private int maxSzCommandHistory;
	
	private KeyResultListener listener = null;
	
	//TEXTMODE_VARS
	//
	private int textmode = AppDefs.TEXTMODE_NONE;
	//
	private String textVal = "";
	
	//COMMAND_PROMPT_CONTROL_VARS
	//
	private String strCommandPrompt;

	private DefaultListModel modelCommandHistory;

	//PANEL_COMPONENTS
	//
	private JList lstCommandHistory;
	private JLabel lblCommandPrompt;
	private JTextField txtCommandPrompt;
	
	/* THREADS */
	
	private Thread keyPressedThread = null;
	private boolean bKeyPressedRunning = false;
	
//Public
	
	public CompCommandPrompt(KeyResultListener listener, int maxSzCommandHistory)
	{
		this.init(listener, maxSzCommandHistory);
	}

	/* Methodes */
	
	public void init(KeyResultListener listener, int maxSzCommandHistory)
	{
		this.lsCommandHistory = new ArrayList<String>();
		this.maxSzCommandHistory = maxSzCommandHistory;

		this.modelCommandHistory = new DefaultListModel();
		
		this.listener = listener;
		
		Dimension d = new Dimension(1024, 150);
		
		this.setSize(d);
		this.setPreferredSize(this.getSize());
		
		this.initForm();
		
		this.startThread();
	}
	
	public void initForm()
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		int szLsCommandHistory = this.lsCommandHistory.size();
		for(String str : this.lsCommandHistory) {
			this.modelCommandHistory.addElement(str);
		}
		
		this.lstCommandHistory = FormControlUtil.newListEx(this, this.modelCommandHistory, true, BorderLayout.CENTER, this);
		
		JPanel localPanel = new JPanel();
		
		BorderLayout layout1 = new BorderLayout();
		localPanel.setLayout(layout1);
		
		this.lblCommandPrompt = FormControlUtil.newLabelEx(localPanel, "Command: ", isVisible(), BorderLayout.WEST);
		this.txtCommandPrompt = FormControlUtil.newTextFieldEx(localPanel, this.strCommandPrompt, isVisible(), getFocusTraversalKeysEnabled(), BorderLayout.CENTER, this);
		
		this.add(localPanel, BorderLayout.SOUTH);
	}
	
	/* THREADS */
	
	public void startThread()
	{
		if(this.keyPressedThread == null) {
			this.bKeyPressedRunning = true;
			
			this.keyPressedThread = new Thread(this);
			this.keyPressedThread.start();
		}
	}
	
	public void stopThread()
	{
		if(this.keyPressedThread != null) {
			this.bKeyPressedRunning = false;
			this.keyPressedThread = null;
		}
	}
	
    /* RESET_TEXTMODE_VARS */
    
    public void resetTextModeVars()
    {
    	this.textmode = AppDefs.TEXTMODE_NONE;
    	this.addCommandPromptToCommandHistory(true);
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
	
	/* EVENTS */

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if( this.txtCommandPrompt.equals(o) ) {
			this.textVal = getCommandPromptValue();
			this.addCommandPromptToCommandHistory(true);
			
			this.textmode = AppDefs.TEXTMODE_NONE;
			
			if(this.listener != null) {
				ResultEvent oEvent = new ResultEvent(
					AppDefs.EVENTTYPE_CMDENTER,
					this.textVal);
				this.listener.actionKeyResultListener(oEvent); 				
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
	
	/* Threads */
	
	@Override
	public void run() 
	{
		this.bKeyPressedRunning = true;
		
		while( this.bKeyPressedRunning ) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
			this.txtCommandPrompt.requestFocus();
			
			try {
				Thread.sleep(50);
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
	
}
