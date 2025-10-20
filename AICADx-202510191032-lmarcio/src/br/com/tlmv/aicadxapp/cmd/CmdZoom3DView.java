/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdZoom3DView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/07/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModel3DView;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.samples.BasicSample;
import br.com.tlmv.aicadxapp.samples.Box3DSample;
import br.com.tlmv.aicadxapp.samples.Cilinder3DSample;
import br.com.tlmv.aicadxapp.samples.Cone3DSample;
import br.com.tlmv.aicadxapp.samples.DrenagemSample;
import br.com.tlmv.aicadxapp.samples.DxfSample;
import br.com.tlmv.aicadxapp.samples.LineSample;
import br.com.tlmv.aicadxapp.samples.PipeSample;
import br.com.tlmv.aicadxapp.samples.PointSample;
import br.com.tlmv.aicadxapp.samples.ProjetoCampoSacoSample;
import br.com.tlmv.aicadxapp.samples.Sphere3DSample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemSample;
import br.com.tlmv.aicadxapp.samples.TestePipeSample;
import br.com.tlmv.aicadxapp.samples.Torus3DSample;
import br.com.tlmv.aicadxapp.samples.TroncoCone3DSample;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdZoom3DView extends CmdBase
{
//Public
	
	public CmdZoom3DView() {
		super(AppDefs.ACTION_ZOOM_3DVIEW, false, false);
	}
	
	/* Methodes */
	
	@Override
	public void initCommand() { }

	@Override
	public void finishCommand() {
		MainPanel panel = (MainPanel)this.getFrm().getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("3D View...");

		result = new InputParamVO();
		return result;
	}
	
//	@Override
//	public void doCommand() {
//		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
//		if(oParam == null) return;
//
//    	AppCadMain cad = AppCadMain.getCad();
//    	
//		CadDocumentDef doc = this.getDoc();
//
//		ViewTable viewTbl = doc.getViewTable();
//
//		String strName = doc.getName() + "-3D";
//		CompView oNewView = viewTbl.new3DView(strName);
//    	if(oNewView != null) {
//	    	MainPanel panel = MainPanel.getMainPanel();
//	    	panel.addNewView(doc, oNewView);
//    	}
//	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

    	AppCadMain cad = AppCadMain.getCad();
    	
		CadDocumentDef doc = this.getDoc();

		ViewTable viewTbl = doc.getViewTable();
		
		String strName = doc.getName() + "-3D";
		CompModel3DView oOldView = (CompModel3DView)viewTbl.getView(strName); 
    	if(oOldView == null) {
    		CompView oNewView = viewTbl.new3DView(strName);
    		if(oNewView != null) {
		    	MainPanel panel = MainPanel.getMainPanel();
		    	panel.addNewView(doc, oNewView);
    		}
    	}
    	else {
    		double wScr = oOldView.getWidth();
			double hScr = oOldView.getHeight();

			if( (wScr < AppDefs.MATHPREC_MIN) && (hScr < AppDefs.MATHPREC_MIN) )
				return;
    		
			// RE-INIT VIEW
			//
    		oOldView.setPlanArea(null, null);		// remove preset area
    		
	    	GeomDimension3d oDim3d = oOldView.getEnvelop3d();	    			
			
	    	GeomPoint3d ptCentroid3d = new GeomPoint3d( oDim3d.getPtCentroid() );
	    	GeomPoint3d ptMin3d = new GeomPoint3d( oDim3d.getPtMin() );
	    	GeomPoint3d ptMax3d = new GeomPoint3d( oDim3d.getPtMax() );
			
	    	double modelDist = ptMin3d.distTo(ptMax3d);
	    	double obsDist = modelDist * AppDefs.MODEL_DIST_16X;

	    	ICadViewBase v = oOldView.getCadViewBase();
			if(v == null) {
				oOldView.initCadView(wScr, hScr, ptCentroid3d, modelDist, obsDist);
			}
			else {
				oOldView.resetCadView(wScr, hScr, ptCentroid3d, modelDist, obsDist);
			}
			
			oOldView.repaint();
    	}
	}

}
