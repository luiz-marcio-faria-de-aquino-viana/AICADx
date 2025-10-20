/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ScaleData3dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;

public class ScaleData3dVO 
{
//Private	
	//Points
	private GeomPoint3d ptBase;	
	private GeomPoint3d ptDir;
	private GeomPoint3d ptRef;
	//Vectors
	private GeomVector3d vBaseDir;
	private GeomVector3d vBaseRef;
	//Values
	private double distBaseDir;
	private double distBaseRef;
	private double scale;
	//Origin Point and Vector
	private GeomPoint3d ptOrig;
	private GeomVector3d vBaseOrig;
	//Destination Point and Vector
	private GeomPoint3d ptDest;
	private GeomVector3d vBaseDest;
	
//Public
	
	//Points
	public ScaleData3dVO()
	{
		//Points
		this.ptBase = new GeomPoint3d(0.0, 0.0, 0.0);	
		this.ptDir = new GeomPoint3d(0.0, 0.0, 0.0);
		this.ptRef = new GeomPoint3d(0.0, 0.0, 0.0);
		//Vectors
		this.vBaseDir = new GeomVector3d(0.0, 0.0, 0.0);
		this.vBaseRef = new GeomVector3d(0.0, 0.0, 0.0);
		//Values
		this.distBaseDir = 0.0;
		this.distBaseRef = 0.0;
		this.scale = 0.0;
		//Origin Point and Vector
		this.ptOrig = new GeomPoint3d(0.0, 0.0, 0.0);
		this.vBaseOrig = new GeomVector3d(0.0, 0.0, 0.0);
		//Destination Point and Vector
		this.ptDest = new GeomPoint3d(0.0, 0.0, 0.0);
		this.vBaseDest = new GeomVector3d(0.0, 0.0, 0.0);
	}
	
	/* Methodes */

	public void init(
		double refDist,
		GeomPoint2d ptBase,	
		GeomPoint2d ptDir)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint3d(ptBase);	
		this.ptDir = new GeomPoint3d(ptDir);
		
		GeomVector3d vDir = new GeomVector3d(this.ptBase, this.ptDir);
		this.ptRef = this.ptBase.otherMoveTo(vDir, refDist);
		
		calculate();
	}

	public void init(
		double refDist,
		GeomPoint3d ptBase,	
		GeomPoint3d ptDir)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint3d(ptBase);	
		this.ptDir = new GeomPoint3d(ptDir);
		
		GeomVector3d vDir = new GeomVector3d(this.ptBase, this.ptDir);
		this.ptRef = this.ptBase.otherMoveTo(vDir, refDist);
		
		calculate();
	}

	public void init(
		GeomPoint2d ptBase,	
		GeomPoint2d ptDir,
		GeomPoint2d ptRef)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint3d(ptBase);	
		this.ptDir = new GeomPoint3d(ptDir);
		this.ptRef = new GeomPoint3d(ptRef);

		calculate();
	}

	public void init(
		GeomPoint3d ptBase,	
		GeomPoint3d ptDir,
		GeomPoint3d ptRef)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint3d(ptBase);	
		this.ptDir = new GeomPoint3d(ptDir);
		this.ptRef = new GeomPoint3d(ptRef);

		calculate();
	}

	public double calculate()
	{
		this.vBaseRef = new GeomVector3d(this.ptBase, this.ptRef);
		this.distBaseRef = this.vBaseRef.mod();
		
    	if(this.ptBase != null) {
    		this.vBaseDir = new GeomVector3d(this.ptBase, this.ptDir);
    		this.distBaseDir = this.vBaseDir.mod();

    		if(this.distBaseRef >= AppDefs.MATHPREC_MIN)
    			this.scale = this.distBaseDir / this.distBaseRef;	
    	}
    	return this.scale;
	}
	
	/* OPERATIONS */
	
	public GeomPoint3d scalePoint3d(GeomPoint2d ptOrig)
	{
		GeomPoint3d ptOrig3d = new GeomPoint3d(ptOrig);

		GeomPoint3d ptDest = scalePoint3d(ptOrig3d);
		return ptDest;
	}
	
	public GeomPoint3d scalePoint3d(GeomPoint3d ptOrig)
	{
		this.ptOrig = new GeomPoint3d(ptOrig);
		this.ptDest = new GeomPoint3d(this.ptOrig);
    	if(this.ptBase != null) {
    		this.vBaseOrig = new GeomVector3d(this.ptBase, this.ptOrig);  

    		this.vBaseDest = this.vBaseOrig.otherMult(this.scale);		
    		this.ptDest = new GeomPoint3d(this.vBaseDest.getXF(), this.vBaseDest.getYF(), this.vBaseDest.getZF());
    	}
    	return this.ptDest;
	}
	
	public double scaleValue(double val)
	{
		double result = val * this.scale;
    	return result;
	}

	/* Getters/Setters */
						
	public GeomPoint3d getPtBase() {
		return ptBase;
	}

	public void setPtBase(GeomPoint3d ptBase) {
		this.ptBase = ptBase;
	}

	public GeomPoint3d getPtDir() {
		return ptDir;
	}

	public void setPtDir(GeomPoint3d ptDir) {
		this.ptDir = ptDir;
	}

	public GeomVector3d getvBaseDir() {
		return vBaseDir;
	}

	public void setvBaseDir(GeomVector3d vBaseDir) {
		this.vBaseDir = vBaseDir;
	}

	public double getDistBaseDir() {
		return distBaseDir;
	}

	public void setDistBaseDir(double distBaseDir) {
		this.distBaseDir = distBaseDir;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public GeomPoint3d getPtRef() {
		return ptRef;
	}

	public void setPtRef(GeomPoint3d ptRef) {
		this.ptRef = ptRef;
	}

	public GeomVector3d getvBaseRef() {
		return vBaseRef;
	}

	public void setvBaseRef(GeomVector3d vBaseRef) {
		this.vBaseRef = vBaseRef;
	}

	public double getDistBaseRef() {
		return distBaseRef;
	}

	public void setDistBaseRef(double distBaseRef) {
		this.distBaseRef = distBaseRef;
	}

	public GeomPoint3d getPtOrig() {
		return ptOrig;
	}

	public void setPtOrig(GeomPoint3d ptOrig) {
		this.ptOrig = ptOrig;
	}

	public GeomVector3d getvBaseOrig() {
		return vBaseOrig;
	}

	public void setvBaseOrig(GeomVector3d vBaseOrig) {
		this.vBaseOrig = vBaseOrig;
	}

	public GeomPoint3d getPtDest() {
		return ptDest;
	}

	public void setPtDest(GeomPoint3d ptDest) {
		this.ptDest = ptDest;
	}

	public GeomVector3d getvBaseDest() {
		return vBaseDest;
	}

	public void setvBaseDest(GeomVector3d vBaseDest) {
		this.vBaseDest = vBaseDest;
	}
		
}
