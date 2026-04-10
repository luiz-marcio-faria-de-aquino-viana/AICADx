/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BorderStrokeVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
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

import java.awt.BasicStroke;
import java.awt.Stroke;

public class BorderStrokeVO 
{
//Private	
	private int borderId;
	private String name;
	private Stroke ltype;
	
//Public

	public BorderStrokeVO()
	{
		this.init(
			-1,
			"",
			null);
	}
	
	public BorderStrokeVO(
		int borderId,
		String name,
		Stroke ltype)
	{
		this.init(
			borderId,
			name,
			ltype);
	}
	
	/* Methodes */

	public void init(
		int borderId,
		String name,
		Stroke ltype)
	{
		this.borderId = borderId;
		this.name = name;
		this.ltype = ltype;
	}

	public BorderStrokeVO duplicate(double lineWidth)
	{
		int rnd = (int)Math.floor(Math.random() * 1000.0);

		int borderId = this.borderId * 1000 + rnd;
		String name = this.name + Integer.toString(rnd);		
		
		BasicStroke bs = (BasicStroke)this.getLtype(); 
		BasicStroke ltype = new BasicStroke(
			(float)lineWidth, 
			bs.getEndCap(), 
			bs.getLineJoin(),
			bs.getMiterLimit(),
			bs.getDashArray(),
			bs.getDashPhase() );
		
		BorderStrokeVO other = new BorderStrokeVO(borderId, name, ltype);
		return other;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	/* Getters/Setters */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stroke getLtype() {
		return ltype;
	}

	public void setLtype(Stroke ltype) {
		this.ltype = ltype;
	}

	public int getBorderId() {
		return borderId;
	}

	public void setBorderId(int borderId) {
		this.borderId = borderId;
	}
	
}
