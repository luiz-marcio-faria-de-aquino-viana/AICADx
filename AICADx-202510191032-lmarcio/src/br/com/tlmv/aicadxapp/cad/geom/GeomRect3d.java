/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomRect3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

public class GeomRect3d 
{
//Private
	
	//PLAN_ORIGIN
	//
	private GeomPoint3d ptMin;
	private GeomPoint3d ptMax;

//Public
	
	public GeomRect3d(GeomPoint3d ptMin, GeomPoint3d ptMax)
	{
		this.init(ptMin, ptMax);
	}
	
	public GeomRect3d(GeomRect3d rect)
	{
		this.init(rect.getPtMin(), rect.getPtMax());
	}
	
	public GeomRect3d(GeomRect2d rect)
	{
		GeomPoint3d ptMin3d = new GeomPoint3d(rect.getPtMin());
		GeomPoint3d ptMax3d = new GeomPoint3d(rect.getPtMax());
		
		this.init(ptMin3d, ptMax3d);
	}
	
	/* Methodes */
	
	public void init(GeomPoint3d ptMin, GeomPoint3d ptMax)
	{
		this.ptMin = new GeomPoint3d(ptMin);
		this.ptMax = new GeomPoint3d(ptMax);
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtMin() {
		return ptMin;
	}

	public GeomPoint3d getPtMax() {
		return ptMax;
	}

}
