/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleSecaoCondutorProtecao.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleSecaoCondutorProtecao 
{
//Private
	private Integer secaoCondutorProtecaoId;
	private String descricao;
	private Double bitolaCondutorFase;
	private Double bitolaCondutorProtecao;

//Public
	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getBitolaCondutorFase() {
		return bitolaCondutorFase;
	}

	public void setBitolaCondutorFase(Double bitolaCondutorFase) {
		this.bitolaCondutorFase = bitolaCondutorFase;
	}

	public Integer getSecaoCondutorProtecaoId() {
		return secaoCondutorProtecaoId;
	}

	public void setSecaoCondutorProtecaoId(Integer secaoCondutorProtecaoId) {
		this.secaoCondutorProtecaoId = secaoCondutorProtecaoId;
	}

	public Double getBitolaCondutorProtecao() {
		return bitolaCondutorProtecao;
	}

	public void setBitolaCondutorProtecao(Double bitolaCondutorProtecao) {
		this.bitolaCondutorProtecao = bitolaCondutorProtecao;
	}

}
