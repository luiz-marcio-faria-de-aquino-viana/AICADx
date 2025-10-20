/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.CadView3d;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;

public class GeomUtil 
{
//Public

	//2D-ENVELOP

	public static GeomDimension2d getEnvelop2d(int objType) {
		AppCadMain cad = AppCadMain.getCad();
		
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CadBlockDef blk = doc.getCurrBlockDef();
		
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(blk, objType);
		return oGeomDim;
	}

	public static GeomDimension2d getEnvelop2d(CadBlockDef blkDef, int objType) {
		ArrayList<CadEntity> lsEntity = blkDef.findAllEntity();

		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(lsEntity, objType);
		return oGeomDim;
	}

	public static GeomDimension2d getEnvelop2d(ArrayList<CadEntity> lsEntity, int objType) {
		//LIMITS
		//
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		//
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		
		int szLsEntity = lsEntity.size();
		for(int i = 0; i < szLsEntity; i++) {
			CadEntity oEnt = lsEntity.get(i);

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
	
	//3D-ENVELOP

	public static GeomDimension3d getEnvelop3d(ArrayList<CadEntity> lsEntity, int objType) {
		//LIMITS
		//
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		//
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		double maxZ = - Double.MAX_VALUE;
		
		int szLsEntity = lsEntity.size();
		for(int i = 0; i < szLsEntity; i++) {
			CadEntity oEnt = lsEntity.get(i);

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
		
		String strObjectId = Integer.toString(objectId);
		String strObjType = oEnt.getObjTypeStr();		
		String strLayerName = oLayer.getName();
		
		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();		

		lsProperty.add( new ItemDataVO("ObjectId", strObjectId) );		
		lsProperty.add( new ItemDataVO("ObjType", strObjType) );		
		lsProperty.add( new ItemDataVO("Layer", strLayerName) );
		
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
    
	public static GeomPoint2d intersectionOf(GeomPoint3d pt3dI1, GeomPoint3d pt3dF1, GeomPoint3d pt3dI2, GeomPoint3d pt3dF2)
	{
		GeomPoint2d pt2dI1 = new GeomPoint2d(pt3dI1); 
		GeomPoint2d pt2dF1 = new GeomPoint2d(pt3dF1);
		
		GeomPoint2d pt2dI2 = new GeomPoint2d(pt3dI2); 
		GeomPoint2d pt2dF2 = new GeomPoint2d(pt3dF2);
		
		GeomPoint2d pt2d = intersectionOf(pt2dI1, pt2dF1, pt2dI2, pt2dF2);
		return pt2d;
	}
		
	public static GeomPoint2d intersectionOf(GeomPoint2d pt2dI1, GeomPoint2d pt2dF1, GeomPoint2d pt2dI2, GeomPoint2d pt2dF2)
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
	
}
