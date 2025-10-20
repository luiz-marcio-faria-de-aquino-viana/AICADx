/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BaseFrameMenu.java
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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.frm.popup.PopupBlocks;
import br.com.tlmv.aicadxapp.frm.popup.PopupDisplay;
import br.com.tlmv.aicadxapp.frm.popup.PopupDraw1;
import br.com.tlmv.aicadxapp.frm.popup.PopupDraw2;
import br.com.tlmv.aicadxapp.frm.popup.PopupDraw3D;
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
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarDraw3D;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarEdit2;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarFile;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarHelp;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarInquiry;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarSettings;
import br.com.tlmv.aicadxapp.frm.toolbar.ToolbarZoom;
import br.com.tlmv.aicadxmod.IModule;

public class BaseFrameMenu 
{
//Private
	private BaseFrame mainFrame = null;
	private BasePanel mainPanel = null; 
	
	//MAIN_MENU
	//
	private JMenuBar mnuMain = null;

	//POPUP_MENU
	//
	//PopupMenu
	private JPopupMenu mnuPopup = null;
	//
	private PopupFile mnuFile = null; 
	private PopupEdit1 mnuEdit1 = null; 
	private PopupBlocks mnuBlocks = null; 
	private PopupDraw1 mnuDraw1 = null; 
	private PopupDraw2 mnuDraw2 = null; 
	private PopupDraw3D mnuDraw3D = null; 
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
	private JPanel toolbarDraw3D = null;
	private JPanel toolbarEdit2 = null;
	private JPanel toolbarZoom = null;
	private JPanel toolbarSettings = null;
	private JPanel toolbarLayers = null;
	private JPanel toolbarInquiry = null;
	private JPanel toolbarHelp = null;

	//Toolbars - CONTROLES TEMPORARIOS
	private ArrayList<JPanel> lsToolbarCtrl = null;
	private JPanel toolbarCtrl_Basic = null;

//Public

	public BaseFrameMenu(BaseFrame frame, BasePanel panel)
	{
		this.mainFrame = frame;
		this.mainPanel = panel; 
		
		this.mnuMain = new JMenuBar();
		
		this.lsToolbarCtrl = new ArrayList<JPanel>();
		
		createMainMenu();
		createPopupMenu();
		createIconMenu();
		//createStatusBar();
		
		this.activateMenu(AppDefs.CURR_ACTION_MENU);
	}
	
	/* Methodes */
	
	public void showPopupMenu(int x, int y)
	{
		this.mnuPopup.show(this.mainPanel, x, y);
	}
	
	/* CREATE: MODULES */

	public void createModuleMenu(JMenuBar mnubar, ActionListener listener)
	{
		AppMain app = AppMain.getApp();

		ArrayList<IModule> lsModule = app.getLsModule();
		for(IModule o : lsModule) {
			o.createMenu(mnubar, listener);
		}
	}
	
	/* CREATE: MENUS */

	public void createMainMenu()
	{
		this.mnuMain = new JMenuBar();
		
		this.mnuFile = new PopupFile(); 
		this.mnuFile.createMenu(this.mnuMain, this.mainPanel);		
		
		//mnuEdit1 = new PopupEdit1(); 
		//mnuEdit1.createMenu(mnuMain, this);		
		
		this.mnuBlocks = new PopupBlocks(); 
		this.mnuBlocks.createMenu(this.mnuMain, this.mainPanel);		
		
		this.mnuDraw1 = new PopupDraw1(); 
		this.mnuDraw1.createMenu(this.mnuMain, this.mainPanel);		
		
		//mnuDraw2 = new PopupDraw2(); 
		//mnuDraw2.createMenu(mnuMain, this);		
		
		this.mnuDraw3D = new PopupDraw3D(); 
		this.mnuDraw3D.createMenu(this.mnuMain, this.mainPanel);		
		
		this.mnuEdit2 = new PopupEdit2(); 
		this.mnuEdit2.createMenu(this.mnuMain, this.mainPanel);		
		
		//mnuModify = new PopupModify(); 
		//mnuModify.createMenu(mnuMain, this);		
		
		//mnuTools = new PopupTools(); 
		//mnuTools.createMenu(mnuMain, this);		
		
		this.mnuDisplay = new PopupDisplay(); 
		this.mnuDisplay.createMenu(this.mnuMain, this.mainPanel);		
		
		this.mnuSettings = new PopupSettings(); 
		this.mnuSettings.createMenu(this.mnuMain, this.mainPanel);		
		
		this.mnuLayers = new PopupLayers(); 
		this.mnuLayers.createMenu(this.mnuMain, this.mainPanel);		
		
		this.mnuInquiry = new PopupInquiry(); 
		this.mnuInquiry.createMenu(this.mnuMain, this.mainPanel);		

		createModuleMenu(this.mnuMain, this.mainPanel);
		
		this.mnuMenus = new PopupMenus(); 
		this.mnuMenus.createMenu(this.mnuMain, this.mainPanel);		
		
		//PopupWindow mnuWindow = new PopupWindow(); 
		//mnuWindow.createMenu(mnuMain, this);		
		
		this.mnuHelp = new PopupHelp(); 
		this.mnuHelp.createMenu(this.mnuMain, this.mainPanel);		
		
		this.mainFrame.setJMenuBar(this.mnuMain) ;		
	}
	
