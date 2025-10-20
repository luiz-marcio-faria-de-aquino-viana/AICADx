/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadView3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
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
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class CadView3d implements ICadViewBase
{
//Private
	//InitialView
	private GeomPlan3d planMcs0;
	private GeomPlan3d planProj0;
	private GeomPlan2d planScr0;
	//InitialLimits
	private GeomRect3d limitsMcs0;
	private GeomRect3d limitsProj0;
	private GeomRect2d limitsScr0;
	//InitialObserver
	private GeomObserver3d obsMcs0;
	//InitialScale
	private double scaleProj0;
	private double scaleScr0;
	
	//CurrentView
	private GeomPlan3d planMcs;
	private GeomPlan3d planProj;
	private GeomPlan2d planScr;
	//CurrentLimits
	private GeomRect3d limitsMcs;
	private GeomRect3d limitsProj;
	private GeomRect2d limitsScr;
	//CurrentObserver
	private GeomObserver3d obsMcs;
	//CurrentScale
	private double scaleProj;			// SCALE_PROJ = WIDTH_PROJ / WIDTH_MCS	
	private double scaleScr;			// SCALE_SCR = WIDTH_SCR / WIDTH_PROJ
	
	//Camera References
	//
	private GeomPoint3d ptCentroid;
	private double modelRadius;
	private double obsRadius;
	
	private String detailLevel = AppDefs.DEF_DETAILLEVEL_HIGH;
	
	/* Methodes */
	
	private void adjustPlanProj()
	{
		GeomPoint3d ptPlanMcs = this.planMcs.getPtCenter();

		GeomPoint3d ptObsMcs = this.obsMcs.getPtObserver();
		GeomPoint3d ptOrigMcs = new GeomPoint3d(0.0, 0.0, ptObsMcs.getZ());
		
		double distToPlanMcs = ptObsMcs.distTo(ptOrigMcs);
		
		GeomPlan3d planMcs3d = this.planMcs;
		
		double width = planMcs3d.getWidth();
		double height = planMcs3d.getHeight();
		double zHeight = planMcs3d.getZHeight();
		
		this.planProj = obsMcs.getPlanFrom(distToPlanMcs, width, height, zHeight);
	}
	
	private void adjustView()
	{
		double ratioScr = this.planScr.getRatio();
		
		this.planMcs.resetRatio(ratioScr);		
		this.planProj.resetRatio(ratioScr);
		
		this.scaleProj = this.planProj.getWidth() / this.planMcs.getWidth();
		this.scaleScr = this.planScr.getWidth() / this.planProj.getWidth();
	}
	
	private void adjustLimits()
	{
		//MCS
		//
		double zHeight = this.planMcs.getZHeight();
		double zHeight2 = zHeight / 2.0;
		
		//PROJ
		//
		GeomPoint3d pt0Proj = this.fromMcsToProj( this.planMcs.getPtLowerLeftCorner() );
		GeomPoint3d pt1Proj = this.fromMcsToProj( this.planMcs.getPtLowerRightCorner() );
		GeomPoint3d pt2Proj = this.fromMcsToProj( this.planMcs.getPtUpperRightCorner() );
		GeomPoint3d pt3Proj = this.fromMcsToProj( this.planMcs.getPtUpperLeftCorner() );

		GeomPoint3d[] arrPtMaxMinProj = GeomUtil.maxMinPointOf(pt0Proj, pt1Proj, pt2Proj, pt3Proj);
		
		GeomPoint3d ptMinProj = arrPtMaxMinProj[0];
		GeomPoint3d ptMaxProj = arrPtMaxMinProj[1];
		
		GeomPoint3d ptLLCProj = new GeomPoint3d(ptMinProj.getX(), - zHeight2, 0.0);
		GeomPoint3d ptLRCProj = new GeomPoint3d(ptMaxProj.getX(), - zHeight2, 0.0);
		GeomPoint3d ptURCProj = new GeomPoint3d(ptMaxProj.getX(),   zHeight2, 0.0);
		GeomPoint3d ptULCProj = new GeomPoint3d(ptMinProj.getX(),   zHeight2, 0.0);

		this.planProj.setPtLowerLeftCorner(ptLLCProj);
		this.planProj.setPtLowerRightCorner(ptLRCProj);
		this.planProj.setPtUpperRightCorner(ptURCProj);
		this.planProj.setPtUpperLeftCorner(ptULCProj);
		
		this.limitsProj = new GeomRect3d(
			ptMinProj,
			ptMaxProj );

		double wProj = ptMaxProj.getX() - ptMinProj.getX();
		double hProj = zHeight;

		this.planProj.setWidth(wProj);
		this.planProj.setHeight(hProj);

		double ratioProj = hProj / wProj;
		
		this.planProj.setRatio(ratioProj);

		this.scaleProj = this.planProj.getWidth() / this.planMcs.getWidth();
		
		//SCR
		//
		double wScr = this.planScr.getWidth();
		double hScr = this.planScr.getHeight();

		GeomPoint2d ptCenterScr = new GeomPoint2d(this.planScr.getPtCenter());		
		
		double xMinScr = ptCenterScr.getX() - (wScr / 2.0);
		double yMinScr = ptCenterScr.getY() - (hScr / 2.0);
		
		double xMaxScr = ptCenterScr.getX() + (wScr / 2.0);
		double yMaxScr = ptCenterScr.getY() + (hScr / 2.0);
		
		GeomPoint2d ptLLCScr = new GeomPoint2d(xMinScr, yMinScr);
		GeomPoint2d ptLRCScr = new GeomPoint2d(xMaxScr, yMinScr);
		GeomPoint2d ptURCScr = new GeomPoint2d(xMaxScr, yMaxScr);
		GeomPoint2d ptULCScr = new GeomPoint2d(xMinScr, yMaxScr);

		this.planScr.setPtLowerLeftCorner(ptLLCScr);
		this.planScr.setPtLowerRightCorner(ptLRCScr);
		this.planScr.setPtUpperRightCorner(ptURCScr);
		this.planScr.setPtUpperLeftCorner(ptULCScr);
		
		this.limitsScr = new GeomRect2d(
			new GeomPoint2d(xMinScr, yMinScr),
			new GeomPoint2d(xMaxScr, yMaxScr) );
		
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
		GeomVector3d vObsDirMcs,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius)
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
			vObsDirMcs,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}

	public CadView3d(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius)
	{
		this.init(
			planMcs,
			obsMcs,
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}
	
	/* Methodes */
	
	// 2D-VEIW
	//	
	@Override
	public void init(
		GeomPoint2d ptCenterMcs, 
		GeomVector2d xDirMcs, 
		double widthMcs, 
		double heightMcs, 
		double widthScr, 
		double heightScr) { }

	@Override
	public void init(
		GeomPlan2d planMcs, 
		GeomPlan2d planScr) { }
	
	@Override
	public void init(
		GeomPlan3d planMcs, 
		GeomPlan2d planScr) { }

	@Override
	public void init(
		GeomPoint3d ptCenterMcs, 
		GeomVector3d xDirMcs, 
		double widthMcs, 
		double heightMcs,
		double zHeightMcs, 
		double widthScr, 
		double heightScr) { }

	// 3D-VIEW
	//
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
		GeomVector3d vObsDirMcs,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius)
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
		
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}
	
	@Override
	public void init(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;
		this.obsMcs = obsMcs;
		
		this.limitsMcs = new GeomRect3d(
			this.planMcs.getPtLowerLeftCorner(),
			this.planMcs.getPtUpperRightCorner() );

		this.ptCentroid = new GeomPoint3d( ptCentroid );
		this.modelRadius = modelRadius;
		this.obsRadius = obsRadius;
		
		this.adjustPlanProj();
		this.adjustView();
		this.adjustLimits();
		this.saveView();
	}

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
		
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			this.ptCentroid,
			this.modelRadius,
			this.obsRadius);
	}
	
	@Override
	public void reset(
		GeomPlan3d planMcs,
		GeomObserver3d obsMcs,
		GeomPlan2d planScr)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;

		this.limitsMcs = new GeomRect3d(
			this.planMcs.getPtLowerLeftCorner(),
			this.planMcs.getPtUpperRightCorner() );
		
		this.obsMcs = obsMcs;

		this.adjustPlanProj();
		this.adjustView();
		this.adjustLimits();
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
	
	/* CAMERA REFERENCES */

	public void setCameraReference(GeomPoint3d ptCentroid, double modelRadius, double obsRadius) 
	{ 
		this.ptCentroid = new GeomPoint3d(ptCentroid);
		this.modelRadius = modelRadius;
		this.obsRadius = obsRadius;
		
		GeomPoint3d ptObserver = new GeomPoint3d( this.obsMcs.getPtObserver() );
		
		GeomVector3d vDir = new GeomVector3d(ptObserver, this.ptCentroid);
		this.obsMcs = new GeomObserver3d(ptObserver, vDir);
	}

	/* RE-CENTER */
	
	@Override
	public void moveToCenter(GeomPoint3d ptNewCenterMcs) { }

