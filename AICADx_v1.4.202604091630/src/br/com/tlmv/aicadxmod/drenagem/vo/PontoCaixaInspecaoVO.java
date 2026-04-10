/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PontoCaixaInspecaoVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 01/06/2025
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

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class PontoCaixaInspecaoVO 
{
//Private
	private GeomPoint2d ptCotaTerreno = null;
	private GeomPoint2d ptFundo = null;
	private GeomPoint2d ptCotaEntrada = null;
	private GeomPoint2d ptCotaSaida = null;
	private double dDeclividade = 0.0;
	private double dDiametro = 0.0;
	private double dComprimento = 0.0;

//Public
	
	public PontoCaixaInspecaoVO(
		GeomPoint2d ptCotaTerreno,
		GeomPoint2d ptFundo,
		GeomPoint2d ptCotaEntrada,
		GeomPoint2d ptCotaSaida,
		double dDeclividade,
		double dDiametro,
		double dComprimento)
	{
		init(ptCotaTerreno, ptFundo, ptCotaEntrada, ptCotaSaida, dDeclividade, dDiametro, dComprimento);
	}
	
	/* Methodes */

	public void init(
		GeomPoint2d ptCotaTerreno,
		GeomPoint2d ptFundo,
		GeomPoint2d ptCotaEntrada,
		GeomPoint2d ptCotaSaida,
		double dDeclividade,
		double dDiametro,
		double dComprimento)
	{
		this.ptCotaTerreno = new GeomPoint2d(ptCotaTerreno);
		this.ptFundo = new GeomPoint2d(ptFundo);
		this.ptCotaEntrada = new GeomPoint2d(ptCotaEntrada);
		this.ptCotaSaida = new GeomPoint2d(ptCotaSaida);
		this.dDeclividade = dDeclividade;
		this.dDiametro = dDiametro;
		this.dComprimento = dComprimento;
	}
	
	/* Getters/Setters */

	public GeomPoint2d getPtCotaTerreno() {
		return ptCotaTerreno;
	}

	public void setPtCotaTerreno(GeomPoint2d ptCotaTerreno) {
		this.ptCotaTerreno = ptCotaTerreno;
	}

	public GeomPoint2d getPtFundo() {
		return ptFundo;
	}

	public void setPtFundo(GeomPoint2d ptFundo) {
		this.ptFundo = ptFundo;
	}

	public GeomPoint2d getPtCotaEntrada() {
		return ptCotaEntrada;
	}

	public void setPtCotaEntrada(GeomPoint2d ptCotaEntrada) {
		this.ptCotaEntrada = ptCotaEntrada;
	}

	public GeomPoint2d getPtCotaSaida() {
		return ptCotaSaida;
	}

	public void setPtCotaSaida(GeomPoint2d ptCotaSaida) {
		this.ptCotaSaida = ptCotaSaida;
	}

	public double getDeclividade() {
		return dDeclividade;
	}

	public void setDeclividade(double dDeclividade) {
		this.dDeclividade = dDeclividade;
	}

	public double getDiametro() {
		return dDiametro;
	}

	public void setDiametro(double dDiametro) {
		this.dDiametro = dDiametro;
	}

	public double getComprimento() {
		return dComprimento;
	}

	public void setComprimento(double dComprimento) {
		this.dComprimento = dComprimento;
	}
	
}
