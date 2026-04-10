/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GeomUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/02/2025
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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomLine2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MidLineSegment2dVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;

public class GeomUtil 
{
//Public
	
	/* LINESTRING */	
	
	public static double lengthOfLineString(ArrayList<GeomPoint2d> lsPts2d)
	{
		int szLsPts = lsPts2d.size();
		if(szLsPts <= 1) return 0.0;
		
		double dDistTotal = 0.0;

		GeomPoint2d ptI = new GeomPoint2d( lsPts2d.get(0) );
		for(int i = 1; i < szLsPts; i++) {
			GeomPoint2d ptF = lsPts2d.get(i);
			
			double dDist = ptI.distTo(ptF);			
			dDistTotal += dDist;
			
			ptI = ptF;
		}
		return dDistTotal;
	}
	
	public static GeomPoint2d pointAtDistanceOfLineString(ArrayList<GeomPoint2d> lsPts2d, double d)
	{
		if(lsPts2d == null) return null;
		
		int szLsPts = lsPts2d.size();
		if(szLsPts == 0) return null;

		GeomPoint2d ptI = lsPts2d.get(0);
		double dDistTotal = 0.0;

		if(szLsPts == 1) return ptI;

		GeomPoint2d ptF = null;
		double dDist = 0.0;
		for(int i = 0; i < szLsPts; i++) {
			ptF = lsPts2d.get(0);
			dDist = ptI.distTo(ptF);

			double dTmpDistTotal = dDistTotal + dDist;
			if(dTmpDistTotal >= d) {
				GeomVector2d vIF = new GeomVector2d(ptI, ptF);
				double diff = d - dDistTotal;

				GeomPoint2d pt0 = ptI.otherMoveTo(vIF, diff);
				return pt0;
			}
			ptI = ptF;
		}
		
		if(ptF != null)
			return ptF;
		return ptI;
	}
	
	public static GeomPoint2d midPointOfLineString(ArrayList<GeomPoint2d> lsPts2d)
	{
		double dDistTotal = GeomUtil.lengthOfLineString(lsPts2d);
		double d = dDistTotal / 2.0;
		
		GeomPoint2d ptResult = GeomUtil.pointAtDistanceOfLineString(lsPts2d, d);
		return ptResult;
	}
	
	public static MidLineSegment2dVO midSegmentOfLineString(ArrayList<GeomPoint2d> lsPts2d)
	{
		double dDistTotal = GeomUtil.lengthOfLineString(lsPts2d);
		double d = dDistTotal / 2.0;
		
		if(lsPts2d == null) return null;
		
		int szLsPts = lsPts2d.size();
		if(szLsPts == 0) return null;

		GeomPoint2d ptI = lsPts2d.get(0);
		double dCalcDistTotal = 0.0;

		GeomPoint2d ptF = null;
		double dDist = 0.0;
		for(int i = 0; i < szLsPts; i++) {
			ptF = lsPts2d.get(i);
			dDist = ptI.distTo(ptF);

			dCalcDistTotal += dDist;
			if(dCalcDistTotal >= d) {
				MidLineSegment2dVO midSeg = new MidLineSegment2dVO(ptI, ptF);
				return midSeg;
			}
			ptI = ptF;
		}
		
		if(ptF != null)
			return new MidLineSegment2dVO(ptI, ptF);
		return new MidLineSegment2dVO(ptI, ptI);
	}
	
	/* LINESTRING - PTI + PTF + LSPTS */	

	public static double lengthOfLineString(GeomPoint2d ptI, GeomPoint2d ptF, ArrayList<GeomPoint2d> lsPts2d)
	{
		ArrayList<GeomPoint2d> lsNewPts = new ArrayList<GeomPoint2d>();

		// ADD-PTS
		//
		if(ptI != null)
			lsNewPts.add(ptI);

		if(lsPts2d != null)
			lsNewPts.addAll(lsPts2d);
		
		if(ptF != null)
			lsNewPts.add(ptF);
		
		double dDistTotal = GeomUtil.lengthOfLineString(lsNewPts);
		return dDistTotal;
	}
	
	public static GeomPoint2d pointAtDistanceOfLineString(GeomPoint2d ptI, GeomPoint2d ptF, ArrayList<GeomPoint2d> lsPts2d, double d)
	{
		ArrayList<GeomPoint2d> lsNewPts = new ArrayList<GeomPoint2d>();

		// ADD-PTS
		//
		if(ptI != null)
			lsNewPts.add(ptI);

		if(lsPts2d != null)
			lsNewPts.addAll(lsPts2d);
		
		if(ptF != null)
			lsNewPts.add(ptF);
		
		GeomPoint2d ptResult = GeomUtil.pointAtDistanceOfLineString(lsNewPts, d);
		return ptResult;
	}
	
	public static GeomPoint2d midPointOfLineString(GeomPoint2d ptI, GeomPoint2d ptF, ArrayList<GeomPoint2d> lsPts2d)
	{
		ArrayList<GeomPoint2d> lsNewPts = new ArrayList<GeomPoint2d>();

		// ADD-PTS
		//
		if(ptI != null)
			lsNewPts.add(ptI);

		if(lsPts2d != null)
			lsNewPts.addAll(lsPts2d);
		
		if(ptF != null)
			lsNewPts.add(ptF);
		
		GeomPoint2d ptResult = GeomUtil.midPointOfLineString(lsNewPts);
		return ptResult;
	}
	
	public static MidLineSegment2dVO midSegmentOfLineString(GeomPoint2d ptI, GeomPoint2d ptF, ArrayList<GeomPoint2d> lsPts2d)
	{
		ArrayList<GeomPoint2d> lsNewPts = new ArrayList<GeomPoint2d>();

		// ADD-PTS
		//
		if(ptI != null)
			lsNewPts.add(ptI);

		if(lsPts2d != null)
			lsNewPts.addAll(lsPts2d);
		
		if(ptF != null)
			lsNewPts.add(ptF);
		
		MidLineSegment2dVO ptResult = GeomUtil.midSegmentOfLineString(lsNewPts);
		return ptResult;
	}
	
	/* PIPES */
	
	public static GeomPoint2d toPipe0d(GeomPoint2d pt0, GeomPoint2d ptI, GeomPoint2d ptF)
	{
		GeomVector2d vIF = new GeomVector2d(ptI, ptF);
		GeomVector2d uIF = vIF.otherUnit();
		
		GeomVector2d vF0 = new GeomVector2d(ptF, pt0);		
		double dPerp = uIF.dotProd(vF0);
		if(dPerp < 0.0) return null;
		
		GeomPoint2d ptDir = ptF.otherMoveTo(uIF, dPerp);
		return ptDir;
	}

	public static GeomPoint2d toPipe90d(GeomPoint2d pt0, GeomPoint2d ptI, GeomPoint2d ptF)
	{
		GeomVector2d vIF = new GeomVector2d(ptI, ptF);
		GeomVector2d uIF = vIF.otherUnit();
		
		GeomVector2d vF0 = new GeomVector2d(ptF, pt0);		
		double dPerp = uIF.dotProd(vF0);
		if(dPerp < 0.0) return null;
		
		GeomPoint2d ptPerp = ptF.otherMoveTo(uIF, dPerp);
		return ptPerp;
	}
	
	public static GeomPoint2d toPipe45d(GeomPoint2d pt0, GeomPoint2d ptI, GeomPoint2d ptF)
	{
		GeomVector2d vIF = new GeomVector2d(ptI, ptF);
		GeomVector2d uIF = vIF.otherUnit();
		
		GeomVector2d vF0 = new GeomVector2d(ptF, pt0);		
		double dPerp = uIF.dotProd(vF0);
		if(dPerp < 0.0) return null;
		
		GeomPoint2d ptPerp = ptF.otherMoveTo(uIF, dPerp);
		double dH = ptPerp.distTo(pt0);

		double dD = dPerp - dH;
		if(dD < 0.0) return null;

		GeomPoint2d pt45d = ptF.otherMoveTo(uIF, dD);
		return pt45d;
	}	
	
	//2D-ENVELOP

	public static GeomDimension2d getEnvelop2d(int objType) {
		MainPanel panel = MainPanel.getMainPanel();

		CadDocumentDef doc = panel.getCurrDocumentDef();
		
		CadBlockDef blk = doc.getCurrBlockDef();
		
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(blk, objType);
		return oGeomDim;
	}

	public static GeomDimension2d getEnvelop2d(CadBlockDef blkDef, int objType) {
		CadEntity[] arrEntity = blkDef.findAllEntity();

		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(arrEntity, objType);
		return oGeomDim;
	}

	public static GeomDimension2d getEnvelop2d(CadEntity[] arrEntity, int objType) {
		//LIMITS
		//
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		//
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		
		int szLsEntity = arrEntity.length;
		for(int i = 0; i < szLsEntity; i++) {
			CadEntity oEnt = arrEntity[i];

			if(oEnt.getObjType() == AppDefs.OBJTYPE_PROJECT_DEF) continue;
			
			if( (objType != AppDefs.OBJTYPE_NONE) && (objType != AppDefs.OBJTYPE_ALL) && (objType != AppDefs.OBJTYPE_ANY) ) {
				if(oEnt.getObjType() != objType) continue;
			}
			
			GeomDimension2d geomDim2d = oEnt.getEnvelop2d();
			GeomPoint2d ptMin = geomDim2d.getPtMin();
			GeomPoint2d ptMax = geomDim2d.getPtMax();
			
			if(ptMin.getX() < minX)
				minX = ptMin.getX(); 
			if(ptMin.getY() < minY)
				minY = ptMin.getY(); 
			
			if(ptMax.getX() > maxX)
				maxX = ptMax.getX(); 
			if(ptMax.getY() > maxY)
				maxY = ptMax.getY(); 
		}

		GeomPoint2d ptResMin2d = new GeomPoint2d(minX, minY);  
		GeomPoint2d ptResMax2d = new GeomPoint2d(maxX, maxY);  
		
		GeomDimension2d oDim = new GeomDimension2d(ptResMin2d, ptResMax2d); 
		return oDim;
	}
	
	public static GeomDimension2d getEnvelop2d(ArrayList<ItemDataVO> lsEntity) {
		//LIMITS
		//
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		//
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		
		int szLsEntity = lsEntity.size();
		for(int i = 0; i < szLsEntity; i++) {
			ItemDataVO oEnt = lsEntity.get(i);

			GeomDimension2d geomDim2d = oEnt.getGeomDim2d();
			GeomPoint2d ptMin = geomDim2d.getPtMin();
			GeomPoint2d ptMax = geomDim2d.getPtMax();
			
			if(ptMin.getX() < minX)
				minX = ptMin.getX(); 
			if(ptMin.getY() < minY)
				minY = ptMin.getY(); 
			
			if(ptMax.getX() > maxX)
				maxX = ptMax.getX(); 
			if(ptMax.getY() > maxY)
				maxY = ptMax.getY(); 
		}

		GeomPoint2d ptResMin2d = new GeomPoint2d(minX, minY);  
		GeomPoint2d ptResMax2d = new GeomPoint2d(maxX, maxY);  
		
		GeomDimension2d oDim = new GeomDimension2d(ptResMin2d, ptResMax2d); 
		return oDim;
	}
	
	//ORDER_LINE_SEQUENCE

//	public static GeomPoint3d[] findConnectedLineTo(GeomPoint3d ptRef3d, ArrayList<CadLine> lsEntities) {
//		GeomPoint3d[] arrResult = new GeomPoint3d[2];
//		
//		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d );
//		
//		for(CadLine oEnt : lsEntities) {
//			GeomPoint2d ptI2d = new GeomPoint2d( oEnt.getPtI() );
//			GeomPoint2d ptF2d = new GeomPoint2d( oEnt.getPtF() );
//
//			if( ptRef2d.distTo(ptI2d) < AppDefs.MATHPREC_MIN ) {
//				arrResult[0] = new GeomPoint3d( oEnt.getPtI() );
//				arrResult[1] = new GeomPoint3d( oEnt.getPtF() );
//			}
//			else if( ptRef2d.distTo(ptF2d) < AppDefs.MATHPREC_MIN ) {
//					arrResult[0] = new GeomPoint3d( oEnt.getPtF() );
//					arrResult[1] = new GeomPoint3d( oEnt.getPtI() );
//			}
//		}
//		return arrResult;
//	}
	
	public static CadLine findConnectedLineTo_202511091133(GeomPoint3d ptRef3d, ArrayList<CadEntity> lsEntities) 
	{
		GeomPoint2d ptRef2d = new GeomPoint2d(ptRef3d);
		for(CadEntity ent : lsEntities) {
			CadLine oLine = (CadLine)ent;
			
			GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
			GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

			double dPtRefToPtI = ptRef2d.distTo(ptI2d);
			double dPtRefToPtF = ptRef2d.distTo(ptF2d);			
			if( dPtRefToPtI < AppDefs.MATHPREC_MIN ) {
				return oLine;
			}
			else if( dPtRefToPtF < AppDefs.MATHPREC_MIN ) {
				return oLine;
			}
		}
		return null;
	}
	
	public static ArrayList<CadEntity> toOrderedEntities_202511091133(CadLine oLine, ArrayList<CadEntity> lsEntities) 
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>(); 

		GeomPoint3d ptRef3d = new GeomPoint3d( oLine.getPtF() );
		
