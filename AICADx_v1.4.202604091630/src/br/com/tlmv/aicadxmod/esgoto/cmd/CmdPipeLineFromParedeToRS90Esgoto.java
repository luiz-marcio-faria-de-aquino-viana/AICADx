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
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
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
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.EntSelVO;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.esgoto.calc.EsgotoCalc;
import br.com.tlmv.aicadxmod.esgoto.vo.TubulacaoEsgotoVO;

public class CmdPipeLineFromParedeToRS90Esgoto extends CmdBase
{
//Private Static
	private static TubulacaoEsgotoVO gDiaTubulacaoMeter = EsgotoCalc.DIAM_TUBULACAO_PVC_40MM;			// diametro (mm) = 40.0
	private static TubulacaoEsgotoVO gMinDiaTubulacaoMeter = EsgotoCalc.DIAM_TUBULACAO_PVC_40MM;		// diametro minimo (mm) = 40.0
	private static double gAlturaPonto = 0.7;						// altura do ponto (m) = 0.7
	private static double gAlturaPiso = -0.05;						// altura da tubulacao em relacao ao piso (m) = -0.05
	private static double gSlope = -0.001;							// declividade = 0,1% = 0,001

	private static int[] arrObjTypeInicial = {
		AppDefs.OBJTYPE_BIMPAREDE,
		AppDefs.OBJTYPE_LINE
	};
	
	private static int[] arrObjTypeFinal = {
		AppDefs.OBJTYPE_MODESRALOSIFONADO
	};
		
//Private
	private String name = AppDefs.NULL_STR;
	private String subcmdName = AppDefs.NULL_STR;
	private TubulacaoEsgotoVO diaTubulacaoMeter = null;
	
//Public

