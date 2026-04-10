/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MidLineSegment2dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/01/2026
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

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;

public class MidLineSegment2dVO 
{
//Private
	private GeomPoint2d ptI = null;
	private GeomPoint2d ptF = null;
	private double length = 0.0;
	//
	private GeomPoint2d ptMid = null;
	private double midLength = 0.0;
	
//Public

	public MidLineSegment2dVO(GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		this.init(ptI2d, ptF2d);
	}
	
	public MidLineSegment2dVO(GeomPoint3d ptI3d, GeomPoint3d ptF3d)
	{
		this.init(ptI3d, ptF3d);
	}
	
	/* Methodes */
	
	public void init(GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		this.ptI = new GeomPoint2d(ptI2d);
		this.ptF = new GeomPoint2d(ptF2d);
		this.length = this.ptI.distTo(this.ptF);
		
		this.ptMid = GeomUtil.midPointOf(this.ptI, this.ptF);
		this.midLength = this.ptI.distTo(this.ptMid);
	}
	
	public void init(GeomPoint3d ptI3d, GeomPoint3d ptF3d)
	{
		this.init(ptI3d, ptF3d);;
	}
	
	/* Getters/Setters */

	public GeomPoint2d getPtI() {
		return this.ptI;
	}

	public GeomPoint2d getPtF() {
		return this.ptF;
	}

	public GeomPoint2d getPtMid() {
		return this.ptMid;
	}

	public double getLength() {
		return length;
	}

	public double getMidLength() {
		return midLength;
	}
		
}
