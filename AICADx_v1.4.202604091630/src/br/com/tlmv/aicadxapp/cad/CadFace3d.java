/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadFace3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/02/2025
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

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadFace3dRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;
import br.com.tlmv.aicadxmod.arquitetura.dao.record.CadPisoPointRecord;

public class CadFace3d extends CadEntity 
{
//Private
    private GeomPoint3d ptCentroid;
    private ArrayList<GeomPoint3d> lsPts;
    
//Public

    public CadFace3d(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_FACE3D, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */

	public void init(
		double ptCentroidX,
		double ptCentroidY,
		double ptCentroidZ ) 
	{
		this.lsPts = new ArrayList<GeomPoint3d>();

		this.ptCentroid = new GeomPoint3d(
			ptCentroidX,
			ptCentroidY, 
			ptCentroidZ);
		
		this.createAllDrawCache();
    }
    
	private void init(ArrayList<GeomPoint2d> lsPts2d, double altura) 
	{
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.from2dTo3d(lsPts2d, altura);
		this.init(lsPts3d);
	}

	public void init(ArrayList<GeomPoint3d> lsPts3d) 
	{
		this.lsPts = new ArrayList<GeomPoint3d>(lsPts3d);
		this.ptCentroid = GeomUtil.centroidOf3d(lsPts3d);
		
		this.createAllDrawCache();
    }
	
	@Override
	public void init(ICadObject o) {
		CadFace3d other = (CadFace3d)o; 

	    this.init( other.lsPts );
	}
	
	/* CREATE */
	
	public static CadFace3d create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		ArrayList<GeomPoint2d> lsPts2d,
		double altura) 
	{
		CadFace3d o = new CadFace3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(lsPts2d, altura);
    	return o;
    }
	
