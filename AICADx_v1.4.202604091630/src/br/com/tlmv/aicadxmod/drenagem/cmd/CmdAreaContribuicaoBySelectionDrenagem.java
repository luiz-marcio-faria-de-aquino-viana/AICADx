/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdAreaContribuicaoBySelectionDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/11/2025
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
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CmdAreaContribuicaoBySelectionDrenagem extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optAreaDrenagem = new PromptOptionVO(AppDefs.OPT_AREATYPE_DRENAGEAREA, "Area Drenagem", "D", true);
	
//Public
	
	public CmdAreaContribuicaoBySelectionDrenagem() {
		super(AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYSELECTION, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("DRENAGEM: Adding new Area by Contour...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAreaDrenagem);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, "Nome da area (ou ENTER para nome do PV): ");
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODDRCAIXAINSPECAO, "Caixa de Inspecao: ");
		if(ent1 == null) return null;
		
		CadEntity ent2 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, "Bloco de Referencia: ");
		if(ent2 == null) return null;

		CadInsertBlock oInsBlk = (CadInsertBlock)ent2;
		DrawCache cache = oInsBlk.getDrawCache2d();
		
		//LIST_POINTS-3D
		//
		ArrayList<CadEntity> lsLines = new ArrayList<CadEntity>();
		
		String blockName = ((CadInsertBlock)ent2).getBlockName();

		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			CadBlockDef blkDef = blkTbl.getBlockDef(blockName);

			CadObject[] arrObj = PromptUtil.selectSubObject(this, blkDef, AppDefs.OBJTYPE_LINE, "Selecione primeira linha de contorno: ");
			while((arrObj != null)) {
				CadLine oLine = (CadLine)arrObj[1];
				lsLines.add(oLine);

				if(cache != null) {
					LineStringEntityDrawCache line = new LineStringEntityDrawCache(); 
					line.addLine3d(oLine.getPtI(), oLine.getPtF());
					cache.addItemSelected(line);
				}							
				arrObj = PromptUtil.selectSubObject(this, blkDef, AppDefs.OBJTYPE_LINE, "Selecione proxima linha de contorno: ");
			}
			oInsBlk.createAllDrawCache();
		}

		//ORDER_ENTITIES
		//
		result = new InputParamVO();
		result.initEntityKeyEntityAndArea(oKeyword, strName, ent1, ent2, lsLines);

		return result;
	}	
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			PromptOptionVO oKeyword = oParam.getKeyword();
			String strAreaName = oParam.getText();
			CadCaixaInspecaoDrenagem oEnt1 = (CadCaixaInspecaoDrenagem)oParam.getEnt1();
			CadInsertBlock oEnt2 = (CadInsertBlock)oParam.getEnt2();
			ArrayList<CadEntity> lsEntities = oParam.getLsEntities(); 
			
			// AREA_NAME
			//
			String text = strAreaName;
			
			if( "".equals(strAreaName) ) {
				text = oEnt1.getPv();														
			}
			
			//PRE-PROCESSING
			//
			ArrayList<GeomPoint3d> lsPts3d_orig = GeomUtil.sortSequentialLines(lsEntities);
			if(lsPts3d_orig != null) {

				//TO_LEVEL
				//
				CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());

				ArrayList<GeomPoint3d> lsPts3d = GeomUtil.toLevelFromLsPts3d(lsPts3d_orig, oLevel); 

				GeomPoint3d pt0 = lsPts3d.get(0);
				lsPts3d.add(pt0);
				
				//CADPOLYGON
				//
				LayerTable oTbl = this.getDoc().getLayerTable();
		
				CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_AREAS_CONTRIB);
				
				CadAreaContribuicaoDrenagem oArea = CadAreaContribuicaoDrenagem.create(currBlockDef, oLayer, oLevel, oKeyword.getOptionId(), text, lsPts3d, oEnt1);
				currBlockDef.addEntity(oArea);
			}
			else {
				PromptUtil.prompt("Err: Nao foi possivel criar o poligono correspondente as areas de contribuicao.");
			}
		}
	}

}