		CadLine oCurrLine = oLine;
		while(oCurrLine != null) {
			oCurrLine = GeomUtil.findConnectedLineTo_202511091133(ptRef3d, lsEntities);
			if(oCurrLine != null) {
				lsEntities.remove(oCurrLine);
				lsResult.add(oCurrLine);
			}
		}
		return lsResult;
	}
	
	//FIRST_CONNECTED_LINE
	
	public static int findFirstConnectedLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities) {
		int sz = lsEntities.size();
		for(int i = 0; i < sz; i++) {
			CadLine oLine = (CadLine)lsEntities.get(i);
			
			GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
			GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

			if( ( ptRef2d.distTo(ptI2d) < AppDefs.MATHPREC_MIN ) || ( ptRef2d.distTo(ptF2d) < AppDefs.MATHPREC_MIN ) )
				return i;
		}
		return -1;
	}

	public static int findFirstConnectedLine(GeomPoint3d ptRef3d, ArrayList<CadEntity> lsEntities) {
		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d );		

		int pos = findFirstConnectedLine(ptRef2d, lsEntities);
		return pos;
	}
	
	//CONNECTED_LINE
	
	public static int findConnectedLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities) {
		int sz = lsEntities.size();
		for(int i = 0; i < sz; i++) {
			CadLine oLine = (CadLine)lsEntities.get(i);
			
			GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
			GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

			double dI = ptRef2d.distTo(ptI2d);
			double dF = ptRef2d.distTo(ptF2d);
			
			if(dI < AppDefs.MATHPREC_MIN) {
				return i;
			}
			else if(dF < AppDefs.MATHPREC_MIN) {
				return i;
			}
		}
		return -1;
	}

	public static int findConnectedLine(GeomPoint3d ptRef3d, ArrayList<CadEntity> lsEntities) {
		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d );		

		int pos = findConnectedLine(ptRef2d, lsEntities);
		return pos;
	}
	
	//NEAREST_CONNECTED_LINE
	
	public static int findNearestConnectedLine(GeomPoint2d ptInside2d, GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities) {
		double maxDotProd = - Double.MAX_VALUE;
		double minProd = Double.MAX_VALUE;
		int pos = -1;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();		

		double xPtInside = ptInside2d.getX();
		double yPtInside = ptInside2d.getY();		

		GeomVector2d vPtRefToPtInside = new GeomVector2d(xPtRef, yPtRef, xPtInside, yPtRef);
		GeomVector2d uPtRefToPtInside = vPtRefToPtInside.otherUnit();
		
		GeomVector2d vDir = new GeomVector2d(ptRef2d, ptInside2d); 
		GeomVector2d uDir = vDir.otherUnit();
		
		int sz = lsEntities.size();
		for(int i = 0; i < sz; i++) {
			CadLine oLine = (CadLine)lsEntities.get(i);
			
			GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
			GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

			double dI = ptRef2d.distTo(ptI2d);
			double dF = ptRef2d.distTo(ptF2d);
			
			GeomVector2d uIF = null;
			if(dI < AppDefs.MATHPREC_MIN) {
				GeomVector2d vIF = new GeomVector2d(ptI2d, ptF2d);
				uIF = vIF.otherUnit();
			}
			else if(dF < AppDefs.MATHPREC_MIN) {
				GeomVector2d vIF = new GeomVector2d(ptF2d, ptI2d);
				uIF = vIF.otherUnit();
			}

			if(uIF != null) {
				GeomVector3d zDir = uDir.vectProd(uIF);
				double valueZDir = zDir.getZOrig();
				if(valueZDir >= 0) {
					double valueProd = Math.abs( zDir.getZOrig() );
					if(valueProd < minProd) {
						double valueDotProd = uDir.dotProd(uIF);
						if(valueDotProd > maxDotProd) {
							minProd = valueProd;
							maxDotProd = valueDotProd;
							pos = i;
						}
					}
				}
			}
		}
		return pos;
	}

	public static int findNearestConnectedLine(GeomPoint3d ptInside3d, GeomPoint3d ptRef3d, ArrayList<CadEntity> lsEntities) {
		GeomPoint2d ptInside2d = new GeomPoint2d( ptInside3d );		
		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d );		

		int pos = findNearestConnectedLine(ptInside2d, ptRef2d, lsEntities);
		return pos;
	}
	
	//NEAREST_CONNECTED_LINES_TO_REFPOINT	

	public static ArrayList<CadEntity> selectNearestConnectedLines(GeomPoint3d ptInside3d, ArrayList<CadEntity> lsEntities)
	{
		GeomPoint2d ptInside2d = new GeomPoint2d( ptInside3d );		

		ArrayList<CadEntity> lsLines = GeomUtil.selectNearestConnectedLines(ptInside2d, lsEntities);
		return lsLines;
	}

	public static ArrayList<CadEntity> selectNearestConnectedLines(GeomPoint2d ptInside2d, ArrayList<CadEntity> lsEntities)
	{
		ArrayList<CadEntity> lsLines = new ArrayList<CadEntity>();

		//TODO:
		
		return lsLines;
	}

	//ORDER_LINE_SEQUENCE
		
	public static ArrayList<GeomPoint3d> sortSequentialLines(ArrayList<CadEntity> lsEntities) {
		if(lsEntities == null) return null;
		if(lsEntities.size() == 0) return null;
		
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		Hashtable map = new Hashtable();

		ArrayList<CadEntity> lsTmp = new ArrayList<CadEntity>(lsEntities);		
		if(lsTmp.size() > 0) {
			CadLine oLine = (CadLine)lsTmp.remove(0);
	
			//PT-I
			//
			GeomPoint3d ptI3d = new GeomPoint3d( oLine.getPtI() );
			if( !map.containsKey(ptI3d.toKeyValue()) ) {
				map.put(ptI3d.toKeyValue(), ptI3d);
				lsResult.add(ptI3d);
			}
	
			//PT-F
			//
			GeomPoint3d ptF3d = new GeomPoint3d( oLine.getPtF() );
			if( !map.containsKey(ptF3d.toKeyValue()) ) {
				map.put(ptF3d.toKeyValue(), ptF3d);
				lsResult.add(ptF3d);
			}
	
			GeomPoint2d ptRef2d = new GeomPoint2d( ptF3d );
			while(lsTmp.size() > 0) {		
				int pos = GeomUtil.findFirstConnectedLine(ptRef2d, lsTmp);
				if(pos == -1)
					pos = 0;

				oLine = (CadLine)lsTmp.remove(pos);
	
				//PT-I
				//
				ptI3d = new GeomPoint3d( oLine.getPtI() );
				ptF3d = new GeomPoint3d( oLine.getPtF() );

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
				double dPtRefToPtI = ptRef2d.distTo(ptI2d);
				
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );
				double dPtRefToPtF = ptRef2d.distTo(ptF2d);

				if(dPtRefToPtI < dPtRefToPtF) 
				{
					//PT-I and PT-F
					//
					if( !map.containsKey(ptI3d.toKeyValue()) ) {
						map.put(ptI3d.toKeyValue(), ptI3d);
						lsResult.add(ptI3d);
					}
				
					if( !map.containsKey(ptF3d.toKeyValue()) ) {
						map.put(ptF3d.toKeyValue(), ptF3d);
						lsResult.add(ptF3d);
					}

					ptRef2d = new GeomPoint2d( ptF3d );					
				}
				else {
					//PT-F and PT-I
					//
					if( !map.containsKey(ptF3d.toKeyValue()) ) {
						map.put(ptF3d.toKeyValue(), ptF3d);
						lsResult.add(ptF3d);
					}
					
					if( !map.containsKey(ptI3d.toKeyValue()) ) {
						map.put(ptI3d.toKeyValue(), ptI3d);
						lsResult.add(ptI3d);
					}

					ptRef2d = new GeomPoint2d( ptI3d );					
				}
			}		
		}
		return lsResult;
	}
		
	//3D-ENVELOP

	public static GeomDimension3d getEnvelop3d(CadEntity[] arrEntity, int objType) {
		//LIMITS
		//
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		//
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		double maxZ = - Double.MAX_VALUE;
		
		int szLsEntity = arrEntity.length;
		for(int i = 0; i < szLsEntity; i++) {
			CadEntity oEnt = arrEntity[i];

			if(oEnt.getObjType() == AppDefs.OBJTYPE_PROJECT_DEF) continue;
			
			if( (objType != AppDefs.OBJTYPE_NONE) && (objType != AppDefs.OBJTYPE_ALL) && (objType != AppDefs.OBJTYPE_ANY) ) {
				if(oEnt.getObjType() != objType) continue;
			}
			
			GeomDimension3d geomDim3d = oEnt.getEnvelop3d();
			GeomPoint3d ptMin = geomDim3d.getPtMin();
			GeomPoint3d ptMax = geomDim3d.getPtMax();
			
			if(ptMin.getX() < minX)
				minX = ptMin.getX(); 
			if(ptMin.getY() < minY)
				minY = ptMin.getY(); 
			if(ptMin.getZ() < minZ)
				minZ = ptMin.getZ(); 
			
			if(ptMax.getX() > maxX)
				maxX = ptMax.getX(); 
			if(ptMax.getY() > maxY)
				maxY = ptMax.getY(); 
			if(ptMax.getZ() > maxZ)
				maxZ = ptMax.getZ(); 
		}

		GeomPoint3d ptResMin3d = new GeomPoint3d(minX, minY, minZ);  
		GeomPoint3d ptResMax3d = new GeomPoint3d(maxX, maxY, maxZ);  
		
		GeomDimension3d oDim = new GeomDimension3d(ptResMin3d, ptResMax3d); 
		return oDim;
	}

	/* AREA_TYPE */
	
	public static String getAreaTypeText(int areaType)
	{
        String strTipoArea = "Apartamento";

        if(areaType == AppDefs.OPT_AREATYPE_ROOM)
        	strTipoArea = "Ambiente";
        else if(areaType == AppDefs.OPT_AREATYPE_APARTMENT)
        	strTipoArea = "Apartamento";
        else if(areaType == AppDefs.OPT_AREATYPE_BALCONY)
        	strTipoArea = "Varanda";
        else if(areaType == AppDefs.OPT_AREATYPE_BUILDINGCOMMOM)
        	strTipoArea = "Area Comum";
        else if(areaType == AppDefs.OPT_AREATYPE_BUILDINGINTERNAL)
        	strTipoArea = "Area Interna";
        else if(areaType == AppDefs.OPT_AREATYPE_BUILDINGEXTERNAL)
        	strTipoArea = "Area Externa";
        else if(areaType == AppDefs.OPT_AREATYPE_PARKING)
        	strTipoArea = "Estacionamento";
        else if(areaType == AppDefs.OPT_AREATYPE_TERRAIN)
        	strTipoArea = "Terreno";
        else if(areaType == AppDefs.OPT_AREATYPE_DRENAGEAREA)
        	strTipoArea = "Area Drenagem";
        return strTipoArea;
	}
    
	public static ArrayList<ItemDataVO> toBasicPropertyList(CadEntity oEnt)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		Class c = oEnt.getClass();		

		int objectId = oEnt.getObjectId();
		CadLayerDef oLayer = oEnt.getLayer();
		CadLevel oLevel = oEnt.getLevel();
		
		String strObjectId = Integer.toString(objectId);
		String strObjType = oEnt.getObjTypeStr();		
		String strLayerName = oLayer.getName();
		
		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();		

		lsProperty.add( new ItemDataVO("ObjectId", strObjectId, false) );		
		lsProperty.add( new ItemDataVO("ObjType", strObjType, false) );		
		lsProperty.add( new ItemDataVO("Layer", strLayerName, false) );

		if(oLevel != null) {
			String strLevelText = oLevel.getLevelLocalText();
			String strZLevel = String.format("%s m", nf3.format( oLevel.getZLevel() ) );
			lsProperty.add( new ItemDataVO("Level", strLevelText, false) );
			lsProperty.add( new ItemDataVO("Z-Level", strZLevel, false) );
		}
		
		return lsProperty;
	}
	
	/* SQUARE_METER_TO_HECTARE */
	    
    public static double convertFromSquareMeterToHectare(double areaM2) {
    	double areaHectare = areaM2 / 10000.0;
    	return areaHectare;
    }
	
	/* COPY POINT LIST 2D/3D */
	
	public static ArrayList<GeomPoint2d> copyPt2dList(ArrayList<GeomPoint2d> lsPts2d)
	{
		ArrayList<GeomPoint2d> lsResult = new ArrayList<GeomPoint2d>();
		
		for(GeomPoint2d oPt2d : lsPts2d)
		{
			lsResult.add( new GeomPoint2d(oPt2d) );
		}
		return lsResult;
	}
	
	public static ArrayList<GeomPoint3d> copyPt3dList(ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		
		for(GeomPoint3d oPt3d : lsPts3d)
		{
			lsResult.add( new GeomPoint3d(oPt3d) );
		}
		return lsResult;
	}
	
	public static ArrayList<GeomPoint3d> copyPt2dTo3dList(ArrayList<GeomPoint2d> lsPts2d, double z)
	{
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		
		for(GeomPoint2d oPt2d : lsPts2d)
		{
			lsResult.add( new GeomPoint3d(oPt2d.getX(), oPt2d.getY(), z) );
		}
		return lsResult;
	}
	
	public static ArrayList<GeomPoint3d> copyPt3dTo3dList(ArrayList<GeomPoint3d> lsPts3d, double z)
	{
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		
		for(GeomPoint3d oPt3d : lsPts3d)
		{
			lsResult.add( new GeomPoint3d(oPt3d.getX(), oPt3d.getY(), z) );
		}
		return lsResult;
	}
	
	public static ArrayList<GeomPoint2d> copyPt3dTo2dList(ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint2d> lsResult = new ArrayList<GeomPoint2d>();
		
		for(GeomPoint3d oPt3d : lsPts3d)
		{
			lsResult.add( new GeomPoint2d(oPt3d.getX(), oPt3d.getY()) );
		}
		return lsResult;
	}
	
    /* CALCULATE_AREA */
    
    public static double calculateArea(GeomPoint3d ptCentroid, ArrayList<GeomPoint3d> lsPts)
    {
    	double area = 0.0;
    	
    	int sz = lsPts.size();
    	if(sz >= 3) {
    		GeomPoint3d pt1 = lsPts.get(0);
    		for(int i = 1; i < sz; i++) {
    			GeomPoint3d pt2 = lsPts.get(i);
    			
    			GeomVector3d vC1 = new GeomVector3d(ptCentroid, pt1);
    			//GeomVector3d vC2 = new GeomVector3d(ptCentroid, pt2);

    			GeomVector3d v12 = new GeomVector3d(pt1, pt2);
    			GeomVector3d u12 = v12.otherUnit();    			
    			double dBase = v12.mod();

    			GeomVector3d vAltura = u12.vectProd(vC1);
    			double dAltura = Math.abs(vAltura.getZOrig());

    			area += (dBase * dAltura) / 2.0;
    			pt1 = pt2;
    		}
    	}
    	return area;
    }
    
	/* MOVE PLAN-XY: POINT / POINT_LIST */

    public static GeomPoint3d moveTo(GeomPoint3d ptBase3d, GeomPoint3d ptRef3d, GeomPoint3d pt3d)
    {
    	GeomVector3d vDir = new GeomVector3d(ptBase3d, ptRef3d);
    	double d = vDir.mod();
    	
    	GeomPoint3d ptNew3d = pt3d.otherMoveTo(vDir, d);
    	return ptNew3d;
    }
	
    public static ArrayList<GeomPoint3d> moveTo(GeomPoint3d ptBase3d, GeomPoint3d ptRef3d, ArrayList<GeomPoint3d> lsPts3d)
    {
    	ArrayList<GeomPoint3d> lsNewPts = new ArrayList<GeomPoint3d>();
    	for(GeomPoint3d pt3d : lsPts3d) {
    		GeomPoint3d ptNew3d = GeomUtil.moveTo(ptBase3d, ptRef3d, pt3d);
    		lsNewPts.add(ptNew3d);
    	}
    	return lsNewPts;
    }
	
	/* SCALE PLAN-XY: POINT / POINT_LIST */

    public static GeomPoint3d scaleTo(GeomPoint3d ptIns3d, GeomPoint3d pt3d, double sclFact)
    {
    	GeomVector3d vPtInsToPt3d = new GeomVector3d(ptIns3d, pt3d);

    	GeomVector3d vNewPtInsToPt3d = vPtInsToPt3d.otherMult(sclFact);

    	double xNew3d = vNewPtInsToPt3d.getXF();
    	double yNew3d = vNewPtInsToPt3d.getYF();
    	double zNew3d = vNewPtInsToPt3d.getZF();
    	
    	GeomPoint3d ptNew3d = new GeomPoint3d(xNew3d, yNew3d, zNew3d);
    	return ptNew3d;
    }
	
    public static ArrayList<GeomPoint3d> scaleTo(GeomPoint3d ptIns3d, ArrayList<GeomPoint3d> lsPts3d, double sclFact)
    {
    	ArrayList<GeomPoint3d> lsNewPts = new ArrayList<GeomPoint3d>();
    	for(GeomPoint3d pt3d : lsPts3d) {
    		GeomPoint3d ptNew3d = GeomUtil.scaleTo(ptIns3d, pt3d, sclFact);
    		lsNewPts.add(ptNew3d);
    	}
    	return lsNewPts;
    }
	
	/* ROTATE PLAN-XY: POINT / POINT_LIST */

    public static GeomPoint3d rotateTo(GeomPoint3d ptIns3d, GeomPoint3d pt3d, double rotateRad)
    {
    	GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);
    	GeomPoint2d pt2d = new GeomPoint2d(pt3d);
    	
    	GeomVector2d vPtInsToPt2d = new GeomVector2d(ptIns2d, pt2d);
    	
    	GeomVector2d vNewPtInsToPt2d = vPtInsToPt2d.otherRotateToRad(rotateRad);

    	double xNew3d = vNewPtInsToPt2d.getXF();
    	double yNew3d = vNewPtInsToPt2d.getYF();
    	double zNew3d = pt3d.getZ();
    	
    	GeomPoint3d ptNew3d = new GeomPoint3d(xNew3d, yNew3d, zNew3d);
    	return ptNew3d;
    }
	
    public static ArrayList<GeomPoint3d> rotateTo(GeomPoint3d ptIns3d, ArrayList<GeomPoint3d> lsPts3d, double rotateRad)
    {
    	ArrayList<GeomPoint3d> lsNewPts = new ArrayList<GeomPoint3d>();
    	for(GeomPoint3d pt3d : lsPts3d) {
    		GeomPoint3d ptNew3d = GeomUtil.rotateTo(ptIns3d, pt3d, rotateRad);
    		lsNewPts.add(ptNew3d);
    	}
    	return lsNewPts;
    }
	
	/* MAX/MIN VALUE POSITION */
	
	public static int positionMin(double min1, double min2, double min3)
	{
		ArrayList<Double> lsValues = new ArrayList<Double>();
		lsValues.add(min1);
		lsValues.add(min2);
		lsValues.add(min3);
		
		int pos = positionMin(lsValues);
		return pos;
	}	
	
	public static int positionMin(double min1, double min2, double min3, double min4)
	{
		ArrayList<Double> lsValues = new ArrayList<Double>();
		lsValues.add(min1);
		lsValues.add(min2);
		lsValues.add(min3);
		lsValues.add(min4);
		
		int pos = positionMin(lsValues);
		return pos;
	}	
	
	public static int positionMin(double min1, double min2, double min3, double min4, double min5, double min6)
	{
		ArrayList<Double> lsValues = new ArrayList<Double>();
		lsValues.add(min1);
		lsValues.add(min2);
		lsValues.add(min3);
		lsValues.add(min4);
		lsValues.add(min5);
		lsValues.add(min6);
		
		int pos = positionMin(lsValues);
		return pos;
	}	
	
	public static int positionMin(ArrayList<Double> lsValues)
	{
		int pos = -1;
		
		double min_val = Double.MAX_VALUE;

		for(int i = 0; i < lsValues.size(); i++) {
			double val = lsValues.get(i);
			if(val < min_val)
				pos = i;
		}
		return pos;
	}	
	
	public static int positionMax(double min1, double min2, double min3)
	{
		ArrayList<Double> lsValues = new ArrayList<Double>();
		lsValues.add(min1);
		lsValues.add(min2);
		lsValues.add(min3);
		
		int pos = positionMax(lsValues);
		return pos;
	}	
	
	public static int positionMax(double min1, double min2, double min3, double min4)
	{
		ArrayList<Double> lsValues = new ArrayList<Double>();
		lsValues.add(min1);
		lsValues.add(min2);
		lsValues.add(min3);
		lsValues.add(min4);
		
		int pos = positionMax(lsValues);
		return pos;
	}	
	
	public static int positionMax(double min1, double min2, double min3, double min4, double min5, double min6)
	{
		ArrayList<Double> lsValues = new ArrayList<Double>();
		lsValues.add(min1);
		lsValues.add(min2);
		lsValues.add(min3);
		lsValues.add(min4);
		lsValues.add(min5);
		lsValues.add(min6);
		
		int pos = positionMax(lsValues);
		return pos;
	}	
	
	public static int positionMax(ArrayList<Double> lsValues)
	{
		int pos = -1;
		
		double max_val = - Double.MAX_VALUE;

		for(int i = 0; i < lsValues.size(); i++) {
			double val = lsValues.get(i);
			if(val > max_val)
				pos = i;
		}
		return pos;
	}	
	
	/* 2D/3D DIRECT_FROM */
	
	public static GeomPoint2d directFrom3dMcsToVideo(ICadViewBase v, GeomPoint3d ptMcs)
	{
		GeomPoint3d ptProj = v.fromMcsToProj(ptMcs);
		GeomPoint2d ptScr = v.fromProjToScr(ptProj);
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);
		return ptVideo;
	}

	public static GeomPoint2d directFrom2dMcsToVideo(ICadViewBase v, GeomPoint2d ptMcs)
	{
		GeomPoint2d ptScr = v.fromMcsToScr(ptMcs);
		GeomPoint2d ptVideo = v.fromScrToVideo(ptScr);
		return ptVideo;
	}
	
	/* 2D/3D ARRAY CONVERTION */
	
	public static ArrayList<GeomPoint3d> from2dTo3d(ArrayList<GeomPoint2d> lsPts2d, double z)
	{
		ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint2d oPt2d : lsPts2d) {
			GeomPoint3d oPt3d = new GeomPoint3d(oPt2d.getX(), oPt2d.getY(), z);
			lsPts3d.add(oPt3d);
		}
		return lsPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from2dTo3d(int tagId, String tagName, ArrayList<GeomPoint2d> lsPts2d, double z)
	{
		ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint2d oPt2d : lsPts2d) {
			GeomPoint3d oPt3d = new GeomPoint3d(tagId, tagName, oPt2d.getX(), oPt2d.getY(), z);
			lsPts3d.add(oPt3d);
		}
		return lsPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from2dTo3d(int tagId, ArrayList<GeomPoint2d> lsPts2d, double z)
	{
		ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint2d oPt2d : lsPts2d) {
			GeomPoint3d oPt3d = new GeomPoint3d(tagId, oPt2d.getX(), oPt2d.getY(), z);
			lsPts3d.add(oPt3d);
		}
		return lsPts3d;
	}
	
	public static ArrayList<GeomPoint2d> from3dTo2d(ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint2d oPt2d = new GeomPoint2d(oPt3d.getX(), oPt3d.getY());
			lsPts2d.add(oPt2d);
		}
		return lsPts2d;
	}
	
	public static ArrayList<GeomPoint2d> from3dTo2d(int tagId, String tagName, ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint2d oPt2d = new GeomPoint2d(tagId, tagName, oPt3d.getX(), oPt3d.getY());
			lsPts2d.add(oPt2d);
		}
		return lsPts2d;
	}
	
	public static ArrayList<GeomPoint2d> from3dTo2d(int tagId, ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint2d oPt2d = new GeomPoint2d(tagId, oPt3d.getX(), oPt3d.getY());
			lsPts2d.add(oPt2d);
		}
		return lsPts2d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(oPt3d.getX(), oPt3d.getY(), oPt3d.getZ());
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(int tagId, String tagName, ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(tagId, tagName, oPt3d.getX(), oPt3d.getY(), oPt3d.getZ());
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(int tagId, ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(tagId, oPt3d.getX(), oPt3d.getY(), oPt3d.getZ());
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(ArrayList<GeomPoint3d> lsPts3d, double z)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(oPt3d.getX(), oPt3d.getY(), z);
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(int tagId, String tagName, ArrayList<GeomPoint3d> lsPts3d, double z)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(tagId, tagName, oPt3d.getX(), oPt3d.getY(), z);
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(int tagId, ArrayList<GeomPoint3d> lsPts3d, double z)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(tagId, oPt3d.getX(), oPt3d.getY(), z);
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> from3dTo3d(ArrayList<GeomPoint3d> lsPts3d, GeomVector3d uDir3d, double d)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		for(GeomPoint3d oPt3d : lsPts3d) {
			GeomPoint3d oNewPt3d = oPt3d.otherMoveTo(uDir3d, d);
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> toPts3d(ArrayList<CadEntity> lsLineString)
	{
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		Hashtable map = new Hashtable();

		for(CadEntity ent : lsLineString) {
			CadLine oLine = (CadLine)ent;
			
			//PT-I
			//
			GeomPoint3d oPtI3d = oLine.getPtI();			
			String keyPtI3d = oPtI3d.toKeyValue();
			if( map.contains(keyPtI3d) ) {
				map.put(keyPtI3d, oPtI3d);
				
				lsResult.add(oPtI3d);
			}
			
			//PT-F
			//
			GeomPoint3d oPtF3d = oLine.getPtF();
			String keyPtF3d = oPtF3d.toKeyValue();
			if( map.contains(keyPtF3d) ) {
				map.put(keyPtF3d, oPtF3d);
				
				lsResult.add(oPtF3d);
			}
		}
		return lsResult;
	}

	/* INVERT_LSPTS */
	
	public static ArrayList<GeomPoint2d> invertFrom2dTo2d(ArrayList<GeomPoint2d> lsPts2d)
	{
		ArrayList<GeomPoint2d> lsNewPts2d = new ArrayList<GeomPoint2d>(); 

		int sz = lsPts2d.size() - 1;
		for(int i = sz; i >= 0; i--) {
			GeomPoint2d oNewPt2d = new GeomPoint2d( lsPts2d.get(i) );
			lsNewPts2d.add(oNewPt2d);
		}
		return lsNewPts2d;
	}
	
	public static ArrayList<GeomPoint2d> invertFrom3dTo2d(ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint2d> lsNewPts2d = new ArrayList<GeomPoint2d>(); 

		int sz = lsPts3d.size() - 1;
		for(int i = sz; i >= 0; i--) {
			GeomPoint2d oNewPt2d = new GeomPoint2d( lsPts3d.get(i) );
			lsNewPts2d.add(oNewPt2d);
		}
		return lsNewPts2d;
	}
	
	public static ArrayList<GeomPoint3d> invertFrom2dTo3d(ArrayList<GeomPoint2d> lsPts2d)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		int sz = lsPts2d.size() - 1;
		for(int i = sz; i >= 0; i--) {
			GeomPoint3d oNewPt3d = new GeomPoint3d( lsPts2d.get(i) );
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	public static ArrayList<GeomPoint3d> invertFrom3dTo3d(ArrayList<GeomPoint3d> lsPts3d)
	{
		ArrayList<GeomPoint3d> lsNewPts3d = new ArrayList<GeomPoint3d>(); 

		int sz = lsPts3d.size() - 1;
		for(int i = sz; i >= 0; i--) {
			GeomPoint3d oNewPt3d = new GeomPoint3d( lsPts3d.get(i) );
			lsNewPts3d.add(oNewPt3d);
		}
		return lsNewPts3d;
	}
	
	/* SET - COLORS/FONTS/TEXTS */

	public static Color setColor(Graphics g, Color newcolor)
	{
		if(newcolor == null) return null;
		
		Color oldcolor = g.getColor();
		g.setColor(newcolor);		
		return oldcolor;
	}

	public static Font setFont(Graphics g, Font newfont)
	{
		if(newfont == null) return null;
		
	    Font oldfont = g.getFont();
	    g.setFont(newfont);
		return oldfont;
	}
	
	public static int setTextHeight(Graphics g, int textHeightScr)
	{
	    Font oldfont = g.getFont();
	    int oldtextheight = oldfont.getSize();
	    Font newfont = new Font(Font.SANS_SERIF, Font.PLAIN, (int)textHeightScr);
	    g.setFont(newfont);
		return oldtextheight;
	}
	
	public static Stroke setLtype(Graphics g, Stroke newltype)
	{
		if(newltype == null) return null;
		
		Graphics2D g2d = (Graphics2D)g;
		Stroke oldltype = g2d.getStroke();
	    g2d.setStroke(newltype);
	    return oldltype;
	}
	
	/* ANGLE_CONVERSIONS */

	public static double convertRadToDegrees(double angRad)
	{
		double angDegrees = Math.toDegrees(angRad);

		if( Math.abs(angDegrees) < AppDefs.MATHPREC_MIN )
			angDegrees = 0.0;
		else if( Math.abs(angDegrees - AppDefs.MATHVAL_90d) < AppDefs.MATHPREC_MIN )
			angDegrees = AppDefs.MATHVAL_90d;
		else if( Math.abs(angDegrees - AppDefs.MATHVAL_180d) < AppDefs.MATHPREC_MIN )
			angDegrees = AppDefs.MATHVAL_180d;
		else if( Math.abs(angDegrees - AppDefs.MATHVAL_270d) < AppDefs.MATHPREC_MIN )
			angDegrees = AppDefs.MATHVAL_270d;
		else if( Math.abs(angDegrees - AppDefs.MATHVAL_360d) < AppDefs.MATHPREC_MIN )
			angDegrees = 0.0;
		
		return angDegrees;
	}

	public static double convertDegreesToRad(double angDegrees)
	{
		double angRad = Math.toRadians(angDegrees);

		if( Math.abs(angRad) < AppDefs.MATHPREC_MIN )
			angRad = 0.0;
		else if( Math.abs(angRad - AppDefs.MATHVAL_HPI) < AppDefs.MATHPREC_MIN )
			angRad = AppDefs.MATHVAL_HPI;
		else if( Math.abs(angRad - AppDefs.MATHVAL_PI) < AppDefs.MATHPREC_MIN )
			angRad = AppDefs.MATHVAL_PI;
		else if( Math.abs(angRad - AppDefs.MATHVAL_3HPI) < AppDefs.MATHPREC_MIN )
			angRad = AppDefs.MATHVAL_3HPI;
		else if( Math.abs(angRad - AppDefs.MATHVAL_2PI) < AppDefs.MATHPREC_MIN )
			angRad = 0.0;
		
		return angRad;
	}
	
	/* ANGLE_UTILITIES */
	
    public static double angleFromAxisX(GeomPoint2d ptI, GeomPoint2d ptF)
    {
    	GeomVector2d vDir = new GeomVector2d(ptI, ptF);
    	
    	double angleRad = vDir.angleToAxisX();
    	return angleRad;
    }
	
    public static double angleFromAxisX(GeomPoint3d ptI, GeomPoint3d ptF)
    {
    	GeomVector3d vDir = new GeomVector3d(ptI, ptF);
    	
    	double angleRad = vDir.angleToAxisX();
    	return angleRad;
    }

	public static double angleFromArc(GeomPoint2d ptCenterPoint, GeomPoint2d ptStartPoint, GeomPoint2d ptEndPoint)
	{
		GeomVector2d vCS = new GeomVector2d(ptCenterPoint, ptStartPoint);
		GeomVector2d vCE = new GeomVector2d(ptCenterPoint, ptEndPoint);

		double result = vCS.angleTo(vCE);
		return result;
	}

	public static double angleFromArc(GeomPoint3d ptCenterPoint, GeomPoint3d ptStartPoint, GeomPoint3d ptEndPoint)
	{
		GeomVector3d vCS = new GeomVector3d(ptCenterPoint, ptStartPoint);
		GeomVector3d vCE = new GeomVector3d(ptCenterPoint, ptEndPoint);

		double result = vCS.angleTo(vCE);
		return result;
	}

	/* ANGLE_ADJUST */
	
	public static double[] adjustEndAngleRad(double startAngleRad, double endAngleRad)
	{
		double[] arrAngleRad = new double[2];
		
		if(startAngleRad <= endAngleRad) {
			arrAngleRad[0] = startAngleRad;
			arrAngleRad[1] = endAngleRad;
		}
		else {
			arrAngleRad[0] = endAngleRad;
			arrAngleRad[1] = startAngleRad;
		}
		return arrAngleRad;
	}
	
	public static double[] adjustEndAngleDeegres(double startAngleDegrees, double endAngleDegrees)
	{
		double startAngleRad = GeomUtil.convertDegreesToRad(startAngleDegrees);
		double endAngleRad = GeomUtil.convertDegreesToRad(endAngleDegrees);
		
		double[] arrAngleRad = GeomUtil.adjustEndAngleRad(startAngleRad, endAngleRad);
		
		double finalStartAngleDegrees = GeomUtil.convertRadToDegrees(arrAngleRad[0]);
		double finalEndAngleDegrees = GeomUtil.convertRadToDegrees(arrAngleRad[1]);
		
		double[] arrAngleDegrees = new double[2];
		arrAngleDegrees[0] = finalStartAngleDegrees;
		arrAngleDegrees[1] = finalEndAngleDegrees;
		
		return arrAngleDegrees;
	}
	
	/* SCALE_FROM */
	
	public static double scaleFrom(GeomPoint2d ptBase2d, GeomPoint2d ptRef2d, GeomPoint2d ptCurr2d)
	{
		GeomVector2d vPtBase2dToPtCurr2d = new GeomVector2d(ptBase2d, ptCurr2d);
		double dist = vPtBase2dToPtCurr2d.mod();
		
		GeomVector2d vPtBase2dToPtRef2d = new GeomVector2d(ptBase2d, ptRef2d);
		double distRef = vPtBase2dToPtRef2d.mod();
		
		double scale = dist / distRef;
		return scale;
	}
			
	/* POINT_MOVE */
	
    public static MoveData2dVO moveToPt2d(GeomPoint2d ptBase, GeomPoint2d ptRef, GeomPoint2d ptOrig) 
    {
    	MoveData2dVO o = new MoveData2dVO();
    	o.init(ptBase, ptRef);
    	o.movePoint2d(ptOrig);
    	return o;
    }
	
    public static MoveData3dVO moveToPt3d(GeomPoint3d ptBase, GeomPoint3d ptRef, GeomPoint3d ptOrig) 
    {
    	MoveData3dVO o = new MoveData3dVO();
    	o.init(ptBase, ptRef);
    	o.movePoint3d(ptOrig);
    	return o;
    }
    
    /* POINT_MIRROR */
    
	public static GeomPoint3d mirror(GeomPoint3d pt3d, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs)
	{
		GeomVector2d vIF = new GeomVector2d(ptI2dMcs, ptF2dMcs);
		GeomVector2d nIF = vIF.otherNorm();
		
		GeomPoint2d pt2dMcs = new GeomPoint2d(pt3d);

		GeomVector2d vIToPt = new GeomVector2d(ptI2dMcs, pt2dMcs);
		double dist = nIF.dotProd(vIToPt);

		double dist_mirror = -2.0 * dist;
	
		GeomVector2d vToMirrorPt = nIF.otherMult(dist_mirror);
		
		double xPt = pt3d.getX() + vToMirrorPt.getXOrig();
		double yPt = pt3d.getY() + vToMirrorPt.getYOrig();
		double zPt = pt3d.getZ();
		
		GeomPoint3d ptResult = new GeomPoint3d(xPt, yPt, zPt);
		return ptResult;
	}
    
	public static GeomPoint2d mirror(GeomPoint2d pt2d, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs)
	{
		GeomVector2d vIF = new GeomVector2d(ptI2dMcs, ptF2dMcs);
		GeomVector2d nIF = vIF.otherNorm();
		
		GeomVector2d vIToPt = new GeomVector2d(ptI2dMcs, pt2d);
		double dist = nIF.dotProd(vIToPt);

		double dist_mirror = -2.0 * dist;
	
		GeomVector2d vToMirrorPt = nIF.otherMult(dist_mirror);
		
		double xPt = pt2d.getX() + vToMirrorPt.getXOrig();
		double yPt = pt2d.getY() + vToMirrorPt.getYOrig();
		
		GeomPoint2d ptResult = new GeomPoint2d(xPt, yPt);
		return ptResult;
	}
	
	/* POINT_SCALE */
	
    public static ScaleData2dVO scaleToPt2dByRefPt(GeomPoint2d ptBase, GeomPoint2d ptDir, GeomPoint2d ptRef, GeomPoint2d ptOrig) 
    {
    	ScaleData2dVO o = new ScaleData2dVO();
    	o.init(ptBase, ptDir, ptRef);
    	o.scalePoint2d(ptOrig);
    	return o;
    }
	
    public static ScaleData3dVO scaleToPt3dByRefPt(GeomPoint3d ptBase, GeomPoint3d ptDir, GeomPoint3d ptRef, GeomPoint3d ptOrig) 
    {
    	ScaleData3dVO o = new ScaleData3dVO();
    	o.init(ptBase, ptDir, ptRef);
    	o.scalePoint3d(ptOrig);
    	return o;
    }
	
    public static ScaleData2dVO scaleToPt2dByRefDist(double refDist, GeomPoint2d ptBase, GeomPoint2d ptDir, GeomPoint2d ptOrig) 
    {
    	ScaleData2dVO o = new ScaleData2dVO();
    	o.init(refDist, ptBase, ptDir);
    	o.scalePoint2d(ptOrig);
    	return o;
    }
	
    public static ScaleData3dVO scaleToPt3dByRefDist(double refDist, GeomPoint3d ptBase, GeomPoint3d ptDir, GeomPoint3d ptOrig) 
    {
    	ScaleData3dVO o = new ScaleData3dVO();
    	o.init(refDist, ptBase, ptDir);
    	o.scalePoint3d(ptOrig);
    	return o;
    }

	/* GEOMETRY */
    
    //
    // IntersectionOf()
    //
    
	public static GeomPoint2d intersectionOf(GeomPoint3d pt3dI1, GeomPoint3d pt3dF1, GeomPoint3d pt3dI2, GeomPoint3d pt3dF2)
	{
		GeomPoint2d pt2dI1 = new GeomPoint2d(pt3dI1); 
		GeomPoint2d pt2dF1 = new GeomPoint2d(pt3dF1);
		
		GeomPoint2d pt2dI2 = new GeomPoint2d(pt3dI2); 
		GeomPoint2d pt2dF2 = new GeomPoint2d(pt3dF2);
		
		GeomPoint2d pt2d = intersectionOf(pt2dI1, pt2dF1, pt2dI2, pt2dF2);
		return pt2d;
	}
		
	public static GeomPoint2d intersectionOf202512251036(GeomPoint2d pt2dI1, GeomPoint2d pt2dF1, GeomPoint2d pt2dI2, GeomPoint2d pt2dF2)
	{
		double dII = pt2dI1.distTo(pt2dI2);
		double dIF = pt2dI1.distTo(pt2dF2);

		double dFI = pt2dF1.distTo(pt2dI2);
		double dFF = pt2dF1.distTo(pt2dF2);

		if( (dII <= AppDefs.MATHPREC_MIN) || 
			(dIF <= AppDefs.MATHPREC_MIN) ) {
			return pt2dI1;
		}

		if( (dFI <= AppDefs.MATHPREC_MIN) || 
			(dFF <= AppDefs.MATHPREC_MIN) ) {
			return pt2dF1;
		}
		
		GeomPoint2d pt2dIA = new GeomPoint2d(pt2dI1);
		GeomPoint2d pt2dFA = new GeomPoint2d(pt2dF1);
		
		GeomPoint2d pt2dIB = new GeomPoint2d(pt2dI2);
		GeomPoint2d pt2dFB = new GeomPoint2d(pt2dF2);
		
		int pos = GeomUtil.positionMin(dII, dIF, dFI, dFF);
		if(pos == 0) {
			/* nothing todo! */
		}
		else if(pos == 1) {
			pt2dIA = new GeomPoint2d(pt2dI1);
			pt2dFA = new GeomPoint2d(pt2dF1);
			
			pt2dIB = new GeomPoint2d(pt2dF2);
			pt2dFB = new GeomPoint2d(pt2dI2);
		}
		else if(pos == 2) {
			pt2dIA = new GeomPoint2d(pt2dF1);
			pt2dFA = new GeomPoint2d(pt2dI1);
			
			pt2dIB = new GeomPoint2d(pt2dI2);
			pt2dFB = new GeomPoint2d(pt2dF2);
		}
		else if(pos == 3) {
			pt2dIA = new GeomPoint2d(pt2dF1);
			pt2dFA = new GeomPoint2d(pt2dI1);
			
			pt2dIB = new GeomPoint2d(pt2dF2);
			pt2dFB = new GeomPoint2d(pt2dI2);
		}
		
		GeomVector2d v2dA = new GeomVector2d(pt2dIA, pt2dFA);
		GeomVector2d u2dA = v2dA.otherUnit();

		double dHipotenusaA = v2dA.mod();
		
		GeomVector2d v2dB = new GeomVector2d(pt2dIB, pt2dFB);
		GeomVector2d u2dB = v2dB.otherUnit();

		GeomVector3d v3dAxB = u2dA.vectProd(v2dB);
		
		double dCatetoOpostoA = v3dAxB.getZOrig();
		
		GeomVector2d v2dIBIA = new GeomVector2d(pt2dIB, pt2dIA);
		
		GeomVector3d v3dBxIBIA = u2dB.vectProd(v2dIBIA);
		
		double dDiff = v3dBxIBIA.getZOrig();
		
		double d = (dDiff * dHipotenusaA) / dCatetoOpostoA;
		
		GeomPoint2d pt2d = pt2dIA.otherMoveTo(u2dA, d);
		return pt2d;
	}

	public static GeomPoint2d intersectionOf(GeomPoint2d pt2dI1, GeomPoint2d pt2dF1, GeomPoint2d pt2dI2, GeomPoint2d pt2dF2)
	{
		GeomLine2d oLine1 = new GeomLine2d(pt2dI1, pt2dF1);
		GeomLine2d oLine2 = new GeomLine2d(pt2dI2, pt2dF2);

		GeomPoint2d ptResult = oLine1.intersectionOf(oLine2);
		return ptResult;
	}
	
    //
    // ExactlyIntersectionOf()
    //
    
	public static GeomPoint2d exactlyIntersectionOf(GeomPoint3d pt3dI1, GeomPoint3d pt3dF1, GeomPoint3d pt3dI2, GeomPoint3d pt3dF2)
	{
		GeomPoint2d pt2dI1 = new GeomPoint2d(pt3dI1); 
		GeomPoint2d pt2dF1 = new GeomPoint2d(pt3dF1);
		
		GeomPoint2d pt2dI2 = new GeomPoint2d(pt3dI2); 
		GeomPoint2d pt2dF2 = new GeomPoint2d(pt3dF2);
		
		GeomPoint2d pt2d = GeomUtil.exactlyIntersectionOf(pt2dI1, pt2dF1, pt2dI2, pt2dF2);
		return pt2d;
	}
		
	public static GeomPoint2d exactlyIntersectionOf(GeomPoint2d pt2dI1, GeomPoint2d pt2dF1, GeomPoint2d pt2dI2, GeomPoint2d pt2dF2)
	{
		GeomPoint2d pt2d = GeomUtil.intersectionOf(pt2dI1, pt2dF1, pt2dI2, pt2dF2);
		if( pt2d == null ) return null;
		
		double xPt2d = pt2d.getX();
		double yPt2d = pt2d.getY();
		
		double xPtMin2d = Math.min( pt2dI1.getX(), pt2dF1.getX() );
		double yPtMin2d = Math.min( pt2dI1.getY(), pt2dF1.getY() );

		double xPtMax2d = Math.max( pt2dI1.getX(), pt2dF1.getX() );
		double yPtMax2d = Math.max( pt2dI1.getY(), pt2dF1.getY() );
		
		if( ( (xPt2d >= xPtMin2d) && (xPt2d <= xPtMax2d) ) &&
			( (yPt2d >= yPtMin2d) && (yPt2d <= yPtMax2d) ) ) {
			return pt2d;
		}
		return null;
	}
    
    //
    // IsHorizCrossing()
    //
	
	public static boolean isHorizCrossing(double yPtRef, GeomPoint3d ptI3d, GeomPoint3d ptF3d)
	{
		GeomPoint2d ptI2d = new GeomPoint2d( ptI3d ); 
		GeomPoint2d ptF2d = new GeomPoint2d( ptF3d );
		
		boolean bResult = GeomUtil.isHorizCrossing(yPtRef, ptI2d, ptF2d);
		return bResult;
	}
		
	public static boolean isHorizCrossing(double yPtRef, GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		double yPtMin = Math.min( ptI2d.getY(), ptF2d.getY() );
		double yPtMax = Math.max( ptI2d.getY(), ptF2d.getY() );
		
		if( (yPtRef >= yPtMin) && (yPtRef <= yPtMax) ) {
			return true;
		}
		return false;
	}
    
    //
    // IsVertCrossing()
    //
	
	public static boolean isVertCrossing(double xPtRef, GeomPoint3d ptI3d, GeomPoint3d ptF3d)
	{
		GeomPoint2d ptI2d = new GeomPoint2d( ptI3d ); 
		GeomPoint2d ptF2d = new GeomPoint2d( ptF3d );
		
		boolean bResult = GeomUtil.isVertCrossing(xPtRef, ptI2d, ptF2d);
		return bResult;
	}
		
	public static boolean isVertCrossing(double xPtRef, GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		double xPtMin = Math.min( ptI2d.getX(), ptF2d.getX() );
		double xPtMax = Math.max( ptI2d.getX(), ptF2d.getX() );
		
		if( (xPtRef >= xPtMin) && (xPtRef <= xPtMax) ) {
			return true;
		}
		return false;
	}
    
    //
    // CheckIfInside()
    //
    
	public static boolean checkIfPointAInsideRectB(GeomPoint2d ptA2d, GeomPoint2d ptBMin2d, GeomPoint2d ptBMax2d)
	{
		double xPtA = ptA2d.getX();
		double yPtA = ptA2d.getY();
		
		double xPtBMin = ptBMin2d.getX();
		double yPtBMin = ptBMin2d.getY();
		
		double xPtBMax = ptBMax2d.getX();
		double yPtBMax = ptBMax2d.getY();

		if( ( (xPtA >= xPtBMin) && (xPtA <= xPtBMax) ) &&
			( (yPtA >= yPtBMin) && (yPtA <= yPtBMax) ) ) {
			return true;
		}
		return false;
	}
	
	public static boolean checkIfRectACrossingLine(GeomPoint2d ptAMin2d, GeomPoint2d ptAMax2d, GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		double xPtAMin = ptAMin2d.getX();
		double yPtAMin = ptAMin2d.getY();
		
		double xPtAMax = ptAMax2d.getX();
		double yPtAMax = ptAMax2d.getY();

		GeomPoint2d ptA0 = new GeomPoint2d(xPtAMin, yPtAMin);
		GeomPoint2d ptA1 = new GeomPoint2d(xPtAMax, yPtAMin);
		GeomPoint2d ptA2 = new GeomPoint2d(xPtAMax, yPtAMax);
		GeomPoint2d ptA3 = new GeomPoint2d(xPtAMin, yPtAMax);

		GeomPoint2d ptInter2d = GeomUtil.dirIntersectionOf(ptA0, ptA1, ptI2d, ptF2d, true);
		if(ptInter2d != null) return true; 
		
		ptInter2d = GeomUtil.dirIntersectionOf(ptA1, ptA2, ptI2d, ptF2d, true);
		if(ptInter2d != null) return true; 
		
		ptInter2d = GeomUtil.dirIntersectionOf(ptA2, ptA3, ptI2d, ptF2d, true);
		if(ptInter2d != null) return true; 
		
		ptInter2d = GeomUtil.dirIntersectionOf(ptA3, ptA0, ptI2d, ptF2d, true);
		if(ptInter2d != null) return true; 
		
		return false;
	}

	public static boolean checkIfRectAInsideRectB(GeomPoint2d ptAMin2d, GeomPoint2d ptAMax2d, GeomPoint2d ptBMin2d, GeomPoint2d ptBMax2d)
	{
		double xPtAMin = ptAMin2d.getX();
		double yPtAMin = ptAMin2d.getY();
		
		double xPtAMax = ptAMax2d.getX();
		double yPtAMax = ptAMax2d.getY();

		GeomPoint2d ptA0 = new GeomPoint2d(xPtAMin, yPtAMin);
		GeomPoint2d ptA1 = new GeomPoint2d(xPtAMax, yPtAMin);
		GeomPoint2d ptA2 = new GeomPoint2d(xPtAMax, yPtAMax);
		GeomPoint2d ptA3 = new GeomPoint2d(xPtAMin, yPtAMax);
		
		if( checkIfPointAInsideRectB(ptA0, ptBMin2d, ptBMax2d) )
			return true;

		if( checkIfPointAInsideRectB(ptA1, ptBMin2d, ptBMax2d) )
			return true;

		if( checkIfPointAInsideRectB(ptA2, ptBMin2d, ptBMax2d) )
			return true;

		if( checkIfPointAInsideRectB(ptA3, ptBMin2d, ptBMax2d) )
			return true;
		
		return false;
	}
	
	public static GeomDimension2d dimensionOf(GeomPoint2d ptI, GeomPoint2d ptF)
	{
		GeomDimension2d result = new GeomDimension2d(ptI, ptF);
		return result;
	}

	public static GeomDimension3d dimensionOf(GeomPoint3d ptI, GeomPoint3d ptF)
	{
		GeomDimension3d result = new GeomDimension3d(ptI, ptF);
		return result;
	}
	
	public static GeomPoint2d[] maxMinPointOfArray2d(ArrayList<GeomPoint2d> lsPts)
	{
		double xPtMin2d = Double.MAX_VALUE;
		double yPtMin2d = Double.MAX_VALUE;
		
		double xPtMax2d = - Double.MAX_VALUE;
		double yPtMax2d = - Double.MAX_VALUE;
			
		for(GeomPoint2d pt2d : lsPts) {
			double xPt2d = pt2d.getX();
			double yPt2d = pt2d.getY();
					
			if(xPt2d > xPtMax2d) {
				xPtMax2d = xPt2d;
			}

			if(xPt2d < xPtMin2d) {
				xPtMin2d = xPt2d;
			}
			
			if(yPt2d > yPtMax2d) {
				yPtMax2d = yPt2d;
			}
		
			if(xPt2d < xPtMin2d) {
				yPtMin2d = yPt2d;
			}
		}
			
		GeomPoint2d[] arr = new GeomPoint2d[2];
		arr[0] = new GeomPoint2d(xPtMin2d, yPtMin2d);
		arr[1] = new GeomPoint2d(xPtMax2d, yPtMax2d);

		return arr;
	}
	
	public static GeomPoint3d[] maxMinPointOfArray3d(ArrayList<GeomPoint3d> lsPts)
	{
		if(lsPts == null) {
			GeomPoint3d[] arr = new GeomPoint3d[2];
			arr[0] = new GeomPoint3d(0.0, 0.0, 0.0);
			arr[1] = new GeomPoint3d(0.0, 0.0, 0.0);
			return arr;			
		}
		
		double xPtMin3d = Double.MAX_VALUE;
		double yPtMin3d = Double.MAX_VALUE;
		double zPtMin3d = Double.MAX_VALUE;
		
		double xPtMax3d = - Double.MAX_VALUE;
		double yPtMax3d = - Double.MAX_VALUE;
		double zPtMax3d = - Double.MAX_VALUE;
			
		for(GeomPoint3d pt3d : lsPts) {
			double xPt3d = pt3d.getX();
			double yPt3d = pt3d.getY();
			double zPt3d = pt3d.getZ();
				
			//X
			if(xPt3d > xPtMax3d) {
				xPtMax3d = xPt3d;
			}

			if(xPt3d < xPtMin3d) {
				xPtMin3d = xPt3d;
			}
			
			//Y
			if(yPt3d > yPtMax3d) {
				yPtMax3d = yPt3d;
			}
		
			if(yPt3d < yPtMin3d) {
				yPtMin3d = yPt3d;
			}
			
			//Z
			if(zPt3d > zPtMax3d) {
				zPtMax3d = zPt3d;
			}
		
			if(zPt3d < zPtMin3d) {
				zPtMin3d = zPt3d;
			}
		}
			
		GeomPoint3d[] arr = new GeomPoint3d[2];
		arr[0] = new GeomPoint3d(xPtMin3d, yPtMin3d, zPtMin3d);
		arr[1] = new GeomPoint3d(xPtMax3d, yPtMax3d, zPtMax3d);

		return arr;
	}
	
	public static GeomPoint3d[] maxMinPointOfArray3d(int tagId, ArrayList<GeomPoint3d> lsPts)
	{
		double xPtMin3d = Double.MAX_VALUE;
		double yPtMin3d = Double.MAX_VALUE;
		double zPtMin3d = Double.MAX_VALUE;
		
		double xPtMax3d = - Double.MAX_VALUE;
		double yPtMax3d = - Double.MAX_VALUE;
		double zPtMax3d = - Double.MAX_VALUE;
			
		for(GeomPoint3d pt3d : lsPts) {
			double xPt3d = pt3d.getX();
			double yPt3d = pt3d.getY();
			double zPt3d = pt3d.getZ();
				
			//X
			if(xPt3d > xPtMax3d) {
				xPtMax3d = xPt3d;
			}

			if(xPt3d < xPtMin3d) {
				xPtMin3d = xPt3d;
			}
			
			//Y
			if(yPt3d > yPtMax3d) {
				yPtMax3d = yPt3d;
			}
		
			if(yPt3d < yPtMin3d) {
				yPtMin3d = yPt3d;
			}
			
			//Z
			if(zPt3d > zPtMax3d) {
				zPtMax3d = zPt3d;
			}
		
			if(zPt3d < zPtMin3d) {
				zPtMin3d = zPt3d;
			}
		}
			
		GeomPoint3d[] arr = new GeomPoint3d[2];
		arr[0] = new GeomPoint3d(tagId, xPtMin3d, yPtMin3d, zPtMin3d);
		arr[1] = new GeomPoint3d(tagId, xPtMax3d, yPtMax3d, zPtMax3d);

		return arr;
	}

	public static GeomPoint2d[] maxMinPointOf(GeomPoint2d ptI, GeomPoint2d ptF)
	{
		double xI = ptI.getX();
		double yI = ptI.getY();
				
		double xF = ptF.getX();
		double yF = ptF.getY();

		// MIN and MAX points
		//
		double xMin = Math.min(xI, xF);
		double yMin = Math.min(yI, yF);

		GeomPoint2d ptMin = new GeomPoint2d(xMin, yMin);
		
		double xMax = Math.max(xI, xF);
		double yMax = Math.max(yI, yF);

		GeomPoint2d ptMax = new GeomPoint2d(xMax, yMax);
		
		GeomPoint2d[] arrResult = new GeomPoint2d[2];
		arrResult[0] = ptMin;
		arrResult[1] = ptMax;
		
		return arrResult;
	}

	public static GeomPoint2d[] maxMinPointOf(GeomPoint2d pt0, GeomPoint2d pt1, GeomPoint2d pt2)
	{
		ArrayList<GeomPoint2d> lsPts = new ArrayList<GeomPoint2d>();
		lsPts.add(pt0);
		lsPts.add(pt1);
		lsPts.add(pt2);
		
		GeomPoint2d[] arrResult = GeomUtil.maxMinPointOfArray2d(lsPts);
		return arrResult;
	}

	public static GeomPoint2d[] maxMinPointOf(GeomPoint2d pt0, GeomPoint2d pt1, GeomPoint2d pt2, GeomPoint2d pt3)
	{
		ArrayList<GeomPoint2d> lsPts = new ArrayList<GeomPoint2d>();
		lsPts.add(pt0);
		lsPts.add(pt1);
		lsPts.add(pt2);
		lsPts.add(pt3);
		
		GeomPoint2d[] arrResult = GeomUtil.maxMinPointOfArray2d(lsPts);
		return arrResult;
	}

	public static GeomPoint3d[] maxMinPointOf(GeomPoint3d ptI, GeomPoint3d ptF)
	{
		double xI = ptI.getX();
		double yI = ptI.getY();
		double zI = ptI.getZ();
				
		double xF = ptF.getX();
		double yF = ptF.getY();
		double zF = ptF.getZ();

		// MIN and MAX points
		//
		double xMin = Math.min(xI, xF);
		double yMin = Math.min(yI, yF);
		double zMin = Math.min(zI, zF);

		GeomPoint3d ptMin = new GeomPoint3d(xMin, yMin, zMin);
		
		double xMax = Math.max(xI, xF);
		double yMax = Math.max(yI, yF);
		double zMax = Math.max(zI, zF);

		GeomPoint3d ptMax = new GeomPoint3d(xMax, yMax, zMax);
		
		GeomPoint3d[] arrResult = new GeomPoint3d[2];
		arrResult[0] = ptMin;
		arrResult[1] = ptMax;
		
		return arrResult;
	}

	public static GeomPoint3d[] maxMinPointOf(GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2)
	{
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();
		lsPts.add(pt0);
		lsPts.add(pt1);
		lsPts.add(pt2);
		
		GeomPoint3d[] arrResult = GeomUtil.maxMinPointOfArray3d(lsPts);
		return arrResult;
	}

	public static GeomPoint3d[] maxMinPointOf(GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();
		lsPts.add(pt0);
		lsPts.add(pt1);
		lsPts.add(pt2);
		lsPts.add(pt3);
		
		GeomPoint3d[] arrResult = GeomUtil.maxMinPointOfArray3d(lsPts);
		return arrResult;
	}

	public static GeomPoint3d[] maxMinPointOf(int tagId, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();
		lsPts.add(pt0);
		lsPts.add(pt1);
		lsPts.add(pt2);
		lsPts.add(pt3);
		
		GeomPoint3d[] arrResult = GeomUtil.maxMinPointOfArray3d(lsPts);
		return arrResult;
	}

	public static GeomPoint2d midPointOf(GeomPoint2d ptI, GeomPoint2d ptF)
	{
		double xI = ptI.getX();
		double yI = ptI.getY();
				
		double xF = ptF.getX();
		double yF = ptF.getY();
				
		double xMiddle = xI + (xF - xI) / 2.0;
		double yMiddle = yI + (yF - yI) / 2.0;
		
		GeomPoint2d ptMiddle = new GeomPoint2d(xMiddle, yMiddle);
		return ptMiddle;
	}

	public static GeomPoint2d midPointOf(int tagId, GeomPoint2d ptI, GeomPoint2d ptF)
	{
		double xI = ptI.getX();
		double yI = ptI.getY();
				
		double xF = ptF.getX();
		double yF = ptF.getY();
				
		double xMiddle = xI + (xF - xI) / 2.0;
		double yMiddle = yI + (yF - yI) / 2.0;
		
		GeomPoint2d ptMiddle = new GeomPoint2d(tagId, xMiddle, yMiddle);
		return ptMiddle;
	}

	public static GeomPoint3d midPointOf(GeomPoint3d ptI, GeomPoint3d ptF)
	{
		double xI = ptI.getX();
		double yI = ptI.getY();
		double zI = ptI.getZ();
				
		double xF = ptF.getX();
		double yF = ptF.getY();
		double zF = ptF.getZ();
				
		double xMiddle = xI + (xF - xI) / 2.0;
		double yMiddle = yI + (yF - yI) / 2.0;
		double zMiddle = zI + (zF - zI) / 2.0;
		
		GeomPoint3d ptMiddle = new GeomPoint3d(xMiddle, yMiddle, zMiddle);
		return ptMiddle;
	}

	public static GeomPoint3d midPointOf(int tagId, GeomPoint3d ptI, GeomPoint3d ptF)
	{
		double xI = ptI.getX();
		double yI = ptI.getY();
		double zI = ptI.getZ();
				
		double xF = ptF.getX();
		double yF = ptF.getY();
		double zF = ptF.getZ();
				
		double xMiddle = xI + (xF - xI) / 2.0;
		double yMiddle = yI + (yF - yI) / 2.0;
		double zMiddle = zI + (zF - zI) / 2.0;
		
		GeomPoint3d ptMiddle = new GeomPoint3d(tagId, xMiddle, yMiddle, zMiddle);
		return ptMiddle;
	}
	
	/* MIN/MAX/SUMOF/MEANOF DISTANTE */
	
	// MIN DISTANCE
	//
	public static double minDistOf2d(GeomPoint2d pt2d, ArrayList<GeomPoint2d> lsPts2d)
	{
		double minDist = Double.MAX_VALUE;
		for(GeomPoint2d ptVertex2d : lsPts2d) {
			double dist = pt2d.distTo(ptVertex2d);
			if(dist < minDist) {
				minDist = dist;
			}
		}
		return minDist;
	}
	
	public static double minDistOf3d(GeomPoint3d pt3d, ArrayList<GeomPoint3d> lsPts3d)
	{
		double minDist = Double.MAX_VALUE;
		for(GeomPoint3d ptVertex3d : lsPts3d) {
			double dist = pt3d.distTo(ptVertex3d);
			if(dist < minDist) {
				minDist = dist;
			}
		}
		return minDist;
	}
	
	// MAX DISTANCE
	//
	public static double maxDistOf2d(GeomPoint2d pt2d, ArrayList<GeomPoint2d> lsPts2d)
	{
		double maxDist = - Double.MAX_VALUE;
		for(GeomPoint2d ptVertex2d : lsPts2d) {
			double dist = pt2d.distTo(ptVertex2d);
			if(dist > maxDist) {
				maxDist = dist;
			}
		}
		return maxDist;
	}
	
	public static double maxDistOf3d(GeomPoint3d pt3d, ArrayList<GeomPoint3d> lsPts3d)
	{
		double maxDist = - Double.MAX_VALUE;
		for(GeomPoint3d ptVertex3d : lsPts3d) {
			double dist = pt3d.distTo(ptVertex3d);
			if(dist > maxDist) {
				maxDist = dist;
			}
		}
		return maxDist;
	}
	
	// SUMOF DISTANCE
	//
	public static double sumDistOf2d(GeomPoint2d pt2d, ArrayList<GeomPoint2d> lsPts2d)
	{
		double sumOfDist = 0.0;
		for(GeomPoint2d ptVertex2d : lsPts2d) {
			double dist = pt2d.distTo(ptVertex2d);
			sumOfDist += dist;
		}
		return sumOfDist;
	}
	
	public static double sumDistOf3d(GeomPoint3d pt3d, ArrayList<GeomPoint3d> lsPts3d)
	{
		double sumOfDist = 0.0;
		for(GeomPoint3d ptVertex3d : lsPts3d) {
			double dist = pt3d.distTo(ptVertex3d);
			sumOfDist += dist;
		}
		return sumOfDist;
	}
		
	// MEANOF DISTANCE
	//
	public static double meanDistOf2d(GeomPoint2d pt2d, ArrayList<GeomPoint2d> lsPts2d)
	{
		double sumOfDist = GeomUtil.sumDistOf2d(pt2d, lsPts2d);
		int n = lsPts2d.size();

		double meanDist = sumOfDist / n;
		return meanDist;
	}
	
	public static double meanDistOf3d(GeomPoint3d pt3d, ArrayList<GeomPoint3d> lsPts3d)
	{
		double sumOfDist = GeomUtil.sumDistOf3d(pt3d, lsPts3d);
		int n = lsPts3d.size();

		double meanDist = sumOfDist / n;
		return meanDist;
	}

	/* CENTROID */
	
	public static GeomPoint2d centroidOf2d(ArrayList<GeomPoint2d> lsPts)
	{
		double xCentroid = 0.0;
		double yCentroid = 0.0;

		double n = lsPts.size(); 
		
		for(GeomPoint2d ptVertex : lsPts) {
			xCentroid = xCentroid + ptVertex.getX();
			yCentroid = yCentroid + ptVertex.getY();
		}
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		
		GeomPoint2d ptCentroid = new GeomPoint2d(xCentroid, yCentroid);
		return ptCentroid;
	}
	
	public static GeomPoint2d centroidOf2d(int tagId, ArrayList<GeomPoint2d> lsPts)
	{
		double xCentroid = 0.0;
		double yCentroid = 0.0;

		double n = lsPts.size(); 
		
		for(GeomPoint2d ptVertex : lsPts) {
			xCentroid = xCentroid + ptVertex.getX();
			yCentroid = yCentroid + ptVertex.getY();
		}
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		
		GeomPoint2d ptCentroid = new GeomPoint2d(tagId, xCentroid, yCentroid);
		return ptCentroid;
	}

	public static GeomPoint3d centroidOf3d(ArrayList<GeomPoint3d> lsPts)
	{
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = lsPts.size(); 
		
		for(GeomPoint3d ptVertex : lsPts) {
			xCentroid = xCentroid + ptVertex.getX();
			yCentroid = yCentroid + ptVertex.getY();
			zCentroid = zCentroid + ptVertex.getZ();
		}
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		GeomPoint3d ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		return ptCentroid;
	}

	public static GeomPoint3d centroidOfPolygon3d(ArrayList<GeomPoint3d> lsPts)
	{
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = lsPts.size() - 1; 
		
		for(int i = 0; i < n; i++) {
			GeomPoint3d ptVertex = lsPts.get(i);
			
			xCentroid = xCentroid + ptVertex.getX();
			yCentroid = yCentroid + ptVertex.getY();
			zCentroid = zCentroid + ptVertex.getZ();
		}
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		GeomPoint3d ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		return ptCentroid;
	}

	public static GeomPoint3d centroidOf3d(int tagId, String tagName, ArrayList<GeomPoint3d> lsPts)
	{
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = lsPts.size(); 
		
		for(GeomPoint3d ptVertex : lsPts) {
			xCentroid = xCentroid + ptVertex.getX();
			yCentroid = yCentroid + ptVertex.getY();
			zCentroid = zCentroid + ptVertex.getZ();
		}
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		GeomPoint3d ptCentroid = new GeomPoint3d(tagId, tagName, xCentroid, yCentroid, zCentroid);
		return ptCentroid;
	}

	public static GeomPoint3d centroidOf3d(int tagId, ArrayList<GeomPoint3d> lsPts)
	{
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = lsPts.size(); 
		
		for(GeomPoint3d ptVertex : lsPts) {
			xCentroid = xCentroid + ptVertex.getX();
			yCentroid = yCentroid + ptVertex.getY();
			zCentroid = zCentroid + ptVertex.getZ();
		}
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		GeomPoint3d ptCentroid = new GeomPoint3d(tagId, xCentroid, yCentroid, zCentroid);
		return ptCentroid;
	}

	//VECTOR_CONVERSIONS
	
	public static GeomVector2d vector3dToVector2d(GeomVector3d v3d)
	{
		double xI2d = v3d.getXI();
		double yI2d = v3d.getYI();
		
		double xF2d = v3d.getXF();
		double yF2d = v3d.getYF();

		GeomVector2d v2d = new GeomVector2d(xI2d, yI2d, xF2d, yF2d);
		return v2d;
	}	
	
	public static GeomVector3d vector2dToVector3d(GeomVector3d v2d)
	{
		double xI3d = v2d.getXI();
		double yI3d = v2d.getYI();
		
		double xF3d = v2d.getXF();
		double yF3d = v2d.getYF();

		GeomVector3d v3d = new GeomVector3d(xI3d, yI3d, 0.0, xF3d, yF3d, 0.0);
		return v3d;
	}	
	
	/* TRANSFORM */
	
	public static GeomVector3d transformTo(GeomPoint3d ptRef, GeomVector3d v3d, GeomVector3d vDir3d, double dist, double stepAngleXRad, double stepAngleYRad, double stepAngleZRad)
	{
		GeomPoint3d ptI3d = new GeomPoint3d(v3d.getXI(), v3d.getYI(), v3d.getZI());
		GeomPoint3d ptF3d = new GeomPoint3d(v3d.getXF(), v3d.getYF(), v3d.getZF());
		
		GeomPoint3d ptIProj3d = GeomUtil.transformTo(ptRef, ptI3d, vDir3d, dist, stepAngleXRad, stepAngleYRad, stepAngleZRad);
		GeomPoint3d ptFProj3d = GeomUtil.transformTo(ptRef, ptF3d, vDir3d, dist, stepAngleXRad, stepAngleYRad, stepAngleZRad);
		
		GeomVector3d vResult = new GeomVector3d(ptIProj3d, ptFProj3d); 
		return vResult;
	}
	
	public static GeomPoint3d transformTo(GeomPoint3d ptRef, GeomPoint3d pt3d, GeomVector3d vDir3d, double dist, double stepAngleXRad, double stepAngleYRad, double stepAngleZRad)
	{
		GeomPoint3d ptResult = new GeomPoint3d(pt3d); 

		//ptResult = ptResult.otherRotatePlanYZRad(ptRef, stepAngleXRad);
		//ptResult = ptResult.otherRotatePlanZXRad(ptRef, stepAngleYRad);
		//ptResult = ptResult.otherRotatePlanXYRad(ptRef, stepAngleZRad);		

		//if(dist > AppDefs.MATHPREC_MIN)
		//	ptResult = ptResult.otherMoveTo(vDir3d, dist);
		return ptResult;
	}
	
	/* AXIS */

	public static GeomVector2d axisX2d()
	{
		GeomVector2d axisX = new GeomVector2d(1.0, 0.0);
		return axisX;
	}

	public static GeomVector3d axisX3d()
	{
		GeomVector3d axisX = new GeomVector3d(1.0, 0.0, 0.0);
		return axisX;
	}

	public static GeomVector2d axisY2d()
	{
		GeomVector2d axisX = new GeomVector2d(0.0, 1.0);
		return axisX;
	}

	public static GeomVector3d axisY3d()
	{
		GeomVector3d axisX = new GeomVector3d(0.0, 1.0, 0.0);
		return axisX;
	}

	public static GeomVector3d axisZ3d()
	{
		GeomVector3d axisX = new GeomVector3d(0.0, 0.0, 1.0);
		return axisX;
	}
	
	/* TEST_OSNAPMODE */
	
	public static boolean testOsnapmode(int osnapmode, int osnap)
	{
		int _testOsnapmode = (osnapmode & osnap);
		if(_testOsnapmode == osnap)
			return true;
		return false;
	}
	
	/* OSNAP_2D */
	
	public static GeomPoint3d osnap3d(ICadViewBase v, int osnapmode, GeomPoint2d pt2dMcs, ArrayList<GeomPoint3d> lsPts, Graphics g)
	{
		if(osnapmode == AppDefs.OSNAPMODE_NONE) return null;
		
		GeomPoint2d ptResult = null; 
		
		double boxSzMcs = v.fromScrToMcs(AppDefs.OSNAPBOX_SIZE);

		for(GeomPoint3d pt3d : lsPts) {
			GeomPoint2d pt2d = new GeomPoint2d(pt3d);
			double dist = pt2dMcs.distTo(pt2d);

			if(dist <= boxSzMcs) {
				if(g != null) {
					if( GeomUtil.testOsnapmode(osnapmode, AppDefs.OSNAPMODE_NODEPOINT) ) {
						GeomUtil.drawOsnapNode2d(v, pt2d, g);
					}
					else if( GeomUtil.testOsnapmode(osnapmode, AppDefs.OSNAPMODE_ENDPOINT) ) {
							GeomUtil.drawOsnapEndpoint2d(v, pt2d, g);
					}
					else if( GeomUtil.testOsnapmode(osnapmode, AppDefs.OSNAPMODE_MIDDLE) ) {
						GeomUtil.drawOsnapMiddle2d(v, pt2d, g);
					}
					else if( GeomUtil.testOsnapmode(osnapmode, AppDefs.OSNAPMODE_CENTER) ) {
						GeomUtil.drawOsnapCenter2d(v, pt2d, g);
					}
					else if( GeomUtil.testOsnapmode(osnapmode, AppDefs.OSNAPMODE_QUADRANT) ) {
						GeomUtil.drawOsnapQuadrant2d(v, pt2d, g);
					}
					else if( GeomUtil.testOsnapmode(osnapmode, AppDefs.OSNAPMODE_INTERPOINT) ) {
						GeomUtil.drawOsnapInterpoint2d(v, pt2d, g);
					}
				}
				return pt3d;
			}
		}
		return null;
	}
	
	/* DRAWOSNAPxxx2d */
	
	public static void drawOsnapNode2d(ICadViewBase v, GeomPoint2d pt2dMcs, Graphics g)
	{
		double boxSzScr = AppDefs.OSNAPBOX_SIZE;
		
		double boxSzMcs = v.fromScrToMcs(boxSzScr);		
		double hBoxSzMcs = boxSzMcs / 2.0;
		
		double xMinMcs = pt2dMcs.getX() - hBoxSzMcs;
		double yMinMcs = pt2dMcs.getY() + hBoxSzMcs;
		
		GeomPoint2d ptMin2dMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMin2dScr = v.fromMcsToScr(ptMin2dMcs);
		
		GeomPoint2d ptMin2dVideo = v.fromScrToVideo(ptMin2dScr);

		double xMinVideo = ptMin2dVideo.getX();
		double yMinVideo = ptMin2dVideo.getY();
		
		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIPOBJECTCOLOR_SELECTMODE);
		
		g.drawOval((int)xMinVideo, (int)yMinVideo, (int)boxSzScr, (int)boxSzScr);
		
		g.setColor(oldcol);
	}
	
	public static void drawOsnapEndpoint2d(ICadViewBase v, GeomPoint2d pt2dMcs, Graphics g)
	{
		double boxSzScr = AppDefs.OSNAPBOX_SIZE;
		
		double boxSzMcs = v.fromScrToMcs(boxSzScr);		
		double hBoxSzMcs = boxSzMcs / 2.0;
		
		double xMinMcs = pt2dMcs.getX() - hBoxSzMcs;
		double yMinMcs = pt2dMcs.getY() + hBoxSzMcs;
		
		GeomPoint2d ptMin2dMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMin2dScr = v.fromMcsToScr(ptMin2dMcs);
		
		GeomPoint2d ptMin2dVideo = v.fromScrToVideo(ptMin2dScr);

		double xMinVideo = ptMin2dVideo.getX();
		double yMinVideo = ptMin2dVideo.getY();
		
		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIPOBJECTCOLOR_SELECTMODE);
		
		g.drawRect((int)xMinVideo, (int)yMinVideo, (int)boxSzScr, (int)boxSzScr);
		
		g.setColor(oldcol);
	}
	
	public static void drawOsnapMiddle2d(ICadViewBase v, GeomPoint2d pt2dMcs, Graphics g)
	{
		double boxSzScr = AppDefs.OSNAPBOX_SIZE;
		
		double boxSzMcs = v.fromScrToMcs(boxSzScr);		
		double hBoxSzMcs = boxSzMcs / 2.0;
		
		double xMinMcs = pt2dMcs.getX() - hBoxSzMcs;
		double yMinMcs = pt2dMcs.getY() + hBoxSzMcs;
		
		GeomPoint2d ptMin2dMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMin2dScr = v.fromMcsToScr(ptMin2dMcs);
		
		GeomPoint2d ptMin2dVideo = v.fromScrToVideo(ptMin2dScr);

		double xMinVideo = ptMin2dVideo.getX();
		double yMinVideo = ptMin2dVideo.getY();

		double xMaxVideo = xMinVideo + boxSzScr;
		double yMaxVideo = yMinVideo + boxSzScr;

		double xMidVideo = xMinVideo + (xMaxVideo - xMinVideo) / 2.0;
		
		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIPOBJECTCOLOR_SELECTMODE);
		
		g.drawLine((int)xMinVideo, (int)yMaxVideo, (int)xMaxVideo, (int)yMaxVideo);
		g.drawLine((int)xMinVideo, (int)yMaxVideo, (int)xMidVideo, (int)yMinVideo);
		g.drawLine((int)xMaxVideo, (int)yMaxVideo, (int)xMidVideo, (int)yMinVideo);
		
		g.setColor(oldcol);
	}
	
	public static void drawOsnapCenter2d(ICadViewBase v, GeomPoint2d pt2dMcs, Graphics g)
	{
		double boxSzScr = AppDefs.OSNAPBOX_SIZE;
		
		double boxSzMcs = v.fromScrToMcs(boxSzScr);		
		double hBoxSzMcs = boxSzMcs / 2.0;
		
		double xMinMcs = pt2dMcs.getX() - hBoxSzMcs;
		double yMinMcs = pt2dMcs.getY() + hBoxSzMcs;
		
		GeomPoint2d ptMin2dMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMin2dScr = v.fromMcsToScr(ptMin2dMcs);
		
		GeomPoint2d ptMin2dVideo = v.fromScrToVideo(ptMin2dScr);

		double xMinVideo = ptMin2dVideo.getX();
		double yMinVideo = ptMin2dVideo.getY();

		double xMaxVideo = xMinVideo + boxSzScr;
		double yMaxVideo = yMinVideo + boxSzScr;

		double xMidVideo = xMinVideo + (xMaxVideo - xMinVideo) / 2.0;
		double yMidVideo = yMinVideo + (yMaxVideo - yMinVideo) / 2.0;
		
		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIPOBJECTCOLOR_SELECTMODE);
		
		g.drawLine((int)xMidVideo, (int)yMinVideo, (int)xMidVideo, (int)yMaxVideo);
		g.drawLine((int)xMinVideo, (int)yMidVideo, (int)xMaxVideo, (int)yMidVideo);
		
		g.setColor(oldcol);
	}

	public static void drawOsnapQuadrant2d(ICadViewBase v, GeomPoint2d pt2dMcs, Graphics g)
	{
		double boxSzScr = AppDefs.OSNAPBOX_SIZE;
		
		double boxSzMcs = v.fromScrToMcs(boxSzScr);		
		double hBoxSzMcs = boxSzMcs / 2.0;
		
		double xMinMcs = pt2dMcs.getX() - hBoxSzMcs;
		double yMinMcs = pt2dMcs.getY() + hBoxSzMcs;
		
		GeomPoint2d ptMin2dMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMin2dScr = v.fromMcsToScr(ptMin2dMcs);
		
		GeomPoint2d ptMin2dVideo = v.fromScrToVideo(ptMin2dScr);

		double xMinVideo = ptMin2dVideo.getX();
		double yMinVideo = ptMin2dVideo.getY();

		double xMaxVideo = xMinVideo + boxSzScr;
		double yMaxVideo = yMinVideo + boxSzScr;

		double xMidVideo = xMinVideo + (xMaxVideo - xMinVideo) / 2.0;
		double yMidVideo = yMinVideo + (yMaxVideo - yMinVideo) / 2.0;
		
		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIPOBJECTCOLOR_SELECTMODE);
		
		g.drawLine((int)xMidVideo, (int)yMinVideo, (int)xMaxVideo, (int)yMidVideo);
		g.drawLine((int)xMaxVideo, (int)yMidVideo, (int)xMidVideo, (int)yMaxVideo);
		g.drawLine((int)xMidVideo, (int)yMaxVideo, (int)xMinVideo, (int)yMidVideo);
		g.drawLine((int)xMinVideo, (int)yMidVideo, (int)xMidVideo, (int)yMinVideo);
		
		g.setColor(oldcol);		
	}

	public static void drawOsnapInterpoint2d(ICadViewBase v, GeomPoint2d pt2dMcs, Graphics g)
	{
		double boxSzScr = AppDefs.OSNAPBOX_SIZE;
		
		double boxSzMcs = v.fromScrToMcs(boxSzScr);		
		double hBoxSzMcs = boxSzMcs / 2.0;
		
		double xMinMcs = pt2dMcs.getX() - hBoxSzMcs;
		double yMinMcs = pt2dMcs.getY() - hBoxSzMcs;
		
		double xMaxMcs = pt2dMcs.getX() + hBoxSzMcs;
		double yMaxMcs = pt2dMcs.getY() + hBoxSzMcs;
		
		GeomPoint2d ptMin2dMcs = new GeomPoint2d(xMinMcs, yMinMcs);
		GeomPoint2d ptMax2dMcs = new GeomPoint2d(xMaxMcs, yMaxMcs);
		
		GeomPoint2d ptMin2dScr = v.fromMcsToScr(ptMin2dMcs);
		GeomPoint2d ptMax2dScr = v.fromMcsToScr(ptMax2dMcs);

		GeomPoint2d ptMin2dVideo = v.fromScrToVideo(ptMin2dScr);
		GeomPoint2d ptMax2dVideo = v.fromScrToVideo(ptMax2dScr);

		double xMinVideo = ptMin2dVideo.getX();
		double yMinVideo = ptMin2dVideo.getY();

		double xMaxVideo = ptMax2dVideo.getX();
		double yMaxVideo = ptMax2dVideo.getY();
		
		Color oldcol = GeomUtil.setColor(g, AppDefs.GRIPOBJECTCOLOR_SELECTMODE);
		
		g.drawLine((int)xMinVideo, (int)yMinVideo, (int)xMaxVideo, (int)yMaxVideo);
		g.drawLine((int)xMinVideo, (int)yMaxVideo, (int)xMaxVideo, (int)yMinVideo);
		
		g.setColor(oldcol);		
	}
	
	/* POINT TRANSLATION, SCALE AND ROTATION */

	//POINT TRANSLATION
	//
	public static GeomPoint2d translation(GeomPoint2d pt2dMcs, GeomVector2d v2dMcs)
	{
		GeomPoint2d newPt2dMcs = pt2dMcs.otherMoveTo(v2dMcs, 1.0);
		return newPt2dMcs;
	}	
	
	public static GeomPoint3d translation(GeomPoint3d pt3dMcs, GeomVector3d v3dMcs)
	{
		GeomPoint3d newPt3dMcs = pt3dMcs.otherMoveTo(v3dMcs, 1.0);
		return newPt3dMcs;
	}	
	
	//POINT SCALE
	//
	public static GeomPoint2d scale(GeomPoint2d pt2dMcs, GeomVector2d v2dMcs, double dist)
	{
		GeomPoint2d newPt2dMcs = pt2dMcs.otherMoveTo(v2dMcs, dist);
		return newPt2dMcs;
	}	
	
	public static GeomPoint3d scale(GeomPoint3d pt3dMcs, GeomVector3d v3dMcs, double dist)
	{
		GeomPoint3d newPt3dMcs = pt3dMcs.otherMoveTo(v3dMcs, dist);
		return newPt3dMcs;
	}	
		
	//POINT ROTATION
	//
