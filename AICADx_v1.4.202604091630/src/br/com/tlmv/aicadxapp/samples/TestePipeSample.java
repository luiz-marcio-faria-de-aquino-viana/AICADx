/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PipeSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/05/2025
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
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class TestePipeSample implements ISample 
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
    	if(debugLevel != AppDefs.DEBUG_LEVEL) return;
    	
		CadBlockDef currBlockDef = doc.getCurrBlockDef();

		// LAYER_TABLE
		//
		LayerTable layTbl = doc.getLayerTable();
		CadLayerDef oLayerPontos = layTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);
		CadLayerDef oLayerTb = layTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TB_DRENAGEM);

		// LEVEL_TABLE
		//
		LevelTable levelTbl = doc.getLevelTable();
		CadLevel oLevel = levelTbl.getLevel(AppDefs.DEFAULT_LEVELNAME);

		GeomVector3d axisZ = GeomUtil.axisZ3d(); 

		//FILENAME
		//
		ProjectRepoVO projectRepo = doc.getProjectRepo();    				
		
		String name = projectRepo.getName();	
		//String fileName = projectRepo.getFileName();	
		
    	MainPanel panel = MainPanel.getMainPanel();
    	
    	MainFrame frm = MainFrame.getMainFrame();
		frm.updateTitle(name);
		
		//GEOMPOINT3D
		//
		GeomPoint3d oPt3d0 = new GeomPoint3d( 2.5,  2.5,  0.0);
		GeomPoint3d oPt3d1 = new GeomPoint3d( 2.5,  2.5,  5.0);
		GeomPoint3d oPt3d2 = new GeomPoint3d( 2.5,  0.0,  5.0);
		GeomPoint3d oPt3d3 = new GeomPoint3d( 2.5, -2.5,  5.0);
		GeomPoint3d oPt3d4 = new GeomPoint3d( 2.5, -5.0,  5.0);
		//
//		GeomPoint3d oPt3d4 = new GeomPoint3d( 4.5, 2.5,  0.0);
//		GeomPoint3d oPt3d5 = new GeomPoint3d( 9.5, 2.5, -2.5);
		//
//		GeomPoint3d oPt3d6 = new GeomPoint3d( 5.5, 2.5,  0.0);
//		GeomPoint3d oPt3d7 = new GeomPoint3d(14.0, 2.5, -5.0);
		//
//		GeomPoint3d oPt3d8 = new GeomPoint3d( 5.5, 2.5,  0.0);
//		GeomPoint3d oPt3d9 = new GeomPoint3d(15.0, 2.5,  2.5);
		//
//		GeomPoint3d oPt3d10 = new GeomPoint3d( 5.5, 2.5, 0.0);
//		GeomPoint3d oPt3d11 = new GeomPoint3d(14.0, 2.5, 5.0);
		
		//PIPE
		//
		//CadPipe oPipe0 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d0, oPt3d1, 1.0);
		//currBlockDef.addEntity(oPipe0);
		
		//CadPipe oPipe1 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d0, oPt3d2, 1.0);
		//currBlockDef.addEntity(oPipe1);
		
		//CadPipe oPipe2 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d0, oPt3d3, 1.0);
		//currBlockDef.addEntity(oPipe2);
		
		CadPipe oPipe3 = CadPipe.create(currBlockDef, oLayerTb, oLevel, oPt3d0, oPt3d4, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), 100.0, 10.0);
		currBlockDef.addEntity(oPipe3);
		
//		CadPipe oPipe1 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d2, oPt3d3, 1.0);
//		currBlockDef.addEntity(oPipe1);
		
//		CadPipe oPipe2 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d4, oPt3d5, 1.0);
//		currBlockDef.addEntity(oPipe2);
		
//		CadPipe oPipe3 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d6, oPt3d7, 1.0);
//		currBlockDef.addEntity(oPipe3);
		
//		CadPipe oPipe4 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d8, oPt3d9, 1.0);
//		currBlockDef.addEntity(oPipe4);
		
//		CadPipe oPipe5 = CadPipe.create(currBlockDef, oLayerTb, null, oPt3d10, oPt3d11, 1.0);
//		currBlockDef.addEntity(oPipe5);

    }

}
