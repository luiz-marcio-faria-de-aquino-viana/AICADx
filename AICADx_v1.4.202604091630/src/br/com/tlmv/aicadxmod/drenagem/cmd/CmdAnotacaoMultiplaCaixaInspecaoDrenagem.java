/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdAnotacaoCaixaInspecaoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/04/2025
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

package br.com.tlmv.aicadxmod.drenagem.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAnotacaoCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CmdAnotacaoMultiplaCaixaInspecaoDrenagem extends CmdBase
{
//Public
	
	public CmdAnotacaoMultiplaCaixaInspecaoDrenagem() {
		super(AppDefs.ACTION_RDP1_ANOTACAO_MULTIPLA_CI, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("DRENAGEM: Insere Anotacao Multipla da Caixa de Inspecao (CI)...");

		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		//TO_LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());

		//LAYER_TABLE
		//
		LayerTable oTbl = this.getDoc().getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TEXTOS);
		
		GeomVector3d vDir = new GeomVector3d(0.0, 0.0, 0.0, AppDefs.MATHVAL_HSQRT2, AppDefs.MATHVAL_HSQRT2, 0.0);
		
		CadEntity[] arrCI = currBlockDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
		for(CadEntity oEnt : arrCI) {
			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
			
			GeomPoint3d ptIns = new GeomPoint3d( oCI.getPtIns() );
			GeomPoint3d ptAnotacao = ptIns.otherMoveTo(vDir, AppDefs.TBL_ANNOTATION_LINE_SIZE);
			
			CadAnotacaoCaixaInspecaoDrenagem o = CadAnotacaoCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oCI, ptAnotacao);
			currBlockDef.addEntity(o);
		}
	}

	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args) {
		super.doExecuteCommand(app, frm, cad, doc, args);
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		//TO_LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());

		LayerTable oTbl = this.getDoc().getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TEXTOS);
		
		GeomVector3d vDir = new GeomVector3d(0.0, 0.0, 0.0, AppDefs.MATHVAL_HSQRT2, AppDefs.MATHVAL_HSQRT2, 0.0);
		
		CadEntity[] arrCI = currBlockDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
		for(CadEntity oEnt : arrCI) {
			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
			
			GeomPoint3d ptIns = new GeomPoint3d( oCI.getPtIns() );
			GeomPoint3d ptAnotacao = ptIns.otherMoveTo(vDir, AppDefs.TBL_ANNOTATION_LINE_SIZE);
			
			CadAnotacaoCaixaInspecaoDrenagem o = CadAnotacaoCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oCI, ptAnotacao);
			currBlockDef.addEntity(o);
		}
	}

}
