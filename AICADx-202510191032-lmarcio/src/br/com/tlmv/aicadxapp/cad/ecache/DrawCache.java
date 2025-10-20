/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DrawCache.java
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;

public class DrawCache 
{
//Private
	//ENTITY_DRAWCACHE
	private ArrayList<IEntityDrawCache> lsEntityDrawCache = null;

	//OSNAP_DRAWCACHE
	private ArrayList<GeomPoint3d> lsOsnapCenterDrawCache = null;
	private ArrayList<GeomPoint3d> lsOsnapMiddleDrawCache = null;
	private ArrayList<GeomPoint3d> lsOsnapEndpointDrawCache = null;
	private ArrayList<GeomPoint3d> lsOsnapNodepointDrawCache = null;
	private ArrayList<GeomPoint3d> lsOsnapQuadrantDrawCache = null;
	private ArrayList<GeomPoint3d> lsOsnapInterpointDrawCache = null;
	//
	private ArrayList<GeomPoint3d> lsOsnapAllDrawCache = null;

//Public
	
	public DrawCache()
	{
		this.init();
	}
	
	public DrawCache(DrawCache other)
	{
		this.init(other);
	}
	
	/* Methodes */
	
	public synchronized void init()
	{
		//ENTITY_DRAWCACHE
		this.lsEntityDrawCache = new ArrayList<IEntityDrawCache>();

		//OSNAP_DRAWCACHE
		this.lsOsnapCenterDrawCache = new ArrayList<GeomPoint3d>();
		this.lsOsnapMiddleDrawCache = new ArrayList<GeomPoint3d>();
		this.lsOsnapEndpointDrawCache = new ArrayList<GeomPoint3d>();
		this.lsOsnapNodepointDrawCache = new ArrayList<GeomPoint3d>();
		this.lsOsnapQuadrantDrawCache = new ArrayList<GeomPoint3d>();
		this.lsOsnapInterpointDrawCache = new ArrayList<GeomPoint3d>();
		//
		this.lsOsnapAllDrawCache = new ArrayList<GeomPoint3d>();
	}
	
	public synchronized void init(DrawCache other)
	{
		this.lsEntityDrawCache = new ArrayList<IEntityDrawCache>();
		
		ArrayList<IEntityDrawCache> ls = other.getLsEntityDrawCache();
		for(IEntityDrawCache obj : ls) {
			IEntityDrawCache newObj = obj.duplicate();
			this.lsEntityDrawCache.add(newObj);
		}
	}
	
	public synchronized DrawCache duplicate()
	{
		DrawCache other = new DrawCache(this);
		return other;
	}

	/* BASIC_OPERATIONS */
	
	//DrawCache
	//
	public synchronized void addDrawCache(DrawCache cache)
	{
		this.lsEntityDrawCache.addAll(cache.getLsEntityDrawCache());
		//
		this.lsOsnapCenterDrawCache.addAll(cache.getLsOsnapCenterDrawCache());
		this.lsOsnapMiddleDrawCache.addAll(cache.getLsOsnapMiddleDrawCache());
		this.lsOsnapEndpointDrawCache.addAll(cache.getLsOsnapEndpointDrawCache());
		this.lsOsnapNodepointDrawCache.addAll(cache.getLsOsnapNodepointDrawCache());		
		this.lsOsnapQuadrantDrawCache.addAll(cache.getLsOsnapQuadrantDrawCache());		
		this.lsOsnapInterpointDrawCache.addAll(cache.getLsOsnapInterpointDrawCache());		
		this.lsOsnapAllDrawCache.addAll(cache.getLsOsnapAllDrawCache());
	}
	
	//DRAW_FUNCTIONS
	//

	//
	// TODO:
	//
	
	//IEntityDrawCache
	//
	public synchronized void addAllItem(ArrayList<IEntityDrawCache> ls)
	{
		this.lsEntityDrawCache.addAll(ls);
	}

	public synchronized void addItem(IEntityDrawCache o)
	{
		this.lsEntityDrawCache.add(o);
	}
	
	public synchronized int getSize()
	{
		int sz = this.lsEntityDrawCache.size();
		return sz;
	}

