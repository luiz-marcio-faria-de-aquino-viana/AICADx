/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.frm.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.print.Printable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public abstract class CompView extends BasePanel implements ICompView, Printable, MouseWheelListener, MouseListener, MouseMotionListener, KeyResultListener, Runnable
{
//Private
	protected String name = null;
	
	protected ICadViewBase v = null;
	
	protected int viewType = AppDefs.NULL_INT;
	
	protected MainFrame parentFrame = null;
	
	protected CadDocumentDef doc = null;
	
	protected Hashtable previousSelectionSet = null;
	
	protected boolean forceRefresh = false;
	
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
	
	/* IS_OBJTYPEOF */

	public boolean isObjtypeOf(int[] arrSelectobjtype, int selectObjtype)
	{    	
		int sz = arrSelectobjtype.length;
		for(int i = 0; i < sz; i++) {
			int objtype = arrSelectobjtype[i];
			if(selectObjtype == objtype)
				return true;
		}
		return false;
	}	

	/* SMP */

	//DRAW_ENTITIES
	//
	@Override
	public void drawEntities(
		ICadViewBase v, 
		double dist, 
		GeomPoint2d ptBase2dMcs, 
		GeomPoint2d pt2dMcs, 
		boolean bDragMode, 
		Graphics g,
		boolean bDrawSelected, 
		boolean bDrawEntities,
		boolean bDrawOsnap, 
		boolean bDrawTooltip ) 
	{ 
    	NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
    	
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	String str = "ScaleFactor: " + nf3.format(sclFact);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());

    	CadBlockDef blkDef = this.doc.getCurrBlockDef();
    	if(blkDef != null) {
			if( AppDefs.SMP_USE_THREADS ) {
				this.drawEntitiesSMP(
					v, 
					blkDef, 
					dist, 
					ptBase2dMcs, 
					pt2dMcs, 
					sclFact, 
					bDragMode, 
					g,
					bDrawSelected, 
					bDrawEntities,
					bDrawOsnap, 
					bDrawTooltip ); 
			}
			else {
				this.drawEntitiesNoSMP(
					v, 
					blkDef, 
					dist, 
					ptBase2dMcs, 
					pt2dMcs, 
					sclFact, 
					bDragMode, 
					g,
					bDrawSelected, 
					bDrawEntities,
					bDrawOsnap, 
					bDrawTooltip ); 			
			}
    	}
	}
	
	public abstract void drawEntitiesNoSMP(
		ICadViewBase v, 
		CadBlockDef blkDef, 
		double dist, 
		GeomPoint2d ptBase2dMcs, 
		GeomPoint2d pt2dMcs, 
		double sclFact, 
		boolean bDragMode, 
		Graphics g,
		boolean bDrawSelected, 
		boolean bDrawEntities,
		boolean bDrawOsnap, 
		boolean bDrawTooltip ); 

	public abstract void drawEntitiesSMP(
		ICadViewBase v, 
		CadBlockDef blkDef, 
		double dist, 
		GeomPoint2d ptBase2dMcs, 
		GeomPoint2d pt2dMcs, 
		double sclFact, 
		boolean bDragMode, 
		Graphics g,
		boolean bDrawSelected, 
		boolean bDrawEntities,
		boolean bDrawOsnap, 
		boolean bDrawTooltip ); 
	
	public abstract void drawEntitiesWorkerSMP(
		ICadViewBase v, 
		CadBlockDef blkDef, 
		double dist, 
		GeomPoint2d ptBase2dMcs, 
		GeomPoint2d pt2dMcs, 
		double sclFact, 
		boolean bDragMode, 
		int startPos, 
		int endPos, 
		Graphics g, 
		boolean bDrawSelected, 
		boolean bDrawEntities,
		boolean bDrawOsnap, 
		boolean bDrawTooltip,
		CadEntity[] arrEntities ); 

	//PROCESS_OSNAP
	//
	@Override
    public GeomPoint3d processOsnap(ICadViewBase v, GeomPoint2d pt2dMcs)
    {
    	GeomPoint3d ptResult = null;
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
    	
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	String str = "ScaleFactor: " + nf3.format(sclFact);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());
   	
    	CadBlockDef blkDef = this.doc.getCurrBlockDef();
    	if(blkDef != null) {
			if( AppDefs.SMP_USE_THREADS ) {
				ptResult = processOsnapSMP(v, blkDef, pt2dMcs, sclFact); 
			}
			else {
				ptResult = processOsnapNoSMP(v, blkDef, pt2dMcs, sclFact); 
			}
    	}
		return ptResult;
    }
	
    public abstract GeomPoint3d processOsnapNoSMP(ICadViewBase v, CadBlockDef blkDef, GeomPoint2d pt2dMcs, double sclFact);
	
    public abstract GeomPoint3d processOsnapSMP(ICadViewBase v, CadBlockDef blkDef, GeomPoint2d pt2dMcs, double sclFact);
	
	public abstract GeomPoint3d processOsnapWorkerSMP(ICadViewBase v, CadBlockDef blkDef, GeomPoint2d pt2dMcs, double sclFact, int startPos, int endPos, CadEntity[] arrEntities); 

	/* SELECT */
    
	@Override
	public CadEntity selectEntity(GeomPoint2d pt2dMcs)
	{
		int[] arrObjType = { AppDefs.OBJTYPE_ANY };
		
    	CadBlockDef blkDef = this.doc.getCurrBlockDef();

    	CadEntity oEnt = this.selectEntity(blkDef, arrObjType, pt2dMcs);
    	return oEnt;
	}
    
	@Override
	public CadEntity selectEntity(int objtype, GeomPoint2d pt2dMcs)
	{
		int[] arrObjType = { objtype };
		
    	CadBlockDef blkDef = this.doc.getCurrBlockDef();

    	CadEntity oEnt = this.selectEntity(blkDef, arrObjType, pt2dMcs);
		return oEnt;
	}

	//
	
	@Override
	public CadEntity selectEntity(CadBlockDef blkDef, GeomPoint2d pt2dMcs)
	{
		int[] arrObjType = { AppDefs.OBJTYPE_ANY };
		
    	CadEntity oEnt = this.selectEntity(blkDef, arrObjType, pt2dMcs);
    	return oEnt;
	}
    
	@Override
	public CadEntity selectEntity(CadBlockDef blkDef, int objtype, GeomPoint2d pt2dMcs)
	{
		int[] arrObjType = { objtype };
		
    	CadEntity oEnt = this.selectEntity(blkDef, arrObjType, pt2dMcs);
		return oEnt;
	}

	//
	
	@Override
	public CadEntity selectEntity(int[] arrObjType, GeomPoint2d pt2dMcs)
	{
    	CadBlockDef blkDef = this.doc.getCurrBlockDef();

    	CadEntity oEnt = this.selectEntity(blkDef, arrObjType, pt2dMcs);
    	return oEnt;		
	}
	
	@Override
	public CadEntity selectEntity(CadBlockDef blkDef, int[] arrObjType, GeomPoint2d pt2dMcs)
	{
		if(pt2dMcs == null) return null;
		
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 
        	
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
				boolean bSelected = oEnt.select2d(v, pt2dMcs, sclFact, true);
				if( bSelected )
					return oEnt;
			}
        }
        return null;
	}

	/* SELECT OPTIONS */

	@Override
	public CadEntity selectFirst(int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		CadEntity oEnt = this.selectFirst(blkDef, arrObjType, bOnlyUserEntities);
		return oEnt;
	}

	@Override
	public CadEntity selectLast(int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		CadEntity oEnt = this.selectLast(blkDef, arrObjType, bOnlyUserEntities);
		return oEnt;
	}

	@Override
	public ArrayList<CadEntity> selectAll(int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectAll(blkDef, arrObjType, bOnlyUserEntities);
		return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectWindow(int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectWindow(blkDef, arrObjType, ptMin, ptMax, bOnlyUserEntities);
		return lsEntities;		
	}

	@Override
	public ArrayList<CadEntity> selectCrossing(int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectCrossing(blkDef, arrObjType, ptMin, ptMax, bOnlyUserEntities);
		return lsEntities;		
	}
	
	@Override
	public ArrayList<CadEntity> selectFence(int objtype, GeomPoint2d ptI, GeomPoint2d ptF, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectFence(blkDef, arrObjType, ptI, ptF, bOnlyUserEntities);
		return lsEntities;		
	}
	
	@Override
	public ArrayList<CadEntity> selectPrevious(int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectPrevious(blkDef, arrObjType, bOnlyUserEntities);
		return lsEntities;		
	}
	
	//
		
	@Override
	public CadEntity selectFirst(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadEntity oEnt = this.selectFirst(blkDef, arrObjType, bOnlyUserEntities);
		return oEnt;
	}
	
	@Override
	public CadEntity selectLast(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		CadEntity oEnt = this.selectLast(blkDef, arrObjType, bOnlyUserEntities);
		return oEnt;
	}
	
	@Override
	public ArrayList<CadEntity> selectAll(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		ArrayList<CadEntity> lsEntities = this.selectAll(blkDef, arrObjType, bOnlyUserEntities);
		return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectWindow(CadBlockDef blkDef, int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		ArrayList<CadEntity> lsEntities = this.selectWindow(blkDef, arrObjType, ptMin, ptMax, bOnlyUserEntities);
		return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectCrossing(CadBlockDef blkDef, int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		ArrayList<CadEntity> lsEntities = this.selectCrossing(blkDef, arrObjType, ptMin, ptMax, bOnlyUserEntities);
		return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectFence(CadBlockDef blkDef, int objtype, GeomPoint2d ptI, GeomPoint2d ptF, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		ArrayList<CadEntity> lsEntities = this.selectFence(blkDef, arrObjType, ptI, ptF, bOnlyUserEntities);
		return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectPrevious(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities)
	{
		int[] arrObjType = { objtype };
		
		ArrayList<CadEntity> lsEntities = this.selectPrevious(blkDef, arrObjType, bOnlyUserEntities);
		return lsEntities;
	}

	//
	
	@Override
	public CadEntity selectFirst(int[] arrObjType, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		CadEntity oEnt = this.selectFirst(blkDef, arrObjType, bOnlyUserEntities);
        return oEnt;
	}

	@Override
	public CadEntity selectLast(int[] arrObjType, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		CadEntity oEnt = this.selectLast(blkDef, arrObjType, bOnlyUserEntities);
        return oEnt;
	}

	@Override
	public ArrayList<CadEntity> selectAll(int[] arrObjType, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectAll(blkDef, arrObjType, bOnlyUserEntities);
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectWindow(int[] arrObjType, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectWindow(blkDef, arrObjType, ptMin, ptMax, bOnlyUserEntities);
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectCrossing(int[] arrObjType, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectCrossing(blkDef, arrObjType, ptMin, ptMax, bOnlyUserEntities);
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectFence(int[] arrObjType, GeomPoint2d ptI, GeomPoint2d ptF, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectFence(blkDef, arrObjType, ptI, ptF, bOnlyUserEntities);
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectPrevious(int[] arrObjType, boolean bOnlyUserEntities)
	{
		CadBlockDef blkDef = this.doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = this.selectPrevious(blkDef, arrObjType, bOnlyUserEntities);
        return lsEntities;
	}

	//
	
	@Override
	public CadEntity selectFirst(CadBlockDef blkDef, int[] arrObjType, boolean bOnlyUserEntities)
	{
		CadEntity oResult = null;
		int objectId = AppDefs.NULL_INT;
		
    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;

        	if( !oEnt.isEntityObject() ) continue;
        	if( !oEnt.isVisible() ) continue; 

        	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
        		
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
    			if(oResult == null) {
    				oResult = oEnt;
    				objectId = oResult.getObjectId();
    			}
    			else {
    				if(oEnt.getObjectId() < objectId) {
        				oResult = oEnt;
        				objectId = oResult.getObjectId();    					
    				}
    			}
        	}
        }
        return oResult;
	}
	
	@Override
	public CadEntity selectLast(CadBlockDef blkDef, int[] arrObjType, boolean bOnlyUserEntities)
	{
		CadEntity oResult = null;
		int objectId = AppDefs.NULL_INT;
		
    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;

        	if( !oEnt.isEntityObject() ) continue;
        	if( !oEnt.isVisible() ) continue; 

        	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
    		
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
    			if(oResult == null) {
    				oResult = oEnt;
    				objectId = oResult.getObjectId();
    			}
    			else {
    				if(oEnt.getObjectId() > objectId) {
        				oResult = oEnt;
        				objectId = oResult.getObjectId();    					
    				}
    			}
        	}
        }
        return oResult;
	}
	
	@Override
	public ArrayList<CadEntity> selectAll(CadBlockDef blkDef, int[] arrObjType, boolean bOnlyUserEntities)
	{
		ArrayList<CadEntity> lsEntities = new ArrayList<CadEntity>();
		
    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;

        	if( !oEnt.isEntityObject() ) continue;
        	if( !oEnt.isVisible() ) continue; 

        	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
    		
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
    			oEnt.setSelected(true);
    			lsEntities.add(oEnt);
        	}
        }
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectWindow(CadBlockDef blkDef, int[] arrObjType, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		ArrayList<CadEntity> lsEntities = new ArrayList<CadEntity>();
		
    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;

        	if( !oEnt.isEntityObject() ) continue;
        	if( !oEnt.isVisible() ) continue; 

        	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
    		
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
        		boolean bInside = oEnt.isInside(ptMin, ptMax);
        		if( bInside ) {
	    			oEnt.setSelected(true);
	    			lsEntities.add(oEnt);
        		}
        	}
        }
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectCrossing(CadBlockDef blkDef, int[] arrObjType, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities)
	{
		ArrayList<CadEntity> lsEntities = new ArrayList<CadEntity>();
		
    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;

        	if( !oEnt.isEntityObject() ) continue;
        	if( !oEnt.isVisible() ) continue; 

        	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
    		
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
        		boolean bCrossing = oEnt.isCrossing(ptMin, ptMax);
        		if( bCrossing ) {
	    			oEnt.setSelected(true);
	    			lsEntities.add(oEnt);
        		}
        	}
        }
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectFence(CadBlockDef blkDef, int[] arrObjType, GeomPoint2d ptI, GeomPoint2d ptF, boolean bOnlyUserEntities)
	{
		ArrayList<CadEntity> lsEntities = new ArrayList<CadEntity>();
		
    	int size = blkDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = blkDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;

        	if( !oEnt.isEntityObject() ) continue;
        	if( !oEnt.isVisible() ) continue; 

        	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
    		
        	if( oEnt.isObjtypeOf( arrObjType ) ) {
        		boolean bCrossing = oEnt.isCrossingLine(ptI, ptF);
        		if( bCrossing ) {
	    			oEnt.setSelected(true);
	    			lsEntities.add(oEnt);
        		}
        	}
        }
        return lsEntities;
	}

	@Override
	public ArrayList<CadEntity> selectPrevious(CadBlockDef blkDef, int[] arrObjType, boolean bOnlyUserEntities)
	{
		ArrayList<CadEntity> lsEntities = new ArrayList<CadEntity>();
		
		Hashtable prevSet = this.getPreviousSelectionSet();
		if(prevSet != null) {
			Collection col = prevSet.values();
			Iterator iter = col.iterator();
			while( iter.hasNext() ) {
				CadObject oEnt = (CadObject)iter.next();
	        	if( oEnt.isDeleted() ) continue;
	        	if( !oEnt.isEntityObject() ) continue;

	        	int objectId = oEnt.getObjectId();
				
	        	CadEntity oEntRes = blkDef.getEntity(objectId);
	        	if(oEntRes != null) {
	        		if( !oEntRes.isVisible() ) continue; 

	            	if( bOnlyUserEntities && ( oEnt.getObjType() < AppDefs.OBJTYPE_ENTITIES ) ) continue;
	        		
		        	if( oEntRes.isObjtypeOf( arrObjType ) ) {
		    			oEntRes.setSelected(true);
		    			lsEntities.add(oEntRes);
		        	}
		        }
		        return lsEntities;
			}
		}
        return lsEntities;
	}
	
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

	//SelectionSet
	//
	public Hashtable getPreviousSelectionSet()
	{
		return this.previousSelectionSet;
	}
	
	public void setPreviousSelectionSet(Hashtable currSelectionSet)
	{
		this.previousSelectionSet = currSelectionSet;
	}
					
    /* Getters/Setters */

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getViewType() {
		return this.viewType;
	}

	@Override
	public boolean isForceRefresh() {
		return this.forceRefresh;
	}

	@Override
	public void setForceRefresh(boolean forceRefresh) {
		this.forceRefresh = forceRefresh;
	}

	public MainFrame getParent() {
		return this.parentFrame;
	}

	public CadDocumentDef getDocument() {
		return this.doc;
	}

	public ICadViewBase getCadViewBase()
	{
		return this.v;
	}

}
