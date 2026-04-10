/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleEspacoReservaQuadroDistribuicao.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleEspacoReservaQuadroDistribuicao 
{
//Private
	private Integer espacoReservaQuadroDistribuicaoId;
	private Integer numeroCircuitos;
	private Integer numeroEspacosReserva;
	private String descricao;
	private String tipoOperacao;
	private Double multiplicadorEspacosReserva;

//Public
	
	/* Getters/Setters */

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getEspacoReservaQuadroDistribuicaoId() {
		return espacoReservaQuadroDistribuicaoId;
	}

	public void setEspacoReservaQuadroDistribuicaoId(
			Integer espacoReservaQuadroDistribuicaoId) {
		this.espacoReservaQuadroDistribuicaoId = espacoReservaQuadroDistribuicaoId;
	}

	public Integer getNumeroCircuitos() {
		return numeroCircuitos;
	}

	public void setNumeroCircuitos(Integer numeroCircuitos) {
		this.numeroCircuitos = numeroCircuitos;
	}

	public Integer getNumeroEspacosReserva() {
		return numeroEspacosReserva;
	}

	public void setNumeroEspacosReserva(Integer numeroEspacosReserva) {
		this.numeroEspacosReserva = numeroEspacosReserva;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Double getMultiplicadorEspacosReserva() {
		return multiplicadorEspacosReserva;
	}

	public void setMultiplicadorEspacosReserva(Double multiplicadorEspacosReserva) {
		this.multiplicadorEspacosReserva = multiplicadorEspacosReserva;
	}

}
