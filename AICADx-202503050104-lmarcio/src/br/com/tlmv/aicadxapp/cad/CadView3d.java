/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadView3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;

public class CadView3d implements ICadViewBase
{
//Private
	//InitialView
	private GeomPlan3d planMcs0;
	//InitialLimits
	private GeomRect3d limitsMcs0;
	//InitialObserver
	private GeomObserver3d obsMcs0;
	
	//CurrView
	private GeomPlan3d planMcs;
	//CurrLimits
	private GeomRect3d limitsMcs;
	//CurrObserver
	private GeomObserver3d obsMcs;
	
	//ProjView
	private GeomPlan3d planProj;
	//ProjLimits
	private GeomRect3d limitsProj;
	//ScaleProj
	private double scaleProj;			// SCALE_PROJ = WIDTH_PROJ / WIDTH_MCS
	
	//ScreenView
	private GeomPlan2d planScr;
	//ScreenScale
	private double scaleScr;			// SCALE_SCR = WIDTH_SCR / WIDTH_PROJ
	
	/* Methodes */
	
	private void adjustPlanProj()
	{
		double distMcs = AppDefs.PROJPLAN_OBSERVER_DIST;
		
		GeomPlan3d planMcs3d = this.planMcs;
		
		double width = planMcs3d.getWidth();
		double height = planMcs3d.getHeight();
		double zHeight = planMcs3d.getZHeight();
		
		this.planProj = obsMcs.getPlanFrom(distMcs, width, height, zHeight);
	}
	
	private void adjustView()
	{
		double ratioScr = this.planScr.getRatio();
		
		this.planMcs.resetRatio(ratioScr);		
		this.planProj.resetRatio(ratioScr);
		
		this.scaleProj = this.planProj.getWidth() / this.planMcs.getWidth();
		this.scaleScr = this.planScr.getWidth() / this.planProj.getWidth();
	}
	
//Public

	public CadView3d(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr,
		GeomPoint3d ptObsMcs,
		GeomVector3d vObsDirMcs)
	{
		this.init(
			ptCenterMcs,
			xDirMcs,
			widthMcs,
			heightMcs,
			zHeightMcs,
			widthScr,
			heightScr,
			ptObsMcs,
			vObsDirMcs);
	}

	public CadView3d(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr)
	{
		this.init(
			planMcs,
			obsMcs,
			planScr);
	}
	
	/* Methodes */
	
	@Override
	public void init(GeomPoint2d ptCenterMcs, GeomVector2d xDirMcs, double widthMcs, double heightMcs, double widthScr, double heightScr) { }

	@Override
	public void init(GeomPlan2d planMcs, GeomPlan2d planScr) { }

	@Override
	public void init(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr,
		GeomPoint3d ptObsMcs,
		GeomVector3d vObsDirMcs)
	{
		//PLAN_MCS
		//
		GeomPlan3d planMcs = new GeomPlan3d(ptCenterMcs, xDirMcs, widthMcs, heightMcs, zHeightMcs);

		//PLAN_SCR
		//
		double xCenterScr = widthScr / 2.0;
		double yCenterScr = heightScr / 2.0;
		
		GeomPoint2d ptCenterScr = new GeomPoint2d(xCenterScr, yCenterScr);
		GeomPoint2d ptXDirScr = new GeomPoint2d(xCenterScr + 1.0, yCenterScr);
		
		GeomVector2d xDirScr = new GeomVector2d(ptCenterScr, ptXDirScr);
		GeomPlan2d planScr = new GeomPlan2d(ptCenterScr, xDirScr, widthScr, heightScr);

		//OBSERVER_MCS
		//
		GeomObserver3d obsMcs = new GeomObserver3d(ptObsMcs, vObsDirMcs);
		
		this.init(planMcs, obsMcs, planScr);
	}
	
	@Override
	public void init(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;
		//
		this.limitsMcs = new GeomRect3d(
			this.planMcs.getPtLowerLeftCorner(),
			this.planMcs.getPtUpperRightCorner() );
		//
		this.obsMcs = obsMcs;
		//
		this.adjustPlanProj();
		this.adjustView();
		this.saveView();
	}

