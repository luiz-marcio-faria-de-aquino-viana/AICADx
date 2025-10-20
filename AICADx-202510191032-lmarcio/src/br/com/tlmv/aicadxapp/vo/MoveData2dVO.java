/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MoveData3dVO.java
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

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;

public class MoveData2dVO 
{
//Private	
	//Points
	private GeomPoint2d ptBase;	
	private GeomPoint2d ptDir;
	//Vectors
	private GeomVector2d vBaseDir;
	//Values
	private double distBaseDir;
	//Origin Point and Vector
	private GeomPoint2d ptOrig;
	private GeomVector2d vBaseOrig;
	//Destination Point and Vector
	private GeomPoint2d ptDest;
	private GeomVector2d vBaseDest;
	
//Public
	
	//Points
	public MoveData2dVO()
	{
		//Points
		this.ptBase = new GeomPoint2d(0.0, 0.0);	
		this.ptDir = new GeomPoint2d(0.0, 0.0);
		//Vectors
		this.vBaseDir = new GeomVector2d(0.0, 0.0);
		//Values
		this.distBaseDir = 0.0;
		//Origin Point and Vector
		this.ptOrig = new GeomPoint2d(0.0, 0.0);
		this.vBaseOrig = new GeomVector2d(0.0, 0.0);
		//Destination Point and Vector
		this.ptDest = new GeomPoint2d(0.0, 0.0);
		this.vBaseDest = new GeomVector2d(0.0, 0.0);
	}
	
	/* Methodes */

	public void init(
		GeomPoint3d ptBase,	
		GeomPoint3d ptDir)
	{
		GeomPoint2d ptBase2d = null;
		if(ptBase != null)
			ptBase2d = new GeomPoint2d(ptBase);	
		GeomPoint2d ptDir2d = new GeomPoint2d(ptDir);

		this.init(ptBase2d, ptDir2d);
	}

	public void init(
		GeomPoint2d ptBase,	
		GeomPoint2d ptDir)
	{
		if(ptBase != null)
			this.ptBase = new GeomPoint2d(ptBase);	
		this.ptDir = new GeomPoint2d(ptDir);

    	if(this.ptBase != null) {
    		this.vBaseDir = new GeomVector2d(this.ptBase, this.ptDir);
    		this.distBaseDir = this.vBaseDir.mod();
    	}
	}
	
	/* OPERATIONS */
	
	public GeomPoint2d movePoint3d(GeomPoint3d ptOrig)
	{
		GeomPoint2d ptOrig2d = new GeomPoint2d(ptOrig);

		GeomPoint2d ptDest = movePoint2d(ptOrig2d);
		return ptDest;
	}
	
	public GeomPoint2d movePoint2d(GeomPoint2d ptOrig)
	{
		this.ptOrig = new GeomPoint2d(ptOrig);
		this.ptDest = new GeomPoint2d(this.ptOrig);
    	if(this.ptBase != null) {
    		this.vBaseOrig = new GeomVector2d(this.ptBase, this.ptOrig);
    		this.vBaseDest = new GeomVector2d(this.ptBase, this.ptDest);

    		this.ptDest = this.ptOrig.otherMoveTo(this.vBaseDir, this.distBaseDir);
    	}
    	return this.ptDest;
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
