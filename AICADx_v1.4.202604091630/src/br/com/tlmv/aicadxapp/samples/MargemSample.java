/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MargemSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 11/01/2026
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
import br.com.tlmv.aicadxapp.cad.CadMargem;
import br.com.tlmv.aicadxapp.cad.CadParamMargemOData;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class MargemSample implements ISample 
{
//Private
	
	private void insertShape(CadDocumentDef doc, CadBlockDef currBlockDef, CadLayerDef oLayer, CadLevel oLevel, String shapeName, double width, double height, GeomPoint3d ptIns)
	{
		//ATTRIB
		//
		String strTituloProjeto = "VILA VELHA RESORT";
		String strDisciplinaDesenho = "REDES PUBLICAS DE DRENAGEM";
		String strResponsavelTecnico = "Luiz Marcio Faria de Aquino Viana";
		String strNumeroDesenho = "01/10";
		String strDescricaoDesenho = "TERREO - PLANTA BAIXA";
		String strEscala = "1/100";
		String strDataEmissao = "11/01/2026";
		String strNumeroRevisao = "0";

		//-- SHAPE
		//
		ShapeTable shapeTable = doc.getShapeTable();
		
		Shape oShape = shapeTable.getShape(shapeName);
		if(oShape != null) {
			CadMargem o = CadMargem.create(currBlockDef, oLayer, oLevel, ptIns, 0.0, width, height, oShape);
			
			CadParamMargemOData oParamMargem = o.getParamMargemAt(0);
			oParamMargem.setTituloProjeto(strTituloProjeto);
			oParamMargem.setDisciplina(strDisciplinaDesenho);
			oParamMargem.setNumeroDesenho(strNumeroDesenho);
			oParamMargem.setDescricaoDesenho(strDescricaoDesenho);
			oParamMargem.setResponsavelTecnico(strResponsavelTecnico);
			oParamMargem.setEscala(strEscala);
			oParamMargem.setDataEmissao(strDataEmissao);
			oParamMargem.setNumeroRevisao(strNumeroRevisao);

			currBlockDef.addEntity(o);
		}
	}
	
//Public
	
    public void initSampleData()
    {
    	AppCadMain cad = AppCadMain.getCad();
    	
    	CadDocumentDef oNewDoc = cad.newCadDocumentDef();
    	if(oNewDoc != null) {
    		ProjectRepoVO projectRepo = oNewDoc.getProjectRepo();    		
    		
    		ViewTable viewTbl = oNewDoc.getViewTable();
    		CompView oNewView = viewTbl.newPlanView(projectRepo.getName(), 0);
    		
        	MainPanel panel = MainPanel.getMainPanel();
        	panel.addNewView(oNewDoc, oNewView);
        	
		    this.initSampleData(AppDefs.DEBUG_LEVEL, oNewDoc);
    	}
    }
	
    public void initSampleData(int debugLevel, CadDocumentDef doc)
    {
    	if(debugLevel != AppDefs.DEBUG_LEVEL) return;
    	
		CadBlockDef currBlockDef = doc.getCurrBlockDef();

		//LAYER_TABLE
		//
		LayerTable layTbl = doc.getLayerTable();
		CadLayerDef oLayer = layTbl.getLayerDefByReference(AppDefs.LAYER_0_MARGEM);

		//LEVEL_TABLE
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
		double yp = 0.0;
		GeomPoint3d oPt3d0 = new GeomPoint3d(0.0, yp, 0.0);
		this.insertShape(doc, currBlockDef, oLayer, oLevel, "SET-A0", 1189.0, 841.0, oPt3d0);

		yp += 85.1;
		GeomPoint3d oPt3d1 = new GeomPoint3d(0.0, yp, 0.0);
		this.insertShape(doc, currBlockDef, oLayer, oLevel, "SET-A1",  841.0, 594.0, oPt3d1);

		yp += 60.4;
		GeomPoint3d oPt3d2 = new GeomPoint3d(0.0, yp, 0.0);
		this.insertShape(doc, currBlockDef, oLayer, oLevel, "SET-A2",  594.0, 420.0, oPt3d2);

		yp += 43.0;
		GeomPoint3d oPt3d3 = new GeomPoint3d(0.0, yp, 0.0);
		this.insertShape(doc, currBlockDef, oLayer, oLevel, "SET-A3",  420.0, 297.0, oPt3d3);

    }

}
