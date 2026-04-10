/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadEixoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/09/2025
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

package br.com.tlmv.aicadxmod.drenagem.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.ICadEntity;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.TextEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomTextPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadEixoDrenagemRecord;

public class CadEixoDrenagem extends CadEntity 
{
//Private
	private GeomPoint3d ptMin;
    private GeomPoint3d ptMax;
    private double escalaEixo;
    private double eixoDistX;
    private double eixoDistY;
    
//Public

    public CadEixoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDREIXODRENAGEM, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	public void init(
		GeomPoint2d ptMin,
		GeomPoint2d ptMax,
	    double escalaEixo,
	    double eixoDistX,
	    double eixoDistY ) 
	{
		this.ptMin = new GeomPoint3d( ptMin );
		this.ptMax = new GeomPoint3d( ptMax );
	    this.escalaEixo = escalaEixo;
	    this.eixoDistX = eixoDistX;
	    this.eixoDistY = eixoDistY;
		
		this.createAllDrawCache();
    }
	
	public void init(
		GeomPoint3d ptMin,
		GeomPoint3d ptMax,
	    double escalaEixo,
	    double eixoDistX,
	    double eixoDistY ) 
	{
		this.ptMin = new GeomPoint3d( ptMin );
		this.ptMax = new GeomPoint3d( ptMax );
	    this.escalaEixo = escalaEixo;
	    this.eixoDistX = eixoDistX;
	    this.eixoDistY = eixoDistY;
		
		this.createAllDrawCache();
    }
	
	public void init(
		double xMin, 
		double yMin, 
		double zMin, 
		//
		double xMax, 
		double yMax, 
		double zMax,
		//
	    double escalaEixo,
	    double eixoDistX,
	    double eixoDistY ) 
	{
		this.ptMin = new GeomPoint3d(xMin, yMin, zMin);
		this.ptMax = new GeomPoint3d(xMax, yMax, zMax);
	    this.escalaEixo = escalaEixo;
	    this.eixoDistX = eixoDistX;
	    this.eixoDistY = eixoDistY;
		
		this.createAllDrawCache();
    }
	
	@Override
	public void init(ICadObject o) {
		CadEixoDrenagem other = (CadEixoDrenagem)o; 

		this.init(
			other.ptMin, 
			other.ptMax, 
		    other.escalaEixo,
		    other.eixoDistX,
		    other.eixoDistY );
	}
	
	/* CREATE */
	
