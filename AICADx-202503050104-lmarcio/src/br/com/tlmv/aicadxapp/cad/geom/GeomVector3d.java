/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Vector3dVO.java
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

public class GeomVector3d 
{
//Private
	//Vector
	//
	private double xI;
	private double yI;
	private double zI;
	private double xF;
	private double yF;
	private double zF;

	//VectorToOrigin
	//
	private double xOrig;
	private double yOrig;
	private double zOrig;
	
//Public
	
	public GeomVector3d(double xF, double yF, double zF)
	{
		this.init(0.0, 0.0, 0.0, xF, yF, zF);
	}
	
	public GeomVector3d(double xI, double yI, double zI, double xF, double yF, double zF)
	{
		this.init(xI, yI, zI, xF, yF, zF);
	}
	
	public GeomVector3d(GeomPoint3d ptF)
	{
		this.init(0.0, 0.0, 0.0, ptF.getX(), ptF.getY(), ptF.getZ());
	}
	
	public GeomVector3d(GeomPoint3d ptI, GeomPoint3d ptF)
	{
		this.init(ptI.getX(), ptI.getY(), ptI.getZ(), ptF.getX(), ptF.getY(), ptF.getZ());
	}
	
	public GeomVector3d(GeomVector2d v)
	{
		this.init(v.getXI(), v.getYI(), 0.0, v.getXF(), v.getYF(), 0.0);
	}
	
	public GeomVector3d(GeomVector3d v)
	{
		this.init(v.getXI(), v.getYI(), v.getZI(), v.getXF(), v.getYF(), v.getZF());
	}
	
	public GeomVector3d(GeomPoint2d pt, GeomVector2d v)
	{
		double xI = pt.getX();
		double yI = pt.getY();
		double zI = 0.0;
		double xF = xI + v.getXOrig();
		double yF = yI + v.getYOrig();
		double zF = 0.0;
		
		this.init(xI, yI, zI, xF, yF, zF);
	}
	
	public GeomVector3d(GeomPoint3d pt, GeomVector2d v)
	{
		double xI = pt.getX();
		double yI = pt.getY();
		double zI = pt.getZ();
		double xF = xI + v.getXOrig();
		double yF = yI + v.getYOrig();
		double zF = zI;
		
		this.init(xI, yI, zI, xF, yF, zF);
	}
	
	public GeomVector3d(GeomPoint3d pt, GeomVector3d v)
	{
		double xI = pt.getX();
		double yI = pt.getY();
		double zI = pt.getZ();
		double xF = xI + v.getXOrig();
		double yF = yI + v.getYOrig();
		double zF = zI + v.getZOrig();
		
		this.init(xI, yI, zI, xF, yF, zF);
	}

	/* Methodes */
	
	public void init(double xI, double yI, double zI, double xF, double yF, double zF)
	{
		this.xI = xI;
		this.yI = yI;
		this.zI = zI;
		this.xF = xF;
		this.yF = yF;
		this.zF = zF;
		
		this.xOrig = xF - xI;
		this.yOrig = yF - yI;
		this.zOrig = zF - zI;
	}
	
	public double mod()
	{
		double d = Math.sqrt(xOrig * xOrig + yOrig * yOrig + zOrig * zOrig);
		return d;
	}
	
	public double dotProd(GeomVector3d v)
	{
		double xOrig = this.getXOrig();
		double yOrig = this.getYOrig();
		double zOrig = this.getZOrig();

		double vXOrig = v.getXOrig();
		double vYOrig = v.getYOrig();
		double vZOrig = v.getZOrig();
		
		double d = xOrig * vXOrig + yOrig * vYOrig + zOrig * vZOrig;
		return d;		
	}
	
	public GeomVector3d vectProd(GeomVector3d v)
	{
		double x0 = this.getXOrig();
		double y0 = this.getYOrig();
		double z0 = this.getZOrig();

		double x1 = v.getXOrig();
		double y1 = v.getYOrig();
		double z1 = v.getZOrig();
		
		double x = y0 * z1 - z0 * y1;
		double y = z0 * x1 - x0 * z1;
		double z = x0 * y1 - y0 * x1;

		GeomVector3d w = new GeomVector3d(x, y, z);
		return w;		
	}
	
	/* ANGLE */
	
	public double angleToAxisX()
	{
		double dV = this.mod();
		if(dV <= AppDefs.MATHPREC_MIN) return 0.0;

		GeomVector3d axisX = GeomUtil.axisX3d();
		double dProjV = axisX.dotProd(this);

		double valCos = dProjV / dV; 
		
		double angRad = Math.acos(valCos);
		if(this.yOrig < 0.0)
			angRad = AppDefs.MATHVAL_2PI - angRad;
		return angRad;		
	}
	
