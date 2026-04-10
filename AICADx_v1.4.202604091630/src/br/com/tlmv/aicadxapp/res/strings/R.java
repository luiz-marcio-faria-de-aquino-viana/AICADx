/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * R.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
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

package br.com.tlmv.aicadxapp.res.strings;

import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;

public abstract class R 
{	
//Public Static
	
	public static final String TIT_MAINFRAME = "TIT_MAINFRAME";
	
	//TAB
	//
	public static final String TAB_PLANVIEW = "TAB_PLANVIEW";
	public static final String TAB_3DVIEW = "TAB_3DVIEW";
	public static final String TAB_ANYVIEW = "TAB_ANYVIEW";
	
	//TITLE
	//
	public static final String TIT_LAYEREXPLORERFRAME = "TIT_LAYEREXPLORERFRAME";
	public static final String TIT_MESSAGEFRAME_INFORMACAO = "TIT_MESSAGEFRAME_INFORMACAO";
	public static final String TIT_MESSAGEFRAME_ERRO = "TIT_MESSAGEFRAME_ERRO";
	public static final String TIT_MESSAGEFRAME_ATENCAO = "TIT_MESSAGEFRAME_ATENCAO";
	public static final String TIT_SEARCHFRAME = "TIT_SEARCHFRAME";
	public static final String TIT_OPENSAVEDATABASEFRAME = "TIT_OPENSAVEDATABASEFRAME";		
	public static final String TIT_OPENSAVEFILEFRAME = "TIT_OPENSAVEFILEFRAME";		
	public static final String TIT_OPENSAVENOSQLFRAME = "TIT_OPENSAVENOSQLFRAME";		
	public static final String TIT_SETUPFRAME = "TIT_SETUPFRAME";
	public static final String TIT_DIMENSIONAREDEDRENAGEMFRAME = "TIT_DIMENSIONAREDEDRENAGEMFRAME";
	public static final String TIT_GERARPLANILHACALCULODRENAGEMFRAME = "TIT_GERARPLANILHACALCULODRENAGEMFRAME";
	public static final String TIT_GERARPLANTASPERFISDRENAGEMFRAME = "TIT_GERARPLANTASPERFISDRENAGEMFRAME";
	public static final String TIT_PROPRIEDADESCAIXAINSPECAOREDEDRENAGEMFRAME = "TIT_PROPRIEDADESCAIXAINSPECAOREDEDRENAGEMFRAME";
	public static final String TIT_PROPRIEDADESPONTOELETRICAFRAME = "TIT_PROPRIEDADESPONTOELETRICAFRAME";
	public static final String TIT_PROPRIEDADESMARGEMFRAME = "TIT_PROPRIEDADESMARGEMFRAME";
	public static final String TIT_SPLASHSCREENFRAME = "TIT_SPLASHSCREENFRAME";
	public static final String TIT_CONTROLEBACKLISTTRANSMARFRAME = "TIT_CONTROLEBACKLISTTRANSMARFRAME";
	public static final String TIT_DEFINICAOQUADROCARGASFRAME = "TIT_DEFINICAOQUADROCARGASFRAME";
	public static final String TIT_GERARQUADROCARGASELETRICAFRAME = "TIT_GERARQUADROCARGASELETRICAFRAME";
	
