/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdProcessaFiacao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/09/2025
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

package br.com.tlmv.aicadxmod.eletrica.cmd;

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadImportaFiacaoEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.fiacao.FiacaoHelper;
import br.com.tlmv.aicadxmod.eletrica.fiacao.util.FiacaoUtil;
import br.com.tlmv.aicadxmod.eletrica.vo.ExportaFiacaoVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletroduto3DEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdProcessaFiacao extends CmdBase
{
//Public
	
	public CmdProcessaFiacao() {
		super(AppDefs.ACTION_EL2_PROCESSA_FIACAO, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;

		PromptUtil.prompt("ELETRICA: Processa Fiacao...");
		
		result = new InputParamVO();
		return result;
	}
	
	@Override
	public void doCommand() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

			int[] arrObjType = {
				AppDefs.OBJTYPE_MODELELETRODUTO,
				AppDefs.OBJTYPE_MODELELETRODUTO3D
			};
			
			CadEntity[] arrEletroduto = currBlockDef.findAllEntityByObjType(arrObjType);

			int szLsEletroduto = arrEletroduto.length;
			if(szLsEletroduto > 0) {
                ArrayList<ExportaFiacaoVO> lsExportaFiacao = new ArrayList<ExportaFiacaoVO>();

                for(CadEntity ent : arrEletroduto)
                {
                	int objectId = ent.getObjectId();
                	int objType = ent.getObjType();

                	String strHnd = Integer.toString(objectId);

                    CadPontoEletrica oElem1 = null;
                    CadPontoEletrica oElem2 = null;
                	if(objType == AppDefs.OBJTYPE_MODELELETRODUTO) {
                    	CadEletrodutoEletrica o = (CadEletrodutoEletrica)ent; 

                    	oElem1 = (CadPontoEletrica)o.getEntI();
                        oElem2 = (CadPontoEletrica)o.getEntF();
                	}
                	else if(objType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
                    	CadEletroduto3DEletrica o = (CadEletroduto3DEletrica)ent; 

                        oElem1 = (CadPontoEletrica)o.getEntI();
                        oElem2 = (CadPontoEletrica)o.getEntF();
                	}
                    
                	if( oElem1.isDeleted() || oElem2.isDeleted() ) continue;
                	
                    //PARAM_ELEM_1
                    //
                    ArrayList<CadParamEletricoOData> oLsParam1 = oElem1.getLsParamEletrico();
                    int szLsParam1 = oLsParam1.size();
                    for(int i = 0; i < szLsParam1; i++) {
                    	CadParamEletricoOData oParam1 = oLsParam1.get(i);
                    	
                        String strHnd1 = Integer.toString( oElem1.getObjectId() );
                        String strIdx1 = Integer.toString( i );
                        String strTip1 = oParam1.getTipo();
                        String strQdr1 = oParam1.getNomeQuadro();
                        String strOrg1 = oParam1.getQuadroOrigem();
                        String strDes1 = oParam1.getDesvio();
                        String strCir1 = oParam1.getCircuito();
                        String strCmd1 = oParam1.getComando();
                        String strFas1 = oParam1.getSistema();
                        //String strCal1 = oParam1.getNomeCalha();

                        //double dPot1 = oParam1.getPotencia();
                        //double dDem1 = oParam1.getPotenciaDemandada();
                        
                        //String strPot1 = nf6.format( dPot1 );
                        //String strDem1 = nf6.format( dDem1 );

                        //PARAM_ELEM_2
                        //
                        ArrayList<CadParamEletricoOData> oLsParam2 = oElem2.getLsParamEletrico();
                        int szLsParam2 = oLsParam2.size();
                        for(int j = 0; j < szLsParam2; j++) {
                        	CadParamEletricoOData oParam2 = oLsParam2.get(j);
                        	
                            String strHnd2 = Integer.toString( oElem2.getObjectId() );
                            String strIdx2 = Integer.toString( j );
                            String strTip2 = oParam2.getTipo();
                            String strQdr2 = oParam2.getNomeQuadro();
                            String strOrg2 = oParam2.getQuadroOrigem();
                            String strDes2 = oParam2.getDesvio();
                            String strCir2 = oParam2.getCircuito();
                            String strCmd2 = oParam2.getComando();
                            String strFas2 = oParam2.getSistema();
                            //String strCal2 = oParam2.getNomeCalha();

                            //double dPot2 = oParam1.getPotencia();
                            //double dDem2 = oParam1.getPotenciaDemandada();
                            
                            //String strPot2 = nf6.format( dPot2 );
                            //String strDem2 = nf6.format( dDem2 );
                        
                            ExportaFiacaoVO oExp = new ExportaFiacaoVO(
                                strHnd,
                                strHnd1,
                                strIdx1,
                                strTip1,
                                strQdr1,
                                strOrg1,
                                strDes1,
                                strCir1,
                                strCmd1,
                                strFas1,
                                //dPot1,
                                //dDem1,
                                strHnd2,
                                strIdx2,
                                strTip2,
                                strQdr2,
                                strOrg2,
                                strDes2,
                                strCir2,
                                strCmd2,
                                strFas2
                                //dPot2,
                                //dDem2
                            );
                            lsExportaFiacao.add(oExp);
                        }
                    }
                }

                int szLsExportaFiacao = lsExportaFiacao.size();
                if (szLsExportaFiacao > 0) {
                	AppMain app = AppMain.getApp();
                	
                	AppCtx ctx = app.getCtx();
                	
                	String expFiacaoFile = ctx.getTempDir() + AppDefs.fiacaoImpFile;
                	int rscode = FiacaoUtil.exportaFiacao(expFiacaoFile, lsExportaFiacao);
                	if(rscode == AppDefs.RSOK) {
                		String homeDir = ctx.getTempDir();
                		String srcFile = AppDefs.fiacaoImpFile;
                		String targetFile = AppDefs.fiacaoExpFile;
                		String debugFile = AppDefs.fiacaoLogFile;                		
                		int debugMode = AppDefs.fiacaoDbg;
                		
                		FiacaoHelper helper = new FiacaoHelper(this.getDoc(), homeDir, debugMode);
                		rscode = helper.execute(srcFile, targetFile, debugFile);
                		if(rscode == AppDefs.RSOK) {
                			ArrayList<CadImportaFiacaoEletricaOData> lsImportaFiacao = helper.loadResults(targetFile);
	                		for(CadImportaFiacaoEletricaOData oImportaFiacao : lsImportaFiacao) {
	                			int objectId = StringUtil.safeInt(oImportaFiacao.getHnd());
	                			CadEntity ent1 = currBlockDef.getEntity(objectId);
	                			if(ent1 != null) {
	                            	int objType1 = ent1.getObjType();
	                            	if(objType1 == AppDefs.OBJTYPE_MODELELETRODUTO) {
		                				CadEletrodutoEletrica oEnt1 = (CadEletrodutoEletrica)ent1;
		                				oEnt1.setImportaFiacao(oImportaFiacao);
	                            	}
	                            	else if(objType1 == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
		                				CadEletroduto3DEletrica oEnt1 = (CadEletroduto3DEletrica)ent1;
		                				oEnt1.setImportaFiacao(oImportaFiacao);
	                            	}
	                			}
	                		}
                		}
                		EletricaModule oEleMod = app.getElModule();
                		oEleMod.setFiamode(AppDefs.FIAMODE_ON);
                	}
                	
                }
                
			}
			
		}
		
	}
	
}
