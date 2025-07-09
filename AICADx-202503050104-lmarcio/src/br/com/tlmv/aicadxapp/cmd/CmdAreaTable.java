/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdAreaTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
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
import br.com.tlmv.aicadxapp.cad.CadAreaTable;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;

public class CmdAreaTable implements CmdBase, Runnable
{
//Private
	private AppMain app = null;
	private MainFrame frm = null;
	private AppCadMain cad = null;
	private CadDocumentDef doc = null;

	private Thread thread = null;
	private boolean bRunning = false;

	/* PromptOption
	*/
	private PromptOptionVO optAmbiente = new PromptOptionVO(AppDefs.AREATYPE_ROOM, "Ambiente", "A", false);
	private PromptOptionVO optApartamento = new PromptOptionVO(AppDefs.AREATYPE_APARTMENT, "aPartamento", "P", true);
	private PromptOptionVO optVaranda = new PromptOptionVO(AppDefs.AREATYPE_BALCONY, "Varanda", "V", false);
	private PromptOptionVO optAreaComum = new PromptOptionVO(AppDefs.AREATYPE_BUILDINGCOMMOM, "area Comum", "C", false);
	private PromptOptionVO optAreaInterna = new PromptOptionVO(AppDefs.AREATYPE_BUILDINGINTERNAL, "area Interna", "I", false);
	private PromptOptionVO optAreaExterna = new PromptOptionVO(AppDefs.AREATYPE_BUILDINGEXTERNAL, "area Externa", "E", false);
	private PromptOptionVO optEstacionamento = new PromptOptionVO(AppDefs.AREATYPE_PARKING, "eStacionamento", "S", false);
	private PromptOptionVO optTerreno = new PromptOptionVO(AppDefs.AREATYPE_TERRAIN, "Terreno", "T", false);
	
	/* Methodes */
	
	private InputParamVO promptInputParam(MainFrame frm)
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
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(lsPromptOptions, "Tipo de area: ");
		if(oKeyword == null) return null;
		
		GeomPoint2d ptI2d = PromptUtil.getFirstPoint2d(null, "Insert point: ");
		if(ptI2d == null) return null;

		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);
		
		result = new InputParamVO();
		result.initKeyAreaTable(oKeyword, ptI3d);
		
		MainPanel panel = frm.getMainPanel();

		ICompView v = panel.getCurrView();
		v.repaintAll();
		
		return result;
	}
	
//Public
	
	@Override
	public int executeCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc) 
	{
		this.app = app;
		this.frm = frm;
		this.cad = cad;
		this.doc = doc;
		
		this.thread = new Thread(this);
		this.thread.start();
		
		return AppDefs.RSOK;
	}

	/* THREADS */
	
	@Override
	public void run() {
		this.bRunning = true;
		
		InputParamVO oParam = this.promptInputParam(frm);
		if(oParam == null) return;
		
		CadBlockDef currBlockDef = doc.getCurrBlockDef();
		
		//LIST TEXTOS/GEOMPOINT3D
		//
		PromptOptionVO oKeyword = oParam.getKeyword();
		String text = oParam.getText();
		
		GeomPoint3d ptIns = oParam.getPt0();
		
		//CADAREATABLE
		//
		LayerTable oTbl = doc.getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_A_TEXTOS);
		if(oLayer == null) return;
		
		CadAreaTable oAreaTable = CadAreaTable.create(
			oLayer,
			ptIns, 
			oKeyword.getOptionId());
		currBlockDef.addEntity(oAreaTable);

		//BLIPS
		//
		MainPanel panel = MainPanel.getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();

		this.bRunning = false;		
	}
	
	/* Getters/Setters */
	
	public AppMain getApp() {
		return app;
	}

	public MainFrame getFrm() {
		return frm;
	}

	public AppCadMain getCad() {
		return cad;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public boolean isbRunning() {
		return bRunning;
	}

}
