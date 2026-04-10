/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPipeLine.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/12/2025
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

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.CadPipeLine;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PipeLineSelVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class CmdPipeLine extends CmdBase
{
//Private Static
	private static double gAlturaInicial = 0.0;		// altura inicial em relacao ao nivel (m) = 0.0
	private static double gDiameterMili = 400.0;	// diametro (mm) = 400.0
	private static double gMinDiameterMili = 15.0;	// diametro minimo (mm) = 15.0
	private static double gSlope = -0.001;			// declividade = 0,1%
	
//Private
	
	private PipeLineSelVO toPipe(GeomPoint2d pt0, GeomPoint2d ptI, GeomPoint2d ptF)
	{
		PipeLineSelVO oResult = new PipeLineSelVO(pt0, ptI, ptF);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		GeomVector2d vIF = new GeomVector2d(ptI, ptF);
		GeomVector2d uIF = vIF.otherUnit();

		GeomVector2d vF0 = new GeomVector2d(ptF, pt0);
		double angleRad = uIF.angleTo(vF0);

		double angleDegrees = GeomUtil.convertRadToDegrees(angleRad);
		
		String warnmsg = String.format("AngleDegrees: %s; ", nf6.format(angleDegrees) );
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL31, warnmsg, this.getClass());
		
		GeomPoint2d ptRef2d = null;
		
		if( ( (angleDegrees >= AppDefs.MATHVAL_0d  ) && (angleDegrees <= AppDefs.MATHVAL_90d ) ) ||
		    ( (angleDegrees >= AppDefs.MATHVAL_270d) && (angleDegrees <= AppDefs.MATHVAL_360d) ) ) {
			
			if( ( (angleDegrees >= AppDefs.ANGLEDIR_0_INF  ) && (angleDegrees <= AppDefs.ANGLEDIR_0_SUP  ) ) ||
			    ( (angleDegrees >= AppDefs.ANGLEDIR_360_INF) && (angleDegrees <= AppDefs.ANGLEDIR_360_SUP) ) ) {
				ptRef2d = GeomUtil.toPipe0d(pt0, ptI, ptF);			
				if(ptRef2d != null) {
					PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PIPE_INLINE ) );								
					oResult.setResult(AppDefs.pipeCurve0d, ptRef2d);				
				}
			}
			else {
				ptRef2d = GeomUtil.toPipe45d(pt0, ptI, ptF);			
				if(ptRef2d != null) {
					PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PIPE_INCURVE45 ) );								
					oResult.setResult(AppDefs.pipeCurve45d, ptRef2d);				
				}
				else {
					ptRef2d = GeomUtil.toPipe90d(pt0, ptI, ptF);			
					if(ptRef2d != null) {
						PromptUtil.prompt( this.getR().getString( R.CMD_PRT_PIPE_INCURVE90 ) );								
						oResult.setResult(AppDefs.pipeCurve90d, ptRef2d);				
					}
				}								
			}
		}		
		return oResult;
	}
	
