/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFatorCorrecaoAgrupamentoEletrodutoEnterrado.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFatorCorrecaoAgrupamentoEletrodutoEnterrado 
{
//Private
	private Integer fatorCorrecaoAgrupamentoEletrodutoEnterradoId;
	private String tipoCabo;
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

	public Double getDistanciaEntreCircuitos() {
		return distanciaEntreCircuitos;
	}

	public void setDistanciaEntreCircuitos(Double distanciaEntreCircuitos) {
		this.distanciaEntreCircuitos = distanciaEntreCircuitos;
	}

	public Integer getFatorCorrecaoAgrupamentoEletrodutoEnterradoId() {
		return fatorCorrecaoAgrupamentoEletrodutoEnterradoId;
	}

	public void setFatorCorrecaoAgrupamentoEletrodutoEnterradoId(
			Integer fatorCorrecaoAgrupamentoEletrodutoEnterradoId) {
		this.fatorCorrecaoAgrupamentoEletrodutoEnterradoId = fatorCorrecaoAgrupamentoEletrodutoEnterradoId;
	}

	public String getTipoCabo() {
		return tipoCabo;
	}

	public void setTipoCabo(String tipoCabo) {
		this.tipoCabo = tipoCabo;
	}

}