	//COMMAND_TITLE
	//
	public static final String CMD_TIT_FILE_DXFIN						= "CMD_TIT_FILE_DXFIN";
	public static final String CMD_TIT_FILE_DXFOUT						= "CMD_TIT_FILE_DXFOUT";
	public static final String CMD_TIT_FILE_IFCIN						= "CMD_TIT_FILE_IFCIN";
	public static final String CMD_TIT_FILE_IFCOUT						= "CMD_TIT_FILE_IFCOUT";
	//
	public static final String CMD_TIT_DRAW_OFFSET						= "CMD_TIT_DRAW_OFFSET";
	public static final String CMD_TIT_DRAW_ARC							= "CMD_TIT_DRAW_ARC";
	public static final String CMD_TIT_DRAW_AREA						= "CMD_TIT_DRAW_AREA";
	public static final String CMD_TIT_DRAW_AREA_BY_INSIDEPOINT			= "CMD_TIT_DRAW_AREA_BY_INSIDEPOINT";
	public static final String CMD_TIT_DRAW_LINE						= "CMD_TIT_DRAW_LINE";
	public static final String CMD_TIT_DRAW_POLYGON						= "CMD_TIT_DRAW_POLYGON";
	public static final String CMD_TIT_DRAW_REGULAR_POLYGON				= "CMD_TIT_DRAW_REGULAR_POLYGON";
	public static final String CMD_TIT_DRAW_PIPE						= "CMD_TIT_DRAW_PIPE";
	public static final String CMD_TIT_DRAW_PIPE_LINE					= "CMD_TIT_DRAW_PIPE_LINE";
	//
	public static final String CMD_TIT_DRAW3D_FACE3D					= "CMD_TIT_DRAW3D_FACE3D";
	//
	public static final String CMD_TIT_EDIT_UNDO 						= "CMD_TIT_EDIT_UNDO";
	public static final String CMD_TIT_EDIT_REDO						= "CMD_TIT_EDIT_REDO";
	public static final String CMD_TIT_EDIT_MATCHPROP					= "CMD_TIT_EDIT_MATCHPROP";
	public static final String CMD_TIT_EDIT_ERASE 						= "CMD_TIT_EDIT_ERASE";
	public static final String CMD_TIT_EDIT_UNILINE						= "CMD_TIT_EDIT_UNILINE";
	public static final String CMD_TIT_EDIT_COPY 						= "CMD_TIT_EDIT_COPY";
	public static final String CMD_TIT_EDIT_RECT_ARRAY					= "CMD_TIT_EDIT_RECT_ARRAY";
	public static final String CMD_TIT_EDIT_POLAR_ARRAY					= "CMD_TIT_EDIT_POLAR_ARRAY";
	public static final String CMD_TIT_EDIT_MIRROR 						= "CMD_TIT_EDIT_MIRROR";
	public static final String CMD_TIT_EDIT_MOVE 						= "CMD_TIT_EDIT_MOVE";
	public static final String CMD_TIT_EDIT_SCALE 						= "CMD_TIT_EDIT_SCALE";
	public static final String CMD_TIT_EDIT_SELECT 						= "CMD_TIT_EDIT_SELECT";
	//
	public static final String CMD_TIT_ARQ1_PORTA 						= "CMD_TIT_ARQ1_PORTA";
	public static final String CMD_TIT_ARQ1_PDUPLA 						= "CMD_TIT_ARQ1_PDUPLA";
	//
	public static final String CMD_TIT_ELE1_MATCHPROP 					= "CMD_TIT_ELE1_MATCHPROP";
	public static final String CMD_TIT_ELE1_ADDELETRICALCONDUITS 		= "CMD_TIT_ELE1_ADDELETRICALCONDUITS";
	public static final String CMD_TIT_ELE1_ADDELETRICALCONDUITS3PTS 	= "CMD_TIT_ELE1_ADDELETRICALCONDUITS3PTS";
	public static final String CMD_TIT_ELE1_INSERE_PONTO_ELETRICO_MATRIZ	= "CMD_TIT_ELE1_INSERE_PONTO_ELETRICO_MATRIZ";
	//
	public static final String CMD_TIT_RPD_ADICIONA_ALINHAMENTO_ESTACA	= "CMD_TIT_RPD_ADICIONA_ALINHAMENTO_ESTACA";
	public static final String CMD_TIT_RPD_GERAR_PLANILHA_CALCULO		= "CMD_TIT_RPD_GERAR_PLANILHA_CALCULO";
	public static final String CMD_TIT_RPD_ADICIONA_EIXOS				= "CMD_TIT_RPD_ADICIONA_EIXOS";
	//
	public static final String CMD_TIT_ES1_COLUNA_CD_ESGOTO				= "CMD_TIT_ES1_COLUNA_CD_ESGOTO";
	public static final String CMD_TIT_ES1_COLUNA_TD_ESGOTO				= "CMD_TIT_ES1_COLUNA_TD_ESGOTO";
	public static final String CMD_TIT_ES1_COLUNA_TTD_ESGOTO			= "CMD_TIT_ES1_COLUNA_TTD_ESGOTO";
	public static final String CMD_TIT_ES1_PIPE_ESGOTO					= "CMD_TIT_ES1_PIPE_ESGOTO"; 
	public static final String CMD_TIT_ES1_PIPE_LINE_ESGOTO				= "CMD_TIT_ES1_PIPE_LINE_ESGOTO"; 
	public static final String CMD_TIT_ES1_ADICIONA_RALO_SIFONADO		= "CMD_TIT_ES1_ADICIONA_RALO_SIFONADO";
	//
	public static final String CMD_TIT_ES1_ESGOTO_PRIMARIO	 			= "CMD_TIT_ES1_ESGOTO_PRIMARIO";
	public static final String CMD_TIT_ES1_ESGOTO_SECUNDARIO 			= "CMD_TIT_ES1_ESGOTO_SECUNDARIO";
	public static final String CMD_TIT_ES1_ESGOTO_SECUNDARIO_GORDURA 	= "CMD_TIT_ES1_ESGOTO_SECUNDARIO_GORDURA";
	public static final String CMD_TIT_ES1_ESGOTO_SECUNDARIO_SABAO	 	= "CMD_TIT_ES1_ESGOTO_SECUNDARIO_SABAO";
	public static final String CMD_TIT_ES1_ESGOTO_VENTILACAO		 	= "CMD_TIT_ES1_ESGOTO_VENTILACAO";
	
