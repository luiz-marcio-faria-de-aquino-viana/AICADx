/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFatorCorrecaoAgrupamentoMultiplaCamada.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFatorCorrecaoAgrupamentoMultiplaCamada 
{
//Private
	private Integer fatorCorrecaoAgrupamentoMultiplaCamadaId;
	private String descricao;
	private Integer quantidadeCamadas;
	private Integer numeroCircuitos;
	private Double fatorCorrecao;

//Public
	
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

	public Double getFatorCorrecao() {
		return fatorCorrecao;
	}

	public void setFatorCorrecao(Double fatorCorrecao) {
		this.fatorCorrecao = fatorCorrecao;
	}

	public Integer getFatorCorrecaoAgrupamentoMultiplaCamadaId() {
		return fatorCorrecaoAgrupamentoMultiplaCamadaId;
	}

	public void setFatorCorrecaoAgrupamentoMultiplaCamadaId(
			Integer fatorCorrecaoAgrupamentoMultiplaCamadaId) {
		this.fatorCorrecaoAgrupamentoMultiplaCamadaId = fatorCorrecaoAgrupamentoMultiplaCamadaId;
	}

	public Integer getQuantidadeCamadas() {
		return quantidadeCamadas;
	}

	public void setQuantidadeCamadas(Integer quantidadeCamadas) {
		this.quantidadeCamadas = quantidadeCamadas;
	}

}
