/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdBase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 08/05/2025
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

package br.com.tlmv.aicadxapp.cmd;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.EntSelVO;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public abstract class CmdBase implements ICmdBase, Runnable
{
//Private Static
	private static ArrayList<Thread> lsThreads = new ArrayList<Thread>();
	private static boolean isCmdActive = false;
	
//Private
	private AppMain app = null;
	private MainFrame frm = null;
	private MainPanel panel = null;
	private AppCadMain cad = null;
	private CadDocumentDef doc = null;
	
	private boolean bShowToolbarControl = true;
	private boolean bCancelActiveCommand = true;

	private String cmdName = null;
	
	// Threads
	//
	private Thread thread = null;
	private boolean bRunning = false;

	private R r = null;

	/* Methodes */
	
	private ArrayList<PromptOptionVO> getPromptOptionSelectionMode()
	{
		/* PromptOption
		*/
		PromptOptionVO optSelectAdd = new PromptOptionVO(
			AppDefs.OPT_SELECT_ADD_VAL, this.getR().getString( R.CMD_OPT_SELECT_ADD ), "D", false);

		PromptOptionVO optSelectRemove = new PromptOptionVO(
			AppDefs.OPT_SELECT_REMOVE_VAL, this.getR().getString( R.CMD_OPT_SELECT_REMOVE ), "R", false);

		PromptOptionVO optSelectObject = new PromptOptionVO(
				AppDefs.OPT_SELECT_OBJECT_VAL, this.getR().getString( R.CMD_OPT_SELECT_OBJECT ), "O", false);

		PromptOptionVO optSelectFirst = new PromptOptionVO(
				AppDefs.OPT_SELECT_FIRST_VAL, this.getR().getString( R.CMD_OPT_SELECT_FIRST ), "F", false);

		PromptOptionVO optSelectLast = new PromptOptionVO(
			AppDefs.OPT_SELECT_LAST_VAL, this.getR().getString( R.CMD_OPT_SELECT_LAST ), "L", false);

		PromptOptionVO optSelectWindow = new PromptOptionVO(
			AppDefs.OPT_SELECT_WINDOW_VAL, this.getR().getString( R.CMD_OPT_SELECT_WINDOW ), "W", false);

		PromptOptionVO optSelectCrossing = new PromptOptionVO(
			AppDefs.OPT_SELECT_CROSSING_VAL, this.getR().getString( R.CMD_OPT_SELECT_CROSSING ), "C", false);

		PromptOptionVO optSelectFence = new PromptOptionVO(
				AppDefs.OPT_SELECT_FENCE_VAL, this.getR().getString( R.CMD_OPT_SELECT_FENCE ), "E", false);

		PromptOptionVO optSelectPrevious = new PromptOptionVO(
			AppDefs.OPT_SELECT_PREVIOUS_VAL, this.getR().getString( R.CMD_OPT_SELECT_PREVIOUS ), "P", false);

		PromptOptionVO optSelectAll = new PromptOptionVO(
			AppDefs.OPT_SELECT_ALL_VAL, this.getR().getString( R.CMD_OPT_SELECT_ALL ), "A", false);

		/* ListOfPromptOptions
		*/
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();

		lsPromptOptions.add(optSelectAdd);
		lsPromptOptions.add(optSelectRemove);
		lsPromptOptions.add(optSelectObject);
		lsPromptOptions.add(optSelectFirst);
		lsPromptOptions.add(optSelectLast);
		lsPromptOptions.add(optSelectWindow);
		lsPromptOptions.add(optSelectCrossing);
		lsPromptOptions.add(optSelectFence);
		lsPromptOptions.add(optSelectPrevious);
		lsPromptOptions.add(optSelectAll);
		
		return lsPromptOptions;
	}	
	
//Public
	
	public CmdBase(String cmdName, boolean bShowToolbarControl, boolean bCancelActiveCommand) {
		this.bShowToolbarControl = bShowToolbarControl;
		this.bCancelActiveCommand = bCancelActiveCommand;

		this.cmdName = cmdName;
	}

	/* Methodes */
	
	@Override
	public String getCommandName() {
		return this.cmdName;
	}
	
	@Override
	public void refreshAll() {
		MainPanel panel = (MainPanel)this.getPanel();
		panel.refreshAll();
	}
	
	/* SELECTION_SET */

	public ArrayList<CadEntity> getSelectionSet(Hashtable selectionSet)
	{
		Collection col = selectionSet.values();
		
		ArrayList<CadEntity> lsEntities = new ArrayList<CadEntity>(col);
		return lsEntities;
	}
	
	public void updateSelectionSet(CadBlockDef blkDef, Hashtable selectionSet, boolean bSelected)
	{
		Collection col = selectionSet.values();

		Iterator iter = col.iterator();
		while( iter.hasNext() ) {
			CadEntity oEnt = (CadEntity)iter.next();
			
			int objectId = oEnt.getObjectId();
			CadEntity oEntRes = blkDef.getEntity(objectId);
			oEntRes.setSelected(bSelected);
		}
	}
	
	public void addToSelectionSet(Hashtable selectionSet, CadEntity oEnt)
	{
		Integer objectId = oEnt.getObjectId();
		if( !selectionSet.containsKey(objectId) ) {
			oEnt.setSelected(true);
			selectionSet.put(objectId, oEnt);
		}
	}
	
	public void removeFromSelectionSet(Hashtable selectionSet, CadEntity oEnt)
	{
		Integer objectId = oEnt.getObjectId();
		if( selectionSet.containsKey(objectId) ) {
			oEnt.setSelected(false);
			selectionSet.remove(objectId);
		}
	}

	public void addToSelectionSet(Hashtable selectionSet, ArrayList<CadEntity> lsEntities)
	{
		for(CadEntity oEnt : lsEntities) {
			this.addToSelectionSet(selectionSet, oEnt);
		}
	}
	
	public void removeFromSelectionSet(Hashtable selectionSet, ArrayList<CadEntity> lsEntities)
	{
		for(CadEntity oEnt : lsEntities) {
			this.removeFromSelectionSet(selectionSet, oEnt);
		}
	}
	
	public Hashtable promptSelectioSet_basic(BasePanel basePanel, CadBlockDef blkDef, int[] arrObjType)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);

		Hashtable selectionSet = new Hashtable(); 
		int selectionMode = AppDefs.OPT_SELECT_ADD_VAL;
		
		EntSelVO oEntSel = PromptUtil.selectEntSel(this, arrObjType, this.getR().getString(R.CMD_PRT_SELECT_OBJECT) );
		if(oEntSel != null) {
			CadEntity oEnt = oEntSel.getEnt1();
			if(oEnt != null) {
				addToSelectionSet(selectionSet, oEnt);
			}
			else {
				GeomPoint2d pt2dI = oEntSel.getPtIns2d();
				if(pt2dI != null) {
					GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_CORNER) );
					if(pt2dF != null) {
						GeomPoint2d ptMin2d = GeomPoint2d.lowerLeftCornerFrom(pt2dI, pt2dF);
						GeomPoint2d ptMax2d = GeomPoint2d.upperRightCornerFrom(pt2dI, pt2dF);
						
						ArrayList<CadEntity> lsEntities = PromptUtil.selectWindow( arrObjType, ptMin2d, ptMax2d, true);
						addToSelectionSet(selectionSet, lsEntities); 					
					}
				}
			}

			int sz = selectionSet.size();
			
			String strMessage = String.format(this.getR().getString(R.CMD_PRT_NUMBER_OF_SELECTED_OBJECTS), nf0.format(sz));
			PromptUtil.prompt(strMessage);			
			
			updateSelectionSet(blkDef, selectionSet, true);
			
			this.refreshAll();
		}			
		return selectionSet;
	}
	
	public Hashtable promptSelectioSet_complete(BasePanel basePanel, CadBlockDef blkDef, int[] arrObjType)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);

		Hashtable selectionSet = new Hashtable(); 
		int selectionMode = AppDefs.OPT_SELECT_ADD_VAL;
		
		ArrayList<PromptOptionVO> lsPromptOptions = this.getPromptOptionSelectionMode();

		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString(R.CMD_PRT_CHOICE_SELECTION_TYPE), false);
		while(oKeyword != null) {
			
			// SELECT_ADD
			//
			if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_ADD_VAL) 
			{
				selectionMode = AppDefs.OPT_SELECT_ADD_VAL;
			}
			
			// SELECT_REMOVE
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_REMOVE_VAL) {
				selectionMode = AppDefs.OPT_SELECT_REMOVE_VAL;
			}
			
			// SELECT_OBJECT
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_OBJECT_VAL) 
			{
				CadEntity oEnt = PromptUtil.selectObject(this, arrObjType, this.getR().getString(R.CMD_PRT_SELECT_OBJECT) );
				if(oEnt != null) {
					if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
						addToSelectionSet(selectionSet, oEnt); 					
					}
					else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
						removeFromSelectionSet(selectionSet, oEnt); 					
					}
				}
			}
			
			// SELECT_FIRST
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_FIRST_VAL) 
			{
				CadEntity oEnt = PromptUtil.selectFirst( arrObjType, true );
				if(oEnt != null) {
					if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
						addToSelectionSet(selectionSet, oEnt); 					
					}
					else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
						removeFromSelectionSet(selectionSet, oEnt); 					
					}
				}
			}
			
			// SELECT_LAST
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_LAST_VAL) 
			{
				CadEntity oEnt = PromptUtil.selectLast( arrObjType, true );
				if(oEnt != null) {
					if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
						addToSelectionSet(selectionSet, oEnt); 					
					}
					else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
						removeFromSelectionSet(selectionSet, oEnt); 					
					}
				}
			}
			
			// SELECT_WINDOW
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_WINDOW_VAL) 
			{
				GeomPoint2d pt2dI = PromptUtil.getFirstCorner2d(this, null, this.getR().getString(R.CMD_PRT_FIRST_CORNER) );
				if(pt2dI != null) {
	
					GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_CORNER) );
					if(pt2dF != null) {
				
						GeomPoint2d ptMin2d = GeomPoint2d.lowerLeftCornerFrom(pt2dI, pt2dF);
						GeomPoint2d ptMax2d = GeomPoint2d.upperRightCornerFrom(pt2dI, pt2dF);
	
						ArrayList<CadEntity> lsEntities = PromptUtil.selectWindow( arrObjType, ptMin2d, ptMax2d, true);
						if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
							addToSelectionSet(selectionSet, lsEntities); 					
						}
						else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
							removeFromSelectionSet(selectionSet, lsEntities); 					
						}
					}
				}
			}
			
			// SELECT_CROSSING
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_CROSSING_VAL) 
			{
				GeomPoint2d pt2dI = PromptUtil.getFirstCorner2d(this, null, this.getR().getString(R.CMD_PRT_FIRST_CORNER) );
				if(pt2dI != null) {
	
					GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_CORNER) );
					if(pt2dF != null) {
					
						GeomPoint2d ptMin2d = GeomPoint2d.lowerLeftCornerFrom(pt2dI, pt2dF);
						GeomPoint2d ptMax2d = GeomPoint2d.upperRightCornerFrom(pt2dI, pt2dF);
						
						ArrayList<CadEntity> lsEntities = PromptUtil.selectCrossing( arrObjType, ptMin2d, ptMax2d, true);
						if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
							addToSelectionSet(selectionSet, lsEntities); 					
						}
						else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
							removeFromSelectionSet(selectionSet, lsEntities); 					
						}
					}
				}
			}
			
			// SELECT_FENCE
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_FENCE_VAL) 
			{
				GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString(R.CMD_PRT_FIRST_CORNER) );
				if(pt2dI != null) {
	
					GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(this, pt2dI, this.getR().getString(R.CMD_PRT_SECOND_CORNER) );
					if(pt2dF != null) {
					
						ArrayList<CadEntity> lsEntities = PromptUtil.selectFence( arrObjType, pt2dI, pt2dF, true);
						if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
							addToSelectionSet(selectionSet, lsEntities); 					
						}
						else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
							removeFromSelectionSet(selectionSet, lsEntities); 					
						}
					}
				}
			}
			
			// SELECT_PREVIOUS
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_PREVIOUS_VAL) 
			{
				ArrayList<CadEntity> lsEntities = PromptUtil.selectPrevious( arrObjType, true );
				if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
					addToSelectionSet(selectionSet, lsEntities); 					
				}
				else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
					removeFromSelectionSet(selectionSet, lsEntities); 					
				}
			}
			
			// SELECT_ALL
			//
			else if(oKeyword.getOptionId() == AppDefs.OPT_SELECT_ALL_VAL) 
			{
				ArrayList<CadEntity> lsEntities = PromptUtil.selectAll( arrObjType, true );
				if(selectionMode == AppDefs.OPT_SELECT_ADD_VAL) {
					addToSelectionSet(selectionSet, lsEntities); 					
				}
				else if(selectionMode == AppDefs.OPT_SELECT_REMOVE_VAL) {
					removeFromSelectionSet(selectionSet, lsEntities); 					
				}
			}

			int sz = selectionSet.size();
			
			String strMessage = String.format(this.getR().getString(R.CMD_PRT_NUMBER_OF_SELECTED_OBJECTS), nf0.format(sz));
			PromptUtil.prompt(strMessage);			
			
			updateSelectionSet(blkDef, selectionSet, true);
			
			this.refreshAll();
			
			oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString(R.CMD_PRT_CHOICE_SELECTION_TYPE), false);
		}			
		return selectionSet;
	}
	
	@Override
	public boolean initCommand() {
		this.frm.showToolbarControl(
			AppDefs.TOOLBARCTRL_BASIC, 
			this.bShowToolbarControl );		
		return true;
	}

	@Override
	public int executeCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc)
	{
		// FORM_DATA
		//
		this.app = app;
		this.frm = frm;
		this.cad = cad;
		this.doc = doc;

		// MAIN_PANEL
		//
		this.panel = (MainPanel)this.frm.getPanel();
		
		// LOAD_RESOURCES
		//
		this.r = app.getResource();

		// CANCEL_ANY_ACTIVE_COMMAND
		//
		if( this.cmdName.equalsIgnoreCase(AppDefs.ACTION_FILE_STOP) ) {
			int nErr = this.stopAllThread();
			if(nErr == 0) {
				this.frm.showToolbarControl(
					AppDefs.TOOLBARCTRL_BASIC, 
					false );
				this.panel.stopAll(this.doc);
			}
			else {
				int n = numberOfAliveThreads();
				if(n == 0) {
					this.frm.showToolbarControl(
						AppDefs.TOOLBARCTRL_BASIC, 
						false );		
					panel.stopAll(this.doc);
				}
				else {
					PromptUtil.prompt( this.getR().getString( R.ERR_EXISTEM_COMANDOS_ATIVOS ) );
					return AppDefs.RSERR;
				}
			}
			CmdBase.isCmdActive = false;
		}
		
		this.createThread();
		return AppDefs.RSOK;
	}
	
	@Override
	public void finishCommand() {
		MainFrame frm = this.getFrm();
		if(frm != null) {
			MainPanel panel = (MainPanel)frm.getPanel();

			frm.showToolbarControl(AppDefs.TOOLBARCTRL_ALL, false);

			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			if(currBlockDef != null) {
				currBlockDef.clearAllSelected();
			}
			
			this.refreshAll();
		}
	}

	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args)
	{
		this.app = app;
		this.frm = frm;
		this.cad = cad;
		this.doc = doc;

		//TODO:
	}
	
	/* THREADS */
	
	public synchronized int numberOfThreads()
	{
		int sz = CmdBase.lsThreads.size();
		return sz;
	}
	
	public synchronized int numberOfAliveThreads()
	{
		int n = 0;
		
		int sz = CmdBase.lsThreads.size();
		for(int i = 0; i < sz; i++) {
			Thread currThread = CmdBase.lsThreads.get(i);
			if( currThread.isAlive() ) {
				n += 1;
			}
		}
		return n;
	}
	
	public synchronized Thread createThread()
	{
		this.thread = new Thread(this);
		if(this.thread != null) {
			CmdBase.lsThreads.add(this.thread);
			this.thread.start();
		}
		return this.thread;
	}

	public synchronized boolean stopThreadAt(int pos) 
	{
		int sz = CmdBase.lsThreads.size();
		if( (pos >= 0) && (pos < sz) ) {
			Thread currThread = CmdBase.lsThreads.get(pos);
			if( currThread.isAlive() ) {
				try {
					this.bRunning = false;
					currThread.join(AppDefs.PICKMODE_WAITTIMEOUTMILI);
					
					CmdBase.lsThreads.remove(pos);
					return true;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public int stopAllThread() 
	{
		int nErr = 0;
		
		int lastPos = CmdBase.lsThreads.size() - 1;
		while(lastPos >= 0) {
			boolean bResult = this.stopThreadAt(lastPos);
			if( !bResult )
				nErr += 1;
			lastPos = lastPos - 1;
		}
		return nErr;
	}
	
	/* RUNNABLE */
	
	@Override
	public void run() {
		if( CmdBase.isCmdActive ) {
			PromptUtil.prompt( this.r.getString(R.ERR_EXISTEM_COMANDOS_ATIVOS) );
			return;
		}
		CmdBase.isCmdActive = true;
		
		this.bRunning = true;

		boolean bResult = this.initCommand();
		if( bResult ) {
			this.doCommand();
			this.finishCommand();
		}

		this.bRunning = false;		

		CmdBase.isCmdActive = false;
	}

	/* ABSTRACT */
	
	@Override
	public abstract InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam);
	
	@Override
	public abstract void doCommand();
	
	/* Getters/Setters */

	public String getCmdName() {
		return cmdName;
	}

	public AppMain getApp() {
		return app;
	}

	public MainFrame getFrm() {
		return frm;
	}

	public MainPanel getPanel() {
		return panel;
	}

	public void setPanel(MainPanel panel) {
		this.panel = panel;
	}

	public AppCadMain getCad() {
		return cad;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public boolean isRunning() {
		return bRunning;
	}

	public R getR() {
		return r;
	}

	public void setR(R r) {
		this.r = r;
	}
		
}
