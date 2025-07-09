/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Vector2dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;

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
		double dV = v.mod();
		if(dV <= AppDefs.MATHPREC_MIN) return 0.0;
		
		GeomVector2d uDir = this.otherUnit();
		double angleDir = uDir.angleToAxisX();
		
		double dProjV = uDir.dotProd(v);

		GeomVector2d nDir = uDir.otherNorm();
		double dProjNormV = nDir.dotProd(v);
		
		double valCos = dProjV / dV; 
		
		
		double angRad = Math.acos(valCos);
		if(dProjNormV < 0.0)
			angRad = (AppDefs.MATHVAL_2PI + angleDir) - angRad;
		return angRad;		
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
