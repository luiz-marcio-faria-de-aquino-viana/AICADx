/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
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
