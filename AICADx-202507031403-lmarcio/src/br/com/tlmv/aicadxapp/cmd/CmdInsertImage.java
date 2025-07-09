/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdInsertImage.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.awt.FileDialog;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadInsertImage;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.ImageTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdInsertImage extends CmdBase
{
//Public

	public CmdInsertImage() {
		super(AppDefs.ACTION_FILE_INSERTIMAGE);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		PromptUtil.prompt("Insert image...");
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
				
		InputParamVO result = null;
		
		String cdir = ctx.getHomeDir();		
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle("Select Image");
		dlg.setDirectory(cdir);
		dlg.setModal(true);
		dlg.show();
		
		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fileName, this.getClass());

		String fullFileName = dirName + fileName;
		
		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(null, "Insert point: ");
		while(ptIns2d != null) {
			GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);
	
			GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(ptIns2d, "Rotation: ");
			if(ptDir2d == null) return null;
			
			GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);		
					
			result = new InputParamVO();
			result.initPointRotationScaleAndFileName(ptIns3d, ptDir3d, 1.0, fullFileName);
			
			ptIns2d = PromptUtil.getFirstPoint2d(null, "Insert point: ");
		}
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		//String fullFileName = AppDefs.DEF_IMGIN_FILENAME;
		String fullFileName = oParam.getFileName();
		
		String imageName = FileUtil.getFileNameWithoutExtension(fullFileName);		
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
		
		ImageTable tbl = this.getDoc().getImageTable();

		if( tbl.hasImageDef(imageName) ) {
			String warnmsg = String.format("ERR: Existe uma imagem com o mesmo nome carregado (=%s).", imageName);
			PromptUtil.prompt(warnmsg);
		}
		else {
			CadImageDef newImageDef = CadImageDef.create(imageName, fullFileName);		
			tbl.addImageDef(imageName, newImageDef);

			String warnmsg = String.format("Imagem carregada com sucesso (=%s).", imageName);
			PromptUtil.prompt(warnmsg);
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptIns3d = oParam.getPt0(); 
			GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);
			
			GeomPoint3d ptDir3d = oParam.getPt1(); 
			GeomPoint2d ptDir2d = new GeomPoint2d(ptDir3d);
			
			GeomVector2d vDir = new GeomVector2d(ptIns2d, ptDir2d);
			double rotateRad = vDir.angleToAxisX();

			double rotate = GeomUtil.convertRadToDegrees(rotateRad);

			double scale = oParam.getScale();
			
			//CADINSERTBLOCK
			//
			LayerTable oTbl = this.getDoc().getLayerTable();

			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);
			
			CadInsertImage newInsertImage = CadInsertImage.create(oLayer, imageName, ptIns3d, rotate, scale);
			currBlockDef.addEntity(newInsertImage);
		}
	}

}
