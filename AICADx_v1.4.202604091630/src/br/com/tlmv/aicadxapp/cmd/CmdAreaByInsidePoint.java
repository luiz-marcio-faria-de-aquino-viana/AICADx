/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdAreaByInsidePoint.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/11/2025
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
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadArea;
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
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.SysUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdAreaByInsidePoint extends CmdBase
{

	/* Methodes */
	
	private ArrayList<PromptOptionVO> getPromptOptionAreaType()
	{
		/* PromptOption
		*/
		PromptOptionVO optAmbiente = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_ROOM, this.getR().getString( R.CMD_OPT_AREATYPE_ROOM ), "A", false);
		PromptOptionVO optApartamento = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_APARTMENT, this.getR().getString( R.CMD_OPT_AREATYPE_APARTMENT ), "P", true);
		PromptOptionVO optVaranda = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BALCONY, this.getR().getString( R.CMD_OPT_AREATYPE_BALCONY ), "V", false);
		PromptOptionVO optAreaComum = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BUILDINGCOMMOM, this.getR().getString( R.CMD_OPT_AREATYPE_BUILDINGCOMMOM ), "C", false);
		PromptOptionVO optAreaInterna = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BUILDINGINTERNAL, this.getR().getString( R.CMD_OPT_AREATYPE_BUILDINGINTERNAL ), "I", false);
		PromptOptionVO optAreaExterna = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_BUILDINGEXTERNAL, this.getR().getString( R.CMD_OPT_AREATYPE_BUILDINGEXTERNAL ), "E", false);
		PromptOptionVO optEstacionamento = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_PARKING, this.getR().getString( R.CMD_OPT_AREATYPE_PARKING ), "S", false);
		PromptOptionVO optTerreno = new PromptOptionVO(
			AppDefs.OPT_AREATYPE_TERRAIN, this.getR().getString( R.CMD_OPT_AREATYPE_TERRAIN ), "T", false);

		/* ListOfPromptOptions
		*/
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();

		lsPromptOptions.add(optAmbiente);
		lsPromptOptions.add(optApartamento);
		lsPromptOptions.add(optVaranda);
		lsPromptOptions.add(optAreaComum);
		lsPromptOptions.add(optAreaInterna);
		lsPromptOptions.add(optAreaExterna);
		lsPromptOptions.add(optEstacionamento);
		lsPromptOptions.add(optTerreno);

		return lsPromptOptions;
	}	
	
	/* Methode */
	
	public void debugFase1(int debugLevel, DrawCache cache, GeomPoint2d ptInside2d, GeomPoint2d ptDir2d, ArrayList<CadEntity> lsTmpLines)
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
		if(lsTmpLines != null) {
			for(CadEntity ent : lsTmpLines) {
				CadLine oLine = (CadLine)ent;
				
				line = new LineStringEntityDrawCache(); 
				line.addLine3d(oLine.getPtI(), oLine.getPtF());
				cache.addItemSelected(line);
			}
		}													
		this.refreshAll();

		PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PRESSENTERTOCONTINUE ) );

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

		PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PRESSENTERTOCONTINUE ) );

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

		PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PRESSENTERTOCONTINUE ) );

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

		PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PRESSENTERTOCONTINUE ) );

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

			this.debugFase1(AppDefs.DEBUG_LEVEL27, cache, ptInside2d, ptDir2d, lsTmpLines);
			
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
	
	public CmdAreaByInsidePoint() {
		super(AppDefs.ACTION_DRAW1_AREA_BYINSIDEPOINT, true, true);
	}
	
	/* Methodes */

	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_DRAW_AREA_BY_INSIDEPOINT ) );

		ArrayList<PromptOptionVO> lsPromptOptions = this.getPromptOptionAreaType();
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString( R.CMD_PRT_CHOICE_AREA_TYPE ) );
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, this.getR().getString( R.CMD_PRT_AREA_NAME ) );
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_INSERTBLOCK, this.getR().getString( R.CMD_PRT_SELECT_BLOCK ) );

		GeomPoint3d ptInside3d = PromptUtil.getPoint3d(this, this.getR().getString( R.CMD_PRT_REFERENCE_INSIDEPOINT ) ); 
		if(ptInside3d == null) return null;

		//ORDER_ENTITIES
		//
		result = new InputParamVO();
		result.initEntityKeyEntityWithPoint(oKeyword, strName, ent1, ptInside3d);

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
			GeomPoint3d ptInside3d = oParam.getPt0();
			
			String blockName = null;		
			CadInsertBlock oInsBlk = null;

			DrawCache cache = null;
			
			if(oEnt1 != null) {
				oInsBlk = (CadInsertBlock)oEnt1;
				blockName = oInsBlk.getBlockName();

				cache = oInsBlk.getDrawCache2d();
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
			PromptUtil.prompt( this.getR().getString( R.TXT_PROCESSING ) );

			CadEntity[] arrAllLineEntities = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_LINE);
			int szLsAllLineEntities = arrAllLineEntities.length;
			if(szLsAllLineEntities > 0) {

				GeomPoint2d ptInside2d = new GeomPoint2d( ptInside3d );
			
				ArrayList<CadEntity> lsSelectedLines = this.processaAlgor1(blkDef, cache, ptInside2d, arrAllLineEntities);

				ArrayList<GeomPoint3d> lsPts3d_orig = GeomUtil.sortSequentialLines(lsSelectedLines);

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
					PromptUtil.prompt( this.getR().getString( R.ERR_CANT_CREATE_AREA_POLYGON ) );
				}
			}
		}
	}

}
