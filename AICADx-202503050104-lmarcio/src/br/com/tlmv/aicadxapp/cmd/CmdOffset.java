/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdOffset.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 25/02/2025
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
import br.com.tlmv.aicadxapp.cad.CadBox3D;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadJanela;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPDupla;
import br.com.tlmv.aicadxapp.cad.CadParede;
import br.com.tlmv.aicadxapp.cad.CadPiso;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.CadPorta;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdOffset implements CmdBase, Runnable
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
		
		PromptUtil.prompt("Offset object...");
		
		MainPanel panel = frm.getMainPanel();

		ICompView v = panel.getCurrView();

		CadEntity oEnt = PromptUtil.selectObject("Select object: ");
		if(oEnt == null) return null;

		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(null, "Base point: ");
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		v.setDragmode(AppDefs.DRAGMODE_DRAGOBJECT);

		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(pt2dI, "Second point: ");
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
		
		v.setDragmode(AppDefs.DRAGMODE_NONE);
			
		result = new InputParamVO();
		result.initEntity(oEnt, pt3dI, pt3dF);
		
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
		
		//CADPOINT
		//
		CadEntity oEnt = oParam.getEnt1();
		oEnt.reset();

		GeomPoint3d ptI = oParam.getPt0();
		GeomPoint3d ptF = oParam.getPt1();
		
		GeomVector3d vIF = new GeomVector3d(ptI, ptF); 
		double dist = 0.5;	// distance = 0.5 metros (default)
		
		if(oEnt != null) {

			//CAD
			//
			if(oEnt.getObjType() == AppDefs.OBJTYPE_POINT) {
				CadPoint o = (CadPoint)oEnt;
				
				CadPoint oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_LINE) {
				CadLine o = (CadLine)oEnt;
				
				CadLine oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_POLYGON) {
				CadPolygon o = (CadPolygon)oEnt;
				
				CadPolygon oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_RECTANGLE) {
				CadRectangle o = (CadRectangle)oEnt;
				
				CadRectangle oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_CIRCLE) {
				CadCircle o = (CadCircle)oEnt;
				
				CadCircle oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_ARC) {
				CadArc o = (CadArc)oEnt;
				
				CadArc oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_TEXT) {
				CadText o = (CadText)oEnt;
				
				CadText oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			//CAD3D
			//
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BOX3D) {
				CadBox3D o = (CadBox3D)oEnt;
				
				CadBox3D oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			//BIM
			//
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPISO) {
				CadPiso o = (CadPiso)oEnt;
				
				//CadPiso oNewEnt = o.offsetTo(ptI, vIF, dist);
				//currBlockDef.addEntity(oNewEnt);
				PromptUtil.prompt("[DEMO VERSION] Command not implemented!");
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
				CadParede o = (CadParede)oEnt;
				
				CadParede oNewEnt = o.offsetTo(ptI, vIF, dist);
				currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPORTA) {
				CadPorta o = (CadPorta)oEnt;
				
				//CadPorta oNewEnt = o.offsetTo(ptI, vIF, dist);
				//currBlockDef.addEntity(oNewEnt);
				PromptUtil.prompt("[DEMO VERSION] Command not implemented!");
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPDUPLA) {
				CadPDupla o = (CadPDupla)oEnt;
				
				//CadPDupla oNewEnt = o.offsetTo(ptI, vIF, dist);
				//currBlockDef.addEntity(oNewEnt);
			}
			else if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMJANELA) {
				CadJanela o = (CadJanela)oEnt;
				
				//CadJanela oNewEnt = o.offsetTo(ptI, vIF, dist);
				//currBlockDef.addEntity(oNewEnt);
				PromptUtil.prompt("[DEMO VERSION] Command not implemented!");
			}
		}
		
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
