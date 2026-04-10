/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleNumeroCondutorCarregado.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleNumeroCondutorCarregado 
{
//Private
	private Integer numeroCondutorCarregadoId;
	private String descricao;
	private Integer numeroCondutor;
	private String sistemaFase;

//Public
	
	/* Getters/Setters */

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroCondutorCarregadoId() {
		return numeroCondutorCarregadoId;
	}

	public void setNumeroCondutorCarregadoId(Integer numeroCondutorCarregadoId) {
		this.numeroCondutorCarregadoId = numeroCondutorCarregadoId;
	}

	public Integer getNumeroCondutor() {
		return numeroCondutor;
	}

	public void setNumeroCondutor(Integer numeroCondutor) {
		this.numeroCondutor = numeroCondutor;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

}
