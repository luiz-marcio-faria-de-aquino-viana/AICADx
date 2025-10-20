/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompModel3DView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.TextEvent;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.CadView3d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cad.utils.RubberbandUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPiso;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public class CompModel3DView extends CompView
{
//Private
	protected ICadViewBase v = null;
	
	protected int viewType = AppDefs.NULL_INT;
	
	protected GeomPoint2d currMousePosScr = null;

	protected GeomPoint2d lastMousePosScr = null;
	
	protected long lastMousePosTimeMili = -1; 
    
	protected long lastMouseClickTimeMili = -1; 
	
	protected long lastMouseWheelTimeMili = -1;
    
	//PICKMODE_VARS
	//
	protected int pickmode = AppDefs.PICKMODE_NONE;
	//
	protected GeomPoint2d currPickpointMcs = null;
	protected double currPickpointZMcs = 0.0;
	//BasePoint (for Linear or Rectangle Rubberband)
	protected GeomPoint2d basePickpointMcs = null;
	//CenterPoint And StartPoint (for Angles, Arcs or Circles Rubberband)
	protected GeomPoint2d centerPickpointMcs = null;
	protected GeomPoint2d startPickpointMcs = null;
	protected GeomPoint2d endPickpointMcs = null;
	//Direction vector
	protected GeomVector2d vDirPickpointMcs = null;
	//Point list
	protected ArrayList<GeomPoint2d> lsPtsPickpointMcs = null;
	//Reference
	protected double refPickpointMcs = 0.0;
	
	//REALTIME_3DMOVE_MODE
	//
	protected boolean b3DMoveMode = false;
	//
	protected GeomPoint2d base3DMoveModePtScr = null;
	protected GeomPoint2d curr3DMoveModePtScr = null;
	
	//REALTIME_PAN_MODE
	//
	protected boolean bPanMode = false;
	//
	protected GeomPoint2d baseMousePosPanScr = null;
	protected GeomPoint2d currMousePosPanScr = null;
	
	//ZOOMMODE_VARS
	//
	protected int zoommode = AppDefs.ZOOMMODE_NONE;
	//
	protected GeomPoint2d currZoompointMcs = null;
	//BasePoint (for Linear or Rectangle Rubberband)
	protected GeomPoint2d baseZoompointMcs = null;
	
	//GRIDMODE_VARS
	//
	protected int gridmode = AppDefs.GRIDMODE_ON;
	//
	protected double gridmodeXSize = AppDefs.GRIDMODE_XSIZE;
	protected double gridmodeYSize = AppDefs.GRIDMODE_YSIZE;
	//
	protected GeomPoint2d gridmodeOriginMcs = AppDefs.GRIDMODE_ORIGIN;

	//SNAP_MODE
	//
	protected int snapmode = AppDefs.SNAPMODE_ON;
	//
	protected double snapmodeXSize = AppDefs.SNAPMODE_XSIZE;
	protected double snapmodeYSize = AppDefs.SNAPMODE_YSIZE;
	//
	protected GeomPoint2d snapmodeOriginMcs = AppDefs.SNAPMODE_ORIGIN;

	//OSNAP_MODE
	//
	protected int osnapmode = AppDefs.OSNAPMODE_ALL;
	
	//ORTHOMODE_VARS
	//
	protected int orthomode = AppDefs.ORTHOMODE_OFF;
	
	//SELECTMODE_VARS
	//
	protected int selectmode = AppDefs.SELECTMODE_NONE;
	protected int selectobjtype = AppDefs.OBJTYPE_ANY;
	//
	protected GeomPoint2d currSelectpointMcs = null;

	//DRAGMODE_VARS
	//
	protected int dragmode = AppDefs.DRAGMODE_NONE;
	
	//BLIPS_VARS
	//
	protected ArrayList<GeomPoint2d> lsBlipsMcs = null;
	
	//PLAN_AREA
	//
	protected GeomPoint2d ptPlanAreaMin = null;
	protected GeomPoint2d ptPlanAreaMax = null;
	
	/* Methodes */
	
	//CLEARSCREEN
	//
    protected void clearScreen(ICadViewBase v, Graphics g) 
    {
    	Color oldcol = g.getColor();		
		
		Color c = AppDefs.BACKGROUNDCOLOR;
		g.setColor(c);
    	
    	int w = this.getWidth();
    	int h = this.getHeight();

    	g.fillRect(0, 0, w, h);

		g.setColor(oldcol);
	}
    
    //BASEPLAN
    //
    protected void drawBasePlan(ICadViewBase v, PrepareDrawUtil prep)
    {
		Color c = AppDefs.BASEPLANCOLOR;
    	
		GeomPlan3d planMcs = v.getPlanMcs3d();
		
		GeomVector3d axisZ = new GeomVector3d( planMcs.getAxisZ() );

		//LIMITS (MCS)
		GeomPoint3d ptLLC = planMcs.getPtLowerLeftCorner();
		GeomPoint3d ptLRC = planMcs.getPtLowerRightCorner();
		GeomPoint3d ptURC = planMcs.getPtUpperRightCorner();
		GeomPoint3d ptULC = planMcs.getPtUpperLeftCorner();

		prep.addFace(v, null, c, ptLLC, ptLRC, ptURC, ptULC, axisZ);
	}
    
    // DRAWENTITIESBYxxx
    //
    protected void drawEntities(ICadViewBase v, int objType, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, PrepareDrawUtil prep)
    {
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();

    	GeomObserver3d obs = v.getObsMcs();

    	GeomPoint3d ptCentroid = new GeomPoint3d(obs.getPtObserver());

    	ArrayList<CadEntity> lsEntities = oBlockDef.sortAllEntities(ptCentroid, objType);
    	int szLsEntities = lsEntities.size();
    	
        for(int i = 0; i < szLsEntities; i++) {
        	CadEntity oEnt = lsEntities.get(i);
        	if( oEnt.isDeleted() ) continue;
        		
			oEnt.redraw3d(v, 0.0, null, pt2dMcs, sclFact, false, false, prep);
        }
    }
        
    // DRAWENTITIES
    //
    protected void drawEntities(ICadViewBase v, ArrayList<Integer> lsObjTypeExcluded, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, PrepareDrawUtil prep) 
    {
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();
    	
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();

    	GeomObserver3d obs = v.getObsMcs();

    	GeomPoint3d ptCentroid = new GeomPoint3d(obs.getPtObserver());

    	ArrayList<CadEntity> lsEntities = oBlockDef.sortAllEntities(ptCentroid);
    	int szLsEntities = lsEntities.size();
    	
        for(int i = 0; i < szLsEntities; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        	
        	int objType = oEnt.getObjType();
        	if( lsObjTypeExcluded.contains(objType) ) continue;
        	
			oEnt.redraw3d(v, 0.0, null, pt2dMcs, sclFact, false, false, prep);
        }
    }
    
    // DRAWBLIP
	//
	protected void drawBlip(ICadViewBase v, Graphics g)
	{
		Color oldcol = g.getColor();		
		
		Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
		g.setColor(c);		

		for(GeomPoint2d ptMcs : this.lsBlipsMcs)
		{
			GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);				
			GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);
			
			int xVideo = (int)ptVideo.getX() - 1;
			int yVideo = (int)ptVideo.getY() - 1;
			
			g.drawLine(xVideo - 2, yVideo, xVideo + 2, yVideo);
			g.drawLine(xVideo, yVideo - 2, xVideo, yVideo + 2);
		}

		g.setColor(oldcol);		
	}

	/* DRAWRUBBERBAND */

	//DRAWRUBBERBAND_PICKMODE
	protected void drawRubberband_PickMode(ICadViewBase v, Graphics g)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);

		Color oldcol = GeomUtil.setColor(g, AppDefs.RUBBERBANDCOLOR_PICKMODE);		

		int oldtextheight = GeomUtil.setTextHeight(g, AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		//DISPLAY: POINT_COORDS
		//
		if(this.currMousePosScr == null) return;
		
		GeomPoint2d ptMcsF = v.fromScrToMcs(this.currMousePosScr);			
		GeomPoint2d ptVideoF = v.fromScrToVideo(this.currMousePosScr);
		
		if(this.pickmode != AppDefs.PICKMODE_NONE)
		{
			RubberbandUtil.drawRubberband_PickPoint(v, g, ptMcsF, this.currMousePosScr);
		}
		
		//DISPLAY: PICKMODExxx
		//
		if(this.pickmode == AppDefs.PICKMODE_PICKSECONDPOINT)
		{
			if(this.basePickpointMcs == null) return;

			if(this.lsPtsPickpointMcs == null) {
				RubberbandUtil.drawRubberband_PickSecondPoint(v, g, this.basePickpointMcs, ptMcsF);				
			}
			else {
				RubberbandUtil.drawRubberband_PickSecondPoint(v, g, this.basePickpointMcs, this.lsPtsPickpointMcs, ptMcsF);				
			}
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKSECONDCORNER)
		{
			if(this.basePickpointMcs == null) return;
			
			RubberbandUtil.drawRubberband_PickSecondCorner(v, g, this.basePickpointMcs, ptMcsF);
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKRADIUS)
		{
			if(this.centerPickpointMcs == null) return;
		
			RubberbandUtil.drawRubberband_PickRadius(v, g, this.centerPickpointMcs, ptMcsF);
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKCENTERPOINT)
		{
			if( (this.startPickpointMcs == null) && (this.endPickpointMcs == null) ) return;

			//RADIUS (CENTER-START)
			//
			if( (this.startPickpointMcs != null) && (this.endPickpointMcs == null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStart(v, g, ptMcsF, this.startPickpointMcs);
			}			
			//RADIUS (CENTER-END)
			//
			if( (this.startPickpointMcs == null) && (this.endPickpointMcs != null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStart(v, g, ptMcsF, this.endPickpointMcs);
			}			
			//ARC (CENTER-START-END)
			//
			else if( (this.startPickpointMcs != null) && (this.endPickpointMcs != null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStartEnd(v, g, ptMcsF, this.startPickpointMcs, this.endPickpointMcs);
			}
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKARCSTARTPOINT)
		{
			if( (this.centerPickpointMcs == null) && (this.endPickpointMcs == null) ) return;

			//RADIUS (CENTER-START or CENTER-END)
			//
			if( (this.centerPickpointMcs != null) && (this.endPickpointMcs == null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStart(v, g, this.centerPickpointMcs, ptMcsF);
			}			
			//DIAMETER (START-END)
			//
			else if( (this.centerPickpointMcs == null) && (this.endPickpointMcs != null) )
			{
				RubberbandUtil.drawRubberband_Arc_StartEnd(v, g, ptMcsF, this.endPickpointMcs);
			}
			//DIAMETER (CENTER-START-END)
			//
			else if( (this.centerPickpointMcs != null) && (this.endPickpointMcs != null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStartEnd(v, g, this.centerPickpointMcs, ptMcsF, this.endPickpointMcs);
			}
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKARCENDPOINT)
		{
			if( (this.centerPickpointMcs == null) && (this.startPickpointMcs == null) ) return;

			//RADIUS (CENTER-START or CENTER-END)
			//
			if( (this.centerPickpointMcs != null) && (this.startPickpointMcs == null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStart(v, g, this.centerPickpointMcs, ptMcsF);
			}			
			//DIAMETER (START-END)
			//
			else if( (this.centerPickpointMcs == null) && (this.startPickpointMcs != null) )
			{
				RubberbandUtil.drawRubberband_Arc_StartEnd(v, g, this.startPickpointMcs, ptMcsF);
			}
			//DIAMETER (CENTER-START-END)
			//
			else if( (this.centerPickpointMcs != null) && (this.startPickpointMcs != null) )
			{
				RubberbandUtil.drawRubberband_Arc_CenterStartEnd(v, g, this.centerPickpointMcs, this.startPickpointMcs, ptMcsF);
			}
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKANGLE)
		{
			if( (this.centerPickpointMcs == null) && (this.startPickpointMcs == null) ) return;

			RubberbandUtil.drawRubberband_Angle_CenterStartEnd(v, g, this.centerPickpointMcs, this.startPickpointMcs, ptMcsF);
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKPOINTATDIR) 
		{
			if(this.vDirPickpointMcs == null) return;
			
			RubberbandUtil.drawRubberband_PickPointAtDir(v, g, this.basePickpointMcs, this.vDirPickpointMcs, ptMcsF);
		}

		GeomUtil.setTextHeight(g, oldtextheight);

		GeomUtil.setColor(g, oldcol);		
	}
	
	//DRAWRUBBERBAND_ZOOMMODE
	protected void drawRubberband_ZoomMode(ICadViewBase v, Graphics g)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);

		Color oldcol = GeomUtil.setColor(g, AppDefs.RUBBERBANDCOLOR_ZOOMMODE);		

		int oldtextheight = GeomUtil.setTextHeight(g, AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		//DISPLAY: POINT_COORDS
		//
		if(this.currMousePosScr == null) return;
		
		GeomPoint2d ptMcsF = v.fromScrToMcs(this.currMousePosScr);			
		GeomPoint2d ptVideoF = v.fromScrToVideo(this.currMousePosScr);
		
		if(this.zoommode != AppDefs.ZOOMMODE_NONE)
		{
			RubberbandUtil.drawRubberband_ZoomPoint(v, g, ptMcsF, this.currMousePosScr);
		}
		
		//DISPLAY: ZOOMMODExxx
		//
		if(this.zoommode == AppDefs.ZOOMMODE_PAN)
		{
			if(this.baseZoompointMcs == null) return;

			RubberbandUtil.drawRubberband_ZoomSecondPoint(v, g, this.baseZoompointMcs, ptMcsF);
		}
		else if(this.zoommode == AppDefs.ZOOMMODE_ZOOMSECONDCORNER)
		{
			if(this.baseZoompointMcs == null) return;
			
			RubberbandUtil.drawRubberband_ZoomSecondCorner(v, g, this.baseZoompointMcs, ptMcsF);
		}

		GeomUtil.setTextHeight(g, oldtextheight);

		GeomUtil.setColor(g, oldcol);		
	}

	//DRAWRUBBERBAND
	protected void drawRubberband(ICadViewBase v, Graphics g)
	{
		if(zoommode != AppDefs.ZOOMMODE_NONE) {
			this.drawRubberband_ZoomMode(v, g);
		}
		else if(pickmode != AppDefs.PICKMODE_NONE) {
			this.drawRubberband_PickMode(v, g);
		}
	}
	
	// DRAWGRID
	//
	protected void drawGrid(ICadViewBase v, Graphics g)
	{
		if(this.gridmode == AppDefs.GRIDMODE_OFF) return;

		Color oldcol = g.getColor();		
		
		Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
		g.setColor(c);		
		
		GeomPlan2d planMcs0 = v.getPlanMcs02d();

		GeomPoint2d ptMin = planMcs0.getPtLowerLeftCorner();
				
		double w = planMcs0.getWidth();
		double h = planMcs0.getHeight();

		int nW = (int)Math.floor(w / AppDefs.GRIDMODE_XSIZE) + 1;
		int nH = (int)Math.floor(h / AppDefs.GRIDMODE_YSIZE) + 1;

		//DRAW_GRID
		//
		double xMin = ptMin.getX();
		double yMin = ptMin.getY();		

		for(int i = 0; i <= nW; i++) {
			double y = yMin + (i * AppDefs.GRIDMODE_YSIZE);
			
			for(int j = 0; j <= nH; j++) {
				double x = xMin + (j * AppDefs.GRIDMODE_XSIZE);
				
				GeomPoint2d gridMcs = new GeomPoint2d(x, y);

				GeomPoint2d gridScr = v.fromMcsToScr(gridMcs);				
				GeomPoint2d gridVideo = v.fromScrToVideo(gridScr);
				
				int xVideo = (int)gridVideo.getX() - 1;
				int yVideo = (int)gridVideo.getY() - 1;
				
				g.fillRect(xVideo, yVideo, 3, 3);
			}
		}

		g.setColor(oldcol);		
	}
	
	// DRAWCOORDS
	//
	protected void drawCoords(ICadViewBase v, Graphics g)
	{
		GeomPoint2d ptScr2d = this.currMousePosScr;
		if(ptScr2d == null) 
			ptScr2d = new GeomPoint2d(0.0, 0.0);

		Color oldcol = g.getColor();		
		
		Color c = AppDefs.DISPCOORDS_COLOR;
		g.setColor(c);		

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		GeomPoint2d ptMcs = v.fromScrToMcs(ptScr2d); 
		double xMcs = ptMcs.getX();
		double yMcs = ptMcs.getY();
		
		String strCoords = String.format(
			"%s x %s", 
			nf3.format(xMcs),
			nf3.format(yMcs));

        Font f = new Font(AppDefs.DISPCORDS_FONTFAMILY, Font.BOLD, AppDefs.DISPCOORDS_HEIGHT);

        FontMetrics metrics = g.getFontMetrics();
        Rectangle2D rect = metrics.getStringBounds(strCoords, g);
        
        int xVideo = (int)(this.getWidth() / 2.0);
        int yVideo = AppDefs.DISPCOORDS_VERTICALPOS;
        
        Font oldFont = g.getFont();
        g.setFont(f);
        
    	g.drawString(strCoords, xVideo, yVideo);

        g.setFont(oldFont);
				
		g.setColor(oldcol);		
	}
	
	// DRAWCURSOR
	//
	protected void drawCursor(ICadViewBase v, Graphics g)
	{
		if(this.currMousePosScr == null) return;

		Color oldcol = g.getColor();		
		
		if(this.selectmode == AppDefs.SELECTMODE_SELECTOBJECT)
		{
			Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
			g.setColor(c);
	
			GeomPoint2d mousePorVideo = v.fromVideoToScr(this.currMousePosScr);
			
			int xCursorVideo = (int)mousePorVideo.getX();
			int yCursorVideo = (int)mousePorVideo.getY();
	
			int boxSz = AppDefs.SELECTBOX_SIZE;
			int hBoxSz = (int)(boxSz / 2.0);
			
			int xMinVideo = xCursorVideo - hBoxSz;
			int yMinVideo = yCursorVideo - hBoxSz;
		
			g.drawRect(xMinVideo, yMinVideo, boxSz, boxSz);
		}
		else
		{
			Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
			g.setColor(c);		
	
			GeomPoint2d mousePorVideo = v.fromVideoToScr(this.currMousePosScr);
			
			int xCursorVideo = (int)mousePorVideo.getX();
			int yCursorVideo = (int)mousePorVideo.getY();
	
			int w = this.getWidth();
			int h = this.getHeight();
		
			g.drawLine(0, yCursorVideo, w, yCursorVideo);
			g.drawLine(xCursorVideo, 0, xCursorVideo, h);
		}
		
		g.setColor(oldcol);		
	}
	
	/* SELECT */

	@Override
	public CadEntity selectEntity(GeomPoint2d pt2dMcs)
	{
		if(pt2dMcs == null) return null;
		
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	
			if( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				(this.selectobjtype == oEnt.getObjType()) ) 
			{
				boolean bSelected = oEnt.select2d(this.v, pt2dMcs, sclFact, true);
				if( bSelected )
					return oEnt;
			}
        }
        return null;
	}
		
//Public

    public CompModel3DView(String name, int viewType, MainFrame frm, CadDocumentDef doc) {
		super(frm);
		
		this.init(name, viewType, frm, doc);
	}
	
    /* Methodes */
    
	@Override
    public void init(String name, int viewType, MainFrame frm, CadDocumentDef doc) {
		super.init(name, viewType, frm, doc);
		
    	this.lsBlipsMcs = new ArrayList<GeomPoint2d>();
		//this.startThread();
    }
    
	@Override
	public void initCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist)
	{
		double xObsMcs = ptCentroid.getX() - obsDist;
		double yObsMcs = ptCentroid.getY() - obsDist;		
		double zObsMcs = ptCentroid.getZ() + obsDist;
		
		GeomPoint3d ptObsMcs = new GeomPoint3d(xObsMcs, yObsMcs, zObsMcs);

		GeomVector3d vDirMcs = new GeomVector3d(ptObsMcs, ptCentroid);
		GeomVector3d uDirMcs = vDirMcs.otherUnit();

		this.v = new CadView3d(
			ptCentroid,
			uDirMcs,
			modelDist,
			modelDist,
			modelDist,
			wScr,
			hScr,
			ptObsMcs,
			uDirMcs,
			//
			//Camera References
			//
			ptCentroid,
			modelDist,
			obsDist);		
	}
    
	@Override
	public void resetCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist)
	{
		double xObsMcs = ptCentroid.getX() - obsDist;
		double yObsMcs = ptCentroid.getY() - obsDist;		
		double zObsMcs = ptCentroid.getZ() + obsDist;
		
		GeomPoint3d ptObsMcs = new GeomPoint3d(xObsMcs, yObsMcs, zObsMcs);

		GeomVector3d vDirMcs = new GeomVector3d(ptObsMcs, ptCentroid);
		GeomVector3d uDirMcs = vDirMcs.otherUnit();

		this.v = new CadView3d(
			ptCentroid,
			uDirMcs,
			modelDist,
			modelDist,
			modelDist,
			wScr,
			hScr,
			ptObsMcs,
			uDirMcs,
			//
			//Camera References
			//
			ptCentroid,
			modelDist,
			obsDist);
	}

//	public void initCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist)
//	{
//		//MCS
//		double wMcs = AppDefs.MCSPLAN_XSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
//		double hMcs = AppDefs.MCSPLAN_YSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
//		double zHMcs = AppDefs.MCSPLAN_ZHEIGHT;
//		
//		double xCenterMcs = wMcs / 2.0;
//		double yCenterMcs = hMcs / 2.0;
//		
//		GeomPoint3d ptCenterMcs = new GeomPoint3d(xCenterMcs, yCenterMcs, 0.0); 
//		GeomPoint3d ptXDirMcs = new GeomPoint3d(xCenterMcs + 1.0, yCenterMcs, 0.0);
//
//		GeomVector3d xDirMcs = new GeomVector3d(ptCenterMcs, ptXDirMcs);
//		GeomVector3d uDirMcs = xDirMcs.otherUnit();
//
//		//OBSERVER
//		double lDist = Math.sqrt( (AppDefs.PROJPLAN_OBSERVER_DISTANCE * AppDefs.PROJPLAN_OBSERVER_DISTANCE) / 2.0 );
//		
//		GeomPoint3d ptObsMcs = new GeomPoint3d(- lDist, - lDist, AppDefs.PROJPLAN_OBSERVER_HEIGHT);
//		GeomPoint3d ptDirMcs = new GeomPoint3d(0.0, 0.0, AppDefs.PROJPLAN_OBSERVER_HEIGHT);
//		
//		GeomVector3d vObsDirMcs = new GeomVector3d(ptObsMcs, ptDirMcs);
//		GeomVector3d uObsDirMcs = vObsDirMcs.otherUnit();
//		
//		this.v = new CadView3d(
//			ptCenterMcs,
//			uDirMcs,
//			wMcs,
//			hMcs,
//			zHMcs,
//			wScr,
//			hScr,
//			ptObsMcs,
//			uObsDirMcs,
//			//
//			//Camera References
//			//
//			ptCentroid,
//			modelDist,
//			obsDist);
//	}
    
//	@Override
//	public void resetCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist)
//	{
//		//MCS
//		GeomPlan3d planMcs = this.v.getPlanMcs3d();
//		
//		double wMcs = planMcs.getWidth();
//		double hMcs = planMcs.getHeight();
//		double zHMcs = planMcs.getZHeight();;
//		
//		GeomPoint3d ptCenterMcs = planMcs.getPtCenter();
//		GeomVector3d xDirMcs = planMcs.getXDir();
//
//		//OBSERVER
//		GeomObserver3d obsMcs = this.v.getObsMcs();
//
//		GeomPoint3d ptObsMcs = obsMcs.getPtObserver();
//		GeomVector3d vObsDirMcs = obsMcs.getUnitDir();
//		
//		this.v = new CadView3d(
//			ptCenterMcs,
//			xDirMcs,
//			wMcs,
//			hMcs,
//			zHMcs,
//			wScr,
//			hScr,
//			ptObsMcs,
//			vObsDirMcs,
//			//
//			//Camera References
//			//
//			ptCentroid,
//			modelDist,
//			obsDist);
//	}

    /* RESET_PICKMODE_VARS */
    
	@Override
    public void resetPickModeVars()
    {
    	this.pickmode = AppDefs.PICKMODE_NONE;
    	//
    	this.currPickpointMcs = null;
    	this.currPickpointZMcs = 0.0;
    	//BasePoint (for Linear or Rectangle Rubberband)
    	this.basePickpointMcs = null;
    	//CenterPoint And StartPoint (for Angles, Arcs or Circles Rubberband)
    	this.centerPickpointMcs = null;
    	this.startPickpointMcs = null;
    	this.endPickpointMcs = null;
    	//Direction vector
    	this.vDirPickpointMcs = null;
    	//Point list
    	this.lsPtsPickpointMcs = null;
    }
    
    /* RESET_SELECTMODE_VARS */
    
	@Override
    public void resetSelectModeVars()
    {
    	this.selectmode = AppDefs.SELECTMODE_NONE;
    	this.selectobjtype = AppDefs.OBJTYPE_BIMENTITIES;
    	//
    	this.currSelectpointMcs = null;
    }

    /* RESET_ZOOMMODE_VARS */
    
	@Override
    public void resetZoomModeVars()
    {
    	this.zoommode = AppDefs.ZOOMMODE_NONE;
    	//
    	this.currZoompointMcs = null;
    	//BasePoint (for Linear or Rectangle Rubberband)
    	this.baseZoompointMcs = null;
    }

    /* BLIPS */
    
	@Override
    public synchronized void clearBlips()
    {
    	this.lsBlipsMcs.clear();
    }

    /* RESET ALL */
    
	@Override
    public void resetAll()
    {
    	this.resetPickModeVars();
    	this.resetSelectModeVars();
    	this.resetZoomModeVars();
    	//
    	this.clearBlips();
    }

    /* REPAINT ALL */
    
	@Override
	public void repaintAll()
	{
		this.repaint();
	}
	
	/* PRINT VIEW */
	
	@Override
	public void printView()
	{
		PrinterJob printerJob = PrinterJob.getPrinterJob();

		if( printerJob.printDialog() ) {
			try {
				PageFormat pageFormat = printerJob.defaultPage();
				//printerJob.pageDialog(pageFormat);
				printerJob.setPrintable(this, pageFormat);
				printerJob.print();
			}
			catch(Exception e) { 
				PromptUtil.prompt("ERR: Can't print the document.");				
			}
		}
	}
    
    /* PAINT EVENTS */

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);

    	if(this.v == null) {
			double wScr = this.getWidth();
			double hScr = this.getHeight();

			if( (wScr < AppDefs.MATHPREC_MIN) && (hScr < AppDefs.MATHPREC_MIN) )
				return;
			
	    	GeomDimension3d oDim3d = this.getEnvelop3d();	    			
			
	    	GeomPoint3d ptCentroid3d = new GeomPoint3d( oDim3d.getPtCentroid() );
	    	GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
	    	GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
			
	    	double modelDist = ptMin3d.distTo(ptMax3d);
	    	double obsDist = modelDist * AppDefs.MODEL_DIST_16X;
	    	
			this.initCadView(wScr, hScr, ptCentroid3d, modelDist, obsDist);
		}

		boolean bDragMode = (this.dragmode != AppDefs.DRAGMODE_NONE);
    	
		GeomPoint2d pt2dScr = new GeomPoint2d(Double.MAX_VALUE, Double.MAX_VALUE);
		if(this.currMousePosScr != null)
			pt2dScr = new GeomPoint2d(this.currMousePosScr);		
		GeomPoint2d pt2dMcs = v.fromScrToMcs(pt2dScr);

		GeomPoint2d ptBase2dMcs = null;    	
		if(this.pickmode != AppDefs.PICKMODE_NONE) {
			bDragMode = true;

			if(this.basePickpointMcs != null)
				ptBase2dMcs = new GeomPoint2d(this.basePickpointMcs);
		}
		
		ArrayList<Integer> lsObjTypeExcluded = new ArrayList<Integer>();
		lsObjTypeExcluded.add( AppDefs.OBJTYPE_BIMPISO);
		lsObjTypeExcluded.add( AppDefs.OBJTYPE_BIMPAREDE);
		lsObjTypeExcluded.add( AppDefs.OBJTYPE_BIMPIPE);
		
		this.clearScreen(this.v, g);

		//PrepareDrawUtil prep = new PrepareDrawUtil();
		//this.drawBasePlan(this.v, prep);
		
		PrepareDrawUtil prep = new PrepareDrawUtil();
    	this.drawEntities(this.v, AppDefs.OBJTYPE_BIMPISO, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(this.v, g);

		prep = new PrepareDrawUtil();
    	this.drawEntities(this.v, AppDefs.OBJTYPE_BIMPAREDE, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(this.v, g);

		prep = new PrepareDrawUtil();
		this.drawEntities(this.v, AppDefs.OBJTYPE_BIMPIPE, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(this.v, g);

    	prep = new PrepareDrawUtil();
		this.drawEntities(this.v, lsObjTypeExcluded, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(this.v, g);
		
		//this.drawBlip(this.v, g);
		//this.drawRubberband(this.v, g);
		//this.drawGrid(this.v, g);
		//this.drawCoords(this.v, g);
		//this.drawCursor(this.v, g);
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) 
		throws PrinterException 
	{
		if(pageIndex > 0) return Printable.NO_SUCH_PAGE;

		if(v == null) 
			return Printable.NO_SUCH_PAGE;
		
		boolean bDragMode = false;
		GeomPoint2d pt2dMcs = new GeomPoint2d(Double.MAX_VALUE, Double.MAX_VALUE);
		GeomPoint2d ptBase2dMcs = null;    	
		
		ArrayList<Integer> lsObjTypeExcluded = new ArrayList<Integer>();
		lsObjTypeExcluded.add( AppDefs.OBJTYPE_BIMPIPE);
		lsObjTypeExcluded.add( AppDefs.OBJTYPE_BIMPAREDE);
		lsObjTypeExcluded.add( AppDefs.OBJTYPE_BIMPIPE);
		
		this.clearScreen(v, g);    	

		//PrepareDrawUtil prep = new PrepareDrawUtil();
		//this.drawBasePlan(v, prep);
		
		PrepareDrawUtil prep = new PrepareDrawUtil();
    	this.drawEntities(v, AppDefs.OBJTYPE_BIMPISO, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(v, g);

		prep = new PrepareDrawUtil();
    	this.drawEntities(v, AppDefs.OBJTYPE_BIMPAREDE, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(v, g);

		prep = new PrepareDrawUtil();
		this.drawEntities(v, AppDefs.OBJTYPE_BIMPIPE, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(v, g);

		prep = new PrepareDrawUtil();
		this.drawEntities(v, lsObjTypeExcluded, ptBase2dMcs, pt2dMcs, bDragMode, prep);
    	prep.drawAllSolid(v, g);
		
		//this.drawBlip(v, g);
		//this.drawRubberband(v, g);
		//this.drawGrid(v, g);
		//this.drawCoords(v, g);
		//this.drawCursor(v, g);

		return Printable.PAGE_EXISTS;
	}
	
	/* PROCESS OSNAP */

	@Override
    public GeomPoint3d processOsnap(ICadViewBase v, GeomPoint2d pt2dMcs) 
    {
    	GeomPoint3d ptResult = null;
    	
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) return null;
        		
			ptResult = oEnt.osnap3d(v, this.osnapmode, pt2dMcs, null);
			if(ptResult != null) return ptResult;
        }
        return ptResult;
    }

	/* PROCESS MOUSE EVENTS */
    
	@Override
	public void processMouseWheel(double rotationVal)
	{
		if(this.v == null) return;

		Date dataHoraAtual = new Date();
		long dataHoraAtualMili = dataHoraAtual.getTime();

		long diff = dataHoraAtualMili - this.lastMouseWheelTimeMili;		
		if(diff > AppDefs.MOUSEWHEEL_TIMEOUT) {
			if(rotationVal < 0.0) {
				this.v.zoomMoveForwardBackwardMcs(AppDefs.ZOOM_FACTOR_IN);
			}
			else {
				this.v.zoomMoveForwardBackwardMcs(AppDefs.ZOOM_FACTOR_OUT);
			}

			this.lastMouseWheelTimeMili = dataHoraAtualMili;
			
			this.repaint();
		}
	}

	@Override
	public void processMouseClicked_SetCurrSelEnt(int xCursor, int yCursor) { }
	
	@Override
	public void processMouseClicked_SelectMode(int xCursor, int yCursor)
	{
		GeomPoint2d mousePosVideo0 = new GeomPoint2d(xCursor, yCursor);
		GeomPoint2d mousePosScr0 = v.fromVideoToScr(mousePosVideo0);
		GeomPoint2d mousePosMcs0 = v.fromScrToMcs(mousePosScr0);

		//SNAPMODE
		//
		GeomPoint2d mousePosMcs1 = mousePosMcs0;
		if(this.snapmode == AppDefs.SNAPMODE_ON) {
			mousePosMcs1 = v.toSnapPoint(mousePosMcs0);
		}
		
		//ORTHOMODE
		//
		GeomPoint2d mousePosMcs = mousePosMcs1;
		if(this.orthomode == AppDefs.ORTHOMODE_ON) {
			GeomPoint2d basePosMcs = this.getBasePosMcs();
			mousePosMcs = v.toOrthoPoint(basePosMcs, mousePosMcs1);
		}
		GeomPoint2d ptScr = v.fromMcsToScr(mousePosMcs);
    	
		if(this.selectmode == AppDefs.SELECTMODE_SELECTOBJECT)
		{
			this.currSelectpointMcs = v.fromScrToMcs(ptScr);

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currSelectpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currSelectpointMcs);
			this.selectmode = AppDefs.SELECTMODE_NONE;
		}
	}

	@Override
	public void processMouseClicked_PickMode(int xCursor, int yCursor)
	{
		GeomPoint2d mousePosVideo0 = new GeomPoint2d(xCursor, yCursor);
		GeomPoint2d mousePosScr0 = v.fromVideoToScr(mousePosVideo0);
		GeomPoint2d mousePosMcs0 = v.fromScrToMcs(mousePosScr0);

		//SNAPMODE
		//
		GeomPoint2d mousePosMcs1 = mousePosMcs0;
		if(this.snapmode == AppDefs.SNAPMODE_ON) {
			mousePosMcs1 = v.toSnapPoint(mousePosMcs0);
		}
		
		//ORTHOMODE
		//
		GeomPoint2d mousePosMcs2 = mousePosMcs1;
		if(this.orthomode == AppDefs.ORTHOMODE_ON) {
			GeomPoint2d basePosMcs = this.getBasePosMcs();
			mousePosMcs2 = v.toOrthoPoint(basePosMcs, mousePosMcs1);
		}
		
		//OSNAPMODE
		//
		GeomPoint2d mousePosMcs = mousePosMcs2;
		double mousePosZMcs = 0.0;
		if(this.pickmode != AppDefs.PICKMODE_NONE) {
			if(this.osnapmode != AppDefs.OSNAPMODE_NONE) {
				GeomPoint3d mousePosMcs3 = this.processOsnap(v, mousePosMcs2);
				if(mousePosMcs3 != null) {
					mousePosMcs = new GeomPoint2d(mousePosMcs3);
					mousePosZMcs = mousePosMcs3.getZ();					
				}				
			}
		}
		GeomPoint2d ptScr = v.fromMcsToScr(mousePosMcs);
    	
		if(this.pickmode == AppDefs.PICKMODE_PICKPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKFIRSTPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;
			
			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKSECONDPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;
			
			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKFIRSTCORNER)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKSECONDCORNER)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;
			
			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKCENTERPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKRADIUS)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKARCSTARTPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKARCENDPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKANGLE)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
		else if(this.pickmode == AppDefs.PICKMODE_PICKPOINTATDIR)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);
			this.currPickpointZMcs = mousePosZMcs;

			//ADD_BLIP
			//
			GeomPoint2d ptBlip = new GeomPoint2d(this.currPickpointMcs);
			this.lsBlipsMcs.add(ptBlip);

			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptCoords2d(this.currPickpointMcs);
			this.pickmode = AppDefs.PICKMODE_NONE;
		}
	}

	@Override
	public void processMouseClicked_ZoomMode(int xCursor, int yCursor)
	{
		GeomPoint2d mousePosVideo0 = new GeomPoint2d(xCursor, yCursor);
		GeomPoint2d mousePosScr0 = v.fromVideoToScr(mousePosVideo0);
		GeomPoint2d mousePosMcs0 = v.fromScrToMcs(mousePosScr0);

		//SNAPMODE
		//
		GeomPoint2d mousePosMcs1 = mousePosMcs0;
		if(this.snapmode == AppDefs.SNAPMODE_ON) {
			mousePosMcs1 = v.toSnapPoint(mousePosMcs0);
		}
		
		//ORTHOMODE
		//
		GeomPoint2d mousePosMcs = mousePosMcs1;
		if(this.orthomode == AppDefs.ORTHOMODE_ON) {
			GeomPoint2d basePosMcs = this.getBasePosMcs();
			mousePosMcs = v.toOrthoPoint(basePosMcs, mousePosMcs1);
		}
		GeomPoint2d ptScr = v.fromMcsToScr(mousePosMcs);
    	
		if(this.zoommode == AppDefs.ZOOMMODE_PAN)
		{
			this.currZoompointMcs = v.fromScrToMcs(ptScr);
			this.zoommode = AppDefs.ZOOMMODE_NONE;
		}
		else if(this.zoommode == AppDefs.ZOOMMODE_ZOOMFIRSTCORNER)
		{
			this.currZoompointMcs = v.fromScrToMcs(ptScr);
			this.zoommode = AppDefs.ZOOMMODE_NONE;
		}
		else if(this.zoommode == AppDefs.ZOOMMODE_ZOOMSECONDCORNER)
		{
			this.currZoompointMcs = v.fromScrToMcs(ptScr);
			this.zoommode = AppDefs.ZOOMMODE_NONE;
		}
	}
	
	//PROCESS: MOUSE_CLICKED_EVENT
	//		
	@Override
	public void processMouseClicked(int mouseButton, int modifiers, int xCursor, int yCursor)
	{
//		if((modifiers & AppDefs.KEYB_MODIFIERS_CRTLDOWN) == AppDefs.KEYB_MODIFIERS_CRTLDOWN) {
//			if(mouseButton == MouseEvent.BUTTON1) {
//				this.v.zoomRotateLeftRightDegrees(AppDefs.ZOOM_ANGLE_ROTATELEFT);
//			}
//			else if(mouseButton == MouseEvent.BUTTON3) {
//				this.v.zoomRotateLeftRightDegrees(AppDefs.ZOOM_ANGLE_ROTATERIGHT);
//			}
//		}
//		if((modifiers & AppDefs.KEYB_MODIFIERS_SHIFTDOWN) == AppDefs.KEYB_MODIFIERS_SHIFTDOWN) {
//			if(mouseButton == MouseEvent.BUTTON1) {
//				this.v.zoomRotateUpDownDegrees(AppDefs.ZOOM_ANGLE_ROTATEUP);
//			}
//			else if(mouseButton == MouseEvent.BUTTON3) {
//				this.v.zoomRotateUpDownDegrees(AppDefs.ZOOM_ANGLE_ROTATEDOWN);
//			}
//		}
//		this.repaint();
	}
	
	//PROCESS: MOUSE_DOUBLE_CLICKED_EVENT
	//	
	@Override
	public void processMouseDoubleClicked(int mouseButton, int modifiers, int xCursor, int yCursor)
	{
    	if(mouseButton == MouseEvent.BUTTON3)
		{
    		/* nothing todo! */
		}
		else
		{
			this.resetAll();
		}
	}

	//CHECK: MOUSE_DOUBLE_CLICKED_EVENT
	//	
	@Override
	public boolean checkMouseDoubleClicked(int mouseButton, int modifiers, int xCursor, int yCursor)
	{
		boolean bResult = false;
		
		Date dataHoraAtual = new Date();
		long dataHoraAtualMili = dataHoraAtual.getTime();

		long diff = dataHoraAtualMili - this.lastMouseClickTimeMili;	
		
		String warnmsg = String.format("DIFF:%s;", diff);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL02, warnmsg, getClass());
		
		if(diff < AppDefs.MOUSEDOUBLECLICKED_TIMEOUT) {
			bResult = true;
		}
		this.lastMouseClickTimeMili = dataHoraAtualMili;
		return bResult;
	}

	//PROCESS: MOUSE_MOVED_EVENT
	//	
	@Override
	public void processMouseMoved(MouseEvent e)
	{
		if(v == null) return;
		
		int xCursor = e.getX();
		int yCursor = e.getY();

		GeomPoint2d mousePosVideo0 = new GeomPoint2d(xCursor, yCursor);
		GeomPoint2d mousePosScr0 = v.fromVideoToScr(mousePosVideo0);
		//GeomPoint2d mousePosProj0 = v.fromScrToProj(mousePosScr0);

		//GeomVector3d vDirMcs = v.fromProjToMcs(mousePosProj0);
		//GeomPoint3d mousePosMcs0 = new GeomPoint3d( 
		//	vDirMcs.getXF(),
		//	vDirMcs.getYF(),
		//	vDirMcs.getZF() );
		
		Date dataHoraAtual = new Date();
		long dataHoraAtualMili = dataHoraAtual.getTime();

		if(this.currMousePosScr == null) {
			this.currMousePosScr = mousePosScr0;
			this.lastMousePosTimeMili = dataHoraAtualMili;
			
			this.repaint();
		}
		else {
			long diff = dataHoraAtualMili - this.lastMousePosTimeMili;		
			if(diff > AppDefs.MOUSEMOVE_TIMEOUT) {
				double d = this.currMousePosScr.distTo(mousePosScr0);
				if(d >= AppDefs.MOUSEMOVE_MINDIST) {
					this.lastMousePosScr = this.currMousePosScr;
					this.currMousePosScr = mousePosScr0;
				}
				this.lastMousePosTimeMili = dataHoraAtualMili;
				
				this.repaint();
			}
		}
	}

	//PROCESS: MOUSE_DRAGGED_EVENT
	//
	
	@Override
	public void processMouseDragged(MouseEvent e)
	{
		if(this.v == null) return;

		Date dataHoraAtual = new Date();
		long dataHoraAtualMili = dataHoraAtual.getTime();

		long diff = dataHoraAtualMili - this.lastMousePosTimeMili;		
		if(diff > AppDefs.MOUSEDRAGGED_TIMEOUT) {
			double xCursor = e.getX();
			double yCursor = e.getY();

			double xVideo = xCursor - AppDefs.CANVAS_LOCATION_X;
			double yVideo = yCursor - AppDefs.CANVAS_LOCATION_Y;

			this.lastMousePosScr = this.currMousePosScr;
			
			GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
			this.currMousePosScr = this.v.fromVideoToScr(ptVideo);
			this.currMousePosPanScr = new GeomPoint2d(this.currMousePosScr);

	    	if( e.isControlDown() ) 
	    	{
	    		if(this.base3DMoveModePtScr == null) return;	    		
    			this.curr3DMoveModePtScr = new GeomPoint2d(this.currMousePosScr);
	    		
	    		double dX = this.curr3DMoveModePtScr.getX() - this.base3DMoveModePtScr.getX();
	    		if(dX < 0.0) {
	    			v.zoomRotateLeftRightDegrees(AppDefs.ZOOM_ANGLE_ROTATELEFT);		    			
	    		}
	    		else {
	    			v.zoomRotateLeftRightDegrees(AppDefs.ZOOM_ANGLE_ROTATERIGHT);		    			
	    		}

	    		if(this.base3DMoveModePtScr == null) return;
    			this.curr3DMoveModePtScr = new GeomPoint2d(this.currMousePosScr);
	    		
	    		double dY = this.curr3DMoveModePtScr.getY() - this.base3DMoveModePtScr.getY();
	    		if(dY < 0.0) {
	    			v.zoomRotateUpDownDegrees(AppDefs.ZOOM_ANGLE_ROTATEUP);
	    		}
	    		else {
	    			v.zoomRotateUpDownDegrees(AppDefs.ZOOM_ANGLE_ROTATEDOWN);
	    		}
	    	}			
			this.lastMousePosTimeMili = dataHoraAtualMili;
			
			this.repaint();
		}
	}

	//PROCESS: MOUSE_PRESSED_EVENT
	//	

	@Override
	public void processMousePressed(MouseEvent e)
	{
		int buttonId = e.getButton();
		if(buttonId == MouseEvent.BUTTON2) {
			if(this.v == null) return;
	    	
			double xCursor = e.getX();
			double yCursor = e.getY();

			double xVideo = xCursor - AppDefs.CANVAS_LOCATION_X;
			double yVideo = yCursor - AppDefs.CANVAS_LOCATION_Y;

			this.lastMousePosScr = this.currMousePosScr;
			
			GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
			this.currMousePosScr = this.v.fromVideoToScr(ptVideo);
			
	    	if( e.isControlDown() ) {
	    		this.b3DMoveMode = true;
			
    			this.base3DMoveModePtScr = new GeomPoint2d(this.currMousePosScr);
    			this.curr3DMoveModePtScr = new GeomPoint2d(this.base3DMoveModePtScr);
	    	}
//	    	else if( e.isShiftDown() ) {
//	    		this.b3DMoveMode = true;
//			
//    			this.base3DMoveModePtScr = new GeomPoint2d(this.currMousePosScr);
//    			this.curr3DMoveModePtScr = new GeomPoint2d(this.base3DMoveModePtScr);
//	    	}
//	    	else {
//	    		this.b3DMoveMode = true;
//			
//    			this.base3DMoveModePtScr = new GeomPoint2d(this.currMousePosScr);
//    			this.curr3DMoveModePtScr = new GeomPoint2d(this.base3DMoveModePtScr);
//	    	}
    	}
	}

	//PROCESS: MOUSE_RELEASED_EVENT
	//	

	@Override
	public void processMouseReleased(MouseEvent e)
	{
		if(this.v == null) return;
    	
		double xCursor = e.getX();
		double yCursor = e.getY();

		double xVideo = xCursor - AppDefs.CANVAS_LOCATION_X;
		double yVideo = yCursor - AppDefs.CANVAS_LOCATION_Y;

		this.lastMousePosScr = this.currMousePosScr;
		
		GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
		this.currMousePosScr = this.v.fromVideoToScr(ptVideo);
		
		int buttonId = e.getButton();
		if(buttonId == MouseEvent.BUTTON2) {
	    	if( e.isControlDown() ) {
	    		this.b3DMoveMode = false;

    			this.curr3DMoveModePtScr = new GeomPoint2d(this.currMousePosScr);
	    		
	    		double dX = this.curr3DMoveModePtScr.getX() - this.base3DMoveModePtScr.getX();
	    		if(dX < 0.0) {
	    			this.v.zoomRotateLeftRightDegrees(AppDefs.ZOOM_ANGLE_ROTATELEFT);
	    		}
	    		else {
	    			this.v.zoomRotateLeftRightDegrees(AppDefs.ZOOM_ANGLE_ROTATERIGHT);
	    		}

	    		double dY = this.curr3DMoveModePtScr.getY() - this.base3DMoveModePtScr.getY();
	    		if(dY < 0.0) {
	    			this.v.zoomRotateUpDownDegrees(AppDefs.ZOOM_ANGLE_ROTATEUP);
	    		}
	    		else {
	    			this.v.zoomRotateUpDownDegrees(AppDefs.ZOOM_ANGLE_ROTATEDOWN);
	    		}
	    	}
			
			this.repaint();
    	}
	}

	/* MOUSE EVENTS */

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int mouseButton = e.getButton();

		int modifiers = 0;
		
    	if( e.isControlDown() ) {
    		modifiers += AppDefs.KEYB_MODIFIERS_CRTLDOWN;
    	}
    	
    	if( e.isShiftDown() ) {
    		modifiers += AppDefs.KEYB_MODIFIERS_SHIFTDOWN;
    	}
    	
		int xCursor = e.getX();
		int yCursor = e.getY();

		if( checkMouseDoubleClicked(mouseButton, modifiers, xCursor, yCursor) )
			this.processMouseDoubleClicked(mouseButton, modifiers, xCursor, yCursor);
		else
			this.processMouseClicked(mouseButton, modifiers, xCursor, yCursor);
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		this.processMouseMoved(e);
	}
	
    @Override
	public void mouseDragged(MouseEvent e) {
    	this.processMouseDragged(e);
    }	

	@Override
	public void mousePressed(MouseEvent e) { 
		this.processMousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) { 
    	this.processMouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Cursor cursor = FormControlUtil.create3DCursor();
		this.setCursor(cursor);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Cursor cursor = Cursor.getDefaultCursor();
		this.setCursor(cursor);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double rotationVal = e.getPreciseWheelRotation();
		this.processMouseWheel(rotationVal);
	}

	/* ACTION EVENTS */

	@Override
	public void valueChanged(ListSelectionEvent e) { }

	@Override
	public void actionPerformed(ActionEvent e) { }

	@Override
	public void itemStateChanged(ItemEvent e) { }

	@Override
	public void actionResultListener(ResultEvent e) { }

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) { }

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) { }

	@Override
	public void textValueChanged(TextEvent e) { }

	/* COMPONENT EVENTS */

	@Override
	public void componentResized(ComponentEvent e) {
		Component c = e.getComponent();

		double wScr = c.getWidth();
		double hScr = c.getHeight();

		if( (wScr < AppDefs.MATHPREC_MIN) && (hScr < AppDefs.MATHPREC_MIN) )
			return;
		
    	GeomDimension3d oDim3d = this.getEnvelop3d();	    			
		
    	GeomPoint3d ptCentroid3d = new GeomPoint3d( oDim3d.getPtCentroid() );
    	GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
    	GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		
    	double modelDist = ptMin3d.distTo(ptMax3d);
    	double obsDist = modelDist * AppDefs.MODEL_DIST_16X;
		
		if(v == null) {
			this.initCadView(wScr, hScr, ptCentroid3d, modelDist, obsDist);
		}
		else {
			this.resetCadView(wScr, hScr, ptCentroid3d, modelDist, obsDist);
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }

	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) { }

	/* KEY_EVENTS */
	
	@Override
	public void actionKeyResultListener(ResultEvent e) { }		
	
    /* Getters/Setters */
	
	public GeomDimension3d getEnvelop3d()
	{
		GeomDimension3d oDim3d = null;

		if( (this.ptPlanAreaMin == null) && 
			(this.ptPlanAreaMax == null) ) {
			CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
			oDim3d = oBlockDef.getEnvelop3d(AppDefs.OBJTYPE_ALL);
		}
		else {
			GeomPoint3d ptI = new GeomPoint3d(this.ptPlanAreaMin);
			GeomPoint3d ptF = new GeomPoint3d(this.ptPlanAreaMax);
			
			oDim3d = new GeomDimension3d(ptI, ptF);
		}
		return oDim3d;
	}
	
	public void setPlanArea(GeomPoint2d ptMin, GeomPoint2d ptMax)
	{
		this.ptPlanAreaMin = ptMin;
		this.ptPlanAreaMax = ptMax;
	}

	public GeomPoint2d getCurrMousePosScr() {
		return currMousePosScr;
	}

	public void setCurrMousePosScr(GeomPoint2d currMousePosScr) {
		this.currMousePosScr = currMousePosScr;
	}

	public GeomPoint2d getLastMousePosScr() {
		return lastMousePosScr;
	}

	public void setLastMousePosScr(GeomPoint2d lastMousePosScr) {
		this.lastMousePosScr = lastMousePosScr;
	}

	public int getPickmode() {
		return pickmode;
	}

	public void setPickmode(int pickmode) {
		this.pickmode = pickmode;
	}

	public GeomPoint2d getCurrPickpointMcs() {
		return currPickpointMcs;
	}

	public void setCurrPickpointMcs(GeomPoint2d pickpointMcs) {
		this.currPickpointMcs = pickpointMcs;
	}

	public GeomPoint2d getBasePickpointMcs() {
		return basePickpointMcs;
	}

	public void setBasePickpointMcs(GeomPoint2d pickpointMcs) {
		this.basePickpointMcs = pickpointMcs;
	}

	public GeomPoint2d getCenterPickpointMcs() {
		return centerPickpointMcs;
	}

	public void setCenterPickpointMcs(GeomPoint2d centerPickpointMcs) {
		this.centerPickpointMcs = centerPickpointMcs;
	}

	public GeomPoint2d getStartPickpointMcs() {
		return startPickpointMcs;
	}

	public void setStartPickpointMcs(GeomPoint2d startPickpointMcs) {
		this.startPickpointMcs = startPickpointMcs;
	}

	public GeomPoint2d getEndPickpointMcs() {
		return endPickpointMcs;
	}

	public void setEndPickpointMcs(GeomPoint2d endPickpointMcs) {
		this.endPickpointMcs = endPickpointMcs;
	}

	public ArrayList<GeomPoint2d> getLsPtsPickpointMcs() {
		return lsPtsPickpointMcs;
	}

	public void setLsPtsPickpointMcs(ArrayList<GeomPoint2d> lsPtsPickpointMcs) {
		this.lsPtsPickpointMcs = lsPtsPickpointMcs;
	}

	public int getGridmode() {
		return gridmode;
	}

	public void setGridmode(int gridmode) {
		this.gridmode = gridmode;
	}
	
	public int getSnapmode() {
		return snapmode;
	}

	public void setSnapmode(int snapmode) {
		this.snapmode = snapmode;
	}

	public double getSnapmodeXSize() {
		return snapmodeXSize;
	}

	public void setSnapmodeXSize(double snapmodeXSize) {
		this.snapmodeXSize = snapmodeXSize;
	}

	public double getSnapmodeYSize() {
		return snapmodeYSize;
	}

	public void setSnapmodeYSize(double snapmodeYSize) {
		this.snapmodeYSize = snapmodeYSize;
	}

	public GeomPoint2d getSnapmodeOriginMcs() {
		return snapmodeOriginMcs;
	}

	public void setSnapmodeOriginMcs(GeomPoint2d snapmodeOriginMcs) {
		this.snapmodeOriginMcs = snapmodeOriginMcs;
	}

	public int getOrthomode() {
		return orthomode;
	}

	public void setOrthomode(int orthomode) {
		this.orthomode = orthomode;
	}

	public double getGridmodeXSize() {
		return gridmodeXSize;
	}

	public void setGridmodeXSize(double gridmodeXSize) {
		this.gridmodeXSize = gridmodeXSize;
	}

	public double getGridmodeYSize() {
		return gridmodeYSize;
	}

	public void setGridmodeYSize(double gridmodeYSize) {
		this.gridmodeYSize = gridmodeYSize;
	}

	public GeomPoint2d getGridmodeOriginMcs() {
		return gridmodeOriginMcs;
	}

	public void setGridmodeOriginMcs(GeomPoint2d gridmodeOriginMcs) {
		this.gridmodeOriginMcs = gridmodeOriginMcs;
	}

	public int getZoommode() {
		return zoommode;
	}

	public void setZoommode(int zoommode) {
		this.zoommode = zoommode;
	}

	public int getSelectmode() {
		return selectmode;
	}

	public void setSelectmode(int selectmode) {
		this.selectmode = selectmode;
	}

	public int getSelectobjtype() {
		return selectobjtype;
	}

	public void setSelectobjtype(int selectobjtype) {
		this.selectobjtype = selectobjtype;
	}

	public GeomPoint2d getCurrSelectpointMcs() {
		return currSelectpointMcs;
	}

	public void setCurrSelectpointMcs(GeomPoint2d currSelectpointMcs) {
		this.currSelectpointMcs = currSelectpointMcs;
	}

	public GeomPoint2d getCurrZoompointMcs() {
		return currZoompointMcs;
	}

	public void setCurrZoompointMcs(GeomPoint2d currZoompointMcs) {
		this.currZoompointMcs = currZoompointMcs;
	}

	public GeomPoint2d getBaseZoompointMcs() {
		return baseZoompointMcs;
	}

	public void setBaseZoompointMcs(GeomPoint2d baseZoompointMcs) {
		this.baseZoompointMcs = baseZoompointMcs;
	}

	public int getDragmode() {
		return dragmode;
	}

	public void setDragmode(int dragmode) {
		this.dragmode = dragmode;
	}

	public GeomVector2d getVDirPickpointMcs() {
		return vDirPickpointMcs;
	}

	public void setVDirPickpointMcs(GeomVector2d vDirPickpointMcs) {
		this.vDirPickpointMcs = vDirPickpointMcs;
	}

	public double getRefPickpointMcs() {
		return refPickpointMcs;
	}

	public void setRefPickpointMcs(double refPickpointMcs) {
		this.refPickpointMcs = refPickpointMcs;
	}

	public double getCurrPickpointZMcs() {
		return 0;
	}

	public void setCurrPickpointZMcs(double currPickpointZMcs) {
		/* nothing todo! */
	}

	public GeomPoint2d getBasePosMcs()
	{
		if( (pickmode == AppDefs.PICKMODE_PICKSECONDPOINT) ||
			(pickmode == AppDefs.PICKMODE_PICKCENTERPOINT) ||
			(pickmode == AppDefs.PICKMODE_PICKRADIUS) )
		{
			if(basePickpointMcs != null) return basePickpointMcs;
			//CenterPoint And StartPoint (for Angles, Arcs or Circles Rubberband)
			if(centerPickpointMcs != null) return centerPickpointMcs;
			if(startPickpointMcs != null) return startPickpointMcs;
			if(endPickpointMcs != null) return endPickpointMcs;
		}
		return null;
	}

	public ICadViewBase getCadViewBase() {
		return v;
	}
	
}
