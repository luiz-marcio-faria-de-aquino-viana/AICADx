/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.BlockTable;
import br.com.tlmv.aicadxapp.cad.tables.ImageTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ObjectDataTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.tables.ViewTable;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoJanelaDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoParedeDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoPisoDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoPortaDef;

public class CadDocumentDef extends CadObject 
{
//Private
	private String name;
	
	private String fileName;
	
	private boolean isNew;

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

	//PROJECT_DEF_REFERENCE
	//
	private CadProjectDef currProjectDef = null;
	
	/* Methodes */

	private void initProject()
	{
		CadLayerDef oLayer = this.layerTable.getLayerDefByReference(AppDefs.LAYER_0_COORDSYS);

		if(oLayer == null)
			oLayer = this.defaultLayerDef;
		
		this.currProjectDef = CadProjectDef.create(
			this.currBlockDef,
			oLayer,		// layer definition
			//
			"",			// codigo projeto
			"",			// titulo projeto
			"",			// descricao projeto
			//
			"",			// logradouro
			"",			// numero
			"",			// complemento
			"",			// bairro
			"",			// municipio
			"",			// estado
			"",			// cep
			//
			"",			// art
			"",			// nome responsavel tecnico
			"",			// registro responsavel tecnico
			"",			// telefone responsavel tecnico
			"", 		// email responsavel tecnico
			//
			AppDefs.DEF_DEFAULT_PROJECT_CODIGOPLUVIOGRAFO,
			AppDefs.DEF_DEFAULT_PROJECT_PLUVIOGRAFO,
			AppDefs.DEF_DEFAULT_PROJECT_COEFMANNING,
			AppDefs.DEF_DEFAULT_PROJECT_PERIODO_RECORRENCIA,
			//
			AppDefs.DEF_DEFAULT_PROJECT_SCALE,
			AppDefs.DEF_DEFAULT_PROJECT_PAPEL_WIDTH,
			AppDefs.DEF_DEFAULT_PROJECT_PAPEL_HEIGHT,
			//
			AppDefs.DEF_DEFAULT_PROJECT_ESPGCODE,
			AppDefs.DEF_DEFAULT_PROJECT_ORIGEM, 
			AppDefs.DEF_DEFAULT_PROJECT_XDIR);
		this.currBlockDef.addEntity(currProjectDef);
	}
	
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

				Shape shape = Shape.createFrom(str);
				if(shape != null)
					this.shapeTable.newShape(shape);
			}
		}		
	}
	
//Public

	public CadDocumentDef() 
	{
		super(AppDefs.OBJTYPE_DOCUMENT_DEF, null);
	}

	public static CadDocumentDef newDocument() 
	{
		String strUuid = UuidUtil.generateUUID();
		
		String docName = FileUtil.generateSchemaName(strUuid);
		String docFileName = FileUtil.generateSchemaFileName(strUuid);
		
		CadDocumentDef o = new CadDocumentDef(); 
		o.init(docName, docFileName, true);
		return o;
	}
    
	/* Methodes */

	public void init(String name, String fileName, boolean isNew) 
	{
		this.name = name;
		
		this.fileName = fileName;
		
		this.isNew = isNew;

		this.initObjectDataTable();
		this.initShapeTable();
		this.initLayerTable();
		this.initImageTable();
		this.initBlockTable();
		this.initProject();
		this.initAcabamento();
		this.initViewTable();
	}

	@Override
	public void reset() {
		/* nothing todo! */
	}

	public void initObjectDataTable()
	{
		//OBJECT_DATA TABLE
		//
		this.objectDataTable = new ObjectDataTable();
	}
	
	public void initShapeTable()
	{
		//SHAPE TABLE
		//
		this.shapeTable = new ShapeTable();
		
		this.loadShapeTable();
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
	
	public void initImageTable()
	{
		//IMAGE_DEF TABLE
		//
		this.imageTable = new ImageTable();
	}
	
	public void initViewTable()
	{
		//VIEW TABLE
		//
		this.viewTable = new ViewTable();
	}
	
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
	
	public void loadAllPts(ArrayList<BasePointRecord> lsPts)
	{
		CadBlockDef blkDef = this.currBlockDef;
		int szLsPts = lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			BasePointRecord oRec = (BasePointRecord)lsPts.get(i);
			int refEntityId = oRec.getCadRefEntityId();

			CadEntity oEnt = blkDef.getEntity(refEntityId);
			
			GeomPoint3d oPt = (GeomPoint3d)oRec.toGeomPoint3d();
			if(oPt != null) {
				
			}
		}
	}

	/* DEBUG */
    
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();		

		lsProperty.add( new ItemDataVO("ObjectId", Integer.toString(this.getObjectId())) );
		lsProperty.add( new ItemDataVO("Name", this.name) );
		
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
	
	@Override
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

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

	public String getName() {
		return name;
	}

	public String getFileName() {
		return fileName;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
