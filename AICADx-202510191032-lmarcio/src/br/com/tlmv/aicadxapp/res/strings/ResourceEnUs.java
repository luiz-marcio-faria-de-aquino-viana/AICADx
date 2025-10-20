/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ResourceEnUs.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.res.strings;

import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;

public class ResourceEnUs extends R
{
//Private Static

	/* DOCUMENTO_VIEW_DEFINITIONS (STRING)
	 */
	public static final String DOCVIEW_GRP_LEVELS_STR			= "Levels";
	public static final String DOCVIEW_GRP_PLANVIEWS_STR		= "Plan Views";
	public static final String DOCVIEW_GRP_SECTIONVIEWS_STR		= "Sections";
	public static final String DOCVIEW_GRP_ELEVATIONVIEWS_STR	= "Elevations";
	public static final String DOCVIEW_GRP_DETAILVIEWS_STR		= "Details";
	public static final String DOCVIEW_GRP_3DVIEWS_STR			= "3D";
	public static final String DOCVIEW_GRP_IMAGES_STR			= "Images";
	public static final String DOCVIEW_GRP_BLOCKS_STR			= "Blocks";
	public static final String DOCVIEW_GRP_SHAPES_STR			= "Symbols";
	public static final String DOCVIEW_GRP_LAYERS_STR			= "Layers";
	public static final String DOCVIEW_GRP_SHEETS_STR			= "Outputs";	

	/* DOCUMENTO_VIEW_DEFINITIONS (ARR_GROUP_ITEM_DATA)
	 */
	public static final GroupItemDataVO[] ARR_DOCVIEW_GROUPS = {
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_LEVELS_VAL), ResourceEnUs.DOCVIEW_GRP_LEVELS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL), ResourceEnUs.DOCVIEW_GRP_PLANVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_SECTIONVIEWS_VAL), ResourceEnUs.DOCVIEW_GRP_SECTIONVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_ELEVATIONVIEWS_VAL), ResourceEnUs.DOCVIEW_GRP_ELEVATIONVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL), ResourceEnUs.DOCVIEW_GRP_DETAILVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_3DVIEWS_VAL), ResourceEnUs.DOCVIEW_GRP_3DVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_IMAGES_VAL), ResourceEnUs.DOCVIEW_GRP_IMAGES_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_BLOCKS_VAL), ResourceEnUs.DOCVIEW_GRP_BLOCKS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_SHAPES_VAL), ResourceEnUs.DOCVIEW_GRP_SHAPES_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_LAYERS_VAL), ResourceEnUs.DOCVIEW_GRP_LAYERS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_SHEETS_VAL), ResourceEnUs.DOCVIEW_GRP_SHEETS_STR)
	};
		
//Private

	private Hashtable<String,String> map;
	