	//COMMAND_PROMPT
	//
	public static final String CMD_PRT_SELECT_DXF_FILE_TO_OPEN			= "CMD_PRT_SELECT_DXF_FILE_TO_OPEN";
	public static final String CMD_PRT_SELECT_DXF_FILE_TO_SAVE			= "CMD_PRT_SELECT_DXF_FILE_TO_SAVE";
	public static final String CMD_PRT_SELECT_IFC_FILE_TO_OPEN			= "CMD_PRT_SELECT_IFC_FILE_TO_OPEN";
	public static final String CMD_PRT_SELECT_IFC_FILE_TO_SAVE			= "CMD_PRT_SELECT_IFC_FILE_TO_SAVE";
	public static final String CMD_PRT_PRESSENTERTOCONTINUE				= "CMD_PRT_PRESSENTERTOCONTINUE";
	public static final String CMD_PRT_UNDODONE							= "CMD_PRT_UNDODONE";
	public static final String CMD_PRT_CHOICE_SELECTION_TYPE			= "CMD_PRT_CHOICE_SELECTION_TYPE";
	public static final String CMD_PRT_CHOICE_REGULAR_POLYGON_TYPE		= "CMD_PRT_CHOICE_REGULAR_POLYGON_TYPE";
	public static final String CMD_PRT_NUMBER_OF_SELECTED_OBJECTS		= "CMD_PRT_NUMBER_OF_SELECTED_OBJECTS";
	public static final String CMD_PRT_LIST_SELECTED_ENTITIES			= "CMD_PRT_LIST_SELECTED_ENTITIES";
	public static final String CMD_PRT_LIST_DELETED_ENTITIES			= "CMD_PRT_LIST_DELETED_ENTITIES";
	public static final String CMD_PRT_NUMBER_OF_AFECTED_ENTITIES		= "CMD_PRT_NUMBER_OF_AFECTED_ENTITIES";
	public static final String CMD_PRT_NUMBER_OF_DELETED_ENTITIES		= "CMD_PRT_NUMBER_OF_DELETED_ENTITIES";
	public static final String CMD_PRT_BLOCK_LOAD_SUCCESS				= "CMD_PRT_BLOCK_LOAD_SUCCESS";
	public static final String CMD_PRT_BLOCK_LOAD_FAILURE				= "CMD_PRT_BLOCK_LOAD_FAILURE";
	public static final String CMD_PRT_BLOCK_ELEMENTS_LOAD_SUCCESS		= "CMD_PRT_BLOCK_ELEMENTS_LOAD_SUCCESS";
	public static final String CMD_PRT_BLOCK_ELEMENTS_LOAD_FAILURE		= "CMD_PRT_BLOCK_ELEMENTS_LOAD_FAILURE";
	public static final String CMD_PRT_NUMBER_OF_PROCESSED_LINES		= "CMD_PRT_NUMBER_OF_PROCESSED_LINES";
	public static final String CMD_PRT_CHOICE_AREA_TYPE					= "CMD_PRT_CHOICE_AREA_TYPE";
	//
	public static final String CMD_PRT_SELECT_OBJECT					= "CMD_PRT_SELECT_OBJECT";
	public static final String CMD_PRT_SELECT_REFERENCE_OBJECT			= "CMD_PRT_SELECT_REFERENCE_OBJECT";
	public static final String CMD_PRT_SELECT_FIRST_OBJECT				= "CMD_PRT_SELECT_FIRST_OBJECT";
	public static final String CMD_PRT_SELECT_SECOND_OBJECT				= "CMD_PRT_SELECT_SECOND_OBJECT";
	public static final String CMD_PRT_SELECT_BLOCK						= "CMD_PRT_SELECT_BLOCK";
	public static final String CMD_PRT_FIRST_CORNER 					= "CMD_PRT_FIRST_CORNER";
	public static final String CMD_PRT_SECOND_CORNER 					= "CMD_PRT_SECOND_CORNER";
	public static final String CMD_PRT_SECOND_CORNER_OR_ENTER			= "CMD_PRT_SECOND_POINT_OR_ENTER";
	public static final String CMD_PRT_CENTER_POINT	 					= "CMD_PRT_CENTER_POINT";
	public static final String CMD_PRT_FIRST_POINT	 					= "CMD_PRT_FIRST_POINT";
	public static final String CMD_PRT_SECOND_POINT	 					= "CMD_PRT_SECOND_POINT";
	public static final String CMD_PRT_THERD_POINT						= "CMD_PRT_THERD_POINT";
	public static final String CMD_PRT_FOURTH_POINT						= "CMD_PRT_FOURTH_POINT";
	public static final String CMD_PRT_START_POINT						= "CMD_PRT_START_POINT";
	public static final String CMD_PRT_END_POINT						= "CMD_PRT_END_POINT";
	public static final String CMD_PRT_END_POINT_OR_ENTER				= "CMD_PRT_END_POINT_OR_ENTER";
	public static final String CMD_PRT_BASE_POINT	 					= "CMD_PRT_BASE_POINT";
	public static final String CMD_PRT_REFERENCE_POINT					= "CMD_PRT_REFERENCE_POINT";
	public static final String CMD_PRT_INSERT_POINT						= "CMD_PRT_INSERT_POINT";
	public static final String CMD_PRT_ROTATION							= "CMD_PRT_ROTATION";
	public static final String CMD_PRT_SCALE_POINT						= "CMD_PRT_SCALE_POINT";
	public static final String CMD_PRT_NEXT_POINT						= "CMD_PRT_NEXT_POINT";
	public static final String CMD_PRT_OFFSET_DISTANCE					= "CMD_PRT_OFFSET_DISTANCE";
	public static final String CMD_PRT_NUMBER_OF_VERTICES				= "CMD_PRT_NUMBER_OF_VERTICES";
	public static final String CMD_PRT_RADIUS							= "CMD_PRT_RADIUS";
	public static final String CMD_PRT_NUMBER_OF_ELEM 					= "CMD_PRT_NUMBER_OF_ELEM";
	public static final String CMD_PRT_NUMBER_OF_ROWS 					= "CMD_PRT_NUMBER_OF_ROWS";
	public static final String CMD_PRT_NUMBER_OF_COLS 					= "CMD_PRT_NUMBER_OF_COLS";
	public static final String CMD_PRT_DISTANCE_BETWEEN_ROWS 			= "CMD_PRT_DISTANCE_BETWEEN_ROWS";
	public static final String CMD_PRT_DISTANCE_BETWEEN_COLS 			= "CMD_PRT_DISTANCE_BETWEEN_COLS";
	public static final String CMD_PRT_SELECT_FILE_LOAD_METHOD			= "CMD_PRT_SELECT_FILE_LOAD_METHOD";
	public static final String CMD_PRT_SELECT_FILE_LOAD_LAYER			= "CMD_PRT_SELECT_FILE_LOAD_LAYER";
	public static final String CMD_PRT_HEIGHT_FROM_CURRENT_LEVEL		= "CMD_PRT_HEIGHT_FROM_CURRENT_LEVEL";
	public static final String CMD_PRT_HPONTO_FROM_CURRENT_LEVEL		= "CMD_PRT_HPONTO_FROM_CURRENT_LEVEL";
	public static final String CMD_PRT_PIPE_COLUMN_LENGHT				= "CMD_PRT_PIPE_COLUMN_LENGHT";
	public static final String CMD_PRT_DIAMETER_MILI					= "CMD_PRT_DIAMETER_MILI";
	public static final String CMD_PRT_SLOPE_PERC						= "CMD_PRT_SLOPE_PERC";
	public static final String CMD_PRT_PIPE_INLINE						= "CMD_PRT_PIPE_INLINE";
	public static final String CMD_PRT_PIPE_INCURVE90					= "CMD_PRT_PIPE_INCURVE90";
	public static final String CMD_PRT_PIPE_INCURVE45					= "CMD_PRT_PIPE_INCURVE45";
	public static final String CMD_PRT_PIPE_INCURVE						= "CMD_PRT_PIPE_INCURVE";
	public static final String CMD_PRT_INDENTIFICADOR_COLUNA			= "CMD_PRT_INDENTIFICADOR_COLUNA";
	public static final String CMD_PRT_REFERENCE_SIDE					= "CMD_PRT_REFERENCE_SIDE";
	public static final String CMD_PRT_REFERENCE_INSIDEPOINT			= "CMD_PRT_REFERENCE_INSIDEPOINT";
	public static final String CMD_PRT_AREA_NAME						= "CMD_PRT_AREA_NAME";
	//
	public static final String CMD_PRT_SELECT_EQUIPAMENTO_ESGOTO		= "CMD_PRT_SELECT_EQUIPAMENTO_ESGOTO";
	//
	public static final String CMD_PRT_SELECT_PAREDE					= "CMD_PRT_SELECT_PAREDE";
	public static final String CMD_PRT_DOOR_LOCATION					= "CMD_PRT_DOOR_LOCATION";
	public static final String CMD_PRT_DOOR_OPPENINGSIDE				= "CMD_PRT_DOOR_OPPENINGSIDE";
	public static final String CMD_PRT_WINDOW_LOCATION					= "CMD_PRT_WINDOW_LOCATION";
	//
	public static final String CMD_PRT_SELECT_ELECTRICAL_OBJECT			= "CMD_PRT_SELECT_ELECTRICAL_OBJECT";
	public static final String CMD_PRT_COPYTO_ELECTRICAL_OBJECT			= "CMD_PRT_COPYTO_ELECTRICAL_OBJECT";
	public static final String CMD_PRT_SELECT_ELECTRICAL_OBJECT_FROM	= "CMD_PRT_SELECT_ELECTRICAL_OBJECT_FROM";
	public static final String CMD_PRT_SELECT_ELECTRICAL_OBJECT_TO 		= "CMD_PRT_SELECT_ELECTRICAL_OBJECT_TO";
	//
	public static final String CMD_PRT_SELECIONE_CAIXA_INSPECAO_RAIZ	= "CMD_PRT_SELECIONE_CAIXA_INSPECAO_RAIZ";
	public static final String CMD_PRT_SELECIONE_CAIXA_INSPECAO_CHEGADA	= "CMD_PRT_SELECIONE_CAIXA_INSPECAO_CHEGADA";
	public static final String CMD_PRT_NUMERACAO_INICIAL_ESTACA			= "CMD_PRT_NUMERACAO_INICIAL_ESTACA";

