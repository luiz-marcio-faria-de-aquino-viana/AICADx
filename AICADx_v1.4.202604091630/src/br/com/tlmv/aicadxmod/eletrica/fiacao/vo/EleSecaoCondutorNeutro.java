/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleSecaoCondutorNeutro.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleSecaoCondutorNeutro 
{
//Private
	private Integer secaoCondutorNeutroId;
	private String descricao;
	private Double bitolaCondutorFase;
	private Double bitolaCondutorNeutro;

//Public
	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getSecaoCondutorNeutroId() {
		return secaoCondutorNeutroId;
	}

	public void setSecaoCondutorNeutroId(Integer secaoCondutorNeutroId) {
		this.secaoCondutorNeutroId = secaoCondutorNeutroId;
	}

	public Double getBitolaCondutorFase() {
		return bitolaCondutorFase;
	}

	public void setBitolaCondutorFase(Double bitolaCondutorFase) {
		this.bitolaCondutorFase = bitolaCondutorFase;
	}

	public Double getBitolaCondutorNeutro() {
		return bitolaCondutorNeutro;
	}

	public void setBitolaCondutorNeutro(Double bitolaCondutorNeutro) {
		this.bitolaCondutorNeutro = bitolaCondutorNeutro;
	}

}
