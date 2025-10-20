/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdAreaTable.java
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
import br.com.tlmv.aicadxapp.cad.CadAreaTable;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;

public class CmdAreaTable extends CmdBase
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
	private PromptOptionVO optDrenagem = new PromptOptionVO(AppDefs.OPT_AREATYPE_DRENAGEAREA, "area Drenagem", "D", false);
	
//Public

	public CmdAreaTable() {
		super(AppDefs.ACTION_DRAW1_AREATABLE, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Adding new Area Table...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optAmbiente);
		lsPromptOptions.add(optApartamento);
		lsPromptOptions.add(optVaranda);
		lsPromptOptions.add(optAreaComum);
		lsPromptOptions.add(optAreaInterna);
		lsPromptOptions.add(optAreaExterna);
		lsPromptOptions.add(optEstacionamento);
		lsPromptOptions.add(optTerreno);
		lsPromptOptions.add(optDrenagem);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(null, "Insert point: ");
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);
		
		result = new InputParamVO();
		result.initKeyAreaTable(oKeyword, ptI3d);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//LIST TEXTOS/GEOMPOINT3D
			//
			PromptOptionVO oKeyword = oParam.getKeyword();
			
			GeomPoint3d ptIns = oParam.getPt0();
			
			//CADAREATABLE
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_TEXTOS);
			if(oLayer == null) return;
			
			CadAreaTable oAreaTable = CadAreaTable.create(
				currBlockDef,
				oLayer,
				ptIns, 
				oKeyword.getOptionId());
			currBlockDef.addEntity(oAreaTable);

			//oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
