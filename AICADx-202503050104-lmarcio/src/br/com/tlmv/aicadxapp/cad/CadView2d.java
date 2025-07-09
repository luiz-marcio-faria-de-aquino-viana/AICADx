/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadView2d.java
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

public class CadView2d implements ICadViewBase 
{
//Private
	//InitialView
	private GeomPlan2d planMcs0;
	//InitialLimits
	private GeomRect2d limitsMcs0;
	
	//CurrentView
	private GeomPlan2d planMcs;
	//CurrentLimits
	private GeomRect2d limitsMcs;

	//CurrentScreen
	private GeomPlan2d planScr;
	private double scaleScr;		// SCALE = WIDTH_SCR / WIDTH_MCS
	
	/* Methodes */
	
	private void adjustView()
	{
		double ratioScr = this.planScr.getRatio();
		this.planMcs.resetRatio(ratioScr);
		
		this.scaleScr = this.planScr.getWidth() / this.planMcs.getWidth();
	}
	
//Public

	public CadView2d(
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr)
	{
		this.init(
			ptCenterMcs,
			xDirMcs,
			widthMcs,
			heightMcs,
			widthScr,
			heightScr);
	}
		
	public CadView2d(
		GeomPlan2d planMcs,
		GeomPlan2d planScr)
	{
		this.init(
			planMcs,
			planScr);
	}
	
	/* Methodes */
	
	@Override
	public void init(
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr)
	{
		GeomPlan2d planMcs = new GeomPlan2d(ptCenterMcs, xDirMcs, widthMcs, heightMcs);

		double xCenterScr = widthScr / 2.0;
		double yCenterScr = heightScr / 2.0;
		
		GeomPoint2d ptCenterScr = new GeomPoint2d(xCenterScr, yCenterScr);
		GeomPoint2d ptXDirScr = new GeomPoint2d(xCenterScr + 1.0, yCenterScr);
		
		GeomVector2d xDirScr = new GeomVector2d(ptCenterScr, ptXDirScr);		
		GeomPlan2d planScr = new GeomPlan2d(ptCenterScr, xDirScr, widthScr, heightScr);

		this.init(planMcs, planScr);
	}
	
	@Override
	public void init(
		GeomPlan2d planMcs,
		GeomPlan2d planScr)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;

