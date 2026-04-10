/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadViewPlan2d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
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

package br.com.tlmv.aicadxapp.cad;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
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
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class CadViewPlan2d implements ICadViewBase 
{
//Public Static
	public static final String sqlTableName = "cad_view2d";

	public static final String sqlCreate =
		//INITIAL_VIEW
	    "plan_mcs0_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_mcs0_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_mcs0_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "plan_proj0_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_proj0_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_proj0_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "plan_scr0_x	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_scr0_y 	#SQLTYPE_DBL# NOT NULL, " +

		//INITIAL_LIMITS
	    "limits_mcs0_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_mcs0_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_mcs0_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "limits_proj0_x #SQLTYPE_DBL# NOT NULL, " +
	    "limits_proj0_y #SQLTYPE_DBL# NOT NULL, " +
	    "limits_proj0_z #SQLTYPE_DBL# NOT NULL, " +
	    //
	    "limits_scr0_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_scr0_y 	#SQLTYPE_DBL# NOT NULL, " +

		//INITIAL_OBSERVER
	    "obs_mcs0_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "obs_mcs0_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "obs_mcs0_z 	#SQLTYPE_DBL# NOT NULL, " +

	    //INITIAL_SCALES
	    "scale_proj0 	#SQLTYPE_DBL# NOT NULL, " +
	    "scale_scr0 	#SQLTYPE_DBL# NOT NULL, " +

	    //CURRENT_MCS
	    //
	    "plan_mcs_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_mcs_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_mcs_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "limits_mcs_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_mcs_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_mcs_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "obs_mcs_x 		#SQLTYPE_DBL# NOT NULL, " +
	    "obs_mcs_y 		#SQLTYPE_DBL# NOT NULL, " +
	    "obs_mcs_z 		#SQLTYPE_DBL# NOT NULL, " +

	    //CURRENT_PROJ
	    //
	    "plan_proj_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_proj_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_proj_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "limits_proj_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_proj_y 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_proj_z 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "scale_proj0 	#SQLTYPE_DBL# NOT NULL, " +

	    //CURRENT_SCREEN
	    //
	    "plan_scr_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "plan_scr_y 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "limits_scr_x 	#SQLTYPE_DBL# NOT NULL, " +
	    "limits_scr_y 	#SQLTYPE_DBL# NOT NULL, " +
	    //
	    "scale_scr 		#SQLTYPE_DBL# NOT NULL ";

	public static final String sqlFields =
		//INITIAL_VIEW
	    "plan_mcs0_x, " +
	    "plan_mcs0_y, " +
	    "plan_mcs0_z, " +
	    //
	    "plan_proj0_x, " +
	    "plan_proj0_y, " +
	    "plan_proj0_z, " +
	    //
	    "plan_scr0_x, " +
	    "plan_scr0_y, " +

		//INITIAL_LIMITS
	    "limits_mcs0_x, " +
	    "limits_mcs0_y, " +
	    "limits_mcs0_z, " +
	    //
	    "limits_proj0_x, " +
	    "limits_proj0_y, " +
	    "limits_proj0_z, " +
	    //
	    "limits_scr0_x, " +
	    "limits_scr0_y, " +

		//INITIAL_OBSERVER
	    "obs_mcs0_x, " +
	    "obs_mcs0_y, " +
	    "obs_mcs0_z, " +

	    //INITIAL_SCALES
	    "scale_proj0, " +
	    "scale_scr0, " +

	    //CURRENT_MCS
	    //
	    "plan_mcs_x, " +
	    "plan_mcs_y, " +
	    "plan_mcs_z, " +
	    //
	    "limits_mcs_x, " +
	    "limits_mcs_y, " +
	    "limits_mcs_z, " +
	    //
	    "obs_mcs_x, " +
	    "obs_mcs_y, " +
	    "obs_mcs_z, " +

	    //CURRENT_PROJ
	    //
	    "plan_proj_x, " +
	    "plan_proj_y, " +
	    "plan_proj_z, " +
	    //
	    "limits_proj_x, " +
	    "limits_proj_y, " +
	    "limits_proj_z, " +
	    //
	    "scale_proj, " +

	    //CURRENT_SCR
	    //
	    "plan_scr_x, " +
	    "plan_scr_y, " +
	    //
	    "limits_scr_x, " +
	    "limits_scr_y, " +
	    //
	    "scale_scr ";
	
	public static final String sqlParams =
		"?, ?, ?, ?, ?, ?, ?, ?, " +
		"?, ?, ?, ?, ?, ?, ?, ?, " +
		"?, ?, ?, " +
		"?, ?, " +
		"?, ?, ?, ?, ?, ?, ?, ?, ?, " +
		"?, ?, ?, ?, ?, ?, ?, " +
		"?, ?, ?, ?, ? ";
			
//Private
	private CadDocumentDef doc;

	//InitialView
	private GeomPlan2d planMcs0;
	//InitialLimits
	private GeomRect2d limitsMcs0;

	//LastView
	private GeomPlan2d lastPlanMcs;
	//LastLimits
	private GeomRect2d lastLimitsMcs;
	
	//CurrentView
	private GeomPlan2d planMcs;
	//CurrentLimits
	private GeomRect2d limitsMcs;

	//CurrentScreen
	private GeomPlan2d planScr;
	private double scaleScr;		// SCALE = WIDTH_SCR / WIDTH_MCS
	
	private String detailLevel = AppDefs.DEF_DETAILLEVEL_HIGH;
	
	/* Methodes */
	
	private void adjustView()
	{
		double ratioScr = this.planScr.getRatio();
		this.planMcs.resetRatio(ratioScr);
		
		this.scaleScr = this.planScr.getWidth() / this.planMcs.getWidth();
		
		this.limitsMcs = new GeomRect2d(
			this.planMcs.getPtLowerLeftCorner(),
			this.planMcs.getPtUpperRightCorner() );
	}
	
//Public

	public CadViewPlan2d(
		CadDocumentDef doc,
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr)
	{
		this.init(
			doc,
			ptCenterMcs,
			xDirMcs,
			widthMcs,
			heightMcs,
			widthScr,
			heightScr);
	}
		
	public CadViewPlan2d(
		CadDocumentDef doc,
		GeomPlan2d planMcs,
		GeomPlan2d planScr)
	{
		this.init(
			doc,
			planMcs,
			planScr);
	}
	
	/* Methodes */
	
	// 2D-VIEW
	//
	@Override
	public void init(
		CadDocumentDef doc,
		GeomPoint2d ptCenterMcs,
		GeomVector2d xDirMcs,
		double widthMcs,
		double heightMcs,
		double widthScr,
		double heightScr)
	{
		double ratioScr = heightScr / widthScr;
		
		double newHeightMcs = widthMcs * ratioScr;

		GeomPlan2d planMcs = new GeomPlan2d(ptCenterMcs, xDirMcs, widthMcs, newHeightMcs);

		double xCenterScr = widthScr / 2.0;
		double yCenterScr = heightScr / 2.0;
		
		GeomPoint2d ptCenterScr = new GeomPoint2d(xCenterScr, yCenterScr);
		GeomPoint2d ptXDirScr = new GeomPoint2d(xCenterScr + 1.0, yCenterScr);
		
		GeomVector2d xDirScr = new GeomVector2d(ptCenterScr, ptXDirScr);		
		GeomPlan2d planScr = new GeomPlan2d(ptCenterScr, xDirScr, widthScr, heightScr);

		this.init(doc, planMcs, planScr);
	}
	
	@Override
	public void init(
		CadDocumentDef doc,
		GeomPlan2d planMcs,
		GeomPlan2d planScr)
	{
		this.doc = doc;
		
		this.planMcs = planMcs;
		this.planScr = planScr;

		this.lastPlanMcs = new GeomPlan2d( this.planMcs );
		//this.lastLimitsMcs = new GeomRect2d( this.limitsMcs );
		
		this.adjustView();
		this.saveView();
	}

	@Override
	public void init(
		CadDocumentDef doc,
		GeomPlan3d planMcs, 
		GeomPlan2d planScr) { }	

	@Override
	public void init(
		CadDocumentDef doc,
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
		CadDocumentDef doc,
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
		double obsRadius) { }

	@Override
	public void init(
		CadDocumentDef doc,
		GeomPlan3d planMcs, 
		GeomObserver3d obsMcs, 
		GeomPlan2d planScr,
		//
		//Camera References
		//
		GeomPoint3d ptCentroid,
		double modelRadius,
		double obsRadius) { }
	
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
		double ratioScr = heightScr / widthScr;

		double newHeightMcs = heightMcs * ratioScr;
		
		GeomPlan2d planMcs = new GeomPlan2d(ptCenterMcs, xDirMcs, widthMcs, newHeightMcs);
		
		double xCenterScr = widthScr / 2.0;
		double yCenterScr = heightScr / 2.0;
		
		GeomPoint2d ptCenterScr = new GeomPoint2d(xCenterScr, yCenterScr);
		GeomPoint2d ptXDirScr = new GeomPoint2d(xCenterScr + 1.0, yCenterScr);
		
		GeomVector2d xDirScr = new GeomVector2d(ptCenterScr, ptXDirScr);		
		GeomPlan2d planScr = new GeomPlan2d(ptCenterScr, xDirScr, widthScr, heightScr);

		this.init(this.doc, planMcs, planScr);
	}
	
	@Override
	public void reset(
		GeomPlan2d planMcs,
		GeomPlan2d planScr)
	{
		this.planMcs = planMcs;
		this.planScr = planScr;

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
	
	/* CAMERA REFERENCES */

	public void setCameraReference(GeomPoint3d ptCentroid, double modelDist, double obsDist) { }
		
	/* RE-CENTER */
	
	@Override
	public void moveToCenter(GeomPoint3d ptNewCenterMcs) { };

	/* OPERATIONS */

	// MCS_to_PROJ CONVERTION
	
	@Override
	public GeomPoint3d fromMcsToProj(GeomPoint3d pt3dMcs)
	{
		return null;
	}
	
	@Override
	public GeomVector3d fromProjToMcs(GeomPoint3d ptProj3d)
	{
		return null;
	}
	
	@Override
	public GeomPoint3d fromProjToMcs(GeomPoint3d ptProj3d, double zp)
	{
		return null;
	}

	// MCS_to_SCR CONVERTION
	
	@Override
	public double fromMcsToScr(double val)
	{
		double valScr = val * scaleScr;
		return valScr;
	}
	
	@Override
	public double fromScrToMcs(double val)
	{
		double valScr = val / scaleScr;
		return valScr;
	}
	
	@Override
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs)
	{
		GeomPoint2d ptCenterMcs = this.planMcs.getPtCenter();		
		GeomPoint2d ptCenterScr = this.planScr.getPtCenter();

		GeomVector2d vMcs = new GeomVector2d(ptCenterMcs, ptMcs);
		GeomVector2d vScr = vMcs.otherMult(scaleScr);
		
		GeomPoint2d ptScr = ptCenterScr.otherMoveTo(vScr, vScr.mod());
		return ptScr;
	}

	@Override
	public GeomPoint2d fromMcsToScr(GeomPoint2d ptMcs, GeomPoint2d ptBaseMcs, double rotate)
	{
		GeomPoint2d ptRotMcs = ptMcs.otherRotateTo(ptBaseMcs, rotate);
		
		GeomPoint2d ptCenterMcs = this.planMcs.getPtCenter();		
		GeomPoint2d ptCenterScr = this.planScr.getPtCenter();

		GeomVector2d vMcs = new GeomVector2d(ptCenterMcs, ptRotMcs);
		GeomVector2d vScr = vMcs.otherMult(scaleScr);
		
		GeomPoint2d ptScr = ptCenterScr.otherMoveTo(vScr, vScr.mod());
		return ptScr;		
	}
	
	@Override
	public GeomPoint2d fromScrToMcs(GeomPoint2d ptScr)
	{
		GeomVector2d vScr = new GeomVector2d(this.planScr.getPtCenter(), ptScr);
		GeomVector2d vMcs = vScr.otherMult(1.0 / scaleScr);

		GeomPoint2d ptMcs = this.planMcs.getPtCenter().otherMoveTo(vMcs, vMcs.mod());
		return ptMcs;
	}
	
	// PROJ_to_SCR CONVERTION
	
	@Override
	public double fromProjToScr(double val) {
		return 0.0;
	}

	@Override
	public double fromScrToProj(double val) {
		return 0.0;
	}

	@Override
	public GeomPoint2d fromProjToScr(GeomPoint3d ptProj) {
		return null;
	}

	@Override
	public GeomPoint3d fromScrToProj(GeomPoint2d ptScr) {
		return null;
	}
	
	// SCR_to_VIDEO CONVERTION
	
	@Override
	public boolean isVideoVisible(GeomPoint2d ptScr)
	{
		double xVideo = ptScr.getX();
		double yVideo = ptScr.getY();

		double w = this.planScr.getWidth();
		double h = this.planScr.getHeight();
		
		if( ( (xVideo >= 0) && (xVideo <= w) ) &&
			( (yVideo >= 0) && (yVideo <= h) ) ) { 
			return true;
		}
		return false;
	}
	
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
		this.planMcs0 = new GeomPlan2d(this.planMcs);
		this.limitsMcs0 = new GeomRect2d(this.limitsMcs);
	}
	
	@Override
	public void loadView()
	{
		this.planMcs = new GeomPlan2d(this.planMcs0);
		this.limitsMcs = new GeomRect2d(this.limitsMcs0);

		this.lastPlanMcs = new GeomPlan2d(this.planMcs);
		this.lastLimitsMcs = new GeomRect2d(this.limitsMcs);
	}
	
	/* PAN and ZOOM */
	
	@Override
	public void moveToMcs(GeomVector2d vDirMcs, double distMcs)
	{
		GeomPoint2d ptNewCenterMcs = this.planMcs.getPtCenter();
		ptNewCenterMcs.selfMoveTo(vDirMcs, distMcs);
		
		this.lastPlanMcs = new GeomPlan2d(this.planMcs);
		this.planMcs.resetOrigin(ptNewCenterMcs);
	}
	
	@Override
	public void moveToMcs(GeomPoint2d ptNewCenterMcs)
	{
		GeomPoint2d ptOldCenterMcs = this.planMcs.getPtCenter();
		
		GeomVector2d vDirMcs = new GeomVector2d(ptOldCenterMcs, ptNewCenterMcs);
		double distMcs = vDirMcs.mod();
		
		this.moveToMcs(vDirMcs, distMcs);
	}
	
	@Override
	public void moveToMcs(GeomVector3d vDirMcs, double distMcs) { }

	@Override
	public void moveToMcs(GeomPoint3d ptNewCenterMcs) { }

	@Override
	public void resizeToMcs(double w, double h)
	{
		this.lastPlanMcs = new GeomPlan2d(this.planMcs);
		this.planMcs.resetDimention(w, h);
		this.adjustView();
	}

	@Override
	public void resizeToScr(double w, double h)
	{
		this.lastPlanMcs = new GeomPlan2d(this.planMcs);
		this.planScr.resetDimention(w, h);
		this.adjustView();
	}

	@Override
	public void zoomScaleMcs(double sclFactor)
	{
		double w = this.planMcs.getWidth() * sclFactor;
		double h = this.planMcs.getHeight() * sclFactor;

		this.lastPlanMcs = new GeomPlan2d(this.planMcs);
		this.planMcs.resetDimention(w, h);
		this.adjustView();
	}
	
	@Override
	public void zoomMoveMcs(GeomPoint2d baseMousePosPanMcs, GeomPoint2d currMousePosPanMcs)
	{
		GeomVector2d vDir2d = new GeomVector2d(baseMousePosPanMcs, currMousePosPanMcs);
		//GeomVector2d vDir2d = new GeomVector2d(currMousePosPanMcs, baseMousePosPanMcs);
		double d = vDir2d.mod();
		
		GeomPoint2d ptCenter = this.planMcs.getPtCenter();

		GeomPoint2d newPtCenter = ptCenter.otherMoveTo(vDir2d, -d);

		this.lastPlanMcs = new GeomPlan2d(this.planMcs);
		this.planMcs.resetOrigin(newPtCenter);
	}

	@Override
	public void zoomCenterMcs(GeomPoint2d ptNewCenterMcs, double sclFactor)
	{
		this.moveToMcs(ptNewCenterMcs);
		this.zoomScaleMcs(sclFactor);
	}
		
	@Override
	public void zoomCenterMcs(GeomPoint3d ptNewCenterMcs, double sclFactor) { }

	@Override
	public void zoomWindowMcs(GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs)
	{
		GeomPoint2d ptNewCenterMcs = GeomUtil.midPointOf(ptMinMcs, ptMaxMcs);
		
		double xMinMcs = ptMinMcs.getX();
		double yMinMcs = ptMinMcs.getY();
		
		double xMaxMcs = ptMaxMcs.getX();
		double yMaxMcs = ptMaxMcs.getY();
		
		double w = xMaxMcs - xMinMcs;
		double h = yMaxMcs - yMinMcs;

		if(w < AppDefs.ZOOMMODE_ZOOMWINDOW_MINVAL)
			w = AppDefs.ZOOMMODE_ZOOMWINDOW_MINVAL;
		else if(w > AppDefs.ZOOMMODE_ZOOMWINDOW_MAXVAL)
			w = AppDefs.ZOOMMODE_ZOOMWINDOW_MAXVAL;

		if(h < AppDefs.ZOOMMODE_ZOOMWINDOW_MINVAL)
			h = AppDefs.ZOOMMODE_ZOOMWINDOW_MINVAL;
		else if(h > AppDefs.ZOOMMODE_ZOOMWINDOW_MAXVAL)
			h = AppDefs.ZOOMMODE_ZOOMWINDOW_MAXVAL;

		w = w * 1.5;
		h = h * 1.5;
		
		this.moveToMcs(ptNewCenterMcs);
		this.resizeToMcs(w, h);
	}
	
	@Override
	public void zoomWindowMcs(GeomPoint3d ptMinMcs, GeomPoint3d ptMaxMcs) { }

	@Override
	public void zoomExtMcs()
	{
		this.loadView();
		this.adjustView();
	}

	@Override
	public void zoomAllMcs(CadBlockDef blkDef)
	{
		GeomDimension2d oGeomDim = blkDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);
		
		GeomPoint2d ptMinMcs = new GeomPoint2d( oGeomDim.getPtMin() );
		GeomPoint2d ptMaxMcs = new GeomPoint2d( oGeomDim.getPtMax() );
		
		this.zoomWindowMcs(ptMinMcs, ptMaxMcs);
	}

	@Override
	public void zoomAllMcs(CadEntity[] arrEntity)
	{
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(arrEntity, AppDefs.OBJTYPE_ALL);
		
		GeomPoint2d ptMinMcs = new GeomPoint2d( oGeomDim.getPtMin() );
		GeomPoint2d ptMaxMcs = new GeomPoint2d( oGeomDim.getPtMax() );
		
		this.zoomWindowMcs(ptMinMcs, ptMaxMcs);
	}

	@Override
	public void zoomAllItemDataMcs(ArrayList<ItemDataVO> lsItemData)
	{
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(lsItemData);
		
		GeomPoint2d ptMinMcs = new GeomPoint2d( oGeomDim.getPtMin() );
		GeomPoint2d ptMaxMcs = new GeomPoint2d( oGeomDim.getPtMax() );
		
		this.zoomWindowMcs(ptMinMcs, ptMaxMcs);
	}

	@Override
	public void zoomToCenterMcs(CadBlockDef blkDef)
	{
		GeomDimension2d oGeomDim = blkDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);
		GeomPoint2d ptCenterMcs = new GeomPoint2d( oGeomDim.getPtCentroid() );
		
		this.moveToMcs(ptCenterMcs);
	}

	@Override
	public void zoomToCenterMcs(CadEntity[] arrEntity)
	{
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(arrEntity, AppDefs.OBJTYPE_ALL);
		GeomPoint2d ptCenterMcs = new GeomPoint2d( oGeomDim.getPtCentroid() );
		
		this.moveToMcs(ptCenterMcs);
	}

	@Override
	public void zoomToCenterItemDataMcs(ArrayList<ItemDataVO> lsItemData)
	{
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(lsItemData);
		GeomPoint2d ptCenterMcs = new GeomPoint2d( oGeomDim.getPtCentroid() );
		
		this.moveToMcs(ptCenterMcs);
	}
	
	/* 3D-VIEW */

	@Override
	public void zoomViewTopMcs(CadDocumentDef doc) { }

	@Override
	public void zoomViewBottomMcs(CadDocumentDef doc) { }

	@Override
	public void zoomViewFrontMcs(CadDocumentDef doc) { }

	@Override
	public void zoomViewBackMcs(CadDocumentDef doc) { }

	@Override
	public void zoomViewLeftMcs(CadDocumentDef doc) { }
	
	@Override
	public void zoomViewRightMcs(CadDocumentDef doc) { }
	
	/* 3D-OBSERVER */
		
	@Override
	public void zoomMoveForwardBackwardMcs(double dist) { }
	
	@Override
	public void zoomRotateUpDownDegrees(double angleDegrees) { }
	
	@Override
	public void zoomRotateLeftRightDegrees(double angleDegrees) { }

	/* Getters/Setters */
	
	public CadDocumentDef getDoc() {
		return doc;
	}

	public void setDoc(CadDocumentDef doc) {
		this.doc = doc;
	}
		
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

	//LIMITS PROJ
	
	@Override
	public GeomRect3d getLimitsProj() {
		return null;
	}
	
	//PLAN SCR
	
	@Override
	public GeomPlan2d getPlanScr() {
		return planScr;
	}

	//LIMITS SCR
	
	@Override
	public GeomRect2d getLimitsScr() {
		return null;
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

	//CHECKIF_VIEWCHANGED
	public boolean isChanged()
	{
		if( !this.lastPlanMcs.compareTo( this.planMcs ) )  
		{
			this.lastPlanMcs = new GeomPlan2d( this.planMcs );
			return true;
		}
		return false;
	}

}
