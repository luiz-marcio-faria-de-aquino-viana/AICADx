/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PipeSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/05/2025
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
import br.com.tlmv.aicadxapp.cad.CadCilinder3d;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class PipeSample implements ISample 
{
//Public
	
    public void initSampleData(int debugLevel, CadDocumentDef doc)
    {
    	if(debugLevel != AppDefs.DEBUG_LEVEL) return;
    	
		CadBlockDef currBlockDef = doc.getCurrBlockDef();

		LayerTable oTbl = doc.getLayerTable();

		CadLayerDef oLayerPontos = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);

		CadLayerDef oLayerTb = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TB_DRENAGEM);

		GeomVector3d axisZ = GeomUtil.axisZ3d(); 

		//FILENAME
		//
		String name = doc.getName();	
		String fileName = doc.getFileName();	
		
    	MainPanel panel = MainPanel.getPanel();
    	
    	MainFrame frm = panel.getParentFrame();
		frm.updateTitle(name);
		
		//GEOMPOINT3D
		//
		GeomPoint3d oPt3d0 = new GeomPoint3d( 2.5,  2.5,  0.0);
		GeomPoint3d oPt3d1 = new GeomPoint3d( 7.5, -2.5,  5.0);
		//
		GeomPoint3d oPt3d2 = new GeomPoint3d( 3.5, 2.5,  0.0);
		GeomPoint3d oPt3d3 = new GeomPoint3d( 6.0, 2.5, -1.0);
		//
		GeomPoint3d oPt3d4 = new GeomPoint3d( 4.5, 2.5,  0.0);
		GeomPoint3d oPt3d5 = new GeomPoint3d( 9.5, 2.5, -2.5);
		//
		GeomPoint3d oPt3d6 = new GeomPoint3d( 5.5, 2.5,  0.0);
		GeomPoint3d oPt3d7 = new GeomPoint3d(14.0, 2.5, -5.0);
		//
		GeomPoint3d oPt3d8 = new GeomPoint3d( 5.5, 2.5,  0.0);
		GeomPoint3d oPt3d9 = new GeomPoint3d(15.0, 2.5,  2.5);
		//
		GeomPoint3d oPt3d10 = new GeomPoint3d( 5.5, 2.5, 0.0);
		GeomPoint3d oPt3d11 = new GeomPoint3d(14.0, 2.5, 5.0);
		
		//PIPE
		//
		CadPipe oPipe0 = CadPipe.create(oLayerTb, oPt3d0, oPt3d1, 1.0);
		currBlockDef.addEntity(oPipe0);
		
		CadPipe oPipe1 = CadPipe.create(oLayerTb, oPt3d2, oPt3d3, 1.0);
		currBlockDef.addEntity(oPipe1);
		
		CadPipe oPipe2 = CadPipe.create(oLayerTb, oPt3d4, oPt3d5, 1.0);
		currBlockDef.addEntity(oPipe2);
		
		CadPipe oPipe3 = CadPipe.create(oLayerTb, oPt3d6, oPt3d7, 1.0);
		currBlockDef.addEntity(oPipe3);
		
		CadPipe oPipe4 = CadPipe.create(oLayerTb, oPt3d8, oPt3d9, 1.0);
		currBlockDef.addEntity(oPipe4);
		
		CadPipe oPipe5 = CadPipe.create(oLayerTb, oPt3d10, oPt3d11, 1.0);
		currBlockDef.addEntity(oPipe5);

		//CI
		//
		CadCilinder3d oCI0 = CadCilinder3d.create(oLayerPontos, oPt3d0, 0.75, 2.0);
		currBlockDef.addEntity(oCI0);
		
		CadCilinder3d oCI1 = CadCilinder3d.create(oLayerPontos, oPt3d2, 0.75, 2.0);
		currBlockDef.addEntity(oCI1);
		
		CadCilinder3d oCI2 = CadCilinder3d.create(oLayerPontos, oPt3d4, 0.75, 2.0);
		currBlockDef.addEntity(oCI2);
		
		CadCilinder3d oCI3 = CadCilinder3d.create(oLayerPontos, oPt3d6, 0.75, 2.0);
		currBlockDef.addEntity(oCI3);
		
		CadCilinder3d oCI4 = CadCilinder3d.create(oLayerPontos, oPt3d8, 0.75, 2.0);
		currBlockDef.addEntity(oCI4);
		
		CadCilinder3d oCI5 = CadCilinder3d.create(oLayerPontos, oPt3d10, 0.75, 2.0);
		currBlockDef.addEntity(oCI5);
    }

}
