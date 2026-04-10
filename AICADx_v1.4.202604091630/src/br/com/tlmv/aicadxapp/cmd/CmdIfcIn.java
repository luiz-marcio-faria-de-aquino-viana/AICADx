/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdIfcIn.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2026
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
import br.com.tlmv.aicadxapp.cad.CadFace3d;
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPolyline;
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
import br.com.tlmv.aicadxapp.ifc.IIfcFile;
import br.com.tlmv.aicadxapp.ifc.IfcDefs;
import br.com.tlmv.aicadxapp.ifc.IfcFile;
import br.com.tlmv.aicadxapp.ifc.IfcFileEx;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcBaseObject;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcCartesianPoint;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcFace;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcFaceOuterBound;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcPolyLoop;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcPolyline;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdIfcIn extends CmdBase
{
//Private Static
	private static long gMaxNumElem = 64 * 1024;
	
	private static String gLayerName = "0-BASE";

	private static String[] gExtension = { 
		"ifc"
	};
	
//Private

	/* PromptOption
	*/
	private PromptOptionVO optIfcInBlock = new PromptOptionVO(AppDefs.OPT_DXFIN_BLOCK_VAL, AppDefs.OPT_DXFIN_BLOCK_STR, "B", true);
	private PromptOptionVO optIfcInLoad = new PromptOptionVO(AppDefs.OPT_DXFIN_LOAD_VAL, AppDefs.OPT_DXFIN_LOAD_STR, "L", false);
	
//Public
	
	public CmdIfcIn() {
		super(AppDefs.ACTION_FILE_IFCIN, false, true);
	}

	/* Methodes */
	
	private long loadIfcPolyline(
		IIfcFile ifcFile,
		CadBlockDef blockDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel )
	{
		long nrows = 0;
		
		long startPos = 0;
		long endPos = startPos + CmdIfcIn.gMaxNumElem;
		
		ArrayList<IfcBaseObject> lsSelected = ifcFile.selectByIfcClass(IfcDefs.tagIfcPolyline, startPos, endPos);
		while(lsSelected != null) {
			for(IfcBaseObject o : lsSelected) {
				IfcPolyline ifcPL = (IfcPolyline)o; 
				
				ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>();
	
				int szIfcCP = ifcPL.getSzLsIfcCartesianPoint();
				for(int k = 0; k < szIfcCP; k++) {
					IfcCartesianPoint ifcCP = ifcPL.getIfcCartesianPointAt(k);
	
					double xp = ifcCP.getX();
					double yp = ifcCP.getY();
					double zp = ifcCP.getZ();
					
					lsPts3d.add( new GeomPoint3d(xp, yp, zp) );
				}
	
				CadPolyline oPolyline = CadPolyline.create(blockDef, oLayer, oLevel, lsPts3d); 
				blockDef.addEntity(oPolyline);
				
				nrows++;
			}

			startPos = endPos;
			endPos = startPos + CmdIfcIn.gMaxNumElem;
			
			lsSelected = ifcFile.selectByIfcClass(IfcDefs.tagIfcPolyline, startPos, endPos);
		}
		return nrows;
	}
	
	private long loadIfcPolyLoop(
		IIfcFile ifcFile,
		CadBlockDef blockDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel)
	{
		long nrows = 0;
		
		long startPos = 0;
		long endPos = startPos + CmdIfcIn.gMaxNumElem;
		
		ArrayList<IfcBaseObject> lsSelected = ifcFile.selectByIfcClass(IfcDefs.tagIfcPolyLoop, startPos, endPos);
		while(lsSelected != null) {
			for(IfcBaseObject o : lsSelected) {
				IfcPolyLoop ifcPL = (IfcPolyLoop)o;
	
				ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>();
	
				int szIfcCP = ifcPL.getSzLsIfcCartesianPoint();
				for(int k = 0; k < szIfcCP; k++) {
					IfcCartesianPoint ifcCP = ifcPL.getIfcCartesianPointAt(k);
	
					double xp = ifcCP.getX();
					double yp = ifcCP.getY();
					double zp = ifcCP.getZ();
					
					lsPts3d.add( new GeomPoint3d(xp, yp, zp) );
				}
				
				CadFace3d oFace = CadFace3d.create(blockDef, oLayer, oLevel, lsPts3d); 
				blockDef.addEntity(oFace);
				
				nrows++;
			}

			startPos = endPos;
			endPos = startPos + CmdIfcIn.gMaxNumElem;
			
			lsSelected = ifcFile.selectByIfcClass(IfcDefs.tagIfcPolyLoop, startPos, endPos);
		}
		return nrows;
	}
		
	private long loadIfcFace(
		IIfcFile ifcFile,
		CadBlockDef blockDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel )
	{
		long nrows = 0;
		
		long startPos = 0;
		long endPos = startPos + CmdIfcIn.gMaxNumElem;
		
		ArrayList<IfcBaseObject> lsSelected = ifcFile.selectByIfcClass(IfcDefs.tagIfcFace, startPos, endPos);
		while(lsSelected != null) {
			for(IfcBaseObject o : lsSelected) {
				IfcFace ifcFace = (IfcFace)o; 
				
				ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>();
	
				int szIfcFOB = ifcFace.getSzLsIfcFaceOuterBound();
				for(int i = 0; i < szIfcFOB; i++) {
					IfcFaceOuterBound ifcFOB = ifcFace.getIfcFaceOuterBoundAt(i);
					
					int szIfcPL = ifcFOB.getSzLsIfcPolyLoop();
					for(int j = 0; j < szIfcPL; j++) {
						IfcPolyLoop ifcPL = ifcFOB.getIfcPolyLoopAt(j);
	
						int szIfcCP = ifcPL.getSzLsIfcCartesianPoint();
						for(int k = 0; k < szIfcCP; k++) {
							IfcCartesianPoint ifcCP = ifcPL.getIfcCartesianPointAt(k);
	
							double xp = ifcCP.getX();
							double yp = ifcCP.getY();
							double zp = ifcCP.getZ();
							
							lsPts3d.add( new GeomPoint3d(xp, yp, zp) );
						}
					}
				}
	
				CadFace3d oFace = CadFace3d.create(blockDef, oLayer, oLevel, lsPts3d); 
				blockDef.addEntity(oFace);
				
				nrows++;
			}

			startPos = endPos;
			endPos = startPos + CmdIfcIn.gMaxNumElem;
			
			lsSelected = ifcFile.selectByIfcClass(IfcDefs.tagIfcFace, startPos, endPos);
		}
		return nrows;
	}
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();

		String cdir = ctx.getHomeDir();

		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_FILE_IFCIN ) );
		
		String strTitle = this.getR().getString( R.CMD_PRT_SELECT_IFC_FILE_TO_OPEN );
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle(strTitle);
		dlg.setDirectory(cdir);
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

		int pos = ListUtil.findPosInList(CmdIfcIn.gExtension, ext);
		if(pos == -1) {
			PromptUtil.prompt( this.getR().getString(R.ERR_INVALID_FILE_TYPE) );
			return null;
		}
		
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optIfcInBlock);
		lsPromptOptions.add(optIfcInLoad);
		
		//PromptOptionVO oKeyword = PromptUtil.getKeyword(lsPromptOptions, this.getR().getString( R.CMD_PRT_SELECT_FILE_LOAD_METHOD ) );
		//if(oKeyword == null) {
		//	oKeyword = this.optIfcInBlock;
		//}
		PromptOptionVO oKeyword = this.optIfcInLoad;

		//LAYER_NAME
		//
		String lblLayerName = String.format(
				this.getR().getString( R.CMD_PRT_SELECT_FILE_LOAD_LAYER ),
				CmdIfcIn.gLayerName );
		
		String strLayerName = PromptUtil.getText(this, lblLayerName );
		if( !StringUtil.isEmpty( strLayerName ) ) {
			CmdIfcIn.gLayerName = strLayerName;
		}

		//LAYER_REFERENCE
		//
		String strReference = AppDefs.LAYER_0;

		LayerTable oTbl = this.getDoc().getLayerTable();
		
		CadLayerDef oLayer = oTbl.getLayerDefByName(CmdIfcIn.gLayerName);
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
		
		String fullFileName = oParam.getFileName();

		String blockName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		String reference = oParam.getReference();
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		//CADENTITY
		//
		PromptOptionVO oKeyword = oParam.getKeyword();
		GeomPoint3d pt3d_orig = oParam.getPt0(); 
		
		//LAYER
		//
		LayerTable oTbl = this.getDoc().getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
		ColorVO oColor = oLayer.getColor();
		
		//LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
		
		GeomPoint3d pt3d = GeomUtil.toLevelFromPt3d(pt3d_orig, oLevel); 
		GeomPoint3d ptBase3d = new GeomPoint3d(0.0, 0.0, pt3d.getZ()); 

		//IFCFILE
		//
		IfcFileEx ifcFile = new IfcFileEx(fullFileName);
		ifcFile.execute();

		//BLOCK
		//
		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			String warnmsg = String.format(
				this.getR().getString( R.ERR_EXIST_BLOCK_WITH_THE_SAME_NAME_IN_MEMORY ),
				blockName );
			PromptUtil.prompt(warnmsg);
		}
		else {	
			
			GeomDimension2d oDim = null;
			
			if( AppDefs.OPT_DXFIN_BLOCK_STR.equals( oKeyword.getTextOption() )  ) {
				// LOAD_METHOD: BLOCK
				//
				CadBlockDef newBlockDef = CadBlockDef.create(this.getDoc(), AppDefs.OPT_BLOCKDEF_DXFFILE, blockName, fullFileName);		
				
				this.loadIfcFace(ifcFile, newBlockDef, oLayer, oLevel);
				this.loadIfcPolyLoop(ifcFile, newBlockDef, oLayer, oLevel);
				this.loadIfcPolyline(ifcFile, newBlockDef, oLayer, oLevel);

				blkTbl.addBlockDef(blockName, newBlockDef);
				
				String warnmsg = String.format(
					this.getR().getString( R.CMD_PRT_BLOCK_LOAD_SUCCESS ),
					blockName );
				PromptUtil.prompt(warnmsg);
				
				//CADINSERTBLOCK
				//
				CadInsertBlock newInsertBlock = CadInsertBlock.create(currBlockDef, oLayer, oLevel, blockName, pt3d, 1.0);
				currBlockDef.addEntity(newInsertBlock);
				
				oDim = newInsertBlock.getEnvelop2d();
			}
			else {
				// LOAD_METHOD: IN_DWG
				//
				this.loadIfcFace(ifcFile, currBlockDef, oLayer, oLevel);
				this.loadIfcPolyLoop(ifcFile, currBlockDef, oLayer, oLevel);
				this.loadIfcPolyline(ifcFile, currBlockDef, oLayer, oLevel);
				
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
		
		String fullFileName = args[0];

		String blockName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		String layerName = AppDefs.LAYER_0_BASE;
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		GeomPoint3d pt3d_orig = new GeomPoint3d(0.0, 0.0, 0.0); 
				
		//LAYER
		//
		LayerTable oTbl = this.getDoc().getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
		ColorVO oColor = oLayer.getColor();
		
		//LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
		
		GeomPoint3d pt3d = GeomUtil.toLevelFromPt3d(pt3d_orig, oLevel); 

		//BLOCK
		//
		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			String warnmsg = String.format(
				this.getR().getString( R.ERR_EXIST_BLOCK_WITH_THE_SAME_NAME_IN_MEMORY ), 
				blockName );
			AppError.showCmdError(warnmsg, this.getClass());
		}
		else {		
			IfcFile ifcFile = new IfcFile(fullFileName);
			ifcFile.execute();
			
			ArrayList<GeomPoint3d> lsPts3d_orig = null;
			
			ArrayList<IfcBaseObject> ls = ifcFile.selectByIfcClass(IfcDefs.tagIfcFace, 0, 1000);
			for(IfcBaseObject o : ls) {
				IfcFace ifcFace = (IfcFace)o;

				lsPts3d_orig = new ArrayList<GeomPoint3d>();
				
				int szIfcFOB = ifcFace.getSzLsIfcFaceOuterBound();
				for(int i = 0; i < szIfcFOB; i++) {
					IfcFaceOuterBound ifcFOB = ifcFace.getIfcFaceOuterBoundAt(i);
					
					int szIfcPL = ifcFOB.getSzLsIfcPolyLoop();
					for(int j = 0; j < szIfcPL; j++) {
						IfcPolyLoop ifcPL = ifcFOB.getIfcPolyLoopAt(i);

						int szIfcCP = ifcPL.getSzLsIfcCartesianPoint();
						for(int k = 0; k < szIfcCP; k++) {
							IfcCartesianPoint ifcCP = ifcPL.getIfcCartesianPointAt(i);

							double xp = ifcCP.getX();
							double yp = ifcCP.getY();
							double zp = ifcCP.getZ();
							
							lsPts3d_orig.add( new GeomPoint3d(xp, yp, zp) );
						}
					}
				}
				
				ArrayList<GeomPoint3d> lsPts3d = GeomUtil.toLevelFromLsPts3d(lsPts3d_orig, oLevel); 

				CadFace3d oFace = CadFace3d.create(currBlockDef, oLayer, oLevel, lsPts3d); 
				currBlockDef.addEntity(oFace);
			}
			
			//ZOOM_TO
			//
			MainPanel panel = (MainPanel)this.getFrm().getPanel();

			ICompView v = panel.getCurrView();

			ICadViewBase cv = v.getCadViewBase();
			cv.zoomExtMcs();
		}
		
	}
	
}
