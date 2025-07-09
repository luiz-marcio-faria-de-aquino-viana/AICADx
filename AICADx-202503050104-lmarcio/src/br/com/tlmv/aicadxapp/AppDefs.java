/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppDefs.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;

import br.com.tlmv.aicadxapp.cad.CadAcabamentoJanelaDef;
import br.com.tlmv.aicadxapp.cad.CadAcabamentoParedeDef;
import br.com.tlmv.aicadxapp.cad.CadAcabamentoPisoDef;
import br.com.tlmv.aicadxapp.cad.CadAcabamentoPortaDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import javafx.scene.layout.BorderStrokeStyle;

public class AppDefs 
{
//Public	
	public static final int DEBUG_LEVEL00 = 0;
	public static final int DEBUG_LEVEL01 = 1;
	public static final int DEBUG_LEVEL02 = 2;
	public static final int DEBUG_LEVEL03 = 3;
	public static final int DEBUG_LEVEL04 = 4;
	public static final int DEBUG_LEVEL05 = 5;
	//
	public static final int DEBUG_LEVEL99 = 99;

	public static final int DEBUG_LEVEL = AppDefs.DEBUG_LEVEL02;

	public static final String APP_NAME = "AICADx for Steam (CodeName: JuJu)"; 
	
	public static final String APP_VERSAO = "1.0.20250305";

	public static final String APP_COPYRIGHT = "Copyright(C) 2025 TLMV Consultoria e Sistemas EIRELI. All Rights Reserved.";

	public static final String APP_AUTHOR_NAME = "Autor: Luiz Marcio Faria de Aquino Viana, Pos-D.Sc.";
	public static final String APP_AUTHOR_REGISTRO = "CPF: 024.723.347-10 - RG: 08855128-8 IFP-RJ - Registro: 2000103581 CREA-RJ";
	public static final String APP_AUTHOR_EMAIL = "E-mail: lmarcio@tlmv.com.br";
	public static final String APP_AUTHOR_TELEFONE = "Telefone: +55-21-99983-7207";
	
	public static final String APP_HOME = "AICADX_HOME";
	
	public static final String APP_ICON = "/br/com/tlmv/aicadxapp/res/icons/aicadx_v1_0.jpg";
	
	public static final String HLP_USAGE_INFO =
		    "HELP\n\n" +
			"Use: AICADx -open [DRAWING_FILE_NAME]           - to load a drawing file name (.aix)\n";
	
	//RESULT_CODES
	//
	public static final int RSERR = -1;
	public static final int RSOK = 0;
	
	//DEFAULT_EXTENSIONS
	//
	public static final String EXT_AIX = "aix";
	public static final String EXT_DXF = "dxf";
	public static final String EXT_IFC = "ifc";
			
	//DEFAULT_TIMEMILE
	//
	public static final long H24 = 24L * 60L * 60L * 1000L; 
	//
	public static final long D01 = 1L * H24; 
	public static final long D02 = 2L * H24; 
	public static final long D03 = 3L * H24; 
	public static final long D04 = 4L * H24; 
	public static final long D05 = 5L * H24; 
	public static final long D10 = 10L * H24; 
	public static final long D15 = 15L * H24; 
	public static final long D20 = 20L * H24; 
	public static final long D25 = 25L * H24; 
	public static final long D30 = 30L * H24; 
	public static final long D45 = 45L * H24; 
	public static final long D60 = 60L * H24; 
	public static final long D90 = 90L * H24; 
	
	//DEFAULT_DATETIME_INITIAL
	//
	public static final Date DATETIME_INITIAL = new Date(70, 9, 1);							// Author BirthDay: 01/10/1970 - Luiz Marcio Faria de Aquino Viana, Pos-D.Sc.
	public static final long DATETIME_INITIAL_MILI = AppDefs.DATETIME_INITIAL.getTime();
	
	//CONTEXT_DEFINITIONS
	//
	public static final String CTX_HOMEDIR = "/home/lmarcio/9997-TLMV/002-AICADx/AICADx_v1.0/";
	//
	public static final String CTX_BINDIR = "Bin/";
	public static final String CTX_CONFDIR = "Conf/";
	public static final String CTX_DATADIR = "Data/";
	public static final String CTX_DOCSDIR = "Docs/";
	public static final String CTX_REPOSITORYDIR = "Repository/";
	public static final String CTX_SPOOLDIR = "Spool/";
	public static final String CTX_TEMPDIR = "Temp/";
	public static final String CTX_TEMPLATESDIR = "Templates/";
	//
	public static final String CTX_CONFFILE = "aicadx_config.cfg";
	public static final String CTX_CONFFILE_DEFAULT = "aicadx_config-default.cfg";
	//
	public static final String CTX_LAYERSFILE = "aicadx_layers.cfg";
	public static final String CTX_LAYERSFILE_DEFAULT = "aicadx_layers-default.cfg";
	
	//CONFIG_TAGS
	//
	public static final String CFG_TAG_CONFIGURATION = "Configuration";
	public static final String CFG_TAG_CONFIGURATION_ENVIROMENT_TYPE = "EnviromentType";
	public static final String CFG_TAG_CONFIGURATION_REPOSITORY_LOCATION = "RepositoryLocation";
	
	//EVENT_TYPES
	//
	public static final int EVENTTYPE_NONE = -1;
	public static final int EVENTTYPE_CMDENTER = 1001;
		
	//NULL_VALUES
	//
	public static final int    NULL_INT  = -1;
	public static final char   NULL_CHAR = '\0';
	public static final double NULL_DBL  = 0.0;
	public static final String NULL_STR  = "?";
	
	//LANGUAGE_DEFINITIONS
	//
	public static final String DEF_LANG_PT = "Pt";
	public static final String DEF_LANG_EN = "En";
	
	//FORMAT_REGIONS_DEFINITIONS
	//
	public static final String DEF_COUNTRY_BR = "Br";	
	public static final String DEF_COUNTRY_US = "Us";

	//FORMAT_DATE_DEFINITIONS
	//
	public static final String DEF_DATE_TYPE1_ENUS_MASC = "MM/dd/yyyy";
	public static final String DEF_DATE_TYPE2_PTBR_MASC = "dd/MM/yyyy";
	public static final String DEF_DATE_TYPE3_MASC = "yyyy-MM-dd";
	public static final String DEF_DATE_TYPE4_MASC = "yyyyMMdd";

	//FORMAT_DATETIME_DEFINITIONS
	//
	public static final String DEF_DATETIME_TYPE1_ENUS_MASC = "MM/dd/yyyy HH:mm:ss";
	public static final String DEF_DATETIME_TYPE2_PTBR_MASC = "dd/MM/yyyy HH:mm:ss";
	public static final String DEF_DATETIME_TYPE3_MASC = "yyyyMMdd_HHmmss";
	public static final String DEF_DATETIME_TYPE4_MASC = "yyyyMMddHHmmss";
	
	//SEQUENCE_VALUE_RANGE
	//
	public static final int DEF_SEQ_INIT = 100000001;
	public static final int DEF_SEQ_END = 999999999;
			
	//MESSAGE_TYPES
	//
	public static final int DEF_MSGTYPE_NONE = 0;
	public static final int DEF_MSGTYPE_WARN = 1;
	public static final int DEF_MSGTYPE_ERROR = 2;

	//MESSAGE_ACTIONS
	//
	public static final String DEF_BTN_ACTION_MESSAGE_NONE = "actMessageNone";
	public static final String DEF_BTN_ACTION_MESSAGE_OK = "actMessageOk";
	public static final String DEF_BTN_ACTION_MESSAGE_CANCELAR = "actMessageCancelar";
	public static final String DEF_BTN_ACTION_MESSAGE_FECHAR = "actMessageFechar";
	
	//INPUT_PARAM_TYPE
	//	
	public static final int DEF_INPUTPARAMTYPE_NONE = 1000;
	public static final int DEF_INPUTPARAMTYPE_POINT = 1001;
	public static final int DEF_INPUTPARAMTYPE_LINE = 1002;
	public static final int DEF_INPUTPARAMTYPE_RECTANGLE = 1003;
	public static final int DEF_INPUTPARAMTYPE_POLYLINE = 1004;
	public static final int DEF_INPUTPARAMTYPE_CIRCLE = 1005;
	public static final int DEF_INPUTPARAMTYPE_ARC = 1006;
	public static final int DEF_INPUTPARAMTYPE_TEXT = 1007;
	public static final int DEF_INPUTPARAMTYPE_POLYGON = 1008;
	public static final int DEF_INPUTPARAMTYPE_AREA = 1009;
	public static final int DEF_INPUTPARAMTYPE_KEYAREA = 1010;
	public static final int DEF_INPUTPARAMTYPE_KEYAREATABLE = 1011;
	//
	public static final int DEF_INPUTPARAMTYPE_VDIR = 1101;
	//
	public static final int DEF_INPUTPARAMTYPE_ENTITY = 1201;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_1PTS = 1202;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_2PTS = 1203;
	
	//FORMS_RESULT_CODES
	//
	//RSCODE: MAIN
	public static final int RSCODE_MAIN_FECHAR = 1001;
	//RSCODE: MESSAGE
	public static final int RSCODE_MESSAGE_NONE = 2001;
	public static final int RSCODE_MESSAGE_OK = 2002;
	public static final int RSCODE_MESSAGE_CANCELAR = 2003;
	public static final int RSCODE_MESSAGE_FECHAR = 2004;
	//RSCODE: SELECIONA_ARQUIVO
	public static final int RSCODE_SELECIONA_ARQUIVO_NONE = 3001;
	public static final int RSCODE_SELECIONA_ARQUIVO_OK = 3002;
	public static final int RSCODE_SELECIONA_ARQUIVO_CANCELAR = 3003;
	//RSCODE: CONFIG
	public static final int RSCODE_CONFIG_NONE = 4001;
	public static final int RSCODE_CONFIG_OK = 4002;
	public static final int RSCODE_CONFIG_CANCELAR = 4003;
	public static final int RSCODE_CONFIG_LOADDEFAULTS = 4004;
	//RSCODE: LAYEREXPLORER
	public static final int RSCODE_LAYEREXPLORER_NONE = 5001;
	public static final int RSCODE_LAYEREXPLORER_ADICIONAR = 5002;
	public static final int RSCODE_LAYEREXPLORER_REMOVER = 5003;
	public static final int RSCODE_LAYEREXPLORER_FECHAR = 5004;
	
	//ACTION_LAYEREXPLORER_TYPE
	//	
	public static final String DEF_CBX_ACTION_LAYEREXPLORER_LTYPE = "_CBX_ACTION_LAYEREXPLORER_LTYPE_";

	//ACCELERATORS (CONTROL+KEY)
	//
	public static final char CTRL_ESC = (char)0x27; 
	public static final char CTRL_LOAD = 'L'; 
	public static final char CTRL_REDRAW = 'R';
	public static final char CTRL_UNDO = 'Z';
	public static final char CTRL_CUT = 'X';
	public static final char CTRL_COPY = 'C';
	public static final char CTRL_PASTE = 'V';
	public static final char CTRL_OPEN = 'O';
	public static final char CTRL_PRINT = 'P';
	public static final char CTRL_NEW = 'N';
	public static final char CTRL_SAVE = 'S';		
	
	/* BLOCKTABLE_DEFINITIONS 
	 */
	public static final String BLKTABLE_MODELSPACE			= "_MODELSPACE_";
	public static final String BLKTABLE_PAPERSPACE			= "_PAPERSPACE_";
	
	/* LAYER_DEFINITIONS 
	 */
	public static final String LAYER_0						= "0";
	//
	public static final String LAYER_F_VIGA_TETO			= "F_VIGA_TETO";
	public static final String LAYER_F_VIGA_PISO            = "F_VIGA_PISO";
	public static final String LAYER_F_PILAR                = "F_PILAR";
	//
	public static final String LAYER_A_PISO                 = "A_PISO";
	public static final String LAYER_A_PISO_ACAB            = "A_PISO_ACAB";
	public static final String LAYER_A_ALVE                 = "A_ALVE";
	public static final String LAYER_A_ALVE_ACAB            = "A_ALVE_ACAB";
	public static final String LAYER_A_PORTA                = "A_PORTA";
	public static final String LAYER_A_PORTA_ACAB           = "A_PORTA_ACAB";
	public static final String LAYER_A_JANELA               = "A_JANELA";
	public static final String LAYER_A_JANELA_ACAB          = "A_JANELA_ACAB";
	public static final String LAYER_A_MOBI                 = "A_MOBI";
	public static final String LAYER_A_PONTOS               = "A_PONTOS";
	public static final String LAYER_A_AREAS                = "A_AREAS";
	public static final String LAYER_A_COTAS                = "A_COTAS";
	public static final String LAYER_A_TEXTOS               = "A_TEXTOS";
	
	/* POINT_DEFINITION
	 */
	public static final double POINT_SIZE					= 5;		// 5 pixels
	
