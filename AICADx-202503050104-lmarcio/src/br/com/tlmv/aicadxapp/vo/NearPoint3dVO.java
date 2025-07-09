/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * NearPoint3dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;

public class NearPoint3dVO 
{
//Private
	private GeomPoint3d ptNear3d = null;
	private double dist = 0.0;
	
//Public
	
	public NearPoint3dVO(
		GeomPoint3d ptNear3d,
		double dist)
	{
		this.ptNear3d = new GeomPoint3d(ptNear3d);
		this.dist = dist;
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtNear3d() {
		return ptNear3d;
	}

	public void setPtNear3d(GeomPoint3d ptNear3d) {
		this.ptNear3d = ptNear3d;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}
	
}
