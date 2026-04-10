/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * FiacaoEletrodutoData.java
 * Autor: Luiz Marcio Viana, 24/07/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class FiacaoEletrodutoData 
{
//Private
	private static final long serialVersionUID = 1L;
	
	private Integer fiacaoEletrodutoId;
	private Integer identificacaoModeloId;
	private String hndEletroduto;
	private Double areaOcupada;
	private Double areaEletroduto;
	private Double bitolaEletroduto;
	private Double taxaOcupacao;
	private Double comprimento;
	private Integer numeroCondutores;

//Public
	
	/* Getters/Setters */

	public String getHndEletroduto() {
		return hndEletroduto;
	}

	public void setHndEletroduto(String hndEletroduto) {
		this.hndEletroduto = hndEletroduto;
	}

	public Integer getFiacaoEletrodutoId() {
		return fiacaoEletrodutoId;
	}

	public void setFiacaoEletrodutoId(Integer fiacaoEletrodutoId) {
		this.fiacaoEletrodutoId = fiacaoEletrodutoId;
	}

	public Double getAreaOcupada() {
		return areaOcupada;
	}

	public void setAreaOcupada(Double areaOcupada) {
		this.areaOcupada = areaOcupada;
	}

	public Double getAreaEletroduto() {
		return areaEletroduto;
	}

	public void setAreaEletroduto(Double areaEletroduto) {
		this.areaEletroduto = areaEletroduto;
	}

	public Double getBitolaEletroduto() {
		return bitolaEletroduto;
	}

	public void setBitolaEletroduto(Double bitolaEletroduto) {
		this.bitolaEletroduto = bitolaEletroduto;
	}

	public Double getTaxaOcupacao() {
		return taxaOcupacao;
	}

	public void setTaxaOcupacao(Double taxaOcupacao) {
		this.taxaOcupacao = taxaOcupacao;
	}

	public Double getComprimento() {
		return comprimento;
	}

	public void setComprimento(Double comprimento) {
		this.comprimento = comprimento;
	}

	public Integer getNumeroCondutores() {
		return numeroCondutores;
	}

	public void setNumeroCondutores(Integer numeroCondutores) {
		this.numeroCondutores = numeroCondutores;
	}

	public Integer getIdentificacaoModeloId() {
		return identificacaoModeloId;
	}

	public void setIdentificacaoModeloId(Integer identificacaoModeloId) {
		this.identificacaoModeloId = identificacaoModeloId;
	}

}
