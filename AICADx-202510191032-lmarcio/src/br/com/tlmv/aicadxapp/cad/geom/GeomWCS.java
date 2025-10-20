/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomUCS.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class GeomWCS 
{
//Private
	private GeomPoint3d ptOriginMcs;
	//
	private GeomVector3d axisX;
	private GeomVector3d axisY;
	private GeomVector3d axisZ;
	
//Public

	public GeomWCS()
	{
		this.init();
	}
		
	/* Methodes */
	
	public void init()
	{
		this.ptOriginMcs = new GeomPoint3d(0.0, 0.0, 0.0);
		//
		this.axisX = new GeomVector3d(1.0, 0.0, 0.0);
		this.axisY = new GeomVector3d(0.0, 1.0, 0.0);
		this.axisZ = new GeomVector3d(0.0, 0.0, 1.0);		
	}

	/* OPERATIONS */
	
	public double distTo(GeomPoint3d ptMcs)
	{
		double d = this.ptOriginMcs.distTo(ptMcs);
		return d;
	}

	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"Origin:[ %s, %s, %s ];" +
			"Axis-X:[ %s, %s, %s ];" +
			"Axis-Y:[ %s, %s, %s ];" +
			"Axis-Z:[ %s, %s, %s ];", 
			nf3.format(this.ptOriginMcs.getX()),
			nf3.format(this.ptOriginMcs.getY()),
			nf3.format(this.ptOriginMcs.getZ()),
			nf3.format(this.axisX.getXOrig()),
			nf3.format(this.axisX.getYOrig()),
			nf3.format(this.axisX.getZOrig()),
			nf3.format(this.axisY.getXOrig()),
			nf3.format(this.axisY.getYOrig()),
			nf3.format(this.axisY.getZOrig()),
			nf3.format(this.axisZ.getXOrig()),
			nf3.format(this.axisZ.getYOrig()),
			nf3.format(this.axisZ.getZOrig()) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtOriginMcs() {
		return this.ptOriginMcs;
	}

	public GeomVector3d getAxisX() {
		return axisX;
	}

	public GeomVector3d getAxisY() {
		return axisY;
	}

	public GeomVector3d getAxisZ() {
		return axisZ;
	}
	
}
