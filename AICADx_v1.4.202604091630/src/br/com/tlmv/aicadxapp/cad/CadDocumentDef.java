/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadDocumentDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
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

package br.com.tlmv.aicadxapp.cad;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.FilterTable;
import br.com.tlmv.aicadxapp.cad.tables.ImageTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.tables.ObjectDataTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.UndoTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLevelRecord;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.LevelVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoJanelaDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoParedeDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoPisoDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoPortaDef;

public class CadDocumentDef extends CadObject 
{
//Private
	ProjectRepoVO projectRepo;
	
	private boolean isNew;

	//FILTER_ENTITY_TABLE
	//
	private FilterTable filterTable = null;
	
	//OBJECT_DATA_TABLE
	//
	private ObjectDataTable objectDataTable = null;
	
	//SHAPE_TABLE
	//
	private ShapeTable shapeTable = null;

	//IMAGE_DEF_TABLE
	//
	private ImageTable imageTable = null;

	//VIEW_TABLE
	//
	private ViewTable viewTable = null;

	//UNDO_TABLE
	//
	private UndoTable undoTable = null;
	
	//LAYER_DEF_TABLE
	//
	private LayerTable layerTable = null;

	private CadLayerDef defaultLayerDef = null; 
	
	private CadLayerDef currLayerDef = null; 
	
	//LEVEL_DEF_TABLE
	//
	private LevelTable levelTable = null;

	private CadLevel defaultLevel = null; 
			
	private CadLevel currLevel = null; 
	
	//BLOCK_DEF_TABLE
	//
	private BlockTable blockTable = null;

	private CadBlockDef defaultBlockDef = null; 
			
	private CadBlockDef currBlockDef = null; 

	//PROJECT_DEF_REFERENCE
	//
	private CadProjectDef currProjectDef = null;
	
	//VERSION_CONTROL
	//
	private String currObjVer = AppDefs.NULL_STR;
	
	/* Methodes */

	private void initProject()
	{
		CadLayerDef oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_0_COORDSYS);
		if(oLayer == null)
			oLayer = this.defaultLayerDef;

		CadLevel oLevel = this.levelTable.getLevel(AppDefs.DEFAULT_LEVELNAME);
			oLevel = this.defaultLevel;
			
		GeomPoint2d ptOrigem2d = new GeomPoint2d(0.0, 0.0);

		GeomVector2d xDir2d = new GeomVector2d(0.0, 0.0, 1.0, 0.0);
				
