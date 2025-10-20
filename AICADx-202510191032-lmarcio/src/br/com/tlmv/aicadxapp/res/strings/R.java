/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.res.strings;

import br.com.tlmv.aicadxapp.AppDefs;
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
	public static final String TIT_SETUPFRAME = "TIT_SETUPFRAME";
	public static final String TIT_DIMENSIONAREDEDRENAGEMFRAME = "TIT_DIMENSIONAREDEDRENAGEMFRAME";
	public static final String TIT_GERARPLANILHACALCULODRENAGEMFRAME = "TIT_GERARPLANILHACALCULODRENAGEMFRAME";
	public static final String TIT_GERARPLANTASPERFISDRENAGEMFRAME = "TIT_GERARPLANTASPERFISDRENAGEMFRAME";
	public static final String TIT_PROPRIEDADESCAIXAINSPECAOREDEDRENAGEMFRAME = "TIT_PROPRIEDADESCAIXAINSPECAOREDEDRENAGEMFRAME";
	public static final String TIT_SPLASHSCREENFRAME = "TIT_SPLASHSCREENFRAME";
	public static final String TIT_CONTROLEBACKLISTTRANSMARFRAME = "TIT_CONTROLEBACKLISTTRANSMARFRAME";
	
	//LABEL
	//
	public static final String LBL_DETAIL_LEVEL = "LBL_DETAIL_LEVEL";
	public static final String LBL_SCALE = "LBL_SCALE";
	public static final String LBL_COMMAND = "LBL_COMMAND";
	public static final String LBL_LAYERLIST = "LBL_LAYERLIST";
	public static final String LBL_DATA = "LBL_DATA";
	public static final String LBL_ASSUNTO = "LBL_ASSUNTO";
	public static final String LBL_MENSAGEM = "LBL_MENSAGEM";
	public static final String LBL_SELECTDATABASE = "LBL_SELECTDATABASE";
	public static final String LBL_ACTIVEDATABASE = "LBL_ACTIVEDATABASE";
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
	public static final String LBL_SEARCH_OBJECTTYPE = "LBL_SEARCH_OBJECTTYPE";
	public static final String LBL_SEARCH_SEARCHBY = "LBL_SEARCH_SEARCHBY";
	public static final String LBL_SEARCH_RESULTLIST = "LBL_SEARCH_RESULTLIST";
	
	//TEXT
	//
	public static final String TXT_VIEWS = "TXT_VIEWS";
	
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
	
	//ERROR
	//
	public static final String ERR_PROCESSING_FAILURE = "ERR_PROCESSING_FAILURE";
	public static final String ERR_COMANDO_INVALIDO_NAO_IMPLEMENTADO = "ERR_COMANDO_INVALIDO_NAO_IMPLEMENTADO";
	public static final String ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS = "ERR_CAMPOS_OBRIGATORIOS_NAO_INFORMADOS";
	public static final String ERR_CAMPOS_INVALIDOS = "ERR_CAMPOS_INVALIDOS";
	//
	public static final String ERR_VALOR_COEFMANNING_DEVE_SER_SUPERIOR = "ERR_VALOR_COEFMANNING_DEVE_SER_SUPERIOR";
	public static final String ERR_PERIODO_RECORRENCIA_DEVE_SER_SUPERIOR = "ERR_PERIODO_RECORRENCIA_DEVE_SER_SUPERIOR";
	public static final String ERR_VALOR_ESCALA_DEVE_SER_SUPERIOR = "ERR_VALOR_ESCALA_DEVE_SER_SUPERIOR";
	public static final String ERR_VALOR_LARGURA_PAPEL_DEVE_SER_SUPERIOR = "ERR_VALOR_LARGURA_PAPEL_DEVE_SER_SUPERIOR";
	public static final String ERR_VALOR_ALTURA_PAPEL_DEVE_SER_SUPERIOR = "ERR_VALOR_altura_PAPEL_DEVE_SER_SUPERIOR";

	//ERROR: FIELDS
	//
	public static final String ERR_DATABASE = "ERR_DATABASE_NAME";
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
