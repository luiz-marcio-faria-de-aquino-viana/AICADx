/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomPlan3d.java
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

public class GeomPlan3d 
{
//Private
	private GeomUCS ucs;
		
	//PLAN_ORIGIN
	//
	private GeomPoint3d ptCenter;  
	private GeomVector3d xDir;

	//AXIS_DIRECTIONS
	//
	private GeomVector3d axisX;
	private GeomVector3d axisY;
	private GeomVector3d axisZ;

	//PLAN_SIZE
	//
	private double width;
	private double height;
	//
	private double zHeight;
	
	//PLAN_RATIO
	//
	private double ratio;			// RATIO = HEIGHT / WIDTH
	
	//PLAN_LIMITS
	//
	private GeomPoint3d ptLowerLeftCorner;
	private GeomPoint3d ptLowerRightCorner;
	private GeomPoint3d ptUpperRightCorner;
	private GeomPoint3d ptUpperLeftCorner;
	
//Public
	
	public GeomPlan3d(GeomPoint3d ptCenter, GeomVector3d xDir)
	{
		this.init(ptCenter, xDir, 1.0, 1.0, 1.0);
	}
	
	public GeomPlan3d(GeomPoint3d ptCenter, GeomVector3d xDir, double width, double height, double zHeight)
	{
		this.init(ptCenter, xDir, width, height, zHeight);
	}
	
	public GeomPlan3d(GeomPoint3d ptCenter, GeomVector3d xDir, GeomVector3d zDir, double width, double height, double zHeight)
	{
		this.init(ptCenter, xDir, zDir, width, height, zHeight);
	}
	
	public GeomPlan3d(GeomUCS ucs, GeomPoint3d ptCenter, GeomVector3d xDir)
	{
		this.init(ucs, ptCenter, xDir, 1.0, 1.0, 1.0);
	}
	
	public GeomPlan3d(GeomUCS ucs, GeomPoint3d ptCenter, GeomVector3d xDir, double width, double height, double zHeight)
	{
		this.init(ucs, ptCenter, xDir, width, height, zHeight);
	}
	
	public GeomPlan3d(GeomUCS ucs, GeomPoint3d ptCenter, GeomVector3d xDir, GeomVector3d zDir, double width, double height, double zHeight)
	{
		this.init(ucs, ptCenter, xDir, zDir, width, height, zHeight);
	}
	
	public GeomPlan3d(GeomPlan3d plan)
	{
		this.init(plan.getUcs(), plan.getPtCenter(), plan.getXDir(), plan.getWidth(), plan.getHeight(), plan.getZHeight());
	}
	
	public GeomPlan3d(GeomPlan2d plan)
	{
		GeomPoint3d ptCenter3d = new GeomPoint3d(plan.getPtCenter());
		
		GeomVector2d xDir2d = plan.getXDir();
		
		GeomPoint3d ptI3d = new GeomPoint3d(xDir2d.getXI(), xDir2d.getYI(), 0.0);
		GeomPoint3d ptF3d = new GeomPoint3d(xDir2d.getXF(), xDir2d.getYF(), 0.0);
		
		GeomVector3d xDir3d = new GeomVector3d(ptI3d, ptF3d);
		
		double w = plan.getWidth();
		double h = plan.getHeight();
		//
		double zH = h;
		
		this.init(plan.getUcs(), ptCenter3d, xDir3d, w, h, zH);
	}

	/* Methodes */
	
	public void init(GeomPoint3d ptCenter, GeomVector3d xDir, double width, double height, double zHeight)
	{
		this.ucs = null;
		
		//PLAN_ORIGIN
		//
		this.ptCenter = new GeomPoint3d(ptCenter);
		this.xDir = new GeomVector3d(ptCenter, xDir);

		//AXIS_DIRECTIONS
		//
		this.axisX = new GeomVector3d(this.xDir);
		this.axisX.selfUnit();
		
		this.axisY = this.axisX.otherNorm();
		
		GeomVector3d zDir = this.axisX.vectProd(this.axisY);
		this.axisZ = new GeomVector3d(ptCenter, zDir);

		//PLAN_SIZE
		//
		this.width = width;
		this.height = height;
		this.zHeight = zHeight;
		
		//PLAN_RATIO
		//
		this.ratio = this.height / this.width;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}
	
	public void init(GeomPoint3d ptCenter, GeomVector3d xDir, GeomVector3d zDir, double width, double height, double zHeight)
	{
		this.ucs = null;
		
		//PLAN_ORIGIN
		//
		this.ptCenter = new GeomPoint3d(ptCenter);
		this.xDir = new GeomVector3d(ptCenter, xDir);
		
		//AXIS_DIRECTIONS
		//
		this.axisX = new GeomVector3d(this.xDir);
		this.axisX.selfUnit();
		
		this.axisZ = new GeomVector3d(ptCenter, zDir);
		this.axisZ.selfUnit(); 
		
		GeomVector3d yDir = this.axisX.vectProd(this.axisZ);

		this.axisY = new GeomVector3d(ptCenter, yDir);
		this.axisY.selfUnit();
		this.axisY.selfMult(-1.0);

		//PLAN_SIZE
		//
		this.width = width;
		this.height = height;
		this.zHeight = zHeight;
		
		//PLAN_RATIO
		//
		this.ratio = this.height / this.width;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}
	
