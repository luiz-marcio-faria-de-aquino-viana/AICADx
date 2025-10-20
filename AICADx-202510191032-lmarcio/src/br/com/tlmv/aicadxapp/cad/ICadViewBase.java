/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ICadViewBase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public interface ICadViewBase 
{
//Public

	// 2D-VIEW
	//
	public void init(
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr);
	
	public void init(
		GeomPlan2d planMcs,
		GeomPlan2d planScr);
	
	public void init(
		GeomPlan3d planMcs,
		GeomPlan2d planScr);

	public void init(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr);
	
	// 3D-VIEW
	//	
	public void init(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr,
		GeomPoint3d ptObsMcs,
		GeomVector3d vObsDirMcs,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius);

	public void init(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius);
	
	/* RESET */
	
	public void reset(
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr);
	
	public void reset(
		GeomPlan2d planMcs,
		GeomPlan2d planScr);
	
	public void reset(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr,
		GeomPoint3d ptObsMcs,
		GeomVector3d vObsDirMcs);

	public void reset(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr);
	
	public void reset(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr);
	
	public void reset(
		GeomPlan3d planMcs,
		GeomPlan2d planScr);
	
	/* CAMERA REFERENCES */

	public void setCameraReference(GeomPoint3d ptCentroid, double modelDist, double obsDist);
	
	/* RE-CENTER */
	
	public void moveToCenter(GeomPoint3d ptNewCenterMcs);
	
	/* OPERATIONS */

	// MCS_to_PROJ CONVERTION
	
	public GeomPoint3d fromMcsToProj(GeomPoint3d pt3dMcs);
	
	public GeomVector3d fromProjToMcs(GeomPoint3d ptProj2d);
	
	public GeomPoint3d fromProjToMcs(GeomPoint3d ptProj3d, double zp);

	// MCS_to_SCR CONVERTION
	
	public double fromMcsToScr(double val);
	
	public double fromScrToMcs(double val);
	
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs);
	
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs, GeomPoint2d ptBaseMcs, double rotate);
	
	public GeomPoint2d fromScrToMcs(GeomPoint2d ptScr);
	
	// PROJ_to_SCR CONVERTION
	
	public double fromProjToScr(double val);
	
	public double fromScrToProj(double val);
	
	public GeomPoint2d fromProjToScr(GeomPoint3d ptProj);

	public GeomPoint3d fromScrToProj(GeomPoint2d ptScr);
	
	// SCR_to_VIDEO CONVERTION
	
	public GeomPoint2d fromScrToVideo(GeomPoint2d ptScr);
	
	public GeomPoint2d fromVideoToScr(GeomPoint2d ptVideo);
	
	/* SNAP and ORTHO */
	
	public GeomPoint2d toSnapPoint(GeomPoint2d ptSrcMcs);
	
	public GeomPoint2d toOrthoPoint(GeomPoint2d ptBaseMcs, GeomPoint2d ptSrcMcs);
	
	/* SAVE and LOAD */
	
	public void saveView();
	
	public void loadView();
	
	/* PAN and ZOOM */
	
	public void moveToMcs(GeomVector3d vDirMcs, double distMcs);
	
	public void moveToMcs(GeomVector2d vDirMcs, double distMcs);
	
	public void moveToMcs(GeomPoint3d ptNewCenterMcs);
	
	public void moveToMcs(GeomPoint2d ptNewCenterMcs);

	public void resizeToMcs(double w, double h);

	public void resizeToScr(double w, double h);

	public void zoomScaleMcs(double sclFactor);
	
	public void zoomMoveMcs(GeomPoint2d baseMousePosPanMcs, GeomPoint2d currMousePosPanMcs);

	public void zoomCenterMcs(GeomPoint3d ptNewCenterMcs, double sclFactor);

	public void zoomCenterMcs(GeomPoint2d ptNewCenterMcs, double sclFactor);

	public void zoomWindowMcs(GeomPoint3d ptMinMcs, GeomPoint3d ptMaxMcs);
	
	public void zoomWindowMcs(GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs);
	
	public void zoomExtMcs();
	
	public void zoomAllMcs(CadBlockDef blk);
	
	public void zoomAllMcs(ArrayList<CadEntity> lsEntities);
	
	public void zoomAllItemDataMcs(ArrayList<ItemDataVO> lsItemData);
	
	public void zoomToCenterMcs(CadBlockDef blk);
	
	public void zoomToCenterMcs(ArrayList<CadEntity> lsEntities);
	
	public void zoomToCenterItemDataMcs(ArrayList<ItemDataVO> lsItemData);
	
	/* 3D-VIEW */

	public void zoomViewTopMcs(CadDocumentDef doc);

	public void zoomViewBottomMcs(CadDocumentDef doc);

	public void zoomViewFrontMcs(CadDocumentDef doc);

	public void zoomViewBackMcs(CadDocumentDef doc);

	public void zoomViewLeftMcs(CadDocumentDef doc);
	
	public void zoomViewRightMcs(CadDocumentDef doc);

	/* 3D-OBSERVER */
	
	public void zoomMoveForwardBackwardMcs(double dist);

	public void zoomRotateUpDownDegrees(double angleDegrees);        		    		        		
	
	public void zoomRotateLeftRightDegrees(double angleDegrees);        		    		        		

	/* Getters/Setters */

	public String getDetailLevel();

	public void setDetailLevel(String detailLevel);
	
	//LIMITS MCS (0-INICIAL)
	public GeomRect2d getLimitsMcs02d();

	public GeomRect3d getLimitsMcs03d();
	
	//OBSERVER MCS
	
	//PLAN MCS (0-INICIAL)
	public GeomPlan2d getPlanMcs02d();

	public GeomPlan3d getPlanMcs03d();
	
	//OBSERVER MCS (0-INICIAL)
	public GeomObserver3d getObsMcs0();

	//LIMITS MCS
	public GeomRect2d getLimitsMcs2d();

	public GeomRect3d getLimitsMcs3d();

	//PLAN MCS
	public GeomPlan2d getPlanMcs2d();

	public GeomPlan3d getPlanMcs3d();
	
	//OBSERVER MCS
	public GeomObserver3d getObsMcs();

	//PLAN PROJ
	public GeomPlan3d getPlanProj();
	
	//LIMITS PROJ
	public GeomRect3d getLimitsProj();

	//PLAN SCR
	public GeomPlan2d getPlanScr();

	//LIMITS SCR
	public GeomRect2d getLimitsScr();
	
	//SCALE_PROJ = PROJ / MCS
	public double getScaleProj();

	//SCALE_SCR = SCR / PROJ
	public double getScaleScr();

}
