/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BasicSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.samples;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class BasicSample implements ISample 
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
		
		//CADLINE
		//
		CadLine oLn0 = CadLine.create(oLayer, oPt3d0, oPt3d1);
		currBlockDef.addEntity(oLn0);
		
		CadLine oLn1 = CadLine.create(oLayer, oPt3d1, oPt3d2);
		currBlockDef.addEntity(oLn1);
		
		CadLine oLn2 = CadLine.create(oLayer, oPt3d2, oPt3d3);
		currBlockDef.addEntity(oLn2);
		
		CadLine oLn3 = CadLine.create(oLayer, oPt3d3, oPt3d4);
		currBlockDef.addEntity(oLn3);
		
		CadLine oLn4 = CadLine.create(oLayer, oPt3d4, oPt3d5);
		currBlockDef.addEntity(oLn4);
		
		CadLine oLn5 = CadLine.create(oLayer, oPt3d5, oPt3d6);
		currBlockDef.addEntity(oLn5);
		
		CadLine oLn6 = CadLine.create(oLayer, oPt3d6, oPt3d0);
		currBlockDef.addEntity(oLn6);
		
		//CADRECTANGLE
		//
		CadRectangle oRect0 = CadRectangle.create(oLayer, oPt3d0, oPt3d1);
		currBlockDef.addEntity(oRect0);
		
		CadRectangle oRect1 = CadRectangle.create(oLayer, oPt3d1, oPt3d2);
		currBlockDef.addEntity(oRect1);
		
		CadRectangle oRect2 = CadRectangle.create(oLayer, oPt3d2, oPt3d3);
		currBlockDef.addEntity(oRect2);
		
		CadRectangle oRect3 = CadRectangle.create(oLayer, oPt3d3, oPt3d4);
		currBlockDef.addEntity(oRect3);
		
		CadRectangle oRect4 = CadRectangle.create(oLayer, oPt3d4, oPt3d5);
		currBlockDef.addEntity(oRect4);
		
		CadRectangle oRect5 = CadRectangle.create(oLayer, oPt3d5, oPt3d6);
		currBlockDef.addEntity(oRect5);
		
		//CADCIRCLE
		//
		double radioMax = 1.0; 
		
		CadCircle oCirc0 = CadCircle.create(oLayer, oPt3d0, radioMax * Math.random());
		currBlockDef.addEntity(oCirc0);
		
		CadCircle oCirc1 = CadCircle.create(oLayer, oPt3d1, radioMax * Math.random());
		currBlockDef.addEntity(oCirc1);
		
		CadCircle oCirc2 = CadCircle.create(oLayer, oPt3d2, radioMax * Math.random());
		currBlockDef.addEntity(oCirc2);
		
		CadCircle oCirc3 = CadCircle.create(oLayer, oPt3d3, radioMax * Math.random());
		currBlockDef.addEntity(oCirc3);
		
		CadCircle oCirc4 = CadCircle.create(oLayer, oPt3d4, radioMax * Math.random());
		currBlockDef.addEntity(oCirc4);
		
		CadCircle oCirc5 = CadCircle.create(oLayer, oPt3d5, radioMax * Math.random());
		currBlockDef.addEntity(oCirc5);
		
		CadCircle oCirc6 = CadCircle.create(oLayer, oPt3d6, radioMax * Math.random());
		currBlockDef.addEntity(oCirc6);
		
		//CADARC
		//
		double radioDiff = 0.25; 
		
		CadArc oArc0 = CadArc.create(oLayer, oPt3d0, oCirc0.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc0);
		
		CadArc oArc1 = CadArc.create(oLayer, oPt3d1, oCirc1.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc1);
		
		CadArc oArc2 = CadArc.create(oLayer, oPt3d2, oCirc2.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc2);
		
		CadArc oArc3 = CadArc.create(oLayer, oPt3d3, oCirc3.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc3);
		
		CadArc oArc4 = CadArc.create(oLayer, oPt3d4, oCirc4.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc4);
		
		CadArc oArc5 = CadArc.create(oLayer, oPt3d5, oCirc5.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc5);
		
		CadArc oArc6 = CadArc.create(oLayer, oPt3d6, oCirc6.getRadius() + radioDiff, 15.0, 75.0);
		currBlockDef.addEntity(oArc6);
				
		//CADTEXT
		//
		double textHeightMax = 0.25; 
		
		CadText oTxt0 = CadText.create(oLayer, "Ponto 0", oPt3d0, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt0);
		
		CadText oTxt1 = CadText.create(oLayer, "Ponto 1", oPt3d1, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt1);
		
		CadText oTxt2 = CadText.create(oLayer, "Ponto 2", oPt3d2, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt2);
		
		CadText oTxt3 = CadText.create(oLayer, "Ponto 3", oPt3d3, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt3);
		
		CadText oTxt4 = CadText.create(oLayer, "Ponto 4", oPt3d4, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt4);
		
		CadText oTxt5 = CadText.create(oLayer, "Ponto 5", oPt3d5, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt5);
		
		CadText oTxt6 = CadText.create(oLayer, "Ponto 6", oPt3d6, textHeightMax * Math.random());
		currBlockDef.addEntity(oTxt6);
    }

}
