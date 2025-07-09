/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadDocumentDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;

public class CadDocumentDef extends CadObject 
{
//Private
	private String name;
	
	private String fileName;
	
	private boolean isNew;
	
	//LAYER_DEF_TABLE
	//
	private LayerTable layerTable = null;

	private CadLayerDef defaultLayerDef = null; 
	
	private CadLayerDef currLayerDef = null; 

	//BLOCK_DEF_TABLE
	//
	private BlockTable blockTable = null;

	private CadBlockDef defaultBlockDef = null; 
			
	private CadBlockDef currBlockDef = null; 
	
	/* Methodes */
	
	private void initAcabamento()
	{
		//ACABAMENTOS_PAREDE
		//
		CadLayerDef oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_ALVE_ACAB);
		
		AppDefs.WALLFINISHDEF_BASIC = 
				new CadAcabamentoParedeDef("Basica", "Alvenaria basica sem revestimento.", AppDefs.WALLFINISH_BASICA, AppDefs.WALLFINISH_0MM, AppDefs.WALLFINISHCOLOR1, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO = 
				new CadAcabamentoParedeDef("Chapisco", "Alvenaria com chapisco.", AppDefs.WALLFINISH_CHAPISCO, AppDefs.WALLFINISH_5MM, AppDefs.WALLFINISHCOLOR1, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_PINTURA = 
				new CadAcabamentoParedeDef("Chapisco+Emboco+Pintura", "Alvenaria com chapisco, emboco e pintura.", AppDefs.WALLFINISH_CHAPISCO_EMBOCO_PINTURA, AppDefs.WALLFINISH_10MM, AppDefs.WALLFINISHCOLOR2, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_PINTURA = 
				new CadAcabamentoParedeDef("Chapisco+Emboco+Reboco+Pintura", "Alvenaria com chapisco, emboco, reboco e pintura.", AppDefs.WALLFINISH_CHAPISCO_EMBOCO_REBOCO_PINTURA, AppDefs.WALLFINISH_12MM, AppDefs.WALLFINISHCOLOR2, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_CERAMICA = 
				new CadAcabamentoParedeDef("Chapisco+Emboco+Reboco+Ceramica", "Alvenaria com chapisco, emboco, reboco e ceramica.", AppDefs.WALLFINISH_CHAPISCO_EMBOCO_REBOCO_CERAMICA, AppDefs.WALLFINISH_12MM, AppDefs.WALLFINISHCOLOR3, oLayer);

		//ACABAMENTOS_PORTA
		//
		oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_PORTA_ACAB);
		
		AppDefs.DOORFINISHDEF_BASIC = 
				new CadAcabamentoPortaDef("Basica", "Acabamento basico.", AppDefs.DOORFINISH_BASICA, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);
		AppDefs.DOORFINISHDEF_WOOD = 
				new CadAcabamentoPortaDef("Madeira", "Acabamento em madeira.", AppDefs.DOORFINISH_MADEIRA, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);
		AppDefs.DOORFINISHDEF_IRON = 
				new CadAcabamentoPortaDef("Ferro", "Acabamento em ferro.", AppDefs.DOORFINISH_FERRO, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);
		AppDefs.DOORFINISHDEF_GLASS = 
				new CadAcabamentoPortaDef("Vidro", "Acabamento em vidro.", AppDefs.DOORFINISH_VIDRO, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);

		//ACABAMENTOS_JANELA
		//
		oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_JANELA_ACAB);
		
		AppDefs.WINDOWFINISHDEF_BASIC = 
				new CadAcabamentoJanelaDef("Basica", "Acabamento basico.", AppDefs.WINDOWFINISH_BASICA, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		AppDefs.WINDOWFINISHDEF_WOOD = 
				new CadAcabamentoJanelaDef("Madeira", "Acabamento em madeira.", AppDefs.WINDOWFINISH_MADEIRA, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		AppDefs.WINDOWFINISHDEF_IRON = 
				new CadAcabamentoJanelaDef("Ferro", "Acabamento em ferro.", AppDefs.WINDOWFINISH_FERRO, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		AppDefs.WINDOWFINISHDEF_GLASS = 
				new CadAcabamentoJanelaDef("Vidro", "Acabamento em vidro.", AppDefs.WINDOWFINISH_VIDRO, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		
		//ACABAMENTOS_PISO
		//
		oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_PISO_ACAB);
		
		AppDefs.FLOORFINISHDEF_BASIC = 
				new CadAcabamentoPisoDef("Basica", "Piso basico sem revestimento.", AppDefs.FLOORFINISH_BASICA, AppDefs.FLOORFINISH_0MM, AppDefs.FLOORFINISHCOLOR1, oLayer);
		AppDefs.FLOORFINISHDEF_MADEIRA = 
				new CadAcabamentoPisoDef("Madeira", "Piso em madeira.", AppDefs.FLOORFINISH_MADEIRA, AppDefs.FLOORFINISH_30MM, AppDefs.FLOORFINISHCOLOR1, oLayer);
		AppDefs.FLOORFINISHDEF_TACO = 
				new CadAcabamentoPisoDef("Taco", "Piso de taco.", AppDefs.FLOORFINISH_TACO, AppDefs.FLOORFINISH_30MM, AppDefs.FLOORFINISHCOLOR2, oLayer);
		AppDefs.FLOORFINISHDEF_CERAMICA = 
				new CadAcabamentoPisoDef("Ceramica", "Piso de ceramica.", AppDefs.FLOORFINISH_CERAMICA, AppDefs.FLOORFINISH_10MM, AppDefs.FLOORFINISHCOLOR2, oLayer);
		AppDefs.FLOORFINISHDEF_CIMENTO = 
				new CadAcabamentoPisoDef("Cimento", "Piso de cimento.", AppDefs.FLOORFINISH_CIMENTO, AppDefs.FLOORFINISH_0MM, AppDefs.FLOORFINISHCOLOR3, oLayer);
		AppDefs.FLOORFINISHDEF_ASFALTO = 
				new CadAcabamentoPisoDef("Asfalto", "Piso de asfalto.", AppDefs.FLOORFINISH_ASFALTO, AppDefs.FLOORFINISH_10MM, AppDefs.FLOORFINISHCOLOR3, oLayer);		
	}
	
	private void loadLayerTable()
	{
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		String layersFile = ctx.getLayersFile();
		
		File f = new File(layersFile);
		if( f.exists() ) {
			ArrayList<String> lsStr = FileUtil.readDataAsList(layersFile);
			int pos = 0;
			for(String str : lsStr) {
				pos = pos + 1;				
				if(pos <= 2) continue;

				CadLayerDef oLayer = new CadLayerDef();
				oLayer.fromStrData(str);
				
				this.layerTable.newLayerDef(oLayer);
			}
		}		
	}
	
//Public

	public CadDocumentDef() 
	{
		super(AppDefs.OBJTYPE_DOCUMENT_DEF);
	}

	public static CadDocumentDef newDocument() 
	{
		String strUuid = UuidUtil.generateUUID();
		
		String docName = String.format(AppDefs.DEFAULT_DOCUMENT_NAME, strUuid);
		String docFileName = String.format(AppDefs.DEFAULT_DOCUMENT_FILENAME, strUuid);
		
		CadDocumentDef o = new CadDocumentDef(); 
		o.init(docName, docFileName, true);
		return o;
	}

	public static CadDocumentDef openDocument(
		String name,
		String fileName) 
	{
		CadDocumentDef o = new CadDocumentDef(); 
		o.init(name, fileName, false);
		o.load();
		return o;
	}
    
	/* Methodes */

	public void init(String name, String fileName, boolean isNew) 
	{
		this.name = name;
		
		this.fileName = fileName;
		
		this.isNew = isNew;

		this.initLayerTable();
		this.initBlockTable();
		this.initAcabamento();
	}

	public void initLayerTable()
	{
		//LAYER_DEF TABLE
		//
		this.layerTable = new LayerTable();
		
		this.defaultLayerDef = this.layerTable.newLayerDef(AppDefs.LAYER_0, AppDefs.LAYER_0, AppDefs.COLORINDEX_BLACK, AppDefs.LTYPEINDEX_SOLID);
		
		this.currLayerDef = this.defaultLayerDef;		
		
		this.loadLayerTable();
	}
	
	public void initBlockTable()
	{
		//BLOCK_DEF TABLE
		//
		this.blockTable = new BlockTable();
		
		this.defaultBlockDef = this.blockTable.newBlockDef(AppDefs.BLKTABLE_MODELSPACE);
		
		this.currBlockDef = this.defaultBlockDef;		
	}

	public void load() 
	{
		if( this.isNew ) return;
		
		//TODO:
	}

	public void save() 
	{
		this.saveAs(this.name, this.fileName);
	}

	public void saveAs(String name, String fileName) 
	{
		//TODO:
		
		this.name = name;
		this.fileName = fileName;
		
		this.isNew = false;
	}
	
	/* Getters/Setters */
	
	public LayerTable getLayerTable() {
		return this.layerTable;
	}

	public BlockTable getBlockTable() {
		return this.blockTable;
	}

	public CadLayerDef getDefaultLayerDef() {
		return this.defaultLayerDef;
	}

	public CadBlockDef getDefaultBlockDef() {
		return this.defaultBlockDef;
	}

	public CadLayerDef getCurrLayerDef() {
		return currLayerDef;
	}

	public void setCurrLayerDef(CadLayerDef currLayerDef) {
		this.currLayerDef = currLayerDef;
	}

	public CadBlockDef getCurrBlockDef() {
		return currBlockDef;
	}

	public void setCurrBlockDef(CadBlockDef currBlockDef) {
		this.currBlockDef = currBlockDef;
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isNew() {
		return isNew;
	}
	
}
