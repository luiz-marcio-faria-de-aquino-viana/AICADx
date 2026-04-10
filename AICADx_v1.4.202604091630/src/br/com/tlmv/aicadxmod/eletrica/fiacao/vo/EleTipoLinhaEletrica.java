/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleTipoLinhaEletrica.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleTipoLinhaEletrica 
{
//Private
	private Integer tipoLinhaEletricaId;
	private String descricao;
	private String metodoReferencia;
	private String nomeImage;
	private String nomeDisco;
	private String extImage;

//Public
	
	/* Getters/Setters */
	
	public Integer getTipoLinhaEletricaId() {
		return tipoLinhaEletricaId;
	}

	public void setTipoLinhaEletricaId(Integer tipoLinhaEletricaId) {
		this.tipoLinhaEletricaId = tipoLinhaEletricaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMetodoReferencia() {
		return metodoReferencia;
	}

	public void setMetodoReferencia(String metodoReferencia) {
		this.metodoReferencia = metodoReferencia;
	}

	public String getNomeImage() {
		return nomeImage;
	}

	public void setNomeImage(String nomeImage) {
		this.nomeImage = nomeImage;
	}

	public String getNomeDisco() {
		return nomeDisco;
	}

	public void setNomeDisco(String nomeDisco) {
		this.nomeDisco = nomeDisco;
	}

	public String getExtImage() {
		return extImage;
	}

	public void setExtImage(String extImage) {
		this.extImage = extImage;
	}

}
