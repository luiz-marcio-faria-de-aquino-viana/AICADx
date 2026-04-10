/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PipeLineSelVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/12/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class PipeLineSelVO 
{
//Private
	//Inputs
	private GeomPoint2d pt02d = null;	
	private GeomPoint2d ptI2d = null;	
	private GeomPoint2d ptF2d = null;	
	
	//Outputs
	private int result = AppDefs.pipeCurveNone;
	private GeomPoint2d ptRef2d = null;	
	
//Public
	
	public PipeLineSelVO(
		GeomPoint2d pt02d,	
		GeomPoint2d ptI2d,	
		GeomPoint2d ptF2d)
	{
		this.init(
			pt02d,	
			ptI2d,	
			ptF2d);
	}

	/* Methodes */

	public void init(
		GeomPoint2d pt02d,	
		GeomPoint2d ptI2d,	
		GeomPoint2d ptF2d)
	{
		//Inputs
		this.pt02d = new GeomPoint2d( pt02d );	
		this.ptI2d = new GeomPoint2d( ptI2d );	
		this.ptF2d = new GeomPoint2d( ptF2d );	

		//Outputs
		this.result = AppDefs.pipeCurveNone;
		this.ptRef2d = null;	
	}
	
	public void setResult(int result, GeomPoint2d ptRef2d)
	{
		this.result = result;
		this.ptRef2d = new GeomPoint2d( ptRef2d ); 
	}
	
	/* DEBUG */
	
	public String toString()
	{
		String str = String.format(
			"Result: %s; ", 
		 	Integer.toString( this.result ) );		
		return str;
	}
	
	/* Getters/Setters */

	public GeomPoint2d getPt02d() {
		return pt02d;
	}

	public void setPt02d(GeomPoint2d pt02d) {
		this.pt02d = pt02d;
	}

	public GeomPoint2d getPtI2d() {
		return ptI2d;
	}

	public void setPtI2d(GeomPoint2d ptI2d) {
		this.ptI2d = ptI2d;
	}

	public GeomPoint2d getPtF2d() {
		return ptF2d;
	}

	public void setPtF2d(GeomPoint2d ptF2d) {
		this.ptF2d = ptF2d;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public GeomPoint2d getPtRef2d() {
		return ptRef2d;
	}

	public void setPtRef2d(GeomPoint2d ptRef2d) {
		this.ptRef2d = ptRef2d;
	}	
	
}
