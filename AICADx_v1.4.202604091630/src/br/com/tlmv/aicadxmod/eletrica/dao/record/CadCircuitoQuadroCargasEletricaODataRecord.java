/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadCircuitoQuadroCargasEletricaODataRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/02/2026
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

package br.com.tlmv.aicadxmod.eletrica.dao.record;

import java.sql.ResultSet;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadCircuitoQuadroCargasEletricaOData;

public class CadCircuitoQuadroCargasEletricaODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_circuito_quadro_cargas_eletrica_odata";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("row_id", 								AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("nome_quadro", 							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("numero_circuito", 						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("descricao_circuito", 					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("sistema_fase",							AppDefs.TAG_SQLTYPE_STR),
		//
		new SqlColumnVO("qtd_carga_circuito", 					AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_circuito", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_carga_painel", 					AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_painel", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_carga_iluminacao", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_iluminacao", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_carga_tomada", 					AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_tomada", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_carga_motor", 						AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_motor", 							AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("qtd_carga_raiox", 						AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_raiox", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_carga_aquecimento", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_aquecimento", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_carga_outra", 						AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("carga_outra", 							AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("alimentador_circuito", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("alimentador_protecao_circuito", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("disjuntor_circuito", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("fase_circuito", 						AppDefs.TAG_SQLTYPE_STR)
			
	};
					
//Private
	private int rowId = AppDefs.NULL_INT;
	private String nomeQuadro = AppDefs.NULL_STR;
	private String numeroCircuito = AppDefs.NULL_STR;
	private String descricaoCircuito = AppDefs.NULL_STR;
	private String sistemaFase = AppDefs.NULL_STR;
	//
	private int qtdCargaCircuito = 0;
	private double cargaCircuito = 0.0;
	private int qtdCargaPainel = 0;
	private double cargaPainel = 0.0;
	private int qtdCargaIluminacao = 0;
	private double cargaIluminacao = 0.0;
	private int qtdCargaTomada = 0;
	private double cargaTomada = 0.0;
	private int qtdCargaMotor = 0;
	private double cargaMotor = 0.0;
	//
	private int qtdCargaRaioX = 0;
	private double cargaRaioX = 0.0;
	private int qtdCargaAquecimento = 0;
	private double cargaAquecimento = 0.0;
	private int qtdCargaOutra = 0;
	private double cargaOutra = 0.0;
	//
	private double alimentadorCircuito = 0.0;
	private double alimentadorProtecaoCircuito = 0.0;
	private double disjuntorCircuito = 0.0;
	private String faseCircuito = AppDefs.NULL_STR;
	  