	public void init(GeomUCS ucs, GeomPoint3d ptCenter, GeomVector3d xDir, double width, double height, double zHeight)
	{
		this.ucs = new GeomUCS(ucs);
		
		//PLAN_ORIGIN
		//
		this.ptCenter = new GeomPoint3d(ptCenter);
		this.xDir = new GeomVector3d(ptCenter, xDir);

		//AXIS_DIRECTIONS
		//
		this.axisX = new GeomVector3d(this.xDir);
		this.axisX.selfUnit();
		
		this.axisY = this.axisX.otherNorm();
		
		GeomVector3d zDir = this.axisX.vectProd(this.axisY);
		this.axisZ = new GeomVector3d(ptCenter, zDir);

		//PLAN_SIZE
		//
		this.width = width;
		this.height = height;
		this.zHeight = zHeight;
		
		//PLAN_RATIO
		//
		this.ratio = this.height / this.width;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}
	
	public void init(GeomUCS ucs, GeomPoint3d ptCenter, GeomVector3d xDir, GeomVector3d zDir, double width, double height, double zHeight)
	{
		this.ucs = new GeomUCS(ucs);
		
		//PLAN_ORIGIN
		//
		this.ptCenter = new GeomPoint3d(ptCenter);
		this.xDir = new GeomVector3d(ptCenter, xDir);
		
		//AXIS_DIRECTIONS
		//
		this.axisX = new GeomVector3d(this.xDir);
		this.axisX.selfUnit();
		
		this.axisZ = new GeomVector3d(ptCenter, zDir);
		this.axisZ.selfUnit(); 
		
		GeomVector3d yDir = this.axisX.vectProd(this.axisZ);

		this.axisY = new GeomVector3d(ptCenter, yDir);
		this.axisY.selfUnit();
		this.axisY.selfMult(-1.0);

		//PLAN_SIZE
		//
		this.width = width;
		this.height = height;
		this.zHeight = zHeight;
		
		//PLAN_RATIO
		//
		this.ratio = this.height / this.width;
		
		//PLAN_LIMITS
		//
		this.adjustLimits();
	}
	
	public void resetOrigin(GeomPoint3d newPtCenter) 
	{
		this.ptCenter = new GeomPoint3d(newPtCenter);
		
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

	public void resetDimention(double height, double width, double zHeight) {
		this.height = height;
		this.width = width;
		//
		this.zHeight = zHeight;

		double valRatio = this.height / this.width;
		this.resetRatio(valRatio);
	}

	public void resetRatio(double ratio) {
		//PLAN_RATIO
		//
		this.ratio = ratio;
		
		if(this.height <= this.width)
			this.height = this.width * this.ratio;
		else
			this.width = this.height / this.ratio;
		
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
		
		this.ptLowerLeftCorner = new GeomPoint3d(xMin, yMin, 0.0);
		this.ptLowerRightCorner = new GeomPoint3d(xMax, yMin, 0.0);
		this.ptUpperRightCorner = new GeomPoint3d(xMax, yMax, 0.0);
		this.ptUpperLeftCorner = new GeomPoint3d(xMin, yMax, 0.0);
	}
		
	/* Getters/Setters */

	public double getZHeight() {
		return zHeight;
	}

	public void setZHeight(double zHeight) {
		this.zHeight = zHeight;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public GeomVector3d getXDir() {
		return xDir;
	}

	public void setXDir(GeomVector3d xDir) {
		this.xDir = xDir;
	}

	public GeomVector3d getAxisX() {
		return axisX;
	}

	public void setAxisX(GeomVector3d axisX) {
		this.axisX = axisX;
	}

	public GeomVector3d getAxisY() {
		return axisY;
	}

	public void setAxisY(GeomVector3d axisY) {
		this.axisY = axisY;
	}

	public GeomVector3d getAxisZ() {
		return axisZ;
	}

	public void setAxisZ(GeomVector3d axisZ) {
		this.axisZ = axisZ;
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

	public GeomPoint3d getPtLowerLeftCorner() {
		return ptLowerLeftCorner;
	}

	public void setPtLowerLeftCorner(GeomPoint3d ptLowerLeftCorner) {
		this.ptLowerLeftCorner = ptLowerLeftCorner;
	}

	public GeomPoint3d getPtLowerRightCorner() {
		return ptLowerRightCorner;
	}

	public void setPtLowerRightCorner(GeomPoint3d ptLowerRightCorner) {
		this.ptLowerRightCorner = ptLowerRightCorner;
	}

	public GeomPoint3d getPtUpperRightCorner() {
		return ptUpperRightCorner;
	}

	public void setPtUpperRightCorner(GeomPoint3d ptUpperRightCorner) {
		this.ptUpperRightCorner = ptUpperRightCorner;
	}

	public GeomPoint3d getPtUpperLeftCorner() {
		return ptUpperLeftCorner;
	}

	public void setPtUpperLeftCorner(GeomPoint3d ptUpperLeftCorner) {
		this.ptUpperLeftCorner = ptUpperLeftCorner;
	}

	public GeomPoint3d getPtCenter() {
		return ptCenter;
	}

	public void setPtCenter(GeomPoint3d ptCenter) {
		this.ptCenter = ptCenter;
	}

	public GeomUCS getUcs() {
		return ucs;
	}

	public void setUcs(GeomUCS ucs) {
		this.ucs = ucs;
	}
	
}
