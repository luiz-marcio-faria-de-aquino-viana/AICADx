/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ProjetoCampoSacoDrenagemSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 11/07/2025
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
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdAnotacaoMultiplaCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdCsvImportarDrenagem;

public class ProjetoCampoSacoSample implements ISample 
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
    	if(debugLevel != AppDefs.DEBUG_LEVEL) return;
    	
    	AppMain app = AppMain.getApp();
    	
    	MainFrame frm = MainFrame.getMainFrame();
    	
    	AppCadMain cad = AppCadMain.getCad();
    	
    	CmdCsvImportarDrenagem cmd1 = new CmdCsvImportarDrenagem();
    	cmd1.doExecuteCommand(app, frm, cad, doc, null);
    	
    	CmdAnotacaoMultiplaCaixaInspecaoDrenagem cmd2 = new CmdAnotacaoMultiplaCaixaInspecaoDrenagem();
    	cmd2.doExecuteCommand(app, frm, cad, doc, null);    	
    }

}
