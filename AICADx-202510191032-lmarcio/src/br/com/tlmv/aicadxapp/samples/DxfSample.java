/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DxfSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/05/2025
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
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cmd.CmdDxfIn;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class DxfSample implements ISample 
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

		AppCadMain cad = AppCadMain.getCad();
    	
		//FILENAME
		//
		String name = doc.getName();	
		String fileName = doc.getFileName();	
		
    	MainPanel panel = MainPanel.getMainPanel();
    	
    	MainFrame frm = MainFrame.getMainFrame();
		frm.updateTitle(name);

    	//ExtMin
    	//
    	double xExtMin = 598110.5797039024;
    	double yExtMin = 7463072.230767329;

    	GeomPoint2d ptExtMin = new GeomPoint2d(xExtMin, yExtMin);

    	//ExtMax
    	//
    	double xExtMax = 598337.6821770571;
    	double yExtMax = 7463178.671520648;
    	
    	GeomPoint2d ptExtMax = new GeomPoint2d(xExtMax, yExtMax);
    	
    	String[] args = {
    		"/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/TOPO-MANGARATIBA-R12.dxf"
    	};
    	
    	CmdDxfIn cmd = new CmdDxfIn();
    	cmd.doExecuteCommand(app, frm, cad, doc, args);
    	
    	ICompView cmp = panel.getCurrView();

    	ICadViewBase v = cmp.getCadViewBase();
    	v.zoomWindowMcs(ptExtMin, ptExtMax);
    }

}
