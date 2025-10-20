/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * TextEntityDrawCache.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/09/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomTextPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomTextPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;

public class TextEntityDrawCache extends EntityDrawCache
{
//Private
	private ArrayList<GeomTextPoint3d> lsTextPts3d = null; 
	private ArrayList<GeomTextPoint2d> lsTextPts2d = null; 
	//
	private GeomDimension2d oDimension2d = null;

//Public
	
	public TextEntityDrawCache() 
	{
		this.init();
	}
	
	public TextEntityDrawCache(Color oColor, Stroke oStroke) 
	{
		this.init(oColor, oStroke);
	}

	public TextEntityDrawCache(IEntityDrawCache other) {
		this.init(other);
	}

	public TextEntityDrawCache(Color oColor, Stroke oLtype, ArrayList<GeomTextPoint2d> lsTextPts2d, ArrayList<GeomTextPoint3d> lsTextPts3d) {
		if(lsTextPts2d != null) {
			this.initText2d(oColor, oLtype, lsTextPts2d);			
		}
		else if(lsTextPts3d != null) {
			this.initText3d(oColor, oLtype, lsTextPts3d);			
		}
	}
	
	/* Methodes */
	
	@Override
	public void init() 
	{
		super.init();

		this.lsTextPts3d = new ArrayList<GeomTextPoint3d>(); 
		this.lsTextPts2d = new ArrayList<GeomTextPoint2d>(); 
	}
	
	@Override
	public void init(Color oColor, Stroke oStroke) 
	{
		super.init(oColor, oStroke);

		this.lsTextPts3d = new ArrayList<GeomTextPoint3d>(); 
		this.lsTextPts2d = new ArrayList<GeomTextPoint2d>(); 
	}

