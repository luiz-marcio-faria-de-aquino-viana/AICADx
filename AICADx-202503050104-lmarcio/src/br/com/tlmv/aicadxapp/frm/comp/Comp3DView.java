/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Comp3DView.java
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
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3D;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadJanela;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
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
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class Comp3DView extends JPanel implements ICompView, ComponentListener, MouseListener, MouseMotionListener
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
	
	//CLEARSCREEN
	//
    private void clearScreen(ICadViewBase v, Graphics g) 
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
    private void drawBasePlan(ICadViewBase v, Graphics g) 
    {
    	Color oldcol = g.getColor();		
		
		Color c = AppDefs.BASEPLANCOLOR;
		g.setColor(c);
    	
		GeomPlan3d planMcs = v.getPlanMcs3d();

		//LIMITS (MCS)
		GeomPoint3d ptLLC = planMcs.getPtLowerLeftCorner();
		GeomPoint3d ptLRC = planMcs.getPtLowerRightCorner();
		GeomPoint3d ptURC = planMcs.getPtUpperRightCorner();
		GeomPoint3d ptULC = planMcs.getPtUpperLeftCorner();

		DrawUtil.drawFaceProj(v, ptLLC, ptLRC, ptURC, ptULC, g);

		g.setColor(oldcol);
	}
    
    // DRAWENTITIES
    //
    private void drawEntities(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	PrepareDrawUtil prep = new PrepareDrawUtil();
    	
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();

    	GeomObserver3d obs = v.getObsMcs();

    	GeomPoint3d ptCentroid = new GeomPoint3d(obs.getPtObserver());

    	ArrayList<CadEntity> lsEntities = oBlockDef.sortAllEntities(ptCentroid);
    	int szLsEntities = lsEntities.size();
    	
        for(int i = 0; i < szLsEntities; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        		
			//CAD3Dxxx
			//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BOX3D) 
			{
				CadBox3D o = (CadBox3D)oEnt;
				o.redraw3d(v, null, pt2dMcs, false, prep);
				continue;
			}

			//BIMxxx
			//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPISO) 
			{
				CadPiso o = (CadPiso)oEnt;
				o.redraw3d(v, null, pt2dMcs, false, prep);
				continue;
			}
			
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) 
			{
				CadParede o = (CadParede)oEnt;
				o.redraw3d(v, null, pt2dMcs, false, prep);
				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMJANELA) 
			{
				CadJanela o = (CadJanela)oEnt;
				o.redraw3d(v, null, pt2dMcs, false, prep);
				continue;
			}

			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPORTA) 
			{
				CadPorta o = (CadPorta)oEnt;
				o.redraw3d(v, null, pt2dMcs, false, prep);
				continue;
			}
			
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPDUPLA) 
			{
				CadPDupla o = (CadPDupla)oEnt;
				o.redraw3d(v, null, pt2dMcs, false, prep);
				continue;
			}
        }

        prep.drawAllSolid(v, g);
    }
    
    // DRAWBLIP
	//
	private void drawBlip(ICadViewBase v, Graphics g)
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
	private void drawCoords(ICadViewBase v, Graphics g)
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
	private void drawCursor(ICadViewBase v, Graphics g)
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
    
    // SELECTENTITY
    //
	public CadEntity selectEntity(GeomPoint2d pt2dMcs)
	{
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	
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
        }
        return null;
	}
		
