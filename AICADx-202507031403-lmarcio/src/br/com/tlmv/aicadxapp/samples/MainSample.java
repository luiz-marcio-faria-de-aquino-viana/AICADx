/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.samples;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;

public class MainSample implements ISample 
{
//Public
	
    public void initSampleData(int debugLevel, CadDocumentDef doc)
    {
    	if(debugLevel == AppDefs.DEBUG_LEVEL00) return;
    	
		AppCadMain cad = AppCadMain.getCad();
		//
		(new BasicSample()).initSampleData(AppDefs.DEBUG_LEVEL01, doc);
		(new Box3DSample()).initSampleData(AppDefs.DEBUG_LEVEL02, doc);
		(new Cilinder3DSample()).initSampleData(AppDefs.DEBUG_LEVEL03, doc);
		(new Cone3DSample()).initSampleData(AppDefs.DEBUG_LEVEL04, doc);
		(new TroncoCone3DSample()).initSampleData(AppDefs.DEBUG_LEVEL05, doc);
		(new Sphere3DSample()).initSampleData(AppDefs.DEBUG_LEVEL06, doc);
		(new Torus3DSample()).initSampleData(AppDefs.DEBUG_LEVEL07, doc);
		(new PipeSample()).initSampleData(AppDefs.DEBUG_LEVEL08, doc);
		(new DxfSample()).initSampleData(AppDefs.DEBUG_LEVEL09, doc);
		(new LineSample()).initSampleData(AppDefs.DEBUG_LEVEL10, doc);
		(new PointSample()).initSampleData(AppDefs.DEBUG_LEVEL11, doc);
		//
		//*** DEBUG_LEVEL ***
		//
		(new DrenagemSample()).initSampleData(AppDefs.DEBUG_LEVEL16, doc);
		
    }

}
