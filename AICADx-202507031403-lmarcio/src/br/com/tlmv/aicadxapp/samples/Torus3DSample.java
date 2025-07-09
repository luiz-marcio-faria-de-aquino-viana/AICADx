/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Torus3DSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.samples;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadSphere3d;
import br.com.tlmv.aicadxapp.cad.CadTorus3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class Torus3DSample implements ISample 
{
//Public
	
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
		
    	MainPanel panel = MainPanel.getPanel();
    	
    	MainFrame frm = panel.getParentFrame();
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
		CadPoint oPt0 = CadPoint.create(oLayer, oPt3d0);
		currBlockDef.addEntity(oPt0);

		CadPoint oPt1 = CadPoint.create(oLayer, oPt3d1);
		currBlockDef.addEntity(oPt1);

		CadPoint oPt2 = CadPoint.create(oLayer, oPt3d2);
		currBlockDef.addEntity(oPt2);

		CadPoint oPt3 = CadPoint.create(oLayer, oPt3d3);
		currBlockDef.addEntity(oPt3);

		CadPoint oPt4 = CadPoint.create(oLayer, oPt3d4);
		currBlockDef.addEntity(oPt4);

		CadPoint oPt5 = CadPoint.create(oLayer, oPt3d5);
		currBlockDef.addEntity(oPt5);

		CadPoint oPt6 = CadPoint.create(oLayer, oPt3d6);
		currBlockDef.addEntity(oPt6);
		
		//CADCILINDER3D
		//
		double torusRadius = 0.25 * Math.random();
		double radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT0 = CadTorus3d.create(oLayer, oPt3d0, radius, torusRadius);
		currBlockDef.addEntity(oT0);

		torusRadius = 0.25 * Math.random();
		radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT1 = CadTorus3d.create(oLayer, oPt3d1, radius, torusRadius);
		currBlockDef.addEntity(oT1);

		torusRadius = 0.25 * Math.random();
		radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT2 = CadTorus3d.create(oLayer, oPt3d2, radius, torusRadius);
		currBlockDef.addEntity(oT2);

		torusRadius = 0.25 * Math.random();
		radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT3 = CadTorus3d.create(oLayer, oPt3d3, radius, torusRadius);
		currBlockDef.addEntity(oT3);

		torusRadius = 0.25 * Math.random();
		radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT4 = CadTorus3d.create(oLayer, oPt3d4, radius, torusRadius);
		currBlockDef.addEntity(oT4);

		torusRadius = 0.25 * Math.random();
		radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT5 = CadTorus3d.create(oLayer, oPt3d5, radius, torusRadius);
		currBlockDef.addEntity(oT5);

		torusRadius = 0.25 * Math.random();
		radius = 0.5 * Math.random() + torusRadius;
		CadTorus3d oT6 = CadTorus3d.create(oLayer, oPt3d6, radius, torusRadius);
		currBlockDef.addEntity(oT6);
    }

}
