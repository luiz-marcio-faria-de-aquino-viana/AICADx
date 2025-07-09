/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompPlanView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.comp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadArea;
import br.com.tlmv.aicadxapp.cad.CadAreaTable;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3D;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadJanela;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPDupla;
import br.com.tlmv.aicadxapp.cad.CadParede;
import br.com.tlmv.aicadxapp.cad.CadPiso;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.CadPorta;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.CadView2d;
import br.com.tlmv.aicadxapp.cad.CadView3d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cad.utils.RubberbandUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.events.KeyResultListener;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CompPlanView extends JPanel implements ICompView, ComponentListener, MouseListener, MouseMotionListener, KeyResultListener
{
//Private
	private MainFrame parentFrame = null;
	
	private CadDocumentDef doc = null;
	
	private ICadViewBase v = null;
	
	private GeomPoint2d currMousePosScr = null;

	private GeomPoint2d lastMousePosScr = null;
	
	private long lastMousePosTimeMili = -1; 
    
	private long lastMouseClickTimeMili = -1; 
    
	//PICKMODE_VARS
	//
	private int pickmode = AppDefs.PICKMODE_NONE;
	//
	private GeomPoint2d currPickpointMcs = null;
	//BasePoint (for Linear or Rectangle Rubberband)
	private GeomPoint2d basePickpointMcs = null;
	//CenterPoint And StartPoint (for Angles, Arcs or Circles Rubberband)
	private GeomPoint2d centerPickpointMcs = null;
	private GeomPoint2d startPickpointMcs = null;
	private GeomPoint2d endPickpointMcs = null;
	//Direction vector
	private GeomVector2d vDirPickpointMcs = null;
	//Point list
	private ArrayList<GeomPoint2d> lsPtsPickpointMcs = null;
	
	//ZOOMMODE_VARS
	//
	private int zoommode = AppDefs.ZOOMMODE_NONE;
	//
	private GeomPoint2d currZoompointMcs = null;
	//BasePoint (for Linear or Rectangle Rubberband)
	private GeomPoint2d baseZoompointMcs = null;
	
	//GRIDMODE_VARS
	//
	private int gridmode = AppDefs.GRIDMODE_ON;
	//
	private double gridmodeXSize = AppDefs.GRIDMODE_XSIZE;
	private double gridmodeYSize = AppDefs.GRIDMODE_YSIZE;
	//
	private GeomPoint2d gridmodeOriginMcs = AppDefs.GRIDMODE_ORIGIN;

	//SNAP_MODE
	//
	private int snapmode = AppDefs.SNAPMODE_ON;
	//
	private double snapmodeXSize = AppDefs.SNAPMODE_XSIZE;
	private double snapmodeYSize = AppDefs.SNAPMODE_YSIZE;
	//
	private GeomPoint2d snapmodeOriginMcs = AppDefs.SNAPMODE_ORIGIN;

	//OSNAP_MODE
	//
	private int osnapmode = AppDefs.OSNAPMODE_ALL;
	
	//ORTHOMODE_VARS
	//
	private int orthomode = AppDefs.ORTHOMODE_OFF;
	
	//SELECTMODE_VARS
	//
	private int selectmode = AppDefs.SELECTMODE_NONE;
	private int selectobjtype = AppDefs.OBJTYPE_ANY;
	//
	private GeomPoint2d currSelectpointMcs = null;

	//DRAGMODE_VARS
	//
	private int dragmode = AppDefs.DRAGMODE_NONE;
	
	//BLIPS_VARS
	//
	private ArrayList<GeomPoint2d> lsBlipsMcs = null;
	
	/* Methodes */
	
	/* DRAW */
	
	//CLEARSCREEN
	//
    private void clearScreen(ICadViewBase v, Graphics g) 
    {
    	Color oldcol = GeomUtil.setColor(g, AppDefs.BACKGROUNDCOLOR);
    	
    	int w = this.getWidth();
    	int h = this.getHeight();

    	g.fillRect(0, 0, w, h);

    	GeomUtil.setColor(g, oldcol);
	}
    
    // DRAWENTITIES
    //
    private void drawEntities(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 
        		
			//CADxxx
			//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_POINT) 
			{
				CadPoint o = (CadPoint)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_LINE) 
			{
				CadLine o = (CadLine)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_RECTANGLE) 
			{
				CadRectangle o = (CadRectangle)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_POLYGON) 
			{
				CadPolygon o = (CadPolygon)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_CIRCLE) 
			{
				CadCircle o = (CadCircle)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_ARC) 
			{
				CadArc o = (CadArc)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_TEXT) 
			{
				CadText o = (CadText)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMAREA) 
			{
				CadArea o = (CadArea)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}
			
			//CAD3Dxxx
			//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BOX3D) 
			{
				CadBox3D o = (CadBox3D)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}
			
			//BIMxxx
			//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPISO) 
			{
				CadPiso o = (CadPiso)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}
			
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) 
			{
				CadParede o = (CadParede)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPORTA) 
			{
				CadPorta o = (CadPorta)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}
			
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPDUPLA) 
			{
				CadPDupla o = (CadPDupla)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMJANELA) 
			{
				CadJanela o = (CadJanela)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMAREATABLE) 
			{
				CadAreaTable o = (CadAreaTable)oEnt;
				
				if( o.isSelected() )
					o.redraw2d(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
				o.redraw2d(v, null, pt2dMcs, false, g);
				o.osnap2d(v, this.osnapmode, pt2dMcs, g);

				continue;
			}
        }
    }
    
    // DRAWBLIP
	//
	private void drawBlip(ICadViewBase v, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, AppDefs.BLIPCOLOR);		

		for(GeomPoint2d ptMcs : this.lsBlipsMcs)
		{
			GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);				
			GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);
			
			int xVideo = (int)ptVideo.getX() - 1;
			int yVideo = (int)ptVideo.getY() - 1;
			
			g.drawLine(xVideo - 2, yVideo, xVideo + 2, yVideo);
			g.drawLine(xVideo, yVideo - 2, xVideo, yVideo + 2);
		}

		GeomUtil.setColor(g, oldcol);		
	}

	/* DRAWRUBBERBAND */

	//DRAWRUBBERBAND_PICKMODE
	private void drawRubberband_PickMode(ICadViewBase v, Graphics g)
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
	private void drawRubberband_ZoomMode(ICadViewBase v, Graphics g)
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
	private void drawRubberband(ICadViewBase v, Graphics g)
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
	private void drawGrid(ICadViewBase v, Graphics g)
	{
		if(this.gridmode == AppDefs.GRIDMODE_OFF) return;

		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIDCOLOR); 
		
		int oldtextsz = GeomUtil.setTextHeight(g, AppDefs.GRIDMODE_TEXT_HEIGHTSCR);
		
		GeomRect2d limitsMcs = v.getLimitsMcs2d();

		GeomPoint2d ptMin = limitsMcs.getPtMin();
		GeomPoint2d ptMax = limitsMcs.getPtMax();
				
		double w = ptMax.getX() - ptMin.getX();
		double h = ptMax.getY() - ptMin.getY();

		int nW = (int)Math.floor(w / AppDefs.GRIDMODE_XSIZE) + 1;
		int nH = (int)Math.floor(h / AppDefs.GRIDMODE_YSIZE) + 1;

		//DRAW_GRID
		//
		double xMin = ptMin.getX();
		double yMin = ptMin.getY();		

		for(int i = 0; i < nH; i++) {
			double y = yMin + (i * AppDefs.GRIDMODE_YSIZE);
			
			int yaxisnum = i % AppDefs.GRIDMODE_YAXISNUM;
			if(yaxisnum == 0) {
				double xp = xMin - (AppDefs.GRIDMODE_XSIZE / 2.0);
				double yp = y;

				GeomPoint2d textMcs = new GeomPoint2d(xp, yp);
				GeomPoint2d textScr = v.fromMcsToScr(textMcs);				
				GeomPoint2d textVideo = v.fromScrToVideo(textScr);
				
				String str = String.format("%s", i);
				g.drawString(str, (int)textVideo.getX(), (int)textVideo.getY());
			}
			
			for(int j = 0; j < nW; j++) {
				double x = xMin + (j * AppDefs.GRIDMODE_XSIZE);

				int xaxisnum = j % AppDefs.GRIDMODE_XAXISNUM;
				if(xaxisnum == 0) {
					double xp = x;
					double yp = yMin - (AppDefs.GRIDMODE_YSIZE / 2.0);

					GeomPoint2d textMcs = new GeomPoint2d(xp, yp);
					GeomPoint2d textScr = v.fromMcsToScr(textMcs);				
					GeomPoint2d textVideo = v.fromScrToVideo(textScr);
					
					String str = String.format("%s", j);
					g.drawString(str, (int)textVideo.getX(), (int)textVideo.getY());
				}
				
				GeomPoint2d gridMcs = new GeomPoint2d(x, y);
				GeomPoint2d gridScr = v.fromMcsToScr(gridMcs);				
				GeomPoint2d gridVideo = v.fromScrToVideo(gridScr);
				
				int xVideo = (int)gridVideo.getX() - 1;
				int yVideo = (int)gridVideo.getY() - 1;
				
				g.fillRect(xVideo, yVideo, (int)AppDefs.GRIDMODE_PIXELSIZE, (int)AppDefs.GRIDMODE_PIXELSIZE);
			}
		}

		GeomUtil.setTextHeight(g, oldtextsz);
		
		GeomUtil.setColor(g, oldcol); 
	}
	
	// DRAWCOORDS
	//
	private void drawCoords(ICadViewBase v, Graphics g)
	{
		GeomPoint2d ptScr2d = this.currMousePosScr;
		if(ptScr2d == null) 
			ptScr2d = new GeomPoint2d(0.0, 0.0);

		Color oldcol = GeomUtil.setColor(g, AppDefs.DISPCOORDS_COLOR);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		GeomPoint2d ptMcs = v.fromScrToMcs(ptScr2d); 
		double xMcs = ptMcs.getX();
		double yMcs = ptMcs.getY();
		
		String strCoords = String.format(
			"%s x %s", 
			nf3.format(xMcs),
			nf3.format(yMcs));

        Font f = new Font(AppDefs.DISPCORDS_FONTFAMILY, Font.BOLD, AppDefs.DISPCOORDS_HEIGHT);

        Font oldfont = GeomUtil.setFont(g, f);
        
        FontMetrics metrics = g.getFontMetrics();
        Rectangle2D rect = metrics.getStringBounds(strCoords, g);
        
        int xVideo = (int)((this.getWidth() / 2.0) - rect.getCenterX());
        int yVideo = AppDefs.DISPCOORDS_VERTICALPOS;
        
    	g.drawString(strCoords, xVideo, yVideo);

        GeomUtil.setFont(g, oldfont);
				
		GeomUtil.setColor(g, oldcol);
	}
	
	// DRAWCURSOR
	//
	private void drawCursor(ICadViewBase v, Graphics g)
	{
		if(this.currMousePosScr == null) return;

		Color oldcol = GeomUtil.setColor(g, AppDefs.CURSORCOLOR);		
		
		if(this.selectmode == AppDefs.SELECTMODE_SELECTOBJECT)
		{
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
			GeomPoint2d mousePorVideo = v.fromVideoToScr(this.currMousePosScr);
			
			int xCursorVideo = (int)mousePorVideo.getX();
			int yCursorVideo = (int)mousePorVideo.getY();
	
			int w = this.getWidth();
			int h = this.getHeight();
		
			g.drawLine(0, yCursorVideo, w, yCursorVideo);
			g.drawLine(xCursorVideo, 0, xCursorVideo, h);
		}
		
		GeomUtil.setColor(g, oldcol);		
	}
	
	/* SELECT */
    
    // SELECTENTITY
    //
	public CadEntity selectEntity(GeomPoint2d pt2dMcs)
	{
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 
        	
			//CADxxx
			//
			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_POINT) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_POINT) ) 
			{
				CadPoint o = (CadPoint)oEnt;
				
				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}
			
			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_LINE) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_LINE) ) 
			{
				CadLine o = (CadLine)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_RECTANGLE) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_RECTANGLE) ) 
			{
				CadRectangle o = (CadRectangle)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}
			
			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_POLYGON) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_POLYGON) ) 
			{
				CadPolygon o = (CadPolygon)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_CIRCLE) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_CIRCLE) ) 
			{
				CadCircle o = (CadCircle)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_ARC) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_ARC) ) 
			{
				CadArc o = (CadArc)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_TEXT) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_TEXT) ) 
			{
				CadText o = (CadText)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}
			
			//CAD3Dxxx
			//
			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BOX3D) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BOX3D) ) 
			{
				CadBox3D o = (CadBox3D)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}
			
			//BIMxxx
			//
			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BIMPISO) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPISO) ) 
			{
				CadPiso o = (CadPiso)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BIMPAREDE) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) ) 
			{
				CadParede o = (CadParede)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BIMPORTA) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPORTA) ) 
			{
				CadPorta o = (CadPorta)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BIMPDUPLA) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPDUPLA) ) 
			{
				CadPDupla o = (CadPDupla)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BIMJANELA) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BIMJANELA) ) 
			{
				CadJanela o = (CadJanela)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}

			if( ( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				  (this.selectobjtype == AppDefs.OBJTYPE_BIMAREATABLE) ) && 
				(oEnt.getObjType() == AppDefs.OBJTYPE_BIMAREATABLE) ) 
			{
				CadAreaTable o = (CadAreaTable)oEnt;

				boolean bSelected = o.select2d(v, pt2dMcs, true);
				if( bSelected )
					return o;
			}
        }
        return null;
	}
		
