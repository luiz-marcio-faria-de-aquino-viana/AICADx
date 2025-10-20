/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PromptUtil.java
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

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class PromptUtil
{
//Public

	/* WAIT_RESPONSE */

	// COMP_MODEL_VIEW
	//
	public static void waitPickmodeResponse()
	{
		MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();
		while(v.getPickmode() != AppDefs.PICKMODE_NONE ) {
			try {
				Thread.sleep(AppDefs.PICKMODE_WAITTIMEOUTMILI);
			}
			catch(Exception e) { }
		}
	}

	public static void waitSelectmodeResponse()
	{
		MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();
		while(v.getSelectmode() != AppDefs.SELECTMODE_NONE ) {
			try {
				Thread.sleep(AppDefs.SELECTMODE_WAITTIMEOUTMILI);
			}
			catch(Exception e) { }
		}
	}

	public static void waitZoommodeResponse()
	{
		MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();
		while(v.getZoommode() != AppDefs.ZOOMMODE_NONE ) {
			try {
				Thread.sleep(AppDefs.ZOOMMODE_WAITTIMEOUTMILI);
			}
			catch(Exception e) { }
		}
	}

	// COMP_MODEL_VIEW
	//
	public static void waitTextmodeResponse()
	{
		MainPanel panel = MainPanel.getMainPanel();

		CompCommandPrompt cmdPrompt = panel.getCommandPrompt();
		while(cmdPrompt.getTextmode() != AppDefs.TEXTMODE_NONE ) {
			try {
				Thread.sleep(AppDefs.TEXTMODE_WAITTIMEOUTMILI);
			}
			catch(Exception e) { }
		}
	}

	/* PROMPT */

	public static void prompt(String msg)
	{
		MainPanel panel = MainPanel.getMainPanel();
		if(panel == null) return;
		
		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(msg);
	}	
	
	public static void promptCoords3d(double xVal, double yVal, double zVal)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		String strMsg = String.format(
			"Coords 3D: [ %s, %s, %s ]",
			nf3.format(xVal),
			nf3.format(yVal),
			nf3.format(zVal) );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptCoords3d(GeomPoint3d ptVal)
	{
		PromptUtil.promptCoords3d(ptVal.getX(), ptVal.getY(), ptVal.getZ());
	}	
	
	public static void promptCoords2d(double xVal, double yVal)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		String strMsg = String.format(
			"Coords 2D: [ %s, %s ]",
			nf3.format(xVal),
			nf3.format(yVal) );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptCoords2d(GeomPoint2d ptVal)
	{
		PromptUtil.promptCoords2d(ptVal.getX(), ptVal.getY());
	}	
	
	public static void promptEntity(CadEntity oEnt)
	{
		if(oEnt == null) return;
		
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		Class c = oEnt.getClass();
		String className = c.getSimpleName();

		CadLayerDef oLayer = oEnt.getLayer();
		String layerName = oLayer.getName();
		
		String strMsg = String.format(
			"Selected: [ ObjectId: %s; ObjectType: %s; Layer: %s ]",
			oEnt.getObjectId(),
			className,
			layerName );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptDist(double val)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		String strMsg = String.format(
			"Distance (m): %s m",
			nf3.format(val) );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptAltura(double val)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		String strMsg = String.format(
			"Altura (m): %s m",
			nf3.format(val) );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptAngle(double val)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		String strMsg = String.format(
			"Angle (Degrees): %s",
			nf3.format(val) );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptAngles(double startAngleDegrees, double endAngleDegrees)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		double angleDegrees = startAngleDegrees - endAngleDegrees;
		
		String strMsg = String.format(
			"Angle (Degrees): %s - Start Angle (Degrees): %s; End Angle (Degrees): %s",
			nf3.format(angleDegrees),
			nf3.format(startAngleDegrees),
			nf3.format(endAngleDegrees));

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}	
	
	public static void promptVal(String str, double val)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		String strMsg = String.format(
			"%s: %s",
			str,
			nf3.format(val) );

		CompCommandPrompt commandPrompt = panel.getCommandPrompt();		
		commandPrompt.writeMessage(strMsg);
	}

	/* KEYWORDxxx */

	public static PromptOptionVO getKeywordResponse(ArrayList<PromptOptionVO> lsPromptOptions, String keyEntered)
	{
		for(PromptOptionVO o : lsPromptOptions) {
			if( keyEntered.equalsIgnoreCase(o.getKeyOption()) )
				return o;
		}
		return null;
	}	

	public static String getKeywordPrompt(ArrayList<PromptOptionVO> lsPromptOptions)
	{
		StringBuilder sbResult = new StringBuilder();
		for(PromptOptionVO o : lsPromptOptions) {
			String strOption = o.toStringOption();
			if( o.isDefaultOption() )
				strOption = "<" + strOption + ">";
			if(sbResult.length() > 0)
				sbResult.append("/");
			sbResult.append(strOption);
		}
		return sbResult.toString();
	}	

	public static PromptOptionVO getKeyword(ArrayList<PromptOptionVO> lsPromptOptions, String msg)
	{
		MainPanel panel = MainPanel.getMainPanel();

		String strOption = PromptUtil.getKeywordPrompt(lsPromptOptions);
		
		PromptUtil.prompt(msg + strOption);

		CompCommandPrompt cmdPrompt = panel.getCommandPrompt();
		cmdPrompt.resetTextModeVars();
		
		cmdPrompt.setTextmode(AppDefs.TEXTMODE_ENTERTEXT);
		PromptUtil.waitTextmodeResponse();

		String keyEntered = cmdPrompt.getTextVal();
		
		PromptOptionVO result = PromptUtil.getKeywordResponse(lsPromptOptions, keyEntered);
		return result;
	}	
	
	/* TEXTxxx */
	
	public static String getText(String msg)
	{
		String result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		CompCommandPrompt cmdPrompt = panel.getCommandPrompt();
		cmdPrompt.resetTextModeVars();
		
		cmdPrompt.setTextmode(AppDefs.TEXTMODE_ENTERTEXT);
		PromptUtil.waitTextmodeResponse();

		result = cmdPrompt.getTextVal();
		return result;
	}
	
	/* SELECTxxx */
	
	public static CadEntity selectObject(String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetSelectModeVars();
		
		v.setSelectmode(AppDefs.SELECTMODE_SELECTOBJECT);
		PromptUtil.waitSelectmodeResponse();

		GeomPoint2d ptCursorMcs = v.getCurrSelectpointMcs();

		CadEntity oEnt = v.selectEntity(ptCursorMcs);
		if(oEnt == null) return null;
		oEnt.setSelected(false);
		
		v.resetSelectModeVars();
		return oEnt;
	}
	
	public static CadEntity selectObject(int objtype, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetSelectModeVars();
		v.setSelectobjtype(objtype);
		
		v.setSelectmode(AppDefs.SELECTMODE_SELECTOBJECT);
		PromptUtil.waitSelectmodeResponse();

		GeomPoint2d ptCursorMcs = v.getCurrSelectpointMcs();

		CadEntity oEnt = v.selectEntity(ptCursorMcs);
		if(oEnt == null) return null;

		oEnt.setSelected(false);

		v.resetSelectModeVars();
		return oEnt;
	}
	
	/* GETxxx - 2D */
	
	public static GeomPoint2d getPoint2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);
		
		v.setPickmode(AppDefs.PICKMODE_PICKPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getFirstPoint2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);

		v.setPickmode(AppDefs.PICKMODE_PICKFIRSTPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getSecondPoint2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getSecondPoint2d(double dist, GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setRefPickpointMcs(dist);
		v.setBasePickpointMcs(ptBase);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getScalePoint2d(double dist, GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setRefPickpointMcs(dist);
		v.setBasePickpointMcs(ptBase);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getSecondPoint2d(GeomPoint2d ptBase, ArrayList<GeomPoint2d> lsPts, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);
		v.setLsPtsPickpointMcs(lsPts);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getFirstCorner2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);

		v.setPickmode(AppDefs.PICKMODE_PICKFIRSTCORNER);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getSecondCorner2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDCORNER);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getPointAtDir2d(GeomPoint2d ptBase2d, GeomVector2d vDir, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase2d);
		v.setVDirPickpointMcs(vDir);

		v.setPickmode(AppDefs.PICKMODE_PICKPOINTATDIR);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}

	public static GeomPoint2d getCenter2d(GeomPoint2d ptStartPoint, GeomPoint2d ptEndPoint, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setStartPickpointMcs(ptStartPoint);
		v.setEndPickpointMcs(ptEndPoint);

		v.setPickmode(AppDefs.PICKMODE_PICKCENTERPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getStartPoint2d(GeomPoint2d ptCenterPoint, GeomPoint2d ptEndPoint, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setCenterPickpointMcs(ptCenterPoint);
		v.setEndPickpointMcs(ptEndPoint);

		v.setPickmode(AppDefs.PICKMODE_PICKARCSTARTPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static GeomPoint2d getEndPoint2d(GeomPoint2d ptCenterPoint, GeomPoint2d ptStartPoint, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setCenterPickpointMcs(ptCenterPoint);
		v.setStartPickpointMcs(ptStartPoint);

		v.setPickmode(AppDefs.PICKMODE_PICKARCENDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		result = v.getCurrPickpointMcs();
		return result;
	}
	
	public static Double getRadius(GeomPoint2d ptCenterPoint, String msg)
	{
		Double result = null;
		
		GeomPoint2d ptCenterRef = new GeomPoint2d(0.0, 0.0);
		if(ptCenterPoint != null)
			ptCenterRef = new GeomPoint2d(ptCenterPoint);
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setCenterPickpointMcs(ptCenterRef);
		
		v.setPickmode(AppDefs.PICKMODE_PICKRADIUS);
		PromptUtil.waitPickmodeResponse();
		
		GeomPoint2d ptRadius = v.getCurrPickpointMcs();
		if(ptRadius == null) return null;
		
		result = ptCenterRef.distTo(ptRadius);
		return result;
	}
	
	public static Double getDouble(GeomPoint2d ptRefPoint, String msg)
	{
		Double result = null;
		
		GeomPoint2d ptRef = new GeomPoint2d(0.0, 0.0);
		if(ptRefPoint != null)
			ptRef = new GeomPoint2d(ptRefPoint);
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptRef);
		
		v.setPickmode(AppDefs.PICKMODE_PICKPOINT);
		PromptUtil.waitPickmodeResponse();
		
		GeomPoint2d ptDist = v.getCurrPickpointMcs();
		if(ptDist == null) return null;
		
		result = ptRef.distTo(ptDist);
		return result;
	}
	
	public static Double getTextHeight(String msg)
	{
		Double result = null;

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		CompCommandPrompt cmdPrompt = panel.getCommandPrompt();
		cmdPrompt.resetTextModeVars();
		
		cmdPrompt.setTextmode(AppDefs.TEXTMODE_ENTERTEXT);
		PromptUtil.waitTextmodeResponse();

		String strTextVal = cmdPrompt.getTextVal();
		if(strTextVal == null) return null;		
		
		result = StringUtil.safeDbl(nf3, strTextVal);
		return result;
	}
	
	public static Double getTextHeight(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d ptBase2d = new GeomPoint2d(0.0, 0.0);
		if(ptBase != null)
			ptBase2d = new GeomPoint2d(ptBase);
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		GeomPoint2d ptTextHeight = v.getCurrPickpointMcs();
		if(ptTextHeight == null) return null;
		
		Double textHeightMcs = ptBase.distTo(ptTextHeight);
		return textHeightMcs;
	}
	
	public static Double getAngleDef(GeomPoint2d ptCenterPointer, GeomPoint2d ptStartPoint, String msg)
	{
		Double result = null;
		
		GeomPoint2d ptCenterRef = new GeomPoint2d(0.0, 0.0);
		if(ptCenterPointer != null)
			ptCenterRef = new GeomPoint2d(ptCenterPointer);
		
		GeomPoint2d ptStartPointRef = new GeomPoint2d(1.0, 0.0);
		if(ptStartPoint != null)
			ptStartPointRef = new GeomPoint2d(ptStartPoint);
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setCenterPickpointMcs(ptCenterRef);
		v.setStartPickpointMcs(ptStartPointRef);
		
		v.setPickmode(AppDefs.PICKMODE_PICKANGLE);
		PromptUtil.waitPickmodeResponse();

		GeomPoint2d ptEndPoint = v.getCurrPickpointMcs();
		if(ptEndPoint == null) return null;
		
		result = GeomUtil.angleFromArc(ptCenterRef, ptStartPointRef, ptEndPoint);
		return result;
	}
	
	/* GETxxx - 3D */	
	
	public static GeomPoint3d getStartPoint3d(GeomPoint2d ptCenterPoint, GeomPoint2d ptEndPoint, String msg)
	{
		GeomPoint3d ptResult3d = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setCenterPickpointMcs(ptCenterPoint);
		v.setEndPickpointMcs(ptEndPoint);

		v.setPickmode(AppDefs.PICKMODE_PICKARCSTARTPOINT);
		PromptUtil.waitPickmodeResponse();
		
		GeomPoint2d ptResult2d = v.getCurrPickpointMcs();
		double zPtResult3d = v.getCurrPickpointZMcs();
		
		if(ptResult2d != null) {
			ptResult3d = new GeomPoint3d(ptResult2d.getX(), ptResult2d.getY(), zPtResult3d);
		}
		return ptResult3d;
	}

	public static GeomPoint3d getSecondPoint3d(GeomPoint3d ptBase, ArrayList<GeomPoint3d> lsPts, String msg)
	{
		GeomPoint3d ptResult3d = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		GeomPoint2d ptBase2d = new GeomPoint2d(ptBase);
		//double z = ptBase.getZ();
		
		ArrayList<GeomPoint2d> lsPts2 = GeomUtil.copyPt3dTo2dList(lsPts);
		
		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase2d);
		v.setLsPtsPickpointMcs(lsPts2);
		
		v.setPickmode(AppDefs.PICKMODE_PICKSECONDPOINT);
		PromptUtil.waitPickmodeResponse();
		
		GeomPoint2d ptResult2d = v.getCurrPickpointMcs();
		double zPtResult3d = v.getCurrPickpointZMcs();

		if(ptResult2d != null) {
			ptResult3d = new GeomPoint3d(ptResult2d.getX(), ptResult2d.getY(), zPtResult3d);
		}
		return ptResult3d;
	}
	
	public static GeomPoint3d getPoint3d(GeomPoint3d ptBase, String msg)
	{
		GeomPoint3d ptResult3d = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		GeomPoint2d ptBase2d = new GeomPoint2d(ptBase);
		//double z = ptBase.getZ();
		
		ICompView v = panel.getCurrView();
		v.resetPickModeVars();
		v.setBasePickpointMcs(ptBase2d);

		v.setPickmode(AppDefs.PICKMODE_PICKPOINT);
		PromptUtil.waitPickmodeResponse();
		
		GeomPoint2d ptResult2d = v.getCurrPickpointMcs();
		double zPtResult3d = v.getCurrPickpointZMcs();
		
		if(ptResult2d != null) {
			ptResult3d = new GeomPoint3d(ptResult2d.getX(), ptResult2d.getY(), zPtResult3d);
		}
		return ptResult3d;
	}
	
	/* ZOOMxxx */
	
	public static GeomPoint2d zoomPan(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetZoomModeVars();
		v.setBaseZoompointMcs(ptBase);
		
		v.setZoommode(AppDefs.ZOOMMODE_PAN);
		PromptUtil.waitZoommodeResponse();
		
		result = v.getCurrZoompointMcs();
		return result;
	}
	
	public static GeomPoint2d zoomFirstCorner2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetZoomModeVars();
		v.setBaseZoompointMcs(ptBase);

		v.setZoommode(AppDefs.ZOOMMODE_ZOOMFIRSTCORNER);
		PromptUtil.waitZoommodeResponse();
		
		result = v.getCurrZoompointMcs();
		return result;
	}
	
	public static GeomPoint2d zoomSecondCorner2d(GeomPoint2d ptBase, String msg)
	{
		GeomPoint2d result = null;
		
		MainPanel panel = MainPanel.getMainPanel();

		PromptUtil.prompt(msg);

		ICompView v = panel.getCurrView();
		v.resetZoomModeVars();
		v.setBaseZoompointMcs(ptBase);
		
		v.setZoommode(AppDefs.ZOOMMODE_ZOOMSECONDCORNER);
		PromptUtil.waitZoommodeResponse();
		
		result = v.getCurrZoompointMcs();
		return result;
	}
		
}