	/* OBJECT_TYPES_DEFINITIONS 
	 */
	public static final int OBJTYPE_ANY						= -2; 
	public static final int OBJTYPE_NONE					= -1; 
	//
	public static final int OBJTYPE_APPCADMAIN				= 10;
	//
	public static final int OBJTYPE_TABLES					= 1000;
	public static final int OBJTYPE_LAYER_TABLE				= 1001;
	public static final int OBJTYPE_BLOCK_TABLE				= 1099;
	public static final int OBJTYPE_DOCUMENT_TABLE			= 1999;
	//
	public static final int OBJTYPE_DEFS					= 2000;
	public static final int OBJTYPE_LAYER_DEF				= 2001;
	public static final int OBJTYPE_BLOCK_DEF				= 2099; 
	public static final int OBJTYPE_DOCUMENT_DEF			= 2999;
	//
	public static final int OBJTYPE_BIMDEFS					= 3000;
	public static final int OBJTYPE_BIMACABAMENTOPAREDE_DEF = 3001;
	public static final int OBJTYPE_BIMACABAMENTOPISO_DEF 	= 3002;
	public static final int OBJTYPE_BIMACABAMENTOPORTA_DEF	= 3003;
	public static final int OBJTYPE_BIMACABAMENTOJANELA_DEF	= 3004;
	//
	public static final int OBJTYPE_ENTITIES				= 4000;
	public static final int OBJTYPE_POINT					= 4001;
	public static final int OBJTYPE_LINE					= 4002;
	public static final int OBJTYPE_RECTANGLE				= 4003;
	public static final int OBJTYPE_CIRCLE					= 4004;
	public static final int OBJTYPE_ARC						= 4005;
	public static final int OBJTYPE_TEXT					= 4006;
	public static final int OBJTYPE_POLYGON					= 4007;
	public static final int OBJTYPE_INSERT					= 4099; 
	//
	public static final int OBJTYPE_ENTITIES3D				= 5000;
	public static final int OBJTYPE_BOX3D					= 5001;
	//
	public static final int OBJTYPE_BIMENTITIES				= 6000;
	public static final int OBJTYPE_BIMPAREDE				= 6001;
	public static final int OBJTYPE_BIMPORTA				= 6002;
	public static final int OBJTYPE_BIMPDUPLA				= 6003;
	public static final int OBJTYPE_BIMJANELA				= 6004;
	public static final int OBJTYPE_BIMPISO					= 6005;
	public static final int OBJTYPE_BIMAREA					= 6006;
	//
	public static final int OBJTYPE_BIMAREATABLE			= 7001;

	/* COLORINDEX TABLE DEFINITION 
	 */
	public static Color[] ARR_COLORINDEX_TABLE = {
		new Color(0xFF, 0xFF, 0xFF),						// 0=BLACK
		new Color(0xFF, 0x00, 0x00),						// 1=RED
		new Color(0xFF, 0xFF, 0x00),						// 2=YELLOW
		new Color(0x00, 0xFF, 0x00),						// 3=GREEN
		new Color(0x00, 0xFF, 0xFF),						// 4=CYAN
		new Color(0x00, 0x00, 0xFF),						// 5=BLUE
		new Color(0xFF, 0x00, 0xFF),						// 6=MAGENTA
		new Color(0xAA, 0xAA, 0xAA),						// 7=WHITE
		new Color(0x41, 0x41, 0x41),						// 8=DARK_GREY
		new Color(0x80, 0x80, 0x80),						// 9=LIGHT_GREY
		new Color(0xFF, 0x00, 0x00),						// 10=RED10
		new Color(0xFF, 0xAA, 0xAA),						// 11=RED11
		new Color(0xBD, 0x00, 0x00),						// 12=DARK_RED12
		new Color(0xBD, 0x7E, 0x7E),						// 13=DARK_RED13
		new Color(0x81, 0x00, 0x00),						// 14=DARK_RED14
		new Color(0x81, 0x56, 0x56)							// 15=DARK_RED15
	};

	/* COLORINDEX TABLE DEFINITION (COLOR NAMES) 
	 */
	public static String[] ARR_COLORNAME_TABLE = {
		"Black",
		"Red",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Magenta",
		"White",
		"Dark Grey",
		"Light Grey",
		"Red 10",
		"Red 11",
		"Dark Red 12",
		"Dark Red 13",
		"Dark Red 14",
		"Dark Red 15"
	};

	/* COLORINDEX DEFINITION 
	 */
	public static int COLORINDEX_BLACK		= 0;			// 0=BLACK
	public static int COLORINDEX_RED		= 1;			// 1=RED
	public static int COLORINDEX_YELLOW		= 2;			// 2=YELLOW
	public static int COLORINDEX_GREEN		= 3;			// 3=GREEN
	public static int COLORINDEX_CYAN		= 4;			// 4=CYAN
	public static int COLORINDEX_BLUE		= 5;			// 5=BLUE
	public static int COLORINDEX_MAGENTA	= 6;			// 6=MAGENTA
	public static int COLORINDEX_WHITE		= 7;			// 7=WHITE
	public static int COLORINDEX_DARK_GREY	= 8;			// 8=DARK_GREY
	public static int COLORINDEX_LIGHT_GREY	= 9;			// 9=LIGHT_GREY
	public static int COLORINDEX_RED10		= 10;			// 10=RED10
	public static int COLORINDEX_RED11		= 11;			// 11=RED11
	public static int COLORINDEX_DARK_RED12	= 12;			// 12=DARK_RED12
	public static int COLORINDEX_DARK_RED13	= 13;			// 13=DARK_RED13
	public static int COLORINDEX_DARK_RED14	= 14;			// 14=DARK_RED14
	public static int COLORINDEX_DARK_RED15	= 15;			// 15=DARK_RED15

