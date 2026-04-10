/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TransMarSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/09/2025
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

import java.util.Date;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.transmar.cad.CadControleBacklistTransMar;

public class TransMarR1Sample implements ISample 
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
		CadLayerDef oLayer = layTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);

		// LEVEL_TABLE
		//
		LevelTable levelTbl = doc.getLevelTable();
		CadLevel oLevel = levelTbl.getLevel(AppDefs.DEFAULT_LEVELNAME);

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
		//LIMITE_CONVES
		GeomPoint3d oPt3d0 = new GeomPoint3d( 5.0,  5.0, 0.0);
		
		//CADPOINT
		//
		CadControleBacklistTransMar oCtrlBacklist = CadControleBacklistTransMar.create(
			currBlockDef,
			oLayer, 
			oLevel,
			oPt3d0,
			name,
			"",
		    1001,
			new Date());
		currBlockDef.addEntity(oCtrlBacklist);

    }

}
