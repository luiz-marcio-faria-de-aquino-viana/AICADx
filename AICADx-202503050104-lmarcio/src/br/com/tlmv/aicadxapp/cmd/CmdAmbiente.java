/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdAmbiente.java
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
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompPlanView;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdAmbiente implements CmdBase, Runnable
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
		
		PromptUtil.prompt("Room 2-Walls...");
		
		MainPanel panel = frm.getMainPanel();

		ICompView v = panel.getCurrView();

		CadEntity oEnt1 = PromptUtil.selectObject(AppDefs.OBJTYPE_BIMPAREDE, "Select first wall: ");
		if(oEnt1 == null) return null;

		CadParede oParede1 = (CadParede)oEnt1;

		GeomPoint3d pt3dI1 = oParede1.getPtI();
		GeomPoint3d pt3dF1 = oParede1.getPtF();
		
		CadEntity oEnt2 = PromptUtil.selectObject(AppDefs.OBJTYPE_BIMPAREDE, "Select second wall: ");
		if(oEnt2 == null) return null;

		CadParede oParede2 = (CadParede)oEnt2;

		GeomPoint3d pt3dI2 = oParede2.getPtI();
		GeomPoint3d pt3dF2 = oParede2.getPtF();

		//Calculate Intersection
		
		GeomPoint2d pt2dI = GeomUtil.intersectionOf(pt3dI1, pt3dF1, pt3dI2, pt3dF2); 
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		v.setDragmode(AppDefs.DRAGMODE_DRAGOBJECT);

		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(pt2dI, "Second point: ");
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
		
		v.setDragmode(AppDefs.DRAGMODE_NONE);
			
		result = new InputParamVO();
		result.initEntity(oEnt1, oEnt2, pt3dI, pt3dF);
		
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
		
		//SENTIDO
		
		GeomPoint2d ptI = new GeomPoint2d(oParam.getPt0());
		GeomPoint2d ptF = new GeomPoint2d(oParam.getPt1());

		GeomVector2d vIF = new GeomVector2d(ptI, ptF);

		//PAREDE 1
		CadEntity oEnt1 = oParam.getEnt1();
		oEnt1.reset();

		CadParede oParede1 = (CadParede)oEnt1;
		
		GeomPoint2d ptI1 = new GeomPoint2d(oParede1.getPtI());
		GeomPoint2d ptF1 = new GeomPoint2d(oParede1.getPtF());
		
		GeomVector2d v1 = new GeomVector2d(ptI1, ptF1);

		GeomVector2d u1 = v1.otherUnit();
		GeomVector2d n1 = u1.otherNorm();
		
		double d1 = n1.dotProd(vIF);
		
		//PAREDE 2
		CadEntity oEnt2 = oParam.getEnt2();
		oEnt2.reset();

		CadParede oParede2 = (CadParede)oEnt2;
		
		GeomPoint2d ptI2 = new GeomPoint2d(oParede2.getPtI());
		GeomPoint2d ptF2 = new GeomPoint2d(oParede2.getPtF());
		
		GeomVector2d v2 = new GeomVector2d(ptI2, ptF2);

		GeomVector2d u2 = v2.otherUnit();
		GeomVector2d n2 = u2.otherNorm();
		
		double d2 = n2.dotProd(vIF);

		//
		
		GeomPoint3d pt3dI1 = new GeomPoint3d(ptI1);
		GeomVector3d u3d1 = new GeomVector3d(u1);
		GeomVector3d n3d1 = new GeomVector3d(n1);
		
		GeomPoint3d newPt3dI1 = pt3dI1.otherMoveTo(n3d1, d1);
		GeomPoint3d newPt3dF1 = newPt3dI1.otherMoveTo(u3d1, d2);

		CadParede oNewEnt1 = oParede1.duplicate();
		oNewEnt1.setPtI(newPt3dI1);
		oNewEnt1.setPtF(newPt3dF1);

		//
		
		GeomPoint3d pt3dI2 = new GeomPoint3d(ptI2);
		GeomVector3d u3d2 = new GeomVector3d(u2);
		GeomVector3d n3d2 = new GeomVector3d(n2);
		
		GeomPoint3d newPt3dI2 = pt3dI2.otherMoveTo(n3d2, d2);
		GeomPoint3d newPt3dF2 = newPt3dI2.otherMoveTo(u3d2, - d1);

		CadParede oNewEnt2 = oParede2.duplicate();
		oNewEnt2.setPtI(newPt3dI2);
		oNewEnt2.setPtF(newPt3dF2);
				
		currBlockDef.addEntity(oNewEnt1);
		currBlockDef.addEntity(oNewEnt2);
		
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
