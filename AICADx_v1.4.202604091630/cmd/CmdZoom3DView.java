/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModel3DView;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class CmdZoom3DView extends CmdBase
{
//Public
	
	public CmdZoom3DView() {
		super(AppDefs.ACTION_ZOOM_3DVIEW, false, false);
	}
	
	/* Methodes */
	
	@Override
	public boolean initCommand() { return true; }

	@Override
	public void finishCommand() {
		this.refreshAll();
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

		ProjectRepoVO projectRepo = doc.getProjectRepo();    				
		
		String name = projectRepo.getName();	
		//String fileName = projectRepo.getFileName();	

		ViewTable viewTbl = doc.getViewTable();
		
		String strName = name + "-3D";
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