//	public void moveToCenter(GeomPoint3d ptNewCenterMcs) {
//		GeomPlan3d plan3dMcs = this.getPlanMcs3d(); 
//		
//		double w = plan3dMcs.getWidth(); 
//		double h = plan3dMcs.getHeight(); 
//		double zH = plan3dMcs.getZHeight(); 
//
//		// MAX/MIN
//		double xpMin = ptNewCenterMcs.getX() - (w / 2.0);
//		double ypMin = ptNewCenterMcs.getY() - (h / 2.0);
//		//
//		double xpMax = ptNewCenterMcs.getX() + (w / 2.0);
//		double ypMax = ptNewCenterMcs.getY() + (h / 2.0);
//
//		// POINT_MCS 
//		GeomPoint3d pt0Mcs = new GeomPoint3d(xpMin, ypMin, 0.0);
//		GeomPoint3d pt1Mcs = new GeomPoint3d(xpMax, ypMin, 0.0);
//		GeomPoint3d pt2Mcs = new GeomPoint3d(xpMax, ypMax, 0.0);
//		GeomPoint3d pt3Mcs = new GeomPoint3d(xpMin, ypMax, 0.0);
//
//		// POINT_PROJ 
//		GeomPoint3d ptNewCenterProj = this.fromMcsToProj(ptNewCenterMcs);
//		//
//		GeomPoint3d pt0Proj = this.fromMcsToProj(pt0Mcs);
//		GeomPoint3d pt1Proj = this.fromMcsToProj(pt1Mcs);
//		GeomPoint3d pt2Proj = this.fromMcsToProj(pt2Mcs);
//		GeomPoint3d pt3Proj = this.fromMcsToProj(pt3Mcs);
//
//		GeomPlan3d plan3dProj = this.getPlanProj();
//		plan3dProj.setPtCenter(ptNewCenterProj);
//
//		double d01 = pt0Proj.distTo(pt1Proj);
//		double d12 = pt1Proj.distTo(pt2Proj);
//		double d23 = pt2Proj.distTo(pt3Proj);
//		double d30 = pt3Proj.distTo(pt0Proj);
//		
//		double dMax = Math.max( Math.max( Math.max(d01, d12), d23 ), d30 );
//		double dMax2 = dMax / 2.0;
//		
//		double xMin = ptNewCenterProj.getX() - dMax2;
//		double yMin = ptNewCenterProj.getY() - dMax2;
//		double xMax = ptNewCenterProj.getX() + dMax2;
//		double yMax = ptNewCenterProj.getY() + dMax2;
//		
//		GeomPoint3d ptMin3d = new GeomPoint3d(xMin, yMin, ) 
//		
//		plan3dProj.setWidth(dMax);
//		plan3dProj.setHeight(dMax);
//		
//		plan3dProj.setPtLowerLeftCorner(pt0Proj);
//		plan3dProj.setPtLowerRightCorner(pt1Proj);
//		plan3dProj.setPtUpperRightCorner(pt2Proj);
//		plan3dProj.setPtUpperLeftCorner(pt3Proj);
//
//		limitsProj.init(pt2Proj, pt3Proj);
//		//ScaleProj
//		this.scaleProj = dMax / ;			// SCALE_PROJ = WIDTH_PROJ / WIDTH_MCS
//		
//	}

	/* OPERATIONS */

	// MCS_to_PROJ CONVERTION
	
	@Override
	public GeomPoint3d fromMcsToProj(GeomPoint3d pt3dMcs)
	{
		GeomPoint3d ptObserver3d = this.obsMcs.getPtObserver();
		GeomVector3d uDir3d = this.obsMcs.getUnitDir();

		GeomVector3d vObsToPt3d = new GeomVector3d(ptObserver3d, pt3dMcs);						// V_OBS_TO_PTMCS
		GeomVector3d uObsToPt3d = vObsToPt3d.otherUnit();

		double distObsToPt3d = vObsToPt3d.mod();												// DIST_OBS_TO_PTMCS
		if(distObsToPt3d < AppDefs.MATHPREC_MIN)
			distObsToPt3d = AppDefs.MATHPREC_MIN;
		
		double distDirObsToPt3d = Math.abs( uDir3d.dotProd(vObsToPt3d) );						// DIST_DIROBS (PTMCS)
		if(distDirObsToPt3d < AppDefs.MATHPREC_MIN)
			distDirObsToPt3d = AppDefs.MATHPREC_MIN;

		double scale = 1.0 / distDirObsToPt3d;													// SCALE_MCS_TO_PROJ

		double dist = distObsToPt3d * scale * AppDefs.EXAGERATION;
		
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
	
	@Override
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
	
	@Override
	public GeomPoint3d fromProjToMcs(GeomPoint3d ptProj3d, double zp)
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
		
		GeomVector3d vPlanDirToPt3dProj = new GeomVector3d(vObsToPt3dProj, zp); 
		GeomVector3d uPlanDirToPt3dProj = vPlanDirToPt3dProj.otherUnit(); 

		GeomPlan3d planMcs = this.planMcs;

		GeomVector3d axisZ = planMcs.getAxisZ();
		double dH = ptObserver3d.getZ() - zp;

		double angleZ = axisZ.angleTo(uObsToPt3dProj);

		double d = dH;
		double dL = 0;
		if(Math.abs(angleZ) >= AppDefs.MATHPREC_MIN) {
			d = dH / Math.cos(angleZ);
			dL = d * Math.sin(angleZ);
		}

		GeomPoint3d ptObserver3dAtZ = new GeomPoint3d(ptObserver3d.getX(), ptObserver3d.getY(), zp);
		GeomPoint3d pt3dMcs = ptObserver3dAtZ.otherMoveTo(uPlanDirToPt3dProj, dL);
		
		return pt3dMcs;
	}
	
	// MCS_to_SCR CONVERTION
	
	@Override
	public double fromMcsToScr(double val) {
		return 0;
	}

	@Override
	public double fromScrToMcs(double val) {
		return 0;
	}

	@Override
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs) {
		return null;
	}
	
	@Override
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs, GeomPoint2d ptBaseMcs, double rotate) {
		return null;		
	}

	@Override
	public GeomPoint2d fromScrToMcs(GeomPoint2d ptScr) {
		return null;
	}
	
	// PROJ_to_SCR CONVERTION
	
	@Override
	public double fromProjToScr(double val) {
		double valScr = val * this.scaleScr;
		return valScr;
	}

	@Override
	public double fromScrToProj(double val) {
		double valScr = val / this.scaleScr;
		return valScr;
	}

	@Override
	public GeomPoint2d fromProjToScr(GeomPoint3d ptProj) {
		GeomPoint2d ptCenterScr = this.planScr.getPtCenter();
		
		double xScr = ptCenterScr.getX() + (ptProj.getX() * scaleScr);
		double yScr = ptCenterScr.getY() + (ptProj.getY() * scaleScr);

		GeomPoint2d ptScr = new GeomPoint2d(xScr, yScr);		
		return ptScr;
	}

	@Override
	public GeomPoint3d fromScrToProj(GeomPoint2d ptScr) {
		GeomPoint3d ptCenterProj = this.planProj.getPtCenter();
		
		double xProj = ptCenterProj.getX() + (ptScr.getX() / scaleScr);
		double yProj = ptCenterProj.getY() + (ptScr.getY() / scaleScr);
		double zProj = ptCenterProj.getZ();

		GeomPoint3d ptProj = new GeomPoint3d(xProj, yProj, zProj);		
		return ptProj;
	}
	
	// SCR_to_VIDEO CONVERTION
	
	@Override
	public GeomPoint2d fromScrToVideo(GeomPoint2d ptScr)
	{
		double xScr = ptScr.getX();
		double yScr = ptScr.getY();

		double xVideo = xScr;
		double yVideo = this.planScr.getHeight() - yScr;
		
		GeomPoint2d ptVideo = new GeomPoint2d(xVideo, yVideo);
		return ptVideo;
	}
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	public void saveView()
	{
		this.planMcs0 = new GeomPlan3d(this.planMcs);
		this.planProj0 = new GeomPlan3d(this.planProj);
		this.planScr0 = new GeomPlan2d(this.planScr);
		//
		this.limitsMcs0 = new GeomRect3d(this.limitsMcs);
		this.limitsProj0 = new GeomRect3d(this.limitsProj);
		this.limitsScr0 = new GeomRect2d(this.limitsScr);
		//
		this.obsMcs0 = new GeomObserver3d(this.obsMcs);
		//
		this.scaleProj0 = this.scaleProj;
		this.scaleScr0 = this.scaleScr;
	}
	
	@Override
	public void loadView()
	{
		this.planMcs = new GeomPlan3d(this.planMcs0);
		this.planProj = new GeomPlan3d(this.planProj0);
		this.planScr = new GeomPlan2d(this.planScr0);
		//
		this.limitsMcs = new GeomRect3d(this.limitsMcs0);
		this.limitsProj = new GeomRect3d(this.limitsProj0);
		this.limitsScr = new GeomRect2d(this.limitsScr0);
		//
		this.obsMcs = new GeomObserver3d(this.obsMcs0);
		//
		this.scaleProj = this.scaleProj0;
		this.scaleScr = this.scaleScr0;
	}
	
	/* PAN and ZOOM */
	
	@Override
	public void moveToMcs(GeomVector3d vDirMcs, double distMcs)
	{
		GeomPoint3d ptNewCenterMcs = this.planMcs0.getPtCenter();
		ptNewCenterMcs.selfMoveTo(vDirMcs, distMcs);
		
		this.planMcs.resetOrigin(ptNewCenterMcs);
	}
	
	@Override
	public void moveToMcs(GeomVector2d vDirMcs, double distMcs) { }

	@Override
	public void moveToMcs(GeomPoint3d ptNewCenterMcs)
	{
		GeomPoint3d ptOldCenterMcs = this.planMcs.getPtCenter();
		
		GeomVector3d vDirMcs = new GeomVector3d(ptOldCenterMcs, ptNewCenterMcs);
		double distMcs = vDirMcs.mod();
		
		this.moveToMcs(vDirMcs, distMcs);
	}

	@Override
	public void moveToMcs(GeomPoint2d ptNewCenterMcs) { }
	
	@Override
	public void resizeToMcs(double w, double h)
	{
		this.planMcs.resetDimention(w, h);
		this.adjustView();
		this.adjustLimits();
	}

	@Override
	public void resizeToScr(double w, double h)
	{
		this.planScr.resetDimention(w, h);
		this.adjustView();
		this.adjustLimits();
	}

	@Override
	public void zoomScaleMcs(double sclFactor)
	{
		double w = this.planMcs.getWidth() * sclFactor;
		double h = this.planMcs.getHeight() * sclFactor;
		
		this.planMcs.resetDimention(w, h);
		this.adjustView();
		this.adjustLimits();
	}
	
	@Override
	public void zoomMoveMcs(GeomPoint2d baseMousePosPanMcs, GeomPoint2d currMousePosPanMcs)
	{
		GeomVector2d vDir2d = new GeomVector2d(baseMousePosPanMcs, currMousePosPanMcs);
		//GeomVector2d vDir2d = new GeomVector2d(currMousePosPanMcs, baseMousePosPanMcs);
		double d = vDir2d.mod();
		
		GeomPoint3d ptCenter3d = this.planMcs.getPtCenter();
		GeomPoint2d ptCenter2d = new GeomPoint2d(ptCenter3d);

		GeomPoint2d newPtCenter2d = ptCenter2d.otherMoveTo(vDir2d, -d);

		GeomPoint3d newPtCenter3d = new GeomPoint3d(newPtCenter2d.getX(), newPtCenter2d.getY(), ptCenter3d.getZ());
		this.planMcs.resetOrigin(newPtCenter3d);
	}

	@Override
	public void zoomCenterMcs(GeomPoint3d ptNewCenterMcs, double sclFactor)
	{
		this.moveToMcs(ptNewCenterMcs);
		this.zoomScaleMcs(sclFactor);
	}

	@Override
	public void zoomCenterMcs(GeomPoint2d ptNewCenterMcs, double sclFactor) { }

	@Override
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

	@Override
	public void zoomWindowMcs(GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs) { }
	
	@Override
	public void zoomExtMcs()
	{
		this.loadView();
		this.adjustView();
		this.adjustLimits();
	}

	@Override
	public void zoomAllMcs(CadBlockDef blkDef) { 
		GeomDimension3d oGeomDim3d = blkDef.getEnvelop3d(AppDefs.OBJTYPE_ALL);
		
		GeomPoint3d ptMinMcs = new GeomPoint3d( oGeomDim3d.getPtMin() );
		GeomPoint3d ptMaxMcs = new GeomPoint3d( oGeomDim3d.getPtMax() );
		
		this.zoomWindowMcs(ptMinMcs, ptMaxMcs);
		
		
	}

	@Override
	public void zoomAllMcs(ArrayList<CadEntity> lsEntity) { }

	@Override
	public void zoomAllItemDataMcs(ArrayList<ItemDataVO> lsItemData) { }

	@Override
	public void zoomToCenterMcs(CadBlockDef blkDef)
	{
		GeomDimension2d oGeomDim = blkDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);
		
		GeomPoint2d ptMinMcs = new GeomPoint2d( oGeomDim.getPtMin() );
		GeomPoint2d ptMaxMcs = new GeomPoint2d( oGeomDim.getPtMax() );
		
		//OBSERVER_MCS
		//
		GeomPoint3d ptObserver = new GeomPoint3d(ptMinMcs.getX(), ptMinMcs.getY(), AppDefs.PROJPLAN_OBSERVER_HEIGHT);
		GeomPoint3d ptObsDir = new GeomPoint3d(ptMaxMcs.getX(), ptMaxMcs.getY(), AppDefs.PROJPLAN_OBSERVER_HEIGHT);

		GeomVector3d vObsDir = new GeomVector3d(ptObserver, ptObsDir);

		GeomObserver3d oNewObsMcs = new GeomObserver3d(ptObserver, vObsDir);

		//PLAN_MCS
		//
		GeomPoint3d ptPlanCenter = new GeomPoint3d(oGeomDim.getPtCentroid());
		GeomPoint3d ptPlanDir = new GeomPoint3d(ptPlanCenter.getX() + 1, ptPlanCenter.getY(), ptPlanCenter.getZ());
		
		GeomVector3d vPlanDir = new GeomVector3d(ptPlanCenter, ptPlanDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptPlanCenter, vPlanDir);

		//ZoomTo: CENTER
		//
		this.init(
			planMcs, 
			oNewObsMcs, 
			this.planScr0,
			//
			//Camera References
			//
			this.ptCentroid,
			this.modelRadius,
			this.obsRadius);
	}

	@Override
	public void zoomToCenterMcs(ArrayList<CadEntity> lsEntity)
	{
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(lsEntity, AppDefs.OBJTYPE_ALL);
		
		GeomPoint2d ptMinMcs = new GeomPoint2d( oGeomDim.getPtMin() );
		GeomPoint2d ptMaxMcs = new GeomPoint2d( oGeomDim.getPtMax() );
		
		//OBSERVER_MCS
		//
		GeomPoint3d ptObserver = new GeomPoint3d(ptMinMcs.getX(), ptMinMcs.getY(), AppDefs.PROJPLAN_OBSERVER_HEIGHT);
		GeomPoint3d ptObsDir = new GeomPoint3d(ptMaxMcs.getX(), ptMaxMcs.getY(), AppDefs.PROJPLAN_OBSERVER_HEIGHT);

		GeomVector3d vObsDir = new GeomVector3d(ptObserver, ptObsDir);

		GeomObserver3d oNewObsMcs = new GeomObserver3d(ptObserver, vObsDir);

		//PLAN_MCS
		//
		GeomPoint3d ptPlanCenter = new GeomPoint3d(oGeomDim.getPtCentroid());
		GeomPoint3d ptPlanDir = new GeomPoint3d(ptPlanCenter.getX() + 1, ptPlanCenter.getY(), ptPlanCenter.getZ());
		
		GeomVector3d vPlanDir = new GeomVector3d(ptPlanCenter, ptPlanDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptPlanCenter, vPlanDir);

		//ZoomTo: CENTER
		//
		this.init(
			planMcs, 
			oNewObsMcs, 
			this.planScr0,
			//
			//Camera References
			//
			this.ptCentroid,
			this.modelRadius,
			this.obsRadius);
	}

	@Override
	public void zoomToCenterItemDataMcs(ArrayList<ItemDataVO> lsItemData)
	{
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(lsItemData);
		
		GeomPoint2d ptMinMcs = new GeomPoint2d( oGeomDim.getPtMin() );
		GeomPoint2d ptMaxMcs = new GeomPoint2d( oGeomDim.getPtMax() );
		
		//OBSERVER_MCS
		//
		GeomPoint3d ptObserver = new GeomPoint3d(ptMinMcs.getX(), ptMinMcs.getY(), AppDefs.PROJPLAN_OBSERVER_HEIGHT);
		GeomPoint3d ptObsDir = new GeomPoint3d(ptMaxMcs.getX(), ptMaxMcs.getY(), AppDefs.PROJPLAN_OBSERVER_HEIGHT);

		GeomVector3d vObsDir = new GeomVector3d(ptObserver, ptObsDir);

		GeomObserver3d oNewObsMcs = new GeomObserver3d(ptObserver, vObsDir);

		//PLAN_MCS
		//
		GeomPoint3d ptPlanCenter = new GeomPoint3d(oGeomDim.getPtCentroid());
		GeomPoint3d ptPlanDir = new GeomPoint3d(ptPlanCenter.getX() + 1, ptPlanCenter.getY(), ptPlanCenter.getZ());
		
		GeomVector3d vPlanDir = new GeomVector3d(ptPlanCenter, ptPlanDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptPlanCenter, vPlanDir);

		//ZoomTo: CENTER
		//
		this.init(
			planMcs, 
			oNewObsMcs, 
			this.planScr0,
			//
			//Camera References
			//
			this.ptCentroid,
			this.modelRadius,
			this.obsRadius);
	}

	/* 3D-VIEW */

	@Override
	public void zoomViewTopMcs(CadDocumentDef doc) {
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		GeomDimension3d oDim3d = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);

		GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
		double xMin3d = ptMin3d.getX();
		double yMin3d = ptMin3d.getY();
		double zMin3d = ptMin3d.getZ();

		GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		double xMax3d = ptMax3d.getX();
		double yMax3d = ptMax3d.getY();
		double zMax3d = ptMax3d.getZ();

		double wMcs = Math.abs(xMax3d - xMin3d);
		double hMcs = Math.abs(yMax3d - yMin3d);
		double zH = Math.abs(zMax3d - zMin3d);
		
		// PLAN3D MCS
		//
		GeomPoint3d ptCenter3d = GeomUtil.midPointOf(ptMin3d, ptMax3d);
		
		double xCenter = ptCenter3d.getX();
		double yCenter = ptCenter3d.getY();
		double zCenter = ptCenter3d.getZ();
		
		double xDir = xCenter + 1.0;
		double yDir = yCenter;
		double zDir = zCenter;
		
		GeomPoint3d ptXDir = new GeomPoint3d(xDir, yDir, zDir);
		GeomVector3d vXDir = new GeomVector3d(ptCenter3d, ptXDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptCenter3d, vXDir, wMcs, hMcs, zH);

		// PLAN2D SCR
		//
		GeomPlan2d planScr = new GeomPlan2d(this.planScr0);

		// CENTROID
		//		
		GeomPoint3d ptCentroid = new GeomPoint3d(ptCenter3d);
		double modelRadius = ptMin3d.distTo(ptMax3d);
		double obsRadius = modelRadius * AppDefs.MODEL_DIST_16X;
				
		// OBSERVER3D
		//
		double xObs3d = xCenter;
		double yObs3d = yCenter;
		double zObs3d = zCenter + obsRadius;

		GeomPoint3d ptObserver3d = new GeomPoint3d(xObs3d, yObs3d, zObs3d); 
		GeomVector3d vDir3d = new GeomVector3d(ptObserver3d, ptCenter3d);

		GeomObserver3d obsMcs = new GeomObserver3d(ptObserver3d, vDir3d);
		
		//RE-INIT_VIEW
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}

	@Override
	public void zoomViewBottomMcs(CadDocumentDef doc) { 
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		GeomDimension3d oDim3d = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);

		GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
		double xMin3d = ptMin3d.getX();
		double yMin3d = ptMin3d.getY();
		double zMin3d = ptMin3d.getZ();

		GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		double xMax3d = ptMax3d.getX();
		double yMax3d = ptMax3d.getY();
		double zMax3d = ptMax3d.getZ();

		double wMcs = Math.abs(xMax3d - xMin3d);
		double hMcs = Math.abs(yMax3d - yMin3d);
		double zH = Math.abs(zMax3d - zMin3d);
		
		// PLAN3D MCS
		//
		GeomPoint3d ptCenter3d = GeomUtil.midPointOf(ptMin3d, ptMax3d);
		
		double xCenter = ptCenter3d.getX();
		double yCenter = ptCenter3d.getY();
		double zCenter = ptCenter3d.getZ();
		
		double xDir = xCenter + 1.0;
		double yDir = yCenter;
		double zDir = zCenter;
		
		GeomPoint3d ptXDir = new GeomPoint3d(xDir, yDir, zDir);
		GeomVector3d vXDir = new GeomVector3d(ptCenter3d, ptXDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptCenter3d, vXDir, wMcs, hMcs, zH);

		// PLAN2D SCR
		//
		GeomPlan2d planScr = new GeomPlan2d(this.planScr0);

		// CENTROID
		//		
		GeomPoint3d ptCentroid = new GeomPoint3d(ptCenter3d);
		double modelRadius = ptMin3d.distTo(ptMax3d);
		double obsRadius = modelRadius * AppDefs.MODEL_DIST_16X;
				
		// OBSERVER3D
		//
		double xObs3d = xCenter;
		double yObs3d = yCenter;
		double zObs3d = zCenter - obsRadius;

		GeomPoint3d ptObserver3d = new GeomPoint3d(xObs3d, yObs3d, zObs3d); 
		GeomVector3d vDir3d = new GeomVector3d(ptObserver3d, ptCenter3d);

		GeomObserver3d obsMcs = new GeomObserver3d(ptObserver3d, vDir3d);
		
		//RE-INIT_VIEW
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}

	@Override
	public void zoomViewFrontMcs(CadDocumentDef doc) { 
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		GeomDimension3d oDim3d = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);

		GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
		double xMin3d = ptMin3d.getX();
		double yMin3d = ptMin3d.getY();
		double zMin3d = ptMin3d.getZ();

		GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		double xMax3d = ptMax3d.getX();
		double yMax3d = ptMax3d.getY();
		double zMax3d = ptMax3d.getZ();

		double wMcs = Math.abs(xMax3d - xMin3d);
		double hMcs = Math.abs(yMax3d - yMin3d);
		double zH = Math.abs(zMax3d - zMin3d);
		
		// PLAN3D MCS
		//
		GeomPoint3d ptCenter3d = GeomUtil.midPointOf(ptMin3d, ptMax3d);
		
		double xCenter = ptCenter3d.getX();
		double yCenter = ptCenter3d.getY();
		double zCenter = ptCenter3d.getZ();
		
		double xDir = xCenter + 1.0;
		double yDir = yCenter;
		double zDir = zCenter;
		
		GeomPoint3d ptXDir = new GeomPoint3d(xDir, yDir, zDir);
		GeomVector3d vXDir = new GeomVector3d(ptCenter3d, ptXDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptCenter3d, vXDir, wMcs, hMcs, zH);

		// PLAN2D SCR
		//
		GeomPlan2d planScr = new GeomPlan2d(this.planScr0);

		// CENTROID
		//		
		GeomPoint3d ptCentroid = new GeomPoint3d(ptCenter3d);
		double modelRadius = ptMin3d.distTo(ptMax3d);
		double obsRadius = modelRadius * AppDefs.MODEL_DIST_16X;
				
		// OBSERVER3D
		//
		double xObs3d = xCenter;
		double yObs3d = yCenter + obsRadius;
		double zObs3d = zCenter;

		GeomPoint3d ptObserver3d = new GeomPoint3d(xObs3d, yObs3d, zObs3d); 
		GeomVector3d vDir3d = new GeomVector3d(ptObserver3d, ptCenter3d);

		GeomObserver3d obsMcs = new GeomObserver3d(ptObserver3d, vDir3d);
		
		//RE-INIT_VIEW
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}

	@Override
	public void zoomViewBackMcs(CadDocumentDef doc) { 
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		GeomDimension3d oDim3d = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);

		GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
		double xMin3d = ptMin3d.getX();
		double yMin3d = ptMin3d.getY();
		double zMin3d = ptMin3d.getZ();

		GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		double xMax3d = ptMax3d.getX();
		double yMax3d = ptMax3d.getY();
		double zMax3d = ptMax3d.getZ();

		double wMcs = Math.abs(xMax3d - xMin3d);
		double hMcs = Math.abs(yMax3d - yMin3d);
		double zH = Math.abs(zMax3d - zMin3d);
		
		// PLAN3D MCS
		//
		GeomPoint3d ptCenter3d = GeomUtil.midPointOf(ptMin3d, ptMax3d);
		
		double xCenter = ptCenter3d.getX();
		double yCenter = ptCenter3d.getY();
		double zCenter = ptCenter3d.getZ();
		
		double xDir = xCenter + 1.0;
		double yDir = yCenter;
		double zDir = zCenter;
		
		GeomPoint3d ptXDir = new GeomPoint3d(xDir, yDir, zDir);
		GeomVector3d vXDir = new GeomVector3d(ptCenter3d, ptXDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptCenter3d, vXDir, wMcs, hMcs, zH);

		// PLAN2D SCR
		//
		GeomPlan2d planScr = new GeomPlan2d(this.planScr0);

		// CENTROID
		//		
		GeomPoint3d ptCentroid = new GeomPoint3d(ptCenter3d);
		double modelRadius = ptMin3d.distTo(ptMax3d);
		double obsRadius = modelRadius * AppDefs.MODEL_DIST_16X;
				
		// OBSERVER3D
		//
		double xObs3d = xCenter;
		double yObs3d = yCenter - obsRadius;
		double zObs3d = zCenter;

		GeomPoint3d ptObserver3d = new GeomPoint3d(xObs3d, yObs3d, zObs3d); 
		GeomVector3d vDir3d = new GeomVector3d(ptObserver3d, ptCenter3d);

		GeomObserver3d obsMcs = new GeomObserver3d(ptObserver3d, vDir3d);
		
		//RE-INIT_VIEW
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}

	@Override
	public void zoomViewRightMcs(CadDocumentDef doc) { 
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		GeomDimension3d oDim3d = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);

		GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
		double xMin3d = ptMin3d.getX();
		double yMin3d = ptMin3d.getY();
		double zMin3d = ptMin3d.getZ();

		GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		double xMax3d = ptMax3d.getX();
		double yMax3d = ptMax3d.getY();
		double zMax3d = ptMax3d.getZ();

		double wMcs = Math.abs(xMax3d - xMin3d);
		double hMcs = Math.abs(yMax3d - yMin3d);
		double zH = Math.abs(zMax3d - zMin3d);
		
		// PLAN3D MCS
		//
		GeomPoint3d ptCenter3d = GeomUtil.midPointOf(ptMin3d, ptMax3d);
		
		double xCenter = ptCenter3d.getX();
		double yCenter = ptCenter3d.getY();
		double zCenter = ptCenter3d.getZ();
		
		double xDir = xCenter + 1.0;
		double yDir = yCenter;
		double zDir = zCenter;
		
		GeomPoint3d ptXDir = new GeomPoint3d(xDir, yDir, zDir);
		GeomVector3d vXDir = new GeomVector3d(ptCenter3d, ptXDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptCenter3d, vXDir, wMcs, hMcs, zH);

		// PLAN2D SCR
		//
		GeomPlan2d planScr = new GeomPlan2d(this.planScr0);

		// CENTROID
		//		
		GeomPoint3d ptCentroid = new GeomPoint3d(ptCenter3d);
		double modelRadius = ptMin3d.distTo(ptMax3d);
		double obsRadius = modelRadius * AppDefs.MODEL_DIST_16X;
				
		// OBSERVER3D
		//
		double xObs3d = xCenter + obsRadius;
		double yObs3d = yCenter;
		double zObs3d = zCenter;

		GeomPoint3d ptObserver3d = new GeomPoint3d(xObs3d, yObs3d, zObs3d); 
		GeomVector3d vDir3d = new GeomVector3d(ptObserver3d, ptCenter3d);

		GeomObserver3d obsMcs = new GeomObserver3d(ptObserver3d, vDir3d);
		
		//RE-INIT_VIEW
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}
	
	@Override
	public void zoomViewLeftMcs(CadDocumentDef doc) { 
		CadBlockDef oBlk = doc.getCurrBlockDef();
		
		GeomDimension3d oDim3d = oBlk.getEnvelop3d(AppDefs.OBJTYPE_ALL);

		GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
		double xMin3d = ptMin3d.getX();
		double yMin3d = ptMin3d.getY();
		double zMin3d = ptMin3d.getZ();

		GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
		double xMax3d = ptMax3d.getX();
		double yMax3d = ptMax3d.getY();
		double zMax3d = ptMax3d.getZ();

		double wMcs = Math.abs(xMax3d - xMin3d);
		double hMcs = Math.abs(yMax3d - yMin3d);
		double zH = Math.abs(zMax3d - zMin3d);
		
		// PLAN3D MCS
		//
		GeomPoint3d ptCenter3d = GeomUtil.midPointOf(ptMin3d, ptMax3d);
		
		double xCenter = ptCenter3d.getX();
		double yCenter = ptCenter3d.getY();
		double zCenter = ptCenter3d.getZ();
		
		double xDir = xCenter - 1.0;
		double yDir = yCenter;
		double zDir = zCenter;
		
		GeomPoint3d ptXDir = new GeomPoint3d(xDir, yDir, zDir);
		GeomVector3d vXDir = new GeomVector3d(ptCenter3d, ptXDir);

		GeomPlan3d planMcs = new GeomPlan3d(ptCenter3d, vXDir, wMcs, hMcs, zH);

		// PLAN2D SCR
		//
		GeomPlan2d planScr = new GeomPlan2d(this.planScr0);

		// CENTROID
		//		
		GeomPoint3d ptCentroid = new GeomPoint3d(ptCenter3d);
		double modelRadius = ptMin3d.distTo(ptMax3d);
		double obsRadius = modelRadius * AppDefs.MODEL_DIST_16X;
				
		// OBSERVER3D
		//
		double xObs3d = xCenter + obsRadius;
		double yObs3d = yCenter;
		double zObs3d = zCenter;

		GeomPoint3d ptObserver3d = new GeomPoint3d(xObs3d, yObs3d, zObs3d); 
		GeomVector3d vDir3d = new GeomVector3d(ptObserver3d, ptCenter3d);

		GeomObserver3d obsMcs = new GeomObserver3d(ptObserver3d, vDir3d);
		
		//RE-INIT_VIEW
		this.init(
			planMcs, 
			obsMcs, 
			planScr,
			//
			//Camera References
			//
			ptCentroid,
			modelRadius,
			obsRadius);
	}

	/* 3D-OBSERVER */

	@Override
	public void zoomMoveForwardBackwardMcs(double zoomFact) {
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		double modelMinRadius = this.modelRadius * AppDefs.ZOOM_FACTOR_MIN_DIST;
		double newObsRadius = this.obsRadius * zoomFact;
		if(newObsRadius < modelMinRadius)
			newObsRadius = modelMinRadius;

		double dist = this.obsRadius - newObsRadius;  
		this.obsMcs.moveForwardBackwardMcs(dist);		

		this.obsRadius = newObsRadius;		

		String warnmsg = String.format(
				"ModelMin:%s;CurrModel:%s;CurrObs:%s;\n", 
				nf6.format( modelMinRadius ),
				nf6.format( modelRadius ),
				nf6.format( obsRadius ) );
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL24, warnmsg, this.getClass());		
	}
	
	@Override
	public void zoomRotateUpDownDegrees(double angleDegrees) {
		this.obsMcs.rotateUpDownDegrees(this.ptCentroid, angleDegrees);
		
		//RE-INIT_VIEW
		this.init(
			this.planMcs, 
			this.obsMcs, 
			this.planScr,
			//
			//Camera References
			//
			this.ptCentroid,
			this.modelRadius,
			this.obsRadius);
	}
	
	@Override
	public void zoomRotateLeftRightDegrees(double angleDegrees) {
		this.obsMcs.rotateLeftRightDegrees(this.ptCentroid, angleDegrees);
		
		//RE-INIT_VIEW
		this.init(
			this.planMcs, 
			this.obsMcs, 
			this.planScr,
			//
			//Camera References
			//
			this.ptCentroid,
			this.modelRadius,
			this.obsRadius);
	}
	
