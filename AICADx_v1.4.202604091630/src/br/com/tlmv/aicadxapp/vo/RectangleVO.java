/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
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
