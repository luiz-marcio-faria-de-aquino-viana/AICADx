/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdColunaTDEsgoto.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/12/2025
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
import br.com.tlmv.aicadxapp.cad.CadCilinder3d;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
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
import br.com.tlmv.aicadxmod.esgoto.cad.CadColunaEsgoto;
import br.com.tlmv.aicadxmod.esgoto.vo.CentroColunaEsgotoVO;

public class CmdColunaTDEsgoto extends CmdBase
{
//Private Static
	private static int gNumIdentColuna = 1;
	//
	private static double gAlturaBase = 0.0;
	private static double gComprimento = 20.0;
	
//Private
	private String name = AppDefs.NULL_STR;
	private String subcmdName = AppDefs.NULL_STR;
	private double diameterMeter = 0.0;

	/* Methodes */
	
	private CentroColunaEsgotoVO locationOf(CadEntity oEnt1, double centerDist1)
	{
		GeomPoint2d ptI = null;
		GeomPoint2d ptF = null;

		Double centerDist = centerDist1;
		
		if(oEnt1.getObjType() == AppDefs.OBJTYPE_LINE) {
			CadLine oLine1 = (CadLine)oEnt1;
			
			ptI = new GeomPoint2d( oLine1.getPtI() );
			ptF = new GeomPoint2d( oLine1.getPtF() );
		}
		else if(oEnt1.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) {
			CadParede oParede = (CadParede)oEnt1;
			centerDist += (oParede.getLarguraTotal() / 2.0);
			
			ptI = new GeomPoint2d( oParede.getPtI() );
			ptF = new GeomPoint2d( oParede.getPtF() );			
		}
		if( (ptI == null) && (ptF == null) ) return null;
		
		CentroColunaEsgotoVO ptResult = new CentroColunaEsgotoVO(ptI, ptF, centerDist );
		return ptResult;
	}
	
//Public
	
