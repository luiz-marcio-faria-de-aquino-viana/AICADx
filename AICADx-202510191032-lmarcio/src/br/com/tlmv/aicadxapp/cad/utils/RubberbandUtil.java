/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * RubberbandUtil.java
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

import java.awt.Color;
import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class RubberbandUtil 
{
//Public
	
	//PICKPOINT (ANY)
	public static void drawRubberband_PickPoint(ICadViewBase v, Graphics g, GeomPoint2d ptMcsF, GeomPoint2d currMousePosScr)
	{
		if(currMousePosScr == null) return;
		
		GeomPoint2d ptLabelMcs = new GeomPoint2d(ptMcsF);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		ptLabelMcs.selfOffsetTo(lrMarg, tbMarg);
		
		String strLabel = ptMcsF.toStr();
		
		DrawUtil.drawTextMcs(v, strLabel, ptLabelMcs, heightScr, g);
	}
	
	//PICKSECONDPOINT
	public static void drawRubberband_PickSecondPoint(ICadViewBase v, Graphics g, GeomPoint2d ptMcsI, GeomPoint2d ptMcsF)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		GeomPoint2d ptLabelMcs = GeomUtil.midPointOf(ptMcsI, ptMcsF);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		ptLabelMcs.selfOffsetTo(lrMarg, tbMarg);
			
		double dist = ptMcsI.distTo(ptMcsF);
	
		String strLabel = String.format(
			"%s m", 
			nf3.format(dist) );

		DrawUtil.drawLineMcs(v, ptMcsI, ptMcsF, g);
		DrawUtil.drawTextMcs(v, strLabel, ptLabelMcs, heightScr, g);
	}	
	
	public static void drawRubberband_PickSecondPoint(ICadViewBase v, Graphics g, GeomPoint2d ptMcsI, ArrayList<GeomPoint2d> lsPtsMcs, GeomPoint2d ptMcsF)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		GeomPoint2d ptLabelMcs = GeomUtil.midPointOf(ptMcsI, ptMcsF);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		ptLabelMcs.selfOffsetTo(lrMarg, tbMarg);
			
		double dist = ptMcsI.distTo(ptMcsF);
	
		String strLabel = String.format(
			"%s m", 
			nf3.format(dist) );

		int szLsPtsMcs = lsPtsMcs.size();
		if(szLsPtsMcs > 0) {
			GeomPoint2d pt0Mcs = lsPtsMcs.get(0); 

			for(int i = 1; i < szLsPtsMcs; i++) {
				GeomPoint2d pt1Mcs = lsPtsMcs.get(i); 

				DrawUtil.drawLineMcs(v, pt0Mcs, pt1Mcs, g);
				pt0Mcs = pt1Mcs;
			}
		}
		DrawUtil.drawLineMcs(v, ptMcsI, ptMcsF, g);
		DrawUtil.drawTextMcs(v, strLabel, ptLabelMcs, heightScr, g);
	}	
	
	//PICKSECONDCORNER
	public static void drawRubberband_PickSecondCorner(ICadViewBase v, Graphics g, GeomPoint2d ptMcsI, GeomPoint2d ptMcsF)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		//INITIALIZE_DIMENSIONS (MCS)
		//
		GeomDimension2d dimension = GeomUtil.dimensionOf(ptMcsI, ptMcsF);
		dimension.debug(AppDefs.DEBUG_LEVEL01);

		//DIMENSIONS (MCS)
		//
		GeomPoint2d ptMinMcs = dimension.getPtMin();
		GeomPoint2d ptMaxMcs = dimension.getPtMax();
		GeomPoint2d ptCentroidMcs = dimension.getPtCentroid();
		GeomPoint2d ptMiddleHorizMcs = dimension.getPtMiddleHoriz();
		GeomPoint2d ptMiddleVertMcs = dimension.getPtMiddleVert();

		GeomPoint2d ptLabelHorizMcs = ptMiddleHorizMcs.otherOffsetTo(0.0, tbMarg);
		GeomPoint2d ptLabelVertMcs = ptMiddleVertMcs.otherOffsetTo(lrMarg, 0.0);
		
		DrawUtil.drawTextMcs(v, dimension.getAreaStr(), ptCentroidMcs, heightScr, g);
		DrawUtil.drawTextMcs(v, dimension.getWidthStr(), ptLabelHorizMcs, heightScr, g);
		DrawUtil.drawTextMcs(v, dimension.getHeightStr(), ptLabelVertMcs, heightScr, g);

		DrawUtil.drawRectangleMcs(v, ptMinMcs, ptMaxMcs, g);
	}
	
	//PICKRADIUS
	public static void drawRubberband_PickRadius(ICadViewBase v, Graphics g, GeomPoint2d ptCenterMcs, GeomPoint2d ptRadiusMcs)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double radiusMcs = ptCenterMcs.distTo(ptRadiusMcs);
        //double diameterMcs = 2.0 * radiusMcs;

		GeomPoint2d ptMiddleMcs = GeomUtil.midPointOf(ptCenterMcs, ptRadiusMcs);
		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		String strRadius = String.format("Radius: %s m", nf3.format(radiusMcs));

		DrawUtil.drawLineMcs(v, ptCenterMcs, ptRadiusMcs, g);
		DrawUtil.drawCircleMcs(v, ptCenterMcs, radiusMcs, g);
		DrawUtil.drawTextMcs(v, strRadius, ptMiddleMcs, heightScr, g);
	}
	
	//RADIUS (CENTER-START or CENTER-END)
	public static void drawRubberband_Arc_CenterStart(ICadViewBase v, Graphics g, GeomPoint2d ptMcsI, GeomPoint2d ptMcsF)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		GeomPoint2d ptMiddleMcs = GeomUtil.midPointOf(ptMcsI, ptMcsF);
		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		ptMiddleMcs.selfOffsetTo(lrMarg, tbMarg);
			
		double dist = ptMcsI.distTo(ptMcsF);

		String str = String.format(
			"%s m", 
			nf3.format(dist) );
			
		DrawUtil.drawLineMcs(v, ptMcsI, ptMcsF, g);
		DrawUtil.drawTextMcs(v, str, ptMiddleMcs, heightScr, g);
	}
	
	//DIAMETER (START-END)
	public static void drawRubberband_Arc_StartEnd(ICadViewBase v, Graphics g, GeomPoint2d ptStartMcs, GeomPoint2d ptEndMcs)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		GeomPoint2d ptMiddleMcs = GeomUtil.midPointOf(ptStartMcs, ptEndMcs);

		GeomPoint2d ptLabelMcs = new GeomPoint2d(ptMiddleMcs);
		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		ptLabelMcs.selfOffsetTo(lrMarg, tbMarg);

		double diameterMcs = ptStartMcs.distTo(ptEndMcs);

        double radiusMcs = diameterMcs / 2.0;

		GeomVector2d v2d = new GeomVector2d(ptStartMcs, ptEndMcs);
		double startAngleRad = v2d.angleToAxisX();
        double angleRad = AppDefs.MATHVAL_HPI; 
        
		DrawUtil.drawLineMcs(v, ptStartMcs, ptEndMcs, g);
		DrawUtil.drawArcMcs(v, ptMiddleMcs, radiusMcs, startAngleRad, angleRad, g);
		
		String strLabel = String.format(
			"%s m", 
			nf3.format(diameterMcs) );

		DrawUtil.drawTextMcs(v, strLabel, ptLabelMcs, heightScr, g);
	}			
	
	//ARC (CENTER-START-END)
	public static void drawRubberband_Arc_CenterStartEnd(ICadViewBase v, Graphics g, GeomPoint2d ptCenterMcs, GeomPoint2d ptStartMcs, GeomPoint2d ptEndMcs)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double radiusMcs = ptCenterMcs.distTo(ptStartMcs);
        //double diameterMcs = 2.0 * radiusMcs;

		GeomVector2d vCenterStart2d = new GeomVector2d(ptCenterMcs, ptStartMcs);
		double startAngleRad = vCenterStart2d.angleToAxisX();

		GeomVector2d vCenterEnd2d = new GeomVector2d(ptCenterMcs, ptEndMcs);
		double endAngleRad = vCenterEnd2d.angleToAxisX();
		if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
			endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;
		
		GeomPoint2d ptMiddleMcs = GeomUtil.midPointOf(ptCenterMcs, ptStartMcs);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		GeomPoint2d ptLabel1Mcs = new GeomPoint2d(ptMiddleMcs);
		ptLabel1Mcs.selfOffsetTo(lrMarg, tbMarg);
		
		GeomPoint2d ptLabel2Mcs = new GeomPoint2d(ptLabel1Mcs);
		ptLabel2Mcs.selfOffsetTo(0.0, tbMarg);
		
		double angleRad = endAngleRad - startAngleRad;
		double angleDegrees = GeomUtil.convertRadToDegrees(angleRad);
		
		String strLabel1 = String.format(
			"Angle: %s Degrees", 
			nf3.format(angleDegrees) );
        
		String strLabel2 = String.format(
			"Radius: %s m", 
			nf3.format(radiusMcs) );
			
		DrawUtil.drawLineMcs(v, ptCenterMcs, ptStartMcs, g);
		DrawUtil.drawArcMcs(v, ptCenterMcs, radiusMcs, startAngleRad, endAngleRad, g);
		DrawUtil.drawTextMcs(v, strLabel1, ptLabel1Mcs, heightScr, g);
		DrawUtil.drawTextMcs(v, strLabel2, ptLabel2Mcs, heightScr, g);
	}
	
	//ANGLE (CENTER-START-END)
	public static void drawRubberband_Angle_CenterStartEnd(ICadViewBase v, Graphics g, GeomPoint2d ptCenterMcs, GeomPoint2d ptStartMcs, GeomPoint2d ptEndMcs)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double radiusMcs = ptCenterMcs.distTo(ptStartMcs);
        //double diameterMcs = 2.0 * radiusMcs;

		GeomVector2d vCenterStart2d = new GeomVector2d(ptCenterMcs, ptStartMcs);
		double startAngleRad = vCenterStart2d.angleToAxisX();

		GeomVector2d vCenterEnd2d = new GeomVector2d(ptCenterMcs, ptEndMcs);
		double endAngleRad = vCenterEnd2d.angleToAxisX();
		if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
			endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;
		
		GeomPoint2d ptMiddleMcs = GeomUtil.midPointOf(ptCenterMcs, ptStartMcs);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		GeomPoint2d ptLabel1Mcs = new GeomPoint2d(ptMiddleMcs);
		ptLabel1Mcs.selfOffsetTo(lrMarg, tbMarg);
		
		GeomPoint2d ptLabel2Mcs = new GeomPoint2d(ptLabel1Mcs);
		ptLabel2Mcs.selfOffsetTo(0.0, tbMarg);
		
		double angleRad = endAngleRad - startAngleRad;
		double angleDegrees = GeomUtil.convertRadToDegrees(angleRad);
        
		String strLabel1 = String.format(
			"Angle: %s Degrees", 
			nf3.format(angleDegrees) );
        
		String strLabel2 = String.format(
			"Radius: %s m", 
			nf3.format(radiusMcs) );
		
		DrawUtil.drawLineMcs(v, ptCenterMcs, ptStartMcs, g);
		DrawUtil.drawLineMcs(v, ptCenterMcs, ptEndMcs, g);
		DrawUtil.drawArcMcs(v, ptCenterMcs, radiusMcs, startAngleRad, endAngleRad, g);
		DrawUtil.drawTextMcs(v, strLabel1, ptLabel1Mcs, heightScr, g);
		DrawUtil.drawTextMcs(v, strLabel2, ptLabel2Mcs, heightScr, g);
	}
	
	//POINT_AT_DIR
	public static void drawRubberband_PickPointAtDir(ICadViewBase v, Graphics g, GeomPoint2d ptBase2dMcs, GeomVector2d vDir2dMcs, GeomPoint2d ptCurr2dMcs)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		GeomVector2d uDir = vDir2dMcs.otherUnit();
		
		GeomVector2d vBaseToCurr = new GeomVector2d(ptBase2dMcs, ptCurr2dMcs);
		
		double dist = uDir.dotProd(vBaseToCurr);
		
		GeomVector2d vCurr2dMcs = uDir.otherMult(dist);
		
		GeomPoint2d pt2dMcs = new GeomPoint2d(vCurr2dMcs.getXF(), vCurr2dMcs.getYF()); 

		GeomPoint2d ptMiddleMcs = GeomUtil.midPointOf(ptBase2dMcs, pt2dMcs);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		GeomPoint2d ptLabel1Mcs = new GeomPoint2d(ptMiddleMcs);
		ptLabel1Mcs.selfOffsetTo(lrMarg, tbMarg);
		
		String strLabel1 = String.format(
			"%s m", 
			nf3.format(dist) );
		
		DrawUtil.drawLineMcs(v, ptBase2dMcs, pt2dMcs, g);
		DrawUtil.drawTextMcs(v, strLabel1, ptLabel1Mcs, heightScr, g);
	}
	
	//ZOOMPOINT (ANY)
	public static void drawRubberband_ZoomPoint(ICadViewBase v, Graphics g, GeomPoint2d ptMcsF, GeomPoint2d currMousePosScr)
	{
		if(currMousePosScr == null) return;
		
		GeomPoint2d ptLabelMcs = new GeomPoint2d(ptMcsF);

		double heightScr = v.fromScrToMcs(AppDefs.RUBBERBAND_TEXT_HEIGHTSCR);

		double lrMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR );
		double tbMarg = v.fromScrToMcs( (double)AppDefs.RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR );
		
		ptLabelMcs.selfOffsetTo(lrMarg, tbMarg);
		
		String strLabel = ptMcsF.toStr();
		
		DrawUtil.drawTextMcs(v, strLabel, ptLabelMcs, heightScr, g);
	}
	
	//ZOOMSECONDPOINT
	public static void drawRubberband_ZoomSecondPoint(ICadViewBase v, Graphics g, GeomPoint2d ptMcsI, GeomPoint2d ptMcsF)
	{
		DrawUtil.drawLineMcs(v, ptMcsI, ptMcsF, g);
	}	
	
	//ZOOMSECONDCORNER
	public static void drawRubberband_ZoomSecondCorner(ICadViewBase v, Graphics g, GeomPoint2d ptMcsI, GeomPoint2d ptMcsF)
	{
		GeomDimension2d dimension = GeomUtil.dimensionOf(ptMcsI, ptMcsF);
		dimension.debug(AppDefs.DEBUG_LEVEL01);

		GeomPoint2d ptMinMcs = dimension.getPtMin();
		GeomPoint2d ptMaxMcs = dimension.getPtMax();
		
		DrawUtil.drawRectangleMcs(v, ptMinMcs, ptMaxMcs, g);
	}

}
