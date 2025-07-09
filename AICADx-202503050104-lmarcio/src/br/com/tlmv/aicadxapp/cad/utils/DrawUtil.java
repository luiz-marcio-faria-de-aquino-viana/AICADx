/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DrawUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.utils;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadView2d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import javafx.scene.layout.BorderStrokeStyle;

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
	
	public static void drawArcMcs(ICadViewBase v, GeomPoint2d ptCenterMcs, GeomPoint2d ptStartMcs, GeomPoint2d ptEndMcs, Graphics g)
	{
		double radius = ptCenterMcs.distTo(ptStartMcs);
    	
        double startAngleRad = GeomUtil.angleFromAxisX(ptCenterMcs, ptStartMcs);
        double endAngleRad = GeomUtil.angleFromAxisX(ptCenterMcs, ptEndMcs);
        if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
        	endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;

        drawArcMcs(v, ptCenterMcs, radius, startAngleRad, endAngleRad, g);
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
		
    	g.drawPolygon(arrXMcs, arrYMcs, sz);
	}
	
	public static void drawPointMcs(ICadViewBase v, GeomPoint2d ptMcs, double sizeScr, Graphics g)
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
	
	public static void drawTextMcs(ICadViewBase v, String text, GeomPoint2d ptMcs, Graphics g)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);
		
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);

		double xPtVideo = ptVideo.getX(); 
		double yPtVideo = ptVideo.getY(); 
		
    	g.drawString(text, (int)xPtVideo, (int)yPtVideo);
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
            ptIns = ptMcs.otherMoveTo(axisX, - (wTextMcs / 2.0));
        }
        else if(horizAlign == AppDefs.HORIZALIGN_RIGHT) {
            ptIns = ptMcs.otherMoveTo(axisX, - wTextMcs);            
        }
        
    	//VERTALIGN
        if(horizAlign == AppDefs.VERTALIGN_TOP) {
        	/* nothing todo! */            
        }
        else if(horizAlign == AppDefs.VERTALIGN_MIDDLE) {
            ptIns = ptMcs.otherMoveTo(axisY, - (hTextMcs / 2.0));            
        }
        else if(horizAlign == AppDefs.VERTALIGN_BOTTOM) {
            ptIns = ptMcs.otherMoveTo(axisY, - hTextMcs);            
        }
        
    	drawTextMcs(v, text, ptIns, g);
        
		GeomUtil.setTextHeight(g, (int)oldtextheight);
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
	
    public static void drawPolygonProj(ICadViewBase v, ArrayList<GeomPoint3d> lsPtsMcs, Graphics g)
    {
		int sz = lsPtsMcs.size();

		int[] arrX = new int[sz + 1];
		int[] arrY = new int[sz + 1];
		
		int pos = 0;
		for(int i = 0; i < sz; i++) {
			GeomPoint3d ptMcs = lsPtsMcs.get(i);
			
			GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);		
			arrX[pos] = (int)ptVideo.getX();
			arrY[pos] = (int)ptVideo.getY();
			pos += 1;
		}

		GeomPoint3d ptMcs = lsPtsMcs.get(0);
		
		GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);
		
		arrX[pos] = (int)ptVideo.getX();
		arrY[pos] = (int)ptVideo.getY();
		
    	g.drawPolygon(arrX, arrY, sz);
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

	    DrawUtil.fillPolygonProj(v, lsPts, g);
    }
	
    public static void fillFaceProj(ICadViewBase v, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, GeomPoint3d pt3Mcs, Graphics g)
    {
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();		

		lsPts.add(pt0Mcs);
		lsPts.add(pt1Mcs);
		lsPts.add(pt2Mcs);
		lsPts.add(pt3Mcs);

		DrawUtil.fillPolygonProj(v, lsPts, g);
    }
	
    public static void fillPolygonProj(ICadViewBase v, ArrayList<GeomPoint3d> lsPtsMcs, Graphics g)
    {
		int sz = lsPtsMcs.size();

		int[] arrX = new int[sz];
		int[] arrY = new int[sz];
		
		for(int i = 0; i < sz; i++) {
			GeomPoint3d ptMcs = lsPtsMcs.get(i);
			
			GeomPoint2d ptVideo = GeomUtil.directFrom3dMcsToVideo(v, ptMcs);		
			arrX[i] = (int)ptVideo.getX();
			arrY[i] = (int)ptVideo.getY();
		}
		
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
	
}
