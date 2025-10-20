/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadArea;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdArea extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optAmbiente = new PromptOptionVO(AppDefs.OPT_AREATYPE_ROOM, "Ambiente", "A", false);
	private PromptOptionVO optApartamento = new PromptOptionVO(AppDefs.OPT_AREATYPE_APARTMENT, "aPartamento", "P", true);
	private PromptOptionVO optVaranda = new PromptOptionVO(AppDefs.OPT_AREATYPE_BALCONY, "Varanda", "V", false);
	private PromptOptionVO optAreaComum = new PromptOptionVO(AppDefs.OPT_AREATYPE_BUILDINGCOMMOM, "area Comum", "C", false);
	private PromptOptionVO optAreaInterna = new PromptOptionVO(AppDefs.OPT_AREATYPE_BUILDINGINTERNAL, "area Interna", "I", false);
	private PromptOptionVO optAreaExterna = new PromptOptionVO(AppDefs.OPT_AREATYPE_BUILDINGEXTERNAL, "area Externa", "E", false);
	private PromptOptionVO optEstacionamento = new PromptOptionVO(AppDefs.OPT_AREATYPE_PARKING, "eStacionamento", "S", false);
	private PromptOptionVO optTerreno = new PromptOptionVO(AppDefs.OPT_AREATYPE_TERRAIN, "Terreno", "T", false);
	
//Public
	
	public CmdArea() {
		super(AppDefs.ACTION_DRAW1_AREA, true, true);		
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>();
		ArrayList<GeomPoint3d> lsPts3d = new ArrayList<GeomPoint3d>();

		PromptUtil.prompt("Adding new Area...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAmbiente);
		lsPromptOptions.add(optApartamento);
		lsPromptOptions.add(optVaranda);
		lsPromptOptions.add(optAreaComum);
		lsPromptOptions.add(optAreaInterna);
		lsPromptOptions.add(optAreaExterna);
		lsPromptOptions.add(optEstacionamento);
		lsPromptOptions.add(optTerreno);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		String strName = PromptUtil.getText("Nome da area: ");
		if(strName == null) return null;

		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(null, "Start point: ");
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);

		GeomPoint2d pt02d = new GeomPoint2d(ptI2d);
		GeomPoint3d pt03d = new GeomPoint3d(ptI3d);		
		
		lsPts2d.add(ptI2d);		
		lsPts3d.add(ptI3d);		

		for( ; ; ) {
			GeomPoint2d ptF2d = PromptUtil.getSecondPoint2d(ptI2d, lsPts2d, "Next point: ");
			if(ptF2d == null) break;
		
			GeomPoint3d ptF3d = new GeomPoint3d(ptF2d);

			lsPts2d.add(ptF2d);
			lsPts3d.add(ptF3d);
			
			ptI2d = ptF2d;
		}						
		lsPts2d.add(pt02d);
		lsPts3d.add(pt03d);
		
		result = new InputParamVO();
		result.initKeyArea(oKeyword, strName, lsPts3d);
		
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
			ArrayList<GeomPoint3d> lsPts3d = oParam.getLsPt(); 
			
			//CADPOLYGON
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_AREAS);
			
			CadArea oArea = CadArea.create(currBlockDef, oLayer, oKeyword.getOptionId(), text, lsPts3d);
			currBlockDef.addEntity(oArea);
			
			//oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
