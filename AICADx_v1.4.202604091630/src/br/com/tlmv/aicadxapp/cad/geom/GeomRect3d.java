/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GeomRect3d.java
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

public class GeomRect3d 
{
//Private
	
	//PLAN_ORIGIN
	//
	private GeomPoint3d ptMin;
	private GeomPoint3d ptMax;

//Public
	
	public GeomRect3d(GeomPoint3d ptMin, GeomPoint3d ptMax)
	{
		this.init(ptMin, ptMax);
	}
	
	public GeomRect3d(GeomRect3d rect)
	{
		this.init(rect.getPtMin(), rect.getPtMax());
	}
	
	public GeomRect3d(GeomRect2d rect)
	{
		GeomPoint3d ptMin3d = new GeomPoint3d(rect.getPtMin());
		GeomPoint3d ptMax3d = new GeomPoint3d(rect.getPtMax());
		
		this.init(ptMin3d, ptMax3d);
	}
	
	/* Methodes */
	
	public void init(GeomPoint3d ptMin, GeomPoint3d ptMax)
	{
		this.ptMin = new GeomPoint3d(ptMin);
		this.ptMax = new GeomPoint3d(ptMax);
	}
	
	public boolean compareTo(GeomRect3d other)
	{
		if( ( ptMin.distTo( other.getPtMin() ) < AppDefs.MATHPREC_MIN ) &&
			( ptMax.distTo( other.getPtMax() ) < AppDefs.MATHPREC_MIN ) ) {
			return true;
		}
		return false;
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtMin() {
		return ptMin;
	}

	public GeomPoint3d getPtMax() {
		return ptMax;
	}

}
