/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PrepareDrawUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cmp.CmpGeomFace3d;

public class PrepareDrawUtil 
{
//Private
	private ArrayList<GeomFace3d> lsFace = null;
	
//Public
	
	public PrepareDrawUtil()
	{
		this.init();
	}
	
	/* Methodes */
	
	public void init()
	{
		this.lsFace = new ArrayList<GeomFace3d>();	
	}
	
	/* OPERATIONS */
	    
    public void addBox(ICadViewBase v, Color c, GeomPoint3d ptMin3dMcs, GeomPoint3d ptMax3dMcs)
    {
    	double xMinMcs = ptMin3dMcs.getX();
    	double yMinMcs = ptMin3dMcs.getY();
    	double zMinMcs = ptMin3dMcs.getZ();
    	//
    	double xMaxMcs = ptMax3dMcs.getX();
    	double yMaxMcs = ptMax3dMcs.getY();
    	double zMaxMcs = ptMax3dMcs.getZ();

    	GeomPoint3d pt1Mcs = new GeomPoint3d(xMinMcs, yMinMcs, zMinMcs); 
    	GeomPoint3d pt2Mcs = new GeomPoint3d(xMaxMcs, yMinMcs, zMinMcs); 
    	GeomPoint3d pt3Mcs = new GeomPoint3d(xMaxMcs, yMaxMcs, zMinMcs); 
    	GeomPoint3d pt4Mcs = new GeomPoint3d(xMinMcs, yMaxMcs, zMinMcs); 

    	GeomPoint3d pt5Mcs = new GeomPoint3d(xMinMcs, yMinMcs, zMaxMcs); 
    	GeomPoint3d pt6Mcs = new GeomPoint3d(xMaxMcs, yMinMcs, zMaxMcs); 
    	GeomPoint3d pt7Mcs = new GeomPoint3d(xMaxMcs, yMaxMcs, zMaxMcs); 
    	GeomPoint3d pt8Mcs = new GeomPoint3d(xMinMcs, yMaxMcs, zMaxMcs); 

        this.addFace(v, c, pt1Mcs, pt2Mcs, pt3Mcs, pt4Mcs);
        this.addFace(v, c, pt1Mcs, pt2Mcs, pt6Mcs, pt5Mcs);
        this.addFace(v, c, pt2Mcs, pt3Mcs, pt7Mcs, pt6Mcs);
        this.addFace(v, c, pt3Mcs, pt4Mcs, pt8Mcs, pt7Mcs);
        this.addFace(v, c, pt4Mcs, pt1Mcs, pt5Mcs, pt8Mcs);
        this.addFace(v, c, pt5Mcs, pt6Mcs, pt7Mcs, pt8Mcs);
    }
    
    public void addExternalFace(ICadViewBase v, Color c, ArrayList<GeomPoint3d> lsBasePtsMcs, ArrayList<GeomPoint3d> lsElevPtsMcs)
    {
		int sz = lsBasePtsMcs.size();

		GeomPoint3d ptBaseIMcs = lsBasePtsMcs.get(0);
		GeomPoint3d ptElevIMcs = lsElevPtsMcs.get(0);
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptBaseFMcs = lsBasePtsMcs.get(i);
			GeomPoint3d ptElevFMcs = lsElevPtsMcs.get(i);

			this.addFace(v, c, ptBaseIMcs, ptBaseFMcs, ptElevFMcs, ptElevIMcs);

			ptBaseIMcs = ptBaseFMcs;
			ptElevIMcs = ptElevFMcs;
		}
    }
	
    public synchronized GeomFace3d addFace(ICadViewBase v, Color c, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs)
    {
		GeomFace3d oFace = new GeomFace3d(c, pt0Mcs, pt1Mcs, pt2Mcs);
		this.lsFace.add(oFace);
		//
		return oFace;
    }
	
    public synchronized GeomFace3d addFace(ICadViewBase v, Color c, GeomPoint3d pt0Mcs, GeomPoint3d pt1Mcs, GeomPoint3d pt2Mcs, GeomPoint3d pt3Mcs)
    {
		GeomFace3d oFace = new GeomFace3d(c, pt0Mcs, pt1Mcs, pt2Mcs, pt3Mcs);
		this.lsFace.add(oFace);
		//
		return oFace;
    }
	
    public synchronized GeomFace3d addFace(ICadViewBase v, Color c, ArrayList<GeomPoint3d> lsPtsMcs)
    {
		GeomFace3d oFace = null;

		if(lsPtsMcs.size() < 3) return null;
    	
    	GeomPoint3d pt0Mcs = lsPtsMcs.get(0);
    	GeomPoint3d pt1Mcs = lsPtsMcs.get(1);
    	GeomPoint3d pt2Mcs = lsPtsMcs.get(2);
		GeomPoint3d pt3Mcs = null;

    	if(lsPtsMcs.size() > 3) {
    		pt3Mcs = lsPtsMcs.get(3);

    		oFace = new GeomFace3d(c, pt0Mcs, pt1Mcs, pt2Mcs, pt3Mcs);
    	}
    	else {
    		oFace = new GeomFace3d(c, pt0Mcs, pt1Mcs, pt2Mcs);
    	}

    	this.lsFace.add(oFace);

    	return oFace;
    }

    public synchronized ArrayList<GeomFace3d> retriveOrderedListOfFaces(GeomPoint3d ptObserver3d)
    {
    	CmpGeomFace3d c = new CmpGeomFace3d(ptObserver3d, false); 
    	this.lsFace.sort(c);
    	return this.lsFace;
    }
    
    public void drawAllWireframe(ICadViewBase v, Graphics g)
    {
    	GeomObserver3d oObservador = v.getObsMcs();
    	//
    	GeomPoint3d ptObserver3d = oObservador.getPtObserver();
    	ArrayList<GeomFace3d> ls = retriveOrderedListOfFaces(ptObserver3d);
    	for(GeomFace3d o : ls) {
    		o.drawEdgesMcs(v, o.getColor(), g);
    	}    	
    }
    
    public void drawAllSolid(ICadViewBase v, Graphics g)
    {
    	GeomObserver3d oObservador = v.getObsMcs();
    	//
    	GeomPoint3d ptObserver3d = oObservador.getPtObserver();
    	ArrayList<GeomFace3d> ls = retriveOrderedListOfFaces(ptObserver3d);
    	for(GeomFace3d o : ls) {
    		o.drawFaceMcs(v, o.getColor(), o.getColor(), g);
    	}    	
    }

}