	//SELECTION_OPTIONS
	//
	public static final String CMD_OPT_SELECT_NONE						= "CMD_OPT_SELECT_NONE";
	public static final String CMD_OPT_SELECT_ADD 						= "CMD_OPT_SELECT_ADD";
	public static final String CMD_OPT_SELECT_REMOVE 					= "CMD_OPT_SELECT_REMOVE";
	public static final String CMD_OPT_SELECT_OBJECT 					= "CMD_OPT_SELECT_OBJECT";	
	public static final String CMD_OPT_SELECT_FENCE 					= "CMD_OPT_SELECT_FENCE";
	public static final String CMD_OPT_SELECT_WINDOW 					= "CMD_OPT_SELECT_WINDOW";
	public static final String CMD_OPT_SELECT_CROSSING					= "CMD_OPT_SELECT_CROSSING";
	public static final String CMD_OPT_SELECT_FIRST						= "CMD_OPT_SELECT_FIRST";
	public static final String CMD_OPT_SELECT_LAST						= "CMD_OPT_SELECT_LAST";
	public static final String CMD_OPT_SELECT_PREVIOUS					= "CMD_OPT_SELECT_PREVIOUS";
	public static final String CMD_OPT_SELECT_ALL						= "CMD_OPT_SELECT_ALL";

