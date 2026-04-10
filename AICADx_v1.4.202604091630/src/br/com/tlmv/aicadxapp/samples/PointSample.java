/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PointSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/05/2025
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
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class PointSample implements ISample 
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
		LayerTable oTbl = doc.getLayerTable();
		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);

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
		GeomPoint3d oPt3d0 = new GeomPoint3d(2.0, 1.0, 0.0);
		GeomPoint3d oPt3d1 = new GeomPoint3d(3.0, 3.0, 0.0);
		GeomPoint3d oPt3d2 = new GeomPoint3d(4.0, 5.0, 0.0);
		GeomPoint3d oPt3d3 = new GeomPoint3d(5.0, 7.0, 0.0);
		GeomPoint3d oPt3d4 = new GeomPoint3d(6.0, 5.0, 0.0);
		GeomPoint3d oPt3d5 = new GeomPoint3d(7.0, 3.0, 0.0);
		GeomPoint3d oPt3d6 = new GeomPoint3d(8.0, 1.0, 0.0);
		
		//CADPOINT
		//
		CadPoint oPt0 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d0);
		currBlockDef.addEntity(oPt0);

		CadPoint oPt1 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d1);
		currBlockDef.addEntity(oPt1);

		CadPoint oPt2 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d2);
		currBlockDef.addEntity(oPt2);

		CadPoint oPt3 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d3);
		currBlockDef.addEntity(oPt3);

		CadPoint oPt4 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d4);
		currBlockDef.addEntity(oPt4);

		CadPoint oPt5 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d5);
		currBlockDef.addEntity(oPt5);

		CadPoint oPt6 = CadPoint.create(currBlockDef, oLayer, oLevel, oPt3d6);
		currBlockDef.addEntity(oPt6);
		
		//CADLINE
		//
		CadLine oLine0 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d0, oPt3d1);
		currBlockDef.addEntity(oLine0);

		CadLine oLine1 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d1, oPt3d2);
		currBlockDef.addEntity(oLine1);

		CadLine oLine2 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d2, oPt3d3);
		currBlockDef.addEntity(oLine2);

		CadLine oLine3 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d3, oPt3d4);
		currBlockDef.addEntity(oLine3);

		CadLine oLine4 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d4, oPt3d5);
		currBlockDef.addEntity(oLine4);

		CadLine oLine5 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d5, oPt3d6);
		currBlockDef.addEntity(oLine5);

		CadLine oLine6 = CadLine.create(currBlockDef, oLayer, oLevel, oPt3d6, oPt3d0);
		currBlockDef.addEntity(oLine6);
		
		//CADCIRCLE
		//
		CadCircle oCircle0 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d0, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle0);

		CadCircle oCircle1 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d1, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle1);

		CadCircle oCircle2 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d2, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle2);

		CadCircle oCircle3 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d3, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle3);

		CadCircle oCircle4 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d4, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle4);

		CadCircle oCircle5 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d5, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle5);

		CadCircle oCircle6 = CadCircle.create(currBlockDef, oLayer, oLevel, oPt3d6, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle6);
		
		//CADARC
		//
		double startAngleDegrees = 22.5 + (Math.random() * 45.0);
		double endAngleDegrees = startAngleDegrees + 22.5 + (Math.random() * 45.0);
		
		CadArc oArc0 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d0, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc0);

		CadArc oArc1 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d1, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc1);

		CadArc oArc2 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d2, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc2);

		CadArc oArc3 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d3, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc3);

		CadArc oArc4 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d4, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc4);

		CadArc oArc5 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d5, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc5);

		CadArc oArc6 = CadArc.create(currBlockDef, oLayer, oLevel, oPt3d6, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc6);

    }

}