//Public

    public CompPlanView(MainFrame frm, CadDocumentDef doc) {
		super();
		
		this.init(frm, doc);
	}
	
    /* Methodes */
    
    public void init(MainFrame frm, CadDocumentDef doc) {
    	this.parentFrame = frm;
    	this.doc = doc;
    	
		this.setBackground(Color.LIGHT_GRAY);
		this.setForeground(Color.BLACK);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addComponentListener(this);
		
    	this.lsBlipsMcs = new ArrayList<GeomPoint2d>();		
    }
    
	public void initCadView(double wScr, double hScr)
	{
		//MCS
		double wMcs = AppDefs.MCSPLAN_XSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
		double hMcs = AppDefs.MCSPLAN_YSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
		
		double xCenterMcs = wMcs / 2.0;
		double yCenterMcs = hMcs / 2.0;
		
		GeomPoint2d ptCenterMcs = new GeomPoint2d(xCenterMcs, yCenterMcs); 
		GeomPoint2d ptXDirMcs = new GeomPoint2d(xCenterMcs + 1.0, yCenterMcs);

		GeomVector2d xDirMcs = new GeomVector2d(ptCenterMcs, ptXDirMcs);

		this.v = new CadView2d(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			wScr,
			hScr);
	}
	
	public void resetCadView(double wScr, double hScr)
	{
		//MCS
		GeomPlan2d planMcs = this.v.getPlanMcs2d();
		
		double wMcs = planMcs.getWidth();
		double hMcs = planMcs.getHeight();
		
		GeomPoint2d ptCenterMcs = planMcs.getPtCenter();
		GeomVector2d xDirMcs = planMcs.getXDir();

		this.v.reset(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			wScr,
			hScr);
	}

    /* RESET_PICKMODE_VARS */
    
    public void resetPickModeVars()
    {
    	this.pickmode = AppDefs.PICKMODE_NONE;
    	//
    	this.currPickpointMcs = null;
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
    
    public void resetSelectModeVars()
    {
    	this.selectmode = AppDefs.SELECTMODE_NONE;
    	this.selectobjtype = AppDefs.OBJTYPE_ANY;
    	//
    	this.currSelectpointMcs = null;
    }

    /* RESET_ZOOMMODE_VARS */
    
    public void resetZoomModeVars()
    {
    	this.zoommode = AppDefs.ZOOMMODE_NONE;
    	//
    	this.currZoompointMcs = null;
    	//BasePoint (for Linear or Rectangle Rubberband)
    	this.baseZoompointMcs = null;
    }

    /* BLIPS */
    
    public synchronized void clearBlips()
    {
    	this.lsBlipsMcs.clear();
    }

    /* RESET ALL */
    
    public void resetAll()
    {
    	this.resetPickModeVars();
    	this.resetSelectModeVars();
    	this.resetZoomModeVars();
    	//
    	this.clearBlips();
    }

    /* REPAINT ALL */
    
	public void repaintAll()
	{
		this.repaint();
	}
    
    /* PAINT EVENTS */

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);

		if(v == null) {
			double wScr = this.getWidth();
			double hScr = this.getHeight();

			if( (wScr < AppDefs.MATHPREC_MIN) && (hScr < AppDefs.MATHPREC_MIN) )
				return;
			
			this.initCadView(wScr, hScr);
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
		
    	this.clearScreen(v, g);
		this.drawEntities(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
		this.drawBlip(v, g);
		this.drawRubberband(v, g);
		this.drawGrid(v, g);
		this.drawCoords(v, g);
		this.drawCursor(v, g);
	}
	
	/* PROCESS OSNAP */
	
    private GeomPoint2d processOsnap(ICadViewBase v, GeomPoint2d pt2dMcs) 
    {
    	GeomPoint2d ptResult = null;
    	
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 
        		
        	//CADxxx
        	//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_POINT) {
				CadPoint o = (CadPoint)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine o = (CadLine)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_RECTANGLE) {
				CadRectangle o = (CadRectangle)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_POLYGON) {
				CadPolygon o = (CadPolygon)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_CIRCLE) {
				CadCircle o = (CadCircle)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_ARC) {
				CadArc o = (CadArc)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_TEXT) {
				CadText o = (CadText)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMAREA) {
				CadArea o = (CadArea)oEnt;

				o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}			
			//CAD3Dxxx
			//
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BOX3D) {
				CadBox3D o = (CadBox3D)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			//BIMxxx
			//
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPISO) {
				CadPiso o = (CadPiso)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
				CadParede o = (CadParede)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPORTA) {
				CadPorta o = (CadPorta)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPDUPLA) {
				CadPDupla o = (CadPDupla)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMJANELA) {
				CadJanela o = (CadJanela)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMAREATABLE) {
				CadAreaTable o = (CadAreaTable)oEnt;
				
				ptResult = o.osnap2d(v, this.osnapmode, pt2dMcs, null);
				if(ptResult != null) return ptResult;
			}
        }
        return ptResult;
    }

	/* PROCESS MOUSE EVENTS */

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
		if(this.pickmode != AppDefs.PICKMODE_NONE) {
			if(this.osnapmode != AppDefs.OSNAPMODE_NONE) {
				GeomPoint2d mousePosMcs3 = this.processOsnap(v, mousePosMcs2);
				if(mousePosMcs3 != null)
					mousePosMcs = mousePosMcs3;
			}
		}
		GeomPoint2d ptScr = v.fromMcsToScr(mousePosMcs);
    	
		if(this.pickmode == AppDefs.PICKMODE_PICKPOINT)
		{
			this.currPickpointMcs = v.fromScrToMcs(ptScr);

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
	public void processMouseClicked(int mouseButton, int xCursor, int yCursor)
	{
    	if(mouseButton == MouseEvent.BUTTON3)
		{
			MainPanel.getParentFrame().showPopupMenu(xCursor, yCursor);			
		}
		else
		{
			if(this.zoommode != AppDefs.ZOOMMODE_NONE)
			{
				this.processMouseClicked_ZoomMode(xCursor, yCursor);				
			}
			else if(this.selectmode != AppDefs.SELECTMODE_NONE)
			{
				this.processMouseClicked_SelectMode(xCursor, yCursor);
			}
			else if(this.pickmode != AppDefs.PICKMODE_NONE)
			{
				this.processMouseClicked_PickMode(xCursor, yCursor);
			}
		}
	}
	
	//PROCESS: MOUSE_DOUBLE_CLICKED_EVENT
	//	
	public void processMouseDoubleClicked(
		int mouseButton, 
		int xCursor, 
		int yCursor)
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
	public boolean checkMouseDoubleClicked(
		int mouseButton, 
		int xCursor, 
		int yCursor)
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
	public void processMouseMoved(MouseEvent e)
	{
		int xCursor = e.getX();
		int yCursor = e.getY();

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
		GeomPoint2d mousePosScr = v.fromMcsToScr(mousePosMcs);
		//GeomPoint2d mousePosVideo = new GeomPoint2d(mousePosScr);		
		
		Date dataHoraAtual = new Date();
		long dataHoraAtualMili = dataHoraAtual.getTime();

		if(this.currMousePosScr == null) {
			this.currMousePosScr = mousePosScr;
			this.lastMousePosTimeMili = dataHoraAtualMili;
			
			this.repaint();
		}
		else {
			long diff = dataHoraAtualMili - this.lastMousePosTimeMili;		
			if(diff > AppDefs.MOUSEMOVE_TIMEOUT) {
				double d = this.currMousePosScr.distTo(mousePosScr);
				if(d >= AppDefs.MOUSEMOVE_MINDIST) {
					this.lastMousePosScr = this.currMousePosScr;
					this.currMousePosScr = mousePosScr;
				}
				this.lastMousePosTimeMili = dataHoraAtualMili;
				
				this.repaint();
			}
		}
	}

	/* MOUSE EVENTS */

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int mouseButton = e.getButton();
		int xCursor = e.getX();
		int yCursor = e.getY();
		
		if( checkMouseDoubleClicked(mouseButton, xCursor, yCursor) )
			this.processMouseDoubleClicked(mouseButton, xCursor, yCursor);
		else
			this.processMouseClicked(mouseButton, xCursor, yCursor);
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		this.processMouseMoved(e);
	}
	
    @Override
	public void mouseDragged(MouseEvent e) { }	

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) {
		Cursor cursor = FormControlUtil.createBlankCursor();		
		this.setCursor(cursor);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Cursor cursor = Cursor.getDefaultCursor();
		this.setCursor(cursor);
	}
	
	/* COMPONENT EVENTS */

	@Override
	public void componentResized(ComponentEvent e) {
		Component c = e.getComponent();

		double wScr = c.getWidth();
		double hScr = c.getHeight();

		if( (wScr < AppDefs.MATHPREC_MIN) && (hScr < AppDefs.MATHPREC_MIN) )
			return;
		
		if(v == null) {
			this.initCadView(wScr, hScr);
		}
		else {
			this.resetCadView(wScr, hScr);			
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }

	/* KEY_EVENTS */
	
	@Override
	public void actionKeyResultListener(ResultEvent e) 
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = (String)e.getEventData();
		if(this.pickmode != AppDefs.PICKMODE_NONE)
		{
			if(str.length() > 0) {
				char ch = str.charAt(0);
				if(ch == '@') {
					str = str.substring(1);
					
					String[] arr = StringUtil.split(str, ',');
					if(arr.length >= 2) {
						double dX = StringUtil.safeDbl(nf6, arr[0]);
						double dY = StringUtil.safeDbl(nf6, arr[1]);

						GeomVector2d v2dMcs = new GeomVector2d(dX, dY); 
						
						if(this.basePickpointMcs != null) {
							GeomPoint2d pt2dMcs = this.basePickpointMcs.otherAdd(v2dMcs);
							GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);

							GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);
							int xCursor = (int)pt2dVideo.getX();
							int yCursor = (int)pt2dVideo.getY();
							
							this.processMouseClicked_PickMode(xCursor, yCursor);
						}
					}
				}
				else {
					String[] arr = StringUtil.split(str, ',');
					if(arr.length == 1) {
						double dist = StringUtil.safeDbl(nf6, arr[0]);
						
						if(this.basePickpointMcs != null) {
							if(this.vDirPickpointMcs != null) {
								GeomPoint2d ptBase2dMcs = new GeomPoint2d(this.basePickpointMcs);
								
								GeomPoint2d pt2dMcs = ptBase2dMcs.otherMoveTo(this.vDirPickpointMcs, dist); 
								GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);
	
								GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);
								int xCursor = (int)pt2dVideo.getX();
								int yCursor = (int)pt2dVideo.getY();
								
								this.processMouseClicked_PickMode(xCursor, yCursor);
							}
							else {
								GeomPoint2d ptBase2dMcs = new GeomPoint2d(this.basePickpointMcs);
								GeomPoint2d ptCurr2dMcs = v.fromScrToMcs(this.currMousePosScr);
		
								GeomVector2d vPtBaseToPtCurr2d = new GeomVector2d(ptBase2dMcs, ptCurr2dMcs);
								GeomVector2d uPtBaseToPtCurr2d = vPtBaseToPtCurr2d.otherUnit();
	
								GeomPoint2d pt2dMcs = ptBase2dMcs.otherMoveTo(uPtBaseToPtCurr2d, dist); 
								GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);
	
								GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);
								int xCursor = (int)pt2dVideo.getX();
								int yCursor = (int)pt2dVideo.getY();
								
								this.processMouseClicked_PickMode(xCursor, yCursor);
							}
						}
					}
					else {
						double dX = StringUtil.safeDbl(nf6, arr[0]);
						double dY = StringUtil.safeDbl(nf6, arr[1]);

						GeomPoint2d pt2dMcs = new GeomPoint2d(dX, dY); 
						GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);

						GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);
						int xCursor = (int)pt2dVideo.getX();
						int yCursor = (int)pt2dVideo.getY();
						
						this.processMouseClicked_PickMode(xCursor, yCursor);
					}
				}
			}
		}
	}
				
    /* Getters/Setters */

	public MainFrame getParent() {
		return this.parentFrame;
	}

	public CadDocumentDef getDocument() {
		return this.doc;
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
	
	public ICadViewBase getCadViewBase()
	{
		return this.v;
	}
	
}
