/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomPlan2d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

public class GeomPlan2d 
{
//Private
	private GeomUCS ucs;
	
	//PLAN_ORIGIN
	//
	private GeomPoint2d ptCenter;
	private GeomVector2d xDir;

	//AXIS_DIRECTIONS
	//
	private GeomVector2d axisX;
	private GeomVector2d axisY;
	private GeomVector3d axisZ;

	//PLAN_SIZE
	//
	private double width;
	private double height;
	
	//PLAN_RATIO
	//
	private double ratio;			// RATIO = HEIGHT / WIDTH
	
	//PLAN_LIMITS
	//
	private GeomPoint2d ptLowerLeftCorner;
	private GeomPoint2d ptLowerRightCorner;
	private GeomPoint2d ptUpperRightCorner;
	private GeomPoint2d ptUpperLeftCorner;
	
//Public
	
	public GeomPlan2d(GeomPoint2d ptCenter, GeomVector2d xDir)
	{
		this.init(ptCenter, xDir, 1.0, 1.0);
	}
	
	public GeomPlan2d(GeomPoint2d ptCenter, GeomVector2d xDir, double width, double height)
	{
		this.init(ptCenter, xDir, width, height);
	}
	
	public GeomPlan2d(GeomUCS ucs, GeomPoint2d ptCenter, GeomVector2d xDir)
	{
		this.init(ucs, ptCenter, xDir, 1.0, 1.0);
	}
	
	public GeomPlan2d(GeomUCS ucs, GeomPoint2d ptCenter, GeomVector2d xDir, double width, double height)
	{
		this.init(ucs, ptCenter, xDir, width, height);
	}
	
	public GeomPlan2d(GeomPlan2d plan)
	{
		this.init(plan.getUcs(), plan.getPtCenter(), plan.getXDir(), plan.getWidth(), plan.getHeight());
	}
	
	public GeomPlan2d(GeomPlan3d plan)
	{
		GeomPoint2d ptCenter2d = new GeomPoint2d(plan.getPtCenter());
		
		GeomVector3d xDir3d = plan.getXDir();
		
		GeomPoint2d ptI2d = new GeomPoint2d(xDir3d.getXI(), xDir3d.getYI());
		GeomPoint2d ptF2d = new GeomPoint2d(xDir3d.getXF(), xDir3d.getYF());
		
		GeomVector2d xDir2d = new GeomVector2d(ptI2d, ptF2d);
		
		double w = plan.getWidth();
		double h = plan.getHeight();
		
		this.init(plan.getUcs(), ptCenter2d, xDir2d, w, h);
	}

	/* Methodes */
	
	public void init(GeomPoint2d ptCenter, GeomVector2d xDir, double width, double height)
	{
		GeomPoint3d ptOriginMcs = new GeomPoint3d(0.0, 0.0, 0.0);
		GeomPoint3d ptXDirMcs = new GeomPoint3d(1.0, 0.0, 0.0);
		GeomPoint3d ptPlanMcs = new GeomPoint3d(1.0, 1.0, 0.0);
		
		this.ucs = new GeomUCS(ptOriginMcs, ptXDirMcs, ptPlanMcs);
		
		//PLAN_ORIGIN
		//
		this.ptCenter = new GeomPoint2d(ptCenter);
		this.xDir = new GeomVector2d(ptCenter, xDir);

		//AXIS_DIRECTIONS
		//
		this.axisX = new GeomVector2d(this.xDir);
		this.axisX.selfUnit();
		
		this.axisY = this.axisX.otherNorm();
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter);
		GeomVector3d zDir = new GeomVector3d(0.0, 0.0, 1.0);
		this.axisZ = new GeomVector3d(ptCenter3d, zDir);

		//PLAN_SIZE
		//
		this.width = width;
		this.height = height;

