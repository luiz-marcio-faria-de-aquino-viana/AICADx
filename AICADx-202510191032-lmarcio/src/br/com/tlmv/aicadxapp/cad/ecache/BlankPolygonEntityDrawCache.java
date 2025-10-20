/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BlankPolygonEntityDrawCache.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/08/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.ecache;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;

public class BlankPolygonEntityDrawCache extends EntityDrawCache
{
//Private
	private ArrayList<GeomPoint3d> lsPts3d = null; 
	private ArrayList<GeomPoint2d> lsPts2d = null; 
	//
	private GeomDimension2d oDimension2d = null;

//Public
	
	public BlankPolygonEntityDrawCache() 
	{
		this.init();
	}
	
	public BlankPolygonEntityDrawCache(Color oColor, Stroke oStroke) 
	{
		this.init(oColor, oStroke);
	}

	public BlankPolygonEntityDrawCache(IEntityDrawCache other) {
		this.init(other);
	}

	public BlankPolygonEntityDrawCache(Color oColor, Stroke oLtype, ArrayList<GeomPoint2d> lsPts2d, ArrayList<GeomPoint3d> lsPts3d) {
		if(lsPts2d != null) {
			this.init2d(oColor, oLtype, lsPts2d);			
		}
		else if(lsPts3d != null) {
			this.init3d(oColor, oLtype, lsPts3d);			
		}
		else {
			this.lsPts3d = new ArrayList<GeomPoint3d>(); 
			this.lsPts2d = new ArrayList<GeomPoint2d>(); 			
		}
	}
	
	/* Methodes */
	
	@Override
	public void init() 
	{
		super.init();

		this.lsPts3d = new ArrayList<GeomPoint3d>(); 
		this.lsPts2d = new ArrayList<GeomPoint2d>(); 
	}
	
	@Override
	public void init(Color oColor, Stroke oStroke) 
	{
		super.init(oColor, oStroke);

		this.lsPts3d = new ArrayList<GeomPoint3d>(); 
		this.lsPts2d = new ArrayList<GeomPoint2d>(); 
	}

	@Override
	public void init(IEntityDrawCache other) {
		super.init(other.getColor(), other.getLtype());

		this.lsPts2d = new ArrayList<GeomPoint2d>();
		this.lsPts3d = new ArrayList<GeomPoint3d>(); 
		for(GeomPoint2d oPt2d : lsPts2d) {
			this.lsPts2d.add( new GeomPoint2d(oPt2d) );
			this.lsPts3d.add( new GeomPoint3d(oPt2d.getTagId(), oPt2d.getTagName(), oPt2d.getX(), oPt2d.getY(), 0.0) );
		}		
	}

	@Override
	public void init2d(Color oColor, Stroke oLtype, ArrayList<GeomPoint2d> lsPts2d) {
		super.init(oColor, oLtype);

		this.lsPts2d = new ArrayList<GeomPoint2d>();
		this.lsPts3d = new ArrayList<GeomPoint3d>(); 
		for(GeomPoint2d oPt2d : lsPts2d) {
			this.lsPts2d.add( new GeomPoint2d(oPt2d) );
			this.lsPts3d.add( new GeomPoint3d(oPt2d.getTagId(), oPt2d.getTagName(), oPt2d.getX(), oPt2d.getY(), 0.0) );
		}
	}

	@Override
	public void init3d(Color oColor, Stroke oLtype, ArrayList<GeomPoint3d> lsPts3d) {
		super.init(oColor, oLtype);

		this.lsPts2d = new ArrayList<GeomPoint2d>();
		this.lsPts3d = new ArrayList<GeomPoint3d>(); 
		for(GeomPoint3d oPt3d : lsPts3d) {
			this.lsPts3d.add( new GeomPoint3d(oPt3d) );
			this.lsPts2d.add( new GeomPoint2d(oPt3d.getTagId(), oPt3d.getTagName(), oPt3d.getX(), oPt3d.getY()) );
		}
	}

	/* BASIC_OPERATIONS */
	
	public void addLine2d(GeomPoint2d oPtI2d, GeomPoint2d oPtF2d) {
		//2D-POINTS
		this.lsPts2d.add( new GeomPoint2d(oPtI2d) );
		this.lsPts2d.add( new GeomPoint2d(oPtF2d) );
		//3D-POINTS
		this.lsPts3d.add( new GeomPoint3d(oPtI2d) );
		this.lsPts3d.add( new GeomPoint3d(oPtF2d) );
	}
	
	public void addLine3d(GeomPoint2d oPtI3d, GeomPoint2d oPtF3d) {
		//3D-POINTS
		this.lsPts3d.add( new GeomPoint3d(oPtI3d) );
		this.lsPts3d.add( new GeomPoint3d(oPtF3d) );
		//2D-POINTS
		this.lsPts2d.add( new GeomPoint2d(oPtI3d) );
		this.lsPts2d.add( new GeomPoint2d(oPtF3d) );
	}
	
