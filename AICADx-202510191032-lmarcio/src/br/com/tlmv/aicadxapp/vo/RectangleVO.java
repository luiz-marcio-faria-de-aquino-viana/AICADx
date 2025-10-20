/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * RectangleVO.java
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

import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;

public class RectangleVO 
{
//Private
	private GeomPoint3d ptMin3d = null;
	private GeomPoint3d ptMax3d = null;
	//
	private GeomPoint3d ptCentroid = null;
	
	private double width = 0.0;
	private double height = 0.0;
	private double zHeight = 0.0;
	
//Public

	public RectangleVO(GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		this.init(ptI2d, ptF2d);
	}
	
	public RectangleVO(GeomPoint3d ptI3d, GeomPoint3d ptF3d)
	{
		this.init(ptI3d, ptF3d);
	}
	
	/* Methodes */
	
	public void init(GeomPoint2d ptI2d, GeomPoint2d ptF2d)
	{
		GeomPoint3d ptI3d = new GeomPoint3d(ptI2d);
		GeomPoint3d ptF3d = new GeomPoint3d(ptF2d);

		this.init(ptI3d, ptF3d);
	}
	
	public void init(GeomPoint3d ptI3d, GeomPoint3d ptF3d)
	{
		GeomDimension3d rect = GeomUtil.dimensionOf(ptI3d, ptF3d);

		this.ptMin3d = new GeomPoint3d(rect.getPtMin());
		this.ptMax3d = new GeomPoint3d(rect.getPtMax());

		this.ptCentroid = GeomUtil.midPointOf(ptMin3d, ptMax3d);

		this.width = this.ptMax3d.getX() - this.ptMin3d.getX();
		this.height = this.ptMax3d.getY() - this.ptMin3d.getY();
		this.zHeight = this.ptMax3d.getZ() - this.ptMin3d.getZ();
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtMin3d() {
		return ptMin3d;
	}

	public void setPtMin3d(GeomPoint3d ptMin3d) {
		this.ptMin3d = ptMin3d;
	}

	public GeomPoint3d getPtMax3d() {
		return ptMax3d;
	}

	public void setPtMax3d(GeomPoint3d ptMax3d) {
		this.ptMax3d = ptMax3d;
	}

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public void setPtCentroid(GeomPoint3d ptCentroid) {
		this.ptCentroid = ptCentroid;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getzHeight() {
		return zHeight;
	}

	public void setzHeight(double zHeight) {
		this.zHeight = zHeight;
	}
		
}
