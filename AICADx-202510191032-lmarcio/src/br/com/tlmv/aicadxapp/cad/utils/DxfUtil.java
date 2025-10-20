/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DxfUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 18/07/2025
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

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfEntry;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class DxfUtil 
{
//Private Static
	private static int dxfObjectId = AppDefs.DEF_SEQID_DXFOBJECTID;
	
	/* Methodes */
	
	private static synchronized int currDxfObjectId() {
		return DxfUtil.dxfObjectId;
	}
	
	private static synchronized int nextDxfObjectId() {
		return DxfUtil.dxfObjectId++;
	}
	
//Public

	/* 3DFACE (P1, P2, P3, P4) */
	
	public static ArrayList<DxfCadEntity> toDxf3DFace(CadLayerDef oLayer, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3, GeomVector3d zDir)
	{
		ArrayList<DxfCadEntity> lsDxfResult = toDxf3DFace(oLayer, pt1.getX(), pt1.getY(), pt1.getZ(), pt2.getX(), pt2.getY(), pt2.getZ(), pt3.getX(), pt3.getY(), pt3.getZ(), zDir);
		return lsDxfResult;
	}

	public static ArrayList<DxfCadEntity> toDxf3DFace(CadLayerDef oLayer, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		ArrayList<DxfCadEntity> lsDxfResult = toDxf3DFace(
			oLayer, pt1.getX(), pt1.getY(), pt1.getZ(), pt2.getX(), pt2.getY(), pt2.getZ(), pt3.getX(), pt3.getY(), pt3.getZ() );
		return lsDxfResult;
	}

	public static ArrayList<DxfCadEntity> toDxf3DFace(
		CadLayerDef oLayer, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3, GeomPoint3d pt4, GeomVector3d zDir)
	{
		ArrayList<DxfCadEntity> lsDxfResult = toDxf3DFace(
			oLayer, pt1.getX(), pt1.getY(), pt1.getZ(), pt2.getX(), pt2.getY(), pt2.getZ(), pt3.getX(), pt3.getY(), pt3.getZ(), pt4.getX(), pt4.getY(), pt4.getZ(), zDir);
		return lsDxfResult;
	}

	public static ArrayList<DxfCadEntity> toDxf3DFace(
		CadLayerDef oLayer, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3, GeomPoint3d pt4)
	{
		ArrayList<DxfCadEntity> lsDxfResult = toDxf3DFace(
			oLayer, pt1.getX(), pt1.getY(), pt1.getZ(), pt2.getX(), pt2.getY(), pt2.getZ(), pt3.getX(), pt3.getY(), pt3.getZ(), pt4.getX(), pt4.getY(), pt4.getZ() );
		return lsDxfResult;
	}
	
	/* 3DFACE [P1(X, Y, Z), P2(X, Y, Z), P3(X, Y, Z), P4(X, Y, Z)] */
	
	public static ArrayList<DxfCadEntity> toDxf3DFace(
		CadLayerDef oLayer, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, GeomVector3d zDir)
	{
		ArrayList<DxfCadEntity> lsResult = DxfUtil.toDxf3DFace(
			oLayer, x1, y1, z1, x2, y2, z2, x3, y3, z3, x3, y3, z3, zDir);
		return lsResult;
	}
			
	public static ArrayList<DxfCadEntity> toDxf3DFace(
		CadLayerDef oLayer, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3)
	{
		ArrayList<DxfCadEntity> lsResult = DxfUtil.toDxf3DFace(
			oLayer, x1, y1, z1, x2, y2, z2, x3, y3, z3, x3, y3, z3);
		return lsResult;
	}
	
	public static ArrayList<DxfCadEntity> toDxf3DFace(
		CadLayerDef oLayer, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, GeomVector3d zDir)
    {
    	GeomPoint3d pt0Mcs = new GeomPoint3d(x1, y1, z1);
    	GeomPoint3d pt1Mcs = new GeomPoint3d(x2, y2, z2);
    	GeomPoint3d pt2Mcs = new GeomPoint3d(x3, y3, z3);
    	GeomPoint3d pt3Mcs = new GeomPoint3d(x4, y4, z4);
		
		GeomPoint3d newPt0Mcs = GeomUtil.rotationXY(pt0Mcs, zDir);
    	GeomPoint3d newPt1Mcs = GeomUtil.rotationXY(pt1Mcs, zDir);
    	GeomPoint3d newPt2Mcs = GeomUtil.rotationXY(pt2Mcs, zDir);
    	GeomPoint3d newPt3Mcs = GeomUtil.rotationXY(pt3Mcs, zDir);
    
    	ArrayList<DxfCadEntity> lsDxfEntities3d = DxfUtil.toDxf3DFace(
    		oLayer, 
			newPt0Mcs.getX(), 
			newPt0Mcs.getY(), 
			newPt0Mcs.getZ(), 
			newPt1Mcs.getX(), 
			newPt1Mcs.getY(), 
			newPt1Mcs.getZ(), 
			newPt2Mcs.getX(), 
			newPt2Mcs.getY(), 
			newPt2Mcs.getZ(), 
			newPt3Mcs.getX(), 
			newPt3Mcs.getY(), 
			newPt3Mcs.getZ() );
    	return lsDxfEntities3d;
    }

	public static ArrayList<DxfCadEntity> toDxf3DFace(
		CadLayerDef oLayer, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		AppCadMain cad = AppCadMain.getCad();
		
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		LayerTable tbl = doc.getLayerTable();

		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
    		DxfUtil.nextDxfObjectId(),
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_3DFACE); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		String layerRef3d = oLayer.getReference() + AppDefs.DEF_LAYER_SULFIX_3D;

		CadLayerDef oLayer3d = tbl.getLayerDefByRef(layerRef3d);
		if(oLayer3d == null)
			oLayer3d = oLayer;

		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer3d.getName());
		oDxfCadEntity.add(oDxfEntry);

		//START_POINT
		String dxfFirstPointXVal = nf6.format(x1);
		DxfEntry oDxfFirstPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_FIRSTPOINT_X, dxfFirstPointXVal);
		oDxfCadEntity.add(oDxfFirstPointX);
		
		String dxfFirstPointYVal = nf6.format(y1);
		DxfEntry oDxfFirstPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_FIRSTPOINT_Y, dxfFirstPointYVal);
		oDxfCadEntity.add(oDxfFirstPointY);
		
		String dxfFirstPointZVal = nf6.format(z1);
		DxfEntry oDxfFirstPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_FIRSTPOINT_Z, dxfFirstPointZVal);
		oDxfCadEntity.add(oDxfFirstPointZ);
		
		//SECOND_POINT
		String dxfSecondPointXVal = nf6.format(x2);
		DxfEntry oDxfSecondPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_SECONDPOINT_X, dxfSecondPointXVal);
		oDxfCadEntity.add(oDxfSecondPointX);
		
		String dxfSecondPointYVal = nf6.format(y2);
		DxfEntry oDxfSecondPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_SECONDPOINT_Y, dxfSecondPointYVal);
		oDxfCadEntity.add(oDxfSecondPointY);
		
		String dxfSecondPointZVal = nf6.format(z2);
		DxfEntry oDxfSecondPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_SECONDPOINT_Z, dxfSecondPointZVal);
		oDxfCadEntity.add(oDxfSecondPointZ);
		
		//THIRD_POINT
		String dxfThirdPointXVal = nf6.format(x3);
		DxfEntry oDxfThirdPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_THIRDPOINT_X, dxfThirdPointXVal);
		oDxfCadEntity.add(oDxfThirdPointX);
		
		String dxfThirdPointYVal = nf6.format(y3);
		DxfEntry oDxfThirdPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_THIRDPOINT_Y, dxfThirdPointYVal);
		oDxfCadEntity.add(oDxfThirdPointY);
		
		String dxfThirdPointZVal = nf6.format(z3);
		DxfEntry oDxfThirdPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_THIRDPOINT_Z, dxfThirdPointZVal);
		oDxfCadEntity.add(oDxfThirdPointZ);
		
		//FOURTH_POINT
		String dxfFourthPointXVal = nf6.format(x4);
		DxfEntry oDxfFourthPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_FOURTHPOINT_X, dxfFourthPointXVal);
		oDxfCadEntity.add(oDxfFourthPointX);
		
		String dxfFourthPointYVal = nf6.format(y4);
		DxfEntry oDxfFourthPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_FOURTHPOINT_Y, dxfFourthPointYVal);
		oDxfCadEntity.add(oDxfFourthPointY);
		
		String dxfFourthPointZVal = nf6.format(z4);
		DxfEntry oDxfFourthPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_FOURTHPOINT_Z, dxfFourthPointZVal);
		oDxfCadEntity.add(oDxfFourthPointZ);
		
		//FLAGS
		String dxfInvisibleFlagVal = StringUtil.fillLeft("0", 6);
		DxfEntry oDxfInvisibleFlagX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_INVISIBLE_FLAG, dxfInvisibleFlagVal);
		oDxfCadEntity.add(oDxfInvisibleFlagX);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}
	
	/* LINE */
	
	public static ArrayList<DxfCadEntity> toDxfLine(CadLayerDef oLayer, GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		GeomPoint3d ptI = new GeomPoint3d(ptI2d);
		GeomPoint3d ptF = new GeomPoint3d(ptF2d);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfLine(oLayer, ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ() );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfLine(CadLayerDef oLayer, GeomPoint3d ptI, GeomPoint3d ptF)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfLine(oLayer, ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ() );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfLine(CadLayerDef oLayer, double xI, double yI, double zI, double xF, double yF, double zF)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			objectId, 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_LINE); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);

		//START_POINT
		String dxfStartPointXVal = nf6.format(xI);
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format(yI);
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format(zI);
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);

		//END_POINT
		String dxfEndPointXVal = nf6.format(xF);
		DxfEntry oDxfEndPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_X, dxfEndPointXVal);
		oDxfCadEntity.add(oDxfEndPointX);
		
		String dxfEndPointYVal = nf6.format(yF);
		DxfEntry oDxfEndPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_Y, dxfEndPointYVal);
		oDxfCadEntity.add(oDxfEndPointY);
		
		String dxfEndPointZVal = nf6.format(zF);
		DxfEntry oDxfEndPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_Z, dxfEndPointZVal);
		oDxfCadEntity.add(oDxfEndPointZ);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}

	/* LINE - COLOR: BYLAYER / LTYPE: BYLAYER */
	
	public static ArrayList<DxfCadEntity> toDxfLine(CadLayerDef oLayer, int colnum, String ltype, GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		GeomPoint3d ptI = new GeomPoint3d(ptI2d);
		GeomPoint3d ptF = new GeomPoint3d(ptF2d);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfLine(oLayer, colnum, ltype, ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ() );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfLine(CadLayerDef oLayer, int colnum, String ltype, GeomPoint3d ptI, GeomPoint3d ptF)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfLine(oLayer, colnum, ltype, ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ() );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfLine(CadLayerDef oLayer, int colnum, String ltype, double xI, double yI, double zI, double xF, double yF, double zF)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			objectId, 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_LINE); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);
		
		//COLOR
		String strColor = StringUtil.fillLeft(nf0.format(colnum), 6);
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_COLOR, strColor);
		oDxfCadEntity.add(oDxfEntry);
		
		//LTYPE
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LTYPE, ltype);
		oDxfCadEntity.add(oDxfEntry);

		//START_POINT
		String dxfStartPointXVal = nf6.format(xI);
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format(yI);
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format(zI);
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);

		//END_POINT
		String dxfEndPointXVal = nf6.format(xF);
		DxfEntry oDxfEndPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_X, dxfEndPointXVal);
		oDxfCadEntity.add(oDxfEndPointX);
		
		String dxfEndPointYVal = nf6.format(yF);
		DxfEntry oDxfEndPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_Y, dxfEndPointYVal);
		oDxfCadEntity.add(oDxfEndPointY);
		
		String dxfEndPointZVal = nf6.format(zF);
		DxfEntry oDxfEndPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_ENDPOINT_Z, dxfEndPointZVal);
		oDxfCadEntity.add(oDxfEndPointZ);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}
	
	/* POINT - COLOR: BYLAYER + LTYPE: BYLAYER */	
	
	public static ArrayList<DxfCadEntity> toDxfPoint(CadLayerDef oLayer, GeomPoint2d ptIns2d)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = DxfUtil.toDxfPoint(
			oLayer, 
			AppDefs.DEF_DXFCOLOR_BYLAYER, 
			AppDefs.DEF_DXFLTYPE_BYLAYER, 
			ptIns2d.getX(),
			ptIns2d.getY(),
			0.0 );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfPoint(CadLayerDef oLayer, GeomPoint3d ptIns3d)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = DxfUtil.toDxfPoint(
			oLayer, 
			AppDefs.DEF_DXFCOLOR_BYLAYER, 
			AppDefs.DEF_DXFLTYPE_BYLAYER, 
			ptIns3d.getX(),
			ptIns3d.getY(),
			ptIns3d.getZ() );
		return lsCadEntity2d;
	}
		
	public static ArrayList<DxfCadEntity> toDxfPoint(CadLayerDef oLayer, double xP, double yP, double zP)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = DxfUtil.toDxfPoint(oLayer, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_BYLAYER, xP, yP, zP);
		return lsCadEntity2d;
	}

	/* POINT - COLOR + LTYPE */	
	
	public static ArrayList<DxfCadEntity> toDxfPoint(CadLayerDef oLayer, int colnum, String ltype, GeomPoint2d ptIns2d)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = DxfUtil.toDxfPoint(
			oLayer,
			colnum,
			ltype,
			ptIns2d.getX(),
			ptIns2d.getY(),
			0.0 );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfPoint(CadLayerDef oLayer, int colnum, String ltype, GeomPoint3d ptIns3d)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = DxfUtil.toDxfPoint(
			oLayer,
			colnum,
			ltype,
			ptIns3d.getX(),
			ptIns3d.getY(),
			ptIns3d.getZ() );
		return lsCadEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfPoint(CadLayerDef oLayer, int colnum, String ltype, double xP, double yP, double zP)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			objectId, 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_POINT); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);
		
		//COLOR
		String strColor = StringUtil.fillLeft(nf0.format(colnum), 6);
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_COLOR, strColor);
		oDxfCadEntity.add(oDxfEntry);
		
		//LTYPE
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LTYPE, ltype);
		oDxfCadEntity.add(oDxfEntry);

		//START_POINT
		String dxfStartPointXVal = nf6.format(xP);
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format(yP);
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format(zP);
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}

	/* CIRCLE - COLOR: BYLAYER + LTYPE: BYLAYER */	
	
	public static ArrayList<DxfCadEntity> toDxfCircle(CadLayerDef oLayer,GeomPoint2d ptCenter, double radius)
	{
		ArrayList<DxfCadEntity> lsEntity2d = toDxfCircle(oLayer, ptCenter.getX(), ptCenter.getY(), 0.0, radius);
		return lsEntity2d;
	}

	public static ArrayList<DxfCadEntity> toDxfCircle(CadLayerDef oLayer, GeomPoint3d ptCenter, double radius)
	{
		ArrayList<DxfCadEntity> lsEntity2d = toDxfCircle(oLayer, ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radius);
		return lsEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfCircle(CadLayerDef oLayer, double xCenter, double yCenter, double zCenter, double radius)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			objectId, 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_CIRCLE); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);

		//START_POINT
		String dxfStartPointXVal = nf6.format(xCenter);
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format(yCenter);
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format(zCenter);
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);

		//RADIUS
		String dxfRadiusVal = nf6.format(radius);
		DxfEntry oDxfRadius = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_RADIUS, dxfRadiusVal);
		oDxfCadEntity.add(oDxfRadius);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}
	
	/* CIRCLE - COLOR + LTYPE */	
	
	public static ArrayList<DxfCadEntity> toDxfCircle(CadLayerDef oLayer, int colnum, String ltype, GeomPoint2d ptCenter, double radius)
	{
		ArrayList<DxfCadEntity> lsEntity2d = toDxfCircle(oLayer, colnum, ltype, ptCenter.getX(), ptCenter.getY(), 0.0, radius);
		return lsEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfCircle(CadLayerDef oLayer, int colnum, String ltype, GeomPoint3d ptCenter, double radius)
	{
		ArrayList<DxfCadEntity> lsEntity2d = toDxfCircle(oLayer, colnum, ltype, ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), radius);
		return lsEntity2d;
	}

	public static ArrayList<DxfCadEntity> toDxfCircle(CadLayerDef oLayer, int colnum, String ltype, double xCenter, double yCenter, double zCenter, double radius)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			objectId, 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_CIRCLE); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);
		
		//COLOR
		String strColor = StringUtil.fillLeft(nf0.format(colnum), 6);
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_COLOR, strColor);
		oDxfCadEntity.add(oDxfEntry);
		
		//LTYPE
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LTYPE, ltype);
		oDxfCadEntity.add(oDxfEntry);
		
		//START_POINT
		String dxfStartPointXVal = nf6.format(xCenter);
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format(yCenter);
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format(zCenter);
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);

		//RADIUS
		String dxfRadiusVal = nf6.format(radius);
		DxfEntry oDxfRadius = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_RADIUS, dxfRadiusVal);
		oDxfCadEntity.add(oDxfRadius);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}
	
	/* TEXT */	
	
	public static ArrayList<DxfCadEntity> toDxfText(CadLayerDef oLayer, String strText, GeomPoint2d ptStartPoint, double textHeight, double textRotation, int horizAlign, int vertAlign)
	{
		ArrayList<DxfCadEntity> lsEntity2d = toDxfText(
			oLayer, 
			strText, 
			ptStartPoint.getX(), 
			ptStartPoint.getY(), 
			0.0, 
			textHeight, 
			textRotation, 
			horizAlign, 
			vertAlign);
		return lsEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfText(CadLayerDef oLayer, String strText, GeomPoint3d ptStartPoint, double textHeight, double textRotation, int horizAlign, int vertAlign)
	{
		ArrayList<DxfCadEntity> lsEntity2d = toDxfText(
			oLayer, 
			strText, 
			ptStartPoint.getX(), 
			ptStartPoint.getY(), 
			ptStartPoint.getZ(), 
			textHeight, 
			textRotation, 
			horizAlign, 
			vertAlign);
		return lsEntity2d;
	}
		
	public static ArrayList<DxfCadEntity> toDxfText(CadLayerDef oLayer, String strText, double xStartPoint, double yStartPoint, double zStartPoint, double textHeight, double textRotation, int horizAlign, int vertAlign)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);

		int objectId = DxfUtil.nextDxfObjectId();
		
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			objectId, 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_TEXT); 
		
		//HANDLE
		String dxfHandleVal = Integer.toHexString(objectId);
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HANDLE, dxfHandleVal);
		oDxfCadEntity.add(oDxfEntry);
		
		//LAYER
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER, oLayer.getName());
		oDxfCadEntity.add(oDxfEntry);
		
		//TEXTSTRING
		oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_TEXTSTRING, strText);
		oDxfCadEntity.add(oDxfEntry);
		
		//START_POINT
		String dxfStartPointXVal = nf6.format(xStartPoint);
		DxfEntry oDxfStartPointX = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_X, dxfStartPointXVal);
		oDxfCadEntity.add(oDxfStartPointX);
		
		String dxfStartPointYVal = nf6.format(yStartPoint);
		DxfEntry oDxfStartPointY = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Y, dxfStartPointYVal);
		oDxfCadEntity.add(oDxfStartPointY);
		
		String dxfStartPointZVal = nf6.format(zStartPoint);
		DxfEntry oDxfStartPointZ = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_STARTPOINT_Z, dxfStartPointZVal);
		oDxfCadEntity.add(oDxfStartPointZ);

		//TEXT_HEIGHT
		String dxfTextHeightVal = nf6.format(textHeight);
		DxfEntry oDxfTextHeight = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_TEXTHEIGHT, dxfTextHeightVal);
		oDxfCadEntity.add(oDxfTextHeight);
		
		//TEXT_ROTATION
		String dxfTextRotationVal = nf6.format(textRotation);
		DxfEntry oDxfTextRotation = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_TEXTROTATION, dxfTextRotationVal);
		oDxfCadEntity.add(oDxfTextRotation);
		
		//HORIZ_ALIGN
		DxfEntry oDxfHorizAlign = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_HORIZALIGN, Integer.toString(horizAlign));
		oDxfCadEntity.add(oDxfHorizAlign);
		
		//VERT_ALIGN
		DxfEntry oDxfVertAlign = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_VERTALIGN, Integer.toString(vertAlign));
		oDxfCadEntity.add(oDxfVertAlign);
		
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();
		lsCadEntity2d.add( oDxfCadEntity );
		
		return lsCadEntity2d;
	}
	
	/* BOX */	

    public static ArrayList<DxfCadEntity> toDxfBox(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptCenter3dMcs, double w, double h, double zH, double rotateRad, GeomVector3d zDir3dMcs)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

    	GeomVector3d zDir = null;
    	if(zDir3dMcs != null)
    		zDir = zDir3dMcs.otherUnit();
	    	
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
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt1Mcs3d, pt2Mcs3d, pt3Mcs3d, pt4Mcs3d, zDir) );
        //TOPO
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt5Mcs3d, pt6Mcs3d, pt7Mcs3d, pt8Mcs3d, zDir) );
        //FRONT
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt1Mcs3d, pt2Mcs3d, pt6Mcs3d, pt5Mcs3d, zDir) );
        //RIGHT
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt2Mcs3d, pt3Mcs3d, pt7Mcs3d, pt6Mcs3d, zDir) );
        //BACK
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt3Mcs3d, pt4Mcs3d, pt8Mcs3d, pt7Mcs3d, zDir) );
        //LEFT
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt4Mcs3d, pt1Mcs3d, pt5Mcs3d, pt8Mcs3d, zDir) );
        
        return lsCadEntity3d;
    }

    public static ArrayList<DxfCadEntity> toDxfBox2Pt(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptMin3dMcs, GeomPoint3d ptMax3dMcs, GeomVector3d vDir3dMcs)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

    	GeomVector3d zDir = null;
    	if(vDir3dMcs != null)
    		zDir = vDir3dMcs.otherUnit();
    	
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
    	
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt1Mcs, pt2Mcs, pt3Mcs, pt4Mcs, zDir) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt5Mcs, pt6Mcs, pt7Mcs, pt8Mcs, zDir) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt1Mcs, pt2Mcs, pt6Mcs, pt5Mcs, zDir) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt2Mcs, pt3Mcs, pt7Mcs, pt6Mcs, zDir) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt3Mcs, pt4Mcs, pt8Mcs, pt7Mcs, zDir) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, pt4Mcs, pt1Mcs, pt5Mcs, pt8Mcs, zDir) );
        
        return lsCadEntity3d;
    }
	
	/* CILINDER */	
	
    public static ArrayList<DxfCadEntity> toDxfCilinder(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptCenterBase3dMcs, GeomVector3d vDir3dMcs, double dist, double radius, boolean bCloseBase, boolean bCloseTop)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

    	int numsegs = AppDefs.DRAWARC_NUMBER_SEGMENTS;

    	double stepAngleRad = AppDefs.MATHVAL_2PI /numsegs;
    	
    	GeomVector3d axisZ = GeomUtil.axisZ3d();
    	
    	double xPtCenterBase3dMcs = ptCenterBase3dMcs.getX();
    	double yPtCenterBase3dMcs = ptCenterBase3dMcs.getY();
    	double zPtCenterBase3dMcs = ptCenterBase3dMcs.getZ();

    	GeomPoint3d ptCenterTop3dMcs = ptCenterBase3dMcs.otherMoveTo(vDir3dMcs, dist); 
    	
    	GeomPoint3d ptIBase3dMcs = new GeomPoint3d(
			xPtCenterBase3dMcs + radius, 
			yPtCenterBase3dMcs, 
			zPtCenterBase3dMcs);

    	GeomVector3d vCenterToBase3dMcs = new GeomVector3d(ptCenterBase3dMcs, ptIBase3dMcs);

    	GeomPoint3d ptITop3dMcs = ptIBase3dMcs.otherMoveTo(axisZ, dist);
    	
		int nsteps = numsegs + 1;
    	double angleRad = 0.0;
		for(int i = 1; i < nsteps; i++) {
			GeomVector3d vNewCenterToBase3dMcs = vCenterToBase3dMcs.otherRotateToRad(angleRad);

			GeomPoint3d ptFBase3dMcs = new GeomPoint3d(vNewCenterToBase3dMcs.getXF(), vNewCenterToBase3dMcs.getYF(), vNewCenterToBase3dMcs.getZF()); 
			GeomPoint3d ptFTop3dMcs = ptFBase3dMcs.otherMoveTo(axisZ, dist); 
			
			lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptIBase3dMcs, ptFBase3dMcs, ptFTop3dMcs, ptITop3dMcs) );
			
			if( bCloseTop ) {
				lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptITop3dMcs, ptFTop3dMcs, ptCenterTop3dMcs) );
			}
			
			if( bCloseBase ) {
				lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptIBase3dMcs, ptFBase3dMcs, ptCenterBase3dMcs) );
			}
			
			angleRad += stepAngleRad;            

			ptIBase3dMcs = ptFBase3dMcs;
			ptITop3dMcs = ptFTop3dMcs;
		}
    	
		//FINAL_FACE3D
		GeomVector3d vNewCenterToBase3dMcs = vCenterToBase3dMcs.otherRotateToRad(angleRad);

		GeomPoint3d ptFBase3dMcs = new GeomPoint3d(vNewCenterToBase3dMcs.getXF(), vNewCenterToBase3dMcs.getYF(), vNewCenterToBase3dMcs.getZF()); 
		GeomPoint3d ptFTop3dMcs = ptFBase3dMcs.otherMoveTo(axisZ, dist); 
		
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptIBase3dMcs, ptFBase3dMcs, ptFTop3dMcs, ptITop3dMcs ) );
		
		if( bCloseTop ) {
			lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptITop3dMcs, ptFTop3dMcs, ptCenterTop3dMcs) );
		}
		
		if( bCloseBase ) {
			lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptIBase3dMcs, ptFBase3dMcs, ptCenterBase3dMcs) );
		}
		
		return lsCadEntity3d;
    }
	
	/* PIPE_SECTION_CIRCULAR */	
	
    public static ArrayList<DxfCadEntity> toDxfPipeSectionCirc(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptI3d, GeomVector3d vDir3d, double dist, double radiusIntMcs, double radiusExtMcs, double thicknessMcs)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

    	GeomVector3d uDir3d = new GeomVector3d(vDir3d);

    	lsCadEntity3d.addAll( toDxfPipeCilinder(oLayer, oEnt, ptI3d, uDir3d, dist, radiusIntMcs, radiusExtMcs) );

		int nsegs = (int) Math.floor(dist / AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH); 

		double d = radiusExtMcs + thicknessMcs;
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint3d ptSegI = ptI3d.otherMoveTo(uDir3d, currDist);

    		lsCadEntity3d.addAll( toDxfPipeCilinder(oLayer, oEnt, ptSegI, uDir3d, thicknessMcs, radiusExtMcs, d) );

    		currDist += AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH;
        }
        return lsCadEntity3d;
    }
    
    public static ArrayList<DxfCadEntity> toDxfPipeCilinder(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptCenterBase3dMcs, GeomVector3d uDir3dMcs, double dist, double intRadius, double extRadius)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

    	GeomPoint3d ptBase3d = new GeomPoint3d(ptCenterBase3dMcs);
    	GeomPoint3d ptTop3d = ptBase3d.otherMoveTo(uDir3dMcs, dist);
    	
    	GeomPoint2d ptBase2d = new GeomPoint2d(ptBase3d);
    	GeomPoint2d ptTop2d = new GeomPoint2d(ptTop3d);

    	GeomVector2d vDir2dMcs = new GeomVector2d(ptBase2d, ptTop2d);

    	GeomVector2d uDir2dMcs = vDir2dMcs.otherUnit();
    	GeomVector2d nDir2dMcs = uDir2dMcs.otherNorm();
		
		int objectId = DxfUtil.nextDxfObjectId();

    	double ang45Rad = Math.PI / 4.0;
    	
    	double cos45Rad = Math.cos(ang45Rad);
    	double sin45Rad = Math.sin(ang45Rad);
    	
    	// INTERNAL_RADIUS 
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
    	
    	// INTERNAL_RADIUS - BASE/TOP POINTS
    	GeomPoint3d ptBaseInt3d_0 = new GeomPoint3d(ptBaseInt2d_left_0.getX() , ptBaseInt2d_left_0.getY() , z0_int);  
    	GeomPoint3d ptBaseInt3d_1 = new GeomPoint3d(ptBaseInt2d_left_1.getX() , ptBaseInt2d_left_1.getY() , z45p_int);  
    	GeomPoint3d ptBaseInt3d_2 = new GeomPoint3d(ptBase2d.getX()           , ptBase2d.getY()           , z90p_int);  
    	GeomPoint3d ptBaseInt3d_3 = new GeomPoint3d(ptBaseInt2d_right_1.getX(), ptBaseInt2d_right_1.getY(), z45p_int);  
    	GeomPoint3d ptBaseInt3d_4 = new GeomPoint3d(ptBaseInt2d_right_0.getX(), ptBaseInt2d_right_1.getY(), z0_int);  
    	GeomPoint3d ptBaseInt3d_5 = new GeomPoint3d(ptBaseInt2d_right_1.getX(), ptBaseInt2d_right_1.getY(), z45n_int);  
    	GeomPoint3d ptBaseInt3d_6 = new GeomPoint3d(ptBase2d.getX()           , ptBase2d.getY()           , z90n_int);
    	GeomPoint3d ptBaseInt3d_7 = new GeomPoint3d(ptBaseInt2d_left_1.getX() , ptBaseInt2d_left_1.getY() , z45n_int);  
    	//
    	GeomPoint3d ptTopInt3d_0 = ptBaseInt3d_0.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_1 = ptBaseInt3d_1.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_2 = ptBaseInt3d_2.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_3 = ptBaseInt3d_3.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_4 = ptBaseInt3d_4.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_5 = ptBaseInt3d_5.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_6 = ptBaseInt3d_6.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopInt3d_7 = ptBaseInt3d_7.otherMoveTo(uDir3dMcs, dist);  
		
    	// EXTERNAL_RADIUS 
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
    	
    	// EXTERNAL_RADIUS - BASE/TOP POINTS
    	GeomPoint3d ptBaseExt3d_0 = new GeomPoint3d(ptBaseExt2d_left_0.getX() , ptBaseExt2d_left_0.getY() , z0_ext);  
    	GeomPoint3d ptBaseExt3d_1 = new GeomPoint3d(ptBaseExt2d_left_1.getX() , ptBaseExt2d_left_1.getY() , z45p_ext);  
    	GeomPoint3d ptBaseExt3d_2 = new GeomPoint3d(ptBase2d.getX()           , ptBase2d.getY()           , z90p_ext);  
    	GeomPoint3d ptBaseExt3d_3 = new GeomPoint3d(ptBaseExt2d_right_1.getX(), ptBaseExt2d_right_1.getY(), z45p_ext);  
    	GeomPoint3d ptBaseExt3d_4 = new GeomPoint3d(ptBaseExt2d_right_0.getX(), ptBaseExt2d_right_1.getY(), z0_ext);  
    	GeomPoint3d ptBaseExt3d_5 = new GeomPoint3d(ptBaseExt2d_right_1.getX(), ptBaseExt2d_right_1.getY(), z45n_ext);  
    	GeomPoint3d ptBaseExt3d_6 = new GeomPoint3d(ptBase2d.getX()           , ptBase2d.getY()           , z90n_ext);
    	GeomPoint3d ptBaseExt3d_7 = new GeomPoint3d(ptBaseExt2d_left_1.getX() , ptBaseExt2d_left_1.getY() , z45n_ext);  
    	//
    	GeomPoint3d ptTopExt3d_0 = ptBaseExt3d_0.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_1 = ptBaseExt3d_1.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_2 = ptBaseExt3d_2.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_3 = ptBaseExt3d_3.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_4 = ptBaseExt3d_4.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_5 = ptBaseExt3d_5.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_6 = ptBaseExt3d_6.otherMoveTo(uDir3dMcs, dist);  
    	GeomPoint3d ptTopExt3d_7 = ptBaseExt3d_7.otherMoveTo(uDir3dMcs, dist);  
    	
    	// DRAW_EXTERNAL_CILINDER
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_0,  ptBaseExt3d_1, ptTopExt3d_1, ptTopExt3d_0,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_1,  ptBaseExt3d_2, ptTopExt3d_2, ptTopExt3d_1,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_2,  ptBaseExt3d_3, ptTopExt3d_3, ptTopExt3d_2,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_3,  ptBaseExt3d_4, ptTopExt3d_4, ptTopExt3d_3,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_4,  ptBaseExt3d_5, ptTopExt3d_5, ptTopExt3d_4,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_5,  ptBaseExt3d_6, ptTopExt3d_6, ptTopExt3d_5,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_6,  ptBaseExt3d_7, ptTopExt3d_7, ptTopExt3d_6,  null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_7,  ptBaseExt3d_0, ptTopExt3d_0, ptTopExt3d_7,  null) );
		
    	// DRAW_FINISH_FACES (BASE)
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_0, ptBaseExt3d_1, ptBaseInt3d_1, ptBaseInt3d_0, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_1, ptBaseExt3d_2, ptBaseInt3d_2, ptBaseInt3d_1, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_2, ptBaseExt3d_3, ptBaseInt3d_3, ptBaseInt3d_2, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_3, ptBaseExt3d_4, ptBaseInt3d_4, ptBaseInt3d_3, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_4, ptBaseExt3d_5, ptBaseInt3d_5, ptBaseInt3d_4, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_5, ptBaseExt3d_6, ptBaseInt3d_6, ptBaseInt3d_5, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_6, ptBaseExt3d_7, ptBaseInt3d_7, ptBaseInt3d_6, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBaseExt3d_7, ptBaseExt3d_0, ptBaseInt3d_0, ptBaseInt3d_7, null) );		
		
    	// DRAW_FINISH_FACES (TOP)
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_0, ptTopExt3d_1, ptTopInt3d_1, ptTopInt3d_0, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_1, ptTopExt3d_2, ptTopInt3d_2, ptTopInt3d_1, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_2, ptTopExt3d_3, ptTopInt3d_3, ptTopInt3d_2, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_3, ptTopExt3d_4, ptTopInt3d_4, ptTopInt3d_3, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_4, ptTopExt3d_5, ptTopInt3d_5, ptTopInt3d_4, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_5, ptTopExt3d_6, ptTopInt3d_6, ptTopInt3d_5, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_6, ptTopExt3d_7, ptTopInt3d_7, ptTopInt3d_6, null) );
		lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptTopExt3d_7, ptTopExt3d_0, ptTopInt3d_0, ptTopInt3d_7, null) );
		
		return lsCadEntity3d;
    }
    
	/* PIPE_SECTION_RECTANGLE */	
	
    public static ArrayList<DxfCadEntity> toDxfPipeSectionRect(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptI3d, GeomVector3d vDir3d, double dist, double widthIntMcs, double heightIntMcs, double widthExtMcs, double heightExtMcs, double thicknessMcs)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

    	GeomVector3d uDir3d = new GeomVector3d(vDir3d);

    	lsCadEntity3d.addAll( toDxfPipeRectangle(oLayer, oEnt, ptI3d, uDir3d, dist, widthIntMcs, heightIntMcs, widthExtMcs, heightExtMcs, thicknessMcs) );

		int nsegs = (int) Math.floor(dist / AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH); 

		double dW = widthExtMcs + thicknessMcs;
		double dH = heightExtMcs + thicknessMcs;
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint3d ptSegI = ptI3d.otherMoveTo(uDir3d, currDist);

        	lsCadEntity3d.addAll( toDxfPipeRectangle(oLayer, oEnt, ptSegI, uDir3d, thicknessMcs, widthExtMcs, heightExtMcs, dW, dH, thicknessMcs) );

    		currDist += AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH;
        }
        return lsCadEntity3d;
    }
	    
    public static ArrayList<DxfCadEntity> toDxfPipeRectangle(CadLayerDef oLayer, CadEntity oEnt, GeomPoint3d ptCenterBase3dMcs, GeomVector3d vDir3dMcs, double dist, double widthIntMcs, double heightIntMcs, double widthExtMcs, double heightExtMcs, double thicknessMcs)
    {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();
		
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
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase0_left, ptBase1_left, ptTop1_left, ptTop0_left, null) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase1_left, ptBase2_left, ptTop2_left, ptTop1_left, null) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase2_left, ptBase3_left, ptTop3_left, ptTop2_left, null) );
		//
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase3_left, ptBase3_right, ptTop3_right, ptTop3_left, null) );
		//
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase2_right, ptBase3_right, ptTop3_right, ptTop2_right, null) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase1_right, ptBase2_right, ptTop2_right, ptTop1_right, null) );
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase0_right, ptBase1_right, ptTop1_right, ptTop0_right, null) );
		//
    	lsCadEntity3d.addAll( DxfUtil.toDxf3DFace(oLayer, ptBase0_left, ptBase0_right, ptTop0_right, ptTop0_left, null) );
		
		return lsCadEntity3d;
    }
    
	/* CAIXA_INSPECAO */	
    
    public static ArrayList<DxfCadEntity> toDxfCaixaInspecaoDrenagem_lowDetailView_planView(
    	CadLayerDef oLayer, 
    	CadEntity oEnt, 
		GeomPoint3d ptIns3dMcs, 
		double sclFact, 
		double radiusExtMcs, 
		double thicknessMcs)
    {
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();

    	double dRadiusIntMcs = radiusExtMcs - thicknessMcs;

    	lsCadEntity2d.addAll( toDxfCircle(oLayer, 256, "HIDDEN", ptIns3dMcs.getX(), ptIns3dMcs.getY(), ptIns3dMcs.getZ(), dRadiusIntMcs) ); 
    	lsCadEntity2d.addAll( toDxfCircle(oLayer, ptIns3dMcs.getX(), ptIns3dMcs.getY(), ptIns3dMcs.getZ(), radiusExtMcs) );
		return lsCadEntity2d;
	}
    
	public static ArrayList<DxfCadEntity> toDxfCaixaInspecaoDrenagem_highDetailView_planView(
		CadLayerDef oLayer, 
		CadEntity oEnt, 
		GeomPoint3d ptIns3dMcs, 
		GeomVector3d vDir3dMcs, 
		double sclFact, 
		double radiusExtMcs, 
		double thicknessMcs,
		double widthMcs,
		double heightMcs,
		double boxThicknessMcs)
	{
		ArrayList<DxfCadEntity> lsCadEntity2d = new ArrayList<DxfCadEntity>();

		GeomPoint2d ptIns2dMcs = new GeomPoint2d(ptIns3dMcs); 
		GeomVector2d vDir = new GeomVector2d(vDir3dMcs);
		    	
		GeomVector2d uDir = vDir.otherUnit();
		GeomVector2d nDir = uDir.otherNorm();
  	
		double dRadiusIntMcs = radiusExtMcs - thicknessMcs;

		double dHeightMcs = (2.0 * boxThicknessMcs) + heightMcs;

		double dWidthMcs = (2.0 * boxThicknessMcs) + widthMcs;
		double dWidthMcs2 = dWidthMcs / 2.0;
  	
		double dHeightIntMcs = heightMcs;
  	
		double dWidthIntMcs = widthMcs;
		double dWidthIntMcs2 = dWidthIntMcs / 2.0;

		//EXTERNAL
		GeomPoint2d pt1 = ptIns2dMcs.otherMoveTo(uDir, radiusExtMcs + (2.0 * boxThicknessMcs));
		GeomPoint2d pt2 = pt1.otherMoveTo(nDir,   dWidthMcs2);
		GeomPoint2d pt3 = pt2.otherMoveTo(uDir, - dHeightMcs);
		GeomPoint2d pt4 = pt3.otherMoveTo(nDir, - dWidthMcs);
		GeomPoint2d pt5 = pt4.otherMoveTo(uDir,   dHeightMcs);
  	
		//INTERNAL
		GeomPoint2d pt1_int = ptIns2dMcs.otherMoveTo(uDir, radiusExtMcs + boxThicknessMcs);
		GeomPoint2d pt2_int = pt1_int.otherMoveTo(nDir,   dWidthIntMcs2);
		GeomPoint2d pt3_int = pt2_int.otherMoveTo(uDir, - dHeightIntMcs);
		GeomPoint2d pt4_int = pt3_int.otherMoveTo(nDir, - dWidthIntMcs);
		GeomPoint2d pt5_int = pt4_int.otherMoveTo(uDir,   dHeightIntMcs);

		//EXTERNAL
    	lsCadEntity2d.addAll( toDxfLine(oLayer, pt1, pt2) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, pt2, pt3) );		
    	lsCadEntity2d.addAll( toDxfLine(oLayer, pt3, pt4) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, pt4, pt5) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, pt5, pt1) );
    	//
    	lsCadEntity2d.addAll( toDxfCircle(oLayer, ptIns2dMcs, radiusExtMcs) );
    	
    	//INTERNAL
    	lsCadEntity2d.addAll( toDxfLine(oLayer, 256, "HIDDEN", pt1_int, pt2_int) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, 256, "HIDDEN", pt2_int, pt3_int) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, 256, "HIDDEN", pt3_int, pt4_int) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, 256, "HIDDEN", pt4_int, pt5_int) );
    	lsCadEntity2d.addAll( toDxfLine(oLayer, 256, "HIDDEN", pt5_int, pt1_int) );
    	//
    	lsCadEntity2d.addAll( toDxfCircle(oLayer, 256, "HIDDEN", ptIns2dMcs, dRadiusIntMcs) );

		return lsCadEntity2d;
	}

	/* NETWORK_LINK */	
	
    public static ArrayList<DxfCadEntity> toDxfCaixaInspecaoDrenagem_netLink_planView(
		CadLayerDef oLayer, 
		CadEntity oEnt, 
    	GeomPoint3d ptIns3dMcs, 
    	double sclFact) 
    {
		ArrayList<DxfCadEntity> lsEntity2d = new ArrayList<DxfCadEntity>();
		
		AppCadMain cad = AppCadMain.getCad();

		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		double arrowLengthSz = AppDefs.ARROWLENGTHSZ_SMALL * sclFact;
		double arrowWidthSz = AppDefs.ARROWWIDTHSZ_SMALL * sclFact;
		double arrowPointSz = AppDefs.ARROWPOINTSZ_SMALL * sclFact;

		CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
		int proximaCI = oCI.getProximaCI();
    	if(proximaCI != AppDefs.NULL_INT) {
    		CadBlockDef oBlk = doc.getCurrBlockDef();        		
    		CadEntity ent2 = oBlk.getEntity(proximaCI);
    		if(ent2 != null) {
	    		if(ent2.getObjType() == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
	    			CadCaixaInspecaoDrenagem oEnt2 = (CadCaixaInspecaoDrenagem)ent2; 
	    			
	    			GeomPoint2d ptIns2dMcs = new GeomPoint2d(ptIns3dMcs);

	    			GeomPoint2d ptProximo2dMcs = new GeomPoint2d(ptIns2dMcs);
		    			
	    			GeomVector2d vDir2d = new GeomVector2d(ptIns2dMcs, ptProximo2dMcs);
		    			
		    		GeomVector2d uDir2d = vDir2d.otherUnit(); 

	    			lsEntity2d.addAll( DxfUtil.toDxfLine(oLayer, ptIns2dMcs, ptProximo2dMcs) );

	    			lsEntity2d.addAll( DxfUtil.toDxfArrow(oLayer, ptIns2dMcs, arrowLengthSz, arrowWidthSz, arrowPointSz, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uDir2d) );
	    		}
    		}
    	}
    	return lsEntity2d;
    }

    /* ARROW */	
	
	public static ArrayList<DxfCadEntity> toDxfArrow(
		CadLayerDef oLayer, 
		GeomPoint2d ptMcs, 
		double arrowLengthMcs, 
		double arrowWidthMcs, 
		double arrowPointMcs, 
		int horizAlign, 
		int vertAlign, 
		GeomVector2d vDir)
	{
		ArrayList<DxfCadEntity> lsEntity2d = new ArrayList<DxfCadEntity>();

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
        
        lsEntity2d.addAll( toDxfArrow(oLayer, ptIns, arrowLengthMcs, arrowWidthMcs, arrowPointMcs, uDir) );
    	return lsEntity2d;
	}
	
	public static ArrayList<DxfCadEntity> toDxfArrow(
		CadLayerDef oLayer, 
		GeomPoint2d ptMcs, 
		double arrowLengthMcs, 
		double arrowWidthMcs, 
		double arrowPointMcs, 
		GeomVector2d vDir)
	{
		ArrayList<DxfCadEntity> lsEntity2d = new ArrayList<DxfCadEntity>();

		double arrowWidthMcs2 = arrowWidthMcs / 2.0;
		double arrowLengthMcs2 = arrowLengthMcs - arrowPointMcs;
		
        GeomPoint2d ptIns = new GeomPoint2d(ptMcs);

        GeomVector2d uDir = vDir.otherUnit();
        GeomVector2d nDir = uDir.otherNorm();

        GeomPoint2d ptI = new GeomPoint2d(ptIns); 
        GeomPoint2d ptF = ptI.otherMoveTo(uDir, arrowLengthMcs); 

        //UP-SIDE
        GeomPoint2d pt0 = ptI.otherMoveTo(nDir, arrowWidthMcs2); 
        GeomPoint2d pt1 = pt0.otherMoveTo(uDir, arrowLengthMcs2); 
        GeomPoint2d pt2 = pt1.otherMoveTo(nDir, arrowWidthMcs2); 

        //DOWN-SIDE
        GeomPoint2d pt3 = ptI.otherMoveTo(nDir, - arrowWidthMcs2); 
        GeomPoint2d pt4 = pt3.otherMoveTo(uDir, arrowLengthMcs2); 
        GeomPoint2d pt5 = pt4.otherMoveTo(nDir, - arrowWidthMcs2); 
        
        //DRAW_ARROW
        lsEntity2d.addAll( toDxfLine(oLayer, ptI, pt0) );
        lsEntity2d.addAll( toDxfLine(oLayer, pt0, pt1) );
        lsEntity2d.addAll( toDxfLine(oLayer, pt1, pt2) );
        lsEntity2d.addAll( toDxfLine(oLayer, pt2, ptF) );
        lsEntity2d.addAll( toDxfLine(oLayer, ptF, pt5) );
        lsEntity2d.addAll( toDxfLine(oLayer, pt5, pt4) );
        lsEntity2d.addAll( toDxfLine(oLayer, pt4, pt3) );
        lsEntity2d.addAll( toDxfLine(oLayer, pt3, ptI) );        

    	return lsEntity2d;
	}
    
}
