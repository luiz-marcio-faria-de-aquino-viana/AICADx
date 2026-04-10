/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * FatorCorrecaoAgrupamentoVO.java
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

public class FatorCorrecaoAgrupamentoVO 
{
//Private
	private Integer fatorCorrecaoAgrupamentoId;
	private String descricao;
	private String formaAgrupamento;
	private Integer numeroCircuitos;
	private Double fatorCorrecao;

//Public

    public FatorCorrecaoAgrupamentoVO() {
    	this.init(
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT,
			AppDefs.NULL_DBL );
    }
			
	public FatorCorrecaoAgrupamentoVO(
		Integer fatorCorrecaoAgrupamentoId,
		String descricao,
		String formaAgrupamento,
		Integer numeroCircuitos,
		Double fatorCorrecao )
	{
    	this.init(
			fatorCorrecaoAgrupamentoId,
			descricao,
			formaAgrupamento,
			numeroCircuitos,
			fatorCorrecao );
	}

    public FatorCorrecaoAgrupamentoVO(FatorCorrecaoAgrupamentoVO other)
    {
        this.init(other);
    }

	/* Methodes */
		
	public void init(
		Integer fatorCorrecaoAgrupamentoId,
		String descricao,
		String formaAgrupamento,
		Integer numeroCircuitos,
		Double fatorCorrecao )
	{
		this.fatorCorrecaoAgrupamentoId = fatorCorrecaoAgrupamentoId;
		this.descricao = descricao;
		this.formaAgrupamento = formaAgrupamento;
		this.numeroCircuitos = numeroCircuitos;
		this.fatorCorrecao = fatorCorrecao;
	}
	
    public void init(FatorCorrecaoAgrupamentoVO other)
    {
    	this.init(
			other.fatorCorrecaoAgrupamentoId,
			other.descricao,
			other.formaAgrupamento,
			other.numeroCircuitos,
			other.fatorCorrecao );
    }
	
    /* CREATE */
    
    public static FatorCorrecaoAgrupamentoVO createFrom(String str)
    {
    	FatorCorrecaoAgrupamentoVO o = new FatorCorrecaoAgrupamentoVO();
    	o.fromStr(str);
    	return o;
    }
    
    /* DEBUG */
    
	public String toStr() {
		String str = String.format(
			"fatorCorrecaoAgrupamentoId:%s;" +
			"descricao:%s;" +
			"formaAgrupamento:%s;" +
			"numeroCircuitos:%s;" +
			"fatorCorrecao:%s;\n",
			this.fatorCorrecaoAgrupamentoId,
			this.descricao,
			this.formaAgrupamento,
			this.numeroCircuitos,
			this.fatorCorrecao );
		return str;
	}

	public void fromStr(String str) {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String[] arr = StringUtil.split(str, '|');
		if(arr.length >= 5) {
			String strFatorCorrecaoAgrupamentoId = arr[0];
			String strDescricao = arr[1];
			String strFormaAgrupamento = arr[2];
			String strNumeroCircuitos = arr[3];	
			String strFatorCorrecao = arr[4];

			this.fatorCorrecaoAgrupamentoId = StringUtil.safeInt(strFatorCorrecaoAgrupamentoId);
			this.descricao = strDescricao;
			this.formaAgrupamento = strFormaAgrupamento;
			this.numeroCircuitos = StringUtil.safeInt(strNumeroCircuitos);
			this.fatorCorrecao = StringUtil.safeDbl(nf6, strFatorCorrecao);
		}
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		AppError.showCmdWarn(debugLevel, str, this.getClass());
	}
	    	
	
	/* Getters/Setters */

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroCircuitos() {
		return numeroCircuitos;
	}

	public void setNumeroCircuitos(Integer numeroCircuitos) {
		this.numeroCircuitos = numeroCircuitos;
	}

	public Integer getFatorCorrecaoAgrupamentoId() {
		return fatorCorrecaoAgrupamentoId;
	}

	public void setFatorCorrecaoAgrupamentoId(Integer fatorCorrecaoAgrupamentoId) {
		this.fatorCorrecaoAgrupamentoId = fatorCorrecaoAgrupamentoId;
	}

	public String getFormaAgrupamento() {
		return formaAgrupamento;
	}

	public void setFormaAgrupamento(String formaAgrupamento) {
		this.formaAgrupamento = formaAgrupamento;
	}

	public Double getFatorCorrecao() {
		return fatorCorrecao;
	}

	public void setFatorCorrecao(Double fatorCorrecao) {
		this.fatorCorrecao = fatorCorrecao;
	}

}
