/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFiacaoFio.java
 * Autor: Luiz Marcio Viana, 31/10/2017
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFiacaoFio 
{
//Private
	private Integer fiacaoFioId;
	private EleIdentificacaoModelo identificacaoModelo;
	private String hndEletroduto;
	private String nomeQuadro;
	private String circuito;
	private String label;
	private Integer condutor;
	private Double bitolaCondutor;

//Public
	
	/* Getters/Setters */
	
	public String getHndEletroduto() {
		return hndEletroduto;
	}

	public void setHndEletroduto(String hndEletroduto) {
		this.hndEletroduto = hndEletroduto;
	}

	public Integer getFiacaoFioId() {
		return fiacaoFioId;
	}

	public void setFiacaoFioId(Integer fiacaoFioId) {
		this.fiacaoFioId = fiacaoFioId;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public Integer getCondutor() {
		return condutor;
	}

	public void setCondutor(Integer condutor) {
		this.condutor = condutor;
	}

	public Double getBitolaCondutor() {
		return bitolaCondutor;
	}

	public void setBitolaCondutor(Double bitolaCondutor) {
		this.bitolaCondutor = bitolaCondutor;
	}

	public EleIdentificacaoModelo getIdentificacaoModelo() {
		return identificacaoModelo;
	}

	public void setIdentificacaoModelo(EleIdentificacaoModelo identificacaoModelo) {
		this.identificacaoModelo = identificacaoModelo;
	}

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
