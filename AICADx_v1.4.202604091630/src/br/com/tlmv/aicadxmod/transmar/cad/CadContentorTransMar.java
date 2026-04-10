/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadContentorTransMar.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/08/2025
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

package br.com.tlmv.aicadxmod.transmar.cad;

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
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadContentorTransMar extends CadEntity 
{
//Private
    private GeomPoint3d ptCenter;
    private double comprimento;
    private double largura;
    private double altura;
    private double rotacao;

    //BACKLIST
    private CadControleBacklistItemTransMarOData data;

    /* Methodes */
    
	public GeomPoint2d[] contentorExtents2d(GeomPoint2d ptCenter2d) {
		GeomPoint2d ptDir2d = new GeomPoint2d(
			ptCenter2d.getX() + 1,
			ptCenter2d.getY() );

		GeomVector2d vDirI = new GeomVector2d(ptCenter2d, ptDir2d);
		GeomVector2d vDirF = vDirI.otherRotateToDegrees(this.rotacao);
		
		GeomVector2d uDirF = vDirF.otherUnit();
		GeomVector2d nDirF = uDirF.otherNorm();

		double comprimento2 = this.comprimento / 2.0;
		double largura2     = this.largura / 2.0;

		GeomPoint2d ptRef = ptCenter2d.otherMoveTo(uDirF, - comprimento2); 

		GeomPoint2d pt0 = ptRef.otherMoveTo(nDirF, - largura2); 
		GeomPoint2d pt1 = pt0.otherMoveTo(uDirF, 	 this.comprimento); 
		GeomPoint2d pt2 = pt1.otherMoveTo(nDirF, 	 this.largura); 
		GeomPoint2d pt3 = pt2.otherMoveTo(uDirF, -   this.comprimento); 
		
		GeomPoint2d ptResult[] = { pt0, pt1, pt2, pt3 };
		return ptResult;
	}
    
	public GeomPoint3d[] contentorExtents3d(GeomPoint2d ptCenter2d) {
		GeomPoint2d ptDir2d = new GeomPoint2d(
			ptCenter2d.getX() + 1,
			ptCenter2d.getY() );

		GeomVector2d vDirI = new GeomVector2d(ptCenter2d, ptDir2d);
		GeomVector2d vDirF = vDirI.otherRotateToDegrees(this.rotacao);
		
		GeomVector2d uDirF = vDirF.otherUnit();
		GeomVector2d nDirF = uDirF.otherNorm();

		double comprimento2 = (this.comprimento / 2.0);
		double largura2     = (this.largura / 2.0);

		GeomPoint2d ptRef = ptCenter2d.otherMoveTo(uDirF, - comprimento2); 

		GeomPoint2d pt02d = ptRef.otherMoveTo(nDirF, - largura2); 
		GeomPoint2d pt12d = pt02d.otherMoveTo(uDirF,   this.comprimento); 
		GeomPoint2d pt22d = pt12d.otherMoveTo(nDirF,   this.largura); 
		GeomPoint2d pt32d = pt22d.otherMoveTo(uDirF, - this.comprimento); 
		
		GeomPoint3d pt03d = new GeomPoint3d(pt02d.getX(), pt02d.getY(), 0.0); 
		GeomPoint3d pt13d = new GeomPoint3d(pt12d.getX(), pt12d.getY(), 0.0); 
		GeomPoint3d pt23d = new GeomPoint3d(pt22d.getX(), pt22d.getY(), 0.0); 
		GeomPoint3d pt33d = new GeomPoint3d(pt32d.getX(), pt32d.getY(), 0.0);
		//
		GeomPoint3d pt43d = new GeomPoint3d(pt02d.getX(), pt02d.getY(), this.altura); 
		GeomPoint3d pt53d = new GeomPoint3d(pt12d.getX(), pt12d.getY(), this.altura); 
		GeomPoint3d pt63d = new GeomPoint3d(pt22d.getX(), pt22d.getY(), this.altura); 
		GeomPoint3d pt73d = new GeomPoint3d(pt32d.getX(), pt32d.getY(), this.altura); 
		
		GeomPoint3d ptResult[] = { pt03d, pt13d, pt23d, pt33d,
								   pt43d, pt53d, pt63d, pt73d };
		return ptResult;
	}
    
//Public

    public CadContentorTransMar(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODTMARCONTENTOR, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(
		GeomPoint2d ptCenter, 
		double comprimento,
		double largura,
		double altura,
		double rotacao) 
	{
		this.init(
			ptCenter.getX(), 
			ptCenter.getY(), 
			0.0, 
			comprimento,
			largura,
			altura,
			rotacao);
	}
	
	private void init(
		GeomPoint3d ptCenter, 
		double comprimento,
		double largura,
		double altura,
		double rotacao) 
	{
		this.init(
			ptCenter.getX(), 
			ptCenter.getY(), 
			ptCenter.getZ(), 
			comprimento,
			largura,
			altura,
			rotacao);
	}
	
	public void init(
		double xCenter, 
		double yCenter, 
		double zCenter, 
		double comprimento,
		double largura,
		double altura,
		double rotacao) 
	{
		this.ptCenter = new GeomPoint3d(xCenter, yCenter, zCenter);
		this.comprimento = comprimento;
		this.largura = largura;
		this.altura = altura;
		this.rotacao = rotacao;
    }
	
	@Override
	public void init(ICadObject o) {
		CadContentorTransMar other = (CadContentorTransMar)o;

		this.init(
			other.getPtCenter(), 
			other.getComprimento(),
			other.getLargura(),
			other.getAltura(),
			other.getRotacao() ); 
	}
	
	/* CREATE */
	
	public static CadContentorTransMar create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		GeomPoint2d ptCenter, 
		double comprimento,
		double largura,
		double altura,
		double rotacao) 
	{
		CadContentorTransMar o = new CadContentorTransMar(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		ptCenter, 
    		comprimento, 
    		largura, 
    		altura, 
    		rotacao);
    	return o;
    }
	
	public static CadContentorTransMar create(
		CadBlockDef oBlkDef, 			
		CadLayerDef oLayer, 
		CadLevel oLevel,
		GeomPoint3d ptCenter, 
		double comprimento,
		double largura,
		double altura,
		double rotacao) 
	{
		CadContentorTransMar o = new CadContentorTransMar(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		ptCenter, 
    		comprimento, 
    		largura, 
    		altura, 
    		rotacao);
    	return o;
    }

	public static CadContentorTransMar create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,		
		double xCenter, 
		double yCenter, 
		double zCenter, 
		double comprimento,
		double largura,
		double altura,
		double rotacao) 
	{			
		CadContentorTransMar o = new CadContentorTransMar(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		xCenter, 
    		yCenter,
    		zCenter,
    		comprimento, 
    		largura, 
    		altura, 
    		rotacao);
    	return o;
    }

	public static CadContentorTransMar create(CadContentorTransMar other)
	{		
		GeomPoint3d ptCenter = new GeomPoint3d( other.getPtCenter() );
		
		CadContentorTransMar o = new CadContentorTransMar(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(
    		ptCenter.getX(), 
    		ptCenter.getY(), 
    		ptCenter.getZ(), 
    		other.getComprimento(), 
    		other.getLargura(), 
    		other.getAltura(), 
    		other.getRotacao() );
    	return o;
    }

	public static CadContentorTransMar create(CadBlockDef blkDef, CadContentorTransMar other)
	{		
		GeomPoint3d ptCenter = new GeomPoint3d( other.getPtCenter() );
		
		CadContentorTransMar o = new CadContentorTransMar(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(
    		ptCenter.getX(), 
    		ptCenter.getY(), 
    		ptCenter.getZ(), 
    		other.getComprimento(), 
    		other.getLargura(), 
    		other.getAltura(), 
    		other.getRotacao() );
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadContentorTransMar duplicate()
	{
		CadContentorTransMar other = CadContentorTransMar.create(this);
		return other;
	}
	
	@Override
	public CadContentorTransMar duplicate(CadBlockDef blkDef)
	{
		CadContentorTransMar other = CadContentorTransMar.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadContentorTransMar copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadContentorTransMar other = CadContentorTransMar.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadContentorTransMar moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);
    	MoveData2dVO oCenter = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptCenter2dMcs);
    	this.ptCenter = new GeomPoint3d(oCenter.getPtDest());

    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		GeomPoint3d ptCenter2d = new GeomPoint3d(this.ptCenter);
		this.ptCenter = GeomUtil.mirror(ptCenter2d, ptI2dMcs, ptF2dMcs);		
		return this;
	}

	@Override
	public CadContentorTransMar scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptCenter2dMcs = new GeomPoint2d(this.ptCenter);

    	ScaleData2dVO oScl = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptCenter2dMcs);
    	this.ptCenter = new GeomPoint3d(oScl.getPtDest());

    	this.comprimento = this.comprimento * oScl.getScale();
    	this.largura = this.largura * oScl.getScale();
    	this.altura = this.altura * oScl.getScale();
    	
    	return this;
	}
	
	@Override
	public CadContentorTransMar offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadContentorTransMar oRect = copyTo(ptIMcs, ptFMcs);
		return oRect;
	}
    
	/* DEBUG */
    
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);
		
		lsProperty.addAll( this.ptCenter.toPropertyList("Center", true) ); 
		lsProperty.add( new ItemDataVO("Comprimento (m)", nf3.format( this.comprimento ), true ) );
		lsProperty.add( new ItemDataVO("Largura (m)", nf3.format( this.largura ), true ) );
		lsProperty.add( new ItemDataVO("Altura (m)", nf3.format( this.altura ), true ) );
		lsProperty.add( new ItemDataVO("Rotacao (m)", nf3.format( this.rotacao ), true ) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"XCenter:%s; YCenter:%s; ZCenter:%s; Comprimento:%s; Largura:%s; Altura:%s; Rotacao:%s; ", 
			nf6.format(this.ptCenter.getX()), 
			nf6.format(this.ptCenter.getY()), 
			nf6.format(this.ptCenter.getZ()),
			nf6.format(this.comprimento), 
			nf6.format(this.largura), 
			nf6.format(this.altura),
			nf6.format(this.rotacao) );
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
    	if(this.isDeleted()) return;
    	
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
        
        GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );
        
        if( bDragMode )
        {
	        if(ptBase2dMcs != null) 
	        {      	            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
	            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

	            GeomVector3d vDir3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs);		            
	            
		        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
		        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
		        {
		        	CadContentorTransMar other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptCenter2d = new GeomPoint2d(other.ptCenter);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadContentorTransMar other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptCenter2d = new GeomPoint2d(other.ptCenter);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadContentorTransMar other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptCenter2d = new GeomPoint2d(other.ptCenter);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadContentorTransMar other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptCenter2d = new GeomPoint2d(other.ptCenter);
		        }
	        }
        }

        //DRAW_OBJECT
        //
        GeomPoint2d[] arrPts = this.contentorExtents2d(ptCenter2d);
        
		GeomPoint2d pt0 = new GeomPoint2d( arrPts[0] ); 
		GeomPoint2d pt1 = new GeomPoint2d( arrPts[1] ); 
		GeomPoint2d pt2 = new GeomPoint2d( arrPts[2] ); 
		GeomPoint2d pt3 = new GeomPoint2d( arrPts[3] ); 
                
        DrawUtil.drawLineMcs(v, pt0, pt1, g);
        DrawUtil.drawLineMcs(v, pt1, pt2, g);
        DrawUtil.drawLineMcs(v, pt2, pt3, g);
        DrawUtil.drawLineMcs(v, pt3, pt0, g);
        
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptCenter2d, AppDefs.POINT_SIZE, AppDefs.POINT_TYPE_CROSS, g);
        }

        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
    	if(this.isDeleted()) return;
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();
        
        GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );
        
        //DRAW_OBJECT
        //
        GeomPoint3d[] arrPts = this.contentorExtents3d(ptCenter2d);
        
		GeomPoint3d pt0 = new GeomPoint3d( arrPts[0] ); 
		GeomPoint3d pt1 = new GeomPoint3d( arrPts[1] ); 
		GeomPoint3d pt2 = new GeomPoint3d( arrPts[2] ); 
		GeomPoint3d pt3 = new GeomPoint3d( arrPts[3] ); 
		//
		GeomPoint3d pt4 = new GeomPoint3d( arrPts[4] ); 
		GeomPoint3d pt5 = new GeomPoint3d( arrPts[5] ); 
		GeomPoint3d pt6 = new GeomPoint3d( arrPts[6] ); 
		GeomPoint3d pt7 = new GeomPoint3d( arrPts[7] ); 

		//BASE
		prep.addFace(view3d, this, c, pt0, pt1, pt2, pt3, null);
		//TOPO
		prep.addFace(view3d, this, c, pt4, pt5, pt6, pt7, null);
		//FRONT
		prep.addFace(view3d, this, c, pt0, pt1, pt5, pt4, null);
		//BACK
		prep.addFace(view3d, this, c, pt2, pt3, pt7, pt6, null);
		//LEFT
		prep.addFace(view3d, this, c, pt0, pt4, pt7, pt3, null);
		//RIGHT
		prep.addFace(view3d, this, c, pt1, pt2, pt6, pt5, null);
	}
    
	/* SELECT */
	
	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;

		if(this.isSelected()) return true;

		if(pt2dMcs == null) return false;
		
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distMax = boxSz / 2.0;

	    double xMcs = pt2dMcs.getX();
	    double yMcs = pt2dMcs.getY();

	    // LIMITES
        //
        GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );

        GeomPoint2d[] arrPts = this.contentorExtents2d(ptCenter2d);
        
		GeomPoint2d pt0 = new GeomPoint2d( arrPts[0] ); 
		GeomPoint2d pt1 = new GeomPoint2d( arrPts[1] ); 
		GeomPoint2d pt2 = new GeomPoint2d( arrPts[2] ); 
		GeomPoint2d pt3 = new GeomPoint2d( arrPts[3] ); 

		GeomPoint2d[] ptMaxMin = GeomUtil.maxMinPointOf(pt0, pt1, pt2, pt3);
		
		GeomPoint2d ptMin = new GeomPoint2d( ptMaxMin[0] );
		GeomPoint2d ptMax = new GeomPoint2d( ptMaxMin[1] );
		
	    double xMin = ptMin.getX() - distMax;
	    double yMin = ptMin.getY() - distMax;

	    double xMax = ptMax.getX() + distMax;
	    double yMax = ptMax.getY() + distMax;

	    // FAIXAS
	    double xMin_faixaInf = ptMin.getX() - distMax;
	    double xMin_faixaSup = ptMin.getX() + distMax;
	    //
	    double yMin_faixaInf = ptMin.getY() - distMax;
	    double yMin_faixaSup = ptMin.getY() + distMax;
	    //
	    double xMax_faixaInf = ptMax.getX() - distMax;
	    double xMax_faixaSup = ptMax.getX() + distMax;
	    //
	    double yMax_faixaInf = ptMax.getY() - distMax;
	    double yMax_faixaSup = ptMax.getY() + distMax;
	    
	    //LINE #1: (xMin, yMin)-(xMax, yMin)
		if( ( (xMcs >= xMin) && (xMcs <= xMax) ) &&
			( (yMcs >= yMin_faixaInf) && (yMcs <= yMin_faixaSup) ) ) 
		{
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
	    //LINE #2: (xMin, yMax)-(xMax, yMax)
		else if( ( (xMcs >= xMin) && (xMcs <= xMax) ) &&
				 ( (yMcs >= yMax_faixaInf) && (yMcs <= yMax_faixaSup) ) ) 
		{
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
	    //LINE #3: (xMin, yMin)-(xMin, yMax)
		else if( ( (xMcs >= xMin_faixaInf) && (xMcs <= xMin_faixaSup) ) &&
				 ( (yMcs >= yMin) && (yMcs <= yMax) ) ) 
		{
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
	    //LINE #4: (xMax, yMin)-(xMax, yMax)
		else if( ( (xMcs >= xMax_faixaInf) && (xMcs <= xMax_faixaSup) ) &&
				 ( (yMcs >= yMin) && (yMcs <= yMax) ) ) 
		{
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
    	
    	GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );
    	
        GeomPoint2d[] arrPts = this.contentorExtents2d(ptCenter2d);
        
		GeomPoint3d pt0 = new GeomPoint3d( arrPts[0] ); 
		GeomPoint3d pt1 = new GeomPoint3d( arrPts[1] ); 
		GeomPoint3d pt2 = new GeomPoint3d( arrPts[2] ); 
		GeomPoint3d pt3 = new GeomPoint3d( arrPts[3] ); 
                
    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add(pt0);
    	lsPtEndpoint.add(pt1);
    	lsPtEndpoint.add(pt2);
    	lsPtEndpoint.add(pt3);
		
    	//MIDDLE
    	//
    	GeomPoint3d ptMid0 = GeomUtil.midPointOf(pt0, pt1);
    	GeomPoint3d ptMid1 = GeomUtil.midPointOf(pt1, pt2);
    	GeomPoint3d ptMid2 = GeomUtil.midPointOf(pt2, pt3);
    	GeomPoint3d ptMid3 = GeomUtil.midPointOf(pt3, pt0);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(ptMid0);
    	lsPtMiddle.add(ptMid1);
    	lsPtMiddle.add(ptMid2);
    	lsPtMiddle.add(ptMid3);
		
    	//CENTER
    	//
    	GeomPoint3d ptCenter3d = new GeomPoint3d(this.ptCenter);
    	
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>();    	
    	lsPtCenter.add(ptCenter3d);
    	    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
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
    	
    	GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );
    	
        GeomPoint2d[] arrPts = this.contentorExtents2d(ptCenter2d);
        
		GeomPoint3d pt0 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, arrPts[0]); 
		GeomPoint3d pt1 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, arrPts[1]); 
		GeomPoint3d pt2 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, arrPts[2]); 
		GeomPoint3d pt3 = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, arrPts[3]); 
                
    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add(pt0);
    	lsPtEndpoint.add(pt1);
    	lsPtEndpoint.add(pt2);
    	lsPtEndpoint.add(pt3);
		
    	//MIDDLE
    	//
    	GeomPoint3d ptMid0 = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, pt0, pt1);
    	GeomPoint3d ptMid1 = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, pt1, pt2);
    	GeomPoint3d ptMid2 = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, pt2, pt3);
    	GeomPoint3d ptMid3 = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, pt3, pt0);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(ptMid0);
    	lsPtMiddle.add(ptMid1);
    	lsPtMiddle.add(ptMid2);
    	lsPtMiddle.add(ptMid3);
		
    	//CENTER
    	//
    	GeomPoint3d ptCenter3d = new GeomPoint3d(AppDefs.OSNAPMODE_CENTER, this.ptCenter);
    	
    	ArrayList<GeomPoint3d> lsPtCenter = new ArrayList<GeomPoint3d>();    	
    	lsPtCenter.add(ptCenter3d);
    	    	
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtEndpoint);
    	lsResult.addAll(lsPtMiddle);
    	lsResult.addAll(lsPtCenter);
    	return lsResult;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d( this.ptCenter );
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
	
    /* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomPoint3d ptCenter3d = new GeomPoint3d( this.ptCenter );
		GeomPoint3d ptDir3d = new GeomPoint3d(
			ptCenter3d.getX() + 1,
			ptCenter3d.getY(),
			ptCenter3d.getZ());

		GeomVector3d vDirI = new GeomVector3d(ptCenter3d, ptDir3d);
		GeomVector3d vDirF = vDirI.otherRotateToDegrees(this.rotacao);
		
		GeomVector3d uDirF = vDirF.otherUnit();
		GeomVector3d nDirF = uDirF.otherNorm();

		double comprimento2 = this.comprimento / 2.0;
		double largura2 = this.largura / 2.0;

		GeomPoint3d ptRef = ptCenter3d.otherMoveTo(uDirF, - comprimento2); 

		GeomPoint3d pt0 = ptRef.otherMoveTo(nDirF, - largura2); 
		GeomPoint3d pt1 = pt0.otherMoveTo(uDirF, comprimento2); 
		GeomPoint3d pt2 = pt1.otherMoveTo(nDirF, largura2); 
		GeomPoint3d pt3 = pt2.otherMoveTo(uDirF, - comprimento2); 
		
		GeomPoint3d ptResult[] = GeomUtil.maxMinPointOf(pt0, pt1, pt2, pt3);
		
		GeomPoint3d ptMin = new GeomPoint3d( ptResult[0] ); 
		GeomPoint3d ptMax = new GeomPoint3d( ptResult[1] ); 
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin, ptMax); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint2d ptCenter2d = new GeomPoint2d( this.ptCenter );
		GeomPoint2d ptDir2d = new GeomPoint2d(
			ptCenter2d.getX() + 1,
			ptCenter2d.getY() );

		GeomVector2d vDirI = new GeomVector2d(ptCenter2d, ptDir2d);
		GeomVector2d vDirF = vDirI.otherRotateToDegrees(this.rotacao);
		
		GeomVector2d uDirF = vDirF.otherUnit();
		GeomVector2d nDirF = uDirF.otherNorm();

		double comprimento2 = this.comprimento / 2.0;
		double largura2 = this.largura / 2.0;

		GeomPoint2d ptRef = ptCenter2d.otherMoveTo(uDirF, - comprimento2); 

		GeomPoint2d pt0 = ptRef.otherMoveTo(nDirF, - largura2); 
		GeomPoint2d pt1 = pt0.otherMoveTo(uDirF, comprimento2); 
		GeomPoint2d pt2 = pt1.otherMoveTo(nDirF, largura2); 
		GeomPoint2d pt3 = pt2.otherMoveTo(uDirF, - comprimento2); 
		
		GeomPoint2d ptResult[] = GeomUtil.maxMinPointOf(pt0, pt1, pt2, pt3);
		
		GeomPoint2d ptMin = new GeomPoint2d( ptResult[0] ); 
		GeomPoint2d ptMax = new GeomPoint2d( ptResult[1] ); 
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin, ptMax); 
		return oDim;
	}

	public GeomPoint3d getPtCenter() {
		return ptCenter;
	}

	public void setPtCenter(GeomPoint3d ptCenter) {
		this.ptCenter = ptCenter;
	}

	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getRotacao() {
		return rotacao;
	}

	public void setRotacao(double rotacao) {
		this.rotacao = rotacao;
	}

	public CadControleBacklistItemTransMarOData getData() {
		return data;
	}

	public void setData(CadControleBacklistItemTransMarOData data) {
		this.data = data;
	}

}
