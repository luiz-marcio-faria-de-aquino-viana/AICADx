/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * CaboData.java
 * Autor: Luiz Marcio Viana, 17/02/2018
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class CaboData 
{
//Private
	private String nomeQuadro;
	private Double bitolaCondutor;
	private Double comprimentoCondutor;
	
//Public
	
	public CaboData(
		String nomeQuadro,
		Double bitolaCondutor,
		Double comprimentoCondutor)
	{
		this.nomeQuadro = nomeQuadro;
		this.bitolaCondutor = bitolaCondutor;
		this.comprimentoCondutor = comprimentoCondutor;	
	}

	/* Getters/Setters */
	
	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public Double getBitolaCondutor() {
		return bitolaCondutor;
	}

	public void setBitolaCondutor(Double bitolaCondutor) {
		this.bitolaCondutor = bitolaCondutor;
	}

	public Double getComprimentoCondutor() {
		return comprimentoCondutor;
	}

	public void setComprimentoCondutor(Double comprimentoCondutor) {
		this.comprimentoCondutor = comprimentoCondutor;
	}	
	
}
