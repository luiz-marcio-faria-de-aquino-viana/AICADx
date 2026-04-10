/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.frm.MainFrame;

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

    /* IS_OBJTYPEOF */

	public boolean isObjtypeOf(int[] arrSelectobjtype, int selectObjtype);

	/* SELECT */
    
	public CadEntity selectEntity(GeomPoint2d pt2dMcs);

	public CadEntity selectEntity(CadBlockDef blkDef, GeomPoint2d pt2dMcs);
    
	//

	public CadEntity selectEntity(int objtype, GeomPoint2d pt2dMcs);
	
	public CadEntity selectEntity(CadBlockDef blkDef, int objtype, GeomPoint2d pt2dMcs);
    
	//

	public CadEntity selectEntity(int[] objtype, GeomPoint2d pt2dMcs);
	
	public CadEntity selectEntity(CadBlockDef blkDef, int[] objtype, GeomPoint2d pt2dMcs);

	/* SELECT OPTIONS */
    
	public CadEntity selectFirst(int objtype, boolean bOnlyUserEntities);

	public CadEntity selectLast(int objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectAll(int objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectWindow(int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectCrossing(int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectFence(int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectPrevious(int objtype, boolean bOnlyUserEntities);

	//
	
	public CadEntity selectFirst(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities);

	public CadEntity selectLast(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectAll(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectWindow(CadBlockDef blkDef, int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectCrossing(CadBlockDef blkDef, int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectFence(CadBlockDef blkDef, int objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectPrevious(CadBlockDef blkDef, int objtype, boolean bOnlyUserEntities);

	//
	
	public CadEntity selectFirst(int[] objtype, boolean bOnlyUserEntities);

	public CadEntity selectLast(int[] objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectAll(int[] objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectWindow(int[] objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectCrossing(int[] objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectFence(int[] objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectPrevious(int[] objtype, boolean bOnlyUserEntities);

	//
	
	public CadEntity selectFirst(CadBlockDef blkDef, int[] objtype, boolean bOnlyUserEntities);

	public CadEntity selectLast(CadBlockDef blkDef, int[] objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectAll(CadBlockDef blkDef, int[] objtype, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectWindow(CadBlockDef blkDef, int[] objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectCrossing(CadBlockDef blkDef, int[] objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectFence(CadBlockDef blkDef, int[] objtype, GeomPoint2d ptMin, GeomPoint2d ptMax, boolean bOnlyUserEntities);

	public ArrayList<CadEntity> selectPrevious(CadBlockDef blkDef, int[] objtype, boolean bOnlyUserEntities);

    /* BLIPS */
    
    public void clearBlips();

    /* RESET ALL */

    public void resetAll();

    /* REPAINT ALL */
    
	public void repaintAll();
	
	/* PRINT VIEW */
	
	public void printView();

	/* SMP */
	
	//DRAW
	//
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
		boolean bDrawTooltip ); 

	public void drawEntitiesNoSMP(
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

	public void drawEntitiesSMP(
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
	
	public void drawEntitiesWorkerSMP(
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

	//OSNAP
	//
    public GeomPoint3d processOsnap(ICadViewBase v, GeomPoint2d pt2dMcs);
	
    public GeomPoint3d processOsnapNoSMP(ICadViewBase v, CadBlockDef blkDef, GeomPoint2d pt2dMcs, double sclFact);
	
    public GeomPoint3d processOsnapSMP(ICadViewBase v, CadBlockDef blkDef, GeomPoint2d pt2dMcs, double sclFact);
	
	public GeomPoint3d processOsnapWorkerSMP(ICadViewBase v, CadBlockDef blkDef, GeomPoint2d pt2dMcs, double sclFact, int startPos, int endPos, CadEntity[] arrEntities); 
	
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

	public void setOtherBasePickpointMcs(GeomPoint2d pickpointMcs);
	
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

	//Attrmode
	//
	public int getAttrmode();

	public void setAttrmode(int attrmode);

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

	public int[] getArrSelectObjtype();

	public void setArrSelectObjtype(int[] arrSelectobjtype);
	
	public GeomPoint2d getCurrSelectpointMcs();
	
	//Dragmode
	//
	public int getDragmode();

	public void setDragmode(int dragmode);
	
	//ICadViewBase
	//
	public ICadViewBase getCadViewBase();
	
	//SelectionSet
	//
	public Hashtable getPreviousSelectionSet();
	public void setPreviousSelectionSet(Hashtable currSelectionSet);
	
	/* Getters/Setters */

	public String getName();
	
	public int getViewType();

	public boolean isForceRefresh();

	public void setForceRefresh(boolean forceRefresh);
	
}
