/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PrepareDrawUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
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

package br.com.tlmv.aicadxapp.cad.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeFace3d;
import br.com.tlmv.aicadxapp.cmp.CmpGeomFace3d;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class PrepareDrawUtil 
{
//Private
	private ArrayList<GeomFace3d> lsFace = null;
	
//Public
	
	public PrepareDrawUtil()
	{
		this.init();
	}
	
	/* Methodes */
	
	public void init()
	{
		this.lsFace = new ArrayList<GeomFace3d>();	
	}
	
	/* TRANSFORM */

	public void transformTo(GeomPoint3d ptRef, GeomVector3d vDir3d, double dist, double stepAngleXRad, double stepAngleYRad, double stepAngleZRad) {
		for(GeomFace3d oFace : this.lsFace) {
			oFace.transformTo(ptRef, vDir3d, dist, stepAngleXRad, stepAngleYRad, stepAngleZRad);
		}
	}
	
	/* OPERATIONS */
	
	public void addAll(PrepareDrawUtil prep)
	{
		for(GeomFace3d oFace : prep.lsFace) {
			this.lsFace.add(oFace);
		}
	}

	/* 3DFACE (P1, P2, P3) */
	
    public synchronized GeomFace3d addFace(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, GeomVector3d zDir)
    {
    	GeomPoint3d newPt0Mcs = GeomUtil.rotationXY(pt0Mcs, zDir);
    	GeomPoint3d newPt1Mcs = GeomUtil.rotationXY(pt1Mcs, zDir);
    	GeomPoint3d newPt2Mcs = GeomUtil.rotationXY(pt2Mcs, zDir);
    	
		GeomFace3d oFace = new GeomFace3d(oEnt, c, newPt0Mcs, newPt1Mcs, newPt2Mcs);
		this.lsFace.add(oFace);
		//
		return oFace;
    }

    /* 3DFACE (P1, P2, P3, P4) */
    
    public synchronized GeomFace3d addFace(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, GeomPoint3d pt3Mcs, GeomVector3d zDir)
    {
    	GeomPoint3d newPt0Mcs = GeomUtil.rotationXY(pt0Mcs, zDir);
    	GeomPoint3d newPt1Mcs = GeomUtil.rotationXY(pt1Mcs, zDir);
    	GeomPoint3d newPt2Mcs = GeomUtil.rotationXY(pt2Mcs, zDir);
    	GeomPoint3d newPt3Mcs = GeomUtil.rotationXY(pt3Mcs, zDir);
    	
		GeomFace3d oFace = new GeomFace3d(oEnt, c, newPt0Mcs, newPt1Mcs, newPt2Mcs, newPt3Mcs);
		this.lsFace.add(oFace);
		//
		return oFace;
    }
    
    /* 3DFACE (POINT_LIST) */
	
    public synchronized GeomFace3d addFace(ICadViewBase v, CadEntity oEnt, Color c, ArrayList<GeomPoint3d> lsPtsMcs, GeomVector3d zDir)
    {
		if(lsPtsMcs.size() < 3) return null;

    	ArrayList<GeomPoint3d> newLsPtsMcs = new ArrayList<GeomPoint3d>(); 
    	for(GeomPoint3d ptMcs : newLsPtsMcs) {
        	GeomPoint3d newPtMcs = GeomUtil.rotationXY(ptMcs, zDir);
        	newLsPtsMcs.add(newPtMcs);
    	}

    	GeomFace3d oFace = new GeomFace3d(oEnt, c, lsPtsMcs);
    	this.lsFace.add(oFace);
    	
    	return oFace;
    }
	
	/* 3DFACE (P1, W, H) */
	
    public void addFace(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptI3dMcs, double width, double height, GeomVector3d vDir3dMcs)
    {
    	GeomVector3d zDir = vDir3dMcs.otherUnit();
    	
    	//PT0
    	double xPt1Mcs = ptI3dMcs.getX();
    	double yPt1Mcs = ptI3dMcs.getY();
    	double zPt1Mcs = ptI3dMcs.getZ();
    	//PT1
    	double xPt2Mcs = xPt1Mcs + width;
    	double yPt2Mcs = yPt1Mcs;
    	double zPt2Mcs = zPt1Mcs;
    	//PT2
    	double xPt3Mcs = xPt1Mcs + width;
    	double yPt3Mcs = yPt1Mcs;
    	double zPt3Mcs = zPt1Mcs + height;
    	//PT3
    	double xPt4Mcs = xPt1Mcs;
    	double yPt4Mcs = yPt1Mcs;
    	double zPt4Mcs = zPt1Mcs + height;

    	GeomPoint3d pt1Mcs = new GeomPoint3d(xPt1Mcs, yPt1Mcs, zPt1Mcs); 
    	GeomPoint3d pt2Mcs = new GeomPoint3d(xPt2Mcs, yPt2Mcs, zPt2Mcs); 
    	GeomPoint3d pt3Mcs = new GeomPoint3d(xPt3Mcs, yPt3Mcs, zPt3Mcs); 
    	GeomPoint3d pt4Mcs = new GeomPoint3d(xPt4Mcs, yPt4Mcs, zPt4Mcs); 

        this.addFace(v, oEnt, c, pt1Mcs, pt2Mcs, pt3Mcs, pt4Mcs, zDir);
    }

	/* 3DFACE (P_MIN, P_MAX) */
	
    public void addBox(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenter3dMcs, double w, double h, double zH, double rotateRad, GeomVector3d vDir3dMcs)
    {
    	GeomVector3d zDir = null;
    	if(vDir3dMcs != null)
    		zDir = vDir3dMcs.otherUnit();
    	
    	double w2 = w / 2.0;
    	double h2 = h / 2.0;
    	
    	double xDir = Math.cos(rotateRad);
    	double yDir = Math.sin(rotateRad);
    	
    	GeomVector2d uDir = new GeomVector2d(xDir, yDir);    	
    	GeomVector2d nDir = uDir.otherNorm();
    	
    	double zPtBaseMcs = ptCenter3dMcs.getZ();
    	double zPtTopoMcs = zPtBaseMcs + zH;

    	// PLAN_PROJECTION
    	//
    	GeomPoint2d ptCenter2dMcs = new GeomPoint2d( ptCenter3dMcs );
    	
    	GeomPoint2d ptMidMcs = ptCenter2dMcs.otherMoveTo(uDir, - w2);
    	
    	GeomPoint2d pt1Mcs = ptMidMcs.otherMoveTo(nDir, - h2);
    	GeomPoint2d pt2Mcs = pt1Mcs.otherMoveTo(uDir, w);
    	GeomPoint2d pt3Mcs = pt2Mcs.otherMoveTo(nDir, h);
    	GeomPoint2d pt4Mcs = pt3Mcs.otherMoveTo(uDir, - w);

    	// 3D-POINTS
    	//
    	GeomPoint3d pt1Mcs3d = new GeomPoint3d(pt1Mcs.getX(), pt1Mcs.getY(), zPtBaseMcs); 
    	GeomPoint3d pt2Mcs3d = new GeomPoint3d(pt2Mcs.getX(), pt2Mcs.getY(), zPtBaseMcs); 
    	GeomPoint3d pt3Mcs3d = new GeomPoint3d(pt3Mcs.getX(), pt3Mcs.getY(), zPtBaseMcs); 
    	GeomPoint3d pt4Mcs3d = new GeomPoint3d(pt4Mcs.getX(), pt4Mcs.getY(), zPtBaseMcs); 
    			
    	GeomPoint3d pt5Mcs3d = new GeomPoint3d(pt1Mcs.getX(), pt1Mcs.getY(), zPtTopoMcs); 
    	GeomPoint3d pt6Mcs3d = new GeomPoint3d(pt2Mcs.getX(), pt2Mcs.getY(), zPtTopoMcs); 
    	GeomPoint3d pt7Mcs3d = new GeomPoint3d(pt3Mcs.getX(), pt3Mcs.getY(), zPtTopoMcs); 
    	GeomPoint3d pt8Mcs3d = new GeomPoint3d(pt4Mcs.getX(), pt4Mcs.getY(), zPtTopoMcs); 

    	//BASE
        this.addFace(v, oEnt, c, pt1Mcs3d, pt2Mcs3d, pt3Mcs3d, pt4Mcs3d, zDir);
        //TOPO
        this.addFace(v, oEnt, c, pt5Mcs3d, pt6Mcs3d, pt7Mcs3d, pt8Mcs3d, zDir);
        //FRONT
        this.addFace(v, oEnt, c, pt1Mcs3d, pt2Mcs3d, pt6Mcs3d, pt5Mcs3d, zDir);
        //RIGHT
        this.addFace(v, oEnt, c, pt2Mcs3d, pt3Mcs3d, pt7Mcs3d, pt6Mcs3d, zDir);
        //BACK
        this.addFace(v, oEnt, c, pt3Mcs3d, pt4Mcs3d, pt8Mcs3d, pt7Mcs3d, zDir);
        //LEFT
        this.addFace(v, oEnt, c, pt4Mcs3d, pt1Mcs3d, pt5Mcs3d, pt8Mcs3d, zDir);
    }
	
    public void addBox2Pt(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptMin3dMcs, GeomPoint3d ptMax3dMcs, GeomVector3d zDir3dMcs)
    {
    	GeomVector3d zDir = null;
    	if(zDir3dMcs != null)
    		zDir = zDir3dMcs.otherUnit();
    	
    	double xMinMcs = ptMin3dMcs.getX();
    	double yMinMcs = ptMin3dMcs.getY();
    	double zMinMcs = ptMin3dMcs.getZ();

    	double xMaxMcs = ptMax3dMcs.getX();
    	double yMaxMcs = ptMax3dMcs.getY();
    	double zMaxMcs = ptMax3dMcs.getZ();

    	GeomPoint3d pt1Mcs = new GeomPoint3d(xMinMcs, yMinMcs, zMinMcs); 
    	GeomPoint3d pt2Mcs = new GeomPoint3d(xMaxMcs, yMinMcs, zMinMcs); 
    	GeomPoint3d pt3Mcs = new GeomPoint3d(xMaxMcs, yMaxMcs, zMinMcs); 
    	GeomPoint3d pt4Mcs = new GeomPoint3d(xMinMcs, yMaxMcs, zMinMcs); 

    	GeomPoint3d pt5Mcs = new GeomPoint3d(xMinMcs, yMinMcs, zMaxMcs); 
    	GeomPoint3d pt6Mcs = new GeomPoint3d(xMaxMcs, yMinMcs, zMaxMcs); 
    	GeomPoint3d pt7Mcs = new GeomPoint3d(xMaxMcs, yMaxMcs, zMaxMcs); 
    	GeomPoint3d pt8Mcs = new GeomPoint3d(xMinMcs, yMaxMcs, zMaxMcs); 

        this.addFace(v, oEnt, c, pt1Mcs, pt2Mcs, pt3Mcs, pt4Mcs, zDir);
        this.addFace(v, oEnt, c, pt5Mcs, pt6Mcs, pt7Mcs, pt8Mcs, zDir);
        this.addFace(v, oEnt, c, pt1Mcs, pt2Mcs, pt6Mcs, pt5Mcs, zDir);
        this.addFace(v, oEnt, c, pt2Mcs, pt3Mcs, pt7Mcs, pt6Mcs, zDir);
        this.addFace(v, oEnt, c, pt3Mcs, pt4Mcs, pt8Mcs, pt7Mcs, zDir);
        this.addFace(v, oEnt, c, pt4Mcs, pt1Mcs, pt5Mcs, pt8Mcs, zDir);
    }
    
	/* 3DFACE (POINT_LIST1, POINT_LIST2) */

    public void addExternalFace(ICadViewBase v, CadEntity oEnt, Color c, ArrayList<GeomPoint3d> lsPts0Mcs, ArrayList<GeomPoint3d> lsPts1Mcs, GeomVector3d vDir3dMcs)
    {
    	int sz = lsPts0Mcs.size();
    	if(sz == 0) return;

    	GeomVector3d zDir = vDir3dMcs.otherUnit();

		GeomPoint3d ptI0Mcs = lsPts0Mcs.get(0);
		GeomPoint3d ptI1Mcs = lsPts1Mcs.get(0);
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF0Mcs = lsPts0Mcs.get(i);
			GeomPoint3d ptF1Mcs = lsPts1Mcs.get(i);

			this.addFace(v, oEnt, c, ptI0Mcs, ptI1Mcs, ptF1Mcs, ptF0Mcs, zDir);

			ptI0Mcs = ptF0Mcs;
			ptI1Mcs = ptF1Mcs;
		}
    }

    /* SHAPE3D */
	
	public void addShape3dMcs(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptInsMcs, GeomShape3d shape3d, double sclFact, double rotate, GeomVector3d zDir)
	{
		GeomPoint3d ptOrigem = new GeomPoint3d(0.0, 0.0, 0.0);
		
		GeomVector3d uZDir = null;
		if(zDir != null)
			uZDir = zDir.otherUnit();
		
		boolean bAnnotation = shape3d.isAnnotation();		
		int sz = shape3d.size();
		for(int i = 0; i < sz; i++) {
			ShapeFace3d oper = shape3d.getAt(i);
			boolean bFill = oper.isFill();
			
			ArrayList<GeomPoint3d> lsPts = oper.getLsPts();			
			if( bAnnotation ) {
				lsPts = GeomUtil.scaleTo(ptInsMcs, lsPts, sclFact);
			}
			ArrayList<GeomPoint3d> lsNewPts1 = GeomUtil.moveTo(ptOrigem, ptInsMcs, lsPts);
			ArrayList<GeomPoint3d> lsNewPts2 = GeomUtil.rotateTo(ptInsMcs, lsNewPts1, rotate);
			
			if( bFill ) {
				this.addFace(v, oEnt, c, lsNewPts2, uZDir);
			}
			else {
				this.addFace(v, oEnt, null, lsNewPts2, uZDir);		// empty 3d-face
			}
		}
	}

    /* CILINDER */
	
    public void addCilinder(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenterBase3dMcs, GeomVector3d vDir3dMcs, double dist, double radius, boolean bCloseBottom, boolean bCloseTop)
    {
    	NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
    	
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	GeomVector3d axisZ = GeomUtil.axisZ3d();
    	
    	GeomPoint3d ptCenterTop3dMcs = ptCenterBase3dMcs.otherMoveTo(axisZ, dist); 
    	
    	double xPtCenterBase3dMcs = ptCenterBase3dMcs.getX();
    	double yPtCenterBase3dMcs = ptCenterBase3dMcs.getY();
    	double zPtCenterBase3dMcs = ptCenterBase3dMcs.getZ();

    	GeomPoint3d ptIBase3dMcs = new GeomPoint3d(
			xPtCenterBase3dMcs + radius, 
			yPtCenterBase3dMcs, 
			zPtCenterBase3dMcs);
    	GeomVector3d vCenterToBase3dMcs = new GeomVector3d(ptCenterBase3dMcs, ptIBase3dMcs);

    	GeomPoint3d ptITop3dMcs = ptIBase3dMcs.otherMoveTo(axisZ, dist);
    	
		int nsteps = numsegs + 1;
    	double angleRad = 0.0;
		for(int i = 1; i <= nsteps; i++) {
			GeomVector3d vNewCenterToBase3dMcs = vCenterToBase3dMcs.otherRotateToRad(angleRad);

			GeomPoint3d ptFBase3dMcs = new GeomPoint3d(vNewCenterToBase3dMcs.getXF(), vNewCenterToBase3dMcs.getYF(), vNewCenterToBase3dMcs.getZF()); 
			GeomPoint3d ptFTop3dMcs = ptFBase3dMcs.otherMoveTo(axisZ, dist); 
			
			this.addFace(v, oEnt, c, ptIBase3dMcs, ptFBase3dMcs, ptFTop3dMcs, ptITop3dMcs, vDir3dMcs);
			angleRad += stepAngleRad; 
			
			if( bCloseTop ) {
				this.addFace(v, oEnt, c, ptITop3dMcs, ptFTop3dMcs, ptCenterTop3dMcs, vDir3dMcs);
			}

			if( bCloseBottom ) {
				this.addFace(v, oEnt, c, ptIBase3dMcs, ptFBase3dMcs, ptCenterBase3dMcs, vDir3dMcs);				
			}
			
			ptIBase3dMcs = ptFBase3dMcs;
			ptITop3dMcs = ptFTop3dMcs;
		}
    	
		//FINAL_FACE3D
		//
		GeomVector3d vNewCenterToBase3dMcs = vCenterToBase3dMcs.otherRotateToRad(angleRad);

		GeomPoint3d ptFBase3dMcs = new GeomPoint3d(vNewCenterToBase3dMcs.getXF(), vNewCenterToBase3dMcs.getYF(), vNewCenterToBase3dMcs.getZF()); 
		GeomPoint3d ptFTop3dMcs = ptFBase3dMcs.otherMoveTo(axisZ, dist); 
		
		this.addFace(v, oEnt, c, ptIBase3dMcs, ptFBase3dMcs, ptFTop3dMcs, ptITop3dMcs, vDir3dMcs);
		
		if( bCloseTop ) {
			this.addFace(v, oEnt, c, ptITop3dMcs, ptFTop3dMcs, ptCenterTop3dMcs, vDir3dMcs);
		}

		if( bCloseBottom ) {
			this.addFace(v, oEnt, c, ptIBase3dMcs, ptFBase3dMcs, ptCenterBase3dMcs, vDir3dMcs);				
		}
				
    }
    
    /* CONDUIT - SECTION_CIRCULAR */
    
    public void addConduitSectionCirc(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptI3d, GeomVector3d vDir3d, double dist, double radiusIntMcs, double radiusExtMcs, double thicknessMcs)
    {
    	GeomVector3d uDir3d = new GeomVector3d(vDir3d);

    	this.addPipeCilinder(v, oEnt, c, ptI3d, uDir3d, dist, radiusIntMcs, radiusExtMcs);

		int nsegs = (int) Math.floor(dist / AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH); 

		double d = radiusExtMcs + thicknessMcs;
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint3d ptSegI = ptI3d.otherMoveTo(uDir3d, currDist);

    		this.addPipeCilinder(v, oEnt, c, ptSegI, uDir3d, thicknessMcs, radiusExtMcs, d);

    		currDist += AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH;
        }
    }
    
    public void addConduitCilinder(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenterBase3dMcs, GeomVector3d uDir3dMcs, double dist, double intRadius, double extRadius)
    {
    	GeomPoint3d ptBase3d = new GeomPoint3d(ptCenterBase3dMcs);
    	GeomPoint3d ptTop3d = ptBase3d.otherMoveTo(uDir3dMcs, dist);
    	
    	GeomPoint2d ptBase2d = new GeomPoint2d(ptBase3d);
    	GeomPoint2d ptTop2d = new GeomPoint2d(ptTop3d);

    	GeomVector2d vDir2dMcs = new GeomVector2d(ptBase2d, ptTop2d);

    	GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();
    	GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();

    	double ang45Rad = Math.PI / 4.0;
    	
    	double cos45Rad = Math.cos(ang45Rad);
    	double sin45Rad = Math.sin(ang45Rad);
    	
    	/* INTERNAL_RADIUS 
    	 */
    	double wIntDist1 = intRadius * cos45Rad;
    	double zIntDist1 = intRadius * sin45Rad;
    	
		//PT_LEFT / PT_RIGHT
		GeomPoint2d ptBaseInt2d_left_0 = ptBase2d.otherMoveTo(nDir2dMcs, intRadius); 
		GeomPoint2d ptBaseInt2d_left_1 = ptBase2d.otherMoveTo(nDir2dMcs, wIntDist1); 
		GeomPoint2d ptBaseInt2d_right_0 = ptBase2d.otherMoveTo(nDir2dMcs, - intRadius); 
		GeomPoint2d ptBaseInt2d_right_1 = ptBase2d.otherMoveTo(nDir2dMcs, - wIntDist1); 

    	//PT_FACES
    	double z90p_int = ptBase3d.getZ() + intRadius;
    	double z45p_int = ptBase3d.getZ() + zIntDist1;
    	double z0_int = ptBase3d.getZ() + 0.0;
    	double z45n_int = ptBase3d.getZ() - zIntDist1;
    	double z90n_int = ptBase3d.getZ() - intRadius;
    	
    	/* INTERNAL_RADIUS - BASE/TOP POINTS
    	 */
    	GeomPoint3d ptBaseInt3d_0 = new GeomPoint3d(ptBaseInt2d_left_0.getX()  , ptBaseInt2d_left_0.getY()  , z0_int);  
    	GeomPoint3d ptBaseInt3d_1 = new GeomPoint3d(ptBaseInt2d_left_1.getX()  , ptBaseInt2d_left_1.getY()  , z45p_int);  
    	GeomPoint3d ptBaseInt3d_2 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90p_int);  
    	GeomPoint3d ptBaseInt3d_3 = new GeomPoint3d(ptBaseInt2d_right_1.getX() , ptBaseInt2d_right_1.getY() , z45p_int);  
    	GeomPoint3d ptBaseInt3d_4 = new GeomPoint3d(ptBaseInt2d_right_0.getX() , ptBaseInt2d_right_1.getY() , z0_int);  
    	GeomPoint3d ptBaseInt3d_5 = new GeomPoint3d(ptBaseInt2d_right_1.getX() , ptBaseInt2d_right_1.getY() , z45n_int);  
    	GeomPoint3d ptBaseInt3d_6 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90n_int);
    	GeomPoint3d ptBaseInt3d_7 = new GeomPoint3d(ptBaseInt2d_left_1.getX()  , ptBaseInt2d_left_1.getY()  , z45n_int);  
    	//
    	GeomPoint3d ptTopInt3d_0 = ptBaseInt3d_0.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_1 = ptBaseInt3d_1.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_2 = ptBaseInt3d_2.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_3 = ptBaseInt3d_3.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_4 = ptBaseInt3d_4.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_5 = ptBaseInt3d_5.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_6 = ptBaseInt3d_6.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_7 = ptBaseInt3d_7.otherMoveTo(uDir3dMcs, dist);  
		
    	/* EXTERNAL_RADIUS 
    	 */
    	double wExtDist1 = extRadius * cos45Rad;
    	double zExtDist1 = extRadius * sin45Rad;
    	    	
    	//PT_LEFT / PT_RIGHT
    	GeomPoint2d ptBaseExt2d_left_0 = ptBase2d.otherMoveTo(nDir2dMcs, extRadius); 
    	GeomPoint2d ptBaseExt2d_left_1 = ptBase2d.otherMoveTo(nDir2dMcs, wExtDist1); 
    	GeomPoint2d ptBaseExt2d_right_0 = ptBase2d.otherMoveTo(nDir2dMcs, - extRadius); 
    	GeomPoint2d ptBaseExt2d_right_1 = ptBase2d.otherMoveTo(nDir2dMcs, - wExtDist1); 

    	//PT_FACES
    	double z90p_ext = ptBase3d.getZ() + extRadius;
    	double z45p_ext = ptBase3d.getZ() + zExtDist1;
    	double z0_ext = ptBase3d.getZ() + 0.0;
    	double z45n_ext = ptBase3d.getZ() - zExtDist1;
    	double z90n_ext = ptBase3d.getZ() - extRadius;
    	
    	/* EXTERNAL_RADIUS - BASE/TOP POINTS
    	 */
    	GeomPoint3d ptBaseExt3d_0 = new GeomPoint3d(ptBaseExt2d_left_0.getX()  , ptBaseExt2d_left_0.getY()  , z0_ext);  
    	GeomPoint3d ptBaseExt3d_1 = new GeomPoint3d(ptBaseExt2d_left_1.getX()  , ptBaseExt2d_left_1.getY()  , z45p_ext);  
    	GeomPoint3d ptBaseExt3d_2 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90p_ext);  
    	GeomPoint3d ptBaseExt3d_3 = new GeomPoint3d(ptBaseExt2d_right_1.getX() , ptBaseExt2d_right_1.getY() , z45p_ext);  
    	GeomPoint3d ptBaseExt3d_4 = new GeomPoint3d(ptBaseExt2d_right_0.getX() , ptBaseExt2d_right_1.getY() , z0_ext);  
    	GeomPoint3d ptBaseExt3d_5 = new GeomPoint3d(ptBaseExt2d_right_1.getX() , ptBaseExt2d_right_1.getY() , z45n_ext);  
    	GeomPoint3d ptBaseExt3d_6 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90n_ext);
    	GeomPoint3d ptBaseExt3d_7 = new GeomPoint3d(ptBaseExt2d_left_1.getX()  , ptBaseExt2d_left_1.getY()  , z45n_ext);  
    	//
    	GeomPoint3d ptTopExt3d_0 = ptBaseExt3d_0.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_1 = ptBaseExt3d_1.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_2 = ptBaseExt3d_2.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_3 = ptBaseExt3d_3.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_4 = ptBaseExt3d_4.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_5 = ptBaseExt3d_5.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_6 = ptBaseExt3d_6.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_7 = ptBaseExt3d_7.otherMoveTo(uDir3dMcs, dist);  
    	
    	/* DRAW_EXTERNAL_CILINDER
    	 */
		this.addFace(v, oEnt, c, ptBaseExt3d_0,  ptBaseExt3d_1, ptTopExt3d_1, ptTopExt3d_0,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_1,  ptBaseExt3d_2, ptTopExt3d_2, ptTopExt3d_1,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_2,  ptBaseExt3d_3, ptTopExt3d_3, ptTopExt3d_2,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_3,  ptBaseExt3d_4, ptTopExt3d_4, ptTopExt3d_3,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_4,  ptBaseExt3d_5, ptTopExt3d_5, ptTopExt3d_4,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_5,  ptBaseExt3d_6, ptTopExt3d_6, ptTopExt3d_5,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_6,  ptBaseExt3d_7, ptTopExt3d_7, ptTopExt3d_6,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_7,  ptBaseExt3d_0, ptTopExt3d_0, ptTopExt3d_7,  null);
		
    	/* DRAW_FINISH_FACES (BASE)
    	 */
		this.addFace(v, oEnt, c, ptBaseExt3d_0, ptBaseExt3d_1, ptBaseInt3d_1, ptBaseInt3d_0, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_1, ptBaseExt3d_2, ptBaseInt3d_2, ptBaseInt3d_1, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_2, ptBaseExt3d_3, ptBaseInt3d_3, ptBaseInt3d_2, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_3, ptBaseExt3d_4, ptBaseInt3d_4, ptBaseInt3d_3, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_4, ptBaseExt3d_5, ptBaseInt3d_5, ptBaseInt3d_4, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_5, ptBaseExt3d_6, ptBaseInt3d_6, ptBaseInt3d_5, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_6, ptBaseExt3d_7, ptBaseInt3d_7, ptBaseInt3d_6, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_7, ptBaseExt3d_0, ptBaseInt3d_0, ptBaseInt3d_7, null);		
		
    	/* DRAW_FINISH_FACES (TOP)
    	 */
		this.addFace(v, oEnt, c, ptTopExt3d_0, ptTopExt3d_1, ptTopInt3d_1, ptTopInt3d_0, null);
		this.addFace(v, oEnt, c, ptTopExt3d_1, ptTopExt3d_2, ptTopInt3d_2, ptTopInt3d_1, null);
		this.addFace(v, oEnt, c, ptTopExt3d_2, ptTopExt3d_3, ptTopInt3d_3, ptTopInt3d_2, null);
		this.addFace(v, oEnt, c, ptTopExt3d_3, ptTopExt3d_4, ptTopInt3d_4, ptTopInt3d_3, null);
		this.addFace(v, oEnt, c, ptTopExt3d_4, ptTopExt3d_5, ptTopInt3d_5, ptTopInt3d_4, null);
		this.addFace(v, oEnt, c, ptTopExt3d_5, ptTopExt3d_6, ptTopInt3d_6, ptTopInt3d_5, null);
		this.addFace(v, oEnt, c, ptTopExt3d_6, ptTopExt3d_7, ptTopInt3d_7, ptTopInt3d_6, null);
		this.addFace(v, oEnt, c, ptTopExt3d_7, ptTopExt3d_0, ptTopInt3d_0, ptTopInt3d_7, null);		
    }
    
    /* PIPE - SECTION_CIRCULAR */
    
    public void addPipeSectionCirc(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptI3d, GeomVector3d vDir3d, double dist, double radiusIntMcs, double radiusExtMcs, double thicknessMcs)
    {
    	GeomVector3d uDir3d = new GeomVector3d(vDir3d);

    	this.addPipeCilinder(v, oEnt, c, ptI3d, uDir3d, dist, radiusIntMcs, radiusExtMcs);

		int nsegs = (int) Math.floor(dist / AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH); 

		double d = radiusExtMcs + thicknessMcs;
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint3d ptSegI = ptI3d.otherMoveTo(uDir3d, currDist);

    		this.addPipeCilinder(v, oEnt, c, ptSegI, uDir3d, thicknessMcs, radiusExtMcs, d);

    		currDist += AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH;
        }
    }
    
    public void addPipeCilinder(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenterBase3dMcs, GeomVector3d uDir3dMcs, double dist, double intRadius, double extRadius)
    {
    	GeomPoint3d ptBase3d = new GeomPoint3d(ptCenterBase3dMcs);
    	GeomPoint3d ptTop3d = ptBase3d.otherMoveTo(uDir3dMcs, dist);
    	
    	GeomPoint2d ptBase2d = new GeomPoint2d(ptBase3d);
    	GeomPoint2d ptTop2d = new GeomPoint2d(ptTop3d);

    	GeomVector2d vDir2dMcs = new GeomVector2d(ptBase2d, ptTop2d);

    	GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();
    	GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();

    	double ang45Rad = Math.PI / 4.0;
    	
    	double cos45Rad = Math.cos(ang45Rad);
    	double sin45Rad = Math.sin(ang45Rad);
    	
    	/* INTERNAL_RADIUS 
    	 */
    	double wIntDist1 = intRadius * cos45Rad;
    	double zIntDist1 = intRadius * sin45Rad;
    	
		//PT_LEFT / PT_RIGHT
		GeomPoint2d ptBaseInt2d_left_0 = ptBase2d.otherMoveTo(nDir2dMcs, intRadius); 
		GeomPoint2d ptBaseInt2d_left_1 = ptBase2d.otherMoveTo(nDir2dMcs, wIntDist1); 
		GeomPoint2d ptBaseInt2d_right_0 = ptBase2d.otherMoveTo(nDir2dMcs, - intRadius); 
		GeomPoint2d ptBaseInt2d_right_1 = ptBase2d.otherMoveTo(nDir2dMcs, - wIntDist1); 

    	//PT_FACES
    	double z90p_int = ptBase3d.getZ() + intRadius;
    	double z45p_int = ptBase3d.getZ() + zIntDist1;
    	double z0_int = ptBase3d.getZ() + 0.0;
    	double z45n_int = ptBase3d.getZ() - zIntDist1;
    	double z90n_int = ptBase3d.getZ() - intRadius;
    	
    	/* INTERNAL_RADIUS - BASE/TOP POINTS
    	 */
    	GeomPoint3d ptBaseInt3d_0 = new GeomPoint3d(ptBaseInt2d_left_0.getX()  , ptBaseInt2d_left_0.getY()  , z0_int);  
    	GeomPoint3d ptBaseInt3d_1 = new GeomPoint3d(ptBaseInt2d_left_1.getX()  , ptBaseInt2d_left_1.getY()  , z45p_int);  
    	GeomPoint3d ptBaseInt3d_2 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90p_int);  
    	GeomPoint3d ptBaseInt3d_3 = new GeomPoint3d(ptBaseInt2d_right_1.getX() , ptBaseInt2d_right_1.getY() , z45p_int);  
    	GeomPoint3d ptBaseInt3d_4 = new GeomPoint3d(ptBaseInt2d_right_0.getX() , ptBaseInt2d_right_1.getY() , z0_int);  
    	GeomPoint3d ptBaseInt3d_5 = new GeomPoint3d(ptBaseInt2d_right_1.getX() , ptBaseInt2d_right_1.getY() , z45n_int);  
    	GeomPoint3d ptBaseInt3d_6 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90n_int);
    	GeomPoint3d ptBaseInt3d_7 = new GeomPoint3d(ptBaseInt2d_left_1.getX()  , ptBaseInt2d_left_1.getY()  , z45n_int);  
    	//
    	GeomPoint3d ptTopInt3d_0 = ptBaseInt3d_0.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_1 = ptBaseInt3d_1.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_2 = ptBaseInt3d_2.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_3 = ptBaseInt3d_3.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_4 = ptBaseInt3d_4.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_5 = ptBaseInt3d_5.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_6 = ptBaseInt3d_6.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_7 = ptBaseInt3d_7.otherMoveTo(uDir3dMcs, dist);  
		
    	/* EXTERNAL_RADIUS 
    	 */
    	double wExtDist1 = extRadius * cos45Rad;
    	double zExtDist1 = extRadius * sin45Rad;
    	    	
    	//PT_LEFT / PT_RIGHT
    	GeomPoint2d ptBaseExt2d_left_0 = ptBase2d.otherMoveTo(nDir2dMcs, extRadius); 
    	GeomPoint2d ptBaseExt2d_left_1 = ptBase2d.otherMoveTo(nDir2dMcs, wExtDist1); 
    	GeomPoint2d ptBaseExt2d_right_0 = ptBase2d.otherMoveTo(nDir2dMcs, - extRadius); 
    	GeomPoint2d ptBaseExt2d_right_1 = ptBase2d.otherMoveTo(nDir2dMcs, - wExtDist1); 

    	//PT_FACES
    	double z90p_ext = ptBase3d.getZ() + extRadius;
    	double z45p_ext = ptBase3d.getZ() + zExtDist1;
    	double z0_ext = ptBase3d.getZ() + 0.0;
    	double z45n_ext = ptBase3d.getZ() - zExtDist1;
    	double z90n_ext = ptBase3d.getZ() - extRadius;
    	
    	/* EXTERNAL_RADIUS - BASE/TOP POINTS
    	 */
    	GeomPoint3d ptBaseExt3d_0 = new GeomPoint3d(ptBaseExt2d_left_0.getX()  , ptBaseExt2d_left_0.getY()  , z0_ext);  
    	GeomPoint3d ptBaseExt3d_1 = new GeomPoint3d(ptBaseExt2d_left_1.getX()  , ptBaseExt2d_left_1.getY()  , z45p_ext);  
    	GeomPoint3d ptBaseExt3d_2 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90p_ext);  
    	GeomPoint3d ptBaseExt3d_3 = new GeomPoint3d(ptBaseExt2d_right_1.getX() , ptBaseExt2d_right_1.getY() , z45p_ext);  
    	GeomPoint3d ptBaseExt3d_4 = new GeomPoint3d(ptBaseExt2d_right_0.getX() , ptBaseExt2d_right_1.getY() , z0_ext);  
    	GeomPoint3d ptBaseExt3d_5 = new GeomPoint3d(ptBaseExt2d_right_1.getX() , ptBaseExt2d_right_1.getY() , z45n_ext);  
    	GeomPoint3d ptBaseExt3d_6 = new GeomPoint3d(ptBase2d.getX()            , ptBase2d.getY()            , z90n_ext);
    	GeomPoint3d ptBaseExt3d_7 = new GeomPoint3d(ptBaseExt2d_left_1.getX()  , ptBaseExt2d_left_1.getY()  , z45n_ext);  
    	//
    	GeomPoint3d ptTopExt3d_0 = ptBaseExt3d_0.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_1 = ptBaseExt3d_1.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_2 = ptBaseExt3d_2.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_3 = ptBaseExt3d_3.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_4 = ptBaseExt3d_4.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_5 = ptBaseExt3d_5.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_6 = ptBaseExt3d_6.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_7 = ptBaseExt3d_7.otherMoveTo(uDir3dMcs, dist);  
    	
    	/* DRAW_EXTERNAL_CILINDER
    	 */
		this.addFace(v, oEnt, c, ptBaseExt3d_0,  ptBaseExt3d_1, ptTopExt3d_1, ptTopExt3d_0,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_1,  ptBaseExt3d_2, ptTopExt3d_2, ptTopExt3d_1,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_2,  ptBaseExt3d_3, ptTopExt3d_3, ptTopExt3d_2,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_3,  ptBaseExt3d_4, ptTopExt3d_4, ptTopExt3d_3,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_4,  ptBaseExt3d_5, ptTopExt3d_5, ptTopExt3d_4,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_5,  ptBaseExt3d_6, ptTopExt3d_6, ptTopExt3d_5,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_6,  ptBaseExt3d_7, ptTopExt3d_7, ptTopExt3d_6,  null);
		this.addFace(v, oEnt, c, ptBaseExt3d_7,  ptBaseExt3d_0, ptTopExt3d_0, ptTopExt3d_7,  null);
		
    	/* DRAW_FINISH_FACES (BASE)
    	 */
		this.addFace(v, oEnt, c, ptBaseExt3d_0, ptBaseExt3d_1, ptBaseInt3d_1, ptBaseInt3d_0, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_1, ptBaseExt3d_2, ptBaseInt3d_2, ptBaseInt3d_1, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_2, ptBaseExt3d_3, ptBaseInt3d_3, ptBaseInt3d_2, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_3, ptBaseExt3d_4, ptBaseInt3d_4, ptBaseInt3d_3, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_4, ptBaseExt3d_5, ptBaseInt3d_5, ptBaseInt3d_4, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_5, ptBaseExt3d_6, ptBaseInt3d_6, ptBaseInt3d_5, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_6, ptBaseExt3d_7, ptBaseInt3d_7, ptBaseInt3d_6, null);
		this.addFace(v, oEnt, c, ptBaseExt3d_7, ptBaseExt3d_0, ptBaseInt3d_0, ptBaseInt3d_7, null);		
		
    	/* DRAW_FINISH_FACES (TOP)
    	 */
		this.addFace(v, oEnt, c, ptTopExt3d_0, ptTopExt3d_1, ptTopInt3d_1, ptTopInt3d_0, null);
		this.addFace(v, oEnt, c, ptTopExt3d_1, ptTopExt3d_2, ptTopInt3d_2, ptTopInt3d_1, null);
		this.addFace(v, oEnt, c, ptTopExt3d_2, ptTopExt3d_3, ptTopInt3d_3, ptTopInt3d_2, null);
		this.addFace(v, oEnt, c, ptTopExt3d_3, ptTopExt3d_4, ptTopInt3d_4, ptTopInt3d_3, null);
		this.addFace(v, oEnt, c, ptTopExt3d_4, ptTopExt3d_5, ptTopInt3d_5, ptTopInt3d_4, null);
		this.addFace(v, oEnt, c, ptTopExt3d_5, ptTopExt3d_6, ptTopInt3d_6, ptTopInt3d_5, null);
		this.addFace(v, oEnt, c, ptTopExt3d_6, ptTopExt3d_7, ptTopInt3d_7, ptTopInt3d_6, null);
		this.addFace(v, oEnt, c, ptTopExt3d_7, ptTopExt3d_0, ptTopInt3d_0, ptTopInt3d_7, null);		
    }

    /* PIPE - SECTION_RECTANGULAR */
    
    public void addPipeSectionRect(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptI3d, GeomVector3d vDir3d, double dist, double widthIntMcs, double heightIntMcs, double widthExtMcs, double heightExtMcs, double thicknessMcs)
    {
    	GeomVector3d uDir3d = new GeomVector3d(vDir3d);

    	this.addPipeRectangle(v, oEnt, c, ptI3d, uDir3d, dist, widthIntMcs, heightIntMcs, widthExtMcs, heightExtMcs, thicknessMcs);

		int nsegs = (int) Math.floor(dist / AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH); 

		double dW = widthExtMcs + thicknessMcs;
		double dH = heightExtMcs + thicknessMcs;
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint3d ptSegI = ptI3d.otherMoveTo(uDir3d, currDist);

    		this.addPipeRectangle(v, oEnt, c, ptSegI, uDir3d, thicknessMcs, widthExtMcs, heightExtMcs, dW, dH, thicknessMcs);

    		currDist += AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH;
        }
    }
    
    public void addPipeRectangle(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenterBase3dMcs, GeomVector3d vDir3dMcs, double dist, double widthIntMcs, double heightIntMcs, double widthExtMcs, double heightExtMcs, double thicknessMcs)
    {
    	GeomVector3d uDir3dMcs = vDir3dMcs.otherUnit();
    	
    	GeomPoint3d ptBase3d = new GeomPoint3d(ptCenterBase3dMcs);
    	GeomPoint3d ptTop3d = ptBase3d.otherMoveTo(uDir3dMcs, dist);
    	
    	GeomPoint2d ptBase2d = new GeomPoint2d(ptBase3d);
    	GeomPoint2d ptTop2d = new GeomPoint2d(ptTop3d);

    	GeomVector2d vDir2dMcs = new GeomVector2d(ptBase2d, ptTop2d);

    	GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();
    	GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();

    	double widthExtMcs2 = widthExtMcs / 2.0;
    	double heightExtMcs2 = heightExtMcs / 2.0;

    	double widthIntMcs2 = widthIntMcs / 2.0;
    	double heightIntMcs2 = heightIntMcs / 2.0;
    	
		//PT_LEFT / PT_RIGHT
		GeomPoint2d ptBaseExt2d_left  = ptBase2d.otherMoveTo(nDir2dMcs,   widthExtMcs2); 
		GeomPoint2d ptBaseInt2d_left  = ptBase2d.otherMoveTo(nDir2dMcs,   widthIntMcs2); 
		GeomPoint2d ptBaseInt2d_right = ptBase2d.otherMoveTo(nDir2dMcs, - widthIntMcs2); 
		GeomPoint2d ptBaseExt2d_right = ptBase2d.otherMoveTo(nDir2dMcs, - widthExtMcs2); 

    	//PT_TOP / PT_BOTTOM
    	double z90p_ext = ptBase3d.getZ() + heightExtMcs2;
    	double z90n_ext = ptBase3d.getZ() - heightExtMcs2;
    	//double z0_int   = ptBase3d.getZ() + 0.0;
    	//double z90p_int = ptBase3d.getZ() + heightIntMcs2;
    	double z90n_int = ptBase3d.getZ() - heightIntMcs2;
    	
    	/* FACE_I (POINTS)
    	 */
    	GeomPoint3d ptBase0_left  = new GeomPoint3d(ptBaseExt2d_left.getX(), ptBaseExt2d_left.getY(), z90n_ext);  
    	GeomPoint3d ptBase1_left  = new GeomPoint3d(ptBaseExt2d_left.getX(), ptBaseExt2d_left.getY(), z90p_ext);  
    	GeomPoint3d ptBase2_left  = new GeomPoint3d(ptBaseInt2d_left.getX(), ptBaseInt2d_left.getY(), z90p_ext);  
    	GeomPoint3d ptBase3_left  = new GeomPoint3d(ptBaseInt2d_left.getX(), ptBaseInt2d_left.getY(), z90n_int);  
    	//
    	GeomPoint3d ptBase0_right = new GeomPoint3d(ptBaseExt2d_right.getX(), ptBaseExt2d_right.getY(), z90n_ext);  
    	GeomPoint3d ptBase1_right = new GeomPoint3d(ptBaseExt2d_right.getX(), ptBaseExt2d_right.getY(), z90p_ext);  
    	GeomPoint3d ptBase2_right = new GeomPoint3d(ptBaseInt2d_right.getX(), ptBaseInt2d_right.getY(), z90p_ext);  
    	GeomPoint3d ptBase3_right = new GeomPoint3d(ptBaseInt2d_right.getX(), ptBaseInt2d_right.getY(), z90n_int);  
    	
    	/* FACE_II (POINTS)
    	 */
    	GeomPoint3d ptTop0_left = ptBase0_left.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTop1_left = ptBase1_left.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTop2_left = ptBase2_left.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTop3_left = ptBase3_left.otherMoveTo(uDir3dMcs, dist);
    	//
    	GeomPoint3d ptTop0_right = ptBase0_right.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTop1_right = ptBase1_right.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTop2_right = ptBase2_right.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTop3_right = ptBase3_right.otherMoveTo(uDir3dMcs, dist);  
    	
    	/* DRAW_FACES
    	 */
		this.addFace(v, oEnt, c, ptBase0_left, ptBase1_left, ptTop1_left, ptTop0_left, null);
		this.addFace(v, oEnt, c, ptBase1_left, ptBase2_left, ptTop2_left, ptTop1_left, null);
		this.addFace(v, oEnt, c, ptBase2_left, ptBase3_left, ptTop3_left, ptTop2_left, null);
		//
		this.addFace(v, oEnt, c, ptBase3_left, ptBase3_right, ptTop3_right, ptTop3_left, null);
		//
		this.addFace(v, oEnt, c, ptBase2_right, ptBase3_right, ptTop3_right, ptTop2_right, null);
		this.addFace(v, oEnt, c, ptBase1_right, ptBase2_right, ptTop2_right, ptTop1_right, null);
		this.addFace(v, oEnt, c, ptBase0_right, ptBase1_right, ptTop1_right, ptTop0_right, null);
		//
		this.addFace(v, oEnt, c, ptBase0_left, ptBase0_right, ptTop0_right, ptTop0_left, null);
    }

    /* SPHERE_CILINDER */
	
    public void addSphereCilinder(
    	ICadViewBase v, 
    	CadEntity oEnt, 
    	Color c, 
    	GeomPoint2d ptCenter2dMcs, 
    	GeomVector2d vCurrDir2dMcs, 
    	GeomVector2d vNextDir2dMcs, 
    	double radius, 
    	double zLevel,
    	GeomVector3d zDir3dMcs)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double currAngleRad = 0.0;
		while(currAngleRad < AppDefs.MATHVAL_2PI / 2.0) {
	    	double nextAngleRad = currAngleRad + stepAngleRad;

	    	//PT-I
	    	double dI = radius * Math.cos(nextAngleRad);	    	

	    	double hI_top = zLevel + radius * Math.sin(nextAngleRad);
	    	double hI_bottom = zLevel - radius * Math.sin(nextAngleRad);
	    	
	    	GeomPoint2d ptCurrI2d = ptCenter2dMcs.otherMoveTo(vCurrDir2dMcs, dI);
	    	GeomPoint2d ptNextI2d = ptCenter2dMcs.otherMoveTo(vNextDir2dMcs, dI);

	    	//PT-F
	    	double dF = radius * Math.cos(currAngleRad);	    	

	    	double hF_top = zLevel + radius * Math.sin(currAngleRad);
	    	double hF_bottom = zLevel - radius * Math.sin(currAngleRad);
	    	
	    	GeomPoint2d ptCurrF2d = ptCenter2dMcs.otherMoveTo(vCurrDir2dMcs, dF);
	    	GeomPoint2d ptNextF2d = ptCenter2dMcs.otherMoveTo(vNextDir2dMcs, dF);

	    	//POINT-3D
	    	//
	    	//TOP
	    	GeomPoint3d ptCurrI3d_top = new GeomPoint3d(ptCurrI2d, hI_top);
	    	GeomPoint3d ptNextI3d_top = new GeomPoint3d(ptNextI2d, hI_top);

	    	GeomPoint3d ptCurrF3d_top = new GeomPoint3d(ptCurrF2d, hF_top);
	    	GeomPoint3d ptNextF3d_top = new GeomPoint3d(ptNextF2d, hF_top);
	    	
    		this.addFace(v, oEnt, c, ptCurrI3d_top, ptCurrF3d_top, ptNextF3d_top, ptNextI3d_top, zDir3dMcs);

	    	//BOTTOM
	    	GeomPoint3d ptCurrI3d_bottom = new GeomPoint3d(ptCurrI2d, hI_bottom);
	    	GeomPoint3d ptNextI3d_bottom = new GeomPoint3d(ptNextI2d, hI_bottom);

	    	GeomPoint3d ptCurrF3d_bottom = new GeomPoint3d(ptCurrF2d, hF_bottom);
	    	GeomPoint3d ptNextF3d_bottom = new GeomPoint3d(ptNextF2d, hF_bottom);
	    	
    		this.addFace(v, oEnt, c, ptCurrI3d_bottom, ptCurrF3d_bottom, ptNextF3d_bottom, ptNextI3d_bottom, zDir3dMcs);
    		
        	currAngleRad = nextAngleRad;
    	}
    }
        
    public void addSphere(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenter3dMcs, double radius, GeomVector3d zDir3dMcs)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double zPtCenter = ptCenter3dMcs.getZ();

    	GeomVector3d zDir = null;
    	if(zDir3dMcs != null)
    		zDir = zDir3dMcs.otherUnit();
    	
		GeomPoint2d ptCenter = new GeomPoint2d( ptCenter3dMcs );
		double xCenter = ptCenter.getX();
		double yCenter = ptCenter.getY();
		
    	GeomPoint2d ptDir = new GeomPoint2d( xCenter + 1.0, yCenter );
    	GeomVector2d vDir = new GeomVector2d( ptCenter, ptDir );
    	
    	double currAngleRad = 0.0;
    	while(currAngleRad < AppDefs.MATHVAL_2PI) {
	    	double nextAngleRad = currAngleRad + stepAngleRad;

	    	GeomVector2d vCurrDir = vDir.otherRotateToRad(currAngleRad);
	    	GeomVector2d vNextDir = vDir.otherRotateToRad(nextAngleRad);

	    	this.addSphereCilinder( v, oEnt, c, ptCenter, vCurrDir, vNextDir, radius, zPtCenter, zDir);
	    	
	    	currAngleRad = nextAngleRad;    	
		}		

    }

    /* TORUS_CILINDER */
	
    public void addTorusCilinder(
    	ICadViewBase v, 
    	CadEntity oEnt, 
    	Color c, 
    	GeomPoint2d ptCurrCenter2dMcs, 
    	GeomVector2d vCurrDir2dMcs, 
    	GeomPoint2d ptNextCenter2dMcs, 
    	GeomVector2d vNextDir2dMcs, 
    	double torusRadius, 
    	double zLevel,
    	GeomVector3d zDir3dMcs)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double currAngleRad = 0.0;
		while(currAngleRad < AppDefs.MATHVAL_2PI) {
	    	double nextAngleRad = currAngleRad + stepAngleRad;

	    	double dI = torusRadius * Math.cos(nextAngleRad);
	    	double hI = zLevel + torusRadius * Math.sin(nextAngleRad);

	    	double dF = torusRadius * Math.cos(currAngleRad);
	    	double hF = zLevel + torusRadius * Math.sin(currAngleRad);

	    	//POINT-2D
	    	GeomPoint2d ptCurrI2d = ptCurrCenter2dMcs.otherMoveTo(vCurrDir2dMcs, + dI);
	    	GeomPoint2d ptCurrF2d = ptCurrCenter2dMcs.otherMoveTo(vCurrDir2dMcs, + dF);
	    	
	    	GeomPoint2d ptNextI2d = ptNextCenter2dMcs.otherMoveTo(vNextDir2dMcs, + dI);
	    	GeomPoint2d ptNextF2d = ptNextCenter2dMcs.otherMoveTo(vNextDir2dMcs, + dF);

	    	//POINT-3D
	    	GeomPoint3d ptCurrI3d = new GeomPoint3d(ptCurrI2d, hI);
	    	GeomPoint3d ptCurrF3d = new GeomPoint3d(ptCurrF2d, hF);
	    	
	    	GeomPoint3d ptNextI3d = new GeomPoint3d(ptNextI2d, hI);
	    	GeomPoint3d ptNextF3d = new GeomPoint3d(ptNextF2d, hF);
	    	
    		this.addFace(v, oEnt, c, ptCurrI3d, ptCurrF3d, ptNextF3d, ptNextI3d, zDir3dMcs);
	    	
        	currAngleRad = nextAngleRad;
    	}
    }

    /* TORUS */
	
    public void addTorus(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptCenter3dMcs, double radius, double torusRadius, GeomVector3d zDir3dMcs)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double zPtCenter = ptCenter3dMcs.getZ();

    	GeomVector3d zDir = null;
    	if(zDir3dMcs != null)
    		zDir = zDir3dMcs.otherUnit();
    	
		GeomPoint2d ptCenter = new GeomPoint2d( ptCenter3dMcs );
		double xCenter = ptCenter.getX();
		double yCenter = ptCenter.getY();
		
    	GeomPoint2d ptDir = new GeomPoint2d( xCenter + 1.0, yCenter );
    	GeomVector2d vDir = new GeomVector2d( ptCenter, ptDir );
    	
    	double currAngleRad = 0.0;
    	while(currAngleRad < AppDefs.MATHVAL_2PI) {
	    	double nextAngleRad = currAngleRad + stepAngleRad;

	    	GeomVector2d vCurrDir = vDir.otherRotateToRad(currAngleRad);
	    	GeomPoint2d ptCurrCenter = ptCenter.otherMoveTo(vCurrDir, radius);

	    	GeomVector2d vNextDir = vDir.otherRotateToRad(nextAngleRad);
	    	GeomPoint2d ptNextCenter = ptCenter.otherMoveTo(vNextDir, radius);

	    	this.addTorusCilinder( v, oEnt, c, ptCurrCenter, vCurrDir, ptNextCenter, vNextDir, torusRadius, zPtCenter, zDir);
	    	
	    	currAngleRad = nextAngleRad;    	
		}		

    }
    
    /* CONE */	
    
    public void addCone(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, double radius, GeomVector3d vDir3dMcs)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	GeomVector3d zDir = vDir3dMcs.otherUnit();
    	
    	double xPtCenterBase3dMcs = ptI3dMcs.getX();
    	double yPtCenterBase3dMcs = ptI3dMcs.getY();

    	double xPtBase3dMcs0 = xPtCenterBase3dMcs + radius;
    	double yPtBase3dMcs0 = yPtCenterBase3dMcs;

    	double xPtCenterTop3dMcs = ptF3dMcs.getX();
    	double yPtCenterTop3dMcs = ptF3dMcs.getY();
    	
    	GeomVector2d vDirBaseMcs = new GeomVector2d(xPtCenterBase3dMcs, yPtCenterBase3dMcs, xPtBase3dMcs0, yPtBase3dMcs0);
    	GeomPoint2d ptIMcs = new GeomPoint2d(vDirBaseMcs.getXF(), vDirBaseMcs.getYF());

		int nsteps = numsegs + 1;
    	double angleRad = 0.0;
		for(int i = 1; i < nsteps; i++) {
    		GeomVector2d vNextDirBaseMcs = vDirBaseMcs.otherRotateToRad(angleRad);
    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextDirBaseMcs.getXF(), vNextDirBaseMcs.getYF()); 

    		//GEOPOINT3D
    		//
    		GeomPoint3d ptI3dMcs0 = new GeomPoint3d(ptIMcs.getX(), ptIMcs.getY(), ptI3dMcs.getZ());
    		GeomPoint3d ptF3dMcs1 = new GeomPoint3d(ptFMcs.getX(), ptFMcs.getY(), ptI3dMcs.getZ());
    		GeomPoint3d ptF3dMcs2 = new GeomPoint3d(xPtCenterTop3dMcs, yPtCenterTop3dMcs, ptF3dMcs.getZ());

            this.addFace(v, oEnt, c, ptI3dMcs0, ptF3dMcs1, ptF3dMcs2, zDir);
            
            angleRad += stepAngleRad;            
            ptIMcs = ptFMcs;
    	}		

		GeomPoint2d ptFMcs = new GeomPoint2d(vDirBaseMcs.getXF(), vDirBaseMcs.getYF());

		//GEOPOINT3D
		//
		GeomPoint3d ptI3dMcs0 = new GeomPoint3d(ptIMcs.getX(), ptIMcs.getY(), ptI3dMcs.getZ());
		GeomPoint3d ptF3dMcs1 = new GeomPoint3d(ptFMcs.getX(), ptFMcs.getY(), ptI3dMcs.getZ());
		GeomPoint3d ptF3dMcs2 = new GeomPoint3d(xPtCenterTop3dMcs, yPtCenterTop3dMcs, ptF3dMcs.getZ());

        this.addFace(v, oEnt, c, ptI3dMcs0, ptF3dMcs1, ptF3dMcs2, zDir);
    }

    /* TRONCO_CONE */
    
    public void addTroncoCone(ICadViewBase v, CadEntity oEnt, Color c, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, double baseRadius, double topRadius, GeomVector3d vDir3dMcs)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	GeomVector3d zDir = vDir3dMcs.otherUnit();
    	
    	double xPtCenterBase3dMcs = ptI3dMcs.getX();
    	double yPtCenterBase3dMcs = ptI3dMcs.getY();

    	double xPtTop3dMcs0 = xPtCenterBase3dMcs + topRadius;
    	double yPtTop3dMcs0 = yPtCenterBase3dMcs;

    	double xPtBase3dMcs0 = xPtCenterBase3dMcs + baseRadius;
    	double yPtBase3dMcs0 = yPtCenterBase3dMcs;

    	GeomVector2d vDirTopMcs = new GeomVector2d(xPtCenterBase3dMcs, yPtCenterBase3dMcs, xPtTop3dMcs0, yPtTop3dMcs0);
    	GeomPoint2d ptITopMcs = new GeomPoint2d(vDirTopMcs.getXF(), vDirTopMcs.getYF());

    	GeomVector2d vDirBaseMcs = new GeomVector2d(xPtCenterBase3dMcs, yPtCenterBase3dMcs, xPtBase3dMcs0, yPtBase3dMcs0);
    	GeomPoint2d ptIBaseMcs = new GeomPoint2d(vDirBaseMcs.getXF(), vDirBaseMcs.getYF());

		int nsteps = numsegs + 1;
    	double angleRad = 0.0;
		for(int i = 1; i < nsteps; i++) {
    		GeomVector2d vNextDirTopMcs = vDirTopMcs.otherRotateToRad(angleRad);
    		GeomPoint2d ptFTopMcs = new GeomPoint2d(vNextDirTopMcs.getXF(), vNextDirTopMcs.getYF()); 

    		GeomVector2d vNextDirBaseMcs = vDirBaseMcs.otherRotateToRad(angleRad);
    		GeomPoint2d ptFBaseMcs = new GeomPoint2d(vNextDirBaseMcs.getXF(), vNextDirBaseMcs.getYF()); 

    		//GEOPOINT3D
    		//
    		GeomPoint3d ptI3dMcs0 = new GeomPoint3d(ptIBaseMcs.getX(), ptIBaseMcs.getY(), ptI3dMcs.getZ());
    		GeomPoint3d ptI3dMcs1 = new GeomPoint3d(ptITopMcs.getX(), ptITopMcs.getY(), ptF3dMcs.getZ());
    		GeomPoint3d ptF3dMcs2 = new GeomPoint3d(ptFTopMcs.getX(), ptFTopMcs.getY(), ptF3dMcs.getZ());
    		GeomPoint3d ptF3dMcs3 = new GeomPoint3d(ptFBaseMcs.getX(), ptFBaseMcs.getY(), ptI3dMcs.getZ());

            this.addFace(v, oEnt, c, ptI3dMcs0, ptI3dMcs1, ptF3dMcs2, ptF3dMcs3, zDir);

            angleRad += stepAngleRad;            
            ptITopMcs = ptFTopMcs;
            ptIBaseMcs = ptFBaseMcs;
    	}		

		GeomVector2d vNextDirTopMcs = vDirTopMcs.otherRotateToRad(angleRad);
		GeomPoint2d ptFTopMcs = new GeomPoint2d(vNextDirTopMcs.getXF(), vNextDirTopMcs.getYF()); 

		GeomVector2d vNextDirBaseMcs = vDirBaseMcs.otherRotateToRad(angleRad);
		GeomPoint2d ptFBaseMcs = new GeomPoint2d(vNextDirBaseMcs.getXF(), vNextDirBaseMcs.getYF()); 

		//GEOPOINT3D
		//
		GeomPoint3d ptI3dMcs0 = new GeomPoint3d(ptIBaseMcs.getX(), ptIBaseMcs.getY(), ptI3dMcs.getZ());
		GeomPoint3d ptI3dMcs1 = new GeomPoint3d(ptITopMcs.getX(), ptITopMcs.getY(), ptF3dMcs.getZ());
		GeomPoint3d ptF3dMcs2 = new GeomPoint3d(ptFTopMcs.getX(), ptFTopMcs.getY(), ptF3dMcs.getZ());
		GeomPoint3d ptF3dMcs3 = new GeomPoint3d(ptFBaseMcs.getX(), ptFBaseMcs.getY(), ptI3dMcs.getZ());

        this.addFace(v, oEnt, c, ptI3dMcs0, ptI3dMcs1, ptF3dMcs2, ptF3dMcs3, zDir);
    }

    public synchronized ArrayList<GeomFace3d> retriveOrderedListOfFaces(GeomPoint3d ptObserver3d)
    {
    	CmpGeomFace3d c = new CmpGeomFace3d(ptObserver3d, false); 
    	this.lsFace.sort(c);
    	return this.lsFace;
    }
    
    public void drawAllWireframe(ICadViewBase v, Graphics g)
    {
    	GeomObserver3d oObservador = v.getObsMcs();
    	//
    	GeomPoint3d ptObserver3d = oObservador.getPtObserver();
    	ArrayList<GeomFace3d> ls = retriveOrderedListOfFaces(ptObserver3d);
    	for(GeomFace3d o : ls) {
    		o.drawEdgesMcs(v, o.getColor(), g);
    	}    	
    }
    
    public void drawAllSolid(ICadViewBase v, Graphics g)
    {
    	GeomObserver3d oObservador = v.getObsMcs();

    	GeomPoint3d ptObserver3d = oObservador.getPtObserver();
    	ArrayList<GeomFace3d> ls = retriveOrderedListOfFaces(ptObserver3d);
    	for(GeomFace3d o : ls) {
        	Color faceColor = o.getColor();
        	Color edgeColor = AppDefs.ARR_COLORINDEX_TABLE[ AppDefs.COLORINDEX_WHITE ];

        	boolean bOnScreen = o.checkIfOnScreen(v);
        	if( bOnScreen ) {
        		if(faceColor != null) {
        			o.drawFaceMcs(v, faceColor, edgeColor, g);
        		}
        		else {
        			o.drawEdgesMcs(v, edgeColor, g);
        		}
        	}
    	}    	
    }

}
