/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppDefs.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/05/2025
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

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cmd.CmdAbout;
import br.com.tlmv.aicadxapp.cmd.CmdArc;
import br.com.tlmv.aicadxapp.cmd.CmdBox3D;
import br.com.tlmv.aicadxapp.cmd.CmdCilinder3D;
import br.com.tlmv.aicadxapp.cmd.CmdCircle;
import br.com.tlmv.aicadxapp.cmd.CmdClose;
import br.com.tlmv.aicadxapp.cmd.CmdCone3D;
import br.com.tlmv.aicadxapp.cmd.CmdCopy;
import br.com.tlmv.aicadxapp.cmd.CmdCreateShape;
import br.com.tlmv.aicadxapp.cmd.CmdDistance;
import br.com.tlmv.aicadxapp.cmd.CmdDxfIn;
import br.com.tlmv.aicadxapp.cmd.CmdDxfOut;
import br.com.tlmv.aicadxapp.cmd.CmdErase;
import br.com.tlmv.aicadxapp.cmd.CmdExit;
import br.com.tlmv.aicadxapp.cmd.CmdGridOnOff;
import br.com.tlmv.aicadxapp.cmd.CmdInsertImage;
import br.com.tlmv.aicadxapp.cmd.CmdLayerExplorer;
import br.com.tlmv.aicadxapp.cmd.CmdLine;
import br.com.tlmv.aicadxapp.cmd.CmdMenu;
import br.com.tlmv.aicadxapp.cmd.CmdMirror;
import br.com.tlmv.aicadxapp.cmd.CmdMove;
import br.com.tlmv.aicadxapp.cmd.CmdNew;
import br.com.tlmv.aicadxapp.cmd.CmdOffset;
import br.com.tlmv.aicadxapp.cmd.CmdOpen;
import br.com.tlmv.aicadxapp.cmd.CmdOrthoOnOff;
import br.com.tlmv.aicadxapp.cmd.CmdPan;
import br.com.tlmv.aicadxapp.cmd.CmdPoint;
import br.com.tlmv.aicadxapp.cmd.CmdPolygon;
import br.com.tlmv.aicadxapp.cmd.CmdPrint;
import br.com.tlmv.aicadxapp.cmd.CmdRectangle;
import br.com.tlmv.aicadxapp.cmd.CmdSave;
import br.com.tlmv.aicadxapp.cmd.CmdSaveAs;
import br.com.tlmv.aicadxapp.cmd.CmdScale;
import br.com.tlmv.aicadxapp.cmd.CmdSetup;
import br.com.tlmv.aicadxapp.cmd.CmdSnapOnOff;
import br.com.tlmv.aicadxapp.cmd.CmdSphere3D;
import br.com.tlmv.aicadxapp.cmd.CmdStop;
import br.com.tlmv.aicadxapp.cmd.CmdText;
import br.com.tlmv.aicadxapp.cmd.CmdTorus3D;
import br.com.tlmv.aicadxapp.cmd.CmdTroncoCone3D;
import br.com.tlmv.aicadxapp.cmd.CmdZoomExt;
import br.com.tlmv.aicadxapp.cmd.CmdZoomIn;
import br.com.tlmv.aicadxapp.cmd.CmdZoomViewFront;
import br.com.tlmv.aicadxapp.cmd.CmdZoomViewBack;
import br.com.tlmv.aicadxapp.cmd.CmdZoomViewBottom;
import br.com.tlmv.aicadxapp.cmd.CmdZoomOut;
import br.com.tlmv.aicadxapp.cmd.CmdZoomViewLeft;
import br.com.tlmv.aicadxapp.cmd.CmdZoomViewRight;
import br.com.tlmv.aicadxapp.cmd.CmdZoomViewTop;
import br.com.tlmv.aicadxapp.cmd.CmdZoomWindow;
import br.com.tlmv.aicadxapp.cmd.ICmdBase;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoJanelaDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoParedeDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoPisoDef;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadAcabamentoPortaDef;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdAmbiente;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdArea;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdAreaTable;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdJanela;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdParede;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdParedeAbertura;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdPiso;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdPontoArquitetura;
import br.com.tlmv.aicadxmod.arquitetura.cmd.CmdPorta;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdAlterarPlanilhaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdAnotacaoCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdCsvExportarDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdCsvImportarDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdDimensionaRedeDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdGerarPlanilhaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdGerarPlantaAreaContribuicaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdGerarPlantaPerfisDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdLigacaoCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdPropriedadeCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cmd.CmdVerifLigacaoCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.drenagem.vo.EpsgCodeVO;
import br.com.tlmv.aicadxmod.eletrica.cmd.CmdInsereEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cmd.CmdPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cmd.CmdTrocaCircuito;
import br.com.tlmv.aicadxmod.eletrica.cmd.CmdTrocaComando;
import br.com.tlmv.aicadxmod.eletrica.cmd.CmdTrocaOrigem;
import br.com.tlmv.aicadxmod.esgoto.cmd.CmdPontoEsgoto;
import br.com.tlmv.aicadxmod.gas.cmd.CmdPontoGas;

public class AppDefs 
{
//Public	
	public static final int DEBUG_LEVEL00 = 0;
	public static final int DEBUG_LEVEL01 = 1;
	public static final int DEBUG_LEVEL02 = 2;
	public static final int DEBUG_LEVEL03 = 3;
	public static final int DEBUG_LEVEL04 = 4;
	public static final int DEBUG_LEVEL05 = 5;
	public static final int DEBUG_LEVEL06 = 6;
	public static final int DEBUG_LEVEL07 = 7;
	public static final int DEBUG_LEVEL08 = 8;
	public static final int DEBUG_LEVEL09 = 9;
	public static final int DEBUG_LEVEL10 = 10;
	public static final int DEBUG_LEVEL11 = 11;
	public static final int DEBUG_LEVEL12 = 12;
	public static final int DEBUG_LEVEL13 = 13;
	public static final int DEBUG_LEVEL14 = 14;
	public static final int DEBUG_LEVEL15 = 15;
	public static final int DEBUG_LEVEL16 = 16;
	public static final int DEBUG_LEVEL17 = 17;
	//
	public static final int DEBUG_LEVEL99 = 99;

	public static final int DEBUG_LEVEL = AppDefs.DEBUG_LEVEL00;

	public static final String APP_NAME = "AICADx for Steam (CodeName: JuJu)"; 
	
	public static final String APP_VERSAO = "1.1.20250703.Alfa";

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
	public static final String EXT_XLS = "xls";
			
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
	
	//DEFAULT_INITIAL_LOCATION
	//
	public static final double CANVAS_LOCATION_X = 271.0;
	public static final double CANVAS_LOCATION_Y = 91.0;
	
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
	public static final String CTX_OUTPUTDIR = "Output/";
	public static final String CTX_REPOSITORYDIR = "Repository/";
	public static final String CTX_SHAPESDIR = "Shapes/";
	public static final String CTX_SPOOLDIR = "Spool/";
	public static final String CTX_TEMPDIR = "Temp/";
	public static final String CTX_TEMPLATESDIR = "Templates/";
	//
	public static final String CTX_CONFFILE = "aicadx_config.cfg";
	public static final String CTX_CONFFILE_DEFAULT = "aicadx_config-default.cfg";
	//
	public static final String CTX_LAYERSFILE = "aicadx_layers.cfg";
	public static final String CTX_LAYERSFILE_DEFAULT = "aicadx_layers-default.cfg";
	//
	public static final String CTX_SHAPEFILE = "aicadx_shapefiles.cfg";
	public static final String CTX_SHAPEFILE_DEFAULT = "aicadx_shapefiles-default.cfg";
	
	//CONFIG_TAGS
	//
	public static final String CFG_TAG_CONFIGURATION = "Configuration";
	public static final String CFG_TAG_CONFIGURATION_ENVIROMENT_TYPE = "EnviromentType";
	public static final String CFG_TAG_CONFIGURATION_REPOSITORY_LOCATION = "RepositoryLocation";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION = "DatabaseConnection";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION_DATABASETYPE = "DatabaseType";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION_DSNAME = "DsName";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION_DRIVER = "Driver";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION_URL = "URL";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION_USER = "User";
	public static final String CFG_TAG_CONFIGURATION_DATABASECONNECTION_PWD = "Pwd";
	
	//CAIXAINSPECAO/REDE DRENAGEM (CSV)
	//
	public static final String DEF_CSVFILENAME_CAIXAINSPECAODRENAGEM = "PVS.csv";
	public static final String DEF_CSVFILENAME_REDEDRENAGEM = "REDE.csv";
	
	//DATABASE BACKUP/RESTORE
	//
	public static final String DATABASE_BACKUP = "/usr/bin/pg_dump --file \"#BACKUP_FILE#\" --host \"127.0.0.1\" --port \"5432\" --username \"#DATABASE_USER#\" --no-password --format=p --schema \"#SCHEMA_NAME#\" \"#DATABASE_NAME#\" ";
	public static final String DATABASE_RESTORE = "/usr/bin/pg_restore --host \"127.0.0.1\" --port \"5432\" --username \"#DATABASE_USER#\" --no-password --dbname \"#DATABASE_NAME#\" --verbose \"#BACKUP_FILE#\" ";
	
	//EVENT_TYPES
	//
	public static final int EVENTTYPE_NONE = -1;
	public static final int EVENTTYPE_CMDENTER = 1001;
	public static final int EVENTTYPE_SCLCHANGE = 1002;
	
	//NULL_VALUES
	//
	public static final int NULL_INT  = -1;
	public static final long NULL_LNG  = -1L;
	public static final char NULL_CHAR = '\0';
	public static final double NULL_DBL  = 0.0;
	public static final String NULL_STR  = "";
	public static final Date NULL_DATE  = new Date(0, 0, 1);
	//public static final String NULL_BOOL  = "F";
	
	//DATABASE_DRIVER
	//	
    public static final String DEF_DATABASE_DRIVER_NOSQL = "nosql";
    public static final String DEF_DATABASE_DRIVER_SQLIGHT = "sqlight";    	
    public static final String DEF_DATABASE_DRIVER_POSTGRESQL = "postgresql";
	
	//DATABASE_TYPE_LENGTH
	//
	public static final int DBTYPE_LENGTH_NUMERIC = 20;
	public static final int DBTYPE_LENGTH_DATETIME = 20;
	public static final int DBTYPE_LENGTH_VARCHAR = 256;
	
	//LANGUAGE_DEFINITIONS
	//
	public static final String DEF_LANG_PT = "Pt";
	public static final String DEF_LANG_EN = "En";
	
	//FORMAT_REGIONS_DEFINITIONS
	//
	public static final String DEF_COUNTRY_BR = "Br";	
	public static final String DEF_COUNTRY_US = "Us";

	//SIM/NAO VALUES
	//
	public static final String DEF_VALUES_SIM  = "S";
	public static final String DEF_VALUES_NAO  = "N";

	//SIM/NAO TEXT
	//
	public static final String DEF_TEXT_SIM  = "Sim";
	public static final String DEF_TEXT_NAO  = "Nao";
	
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
	public static final String DEF_DATETIME_TYPE5_FILEFORMAT_MASC = "yyyy-MM-dd HH:mm:ss";
	
	//SEQUENCE_VALUE_RANGE
	//
	public static final int DEF_SEQ_INIT = 100000001;
	public static final int DEF_SEQ_END = 999999999;
			
	//MESSAGE_TYPES
	//
	public static final int DEF_MSGTYPE_NONE = 0;
	public static final int DEF_MSGTYPE_WARN = 1;
	public static final int DEF_MSGTYPE_ERROR = 2;
	
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
	public static final int DEF_INPUTPARAMTYPE_POINTANDTEXT = 1013;
	public static final int DEF_INPUTPARAMTYPE_POINTANDROTATION = 1012;
	public static final int DEF_INPUTPARAMTYPE_POINTTEXTANDROTATION = 1013;
	public static final int DEF_INPUTPARAMTYPE_POINTROTATIONANDSCALE = 1014;
	public static final int DEF_INPUTPARAMTYPE_DIRNAME = 1015;
	public static final int DEF_INPUTPARAMTYPE_FILENAME = 1016;
	public static final int DEF_INPUTPARAMTYPE_POINTANDFILENAME = 1017;
	public static final int DEF_INPUTPARAMTYPE_POINTTEXTANDFILENAME = 1018;
	public static final int DEF_INPUTPARAMTYPE_POINTROTATIONANDFILENAME = 1019;
	public static final int DEF_INPUTPARAMTYPE_POINTTEXTROTATIONANDFILENAME = 1020;
	public static final int DEF_INPUTPARAMTYPE_POINTROTATIONSCALEANDFILENAME = 1021;
	public static final int DEF_INPUTPARAMTYPE_POINTDISCIPLINEANDSHAPENAME = 1022;
	public static final int DEF_INPUTPARAMTYPE_RECTANGLEANDHEIGHT = 1023;
	public static final int DEF_INPUTPARAMTYPE_CIRCLEANDHEIGHT = 1024;
	public static final int DEF_INPUTPARAMTYPE_TORUS = 1025;
	//
	public static final int DEF_INPUTPARAMTYPE_VDIR = 1101;
	//
	public static final int DEF_INPUTPARAMTYPE_ENTITY = 1201;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_1PTS = 1202;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_2PTS = 1203;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_2PTS_DIST = 1204;
	//
	public static final int DEF_INPUTPARAMTYPE_2ENTITY = 1301;
	public static final int DEF_INPUTPARAMTYPE_2ENTITY_1PTS = 1302;
	public static final int DEF_INPUTPARAMTYPE_2ENTITY_2PTS = 1303;
	public static final int DEF_INPUTPARAMTYPE_2ENTITY_2PTS_DIST = 1304;
	//
	public static final int DEF_INPUTPARAMTYPE_ENTITY_STRVAL = 1401;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_INTVAL = 1402;
	public static final int DEF_INPUTPARAMTYPE_ENTITY_DBLVAL = 1403;
	//
	public static final int DEF_INPUTPARAMTYPE_ENTITY_1PTS_KEYAREA = 1501;
	
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
	//RSCODE: DIMENSIONA_CAIXA_INSPECAO_DRENAGEM
	public static final int RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_NONE = 6001;
	public static final int RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_OK = 6002;
	public static final int RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_CANCELAR = 6003;
	//RSCODE: GERAR_PLANTA_PERFIL_DRENAGEM
	public static final int RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_NONE = 7001;
	public static final int RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARPERFIL = 7002;
	public static final int RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARTODOSPERFIS = 7003;
	public static final int RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_FECHAR = 7004;
	//RSCODE: GERAR_PLANILHA_CALCULO_DRENAGEM
	public static final int RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_NONE = 8001;
	public static final int RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_EXPORTAR = 8002;
	public static final int RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_GRAVAR = 8003;
	public static final int RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_OK = 8004;
	public static final int RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_CANCELAR = 8005;
	public static final int RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_MINIMIZED = 8006;
	//RSCODE: COMPDOCUMENTPROPERTY
	public static final int RSCODE_COMPDOCUMENTPROPERTY_NONE = 9001;
	public static final int RSCODE_COMPDOCUMENTPROPERTY_DOCUMENTO_SELECIONADO = 9002;
	//RSCODE: COMPCOMMANDPROMPT
	public static final int RSCODE_COMPCOMMANDPROMPT_SCALE_CHANGE = 10001;
	//RSCODE: SETUP
	public static final int RSCODE_SETUP_NONE = 11001;
	public static final int RSCODE_SETUP_ORIGEM = 11002;
	public static final int RSCODE_SETUP_OK = 11003;
	public static final int RSCODE_SETUP_CANCELAR = 11004;
	//RSCODE: PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_NONE = 12001;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_OK = 12002;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_CANCELAR = 12003;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_PROXIMACI = 12004;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_LOCAL_SELECTED = 12005;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_AREA_CHANGED = 12006;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_DIAMETRO_CHANGED = 12007;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_DECLIVIDADE_CHANGED = 12008;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_COEFIMPER_CHANGED = 12009;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_PROFUNDIDADE_CHANGED = 12010;
	public static final int RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_COTATERRENO_CHANGED = 12011;
	//RSCODE: OPENDATABASE
	public static final int RSCODE_OPENSAVEDATABASE_NONE = 13001;
	public static final int RSCODE_OPENSAVEDATABASE_NEW = 13002;
	public static final int RSCODE_OPENSAVEDATABASE_OPEN = 13003;
	public static final int RSCODE_OPENSAVEDATABASE_SAVE = 13004;
	public static final int RSCODE_OPENSAVEDATABASE_SAVEAS = 13005;
	public static final int RSCODE_OPENSAVEDATABASE_COPY = 13006;
	public static final int RSCODE_OPENSAVEDATABASE_RENAME = 13007;
	public static final int RSCODE_OPENSAVEDATABASE_DROP = 13008;
	public static final int RSCODE_OPENSAVEDATABASE_FECHAR = 13009;
	//RSCODE: NEWDATABASE
	public static final int RSCODE_NEWDATABASE_NONE = 14001;
	public static final int RSCODE_NEWDATABASE_OK = 14002;
	public static final int RSCODE_NEWDATABASE_CANCELAR = 14003;
	
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
	
	/* ESPG (UTM_PROJECTIONS)
	*/
	private static final String ESPGCODE_XY = "0";						// NO_DATUM - XY METERS
	//
	private static final String ESPGCODE_LL84 = "4326";					// WGS84 - LATITUDE/LONGITUDE
	//
	private static final String ESPGCODE_UTMZONE17S = "31977";			// SIRGAS 2000 - UTM Zone 17S
	private static final String ESPGCODE_UTMZONE18S = "31978";			// SIRGAS 2000 - UTM Zone 18S
	private static final String ESPGCODE_UTMZONE19S = "31979";			// SIRGAS 2000 - UTM Zone 19S
	private static final String ESPGCODE_UTMZONE20S = "31980";			// SIRGAS 2000 - UTM Zone 20S
	private static final String ESPGCODE_UTMZONE21S = "31981";			// SIRGAS 2000 - UTM Zone 21S
	private static final String ESPGCODE_UTMZONE22S = "31982";			// SIRGAS 2000 - UTM Zone 22S
	private static final String ESPGCODE_UTMZONE23S = "31983";			// SIRGAS 2000 - UTM Zone 23S
	private static final String ESPGCODE_UTMZONE24S = "31984";			// SIRGAS 2000 - UTM Zone 24S
	private static final String ESPGCODE_UTMZONE25S = "31985";			// SIRGAS 2000 - UTM Zone 25S
	
	/* ARRAY_ESPGCODE (UTM_PROJECTIONS)
	*/
	private static final EpsgCodeVO[] ARR_ESPGCODE = {
		new EpsgCodeVO(1001, AppDefs.ESPGCODE_XY, "XY Meters", ""),
		//
		new EpsgCodeVO(1001, AppDefs.ESPGCODE_LL84, "LL84", ""),
		//
		new EpsgCodeVO(1001, AppDefs.ESPGCODE_UTMZONE17S, "SIRGAS 2000 - UTM Zone 17S", ""),
		new EpsgCodeVO(1002, AppDefs.ESPGCODE_UTMZONE18S, "SIRGAS 2000 - UTM Zone 18S", ""),
		new EpsgCodeVO(1003, AppDefs.ESPGCODE_UTMZONE19S, "SIRGAS 2000 - UTM Zone 19S", ""),
		new EpsgCodeVO(1004, AppDefs.ESPGCODE_UTMZONE20S, "SIRGAS 2000 - UTM Zone 20S", ""),
		new EpsgCodeVO(1005, AppDefs.ESPGCODE_UTMZONE21S, "SIRGAS 2000 - UTM Zone 21S", ""),
		new EpsgCodeVO(1006, AppDefs.ESPGCODE_UTMZONE22S, "SIRGAS 2000 - UTM Zone 22S", ""),
		new EpsgCodeVO(1007, AppDefs.ESPGCODE_UTMZONE23S, "SIRGAS 2000 - UTM Zone 23S", ""),
		new EpsgCodeVO(1008, AppDefs.ESPGCODE_UTMZONE24S, "SIRGAS 2000 - UTM Zone 24S", ""),
		new EpsgCodeVO(1009, AppDefs.ESPGCODE_UTMZONE25S, "SIRGAS 2000 - UTM Zone 25S", "")			
	};
	
	//PAPERSIZE_DEFINITION
	//
	//A0
	public static final int PAPERSIZE_AO_WIDTH_MILI = 1189;
	public static final int PAPERSIZE_AO_HEIGHT_MILI = 841;
	//A1
	public static final int PAPERSIZE_A1_WIDTH_MILI = 841;
	public static final int PAPERSIZE_A1_HEIGHT_MILI = 594;
	//A2
	public static final int PAPERSIZE_A2_WIDTH_MILI = 594;
	public static final int PAPERSIZE_A2_HEIGHT_MILI = 420;
	//A3
	public static final int PAPERSIZE_A3_WIDTH_MILI = 420;
	public static final int PAPERSIZE_A3_HEIGHT_MILI = 297;
	//A4
	public static final int PAPERSIZE_A4_WIDTH_MILI = 297;
	public static final int PAPERSIZE_A4_HEIGHT_MILI = 210;
	
	/* DEFAULT_PROJECT_DEFINITIONS 
	 */
	public static final double DEF_DEFAULT_PROJECT_SCALE = 100.0;										// 100.0 = 1/100; 50.0 = 1/50;
	public static final double DEF_DEFAULT_PROJECT_UNIT = 1000.0;										// 1000.0 = Metro; 1.0 = milimetro; 
	public static final double DEF_DEFAULT_PROJECT_SCALEFACTOR =
		AppDefs.DEF_DEFAULT_PROJECT_SCALE / AppDefs.DEF_DEFAULT_PROJECT_UNIT;							
	public static final double DEF_DEFAULT_PROJECT_PAPEL_WIDTH = 1189.0;								// 1189.0 mm 
	public static final double DEF_DEFAULT_PROJECT_PAPEL_HEIGHT = 841.0;								// 841.0 mm 
	public static final String DEF_DEFAULT_PROJECT_ESPGCODE = AppDefs.ESPGCODE_XY;						// NO_DATUM - XY METERS
	public static final GeomPoint3d DEF_DEFAULT_PROJECT_ORIGEM = new GeomPoint3d(0.0, 0.0, 0.0);		// Origem: (0.0, 0.0, 0.0) 
	public static final GeomVector3d DEF_DEFAULT_PROJECT_XDIR = new GeomVector3d(1.0, 0.0, 0.0);		// XDir: (1.0, 0.0, 0.0) 
	public static final double DEF_DEFAULT_PROJECT_AREA_WIDTH = 
		AppDefs.DEF_DEFAULT_PROJECT_PAPEL_WIDTH * AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
	public static final double DEF_DEFAULT_PROJECT_AREA_HEIGHT = 
		AppDefs.DEF_DEFAULT_PROJECT_PAPEL_HEIGHT * AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
	
	//DREANAGEM
	public static final int DEF_DEFAULT_PROJECT_CODIGOPLUVIOGRAFO = DrenagemCalc.IDFLOCAL_SANTACRUZ_VAL;	
	public static final String DEF_DEFAULT_PROJECT_PLUVIOGRAFO = "SANTA CRUZ";	
	public static final double DEF_DEFAULT_PROJECT_COEFMANNING = 0.013;	
	public static final int DEF_DEFAULT_PROJECT_PERIODO_RECORRENCIA = 10;	

	//DRENAGEM - MEMORIA_CALCULO
	public static final String DEF_DEFAULT_DRENAGEM_NOMEMEMORIACALCULO = "MemoriaCalculoDrenagem%s";	
	public static final String DEF_DEFAULT_DRENAGEM_DESCRICAOMEMORIACALCULO = "Memoria de Calculo de Drenagem";	
	
	/* TOOLBAR_CONTROLS
	*/
	public static final int TOOLBARCTRL_ALL					= -1;
	public static final int TOOLBARCTRL_BASIC				= 7001;
	
	/* 3DVIEW EXAGERATION
	 */
	public static final double EXAGERATION					= 3.0;
	
	/* BLOCKTABLE_DEFINITIONS 
	 */
	public static final String BLKTABLE_MODELSPACE			= "_MODELSPACE_";
	public static final String BLKTABLE_PAPERSPACE			= "_PAPERSPACE_";
	
	/* LAYER_DEFINITIONS 
	 */
	public static final String LAYER_0						= "0";
	//
	public static final String LAYER_0_COORDSYS				= "0_COORDSYS";
	public static final String LAYER_0_BASE				    = "0_BASE";
	//
	// ESTRUTURA (FORMAS)
	//
	public static final String LAYER_F_VIGA_TETO			= "F_VIGA_TETO";
	public static final String LAYER_F_VIGA_PISO            = "F_VIGA_PISO";
	public static final String LAYER_F_PILAR                = "F_PILAR";
	//
	// ARQUITETURA
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
	//
	// ESGOTO / AGUAS_PLUVIAIS
	//
	public static final String LAYER_ESG_COLUNAS            = "ESG_COLUNAS";
	public static final String LAYER_ESG_PONTOS             = "ESG_PONTOS";
	public static final String LAYER_ESG_AREAS_CONTRIB 		= "ESG_AREAS_CONTRIB";
	public static final String LAYER_ESG_TB_APLUV_RET       = "ESG_TB_APLUV_RET";
	public static final String LAYER_ESG_TB_APLUV_REUSO     = "ESG_TB_APLUV_REUSO";
	public static final String LAYER_ESG_TB_PRIMARIO        = "ESG_TB_PRIMARIO";
	public static final String LAYER_ESG_TB_SECUNDARIO      = "ESG_TB_SECUNDARIO";
	public static final String LAYER_ESG_TB_SECUND_GORD     = "ESG_TB_SECUND_GORD";
	public static final String LAYER_ESG_TB_SECUND_MLR      = "ESG_TB_SECUND_MLR";
	public static final String LAYER_ESG_TB_VENTILACAO      = "ESG_TB_VENTILACAO";	
	public static final String LAYER_ESG_COTAS              = "ESG_COTAS";
	public static final String LAYER_ESG_TEXTOS             = "ESG_TEXTOS";
	//
	// DRENAGEM
	//
	public static final String LAYER_RPD_MEMORIA_CALCULO    = "RPD_MEMORIA_CALCULO";
	public static final String LAYER_RPD_PERFIL_DRENAGEM    = "RPD_PERFIL_DRENAGEM";
	public static final String LAYER_RPD_PONTOS             = "RPD_PONTOS";
	public static final String LAYER_RPD_AREAS_CONTRIB 		= "RPD_AREAS_CONTRIB";
	public static final String LAYER_RPD_TB_DRENAGEM_EXIST  = "RPD_TB_DRENAGEM_EXIST";
	public static final String LAYER_RPD_TB_DRENAGEM        = "RPD_TB_DRENAGEM";
	public static final String LAYER_RPD_LIGACOES           = "RPD_LIGACOES";
	public static final String LAYER_RPD_COTAS              = "RPD_COTAS";
	public static final String LAYER_RPD_TEXTOS             = "RPD_TEXTOS";
	//
	// ELETRICA
	//
	public static final String LAYER_ELE_DT_APARENTE 		= "ELE_DT_APARENTE";
	public static final String LAYER_ELE_DT_PISO 			= "ELE_DT_PISO";
	public static final String LAYER_ELE_DT_TETO 			= "ELE_DT_TETO";
	public static final String LAYER_ELE_ELETROC_BT 		= "ELE_ELETROC_BT";
	public static final String LAYER_ELE_ELETROC_MT 		= "ELE_ELETROC_MT";
	public static final String LAYER_ELE_ELETROC_BUS1 		= "ELE_ELETROC_BUS1";
	public static final String LAYER_ELE_ELETROC_BUS2 		= "ELE_ELETROC_BUS2";
	public static final String LAYER_ELE_CIRCUITOS 			= "ELE_CIRCUITOS";
	public static final String LAYER_ELE_PRUMADAS 			= "ELE_PRUMADAS";
	public static final String LAYER_ELE_PONTOS 			= "ELE_PONTOS";
	public static final String LAYER_ELE_TEXTOS 			= "ELE_TEXTOS";
	//
	// GAS
	//
	public static final String LAYER_G_PONTOS				= "G_PONTOS";
	public static final String LAYER_G_TB_PISO				= "G_TB_PISO";
	public static final String LAYER_G_TB_TETO				= "G_TB_TETO";
	public static final String LAYER_G_LIGACOES				= "G_LIGACOES";
	public static final String LAYER_G_COTAS				= "G_COTAS";
	public static final String LAYER_G_TEXTOS				= "G_TEXTOS";
	
