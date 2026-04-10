/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TubulacaoDrenagemVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxmod.drenagem.vo;

public class TubulacaoDrenagemVO 
{
//Private
	private int oid;
	private int categoriaTubulacaoId;
	private String descricaoCategoriaTubulacao;
	private String descricao;
	private double diamNominalMeter;
	private double diamInternoMeter;
	private double diamExternoMeter;
	private double espessuraTubulacaoMeter;
	private double profMinimaSemLajeMeter;
	private double recobrMinimoSemLajeMeter;
	private double profMinimaComLajeMeter;
	private double recobrMinimoComLajeMeter;
	
//Public
	
	public TubulacaoDrenagemVO(
		int oid,
		int categoriaTubulacaoId,
		String descricaoCategoriaTubulacao,
		String descricao,
		double diamNominalMeter,
		double diamInternoMeter,
		double diamExternoMeter,
		double espessuraTubulacaoMeter,
		double profMinimaSemLajeMeter,
		double recobrMinimoSemLajeMeter,
		double profMinimaComLajeMeter,
		double recobrMinimoComLajeMeter)
	{
		this.oid = oid;
		this.categoriaTubulacaoId = categoriaTubulacaoId;
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
		this.descricao = descricao;
		this.diamNominalMeter = diamNominalMeter;
		this.diamInternoMeter = diamInternoMeter;
		this.diamExternoMeter = diamExternoMeter;
		this.espessuraTubulacaoMeter = espessuraTubulacaoMeter;
		this.profMinimaSemLajeMeter = profMinimaSemLajeMeter;
		this.recobrMinimoSemLajeMeter = recobrMinimoSemLajeMeter;
		this.profMinimaComLajeMeter = profMinimaComLajeMeter;
		this.recobrMinimoComLajeMeter = recobrMinimoComLajeMeter;
	}
	
	public TubulacaoDrenagemVO(TubulacaoDrenagemVO o)
	{
		this.oid = o.getOid();
		this.categoriaTubulacaoId = o.getCategoriaTubulacaoId();
		this.descricaoCategoriaTubulacao = o.getDescricaoCategoriaTubulacao();
		this.descricao = o.getDescricao();
		this.diamNominalMeter = o.getDiamNominalMeter();
		this.diamInternoMeter = o.getDiamInternoMeter();
		this.diamExternoMeter = o.getDiamExternoMeter();
		this.espessuraTubulacaoMeter = o.getEspessuraTubulacaoMeter();
		this.profMinimaSemLajeMeter = o.getProfMinimaSemLajeMeter();
		this.recobrMinimoSemLajeMeter = o.getRecobrMinimoSemLajeMeter();
		this.profMinimaComLajeMeter = o.getProfMinimaComLajeMeter();
		this.recobrMinimoComLajeMeter = o.getRecobrMinimoComLajeMeter();
	}

	/* Methodes */
	
	public String toString()
	{
		return this.descricao;
	}
	
	/* Getters/Setters */
		
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getCategoriaTubulacaoId() {
		return categoriaTubulacaoId;
	}

	public void setCategoriaTubulacaoId(int categoriaTubulacaoId) {
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	}

	public String getDescricaoCategoriaTubulacao() {
		return descricaoCategoriaTubulacao;
	}

	public void setDescricaoCategoriaTubulacao(String descricaoCategoriaTubulacao) {
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	}

	public double getDiamNominalMeter() {
		return diamNominalMeter;
	}

	public void setDiamNominalMeter(double diamNominalMeter) {
		this.diamNominalMeter = diamNominalMeter;
	}

	public double getDiamInternoMeter() {
		return diamInternoMeter;
	}

	public void setDiamInternoMeter(double diamInternoMeter) {
		this.diamInternoMeter = diamInternoMeter;
	}

	public double getDiamExternoMeter() {
		return diamExternoMeter;
	}

	public void setDiamExternoMeter(double diamExternoMeter) {
		this.diamExternoMeter = diamExternoMeter;
	}

	public double getEspessuraTubulacaoMeter() {
		return espessuraTubulacaoMeter;
	}

	public void setEspessuraTubulacaoMeter(double espessuraTubulacaoMeter) {
		this.espessuraTubulacaoMeter = espessuraTubulacaoMeter;
	}

	public double getProfMinimaSemLajeMeter() {
		return profMinimaSemLajeMeter;
	}

	public void setProfMinimaSemLajeMeter(double profMinimaSemLajeMeter) {
		this.profMinimaSemLajeMeter = profMinimaSemLajeMeter;
	}

	public double getRecobrMinimoSemLajeMeter() {
		return recobrMinimoSemLajeMeter;
	}

	public void setRecobrMinimoSemLajeMeter(double recobrMinimoSemLajeMeter) {
		this.recobrMinimoSemLajeMeter = recobrMinimoSemLajeMeter;
	}

	public double getProfMinimaComLajeMeter() {
		return profMinimaComLajeMeter;
	}

	public void setProfMinimaComLajeMeter(double profMinimaComLajeMeter) {
		this.profMinimaComLajeMeter = profMinimaComLajeMeter;
	}

	public double getRecobrMinimoComLajeMeter() {
		return recobrMinimoComLajeMeter;
	}

	public void setRecobrMinimoComLajeMeter(double recobrMinimoComLajeMeter) {
		this.recobrMinimoComLajeMeter = recobrMinimoComLajeMeter;
	}
	
}
