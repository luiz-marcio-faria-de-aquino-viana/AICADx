/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleTipoCondutor.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleTipoCondutor 
{
//Private
	private Integer tipoCondutorId;
	private String descricao;
	private Double resistividade;

//Public
	
	/* Getters/Setters */
	
	public Integer getTipoCondutorId() {
		return tipoCondutorId;
	}

	public void setTipoCondutorId(Integer tipoCondutorId) {
		this.tipoCondutorId = tipoCondutorId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getResistividade() {
		return resistividade;
	}

	public void setResistividade(Double resistividade) {
		this.resistividade = resistividade;
	}

}
