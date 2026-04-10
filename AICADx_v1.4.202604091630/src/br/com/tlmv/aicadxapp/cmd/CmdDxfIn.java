/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdDxfIn.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
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

import java.awt.FileDialog;
import java.io.FilenameFilter;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfEntry;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfFile;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfSection;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdDxfIn extends CmdBase
{
//Private Static
	private static String gLayerName = "0-BASE";

	private static String[] gExtension = { 
		"dxf"
	};
	
//Private

	/* PromptOption
	*/
	private PromptOptionVO optDxfInBlock = new PromptOptionVO(AppDefs.OPT_DXFIN_BLOCK_VAL, AppDefs.OPT_DXFIN_BLOCK_STR, "B", true);
	private PromptOptionVO optDxfInLoad = new PromptOptionVO(AppDefs.OPT_DXFIN_LOAD_VAL, AppDefs.OPT_DXFIN_LOAD_STR, "L", false);
	
	/* Methodes */
	
	private ArrayList<CadEntity> insertLine(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		int n = 0;
		
		ArrayList<DxfCadEntity> lsCadEntity = dxfSection.filterDxfCadEntityByEntityType(AppDefs.DXFETYPE_LINE);
		int sz = lsCadEntity.size();
		for(int i = 0; i < sz; i++) {
			DxfCadEntity o = lsCadEntity.get(i);
			
			DxfEntry oXI = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_X);
			DxfEntry oYI = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Y);
			DxfEntry oZI = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Z);
			
			DxfEntry oXF = o.getByDxfCode(AppDefs.DXFCODE_ENDPOINT_X);
			DxfEntry oYF = o.getByDxfCode(AppDefs.DXFCODE_ENDPOINT_Y);
			DxfEntry oZF = o.getByDxfCode(AppDefs.DXFCODE_ENDPOINT_Z);
			
			//GEOMPOINT3D
			//
			//PtI
			double xI = StringUtil.safeDbl(nf6, oXI.getDxfVal());
			double yI = StringUtil.safeDbl(nf6, oYI.getDxfVal());
			double zI = StringUtil.safeDbl(nf6, oZI.getDxfVal());
			
			GeomPoint3d pt3dI_orig = new GeomPoint3d(xI, yI, zI);

			//PtF
			double xF = StringUtil.safeDbl(nf6, oXF.getDxfVal());
			double yF = StringUtil.safeDbl(nf6, oYF.getDxfVal());
			double zF = StringUtil.safeDbl(nf6, oZF.getDxfVal());
			
			GeomPoint3d pt3dF_orig = new GeomPoint3d(xF, yF, zF);
			
			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d pt3dI = GeomUtil.toLevelFromPt3d(pt3dI_orig, oLevel); 
			GeomPoint3d pt3dF = GeomUtil.toLevelFromPt3d(pt3dF_orig, oLevel); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadLine oLine = CadLine.create(currBlockDef, oLayer, oLevel, pt3dI, pt3dF);
			currBlockDef.addEntity(oLine);
			
			lsResult.add(oLine);
			
			n += 1;
		}
		return lsResult;
	}
	
	private ArrayList<CadEntity> insertPolyline(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		int n = 0;
		
		ArrayList<DxfCadEntity> lsCadEntity = dxfSection.filterDxfCadEntityByEntityType(AppDefs.DXFETYPE_POLYLINE);
		int sz = lsCadEntity.size();
		for(int i = 0; i < sz; i++) {
			DxfCadEntity o = lsCadEntity.get(i);
			
			DxfEntry oFF_I = o.getByDxfCode(AppDefs.DXFCODE_ENTITIESFOLLOWFLAG);

			DxfEntry oXI = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_X);
			DxfEntry oYI = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Y);
			DxfEntry oZI = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Z);
			
			int startPos = o.getDxfEntityNum();
			ArrayList<DxfCadEntity> lsChild = dxfSection.filterDxfChildCadEntityByEntityType(startPos, AppDefs.DXFETYPE_VERTEX, AppDefs.DXFETYPE_SEQEND);
			int szLsChild = lsChild.size();
			for(int j = 0; j < szLsChild; j++) {				
				DxfCadEntity oChild = lsChild.get(j);

				DxfEntry oXF = oChild.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_X);
				DxfEntry oYF = oChild.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Y);
				DxfEntry oZF = oChild.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Z);
				
				if(oFF_I == null) {				
					//GEOMPOINT3D
					//
					//PtI
					double xI = StringUtil.safeDbl(nf6, oXI.getDxfVal());
					double yI = StringUtil.safeDbl(nf6, oYI.getDxfVal());
					double zI = StringUtil.safeDbl(nf6, oZI.getDxfVal());
					
					GeomPoint3d pt3dI_orig = new GeomPoint3d(xI, yI, zI);
	
					//PtF
					double xF = StringUtil.safeDbl(nf6, oXF.getDxfVal());
					double yF = StringUtil.safeDbl(nf6, oYF.getDxfVal());
					double zF = StringUtil.safeDbl(nf6, oZF.getDxfVal());
					
					GeomPoint3d pt3dF_orig = new GeomPoint3d(xF, yF, zF);
					
					//TO_LEVEL
					//
					CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
					
					GeomPoint3d pt3dI = GeomUtil.toLevelFromPt3d(pt3dI_orig, oLevel); 
					GeomPoint3d pt3dF = GeomUtil.toLevelFromPt3d(pt3dF_orig, oLevel); 
					
					//CADLINE
					//
					LayerTable oTbl = this.getDoc().getLayerTable();
	
					CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
					
					CadLine oLine = CadLine.create(currBlockDef, oLayer, oLevel, pt3dI, pt3dF);
					currBlockDef.addEntity(oLine);
					
					lsResult.add(oLine);
				}
				
				oFF_I = null;
				
				oXI = oXF;
				oYI = oYF;
				oZI = oZF;
				
				n += 1;
			}
		}
		return lsResult;
	}
	
	private ArrayList<CadEntity> insertCircle(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		int n = 0;
		
		ArrayList<DxfCadEntity> lsCadEntity = dxfSection.filterDxfCadEntityByEntityType(AppDefs.DXFETYPE_CIRCLE);
		int sz = lsCadEntity.size();
		for(int i = 0; i < sz; i++) {
			DxfCadEntity o = lsCadEntity.get(i);
			
			DxfEntry oXCenter = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_X);
			DxfEntry oYCenter = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Y);
			DxfEntry oZCenter = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Z);
			
			DxfEntry oRadius = o.getByDxfCode(AppDefs.DXFCODE_RADIUS);
			
			//GEOMPOINT3D
			//
			//PtCenter
			double xCenter = StringUtil.safeDbl(nf6, oXCenter.getDxfVal());
			double yCenter = StringUtil.safeDbl(nf6, oYCenter.getDxfVal());
			double zCenter = StringUtil.safeDbl(nf6, oZCenter.getDxfVal());
			
			GeomPoint3d ptCenter3d_orig = new GeomPoint3d(xCenter, yCenter, zCenter);

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d ptCenter3d = GeomUtil.toLevelFromPt3d(ptCenter3d_orig, oLevel); 
			
			//Radius
			double radius = StringUtil.safeDbl(nf6, oRadius.getDxfVal());
			
			//CADCIRCLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadCircle oCircle = CadCircle.create(currBlockDef, oLayer, oLevel, ptCenter3d, radius);
			currBlockDef.addEntity(oCircle);
			
			lsResult.add(oCircle);
			
			n += 1;
		}
		return lsResult;
	}
	
	private ArrayList<CadEntity> insertArc(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		int n = 0;
		
		ArrayList<DxfCadEntity> lsCadEntity = dxfSection.filterDxfCadEntityByEntityType(AppDefs.DXFETYPE_ARC);
		int sz = lsCadEntity.size();
		for(int i = 0; i < sz; i++) {
			DxfCadEntity o = lsCadEntity.get(i);
			
			DxfEntry oXCenter = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_X);
			DxfEntry oYCenter = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Y);
			DxfEntry oZCenter = o.getByDxfCode(AppDefs.DXFCODE_STARTPOINT_Z);
			
			DxfEntry oRadius = o.getByDxfCode(AppDefs.DXFCODE_RADIUS);
			
			DxfEntry oStartAngle = o.getByDxfCode(AppDefs.DXFCODE_STARTANGLE);
			DxfEntry oEndAngle = o.getByDxfCode(AppDefs.DXFCODE_ENDANGLE);
			
			//GEOMPOINT3D
			//
			//PtCenter
			double xCenter = StringUtil.safeDbl(nf6, oXCenter.getDxfVal());
			double yCenter = StringUtil.safeDbl(nf6, oYCenter.getDxfVal());
			double zCenter = StringUtil.safeDbl(nf6, oZCenter.getDxfVal());
			
			GeomPoint3d ptCenter3d_orig = new GeomPoint3d(xCenter, yCenter, zCenter);
			
			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d ptCenter3d = GeomUtil.toLevelFromPt3d(ptCenter3d_orig, oLevel); 

			//Radius
			double radius = StringUtil.safeDbl(nf6, oRadius.getDxfVal());

			//Start/End Angle
			double startAngle = StringUtil.safeDbl(nf6, oStartAngle.getDxfVal());
			double endAngle = StringUtil.safeDbl(nf6, oEndAngle.getDxfVal());
			
			//CADCIRCLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadArc oArc = CadArc.create(currBlockDef, oLayer, oLevel, ptCenter3d, radius, startAngle, endAngle);
			currBlockDef.addEntity(oArc);
			
			lsResult.add(oArc);
			
			n += 1;
		}
		return lsResult;
	}
	