	@Override
	public void init(IEntityDrawCache other) {
		super.init(other.getColor(), other.getLtype());

		this.lsTextPts2d = new ArrayList<GeomTextPoint2d>();
		this.lsTextPts3d = new ArrayList<GeomTextPoint3d>(); 
		for(GeomTextPoint2d oTextPt2d : lsTextPts2d) {
			this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt2d) );
			this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt2d) );
		}		
	}

	public void initText2d(Color oColor, Stroke oLtype, ArrayList<GeomTextPoint2d> lsTextPts2d) {
		super.init(oColor, oLtype);

		this.lsTextPts2d = new ArrayList<GeomTextPoint2d>();
		this.lsTextPts3d = new ArrayList<GeomTextPoint3d>(); 
		for(GeomTextPoint2d oTextPt2d : lsTextPts2d) {
			this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt2d) );
			this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt2d) );
		}
	}

	public void initText3d(Color oColor, Stroke oLtype, ArrayList<GeomTextPoint3d> lsTextPts3d) {
		super.init(oColor, oLtype);

		this.lsTextPts2d = new ArrayList<GeomTextPoint2d>();
		this.lsTextPts3d = new ArrayList<GeomTextPoint3d>(); 
		for(GeomTextPoint3d oTextPt3d : lsTextPts3d) {
			this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt3d) );
			this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt3d) );
		}
	}

	/* BASIC_OPERATIONS */
	
	public void addTextPoint2d(GeomTextPoint2d oTextPt2d) {
		this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt2d) ); 
		this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt2d) );
	}

	public void addTextPoint3d(GeomTextPoint3d oTextPt3d) {
		this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt3d) ); 
		this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt3d) );
	}
	
	public void addAllTextPoint2d(ArrayList<GeomTextPoint2d> lsTextPts2d) {
		for(GeomTextPoint2d oTextPt2d : lsTextPts2d) {
			this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt2d) );
			this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt2d) );
		}
	}

	public void addAllTextPoint3d(ArrayList<GeomTextPoint3d> lsTextPts3d) {
		for(GeomTextPoint3d oTextPt3d : lsTextPts3d) {
			this.lsTextPts3d.add( new GeomTextPoint3d(oTextPt3d) );
			this.lsTextPts2d.add( new GeomTextPoint2d(oTextPt3d) );
		}
	}

	public GeomTextPoint2d getTextPoint2dAt(int pos) {
		GeomTextPoint2d ptTextResult = null;
		int sz = this.lsTextPts2d.size();
		if((pos >= 0) && (pos < sz))
			ptTextResult = new GeomTextPoint2d( this.lsTextPts2d.get(pos) );
		return ptTextResult;
	}

	public GeomTextPoint3d getTextPoint3dAt(int pos) {
		GeomTextPoint3d ptTextResult = null;
		int sz = this.lsTextPts3d.size();
		if((pos >= 0) && (pos < sz))
			ptTextResult = new GeomTextPoint3d( this.lsTextPts3d.get(pos) );
		return ptTextResult;
	}

	@Override
	public int getSize() {
		int sz = this.lsTextPts3d.size();
		return sz;
	}

	@Override
	public IEntityDrawCache duplicate() {
		TextEntityDrawCache other = new TextEntityDrawCache(this);
		return other;
	}
	
	/*** ***/

	@Override
	public void init2d(Color oColor, Stroke oLtype, ArrayList<GeomPoint2d> lsPts2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init3d(Color oColor, Stroke oLtype, ArrayList<GeomPoint3d> lsPts3d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPoint2d(GeomPoint2d oPt2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPoint3d(GeomPoint3d oPt3d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAllPoint2d(ArrayList<GeomPoint2d> lsPts2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAllPoint3d(ArrayList<GeomPoint3d> lsPts3d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GeomPoint2d getPoint2dAt(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeomPoint3d getPoint3dAt(int pos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* SELF_OPERATIONS */

	@Override
	public IEntityDrawCache selfOffsetTo(double deltaX, double deltaY, double deltaZ)
	{
    	for(GeomTextPoint3d oTextPt3d : this.lsTextPts3d) {
			oTextPt3d.selfOffsetTo(deltaX, deltaY, deltaZ);
    	}
    	return this;
	}
	
	@Override
	public IEntityDrawCache selfMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		GeomVector3d vDir = new GeomVector3d(ptBase, ptRef); 
		double d = vDir.mod();
		
    	for(GeomTextPoint3d oTextPt3d : this.lsTextPts3d) {
			oTextPt3d.selfMoveTo(vDir, d);
    	}
    	return this;
	}
	
	@Override
	public IEntityDrawCache selfMirrorTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		GeomVector3d vDir = new GeomVector3d(ptBase, ptRef); 
		double d = vDir.mod();
		
    	for(GeomTextPoint3d oTextPt3d : this.lsTextPts3d) {
			GeomUtil.mirror(oTextPt3d, new GeomPoint2d(ptBase), new GeomPoint2d(ptRef) );
    	}
    	return this;
	}

	@Override
	public IEntityDrawCache selfScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale) {
		GeomVector3d vDir = new GeomVector3d(ptBase, ptRef); 
		double d = scale * vDir.mod();
		
    	for(GeomTextPoint3d oTextPt3d : this.lsTextPts3d) {
			oTextPt3d.selfMoveTo(vDir, d);
    	}
    	return this;
	}

	@Override
	public IEntityDrawCache selfRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad) {
    	for(GeomTextPoint3d oTextPt3d : this.lsTextPts3d) {
    		oTextPt3d.selfRotateTo(ptBase, zDir, angleRad);
    	}
    	return this;
	}

	/* OTHER_OPERATIONS */

	@Override
	public IEntityDrawCache otherOffsetTo(double deltaX, double deltaY, double deltaZ)
	{
		IEntityDrawCache other = new TextEntityDrawCache(this);
		other.selfOffsetTo(deltaX, deltaY, deltaZ);
    	return other;
	}

	@Override
	public IEntityDrawCache otherMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		IEntityDrawCache other = new TextEntityDrawCache(this);
		other.selfMoveTo(ptBase, ptRef);
    	return other;
	}

	@Override
	public IEntityDrawCache otherMirrorTo(GeomPoint3d ptBase, GeomPoint3d ptRef) {
		IEntityDrawCache other = new TextEntityDrawCache(this);
		other.selfMirrorTo(ptBase, ptRef);
    	return other;
	}
	
	@Override
	public IEntityDrawCache otherScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale) {
		IEntityDrawCache other = new TextEntityDrawCache(this);
		other.selfScaleTo(ptBase, ptRef, scale);
    	return other;
	}

	@Override
	public IEntityDrawCache otherRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad) {
		IEntityDrawCache other = new TextEntityDrawCache(this);
		other.selfRotateTo(ptBase, zDir, angleRad);
    	return other;
	}

    /* DRAWING */

	@Override
	public void redraw2d(ICadViewBase view2d, Color c, Stroke b, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, Graphics g) 
	{
		ArrayList<GeomTextPoint2d> lsTextPts2d = this.lsTextPts2d;
		if(lsTextPts2d == null) return;
		
		int sz = lsTextPts2d.size();
		if(sz == 0) return;
				
        if(ptBase2dMcs != null) {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

            if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) ) 
            {
            	TextEntityDrawCache other = (TextEntityDrawCache)this.otherMoveTo(ptBase3dMcs, pt3dMcs);
            	lsTextPts2d = other.getLsTextPts2d();
	        }	        
	        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
	        {
	        	TextEntityDrawCache other = (TextEntityDrawCache)this.otherMirrorTo(ptBase3dMcs, pt3dMcs);
	        	lsTextPts2d = other.getLsTextPts2d();
	        }
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	TextEntityDrawCache other = (TextEntityDrawCache)this.otherScaleTo(ptBase3dMcs, pt3dMcs, dist);
	        	lsTextPts2d = other.getLsTextPts2d();
	        }
	        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
	        {
	        	TextEntityDrawCache other = (TextEntityDrawCache)this.otherScaleTo(ptBase3dMcs, pt3dMcs, dist);
	        	lsTextPts2d = other.getLsTextPts2d();
	        }
        }
        
        DrawUtil.drawTextMcs(view2d, lsTextPts2d, sclFact, g);
	}

	@Override
	public void redraw3d(ICadViewBase view3d, Color c, Stroke b, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		ArrayList<GeomTextPoint2d> lsTextPts2d = this.lsTextPts2d;
		if(lsTextPts2d == null) return false;
		
		int sz = lsTextPts2d.size();
		if(sz == 0) return false;
						
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        double distMax = boxSz / 2.0;

        GeomPoint2d ptF2dMcs = null;
        for(GeomPoint2d ptI2dMcs : this.lsTextPts2d) {
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
	
	public ArrayList<GeomTextPoint3d> getLsTextPts3d() {
		return lsTextPts3d;
	}

	public void setLsTextPts3d(ArrayList<GeomTextPoint3d> lsTextPts3d) {
		this.lsTextPts3d = lsTextPts3d;
	}

	public ArrayList<GeomTextPoint2d> getLsTextPts2d() {
		return lsTextPts2d;
	}

	public void setLsTextPts2d(ArrayList<GeomTextPoint2d> lsTextPts2d) {
		this.lsTextPts2d = lsTextPts2d;
	}

}
