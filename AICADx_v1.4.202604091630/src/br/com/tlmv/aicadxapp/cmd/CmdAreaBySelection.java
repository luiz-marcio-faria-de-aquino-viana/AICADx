/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdAreaBySelection.java
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

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadArea;
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
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CmdAreaBySelection extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optAmbiente = new PromptOptionVO(AppDefs.OPT_AREATYPE_ROOM, "Ambiente", "A", false);
	private PromptOptionVO optApartamento = new PromptOptionVO(AppDefs.OPT_AREATYPE_APARTMENT, "aPartamento", "P", true);
	private PromptOptionVO optVaranda = new PromptOptionVO(AppDefs.OPT_AREATYPE_BALCONY, "Varanda", "V", false);
	private PromptOptionVO optAreaComum = new PromptOptionVO(AppDefs.OPT_AREATYPE_BUILDINGCOMMOM, "area Comum", "C", false);
	private PromptOptionVO optAreaInterna = new PromptOptionVO(AppDefs.OPT_AREATYPE_BUILDINGINTERNAL, "area Interna", "I", false);
	private PromptOptionVO optAreaExterna = new PromptOptionVO(AppDefs.OPT_AREATYPE_BUILDINGEXTERNAL, "area Externa", "E", false);
	private PromptOptionVO optEstacionamento = new PromptOptionVO(AppDefs.OPT_AREATYPE_PARKING, "eStacionamento", "S", false);
	private PromptOptionVO optTerreno = new PromptOptionVO(AppDefs.OPT_AREATYPE_TERRAIN, "Terreno", "T", false);
	
//Public
	
	public CmdAreaBySelection() {
		super(AppDefs.ACTION_DRAW1_AREA_BYSELECTION, true, true);
	}
	
	/* Methodes */
	
	public InputParamVO promptInputParam_202511152248(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("Adding new Area by Contour...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAmbiente);
		lsPromptOptions.add(optApartamento);
		lsPromptOptions.add(optVaranda);
		lsPromptOptions.add(optAreaComum);
		lsPromptOptions.add(optAreaInterna);
		lsPromptOptions.add(optAreaExterna);
		lsPromptOptions.add(optEstacionamento);
		lsPromptOptions.add(optTerreno);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, "Nome da area: ");
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, "Bloco de Referencia: ");
		if(ent1 == null) return null;

		CadInsertBlock oInsBlk = (CadInsertBlock)ent1;
		DrawCache cache = oInsBlk.getDrawCache2d();
		
		//LIST_POINTS-3D
		//
		ArrayList<CadEntity> lsLines = new ArrayList<CadEntity>();
		
		String blockName = ((CadInsertBlock)ent1).getBlockName();

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
		result.initEntityKeyEntityAndArea(oKeyword, strName, ent1, lsLines);

		return result;
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("Adding new Area by Contour...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAmbiente);
		lsPromptOptions.add(optApartamento);
		lsPromptOptions.add(optVaranda);
		lsPromptOptions.add(optAreaComum);
		lsPromptOptions.add(optAreaInterna);
		lsPromptOptions.add(optAreaExterna);
		lsPromptOptions.add(optEstacionamento);
		lsPromptOptions.add(optTerreno);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, "Nome da area: ");
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, "Bloco de Referencia: ");

		String blockName = null;		
		CadInsertBlock oInsBlk = null;

		DrawCache cache = null;
		
		if(ent1 != null) {
			oInsBlk = (CadInsertBlock)ent1;
			blockName = oInsBlk.getBlockName();

			cache = oInsBlk.getDrawCache2d();
		}
		
		//LIST_POINTS-3D
		//
		ArrayList<CadEntity> lsLines = new ArrayList<CadEntity>();
		
		BlockTable blkTbl = this.getDoc().getBlockTable();

		CadBlockDef blkDef = null;
		if(blockName != null) {		
			if( blkTbl.hasBlockDef(blockName) ) {
				blkDef = blkTbl.getBlockDef(blockName);

				CadObject[] arrObj = PromptUtil.selectSubObject(this, blkDef, AppDefs.OBJTYPE_LINE, "Selecione primeira linha de contorno: ");
				while(arrObj != null) {
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
		}
		else {
			blkDef = this.getDoc().getCurrBlockDef();

			CadEntity ent = PromptUtil.selectObject(this, AppDefs.OBJTYPE_LINE, "Selecione primeira linha de contorno: ");
			while(ent != null) {
				CadLine oLine = (CadLine)ent;
				lsLines.add(oLine);

				if(cache != null) {
					LineStringEntityDrawCache line = new LineStringEntityDrawCache(); 
					line.addLine3d(oLine.getPtI(), oLine.getPtF());
					cache.addItemSelected(line);
				}							
				
				ent = PromptUtil.selectObject(this, AppDefs.OBJTYPE_LINE, "Selecione proxima linha de contorno: ");
			}
			oInsBlk.createAllDrawCache();
		}
		if( (lsLines == null) || (lsLines.size() == 0) ) return null;

		//ORDER_ENTITIES
		//
		result = new InputParamVO();
		result.initEntityKeyEntityAndArea(oKeyword, strName, ent1, lsLines);

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
			String text = oParam.getText();
			CadInsertBlock oEnt1 = (CadInsertBlock)oParam.getEnt1();
			ArrayList<CadEntity> lsEntities = oParam.getLsEntities(); 

			//PRE-PROCESSING
			//
			ArrayList<GeomPoint3d> lsPts3d_orig = GeomUtil.sortSequentialLines(lsEntities);

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			ArrayList<GeomPoint3d> lsPts3d = GeomUtil.toLevelFromLsPts3d(lsPts3d_orig, oLevel); 
			if(lsPts3d != null) {
				GeomPoint3d pt0 = lsPts3d.get(0);
				lsPts3d.add(pt0);
				
				//CADPOLYGON
				//
				LayerTable oTbl = this.getDoc().getLayerTable();
		
				CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_AREAS);
				
				CadArea oArea = CadArea.create(currBlockDef, oLayer, oLevel, oKeyword.getOptionId(), text, lsPts3d);
				currBlockDef.addEntity(oArea);
			}
			else {
				PromptUtil.prompt("Err: Nao foi possivel criar o poligono correspondente as areas.");
			}
		}
	}

}
