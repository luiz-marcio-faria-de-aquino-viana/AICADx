/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GeomRect2dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/02/2025
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

import br.com.tlmv.aicadxapp.AppDefs;

public class GeomRect2d 
{
//Private
	
	//PLAN_ORIGIN
	//
	private GeomPoint2d ptMin;
	private GeomPoint2d ptMax;

//Public
	
	public GeomRect2d(double xMin, double yMin, double xMax, double yMax)
	{
		this.init(xMin, yMin, xMax, yMax);
	}
	
	public GeomRect2d(GeomPoint2d ptMin, GeomPoint2d ptMax)
	{
		this.init(ptMin, ptMax);
	}
	
	public GeomRect2d(GeomRect2d rect)
	{
		this.init(rect.getPtMin(), rect.getPtMax());
	}
	
	public GeomRect2d(GeomRect3d rect)
	{
		GeomPoint2d ptMin2d = new GeomPoint2d(rect.getPtMin());
		GeomPoint2d ptMax2d = new GeomPoint2d(rect.getPtMax());
		
		this.init(ptMin2d, ptMax2d);
	}
	
	/* Methodes */
	
	public void init(double xMin, double yMin, double xMax, double yMax)
	{
		this.ptMin = new GeomPoint2d(xMin, yMin);
		this.ptMax = new GeomPoint2d(xMax, yMax);
	}
	
	public void init(GeomPoint2d ptMin, GeomPoint2d ptMax)
	{
		this.ptMin = new GeomPoint2d(ptMin);
		this.ptMax = new GeomPoint2d(ptMax);
	}
	
	public boolean compareTo(GeomRect2d other)
	{
		if( ( ptMin.distTo( other.getPtMin() ) < AppDefs.MATHPREC_MIN ) &&
			( ptMax.distTo( other.getPtMax() ) < AppDefs.MATHPREC_MIN ) ) {
			return true;
		}
		return false;
	}
	
	/* Getters/Setters */

	public GeomPoint2d getPtMin() {
		return ptMin;
	}

	public GeomPoint2d getPtMax() {
		return ptMax;
	}

}
