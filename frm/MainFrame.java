/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainFrame.java
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
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
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
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarBlocks;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarControl;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarDraw1;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarEdit2;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarFile;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarHelp;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarInquiry;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarSettings;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarZoom;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxmod.IModule;

public class MainFrame extends JFrame implements WindowListener
{
//Private
	private MainPanel mainPanel;

	//MAIN_MENU
	//
	private JMenuBar mnuMain = new JMenuBar();

	//POPUP_MENU
	//
	//PopupMenu
	private JPopupMenu mnuPopup;
	//
	private PopupFile mnuFile = null; 
	private PopupEdit1 mnuEdit1 = null; 
	private PopupBlocks mnuBlocks = null; 
	private PopupDraw1 mnuDraw1 = null; 
	private PopupDraw2 mnuDraw2 = null; 
	private PopupEdit2 mnuEdit2 = null; 
	private PopupModify mnuModify = null; 
	private PopupTools mnuTools = null; 
	private PopupDisplay mnuDisplay = null; 
	private PopupSettings mnuSettings = null; 
	private PopupLayers mnuLayers = null; 
	private PopupInquiry mnuInquiry = null; 
	private PopupMenus mnuMenus = null; 
	private PopupWindow mnuWindow = null; 
	private PopupHelp mnuHelp = null; 
	
	//ICON_MENU
	//
	//Toolbar - TOPO
	private JPanel icomnuTop1 = null;	
	private JPanel icomnuLeft1 = null;	
	private JPanel icomnuRight1 = null;	
	//Toolbars - LATERAIS
	private JPanel toolbarFile = null;
	private JPanel toolbarBlocks = null;
	private JPanel toolbarDraw1 = null;
	private JPanel toolbarEdit2 = null;
	private JPanel toolbarZoom = null;
	private JPanel toolbarSettings = null;
	private JPanel toolbarLayers = null;
	private JPanel toolbarInquiry = null;
	private JPanel toolbarHelp = null;

	//Toolbars - CONTROLES TEMPORARIOS
	private ArrayList<JPanel> lsToolbarCtrl = null;
	private JPanel toolbarCtrl_Basic = null;
	
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
	
	private void createModuleMenu(JMenuBar mnubar, ActionListener listener)
	{
		AppMain app = AppMain.getApp();

		ArrayList<IModule> lsModule = app.getLsModule();
		for(IModule o : lsModule) {
			o.createMenu(mnubar, listener);
		}
	}
	
