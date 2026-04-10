/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFatorCorrecaoResistividadeTermica.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFatorCorrecaoResistividadeTermica 
{
//Private
	private Integer fatorCorrecaoResistividadeTermicaId;
	private String descricao;
	private Double resistividadeTermica;
	private Double fatorCorrecao;

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

	public Integer getFatorCorrecaoResistividadeTermicaId() {
		return fatorCorrecaoResistividadeTermicaId;
	}

	public void setFatorCorrecaoResistividadeTermicaId(
			Integer fatorCorrecaoResistividadeTermicaId) {
		this.fatorCorrecaoResistividadeTermicaId = fatorCorrecaoResistividadeTermicaId;
	}

	public Double getResistividadeTermica() {
		return resistividadeTermica;
	}

	public void setResistividadeTermica(Double resistividadeTermica) {
		this.resistividadeTermica = resistividadeTermica;
	}

}
