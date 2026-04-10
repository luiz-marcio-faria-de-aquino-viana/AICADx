/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdMultCaixaInspecaoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/08/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CmdMultCaixaInspecaoDrenagem extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optManual = new PromptOptionVO(AppDefs.OPT_CAIXA_INSPECAO_MANUAL, "Manual", "M", true);
	private PromptOptionVO optAutomaticaPonto = new PromptOptionVO(AppDefs.OPT_CAIXA_INSPECAO_AUTOMATICA_PONTO, "Automatica Ponto", "P", false);
	private PromptOptionVO optAutomaticaTrecho = new PromptOptionVO(AppDefs.OPT_CAIXA_INSPECAO_AUTOMATICA_TRECHO, "Automatica Trecho", "T", false);

//Public

	public CmdMultCaixaInspecaoDrenagem() {
		super(AppDefs.ACTION_RDP1_INSERE_MULT_CI, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("DRENAGEM: Adding Multiple Caixa de Inspecao (CI)...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optManual);
		lsPromptOptions.add(optAutomaticaPonto);
		//lsPromptOptions.add(optAutomaticaTrecho);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de operacao: ");
		if(oKeyword == null) return null;
		
		if( oKeyword.getOptionId() == AppDefs.OPT_CAIXA_INSPECAO_MANUAL ) {
			//OPT_CAIXA_INSPECAO_MANUAL
			//
			ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>(); 
		
			GeomPoint3d ptI3d = PromptUtil.getStartPoint3d(this, null, null, "Insert first point: ");
			while(ptI3d != null) {
				lsPts.add(ptI3d);
	
				GeomPoint3d ptF3d = new GeomPoint3d(ptI3d);
				ptI3d = PromptUtil.getSecondPoint3d(this, ptF3d, lsPts, "Insert next point: ");
			}
		
			int szLsPts = lsPts.size();
			if(szLsPts == 0) return null;
		
			result = new InputParamVO();
			result.initKeyLsPts(oKeyword, lsPts);
		}
		else if( oKeyword.getOptionId() == AppDefs.OPT_CAIXA_INSPECAO_AUTOMATICA_PONTO ) {
			//OPT_CAIXA_INSPECAO_AUTOMATICA_PONTO
			//
			result = new InputParamVO();
			result.initKey(oKeyword);
		}
		else if( oKeyword.getOptionId() == AppDefs.OPT_CAIXA_INSPECAO_AUTOMATICA_TRECHO ) {
			//
			//TODO:
			//
		}
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			PromptOptionVO oKeyword = oParam.getKeyword(); 
			if( oKeyword.getOptionId() == AppDefs.OPT_CAIXA_INSPECAO_MANUAL ) {
				
				/* OPT_CAIXA_INSPECAO_MANUAL */
				
				//GEOMPOINT3D
				//
				ArrayList<GeomPoint3d> lsPts_orig = oParam.getLsPts3d(); 

				//TO_LEVEL
				//
				CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
				
				ArrayList<GeomPoint3d> lsPts = GeomUtil.toLevelFromLsPts3d(lsPts_orig, oLevel); 
				int szLsPts = lsPts.size();
				
				//CADCAIXAINSPECAO
				//
				LayerTable oTbl = this.getDoc().getLayerTable();
		
				CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);
	
				CadCaixaInspecaoDrenagem oCIAnterior = null;
				for(GeomPoint3d oPt3d : lsPts) {
					CadCaixaInspecaoDrenagem oCIAtual = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, oPt3d);
					currBlockDef.addEntity(oCIAtual);
	
					if(oCIAnterior != null) {
						int numeroCIAtual = oCIAtual.getNumeroCI();
						
						oCIAnterior.setProximaCI(numeroCIAtual);
						oCIAnterior.setProximo(oCIAtual);
					}				
					oCIAnterior = oCIAtual;
				}
			}
			else if( oKeyword.getOptionId() == AppDefs.OPT_CAIXA_INSPECAO_AUTOMATICA_PONTO ) {

				/* OPT_CAIXA_INSPECAO_AUTOMATICA_PONTO */
				
		        MainPanel panel = MainPanel.getMainPanel();

				ICompView v = panel.getCurrView();

				ICadViewBase cv = v.getCadViewBase();

				//LAYER
				//
		        LayerTable oTbl = this.getDoc().getLayerTable();
				
				CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);
	
				//GEOMPOINT3D
				//
				ArrayList<GeomPoint3d> lsPtIns = new ArrayList<GeomPoint3d>(); 
				
				//CADCAIXAINSPECAO
				//
				CadEntity[] lsEntity = currBlockDef.findAllEntityByObjType(AppDefs.OBJTYPE_INSERTBLOCK);
				GeomPoint2d pt2dMcs = new GeomPoint2d(0.0, 0.0);
				for(CadEntity oEnt : lsEntity) {
					ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();
					lsPts.addAll( oEnt.osnap3d(cv, AppDefs.OSNAPMODE_ALL, pt2dMcs) );
					
					ArrayList<GeomPoint3d> lsPtsOsnapMode = GeomUtil.filterByTag3d(AppDefs.OSNAPMODE_CENTER, lsPts);
					int sz = lsPtsOsnapMode.size();
					if(sz > 0) {
						lsPtIns.addAll(lsPtsOsnapMode);
					}
					else {
						lsPtsOsnapMode = GeomUtil.filterByTag3d(AppDefs.OSNAPMODE_MIDDLE, lsPts);
						sz = lsPtsOsnapMode.size();
						if(sz > 0) {
							lsPtIns.addAll(lsPtsOsnapMode);
						}
						else {
							lsPtsOsnapMode = GeomUtil.filterByTag3d(AppDefs.OSNAPMODE_NODEPOINT, lsPts);
							sz = lsPtsOsnapMode.size();
							if(sz > 0) {
								lsPtIns.addAll(lsPtsOsnapMode);
							}							
							else {
								lsPtsOsnapMode = GeomUtil.filterByTag3d(AppDefs.OSNAPMODE_ENDPOINT, lsPts);
								sz = lsPtsOsnapMode.size();
								if(sz > 0) {
									lsPtIns.addAll(lsPtsOsnapMode);
								}
								else {
									lsPtsOsnapMode = GeomUtil.filterByTag3d(AppDefs.OSNAPMODE_QUADRANT, lsPts);
									sz = lsPtsOsnapMode.size();
									if(sz > 0) {
										lsPtIns.addAll(lsPtsOsnapMode);
									}
								}
							}
						}
					}
					
					for(GeomPoint3d oPt3d : lsPtIns) {
						CadCaixaInspecaoDrenagem oCIAtual = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, null, oPt3d);
						currBlockDef.addEntity(oCIAtual);
					}
				}
			}
			else if( oKeyword.getOptionId() == AppDefs.OPT_CAIXA_INSPECAO_AUTOMATICA_TRECHO ) {

				/* OPT_CAIXA_INSPECAO_AUTOMATICA_TRECHO */
				
		        MainPanel panel = MainPanel.getMainPanel();

				ICompView v = panel.getCurrView();

				ICadViewBase cv = v.getCadViewBase();

				//LAYER
				//
		        LayerTable oTbl = this.getDoc().getLayerTable();
				
				CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);
	
				//GEOMPOINT3D
				//
				ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>(); 
				
				//
				//TODO:
				//
			}
			
	        MainPanel panel = MainPanel.getMainPanel();
			
			CompCommandPrompt commandPrompt = panel.getCommandPrompt();
			commandPrompt.setCommandPromptFocus(false);
		}
	}

}