		//PLAN_RATIO
		//
		this.ratio = this.height / this.width;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}
	
	public void init(GeomUCS ucs, GeomPoint2d ptCenter, GeomVector2d xDir, double width, double height)
	{
		GeomPoint3d ptOriginMcs = new GeomPoint3d(0.0, 0.0, 0.0);
		GeomPoint3d ptXDirMcs = new GeomPoint3d(1.0, 0.0, 0.0);
		GeomPoint3d ptPlanMcs = new GeomPoint3d(1.0, 1.0, 0.0);
		
		this.ucs = new GeomUCS(ptOriginMcs, ptXDirMcs, ptPlanMcs);
		
		//PLAN_ORIGIN
		//
		this.ptCenter = new GeomPoint2d(ptCenter);
		this.xDir = new GeomVector2d(ptCenter, xDir);

		//AXIS_DIRECTIONS
		//
		this.axisX = new GeomVector2d(this.xDir);
		this.axisX.selfUnit();
		
		this.axisY = this.axisX.otherNorm();
		
		GeomPoint3d ptCenter3d = new GeomPoint3d(ptCenter);
		GeomVector3d zDir = new GeomVector3d(0.0, 0.0, 1.0);
		this.axisZ = new GeomVector3d(ptCenter3d, zDir);

		//PLAN_SIZE
		//
		this.width = width;
		this.height = height;

		//PLAN_RATIO
		//
		this.ratio = this.height / this.width;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}
	
	public void resetOrigin(GeomPoint2d newPtCenter) 
	{
		this.ptCenter = new GeomPoint2d(newPtCenter);
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}

	public void resetDimention(double width, double height) {
		this.height = height;
		this.width = width;

		double valRatio = this.height / this.width;
		this.resetRatio(valRatio);
	}

	public void resetRatio(double ratio) {
		//PLAN_RATIO
		//
		this.ratio = ratio;
		
		//if(this.height <= this.width)
		//	this.height = this.width * this.ratio;
		//else
		//	this.width = this.height / this.ratio;

		this.height = this.width * this.ratio;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}

	public void adjustLimits()
	{
		double halfWidth = this.width / 2.0;
		double halfHeight = this.height / 2.0;

		double xMin = this.ptCenter.getX() - halfWidth;
		double yMin = this.ptCenter.getY() - halfHeight;
		
		double xMax = this.ptCenter.getX() + halfWidth;
		double yMax = this.ptCenter.getY() + halfHeight;
		
		this.ptLowerLeftCorner = new GeomPoint2d(xMin, yMin);
		this.ptLowerRightCorner = new GeomPoint2d(xMax, yMin);
		this.ptUpperRightCorner = new GeomPoint2d(xMax, yMax);
		this.ptUpperLeftCorner = new GeomPoint2d(xMin, yMax);
	}
	
	/* Getters/Setters */

	public GeomVector2d getXDir() {
		return xDir;
	}

	public GeomVector2d getAxisX() {
		return axisX;
	}

	public GeomVector2d getAxisY() {
		return axisY;
	}

	public GeomVector3d getAxisZ() {
		return axisZ;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getRatio() {
		return ratio;
	}

	public GeomPoint2d getPtCenter() {
		return ptCenter;
	}

	public GeomPoint2d getPtLowerLeftCorner() {
		return ptLowerLeftCorner;
	}

	public GeomPoint2d getPtLowerRightCorner() {
		return ptLowerRightCorner;
	}

	public GeomPoint2d getPtUpperRightCorner() {
		return ptUpperRightCorner;
	}

	public GeomPoint2d getPtUpperLeftCorner() {
		return ptUpperLeftCorner;
	}

	public GeomUCS getUcs() {
		return ucs;
	}

	public void setUcs(GeomUCS ucs) {
		this.ucs = ucs;
	}

	public void setPtLowerLeftCorner(GeomPoint2d ptLowerLeftCorner) {
		this.ptLowerLeftCorner = ptLowerLeftCorner;
	}

	public void setPtLowerRightCorner(GeomPoint2d ptLowerRightCorner) {
		this.ptLowerRightCorner = ptLowerRightCorner;
	}

	public void setPtUpperRightCorner(GeomPoint2d ptUpperRightCorner) {
		this.ptUpperRightCorner = ptUpperRightCorner;
	}

	public void setPtUpperLeftCorner(GeomPoint2d ptUpperLeftCorner) {
		this.ptUpperLeftCorner = ptUpperLeftCorner;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
