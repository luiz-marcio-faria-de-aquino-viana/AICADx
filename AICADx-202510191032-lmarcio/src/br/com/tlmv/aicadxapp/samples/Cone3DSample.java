/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Cone3DSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/05/2025
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
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCone3d;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class Cone3DSample implements ISample 
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
    	
		CadBlockDef currBlockDef = doc.getCurrBlockDef();

		LayerTable oTbl = doc.getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);

		//FILENAME
		//
		String name = doc.getName();	
		String fileName = doc.getFileName();	
		
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
		CadPoint oPt0 = CadPoint.create(currBlockDef, oLayer, oPt3d0);
		currBlockDef.addEntity(oPt0);

		CadPoint oPt1 = CadPoint.create(currBlockDef, oLayer, oPt3d1);
		currBlockDef.addEntity(oPt1);

		CadPoint oPt2 = CadPoint.create(currBlockDef, oLayer, oPt3d2);
		currBlockDef.addEntity(oPt2);

		CadPoint oPt3 = CadPoint.create(currBlockDef, oLayer, oPt3d3);
		currBlockDef.addEntity(oPt3);

		CadPoint oPt4 = CadPoint.create(currBlockDef, oLayer, oPt3d4);
		currBlockDef.addEntity(oPt4);

		CadPoint oPt5 = CadPoint.create(currBlockDef, oLayer, oPt3d5);
		currBlockDef.addEntity(oPt5);

		CadPoint oPt6 = CadPoint.create(currBlockDef, oLayer, oPt3d6);
		currBlockDef.addEntity(oPt6);
		
		//CADCILINDER3D
		//
		double radius = 1.0 * Math.random();
		double altura = 5.0 * Math.random();
		CadCone3d oCilinder0 = CadCone3d.create(currBlockDef, oLayer, oPt3d0, radius, altura);
		currBlockDef.addEntity(oCilinder0);
		
		radius = 1.0 * Math.random();
		altura = 5.0 * Math.random();
		CadCone3d oCilinder1 = CadCone3d.create(currBlockDef, oLayer, oPt3d1, radius, altura);
		currBlockDef.addEntity(oCilinder1);
		
		radius = 1.0 * Math.random();
		altura = 5.0 * Math.random();
		CadCone3d oCilinder2 = CadCone3d.create(currBlockDef, oLayer, oPt3d2, radius, altura);
		currBlockDef.addEntity(oCilinder2);
		
		radius = 1.0 * Math.random();
		altura = 5.0 * Math.random();
		CadCone3d oCilinder3 = CadCone3d.create(currBlockDef, oLayer, oPt3d3, radius, altura);
		currBlockDef.addEntity(oCilinder3);
		
		radius = 1.0 * Math.random();
		altura = 5.0 * Math.random();
		CadCone3d oCilinder4 = CadCone3d.create(currBlockDef, oLayer, oPt3d4, radius, altura);
		currBlockDef.addEntity(oCilinder4);
		
		radius = 1.0 * Math.random();
		altura = 5.0 * Math.random();
		CadCone3d oCilinder5 = CadCone3d.create(currBlockDef, oLayer, oPt3d5, radius, altura);
		currBlockDef.addEntity(oCilinder5);
    }

}
