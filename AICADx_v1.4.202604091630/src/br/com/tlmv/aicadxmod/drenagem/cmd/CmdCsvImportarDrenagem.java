/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdCsvImportarDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/07/2025
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

import java.awt.FileDialog;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.filter.BaseFilenameFilter;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.vo.CsvImportaCaixaInspecaoVO;
import br.com.tlmv.aicadxmod.drenagem.vo.CsvImportaRedeVO;

public class CmdCsvImportarDrenagem extends CmdBase
{
//Private
	private Hashtable mapRede = new Hashtable();
	
	private int loadCaixaInspecao(String dirName, boolean bOnlyDataEntry)
	{
		PromptUtil.prompt("Importando Caixas de Inspecao...");
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		LayerTable oTbl = this.getDoc().getLayerTable();

		String reference = AppDefs.LAYER_RPD_PONTOS;
		CadLayerDef oLayer = oTbl.getLayerDefByReference(reference);		
		if(oLayer == null)
			oLayer = this.getDoc().getCurrLayerDef();
			
		String fullFileName = dirName + AppDefs.DEF_CSVFILENAME_CAIXAINSPECAODRENAGEM;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL17, fullFileName, this.getClass());
		
		ArrayList<String> lsStr = FileUtil.readDataAsList(fullFileName);
		int pos = 0;
		for(String str : lsStr) {
			if(pos++ == 0) continue;				// Header Line
			if( "".equals(str) ) continue;			// Empty Line
			if( str.startsWith(";;") ) continue;	// Comment Line / End Of Data

			CsvImportaCaixaInspecaoVO oCsvCI = CsvImportaCaixaInspecaoVO.fromCsv(str);
			if(oCsvCI != null) {
				if( !oCsvCI.isValid() ) continue;
				oCsvCI.debug(AppDefs.DEBUG_LEVEL18);
				
				GeomPoint3d ptCenter_orig = new GeomPoint3d(oCsvCI.getCoordX1(), oCsvCI.getCoordY1(), oCsvCI.getCotaTopo());

				//TO_LEVEL
				//
				CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
				
				GeomPoint3d ptCenter = GeomUtil.toLevelFromPt3d(ptCenter_orig, oLevel); 

				CadCaixaInspecaoDrenagem oCI = CadCaixaInspecaoDrenagem.create(currBlockDef, oLayer, oLevel, ptCenter);
				oCI.setPv(oCsvCI.getPv());
				oCI.setCt(oCsvCI.getCotaTopo());
				oCI.setCb(oCsvCI.getCotaFundo());
				oCI.setProfundidade(- Math.abs( oCsvCI.getProfundidade() ) );
				oCI.setAreaExterna(0.0);
				oCI.setAreaLocal(oCsvCI.getAreaLocal());
				oCI.setAreaTotal(oCsvCI.getAreaLocal());
				oCI.setAreaTotalImp(oCsvCI.getAreaTotal());
				oCI.setVazao(oCsvCI.getVazaoPv());
				oCI.setVazaoAcumulada(oCsvCI.getVazaoTotal());
				if( bOnlyDataEntry ) {
					double dProfundidade = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
					double dCb = oCI.getCt() - dProfundidade;
					double dAreaTotal = 0.0;
					double dVazao = 0.0;
					double dVazaoTotal = 0.0;
					
					oCI.setCb(dCb);
					oCI.setProfundidade(dProfundidade);
					oCI.setAreaTotal(dAreaTotal);
					oCI.setVazao(dVazao);
					oCI.setVazaoAcumulada(dVazaoTotal);					
				}

				oCI.debug(AppDefs.DEBUG_LEVEL18);
				
				currBlockDef.addEntity(oCI);
				this.mapRede.put(oCsvCI.getPv(), oCI);
			}
		}
		return AppDefs.RSOK;
	}

	private int loadRede(String dirName, boolean bOnlyDataEntry)
	{
		PromptUtil.prompt("Importando Rede...");
		
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		LayerTable oTbl = this.getDoc().getLayerTable();

		String reference = AppDefs.LAYER_RPD_TB_DRENAGEM;
		CadLayerDef oLayer = oTbl.getLayerDefByReference(reference);		
		if(oLayer == null)
			oLayer = this.getDoc().getCurrLayerDef();
			
		String fullFileName = dirName + AppDefs.DEF_CSVFILENAME_REDEDRENAGEM;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL17, fullFileName, this.getClass());
		
		ArrayList<String> lsStr = FileUtil.readDataAsList(fullFileName);
		int pos = 0;
		for(String str : lsStr) {
			if(pos++ == 0) continue;				// Header Line
			if( "".equals(str) ) continue;			// Empty Line
			if( str.startsWith(";;") ) continue;	// Comment Line / End Of Data

			CsvImportaRedeVO oCsvRede = CsvImportaRedeVO.fromCsv(str);
			if(oCsvRede != null) {
				if( !oCsvRede.isValid() ) continue;
				oCsvRede.debug(AppDefs.DEBUG_LEVEL18);
				
				String pvMont = oCsvRede.getPvMont();
				String pvJus = oCsvRede.getPvJus();

				if( this.mapRede.containsKey(pvMont) ) {
					CadCaixaInspecaoDrenagem oCIAtual = (CadCaixaInspecaoDrenagem)this.mapRede.get(pvMont);
					if(oCIAtual != null) {
						CadCaixaInspecaoDrenagem oCIProximo = (CadCaixaInspecaoDrenagem)this.mapRede.get(pvJus);
						if(oCIProximo != null) {
							oCIAtual.setProximaCI(oCIProximo.getNumeroCI());
							oCIAtual.setProximo(oCIProximo);
							
							oCIProximo.addAnterior(oCIAtual);
						}
					}
				}
				
				if( bOnlyDataEntry ) {
					//TODO:
				}
			}
		}
		return AppDefs.RSOK;
	}
	
	private GeomDimension2d getEnvelop()
	{
		GeomDimension2d oDim = null; 
		
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		
		Collection<CadCaixaInspecaoDrenagem> colCI = this.mapRede.values();
		for(CadCaixaInspecaoDrenagem oCI : colCI) {
			GeomPoint3d ptIns = oCI.getPtIns();
			double ptInsX = ptIns.getX();
			double ptInsY = ptIns.getY();

			if(Math.abs(ptInsX) < AppDefs.MATHPREC_MIN) continue;
			if(Math.abs(ptInsY) < AppDefs.MATHPREC_MIN) continue;
			
			if(ptInsX < minX)
				minX = ptInsX;
			if(ptInsX > maxX)
				maxX = ptInsX;

			if(ptInsY < minY)
				minY = ptInsY;
			if(ptInsY > maxY)
				maxY = ptInsY;
		}
		
		if( (minX == Double.MAX_VALUE) && (minY == Double.MAX_VALUE) )
			return null;
		if( (maxX == Double.MAX_VALUE) && (maxY == Double.MAX_VALUE) )
			return null;
		
		GeomPoint2d ptMin = new GeomPoint2d(minX, minY); 
		GeomPoint2d ptMax = new GeomPoint2d(maxX, maxY); 
		
		oDim = new GeomDimension2d(ptMin, ptMax);
		return oDim;
	}
	
