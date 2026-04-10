/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdAreaContribuicaoByMultInsidePointDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/11/2025
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
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.SysUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.vo.MultAreaContribuicaoDrenagemVO;

public class CmdAreaContribuicaoByMultInsidePointDrenagem extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optAreaDrenagem = new PromptOptionVO(AppDefs.OPT_AREATYPE_DRENAGEAREA, "Area Drenagem", "D", true);
	
	/* Methode */
	
	public void debugFase1(int debugLevel, DrawCache cache, GeomPoint2d ptInside2d, GeomPoint2d ptDir2d, CadEntity[] arrTmpLines)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		if(cache == null) return;
		
		long delayTimeMili = 50;

		// DIRECTION
		//
		LineStringEntityDrawCache line = new LineStringEntityDrawCache(); 
		line.addLine2d(ptInside2d, ptDir2d);
		cache.addItemSelected(line);

		// INTERSEC_LINES
		//
		if(arrTmpLines != null) {
			for(CadEntity ent : arrTmpLines) {
				CadLine oLine = (CadLine)ent;
				
				line = new LineStringEntityDrawCache(); 
				line.addLine3d(oLine.getPtI(), oLine.getPtF());
				cache.addItemSelected(line);
			}
		}													
		this.refreshAll();

		PromptUtil.prompt("=== FINISH FASE 1 ===");

		SysUtil.delay(delayTimeMili);
		cache.clearSelected();
	}
		
	public void debugFase2(int debugLevel, DrawCache cache, CadEntity oNearEnt)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		if(cache == null) return;

		long delayTimeMili = 50;

		if(oNearEnt != null) {
			CadLine oNearLine = (CadLine)oNearEnt;

			LineStringEntityDrawCache line = new LineStringEntityDrawCache(); 
			line.addLine3d(oNearLine.getPtI(), oNearLine.getPtF());
			cache.addItemSelected(line);
		}
		this.refreshAll();

		PromptUtil.prompt("=== FINISH FASE 2 ===");

		SysUtil.delay(delayTimeMili);
		cache.clearSelected();
	}
						
	public void debugFase3(int debugLevel, DrawCache cache, ArrayList<CadEntity> lsAllNearestLines)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		if(cache == null) return;

		long delayTimeMili = 50;

		// NEAREST_LINES
		//
		if(lsAllNearestLines != null) {
			for(CadEntity ent : lsAllNearestLines) {
				CadLine oLine = (CadLine)ent;
				
				LineStringEntityDrawCache line = new LineStringEntityDrawCache(); 
				line.addLine3d(oLine.getPtI(), oLine.getPtF());
				cache.addItemSelected(line);
			}
		}						
		this.refreshAll();

		PromptUtil.getText(this, "=== FINISH FASE 3 ===");

		SysUtil.delay(delayTimeMili);
		cache.clearSelected();
	}
	
	public void debugFase4(int debugLevel, DrawCache cache, ArrayList<CadEntity> lsLines)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		if(cache == null) return;

		long delayTimeMili = 50;

		// SELECTED_LINES
		//
		if(lsLines != null) {
			for(CadEntity ent : lsLines) {
				CadLine oLine = (CadLine)ent;
				
				LineStringEntityDrawCache line = new LineStringEntityDrawCache(); 
				line.addLine3d(oLine.getPtI(), oLine.getPtF());
				cache.addItemSelected(line);
			}
		}						
		this.refreshAll();

		PromptUtil.getText(this, "=== FINISH FASE 4 ===");

		SysUtil.delay(delayTimeMili);
		cache.clearSelected();
	}	
	
	/* BASIC_ALGORITM - ALGOR_1 */
	
	public ArrayList<CadEntity> processaAlgor1(CadBlockDef blkDef, DrawCache cache, GeomPoint2d ptInside2d, CadEntity[] arrEntities)
	{
		ArrayList<CadEntity> lsLines = null;
		
		ArrayList<CadEntity> lsAllNearestLines = new ArrayList<CadEntity>();
		Hashtable map = new Hashtable();
		
		// NEAREST_LINES
		//
		double xPtInside = ptInside2d.getX();
		double yPtInside = ptInside2d.getY();
								
		int numSteps = AppDefs.AREADETECTION_NUMBER_RAYS;
		double stepAngle = AppDefs.MATHVAL_2PI / ((double)numSteps);
	
		double currAngle = 0;
		for(int i = 0; i < numSteps; i++) {
			double dx = Math.cos(currAngle) * 10.0;
			double dy = Math.sin(currAngle) * 10.0;
					
			GeomPoint2d ptDir2d = new GeomPoint2d(xPtInside + dx, yPtInside + dy);
			
			ArrayList<CadEntity> lsTmpLines = GeomUtil.selectAllLinesAtDirection(ptInside2d, ptDir2d, arrEntities);
			CadEntity[] arrTmpLines = CadUtil.toArrEntities(lsTmpLines);		
			
			this.debugFase1(AppDefs.DEBUG_LEVEL27, cache, ptInside2d, ptDir2d, arrTmpLines);
			
			CadEntity oNearEnt = GeomUtil.selectNearestLineAtDirection(ptInside2d, ptDir2d, arrTmpLines);
			if(oNearEnt != null) {
				CadLine oNearLine = (CadLine)oNearEnt;
	
				this.debugFase2(AppDefs.DEBUG_LEVEL27, cache, oNearEnt);
	
				Integer objectId = oNearLine.getObjectId();
				if( !map.containsKey(objectId) ) {
					map.put(objectId, oNearLine);
					
					lsAllNearestLines.add(oNearLine);
				}
			}
			currAngle = currAngle + stepAngle;
		}
		
		this.debugFase3(AppDefs.DEBUG_LEVEL27, cache, lsAllNearestLines);						
				
		// SELECTED_LINES
		//
		lsLines = GeomUtil.selectConnectedLines(lsAllNearestLines);
		
		this.debugFase4(AppDefs.DEBUG_LEVEL27, cache, lsLines);
		
		return lsLines;
	}
			
