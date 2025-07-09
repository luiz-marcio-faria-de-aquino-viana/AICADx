/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PointSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.samples;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadCilinder3d;
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

public class PointSample implements ISample 
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
		CadLine oLine0 = CadLine.create(oLayer, oPt3d0, oPt3d1);
		currBlockDef.addEntity(oLine0);

		CadLine oLine1 = CadLine.create(oLayer, oPt3d1, oPt3d2);
		currBlockDef.addEntity(oLine1);

		CadLine oLine2 = CadLine.create(oLayer, oPt3d2, oPt3d3);
		currBlockDef.addEntity(oLine2);

		CadLine oLine3 = CadLine.create(oLayer, oPt3d3, oPt3d4);
		currBlockDef.addEntity(oLine3);

		CadLine oLine4 = CadLine.create(oLayer, oPt3d4, oPt3d5);
		currBlockDef.addEntity(oLine4);

		CadLine oLine5 = CadLine.create(oLayer, oPt3d5, oPt3d6);
		currBlockDef.addEntity(oLine5);

		CadLine oLine6 = CadLine.create(oLayer, oPt3d6, oPt3d0);
		currBlockDef.addEntity(oLine6);
		
		//CADCIRCLE
		//
		CadCircle oCircle0 = CadCircle.create(oLayer, oPt3d0, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle0);

		CadCircle oCircle1 = CadCircle.create(oLayer, oPt3d1, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle1);

		CadCircle oCircle2 = CadCircle.create(oLayer, oPt3d2, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle2);

		CadCircle oCircle3 = CadCircle.create(oLayer, oPt3d3, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle3);

		CadCircle oCircle4 = CadCircle.create(oLayer, oPt3d4, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle4);

		CadCircle oCircle5 = CadCircle.create(oLayer, oPt3d5, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle5);

		CadCircle oCircle6 = CadCircle.create(oLayer, oPt3d6, 0.25 + (Math.random() * 0.5));
		currBlockDef.addEntity(oCircle6);
		
		//CADARC
		//
		double startAngleDegrees = 22.5 + (Math.random() * 45.0);
		double endAngleDegrees = startAngleDegrees + 22.5 + (Math.random() * 45.0);
		
		CadArc oArc0 = CadArc.create(oLayer, oPt3d0, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc0);

		CadArc oArc1 = CadArc.create(oLayer, oPt3d1, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc1);

		CadArc oArc2 = CadArc.create(oLayer, oPt3d2, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc2);

		CadArc oArc3 = CadArc.create(oLayer, oPt3d3, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc3);

		CadArc oArc4 = CadArc.create(oLayer, oPt3d4, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc4);

		CadArc oArc5 = CadArc.create(oLayer, oPt3d5, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc5);

		CadArc oArc6 = CadArc.create(oLayer, oPt3d6, 0.25 + (Math.random() * 0.5), startAngleDegrees, endAngleDegrees);
		currBlockDef.addEntity(oArc6);

    }

}

