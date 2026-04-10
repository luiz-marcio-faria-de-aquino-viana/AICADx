/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadInsertImage.java
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
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadInsertImageRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadInsertImage extends CadEntity
{
//Private
	private String imageName;
    private GeomPoint3d ptIns;
    private double scaleX;
    private double scaleY;
    private double scaleZ;
    private double rotate;
	
//Public

    public CadInsertImage(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_INSERTIMAGE, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(String imageName, GeomPoint2d pt, double rotate, double scale) {
		this.init(imageName, pt.getX(), pt.getY(), 0.0, rotate, scale);
	}
	
	private void init(String imageName, GeomPoint2d pt, double rotate, double scaleX, double scaleY, double scaleZ) {
		this.init(imageName, pt.getX(), pt.getY(), 0.0, rotate, scaleX, scaleY, scaleZ);
	}
	
	private void init(String imageName, GeomPoint3d pt, double rotate, double scale) {
		this.init(imageName, pt.getX(), pt.getY(), pt.getZ(), rotate, scale);
	}
	
	public void init(String imageName, double x, double y, double z, double rotate, double scale) {
		this.init(
			imageName,
			x, 
			y, 
			z,
			rotate,
			scale,
			scale,
			scale );
    }

	private void init(String imageName, GeomPoint3d pt, double rotate, double scaleX, double scaleY, double scaleZ) {
		this.init(imageName, pt.getX(), pt.getY(), pt.getZ(), rotate, scaleX, scaleY, scaleZ);
	}

	public void init(String imageName, double x, double y, double z, double rotate, double scaleX, double scaleY, double scaleZ) {
		this.imageName = imageName;
    	this.ptIns = new GeomPoint3d(x, y, z);
    	this.rotate = rotate;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.scaleZ = scaleZ;
    }

	@Override
	public void init(ICadObject other) {
		CadInsertImage oEnt = (CadInsertImage)other;
		this.init(oEnt.imageName, oEnt.ptIns, oEnt.scaleX, oEnt.scaleY);
	}

	/* CREATE */
		
	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, GeomPoint2d pt, double rotate, double scale) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(imageName, pt, rotate, scale);
    	return o;
    }
	
	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, GeomPoint2d pt, double rotate, double scaleX, double scaleY, double scaleZ) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, false);
		o.init(imageName, pt, rotate, scaleX, scaleY, scaleZ);
		return o;
	}
	
	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, GeomPoint3d pt, double rotate, double scale) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(imageName, pt, rotate, scale);
    	return o;
    }
	
	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, GeomPoint3d pt, double rotate, double scaleX, double scaleY, double scaleZ) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(imageName, pt, rotate, scaleX, scaleY, scaleZ);
    	return o;
    }

	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, double x, double y, double z, double rotate, double scale) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(imageName, x, y, z, rotate, scale);
    	return o;
    }

	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, double x, double y, double z, double rotate, double scaleX, double scaleY, double scaleZ) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(imageName, x, y, z, rotate, scaleX, scaleY, scaleZ);
    	return o;
    }

	public static CadInsertImage create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String imageName, double x, double y, double z, double rotate, double scaleX, double scaleY, double scaleZ, boolean bLocked) {
		CadInsertImage o = new CadInsertImage(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(imageName, x, y, z, rotate, scaleX, scaleY, scaleZ);
    	return o;
    }
	
	public static CadInsertImage create(CadInsertImage other) {
		CadInsertImage o = new CadInsertImage(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadInsertImage create(CadBlockDef blkDef, CadInsertImage other) {
		CadInsertImage o = new CadInsertImage(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadInsertImage duplicate()
	{
		CadInsertImage other = CadInsertImage.create(this);
		return other;
	}
	
	@Override
	public CadInsertImage duplicate(CadBlockDef blkDef)
	{
		CadInsertImage other = CadInsertImage.create(blkDef, this);
		return other;
	}

	@Override
	public CadInsertImage copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadInsertImage other = CadInsertImage.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadInsertImage moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	@Override
	public CadInsertImage scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
    	
    	this.scaleX = this.scaleX * o.getScale();
    	this.scaleY = this.scaleY * o.getScale();
    	
		return this;
	}
    
	@Override
	public CadInsertImage mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadInsertImage offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadInsertImage oPoint = copyTo(ptIMcs, ptFMcs);
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
		lsProperty.add( new ItemDataVO("Image Name", imageName, false) );
		//
		lsProperty.add( new ItemDataVO("Scale X", nf3.format(this.scaleX), true) );
		lsProperty.add( new ItemDataVO("Scale Y", nf3.format(this.scaleY), true) );
		//
		lsProperty.add( new ItemDataVO("Rotate", nf3.format(this.rotate), true) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String strLayerName = this.getLayer().getName();
		
		String str = String.format(
			"ObjectId:%s;ObjType:%s;Layer:%s;ImageName:%s;[X:%s;Y:%s;Z:%s];ScaleX:%s;ScaleY:%s;Rotate:%s; ", 
			this.getObjectId(),
			this.getObjType(),
			strLayerName,
			this.imageName,
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()),
			nf6.format(this.scaleX),
			nf6.format(this.scaleY),
			nf6.format(this.rotate) );
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

    	CadImageDef oImageDef = CadUtil.selectImageByImageName(null, imageName);
    	if(oImageDef != null) {
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
	
	        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.ptIns);
	        
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
			        	CadInsertImage other = this.duplicate();
			        	other.moveTo(ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
			        }	        
			        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
			        {
			        	CadInsertImage other = this.duplicate();
			        	other.mirror(ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
			        }
			        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
			        {
			        	if(dist > AppDefs.MATHPREC_MIN) {
			        		CadInsertImage other = this.duplicate();
				        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
				        	
				        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
			        	}
			        }
			        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
			        {
			        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);
	
			        	CadInsertImage other = this.duplicate();
			        	other.moveTo(ptBase3dMcs, newPt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
			        }
		        }        
	        }
	        
	    	DrawUtil.drawImageMcs(v, oImageDef, ptDest2dMcs, (- this.rotate), this.scaleX, this.scaleY, g);
	        
	        if(bSelected || bHover) {
	            GeomPoint2d pt2d = new GeomPoint2d(this.ptIns);
	        	DrawUtil.drawPointMcs(v, pt2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
	        }

	        GeomUtil.setColor(g, oldcol);
	        
	        GeomUtil.setLtype(g, oldltype);
    	}
    }

	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
		//TODO:
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
    	if( !this.isVisible() ) return false;

		if( this.isLocked() ) return false;
		
    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptPoint2dMcs = new GeomPoint2d(this.ptIns);
        
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;
        
        double dist = ptPoint2dMcs.distTo(pt2dMcs); 
        if(dist <= distMax) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return this.isSelected();
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

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(this.ptIns);
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();
    	GeomPoint3d pt0 = new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.ptIns);
    	lsPtNodepoint.add(pt0);

    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtNodepoint);
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
			new String( this.imageName ),
			//
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
 			new Double( this.scaleX ),
 			new Double( this.scaleY ),
 			new Double( this.scaleZ ),
			//
 			new Double( this.rotate )
 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadInsertImageRecord entRec = new CadInsertImageRecord(this); 
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
		GeomDimension3d oDim = new GeomDimension3d(this.ptIns, this.ptIns); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oDim = new GeomDimension2d(this.ptIns, this.ptIns); 
		return oDim;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" + 
			"NOME=" + this.imageName;
		return searchString;
	}

    public String getImageName() {
		return imageName;
	}

	public GeomPoint3d getPtIns() {
        return this.ptIns;
    }

	public double getScaleX() {
		return scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public double getScaleZ() {
		return scaleZ;
	}

	public double getRotate() {
		return rotate;
	}

}