//Public
	
	public ResourceEnUs()
	{
		this.initVars();
		this.init();
	}
	
	/* Methodes */
	
	public void initVars()
	{
		this.LS_MONTHS_FULLNAME = AppDefs.TBL_MONTHS_FULLNAME_ENUS;
		this.LS_MONTHS_ABREV = AppDefs.TBL_MONTHS_ABREV_ENUS;
		this.LS_TBLLAYEREXPLORER = AppDefs.TBL_LAYEREXPLORER_ENUS;
		this.LS_TBLPROPERTYEDITOR = AppDefs.TBL_PROPERTYEDITOR_ENUS;
		this.LS_TBLRESULTLIST = AppDefs.TBL_RESULTLIST_PTBR;
	}
	
	public void init()
	{
		map = new Hashtable<String,String>();
		
		map.put(R.TIT_MAINFRAME, AppDefs.APP_NAME + " - " + AppDefs.APP_VERSAO);
		
		//TAB
		//
		map.put(R.TAB_PLANVIEW, "Plan View");
		map.put(R.TAB_3DVIEW, "3D View");
		map.put(R.TAB_ANYVIEW, "Any View");

		//TITLE
		//
		map.put(R.TIT_LAYEREXPLORERFRAME, "Layer Manager");
		map.put(R.TIT_MESSAGEFRAME_INFORMACAO, "Notification");
		map.put(R.TIT_MESSAGEFRAME_ERRO, "Error");
		map.put(R.TIT_MESSAGEFRAME_ATENCAO, "Warning");
		map.put(R.TIT_SEARCHFRAME, "Search");
		map.put(R.TIT_OPENSAVEDATABASEFRAME, "Open and Save Data File");
		map.put(R.TIT_SETUPFRAME, "Project Settings");
		map.put(R.TIT_DIMENSIONAREDEDRENAGEMFRAME, "Sewage Water Dimensioning");
		map.put(R.TIT_GERARPLANILHACALCULODRENAGEMFRAME, "Sewage Water Worksheet");
		map.put(R.TIT_GERARPLANTASPERFISDRENAGEMFRAME, "Sewage Water Schema");		
		map.put(R.TIT_PROPRIEDADESCAIXAINSPECAOREDEDRENAGEMFRAME, "Sewage Water Properties");
		map.put(R.TIT_SPLASHSCREENFRAME, AppDefs.APP_NAME + " - " + AppDefs.APP_VERSAO);
		map.put(R.TIT_CONTROLEBACKLISTTRANSMARFRAME, "Backlist Control");
		
		//LABEL
		//
		map.put(R.LBL_DETAIL_LEVEL, "Detail level: ");
		map.put(R.LBL_SCALE, "Scale: ");
		map.put(R.LBL_COMMAND, "Command: ");
		map.put(R.LBL_LAYERLIST, "Layer list: ");
		map.put(R.LBL_DATA, "Date: ");
		map.put(R.LBL_ASSUNTO, "Subject: ");
		map.put(R.LBL_MENSAGEM, "Message: ");
		map.put(R.LBL_SELECTDATABASE, "Select database: ");
		map.put(R.LBL_ACTIVEDATABASE, "Active database: ");
		map.put(R.LBL_PROJECTINFORMATION, "1. Project Information");
		map.put(R.LBL_CODIGOPROJETO, "Code: ");
		map.put(R.LBL_NOMEPROJETO, "Project: ");
		map.put(R.LBL_DESCRICAOPROJETO, "Description: ");
		map.put(R.LBL_ENDERECOPROJETO, "2. Project Address");
		map.put(R.LBL_LOGRADOURO, "Street address 1: ");
		map.put(R.LBL_NUMERO, "Number: ");
		map.put(R.LBL_COMPLEMENTO, "Street address 2: ");
		map.put(R.LBL_BAIRRO, "Neighborhood: ");
		map.put(R.LBL_MUNICIPIO, "County: ");
		map.put(R.LBL_ESTADO, "State: ");
		map.put(R.LBL_CEP, "Zip Code: ");
		map.put(R.LBL_PROJECTREGISTER, "3. Project Register");
		map.put(R.LBL_PROJECTREGISTER_ART, "Project license: ");
		map.put(R.LBL_PROJECTREGISTER_NOMERESPTECNICO, "Technical responsable: ");
		map.put(R.LBL_PROJECTREGISTER_REGISTRORESPTECNICO, "Technical register: ");
		map.put(R.LBL_PROJECTREGISTER_TELEFONERESPTECNICO, "Technical phone: ");
		map.put(R.LBL_PROJECTREGISTER_EMAILRESPTECNICO, "Technical e-mail: ");
		map.put(R.LBL_PARAMETROSDRENAGEM, "4. Project Parameters (Drenage)");
		map.put(R.LBL_PARAMETROSDRENAGEM_PLUVIOGRAFO, "Pluviograph: ");
		map.put(R.LBL_PARAMETROSDRENAGEM_COEFMANNING, "Coef. Manning: ");
		map.put(R.LBL_PARAMETROSDRENAGEM_PERIODORECORRENCIA, "Recourrence period: ");
		map.put(R.LBL_PARAMETROSIMPRESSAO, "5. Printer Parameters");
		map.put(R.LBL_PARAMETROSIMPRESSAO_ESCALA, "Scale: ");
		map.put(R.LBL_PARAMETROSIMPRESSAO_LARGURAPAPEL, "Paper width: ");
		map.put(R.LBL_PARAMETROSIMPRESSAO_ALTURAPAPEL, "Paper height: ");
		map.put(R.LBL_PARAMETROSCOORDSYS, "6. Coordinate System Parameters");
		map.put(R.LBL_PARAMETROSCOORDSYS_ESPGCODE, "ESPG Code: ");
		map.put(R.LBL_PARAMETROSCOORDSYS_ORIGEM, "Origin: ");
		map.put(R.LBL_PARAMETROSCOORDSYS_DIRECAO_EIXO_X, "Direction of X axis: ");
		map.put(R.LBL_SEARCH_OBJECTTYPE, "Object type: ");
		map.put(R.LBL_SEARCH_SEARCHBY, "Search by: ");
		map.put(R.LBL_SEARCH_RESULTLIST, "Result list: ");
		
		//TEXT
		//
		map.put(R.TXT_VIEWS, "Views: ");

		//BUTTON
		//
		map.put(R.BTN_FECHAR, "Close");
		map.put(R.BTN_NOVO, "New");
		map.put(R.BTN_ABRIR, "Open");
		map.put(R.BTN_GRAVAR, "Save");
		map.put(R.BTN_GRAVAR_COMO, "Save as");
		map.put(R.BTN_APAGAR, "Drop");
		map.put(R.BTN_RENOMEAR, "Rename");
		map.put(R.BTN_COPIAR, "Copy");
		map.put(R.BTN_CANCELAR, "Cancel");
		map.put(R.BTN_OK, "Ok");
		map.put(R.BTN_ORIGEM, "Origin >");
		map.put(R.BTN_ZOOMTOITEM, "Zoom To >");
		map.put(R.BTN_ZOOMTOALL, "Zoom All >");
		map.put(R.BTN_SEARCH, "Search >");
		
		//ERROR
		//
		map.put(R.ERR_PROCESSING_FAILURE, "ERR: Processing failure.");
		map.put(R.ERR_COMANDO_INVALIDO_NAO_IMPLEMENTADO, "ERR: Invalid or not implemented command.");
		map.put(R.ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS, "ERR: Required form fields not informed.");
		map.put(R.ERR_CAMPOS_INVALIDOS, "ERR: Invalid fields."); 
		//
		map.put(R.ERR_VALOR_COEFMANNING_DEVE_SER_SUPERIOR, "The Manning Coeficient need to be greater than %s."); 
		map.put(R.ERR_PERIODO_RECORRENCIA_DEVE_SER_SUPERIOR, "The Recorrence Period need to be greater than or equal to 1."); 
		map.put(R.ERR_VALOR_ESCALA_DEVE_SER_SUPERIOR, "The scale need to be greater to 1:1."); 
		map.put(R.ERR_VALOR_LARGURA_PAPEL_DEVE_SER_SUPERIOR, "The paper width need to be greater to 1 mm."); 
		map.put(R.ERR_VALOR_ALTURA_PAPEL_DEVE_SER_SUPERIOR, "The paper height need to be greater to 1 mm."); 
		
		//ERROR: FIELDS
		//
		map.put(R.ERR_DATABASE, "Database");
		map.put(R.ERR_CODIGOPROJETO, "Codigo");			
		map.put(R.ERR_NOMEPROJETO, "Projeto");
		map.put(R.ERR_DESCRICAOPROJETO, "Descricao");
		map.put(R.ERR_LOGRADOURO, "Logradouro");			
		map.put(R.ERR_NUMERO, "Numero");
		map.put(R.ERR_COMPLEMENTO, "Complemento");			
		map.put(R.ERR_BAIRRO, "Bairro");			
		map.put(R.ERR_MUNICIPIO, "Municipio");			
		map.put(R.ERR_ESTADO, "Estado");			
		map.put(R.ERR_CEP, "CEP");			
		map.put(R.ERR_ART, "ART");			
		map.put(R.ERR_RESPTECNICO, "Resp. Tecnico");			
		map.put(R.ERR_REGISTRO, "Registro");			
		map.put(R.ERR_TELEFONE, "Telefone");			
		map.put(R.ERR_EMAIL, "E-mail");			
		map.put(R.ERR_PLUVIOGRAFO, "Pluviografo");			
		map.put(R.ERR_COEFMANNING, "Coef. Manning");			
		map.put(R.ERR_PERIODORECORRENCIA, "Periodo Recorrencia");			
		map.put(R.ERR_ESCALA, "Escala");			
		map.put(R.ERR_LARGURAPAPEL, "Largura Papel");			
		map.put(R.ERR_ALTURAPAPEL, "Altura Papel");			
		map.put(R.ERR_ESPG, "ESPG");			
		
		/*** ***/

		/* PROMPTS_AND_OPTIONS
		 */
		
		//COMMAND_PROMPT
		map.put("TXT_COMMAND_PROMPT", "Comando");

		//COMMAND_ARC
		map.put("TXT_COMMAND_ARC_PROMPT1", "Centro/<Ponto inicial>");
		map.put("TXT_COMMAND_ARC_PROMPT2", "Centro/Final/<Segundo ponto>");
		map.put("TXT_COMMAND_ARC_PROMPT3", "Ponto final");
		map.put("TXT_COMMAND_ARC_PROMPT4", "Centro");
		map.put("TXT_COMMAND_ARC_PROMPT5", "Inicio");
		map.put("TXT_COMMAND_ARC_PROMPT6", "Angulo/Comprimento da corda/<Ponto final>");
		map.put("TXT_COMMAND_ARC_PROMPT7", "Included angle");
		map.put("TXT_COMMAND_ARC_PROMPT8", "Length of chord");	
		map.put("TXT_COMMAND_ARC_PROMPT9", "Raio");
		map.put("TXT_COMMAND_ARC_PROMPT10", "Direcao");

		//COMMAND_LINE
		map.put("TXT_COMMAND_LINE_PROMPT1", "Do ponto");
		map.put("TXT_COMMAND_LINE_PROMPT2", "Fechar/<Para o ponto>");
		
		//ERROS
		map.put("ERR_DATA_READ_FAILURE", "Falha na leitura dos dados");
		map.put("ERR_DATA_WRITE_FAILURE", "Falha na escrita dos dados");

		//ALERTAS
		map.put("ALR_PROCESSING_CONCLUDED", "Processamento concluido (%s segundos)");
		map.put("ALR_FILE_SAVED", "Arquivo gravado: %s");
		map.put("ALR_FILE_OPENED", "Arquivo aberto: %s");
		
		//HELPSTRINGS
		map.put("ID_New", "Creates a new drawing file");
		map.put("ID_Open", "Opens an existing drawing file");
		map.put("ID_Close", "Closes the drawing file");
		map.put("ID_Save", "Saves the drawing with the current file name or a specified name");
		map.put("ID_Saveas", "Saves an unnamed drawing with a file name or renames the current drawing");
		map.put("ID_SavR12", "Saves the current drawing in AutoCAD Release 12 format");
		map.put("ID_Print", "Prints a drawing to a plotter, printer, or file");
		map.put("ID_Import", "Imports a file into a drawing");
		map.put("ID_Export", "Exports a drawing to a different file format");
		map.put("ID_Ioopts", "Controls options for importing and exporting");
		map.put("ID_Wmfopt", "Sets options for WMFIN");
		map.put("ID_Psqual", "Controls the rendering quality of PostScript images");
		map.put("ID_Psdisp", "Controls the appearance of a PostScript image as it's dragged using PSIN");
		map.put("ID_Psprol", "Names the prologue section read from ACAD.psf when PSOUT is used");
		map.put("ID_Mngt", "Lists managerial functions");
		map.put("ID_Files", "List, Delete, Copy, Unlock or Rename drawing files");
		map.put("ID_Audit", "Examines the integrity of a drawing");
		map.put("ID_Recov", "Repairs a damaged drawing");
		map.put("ID_Exit", "Exits AutoCAD");
		map.put("ID_Undo", "Reverses the most recent operation");
		map.put("ID_Redo", "Reverses the previous UNDO or U command");
		map.put("ID_Cut", "Removes objects and places them on the Clipboard");
		map.put("ID_Copy", "Copies objects to the Clipboard");
		map.put("ID_Copylnk", "Copies the current view to the Clipboard");
		map.put("ID_Paste", "Inserts data from the Clipboard");
		map.put("ID_Pastesp", "Inserts data from the Clipboard and controls the format of the data");
		map.put("ID_Ddmodi", "Controls object properties");
		map.put("ID_Links", "Manages OLE links");
		map.put("ID_Insobj", "Inserts a linked or embedded object");
		map.put("ID_TbRedraw", "Refreshes the display of the current viewport");
		map.put("ID_Redraw", "Refreshes the display of the current viewport");
		map.put("ID_Redall", "Refreshes the display of all viewports");
		map.put("ID_TbZoom", "Increases or decreases the apparent size of objects in the current viewport");
		map.put("ID_Zooin", "Increases the apparent size of objects in the current viewport");
		map.put("ID_Zooout", "Decreases the apparent size of objects in the current viewport");
		map.put("ID_Zoowin", "Zooms to display an area specified by a rectangular window");
		map.put("ID_Zooall", "Zooms to display the entire drawing in the current viewport");
		map.put("ID_Zoopre", "Zooms to display the previous view"); 
		map.put("ID_Zoosca", "Zooms the display using a specified scale factor"); 
		map.put("ID_Zoodyn", "Zooms to display the generated portion of the drawing");
		map.put("ID_Zoocen", "Displays a window specified by a center point and height");
		map.put("ID_Zoolef", "Displays a window specified by the lower-left corner and height");
		map.put("ID_Zoolim", "Zooms to display the objects within the drawing limits");
		map.put("ID_Zooext", "Zooms to display the drawing extents");
		map.put("ID_Zoovma", "Zooms out as far as possible on the current screen");
		map.put("ID_TbPan", "Moves the view of the drawing in the current viewport");
		map.put("ID_TbPan", "Moves the view of the drawing in the current viewport");
		map.put("ID_Panpic", "Moves the view of the drawing by the specified distance");
		map.put("ID_Panlef", "Moves the view of the drawing to the left");
		map.put("ID_Panrig", "Moves the view of the drawing to the right");
		map.put("ID_Panup", "Moves the view of the drawing up");
		map.put("ID_Pandow", "Moves the view of the drawing down");
		map.put("ID_Panupl", "Moves the view of the drawing up and to the left");
		map.put("ID_Panupr", "Moves the view of the drawing up and to the right");
		map.put("ID_Pandol", "Moves the view of the drawing down and to the left");
		map.put("ID_Pandor", "Moves the view of the drawing down and to the right");
		map.put("ID_Namview", "Creates and restores named views");
		map.put("ID_3DViewp", "Displays preset 3D viewpoint options");
		map.put("ID_Plan", "Displays the plan view of a User Coordinate System");
		map.put("ID_Planw", "Displays the plan view of the World Coordinate System");
		map.put("ID_Planu", "Displays the plan view of a previously saved UCS");
		map.put("ID_Vietop", "Sets the view point to top");
		map.put("ID_Viebot", "Sets the view point to bottom");
		map.put("ID_Vielef", "Sets the view point to left");
		map.put("ID_Vierig", "Sets the view point to right");
		map.put("ID_Viefro", "Sets the view point to front");
		map.put("ID_Viebac", "Sets the view point to back");
		map.put("ID_Vieswi", "Sets the view point to southwest isometric");
		map.put("ID_Viesei", "Sets the view point to southeast isometric");
		map.put("ID_Vienei", "Sets the view point to northeast isometric");
		map.put("ID_Vienwi", "Sets the view point to northwest isometric");
		map.put("ID_3DVpt", "Sets the direction for a 3D visualization of the drawing");
		map.put("ID_3DVptr", "Specifies a viewpoint");
		map.put("ID_3DVptt", "Displays a compass and tripod for defining a view direction");
		map.put("ID_3DVptv", "Specifies a direction as if looking back at the origin (0,0,0)");
		map.put("ID_3DDv", "Defines parallel projection or perspective views");
		map.put("ID_Vietms", "Switches to tiled model space");
		map.put("ID_Viefms", "Switches to floating model space");
		map.put("ID_Vieps", "Switches to paper space");
		map.put("ID_TVp", "Provides tiled viewport options");
		map.put("ID_TVpl", "Displays layout options for tiled viewports");
		map.put("ID_TVp1", "Returns the drawing to a single viewport view");
		map.put("ID_TVp2", "Divides the current viewport in half");
		map.put("ID_TVp3", "Divides the current viewport into three viewports");
		map.put("ID_TVp4", "Divides the current viewport into four viewports of equal size");
		map.put("ID_TVpr", "Restores a previously saved viewport configuration");
		map.put("ID_TVpd", "Deletes a named viewport configuration");
		map.put("ID_TVpj", "Combines two adjacent viewports into one larger viewport");
		map.put("ID_TVps", "Saves the current viewport using a specified name");
		map.put("ID_FVp", "Provides floating viewport options");
		map.put("ID_FVp1", "Creates one viewport that fills the available display area");
		map.put("ID_FVp2", "Divides the specified area into two viewports");
		map.put("ID_FVp3", "Divides the specified area into three viewports");
		map.put("ID_FVp4", "Divides the specified area into four viewports");
		map.put("ID_FVpr", "Restores viewport configurations to individual viewports in paper space");
		map.put("ID_FVpon", "Turns on a viewport");
		map.put("ID_FVpoff", "Turns off a viewport");
		map.put("ID_FVph", "Suppresses hidden lines within a viewport during plotting from paper space");
		map.put("ID_FVsetup", "Sets up the specifications of a drawing");
		map.put("ID_Ucsp", "Selects a preset User Coordinate System");
		map.put("ID_Dducs", "Manages User Coordinate Systems");
		map.put("ID_Ucsset", "Provides User Coordinate System options");
		map.put("ID_Ucsw", "Sets the UCS to the World Coordinate System");
		map.put("ID_Ucsori", "Defines a new UCS by shifting the origin");
		map.put("ID_Ucszav", "Defines a UCS using a positive Z axis extrusion method");
		map.put("ID_Ucs3pt", "Specifies the new UCS origin and the direction of the X and Y axes");
		map.put("ID_Ucsobj", "Defines a new coordinate system based on a selected object");
		map.put("ID_Ucsvie", "Establishes a new coordinate system with the XY plane parallel to the screen");
		map.put("ID_Ucsxar", "Rotates the current UCS about the X axis");
		map.put("ID_Ucsyar", "Rotates the current UCS about the Y axis");
		map.put("ID_Ucszar", "Rotates the current UCS about the Z axis");
		map.put("ID_Ucspre", "Restores the previous UCS");
		map.put("ID_Ucsres", "Restores a saved UCS so it becomes the current UCS");
		map.put("ID_Ucssav", "Saves the current UCS to a specified name");
		map.put("ID_Ucsdel", "Removes the specified UCS from the list of saved coordinate systems");
		map.put("ID_Ucslis", "Lists the defined UCS names");
		map.put("ID_Objcre", "Sets properties for new objects");
		map.put("ID_Layers", "Manages layers");
		map.put("ID_Vlc", "Freezes layers in specific viewports");
		map.put("ID_Vlcf", "Freezes a layer or set of layers in one or more viewports");
		map.put("ID_Vlct", "Thaws layers in specific viewports");
		map.put("ID_Vlcr", "Sets the visibility of layers in specified viewports to their default setting");
		map.put("ID_Vlcn", "Creates new layers that are frozen in all viewports");
		map.put("ID_Vlcd", "Sets a default visibility-per-viewport for layers");
		map.put("ID_Vlcl", "Displays the names of frozen layers in a viewport");
		map.put("ID_Color", "Sets the color for newly drawn objects");
		map.put("ID_Linety", "Loads and sets linetypes");
		map.put("ID_Mstyle", "Manages multiline styles");
		map.put("ID_TexSty", "Creates named text styles");
		map.put("ID_DimSty", "Creates and modifies dimension styles");
		map.put("ID_Shape", "Inserts a shape");
		map.put("ID_Units", "Controls coordinate and angle display formats and determines precision");
		map.put("ID_Limits", "Sets and controls the drawing boundaries");
		map.put("ID_Time", "Displays the date and time statistics of a drawing");
		map.put("ID_Status", "Displays drawing statistics, modes, and extents");
		map.put("ID_Rename", "Changes the names of objects");
		map.put("ID_Purge", "Removes unused objects from the drawing database");
		map.put("ID_Purla", "Removes unused layers from the drawing database");
		map.put("ID_Purlt", "Removes unused linetypes from the drawing database");
		map.put("ID_Purmls", "Removes unused multiline styles from the drawing database");
		map.put("ID_Purts", "Removes unused text styles from the drawing database");
		map.put("ID_Purds", "Removes unused dimension styles from the drawing database");
		map.put("ID_Purs", "Removes unused shapes from the drawing database");
		map.put("ID_Purblk", "Removes unused blocks from the drawing database");
		map.put("ID_Purall", "Removes all unused objects from the drawing database");
		map.put("ID_Dwgaids", "Sets drawing aids");
		map.put("ID_Osndd", "Sets running Object Snap mode and changes the target box size");
		map.put("ID_Cordis", "Changes the coordinate mode");
		map.put("ID_Ddselec", "Sets Object Selection mode settings");
		map.put("ID_Ddgrips", "Enables grips and sets their colors");
		map.put("ID_Rpref", "Sets rendering preferences");
		map.put("ID_Ucs", "Saves, restores, renames, deletes, and lists user coordinate systems");
		map.put("ID_Ucsi", "Controls visibility and placement of the UCS icon in viewports");
		map.put("ID_Ucsor", "Forces the icon to appear at the origin of the coordinate system");
		map.put("ID_Ucsf", "Generates a plan view when the UCS changes");
		map.put("ID_Disopt", "Lists display options");
		map.put("ID_Dosolf", "Specifies whether solids are filled");
		map.put("ID_Dosplfr", "Controls display of spline-fit polylines");
		map.put("ID_Doattds", "Globally controls the visibility of attributes");
		map.put("ID_Doattdn", "Keeps the current visibility of each attribute");
		map.put("ID_Doattdo", "Makes all attributes visible");
		map.put("ID_Doattdf", "Makes all attributes invisible");
		map.put("ID_Doddpt", "Specifies the display mode and size of point objects");
		map.put("ID_Doft", "Specifies that TrueType and Type 1 PostScript fonts appear as filled text");
		map.put("ID_Doot", "Specifies that TrueType and Type 1 PostScript fonts appear as outlines");
		map.put("ID_Dotf", "Controls whether text frames appear in place of text");
		map.put("ID_Dotq", "Sets the resolution of TrueType and Type 1 PostScript fonts");
		map.put("ID_Lintyps", "Lists linetype and linetype scale options");
		map.put("ID_Ltsgls", "Sets the global linetype scale factor");
		map.put("ID_Ltpss", "Sets paper space linetype scaling");
		map.put("ID_Ltgen", "Sets the linetype pattern generation around the vertices of the polyline");
		map.put("ID_Prefs", "Customizes the AutoCAD settings");
		map.put("ID_Config", "Reconfigures AutoCAD");
		map.put("ID_Rconfi", "Reconfigures the rendering setup");
		map.put("ID_Tablet", "Lists tablet options");
		map.put("ID_Tabon", "Turns Tablet mode on");
		map.put("ID_Taboff", "Turns Tablet mode off");
		map.put("ID_Tabcal", "Calibrates the digitizer with the drawing's coordinate system");
		map.put("ID_Tabcfg", "Designates or realigns the tablet menu areas");
		map.put("ID_Savtim", "Sets the automatic save interval");
		map.put("ID_Sysvar", "Lists system variables and sets their values");
		map.put("ID_Sysvarl", "Lists system variables and their values");
		map.put("ID_Sysvars", "Sets system variable values");
		map.put("ID_Appld", "Loads AutoLISP, ADS, and Arx applications");
		map.put("ID_Script", "Executes a sequence of commands from a script");
		map.put("ID_Tpal", "Opens and closes toolbars");
		map.put("ID_TbDraw", "Displays the Draw toolbar");
		map.put("ID_TbModify", "Displays the Modify toolbar");
		map.put("ID_TbDims", "Displays the Dimensions toolbar");
		map.put("ID_TbSolids", "Displays the Solids toolbar");
		map.put("ID_TbSurfac", "Displays the Surfaces toolbar");
		map.put("ID_TbXref", "Displays the External Reference toolbar");
		map.put("ID_TbRender", "Displays the Render toolbar");
		map.put("ID_TbExtdb", "Displays the External Database toolbar");
		map.put("ID_TbObjpro", "Displays the Object Properties toolbar");
		map.put("ID_TbAttrib", "Displays the Attribute toolbar");
		map.put("ID_TbMisc", "Displays the Miscellaneous toolbar");
		map.put("ID_TbSelect", "Displays the Object Selection toolbar");
		map.put("ID_Group", "Creates a named selection set of objects");
		map.put("ID_TbOsnap", "Displays the Object Snap toolbar");
		map.put("ID_TbPoiFil", "Displays the Point Filters toolbar");
		map.put("ID_TbUcs", "Displays the UCS toolbar");
		map.put("ID_TbView", "Displays the View toolbar");
		map.put("ID_Stdtbar", "Displays the Standard toolbar");
		map.put("ID_TbClall", "Closes all toolbars");
		map.put("ID_Aerial", "Opens the Aerial View window");
		map.put("ID_TxtWin", "Opens the AutoCAD Text window");
		map.put("ID_Slides", "Creates or displays slide files");
		map.put("ID_Sldvie", "Displays a raster image slide file in the current viewport");
		map.put("ID_Sldsav", "Creates a raster image slide file of the current viewport");
		map.put("ID_Images", "Creates or saves GIF, TGA, and TIFF files");
		map.put("ID_Imgvie", "Displays a GIF, TGA, or TIFF image");
		map.put("ID_Imgsav", "Saves a rendered image to a GIF, TGA, or TIFF file");
		map.put("ID_Spell", "Checks the spelling of text");
		map.put("ID_Cal", "Evaluates mathematical and geometric expressions");
		map.put("ID_Cusmnu", "Customizes menus");
		map.put("ID_Custb", "Customizes toolbars");
		map.put("ID_Reinit", "Reinitializes the input/output ports, digitizer, display, and program parameters file");
		map.put("ID_Compile", "Compiles shape files and PostScript font files");
		map.put("ID_Help", "Displays on-line help");
		map.put("ID_Helpcts", "Displays the table of contents for on-line help");
		map.put("ID_Helpsrc", "Displays the Search dialog box");
		map.put("ID_Helphow", "Describes how to use Windows on-line help");
		map.put("ID_Whatnew", "Describes new features in AutoCAD Release 13");
		map.put("ID_Qtour", "Starts Quick Tour");
		map.put("ID_Lrnacad", "Starts Learning AutoCAD on-line tutorial");
		map.put("ID_About", "Displays information about AutoCAD");
		map.put("ID_Selwin", "Selects all objects completely within a defined window");
		map.put("ID_Selcro", "Selects objects within and crossing a defined window");
		map.put("ID_Selgro", "Selects objects within a specified group");
		map.put("ID_Selpre", "Selects the most recent selection set");
		map.put("ID_Sellas", "Selects the most recently created visible object");
		map.put("ID_Selall", "Selects all objects on thawed layers");
		map.put("ID_Selwp", "Selects objects within a defined polygon");
		map.put("ID_Selcp", "Selects objects within and crossing a defined polygon");
		map.put("ID_Selfen", "Selects all objects crossing a selection fence");
		map.put("ID_Seladd", "Switches to Add mode");
		map.put("ID_Selrem", "Switches to Remove mode");
		map.put("ID_Filter", "Creates lists to select objects based on properties");
		map.put("ID_Osnfro", "Snaps to a temporary reference point");
		map.put("ID_Osnend", "Snaps to the closest endpoint of an arc or a line");
		map.put("ID_Osnmid", "Snaps to the midpoint of an arc or a line");
		map.put("ID_Osnint", "Snaps to the intersection of a line, an arc, or a circle");
		map.put("ID_Osnapp", "Snaps to the apparent intersection of two objects");
		map.put("ID_Osncen", "Snaps to the center of an arc or a circle");
		map.put("ID_Osnqua", "Snaps to a quadrant point of an arc or a circle");
		map.put("ID_Osnper", "Snaps to a point perpendicular to an arc, a line, or a circle");
		map.put("ID_Osntan", "Snaps to the tangent of an arc or a circle");
		map.put("ID_Osnnod", "Snaps to a point object");
		map.put("ID_Osnins", "Snaps to the insertion point of text, a block, a shape, or an attribute");
		map.put("ID_Osnnea", "Snaps to the nearest point of an arc, a circle, a line, or a point");
		map.put("ID_Osnqui", "Snaps to the first snap point found");
		map.put("ID_Osnnon", "Turns off Object Snap mode");
		map.put("ID_Ptfx", "Specifies a .X point filter");
		map.put("ID_Ptfy", "Specifies a .Y point filter");
		map.put("ID_Ptfz", "Specifies a .Z point filter");
		map.put("ID_Ptfxy", "Specifies a .XY point filter");
		map.put("ID_Ptfxz", "Specifies a .XZ point filter");
		map.put("ID_Ptfyz", "Specifies a .YZ point filter");
		map.put("ID_Dview", "Defines parallel projection or perspective views");
		map.put("ID_LType", "Creates, loads, and sets linetypes");
		map.put("ID_Mlstyle", "Defines a style for multiple parallel lines");
		map.put("ID_Dchpro", "Changes the color, layer, linetype, and thickness of an object");
		map.put("ID_List", "Displays database information for selected objects");
		map.put("ID_Id", "Displays the coordinates of a location");
		map.put("ID_Dist", "Measures the distance and angle between two points");
		map.put("ID_Area", "Calculates the area and perimeter of objects or of defined areas");
		map.put("ID_Masspr", "Calculates and displays the mass properties of regions or solids");
		map.put("ID_Line", "Creates straight line segments");
		map.put("ID_Xline", "Creates an infinite line");
		map.put("ID_Ray", "Creates a semi-infinite line");
		map.put("ID_Pline", "Creates two-dimensional polylines");
		map.put("ID_Poly3d", "Creates a polyline of straight line segments");
		map.put("ID_Mline", "Creates multiple parallel lines");
		map.put("ID_Spline", "Creates a quadratic or cubic spline (NURBS)");
		map.put("ID_Arc3pt", "Creates an arc using three points");
		map.put("ID_Arcsce", "Creates an arc using the start point, center, and end point");
		map.put("ID_Arcsca", "Creates an arc using the start point, center, and included angle");
		map.put("ID_Arcscl", "Creates an arc using the start point, center, and length of chord");
		map.put("ID_Arcsea", "Creates an arc using the start point, end point, and included angle");
		map.put("ID_Arcsed", "Creates an arc using the start point, end point, and starting direction");
		map.put("ID_Arcser", "Creates an arc using the start point, end point, and radius");
		map.put("ID_Arccse", "Creates an arc using the center, start point, and end point");
		map.put("ID_Arccsa", "Creates an arc using the center, start point, and included angle");
		map.put("ID_Arccsl", "Creates an arc using the center, start point, and length of chord");
		map.put("ID_Arccon", "Creates an arc tangent to the last line or arc drawn");
		map.put("ID_Cirrad", "Creates a circle using a specified radius");
		map.put("ID_Cirdia", "Creates a circles using a specified diameter");
		map.put("ID_Cir2pt", "Creates a circle using two endpoints of the diameter");
		map.put("ID_Cir3pt", "Creates a circle using three points on the circumference");
		map.put("ID_Cirttr", "Creates a circle tangent to two objects with a specified radius");
		map.put("ID_Donut", "Draws filled circles and rings");
		map.put("ID_Ellips", "Creates an ellipse using a specified center point");
		map.put("ID_Ellcir", "Creates an ellipse using a specified axis");
		map.put("ID_Ellarc", "Creates an elliptical arc");
		map.put("ID_Rectan", "Draws a rectangular polyline");
		map.put("ID_Polygo", "Creates an equilateral closed polyline");
		map.put("ID_Solid", "Creates solid-filled polygons");
		map.put("ID_Region", "Creates a region object from a selection set of existing objects");
		map.put("ID_Bpoly", "Creates a region or polyline of a closed boundary");
		map.put("ID_Point", "Creates a point object");
		map.put("ID_Divide", "Places evenly spaced point objects or blocks along the length or perimeter of an object");
		map.put("ID_Measure", "Places point objects or blocks at measured intervals on an object");
		map.put("ID_Dinser", "Inserts a block or another drawing");
		map.put("ID_Block", "Creates a block definition from a set of objects");
		map.put("ID_TbBlock", "Creates a block definition from a set of objects");
		map.put("ID_Bhatch", "Fills an enclosed area with an associative hatch pattern");
		map.put("ID_Psfill", "Fills a two-dimensional polyline outline with a PostScript pattern");
		map.put("ID_Mtext", "Creates paragraph text");
		map.put("ID_Dtext", "Displays text on screen as it is entered");
		map.put("ID_Text", "Creates a single line of text");
		map.put("ID_Move", "Displaces objects a specified distance in a specified direction");
		map.put("ID_Explode", "Breaks a compound object into its component objects");
		map.put("ID_Erase", "Removes objects from a drawing");
		map.put("ID_Copyob", "Duplicates objects");
		map.put("ID_Offset", "Creates concentric circles, parallel lines, and parallel curves");
		map.put("ID_Mirror", "Creates a mirror image copy of objects");
		map.put("ID_Mir3d", "Creates a mirror image copy of objects about a plane");
		map.put("ID_Arrrec", "Creates a rectangular array");
		map.put("ID_Arrpol", "Creates a polar array");
		map.put("ID_Arr3dr", "Creates a rectangular 3D array");
		map.put("ID_Arr3dp", "Creates a polar 3D array");
		map.put("ID_Rotate", "Moves objects about a base point");
		map.put("ID_Rot3d", "Moves objects about a three-dimensional axis");
		map.put("ID_Align", "Moves and rotates objects to align with other objects");
		map.put("ID_Stretch", "Moves or stretches objects");
		map.put("ID_Scale", "Enlarges or reduces objects equally in the X, Y, and Z directions");
		map.put("ID_Length", "Lengthens an object");
		map.put("ID_Change", "Changes the properties of existing objects");
		map.put("ID_Trim", "Trims objects at a cutting edge defined by other objects");
		map.put("ID_Extend", "Extends an object to meet another object");
		map.put("ID_Bre1pt", "Breaks the selected object at the selection point");
		map.put("ID_Bre1ps", "Breaks the selected object at a specified point");
		map.put("ID_Bre2pt", "Breaks the selected object between the selection point and a specified point");
		map.put("ID_Bre2ps", "Breaks the selected object between two specified points");
		map.put("ID_Pedit", "Edits polylines and three-dimensional polygon meshes");
		map.put("ID_Mledit", "Edits multiple parallel lines");
		map.put("ID_Spledi", "Edits a spline object");
		map.put("ID_TexEdi", "Edits attribute definitions, text, and mtext objects");
		map.put("ID_Hatedi", "Modifies an existing associative hatch block");
		map.put("ID_Chamfe", "Bevels the edges of objects");
		map.put("ID_Fillet", "Rounds and fillets the edges of objects");
		map.put("ID_DimLin", "Creates linear dimensions");
		map.put("ID_DimAli", "Creates an aligned linear dimension");
		map.put("ID_TbDimRad", "Creates radial dimensions for circles and arcs");
		map.put("ID_DimAng", "Creates an angular dimension");
		map.put("ID_TbDimOrd", "Creates ordinate point dimensions");
		map.put("ID_DimBas", "Continues a dimension from the baseline of the previous or selected dimension");
		map.put("ID_DimCon", "Continues a dimension from the second extension line of the previous or a selected dimension");
		map.put("ID_DimCen", "Creates the center mark or the center lines of circles and arcs");
		map.put("ID_DimLea", "Creates a line that connects annotation to a feature");
		map.put("ID_DimTol", "Creates geometric tolerances");
		map.put("ID_TbDimText", "Moves and rotates dimension text");
		map.put("ID_DimRad", "Creates radius dimensions for circles and arcs");
		map.put("ID_DimDia", "Creates diameter dimensions for circles and arcs");
		map.put("ID_DimOrd", "Creates ordinate point dimensions");
		map.put("ID_DimOrx", "Measures the X coordinate of a feature");
		map.put("ID_DimOry", "Measures the Y coordinate of a feature");
		map.put("ID_DimHom", "Moves dimension text back to its default position");
		map.put("ID_DimTro", "Rotates dimension text");
		map.put("ID_DimTle", "Left justifies dimension text");
		map.put("ID_DimTce", "Center justifies dimension text");
		map.put("ID_DimTri", "Right justifies dimension text");  
		map.put("ID_DimObl", "Modifies oblique angle of dimension");
		map.put("ID_Sphere", "Creates a 3D solid sphere");
		map.put("ID_Torus", "Creates a donut-shaped solid");
		map.put("ID_Extrud", "Creates unique solid primitives by extruding existing 2D objects");
		map.put("ID_Revolv", "Creates a solid by revolving a 2D object about an axis");
		map.put("ID_Slice", "Slices a set of solids with a plane");
		map.put("ID_Sectio", "Creates a region using the intersection of a plane and solids");
		map.put("ID_Interf", "Creates a composite solid from the common volume where a set of solids interfere");
		map.put("ID_Amecon", "Converts AME solid models to AutoCAD solid objects");
		map.put("ID_Union", "Creates a composite region or solid");
		map.put("ID_Subtra", "Subtracts the area or volume of one set of regions or solids from another set");
		map.put("ID_Inters", "Creates composite solids or regions from the intersection of solids or regions");
		map.put("ID_Boxcen", "Creates a box using a specified center point");
		map.put("ID_Boxcor", "Defines the first corner of a box");
		map.put("ID_Cylell", "Creates a cylinder with an elliptical base");
		map.put("ID_Cylcen", "Defines the center of the circular base of a cylinder");
		map.put("ID_Conell", "Creates a cone with an elliptical base");
		map.put("ID_Concen", "Defines the center of the circular base of a cone");
		map.put("ID_Wedcen", "Creates a wedge using a specified center point");
		map.put("ID_Wedcor", "Defines the first corner of a wedge");
		map.put("ID_SurBox", "Creates a 3D box wire frame");
		map.put("ID_SurWed", "Creates a right angle wedge-shaped polygon wire frame");
		map.put("ID_SurPyr", "Creates a pyramid or a tetrahedron");
		map.put("ID_SurCon", "Creates a cone-shaped wire frame");
		map.put("ID_SurSph", "Creates a spherical polygon mesh");
		map.put("ID_SurDom", "Creates the upper half of a spherical polygon mesh");
		map.put("ID_SurDis", "Creates the lower half of a spherical polygon mesh");
		map.put("ID_SurTor", "Creates a toroidal polygon mesh");
		map.put("ID_3dface", "Creates a 3D face");
		map.put("ID_Edge", "Changes the visibility of 3D face edges");
		map.put("ID_Revsur", "Creates a rotated surface about a selected axis");
		map.put("ID_Tabsur", "Creates a tabulated surface from a path curve and direction vector");
		map.put("ID_Rulesu", "Creates a ruled surface between two curves");
		map.put("ID_Edgesu", "Creates a three-dimensional polygon mesh");
		map.put("ID_3dmesh", "Creates a free-form polygon mesh");
		map.put("ID_XreAtt", "Attaches an xref");
		map.put("ID_XreOve", "Overlays an xref");
		map.put("ID_XreRel", "Reloads one or more xrefs");
		map.put("ID_XreDet", "Detaches xrefs");
		map.put("ID_XreCli", "Inserts and clips an xref");
		map.put("ID_XrePat", "Displays and edits the path name associated with an xref");
		map.put("ID_XreLis", "Lists the xref path name and the number of attached xrefs");
		map.put("ID_TbBind", "Binds dependent symbols of an xref to the drawing");
		map.put("ID_Bind", "Binds all external references to the current drawing");
		map.put("ID_BinBlo", "Binds blocks of an xref to the drawing");
		map.put("ID_BinLay", "Binds layers of an xref to the drawing");
		map.put("ID_BinLin", "Binds linetypes of an xref to the drawing");
		map.put("ID_BinTex", "Binds text styles of an xref to the drawing");
		map.put("ID_BinDim", "Binds dimension styles of an xref to the drawing");
		map.put("ID_AttDef", "Creates an attribute definition");
		map.put("ID_AttRed", "Redefines a block and updates associated attributes");
		map.put("ID_AttEdi", "Edits the variable attributes of a block");
		map.put("ID_AttEdg", "Changes attribute information independent of its block definition");
		map.put("ID_Hide", "Regenerates a three-dimensional model with hidden lines suppressed");
		map.put("ID_Shade", "Displays a flat-shaded image of the drawing in the current viewport");
		map.put("ID_Render", "Creates a realistically shaded image of a three-dimensional wire frame or solid model");
		map.put("ID_Scene", "Manages scenes");
		map.put("ID_Light", "Manages lights and lighting effects");
		map.put("ID_Rmat", "Manages rendering materials");
		map.put("ID_Matlib", "Imports and exports materials to and from a library of materials");
		map.put("ID_Stats", "Displays rendering statistics");
		map.put("ID_Aseadm", "Performs administrative functions for external database commands");
		map.put("ID_Aserow", "Displays and edits table data and creates links and selection sets");
		map.put("ID_Aselin", "Manipulates links between objects and an external database");
		map.put("ID_Asesel", "Creates a selection set from rows linked to textual selection sets and graphic selection sets");
		map.put("ID_Aseexp", "Exports link information for selected objects");    
		map.put("ID_Asesql", "Executes Structured Query Language (SQL)");
		map.put("ID_Minser", "Inserts multiple instances of a block in a rectangular array");
		map.put("ID_Oops", "Restores erased objects");
		map.put("ID_Sketch", "Creates a series of freehand line segments");
		map.put("ID_Trace", "Creates solid lines");
		map.put("ID_Tbarc", "Displays the Arc toolbar");
		map.put("ID_TbBox", "Displays the Box toolbar");
		map.put("ID_TbBreak", "Displays the Break toolbar");
		map.put("ID_Tbcir", "Displays the Circle toolbar");
		map.put("ID_TbCone", "Displays the Cone toolbar");
		map.put("ID_TbCopy", "Displays the Copy toolbar");
		map.put("ID_TbCylind", "Displays the Cylinder toolbar");
		map.put("ID_Tbell", "Displays the Ellipse toolbar");
		map.put("ID_TbExplode", "Displays the Explode toolbar");
		map.put("ID_TbFeatur", "Displays the Chamfer and Fillet toolbar");
		map.put("ID_Tbhatch", "Displays the Hatch toolbar");
		map.put("ID_TbInq", "Displays the Inquiry toolbar");
		map.put("ID_TbLine", "Displays the Line toolbar");
		map.put("ID_TbPoint", "Displays the Point toolbar");
		map.put("ID_Tbpgon", "Displays the Polygon toolbar");
		map.put("ID_Tbpline", "Displays the Polyline toolbar");
		map.put("ID_TbResize", "Displays the Resize toolbar");
		map.put("ID_TbRotate", "Displays the Rotate toolbar");
		map.put("ID_TbSelect", "Displays the Select Objects toolbar");
		map.put("ID_TbSpace", "Switches between paper space and model space");
		map.put("ID_TbSpledi", "Displays the Special Edit toolbar");
		map.put("ID_Tbtext", "Displays the Text toolbar");
		map.put("ID_TbToolwi", "Displays the Tool Windows toolbar");
		map.put("ID_TbTrim", "Displays the Trim and Extend toolbar");
		map.put("ID_TbWedge", "Displays the Wedge toolbar");
		map.put("ID_Picks1", "Selecting a single group member selects all members");
		map.put("ID_Picks2", "Selecting hatching also selects the hatch boundary");
	}
	
	/* Methodes */

	@Override
	public String getString(String name)
	{
		String result = AppDefs.NULL_STR;
		if( map.containsKey(name) )
			result = map.get(name);
		return result;
	}
	
	@Override
	public String getMonthFullName(int month)
	{
		String result = AppDefs.NULL_STR;
		if( (month >= 0) && (month < LS_MONTHS_FULLNAME.length) )
			result = LS_MONTHS_FULLNAME[month];
		return result;
	}
	
	@Override
	public String getMonthAbrev(int month)
	{
		String result = AppDefs.NULL_STR;
		if( (month >= 0) && (month < LS_MONTHS_ABREV.length) )
			result = LS_MONTHS_ABREV[month];
		return result;
	}
	
	@Override
	public String getTblLayerExplorer(int col)
	{
		String result = AppDefs.NULL_STR;
		if( (col >= 0) && (col < LS_TBLLAYEREXPLORER.length) )
			result = LS_TBLLAYEREXPLORER[col];
		return result;
	}
	
	@Override
	public String getTblPropertyEditor(int col)
	{
		String result = AppDefs.NULL_STR;
		if( (col >= 0) && (col < LS_TBLPROPERTYEDITOR.length) )
			result = LS_TBLPROPERTYEDITOR[col];
		return result;
	}
	
	@Override
	public String getTblResultList(int col)
	{
		String result = AppDefs.NULL_STR;
		if( (col >= 0) && (col < LS_TBLRESULTLIST.length) )
			result = LS_TBLRESULTLIST[col];
		return result;
	}

	/* GROUP_ITEM_DATA */
	
	public GroupItemDataVO getGroupItemData(int groupItemDataId)
	{
		int pos = groupItemDataId - AppDefs.DOCVIEW_GRP_LEVELS_VAL;
		
		GroupItemDataVO oGroupItemData = ResourceEnUs.ARR_DOCVIEW_GROUPS[pos];
		return oGroupItemData;
	}
	
	public int getSzGroupItemData()
	{
		int sz = ResourceEnUs.ARR_DOCVIEW_GROUPS.length;
		return sz;
	}
	
}
