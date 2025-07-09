/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MoveData3dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;

public class MoveData3dVO 
{
//Private	
	//Points
	private GeomPoint3d ptBase;	
	private GeomPoint3d ptDir;
	//Vectors
	private GeomVector3d vBaseDir;
	//Values
	private double distBaseDir;
	//Origin Point and Vector
	private GeomPoint3d ptOrig;
	private GeomVector3d vBaseOrig;
	//Destination Point and Vector
	private GeomPoint3d ptDest;
	private GeomVector3d vBaseDest;
	
//Public
	
	//Points
	public MoveData3dVO()
	{
		//Points
		this.ptBase = new GeomPoint3d(0.0, 0.0, 0.0);	
		this.ptDir = new GeomPoint3d(0.0, 0.0, 0.0);
		//Vectors
		this.vBaseDir = new GeomVector3d(0.0, 0.0, 0.0);
		//Values
		this.distBaseDir = 0.0;
		//Origin Point and Vector
		this.ptOrig = new GeomPoint3d(0.0, 0.0, 0.0);
		this.vBaseOrig = new GeomVector3d(0.0, 0.0, 0.0);
		//Destination Point and Vector
		this.ptDest = new GeomPoint3d(0.0, 0.0, 0.0);
		this.vBaseDest = new GeomVector3d(0.0, 0.0, 0.0);
	}
	
	/* Methodes */

	public void init(
		GeomPoint2d ptBase,	
		GeomPoint2d ptDir)
	{
		GeomPoint3d ptBase3d = null;
		if(ptBase != null)
			ptBase3d = new GeomPoint3d(ptBase);	
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir);

		this.init(ptBase3d, ptDir3d);
	}

	public void init(
		GeomPoint3d ptBase,	
		GeomPoint3d ptDir)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint3d(ptBase);	
		this.ptDir = new GeomPoint3d(ptDir);

    	if(this.ptBase != null) {
    		this.vBaseDir = new GeomVector3d(this.ptBase, this.ptDir);
    		this.distBaseDir = this.vBaseDir.mod();
    	}
	}
	
	/* OPERATIONS */
	
	public GeomPoint3d movePoint3d(GeomPoint2d ptOrig)
	{
		GeomPoint3d ptOrig3d = new GeomPoint3d(ptOrig);

		GeomPoint3d ptDest = movePoint3d(ptOrig3d);
		return ptDest;
	}
	
	public GeomPoint3d movePoint3d(GeomPoint3d ptOrig)
	{
		this.ptOrig = new GeomPoint3d(ptOrig);
		this.ptDest = this.ptOrig.otherMoveTo(this.vBaseDir, this.distBaseDir);
    	if(this.ptBase != null) {
    		this.vBaseOrig = new GeomVector3d(this.ptBase, this.ptOrig);
    		this.vBaseDest = new GeomVector3d(this.ptBase, this.ptDest);
    	}
    	return this.ptDest;
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

	public GeomPoint3d getPtOrig() {
		return ptOrig;
	}

	public void setPtOrig(GeomPoint3d ptOrig) {
		this.ptOrig = ptOrig;
	}

	public GeomVector3d getBaseOrig() {
		return vBaseOrig;
	}

	public void setBaseOrig(GeomVector3d vBaseOrig) {
		this.vBaseOrig = vBaseOrig;
	}

	public GeomPoint3d getPtDest() {
		return ptDest;
	}

	public void setPtDest(GeomPoint3d ptDest) {
		this.ptDest = ptDest;
	}

	public GeomVector3d getBaseDest() {
		return vBaseDest;
	}

	public void setBaseDest(GeomVector3d vBaseDest) {
		this.vBaseDest = vBaseDest;
	}

}