		this.currProjectDef = CadProjectDef.create(
			this.currBlockDef,							// current block definition
			oLayer,										// layer definition
			oLevel,										// level definition
			//
			AppDefs.DEF_DEFAULT_PROJECT_ORIGEM,			// origem 
			AppDefs.DEF_DEFAULT_PROJECT_XDIR,			// eixo-x
			//
			AppDefs.NULL_STR,							// codigo projeto
			AppDefs.NULL_STR,							// titulo projeto
			AppDefs.NULL_STR,							// descricao projeto
			//
			AppDefs.NULL_STR,							// logradouro
			AppDefs.NULL_STR,							// numero
			AppDefs.NULL_STR,							// complemento
			AppDefs.NULL_STR,							// bairro
			AppDefs.NULL_STR,							// municipio
			AppDefs.NULL_STR,							// estado
			AppDefs.NULL_STR,							// cep
			//
			AppDefs.NULL_STR,							// art
			AppDefs.NULL_STR,							// nome responsavel tecnico
			AppDefs.NULL_STR,							// registro responsavel tecnico
			AppDefs.NULL_STR,							// telefone responsavel tecnico
			AppDefs.NULL_STR, 							// email responsavel tecnico
			//
			AppDefs.DEF_DEFAULT_PROJECT_ESPGCODE,
			AppDefs.DEF_DEFAULT_PROJECT_CODIGOPLUVIOGRAFO,
			AppDefs.DEF_DEFAULT_PROJECT_PLUVIOGRAFO,
			AppDefs.DEF_DEFAULT_PROJECT_COEFMANNING,
			AppDefs.DEF_DEFAULT_PROJECT_PERIODO_RECORRENCIA,
			//
			AppDefs.DEF_DEFAULT_PROJECT_SCALE,
			AppDefs.DEF_DEFAULT_PROJECT_PAPEL_WIDTH,
			AppDefs.DEF_DEFAULT_PROJECT_PAPEL_HEIGHT );
		this.currBlockDef.addEntity(currProjectDef);
	}

	private void initLevels()
	{
		this.levelTable = new LevelTable(this);
		
		CadLayerDef oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_0_LEVELS);
		if(oLayer == null)
			oLayer = this.defaultLayerDef;

		for(LevelVO o : AppDefs.ARR_BASIC_LEVELS) {		
			CadLevel oLevel = this.levelTable.newLevel(
					this.currBlockDef, 
					oLayer, 
					o);
			this.currBlockDef.addEntity(oLevel);
			
			double zLevelPos = Math.abs( o.getZLevel() );
			if(zLevelPos < AppDefs.MATHPREC_MIN) {
				this.defaultLevel = oLevel;
				this.currLevel = oLevel;
			}
		}
	}
	
	private void initAcabamento()
	{
		//ACABAMENTOS_PAREDE
		//
		CadLayerDef oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_ALVE_ACAB);
		
		AppDefs.WALLFINISHDEF_BASIC = 
				new CadAcabamentoParedeDef(this, "Basica", "Alvenaria basica sem revestimento.", AppDefs.WALLFINISH_BASICA, AppDefs.WALLFINISH_0MM, AppDefs.WALLFINISHCOLOR1, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO = 
				new CadAcabamentoParedeDef(this, "Chapisco", "Alvenaria com chapisco.", AppDefs.WALLFINISH_CHAPISCO, AppDefs.WALLFINISH_5MM, AppDefs.WALLFINISHCOLOR1, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_PINTURA = 
				new CadAcabamentoParedeDef(this, "Chapisco+Emboco+Pintura", "Alvenaria com chapisco, emboco e pintura.", AppDefs.WALLFINISH_CHAPISCO_EMBOCO_PINTURA, AppDefs.WALLFINISH_10MM, AppDefs.WALLFINISHCOLOR2, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_PINTURA = 
				new CadAcabamentoParedeDef(this, "Chapisco+Emboco+Reboco+Pintura", "Alvenaria com chapisco, emboco, reboco e pintura.", AppDefs.WALLFINISH_CHAPISCO_EMBOCO_REBOCO_PINTURA, AppDefs.WALLFINISH_12MM, AppDefs.WALLFINISHCOLOR2, oLayer);
		AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_CERAMICA = 
				new CadAcabamentoParedeDef(this, "Chapisco+Emboco+Reboco+Ceramica", "Alvenaria com chapisco, emboco, reboco e ceramica.", AppDefs.WALLFINISH_CHAPISCO_EMBOCO_REBOCO_CERAMICA, AppDefs.WALLFINISH_12MM, AppDefs.WALLFINISHCOLOR3, oLayer);

		//ACABAMENTOS_PORTA
		//
		oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_PORTA_ACAB);
		
		AppDefs.DOORFINISHDEF_BASIC = 
				new CadAcabamentoPortaDef(this, "Basica", "Acabamento basico.", AppDefs.DOORFINISH_BASICA, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);
		AppDefs.DOORFINISHDEF_WOOD = 
				new CadAcabamentoPortaDef(this, "Madeira", "Acabamento em madeira.", AppDefs.DOORFINISH_MADEIRA, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);
		AppDefs.DOORFINISHDEF_IRON = 
				new CadAcabamentoPortaDef(this, "Ferro", "Acabamento em ferro.", AppDefs.DOORFINISH_FERRO, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);
		AppDefs.DOORFINISHDEF_GLASS = 
				new CadAcabamentoPortaDef(this, "Vidro", "Acabamento em vidro.", AppDefs.DOORFINISH_VIDRO, AppDefs.DOORFINISH_BATENTE_WEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WIDTH_30MM, AppDefs.DOORFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.DOORFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.DOORFINISHCOLOR3, AppDefs.DOOROPPENINGCOLOR1, oLayer);

		//ACABAMENTOS_JANELA
		//
		oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_JANELA_ACAB);
		
		AppDefs.WINDOWFINISHDEF_BASIC = 
				new CadAcabamentoJanelaDef(this, "Basica", "Acabamento basico.", AppDefs.WINDOWFINISH_BASICA, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		AppDefs.WINDOWFINISHDEF_WOOD = 
				new CadAcabamentoJanelaDef(this, "Madeira", "Acabamento em madeira.", AppDefs.WINDOWFINISH_MADEIRA, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		AppDefs.WINDOWFINISHDEF_IRON = 
				new CadAcabamentoJanelaDef(this, "Ferro", "Acabamento em ferro.", AppDefs.WINDOWFINISH_FERRO, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		AppDefs.WINDOWFINISHDEF_GLASS = 
				new CadAcabamentoJanelaDef(this, "Vidro", "Acabamento em vidro.", AppDefs.WINDOWFINISH_VIDRO, AppDefs.WINDOWFINISH_BATENTE_WEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WIDTH_30MM, AppDefs.WINDOWFINISH_GUARNICAO_HEIGHT_30MM, AppDefs.WINDOWFINISH_GUARNICAO_WEIGHT_10MM, AppDefs.WINDOWFINISHCOLOR3, AppDefs.WINDOWOPPENINGCOLOR1, oLayer);
		
		//ACABAMENTOS_PISO
		//
		oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_A_PISO_ACAB);
		
		AppDefs.FLOORFINISHDEF_BASIC = 
				new CadAcabamentoPisoDef(this, "Basica", "Piso basico sem revestimento.", AppDefs.FLOORFINISH_BASICA, AppDefs.FLOORFINISH_0MM, AppDefs.FLOORFINISHCOLOR1, oLayer);
		AppDefs.FLOORFINISHDEF_MADEIRA = 
				new CadAcabamentoPisoDef(this, "Madeira", "Piso em madeira.", AppDefs.FLOORFINISH_MADEIRA, AppDefs.FLOORFINISH_30MM, AppDefs.FLOORFINISHCOLOR1, oLayer);
		AppDefs.FLOORFINISHDEF_TACO = 
				new CadAcabamentoPisoDef(this, "Taco", "Piso de taco.", AppDefs.FLOORFINISH_TACO, AppDefs.FLOORFINISH_30MM, AppDefs.FLOORFINISHCOLOR2, oLayer);
		AppDefs.FLOORFINISHDEF_CERAMICA = 
				new CadAcabamentoPisoDef(this, "Ceramica", "Piso de ceramica.", AppDefs.FLOORFINISH_CERAMICA, AppDefs.FLOORFINISH_10MM, AppDefs.FLOORFINISHCOLOR2, oLayer);
		AppDefs.FLOORFINISHDEF_CIMENTO = 
				new CadAcabamentoPisoDef(this, "Cimento", "Piso de cimento.", AppDefs.FLOORFINISH_CIMENTO, AppDefs.FLOORFINISH_0MM, AppDefs.FLOORFINISHCOLOR3, oLayer);
		AppDefs.FLOORFINISHDEF_ASFALTO = 
				new CadAcabamentoPisoDef(this, "Asfalto", "Piso de asfalto.", AppDefs.FLOORFINISH_ASFALTO, AppDefs.FLOORFINISH_10MM, AppDefs.FLOORFINISHCOLOR3, oLayer);		
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

				CadLayerDef oLayer = new CadLayerDef(this);
				oLayer.fromStrData(str);
				
				this.layerTable.newLayerDef(oLayer);
			}
		}		
	}
	
	private void loadShapeTable()
	{
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		String shapeFiles = ctx.getShapeFiles();
		
		File f = new File(shapeFiles);
		if( f.exists() ) {
			ArrayList<String> lsStr = FileUtil.readDataAsList(shapeFiles);
			int pos = 0;
			for(String str : lsStr) {
				pos = pos + 1;				
				if(pos <= 2) continue;

				Shape shape = Shape.createFrom(this.getDocument(), str);
				if(shape != null)
					this.shapeTable.newShape(shape);
			}
		}		
	}
	