//Public
	
	public CmdAreaContribuicaoByMultInsidePointDrenagem() {
		super(AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYMULTINSIDEPOINT, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("DRENAGEM: Adiciona Areas de Contribuicao Automaticamente...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAreaDrenagem);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, "Prefixo da area (ou ENTER para nome do PV): ");
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, "Bloco de Referencia: ");
		
		//ORDER_ENTITIES
		//
		result = new InputParamVO();
		result.initEntityKeyEntity(oKeyword, strName, ent1);

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
			CadInsertBlock oEnt1 = (CadInsertBlock)oParam.getEnt1();
			
			String blockName = null;		
			CadInsertBlock oInsBlk = null;

			DrawCache cache = null;
			
			if(oEnt1 != null) {
				oInsBlk = (CadInsertBlock)oEnt1;
				blockName = oInsBlk.getBlockName();

				cache = oInsBlk.getDrawCache2d();
			}

			CadEntity[] lsAllCaixaInspecao = currBlockDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
			int szLsAllCaixaInspecao = lsAllCaixaInspecao.length;
			if(szLsAllCaixaInspecao > 0) {

				// BLOCK_DEF
				//
				CadBlockDef blkDef = currBlockDef;
				if(blockName != null) {		
					BlockTable blkTbl = this.getDoc().getBlockTable();
					if( blkTbl.hasBlockDef(blockName) ) {
						blkDef = blkTbl.getBlockDef(blockName);
					}
				}
			
				// ALL_ENTITIES
				//
				CadEntity[] lsAllLineEntities = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_LINE);
				int szLsAllLineEntities = lsAllLineEntities.length;
				if(szLsAllLineEntities > 0) {

					// PROCESS_ALGOR1
					//
					ArrayList<MultAreaContribuicaoDrenagemVO> lsMultAreaContrib = new ArrayList<MultAreaContribuicaoDrenagemVO>();
				
					for(CadEntity ent : lsAllCaixaInspecao) {
						CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)ent;
						int numeroCI = oCI.getNumeroCI();
						
						GeomPoint2d ptInside2d = new GeomPoint2d( oCI.getPtIns() );
	
						int blkObjectId = -1;
						if(oInsBlk != null)
							blkObjectId = oInsBlk.getObjectId();
						
						ArrayList<CadEntity> lsLines = this.processaAlgor1(blkDef, cache, ptInside2d, lsAllLineEntities);
						if(lsLines != null) {
							MultAreaContribuicaoDrenagemVO oAreaContrib = new MultAreaContribuicaoDrenagemVO(
								oKeyword,
								strAreaName,
								numeroCI,
								oCI,
								blkObjectId,
								oInsBlk,
								blockName,	
								blkDef,
								lsLines);
							lsMultAreaContrib.add(oAreaContrib);
						}
					}
					
					// DRAW_AREA_CONTRIB
					//
					int numSuccess = 0;
					int numFail = 0;
					for(MultAreaContribuicaoDrenagemVO oAreaContrib : lsMultAreaContrib) {
						int pos = numSuccess + 1;
						
						CadBlockDef ofBlkDef = oAreaContrib.getBlkDef();
						PromptOptionVO ofKeyword = oAreaContrib.getKeyword();	
						String ofPrefixoAreaContrib = oAreaContrib.getAreaPrefix(); 
						CadCaixaInspecaoDrenagem ofCI = (CadCaixaInspecaoDrenagem)oAreaContrib.getCI();
						ArrayList<CadEntity> lsLines = oAreaContrib.getLsLines();
						int szLsLines = lsLines.size();
						
						int qtdEstimadaPts = szLsLines + 1;

						// AREA_NAME
						//
						String ofAreaName = null;
						
						if( !"".equals(ofPrefixoAreaContrib) ) {
							ofAreaName = String.format( "%s-%s", ofPrefixoAreaContrib, Integer.toString(pos) );
						}
						else {
							ofAreaName = ofCI.getPv();														
						}
						
						ArrayList<GeomPoint3d> lsPts3d_orig = GeomUtil.sortSequentialLines(lsLines);
						if(lsPts3d_orig != null) {

							//TO_LEVEL
							//
							CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
							
							ArrayList<GeomPoint3d> lsPts3d = GeomUtil.toLevelFromLsPts3d(lsPts3d_orig, oLevel); 

							int szLsPts = lsPts3d.size();
							if(szLsPts < qtdEstimadaPts) {
								numFail += 1;
							}
							else {
								numSuccess += 1;
							}
								
							GeomPoint3d pt0 = lsPts3d.get(0);
							lsPts3d.add(pt0);
							
							//CADPOLYGON
							//
							LayerTable oTbl = this.getDoc().getLayerTable();
					
							CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_AREAS_CONTRIB);
							
							CadAreaContribuicaoDrenagem oArea = CadAreaContribuicaoDrenagem.create(
								currBlockDef, 
								oLayer,
								oLevel,
								ofKeyword.getOptionId(), 
								ofAreaName, 
								lsPts3d, 
								ofCI );
							currBlockDef.addEntity(oArea);
						}
					}

					// SHOW_RESULTS
					//
					int total = numSuccess + numFail;
					String msg = String.format(
						"Resultado do processamento: Total Caixas Inspecao = %s; Quantidade de Areas Calculadas com Sucesso = %s; Quantidade de erros = %s; ",
						total,
						numSuccess,
						numFail);
					PromptUtil.prompt(msg);
					
					this.refreshAll();
				}
			}
		}
	}

}
