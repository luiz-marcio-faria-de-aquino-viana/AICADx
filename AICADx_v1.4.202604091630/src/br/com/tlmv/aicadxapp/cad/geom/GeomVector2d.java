/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * Vector2dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/01/2025
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
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class GeomVector2d 
{
//Private
	//Vector
	//
	private double xI;
	private double yI;
	private double xF;
	private double yF;

	//VectorToOrigin
	//
	private double xOrig;
	private double yOrig;
	
//Public
	
	public GeomVector2d(double xF, double yF)
	{
		this.init(0.0, 0.0, xF, yF);
	}
	
	public GeomVector2d(double xI, double yI, double xF, double yF)
	{
		this.init(xI, yI, xF, yF);
	}
	
	public GeomVector2d(GeomPoint2d ptF)
	{
		this.init(0.0, 0.0, ptF.getX(), ptF.getY());
	}
	
	public GeomVector2d(GeomPoint2d ptI, GeomPoint2d ptF)
	{
		this.init(ptI.getX(), ptI.getY(), ptF.getX(), ptF.getY());
	}
	
	public GeomVector2d(GeomVector2d v)
	{
		this.init(v.getXI(), v.getYI(), v.getXF(), v.getYF());
	}
	
	public GeomVector2d(GeomVector3d v)
	{
		this.init(v.getXI(), v.getYI(), v.getXF(), v.getYF());
	}
	
	public GeomVector2d(GeomPoint2d pt, GeomVector2d v)
	{
		double xI = pt.getX();
		double yI = pt.getY();
		double xF = xI + v.getXOrig();
		double yF = yI + v.getYOrig();
		
		this.init(xI, yI, xF, yF);
	}

	/* Methodes */
	
	public void init(double xI, double yI, double xF, double yF)
	{
		this.xI = xI;
		this.yI = yI;
		this.xF = xF;
		this.yF = yF;
		
		this.xOrig = xF - xI;
		this.yOrig = yF - yI;
	}
	
	public double mod()
	{
		double d = Math.sqrt(xOrig * xOrig + yOrig * yOrig);
		return d;
	}
	
	public double dotProd(GeomVector2d v)
	{
		double xOrig = this.getXOrig();
		double yOrig = this.getYOrig();

		double vXOrig = v.getXOrig();
		double vYOrig = v.getYOrig();
		
		double d = xOrig * vXOrig + yOrig * vYOrig;
		return d;		
	}
	
	public GeomVector3d vectProd(GeomVector2d v)
	{
		double x0 = this.getXOrig();
		double y0 = this.getYOrig();

		double x1 = v.getXOrig();
		double y1 = v.getYOrig();
		
		double z = x0 * y1 - y0 * x1;

		GeomVector3d w = new GeomVector3d(0.0, 0.0, z);
		return w;		
	}

	/* DISTANCE */
	
	public double distTo(GeomPoint2d pt2d)
	{
		GeomVector2d vToPt = new GeomVector2d(this.xI, this.yI, pt2d.getX(), pt2d.getY());
		GeomVector2d uDir = this.otherUnit();
		
		GeomVector3d zV = uDir.vectProd(vToPt);

		double d = Math.abs( zV.getZOrig() );
		return d;		
	}
	
	public double distTo(GeomPoint3d pt3d)
	{
		GeomVector3d vToPt = new GeomVector3d(this.xI, this.yI, 0.0, pt3d.getX(), pt3d.getY(), pt3d.getZ());

		GeomVector3d vDir3d = new GeomVector3d(this.xI, this.yI, 0.0);
		GeomVector3d uDir3d = vDir3d.otherUnit();
		
		GeomVector3d zV = uDir3d.vectProd(vToPt);

		double d = zV.mod();
		return d;		
	}

	/* ANGLE */
	
	public double angleToAxisX()
	{
		double dV = this.mod();
		if(dV <= AppDefs.MATHPREC_MIN) return 0.0;

		GeomVector2d axisX = GeomUtil.axisX2d();
		double dProjV = axisX.dotProd(this);

		double valCos = dProjV / dV; 
		
		double angRad = Math.acos(valCos);
		if(this.yOrig < 0.0)
			angRad = AppDefs.MATHVAL_2PI - angRad;
		return angRad;		
	}
	
	public double angleTo(GeomVector2d v)
	{
		GeomVector2d uDir = this.otherUnit();
		double d = uDir.dotProd(v);

		double dH = v.mod();
		if(dH == 0) return 0.0;

		GeomVector2d nDir = uDir.otherNorm();
		double dD = nDir.dotProd(v);
		
		double cosAngleRad = d / dH;
		double angleRad = Math.acos(cosAngleRad);
		if(dD < 0) 
			return angleRad = AppDefs.MATHVAL_2PI - angleRad;
		return angleRad;		
	}
	
	public double angleTo(GeomVector3d v)
	{
		GeomVector2d v2d = new GeomVector2d(v.getXOrig(), v.getYOrig());
		double angRad = this.angleTo(v2d);
		return angRad;		
	}
	
	/* SELF */
	
	public GeomVector2d selfMult(double mult)
	{
		this.xOrig = this.xOrig * mult;
		this.yOrig = this.yOrig * mult;

		this.xF = this.xI + this.xOrig;
		this.yF = this.yI + this.yOrig;
		
		return this;
	}
	
	public GeomVector2d selfUnit()
	{
		double mult = this.mod();
		if(mult > AppDefs.MATHPREC_MIN)
			this.selfMult(1.0 / mult);
		else
			this.selfMult(0.0);
		return this;
	}
	
	public GeomVector2d selfNorm()
	{
		this.selfUnit();

		double tmpXOrig = - this.getYOrig();
		double tmpYOrig = this.getXOrig();
		
		this.xOrig = tmpXOrig;
		this.yOrig = tmpYOrig;
		
		this.xF = this.xI + this.xOrig;
		this.yF = this.yI + this.yOrig;
		
		return this;		
	}
	
	public GeomVector2d selfAdd(GeomVector2d v2d)
	{
		double xOrigFinal = this.xOrig + v2d.xOrig;
		double yOrigFinal = this.yOrig + v2d.yOrig;

		this.xF = this.xI + xOrigFinal;
		this.yF = this.yI + yOrigFinal;
		
		return this;
	}
	
	public GeomVector2d selfSub(GeomVector2d v2d)
	{
		double xOrigFinal = this.xOrig - v2d.xOrig;
		double yOrigFinal = this.yOrig - v2d.yOrig;

		this.xF = this.xI + xOrigFinal;
		this.yF = this.yI + yOrigFinal;
		
		return this;
	}

	//ROTATE
	//
	public GeomVector2d selfRotateToRad(double angleRad)
	{
		GeomVector2d axisX = GeomUtil.axisX2d();
		
		double startAngleRad = axisX.angleTo(this);
		double endAngleRad = startAngleRad + angleRad;
		
		double d = this.mod();
		
		this.xOrig = d * Math.cos(endAngleRad); 
		this.yOrig = d * Math.sin(endAngleRad);
		
		this.xF = this.xI + this.xOrig;
		this.yF = this.yI + this.yOrig;
		
		return this;
	}

	public GeomVector2d selfRotateToDegrees(double angleDegrees)
	{
		double angleRad = GeomUtil.convertDegreesToRad(angleDegrees);
		this.selfRotateToRad(angleRad);
		return this;
	}

	/* OTHER */
	
	public GeomVector2d otherMult(double mult)
	{
		GeomVector2d other = new GeomVector2d(this);
		other.selfMult(mult);
		return other;
	}
	
	public GeomVector2d otherUnit()
	{
		GeomVector2d other = new GeomVector2d(this);
		other.selfUnit();
		return other;
	}
	
	public GeomVector2d otherNorm()
	{
		GeomVector2d other = new GeomVector2d(this);
		other.selfNorm();
		return other;		
	}
	
	public GeomVector2d otherAdd(GeomVector2d v2d)
	{
		GeomVector2d other = new GeomVector2d(this);
		other.selfAdd(v2d);
		return other;		
	}
	
	public GeomVector2d otherSub(GeomVector2d v2d)
	{
		GeomVector2d other = new GeomVector2d(this);
		other.selfSub(v2d);
		return other;		
	}

	//ROTATE
	//
	public GeomVector2d otherRotateToRad(double angleRad)
	{
		GeomVector2d other = new GeomVector2d(this);
		other.selfRotateToRad(angleRad);
		return other;		
	}

	public GeomVector2d otherRotateToDegrees(double angleDegrees)
	{
		double angleRad = GeomUtil.convertDegreesToRad(angleDegrees);

		GeomVector2d other = this.otherRotateToRad(angleRad);
		return other;
	}
	
	//PROJECT
	//
	public GeomPoint2d otherProjectFrom(GeomPoint2d pt2dMcs)
	{
		GeomVector2d uDir = this.otherUnit();
		
		GeomPoint2d ptBase2dMcs = new GeomPoint2d(this.getXI(), this.getYI());		
		
		GeomVector2d vBaseToPt2dMcs = new GeomVector2d(ptBase2dMcs, pt2dMcs);

		double d = uDir.dotProd(vBaseToPt2dMcs);

		GeomPoint2d ptProj2dMcs = ptBase2dMcs.otherMoveTo(uDir, d);
		return ptProj2dMcs;
	}
		
	public GeomPoint3d otherProjectFrom(GeomPoint3d pt3dMcs)
	{
		GeomPoint3d pt2dMcs = new GeomPoint3d(pt3dMcs);

		GeomPoint3d ptProj3dMcs = this.otherProjectFrom(pt2dMcs);
		return ptProj3dMcs;
	}

	/* AXIAL_ROTATE */
	
	//PLAN-XY
	//
	public GeomVector3d otherRotatePlanXYRad(GeomPoint3d ptRef, double angleXYRad)
	{
		GeomVector3d v3d = new GeomVector3d(this);
		
		GeomVector3d vResult3d = v3d.otherRotatePlanXYRad(ptRef, angleXYRad);
		return vResult3d;
	}
	
	public GeomVector3d otherRotatePlanXYDegrees(GeomPoint3d ptRef, double angleXYDegrees)
	{
		GeomVector3d v3d = new GeomVector3d(this);
		
		GeomVector3d vResult3d = v3d.otherRotatePlanXYDegrees(ptRef, angleXYDegrees);
		return vResult3d;
	}
	
	//PLAN-ZX
	//
	public GeomVector3d otherRotatePlanZXRad(GeomPoint3d ptRef, double angleZXRad)
	{
		GeomVector3d v3d = new GeomVector3d(this);
		
		GeomVector3d vResult3d = v3d.otherRotatePlanZXRad(ptRef, angleZXRad);
		return vResult3d;
	}
	
	public GeomVector3d otherRotatePlanZXDegrees(GeomPoint3d ptRef, double angleZXDegrees)
	{
		GeomVector3d v3d = new GeomVector3d(this);
		
		GeomVector3d vResult3d = v3d.otherRotatePlanZXDegrees(ptRef, angleZXDegrees);
		return vResult3d;
	}
	
	//PLAN-YZ
	//
	public GeomVector3d otherRotatePlanYZRad(GeomPoint3d ptRef, double angleYZRad)
	{
		GeomVector3d v3d = new GeomVector3d(this);
		
		GeomVector3d vResult3d = v3d.otherRotatePlanXYRad(ptRef, angleYZRad);
		return vResult3d;
	}
	
	public GeomVector3d otherRotatePlanYZDegrees(GeomPoint3d ptRef, double angleYZDegrees)
	{
		GeomVector3d v3d = new GeomVector3d(this);
		
		GeomVector3d vResult3d = v3d.otherRotatePlanYZDegrees(ptRef, angleYZDegrees);
		return vResult3d;
	}

	/* PROPERTY_LIST */
	
	public ArrayList<ItemDataVO> toPropertyList(String strLabel)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();

		lsProperty.add( new ItemDataVO(strLabel + " - X", nf3.format(this.getXOrig())) );
		lsProperty.add( new ItemDataVO(strLabel + " - Y", nf3.format(this.getYOrig())) );
		
		return lsProperty;
	}
	
	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"[ %s, %s ]-[ %s, %s ] <=> [ %s, %s ]", 
			nf3.format(this.xI),
			nf3.format(this.yI),
			nf3.format(this.xF),
			nf3.format(this.yF),
			nf3.format(this.xOrig),
			nf3.format(this.yOrig) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
	}
		
	/* Getters/Setters */

	public double getXI() {
		return xI;
	}

	public void setXI(double xI) {
		this.xI = xI;
	}

	public double getYI() {
		return yI;
	}

	public void setYI(double yI) {
		this.yI = yI;
	}

	public double getXF() {
		return xF;
	}

	public void setXF(double xF) {
		this.xF = xF;
	}

	public double getYF() {
		return yF;
	}

	public void setYF(double yF) {
		this.yF = yF;
	}

	public double getXOrig() {
		return xOrig;
	}

	public void setXOrig(double xOrig) {
		this.xOrig = xOrig;
	}

	public double getYOrig() {
		return yOrig;
	}

	public void setYOrig(double yOrig) {
		this.yOrig = yOrig;
	}
	
}
