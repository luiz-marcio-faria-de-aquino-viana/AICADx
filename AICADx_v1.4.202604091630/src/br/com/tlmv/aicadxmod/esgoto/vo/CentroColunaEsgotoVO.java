/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CentroColunaEsgotoVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/12/2025
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

package br.com.tlmv.aicadxmod.esgoto.vo;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class CentroColunaEsgotoVO 
{
//Private
	private GeomPoint2d ptI = null;
	private GeomPoint2d ptF = null;
	private Double centerDist = null;
	
//Public

	public CentroColunaEsgotoVO(
		GeomPoint2d ptI,
		GeomPoint2d ptF,
		Double centerDist)
	{
		this.init(ptI, ptF, centerDist);
	}	
	
	/* Methodes */
	
	public void init(
		GeomPoint2d ptI,
		GeomPoint2d ptF,
		Double centerDist)
	{
		this.ptI = new GeomPoint2d(ptI);
		this.ptF = new GeomPoint2d(ptF);
		this.centerDist = centerDist;
	}

	/* Getters/Setters */
	
	public GeomPoint2d getPtI() {
		return ptI;
	}

	public void setPtI(GeomPoint2d ptI) {
		this.ptI = ptI;
	}

	public GeomPoint2d getPtF() {
		return ptF;
	}

	public void setPtF(GeomPoint2d ptF) {
		this.ptF = ptF;
	}

	public Double getCenterDist() {
		return centerDist;
	}

	public void setCenterDist(Double centerDist) {
		this.centerDist = centerDist;
	}
	
}
