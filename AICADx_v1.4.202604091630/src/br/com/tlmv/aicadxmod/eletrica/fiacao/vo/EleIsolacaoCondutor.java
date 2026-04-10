/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleIsolacaoCondutor.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleIsolacaoCondutor 
{
//Private
	private Integer isolacaoCondutorId;
	private String descricao;
	private Double temperaturaMaximaServico;
	private Double temperaturaLimiteSobrecarga;
	private Double temperaturaLimiteCurtoCircuito;
	private Double diametroMaximo;
	private String tipoIsolacaoCondutor;

//Public
	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoIsolacaoCondutor() {
		return tipoIsolacaoCondutor;
	}

	public void setTipoIsolacaoCondutor(String tipoIsolacaoCondutor) {
		this.tipoIsolacaoCondutor = tipoIsolacaoCondutor;
	}

	public Integer getIsolacaoCondutorId() {
		return isolacaoCondutorId;
	}

	public void setIsolacaoCondutorId(Integer isolacaoCondutorId) {
		this.isolacaoCondutorId = isolacaoCondutorId;
	}

	public Double getTemperaturaMaximaServico() {
		return temperaturaMaximaServico;
	}

	public void setTemperaturaMaximaServico(Double temperaturaMaximaServico) {
		this.temperaturaMaximaServico = temperaturaMaximaServico;
	}

	public Double getTemperaturaLimiteSobrecarga() {
		return temperaturaLimiteSobrecarga;
	}

	public void setTemperaturaLimiteSobrecarga(Double temperaturaLimiteSobrecarga) {
		this.temperaturaLimiteSobrecarga = temperaturaLimiteSobrecarga;
	}

	public Double getTemperaturaLimiteCurtoCircuito() {
		return temperaturaLimiteCurtoCircuito;
	}

	public void setTemperaturaLimiteCurtoCircuito(
			Double temperaturaLimiteCurtoCircuito) {
		this.temperaturaLimiteCurtoCircuito = temperaturaLimiteCurtoCircuito;
	}

	public Double getDiametroMaximo() {
		return diametroMaximo;
	}

	public void setDiametroMaximo(Double diametroMaximo) {
		this.diametroMaximo = diametroMaximo;
	}

}