	//MIRROR_OPTIONS
	//
	public static final String CMD_OPT_MIRRORMODE_DUPLICATE_YES			= "CMD_OPT_MIRRORMODE_DUPLICATE_YES_VAL";
	public static final String CMD_OPT_MIRRORMODE_DUPLICATE_NO			= "CMD_OPT_MIRRORMODE_DUPLICATE_NO_VAL";
	
	//REGULAR_POLYGON_OPTIONS
	//
	public static final String CMD_OPT_REGPOLYGON_INTERIOR				= "CMD_OPT_REGPOLYGON_INTERIOR";
	public static final String CMD_OPT_REGPOLYGON_EXTERIOR				= "CMD_OPT_REGPOLYGON_EXTERIOR";
	
	//AREATYPE_OPTIONS
	//
	public static final String CMD_OPT_AREATYPE_ROOM					= "CMD_OPT_AREATYPE_ROOM";
	public static final String CMD_OPT_AREATYPE_APARTMENT				= "CMD_OPT_AREATYPE_APARTMENT";
	public static final String CMD_OPT_AREATYPE_BALCONY					= "CMD_OPT_AREATYPE_BALCONY";
	public static final String CMD_OPT_AREATYPE_BUILDINGCOMMOM			= "CMD_OPT_AREATYPE_BUILDINGCOMMOM";
	public static final String CMD_OPT_AREATYPE_BUILDINGINTERNAL		= "CMD_OPT_AREATYPE_BUILDINGINTERNAL";
	public static final String CMD_OPT_AREATYPE_BUILDINGEXTERNAL		= "CMD_OPT_AREATYPE_BUILDINGEXTERNAL";
	public static final String CMD_OPT_AREATYPE_PARKING					= "CMD_OPT_AREATYPE_PARKING";
	public static final String CMD_OPT_AREATYPE_TERRAIN					= "CMD_OPT_AREATYPE_TERRAIN";
	