//	@Override
//	public void zoomHeightUpDownMcs(double dist) { 
//		this.obsMcs.zoomHeightUpDownMcs(dist);
//		this.init(
//			this.planMcs, 
//			this.obsMcs, 
//			this.planScr,
//			//
//			//Camera References
//			//
//			this.ptCentroid,
//			this.modelRadius,
//			this.obsRadius);
//	}
//	
//	@Override
//	public void zoomTurnLeftRightMcs(double observerHorizDist) { 
//		this.obsMcs.turnLeftRightMcs(observerHorizDist);
//		this.init(
//			this.planMcs, 
//			this.obsMcs, 
//			this.planScr,
//			//
//			//Camera References
//			//
//			this.ptCentroid,
//			this.modelRadius,
//			this.obsRadius);
//	}
//	
//	@Override
//	public void zoomTurnUpDownMcs(double observerVertDist) { 
//		this.obsMcs.turnUpDownMcs(observerVertDist);
//		this.init(
//			this.planMcs, 
//			this.obsMcs, 
//			this.planScr,
//			//
//			//Camera References
//			//
//			this.ptCentroid,
//			this.modelRadius,
//			this.obsRadius);
//	}
//	
//	@Override
//	public void zoomTiltLeftRightMcs(double focusHorizDist) { 
//		this.obsMcs.zoomTiltLeftRightMcs(focusHorizDist);
//		this.init(
//			this.planMcs, 
//			this.obsMcs, 
//			this.planScr,
//			//
//			//Camera References
//			//
//			this.ptCentroid,
//			this.modelRadius,
//			this.obsRadius);
//	}
//	
//	@Override
//	public void zoomTiltUpDownMcs(double focusVertDist) { 
//		this.obsMcs.zoomTiltUpDownMcs(focusVertDist);
//		this.init(
//			this.planMcs, 
//			this.obsMcs, 
//			this.planScr,
//			//
//			//Camera References
//			//
//			this.ptCentroid,
//			this.modelRadius,
//			this.obsRadius);
//	}
	
	/* Getters/Setters */
	
	public String getDetailLevel()
	{
		return this.detailLevel;
	}

	public void setDetailLevel(String detailLevel)
	{
		this.detailLevel = detailLevel;
	}

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
	
	//LIMITS PROJ

	@Override	
	public GeomRect3d getLimitsProj() {
		return limitsProj;
	}

	//PLAN SCR
	
	@Override
	public GeomPlan2d getPlanScr() {
		return planScr;
	}

	//LIMITS SCR
	
	@Override	
	public GeomRect2d getLimitsScr() {
		return limitsScr;
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
