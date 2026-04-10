/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPipe.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/03/2026
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

package br.com.tlmv.aicadxmod.esgoto.cmd;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.esgoto.calc.EsgotoCalc;
import br.com.tlmv.aicadxmod.esgoto.vo.TubulacaoEsgotoVO;

public class CmdPipeEsgoto extends CmdBase
{
//Private Static
	private static TubulacaoEsgotoVO gDiaTubulacaoMeter = EsgotoCalc.DIAM_TUBULACAO_PVC_40MM;		// diametro (mm) = 40.0
	private static TubulacaoEsgotoVO gMinDiaTubulacaoMeter = EsgotoCalc.DIAM_TUBULACAO_PVC_40MM;		// diametro minimo (mm) = 40.0
	private static double gAlturaInicial = 0.0;		// altura inicial em relacao ao nivel (m) = 0.0
	private static double gSlope = -0.001;			// declividade = 0,1%

	private static int[] arrObjType = {
		AppDefs.OBJTYPE_MODESRALOSIFONADO,				
		AppDefs.OBJTYPE_MODESCAIXAINSPECAO,				
		AppDefs.OBJTYPE_MODESCOLUNA				
	};
	
//Private
	private TubulacaoEsgotoVO diaTubulacaoMeter = null;
	
//Public

	public CmdPipeEsgoto(String action, TubulacaoEsgotoVO diaTubulacaoMeter) {
		super(action, true, true);
		
		this.diaTubulacaoMeter = CmdPipeEsgoto.gDiaTubulacaoMeter;
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		String strTipoTubulacao = this.getTextoTipoTubulacao();
		
		String strTitulo = String.format(
			this.getR().getString( R.CMD_TIT_ES1_PIPE_ESGOTO ),
			strTipoTubulacao );
		PromptUtil.prompt( strTitulo );

		// ALTURA_INICIAL
		//
		double dZ = CmdPipeEsgoto.gAlturaInicial;

		String lblAlturaInicial = String.format(
			this.getR().getString( R.CMD_PRT_HEIGHT_FROM_CURRENT_LEVEL ),
			nf3.format( dZ ) );
		
		String strAlturaInicial = PromptUtil.getText(this, lblAlturaInicial );
		if( !StringUtil.isEmpty(strAlturaInicial) ) {
			dZ = StringUtil.safeDbl(nf6, strAlturaInicial);
			if(Math.abs(dZ) < AppDefs.MATHPREC_MIN) {
				dZ = 0.0;
			}
			CmdPipeEsgoto.gAlturaInicial = dZ;
		}
		
		// CAIMENTO
		//
		double slope = CmdPipeEsgoto.gSlope;

		String lblSlope = String.format(
			this.getR().getString( R.CMD_PRT_SLOPE_PERC ),
			nf3.format( slope ) );
		
		String strSlope = PromptUtil.getText(this, lblSlope );
		if( !StringUtil.isEmpty(strSlope) ) {
			slope = StringUtil.safeDbl(nf6, strSlope);
			if(Math.abs(slope) < AppDefs.MATHPREC_MIN) {
				slope = 0.0;
			}
			CmdPipeEsgoto.gSlope = slope;
		}

		// START_POINT
		//
		GeomPoint2d pt2dI = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_START_POINT ) );
		if(pt2dI == null) return null;
		
		GeomPoint3d pt3dI = new GeomPoint3d(pt2dI);
		
		//CadEntity oEntI = PromptUtil.selectObjectAt(arrObjType, pt2dI);
		//if(oEntI != null) return null;
		
		// END_POINT
		//
		GeomPoint2d pt2dF = PromptUtil.getSecondPoint2d(this, pt2dI, this.getR().getString( R.CMD_PRT_END_POINT ) );
		if(pt2dF == null) return null;
		
		GeomPoint3d pt3dF = new GeomPoint3d(pt2dF);

		CadEntity oEntF = PromptUtil.selectObjectAt(arrObjType, pt2dF);
		if(oEntF != null) return null;
		
		result = new InputParamVO();
		result.initPipe(pt3dI, pt3dF, AppDefs.NULL_DBL, slope, dZ);
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
			//double pipeDiameterMili = oParam.getDiametro();												//diameter in milimeters
			//double pipeThicknessMili = pipeDiameterMili * 0.05;											//thickness (5% diameter) in milimeters
			double pipeDiameterMili = this.diaTubulacaoMeter.getDiamNominalMeter() * 1000.0;
			double pipeThicknessMili = this.diaTubulacaoMeter.getEspessuraTubulacaoMeter() * 1000.0;
			double pipeSlope = oParam.getCaimento();														//slope (meter / meter)
			double pipeAltura = oParam.getAltura();															//altura em relacao ao nivel

			String strDescricao = this.diaTubulacaoMeter.getDescricao();
			
			//LEVEL
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
			CadLayerDef oLayer = this.getLayerDef();
			
			CadPipe oPipe = CadPipe.create(currBlockDef, oLayer, oLevel, pt3dI, pt3dF, strDescricao, pipeDiameterMili, pipeThicknessMili);
			currBlockDef.addEntity(oPipe);
		}
	}
	
	/* Getters/Setters */
	
	private String getTextoTipoTubulacao()
	{
		String strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_PRIMARIO );
		
		String action = this.getCmdName();

		if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO ) ) {
			strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_PRIMARIO );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO ) ) {
			strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_SECUNDARIO );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA ) ) {
			strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_SECUNDARIO_GORDURA );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO ) ) {
			strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_SECUNDARIO_SABAO );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO ) ) {
			strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_VENTILACAO );
		}
		return strResult;
	}

	private CadLayerDef getLayerDef()
	{
		LayerTable oTbl = this.getDoc().getLayerTable();
		
		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_TB_PRIMARIO);

		String action = this.getCmdName();

		if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO ) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_TB_PRIMARIO);
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO ) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_TB_SECUNDARIO);
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA ) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_TB_SECUND_GORD);
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO ) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_TB_SECUND_MLR);
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO ) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_TB_VENTILACAO);
		}
		return oLayer;
	}
	
}
