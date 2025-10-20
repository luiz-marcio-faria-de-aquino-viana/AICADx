/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.vo;

import java.awt.BasicStroke;
import java.awt.Stroke;

import br.com.tlmv.aicadxapp.AppDefs;

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
