/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.popup.PopupArchitectureBlocks;
import br.com.tlmv.aicadxapp.frm.popup.PopupArchitectureCommands;
import br.com.tlmv.aicadxapp.frm.popup.PopupArq1;
import br.com.tlmv.aicadxapp.frm.popup.PopupBlocks;
import br.com.tlmv.aicadxapp.frm.popup.PopupDisplay;
import br.com.tlmv.aicadxapp.frm.popup.PopupDraw1;
import br.com.tlmv.aicadxapp.frm.popup.PopupDraw2;
import br.com.tlmv.aicadxapp.frm.popup.PopupEdit1;
import br.com.tlmv.aicadxapp.frm.popup.PopupEdit2;
import br.com.tlmv.aicadxapp.frm.popup.PopupFile;
import br.com.tlmv.aicadxapp.frm.popup.PopupHelp;
import br.com.tlmv.aicadxapp.frm.popup.PopupInquiry;
import br.com.tlmv.aicadxapp.frm.popup.PopupLayers;
import br.com.tlmv.aicadxapp.frm.popup.PopupMenus;
import br.com.tlmv.aicadxapp.frm.popup.PopupModify;
import br.com.tlmv.aicadxapp.frm.popup.PopupOsnap;
import br.com.tlmv.aicadxapp.frm.popup.PopupSettings;
import br.com.tlmv.aicadxapp.frm.popup.PopupTools;
import br.com.tlmv.aicadxapp.frm.popup.PopupWindow;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarArq1;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarDraw1;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarEdit2;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarFile;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarHelp;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarInquiry;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarSettings;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarZoom;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class MainFrame extends JFrame implements WindowListener
{
//Private
	private MainPanel mainPanel;

	//MAIN_MENU
	//
	JMenuBar mnuMain = new JMenuBar();

	//POPUP_MENU
	//
	//PopupMenu
	private JPopupMenu mnuPopup;

	//ICON_MENU
	//
	//Toolbar Panels
	private JPanel icomnuTop1 = null;	
	private JPanel icomnuLeft1 = null;	
	private JPanel icomnuRight1 = null;	
	//Toolbars
	private JPanel toolbarFile = null;
	private JPanel toolbarArq1 = null;
	private JPanel toolbarDraw1 = null;
	private JPanel toolbarEdit2 = null;
	private JPanel toolbarZoom = null;
	private JPanel toolbarSettings = null;
	private JPanel toolbarLayers = null;
	private JPanel toolbarInquiry = null;
	private JPanel toolbarHelp = null;

	//STATUS_BAR
	//
	//private JPanel statusBar = null;	
	//Controls
	private JLabel lblCoordinate = null;

	/* THREADS */
	
	private Thread keyPressedThread = null;
	
	private boolean bKeyPressedRunning = false;
	
	/* Methodes */

	// Create: Main Menu
	
	private void createMainMenu()
	{
		mnuMain = new JMenuBar();
		
		PopupFile mnuFile = new PopupFile(); 
		mnuFile.createMenu(mnuMain, mainPanel);		
		
		//PopupEdit1 mnuEdit1 = new PopupEdit1(); 
		//mnuEdit1.createMenu(mnuMain, this);		
		
		PopupArq1 mnuArq1 = new PopupArq1(); 
		mnuArq1.createMenu(mnuMain, mainPanel);		
		
		//PopupArchitectureBlocks mnuArchitectureBlocks = new PopupArchitectureBlocks(); 
		//mnuArchitectureBlocks.createMenu(mnuMain, this);		
		
		//PopupArchitectureCommands mnuArchitectureCommands = new PopupArchitectureCommands(); 
		//mnuArchitectureCommands.createMenu(mnuMain, this);		
		
		//PopupBlocks mnuBlocks = new PopupBlocks(); 
		//mnuBlocks.createMenu(mnuMain, this);		
		
		PopupDraw1 mnuDraw1 = new PopupDraw1(); 
		mnuDraw1.createMenu(mnuMain, mainPanel);		
		
		//PopupDraw2 mnuDraw2 = new PopupDraw2(); 
		//mnuDraw2.createMenu(mnuMain, this);		
		
		PopupEdit2 mnuEdit2 = new PopupEdit2(); 
		mnuEdit2.createMenu(mnuMain, mainPanel);		
		
		//PopupModify mnuModify = new PopupModify(); 
		//mnuModify.createMenu(mnuMain, this);		
		
		//PopupTools mnuTools = new PopupTools(); 
		//mnuTools.createMenu(mnuMain, this);		
		
		PopupDisplay mnuDisplay = new PopupDisplay(); 
		mnuDisplay.createMenu(mnuMain, mainPanel);		
		
		PopupSettings mnuSettings = new PopupSettings(); 
		mnuSettings.createMenu(mnuMain, mainPanel);		
		
		PopupLayers mnuLayers = new PopupLayers(); 
		mnuLayers.createMenu(mnuMain, mainPanel);		
		
		PopupInquiry mnuInquiry = new PopupInquiry(); 
		mnuInquiry.createMenu(mnuMain, mainPanel);		
		
		//PopupMenus mnuMenus = new PopupMenus(); 
		//mnuMenus.createMenu(mnuMain, this);		
		
		//PopupWindow mnuWindow = new PopupWindow(); 
		//mnuWindow.createMenu(mnuMain, this);		
		
		PopupHelp mnuHelp = new PopupHelp(); 
		mnuHelp.createMenu(mnuMain, mainPanel);		
		
		this.setJMenuBar(mnuMain) ;		
	}
	
	// Create: Popup Menu
	
	private void createPopupMenu()
	{
		mnuPopup = new JPopupMenu();
		
		PopupOsnap mnuOsnap = new PopupOsnap(); 
		mnuOsnap.createPopupMenu(mnuPopup, mainPanel);		
	}
	
	// Create: Icon Menu
	
	private void createIconMenu()
	{
		this.createTopIconMenu();
		this.createLeftIconMenu();
		this.createRightIconMenu();
	}

	private void createTopIconMenu()
	{
		icomnuTop1 = new JPanel();
		
		FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
		icomnuTop1.setLayout(layout1);
		
		//TOOLBAR_FILE
		//
		toolbarFile = new JPanel();
		
		GridLayout layout = new GridLayout(1, 0, 0, 0);
		toolbarFile.setLayout(layout);
		
		ToolbarFile mnuFile  = new ToolbarFile();
		mnuFile.createToolbarMenu(toolbarFile, mainPanel);	

		icomnuTop1.add(toolbarFile);
		
		//TOOLBAR_SETTINGS
		//
		toolbarSettings = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		toolbarSettings.setLayout(layout);
		
		ToolbarSettings mnuSettings  = new ToolbarSettings();
		mnuSettings.createToolbarMenu(toolbarSettings, mainPanel);	

		icomnuTop1.add(toolbarSettings);
		
		//TOOLBAR_INQUIRY
		//
		toolbarInquiry = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		toolbarInquiry.setLayout(layout);
		
		ToolbarInquiry mnuInquiry  = new ToolbarInquiry();
		mnuInquiry.createToolbarMenu(toolbarInquiry, mainPanel);	

		icomnuTop1.add(toolbarInquiry);
		
		//TOOLBAR_HELP
		//
		toolbarHelp = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		toolbarHelp.setLayout(layout);
		
		ToolbarHelp mnuHelp  = new ToolbarHelp();
		mnuHelp.createToolbarMenu(toolbarHelp, mainPanel);	

		icomnuTop1.add(toolbarHelp);
		
		this.mainPanel.add(icomnuTop1, BorderLayout.NORTH);
	}

	private void createRightIconMenu()
	{
		icomnuRight1 = new JPanel();
		
		FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
		icomnuRight1.setLayout(layout1);
		
		//TOOLBAR_ZOOM
		//
		toolbarZoom = new JPanel();
		
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		toolbarZoom.setLayout(layout);
		
		ToolbarZoom mnuZoom  = new ToolbarZoom();
		mnuZoom.createToolbarMenu(toolbarZoom, mainPanel);	

		icomnuRight1.add(toolbarZoom);
		
		this.mainPanel.add(icomnuRight1, BorderLayout.EAST);
	}

	private void createLeftIconMenu()
	{
		icomnuLeft1 = new JPanel();
		
		FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
		icomnuLeft1.setLayout(layout1);

		JPanel localPanel = new JPanel();
		
		GridLayout layout2 = new GridLayout(0, 2, 0, 0);
		localPanel.setLayout(layout2);
		
		//TOOLBAR_DRAW1
		//
		toolbarDraw1 = new JPanel();
		
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		toolbarDraw1.setLayout(layout);
		
		ToolbarDraw1 mnuDraw1  = new ToolbarDraw1();
		mnuDraw1.createToolbarMenu(toolbarDraw1, mainPanel);	

		localPanel.add(toolbarDraw1);
		
		//TOOLBAR_ARQ1
		//
		toolbarArq1 = new JPanel();
		
		layout = new GridLayout(0, 1, 0, 0);
		toolbarArq1.setLayout(layout);
		
		ToolbarArq1 mnuArq1  = new ToolbarArq1();
		mnuArq1.createToolbarMenu(toolbarArq1, mainPanel);	

		localPanel.add(toolbarArq1);
		
		//TOOLBAR_EDIT2
		//
		toolbarEdit2 = new JPanel();
		
		layout = new GridLayout(0, 1, 0, 0);
		toolbarEdit2.setLayout(layout);
		
		ToolbarEdit2 mnuEdit2  = new ToolbarEdit2();
		mnuEdit2.createToolbarMenu(toolbarEdit2, mainPanel);	

		localPanel.add(toolbarEdit2);
		
		icomnuLeft1.add(localPanel);
		
		this.mainPanel.add(icomnuLeft1, BorderLayout.WEST);
	}

	//private void createStatusBar()
	//{
	//	statusBar = new JPanel();
	//	
	//	FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
	//	statusBar.setLayout(layout1);
	//
	//	lblCoordinate = FormControlUtil.newLabel("Position: 0,000 m x 0,000 m", 0, 0, 150, 20, true);
	//	statusBar.add(lblCoordinate);
	//	
	//	this.mainPanel.add(statusBar, BorderLayout.SOUTH);
	//}
	
	// Create: Main Panel

	private void createMainPanel()
	{
		mainPanel = new MainPanel();
		mainPanel.init(this);
		
		Container c = getContentPane();
		c.add(mainPanel);		
	}
	
//Public

	public MainFrame()
	{
		String appTitle = AppDefs.APP_NAME + " - " + AppDefs.APP_VERSAO;		
		setTitle(appTitle);
		setSize(1024, 768);
		setLocation(25, 25);
		setResizable(true);

		FormControlUtil.loadIcon(this);
		
		addWindowListener(this);
		
		createMainPanel();
		createMainMenu();
		createPopupMenu();
		createIconMenu();
		//createStatusBar();
		
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		this.initCopyright();
	}
	
	/* Methodes */
	
	public void initCopyright()
	{
		MainPanel panel = (MainPanel)this.mainPanel.getPanel();

		CompCommandPrompt cmd = panel.getCommandPrompt();
		
		String strAppNomeVersao = String.format("%s %s", AppDefs.APP_NAME, AppDefs.APP_VERSAO);
		
		cmd.writeMessage(AppDefs.APP_COPYRIGHT);
		cmd.writeMessage(strAppNomeVersao); 
		cmd.writeMessage(AppDefs.APP_AUTHOR_NAME);
		cmd.writeMessage(AppDefs.APP_AUTHOR_REGISTRO);
		cmd.writeMessage(AppDefs.APP_AUTHOR_EMAIL);
		cmd.writeMessage(AppDefs.APP_AUTHOR_TELEFONE);
		cmd.writeMessage("");
	}
	
	public void showPopupMenu(int x, int y)
	{
		this.mnuPopup.show(this.mainPanel, x, y);
	}

	public void showSetupFrame(JFrame parent)
	{
		
	}
	
	/* Events */
	
	@Override
	public void windowClosing(WindowEvent e) 
	{
		System.exit(0);
	}

	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowClosed(WindowEvent e) { }

	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowOpened(WindowEvent e) { }

	/* Getters/Setters */

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JMenuBar getMnuMain() {
		return mnuMain;
	}

	public void setMnuMain(JMenuBar mnuMain) {
		this.mnuMain = mnuMain;
	}

	public JPopupMenu getMnuPopup() {
		return mnuPopup;
	}

	public void setMnuPopup(JPopupMenu mnuPopup) {
		this.mnuPopup = mnuPopup;
	}

	public JPanel getIcomnuTop() {
		return icomnuTop1;
	}

	public void setIcomnuTop(JPanel icomnuTop) {
		this.icomnuTop1 = icomnuTop;
	}

	public JPanel getIcomnuLeft() {
		return icomnuLeft1;
	}

	public void setIcomnuLeft(JPanel icomnuLeft) {
		this.icomnuLeft1 = icomnuLeft;
	}

	public JPanel getIcomnuRight() {
		return icomnuRight1;
	}

	public void setIcomnuRight(JPanel icomnuRight) {
		this.icomnuRight1 = icomnuRight;
	}

	public JPanel getToolbarFile() {
		return toolbarFile;
	}

	public void setToolbarFile(JPanel toolbarFile) {
		this.toolbarFile = toolbarFile;
	}

	public JPanel getToolbarDraw1() {
		return toolbarDraw1;
	}

	public void setToolbarDraw1(JPanel toolbarDraw1) {
		this.toolbarDraw1 = toolbarDraw1;
	}

	public JPanel getToolbarEdit2() {
		return toolbarEdit2;
	}

	public void setToolbarEdit2(JPanel toolbarEdit2) {
		this.toolbarEdit2 = toolbarEdit2;
	}

	public JPanel getToolbarZoom() {
		return toolbarZoom;
	}

	public void setToolbarZoom(JPanel toolbarZoom) {
		this.toolbarZoom = toolbarZoom;
	}

	public JPanel getToolbarInquiry() {
		return toolbarInquiry;
	}

	public void setToolbarInquiry(JPanel toolbarInquiry) {
		this.toolbarInquiry = toolbarInquiry;
	}

	public JPanel getToolbarHelp() {
		return toolbarHelp;
	}

	public void setToolbarHelp(JPanel toolbarHelp) {
		this.toolbarHelp = toolbarHelp;
	}

	public JLabel getLblCoordinate() {
		return lblCoordinate;
	}

	public void setLblCoordinate(JLabel lblCoordinate) {
		this.lblCoordinate = lblCoordinate;
	}
	
}
