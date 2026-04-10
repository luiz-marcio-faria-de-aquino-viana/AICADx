/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadCaixaInspecao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
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

package br.com.tlmv.aicadxmod.esgoto.cad;

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
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class CadCaixaInspecaoEsgoto extends CadEntity 
{
//Private
    private GeomPoint3d ptIns;   
    //
    private String tipoCI;
    private String subtipoCI; 
    private int numeroCI;
    private int proximaCI;
    private double diametro;
    private double profundidade;
    private double declividade;    
    
//Public

    public CadCaixaInspecaoEsgoto(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODESCAIXAINSPECAO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d ptCenter) {
		this.init(ptCenter.getX(), ptCenter.getY(), 0.0, DrenagemCalc.DEF_ESGOTO_DIAMCI_60CM, DrenagemCalc.DEF_ESGOTO_PROFCI_100CM, DrenagemCalc.DEF_ESGOTO_DECLIVIDADEMINCI);
	}
	
	private void init(GeomPoint3d ptCenter) {
		this.init(ptCenter.getX(), ptCenter.getY(), ptCenter.getZ(), DrenagemCalc.DEF_ESGOTO_DIAMCI_60CM, DrenagemCalc.DEF_ESGOTO_PROFCI_100CM, DrenagemCalc.DEF_ESGOTO_DECLIVIDADEMINCI);
	}

	public void init(
		double xCenter, 
		double yCenter, 
		double zCenter,
		//
		double diam, 
		double prof, 
		double declividade) 
	{
		this.ptIns = new GeomPoint3d(xCenter, yCenter, zCenter);
		//
	    this.tipoCI = AppDefs.DEF_TIPOCI_ESGOTO;
	    this.subtipoCI = AppDefs.DEF_SUBTIPOCI_ESGOTO_PRIMARIO; 
		this.numeroCI = this.getObjectId();
		this.proximaCI = AppDefs.NULL_INT;
		this.diametro = diam;
		this.profundidade = prof;
		this.declividade = declividade;
    }
	
	public void init(
		double xCenter, 
		double yCenter, 
		double zCenter,
		//
	    String tipoCI,
	    String subtipoCI, 
		int numeroCI,
		int proximaCI,
		double diam, 
		double prof, 
		double declividade) 
	{
		this.ptIns = new GeomPoint3d(xCenter, yCenter, zCenter);
		//
	    this.tipoCI = tipoCI;
	    this.subtipoCI = subtipoCI; 
		this.numeroCI = numeroCI;
		this.proximaCI = proximaCI;
		this.diametro = diam;
		this.profundidade = prof;
		this.declividade = declividade;
    }
	
	@Override
	public void init(ICadObject o) {
		CadCaixaInspecaoEsgoto other = (CadCaixaInspecaoEsgoto)o;

		GeomPoint3d ptTmpIns = other.ptIns;
		//
		double tmpDiam = other.diametro;
		double tmpProf = other.profundidade;
		double tmpDeclividade = other.declividade;
		
		this.init(ptTmpIns.getX(), ptTmpIns.getY(), ptTmpIns.getZ(), tmpDiam, tmpProf, tmpDeclividade);
	}
	
	/* CREATE */
	
	public static CadCaixaInspecaoEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d ptIns) {
    	CadCaixaInspecaoEsgoto o = new CadCaixaInspecaoEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns);
    	return o;
    }
	
	public static CadCaixaInspecaoEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d ptIns) {
    	CadCaixaInspecaoEsgoto o = new CadCaixaInspecaoEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns);
    	return o;
    }

	public static CadCaixaInspecaoEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter) {
    	CadCaixaInspecaoEsgoto o = new CadCaixaInspecaoEsgoto(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(xCenter, yCenter, zCenter, DrenagemCalc.DEF_ESGOTO_DIAMCI_60CM, DrenagemCalc.DEF_ESGOTO_PROFCI_100CM, DrenagemCalc.DEF_ESGOTO_DECLIVIDADEMINCI);
    	return o;
    }

	public static CadCaixaInspecaoEsgoto create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double xCenter, double yCenter, double zCenter, boolean bLocked) {
    	CadCaixaInspecaoEsgoto o = new CadCaixaInspecaoEsgoto(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(xCenter, yCenter, zCenter, DrenagemCalc.DEF_ESGOTO_DIAMCI_60CM, DrenagemCalc.DEF_ESGOTO_PROFCI_100CM, DrenagemCalc.DEF_ESGOTO_DECLIVIDADEMINCI);
    	return o;
    }
	
	public static CadCaixaInspecaoEsgoto create(CadCaixaInspecaoEsgoto other) {
    	CadCaixaInspecaoEsgoto o = new CadCaixaInspecaoEsgoto(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadCaixaInspecaoEsgoto create(CadBlockDef blkDef, CadCaixaInspecaoEsgoto other) {
    	CadCaixaInspecaoEsgoto o = new CadCaixaInspecaoEsgoto(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadCaixaInspecaoEsgoto duplicate()
	{
		CadCaixaInspecaoEsgoto other = CadCaixaInspecaoEsgoto.create(this);
		return other;
	}	
	
	@Override
	public CadCaixaInspecaoEsgoto duplicate(CadBlockDef blkDef)
	{
		CadCaixaInspecaoEsgoto other = CadCaixaInspecaoEsgoto.create(blkDef, this);
		return other;
	}	
	
	@Override
	public CadCaixaInspecaoEsgoto copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadCaixaInspecaoEsgoto other = CadCaixaInspecaoEsgoto.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadCaixaInspecaoEsgoto moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptInsOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptInsOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}

    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadCaixaInspecaoEsgoto scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptInsOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptInsOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
        //this.diametro = this.diametro * o.getScale();			;; diametro da CI depende da profundidade (nao_aplicavel)
		return this;
	}
	
	@Override
	public CadCaixaInspecaoEsgoto offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadCaixaInspecaoEsgoto o = copyTo(ptIMcs, ptFMcs);
		return o;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ItemDataVO oItem_tipoCI = ListUtil.findItemDataById(this.tipoCI, AppDefs.ARR_TIPOCI);
		ItemDataVO oItem_subtipoCI = ListUtil.findItemDataById(this.subtipoCI, AppDefs.ARR_SUBTIPOCI);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		//
		lsProperty.add( new ItemDataVO("Tipo de CI", oItem_tipoCI.getDescricao(), false) );
		lsProperty.add( new ItemDataVO("Subtipo de CI", oItem_subtipoCI.getDescricao(), false) );
		//
		lsProperty.add( new ItemDataVO("Numero CI", Integer.toString(this.numeroCI), false) );
		lsProperty.add( new ItemDataVO("Proxima CI", Integer.toString(this.proximaCI), false) );
		//
		lsProperty.add( new ItemDataVO("Diametro (m)", nf3.format(this.diametro), true) );		
		lsProperty.add( new ItemDataVO("Profundidade (m)", nf3.format(this.profundidade), true) );		
		lsProperty.add( new ItemDataVO("Declividade (m/m)", nf3.format(this.declividade), true) );		
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(X:%s;Y:%s;Z:%s);Tipo:%s;Subtipo:%s;NumeroCI:%s;ProximaCI:%s;Diametro:%s;Profundidade:%s;Declividade:%s;", 
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()),
			nf6.format(this.tipoCI),
		    nf6.format(this.subtipoCI),
		    nf6.format(this.numeroCI),
		    nf6.format(this.proximaCI),
		    nf6.format(this.diametro),
		    nf6.format(this.profundidade),
			nf6.format(this.declividade) );
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

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptInsDest2dMcs = new GeomPoint2d(this.ptIns);
        double radius = this.diametro / 2.0;
        
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
		        	CadCaixaInspecaoEsgoto other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadCaixaInspecaoEsgoto other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }        
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadCaixaInspecaoEsgoto other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
			            radius = other.diametro / 2.0;
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadCaixaInspecaoEsgoto other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptInsDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }
        
    	DrawUtil.drawCircleMcs(v, ptInsDest2dMcs, radius, g);
        
        if(bSelected || bHover) {
            GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);
        	DrawUtil.drawPointMcs(v, ptIns2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
        }

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

    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptIns2dMcs = new GeomPoint2d(this.ptIns);

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double radius = this.diametro / 2.0;
        
        double distMin = radius - (boxSz / 2.0);
        double distMax = radius + (boxSz / 2.0);
        
        double dist = ptIns2dMcs.distTo(pt2dMcs); 

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

    	GeomPoint2d ptIns = new GeomPoint2d(this.ptIns);    	
        double radius = this.diametro / 2.0;
        
        double zp = this.ptIns.getZ();
        
    	GeomVector2d vAxisX = new GeomVector2d(radius, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptIns, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add(new GeomPoint3d(this.ptIns));

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

    	GeomPoint2d ptIns = new GeomPoint2d(this.ptIns);    	
        double radius = this.diametro / 2.0;
        
        double zp = this.ptIns.getZ();
        
    	GeomVector2d vAxisX = new GeomVector2d(radius, 0.0);

    	GeomVector2d vDir = new GeomVector2d(ptIns, vAxisX);
    	
    	//CENTER
    	//
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>(); 
    	lsPtCenter.add(new GeomPoint3d(AppDefs.OSNAPMODE_CENTER, this.ptIns));

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
		GeomPoint3d ptResult = new GeomPoint3d(this.ptIns);
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
		return false;
	}
	
	/* UTILITIES */

	@Override
	public GeomPoint3d nearestConexao(GeomPoint3d ptRef) {
		GeomPoint2d ptRef2d = new GeomPoint2d( ptRef );

		GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptIns );
		
		GeomVector2d vDir = new GeomVector2d(ptCenter2d, ptRef2d);
		GeomVector2d uDir = vDir.otherUnit(); 
		
		double dRaioExterno = this.diametro / 2.0;
		GeomPoint2d ptResult2d = ptCenter2d.otherMoveTo(uDir, dRaioExterno);
		
		GeomPoint3d ptResult = new GeomPoint3d( ptResult2d );
		return ptResult;
	}

	@Override
	public GeomPoint3d nearestConexaoEntrada(GeomPoint3d ptRef) {
		GeomPoint3d ptResult = this.nearestConexao(ptRef);
		return ptResult;
	}

	@Override
	public GeomPoint3d nearestConexaoSaida(GeomPoint3d ptRef) {
		GeomPoint3d ptResult = this.nearestConexao(ptRef);
		return ptResult;
	}

	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		double xPtIns = this.ptIns.getX();
		double yPtIns = this.ptIns.getY();
		double zPtIns = this.ptIns.getZ();

        double radius = this.diametro / 2.0;

        double xPtMin = xPtIns - radius;
		double yPtMin = yPtIns - radius;
		double zPtMin = zPtIns - radius;
		
		double xPtMax = xPtIns + radius;
		double yPtMax = yPtIns + radius;
		double zPtMax = zPtIns + radius;
		
		GeomPoint3d ptMin3d = new GeomPoint3d(xPtMin, yPtMin, zPtMin);
		GeomPoint3d ptMax3d = new GeomPoint3d(xPtMax, yPtMax, zPtMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		double xPtIns = this.ptIns.getX();
		double yPtIns = this.ptIns.getY();

        double radius = this.diametro / 2.0;

        double xPtMin = xPtIns - radius;
		double yPtMin = yPtIns - radius;
		
		double xPtMax = xPtIns + radius;
		double yPtMax = yPtIns + radius;
		
		GeomPoint2d ptMin2d = new GeomPoint2d(xPtMin, yPtMin);
		GeomPoint2d ptMax2d = new GeomPoint2d(xPtMax, yPtMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"CI=" + Integer.toString( this.numeroCI ) +
			"CI=" + ((this.proximaCI != AppDefs.NULL_INT) ? Integer.toString( this.proximaCI ) : "FOLHA") +
			"PROFUNDIDADE=" + Double.toString( this.profundidade ) +
			"DECLIVIDADE=" + Double.toString( this.declividade ) +
			"RAIO=" + Double.toString( this.diametro / 2.0 ) +
			"DIAMETRO=" + Double.toString( this.diametro );
		return searchString;
	}

	public GeomPoint3d getPtIns() {
        return this.ptIns;
    }

    public double getDiametro() {
        return this.diametro;
    }

	public String getTipoCI() {
		return tipoCI;
	}

	public String getSubtipoCI() {
		return subtipoCI;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public int getProximaCI() {
		return proximaCI;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public double getDeclividade() {
		return declividade;
	}

}
