/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * NearPoint3dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
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
	private GeomPoint3d ptEntCentroid3d = null;
	private double distEnt = 0.0;
	private GeomPoint3d ptFaceCentroid3d = null;
	private double distFace = 0.0;
	
//Public
	
	public NearPoint3dVO(
		GeomPoint3d ptEntCentroid3d,
		double distEnt,
		GeomPoint3d ptFaceCentroid3d,
		double distFace)
	{
		this.ptEntCentroid3d = new GeomPoint3d(ptEntCentroid3d);
		this.distEnt = distEnt;
		this.ptFaceCentroid3d = new GeomPoint3d(ptFaceCentroid3d);
		this.distFace = distFace;
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtEntCentroid3d() {
		return ptEntCentroid3d;
	}

	public void setPtEntCentroid3d(GeomPoint3d ptEntCentroid3d) {
		this.ptEntCentroid3d = ptEntCentroid3d;
	}

	public double getDistEnt() {
		return distEnt;
	}

	public void setDistEnt(double distEnt) {
		this.distEnt = distEnt;
	}

	public GeomPoint3d getPtFaceCentroid3d() {
		return ptFaceCentroid3d;
	}

	public void setPtFaceCentroid3d(GeomPoint3d ptFaceCentroid3d) {
		this.ptFaceCentroid3d = ptFaceCentroid3d;
	}

	public double getDistFace() {
		return distFace;
	}

	public void setDistFace(double distFace) {
		this.distFace = distFace;
	}
	
}
