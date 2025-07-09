/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomFace3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.vo.NearPoint3dVO;

public class GeomFace3d 
{
//Private
	private int tipo = AppDefs.FACETYPE_NONE;
	//
	private Color c = null;
	//
	private GeomPoint3d pt0 = null;  
	private GeomPoint3d pt1 = null;
	private GeomPoint3d pt2 = null;
	private GeomPoint3d pt3 = null;
	//
	private GeomPoint3d ptCentroid = null;
	private GeomVector3d zDir = null;
	//
	private ArrayList<GeomPoint3d> lsPts = null;
	//
	private double distToCurrObserver = Double.MAX_VALUE;
	
//Public
	
	public GeomFace3d(
		Color c,
		GeomPoint3d pt0,  
		GeomPoint3d pt1,
		GeomPoint3d pt2)
	{
		this.init(c, pt0, pt1, pt2, null);
	}
	
	public GeomFace3d(
		Color c,
		GeomPoint3d pt0,  
		GeomPoint3d pt1,
		GeomPoint3d pt2,
		GeomPoint3d pt3)
	{
		this.init(c, pt0, pt1, pt2, pt3);
	}
	
	public GeomFace3d(GeomFace3d oFace)
	{
		this.init(c, oFace.pt0, oFace.pt1, oFace.pt2, oFace.pt3);
	}
	
	/* Methodes */
	
	public void init(Color c, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		if( (pt0 == null) || (pt1 == null) || (pt2 == null) ) return;
		
		this.c = c;
		
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.pt3 = pt3;

		this.lsPts = new ArrayList<GeomPoint3d>();
		this.lsPts.add(pt0);
		this.lsPts.add(pt1);
		this.lsPts.add(pt2);

		if(pt3 == null) {
			this.tipo = AppDefs.FACETYPE_3PTS;
		}
		else {
			this.tipo = AppDefs.FACETYPE_4PTS;
			this.lsPts.add(pt3);
		}

		this.ptCentroid = GeomUtil.centroidOf3d(lsPts);

		GeomVector3d vDirC0 = new GeomVector3d(this.ptCentroid, this.pt0);
		GeomVector3d vDirC1 = new GeomVector3d(this.ptCentroid, this.pt1);
		
		this.zDir = vDirC0.vectProd(vDirC1);
	}
	
	/* DRAW */
	
	public void drawVertexMcs(ICadViewBase v, Color vertexColor, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, vertexColor);
		
		for(GeomPoint3d oPt : this.lsPts) {
			DrawUtil.drawPointProj(v, oPt, AppDefs.POINT_SIZE, g);
		}
		
		GeomUtil.setColor(g, oldcol);
	}
		
	public void drawEdgesMcs(ICadViewBase v, Color edgeColor, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, edgeColor);
		
		DrawUtil.drawPolygonProj(v, this.lsPts, g);
		
		GeomUtil.setColor(g, oldcol);		
	}
		
	public void drawFaceMcs(ICadViewBase v, Color faceColor, Color edgeColor, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, faceColor);
		
		DrawUtil.fillPolygonProj(v, this.lsPts, g);
		
		GeomUtil.setColor(g, edgeColor);
		
		DrawUtil.drawPolygonProj(v, this.lsPts, g);
		
		GeomUtil.setColor(g, oldcol);		
	}
	
	public NearPoint3dVO nearPoint3d(GeomPoint3d ptBase3d)
	{
		GeomVector3d v01 = new GeomVector3d(this.pt0, this.pt1);
		GeomVector3d u01 = v01.otherUnit();
		
		GeomVector3d v02 = new GeomVector3d(this.pt0, this.pt2);

		GeomVector3d vZ = u01.vectProd(v02);
		GeomVector3d uZ = vZ.otherUnit();
		
		GeomVector3d vBaseToPt1 = new GeomVector3d(ptBase3d, this.ptCentroid);
		this.distToCurrObserver = Math.abs(uZ.dotProd(vBaseToPt1));

		NearPoint3dVO oResult = new NearPoint3dVO(this.ptCentroid, this.distToCurrObserver);			
		return oResult;
	}
		
	/* Getters/Setters */

	public int getTipo() {
		return tipo;
	}

	public GeomPoint3d getPt0() {
		return pt0;
	}

	public GeomPoint3d getPt1() {
		return pt1;
	}

	public GeomPoint3d getPt2() {
		return pt2;
	}

	public GeomPoint3d getPt3() {
		return pt3;
	}

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public GeomVector3d getZDir() {
		return zDir;
	}

	public ArrayList<GeomPoint3d> getLsPts() {
		return lsPts;
	}

	public Color getColor() {
		return c;
	}

	public double getDistToCurrObserver() {
		return distToCurrObserver;
	}

	public void setDistToCurrObserver(double distToCurrObserver) {
		this.distToCurrObserver = distToCurrObserver;
	}
	
}