	//LABEL
	//
	public static final String LBL_LEVEL = "LBL_LEVEL";
	public static final String LBL_DETAIL_LEVEL = "LBL_DETAIL_LEVEL";
	public static final String LBL_SCALE = "LBL_SCALE";
	public static final String LBL_COMMAND = "LBL_COMMAND";
	public static final String LBL_LAYERLIST = "LBL_LAYERLIST";
	public static final String LBL_DATA = "LBL_DATA";
	public static final String LBL_ASSUNTO = "LBL_ASSUNTO";
	public static final String LBL_MENSAGEM = "LBL_MENSAGEM";
	public static final String LBL_SELECTDATABASE = "LBL_SELECTDATABASE";
	public static final String LBL_ACTIVEDATABASE = "LBL_ACTIVEDATABASE";
	public static final String LBL_SELECTPROJECT = "LBL_SELECTPROJECT";
	public static final String LBL_ACTIVEPROJECT = "LBL_ACTIVEPROJECT";
	public static final String LBL_PROJECTINFORMATION = "LBL_PROJECTINFORMATION";
	public static final String LBL_CODIGOPROJETO = "LBL_CODIGOPROJETO";
	public static final String LBL_NOMEPROJETO = "LBL_NOMEPROJETO";
	public static final String LBL_DESCRICAOPROJETO = "LBL_DESCRICAOPROJETO";
	public static final String LBL_ENDERECOPROJETO = "LBL_ENDERECOPROJETO";
	public static final String LBL_LOGRADOURO = "LBL_LOGRADOURO";
	public static final String LBL_NUMERO = "LBL_NUMERO";
	public static final String LBL_COMPLEMENTO = "LBL_COMPLEMENTO";
	public static final String LBL_BAIRRO = "LBL_BAIRRO";
	public static final String LBL_MUNICIPIO = "LBL_MUNICIPIO";
	public static final String LBL_ESTADO = "LBL_ESTADO";
	public static final String LBL_CEP = "LBL_CEP";
	public static final String LBL_PROJECTREGISTER = "LBL_PROJECTREGISTER";
	public static final String LBL_PROJECTREGISTER_ART = "LBL_PROJECTREGISTER_ART";
	public static final String LBL_PROJECTREGISTER_NOMERESPTECNICO = "LBL_PROJECTREGISTER_NOMERESPTECNICO";
	public static final String LBL_PROJECTREGISTER_REGISTRORESPTECNICO = "LBL_PROJECTREGISTER_REGISTRORESPTECNICO";
	public static final String LBL_PROJECTREGISTER_TELEFONERESPTECNICO = "LBL_PROJECTREGISTER_TELEFONERESPTECNICO";
	public static final String LBL_PROJECTREGISTER_EMAILRESPTECNICO = "LBL_PROJECTREGISTER_EMAILRESPTECNICO";
	public static final String LBL_PARAMETROSDRENAGEM = "LBL_PARAMETROSDRENAGEM";
	public static final String LBL_PARAMETROSDRENAGEM_PLUVIOGRAFO = "LBL_PARAMETROSDRENAGEM_PLUVIOGRAFO";
	public static final String LBL_PARAMETROSDRENAGEM_COEFMANNING = "LBL_PARAMETROSDRENAGEM_COEFMANNING";
	public static final String LBL_PARAMETROSDRENAGEM_PERIODORECORRENCIA = "LBL_PARAMETROSDRENAGEM_PERIODORECORRENCIA";
	public static final String LBL_PARAMETROSIMPRESSAO = "LBL_PARAMETROSIMPRESSAO";
	public static final String LBL_PARAMETROSIMPRESSAO_ESCALA = "LBL_PARAMETROSIMPRESSAO_ESCALA";
	public static final String LBL_PARAMETROSIMPRESSAO_LARGURAPAPEL = "LBL_PARAMETROSIMPRESSAO_LARGURAPAPEL";
	public static final String LBL_PARAMETROSIMPRESSAO_ALTURAPAPEL = "LBL_PARAMETROSIMPRESSAO_ALTURAPAPEL";
	public static final String LBL_PARAMETROSCOORDSYS = "LBL_PARAMETROSCOORDSYS";
	public static final String LBL_PARAMETROSCOORDSYS_ESPGCODE = "LBL_PARAMETROSCOORDSYS_ESPGCODE";
	public static final String LBL_PARAMETROSCOORDSYS_ORIGEM = "LBL_PARAMETROSCOORDSYS_ORIGEM";
	public static final String LBL_PARAMETROSCOORDSYS_DIRECAO_EIXO_X = "LBL_PARAMETROSCOORDSYS_DIRECAO_EIXO_X";
	public static final String LBL_PROJECTLEVELS = "LBL_PROJECTLEVELS";
	public static final String LBL_PROJECTLEVELS_LEVELLIST = "LBL_PROJECTLEVELS_LEVELLIST";
	public static final String LBL_PROJECTLEVELS_LEVELNAME = "LBL_PROJECTLEVELS_LEVELNAME";
	public static final String LBL_PROJECTLEVELS_LEVELTITLE = "LBL_PROJECTLEVELS_LEVELTITLE";
	public static final String LBL_PROJECTLEVELS_LEVELHEIGHT = "LBL_PROJECTLEVELS_LEVELHEIGHT";
	public static final String LBL_SEARCH_OBJECTTYPE = "LBL_SEARCH_OBJECTTYPE";
	public static final String LBL_SEARCH_SEARCHBY = "LBL_SEARCH_SEARCHBY";
	public static final String LBL_SEARCH_RESULTLIST = "LBL_SEARCH_RESULTLIST";
	public static final String LBL_TENSAO_FASE = "LBL_TENSAO_FASE";
	public static final String LBL_BITOLA_MINIMA_CONDUTOR = "LBL_BITOLA_MINIMA_CONDUTOR";
	public static final String LBL_TEMPERATURA = "LBL_TEMPERATURA";
	public static final String LBL_FATOR_REDUCAO = "LBL_FATOR_REDUCAO";
	