	public static CadEixoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		GeomPoint2d ptMin, 
		GeomPoint2d ptMax,
	    double escalaEixo,
	    double eixoDistX,
	    double eixoDistY ) 
	{
    	CadEixoDrenagem o = new CadEixoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptMin, ptMax, escalaEixo, eixoDistX, eixoDistY);
    	return o;
    }
	
	public static CadEixoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		GeomPoint3d ptMin, 
		GeomPoint3d ptMax,
	    double escalaEixo,
	    double eixoDistX,
	    double eixoDistY ) 
	{
    	CadEixoDrenagem o = new CadEixoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptMin, ptMax, escalaEixo, eixoDistX, eixoDistY);
    	return o;
    }

	public static CadEixoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		double xMin, 
		double yMin, 
		double zMin, 
		double xMax, 
		double yMax, 
		double zMax, 
		double escalaEixo,
	    double eixoDistX,
	    double eixoDistY,
	    boolean bLocked ) 
	{
    	CadEixoDrenagem o = new CadEixoDrenagem(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(xMin, yMin, zMin, xMax, yMax, zMax, escalaEixo, eixoDistX, eixoDistY);    			
    	return o;
    }
	
	public static CadEixoDrenagem create(CadEixoDrenagem other) {
    	CadEixoDrenagem o = new CadEixoDrenagem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadEixoDrenagem create(CadBlockDef blkDef, CadEixoDrenagem other) {
    	CadEixoDrenagem o = new CadEixoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadEixoDrenagem duplicate()
	{
		CadEixoDrenagem other = CadEixoDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadEixoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadEixoDrenagem other = CadEixoDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadEixoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadEixoDrenagem otherRectangle = CadEixoDrenagem.create(this);
		otherRectangle.moveTo(ptIMcs, ptFMcs);
		return otherRectangle;
	}

	@Override
	public CadEixoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);
    	MoveData2dVO oMin = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigMin2dMcs);
    	this.ptMin = new GeomPoint3d(oMin.getPtDest());

    	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);
    	MoveData2dVO oMax = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigMax2dMcs);
    	this.ptMax = new GeomPoint3d(oMax.getPtDest());

		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		GeomPoint3d ptCentroid = GeomUtil.midPointOf(this.ptMin, this.ptMax);
		
		GeomPoint3d newPtCentroid = GeomUtil.mirror(ptCentroid, ptI2dMcs, ptF2dMcs);

		double xMin = this.ptMin.getX();
		double yMin = this.ptMin.getY();

		double xMax = this.ptMax.getX();
		double yMax = this.ptMax.getY();
		
		double width = xMax - xMin;
		double height = yMax - yMin;
	
		double hWidth = width / 2.0;
		double hHeight = height / 2.0;

		double newXMin = newPtCentroid.getX() - hWidth;
		double newYMin = newPtCentroid.getY() - hHeight;
		double newZMin = newPtCentroid.getZ();

		double newXMax = newPtCentroid.getX() + hWidth;
		double newYMax = newPtCentroid.getY() + hHeight;
		double newZMax = newPtCentroid.getZ();
		
		this.ptMin = new GeomPoint3d(newXMin, newYMin, newZMin);
		this.ptMax = new GeomPoint3d(newXMax, newYMax, newZMax);

		this.createAllDrawCache();
		return this;
	}

	@Override
	public CadEixoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigMin2dMcs = new GeomPoint2d(this.ptMin);

    	ScaleData2dVO oMin = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigMin2dMcs);
    	this.ptMin = new GeomPoint3d(oMin.getPtDest());
		
    	GeomPoint2d ptOrigMax2dMcs = new GeomPoint2d(this.ptMax);

    	ScaleData2dVO oMax = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigMax2dMcs);
    	this.ptMax = new GeomPoint3d(oMax.getPtDest());

		this.createAllDrawCache();
    	return this;
	}
	
	@Override
	public CadEixoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadEixoDrenagem oRect = copyTo(ptIMcs, ptFMcs);
		return oRect;
	}
    
	/* DEBUG */
    
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptMin.toPropertyList("Pt.Min", true) );
		lsProperty.addAll( this.ptMax.toPropertyList("Pt.Max", true) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XMin: %s; YMin: %s; ZMin: %s)-(XMax: %s; YMax: %s; ZMax: %s); ", 
			nf6.format(this.ptMin.getX()), 
			nf6.format(this.ptMin.getY()), 
			nf6.format(this.ptMin.getZ()),
			nf6.format(this.ptMax.getX()), 
			nf6.format(this.ptMax.getY()), 
			nf6.format(this.ptMax.getZ()) );
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
		DrawCache cache = new DrawCache();
		
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		double fontSz = AppDefs.FONTSZ_SMALL;
		
		double lineHeight = fontSz * 1.5 * AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
		//LIMITES
		//
		double xMin = this.ptMin.getX();
		double yMin = this.ptMin.getY();
		
		double xMax = this.ptMax.getX();
		double yMax = this.ptMax.getY();
		
		//LINHAS_EIXO
		//
		Color c = Color.YELLOW;
		
		//xMin0 + yMin0 - localizacao ajustada do limite minimo do eixo de coordenadas
		//
		double xMin0 = Math.floor( Math.abs(xMin) );
		if(xMin < 0) {
			xMin0 = - xMin0;			
		}
		
		double yMin0 = Math.floor( Math.abs(yMin) );
		if(yMin < 0) {
			yMin0 = - yMin0;			
		}

		//xMax0 + yMax0 - localizacao ajustada do limite minimo do eixo de coordenadas
		//
		double xMax0 = Math.ceil( Math.abs(xMax) );
		if(xMax < 0) {
			xMax0 = - xMax0;			
		}
		
		double yMax0 = Math.ceil( Math.abs(yMax) );
		if(yMax < 0) {
			yMax0 = - yMax0;			
		}

		//dx + dy - largura da area de eixos de coordenadas
		//
		double dx = Math.abs( xMax0 - xMin0 );
		double dy = Math.abs( yMax0 - yMin0 );

		//nx + ny - numero de eixos de coordenadas
		//
		double nx = Math.ceil( dx / this.eixoDistX );
		double ny = Math.ceil( dy / this.eixoDistY );
		
		LineStringEntityDrawCache oLine = new LineStringEntityDrawCache(); 

		oLine.addPoint3d( new GeomPoint3d(xMin0, yMin0, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMax0, yMin0, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMax0, yMax0, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMin0, yMax0, 0.0) );
		oLine.addPoint3d( new GeomPoint3d(xMin0, yMin0, 0.0) );

		cache.addItem(oLine);

		for(double x = xMin0; x < xMax0; x += this.eixoDistX) {
			oLine = new LineStringEntityDrawCache(c);
			oLine.addPoint3d( new GeomPoint3d(x, yMin0, 0.0) );
			oLine.addPoint3d( new GeomPoint3d(x, yMax0, 0.0) );
			cache.addItem(oLine);
			
			TextEntityDrawCache oText = new TextEntityDrawCache();

			String strXCoords = String.format("%s", nf3.format(x) );			
			oText.addTextPoint2d( new GeomTextPoint2d(strXCoords, new GeomPoint2d(x - lineHeight, yMin), fontSz, 90.0, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_BOTTOM) );
			oText.addTextPoint2d( new GeomTextPoint2d(strXCoords, new GeomPoint2d(x - lineHeight, yMax), fontSz, 90.0, AppDefs.HORIZALIGN_RIGHT, AppDefs.VERTALIGN_BOTTOM) );
			cache.addItem(oText);
		}
		
		for(double y = yMin0; y < yMax0; y += this.eixoDistY) {
			oLine = new LineStringEntityDrawCache(c);
			oLine.addPoint3d( new GeomPoint3d(xMin0, y, 0.0) );
			oLine.addPoint3d( new GeomPoint3d(xMax0, y, 0.0) );
			cache.addItem(oLine);
			
			TextEntityDrawCache oText = new TextEntityDrawCache();

			String strYCoords = String.format("%s", nf3.format(y) );
			oText.addTextPoint2d( new GeomTextPoint2d(strYCoords, new GeomPoint2d(xMin, y + lineHeight), fontSz, 0.0, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_BOTTOM) );
			oText.addTextPoint2d( new GeomTextPoint2d(strYCoords, new GeomPoint2d(xMax, y + lineHeight), fontSz, 0.0, AppDefs.HORIZALIGN_RIGHT, AppDefs.VERTALIGN_BOTTOM) );
			cache.addItem(oText);
		}
		return cache;
	}

	@Override
	public DrawCache createDrawCache3d()
	{
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		DrawCache osnapCache = new DrawCache();

    	//ENDPOINT
    	//
    	double xMinMcs = this.ptMin.getX();
    	double yMinMcs = this.ptMin.getY();
    	double zMinMcs = this.ptMin.getZ();
    	//
    	double xMaxMcs = this.ptMax.getX();
    	double yMaxMcs = this.ptMax.getY();
    	double zMaxMcs = this.ptMin.getZ();
    	
    	GeomPoint3d pt3d0 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMinMcs, yMinMcs, zMinMcs);
    	GeomPoint3d pt3d1 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMaxMcs, yMinMcs, zMinMcs);
    	GeomPoint3d pt3d2 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMaxMcs, yMaxMcs, zMinMcs);
    	GeomPoint3d pt3d3 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, xMinMcs, yMaxMcs, zMinMcs);

    	osnapCache.addOsnapItem( pt3d0 );
    	osnapCache.addOsnapItem( pt3d1 );
    	osnapCache.addOsnapItem( pt3d2 );
    	osnapCache.addOsnapItem( pt3d3 );
    	
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, this.ptMin, this.ptMax);
    	osnapCache.addOsnapItem( pt3dMid );

    	GeomPoint3d pt3dMid0 = GeomUtil.midPointOf(pt3d0, pt3d1);
    	GeomPoint3d pt3dMid1 = GeomUtil.midPointOf(pt3d1, pt3d2);
    	GeomPoint3d pt3dMid2 = GeomUtil.midPointOf(pt3d2, pt3d3);
    	GeomPoint3d pt3dMid3 = GeomUtil.midPointOf(pt3d3, pt3d0);

    	osnapCache.addOsnapItem( pt3dMid0 );
    	osnapCache.addOsnapItem( pt3dMid1 );
    	osnapCache.addOsnapItem( pt3dMid2 );
    	osnapCache.addOsnapItem( pt3dMid3 );
		
    	//CENTER
    	//
    	GeomPoint3d ptCenter3d = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, pt3d0, pt3d2);
    	osnapCache.addOsnapItem( ptCenter3d );
    	
		return osnapCache;
	}
    
    /* DRAWING */
	
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

            GeomPoint2d ptCentroid2d = new GeomPoint2d( GeomUtil.midPointOf(this.ptMin, this.ptMax) );
        	DrawUtil.drawPointMcs(v, ptCentroid2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);

        	GeomUtil.setColor(g, oldcol);
        
        	GeomUtil.setLtype(g, oldltype);
        }
    }
	
	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
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

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = GeomUtil.midPointOf(ptMin, ptMax);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new Double( this.ptMin.getX() ),
			new Double( this.ptMin.getY() ),
			new Double( this.ptMin.getZ() ),
			//
			new Double( this.ptMax.getX() ),
			new Double( this.ptMax.getY() ),
			new Double( this.ptMax.getZ() ),
			//
			new Double( this.escalaEixo ),
			new Double( this.eixoDistX ),
			new Double( this.eixoDistY )
 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadEixoDrenagemRecord entRec = new CadEixoDrenagemRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );

		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
    /* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomDimension3d oDim = new GeomDimension3d(this.ptMin, this.ptMax); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oDim = new GeomDimension2d(this.ptMin, this.ptMax); 
		return oDim;
	}

    public GeomPoint3d getPtMin() {
        return this.ptMin;
    }

    public GeomPoint3d getPtMax() {
        return this.ptMax;
    }

	public double getEscalaEixo() {
		return escalaEixo;
	}

	public void setEscalaEixo(double escalaEixo) {
		this.escalaEixo = escalaEixo;
	}

	public double getEixoDistX() {
		return eixoDistX;
	}

	public void setEixoDistX(double eixoDistX) {
		this.eixoDistX = eixoDistX;
	}

	public double getEixoDistY() {
		return eixoDistY;
	}

	public void setEixoDistY(double eixoDistY) {
		this.eixoDistY = eixoDistY;
	}

}