//Public

	public CmdCsvImportarDrenagem() {
		super(AppDefs.ACTION_RDP1_CSV_IMPORT, false, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("DRENAGEM: Importar Rede de Drenagem (CSV)...");
		
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		String cdir = ctx.getHomeDir();		
		
		FilenameFilter ff = new BaseFilenameFilter(AppDefs.EXT_CSV); 
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle("Select CSV File");
		dlg.setDirectory(cdir);
		dlg.setAutoRequestFocus(true);
		dlg.setFilenameFilter(ff);
		dlg.setAlwaysOnTop(true);
		dlg.setMode(FileDialog.LOAD);
		dlg.setMultipleMode(false);
		dlg.setModal(true);
		dlg.show();

		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL17, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL17, fileName, this.getClass());

		if( StringUtil.isEmpty(fileName) || StringUtil.isEmpty(dirName) ) 
			return null;

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL17, fullFileName, this.getClass());

		result = new InputParamVO();
		result.initDirName(dirName);

		return result;
	}

	@Override
	public void doCommand() 
	{
		this.getFrm().showToolbarControl(AppDefs.TOOLBARCTRL_BASIC, true);
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		String dirName = oParam.getDirName();

		this.mapRede = new Hashtable();
				
		int rscode = this.loadCaixaInspecao(dirName, true);
		if(rscode == AppDefs.RSOK) {
			rscode = this.loadRede(dirName, true);
		}

		String warnmsg = String.format("Rede de Drenagem carregada com sucesso (=%s).", dirName);
		PromptUtil.prompt(warnmsg);
				
		//ZOOM_TO
		//
		GeomDimension2d oDim = this.getEnvelop();
		if(oDim == null) return;
		
        MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();

		ICadViewBase cv = v.getCadViewBase();
		cv.zoomWindowMcs(oDim.getPtMin(), oDim.getPtMax());
	}

	@Override
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args) {
		super.doExecuteCommand(app, frm, cad, doc, args);

		String dirName = AppDefs.DEF_CSVIN_DIRNAME;

		this.mapRede = new Hashtable();
				
		int rscode = this.loadCaixaInspecao(dirName, true);
		if(rscode == AppDefs.RSOK) {
			rscode = this.loadRede(dirName, true);
		}

		String warnmsg = String.format("Rede de Drenagem carregada com sucesso (=%s).", dirName);
		PromptUtil.prompt(warnmsg);
	}

}
