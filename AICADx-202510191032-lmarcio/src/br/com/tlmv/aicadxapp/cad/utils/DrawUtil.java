/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DrawUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.utils;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomTextPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeFace3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxmod.eletrica.vo.ImportaFiacaoEletrodutoVO;

public class DrawUtil 
{
//Public
	
	/* Mcs */
	
	public static void drawArcMcs(ICadViewBase v, GeomPoint2d ptCenterMcs, double radius, double startAngleRad, double endAngleRad, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double angleDiffRad = endAngleRad - startAngleRad;

    	double stepAngleRad = angleDiffRad /numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt0dMcs = ptCenterMcs.getX() + radius;
    	double yPt0dMcs = ptCenterMcs.getY();
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0dMcs, yPt0dMcs);
    	vDirMcs.selfRotateToRad(startAngleRad);
    	for(int i = 0; i < numsegs; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIMcs = new GeomPoint2d(vDirMcs.getXF(), vDirMcs.getYF());
    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextDirMcs.getXF(), vNextDirMcs.getYF()); 

    		GeomPoint2d ptIScr = v.fromMcsToScr(ptIMcs);
    		GeomPoint2d ptFScr = v.fromMcsToScr(ptFMcs);

    		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
    		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirMcs = vNextDirMcs;
    	}		
	}
	
	public static void drawShape2dMcs(ICadViewBase v, GeomPoint2d ptInsMcs, GeomShape2d shape2d, double sclFact, Graphics g)
	{
		boolean bAnnotation = shape2d.isAnnotation();

		GeomPoint2d ptIMcs_real = new GeomPoint2d(ptInsMcs);

		int sz = shape2d.size();
		for(int i = 0; i < sz; i++) {
			ShapeOper2d oper = shape2d.getAt(i);
			GeomPoint2d ptFMcs = new GeomPoint2d(oper.getPt2d());
			
			GeomVector2d vIF = new GeomVector2d(ptFMcs.getX(), ptFMcs.getY());
			GeomVector2d uIF = vIF.otherUnit();
			double d = vIF.mod();
			if( bAnnotation )
				d = d * sclFact;
			
			GeomPoint2d ptFMcs_real = ptIMcs_real.otherMoveTo(uIF, d);			
			
			boolean bDrawLine = oper.isDrawLine();
			if( bDrawLine ) {
				GeomPoint2d ptIScr_real = v.fromMcsToScr(ptIMcs_real);
				GeomPoint2d ptFScr_real = v.fromMcsToScr(ptFMcs_real);
				
				GeomPoint2d ptIVideo_real = v.fromScrToVideo(ptIScr_real);
				GeomPoint2d ptFVideo_real = v.fromScrToVideo(ptFScr_real);
				
				g.drawLine((int)ptIVideo_real.getX(), (int)ptIVideo_real.getY(), (int)ptFVideo_real.getX(), (int)ptFVideo_real.getY());
			}

			ptIMcs_real = ptFMcs_real;
		}
	}
	
	public static void drawShape2dMcs(ICadViewBase v, GeomPoint2d ptInsMcs, GeomShape2d shape2d, double sclFact, double rotate, Graphics g)
	{
		boolean bAnnotation = shape2d.isAnnotation();

		GeomPoint2d ptIMcs_real = new GeomPoint2d(ptInsMcs);

		int sz = shape2d.size();
		for(int i = 0; i < sz; i++) {
			ShapeOper2d oper = shape2d.getAt(i);
			GeomPoint2d ptFMcs = new GeomPoint2d(oper.getPt2d());
			
			GeomVector2d vIF = new GeomVector2d(ptFMcs.getX(), ptFMcs.getY());
			GeomVector2d uIF = vIF.otherUnit();
			double d = vIF.mod();
			if( bAnnotation )
				d = d * sclFact;
			
			GeomPoint2d ptFMcs_real = ptIMcs_real.otherMoveTo(uIF, d);			
			
			boolean bDrawLine = oper.isDrawLine();
			if( bDrawLine ) {
				GeomPoint2d ptIScr_final = v.fromMcsToScr(ptIMcs_real, ptInsMcs, rotate);
				GeomPoint2d ptFScr_final = v.fromMcsToScr(ptFMcs_real, ptInsMcs, rotate);
				
				GeomPoint2d ptIVideo_final = v.fromScrToVideo(ptIScr_final);
				GeomPoint2d ptFVideo_final = v.fromScrToVideo(ptFScr_final);
				
				g.drawLine((int)ptIVideo_final.getX(), (int)ptIVideo_final.getY(), (int)ptFVideo_final.getX(), (int)ptFVideo_final.getY());
			}
			ptIMcs_real = ptFMcs_real;
		}
	}

	public static void drawArcMcs(ICadViewBase v, GeomPoint2d ptCenterMcs, GeomPoint2d ptStartMcs, GeomPoint2d ptEndMcs, Graphics g)
	{
		double radius = ptCenterMcs.distTo(ptStartMcs);
    	
        double startAngleRad = GeomUtil.angleFromAxisX(ptCenterMcs, ptStartMcs);
        double endAngleRad = GeomUtil.angleFromAxisX(ptCenterMcs, ptEndMcs);
        if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
        	endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;

        drawArcMcs(v, ptCenterMcs, radius, startAngleRad, endAngleRad, g);
	}
	
	public static void drawRadialLinesMcs(ICadViewBase v, GeomPoint2d ptCenterMcs, double radiusMcs, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt0dMcs = ptCenterMcs.getX() + radiusMcs;
    	double yPt0dMcs = ptCenterMcs.getY();

		GeomPoint2d ptCenterScr = v.fromMcsToScr(ptCenterMcs);
		GeomPoint2d ptCenterVideo = v.fromScrToVideo(ptCenterScr);
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0dMcs, yPt0dMcs);
    	int nsteps = numsegs + 1;
    	for(int i = 0; i < nsteps; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextDirMcs.getXF(), vNextDirMcs.getYF()); 
    		GeomPoint2d ptFScr = v.fromMcsToScr(ptFMcs);
    		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
        	g.drawLine((int)ptCenterVideo.getX(), (int)ptCenterVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirMcs = vNextDirMcs;
    	}		
	}
	
	public static void drawRadialLinesMcs(ICadViewBase v, GeomPoint2d ptCenterMcs, double baseRadiusMcs, double topRadiusMcs, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt1dMcs = xPtCenterMcs + topRadiusMcs;
    	double yPt1dMcs = yPtCenterMcs;
    	
    	double xPt0dMcs = xPtCenterMcs + baseRadiusMcs;
    	double yPt0dMcs = yPtCenterMcs;

    	GeomVector2d vTopDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt1dMcs, yPt1dMcs);
    	GeomVector2d vBaseDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0dMcs, yPt0dMcs);
    	
    	int nsteps = numsegs + 1;
    	for(int i = 0; i < nsteps; i++) {
    		GeomVector2d vNextTopDirMcs = vTopDirMcs.otherRotateToRad(stepAngleRad);
    		GeomVector2d vNextBaseDirMcs = vBaseDirMcs.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIMcs = new GeomPoint2d(vNextTopDirMcs.getXF(), vNextTopDirMcs.getYF()); 
    		GeomPoint2d ptIScr = v.fromMcsToScr(ptIMcs);
    		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
    		
    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextBaseDirMcs.getXF(), vNextBaseDirMcs.getYF()); 
    		GeomPoint2d ptFScr = v.fromMcsToScr(ptFMcs);
    		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		

        	vTopDirMcs = vNextTopDirMcs;
        	vBaseDirMcs = vNextBaseDirMcs;
    	}		
	}
	
	public static void drawCircleMcs(ICadViewBase v, GeomPoint2d ptCenterMcs, double radiusMcs, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt0dMcs = ptCenterMcs.getX() + radiusMcs;
    	double yPt0dMcs = ptCenterMcs.getY();
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0dMcs, yPt0dMcs);
    	int nsteps = numsegs + 1;
    	for(int i = 0; i < nsteps; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIMcs = new GeomPoint2d(vDirMcs.getXF(), vDirMcs.getYF());
    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextDirMcs.getXF(), vNextDirMcs.getYF()); 

    		GeomPoint2d ptIScr = v.fromMcsToScr(ptIMcs);
    		GeomPoint2d ptFScr = v.fromMcsToScr(ptFMcs);
    		
    		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
    		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirMcs = vNextDirMcs;
    	}		
	}
	
	public static void drawLineMcs(ICadViewBase v, GeomPoint2d ptIMcs, GeomPoint2d ptFMcs, Graphics g)
	{
		GeomPoint2d ptIScr = v.fromMcsToScr(ptIMcs);
		GeomPoint2d ptFScr = v.fromMcsToScr(ptFMcs);

		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
    	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
	}
	
	public static GeomRect2d drawGridLineMcs(ICadViewBase v, double xMinMcs, double yMinMcs, double xMaxMcs, double yMaxMcs, Graphics g)
	{
		double nx = Math.ceil(xMaxMcs - xMinMcs);
		double ny = Math.ceil(yMaxMcs - yMinMcs);
		
		double xmax = xMinMcs + nx;
		double ymax = yMinMcs + ny;

		for(int i = 0; i <= nx; i += 1) {
			double xp = xMinMcs + i;
			
	    	GeomPoint2d ptHMinMcs = new GeomPoint2d(xp, yMinMcs); 
	    	GeomPoint2d ptHMaxMcs = new GeomPoint2d(xp, ymax); 
	    	//
			GeomPoint2d ptHMinScr = v.fromMcsToScr(ptHMinMcs);
			GeomPoint2d ptHMaxScr = v.fromMcsToScr(ptHMaxMcs);
			//
			GeomPoint2d ptHMinVideo = v.fromScrToVideo(ptHMinScr);
			GeomPoint2d ptHMaxVideo = v.fromScrToVideo(ptHMaxScr);
    		
	    	g.drawLine((int)ptHMinVideo.getX(), (int)ptHMinVideo.getY(), (int)ptHMaxVideo.getX(), (int)ptHMaxVideo.getY());    		
			for(int j = 0; j <= ny; j += 1) {
				double yp = yMinMcs + j;
				
		    	GeomPoint2d ptVMinMcs = new GeomPoint2d(xMinMcs, yp); 
		    	GeomPoint2d ptVMaxMcs = new GeomPoint2d(xmax, yp); 
		    	//
				GeomPoint2d ptVMinScr = v.fromMcsToScr(ptVMinMcs);
				GeomPoint2d ptVMaxScr = v.fromMcsToScr(ptVMaxMcs);
				//
				GeomPoint2d ptVMinVideo = v.fromScrToVideo(ptVMinScr);
				GeomPoint2d ptVMaxVideo = v.fromScrToVideo(ptVMaxScr);
	    		
		    	g.drawLine((int)ptVMinVideo.getX(), (int)ptVMinVideo.getY(), (int)ptVMaxVideo.getX(), (int)ptVMaxVideo.getY());    		
			}
		}
		
		GeomRect2d oRect = new GeomRect2d(xMinMcs, yMinMcs, xmax, ymax);
		return oRect;
	}
	
	public static void drawRectangleMcs(ICadViewBase v, GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs, Graphics g)
	{
		GeomPoint2d ptMinScr = v.fromMcsToScr(ptMinMcs);
		GeomPoint2d ptMaxScr = v.fromMcsToScr(ptMaxMcs);

		double wScr = ptMaxScr.getX() - ptMinScr.getX();		
		double hScr = ptMaxScr.getY() - ptMinScr.getY();		

		GeomPoint2d ptMinVideo = v.fromScrToVideo(ptMinScr);
		GeomPoint2d ptMaxVideo = v.fromScrToVideo(ptMaxScr);
		
		GeomPoint2d ptVideo = new GeomPoint2d(ptMinVideo.getX(), ptMaxVideo.getY());

    	g.drawRect((int)ptVideo.getX(), (int)ptVideo.getY(), (int)wScr, (int)hScr);    		
	}
	
	public static void drawRectangleMcs(ICadViewBase v, double xMinMcs, double yMinMcs, double xMaxMcs, double yMaxMcs, Graphics g)
	{
		GeomPoint2d ptMinMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMaxMcs = new GeomPoint2d(xMaxMcs, yMaxMcs);
		
		GeomPoint2d ptMinScr = v.fromMcsToScr(ptMinMcs);
		GeomPoint2d ptMaxScr = v.fromMcsToScr(ptMaxMcs);

		double wScr = ptMaxScr.getX() - ptMinScr.getX();		
		double hScr = ptMaxScr.getY() - ptMinScr.getY();		

		GeomPoint2d ptMinVideo = v.fromScrToVideo(ptMinScr);
		GeomPoint2d ptMaxVideo = v.fromScrToVideo(ptMaxScr);
		
		GeomPoint2d ptVideo = new GeomPoint2d(ptMinVideo.getX(), ptMaxVideo.getY());

    	g.drawRect((int)ptVideo.getX(), (int)ptVideo.getY(), (int)wScr, (int)hScr);    		
	}
	
	public static void drawPolygonMcs(ICadViewBase v, ArrayList<GeomPoint2d> lsPts2dMcs, Graphics g)
	{
		int sz = lsPts2dMcs.size();
		
		int[] arrXMcs = new int[sz];
		int[] arrYMcs = new int[sz];

		for(int i = 0; i < sz; i++) {
			GeomPoint2d pt2dMcs = lsPts2dMcs.get(i);

			GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);
			GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);

			arrXMcs[i] = (int)pt2dVideo.getX();
			arrYMcs[i] = (int)pt2dVideo.getY();
		}
		
    	g.drawPolyline(arrXMcs, arrYMcs, sz);
	}
	
	public static void drawImageMcs(ICadViewBase v, CadImageDef oImageDef, GeomPoint2d pt2dMcs, double scaleX, double scaleY, Graphics g)
	{
		//MCS
		double wBaseMcs = oImageDef.getWidth();
		double hBaseMcs = oImageDef.getHeight();

		double wMcs = wBaseMcs * scaleX; 
		double hMcs = hBaseMcs * scaleY; 

		//SCR
		GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);
		
		double wScr = v.fromMcsToScr(wMcs);
		double hScr = v.fromMcsToScr(hMcs);
		
		//VIDEO
		GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);
		
		//IMAGE_FILE
		//
		Image oImage = oImageDef.getImageObj();
		double xVideo = pt2dVideo.getX();
		double yVideo = pt2dVideo.getY();
		
		g.drawImage(oImage, (int)xVideo, (int)yVideo, (int)wScr, (int)hScr, AppDefs.BACKGROUNDCOLOR, null);
	}
	
	public static void drawImageMcs(ICadViewBase v, CadImageDef oImageDef, GeomPoint2d pt2dMcs, double rotate, double scaleX, double scaleY, Graphics g)
	{
		//MCS
		double wBaseMcs = oImageDef.getWidth();
		double hBaseMcs = oImageDef.getHeight();

		double wMcs = wBaseMcs * scaleX; 
		double hMcs = hBaseMcs * scaleY; 

		//SCR
		GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);
		
		double wScr = v.fromMcsToScr(wMcs);
		double hScr = v.fromMcsToScr(hMcs);
		
		//VIDEO
		GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);
		
		//IMAGE_FILE
		//
		Image oImage = oImageDef.getImageObj();
		double xVideo = pt2dVideo.getX();
		double yVideo = pt2dVideo.getY();
		
		double rotateRad = GeomUtil.convertDegreesToRad(rotate);
		double xp = pt2dVideo.getX();
		double yp = pt2dVideo.getY();
		
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform oldtx = g2d.getTransform();
		
	    AffineTransform a = AffineTransform.getRotateInstance(rotateRad, xp, yp);
	    g2d.setTransform(a);
		g.drawImage(oImage, (int)xVideo, (int)yVideo, (int)wScr, (int)hScr, AppDefs.BACKGROUNDCOLOR, null);

		g2d.setTransform(oldtx);		
	}
	
	public static void drawCoordsysMcs(ICadViewBase v, GeomPoint2d ptOrigemMcs, GeomVector2d vDirMcs, double sizeScr, Graphics g)
	{
		GeomVector2d uDirMcs = vDirMcs.otherUnit();
		GeomVector2d nDirMcs = uDirMcs.otherNorm();
		
		double sizeMcs = v.fromScrToMcs(sizeScr);
		
		GeomPoint2d ptXDirMcs = ptOrigemMcs.otherMoveTo(uDirMcs, sizeMcs);
		GeomPoint2d ptYDirMcs = ptOrigemMcs.otherMoveTo(nDirMcs, sizeMcs);

		//SCR
		GeomPoint2d ptOrigemScr = v.fromMcsToScr(ptOrigemMcs);

		GeomPoint2d ptXDirScr = v.fromMcsToScr(ptXDirMcs);
		GeomPoint2d ptYDirScr = v.fromMcsToScr(ptYDirMcs);
		
		//VIDEO
		GeomPoint2d ptOrigemVideo = v.fromScrToVideo(ptOrigemScr);

		GeomPoint2d ptXDirVideo = v.fromScrToVideo(ptXDirScr);
		GeomPoint2d ptYDirVideo = v.fromScrToVideo(ptYDirScr);

		//DRAW COORDS
		double radius = sizeScr / 6.0;

		double xmin = ptOrigemVideo.getX() - (radius / 2.0);
		double ymin = ptOrigemVideo.getY() - (radius / 2.0);

    	g.drawLine((int)ptOrigemVideo.getX(), (int)ptOrigemVideo.getY(), (int)ptXDirVideo.getX(), (int)ptXDirVideo.getY());    		
    	g.drawLine((int)ptOrigemVideo.getX(), (int)ptOrigemVideo.getY(), (int)ptYDirVideo.getX(), (int)ptYDirVideo.getY());    		
	
    	g.drawOval((int)xmin, (int)ymin, (int)radius, (int)radius);
	}
	
	public static void drawTableIndicatorMcs(ICadViewBase v, GeomPoint2d ptOrigemMcs, GeomVector2d vDirMcs, String strNome, String strDescricao, double symbolSizeScr, double textSizeScr, Graphics g)
	{
		GeomVector2d uDirMcs = vDirMcs.otherUnit();
		GeomVector2d nDirMcs = uDirMcs.otherNorm();
		
		double symbolSizeMcs = v.fromScrToMcs(symbolSizeScr);
		double textSizeMcs = v.fromScrToMcs(textSizeScr);

		double lineSizeMcs = 2.5 * textSizeMcs;

		GeomPoint2d pt0Mcs = new GeomPoint2d(ptOrigemMcs);
		GeomPoint2d pt1Mcs = pt0Mcs.otherMoveTo(uDirMcs, symbolSizeMcs);
		GeomPoint2d pt2Mcs = pt1Mcs.otherMoveTo(nDirMcs, symbolSizeMcs);
		GeomPoint2d pt3Mcs = pt2Mcs.otherMoveTo(uDirMcs, - symbolSizeMcs);
		//
		GeomPoint2d ptTxt1Mcs = pt1Mcs.otherMoveTo(uDirMcs, textSizeMcs);
		GeomPoint2d ptTxt2Mcs = ptTxt1Mcs.otherMoveTo(nDirMcs, - lineSizeMcs);
		
		//SCR
		GeomPoint2d pt0Scr = v.fromMcsToScr(pt0Mcs);
		GeomPoint2d pt1Scr = v.fromMcsToScr(pt1Mcs);
		GeomPoint2d pt2Scr = v.fromMcsToScr(pt2Mcs);
		GeomPoint2d pt3Scr = v.fromMcsToScr(pt3Mcs);
		//
		GeomPoint2d ptTxt1Scr = v.fromMcsToScr(ptTxt1Mcs);
		GeomPoint2d ptTxt2Scr = v.fromMcsToScr(ptTxt2Mcs);
		
		//VIDEO
		GeomPoint2d pt0Video = v.fromScrToVideo(pt0Scr);
		GeomPoint2d pt1Video = v.fromScrToVideo(pt1Scr);
		GeomPoint2d pt2Video = v.fromScrToVideo(pt2Scr);
		GeomPoint2d pt3Video = v.fromScrToVideo(pt3Scr);
		//
		GeomPoint2d ptTxt1Video = v.fromScrToVideo(ptTxt1Scr);
		GeomPoint2d ptTxt2Video = v.fromScrToVideo(ptTxt2Scr);
		
		//DRAW COORDS
    	g.drawLine((int)pt0Video.getX(), (int)pt0Video.getY(), (int)pt1Video.getX(), (int)pt1Video.getY());    		
    	g.drawLine((int)pt1Video.getX(), (int)pt1Video.getY(), (int)pt2Video.getX(), (int)pt2Video.getY());    		
    	g.drawLine((int)pt2Video.getX(), (int)pt2Video.getY(), (int)pt3Video.getX(), (int)pt3Video.getY());    		
    	g.drawLine((int)pt3Video.getX(), (int)pt3Video.getY(), (int)pt0Video.getX(), (int)pt0Video.getY());    		
    	//
    	g.drawLine((int)pt0Video.getX(), (int)pt0Video.getY(), (int)pt2Video.getX(), (int)pt2Video.getY());    		
    	g.drawLine((int)pt1Video.getX(), (int)pt1Video.getY(), (int)pt3Video.getX(), (int)pt3Video.getY());

    	//DRAW_TEXT
    	g.drawString(strNome, (int)ptTxt1Video.getX(), (int)ptTxt1Video.getY());
    	g.drawString(strDescricao, (int)ptTxt2Video.getX(), (int)ptTxt2Video.getY());
	}

	public static void drawPointMcs(ICadViewBase v, GeomPoint2d ptMcs, double sizeScr, int ptType, Graphics g)
	{
		//POINT_TYPE_X
		if((ptType & AppDefs.POINT_TYPE_X) == AppDefs.POINT_TYPE_X) {
			DrawUtil.drawPointXMcs(v, ptMcs, sizeScr, g);
		}
		
		//POINT_TYPE_CROSS
		if((ptType & AppDefs.POINT_TYPE_CROSS) == AppDefs.POINT_TYPE_CROSS) {
			DrawUtil.drawPointCrossMcs(v, ptMcs, sizeScr, g);
		}
		
		//POINT_TYPE_TRIANGLE
		if((ptType & AppDefs.POINT_TYPE_TRIANGLE) == AppDefs.POINT_TYPE_TRIANGLE) {
			DrawUtil.drawPointTriangleMcs(v, ptMcs, sizeScr, g);
		}
		
		//POINT_TYPE_CIRCLE
		if((ptType & AppDefs.POINT_TYPE_CIRCLE) == AppDefs.POINT_TYPE_CIRCLE) {
			DrawUtil.drawPointCricleMcs(v, ptMcs, sizeScr, g);
		}
	}

	public static void drawPointXMcs(ICadViewBase v, GeomPoint2d ptMcs, double sizeScr, Graphics g)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);

		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xMinVideo = ptVideo.getX() - sizeScr; 
		double yMinVideo = ptVideo.getY() - sizeScr; 
		
		double xMaxVideo = ptVideo.getX() + sizeScr; 
		double yMaxVideo = ptVideo.getY() + sizeScr; 
		
    	g.drawLine((int)xMinVideo, (int)yMinVideo, (int)xMaxVideo, (int)yMaxVideo);    		
    	g.drawLine((int)xMinVideo, (int)yMaxVideo, (int)xMaxVideo, (int)yMinVideo);    		
	}

	public static void drawPointCrossMcs(ICadViewBase v, GeomPoint2d ptMcs, double sizeScr, Graphics g)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);

		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xMinVideo = ptVideo.getX() - sizeScr; 
		double yMinVideo = ptVideo.getY() - sizeScr; 
		
		double xMaxVideo = ptVideo.getX() + sizeScr; 
		double yMaxVideo = ptVideo.getY() + sizeScr; 

    	g.drawLine((int)ptVideo.getX(), (int)yMinVideo, (int)ptVideo.getX(), (int)yMaxVideo);    		
    	g.drawLine((int)xMinVideo, (int)ptVideo.getY(), (int)xMaxVideo, (int)ptVideo.getY());    		
	}

	public static void drawPointTriangleMcs(ICadViewBase v, GeomPoint2d ptMcs, double sizeScr, Graphics g)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);

		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xMinVideo = ptVideo.getX() - sizeScr; 
		double yMinVideo = ptVideo.getY() - sizeScr; 
		
		double xMaxVideo = ptVideo.getX() + sizeScr; 
		double yMaxVideo = ptVideo.getY() + sizeScr; 

    	g.drawLine((int)ptVideo.getX(), (int)yMaxVideo, (int)xMinVideo, (int)yMinVideo);
    	g.drawLine((int)xMinVideo, (int)yMinVideo, (int)xMaxVideo, (int)yMinVideo);
    	g.drawLine((int)xMaxVideo, (int)yMinVideo, (int)ptVideo.getX(), (int)yMaxVideo);
	}

	public static void drawPointCricleMcs(ICadViewBase v, GeomPoint2d ptMcs, double sizeScr, Graphics g)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);

		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xMinVideo = ptVideo.getX() - sizeScr; 
		double yMinVideo = ptVideo.getY() - sizeScr; 
		
		double xMaxVideo = ptVideo.getX() + sizeScr; 
		double yMaxVideo = ptVideo.getY() + sizeScr; 

		g.drawOval((int)xMinVideo, (int)yMinVideo, (int)xMaxVideo, (int)yMaxVideo);
	}
	
	public static void drawTextMcs(ICadViewBase v, String text, GeomPoint2d ptMcs, Graphics g)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);
		
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xPtVideo = ptVideo.getX(); 
		double yPtVideo = ptVideo.getY(); 
		
    	g.drawString(text, (int)xPtVideo, (int)yPtVideo);
	}
	
	public static void drawTextMcs(ICadViewBase v, ArrayList<GeomTextPoint2d> lsPts2d, double sclFact, Graphics g)
	{
		for(GeomTextPoint2d oTextPt2d : lsPts2d) {
			GeomPoint2d ptIns = new GeomPoint2d(oTextPt2d.getX(), oTextPt2d.getY());			
			double xPtIns = ptIns.getX();
			double yPtIns = ptIns.getY();
			
			GeomVector2d vDir = new GeomVector2d(xPtIns, yPtIns, xPtIns + 1.0, yPtIns);
			vDir.selfRotateToDegrees(oTextPt2d.getRotate());

			double heightMcs = oTextPt2d.getHeight() * sclFact;			
			
			int horizAlign = oTextPt2d.getHorizAlign();
			int vertAlign = oTextPt2d.getVertAlign();
			
			DrawUtil.drawTextMcs(v, oTextPt2d.getText(), ptIns, heightMcs, horizAlign, vertAlign, vDir, g);
		}
	}
	
	public static void drawTextMcs(ICadViewBase v, String text, GeomPoint2d ptMcs, GeomVector2d vDir, Graphics g)
	{
		GeomVector2d uDir = vDir.otherUnit();
		
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);
		
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xPtVideo = ptVideo.getX(); 
		double yPtVideo = ptVideo.getY(); 

		Graphics2D g2d = (Graphics2D)g;

		AffineTransform oldtx = g2d.getTransform();
		
		double rotateRad = - uDir.angleToAxisX();
	    AffineTransform a = AffineTransform.getRotateInstance(rotateRad, xPtVideo, yPtVideo);
	    g2d.setTransform(a);

		g2d.drawString(text, (int)xPtVideo, (int)yPtVideo);

		g2d.setTransform(oldtx);		
	}
	
	public static void drawArrowMcs(ICadViewBase v, GeomPoint2d ptMcs, double arrowLengthMcs, double arrowWidthMcs, double arrowPointMcs, GeomVector2d vDir, Graphics g)
	{
		double arrowWidthMcs2 = arrowWidthMcs / 2.0;
		double arrowLengthMcs2 = arrowLengthMcs - arrowPointMcs;
		
        GeomPoint2d ptIns = new GeomPoint2d(ptMcs);

        GeomVector2d uDir = vDir.otherUnit();
        GeomVector2d nDir = uDir.otherNorm();

        GeomPoint2d ptI = new GeomPoint2d(ptIns); 
        GeomPoint2d ptF = ptI.otherMoveTo(uDir, arrowLengthMcs); 

        //UP-SIDE
        //
        GeomPoint2d pt0 = ptI.otherMoveTo(nDir, arrowWidthMcs2); 
        GeomPoint2d pt1 = pt0.otherMoveTo(uDir, arrowLengthMcs2); 
        GeomPoint2d pt2 = pt1.otherMoveTo(nDir, arrowWidthMcs2); 

        //DOWN-SIDE
        //
        GeomPoint2d pt3 = ptI.otherMoveTo(nDir, - arrowWidthMcs2); 
        GeomPoint2d pt4 = pt3.otherMoveTo(uDir, arrowLengthMcs2); 
        GeomPoint2d pt5 = pt4.otherMoveTo(nDir, - arrowWidthMcs2); 
        
        //DRAW_ARROW
        //
        drawLineMcs(v, ptI, pt0, g);
        drawLineMcs(v, pt0, pt1, g);
        drawLineMcs(v, pt1, pt2, g);
        drawLineMcs(v, pt2, ptF, g);
        drawLineMcs(v, ptF, pt5, g);
        drawLineMcs(v, pt5, pt4, g);
        drawLineMcs(v, pt4, pt3, g);
        drawLineMcs(v, pt3, ptI, g);        
	}
	
	public static void drawTextMcs(ICadViewBase v, String text, GeomPoint2d ptMcs, double heightMcs, Graphics g)
	{
		double heightScr = v.fromMcsToScr(heightMcs);
		
        int oldtextheight = GeomUtil.setTextHeight(g, (int)heightScr);
		
    	drawTextMcs(v, text, ptMcs, g);

		GeomUtil.setTextHeight(g, (int)oldtextheight);
	}
	
	public static void drawTextMcs(ICadViewBase v, String text, GeomPoint2d ptMcs, double heightMcs, int horizAlign, int vertAlign, Graphics g)
	{
		double heightScr = v.fromMcsToScr(heightMcs);
		
        int oldtextheight = GeomUtil.setTextHeight(g, (int)heightScr);

        FontMetrics f = g.getFontMetrics();
        
        Rectangle2D rect = f.getStringBounds(text, g);

        double wTextScr = rect.getWidth();
        double hTextScr = rect.getHeight();

        //TextMcs

        double wTextMcs = v.fromScrToMcs(wTextScr);
        double hTextMcs = v.fromScrToMcs(hTextScr);
        
        GeomPoint2d ptIns = new GeomPoint2d(ptMcs);

        GeomVector2d axisX = new GeomVector2d(1.0, 0.0);
        GeomVector2d axisY = new GeomVector2d(0.0, 1.0);
        
        //HORIZALIGN
        if(horizAlign == AppDefs.HORIZALIGN_LEFT) {
        	/* nothing todo! */
        }
        else if(horizAlign == AppDefs.HORIZALIGN_CENTER) {
            ptIns.selfMoveTo(axisX, - (wTextMcs / 2.0));
        }
        else if(horizAlign == AppDefs.HORIZALIGN_RIGHT) {
            ptIns.selfMoveTo(axisX, - wTextMcs);            
        }
        
    	//VERTALIGN
        if(horizAlign == AppDefs.VERTALIGN_TOP) {
        	/* nothing todo! */            
        }
        else if(horizAlign == AppDefs.VERTALIGN_MIDDLE) {
            ptIns.selfMoveTo(axisY, - (hTextMcs / 2.0));            
        }
        else if(horizAlign == AppDefs.VERTALIGN_BOTTOM) {
            ptIns.selfMoveTo(axisY, - hTextMcs);            
        }
        
    	drawTextMcs(v, text, ptIns, g);
        
		GeomUtil.setTextHeight(g, (int)oldtextheight);
	}
	
	public static void drawTextMcs(ICadViewBase v, String text, GeomPoint2d ptMcs, double heightMcs, int horizAlign, int vertAlign, GeomVector2d vDir, Graphics g)
	{
		double heightScr = v.fromMcsToScr(heightMcs);
		
        int oldtextheight = GeomUtil.setTextHeight(g, (int)heightScr);

        FontMetrics f = g.getFontMetrics();
        
        Rectangle2D rect = f.getStringBounds(text, g);

        double wTextScr = rect.getWidth();
        double hTextScr = rect.getHeight();

        //TextMcs

        double wTextMcs = v.fromScrToMcs(wTextScr);
        double hTextMcs = v.fromScrToMcs(hTextScr);
        
        GeomPoint2d ptIns = new GeomPoint2d(ptMcs);

        GeomVector2d uDir = vDir.otherUnit();
        GeomVector2d nDir = uDir.otherNorm();
        
        //HORIZALIGN
        if(horizAlign == AppDefs.HORIZALIGN_LEFT) {
        	/* nothing todo! */
        }
        else if(horizAlign == AppDefs.HORIZALIGN_CENTER) {
            ptIns.selfMoveTo(uDir, - (wTextMcs / 2.0));
        }
        else if(horizAlign == AppDefs.HORIZALIGN_RIGHT) {
            ptIns.selfMoveTo(uDir, - wTextMcs);            
        }
        
    	//VERTALIGN
        if(horizAlign == AppDefs.VERTALIGN_TOP) {
        	/* nothing todo! */            
        }
        else if(horizAlign == AppDefs.VERTALIGN_MIDDLE) {
            ptIns.selfMoveTo(nDir, - (hTextMcs / 2.0));            
        }
        else if(horizAlign == AppDefs.VERTALIGN_BOTTOM) {
            ptIns.selfMoveTo(nDir, - hTextMcs);            
        }
        
    	drawTextMcs(v, text, ptIns, vDir, g);
        
		GeomUtil.setTextHeight(g, (int)oldtextheight);
	}
	
	public static void drawArrowMcs(ICadViewBase v, GeomPoint2d ptMcs, double arrowLengthMcs, double arrowWidthMcs, double arrowPointMcs, int horizAlign, int vertAlign, GeomVector2d vDir, Graphics g)
	{
        GeomPoint2d ptIns = new GeomPoint2d(ptMcs);

        GeomVector2d uDir = vDir.otherUnit();
        GeomVector2d nDir = uDir.otherNorm();
        
        //HORIZALIGN
        if(horizAlign == AppDefs.HORIZALIGN_LEFT) {
        	/* nothing todo! */
        }
        else if(horizAlign == AppDefs.HORIZALIGN_CENTER) {
            ptIns.selfMoveTo(uDir, - (arrowLengthMcs / 2.0));
        }
        else if(horizAlign == AppDefs.HORIZALIGN_RIGHT) {
            ptIns.selfMoveTo(uDir, - arrowLengthMcs);
        }
        
    	//VERTALIGN
        if(horizAlign == AppDefs.VERTALIGN_TOP) {
        	/* nothing todo! */            
        }
        else if(horizAlign == AppDefs.VERTALIGN_MIDDLE) {
            ptIns.selfMoveTo(nDir, - (arrowWidthMcs / 2.0));            
        }
        else if(horizAlign == AppDefs.VERTALIGN_BOTTOM) {
            ptIns.selfMoveTo(nDir, - arrowWidthMcs);            
        }
        
        drawArrowMcs(v, ptIns, arrowLengthMcs, arrowWidthMcs, arrowPointMcs, uDir, g);
	}

	public static void fillRectangleMcs(ICadViewBase v, GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs, Graphics g)
	{
		GeomPoint2d ptMinScr = v.fromMcsToScr(ptMinMcs);
		GeomPoint2d ptMaxScr = v.fromMcsToScr(ptMaxMcs);

		double wScr = ptMaxScr.getX() - ptMinScr.getX();		
		double hScr = ptMaxScr.getY() - ptMinScr.getY();		

		GeomPoint2d ptMinVideo = v.fromScrToVideo(ptMinScr);
		GeomPoint2d ptMaxVideo = v.fromScrToVideo(ptMaxScr);
		
		GeomPoint2d ptVideo = new GeomPoint2d(ptMinVideo.getX(), ptMaxVideo.getY());

    	g.fillRect((int)ptVideo.getX(), (int)ptVideo.getY(), (int)wScr, (int)hScr);    		
	}

	public static void fillRectangleScr(ICadViewBase v, GeomPoint2d ptMinScr, GeomPoint2d ptMaxScr, Graphics g)
	{
		double wScr = ptMaxScr.getX() - ptMinScr.getX();		
		double hScr = ptMaxScr.getY() - ptMinScr.getY();		

		GeomPoint2d ptMinVideo = v.fromScrToVideo(ptMinScr);
		GeomPoint2d ptMaxVideo = v.fromScrToVideo(ptMaxScr);
		
		GeomPoint2d ptVideo = new GeomPoint2d(ptMinVideo.getX(), ptMaxVideo.getY());

    	g.fillRect((int)ptVideo.getX(), (int)ptVideo.getY(), (int)wScr, (int)hScr);    		
	}

	public static void fillPolygonMcs(ICadViewBase v, ArrayList<GeomPoint2d> lsPts2dMcs, Graphics g)
	{
		int sz = lsPts2dMcs.size();
		
		int[] arrXMcs = new int[sz];
		int[] arrYMcs = new int[sz];

		for(int i = 0; i < sz; i++) {
			GeomPoint2d pt2dMcs = lsPts2dMcs.get(i);

			GeomPoint2d pt2dScr = v.fromMcsToScr(pt2dMcs);
			GeomPoint2d pt2dVideo = v.fromScrToVideo(pt2dScr);

			arrXMcs[i] = (int)pt2dVideo.getX();
			arrYMcs[i] = (int)pt2dVideo.getY();
		}
		
    	g.fillPolygon(arrXMcs, arrYMcs, sz);
	}
		
	/* SCR */
	
	public static void drawArcScr(ICadViewBase v, GeomPoint2d ptCenterScr, double radius, double startAngleRad, double endAngleRad, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double angleDiffRad = endAngleRad - startAngleRad;

    	double stepAngleRad = angleDiffRad /numsegs;
    	
    	double xPtCenterScr = ptCenterScr.getX();
    	double yPtCenterScr = ptCenterScr.getY();
    	
    	double xPt0dScr = ptCenterScr.getX() + radius;
    	double yPt0dScr = ptCenterScr.getY();
    	
    	GeomVector2d vDirScr = new GeomVector2d(xPtCenterScr, yPtCenterScr, xPt0dScr, yPt0dScr);
    	vDirScr.selfRotateToRad(startAngleRad);
    	for(int i = 0; i < numsegs; i++) {
    		GeomVector2d vNextDirScr = vDirScr.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIScr = new GeomPoint2d(vDirScr.getXF(), vDirScr.getYF());
    		GeomPoint2d ptFScr = new GeomPoint2d(vNextDirScr.getXF(), vNextDirScr.getYF()); 

    		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
    		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirScr = vNextDirScr;
    	}
	}
	
	public static void drawCircleScr(ICadViewBase v, GeomPoint2d ptCenterScr, double radiusScr, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double xPtCenterScr = ptCenterScr.getX();
    	double yPtCenterScr = ptCenterScr.getY();
    	
    	double xPt0dScr = ptCenterScr.getX() + radiusScr;
    	double yPt0dScr = ptCenterScr.getY();
    	
    	GeomVector2d vDirScr = new GeomVector2d(xPtCenterScr, yPtCenterScr, xPt0dScr, yPt0dScr);
    	int nsteps = numsegs + 1;
    	for(int i = 0; i < nsteps; i++) {
    		GeomVector2d vNextDirScr = vDirScr.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIScr = new GeomPoint2d(vDirScr.getXF(), vDirScr.getYF());
    		GeomPoint2d ptFScr = new GeomPoint2d(vNextDirScr.getXF(), vNextDirScr.getYF()); 

    		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
    		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirScr = vNextDirScr;
    	}		
	}
	
	public static void drawLineScr(ICadViewBase v, GeomPoint2d ptIScr, GeomPoint2d ptFScr, Graphics g)
	{
		GeomPoint2d ptIVideo = v.fromScrToVideo(ptIScr);
		GeomPoint2d ptFVideo = v.fromScrToVideo(ptFScr);
    		
    	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
	}
	
	public static void drawRectangleScr(ICadViewBase v, GeomPoint2d ptMinScr, GeomPoint2d ptMaxScr, Graphics g)
	{
		double wScr = ptMaxScr.getX() - ptMinScr.getX();		
		double hScr = ptMaxScr.getY() - ptMinScr.getY();		

		GeomPoint2d ptMinVideo = v.fromScrToVideo(ptMinScr);
		GeomPoint2d ptMaxVideo = v.fromScrToVideo(ptMaxScr);
		
		GeomPoint2d ptVideo = new GeomPoint2d(ptMinVideo.getX(), ptMaxVideo.getY());

    	g.drawRect((int)ptVideo.getX(), (int)ptVideo.getY(), (int)wScr, (int)hScr);    		
	}
	
	public static void drawPointScr(ICadViewBase v, GeomPoint2d ptScr, double sizeScr, Graphics g)
	{
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xMinVideo = ptVideo.getX() - sizeScr; 
		double yMinVideo = ptVideo.getY() - sizeScr; 
		
		double xMaxVideo = ptVideo.getX() + sizeScr; 
		double yMaxVideo = ptVideo.getY() + sizeScr; 
		
    	g.drawLine((int)ptVideo.getX(), (int)yMinVideo, (int)ptVideo.getX(), (int)yMaxVideo);    		
    	g.drawLine((int)xMinVideo, (int)ptVideo.getY(), (int)xMaxVideo, (int)ptVideo.getY());    		
	}
	
	public static void drawTextScr(ICadViewBase v, String text, GeomPoint2d ptScr, double heightScr, Graphics g)
	{
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xPtVideo = ptVideo.getX(); 
		double yPtVideo = ptVideo.getY(); 
		
        int oldtextheight = GeomUtil.setTextHeight(g, (int)heightScr);
		
    	g.drawString(text, (int)xPtVideo, (int)yPtVideo);

		GeomUtil.setTextHeight(g, (int)oldtextheight);
	}
	
	/* PROJ */
	
	public static void drawArcProj(ICadViewBase v, GeomPoint3d ptCenterMcs, double radius, double startAngleRad, double endAngleRad, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double angleDiffRad = endAngleRad - startAngleRad;

    	double stepAngleRad = angleDiffRad /numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt0dMcs = ptCenterMcs.getX() + radius;
    	double yPt0dMcs = ptCenterMcs.getY();
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0dMcs, yPt0dMcs);
    	vDirMcs.selfRotateToRad(startAngleRad);
    	for(int i = 0; i < numsegs; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIMcs = new GeomPoint2d(vDirMcs.getXF(), vDirMcs.getYF());
    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextDirMcs.getXF(), vNextDirMcs.getYF()); 

    		GeomPoint2d ptIVideo = GeomUtil.directFrom3dMcsToVideo(v, new GeomPoint3d(ptIMcs));	
    		GeomPoint2d ptFVideo = GeomUtil.directFrom3dMcsToVideo(v, new GeomPoint3d(ptFMcs));	
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirMcs = vNextDirMcs;
    	}		
	}
	
	public static void drawArcProj(ICadViewBase v, GeomPoint3d ptCenterMcs, GeomPoint3d ptStartMcs, GeomPoint3d ptEndMcs, Graphics g)
	{
		double radius = ptCenterMcs.distTo(ptStartMcs);
    	
        double startAngleRad = GeomUtil.angleFromAxisX(ptCenterMcs, ptStartMcs);
        double endAngleRad = GeomUtil.angleFromAxisX(ptCenterMcs, ptEndMcs);
        if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
        	endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;

        DrawUtil.drawArcProj(v, ptCenterMcs, radius, startAngleRad, endAngleRad, g);
	}
	
	public static void drawCircleProj(ICadViewBase v, GeomPoint3d ptCenterMcs, double radiusMcs, Graphics g)
	{
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double xPtCenterMcs = ptCenterMcs.getX();
    	double yPtCenterMcs = ptCenterMcs.getY();
    	
    	double xPt0dMcs = ptCenterMcs.getX() + radiusMcs;
    	double yPt0dMcs = ptCenterMcs.getY();
    	
    	GeomVector2d vDirMcs = new GeomVector2d(xPtCenterMcs, yPtCenterMcs, xPt0dMcs, yPt0dMcs);
    	int nsteps = numsegs + 1;
    	for(int i = 0; i < nsteps; i++) {
    		GeomVector2d vNextDirMcs = vDirMcs.otherRotateToRad(stepAngleRad);

    		GeomPoint2d ptIMcs = new GeomPoint2d(vDirMcs.getXF(), vDirMcs.getYF());
    		GeomPoint2d ptFMcs = new GeomPoint2d(vNextDirMcs.getXF(), vNextDirMcs.getYF()); 

    		GeomPoint2d ptIVideo = GeomUtil.directFrom3dMcsToVideo(v, new GeomPoint3d(ptIMcs));	
    		GeomPoint2d ptFVideo = GeomUtil.directFrom3dMcsToVideo(v, new GeomPoint3d(ptFMcs));	
    		
        	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
        	vDirMcs = vNextDirMcs;
    	}		
	}
	
    public static void drawPointProj(ICadViewBase v, GeomPoint3d ptMcs, double sizeScr, Graphics g)
    {
		GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);	

		double xMinVideo = ptVideo.getX() - sizeScr; 
		double yMinVideo = ptVideo.getY() - sizeScr; 
		//
		double xMaxVideo = ptVideo.getX() + sizeScr; 
		double yMaxVideo = ptVideo.getY() + sizeScr; 
		
    	g.drawLine((int)ptVideo.getX(), (int)yMinVideo, (int)ptVideo.getX(), (int)yMaxVideo);    		
    	g.drawLine((int)xMinVideo, (int)ptVideo.getY(), (int)xMaxVideo, (int)ptVideo.getY());    		
    }
	
	public static void drawLineProj(ICadViewBase v, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs, Graphics g)
	{
		GeomPoint2d ptIVideo = GeomUtil.directFrom3dMcsToVideo(v, ptIMcs);	
		GeomPoint2d ptFVideo = GeomUtil.directFrom3dMcsToVideo(v, ptFMcs);	
    		
    	g.drawLine((int)ptIVideo.getX(), (int)ptIVideo.getY(), (int)ptFVideo.getX(), (int)ptFVideo.getY());    		
	}
	
	public static void drawRectangleProj(ICadViewBase v, GeomPoint3d ptMinMcs, GeomPoint3d ptMaxMcs, Graphics g)
	{
		GeomPoint2d ptMinVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMinMcs);	
		GeomPoint2d ptMaxVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMaxMcs);	

		double wScr = Math.abs(ptMaxVideo.getX() - ptMinVideo.getX());		
		double hScr = Math.abs(ptMaxVideo.getY() - ptMinVideo.getY());		

		GeomPoint2d ptVideo = new GeomPoint2d(ptMinVideo.getX(), ptMaxVideo.getY());

    	g.drawRect((int)ptVideo.getX(), (int)ptVideo.getY(), (int)wScr, (int)hScr);    		
	}
	
	public static void drawTextProj(ICadViewBase v, String text, GeomPoint3d ptMcs, double heightScr, Graphics g)
	{
		GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);	

		double xPtVideo = ptVideo.getX(); 
		double yPtVideo = ptVideo.getY(); 
		
        int oldtextheight = GeomUtil.setTextHeight(g, (int)heightScr);
		
    	g.drawString(text, (int)xPtVideo, (int)yPtVideo);

		GeomUtil.setTextHeight(g, (int)oldtextheight);
	}
	
    public static void drawFaceProj(ICadViewBase v, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, Graphics g)
    {
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();		

		lsPts.add(pt0Mcs);
		lsPts.add(pt1Mcs);
		lsPts.add(pt2Mcs);
		//lsPts.add(pt0Mcs);

	    DrawUtil.drawPolygonProj(v, lsPts, g);
    }
	
    public static void drawFaceProj(ICadViewBase v, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, GeomPoint3d pt3Mcs, Graphics g)
    {
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();		

		lsPts.add(pt0Mcs);
		lsPts.add(pt1Mcs);
		lsPts.add(pt2Mcs);
		lsPts.add(pt3Mcs);
		//lsPts.add(pt0Mcs);

		DrawUtil.drawPolygonProj(v, lsPts, g);
    }
	
    public static void drawFaceProj(ICadViewBase v, ArrayList<GeomPoint3d> lsPts, Graphics g)
    {
		DrawUtil.drawPolygonProj(v, lsPts, g);
    }
	
    public static void drawPolygonProj(ICadViewBase v, ArrayList<GeomPoint3d> lsPtsMcs, Graphics g)
    {
		int sz = lsPtsMcs.size();
		if(sz == 0) return;
		
		int n = sz + 1;
		
		int[] arrX = new int[n];
		int[] arrY = new int[n];
		
		int pos = 0;
		for(int i = 0; i < sz; i++) {
			GeomPoint3d ptMcs = lsPtsMcs.get(i);
			
			GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);		
			arrX[pos] = (int)ptVideo.getX();
			arrY[pos] = (int)ptVideo.getY();
			pos += 1;
		}

		arrX[pos] = arrX[0];
		arrY[pos] = arrY[0];
		
    	g.drawPolygon(arrX, arrY, n);
    }
	
    public static void drawEdgeProj(ICadViewBase v, ArrayList<GeomPoint3d> lsBasePtsMcs, ArrayList<GeomPoint3d> lsElevPtsMcs, Graphics g)
    {
		int sz = lsBasePtsMcs.size();

		GeomPoint3d ptBaseIMcs = lsBasePtsMcs.get(0);
		GeomPoint3d ptElevIMcs = lsElevPtsMcs.get(0);
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptBaseFMcs = lsBasePtsMcs.get(i);
			GeomPoint3d ptElevFMcs = lsElevPtsMcs.get(i);

			DrawUtil.drawFaceProj(v, ptBaseIMcs, ptBaseFMcs, ptElevFMcs, ptElevIMcs, g);

			ptBaseIMcs = ptBaseFMcs;
			ptElevIMcs = ptElevFMcs;
		}
    }
    
    public static void drawBoxProj(ICadViewBase v, GeomPoint3d ptMin3dMcs, GeomPoint3d ptMax3dMcs, Graphics g)
    {
    	double xMinMcs = ptMin3dMcs.getX();
    	double yMinMcs = ptMin3dMcs.getY();
    	double zMinMcs = ptMin3dMcs.getZ();
    	//
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

        DrawUtil.drawFaceProj(v, pt1Mcs, pt2Mcs, pt3Mcs, pt4Mcs, g);
        DrawUtil.drawFaceProj(v, pt1Mcs, pt2Mcs, pt6Mcs, pt5Mcs, g);
        DrawUtil.drawFaceProj(v, pt2Mcs, pt3Mcs, pt7Mcs, pt6Mcs, g);
        DrawUtil.drawFaceProj(v, pt3Mcs, pt4Mcs, pt8Mcs, pt7Mcs, g);
        DrawUtil.drawFaceProj(v, pt4Mcs, pt1Mcs, pt5Mcs, pt8Mcs, g);
        DrawUtil.drawFaceProj(v, pt5Mcs, pt6Mcs, pt7Mcs, pt8Mcs, g);
    }
	
    public static void fillFaceProj(ICadViewBase v, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, Graphics g)
    {
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();		

		lsPts.add(pt0Mcs);
		lsPts.add(pt1Mcs);
		lsPts.add(pt2Mcs);
		lsPts.add(pt0Mcs);

	    DrawUtil.fillPolygonProj(v, lsPts, g);
    }
	
    public static void fillFaceProj(ICadViewBase v, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, GeomPoint3d pt3Mcs, Graphics g)
    {
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();		

		lsPts.add(pt0Mcs);
		lsPts.add(pt1Mcs);
		lsPts.add(pt2Mcs);
		lsPts.add(pt3Mcs);
		lsPts.add(pt0Mcs);

		DrawUtil.fillPolygonProj(v, lsPts, g);
    }
	
    public static void fillFaceProj(ICadViewBase v, ArrayList<GeomPoint3d> lsPts, Graphics g)
    {
	    DrawUtil.fillPolygonProj(v, lsPts, g);
    }
	
    public static void fillPolygonProj(ICadViewBase v, ArrayList<GeomPoint3d> lsPtsMcs, Graphics g)
    {
		int sz = lsPtsMcs.size();

		int[] arrX = new int[sz + 1];
		int[] arrY = new int[sz + 1];
		
		for(int i = 0; i < sz; i++) {
			GeomPoint3d ptMcs = lsPtsMcs.get(i);
			
			GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);		
			arrX[i] = (int)ptVideo.getX();
			arrY[i] = (int)ptVideo.getY();
		}

		//CLOSE POLYGON
		GeomPoint3d ptMcs = lsPtsMcs.get(0);
		
		GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);		
		arrX[sz] = (int)ptVideo.getX();
		arrY[sz] = (int)ptVideo.getY();
		
    	g.fillPolygon(arrX, arrY, sz);
    }
	
    public static void fillEdgeProj(ICadViewBase v, ArrayList<GeomPoint3d> lsBasePtsMcs, ArrayList<GeomPoint3d> lsElevPtsMcs, Graphics g)
    {
		int sz = lsBasePtsMcs.size();

		GeomPoint3d ptBaseIMcs = lsBasePtsMcs.get(0);
		GeomPoint3d ptElevIMcs = lsElevPtsMcs.get(0);
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptBaseFMcs = lsBasePtsMcs.get(i);
			GeomPoint3d ptElevFMcs = lsElevPtsMcs.get(i);

			DrawUtil.fillFaceProj(v, ptBaseIMcs, ptBaseFMcs, ptElevFMcs, ptElevIMcs, g);

			ptBaseIMcs = ptBaseFMcs;
			ptElevIMcs = ptElevFMcs;
		}

		//CLOSE POLYGON
		GeomPoint3d ptBaseFMcs = lsBasePtsMcs.get(0);
		GeomPoint3d ptElevFMcs = lsElevPtsMcs.get(0);

		DrawUtil.fillFaceProj(v, ptBaseIMcs, ptBaseFMcs, ptElevFMcs, ptElevIMcs, g);
    }
	
    public static void fillBoxProj(ICadViewBase v, GeomPoint3d ptMin3dMcs, GeomPoint3d ptMax3dMcs, Graphics g)
    {
    	double xMinMcs = ptMin3dMcs.getX();
    	double yMinMcs = ptMin3dMcs.getY();
    	double zMinMcs = ptMin3dMcs.getZ();

    	double xMaxMcs = ptMax3dMcs.getX();
    	double yMaxMcs = ptMax3dMcs.getY();
    	double zMaxMcs = ptMax3dMcs.getZ();

    	GeomPoint3d pt0Mcs = new GeomPoint3d(xMinMcs, yMinMcs, zMinMcs); 
    	GeomPoint3d pt1Mcs = new GeomPoint3d(xMaxMcs, yMinMcs, zMinMcs); 
    	GeomPoint3d pt2Mcs = new GeomPoint3d(xMaxMcs, yMaxMcs, zMinMcs); 
    	GeomPoint3d pt3Mcs = new GeomPoint3d(xMinMcs, yMaxMcs, zMinMcs); 

        DrawUtil.fillFaceProj(v, pt0Mcs, pt1Mcs, pt2Mcs, pt3Mcs, g);

    	GeomPoint3d pt5Mcs = new GeomPoint3d(xMinMcs, yMinMcs, zMaxMcs); 
    	GeomPoint3d pt6Mcs = new GeomPoint3d(xMaxMcs, yMinMcs, zMaxMcs); 
    	GeomPoint3d pt7Mcs = new GeomPoint3d(xMaxMcs, yMaxMcs, zMaxMcs); 
    	GeomPoint3d pt8Mcs = new GeomPoint3d(xMinMcs, yMaxMcs, zMaxMcs); 

        DrawUtil.fillFaceProj(v, pt5Mcs, pt6Mcs, pt7Mcs, pt8Mcs, g);
    }
	
    public static void fillCilinderProj(ICadViewBase v, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, double diameter, Graphics g)
    {
    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	double radius = diameter / 2.0;
    	
    	GeomVector3d vIF = new GeomVector3d(ptI3dMcs, ptF3dMcs);
    	GeomVector3d uIF = vIF.otherUnit();

    	GeomVector3d nIF = uIF.otherNorm();
    	
    	GeomVector3d axisX = uIF.vectProd(nIF);
    	
    	int nsteps = numsegs + 1;

		GeomVector3d uCurrDirMcs = new GeomVector3d(nIF);
    	for(int i = 0; i < nsteps; i++) {
    		GeomVector3d uNextDirMcs = uCurrDirMcs.otherRotateToRad(axisX, stepAngleRad);

    		GeomPoint3d ptI3dMcs0 = ptI3dMcs.otherMoveTo(uCurrDirMcs, radius);
    		GeomPoint3d ptF3dMcs1 = ptF3dMcs.otherMoveTo(uCurrDirMcs, radius);
    		GeomPoint3d ptF3dMcs2 = ptF3dMcs.otherMoveTo(uNextDirMcs, radius);
    		GeomPoint3d ptI3dMcs3 = ptI3dMcs.otherMoveTo(uNextDirMcs, radius);
    		
            DrawUtil.fillFaceProj(v, ptI3dMcs0, ptF3dMcs1, ptF3dMcs2, ptI3dMcs3, g);
    	}		
    }
	
}
