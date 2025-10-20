/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ShapeResult.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/09/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom.shape;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class ShapeResult 
{
//Private
	private ArrayList<ShapeOper2d> lsShapeOper2d = null;
	private GeomPoint2d ptBase2d = null;
	private GeomPoint2d ptFinal2d = null;
	
//Public
	
	public ShapeResult(
		ArrayList<ShapeOper2d> lsShapeOper2d,
		GeomPoint2d ptBase2d,
		GeomPoint2d ptFinal2d) 
	{
		this.init(
			lsShapeOper2d, 
			ptBase2d, 
			ptFinal2d);
	}

	/* Methodes */
	
	public void init(
		ArrayList<ShapeOper2d> lsShapeOper2d,
		GeomPoint2d ptBase2d,
		GeomPoint2d ptFinal2d) 
	{
		this.lsShapeOper2d = lsShapeOper2d;
		this.ptBase2d = new GeomPoint2d(ptBase2d);
		this.ptFinal2d = new GeomPoint2d(ptFinal2d); 		
	}

	/* Getters/Setters */
	
	public ArrayList<ShapeOper2d> getLsShapeOper2d() {
		return lsShapeOper2d;
	}

	public void setLsShapeOper2d(ArrayList<ShapeOper2d> lsShapeOper2d) {
		this.lsShapeOper2d = lsShapeOper2d;
	}

	public GeomPoint2d getPtBase2d() {
		return ptBase2d;
	}

	public void setPtBase2d(GeomPoint2d ptBase2d) {
		this.ptBase2d = ptBase2d;
	}

	public GeomPoint2d getPtFinal2d() {
		return ptFinal2d;
	}

	public void setPtFinal2d(GeomPoint2d ptFinal2d) {
		this.ptFinal2d = ptFinal2d;
	}

}