	/* SHAPE_DEFINITION
	 */
	//
	//ARQUITETURA
	//
	public static final String SHAPE_ARQ_VASOSANITARIO					= "ARQ-Vaso_Sanitario";
	public static final String SHAPE_ARQ_VASOCAIXAACLOPADA				= "ARQ-Vaso_Caixa_Aclopada";
	public static final String SHAPE_ARQ_LAVATORIOGRANDE				= "ARQ-Lavatorio_Grande";
	//
	//ELETRICA
	//
	public static final String SHAPE_ELE_CAIXA_PASSAGEM_PISO			= "EL-Caixa_Passagem_Piso";
	public static final String SHAPE_ELE_INTERRUPTOR_SIMPLES			= "EL-Interruptor_Simples";
	public static final String SHAPE_ELE_PONTO_FORCA_PISO				= "EL-Ponto_Forca_Piso";
	public static final String SHAPE_ELE_PONTO_LUZ						= "EL-Ponto_Luz";
	public static final String SHAPE_ELE_QUADRO_DISTRIBUICAO			= "EL-Quadro_Distribuicao";
	public static final String SHAPE_ELE_TOMADA_BAIXA					= "EL-Tomada_Baixa";
	//
	//ESGOTO
	//
	public static final String SHAPE_ESG_RALO_SIFONADO					= "ES-Ralo_Sifonado";
	public static final String SHAPE_ESG_CAIXA_PASSAGEM_60X60			= "ES-Caixa_Passagem_60x60";
	//
	//GAS
	//
	public static final String SHAPE_G_PONTO_GAS						= "G-Ponto_Gas";
	
	/* POINT_DEFINITION
	 */
	public static final double POINT_SIZE						= 5;		// 5 pixels
	
	/* COORDSYS_DEFINITION
	 */
	public static final double COORDSYS_SIZE					= 24;		// 24 pixels

	/* TABLE_SYMBOL_DEFINITION
	 */
	public static final double TBL_SYMBOL_SIZE					= 24;		// 24 pixels
	public static final double TBL_SYMBOL_TEXT_SIZE				= 5;		// 5 pixels
	
	/* DOCUMENTO_VIEW_DEFINITIONS (VALUES)
	 */
	public static final int DOCVIEW_GRP_LEVELS_VAL				= 21001;
	public static final int DOCVIEW_GRP_PLANVIEWS_VAL			= 21002;
	public static final int DOCVIEW_GRP_SECTIONVIEWS_VAL		= 21003;
	public static final int DOCVIEW_GRP_ELEVATIONVIEWS_VAL		= 21004;
	public static final int DOCVIEW_GRP_DETAILVIEWS_VAL			= 21005;
	public static final int DOCVIEW_GRP_3DVIEWS_VAL				= 21006;
	public static final int DOCVIEW_GRP_IMAGES_VAL				= 21007;
	public static final int DOCVIEW_GRP_BLOCKS_VAL				= 21008;
	public static final int DOCVIEW_GRP_SHAPES_VAL				= 21009;
	public static final int DOCVIEW_GRP_LAYERS_VAL				= 21010;
	public static final int DOCVIEW_GRP_SHEETS_VAL				= 21011;

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
	public static final String DOCVIEW_GRP_SHAPES_STR			= "Shapes";
	public static final String DOCVIEW_GRP_LAYERS_STR			= "Layers";
	public static final String DOCVIEW_GRP_SHEETS_STR			= "Sheets";