	@Override
	public void addPoint2d(GeomPoint2d oPt2d) {
		this.lsPts2d.add( new GeomPoint2d(oPt2d) ); 
		this.lsPts3d.add( new GeomPoint3d(oPt2d.getTagId(), oPt2d.getTagName(), oPt2d.getX(), oPt2d.getY(), 0.0) );
	}

	@Override
	public void addPoint3d(GeomPoint3d oPt3d) {
		this.lsPts3d.add( new GeomPoint3d(oPt3d) ); 
		this.lsPts2d.add( new GeomPoint2d(oPt3d.getTagId(), oPt3d.getTagName(), oPt3d.getX(), oPt3d.getY()) );
	}
	
	@Override
	public void addAllPoint2d(ArrayList<GeomPoint2d> lsPts2d) {
		for(GeomPoint2d oPt2d : lsPts2d) {
			this.lsPts2d.add( new GeomPoint2d(oPt2d) );
			this.lsPts3d.add( new GeomPoint3d(oPt2d.getTagId(), oPt2d.getTagName(), oPt2d.getX(), oPt2d.getY(), 0.0) );
		}
	}

	@Override
	public void addAllPoint3d(ArrayList<GeomPoint3d> lsPts3d) {
		for(GeomPoint3d oPt3d : lsPts3d) {
			this.lsPts3d.add( new GeomPoint3d(oPt3d) );
			this.lsPts2d.add( new GeomPoint2d(oPt3d.getTagId(), oPt3d.getTagName(), oPt3d.getX(), oPt3d.getY()) );
		}
	}

	@Override
	public GeomPoint2d getPoint2dAt(int pos) {
		GeomPoint2d ptResult = null;
		int sz = this.lsPts2d.size();
		if((pos >= 0) && (pos < sz))
			ptResult = new GeomPoint2d( this.lsPts2d.get(pos) );
		return ptResult;
	}

	@Override
	public GeomPoint3d getPoint3dAt(int pos) {
		GeomPoint3d ptResult = null;
		int sz = this.lsPts3d.size();
		if((pos >= 0) && (pos < sz))
			ptResult = new GeomPoint3d( this.lsPts3d.get(pos) );
		return ptResult;
	}

	@Override
	public int getSize() {
		int sz = this.lsPts3d.size();
		return sz;
	}

	@Override
	public IEntityDrawCache duplicate() {
		BlankPolygonEntityDrawCache other = new BlankPolygonEntityDrawCache(this);
		return other;
	}

	/* SELF_OPERATIONS */

	@Override
	public IEntityDrawCache selfOffsetTo(double deltaX, double deltaY, double deltaZ)
	{
    	for(GeomPoint3d oPt3d : this.lsPts3d) {
			oPt3d.selfOffsetTo(deltaX, deltaY, deltaZ);
    	}
    	return this;
	}
	
	@Override
	public IEntityDrawCache selfMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		GeomVector3d vDir = new GeomVector3d(ptBase, ptRef); 
		double d = vDir.mod();
		
