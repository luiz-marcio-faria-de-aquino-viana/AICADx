/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPipeLine.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/12/2025
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
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadPipeLineRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class CadPipeLine extends CadEntity 
{
//Private
    private int numeroPipe 				 = AppDefs.NULL_INT;
    //
    private int fromObjId 				 = AppDefs.NULL_INT;
    private int toObjId 				 = AppDefs.NULL_INT;
    //
    private String sectionType			 = AppDefs.NULL_STR;
    private String pipeCategory			 = AppDefs.NULL_STR;
    //
    private double diameterMili			 = AppDefs.NULL_DBL;			// 600mm
    private double widthMili			 = AppDefs.NULL_DBL;			// 600mm
    private double heightMili			 = AppDefs.NULL_DBL;			// 600mm
    private double thicknessMili		 = AppDefs.NULL_DBL; 			// 300mm
    private double maxPipeSegmentLength	 = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH;

    /* PIPE_EXTERNAL_DIMENSION */
    private double externalDiameterMili	 = AppDefs.NULL_DBL;			// 600mm + 50mm = 650mm
    private double externalWidthMili	 = AppDefs.NULL_DBL;			// 600mm + 50mm = 650mm
    private double externalHeightMili	 = AppDefs.NULL_DBL;			// 300mm + 50mm = 350mm
        
    private ArrayList<GeomPoint3d> lsPts = null;
	
	/* Methodes */

	public void loadAllPoints(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = new ArrayList<GeomPoint3d>();
		for(GeomPoint3d oPt0 : lsPts) {
			this.lsPts.add(oPt0);
		}
	}

//Public

    public CadPipeLine(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_BIMPIPELINE, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(String pipeCategory, double diameterMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts)
	{
		this.init(AppDefs.NULL_INT, AppDefs.NULL_INT, DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR, pipeCategory, diameterMili, 0.0, 0.0, thicknessMili, AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH, lsPts);
	}
	
	private void init(String pipeCategory, double widthMili, double heightMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts)
	{
		this.init(AppDefs.NULL_INT, AppDefs.NULL_INT, DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR, pipeCategory, 0.0, widthMili, heightMili, thicknessMili, AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH, lsPts);
	}
	
	private void init(int fromObjId, int toObjId, String pipeCategory, double diameterMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts)
	{
		this.init(fromObjId, toObjId, DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR, pipeCategory, diameterMili, 0.0, 0.0, thicknessMili, AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH, lsPts);
	}
	
	private void init(int fromObjId, int toObjId, String pipeCategory, double widthMili, double heightMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts)
	{
		this.init(fromObjId, toObjId, DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR, pipeCategory, 0.0, widthMili, heightMili, thicknessMili, AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH, lsPts);
	}

	public void init(
		int fromObjId, 
		int toObjId, 
		String sectionType, 
		String pipeCategory, 
		double diameterMili, 
		double widthMili, 
		double heightMili, 
		double thicknessMili,
		double maxPipeSegmentLength, 
		ArrayList<GeomPoint3d> lsPts) 
	{
    	this.numeroPipe = this.getObjectId();
    	this.fromObjId = fromObjId;
    	this.toObjId = toObjId;
	    this.sectionType = sectionType;
	    this.pipeCategory = pipeCategory;
	    this.diameterMili = diameterMili;
    	this.widthMili = widthMili;
    	this.heightMili = heightMili;
	    this.thicknessMili = thicknessMili;
	    this.maxPipeSegmentLength = maxPipeSegmentLength;

	    /* PIPE_EXTERNAL_DIMENSION */
	    if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals( sectionType ) ) {
		    this.externalDiameterMili = this.diameterMili + (2.0 * this.thicknessMili);
	    }
	    else {
		    this.externalWidthMili = this.widthMili + (2.0 * this.thicknessMili);
		    this.externalHeightMili = this.heightMili + (2.0 * this.thicknessMili);
	    }	    

    	this.loadAllPoints(lsPts); 
    }

	public void init(
		int fromObjId, 
		int toObjId, 
		String sectionType, 
		String pipeCategory, 
		double diameterMili, 
		double widthMili, 
		double heightMili, 
		double thicknessMili,
		double maxPipeSegmentLength) 
	{
    	this.numeroPipe = this.getObjectId();
    	this.fromObjId = fromObjId;
    	this.toObjId = toObjId;
	    this.sectionType = sectionType;
	    this.pipeCategory = pipeCategory;
	    this.diameterMili = diameterMili;
    	this.widthMili = widthMili;
    	this.heightMili = heightMili;
	    this.thicknessMili = thicknessMili;
	    this.maxPipeSegmentLength = maxPipeSegmentLength;

	    /* PIPE_EXTERNAL_DIMENSION */
	    if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals( sectionType ) ) {
		    this.externalDiameterMili = this.diameterMili + (2.0 * this.thicknessMili);
	    }
	    else {
		    this.externalWidthMili = this.widthMili + (2.0 * this.thicknessMili);
		    this.externalHeightMili = this.heightMili + (2.0 * this.thicknessMili);
	    }	    
    }

	@Override
	public void init(ICadObject o) {
		CadPipeLine other = (CadPipeLine)o;

    	this.init(
    		other.fromObjId,
        	other.toObjId,
    	    other.sectionType,
    	    other.pipeCategory,
        	other.diameterMili,
        	other.widthMili,
        	other.heightMili,
        	other.thicknessMili,
        	AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH,
        	other.getLsPts() );    	
	}
	
	/* CREATE */
	
	public static CadPipeLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String pipeCategory, double diameterMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts) {
    	CadPipeLine o = new CadPipeLine(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pipeCategory, diameterMili, thicknessMili, lsPts);
    	return o;
    }
	
	public static CadPipeLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, String pipeCategory, double widthMili, double heightMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts) {
    	CadPipeLine o = new CadPipeLine(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pipeCategory, widthMili, heightMili, thicknessMili, lsPts);
    	return o;
    }
	
	public static CadPipeLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, int fromObjId, int toObjId, String pipeCategory, double diameterMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts) {
    	CadPipeLine o = new CadPipeLine(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(fromObjId, toObjId, pipeCategory, diameterMili, thicknessMili, lsPts);
    	return o;
    }
	
	public static CadPipeLine create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, int fromObjId, int toObjId, String pipeCategory, double widthMili, double heightMili, double thicknessMili, ArrayList<GeomPoint3d> lsPts) {
    	CadPipeLine o = new CadPipeLine(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(fromObjId, toObjId, pipeCategory, widthMili, heightMili, thicknessMili, lsPts);
    	return o;
    }
	
	public static CadPipeLine create(CadPipeLine other) {
    	CadPipeLine o = new CadPipeLine(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadPipeLine create(CadBlockDef blkDef, CadPipeLine other) {
    	CadPipeLine o = new CadPipeLine(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPipeLine duplicate()
	{
		CadPipeLine other = CadPipeLine.create(this);
		return other;
	}
	
	@Override
	public CadPipeLine duplicate(CadBlockDef blkDef)
	{
		CadPipeLine other = CadPipeLine.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadPipeLine copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPipeLine otherLine = CadPipeLine.create(this);
		otherLine.moveTo(ptIMcs, ptFMcs);
		return otherLine;
	}

	@Override
	public CadPipeLine moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
	    ArrayList<GeomPoint3d> lsPts_moved = GeomUtil.moveTo(ptIMcs, ptFMcs, this.lsPts);
	    this.lsPts = lsPts_moved;
    	return this;
	}
	
	@Override
	public CadPipeLine scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
	    GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);		
		
	    ArrayList<GeomPoint3d> lsPts_moved = GeomUtil.moveTo(ptIMcs, ptFMcs, this.lsPts);
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			GeomPoint3d ptOrig3dMcs = this.lsPts.get(0);			
			GeomPoint2d ptOrig2dMcs = new GeomPoint2d( ptOrig3dMcs );

	    	ScaleData2dVO oI = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
	    	
	    	GeomPoint3d ptNewOrig3dMcs = new GeomPoint3d( oI.getPtDest() );			
	    	lsPts_moved.add(ptNewOrig3dMcs);
		}

		this.lsPts = lsPts_moved;
    	return this;
	}
	
    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
	    GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);		
		
	    ArrayList<GeomPoint3d> lsPts_moved = new ArrayList<GeomPoint3d>();
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			GeomPoint2d ptOrig2dMcs = new GeomPoint2d( this.lsPts.get(i) );
	    	GeomPoint2d ptNewOrig2dMcs = GeomUtil.mirror(ptOrig2dMcs, ptI2dMcs, ptF2dMcs);
	    	GeomPoint3d ptNewOrig3dMcs = new GeomPoint3d( ptNewOrig2dMcs );			
	    	lsPts_moved.add(ptNewOrig3dMcs);
		}
		this.lsPts = lsPts_moved;
    	return this;
	}
	
	@Override
	public CadPipeLine offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
	    ArrayList<GeomPoint3d> lsPts_offset = new ArrayList<GeomPoint3d>();
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			GeomPoint3d ptOrig3dMcs = new GeomPoint3d( this.lsPts.get(i) );
			GeomPoint3d ptNewOrig3dMcs = ptOrig3dMcs.otherMoveTo(uDirMcs, dist);
	    	lsPts_offset.add(ptNewOrig3dMcs);
		}
		this.lsPts = lsPts_offset;
    	return this;
	}
    
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.add( new ItemDataVO("Tubulacao", Integer.toString(this.numeroPipe)) );

		if(this.fromObjId != AppDefs.NULL_INT)
			lsProperty.add( new ItemDataVO("CI Anterior", Integer.toString(this.fromObjId)) );
		
		if(this.toObjId != AppDefs.NULL_INT)
			lsProperty.add( new ItemDataVO("CI Proxima", Integer.toString(this.toObjId)) );

		lsProperty.add( new ItemDataVO("Secao", this.sectionType) );
		lsProperty.add( new ItemDataVO("Categoria", this.pipeCategory) );

		if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals(this.sectionType) ) {
			//SECAO_CIRCULAR
			String strDiameterMili = nf0.format(this.diameterMili);
			lsProperty.add( new ItemDataVO("Diam.Nom.(mm)", strDiameterMili) );
			
			String strExternalDiameterMili = nf0.format(this.externalDiameterMili);
			lsProperty.add( new ItemDataVO("Diam.Ext.(mm)", strExternalDiameterMili) );
		}
		else {
			//SECAO_RETANGULAR
			String strWidthMili = nf0.format(this.widthMili);
			lsProperty.add( new ItemDataVO("Larg.Int.(mm)", strWidthMili) );

			String strHeightMili = nf0.format(this.heightMili);			
			lsProperty.add( new ItemDataVO("Alt.Int(mm)", strHeightMili) );

			String strExternalWidthMili = nf0.format(this.externalWidthMili);
			lsProperty.add( new ItemDataVO("Larg.Ext.(mm)", strExternalWidthMili) );

			String strExternalHeightMili = nf0.format(this.externalHeightMili);
			lsProperty.add( new ItemDataVO("Al.Ext.(mm)", strExternalHeightMili) );
		}

		String strThicknessMili = nf0.format(this.thicknessMili);
		lsProperty.add( new ItemDataVO("Espesura(mm)", strThicknessMili) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"numeroPipe:%s;" +
			"fromObjId:%s;" +
			"toObjId:%s;" +
			"sectionType:%s;" +
			"pipeCategory:%s;" +
			"diameterMili:%s;" +
			"widthMili:%s;" +
			"heightMili:%s;" +
			"thicknessMili:%s;" +
			"maxPipeSegmentLength:%s;" + 
			"externalDiameterMili:%s;" +
	    	"externalWidthMili:%s;" +
	    	"externalHeightMili:%s;", 
	    	this.numeroPipe,
	    	this.fromObjId,
	    	this.toObjId,
		    this.sectionType,
		    this.pipeCategory,
	    	this.diameterMili,
	    	this.widthMili,
	    	this.heightMili,
			this.thicknessMili,
			this.maxPipeSegmentLength,
			this.externalDiameterMili,
	    	this.externalWidthMili,
	    	this.externalHeightMili);
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
	public DrawCache createOsnapCache() {
		return null;
	}

    /* DRAWING */

	public void redraw2d_flowdir_planView(ICadViewBase v, int posI, int posF, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact, Graphics g)
	{
		if( !DrawUtil.checkIfLayRefOfTubulacaoDrenagem( this.getLayer() ) ) return;             
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		double arrowLengthMcs = AppDefs.ARROWLENGTHSZ_SMALL * sclFact;
		double arrowWidthMcs = AppDefs.ARROWWIDTHSZ_SMALL * sclFact;
		double arrowPointMcs = AppDefs.ARROWPOINTSZ_SMALL * sclFact;
		
		double textHeight = AppDefs.FONTSZ_SMALL * sclFact;
		
		double lineHeight = arrowWidthMcs * 3.0;
		
		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(posI) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(posF) );
		
		double dist3d = ptOrigI.distTo(ptOrigF);

		double dX = ptOrigF.getX() - ptOrigI.getX();
		double dY = ptOrigF.getY() - ptOrigI.getY();
		double dZ = ptOrigF.getZ() - ptOrigI.getZ();

		GeomPoint2d ptI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptF = new GeomPoint2d(ptF2dMcs);
		if(dZ > 0.0) {
			ptI = new GeomPoint2d(ptF2dMcs);
			ptF = new GeomPoint2d(ptI2dMcs);			
		}
				
		GeomPoint2d ptTextI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptTextF = new GeomPoint2d(ptF2dMcs);
		if(dX < 0.0) {
			ptTextI = new GeomPoint2d(ptF2dMcs);
			ptTextF = new GeomPoint2d(ptI2dMcs);			
		}
		
		double declividadePerc = dZ / dist3d * 100.0; 
		
		String strDeclividadePerc = String.format("i = %s %%", nf1.format(declividadePerc));
		
		GeomVector2d vIF2d = new GeomVector2d(ptI, ptF); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();

		GeomVector2d vTextIF2d = new GeomVector2d(ptTextI, ptTextF); 
		
		GeomVector2d uTextIF2d = vTextIF2d.otherUnit();
		GeomVector2d nTextIF2d = uTextIF2d.otherNorm();

		double dist2d = ptI.distTo(ptF);		
		
		Color oldcolor = GeomUtil.setColor(g, Color.GREEN);
		
		double d = dist2d / 6.0;

		GeomPoint2d ptArrowDeclividadePerc = ptI.otherMoveTo(uIF2d, d);		
		DrawUtil.drawArrowMcs(v, ptArrowDeclividadePerc, arrowLengthMcs, arrowWidthMcs, arrowPointMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uIF2d, g);

		GeomPoint2d ptTextDeclividadePerc = ptArrowDeclividadePerc.otherMoveTo(nTextIF2d, - lineHeight);
		DrawUtil.drawTextMcs(v, strDeclividadePerc, ptTextDeclividadePerc, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF2d, g);

		GeomUtil.setColor(g, oldcolor);	
	}
	
	public void redraw2d_annotation_planView(ICadViewBase v, int posI, int posF, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact, Graphics g)
	{
		if( !DrawUtil.checkIfLayRefOfTubulacaoDrenagem( this.getLayer() ) ) return;             
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		double textHeight = AppDefs.FONTSZ_SMALL * sclFact;
		
		double lineHeight = textHeight * 1.0;
				
		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(posI) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(posF) );
		
		double dist3d = ptOrigI.distTo(ptOrigF);

		double diamMeter = this.externalDiameterMili / 1000.0;

		double dX = ptOrigF.getX() - ptOrigI.getX();
		double dY = ptOrigF.getY() - ptOrigI.getY();
		double dZ = ptOrigF.getZ() - ptOrigI.getZ();

		GeomPoint2d ptI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptF = new GeomPoint2d(ptF2dMcs);
		if(dZ > 0.0) {
			ptI = new GeomPoint2d(ptF2dMcs);
			ptF = new GeomPoint2d(ptI2dMcs);			
		}

		GeomVector2d vIF = new GeomVector2d(ptI2dMcs, ptF2dMcs);
		GeomVector2d uIF = vIF.otherUnit();
				
		GeomPoint2d ptTextI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptTextF = new GeomPoint2d(ptF2dMcs);
		if(dX < 0.0) {
			ptTextI = new GeomPoint2d(ptF2dMcs);
			ptTextF = new GeomPoint2d(ptI2dMcs);			
		}
		
		double dimMeter = this.diameterMili / 1000.0;
		double dimMeter2 = dimMeter / 2.0;

		String strLengthMeter = String.format("L = %s m", nf2.format(dist3d));
		String strDiameterMeter = String.format("D = %s m", nf2.format(dimMeter));
        
		if( DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR.equals(this.sectionType) ) {
    		dimMeter = this.widthMili / 1000.0;
    		dimMeter2 = dimMeter / 2.0;        

    		double hMeter = this.heightMili / 1000.0;
    		strDiameterMeter = String.format("D = %s m X %s m", nf2.format(dimMeter), nf2.format(hMeter));
        }
		
		GeomVector2d vTextIF = new GeomVector2d(ptTextI, ptTextF); 
		
		GeomVector2d uTextIF = vTextIF.otherUnit();
		GeomVector2d nTextIF = uTextIF.otherNorm();

		GeomPoint2d ptCenter2dMcs = GeomUtil.midPointOf(ptTextI, ptTextF);

		Color oldcolor = GeomUtil.setColor(g, Color.GREEN);
		
		// COMPRIMENTO
		//
		double d = dimMeter + lineHeight;
		
		GeomPoint2d ptTextLengthMeter = ptCenter2dMcs.otherMoveTo(nTextIF, d);
		DrawUtil.drawTextMcs(v, strLengthMeter, ptTextLengthMeter, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF, g);

		// DIAMETRO
		//
		GeomPoint2d ptTextDiameterMeter = ptCenter2dMcs.otherMoveTo(nTextIF, - d);
		DrawUtil.drawTextMcs(v, strDiameterMeter, ptTextDiameterMeter, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF, g);

		// PTI-Z
		//
		double zPos = 1.5 * diamMeter;
		
		double zI = ptOrigI.getZ();
		
		String strZI = String.format("H = %s m", nf3.format(zI));		
		GeomPoint2d ptTextZI = ptI2dMcs.otherMoveTo(uIF, zPos);
		DrawUtil.drawTextMcs(v, strZI, ptTextZI, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF, g);

		// PTF-Z
		//
		double zF = ptOrigF.getZ();
		
		String strZF = String.format("H = %s m", nf3.format(zF));		
		GeomPoint2d ptTextZF = ptF2dMcs.otherMoveTo(uIF, - zPos);
		DrawUtil.drawTextMcs(v, strZF, ptTextZF, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF, g);

		GeomUtil.setColor(g, oldcolor);	
	}
	
	public void redraw2d_lowDetailLevel_annotation_planView(ICadViewBase v, int posI, int posF, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact, Graphics g)
	{
		if( !DrawUtil.checkIfLayRefOfTubulacaoDrenagem( this.getLayer() ) ) return;             
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		double textHeight = AppDefs.FONTSZ_SMALL * sclFact;
		
		double lineHeight = textHeight * 1.0;
		
		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(posI) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(posF) );
		
		double dist3d = ptOrigI.distTo(ptOrigF);

		double dX = ptOrigF.getX() - ptOrigI.getX();
		double dY = ptOrigF.getY() - ptOrigI.getY();
		double dZ = ptOrigF.getZ() - ptOrigI.getZ();

		GeomPoint2d ptI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptF = new GeomPoint2d(ptF2dMcs);
		if(dZ > 0.0) {
			ptI = new GeomPoint2d(ptF2dMcs);
			ptF = new GeomPoint2d(ptI2dMcs);			
		}
		
		GeomPoint2d ptTextI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptTextF = new GeomPoint2d(ptF2dMcs);
		if(dX < 0.0) {
			ptTextI = new GeomPoint2d(ptF2dMcs);
			ptTextF = new GeomPoint2d(ptI2dMcs);			
		}		
		
		double dimMeter = this.diameterMili / 1000.0;
		double dimMeter2 = dimMeter / 2.0;

		String strLengthMeter = String.format("L = %s m", nf2.format(dist3d));
		String strDiameterMeter = String.format("D = %s m", nf2.format(dimMeter));
        
		if( DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR.equals(this.sectionType) ) {
    		dimMeter = this.widthMili / 1000.0;
    		dimMeter2 = dimMeter / 2.0;        

    		double hMeter = this.heightMili / 1000.0;
    		strDiameterMeter = String.format("D = %s m X %s m", nf2.format(dimMeter), nf2.format(hMeter));
        }
		
		GeomVector2d vTextIF = new GeomVector2d(ptTextI, ptTextF); 
		
		GeomVector2d uTextIF = vTextIF.otherUnit();
		GeomVector2d nTextIF = uTextIF.otherNorm();

		GeomPoint2d ptCenter2dMcs = GeomUtil.midPointOf(ptTextI, ptTextF);

		Color oldcolor = GeomUtil.setColor(g, Color.GREEN);
		
		double d = lineHeight;
		
		GeomPoint2d ptTextLengthMeter = ptCenter2dMcs.otherMoveTo(nTextIF, d);
		DrawUtil.drawTextMcs(v, strLengthMeter, ptTextLengthMeter, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF, g);

		GeomPoint2d ptTextDiameterMeter = ptCenter2dMcs.otherMoveTo(nTextIF, - d);
		DrawUtil.drawTextMcs(v, strDiameterMeter, ptTextDiameterMeter, textHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uTextIF, g);

		GeomUtil.setColor(g, oldcolor);	
	}
		
	//PLANVIEW
	//
	public void redraw2d_sectionCircle_planView(ICadViewBase v, int posI, int posF, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, Graphics g)
	{
		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(posI) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(posF) );

		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();

		//PIPE_CENTER_LINE
		//
		BorderStrokeVO b = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		
		Stroke oldltype = GeomUtil.setLtype(g, b.getLtype());
		
        DrawUtil.drawLineMcs(v, ptI2dMcs, ptF2dMcs, g);

        GeomUtil.setLtype(g, oldltype);
		
		//INTERNAL_DIAMETER
		//
		b = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		
		oldltype = GeomUtil.setLtype(g, b.getLtype());
		
		double diamMeter = this.diameterMili / 1000.0;
		double radiusMeter = diamMeter / 2.0;
		
		GeomPoint2d ptI2dMcs_left = ptI2dMcs.otherMoveTo(nIF2d, radiusMeter);
		GeomPoint2d ptI2dMcs_right = ptI2dMcs.otherMoveTo(nIF2d, - radiusMeter);

		GeomPoint2d ptF2dMcs_left = ptF2dMcs.otherMoveTo(nIF2d, radiusMeter);
		GeomPoint2d ptF2dMcs_right = ptF2dMcs.otherMoveTo(nIF2d, - radiusMeter);
		
        DrawUtil.drawLineMcs(v, ptI2dMcs_left, ptF2dMcs_left, g);
        DrawUtil.drawLineMcs(v, ptI2dMcs_right, ptF2dMcs_right, g);

        GeomUtil.setLtype(g, oldltype);
		
		//EXTERNAL_DIAMETER
		//
		double diamExtMeter = this.externalDiameterMili / 1000.0;
		double radiusExtMeter = diamExtMeter / 2.0;
		
		GeomPoint2d ptI2dMcs_left_ext = ptI2dMcs.otherMoveTo(nIF2d, radiusExtMeter);
		GeomPoint2d ptI2dMcs_right_ext = ptI2dMcs.otherMoveTo(nIF2d, - radiusExtMeter);

		GeomPoint2d ptF2dMcs_left_ext = ptF2dMcs.otherMoveTo(nIF2d, radiusExtMeter);
		GeomPoint2d ptF2dMcs_right_ext = ptF2dMcs.otherMoveTo(nIF2d, - radiusExtMeter);
		
        DrawUtil.drawLineMcs(v, ptI2dMcs_left_ext, ptF2dMcs_left_ext, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_left_ext, ptF2dMcs_right_ext, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_right_ext, ptI2dMcs_right_ext, g);
        DrawUtil.drawLineMcs(v, ptI2dMcs_right_ext, ptI2dMcs_left_ext, g);

		//PIPE_UNION
		//
        double thicknessMeter = this.thicknessMili / 1000.0;
		double radiusUnionMeter = radiusExtMeter + thicknessMeter;

		double dist3d = ptOrigI.distTo(ptOrigF);
		int nsegs = (int) Math.floor(dist3d / this.maxPipeSegmentLength); 

		double dist2d = ptI2dMcs.distTo(ptF2dMcs);
		
		double maxPipeSegmentLenght2d = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH * (dist2d / dist3d); 
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint2d ptSegI2dMcs = ptI2dMcs.otherMoveTo(uIF2d, currDist);
    		GeomPoint2d ptSegF2dMcs = ptSegI2dMcs.otherMoveTo(uIF2d, thicknessMeter);

    		//PIPE_UNION_POINTS
    		//
    		GeomPoint2d ptSegI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, radiusUnionMeter);
    		GeomPoint2d ptSegI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - radiusUnionMeter);

    		GeomPoint2d ptSegF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, radiusUnionMeter);
    		GeomPoint2d ptSegF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - radiusUnionMeter);

    		//REMOVE_PIPE_LINES
    		//
    		ArrayList<GeomPoint2d> lsPts2dMcs = new ArrayList<GeomPoint2d>(); 
    		
    		lsPts2dMcs.add(ptSegI2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_right_union);
    		lsPts2dMcs.add(ptSegI2dMcs_right_union);

    		Color oldcol1 = GeomUtil.setColor(g, AppDefs.BACKGROUNDCOLOR);

    		DrawUtil.fillPolygonMcs(v, lsPts2dMcs, g);

    		GeomUtil.setColor(g, oldcol1);
    		
    		//DRAW_PIPE_UNION_LINES
    		//
            DrawUtil.drawLineMcs(v, ptSegI2dMcs_left_union, ptSegF2dMcs_left_union, g);
            DrawUtil.drawLineMcs(v, ptSegF2dMcs_left_union, ptSegF2dMcs_right_union, g);
            DrawUtil.drawLineMcs(v, ptSegF2dMcs_right_union, ptSegI2dMcs_right_union, g);
            DrawUtil.drawLineMcs(v, ptSegI2dMcs_right_union, ptSegI2dMcs_left_union, g);
            
            currDist += maxPipeSegmentLenght2d;
        }
	}
	
	public void redraw2d_sectionRectangle_planView(ICadViewBase v, int posI, int posF, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, Graphics g)
	{
		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(posI) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(posF) );

		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
		
		//INTERNAL_DIAMETER
		//
		double widthMeter = this.widthMili / 1000.0;
		double widthMeter2 = widthMeter / 2.0;

		double heightMeter = this.heightMili / 1000.0;
		
		GeomPoint2d ptI2dMcs_left = ptI2dMcs.otherMoveTo(nIF2d, widthMeter2);
		GeomPoint2d ptI2dMcs_right = ptI2dMcs.otherMoveTo(nIF2d, - widthMeter2);

		GeomPoint2d ptF2dMcs_left = ptF2dMcs.otherMoveTo(nIF2d, widthMeter2);
		GeomPoint2d ptF2dMcs_right = ptF2dMcs.otherMoveTo(nIF2d, - widthMeter2);
		
        DrawUtil.drawLineMcs(v, ptI2dMcs_left, ptF2dMcs_left, g);
        DrawUtil.drawLineMcs(v, ptI2dMcs_right, ptF2dMcs_right, g);
        
		//EXTERNAL_DIAMETER
		//
		double widthExtMeter = this.externalWidthMili / 1000.0;
		double widthExtMeter2 = widthExtMeter / 2.0;
		
		GeomPoint2d ptI2dMcs_left_ext = ptI2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
		GeomPoint2d ptI2dMcs_right_ext = ptI2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

		GeomPoint2d ptF2dMcs_left_ext = ptF2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
		GeomPoint2d ptF2dMcs_right_ext = ptF2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);
		
        DrawUtil.drawLineMcs(v, ptI2dMcs_left_ext, ptF2dMcs_left_ext, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_left_ext, ptF2dMcs_right_ext, g);
        DrawUtil.drawLineMcs(v, ptF2dMcs_right_ext, ptI2dMcs_right_ext, g);
        DrawUtil.drawLineMcs(v, ptI2dMcs_right_ext, ptI2dMcs_left_ext, g);

		//PIPE_UNION
		//
        double thicknessMeter = this.thicknessMili / 1000.0;
		double widthUnionMeter2 = widthExtMeter2 + thicknessMeter;

		double dist3d = ptOrigI.distTo(ptOrigF);
		int nsegs = (int) Math.floor(dist3d / this.maxPipeSegmentLength); 

		double dist2d = ptI2dMcs.distTo(ptF2dMcs);
		
		double maxPipeSegmentLenght2d = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH * (dist2d / dist3d); 
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		GeomPoint2d ptSegI2dMcs = ptI2dMcs.otherMoveTo(uIF2d, currDist);
    		GeomPoint2d ptSegF2dMcs = ptSegI2dMcs.otherMoveTo(uIF2d, - thicknessMeter);
            
    		//INTERNAL
    		//
    		GeomPoint2d ptSegIntI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, widthMeter2);
    		GeomPoint2d ptSegIntI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - widthMeter2);
            
    		GeomPoint2d ptSegIntF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, widthMeter2);
    		GeomPoint2d ptSegIntF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - widthMeter2);
            
    		//EXTERNAL
    		//
    		GeomPoint2d ptSegExtI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
    		GeomPoint2d ptSegExtI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

    		GeomPoint2d ptSegExtF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
    		GeomPoint2d ptSegExtF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

    		//FLANGE-TO-FLANGE
    		//
    		GeomPoint2d ptSegI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, widthUnionMeter2);
    		GeomPoint2d ptSegI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - widthUnionMeter2);

    		GeomPoint2d ptSegF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, widthUnionMeter2);
    		GeomPoint2d ptSegF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - widthUnionMeter2);

    		//REMOVE_PIPE_LINES
    		//
    		ArrayList<GeomPoint2d> lsPts2dMcs = new ArrayList<GeomPoint2d>(); 
    		
    		lsPts2dMcs.add(ptSegI2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_right_union);
    		lsPts2dMcs.add(ptSegI2dMcs_right_union);

    		Color oldcol1 = GeomUtil.setColor(g, AppDefs.BACKGROUNDCOLOR);

    		DrawUtil.fillPolygonMcs(v, lsPts2dMcs, g);

    		GeomUtil.setColor(g, oldcol1);
    		
    		//DRAW_PIPE_UNION_LINES
    		//
    		if(currDist < AppDefs.MATHPREC_MIN) {
                DrawUtil.drawLineMcs(v, ptSegF2dMcs_left_union, ptSegI2dMcs_left_union, g);
                DrawUtil.drawLineMcs(v, ptSegI2dMcs_left_union, ptSegExtI2dMcs_left_union, g);
                //
                DrawUtil.drawLineMcs(v, ptSegF2dMcs_left_union, ptSegF2dMcs_right_union, g);
                DrawUtil.drawLineMcs(v, ptSegIntI2dMcs_left_union, ptSegIntI2dMcs_right_union, g);
        		//
                DrawUtil.drawLineMcs(v, ptSegF2dMcs_right_union, ptSegI2dMcs_right_union, g);
                DrawUtil.drawLineMcs(v, ptSegI2dMcs_right_union, ptSegExtI2dMcs_right_union, g);
    		}
    		else {
                DrawUtil.drawLineMcs(v, ptSegIntI2dMcs_left_union, ptSegIntF2dMcs_left_union, g);
                DrawUtil.drawLineMcs(v, ptSegIntF2dMcs_left_union, ptSegF2dMcs_left_union, g);
                DrawUtil.drawLineMcs(v, ptSegF2dMcs_left_union, ptSegI2dMcs_left_union, g);
                DrawUtil.drawLineMcs(v, ptSegI2dMcs_left_union, ptSegExtI2dMcs_left_union, g);
                //
                DrawUtil.drawLineMcs(v, ptSegIntI2dMcs_left_union, ptSegIntI2dMcs_right_union, g);
        		//
                DrawUtil.drawLineMcs(v, ptSegIntI2dMcs_right_union, ptSegIntF2dMcs_right_union, g);
                DrawUtil.drawLineMcs(v, ptSegIntF2dMcs_right_union, ptSegF2dMcs_right_union, g);
                DrawUtil.drawLineMcs(v, ptSegF2dMcs_right_union, ptSegI2dMcs_right_union, g);
                DrawUtil.drawLineMcs(v, ptSegI2dMcs_right_union, ptSegExtI2dMcs_right_union, g);
    		}
    		
            currDist += maxPipeSegmentLenght2d;
        }
	}
		
	//LOW_DETAIL_LEVEL
	//
	public void redraw2d_lowDetailLevel_planView(ICadViewBase v, int posI, int posF, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact, Graphics g)
	{
		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(posI) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(posF) );

		double lineWidth = AppDefs.LINEWIDTH_SMALL * sclFact;
		
		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
	
		//PIPE_CENTER_LINE
		//
		BorderStrokeVO b = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		
		BorderStrokeVO bs = b.duplicate(lineWidth);
		
		Stroke oldltype = GeomUtil.setLtype(g, bs.getLtype());
		
	    DrawUtil.drawLineMcs(v, ptI2dMcs, ptF2dMcs, g);
	
	    GeomUtil.setLtype(g, oldltype);
		
		//INTERNAL_DIAMETER
		//
		b = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_HIDDEN];
		
		oldltype = GeomUtil.setLtype(g, b.getLtype());
		
	    DrawUtil.drawLineMcs(v, ptI2dMcs, ptF2dMcs, g);
	
	    GeomUtil.setLtype(g, oldltype);
		
		//PIPE_UNION
		//
	    double thicknessMeter = this.thicknessMili / 1000.0;
	    double thicknessMeter2 = thicknessMeter / 2.0;
	
		double dist3d = ptOrigI.distTo(ptOrigF);
		int nsegs = (int) Math.floor(dist3d / this.maxPipeSegmentLength); 
	
		double dist2d = ptI2dMcs.distTo(ptF2dMcs);
		
		double maxPipeSegmentLenght2d = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH * (dist2d / dist3d); 
		
	    double currDist = 0.0;
	    for(int i = 0; i <= nsegs; i++) {
			//PIPE_SEGMENT_START_AND_END_POINTS
			//
			GeomPoint2d ptSegI2dMcs = ptI2dMcs.otherMoveTo(uIF2d, currDist);
	
			//PIPE_UNION_POINTS
			//
			GeomPoint2d ptSegI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, thicknessMeter2);
			GeomPoint2d ptSegI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - thicknessMeter2);
	
			//DRAW_PIPE_UNION_LINES
			//
	        DrawUtil.drawLineMcs(v, ptSegI2dMcs_left_union, ptSegI2dMcs_right_union, g);
	        
	        currDist += maxPipeSegmentLenght2d;
	    }
	}

	
	//LOW_DETAIL_LEVEL
	//
	public void redraw2d_lowDetailLevel_planView(ICadViewBase v, ArrayList<GeomPoint3d> lsPts3dMcs, double sclFact, Graphics g)
	{
		int sz = lsPts3dMcs.size();
		if(sz == 0) return;

		CadLayerDef oLayer = this.getLayer();
		
		GeomPoint2d ptI2dMcs = new GeomPoint2d( lsPts3dMcs.get(0) ); 
		for(int i = 1; i < sz; i++) {
			int posI = i - 1;
			int posF = i;			
			
			GeomPoint2d ptF2dMcs = new GeomPoint2d( lsPts3dMcs.get(i) );
			
			this.redraw2d_lowDetailLevel_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, sclFact, g);
			
			redraw2d_lowDetailLevel_annotation_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, sclFact, g);

			redraw2d_flowdir_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, sclFact, g);

			ptI2dMcs = ptF2dMcs;
		}		
	}

	//HIGH_DETAIL_LEVEL
	//
	public void redraw2d_highDetailLevel_planView(ICadViewBase v, ArrayList<GeomPoint3d> lsPts3dMcs, double sclFact, Graphics g)
	{
		int sz = lsPts3dMcs.size();
		if(sz == 0) return;

		CadLayerDef oLayer = this.getLayer();
		
		GeomPoint2d ptI2dMcs = new GeomPoint2d( lsPts3dMcs.get(0) ); 
		for(int i = 1; i < sz; i++) {
			int posI = i - 1;
			int posF = i;			
			
			GeomPoint2d ptF2dMcs = new GeomPoint2d( lsPts3dMcs.get(i) );

			if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals(this.sectionType) ) {
				redraw2d_sectionCircle_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, g);
			}
			else {
				redraw2d_sectionRectangle_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, g);
			}

			redraw2d_annotation_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, sclFact, g);	

			redraw2d_flowdir_planView(v, posI, posF, ptI2dMcs, ptF2dMcs, sclFact, g);
			
			ptI2dMcs = ptF2dMcs;
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
        
        ArrayList<GeomPoint3d> lsDestPts = this.lsPts;
        
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
		        	CadPipeLine other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	lsDestPts = other.getLsPts();
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPipeLine other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	lsDestPts = other.getLsPts();
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadPipeLine other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	lsDestPts = other.getLsPts();
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPipeLine other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	lsDestPts = other.getLsPts();
		        }
	        }
        }
        
        String strDetailLevel = v.getDetailLevel();

        if( AppDefs.DEF_DETAILLEVEL_HIGH.equals(strDetailLevel) ) {
        	redraw2d_highDetailLevel_planView(v, lsDestPts, sclFact, g);
        }
        else {
        	redraw2d_lowDetailLevel_planView(v, lsDestPts, sclFact, g);
        }
        
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	//VIEW-3D
	//
	public void redraw3d_view(ICadViewBase v, double dist, GeomPoint3d ptDestIMcs, GeomPoint3d ptDestFMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep)
	{
    	if(this.isDeleted()) return;
    	
    	CadBlockDef oBlkDef = this.getBlkDef();
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

		GeomPoint3d ptOrigI = new GeomPoint3d( ptDestIMcs );
		GeomPoint3d ptOrigF = new GeomPoint3d( ptDestFMcs );

    	GeomPoint3d ptMid3d = GeomUtil.midPointOf(ptOrigI, ptOrigF);

    	CadEntity oFromEnt = oBlkDef.getEntity( this.getFromObjId() );
    	CadEntity oToEnt = oBlkDef.getEntity( this.getToObjId() );

    	//TODO!
    	
    	GeomVector3d vDir3d = new GeomVector3d(ptOrigI, ptOrigF);
    	double d = vDir3d.mod();

    	GeomVector3d uDir3d = vDir3d.otherUnit();
    	
        if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals(this.sectionType) ) {
        	//INTERNAL_DIAMETER
        	//
            double diamIntMeter = this.diameterMili / 1000.0;
            double radiusIntMeter = diamIntMeter / 2.0;

        	//EXTERNAL_DIAMETER
        	//
            double diamExtMeter = this.externalDiameterMili / 1000.0;
            double radiusExtMeter = diamExtMeter / 2.0;

    		//PIPE_UNION
    		//
            double thicknessMeter = this.thicknessMili / 1000.0;

    		prep.addPipeSectionCirc(v, this, c, ptOrigI, uDir3d, d, radiusIntMeter, radiusExtMeter, thicknessMeter);
        }
        else {
        	//INTERNAL_SIZE
        	//
            double widthIntMeter = this.widthMili / 1000.0;
            double heightIntMeter = this.heightMili / 1000.0;

        	//EXTERNAL_SIZE
        	//
            double widthExtMeter = this.externalWidthMili / 1000.0;
            double heightExtMeter = this.externalHeightMili / 1000.0;

    		//PIPE_UNION
    		//
            double thicknessMeter = this.thicknessMili / 1000.0;

            prep.addPipeSectionRect(v, this, c, ptOrigI, uDir3d, d, widthIntMeter, heightIntMeter, widthExtMeter, heightExtMeter, thicknessMeter);		
        }
	}

	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep)
	{
    	if( !this.isVisible() ) return;

    	int sz = this.lsPts.size();
		if(sz == 0) return;
		
		GeomPoint3d ptI3dMcs = new GeomPoint3d( this.lsPts.get(0) ); 
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF3dMcs = new GeomPoint3d( this.lsPts.get(i) );

			redraw3d_view(v, dist, ptI3dMcs, ptF3dMcs, sclFact, bDragMode, bSelEnt, prep);			
			ptI3dMcs = ptF3dMcs;
		}		
	}
	        
	/* SELECT */
	
	public boolean select2d_planView(ICadViewBase view2d, GeomPoint2d pt2dMcs, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, double sclFact, boolean bSelectEntity) 
	{
		GeomPoint3d ptOrigI = new GeomPoint3d( ptI3dMcs );
		GeomPoint3d ptOrigF = new GeomPoint3d( ptF3dMcs );
    	
        GeomPoint2d ptI2dMcs = new GeomPoint2d(ptOrigI);
        GeomPoint2d ptF2dMcs = new GeomPoint2d(ptOrigF);
        
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
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;

    	if(this.isSelected()) return true;

		if(pt2dMcs == null) return false;
		
    	int sz = this.lsPts.size();
		if(sz == 0) return false;
		
		GeomPoint3d ptI3dMcs = new GeomPoint3d( this.lsPts.get(0) ); 
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF3dMcs = new GeomPoint3d( this.lsPts.get(i) );

			boolean bResult = this.select2d_planView(view2d, pt2dMcs, ptI3dMcs, ptF3dMcs, sclFact, bSelectEntity); 
			if( bResult ) return true;

			ptI3dMcs = ptF3dMcs;
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

	public GeomPoint3d osnap3d_view(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, Graphics g) 
	{
		GeomPoint3d ptOrigI = new GeomPoint3d( ptI3dMcs );
		GeomPoint3d ptOrigF = new GeomPoint3d( ptF3dMcs );

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(ptOrigI) );
    	lsPtEndpoint.add( new GeomPoint3d(ptOrigF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(ptOrigI, ptOrigF);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	public ArrayList<GeomPoint3d> osnap3d_view(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
    	if( !this.isVisible() ) return null;

        int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return null;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );
    	
    	//ENDPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();    	
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptOrigI) );
    	lsPtEndpoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptOrigF) );
		
    	//MIDDLE
    	//
    	GeomPoint3d pt3dMid = GeomUtil.midPointOf(ptOrigI, ptOrigF);
    	pt3dMid.setTagId(AppDefs.OSNAPMODE_MIDDLE);
    	
    	ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();    	
    	lsPtMiddle.add(pt3dMid);
    	
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	lsResult.addAll(lsPtEndpoint);
    	lsResult.addAll(lsPtMiddle);
    	return lsResult;    	
	}
	
	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

		if(pt2dMcs == null) return null;
		
    	int sz = this.lsPts.size();
		if(sz == 0) return null;
		
		GeomPoint3d ptI3dMcs = new GeomPoint3d( this.lsPts.get(0) ); 
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF3dMcs = new GeomPoint3d( this.lsPts.get(i) );

			GeomPoint3d ptResult = this.osnap3d_view(view2d, osnapmode, pt2dMcs, ptI3dMcs, ptF3dMcs, g); 
			if(ptResult != null) return ptResult;

			ptI3dMcs = ptF3dMcs;
		}		
    	return null;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

		if(pt2dMcs == null) return null;
		
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();

    	int sz = this.lsPts.size();
		if(sz == 0) return null;
		
		GeomPoint3d ptI3dMcs = new GeomPoint3d( this.lsPts.get(0) ); 
		for(int i = 1; i < sz; i++) {
			GeomPoint3d ptF3dMcs = new GeomPoint3d( this.lsPts.get(i) );

			ArrayList<GeomPoint3d> lsParcialResult = this.osnap3d_view(view2d, osnapmode, pt2dMcs); 
			if(lsParcialResult != null) 
				lsResult.addAll( lsParcialResult );

			ptI3dMcs = ptF3dMcs;
		}		
    	return null;
	}

	/* CENTROID */	
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(0.0, 0.0, 0.0);

		int szLsPts = this.lsPts.size();
		if(szLsPts == 0) return ptResult;

		ptResult = new GeomPoint3d( this.lsPts.get(0) );
		if(szLsPts < 2) return ptResult;

		ptResult = GeomUtil.centroidOf3d( this.lsPts );
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
			    new Integer( numeroPipe ),
			    //
			    new Integer( fromObjId ),
			    new Integer( toObjId ),
			    //
			    new String( sectionType ),
			    new String( pipeCategory ),
			    //
				new Double( diameterMili ),
				new Double( widthMili ),
				new Double( heightMili ),
				new Double( thicknessMili ),
				new Double( maxPipeSegmentLength ),

			    /* PIPE_EXTERNAL_DIMENSION */
				new Double( externalDiameterMili ),
				new Double( externalWidthMili ),
				new Double( externalHeightMili )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadPipeLineRecord entRec = new CadPipeLineRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );
		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
    	if(this.isDeleted()) return lsDxfCadEntity;
		
		//ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		//lsDxfCadEntity.addAll( lsCadEntity2d );

		//ArrayList<DxfCadEntity> lsCadEntity3d = toDxfR12_view3d();
		//lsDxfCadEntity.addAll( lsCadEntity3d );
		
		return lsDxfCadEntity;
	}

	/* DXFR12_VIEW2D */
		
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
        int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return null;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );
    	
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 

		CadDocumentDef doc = this.getDocument();
		
		LayerTable layTbl = doc.getLayerTable(); 
		
        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(ptOrigI);
        GeomPoint2d ptDestF2dMcs = new GeomPoint2d(ptOrigF);
        
        double sclFact = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;

        // DETAIL_LEVEL_HIGH
        CadLayerDef oLayer_highDetailLeve_planView = layTbl.getLayerDefByRef(AppDefs.LAYER_RPD_TB_DRENAGEM_HD);
        CadLayerDef oLayer_highDetailLeve_annotation = layTbl.getLayerDefByRef(AppDefs.LAYER_RPD_TEXTOS_HD);
        
        if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals(this.sectionType) ) {
        	toDxfR12_sectionCircle_planView(oLayer_highDetailLeve_planView, this, ptDestI2dMcs, ptDestF2dMcs);
        }
        else {
        	toDxfR12_sectionRectangle_planView(oLayer_highDetailLeve_planView, this, ptDestI2dMcs, ptDestF2dMcs);
        }            
        toDxfR12_highDetailLevel_annotation_planView(oLayer_highDetailLeve_annotation, this, ptDestI2dMcs, ptDestF2dMcs, sclFact);
        toDxfR12_flowdir_planView(oLayer_highDetailLeve_annotation, this, ptDestI2dMcs, ptDestF2dMcs, sclFact);

        // DETAIL_LEVEL_LOW
        CadLayerDef oLayer_lowDetailLevel_planView = layTbl.getLayerDefByRef(AppDefs.LAYER_RPD_TB_DRENAGEM);
        CadLayerDef oLayer_lowDetailLevel_annotation = layTbl.getLayerDefByRef(AppDefs.LAYER_RPD_TEXTOS);
        
        toDxfR12_lowDetailLevel_planView(oLayer_lowDetailLevel_planView, this, ptDestI2dMcs, ptDestF2dMcs, sclFact);
        toDxfR12_lowDetailLevel_annotation_planView(oLayer_lowDetailLevel_annotation, this, ptDestI2dMcs, ptDestF2dMcs, sclFact);        
        toDxfR12_flowdir_planView(oLayer_lowDetailLevel_annotation, this, ptDestI2dMcs, ptDestF2dMcs, sclFact);
		
		return lsDxfCadEntity;
	}

	public ArrayList<DxfCadEntity> toDxfR12_flowdir_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);

        int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return null;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
		
		double arrowLengthMcs = AppDefs.ARROWLENGTHSZ_SMALL * sclFact;
		double arrowWidthMcs = AppDefs.ARROWWIDTHSZ_SMALL * sclFact;
		double arrowPointMcs = AppDefs.ARROWPOINTSZ_SMALL * sclFact;
		
		double textHeight = AppDefs.FONTSZ_SMALL * sclFact;
		
		double lineHeight = arrowWidthMcs * 3.0;
		
		double dist3d = ptOrigI.distTo(ptOrigF);

		double dX = ptOrigF.getX() - ptOrigI.getX();
		double dY = ptOrigF.getY() - ptOrigI.getY();
		double dZ = ptOrigF.getZ() - ptOrigI.getZ();

		GeomPoint2d ptI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptF = new GeomPoint2d(ptF2dMcs);
		if(dZ > 0.0) {
			ptI = new GeomPoint2d(ptF2dMcs);
			ptF = new GeomPoint2d(ptI2dMcs);			
		}
				
		GeomPoint2d ptTextI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptTextF = new GeomPoint2d(ptF2dMcs);
		if(dX < 0.0) {
			ptTextI = new GeomPoint2d(ptF2dMcs);
			ptTextF = new GeomPoint2d(ptI2dMcs);			
		}
		
		double declividadePerc = dZ / dist3d * 100.0; 
		
		String strDeclividadePerc = String.format("i = %s %%", nf1.format(declividadePerc));
		
		GeomVector2d vIF2d = new GeomVector2d(ptI, ptF); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();

		GeomVector2d vTextIF2d = new GeomVector2d(ptTextI, ptTextF); 
		
		GeomVector2d uTextIF2d = vTextIF2d.otherUnit();
		GeomVector2d nTextIF2d = uTextIF2d.otherNorm();

		double dist2d = ptI.distTo(ptF);		
		
		double d = dist2d / 6.0;

		double textRotation = uIF2d.angleToAxisX();
		
		GeomPoint2d ptArrowDeclividadePerc = ptI.otherMoveTo(uIF2d, d);		
		DxfUtil.toDxfArrow(oLayer, ptArrowDeclividadePerc, arrowLengthMcs, arrowWidthMcs, arrowPointMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, uIF2d);

		GeomPoint2d ptTextDeclividadePerc = ptArrowDeclividadePerc.otherMoveTo(nTextIF2d, - lineHeight);
		DxfUtil.toDxfText(oLayer, strDeclividadePerc, ptTextDeclividadePerc, textHeight, textRotation, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE);
		
		return lsCadEntity3d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_highDetailLevel_annotation_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		double textHeight = AppDefs.FONTSZ_SMALL * sclFact;
		
		double lineHeight = textHeight * 1.0;

		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return null;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
		
		double dist3d = ptOrigI.distTo(ptOrigF);

		double dX = ptOrigF.getX() - ptOrigI.getX();
		double dY = ptOrigF.getY() - ptOrigI.getY();
		double dZ = ptOrigF.getZ() - ptOrigI.getZ();
		
		GeomPoint2d ptTextI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptTextF = new GeomPoint2d(ptF2dMcs);
		if(dX < 0.0) {
			ptTextI = new GeomPoint2d(ptF2dMcs);
			ptTextF = new GeomPoint2d(ptI2dMcs);			
		}		
		
		double dimMeter = this.diameterMili / 1000.0;
		double dimMeter2 = dimMeter / 2.0;

		String strLengthMeter = String.format("L = %s m", nf2.format(dist3d));
		String strDiameterMeter = String.format("D = %s m", nf2.format(dimMeter));
        
		if( DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR.equals(this.sectionType) ) {
    		dimMeter = this.widthMili / 1000.0;
    		dimMeter2 = dimMeter / 2.0;        

    		double hMeter = this.heightMili / 1000.0;
    		strDiameterMeter = String.format("D = %s m X %s m", nf2.format(dimMeter), nf2.format(hMeter));
        }
		
		GeomVector2d vTextIF = new GeomVector2d(ptTextI, ptTextF); 
		
		GeomVector2d uTextIF = vTextIF.otherUnit();
		GeomVector2d nTextIF = uTextIF.otherNorm();

		GeomPoint2d ptCenter2dMcs = GeomUtil.midPointOf(ptTextI, ptTextF);

		double d = dimMeter + lineHeight;

		double textRotation = uTextIF.angleToAxisX();
		
		GeomPoint2d ptTextLengthMeter = ptCenter2dMcs.otherMoveTo(nTextIF, d);
		DxfUtil.toDxfText(oLayer, strLengthMeter, ptTextLengthMeter, textHeight, textRotation, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE);

		GeomPoint2d ptTextDiameterMeter = ptCenter2dMcs.otherMoveTo(nTextIF, - d);
		DxfUtil.toDxfText(oLayer, strDiameterMeter, ptTextDiameterMeter, textHeight, textRotation, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE);
		
		return lsCadEntity3d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_lowDetailLevel_annotation_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		double textHeight = AppDefs.FONTSZ_SMALL * sclFact;
		
		double lineHeight = textHeight * 1.25;

		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return null;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
				
		double dist3d = ptOrigI.distTo(ptOrigF);

		double dX = ptOrigF.getX() - ptOrigI.getX();
		double dY = ptOrigF.getY() - ptOrigI.getY();
		double dZ = ptOrigF.getZ() - ptOrigI.getZ();
		
		GeomPoint2d ptTextI = new GeomPoint2d(ptI2dMcs);
		GeomPoint2d ptTextF = new GeomPoint2d(ptF2dMcs);
		if(dX < 0.0) {
			ptTextI = new GeomPoint2d(ptF2dMcs);
			ptTextF = new GeomPoint2d(ptI2dMcs);			
		}		
		
		double dimMeter = this.diameterMili / 1000.0;
		double dimMeter2 = dimMeter / 2.0;

		String strLengthMeter = String.format("L = %s m", nf2.format(dist3d));
		String strDiameterMeter = String.format("D = %s m", nf2.format(dimMeter));
        
		if( DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR.equals(this.sectionType) ) {
    		dimMeter = this.widthMili / 1000.0;
    		dimMeter2 = dimMeter / 2.0;        

    		double hMeter = this.heightMili / 1000.0;
    		strDiameterMeter = String.format("D = %s m X %s m", nf2.format(dimMeter), nf2.format(hMeter));
        }
		
		GeomVector2d vTextIF = new GeomVector2d(ptTextI, ptTextF); 
		
		GeomVector2d uTextIF = vTextIF.otherUnit();
		GeomVector2d nTextIF = uTextIF.otherNorm();

		GeomPoint2d ptCenter2dMcs = GeomUtil.midPointOf(ptTextI, ptTextF);
		
		double d = lineHeight;

		double textRotation = uTextIF.angleToAxisX();
		
		GeomPoint2d ptTextLengthMeter = ptCenter2dMcs.otherMoveTo(nTextIF, d);
		DxfUtil.toDxfText(oLayer, strLengthMeter, ptTextLengthMeter, textHeight, textRotation, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE);

		GeomPoint2d ptTextDiameterMeter = ptCenter2dMcs.otherMoveTo(nTextIF, - d);
		DxfUtil.toDxfText(oLayer, strDiameterMeter, ptTextDiameterMeter, textHeight, textRotation, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE);
		
		return lsCadEntity3d;
	}
		
	public ArrayList<DxfCadEntity> toDxfR12_sectionCircle_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return lsCadEntity3d;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
				
		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();

		//PIPE_CENTER_LINE
		//
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_CENTER, ptI2dMcs, ptF2dMcs) );

		//INTERNAL_DIAMETER
		//
		double diamMeter = this.diameterMili / 1000.0;
		double radiusMeter = diamMeter / 2.0;
		
		GeomPoint2d ptI2dMcs_left = ptI2dMcs.otherMoveTo(nIF2d, radiusMeter);
		GeomPoint2d ptI2dMcs_right = ptI2dMcs.otherMoveTo(nIF2d, - radiusMeter);

		GeomPoint2d ptF2dMcs_left = ptF2dMcs.otherMoveTo(nIF2d, radiusMeter);
		GeomPoint2d ptF2dMcs_right = ptF2dMcs.otherMoveTo(nIF2d, - radiusMeter);
		
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_HIDDEN, ptI2dMcs_left, ptF2dMcs_left) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_HIDDEN, ptI2dMcs_right, ptF2dMcs_right) );
		
		//EXTERNAL_DIAMETER
		//
		double diamExtMeter = this.externalDiameterMili / 1000.0;
		double radiusExtMeter = diamExtMeter / 2.0;
		
		GeomPoint2d ptI2dMcs_left_ext = ptI2dMcs.otherMoveTo(nIF2d, radiusExtMeter);
		GeomPoint2d ptI2dMcs_right_ext = ptI2dMcs.otherMoveTo(nIF2d, - radiusExtMeter);

		GeomPoint2d ptF2dMcs_left_ext = ptF2dMcs.otherMoveTo(nIF2d, radiusExtMeter);
		GeomPoint2d ptF2dMcs_right_ext = ptF2dMcs.otherMoveTo(nIF2d, - radiusExtMeter);
		
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptI2dMcs_left_ext, ptF2dMcs_left_ext) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptF2dMcs_left_ext, ptF2dMcs_right_ext) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptF2dMcs_right_ext, ptI2dMcs_right_ext) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptI2dMcs_right_ext, ptI2dMcs_left_ext) );

		//PIPE_UNION
		//
        double thicknessMeter = this.thicknessMili / 1000.0;
		double radiusUnionMeter = radiusExtMeter + thicknessMeter;

		double dist3d = ptOrigI.distTo(ptOrigF);
		int nsegs = (int) Math.floor(dist3d / this.maxPipeSegmentLength); 

		double dist2d = ptI2dMcs.distTo(ptF2dMcs);
		
		double maxPipeSegmentLenght2d = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH * (dist2d / dist3d); 
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		//PIPE_SEGMENT_START_AND_END_POINTS
    		//
    		GeomPoint2d ptSegI2dMcs = ptI2dMcs.otherMoveTo(uIF2d, currDist);
    		GeomPoint2d ptSegF2dMcs = ptSegI2dMcs.otherMoveTo(uIF2d, thicknessMeter);

    		//PIPE_UNION_POINTS
    		//
    		GeomPoint2d ptSegI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, radiusUnionMeter);
    		GeomPoint2d ptSegI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - radiusUnionMeter);

    		GeomPoint2d ptSegF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, radiusUnionMeter);
    		GeomPoint2d ptSegF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - radiusUnionMeter);

    		//REMOVE_PIPE_LINES
    		//
    		ArrayList<GeomPoint2d> lsPts2dMcs = new ArrayList<GeomPoint2d>(); 
    		
    		lsPts2dMcs.add(ptSegI2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_right_union);
    		lsPts2dMcs.add(ptSegI2dMcs_right_union);
    		
    		//DRAW_PIPE_UNION_LINES
    		//
    		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_left_union, ptSegF2dMcs_left_union) );
    		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_left_union, ptSegF2dMcs_right_union) );
    		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_right_union, ptSegI2dMcs_right_union) );
    		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_right_union, ptSegI2dMcs_left_union) );
            
            currDist += maxPipeSegmentLenght2d;
        }
        return lsCadEntity3d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_sectionRectangle_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return lsCadEntity3d;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
						
		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
		
		//INTERNAL_DIAMETER
		//
		double widthMeter = this.widthMili / 1000.0;
		double widthMeter2 = widthMeter / 2.0;

		double heightMeter = this.heightMili / 1000.0;
		
		GeomPoint2d ptI2dMcs_left = ptI2dMcs.otherMoveTo(nIF2d, widthMeter2);
		GeomPoint2d ptI2dMcs_right = ptI2dMcs.otherMoveTo(nIF2d, - widthMeter2);

		GeomPoint2d ptF2dMcs_left = ptF2dMcs.otherMoveTo(nIF2d, widthMeter2);
		GeomPoint2d ptF2dMcs_right = ptF2dMcs.otherMoveTo(nIF2d, - widthMeter2);
		
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptI2dMcs_left, ptF2dMcs_left) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptI2dMcs_right, ptF2dMcs_right) );
        
		//EXTERNAL_DIAMETER
		//
		double widthExtMeter = this.externalWidthMili / 1000.0;
		double widthExtMeter2 = widthExtMeter / 2.0;
		
		GeomPoint2d ptI2dMcs_left_ext = ptI2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
		GeomPoint2d ptI2dMcs_right_ext = ptI2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

		GeomPoint2d ptF2dMcs_left_ext = ptF2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
		GeomPoint2d ptF2dMcs_right_ext = ptF2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptI2dMcs_left_ext, ptF2dMcs_left_ext) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptF2dMcs_left_ext, ptF2dMcs_right_ext) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptF2dMcs_right_ext, ptI2dMcs_right_ext) );
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptI2dMcs_right_ext, ptI2dMcs_left_ext) );

		//PIPE_UNION
		//
        double thicknessMeter = this.thicknessMili / 1000.0;
		double widthUnionMeter2 = widthExtMeter2 + thicknessMeter;

		double dist3d = ptOrigI.distTo(ptOrigF);
		int nsegs = (int) Math.floor(dist3d / this.maxPipeSegmentLength); 

		double dist2d = ptI2dMcs.distTo(ptF2dMcs);
		
		double maxPipeSegmentLenght2d = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH * (dist2d / dist3d); 
		
        double currDist = 0.0;
        for(int i = 0; i <= nsegs; i++) {
    		GeomPoint2d ptSegI2dMcs = ptI2dMcs.otherMoveTo(uIF2d, currDist);
    		GeomPoint2d ptSegF2dMcs = ptSegI2dMcs.otherMoveTo(uIF2d, - thicknessMeter);
            
    		//INTERNAL
    		//
    		GeomPoint2d ptSegIntI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, widthMeter2);
    		GeomPoint2d ptSegIntI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - widthMeter2);
            
    		GeomPoint2d ptSegIntF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, widthMeter2);
    		GeomPoint2d ptSegIntF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - widthMeter2);
            
    		//EXTERNAL
    		//
    		GeomPoint2d ptSegExtI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
    		GeomPoint2d ptSegExtI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

    		GeomPoint2d ptSegExtF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, widthExtMeter2);
    		GeomPoint2d ptSegExtF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - widthExtMeter2);

    		//FLANGE-TO-FLANGE
    		//
    		GeomPoint2d ptSegI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, widthUnionMeter2);
    		GeomPoint2d ptSegI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - widthUnionMeter2);

    		GeomPoint2d ptSegF2dMcs_left_union = ptSegF2dMcs.otherMoveTo(nIF2d, widthUnionMeter2);
    		GeomPoint2d ptSegF2dMcs_right_union = ptSegF2dMcs.otherMoveTo(nIF2d, - widthUnionMeter2);

    		//REMOVE_PIPE_LINES
    		//
    		ArrayList<GeomPoint2d> lsPts2dMcs = new ArrayList<GeomPoint2d>(); 
    		
    		lsPts2dMcs.add(ptSegI2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_left_union);
    		lsPts2dMcs.add(ptSegF2dMcs_right_union);
    		lsPts2dMcs.add(ptSegI2dMcs_right_union);
    		
    		//DRAW_PIPE_UNION_LINES
    		//
    		if(currDist < AppDefs.MATHPREC_MIN) {
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_left_union, ptSegI2dMcs_left_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_left_union, ptSegExtI2dMcs_left_union) );
                //
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_left_union, ptSegF2dMcs_right_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegIntI2dMcs_left_union, ptSegIntI2dMcs_right_union) );
        		//
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_right_union, ptSegI2dMcs_right_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_right_union, ptSegExtI2dMcs_right_union) );
    		}
    		else {
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegIntI2dMcs_left_union, ptSegIntF2dMcs_left_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegIntF2dMcs_left_union, ptSegF2dMcs_left_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_left_union, ptSegI2dMcs_left_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_left_union, ptSegExtI2dMcs_left_union) );
                //
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegIntI2dMcs_left_union, ptSegIntI2dMcs_right_union) );
        		//
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegIntI2dMcs_right_union, ptSegIntF2dMcs_right_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegIntF2dMcs_right_union, ptSegF2dMcs_right_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegF2dMcs_right_union, ptSegI2dMcs_right_union) );
    			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_right_union, ptSegExtI2dMcs_right_union) );
    		}
    		
            currDist += maxPipeSegmentLenght2d;
        }
        return lsCadEntity3d;
	}
		
	//LOW_DETAIL_LEVEL
	//
	public ArrayList<DxfCadEntity> toDxfR12_lowDetailLevel_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return lsCadEntity3d;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
						
		double lineWidth = AppDefs.LINEWIDTH_SMALL * sclFact;
		
		GeomVector2d vIF2d = new GeomVector2d(ptI2dMcs, ptF2dMcs); 
		
		GeomVector2d uIF2d = vIF2d.otherUnit();
		GeomVector2d nIF2d = uIF2d.otherNorm();
	
		//PIPE_CENTER_LINE
		//
		lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, AppDefs.DEF_DXFCOLOR_BYLAYER, AppDefs.DEF_DXFLTYPE_CENTER, ptI2dMcs, ptF2dMcs) );
		
		//PIPE_UNION
		//
	    double thicknessMeter = this.thicknessMili / 1000.0;
	    double thicknessMeter2 = thicknessMeter / 2.0;
	
		double dist3d = ptOrigI.distTo(ptOrigF);
		int nsegs = (int) Math.floor(dist3d / this.maxPipeSegmentLength); 
	
		double dist2d = ptI2dMcs.distTo(ptF2dMcs);
		
		double maxPipeSegmentLenght2d = AppDefs.DEF_MAX_PIPE_SEGMENT_LENGTH * (dist2d / dist3d); 
		
	    double currDist = 0.0;
	    for(int i = 0; i <= nsegs; i++) {
			//PIPE_SEGMENT_START_AND_END_POINTS
			//
			GeomPoint2d ptSegI2dMcs = ptI2dMcs.otherMoveTo(uIF2d, currDist);
	
			//PIPE_UNION_POINTS
			//
			GeomPoint2d ptSegI2dMcs_left_union = ptSegI2dMcs.otherMoveTo(nIF2d, thicknessMeter2);
			GeomPoint2d ptSegI2dMcs_right_union = ptSegI2dMcs.otherMoveTo(nIF2d, - thicknessMeter2);
	
			//DRAW_PIPE_UNION_LINES
			//
			lsCadEntity3d.addAll( DxfUtil.toDxfLine(oLayer, ptSegI2dMcs_left_union, ptSegI2dMcs_right_union) );
	        
	        currDist += maxPipeSegmentLenght2d;
	    }
        return lsCadEntity3d;
	}

	/* DXFR12_VIEW3D */		
		
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

		CadDocumentDef doc = this.getDocument();
		
		LayerTable layTbl = doc.getLayerTable(); 

        CadLayerDef oLayer = layTbl.getLayerDefByRef(AppDefs.LAYER_RPD_TB_DRENAGEM_3D);

		int szLsPts = this.lsPts.size();
		if(szLsPts < 2) return lsCadEntity3d;

		GeomPoint3d ptOrigI = new GeomPoint3d( this.lsPts.get(0) );
		GeomPoint3d ptOrigF = new GeomPoint3d( this.lsPts.get(1) );		
				
    	GeomVector3d vDir3d = new GeomVector3d(ptOrigI, ptOrigF);
    	double d = vDir3d.mod();

    	GeomVector3d uDir3d = vDir3d.otherUnit();
    	
        if( DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR.equals(this.sectionType) ) {
            //INTERNAL_DIAMETER
        	//
            double diamIntMeter = this.diameterMili / 1000.0;
            double radiusIntMeter = diamIntMeter / 2.0;

        	//EXTERNAL_DIAMETER
        	//
            double diamExtMeter = this.externalDiameterMili / 1000.0;
            double radiusExtMeter = diamExtMeter / 2.0;

    		//PIPE_UNION
    		//
            double thicknessMeter = this.thicknessMili / 1000.0;
            
            lsCadEntity3d.addAll( DxfUtil.toDxfPipeSectionCirc(oLayer, this, ptOrigI, uDir3d, d, radiusIntMeter, radiusExtMeter, thicknessMeter) );
        }
        else {
        	//INTERNAL_SIZE
        	//
            double widthIntMeter = this.widthMili / 1000.0;
            double heightIntMeter = this.heightMili / 1000.0;

        	//EXTERNAL_SIZE
        	//
            double widthExtMeter = this.externalWidthMili / 1000.0;
            double heightExtMeter = this.externalHeightMili / 1000.0;

    		//PIPE_UNION
    		//
            double thicknessMeter = this.thicknessMili / 1000.0;

            lsCadEntity3d.addAll( DxfUtil.toDxfPipeSectionRect(oLayer, this, ptOrigI, uDir3d, d, widthIntMeter, heightIntMeter, widthExtMeter, heightExtMeter, thicknessMeter) );		
        }

    	return lsCadEntity3d;
	}
	
	/* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
		//CHECK_IFSIZE_ISZERO
		//
		GeomPoint3d ptMin = AppDefs.NULL_GEOMPOINT3D;
		GeomPoint3d ptMax = AppDefs.NULL_GEOMPOINT3D;

		GeomDimension3d oDim = new GeomDimension3d(ptMin, ptMax); 

		int szLsPts = this.lsPts.size();
		if(szLsPts == 0) return oDim;

		//CHECK_IFSIZE_ISLESSTHAN2
		//
		ptMin = new GeomPoint3d( this.lsPts.get(0) );
		ptMax = new GeomPoint3d( ptMin );
		
		oDim = new GeomDimension3d(ptMin, ptMax); 

		if(szLsPts < 2) return oDim;

		//PT-MIN
		//
		double xPtMin = ptMin.getX(); 
		double yPtMin = ptMin.getY(); 
		double zPtMin = ptMin.getZ(); 

		//PT-MAX
		//
		double xPtMax = ptMax.getX(); 
		double yPtMax = ptMax.getY(); 
		double zPtMax = ptMax.getZ(); 
		
		for(GeomPoint3d pt3d : this.lsPts) {
			double xPt = pt3d.getX(); 
			double yPt = pt3d.getY(); 
			double zPt = pt3d.getZ(); 

			//PT-MIN
			//
			if(xPt < xPtMin) xPtMin = xPt; 
			if(yPt < yPtMin) yPtMin = yPt; 
			if(zPt < zPtMin) zPtMin = zPt; 

			//PT-MAX
			//
			if(xPt > xPtMax) xPtMax = xPt; 
			if(yPt > yPtMax) yPtMax = yPt; 
			if(zPt > zPtMax) zPtMax = zPt; 
		}

		ptMin = new GeomPoint3d(xPtMin, yPtMin, zPtMin);
		ptMax = new GeomPoint3d(xPtMax, yPtMax, zPtMax);
		
		oDim = new GeomDimension3d(ptMin, ptMax); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
		//CHECK_IFSIZE_ISZERO
		//
		GeomPoint2d ptMin = AppDefs.NULL_GEOMPOINT2D;
		GeomPoint2d ptMax = AppDefs.NULL_GEOMPOINT2D;

		GeomDimension2d oDim = new GeomDimension2d(ptMin, ptMax); 

		int szLsPts = this.lsPts.size();
		if(szLsPts == 0) return oDim;

		//CHECK_IFSIZE_ISLESSTHAN2
		//
		ptMin = new GeomPoint2d( this.lsPts.get(0) );
		ptMax = new GeomPoint2d( ptMin );
		
		oDim = new GeomDimension2d(ptMin, ptMax); 

		if(szLsPts < 2) return oDim;

		//PT-MIN
		//
		double xPtMin = ptMin.getX(); 
		double yPtMin = ptMin.getY(); 

		//PT-MAX
		//
		double xPtMax = ptMax.getX(); 
		double yPtMax = ptMax.getY(); 
		
		for(GeomPoint3d pt3d : this.lsPts) {
			double xPt = pt3d.getX(); 
			double yPt = pt3d.getY(); 

			//PT-MIN
			//
			if(xPt < xPtMin) xPtMin = xPt; 
			if(yPt < yPtMin) yPtMin = yPt; 

			//PT-MAX
			//
			if(xPt > xPtMax) xPtMax = xPt; 
			if(yPt > yPtMax) yPtMax = yPt; 
		}

		ptMin = new GeomPoint2d(xPtMin, yPtMin);
		ptMax = new GeomPoint2d(xPtMax, yPtMax);
		
		oDim = new GeomDimension2d(ptMin, ptMax); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" + 
			Integer.toString( this.fromObjId ) + "^" + 
			Integer.toString( this.toObjId ) + "^" + 
			"DIAMETRO=" + Double.toString( this.diameterMili ) +
			"RAIO=" + Double.toString( this.diameterMili / 2.0 );
		return searchString;
	}

	public double getDiameterMili() {
		return diameterMili;
	}

	public void setDiameterMili(double diameterMili) {
		this.diameterMili = diameterMili;
	}

	public int getNumeroPipe() {
		return numeroPipe;
	}

	public int getFromObjId() {
		return fromObjId;
	}

	public int getToObjId() {
		return toObjId;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getPipeCategory() {
		return pipeCategory;
	}

	public void setPipeCategory(String pipeCategory) {
		this.pipeCategory = pipeCategory;
	}

	public double getWidthMili() {
		return widthMili;
	}

	public void setWidthMili(double widthMili) {
		this.widthMili = widthMili;
	}

	public double getHeightMili() {
		return heightMili;
	}

	public void setHeightMili(double heightMili) {
		this.heightMili = heightMili;
	}

	public double getExternalDiameterMili() {
		return externalDiameterMili;
	}

	public void setExternalDiameterMili(double externalDiameterMili) {
		this.externalDiameterMili = externalDiameterMili;
	}

	public double getExternalWidthMili() {
		return externalWidthMili;
	}

	public void setExternalWidthMili(double externalWidthMili) {
		this.externalWidthMili = externalWidthMili;
	}

	public double getExternalHeightMili() {
		return externalHeightMili;
	}

	public void setExternalHeightMili(double externalHeightMili) {
		this.externalHeightMili = externalHeightMili;
	}

	public void setNumeroPipe(int numeroPipe) {
		this.numeroPipe = numeroPipe;
	}

	public void setFromObjId(int fromObjId) {
		this.fromObjId = fromObjId;
	}

	public void setToObjId(int toObjId) {
		this.toObjId = toObjId;
	}

	public double getMaxPipeSegmentLength() {
		return maxPipeSegmentLength;
	}

	public void setMaxPipeSegmentLength(double maxPipeSegmentLength) {
		this.maxPipeSegmentLength = maxPipeSegmentLength;
	}

	public double getThicknessMili() {
		return thicknessMili;
	}

	public void setThicknessMili(double thicknessMili) {
		this.thicknessMili = thicknessMili;
	}

	public ArrayList<GeomPoint3d> getLsPts() {
		return lsPts;
	}

	public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}

}