//Public

	public CmdPipeLine() {
		super(AppDefs.ACTION_DRAW1_PIPELINE, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_DRAW_PIPE_LINE ) );

		// ALTURA_INICIAL
		//
		double dZ = CmdPipeLine.gAlturaInicial;

		String lblAlturaInicial = String.format(
			this.getR().getString( R.CMD_PRT_HEIGHT_FROM_CURRENT_LEVEL ),
			nf3.format( dZ ) );
		
		String strAlturaInicial = PromptUtil.getText(this, lblAlturaInicial );
		if( !StringUtil.isEmpty(strAlturaInicial) ) {
			dZ = StringUtil.safeDbl(nf6, strAlturaInicial);
			if(Math.abs(dZ) < AppDefs.MATHPREC_MIN) {
				dZ = 0.0;
			}
			CmdPipeLine.gAlturaInicial = dZ;
		}
		
		// DIAMETRO
		//
		double diameterMili = CmdPipeLine.gDiameterMili;

		String lblDiameterMili = String.format(
			this.getR().getString( R.CMD_PRT_DIAMETER_MILI ),
			nf3.format( diameterMili ) );
		
		String strDiameterMili = PromptUtil.getText(this, lblDiameterMili );
		if( !StringUtil.isEmpty(strDiameterMili) ) {
			diameterMili = StringUtil.safeDbl(nf6, strDiameterMili);
			if(Math.abs(diameterMili) < CmdPipeLine.gMinDiameterMili) {
				diameterMili = CmdPipeLine.gMinDiameterMili;
			}
			CmdPipeLine.gDiameterMili = diameterMili;
		}

		// CAIMENTO
		//
		double slope = CmdPipeLine.gSlope;

		String lblSlope = String.format(
			this.getR().getString( R.CMD_PRT_SLOPE_PERC ),
			nf3.format( slope ) );
		
		String strSlope = PromptUtil.getText(this, lblSlope );
		if( !StringUtil.isEmpty(strSlope) ) {
			slope = StringUtil.safeDbl(nf6, strSlope);
			if(Math.abs(slope) < AppDefs.MATHPREC_MIN) {
				slope = 0.0;
			}
			CmdPipeLine.gSlope = slope;
		}
		
		// START_POINT
		//
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>();

		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_START_POINT ) );
		if(ptI2d == null) return null;
		lsPts2d.add(ptI2d);		

		// NEXT_POINT
		//
		GeomPoint2d ptF2d = null;
		for( ; ; ) {
			if(ptF2d == null) {
				ptF2d = PromptUtil.getSecondPoint2d(this, ptI2d, lsPts2d, this.getR().getString( R.CMD_PRT_NEXT_POINT ) );
				if(ptF2d == null) break;				
				lsPts2d.add(ptF2d);				
			}
			else {
				GeomPoint2d pt02d = PromptUtil.getSecondPoint2d(this, ptF2d, lsPts2d, this.getR().getString( R.CMD_PRT_NEXT_POINT ) );
				if(pt02d == null) break;

				PipeLineSelVO oResult = this.toPipe(pt02d, ptI2d, ptF2d);

				int iResult = oResult.getResult();
				if(iResult == AppDefs.pipeCurveNone) continue;
				
				GeomPoint2d ptRef2d = new GeomPoint2d( oResult.getPtRef2d() );
				
				if(iResult == AppDefs.pipeCurve0d) {
					lsPts2d.add( ptRef2d );

					ptI2d = ptF2d;
					ptF2d = ptRef2d;
				}
				else if(iResult == AppDefs.pipeCurve90d) {
					lsPts2d.add( ptRef2d );
					lsPts2d.add( pt02d );

					ptI2d = ptRef2d;
					ptF2d = pt02d;
				}
				else if(iResult == AppDefs.pipeCurve45d) {
					lsPts2d.add( ptRef2d );
					lsPts2d.add( pt02d );

					ptI2d = ptRef2d;
					ptF2d = pt02d;
				}			
			}
		}
		
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.copyPt2dTo3dList(lsPts2d, 0.0);
		
		result = new InputParamVO();
		result.initPipeLine(lsPts3d, diameterMili, slope, dZ);
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			ArrayList<GeomPoint3d> lsPts3d_orig = oParam.getLsPts3d(); 
			double pipeDiameterMili = oParam.getDiametro();					//diameter in milimeters
			double pipeThicknessMili = pipeDiameterMili * 0.05;				//thickness (5% diameter) in milimeters
			double pipeSlope = oParam.getCaimento();						//slope (meter / meter)
			double pipeAltura = oParam.getAltura();							//altura em relacao ao nivel

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			oLevel.debug(AppDefs.DEBUG_LEVEL29);

			ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>(); 

			GeomPoint3d pt3dI_orig = lsPts3d_orig.get(0);
			
			GeomPoint3d pt3dI = GeomUtil.toLevelFromPt3d(pt3dI_orig, oLevel, pipeAltura); 
			lsPts3d.add(pt3dI);

			for(int i = 1; i < lsPts3d_orig.size(); i++) {				
				GeomPoint3d pt3dF_orig = lsPts3d_orig.get(i);
			
				GeomPoint2d pt2dI = new GeomPoint2d(pt3dI_orig); 
				GeomPoint2d pt2dF = new GeomPoint2d(pt3dF_orig);			

				double dH = pt2dI.distTo(pt2dF);
				double dV = dH * pipeSlope;

				pipeAltura += dV;
				
				GeomPoint3d pt3dF = GeomUtil.toLevelFromPt3d(pt3dF_orig, oLevel, pipeAltura); 
				lsPts3d.add(pt3dF);
				
				pt3dI_orig = pt3dF_orig;
			}
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);

		    int iCategoriaTubulacaoId = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
		    String strDescricaoCategoriaTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
			
			CadPipeLine oPipeLine = CadPipeLine.create(currBlockDef, oLayer, oLevel, strDescricaoCategoriaTubulacao, pipeDiameterMili, pipeThicknessMili, lsPts3d);
			currBlockDef.addEntity(oPipeLine);
		}
	}

}
