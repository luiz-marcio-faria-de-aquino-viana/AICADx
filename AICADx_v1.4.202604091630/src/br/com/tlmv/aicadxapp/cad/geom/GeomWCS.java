/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GeomUCS.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/03/2025
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

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class GeomWCS 
{
//Private
	private GeomPoint3d ptOriginMcs;
	//
	private GeomVector3d axisX;
	private GeomVector3d axisY;
	private GeomVector3d axisZ;
	
//Public

	public GeomWCS()
	{
		this.init();
	}
		
	/* Methodes */
	
	public void init()
	{
		this.ptOriginMcs = new GeomPoint3d(0.0, 0.0, 0.0);
		//
		this.axisX = new GeomVector3d(1.0, 0.0, 0.0);
		this.axisY = new GeomVector3d(0.0, 1.0, 0.0);
		this.axisZ = new GeomVector3d(0.0, 0.0, 1.0);		
	}

	/* OPERATIONS */
	
	public double distTo(GeomPoint3d ptMcs)
	{
		double d = this.ptOriginMcs.distTo(ptMcs);
		return d;
	}

	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"Origin:[ %s, %s, %s ];" +
			"Axis-X:[ %s, %s, %s ];" +
			"Axis-Y:[ %s, %s, %s ];" +
			"Axis-Z:[ %s, %s, %s ];", 
			nf3.format(this.ptOriginMcs.getX()),
			nf3.format(this.ptOriginMcs.getY()),
			nf3.format(this.ptOriginMcs.getZ()),
			nf3.format(this.axisX.getXOrig()),
			nf3.format(this.axisX.getYOrig()),
			nf3.format(this.axisX.getZOrig()),
			nf3.format(this.axisY.getXOrig()),
			nf3.format(this.axisY.getYOrig()),
			nf3.format(this.axisY.getZOrig()),
			nf3.format(this.axisZ.getXOrig()),
			nf3.format(this.axisZ.getYOrig()),
			nf3.format(this.axisZ.getZOrig()) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtOriginMcs() {
		return this.ptOriginMcs;
	}

	public GeomVector3d getAxisX() {
		return axisX;
	}

	public GeomVector3d getAxisY() {
		return axisY;
	}

	public GeomVector3d getAxisZ() {
		return axisZ;
	}
	
}
