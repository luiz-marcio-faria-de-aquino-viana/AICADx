/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * Face3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
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

	public boolean isFill() {
		return bFill;
	}

	public void setFill(boolean bFill) {
		this.bFill = bFill;
	}

	public ArrayList<GeomPoint3d> getLsPts() {
		return lsPts;
	}

	public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}
	
}