	@Override
	public void init(
		GeomPoint3d ptCenterMcs, 
		GeomVector3d xDirMcs, 
		double widthMcs, 
		double heightMcs,
		double zHeightMcs, 
		double widthScr, 
		double heightScr) { }

	@Override
	public void init(GeomPlan3d planMcs, GeomPlan2d planScr) { }

	/* RESET */

	@Override
	public void reset(GeomPoint2d ptCenterMcs, GeomVector2d xDirMcs, double widthMcs, double heightMcs, double widthScr, double heightScr) { }
	
	@Override
	public void reset(GeomPlan2d planMcs, GeomPlan2d planScr) { }
	
	@Override
	public void reset(
		GeomPoint3d ptCenterMcs,
		GeomVector3d xDirMcs,
		double widthMcs,
		double heightMcs,
		double zHeightMcs,
		double widthScr,
		double heightScr,
		GeomPoint3d ptObsMcs,
		GeomVector3d vObsDirMcs)
	{
		//PLAN_MCS
		//
		GeomPlan3d planMcs = new GeomPlan3d(ptCenterMcs, xDirMcs, widthMcs, heightMcs, zHeightMcs);

		//PLAN_SCR
		//
		double xCenterScr = widthScr / 2.0;
		double yCenterScr = heightScr / 2.0;
		
		GeomPoint2d ptCenterScr = new GeomPoint2d(xCenterScr, yCenterScr);
		GeomPoint2d ptXDirScr = new GeomPoint2d(xCenterScr + 1.0, yCenterScr);
		
		GeomVector2d xDirScr = new GeomVector2d(ptCenterScr, ptXDirScr);
		GeomPlan2d planScr = new GeomPlan2d(ptCenterScr, xDirScr, widthScr, heightScr);

		//OBSERVER_MCS
		//
		GeomObserver3d obsMcs = new GeomObserver3d(ptObsMcs, vObsDirMcs);
		
		this.init(planMcs, obsMcs, planScr);
	}
	
	@Override
	public void reset(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;
		//
		//this.limitsMcs = new GeomRect3d(
		//	this.planMcs.getPtLowerLeftCorner(),
		//	this.planMcs.getPtUpperRightCorner() );
		//
		this.obsMcs = obsMcs;
		//
		this.adjustPlanProj();
		this.adjustView();
		this.saveView();
	}

	@Override
	public void reset(
		GeomPoint3d ptCenterMcs, 
		GeomVector3d xDirMcs, 
		double widthMcs, 
		double heightMcs,
		double zHeightMcs, 
		double widthScr, 
		double heightScr) { }

	@Override
	public void reset(GeomPlan3d planMcs, GeomPlan2d planScr) { }

	/* OPERATIONS */

	// MCS_to_PROJ CONVERTION
	
	public GeomPoint3d fromMcsToProj(GeomPoint3d pt3dMcs)
	{
		GeomPoint3d ptObserver3d = this.obsMcs.getPtObserver();
		GeomVector3d uDir3d = this.obsMcs.getUnitDir();

		GeomVector3d vObsToPt3d = new GeomVector3d(ptObserver3d, pt3dMcs);						// V_OBS_TO_PTMCS
		GeomVector3d uObsToPt3d = vObsToPt3d.otherUnit();

		double distObsToPt3d = vObsToPt3d.mod();												// DIST_OBS_TO_PTMCS
		if(distObsToPt3d < AppDefs.MATHPREC_MIN)
			distObsToPt3d = AppDefs.MATHPREC_MIN;
		
		double distDirObsToPt3d = uDir3d.dotProd(vObsToPt3d);									// DIST_DIROBS (PTMCS)
		if(distDirObsToPt3d < AppDefs.MATHPREC_MIN)
			distDirObsToPt3d = AppDefs.MATHPREC_MIN;

		double scale = 1.0 / distDirObsToPt3d;													// SCALE_MCS_TO_PROJ

		double dist = distObsToPt3d * scale;
		
		GeomPoint3d pt3dProj = ptObserver3d.otherMoveTo(uObsToPt3d, dist);

		GeomPoint3d ptCenterProj = this.planProj.getPtCenter();
		
		GeomVector3d axisX = this.planProj.getAxisX();
		GeomVector3d axisY = this.planProj.getAxisY();
		
		GeomVector3d vCenterToPt3dProj = new GeomVector3d(ptCenterProj, pt3dProj);  
		
		double xProj = - axisX.dotProd(vCenterToPt3dProj);
		double yProj = axisY.dotProd(vCenterToPt3dProj);
		
		GeomPoint3d ptProj = new GeomPoint3d(xProj, yProj, 0.0);		
		return ptProj;
	}
	
