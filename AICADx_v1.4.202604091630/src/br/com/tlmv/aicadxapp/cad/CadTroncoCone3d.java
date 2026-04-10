/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadTroncoCone3D.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/05/2025
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
import br.com.tlmv.aicadxapp.dao.record.CadTroncoCone3dRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadTroncoCone3d extends CadEntity 
{
//Private
    private GeomPoint3d ptCenter;
    private double baseRadius;    
    private double topRadius;    
    private double altura;    
    
//Public

    public CadTroncoCone3d(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_TRONCOCONE3D, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
    public void init(GeomPoint2d ptCenter, double baseRadius, double topRadius, double altura) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, baseRadius, topRadius, altura);
	}
	
	public void init(GeomPoint3d ptCenter, double baseRadius, double topRadius, double altura) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), baseRadius, topRadius, altura);
	}

	public void init(double xCenter, double yCenter, double zCenter, double baseRadius, double topRadius, double altura) {
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.baseRadius = baseRadius;
		this.topRadius = topRadius;
		this.altura = altura;
    }
	
	@Override
	public void init(ICadObject o) {
		CadTroncoCone3d other = (CadTroncoCone3d)o;

		GeomPoint3d ptCenter = new GeomPoint3d(other.ptCenter);
		this.baseRadius = other.baseRadius;
		this.topRadius = other.topRadius;
		double altura = other.altura;
		
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), baseRadius, topRadius, altura);
	}
	
	/* CREATE */
	
	public static CadTroncoCone3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptCenter, double baseRadius, double topRadius, double altura) {
    	CadTroncoCone3d o = new CadTroncoCone3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptCenter, baseRadius, topRadius, altura);
    	return o;
    }
	
	public static CadTroncoCone3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptCenter, double baseRadius, double topRadius, double altura) {
		CadTroncoCone3d o = new CadTroncoCone3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptCenter, baseRadius, topRadius, altura);
    	return o;
    }
	
	public static CadTroncoCone3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, double baseRadius, double topRadius, double altura) {
    	CadTroncoCone3d o = new CadTroncoCone3d(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(xCenter, yCenter, zCenter, baseRadius, topRadius, altura);
    	return o;
    }
	
	public static CadTroncoCone3d create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, double baseRadius, double topRadius, double altura, boolean bLocked) {
    	CadTroncoCone3d o = new CadTroncoCone3d(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(xCenter, yCenter, zCenter, baseRadius, topRadius, altura);
    	return o;
    }
	
	public static CadTroncoCone3d create(CadTroncoCone3d other) {
    	CadTroncoCone3d o = new CadTroncoCone3d(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadTroncoCone3d create(CadBlockDef blkDef, CadTroncoCone3d other) {
    	CadTroncoCone3d o = new CadTroncoCone3d(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadTroncoCone3d duplicate()
	{
		CadTroncoCone3d other = CadTroncoCone3d.create(this);
		return other;
	}
	
	@Override
	public CadTroncoCone3d duplicate(CadBlockDef blkDef)
	{
		CadTroncoCone3d other = CadTroncoCone3d.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadTroncoCone3d copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadTroncoCone3d other = CadTroncoCone3d.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadTroncoCone3d moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);

    	MoveData2dVO oNewCenter = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptCenter2d);
    	this.ptCenter = new GeomPoint3d(oNewCenter.getPtDest());
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptCenter = GeomUtil.mirror(this.ptCenter, ptI2dMcs, ptF2dMcs);
		return this;
	}

	@Override
	public CadTroncoCone3d scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2d = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO oNewCenter = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptCenter2d);
    	this.ptCenter = new GeomPoint3d(oNewCenter.getPtDest());	
    	this.baseRadius = this.baseRadius * oNewCenter.getScale();
    	this.topRadius = this.topRadius * oNewCenter.getScale();
    	return this;
	}
	
	@Override
	public CadTroncoCone3d offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadTroncoCone3d oOffset = copyTo(ptIMcs, ptFMcs);
		return oOffset;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptCenter.toPropertyList("Pt.Center", true) );
		//
		lsProperty.add( new ItemDataVO("Top Radius", nf3.format(this.topRadius), true) );
		lsProperty.add( new ItemDataVO("Base Radius", nf3.format(this.baseRadius), true) );
		lsProperty.add( new ItemDataVO("Altura", nf3.format(this.altura), true) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"XCenter:%s;YCenter:%s;ZCenter:%s;TopRadius:%s;BaseRadius:%s;Altura:%s; ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.topRadius), 
			nf6.format(this.baseRadius), 
			nf6.format(this.altura) );
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

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);
				
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDestCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double topRadius = this.topRadius;
        double baseRadius = this.baseRadius;
        
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
		        	CadTroncoCone3d other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadTroncoCone3d other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadTroncoCone3d other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadTroncoCone3d other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDestCenter2dMcs = new GeomPoint2d(other.ptCenter);
		        }
	        }
        }
        
        DrawUtil.drawCircleMcs(v, ptDestCenter2dMcs, topRadius, g);

        DrawUtil.drawCircleMcs(v, ptDestCenter2dMcs, baseRadius, g);

        DrawUtil.drawRadialLinesMcs(v, ptDestCenter2dMcs, baseRadius, topRadius, g);
        
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptDestCenter2dMcs, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
        }

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
    
	@Override
    public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
    {
    	if( !this.isVisible() ) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint3d ptDestMin3dMcs = new GeomPoint3d(
        	this.ptCenter.getX(),
        	this.ptCenter.getY(),
        	this.ptCenter.getZ() );

        GeomPoint3d ptDestMax3dMcs = new GeomPoint3d(
        	this.ptCenter.getX(),
        	this.ptCenter.getY(),
        	this.ptCenter.getZ() + this.altura );
                
        GeomVector3d axisZ = GeomUtil.axisZ3d();
        prep.addTroncoCone(v, this, c, ptDestMin3dMcs, ptDestMax3dMcs, this.baseRadius, this.topRadius, axisZ);
    }
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double radius = Math.max(this.topRadius, this.baseRadius);

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMin = radius - (boxSz / 2.0);
        double distMax = radius + (boxSz / 2.0);
        
        double dist = ptCenter2dMcs.distTo(pt2dMcs); 

        if( (dist >= distMin) && (dist <= distMax) ) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
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

    	double zp = this.ptCenter.getZ();

        GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double radius = Math.max(this.topRadius, this.baseRadius);
        
    	GeomVector2d vAxisX = new GeomVector2d(radius, 0.0);
    	GeomVector2d vDir = new GeomVector2d(ptCenter2dMcs, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add(new GeomPoint3d(this.ptCenter));

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vDir.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vDir.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vDir.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vDir.otherRotateToDegrees(270.0);
    	
    	ArrayList<GeomPoint3d> lsPtQuadrant = new ArrayList<GeomPoint3d>();    	
		lsPtQuadrant.add( new GeomPoint3d(vPt0d.getXF(), vPt0d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt90d.getXF(), vPt90d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt180d.getXF(), vPt180d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(vPt270d.getXF(), vPt270d.getYF(), zp) );
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_QUADRANT, pt2dMcs, lsPtQuadrant, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	double zp = this.ptCenter.getZ();

        GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);
        double radius = Math.max(this.topRadius, this.baseRadius);
        
    	GeomVector2d vAxisX = new GeomVector2d(radius, 0.0);
    	GeomVector2d vDir = new GeomVector2d(ptCenter2dMcs, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add( new GeomPoint3d(AppDefs.OSNAPMODE_CENTER, this.ptCenter) );

    	//QUADRANT
    	//
    	GeomVector2d vPt0d = vDir.otherRotateToDegrees(0.0);
    	GeomVector2d vPt90d = vDir.otherRotateToDegrees(90.0);
    	GeomVector2d vPt180d = vDir.otherRotateToDegrees(180.0);
    	GeomVector2d vPt270d = vDir.otherRotateToDegrees(270.0);
    	
    	ArrayList<GeomPoint3d> lsPtQuadrant = new ArrayList<GeomPoint3d>();    	
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt0d.getXF(), vPt0d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt90d.getXF(), vPt90d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt180d.getXF(), vPt180d.getYF(), zp) );
		lsPtQuadrant.add( new GeomPoint3d(AppDefs.OSNAPMODE_QUADRANT, vPt270d.getXF(), vPt270d.getYF(), zp) );
    	
		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		lsResult.addAll(lsPtCenter);
		lsResult.addAll(lsPtQuadrant);
		return lsResult;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCenter);
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
			new Double( ptCenter.getX() ),
			new Double( ptCenter.getY() ),
			new Double( ptCenter.getZ() ),
			//
			new Double( baseRadius ),
			new Double( topRadius ),
			new Double( altura )
				
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadTroncoCone3dRecord entRec = new CadTroncoCone3dRecord(this); 
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
		double xPtCenter = this.ptCenter.getX();
		double yPtCenter = this.ptCenter.getY();
		double zPtCenter = this.ptCenter.getZ();
		
		double xPtMin = xPtCenter - this.baseRadius;
		double yPtMin = yPtCenter - this.baseRadius;
		double zPtMin = zPtCenter - this.baseRadius;
		
		double xPtMax = xPtCenter + this.baseRadius;
		double yPtMax = yPtCenter + this.baseRadius;
		double zPtMax = zPtCenter + this.baseRadius;
		
		GeomPoint3d ptMin3d = new GeomPoint3d(xPtMin, yPtMin, zPtMin);
		GeomPoint3d ptMax3d = new GeomPoint3d(xPtMax, yPtMax, zPtMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		double xPtCenter = this.ptCenter.getX();
		double yPtCenter = this.ptCenter.getY();
		
		double xPtMin = xPtCenter - this.baseRadius;
		double yPtMin = yPtCenter - this.baseRadius;
		
		double xPtMax = xPtCenter + this.baseRadius;
		double yPtMax = yPtCenter + this.baseRadius;
		
		GeomPoint2d ptMin2d = new GeomPoint2d(xPtMin, yPtMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(xPtMax, yPtMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"RAIO=" + Double.toString( this.baseRadius ) +
			"DIAMETRO=" + Double.toString( 2.0 * this.baseRadius );
		return searchString;
	}

	public GeomPoint3d getPtCenter() {
        return this.ptCenter;
    }

    public double getBaseRadius() {
		return baseRadius;
	}

	public void setBaseRadius(double baseRadius) {
		this.baseRadius = baseRadius;
	}

	public double getTopRadius() {
		return topRadius;
	}

	public void setTopRadius(double topRadius) {
		this.topRadius = topRadius;
	}

	public double getAltura() {
		return altura;
	}

}
