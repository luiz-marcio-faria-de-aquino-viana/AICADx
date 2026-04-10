/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadInsertBlock.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/04/2025
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

import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadInsertBlockRecord;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadInsertBlock extends CadEntity
{
//Private
	private String blockName;
    private GeomPoint3d ptIns;
    private double scaleX;
    private double scaleY;
    private double scaleZ;
	
//Public

    public CadInsertBlock(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_INSERTBLOCK, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(String blockName, GeomPoint2d pt, double scale) {
		this.init(blockName, pt.getX(), pt.getY(), 0.0, scale);
	}
	
	private void init(String blockName, GeomPoint2d pt, double scaleX, double scaleY, double scaleZ) {
		this.init(blockName, pt.getX(), pt.getY(), 0.0, scaleX, scaleY, scaleZ);
	}
	
	private void init(String blockName, GeomPoint3d pt, double scale) {
		this.init(blockName, pt.getX(), pt.getY(), pt.getZ(), scale);
	}
	
	private void init(String blockName, GeomPoint3d pt, double scaleX, double scaleY, double scaleZ) {
		this.init(blockName, pt.getX(), pt.getY(), pt.getZ(), scaleX, scaleY, scaleZ);
	}

	public void init(String blockName, double x, double y, double z, double scale) {
    	this.init(blockName, x, y, z, scale, scale, scale);
    }

	public void init(String blockName, double x, double y, double z, double scaleX, double scaleY, double scaleZ) {
		this.blockName = blockName;
    	this.ptIns = new GeomPoint3d(x, y, z);
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.scaleZ = scaleZ;

    	this.createAllDrawCache();
	}
	
	@Override
	public void init(ICadObject other) {
		CadInsertBlock oEnt = (CadInsertBlock)other;
		this.init(oEnt.blockName, oEnt.ptIns, oEnt.scaleX, oEnt.scaleY, oEnt.scaleZ);
	}

	/* CREATE */
		
	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, GeomPoint2d pt, double scale) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(blockName, pt, scale);
    	return o;
    }
	
	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, GeomPoint2d pt, double scaleX, double scaleY, double scaleZ) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, false);
		o.init(blockName, pt, scaleX, scaleY, scaleZ);
		return o;
	}
	
	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, GeomPoint3d pt, double scale) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(blockName, pt, scale);
    	return o;
    }
	
	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, GeomPoint3d pt, double scaleX, double scaleY, double scaleZ) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(blockName, pt, scaleX, scaleY, scaleZ);
    	return o;
    }

	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, double x, double y, double z, double scale) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(blockName, x, y, z, scale);
    	return o;
    }

	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, double x, double y, double z, double scaleX, double scaleY, double scaleZ) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(blockName, x, y, z, scaleX, scaleY, scaleZ);
    	return o;
    }

	public static CadInsertBlock create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String blockName, double x, double y, double z, double scaleX, double scaleY, double scaleZ, boolean bLocked) {
		CadInsertBlock o = new CadInsertBlock(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(blockName, x, y, z, scaleX, scaleY, scaleZ);
    	return o;
    }
	
	public static CadInsertBlock create(CadInsertBlock other) {
		CadInsertBlock o = new CadInsertBlock(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadInsertBlock create(CadBlockDef blkDef, CadInsertBlock other) {
		CadInsertBlock o = new CadInsertBlock(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadInsertBlock duplicate()
	{
		CadInsertBlock other = CadInsertBlock.create(this);
		return other;
	}
	
	@Override
	public CadInsertBlock duplicate(CadBlockDef blkDef)
	{
		CadInsertBlock other = CadInsertBlock.create(blkDef, this);
		return other;
	}

	@Override
	public CadInsertBlock copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadInsertBlock other = CadInsertBlock.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadInsertBlock moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	@Override
	public CadInsertBlock scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
    	
    	this.scaleX = this.scaleX * o.getScale();
    	this.scaleY = this.scaleY * o.getScale();
    	this.scaleZ = this.scaleZ * o.getScale();
    	
		return this;
	}
    
	@Override
	public CadInsertBlock mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadInsertBlock offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadInsertBlock oPoint = copyTo(ptIMcs, ptFMcs);
		return oPoint;
	}
	
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.", true) );
		//
		lsProperty.add( new ItemDataVO("Block Name", blockName, false) );
		//
		lsProperty.add( new ItemDataVO("Scale X", nf3.format(this.scaleX), true) );
		lsProperty.add( new ItemDataVO("Scale Y", nf3.format(this.scaleY), true) );
		lsProperty.add( new ItemDataVO("Scale Z", nf3.format(this.scaleZ), true) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String strLayerName = this.getLayer().getName();
		
		String str = String.format(
			"ObjectId:%s;ObjType:%s;Layer:%s;BlockName:%s;[X:%s;Y:%s;Z:%s];ScaleX:%s;ScaleY:%s;ScaleZ:%s; ", 
			this.getObjectId(),
			this.getObjType(),
			strLayerName,
			this.blockName,
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()),
			nf6.format(this.scaleX),
			nf6.format(this.scaleY),
			nf6.format(this.scaleZ) );
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
	public DrawCache createDrawCache2d()
	{
		DrawCache cache = new DrawCache();
		
    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, this.blockName);
    	if(oBlockDef != null) {
    		GeomPoint3d ptBase3d = new GeomPoint3d(0.0, 0.0, 0.0);
    		GeomPoint3d ptRef3d = new GeomPoint3d(this.ptIns);

    		int size = oBlockDef.getEntityTableSz();
	        for(int i = 0; i < size; i++) {
	        	CadEntity oEnt = oBlockDef.getEntityAt(i);
	        	if( oEnt.isDeleted() ) continue;
	        	if( !oEnt.isVisible() ) continue; 
	
				cache.addDrawCache(ptBase3d, ptRef3d, oEnt.getDrawCache2d());
	        }
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
		DrawCache cache = new DrawCache();

    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, this.blockName);
    	if(oBlockDef != null) {
    		GeomPoint3d ptBase3d = new GeomPoint3d(0.0, 0.0, 0.0);
    		GeomPoint3d ptRef3d = new GeomPoint3d(this.ptIns);

    		int size = oBlockDef.getEntityTableSz();
	        for(int i = 0; i < size; i++) {
	        	CadEntity oEnt = oBlockDef.getEntityAt(i);
	        	if( oEnt.isDeleted() ) continue;
	        	if( !oEnt.isVisible() ) continue; 
	
				cache.addDrawCache(ptBase3d, ptRef3d, oEnt.getOsnapCache3d());
	        }
		}    	
		return cache;
	}
	
    /* DRAWING */

	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
		//TODO:
	}
    
	/* SELECT */

	public boolean select2d_202511091026(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;

		if( this.isDeleted() ) return false;
    	
		if(pt2dMcs == null) return false;

		if( this.isSelected() ) return true;		

    	boolean bResult = false;
    	
		bResult = this.getOsnapCache3d().select2d(view2d, pt2dMcs, sclFact, bSelectEntity);
    	return bResult;
	}
	
	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;    	

		if(pt2dMcs == null) return false;

    	if(this.isSelected()) return true;
		
		DrawCache cache = this.getDrawCache2d();

		boolean bResult = cache.select2d(view2d, pt2dMcs, sclFact, bSelectEntity);
    	return bResult;
	}

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity)
	{
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

    	GeomPoint3d ptResult = null;
    	
    	//INSERT_POINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(this.ptIns);

    	//BLOCK_MULTIPLE_POINTS
    	//
    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, blockName);
    	if(oBlockDef != null) {
	    	int size = oBlockDef.getEntityTableSz();
	        for(int i = 0; i < size; i++) {
	        	CadEntity oEnt = oBlockDef.getEntityAt(i);
	        	if( !oEnt.isVisible() ) continue; 

				ptResult = oEnt.osnap3d(view2d, osnapmode, pt2dMcs, g);
		    	if(ptResult != null) {
					ptResult.debug(AppDefs.DEBUG_LEVEL11);				
		    		return ptResult;
		    	}
	        }    	
    	}
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) {
			ptResult.debug(AppDefs.DEBUG_LEVEL11);				
    		return ptResult;
    	}
    	
    	return ptResult;
	}
	
	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs)
	{
		if( this.isLocked() ) return null;
				
    	if( !this.isVisible() ) return null;

		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();

    	//INSERT_POINT
    	//
    	GeomPoint3d pt0 = new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.ptIns);
    	lsResult.add(pt0);
    	
    	//BLOCK_MULTIPLE_POINTS
    	//
    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, blockName);
    	if(oBlockDef != null) {
	    	int size = oBlockDef.getEntityTableSz();
	        for(int i = 0; i < size; i++) {
	        	CadEntity oEnt = oBlockDef.getEntityAt(i);
	        	if( !oEnt.isVisible() ) continue; 

	        	ArrayList<GeomPoint3d> lsPts = oEnt.osnap3d(view2d, osnapmode, pt2dMcs);
	        	lsResult.addAll(lsPts);
	        }    	
    	}
    	
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
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new String( this.blockName ),
			//
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
 			new Double( this.scaleX ),
 			new Double( this.scaleY ),
 			new Double( this.scaleZ )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadInsertBlockRecord entRec = new CadInsertBlockRecord(this); 
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
		GeomDimension3d oResDim3d = new GeomDimension3d(this.ptIns, this.ptIns);

    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, blockName);
    	if(oBlockDef != null) {
    		GeomDimension3d oDim3d = oBlockDef.getEnvelop3d(AppDefs.OBJTYPE_ALL);

    		GeomPoint3d ptMin = new GeomPoint3d(oDim3d.getPtMin());
    		GeomPoint3d ptMax = new GeomPoint3d(oDim3d.getPtMax());

    		double minX = this.ptIns.getX() + ptMin.getX();
    		double minY = this.ptIns.getY() + ptMin.getY();
    		double minZ = this.ptIns.getZ() + ptMin.getZ();
    		
    		double maxX = this.ptIns.getX() + ptMax.getX();
    		double maxY = this.ptIns.getY() + ptMax.getY();
    		double maxZ = this.ptIns.getZ() + ptMax.getZ();
    		
    		GeomPoint3d ptResMin3d = new GeomPoint3d(minX, minY, minZ);    		
    		GeomPoint3d ptResMax3d = new GeomPoint3d(maxX, maxY, maxZ);    		
    		
    		oResDim3d = new GeomDimension3d(ptResMin3d, ptResMax3d);
    	}
    	
		return oResDim3d;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oResDim2d = new GeomDimension2d(this.ptIns, this.ptIns);

    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, this.blockName);
    	if(oBlockDef != null) {
    		GeomDimension2d oDim2d = oBlockDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);

    		GeomPoint2d ptMin = new GeomPoint2d(oDim2d.getPtMin());
    		GeomPoint2d ptMax = new GeomPoint2d(oDim2d.getPtMax());

    		double minX = this.ptIns.getX() + ptMin.getX();
    		double minY = this.ptIns.getY() + ptMin.getY();
    		
    		double maxX = this.ptIns.getX() + ptMax.getX();
    		double maxY = this.ptIns.getY() + ptMax.getY();
    		
    		GeomPoint2d ptResMin2d = new GeomPoint2d(minX, minY);    		
    		GeomPoint2d ptResMax2d = new GeomPoint2d(maxX, maxY);    		
    		
    		oResDim2d = new GeomDimension2d(ptResMin2d, ptResMax2d);
    	}
    	
		return oResDim2d;
	}

	public GeomDimension2d getEnvelop2d_20251102() {
		GeomDimension2d oResDim2d = new GeomDimension2d(this.ptIns, this.ptIns);

    	CadBlockDef oBlockDef = CadUtil.selectBlockByBlockName(null, blockName);
    	if(oBlockDef != null) {
    		GeomDimension2d oDim2d = oBlockDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);

    		GeomPoint2d ptMin = new GeomPoint2d(oDim2d.getPtMin());
    		GeomPoint2d ptMax = new GeomPoint2d(oDim2d.getPtMax());

    		double minX = this.ptIns.getX() + ptMin.getX();
    		double minY = this.ptIns.getY() + ptMin.getY();
    		
    		double maxX = this.ptIns.getX() + ptMax.getX();
    		double maxY = this.ptIns.getY() + ptMax.getY();
    		
    		GeomPoint2d ptResMin2d = new GeomPoint2d(minX, minY);    		
    		GeomPoint2d ptResMax2d = new GeomPoint2d(maxX, maxY);    		
    		
    		oResDim2d = new GeomDimension2d(ptResMin2d, ptResMax2d);
    	}
    	
		return oResDim2d;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" + 
			"NOME=" + this.blockName;
		return searchString;
	}

    public String getBlockName() {
		return blockName;
	}

	public GeomPoint3d getPtIns() {
        return this.ptIns;
    }

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(double scaleZ) {
		this.scaleZ = scaleZ;
	}

}
