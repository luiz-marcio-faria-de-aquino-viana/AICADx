/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMargem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/01/2026
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
import br.com.tlmv.aicadxapp.dao.record.CadMargemRecord;
import br.com.tlmv.aicadxapp.dao.record.CadParamMargemODataRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadMargem extends CadEntity
{
//Private
    private Shape shape = null;
    private GeomPoint3d ptIns = null;
    private double rotate = 0.0;
    private double width = 0.0;
    private double height = 0.0;
    //
    private ArrayList<CadParamMargemOData> lsParamMargem = null; 
    
//Public

    public CadMargem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MARGEM, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(GeomPoint2d pt, double rotate, double width, double height, Shape shape) {
		this.init(pt.getX(), pt.getY(), 0.0, rotate, width, height, shape);
	}
	
	private void init(GeomPoint3d pt, double rotate, double width, double height, Shape shape) {
		this.init(pt.getX(), pt.getY(), pt.getZ(), rotate, width, height, shape);
	}

	public void init(double x, double y, double z, double rotate, double width, double height, Shape shape) {
    	this.ptIns = new GeomPoint3d(x, y, z);
    	this.shape = new Shape(shape);
    	this.rotate = rotate;
    	this.width = width;
    	this.height = height;
    	
    	ArrayList<CadParamMargemOData> lsSrcParam = shape.getLsParamMargem();
    	int szLsSrcParam = lsSrcParam.size();
    	
    	this.lsParamMargem = new ArrayList<CadParamMargemOData>();
    	for(int i = 0; i < szLsSrcParam; i++) {
    		CadParamMargemOData oParamMargem = new CadParamMargemOData( lsSrcParam.get(i) );
    		
    		String cadRefEntityId = Integer.toString( this.getObjectId() );
    		oParamMargem.setCadRefEntityId( cadRefEntityId );

    		this.lsParamMargem.add(oParamMargem);
    	}
    }

	@Override
	public void init(ICadObject other) {
		CadMargem oEnt = (CadMargem)other;
		this.init(oEnt.ptIns, oEnt.rotate, oEnt.width, oEnt.height, oEnt.shape);
	}

	/* CREATE */
		
	public static CadMargem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint2d pt, double rotate, double width, double height, Shape shape) {
    	CadMargem o = new CadMargem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pt, rotate, width, height, shape);
    	return o;
    }
	
	public static CadMargem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, GeomPoint3d pt, double rotate, double width, double height, Shape shape) {
    	CadMargem o = new CadMargem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(pt, rotate, width, height, shape);
    	return o;
    }

	public static CadMargem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double x, double y, double z, double rotate, double width, double height, Shape shape) {
    	CadMargem o = new CadMargem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(x, y, z, rotate, width, height, shape);
    	return o;
    }
	
	public static CadMargem create(CadMargem other) {
		CadMargem o = new CadMargem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadMargem create(CadBlockDef blkDef, CadMargem other) {
		CadMargem o = new CadMargem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadMargem duplicate()
	{
		CadMargem other = CadMargem.create(this);
		return other;
	}
	
	@Override
	public CadMargem duplicate(CadBlockDef blkDef)
	{
		CadMargem other = CadMargem.create(blkDef, this);
		return other;
	}

	@Override
	public CadMargem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadMargem other = CadMargem.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadMargem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	@Override
	public CadMargem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptIns);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptIns = new GeomPoint3d(o.getPtDest());
		return this;
	}
    
	@Override
	public CadMargem mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadMargem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadMargem oPoint = copyTo(ptIMcs, ptFMcs);
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
    	    	
    	int szLsParamMargem = this.lsParamMargem.size();
    	if(szLsParamMargem > 0) 
    	{
			MainPanel panel = (MainPanel)MainPanel.getMainPanel();
    		ICompView compView = panel.getCurrView();

    		int attrmode = compView.getAttrmode();

    		GeomDimension2d oDim = this.getEnvelop2d();
    		
			double xIns = this.ptIns.getX() + (this.width - 185.0) * sclFact;
			double yIns = this.ptIns.getY();

			double rotateRad = GeomUtil.convertDegreesToRad(this.rotate);

			double biggerTextSzMcs = AppDefs.FONTSZ_BIGGER * sclFact;

			double bigTextSzMcs = AppDefs.FONTSZ_BIG * sclFact;

			double smallTextSzMcs = AppDefs.FONTSZ_SMALL * sclFact;
			
			double lineHeightMcs = smallTextSzMcs * 2.5;
			double d = lineHeightMcs;
			
			double cellMargem = smallTextSzMcs;
			
			CadParamMargemOData oParam = this.lsParamMargem.get(0); 

			/* LABELS */
						
			//TITULO_PROJETO
			double newXIns = xIns + cellMargem;
			double newYIns = yIns + (60.0 * sclFact) - cellMargem;

			GeomPoint2d newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Projeto:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//DISCIPLINA_DESENHO
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Disciplina:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//DESCRICAO_DESENHO
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Descricao:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//RESPONSAVEL_TECNICO
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Resp. Tecnico:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//ESCALA
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Escala:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//DATA
			newXIns = newXIns + 45.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Emissao", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//DATA
			newXIns = newXIns + 45.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Revisao:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);

			//NUMERO_DESENHO
			newXIns = xIns + (185.0 - 50.0) * sclFact + cellMargem;
			newYIns = yIns + (60.0 * sclFact) - cellMargem;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, "Prancha:", newPtIns, smallTextSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
			
			/* VALORES */
			
			//TITULO_PROJETO
			newXIns = xIns + (135.0 / 2.0) * sclFact;
			newYIns = yIns + 55.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getTituloProjeto(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//DISCIPLINA_DESENHO
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getDisciplina(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//DESCRICAO_DESENHO
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getDescricaoDesenho(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//RESPONSAVEL_TECNICO
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getResponsavelTecnico(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//ESCALA
			newXIns = xIns + (45.0 / 2.0) * sclFact;
			newYIns = newYIns - 10.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getEscala(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//DATA
			newXIns = newXIns + 45.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getDataEmissao(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//DATA
			newXIns = newXIns + 45.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getNumeroRevisao(), newPtIns, bigTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

			//NUMERO_DESENHO
			newXIns = xIns + (185.0 - 30.0) * sclFact;
			newYIns = yIns + 35.0 * sclFact;

			newPtIns = new GeomPoint2d(newXIns, newYIns);
			DrawUtil.drawTextMcs(v, oParam.getNumeroDesenho(), newPtIns, biggerTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

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
		        	CadMargem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadMargem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadMargem other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadMargem other = this.duplicate();
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
    	
        prep.addShape3dMcs(v, this, c, this.ptIns, shape3d, sclFact, this.rotate, axisZ);
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
    	GeomPoint3d pt3d = new GeomPoint3d(this.ptIns);
    	
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(pt3d);
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	GeomPoint3d pt3d = new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, this.ptIns);
    	
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(pt3d);
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
		/* nothing todo! */
	}
		
	public synchronized int getSzLsParamMargem() {
		int sz = this.lsParamMargem.size();
		return sz;
	}

	public synchronized CadParamMargemOData getParamMargemAt(int pos) {
		int sz = this.lsParamMargem.size();
		if(pos < sz) {
			CadParamMargemOData o = this.lsParamMargem.get(pos);
			return o;
		}
		return null;
	}

	public synchronized void addParamMargem(CadParamMargemOData o) {
		this.lsParamMargem.add(o);
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lsdata(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odDao = dao.createODataDao(AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA); 

		String strCadRefEntityId = Integer.toString(this.getObjectId());

		int sz = this.lsParamMargem.size();
		for(int i = 0; i < sz; i++) {
			CadParamMargemOData o = (CadParamMargemOData)this.lsParamMargem.get(i);
			
			CadParamMargemODataRecord odataRec = new CadParamMargemODataRecord(o);				
			odataRec.setCadRefEntityId(strCadRefEntityId);
			odataRec.setObjVer(objVer);
			
			Object[] arrVal = {
				new Integer( odataRec.getParmNum() ),
			    new String( odataRec.getTituloProjeto() ),
			    new String( odataRec.getDisciplina() ),
			    new String( odataRec.getNumeroDesenho() ),
			    new String( odataRec.getDescricaoDesenho() ),
			    new String( odataRec.getResponsavelTecnico() ),
			    new String( odataRec.getEscala() ),
			    new String( odataRec.getDataEmissao() ),
			    new String( odataRec.getNumeroRevisao() )
			};
		
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
			new Double( this.rotate ),
			//
			new Double( this.width ),
			new Double( this.height )
 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadMargemRecord entRec = new CadMargemRecord(this); 
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

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public ArrayList<CadParamMargemOData> getLsParamMargem() {
		return lsParamMargem;
	}

	public void setLsParamMargem(ArrayList<CadParamMargemOData> lsParamMargem) {
		this.lsParamMargem = lsParamMargem;
	}

}
