/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MainSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/05/2025
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

package br.com.tlmv.aicadxapp.samples;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class MainSample implements ISample 
{
//Public
	
    public void initSampleData()
    {
    	AppCadMain cad = AppCadMain.getCad();

    	CadDocumentDef oNewDoc = cad.newCadDocumentDef();
    	if(oNewDoc != null) {
    		ProjectRepoVO projectRepo = oNewDoc.getProjectRepo();    				
    		
    		String name = projectRepo.getName();	
    		//String fileName = projectRepo.getFileName();	

    		ViewTable viewTbl = oNewDoc.getViewTable();
    		CompView oNewView = viewTbl.newPlanView(name, 0);
    		
        	MainPanel panel = MainPanel.getMainPanel();
        	panel.addNewView(oNewDoc, oNewView);
        	
		    this.initSampleData(AppDefs.DEBUG_LEVEL, oNewDoc);
    	}
    }	
	
    public void initSampleData(int debugLevel, CadDocumentDef doc)
    {
    	if(debugLevel == AppDefs.DEBUG_LEVEL00) return;
    	
		AppCadMain cad = AppCadMain.getCad();
		//
		(new BasicSample()).initSampleData(AppDefs.DEBUG_LEVEL01, doc);
		(new Box3DSample()).initSampleData(AppDefs.DEBUG_LEVEL02, doc);
		(new Cilinder3DSample()).initSampleData(AppDefs.DEBUG_LEVEL03, doc);
		(new Cone3DSample()).initSampleData(AppDefs.DEBUG_LEVEL04, doc);
		(new TroncoCone3DSample()).initSampleData(AppDefs.DEBUG_LEVEL05, doc);
		(new Sphere3DSample()).initSampleData(AppDefs.DEBUG_LEVEL06, doc);
		(new Torus3DSample()).initSampleData(AppDefs.DEBUG_LEVEL07, doc);
		(new DxfSample()).initSampleData(AppDefs.DEBUG_LEVEL09, doc);
		(new LineSample()).initSampleData(AppDefs.DEBUG_LEVEL10, doc);
		(new PointSample()).initSampleData(AppDefs.DEBUG_LEVEL11, doc);
		(new DrenagemSample()).initSampleData(AppDefs.DEBUG_LEVEL16, doc);
		(new PipeSample()).initSampleData(AppDefs.DEBUG_LEVEL17, doc);
		(new TestePipeSample()).initSampleData(AppDefs.DEBUG_LEVEL18, doc);
		(new ProjetoCampoSacoSample()).initSampleData(AppDefs.DEBUG_LEVEL19, doc);
		(new TesteDrenagemSample()).initSampleData(AppDefs.DEBUG_LEVEL20, doc);
		(new PipeSample()).initSampleData(AppDefs.DEBUG_LEVEL21, doc);
		//
		//*** DEBUG_LEVEL ***
		//
		
    }

}
