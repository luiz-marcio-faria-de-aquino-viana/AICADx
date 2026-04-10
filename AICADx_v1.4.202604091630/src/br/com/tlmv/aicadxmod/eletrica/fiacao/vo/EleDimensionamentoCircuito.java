/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleDimensionamentoCircuito.java
 * Autor: Luiz Marcio Viana, 03/10/2017
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleDimensionamentoCircuito 
{
//Private
	private Integer dimensionamentoCircuitoId;
	private EleIdentificacaoModelo identificacaoModelo;
	private EleDimensionamentoQuadro dimensionamentoQuadro;
	private String circuito;
	private Double potencia;
	private Double tensao;
	private Double bitolaCondutor;
	private Double disjuntorProtecao;
	private String fase;
	private String sistemaFase;
	private EleTipoLinhaEletrica tipoLinhaEletrica;
	private EleIsolacaoCondutor isolacaoCondutor;
	private EleTipoCabo tipoCabo;
	private EleTipoCondutor tipoCondutor;
	private String tipoInstalacao;
	private String metodoReferencia;
	private String possuiDps;
	private String classeDps;
	private Double correnteNominalDps;
	private String possuiIdrDdr;
	private Double correnteFugaIdrDdr;
	private String grupoIdrDdr;
	private String reserva;
	
//Public
	
	/* Getters/Setters */

	public Integer getDimensionamentoCircuitoId() {
		return dimensionamentoCircuitoId;
	}

	public void setDimensionamentoCircuitoId(Integer dimensionamentoCircuitoId) {
		this.dimensionamentoCircuitoId = dimensionamentoCircuitoId;
	}

	public EleDimensionamentoQuadro getDimensionamentoQuadro() {
		return dimensionamentoQuadro;
	}

	public void setDimensionamentoQuadro(
			EleDimensionamentoQuadro dimensionamentoQuadro) {
		this.dimensionamentoQuadro = dimensionamentoQuadro;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public Double getPotencia() {
		return potencia;
	}

	public void setPotencia(Double potencia) {
		this.potencia = potencia;
	}

	public Double getTensao() {
		return tensao;
	}

	public void setTensao(Double tensao) {
		this.tensao = tensao;
	}

	public Double getBitolaCondutor() {
		return bitolaCondutor;
	}

	public void setBitolaCondutor(Double bitolaCondutor) {
		this.bitolaCondutor = bitolaCondutor;
	}

	public Double getDisjuntorProtecao() {
		return disjuntorProtecao;
	}

	public void setDisjuntorProtecao(Double disjuntorProtecao) {
		this.disjuntorProtecao = disjuntorProtecao;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public EleIdentificacaoModelo getIdentificacaoModelo() {
		return identificacaoModelo;
	}

	public void setIdentificacaoModelo(EleIdentificacaoModelo identificacaoModelo) {
		this.identificacaoModelo = identificacaoModelo;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public EleTipoLinhaEletrica getTipoLinhaEletrica() {
		return tipoLinhaEletrica;
	}

	public void setTipoLinhaEletrica(EleTipoLinhaEletrica tipoLinhaEletrica) {
		this.tipoLinhaEletrica = tipoLinhaEletrica;
	}

	public EleIsolacaoCondutor getIsolacaoCondutor() {
		return isolacaoCondutor;
	}

	public void setIsolacaoCondutor(EleIsolacaoCondutor isolacaoCondutor) {
		this.isolacaoCondutor = isolacaoCondutor;
	}

	public EleTipoCabo getTipoCabo() {
		return tipoCabo;
	}

	public void setTipoCabo(EleTipoCabo tipoCabo) {
		this.tipoCabo = tipoCabo;
	}

	public EleTipoCondutor getTipoCondutor() {
		return tipoCondutor;
	}

	public void setTipoCondutor(EleTipoCondutor tipoCondutor) {
		this.tipoCondutor = tipoCondutor;
	}

	public String getMetodoReferencia() {
		return metodoReferencia;
	}

	public void setMetodoReferencia(String metodoReferencia) {
		this.metodoReferencia = metodoReferencia;
	}

	public String getTipoInstalacao() {
		return tipoInstalacao;
	}

	public void setTipoInstalacao(String tipoInstalacao) {
		this.tipoInstalacao = tipoInstalacao;
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

	public String getGrupoIdrDdr() {
		return grupoIdrDdr;
	}

	public void setGrupoIdrDdr(String grupoIdrDdr) {
		this.grupoIdrDdr = grupoIdrDdr;
	}

	public String getReserva() {
		return reserva;
	}

	public void setReserva(String reserva) {
		this.reserva = reserva;
	}

}
