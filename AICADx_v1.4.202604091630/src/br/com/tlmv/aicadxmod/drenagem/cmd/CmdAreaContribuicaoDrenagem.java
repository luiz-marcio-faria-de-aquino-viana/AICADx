/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdArea.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
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

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArea;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadPolygon;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.cmd.ICmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CmdAreaContribuicaoDrenagem extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optAreaDrenagem = new PromptOptionVO(AppDefs.OPT_AREATYPE_DRENAGEAREA, "Area Drenagem", "D", true);
	
//Public
	
	public CmdAreaContribuicaoDrenagem() {
		super(AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO, true, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>();
		ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>();

		PromptUtil.prompt("DRENAGEM: Adding new Area...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAreaDrenagem);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(this, lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText(this, "Nome da area: ");
		if(strName == null) return null;
		
		CadEntity ent1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODDRCAIXAINSPECAO, "Caixa de Inspecao: ");
		if(ent1 == null) return null;
		
		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(this, null, "Start point: ");
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);

		GeomPoint2d pt02d = new GeomPoint2d(ptI2d);
		GeomPoint3d pt03d = new GeomPoint3d(ptI3d);		
		
		lsPts2d.add(ptI2d);		
		lsPts3d.add(ptI3d);		

		for( ; ; ) {
			GeomPoint2d ptF2d = PromptUtil.getSecondPoint2d(this, ptI2d, lsPts2d, "Next point: ");
			if(ptF2d == null) break;
		
			GeomPoint3d ptF3d = new GeomPoint3d(ptF2d);

			lsPts2d.add(ptF2d);
			lsPts3d.add(ptF3d);
			
			ptI2d = ptF2d;
		}						
		lsPts2d.add(pt02d);
		lsPts3d.add(pt03d);
		
		result = new InputParamVO();
		result.initEntityKeyAreaWithEntity(oKeyword, strName, lsPts3d, ent1);
		
		return result;
	}	
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//LIST OF TEXTOS/GEOMPOINT3D
			//
			PromptOptionVO oKeyword = oParam.getKeyword();
			String text = oParam.getText();
			CadCaixaInspecaoDrenagem oEnt1 = (CadCaixaInspecaoDrenagem)oParam.getEnt1();
			ArrayList<GeomPoint3d> lsPts3d_orig = oParam.getLsPts3d(); 

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel(this.getDoc());
			
			ArrayList<GeomPoint3d> lsPts3d = GeomUtil.toLevelFromLsPts3d(lsPts3d_orig, oLevel); 
			
			//CADPOLYGON
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_AREAS_CONTRIB);
			
			CadAreaContribuicaoDrenagem oArea = CadAreaContribuicaoDrenagem.create(currBlockDef, oLayer, oLevel, oKeyword.getOptionId(), text, lsPts3d, oEnt1);
			currBlockDef.addEntity(oArea);
			
			//oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
