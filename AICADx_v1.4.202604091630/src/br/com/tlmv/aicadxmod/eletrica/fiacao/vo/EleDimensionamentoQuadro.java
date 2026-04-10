/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleDimensionamentoQuadro.java
 * Autor: Luiz Marcio Viana, 03/10/2017
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleDimensionamentoQuadro 
{
//Private
	private Integer dimensionamentoQuadroId;
	private EleIdentificacaoModelo identificacaoModelo;
	private String nomeQuadro;
	private Double potenciaQuadro;
	private Double potenciaQuadroSemReserva;
	private Double tensaoQuadro;
	private Double alimentadorQuadro;
	private Double alimentadorProtecaoQuadro;
	private Double disjuntorQuadro;
	private String faseQuadro;
	private String sistemaFase;
	private String possuiDps;
	private String classeDps;
	private Double correnteNominalDps;
	private String possuiIdrDdr;
	private Double correnteFugaIdrDdr;
	
//Public
	
	/* Getters/Setters */
	
	public Integer getDimensionamentoQuadroId() {
		return dimensionamentoQuadroId;
	}

	public void setDimensionamentoQuadroId(Integer dimensionamentoQuadroId) {
		this.dimensionamentoQuadroId = dimensionamentoQuadroId;
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

	public Double getPotenciaQuadro() {
		return potenciaQuadro;
	}

	public void setPotenciaQuadro(Double potenciaQuadro) {
		this.potenciaQuadro = potenciaQuadro;
	}

	public Double getTensaoQuadro() {
		return tensaoQuadro;
	}

	public void setTensaoQuadro(Double tensaoQuadro) {
		this.tensaoQuadro = tensaoQuadro;
	}

	public Double getAlimentadorQuadro() {
		return alimentadorQuadro;
	}

	public void setAlimentadorQuadro(Double alimentadorQuadro) {
		this.alimentadorQuadro = alimentadorQuadro;
	}

	public Double getAlimentadorProtecaoQuadro() {
		return alimentadorProtecaoQuadro;
	}

	public void setAlimentadorProtecaoQuadro(Double alimentadorProtecaoQuadro) {
		this.alimentadorProtecaoQuadro = alimentadorProtecaoQuadro;
	}

	public Double getDisjuntorQuadro() {
		return disjuntorQuadro;
	}

	public void setDisjuntorQuadro(Double disjuntorQuadro) {
		this.disjuntorQuadro = disjuntorQuadro;
	}

	public String getFaseQuadro() {
		return faseQuadro;
	}

	public void setFaseQuadro(String faseQuadro) {
		this.faseQuadro = faseQuadro;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public String getPossuiDps() {
		return possuiDps;
	}

	public void setPossuiDps(String possuiDps) {
		this.possuiDps = possuiDps;
	}

	public String getClasseDps() {
		return classeDps;
	}

	public void setClasseDps(String classeDps) {
		this.classeDps = classeDps;
	}

	public Double getCorrenteNominalDps() {
		return correnteNominalDps;
	}

	public void setCorrenteNominalDps(Double correnteNominalDps) {
		this.correnteNominalDps = correnteNominalDps;
	}

	public String getPossuiIdrDdr() {
		return possuiIdrDdr;
	}

	public void setPossuiIdrDdr(String possuiIdrDdr) {
		this.possuiIdrDdr = possuiIdrDdr;
	}

	public Double getCorrenteFugaIdrDdr() {
		return correnteFugaIdrDdr;
	}

	public void setCorrenteFugaIdrDdr(Double correnteFugaIdrDdr) {
		this.correnteFugaIdrDdr = correnteFugaIdrDdr;
	}

	public Double getPotenciaQuadroSemReserva() {
		return potenciaQuadroSemReserva;
	}

	public void setPotenciaQuadroSemReserva(Double potenciaQuadroSemReserva) {
		this.potenciaQuadroSemReserva = potenciaQuadroSemReserva;
	}

}
