/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.cad.ecache;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;

public class DrawCache 
{
//Private
	//ENTITY_DRAWCACHE
	private ArrayList<IEntityDrawCache> lsEntityDrawCache = null;	
	private ArrayList<IEntityDrawCache> lsEntityDrawCacheHover = null;
	private ArrayList<IEntityDrawCache> lsEntityDrawCacheSelected = null;
	
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
		this.lsEntityDrawCacheHover = new ArrayList<IEntityDrawCache>();
		this.lsEntityDrawCacheSelected = new ArrayList<IEntityDrawCache>();

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
		if(other == null) return;
		
		this.init();

		this.lsEntityDrawCache = new ArrayList<IEntityDrawCache>();
		this.lsEntityDrawCacheHover = new ArrayList<IEntityDrawCache>();
		this.lsEntityDrawCacheSelected = new ArrayList<IEntityDrawCache>();

		// DRAWCACHE - COPY_ALL
		//
		this.copyAll( this.lsEntityDrawCache, other.getLsEntityDrawCache() );
		this.copyAll( this.lsEntityDrawCacheHover, other.getLsEntityDrawCacheHover() );
		this.copyAll( this.lsEntityDrawCacheSelected, other.getLsEntityDrawCacheSelected() );
		//
		this.lsOsnapCenterDrawCache.addAll( other.getLsOsnapCenterDrawCache() );
		this.lsOsnapMiddleDrawCache.addAll( other.getLsOsnapMiddleDrawCache() );
		this.lsOsnapEndpointDrawCache.addAll( other.getLsOsnapEndpointDrawCache() );
		this.lsOsnapNodepointDrawCache.addAll( other.getLsOsnapNodepointDrawCache() );		
		this.lsOsnapQuadrantDrawCache.addAll( other.getLsOsnapQuadrantDrawCache() );		
		this.lsOsnapInterpointDrawCache.addAll( other.getLsOsnapInterpointDrawCache() );		
		//
		this.lsOsnapAllDrawCache.addAll( other.getLsOsnapAllDrawCache() );
	
	}
	
	public synchronized DrawCache duplicate()
	{
		DrawCache other = new DrawCache(this);
		return other;
	}

	/* BASIC_OPERATIONS */
	
	//ClearHover
	//
	public synchronized void clearHover()
	{
		this.lsEntityDrawCacheHover = new ArrayList<IEntityDrawCache>();
	}
	
	//ClearSelected
	//
	public synchronized void clearSelected()
	{
		this.lsEntityDrawCacheSelected = new ArrayList<IEntityDrawCache>();
	}
	
	//DrawCache
	//
	public synchronized void addDrawCache(DrawCache cache)
	{
		if(cache == null) return;
		
		this.lsEntityDrawCache.addAll(cache.getLsEntityDrawCache());
		this.lsEntityDrawCacheHover.addAll(cache.getLsEntityDrawCacheHover());
		this.lsEntityDrawCacheSelected.addAll(cache.getLsEntityDrawCacheSelected());
		//
		this.lsOsnapCenterDrawCache.addAll(cache.getLsOsnapCenterDrawCache());
		this.lsOsnapMiddleDrawCache.addAll(cache.getLsOsnapMiddleDrawCache());
		this.lsOsnapEndpointDrawCache.addAll(cache.getLsOsnapEndpointDrawCache());
		this.lsOsnapNodepointDrawCache.addAll(cache.getLsOsnapNodepointDrawCache());		
		this.lsOsnapQuadrantDrawCache.addAll(cache.getLsOsnapQuadrantDrawCache());		
		this.lsOsnapInterpointDrawCache.addAll(cache.getLsOsnapInterpointDrawCache());		
		this.lsOsnapAllDrawCache.addAll(cache.getLsOsnapAllDrawCache());
	}
	
	public synchronized void addDrawCache(GeomPoint3d ptBase3d, GeomPoint3d ptRef3d, DrawCache cache)
	{
		if(cache == null) return;
		
		DrawCache other = cache.otherMoveTo(ptBase3d, ptRef3d);
		
		this.lsEntityDrawCache.addAll( other.getLsEntityDrawCache() );
		this.lsEntityDrawCacheHover.addAll( other.getLsEntityDrawCacheHover() );
		this.lsEntityDrawCacheSelected.addAll( other.getLsEntityDrawCacheSelected() );
		//
		this.lsOsnapCenterDrawCache.addAll( other.getLsOsnapCenterDrawCache() );
		this.lsOsnapMiddleDrawCache.addAll( other.getLsOsnapMiddleDrawCache() );
		this.lsOsnapEndpointDrawCache.addAll( other.getLsOsnapEndpointDrawCache() );
		this.lsOsnapNodepointDrawCache.addAll( other.getLsOsnapNodepointDrawCache() );		
		this.lsOsnapQuadrantDrawCache.addAll( other.getLsOsnapQuadrantDrawCache() );		
		this.lsOsnapInterpointDrawCache.addAll( other.getLsOsnapInterpointDrawCache() );		
		this.lsOsnapAllDrawCache.addAll( other.getLsOsnapAllDrawCache() );
	}
	
	//DRAWCACHE_UTILITY_FUNCTIONS
	//

	public synchronized void copyAll(ArrayList<IEntityDrawCache> lsDst, ArrayList<IEntityDrawCache> lsSrc)
	{
		for(IEntityDrawCache obj : lsSrc) {
			IEntityDrawCache newObj = obj.duplicate();
			lsDst.add(newObj);
		}
	}
	
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

	//IEntityDrawCache - HOVER
	//
	public synchronized void addAllItemHover(ArrayList<IEntityDrawCache> ls)
	{
		this.lsEntityDrawCacheHover.addAll(ls);
	}

	public synchronized void addItemHover(IEntityDrawCache o)
	{
		this.lsEntityDrawCacheHover.add(o);
	}
	
	public synchronized int getSizeHover()
	{
		int sz = this.lsEntityDrawCacheHover.size();
		return sz;
	}

	public synchronized IEntityDrawCache getItemAtHover(int pos)
	{
		IEntityDrawCache oResult = null;
		
		int sz = this.lsEntityDrawCacheHover.size();
		if((sz >= 0) && (pos < sz))
			oResult = this.lsEntityDrawCacheHover.get(pos);
		return oResult;
	}

	//IEntityDrawCache - SELECTED
	//
	public synchronized void addAllItemSelected(ArrayList<IEntityDrawCache> ls)
	{
		this.lsEntityDrawCacheSelected.addAll(ls);
	}

	public synchronized void addItemSelected(IEntityDrawCache o)
	{
		this.lsEntityDrawCacheSelected.add(o);
	}
	
	public synchronized int getSizeSelected()
	{
		int sz = this.lsEntityDrawCacheSelected.size();
		return sz;
	}

	public synchronized IEntityDrawCache getItemAtSelected(int pos)
	{
		IEntityDrawCache oResult = null;
		
		int sz = this.lsEntityDrawCacheSelected.size();
		if((sz >= 0) && (pos < sz))
			oResult = this.lsEntityDrawCacheSelected.get(pos);
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
	
    public void redraw2d_202511090956(ICadViewBase view2d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, Graphics g) {
        
		if(this.lsEntityDrawCache == null) return; 
		
		Stroke oldltype = GeomUtil.setLtype(g, oLtype);
		
		Color oldcol = GeomUtil.setColor(g, oColor);		

		for(IEntityDrawCache obj : this.lsEntityDrawCache) {
    		obj.redraw2d(view2d, oColor, oLtype, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, action, g);
    	}

        GeomUtil.setColor(g, oldcol);

		GeomUtil.setLtype(g, oldltype);		
    }
	
    public void redraw2d(ICadViewBase view2d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, Graphics g) 
    {        
		if(this.lsEntityDrawCache == null) return; 
		
		Stroke oldltype = GeomUtil.setLtype(g, oLtype);
		
		Color oldcol = GeomUtil.setColor(g, oColor);		

		for(IEntityDrawCache obj : this.lsEntityDrawCache) {
    		obj.redraw2d(view2d, oColor, oLtype, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, action, g);
    	}

		GeomUtil.setLtype(g, AppDefs.SELECTOBJECTLTYPE_SELECTMODE.getLtype());
		
		GeomUtil.setColor(g, AppDefs.SELECTOBJECTCOLOR_SELECTMODE);		

		for(IEntityDrawCache obj : this.lsEntityDrawCacheSelected) {
    		obj.redraw2d(view2d, oColor, oLtype, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, action, g);
    	}

		GeomUtil.setLtype(g, AppDefs.HOVEROBJECTLTYPE_SELECTMODE.getLtype());
		
		GeomUtil.setColor(g, AppDefs.HOVEROBJECTCOLOR_SELECTMODE);		

		for(IEntityDrawCache obj : this.lsEntityDrawCacheHover) {
    		obj.redraw2d(view2d, oColor, oLtype, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, action, g);
    	}

        GeomUtil.setColor(g, oldcol);

		GeomUtil.setLtype(g, oldltype);		
    }
	
	public void redraw3d(ICadViewBase view3d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, PrepareDrawUtil prep) {
		//TODO:
	}
	        
	/* SELECT */

	public boolean select2d_202511091021(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		if(this.lsEntityDrawCache == null) return false;
		
		boolean bResult = false;
		for(IEntityDrawCache obj : this.lsEntityDrawCache) {
    		bResult = obj.select2d(view2d, pt2dMcs, sclFact, bSelectEntity);
    	}
    	return bResult;
	}

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		if(this.lsEntityDrawCache == null) return false;
		
		for(IEntityDrawCache obj : this.lsEntityDrawCache) {
    		boolean bResult = obj.select2d(view2d, pt2dMcs, sclFact, bSelectEntity);
    		if( bResult ) return true;
    	}
    	return false;
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
	
	/* READ/WRITE DXF R12 */
	
	public ArrayList<DxfCadEntity> toDxfR12(CadLayerDef oLayer)
	{
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d(oLayer);
		if(lsCadEntity2d != null)
			lsDxfCadEntity.addAll( lsCadEntity2d );
	
		ArrayList<DxfCadEntity> lsCadEntity3d = toDxfR12_view3d(oLayer);
		if(lsCadEntity3d != null)
			lsDxfCadEntity.addAll( lsCadEntity3d );
		
		return lsDxfCadEntity;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_view2d(CadLayerDef oLayer)
	{
		ArrayList<DxfCadEntity> lsEntity2d = new ArrayList<DxfCadEntity>(); 
	    	
		for(IEntityDrawCache o : this.lsEntityDrawCache) {
			lsEntity2d.addAll( o.toDxfR12_view2d(oLayer) );
		}
		return lsEntity2d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_view3d(CadLayerDef oLayer) {
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 
		
		for(IEntityDrawCache o : this.lsEntityDrawCache) {
			lsCadEntity3d.addAll( o.toDxfR12_view3d(oLayer) );
		}	
	    return lsCadEntity3d;
	}

	/* Getters/Setters */
	
	public ArrayList<IEntityDrawCache> getLsEntityDrawCache() {
		return lsEntityDrawCache;
	}

	public void setLsEntityDrawCache(ArrayList<IEntityDrawCache> lsEntityDrawCache) {
		this.lsEntityDrawCache = lsEntityDrawCache;
	}

	public ArrayList<IEntityDrawCache> getLsEntityDrawCacheHover() {
		return lsEntityDrawCacheHover;
	}

	public void setLsEntityDrawCacheHover(ArrayList<IEntityDrawCache> lsEntityDrawCacheHover) {
		this.lsEntityDrawCacheHover = lsEntityDrawCacheHover;
	}

	public ArrayList<IEntityDrawCache> getLsEntityDrawCacheSelected() {
		return lsEntityDrawCacheSelected;
	}

	public void setLsEntityDrawCacheSelected(ArrayList<IEntityDrawCache> lsEntityDrawCacheSelected) {
		this.lsEntityDrawCacheSelected = lsEntityDrawCacheSelected;
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
