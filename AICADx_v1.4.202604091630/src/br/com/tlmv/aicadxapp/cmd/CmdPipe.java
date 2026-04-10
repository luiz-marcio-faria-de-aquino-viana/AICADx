/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPipe.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/02/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class CmdPipe extends CmdBase
{
//Private Static
	private static double gAlturaInicial = 0.0;		// altura inicial em relacao ao nivel (m) = 0.0
	private static double gDiameterMili = 400.0;	// diametro (mm) = 400.0
	private static double gMinDiameterMili = 15.0;	// diametro minimo (mm) = 15.0
	private static double gSlope = -0.001;			// declividade = 0,1%
	
//Public

	public CmdPipe() {
		super(AppDefs.ACTION_DRAW1_PIPE, true, true);
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
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_DRAW_PIPE ) );

		// ALTURA_INICIAL
		//
		double dZ = CmdPipe.gAlturaInicial;

		String lblAlturaInicial = String.format(
			this.getR().getString( R.CMD_PRT_HEIGHT_FROM_CURRENT_LEVEL ),
			nf3.format( dZ ) );
		
		String strAlturaInicial = PromptUtil.getText(this, lblAlturaInicial );
		if( !StringUtil.isEmpty(strAlturaInicial) ) {
			dZ = StringUtil.safeDbl(nf6, strAlturaInicial);
			if(Math.abs(dZ) < AppDefs.MATHPREC_MIN) {
				dZ = 0.0;
			}
			CmdPipe.gAlturaInicial = dZ;
		}
		
		// DIAMETRO
		//
		double diameterMili = CmdPipe.gDiameterMili;

		String lblDiameterMili = String.format(
			this.getR().getString( R.CMD_PRT_DIAMETER_MILI ),
			nf3.format( diameterMili ) );
		
		String strDiameterMili = PromptUtil.getText(this, lblDiameterMili );
		if( !StringUtil.isEmpty(strDiameterMili) ) {
			diameterMili = StringUtil.safeDbl(nf6, strDiameterMili);
			if(Math.abs(diameterMili) < CmdPipe.gMinDiameterMili) {
				diameterMili = CmdPipe.gMinDiameterMili;
			}
			CmdPipe.gDiameterMili = diameterMili;
		}

		// CAIMENTO
		//
		double slope = CmdPipe.gSlope;

		String lblSlope = String.format(
			this.getR().getString( R.CMD_PRT_SLOPE_PERC ),
			nf3.format( slope ) );
		
		String strSlope = PromptUtil.getText(this, lblSlope );
		if( !StringUtil.isEmpty(strSlope) ) {
			slope = StringUtil.safeDbl(nf6, strSlope);
			if(Math.abs(slope) < AppDefs.MATHPREC_MIN) {
				slope = 0.0;
			}
			CmdPipe.gSlope = slope;
		}

		// START_POINT
		//
		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_START_POINT ) );
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);

		// END_POINT
		//
		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(this, pt2dI, this.getR().getString( R.CMD_PRT_END_POINT ) );
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);
		
		result = new InputParamVO();
		result.initPipe(pt3dI, pt3dF, diameterMili, slope, dZ);
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d pt3dI_orig = oParam.getPt0(); 
			GeomPoint3d pt3dF_orig = oParam.getPt1(); 
			double pipeDiameterMili = oParam.getDiametro();					//diameter in milimeters
			double pipeThicknessMili = pipeDiameterMili * 0.05;				//thickness (5% diameter) in milimeters
			double pipeSlope = oParam.getCaimento();						//slope (meter / meter)
			double pipeAltura = oParam.getAltura();							//altura em relacao ao nivel

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			oLevel.debug(AppDefs.DEBUG_LEVEL29);

			GeomPoint2d pt2dI = new GeomPoint2d(pt3dI_orig); 
			GeomPoint2d pt2dF = new GeomPoint2d(pt3dF_orig);			

			double dH = pt2dI.distTo(pt2dF);
			double dV = dH * pipeSlope;
			
			GeomPoint3d pt3dI = GeomUtil.toLevelFromPt3d(pt3dI_orig, oLevel, pipeAltura); 
			GeomPoint3d pt3dF = GeomUtil.toLevelFromPt3d(pt3dF_orig, oLevel, pipeAltura + dV); 
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_PONTOS);

		    int iCategoriaTubulacaoId = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
		    String strDescricaoCategoriaTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
			
			CadPipe oPipe = CadPipe.create(currBlockDef, oLayer, oLevel, pt3dI, pt3dF, strDescricaoCategoriaTubulacao, pipeDiameterMili, pipeThicknessMili);
			currBlockDef.addEntity(oPipe);
		}
	}

}
