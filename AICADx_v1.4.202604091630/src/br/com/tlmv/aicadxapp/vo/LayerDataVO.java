/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * LayerDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class LayerDataVO 
{
//Private
	private String layer;
	private String ltype;
	private String color;
	private String lweight;
	
//Public
	
	/* Methodes */
	
	public void init(
		String layer,
		String ltype,
		String color,
		String lweight)
	{
		this.layer = layer;
		this.ltype = ltype;
		this.color = color;
		this.lweight = lweight;
	}
	
	/* TO/FROM DATA */
	
	public void fromStr(String str)
	{
		this.layer = str.substring(0, 35);		//POSICAO: [ 1, 35 + 1]
		this.ltype = str.substring(36, 51);		//POSICAO: [37, 51 + 1]
		this.color = str.substring(52, 67);		//POSICAO: [53, 67 + 1]
		this.lweight = str.substring(68);		//POSICAO: [69, 83 + 1]
	}

	public String toStr()
	{
		String str = String.format(
			"Layer:%s;Ltype:%s;Color:%s;Lweight:%s", 
			this.layer,
			this.ltype,
			this.color,
			this.lweight);
		return str;
	}

	/* DEBUG */
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String warnmsg = this.toStr();
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* Getters/Setters */
	
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getLtype() {
		return ltype;
	}
	public void setLtype(String ltype) {
		this.ltype = ltype;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getLweight() {
		return lweight;
	}
	public void setLweight(String lweight) {
		this.lweight = lweight;
	}

	public Color getColorRGB() {
		Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
		
		String[] arr = StringUtil.split(this.color, ',');
		if(arr.length >= 3) {
			int r = StringUtil.safeInt(arr[0]);
			int g = StringUtil.safeInt(arr[1]);
			int b = StringUtil.safeInt(arr[2]);
			
			c = new Color(r, g, b);
		}
		else {
			int cad_color = StringUtil.safeInt(arr[0]);
			
			if(cad_color < AppDefs.ARR_COLORINDEX_TABLE.length)
				c = AppDefs.ARR_COLORINDEX_TABLE[cad_color];
		}
		return c;
	}
	
}
