/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomRect2dVO.java
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

public class GeomRect2d 
{
//Private
	
	//PLAN_ORIGIN
	//
	private GeomPoint2d ptMin;
	private GeomPoint2d ptMax;

//Public
	
	public GeomRect2d(double xMin, double yMin, double xMax, double yMax)
	{
		this.init(xMin, yMin, xMax, yMax);
	}
	
	public GeomRect2d(GeomPoint2d ptMin, GeomPoint2d ptMax)
	{
		this.init(ptMin, ptMax);
	}
	
	public GeomRect2d(GeomRect2d rect)
	{
		this.init(rect.getPtMin(), rect.getPtMax());
	}
	
	public GeomRect2d(GeomRect3d rect)
	{
		GeomPoint2d ptMin2d = new GeomPoint2d(rect.getPtMin());
		GeomPoint2d ptMax2d = new GeomPoint2d(rect.getPtMax());
		
		this.init(ptMin2d, ptMax2d);
	}
	
	/* Methodes */
	
	public void init(double xMin, double yMin, double xMax, double yMax)
	{
		this.ptMin = new GeomPoint2d(xMin, yMin);
		this.ptMax = new GeomPoint2d(xMax, yMax);
	}
	
	public void init(GeomPoint2d ptMin, GeomPoint2d ptMax)
	{
		this.ptMin = new GeomPoint2d(ptMin);
		this.ptMax = new GeomPoint2d(ptMax);
	}
	
	/* Getters/Setters */

	public GeomPoint2d getPtMin() {
		return ptMin;
	}

	public GeomPoint2d getPtMax() {
		return ptMax;
	}

}