//	public static GeomPoint2d rotation(GeomPoint2d pt2dMcs, GeomVector2d vRef2dMcs)
//	{
//		GeomPoint2d ptRef2dMcs = vRef2dMcs.otherProjectFrom(pt2dMcs);
//		
//    	GeomVector2d axisX = GeomUtil.axisX2d();
//
//    	double angleXRad = axisX.angleTo(vRef2dMcs);
//
//    	double xI2dMcs = (pt2dMcs.getX() - ptRef2dMcs.getX());
//		double yI2dMcs = (pt2dMcs.getY() - ptRef2dMcs.getY());
//		
//		double xF2dMcs = ptRef2dMcs.getX() + (xI2dMcs * Math.cos(angleXRad)) - (yI2dMcs * Math.sin(angleXRad));
//		double yF2dMcs = ptRef2dMcs.getY() + (xI2dMcs * Math.sin(angleXRad)) + (yI2dMcs * Math.cos(angleXRad));
//		
//		GeomPoint2d newPt2dMcs = new GeomPoint2d(xF2dMcs, yF2dMcs);
//		return newPt2dMcs;
//	}	

	public static GeomPoint3d rotationXY(GeomPoint3d pt3dMcs, GeomVector3d vRef3dMcs)
	{
		if(vRef3dMcs == null) return pt3dMcs;

		GeomPoint3d ptBase3d = new GeomPoint3d(
			vRef3dMcs.getXI(), 
			vRef3dMcs.getYI(), 
			vRef3dMcs.getZI());

		GeomPoint3d ptTop3d = new GeomPoint3d(
			vRef3dMcs.getXI(), 
			vRef3dMcs.getYI(), 
			vRef3dMcs.getZI() + 1.0);

		GeomVector3d uPtBaseToPtTop3d = new GeomVector3d(ptBase3d, ptTop3d);

		GeomVector3d uRef3dMcs = vRef3dMcs.otherUnit();
		double sign = - Math.signum( uPtBaseToPtTop3d.dotProd(uRef3dMcs) );
		
		double zH = pt3dMcs.getZ() - ptBase3d.getZ();
		
		GeomPoint3d pt3d0 = ptBase3d.otherMoveTo(uPtBaseToPtTop3d, zH);

		GeomPoint3d pt3d1 = ptBase3d.otherMoveTo(uRef3dMcs, zH);
		GeomPoint2d pt2d1 = new GeomPoint2d(pt3d1);
		
		GeomVector3d vPt3d0ToPt3d = new GeomVector3d(pt3d0, pt3dMcs); 				
		GeomVector2d vPt2d0ToPt2d = new GeomVector2d(vPt3d0ToPt3d);
		double d = vPt2d0ToPt2d.mod();
		
		GeomPoint2d pt2d2 = pt2d1.otherMoveTo(vPt2d0ToPt2d, d);
		
		double angA = uPtBaseToPtTop3d.angleTo(uRef3dMcs);
		
		double dXY = d * Math.cos(angA);
		double dZ = sign * d * Math.sin(angA);
		
		GeomPoint2d pt2dF1 = pt2d1.otherMoveTo(vPt2d0ToPt2d, dXY);
		
		double zF = pt3d1.getZ() + dZ;
		GeomPoint3d pt2dF = new GeomPoint3d(pt2dF1.getX(), pt2dF1.getY(), zF);
		return pt2dF;
	}	

	public static GeomPoint3d rotationXY_NEW(GeomPoint3d pt3dMcs, GeomVector3d vRef3dMcs)
	{
		if(vRef3dMcs == null) return pt3dMcs;

		GeomVector3d uRef3dMcs = vRef3dMcs.otherUnit();
		double dZ = pt3dMcs.getZ() - vRef3dMcs.getZI();
		
		GeomPoint3d ptBase3d = new GeomPoint3d(
				vRef3dMcs.getXI(), 
				vRef3dMcs.getYI(), 
				vRef3dMcs.getZI());

		GeomPoint3d ptTop3d = new GeomPoint3d(
			vRef3dMcs.getXI(), 
			vRef3dMcs.getYI(), 
			vRef3dMcs.getZF());

		GeomVector3d vBaseToPtTop3d = new GeomVector3d(ptBase3d, ptTop3d);
		double dZ_cosang = vBaseToPtTop3d.getZOrig();
		double dD_cosang = vBaseToPtTop3d.mod();
		
		double cosang = 0.0;
		if(dD_cosang >= AppDefs.MATHPREC_MIN)
			cosang = dZ_cosang / dD_cosang; 

		double angleRad = Math.acos(cosang);
		double sinang = Math.sin(angleRad);
		
		GeomPoint2d ptBase2d = new GeomPoint2d(ptBase3d);
		GeomPoint2d pt2d = new GeomPoint2d(pt3dMcs);
		
		GeomVector2d vPtBaseToPt2d = new GeomVector2d(ptBase2d, pt2d); 
		double dXY = vPtBaseToPt2d.mod();

		double dXY_1 = dXY * cosang;
		double dZ_1 = dXY * sinang;			
			
		GeomPoint3d ptRef3d = ptBase3d.otherMoveTo(vRef3dMcs, dZ); 
		double dZResult = dZ - dZ_1;
		
		GeomPoint2d ptRef2d = new GeomPoint2d(ptRef3d); 
		GeomPoint2d ptResult2d = ptRef2d.otherMoveTo(vPtBaseToPt2d, dXY_1); 
				
		GeomPoint3d ptResult3d = new GeomPoint3d(
			ptResult2d.getX(),
			ptResult2d.getY(),
			dZResult); 		
		return ptResult3d;
	}	

	public static GeomPoint3d rotationXY_OLD(GeomPoint3d pt3dMcs, GeomVector3d vRef3dMcs)
	{
		if(vRef3dMcs == null) return pt3dMcs;
		
		GeomPoint3d ptBase3dMcs = new GeomPoint3d(vRef3dMcs.getXI(), vRef3dMcs.getYI(), vRef3dMcs.getZI());

		GeomVector3d zDir3dMcs = new GeomVector3d(ptBase3dMcs.getX(), ptBase3dMcs.getY(), ptBase3dMcs.getZ(), ptBase3dMcs.getX(), ptBase3dMcs.getY(), ptBase3dMcs.getZ() + 1.0);
    	double angleZRad = zDir3dMcs.angleTo(vRef3dMcs);

    	GeomVector3d vPtBaseToPt3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs); 
    	double d = zDir3dMcs.dotProd( vPtBaseToPt3dMcs );
    	
    	GeomPoint3d ptRef3dMcs = ptBase3dMcs.otherMoveTo(zDir3dMcs, d);
    	
    	GeomVector3d vPtRefToPt3dMcs = new GeomVector3d(ptRef3dMcs, pt3dMcs); 

    	double dZ = vPtRefToPt3dMcs.getZOrig();
    	if(dZ > AppDefs.MATHPREC_MIN) return pt3dMcs;
    	
    	double dX = vPtRefToPt3dMcs.getXOrig();
    	double dY = vPtRefToPt3dMcs.getYOrig();
    	
    	double dXY = Math.sqrt( (dX * dX) + (dY * dY) ); 
    	if(dXY < AppDefs.MATHPREC_MIN) return pt3dMcs;

    	GeomPoint3d ptNewRef3dMcs = ptBase3dMcs.otherMoveTo(vRef3dMcs, d);

    	double xyPtNew = dXY * Math.cos(angleZRad);
    	double zPtNew = ptNewRef3dMcs.getZ() - (dXY * Math.sin(angleZRad)); 
    	
    	double xPtNew = ptNewRef3dMcs.getX() + xyPtNew * (dX / dXY);
    	double yPtNew = ptNewRef3dMcs.getY() + xyPtNew * (dY / dXY);

		GeomPoint3d ptNew3dMcs = new GeomPoint3d(xPtNew, yPtNew, zPtNew);
		return ptNew3dMcs;
	}	
	