//Public

	public CadDocumentDef() 
	{
		super(AppDefs.OBJTYPE_DOCUMENT_DEF, null, null);
	}

	public static CadDocumentDef newDocument() 
	{
		String strUuid = UuidUtil.generateUUID();
		
		String docName = FileUtil.generateSchemaName(strUuid);
		//String docFileName = FileUtil.generateSchemaFileName(strUuid);
		
		CadDocumentDef o = new CadDocumentDef(); 
		o.init(docName, true);
		return o;
	}
    
	/* Methodes */

	public void init(String name, boolean isNew) 
	{
		//this.name = name;
		
		//this.fileName = fileName;
		
		this.projectRepo = new ProjectRepoVO(name);
		
		this.isNew = isNew;

		this.initUndoTable();
		this.initObjectDataTable();
		this.initFilterTable();
		this.initShapeTable();
		this.initLayerTable();
		this.initImageTable();
		this.initBlockTable();
		this.initAcabamento();
		this.initViewTable();
		this.initLevels();
		this.initProject();
		//
		if( AppDefs.ENABLE_UNDO_REDO ) {
			this.undoTable.stopMark();
		}
	}

	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		/* nothing todo! */
	}
		
	public void initUndoTable()
	{
		if( !AppDefs.ENABLE_UNDO_REDO ) return;
		
		//UNDO_TABLE
		//
		this.undoTable = new UndoTable(this);
	}

	public void initObjectDataTable()
	{
		//OBJECT_DATA TABLE
		//
		this.objectDataTable = new ObjectDataTable(this);
	}

	public void initFilterTable()
	{
		//FILTER_ENTITY TABLE
		//
		this.filterTable = new FilterTable(this);
	}
	
	public void initShapeTable()
	{
		//SHAPE TABLE
		//
		this.shapeTable = new ShapeTable(this);
		
		this.loadShapeTable();
	}
	
	public void initLayerTable()
	{
		//LAYER_DEF TABLE
		//
		this.layerTable = new LayerTable(this);
		
		this.defaultLayerDef = this.layerTable.newLayerDef(
			this,
			AppDefs.LAYER_0, 
			AppDefs.LAYER_0, 
			AppDefs.COLORINDEX_BLACK, 
			AppDefs.LTYPEINDEX_SOLID,
			AppDefs.LAYDEF_LINEWEIGHT_DEFAULT,
			AppDefs.LAYDEF_MINDIST_DEFAULT,
			AppDefs.LAYDEF_CATEGORIAID_DEFAULT,
			AppDefs.LAYDEF_DESCRICAO_CATEGORIA_DEFAULT);
		
		this.currLayerDef = this.defaultLayerDef;		
		
		this.loadLayerTable();
	}
	
	public void initBlockTable()
	{
		//BLOCK_DEF TABLE
		//
		this.blockTable = new BlockTable(this);
		
		this.defaultBlockDef = this.blockTable.newBlockDef(AppDefs.OPT_BLOCKDEF_MODELSPACE, AppDefs.BLKTABLE_MODELSPACE);
		
		this.currBlockDef = this.defaultBlockDef;
	}
	
	public void initImageTable()
	{
		//IMAGE_DEF TABLE
		//
		this.imageTable = new ImageTable(this);
	}
	
	public void initViewTable()
	{
		//VIEW TABLE
		//
		this.viewTable = new ViewTable(this);
	}
	
	// UPDATEALL CADLEVELS
	//
	public void removeCadLevels(CadBlockDef oBlkDef, LevelTable oLevelTbl, ArrayList<LevelVO> lsNewListaNiveis) {
		ArrayList<CadLevel> lsLevels = oLevelTbl.getAllLevel();
		for(CadLevel oLevel : lsLevels) {
			String strLevelName = oLevel.getLevelLocalName();			
			LevelVO oItemData = (LevelVO)ListUtil.findItemDataById(strLevelName, lsNewListaNiveis);
			if(oItemData == null) {
				CadEntity[] lsLevelEnt = oBlkDef.findAllEntityByLevelName(AppDefs.OBJTYPE_ALL, strLevelName, true);
				int szLsLevelEnt = lsLevelEnt.length;
				
				if(szLsLevelEnt == 0) {
					oLevel.setDeleted(true);
					oLevelTbl.removeLevel(strLevelName);
					
					String msg = String.format("Nivel %s removido. ", strLevelName);
					PromptUtil.prompt(msg);
				}
				else {
					String errmsg = String.format("ERR: Existem %s entidades no nivel %s. ", szLsLevelEnt, strLevelName);
					PromptUtil.prompt(errmsg);
				}
			}
		}		
	}

	public void insertUpdateCadLevels(CadBlockDef oBlkDef, LevelTable oLevelTbl, LayerTable oLayerTbl, CadLayerDef oLayer, ArrayList<LevelVO> lsNewListaNiveis)
	{
		for(LevelVO oItemData : lsNewListaNiveis) {
			int objectId = oItemData.getObjectId();
			String strLevelName = oItemData.getItemDataId();
			String strLevelText = oItemData.getDescricao();
			GeomPoint3d ptI = oItemData.getPtI();
			GeomPoint3d ptF = oItemData.getPtF();
			double zLevel = oItemData.getDblVal();
			
			CadLevel oLevel = oLevelTbl.getLevel(strLevelName);
			if(oLevel != null) {
				oLevel.setLevelLocalText(strLevelText);
				oLevel.setZLevel(zLevel);

				String msg = String.format("Nivel %s atualizado. ", strLevelName);
				PromptUtil.prompt(msg);
			}
			else {
				oLevel = oLevelTbl.newLevel(
					oBlkDef, 
					oLayer, 
					strLevelName,
					strLevelText,
					ptI.getX(), 
					ptI.getY(), 
					ptF.getX(), 
					ptF.getY(), 
					zLevel);

				if(objectId != AppDefs.NULL_INT)
					oLevel.setObjectId(objectId);
					
				oBlkDef.addEntity(oLevel);

				String msg = String.format("Nivel %s atualizado. ", strLevelName);
				PromptUtil.prompt(msg);
			}
		}
	}
		
	public void updateAllCadLevels(ArrayList<LevelVO> lsNewListaNiveis)
	{
		CadBlockDef oBlkDef = this.getCurrBlockDef();

		LevelTable oLevelTbl = this.getLevelTable();

		LayerTable oLayerTbl = this.getLayerTable();

		CadLayerDef oLayer = oLayerTbl.getLayerDefByReference(AppDefs.LAYER_0_LEVELS);
		if(oLayer == null)
			oLayer = this.getDefaultLayerDef();
		
		this.removeCadLevels(oBlkDef, oLevelTbl, lsNewListaNiveis);
		this.insertUpdateCadLevels(oBlkDef, oLevelTbl, oLayerTbl, oLayer, lsNewListaNiveis);
	}
	
	public ArrayList<LevelVO> loadAllCadLevels(ArrayList<BaseObjectRecord> lsObj) 
	{
		ArrayList<LevelVO> lsNewListaNiveis = new ArrayList<LevelVO>();

		for(BaseObjectRecord rec : lsObj) {
			CadLevelRecord oRec = (CadLevelRecord)rec; 
			
			LevelVO oLevel = new LevelVO(
				oRec.getObjectId(),
				oRec.getLevelLocalName(),
				oRec.getLevelLocalText(),
				oRec.getPtIX(), 
				oRec.getPtIY(), 
				oRec.getPtFX(), 
				oRec.getPtFY(), 
				oRec.getZLevel());
			lsNewListaNiveis.add(oLevel);
		}
		return lsNewListaNiveis;
	}
		
	// UPDATE CADPROJECTDEF
	//
	public void updateCurrProjectDef(CadProjectDef o)
	{
		this.currProjectDef.setCodigoProjeto(o.getCodigoProjeto());
		this.currProjectDef.setTituloProjeto(o.getTituloProjeto());
		this.currProjectDef.setDescricaoProjeto(o.getDescricaoProjeto());
		//
		this.currProjectDef.setLogradouro(o.getLogradouro());
		this.currProjectDef.setNumero(o.getNumero());
		this.currProjectDef.setComplemento(o.getComplemento());
		this.currProjectDef.setBairro(o.getBairro());
		this.currProjectDef.setMunicipio(o.getMunicipio());
		this.currProjectDef.setEstado(o.getEstado());
		this.currProjectDef.setCep(o.getCep());
		//
		this.currProjectDef.setArt(o.getArt());
		//
		this.currProjectDef.setNomeResponsavelTecnico(o.getNomeResponsavelTecnico());
		this.currProjectDef.setRegistroResponsavelTecnico(o.getRegistroResponsavelTecnico());
		this.currProjectDef.setTelefoneResponsavelTecnico(o.getTelefoneResponsavelTecnico());
		this.currProjectDef.setEmailResponsavelTecnico(o.getEmailResponsavelTecnico());
		//
		this.currProjectDef.setPluviografo(o.getPluviografo());					// local medicao volume chuva
		this.currProjectDef.setCoefManning(o.getCoefManning());
		this.currProjectDef.setPeriodoRecorrencia(o.getPeriodoRecorrencia());
		//
		this.currProjectDef.setEscala(o.getEscala());
		this.currProjectDef.setPapelLargura(o.getPapelLargura());
		this.currProjectDef.setPapelAltura(o.getPapelAltura());	
		//
		this.currProjectDef.setEspgCode(o.getEspgCode());
		//
		this.currProjectDef.setPtOrigem(o.getPtOrigem());
		this.currProjectDef.setXDir(o.getXDir());
	}
	
	public void loadAll(ArrayList<BaseObjectRecord> lsObj) 
	{
		CadBlockDef blkDef = this.currBlockDef;
		int szLsObj = lsObj.size();
		for(int i = 0; i < szLsObj; i++) {
			BaseEntityRecord oRec = (BaseEntityRecord)lsObj.get(i);
			CadObject obj = (CadObject)oRec.toCadObject(blkDef);
			if(obj != null) {
				if(obj.getObjType() >= AppDefs.OBJTYPE_ENTITIES) {
					CadEntity oEnt = (CadEntity)obj; 
					blkDef.addEntity(oEnt);				
				}
			}
		}
	}
	
	@Override
	public boolean loadAllPts(ArrayList<BasePointRecord> lsPts)
	{
		return false;

//		CadBlockDef blkDef = this.currBlockDef;
//		int szLsPts = lsPts.size();
//		for(int i = 0; i < szLsPts; i++) {
//			BasePointRecord oRec = (BasePointRecord)lsPts.get(i);
//			
//			int objectId = StringUtil.safeInt( oRec.getCadRefEntityId() );
//			CadEntity oEnt = blkDef.getEntity( objectId );
//			
//			GeomPoint3d oPt = (GeomPoint3d)oRec.toGeomPoint3d();
//			if(oPt != null) {
//				
//			}
//		}
	}

	/* DEBUG */
    
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		String name = this.projectRepo.getName();
		
		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();		

		lsProperty.add( new ItemDataVO("ObjectId", Integer.toString(this.getObjectId())) );
		lsProperty.add( new ItemDataVO("Name", name) );
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		return null;
	}

	@Override
	public void debug(int debugLevel) {
		//TODO:
	}
	
	/* LOAD/SAVE */

	public boolean save_blockTable(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		ArrayList<CadBlockDef> lsBlkDef = this.blockTable.getAllBlockDef();
		for(CadBlockDef oBlkDef : lsBlkDef) {
			String nameStr = oBlkDef.getName();
			if( AppDefs.BLKTABLE_MODELSPACE.equalsIgnoreCase(nameStr) ) continue;			

			boolean bResult = oBlkDef.save(this.currObjVer, dao, schemaName, doc);
			if( !bResult ) {
				String str = String.format("ERR: Nao foi possivel gravar o objeto [%s] do tipo [%s]", 
					oBlkDef.getObjectId(), 
					oBlkDef.getObjTypeStr() );
				PromptUtil.prompt(str);
			}
		}
		return true;
	}

	public boolean save_imageTable(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		ArrayList<CadImageDef> lsImgDef = this.imageTable.getAllImageDef();
		for(CadImageDef oImgDef : lsImgDef) {
			if( oImgDef.isDeleted() ) continue;
			
			boolean bResult = oImgDef.save(this.currObjVer, dao, schemaName, doc);
			if( !bResult ) {
				String str = String.format("ERR: Nao foi possivel gravar o objeto [%s] do tipo [%s]", 
					oImgDef.getObjectId(), 
					oImgDef.getObjTypeStr() );
				PromptUtil.prompt(str);
			}
		}
		return true;
	}

	public boolean save_modelSpace(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BlockTable blockTable = doc.getBlockTable();
		
		ArrayList<CadBlockDef> lsBlkDef = blockTable.getAllBlockDef();
		for(CadBlockDef oBlkDef : lsBlkDef) {
			String blkName = oBlkDef.getName();
			if( !AppDefs.BLKTABLE_MODELSPACE.equalsIgnoreCase(blkName) ) continue;
			
			CadEntity[] arrEnt = oBlkDef.findAllEntity();
			for(CadEntity oEnt : arrEnt) {
				if( oEnt.isDeleted() ) continue;
			
				if(AppDefs.DEBUG_LEVEL == AppDefs.DEBUG_LEVEL32) {
					String str = String.format("Gravando objeto [%s] do tipo [%s]", oEnt.getObjectId(), oEnt.getObjTypeStr() );
					System.out.println(str);
				}
				
				boolean bResult = oEnt.save(this.currObjVer, dao, schemaName, doc);
				if( !bResult ) {
					String str = String.format("ERR: Nao foi possivel gravar o objeto [%s] do tipo [%s]", 
						oEnt.getObjectId(), 
						oEnt.getObjTypeStr() );
					PromptUtil.prompt(str);
					return false;			
				}
			}
		}
		return true;
	}

	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.currObjVer = UuidUtil.generateVersionNumber();
		this.setObjVer(this.currObjVer);
		
		ProjectRepoVO projectRepo = doc.getProjectRepo();
		if(projectRepo == null) return false;

		bResult = save_blockTable(dao, schemaName, doc);
		if( !bResult ) return false;

		bResult = save_imageTable(dao, schemaName, doc);
		if( !bResult ) return false;

		bResult = save_modelSpace(dao, schemaName, doc);
		return bResult;
	}
	
	/* Getters/Setters */

	public CadProjectDef getCurrProjectDef() {
		return currProjectDef;
	}

	public void setCurrProjectDef(CadProjectDef currProjectDef) {
		this.currProjectDef = currProjectDef;
	}
	
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

	public boolean isNew() {
		return isNew;
	}

	public ShapeTable getShapeTable() {
		return shapeTable;
	}

	public ImageTable getImageTable() {
		return imageTable;
	}

	public ViewTable getViewTable() {
		return viewTable;
	}

	public void setViewTable(ViewTable viewTable) {
		this.viewTable = viewTable;
	}

	public ObjectDataTable getObjectDataTable() {
		return objectDataTable;
	}

	public void setObjectDataTable(ObjectDataTable objectDataTable) {
		this.objectDataTable = objectDataTable;
	}

	public LevelTable getLevelTable() {
		return levelTable;
	}

	public CadLevel getDefaultLevel() {
		return defaultLevel;
	}

	public void setDefaultLevel(CadLevel defaultLevel) {
		this.defaultLevel = defaultLevel;
	}

	public CadLevel getCurrLevel() {
		return currLevel;
	}

	public void setCurrLevel(CadLevel currLevel) {
		this.currLevel = currLevel;
	}

	public ProjectRepoVO getProjectRepo() {
		return projectRepo;
	}

	public void setProjectRepo(ProjectRepoVO projectRepo) {
		this.projectRepo = projectRepo;
	}

	public void setDefaultLayerDef(CadLayerDef defaultLayerDef) {
		this.defaultLayerDef = defaultLayerDef;
	}

	public void setDefaultBlockDef(CadBlockDef defaultBlockDef) {
		this.defaultBlockDef = defaultBlockDef;
	}

	public UndoTable getUndoTable() {
		return undoTable;
	}

	public void setUndoTable(UndoTable undoTable) {
		this.undoTable = undoTable;
	}
	
}
