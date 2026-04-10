/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInsereMultTopoPointTopografia.java
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

package br.com.tlmv.aicadxmod.topografia.cad.cmd;

import java.awt.FileDialog;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadInsertBlock;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.topografia.cad.CadTopoPointTopografia;

public class CmdInsereMultTopoPointTopografia extends CmdBase
{
//Private
	
	//1000,10000.000,5000.000,100.000,EST			
	private void processData(CadBlockDef currBlockDef, ArrayList<String> lsStr)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE3_MASC);
		
		Date dataHoraAtual = new Date();
		
		LayerTable oTbl = this.getDoc().getLayerTable();

		String strReferencePadrao = AppDefs.LAYER_TT_PADRAO_N;
		CadLayerDef oLayerPadrao = oTbl.getLayerDefByReference(strReferencePadrao);

		for(String str : lsStr) {
			String[] arr = StringUtil.split(str, ',');
			int n = 0;
			
			if(arr.length >= 5) {
				String strPontoId = arr[n++];
				String strXp = arr[n++];
				String strYp = arr[n++];
				String strZp = arr[n++];
				String strAlturaAntena = strZp;
				if(arr.length >= 6)
					strAlturaAntena = arr[n++];
				String strDescricaoCategoria = arr[n++];
				
			    int pontoId = StringUtil.safeInt(strPontoId);
			    String nome = strPontoId;
			    double alturaAntena = StringUtil.safeDbl(nf6, strAlturaAntena);
			    String dataAtualizacao = df.format(dataHoraAtual);
				
			    CadLayerDef oLayer = oTbl.getLayerDefByDescricaoCategoria(strDescricaoCategoria);
			    if(oLayer == null ) {
			    	oLayer = oLayerPadrao;
				}
			    int categoriaId = oLayer.getCategoriaId();

			 	double xp = StringUtil.safeDbl(nf6, strXp);
			 	double yp = StringUtil.safeDbl(nf6, strYp);
			 	double zp = StringUtil.safeDbl(nf6, strZp);
			    
			    GeomPoint2d ptIns = new GeomPoint2d(xp, yp);
			    
				CadTopoPointTopografia oTopoPoint = CadTopoPointTopografia.create(
					currBlockDef, 
					oLayer, 
					null,
				    pontoId,
				    categoriaId,
				    strDescricaoCategoria,
				    nome,
				    alturaAntena,
				    dataAtualizacao,
					ptIns);
				currBlockDef.addEntity(oTopoPoint);
			}
		}
	}
		
//Public

	public CmdInsereMultTopoPointTopografia() {
		super(AppDefs.ACTION_TOPO1_INSERE_MULT_TOPOPOINT, true, true);
	}

	/* Methodes */

	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		PromptUtil.prompt("TOPOGRAFIA: Adicionando multiplos pontos de topografia...");

		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();

		InputParamVO result = null;
		
		String cdir = ctx.getHomeDir();		
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle("Select Point File");
		dlg.setDirectory(cdir);
		dlg.setModal(true);
		dlg.show();
		
		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fileName, this.getClass());

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fullFileName, this.getClass());

		String strReference = AppDefs.LAYER_TT_PADRAO_N;
		
		result = new InputParamVO();
		result.initFileNameAndLayer(strReference, fullFileName);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			String strReference = oParam.getReference();
			String strFullFileName = oParam.getFileName();
			
			ArrayList<String> lsStr = FileUtil.readDataAsList(strFullFileName);

			String blockName = FileUtil.getFileNameWithoutExtension(strFullFileName);
			
			BlockTable blkTbl = this.getDoc().getBlockTable();
			if( blkTbl.hasBlockDef(blockName) ) {
				String warnmsg = String.format("ERR: Existe um bloco com o mesmo nome carregado (=%s).", blockName);
				PromptUtil.prompt(warnmsg);
			}
			else {		
				CadBlockDef newBlockDef = CadBlockDef.create(this.getDoc(), AppDefs.OPT_BLOCKDEF_POINTFILE, blockName);		
				this.processData(newBlockDef, lsStr);
				
				blkTbl.addBlockDef(blockName, newBlockDef);
							
				String warnmsg = String.format("Pontos carregados com sucesso (=%s).", blockName);
				PromptUtil.prompt(warnmsg);
				
				//CADINSERTBLOCK
				//
				LayerTable oTbl = this.getDoc().getLayerTable();
	
				String layerName = AppDefs.LAYER_0_BASE;
				CadLayerDef oLayer = oTbl.getLayerDefByReference(layerName);
				
				CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
	
				CadInsertBlock newInsertBlock = CadInsertBlock.create(currBlockDef, oLayer, null, blockName, new GeomPoint3d(0.0, 0.0, 0.0), 1.0);
				currBlockDef.addEntity(newInsertBlock);
				
				GeomDimension2d oDim = newInsertBlock.getEnvelop2d();
				
				//ZOOM_TO
				//
				MainPanel panel = (MainPanel)this.getFrm().getPanel();
	
				ICompView v = panel.getCurrView();
	
				ICadViewBase cv = v.getCadViewBase();
				cv.zoomWindowMcs(oDim.getPtMin(), oDim.getPtMax());			
			}
		}
	}

}