	private void createMainMenu()
	{
		mnuMain = new JMenuBar();
		
		mnuFile = new PopupFile(); 
		mnuFile.createMenu(mnuMain, mainPanel);		
		
		//mnuEdit1 = new PopupEdit1(); 
		//mnuEdit1.createMenu(mnuMain, this);		
		
		mnuBlocks = new PopupBlocks(); 
		mnuBlocks.createMenu(mnuMain, mainPanel);		
		
		mnuDraw1 = new PopupDraw1(); 
		mnuDraw1.createMenu(mnuMain, mainPanel);		
		
		//mnuDraw2 = new PopupDraw2(); 
		//mnuDraw2.createMenu(mnuMain, this);		
		
		mnuEdit2 = new PopupEdit2(); 
		mnuEdit2.createMenu(mnuMain, mainPanel);		
		
		//mnuModify = new PopupModify(); 
		//mnuModify.createMenu(mnuMain, this);		
		
		//mnuTools = new PopupTools(); 
		//mnuTools.createMenu(mnuMain, this);		
		
		mnuDisplay = new PopupDisplay(); 
		mnuDisplay.createMenu(mnuMain, mainPanel);		
		
		mnuSettings = new PopupSettings(); 
		mnuSettings.createMenu(mnuMain, mainPanel);		
		
		mnuLayers = new PopupLayers(); 
		mnuLayers.createMenu(mnuMain, mainPanel);		
		
		mnuInquiry = new PopupInquiry(); 
		mnuInquiry.createMenu(mnuMain, mainPanel);		

		createModuleMenu(mnuMain, mainPanel);
		
		mnuMenus = new PopupMenus(); 
		mnuMenus.createMenu(mnuMain, mainPanel);		
		
		//PopupWindow mnuWindow = new PopupWindow(); 
		//mnuWindow.createMenu(mnuMain, this);		
		
		mnuHelp = new PopupHelp(); 
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
	
	private void createModuleIconMenu(JPanel iconmnu, ActionListener listener)
	{
		AppMain app = AppMain.getApp();

		ArrayList<IModule> lsModule = app.getLsModule();
		for(IModule o : lsModule) {
			o.createToolbarMenu(iconmnu, listener);
		}
	}
	
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
		
		//TOOLBAR_CONTROL
		//
		toolbarCtrl_Basic = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		toolbarCtrl_Basic.setLayout(layout);
		
		ToolbarControl mnuCtrl  = new ToolbarControl();
		mnuCtrl.createToolbarMenu(toolbarCtrl_Basic, mainPanel);
		toolbarCtrl_Basic.setVisible(false);

		icomnuTop1.add(toolbarCtrl_Basic);
		
		this.lsToolbarCtrl.add(toolbarCtrl_Basic);
		
		//TOOLBAR_MODULE
		//
		this.createModuleIconMenu(icomnuTop1, mainPanel);
		
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
		
		//TOOLBAR_BLOCKS
		//
		toolbarBlocks = new JPanel();
		
		layout = new GridLayout(0, 1, 0, 0);
		toolbarBlocks.setLayout(layout);
		
		ToolbarBlocks mnuBlocks  = new ToolbarBlocks();
		mnuBlocks.createToolbarMenu(toolbarBlocks, mainPanel);	

		localPanel.add(toolbarBlocks);
		
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
		setSize(AppDefs.MAIN_FRAME_WIDTH, AppDefs.MAIN_FRAME_HEIGHT);
		setLocation(AppDefs.DEFAULT_XPOS, AppDefs.DEFAULT_YPOS);
		setResizable(true);

		updateTitle(null);
		
		FormControlUtil.loadIcon(this);
		
		addWindowListener(this);
	
		this.lsToolbarCtrl = new ArrayList<JPanel>();
		
		createMainPanel();
		createMainMenu();
		createPopupMenu();
		createIconMenu();
		//createStatusBar();
		
		this.activateMenu(AppDefs.ACTION_MENUS_ARQMENU);
		
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		this.initCopyright();
	}
	
	/* Methodes */
	
	public void updateTitle(String str)
	{
		String appTitle = AppDefs.APP_NAME + " - " + AppDefs.APP_VERSAO;
		if(str != null)
			appTitle += (" - " + str);		
		setTitle(appTitle);		
	}
	
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
	
	/* MENUS */
	