//Public
	
	public CmdDxfIn() {
		super(AppDefs.ACTION_FILE_DXFIN, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();

		String cdir = ctx.getHomeDir();		

		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_FILE_DXFIN ) );

		String strTitle = this.getR().getString( R.CMD_PRT_SELECT_DXF_FILE_TO_OPEN );
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle(strTitle);
		dlg.setDirectory(cdir);
		dlg.setFile("");
		dlg.setAlwaysOnTop(true);
		dlg.setMode( FileDialog.LOAD );
		dlg.setModal(true);
		dlg.show();
		
		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fileName, this.getClass());

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fullFileName, this.getClass());

		String ext = FileUtil.getFileExtension(fullFileName);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, ext, this.getClass());

		int pos = ListUtil.findPosInList(CmdDxfIn.gExtension, ext);
		if(pos == -1) {
			PromptUtil.prompt( this.getR().getString(R.ERR_INVALID_FILE_TYPE) );
			return null;
		}
		
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optDxfInBlock);
		lsPromptOptions.add(optDxfInLoad);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, this.getR().getString( R.CMD_PRT_SELECT_FILE_LOAD_METHOD ) );
		if(oKeyword == null) {
			oKeyword = this.optDxfInBlock;
		}

		//LAYER_NAME
		//
		String lblLayerName = String.format(
				this.getR().getString( R.CMD_PRT_SELECT_FILE_LOAD_LAYER ),
				CmdDxfIn.gLayerName );
		
		String strLayerName = PromptUtil.getText(this, lblLayerName );
		if( !StringUtil.isEmpty( strLayerName ) ) {
			CmdDxfIn.gLayerName = strLayerName;
		}

		//LAYER_REFERENCE
		//
		String strReference = AppDefs.LAYER_0;

		LayerTable oTbl = this.getDoc().getLayerTable();
		
		CadLayerDef oLayer = oTbl.getLayerDefByName(CmdDxfIn.gLayerName);
		if(oLayer != null) {
			strReference = oLayer.getReference();
		}		

		//INSERT_POINT
		//
		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_INSERT_POINT ) );
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		//ROTATION
		//
		GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(this, ptIns2d, this.getR().getString( R.CMD_PRT_ROTATION ) );
		if(ptDir2d == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);		
				
		result = new InputParamVO();
		result.initKeyPointLayerRotationScaleAndFileName(oKeyword, strReference, ptIns3d, ptDir3d, 1.0, fullFileName);
		return result;
	}

	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		PromptOptionVO oKeyword = oParam.getKeyword();
		GeomPoint3d pt3d_orig = oParam.getPt0(); 
		
		//TO_LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
		
		GeomPoint3d pt3d = GeomUtil.toLevelFromPt3d(pt3d_orig, oLevel); 
		GeomPoint3d ptBase3d = new GeomPoint3d(0.0, 0.0, pt3d.getZ()); 

		String fullFileName = oParam.getFileName();

		String blockName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		String reference = oParam.getReference();
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			String warnmsg = String.format(
				this.getR().getString( R.ERR_EXIST_BLOCK_WITH_THE_SAME_NAME_IN_MEMORY ),
				blockName );
			PromptUtil.prompt(warnmsg);
		}
		else {		
			DxfFile dxfFile = new DxfFile(fullFileName);
			dxfFile.readDxfFile();
			dxfFile.processAllSection();
			
			dxfFile.debug(AppDefs.DEBUG_LEVEL03);
			
			DxfSection dxfSection = dxfFile.getDxfSectionByName(AppDefs.DXFSECTION_ENTITIES);
			dxfSection.processAllCadEntity();

			GeomDimension2d oDim = null;
			if( AppDefs.OPT_DXFIN_BLOCK_STR.equals( oKeyword.getTextOption() )  ) {
				// LOAD_METHOD: BLOCK
				//
				CadBlockDef newBlockDef = CadBlockDef.create(this.getDoc(), AppDefs.OPT_BLOCKDEF_DXFFILE, blockName, fullFileName);		
				
				this.insertLine(reference, newBlockDef, dxfSection);
				this.insertPolyline(reference, newBlockDef, dxfSection);
				this.insertCircle(reference, newBlockDef, dxfSection);
				this.insertArc(reference, newBlockDef, dxfSection);
				
				blkTbl.addBlockDef(blockName, newBlockDef);
				
				String warnmsg = String.format(
					this.getR().getString( R.CMD_PRT_BLOCK_LOAD_SUCCESS ),
					blockName );
				PromptUtil.prompt(warnmsg);
				
				//CADINSERTBLOCK
				//
				LayerTable oTbl = this.getDoc().getLayerTable();
	
				CadLayerDef oLayer = oTbl.getLayerDefByReference(reference);
				
				CadInsertBlock newInsertBlock = CadInsertBlock.create(currBlockDef, oLayer, oLevel, blockName, pt3d, 1.0);
				currBlockDef.addEntity(newInsertBlock);
				
				oDim = newInsertBlock.getEnvelop2d();
			}
			else {
				// LOAD_METHOD: IN_DWG
				//
				ArrayList<CadEntity> lsSelectedEntities = new ArrayList<CadEntity>();

				lsSelectedEntities.addAll( this.insertLine(reference, currBlockDef, dxfSection) );
				lsSelectedEntities.addAll( this.insertPolyline(reference, currBlockDef, dxfSection) );
				lsSelectedEntities.addAll( this.insertCircle(reference, currBlockDef, dxfSection) );
				lsSelectedEntities.addAll( this.insertArc(reference, currBlockDef, dxfSection) );

				for(CadEntity ent : lsSelectedEntities) {
					ent.moveTo(ptBase3d, pt3d);
				}
				
				String warnmsg = String.format(
					this.getR().getString( R.CMD_PRT_BLOCK_ELEMENTS_LOAD_SUCCESS ),
					blockName);
				PromptUtil.prompt(warnmsg);
				
				oDim = currBlockDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);
			}
			
			//ZOOM_TO
			//
			MainPanel panel = (MainPanel)this.getFrm().getPanel();

			ICompView v = panel.getCurrView();

			ICadViewBase cv = v.getCadViewBase();
			cv.zoomWindowMcs(oDim.getPtMin(), oDim.getPtMax());
		}
	}
	
	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args) {
		super.doExecuteCommand(app, frm, cad, doc, args);		
		if(args.length < 1) return;
		
		GeomPoint3d pt3d_orig = new GeomPoint3d(0.0, 0.0, 0.0); 
				
		//TO_LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
		
		GeomPoint3d pt3d = GeomUtil.toLevelFromPt3d(pt3d_orig, oLevel); 

		String fullFileName = args[0];

		String blockName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		String layerName = AppDefs.LAYER_0_BASE;
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			String warnmsg = String.format(
				this.getR().getString( R.ERR_EXIST_BLOCK_WITH_THE_SAME_NAME_IN_MEMORY ), 
				blockName );
			AppError.showCmdError(warnmsg, this.getClass());
		}
		else {		
			DxfFile dxfFile = new DxfFile(fullFileName);
			dxfFile.readDxfFile();
			dxfFile.processAllSection();
			
			dxfFile.debug(AppDefs.DEBUG_LEVEL03);
			
			DxfSection dxfSection = dxfFile.getDxfSectionByName(AppDefs.DXFSECTION_ENTITIES);
			dxfSection.processAllCadEntity();
	
			CadBlockDef newBlockDef = CadBlockDef.create(this.getDoc(), AppDefs.OPT_BLOCKDEF_DXFFILE, blockName, fullFileName);		
			
			this.insertLine(layerName, newBlockDef, dxfSection);
			this.insertPolyline(layerName, newBlockDef, dxfSection);
			this.insertCircle(layerName, newBlockDef, dxfSection);
			this.insertArc(layerName, newBlockDef, dxfSection);
			
			blkTbl.addBlockDef(blockName, newBlockDef);

			String warnmsg = String.format(
				this.getR().getString( R.CMD_PRT_BLOCK_LOAD_SUCCESS ),
				blockName );
			AppError.showCmdError(warnmsg, this.getClass());
			
			//CADINSERTBLOCK
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadInsertBlock newInsertBlock = CadInsertBlock.create(currBlockDef, oLayer, oLevel, blockName, pt3d, 1.0);
			currBlockDef.addEntity(newInsertBlock);
			
			//ZOOM_TO
			//
			GeomDimension2d d = newInsertBlock.getEnvelop2d();

			double w = d.getWidth();
			double h = d.getHeight();

			GeomPoint2d ptMin = new GeomPoint2d(pt3d);
			GeomPoint2d ptMax = new GeomPoint2d(pt3d.getX() + w, pt3d.getY() + h);
			
			MainPanel panel = (MainPanel)this.getFrm().getPanel();

			ICompView v = panel.getCurrView();

			ICadViewBase cv = v.getCadViewBase();
			cv.zoomWindowMcs(ptMin, ptMax);
		}
		
	}
	
}