		this.limitsMcs = new GeomRect2d(
			this.planMcs.getPtLowerLeftCorner(),
			this.planMcs.getPtUpperRightCorner() );
		
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
		double heightScr, 
		GeomPoint3d ptObsMcs, 
		GeomVector3d vObsDirMcs) { }

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
	public void init(GeomPlan3d planMcs, GeomObserver3d obsMcs, GeomPlan2d planScr) { }

	@Override
	public void init(GeomPlan3d planMcs, GeomPlan2d planScr) { }
	
	/* RESET */
	
	@Override
	public void reset(
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr)
	{
		GeomPlan2d planMcs = new GeomPlan2d(ptCenterMcs, xDirMcs, widthMcs, heightMcs);

		double xCenterScr = widthScr / 2.0;
		double yCenterScr = heightScr / 2.0;
		
		GeomPoint2d ptCenterScr = new GeomPoint2d(xCenterScr, yCenterScr);
		GeomPoint2d ptXDirScr = new GeomPoint2d(xCenterScr + 1.0, yCenterScr);
		
		GeomVector2d xDirScr = new GeomVector2d(ptCenterScr, ptXDirScr);		
		GeomPlan2d planScr = new GeomPlan2d(ptCenterScr, xDirScr, widthScr, heightScr);

		this.init(planMcs, planScr);
	}
	
	@Override
	public void reset(
		GeomPlan2d planMcs,
		GeomPlan2d planScr)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;

		//this.limitsMcs = new GeomRect2d(
		//	this.planMcs.getPtLowerLeftCorner(),
		//	this.planMcs.getPtUpperRightCorner() );
		
		this.adjustView();

		//GeomPoint2d ptScr = planScr.getPtCenter(); 
		//GeomPoint2d ptMcs = this.fromScrToMcs(ptScr); 
		//this.moveToMcs(ptMcs);
		
		//this.zoomScaleMcs(2.0);
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
		double heightScr, 
		GeomPoint3d ptObsMcs, 
		GeomVector3d vObsDirMcs) { }

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
	public void reset(GeomPlan3d planMcs, GeomObserver3d obsMcs, GeomPlan2d planScr) { }

	@Override
	public void reset(GeomPlan3d planMcs, GeomPlan2d planScr) { }
	
	/* OPERATIONS */

	// MCS_to_PROJ CONVERTION
	
	public GeomPoint3d fromMcsToProj(GeomPoint3d pt3dMcs)
	{
		return null;
	}
	
	public GeomVector3d fromProjToMcs(GeomPoint3d ptProj3d)
	{
		return null;
	}

	// MCS_to_SCR CONVERTION
	
	public double fromMcsToScr(double val)
	{
		double valScr = val * scaleScr;
		return valScr;
	}
	
	public double fromScrToMcs(double val)
	{
		double valScr = val / scaleScr;
		return valScr;
	}
	
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs)
	{
		GeomPoint2d ptCenterMcs = this.planMcs.getPtCenter();		
		GeomPoint2d ptCenterScr = this.planScr.getPtCenter();

		GeomVector2d vMcs = new GeomVector2d(ptCenterMcs, ptMcs);
		GeomVector2d vScr = vMcs.otherMult(scaleScr);
		
		GeomPoint2d ptScr = ptCenterScr.otherMoveTo(vScr, vScr.mod());
		return ptScr;
	}
	
	public GeomPoint2d fromScrToMcs(GeomPoint2d ptScr)
	{
		GeomVector2d vScr = new GeomVector2d(this.planScr.getPtCenter(), ptScr);
		GeomVector2d vMcs = vScr.otherMult(1.0 / scaleScr);

		GeomPoint2d ptMcs = this.planMcs.getPtCenter().otherMoveTo(vMcs, vMcs.mod());
		return ptMcs;
	}
	
	// PROJ_to_SCR CONVERTION
	
	public double fromProjToScr(double val) {
		return 0.0;
	}

	public double fromScrToProj(double val) {
		return 0.0;
	}

	public GeomPoint2d fromProjToScr(GeomPoint3d ptProj) {
		return null;
	}

	public GeomPoint3d fromScrToProj(GeomPoint2d ptScr) {
		return null;
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
		this.planMcs0 = new GeomPlan2d(this.planMcs);
		this.limitsMcs0 = new GeomRect2d(this.limitsMcs);
	}
	
	public void loadView()
	{
		this.planMcs = new GeomPlan2d(this.planMcs0);
		this.limitsMcs = new GeomRect2d(this.limitsMcs0);
	}
	
	/* PAN and ZOOM */
	
	public void moveToMcs(GeomVector2d vDirMcs, double distMcs)
	{
		GeomPoint2d ptNewCenterMcs = this.planMcs.getPtCenter();
		ptNewCenterMcs.selfMoveTo(vDirMcs, distMcs);
		
		this.planMcs.resetOrigin(ptNewCenterMcs);
	}
	
	public void moveToMcs(GeomPoint2d ptNewCenterMcs)
	{
		GeomPoint2d ptOldCenterMcs = this.planMcs.getPtCenter();
		
		GeomVector2d vDirMcs = new GeomVector2d(ptOldCenterMcs, ptNewCenterMcs);
		double distMcs = vDirMcs.mod();
		
		this.moveToMcs(vDirMcs, distMcs);
	}
	
	public void moveToMcs(GeomVector3d vDirMcs, double distMcs) { }

	public void moveToMcs(GeomPoint3d ptNewCenterMcs) { }

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

	public void zoomCenterMcs(GeomPoint2d ptNewCenterMcs, double sclFactor)
	{
		this.moveToMcs(ptNewCenterMcs);
		this.zoomScaleMcs(sclFactor);
	}
		
	public void zoomCenterMcs(GeomPoint3d ptNewCenterMcs, double sclFactor) { }

	public void zoomWindowMcs(GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs)
	{
		GeomPoint2d ptNewCenterMcs = GeomUtil.midPointOf(ptMinMcs, ptMaxMcs);
		
		double xMinMcs = ptMinMcs.getX();
		double yMinMcs = ptMinMcs.getY();
		
		double xMaxMcs = ptMaxMcs.getX();
		double yMaxMcs = ptMaxMcs.getY();
		
		double w = xMaxMcs - xMinMcs;
		double h = yMaxMcs - yMinMcs;
		
		this.moveToMcs(ptNewCenterMcs);
		this.resizeToMcs(w, h);
	}
	
	public void zoomWindowMcs(GeomPoint3d ptMinMcs, GeomPoint3d ptMaxMcs) { }

	public void zoomExtMcs()
	{
		this.loadView();
		this.adjustView();
	}
	
	/* 3D-OBSERVER */
	
	public void zoomMoveForwardBackwardMcs(double dist) { }
	
	public void zoomTurnLeftRightDegrees(double angleDegrees) { }

	/* Getters/Setters */

	//PLAN MCS (0-INICIAL)

	@Override
	public GeomPlan2d getPlanMcs02d() {
		return planMcs0;
	}

	@Override
	public GeomPlan3d getPlanMcs03d() {
		GeomPlan3d planMcs03d = new GeomPlan3d(this.planMcs0);
		return planMcs03d;
	}

	//LIMITS MCS (0-INITIAL)

	@Override
	public GeomRect2d getLimitsMcs02d() {
		return limitsMcs0;
	}

	@Override
	public GeomRect3d getLimitsMcs03d() {
		GeomRect3d limitsMcs3d = new GeomRect3d(limitsMcs0);
		return limitsMcs3d;
	}	

	//OBSERVER MCS (0-INICIAL)
	
	@Override
	public GeomObserver3d getObsMcs0() {
		return null;
	}
	
	//PLAN MCS

	@Override
	public GeomPlan2d getPlanMcs2d() {
		return this.planMcs;
	}
	
	@Override
	public GeomPlan3d getPlanMcs3d() {
		GeomPlan3d planMcs3d = new GeomPlan3d(this.planMcs);
		return planMcs3d;
	}

	//LIMITS MCS (0-INITIAL)

	@Override
	public GeomRect2d getLimitsMcs2d() {
		return limitsMcs;
	}

	@Override
	public GeomRect3d getLimitsMcs3d() {
		GeomRect3d limitsMcs3d = new GeomRect3d(limitsMcs);
		return limitsMcs3d;
	}	

	//OBSERVER MCS
	
	@Override
	public GeomObserver3d getObsMcs() {
		return null;
	}

	//PLAN PROJ

	@Override
	public GeomPlan3d getPlanProj() {
		return null;
	}

	//PLAN SCR
	
	@Override
	public GeomPlan2d getPlanScr() {
		return planScr;
	}
	
	//SCALE_PROJ = PROJ / MCS
	
	@Override
	public double getScaleProj() {
		return(1.0);
	}
	
	//SCALE_SCR = SCR / PROJ
	
	@Override
	public double getScaleScr() {
		return scaleScr;
	}

}
