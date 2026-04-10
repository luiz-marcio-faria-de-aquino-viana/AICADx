/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * EleFiacao.java
 * Autor: Luiz Marcio Viana, 03/10/2017
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.vo;

public class EleFiacao 
{
//Private
	private Integer fiacaoId;
	private EleIdentificacaoModelo identificacaoModelo;
	private String hndEletroduto;
	private String hndPonto1;
	private String indicePonto1;
	private String tipoPonto1;
	private String nomeQuadroPonto1;
	private String quadroOrigemPonto1;
	private String desvioPonto1;
	private String circuitoPonto1;
	private String comandoPonto1;
	private String fasePonto1;
	private Double potenciaPonto1;
	private Double demandaPonto1;
	private String hndPonto2;
	private String indicePonto2;
	private String tipoPonto2;
	private String nomeQuadroPonto2;
	private String quadroOrigemPonto2;
	private String desvioPonto2;
	private String circuitoPonto2;
	private String comandoPonto2;
	private String fasePonto2;
	private Double potenciaPonto2;
	private Double demandaPonto2;
	private Double comprimentoEletroduto;
	
//Public
	
	/* Getters/Setters */
	
	public Integer getFiacaoId() {
		return fiacaoId;
	}

	public void setFiacaoId(Integer fiacaoId) {
		this.fiacaoId = fiacaoId;
	}

	public EleIdentificacaoModelo getIdentificacaoModelo() {
		return identificacaoModelo;
	}

	public void setIdentificacaoModelo(EleIdentificacaoModelo identificacaoModelo) {
		this.identificacaoModelo = identificacaoModelo;
	}

	public String getHndEletroduto() {
		return hndEletroduto;
	}

	public void setHndEletroduto(String hndEletroduto) {
		this.hndEletroduto = hndEletroduto;
	}

	public String getHndPonto1() {
		return hndPonto1;
	}

	public void setHndPonto1(String hndPonto1) {
		this.hndPonto1 = hndPonto1;
	}

	public String getIndicePonto1() {
		return indicePonto1;
	}

	public void setIndicePonto1(String indicePonto1) {
		this.indicePonto1 = indicePonto1;
	}

	public String getTipoPonto1() {
		return tipoPonto1;
	}

	public void setTipoPonto1(String tipoPonto1) {
		this.tipoPonto1 = tipoPonto1;
	}

	public String getNomeQuadroPonto1() {
		return nomeQuadroPonto1;
	}

	public void setNomeQuadroPonto1(String nomeQuadroPonto1) {
		this.nomeQuadroPonto1 = nomeQuadroPonto1;
	}

	public String getQuadroOrigemPonto1() {
		return quadroOrigemPonto1;
	}

	public void setQuadroOrigemPonto1(String quadroOrigemPonto1) {
		this.quadroOrigemPonto1 = quadroOrigemPonto1;
	}

	public String getDesvioPonto1() {
		return desvioPonto1;
	}

	public void setDesvioPonto1(String desvioPonto1) {
		this.desvioPonto1 = desvioPonto1;
	}

	public String getCircuitoPonto1() {
		return circuitoPonto1;
	}

	public void setCircuitoPonto1(String circuitoPonto1) {
		this.circuitoPonto1 = circuitoPonto1;
	}

	public String getComandoPonto1() {
		return comandoPonto1;
	}

	public void setComandoPonto1(String comandoPonto1) {
		this.comandoPonto1 = comandoPonto1;
	}

	public String getFasePonto1() {
		return fasePonto1;
	}

	public void setFasePonto1(String fasePonto1) {
		this.fasePonto1 = fasePonto1;
	}

	public String getHndPonto2() {
		return hndPonto2;
	}

	public void setHndPonto2(String hndPonto2) {
		this.hndPonto2 = hndPonto2;
	}

	public String getIndicePonto2() {
		return indicePonto2;
	}

	public void setIndicePonto2(String indicePonto2) {
		this.indicePonto2 = indicePonto2;
	}

	public String getTipoPonto2() {
		return tipoPonto2;
	}

	public void setTipoPonto2(String tipoPonto2) {
		this.tipoPonto2 = tipoPonto2;
	}

	public String getNomeQuadroPonto2() {
		return nomeQuadroPonto2;
	}

	public void setNomeQuadroPonto2(String nomeQuadroPonto2) {
		this.nomeQuadroPonto2 = nomeQuadroPonto2;
	}

	public String getQuadroOrigemPonto2() {
		return quadroOrigemPonto2;
	}

	public void setQuadroOrigemPonto2(String quadroOrigemPonto2) {
		this.quadroOrigemPonto2 = quadroOrigemPonto2;
	}

	public String getDesvioPonto2() {
		return desvioPonto2;
	}

	public void setDesvioPonto2(String desvioPonto2) {
		this.desvioPonto2 = desvioPonto2;
	}

	public String getCircuitoPonto2() {
		return circuitoPonto2;
	}

	public void setCircuitoPonto2(String circuitoPonto2) {
		this.circuitoPonto2 = circuitoPonto2;
	}

	public String getComandoPonto2() {
		return comandoPonto2;
	}

	public void setComandoPonto2(String comandoPonto2) {
		this.comandoPonto2 = comandoPonto2;
	}

	public String getFasePonto2() {
		return fasePonto2;
	}

	public void setFasePonto2(String fasePonto2) {
		this.fasePonto2 = fasePonto2;
	}

	public Double getPotenciaPonto1() {
		return potenciaPonto1;
	}

	public void setPotenciaPonto1(Double potenciaPonto1) {
		this.potenciaPonto1 = potenciaPonto1;
	}

	public Double getDemandaPonto1() {
		return demandaPonto1;
	}

	public void setDemandaPonto1(Double demandaPonto1) {
		this.demandaPonto1 = demandaPonto1;
	}

	public Double getPotenciaPonto2() {
		return potenciaPonto2;
	}

	public void setPotenciaPonto2(Double potenciaPonto2) {
		this.potenciaPonto2 = potenciaPonto2;
	}

	public Double getDemandaPonto2() {
		return demandaPonto2;
	}

	public void setDemandaPonto2(Double demandaPonto2) {
		this.demandaPonto2 = demandaPonto2;
	}

	public Double getComprimentoEletroduto() {
		return comprimentoEletroduto;
	}

	public void setComprimentoEletroduto(Double comprimentoEletroduto) {
		this.comprimentoEletroduto = comprimentoEletroduto;
	}

}
