/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInsereQuadroCargasEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/01/2026
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

package br.com.tlmv.aicadxmod.eletrica.cmd;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadMemoriaCalculoItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.frm.DimensionaRedeDrenagemFrame;
import br.com.tlmv.aicadxmod.drenagem.frm.GerarPlanilhaCalculoDrenagemFrame;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadQuadroCargasEletrica;
import br.com.tlmv.aicadxmod.eletrica.frm.GerarPlanilhaCalculoQuadroCargasFrame;

public class CmdInsereQuadroCargasEletrica extends CmdBase
{
//Private

	/* PromptOption
	*/
	//OPT_TENSAOQUADRO
	private PromptOptionVO optTensaoQuadro22V  = new PromptOptionVO(AppDefs.OPT_TENSAOQUADRO_220V, "220V", "2", true);
	private PromptOptionVO optTensaoQuadro380V = new PromptOptionVO(AppDefs.OPT_TENSAOQUADRO_380V, "380V", "3", false);
	//OPT_SISTEMAFASE
	private PromptOptionVO optSistemaFaseFN   = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_FN,   "F+N",    "F", false);
	private PromptOptionVO optSistemaFase2F   = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_2F,   "2F",     "2", false);
	private PromptOptionVO optSistemaFase2FN  = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_2FN,  "2F+N",   "2", false);
	private PromptOptionVO optSistemaFase3F   = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_3F,   "3F",     "3", false);
	private PromptOptionVO optSistemaFase3FN  = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_3FN,  "3F+N",   "3", false);
	private PromptOptionVO optSistemaFaseFNT  = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_FNT,  "F+N+T",  "F", false);
	private PromptOptionVO optSistemaFase2FT  = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_2FT,  "2F+T",   "2", false);
	private PromptOptionVO optSistemaFase2FNT = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_2FNT, "2F+N+T", "2", false);
	private PromptOptionVO optSistemaFase3FT  = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_3FT,  "3F+T",   "3", false);
	private PromptOptionVO optSistemaFase3FNT = new PromptOptionVO(AppDefs.OPT_SISTEMAFASE_3FNT, "3F+N+T", "3", true);
		