//	public static GeomPoint3d rotation3d(GeomPoint3d pt3dMcs, GeomVector3d vRef3dMcs, double angleXRad)
//	{
//		GeomPoint3d ptRef3dMcs = vRef3dMcs.otherProjectFrom(pt3dMcs);
//
//		GeomVector3d axisZ = GeomUtil.axisZ3d();
//    	double angleZRad = axisZ.angleTo(vRef3dMcs);
//		
//		double xI2dMcs = (pt3dMcs.getX() - ptRef3dMcs.getX());
//		double yI2dMcs = (pt3dMcs.getY() - ptRef3dMcs.getY());
//		double zI2dMcs = (pt3dMcs.getZ() - ptRef3dMcs.getZ());
//		
//		double xyI2dMcs = Math.sqrt( (xI2dMcs * xI2dMcs) + (yI2dMcs * yI2dMcs) );
//
//		double zF2dMcs = ptRef3dMcs.getZ() + (zI2dMcs * Math.cos(angleZRad)) - (xyI2dMcs * Math.sin(angleZRad));
//		double xyF2dMcs = xyI2dMcs + (zI2dMcs * Math.sin(angleZRad)) + (xyI2dMcs * Math.cos(angleZRad));
//		
//		double dL = xI2dMcs;
//		double dH = yI2dMcs;
//
//		double d = Math.sqrt((dL * dL) + (dH * dH));
//		
//		double xF2dMcs = ptRef3dMcs.getX() + (xyF2dMcs * (dL / d));  
//		double yF2dMcs = ptRef3dMcs.getY() + (xyF2dMcs * (dH / d));  
//		
//		GeomPoint3d newPt3dMcs = new GeomPoint3d(xF2dMcs, yF2dMcs, zF2dMcs);
//		return newPt3dMcs;
//	}	

	/* FILTER_BY_OSNAP_TYPE */
	
	public static ArrayList<GeomPoint3d> filterByTag3d(int tagId, ArrayList<GeomPoint3d> lsPts)
	{
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		
		for(GeomPoint3d pt3d : lsPts) {
			if(tagId == pt3d.getTagId())
				lsResult.add( new GeomPoint3d(pt3d) );
		}
		return lsResult;
	}	
	
	public static ArrayList<GeomPoint3d> filterByTag3d(String tagName, ArrayList<GeomPoint3d> lsPts)
	{
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		
		for(GeomPoint3d pt3d : lsPts) {
			if( tagName.compareTo(pt3d.getTagName()) == 0 )
				lsResult.add( new GeomPoint3d(pt3d) );
		}
		return lsResult;
	}	
	
	public static ArrayList<GeomPoint2d> filterByTag2d(int tagId, ArrayList<GeomPoint2d> lsPts)
	{
		ArrayList<GeomPoint2d> lsResult = new ArrayList<GeomPoint2d>();
		
		for(GeomPoint2d pt2d : lsPts) {
			if(tagId == pt2d.getTagId())
				lsResult.add( new GeomPoint2d(pt2d) );
		}
		return lsResult;
	}	
	
	public static ArrayList<GeomPoint2d> filterByTag2d(String tagName, ArrayList<GeomPoint2d> lsPts)
	{
		ArrayList<GeomPoint2d> lsResult = new ArrayList<GeomPoint2d>();
		
		for(GeomPoint2d pt2d : lsPts) {
			if( tagName.compareTo(pt2d.getTagName()) == 0 )
				lsResult.add( new GeomPoint2d(pt2d) );
		}
		return lsResult;
	}	

	/* INSIDE_RECT */

	public static boolean isInsideRect(GeomPoint2d ptRef2d, GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		boolean bResult = false;
		
		//RECT-LIMITS
		//
		double xMin = Math.min(ptI2d.getX(), ptF2d.getX()) - AppDefs.MATHPREC_MIN;
		double yMin = Math.min(ptI2d.getY(), ptF2d.getY()) - AppDefs.MATHPREC_MIN;

		double xMax = Math.max(ptI2d.getX(), ptF2d.getX()) + AppDefs.MATHPREC_MIN;
		double yMax = Math.max(ptI2d.getY(), ptF2d.getY()) + AppDefs.MATHPREC_MIN;
		
		//POINT-COORDS
		//
		double xRef = ptRef2d.getX();
		double yRef = ptRef2d.getY();
		
		if( ( (xRef >= xMin) && (xRef <= xMax) ) && 
			( (yRef >= yMin) && (yRef <= yMax) ) ) 
		{
			bResult = true;
		}
		return bResult;
	}
	
	/* PERP_INTERSECTION */

	public static GeomPoint2d perpIntersectionOf(GeomPoint2d ptRef2d, GeomPoint2d ptI2d, GeomPoint2d ptF2d, boolean bOnlyRealIntersec)
	{
		GeomVector2d vIF = new GeomVector2d(ptI2d, ptF2d);
		GeomVector2d uIF = vIF.otherUnit();

		GeomVector2d vPtIToPtRef = new GeomVector2d(ptI2d, ptRef2d);
		double d = uIF.dotProd(vPtIToPtRef);
		
		GeomPoint2d ptInt2d = ptI2d.otherMoveTo(uIF, d);
		if( bOnlyRealIntersec ) {
			boolean bInsideRect = GeomUtil.isInsideRect(ptInt2d, ptI2d, ptF2d);
			if( !bInsideRect ) {
				ptInt2d = null;
			}
		}
		return ptInt2d;
	}

	/* DIR_ALL_INTERSECTION */

	public static GeomPoint2d dirAllIntersectionOf(GeomPoint2d ptI1, GeomPoint2d ptF1, GeomPoint2d ptI2, GeomPoint2d ptF2, boolean bOnlyRealIntersec)
	{
		//INTERSECTION_OF: PTI1-TO-PTF1 with PTI2-TO-PTF2 
		//
		GeomPoint2d ptIntersec2d = GeomUtil.dirIntersectionOf( ptI1, ptF1, ptI2, ptF2, false );
		if(ptIntersec2d == null) {
			//INTERSECTION_OF: PTI1-TO-PTF1 with PTF2-TO-PTI2 
			//
			ptIntersec2d = GeomUtil.dirIntersectionOf( ptI1, ptF1, ptF2, ptI2, false );
			if(ptIntersec2d == null) {
				//INTERSECTION_OF: PTF1-TO-PTI1 with PTI2-TO-PTF2 
				//
				ptIntersec2d = GeomUtil.dirIntersectionOf( ptF1, ptI1, ptI2, ptF2, false );
				if(ptIntersec2d == null) {
					//INTERSECTION_OF: PTF1-TO-PTI1 with PTF2-TO-PTI2 
					//
					ptIntersec2d = GeomUtil.dirIntersectionOf( ptF1, ptI1, ptF2, ptI2, false );
				}			
			}			
		}

		if( bOnlyRealIntersec ) {
			if(ptIntersec2d != null) {
				boolean bInsideRect = GeomUtil.isInsideRect(ptIntersec2d, ptI2, ptF2);
				if( !bInsideRect ) {
					ptIntersec2d = null;
				}
			}
		}
		return ptIntersec2d;
	}

	/* DIR_INTERSECTION */

	public static GeomPoint3d dirIntersectionOf(GeomPoint3d ptRef, GeomPoint3d ptDir, GeomPoint3d ptI1, GeomPoint3d ptF1, boolean bOnlyRealIntersec)
	{
		//LINE-REF
		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef );
		GeomPoint2d ptDir2d = new GeomPoint2d( ptDir );

		//LINE-1
		GeomPoint2d ptI2d = new GeomPoint2d( ptI1 );
		GeomPoint2d ptF2d = new GeomPoint2d( ptF1 );
		
		GeomPoint2d ptIntersect2d = dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, bOnlyRealIntersec);
		if(ptIntersect2d == null) return null;
		
		GeomPoint3d ptIntersect = new GeomPoint3d( ptIntersect2d.getX(), ptIntersect2d.getY(), ptRef.getZ() );
		return ptIntersect;
	}
	
	public static GeomPoint2d dirIntersectionOf(GeomPoint2d ptRef, GeomPoint2d ptDir, GeomPoint2d ptI1, GeomPoint2d ptF1, boolean bOnlyRealIntersec)
	{
		GeomPoint2d ptPerp = GeomUtil.perpIntersectionOf(ptRef, ptI1, ptF1, false);

		GeomVector2d vIF1 = new GeomVector2d(ptI1, ptF1);		
		GeomVector2d uIF1 = vIF1.otherUnit();		
		
		GeomVector2d vPtRefToPtPerp = new GeomVector2d(ptRef, ptPerp);
		GeomVector2d uPtRefToPtPerp = vPtRefToPtPerp.otherUnit();
		double dPtRefToPtPerp = vPtRefToPtPerp.mod();

		GeomVector2d vPtRefToPtDir = new GeomVector2d(ptRef, ptDir);
		GeomVector2d uPtRefToPtDir = vPtRefToPtDir.otherUnit();

		double angleRad = uPtRefToPtPerp.angleTo(uPtRefToPtDir);
		
		double angleDegrees = GeomUtil.convertRadToDegrees(angleRad);
		//System.out.println("AngleDegrees: " + Double.toString(angleDegrees));

		// 0d, 90d, 180d, 270d, ou 360d
		//
		double diff0d = Math.abs( angleRad );
		double diff90d = Math.abs( angleRad - AppDefs.MATHVAL_HPI );
		double diff180d = Math.abs( angleRad - AppDefs.MATHVAL_PI );
		double diff270d = Math.abs( angleRad - AppDefs.MATHVAL_3HPI );
		double diff360d = Math.abs( angleRad - AppDefs.MATHVAL_2PI );
		
		// PERP_LINES
		//
		if( (diff0d < AppDefs.MATHPREC_MIN) ||
			(diff180d < AppDefs.MATHPREC_MIN) || 
			(diff360d < AppDefs.MATHPREC_MIN) ) 
		{
			return ptPerp;
		}

		// 90d ou 270d = PARALLEL_LINES
		//
		if( (diff90d < AppDefs.MATHPREC_MIN) ||
			(diff270d < AppDefs.MATHPREC_MIN) ) 
		{
			return null;
		}		

		double dH = Math.abs( dPtRefToPtPerp / Math.cos(angleRad) );
		double dD = Math.abs( dH * Math.sin(angleRad) );
		
		GeomVector2d vPtPerpToPtDir = new GeomVector2d(ptPerp, ptDir);
		double dPtPerpToPtDir = uIF1.dotProd(vPtPerpToPtDir);
		if(dPtPerpToPtDir < 0.0)
			dD = - dD;

		double dZ = uPtRefToPtPerp.dotProd(vPtRefToPtDir);
		if(dZ < 0.0) return null;		
		
		GeomPoint2d ptIntersec = ptPerp.otherMoveTo(uIF1, dD); 
		if( bOnlyRealIntersec ) {
			boolean bInsideRect = GeomUtil.isInsideRect(ptIntersec, ptI1, ptF1);
			if( !bInsideRect ) {
				ptIntersec = null;
			}
		}
		return ptIntersec;
	}
	
	/* SELECT_ALL_NEAREST_LINES (360 DEGREES) */

	public static CadEntity selectNearestLineAtDirection(GeomPoint2d ptRef2d, GeomPoint2d ptDir2d, CadEntity[] arrEntities)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;
		
		for(CadEntity ent : arrEntities) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;
				
				GeomPoint2d ptI2d = new GeomPoint2d(oLine.getPtI());
				GeomPoint2d ptF2d = new GeomPoint2d(oLine.getPtF());

				GeomPoint2d ptIntersec2d = GeomUtil.dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, true);
				if(ptIntersec2d != null) {
					double d = ptRef2d.distTo(ptIntersec2d);
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	public static ArrayList<CadEntity> selectAllLinesAtDirection(GeomPoint2d ptRef2d, GeomPoint2d ptDir2d, CadEntity[] arrEntities)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		for(CadEntity ent : arrEntities) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;
				
				GeomPoint2d ptI2d = new GeomPoint2d(oLine.getPtI());
				GeomPoint2d ptF2d = new GeomPoint2d(oLine.getPtF());

				GeomPoint2d ptIntersec2d = GeomUtil.dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, true);
				if(ptIntersec2d != null) {
					lsResult.add(oLine);
				}
			}
		}
		return lsResult;
	}

	public static ArrayList<CadEntity> selectAllNearestLines360Degrees(GeomPoint2d ptRef2d, CadEntity[] arrEntities)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		Hashtable map = new Hashtable();
		
		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
						
		int numSteps = AppDefs.AREADETECTION_NUMBER_RAYS;
		double stepAngle = AppDefs.MATHVAL_2PI / ((double)numSteps);

		double currAngle = 0;
		for(int i = 0; i < numSteps; i++) {
			double dx = Math.cos(currAngle);
			double dy = Math.sin(currAngle);
			
			GeomPoint2d ptDir2d = new GeomPoint2d(xPtRef + dx, yPtRef + dy);
			
			CadEntity ent = GeomUtil.selectNearestLineAtDirection(ptRef2d, ptDir2d, arrEntities);
			if(ent == null) continue;
			
			Integer objectId = ent.getObjectId();
			if( !map.containsKey(objectId) ) {
				map.put(objectId, ent);				
				lsResult.add(ent);
			}
			currAngle = currAngle + stepAngle;
		}
		return lsResult;
	}

	/* SELECT_ALL_CROSSING_LINES (HORIZ/VERT) */
	
	public static ArrayList<CadEntity> selectAllHorizCrossingLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();

		double yPtRef = ptRef2d.getY();
		for(CadEntity ent : lsEntities) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				boolean bHorizCrossing = GeomUtil.isHorizCrossing(yPtRef, oLine.getPtI(), oLine.getPtF());
				if( bHorizCrossing )
					lsResult.add(oLine);
			}
		}
		return lsResult;
	}
	
	public static ArrayList<CadEntity> selectAllVertCrossingLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();

		double xPtRef = ptRef2d.getX();
		for(CadEntity ent : lsEntities) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				boolean bVertCrossing = GeomUtil.isVertCrossing(xPtRef, oLine.getPtI(), oLine.getPtF());
				if( bVertCrossing )
					lsResult.add(oLine);
			}
		}
		return lsResult;
	}

	/* SELECT_NEAREST_LINES (HORIZ_LEFT and HORIZ_RIGHT) */
	
	// WITH_FILTER
	//
	public static CadEntity selectNearestHorizLeftLineWithFilter(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		ArrayList<CadEntity> lsHorizLines = selectAllHorizCrossingLine(ptRef2d, lsEntities);
		for(CadEntity ent : lsHorizLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.perpIntersectionOf(ptRef2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(xPt0 < xPtRef) {
					double d = Math.abs( xPt0 - xPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}
	
	public static CadEntity selectNearestHorizRightLineWithFilter(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		ArrayList<CadEntity> lsHorizLines = selectAllHorizCrossingLine(ptRef2d, lsEntities);
		for(CadEntity ent : lsHorizLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.perpIntersectionOf(ptRef2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(xPt0 >= xPtRef) {
					double d = Math.abs( xPt0 - xPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	// WITHOUT_FILTER
	//
	public static CadEntity selectNearestHorizLeftLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsHorizLines)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		GeomPoint2d ptDir2d = new GeomPoint2d(xPtRef - 1.0, yPtRef);
		
		for(CadEntity ent : lsHorizLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(xPt0 < xPtRef) {
					double d = Math.abs( xPt0 - xPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}
	
	public static CadEntity selectNearestHorizRightLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsHorizLines)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		GeomPoint2d ptDir2d = new GeomPoint2d(xPtRef + 1.0, yPtRef);
		
		for(CadEntity ent : lsHorizLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(xPt0 >= xPtRef) {
					double d = Math.abs( xPt0 - xPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	/* SELECT_NEAREST_LINES (VERT_UP and VERT_DOWN) */
	
	// WITH_FILTER
	//
	public static CadEntity selectNearestVertUpLineWithFilter(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		GeomPoint2d ptDir2d = new GeomPoint2d(xPtRef, yPtRef + 1.0);
		
		ArrayList<CadEntity> lsVertLines = selectAllVertCrossingLine(ptRef2d, lsEntities);
		for(CadEntity ent : lsVertLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(yPt0 >= yPtRef) {
					double d = Math.abs( yPt0 - yPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}
	
	public static CadEntity selectNearestVertDownLineWithFilter(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsEntities)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		GeomPoint2d ptDir2d = new GeomPoint2d(xPtRef, yPtRef - 1.0);
		
		ArrayList<CadEntity> lsVertLines = selectAllHorizCrossingLine(ptRef2d, lsEntities);
		for(CadEntity ent : lsVertLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.dirIntersectionOf(ptRef2d, ptDir2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(yPt0 < yPtRef) {
					double d = Math.abs( yPt0 - yPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	// WITHOUT_FILTER
	//
	public static CadEntity selectNearestVertUpLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsVertLines)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		for(CadEntity ent : lsVertLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.perpIntersectionOf(ptRef2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(yPt0 >= yPtRef) {
					double d = Math.abs( yPt0 - yPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}
	
	public static CadEntity selectNearestVertDownLine(GeomPoint2d ptRef2d, ArrayList<CadEntity> lsVertLines)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		double xPtRef = ptRef2d.getX();
		double yPtRef = ptRef2d.getY();
		
		for(CadEntity ent : lsVertLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() ); 
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() ); 

				GeomPoint2d pt0 = GeomUtil.perpIntersectionOf(ptRef2d, ptI2d, ptF2d, true);
				if(pt0 == null) continue;

				double xPt0 = pt0.getX();
				double yPt0 = pt0.getY();
				
				if(yPt0 < yPtRef) {
					double d = Math.abs( yPt0 - yPtRef );
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	/* SELECT_NEAREST_HORIZ_CROSSING_LINE */

	public static CadEntity selectNearestHorizCrossingLineWithFilter(GeomPoint3d ptRef3d, ArrayList<CadEntity> lsEntities)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d );
		double yPtRef = ptRef2d.getY();
		
		ArrayList<CadEntity> lsAllLines = GeomUtil.selectAllHorizCrossingLine(ptRef2d, lsEntities);
		for(CadEntity ent : lsAllLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

				double xMin = Math.min(ptI2d.getX(), ptF2d.getX());
				double xMax = Math.max(ptI2d.getX(), ptF2d.getX());
				double dX = xMax - xMin;
				
				double yMin = Math.min(ptI2d.getY(), ptF2d.getY());
				double yMax = Math.max(ptI2d.getY(), ptF2d.getY());
				double dY = yMax - yMin;
				
				double dYRef = yPtRef - yMin;
				if(dY > AppDefs.MATHPREC_MIN) {
					double dXRef = xMin + ( dYRef / dY ) * dX;
					
					GeomPoint2d ptInter2d = new GeomPoint2d(dXRef, dYRef); 
					double d = ptRef2d.distTo(ptInter2d);
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	public static CadEntity selectNearestHorizCrossingLine(GeomPoint3d ptRef3d, ArrayList<CadEntity> lsAllCrossingLines)
	{
		CadEntity oResult = null;
		double minDist = Double.MAX_VALUE;

		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef3d );
		double yPtRef = ptRef3d.getY();
		
		for(CadEntity ent : lsAllCrossingLines) {
			if(ent.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine oLine = (CadLine)ent;

				GeomPoint2d ptI2d = new GeomPoint2d( oLine.getPtI() );
				GeomPoint2d ptF2d = new GeomPoint2d( oLine.getPtF() );

				double xMin = Math.min(ptI2d.getX(), ptF2d.getX());
				double xMax = Math.max(ptI2d.getX(), ptF2d.getX());
				double dX = xMax - xMin;
				
				double yMin = Math.min(ptI2d.getY(), ptF2d.getY());
				double yMax = Math.max(ptI2d.getY(), ptF2d.getY());
				double dY = yMax - yMin;
				
				if(dY > AppDefs.MATHPREC_MIN) {
					double dYRef = yPtRef - yMin;
					double dXRef = dYRef * ( dX / dY );
					
					GeomPoint2d ptInter2d = new GeomPoint2d(xMin + dXRef, yMin + dYRef); 
					double d = ptRef2d.distTo(ptInter2d);
					if(d < minDist) {
						oResult = oLine;
						minDist = d;
					}
				}
			}
		}
		return oResult;
	}

	/* SELECT_CONNECTED_LINES */
	
	public static ArrayList<CadEntity> selectConnectedLines(GeomPoint3d ptInside3d, CadEntity ent, ArrayList<CadEntity> lsEntities)
	{
		if(lsEntities == null) return null;
		if(lsEntities.size() == 0) return null;

		if(ent == null) return null;
		if(ent.getObjType() != AppDefs.OBJTYPE_LINE) return null;

		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();

		CadLine oLine = (CadLine)ent;
		GeomPoint3d ptI3d = new GeomPoint3d( oLine.getPtI() );
		GeomPoint3d ptF3d = new GeomPoint3d( oLine.getPtF() );

		GeomPoint3d ptRef3d = new GeomPoint3d(ptF3d);
		int pos = GeomUtil.findNearestConnectedLine(ptInside3d, ptRef3d, lsEntities);
		while(pos != -1) {
			oLine = (CadLine)lsEntities.remove(pos);
			lsResult.add(oLine);
			
			ptI3d = new GeomPoint3d( oLine.getPtI() );
			ptF3d = new GeomPoint3d( oLine.getPtF() );

			double dToPtI = ptRef3d.distTo( ptI3d );
			double dToPtF = ptRef3d.distTo( ptF3d );

			if(dToPtI < dToPtF) {
				ptRef3d = new GeomPoint3d( ptF3d );
			}
			else {
				ptRef3d = new GeomPoint3d( ptI3d );
			}			
			pos = findNearestConnectedLine(ptInside3d, ptRef3d, lsEntities);
		}
		return lsResult;
	}
	
	public static ArrayList<CadEntity> selectConnectedLines(ArrayList<CadEntity> lsEntities)
	{
		if(lsEntities == null) return null;
		if(lsEntities.size() == 0) return null;

		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();

		CadLine oLine = (CadLine)lsEntities.get(0);
		GeomPoint3d ptI = new GeomPoint3d( oLine.getPtI() );
		GeomPoint3d ptF = new GeomPoint3d( oLine.getPtF() );

		GeomPoint3d ptRef = new GeomPoint3d(ptF);
		int pos = GeomUtil.findConnectedLine(ptRef, lsEntities);
		while(pos != -1) {
			oLine = (CadLine)lsEntities.remove(pos);
			lsResult.add(oLine);
			
			ptI = new GeomPoint3d( oLine.getPtI() );
			ptF = new GeomPoint3d( oLine.getPtF() );

			double dToPtI = ptRef.distTo( ptI );
			double dToPtF = ptRef.distTo( ptF );

			if(dToPtI < dToPtF) {
				ptRef = new GeomPoint3d( ptF );
			}
			else {
				ptRef = new GeomPoint3d( ptI );
			}			
			pos = findConnectedLine(ptRef, lsEntities);
		}
		return lsResult;
	}

	// CURRENT_LEVEL
	//
	
	public static CadLevel getCurrLevel(CadDocumentDef doc)
	{
		CadLevel oResult = null;
		if(doc == null) return oResult;		

		oResult = (CadLevel)doc.getCurrLevel();
		return oResult;
	}

	public static CadLevel getCurrLevel()
	{
		CadLevel oResult = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		CadDocumentDef doc = panel.getCurrDocumentDef();
		if(doc == null) return oResult;
		
		oResult = (CadLevel)doc.getCurrLevel();
		return oResult;
	}

	// PT3D-Z - LEVEL_ADJUSTMENT_TO_LEVEL
	//
	
	// LEVEL_FROM_PT2D
	public static GeomPoint3d toLevelFromPt2d(GeomPoint2d pt2d, CadLevel oLevel)
	{
		GeomPoint3d ptResult3d = new GeomPoint3d(pt2d);
		if(oLevel == null) return ptResult3d;
		
		double zH = oLevel.getZLevel();		
		ptResult3d.setZ(zH);
		
		return ptResult3d;
	}

	// LEVEL_FROM_PT3D
	public static GeomPoint3d toLevelFromPt3d(GeomPoint3d pt3d, CadLevel oLevel)
	{
		GeomPoint3d ptResult3d = new GeomPoint3d(pt3d);
		if(oLevel == null) return ptResult3d;
		
		double zH = oLevel.getZLevel();		
		ptResult3d.setZ(zH);
		
		return ptResult3d;
	}

	public static GeomPoint3d toLevelFromPt3d(GeomPoint3d pt3d, CadLevel oLevel, double dZ)
	{
		GeomPoint3d ptResult3d = new GeomPoint3d(pt3d);
		if(oLevel == null) return ptResult3d;
		
		double zH = oLevel.getZLevel() + dZ;		
		ptResult3d.setZ(zH);
		
		return ptResult3d;
	}

	// LEVEL_FROM_LST_PT2D
	public static ArrayList<GeomPoint3d> toLevelFromLsPts2d(ArrayList<GeomPoint2d> lsPt2d, CadLevel oLevel)
	{
		ArrayList<GeomPoint3d> lsResult3d = null;
		if(oLevel == null) {
			lsResult3d = GeomUtil.copyPt2dTo3dList(lsPt2d, 0);
			return lsResult3d;		
		}
		
		double zH = oLevel.getZLevel();
		lsResult3d = GeomUtil.copyPt2dTo3dList(lsPt2d, zH);

		return lsResult3d;		
	}

	// LEVEL_FROM_LST_PT3D
	public static ArrayList<GeomPoint3d> toLevelFromLsPts3d(ArrayList<GeomPoint3d> lsPt3d, CadLevel oLevel)
	{
		ArrayList<GeomPoint3d> lsResult3d = null;
		if(oLevel == null) {
			lsResult3d = GeomUtil.copyPt3dList(lsPt3d);
			return lsResult3d;		
		}
		
		double zH = oLevel.getZLevel();
		lsResult3d = GeomUtil.copyPt3dTo3dList(lsPt3d, zH);

		return lsResult3d;		
	}

	// PT3D-Z - LEVEL_ADJUSTMENT
	//
	
	// LEVEL_FROM_PT2D
	public static GeomPoint3d toLevelFromPt2d(GeomPoint2d pt2d)
	{
		GeomPoint3d ptResult3d = new GeomPoint3d(pt2d);
		
		CadLevel oLevel = GeomUtil.getCurrLevel();
		if(oLevel == null) return ptResult3d;

		double zH = oLevel.getZLevel();		
		ptResult3d.setZ(zH);
		
		return ptResult3d;
	}

	// LEVEL_FROM_PT3D
	public static GeomPoint3d toLevelFromPt3d(GeomPoint3d pt3d)
	{
		GeomPoint3d ptResult3d = new GeomPoint3d(pt3d);
		
		CadLevel oLevel = GeomUtil.getCurrLevel();
		if(oLevel == null) return ptResult3d;

		double zH = oLevel.getZLevel();		
		ptResult3d.setZ(zH);
		
		return ptResult3d;
	}

	// LEVEL_FROM_LST_PT2D
	public static ArrayList<GeomPoint3d> toLevelFromLsPts2d(ArrayList<GeomPoint2d> lsPt2d)
	{
		ArrayList<GeomPoint3d> lsResult3d = null;
		
		CadLevel oLevel = GeomUtil.getCurrLevel();
		if(oLevel == null) {
			lsResult3d = GeomUtil.copyPt2dTo3dList(lsPt2d, 0);
			return lsResult3d;		
		}
		
		double zH = oLevel.getZLevel();
		lsResult3d = GeomUtil.copyPt2dTo3dList(lsPt2d, zH);

		return lsResult3d;		
	}

	// LEVEL_FROM_LST_PT3D
	public static ArrayList<GeomPoint3d> toLevelFromLsPts3d(ArrayList<GeomPoint3d> lsPt3d)
	{
		ArrayList<GeomPoint3d> lsResult3d = null;
		
		CadLevel oLevel = GeomUtil.getCurrLevel();
		if(oLevel == null) {
			lsResult3d = GeomUtil.copyPt3dList(lsPt3d);
			return lsResult3d;		
		}
		
		double zH = oLevel.getZLevel();
		lsResult3d = GeomUtil.copyPt3dTo3dList(lsPt3d, zH);

		return lsResult3d;		
	}
	
}
