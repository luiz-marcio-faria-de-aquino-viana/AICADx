/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdDxfIn.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
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
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfEntry;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfFile;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfSection;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdDxfIn extends CmdBase
{
//Private
	
	private int insertLine(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
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
			
			GeomPoint3d pt3dI = new GeomPoint3d(xI, yI, zI);

			//PtF
			double xF = StringUtil.safeDbl(nf6, oXF.getDxfVal());
			double yF = StringUtil.safeDbl(nf6, oYF.getDxfVal());
			double zF = StringUtil.safeDbl(nf6, oZF.getDxfVal());
			
			GeomPoint3d pt3dF = new GeomPoint3d(xF, yF, zF);
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadLine oLine = CadLine.create(oLayer, pt3dI, pt3dF);
			currBlockDef.addEntity(oLine);
			
			n += 1;
		}
		return n;
	}
	
	private int insertPolyline(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
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
					
					GeomPoint3d pt3dI = new GeomPoint3d(xI, yI, zI);
	
					//PtF
					double xF = StringUtil.safeDbl(nf6, oXF.getDxfVal());
					double yF = StringUtil.safeDbl(nf6, oYF.getDxfVal());
					double zF = StringUtil.safeDbl(nf6, oZF.getDxfVal());
					
					GeomPoint3d pt3dF = new GeomPoint3d(xF, yF, zF);
					
					//CADLINE
					//
					LayerTable oTbl = this.getDoc().getLayerTable();
	
					CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
					
					CadLine oLine = CadLine.create(oLayer, pt3dI, pt3dF);
					currBlockDef.addEntity(oLine);
				}
				
				oFF_I = null;
				
				oXI = oXF;
				oYI = oYF;
				oZI = oZF;
				
				n += 1;
			}
		}
		return n;
	}
	
	private int insertCircle(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
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
			
			GeomPoint3d ptCenter3d = new GeomPoint3d(xCenter, yCenter, zCenter);

			//Radius
			double radius = StringUtil.safeDbl(nf6, oRadius.getDxfVal());
			
			//CADCIRCLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadCircle oCircle = CadCircle.create(oLayer, ptCenter3d, radius);
			currBlockDef.addEntity(oCircle);
			
			n += 1;
		}
		return n;
	}
	
	private int insertArc(String layerName, CadBlockDef currBlockDef, DxfSection dxfSection)
	{
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
			
			GeomPoint3d ptCenter3d = new GeomPoint3d(xCenter, yCenter, zCenter);

			//Radius
			double radius = StringUtil.safeDbl(nf6, oRadius.getDxfVal());

			//Start/End Angle
			double startAngle = StringUtil.safeDbl(nf6, oStartAngle.getDxfVal());
			double endAngle = StringUtil.safeDbl(nf6, oEndAngle.getDxfVal());
			
			//CADCIRCLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadArc oArc = CadArc.create(oLayer, ptCenter3d, radius, startAngle, endAngle);
			currBlockDef.addEntity(oArc);
			
			n += 1;
		}
		return n;
	}
	