	public GeomVector3d fromProjToMcs(GeomPoint3d ptProj3d)
	{
		GeomPoint3d ptObserver3d = this.obsMcs.getPtObserver();
		
		GeomPoint3d ptCenterProj = this.planProj.getPtCenter();

		GeomVector3d axisX = this.planProj.getAxisX();
		GeomVector3d axisY = this.planProj.getAxisY();

		double xProj = - ptProj3d.getX();
		double yProj = ptProj3d.getY();
		
		GeomPoint3d ptAuxProj = ptCenterProj.otherMoveTo(axisX, xProj);
		GeomPoint3d pt3dProj = ptAuxProj.otherMoveTo(axisY, yProj);
				
		GeomVector3d vObsToPt3dProj = new GeomVector3d(ptObserver3d, pt3dProj);
		GeomVector3d uObsToPt3dProj = vObsToPt3dProj.otherUnit();
		return uObsToPt3dProj;
	}
	
	// MCS_to_SCR CONVERTION
	
	public double fromMcsToScr(double val) {
		return 0;
	}

	public double fromScrToMcs(double val) {
		return 0;
	}

	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs) {
		return null;
	}

	public GeomPoint2d fromScrToMcs(GeomPoint2d ptScr) {
		return null;
	}
	
	// PROJ_to_SCR CONVERTION
	
	public double fromProjToScr(double val) {
		double valScr = val * this.scaleScr;
		return valScr;
	}

	public double fromScrToProj(double val) {
		double valScr = val / this.scaleScr;
		return valScr;
	}

	public GeomPoint2d fromProjToScr(GeomPoint3d ptProj) {
		GeomPoint2d ptCenterScr = this.planScr.getPtCenter();
		
		double xScr = ptCenterScr.getX() + (ptProj.getX() * scaleScr);
		double yScr = ptCenterScr.getY() + (ptProj.getY() * scaleScr);

		GeomPoint2d ptScr = new GeomPoint2d(xScr, yScr);		
		return ptScr;
	}

	public GeomPoint3d fromScrToProj(GeomPoint2d ptScr) {
		GeomPoint3d ptCenterProj = this.planProj.getPtCenter();
		
		double xProj = ptCenterProj.getX() + (ptScr.getX() / scaleScr);
		double yProj = ptCenterProj.getY() + (ptScr.getY() / scaleScr);
		double zProj = ptCenterProj.getZ();

		GeomPoint3d ptProj = new GeomPoint3d(xProj, yProj, zProj);		
		return ptProj;
	}
	
	// SCR_to_VIDEO CONVERTION
	
	public GeomPoint2d fromScrToVideo(GeomPoint2d ptScr)
	{
		double xScr = ptScr.getX();
		double yScr = ptScr.getY();

		double xVideo = xScr;
		double yVideo = this.planScr.getHeight() - yScr;
		
		GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
		return ptVideo;
	}
	
	public GeomPoint2d fromVideoToScr(GeomPoint2d ptVideo)
	{
		double xVideo = ptVideo.getX();
		double yVideo = ptVideo.getY();

		double xScr = xVideo;
		double yScr = this.planScr.getHeight() - yVideo;
		
		GeomPoint2d ptScr = new GeomPoint2d(xScr, yScr);
		return ptScr;
	}
	
	/* SNAP and ORTHO */
	
	public GeomPoint2d toSnapPoint(GeomPoint2d ptSrcMcs)
	{
		double xSrcMcs = ptSrcMcs.getX();
		double ySrcMcs = ptSrcMcs.getY();

		double nx = Math.floor( (xSrcMcs - AppDefs.SNAPMODE_ORIGIN.getX()) / AppDefs.SNAPMODE_XSIZE );
		double ny = Math.floor( (ySrcMcs - AppDefs.SNAPMODE_ORIGIN.getY()) / AppDefs.SNAPMODE_YSIZE );
		
		double xDstMcs = nx * AppDefs.SNAPMODE_XSIZE; 
		double yDstMcs = ny * AppDefs.SNAPMODE_YSIZE;
		
		GeomPoint2d ptResult = new GeomPoint2d(xDstMcs, yDstMcs);
		return ptResult;
	}
	
	public GeomPoint2d toOrthoPoint(GeomPoint2d ptBaseMcs, GeomPoint2d ptSrcMcs)
	{
		GeomPoint2d ptResult = new GeomPoint2d(ptSrcMcs);
		if(ptBaseMcs == null) return ptResult;

		double xBaseMcs = ptBaseMcs.getX(); 
		double yBaseMcs = ptBaseMcs.getY(); 
		
		double xSrcMcs = ptSrcMcs.getX();
		double ySrcMcs = ptSrcMcs.getY();
		
		double dX = Math.abs( xSrcMcs - xBaseMcs );
		double dY = Math.abs( ySrcMcs - yBaseMcs );

		if(dX >= dY) {
			//ORTHO-X
			ptResult = new GeomPoint2d(xSrcMcs, yBaseMcs);			
		}
		else {
			//ORTHO-Y
			ptResult = new GeomPoint2d(xBaseMcs, ySrcMcs);			
		}
		return ptResult;
	}
	
	/* SAVE and LOAD */
	
	public void saveView()
	{
		this.planMcs0 = new GeomPlan3d(this.planMcs);
		this.limitsMcs0 = new GeomRect3d(this.limitsMcs);
		this.obsMcs0 = new GeomObserver3d(this.obsMcs);
	}
	
	public void loadView()
	{
		this.planMcs = new GeomPlan3d(this.planMcs0);
		this.limitsMcs = new GeomRect3d(this.limitsMcs0);
		this.obsMcs = new GeomObserver3d(this.obsMcs0);
	}
	
	/* PAN and ZOOM */
	
	public void moveToMcs(GeomVector3d vDirMcs, double distMcs)
	{
		GeomPoint3d ptNewCenterMcs = this.planMcs0.getPtCenter();
		ptNewCenterMcs.selfMoveTo(vDirMcs, distMcs);
		
		this.planMcs.resetOrigin(ptNewCenterMcs);
	}
	
	public void moveToMcs(GeomVector2d vDirMcs, double distMcs) { }

	public void moveToMcs(GeomPoint3d ptNewCenterMcs)
	{
		GeomPoint3d ptOldCenterMcs = this.planMcs.getPtCenter();
		
		GeomVector3d vDirMcs = new GeomVector3d(ptOldCenterMcs, ptNewCenterMcs);
		double distMcs = vDirMcs.mod();
		
		this.moveToMcs(vDirMcs, distMcs);
	}

	public void moveToMcs(GeomPoint2d ptNewCenterMcs) { }
	
	public void resizeToMcs(double w, double h)
	{
		this.planMcs.resetDimention(w, h);
		this.adjustView();
	}

	public void resizeToScr(double w, double h)
	{
		this.planScr.resetDimention(w, h);
		this.adjustView();
	}

	public void zoomScaleMcs(double sclFactor)
	{
		double w = this.planMcs.getWidth() * sclFactor;
		double h = this.planMcs.getHeight() * sclFactor;
		
		this.planMcs.resetDimention(w, h);
		this.adjustView();
	}

	public void zoomCenterMcs(GeomPoint3d ptNewCenterMcs, double sclFactor)
	{
		this.moveToMcs(ptNewCenterMcs);
		this.zoomScaleMcs(sclFactor);
	}

	public void zoomCenterMcs(GeomPoint2d ptNewCenterMcs, double sclFactor) { }

	public void zoomWindowMcs(GeomPoint3d ptMinMcs, GeomPoint3d ptMaxMcs)
	{
		GeomPoint2d ptMinMcs2d = new GeomPoint2d(ptMinMcs);
		GeomPoint2d ptMaxMcs2d = new GeomPoint2d(ptMaxMcs);
		
		GeomPoint2d ptNewCenterMcs2d = GeomUtil.midPointOf(ptMinMcs2d, ptMaxMcs2d);
		double xNewCenterMcs2d = ptNewCenterMcs2d.getX();
		double yNewCenterMcs2d = ptNewCenterMcs2d.getY();
		
		GeomPoint3d ptNewCenterMcs3d = new GeomPoint3d(xNewCenterMcs2d, yNewCenterMcs2d, 0.0);
		
		double xMinMcs = ptMinMcs.getX();
		double yMinMcs = ptMinMcs.getY();
		
		double xMaxMcs = ptMaxMcs.getX();
		double yMaxMcs = ptMaxMcs.getY();
		
		double w = xMaxMcs - xMinMcs;
		double h = yMaxMcs - yMinMcs;
		
		this.moveToMcs(ptNewCenterMcs3d);
		this.resizeToMcs(w, h);
	}

	public void zoomWindowMcs(GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs) { }
	
	public void zoomExtMcs()
	{
		this.loadView();
		this.adjustView();
	}

	/* 3D-OBSERVER */
	
	public void zoomMoveForwardBackwardMcs(double dist) 
	{ 
		this.obsMcs.moveForwardBackwardMcs(dist);
		this.init(planMcs, obsMcs, planScr);
	}
	
	public void zoomTurnLeftRightDegrees(double angleDegrees) 
	{ 
		this.obsMcs.turnLeftRightDegrees(angleDegrees);
		this.init(planMcs, obsMcs, planScr);
	}
	
	/* Getters/Setters */

	//PLAN MCS (0-INICIAL)

	@Override
	public GeomPlan2d getPlanMcs02d() {
		GeomPlan2d planMcs02d = new GeomPlan2d(this.planMcs0);
		return planMcs02d;
	}

	@Override
	public GeomPlan3d getPlanMcs03d() {
		return planMcs0;
	}

	//LIMITS MCS (0-INICIAL)

	@Override
	public GeomRect2d getLimitsMcs02d() {
		GeomRect2d rect = new GeomRect2d(limitsMcs0);
		return rect;
	}

	@Override
	public GeomRect3d getLimitsMcs03d() {
		return limitsMcs0;
	}

	//OBSERVER MCS (0-INICIAL)
	
	@Override
	public GeomObserver3d getObsMcs0() {
		return obsMcs0;
	}
	
	//PLAN MCS

	@Override
	public GeomPlan2d getPlanMcs2d() {
		GeomPlan2d planMcs2d = new GeomPlan2d(this.planMcs);
 		return planMcs2d;
	}

	@Override
	public GeomPlan3d getPlanMcs3d() {
 		return planMcs;
	}

	//LIMITS

	@Override
	public GeomRect2d getLimitsMcs2d() {
		GeomRect2d rect = new GeomRect2d(limitsMcs);
		return rect;
	}

	@Override
	public GeomRect3d getLimitsMcs3d() {
		return limitsMcs;
	}

	//OBSERVER MCS
	
	@Override
	public GeomObserver3d getObsMcs() {
		return obsMcs;
	}

	//PLAN PROJ

	@Override
	public GeomPlan3d getPlanProj() {
		return planProj;
	}

	//PLAN SCR
	
	@Override
	public GeomPlan2d getPlanScr() {
		return planScr;
	}
	
	//SCALE_PROJ = PROJ / MCS
	
	@Override
	public double getScaleProj() {
		return scaleProj;
	}

	//SCALE_SCR = SCR / PROJ
	
	@Override
	public double getScaleScr() {
		return scaleScr;
	}

}
