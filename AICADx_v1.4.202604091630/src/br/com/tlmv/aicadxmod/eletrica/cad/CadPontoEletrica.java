/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPontosEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
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

package br.com.tlmv.aicadxmod.eletrica.cad;

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
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadParamEletricoODataRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadPontoEletricaRecord;

public class CadPontoEletrica extends CadEntity
{
//Private
    private GeomPoint3d ptIns = null;
    private double rotate = 0.0;
    private Shape shape = null;
    //
    private ArrayList<CadParamEletricoOData> lsParamEletrico = null; 
    
//Public

    public CadPontoEletrica(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODELINSEREPONTO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d pt, double rotate, Shape shape) {
		this.init(pt.getX(), pt.getY(), 0.0, rotate, shape);
	}
	
	private void init(GeomPoint3d pt, double rotate, Shape shape) {
		this.init(pt.getX(), pt.getY(), pt.getZ(), rotate, shape);
	}

	public void init(double x, double y, double z, double rotate, Shape shape) {
		int objectId = this.getObjectId();
		
		String cadRefEntityId = Integer.toString(objectId);
		
		this.ptIns = new GeomPoint3d(x, y, z);
    	this.rotate = rotate;
    	this.shape = new Shape(shape);
    	
    	ArrayList<CadParamEletricoOData> lsSrcParam = shape.getLsParamEletrico();
    	int szLsSrcParam = lsSrcParam.size();
    	
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();
    	for(int i = 0; i < szLsSrcParam; i++) {
    		CadParamEletricoOData oParamEletrico = new CadParamEletricoOData( lsSrcParam.get(i) );
    		oParamEletrico.setCadRefEntityId( cadRefEntityId );

    		this.lsParamEletrico.add(oParamEletrico);
    	}
    }
	
	@Override
	public void init(ICadObject o) {
		CadPontoEletrica other = (CadPontoEletrica)o;

		this.init(other.ptIns, other.rotate, other.shape);
	}

	/* CREATE */
		
