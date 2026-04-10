/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadLevel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/03/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadLevelRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadLevel extends CadEntity 
{
//Private
	private String levelLocalName;
	private String levelLocalText;
	private GeomPoint3d ptI;
    private GeomPoint3d ptF;
    
//Public

    public CadLevel() {
		super(AppDefs.OBJTYPE_BIMLEVEL, null, null, null, 0.0, false);		
    }

    public CadLevel(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_BIMLEVEL, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(
		String levelLocalName,
		String levelLocalText,
		GeomPoint2d ptI, 
		GeomPoint2d ptF ) 
	{
		this.init(
			levelLocalName,
			levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY() );
	}
	
	private void init(
		String levelLocalName,
		String levelLocalText,
		GeomPoint3d ptI, 
		GeomPoint3d ptF ) 
	{
		this.init(
			levelLocalName,
			levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY() );
	}

	public void init(
		String levelLocalName,
		String levelLocalText,
		double xI, 
		double yI, 
		double xF, 
		double yF ) 
	{
		double zLevel = super.getZLevel();
		
		this.levelLocalName = levelLocalName;
		this.levelLocalText = levelLocalText;
    	this.ptI = new GeomPoint3d(xI, yI, zLevel);
    	this.ptF = new GeomPoint3d(xF, yF, zLevel);
    }
	
	@Override
	public void init(ICadObject o) {
		CadLevel other = (CadLevel)o;

		String tmpLevelLocalName = other.levelLocalName;
		String tmpLevelLocalText = other.levelLocalText;
		GeomPoint3d ptTmpPtI = other.ptI;
		GeomPoint3d ptTmpPtF = other.ptF;
		
		this.init(
			tmpLevelLocalName,
			tmpLevelLocalText,
			ptTmpPtI.getX(), 
			ptTmpPtI.getY(), 
			ptTmpPtF.getX(), 
			ptTmpPtF.getY() );
	}
	
	/* CREATE */
	
	public static CadLevel create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		double zLevel,
		String levelName,
		String levelText,
		GeomPoint2d ptI, 
		GeomPoint2d ptF ) 
	{
    	CadLevel o = new CadLevel(oBlkDef, oLayer, oLevel, zLevel, false);
    	o.init(
    		levelName, 
    		levelText,
    		ptI, 
    		ptF );
    	return o;
    }
	
	public static CadLevel create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		double zLevel,
		String levelName,
		String levelText,		
		GeomPoint3d ptI, 
		GeomPoint3d ptF ) 
	{
    	CadLevel o = new CadLevel(oBlkDef, oLayer, oLevel, zLevel, false);
    	o.init(
    		levelName, 
    		levelText,
    		ptI, 
    		ptF );
    	return o;
    }

	public static CadLevel create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
		double zLevel,		
		String levelName,
		String levelText,		
		double xI, 
		double yI, 
		double xF, 
		double yF) 
	{
    	CadLevel o = new CadLevel(oBlkDef, oLayer, oLevel, zLevel, false);
    	o.init(levelName, levelText, xI, yI, xF, yF);
    	return o;
    }

	public static CadLevel create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel) 
	{
    	CadLevel o = new CadLevel(oBlkDef, oLayer, oLevel, 0.0, false);

    	GeomPoint2d ptI = new GeomPoint2d( oLevel.getPtI() );
		double xI = ptI.getX();
		double yI = ptI.getY();

    	GeomPoint2d ptF = new GeomPoint2d( oLevel.getPtF() );
		double xF = ptF.getX();
		double yF = ptF.getY();
    	
    	o.init(
    		oLevel.getLevelLocalName(), 
    		oLevel.getLevelLocalText(), 
    		xI, 
    		yI, 
    		xF, 
    		yF );
    	return o;
    }
	
	public static CadLevel create(CadLevel other) {
    	CadLevel o = new CadLevel(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadLevel create(CadBlockDef blkDef, CadLevel other) {
    	CadLevel o = new CadLevel(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadLevel duplicate()
	{
		CadLevel other = CadLevel.create(this);
		return other;
	}
	
	@Override
	public CadLevel duplicate(CadBlockDef blkDef)
	{
		CadLevel other = CadLevel.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadLevel copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadLevel other = CadLevel.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadLevel moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);
    	MoveData2dVO oI = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());

    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);
    	MoveData2dVO oF = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

    	return this;
	}
	
	@Override
	public CadLevel scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptI);

    	ScaleData2dVO oI = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptI = new GeomPoint3d(oI.getPtDest());
		
    	GeomPoint2d ptOrigF2dMcs = new GeomPoint2d(this.ptF);

    	ScaleData2dVO oF = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigF2dMcs);
    	this.ptF = new GeomPoint3d(oF.getPtDest());

    	return this;
	}
	
    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptI = GeomUtil.mirror(this.ptI, ptI2dMcs, ptF2dMcs);
		this.ptF = GeomUtil.mirror(this.ptF, ptI2dMcs, ptF2dMcs);

		return this;
	}
	
	@Override
	public CadLevel offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadLevel oLine = copyTo(ptIMcs, ptFMcs);
		return oLine;
	}
    
	/* DEBUG */

	@Override
	public String toString() {
		return this.getLabel();
	}

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptI.toPropertyList("Pt.Inicial", true) );
		lsProperty.addAll( this.ptF.toPropertyList("Pt.Final", true) );
		lsProperty.add( new ItemDataVO("Name", this.levelLocalName, false) );
		lsProperty.add( new ItemDataVO("Text", this.levelLocalText, true) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XI: %s; YI: %s; ZI: %s)-(XF: %s; YF: %s; ZF: %s); Name: %s; Text: %s; ", 
			nf6.format(this.ptI.getX()), 
			nf6.format(this.ptI.getY()), 
			nf6.format(this.ptI.getZ()),
			nf6.format(this.ptF.getX()), 
			nf6.format(this.ptF.getY()), 
			nf6.format(this.ptF.getZ()),
			this.levelLocalName,
			this.levelLocalText );
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
		return null;
	}

	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}
	
	@Override
	public DrawCache createOsnapCache()
	{
		return null;
	}
	
    /* DRAWING */
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);
		
		double fontSz = AppDefs.FONTSZ_SMALL * sclFact;
		
		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);

		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);	
		
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);
		
        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.ptF);
        
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
		        	CadLevel other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadLevel other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadLevel other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			            ptDestI2dMcs = new GeomPoint2d(other.ptI);
			            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadLevel other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		            ptDestI2dMcs = new GeomPoint2d(other.ptI);
		            ptDestF2dMcs = new GeomPoint2d(other.ptF);
		        }
	        }
        }

        // CALCULATE_DRAWING_POINTS
        //
        double extLineMcs = AppDefs.DEF_DEFAULT_PROJECT_LEVEL_EXTLINESZ_MILI * sclFact;
        double hExtLineMcs = extLineMcs / 2.0;
        
        double xPtCenter = ptDestI2dMcs.getX();
        double yPtCenter = ptDestI2dMcs.getY();

        double xPtMin = xPtCenter - hExtLineMcs;
        double yPtMin = yPtCenter - hExtLineMcs;
        
        double xPtMax = xPtCenter + hExtLineMcs;
        double yPtMax = yPtCenter + hExtLineMcs;
        
        // DRAW LEVEL-LINE
        //
        DrawUtil.drawLineMcs(v, ptDestI2dMcs, ptDestF2dMcs, g);

        // DRAW LEVEL-CIRCLE
        //
        double radiusMcs = (AppDefs.DEF_DEFAULT_PROJECT_LEVEL_SIMBLESZ_MILI / 2.0) * sclFact;
        DrawUtil.drawCircleMcs(v, ptDestI2dMcs, radiusMcs, g);
        
        // DRAW HORIZ-EXT-LINE
        //
        GeomPoint2d ptHorizExtI = new GeomPoint2d(xPtMin, yPtCenter);
        GeomPoint2d ptHorizExtF = new GeomPoint2d(xPtMax, yPtCenter);

        DrawUtil.drawLineMcs(v, ptHorizExtI, ptHorizExtF, g);
        
        // DRAW VERT-EXT-LINE
        //
        GeomPoint2d ptVertExtI = new GeomPoint2d(xPtCenter, yPtMin);
        GeomPoint2d ptVertExtF = new GeomPoint2d(xPtCenter, yPtMax);

        DrawUtil.drawLineMcs(v, ptVertExtI, ptVertExtF, g);
        
        // DRAW TEXT-LEVEL_NAME (LEVEL_HEIGHT)
        //
        String strSign = ( this.getZLevel() >= 0.0 ) ? "+" : "-";
        
        String strText = this.getLabel();
        
    	DrawUtil.drawTextMcs(
    		v, 
    		strText, 
    		ptHorizExtI, 
    		fontSz, 
    		AppDefs.HORIZALIGN_RIGHT,
    		AppDefs.VERTALIGN_BOTTOM, 
    		g);


    	DrawUtil.drawTextMcs(
    		v, 
    		strText, 
    		ptHorizExtF, 
    		fontSz, 
    		AppDefs.HORIZALIGN_LEFT,
    		AppDefs.VERTALIGN_BOTTOM, 
    		g);

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
	
	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
	        
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;

    	if( this.isSelected() ) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.ptI);
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.ptF);
        
        GeomVector2d vIToF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs);
        GeomVector2d uIToF2dMcs = vIToF2dMcs.otherUnit();
        
        GeomVector2d vIToPt2dMcs = new GeomVector2d(ptI2dMcs, pt2dMcs);

    	GeomPoint2d[] arrMaxMinPtMcs = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);
    	GeomPoint2d ptMinMcs = arrMaxMinPtMcs[0];
    	GeomPoint2d ptMaxMcs = arrMaxMinPtMcs[1];
    	
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;

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
            	if( bSelectEntity ) {
            		this.setSelected(true);
            	}
            	return true;
            }
    	}
        return false;
	}

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

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(this.ptI) );
    	lsPtEndpoint.add( new GeomPoint3d(this.ptF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(this.ptI, this.ptF);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
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
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptI) );
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(this.ptI, this.ptF);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();
    	lsPtMiddle.add( new GeomPoint3d(AppDefs.OSNAPMODE_MIDDLE, pt3dMid) );

    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtEndpoint);
    	lsResult.addAll(lsPtMiddle);
    	return lsResult;
	}

	/* CENTROID */	
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = GeomUtil.midPointOf(this.ptI, this.ptF);
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
			new String( this.levelLocalName ),
			new String( this.levelLocalText ),
			//
			new Double( this.ptI.getX() ),
			new Double( this.ptI.getY() ),
			new Double( this.ptI.getZ() ),
			//
			new Double( this.ptF.getX() ),
			new Double( this.ptF.getY() ),
			new Double( this.ptF.getZ() )
 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadLevelRecord entRec = new CadLevelRecord(this); 
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
        GeomPoint3d ptI3dMcs = new GeomPoint3d(this.getPtI());
        GeomPoint3d ptF3dMcs = new GeomPoint3d(this.getPtF());

		GeomPoint3d[] arr = GeomUtil.maxMinPointOf(ptI3dMcs, ptF3dMcs);		
		
		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.getPtI());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.getPtF());

		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);		
		
		GeomPoint2d ptMin2d = new GeomPoint2d(arr[0]);
		GeomPoint2d ptMax2d = new GeomPoint2d(arr[1]);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" + 
			"NOME=" + this.levelLocalName + "^" +
			"TEXTO=" + this.levelLocalText;
		return searchString;
	}

    public GeomPoint3d getPtI() {
        return this.ptI;
    }

    public GeomPoint3d getPtF() {
        return this.ptF;
    }

	public String getLevelLocalName() {
		return levelLocalName;
	}

	public void setLevelLocalName(String levelLocalName) {
		this.levelLocalName = levelLocalName;
	}

	public String getLevelLocalText() {
		return levelLocalText;
	}

	public void setLevelLocalText(String levelLocalText) {
		this.levelLocalText = levelLocalText;
	}

	public void setPtI(GeomPoint3d ptI) {
		this.ptI = ptI;
	}

	public void setPtF(GeomPoint3d ptF) {
		this.ptF = ptF;
	}
	
	public String getLabel()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);
		
		String strLabel =
			String.format(
				"%s (%s %s m)",
				this.levelLocalText,
				this.getSign(),
				nf3.format( Math.abs( this.getZLevel() ) ) );
		return strLabel;
	}		

}
