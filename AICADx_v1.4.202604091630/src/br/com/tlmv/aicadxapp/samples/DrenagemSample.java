/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DrenagemSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/04/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class DrenagemSample implements ISample 
{
//Private
	
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
		
	private void updateData(CadCaixaInspecaoDrenagem oCI, int numPv, int numEstaca, double distEstaca)
	{
		String strEstaca = String.format("%s + %s m", numEstaca, distEstaca);
		String strPv = String.format("PV-%s", numPv);
		
		oCI.setNumEstaca(numEstaca);
		oCI.setDistEstaca(distEstaca);
		oCI.setEstaca(strEstaca);
		oCI.setLocalId(DrenagemCalc.IDFLOCAL_SANTACRUZ_VAL);
		oCI.setLocal(DrenagemCalc.IDFLOCAL_SANTACRUZ_STR);
		oCI.setPv(strPv);
		oCI.setCoefImper(0.8);
	}
	
//Public
	
    public void initSampleData(int debugLevel, CadDocumentDef doc)
    {
    	if(debugLevel != AppDefs.DEBUG_LEVEL) return;
    	
		CadBlockDef currBlockDef = doc.getCurrBlockDef();

		// LAYER_TABLE
		//
		LayerTable oTbl = doc.getLayerTable();
		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);

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
		GeomPoint3d oPt3d0 = new GeomPoint3d(2.0, 14.0,  1.5);
		GeomPoint3d oPt3d1 = new GeomPoint3d(3.0, 12.0,  1.0);
		GeomPoint3d oPt3d2 = new GeomPoint3d(4.0, 10.0,  0.5);
		GeomPoint3d oPt3d3 = new GeomPoint3d(5.0,  8.0,  0.0);
		GeomPoint3d oPt3d4 = new GeomPoint3d(6.0,  6.0, -0.5);
		GeomPoint3d oPt3d5 = new GeomPoint3d(7.0,  4.0, -1.0);
		GeomPoint3d oPt3d6 = new GeomPoint3d(8.0,  2.0, -1.5);
		
		//MODCAIXAINSPECAO
		//
		CadCaixaInspecaoDrenagem oCI0 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d0); 
		this.updateData(oCI0, 1, 1, 0.0);
		currBlockDef.addEntity(oCI0);

		CadCaixaInspecaoDrenagem oCI1 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d1); 
		this.updateData(oCI1, 2, 2, 0.5);
		currBlockDef.addEntity(oCI1);

		CadCaixaInspecaoDrenagem oCI2 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d2); 
		this.updateData(oCI2, 3, 3, 1.0);
		currBlockDef.addEntity(oCI2);

		CadCaixaInspecaoDrenagem oCI3 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d3); 
		this.updateData(oCI3, 4, 4, 1.5);
		currBlockDef.addEntity(oCI3);

		CadCaixaInspecaoDrenagem oCI4 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d4); 
		this.updateData(oCI4, 5, 5, 2.0);
		currBlockDef.addEntity(oCI4);

		CadCaixaInspecaoDrenagem oCI5 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d5); 
		this.updateData(oCI5, 6, 6, 2.5);
		currBlockDef.addEntity(oCI5);

		CadCaixaInspecaoDrenagem oCI6 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d6); 
		this.updateData(oCI6, 7, 7, 3.0);
		currBlockDef.addEntity(oCI6);

		//CONECTA_CAIXAINSPECAO
		//
		oCI0.setProximaCI(oCI1.getNumeroCI());
		oCI0.setProximo(oCI1);
		
		oCI1.setProximaCI(oCI2.getNumeroCI());
		oCI1.setProximo(oCI2);
		oCI1.addAnterior(oCI0);
		
		oCI2.setProximaCI(oCI3.getNumeroCI());
		oCI2.setProximo(oCI3);
		oCI2.addAnterior(oCI1);
		
		oCI3.setProximaCI(oCI4.getNumeroCI());
		oCI3.setProximo(oCI4);
		oCI3.addAnterior(oCI2);

		oCI4.setProximaCI(oCI5.getNumeroCI());
		oCI4.setProximo(oCI5);
		oCI4.addAnterior(oCI3);

		oCI5.setProximaCI(oCI6.getNumeroCI());
		oCI5.setProximo(oCI6);
		oCI5.addAnterior(oCI4);

		oCI6.addAnterior(oCI5);
		
    }

}