	/* DOCUMENTO_VIEW_DEFINITIONS (ARR_GROUP_ITEM_DATA)
	 */
	public static final GroupItemDataVO[] ARR_DOCVIEW_GROUPS = {
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_LEVELS_VAL), AppDefs.DOCVIEW_GRP_LEVELS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL), AppDefs.DOCVIEW_GRP_PLANVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_SECTIONVIEWS_VAL), AppDefs.DOCVIEW_GRP_SECTIONVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_ELEVATIONVIEWS_VAL), AppDefs.DOCVIEW_GRP_ELEVATIONVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL), AppDefs.DOCVIEW_GRP_DETAILVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_3DVIEWS_VAL), AppDefs.DOCVIEW_GRP_3DVIEWS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_IMAGES_VAL), AppDefs.DOCVIEW_GRP_IMAGES_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_BLOCKS_VAL), AppDefs.DOCVIEW_GRP_BLOCKS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_SHAPES_VAL), AppDefs.DOCVIEW_GRP_SHAPES_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_LAYERS_VAL), AppDefs.DOCVIEW_GRP_LAYERS_STR),
		new GroupItemDataVO(Integer.toString(AppDefs.DOCVIEW_GRP_SHEETS_VAL), AppDefs.DOCVIEW_GRP_SHEETS_STR)
	};
	
	/* OBJECT_TYPES_DEFINITIONS (VALUES)
	 */
	public static int OBJTYPE_COUNT = 9001;
	//
	public static final int OBJTYPE_NONE						= AppDefs.OBJTYPE_COUNT++; 
	public static final int OBJTYPE_ANY							= AppDefs.OBJTYPE_COUNT++; 
	//
	public static final int OBJTYPE_APPCADMAIN					= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_TABLES						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_LAYER_TABLE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_SHAPE_TABLE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_VIEW_TABLE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_OBJECTDATA_TABLE			= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_IMAGE_TABLE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BLOCK_TABLE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_DOCUMENT_TABLE				= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_DEFS						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_LAYER_DEF					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_IMAGE_DEF					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BLOCK_DEF					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_DOCUMENT_DEF				= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_BIMDEFS						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMACABAMENTOPAREDE_DEF 	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMACABAMENTOPISO_DEF 		= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMACABAMENTOPORTA_DEF		= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMACABAMENTOJANELA_DEF		= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_ODATA						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_PARAMELETRICO_ODATA			= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MEMORIACALCULOITEM_ODATA	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_PERFILDRENAGEMITEM_ODATA	= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_GEOMPOINT					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_AREACONTRIBUICAO_GEOMPOINT	= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_ENTITIES					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_POINT						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_LINE						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_RECTANGLE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_CIRCLE						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_ARC							= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_TEXT						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_POLYGON						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_POLYLINE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_SHAPE						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_INSERTIMAGE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_INSERTBLOCK					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_PROJECT_DEF					= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_ENTITIES3D					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BOX3D						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_CILINDER3D					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_SPHERE3D					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_TORUS3D						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_CONE3D						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_TRONCOCONE3D				= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_BIMENTITIES					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMPAREDE					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMPORTA					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMPDUPLA					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMJANELA					= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMPISO						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMAREA						= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_BIMPIPE						= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_BIMAREATABLE				= AppDefs.OBJTYPE_COUNT++;
	//
	public static final int OBJTYPE_MODENTITIES					= AppDefs.OBJTYPE_COUNT++;
	//ARQUITETURA
	public static final int OBJTYPE_MODARQINSEREPONTO			= AppDefs.OBJTYPE_COUNT++;
	//ESGOTO
	public static final int OBJTYPE_MODESCAIXAINSPECAO			= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODESANOTACAOCAIXAINSPECAO	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODESLIGACAOCAIXAINSPECAO	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODESINSEREPONTO			= AppDefs.OBJTYPE_COUNT++;
	//AGUAS_PLUVIAIS
	public static final int OBJTYPE_MODAPCAIXAINSPECAO			= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODAPANOTACAOCAIXAINSPECAO	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODAPLIGACAOCAIXAINSPECAO	= AppDefs.OBJTYPE_COUNT++;
	//DRENAGEM
	public static final int OBJTYPE_MODDRCAIXAINSPECAO			= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODDRANOTACAOCAIXAINSPECAO	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODDRLIGACAOCAIXAINSPECAO	= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODDRAREACONTRIBUICAO		= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODDRPERFILDRENAGEM			= AppDefs.OBJTYPE_COUNT++;
	public static final int OBJTYPE_MODDRMEMORIACALCULO			= AppDefs.OBJTYPE_COUNT++;
	//ELETRICA
	public static final int OBJTYPE_MODELINSEREPONTO			= AppDefs.OBJTYPE_COUNT++;
	//GAS
	public static final int OBJTYPE_MODGINSEREPONTO				= AppDefs.OBJTYPE_COUNT++;
	
	/* OBJECT_TYPES_DEFINITIONS (STRING)
	 */
	public static final String[] ARR_OBJTYPE_STR = {
		"NONE", 
		"ANY", 
		//
		"APPCADMAIN",
		//
		"TABLES",
		"LAYER_TABLE",
		"SHAPE_TABLE",
		"VIEW_TABLE",
		"OBJECTDATA_TABLE",
		"IMAGE_TABLE",
		"BLOCK_TABLE",
		"DOCUMENT_TABLE",
		//
		"DEFS",
		"LAYER_DEF",
		"IMAGE_DEF", 
		"BLOCK_DEF", 
		"DOCUMENT_DEF",
		//
		"BIMDEFS",
		"ACABAMENTOPAREDE_DEF",
		"ACABAMENTOPISO_DEF",
		"ACABAMENTOPORTA_DEF",
		"ACABAMENTOJANELA_DEF",
		//
		"ODATA",
		"PARAMELETRICO_ODATA",
		"MEMORIACALCULOITEM_ODATA",
		"TRECHODRENAGEMITEM_ODATA",
		//
		"GEOMPOINT",
		"AREACONTRIBUICAO_GEOMPOINT",
		//
		"ENTITIES",
		"POINT",
		"LINE",
		"RECTANGLE",
		"CIRCLE",
		"ARC",
		"TEXT",
		"POLYGON",
		"POLYLINE",
		"SHAPE",
		"INSERTIMAGE", 
		"INSERTBLOCK",
		"PROJECT_DEF",
		//
		"ENTITIES3D",
		"BOX3D",
		"CILINDER3D",
		"SPHERE3D",
		"TORUS3D",
		"CONE3D",
		"TRONCOCONE3D",
		//
		"BIMENTITIES",
		"PAREDE",
		"PORTA",
		"PDUPLA",
		"JANELA",
		"PISO",
		"AREA",
		"PIPE",
		//
		"AREATABLE",
		//
		"MODENTITIES",
		//ARQUITETURA
		"ARQ_PONTO",
		//ESGOTO
		"ES_CAIXAINSPECAO",
		"ES_ANOTACAOCAIXAINSPECAO",
		"ES_LIGACAOCAIXAINSPECAO",
		"ES_PONTO",
		//AGUAS_PLUVIAIS
		"AP_CAIXAINSPECAO",
		"AP_ANOTACAOCAIXAINSPECAO",
		"AP_LIGACAOCAIXAINSPECAO",
		//DRENAGEM
		"DR_CAIXAINSPECAO",
		"DR_ANOTACAOCAIXAINSPECAO",
		"DR_LIGACAOCAIXAINSPECAO",
		"DR_AREACONTRIBUICAO",
		"DR_PERFILDRENAGEM",
		"DR_MEMORIACALCULO",
		//ELETRICA
		"EL_PONTO",
		//GAS
		"G_PONTO"
	};
	
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
	public static Color BACKGROUNDCOLOR = new Color(0x00, 0x00, 0x00);						//BLACK
	public static Color BASEPLANCOLOR = new Color(0x80, 0x80, 0x80);						//DARK_GRAY
	public static Color CURSORCOLOR = new Color(0xE0, 0xE0, 0xE0);							//LIGHT_GRAY_2
	public static Color GRIDCOLOR = new Color(0xA0, 0xA0, 0xA0);							//LIGHT_GRAY_1
	public static Color BLIPCOLOR = new Color(0xA0, 0xA0, 0xA0);							//LIGHT_GRAY_1
	public static Color CURRENTSELECTENTITYCOLOR = new Color(0xF0, 0x80, 0x80);				//LIGHT_CORAL
	public static Color GRIPOBJECTCOLOR_SELECTMODE = new Color(0x64, 0x95, 0xED);			//CORNFLOWER_BLUE
	public static Color DRAGOBJECTCOLOR_SELECTMODE = new Color(0xFF, 0xEB, 0xCD);			//BLANCHED_ALMOND
	public static Color HOVEROBJECTCOLOR_SELECTMODE = new Color(0xFF, 0xB6, 0xC1);			//LIGHT_PINK
	public static Color SELECTOBJECTCOLOR_SELECTMODE = new Color(0xF0, 0x80, 0x80);			//LIGHT_CORAL
	public static Color RUBBERBANDCOLOR_PICKMODE = new Color(0xFF, 0xEB, 0xCD);				//BLANCHED_ALMOND
	public static Color RUBBERBANDCOLOR_ZOOMMODE = new Color(0xC0, 0xC0, 0xC0);				//SILVER
	//
	public static Color WALLFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);						//GRAY
	public static Color WALLFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);						//PURPLE
	public static Color WALLFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);						//OLIVE
	//
	public static Color FLOORFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);					//GRAY
	public static Color FLOORFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);					//PURPLE
	public static Color FLOORFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);					//OLIVE
	//
	public static Color DOORFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);						//GRAY
	public static Color DOORFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);						//PURPLE
	public static Color DOORFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);						//OLIVE
	//
	public static Color DOOROPPENINGCOLOR1 = new Color(0xFF, 0xFF, 0xFF);					//WHITE
	//
	public static Color WINDOWFINISHCOLOR1 = new Color(0x80, 0x80, 0x80);					//GRAY
	public static Color WINDOWFINISHCOLOR2 = new Color(0x80, 0x00, 0x80);					//PURPLE
	public static Color WINDOWFINISHCOLOR3 = new Color(0x80, 0x80, 0x00);					//OLIVE
	//
	public static Color WINDOWOPPENINGCOLOR1 = new Color(0x00, 0xFF, 0xFF);					//CYAN
	//
	public static Color DRPERFIL_MARGEM_COLOR1 = new Color(0xFF, 0xFF, 0xFF);				//WHITE
	public static Color DRPERFIL_CAIXAINSPECAO_COLOR1 = new Color(0xFF, 0x00, 0x00);		//RED
	public static Color DRPERFIL_CAIXAINSPECAOEIXO_COLOR1 = new Color(0xFF, 0x00, 0xFF);	//MAGENTA
	public static Color DRPERFIL_CAIXAINSPECAOTEXTO_COLOR1 = new Color(0x00, 0xFF, 0x00);	//GREEN
	public static Color DRPERFIL_TEXTOTITULO_COLOR1 = new Color(0x00, 0xFF, 0xFF);			//CYAN
	public static Color DRPERFIL_TUBULACAO_COLOR1 = new Color(0x00, 0xFF, 0xFF);			//CYAN
	public static Color DRPERFIL_TERRENO_COLOR1 = new Color(0xFF, 0xAA, 0xAA);				//RED11
	
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
	public static BorderStrokeVO CURRENTSELECTENTITYLTYPE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO DRAGOBJECTLTYPE_SELECTMODE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_DOTTED];
	public static BorderStrokeVO SELECTOBJECTLTYPE_SELECTMODE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO HOVEROBJECTLTYPE_SELECTMODE = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	//
	public static BorderStrokeVO DRPERFIL_CAIXAINSPECAO_BORDERSTROKE1 = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO DRPERFIL_CAIXAINSPECAOEIXO_BORDERSTROKE1 = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_HIDDEN];
	public static BorderStrokeVO DRPERFIL_CAIXAINSPECAOTEXTO_BORDERSTROKE1 = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO DRPERFIL_TEXTOTITULO_BORDERSTROKE1 = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO DRPERFIL_TUBULACAO_BORDERSTROKE1 = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	public static BorderStrokeVO DRPERFIL_TERRENO_BORDERSTROKE1 = AppDefs.ARR_LTYPE_TABLE[LTYPEINDEX_SOLID];
	
	/* DISPLAY COORDS DEFINITION 
	 */
	public static String DISPCORDS_FONTFAMILY = Font.SANS_SERIF;
	public static Color DISPCOORDS_COLOR = new Color(0xFF, 0xFF, 0xFF);
	public static int DISPCOORDS_HEIGHT = 12;
	public static int DISPCOORDS_VERTICALPOS = 24;
	
	//SCHEMANAME_DEFAULT
	//	
	public static final String DEF_SCHEMAPREFIX_DEFAULT = "aix_";	
	public static final String DEF_SCHEMACOPYPREFIX_DEFAULT = "copy_of_";	
	public static final String DEF_SCHEMANAME_DEFAULT = "sample%s";	
	public static final String DEF_SCHEMAFILE_DEFAULT = "aix_sample%s.aix";
	
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
	//
	public static final double MATHVAL_360d = 360.0;
	public static final double MATHVAL_180d = 180.0;
	public static final double MATHVAL_90d = 90.0;
	public static final double MATHVAL_60d = 60.0;
	public static final double MATHVAL_45d = 45.0;
	public static final double MATHVAL_30d = 30.0;
	
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
	public static final String MNU_FILE_DXFIN = "DXF In...";
	public static final String MNU_FILE_DXFOUT = "DXF Out...";
	public static final String MNU_FILE_INSERTIMAGE = "Insert Image...";
	public static final String MNU_FILE_SETUP = "Setup...";
	public static final String MNU_FILE_PRINT = "Print...";
	public static final String MNU_FILE_PURGEALL = "Purge All...";
	public static final String MNU_FILE_STOP = "Stop";
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
	//MENU: ARQ1 (ARQUITETURA 1)
	public static final String MNU_ARQ1 = "Arquitetura 1";
	public static final String MNU_ARQ1_PILARRETANGULAR = "Pilar Retangular";
	public static final String MNU_ARQ1_PILARCIRCULAR = "Pilar Circular";
	public static final String MNU_ARQ1_PISO = "Piso";
	public static final String MNU_ARQ1_PAREDE = "Parede";
	public static final String MNU_ARQ1_AMBIENTE1 = "Ambiente";
	public static final String MNU_ARQ1_AMBIENTE2 = "Ambiente 2-Paredes";
	public static final String MNU_ARQ1_AMBIENTE3 = "Ambiente 3-Paredes";
	public static final String MNU_ARQ1_MALHA = "Malha";
	public static final String MNU_ARQ1_ABERTURA = "Abertura";
	public static final String MNU_ARQ1_PORTA = "Porta";
	public static final String MNU_ARQ1_PDUPLA = "Porta Dupla";
	public static final String MNU_ARQ1_PCORRER = "Porta Correr";
	public static final String MNU_ARQ1_JANELA = "Janela";
	public static final String MNU_ARQ1_AREA = "Area";
	//MENU: ARQ2 (ARQUITETURA 2)
	public static final String MNU_ARQ2 = "Arquitetura 2";
	public static final String MNU_ARQ2_BIDE = "Bide";
	public static final String MNU_ARQ2_VASOSANITARIO = "Vaso Sanitario";
	public static final String MNU_ARQ2_VASOCAIXAACLOPADA = "Vaso Caixa Aclopada";
	public static final String MNU_ARQ2_LAVATORIOGRANDE = "Lavatorio Grande";
	public static final String MNU_ARQ2_LAVATORIOPEQUENO = "Lavatorio Pequeno";
	public static final String MNU_ARQ2_LAVATORIOBANCA = "Lavatorio para Banca";
	public static final String MNU_ARQ2_CHUVEIRO = "Chuveiro";
	public static final String MNU_ARQ2_MICTORIO = "Mictorio";
	public static final String MNU_ARQ2_PIASIMPLES = "Pia Simples";
	public static final String MNU_ARQ2_PIADUPLA = "Pia Dupla";
	public static final String MNU_ARQ2_FOGAO4BOCAS = "Fogao 4 Bocas";
	public static final String MNU_ARQ2_FOGAO6BOCAS = "Fogao 6 Bocas";
	public static final String MNU_ARQ2_GELADEIRA = "Geladeira";
	public static final String MNU_ARQ2_LAVADOURAROUPA = "Lavadoura Roupa";
	public static final String MNU_ARQ2_TANQUE = "Tanque";
	public static final String MNU_ARQ2_AQUECEDOR = "Aquecedor";
	public static final String MNU_ARQ2_BOILER = "Boiler";
	public static final String MNU_ARQ2_BOMBARECALQUE = "Bomba Recalque";
	public static final String MNU_ARQ2_BOMBAAGUASSERVIDAS = "Bomba Aguas Servidas";
	//MENU: ES1 (ESGOTO 1)
	public static final String MNU_ES1 = "Esgoto 1";
	public static final String MNU_ES1_INSERE_CI = "Inserir Caixas de Inspecao";
	public static final String MNU_ES1_LIGACAO_CI = "Ligacao Caixas de Inspecao";
	public static final String MNU_ES1_VERIF_LIGACAO_CI_ONOFF = "Verificar Ligacoes (On/Off)";
	public static final String MNU_ES1_DIMENSIONA_CI = "Dimensionar Rede de Esgoto...";
	public static final String MNU_ES1_GERAR_PLANILHA_CALCULO_CI = "Gerar Planilha Calculo";
	public static final String MNU_ES1_GERAR_PLANTA_PERFIS_CI = "Gerar Plantas de Perfis";
	public static final String MNU_ES1_ANOTACAO_INDIVIDUAL_CI = "Inserir Anotacao";
	//MENU: ES2 (ESGOTO 2)
	public static final String MNU_ES2 = "Esgoto 2";
	public static final String MNU_ES2_COLUNA_CD50 = "Centro-Diametro 50mm";
	public static final String MNU_ES2_COLUNA_CD75 = "Centro-Diametro 75mm";
	public static final String MNU_ES2_COLUNA_CD100 = "Centro-Diametro 100mm";
	public static final String MNU_ES2_COLUNA_CD150 = "Centro-Diametro 150mm";
	public static final String MNU_ES2_COLUNA_TD50 = "Tangente-Diametro 50mm";
	public static final String MNU_ES2_COLUNA_TD75 = "Tangente-Diametro 75mm";
	public static final String MNU_ES2_COLUNA_TD100 = "Tangente-Diametro 100mm";
	public static final String MNU_ES2_COLUNA_TD150 = "Tangente-Diametro 150mm";
	public static final String MNU_ES2_COLUNA_TTD50 = "Tangente-Tangente-Diametro 50mm";
	public static final String MNU_ES2_COLUNA_TTD75 = "Tangente-Tangente-Diametro 75mm";
	public static final String MNU_ES2_COLUNA_TTD100 = "Tangente-Tangente-Diametro 100mm";
	public static final String MNU_ES2_COLUNA_TTD150 = "Tangente-Tangente-Diametro 150mm";
	//MENU: ES3 (ESGOTO 3)
	public static final String MNU_ES3 = "Esgoto 3";
	public static final String MNU_ES3_TUBULACAO_PRIMARIO_75 = "Primario 75mm";
	public static final String MNU_ES3_TUBULACAO_PRIMARIO_100 = "Primario 100mm";
	public static final String MNU_ES3_TUBULACAO_PRIMARIO_150 = "Primario 150mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_40 = "Secundario 40mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_50 = "Secundario 50mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_75 = "Secundario 75mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_100 = "Secundario 100mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_150 = "Secundario 150mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_40 = "Secundario de Gordura 40mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_50 = "Secundario de Gordura 50mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_75 = "Secundario de Gordura 75mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_100 = "Secundario de Gordura 100mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_150 = "Secundario de Gordura 150mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_40 = "Secundario Sabao 40mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_50 = "Secundario Sabao 50mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_75 = "Secundario Sabao 75mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_100 = "Secundario Sabao 100mm";
	public static final String MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_150 = "Secundario Sabao 150mm";
	public static final String MNU_ES3_TUBULACAO_VENTILACAO_50 = "Ventilacao 50mm";
	public static final String MNU_ES3_TUBULACAO_VENTILACAO_75 = "Ventilacao 75mm";	
	public static final String MNU_ES3_TUBULACAO_VENTILACAO_100 = "Ventilacao 100mm";
	//MENU: ES4 (ESGOTO 4)
	public static final String MNU_ES4 = "Esgoto 4";
	public static final String MNU_ES4_BUJAO40 = "Bujao 40mm";
	public static final String MNU_ES4_BUJAO50 = "Bujao 50mm";
	public static final String MNU_ES4_BUJAO75 = "Bujao 75mm";
	public static final String MNU_ES4_BUJAO100 = "Bujao 100mm";
	public static final String MNU_ES4_BUJAO150 = "Bujao 150mm";
	public static final String MNU_ES4_CAIXA_INSPECAO = "Caixa de Inspecao";
	public static final String MNU_ES4_CAIXA_PASSAGEM_60X60 = "Caixa de Passagem 60x60";
	public static final String MNU_ES4_CAIXA_GORDURA_DUPLA = "Caixa de Gordura Dupla";
	public static final String MNU_ES4_CAIXA_SINFONADA = "Caixa Sinfornada";
	public static final String MNU_ES4_RALO_CONICO = "Ralo Conico";
	public static final String MNU_ES4_RALO_HEMISFERICO = "Ralo Hemisferico";
	public static final String MNU_ES4_RALO_SINFONADO = "Ralo Sinfonado";
	public static final String MNU_ES4_TUBO_OPERCULADO_40 = "Tubo Operculado 40mm";
	public static final String MNU_ES4_TUBO_OPERCULADO_50 = "Tubo Operculado 50mm";
	public static final String MNU_ES4_TUBO_OPERCULADO_75 = "Tubo Operculado 75mm";
	public static final String MNU_ES4_TUBO_OPERCULADO_100 = "Tubo Operculado 100mm";
	public static final String MNU_ES4_TUBO_OPERCULADO_150 = "Tubo Operculado 150mm";
	public static final String MNU_ES4_FINAL_TUBULACAO = "Final de Tubulacao";
	//MENU: ES5 (ESGOTO 5)
	public static final String MNU_ES5 = "Esgoto 5";
	public static final String MNU_ES5_SETA_INDICADOR_DESCE = "Coluna que Desce";
	public static final String MNU_ES5_SETA_INDICADOR_PASSA = "Coluna que Passa";
	public static final String MNU_ES5_SETA_INDICADOR_SOBE = "Coluna que Sobe";
	public static final String MNU_ES5_SETA_SIMPLES_DESCE = "Seta que Desce";
	public static final String MNU_ES5_SETA_SIMPLES_PASSA = "Seta que Passa";
	public static final String MNU_ES5_SETA_SIMPLES_SOBE = "Seta que Sobe";
	//MENU: AP1 (AGUAS_PLUVIAIS 1)
	public static final String MNU_AP1 = "Aguas Pluviais 1";
	public static final String MNU_AP1_INSERE_CI = "Inserir Caixas de Aguas Pluviais";
	public static final String MNU_AP1_LIGACAO_CI = "Ligacao Caixas de Aguas Pluviais";
	public static final String MNU_AP1_VERIF_LIGACAO_CI_ONOFF = "Verificar Ligacoes (On/Off)";
	public static final String MNU_AP1_DIMENSIONA_CI = "Dimensionar Rede de Aguas Pluviais...";
	public static final String MNU_AP1_GERAR_PLANILHA_CALCULO_CI = "Gerar Planilha Calculo";
	public static final String MNU_AP1_GERAR_PLANTA_PERFIS_CI = "Gerar Plantas de Perfis";
	public static final String MNU_AP1_ANOTACAO_INDIVIDUAL_CI = "Inserir Anotacao";
	//MENU: AP2 (AGUAS_PLUVIAIS 2)
	public static final String MNU_AP2 = "Aguas Pluviais 2";
	public static final String MNU_AP2_COLUNA_CD50 = "Centro-Diametro 50mm";
	public static final String MNU_AP2_COLUNA_CD75 = "Centro-Diametro 75mm";
	public static final String MNU_AP2_COLUNA_CD100 = "Centro-Diametro 100mm";
	public static final String MNU_AP2_COLUNA_CD150 = "Centro-Diametro 150mm";
	public static final String MNU_AP2_COLUNA_TD50 = "Tangente-Diametro 50mm";
	public static final String MNU_AP2_COLUNA_TD75 = "Tangente-Diametro 75mm";
	public static final String MNU_AP2_COLUNA_TD100 = "Tangente-Diametro 100mm";
	public static final String MNU_AP2_COLUNA_TD150 = "Tangente-Diametro 150mm";
	public static final String MNU_AP2_COLUNA_TTD50 = "Tangente-Tangente-Diametro 50mm";
	public static final String MNU_AP2_COLUNA_TTD75 = "Tangente-Tangente-Diametro 75mm";
	public static final String MNU_AP2_COLUNA_TTD100 = "Tangente-Tangente-Diametro 100mm";
	public static final String MNU_AP2_COLUNA_TTD150 = "Tangente-Tangente-Diametro 150mm";
	//ICONMENU: AP3 (AGUAS_PLUVIAIS 3)
	public static final String MNU_AP3 = "Aguas Pluviais 3";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50 = "Aguas Pluviais 50mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75 = "Aguas Pluviais 75mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100 = "Aguas Pluviais 100mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200 = "Aguas Pluviais 150mm-200mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300 = "Aguas Pluviais 250mm-300mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50 = "Aguas de Reuso 50mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75 = "Aguas de Reuso 75mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100 = "Aguas de Reuso 100mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200 = "Aguas de Reuso 150mm-200mm";
	public static final String MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300 = "Aguas de Reuso 250mm-300mm";
	//ICONMENU: AP4 (AGUAS_PLUVIAIS 4)
	public static final String MNU_AP4 = "Aguas Pluviais 4";
	public static final String MNU_AP4_BUJAO40 = "Bujao 40mm";
	public static final String MNU_AP4_BUJAO50 = "Bujao 50mm";
	public static final String MNU_AP4_BUJAO75 = "Bujao 75mm";
	public static final String MNU_AP4_BUJAO100 = "Bujao 100mm";
	public static final String MNU_AP4_BUJAO150 = "Bujao 150mm";
	public static final String MNU_AP4_CAIXA_INSPECAO = "Caixa de Inspecao";
	public static final String MNU_AP4_CAIXA_PASSAGEM_60X60 = "Caixa de Passagem 60x60";
	public static final String MNU_AP4_RALO_CONICO = "Ralo Conico";
	public static final String MNU_AP4_RALO_HEMISFERICO = "Ralo Hemisferico";
	public static final String MNU_AP4_TUBO_OPERCULADO_40 = "Tubo Operculado 40mm";
	public static final String MNU_AP4_TUBO_OPERCULADO_50 = "Tubo Operculado 50mm";
	public static final String MNU_AP4_TUBO_OPERCULADO_75 = "Tubo Operculado 75mm";
	public static final String MNU_AP4_TUBO_OPERCULADO_100 = "Tubo Operculado 100mm";
	public static final String MNU_AP4_TUBO_OPERCULADO_150 = "Tubo Operculado 150mm";
	public static final String MNU_AP4_FINAL_TUBULACAO = "Final de Tubulacao";
	//ICONMENU: AP5 (AGUAS_PLUVIAIS 5)
	public static final String MNU_AP5 = "Aguas Pluviais 5";
	public static final String MNU_AP5_SETA_INDICADOR_DESCE = "Coluna que Desce";
	public static final String MNU_AP5_SETA_INDICADOR_PASSA = "Coluna que Passa";
	public static final String MNU_AP5_SETA_INDICADOR_SOBE = "Coluna que Sobe";
	public static final String MNU_AP5_SETA_SIMPLES_DESCE = "Seta que Desce";
	public static final String MNU_AP5_SETA_SIMPLES_PASSA = "Seta que Passa";
	public static final String MNU_AP5_SETA_SIMPLES_SOBE = "Seta que Sobe";
	//MENU: RDP1 (REDES_PUBLICAS_DRENAGEM 1)
	public static final String MNU_RPD1 = "Drenagem 1";
	public static final String MNU_RDP1_CSV_IMPORT = "Importa Rede Drenagem (CSV)... ";
	public static final String MNU_RDP1_CSV_EXPORT = "Exporta Rede Drenagem (CSV)... ";
	public static final String MNU_RDP1_AREA_CONTRIBUICAO = "Area Contribuicao";
	public static final String MNU_RDP1_INSERE_CI = "Inserir Poco de Visita";
	public static final String MNU_RDP1_PROPRIEDADE_CI = "Propriedade Poco de Visita";
	public static final String MNU_RDP1_LIGACAO_CI = "Ligacao Poco de Visita";
	public static final String MNU_RDP1_VERIF_LIGACAO_CI_ONOFF = "Verificar Ligacoes (On/Off)";
	public static final String MNU_RDP1_DIMENSIONA_CI = "Dimensionar Rede de Drenagem...";
	public static final String MNU_RDP1_GERAR_PLANILHA_CALCULO_CI = "Gerar Planilha Calculo...";
	public static final String MNU_RDP1_ALTERAR_PLANILHA_CALCULO_CI = "Alterar Planilha Calculo...";
	public static final String MNU_RDP1_GERAR_PLANTA_PERFIS_CI = "Gerar Plantas de Perfis";
	public static final String MNU_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO = "Gerar Plantas Area Contribuicao";
	public static final String MNU_RDP1_ANOTACAO_INDIVIDUAL_CI = "Inserir Anotacao";
	//MENU: EL1 (ELETRICA 1)
	public static final String MNU_EL1 = "Eletrica 1";
	public static final String MNU_EL1_INSERE_CAIXA_PASSAGEM_PISO = "Caixa Passagem Piso";
	public static final String MNU_EL1_INSERE_INTERRUPTOR_SIMPLES = "Interruptor Simples";
	public static final String MNU_EL1_INSERE_PONTO_FORCA_PISO = "Ponto Forca Piso";
	public static final String MNU_EL1_INSERE_PONTO_LUZ = "Ponto Luz";
	public static final String MNU_EL1_INSERE_QUADRO_DISTRIBUICAO = "Quadro Distribuicao";
	public static final String MNU_EL1_INSERE_TOMADA_BAIXA = "Tomada Baixa";
	//MENU: EL2 (ELETRICA 2)
	public static final String MNU_EL2 = "Eletrica 2";
	public static final String MNU_EL2_INSERE_ELETRODUTO_TETO = "Eletroduto Teto";
	public static final String MNU_EL2_INSERE_ELETRODUTO_PISO = "Eletroduto Piso";
	public static final String MNU_EL2_INSERE_ELETRODUTO_APARENTE = "Eletroduto Aparente";	
	public static final String MNU_EL2_TROCA_CIRCUITO = "Troca Circuito";
	public static final String MNU_EL2_TROCA_COMANDO = "Troca Comando";
	public static final String MNU_EL2_TROCA_ORIGEM = "Troca Origem";
	//MENU: HID1 (HIDRAULICA 1)
	public static final String MNU_HID1 = "Hidraulica 1";
	public static final String MNU_HID1_DEFINE_TRECHO = "Define Trecho...";	
	public static final String MNU_HID1_DEFINE_PERDA_EQUIPAMENTO = "Perda de Carga no Equipamento...";
	public static final String MNU_HID1_CALCULA_PERDA_CARGA = "Calculo da Perda de Carga...";
	//MENU: HID2 (HIDRAULICA 2)	
	public static final String MNU_HID2 = "Hidraulica 2";
	public static final String MNU_HID2_COLUNA_CD50 = "Centro-Diametro 50mm";
	public static final String MNU_HID2_COLUNA_CD60 = "Centro-Diametro 60mm";
	public static final String MNU_HID2_COLUNA_CD75 = "Centro-Diametro 75mm";
	public static final String MNU_HID2_COLUNA_CD85 = "Centro-Diametro 85mm";
	public static final String MNU_HID2_COLUNA_CD110 = "Centro-Diametro 110mm";
	public static final String MNU_HID2_COLUNA_TD50 = "Tangente-Diametro 50mm";
	public static final String MNU_HID2_COLUNA_TD60 = "Tangente-Diametro 60mm";
	public static final String MNU_HID2_COLUNA_TD75 = "Tangente-Diametro 75mm";
	public static final String MNU_HID2_COLUNA_TD85 = "Tangente-Diametro 85mm";
	public static final String MNU_HID2_COLUNA_TD110 = "Tangente-Diametro 110mm";
	public static final String MNU_HID2_COLUNA_TTD50 = "Tangente-Tangente-Diametro 50mm";
	public static final String MNU_HID2_COLUNA_TTD60 = "Tangente-Tangente-Diametro 60mm";
	public static final String MNU_HID2_COLUNA_TTD75 = "Tangente-Tangente-Diametro 75mm";
	public static final String MNU_HID2_COLUNA_TTD85 = "Tangente-Tangente-Diametro 85mm";
	public static final String MNU_HID2_COLUNA_TTD110 = "Tangente-Tangente-Diametro 110mm";
	//MENU: HID3 (HIDRAULICA 3)	
	public static final String MNU_HID3 = "Hidraulica 3";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_20 = "Tubulacao Agua Fria 20mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_25 = "Tubulacao Agua Fria 25mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_32 = "Tubulacao Agua Fria 32mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_40 = "Tubulacao Agua Fria 40mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_50 = "Tubulacao Agua Fria 50mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_60 = "Tubulacao Agua Fria 60mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_75 = "Tubulacao Agua Fria 75mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_85 = "Tubulacao Agua Fria 85mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_110 = "Tubulacao Agua Fria 110mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_22 = "Tubulacao Agua Quente 22mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_28 = "Tubulacao Agua Quente 28mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_35 = "Tubulacao Agua Quente 35mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_42 = "Tubulacao Agua Quente 42mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_54 = "Tubulacao Agua Quente 54mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_60 = "Tubulacao Agua Quente 60mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_73 = "Tubulacao Agua Quente 73mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_89 = "Tubulacao Agua Quente 89mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_114 = "Tubulacao Agua Quente 114mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_PEX_20 = "Tubulacao Agua Fria - PEX 20mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_PEX_25 = "Tubulacao Agua Fria - PEX 25mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_FRIA_PEX_32 = "Tubulacao Agua Fria - PEX 32mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_20 = "Tubulacao Agua Quente - PEX 20mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_25 = "Tubulacao Agua Quente - PEX 25mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_32 = "Tubulacao Agua Quente - PEX 32mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_20 = "Tubulacao Agua Reuso 20mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_25 = "Tubulacao Agua Reuso 25mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_32 = "Tubulacao Agua Reuso 32mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_40 = "Tubulacao Agua Reuso 40mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_50 = "Tubulacao Agua Reuso 50mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_60 = "Tubulacao Agua Reuso 60mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_75 = "Tubulacao Agua Reuso 75mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_85 = "Tubulacao Agua Reuso 85mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_REUSO_110 = "Tubulacao Agua Reuso 110mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_20 = "Tubulacao Agua Tratada 20mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_25 = "Tubulacao Agua Tratada 25mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_32 = "Tubulacao Agua Tratada 32mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_40 = "Tubulacao Agua Tratada 40mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_50 = "Tubulacao Agua Tratada 50mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_60 = "Tubulacao Agua Tratada 60mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_75 = "Tubulacao Agua Tratada 75mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_85 = "Tubulacao Agua Tratada 85mm";
	public static final String MNU_HID3_TUBULACAO_AGUA_TRATADA_110 = "Tubulacao Agua Tratada 110mm";
	//MENU: HID4 (HIDRAULICA 4)	
	public static final String MNU_HID4 = "Hidraulica 4";
	public static final String MNU_HID4_BUCHA_REDUCAO = "Bucha Reducao";
	public static final String MNU_HID4_REGISTRO_AGUA_FRIA = "Registro Agua Fria";
	public static final String MNU_HID4_REGISTRO_AGUA_QUENTE = "Registro Agua Quente";
	public static final String MNU_HID4_REGISTRO_3_4_30 = "Registro 3/4 pol (h=30cm)";
	public static final String MNU_HID4_TORNEIRA_LAVAGEM = "Torneira_Lavagem";
	public static final String MNU_HID4_FINAL_TUBULACAO = "Final Tubulacao";
	//MENU: HID5 (HIDRAULICA 5)	
	public static final String MNU_HID5 = "Hidraulica 5";
	public static final String MNU_HID5_SETA_INDICADOR_DESCE = "Indicador Desce";
	public static final String MNU_HID5_SETA_INDICADOR_PASSA = "Indicador Passa";
	public static final String MNU_HID5_SETA_INDICADOR_SOBE = "Indicador Sobe";
	public static final String MNU_HID5_SETA_SIMPLES_DESCE = "Seta Desce";
	public static final String MNU_HID5_SETA_SIMPLES_PASSA = "Seta Passa";
	public static final String MNU_HID5_SETA_SIMPLES_SOBE = "Seta Sobe";
	//MENU: G1 (GAS 1)
	public static final String MNU_G1 = "Gas 1";
	public static final String MNU_G1_INSERE_TB_GAS_PISO = "Tubulacao Piso";
	public static final String MNU_G1_INSERE_TB_GAS_TETO = "Tubulacao Teto";
	public static final String MNU_G1_INSERE_CANALETA_GAS = "Insere Canaleta";
	public static final String MNU_G1_INSERE_SEPTO_GAS = "Insere Septo Ventilado";
	public static final String MNU_G1_INSERE_PRUMADA_GAS = "Insere Prumadas";
	public static final String MNU_G1_INSERE_PONTO_GAS = "Ponto Gas";
	public static final String MNU_G1_INSERE_SETA_SIMPLES_PASSA = "Seta Passa";
	public static final String MNU_G1_INSERE_SETA_SIMPLES_SOBE = "Seta Sobe";
	public static final String MNU_G1_INSERE_SETA_SIMPLES_DESCE = "Seta Desce";
	public static final String MNU_G1_INSERE_INDICADOR_ECONOMIA = "Indicador Economia";
	//MENU: BLOCKS
	public static final String MNU_BLOCKS = "Blocks";
	public static final String MNU_BLOCKS_INSERTSHAPE = "Insert Shape";
	public static final String MNU_BLOCKS_CREATESHAPE = "Create Shape";
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
	public static final String MNU_DRAW1_CILINDER3D = "Cilinder 3D";
	public static final String MNU_DRAW1_CONE3D = "Cone 3D";
	public static final String MNU_DRAW1_TRONCOCONE3D = "Tronco Cone 3D";
	public static final String MNU_DRAW1_TORUS3D = "Torus 3D";
	public static final String MNU_DRAW1_SPHERE3D = "Sphere 3D";
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
	public static final String MNU_DISPLAY_VIEWTOP = "View Top";
	public static final String MNU_DISPLAY_VIEWBOTTOM = "View Bottom";
	public static final String MNU_DISPLAY_VIEWBACK = "View Back";
	public static final String MNU_DISPLAY_VIEWFRONT = "View Front";
	public static final String MNU_DISPLAY_VIEWLEFT = "View Left";
	public static final String MNU_DISPLAY_VIEWRIGHT = "View Right";
	//public static final String MNU_DISPLAY_MOVEFORWARD = "Move Forward";
	//public static final String MNU_DISPLAY_MOVEBACKWARD = "Move Backward";
	//public static final String MNU_DISPLAY_TURNLEFT = "Turn Left";
	//public static final String MNU_DISPLAY_TURNRIGHT = "Turn Right";
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
	public static final String MNU_MENUS_APMENU = "Aguas Pluviais";
	public static final String MNU_MENUS_RPDMENU = "Redes Publicas Drenagem";
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
	public static final String ICONMNU_FILE_NEW = "/br/com/tlmv/aicadxapp/res/cad/ico_file_new.jpg";
	public static final String ICONMNU_FILE_OPEN = "/br/com/tlmv/aicadxapp/res/cad/ico_file_open.jpg";
	public static final String ICONMNU_FILE_CLOSE = "/br/com/tlmv/aicadxapp/res/cad/ico_file_close.jpg";
	public static final String ICONMNU_FILE_SAVE = "/br/com/tlmv/aicadxapp/res/cad/ico_file_save.jpg";
	public static final String ICONMNU_FILE_SAVEAS = "/br/com/tlmv/aicadxapp/res/cad/ico_file_saveas.jpg";
	public static final String ICONMNU_FILE_DXFIN = "/br/com/tlmv/aicadxapp/res/cad/ico_file_dxfin.jpg";
	public static final String ICONMNU_FILE_DXFOUT = "/br/com/tlmv/aicadxapp/res/cad/ico_file_dxfout.jpg";
	public static final String ICONMNU_FILE_INSERTIMAGE = "/br/com/tlmv/aicadxapp/res/cad/ico_file_insert_image.jpg";
	public static final String ICONMNU_FILE_SETUP = "/br/com/tlmv/aicadxapp/res/cad/ico_file_setup.jpg";
	public static final String ICONMNU_FILE_PRINT = "/br/com/tlmv/aicadxapp/res/cad/ico_file_print.jpg";
	public static final String ICONMNU_FILE_STOP = "/br/com/tlmv/aicadxapp/res/cad/ico_file_stop.jpg";
	public static final String ICONMNU_FILE_EXIT = "/br/com/tlmv/aicadxapp/res/cad/ico_file_exit.jpg";
	//MENU: ARQ1 (ARQUITETURA 1)
	public static final String ICONMNU_ARQ1_PILARRETANGULAR = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_pilar_retangular.jpg";
	public static final String ICONMNU_ARQ1_PILARCIRCULAR = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_pilar_circular.jpg";
	public static final String ICONMNU_ARQ1_PISO = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_piso.jpg";
	public static final String ICONMNU_ARQ1_PAREDE = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_parede.jpg";
	public static final String ICONMNU_ARQ1_AMBIENTE1 = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_ambiente1.jpg";
	public static final String ICONMNU_ARQ1_AMBIENTE2 = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_ambiente2.jpg";
	public static final String ICONMNU_ARQ1_AMBIENTE3 = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_ambiente3.jpg";
	public static final String ICONMNU_ARQ1_MALHA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_malha.jpg";
	public static final String ICONMNU_ARQ1_ABERTURA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_abertura.jpg";
	public static final String ICONMNU_ARQ1_PORTA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_porta.jpg";
	public static final String ICONMNU_ARQ1_PDUPLA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_pdupla.jpg";
	public static final String ICONMNU_ARQ1_PCORRER = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_pcorrer.jpg";
	public static final String ICONMNU_ARQ1_JANELA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_janela.jpg";
	public static final String ICONMNU_ARQ1_AREA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_area.jpg";
	//MENU: ARQ2 (ARQUITETURA 2)
	public static final String ICONMNU_ARQ2_BIDE = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_bide.jpg";
	public static final String ICONMNU_ARQ2_VASOSANITARIO = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_vaso_sanitario.jpg";
	public static final String ICONMNU_ARQ2_VASOCAIXAACLOPADA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_vaso_sanitario_caixa_aclopada.jpg";
	public static final String ICONMNU_ARQ2_LAVATORIOGRANDE = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_lavatorio_grande.jpg";
	public static final String ICONMNU_ARQ2_LAVATORIOPEQUENO = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_lavatorio_pequeno.jpg";
	public static final String ICONMNU_ARQ2_LAVATORIOBANCA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_lavatorio_banca.jpg";
	public static final String ICONMNU_ARQ2_CHUVEIRO = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_chuveiro.jpg";
	public static final String ICONMNU_ARQ2_MICTORIO = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_mictorio.jpg";
	public static final String ICONMNU_ARQ2_PIASIMPLES = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_pia_simples.jpg";
	public static final String ICONMNU_ARQ2_PIADUPLA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_pia_dupla.jpg";
	public static final String ICONMNU_ARQ2_FOGAO4BOCAS = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_fogao_4bocas.jpg";
	public static final String ICONMNU_ARQ2_FOGAO6BOCAS = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_fogao_6bocas.jpg";
	public static final String ICONMNU_ARQ2_GELADEIRA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_geladeira.jpg";
	public static final String ICONMNU_ARQ2_LAVADOURAROUPA = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_lavadoura_roupa.jpg";
	public static final String ICONMNU_ARQ2_TANQUE = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_tanque.jpg";
	public static final String ICONMNU_ARQ2_AQUECEDOR = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_aquecedor.jpg";
	public static final String ICONMNU_ARQ2_BOILER = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_boiler.jpg";
	public static final String ICONMNU_ARQ2_BOMBARECALQUE = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_bomba_recalque.jpg";
	public static final String ICONMNU_ARQ2_BOMBAAGUASSERVIDAS = "/br/com/tlmv/aicadxmod/res/cad/arquitetura/ico_arq_bomba_simples.jpg";
	//ICONMENU: ES1 (ESGOTO 1)
	public static final String ICONMNU_ES1_INSERE_CI = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_inserir_caixas_inspecao.jpg";
	public static final String ICONMNU_ES1_LIGACAO_CI = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_conectar_caixas_inspecao.jpg";
	public static final String ICONMNU_ES1_VERIF_LIGACAO_CI_ONOFF = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_verificar_ligacoes_onoff.jpg";
	public static final String ICONMNU_ES1_DIMENSIONA_CI = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_dimensionar_rede_drenagem.jpg";
	public static final String ICONMNU_ES1_GERAR_PLANILHA_CALCULO_CI = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_gerar_planilha_calculo.jpg";
	public static final String ICONMNU_ES1_GERAR_PLANTA_PERFIS_CI = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_gerar_plantas_perfis.jpg";
	public static final String ICONMNU_ES1_ANOTACAO_INDIVIDUAL_CI = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_inserir_anotacao_ci.jpg";
	//ICONMENU: ES2 (ESGOTO 2)
	public static final String ICONMNU_ES2_COLUNA_CD50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_cd_50.jpg";
	public static final String ICONMNU_ES2_COLUNA_CD75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_cd_75.jpg";
	public static final String ICONMNU_ES2_COLUNA_CD100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_cd_100.jpg";
	public static final String ICONMNU_ES2_COLUNA_CD150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_cd_150.jpg";
	public static final String ICONMNU_ES2_COLUNA_TD50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_td_50.jpg";
	public static final String ICONMNU_ES2_COLUNA_TD75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_td_75.jpg";
	public static final String ICONMNU_ES2_COLUNA_TD100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_td_100.jpg";
	public static final String ICONMNU_ES2_COLUNA_TD150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_td_150.jpg";
	public static final String ICONMNU_ES2_COLUNA_TTD50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_ttd_50.jpg";
	public static final String ICONMNU_ES2_COLUNA_TTD75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_ttd_75.jpg";
	public static final String ICONMNU_ES2_COLUNA_TTD100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_ttd_100.jpg";
	public static final String ICONMNU_ES2_COLUNA_TTD150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_coluna_ttd_150.jpg";
	//ICONMENU: ES3 (ESGOTO 3)
	public static final String ICONMNU_ES3_TUBULACAO_PRIMARIO_75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_primario_75.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_PRIMARIO_100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_primario_100.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_PRIMARIO_150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_primario_150.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_40 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_40.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_50.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_75.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_100.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_150.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_40 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_gordura_40.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_gordura_50.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_gordura_75.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_gordura_100.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_gordura_150.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_40 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_sabao_40.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_sabao_50.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_sabao_75.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_sabao_100.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_esgoto_secundario_de_sabao_150.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_VENTILACAO_50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_ventilacao_50.jpg";
	public static final String ICONMNU_ES3_TUBULACAO_VENTILACAO_75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_ventilacao_75.jpg";	
	public static final String ICONMNU_ES3_TUBULACAO_VENTILACAO_100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubulacao_ventilacao_100.jpg";
	//ICONMENU: ES4 (ESGOTO 4)
	public static final String ICONMNU_ES4_BUJAO40 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_bujao_40.jpg";
	public static final String ICONMNU_ES4_BUJAO50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_bujao_50.jpg";
	public static final String ICONMNU_ES4_BUJAO75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_bujao_75.jpg";
	public static final String ICONMNU_ES4_BUJAO100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_bujao_100.jpg";
	public static final String ICONMNU_ES4_BUJAO150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_bujao_150.jpg";
	public static final String ICONMNU_ES4_CAIXA_INSPECAO = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_caixa_inspecao.jpg";
	public static final String ICONMNU_ES4_CAIXA_PASSAGEM_60X60 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_caixa_passagem_60_60.jpg";
	public static final String ICONMNU_ES4_CAIXA_GORDURA_DUPLA = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_caixa_gordura_dupla.jpg";
	public static final String ICONMNU_ES4_CAIXA_SINFONADA = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_caixa_sinfonada.jpg";
	public static final String ICONMNU_ES4_RALO_CONICO = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_ralo_conico.jpg";
	public static final String ICONMNU_ES4_RALO_HEMISFERICO = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_ralo_hemisferico.jpg";
	public static final String ICONMNU_ES4_RALO_SINFONADO = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_ralo_sinfonado.jpg";
	public static final String ICONMNU_ES4_TUBO_OPERCULADO_100 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubo_operculado_100.jpg";
	public static final String ICONMNU_ES4_TUBO_OPERCULADO_150 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubo_operculado_150.jpg";
	public static final String ICONMNU_ES4_TUBO_OPERCULADO_40 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubo_operculado_40.jpg";
	public static final String ICONMNU_ES4_TUBO_OPERCULADO_50 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubo_operculado_50.jpg";
	public static final String ICONMNU_ES4_TUBO_OPERCULADO_75 = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_tubo_operculado_75.jpg";
	public static final String ICONMNU_ES4_FINAL_TUBULACAO = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_final_tubulacao.jpg";
	//ICONMENU: ES5 (ESGOTO 5)
	public static final String ICONMNU_ES5_SETA_INDICADOR_DESCE = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_seta_indicador_desce.jpg";
	public static final String ICONMNU_ES5_SETA_INDICADOR_PASSA = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_seta_indicador_passa.jpg";
	public static final String ICONMNU_ES5_SETA_INDICADOR_SOBE = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_seta_indicador_sobe.jpg";
	public static final String ICONMNU_ES5_SETA_SIMPLES_DESCE = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_seta_simples_desce.jpg";
	public static final String ICONMNU_ES5_SETA_SIMPLES_PASSA = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_seta_simples_passa.jpg";
	public static final String ICONMNU_ES5_SETA_SIMPLES_SOBE = "/br/com/tlmv/aicadxmod/res/cad/esgoto/ico_es_seta_simples_sobe.jpg";
	//ICONMENU: AP1 (AGUAS_PLUVIAIS 1)
	public static final String ICONMNU_AP1_INSERE_CI = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_inserir_caixas_inspecao.jpg";
	public static final String ICONMNU_AP1_LIGACAO_CI = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_conectar_caixas_inspecao.jpg";
	public static final String ICONMNU_AP1_VERIF_LIGACAO_CI_ONOFF = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_verificar_ligacoes_onoff.jpg";
	public static final String ICONMNU_AP1_DIMENSIONA_CI = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_dimensionar_rede_drenagem.jpg";
	public static final String ICONMNU_AP1_GERAR_PLANILHA_CALCULO_CI = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_gerar_planilha_calculo.jpg";
	public static final String ICONMNU_AP1_GERAR_PLANTA_PERFIS_CI = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_gerar_plantas_perfis.jpg";
	public static final String ICONMNU_AP1_ANOTACAO_INDIVIDUAL_CI = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_inserir_anotacao_ci.jpg";
	//ICONMENU: AP2 (AGUAS_PLUVIAIS 2)
	public static final String ICONMNU_AP2_COLUNA_CD100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_cd_100.jpg";
	public static final String ICONMNU_AP2_COLUNA_CD150 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_cd_150.jpg";
	public static final String ICONMNU_AP2_COLUNA_CD50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_cd_50.jpg";
	public static final String ICONMNU_AP2_COLUNA_CD75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_cd_75.jpg";
	public static final String ICONMNU_AP2_COLUNA_TD100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_td_100.jpg";
	public static final String ICONMNU_AP2_COLUNA_TD150 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_td_150.jpg";
	public static final String ICONMNU_AP2_COLUNA_TD50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_td_50.jpg";
	public static final String ICONMNU_AP2_COLUNA_TD75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_td_75.jpg";
	public static final String ICONMNU_AP2_COLUNA_TTD100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_ttd_100.jpg";
	public static final String ICONMNU_AP2_COLUNA_TTD150 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_ttd_150.jpg";
	public static final String ICONMNU_AP2_COLUNA_TTD50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_ttd_50.jpg";
	public static final String ICONMNU_AP2_COLUNA_TTD75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_coluna_ttd_75.jpg";
	//ICONMENU: AP3 (AGUAS_PLUVIAIS 3)
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_100.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_150_200.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_250_300.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_50.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_75.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_de_reuso_100.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_de_reuso_150_200.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_de_reuso_250_300.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_de_reuso_50.jpg";
	public static final String ICONMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubulacao_aguas_pluviais_de_reuso_75.jpg";
	//ICONMENU: AP4 (AGUAS_PLUVIAIS 4)
	public static final String ICONMNU_AP4_BUJAO100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_bujao_100.jpg";
	public static final String ICONMNU_AP4_BUJAO150 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_bujao_150.jpg";
	public static final String ICONMNU_AP4_BUJAO40 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_bujao_40.jpg";
	public static final String ICONMNU_AP4_BUJAO50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_bujao_50.jpg";
	public static final String ICONMNU_AP4_BUJAO75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_bujao_75.jpg";
	public static final String ICONMNU_AP4_CAIXA_INSPECAO = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_caixa_inspecao.jpg";
	public static final String ICONMNU_AP4_CAIXA_PASSAGEM_60X60 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_caixa_passagem_60_60.jpg";
	public static final String ICONMNU_AP4_FINAL_TUBULACAO = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_final_tubulacao.jpg";
	public static final String ICONMNU_AP4_RALO_CONICO = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_ralo_conico.jpg";
	public static final String ICONMNU_AP4_RALO_HEMISFERICO = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_ralo_hemisferico.jpg";
	public static final String ICONMNU_AP4_TUBO_OPERCULADO_100 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubo_operculado_100.jpg";
	public static final String ICONMNU_AP4_TUBO_OPERCULADO_150 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubo_operculado_150.jpg";
	public static final String ICONMNU_AP4_TUBO_OPERCULADO_40 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubo_operculado_40.jpg";
	public static final String ICONMNU_AP4_TUBO_OPERCULADO_50 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubo_operculado_50.jpg";
	public static final String ICONMNU_AP4_TUBO_OPERCULADO_75 = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_tubo_operculado_75.jpg";
	//ICONMENU: AP5 (AGUAS_PLUVIAIS 5)
	public static final String ICONMNU_AP5_SETA_INDICADOR_DESCE = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_seta_indicador_desce.jpg";
	public static final String ICONMNU_AP5_SETA_INDICADOR_PASSA = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_seta_indicador_passa.jpg";
	public static final String ICONMNU_AP5_SETA_INDICADOR_SOBE = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_seta_indicador_sobe.jpg";
	public static final String ICONMNU_AP5_SETA_SIMPLES_DESCE = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_seta_simples_desce.jpg";
	public static final String ICONMNU_AP5_SETA_SIMPLES_PASSA = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_seta_simples_passa.jpg";
	public static final String ICONMNU_AP5_SETA_SIMPLES_SOBE = "/br/com/tlmv/aicadxmod/res/cad/apluvial/ico_ap_seta_simples_sobe.jpg";
	//ICONMENU: RDP1 (REDES_PUBLICAS_DRENAGEM 1)
	public static final String ICONMNU_RDP1_CSV_IMPORT = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_csv_import.jpg";
	public static final String ICONMNU_RDP1_CSV_EXPORT = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_csv_export.jpg";
	public static final String ICONMNU_RDP1_AREA_CONTRIBUICAO = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_area_contribuicao.jpg";
	public static final String ICONMNU_RDP1_INSERE_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_inserir_caixas_inspecao.jpg";
	public static final String ICONMNU_RDP1_PROPRIEDADE_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_editar_caixas_inspecao.jpg";
	public static final String ICONMNU_RDP1_LIGACAO_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_conectar_caixas_inspecao.jpg";
	public static final String ICONMNU_RDP1_VERIF_LIGACAO_CI_ONOFF = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_verificar_ligacoes_onoff.jpg";
	public static final String ICONMNU_RDP1_DIMENSIONA_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_dimensionar_rede_drenagem.jpg";
	public static final String ICONMNU_RDP1_GERAR_PLANILHA_CALCULO_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_gerar_planilha_calculo.jpg";
	public static final String ICONMNU_RDP1_ALTERAR_PLANILHA_CALCULO_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_alterar_planilha_calculo.jpg";
	public static final String ICONMNU_RDP1_GERAR_PLANTA_PERFIS_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_gerar_plantas_perfis.jpg";
	public static final String ICONMNU_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_gerar_planta_area_contribuicao.jpg";
	public static final String ICONMNU_RDP1_ANOTACAO_INDIVIDUAL_CI = "/br/com/tlmv/aicadxmod/res/cad/drenagem/ico_rpd_inserir_anotacao_ci.jpg";
	//ICONMENU: EL1 (ELETRICA 1)
	public static final String ICONMNU_EL1_INSERE_CAIXA_PASSAGEM_PISO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_pt_caixa_passagem_piso.jpg";
	public static final String ICONMNU_EL1_INSERE_INTERRUPTOR_SIMPLES = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_pt_interruptor_simples.jpg";
	public static final String ICONMNU_EL1_INSERE_PONTO_FORCA_PISO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_pt_ponto_forca_piso.jpg";
	public static final String ICONMNU_EL1_INSERE_PONTO_LUZ = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_pt_ponto_luz.jpg";
	public static final String ICONMNU_EL1_INSERE_QUADRO_DISTRIBUICAO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_pt_quadro_distribuicao.jpg";
	public static final String ICONMNU_EL1_INSERE_TOMADA_BAIXA = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_pt_tomada_baixa.jpg";
	//ICONMENU: EL2 (ELETRICA 2)
	public static final String ICONMNU_EL2_INSERE_ELETRODUTO_TETO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_eletroduto_teto.jpg";
	public static final String ICONMNU_EL2_INSERE_ELETRODUTO_PISO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_eletroduto_piso.jpg";
	public static final String ICONMNU_EL2_INSERE_ELETRODUTO_APARENTE = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_eletroduto_aparente.jpg";	
	public static final String ICONMNU_EL2_TROCA_CIRCUITO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_troca_circuito.jpg";
	public static final String ICONMNU_EL2_TROCA_COMANDO = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_troca_comando.jpg";
	public static final String ICONMNU_EL2_TROCA_ORIGEM = "/br/com/tlmv/aicadxmod/res/cad/eletrica/ico_ele_troca_origem.jpg";
	//MENU: HID1 (HIDRAULICA 1)
	public static final String ICONMNU_HID1_DEFINE_TRECHO = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_define_trecho.jpg";	
	public static final String ICONMNU_HID1_DEFINE_PERDA_EQUIPAMENTO = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_define_perda_equipamento.jpg";
	public static final String ICONMNU_HID1_CALCULA_PERDA_CARGA = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_calcula_perda_carga.jpg";
	//MENU: HID2 (HIDRAULICA 2)	
	public static final String ICONMNU_HID2_COLUNA_CD50 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_cd50.jpg";
	public static final String ICONMNU_HID2_COLUNA_CD60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_cd60.jpg";
	public static final String ICONMNU_HID2_COLUNA_CD75 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_cd75.jpg";
	public static final String ICONMNU_HID2_COLUNA_CD85 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_cd85.jpg";
	public static final String ICONMNU_HID2_COLUNA_CD110 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_cd110.jpg";
	public static final String ICONMNU_HID2_COLUNA_TD50 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_td50.jpg";
	public static final String ICONMNU_HID2_COLUNA_TD60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_td60.jpg";
	public static final String ICONMNU_HID2_COLUNA_TD75 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_td75.jpg";
	public static final String ICONMNU_HID2_COLUNA_TD85 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_td85.jpg";
	public static final String ICONMNU_HID2_COLUNA_TD110 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_td110.jpg";
	public static final String ICONMNU_HID2_COLUNA_TTD50 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_ttd50.jpg";
	public static final String ICONMNU_HID2_COLUNA_TTD60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_ttd60.jpg";
	public static final String ICONMNU_HID2_COLUNA_TTD75 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_ttd75.jpg";
	public static final String ICONMNU_HID2_COLUNA_TTD85 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_ttd85.jpg";
	public static final String ICONMNU_HID2_COLUNA_TTD110 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_coluna_ttd110.jpg";
	//MENU: HID3 (HIDRAULICA 3)	
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_20 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_20.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_25 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_25.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_32 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_32.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_40 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_40.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_50 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_50.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_60.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_75 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_75.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_85 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_85.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_110 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_110.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_22 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_22.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_28 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_28.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_35 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_35.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_42 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_42.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_54 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_54.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_60.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_73 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_73.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_89 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_89.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_114 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_114.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_20 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_pex_20.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_25 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_pex_25.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_32 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_fria_pex_32.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_20 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_pex_20.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_25 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_pex_25.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_32 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_quente_pex_32.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_20 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_20.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_25 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_25.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_32 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_32.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_40 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_40.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_50 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_50.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_60.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_75 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_75.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_85 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_85.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_REUSO_110 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_reuso_110.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_20 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_20.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_25 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_25.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_32 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_32.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_40 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_40.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_50 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_50.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_60 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_60.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_75 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_75.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_85 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_85.jpg";
	public static final String ICONMNU_HID3_TUBULACAO_AGUA_TRATADA_110 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_tubulacao_agua_tratada_110.jpg";
	//MENU: HID4 (HIDRAULICA 4)	
	public static final String ICONMNU_HID4_BUCHA_REDUCAO = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_bucha_reducao.jpg";
	public static final String ICONMNU_HID4_REGISTRO_AGUA_FRIA = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_registro_agua_fria.jpg";
	public static final String ICONMNU_HID4_REGISTRO_AGUA_QUENTE = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_registro_agua_quente.jpg";
	public static final String ICONMNU_HID4_REGISTRO_3_4_30 = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_rg_3_4_30.jpg";
	public static final String ICONMNU_HID4_TORNEIRA_LAVAGEM = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_torneira_lavagem.jpg";
	public static final String ICONMNU_HID4_FINAL_TUBULACAO = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_final_tubulacao.jpg";
	//MENU: HID5 (HIDRAULICA 5)	
	public static final String ICONMNU_HID5_SETA_INDICADOR_DESCE = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_seta_indicador_desce.jpg";
	public static final String ICONMNU_HID5_SETA_INDICADOR_PASSA = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_seta_indicador_passa.jpg";
	public static final String ICONMNU_HID5_SETA_INDICADOR_SOBE = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_seta_indicador_sobe.jpg";
	public static final String ICONMNU_HID5_SETA_SIMPLES_DESCE = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_seta_simples_desce.jpg";
	public static final String ICONMNU_HID5_SETA_SIMPLES_PASSA = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_seta_simples_passa.jpg";
	public static final String ICONMNU_HID5_SETA_SIMPLES_SOBE = "/br/com/tlmv/aicadxmod/res/cad/hidraulica/ico_hid_seta_simples_sobe.jpg";
	//MENU: G1 (GAS 1)
	public static final String ICONMNU_G1_INSERE_TB_GAS_PISO = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_tubulacao_piso.jpg";
	public static final String ICONMNU_G1_INSERE_TB_GAS_TETO = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_tubulacao_teto.jpg";
	public static final String ICONMNU_G1_INSERE_CANALETA_GAS = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_canaleta.jpg";
	public static final String ICONMNU_G1_INSERE_SEPTO_GAS = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_septo_ventilado.jpg";
	public static final String ICONMNU_G1_INSERE_PRUMADA_GAS = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_prumada_gas.jpg";
	public static final String ICONMNU_G1_INSERE_PONTO_GAS = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_ponto_gas.jpg";
	public static final String ICONMNU_G1_INSERE_SETA_SIMPLES_PASSA = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_seta_simples_passa.jpg";
	public static final String ICONMNU_G1_INSERE_SETA_SIMPLES_SOBE = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_seta_simples_sobe.jpg";
	public static final String ICONMNU_G1_INSERE_SETA_SIMPLES_DESCE = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_seta_simples_desce.jpg";
	public static final String ICONMNU_G1_INSERE_INDICADOR_ECONOMIA = "/br/com/tlmv/aicadxmod/res/cad/gas/ico_g_indicador_economia.jpg";
	//ICOMENU: BLOCKS
	public static final String ICOMNU_BLOCKS_INSERTSHAPE = "/br/com/tlmv/aicadxapp/res/cad/ico_block_insert_shape.jpg";
	public static final String ICOMNU_BLOCKS_CREATESHAPE = "/br/com/tlmv/aicadxapp/res/cad/ico_block_create_shape.jpg";
	//ICOMENU: DRAW1
	public static final String ICOMNU_DRAW1_LINE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_line.jpg";
	public static final String ICOMNU_DRAW1_ARC = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_arc_asca.jpg";
	public static final String ICOMNU_DRAW1_CIRCLE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_circle_cr.jpg";
	public static final String ICOMNU_DRAW1_RECTANGLE = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_rectangle.jpg";
	public static final String ICOMNU_DRAW1_POLYGON = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_polygon.jpg";
	public static final String ICOMNU_DRAW1_POINT = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_point.jpg";
	public static final String ICOMNU_DRAW1_TEXT = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_text.jpg";
	public static final String ICOMNU_DRAW1_BOX3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_box3d.jpg";
	public static final String ICOMNU_DRAW1_CILINDER3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_cilinder3d.jpg";
	public static final String ICOMNU_DRAW1_CONE3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_cone3d.jpg";
	public static final String ICOMNU_DRAW1_TRONCOCONE3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_tronco_cone3d.jpg";
	public static final String ICOMNU_DRAW1_TORUS3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_torus3d.jpg";
	public static final String ICOMNU_DRAW1_SPHERE3D = "/br/com/tlmv/aicadxapp/res/cad/ico_draw1_sphere3d.jpg";
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
	public static final String ICOMNU_ZOOM_VIEWTOP = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_viewtop.jpg";
	public static final String ICOMNU_ZOOM_VIEWBOTTOM = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_viewbottom.jpg";
	public static final String ICOMNU_ZOOM_VIEWBACK = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_viewback.jpg";
	public static final String ICOMNU_ZOOM_VIEWFRONT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_viewfront.jpg";
	public static final String ICOMNU_ZOOM_VIEWLEFT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_viewleft.jpg";
	public static final String ICOMNU_ZOOM_VIEWRIGHT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_viewright.jpg";
	//public static final String ICOMNU_ZOOM_MOVEFORWARD = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_moveforward.jpg";
	//public static final String ICOMNU_ZOOM_MOVEBACKWARD = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_movebackward.jpg";
	//public static final String ICOMNU_ZOOM_TURNLEFT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_turnleft.jpg";
	//public static final String ICOMNU_ZOOM_TURNRIGHT = "/br/com/tlmv/aicadxapp/res/cad/ico_zoom_turnright.jpg";
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
	public static final String TOOLTIP_ICOMNU_FILE_DXFIN = "DXF In...";
	public static final String TOOLTIP_ICOMNU_FILE_DXFOUT = "DXF Out...";
	public static final String TOOLTIP_ICOMNU_FILE_INSERTIMAGE = "Insert Image...";
	public static final String TOOLTIP_ICOMNU_FILE_SETUP = "Setup...";
	public static final String TOOLTIP_ICOMNU_FILE_PRINT = "Print...";
	public static final String TOOLTIP_ICOMNU_FILE_STOP = "Stop";
	public static final String TOOLTIP_ICOMNU_FILE_EXIT = "Exit";
	//MENU: ARQ1 (ARQUITETURA 1)
	public static final String TOOLTIP_ICOMNU_ARQ1_PILARRETANGULAR = "Pilar Retangular";
	public static final String TOOLTIP_ICOMNU_ARQ1_PILARCIRCULAR = "Pilar Circular";
	public static final String TOOLTIP_ICOMNU_ARQ1_PISO = "Piso";
	public static final String TOOLTIP_ICOMNU_ARQ1_PAREDE = "Parede";
	public static final String TOOLTIP_ICOMNU_ARQ1_AMBIENTE1 = "Ambiente";
	public static final String TOOLTIP_ICOMNU_ARQ1_AMBIENTE2 = "Ambiente 2-Paredes";
	public static final String TOOLTIP_ICOMNU_ARQ1_AMBIENTE3 = "Ambiente 3-Paredes";
	public static final String TOOLTIP_ICOMNU_ARQ1_MALHA = "Malha";
	public static final String TOOLTIP_ICOMNU_ARQ1_ABERTURA = "Abertura";
	public static final String TOOLTIP_ICOMNU_ARQ1_PORTA = "Porta";
	public static final String TOOLTIP_ICOMNU_ARQ1_PDUPLA = "Porta Dupla";
	public static final String TOOLTIP_ICOMNU_ARQ1_PCORRER = "Porta Correr";
	public static final String TOOLTIP_ICOMNU_ARQ1_JANELA = "Janela";
	public static final String TOOLTIP_ICOMNU_ARQ1_AREA = "Area";
	//MENU: ARQ2 (ARQUITETURA 2)
	public static final String TOOLTIP_ICOMNU_ARQ2_BIDE = "Bide";
	public static final String TOOLTIP_ICOMNU_ARQ2_VASOSANITARIO = "Vaso Sanitario";
	public static final String TOOLTIP_ICOMNU_ARQ2_VASOCAIXAACLOPADA = "Vaso Caixa Aclopada";
	public static final String TOOLTIP_ICOMNU_ARQ2_LAVATORIOGRANDE = "Lavatorio Grande";
	public static final String TOOLTIP_ICOMNU_ARQ2_LAVATORIOPEQUENO = "Lavatorio Pequeno";
	public static final String TOOLTIP_ICOMNU_ARQ2_LAVATORIOBANCA = "Lavatorio para Banca";
	public static final String TOOLTIP_ICOMNU_ARQ2_CHUVEIRO = "Chuveiro";
	public static final String TOOLTIP_ICOMNU_ARQ2_MICTORIO = "Mictorio";
	public static final String TOOLTIP_ICOMNU_ARQ2_PIASIMPLES = "Pia Simples";
	public static final String TOOLTIP_ICOMNU_ARQ2_PIADUPLA = "Pia Dupla";
	public static final String TOOLTIP_ICOMNU_ARQ2_FOGAO4BOCAS = "Fogao 4 Bocas";
	public static final String TOOLTIP_ICOMNU_ARQ2_FOGAO6BOCAS = "Fogao 6 Bocas";
	public static final String TOOLTIP_ICOMNU_ARQ2_GELADEIRA = "Geladeira";
	public static final String TOOLTIP_ICOMNU_ARQ2_LAVADOURAROUPA = "Lavadoura Roupa";
	public static final String TOOLTIP_ICOMNU_ARQ2_TANQUE = "Tanque";
	public static final String TOOLTIP_ICOMNU_ARQ2_AQUECEDOR = "Aquecedor";
	public static final String TOOLTIP_ICOMNU_ARQ2_BOILER = "Boiler";
	public static final String TOOLTIP_ICOMNU_ARQ2_BOMBARECALQUE = "Bomba Recalque";
	public static final String TOOLTIP_ICOMNU_ARQ2_BOMBAAGUASSERVIDAS = "Bomba Aguas Servidas";
	//MENU: ES1 (ESGOTO 1)
	public static final String TOOLTIP_ICOMNU_ES1_INSERE_CI = "Inserir Caixas de Inspecao";
	public static final String TOOLTIP_ICOMNU_ES1_LIGACAO_CI = "Ligacao Caixas de Inspecao";
	public static final String TOOLTIP_ICOMNU_ES1_VERIF_LIGACAO_CI_ONOFF = "Verificar Ligacoes (On/Off)";
	public static final String TOOLTIP_ICOMNU_ES1_DIMENSIONA_CI = "Dimensionar Rede de Esgoto...";
	public static final String TOOLTIP_ICOMNU_ES1_GERAR_PLANILHA_CALCULO_CI = "Gerar Planilha Calculo";
	public static final String TOOLTIP_ICOMNU_ES1_GERAR_PLANTA_PERFIS_CI = "Gerar Plantas de Perfis";
	public static final String TOOLTIP_ICOMNU_ES1_ANOTACAO_INDIVIDUAL_CI = "Inserir Anotacao";
	//MENU: ES2 (ESGOTO 2)
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_CD50 = "Centro-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_CD75 = "Centro-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_CD100 = "Centro-Diametro 100mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_CD150 = "Centro-Diametro 150mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TD50 = "Tangente-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TD75 = "Tangente-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TD100 = "Tangente-Diametro 100mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TD150 = "Tangente-Diametro 150mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TTD50 = "Tangente-Tangente-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TTD75 = "Tangente-Tangente-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TTD100 = "Tangente-Tangente-Diametro 100mm";
	public static final String TOOLTIP_ICOMNU_ES2_COLUNA_TTD150 = "Tangente-Tangente-Diametro 150mm";
	//MENU: ES3 (ESGOTO 3)
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_PRIMARIO_75 = "Primario 75mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_PRIMARIO_100 = "Primario 100mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_PRIMARIO_150 = "Primario 150mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_40 = "Secundario 40mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_50 = "Secundario 50mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_75 = "Secundario 75mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_100 = "Secundario 100mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_150 = "Secundario 150mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_40 = "Secundario de Gordura 40mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_50 = "Secundario de Gordura 50mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_75 = "Secundario de Gordura 75mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_100 = "Secundario de Gordura 100mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_150 = "Secundario de Gordura 150mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_40 = "Secundario Sabao 40mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_50 = "Secundario Sabao 50mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_75 = "Secundario Sabao 75mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_100 = "Secundario Sabao 100mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_150 = "Secundario Sabao 150mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_VENTILACAO_50 = "Ventilacao 50mm";
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_VENTILACAO_75 = "Ventilacao 75mm";	
	public static final String TOOLTIP_ICOMNU_ES3_TUBULACAO_VENTILACAO_100 = "Ventilacao 100mm";
	//MENU: ES4 (ESGOTO 4)
	public static final String TOOLTIP_ICOMNU_ES4_BUJAO40 = "Bujao 40mm";
	public static final String TOOLTIP_ICOMNU_ES4_BUJAO50 = "Bujao 50mm";
	public static final String TOOLTIP_ICOMNU_ES4_BUJAO75 = "Bujao 75mm";
	public static final String TOOLTIP_ICOMNU_ES4_BUJAO100 = "Bujao 100mm";
	public static final String TOOLTIP_ICOMNU_ES4_BUJAO150 = "Bujao 150mm";
	public static final String TOOLTIP_ICOMNU_ES4_CAIXA_INSPECAO = "Caixa de Inspecao";
	public static final String TOOLTIP_ICOMNU_ES4_CAIXA_PASSAGEM_60X60 = "Caixa de Passagem 60x60";
	public static final String TOOLTIP_ICOMNU_ES4_CAIXA_GORDURA_DUPLA = "Caixa de Gordura Dupla";
	public static final String TOOLTIP_ICOMNU_ES4_CAIXA_SINFONADA = "Caixa Sinfornada";
	public static final String TOOLTIP_ICOMNU_ES4_RALO_CONICO = "Ralo Conico";
	public static final String TOOLTIP_ICOMNU_ES4_RALO_HEMISFERICO = "Ralo Hemisferico";
	public static final String TOOLTIP_ICOMNU_ES4_RALO_SINFONADO = "Ralo Sinfonado";
	public static final String TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_40 = "Tubo Operculado 40mm";
	public static final String TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_50 = "Tubo Operculado 50mm";
	public static final String TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_75 = "Tubo Operculado 75mm";
	public static final String TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_100 = "Tubo Operculado 100mm";
	public static final String TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_150 = "Tubo Operculado 150mm";
	public static final String TOOLTIP_ICOMNU_ES4_FINAL_TUBULACAO = "Final de Tubulacao";
	//MENU: ES5 (ESGOTO 5)
	public static final String TOOLTIP_ICOMNU_ES5_SETA_INDICADOR_DESCE = "Coluna que Desce";
	public static final String TOOLTIP_ICOMNU_ES5_SETA_INDICADOR_PASSA = "Coluna que Passa";
	public static final String TOOLTIP_ICOMNU_ES5_SETA_INDICADOR_SOBE = "Coluna que Sobe";
	public static final String TOOLTIP_ICOMNU_ES5_SETA_SIMPLES_DESCE = "Seta que Desce";
	public static final String TOOLTIP_ICOMNU_ES5_SETA_SIMPLES_PASSA = "Seta que Passa";
	public static final String TOOLTIP_ICOMNU_ES5_SETA_SIMPLES_SOBE = "Seta que Sobe";
	//MENU: AP1 (AGUAS_PLUVIAIS 1)
	public static final String TOOLTIP_ICOMNU_AP1_INSERE_CI = "Inserir Caixas de Aguas Pluviais";
	public static final String TOOLTIP_ICOMNU_AP1_LIGACAO_CI = "Ligacao Caixas de Aguas Pluviais";
	public static final String TOOLTIP_ICOMNU_AP1_VERIF_LIGACAO_CI_ONOFF = "Verificar Ligacoes (On/Off)";
	public static final String TOOLTIP_ICOMNU_AP1_DIMENSIONA_CI = "Dimensionar Rede de Aguas Pluviais...";
	public static final String TOOLTIP_ICOMNU_AP1_GERAR_PLANILHA_CALCULO_CI = "Gerar Planilha Calculo";
	public static final String TOOLTIP_ICOMNU_AP1_GERAR_PLANTA_PERFIS_CI = "Gerar Plantas de Perfis";
	public static final String TOOLTIP_ICOMNU_AP1_ANOTACAO_INDIVIDUAL_CI = "Inserir Anotacao";
	//MENU: AP2 (AGUAS_PLUVIAIS 2)
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_CD50 = "Centro-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_CD75 = "Centro-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_CD100 = "Centro-Diametro 100mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_CD150 = "Centro-Diametro 150mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TD50 = "Tangente-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TD75 = "Tangente-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TD100 = "Tangente-Diametro 100mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TD150 = "Tangente-Diametro 150mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TTD50 = "Tangente-Tangente-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TTD75 = "Tangente-Tangente-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TTD100 = "Tangente-Tangente-Diametro 100mm";
	public static final String TOOLTIP_ICOMNU_AP2_COLUNA_TTD150 = "Tangente-Tangente-Diametro 150mm";
	//ICONMENU: AP3 (AGUAS_PLUVIAIS 3)
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50 = "Aguas Pluviais 50mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75 = "Aguas Pluviais 75mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100 = "Aguas Pluviais 100mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200 = "Aguas Pluviais 150mm-200mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300 = "Aguas Pluviais 250mm-300mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50 = "Aguas de Reuso 50mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75 = "Aguas de Reuso 75mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100 = "Aguas de Reuso 100mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200 = "Aguas de Reuso 150mm-200mm";
	public static final String TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300 = "Aguas de Reuso 250mm-300mm";
	//ICONMENU: AP4 (AGUAS_PLUVIAIS 4)
	public static final String TOOLTIP_ICOMNU_AP4_BUJAO40 = "Bujao 40mm";
	public static final String TOOLTIP_ICOMNU_AP4_BUJAO50 = "Bujao 50mm";
	public static final String TOOLTIP_ICOMNU_AP4_BUJAO75 = "Bujao 75mm";
	public static final String TOOLTIP_ICOMNU_AP4_BUJAO100 = "Bujao 100mm";
	public static final String TOOLTIP_ICOMNU_AP4_BUJAO150 = "Bujao 150mm";
	public static final String TOOLTIP_ICOMNU_AP4_CAIXA_INSPECAO = "Caixa de Inspecao";
	public static final String TOOLTIP_ICOMNU_AP4_CAIXA_PASSAGEM_60X60 = "Caixa de Passagem 60x60";
	public static final String TOOLTIP_ICOMNU_AP4_RALO_CONICO = "Ralo Conico";
	public static final String TOOLTIP_ICOMNU_AP4_RALO_HEMISFERICO = "Ralo Hemisferico";
	public static final String TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_40 = "Tubo Operculado 40mm";
	public static final String TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_50 = "Tubo Operculado 50mm";
	public static final String TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_75 = "Tubo Operculado 75mm";
	public static final String TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_100 = "Tubo Operculado 100mm";
	public static final String TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_150 = "Tubo Operculado 150mm";
	public static final String TOOLTIP_ICOMNU_AP4_FINAL_TUBULACAO = "Final de Tubulacao";
	//ICONMENU: AP5 (AGUAS_PLUVIAIS 5)
	public static final String TOOLTIP_ICOMNU_AP5_SETA_INDICADOR_DESCE = "Coluna que Desce";
	public static final String TOOLTIP_ICOMNU_AP5_SETA_INDICADOR_PASSA = "Coluna que Passa";
	public static final String TOOLTIP_ICOMNU_AP5_SETA_INDICADOR_SOBE = "Coluna que Sobe";
	public static final String TOOLTIP_ICOMNU_AP5_SETA_SIMPLES_DESCE = "Seta que Desce";
	public static final String TOOLTIP_ICOMNU_AP5_SETA_SIMPLES_PASSA = "Seta que Passa";
	public static final String TOOLTIP_ICOMNU_AP5_SETA_SIMPLES_SOBE = "Seta que Sobe";
	//MENU: RDP1 (REDES_PUBLICAS_DRENAGEM 1)
	public static final String TOOLTIP_ICOMNU_RDP1_CSV_IMPORT = "Importa Rede Drenagem (CSV)... ";
	public static final String TOOLTIP_ICOMNU_RDP1_CSV_EXPORT = "Exporta Rede Drenagem (CSV)... ";
	public static final String TOOLTIP_ICOMNU_RDP1_AREA_CONTRIBUICAO = "Area Contribuicao";
	public static final String TOOLTIP_ICOMNU_RDP1_INSERE_CI = "Inserir Poco de Visita";
	public static final String TOOLTIP_ICOMNU_RDP1_PROPRIEDADE_CI = "Propriedade Poco de Visita";
	public static final String TOOLTIP_ICOMNU_RDP1_LIGACAO_CI = "Ligacao Poco de Visita";
	public static final String TOOLTIP_ICOMNU_RDP1_VERIF_LIGACAO_CI_ONOFF = "Verificar Ligacoes (On/Off)";
	public static final String TOOLTIP_ICOMNU_RDP1_DIMENSIONA_CI = "Dimensionar Rede de Drenagem...";
	public static final String TOOLTIP_ICOMNU_RDP1_GERAR_PLANILHA_CALCULO_CI = "Gerar Planilha Calculo...";
	public static final String TOOLTIP_ICOMNU_RDP1_ALTERAR_PLANILHA_CALCULO_CI = "Alterar Planilha Calculo...";	
	public static final String TOOLTIP_ICOMNU_RDP1_GERAR_PLANTA_PERFIS_CI = "Gerar Plantas de Perfis";
	public static final String TOOLTIP_ICOMNU_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO = "Gerar Plantas Area Contribuicao";
	public static final String TOOLTIP_ICOMNU_RDP1_ANOTACAO_INDIVIDUAL_CI = "Inserir Anotacao";
	//TOOLTIP_ICOMENU: EL1 (ELETRICA 1)
	public static final String TOOLTIP_ICOMNU_EL1_INSERE_CAIXA_PASSAGEM_PISO = "Caixa Passagem Piso";
	public static final String TOOLTIP_ICOMNU_EL1_INSERE_INTERRUPTOR_SIMPLES = "Interruptor Simples";
	public static final String TOOLTIP_ICOMNU_EL1_INSERE_PONTO_FORCA_PISO = "Ponto Forca Piso";
	public static final String TOOLTIP_ICOMNU_EL1_INSERE_PONTO_LUZ = "Ponto Luz";
	public static final String TOOLTIP_ICOMNU_EL1_INSERE_QUADRO_DISTRIBUICAO = "Quadro Distribuicao";
	public static final String TOOLTIP_ICOMNU_EL1_INSERE_TOMADA_BAIXA = "Tomada Baixa";
	//TOOLTIP_ICOMENU: EL2 (ELETRICA 2)
	public static final String TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_TETO = "Eletroduto Teto";
	public static final String TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_PISO = "Eletroduto Piso";
	public static final String TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_APARENTE = "Eletroduto Aparente";	
	public static final String TOOLTIP_ICOMNU_EL2_TROCA_CIRCUITO = "Troca Circuito";
	public static final String TOOLTIP_ICOMNU_EL2_TROCA_COMANDO = "Troca Comando";
	public static final String TOOLTIP_ICOMNU_EL2_TROCA_ORIGEM = "Troca Origem";
	//MENU: HID1 (HIDRAULICA 1)
	public static final String TOOLTIP_ICOMNU_HID1_DEFINE_TRECHO = "Define Trecho...";	
	public static final String TOOLTIP_ICOMNU_HID1_DEFINE_PERDA_EQUIPAMENTO = "Perda de Carga no Equipamento...";
	public static final String TOOLTIP_ICOMNU_HID1_CALCULA_PERDA_CARGA = "Calculo da Perda de Carga...";
	//MENU: HID2 (HIDRAULICA 2)	
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_CD50 = "Centro-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_CD60 = "Centro-Diametro 60mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_CD75 = "Centro-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_CD85 = "Centro-Diametro 85mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_CD110 = "Centro-Diametro 110mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TD50 = "Tangente-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TD60 = "Tangente-Diametro 60mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TD75 = "Tangente-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TD85 = "Tangente-Diametro 85mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TD110 = "Tangente-Diametro 110mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TTD50 = "Tangente-Tangente-Diametro 50mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TTD60 = "Tangente-Tangente-Diametro 60mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TTD75 = "Tangente-Tangente-Diametro 75mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TTD85 = "Tangente-Tangente-Diametro 85mm";
	public static final String TOOLTIP_ICOMNU_HID2_COLUNA_TTD110 = "Tangente-Tangente-Diametro 110mm";
	//MENU: HID3 (HIDRAULICA 3)	
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_20 = "Tubulacao Agua Fria 20mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_25 = "Tubulacao Agua Fria 25mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_32 = "Tubulacao Agua Fria 32mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_40 = "Tubulacao Agua Fria 40mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_50 = "Tubulacao Agua Fria 50mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_60 = "Tubulacao Agua Fria 60mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_75 = "Tubulacao Agua Fria 75mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_85 = "Tubulacao Agua Fria 85mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_110 = "Tubulacao Agua Fria 110mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_22 = "Tubulacao Agua Quente 22mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_28 = "Tubulacao Agua Quente 28mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_35 = "Tubulacao Agua Quente 35mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_42 = "Tubulacao Agua Quente 42mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_54 = "Tubulacao Agua Quente 54mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_60 = "Tubulacao Agua Quente 60mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_73 = "Tubulacao Agua Quente 73mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_89 = "Tubulacao Agua Quente 89mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_114 = "Tubulacao Agua Quente 114mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_20 = "Tubulacao Agua Fria - PEX 20mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_25 = "Tubulacao Agua Fria - PEX 25mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_32 = "Tubulacao Agua Fria - PEX 32mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_20 = "Tubulacao Agua Quente - PEX 20mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_25 = "Tubulacao Agua Quente - PEX 25mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_32 = "Tubulacao Agua Quente - PEX 32mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_20 = "Tubulacao Agua Reuso 20mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_25 = "Tubulacao Agua Reuso 25mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_32 = "Tubulacao Agua Reuso 32mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_40 = "Tubulacao Agua Reuso 40mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_50 = "Tubulacao Agua Reuso 50mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_60 = "Tubulacao Agua Reuso 60mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_75 = "Tubulacao Agua Reuso 75mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_85 = "Tubulacao Agua Reuso 85mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_110 = "Tubulacao Agua Reuso 110mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_20 = "Tubulacao Agua Tratada 20mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_25 = "Tubulacao Agua Tratada 25mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_32 = "Tubulacao Agua Tratada 32mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_40 = "Tubulacao Agua Tratada 40mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_50 = "Tubulacao Agua Tratada 50mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_60 = "Tubulacao Agua Tratada 60mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_75 = "Tubulacao Agua Tratada 75mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_85 = "Tubulacao Agua Tratada 85mm";
	public static final String TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_110 = "Tubulacao Agua Tratada 110mm";
	//MENU: HID4 (HIDRAULICA 4)	
	public static final String TOOLTIP_ICOMNU_HID4_BUCHA_REDUCAO = "Bucha Reducao";
	public static final String TOOLTIP_ICOMNU_HID4_REGISTRO_AGUA_FRIA = "Registro Agua Fria";
	public static final String TOOLTIP_ICOMNU_HID4_REGISTRO_AGUA_QUENTE = "Registro Agua Quente";
	public static final String TOOLTIP_ICOMNU_HID4_REGISTRO_3_4_30 = "Registro 3/4 pol (h=30cm)";
	public static final String TOOLTIP_ICOMNU_HID4_TORNEIRA_LAVAGEM = "Torneira_Lavagem";
	public static final String TOOLTIP_ICOMNU_HID4_FINAL_TUBULACAO = "Final Tubulacao";
	//MENU: HID5 (HIDRAULICA 5)	
	public static final String TOOLTIP_ICOMNU_HID5_SETA_INDICADOR_DESCE = "Indicador Desce";
	public static final String TOOLTIP_ICOMNU_HID5_SETA_INDICADOR_PASSA = "Indicador Passa";
	public static final String TOOLTIP_ICOMNU_HID5_SETA_INDICADOR_SOBE = "Indicador Sobe";
	public static final String TOOLTIP_ICOMNU_HID5_SETA_SIMPLES_DESCE = "Seta Desce";
	public static final String TOOLTIP_ICOMNU_HID5_SETA_SIMPLES_PASSA = "Seta Passa";
	public static final String TOOLTIP_ICOMNU_HID5_SETA_SIMPLES_SOBE = "Seta Sobe";
	//TOOLTIP_ICOMENU: G1 (GAS 1)
	public static final String TOOLTIP_ICOMNU_G1_INSERE_TB_GAS_PISO = "Tubulacao Piso";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_TB_GAS_TETO = "Tubulacao Teto";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_CANALETA_GAS = "Insere Canaleta";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_SEPTO_GAS = "Insere Septo Ventilado";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_PRUMADA_GAS = "Insere Prumadas";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_PONTO_GAS = "Ponto Gas";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_SETA_SIMPLES_PASSA = "Seta Passa";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_SETA_SIMPLES_SOBE = "Seta Sobe";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_SETA_SIMPLES_DESCE = "Seta Desce";
	public static final String TOOLTIP_ICOMNU_G1_INSERE_INDICADOR_ECONOMIA = "Indicador Economia";
	//MENU: H1 (HIDRAULICA 1)
	public static final String TOOLTIP_ICOMNU_H1_INSERE_TB_HID_PISO = "Tubulacao Piso";
	public static final String TOOLTIP_ICOMNU_H1_INSERE_TB_HID_TETO = "Tubulacao Teto";
	public static final String TOOLTIP_ICOMNU_H1_INSERE_COLUNA_HID = "Registro Agua Fria";
	public static final String TOOLTIP_ICOMNU_H1_INSERE_INDICADOR_HID = "Registro Agua Fria";
	public static final String TOOLTIP_ICOMNU_H1_INSERE_PRUMADA_HID = "Insere Prumadas";
	//MENU: H2 (HIDRAULICA 2)
	public static final String TOOLTIP_ICOMNU_H2_INSERE_REGISTRO_AGUAFRIA = "Registro Agua Fria";
	public static final String TOOLTIP_ICOMNU_H2_INSERE_REGISTRO_AGUAQUENTE = "Registro Agua Quente";
	public static final String TOOLTIP_ICOMNU_H2_INSERE_TORNEIRA_LAVAGEM = "Torneira Lavagem";
	public static final String TOOLTIP_ICOMNU_H2_INSERE_MANGUEIRA_INCENDIO = "Mangueira Incendio";
	public static final String TOOLTIP_ICOMNU_H2_INSERE_BUCHA_REDUCAO = "Bucha Reducao";
	//TOOLTIP_ICOMENU: BLOCKS
	public static final String TOOLTIP_ICOMNU_BLOCKS_INSERTSHAPE = "Insert Shape";
	public static final String TOOLTIP_ICOMNU_BLOCKS_CREATESHAPE = "Create Shape";
	//TOOLTIP_ICOMENU: DRAW1
	public static final String TOOLTIP_ICOMNU_DRAW1_LINE = "Line";
	public static final String TOOLTIP_ICOMNU_DRAW1_ARC = "Arc";
	public static final String TOOLTIP_ICOMNU_DRAW1_CIRCLE = "Circle";
	public static final String TOOLTIP_ICOMNU_DRAW1_RECTANGLE = "Rectangle";
	public static final String TOOLTIP_ICOMNU_DRAW1_POLYGON = "Polygon";
	public static final String TOOLTIP_ICOMNU_DRAW1_POINT = "Point";
	public static final String TOOLTIP_ICOMNU_DRAW1_TEXT = "Text";
	public static final String TOOLTIP_ICOMNU_DRAW1_BOX3D = "Box 3D";
	public static final String TOOLTIP_ICOMNU_DRAW1_CILINDER3D = "Cilinder 3D";
	public static final String TOOLTIP_ICOMNU_DRAW1_CONE3D = "Cone 3D";
	public static final String TOOLTIP_ICOMNU_DRAW1_TRONCOCONE3D = "Tronco Cone 3D";
	public static final String TOOLTIP_ICOMNU_DRAW1_TORUS3D = "Torus 3D";
	public static final String TOOLTIP_ICOMNU_DRAW1_SPHERE3D = "Sphere 3D";
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
	public static final String TOOLTIP_ICOMNU_ZOOM_VIEWTOP = "View Top";
	public static final String TOOLTIP_ICOMNU_ZOOM_VIEWBOTTOM = "View Bottom";
	public static final String TOOLTIP_ICOMNU_ZOOM_VIEWBACK = "View Back";
	public static final String TOOLTIP_ICOMNU_ZOOM_VIEWFRONT = "View Front";
	public static final String TOOLTIP_ICOMNU_ZOOM_VIEWLEFT = "View Left";
	public static final String TOOLTIP_ICOMNU_ZOOM_VIEWRIGHT = "View Right";
	//public static final String TOOLTIP_ICOMNU_ZOOM_MOVEFORWARD = "Move Forward";
	//public static final String TOOLTIP_ICOMNU_ZOOM_MOVEBACKWARD = "Move Backward";
	//public static final String TOOLTIP_ICOMNU_ZOOM_TURNLEFT = "Turn Left";
	//public static final String TOOLTIP_ICOMNU_ZOOM_TURNRIGHT = "Turn Right";
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
	public static final String ACTION_FILE_DXFIN = "_DXFIN_";
	public static final String ACTION_FILE_DXFOUT = "_DXFOUT_";
	public static final String ACTION_FILE_INSERTIMAGE = "_INSERT_IMAGE_";
	public static final String ACTION_FILE_SETUP = "_SETUP_";
	public static final String ACTION_FILE_PRINT = "_PRINT_";
	public static final String ACTION_FILE_STOP = "_STOP_";
	public static final String ACTION_FILE_EXIT = "_EXIT_";
	//MENU: ARQ1 (ARQUITETURA 1)
	public static final String ACTION_ARQ1_PILARRETANGULAR = "_ARQ1_PILAR_RETANGULAR_";
	public static final String ACTION_ARQ1_PILARCIRCULAR = "_ARQ1_PILAR_CIRCULAR_";
	public static final String ACTION_ARQ1_PISO = "_ARQ1_PISO_";
	public static final String ACTION_ARQ1_PAREDE = "_ARQ1_PAREDE_";
	public static final String ACTION_ARQ1_AMBIENTE1 = "_ARQ1_AMBIENTE1_";
	public static final String ACTION_ARQ1_AMBIENTE2 = "_ARQ1_AMBIENTE2_";
	public static final String ACTION_ARQ1_AMBIENTE3 = "ARQ1_AMBIENTE3_";
	public static final String ACTION_ARQ1_MALHA = "_ARQ1_MALHA_";
	public static final String ACTION_ARQ1_ABERTURA = "_ARQ1_ABERTURA_";
	public static final String ACTION_ARQ1_PORTA = "_ARQ1_PORTA_";
	public static final String ACTION_ARQ1_PDUPLA = "_ARQ1_PORTADUPLA_";
	public static final String ACTION_ARQ1_PCORRER = "_ARQ1_PCORRER_";
	public static final String ACTION_ARQ1_JANELA = "_ARQ1_JANELA_";
	public static final String ACTION_ARQ1_AREA = "_ARQ1_AREA_";
	//MENU: ARQ2 (ARQUITETURA 2)
	public static final String ACTION_ARQ2_BIDE = "_ARQ2_BIDE_";
	public static final String ACTION_ARQ2_VASOSANITARIO = "_ARQ2_VASOSANITARIO_";
	public static final String ACTION_ARQ2_VASOCAIXAACLOPADA = "_ARQ2_VASOCAIXAACLOPADA_";
	public static final String ACTION_ARQ2_LAVATORIOGRANDE = "_ARQ2_LAVATORIOGRANDE_";
	public static final String ACTION_ARQ2_LAVATORIOPEQUENO = "_ARQ2_LAVATORIOPEQUENO_";
	public static final String ACTION_ARQ2_LAVATORIOBANCA = "_ARQ2_LAVATORIOBANCA_";
	public static final String ACTION_ARQ2_CHUVEIRO = "_ARQ2_CHUVEIRO_";
	public static final String ACTION_ARQ2_MICTORIO = "_ARQ2_MICTORIO_";
	public static final String ACTION_ARQ2_PIASIMPLES = "_ARQ2_PIASIMPLES_";
	public static final String ACTION_ARQ2_PIADUPLA = "_ARQ2_PIADUPLA_";
	public static final String ACTION_ARQ2_FOGAO4BOCAS = "_ARQ2_FOGAO4BOCAS_";
	public static final String ACTION_ARQ2_FOGAO6BOCAS = "_ARQ2_FOGAO6BOCAS_";
	public static final String ACTION_ARQ2_GELADEIRA = "_ARQ2_GELADEIRA_";
	public static final String ACTION_ARQ2_LAVADOURAROUPA = "_ARQ2_LAVADOURAROUPA_";
	public static final String ACTION_ARQ2_TANQUE = "_ARQ2_TANQUE_";
	public static final String ACTION_ARQ2_AQUECEDOR = "_ARQ2_AQUECEDOR_";
	public static final String ACTION_ARQ2_BOILER = "_ARQ2_BOILER_";
	public static final String ACTION_ARQ2_BOMBARECALQUE = "_ARQ2_BOMBARECALQUE_";
	public static final String ACTION_ARQ2_BOMBAAGUASSERVIDAS = "_ARQ2_BOMBAAGUASSERVIDAS_";
	//MENU: ES1 (ESGOTO 1)
	public static final String ACTION_ES1_INSERE_CI = "_ES_INSERIR_CI_";
	public static final String ACTION_ES1_LIGACAO_CI = "_ES_LIGACAO_CI_";
	public static final String ACTION_ES1_VERIF_LIGACAO_CI_ONOFF = "_ES_VERIF_LIGACAO_CI_ONOFF_";
	public static final String ACTION_ES1_DIMENSIONA_CI = "_ES_DIMENSIONA_CI_";
	public static final String ACTION_ES1_GERAR_PLANILHA_CALCULO_CI = "_ES_GERAR_PLANILHA_CALCULO_CI_";
	public static final String ACTION_ES1_GERAR_PLANTA_PERFIS_CI = "_ES_GERAR_PLANTA_PERFIS_CI_";
	public static final String ACTION_ES1_ANOTACAO_INDIVIDUAL_CI = "_ES_ANOTACAO_INDIVIDUAL_CI_";
	//MENU: ES2 (ESGOTO 2)
	public static final String ACTION_ES2_COLUNA_CD50 = "_ES_COLUNA_CD50_";
	public static final String ACTION_ES2_COLUNA_CD75 = "_ES_COLUNA_CD75_";
	public static final String ACTION_ES2_COLUNA_CD100 = "_ES_COLUNA_CD100_";
	public static final String ACTION_ES2_COLUNA_CD150 = "_ES_COLUNA_CD150_";
	public static final String ACTION_ES2_COLUNA_TD50 = "_ES_COLUNA_TD50_";
	public static final String ACTION_ES2_COLUNA_TD75 = "_ES_COLUNA_TD75_";
	public static final String ACTION_ES2_COLUNA_TD100 = "_ES_COLUNA_TD100_";
	public static final String ACTION_ES2_COLUNA_TD150 = "_ES_COLUNA_TD150_";
	public static final String ACTION_ES2_COLUNA_TTD50 = "_ES_COLUNA_TTD50_";
	public static final String ACTION_ES2_COLUNA_TTD75 = "_ES_COLUNA_TTD75_";
	public static final String ACTION_ES2_COLUNA_TTD100 = "_ES_COLUNA_TTD100_";
	public static final String ACTION_ES2_COLUNA_TTD150 = "_ES_COLUNA_TTD150_";
	//MENU: ES3 (ESGOTO 3)
	public static final String ACTION_ES3_TUBULACAO_PRIMARIO_75 = "_ES_PRIMARIO_75_";
	public static final String ACTION_ES3_TUBULACAO_PRIMARIO_100 = "_ES_PRIMARIO_100_";
	public static final String ACTION_ES3_TUBULACAO_PRIMARIO_150 = "_ES_PRIMARIO_150_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_40 = "_ES_SECUNDARIO_40_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_50 = "_ES_SECUNDARIO_50_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_75 = "_ES_SECUNDARIO_75_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_100 = "_ES_SECUNDARIO_100_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_150 = "_ES_SECUNDARIO_150_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_40 = "_ES_SECUNDARIO_GORDURA_40_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_50 = "_ES_SECUNDARIO_GORDURA_50_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_75 = "_ES_SECUNDARIO_GORDURA_75_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_100 = "_ES_SECUNDARIO_GORDURA_100_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_DE_GORDURA_150 = "_ES_SECUNDARIO_GORDURA_150_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_40 = "_ES_SECUNDARIO_SABAO_40_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_50 = "_ES_SECUNDARIO_SABAO_50_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_75 = "_ES_SECUNDARIO_SABAO_75_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_100 = "_ES_SECUNDARIO_SABAO_100_";
	public static final String ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_150 = "_ES_SECUNDARIO_SABAO_150_";
	public static final String ACTION_ES3_TUBULACAO_VENTILACAO_50 = "_ES_VENTILACAO_50_";
	public static final String ACTION_ES3_TUBULACAO_VENTILACAO_75 = "_ES_VENTILACAO_75_";	
	public static final String ACTION_ES3_TUBULACAO_VENTILACAO_100 = "_ES_VENTILACAO_100_";
	//MENU: ES4 (ESGOTO 4)
	public static final String ACTION_ES4_BUJAO40 = "_ES_BUJAO_40_";
	public static final String ACTION_ES4_BUJAO50 = "_ES_BUJAO_50_";
	public static final String ACTION_ES4_BUJAO75 = "_ES_BUJAO_75_";
	public static final String ACTION_ES4_BUJAO100 = "_ES_BUJAO_100_";
	public static final String ACTION_ES4_BUJAO150 = "_ES_BUJAO_150_";
	public static final String ACTION_ES4_CAIXA_INSPECAO = "_ES_CAIXA_INSPECAO_";
	public static final String ACTION_ES4_CAIXA_PASSAGEM_60X60 = "_ES_CAIXA_PASSAGEM_60X60_";
	public static final String ACTION_ES4_CAIXA_GORDURA_DUPLA = "_ES_CAIXA_GORDURA_DUPLA_";
	public static final String ACTION_ES4_CAIXA_SINFONADA = "_ES_CAIXA_SINFONADA_";
	public static final String ACTION_ES4_RALO_CONICO = "_ES_RALO_CONICO_";
	public static final String ACTION_ES4_RALO_HEMISFERICO = "_ES_RALO_HEMISFERICO_";
	public static final String ACTION_ES4_RALO_SINFONADO = "_ES_RALO_SINFONADO_";
	public static final String ACTION_ES4_TUBO_OPERCULADO_40 = "_ES_TUBO_OPERCULADO_40_";
	public static final String ACTION_ES4_TUBO_OPERCULADO_50 = "_ES_TUBO_OPERCULADO_50_";
	public static final String ACTION_ES4_TUBO_OPERCULADO_75 = "_ES_TUBO_OPERCULADO_75_";
	public static final String ACTION_ES4_TUBO_OPERCULADO_100 = "_ES_TUBO_OPERCULADO_100_";
	public static final String ACTION_ES4_TUBO_OPERCULADO_150 = "_ES_TUBO_OPERCULADO_150_";
	public static final String ACTION_ES4_FINAL_TUBULACAO = "_ES_FINAL_TUBULACAO_";
	//MENU: ES5 (ESGOTO 5)
	public static final String ACTION_ES5_SETA_INDICADOR_DESCE = "_ES_SETA_INDICADOR_DESCE_";
	public static final String ACTION_ES5_SETA_INDICADOR_PASSA = "_ES_SETA_INDICADOR_PASSA_";
	public static final String ACTION_ES5_SETA_INDICADOR_SOBE = "_ES_SETA_INDICADOR_SOBE_";
	public static final String ACTION_ES5_SETA_SIMPLES_DESCE = "_ES_SETA_SIMPLES_DESCE_";
	public static final String ACTION_ES5_SETA_SIMPLES_PASSA = "_ES_SETA_SIMPLES_PASSA_";
	public static final String ACTION_ES5_SETA_SIMPLES_SOBE = "_ES_SETA_SIMPLES_SOBE_";
	//MENU: AP1 (AGUAS_PLUVIAIS 1)
	public static final String ACTION_AP1_INSERE_CI = "_AP_INSERE_CI_";
	public static final String ACTION_AP1_LIGACAO_CI = "_AP_LIGACAO_CI_";
	public static final String ACTION_AP1_VERIF_LIGACAO_CI_ONOFF = "_AP_VERIF_LIGACAO_CI_ONOFF_";
	public static final String ACTION_AP1_DIMENSIONA_CI = "_AP_DIMENSIONA_CI_";
	public static final String ACTION_AP1_GERAR_PLANILHA_CALCULO_CI = "_AP_GERAR_PLANILHA_CALCULO_CI_";
	public static final String ACTION_AP1_GERAR_PLANTA_PERFIS_CI = "_AP_GERAR_PLANTA_PERFIS_CI_";
	public static final String ACTION_AP1_ANOTACAO_INDIVIDUAL_CI = "_AP_ANOTACAO_INDIVIDUAL_CI_";
	//MENU: AP2 (AGUAS_PLUVIAIS 2)
	public static final String ACTION_AP2_COLUNA_CD50 = "_AP_COLUNA_CD50_";
	public static final String ACTION_AP2_COLUNA_CD75 = "_AP_COLUNA_CD75_";
	public static final String ACTION_AP2_COLUNA_CD100 = "_AP_COLUNA_CD100_";
	public static final String ACTION_AP2_COLUNA_CD150 = "_AP_COLUNA_CD150_";
	public static final String ACTION_AP2_COLUNA_TD50 = "_AP_COLUNA_TD50_";
	public static final String ACTION_AP2_COLUNA_TD75 = "_AP_COLUNA_TD75_";
	public static final String ACTION_AP2_COLUNA_TD100 = "_AP_COLUNA_TD100_";
	public static final String ACTION_AP2_COLUNA_TD150 = "_AP_COLUNA_TD150_";
	public static final String ACTION_AP2_COLUNA_TTD50 = "_AP_COLUNA_TTD50_";
	public static final String ACTION_AP2_COLUNA_TTD75 = "_AP_COLUNA_TTD75_";
	public static final String ACTION_AP2_COLUNA_TTD100 = "_AP_COLUNA_TTD100_";
	public static final String ACTION_AP2_COLUNA_TTD150 = "_AP_COLUNA_TTD150_";
	//ICONMENU: AP3 (AGUAS_PLUVIAIS 3)
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_50 = "_AP_TB_AGUAS_PLUVIAIS_50_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_75 = "_AP_TB_AGUAS_PLUVIAIS_75_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_100 = "_AP_TB_AGUAS_PLUVIAIS_100_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200 = "_AP_TB_AGUAS_PLUVIAIS_150_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300 = "_AP_TB_AGUAS_PLUVIAIS_250_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50 = "_AP_TB_REUSO_50_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75 = "_AP_TB_REUSO_75_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100 = "_AP_TB_REUSO_100_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200 = "_AP_TB_REUSO_150_";
	public static final String ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300 = "_AP_TB_REUSO_250_";
	//ICONMENU: AP4 (AGUAS_PLUVIAIS 4)
	public static final String ACTION_AP4_BUJAO40 = "_AP_BUJAO_40_";
	public static final String ACTION_AP4_BUJAO50 = "_AP_BUJAO_50_";
	public static final String ACTION_AP4_BUJAO75 = "_AP_BUJAO_75_";
	public static final String ACTION_AP4_BUJAO100 = "_AP_BUJAO_100_";
	public static final String ACTION_AP4_BUJAO150 = "_AP_BUJAO_150_";
	public static final String ACTION_AP4_CAIXA_INSPECAO = "_AP_CAIXA_INSPECAO_";
	public static final String ACTION_AP4_CAIXA_PASSAGEM_60X60 = "_AP_CAIXA_PASSAGEM_60X60_";
	public static final String ACTION_AP4_RALO_CONICO = "_AP_RALO_CONICO_";
	public static final String ACTION_AP4_RALO_HEMISFERICO = "_AP_RALO_HEMISFERICO_";
	public static final String ACTION_AP4_TUBO_OPERCULADO_40 = "_AP_TUBO_OPERCULADO_40_";
	public static final String ACTION_AP4_TUBO_OPERCULADO_50 = "_AP_TUBO_OPERCULADO_50_";
	public static final String ACTION_AP4_TUBO_OPERCULADO_75 = "_AP_TUBO_OPERCULADO_75_";
	public static final String ACTION_AP4_TUBO_OPERCULADO_100 = "_AP_TUBO_OPERCULADO_100_";
	public static final String ACTION_AP4_TUBO_OPERCULADO_150 = "_AP_TUBO_OPERCULADO_150_";
	public static final String ACTION_AP4_FINAL_TUBULACAO = "_AP_FINAL_TUBULACAO_";
	//ICONMENU: AP5 (AGUAS_PLUVIAIS 5)
	public static final String ACTION_AP5_SETA_INDICADOR_DESCE = "_AP_SETA_INDICADOR_DESCE_";
	public static final String ACTION_AP5_SETA_INDICADOR_PASSA = "_AP_SETA_INDICADOR_PASSA_";
	public static final String ACTION_AP5_SETA_INDICADOR_SOBE = "_AP_SETA_INDICADOR_SOBE_";
	public static final String ACTION_AP5_SETA_SIMPLES_DESCE = "_AP_SETA_SIMPLES_DESCE_";
	public static final String ACTION_AP5_SETA_SIMPLES_PASSA = "_AP_SETA_SIMPLES_PASSA_";
	public static final String ACTION_AP5_SETA_SIMPLES_SOBE = "_AP_SETA_SIMPLES_SOBE_";
	//MENU: RDP1 (REDES_PUBLICAS_DRENAGEM 1)
	public static final String ACTION_RDP1_CSV_IMPORT = "_IMPORTA_REDE_CSV_";
	public static final String ACTION_RDP1_CSV_EXPORT = "_EXPORTA_REDE_CSV_";
	public static final String ACTION_RDP1_AREA_CONTRIBUICAO = "_RDP_AREA_CONTRIBUICAO_";
	public static final String ACTION_RDP1_INSERE_CI = "_RDP_INSERE_CI_";
	public static final String ACTION_RDP1_PROPRIEDADE_CI = "_RDP_PROPRIEDADE_CI_";
	public static final String ACTION_RDP1_LIGACAO_CI = "_RDP_LIGACAO_CI_";
	public static final String ACTION_RDP1_VERIF_LIGACAO_CI_ONOFF = "_RDP_VERIF_LIGACAO_CI_ONOFF_";
	public static final String ACTION_RDP1_DIMENSIONA_CI = "_RDP_DIMENSIONA_CI_";
	public static final String ACTION_RDP1_GERAR_PLANILHA_CALCULO_CI = "_RDP_GERAR_PLANILHA_CALCULO_CI_";
	public static final String ACTION_RDP1_ALTERAR_PLANILHA_CALCULO_CI = "_RDP_ALTERAR_PLANILHA_CALCULO_CI_";		
	public static final String ACTION_RDP1_GERAR_PLANTA_PERFIS_CI = "_RDP_GERAR_PLANTA_PERFIS_CI_";
	public static final String ACTION_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO = "_RDP_GERAR_PLANTA_AREA_CONTRIBUICAO_";
	public static final String ACTION_RDP1_ANOTACAO_INDIVIDUAL_CI = "_RDP_ANOTACAO_INDIVIDUAL_CI_";
	//ACTIONS: EL1 (ELETRICA 1)
	public static final String ACTION_EL1_INSERE_CAIXA_PASSAGEM_PISO = "_CAIXA_PASSAGEM_PISO_";
	public static final String ACTION_EL1_INSERE_INTERRUPTOR_SIMPLES = "_INTERRUPTOR_SIMPLES_";
	public static final String ACTION_EL1_INSERE_PONTO_FORCA_PISO = "_PONTO_FORCA_PISO_";
	public static final String ACTION_EL1_INSERE_PONTO_LUZ = "_PONTO_LUZ_";
	public static final String ACTION_EL1_INSERE_QUADRO_DISTRIBUICAO = "_QUADRO_DISTRIBUICAO_";
	public static final String ACTION_EL1_INSERE_TOMADA_BAIXA = "_TOMADA_BAIXA_";
	//ACTIONS: EL2 (ELETRICA 2)
	public static final String ACTION_EL2_INSERE_ELETRODUTO_TETO = "_INSERE_ELETRODUTO_TETO_";
	public static final String ACTION_EL2_INSERE_ELETRODUTO_PISO = "_INSERE_ELETRODUTO_PISO_";
	public static final String ACTION_EL2_INSERE_ELETRODUTO_APARENTE = "_INSERE_ELETRODUTO_APARENTE_";
	public static final String ACTION_EL2_TROCA_CIRCUITO = "_TROCA_CIRCUITO_";
	public static final String ACTION_EL2_TROCA_COMANDO = "_TROCA_COMANDO_";
	public static final String ACTION_EL2_TROCA_ORIGEM = "_TROCA_ORIGEM_";
	//MENU: HID1 (HIDRAULICA 1)
	public static final String ACTION_HID1_DEFINE_TRECHO = "_HID1_DEFINE_TRECHO_";	
	public static final String ACTION_HID1_DEFINE_PERDA_EQUIPAMENTO = "_HID1_DEFINE_PERDA_EQUIPAMENTO_";
	public static final String ACTION_HID1_CALCULA_PERDA_CARGA = "_HID1_CALCULA_PERDA_CARGA_";
	//MENU: HID2 (HIDRAULICA 2)	
	public static final String ACTION_HID2_COLUNA_CD50 = "_HID2_COLUNA_CD50_";
	public static final String ACTION_HID2_COLUNA_CD60 = "_HID2_COLUNA_CD60_";
	public static final String ACTION_HID2_COLUNA_CD75 = "_HID2_COLUNA_CD75_";
	public static final String ACTION_HID2_COLUNA_CD85 = "_HID2_COLUNA_CD85_";
	public static final String ACTION_HID2_COLUNA_CD110 = "_HID2_COLUNA_CD110_";
	public static final String ACTION_HID2_COLUNA_TD50 = "_HID2_COLUNA_TD50_";
	public static final String ACTION_HID2_COLUNA_TD60 = "_HID2_COLUNA_TD60_";
	public static final String ACTION_HID2_COLUNA_TD75 = "_HID2_COLUNA_TD75_";
	public static final String ACTION_HID2_COLUNA_TD85 = "_HID2_COLUNA_TD85_";
	public static final String ACTION_HID2_COLUNA_TD110 = "_HID2_COLUNA_TD110_";
	public static final String ACTION_HID2_COLUNA_TTD50 = "_HID2_COLUNA_TTD50_";
	public static final String ACTION_HID2_COLUNA_TTD60 = "_HID2_COLUNA_TTD60_";
	public static final String ACTION_HID2_COLUNA_TTD75 = "_HID2_COLUNA_TTD75_";
	public static final String ACTION_HID2_COLUNA_TTD85 = "_HID2_COLUNA_TTD85_";
	public static final String ACTION_HID2_COLUNA_TTD110 = "_HID2_COLUNA_TTD110_";
	//MENU: HID3 (HIDRAULICA 3)	
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_20 = "_HID3_TUBULACAO_AGUA_FRIA_20_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_25 = "_HID3_TUBULACAO_AGUA_FRIA_25_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_32 = "_HID3_TUBULACAO_AGUA_FRIA_32_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_40 = "_HID3_TUBULACAO_AGUA_FRIA_40_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_50 = "_HID3_TUBULACAO_AGUA_FRIA_50_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_60 = "_HID3_TUBULACAO_AGUA_FRIA_60_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_75 = "_HID3_TUBULACAO_AGUA_FRIA_75_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_85 = "_HID3_TUBULACAO_AGUA_FRIA_85_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_110 = "_HID3_TUBULACAO_AGUA_FRIA_110_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_22 = "_HID3_TUBULACAO_AGUA_QUENTE_22_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_28 = "_HID3_TUBULACAO_AGUA_QUENTE_28_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_35 = "_HID3_TUBULACAO_AGUA_QUENTE_35_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_42 = "_HID3_TUBULACAO_AGUA_QUENTE_42_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_54 = "_HID3_TUBULACAO_AGUA_QUENTE_54_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_60 = "_HID3_TUBULACAO_AGUA_QUENTE_60_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_73 = "_HID3_TUBULACAO_AGUA_QUENTE_73_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_89 = "_HID3_TUBULACAO_AGUA_QUENTE_89_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_114 = "_HID3_TUBULACAO_AGUA_QUENTE_114_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_20 = "_HID3_TUBULACAO_AGUA_FRIA_PEX_20_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_25 = "_HID3_TUBULACAO_AGUA_FRIA_PEX_25_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_32 = "_HID3_TUBULACAO_AGUA_FRIA_PEX_32_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_20 = "_HID3_TUBULACAO_AGUA_QUENTE_PEX_20_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_25 = "_HID3_TUBULACAO_AGUA_QUENTE_PEX_25_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_32 = "_HID3_TUBULACAO_AGUA_QUENTE_PEX_32_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_20 = "_HID3_TUBULACAO_AGUA_REUSO_20_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_25 = "_HID3_TUBULACAO_AGUA_REUSO_25_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_32 = "_HID3_TUBULACAO_AGUA_REUSO_32_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_40 = "_HID3_TUBULACAO_AGUA_REUSO_40_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_50 = "_HID3_TUBULACAO_AGUA_REUSO_50_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_60 = "_HID3_TUBULACAO_AGUA_REUSO_60_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_75 = "_HID3_TUBULACAO_AGUA_REUSO_75_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_85 = "_HID3_TUBULACAO_AGUA_REUSO_85_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_REUSO_110 = "_HID3_TUBULACAO_AGUA_REUSO_110_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_20 = "_HID3_TUBULACAO_AGUA_TRATADA_20_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_25 = "_HID3_TUBULACAO_AGUA_TRATADA_25_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_32 = "_HID3_TUBULACAO_AGUA_TRATADA_32_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_40 = "_HID3_TUBULACAO_AGUA_TRATADA_40_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_50 = "_HID3_TUBULACAO_AGUA_TRATADA_50_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_60 = "_HID3_TUBULACAO_AGUA_TRATADA_60_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_75 = "_HID3_TUBULACAO_AGUA_TRATADA_75_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_85 = "_HID3_TUBULACAO_AGUA_TRATADA_85_";
	public static final String ACTION_HID3_TUBULACAO_AGUA_TRATADA_110 = "_HID3_TUBULACAO_AGUA_TRATADA_110_";
	//MENU: HID4 (HIDRAULICA 4)	
	public static final String ACTION_HID4_BUCHA_REDUCAO = "_HID4_BUCHA_REDUCAO_";
	public static final String ACTION_HID4_REGISTRO_AGUA_FRIA = "_HID4_REGISTRO_AGUA_FRIA_";
	public static final String ACTION_HID4_REGISTRO_AGUA_QUENTE = "_HID4_REGISTRO_AGUA_QUENTE_";
	public static final String ACTION_HID4_REGISTRO_3_4_30 = "_HID4_REGISTRO_3_4_30_";
	public static final String ACTION_HID4_TORNEIRA_LAVAGEM = "_HID4_TORNEIRA_LAVAGEM_";
	public static final String ACTION_HID4_FINAL_TUBULACAO = "_HID4_FINAL_TUBULACAO_";
	//MENU: HID5 (HIDRAULICA 5)	
	public static final String ACTION_HID5_SETA_INDICADOR_DESCE = "_HID5_SETA_INDICADOR_DESCE_";
	public static final String ACTION_HID5_SETA_INDICADOR_PASSA = "_HID5_SETA_INDICADOR_PASSA_";
	public static final String ACTION_HID5_SETA_INDICADOR_SOBE = "_HID5_SETA_INDICADOR_SOBE_";
	public static final String ACTION_HID5_SETA_SIMPLES_DESCE = "_HID5_SETA_SIMPLES_DESCE_";
	public static final String ACTION_HID5_SETA_SIMPLES_PASSA = "_HID5_SETA_SIMPLES_PASSA_";
	public static final String ACTION_HID5_SETA_SIMPLES_SOBE = "_HID5_SETA_SIMPLES_SOBE_";
	//ACTIONS: G1 (GAS 1)
	public static final String ACTION_G1_INSERE_TB_GAS_PISO = "_TB_PISO_GAS_";
	public static final String ACTION_G1_INSERE_TB_GAS_TETO = "_TB_TETO_GAS_";
	public static final String ACTION_G1_INSERE_CANALETA_GAS = "_CANALETA_GAS_";
	public static final String ACTION_G1_INSERE_SEPTO_GAS = "_SEPTO_GAS_";
	public static final String ACTION_G1_INSERE_PRUMADA_GAS = "_PRUMADA_GAS_";
	public static final String ACTION_G1_INSERE_PONTO_GAS = "_PONTO_GAS_";
	public static final String ACTION_G1_INSERE_SETA_SIMPLES_PASSA = "_SETA_PASSA_";
	public static final String ACTION_G1_INSERE_SETA_SIMPLES_SOBE = "_SETA_SOBE_";
	public static final String ACTION_G1_INSERE_SETA_SIMPLES_DESCE = "_SETA_DESCE_";
	public static final String ACTION_G1_INSERE_INDICADOR_ECONOMIA = "_INDICADOR_ECONOMIA_";
	//ACTIONS: BLOCKS
	public static final String ACTION_BLOCKS_INSERTSHAPE = "_INSERT_SHAPE_";
	public static final String ACTION_BLOCKS_CREATESHAPE = "_CREATE_SHAPE_";
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
	public static final String ACTION_DRAW1_CILINDER3D = "_CILINDER3D_";
	public static final String ACTION_DRAW1_CONE3D = "_CONE3D_";
	public static final String ACTION_DRAW1_TRONCOCONE3D = "_TRONCOCONE3D_";
	public static final String ACTION_DRAW1_TORUS3D = "_TORUS3D_";
	public static final String ACTION_DRAW1_SPHERE3D = "_SPHERE3D_";
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
	public static final String ACTION_ZOOM_VIEWTOP = "_ZOOMVIEWTOP_";
	public static final String ACTION_ZOOM_VIEWBOTTOM = "_ZOOMVIEWBOTTOM_";
	public static final String ACTION_ZOOM_VIEWBACK = "_ZOOMVIEWBACK_";
	public static final String ACTION_ZOOM_VIEWFRONT = "_ZOOMVIEWFRONT_";
	public static final String ACTION_ZOOM_VIEWLEFT = "_ZOOMVIEWLEFT_";
	public static final String ACTION_ZOOM_VIEWRIGHT = "_ZOOMVIEWRIGHT_";	
	//public static final String ACTION_ZOOM_MOVEFORWARD = "_MOVEFORWARD_";
	//public static final String ACTION_ZOOM_MOVEBACKWARD = "_MOVEBACKWARD_";
	//public static final String ACTION_ZOOM_TURNLEFT = "_TURNLEFT_";
	//public static final String ACTION_ZOOM_TURNRIGHT = "_TURNRIGHT_";
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
	//MENU: MENUS
	public static final String ACTION_MENUS_ARQMENU = "_MENU_ARQUITETURA_";
	public static final String ACTION_MENUS_FORMENU = "_MENU_FORMAS_";
	public static final String ACTION_MENUS_FUMENU = "_MENU_FURACAO_";
	public static final String ACTION_MENUS_EEMENU = "_MENU_ENTRADA_ENERGIA_";
	public static final String ACTION_MENUS_ELMENU = "_MENU_ELETRICA_";
	public static final String ACTION_MENUS_ESMENU = "_MENU_ESGOTO_";
	public static final String ACTION_MENUS_APMENU = "_MENU_AGUAS_PLUVIAIS_";
	public static final String ACTION_MENUS_RPDMENU = "_MENU_REDES_PUBLICAS_DRENAGEM_";
	public static final String ACTION_MENUS_HMENU = "_MENU_HIDRAULICA_";
	public static final String ACTION_MENUS_INCMENU = "_MENU_INCENDIO_";
	public static final String ACTION_MENUS_GMENU = "_MENU_GAS_";
	public static final String ACTION_MENUS_IEMENU = "_MENU_INSTALACOES_ESPECIAIS_";
	public static final String ACTION_MENUS_TEMENU = "_MENU_TELEFONIA_";
	public static final String ACTION_MENUS_ARMENU = "_MENU_AR_CONDICIONADO_";
	
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

	//ARR_TIPOS_PAREDE
	//
	public static final ItemDataVO[] ARR_WALLTYPE = {
		new ItemDataVO(Integer.toString(AppDefs.WALLTYPE_BASICA), "Basica"),
		new ItemDataVO(Integer.toString(AppDefs.WALLTYPE_ALVENARIA), "Alvenaria"),
		new ItemDataVO(Integer.toString(AppDefs.WALLTYPE_DRYWALL), "Dry-wall"),
		new ItemDataVO(Integer.toString(AppDefs.WALLTYPE_PLACAS_CIMENTICIAS), "Placas Cimenticias"),
		new ItemDataVO(Integer.toString(AppDefs.WALLTYPE_DIVISORIA_VIDRO), "Divisoria de Vidro"),
		new ItemDataVO(Integer.toString(AppDefs.WALLTYPE_DIVISORIA_NAVAL), "Divisoria Naval")
	};
	
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

	//ARR_TIPOS_PAREDE
	//
	public static final ItemDataVO[] ARR_DOORTYPE = {
		new ItemDataVO(Integer.toString(AppDefs.DOORTYPE_BASICA), "Basica"),
		new ItemDataVO(Integer.toString(AppDefs.DOORTYPE_MADEIRA), "Madeira"),
		new ItemDataVO(Integer.toString(AppDefs.DOORTYPE_FERRO), "Ferro"),
		new ItemDataVO(Integer.toString(AppDefs.DOORTYPE_VIDRO), "Vidro")
	};
	
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

	//ARR_TIPOS_PAREDE
	//
	public static final ItemDataVO[] ARR_WINDOWTYPE = {
		new ItemDataVO(Integer.toString(AppDefs.WINDOWTYPE_BASICA), "Basica"),
		new ItemDataVO(Integer.toString(AppDefs.WINDOWTYPE_MADEIRA), "Madeira"),
		new ItemDataVO(Integer.toString(AppDefs.WINDOWTYPE_FERRO), "Ferro"),
		new ItemDataVO(Integer.toString(AppDefs.WINDOWTYPE_VIDRO), "Vidro")
	};

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

	//ARR_TIPOS_PISO
	//
	public static final ItemDataVO[] ARR_FLOORTYPE = {
		new ItemDataVO(Integer.toString(AppDefs.FLOORTYPE_BASICA), "Basica"),
		new ItemDataVO(Integer.toString(AppDefs.FLOORTYPE_MADEIRA), "Alvenaria"),
		new ItemDataVO(Integer.toString(AppDefs.FLOORTYPE_TACO), "Dry-wall"),
		new ItemDataVO(Integer.toString(AppDefs.FLOORTYPE_CERAMICA), "Ceramica"),
		new ItemDataVO(Integer.toString(AppDefs.FLOORTYPE_CIMENTO), "Cimento"),
		new ItemDataVO(Integer.toString(AppDefs.FLOORTYPE_ASFALTO), "Asfalto")
	};
	
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
	
	/*
	 * ABERTURAS
	 */
	
	//LARGURAS_ABERTURA
	//
	public static final double OPENNINGWIDTH_60CM = 0.6;
	public static final double OPENNINGWIDTH_70CM = 0.7;
	public static final double OPENNINGWIDTH_80CM = 0.8;
	public static final double OPENNINGWIDTH_90CM = 0.9;
	//
	public static final double OPENNINGWIDTH_100CM = 1.0;
	public static final double OPENNINGWIDTH_110CM = 1.1;
	public static final double OPENNINGWIDTH_120CM = 1.2;
	public static final double OPENNINGWIDTH_130CM = 1.3;
	public static final double OPENNINGWIDTH_140CM = 1.4;
	public static final double OPENNINGWIDTH_150CM = 1.5;
	public static final double OPENNINGWIDTH_160CM = 1.6;
	public static final double OPENNINGWIDTH_170CM = 1.7;
	public static final double OPENNINGWIDTH_180CM = 1.8;
	public static final double OPENNINGWIDTH_190CM = 1.9;
	public static final double OPENNINGWIDTH_200CM = 2.0;

	//ALTURAS_JANELA
	//
	public static final double OPENNINGHEIGHT_60CM = 0.6;
	public static final double OPENNINGHEIGHT_70CM = 0.7;
	public static final double OPENNINGHEIGHT_80CM = 0.8;
	public static final double OPENNINGHEIGHT_90CM = 0.9;
	//
	public static final double OPENNINGHEIGHT_100CM = 1.0;
	public static final double OPENNINGHEIGHT_110CM = 1.1;
	public static final double OPENNINGHEIGHT_120CM = 1.2;
	public static final double OPENNINGHEIGHT_130CM = 1.3;
	public static final double OPENNINGHEIGHT_140CM = 1.4;
	public static final double OPENNINGHEIGHT_150CM = 1.5;
	
	//ALTURAS_PISO_JANELA
	//
	public static final double OPENNINGFLOORHEIGHT_30CM = 0.3;
	public static final double OPENNINGFLOORHEIGHT_40CM = 0.4;
	public static final double OPENNINGFLOORHEIGHT_50CM = 0.5;
	public static final double OPENNINGFLOORHEIGHT_60CM = 0.6;
	public static final double OPENNINGFLOORHEIGHT_70CM = 0.7;
	public static final double OPENNINGFLOORHEIGHT_80CM = 0.8;
	public static final double OPENNINGFLOORHEIGHT_90CM = 0.9;
	//
	public static final double OPENNINGFLOORHEIGHT_100CM = 1.0;
	public static final double OPENNINGFLOORHEIGHT_110CM = 1.1;
	public static final double OPENNINGFLOORHEIGHT_120CM = 1.2;
	public static final double OPENNINGFLOORHEIGHT_130CM = 1.3;
	public static final double OPENNINGFLOORHEIGHT_140CM = 1.4;
	public static final double OPENNINGFLOORHEIGHT_150CM = 1.5;
	
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

	//MOUSE_DRAGGED
	//
	public static final long MOUSEDRAGGED_TIMEOUT = 125;
	
	//MOUSE_WHEEL
	//
	public static final long MOUSEWHEEL_TIMEOUT = 50;

	//
	//
	public static final long SCREENCONTEXT_TIMEOUT = 125;
	
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

	//VIEW_PRECISION
	//
	public static final double VIEWPREC_MIN = 5.0;
	
	//MATH_PRECISION_FILE
	//
	public static final double MATHPREC_FILE = 1000000.0;
	
	//ARC/CIRCLE NUMBER SEGMENTS
	//
	public static final int DRAWARC_NUMBER_SEGMENTS = 20;
	public static final int DRAWCIRCLE_NUMBER_SEGMENTS = 20;
	
	//DETAILVIEW - MCS
	//
	public static final double MCSDETAIL_XSIZE_MILI = PAPERSIZE_A4_WIDTH_MILI;
	public static final double MCSDETAIL_YSIZE_MILI = PAPERSIZE_A4_HEIGHT_MILI;
	//
	public static final double MCSDETAIL_ZHEIGHT = 10.0;
	//
	public static final double MCSDETAIL_SCALEFACTOR = 50.0 / 1000.0;		// ScaleFactor = DrawingScale / UnitsInMilimeters
	
	//PLANVIEW - MCS
	//
	public static final double MCSPLAN_XSIZE_MILI = PAPERSIZE_A4_WIDTH_MILI;
	public static final double MCSPLAN_YSIZE_MILI = PAPERSIZE_A4_HEIGHT_MILI;
	//
	public static final double MCSPLAN_ZHEIGHT = 10.0;
	//
	public static final double MCSPLAN_SCALEFACTOR = 50.0 / 1000.0;		// ScaleFactor = DrawingScale / UnitsInMilimeters
	
	//3DVIEW - PROJ
	//
	public static final double PROJPLAN_OBSERVER_HEIGHT = 1.85;			// Altura Observador = 1.85 metros
	public static final double PROJPLAN_OBSERVER_DISTANCE = 0.01;		// Distancia Observador (Plano Projecao) = 0.01 metros
	//
	public static final double PROJPLAN_WIDTH = 10.0;
	public static final double PROJPLAN_HEIGHT = 10.0;
	public static final double PROJPLAN_ZHEIGHT = 10.0;

	//3DVIEW
	//
	public static final double ZOOM_DIST_VIEWTOP = 100.0;				// 100.0 metros
	public static final double ZOOM_DIST_VIEWBOTTOM = 100.0;			// 100.0 metros
	public static final double ZOOM_DIST_VIEWFRONT = 100.0;				// 100.0 metros
	public static final double ZOOM_DIST_VIEWBACK = 100.0;				// 100.0 metros
	public static final double ZOOM_DIST_VIEWLEFT = 100.0;				// 100.0 metros
	public static final double ZOOM_DIST_VIEWRIGHT = 100.0;				// 100.0 metros
	//3DVIEW - MOVE and ROTATE
	//
	public static final double ZOOM_DIST_TILTUP = 0.05;					//  0.05 metros
	public static final double ZOOM_DIST_TILTDOWN = -0.05;				// -0.05 metros
	public static final double ZOOM_DIST_TILTLEFT = 0.05;				//  0.05 metros
	public static final double ZOOM_DIST_TILTRIGHT = -0.05;				// -0.05 metros
	//
	public static final double ZOOM_DIST_ROTATEUP = 0.05;				//  0.05 degrees
	public static final double ZOOM_DIST_ROTATEDOWN = -0.05;			// -0.05 degrees
	public static final double ZOOM_DIST_ROTATELEFT = 0.05;				//  0.05 degrees
	public static final double ZOOM_DIST_ROTATERIGHT = -0.05;			// -0.05 degrees
	//
	public static final double ZOOM_MOVEFORWARD = 0.5;					//  0.5 metros
	public static final double ZOOM_MOVEBACKWARD = -0.5;				// -0.5 metros
	public static final double ZOOM_HEIGHTUP = 0.5;						//  0.5 metros
	public static final double ZOOM_HEIGHTDOWN = -0.5;					// -0.5 metros
	
	//FACE_TYPE
	//
	public static final int FACETYPE_NONE = -1;
	public static final int FACETYPE_3PTS = 3;
	public static final int FACETYPE_4PTS = 4;
	public static final int FACETYPE_NPTS = 9999;
	
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
	public static double FONTSZ_SMALL = 1.6;
	public static double FONTSZ_NORMAL = 2.0; 
	public static double FONTSZ_MEDIUM = 3.0; 
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
	public static int AREATYPE_DRENAGEAREA = 5990;

	//ARR_AREA_TYPE
	//
	public static ItemDataVO[] ARR_AREATYPE = {
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_ROOM), "Quarto"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_APARTMENT), "Apartamento"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_BALCONY), "Varanda"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_BUILDINGCOMMOM), "Area Comum"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_BUILDINGINTERNAL), "Area Interna"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_BUILDINGEXTERNAL), "Area Externa"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_PARKING), "Estacionamento"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_TERRAIN), "Terreno"),
		new ItemDataVO(Integer.toString(AppDefs.AREATYPE_DRENAGEAREA), "Area Drenagem")
	};
	
	//DISCIPLINE_VAL
	//
	public static final int DISCIPLINE_NONE_VAL = -1;
	public static final int DISCIPLINE_ARQ_VAL  = 6001;
	public static final int DISCIPLINE_FOR_VAL  = 6002;
	public static final int DISCIPLINE_FU_VAL   = 6003;
	public static final int DISCIPLINE_EE_VAL   = 6004;
	public static final int DISCIPLINE_EL_VAL   = 6005;
	public static final int DISCIPLINE_ES_VAL   = 6006;
	public static final int DISCIPLINE_AP_VAL   = 6007;
	public static final int DISCIPLINE_RPD_VAL  = 6008;
	public static final int DISCIPLINE_H_VAL    = 6009;
	public static final int DISCIPLINE_INC_VAL  = 6010;
	public static final int DISCIPLINE_G_VAL    = 6011;
	public static final int DISCIPLINE_IE_VAL   = 6012;
	public static final int DISCIPLINE_TE_VAL   = 6013;
	public static final int DISCIPLINE_AR_VAL   = 6014;
	
	//DISCIPLINE_STR
	//
	public static final String DISCIPLINE_ARQ_STR = "Arquitetura";
	public static final String DISCIPLINE_FOR_STR = "Formas";
	public static final String DISCIPLINE_FU_STR  = "Furacao";
	public static final String DISCIPLINE_EE_STR  = "Entrada Energia";
	public static final String DISCIPLINE_EL_STR  = "Eletrica";
	public static final String DISCIPLINE_ES_STR  = "Esgoto";
	public static final String DISCIPLINE_AP_STR  = "Aguas Pluviais";
	public static final String DISCIPLINE_RPD_STR = "Redes Publicas Drenagem";
	public static final String DISCIPLINE_H_STR   = "Hidraulica";
	public static final String DISCIPLINE_INC_STR = "Incendio";
	public static final String DISCIPLINE_G_STR   = "Gas";
	public static final String DISCIPLINE_IE_STR  = "Instalacoes Especiais";
	public static final String DISCIPLINE_TE_STR  = "Telefonia";
	public static final String DISCIPLINE_AR_STR  = "Ar Condicionado";

	//ARR_DISCIPLINE
	//
	public static ItemDataVO[] ARR_DISCIPLINE = {
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_ARQ_VAL), AppDefs.DISCIPLINE_ARQ_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_FOR_VAL), AppDefs.DISCIPLINE_FOR_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_FU_VAL), AppDefs.DISCIPLINE_FU_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_EE_VAL), AppDefs.DISCIPLINE_EE_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_EL_VAL), AppDefs.DISCIPLINE_EL_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_ES_VAL), AppDefs.DISCIPLINE_ES_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_AP_VAL), AppDefs.DISCIPLINE_AP_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_RPD_VAL), AppDefs.DISCIPLINE_RPD_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_H_VAL), AppDefs.DISCIPLINE_H_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_INC_VAL), AppDefs.DISCIPLINE_INC_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_G_VAL), AppDefs.DISCIPLINE_G_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_IE_VAL), AppDefs.DISCIPLINE_IE_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_TE_VAL), AppDefs.DISCIPLINE_TE_STR),
		new ItemDataVO(Integer.toString(AppDefs.DISCIPLINE_AR_VAL), AppDefs.DISCIPLINE_AR_STR),
	};
	
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
	
	/* FILTER_ENTITY_TABLE */
	public static int FILTERENTTBL_MAXNUMENTITIES_PER_EXECUTOR = 100;
	
	/* SEQUENCE_VALUES (OBJECT_TYPES) 
	 */
	public static final long SEQ_CAD_OBJECTID_MIN				= 9000000000L;
	public static final long SEQ_CAD_OBJECTID_MAX				= 9999999999L;
	//
	public static final long SEQ_CAD_LAYER_DEF_MIN				= 2001000000L;
	public static final long SEQ_CAD_LAYER_DEF_MAX				= 2001999999L;
	//
	public static final long SEQ_CAD_BLOCK_DEF_MIN				= 2099000000L; 
	public static final long SEQ_CAD_BLOCK_DEF_MAX				= 2099999999L; 
	//
	public static final long SEQ_CAD_DOCUMENT_DEF_MIN			= 2999000000L; 
	public static final long SEQ_CAD_DOCUMENT_DEF_MAX			= 2999999999L; 
	//
	public static final long SEQ_CAD_ACABAMENTOPAREDE_DEF_MIN	= 3001000000L;
	public static final long SEQ_CAD_ACABAMENTOPAREDE_DEF_MAX	= 3001999999L;
	//
	public static final long SEQ_CAD_ACABAMENTOPISO_DEF_MIN		= 3002000000L;
	public static final long SEQ_CAD_ACABAMENTOPISO_DEF_MAX		= 3002999999L;
	//
	public static final long SEQ_CAD_ACABAMENTOPORTA_DEF_MIN	= 3003000000L;
	public static final long SEQ_CAD_ACABAMENTOPORTA_DEF_MAX	= 3003999999L;
	//
	public static final long SEQ_CAD_ACABAMENTOJANELA_DEF_MIN	= 3004000000L;
	public static final long SEQ_CAD_ACABAMENTOJANELA_DEF_MAX	= 3004999999L;
	//
	public static final long SEQ_CAD_POINT_MIN					= 4001000000L;
	public static final long SEQ_CAD_POINT_MAX					= 4001999999L;
	//
	public static final long SEQ_CAD_LINE_MIN					= 4002000000L;
	public static final long SEQ_CAD_LINE_MAX					= 4002999999L;
	//
	public static final long SEQ_CAD_RECTANGLE_MIN				= 4003000000L;
	public static final long SEQ_CAD_RECTANGLE_MAx				= 4003999999L;
	//
	public static final long SEQ_CAD_CIRCLE_MIN					= 4004000000L;
	public static final long SEQ_CAD_CIRCLE_MAX					= 4004999999L;
	//
	public static final long SEQ_CAD_ARC_MIN					= 4005000000L;
	public static final long SEQ_CAD_ARC_MAX					= 4005999999L;
	//
	public static final long SEQ_CAD_TEXT_MIN					= 4006000000L;
	public static final long SEQ_CAD_TEXT_MAX					= 4006999999L;
	//
	public static final long SEQ_CAD_POLYGON_MIN				= 4007000000L;
	public static final long SEQ_CAD_POLYGON_MAX				= 4007999999L;
	//
	public static final long SEQ_CAD_INSERT_MIN					= 4099000000L; 
	public static final long SEQ_CAD_INSERT_MAX					= 4099999999L; 
	//
	public static final long SEQ_CAD_BOX3D_MIN					= 5001000000L;
	public static final long SEQ_CAD_BOX3D_MAX					= 5001999999L;
	//
	public static final long SEQ_CAD_PAREDE_MIN					= 6001000000L;
	public static final long SEQ_CAD_PAREDE_MAX					= 6001999999L;
	//
	public static final long SEQ_CAD_PORTA_MIN					= 6002000000L;
	public static final long SEQ_CAD_PORTA_MAX					= 6002999999L;
	//
	public static final long SEQ_CAD_PDUPLA_MIN					= 6003000000L;
	public static final long SEQ_CAD_PDUPLA_MAX					= 6003999999L;
	//
	public static final long SEQ_CAD_JANELA_MIN					= 6004000000L;
	public static final long SEQ_CAD_JANELA_MAX					= 6004999999L;
	//
	public static final long SEQ_CAD_PISO_MIN					= 6005000000L;
	public static final long SEQ_CAD_PISO_MAX					= 6005999999L;
	//
	public static final long SEQ_CAD_AREA_MIN					= 6006000000L;
	public static final long SEQ_CAD_AREA_MAX					= 6006999999L;
	//
	public static final long SEQ_CAD_AREATABLE_MIN				= 7001000000L;
	public static final long SEQ_CAD_AREATABLE_MAX				= 7001999999L;
	
	/* IMAGEIN
	 */
	public static final String DEF_IMGIN_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/MapQuestDefaultImage.jpg";
	
	/* DXFIN/DXFOUT
	 */
	//public static final String DEF_DXFIN_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/SampleIn1.dxf";
	//public static final String DEF_DXFIN_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/20250426-terreo-01.dxf";
	public static final String DEF_DXFIN_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/20250426-terreo-05.dxf";
	//public static final String DEF_DXFIN_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/ACM_BINÁRIO_ESTRADA_RJ_SP_LEV_TOP_ALT_REV2.dxf";
	//public static final String DEF_DXFIN_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/20250426-terreo-06.dxf";
	public static final String DEF_DXFOUT_FILENAME				= "/home/lmarcio/9997-TLMV/991-SANETECH/_SAMPLES/SampleOut1.dxf";
	
	/* DXFCODES
	 */
	public static final int DXFCODE_ENTITYTYPE					= 0;	
	public static final int DXFCODE_NAME						= 2;	
	//
	public static final int DXFCODE_STARTPOINT_X				= 10;	
	public static final int DXFCODE_STARTPOINT_Y				= 20;	
	public static final int DXFCODE_STARTPOINT_Z				= 30;	
	//
	public static final int DXFCODE_ENDPOINT_X					= 11;	
	public static final int DXFCODE_ENDPOINT_Y					= 21;	
	public static final int DXFCODE_ENDPOINT_Z					= 31;	
	//
	public static final int DXFCODE_RADIUS						= 40;	
	//
	public static final int DXFCODE_STARTANGLE					= 50;	
	public static final int DXFCODE_ENDANGLE					= 51;	
	//
	public static final int DXFCODE_ENTITIESFOLLOWFLAG			= 66;	
	
	/* DXF_ENTITY_TYPE
	 */
	public static final String DXFETYPE_SECTION					= "SECTION";
	public static final String DXFETYPE_ENDSEC					= "ENDSEC";
	//
	public static final String DXFETYPE_LINE					= "LINE";
	public static final String DXFETYPE_CIRCLE					= "CIRCLE";
	public static final String DXFETYPE_ARC						= "ARC";
	public static final String DXFETYPE_POLYLINE				= "POLYLINE";
	public static final String DXFETYPE_VERTEX 					= "VERTEX";
	//
	public static final String DXFETYPE_SEQEND 					= "SEQEND";
	
	/* SECTION_NAME
	 */
	public static final String DXFSECTION_HEADER				= "HEADER";
	public static final String DXFSECTION_TABLES				= "TABLES";
	public static final String DXFSECTION_BLOCKS				= "BLOCKS";
	public static final String DXFSECTION_ENTITIES				= "ENTITIES";
	
	/* TIPOS CAIXA_INSPECAO
	 */
	public static final String DEF_TIPOCI_ESGOTO				= "_ESGOTO_";
	public static final String DEF_TIPOCI_APLUVIAL				= "_APLUVIAL_";
	public static final String DEF_TIPOCI_DRENAGEM				= "_DRENAGEM_";

	//ARR_TIPOCI
	//
	public static ItemDataVO[] ARR_TIPOCI = {
		new ItemDataVO(AppDefs.DEF_TIPOCI_ESGOTO, "Esgoto"),
		new ItemDataVO(AppDefs.DEF_TIPOCI_APLUVIAL, "Agua Pluvial"),
		new ItemDataVO(AppDefs.DEF_TIPOCI_DRENAGEM, "Drenagem")
	};
	
	/* SUBTIPOS CAIXA_INSPECAO
	 */
	//ESGOTO
	public static final String DEF_SUBTIPOCI_ESGOTO_PRIMARIO	= "_ESGOTO_PRIMARIO_";
	public static final String DEF_SUBTIPOCI_ESGOTO_SECUNDARIO	= "_ESGOTO_SECUNDARIO_";
	public static final String DEF_SUBTIPOCI_ESGOTO_GORDURA		= "_ESGOTO_GORDURA_";
	public static final String DEF_SUBTIPOCI_ESGOTO_MLL			= "_ESGOTO_MLL_";
	//AGUA_PLUVIAL
	public static final String DEF_SUBTIPOCI_APLUVIAL_NORMAL	= "_APLUVIAL_NORMAL_";
	public static final String DEF_SUBTIPOCI_APLUVIAL_RETORNO	= "_APLUVIAL_RETORNO_";
	public static final String DEF_SUBTIPOCI_APLUVIAL_REUSO		= "_APLUVIAL_REUSO_";
	//DRENAGEM
	public static final String DEF_SUBTIPOCI_DRENAGEM_NORMAL	= "_DRENAGEM_NORMAL_";
	public static final String DEF_SUBTIPOCI_DRENAGEM_RETORNO	= "_DRENAGEM_RETORNO_";

	//ARR_SUBTIPOCI
	//
	public static ItemDataVO[] ARR_SUBTIPOCI = {
		//ESGOTO
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_ESGOTO_PRIMARIO, "Primario"),
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_ESGOTO_SECUNDARIO, "Secundario"),
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_ESGOTO_GORDURA, "Gordura"),
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_ESGOTO_MLL, "MLL"),
		//AGUA_PLUVIAL
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_APLUVIAL_NORMAL, "Normal"),
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_APLUVIAL_RETORNO, "Retorno"),
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_APLUVIAL_REUSO, "Reuso"),
		//DRENAGEM
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_DRENAGEM_NORMAL, "Normal"),
		new ItemDataVO(AppDefs.DEF_SUBTIPOCI_DRENAGEM_RETORNO, "Retorno")
	};
	
	// MARGIN
	//
	//MARGIN_LEFT + MARGIN_RIGHT
	public static final int MARGIN_LEFT = 15;
	public static final int MARGIN_RIGHT = 15;
	//MARGIN_TOP + MARGIN_BOTTOM
	public static final int MARGIN_TOP = 15;
	public static final int MARGIN_BOTTOM = 15;

	/* CONTROLS */

	//SPACE
	//
	//WIDTH
	public static final int SPACE_W5 = 5;		/* MODIFICADO */
	public static final int SPACE_W10 = 10;
	public static final int SPACE_W20 = 20;
	//HEIGHT
	public static final int SPACE_H5 = 5;
	public static final int SPACE_H10 = 10;
	public static final int SPACE_H20 = 20;
	public static final int SPACE_H25 = 25;
	public static final int SPACE_H30 = 30;
	public static final int SPACE_H40 = 40;
	public static final int SPACE_H50 = 50;
	public static final int SPACE_H60 = 60;
	public static final int SPACE_H70 = 70;
	public static final int SPACE_H85 = 80;

	//DATE
	//
	public static final int DATE_W150 = 150;
	//
	public static final int DATE_H20 = 20;
	
	//CHECKBOX
	//
	//WIDTH
	public static final int CHECKBOX_W500 = 500;
	public static final int CHECKBOX_W330 = 330;
	public static final int CHECKBOX_W210 = 210;
	public static final int CHECKBOX_W150 = 150;
	//HEIGHT
	public static final int CHECKBOX_H20 = 20;
	
	//COMBOBOX
	//
	//WIDTH
	public static final int COMBO_W820 = 820;
	public static final int COMBO_W630 = 630;
	public static final int COMBO_W580 = 580;
	public static final int COMBO_W450 = 450;
	public static final int COMBO_W430 = 430;
	public static final int COMBO_W395 = 395;
	public static final int COMBO_W390 = 390;
	public static final int COMBO_W360 = 360;
	public static final int COMBO_W330 = 330;
	public static final int COMBO_W260 = 260;
	public static final int COMBO_W250 = 250;
	public static final int COMBO_W150 = 150;
	public static final int COMBO_W100 = 100;
	//HEIGHT
	public static final int COMBO_H20 = 20;
	
	//LIST
	//
	//WIDTH
	public static final int LIST_W1100 = 1100;
	public static final int LIST_W1075 = 1075;
	public static final int LIST_W1050 = 1050;
	public static final int LIST_W1020 = 1020;
	public static final int LIST_W980 = 980;
	public static final int LIST_W890 = 890;
	public static final int LIST_W800 = 800;
	public static final int LIST_W780 = 780;
	public static final int LIST_W760 = 760;
	public static final int LIST_W700 = 700;
	public static final int LIST_W250 = 250;
	//
	public static final int LIST_H700 = 700;
	public static final int LIST_H600 = 600;
	public static final int LIST_H500 = 500;
	public static final int LIST_H410 = 410;
	public static final int LIST_H400 = 400;
	public static final int LIST_H380 = 380;
	public static final int LIST_H250 = 250;
	public static final int LIST_H50 = 50;

	//LABEL
	//
	//WIDTH
	public static final int LABEL_W800 = 800;
	public static final int LABEL_W780 = 780;
	public static final int LABEL_W760 = 760;
	public static final int LABEL_W700 = 700;
	public static final int LABEL_W600 = 600;
	public static final int LABEL_W500 = 500;
	public static final int LABEL_W400 = 400;
	public static final int LABEL_W300 = 300;
	public static final int LABEL_W250 = 250;
	public static final int LABEL_W200 = 200;
	public static final int LABEL_W150 = 150;
	public static final int LABEL_W140 = 140;
	public static final int LABEL_W100 = 100;
	public static final int LABEL_W75 = 75;
	public static final int LABEL_W70 = 70;
	public static final int LABEL_W50 = 50;
	public static final int LABEL_W30 = 30;
	//HEIGHT
	public static final int LABEL_H20 = 20;

	//TEXT
	//
	//WIDTH
	public static final int TEXT_W820 = 820;
	public static final int TEXT_W760 = 760;
	public static final int TEXT_W650 = 650;
	public static final int TEXT_W630 = 630;
	public static final int TEXT_W600 = 600;
	public static final int TEXT_W580 = 580;
	public static final int TEXT_W570 = 570;
	public static final int TEXT_W550 = 550;
	public static final int TEXT_W450 = 450;
	public static final int TEXT_W430 = 430;
	public static final int TEXT_W400 = 400;
	public static final int TEXT_W395 = 395;
	public static final int TEXT_W390 = 390;
	public static final int TEXT_W360 = 360;
	public static final int TEXT_W355 = 355;
	public static final int TEXT_W350 = 350;
	public static final int TEXT_W340 = 340;
	public static final int TEXT_W330 = 330;
	public static final int TEXT_W300 = 300;
	public static final int TEXT_W260 = 260;
	public static final int TEXT_W250 = 250;
	public static final int TEXT_W165 = 165;
	public static final int TEXT_W150 = 150;
	public static final int TEXT_W125 = 125;
	public static final int TEXT_W115 = 115;
	public static final int TEXT_W100 = 100;
	public static final int TEXT_W75 = 75;
	//HEIGHT
	public static final int TEXT_H180 = 180;
	public static final int TEXT_H170 = 170;
	public static final int TEXT_H80 = 80;
	public static final int TEXT_H20 = 20;

	//BUTTON
	//
	//WIDTH
	public static final int BUTTON_W150 = 150;
	public static final int BUTTON_W125 = 125;
	public static final int BUTTON_W100 = 100;
	public static final int BUTTON_W75 = 75;
	public static final int BUTTON_W50 = 50;
	public static final int BUTTON_W40 = 40;
	public static final int BUTTON_W30 = 30;
	//HEIGHT
	public static final int BUTTON_H20 = 20;
	
	//FrameSize
	//
	public static final int DEFAULT_XPOS = 100;
	public static final int DEFAULT_YPOS = 50;
	//
	public static final int EXTRA_MARGIN_WINDOWS = 20;
	//
	//LAYEREXPLORER_FRAME
	public static final int LAYEREXPLORER_FRAME_WIDTH = 800 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int LAYEREXPLORER_FRAME_HEIGHT = 600 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//MAIN_FRAME
	public static final int MAIN_FRAME_WIDTH = 1024 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int MAIN_FRAME_HEIGHT = 768 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//MESSAGE_FRAME
	public static final int MESSAGE_FRAME_WIDTH = 640 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int MESSAGE_FRAME_HEIGHT = 310 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//SETUP_FRAME
	public static final int SETUP_FRAME_WIDTH = 840 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int SETUP_FRAME_HEIGHT = 550 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_FRAME
	public static final int DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_FRAME_WIDTH = 350 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_FRAME_HEIGHT = 210 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_FRAME
	public static final int PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_FRAME_WIDTH = 500 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_FRAME_HEIGHT = 580 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME
	public static final int GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_WIDTH = 1100 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_HEIGHT = 700 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//GERAR_PLANTA_PERFIS_DRENAGEM_FRAME
	public static final int GERAR_PLANTA_PERFIS_DRENAGEM_FRAME_WIDTH = 550 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int GERAR_PLANTA_PERFIS_DRENAGEM_FRAME_HEIGHT = 75 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//OPENNSAVE_DATABASE_FRAME
	public static final int OPENSAVE_DATABASE_FRAME_WIDTH = 800 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int OPENSAVE_DATABASE_FRAME_HEIGHT = 600 + AppDefs.EXTRA_MARGIN_WINDOWS;
	//NEWDATABASE_FRAME
	public static final int NEWDATABASE_FRAME_WIDTH = 600 + AppDefs.EXTRA_MARGIN_WINDOWS;
	public static final int NEWDATABASE_FRAME_HEIGHT = 80 + AppDefs.EXTRA_MARGIN_WINDOWS;
	
	/* COMMANDS */
	
	public static final ICmdBase[] ARR_COMMAND = {
		//ACTIONS: FILE
		new CmdNew(),
		new CmdOpen(),
		new CmdClose(),
		new CmdSave(),
		new CmdSaveAs(),
		new CmdDxfIn(),
		new CmdDxfOut(),
		new CmdInsertImage(),
		new CmdSetup(),
		new CmdPrint(),
		new CmdStop(),
		new CmdExit(),
		//ACTIONS: ARQ1
		new CmdPiso(),
		new CmdParede(),
		new CmdAmbiente(),
		new CmdPorta(),
		new CmdPDupla(),
		new CmdParedeAbertura(),
		new CmdJanela(),
		new CmdArea(),
		//ACTIONS: ARQ2
		new CmdPontoArquitetura(ACTION_ARQ2_VASOSANITARIO,  AppDefs.SHAPE_ARQ_VASOSANITARIO),
		new CmdPontoArquitetura(ACTION_ARQ2_VASOCAIXAACLOPADA,  AppDefs.SHAPE_ARQ_VASOCAIXAACLOPADA),
		new CmdPontoArquitetura(ACTION_ARQ2_LAVATORIOGRANDE,  AppDefs.SHAPE_ARQ_LAVATORIOGRANDE),
		//ACTIONS: ES2
		new CmdPontoEsgoto(ACTION_ES4_RALO_SINFONADO,  AppDefs.SHAPE_ESG_RALO_SIFONADO),
		new CmdPontoEsgoto(ACTION_ES4_CAIXA_PASSAGEM_60X60,  AppDefs.SHAPE_ESG_CAIXA_PASSAGEM_60X60),
		//ACTIONS: RDP1		
		new CmdCsvImportarDrenagem(),
		new CmdCsvExportarDrenagem(),
		new CmdAreaContribuicaoDrenagem(),
		new CmdAnotacaoCaixaInspecaoDrenagem(),
		new CmdCaixaInspecaoDrenagem(),
		new CmdPropriedadeCaixaInspecaoDrenagem(),
		new CmdLigacaoCaixaInspecaoDrenagem(),
		new CmdVerifLigacaoCaixaInspecaoDrenagem(),
		new CmdDimensionaRedeDrenagem(),
		new CmdGerarPlantaPerfisDrenagem(),
		new CmdGerarPlantaAreaContribuicaoDrenagem(),
		new CmdGerarPlanilhaCalculoDrenagem(),
		new CmdAlterarPlanilhaCalculoDrenagem(),
		//ACTIONS: EL1		
		new CmdPontoEletrica(AppDefs.ACTION_EL1_INSERE_CAIXA_PASSAGEM_PISO, AppDefs.SHAPE_ELE_CAIXA_PASSAGEM_PISO),
		new CmdPontoEletrica(AppDefs.ACTION_EL1_INSERE_INTERRUPTOR_SIMPLES, AppDefs.SHAPE_ELE_INTERRUPTOR_SIMPLES),
		new CmdPontoEletrica(AppDefs.ACTION_EL1_INSERE_PONTO_FORCA_PISO, AppDefs.SHAPE_ELE_PONTO_FORCA_PISO),
		new CmdPontoEletrica(AppDefs.ACTION_EL1_INSERE_PONTO_LUZ, AppDefs.SHAPE_ELE_PONTO_LUZ),
		new CmdPontoEletrica(AppDefs.ACTION_EL1_INSERE_QUADRO_DISTRIBUICAO, AppDefs.SHAPE_ELE_QUADRO_DISTRIBUICAO),
		new CmdPontoEletrica(AppDefs.ACTION_EL1_INSERE_TOMADA_BAIXA, AppDefs.SHAPE_ELE_TOMADA_BAIXA),
		//ACTIONS: EL2		
		new CmdInsereEletrodutoEletrica(AppDefs.ACTION_EL2_INSERE_ELETRODUTO_TETO),
		new CmdInsereEletrodutoEletrica(AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PISO),
		new CmdInsereEletrodutoEletrica(AppDefs.ACTION_EL2_INSERE_ELETRODUTO_APARENTE),
		new CmdTrocaCircuito(),
		new CmdTrocaComando(),
		new CmdTrocaOrigem(),
		//ACTIONS: GAS
		new CmdPontoGas(AppDefs.ACTION_G1_INSERE_PONTO_GAS, AppDefs.SHAPE_G_PONTO_GAS),
		//ACTIONS: DRAW1
		new CmdOffset(),
		new CmdPoint(),
		new CmdLine(),
		new CmdRectangle(),
		new CmdPolygon(),
		new CmdArc(),
		new CmdCircle(),
		new CmdText(),
		new CmdAreaTable(),
		new CmdBox3D(),
		new CmdCilinder3D(),
		new CmdCone3D(),
		new CmdTroncoCone3D(),
		new CmdTorus3D(),
		new CmdSphere3D(),
		//ACTIONS: EDIT2
		new CmdErase(),
		new CmdCopy(),
		new CmdCreateShape(),
		new CmdMirror(),
		new CmdMove(),
		new CmdScale(),
		//new CmdRotate(),
		//ACTIONS: ZOOM
		new CmdPan(),
		new CmdZoomIn(),
		new CmdZoomOut(),
		new CmdZoomWindow(),
		new CmdZoomExt(),
		//ACTIONS: ZOOM-3D
		new CmdZoomViewTop(),
		new CmdZoomViewBottom(),
		new CmdZoomViewBack(),
		new CmdZoomViewFront(),
		new CmdZoomViewLeft(),
		new CmdZoomViewRight(),
		//ACTIONS: SETTINGS
		new CmdGridOnOff(),
		new CmdSnapOnOff(),
		new CmdOrthoOnOff(),
		//ACTIONS: LAYERS
		new CmdLayerExplorer(),
		//ACTIONS: INQUIRY
		new CmdDistance(),
		//ACTIONS: HELP
		new CmdAbout(),
		//ACTIONS: MENUS
		new CmdMenu(AppDefs.ACTION_MENUS_ARMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_ARQMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_FORMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_FUMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_EEMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_ELMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_ESMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_APMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_RPDMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_HMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_GMENU),
		//new CmdMenu(AppDefs.ACTION_MENUS_IEMENU),
		//new CmdMenu(AppDefs.ACTION_MENUS_INCMENU),
		new CmdMenu(AppDefs.ACTION_MENUS_TEMENU)
	};
	
	/* TABLES */

	public static final int TBLHDR_WIDTH = 300;		
	public static final int TBLHDR_HEIGHT = 30;		
	//
	public static final int TBLCOL_WIDTH = 50;		
	public static final int TBLCOL_HEIGHT = 30;

	//TABLE_HEADERS
	//
	
	//LAYER_EXPLORER_HEADERS
	public static final String[] HDR_TBLLAYEREXPLORER = new String[] {
		"Ativo", 
		"Nome", 
		"Cor", 
		"Ltype", 
		"On/Off"
	};	
	
	//OBJECT_PROPERTY_HEADERS
	public static final String[] HDR_TBLPROPERTYEDITOR = { 
		"Property", 
		"Value" 
	};
	
	public static final ColunaTabelaVO[] ARR_TBLCOL_MEMORIA_CALCULO = {
		new ColunaTabelaVO(1001, "rowid", "Codigo", 75, 30, false, Integer.class),
		new ColunaTabelaVO(1002, "pv", "PV", 150, 30, true, String.class),
		new ColunaTabelaVO(1003, "local", "Local", 150, 30, true, String.class),
		new ColunaTabelaVO(1004, "estaca", "Estaca", 150, 30, true, String.class),
		new ColunaTabelaVO(1005, "cotaTerreno", "Cota Terreno", 75, 30, true, Double.class),
		new ColunaTabelaVO(1006, "fundo", "Cota Fundo", 75, 30, false, Double.class),
		new ColunaTabelaVO(1007, "nivelAgua", "Nivel Agua", 75, 30, false, Double.class),
		new ColunaTabelaVO(1008, "area", "Area", 75, 30, true, Double.class),
		new ColunaTabelaVO(1009, "coefImper", "Coeficiente", 75, 30, false, Double.class),
		new ColunaTabelaVO(1010, "areaTotal", "Area Total", 75, 30, false, Double.class),
		new ColunaTabelaVO(1011, "coefDistr", "Coeficiente Distribuicao", 75, 30, false, Double.class),
		new ColunaTabelaVO(1012, "tempoConc", "Tempo Concentracao", 75, 30, false, Double.class),
		new ColunaTabelaVO(1013, "coefDefluv", "Coeficiente Defluvio", 75, 30, false, Double.class),
		new ColunaTabelaVO(1014, "deflLocal", "Defluvio Local", 75, 30, false, Double.class),
		new ColunaTabelaVO(1015, "deflEscoar", "Defluvio Escoar", 75, 30, false, Double.class),
		new ColunaTabelaVO(1016, "declividadeGreide", "Declividade Greide", 75, 30, false, Double.class),
		new ColunaTabelaVO(1017, "declividade", "Declividade", 75, 30, false, Double.class),
		new ColunaTabelaVO(1018, "dimensoes", "Dimensoes", 75, 30, false, Double.class),
		new ColunaTabelaVO(1019, "alturaAgua", "Altura Agua", 75, 30, false, Double.class),
		new ColunaTabelaVO(1020, "profMontJus", "Prof.Mont.Jusante", 75, 30, false, Double.class),
		new ColunaTabelaVO(1021, "velocidade", "Velocidade", 75, 30, false, Double.class),
		new ColunaTabelaVO(1022, "comprimento", "Comprimento", 75, 30, false, Double.class),
		new ColunaTabelaVO(1023, "tempoPercurso", "Tempo", 75, 30, false, Double.class),
		new ColunaTabelaVO(1024, "tempoTotal", "Tempo Total", 75, 30, false, Double.class),
		new ColunaTabelaVO(1025, "observacao", "Observacao", 300, 30, true, String.class)
	};

	//TIPOCAMPO
	//
	public static final String DEF_TIPOCAMPO_INT = "int";
	public static final String DEF_TIPOCAMPO_LONG = "long";
	public static final String DEF_TIPOCAMPO_DOUBLE = "double";
	public static final String DEF_TIPOCAMPO_STRING = "string";
	public static final String DEF_TIPOCAMPO_DATE = "date";	

	//TAG - EXPORTDATA
	//
	// --- CADPAREDE
	//
	public static final String DEF_TAG_CADPAREDE_OBJECT_ID 					= "[OBJECT_ID%s]";
	public static final String DEF_TAG_CADPAREDE_OBJTYPE 					= "[OBJTYPE%s]";
	public static final String DEF_TAG_CADPAREDE_LAYERNAME 					= "[LAYERNAME%s]";
	public static final String DEF_TAG_CADPAREDE_TIPO 						= "[TIPO%s]";
	public static final String DEF_TAG_CADPAREDE_ALTURA 					= "[ALTURA%s]";
	public static final String DEF_TAG_CADPAREDE_LARGURA 					= "[LARGURA%s]";
	public static final String DEF_TAG_CADPAREDE_XPTI 						= "[XPTI%s]";
	public static final String DEF_TAG_CADPAREDE_YPTI 						= "[YPTI%s]";
	public static final String DEF_TAG_CADPAREDE_ZPTI 						= "[ZPTI%s]";
	public static final String DEF_TAG_CADPAREDE_XPTF 						= "[XPTF%s]";
	public static final String DEF_TAG_CADPAREDE_YPTF 						= "[YPTF%s]";
	public static final String DEF_TAG_CADPAREDE_ZPTF 						= "[ZPTF%s]";
	//
	// --- MEMORIA_CALCULO
	//
	public static final String DEF_TAG_MEMCALC_PROJETO		 				= "[PROJETO]";
	public static final String DEF_TAG_MEMCALC_DATA_EMISSAO 				= "[DATA_EMISSAO]";
	public static final String DEF_TAG_MEMCALC_PLUVIOGRAFO 					= "[PLUVIOGRAFO]";
	public static final String DEF_TAG_MEMCALC_COEF_MANNING 				= "[COEF_MANNING]";
	public static final String DEF_TAG_MEMCALC_TR							= "[TR]";				// periodo de recorrencia (em anos)
	//
	// --- MEMORIA_CALCULO_ITEM
	//
	public static final String DEF_TAG_MEMCALCITEM_ROWID 					= "[ROWID%s]";
	public static final String DEF_TAG_MEMCALCITEM_PV 						= "[PV%s]";
	public static final String DEF_TAG_MEMCALCITEM_LOCAL 					= "[LOCAL%s]";
	public static final String DEF_TAG_MEMCALCITEM_ESTACA 					= "[ESTACA%s]";
	public static final String DEF_TAG_MEMCALCITEM_COTA_TERRENO 			= "[COTA_TERRENO%s]";		
	public static final String DEF_TAG_MEMCALCITEM_COTA_FUNDO 				= "[COTA_FUNDO%s]";		
	public static final String DEF_TAG_MEMCALCITEM_NIVEL_AGUA 				= "[NIVEL_AGUA%s]";		
	public static final String DEF_TAG_MEMCALCITEM_AREA 					= "[AREA%s]";		
	public static final String DEF_TAG_MEMCALCITEM_COEF 					= "[COEF%s]";		
	public static final String DEF_TAG_MEMCALCITEM_AREA_TOTAL 				= "[AREA_TOTAL%s]";		
	public static final String DEF_TAG_MEMCALCITEM_COEF_DISTR 				= "[COEF_DISTR%s]";		
	public static final String DEF_TAG_MEMCALCITEM_TEMPO_CONC 				= "[TEMPO_CONC%s]";
	public static final String DEF_TAG_MEMCALCITEM_INDICE_PLUVIOMETRICO		= "[INDICE_PLUVIOMETRICO%s]";
	public static final String DEF_TAG_MEMCALCITEM_COEF_DEFLUV 				= "[COEF_DEFLUV%s]";		
	public static final String DEF_TAG_MEMCALCITEM_DEFL_LOCAL 				= "[DEFL_LOCAL%s]";		
	public static final String DEF_TAG_MEMCALCITEM_DEFL_ESCOAR 				= "[DEFL_ESCOAR%s]";		
	public static final String DEF_TAG_MEMCALCITEM_DECLIVIDADE_GREIDE		= "[DECLIVIDADE_GREIDE%s]";		
	public static final String DEF_TAG_MEMCALCITEM_DECLIVIDADE 				= "[DECLIVIDADE%s]";		
	public static final String DEF_TAG_MEMCALCITEM_DIMENSOES 				= "[DIMENSOES%s]";		
	public static final String DEF_TAG_MEMCALCITEM_ALTURA_AGUA 				= "[ALTURA_AGUA%s]";
	public static final String DEF_TAG_MEMCALCITEM_YD		 				= "[YD%s]";
	public static final String DEF_TAG_MEMCALCITEM_PROF_MONT_JUS 			= "[PROF_MONT_JUS%s]";		
	public static final String DEF_TAG_MEMCALCITEM_VELOCIDADE 				= "[VELOCIDADE%s]";		
	public static final String DEF_TAG_MEMCALCITEM_COMPRIMENTO 				= "[COMPRIMENTO%s]";		
	public static final String DEF_TAG_MEMCALCITEM_TEMPO 					= "[TEMPO%s]";		
	public static final String DEF_TAG_MEMCALCITEM_TEMPO_TOTAL 				= "[TEMPO_TOTAL%s]";		
	public static final String DEF_TAG_MEMCALCITEM_OBSERVACAO 				= "[OBSERVACAO%s]";		
	
	//TEMPLATE_FILES
	//
	public static final String TEMPL_MEMORIA_CALCULO = "20250414-Template_Memoria_de_Calculo.xlsx";
	public static final String TEMPL_LISTAGEM_PAREDES = "20250417-Template_Listagem_de_Paredes.xlsx";
	public static final String TEMPL_TEMPLATEDB_FILE_V1_1 = "20250605-aicadx_template_v1.1.sql";

	//SHAPE_OPERATION_2D
	//
	public static final int DEF_SHPOPER2D_MOVETO = 5001;
	public static final int DEF_SHPOPER2D_LINETO = 5002;
	
	//SHAPE_FILE_TAGS
	//
	public static final String SHPFILE_TAG_SHAPE = "Shape";
	//
	public static final String SHPFILE_TAG_SHAPE_PARAMELETRICO = "ParamEletrico";
	//
	public static final String SHPFILE_TAG_SHAPE_GEOMSHAPE2D = "GeomShape2d";
	public static final String SHPFILE_TAG_SHAPE_GEOMSHAPE2D_SHAPEOPER2D = "ShapeOper2d";
	//
	public static final String SHPFILE_TAG_SHAPE_GEOMSHAPE3D = "GeomShape3d";
	public static final String SHPFILE_TAG_SHAPE_GEOMSHAPE3D_SHAPEFACE3D = "ShapeFace3d";
	public static final String SHPFILE_TAG_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE = "Vertice";
	
	//SHAPE_FILE_TAG_PARAMS
	//
	public static final String SHPFILE_TAGPARM_SHAPE_NAME = "name";
	//
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_PARMNUM = "parmNum";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_TIPO = "tipo";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_NOME_QUADRO = "nomeQuadro";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_QUADRO_ORIGEM = "quadroOrigem";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_NOME_CALHA = "nomeCalha";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_DESVIO = "desvio";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_POTENCIA = "potencia";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_POTENCIA_DEMANDADA = "potenciaDemandada";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_SISTEMA = "sistema";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_CIRCUITO = "circuito";
	public static final String SHPFILE_TAGPARM_SHAPE_PARAMELETRICO_COMANDO = "comando";
	//
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_ANNOTATION = "annotation";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_SHAPEOPER2D_DRAWLINE = "drawLine";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_SHAPEOPER2D_X = "x";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_SHAPEOPER2D_Y = "y";
	//
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_ANNOTATION = "annotation";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_FILL = "fill";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE_X = "x";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE_Y = "y";
	public static final String SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE_Z = "z";
	
	//TEMPLATEDB_FILE_V1_1_TAGS
	//
	public static final String TEMPLATEDBFILE_TAGPARM_SCHEMA_NAME = "#SCHEMA_NAME#";
	public static final String TEMPLATEDBFILE_TAGPARM_SEQUENCE_NAME = "#SEQ_NAME#";

    /* FIACAO */

    public static double FIA_ESPACO_MM = 2.0;   								// distancia entre fios =2mm

    /* definicao dos identificadores dos interruptores three-way e four-way
    */
    public static String FIA_ID3W = "3w";
    public static String FIA_ID4W = "4w";

    /* definicao das constantes que identificam o tipo de interruptor
    */
    public static int FIA_IS = 0;
    public static int FIA_I3W = 1;
    public static int FIA_I4W = 2;

    /* definicao das constantes caracter que define o tipo de elemento
    */
    public static String FIA_S_CARGA = "ECARGA";
    public static String FIA_S_COMANDO = "ECOMANDO";
    public static String FIA_S_QUADRO = "EQUADRO";
    public static String FIA_S_CAMPAINHA = "ECAMPAINHA";
    public static String FIA_S_ILUMINACAO = "EILUMINACAO";
    public static String FIA_S_CAIXA = "ECAIXA";
    public static String FIA_S_DESVIO = "EDESVIO";
    public static String FIA_S_CALHA = "ECALHA";

    /* definicao das constantes que definem o tipo de elemento
    */
    public static int FIA_CARGA = 0;
    public static int FIA_COMANDO = 1;
    public static int FIA_QUADRO = 2;
    public static int FIA_CAMPAINHA = 3;
    public static int FIA_ILUMINACAO = 4;
    public static int FIA_CAIXA = 5;
    public static int FIA_DESVIO = 6;
    public static int FIA_CALHA = 7;

    /* definicao das constantes que identificam a fiacao
    /* pertencente ao circuito que passa pelo eletroduto
    */
    public static int FIA_TR = 0x0001;      									// terra
    public static int FIA_N = 0x0002;       									// neutro
    public static int FIA_F1 = 0x0004;      									// fase (1)
    public static int FIA_F2 = 0x0008;      									// fase (2)
    public static int FIA_F3 = 0x0010;      									// fase (3)
    public static int FIA_RC = 0x0020;      									// retorno campainha
    public static int FIA_R1 = 0x0040;      									// retorno (1)
    public static int FIA_R2 = 0x0080;      									// retorno (2)
    public static int FIA_R3 = 0x0100;      									// retorno (3)
    public static int FIA_R4 = 0x0200;      									// retorno (4)
    public static int FIA_R5 = 0x0400;      									// retorno (5)
    public static int FIA_R6 = 0x0800;      									// retorno (6)
    public static int FIA_R7 = 0x1000;      									// retorno (7)
    public static int FIA_R8 = 0x2000;      									// retorno (8)
    public static int FIA_R9 = 0x4000;      									// retorno (9)
    public static int FIA_R10 = 0x8000;     									// retorno (10)

    /* definicao das familias de fiacao
    */
    public static String FAM_FIA_TR = "EL-Fio_Terra";                   		// terra
    public static String FAM_FIA_N = "EL-Fio_Neutro";                   		// neutro
    public static String FAM_FIA_F = "EL-Fio_Fase";                     		// fase
    public static String FAM_FIA_RC = "EL-Fio_Retorno_Campainha";       		// retorno campainha
    public static String FAM_FIA_R = "EL-Fio_Retorno";                  		// retorno

    /* definicao das constantes caracter que identificam o sistema de fase das economias
    */
    public static String FIA_S_FN = "F+N";                                      // fase + neutro
    public static String FIA_S_2F = "2F";                                       // bi-fasico
    public static String FIA_S_2FN = "2F+N";                                    // bi-fasico + neutro
    public static String FIA_S_3F = "3F";                                       // tri-fasico
    public static String FIA_S_3FN = "3F+N";                                    // tri-fasico + neutro
    public static String FIA_S_FNT = "F+N+T";                                   // fase + neutro + terra
    public static String FIA_S_2FT = "2F+T";                                    // bi-fasico + terra
    public static String FIA_S_2FNT = "2F+N+T";                                 // bi-fasico + neutro + terra
    public static String FIA_S_3FT = "3F+T";                                    // tri-fasico + terra
    public static String FIA_S_3FNT = "3F+N+T";                                 // tri-fasico + neutro + terra

    /* definicao das constantes caracter que identificam o equilibrio de fase dos circuitos
    */
    public static String FAS_R = "R";
    public static String FAS_S = "S";
    public static String FAS_T = "T";
    public static String FAS_RS = "RS";
    public static String FAS_RT = "RT";
    public static String FAS_ST = "ST";
    public static String FAS_RST = "RST";

    /* definicao das constantes que identificam o sistema de fase das economias
    */
    public static int FIA_FN = (FIA_F1 + FIA_N);                                // fase + neutro
    public static int FIA_2F = (FIA_F1 + FIA_F2);                               // bi-fasico
    public static int FIA_2FN = (FIA_F1 + FIA_F2 + FIA_N);                      // bi-fasico + neutro
    public static int FIA_3F = (FIA_F1 + FIA_F2 + FIA_F3);                      // tri-fasico
    public static int FIA_3FN = (FIA_F1 + FIA_F2 + FIA_F3 + FIA_N);             // tri-fasico + neutro
    public static int FIA_FNT = (FIA_F1 + FIA_N + FIA_TR);                      // fase + neutro + terra
    public static int FIA_2FT = (FIA_F1 + FIA_F2 + FIA_TR);                     // bi-fasico + terra
    public static int FIA_2FNT = (FIA_F1 + FIA_F2 + FIA_N + FIA_TR);            // bi-fasico + neutro + terra
    public static int FIA_3FT = (FIA_F1 + FIA_F2 + FIA_F3 + FIA_TR);            // tri-fasico + terra
    public static int FIA_3FNT = (FIA_F1 + FIA_F2 + FIA_F3 + FIA_N + FIA_TR);   // tri-fasico + neutro + terra

    /* definicao dos parametros que identificam os pontos eletricos
    */
    public static String FIA_PARAM_TIPO = "AI3D:{0}:TIPO";
    public static String FIA_PARAM_NOME_QUADRO = "AI3D:{0}:NOME_QUADRO";
    public static String FIA_PARAM_QUADRO_ORIGEM = "AI3D:{0}:QUADRO_ORIGEM";
    public static String FIA_PARAM_POTENCIA = "AI3D:{0}:POTENCIA";
    public static String FIA_PARAM_POTENCIA_DEMANDADA = "AI3D:{0}:POTENCIA_DEMANDADA";
    public static String FIA_PARAM_CIRCUITO = "AI3D:{0}:CIRCUITO";
    public static String FIA_PARAM_COMANDO = "AI3D:{0}:COMANDO";
    public static String FIA_PARAM_SISTEMA = "AI3D:{0}:SISTEMA";
    public static String FIA_PARAM_DESVIO = "AI3D:{0}:DESVIO";
    public static String FIA_PARAM_NOME_CALHA = "AI3D:{0}:NOME_CALHA";

    /* project scale definition
     */
    public static double PROJECT_SCALE_K10 = 10.0;
    public static double PROJECT_SCALE_K20 = 20.0;
    public static double PROJECT_SCALE_K25 = 25.0;
    public static double PROJECT_SCALE_K50 = 50.0;
    public static double PROJECT_SCALE_K75 = 75.0;
    //
    public static double PROJECT_SCALE_K100 = 100.0;
    public static double PROJECT_SCALE_K125 = 125.0;
    public static double PROJECT_SCALE_K200 = 200.0;
    public static double PROJECT_SCALE_K250 = 250.0;
    public static double PROJECT_SCALE_K500 = 500.0;
    //
    public static double PROJECT_SCALE_K1000 = 1000.0;
    public static double PROJECT_SCALE_K1500 = 1500.0;
    public static double PROJECT_SCALE_K2000 = 2000.0;
    public static double PROJECT_SCALE_K2500 = 2500.0;
    public static double PROJECT_SCALE_K5000 = 5000.0;
    //
    public static double PROJECT_SCALE_K10000 = 10000.0;
    
    public static ItemDataVO[] ARR_PROJECT_SCALE = {
    	new ItemDataVO("K1_10",    "1:10",    AppDefs.PROJECT_SCALE_K10),
    	new ItemDataVO("K1_20",    "1:20",    AppDefs.PROJECT_SCALE_K20),
    	new ItemDataVO("K1_25",    "1:25",    AppDefs.PROJECT_SCALE_K25),
    	new ItemDataVO("K1_50",    "1:50",    AppDefs.PROJECT_SCALE_K50),
    	new ItemDataVO("K1_75",    "1:75",    AppDefs.PROJECT_SCALE_K75),
    	new ItemDataVO("K1_100",   "1:100",   AppDefs.PROJECT_SCALE_K100),
    	new ItemDataVO("K1_125",   "1:125",   AppDefs.PROJECT_SCALE_K125),
    	new ItemDataVO("K1_200",   "1:200",   AppDefs.PROJECT_SCALE_K200),
    	new ItemDataVO("K1_250",   "1:250",   AppDefs.PROJECT_SCALE_K250),
    	new ItemDataVO("K1_500",   "1:500",   AppDefs.PROJECT_SCALE_K500),
    	new ItemDataVO("K1_1000",  "1:1000",  AppDefs.PROJECT_SCALE_K1000),
    	new ItemDataVO("K1_1500",  "1:1500",  AppDefs.PROJECT_SCALE_K1500),
    	new ItemDataVO("K1_2000",  "1:2000",  AppDefs.PROJECT_SCALE_K2000),
    	new ItemDataVO("K1_2500",  "1:2500",  AppDefs.PROJECT_SCALE_K2500),
    	new ItemDataVO("K1_5000",  "1:5000",  AppDefs.PROJECT_SCALE_K5000),
    	new ItemDataVO("K1_10000", "1:10000", AppDefs.PROJECT_SCALE_K10000)
    };
    
}

