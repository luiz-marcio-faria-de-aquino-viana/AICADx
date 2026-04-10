/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAreaContribuicaoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/05/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomTextPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAreaContribuicaoPointDrenagemRecord;

public class CadAreaContribuicaoDrenagem extends CadEntity 
{
//Private
    private CadCaixaInspecaoDrenagem oCI;
    private ArrayList<GeomPoint3d> lsPts;
    private GeomPoint3d ptCentroid;
    private int areaType;
    private String name;
    private double area;
    
    //FONT_SIZE
    private double fMediumSz = AppDefs.FONTSZ_MEDIUM;        
    private double fNormalSz = AppDefs.FONTSZ_NORMAL;        
    private double fSmallSz = AppDefs.FONTSZ_SMALL;        
    
//Public

    public CadAreaContribuicaoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */

	public void init(
		int areaType, 
		String name, 
		CadCaixaInspecaoDrenagem oCI) 
	{
		this.areaType = areaType;
		this.name = name;
		this.lsPts = new ArrayList<GeomPoint3d>();
	    this.ptCentroid = new GeomPoint3d(0.0, 0.0, 0.0);
	    this.area = 0.0;
	    this.oCI = oCI;
		
		this.createAllDrawCache();
	}

	public void init(
		int areaType, 
		String name, 
		ArrayList<GeomPoint3d> lsPts3d, 
		CadCaixaInspecaoDrenagem oCI) 
	{
		this.areaType = areaType;
		this.name = name;
		this.lsPts = new ArrayList<GeomPoint3d>(lsPts3d);
		this.ptCentroid = GeomUtil.centroidOf3d(lsPts3d);
		this.area = GeomUtil.calculateArea(this.ptCentroid, this.lsPts);
	    this.oCI = oCI;
		
		this.createAllDrawCache();
    }
	
	private void init(
		int tipoArea, 
		String name, 
		double altura, 
		ArrayList<GeomPoint2d> lsPts2d, 
		CadCaixaInspecaoDrenagem oCI) 
	{
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.from2dTo3d(lsPts2d, altura);
		this.init(tipoArea, name, lsPts3d, oCI);
	}
	
	@Override
	public void init(ICadObject o) {
		CadAreaContribuicaoDrenagem other = (CadAreaContribuicaoDrenagem)o; 

	    this.init(other.areaType, other.name, other.lsPts, other.oCI);
	}
	
	/* CREATE */
	