	//TEXT
	//
	public static final String TXT_VIEWS = "TXT_VIEWS";
	public static final String TXT_PROCESSING = "TXT_PROCESSING";
	public static final String TXT_PROCESSING_STEP = "TXT_PROCESSING_STEP";
	public static final String TXT_FINISHED = "TXT_FINISHED";
	//
	public static final String TXT_SHOW_ALL_ENABLED_FEATURES = "TXT_SHOW_ALL_ENABLED_FEATURES";
	public static final String TXT_SHOW_ALL_LOADED_MODULES = "TXT_SHOW_ALL_LOADED_MODULES";
	public static final String TXT_SHOW_ALL_DATAENTRY = "TXT_SHOW_ALL_DATAENTRY";
	public static final String TXT_SHOW_ALL_DATAENTRY_BY_SECTION_TYPE = "TXT_SHOW_ALL_DATAENTRY_BY_SECTION_TYPE";
	public static final String TXT_SHOW_SELECTED_DATAENTRY = "TXT_SHOW_SELECTED_DATAENTRY";
	public static final String TXT_NUMBER_OF_LOADED_ELEMENTS = "TXT_NUMBER_OF_LOADED_ELEMENTS";
	public static final String TXT_NUMBER_OF_PROCESSED_ELEMENTS = "TXT_NUMBER_OF_PROCESSED_ELEMENTS";
	public static final String TXT_NONE_ELEMENTS_PROCESSED = "TXT_NONE_ELEMENTS_PROCESSED";
	//
	public static final String TXT_ES1_COLUNA_ESGOTO_PRIMARIO	 		= "TXT_ES1_COLUNA_ESGOTO_PRIMARIO";
	public static final String TXT_ES1_COLUNA_ESGOTO_SECUNDARIO 		= "TXT_ES1_COLUNA_ESGOTO_SECUNDARIO";
	public static final String TXT_ES1_COLUNA_ESGOTO_SECUNDARIO_GORDURA = "TXT_ES1_COLUNA_ESGOTO_SECUNDARIO_GORDURA";
	public static final String TXT_ES1_COLUNA_ESGOTO_SECUNDARIO_SABAO	= "TXT_ES1_COLUNA_ESGOTO_SECUNDARIO_SABAO";
	public static final String TXT_ES1_COLUNA_ESGOTO_VENTILACAO		 	= "TXT_ES1_COLUNA_ESGOTO_VENTILACAO";
	
	//BUTTON
	//
	public static final String BTN_FECHAR = "BTN_FECHAR";
	public static final String BTN_NOVO = "BTN_NOVO";
	public static final String BTN_ABRIR = "BTN_ABRIR";
	public static final String BTN_GRAVAR = "BTN_GRAVAR";
	public static final String BTN_GRAVAR_COMO = "BTN_GRAVAR_COMO";
	public static final String BTN_APAGAR = "BTN_APAGAR";
	public static final String BTN_RENOMEAR = "BTN_RENOMEAR";
	public static final String BTN_COPIAR = "BTN_COPIAR";
	public static final String BTN_ORIGEM = "BTN_ORIGEM";
	public static final String BTN_CANCELAR = "BTN_CANCELAR";
	public static final String BTN_OK = "BTN_OK";
	public static final String BTN_ZOOMTOITEM = "BTN_ZOOMTOITEM";
	public static final String BTN_ZOOMTOALL = "BTN_ZOOMTOALL";
	public static final String BTN_SEARCH = "BTN_SEARCH";
	public static final String BTN_ADDLEVEL = "BTN_ADDLEVEL";
	public static final String BTN_REMOVELEVEL = "BTN_REMOVELEVEL";
	public static final String BTN_REMOVEALLLEVELS = "BTN_REMOVEALLLEVELS";
	