	public void createPopupMenu()
	{
		this.mnuPopup = new JPopupMenu();
		
		PopupOsnap mnuOsnap = new PopupOsnap(); 
		mnuOsnap.createPopupMenu(this.mnuPopup, this.mainPanel);		
	}

	public void createModuleIconMenu(JPanel iconmnu, ActionListener listener)
	{
		AppMain app = AppMain.getApp();

		ArrayList<IModule> lsModule = app.getLsModule();
		for(IModule o : lsModule) {
			o.createToolbarMenu(iconmnu, listener);
		}
	}
	
	/* CREATE: ICONMENU */
	
	public void createIconMenu()
	{
		this.createTopIconMenu();
		this.createLeftIconMenu();
		this.createRightIconMenu();
	}

	public void createTopIconMenu()
	{
		this.icomnuTop1 = new JPanel();
		
		FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
		this.icomnuTop1.setLayout(layout1);
		
		//TOOLBAR_FILE
		//
		this.toolbarFile = new JPanel();
		
		GridLayout layout = new GridLayout(1, 0, 0, 0);
		this.toolbarFile.setLayout(layout);
		
		ToolbarFile mnuFile  = new ToolbarFile();
		mnuFile.createToolbarMenu(this.toolbarFile, this.mainPanel);	

		this.icomnuTop1.add(this.toolbarFile);
		
		//TOOLBAR_SETTINGS
		//
		this.toolbarSettings = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		this.toolbarSettings.setLayout(layout);
		
		ToolbarSettings mnuSettings  = new ToolbarSettings();
		mnuSettings.createToolbarMenu(this.toolbarSettings, this.mainPanel);	

		this.icomnuTop1.add(this.toolbarSettings);
		
		//TOOLBAR_INQUIRY
		//
		this.toolbarInquiry = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		this.toolbarInquiry.setLayout(layout);
		
		ToolbarInquiry mnuInquiry  = new ToolbarInquiry();
		mnuInquiry.createToolbarMenu(this.toolbarInquiry, this.mainPanel);	

		this.icomnuTop1.add(this.toolbarInquiry);
		
		//TOOLBAR_HELP
		//
		this.toolbarHelp = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		this.toolbarHelp.setLayout(layout);
		
		ToolbarHelp mnuHelp  = new ToolbarHelp();
		mnuHelp.createToolbarMenu(this.toolbarHelp, this.mainPanel);	

		this.icomnuTop1.add(this.toolbarHelp);
		
		//TOOLBAR_CONTROL
		//
		this.toolbarCtrl_Basic = new JPanel();
		
		layout = new GridLayout(1, 0, 0, 0);
		this.toolbarCtrl_Basic.setLayout(layout);
		
		ToolbarControl mnuCtrl  = new ToolbarControl();
		mnuCtrl.createToolbarMenu(this.toolbarCtrl_Basic, this.mainPanel);
		this.toolbarCtrl_Basic.setVisible(false);

		this.icomnuTop1.add(this.toolbarCtrl_Basic);
		
		this.lsToolbarCtrl.add(this.toolbarCtrl_Basic);
		
		//TOOLBAR_MODULE
		//
		this.createModuleIconMenu(this.icomnuTop1, this.mainPanel);
		
		this.mainPanel.add(this.icomnuTop1, BorderLayout.NORTH);		
	}

	public void createRightIconMenu()
	{
		this.icomnuRight1 = new JPanel();
		
		FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
		this.icomnuRight1.setLayout(layout1);
		
		//TOOLBAR_ZOOM
		//
		this.toolbarZoom = new JPanel();
		
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		this.toolbarZoom.setLayout(layout);
		
		ToolbarZoom mnuZoom  = new ToolbarZoom();
		mnuZoom.createToolbarMenu(this.toolbarZoom, this.mainPanel);	

		this.icomnuRight1.add(this.toolbarZoom);
		
		this.mainPanel.add(this.icomnuRight1, BorderLayout.EAST);
	}

