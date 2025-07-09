/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ColorVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import java.awt.Color;

import br.com.tlmv.aicadxapp.AppDefs;
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
