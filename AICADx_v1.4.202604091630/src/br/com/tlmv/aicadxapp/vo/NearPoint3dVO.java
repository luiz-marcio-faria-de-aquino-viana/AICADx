/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * NearPoint3dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
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

package br.com.tlmv.aicadxapp.vo;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;

public class NearPoint3dVO 
{
//Private
	private GeomPoint3d ptEntCentroid3d = null;
	private double distEnt = 0.0;
	private GeomPoint3d ptFaceCentroid3d = null;
	private double distFace = 0.0;
	
//Public
	
	public NearPoint3dVO(
		GeomPoint3d ptEntCentroid3d,
		double distEnt,
		GeomPoint3d ptFaceCentroid3d,
		double distFace)
	{
		this.ptEntCentroid3d = new GeomPoint3d(ptEntCentroid3d);
		this.distEnt = distEnt;
		this.ptFaceCentroid3d = new GeomPoint3d(ptFaceCentroid3d);
		this.distFace = distFace;
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtEntCentroid3d() {
		return ptEntCentroid3d;
	}

	public void setPtEntCentroid3d(GeomPoint3d ptEntCentroid3d) {
		this.ptEntCentroid3d = ptEntCentroid3d;
	}

	public double getDistEnt() {
		return distEnt;
	}

	public void setDistEnt(double distEnt) {
		this.distEnt = distEnt;
	}

	public GeomPoint3d getPtFaceCentroid3d() {
		return ptFaceCentroid3d;
	}

	public void setPtFaceCentroid3d(GeomPoint3d ptFaceCentroid3d) {
		this.ptFaceCentroid3d = ptFaceCentroid3d;
	}

	public double getDistFace() {
		return distFace;
	}

	public void setDistFace(double distFace) {
		this.distFace = distFace;
	}
	
}
