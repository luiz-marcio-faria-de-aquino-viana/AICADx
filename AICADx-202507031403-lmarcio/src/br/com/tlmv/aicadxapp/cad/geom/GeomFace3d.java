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
import br.com.tlmv.aicadxapp.cad.CadEntity;
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
	private GeomPoint3d ptCentroid = null;
	private GeomVector3d zDir = null;
	//
	private ArrayList<GeomPoint3d> lsPts = null;
	//
	private CadEntity oEnt = null;
	private GeomPoint3d ptEntCentroid = null;
	private double distFromEntToCurrObserver = Double.MAX_VALUE;
	//
	private double distToCurrObserver = Double.MAX_VALUE;
	//
	private ArrayList<GeomFace3d> lsFace = null;
	
//Public
	
	public GeomFace3d(
		CadEntity oEnt,
		Color c,
		GeomPoint3d pt0,  
		GeomPoint3d pt1,
		GeomPoint3d pt2)
	{
		this.init(oEnt, c, pt0, pt1, pt2);
	}
	
	public GeomFace3d(
		CadEntity oEnt,
		Color c,
		GeomPoint3d pt0,  
		GeomPoint3d pt1,
		GeomPoint3d pt2,
		GeomPoint3d pt3)
	{
		this.init(oEnt, c, pt0, pt1, pt2, pt3);
	}
	
	public GeomFace3d(
		CadEntity oEnt,
		Color c,
		ArrayList<GeomPoint3d> lsPts)
	{
		this.init(oEnt, c, lsPts);
	}
		
	public GeomFace3d(GeomFace3d oFace)
	{
		ArrayList<GeomPoint3d> lsPts = oFace.getLsPts();
		this.init(oFace.oEnt, oFace.c, lsPts);
	}
	
	/* Methodes */
	
	public void init(CadEntity oEnt, Color c, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2)
	{
		if( (pt0 == null) || (pt1 == null) || (pt2 == null) ) return;

		this.oEnt = oEnt;
		
		this.c = c;
		
		this.lsPts = new ArrayList<GeomPoint3d>();
		this.lsPts.add(pt0);
		this.lsPts.add(pt1);
		this.lsPts.add(pt2);

		this.lsFace = new ArrayList<GeomFace3d>();
		
		this.tipo = AppDefs.FACETYPE_3PTS;

		this.ptEntCentroid = new GeomPoint3d(this.oEnt.centroid());
		
		this.ptCentroid = GeomUtil.centroidOf3d(lsPts);

		GeomVector3d vDirC0 = new GeomVector3d(this.ptCentroid, pt0);
		GeomVector3d vDirC1 = new GeomVector3d(this.ptCentroid, pt1);
		
		this.zDir = vDirC0.vectProd(vDirC1);
	}
	
	public void init(CadEntity oEnt, Color c, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3)
	{
		if( (pt0 == null) || (pt1 == null) || (pt2 == null) || (pt3 == null) ) return;

		this.oEnt = oEnt;
		
		this.c = c;
		
		this.lsPts = new ArrayList<GeomPoint3d>();
		this.lsPts.add(pt0);
		this.lsPts.add(pt1);
		this.lsPts.add(pt2);
		this.lsPts.add(pt3);

		this.lsFace = new ArrayList<GeomFace3d>();

		this.tipo = AppDefs.FACETYPE_4PTS;

		this.ptCentroid = GeomUtil.centroidOf3d(lsPts);

		this.ptEntCentroid = new GeomPoint3d(this.ptCentroid); 
		if(this.oEnt != null)
			this.ptEntCentroid = new GeomPoint3d(this.oEnt.centroid());
		
		GeomVector3d vDirC0 = new GeomVector3d(this.ptCentroid, pt0);
		GeomVector3d vDirC1 = new GeomVector3d(this.ptCentroid, pt1);
		
		this.zDir = vDirC0.vectProd(vDirC1);
	}
	
	public void init(CadEntity oEnt, Color c, ArrayList<GeomPoint3d> lsPts)
	{
		if(lsPts.size() < 3) return;

		this.oEnt = oEnt;
		
		this.c = c;

		GeomPoint3d pt0 = lsPts.get(0);
		GeomPoint3d pt1 = lsPts.get(1);
		
		this.lsPts = new ArrayList<GeomPoint3d>();
		for(GeomPoint3d oPts : lsPts) {
			this.lsPts.add(oPts);
		}

		this.lsFace = new ArrayList<GeomFace3d>();

		this.tipo = AppDefs.FACETYPE_NPTS;

		this.ptCentroid = GeomUtil.centroidOf3d(lsPts);

		this.ptEntCentroid = new GeomPoint3d(this.ptCentroid); 
		if(this.oEnt != null)
			this.ptEntCentroid = new GeomPoint3d(this.oEnt.centroid());

		GeomVector3d vDirC0 = new GeomVector3d(this.ptCentroid, pt0);
		GeomVector3d vDirC1 = new GeomVector3d(this.ptCentroid, pt1);
		
		this.zDir = vDirC0.vectProd(vDirC1);
	}
	
	/* TRANSFORM */
	
	public void transformTo(GeomPoint3d ptRef, GeomVector3d vDir3d, double dist, double stepAngleXRad, double stepAngleYRad, double stepAngleZRad) {
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();

		for(GeomPoint3d oPts : this.lsPts) {
			GeomPoint3d oNewPts = GeomUtil.transformTo(ptRef, oPts, vDir3d, dist, stepAngleXRad, stepAngleYRad, stepAngleZRad);
			newLsPts.add(oNewPts);
		}
		this.lsPts = newLsPts;
		
		for(GeomFace3d oFace : this.lsFace) {
			oFace.transformTo(ptRef, vDir3d, dist, stepAngleXRad, stepAngleYRad, stepAngleZRad);
		}
	}
	
	/* CHECKxxx */
	
	public boolean checkIfOnScreen(ICadViewBase v)
	{
		//Origin of Projection Plan
		GeomPlan3d planProj = v.getPlanProj();
		GeomPoint2d ptOrigemProj2d = new GeomPoint2d(planProj.getPtCenter());

		//Observer Position
		GeomObserver3d obs = v.getObsMcs();
		GeomPoint2d ptObserver2d = new GeomPoint2d(obs.getPtObserver());		

		GeomVector2d vPtObserverToPtOrigem2d = new GeomVector2d(ptObserver2d, ptOrigemProj2d);
		GeomVector2d uPtObserverToPtOrigem2d = vPtObserverToPtOrigem2d.otherUnit();
		
		for(GeomPoint3d oPt : this.lsPts) {
			GeomPoint2d oPt2d = new GeomPoint2d(oPt);
			
			GeomVector2d vPtOrigemToPt2d = new GeomVector2d(ptOrigemProj2d, oPt2d);
			double d = uPtObserverToPtOrigem2d.dotProd(vPtOrigemToPt2d);
			if(d < 0.0) {
				return false;
			}
		}
		return true;
	}
	
	/* DRAW */
	
	public void drawVertexMcs(ICadViewBase v, Color vertexColor, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, vertexColor);
		
		for(GeomPoint3d oPt : this.lsPts) {
			DrawUtil.drawPointProj(v, oPt, AppDefs.POINT_SIZE, g);
		}
				
		for(GeomFace3d oFace : this.lsFace) {
			oFace.drawVertexMcs(v, vertexColor, g);
		}
		
		GeomUtil.setColor(g, oldcol);
	}
		
	public void drawEdgesMcs(ICadViewBase v, Color edgeColor, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, edgeColor);
		
		DrawUtil.drawPolygonProj(v, this.lsPts, g);
		
		for(GeomFace3d oFace : this.lsFace) {
			oFace.drawEdgesMcs(v, edgeColor, g);
		}
		
		GeomUtil.setColor(g, oldcol);		
	}
		
	public void drawFaceMcs(ICadViewBase v, Color faceColor, Color edgeColor, Graphics g)
	{
		Color oldcol = GeomUtil.setColor(g, faceColor);

		DrawUtil.fillFaceProj(v, this.lsPts, g);

		GeomUtil.setColor(g, edgeColor);
		
		DrawUtil.drawFaceProj(v, this.lsPts, g);
		
		for(GeomFace3d oFace : this.lsFace) {
			oFace.drawFaceMcs(v, faceColor, edgeColor, g);
		}
		
		GeomUtil.setColor(g, oldcol);		
	}
	
	public NearPoint3dVO nearPoint3d(GeomPoint3d ptBase3d)
	{
		//Entity Distance
		this.distFromEntToCurrObserver = ptBase3d.distTo(this.ptEntCentroid);
		
		GeomPoint3d pt0 = lsPts.get(0);  
		GeomPoint3d pt1 = lsPts.get(1);
		GeomPoint3d pt2 = lsPts.get(2);
		
		//Face Distance
		GeomVector3d v01 = new GeomVector3d(pt0, pt1);
		GeomVector3d u01 = v01.otherUnit();
		
		GeomVector3d v02 = new GeomVector3d(pt0, pt2);

		GeomVector3d vZ = u01.vectProd(v02);
		GeomVector3d uZ = vZ.otherUnit();
		
		GeomVector3d vBaseToPt1 = new GeomVector3d(ptBase3d, this.ptCentroid);
		
		this.distToCurrObserver = Math.abs(uZ.dotProd(vBaseToPt1));

		NearPoint3dVO oResult = new NearPoint3dVO(
			this.ptEntCentroid, 
			this.distFromEntToCurrObserver, 
			this.ptCentroid, 
			this.distToCurrObserver);			
		return oResult;
	}
		
	/* Getters/Setters */

	public int getTipo() {
		return tipo;
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
