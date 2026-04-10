/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleSecaoMinimaCondutor.java
 * Autor: Luiz Marcio Viana, 07/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleSecaoMinimaCondutor 
{
//Private
	private Integer secaoMinimaCondutorId;
	private String tipoLinha;
	private String tipoUtilizacao;
	private Double secaoMinimaCondutor;
	private EleTipoCondutor tipoCondutor;

//Public
	
	/* Getters/Setters */

	public Integer getSecaoMinimaCondutorId() {
		return secaoMinimaCondutorId;
	}

	public void setSecaoMinimaCondutorId(Integer secaoMinimaCondutorId) {
		this.secaoMinimaCondutorId = secaoMinimaCondutorId;
	}

	public String getTipoLinha() {
		return tipoLinha;
	}

	public void setTipoLinha(String tipoLinha) {
		this.tipoLinha = tipoLinha;
	}

	public String getTipoUtilizacao() {
		return tipoUtilizacao;
	}

	public void setTipoUtilizacao(String tipoUtilizacao) {
		this.tipoUtilizacao = tipoUtilizacao;
	}

	public Double getSecaoMinimaCondutor() {
		return secaoMinimaCondutor;
	}

	public void setSecaoMinimaCondutor(Double secaoMinimaCondutor) {
		this.secaoMinimaCondutor = secaoMinimaCondutor;
	}

	public EleTipoCondutor getTipoCondutor() {
		return tipoCondutor;
	}

	public void setTipoCondutor(EleTipoCondutor tipoCondutor) {
		this.tipoCondutor = tipoCondutor;
	}

}
