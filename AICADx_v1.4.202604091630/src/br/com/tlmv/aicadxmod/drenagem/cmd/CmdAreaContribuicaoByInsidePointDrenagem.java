/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdAreaContribuicaoByInsidePointDrenagem.java
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
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
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

public class CmdAreaContribuicaoByInsidePointDrenagem extends CmdBase
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
	
	public CmdAreaContribuicaoByInsidePointDrenagem() {
		super(AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYINSIDEPOINT, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("DRENAGEM: Adiciona Area de Contribuicao por Ponto Interior...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAreaDrenagem);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, "Nome da area (ou ENTER para nome do PV): ");
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODDRCAIXAINSPECAO, "Caixa de Inspecao: ");
		if(ent1 == null) return null;
		
		CadEntity ent2 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, "Bloco de Referencia: ");
		
		//ORDER_ENTITIES
		//
		result = new InputParamVO();
		result.initEntityKeyEntityAndBlkRef(oKeyword, strName, ent1, ent2);

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
			
			String blockName = null;		
			CadInsertBlock oInsBlk = null;

			DrawCache cache = null;
			
			if(oEnt1 != null) {
				oInsBlk = (CadInsertBlock)oEnt2;
				blockName = oInsBlk.getBlockName();

				cache = oInsBlk.getDrawCache2d();
			}
			
			// AREA_NAME
			//
			String text = strAreaName;
			
			if( "".equals(strAreaName) ) {
				text = oEnt1.getPv();														
			}

			// BLOCK_DEF
			//
			CadBlockDef blkDef = currBlockDef;
			if(blockName != null) {		
				BlockTable blkTbl = this.getDoc().getBlockTable();
				if( blkTbl.hasBlockDef(blockName) ) {
					blkDef = blkTbl.getBlockDef(blockName);
				}
			}

			//PRE-PROCESSING
			//
			CadEntity[] arrAllLineEntities = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_LINE);
			int szLsAllLineEntities = arrAllLineEntities.length;
			if(szLsAllLineEntities > 0) {
				GeomPoint2d ptInside2d = new GeomPoint2d( oEnt1.getPtIns() );
			
				ArrayList<CadEntity> lsSelectedLines = this.processaAlgor1(blkDef, cache, ptInside2d, arrAllLineEntities);
	
				ArrayList<GeomPoint3d> lsPts3d_orig = GeomUtil.sortSequentialLines(lsSelectedLines);
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

}
