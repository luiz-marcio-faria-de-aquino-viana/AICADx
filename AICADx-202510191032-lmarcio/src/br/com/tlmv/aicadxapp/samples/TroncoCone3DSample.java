/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * TroncoCone3DSample.java
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
import br.com.tlmv.aicadxapp.cad.CadTroncoCone3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class TroncoCone3DSample implements ISample 
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
		double topRadius = 0.75 * Math.random();
		double baseRadius = topRadius + ( 1.0 * Math.random() );
		double altura = 5.0 * Math.random();
		CadTroncoCone3d oTC0 = CadTroncoCone3d.create(currBlockDef, oLayer, oPt3d0, baseRadius, topRadius, altura);
		currBlockDef.addEntity(oTC0);
		
		topRadius = 0.75 * Math.random();
		baseRadius = topRadius + ( 1.0 * Math.random() );
		altura = 5.0 * Math.random();
		CadTroncoCone3d oTC1 = CadTroncoCone3d.create(currBlockDef, oLayer, oPt3d1, baseRadius, topRadius, altura);
		currBlockDef.addEntity(oTC1);
		
		topRadius = 0.75 * Math.random();
		baseRadius = topRadius + ( 1.0 * Math.random() );
		altura = 5.0 * Math.random();
		CadTroncoCone3d oTC2 = CadTroncoCone3d.create(currBlockDef, oLayer, oPt3d2, baseRadius, topRadius, altura);
		currBlockDef.addEntity(oTC2);
		
		topRadius = 0.75 * Math.random();
		baseRadius = topRadius + ( 1.0 * Math.random() );
		altura = 5.0 * Math.random();
		CadTroncoCone3d oTC3 = CadTroncoCone3d.create(currBlockDef, oLayer, oPt3d3, baseRadius, topRadius, altura);
		currBlockDef.addEntity(oTC3);
		
		topRadius = 0.75 * Math.random();
		baseRadius = topRadius + ( 1.0 * Math.random() );
		altura = 5.0 * Math.random();
		CadTroncoCone3d oTC4 = CadTroncoCone3d.create(currBlockDef, oLayer, oPt3d4, baseRadius, topRadius, altura);
		currBlockDef.addEntity(oTC4);
		
		topRadius = 0.75 * Math.random();
		baseRadius = topRadius + ( 1.0 * Math.random() );
		altura = 5.0 * Math.random();
		CadTroncoCone3d oTC5 = CadTroncoCone3d.create(currBlockDef, oLayer, oPt3d5, baseRadius, topRadius, altura);
		currBlockDef.addEntity(oTC5);
    }

}
