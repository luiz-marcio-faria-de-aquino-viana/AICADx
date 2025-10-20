/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 25/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.view;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.print.Printable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;

public abstract class CompView extends BasePanel implements ICompView, Printable, MouseWheelListener, MouseListener, MouseMotionListener, KeyResultListener, Runnable
{
//Private
	protected String name = null;
	
	protected int viewType = AppDefs.NULL_INT;
	
	protected MainFrame parentFrame = null;
	
	protected CadDocumentDef doc = null;
	
	/* THREADS */
	
	protected Thread keyPressedThread = null;
	protected boolean bKeyPressedRunning = false;
	
	protected boolean bCommandPromptFocus = true;
	
//Public
	
	public CompView(BaseFrame parentFrame)
	{
		super(parentFrame);
	}

    /* Methodes */
    
    public void init(String name, int viewType, MainFrame frm, CadDocumentDef doc) {
    	this.parentFrame = frm;
    	this.doc = doc;
    	this.name = name;
    	this.viewType = viewType;
    	
		this.setBackground(Color.LIGHT_GRAY);
		this.setForeground(Color.BLACK);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addComponentListener(this);
    }
    
	public abstract void initCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist);
	
	public abstract void resetCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist);
	
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

    /* RESET_PICKMODE_VARS */
    
    public abstract void resetPickModeVars();
    
    /* RESET_SELECTMODE_VARS */
    
    public abstract void resetSelectModeVars();

    /* RESET_ZOOMMODE_VARS */
    
    public abstract void resetZoomModeVars();

    /* BLIPS */
    
    public abstract void clearBlips();

    /* RESET ALL */
    
    public abstract void resetAll();

    /* REPAINT ALL */
    
	public abstract void repaintAll();
	
	/* PRINT VIEW */
	
	public abstract void printView();
    
	/* PROCESS OSNAP */
	
    public abstract GeomPoint3d processOsnap(ICadViewBase v, GeomPoint2d pt2dMcs); 
	
	/* SELECT */
    
	public abstract CadEntity selectEntity(GeomPoint2d pt2dMcs);

	/* PROCESS MOUSE EVENTS */
    
	public abstract void processMouseWheel(double rotationVal);
	
	public abstract void processMouseClicked_SelectMode(int xCursor, int yCursor);

	public abstract void processMouseClicked_PickMode(int xCursor, int yCursor);

	public abstract void processMouseClicked_ZoomMode(int xCursor, int yCursor);

	public abstract void processMouseClicked_SetCurrSelEnt(int xCursor, int yCursor);
	
	//PROCESS: MOUSE_CLICKED_EVENT
	//	
	public abstract void processMouseClicked(int mouseButton, int modifiers, int xCursor, int yCursor);
	
	//CHECK/PROCESS: MOUSE_DOUBLE_CLICKED_EVENT
	//	
	public abstract boolean checkMouseDoubleClicked(int mouseButton, int modifiers, int xCursor, int yCursor);
	
	public abstract void processMouseDoubleClicked(int mouseButton, int modifiers, int xCursor, int yCursor);
	
	//PROCESS: MOUSE_MOVED_EVENT
	//	
	public abstract void processMouseMoved(MouseEvent e);
	
	//PROCESS: MOUSE_DRAGGED_EVENT
	//	
	public abstract void processMouseDragged(MouseEvent e);

	//PROCESS: MOUSE_PRESSED_EVENT
	//	
	public abstract void processMousePressed(MouseEvent e);
	
	//PROCESS: MOUSE_RELEASED_EVENT
	//	
	public abstract void processMouseReleased(MouseEvent e);

	/* MOUSE EVENTS */

	@Override
	public abstract void mouseClicked(MouseEvent e);

	@Override
	public abstract void mouseMoved(MouseEvent e); 
	
    @Override
	public abstract void mouseDragged(MouseEvent e);

	@Override
	public abstract void mousePressed(MouseEvent e);

	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public abstract void mouseEntered(MouseEvent e);

	@Override
	public abstract void mouseExited(MouseEvent e);

	@Override
	public abstract void mouseWheelMoved(MouseWheelEvent e);
	
	/* COMPONENT EVENTS */

	@Override
	public abstract void componentResized(ComponentEvent e);

	@Override
	public abstract void componentMoved(ComponentEvent e);

	@Override
	public abstract void componentShown(ComponentEvent e);

	@Override
	public abstract void componentHidden(ComponentEvent e);

	/* KEY_EVENTS */
	
	@Override
	public abstract void actionKeyResultListener(ResultEvent e); 
	
	/* Threads */
	
	@Override
	public void run() 
	{
		this.bKeyPressedRunning = true;
		
		while( this.bKeyPressedRunning ) {
			if( bCommandPromptFocus ) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				this.requestFocus();
			}
			
			try {
				Thread.sleep(AppDefs.SCREENCONTEXT_TIMEOUT);
			}
			catch(Exception e) { }
		}
		this.bKeyPressedRunning = false;
	}
				
    /* Getters/Setters */

	public MainFrame getParent() {
		return this.parentFrame;
	}

	public String getName() {
		return name;
	}

	public int getViewType() {
		return viewType;
	}

	public CadDocumentDef getDocument() {
		return this.doc;
	}
		
}