	public void activateMenu(String actionCommand)
	{
		AppMain app = AppMain.getApp();
		
		mnuFile.setVisible(true); 
		//mnuEdit1.setVisible(true); 
		//mnuBlocks.setVisible(true); 
		mnuDraw1.setVisible(true); 
		//mnuDraw2.setVisible(true); 
		mnuEdit2.setVisible(true); 
		//mnuModify.setVisible(true); 
		//mnuTools.setVisible(true); 
		mnuDisplay.setVisible(true); 
		mnuSettings.setVisible(true); 
		mnuLayers.setVisible(true); 
		mnuInquiry.setVisible(true); 
		mnuMenus.setVisible(true); 
		//mnuWindow.setEnabled(true); 
		mnuHelp.setVisible(true); 

		//MODULES
		//
		IModule oArqMod = app.getArqModule();
		oArqMod.setVisible(false);
		
		IModule oApMod = app.getApModule();
		oApMod.setVisible(false);
		
		IModule oElMod = app.getElModule();
		oElMod.setVisible(false);
		
		IModule oEsMod = app.getEsModule();
		oEsMod.setVisible(false);
		
		IModule oRdpMod = app.getRpdModule();
		oRdpMod.setVisible(false);
		
		IModule oHMod = app.getHidModule();
		oHMod.setVisible(false);
		
		IModule oGMod = app.getGasModule();
		oGMod.setVisible(false);
				
		if( AppDefs.ACTION_MENUS_ARQMENU.equals(actionCommand) ) {
			oArqMod.setVisible(true); 
		}
		else if( AppDefs.ACTION_MENUS_FORMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_FUMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_EEMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_ELMENU.equals(actionCommand) ) {
			oElMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_ESMENU.equals(actionCommand) ) {
			oEsMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_APMENU.equals(actionCommand) ) {
			oApMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_RPDMENU.equals(actionCommand) ) {
			oRdpMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_HMENU.equals(actionCommand) ) {
			oHMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_INCMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_GMENU.equals(actionCommand) ) {
			oGMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_IEMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_TEMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_ARMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
	}
	
	public void showToolbarControl(int toolbarId, boolean bVisible)
	{
		if(toolbarId == AppDefs.TOOLBARCTRL_BASIC) {
			this.toolbarCtrl_Basic.setVisible( bVisible );
		}
		else if(toolbarId == AppDefs.TOOLBARCTRL_ALL) {
			for(JPanel o : this.lsToolbarCtrl) {
				o.setVisible( bVisible );
			}
		}
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

	public JMenuBar getMnuMain() {
		return mnuMain;
	}

	public JPopupMenu getMnuPopup() {
		return mnuPopup;
	}

	public JPanel getIcomnuTop() {
		return icomnuTop1;
	}

	public JPanel getIcomnuLeft() {
		return icomnuLeft1;
	}

	public JPanel getIcomnuRight() {
		return icomnuRight1;
	}

	public JPanel getToolbarFile() {
		return toolbarFile;
	}

	public JPanel getToolbarDraw1() {
		return toolbarDraw1;
	}

	public JPanel getToolbarEdit2() {
		return toolbarEdit2;
	}

	public JPanel getToolbarZoom() {
		return toolbarZoom;
	}

	public JPanel getToolbarInquiry() {
		return toolbarInquiry;
	}

	public JPanel getToolbarHelp() {
		return toolbarHelp;
	}

	public JLabel getLblCoordinate() {
		return lblCoordinate;
	}

	public void setLblCoordinate(JLabel lblCoordinate) {
		this.lblCoordinate = lblCoordinate;
	}

	public PopupFile getMnuFile() {
		return mnuFile;
	}

	public PopupEdit1 getMnuEdit1() {
		return mnuEdit1;
	}

	public PopupBlocks getMnuBlocks() {
		return mnuBlocks;
	}

	public PopupDraw1 getMnuDraw1() {
		return mnuDraw1;
	}

	public PopupDraw2 getMnuDraw2() {
		return mnuDraw2;
	}

	public PopupEdit2 getMnuEdit2() {
		return mnuEdit2;
	}

	public PopupModify getMnuModify() {
		return mnuModify;
	}

	public PopupTools getMnuTools() {
		return mnuTools;
	}

	public PopupDisplay getMnuDisplay() {
		return mnuDisplay;
	}

	public PopupSettings getMnuSettings() {
		return mnuSettings;
	}

	public PopupLayers getMnuLayers() {
		return mnuLayers;
	}

	public PopupInquiry getMnuInquiry() {
		return mnuInquiry;
	}

	public PopupMenus getMnuMenus() {
		return mnuMenus;
	}

	public PopupWindow getMnuWindow() {
		return mnuWindow;
	}

	public PopupHelp getMnuHelp() {
		return mnuHelp;
	}

	public JPanel getToolbarBlocks() {
		return toolbarBlocks;
	}

	public JPanel getToolbarSettings() {
		return toolbarSettings;
	}

	public JPanel getToolbarLayers() {
		return toolbarLayers;
	}

	//TOOLBAR_CONTROLS (TEMPORARIOS)
	//
	public JPanel getToolbarControlBasic() {
		return toolbarCtrl_Basic;
	}
	
}