	public CmdColunaTDEsgoto(String cmdName, String subcmdName, double diameterMili) {
		super(cmdName + "_" + subcmdName, true, true);
		
		this.name = cmdName;
		this.subcmdName = subcmdName;
		this.diameterMeter = diameterMili / 1000.0;			// diametro em metros (1 mm = 1.0 / 1000.0 m)
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		InputParamVO result = null;
		
		String strTipoTubulacao = this.getTextoTipoTubulacao();
		
		String strTitulo = String.format(
			this.getR().getString( R.CMD_TIT_ES1_COLUNA_TD_ESGOTO ),
			strTipoTubulacao );
		PromptUtil.prompt( strTitulo );

		//PT-REFERENCE
		//
		int[] arrObjtype = {
			AppDefs.OBJTYPE_LINE,
			AppDefs.OBJTYPE_BIMPAREDE };
		EntSelVO entSel = PromptUtil.selectEntSel(this, arrObjtype, this.getR().getString( R.CMD_PRT_SELECT_OBJECT ) );
		if(entSel == null) return null;

		CadEntity oEnt1 = entSel.getEnt1();
		if(oEnt1 == null) return null;

		GeomPoint2d ptRef1 = new GeomPoint2d( entSel.getPtIns2d() );
		
		//RAIO_COLUNA
		//
		double radius = this.diameterMeter / 2.0;
		double centerDist = radius + AppDefs.DEF_COLUM_WALLSPACING;
		
		CentroColunaEsgotoVO oLocation1 = this.locationOf(oEnt1, centerDist);
		GeomPoint2d ptI = oLocation1.getPtI();		
		GeomPoint2d ptF = oLocation1.getPtF();
		
		double centerDist1 = oLocation1.getCenterDist();
		
		GeomPoint2d ptNear1 = GeomUtil.perpIntersectionOf(ptRef1, ptI, ptF, true);
		if(ptNear1 == null) return null;

		//PT-INSERTION_SIDE
		//
		GeomPoint2d ptSide1 = PromptUtil.getPoint2d(this, ptNear1, this.getR().getString( R.CMD_PRT_REFERENCE_SIDE ) );
		if(ptSide1 == null) return null;
		
		GeomPoint2d ptTg1 = GeomUtil.perpIntersectionOf(ptNear1, ptI, ptF, false);
		if(ptTg1 == null) return null;

		//CENTER_POINT
		//
		GeomVector2d vPerp = new GeomVector2d(ptTg1, ptSide1); 

		GeomPoint2d ptCenter2d = ptTg1.otherMoveTo(vPerp, centerDist1);
		
		GeomPoint3d ptCenter3d = new GeomPoint3d( ptCenter2d );

		//IDENTIFICADOR_COLUNA
		//
		int identificadorColuna = CmdColunaTDEsgoto.gNumIdentColuna; 
		
		String lblIdentificadorColuna = String.format(
			this.getR().getString( R.CMD_PRT_INDENTIFICADOR_COLUNA ),
			this.getIdentificadorColuna( identificadorColuna ) );

		String strIdentificadorColuna = PromptUtil.getText(this, lblIdentificadorColuna);
		if( !StringUtil.isEmpty( strIdentificadorColuna ) ) {
			identificadorColuna = StringUtil.safeInt( StringUtil.getOnlyNumbers(strIdentificadorColuna) );
		}
		
		//ALTURA_BASE
		//
		double zH = CmdColunaTDEsgoto.gAlturaBase;
		
		String lblAlturaBase = String.format(
			this.getR().getString( R.CMD_PRT_HEIGHT_FROM_CURRENT_LEVEL ),
			nf3.format(zH) );

		String strAlturaBase = PromptUtil.getText(this, lblAlturaBase);
		if(strAlturaBase == null) {
			zH = StringUtil.safeDbl(nf3, strAlturaBase);
		}

		CmdColunaTDEsgoto.gAlturaBase = zH;
		PromptUtil.promptAltura( CmdColunaTDEsgoto.gAlturaBase );
		
		//COMPRIMENTO
		//
		double comprimento = CmdColunaTDEsgoto.gComprimento;

		String lblComprimento = String.format(
			this.getR().getString( R.CMD_PRT_PIPE_COLUMN_LENGHT ),
			nf3.format(comprimento) );

		String strComprimento = PromptUtil.getText(this, lblComprimento);
		if(strComprimento == null) {
			comprimento = StringUtil.safeDbl(nf3, strComprimento);
		}
		
		CmdColunaTDEsgoto.gComprimento = comprimento;
		PromptUtil.promptAltura( CmdColunaTDEsgoto.gComprimento );
		
		result = new InputParamVO();
		result.initColuna(ptCenter3d, identificadorColuna, radius, zH, comprimento);
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptCenter3d_orig = oParam.getPtCenter(); 
			int identificadorColuna = oParam.getIntVal();
			double outsideRadius = oParam.getRadius(); 
			double alturaBase = oParam.getAlturaBase(); 
			double comprimento = oParam.getComprimento(); 
			//
			double thickness = outsideRadius * AppDefs.DEF_PERC_COLUM_THICKNESS;
			double insideRadius = outsideRadius - thickness; 
			
			String strIdentificadorColuna = this.getIdentificadorColuna( identificadorColuna );
			
			//LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			GeomPoint3d ptCenter3d = GeomUtil.toLevelFromPt3d(ptCenter3d_orig, oLevel); 
			double zH = ptCenter3d.getZ() + alturaBase;

			ptCenter3d = new GeomPoint3d(ptCenter3d.getX(), ptCenter3d.getY(), zH);
			
			//CADLINE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_PONTOS);
			
			CadColunaEsgoto oColuna = CadColunaEsgoto.create(currBlockDef, oLayer, oLevel, ptCenter3d, strIdentificadorColuna, insideRadius, outsideRadius, comprimento, thickness);
			currBlockDef.addEntity(oColuna);
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
	
	private String getIdentificadorColuna(int identificadorColuna)
	{
		String strTmpResult = this.getR().getString( R.TXT_ES1_COLUNA_ESGOTO_PRIMARIO );
		
		String action = this.getSubcmdName();
		if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO ) ) {
			strTmpResult = this.getR().getString( R.TXT_ES1_COLUNA_ESGOTO_PRIMARIO );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO ) ) {
			strTmpResult = this.getR().getString( R.TXT_ES1_COLUNA_ESGOTO_SECUNDARIO );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA ) ) {
			strTmpResult = this.getR().getString( R.TXT_ES1_COLUNA_ESGOTO_SECUNDARIO_GORDURA );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO ) ) {
			strTmpResult = this.getR().getString( R.TXT_ES1_COLUNA_ESGOTO_SECUNDARIO_SABAO );
		}
		else if( action.startsWith( AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO ) ) {
			strTmpResult = this.getR().getString( R.TXT_ES1_COLUNA_ESGOTO_VENTILACAO );
		}
	
		String strResult = String.format(strTmpResult, identificadorColuna);
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
	
	public double getDiameterMeter() {
		return diameterMeter;
	}
	
	public void setDiameterMeter(double diameterMeter) {
		this.diameterMeter = diameterMeter;
	}

}
