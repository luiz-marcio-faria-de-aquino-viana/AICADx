/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompModelPlanView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cad.utils.RubberbandUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.comp.CompDocumentProperty;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class CompModelPlanView extends CompView
{
//Private
	protected ICadViewBase v = null;
	
	protected GeomPoint2d currMousePosScr = null;
	protected double currMousePosZMcs = 0.0;

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
	
	//REALTIME_PAN_MODE
	//
	protected boolean bPanMode = false;
	//
	protected GeomPoint2d baseMousePosPanMcs = null;
	protected GeomPoint2d currMousePosPanMcs = null;
	
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
	
	//PROPERTY_VARS
	//
	protected CadEntity currSelEnt = null;
	protected GeomPoint2d currSelEntPointMcs = null;
	
	/* DRAW */
	
	//CLEARSCREEN
	//
    protected void clearScreen(ICadViewBase v, Graphics g) 
    {
    	Color oldcol = GeomUtil.setColor(g, AppDefs.BACKGROUNDCOLOR);
    	
    	int w = this.getWidth();
    	int h = this.getHeight();

    	g.fillRect(0, 0, w, h);

    	GeomUtil.setColor(g, oldcol);
	}
    
    //DRAWENTITIES
    //
    protected void drawEntities(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
    	
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	String str = "ScaleFactor: " + nf3.format(sclFact);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());
    	
    	CadBlockDef oBlockDef = this.doc.getCurrBlockDef();
    	int size = oBlockDef.getEntityTableSz();
        for(int i = 0; i < size; i++) {
        	CadEntity oEnt = oBlockDef.getEntityAt(i);
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 

			if( oEnt.isSelected() ) {
				oEnt.redraw2d(v, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, false, g);
			}
			oEnt.redraw2d(v, 0.0, ptBase2dMcs, pt2dMcs, sclFact, false, false, g);
			oEnt.osnap3d(v, this.osnapmode, pt2dMcs, g);
        }
    }
    
    //DRAWBLIP
	//
	protected void drawBlip(ICadViewBase v, Graphics g)
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
	
	//DRAWPROPS
	//
	protected void drawProps(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g)
	{
    	NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
    	
    	CadProjectDef projDef = this.doc.getCurrProjectDef();
    	double sclFact = projDef.getScaleFactor();

    	String str = "ScaleFactor: " + nf3.format(sclFact);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());

		if(this.currSelEnt != null) 
		{
        	if( this.currSelEnt.isDeleted() ) return;
        	if( !this.currSelEnt.isVisible() ) return; 

        	this.currSelEnt.redraw2d(v, 0.0, ptBase2dMcs, pt2dMcs, sclFact, false, true, g);
        }
	}
	
	//DISPLAYPROPS
	//
	protected void displayProps(CadEntity oEnt)
	{
		ArrayList<ItemDataVO> lsProperty = null;

		if(oEnt != null) {
			lsProperty = oEnt.toPropertyList();
		}
		else {
			lsProperty = this.doc.toPropertyList();
		}
		
		MainPanel mainPanel = MainPanel.getPanel();

		CompDocumentProperty oDocProp = mainPanel.getObjectProperty();
		oDocProp.updateObjectProperty(lsProperty);
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
	
	//DRAWGRID
	//
	protected void drawGrid(ICadViewBase v, Graphics g)
	{
		if(this.gridmode == AppDefs.GRIDMODE_OFF) return;

		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double d = v.fromMcsToScr(AppDefs.GRIDMODE_XSIZE);
		if(d < AppDefs.VIEWPREC_MIN) return;

    	String str1 = "GridSize: " + nf3.format(d);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str1, this.getClass());
		
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
	
	//DRAWCOORDS
	//
	protected void drawCoords(ICadViewBase v, Graphics g)
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
	
	//DRAWCURSOR
	//
	protected void drawCursor(ICadViewBase v, Graphics g)
	{
		if(this.currMousePosScr == null) return;

		Color oldcol = GeomUtil.setColor(g, AppDefs.CURSORCOLOR);		
		
		if(this.selectmode == AppDefs.SELECTMODE_SELECTOBJECT)
		{
			GeomPoint2d mousePorVideo = v.fromScrToVideo(this.currMousePosScr);
			
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
			GeomPoint2d mousePorVideo = v.fromScrToVideo(this.currMousePosScr);
			
			int xCursorVideo = (int)mousePorVideo.getX();
			int yCursorVideo = (int)mousePorVideo.getY();
	
			int w = this.getWidth();
			int h = this.getHeight();
		
			g.drawLine(0, yCursorVideo, w, yCursorVideo);
			g.drawLine(xCursorVideo, 0, xCursorVideo, h);
		}
		
		GeomUtil.setColor(g, oldcol);		
	}
	
	//DRAWCOORDS
	//
	protected void drawCoordsys(ICadViewBase v, Graphics g)
	{    	
    	CadProjectDef oProjectDef = this.doc.getCurrProjectDef();
    	double sclFact = oProjectDef.getScaleFactor();

    	CadLayerDef oLayer = oProjectDef.getLayer();

    	ColorVO c = oLayer.getColor();
    	
		Color oldcol = GeomUtil.setColor(g, c.getColor());

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		oProjectDef.redraw2d(v, 0.0, new GeomPoint2d(0.0, 0.0), new GeomPoint2d(0.0, 0.0), sclFact, false, false, g);
				
		GeomUtil.setColor(g, oldcol);
	}
		
//Public

    public CompModelPlanView(String name, int viewType, MainFrame frm, CadDocumentDef doc) {
		super();
		
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
	public void initCadView(double wScr, double hScr)
	{
		double ratioScr = hScr / wScr;

		//MCS
		double wMcs = AppDefs.MCSPLAN_XSIZE_MILI * AppDefs.MCSPLAN_SCALEFACTOR;
		double hMcs = wMcs * ratioScr;

		double xCenterMcs = wMcs / 2.0;
		double yCenterMcs = hMcs / 2.0;
		
		GeomPoint2d ptCenterMcs = new GeomPoint2d(xCenterMcs, yCenterMcs); 
		GeomPoint2d ptXDirMcs = new GeomPoint2d(xCenterMcs + 1.0, yCenterMcs);

		GeomVector2d xDirMcs = new GeomVector2d(ptCenterMcs, ptXDirMcs);

		/* *** */
		
		double ratioMcs = hMcs / wMcs;
		String str = String.format("initCadView: RarioScr:%s = RatioMcs:%s;", ratioScr, ratioMcs);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());

		/* *** */

		this.v = new CadViewPlan2d(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			wScr,
			hScr);
	}
	
	@Override
	public void resetCadView(double wScr, double hScr)
	{
		double ratioScr = hScr / wScr;
		
		//MCS
		GeomPlan2d planMcs = this.v.getPlanMcs2d();
		
		double wMcs = planMcs.getWidth();
		double hMcs = wMcs * ratioScr;
		
		GeomPoint2d ptCenterMcs = planMcs.getPtCenter();
		GeomVector2d xDirMcs = planMcs.getXDir();

		/* *** */
		
		double ratioMcs = hMcs / wMcs;
		String str = String.format("resetCadView: RarioScr:%s = RatioMcs:%s;", ratioScr, ratioMcs);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());

		/* *** */

		this.v.reset(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			wScr,
			hScr);
	}

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
    	this.selectobjtype = AppDefs.OBJTYPE_ANY;
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
				
		double dist = this.refPickpointMcs;
		
    	this.clearScreen(v, g);
		this.drawEntities(v, dist, ptBase2dMcs, pt2dMcs, bDragMode, g);
		this.drawProps(v, dist, ptBase2dMcs, pt2dMcs, bDragMode, g);
		this.drawBlip(v, g);
		this.drawRubberband(v, g);
		this.drawGrid(v, g);
		this.drawCoords(v, g);
		this.drawCursor(v, g);
		this.drawCoordsys(v, g);
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
		
		double dist = 0.0;
		
    	this.clearScreen(v, g);
		this.drawEntities(v, dist, ptBase2dMcs, pt2dMcs, bDragMode, g);

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
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 
        		
			ptResult = oEnt.osnap3d(v, this.osnapmode, pt2dMcs, null);
			if(ptResult != null) return ptResult;
        }
        return ptResult;
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
        	if( oEnt.isDeleted() ) continue;
        	if( !oEnt.isVisible() ) continue; 
        	
			if( (this.selectobjtype == AppDefs.OBJTYPE_ANY) || 
				(this.selectobjtype == oEnt.getObjType()) ) 
			{
				boolean bSelected = oEnt.select2d(v, pt2dMcs, sclFact, true);
				if( bSelected )
					return oEnt;
			}
        }
        return null;
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
				v.zoomScaleMcs(AppDefs.ZOOMMODE_ZOOMIN_SCALEFACT);
			}
			else {
				v.zoomScaleMcs(AppDefs.ZOOMMODE_ZOOMOUT_SCALEFACT);
			}

			this.lastMouseWheelTimeMili = dataHoraAtualMili;
			
			this.repaint();
		}
	}

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

	@Override
	public void processMouseClicked_SetCurrSelEnt(int xCursor, int yCursor)
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
    	
		this.currSelEntPointMcs = v.fromScrToMcs(ptScr);
		
		//ADD_BLIP
		//
		GeomPoint2d ptBlip = new GeomPoint2d(this.currSelEntPointMcs);
		this.lsBlipsMcs.add(ptBlip);

		this.currSelEnt = this.selectEntity(this.currSelEntPointMcs);
		if(this.currSelEnt != null) {
			this.currSelEnt.setSelected(false);			
			
			//SHOW_AT_COMMAND_PROMPT
			//
			PromptUtil.promptEntity(this.currSelEnt);
			this.displayProps(this.currSelEnt);
		}
	}
	
	//PROCESS: MOUSE_CLICKED_EVENT
	//	
	
	@Override
	public void processMouseClicked(int mouseButton, int modifiers, int xCursor, int yCursor)
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
			else
			{
				this.processMouseClicked_SetCurrSelEnt(xCursor, yCursor);				
			}
		}
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
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		int xCursor = e.getX();
		int yCursor = e.getY();

		double xp = xCursor - AppDefs.CANVAS_LOCATION_X;
		double yp = yCursor - AppDefs.CANVAS_LOCATION_Y;
		
		GeomPoint2d mousePosVideo0 = new GeomPoint2d(xp, yp);
		GeomPoint2d mousePosScr0 = v.fromVideoToScr(mousePosVideo0);
		GeomPoint2d mousePosMcs0 = v.fromScrToMcs(mousePosScr0);
				
		//SNAPMODE
		//
		GeomPoint2d mousePosMcs1 = new GeomPoint2d(mousePosMcs0);
		if(this.snapmode == AppDefs.SNAPMODE_ON) {
			mousePosMcs1 = v.toSnapPoint(mousePosMcs0);
		}
		
		//ORTHOMODE
		//
		GeomPoint2d mousePosMcs = new GeomPoint2d(mousePosMcs1);
		if(this.orthomode == AppDefs.ORTHOMODE_ON) {
			GeomPoint2d basePosMcs = this.getBasePosMcs();
			mousePosMcs = v.toOrthoPoint(basePosMcs, mousePosMcs1);
		}
		GeomPoint2d mousePosScr = v.fromMcsToScr(mousePosMcs);
		//GeomPoint2d mousePosVideo = v.fromScrToVideo(mousePosScr);		

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
					this.currMousePosScr = new GeomPoint2d(mousePosScr);
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
			this.currMousePosPanMcs = this.v.fromScrToMcs(this.currMousePosScr);
			
			this.v.zoomMoveMcs(this.baseMousePosPanMcs, this.currMousePosPanMcs);
			
			this.lastMousePosTimeMili = dataHoraAtualMili;
			
			this.repaint();
		}
	}

	//PROCESS: MOUSE_PRESSED_EVENT
	//	

	@Override
	public void processMousePressed(MouseEvent e)
	{
		if(this.v == null) return;
	
		int buttonId = e.getButton();
		if(buttonId == MouseEvent.BUTTON2) {
			this.bPanMode = true;
	
			double xCursor = e.getX();
			double yCursor = e.getY();

			double xVideo = xCursor - AppDefs.CANVAS_LOCATION_X;
			double yVideo = yCursor - AppDefs.CANVAS_LOCATION_Y;

			this.lastMousePosScr = this.currMousePosScr;
			
			GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
			this.currMousePosScr = this.v.fromVideoToScr(ptVideo);
			this.baseMousePosPanMcs = this.v.fromScrToMcs(this.currMousePosScr);
			this.currMousePosPanMcs = new GeomPoint2d(this.baseMousePosPanMcs);
		}
	}
	
	//PROCESS: MOUSE_RELEASED_EVENT
	//	

	@Override
	public void processMouseReleased(MouseEvent e)
	{
		if(this.v == null) return;
		
		int buttonId = e.getButton();
		if(buttonId == MouseEvent.BUTTON2) {
			this.bPanMode = false;

			double xCursor = e.getX();
			double yCursor = e.getY();

			double xVideo = xCursor - AppDefs.CANVAS_LOCATION_X;
			double yVideo = yCursor - AppDefs.CANVAS_LOCATION_Y;

			this.lastMousePosScr = this.currMousePosScr;
			
			GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
			this.currMousePosScr = this.v.fromVideoToScr(ptVideo);
			this.currMousePosPanMcs = this.v.fromScrToMcs(this.currMousePosScr);
			
			this.v.zoomMoveMcs(this.baseMousePosPanMcs, this.currMousePosPanMcs);
			
			this.repaint();
		}
	}

	/* MOUSE EVENTS */

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int mouseButton = e.getButton();
		
		int modifiers = e.getModifiers();
		
		int xCursor = e.getX();
		int yCursor = e.getY();

		int xp = xCursor - (int)AppDefs.CANVAS_LOCATION_X;
		int yp = yCursor - (int)AppDefs.CANVAS_LOCATION_Y;
		
		if( checkMouseDoubleClicked(mouseButton, modifiers, xp, yp) )
			this.processMouseDoubleClicked(mouseButton, modifiers, xp, yp);
		else
			this.processMouseClicked(mouseButton, modifiers, xp, yp);
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
		Cursor cursor = FormControlUtil.createBlankCursor();		
		this.setCursor(cursor);
		//this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		//MAIN_PANEL
		//
		MainPanel panel = this.parentFrame.getMainPanel();

		CompCommandPrompt c = panel.getCommandPrompt();
		c.setCommandPromptFocus(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Cursor cursor = Cursor.getDefaultCursor();
		this.setCursor(cursor);

		//MAIN_PANEL
		//
		MainPanel panel = this.parentFrame.getMainPanel();

		CompCommandPrompt c = panel.getCommandPrompt();
		c.setCommandPromptFocus(false);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.isControlDown()) {
			double rotationVal = e.getPreciseWheelRotation();
			this.processMouseWheel(rotationVal);
		}
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
		
		int eventType = e.getEventType();
		if(eventType == AppDefs.EVENTTYPE_CMDENTER) {		
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
		else {
			Double scaleVal = (Double)e.getEventData();
			if(scaleVal != null) {
				CadProjectDef projDef = this.doc.getCurrProjectDef();
				projDef.updateProjectScale(scaleVal);
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

	public double getRefPickpointMcs() {
		return refPickpointMcs;
	}

	public void setRefPickpointMcs(double refPickpointMcs) {
		this.refPickpointMcs = refPickpointMcs;
	}

	public double getCurrPickpointZMcs() {
		return currPickpointZMcs;
	}

	public void setCurrPickpointZMcs(double currPickpointZMcs) {
		this.currPickpointZMcs = currPickpointZMcs;
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
			//
			this.currPickpointZMcs = 0.0;
		}
		return null;
	}
	
	public ICadViewBase getCadViewBase()
	{
		return this.v;
	}

	public String getName() {
		return name;
	}

	public int getViewType() {
		return viewType;
	}
		
}