	/* COLOR DEFINITION 
	 */
	public static Color BACKGROUNDCOLOR = new Color(0x00, 0x00, 0x00);					//BLACK
	public static Color BASEPLANCOLOR = new Color(0x80, 0x80, 0x80);					//DARK_GRAY
	public static Color CURSORCOLOR = new Color(0xE0, 0xE0, 0xE0);						//LIGHT_GRAY_2
	public static Color GRIDCOLOR = new Color(0xA0, 0xA0, 0xA0);						//LIGHT_GRAY_1
	public static Color BLIPCOLOR = new Color(0xA0, 0xA0, 0xA0);						//LIGHT_GRAY_1
	public static Color GRIPOBJECTCOLOR_SELECTMODE = new Color(0x64, 0x95, 0xED);		//CORNFLOWER_BLUE
	public static Color DRAGOBJECTCOLOR_SELECTMODE = new Color(0xFF, 0xEB, 0xCD);		//BLANCHED_ALMOND
	public static Color HOVEROBJECTCOLOR_SELECTMODE = new Color(0xFF, 0xB6, 0xC1);		//LIGHT_PINK
	public static Color SELECTOBJECTCOLOR_SELECTMODE = new Color(0xF0, 0x80, 0x80);		//LIGHT_CORAL
	public static Color RUBBERBANDCOLOR_PICKMODE = new Color(0xFF, 0xEB, 0xCD);			//BLANCHED_ALMOND
	public static Color RUBBERBANDCOLOR_ZOOMMODE = new Color(0xC0, 0xC0, 0xC0);			//SILVER
	//
	public static Color WALLFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);					//GRAY
	public static Color WALLFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);					//PURPLE
	public static Color WALLFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);					//OLIVE
	//
	public static Color FLOORFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);				//GRAY
	public static Color FLOORFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);				//PURPLE
	public static Color FLOORFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);				//OLIVE
	//
	public static Color DOORFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);					//GRAY
	public static Color DOORFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);					//PURPLE
	public static Color DOORFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);					//OLIVE
	//
	public static Color DOOROPPENINGCOLOR1 = new Color(0xFF, 0xFF, 0xFF);				//WHITE
	//
	public static Color WINDOWFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);				//GRAY
	public static Color WINDOWFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);				//PURPLE
	public static Color WINDOWFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);				//OLIVE
	//
	public static Color WINDOWOPPENINGCOLOR1 = new Color(0x00, 0xFF, 0xFF);				//CYAN
	
	/* LTYPE PATTERN 
	 */
	public static float[] LTYPE_PATTERN_HIDDEN = { 5.0F, 5.0F };
	public static float[] LTYPE_PATTERN_DASHDOT = { 2.0F, 5.0F };
	
	/* LTYPE TABLE DEFINITION 
	 */
	public static BorderStrokeVO[] ARR_LTYPE_TABLE = {
		new BorderStrokeVO(0, "CONTINUOUS", new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER)),
		new BorderStrokeVO(1, "HIDDEN", new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0F, AppDefs.LTYPE_PATTERN_HIDDEN, 0)),
		new BorderStrokeVO(1, "DASHDOT", new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0F, AppDefs.LTYPE_PATTERN_DASHDOT, 0))
	};

	/* LTYPEINDEX TABLE DEFINITION 
	 */
	public static int LTYPEINDEX_SOLID		= 0;			// 0=SOLID
	public static int LTYPEINDEX_HIDDEN		= 1;			// 1=HIDDEN
	public static int LTYPEINDEX_DOTTED		= 2;			// 2=DOTTED

	/* LTYPE DEFINITION 
	 */
	public static BorderStrokeVO DRAGOBJECTLTYPE_SELECTMODE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_DOTTED];
	public static BorderStrokeVO SELECTOBJECTLTYPE_SELECTMODE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO HOVEROBJECTLTYPE_SELECTMODE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	
	/* DISPLAY COORDS DEFINITION 
	 */
	public static String DISPCORDS_FONTFAMILY = Font.SANS_SERIF;
	public static Color DISPCOORDS_COLOR = new Color(0xFF, 0xFF, 0xFF);
	public static int DISPCOORDS_HEIGHT = 12;
	public static int DISPCOORDS_VERTICALPOS = 24;
	
	/* DEFAULT_DOCUMENT
	 */
	public static final String DEFAULT_DOCUMENT_NAME		= "Drawing%s";
	public static final String DEFAULT_DOCUMENT_FILENAME	= "Drawing%s.aix";
	
	/* MATH_TABLE
	 */
	public static final double MATHVAL_SQRT2 = Math.sqrt(2.0);
	public static final double MATHVAL_SQRT3 = Math.sqrt(3.0);
	//
	public static final double MATHVAL_HSQRT2 = Math.sqrt(2.0) / 2.0;
	public static final double MATHVAL_HSQRT3 = Math.sqrt(3.0) / 3.0;
	//
	public static final double MATHVAL_2PI = 2.0 * Math.PI;
	public static final double MATHVAL_3HPI = 3.0 * Math.PI / 2.0;
	public static final double MATHVAL_PI = Math.PI;
	public static final double MATHVAL_HPI = Math.PI / 2.0;
	
	/* MENU_OPTIONS
	 */
	//MENU: OSNAP
	public static final String MNU_OSNAP = "Osnap";
	public static final String MNU_OSNAP_LAYERFILTER = "Layer Filter...";
	public static final String MNU_OSNAP_ENDPOINTSNAP = "Endpoint";
	public static final String MNU_OSNAP_MIDPOINTSNAP = "Midpoint";
	public static final String MNU_OSNAP_INTPOINTSNAP = "Intersection";
	public static final String MNU_OSNAP_PERPPOINTSNAP = "Perpendicular";
	public static final String MNU_OSNAP_NEARPOINTSNAP = "Nearest";
	public static final String MNU_OSNAP_CENTERPOINTSNAP = "Center";
	public static final String MNU_OSNAP_QUADPOINTSNAP = "Quadrant";
	public static final String MNU_OSNAP_TANPOINTSNAP = "Tangent";
	public static final String MNU_OSNAP_POINTSNAP = "Point";
	public static final String MNU_OSNAP_INSPOINTSNAP = "Insertion";
	public static final String MNU_OSNAP_PARALLELSNAP = "Parallel";
	public static final String MNU_OSNAP_45DSNAP = "45d";
	public static final String MNU_OSNAP_SNAPOFF = "None";
	public static final String MNU_OSNAP_OBJECTSNAP = "Object Snap...";	
	//MENU: FILE
	public static final String MNU_FILE = "File";
	public static final String MNU_FILE_NEW = "New...";
	public static final String MNU_FILE_OPEN = "Open...";
	public static final String MNU_FILE_CLOSE = "Close";
	public static final String MNU_FILE_SAVE = "Save";
	public static final String MNU_FILE_SAVEAS = "Save As...";
	public static final String MNU_FILE_SETUP = "Setup...";
	public static final String MNU_FILE_PRINT = "Print...";
	public static final String MNU_FILE_PURGEALL = "Purge All...";
	public static final String MNU_FILE_EXIT = "Exit";
	//MENU: EDIT1
	public static final String MNU_EDIT1 = "Edit 1";
	public static final String MNU_EDIT1_UNDO = "Undo";
	public static final String MNU_EDIT1_REDO = "Redo";
	public static final String MNU_EDIT1_SELECTALL = "Select All";
	public static final String MNU_EDIT1_CUT = "Cut";
	public static final String MNU_EDIT1_PAST = "Past";
	public static final String MNU_EDIT1_PAST_SPETIAL = "Past Spetial...";
	public static final String MNU_EDIT1_DELETE = "Delete";
	//MENU: EDIT2
	public static final String MNU_EDIT2 = "Edit 2";
	public static final String MNU_EDIT2_ERASE = "Erase";
	public static final String MNU_EDIT2_COPY = "Copy";
	public static final String MNU_EDIT2_MOVE = "Move";
	public static final String MNU_EDIT2_MIRROR = "Mirror";
	public static final String MNU_EDIT2_STRETCH = "Stretch";
	public static final String MNU_EDIT2_COPYCHANGE = "Copy-Change";
	public static final String MNU_EDIT2_ARRAYRECT = "Array Retangular";
	public static final String MNU_EDIT2_ARRAYPOLAR = "Array Polar";
	public static final String MNU_EDIT2_CHAMFER = "Chamfer";
	public static final String MNU_EDIT2_FILLET = "Fillet";
	public static final String MNU_EDIT2_UNILINE = "Uniline";
	public static final String MNU_EDIT2_SCALE = "Scale";
	public static final String MNU_EDIT2_ROTATE = "Rotate";
	public static final String MNU_EDIT2_BREAK1POINT = "Break 1 Point";
	public static final String MNU_EDIT2_BREAK1POINTSELC = "Break 1 Point Select";
	public static final String MNU_EDIT2_BREAK2POINT = "Break 2 Point";
	public static final String MNU_EDIT2_BREAK2POINTSELC = "Break 2 Point Select";
	public static final String MNU_EDIT2_TRIM = "Trim";
	public static final String MNU_EDIT2_EXTEND = "Extend";
	public static final String MNU_EDIT2_EXPLODE = "Explode";
	public static final String MNU_EDIT2_EXPLODEATTR = "Explode Attribute";
	public static final String MNU_EDIT2_SPLINETOPLINE = "Spline to Polyline";
	public static final String MNU_EDIT2_SPLINETOPLINEALL = "Spline to Polyline All";
	//MENU: ARQ1
	public static final String MNU_ARQ1 = "Arquitetura 1";
	public static final String MNU_ARQ1_PISO = "Piso";
	public static final String MNU_ARQ1_PAREDE = "Parede";
	public static final String MNU_ARQ1_AMBIENTE1 = "Ambiente";
	public static final String MNU_ARQ1_AMBIENTE2 = "Ambiente 2-Paredes";
	public static final String MNU_ARQ1_AMBIENTE3 = "Ambiente 3-Paredes";
	public static final String MNU_ARQ1_MALHA = "Malha";
	public static final String MNU_ARQ1_PORTA = "Porta";
	public static final String MNU_ARQ1_PDUPLA = "Porta Dupla";
	public static final String MNU_ARQ1_JANELA = "Janela";
	public static final String MNU_ARQ1_AREA = "Area";
	//MENU: ARCHITECTURE_BLOCKS
	public static final String MNU_ARCHITECTURE_BLOCKS = "Pontos";
	public static final String MNU_ARCHITECTURE_BLOCKS_BIDE = "Bide";
	public static final String MNU_ARCHITECTURE_BLOCKS_VASO = "Vaso";
	public static final String MNU_ARCHITECTURE_BLOCKS_VASOCAIXAACOPLADA = "Vaso Caixa Acoplada";
	public static final String MNU_ARCHITECTURE_BLOCKS_LAVATORIOGRANDE = "Lavatorio Grande";
	public static final String MNU_ARCHITECTURE_BLOCKS_LAVATORIOPEQUENO = "Lavatorio Pequeno";
	public static final String MNU_ARCHITECTURE_BLOCKS_LAVATORIOBANCA = "Lavatorio para Banca";
	public static final String MNU_ARCHITECTURE_BLOCKS_CHUVEIRO = "Chuveiro";
	public static final String MNU_ARCHITECTURE_BLOCKS_MICTORIO = "Mictorio";
	public static final String MNU_ARCHITECTURE_BLOCKS_PIASIMPLES = "Pia Simples";
	public static final String MNU_ARCHITECTURE_BLOCKS_PIADUPLA = "Pia Dupla";
	public static final String MNU_ARCHITECTURE_BLOCKS_FOGAO4BOCAS = "Fogao 4 Bocas";
	public static final String MNU_ARCHITECTURE_BLOCKS_FOGAO6BOCAS = "Fogao 6 Bocas";
	public static final String MNU_ARCHITECTURE_BLOCKS_GELADEIRA = "Geladeira";
	public static final String MNU_ARCHITECTURE_BLOCKS_LAVADOURAROUPA = "Lavadoura Roupa";
	public static final String MNU_ARCHITECTURE_BLOCKS_TANQUE = "Tanque";
	public static final String MNU_ARCHITECTURE_BLOCKS_AQUECEDOR = "Aquecedor";
	public static final String MNU_ARCHITECTURE_BLOCKS_BOILER = "Boiler";
	public static final String MNU_ARCHITECTURE_BLOCKS_BOMBARECALQUE = "Bomba Recalque";
	public static final String MNU_ARCHITECTURE_BLOCKS_BOMBAAGUASSERVIDAS = "Bomba Aguas Servidas";
	//MENU: ARCHITECTURE_COMMANDS
	public static final String MNU_ARCHITECTURE_COMMANDS = "Arquitetura";
	public static final String MNU_ARCHITECTURE_COMMANDS_PILARRETANGULAR = "Pilar Retangular";
	public static final String MNU_ARCHITECTURE_COMMANDS_PILARCIRCULAR = "Pilar Circular";
	public static final String MNU_ARCHITECTURE_COMMANDS_PAREDE5CM = "Parede 5cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PAREDE10CM = "Parede <10cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_PAREDE15CM = "Parede 15cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PAREDE20CM = "Parede 20cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PAREDE25CM = "Parede 25cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PAREDE = "Parede";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE5CM = "Ambiente 1-Parede 5cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE10CM = "Ambiente 1-Parede <10cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE15CM = "Ambiente 1-Parede 15cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE20CM = "Ambiente 1-Parede 20cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE25CM = "Ambiente 1-Parede 25cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE1PAREDE = "Ambiente 1-Parede";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE5CM = "Ambiente 2-Parede 5cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE10CM = "Ambiente 2-Parede <10cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE15CM = "Ambiente 2-Parede 15cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE20CM = "Ambiente 2-Parede 20cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE25CM = "Ambiente 2-Parede 25cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE2PAREDE = "Ambiente 2-Parede";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE5CM = "Ambiente 3-Parede 5cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE10CM = "Ambiente 3-Parede <10cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE15CM = "Ambiente 3-Parede 15cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE20CM = "Ambiente 3-Parede 20cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE25CM = "Ambiente 3-Parede 25cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_AMBIENTE3PAREDE = "Ambiente 3-Parede";
	public static final String MNU_ARCHITECTURE_COMMANDS_MALHA5CM = "Malha 5cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_MALHA10CM = "Malha <10cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_MALHA15CM = "Malha 15cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_MALHA20CM = "Malha 20cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_MALHA25CM = "Malha 25cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_MALHA = "Malha";
	public static final String MNU_ARCHITECTURE_COMMANDS_BONECA = "Boneca";
	public static final String MNU_ARCHITECTURE_COMMANDS_CORTAPAREDES = "Corta Paredes";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES60CM = "Porta Simples 60cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES70CM = "Porta Simples <70cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES80CM = "Porta Simples 80cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES90CM = "Porta Simples 90cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTASIMPLES = "Porta Simples";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X60CM = "Porta Dupla 2x60cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X70CM = "Porta Dupla <2x70cm>";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X80CM = "Porta Dupla 2x80cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTADUPLA2X90CM = "Porta Dupla 2x90cm";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTADUPLA = "Porta Dupla";
	public static final String MNU_ARCHITECTURE_COMMANDS_PORTACORRER = "Porta Correr";
	public static final String MNU_ARCHITECTURE_COMMANDS_JANELA = "Janela";
	//MENU: BLOCKS
	public static final String MNU_BLOCKS = "Blocks";
	public static final String MNU_BLOCKS_CARIMBO = "Carimbo";
	public static final String MNU_BLOCKS_DETALHESTIPICOS = "Detalhes Tipicos";
	public static final String MNU_BLOCKS_ISOMETRICO = "Isometrico";
	public static final String MNU_BLOCKS_DETALHAMENTO = "Detalhamento";
	public static final String MNU_BLOCKS_INSERTBLOCK = "Insert Block";
	public static final String MNU_BLOCKS_CHANGEBLOCK = "Change Block";
	public static final String MNU_BLOCKS_WBLOCK = "Wblock";
	public static final String MNU_BLOCKS_BLOCK = "block";
	public static final String MNU_BLOCKS_EDITATTR = "Edit Attribute...";
	public static final String MNU_BLOCKS_COPYATTR = "Copy Attribute";
	public static final String MNU_BLOCKS_EDITATTRGLOBALLY = "Edit Attribute Globally";
	public static final String MNU_BLOCKS_DEFINEATTR = "Define Attribute...";
	public static final String MNU_BLOCKS_EXPLODEATTR = "Explode Attribute";
	public static final String MNU_BLOCKS_INSERINDOMARGENS = "Inserindo Margens";
	public static final String MNU_BLOCKS_COLOCANDOCARIMBOS = "Colocando Carimbos";
	public static final String MNU_BLOCKS_VISTAMODELO = "Vistas Modelo";
	public static final String MNU_BLOCKS_XREFMANAGER = "Xref Manager...";
	public static final String MNU_BLOCKS_XREFRELOAD = "Reload Xrefs";
	public static final String MNU_BLOCKS_XREFBIND = "Xref Bind";
	public static final String MNU_BLOCKS_CONTABLOCOS = "Conta Blocos";
	public static final String MNU_BLOCKS_CONTABLOCOSBYNOME = "Conta Blocos pelo Nome";
	public static final String MNU_BLOCKS_CONTABLOCOAREASELEC = "Conta Blocos em uma Area";
	public static final String MNU_BLOCKS_CONTABLOCOSBYNOMEAREASELEC = "Conta Blocos pelo Nome em uma Area";
	//MENU: DRAW1
	public static final String MNU_DRAW1 = "Draw 1";
	public static final String MNU_DRAW1_LINE = "Line";
	public static final String MNU_DRAW1_PLINE = "Polyline";
	public static final String MNU_DRAW1_ARC = "Arc";
	public static final String MNU_DRAW1_ARC3PT = "3 Points";
	public static final String MNU_DRAW1_ARCSCR = "Start-Center-End";
	public static final String MNU_DRAW1_ARCSCA = "Start-Center-Angle";
	public static final String MNU_DRAW1_ARCSCL = "Start-Center-Length";
	public static final String MNU_DRAW1_ARCSEA = "Start-End-Angle";
	public static final String MNU_DRAW1_ARCSED = "Start-End-Direction";
	public static final String MNU_DRAW1_ARCSER = "Start-End-Radius";
	public static final String MNU_DRAW1_ARCCSE = "Center-Start-End";
	public static final String MNU_DRAW1_ARCCSA = "Center-Start-Angle";
	public static final String MNU_DRAW1_ARCCSL = "Center-Start-Length";
	public static final String MNU_DRAW1_ARCCONT = "Continue";
	public static final String MNU_DRAW1_CIRCLE = "Circle";
	public static final String MNU_DRAW1_CIRCLECR = "Center-Radius";
	public static final String MNU_DRAW1_CIRCLECD = "Center-Diameter";
	public static final String MNU_DRAW1_CIRCLE2PT = "2 Points";
	public static final String MNU_DRAW1_CIRCLE3PT = "3 Points";
	public static final String MNU_DRAW1_CIRCLETTR = "Tangent-Tangent-Radius";
	public static final String MNU_DRAW1_CIRCLETTT = "Tangent-Tangent-Tangent";
	public static final String MNU_DRAW1_SPLINE = "Spline";
	public static final String MNU_DRAW1_RAY = "Ray";
	public static final String MNU_DRAW1_INFILINE = "Infinite Line";
	public static final String MNU_DRAW1_FREEHAND = "Freehand";
	public static final String MNU_DRAW1_RECTANGLE = "Rectangle";
	public static final String MNU_DRAW1_DONUTD = "Donut (Inside Diameter=0)";
	public static final String MNU_DRAW1_DONUTR = "Donut";
	public static final String MNU_DRAW1_ELLIPSE = "Ellipse";
	public static final String MNU_DRAW1_ELLIPSEC = "Center";
	public static final String MNU_DRAW1_ELLIPSEAE = "Axis-End";
	public static final String MNU_DRAW1_ELLIPSEA = "Arc";
	public static final String MNU_DRAW1_POLYGON = "Polygon";
	public static final String MNU_DRAW1_POINT = "Point";
	public static final String MNU_DRAW1_TEXT = "Text";
	public static final String MNU_DRAW1_BOX3D = "Box 3D";
	public static final String MNU_DRAW1_OFFSET = "Offset";
	//MENU: DRAW2
	public static final String MNU_DRAW2 = "Draw 2";
	public static final String MNU_DRAW2_OFFSET = "Offset";
	public static final String MNU_DRAW2_OFFSET2_5CM = "Offset 2,5 cm";
	public static final String MNU_DRAW2_OFFSET5CM = "Offset 5 cm";
	public static final String MNU_DRAW2_OFFSET10CM = "Offset 10 cm";
	public static final String MNU_DRAW2_OFFSET15CM = "Offset 15 cm";
	public static final String MNU_DRAW2_OFFSET20CM = "Offset 20 cm";
	public static final String MNU_DRAW2_OFFSET25CM = "Offset 25 cm";
	public static final String MNU_DRAW2_OFFSET30CM = "Offset 30 cm";
	public static final String MNU_DRAW2_OFFSET35CM = "Offset 35 cm";
	public static final String MNU_DRAW2_OFFSET40CM = "Offset 40 cm";
	public static final String MNU_DRAW2_OFFSET45CM = "Offset 45 cm";
	public static final String MNU_DRAW2_OFFSET50CM = "Offset 50 cm";
	public static final String MNU_DRAW2_OFFSET60CM = "Offset 60 cm";
	public static final String MNU_DRAW2_OFFSET70CM = "Offset 70 cm";
	public static final String MNU_DRAW2_OFFSET80CM = "Offset 80 cm";
	public static final String MNU_DRAW2_OFFSET90CM = "Offset 90 cm";
	public static final String MNU_DRAW2_OFFSET100CM = "Offset 100 cm";
	public static final String MNU_DRAW2_OFFSET125CM = "Offset 125 cm";
	public static final String MNU_DRAW2_OFFSET200CM = "Offset 200 cm";
	public static final String MNU_DRAW2_OFFSET250CM = "Offset 250 cm";
	public static final String MNU_DRAW2_OFFSET500CM = "Offset 500 cm";
	public static final String MNU_DRAW2_OFFSET750CM = "Offset 750 cm";
	public static final String MNU_DRAW2_OFFSET1000CM = "Offset 1000 cm";
	public static final String MNU_DRAW2_OFFSETTHROWGHT = "Throught";
	public static final String MNU_DRAW2_OFFSETDIST = "Distance";
	public static final String MNU_DRAW2_OFFSETCHG = "Offset-Change";
	public static final String MNU_DRAW2_OFFSETCHG2_5CM = "2,5 cm";
	public static final String MNU_DRAW2_OFFSETCHG5CM = "5 cm";
	public static final String MNU_DRAW2_OFFSETCHG10CM = "10 cm";
	public static final String MNU_DRAW2_OFFSETCHG15CM = "15 cm";
	public static final String MNU_DRAW2_OFFSETCHG20CM = "20 cm";
	public static final String MNU_DRAW2_OFFSETCHG25CM = "25 cm";
	public static final String MNU_DRAW2_OFFSETCHG30CM = "30 cm";
	public static final String MNU_DRAW2_OFFSETCHG35CM = "35 cm";
	public static final String MNU_DRAW2_OFFSETCHG40CM = "40 cm";
	public static final String MNU_DRAW2_OFFSETCHG45CM = "45 cm";
	public static final String MNU_DRAW2_OFFSETCHG50CM = "50 cm";
	public static final String MNU_DRAW2_OFFSETCHG60CM = "60 cm";
	public static final String MNU_DRAW2_OFFSETCHG70CM = "70 cm";
	public static final String MNU_DRAW2_OFFSETCHG80CM = "80 cm";
	public static final String MNU_DRAW2_OFFSETCHG90CM = "90 cm";
	public static final String MNU_DRAW2_OFFSETCHG100CM = "100 cm";
	public static final String MNU_DRAW2_OFFSETCHG125CM = "125 cm";
	public static final String MNU_DRAW2_OFFSETCHG200CM = "200 cm";
	public static final String MNU_DRAW2_OFFSETCHG250CM = "250 cm";
	public static final String MNU_DRAW2_OFFSETCHG500CM = "500 cm";
	public static final String MNU_DRAW2_OFFSETCHG750CM = "750 cm";
	public static final String MNU_DRAW2_OFFSETCHG1000CM = "1000 cm";
	public static final String MNU_DRAW2_OFFSETCHGTHROWGHT = "Throught";
	public static final String MNU_DRAW2_OFFSETCHGDIST = "Distance";
	public static final String MNU_DRAW2_BHATCH = "Hatch";
	public static final String MNU_DRAW2_PLANE = "Plane";
	public static final String MNU_DRAW2_TRACE = "Trace";
	public static final String MNU_DRAW2_TEXT = "Texto";
	public static final String MNU_DRAW2_TEXTSMALL = "Texto 1,5 mm";
	public static final String MNU_DRAW2_TEXTNORMAL = "Texto <2 mm>";
	public static final String MNU_DRAW2_TEXTMEDIUM = "Texto 3 mm";
	public static final String MNU_DRAW2_TEXTBIG = "Texto 5 mm";
	public static final String MNU_DRAW2_DTEXT = "Texto";
	public static final String MNU_DRAW2_STYLES = "Styles...";
	public static final String MNU_DRAW2_MULTILINETEXT = "Multiline Text...";
	public static final String MNU_DRAW2_SINGLELINETEXT = "Single Text...";
	public static final String MNU_DRAW2_BOUNDARYPLINE = "Boundary Polyline...";
	public static final String MNU_DRAW2_BOUNDARYENTITIES = "Boundary Entities";
	public static final String MNU_DRAW2_DIVIDE = "Divide";
	public static final String MNU_DRAW2_MEASURE = "Measure";
	//MENU: MODIFY
	public static final String MNU_MODIFY = "Modify";
	public static final String MNU_MODIFY_MATCHPROP = "Match Properties";
	public static final String MNU_MODIFY_CHANGE = "Change";
	public static final String MNU_MODIFY_EDITPOLYLINE = "Edit Polyline";
	public static final String MNU_MODIFY_EDITHATCH = "Edit Hatch...";
	public static final String MNU_MODIFY_EDITTEXT = "Edit Text...";
	public static final String MNU_MODIFY_ADJUSTTEXT = "Adjust Text";
	public static final String MNU_MODIFY_CHANGETEXT = "Change Text";
	//MENU: TOOLS
	public static final String MNU_TOOLS = "Tools";
	public static final String MNU_TOOLS_IMPORTTEXTSMALL = "Importa Textos (Pequeno)";
	public static final String MNU_TOOLS_IMPORTTEXTMEDIUM = "Importa Textos (Normal)";
	public static final String MNU_TOOLS_IMPORTTEXTBIG = "Importa Textos (Grande)";
	public static final String MNU_TOOLS_IMPORTTEXT = "Importa Textos";
	public static final String MNU_TOOLS_INDICADORES = "Indicadores";
	public static final String MNU_TOOLS_LEGENDA = "Legenda";
	public static final String MNU_TOOLS_LEADER = "Leader";
	public static final String MNU_TOOLS_APTO01 = "Apartamento 01";
	public static final String MNU_TOOLS_APTO02 = "Apartamento 02";
	public static final String MNU_TOOLS_APTO03 = "Apartamento 03";
	public static final String MNU_TOOLS_APTO04 = "Apartamento 04";
	public static final String MNU_TOOLS_APTO05 = "Apartamento 05";
	public static final String MNU_TOOLS_APTO06 = "Apartamento 06";
	public static final String MNU_TOOLS_APTO07 = "Apartamento 07";
	public static final String MNU_TOOLS_APTO08 = "Apartamento 08";
	public static final String MNU_TOOLS_APTO09 = "Apartamento 09";
	public static final String MNU_TOOLS_APTO10 = "Apartamento 10";
	public static final String MNU_TOOLS_APTO = "Apartamento";
	public static final String MNU_TOOLS_CONSTRUTOR = "Construtor";
	public static final String MNU_TOOLS_PREENCHIMENTO = "Preenchimento";
	public static final String MNU_TOOLS_SERIE = "Serie";
	public static final String MNU_TOOLS_AREA = "Area";
	public static final String MNU_TOOLS_DISTANCE = "Distance";
	public static final String MNU_TOOLS_LIST = "List";
	//MENU: DISPLAY
	public static final String MNU_DISPLAY = "Display";
	public static final String MNU_DISPLAY_REDRAW = "Redraw";
	public static final String MNU_DISPLAY_REGEN = "Regen";
	public static final String MNU_DISPLAY_PAN = "Pan";
	public static final String MNU_DISPLAY_ZOOMRT = "Zoom Real-Time";
	public static final String MNU_DISPLAY_ZOOMIN = "Zoom In";
	public static final String MNU_DISPLAY_ZOOMOUT = "Zoom Out";
	public static final String MNU_DISPLAY_ZOOMWINDOW = "Zoom Window";
	public static final String MNU_DISPLAY_ZOOMDYNAMIC = "Zoom Dynamic";
	public static final String MNU_DISPLAY_ZOOMEXTEND = "Zoom Extend";
	public static final String MNU_DISPLAY_ZOOMPREVIOUS = "Zoom Previous";
	public static final String MNU_DISPLAY_ZOOMALL = "Zoom All";
	public static final String MNU_DISPLAY_ZOOMVIEWMAX = "Zoom View Max";
	public static final String MNU_DISPLAY_ZOOMLEFT = "Zoom Left";
	public static final String MNU_DISPLAY_ZOOMCENTER = "Zoom Center";
	public static final String MNU_DISPLAY_MOVEFORWARD = "Move Forward";
	public static final String MNU_DISPLAY_MOVEBACKWARD = "Move Backward";
	public static final String MNU_DISPLAY_TURNLEFT = "Turn Left";
	public static final String MNU_DISPLAY_TURNRIGHT = "Turn Right";
	public static final String MNU_DISPLAY_RTPAN = "Real-Time Pan";
	public static final String MNU_DISPLAY_RTZOOM = "Real-Time Zoom";
	public static final String MNU_DISPLAY_RTSPHERE = "Real-Time Sphere";
	public static final String MNU_DISPLAY_RTROTX = "Real-Time Rotate-X";
	public static final String MNU_DISPLAY_RTROTY = "Real-Time Rotate-Y";
	public static final String MNU_DISPLAY_PLAN = "Plan View";
	public static final String MNU_DISPLAY_SPLITVIEW = "Split View";
	public static final String MNU_DISPLAY_JOINVIEW = "Join View";
	//MENU: SETTINGS
	public static final String MNU_SETTINGS = "Settings";
	public static final String MNU_SETTINGS_GRIDONOFF = "Grid On/Off";
	public static final String MNU_SETTINGS_SNAPONOFF = "Snap On/Off";
	public static final String MNU_SETTINGS_ORTHOONOFF = "Ortho On/Off";
	public static final String MNU_SETTINGS_DRAWINGAIDS = "Drawing Aids...";
	public static final String MNU_SETTINGS_UCSICONONOFF = "Ucsicon On/Off";
	public static final String MNU_SETTINGS_UCSENTITY = "Ucs Entity";
	public static final String MNU_SETTINGS_UCSORIGIN = "Ucs Origin";
	public static final String MNU_SETTINGS_UCSROTATION = "Ucs Rotation";
	public static final String MNU_SETTINGS_UCSWORLD = "Ucs World";
	public static final String MNU_SETTINGS_MIRRTEXT = "Mirrtext On/Off";
	public static final String MNU_SETTINGS_PICKBOX1 = "Pickbox 1";
	public static final String MNU_SETTINGS_PICKBOX5 = "Pickbox 5";
	public static final String MNU_SETTINGS_DIMDEC3 = "Dim 3-decimais";
	public static final String MNU_SETTINGS_DIMDEC2 = "Dim 2-decimais";
	public static final String MNU_SETTINGS_DIMDEC1 = "Dim 1-decimal";
	public static final String MNU_SETTINGS_DIMDEC0 = "Dim 0-decimal";
	public static final String MNU_SETTINGS_DIMDEC = "Dim decimal";
	public static final String MNU_SETTINGS_OBJECTSNAP = "Object Snap...";
	public static final String MNU_SETTINGS_TBCONFIG = "Toolbars...";
	public static final String MNU_SETTINGS_PROMPTHISTORYWINDOW = "Prompt History Window";
	public static final String MNU_SETTINGS_COMMANDBAR = "Command Bar";
	public static final String MNU_SETTINGS_STATUSBAR = "Status Bar";
	public static final String MNU_SETTINGS_SCROLLBAR = "Scroll Bar";
	//MENU: LAYERS
	public static final String MNU_LAYERS = "Layers";
	public static final String MNU_LAYERS_LAYERSEXPLORER = "Layers Explorer...";
	public static final String MNU_LAYERS_ISOLACAMADA = "Isola Camada";
	public static final String MNU_LAYERS_CONGELACAMADA = "Congela Camada";
	public static final String MNU_LAYERS_CONGELATUDO = "Congela Tudo";
	public static final String MNU_LAYERS_DESCONGELATUDO = "Descongela Tudo";
	public static final String MNU_LAYERS_DESLIGACAMADA = "Desliga Camada";
	public static final String MNU_LAYERS_LIGATUDO = "Liga Tudo";
	public static final String MNU_LAYERS_APAGAOBJCAMADA = "Apaga Objetos da Camada";
	public static final String MNU_LAYERS_EXPLODEPLINECAMADA = "Explode Polylines Camada";
	public static final String MNU_LAYERS_FREEZEARQ = "Arquitetura Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEFOR = "Formas Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEEL = "Eletrica Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEES = "Esgoto Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEH = "Hidraulica Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEINC = "Incendio Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEG = "Gas Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZETE = "Telefone Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEIE = "Instalacoes Especiais Freeze/Thaw";
	public static final String MNU_LAYERS_FREEZEAR = "Ar Condicionado Freeze/Thaw";
	public static final String MNU_LAYERS_CONGELAINSTALACOES = "Congela Instalacoes";
	public static final String MNU_LAYERS_DESCONGELAINSTALACOES = "Descongela Instalacoes";
	public static final String MNU_LAYERS_TRANCACAMADA = "Tranca Camada";
	public static final String MNU_LAYERS_DESTRANCACAMADA = "Destranca Camada";
	//MENU: MENUS
	public static final String MNU_MENUS = "Menus";
	public static final String MNU_MENUS_ARQMENU = "Arquitetura";
	public static final String MNU_MENUS_FORMENU = "Formas";
	public static final String MNU_MENUS_FUMENU = "Furacao";
	public static final String MNU_MENUS_EEMENU = "Entrada Energia";
	public static final String MNU_MENUS_ELMENU = "Eletrica";
	public static final String MNU_MENUS_ESMENU = "Esgoto";
	public static final String MNU_MENUS_HMENU = "Hidraulica";
	public static final String MNU_MENUS_INCMENU = "Incendio";
	public static final String MNU_MENUS_GMENU = "Gas";
	public static final String MNU_MENUS_IEMENU = "Instalacoes Especiais";
	public static final String MNU_MENUS_TEMENU = "Telefonia";
	public static final String MNU_MENUS_ARMENU = "Ar Condicionado";
	//MENU: INQUIRY
	public static final String MNU_INQUIRY = "Inquiry";
	public static final String MNU_INQUIRY_AREA = "Area";
	public static final String MNU_INQUIRY_DIST = "Distance";
	public static final String MNU_INQUIRY_LIST = "List";
	public static final String MNU_INQUIRY_CONTABLOCOS = "Conta Blocos";
	//MENU: WINDOW
	public static final String MNU_WINDOW = "Window";
	public static final String MNU_WINDOW_CASCADE = "Cascade";
	public static final String MNU_WINDOW_TILEHORIZ = "Tile Horizontally";
	public static final String MNU_WINDOW_TILEVERT = "Tile Vertically";
	//MENU: HELP
	public static final String MNU_HELP = "Help";
	public static final String MNU_HELP_ABOUT = "About AICADx...";
	public static final String MNU_HELP_CONTENTS = "Contents...";
	public static final String MNU_HELP_MAIL = "Envio de Arquivo";

	/* TOOLBARMENU_OPTIONS
	 */
	//ICOMENU: FILE
	public static final String ICOMNU_FILE_NEW = "/br/com/tlmv/aicadxapp/res/cad/ico_file_new.jpg";
	public static final String ICOMNU_FILE_OPEN = "/br/com/tlmv/aicadxapp/res/cad/ico_file_open.jpg";
	public static final String ICOMNU_FILE_CLOSE = "/br/com/tlmv/aicadxapp/res/cad/ico_file_close.jpg";
	public static final String ICOMNU_FILE_SAVE = "/br/com/tlmv/aicadxapp/res/cad/ico_file_save.jpg";
	public static final String ICOMNU_FILE_SAVEAS = "/br/com/tlmv/aicadxapp/res/cad/ico_file_saveas.jpg";
	public static final String ICOMNU_FILE_PRINT = "/br/com/tlmv/aicadxapp/res/cad/ico_file_print.jpg";
	public static final String ICOMNU_FILE_EXIT = "/br/com/tlmv/aicadxapp/res/cad/ico_file_exit.jpg";
	//ICOMENU: ARQ1
	public static final String ICONMNU_ARQ1_PISO = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_piso.jpg";
	public static final String ICONMNU_ARQ1_PAREDE = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_parede.jpg";
	public static final String ICONMNU_ARQ1_AMBIENTE1 = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_ambiente1.jpg";
	public static final String ICONMNU_ARQ1_AMBIENTE2 = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_ambiente2.jpg";
	public static final String ICONMNU_ARQ1_AMBIENTE3 = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_ambiente3.jpg";
	public static final String ICONMNU_ARQ1_MALHA = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_malha.jpg";
	public static final String ICONMNU_ARQ1_PORTA = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_porta.jpg";
	public static final String ICONMNU_ARQ1_PDUPLA = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_pdupla.jpg";
	public static final String ICONMNU_ARQ1_JANELA = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_janela.jpg";
	public static final String ICONMNU_ARQ1_AREA = "/br/com/tlmv/aicadxapp/res/cad/ico_arq_area.jpg";
	//ICOMENU: DRAW1
	public static final String ICOMNU_DRAW1_LINE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_line.jpg";
	public static final String ICOMNU_DRAW1_ARC = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_arc_asca.jpg";
	public static final String ICOMNU_DRAW1_CIRCLE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_circle_cr.jpg";
	public static final String ICOMNU_DRAW1_RECTANGLE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_rectangle.jpg";
	public static final String ICOMNU_DRAW1_POLYGON = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_polygon.jpg";
	public static final String ICOMNU_DRAW1_POINT = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_point.jpg";
	public static final String ICOMNU_DRAW1_TEXT = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_text.jpg";
	public static final String ICOMNU_DRAW1_BOX3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_box3d.jpg";
	public static final String ICOMNU_DRAW1_OFFSET = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_offset.jpg";
	public static final String ICOMNU_DRAW1_AREATABLE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_areatable.jpg";
	//ICOMENU: EDIT2
	public static final String ICOMNU_EDIT2_ERASE = "/br/com/tlmv/aicadxapp/res/cad/ico_edit2_erase.jpg";
	public static final String ICOMNU_EDIT2_COPY = "/br/com/tlmv/aicadxapp/res/cad/ico_edit2_copy.jpg";
	public static final String ICOMNU_EDIT2_MIRROR = "/br/com/tlmv/aicadxapp/res/cad/ico_edit2_mirror.jpg";
	public static final String ICOMNU_EDIT2_MOVE = "/br/com/tlmv/aicadxapp/res/cad/ico_edit2_move.jpg";
	public static final String ICOMNU_EDIT2_SCALE = "/br/com/tlmv/aicadxapp/res/cad/ico_edit2_scale.jpg";
	public static final String ICOMNU_EDIT2_ROTATE = "/br/com/tlmv/aicadxapp/res/cad/ico_edit2_rotate.jpg";
	//ICOMENU: ZOOM
	public static final String ICOMNU_ZOOM_PAN = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_pan.jpg";
	public static final String ICOMNU_ZOOM_ZOOMIN = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_zoomin.jpg";
	public static final String ICOMNU_ZOOM_ZOOMOUT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_zoomout.jpg";
	public static final String ICOMNU_ZOOM_ZOOMWINDOW = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_zoomwindow.jpg";
	public static final String ICOMNU_ZOOM_ZOOMEXT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_zoomext.jpg";
	public static final String ICOMNU_ZOOM_MOVEFORWARD = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_moveforward.jpg";
	public static final String ICOMNU_ZOOM_MOVEBACKWARD = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_movebackward.jpg";
	public static final String ICOMNU_ZOOM_TURNLEFT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_turnleft.jpg";
	public static final String ICOMNU_ZOOM_TURNRIGHT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_turnright.jpg";
	//ICOMENU: SETTINGS
	public static final String ICOMNU_SETTINGS_GRIDONOFF = "/br/com/tlmv/aicadxapp/res/cad/ico_settings_gridonoff.jpg";
	public static final String ICOMNU_SETTINGS_SNAPONOFF = "/br/com/tlmv/aicadxapp/res/cad/ico_settings_snaponoff.jpg";
	public static final String ICOMNU_SETTINGS_ORTHOONOFF = "/br/com/tlmv/aicadxapp/res/cad/ico_settings_orthoonoff.jpg";
	//ICOMENU: LAYERS
	public static final String ICOMNU_LAYERS_LAYEREXPLORER = "/br/com/tlmv/aicadxapp/res/cad/ico_layers_layerexplorer.jpg";
	//ICOMENU: INQUIRY
	public static final String ICOMNU_INQUIRY_DISTANCE = "/br/com/tlmv/aicadxapp/res/cad/ico_inquiry_distance.jpg";
	//ICOMENU: HELP
	public static final String ICOMNU_HELP_ABOUT = "/br/com/tlmv/aicadxapp/res/cad/ico_help_about.jpg";
	
	/* TOOLBARMENU_OPTIONS
	 */
	//TOOLTIP_ICOMENU: FILE
	public static final String TOOLTIP_ICOMNU_FILE_NEW = "New...";
	public static final String TOOLTIP_ICOMNU_FILE_OPEN = "Open...";
	public static final String TOOLTIP_ICOMNU_FILE_CLOSE = "Close";
	public static final String TOOLTIP_ICOMNU_FILE_SAVE = "Save";
	public static final String TOOLTIP_ICOMNU_FILE_SAVEAS = "Save As...";
	public static final String TOOLTIP_ICOMNU_FILE_PRINT = "Print...";
	public static final String TOOLTIP_ICOMNU_FILE_EXIT = "Exit";
	//ICOMENU: ARQ1
	public static final String TOOLTIP_ICOMNU_ARQ1_PISO = "Piso";
	public static final String TOOLTIP_ICOMNU_ARQ1_PAREDE = "Parede";
	public static final String TOOLTIP_ICOMNU_ARQ1_AMBIENTE1 = "Ambiente";
	public static final String TOOLTIP_ICOMNU_ARQ1_AMBIENTE2 = "Ambiente 2-Paredes";
	public static final String TOOLTIP_ICOMNU_ARQ1_AMBIENTE3 = "Ambiente 3-Paredes";
	public static final String TOOLTIP_ICOMNU_ARQ1_MALHA = "Malha";
	public static final String TOOLTIP_ICOMNU_ARQ1_PORTA = "Porta";
	public static final String TOOLTIP_ICOMNU_ARQ1_PDUPLA = "Porta Dupla";
	public static final String TOOLTIP_ICOMNU_ARQ1_JANELA = "Janela";
	public static final String TOOLTIP_ICOMNU_ARQ1_AREA = "Area";
	//TOOLTIP_ICOMENU: DRAW1
	public static final String TOOLTIP_ICOMNU_DRAW1_LINE = "Line";
	public static final String TOOLTIP_ICOMNU_DRAW1_ARC = "Arc";
	public static final String TOOLTIP_ICOMNU_DRAW1_CIRCLE = "Circle";
	public static final String TOOLTIP_ICOMNU_DRAW1_RECTANGLE = "Rectangle";
	public static final String TOOLTIP_ICOMNU_DRAW1_POLYGON = "Polygon";
	public static final String TOOLTIP_ICOMNU_DRAW1_POINT = "Point";
	public static final String TOOLTIP_ICOMNU_DRAW1_TEXT = "Text";
	public static final String TOOLTIP_ICOMNU_DRAW1_BOX3D = "Box 3D";
	public static final String TOOLTIP_ICOMNU_DRAW1_OFFSET = "Offset";
	public static final String TOOLTIP_ICOMNU_DRAW1_AREATABLE = "Area Table";
	//ICOMENU: EDIT2
	public static final String TOOLTIP_ICOMNU_EDIT2_ERASE = "Erase";
	public static final String TOOLTIP_ICOMNU_EDIT2_COPY = "Copy";
	public static final String TOOLTIP_ICOMNU_EDIT2_MIRROR = "Mirror";
	public static final String TOOLTIP_ICOMNU_EDIT2_MOVE = "Move";
	public static final String TOOLTIP_ICOMNU_EDIT2_SCALE = "Scale";
	public static final String TOOLTIP_ICOMNU_EDIT2_ROTATE = "Rotate";
	//TOOLTIP_ICOMENU: ZOOM
	public static final String TOOLTIP_ICOMNU_ZOOM_PAN = "Pan";
	public static final String TOOLTIP_ICOMNU_ZOOM_ZOOMIN = "Zoom In";
	public static final String TOOLTIP_ICOMNU_ZOOM_ZOOMOUT = "Zoom Out";
	public static final String TOOLTIP_ICOMNU_ZOOM_ZOOMWINDOW = "Zoom Window";
	public static final String TOOLTIP_ICOMNU_ZOOM_ZOOMEXT = "Zoom Extends";
	public static final String TOOLTIP_ICOMNU_ZOOM_MOVEFORWARD = "Move Forward";
	public static final String TOOLTIP_ICOMNU_ZOOM_MOVEBACKWARD = "Move Backward";
	public static final String TOOLTIP_ICOMNU_ZOOM_TURNLEFT = "Turn Left";
	public static final String TOOLTIP_ICOMNU_ZOOM_TURNRIGHT = "Turn Right";
	//TOOLTIP_ICOMENU: SETTINGS
	public static final String TOOLTIP_ICOMNU_SETTINGS_GRIDONOFF = "Grid On/Off";
	public static final String TOOLTIP_ICOMNU_SETTINGS_SNAPONOFF = "Snap On/Off";
	public static final String TOOLTIP_ICOMNU_SETTINGS_ORTHOONOFF = "Ortho On/Off";
	//TOOLTIP_ICOMENU: LAYERS
	public static final String TOOLTIP_ICONMENU_LAYERS_LAYEREXPLORER = "Layers...";
	//TOOLTIP_ICOMENU: INQUIRY
	public static final String TOOLTIP_ICOMNU_INQUIRY_DISTANCE = "Distance";
	//TOOLTIP_ICOMENU: HELP
	public static final String TOOLTIP_ICOMNU_HELP_ABOUT = "About AICADx...";
	
	/* ACTIONS COMMAND
	 */
	public static final String ACTION_NONE = "-1";
	//ACTIONS: FILE
	public static final String ACTION_FILE_NEW = "_NEW_";
	public static final String ACTION_FILE_OPEN = "_OPEN_";
	public static final String ACTION_FILE_CLOSE = "_CLOSE_";
	public static final String ACTION_FILE_SAVE = "_SAVE_";
	public static final String ACTION_FILE_SAVEAS = "_SAVEAS_";
	public static final String ACTION_FILE_PRINT = "_PRINT_";
	public static final String ACTION_FILE_EXIT = "_EXIT_";
	//ICOMENU: ARQ1
	public static final String ACTION_ARQ1_PAREDE = "_PAREDE_";
	public static final String ACTION_ARQ1_AMBIENTE1 = "_AMBIENTE1_";
	public static final String ACTION_ARQ1_AMBIENTE2 = "_AMBIENTE2_";
	public static final String ACTION_ARQ1_AMBIENTE3 = "AMBIENTE3_";
	public static final String ACTION_ARQ1_MALHA = "_MALHA_";
	public static final String ACTION_ARQ1_PORTA = "_PORTA_";
	public static final String ACTION_ARQ1_PDUPLA = "_PORTADUPLA_";
	public static final String ACTION_ARQ1_JANELA = "_JANELA_";
	public static final String ACTION_ARQ1_PISO = "_PISO_";
	public static final String ACTION_ARQ1_AREA = "_AREA_";
	//ACTIONS: DRAW1
	public static final String ACTION_DRAW1_LINE = "_LINE_";
	public static final String ACTION_DRAW1_ARCSCA = "_ARCSCA_";
	public static final String ACTION_DRAW1_CIRCLECR = "_CIRCLECR_";
	public static final String ACTION_DRAW1_RECTANGLE = "_RECTANGLE_";
	public static final String ACTION_DRAW1_POLYGON = "_POLYGON_";
	public static final String ACTION_DRAW1_POINT = "_POINT_";
	public static final String ACTION_DRAW1_TEXT = "_TEXT_";
	public static final String ACTION_DRAW1_AREATABLE = "_AREATABLE_";
	public static final String ACTION_DRAW1_BOX3D = "_BOX3D_";
	public static final String ACTION_DRAW1_OFFSET = "_OFFSET_";
	//ACTIONS: EDIT2
	public static final String ACTION_EDIT2_ERASE = "_ERASE_";
	public static final String ACTION_EDIT2_COPY = "_COPY_";
	public static final String ACTION_EDIT2_MIRROR = "_MIRROR_";
	public static final String ACTION_EDIT2_MOVE = "_MOVE_";
	public static final String ACTION_EDIT2_SCALE = "_SCALE_";
	public static final String ACTION_EDIT2_ROTATE = "_ROTATE_";
	//ACTIONS: ZOOM
	public static final String ACTION_ZOOM_PAN = "_PAN_";
	public static final String ACTION_ZOOM_ZOOMIN = "_ZOOMIN_";
	public static final String ACTION_ZOOM_ZOOMOUT = "_ZOOMOUT_";
	public static final String ACTION_ZOOM_ZOOMWINDOW = "_ZOOMWINDOW_";
	public static final String ACTION_ZOOM_ZOOMEXT = "_ZOOMEXT_";
	public static final String ACTION_ZOOM_MOVEFORWARD = "_MOVEFORWARD_";
	public static final String ACTION_ZOOM_MOVEBACKWARD = "_MOVEBACKWARD_";
	public static final String ACTION_ZOOM_TURNLEFT = "_TURNLEFT_";
	public static final String ACTION_ZOOM_TURNRIGHT = "_TURNRIGHT_";
	//TOOLTIP_ICOMENU: SETTINGS
	public static final String ACTION_SETTINGS_GRIDONOFF = "_GRIDONOFF_";
	public static final String ACTION_SETTINGS_SNAPONOFF = "_SNAPONOFF_";
	public static final String ACTION_SETTINGS_ORTHOONOFF = "_ORTHOONOFF_";
	//TOOLTIP_ICOMENU: LAYERS
	public static final String ACTION_LAYERS_LAYEREXPLORER = "_LAYEREXPLORER_";
	//ACTIONS: INQUIRY
	public static final String ACTION_INQUIRY_DIST = "_DIST_";
	//ACTIONS: HELP
	public static final String ACTION_HELP_ABOUT = "_ABOUT_";
	
	/*
	 * PAREDES
	 */

	//TIPOS_PAREDE
	//
	public static final int WALLTYPE_BASICA = 1001;
	public static final int WALLTYPE_ALVENARIA = 1002;
	public static final int WALLTYPE_DRYWALL = 1003;
	public static final int WALLTYPE_PLACAS_CIMENTICIAS = 1004;
	public static final int WALLTYPE_DIVISORIA_VIDRO = 1005;
	public static final int WALLTYPE_DIVISORIA_NAVAL = 1006;

	//ESPESURAS_PAREDE
	//
	public static final double WALLWIDTH_3CM = 0.025;
	public static final double WALLWIDTH_5CM = 0.045;
	public static final double WALLWIDTH_10CM = 0.075;
	public static final double WALLWIDTH_15CM = 0.125;
	public static final double WALLWIDTH_20CM = 0.175;
	public static final double WALLWIDTH_25CM = 0.225;
	public static final double WALLWIDTH_30CM = 0.275;
	public static final double WALLWIDTH_50CM = 0.475;

	//ALTURAS_PAREDE
	//
	public static final double WALLHEIGHT_5CM = 0.5;
	public static final double WALLHEIGHT_10CM = 0.1;
	public static final double WALLHEIGHT_15CM = 0.15;
	public static final double WALLHEIGHT_20CM = 0.2;
	public static final double WALLHEIGHT_25CM = 0.25;
	public static final double WALLHEIGHT_30CM = 0.3;
	public static final double WALLHEIGHT_35CM = 0.35;
	public static final double WALLHEIGHT_40CM = 0.4;
	public static final double WALLHEIGHT_45CM = 0.45;
	//
	public static final double WALLHEIGHT_50CM = 0.5;
	public static final double WALLHEIGHT_60CM = 0.6;
	public static final double WALLHEIGHT_70CM = 0.7;
	public static final double WALLHEIGHT_80CM = 0.8;
	public static final double WALLHEIGHT_90CM = 0.9;
	//
	public static final double WALLHEIGHT_100CM = 1.0;
	public static final double WALLHEIGHT_110CM = 1.1;
	public static final double WALLHEIGHT_120CM = 1.2;
	public static final double WALLHEIGHT_130CM = 1.3;
	public static final double WALLHEIGHT_140CM = 1.4;
	public static final double WALLHEIGHT_150CM = 1.5;
	//
	public static final double WALLHEIGHT_200CM = 2.0;
	public static final double WALLHEIGHT_210CM = 2.1;
	public static final double WALLHEIGHT_220CM = 2.2;
	public static final double WALLHEIGHT_230CM = 2.3;
	public static final double WALLHEIGHT_240CM = 2.4;
	public static final double WALLHEIGHT_250CM = 2.5;
	//
	public static final double WALLHEIGHT_300CM = 3.0;
	
	//TIPOS_ACABAMENTO_PAREDE
	//
	public static final int WALLFINISH_BASICA = 2001;
	public static final int WALLFINISH_CHAPISCO = 2002;
	public static final int WALLFINISH_CHAPISCO_EMBOCO_PINTURA = 2003;
	public static final int WALLFINISH_CHAPISCO_EMBOCO_REBOCO_PINTURA = 2004;
	public static final int WALLFINISH_CHAPISCO_EMBOCO_REBOCO_CERAMICA = 2005;

	//ESPESURAS_ACABAMENTO_PAREDE
	//
	public static final double WALLFINISH_0MM = 0.0;
	public static final double WALLFINISH_2MM = 0.0025;
	public static final double WALLFINISH_5MM = 0.005;
	public static final double WALLFINISH_7MM = 0.0075;
	public static final double WALLFINISH_10MM = 0.01;
	public static final double WALLFINISH_12MM = 0.0125;
	public static final double WALLFINISH_15MM = 0.015;

	/*
	 * PORTAS
	 */

	//TIPOS_PORTA
	//
	public static final int DOORTYPE_BASICA = 3001;
	public static final int DOORTYPE_MADEIRA = 3002;
	public static final int DOORTYPE_FERRO = 3003;
	public static final int DOORTYPE_VIDRO = 3004;

	//ESPESURAS_PORTA
	//
	public static final double DOORWEIGHT_10MM = 0.01;
	public static final double DOORWEIGHT_20MM = 0.02;
	public static final double DOORWEIGHT_30MM = 0.03;
	public static final double DOORWEIGHT_40MM = 0.04;
	public static final double DOORWEIGHT_50MM = 0.05;

	//LARGURAS_PORTA
	//
	public static final double DOORWIDTH_50CM = 0.5;
	public static final double DOORWIDTH_55CM = 0.55;
	public static final double DOORWIDTH_60CM = 0.6;
	public static final double DOORWIDTH_65CM = 0.65;
	public static final double DOORWIDTH_70CM = 0.7;
	public static final double DOORWIDTH_75CM = 0.75;
	public static final double DOORWIDTH_80CM = 0.8;
	public static final double DOORWIDTH_85CM = 0.85;
	public static final double DOORWIDTH_90CM = 0.9;
	public static final double DOORWIDTH_95CM = 0.95;
	public static final double DOORWIDTH_100CM = 1.0;
	//
	public static final double DOORWIDTH_120CM = 1.2;
	public static final double DOORWIDTH_140CM = 1.4;
	public static final double DOORWIDTH_160CM = 1.6;
	public static final double DOORWIDTH_180CM = 1.8;

	//ALTURAS_PORTA
	//
	public static final double DOORHEIGHT_50CM = 0.5;
	public static final double DOORHEIGHT_55CM = 0.55;
	public static final double DOORHEIGHT_60CM = 0.6;
	public static final double DOORHEIGHT_65CM = 0.65;
	public static final double DOORHEIGHT_70CM = 0.7;
	public static final double DOORHEIGHT_75CM = 0.75;
	public static final double DOORHEIGHT_80CM = 0.8;
	public static final double DOORHEIGHT_85CM = 0.85;
	public static final double DOORHEIGHT_90CM = 0.9;
	public static final double DOORHEIGHT_95CM = 0.95;
	//
	public static final double DOORHEIGHT_100CM = 1.0;
	public static final double DOORHEIGHT_110CM = 1.1;
	public static final double DOORHEIGHT_120CM = 1.2;
	public static final double DOORHEIGHT_130CM = 1.3;
	public static final double DOORHEIGHT_140CM = 1.4;
	public static final double DOORHEIGHT_150CM = 1.5;
	public static final double DOORHEIGHT_160CM = 1.6;
	public static final double DOORHEIGHT_170CM = 1.7;
	public static final double DOORHEIGHT_180CM = 1.8;
	public static final double DOORHEIGHT_190CM = 1.9;
	//
	public static final double DOORHEIGHT_200CM = 2.0;
	public static final double DOORHEIGHT_210CM = 2.1;
	
	//DIRECAO_ABERTURA_PORTA
	//
	public static final int DOORDIR_PT0 = 0;
	public static final int DOORDIR_PT1 = 1;
	public static final int DOORDIR_PT2 = 2;
	public static final int DOORDIR_PT3 = 3;
	
	//TIPOS_ACABAMENTO_PORTA
	//
	public static final int DOORFINISH_BASICA = 4001;
	public static final int DOORFINISH_MADEIRA = 4002;
	public static final int DOORFINISH_FERRO = 4003;
	public static final int DOORFINISH_VIDRO = 4004;

	//ESPESURA_BATENTE_ACABAMENTO_PORTA
	//
	public static final double DOORFINISH_BATENTE_WEIGHT_0MM = 0.0;
	public static final double DOORFINISH_BATENTE_WEIGHT_5MM = 0.005;
	public static final double DOORFINISH_BATENTE_WEIGHT_10MM = 0.01;
	public static final double DOORFINISH_BATENTE_WEIGHT_15MM = 0.015;
	public static final double DOORFINISH_BATENTE_WEIGHT_20MM = 0.02;
	public static final double DOORFINISH_BATENTE_WEIGHT_25MM = 0.025;
	public static final double DOORFINISH_BATENTE_WEIGHT_30MM = 0.03;

	//LARGURA_GUARNICOES_ACABAMENTO_PORTA
	//
	public static final double DOORFINISH_GUARNICAO_WIDTH_10MM = 0.01;
	public static final double DOORFINISH_GUARNICAO_WIDTH_15MM = 0.015;
	public static final double DOORFINISH_GUARNICAO_WIDTH_20MM = 0.02;
	public static final double DOORFINISH_GUARNICAO_WIDTH_25MM = 0.025;
	public static final double DOORFINISH_GUARNICAO_WIDTH_30MM = 0.03;
	public static final double DOORFINISH_GUARNICAO_WIDTH_35MM = 0.035;
	public static final double DOORFINISH_GUARNICAO_WIDTH_40MM = 0.04;
	public static final double DOORFINISH_GUARNICAO_WIDTH_45MM = 0.045;
	public static final double DOORFINISH_GUARNICAO_WIDTH_50MM = 0.05;

	//ALTURA_GUARNICOES_ACABAMENTO_PORTA
	//
	public static final double DOORFINISH_GUARNICAO_HEIGHT_10MM = 0.01;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_15MM = 0.015;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_20MM = 0.02;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_25MM = 0.025;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_30MM = 0.03;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_35MM = 0.035;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_40MM = 0.04;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_45MM = 0.045;
	public static final double DOORFINISH_GUARNICAO_HEIGHT_50MM = 0.05;

	//ESPESURA_GUARNICOES_ACABAMENTO_PORTA
	//
	public static final double DOORFINISH_GUARNICAO_WEIGHT_0MM = 0.0;
	public static final double DOORFINISH_GUARNICAO_WEIGHT_5MM = 0.005;
	public static final double DOORFINISH_GUARNICAO_WEIGHT_10MM = 0.01;
	public static final double DOORFINISH_GUARNICAO_WEIGHT_15MM = 0.015;
	public static final double DOORFINISH_GUARNICAO_WEIGHT_20MM = 0.02;
	
	/*
	 * JANELAS
	 */
	
	//TIPOS_JANELA
	//
	public static final int WINDOWTYPE_BASICA = 3001;
	public static final int WINDOWTYPE_MADEIRA = 3002;
	public static final int WINDOWTYPE_FERRO = 3003;
	public static final int WINDOWTYPE_VIDRO = 3004;

	//ESPESURAS_JANELA
	//
	public static final double WINDOWWEIGHT_5MM = 0.005;
	public static final double WINDOWWEIGHT_10MM = 0.01;
	public static final double WINDOWWEIGHT_15MM = 0.015;
	public static final double WINDOWWEIGHT_20MM = 0.02;
	public static final double WINDOWWEIGHT_25MM = 0.025;

	//LARGURAS_JANELA
	//
	public static final double WINDOWWIDTH_60CM = 0.6;
	public static final double WINDOWWIDTH_70CM = 0.7;
	public static final double WINDOWWIDTH_80CM = 0.8;
	public static final double WINDOWWIDTH_90CM = 0.9;
	//
	public static final double WINDOWWIDTH_100CM = 1.0;
	public static final double WINDOWWIDTH_110CM = 1.1;
	public static final double WINDOWWIDTH_120CM = 1.2;
	public static final double WINDOWWIDTH_130CM = 1.3;
	public static final double WINDOWWIDTH_140CM = 1.4;
	public static final double WINDOWWIDTH_150CM = 1.5;
	public static final double WINDOWWIDTH_160CM = 1.6;
	public static final double WINDOWWIDTH_170CM = 1.7;
	public static final double WINDOWWIDTH_180CM = 1.8;
	public static final double WINDOWWIDTH_190CM = 1.9;
	public static final double WINDOWWIDTH_200CM = 2.0;

	//ALTURAS_JANELA
	//
	public static final double WINDOWHEIGHT_60CM = 0.6;
	public static final double WINDOWHEIGHT_70CM = 0.7;
	public static final double WINDOWHEIGHT_80CM = 0.8;
	public static final double WINDOWHEIGHT_90CM = 0.9;
	//
	public static final double WINDOWHEIGHT_100CM = 1.0;
	public static final double WINDOWHEIGHT_110CM = 1.1;
	public static final double WINDOWHEIGHT_120CM = 1.2;
	public static final double WINDOWHEIGHT_130CM = 1.3;
	public static final double WINDOWHEIGHT_140CM = 1.4;
	public static final double WINDOWHEIGHT_150CM = 1.5;
	
	//ALTURAS_PISO_JANELA
	//
	public static final double WINDOWFLOORHEIGHT_30CM = 0.3;
	public static final double WINDOWFLOORHEIGHT_40CM = 0.4;
	public static final double WINDOWFLOORHEIGHT_50CM = 0.5;
	public static final double WINDOWFLOORHEIGHT_60CM = 0.6;
	public static final double WINDOWFLOORHEIGHT_70CM = 0.7;
	public static final double WINDOWFLOORHEIGHT_80CM = 0.8;
	public static final double WINDOWFLOORHEIGHT_90CM = 0.9;
	//
	public static final double WINDOWFLOORHEIGHT_100CM = 1.0;
	public static final double WINDOWFLOORHEIGHT_110CM = 1.1;
	public static final double WINDOWFLOORHEIGHT_120CM = 1.2;
	public static final double WINDOWFLOORHEIGHT_130CM = 1.3;
	public static final double WINDOWFLOORHEIGHT_140CM = 1.4;
	public static final double WINDOWFLOORHEIGHT_150CM = 1.5;

	//DIRECAO_ABERTURA_JANELA
	//
	public static final int WINDOWDIR_PT0 = 0;
	public static final int WINDOWDIR_PT1 = 1;
	
	//TIPOS_ACABAMENTO_JANELA
	//
	public static final int WINDOWFINISH_BASICA = 4001;
	public static final int WINDOWFINISH_MADEIRA = 4002;
	public static final int WINDOWFINISH_FERRO = 4003;
	public static final int WINDOWFINISH_VIDRO = 4004;

	//ESPESURA_BATENTE_ACABAMENTO_JANELA
	//
	public static final double WINDOWFINISH_BATENTE_WEIGHT_0MM = 0.0;
	public static final double WINDOWFINISH_BATENTE_WEIGHT_5MM = 0.005;
	public static final double WINDOWFINISH_BATENTE_WEIGHT_10MM = 0.01;
	public static final double WINDOWFINISH_BATENTE_WEIGHT_15MM = 0.015;
	public static final double WINDOWFINISH_BATENTE_WEIGHT_20MM = 0.02;
	public static final double WINDOWFINISH_BATENTE_WEIGHT_25MM = 0.025;
	public static final double WINDOWFINISH_BATENTE_WEIGHT_30MM = 0.03;

	//LARGURA_GUARNICOES_ACABAMENTO_JANELA
	//
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_10MM = 0.01;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_15MM = 0.015;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_20MM = 0.02;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_25MM = 0.025;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_30MM = 0.03;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_35MM = 0.035;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_40MM = 0.04;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_45MM = 0.045;
	public static final double WINDOWFINISH_GUARNICAO_WIDTH_50MM = 0.05;

	//ALTURA_GUARNICOES_ACABAMENTO_JANELA
	//
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_10MM = 0.01;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_15MM = 0.015;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_20MM = 0.02;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_25MM = 0.025;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_30MM = 0.03;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_35MM = 0.035;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_40MM = 0.04;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_45MM = 0.045;
	public static final double WINDOWFINISH_GUARNICAO_HEIGHT_50MM = 0.05;

	//ESPESURA_GUARNICOES_ACABAMENTO_JANELA
	//
	public static final double WINDOWFINISH_GUARNICAO_WEIGHT_0MM = 0.0;
	public static final double WINDOWFINISH_GUARNICAO_WEIGHT_5MM = 0.005;
	public static final double WINDOWFINISH_GUARNICAO_WEIGHT_10MM = 0.01;
	public static final double WINDOWFINISH_GUARNICAO_WEIGHT_15MM = 0.015;
	public static final double WINDOWFINISH_GUARNICAO_WEIGHT_20MM = 0.02;
		
	/*
	 * PISOS
	 */

	//TIPOS_PISO
	//
	public static final int FLOORTYPE_BASICA = 1001;
	public static final int FLOORTYPE_MADEIRA = 1002;
	public static final int FLOORTYPE_TACO = 1003;
	public static final int FLOORTYPE_CERAMICA = 1004;
	public static final int FLOORTYPE_CIMENTO = 1005;
	public static final int FLOORTYPE_ASFALTO = 1006;

	//ESPESURAS_PISO
	//
	public static final double FLOORWIDTH_3CM = 0.025;
	public static final double FLOORWIDTH_5CM = 0.045;
	public static final double FLOORWIDTH_10CM = 0.075;
	public static final double FLOORWIDTH_15CM = 0.125;
	public static final double FLOORWIDTH_20CM = 0.175;
	public static final double FLOORWIDTH_25CM = 0.225;
	public static final double FLOORWIDTH_30CM = 0.275;
	public static final double FLOORWIDTH_50CM = 0.475;

	//ALTURAS_PISO
	//
	public static final double FLOORHEIGHT_5CM = 0.5;
	public static final double FLOORHEIGHT_10CM = 0.1;
	public static final double FLOORHEIGHT_15CM = 0.15;
	public static final double FLOORHEIGHT_20CM = 0.2;
	public static final double FLOORHEIGHT_25CM = 0.25;
	public static final double FLOORHEIGHT_30CM = 0.3;
	public static final double FLOORHEIGHT_35CM = 0.35;
	public static final double FLOORHEIGHT_40CM = 0.4;
	public static final double FLOORHEIGHT_45CM = 0.45;
	//
	public static final double FLOORHEIGHT_50CM = 0.5;
	public static final double FLOORHEIGHT_60CM = 0.6;
	public static final double FLOORHEIGHT_70CM = 0.7;
	public static final double FLOORHEIGHT_80CM = 0.8;
	public static final double FLOORHEIGHT_90CM = 0.9;
	//
	public static final double FLOORHEIGHT_100CM = 1.0;
	public static final double FLOORHEIGHT_110CM = 1.1;
	public static final double FLOORHEIGHT_120CM = 1.2;
	public static final double FLOORHEIGHT_130CM = 1.3;
	public static final double FLOORHEIGHT_140CM = 1.4;
	public static final double FLOORHEIGHT_150CM = 1.5;
	//
	public static final double FLOORHEIGHT_200CM = 2.0;
	public static final double FLOORHEIGHT_210CM = 2.1;
	public static final double FLOORHEIGHT_220CM = 2.2;
	public static final double FLOORHEIGHT_230CM = 2.3;
	public static final double FLOORHEIGHT_240CM = 2.4;
	public static final double FLOORHEIGHT_250CM = 2.5;
	//
	public static final double FLOORHEIGHT_300CM = 3.0;
	
	//TIPOS_ACABAMENTO_PISO
	//
	public static final int FLOORFINISH_BASICA = 2001;
	public static final int FLOORFINISH_MADEIRA = 2002;
	public static final int FLOORFINISH_TACO = 2003;
	public static final int FLOORFINISH_CERAMICA = 2004;
	public static final int FLOORFINISH_CIMENTO = 2005;
	public static final int FLOORFINISH_ASFALTO = 2006;

	//ESPESURAS_ACABAMENTO_PISO
	//
	public static final double FLOORFINISH_0MM = 0.0;
	public static final double FLOORFINISH_2MM = 0.0025;
	public static final double FLOORFINISH_5MM = 0.005;
	public static final double FLOORFINISH_7MM = 0.0075;
	public static final double FLOORFINISH_10MM = 0.01;
	public static final double FLOORFINISH_12MM = 0.0125;
	public static final double FLOORFINISH_15MM = 0.015;
	public static final double FLOORFINISH_20MM = 0.02;
	public static final double FLOORFINISH_25MM = 0.025;
	public static final double FLOORFINISH_30MM = 0.03;
	public static final double FLOORFINISH_35MM = 0.035;
	public static final double FLOORFINISH_40MM = 0.04;
	public static final double FLOORFINISH_45MM = 0.045;
	public static final double FLOORFINISH_50MM = 0.05;
	
	//TABLE_HEADERS
	//
	public static final String[] HDR_LAYEREXPLORER_TBLLAYERS = new String[] {
		"Ativo", "Nome", "Cor", "Ltype", "On/Off"
	};	
	
	//DEFAULT_COMMAND_HISTORY PARAMETERS
	//
	public static final int CMDHIST_COMMANDPROMPT_CMDSTRING_HEIGHT = 20;
	public static final int CMDHIST_COMMANDPROMPT_CMDLIST_CELLHEIGHT = 20;
	public static final int CMDHIST_COMMANDPROMPT_CMDLIST_HEIGHT = 3 * AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_CELLHEIGHT;
	public static final int CMDHIST_COMMANDPROMPT_CMDLIST_MAXSIZE = 4000;
	public static final int CMDHIST_COMMANDPROMPT_HEIGHT = AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_HEIGHT + AppDefs.CMDHIST_COMMANDPROMPT_CMDSTRING_HEIGHT;
	
	//RUBBERBAND
	//
	public static final int RUBBERBAND_TEXT_MARGINTOPBOTTOMSCR = 10;
	public static final int RUBBERBAND_TEXT_MARGINLEFTRIGHTSCR = 10;
	public static final int RUBBERBAND_TEXT_HEIGHTSCR = 8;
	
	//PICKMODE
	//
	public static final int PICKMODE_NONE = -1;
	public static final int PICKMODE_PICKPOINT = 1001;
	public static final int PICKMODE_PICKFIRSTPOINT = 1002;
	public static final int PICKMODE_PICKSECONDPOINT = 1003;
	public static final int PICKMODE_PICKFIRSTCORNER = 1004;
	public static final int PICKMODE_PICKSECONDCORNER = 1005;
	public static final int PICKMODE_PICKCENTERPOINT = 1006;
	public static final int PICKMODE_PICKRADIUS = 1007;
	public static final int PICKMODE_PICKARCSTARTPOINT = 1008;
	public static final int PICKMODE_PICKARCENDPOINT = 1009;
	public static final int PICKMODE_PICKANGLE = 1010;
	public static final int PICKMODE_PICKPOINTATDIR = 1011;
	
	//PICKMODE_WAIT_TIMEOUT
	//	
	public static final long PICKMODE_WAITTIMEOUTMILI = 50;
	
	//ZOOMMODE
	//
	public static final int ZOOMMODE_NONE = -1;
	public static final int ZOOMMODE_PAN = 2001;
	public static final int ZOOMMODE_ZOOMFIRSTCORNER = 2001;
	public static final int ZOOMMODE_ZOOMSECONDCORNER = 2002;
	
	//ZOOMMODE_WAIT_TIMEOUT
	//	
	public static final long ZOOMMODE_WAITTIMEOUTMILI = 50;
	
	//ZOOMMODE_SCALEFACTOR
	//	
	public static final double ZOOMMODE_ZOOMIN_SCALEFACT = 0.5;
	public static final double ZOOMMODE_ZOOMOUT_SCALEFACT = 2.0;
	
	//MOUSE_DOUBLE_CLICKED
	//
	public static final long MOUSEDOUBLECLICKED_TIMEOUT = 500;
		
	//MOUSE_MOVE
	//
	public static final long MOUSEMOVE_TIMEOUT = 10;
	public static final double MOUSEMOVE_MINDIST = 0.001;
	
	//GRID_MODE
	//
	public static final int GRIDMODE_OFF = 0;
	public static final int GRIDMODE_ON = 1;
	//
	public static final double GRIDMODE_XSIZE = 1.0;
	public static final double GRIDMODE_YSIZE = 1.0;
	//
	public static final double GRIDMODE_PIXELSIZE = 3.0;
	//
	public static final int GRIDMODE_XAXISNUM = 5;
	public static final int GRIDMODE_YAXISNUM = 5;
	//
	public static final int GRIDMODE_TEXT_HEIGHTSCR = 8;
	//
	public static GeomPoint2d GRIDMODE_ORIGIN = new GeomPoint2d(0.0, 0.0);
	
	//SNAP_MODE
	//
	public static final int SNAPMODE_OFF = 0;
	public static final int SNAPMODE_ON = 1;
	//
	public static final double SNAPMODE_XSIZE = 0.005;
	public static final double SNAPMODE_YSIZE = 0.005;
	//
	public static GeomPoint2d SNAPMODE_ORIGIN = new GeomPoint2d(0.0, 0.0);

	//ORTHO_MODE
	//
	public static final int ORTHOMODE_OFF = 0;
	public static final int ORTHOMODE_ON = 1;

	//SELECT_MODE
	//
	public static final int SELECTMODE_NONE = -1;
	public static final int SELECTMODE_SELECTOBJECT = 5001;

	//DRAG_MODE
	//
	public static final int DRAGMODE_NONE = -1;
	public static final int DRAGMODE_DRAGOBJECT = 6001;
	
	//TEXT_MODE
	//
	public static final int TEXTMODE_NONE = -1;
	public static final int TEXTMODE_ENTERTEXT = 7001;
	
	//TEXTMODE_WAIT_TIMEOUT
	//	
	public static final long TEXTMODE_WAITTIMEOUTMILI = 50;
	
	//SELECT_MODE_WAIT_TIMEOUT
	//	
	public static final long SELECTMODE_WAITTIMEOUTMILI = 50;
	
	//SELECTBOX_SIZE
	//
	public static final int SELECTBOX_SIZE = 10;
	
	//OSNAP_MODE
	//
	public static final int OSNAPMODE_NONE = 0;
	public static final int OSNAPMODE_NODEPOINT = 1;
	public static final int OSNAPMODE_ENDPOINT = 2;
	public static final int OSNAPMODE_MIDDLE = 4;
	public static final int OSNAPMODE_CENTER = 8;
	public static final int OSNAPMODE_QUADRANT = 16;
	//
	public static final int OSNAPMODE_ALL = (
			AppDefs.OSNAPMODE_NODEPOINT +
			AppDefs.OSNAPMODE_ENDPOINT +
			AppDefs.OSNAPMODE_MIDDLE +
			AppDefs.OSNAPMODE_CENTER +
			AppDefs.OSNAPMODE_QUADRANT );
	
	//SELECTBOX_SIZE
	//
	public static final int OSNAPBOX_SIZE = 10;
	
	//FACE_DISTANCE
	//
	public static final double FACE_WALLDIST = 0.05;
	
	//MATH_PRECISION
	//
	public static final double MATHPREC_MIN = 0.000001;
	public static final double MATHPREC_MAX = 9999999.0;
	
	//ARC/CIRCLE NUMBER SEGMENTS
	//
	public static final int DRAWARC_NUMBER_SEGMENTS = 20;
	public static final int DRAWCIRCLE_NUMBER_SEGMENTS = 20;
	
	//CADVIEW - MCS
	//
	public static final double MCSPLAN_XSIZE_MILI = 594.0;
	public static final double MCSPLAN_YSIZE_MILI = 420.0;
	//
	public static final double MCSPLAN_ZHEIGHT = 10.0;
	//
	public static final double MCSPLAN_SCALEFACTOR = 50.0 / 1000.0;	// ScaleFactor = DrawingScale / UnitsInMilimeters
	
	//CADVIEW - PROJ
	//
	public static final double PROJPLAN_OBSERVER_DIST = 2.0;			// 2.0 metros
	//
	public static final double PROJPLAN_WIDTH = 15.0;
	public static final double PROJPLAN_HEIGHT = 15.0;
	public static final double PROJPLAN_ZHEIGHT = 15.0;

	//CADVIEW - MOVE and ROTATE
	//
	public static final double ZOOM_MOVEFORWARD = 0.5;					//  0.5 metros
	public static final double ZOOM_MOVEBACKWARD = -0.5;				// -0.5 metros
	//
	public static final double ZOOM_ROTATELEFT = 5.0;					//  5.0 degrees
	public static final double ZOOM_ROTATERIGHT = -5.0;					// -5.0 degrees
	
	//FACE_TYPE
	//
	public static final int FACETYPE_NONE = -1;
	public static final int FACETYPE_3PTS = 3;
	public static final int FACETYPE_4PTS = 4;

	//LAYEREXPLORER
	//
	public static final int LAYEREXPLORER_XPOS = 100;
	public static final int LAYEREXPLORER_YPOS = 50;
	//
	public static final int LAYEREXPLORER_WIDTH = 800;
	public static final int LAYEREXPLORER_HEIGHT = 600;
	
	//LAYEREXPLORER_LIST
	//
	public static final int LAYEREXPLORER_LIST_ACTIVE = 0;
	public static final int LAYEREXPLORER_LIST_NAME = 1;
	public static final int LAYEREXPLORER_LIST_COLOR = 2;
	public static final int LAYEREXPLORER_LIST_LTYPE = 3;
	public static final int LAYEREXPLORER_LIST_LAYERONOFF = 4;

	//ACABAMENTOS_PAREDE
	//
	public static CadAcabamentoParedeDef WALLFINISHDEF_BASIC = null;
	public static CadAcabamentoParedeDef WALLFINISHDEF_CHAPISCO = null;
	public static CadAcabamentoParedeDef WALLFINISHDEF_CHAPISCO_EMBOCO_PINTURA = null; 
	public static CadAcabamentoParedeDef WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_PINTURA = null;
	public static CadAcabamentoParedeDef WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_CERAMICA = null;

	//ACABAMENTOS_PORTA
	//
	public static CadAcabamentoPortaDef DOORFINISHDEF_BASIC = null; 
	public static CadAcabamentoPortaDef DOORFINISHDEF_WOOD = null; 
	public static CadAcabamentoPortaDef DOORFINISHDEF_IRON = null; 
	public static CadAcabamentoPortaDef DOORFINISHDEF_GLASS = null; 

	//ACABAMENTOS_JANELA
	//
	public static CadAcabamentoJanelaDef WINDOWFINISHDEF_BASIC = null; 
	public static CadAcabamentoJanelaDef WINDOWFINISHDEF_WOOD = null;
	public static CadAcabamentoJanelaDef WINDOWFINISHDEF_IRON = null;
	public static CadAcabamentoJanelaDef WINDOWFINISHDEF_GLASS = null;
	
	//ACABAMENTOS_PISO
	//
	public static CadAcabamentoPisoDef FLOORFINISHDEF_BASIC = null;
	public static CadAcabamentoPisoDef FLOORFINISHDEF_MADEIRA = null; 
	public static CadAcabamentoPisoDef FLOORFINISHDEF_TACO = null;
	public static CadAcabamentoPisoDef FLOORFINISHDEF_CERAMICA = null;
	public static CadAcabamentoPisoDef FLOORFINISHDEF_CIMENTO = null;
	public static CadAcabamentoPisoDef FLOORFINISHDEF_ASFALTO = null;
	
	//FONT_SIZE_MILI
	//
	public static double FONTSZ_SMALL = 3.0;
	public static double FONTSZ_NORMAL = 4.0; 
	public static double FONTSZ_MEDIUM = 4.5; 
	public static double FONTSZ_BIG = 5.0; 
	
	//SCREEN_RESOLUTION
	//
	public static double SCREEN_RESOLUTION_DPI = 72.0;
	
	//PRINTER_RESOLUTION
	//
	public static double PRINTER_RESOLUTION_DPI = 300.0;
	
	//UNIT_POL_TO_MM
	//
	public static double UNIT_POL_TO_MM = 25.4;
	public static double UNIT_POL_TO_M = 0.0254;

	//SCALE_FACTOR
	//
	public static double UNIT_FACTOR_POL_TO_MM = AppDefs.SCREEN_RESOLUTION_DPI / AppDefs.UNIT_POL_TO_MM;
	public static double UNIT_FACTOR_POL_TO_M = AppDefs.SCREEN_RESOLUTION_DPI / AppDefs.UNIT_POL_TO_M;
	
	//AREA_TYPE
	//
	public static int AREATYPE_NONE = -1;
	public static int AREATYPE_ROOM = 5001;
	public static int AREATYPE_APARTMENT = 5002;
	public static int AREATYPE_BALCONY = 5003;
	public static int AREATYPE_BUILDINGCOMMOM = 5007;
	public static int AREATYPE_BUILDINGINTERNAL = 5008;
	public static int AREATYPE_BUILDINGEXTERNAL = 5009;
	public static int AREATYPE_PARKING = 5090;
	public static int AREATYPE_TERRAIN = 5900;
	
	//TEXT_ALIGN
	//
	//HORIZALIGN
	public static int HORIZALIGN_LEFT = 9101;
	public static int HORIZALIGN_CENTER = 9102;
	public static int HORIZALIGN_RIGHT = 9103;
	//VERTALIGN
	public static int VERTALIGN_TOP = 9201;
	public static int VERTALIGN_MIDDLE = 9202;
	public static int VERTALIGN_BOTTOM = 9203;
	
}
