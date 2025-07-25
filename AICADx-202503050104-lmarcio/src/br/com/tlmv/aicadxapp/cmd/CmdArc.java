/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdArc.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdArc implements CmdBase, Runnable
{
//Private
	private AppMain app = null;
	private MainFrame frm = null;
	private AppCadMain cad = null;
	private CadDocumentDef doc = null;

	private Thread thread = null;
	private boolean bRunning = false;
	
	/* Methodes */
	
	private InputParamVO promptInputParam(MainFrame frm)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Arc...");

		GeomPoint2d ptStartPoint2d = PromptUtil.getStartPoint2d(null, null, "Start point: ");
		if(ptStartPoint2d == null) return null;
		
		GeomPoint2d ptCenter2d = PromptUtil.getCenter2d(ptStartPoint2d, null, "Center point: ");
		if(ptCenter2d == null) return null;

		double radius = ptCenter2d.distTo(ptStartPoint2d);
		PromptUtil.promptDist(radius);
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter2d);

		GeomPoint2d ptEndPoint2d = PromptUtil.getEndPoint2d(ptCenter2d, ptStartPoint2d, "End point: ");
		if(ptEndPoint2d == null) return null;

        double startAngleRad = GeomUtil.angleFromAxisX(ptCenter2d, ptStartPoint2d);
        double endAngleRad = GeomUtil.angleFromAxisX(ptCenter2d, ptEndPoint2d);
        if( (endAngleRad >= 0.0) && (endAngleRad < startAngleRad) )
        	endAngleRad = AppDefs.MATHVAL_2PI + endAngleRad;

		double startAngleDegrees = GeomUtil.convertRadToDegrees(startAngleRad);
		double endAngleDegrees = GeomUtil.convertRadToDegrees(endAngleRad);

		PromptUtil.promptAngles(startAngleDegrees, endAngleDegrees);
		
		result = new InputParamVO();
		result.initArc(ptCenter3d, radius, startAngleDegrees, endAngleDegrees);
		
		MainPanel panel = frm.getMainPanel();

		ICompView v = panel.getCurrView();
		v.repaintAll();
		
		return result;
	}
	
//Public
	
	@Override
	public int executeCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc) 
	{
		this.app = app;
		this.frm = frm;
		this.cad = cad;
		this.doc = doc;
		
		this.thread = new Thread(this);
		this.thread.start();
		
		return AppDefs.RSOK;
	}

	/* THREADS */
	
	@Override
	public void run() {
		this.bRunning = true;
		
		InputParamVO oParam = this.promptInputParam(frm);
		if(oParam == null) return;
		
		CadBlockDef currBlockDef = doc.getCurrBlockDef();
		
		//GEOMPOINT3D
		//
		GeomPoint3d ptCenter3d = oParam.getPtCenter(); 
		double radius = oParam.getRadius(); 
		double startAngle = oParam.getStartAngle();
		double endAngle = oParam.getEndAngle();		
		
		//CADLINE
		//
		LayerTable oTbl = doc.getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
		
		CadArc oArc = CadArc.create(oLayer, ptCenter3d, radius, startAngle, endAngle);
		currBlockDef.addEntity(oArc);

		//BLIPS
		//
		MainPanel panel = MainPanel.getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();

		this.bRunning = false;		
	}
	
	/* Getters/Setters */
	
	public AppMain getApp() {
		return app;
	}

	public MainFrame getFrm() {
		return frm;
	}

	public AppCadMain getCad() {
		return cad;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public boolean isbRunning() {
		return bRunning;
	}

}