//Public
    
    public CadCircuitoQuadroCargasEletricaODataRecord()
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
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			//
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_STR );
    }
    
    public CadCircuitoQuadroCargasEletricaODataRecord(ResultSet rs)
    {
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
    }

    public CadCircuitoQuadroCargasEletricaODataRecord(CadCircuitoQuadroCargasEletricaOData o)
    {
    	String strIsDeleted = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
    	
    	this.init(
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
	    	//
	    	o.getRowId(),
	    	o.getNomeQuadro(),
	    	o.getNumeroCircuito(),
	    	o.getDescricaoCircuito(),
	    	o.getSistemaFase(),
			//
	    	o.getQtdCargaCircuito(),
	    	o.getCargaCircuito(),
	    	o.getQtdCargaPainel(),
	    	o.getCargaPainel(),
	    	o.getQtdCargaIluminacao(),
	    	o.getCargaIluminacao(),
	    	o.getQtdCargaTomada(),
	    	o.getCargaTomada(),
	    	o.getQtdCargaMotor(),
	    	o.getCargaMotor(),
			//
	    	o.getQtdCargaRaioX(),
	    	o.getCargaRaioX(),
	    	o.getQtdCargaAquecimento(),
	    	o.getCargaAquecimento(),
	    	o.getQtdCargaOutra(),
	    	o.getCargaOutra(),
			//
	    	o.getAlimentadorCircuito(),
	    	o.getAlimentadorProtecaoCircuito(),
	    	o.getDisjuntorCircuito(),
	    	o.getFaseCircuito() );
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
	    //
		int rowId,
		String nomeQuadro,
		String numeroCircuito,
		String descricaoCircuito,
		String sistemaFase,
		//
		int qtdCargaCircuito,
		double cargaCircuito,
		int qtdCargaPainel,
		double cargaPainel,
		int qtdCargaIluminacao,
		double cargaIluminacao,
		int qtdCargaTomada,
		double cargaTomada,
		int qtdCargaMotor,
		double cargaMotor,
		//
		int qtdCargaRaioX,
		double cargaRaioX,
		int qtdCargaAquecimento,
		double cargaAquecimento,
		int qtdCargaOutra,
		double cargaOutra,
		//
		double alimentadorCircuito,
		double alimentadorProtecaoCircuito,
		double disjuntorCircuito,
		String faseCircuito )
	{
    	super.initObj(
        		oid, 
        		//
        		objectId, 
        		objType, 
        		objTypeStr, 
        		objVer, 
        		//
        		cadRefEntityId,
        		//
        		AppDefs.DEF_VALUES_NAO,
        		strIsDeleted );

    	this.rowId = rowId;
    	this.nomeQuadro = nomeQuadro;
    	this.numeroCircuito = numeroCircuito;
    	this.descricaoCircuito = descricaoCircuito;
    	this.sistemaFase = sistemaFase;
		//
    	this.qtdCargaCircuito = qtdCargaCircuito;
    	this.cargaCircuito = cargaCircuito;
    	this.qtdCargaPainel = qtdCargaPainel;
    	this.cargaPainel = cargaPainel;
    	this.qtdCargaIluminacao = qtdCargaIluminacao;
    	this.cargaIluminacao = cargaIluminacao;
    	this.qtdCargaTomada = qtdCargaTomada;
    	this.cargaTomada = cargaTomada;
    	this.qtdCargaMotor = qtdCargaMotor;
    	this.cargaMotor = cargaMotor;
		//
    	this.qtdCargaRaioX = qtdCargaRaioX;
    	this.cargaRaioX = cargaRaioX;
    	this.qtdCargaAquecimento = qtdCargaAquecimento;
    	this.cargaAquecimento = cargaAquecimento;
    	this.qtdCargaOutra = qtdCargaOutra;
    	this.cargaOutra = cargaOutra;
		//
    	this.alimentadorCircuito = alimentadorCircuito; 
    	this.alimentadorProtecaoCircuito = alimentadorProtecaoCircuito;
    	this.disjuntorCircuito = disjuntorCircuito;
    	this.faseCircuito = faseCircuito;
	}
    
	@Override
    public void init(DbUtil o)
    {
		super.initObj(o);

    	this.setRowId( o.getNextInt() );
    	this.setNomeQuadro( o.getNextStr() );
    	this.setNumeroCircuito( o.getNextStr() );
    	this.setDescricaoCircuito( o.getNextStr() );
    	this.setSistemaFase( o.getNextStr() );
		//
    	this.setQtdCargaCircuito( o.getNextInt() );
    	this.setCargaCircuito( o.getNextDbl() );
    	this.setQtdCargaPainel( o.getNextInt() );
    	this.setCargaPainel( o.getNextDbl() );
    	this.setQtdCargaIluminacao( o.getNextInt() );
    	this.setCargaIluminacao( o.getNextDbl() );
    	this.setQtdCargaTomada( o.getNextInt() );
    	this.setCargaTomada( o.getNextDbl() );
    	this.setQtdCargaMotor( o.getNextInt() );
    	this.setCargaMotor( o.getNextDbl() );
		//
    	this.setQtdCargaRaioX( o.getNextInt() );
    	this.setCargaRaioX( o.getNextDbl() );
    	this.setQtdCargaAquecimento( o.getNextInt() );
    	this.setCargaAquecimento( o.getNextDbl() );
    	this.setQtdCargaOutra( o.getNextInt() );
    	this.setCargaOutra( o.getNextDbl() );
		//
    	this.setAlimentadorCircuito( o.getNextDbl() ); 
    	this.setAlimentadorProtecaoCircuito( o.getNextDbl() );
    	this.setDisjuntorCircuito( o.getNextDbl() );
    	this.setFaseCircuito( o.getNextStr() );
    	//
	    this.setIsDeleted( o.getNextStr() );
	    
    }
    
    public void init(CadCircuitoQuadroCargasEletricaODataRecord o)
    {
		super.initObj(
			this.getOid(), 
			this.getObjectId(), 
			this.getObjType(), 
			this.getObjTypeStr(), 
			this.getObjVer(),
			this.getCadRefEntityId(), 
			AppDefs.DEF_VALUES_NAO, 
			this.getIsDeleted() );

    	this.rowId = o.getRowId();
    	this.nomeQuadro = o.getNomeQuadro();
    	this.numeroCircuito = o.getNumeroCircuito();
    	this.descricaoCircuito = o.getDescricaoCircuito();
    	this.sistemaFase = o.getSistemaFase();
    	//
    	this.qtdCargaCircuito = o.getQtdCargaCircuito();
    	this.cargaCircuito = o.getCargaCircuito();
    	this.qtdCargaPainel = o.getQtdCargaPainel();
    	this.cargaPainel = o.getCargaPainel();
    	this.qtdCargaIluminacao = o.getQtdCargaIluminacao();
    	this.cargaIluminacao = o.getCargaIluminacao();
    	this.qtdCargaTomada = o.getQtdCargaTomada();
    	this.cargaTomada = o.getCargaTomada();
    	this.qtdCargaMotor = o.getQtdCargaMotor();
    	this.cargaMotor = o.getCargaMotor();
    	//
    	this.qtdCargaRaioX = o.getQtdCargaRaioX();
    	this.cargaRaioX = o.getCargaRaioX();
    	this.qtdCargaAquecimento = o.getQtdCargaAquecimento();
    	this.cargaAquecimento = o.getCargaAquecimento();
    	this.qtdCargaOutra = o.getQtdCargaOutra();
    	this.cargaOutra = o.getCargaOutra();
    	//
    	this.alimentadorCircuito = o.getAlimentadorCircuito(); 
    	this.alimentadorProtecaoCircuito = o.getAlimentadorProtecaoCircuito();
    	this.disjuntorCircuito = o.getDisjuntorCircuito();
    	this.faseCircuito = o.getFaseCircuito();
    }
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"CadRefEntityId:%s;" +
			//
	    	"RowId:%s;" +
	    	"NomeQuadro:%s;" +
	    	"NumeroCircuito:%s;" +
	    	"DescricaoCircuito:%s;" +
	    	"SistemaFase:%s;" +
	    	//
	    	"QtdCargaCircuito:%s;" +
	    	"CargaCircuito:%s;" +
	    	"QtdCargaPainel:%s;" +
	    	"CargaPainel:%s;" +
	    	"QtdCargaIluminacao:%s;" +
	    	"CargaIluminacao:%s;" +
	    	"QtdCargaTomada:%s;" +
	    	"CargaTomada:%s;" +
	    	"QtdCargaMotor:%s;" +
	    	"CargaMotor:%s;" +
	    	//
	    	"QtdCargaRaioX:%s;" +
	    	"CargaRaioX:%s;" +
	    	"QtdCargaAquecimento:%s;" +
	    	"CargaAquecimento:%s;" +
	    	"QtdCargaOutra:%s;" +
	    	"CargaOutra:%s;" +
	    	//
	    	"AlimentadorCircuito:%s;" + 
	    	"AlimentadorProtecaoCircuito:%s;" +
	    	"DisjuntorCircuito:%s;" +
	    	"FaseCircuito:%s ",
			super.getCadRefEntityId(),
			//
	    	this.rowId,
	    	this.nomeQuadro,
	    	this.numeroCircuito,
	    	this.descricaoCircuito,
	    	this.sistemaFase,
	    	//
	    	this.qtdCargaCircuito,
	    	this.cargaCircuito,
	    	this.qtdCargaPainel,
	    	this.cargaPainel,
	    	this.qtdCargaIluminacao,
	    	this.cargaIluminacao,
	    	this.qtdCargaTomada,
	    	this.cargaTomada,
	    	this.qtdCargaMotor,
	    	this.cargaMotor,
	    	//
	    	this.qtdCargaRaioX,
	    	this.cargaRaioX,
	    	this.qtdCargaAquecimento,
	    	this.cargaAquecimento,
	    	this.qtdCargaOutra,
	    	this.cargaOutra,
	    	//
	    	this.alimentadorCircuito, 
	    	this.alimentadorProtecaoCircuito,
	    	this.disjuntorCircuito,
	    	this.faseCircuito );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();
		
		CadCircuitoQuadroCargasEletricaOData o = new CadCircuitoQuadroCargasEletricaOData(doc);
		
		o.init(
			this.getCadRefEntityId(),
			//
	    	this.rowId,
	    	this.nomeQuadro,
	    	this.numeroCircuito,
	    	this.descricaoCircuito,
	    	this.sistemaFase,
	    	//
	    	this.getIsDeleted() );
		o.setObjectId(this.getObjectId());

    	o.setQtdCargaCircuito(this.qtdCargaCircuito);
    	o.setCargaCircuito(this.cargaCircuito);
    	o.setQtdCargaPainel(this.qtdCargaPainel);
    	o.setCargaPainel(this.cargaPainel);
    	o.setQtdCargaIluminacao(this.qtdCargaIluminacao);
    	o.setCargaIluminacao(this.cargaIluminacao);
    	o.setQtdCargaTomada(this.qtdCargaTomada);
    	o.setCargaTomada(this.cargaTomada);
    	o.setQtdCargaMotor(this.qtdCargaMotor);
    	o.setCargaMotor(this.cargaMotor);
    	//
    	o.setQtdCargaRaioX(this.qtdCargaRaioX);
    	o.setCargaRaioX(this.cargaRaioX);
    	o.setQtdCargaAquecimento(this.qtdCargaAquecimento);
    	o.setCargaAquecimento(this.cargaAquecimento);
    	o.setQtdCargaOutra(this.qtdCargaOutra);
    	o.setCargaOutra(this.cargaOutra);
    	//
    	o.setAlimentadorCircuito(this.alimentadorCircuito); 
    	o.setAlimentadorProtecaoCircuito(this.alimentadorProtecaoCircuito);
    	o.setDisjuntorCircuito(this.disjuntorCircuito);
    	o.setFaseCircuito(this.faseCircuito);

    	return o;
	}

    /* Getters/Setters */

	public String getNomeQuadro() {
		return nomeQuadro;
	}
	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getNumeroCircuito() {
		return numeroCircuito;
	}

	public void setNumeroCircuito(String numeroCircuito) {
		this.numeroCircuito = numeroCircuito;
	}

	public String getDescricaoCircuito() {
		return descricaoCircuito;
	}

	public void setDescricaoCircuito(String descricaoCircuito) {
		this.descricaoCircuito = descricaoCircuito;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public int getQtdCargaCircuito() {
		return qtdCargaCircuito;
	}

	public void setQtdCargaCircuito(int qtdCargaCircuito) {
		this.qtdCargaCircuito = qtdCargaCircuito;
	}

	public double getCargaCircuito() {
		return cargaCircuito;
	}

	public void setCargaCircuito(double cargaCircuito) {
		this.cargaCircuito = cargaCircuito;
	}

	public int getQtdCargaPainel() {
		return qtdCargaPainel;
	}

	public void setQtdCargaPainel(int qtdCargaPainel) {
		this.qtdCargaPainel = qtdCargaPainel;
	}

	public double getCargaPainel() {
		return cargaPainel;
	}

	public void setCargaPainel(double cargaPainel) {
		this.cargaPainel = cargaPainel;
	}

	public int getQtdCargaIluminacao() {
		return qtdCargaIluminacao;
	}

	public void setQtdCargaIluminacao(int qtdCargaIluminacao) {
		this.qtdCargaIluminacao = qtdCargaIluminacao;
	}

	public double getCargaIluminacao() {
		return cargaIluminacao;
	}

	public void setCargaIluminacao(double cargaIluminacao) {
		this.cargaIluminacao = cargaIluminacao;
	}

	public int getQtdCargaTomada() {
		return qtdCargaTomada;
	}

	public void setQtdCargaTomada(int qtdCargaTomada) {
		this.qtdCargaTomada = qtdCargaTomada;
	}

	public double getCargaTomada() {
		return cargaTomada;
	}

	public void setCargaTomada(double cargaTomada) {
		this.cargaTomada = cargaTomada;
	}

	public int getQtdCargaMotor() {
		return qtdCargaMotor;
	}

	public void setQtdCargaMotor(int qtdCargaMotor) {
		this.qtdCargaMotor = qtdCargaMotor;
	}

	public double getCargaMotor() {
		return cargaMotor;
	}

	public void setCargaMotor(double cargaMotor) {
		this.cargaMotor = cargaMotor;
	}

	public int getQtdCargaRaioX() {
		return qtdCargaRaioX;
	}

	public void setQtdCargaRaioX(int qtdCargaRaioX) {
		this.qtdCargaRaioX = qtdCargaRaioX;
	}

	public double getCargaRaioX() {
		return cargaRaioX;
	}

	public void setCargaRaioX(double cargaRaioX) {
		this.cargaRaioX = cargaRaioX;
	}

	public int getQtdCargaAquecimento() {
		return qtdCargaAquecimento;
	}

	public void setQtdCargaAquecimento(int qtdCargaAquecimento) {
		this.qtdCargaAquecimento = qtdCargaAquecimento;
	}

	public double getCargaAquecimento() {
		return cargaAquecimento;
	}

	public void setCargaAquecimento(double cargaAquecimento) {
		this.cargaAquecimento = cargaAquecimento;
	}

	public int getQtdCargaOutra() {
		return qtdCargaOutra;
	}

	public void setQtdCargaOutra(int qtdCargaOutra) {
		this.qtdCargaOutra = qtdCargaOutra;
	}

	public double getCargaOutra() {
		return cargaOutra;
	}

	public void setCargaOutra(double cargaOutra) {
		this.cargaOutra = cargaOutra;
	}

	public double getAlimentadorCircuito() {
		return alimentadorCircuito;
	}

	public void setAlimentadorCircuito(double alimentadorCircuito) {
		this.alimentadorCircuito = alimentadorCircuito;
	}

	public double getAlimentadorProtecaoCircuito() {
		return alimentadorProtecaoCircuito;
	}

	public void setAlimentadorProtecaoCircuito(double alimentadorProtecaoCircuito) {
		this.alimentadorProtecaoCircuito = alimentadorProtecaoCircuito;
	}

	public double getDisjuntorCircuito() {
		return disjuntorCircuito;
	}

	public void setDisjuntorCircuito(double disjuntorCircuito) {
		this.disjuntorCircuito = disjuntorCircuito;
	}

	public String getFaseCircuito() {
		return faseCircuito;
	}

	public void setFaseCircuito(String faseCircuito) {
		this.faseCircuito = faseCircuito;
	}
	
}