//Public
	
	public CmdDxfIn() {
		super(AppDefs.ACTION_FILE_DXFIN);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		PromptUtil.prompt("DXF In...");
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		InputParamVO result = null;
		
		String cdir = ctx.getHomeDir();		
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle("Select DXF File");
		dlg.setDirectory(cdir);
		dlg.setModal(true);
		dlg.show();
		
		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fileName, this.getClass());

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fullFileName, this.getClass());
		
		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(null, "Insert point: ");
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(ptIns2d, "Rotation: ");
		if(ptDir2d == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);		
				
		result = new InputParamVO();
		result.initPointRotationScaleAndFileName(ptIns3d, ptDir3d, 1.0, fullFileName);
		
		return result;
	}

	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		GeomPoint3d pt3d = oParam.getPt0(); 

		String fullFileName = oParam.getFileName();

		String blockName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		String layerName = AppDefs.LAYER_0_BASE;
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		PromptUtil.prompt("DXF In...");
		
		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			String warnmsg = String.format("ERR: Existe um bloco com o mesmo nome carregado (=%s).", blockName);
			PromptUtil.prompt(warnmsg);
		}
		else {		
			DxfFile dxfFile = new DxfFile(fullFileName);
			dxfFile.readDxfFile();
			dxfFile.processAllSection();
			
			dxfFile.debug(AppDefs.DEBUG_LEVEL03);
			
			DxfSection dxfSection = dxfFile.getDxfSectionByName(AppDefs.DXFSECTION_ENTITIES);
			dxfSection.processAllCadEntity();
	
			CadBlockDef newBlockDef = CadBlockDef.create(blockName);		
			
			this.insertLine(layerName, newBlockDef, dxfSection);
			this.insertPolyline(layerName, newBlockDef, dxfSection);
			this.insertCircle(layerName, newBlockDef, dxfSection);
			this.insertArc(layerName, newBlockDef, dxfSection);
			
			blkTbl.addBlockDef(blockName, newBlockDef);

			String warnmsg = String.format("Bloco carregado com sucesso (=%s).", blockName);
			PromptUtil.prompt(warnmsg);
			
			//CADINSERTBLOCK
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadInsertBlock newInsertBlock = CadInsertBlock.create(oLayer, blockName, pt3d, 1.0);
			currBlockDef.addEntity(newInsertBlock);
			
			GeomDimension2d oDim = newInsertBlock.getEnvelop();
			
			//ZOOM_TO
			//
			MainPanel panel = MainPanel.getPanel();

			ICompView v = panel.getCurrView();

			ICadViewBase cv = v.getCadViewBase();
			cv.zoomWindowMcs(oDim.getPtMin(), oDim.getPtMax());
		}
	}
	
	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args) {
		super.doExecuteCommand(app, frm, cad, doc, args);		
		if(args.length < 1) return;
		
		GeomPoint3d pt3d = new GeomPoint3d(0.0, 0.0, 0.0); 

		String fullFileName = args[0];

		String blockName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		String layerName = AppDefs.LAYER_0_BASE;
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		BlockTable blkTbl = this.getDoc().getBlockTable();
		if( blkTbl.hasBlockDef(blockName) ) {
			String warnmsg = String.format("ERR: Existe um bloco com o mesmo nome carregado (=%s).", blockName);
			AppError.showCmdError(warnmsg, this.getClass());
		}
		else {		
			DxfFile dxfFile = new DxfFile(fullFileName);
			dxfFile.readDxfFile();
			dxfFile.processAllSection();
			
			dxfFile.debug(AppDefs.DEBUG_LEVEL03);
			
			DxfSection dxfSection = dxfFile.getDxfSectionByName(AppDefs.DXFSECTION_ENTITIES);
			dxfSection.processAllCadEntity();
	
			CadBlockDef newBlockDef = CadBlockDef.create(blockName);		
			
			this.insertLine(layerName, newBlockDef, dxfSection);
			this.insertPolyline(layerName, newBlockDef, dxfSection);
			this.insertCircle(layerName, newBlockDef, dxfSection);
			this.insertArc(layerName, newBlockDef, dxfSection);
			
			blkTbl.addBlockDef(blockName, newBlockDef);

			String warnmsg = String.format("Bloco carregado com sucesso (=%s).", blockName);
			AppError.showCmdError(warnmsg, this.getClass());
			
			//CADINSERTBLOCK
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
			
			CadInsertBlock newInsertBlock = CadInsertBlock.create(oLayer, blockName, pt3d, 1.0);
			currBlockDef.addEntity(newInsertBlock);
			
			//ZOOM_TO
			//
			GeomDimension2d d = newInsertBlock.getEnvelop();

			double w = d.getWidth();
			double h = d.getHeight();

			GeomPoint2d ptMin = new GeomPoint2d(pt3d);
			GeomPoint2d ptMax = new GeomPoint2d(pt3d.getX() + w, pt3d.getY() + h);
			
			MainPanel panel = MainPanel.getPanel();

			ICompView v = panel.getCurrView();

			ICadViewBase cv = v.getCadViewBase();
			cv.zoomWindowMcs(ptMin, ptMax);
		}
		
	}
	
}
