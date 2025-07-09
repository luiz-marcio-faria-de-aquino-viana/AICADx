/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Face3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom.shape;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;

public class ShapeFace3d 
{
//Private
	private boolean bFill = false;
	private ArrayList<GeomPoint3d> lsPts = null;
	
//Public
	
	public ShapeFace3d(boolean bFill)
	{
		this.init(bFill);
	}

	public ShapeFace3d(boolean bFill, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2)
	{
		this.init(bFill, pt0, pt1, pt2);
	}
	
	public ShapeFace3d(boolean bFill, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		this.init(bFill, pt0, pt1, pt2, pt3);
	}
	
	public ShapeFace3d(boolean bFill, ArrayList<GeomPoint3d> lsPts)
	{
		this.init(bFill, lsPts);
	}
	
	public ShapeFace3d(ShapeFace3d other)
	{
		this.init(other);
	}
	
	/* Methodes */
	
	public void init(boolean bFill)
	{
		this.bFill = bFill;
		this.lsPts = new ArrayList<GeomPoint3d>();
	}
	
	public void init(boolean bFill, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2)
	{
		this.bFill = bFill;
		//
		this.lsPts = new ArrayList<GeomPoint3d>();
		this.lsPts.add(pt0);
		this.lsPts.add(pt1);
		this.lsPts.add(pt2);
	}
	
	public void init(boolean bFill, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		this.bFill = bFill;
		//
		this.lsPts = new ArrayList<GeomPoint3d>();
		this.lsPts.add(pt0);
		this.lsPts.add(pt1);
		this.lsPts.add(pt2);
		this.lsPts.add(pt3);
	}
	
	public void init(boolean bFill, ArrayList<GeomPoint3d> lsPts)
	{
		this.bFill = bFill;
		//
		this.lsPts = new ArrayList<GeomPoint3d>();
		for(GeomPoint3d oPt : lsPts) {
			GeomPoint3d oNewPt = new GeomPoint3d(oPt); 
			this.lsPts.add(oNewPt);
		}
	}

	public void init(ShapeFace3d other)
	{
		this.bFill = other.bFill;
		//
		this.lsPts = new ArrayList<GeomPoint3d>();
		for(GeomPoint3d oPt : other.lsPts) {
			GeomPoint3d oNewPt = new GeomPoint3d(oPt); 
			this.lsPts.add(oNewPt);
		}
	}
	
	/* LIST */
	
	public synchronized int getSzLsPts() {
		int sz = this.lsPts.size();
		return sz;
	}
	
	public synchronized void addPoint(GeomPoint3d oPt) {
		GeomPoint3d oNewPt = new GeomPoint3d(oPt);
		this.lsPts.add(oNewPt);
	}
	
	public synchronized GeomPoint3d getPointAt(int pos) {
		int sz = this.lsPts.size();
		if(pos < sz) {
			GeomPoint3d oPt = this.lsPts.get(pos);
			return oPt;
		}
		return null;
	}
	
	/* Getters/Setters */

	public boolean isbFill() {
		return bFill;
	}

	public void setbFill(boolean bFill) {
		this.bFill = bFill;
	}

	public ArrayList<GeomPoint3d> getLsPts() {
		return lsPts;
	}

	public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}
	
}
