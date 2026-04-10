/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleIdentificacaoModelo.java
 * Autor: Luiz Marcio Viana, 03/10/2017
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleIdentificacaoModelo 
{
//Private
	private Integer identificacaoModeloId;
	private String nomeDisco;
	private Integer codigo;
	private java.sql.Timestamp dataCriacao;
	private java.sql.Timestamp dataAtualizacao;
	private java.sql.Timestamp dataDesativacao;

//Public
	
	/* Getters/Setters */

	public String getNomeDisco() {
		return nomeDisco;
	}

	public void setNomeDisco(String nomeDisco) {
		this.nomeDisco = nomeDisco;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getIdentificacaoModeloId() {
		return identificacaoModeloId;
	}

	public void setIdentificacaoModeloId(Integer identificacaoModeloId) {
		this.identificacaoModeloId = identificacaoModeloId;
	}

	public java.sql.Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(java.sql.Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public java.sql.Timestamp getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(java.sql.Timestamp dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public java.sql.Timestamp getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(java.sql.Timestamp dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

}
