/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdParede.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadParede;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdParede implements CmdBase, Runnable
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
		
		PromptUtil.prompt("Adding new Wall...");

		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(null, "Start point: ");
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);

		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(pt2dI, "End point: ");
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
			
		result = new InputParamVO();
		result.initLine(pt3dI, pt3dF);
		
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
		GeomPoint3d pt3dI = oParam.getPt0(); 
		GeomPoint3d pt3dF = oParam.getPt1(); 
		
		//CADLINE
		//
		LayerTable oTbl = doc.getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_ALVE);
		
		CadParede oParede = CadParede.create(
			oLayer,
			AppDefs.WALLTYPE_BASICA,
			AppDefs.WALLHEIGHT_300CM,
			AppDefs.WALLWIDTH_10CM, 
			pt3dI, 
			pt3dF);
		currBlockDef.addEntity(oParede);
		//
		oParede.addAcabamentoDef(AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_PINTURA);

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
