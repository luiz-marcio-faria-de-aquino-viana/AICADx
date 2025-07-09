/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdPorta.java
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
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadParede;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPorta;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdPorta implements CmdBase, Runnable
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
		
		PromptUtil.prompt("Adding new Door...");

		MainPanel panel = frm.getMainPanel();

		ICompView v = panel.getCurrView();

		CadEntity oEnt = PromptUtil.selectObject(AppDefs.OBJTYPE_BIMPAREDE, "Select wall: ");
		if(oEnt == null) return null;

		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
			CadParede oParede = (CadParede)oEnt;

			GeomPoint2d ptI2d = new GeomPoint2d(oParede.getPtI());
			GeomPoint2d ptF2d = new GeomPoint2d(oParede.getPtF());
			
			GeomVector2d vIF2d = new GeomVector2d(ptI2d, ptF2d);
			GeomPoint2d ptLocation2d = PromptUtil.getPointAtDir2d(ptI2d, vIF2d, "Set the door location: ");
			if(ptLocation2d == null) return null;

			GeomPoint3d ptLocation3d = new GeomPoint3d(ptLocation2d);
			
			result = new InputParamVO();
			result.initEntity(oEnt, ptLocation3d);
		}

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

		//INPUT_PARAM
		//
		CadEntity oEnt = oParam.getEnt1();
		oEnt.reset();
		
		GeomPoint3d ptIns3d = oParam.getPt0(); 

		GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);

		if(oEnt != null) {
			if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
				CadParede oParede = (CadParede)oEnt;

				GeomPoint2d ptI2d = new GeomPoint2d(oParede.getPtI());
				GeomPoint2d ptF2d = new GeomPoint2d(oParede.getPtF());
				
				double dist = ptI2d.distTo(ptIns2d);
				
				//CADPORTA
				//
				LayerTable oTbl = doc.getLayerTable();

				CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PORTA);
				
				CadPorta oPorta = CadPorta.create(
					oLayer,
					AppDefs.DOORTYPE_BASICA,
			    	AppDefs.DOORHEIGHT_210CM,
			    	AppDefs.DOORWIDTH_80CM, 
			    	AppDefs.DOORWEIGHT_30MM,
			    	oParede,
			    	dist,
			    	AppDefs.DOORDIR_PT0,
			    	AppDefs.DOORFINISHDEF_WOOD);
				currBlockDef.addEntity(oPorta);
			}
		}
		
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