	public static CadFace3d create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		double ptCentroidX,
		double ptCentroidY,
		double ptCentroidZ) 
	{
    	CadFace3d o = new CadFace3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		ptCentroidX,
    		ptCentroidY,
    		ptCentroidZ);
    	return o;
    }
	
	public static CadFace3d create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		double ptCentroidX,
		double ptCentroidY,
		double ptCentroidZ,
		boolean bLocked) 
	{
		CadFace3d o = new CadFace3d(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(
    		ptCentroidX,
    		ptCentroidY,
    		ptCentroidZ);
    	return o;
    }
	
	public static CadFace3d create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		ArrayList<GeomPoint3d> lsPts3d) 
	{
		CadFace3d o = new CadFace3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(lsPts3d);
    	return o;
    }
	
	public static CadFace3d create(CadFace3d other) {
		CadFace3d o = new CadFace3d(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadFace3d create(CadBlockDef blkDef, CadFace3d other) {
		CadFace3d o = new CadFace3d(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadFace3d duplicate()
	{
		CadFace3d other = CadFace3d.create(this);
		return other;
	}
	
	@Override
	public CadFace3d duplicate(CadBlockDef blkDef)
	{
		CadFace3d other = CadFace3d.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadFace3d copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadFace3d other = CadFace3d.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadFace3d moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		this.lsPts = newLsPts;
    	return this;
	}

	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts) 
		{
			GeomPoint3d oPtDest3d = GeomUtil.mirror(oPt3d, ptI2dMcs, ptF2dMcs);
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		this.lsPts = newLsPts;
		return this;
	}
	
	@Override
	public CadFace3d scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	ScaleData3dVO o = GeomUtil.scaleToPt3dByRefDist(refDist, ptIMcs, ptFMcs, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
		this.lsPts = newLsPts;
    	return this;
	}

	@Override
	public ICadEntity offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist) {
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadFace3d o = copyTo(ptIMcs, ptFMcs);
		return o;
	}
        
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String strPontos = ListUtil.toStrArray(this.lsPts);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptCentroid.toPropertyList("Pt.Centroid", false) );
		lsProperty.add( new ItemDataVO("Pontos", strPontos, false) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		String str = String.format(
			"Centroid:%s;", 
		    ptCentroid.toStr() );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWCACHE */
	
	@Override
	public DrawCache createDrawCache2d() {
		if( this.lsPts == null ) return null;

		int sz = this.lsPts.size();
		if( sz < 3 ) return null;
		
		DrawCache cache = new DrawCache();
		
		//DRAW_AREA_CONTOUR
		//
		GeomPoint3d ptI3d = this.lsPts.get(0);
		double somaX = ptI3d.getX();
		double somaY = ptI3d.getY();
		double somaZ = ptI3d.getZ();

		LineStringEntityDrawCache oLine = null; 
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF3d = this.lsPts.get(i);
			somaX = somaX + ptF3d.getX();
			somaY = somaY + ptF3d.getY();
			somaZ = somaZ + ptF3d.getZ();

			oLine = new LineStringEntityDrawCache(); 
			oLine.addPoint3d(ptI3d);
			oLine.addPoint3d(ptF3d);
			cache.addItem(oLine);			

			ptI3d = ptF3d;
		}

		return cache;
	}
	
	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		if( this.lsPts == null ) return null;

		int sz = this.lsPts.size();
		if( sz < 3 ) return null;
		
		DrawCache osnapCache = new DrawCache();

		GeomPoint3d ptI3d = this.lsPts.get(0);
		double somaX = ptI3d.getX();
		double somaY = ptI3d.getY();
		double somaZ = ptI3d.getZ();
		
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF3d = this.lsPts.get(i);
			somaX = somaX + ptF3d.getX();
			somaY = somaY + ptF3d.getY();
			somaZ = somaZ + ptF3d.getZ();

			//ENDPOINT
	    	//
			LineStringEntityDrawCache oLine = new LineStringEntityDrawCache(); 
			osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptI3d) );
			osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptF3d) );
			osnapCache.addItem(oLine);			

			//MIDDLE
	    	//
	    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, ptI3d, ptF3d);
			osnapCache.addOsnapItem( pt3dMid );

			ptI3d = ptF3d;
		}

		double xCenter3d = somaX / sz;   
		double yCenter3d = somaY / sz;   
		double zCenter3d = somaZ / sz;   

		GeomPoint3d ptCenter3d = new GeomPoint3d(xCenter3d, yCenter3d, zCenter3d);
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptCenter3d) );
		
		return osnapCache;
	}
	
    /* DRAWING */
	
    // 2D-VIEW
    //
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;
    	
        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		super.redraw2d(v, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, g) ;
		
        if(bSelected || bHover) {
        	Stroke oldltype = GeomUtil.setLtype(g, b);

        	Color oldcol = GeomUtil.setColor(g, c);		

            ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
            GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);

        	DrawUtil.drawPointMcs(v, ptCentroid2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);

        	GeomUtil.setColor(g, oldcol);
        
        	GeomUtil.setLtype(g, oldltype);
        }
    }
    
    // 3D-VIEW
    //
	public void redraw3d_3dView(ICadViewBase v, Color c, ArrayList<GeomPoint3d> lsPtsMcs, PrepareDrawUtil prep)
	{
		GeomVector3d axisZ = GeomUtil.axisZ3d();
		
		//DRAW - LEFT/RIGHT SIDE
        prep.addFace(v, this, c, lsPtsMcs, null);
	}
    
	@Override
    public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
    {
    	if( !this.isVisible() ) return;
    	
        //boolean bSelected = this.isSelected();
		//boolean bHover = false;
		//if( !bSelected )
		//	bHover = this.select2d(v, pt2dMcs, false);
		//Color c = super.selectColor(bDragMode, bSelected, bHover);
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

		//Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();
        
        if(ptBase2dMcs != null) 
        {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

            if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	CadFace3d oFace = this.duplicate();
	        	oFace.moveTo(ptBase3dMcs, pt3dMcs);
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	CadFace3d oFace = this.duplicate();
	        	oFace.scaleTo(1.0, ptBase3dMcs, pt3dMcs);
	        }
        }
        
        redraw3d_3dView(v, c, this.lsPts, prep);
        
        //GeomUtil.setColor(g, oldcol);
    }
    
	/* SELECT */
	
	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
	{
		return null;
	}

	/* OSNAP */

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	if( this.lsPts == null ) return null;
    	
    	int sz = this.lsPts.size();
    	if( sz < 3 ) return null;
    	
    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = GeomUtil.from3dTo3d(this.lsPts);    	
		
    	//MIDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtMidpoint = new ArrayList<GeomPoint3d>();    	

    	GeomPoint3d ptI3d = this.lsPts.get(0); 
    	
    	for(int i = 1; i < sz; i++) {
        	GeomPoint3d ptF3d = this.lsPts.get(i); 

        	GeomPoint3d ptMid3d = GeomUtil.midPointOf(ptI3d, ptF3d);
        	lsPtMidpoint.add(ptMid3d);
        	
        	ptI3d = ptF3d;
    	}
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>();
    	lsPtCenter.add( new GeomPoint3d(this.ptCentroid) );
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMidpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = GeomUtil.from3dTo3d(AppDefs.OSNAPMODE_ENDPOINT, this.lsPts);    	
		
    	//MIDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtMidpoint = new ArrayList<GeomPoint3d>();    	

    	GeomPoint3d ptI3d = this.lsPts.get(0); 
    	
    	int sz = this.lsPts.size();
    	for(int i = 1; i < sz; i++) {
        	GeomPoint3d ptF3d = this.lsPts.get(i); 

        	GeomPoint3d ptMid3d = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, ptI3d, ptF3d);
        	lsPtMidpoint.add(ptMid3d);
        	
        	ptI3d = ptF3d;
    	}
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>();
    	lsPtCenter.add( new GeomPoint3d(AppDefs.OSNAPMODE_CENTER, this.ptCentroid) );
    	
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtEndpoint);
    	lsResult.addAll(lsPtMidpoint);
    	lsResult.addAll(lsPtCenter);
    	return lsResult;
	}

	/* CENTROID */

	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCentroid);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	public void loadAllItens(ArrayList<BasePointRecord> lsPts)
	{
		this.lsPts = new ArrayList<GeomPoint3d>();
		for(BasePointRecord oPtRec : lsPts) {
			GeomPoint3d oPt3d = oPtRec.toGeomPoint3d();
			this.lsPts.add(oPt3d);			
		}
		this.createAllDrawCache();
	}
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lspts(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BasePointDao ptDao = dao.createPtDao(AppDefs.OBJTYPE_BIMPISO_GEOMPOINT); 
		
		String cadRefEntityId = Integer.toString(this.getObjectId());
		
		ArrayList<GeomPoint3d> lsPts3d = this.getLsPts();
		int szLsPts = lsPts3d.size();
		for(int i = 0; i < szLsPts; i++) {
			GeomPoint3d oPt = lsPts3d.get(i);
			
			BasePointRecord ptRec = new BasePointRecord(cadRefEntityId, objVer, oPt);
			int rscode = ptDao.insertOrUpdate(
				objVer,
				schemaName, 
				CadPisoPointRecord.sqlTableName,
				(BasePointRecord) ptRec );
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new Double( ptCentroid.getX() ),
			new Double( ptCentroid.getY() ),
			new Double( ptCentroid.getZ() )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 

		CadFace3dRecord entRec = new CadFace3dRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );
		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = this.save_entity(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_lspts(objVer, dao, schemaName, doc);
		if( !bResult ) return false;

		return bResult;
	}
	
	/* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomPoint3d[] arr = GeomUtil.maxMinPointOfArray3d(this.lsPts);		
		
		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint3d[] arr = GeomUtil.maxMinPointOfArray3d(this.lsPts);		
		
		GeomPoint3d ptMin2d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax2d = new GeomPoint3d(arr[1]);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString();
		return searchString;
	}

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public void setPtCentroid(GeomPoint3d ptCentroid) {
		this.ptCentroid = ptCentroid;
	}

    public ArrayList<GeomPoint3d> getLsPts() {
        return this.lsPts;
    }

    public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}
    
}
