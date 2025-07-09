/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainPanel.java
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
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.CadView2d;
import br.com.tlmv.aicadxapp.cad.CadView3d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdAbout;
import br.com.tlmv.aicadxapp.cmd.CmdAmbiente;
import br.com.tlmv.aicadxapp.cmd.CmdArc;
import br.com.tlmv.aicadxapp.cmd.CmdArea;
import br.com.tlmv.aicadxapp.cmd.CmdAreaTable;
import br.com.tlmv.aicadxapp.cmd.CmdBox3D;
import br.com.tlmv.aicadxapp.cmd.CmdCircle;
import br.com.tlmv.aicadxapp.cmd.CmdClose;
import br.com.tlmv.aicadxapp.cmd.CmdCopy;
import br.com.tlmv.aicadxapp.cmd.CmdDistance;
import br.com.tlmv.aicadxapp.cmd.CmdErase;
import br.com.tlmv.aicadxapp.cmd.CmdExit;
import br.com.tlmv.aicadxapp.cmd.CmdJanela;
import br.com.tlmv.aicadxapp.cmd.CmdLine;
import br.com.tlmv.aicadxapp.cmd.CmdMirror;
import br.com.tlmv.aicadxapp.cmd.CmdMove;
import br.com.tlmv.aicadxapp.cmd.CmdNew;
import br.com.tlmv.aicadxapp.cmd.CmdOffset;
import br.com.tlmv.aicadxapp.cmd.CmdOpen;
import br.com.tlmv.aicadxapp.cmd.CmdPDupla;
import br.com.tlmv.aicadxapp.cmd.CmdPan;
import br.com.tlmv.aicadxapp.cmd.CmdParede;
import br.com.tlmv.aicadxapp.cmd.CmdPiso;
import br.com.tlmv.aicadxapp.cmd.CmdPoint;
import br.com.tlmv.aicadxapp.cmd.CmdPolygon;
import br.com.tlmv.aicadxapp.cmd.CmdPorta;
import br.com.tlmv.aicadxapp.cmd.CmdPrint;
import br.com.tlmv.aicadxapp.cmd.CmdRectangle;
import br.com.tlmv.aicadxapp.cmd.CmdSave;
import br.com.tlmv.aicadxapp.cmd.CmdSaveAs;
import br.com.tlmv.aicadxapp.cmd.CmdScale;
import br.com.tlmv.aicadxapp.cmd.CmdText;
import br.com.tlmv.aicadxapp.cmd.CmdZoomExt;
import br.com.tlmv.aicadxapp.cmd.CmdZoomIn;
import br.com.tlmv.aicadxapp.cmd.CmdZoomMoveBackward;
import br.com.tlmv.aicadxapp.cmd.CmdZoomMoveForward;
import br.com.tlmv.aicadxapp.cmd.CmdZoomOut;
import br.com.tlmv.aicadxapp.cmd.CmdZoomTurnLeft;
import br.com.tlmv.aicadxapp.cmd.CmdZoomTurnRight;
import br.com.tlmv.aicadxapp.cmd.CmdZoomWindow;
import br.com.tlmv.aicadxapp.frm.comp.Comp3DView;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.comp.CompLeftView;
import br.com.tlmv.aicadxapp.frm.comp.CompNorthView;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.comp.CompRightView;
import br.com.tlmv.aicadxapp.frm.comp.CompSouthView;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.LayerExplorerResultVO;

import javax.swing.JTabbedPane;

public class MainPanel extends JPanel implements ActionListener, ItemListener, ListSelectionListener, ResultListener 
{
//Private
	private static MainFrame gParentFrame = null;
	private static MainPanel gPanel = null;
	
	/* PANELS */

	private JTabbedPane tabMainPanel = null; 

	//private JPanel panViewPlan = null;

	//private JPanel panView3D = null;

	/* COMPONENTS */
	
	private CompPlanView viewPlan = null; 

	private CompNorthView viewNorth = null; 

	private CompSouthView viewSouth = null; 

	private CompLeftView viewWest = null; 

	private CompRightView viewEast = null; 

	private Comp3DView view3D = null; 

	private CompCommandPrompt commandPrompt = null;

	/* VARS */
	
	private String currAction = AppDefs.ACTION_NONE;
	
	/* Methodes */
		
	/* TABBED PANNELS */
	
