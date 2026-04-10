/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadProjectDefRecord extends BaseEntityRecord
{
//Public

	/* SQL */

	@Override
	public String getSqlTableName() {
		return sqlTableName;
	}
	
	@Override
	public SqlColumnVO[] getSqlColumn() {
		return sqlColumn;
	}

//Public Static
	public static final String sqlTableName = "cad_project_def";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptorigem_x",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptorigem_y",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptorigem_z",						AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("xdir_xi",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("xdir_yi",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("xdir_zi",							AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("xdir_xf",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("xdir_yf",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("xdir_zf",							AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("codigo_projeto",					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("titulo_projeto",					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("descricao_projeto",				AppDefs.TAG_SQLTYPE_BIGSTR),
	    //
	    new SqlColumnVO("logradouro",						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("numero",							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("complemento",						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("bairro",							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("municipio",						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("estado",							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("cep",								AppDefs.TAG_SQLTYPE_STR),
	    //
	    new SqlColumnVO("art",								AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("nome_responsavel_tecnico",			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("registro_responsavel_tecnico",		AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("telefone_responsavel_tecnico",		AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("email_responsavel_tecnico",		AppDefs.TAG_SQLTYPE_STR),
	    //
	    new SqlColumnVO("espg_code",						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("codigo_pluviografo",				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("pluviografo",						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("coef_manning",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("periodo_recorrencia",				AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("escala",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("papel_largura",					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("papel_altura",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("unidade",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("scale_factor",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_largura",						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_altura",						AppDefs.TAG_SQLTYPE_DBL)
		
	};
		
//Private
	private double ptOrigemX;
	private double ptOrigemY;
	private double ptOrigemZ;
	//
	private double xDirXI;
	private double xDirYI;
	private double xDirZI;
	//
	private double xDirXF;
	private double xDirYF;
	private double xDirZF;

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
	private int codigoPluviografo;							// codigo local medicao volume chuva
	private String pluviografo;								// nome local medicao volume chuva
	private double coefManning;
	private double periodoRecorrencia;
	//
	private double escala;
	private double papelLargura;
	private double papelAltura;	
	private double unidade; 
	private double scaleFactor;
	private double areaLargura;
	private double areaAltura;	
	
//Public
    
    public CadProjectDefRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		//
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR, 
    		//
    		AppDefs.NULL_INTSTR, 
    		//
    		AppDefs.DEF_VALUES_NAO,
    		AppDefs.DEF_VALUES_NAO,
    		//
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_DBL,
    		//
    		AppDefs.NULL_DBL,
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
    		AppDefs.NULL_DBL );
    }

    public CadProjectDefRecord(CadProjectDef o)
    {
    	this.init(o);
    }
    
    public CadProjectDefRecord(ResultSet rs)
    {
		DbUtil o = new DbUtil(rs);
		
    	this.init(o);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		//
		int objectId,
		int objType,
		String objTypeStr,
		String objVer,
		//
		String cadRefEntityId,
		//
	    String strIsDeleted,
	    String strIsLocked,
		//
		String reference,
		String levelName,
		//
		double zLevel,
		//
		double ptOrigemX,
		double ptOrigemY,
		double ptOrigemZ,
		//
		double xDirXI,
		double xDirYI,
		double xDirZI,
		//
		double xDirXF,
		double xDirYF,
		double xDirZF,

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
		int codigoPluviografo,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		//
		double escala,
		double papelLargura,
		double papelAltura,
		double unidade, 
		double scaleFactor,
		double areaLargura,
		double areaAltura )
    {
    	super.initEntity(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		strIsDeleted,
    		strIsLocked,
    		//
    		reference, 
    		levelName,
    		//
    		zLevel );

    	this.ptOrigemX = ptOrigemX;
    	this.ptOrigemY = ptOrigemY;
    	this.ptOrigemZ = ptOrigemZ;
		//
    	this.xDirXI = xDirXI;
    	this.xDirYI = xDirYI;
    	this.xDirZI = xDirZI;
		//
    	this.xDirXF = xDirXF;
    	this.xDirYF = xDirYF;
    	this.xDirZF = xDirZF;

		//PROJECT_ATTR
		//
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
		this.nomeResponsavelTecnico = nomeResponsavelTecnico;
		this.registroResponsavelTecnico = registroResponsavelTecnico;
		this.telefoneResponsavelTecnico = telefoneResponsavelTecnico;
		this.emailResponsavelTecnico = emailResponsavelTecnico;
		//
		this.espgCode = espgCode;
		this.codigoPluviografo = codigoPluviografo;
		this.pluviografo = pluviografo;
		this.coefManning = coefManning;
		this.periodoRecorrencia = periodoRecorrencia;
		//
		this.escala = escala;
		this.papelLargura = papelLargura;
		this.papelAltura = papelAltura;
		this.unidade = AppDefs.DEF_DEFAULT_PROJECT_UNIT;
		this.scaleFactor = this.escala / this.unidade;
		this.areaLargura = this.papelLargura * this.scaleFactor;
		this.areaAltura = this.papelAltura * this.scaleFactor; 

    }
    
    public void init(CadProjectDef o)
    {
    	//LAYER
    	//
    	CadLayerDef oLayer = o.getLayer();
    	String strReference = oLayer.getReference();

    	//LEVEL
    	//
    	CadLevel oLevel = o.getLevel();
    	String strLevelName = oLevel.getLevelLocalName();

    	GeomPoint3d ptOrigem = new GeomPoint3d( o.getPtOrigem() );
    	
    	GeomVector3d xDir = new GeomVector3d( o.getXDir() );

		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() ); 

		String strIsLocked = StringUtil.fromBoolToStr( o.isLocked() ); 

    	super.initEntity(
    		AppDefs.NULL_LNG,
    		//
    		o.getObjectId(), 
    		o.getObjType(), 
    		o.getObjTypeStr(), 
    		o.getObjVer(), 
    		//
    		o.getCadRefEntityId(),
    		//
    		strIsDeleted, 
    		strIsLocked, 
    		//
    		strReference, 
    		strLevelName, 
    		//
    		o.getZLevel() );

    	this.ptOrigemX = ptOrigem.getX();
    	this.ptOrigemY = ptOrigem.getY();
    	this.ptOrigemZ = ptOrigem.getZ();
		//
    	this.xDirXI = xDir.getXI();
    	this.xDirYI = xDir.getYI();
    	this.xDirZI = xDir.getZI();
		//
    	this.xDirXF = xDir.getXF();
    	this.xDirYF = xDir.getYF();
    	this.xDirZF = xDir.getZF();

		//PROJECT_ATTR
		//
		this.codigoProjeto = o.getCodigoProjeto();
		this.tituloProjeto = o.getTituloProjeto();
		this.descricaoProjeto = o.getDescricaoProjeto();
		//
		this.logradouro = o.getLogradouro();
		this.numero = o.getNumero();
		this.complemento = o.getComplemento();
		this.bairro = o.getBairro();
		this.municipio = o.getMunicipio();
		this.estado = o.getEstado();
		this.cep = o.getCep();
		//
		this.art = o.getArt();
		this.nomeResponsavelTecnico = o.getNomeResponsavelTecnico();
		this.registroResponsavelTecnico = o.getRegistroResponsavelTecnico();
		this.telefoneResponsavelTecnico = o.getTelefoneResponsavelTecnico();
		this.emailResponsavelTecnico = o.getEmailResponsavelTecnico();
		//
		this.espgCode = o.getEspgCode();
		this.codigoPluviografo = o.getCodigoPluviografo();
		this.pluviografo = o.getPluviografo();
		this.coefManning = o.getCoefManning();
		this.periodoRecorrencia = o.getPeriodoRecorrencia();
		//
		this.escala = o.getEscala();
		this.papelLargura = o.getPapelLargura();
		this.papelAltura = o.getPapelAltura();
		this.unidade = AppDefs.DEF_DEFAULT_PROJECT_UNIT;
		this.scaleFactor = this.escala / this.unidade;
		this.areaLargura = this.papelLargura * this.scaleFactor;
		this.areaAltura = this.papelAltura * this.scaleFactor; 
    }
	
    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
    	this.ptOrigemX = o.getNextDbl();
    	this.ptOrigemY = o.getNextDbl();
    	this.ptOrigemZ = o.getNextDbl();
		//
    	this.xDirXI = o.getNextDbl();
    	this.xDirYI = o.getNextDbl();
    	this.xDirZI = o.getNextDbl();
		//
    	this.xDirXF = o.getNextDbl();
    	this.xDirYF = o.getNextDbl();
    	this.xDirZF = o.getNextDbl();

		//PROJECT_ATTR
		//
		this.codigoProjeto = o.getNextStr();
		this.tituloProjeto = o.getNextStr();
		this.descricaoProjeto = o.getNextStr();
		//
		this.logradouro = o.getNextStr();
		this.numero = o.getNextStr();
		this.complemento = o.getNextStr();
		this.bairro = o.getNextStr();
		this.municipio = o.getNextStr();
		this.estado = o.getNextStr();
		this.cep = o.getNextStr();
		//
		this.art = o.getNextStr();
		this.nomeResponsavelTecnico = o.getNextStr();
		this.registroResponsavelTecnico = o.getNextStr();
		this.telefoneResponsavelTecnico = o.getNextStr();
		this.emailResponsavelTecnico = o.getNextStr();
		//
		this.espgCode = o.getNextStr();
		this.codigoPluviografo = o.getNextInt();
		this.pluviografo = o.getNextStr();
		this.coefManning = o.getNextDbl();
		this.periodoRecorrencia = o.getNextInt();
		//
		this.escala = o.getNextDbl();
		this.papelLargura = o.getNextDbl();
		this.papelAltura = o.getNextDbl();
		this.unidade = AppDefs.DEF_DEFAULT_PROJECT_UNIT;
		this.scaleFactor = this.escala / this.unidade;
		this.areaLargura = this.papelLargura * this.scaleFactor;
		this.areaAltura = this.papelAltura * this.scaleFactor; 
    }
    
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadProjectDef o = new CadProjectDef(
			oBlkDef, 
			super.getCadLayerDef(doc), 
			super.getCadLevel(doc), 
			super.getZLevel(), 
			false );

    	o.init(
    		o.getPtOrigem(),
    		o.getXDir(),
    		//	
    		o.getCodigoProjeto(),
    		o.getTituloProjeto(),
    		o.getDescricaoProjeto(),
    		//
    		o.getLogradouro(),
    		o.getNumero(),
    		o.getComplemento(),
    		o.getBairro(),
    		o.getMunicipio(),
    		o.getEstado(),
    		o.getCep(),
    		//
    		o.getArt(),
    		o.getNomeResponsavelTecnico(),
    		o.getRegistroResponsavelTecnico(),
    		o.getTelefoneResponsavelTecnico(),
    		o.getEmailResponsavelTecnico(),
    		//
    		o.getEspgCode(),
    		o.getCodigoPluviografo(),
    		o.getPluviografo(),
    		o.getCoefManning(),
    		o.getPeriodoRecorrencia(),
    		//
    		o.getEscala(),
    		o.getPapelLargura(),
    		o.getPapelAltura() );
    	return o;
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

	public double getxDirXI() {
		return xDirXI;
	}

	public void setxDirXI(double xDirXI) {
		this.xDirXI = xDirXI;
	}

	public double getxDirYI() {
		return xDirYI;
	}

	public void setxDirYI(double xDirYI) {
		this.xDirYI = xDirYI;
	}

	public double getxDirZI() {
		return xDirZI;
	}

	public void setxDirZI(double xDirZI) {
		this.xDirZI = xDirZI;
	}

	public double getxDirXF() {
		return xDirXF;
	}

	public void setxDirXF(double xDirXF) {
		this.xDirXF = xDirXF;
	}

	public double getxDirYF() {
		return xDirYF;
	}

	public void setxDirYF(double xDirYF) {
		this.xDirYF = xDirYF;
	}

	public double getxDirZF() {
		return xDirZF;
	}

	public void setxDirZF(double xDirZF) {
		this.xDirZF = xDirZF;
	}
    
}