	public static CadPontoEletrica create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d pt, double rotate, Shape shape) {
    	CadPontoEletrica o = new CadPontoEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pt, rotate, shape);
    	return o;
    }
	
	public static CadPontoEletrica create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d pt, double rotate, Shape shape) {
    	CadPontoEletrica o = new CadPontoEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pt, rotate, shape);
    	return o;
    }

	public static CadPontoEletrica create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double x, double y, double z, double rotate, Shape shape) {
    	CadPontoEletrica o = new CadPontoEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(x, y, z, rotate, shape);
    	return o;
    }

	public static CadPontoEletrica create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double x, double y, double z, double rotate, Shape shape, boolean bLocked) {
    	CadPontoEletrica o = new CadPontoEletrica(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(x, y, z, rotate, shape);
    	return o;
    }
	
	public static CadPontoEletrica create(CadPontoEletrica other) {
		CadPontoEletrica o = new CadPontoEletrica(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadPontoEletrica create(CadBlockDef blkDef, CadPontoEletrica other) {
		CadPontoEletrica o = new CadPontoEletrica(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPontoEletrica duplicate()
	{
		CadPontoEletrica other = CadPontoEletrica.create(this);
		return other;
	}
	
	@Override
	public CadPontoEletrica duplicate(CadBlockDef blkDef)
	{
		CadPontoEletrica other = CadPontoEletrica.create(blkDef, this);
		return other;
	}

	@Override
	public CadPontoEletrica copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPontoEletrica other = CadPontoEletrica.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadPontoEletrica moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	@Override
	public CadPontoEletrica scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}
    
	@Override
	public CadPontoEletrica mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadPontoEletrica offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPontoEletrica oPoint = copyTo(ptIMcs, ptFMcs);
		return oPoint;
	}
	
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String strName = this.shape.getName();
		String strFileName = this.shape.getFileName();
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		//
		lsProperty.add( new ItemDataVO("Rotate", nf3.format(this.rotate), true) );
		//
		lsProperty.add( new ItemDataVO("Name", strName, false) );
		lsProperty.add( new ItemDataVO("FileName", strFileName, false) );
		
		int szLsParamEletrico = this.lsParamEletrico.size();
		for(int i = 0; i < szLsParamEletrico; i++) {
			CadParamEletricoOData oParamEletrico = this.lsParamEletrico.get(i);
			lsProperty.addAll(oParamEletrico.toPropertyList());
		}
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String strLayerName = this.getLayer().getName();		
		String strName = this.shape.getName();
		String strFileName = this.shape.getFileName();
		
		String str = String.format(
			"ObjectId:%s;ObjType:%s;Layer:%s;Name:%s;FileName:%s;[X:%s;Y:%s;Z:%s];", 
			this.getObjectId(),
			this.getObjType(),
			strLayerName,
			strName,
			strFileName,
			nf6.format(this.ptIns.getX()), 
			nf6.format(this.ptIns.getY()), 
			nf6.format(this.ptIns.getZ()),
			nf6.format(this.rotate) );

		int szLsParamEletrico = this.lsParamEletrico.size();
		for(int i = 0; i < szLsParamEletrico; i++) {
			CadParamEletricoOData oParamEletrico = this.lsParamEletrico.get(i);
			str += oParamEletrico.toStr();
		}
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
	
    public void redraw2d_attrib(ICadViewBase v, GeomPoint2d ptInsMcs, double sclFact, double rotate, Graphics g)
    {
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	    	
    	int szLsParamEletrico = this.lsParamEletrico.size();
    	if(szLsParamEletrico > 0) 
    	{
    		MainPanel panel = (MainPanel)MainPanel.getMainPanel();
    		ICompView compView = panel.getCurrView();

    		int attrmode = compView.getAttrmode();

			double xIns = this.ptIns.getX();
			double yIns = this.ptIns.getY();

			double rotateRad = GeomUtil.convertDegreesToRad(this.rotate);

			double rotate0Rad   = rotateRad;
			double rotate45Rad  = rotateRad + AppDefs.MATHVAL_H4PI;
			double rotate90Rad  = rotateRad + (AppDefs.MATHVAL_H4PI * 2.0);
			double rotate135Rad = rotateRad + (AppDefs.MATHVAL_H4PI * 3.0);
			double rotate180Rad = rotateRad + (AppDefs.MATHVAL_H4PI * 4.0);
			double rotate225Rad = rotateRad + (AppDefs.MATHVAL_H4PI * 5.0);
			double rotate270Rad = rotateRad + (AppDefs.MATHVAL_H4PI * 6.0);
			double rotate315Rad = rotateRad + (AppDefs.MATHVAL_H4PI * 7.0);
			
			double textSzMcs = AppDefs.FONTSZ_SMALL * sclFact;
			
			double lineHeightMcs = textSzMcs * 2.5;
			double d = lineHeightMcs;
			
			for(CadParamEletricoOData oParam : this.lsParamEletrico) 
			{
				//PARAM: QUADRO_ORIGEM
	    		if(attrmode == AppDefs.ATTRMODE_ON) {
		    		if( !StringUtil.isEmpty(oParam.getQuadroOrigem()) ) {
		    			String strOrg = oParam.getQuadroOrigem();
		    			
		    			double newXIns = xIns + (d * Math.cos(rotate225Rad));
		    			double newYIns = yIns + (d * Math.sin(rotate225Rad));
		
		    			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
		    			DrawUtil.drawTextMcs(v, strOrg, newPtIns, textSzMcs, AppDefs.HORIZALIGN_RIGHT, AppDefs.VERTALIGN_MIDDLE, g);
		    		}
	    		}

	    		//PARAM: NOME_QUADRO
	    		if( !StringUtil.isEmpty(oParam.getNomeQuadro()) ) {
	    			String strQdr = oParam.getNomeQuadro();
	    			
	    			double newXIns = xIns + (d * Math.cos(rotate135Rad));
	    			double newYIns = yIns + (d * Math.sin(rotate135Rad));
	
	    			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
	    			DrawUtil.drawTextMcs(v, strQdr, newPtIns, textSzMcs, AppDefs.HORIZALIGN_RIGHT, AppDefs.VERTALIGN_MIDDLE, g);	    			
	    		}
	    		
				//PARAM: CIRCUITO
				if( !StringUtil.isEmpty(oParam.getCircuito()) ) {
	    			String strCir = oParam.getCircuito();
	    			
	    			double newXIns = xIns + (d * Math.cos(rotate45Rad));
	    			double newYIns = yIns + (d * Math.sin(rotate45Rad));
	
	    			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
	    			DrawUtil.drawTextMcs(v, strCir, newPtIns, textSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
	    		}
	    		
				//PARAM: COMANDO
	    		if( !StringUtil.isEmpty(oParam.getComando()) ) {
	    			String strCmd = oParam.getComando();
	    			
	    			double newXIns = xIns + (d * Math.cos(rotate315Rad));
	    			double newYIns = yIns + (d * Math.sin(rotate315Rad));
	
	    			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
	    			DrawUtil.drawTextMcs(v, strCmd, newPtIns, textSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
	    		}
	    		
				//PARAM: POTENCIA
	    		if(attrmode == AppDefs.ATTRMODE_ON) {
		    		if( oParam.getPotencia() > AppDefs.MATHPREC_MIN ) {
		    			String strPot = String.format( "%s VA", nf0.format( oParam.getPotencia() ) );
		    			
		    			double newXIns = xIns + (d * Math.cos(rotate180Rad));
		    			double newYIns = yIns + (d * Math.sin(rotate180Rad));
		
		    			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
		    			DrawUtil.drawTextMcs(v, strPot, newPtIns, textSzMcs, AppDefs.HORIZALIGN_RIGHT, AppDefs.VERTALIGN_MIDDLE, g);
		    		}
	    		}
	    		else {
		    		if( oParam.getPotencia() > AppDefs.DEF_DEFAULT_POTENCIA_MINIMA_VISIVEL ) {
		    			String strPot = String.format( "%s VA", nf0.format( oParam.getPotencia() ) );
		    			
		    			double newXIns = xIns + (d * Math.cos(rotate180Rad));
		    			double newYIns = yIns + (d * Math.sin(rotate180Rad));
		
		    			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
		    			DrawUtil.drawTextMcs(v, strPot, newPtIns, textSzMcs, AppDefs.HORIZALIGN_RIGHT, AppDefs.VERTALIGN_MIDDLE, g);
		    		}	    			
	    		}
	    	}
    	}
    }

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
		        	CadPontoEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPontoEletrica other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadPontoEletrica other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPontoEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }
        
    	DrawUtil.drawShape2dMcs(v, ptDest2dMcs, this.shape.getPlanView2d(), sclFact, this.rotate, g);

    	this.redraw2d_attrib(v, ptDest2dMcs, sclFact, this.rotate, g);
    	
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
	
	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
    	if( !this.isVisible() ) return;
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

    	GeomVector3d axisZ = GeomUtil.axisZ3d();
    	
    	GeomShape3d shape3d = this.shape.getModelView3d();
    	
    	double rotateRad = GeomUtil.convertDegreesToRad(this.rotate);
    	
        prep.addShape3dMcs(v, this, c, this.ptIns, shape3d, sclFact, rotateRad, axisZ);
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
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
    	lsPtNodepoint.add(new GeomPoint3d(this.ptIns));
		
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
    	lsPtNodepoint.add(new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.ptIns));
    	return lsPtNodepoint;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptIns);
		return ptResult;
	}
	
	/* LIST */
	
	public synchronized void loadAllItens(ArrayList<BaseObjectRecord> lsItens)
	{
		this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();

		CadBlockDef blkDef = this.getBlkDef();
		
		int objectId = this.getObjectId();

		CadPontoEletrica oPontoEletrica = (CadPontoEletrica)blkDef.getEntity( objectId ); 
		if(oPontoEletrica != null) {
			String objVer = this.getObjVer();
			String cadRefEntityId = Integer.toString(objectId);
			String strIsDeleted = StringUtil.fromBoolToStr( oPontoEletrica.isDeleted() ); 
			
			for(BaseObjectRecord obj : lsItens) {
				CadParamEletricoODataRecord oRec = (CadParamEletricoODataRecord)obj;
		
				CadParamEletricoOData oParamEletrico = new CadParamEletricoOData( this.getDocument() );
				oParamEletrico.setObjVer(objVer);
				oParamEletrico.setCadRefEntityId(cadRefEntityId);
				
				oParamEletrico.init(
		        	oRec.getParmNum(),
		        	oRec.getTipo(),
		        	oRec.getNomeQuadro(),
		        	oRec.getQuadroOrigem(),
		        	oRec.getNomeCalha(),
		        	oRec.getDesvio(),
		        	oRec.getPotencia(),
		        	oRec.getPotenciaDemandada(),
		        	oRec.getSistema(),
		        	oRec.getCircuito(),
		        	oRec.getComando(),
			    	strIsDeleted );
				oPontoEletrica.addParamEletrico(oParamEletrico);
			}
		}
	}
		
	public synchronized int getSzLsParamEletrico() {
		int sz = this.lsParamEletrico.size();
		return sz;
	}

	public synchronized CadParamEletricoOData getParamEletricoAt(int pos) {
		int sz = this.lsParamEletrico.size();
		if(pos < sz) {
			CadParamEletricoOData o = this.lsParamEletrico.get(pos);
			return o;
		}
		return null;
	}

	public synchronized void addParamEletrico(CadParamEletricoOData o) {
		this.lsParamEletrico.add(o);
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lsdata(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odDao = dao.createODataDao(AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA); 

		String strCadRefEntityId = Integer.toString(this.getObjectId());
		
		int szLsPts = this.lsParamEletrico.size();
		for(int i = 0; i < szLsPts; i++) {
			CadParamEletricoOData oItem = (CadParamEletricoOData)this.lsParamEletrico.get(i);
			oItem.setCadRefEntityId(strCadRefEntityId);
			oItem.setObjVer(objVer);

			Object[] arrVal = {
				new Integer( oItem.getParmNum() ),
				new String( oItem.getTipo() ),
				new String( oItem.getNomeQuadro() ),
				new String( oItem.getQuadroOrigem() ),
				new String( oItem.getNomeCalha() ),
				new String( oItem.getDesvio() ),
				new Double( oItem.getPotencia() ),
				new Double( oItem.getPotenciaDemandada() ),
				new String( oItem.getSistema() ),
				new String( oItem.getCircuito() ),
				new String( oItem.getComando() ) 
			};
					
			CadParamEletricoODataRecord odataRec = new CadParamEletricoODataRecord(oItem);
			int rscode = odDao.insertOrUpdate(
				objVer,
				schemaName,
				odataRec, 
				arrVal );
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
	    String shapeName = shape.getName();
	    String shapeFileName = shape.getFileName();
	    double shapeDefaultZ = shape.getDefaultZ();

		Object[] arrVal = {
			new String( shapeName ),
			new String( shapeFileName ),
			new Double( shapeDefaultZ ),
			//
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
			new Double( this.rotate )
 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadPontoEletricaRecord entRec = new CadPontoEletricaRecord(this); 
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
		
		bResult = this.save_lsdata(objVer, dao, schemaName, doc);
		if( !bResult ) return false;

		return bResult;
	}

	/* Getters/Setters */

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomDimension2d oDim = new GeomDimension2d(this.ptIns, this.ptIns); 
		return oDim;
	}

	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomDimension3d oDim = new GeomDimension3d(this.ptIns, this.ptIns); 
		return oDim;
	}

	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"NOME=" + this.shape.getName();
		return searchString;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public ArrayList<CadParamEletricoOData> getLsParamEletrico() {
		return lsParamEletrico;
	}

	public void setLsParamEletrico(ArrayList<CadParamEletricoOData> lsParamEletrico) {
		this.lsParamEletrico = lsParamEletrico;
	}

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

}
