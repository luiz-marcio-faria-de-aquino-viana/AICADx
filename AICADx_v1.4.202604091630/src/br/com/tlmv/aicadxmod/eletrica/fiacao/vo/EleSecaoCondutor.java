/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleSecaoCondutor.java
 * Autor: Luiz Marcio Viana, 20/04/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleSecaoCondutor 
{
//Private
	private Integer secaoCondutorId;
	private String descricao;
	private Double bitolaCondutor;
	private Double diametroExterno;
	private Double diametroInterno;
	private EleIsolacaoCondutor isolacaoCondutor;

//Public
	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getSecaoCondutorId() {
		return secaoCondutorId;
	}

	public void setSecaoCondutorId(Integer secaoCondutorId) {
		this.secaoCondutorId = secaoCondutorId;
	}

	public Double getBitolaCondutor() {
		return bitolaCondutor;
	}

	public void setBitolaCondutor(Double bitolaCondutor) {
		this.bitolaCondutor = bitolaCondutor;
	}

	public Double getDiametroExterno() {
		return diametroExterno;
	}

	public void setDiametroExterno(Double diametroExterno) {
		this.diametroExterno = diametroExterno;
	}

	public Double getDiametroInterno() {
		return diametroInterno;
	}

	public void setDiametroInterno(Double diametroInterno) {
		this.diametroInterno = diametroInterno;
	}

	public EleIsolacaoCondutor getIsolacaoCondutor() {
		return isolacaoCondutor;
	}

	public void setIsolacaoCondutor(EleIsolacaoCondutor isolacaoCondutor) {
		this.isolacaoCondutor = isolacaoCondutor;
	}

}