//Public

    public Comp3DView(MainFrame frm, CadDocumentDef doc) {
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
		
    	this.lsBlipsMcs = new ArrayList<GeomPoint2d>();		
    }
    
	public void initCadView(double wScr, double hScr)
	{
		//MCS
		double wMcs = AppDefs.MCSPLAN_XSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
		double hMcs = AppDefs.MCSPLAN_YSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
		double zHMcs = AppDefs.MCSPLAN_ZHEIGHT;
		
		double xCenterMcs = wMcs / 2.0;
		double yCenterMcs = hMcs / 2.0;
		
		GeomPoint3d ptCenterMcs = new GeomPoint3d(xCenterMcs, yCenterMcs, 0.0); 
		GeomPoint3d ptXDirMcs = new GeomPoint3d(xCenterMcs + 1.0, yCenterMcs, 0.0);

		GeomVector3d xDirMcs = new GeomVector3d(ptCenterMcs, ptXDirMcs);
		GeomVector3d uDirMcs = xDirMcs.otherUnit();

		//OBSERVER
		GeomPoint3d ptObsMcs = new GeomPoint3d(- AppDefs.MATHVAL_HSQRT2, - AppDefs.MATHVAL_HSQRT2, 1.0);
		GeomPoint3d ptDirMcs = new GeomPoint3d(AppDefs.MATHVAL_HSQRT2, AppDefs.MATHVAL_HSQRT2, 1.0);
		
		GeomVector3d vObsDirMcs = new GeomVector3d(ptObsMcs, ptDirMcs);		
		GeomVector3d uObsDirMcs = vObsDirMcs.otherUnit();		
		
		this.v = new CadView3d(
			ptCenterMcs,
			uDirMcs,
			wMcs,
			hMcs,
			zHMcs,
			wScr,
			hScr,
			ptObsMcs,
			uObsDirMcs);
	}
    
	public void resetCadView(double wScr, double hScr)
	{
		//MCS
		GeomPlan3d planMcs = this.v.getPlanMcs3d();
		
		double wMcs = planMcs.getWidth();
		double hMcs = planMcs.getHeight();
		double zHMcs = planMcs.getZHeight();;
		
		GeomPoint3d ptCenterMcs = planMcs.getPtCenter();
		GeomVector3d xDirMcs = planMcs.getXDir();

		//OBSERVER
		GeomObserver3d obsMcs = this.v.getObsMcs();

		GeomPoint3d ptObsMcs = obsMcs.getPtObserver();
		GeomVector3d vObsDirMcs = obsMcs.getUnitDir();
		
		this.v = new CadView3d(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			zHMcs,
			wScr,
			hScr,
			ptObsMcs,
			vObsDirMcs);
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
    	this.selectobjtype = AppDefs.OBJTYPE_BIMENTITIES;
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
		this.drawBasePlan(v, g);
		this.drawEntities(v, ptBase2dMcs, pt2dMcs, bDragMode, g);
		//this.drawBlip(v, g);
		//this.drawRubberband(v, g);
		//this.drawGrid(v, g);
		//this.drawCoords(v, g);
		//this.drawCursor(v, g);
	}
	
	/* PROCESS OSNAP */

    private GeomPoint2d processOsnap(ICadViewBase v, GeomPoint2d pt2dMcs) 
    {
    	GeomPoint2d ptResult = null;
    	
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) return null;
        		
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
        }
        return ptResult;
    }

	/* PROCESS MOUSE EVENTS */

	public void processMouseClicked_SelectMode(MouseEvent e)
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

	public void processMouseClicked_PickMode(MouseEvent e)
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

	public void processMouseClicked_ZoomMode(MouseEvent e)
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
	public void processMouseClicked(MouseEvent e)
	{
    	if(e.getButton() == MouseEvent.BUTTON3)
		{
			MainPanel.getParentFrame().showPopupMenu(
					e.getX(),
					e.getY() );			
		}
		else
		{
			if(this.zoommode != AppDefs.ZOOMMODE_NONE)
			{
				this.processMouseClicked_ZoomMode(e);				
			}
			else if(this.selectmode != AppDefs.SELECTMODE_NONE)
			{
				this.processMouseClicked_SelectMode(e);
			}
			else if(this.pickmode != AppDefs.PICKMODE_NONE)
			{
				this.processMouseClicked_PickMode(e);
			}
		}
	}
	
	//PROCESS: MOUSE_DOUBLE_CLICKED_EVENT
	//	
	public void processMouseDoubleClicked(MouseEvent e)
	{
    	if(e.getButton() == MouseEvent.BUTTON3)
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
	public boolean checkMouseDoubleClicked(MouseEvent e)
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

	/* MOUSE EVENTS */

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if( checkMouseDoubleClicked(e) )
			this.processMouseDoubleClicked(e);
		else
			this.processMouseClicked(e);
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
		Cursor cursor = FormControlUtil.create3DCursor();
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

	public ICadViewBase getCadViewBase() {
		return v;
	}
	
}