	private void initTabPanel()
	{
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		Insets insets = this.getInsets();

		int x = insets.left + 10;
		int y = insets.top + 10;

		int w = 1024;
		int h = 768;
		
		this.tabMainPanel = new JTabbedPane();
		this.tabMainPanel.setBounds(x, y, w, h);
		this.tabMainPanel.setTabPlacement(JTabbedPane.BOTTOM);
		this.add(this.tabMainPanel, BorderLayout.CENTER);
		
		// *** View: Plan
		String nViewPlan = "Plan";
		//this.panViewPlan = FormControlUtil.newTabPanel(nViewPlan);
		//this.panViewPlan.setBounds(x, y, w, h);
		this.viewPlan = new CompPlanView(MainPanel.gParentFrame, doc);	
		this.viewPlan.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nViewPlan, this.viewPlan);
		this.tabMainPanel.setMnemonicAt(0, KeyEvent.VK_P);
		
		// *** View: North
		String nViewNorth = "North";
		//this.panViewPlan = FormControlUtil.newTabPanel(nViewPlan);
		//this.panViewPlan.setBounds(x, y, w, h);
		this.viewNorth = new CompNorthView(MainPanel.gParentFrame, doc);	
		this.viewNorth.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nViewNorth, this.viewNorth);
		this.tabMainPanel.setMnemonicAt(1, KeyEvent.VK_N);
		
		// *** View: Left
		String nViewWest = "West";
		//this.panViewPlan = FormControlUtil.newTabPanel(nViewPlan);
		//this.panViewPlan.setBounds(x, y, w, h);
		this.viewWest = new CompLeftView(MainPanel.gParentFrame, doc);	
		this.viewWest.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nViewWest, this.viewWest);
		this.tabMainPanel.setMnemonicAt(1, KeyEvent.VK_W);
		
		// *** View: Right
		String nViewEast = "East";
		//this.panViewPlan = FormControlUtil.newTabPanel(nViewPlan);
		//this.panViewPlan.setBounds(x, y, w, h);
		this.viewEast = new CompRightView(MainPanel.gParentFrame, doc);	
		this.viewEast.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nViewEast, this.viewEast);
		this.tabMainPanel.setMnemonicAt(1, KeyEvent.VK_E);
		
		// *** View: South
		String nViewSouth = "South";
		//this.panViewPlan = FormControlUtil.newTabPanel(nViewPlan);
		//this.panViewPlan.setBounds(x, y, w, h);
		this.viewSouth = new CompSouthView(MainPanel.gParentFrame, doc);	
		this.viewSouth.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nViewSouth, this.viewSouth);
		this.tabMainPanel.setMnemonicAt(2, KeyEvent.VK_S);

		// *** View: 3D Model
		String nView3D = "3D";
		//this.panView3D = FormControlUtil.newTabPanel(nView3D);
		//this.panView3D.setBounds(x, y, w, h);
		this.view3D = new Comp3DView(MainPanel.gParentFrame, doc);	
		this.view3D.setBounds(x, y, w, h);	
		this.tabMainPanel.addTab(nView3D, this.view3D);
		this.tabMainPanel.setMnemonicAt(3, KeyEvent.VK_3);
	}	
	
	public void initCmdPrompt()
	{
		this.commandPrompt = new CompCommandPrompt(this.viewPlan, AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_MAXSIZE);
		this.add(this.commandPrompt, BorderLayout.SOUTH);
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
		
		this.initTabPanel();
		this.initCmdPrompt();
	}
	
	public void refreshAll()
	{
		if(this.viewPlan != null)
			this.viewPlan.repaint();
		
		if(this.view3D != null)
			this.view3D.repaint();
		
		if(this.viewSouth != null)
			this.viewSouth.repaint();
		
		if(this.viewNorth != null)
			this.viewNorth.repaint();
		
		if(this.viewWest != null)
			this.viewWest.repaint();
		
		if(this.viewEast != null)
			this.viewEast.repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	/* Components */
	
	public ICompView getCurrView()
	{
		int pos = this.tabMainPanel.getSelectedIndex();
		if(pos != -1) {
			ICompView v = (ICompView)this.tabMainPanel.getComponentAt(pos);
			return v;
		}
		return null;
	}
	
	/* Actions */
	
	//DRAW1
	//
	public void doActionFileNew(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdNew cmd = new CmdNew();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionFileOpen(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdOpen cmd = new CmdOpen();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionFileClose(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdClose cmd = new CmdClose();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionFileSave(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdSave cmd = new CmdSave();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionFileSaveAs(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdSaveAs cmd = new CmdSaveAs();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionFilePrint(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPrint cmd = new CmdPrint();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionFileExit(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdExit cmd = new CmdExit();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	//ARQ1
	//
	public void doActionArq1Piso(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPiso cmd = new CmdPiso();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionArq1Parede(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdParede cmd = new CmdParede();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionArq1Ambiente2(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdAmbiente cmd = new CmdAmbiente();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionArq1Porta(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPorta cmd = new CmdPorta();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionArq1PDupla(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPDupla cmd = new CmdPDupla();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionArq1Janela(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdJanela cmd = new CmdJanela();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionArq1Area(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdArea cmd = new CmdArea();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	//DRAW1
	//
	public void doActionDraw1Point(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPoint cmd = new CmdPoint();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Line(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdLine cmd = new CmdLine();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Rectangle(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdRectangle cmd = new CmdRectangle();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Polygon(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPolygon cmd = new CmdPolygon();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Circle(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdCircle cmd = new CmdCircle();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Arc(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdArc cmd = new CmdArc();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Text(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdText cmd = new CmdText();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionDraw1Box3D(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdBox3D cmd = new CmdBox3D();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionDraw1Offset(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdOffset cmd = new CmdOffset();
		cmd.executeCommand(app, frm, cad, doc);
	}

	public void doActionDraw1AreaTable(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdAreaTable cmd = new CmdAreaTable();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	//EDIT2
	//
	public void doActionEdit2Erase(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdErase cmd = new CmdErase();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionEdit2Copy(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdCopy cmd = new CmdCopy();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionEdit2Mirror(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdMirror cmd = new CmdMirror();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionEdit2Move(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdMove cmd = new CmdMove();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionEdit2Scale(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdScale cmd = new CmdScale();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	//PAN and ZOOM
	//
	public void doActionZoomPan(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdPan cmd = new CmdPan();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomIn(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomIn cmd = new CmdZoomIn();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomOut(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomOut cmd = new CmdZoomOut();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomWindow(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomWindow cmd = new CmdZoomWindow();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomExt(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomExt cmd = new CmdZoomExt();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomMoveForward(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomMoveForward cmd = new CmdZoomMoveForward();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomMoveBackward(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomMoveBackward cmd = new CmdZoomMoveBackward();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomTurnLeft(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomTurnLeft cmd = new CmdZoomTurnLeft();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionZoomTurnRight(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdZoomTurnRight cmd = new CmdZoomTurnRight();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionInquiryDistance(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdDistance cmd = new CmdDistance();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	public void doActionHelpAbout(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CmdAbout cmd = new CmdAbout();
		cmd.executeCommand(app, frm, cad, doc);
	}
	
	//SETTINGS
	//
	public void doActionSettingsGridOnOff(ActionEvent e)
	{
		ICompView v = this.getCurrView();

		int gridmode = v.getGridmode();
		if(gridmode == AppDefs.GRIDMODE_ON) {
			gridmode = AppDefs.GRIDMODE_OFF;
		}
		else {
			gridmode = AppDefs.GRIDMODE_ON;
		}
		v.setGridmode(gridmode);
		
		String str = (gridmode == AppDefs.GRIDMODE_ON) ? "[ GRID ON ]" : "[ GRID OFF ]"; 
		PromptUtil.prompt(str);
		
		v.repaintAll();
	}

	public void doActionSettingsSnapOnOff(ActionEvent e)
	{
		ICompView v = this.getCurrView();

		int snapmode = v.getSnapmode();
		if(snapmode == AppDefs.SNAPMODE_ON) {
			snapmode = AppDefs.SNAPMODE_OFF;
		}
		else {
			snapmode = AppDefs.SNAPMODE_ON;
		}
		v.setSnapmode(snapmode);
		
		String str = (snapmode == AppDefs.SNAPMODE_ON) ? "[ SNAP ON ]" : "[ SNAP OFF ]"; 
		PromptUtil.prompt(str);
	}

	public void doActionSettingsOrthoOnOff(ActionEvent e)
	{
		ICompView v = this.getCurrView();

		int orthomode = v.getOrthomode();
		if(orthomode == AppDefs.ORTHOMODE_ON) {
			orthomode = AppDefs.ORTHOMODE_OFF;
		}
		else {
			orthomode = AppDefs.ORTHOMODE_ON;
		}
		v.setOrthomode(orthomode);
		
		String str = (orthomode == AppDefs.ORTHOMODE_ON) ? "[ ORTHO ON ]" : "[ ORTHO OFF ]"; 
		PromptUtil.prompt(str);
	}
	
	//LAYERS
	//
	public void doActionLayersLayerExplorer(ActionEvent e)
	{
		AppMain app = AppMain.getApp();
		MainFrame frm = MainPanel.gParentFrame;
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		LayerExplorerFrame layerFrm = new LayerExplorerFrame(frm);
		layerFrm.init(doc, this);

		layerFrm.show();
	
		//TODO!
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
		
		//ACTIONS: FILE
		if( AppDefs.ACTION_FILE_NEW.equals(e.getActionCommand()) )
		{
			doActionFileNew(e);
		}
		else if( AppDefs.ACTION_FILE_OPEN.equals(e.getActionCommand()) )
		{
			doActionFileOpen(e);
		}
		else if( AppDefs.ACTION_FILE_CLOSE.equals(e.getActionCommand()) )
		{
			doActionFileClose(e);
		}
		else if( AppDefs.ACTION_FILE_SAVE.equals(e.getActionCommand()) )
		{
			doActionFileSave(e);
		}
		else if( AppDefs.ACTION_FILE_SAVEAS.equals(e.getActionCommand()) )
		{
			doActionFileSaveAs(e);
		}		
		else if( AppDefs.ACTION_FILE_PRINT.equals(e.getActionCommand()) )
		{
			doActionFilePrint(e);
		}
		else if( AppDefs.ACTION_FILE_EXIT.equals(e.getActionCommand()) )
		{
			doActionFileExit(e);
		}		
		//ACTIONS: ARQ1
		else if( AppDefs.ACTION_ARQ1_PISO.equals(e.getActionCommand()) )
		{
			doActionArq1Piso(e);
		}
		else if( AppDefs.ACTION_ARQ1_PAREDE.equals(e.getActionCommand()) )
		{
			doActionArq1Parede(e);
		}
		else if( AppDefs.ACTION_ARQ1_AMBIENTE2.equals(e.getActionCommand()) )
		{
			doActionArq1Ambiente2(e);
		}
		else if( AppDefs.ACTION_ARQ1_PORTA.equals(e.getActionCommand()) )
		{
			doActionArq1Porta(e);
		}
		else if( AppDefs.ACTION_ARQ1_PDUPLA.equals(e.getActionCommand()) )
		{
			doActionArq1PDupla(e);
		}
		else if( AppDefs.ACTION_ARQ1_JANELA.equals(e.getActionCommand()) )
		{
			doActionArq1Janela(e);
		}
		else if( AppDefs.ACTION_ARQ1_AREA.equals(e.getActionCommand()) )
		{
			doActionArq1Area(e);
		}
		//ACTIONS: DRAW1
		else if( AppDefs.ACTION_DRAW1_OFFSET.equals(e.getActionCommand()) )
		{
			doActionDraw1Offset(e);
		}
		else if( AppDefs.ACTION_DRAW1_POINT.equals(e.getActionCommand()) )
		{
			doActionDraw1Point(e);
		}
		else if( AppDefs.ACTION_DRAW1_LINE.equals(e.getActionCommand()) )
		{
			doActionDraw1Line(e);
		}
		else if( AppDefs.ACTION_DRAW1_RECTANGLE.equals(e.getActionCommand()) )
		{
			doActionDraw1Rectangle(e);
		}
		else if( AppDefs.ACTION_DRAW1_POLYGON.equals(e.getActionCommand()) )
		{
			doActionDraw1Polygon(e);
		}
		else if( AppDefs.ACTION_DRAW1_ARCSCA.equals(e.getActionCommand()) )
		{
			doActionDraw1Arc(e);
		}
		else if( AppDefs.ACTION_DRAW1_CIRCLECR.equals(e.getActionCommand()) )
		{
			doActionDraw1Circle(e);
		}
		else if( AppDefs.ACTION_DRAW1_TEXT.equals(e.getActionCommand()) )
		{
			doActionDraw1Text(e);
		}
		else if( AppDefs.ACTION_DRAW1_AREATABLE.equals(e.getActionCommand()) )
		{
			doActionDraw1AreaTable(e);
		}
		else if( AppDefs.ACTION_DRAW1_BOX3D.equals(e.getActionCommand()) )
		{
			doActionDraw1Box3D(e);
		}
		//ACTIONS: EDIT2
		else if( AppDefs.ACTION_EDIT2_ERASE.equals(e.getActionCommand()) )
		{
			doActionEdit2Erase(e);
		}
		else if( AppDefs.ACTION_EDIT2_COPY.equals(e.getActionCommand()) )
		{
			doActionEdit2Copy(e);
		}
		else if( AppDefs.ACTION_EDIT2_MIRROR.equals(e.getActionCommand()) )
		{
			doActionEdit2Mirror(e);
		}
		else if( AppDefs.ACTION_EDIT2_MOVE.equals(e.getActionCommand()) )
		{
			doActionEdit2Move(e);
		}
		else if( AppDefs.ACTION_EDIT2_SCALE.equals(e.getActionCommand()) )
		{
			doActionEdit2Scale(e);
		}
		else if( AppDefs.ACTION_EDIT2_ROTATE.equals(e.getActionCommand()) )
		{
			//doActionEdit2Rotate(e);
		}
		//ACTIONS: ZOOM
		else if( AppDefs.ACTION_ZOOM_PAN.equals(e.getActionCommand()) )
		{
			doActionZoomPan(e);
		}
		else if( AppDefs.ACTION_ZOOM_ZOOMIN.equals(e.getActionCommand()) )
		{
			doActionZoomIn(e);
		}
		else if( AppDefs.ACTION_ZOOM_ZOOMOUT.equals(e.getActionCommand()) )
		{
			doActionZoomOut(e);
		}
		else if( AppDefs.ACTION_ZOOM_ZOOMWINDOW.equals(e.getActionCommand()) )
		{
			doActionZoomWindow(e);
		}
		else if( AppDefs.ACTION_ZOOM_ZOOMEXT.equals(e.getActionCommand()) )
		{
			doActionZoomExt(e);
		}
		//ACTIONS: ZOOM-3D
		else if( AppDefs.ACTION_ZOOM_MOVEFORWARD.equals(e.getActionCommand()) )
		{
			doActionZoomMoveForward(e);
		}
		else if( AppDefs.ACTION_ZOOM_MOVEBACKWARD.equals(e.getActionCommand()) )
		{
			doActionZoomMoveBackward(e);
		}
		else if( AppDefs.ACTION_ZOOM_TURNLEFT.equals(e.getActionCommand()) )
		{
			doActionZoomTurnLeft(e);
		}
		else if( AppDefs.ACTION_ZOOM_TURNRIGHT.equals(e.getActionCommand()) )
		{
			doActionZoomTurnRight(e);
		}
		//ACTIONS: SETTINGS
		else if( AppDefs.ACTION_SETTINGS_GRIDONOFF.equals(e.getActionCommand()) )
		{
			doActionSettingsGridOnOff(e);
		}
		else if( AppDefs.ACTION_SETTINGS_SNAPONOFF.equals(e.getActionCommand()) )
		{
			doActionSettingsSnapOnOff(e);
		}
		else if( AppDefs.ACTION_SETTINGS_ORTHOONOFF.equals(e.getActionCommand()) )
		{
			doActionSettingsOrthoOnOff(e);
		}
		//ACTIONS: LAYERS
		else if( AppDefs.ACTION_LAYERS_LAYEREXPLORER.equals(e.getActionCommand()) )
		{
			doActionLayersLayerExplorer(e);
		}		
		//ACTIONS: INQUIRY
		else if( AppDefs.ACTION_INQUIRY_DIST.equals(e.getActionCommand()) )
		{
			doActionInquiryDistance(e);
		}
		//ACTIONS: HELP
		else if( AppDefs.ACTION_HELP_ABOUT.equals(e.getActionCommand()) )
		{
			doActionHelpAbout(e);
		}
		
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

	public String getCurrAction() {
		return currAction;
	}

	public void setCurrAction(String currAction) {
		this.currAction = currAction;
	}
	
}
