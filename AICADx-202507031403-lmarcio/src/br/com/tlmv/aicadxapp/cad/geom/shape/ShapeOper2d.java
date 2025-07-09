/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ShapeOper2d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom.shape;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class ShapeOper2d 
{
//Private
	private boolean bDrawLine = false;
	private GeomPoint2d pt2d = null;
	
//Public
	
	public ShapeOper2d(boolean bDrawLine, GeomPoint2d pt2d)
	{
		this.init(bDrawLine, pt2d);
	}
	
	public ShapeOper2d(boolean bDrawLine, double xp, double yp)
	{
		this.init(bDrawLine, xp, yp);
	}
	
	public ShapeOper2d(ShapeOper2d other)
	{
		this.init(other.isDrawLine(), other.getPt2d());
	}

	/* Methodes */
	
	public void init(boolean bDrawLine, GeomPoint2d pt2d)
	{
		this.bDrawLine = bDrawLine;
		this.pt2d = new GeomPoint2d(pt2d);
	}
	
	public void init(boolean bDrawLine, double xp, double yp)
	{
		this.bDrawLine = bDrawLine;
		this.pt2d = new GeomPoint2d(xp, yp);
	}

	/* Getters/Setters */

	public GeomPoint2d getPt2d() {
		return pt2d;
	}

	public void setPt2d(GeomPoint2d pt2d) {
		this.pt2d = new GeomPoint2d(pt2d);
	}

	public boolean isDrawLine() {
		return bDrawLine;
	}

	public void setDrawLine(boolean bDrawLine) {
		this.bDrawLine = bDrawLine;
	}
	
}
