/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BorderStrokeVO.java
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

import java.awt.Stroke;

import javafx.scene.layout.BorderStrokeStyle;

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
