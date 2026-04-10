/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CapacidadeConducaoCorrenteVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/10/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
package br.com.tlmv.aicadxmod.eletrica.vo;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class CapacidadeConducaoCorrenteVO 
{
//Private
	private Integer capacidadeConducaoCorrenteId;
	private String tipoIsolacaoCondutor;
	private String descricao;
	private Double bitola;
	private Integer numeroCondutoresCarregados;
	private Double capacidadeCorrente;
	//private EleTipoCondutor tipoCondutor;
	private int tipoCondutorId;
	private String metodoReferencia;

//Public
    
    public CapacidadeConducaoCorrenteVO() {
    	this.init(
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR );
    }
		
	public CapacidadeConducaoCorrenteVO(
		Integer capacidadeConducaoCorrenteId,
		String tipoIsolacaoCondutor,
		String descricao,
		Double bitola,
		Integer numeroCondutoresCarregados,
		Double capacidadeCorrente,
		int tipoCondutorId,
		String metodoReferencia )
	{
    	this.init(
			capacidadeConducaoCorrenteId,
			tipoIsolacaoCondutor,
			descricao,
			bitola,
			numeroCondutoresCarregados,
			capacidadeCorrente,
			tipoCondutorId,
			metodoReferencia );
	}

    public CapacidadeConducaoCorrenteVO(CapacidadeConducaoCorrenteVO other)
    {
        this.init(other);
    }

	/* Methodes */
		
	public void init(
		Integer capacidadeConducaoCorrenteId,
		String tipoIsolacaoCondutor,
		String descricao,
		Double bitola,
		Integer numeroCondutoresCarregados,
		Double capacidadeCorrente,
		int tipoCondutorId,
		String metodoReferencia )
	{
		this.capacidadeConducaoCorrenteId = capacidadeConducaoCorrenteId;
		this.tipoIsolacaoCondutor = tipoIsolacaoCondutor;
		this.descricao = descricao;
		this.bitola = bitola;
		this.numeroCondutoresCarregados = numeroCondutoresCarregados;
		this.capacidadeCorrente = capacidadeCorrente;
		this.tipoCondutorId = tipoCondutorId;
		this.metodoReferencia = metodoReferencia;
	}
	
    public void init(CapacidadeConducaoCorrenteVO other)
    {
    	this.init(
			other.getCapacidadeConducaoCorrenteId(),
			other.getTipoIsolacaoCondutor(),
			other.getDescricao(),
			other.getBitola(),
			other.getNumeroCondutoresCarregados(),
			other.getCapacidadeCorrente(),
			other.getTipoCondutorId(),
			other.getMetodoReferencia() );
    }
	
    /* CREATE */
    
    public static CapacidadeConducaoCorrenteVO createFrom(String str)
    {
    	CapacidadeConducaoCorrenteVO o = new CapacidadeConducaoCorrenteVO();
    	o.fromStr(str);
    	return o;
    }
    
    /* DEBUG */
    
	public String toStr() {
		String str = String.format(
			"capacidadeConducaoCorrenteId:%s;" +
			"tipoIsolacaoCondutor:%s;" +
			"descricao:%s;" +
			"bitola:%s;" +
			"numeroCondutoresCarregados:%s;" +
			"capacidadeCorrente:%s;" +
			"tipoCondutorId:%s;" +
			"metodoReferencia:%s;\n",
			this.capacidadeConducaoCorrenteId,
			this.tipoIsolacaoCondutor,
			this.descricao,
			this.bitola,
			this.numeroCondutoresCarregados,
			this.capacidadeCorrente,
			this.tipoCondutorId,
			this.metodoReferencia );
		return str;
	}

	public void fromStr(String str) {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String[] arr = StringUtil.split(str, '|');
		if(arr.length >= 9) {
			String strCapacidadeConducaoCorrenteId = arr[0];
			//String strInstalacaoId = arr[1];
			String strTipoIsolacaoCondutor = arr[2];
			String strDescricao = arr[3];
			String strBitola = arr[4];
			String strNumeroCondutoresCarregados = arr[5];
			String strCapacidadeCorrente = arr[6];
			String strTipoCondutorId = arr[7];
			String strMetodoReferencia = arr[8];

			this.capacidadeConducaoCorrenteId = StringUtil.safeInt(strCapacidadeConducaoCorrenteId);
			this.tipoIsolacaoCondutor = strTipoIsolacaoCondutor;
			this.descricao = strDescricao;
			this.bitola = StringUtil.safeDbl(nf6, strBitola);
			this.numeroCondutoresCarregados = StringUtil.safeInt(strNumeroCondutoresCarregados);
			this.capacidadeCorrente = StringUtil.safeDbl(nf6, strCapacidadeCorrente);
			this.tipoCondutorId = StringUtil.safeInt(strTipoCondutorId);
			this.metodoReferencia = strMetodoReferencia;
		}
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		AppError.showCmdWarn(debugLevel, str, this.getClass());
	}
    
	/* Getters/Setters */
	
	public Integer getCapacidadeConducaoCorrenteId() {
		return capacidadeConducaoCorrenteId;
	}

	public void setCapacidadeConducaoCorrenteId(Integer capacidadeConducaoCorrenteId) {
		this.capacidadeConducaoCorrenteId = capacidadeConducaoCorrenteId;
	}

	public String getTipoIsolacaoCondutor() {
		return tipoIsolacaoCondutor;
	}

	public void setTipoIsolacaoCondutor(String tipoIsolacoCondutor) {
		this.tipoIsolacaoCondutor = tipoIsolacoCondutor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getBitola() {
		return bitola;
	}

	public void setBitola(Double bitola) {
		this.bitola = bitola;
	}

	public Integer getNumeroCondutoresCarregados() {
		return numeroCondutoresCarregados;
	}

	public void setNumeroCondutoresCarregados(Integer numeroCondutoresCarregados) {
		this.numeroCondutoresCarregados = numeroCondutoresCarregados;
	}

	public Double getCapacidadeCorrente() {
		return capacidadeCorrente;
	}

	public void setCapacidadeCorrente(Double capacidadeCorrente) {
		this.capacidadeCorrente = capacidadeCorrente;
	}

	public String getMetodoReferencia() {
		return metodoReferencia;
	}

	public void setMetodoReferencia(String metodoReferencia) {
		this.metodoReferencia = metodoReferencia;
	}

	public int getTipoCondutorId() {
		return tipoCondutorId;
	}

	public void setTipoCondutorId(int tipoCondutorId) {
		this.tipoCondutorId = tipoCondutorId;
	}

}
