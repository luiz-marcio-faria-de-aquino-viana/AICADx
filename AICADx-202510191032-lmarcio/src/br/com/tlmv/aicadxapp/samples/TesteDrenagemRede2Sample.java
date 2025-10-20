/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * TesteDrenagemRede2Sample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/07/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.samples;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPontoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class TesteDrenagemRede2Sample implements ISample 
{
//Public Static
	public static final double DISTANCIA_ENTRE_CI = 30.0;			// 30.0 metros
	public static final double DISTANCIA_ENTRE_ESTACA = 20.0;		// 20.0 metros
	
//Private
	
	private double updateData(double dDistAnterior, CadCaixaInspecaoDrenagem oCIAnterior, CadCaixaInspecaoDrenagem oCIAtual, String strPv, int numPv)
	{
		int iNumeroCIAtual = oCIAtual.getNumeroCI();

		GeomPoint3d ptAnterior = new GeomPoint3d(0.0, 0.0, 0.0);

		if(oCIAnterior != null) {
			ptAnterior = new GeomPoint3d(oCIAnterior.getPtIns());

			oCIAnterior.setProximo(oCIAtual);
			oCIAnterior.setProximaCI(iNumeroCIAtual);
			oCIAtual.addAnterior(oCIAnterior);			
		}
		
		double dAreaLocal = 0.2;
		double dAreaExterna = 0.0;
		double dAreaTotal = dAreaExterna + dAreaLocal;		
		
		GeomPoint3d ptAtual = new GeomPoint3d(oCIAtual.getPtIns());
		double dDistAtual = ptAnterior.distTo(ptAtual);		
		double dDistTotal = dDistAnterior + dDistAtual;
		
		double dNumEstaca = Math.floor(dDistTotal);   
		int iNumEstaca = (int)dNumEstaca;
		
		String strEstaca = String.format("%s + %s m", iNumEstaca, dDistAtual);

		oCIAtual.setNumEstaca(iNumEstaca);
		oCIAtual.setDistEstaca(dDistAtual);
		oCIAtual.setEstaca(strEstaca);
		oCIAtual.setLocalId(DrenagemCalc.IDFLOCAL_SANTACRUZ_VAL);
		oCIAtual.setLocal(DrenagemCalc.IDFLOCAL_SANTACRUZ_STR);
		oCIAtual.setPv(strPv);
		oCIAtual.setCoefImper(0.8);
		oCIAtual.setAreaExterna(dAreaExterna);
		oCIAtual.setAreaLocal(dAreaLocal);
		oCIAtual.setAreaTotal(dAreaTotal);
		oCIAtual.setAreaTotalImp(0.0);
		
		return dDistTotal;
	}
	
	private double updateData(CadCaixaInspecaoDrenagem oCIAnterior, CadCaixaInspecaoDrenagem oCIAtual)
	{
		int iNumeroCIAtual = oCIAtual.getNumeroCI();

		if(oCIAnterior != null) {
			oCIAnterior.setProximo(oCIAtual);
			oCIAnterior.setProximaCI(iNumeroCIAtual);
			oCIAtual.addAnterior(oCIAnterior);			
		}		

		return 0.0;
	}
	
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

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);

		ArrayList<CadCaixaInspecaoDrenagem> lsCI = new ArrayList<CadCaixaInspecaoDrenagem>();
		
		//FILENAME
		//
		String name = doc.getName();	
		String fileName = doc.getFileName();	
		
    	MainPanel panel = MainPanel.getMainPanel();
    	
    	MainFrame frm = MainFrame.getMainFrame();
		frm.updateTitle(name);
		
		//GEOMPOINT3D
		//
		GeomPoint3d oPt3d_A1        = new GeomPoint3d( 5.0, 65.0, 0.50);
		GeomPoint3d oPt3d_A2        = new GeomPoint3d( 5.0, 35.0, 0.25);
		GeomPoint3d oPt3d_DESAGUE_A = new GeomPoint3d( 5.0,  5.0, 0.0);
		
		double dDistAnterior = 0.0;
		
		//MODCAIXAINSPECAODRENAGEM
		//
		int n = 1;
		
		//TRECHO_1
		CadCaixaInspecaoDrenagem oCI_A1 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oPt3d_A1);
		dDistAnterior = this.updateData(dDistAnterior, null, oCI_A1, "A1", n++);		
		currBlockDef.addEntity(oCI_A1);

		CadCaixaInspecaoDrenagem oCI_A2 = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oPt3d_A2);
		dDistAnterior = this.updateData(dDistAnterior, oCI_A1, oCI_A2, "A2", n++);		
		currBlockDef.addEntity(oCI_A2);

		CadCaixaInspecaoDrenagem oCI_DESAGUE_A = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oPt3d_DESAGUE_A); 
		dDistAnterior = this.updateData(dDistAnterior, oCI_A2, oCI_DESAGUE_A, "DESAGUE A", n++);
		currBlockDef.addEntity(oCI_DESAGUE_A);

		// RALO_SIMPLES / BOCA_LOBO / RALO_COM_BOCA_LOBO
		//
		String shapeName = AppDefs.SHAPE_RPD_RALO_SIMPLES;
		//
	    double largura = DrenagemCalc.DEF_RALO_SIMPLES_LARGURA;
	    double altura = DrenagemCalc.DEF_RALO_SIMPLES_ALTURA;
	    double profundidade	= DrenagemCalc.DEF_RALO_SIMPLES_PROFUNDIDADE;
	    //
		GeomPoint3d oPt3d_A1_Right = new GeomPoint3d( 0.0, 65.0, 0.5);
		GeomPoint3d oPt3d_A1_Left  = new GeomPoint3d(10.0, 65.0, 0.5);
			    
	    ShapeTable shapeTable = doc.getShapeTable();
		
		Shape oShape = shapeTable.getShape(shapeName);
		if(oShape != null) {
			//MODPONTODRENAGEM
			//
			CadPontoDrenagem oRalo_A1_Right = CadPontoDrenagem.create(currBlockDef, oLayer, oCI_A1, oPt3d_A1_Right, 0.0, oShape, largura, altura, profundidade);
			currBlockDef.addEntity(oRalo_A1_Right);
	
			CadPontoDrenagem oRalo_A1_Left = CadPontoDrenagem.create(currBlockDef, oLayer, oCI_A1, oPt3d_A1_Left, 0.0, oShape, largura, altura, profundidade);
			currBlockDef.addEntity(oRalo_A1_Left);
	
		}
		
    }

}
