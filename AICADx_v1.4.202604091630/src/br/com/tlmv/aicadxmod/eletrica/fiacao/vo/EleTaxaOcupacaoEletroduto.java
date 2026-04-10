/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleTaxaOcupacaoEletroduto.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleTaxaOcupacaoEletroduto 
{
//Private
	private Integer taxaOcupacaoEletrodutoId;
	private String descricao;
	private Integer numeroCondutores;
	private Double taxaOcupacao;

//Public
	
	/* Getters/Setters */
	
	public Integer getTaxaOcupacaoEletrodutoId() {
		return taxaOcupacaoEletrodutoId;
	}

	public void setTaxaOcupacaoEletrodutoId(Integer taxaOcupacaoEletrodutoId) {
		this.taxaOcupacaoEletrodutoId = taxaOcupacaoEletrodutoId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroCondutores() {
		return numeroCondutores;
	}

	public void setNumeroCondutores(Integer numeroCondutores) {
		this.numeroCondutores = numeroCondutores;
	}

	public Double getTaxaOcupacao() {
		return taxaOcupacao;
	}

	public void setTaxaOcupacao(Double taxaOcupacao) {
		this.taxaOcupacao = taxaOcupacao;
	}

}
