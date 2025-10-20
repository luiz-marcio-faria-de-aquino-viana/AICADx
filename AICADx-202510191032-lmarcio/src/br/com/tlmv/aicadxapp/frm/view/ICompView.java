/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ICompView.java
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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
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
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cad.utils.RubberbandUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPiso;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public interface ICompView
{
//Public
	
    public void init(String name, int viewType, MainFrame frm, CadDocumentDef doc);

	public void initCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist);
	
	public void resetCadView(double wScr, double hScr, GeomPoint3d ptCentroid, double modelDist, double obsDist);			
    
    //public void initSampleData(int debugLevel);

	/* RESET_VARS */
    
    public void resetPickModeVars();
    
    public void resetSelectModeVars();
    
    public void resetZoomModeVars();

	/* SELECT */
    
	public CadEntity selectEntity(GeomPoint2d pt2dMcs);

    /* BLIPS */
    
    public void clearBlips();

    /* RESET ALL */
    
    public void resetAll();

    /* REPAINT ALL */
    
	public void repaintAll();
	
	/* PRINT VIEW */
	
	public void printView();
    
    /* Getters/Setters */

	public MainFrame getParent();

	public CadDocumentDef getDocument();

	public GeomPoint2d getCurrMousePosScr();

	public GeomPoint2d getLastMousePosScr();

	public int getPickmode();

	public void setPickmode(int pickmode);

	public GeomPoint2d getCurrPickpointMcs();
	
	public void setCurrPickpointMcs(GeomPoint2d currPickpointMcs);
	
	public double getCurrPickpointZMcs();
	
	public void setCurrPickpointZMcs(double currPickpointZMcs);

	//Base Pickpoint
	//
	public GeomPoint2d getBasePickpointMcs();

	public void setBasePickpointMcs(GeomPoint2d pickpointMcs);
	
	//Reference Pickpoint
	//
	public double getRefPickpointMcs();

	public void setRefPickpointMcs(double refPickpointMcs);
	
	//Direction Pickpoint
	//	
	public GeomVector2d getVDirPickpointMcs();

	public void setVDirPickpointMcs(GeomVector2d vDirPickpointMcs);

	//Center Pickpoint
	//
	public GeomPoint2d getCenterPickpointMcs();

	public void setCenterPickpointMcs(GeomPoint2d centerPickpointMcs);

	//Start Pickpoint
	//
	public GeomPoint2d getStartPickpointMcs();

	public void setStartPickpointMcs(GeomPoint2d startPickpointMcs);

	//End Pickpoint
	//
	public GeomPoint2d getEndPickpointMcs();

	public void setEndPickpointMcs(GeomPoint2d endPickpointMcs);

	//Point List Pickpoint
	//
	public ArrayList<GeomPoint2d> getLsPtsPickpointMcs();

	public void setLsPtsPickpointMcs(ArrayList<GeomPoint2d> lsPtsPickpointMcs);

	//Gridmode
	//
	public int getGridmode();

	public void setGridmode(int gridmode);
	
	//Snapmode
	//
	public int getSnapmode();

	public void setSnapmode(int snapmode);

	public double getSnapmodeXSize();

	public void setSnapmodeXSize(double snapmodeXSize);

	public double getSnapmodeYSize();

	public void setSnapmodeYSize(double snapmodeYSize);

	public GeomPoint2d getSnapmodeOriginMcs();

	public void setSnapmodeOriginMcs(GeomPoint2d snapmodeOriginMcs);

	//Orthomode
	//
	public int getOrthomode();

	public void setOrthomode(int orthomode);

	//Gridmode
	//
	public double getGridmodeXSize();

	public void setGridmodeXSize(double gridmodeXSize);

	public double getGridmodeYSize();

	public void setGridmodeYSize(double gridmodeYSize);

	public GeomPoint2d getGridmodeOriginMcs();

	public void setGridmodeOriginMcs(GeomPoint2d gridmodeOriginMcs);

	//Zoommode
	//
	public int getZoommode();

	public void setZoommode(int zoommode);
	
	public GeomPoint2d getBaseZoompointMcs();

	public void setBaseZoompointMcs(GeomPoint2d baseZoompointMcs);

	public GeomPoint2d getCurrZoompointMcs();
	
	//Selectmode
	//
	public int getSelectmode();

	public void setSelectmode(int selectmode);

	public int getSelectobjtype();

	public void setSelectobjtype(int selectobjtype);
	
	public GeomPoint2d getCurrSelectpointMcs();
	
	//Dragmode
	//
	public int getDragmode();

	public void setDragmode(int dragmode);
	
	//ICadViewBase
	//
	public ICadViewBase getCadViewBase();
	
	/* Getters/Setters */

	public String getName();
	
	public int getViewType();
	
}