	public synchronized IEntityDrawCache getItemAt(int pos)
	{
		IEntityDrawCache oResult = null;
		
		int sz = this.lsEntityDrawCache.size();
		if((sz >= 0) && (pos < sz))
			oResult = this.lsEntityDrawCache.get(pos);
		return oResult;
	}

	//OsnapItem
	//
	public synchronized void addAllOsnapItem(ArrayList<GeomPoint3d> lsPt3d)
	{
		for(GeomPoint3d oPt3d : lsPt3d) {
			GeomPoint3d oNewPt3d = new GeomPoint3d(oPt3d);

			if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_CENTER) {
				this.lsOsnapCenterDrawCache.add(oNewPt3d);			
			}
			else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_ENDPOINT) {
				this.lsOsnapEndpointDrawCache.add(oNewPt3d);			
			}
			else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_MIDDLE) {
				this.lsOsnapMiddleDrawCache.add(oNewPt3d);			
			}
			else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_NODEPOINT) {
				this.lsOsnapNodepointDrawCache.add(oNewPt3d);			
			}
			else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_QUADRANT) {
				this.lsOsnapQuadrantDrawCache.add(oNewPt3d);			
			}
			else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_INTERPOINT) {
				this.lsOsnapInterpointDrawCache.add(oNewPt3d);			
			}

			this.lsOsnapAllDrawCache.add(oNewPt3d);
		}
	}
	
	public synchronized void addAllOsnapItem(int osnapMode, ArrayList<GeomPoint3d> lsPt3d)
	{
		if(osnapMode == AppDefs.OSNAPMODE_CENTER) {
			this.lsOsnapCenterDrawCache.addAll(lsPt3d);			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_ENDPOINT) {
			this.lsOsnapEndpointDrawCache.addAll(lsPt3d);			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_MIDDLE) {
			this.lsOsnapMiddleDrawCache.addAll(lsPt3d);			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_NODEPOINT) {
			this.lsOsnapNodepointDrawCache.addAll(lsPt3d);			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_QUADRANT) {
			this.lsOsnapQuadrantDrawCache.addAll(lsPt3d);			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_INTERPOINT) {
			this.lsOsnapInterpointDrawCache.addAll(lsPt3d);			
		}

		this.lsOsnapAllDrawCache.addAll(lsPt3d);
	}
	
	public synchronized void addOsnapItem(GeomPoint3d oPt3d)
	{
		GeomPoint3d oNewPt3d = new GeomPoint3d(oPt3d);
		
		if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_CENTER) {
			this.lsOsnapCenterDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_ENDPOINT) {
			this.lsOsnapEndpointDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_MIDDLE) {
			this.lsOsnapMiddleDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_NODEPOINT) {
			this.lsOsnapNodepointDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_QUADRANT) {
			this.lsOsnapQuadrantDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_INTERPOINT) {
			this.lsOsnapInterpointDrawCache.add(oNewPt3d);			
		}

		this.lsOsnapAllDrawCache.add(oNewPt3d);
	}
	
	public synchronized void addOsnapItem(int osnapMode, GeomPoint3d oPt3d)
	{
		GeomPoint3d oNewPt3d = new GeomPoint3d(osnapMode, oPt3d);		
		
		if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_CENTER) {
			this.lsOsnapCenterDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_ENDPOINT) {
			this.lsOsnapEndpointDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_MIDDLE) {
			this.lsOsnapMiddleDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_NODEPOINT) {
			this.lsOsnapNodepointDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_QUADRANT) {
			this.lsOsnapQuadrantDrawCache.add(oNewPt3d);			
		}
		else if(oNewPt3d.getTagId() == AppDefs.OSNAPMODE_INTERPOINT) {
			this.lsOsnapInterpointDrawCache.add(oNewPt3d);			
		}

		this.lsOsnapAllDrawCache.add(oNewPt3d);
	}
	
	public synchronized int getOsnapSize(int osnapMode)
	{
		int sz = 0;
		
		if(osnapMode == AppDefs.OSNAPMODE_CENTER) {
			sz = this.lsOsnapCenterDrawCache.size();			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_ENDPOINT) {
			sz = this.lsOsnapEndpointDrawCache.size();			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_MIDDLE) {
			sz = this.lsOsnapMiddleDrawCache.size();			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_NODEPOINT) {
			sz = this.lsOsnapNodepointDrawCache.size();			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_QUADRANT) {
			sz = this.lsOsnapQuadrantDrawCache.size();			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_INTERPOINT) {
			sz = this.lsOsnapInterpointDrawCache.size();			
		}
		else if(osnapMode == AppDefs.OSNAPMODE_ALL) {
			sz = this.lsOsnapAllDrawCache.size();			
		}
		return sz;
	}

	public synchronized GeomPoint3d getOsnapItemAt(int osnapMode, int pos)
	{
		GeomPoint3d oResult = null;
		int sz = 0;
		
		if(osnapMode == AppDefs.OSNAPMODE_CENTER) {
			sz = this.lsOsnapCenterDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapCenterDrawCache.get(pos);
		}
		else if(osnapMode == AppDefs.OSNAPMODE_ENDPOINT) {
			sz = this.lsOsnapEndpointDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapEndpointDrawCache.get(pos);
		}
		else if(osnapMode == AppDefs.OSNAPMODE_MIDDLE) {
			sz = this.lsOsnapMiddleDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapMiddleDrawCache.get(pos);
		}
		else if(osnapMode == AppDefs.OSNAPMODE_NODEPOINT) {
			sz = this.lsOsnapNodepointDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapNodepointDrawCache.get(pos);
		}
		else if(osnapMode == AppDefs.OSNAPMODE_QUADRANT) {
			sz = this.lsOsnapQuadrantDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapQuadrantDrawCache.get(pos);
		}
		else if(osnapMode == AppDefs.OSNAPMODE_INTERPOINT) {
			sz = this.lsOsnapInterpointDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapInterpointDrawCache.get(pos);
		}
		else if(osnapMode == AppDefs.OSNAPMODE_ALL) {
			sz = this.lsOsnapAllDrawCache.size();			
			if((sz >= 0) && (pos < sz))
				oResult = this.lsOsnapAllDrawCache.get(pos);
		}
		return oResult;
	}

	/* SELF_OPERATIONS */
	
	public synchronized DrawCache selfMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef)
	{
		for(IEntityDrawCache o : this.lsEntityDrawCache) {
			o.selfMoveTo(ptBase, ptRef);
		}
		return this;
	}
	
	public synchronized DrawCache selfScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale)
	{
		for(IEntityDrawCache o : this.lsEntityDrawCache) {
			o.selfScaleTo(ptBase, ptRef, scale);
		}
		return this;
	}
	
	public synchronized DrawCache selfRotateTo(GeomPoint3d ptBase, GeomVector3d vDir, double angleDegree)
	{
		for(IEntityDrawCache o : this.lsEntityDrawCache) {
			o.selfRotateTo(ptBase, vDir, angleDegree);
		}
		return this;
	}

	/* OTHER_OPERATIONS */
	
	public synchronized DrawCache otherMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef)
	{
		DrawCache other = this.duplicate();
		other.selfMoveTo(ptBase, ptRef);
		return other;
	}
	
	public synchronized DrawCache otherScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale)
	{
		DrawCache other = this.duplicate();
		other.selfScaleTo(ptBase, ptRef, scale);
		return other;
	}
	
	public synchronized DrawCache otherRotateTo(GeomPoint3d ptBase, GeomVector3d vDir, double angleRad)
	{
		DrawCache other = this.duplicate();
		other.selfRotateTo(ptBase, vDir, angleRad);
		return other;
	}

    /* DRAWING */
	
    public void redraw2d(ICadViewBase view2d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, Graphics g) {
        
		if(this.lsEntityDrawCache == null) return; 
		
		Stroke oldltype = GeomUtil.setLtype(g, oLtype);
		
		Color oldcol = GeomUtil.setColor(g, oColor);		

		for(IEntityDrawCache obj : this.lsEntityDrawCache) {
    		obj.redraw2d(view2d, oColor, oLtype, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, action, g);
    	}

        GeomUtil.setColor(g, oldcol);

		GeomUtil.setLtype(g, oldltype);		
    }
	
	public void redraw3d(ICadViewBase view3d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, PrepareDrawUtil prep) {
		//TODO:
	}
	        
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		if(this.lsEntityDrawCache == null) return false;
		
		boolean bResult = false;
		for(IEntityDrawCache obj : this.lsEntityDrawCache) {
    		bResult = obj.select2d(view2d, pt2dMcs, sclFact, bSelectEntity);
    	}
    	return bResult;
	}

	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* OSNAP */

	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapMode, GeomPoint2d pt2dMcs)
	{
		if(this.lsOsnapAllDrawCache == null) return null; 
		
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>(this.lsOsnapAllDrawCache);
		return lsResult;
	}

	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g)
	{
		if(this.lsOsnapAllDrawCache == null) return null; 
		//
		if(this.lsOsnapCenterDrawCache == null) return null; 
		if(this.lsOsnapMiddleDrawCache == null) return null; 
		if(this.lsOsnapEndpointDrawCache == null) return null; 
		if(this.lsOsnapQuadrantDrawCache == null) return null; 
		if(this.lsOsnapNodepointDrawCache == null) return null; 
		if(this.lsOsnapInterpointDrawCache == null) return null; 
		
		GeomPoint3d ptResult = null;

    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, this.lsOsnapCenterDrawCache, g);
    	if(ptResult != null) return ptResult;

    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, this.lsOsnapMiddleDrawCache, g);
    	if(ptResult != null) return ptResult;

    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, this.lsOsnapEndpointDrawCache, g);
    	if(ptResult != null) return ptResult;

    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_QUADRANT, pt2dMcs, this.lsOsnapQuadrantDrawCache, g);
    	if(ptResult != null) return ptResult;

    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, this.lsOsnapNodepointDrawCache, g);
    	if(ptResult != null) return ptResult;

    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_INTERPOINT, pt2dMcs, this.lsOsnapInterpointDrawCache, g);
    	if(ptResult != null) return ptResult;

    	return ptResult;
	}

	/* Getters/Setters */
	
	public ArrayList<IEntityDrawCache> getLsEntityDrawCache() {
		return lsEntityDrawCache;
	}

	public void setLsEntityDrawCache(ArrayList<IEntityDrawCache> lsEntityDrawCache) {
		this.lsEntityDrawCache = lsEntityDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapCenterDrawCache() {
		return lsOsnapCenterDrawCache;
	}

	public void setLsOsnapCenterDrawCache(ArrayList<GeomPoint3d> lsOsnapCenterDrawCache) {
		this.lsOsnapCenterDrawCache = lsOsnapCenterDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapMiddleDrawCache() {
		return lsOsnapMiddleDrawCache;
	}

	public void setLsOsnapMiddleDrawCache(ArrayList<GeomPoint3d> lsOsnapMiddleDrawCache) {
		this.lsOsnapMiddleDrawCache = lsOsnapMiddleDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapEndpointDrawCache() {
		return lsOsnapEndpointDrawCache;
	}

	public void setLsOsnapEndpointDrawCache(ArrayList<GeomPoint3d> lsOsnapEndpointDrawCache) {
		this.lsOsnapEndpointDrawCache = lsOsnapEndpointDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapNodepointDrawCache() {
		return lsOsnapNodepointDrawCache;
	}

	public void setLsOsnapNodepointDrawCache(ArrayList<GeomPoint3d> lsOsnapNodepointDrawCache) {
		this.lsOsnapNodepointDrawCache = lsOsnapNodepointDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapQuadrantDrawCache() {
		return lsOsnapQuadrantDrawCache;
	}

	public void setLsOsnapQuadrantDrawCache(ArrayList<GeomPoint3d> lsOsnapQuadrantDrawCache) {
		this.lsOsnapQuadrantDrawCache = lsOsnapQuadrantDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapAllDrawCache() {
		return lsOsnapAllDrawCache;
	}

	public void setLsOsnapAllDrawCache(ArrayList<GeomPoint3d> lsOsnapAllDrawCache) {
		this.lsOsnapAllDrawCache = lsOsnapAllDrawCache;
	}

	public ArrayList<GeomPoint3d> getLsOsnapInterpointDrawCache() {
		return lsOsnapInterpointDrawCache;
	}

	public void setLsOsnapInterpointDrawCache(ArrayList<GeomPoint3d> lsOsnapInterpointDrawCache) {
		this.lsOsnapInterpointDrawCache = lsOsnapInterpointDrawCache;
	}

}
