/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFatorCorrecaoTemperatura.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFatorCorrecaoTemperatura 
{
//Private
	private Integer fatorCorrecaoTemperaturaId;
	private String tipoIsolacaoCondutor;
	private String descricao;
	private Double temperatura;
	private Double fatorCorrecao;
	private String tipo;

//Public
	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getFatorCorrecao() {
		return fatorCorrecao;
	}

	public void setFatorCorrecao(Double fatorCorrecao) {
		this.fatorCorrecao = fatorCorrecao;
	}

	public Integer getFatorCorrecaoTemperaturaId() {
		return fatorCorrecaoTemperaturaId;
	}

	public void setFatorCorrecaoTemperaturaId(Integer fatorCorrecaoTemperaturaId) {
		this.fatorCorrecaoTemperaturaId = fatorCorrecaoTemperaturaId;
	}

	public String getTipoIsolacaoCondutor() {
		return tipoIsolacaoCondutor;
	}

	public void setTipoIsolacaoCondutor(String tipoIsolacaoCondutor) {
		this.tipoIsolacaoCondutor = tipoIsolacaoCondutor;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
