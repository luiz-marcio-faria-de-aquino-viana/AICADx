/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomObserver3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import br.com.tlmv.aicadxapp.AppDefs;

public class GeomObserver3d 
{
//Private
	
	//PLAN_ORIGIN
	//
	private GeomPoint3d ptObserver;
	private GeomVector3d uDir;
	//
	private GeomVector3d nDir;

//Public
	
	public GeomObserver3d(GeomPoint3d ptObserver, GeomVector3d vDir)
	{
		this.init(ptObserver, vDir);
	}
	
	public GeomObserver3d(GeomObserver3d obs)
	{
		this.init(obs.getPtObserver(), obs.getUnitDir());
	}

	/* Methodes */
	
	public void init(GeomPoint3d ptObserver, GeomVector3d vDir)
	{
		GeomVector3d uDir3d = vDir.otherUnit();

		this.ptObserver = new GeomPoint3d(ptObserver);
		this.uDir = new GeomVector3d(ptObserver, uDir3d);
		//
		GeomVector2d vDir2d = new GeomVector2d(this.uDir);		
		GeomVector2d uDir2d = vDir2d.otherUnit(); 
		//
		GeomVector2d nDir2d = uDir2d.otherNorm();
		//
		this.nDir = new GeomVector3d(
			nDir2d.getXI(), 
			nDir2d.getYI(),
			ptObserver.getZ(),
			nDir2d.getXF(), 
			nDir2d.getYF(),
			ptObserver.getZ() );
	}

	/* OPERATIONS */

	// GEOMPOINT_OPERATIONS
	//
	public GeomPoint3d getPtFrom(double distMcs)
	{
		GeomPoint3d ptResult = getPtFrom(this.uDir, distMcs);
		return ptResult;
	}
	
	public GeomPoint3d getPtFrom(GeomVector3d vDir, double distMcs)
	{
		GeomVector3d uDir = vDir.otherUnit();
		//
		GeomPoint3d ptResult = this.ptObserver.otherMoveTo(uDir, distMcs);
		return ptResult;
	}

	// GEOMPLAN_OPERATIONS
	//	
	public GeomPlan3d getPlanFrom(double distToPlanMcs, double width, double height, double zHeight)
	{
		GeomPoint3d ptPlanOrig3d = this.ptObserver.otherMoveTo(uDir, distToPlanMcs);
		GeomPlan3d plan3d = new GeomPlan3d(ptPlanOrig3d, this.nDir, this.uDir, width, height, zHeight);
		return plan3d;
	}
	
	/* 3D-OBSERVER */
	
	public void moveForwardBackwardMcs(double dist)
	{
		GeomPoint3d ptNewObserver = this.ptObserver.otherMoveTo(this.uDir, dist);
		this.ptObserver = ptNewObserver;
	}
	
	public void turnLeftRightMcs(double dist)
	{
		double xIDir = this.ptObserver.getX();
		double yIDir = this.ptObserver.getY() + dist;
		double zIDir = this.ptObserver.getZ();
		//
		double xFDir = xIDir + this.uDir.getXOrig();
		double yFDir = yIDir + this.uDir.getYOrig();
		double zFDir = zIDir + this.uDir.getZOrig();
		
		//UNIT
		GeomVector3d vNewUnitDir = new GeomVector3d(xIDir, yIDir, zIDir, xFDir, yFDir, zFDir);
		this.uDir = vNewUnitDir.otherUnit();
	}
	
	public void turnUpDownMcs(double dist)
	{
		double xIDir = this.ptObserver.getX();
		double yIDir = this.ptObserver.getY();
		double zIDir = this.ptObserver.getZ() + dist;
		//
		double xFDir = xIDir + this.uDir.getXOrig();
		double yFDir = yIDir + this.uDir.getYOrig();
		double zFDir = zIDir + this.uDir.getZOrig();
		
		//UNIT
		GeomVector3d vNewUnitDir = new GeomVector3d(xIDir, yIDir, zIDir, xFDir, yFDir, zFDir);
		this.uDir = vNewUnitDir.otherUnit();
	}
	
	public void zoomTiltLeftRightMcs(double dist)
	{
		double xIDir = this.ptObserver.getX();
		double yIDir = this.ptObserver.getY();
		double zIDir = this.ptObserver.getZ();
		//
		double xFDir = xIDir + this.uDir.getXOrig();
		double yFDir = yIDir + this.uDir.getYOrig() + dist;
		double zFDir = zIDir + this.uDir.getZOrig();
		
		//UNIT
		GeomVector3d vNewUnitDir = new GeomVector3d(xIDir, yIDir, zIDir, xFDir, yFDir, zFDir);
		this.uDir = vNewUnitDir.otherUnit();
	}
	
	public void zoomTiltUpDownMcs(double dist)
	{
		double xIDir = this.ptObserver.getX();
		double yIDir = this.ptObserver.getY();
		double zIDir = this.ptObserver.getZ();
		//
		double xFDir = xIDir + this.uDir.getXOrig();
		double yFDir = yIDir + this.uDir.getYOrig();
		double zFDir = zIDir + this.uDir.getZOrig() + dist;
		
		//UNIT
		GeomVector3d vNewUnitDir = new GeomVector3d(xIDir, yIDir, zIDir, xFDir, yFDir, zFDir);
		this.uDir = vNewUnitDir.otherUnit();
	}

	public void zoomHeightUpDownMcs(double dist)
	{
		GeomVector3d zDir = new GeomVector3d(0.0, 0.0, 1.0);
		
		GeomPoint3d ptNewObserver = this.ptObserver.otherMoveTo(zDir, dist);
		this.ptObserver = new GeomPoint3d(ptNewObserver);

		GeomVector3d vNewUnitDir = this.uDir.otherMoveTo(zDir, dist);
		this.uDir = vNewUnitDir.otherUnit();

		GeomVector3d vNewNormDir = this.nDir.otherMoveTo(zDir, dist);
		this.nDir = vNewNormDir.otherUnit();		
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtObserver() {
		return ptObserver;
	}

	public void setPtObserver(GeomPoint3d ptObserver) {
		this.ptObserver = ptObserver;
	}

	public GeomVector3d getUnitDir() {
		return uDir;
	}

	public void setUnitDir(GeomVector3d vDir) {
		this.uDir = vDir.otherUnit();
	}

	public GeomVector3d getNormDir() {
		return nDir;
	}

	public void setNormDir(GeomVector3d nDir) {
		this.nDir = nDir;
	}
	
}
