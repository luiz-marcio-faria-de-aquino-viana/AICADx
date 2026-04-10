/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadLigacaoCaixaInspecaoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/04/2025
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
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadLigacaoCaixaInspecaoDrenagemRecord;

public class CadLigacaoCaixaInspecaoDrenagem extends CadEntity 
{
//Private
    private CadCaixaInspecaoDrenagem entI;
    private CadCaixaInspecaoDrenagem entF;
	
//Public

    public CadLigacaoCaixaInspecaoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRLIGACAOCAIXAINSPECAO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
    
	public void init(CadCaixaInspecaoDrenagem entI, CadCaixaInspecaoDrenagem entF) {
		this.entI = entI;
		this.entF = entF;
	}

	public void init(int numeroCI, int proximaCI ) {
		CadBlockDef oBlkDef = this.getBlkDef();
		
		this.entI = (CadCaixaInspecaoDrenagem)oBlkDef.getEntity(numeroCI);
		this.entF = (CadCaixaInspecaoDrenagem)oBlkDef.getEntity(proximaCI);
	}
	
	@Override
	public void init(ICadObject o) {
		CadLigacaoCaixaInspecaoDrenagem other = (CadLigacaoCaixaInspecaoDrenagem)o; 

		CadCaixaInspecaoDrenagem entI = other.entI;
		CadCaixaInspecaoDrenagem entF = other.entF;
		
		this.init(entI, entF);
	}
	
	/* CREATE */
	
	public static CadLigacaoCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, CadCaixaInspecaoDrenagem entI, CadCaixaInspecaoDrenagem entF) {
    	CadLigacaoCaixaInspecaoDrenagem o = new CadLigacaoCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(entI, entF);
    	return o;
    }
	
	public static CadLigacaoCaixaInspecaoDrenagem create(CadLigacaoCaixaInspecaoDrenagem other) {
    	CadLigacaoCaixaInspecaoDrenagem o = new CadLigacaoCaixaInspecaoDrenagem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadLigacaoCaixaInspecaoDrenagem create(CadBlockDef blkDef, CadLigacaoCaixaInspecaoDrenagem other) {
    	CadLigacaoCaixaInspecaoDrenagem o = new CadLigacaoCaixaInspecaoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadLigacaoCaixaInspecaoDrenagem duplicate()
	{
		CadLigacaoCaixaInspecaoDrenagem other = CadLigacaoCaixaInspecaoDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadLigacaoCaixaInspecaoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadLigacaoCaixaInspecaoDrenagem other = CadLigacaoCaixaInspecaoDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadLigacaoCaixaInspecaoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return this;
	}

	@Override
	public CadLigacaoCaixaInspecaoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadLigacaoCaixaInspecaoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		return this;
	}
	
	@Override
	public CadLigacaoCaixaInspecaoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
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
        
        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
        
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
		        	CadLigacaoCaixaInspecaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		            
		            ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
		            ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadLigacaoCaixaInspecaoDrenagem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		            
		            ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
		            ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadLigacaoCaixaInspecaoDrenagem other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			            
			            ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
			            ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadLigacaoCaixaInspecaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		            
		            ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
		            ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
		        }
	        }
        }
        
        DrawUtil.drawLineMcs(v, ptDestI2dMcs, ptDestF2dMcs, g);

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
		
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.entI.getPtIns());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.entF.getPtIns());
        
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
    	lsPtEndpoint.add( new GeomPoint3d(this.entI.getPtIns()) );
    	lsPtEndpoint.add( new GeomPoint3d(this.entF.getPtIns()) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(this.entI.getPtIns(), this.entF.getPtIns());
    	
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
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.entI.getPtIns()) );
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.entF.getPtIns()) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, this.entI.getPtIns(), this.entF.getPtIns());
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtEndpoint);
    	lsResult.addAll(lsPtMiddle);
    	return lsResult;
	}

	/* CENTROID */	
	
	@Override
	public GeomPoint3d centroid()
	{
    	//ENDPOINT
    	//
        GeomPoint3d pt3dI = new GeomPoint3d(this.entI.getPtIns());
        GeomPoint3d pt3dF = new GeomPoint3d(this.entF.getPtIns());

        GeomPoint3d ptResult = GeomUtil.midPointOf(pt3dI, pt3dF);
		return ptResult;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.add( new ItemDataVO("Numero CI", Integer.toString(this.entI.getObjectId()), false) );
		lsProperty.add( new ItemDataVO("Proxima CI", Integer.toString(this.entF.getObjectId()), false) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"EntI:%s;EntF:%s;", 
			this.entI.getObjectId(), 
			this.entF.getObjectId() );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
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
		
		int numeroCI = entI.getObjectId();
		int proximaCI = entF.getObjectId();
		
		Object[] arrVal = {
			new Integer( numeroCI ),
			new Integer( proximaCI )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadLigacaoCaixaInspecaoDrenagemRecord entRec = new CadLigacaoCaixaInspecaoDrenagemRecord(this); 
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
    	//ENDPOINT
    	//
        GeomPoint3d ptI3dMcs = new GeomPoint3d(this.entI.getPtIns());
        GeomPoint3d ptF3dMcs = new GeomPoint3d(this.entF.getPtIns());

		GeomPoint3d[] arr = GeomUtil.maxMinPointOf(ptI3dMcs, ptF3dMcs);		
		
		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
    	//ENDPOINT
    	//
        GeomPoint2d ptI2dMcs = new GeomPoint2d(this.entI.getPtIns());
        GeomPoint2d ptF2dMcs = new GeomPoint2d(this.entF.getPtIns());

		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);		
		
		GeomPoint2d ptMin2d = new GeomPoint2d(arr[0]);
		GeomPoint2d ptMax2d = new GeomPoint2d(arr[1]);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"CI=" + Integer.toString( this.entI.getNumeroCI() ) + "^" +
			"CI=" + Integer.toString( this.entF.getNumeroCI() );
		return searchString;
	}

	public CadEntity getEntI() {
		return this.entI;
	}

	public CadEntity getEntF() {
		return this.entF;
	}

}