	public static CadAreaContribuicaoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		int tipoArea, 
		String name, 
		CadCaixaInspecaoDrenagem oCI) 
	{
    	CadAreaContribuicaoDrenagem o = new CadAreaContribuicaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(tipoArea, name, oCI);
    	return o;
    }
	
	public static CadAreaContribuicaoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		int tipoArea, 
		String name, 
		boolean bLocked,
		CadCaixaInspecaoDrenagem oCI) 
	{
    	CadAreaContribuicaoDrenagem o = new CadAreaContribuicaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(tipoArea, name, oCI);
    	return o;
    }
	
	public static CadAreaContribuicaoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,		
		int tipoArea, 
		String name, 
		double altura, 
		ArrayList<GeomPoint2d> lsPts2d, 
		CadCaixaInspecaoDrenagem oCI) 
	{
    	CadAreaContribuicaoDrenagem o = new CadAreaContribuicaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(tipoArea, name, altura, lsPts2d, oCI);
    	return o;
    }
	
	public static CadAreaContribuicaoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		int tipoArea, 
		String name, 
		ArrayList<GeomPoint3d> lsPts3d, 
		CadCaixaInspecaoDrenagem oCI) 
	{
    	CadAreaContribuicaoDrenagem o = new CadAreaContribuicaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(tipoArea, name, lsPts3d, oCI);
    	return o;
    }
	
	public static CadAreaContribuicaoDrenagem create(CadAreaContribuicaoDrenagem other) {
    	CadAreaContribuicaoDrenagem o = new CadAreaContribuicaoDrenagem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadAreaContribuicaoDrenagem create(CadBlockDef blkDef, CadAreaContribuicaoDrenagem other) {
    	CadAreaContribuicaoDrenagem o = new CadAreaContribuicaoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadAreaContribuicaoDrenagem duplicate()
	{
		CadAreaContribuicaoDrenagem other = CadAreaContribuicaoDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadAreaContribuicaoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadAreaContribuicaoDrenagem other = CadAreaContribuicaoDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadAreaContribuicaoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadAreaContribuicaoDrenagem other = CadAreaContribuicaoDrenagem.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadAreaContribuicaoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadAreaContribuicaoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
		//
		this.area = GeomUtil.calculateArea(this.ptCentroid, this.lsPts);

		return this;
	}
	
	@Override
	public CadAreaContribuicaoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadAreaContribuicaoDrenagem oPolygon = copyTo(ptIMcs, ptFMcs);
		return oPolygon;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		double areaHa = this.area / AppDefs.MATHVAL_HECTARE_TO_METER;
		
		ItemDataVO oAreaType = ListUtil.findItemDataById(Integer.toString(this.areaType), AppDefs.ARR_AREATYPE);		

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);
		
		lsProperty.addAll( this.ptCentroid.toPropertyList("Pt.Centroid", false) );
		//		
		lsProperty.add( new ItemDataVO("Type", oAreaType.getDescricao(), false) );
		lsProperty.add( new ItemDataVO("Name", this.name, false) );
		lsProperty.add( new ItemDataVO("Area (ha)", nf3.format(areaHa), false) );
		lsProperty.add( new ItemDataVO("Numero CI", nf0.format(oCI.getNumeroCI()), false) );
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		double areaHa = this.area / AppDefs.MATHVAL_HECTARE_TO_METER;

		String str = String.format(
			"TipoArea:%s;" + 
			"Name:%s;" + 
			"Centroid:%s;" + 
			"Numero CI:%s;" + 
			"Area(ha):%s;",
			Integer.toString(this.areaType),
			this.name,
		    ptCentroid.toStr(),
			nf0.format(oCI.getNumeroCI()),
			nf3.format(areaHa) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWCACHE */
	
	@Override
	public DrawCache createDrawCache2d() {
		DrawCache cache = new DrawCache();

		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);

		double fontSz = AppDefs.FONTSZ_SMALL;
		
		double lineHeight = fontSz * 2.0 * AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
		double areaHa = this.area / AppDefs.MATHVAL_HECTARE_TO_METER;
		
		int sz = this.lsPts.size();
		if(sz < 3) return null;
		
		if(AppDefs.DEBUG_LEVEL != AppDefs.DEBUG_LEVEL00) {
			//DRAW_POINT-1
			//
			GeomPoint3d pt03d = this.lsPts.get(0);
	
			GeomVector3d axisX = GeomUtil.axisX3d();
			GeomVector3d axisY = GeomUtil.axisY3d();
			
			double pointSize = AppDefs.POINT_SIZE * AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
			double hPointSize = (pointSize / 2.0);
			
			GeomPoint3d pt0_I = pt03d.otherMoveTo(axisY, hPointSize);
			GeomPoint3d pt0_F = pt03d.otherMoveTo(axisY, - hPointSize);
	
			GeomPoint3d pt1_I = pt03d.otherMoveTo(axisX, hPointSize);
			GeomPoint3d pt1_F = pt03d.otherMoveTo(axisX, - hPointSize);
			
			LineStringEntityDrawCache oLine1 = new LineStringEntityDrawCache(); 
			oLine1.addPoint3d(pt0_I);
			oLine1.addPoint3d(pt0_F);
			cache.addItem(oLine1);			
			
			LineStringEntityDrawCache oLine2 = new LineStringEntityDrawCache(); 
			oLine2.addPoint3d(pt1_I);
			oLine2.addPoint3d(pt1_F);
			cache.addItem(oLine2);			
			
			//DRAW_POINT-2
			//
			pt03d = this.lsPts.get(1);
			
			pt0_I = pt03d.otherMoveTo(axisY, hPointSize);
			pt0_F = pt03d.otherMoveTo(axisY, - hPointSize);
			
			pt1_I = pt03d.otherMoveTo(axisX, hPointSize);
			pt1_F = pt03d.otherMoveTo(axisX, - hPointSize);
			
			oLine1 = new LineStringEntityDrawCache(); 
			oLine1.addPoint3d(pt0_I);
			oLine1.addPoint3d(pt0_F);
			cache.addItem(oLine1);			
			
			oLine2 = new LineStringEntityDrawCache(); 
			oLine2.addPoint3d(pt1_I);
			oLine2.addPoint3d(pt1_F);
			cache.addItem(oLine2);
		}
		
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

		//CALCULATE_CENTROID
		//
		double xCenter3d = somaX / sz;   
		double yCenter3d = somaY / sz;   
		//double zCenter3d = somaZ / sz;   

		//DRAW_AREA_TEXT
		//
		double xText3d = xCenter3d;   
		double yText3d = yCenter3d + lineHeight;   
		//double zText3d = zCenter3d;   
        
		TextEntityDrawCache oText = new TextEntityDrawCache();

        String strTipoArea = GeomUtil.getAreaTypeText(this.areaType);
		oText.addTextPoint2d( new GeomTextPoint2d(strTipoArea, new GeomPoint2d(xText3d, yText3d), fontSz, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
		cache.addItem(oText);
		yText3d = yText3d - lineHeight;   
		
        String strName = this.name;        
		oText.addTextPoint2d( new GeomTextPoint2d(strName, new GeomPoint2d(xText3d, yText3d), fontSz, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
		cache.addItem(oText);
		yText3d = yText3d - lineHeight;   

        String strArea = String.format("%s ha", nf3.format(areaHa));
		oText.addTextPoint2d( new GeomTextPoint2d(strArea, new GeomPoint2d(xText3d, yText3d), fontSz, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
		cache.addItem(oText);
		
		return cache;
	}
	
	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		DrawCache osnapCache = new DrawCache();

		if(this.lsPts == null) return null;
		
		int sz = this.lsPts.size();
		if(sz < 3) return null;		
		
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

		super.redraw2d(v, dist, ptBase2dMcs, pt2dMcs, sclFact, bDragMode, bSelEnt, g);
		
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

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
	
    public void redraw2d_202510280653(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if(this.isDeleted()) return;

    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
        
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
        GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);

        String strTipoArea = GeomUtil.getAreaTypeText(this.areaType);
        String strName = this.name;        
        String strArea = String.format("%s hectares", nf3.format(this.getAreaHectare()));

        if( bDragMode ) 
        {
	        if(ptBase2dMcs != null) 
	        {        
	            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
	            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

	            GeomVector3d vDir3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs);
	
	            if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
		        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
		        {
		        	CadAreaContribuicaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
	
		            lsPts2d = GeomUtil.from3dTo2d(other.lsPts); 
		            ptCentroid2d = new GeomPoint2d(other.ptCentroid);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadAreaContribuicaoDrenagem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
	
		            lsPts2d = GeomUtil.from3dTo2d(other.lsPts); 
		            ptCentroid2d = new GeomPoint2d(other.ptCentroid);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadAreaContribuicaoDrenagem other = this.duplicate();
		        		other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
	
			            lsPts2d = GeomUtil.from3dTo2d(other.lsPts); 
			            ptCentroid2d = new GeomPoint2d(other.ptCentroid);

			            strArea = String.format("%s hectares", nf3.format(other.getAreaHectare()));
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadAreaContribuicaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		            lsPts2d = GeomUtil.from3dTo2d(other.lsPts); 
		            ptCentroid2d = new GeomPoint2d(other.ptCentroid);
		        }
	        }
        }
        
        DrawUtil.drawPolygonMcs(v, lsPts2d, g);
        
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fMediumSzMcs = this.fMediumSz * sclFact;
        double fNormalSzMcs = this.fNormalSz * sclFact;
        double fSmallSzMcs = this.fSmallSz * sclFact;
    	
        double hTextLine = fMediumSzMcs;

        GeomPoint2d ptLabelArea = new GeomPoint2d(ptCentroid2d);
        GeomPoint2d ptLabelName = ptLabelArea.otherMoveTo(axisY, hTextLine);
        GeomPoint2d ptLabelTipoArea = ptLabelName.otherMoveTo(axisY, hTextLine);
        
        DrawUtil.drawTextMcs(v, strTipoArea, ptLabelTipoArea, fNormalSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        DrawUtil.drawTextMcs(v, strName, ptLabelName, fNormalSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        DrawUtil.drawTextMcs(v, strArea, ptLabelArea, fSmallSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        		
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptCentroid2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
        }

        GeomUtil.setColor(g, oldcol);

        GeomUtil.setLtype(g, oldltype);
    }
    
	/* SELECT */

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}
	
	public boolean select2d_202510280654(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if(pt2dMcs == null) return false;
		
		if(this.isDeleted()) return false;
		if(this.isSelected()) return true;

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distBox = boxSz / 2.0;

        ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
        GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);

        double distCenter = GeomUtil.maxDistOf2d(ptCentroid2d, lsPts2d);

        double maxDist = distCenter + distBox;
        
        double dist = ptCentroid2d.distTo(pt2dMcs);
		if(dist < maxDist) {
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
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

	public GeomPoint3d osnap3d_202510280655(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;
    	
    	int sz = this.lsPts.size();
    	if(sz == 0) return null;

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
    	
    	GeomPoint3d ptCentroid3d = new GeomPoint3d(this.ptCentroid);
    	lsPtCenter.add(ptCentroid3d);
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMidpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	public ArrayList<GeomPoint3d> osnap3d_202510280655(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
    	if( !this.isVisible() ) return null;
    	
    	int sz = this.lsPts.size();
    	if(sz == 0) return null;

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = GeomUtil.from3dTo3d(AppDefs.OSNAPMODE_ENDPOINT, this.lsPts);    	
		
    	//MIDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtMidpoint = new ArrayList<GeomPoint3d>();    	
    	GeomPoint3d ptI3d = this.lsPts.get(0); 
    	
    	for(int i = 1; i < sz; i++) {
        	GeomPoint3d ptF3d = this.lsPts.get(i); 

        	GeomPoint3d ptMid3d = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, ptI3d, ptF3d);
        	lsPtMidpoint.add(ptMid3d);
        	
        	ptI3d = ptF3d;
    	}
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>();
    	
    	GeomPoint3d ptCentroid3d = new GeomPoint3d(AppDefs.OSNAPMODE_CENTER, this.ptCentroid);
    	lsPtCenter.add(ptCentroid3d);
    	
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
	
	public boolean loadAllPts(ArrayList<BasePointRecord> lsPts)
	{
		this.lsPts = new ArrayList<GeomPoint3d>();
		for(BasePointRecord oPtRec : lsPts) {
			GeomPoint3d oPt3d = oPtRec.toGeomPoint3d();
			this.lsPts.add(oPt3d);
		}
		this.ptCentroid = GeomUtil.centroidOf3d(this.lsPts);
		this.area = GeomUtil.calculateArea(this.ptCentroid, this.lsPts);
		
		this.createAllDrawCache();
		
		return true;
	}

	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lspts(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BasePointDao ptDao = dao.createPtDao(AppDefs.OBJTYPE_AREACONTRIBUICAO_GEOMPOINT); 

		String cadRefEntityId = Integer.toString(this.getObjectId());
		
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			GeomPoint3d oPt = this.lsPts.get(i);
			
			BasePointRecord ptRec = new BasePointRecord(cadRefEntityId, objVer, oPt);
			int rscode = ptDao.insertOrUpdate(objVer, schemaName, CadAreaContribuicaoPointDrenagemRecord.sqlTableName, (BasePointRecord) ptRec);
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
	    int numeroCI = oCI.getObjectId();

	    Object[] arrVal = {
			new Integer( numeroCI ),
			new Double( this.ptCentroid.getX() ),
			new Double( this.ptCentroid.getY() ),
			new Double( this.ptCentroid.getZ() ),
			new Integer( this.areaType ),
			new String( this.name ),
			new Double( this.area )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadAreaContribuicaoDrenagemRecord entRec = new CadAreaContribuicaoDrenagemRecord(this); 
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
		String searchString = super.getSearchString() + "^" + 
			"NOME=" + this.name + "^" +
			"TIPO=" + AppDefs.ARR_AREATYPE[this.areaType] + "^" +
			"AREA=" + Double.toString( this.area ) + "^" +
			"PV=" + Integer.toString( this.oCI.getObjectId() );
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

	public double getAreaMetro2() {
		return area;
	}

	public void setAreaMetro2(double area) {
		this.area = area;
	}

	public double getAreaHectare() {
		return (area / 10000.0);
	}

	public void setAreaHectare(double areaHectare) {
		this.area = (areaHectare * 10000.0);
	}

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CadCaixaInspecaoDrenagem getCI() {
		return oCI;
	}

	public void setCI(CadCaixaInspecaoDrenagem oCI) {
		this.oCI = oCI;
	}
    
}
