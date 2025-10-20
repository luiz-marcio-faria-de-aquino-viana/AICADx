/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomSection3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
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
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.vo.NearPoint3dVO;

public class GeomSection3d 
{
//Private
	private ArrayList<GeomPoint3d> lsPts = null;
	private GeomPoint3d ptCentroid = null;
	private GeomVector3d zDir = null;	
	
//Public
	
	public GeomSection3d()
	{
		this.init();
	}
		
	public GeomSection3d(GeomSection3d other)
	{
		this.init(other);
	}
	
	public GeomSection3d(GeomSection3d other, GeomVector3d vDir, double dist)
	{
		this.init(other, vDir, dist);
	}
	
	/* Methodes */
	
	public void init()
	{
		this.lsPts = new ArrayList<GeomPoint3d>();
		this.ptCentroid = new GeomPoint3d(0.0, 0.0, 0.0);
		this.zDir = new GeomVector3d(0.0, 0.0, 1.0);
	}
	
	public void init(GeomSection3d other)
	{
		int sz = other.lsPts.size();
		if(sz < 3) return;
		
		this.lsPts = GeomUtil.copyPt3dList(other.lsPts);
		this.ptCentroid = GeomUtil.centroidOf3d(this.lsPts);

		GeomPoint3d pt0 = this.lsPts.get(0);
		GeomPoint3d pt1 = this.lsPts.get(1);
		
		GeomVector3d vDirC0 = new GeomVector3d(this.ptCentroid, pt0);
		GeomVector3d vDirC1 = new GeomVector3d(this.ptCentroid, pt1);
		
		this.zDir = vDirC0.vectProd(vDirC1);
	}
	
	public void init(GeomSection3d other, GeomVector3d uDir, double dist)
	{
		int sz = other.lsPts.size();
		if(sz < 3) return;
		
		this.lsPts = GeomUtil.from3dTo3d(other.lsPts, uDir, dist);
		this.ptCentroid = GeomUtil.centroidOf3d(this.lsPts);

		GeomPoint3d pt0 = this.lsPts.get(0);
		GeomPoint3d pt1 = this.lsPts.get(1);
		
		GeomVector3d vDirC0 = new GeomVector3d(this.ptCentroid, pt0);
		GeomVector3d vDirC1 = new GeomVector3d(this.ptCentroid, pt1);
		
		this.zDir = vDirC0.vectProd(vDirC1);
	}
	
	/* MAX/MIN Z-HEIGHT */
	
	public double[] maxMinZHeight()
	{
		int sz = this.lsPts.size();
		if(sz < 3) return null;
		
		double zPtMin = Double.MAX_VALUE;
		double zPtMax = - Double.MAX_VALUE;
		
		for(GeomPoint3d oPt : this.lsPts) {
			if(oPt.getZ() < zPtMin)
				zPtMin = oPt.getZ();
			if(oPt.getZ() > zPtMax)
				zPtMax = oPt.getZ();
		}
		
		double[] arrResult = new double[2];
		arrResult[0] = zPtMin;
		arrResult[1] = zPtMax;
		return arrResult;
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
		
		GeomUtil.setColor(g, oldcol);		
	}

	/* 3D POINT - INSERT... */
	
	public void insert(GeomPoint3d pt3d) {
		this.lsPts.add(pt3d);
	}
	
	public void insert(int pos, GeomPoint3d pt3d) {
		int sz = this.lsPts.size();
		if(pos < sz) {
			this.lsPts.add(pos, pt3d);
		}
	}
	
    public void insert(double dist, double width, double baseHeight, double height, GeomVector3d uDir, GeomPoint3d pt3d)
    {
    	double[] arrZ = this.maxMinZHeight();
    	
    	double zMin = arrZ[0];
    	double zMax = arrZ[1];
    	
    	int sz = this.lsPts.size();
    	for(int i = 0; i < sz; i++) {
        	GeomPoint3d oPt = this.lsPts.get(i);
        	
        	double zPt = oPt.getZ();
        	if( (zPt >= zMin) && (zPt <= zMax) ) {
        		double distPt = pt3d.distTo(oPt);
        		
        		if(dist < distPt) {
        			GeomPoint3d ptBaseA3d = pt3d.otherMoveTo(uDir, dist);
        			GeomPoint3d ptBaseB3d = ptBaseA3d.otherMoveTo(uDir, width);
        			GeomPoint3d ptTopA3d = new GeomPoint3d(ptBaseA3d.getX(), ptBaseA3d.getY(), ptBaseA3d.getZ() + baseHeight + height);
        			GeomPoint3d ptTopB3d = new GeomPoint3d(ptBaseB3d.getX(), ptBaseB3d.getY(), ptBaseB3d.getZ() + baseHeight + height);
        			
        			this.lsPts.add(i, ptBaseA3d);
        			this.lsPts.add(i + 1, ptTopA3d);
        			this.lsPts.add(i + 2, ptTopB3d);
        			this.lsPts.add(i + 3, ptBaseB3d);

        			break;
        		}
        	}
    	}
    }
		
	/* Getters/Setters */

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public GeomVector3d getZDir() {
		return zDir;
	}

	public ArrayList<GeomPoint3d> getLsPts() {
		return lsPts;
	}
	
}
