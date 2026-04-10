/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdZoom3DViewArea.java
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
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdZoom3DViewArea extends CmdBase
{
//Public
	
	public CmdZoom3DViewArea() {
		super(AppDefs.ACTION_ZOOM_3DVIEWAREA, false, false);
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
		
		PromptUtil.prompt("3D View (Area)...");

		GeomPoint2d pt2dI = PromptUtil.getFirstCorner2d(this, null, "First corner: ");
		if(pt2dI == null) return null;

		GeomPoint2d pt2dF = PromptUtil.getSecondCorner2d(this, pt2dI, "Second corner: ");
		if(pt2dF == null) return null;
		
		GeomPoint3d ptMin3d = GeomPoint3d.lowerLeftCornerFrom(pt2dI, pt2dF);
		GeomPoint3d ptMax3d = GeomPoint3d.upperRightCornerFrom(pt2dI, pt2dF);
			
		result = new InputParamVO();
		result.initRectangle(ptMin3d, ptMax3d);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

    	AppCadMain cad = AppCadMain.getCad();
    	
		CadDocumentDef doc = this.getDoc();

		ViewTable viewTbl = doc.getViewTable();
		
		ProjectRepoVO projectRepo = doc.getProjectRepo();    				
		
		String name = projectRepo.getName();	
		//String fileName = projectRepo.getFileName();	

		//GEOMPOINT3D
		//
		GeomPoint2d pt2dI = new GeomPoint2d( oParam.getPtMin() );
		GeomPoint2d pt2dF = new GeomPoint2d( oParam.getPtMax() );

		String strName = name + "-3D";
		CompModel3DView oOldView = (CompModel3DView)viewTbl.getView(strName); 
    	if(oOldView == null) {
    		CompView oNewView = viewTbl.new3DView(strName, pt2dI, pt2dF);
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
    		oOldView.setPlanArea(pt2dI, pt2dF);
    		
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
