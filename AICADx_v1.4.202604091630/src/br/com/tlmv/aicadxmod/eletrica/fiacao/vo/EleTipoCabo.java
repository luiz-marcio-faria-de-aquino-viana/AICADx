/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleTipoCabo.java
 * Autor: Luiz Marcio Viana, 19/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleTipoCabo 
{
//Private
	private Integer tipoCaboId;
	private String descricao;
	private String tipoCabo;
	private String numeroPolos;

//Public
	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTipoCaboId() {
		return tipoCaboId;
	}

	public void setTipoCaboId(Integer tipoCaboId) {
		this.tipoCaboId = tipoCaboId;
	}

	public String getTipoCabo() {
		return tipoCabo;
	}

	public void setTipoCabo(String tipoCabo) {
		this.tipoCabo = tipoCabo;
	}

	public String getNumeroPolos() {
		return numeroPolos;
	}

	public void setNumeroPolos(String numeroPolos) {
		this.numeroPolos = numeroPolos;
	}

}
