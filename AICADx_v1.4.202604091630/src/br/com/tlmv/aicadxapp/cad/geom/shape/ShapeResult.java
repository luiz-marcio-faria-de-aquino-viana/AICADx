/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ShapeResult.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/09/2025
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

package br.com.tlmv.aicadxapp.cad.geom.shape;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class ShapeResult 
{
//Private
	private ArrayList<ShapeOper2d> lsShapeOper2d = null;
	private GeomPoint2d ptBase2d = null;
	private GeomPoint2d ptFinal2d = null;
	
//Public
	
	public ShapeResult(
		ArrayList<ShapeOper2d> lsShapeOper2d,
		GeomPoint2d ptBase2d,
		GeomPoint2d ptFinal2d) 
	{
		this.init(
			lsShapeOper2d, 
			ptBase2d, 
			ptFinal2d);
	}

	/* Methodes */
	
	public void init(
		ArrayList<ShapeOper2d> lsShapeOper2d,
		GeomPoint2d ptBase2d,
		GeomPoint2d ptFinal2d) 
	{
		this.lsShapeOper2d = lsShapeOper2d;
		this.ptBase2d = new GeomPoint2d(ptBase2d);
		this.ptFinal2d = new GeomPoint2d(ptFinal2d); 		
	}

	/* Getters/Setters */
	
	public ArrayList<ShapeOper2d> getLsShapeOper2d() {
		return lsShapeOper2d;
	}

	public void setLsShapeOper2d(ArrayList<ShapeOper2d> lsShapeOper2d) {
		this.lsShapeOper2d = lsShapeOper2d;
	}

	public GeomPoint2d getPtBase2d() {
		return ptBase2d;
	}

	public void setPtBase2d(GeomPoint2d ptBase2d) {
		this.ptBase2d = ptBase2d;
	}

	public GeomPoint2d getPtFinal2d() {
		return ptFinal2d;
	}

	public void setPtFinal2d(GeomPoint2d ptFinal2d) {
		this.ptFinal2d = ptFinal2d;
	}

}