	public void createLeftIconMenu()
	{
		this.icomnuLeft1 = new JPanel();
		
		FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
		this.icomnuLeft1.setLayout(layout1);

		JPanel localPanel = new JPanel();
		
		GridLayout layout2 = new GridLayout(0, 2, 0, 0);
		localPanel.setLayout(layout2);
		
		//TOOLBAR_DRAW1
		//
		this.toolbarDraw1 = new JPanel();
		
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		this.toolbarDraw1.setLayout(layout);
		
		ToolbarDraw1 mnuDraw1  = new ToolbarDraw1();
		mnuDraw1.createToolbarMenu(this.toolbarDraw1, this.mainPanel);	

		localPanel.add(this.toolbarDraw1);
		
		//TOOLBAR_DRAW3D
		//
		this.toolbarDraw3D = new JPanel();
		
		layout = new GridLayout(0, 1, 0, 0);
		this.toolbarDraw3D.setLayout(layout);
		
		ToolbarDraw3D mnuDraw3D  = new ToolbarDraw3D();
		mnuDraw3D.createToolbarMenu(this.toolbarDraw3D, this.mainPanel);	

		localPanel.add(this.toolbarDraw3D);
		
		//TOOLBAR_EDIT2
		//
		this.toolbarEdit2 = new JPanel();
		
		layout = new GridLayout(0, 1, 0, 0);
		this.toolbarEdit2.setLayout(layout);
		
		ToolbarEdit2 mnuEdit2  = new ToolbarEdit2();
		mnuEdit2.createToolbarMenu(this.toolbarEdit2, this.mainPanel);	

		localPanel.add(this.toolbarEdit2);
		
		//TOOLBAR_BLOCKS
		//
		this.toolbarBlocks = new JPanel();
		
		layout = new GridLayout(0, 1, 0, 0);
		this.toolbarBlocks.setLayout(layout);
		
		ToolbarBlocks mnuBlocks  = new ToolbarBlocks();
		mnuBlocks.createToolbarMenu(this.toolbarBlocks, this.mainPanel);	

		localPanel.add(this.toolbarBlocks);
		
		this.icomnuLeft1.add(localPanel);
		
		this.mainPanel.add(this.icomnuLeft1, BorderLayout.WEST);
	}
	
	/* MENUS */
	
	public void activateMenu(String actionCommand)
	{
		AppMain app = AppMain.getApp();
		
		this.mnuFile.setVisible(true); 
		//mnuEdit1.setVisible(true); 
		//mnuBlocks.setVisible(true); 
		this.mnuDraw1.setVisible(true); 
		//mnuDraw2.setVisible(true); 
		this.mnuDraw3D.setVisible(true); 
		this.mnuEdit2.setVisible(true); 
		//mnuModify.setVisible(true); 
		//mnuTools.setVisible(true); 
		this.mnuDisplay.setVisible(true); 
		this.mnuSettings.setVisible(true); 
		this.mnuLayers.setVisible(true); 
		this.mnuInquiry.setVisible(true); 
		this.mnuMenus.setVisible(true); 
		//mnuWindow.setEnabled(true); 
		this.mnuHelp.setVisible(true); 

		//MODULES
		//
		IModule oArqMod = app.getArqModule();
		if( oArqMod != null) oArqMod.setVisible(false);
		
		IModule oApMod = app.getApModule();
		if( oApMod != null) oApMod.setVisible(false);
		
		IModule oElMod = app.getElModule();
		if( oElMod != null) oElMod.setVisible(false);
		
		IModule oEsMod = app.getEsModule();
		if( oEsMod != null) oEsMod.setVisible(false);
		
		IModule oRdpMod = app.getRpdModule();
		if( oRdpMod != null) oRdpMod.setVisible(false);
		
		IModule oHMod = app.getHidModule();
		if( oHMod != null) oHMod.setVisible(false);
		
		IModule oGMod = app.getGasModule();
		if( oGMod != null) oGMod.setVisible(false);
		
		IModule oTmarMod = app.getTmarModule();
		if( oTmarMod != null) oTmarMod.setVisible(false);
				
		//ACTION_MENUS
		//
		if( AppDefs.ACTION_MENUS_ARQMENU.equals(actionCommand) ) {
			if( oArqMod != null) oArqMod.setVisible(true); 
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
			if( oElMod != null) oElMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_ESMENU.equals(actionCommand) ) {
			if( oEsMod != null) oEsMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_APMENU.equals(actionCommand) ) {
			if( oApMod != null) oApMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_RPDMENU.equals(actionCommand) ) {
			if( oRdpMod != null) oRdpMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_HMENU.equals(actionCommand) ) {
			if( oHMod != null ) oHMod.setVisible(true);
		}
		else if( AppDefs.ACTION_MENUS_INCMENU.equals(actionCommand) ) {
			/* nothing todo! */
		}
		else if( AppDefs.ACTION_MENUS_GMENU.equals(actionCommand) ) {
			if( oGMod != null ) oGMod.setVisible(true);
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
		else if( AppDefs.ACTION_MENUS_TMARMENU.equals(actionCommand) ) {
			if( oTmarMod != null ) oTmarMod.setVisible(true);
		}
	}

	/* Getters/Setters */

	public BasePanel getMainPanel() {
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

	public PopupDraw3D getMnuDraw3D() {
		return mnuDraw3D;
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

	public ArrayList<JPanel> getLsToolbarCtrl() {
		return lsToolbarCtrl;
	}

	public void setLsToolbarCtrl(ArrayList<JPanel> lsToolbarCtrl) {
		this.lsToolbarCtrl = lsToolbarCtrl;
	}

	//TOOLBAR_CONTROLS (TEMPORARIOS)
	//
	public JPanel getToolbarControlBasic() {
		return toolbarCtrl_Basic;
	}

}
