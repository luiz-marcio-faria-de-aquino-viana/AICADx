/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFatorCorrecaoAgrupamentoCaboEnterrado.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFatorCorrecaoAgrupamentoCaboEnterrado 
{
//Private
	private Integer fatorCorrecaoAgrupamentoCaboEnterradoId;
	private String descricao;
	private Integer numeroCircuitos;
	private Double distanciaEntreCircuitos;
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

	public Integer getFatorCorrecaoAgrupamentoCaboEnterradoId() {
		return fatorCorrecaoAgrupamentoCaboEnterradoId;
	}

	public void setFatorCorrecaoAgrupamentoCaboEnterradoId(
			Integer fatorCorrecaoAgrupamentoCaboEnterradoId) {
		this.fatorCorrecaoAgrupamentoCaboEnterradoId = fatorCorrecaoAgrupamentoCaboEnterradoId;
	}

	public Double getDistanciaEntreCircuitos() {
		return distanciaEntreCircuitos;
	}

	public void setDistanciaEntreCircuitos(Double distanciaEntreCircuitos) {
		this.distanciaEntreCircuitos = distanciaEntreCircuitos;
	}

}
