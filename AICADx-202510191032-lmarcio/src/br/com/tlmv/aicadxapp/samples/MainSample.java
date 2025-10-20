/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.samples;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;

public class MainSample implements ISample 
{
//Public
	
    public void initSampleData()
    {
    	AppCadMain cad = AppCadMain.getCad();

    	CadDocumentDef oNewDoc = cad.newCadDocumentDef();
    	if(oNewDoc != null) {
    		ViewTable viewTbl = oNewDoc.getViewTable();
    		CompView oNewView = viewTbl.newPlanView(oNewDoc.getName(), 0);
    		
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