	//ERROR
	//
	public static final String ERR_PROCESSING_FAILURE = "ERR_PROCESSING_FAILURE";
	public static final String ERR_COMANDO_INVALIDO_NAO_IMPLEMENTADO = "ERR_COMANDO_INVALIDO_NAO_IMPLEMENTADO";
	public static final String ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS = "ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS";
	public static final String ERR_CAMPOS_INVALIDOS = "ERR_CAMPOS_INVALIDOS";
	public static final String ERR_EXISTEM_COMANDOS_ATIVOS = "ERR_EXISTEM_COMANDOS_ATIVOS";
	public static final String ERR_INVALID_FILE_TYPE = "ERR_INVALID_FILE_TYPE"; 
	//
	public static final String ERR_VALOR_COEFMANNING_DEVE_SER_SUPERIOR = "ERR_VALOR_COEFMANNING_DEVE_SER_SUPERIOR";
	public static final String ERR_PERIODO_RECORRENCIA_DEVE_SER_SUPERIOR = "ERR_PERIODO_RECORRENCIA_DEVE_SER_SUPERIOR";
	public static final String ERR_VALOR_ESCALA_DEVE_SER_SUPERIOR = "ERR_VALOR_ESCALA_DEVE_SER_SUPERIOR";
	public static final String ERR_VALOR_LARGURA_PAPEL_DEVE_SER_SUPERIOR = "ERR_VALOR_LARGURA_PAPEL_DEVE_SER_SUPERIOR";
	public static final String ERR_VALOR_ALTURA_PAPEL_DEVE_SER_SUPERIOR = "ERR_VALOR_ALTURA_PAPEL_DEVE_SER_SUPERIOR";
	public static final String ERR_NUMBER_OF_ELEM_HIGHT_THAN_ZERO = "ERR_NUMBER_OF_ELEM_HIGHT_THAN_ZERO";
	public static final String ERR_NUMBER_OF_ROWS_HIGHT_THAN_ZERO = "ERR_NUMBER_OF_ROWS_HIGHT_THAN_ZERO";
	public static final String ERR_NUMBER_OF_COLS_HIGHT_THAN_ZERO = "ERR_NUMBER_OF_COLS_HIGHT_THAN_ZERO";
	public static final String ERR_DISTANCE_BETWEEN_ROWS = "ERR_DISTANCE_BETWEEN_ROWS";
	public static final String ERR_DISTANCE_BETWEEN_COLS = "ERR_DISTANCE_BETWEEN_COLS";
	public static final String ERR_EXIST_BLOCK_WITH_THE_SAME_NAME_IN_MEMORY = "ERR_EXIST_BLOCK_WITH_THE_SAME_NAME_IN_MEMORY";
	public static final String ERR_CANT_CREATE_AREA_POLYGON = "ERR_CANT_CREATE_AREA_POLYGON";

	//ERROR: FIELDS
	//
	public static final String ERR_DATABASE = "ERR_DATABASE_NAME";
	public static final String ERR_PROJECT = "ERR_PROJECT_NAME";
	public static final String ERR_CODIGOPROJETO = "ERR_CODIGOPROJETO";			
	public static final String ERR_NOMEPROJETO = "ERR_NOMEPROJETO";
	public static final String ERR_DESCRICAOPROJETO = "ERR_DESCRICAOPROJETO";
	public static final String ERR_LOGRADOURO = "ERR_LOGRADOURO";			
	public static final String ERR_NUMERO = "ERR_NUMERO";
	public static final String ERR_COMPLEMENTO = "ERR_COMPLEMENTO";			
	public static final String ERR_BAIRRO = "ERR_BAIRRO";			
	public static final String ERR_MUNICIPIO = "ERR_MUNICIPIO";			
	public static final String ERR_ESTADO = "ERR_ESTADO";			
	public static final String ERR_CEP = "ERR_CEP";			
	public static final String ERR_ART = "ERR_ART";			
	public static final String ERR_RESPTECNICO = "ERR_RESPTECNICO";			
	public static final String ERR_REGISTRO = "ERR_REGISTRO";			
	public static final String ERR_TELEFONE = "ERR_TELEFONE";			
	public static final String ERR_EMAIL = "E-ERR_EMAIL";			
	public static final String ERR_PLUVIOGRAFO = "ERR_PLUVIOGRAFO";			
	public static final String ERR_COEFMANNING = "ERR_COEFMANNING";			
	public static final String ERR_PERIODORECORRENCIA = "ERR_PERIODORECORRENCIA";			
	public static final String ERR_ESCALA = "ERR_ESCALA";			
	public static final String ERR_LARGURAPAPEL = "ERR_LARGURAPAPEL";			
	public static final String ERR_ALTURAPAPEL = "ERR_ALTURAPAPEL";			
	public static final String ERR_ESPG = "ERR_ESPG";			
	
//Public
	
	/* MONTHS_FULLNAME
	 */
	public String[] LS_MONTHS_FULLNAME;

	/* MONTHS_ABREVIATION
	 */
	public String[] LS_MONTHS_ABREV;
		
	/* LAYER_EXPLORER_HEADERS
	*/
	public String[] LS_TBLLAYEREXPLORER;	
	
	/* OBJECT_PROPERTY_HEADERS
	*/
	public String[] LS_TBLPROPERTYEDITOR;
	
	/* RESULT_LIST_HEADERS
	*/
	public String[] LS_TBLRESULTLIST;
	
	/* Methodes */
	
	public abstract String getString(String name);
	
	public abstract String getMonthFullName(int month);
	
	public abstract String getMonthAbrev(int month);
	
	public abstract String getTblLayerExplorer(int col);
	
	public abstract String getTblPropertyEditor(int col);
	
	public abstract String getTblResultList(int col);

	/* GROUP_ITEM_DATA */
	
	public abstract GroupItemDataVO getGroupItemData(int groupItemDataId);
	
	public abstract int getSzGroupItemData();
		
}