    	for(GeomPoint3d oPt3d : this.lsPts3d) {
			oPt3d.selfMoveTo(vDir, d);
    	}
    	return this;
	}
	
	@Override
	public IEntityDrawCache selfMirrorTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		GeomVector3d vDir = new GeomVector3d(ptBase, ptRef); 
		double d = vDir.mod();
		
    	for(GeomPoint3d oPt3d : this.lsPts3d) {
			GeomUtil.mirror(oPt3d, new GeomPoint2d(ptBase), new GeomPoint2d(ptRef) );
    	}
    	return this;
	}

	@Override
	public IEntityDrawCache selfScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale) {
		GeomVector3d vDir = new GeomVector3d(ptBase, ptRef); 
		double d = scale * vDir.mod();
		
    	for(GeomPoint3d oPt3d : this.lsPts3d) {
			oPt3d.selfMoveTo(vDir, d);
    	}
    	return this;
	}

	@Override
	public IEntityDrawCache selfRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad) {
    	for(GeomPoint3d oPt3d : this.lsPts3d) {
    		oPt3d.selfRotateTo(ptBase, zDir, angleRad);
    	}
    	return this;
	}

	/* OTHER_OPERATIONS */

	@Override
	public IEntityDrawCache otherOffsetTo(double deltaX, double deltaY, double deltaZ)
	{
		IEntityDrawCache other = new BlankPolygonEntityDrawCache(this);
		other.selfOffsetTo(deltaX, deltaY, deltaZ);
    	return other;
	}

	@Override
	public IEntityDrawCache otherMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		IEntityDrawCache other = new BlankPolygonEntityDrawCache(this);
		other.selfMoveTo(ptBase, ptRef);
    	return other;
	}

	@Override
	public IEntityDrawCache otherMirrorTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		IEntityDrawCache other = new BlankPolygonEntityDrawCache(this);
		other.selfMirrorTo(ptBase, ptRef);
    	return other;
	}
	
	@Override
	public IEntityDrawCache otherScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale) {
		IEntityDrawCache other = new BlankPolygonEntityDrawCache(this);
		other.selfScaleTo(ptBase, ptRef, scale);
    	return other;
	}

	@Override
	public IEntityDrawCache otherRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad) {
		IEntityDrawCache other = new BlankPolygonEntityDrawCache(this);
		other.selfRotateTo(ptBase, zDir, angleRad);
    	return other;
	}

    /* DRAWING */

	@Override
	public void redraw2d(ICadViewBase view2d, Color c, Stroke b, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, Graphics g) 
	{
		ArrayList<GeomPoint2d> lsPts2d = this.lsPts2d;
		if(lsPts2d == null) return;
		
		int sz = lsPts2d.size();
		if(sz == 0) return;
				
        if(ptBase2dMcs != null) {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

            if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) ) 
            {
            	BlankPolygonEntityDrawCache other = (BlankPolygonEntityDrawCache)this.otherMoveTo(ptBase3dMcs, pt3dMcs);
	    		lsPts2d = other.getLsPts2d();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
	        {
	        	BlankPolygonEntityDrawCache other = (BlankPolygonEntityDrawCache)this.otherMirrorTo(ptBase3dMcs, pt3dMcs);
	    		lsPts2d = other.getLsPts2d();
	        }
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	BlankPolygonEntityDrawCache other = (BlankPolygonEntityDrawCache)this.otherScaleTo(ptBase3dMcs, pt3dMcs, dist);
	    		lsPts2d = other.getLsPts2d();
	        }
	        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
	        {
	        	BlankPolygonEntityDrawCache other = (BlankPolygonEntityDrawCache)this.otherScaleTo(ptBase3dMcs, pt3dMcs, dist);
	    		lsPts2d = other.getLsPts2d();
	        }
        }

		Color oldcol = GeomUtil.setColor(g, AppDefs.BACKGROUNDCOLOR);

        DrawUtil.fillPolygonMcs(view2d, lsPts2d, g);

		GeomUtil.setColor(g, oldcol);
	}

	@Override
	public void redraw3d(ICadViewBase view3d, Color c, Stroke b, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		ArrayList<GeomPoint2d> lsPts2d = this.lsPts2d;
		if(lsPts2d == null) return false;
		
		int sz = lsPts2d.size();
		if(sz == 0) return false;
		
		double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        double distMax = boxSz / 2.0;

        GeomPoint2d ptF2dMcs = null;
        for(GeomPoint2d ptI2dMcs : this.lsPts2d) {
        	if(ptF2dMcs != null) {
                GeomVector2d vIToF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs);
                GeomVector2d uIToF2dMcs = vIToF2dMcs.otherUnit();
        		
                GeomVector2d vIToPt2dMcs = new GeomVector2d(ptI2dMcs, pt2dMcs);

            	GeomPoint2d[] arrMaxMinPtMcs = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);
            	GeomPoint2d ptMinMcs = arrMaxMinPtMcs[0];
            	GeomPoint2d ptMaxMcs = arrMaxMinPtMcs[1];
            	
            	double xMinMcs = ptMinMcs.getX() - distMax;
            	double yMinMcs = ptMinMcs.getY() - distMax;
            	
            	double xMaxMcs = ptMaxMcs.getX() + distMax;
            	double yMaxMcs = ptMaxMcs.getY() + distMax;

            	double xMcs = pt2dMcs.getX();
            	double yMcs = pt2dMcs.getY();

            	if( ( (xMcs >= xMinMcs) && (xMcs <= xMaxMcs) ) & 
            		( (yMcs >= yMinMcs) && (yMcs <= yMaxMcs) ) ) 
            	{
                    GeomVector3d vZ = uIToF2dMcs.vectProd(vIToPt2dMcs);
                    double dZ = Math.abs(vZ.getZOrig());
                    if(dZ <= distMax) {
                    	return true;
                    }
            	}
        	}
        	ptF2dMcs = ptI2dMcs;
        }
		return false;
	}

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* OSNAP */

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) {
		
		/* 
		 * IMPORTANTE: NOT USED! SOLVED AT DRAWCACHE LEVEL!
		 */
		
		return null;
	}

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) {
		
		/* 
		 * IMPORTANTE: NOT USED! SOLVED AT DRAWCACHE LEVEL!
		 */

		return null;
	}

	/* ENVELOP */
	
	@Override
	public GeomDimension2d getEnvelop() {
		return this.oDimension2d;
	}

	/* Getters/Setters */

	public GeomDimension2d getDimension2d() {
		return oDimension2d;
	}

	public void setDimension2d(GeomDimension2d oDimension2d) {
		this.oDimension2d = oDimension2d;
	}
	
	public ArrayList<GeomPoint3d> getLsPts3d() {
		return lsPts3d;
	}

	public void setLsPts3d(ArrayList<GeomPoint3d> lsPts3d) {
		this.lsPts3d = lsPts3d;
	}

	public ArrayList<GeomPoint2d> getLsPts2d() {
		return lsPts2d;
	}

	public void setLsPts2d(ArrayList<GeomPoint2d> lsPts2d) {
		this.lsPts2d = lsPts2d;
	}

}