//Public

	public CmdInsereQuadroCargasEletrica() {
		super(AppDefs.ACTION_EL2_QUADRO_CARGAS, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		PromptUtil.prompt("ELETRICA: Insere Quadro de Cargas...");

		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(this, null, "Insert point: ");
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);
		
		String tmpNomeQuadro = AppDefs.NULL_STR;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODELINSEREPONTO, "Selecione quadro de distribuicao: ");
		if(ent1 != null) {
			CadPontoEletrica oEnt1 = (CadPontoEletrica)ent1;

			int szLsParamEletrico = oEnt1.getSzLsParamEletrico();
			if(szLsParamEletrico > 0) {
				CadParamEletricoOData oParametroEletrico1 = oEnt1.getParamEletricoAt(0);

				String strTip = oParametroEletrico1.getTipo();				
				if( AppDefs.FIA_S_QUADRO.equals(strTip) ) {
					tmpNomeQuadro = oParametroEletrico1.getNomeQuadro();
				}
				else if( AppDefs.FIA_S_CARGA.equals(strTip) &&
						 AppDefs.FIA_S_ILUMINACAO.equals(strTip) &&
						 AppDefs.FIA_S_MOTOR.equals(strTip) &&
						 AppDefs.FIA_S_RAIOX.equals(strTip) &&
						 AppDefs.FIA_S_AQUECIMENTO.equals(strTip) ) {
					tmpNomeQuadro = oParametroEletrico1.getQuadroOrigem();
				}
			}
		}
		else {
			tmpNomeQuadro = PromptUtil.getText(this, "Nome do quadro: ");
			if(tmpNomeQuadro == null) return null;			
		}
		
		String strNomeQuadro = StringUtil.toUpperCase(tmpNomeQuadro);
		String strDescricaoQuadro = String.format("Quadro de luz e forca - %s", strNomeQuadro);

		String prompt = String.format("Descricao: <%s> ", strDescricaoQuadro);		

		String tmpDescricaoQuadro = PromptUtil.getText(this, prompt);
		if( !StringUtil.isEmpty(tmpDescricaoQuadro) ) {
			strDescricaoQuadro = tmpDescricaoQuadro;
		}

		ArrayList<PromptOptionVO> lsPromptOptionsTensaoQuadro = new ArrayList<PromptOptionVO>();
		lsPromptOptionsTensaoQuadro.add(optTensaoQuadro22V);
		lsPromptOptionsTensaoQuadro.add(optTensaoQuadro380V);
		
		Double dTensaoQuadro = AppDefs.FIA_TENSAO_220V;
		PromptOptionVO optTensaoQuadro = PromptUtil.getKeyword(this, lsPromptOptionsTensaoQuadro, "Tensao do quadro: ");
		if(optTensaoQuadro != null) {
			dTensaoQuadro = StringUtil.safeDbl(nf6, optTensaoQuadro.getTextOption());
		}
		
		ArrayList<PromptOptionVO> lsPromptOptionsSistemaFase = new ArrayList<PromptOptionVO>();
		lsPromptOptionsSistemaFase.add(optSistemaFaseFN);
		lsPromptOptionsSistemaFase.add(optSistemaFase2F);
		lsPromptOptionsSistemaFase.add(optSistemaFase2FN);
		lsPromptOptionsSistemaFase.add(optSistemaFase3F);
		lsPromptOptionsSistemaFase.add(optSistemaFase3FN);
		lsPromptOptionsSistemaFase.add(optSistemaFaseFNT);
		lsPromptOptionsSistemaFase.add(optSistemaFase2FT);
		lsPromptOptionsSistemaFase.add(optSistemaFase2FNT);
		lsPromptOptionsSistemaFase.add(optSistemaFase3FT);
		lsPromptOptionsSistemaFase.add(optSistemaFase3FNT);
		
		String strSistemaFase = AppDefs.FIA_S_3FNT;
		PromptOptionVO optSistemaFase = PromptUtil.getKeyword(this, lsPromptOptionsSistemaFase, "Sistema de fase: ");
		if(optSistemaFase != null) {
			strSistemaFase = optSistemaFase.getTextOption();
		}
		
		result = new InputParamVO();
		result.initQuadroCargas(
			ptI3d, 
			strNomeQuadro,
			strDescricaoQuadro,
			dTensaoQuadro,
			strSistemaFase );
		return result;
	}

	@Override
	public void doCommand() 
	{
		Date dataAtualHora = new Date();
		
		Date dataAtual = new Date(dataAtualHora.getYear(), dataAtualHora.getMonth(), dataAtualHora.getDate());
		
		CadDocumentDef doc = this.getDoc();
		
		CadBlockDef currBlockDef = doc.getCurrBlockDef();		

		LayerTable layTbl = doc.getLayerTable();
		CadLayerDef oLayer = layTbl.getLayerDefByReference(AppDefs.LAYER_ELE_TEXTOS);
		if(oLayer == null) {
			oLayer = doc.getDefaultLayerDef();
		}
		
		// LEVEL
		//
		double zLevel = 0.0;
		
		CadLevel oLevel = GeomUtil.getCurrLevel();    	
		if(oLevel != null) {
			zLevel = oLevel.getZLevel();
		}
		
		//GEOMPOINT3D
		//
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		GeomPoint3d ptIns_orig = oParam.getPt0();
		
		//TO_LEVEL
		//
		GeomPoint3d ptIns = GeomUtil.toLevelFromPt3d(ptIns_orig); 
		
		String nomeQuadro = oParam.getNomeQuadro();
		String descricaoQuadro = oParam.getDescricaoQuadro();
		double dTensaoQuadro = oParam.getTensaoQuadro();
	  	double dBitolaMinimaCondutor = 2.5;      		// bitola nominal minima do condutor
	  	double dDisjuntorMinimoProtecao = 15;      		// disjuntor minimo de protecao
	  	double dTemperatura = 30.0;   					// temperatura ambiente
	  	double dFatorReducao = 0.9;			   			// fator de reducao
		String sistemaFase = oParam.getSistemaFase();
		
		//CADQUADROCARGAS
		//
		CadQuadroCargasEletrica o = CadQuadroCargasEletrica.create(
			currBlockDef, 
			oLayer,
			oLevel,
			ptIns,
			nomeQuadro,
			descricaoQuadro,
			dTensaoQuadro,
			dBitolaMinimaCondutor,
			dDisjuntorMinimoProtecao,
			dTemperatura,
			dFatorReducao,	
			sistemaFase );
		currBlockDef.addEntity(o);
		
		//FORM_DATA
		//
        MainPanel panel = MainPanel.getMainPanel();
		
		GerarPlanilhaCalculoQuadroCargasFrame frm = new GerarPlanilhaCalculoQuadroCargasFrame(this.getFrm());
		frm.init(panel, o);
		frm.show();
	}

}
