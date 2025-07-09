/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerExplorerResultVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 01/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

public class LayerExplorerResultVO 
{
//Private
	private Boolean bActive;
	private String name;
	private BorderStrokeVO oLtype;
	private ColorVO oColor;
	private Boolean bLayerOnOff;
	
//Public
	
	public LayerExplorerResultVO(
		Boolean bActive,
		String name,
		BorderStrokeVO oLtype,
		ColorVO oColor,
		Boolean bLayerOnOff)
	{
		this.init(
			bActive,
			name,
			oLtype,
			oColor,
			bLayerOnOff);
	}
	
	/* Methodes */
	
	public void init(
		Boolean bActive,
		String name,
		BorderStrokeVO oLtype,
		ColorVO oColor,
		Boolean layerOnOff)
	{
		this.bActive = bActive;
		this.name = name;
		this.oLtype = oLtype;
		this.oColor = oColor;
		this.bLayerOnOff = layerOnOff;
	}

	/* Getters/Setters */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BorderStrokeVO getLtype() {
		return oLtype;
	}

	public void setLtype(BorderStrokeVO oLtype) {
		this.oLtype = oLtype;
	}

	public Boolean getActive() {
		return bActive;
	}

	public void setActive(Boolean bActive) {
		this.bActive = bActive;
	}

	public Boolean getLayerOnOff() {
		return bLayerOnOff;
	}

	public void setLayerOnOff(Boolean bLayerOnOff) {
		this.bLayerOnOff = bLayerOnOff;
	}

	public ColorVO getColor() {
		return oColor;
	}

	public void setColor(ColorVO oColor) {
		this.oColor = oColor;
	}
	
}
