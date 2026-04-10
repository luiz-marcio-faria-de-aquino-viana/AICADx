/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class GeomUCS 
{
//Private
	private GeomPoint3d ptOriginMcs;
	private GeomPoint3d ptXDirMcs;
	private GeomPoint3d ptPlanMcs;
	//
	private GeomVector3d xDirMcs;
	private GeomVector3d yDirMcs;
	private GeomVector3d zDirMcs;
	//
	private GeomVector3d vPlanMcs;
	//
	private GeomVector3d axisX;
	private GeomVector3d axisY;
	private GeomVector3d axisZ;
	
//Public

	public GeomUCS(
		GeomPoint3d ptOriginMcs0,
		GeomPoint3d ptXDirMcs0,
		GeomPoint3d ptPlanMcs0)
	{
		this.init(
			ptOriginMcs0,
			ptXDirMcs0,
			ptPlanMcs0);
	}

	public GeomUCS(GeomUCS ucs0)
	{
		if(ucs0 == null) {
			this.init(
				new GeomPoint3d(0.0, 0.0, 0.0),
				new GeomPoint3d(1.0, 0.0, 0.0),
				new GeomPoint3d(1.0, 1.0, 0.0) );
		}
		else {
			this.init(
				ucs0.ptOriginMcs,
				ucs0.ptXDirMcs,
				ucs0.ptPlanMcs);
		}
	}
		
	/* Methodes */
	
	public void init(
		GeomPoint3d ptOriginMcs0,
		GeomPoint3d ptXDirMcs0, 
		GeomPoint3d ptPlanMcs0)
	{
		this.ptOriginMcs = new GeomPoint3d(ptOriginMcs0);
		this.ptXDirMcs = new GeomPoint3d(ptXDirMcs0);
		this.ptPlanMcs = new GeomPoint3d(ptPlanMcs0);
		//
		this.vPlanMcs = new GeomVector3d(this.ptOriginMcs, this.ptPlanMcs);
		//
		this.xDirMcs = new GeomVector3d(this.ptOriginMcs, this.ptXDirMcs);
		this.zDirMcs = this.xDirMcs.vectProd(vPlanMcs);
		this.yDirMcs = this.xDirMcs.vectProd(zDirMcs);
		//
		this.axisX = this.xDirMcs.otherUnit();
		this.axisY = this.yDirMcs.otherUnit();
		this.axisZ = this.zDirMcs.otherUnit();		
	}
	
	public void init(GeomUCS ucs0)
	{
		this.init(
			ucs0.ptOriginMcs,
			ucs0.ptXDirMcs,
			ucs0.ptPlanMcs);
	}
	
	/* OPERATIONS */
	
	public double distTo(GeomPoint3d ptMcs)
	{
		double d = this.ptOriginMcs.distTo(ptMcs);
		return d;
	}
	
	public GeomPoint3d fromWCSToUCS(GeomPoint3d ptWCSMcs)
	{
		GeomVector3d vWCSMcs = new GeomVector3d(this.ptOriginMcs, ptWCSMcs);
		
		double xp = this.axisX.dotProd(vWCSMcs);
		double yp = this.axisY.dotProd(vWCSMcs);
		double zp = this.axisZ.dotProd(vWCSMcs);
		
		GeomPoint3d ptReturn = new GeomPoint3d(xp, yp, zp);
		return ptReturn;
	}
	
	public GeomPoint3d fromUCSToWCS(GeomPoint3d ptUCSMcs)
	{
		GeomWCS wcs = new GeomWCS();
		
		GeomVector3d vOriginWCSToOriginUCSMcs = new GeomVector3d(wcs.getPtOriginMcs(), this.ptOriginMcs);
		GeomVector3d vOriginUCSToPtUCSMcs = new GeomVector3d(this.ptOriginMcs, ptUCSMcs);

		GeomVector3d vOriginWCSToPtUCSMcs = vOriginWCSToOriginUCSMcs.selfAdd(vOriginUCSToPtUCSMcs);
		
		double xp = wcs.getAxisX().dotProd(vOriginWCSToPtUCSMcs);
		double yp = wcs.getAxisY().dotProd(vOriginWCSToPtUCSMcs);
		double zp = wcs.getAxisZ().dotProd(vOriginWCSToPtUCSMcs);
		
		GeomPoint3d ptReturn = new GeomPoint3d(xp, yp, zp);
		return ptReturn;
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
		return this.axisX;
	}

	public GeomVector3d getAxisY() {
		return this.axisY;
	}

	public GeomVector3d getAxisZ() {
		return this.axisZ;
	}

	public GeomPoint3d getPtXDirMcs() {
		return this.ptXDirMcs;
	}

	public GeomPoint3d getPtPlanMcs() {
		return this.ptPlanMcs;
	}

	public GeomVector3d getXDirMcs() {
		return this.xDirMcs;
	}

	public GeomVector3d getYDirMcs() {
		return this.yDirMcs;
	}

	public GeomVector3d getZDirMcs() {
		return this.zDirMcs;
	}

	public GeomVector3d getVPlanMcs() {
		return this.vPlanMcs;
	}
	
}
