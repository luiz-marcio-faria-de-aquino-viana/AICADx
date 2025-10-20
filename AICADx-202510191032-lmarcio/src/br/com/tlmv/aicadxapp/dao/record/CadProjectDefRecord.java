/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadProjectDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;

//
//CREATE TABLE #SCHEMA_NAME#.cad_project_def (
//	oid numeric(10,0) NOT NULL,
//	object_id numeric(10,0) NOT NULL,
//	obj_type numeric(10,0) NOT NULL,
//	obj_type_str character varying(256) NOT NULL,
//	reference character varying(256) NOT NULL,
//	ptorigem_x numeric(20,6) NOT NULL,
//	ptorigem_y numeric(20,6) NOT NULL,
//	ptorigem_z numeric(20,6) NOT NULL,
//	xdir_xi numeric(20,6) NOT NULL,
//	xdir_yi numeric(20,6) NOT NULL,
//	xdir_zi numeric(20,6) NOT NULL,
//	xdir_xf numeric(20,6) NOT NULL,
//	xdir_yf numeric(20,6) NOT NULL,
//	xdir_zf numeric(20,6) NOT NULL,
//	is_entity_object character(1) NOT NULL,
//	is_deleted character(1) NOT NULL
//	
//	--
//	-- PROJECT_ATTRIB
//	--
//	codigo_projeto character varying(30),
//	titulo_projeto character varying(256),
//	descricao_projeto character varying(4096),
//	logradouro character varying(80),
//	numero character varying(30),
//	complemento character varying(80),
//	bairro character varying(80),
//	municipio character varying(80),
//	estado character varying(80),
//	cep character varying(10),
//	art character varying(30),
//	nome_responsavel_tecnico character varying(80),
//	registro_responsavel_tecnico character varying(30),
//	telefone_responsavel_tecnico character varying(30),
//	email_responsavel_tecnico character varying(256),
//	espg_code character varying(30),
//	codigo_pluviografo numeric(20),					-- codigo local medicao volume chuva
//	pluviografo character varying(256),				-- nome local medicao volume chuva
//	coef_manning numeric(20,6),
//	periodo_recorrencia numeric(20,6),
//	escala numeric(20,6),
//	papel_largura numeric(20,6),
//	papel_altura numeric(20,6),
//	unidade numeric(20,6),
//	scale_factor numeric(20,6),
//	area_largura numeric(20,6),
//	area_altura numeric(20,6),
//);
//
public class CadProjectDefRecord extends BaseEntityRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_project_def ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_project_def ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "reference character varying(256) NOT NULL, " +
		    "ptorigem_x numeric(20,6) NOT NULL, " +
		    "ptorigem_y numeric(20,6) NOT NULL, " +
		    "ptorigem_z numeric(20,6) NOT NULL, " +
		    "xdir_xi numeric(20,6) NOT NULL, " +
		    "xdir_yi numeric(20,6) NOT NULL, " +
		    "xdir_zi numeric(20,6) NOT NULL, " +
		    "xdir_xf numeric(20,6) NOT NULL, " +
		    "xdir_yf numeric(20,6) NOT NULL, " +
		    "xdir_zf numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL " +
		    "codigo_projeto character varying(30), " +
		    "titulo_projeto character varying(256), " +
		    "descricao_projeto character varying(4096), " +
		    "logradouro character varying(80), " +
		    "numero character varying(30), " +
		    "complemento character varying(80), " +
		    "bairro character varying(80), " +
		    "municipio character varying(80), " +
		    "estado character varying(80), " +
		    "cep character varying(10), " +
		    "art character varying(30), " +
		    "nome_responsavel_tecnico character varying(80), " +
		    "registro_responsavel_tecnico character varying(30), " +
		    "telefone_responsavel_tecnico character varying(30), " +
		    "email_responsavel_tecnico character varying(256), " +
		    "espg_code character varying(30), " +
		    "codigo_pluviografo numeric(20), " +
		    "pluviografo character varying(256), " +
		    "coef_manning numeric(20,6), " +
		    "periodo_recorrencia numeric(20,6), " +
		    "escala numeric(20,6), " +
		    "papel_largura numeric(20,6), " +
		    "papel_altura numeric(20,6), " +
		    "unidade numeric(20,6), " +
		    "scale_factor numeric(20,6), " +
		    "area_largura numeric(20,6), " +
		    "area_altura numeric(20,6) )";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptorigem_x, " +
		    "ptorigem_y, " +
		    "ptorigem_z, " +
		    "xdir_xi, " +
		    "xdir_yi, " +
		    "xdir_zi, " +
		    "xdir_xf, " +
		    "xdir_yf, " +
		    "xdir_zf, " +
		    "codigo_projeto, " +
		    "titulo_projeto, " +
		    "descricao_projeto, " +
		    "logradouro, " +
		    "numero, " +
		    "complemento, " +
		    "bairro, " +
		    "municipio, " +
		    "estado, " +
		    "cep, " +
		    "art, " +
		    "nome_responsavel_tecnico, " +
		    "registro_responsavel_tecnico, " +
		    "telefone_responsavel_tecnico, " +
		    "email_responsavel_tecnico, " +
		    "espg_code, " +
		    "codigo_pluviografo, " +
		    "pluviografo, " +
		    "coef_manning, " +
		    "periodo_recorrencia, " +
		    "escala, " +
		    "papel_largura, " +
		    "papel_altura, " +
		    "unidade, " +
		    "scale_factor, " +
		    "area_largura, " +
		    "area_altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_project_def " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptorigem_x, " +
		    "ptorigem_y, " +
		    "ptorigem_z, " +
		    "xdir_xi, " +
		    "xdir_yi, " +
		    "xdir_zi, " +
		    "xdir_xf, " +
		    "xdir_yf, " +
		    "xdir_zf, " +
		    "codigo_projeto, " +
		    "titulo_projeto, " +
		    "descricao_projeto, " +
		    "logradouro, " +
		    "numero, " +
		    "complemento, " +
		    "bairro, " +
		    "municipio, " +
		    "estado, " +
		    "cep, " +
		    "art, " +
		    "nome_responsavel_tecnico, " +
		    "registro_responsavel_tecnico, " +
		    "telefone_responsavel_tecnico, " +
		    "email_responsavel_tecnico, " +
		    "espg_code, " +
		    "codigo_pluviografo, " +
		    "pluviografo, " +
		    "coef_manning, " +
		    "periodo_recorrencia, " +
		    "escala, " +
		    "papel_largura, " +
		    "papel_altura, " +
		    "unidade, " +
		    "scale_factor, " +
		    "area_largura, " +
		    "area_altura, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_project_def " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_project_def( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "reference, " +
		    "ptorigem_x, " +
		    "ptorigem_y, " +
		    "ptorigem_z, " +
		    "xdir_xi, " +
		    "xdir_yi, " +
		    "xdir_zi, " +
		    "xdir_xf, " +
		    "xdir_yf, " +
		    "xdir_zf, " +
		    "codigo_projeto, " +
		    "titulo_projeto, " +
		    "descricao_projeto, " +
		    "logradouro, " +
		    "numero, " +
		    "complemento, " +
		    "bairro, " +
		    "municipio, " +
		    "estado, " +
		    "cep, " +
		    "art, " +
		    "nome_responsavel_tecnico, " +
		    "registro_responsavel_tecnico, " +
		    "telefone_responsavel_tecnico, " +
		    "email_responsavel_tecnico, " +
		    "espg_code, " +
		    "codigo_pluviografo, " +
		    "pluviografo, " +
		    "coef_manning, " +
		    "periodo_recorrencia, " +
		    "escala, " +
		    "papel_largura, " +
		    "papel_altura, " +
		    "unidade, " +
		    "scale_factor, " +
		    "area_largura, " +
		    "area_altura, " +
		    "is_entity_object, " +
		    "is_deleted) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_project_def set " +
		    "reference = ?, " +
		    "ptorigem_x = ?, " +
		    "ptorigem_y = ?, " +
		    "ptorigem_z = ?, " +
		    "xdir_xi = ?, " +
		    "xdir_yi = ?, " +
		    "xdir_zi = ?, " +
		    "xdir_xf = ?, " +
		    "xdir_yf = ?, " +
		    "xdir_zf = ?, " +
		    "codigo_projeto = ?, " +
		    "titulo_projeto = ?, " +
		    "descricao_projeto = ?, " +
		    "logradouro = ?, " +
		    "numero = ?, " +
		    "complemento = ?, " +
		    "bairro = ?, " +
		    "municipio = ?, " +
		    "estado = ?, " +
		    "cep = ?, " +
		    "art = ?, " +
		    "nome_responsavel_tecnico = ?, " +
		    "registro_responsavel_tecnico = ?, " +
		    "telefone_responsavel_tecnico = ?, " +
		    "email_responsavel_tecnico = ?, " +
		    "espg_code = ?, " +
		    "codigo_pluviografo = ?, " +
		    "pluviografo = ?, " +
		    "coef_manning = ?, " +
		    "periodo_recorrencia = ?, " +
		    "escala = ?, " +
		    "papel_largura = ?, " +
		    "papel_altura = ?, " +
		    "unidade = ?, " +
		    "scale_factor = ?, " +
		    "area_largura = ?, " +
		    "area_altura = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_project_def') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_project_def') ";
			
//Private
	private double ptOrigemX;
	private double ptOrigemY;
	private double ptOrigemZ;
	//
	private double xDirX;
	private double xDirY;
	private double xDirZ;

	//PROJECT_ATTR
	//
	private String codigoProjeto;
	private String tituloProjeto;
	private String descricaoProjeto;
	//
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;
	//
	private String art;
	private String nomeResponsavelTecnico;
	private String registroResponsavelTecnico;
	private String telefoneResponsavelTecnico;
	private String emailResponsavelTecnico;
	//
	private String espgCode;
	//
	private int codigoPluviografo;							// codigo local medicao volume chuva
	private String pluviografo;								// nome local medicao volume chuva
	private double coefManning;
	private double periodoRecorrencia;
	//
	private double escala;
	private double papelLargura;
	private double papelAltura;	
	//
	private double unidade; 
	private double scaleFactor;
	private double areaLargura;
	private double areaAltura;	
	
//Public
    
    public CadProjectDefRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		//
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		
    		//PROJECT_ATTR
    		//
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String reference,
		//
		double ptOrigemX,
		double ptOrigemY,
		double ptOrigemZ,
		//
		double xDirX,
		double xDirY,
		double xDirZ,

		//PROJECT_ATTR
		//
		String codigoProjeto,
		String tituloProjeto,
		String descricaoProjeto,
		//
		String logradouro,
		String numero,
		String complemento,
		String bairro,
		String municipio,
		String estado,
		String cep,
		//
		String art,
		String nomeResponsavelTecnico,
		String registroResponsavelTecnico,
		String telefoneResponsavelTecnico,
		String emailResponsavelTecnico,
		//
		String espgCode,
		//
		int codigoPluviografo,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		//
		double escala,
		double papelLargura,
		double papelAltura,
		//
		double unidade, 
		double scaleFactor,
		double areaLargura,
		double areaAltura,
		//
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, reference, isDeleted);
		//
    	this.ptOrigemX = ptOrigemX;
    	this.ptOrigemY = ptOrigemY;
    	this.ptOrigemZ = ptOrigemZ;
		//
    	this.xDirX = xDirX;
    	this.xDirY = xDirY;
    	this.xDirZ = xDirZ;

		this.codigoProjeto = codigoProjeto;
		this.tituloProjeto = tituloProjeto;
		this.descricaoProjeto = descricaoProjeto;
		//
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.estado = estado;
		this.cep = cep;
		//
		this.art = art;
		//
		this.nomeResponsavelTecnico = nomeResponsavelTecnico;
		this.registroResponsavelTecnico = registroResponsavelTecnico;
		this.telefoneResponsavelTecnico = telefoneResponsavelTecnico;
		this.emailResponsavelTecnico = emailResponsavelTecnico;
		//
		this.codigoPluviografo = codigoPluviografo;
		this.pluviografo = pluviografo;
		this.coefManning = coefManning;
		this.periodoRecorrencia = periodoRecorrencia;
		//
		this.escala = escala;
		this.papelLargura = papelLargura;
		this.papelAltura = papelAltura;
		//
		this.espgCode = espgCode;
		//
		this.unidade = AppDefs.DEF_DEFAULT_PROJECT_UNIT;
		this.scaleFactor = this.escala / this.unidade;
		this.areaLargura = this.papelLargura * this.scaleFactor;
		this.areaAltura = this.papelAltura * this.scaleFactor; 

    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}

    /* Getters/Setters */

	public double getPtOrigemX() {
		return ptOrigemX;
	}

	public void setPtOrigemX(double ptOrigemX) {
		this.ptOrigemX = ptOrigemX;
	}

	public double getPtOrigemY() {
		return ptOrigemY;
	}

	public void setPtOrigemY(double ptOrigemY) {
		this.ptOrigemY = ptOrigemY;
	}

	public double getPtOrigemZ() {
		return ptOrigemZ;
	}

	public void setPtOrigemZ(double ptOrigemZ) {
		this.ptOrigemZ = ptOrigemZ;
	}

	public double getxDirX() {
		return xDirX;
	}

	public void setxDirX(double xDirX) {
		this.xDirX = xDirX;
	}

	public double getxDirY() {
		return xDirY;
	}

	public void setxDirY(double xDirY) {
		this.xDirY = xDirY;
	}

	public double getxDirZ() {
		return xDirZ;
	}

	public void setxDirZ(double xDirZ) {
		this.xDirZ = xDirZ;
	}

	public String getCodigoProjeto() {
		return codigoProjeto;
	}

	public void setCodigoProjeto(String codigoProjeto) {
		this.codigoProjeto = codigoProjeto;
	}

	public String getTituloProjeto() {
		return tituloProjeto;
	}

	public void setTituloProjeto(String tituloProjeto) {
		this.tituloProjeto = tituloProjeto;
	}

	public String getDescricaoProjeto() {
		return descricaoProjeto;
	}

	public void setDescricaoProjeto(String descricaoProjeto) {
		this.descricaoProjeto = descricaoProjeto;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getNomeResponsavelTecnico() {
		return nomeResponsavelTecnico;
	}

	public void setNomeResponsavelTecnico(String nomeResponsavelTecnico) {
		this.nomeResponsavelTecnico = nomeResponsavelTecnico;
	}

	public String getRegistroResponsavelTecnico() {
		return registroResponsavelTecnico;
	}

	public void setRegistroResponsavelTecnico(String registroResponsavelTecnico) {
		this.registroResponsavelTecnico = registroResponsavelTecnico;
	}

	public String getTelefoneResponsavelTecnico() {
		return telefoneResponsavelTecnico;
	}

	public void setTelefoneResponsavelTecnico(String telefoneResponsavelTecnico) {
		this.telefoneResponsavelTecnico = telefoneResponsavelTecnico;
	}

	public String getEmailResponsavelTecnico() {
		return emailResponsavelTecnico;
	}

	public void setEmailResponsavelTecnico(String emailResponsavelTecnico) {
		this.emailResponsavelTecnico = emailResponsavelTecnico;
	}

	public String getEspgCode() {
		return espgCode;
	}

	public void setEspgCode(String espgCode) {
		this.espgCode = espgCode;
	}

	public int getCodigoPluviografo() {
		return codigoPluviografo;
	}

	public void setCodigoPluviografo(int codigoPluviografo) {
		this.codigoPluviografo = codigoPluviografo;
	}

	public String getPluviografo() {
		return pluviografo;
	}

	public void setPluviografo(String pluviografo) {
		this.pluviografo = pluviografo;
	}

	public double getCoefManning() {
		return coefManning;
	}

	public void setCoefManning(double coefManning) {
		this.coefManning = coefManning;
	}

	public double getPeriodoRecorrencia() {
		return periodoRecorrencia;
	}

	public void setPeriodoRecorrencia(double periodoRecorrencia) {
		this.periodoRecorrencia = periodoRecorrencia;
	}

	public double getEscala() {
		return escala;
	}

	public void setEscala(double escala) {
		this.escala = escala;
	}

	public double getPapelLargura() {
		return papelLargura;
	}

	public void setPapelLargura(double papelLargura) {
		this.papelLargura = papelLargura;
	}

	public double getPapelAltura() {
		return papelAltura;
	}

	public void setPapelAltura(double papelAltura) {
		this.papelAltura = papelAltura;
	}

	public double getUnidade() {
		return unidade;
	}

	public void setUnidade(double unidade) {
		this.unidade = unidade;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public double getAreaLargura() {
		return areaLargura;
	}

	public void setAreaLargura(double areaLargura) {
		this.areaLargura = areaLargura;
	}

	public double getAreaAltura() {
		return areaAltura;
	}

	public void setAreaAltura(double areaAltura) {
		this.areaAltura = areaAltura;
	}
    
}
