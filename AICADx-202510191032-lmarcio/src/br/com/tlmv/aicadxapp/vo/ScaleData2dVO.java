/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ScaleData2dVO.java
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

public class ScaleData2dVO 
{
//Private	
	//Reference Points
	private GeomPoint2d ptBase;	
	private GeomPoint2d ptDir;
	private GeomPoint2d ptRef;
	//Vectors
	private GeomVector2d vBaseDir;
	private GeomVector2d vBaseRef;
	//Values
	private double distBaseDir;
	private double distBaseRef;
	private double scale;
	//Origin Point and Vector
	private GeomPoint2d ptOrig;
	private GeomVector2d vBaseOrig;
	//Destination Point and Vector
	private GeomPoint2d ptDest;
	private GeomVector2d vBaseDest;
	
//Public
	
	//Points
	public ScaleData2dVO()
	{
		//Points
		this.ptBase = new GeomPoint2d(0.0, 0.0);	
		this.ptDir = new GeomPoint2d(0.0, 0.0);
		this.ptRef = new GeomPoint2d(0.0, 0.0);
		//Vectors
		this.vBaseDir = new GeomVector2d(0.0, 0.0);
		this.vBaseRef = new GeomVector2d(0.0, 0.0);
		//Values
		this.distBaseDir = 0.0;
		this.distBaseRef = 0.0;
		this.scale = 0.0;
		//Origin Point and Vector
		this.ptOrig = new GeomPoint2d(0.0, 0.0);
		this.vBaseOrig = new GeomVector2d(0.0, 0.0);
		//Destination Point and Vector
		this.ptDest = new GeomPoint2d(0.0, 0.0);
		this.vBaseDest = new GeomVector2d(0.0, 0.0);
	}
	
	/* Methodes */

	public void init(
		double refDist,
		GeomPoint2d ptBase,	
		GeomPoint2d ptDir)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint2d(ptBase);	
		this.ptDir = new GeomPoint2d(ptDir);
		
		GeomVector2d vDir = new GeomVector2d(this.ptBase, this.ptDir);
		this.ptRef = this.ptBase.otherMoveTo(vDir, refDist);
		
		calculate();
	}

	public void init(
		double refDist,
		GeomPoint3d ptBase,	
		GeomPoint3d ptDir)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint2d(ptBase);	
		this.ptDir = new GeomPoint2d(ptDir);
		
		GeomVector2d vDir = new GeomVector2d(this.ptBase, this.ptDir);
		this.ptRef = this.ptBase.otherMoveTo(vDir, refDist);
		
		calculate();
	}

	public void init(
		GeomPoint2d ptBase,	
		GeomPoint2d ptDir,
		GeomPoint2d ptRef)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint2d(ptBase);	
		this.ptDir = new GeomPoint2d(ptDir);
		this.ptRef = new GeomPoint2d(ptRef);

		calculate();
	}

	public void init(
		GeomPoint3d ptBase,	
		GeomPoint3d ptDir,
		GeomPoint3d ptRef)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint2d(ptBase);	
		this.ptDir = new GeomPoint2d(ptDir);
		this.ptRef = new GeomPoint2d(ptRef);

		calculate();
	}

	public double calculate()
	{
		this.scale = 1.0;
		
    	if(this.ptBase != null) {
    		this.vBaseRef = new GeomVector2d(this.ptBase, this.ptRef);
    		this.distBaseRef = this.vBaseRef.mod();
    		
    		this.vBaseDir = new GeomVector2d(this.ptBase, this.ptDir);
    		this.distBaseDir = this.vBaseDir.mod();

    		if(this.distBaseRef >= AppDefs.MATHPREC_MIN)
    			this.scale = this.distBaseDir / this.distBaseRef;	
    	}
    	return this.scale;
	}
	
	/* OPERATIONS */
	
	public GeomPoint2d scalePoint3d(GeomPoint3d ptOrig)
	{
		GeomPoint2d ptOrig2d = new GeomPoint2d(ptOrig);

		GeomPoint2d ptDest = scalePoint2d(ptOrig2d);
		return ptDest;
	}
	
	public GeomPoint2d scalePoint2d(GeomPoint2d ptOrig)
	{
		this.ptOrig = new GeomPoint2d(ptOrig);
		this.ptDest = new GeomPoint2d(this.ptOrig);
    	if(this.ptBase != null) {
    		this.vBaseOrig = new GeomVector2d(this.ptBase, this.ptOrig);  

    		this.vBaseDest = this.vBaseOrig.otherMult(this.scale);		
    		this.ptDest = new GeomPoint2d(this.vBaseDest.getXF(), this.vBaseDest.getYF());
    	}
    	return this.ptDest;
	}
	
	public double scaleValue(double val)
	{
		double result = val * this.scale;
    	return result;
	}

	/* Getters/Setters */
			
	public GeomPoint2d getPtBase() {
		return ptBase;
	}

	public void setPtBase(GeomPoint2d ptBase) {
		this.ptBase = ptBase;
	}

	public GeomPoint2d getPtDir() {
		return ptDir;
	}

	public void setPtDir(GeomPoint2d ptDir) {
		this.ptDir = ptDir;
	}

	public GeomVector2d getvBaseDir() {
		return vBaseDir;
	}

	public void setvBaseDir(GeomVector2d vBaseDir) {
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

	public GeomPoint2d getPtRef() {
		return ptRef;
	}

	public void setPtRef(GeomPoint2d ptRef) {
		this.ptRef = ptRef;
	}

	public GeomVector2d getvBaseRef() {
		return vBaseRef;
	}

	public void setvBaseRef(GeomVector2d vBaseRef) {
		this.vBaseRef = vBaseRef;
	}

	public double getDistBaseRef() {
		return distBaseRef;
	}

	public void setDistBaseRef(double distBaseRef) {
		this.distBaseRef = distBaseRef;
	}

	public GeomPoint2d getPtOrig() {
		return ptOrig;
	}

	public void setPtOrig(GeomPoint2d ptOrig) {
		this.ptOrig = ptOrig;
	}

	public GeomVector2d getvBaseOrig() {
		return vBaseOrig;
	}

	public void setvBaseOrig(GeomVector2d vBaseOrig) {
		this.vBaseOrig = vBaseOrig;
	}

	public GeomPoint2d getPtDest() {
		return ptDest;
	}

	public void setPtDest(GeomPoint2d ptDest) {
		this.ptDest = ptDest;
	}

	public GeomVector2d getvBaseDest() {
		return vBaseDest;
	}

	public void setvBaseDest(GeomVector2d vBaseDest) {
		this.vBaseDest = vBaseDest;
	}
		
}
