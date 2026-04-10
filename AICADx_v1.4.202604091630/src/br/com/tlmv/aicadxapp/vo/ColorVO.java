/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ColorVO.java
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

import java.awt.Color;

import br.com.tlmv.aicadxapp.utils.StyleUtil;

public class ColorVO 
{
//Private	
	private String name;
	private int colorIndex;
	private int[] rgb;
	private Color color;
	
//Public

	public ColorVO() 
	{
		this.init();
	}

	public ColorVO(		
		String name,
		int colorIndex) 
	{
		this.init(name, colorIndex);
	}

	public ColorVO(		
		String name,
		int r, int g, int b)
	{
		this.init(name, r, g, b);
	}

	public ColorVO(Color c)
	{
		this.init(c);
	}

	/* Methodes */

	public void init() 
	{
		this.name = "";
		this.colorIndex = -1;
		this.rgb = new int[3];
		this.rgb[0] = -1;
		this.rgb[1] = -1;
		this.rgb[2] = -1;
		this.color = null;
	}

	public void init(
		String name,
		int colorIndex)
	{
		this.name = name;
		this.colorIndex = StyleUtil.getColorIndex(colorIndex);
		this.color = StyleUtil.getColor(this.colorIndex);
	}

	public void init(
		String name,
		int r, int g, int b) 
	{
		this.name = name;
		this.rgb = new int[3];
		this.rgb[0] = r;
		this.rgb[1] = g;
		this.rgb[2] = b;
		this.color = new Color(r, g, b);
	}

	public void init(Color c)
	{
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		String name = StyleUtil.getColorName(r, g, b);
		this.init(name, r, g, b); 
	}
	
	/* Getters/Setters */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	public int[] getRgb() {
		return rgb;
	}

	public void setRgb(int[] rgb) {
		this.rgb = rgb;
	}
		
}