	public double angleTo(GeomVector3d v)
	{
		double dV = v.mod();
		if(dV <= AppDefs.MATHPREC_MIN) return 0.0;
		
		GeomVector3d uDir = this.otherUnit();
		double angleDir = uDir.angleToAxisX();
				
		double dProjV = uDir.dotProd(v);

		GeomVector3d nDir = uDir.otherNorm();
		double dProjNormV = nDir.dotProd(v);

		double valCos = dProjV / dV; 
		
		double angRad = Math.acos(valCos);
		if(dProjNormV < 0.0)
			angRad = (AppDefs.MATHVAL_2PI + angleDir) - angRad;
		return angRad;		
	}
	
	public double angleTo(GeomVector2d v)
	{
		GeomVector3d v3d = new GeomVector3d(v.getXOrig(), v.getYOrig(), 0.0);
		double angRad = this.angleTo(v3d);
		return angRad;		
	}
		
	/* SELF */
	
	public GeomVector3d selfMult(double mult)
	{
		this.xOrig = this.xOrig * mult;
		this.yOrig = this.yOrig * mult;
		this.zOrig = this.zOrig * mult;

		this.xF = this.xI + this.xOrig;
		this.yF = this.yI + this.yOrig;
		this.zF = this.zI + this.zOrig;
		
		return this;
	}
	
	public GeomVector3d selfUnit()
	{
		double mult = this.mod();
		if(mult <= 0.001)
			mult = 0.001;

		this.selfMult(1.0 / mult);
		return this;
	}
	
	public GeomVector3d selfNorm()
	{
		double xI = this.getXI();
		double yI = this.getYI();
		double zI = this.getZI();

		double xOrig = this.getXOrig();
		double yOrig = this.getYOrig();
		double zOrig = this.getZOrig();

		double xNorm = - yOrig;
		double yNorm = xOrig;
		double zNorm = zOrig;
		
		double xF = xI + xNorm;
		double yF = yI + yNorm;
		double zF = zI + zNorm;
		
		this.init(xI, yI, zI, xF, yF, zF);		
		return this;		
	}
	
	public GeomVector3d selfAdd(GeomVector3d v2d)
	{
		double xOrigFinal = this.xOrig + v2d.xOrig;
		double yOrigFinal = this.yOrig + v2d.yOrig;

		this.xF = this.xI + xOrigFinal;
		this.yF = this.yI + yOrigFinal;
		
		return this;
	}
	
	public GeomVector3d selfSub(GeomVector3d v2d)
	{
		double xOrigFinal = this.xOrig - v2d.xOrig;
		double yOrigFinal = this.yOrig - v2d.yOrig;

		this.xF = this.xI + xOrigFinal;
		this.yF = this.yI + yOrigFinal;
		
		return this;
	}

	//ROTATE
	//
	public GeomVector3d selfRotateToRad(double angleRad)
	{
		GeomVector3d axisX = GeomUtil.axisX3d();
		
		double startAngleRad = axisX.angleTo(this);
		double endAngleRad = startAngleRad + angleRad;
		
		double d = this.mod();
		
		this.xOrig = d * Math.cos(endAngleRad); 
		this.yOrig = d * Math.sin(endAngleRad);
		
		this.xF = this.xI + this.xOrig;
		this.yF = this.yI + this.yOrig;
		
		return this;
	}

	public GeomVector3d selfRotateToDegrees(double angleDegrees)
	{
		double angleRad = GeomUtil.convertDegreesToRad(angleDegrees);
		this.selfRotateToRad(angleRad);
		return this;
	}
	
	/* OTHER */
	
	public GeomVector3d otherMult(double mult)
	{
		GeomVector3d other = new GeomVector3d(this);
		other.selfMult(mult);
		return other;
	}
	
	public GeomVector3d otherUnit()
	{
		GeomVector3d other = new GeomVector3d(this);
		other.selfUnit();
		return other;
	}
	
	public GeomVector3d otherNorm()
	{
		GeomVector3d other = new GeomVector3d(this);
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
	public GeomVector3d otherRotateToRad(double angleRad)
	{
		GeomVector3d other = new GeomVector3d(this);
		other.selfRotateToRad(angleRad);
		return other;		
	}

	public GeomVector3d otherRotateToDegrees(double angleDegrees)
	{
		double angleRad = GeomUtil.convertDegreesToRad(angleDegrees);

		GeomVector3d other = this.otherRotateToRad(angleRad);
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

	public double getZI() {
		return zI;
	}

	public void setZI(double zI) {
		this.zI = zI;
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

	public double getZF() {
		return zF;
	}

	public void setZF(double zF) {
		this.zF = zF;
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

	public double getZOrig() {
		return zOrig;
	}

	public void setZOrig(double zOrig) {
		this.zOrig = zOrig;
	}
	
}