	public CmdPipeLineFromParedeToRS90Esgoto(String cmdName, String subcmdName, TubulacaoEsgotoVO diaTubulacaoMeter) {
		super(cmdName + "_" + subcmdName, true, true);
		
		this.name = cmdName;
		this.subcmdName = subcmdName;
		this.diaTubulacaoMeter = CmdPipeLineFromParedeToRS90Esgoto.gDiaTubulacaoMeter;
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
		double dZ = CmdPipeLineFromParedeToRS90Esgoto.gAlturaPonto;

		String lblAlturaPonto = String.format(
			this.getR().getString( R.CMD_PRT_HPONTO_FROM_CURRENT_LEVEL ),
			nf3.format( dZ ) );
		
		String strAlturaPonto = PromptUtil.getText(this, lblAlturaPonto );
		if( !StringUtil.isEmpty(strAlturaPonto) ) {
			dZ = StringUtil.safeDbl(nf6, strAlturaPonto);
			if(Math.abs(dZ) < AppDefs.MATHPREC_MIN) {
				dZ = 0.0;
			}
			CmdPipeLineFromParedeToRS90Esgoto.gAlturaPonto = dZ;
		}
		
		// CAIMENTO
		//
		double slope = CmdPipeLineFromParedeToRS90Esgoto.gSlope;

		String lblSlope = String.format(
			this.getR().getString( R.CMD_PRT_SLOPE_PERC ),
			nf3.format( slope ) );
		
		String strSlope = PromptUtil.getText(this, lblSlope );
		if( !StringUtil.isEmpty(strSlope) ) {
			slope = StringUtil.safeDbl(nf6, strSlope);
			if(Math.abs(slope) < AppDefs.MATHPREC_MIN) {
				slope = 0.0;
			}
			CmdPipeLineFromParedeToRS90Esgoto.gSlope = slope;
		}

		// START_POINT
		//
		String strMsgI = this.getR().getString( R.CMD_PRT_SELECT_PAREDE );
		EntSelVO entSelI = PromptUtil.selectEntSel(this, CmdPipeLineFromParedeToRS90Esgoto.arrObjTypeInicial, strMsgI);
		if(entSelI == null) return null;

		CadEntity entI = entSelI.getEnt1();
		if(entI == null) return null;
		
		CadParede paredeI = (CadParede)entI;
		
		GeomPoint2d pt2dI = entSelI.getPtIns2d();
		
		GeomPoint3d ptConexao3d = entI.nearestPoint( new GeomPoint3d( pt2dI ) ); 
		GeomPoint2d ptConexao2d = new GeomPoint2d( ptConexao3d ); 

		// END_POINT
		//
		String strMsgF = this.getR().getString( R.CMD_PRT_SELECT_EQUIPAMENTO_ESGOTO );
		EntSelVO entSelF = PromptUtil.selectEntSel(this, CmdPipeLineFromParedeToRS90Esgoto.arrObjTypeInicial, strMsgF);
		if(entSelF == null) return null;

		CadEntity entF = entSelF.getEnt1();
		if(entF == null) return null;
		
		GeomPoint2d pt2dF = entSelF.getPtIns2d();
		
		GeomPoint3d ptConexaoRS3d = entF.nearestPoint( new GeomPoint3d( pt2dF ) ); 
		GeomPoint2d ptConexaoRS2d = new GeomPoint2d( ptConexaoRS3d ); 
		
		GeomPoint3d ptCentroRS3d = entF.centroid(); 
		GeomPoint2d ptCentroRS2d = new GeomPoint2d( ptCentroRS3d ); 

		//LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
		oLevel.debug(AppDefs.DEBUG_LEVEL29);
		double zLevelElevation = oLevel.getZLevelElevation();
		
		//CALCULATE
		//
		double wParede = paredeI.getLarguraTotal();
		double w2Parede = wParede / 2.0;

		GeomPoint2d ptParedeI = new GeomPoint2d( paredeI.getPtI() );
		GeomPoint2d ptParedeF = new GeomPoint2d( paredeI.getPtF() );
		
		GeomVector2d vParedeDir = new GeomVector2d(ptParedeI, ptParedeF);
		GeomVector2d uParedeDir = vParedeDir.otherUnit();
		GeomVector2d nParedeDir = uParedeDir.otherNorm();

		GeomVector2d vConexaoDir = new GeomVector2d(ptParedeI, ptParedeF);
		double d = uParedeDir.dotProd(vConexaoDir);
		double d2 = d / 2.0;
		
		//PT0
		//
		GeomPoint2d pt0_2d = ptConexao2d.otherMoveTo(nParedeDir, d2);

		double xPt0 = pt0_2d.getX();
		double yPt0 = pt0_2d.getY();
		double zPt0 = ptConexao3d.getZ();

		GeomPoint3d pt0_3d = new GeomPoint3d(xPt0, yPt0, zPt0);

		//PT1
		//
		double xPt1 = xPt0;
		double yPt1 = yPt0;
		double zPt1 = zLevelElevation + CmdPipeLineFromParedeToRS90Esgoto.gAlturaPiso;

		GeomPoint2d pt1_2d = new GeomPoint2d(xPt1, yPt1);

		GeomPoint3d pt1_3d = new GeomPoint3d(xPt1, yPt1, zPt1);
		
		//PT2
		//
		GeomPoint2d pt2_2d = GeomUtil.perpIntersectionOf(pt1_2d, ptCentroRS2d, ptConexaoRS2d, false);

		GeomPoint3d pt2_3d = new GeomPoint3d(pt2_2d);
		
		//RESULT
		//
		result = new InputParamVO();
		result.initPipe(ptConexao3d, pt0_3d, pt1_3d, pt2_3d, ptConexaoRS3d, AppDefs.NULL_DBL, slope, dZ);
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			ArrayList<GeomPoint3d> lsPts = new ArrayList<GeomPoint3d>();
			
			double pipeSlope = oParam.getCaimento();														//slope (meter / meter)
			double pipeAltura = oParam.getAltura();															//altura em relacao ao nivel

			double pipeDiameterMili = this.diaTubulacaoMeter.getDiamNominalMeter() * 1000.0;
			double pipeThicknessMili = this.diaTubulacaoMeter.getEspessuraTubulacaoMeter() * 1000.0;

			String strDescricao = this.diaTubulacaoMeter.getDescricao();

			GeomPoint3d ptI = oParam.getPt0(); 
			lsPts.add(ptI);

			GeomPoint3d pt0 = oParam.getPt1(); 
			lsPts.add(pt0);

			GeomPoint3d pt1 = oParam.getPt2(); 
			lsPts.add(pt1);

			GeomPoint3d pt2 = oParam.getPt3(); 
			lsPts.add(pt2);

			GeomPoint3d ptF = oParam.getPt4();
			lsPts.add(ptF);

			//LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			oLevel.debug(AppDefs.DEBUG_LEVEL29);
			
			//CADLINE
			//
			CadLayerDef oLayer = this.getLayerDef();
			
			CadPipeLine oPipeLine = CadPipeLine.create(currBlockDef, oLayer, oLevel, strDescricao, pipeDiameterMili, pipeThicknessMili, lsPts);
			currBlockDef.addEntity(oPipeLine);
		}
	}
	
	/* Getters/Setters */
	
	private String getTextoTipoTubulacao()
	{
		String strResult = this.getR().getString( R.CMD_TIT_ES1_ESGOTO_PRIMARIO );
		
		String action = this.getSubcmdName();
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

		String action = this.getSubcmdName();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubcmdName() {
		return subcmdName;
	}

	public void setSubcmdName(String subcmdName) {
		this.subcmdName = subcmdName;
	}

	public TubulacaoEsgotoVO getDiaTubulacaoMeter() {
		return diaTubulacaoMeter;
	}

	public void setDiaTubulacaoMeter(TubulacaoEsgotoVO diaTubulacaoMeter) {
		this.diaTubulacaoMeter = diaTubulacaoMeter;
	}
	
}
