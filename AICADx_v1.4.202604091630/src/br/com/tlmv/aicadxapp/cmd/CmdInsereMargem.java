/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInsereMargem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 08/01/2026
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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadMargem;
import br.com.tlmv.aicadxapp.cad.CadParamMargemOData;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPontoArquitetura;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class CmdInsereMargem extends CmdBase
{
//Private

	/* PromptOption - MARGENS
	*/
	private PromptOptionVO optMargemA0 = new PromptOptionVO(AppDefs.OPT_MARGEM_A0, "A0", "0", true,  1189.0, 841.0, null, null, null);
	private PromptOptionVO optMargemA1 = new PromptOptionVO(AppDefs.OPT_MARGEM_A1, "A1", "1", false,  841.0, 594.0, null, null, null);
	private PromptOptionVO optMargemA2 = new PromptOptionVO(AppDefs.OPT_MARGEM_A2, "A2", "2", false,  594.0, 420.0, null, null, null);
	private PromptOptionVO optMargemA3 = new PromptOptionVO(AppDefs.OPT_MARGEM_A3, "A3", "3", false,  420.0, 297.0, null, null, null);

	/* PromptOption - DISCIPLINAS
	*/
	private PromptOptionVO optDisciplinaAguasPluviais = new PromptOptionVO(AppDefs.DISCIPLINE_AP_VAL, 	AppDefs.DISCIPLINE_AP_STR, 	"P", false);
	private PromptOptionVO optDisciplinaArquitetura = 	new PromptOptionVO(AppDefs.DISCIPLINE_ARQ_VAL, 	AppDefs.DISCIPLINE_ARQ_STR, "A", false);
	private PromptOptionVO optDisciplinaDrenagem = 		new PromptOptionVO(AppDefs.DISCIPLINE_RPD_VAL, 	AppDefs.DISCIPLINE_RPD_STR, "D", true);
	private PromptOptionVO optDisciplinaEletrica = 		new PromptOptionVO(AppDefs.DISCIPLINE_EL_VAL, 	AppDefs.DISCIPLINE_EL_STR, 	"E", false);
	private PromptOptionVO optDisciplinaEsgoto = 		new PromptOptionVO(AppDefs.DISCIPLINE_ES_VAL, 	AppDefs.DISCIPLINE_ES_STR, 	"S", false);
	private PromptOptionVO optDisciplinaGas = 			new PromptOptionVO(AppDefs.DISCIPLINE_G_VAL, 	AppDefs.DISCIPLINE_G_STR, 	"G", false);
	private PromptOptionVO optDisciplinaHidraulica = 	new PromptOptionVO(AppDefs.DISCIPLINE_H_VAL, 	AppDefs.DISCIPLINE_H_STR, 	"H", false);
	
//Public
	
	public CmdInsereMargem() {
		super(AppDefs.ACTION_FILE_MARGEM, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adicionando margem ao projeto...");

		ArrayList<PromptOptionVO> lsPromptOptionsMargem = new ArrayList<PromptOptionVO>();
		lsPromptOptionsMargem.add(this.optMargemA0);
		lsPromptOptionsMargem.add(this.optMargemA1);
		lsPromptOptionsMargem.add(this.optMargemA2);
		lsPromptOptionsMargem.add(this.optMargemA3);
		
		PromptOptionVO oMargemKey = PromptUtil.getKeyword(this, lsPromptOptionsMargem, "Selecione uma margem: ");
		if(oMargemKey == null) {
			oMargemKey = this.optMargemA0;
		}
		
		GeomPoint2d ptIns2d = PromptUtil.getStartPoint2d(this, null, null, "Insert point: ");
		if(ptIns2d == null) {
			ptIns2d = new GeomPoint2d(0.0, 0.0);
		}
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		double xPtDir = ptIns2d.getX() + 1.0;
		double yPtDir = ptIns2d.getY();
		
		GeomPoint3d ptDir3d = new GeomPoint3d(xPtDir, yPtDir, 0.0);		

		ArrayList<PromptOptionVO> lsPromptOptionsDisciplina = new ArrayList<PromptOptionVO>();
		lsPromptOptionsDisciplina.add(this.optDisciplinaAguasPluviais);
		lsPromptOptionsDisciplina.add(this.optDisciplinaArquitetura);
		lsPromptOptionsDisciplina.add(this.optDisciplinaDrenagem);
		lsPromptOptionsDisciplina.add(this.optDisciplinaEletrica);
		lsPromptOptionsDisciplina.add(this.optDisciplinaEsgoto);
		lsPromptOptionsDisciplina.add(this.optDisciplinaGas);
		lsPromptOptionsDisciplina.add(this.optDisciplinaHidraulica);
		
		PromptOptionVO oDisciplinaKey = PromptUtil.getKeyword(this, lsPromptOptionsDisciplina, "Disciplina: ");
		if(oDisciplinaKey == null) {
			oDisciplinaKey = this.optDisciplinaDrenagem;
		}

		String strDisciplina = oDisciplinaKey.getTextOption();
		
		String strNumeroDesenho = PromptUtil.getText(this, "No.Desenho: ");
		if( StringUtil.isEmpty(strNumeroDesenho) ) return null;
		
		String strDescricaoDesenho = PromptUtil.getText(this, "Descricao Desenho: ");
		if( StringUtil.isEmpty(strNumeroDesenho) ) return null;
		
		result = new InputParamVO();
		result.initKeyPointRotationAndParamMargem(
			oMargemKey, 
			ptIns3d, 
			ptDir3d, 
			strDisciplina, 
			strNumeroDesenho, 
			strDescricaoDesenho);		

		return result;
	}
	
	@Override
	public void doCommand() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
	
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		Date dataHoraAtual = new Date();
		
		Date dataAtual = new Date(dataHoraAtual.getYear(), dataHoraAtual.getMonth(), dataHoraAtual.getDay());
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			PromptOptionVO oKeyword = oParam.getKeyword();			
			GeomPoint3d ptIns3d_orig = oParam.getPt0(); 	
			GeomPoint3d ptDir3d_orig = oParam.getPt1(); 
			
			String strDisciplinaDesenho = oParam.getDisciplinaDesenho();
			String strNumeroDesenho = oParam.getNumeroDesenho();
			String strDescricaoDesenho = oParam.getDescricaoDesenho();

			String shapeName = String.format("SET-%s", oKeyword.getTextOption());
			
			//TO_LEVEL
			//
			double zLevel = 0.0;
			
			CadLevel oLevel = GeomUtil.getCurrLevel();    	
			if(oLevel != null) {
				zLevel = oLevel.getZLevel();
			}
			
			GeomPoint3d ptIns3d = GeomUtil.toLevelFromPt3d(ptIns3d_orig, oLevel); 			
			GeomPoint3d ptDir3d = GeomUtil.toLevelFromPt3d(ptDir3d_orig, oLevel); 

			//CADSHAPE
			//
			//-- LAYER
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_0_MARGEM);
			
			//-- SHAPE
			ShapeTable shapeTable = this.getDoc().getShapeTable();
			
			Shape oShape = shapeTable.getShape(shapeName);
			if(oShape != null) {
				CadProjectDef oProjetoDef = this.getDoc().getCurrProjectDef();
				
				String strTituloProjeto = oProjetoDef.getTituloProjeto();
				String strResponsavelTecnico = oProjetoDef.getNomeResponsavelTecnico();

				double dEscala = oProjetoDef.getEscala();
				String strEscala = String.format("1/", nf0.format(dEscala) );

				String strDataEmissao = df.format(dataAtual);

				String strNumeroRevisao = "0";
				
				GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);
				GeomPoint2d ptDir2d = new GeomPoint2d(ptDir3d);
				
				GeomVector2d vDir = new GeomVector2d(ptIns2d, ptDir2d);
				double rotateRad = vDir.angleToAxisX();
	
				double rotate = GeomUtil.convertRadToDegrees(rotateRad);
				
				double width = (Double)oKeyword.getVal0();
				double height = (Double)oKeyword.getVal1();
				
				CadMargem o = CadMargem.create(currBlockDef, oLayer, oLevel, ptIns3d, rotate, width, height, oShape);
				
				CadParamMargemOData oParamMargem = o.getParamMargemAt(0);
				oParamMargem.setTituloProjeto(strTituloProjeto);
				oParamMargem.setDisciplina(strDisciplinaDesenho);
				oParamMargem.setNumeroDesenho(strNumeroDesenho);
				oParamMargem.setDescricaoDesenho(strDescricaoDesenho);
				oParamMargem.setResponsavelTecnico(strResponsavelTecnico);
				oParamMargem.setEscala(strEscala);
				oParamMargem.setDataEmissao(strDataEmissao);
				oParamMargem.setNumeroRevisao(strNumeroRevisao);

				currBlockDef.addEntity(o);
			}
			
		}
		
	}

}
