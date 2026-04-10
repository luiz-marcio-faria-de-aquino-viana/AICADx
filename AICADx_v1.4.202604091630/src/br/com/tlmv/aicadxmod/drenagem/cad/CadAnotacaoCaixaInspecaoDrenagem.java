/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAnotacaoCaixaInspecaoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/04/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
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
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadAnotacaoCaixaInspecaoDrenagemRecord;

public class CadAnotacaoCaixaInspecaoDrenagem extends CadEntity 
{
//Private
	CadCaixaInspecaoDrenagem entI;
	GeomPoint3d ptIns;
	
//Public

    public CadAnotacaoCaixaInspecaoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRANOTACAOCAIXAINSPECAO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	private void init(CadCaixaInspecaoDrenagem entI, GeomPoint3d ptIns) {
		this.entI = entI;
		this.ptIns = ptIns;
	}
	
	@Override
	public void init(ICadObject o) {
		CadAnotacaoCaixaInspecaoDrenagem other = (CadAnotacaoCaixaInspecaoDrenagem)o; 
		
		CadCaixaInspecaoDrenagem entI = other.entI;
		GeomPoint3d ptIns = other.ptIns;
		
		this.init(entI, ptIns);
	}
	
	/* CREATE */
	
	public static CadAnotacaoCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, CadCaixaInspecaoDrenagem entI, GeomPoint3d ptIns) {
    	CadAnotacaoCaixaInspecaoDrenagem o = new CadAnotacaoCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(entI, ptIns);
    	return o;
    }
	
	public static CadAnotacaoCaixaInspecaoDrenagem create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, CadCaixaInspecaoDrenagem entI, GeomPoint3d ptIns, boolean bLocked) {
    	CadAnotacaoCaixaInspecaoDrenagem o = new CadAnotacaoCaixaInspecaoDrenagem(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(entI, ptIns);
    	return o;
    }
	
	public static CadAnotacaoCaixaInspecaoDrenagem create(CadAnotacaoCaixaInspecaoDrenagem other) {
    	CadAnotacaoCaixaInspecaoDrenagem o = new CadAnotacaoCaixaInspecaoDrenagem(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadAnotacaoCaixaInspecaoDrenagem create(CadBlockDef blkDef, CadAnotacaoCaixaInspecaoDrenagem other) {
    	CadAnotacaoCaixaInspecaoDrenagem o = new CadAnotacaoCaixaInspecaoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadAnotacaoCaixaInspecaoDrenagem duplicate()
	{
		CadAnotacaoCaixaInspecaoDrenagem other = CadAnotacaoCaixaInspecaoDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadAnotacaoCaixaInspecaoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadAnotacaoCaixaInspecaoDrenagem other = CadAnotacaoCaixaInspecaoDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadAnotacaoCaixaInspecaoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return this;
	}

	@Override
	public CadAnotacaoCaixaInspecaoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadAnotacaoCaixaInspecaoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
    @Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		return this;
	}
	
	@Override
	public CadAnotacaoCaixaInspecaoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
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
	
    public void redraw2d_anotation(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, GeomPoint2d ptIns, double sclFact, boolean bDragMode, Graphics g) 
    {
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	double diam = entI.getDiametroMeter() * 100.0;		// centimetros
    	double raio = diam / 2.0;
    	
    	double d = raio / 100.0;
    	
    	String strPV = entI.getPv();
    	String strEstaca = entI.getEstaca();
    	String strDiametro = String.format("%s cm", nf0.format(diam) );
    	String strCT = String.format("%s m", nf3.format(entI.getCt()) );
    	String strCB = String.format("%s m", nf3.format(entI.getCb()) );
    	String strProf = String.format("%s m", nf3.format(entI.getProfundidade()) );
    	String strAreaExterna = String.format("%s ha", nf3.format(entI.getAreaExterna()) );
    	String strAreaLocal = String.format("%s ha", nf3.format(entI.getAreaLocal()) );
    	String strAreaTotal = String.format("%s ha", nf3.format(entI.getAreaTotal()) );
    	
    	GeomPlan2d planMcs = v.getPlanMcs2d();
    	
    	GeomVector2d axisX = planMcs.getAxisX();

    	GeomVector2d axisY = planMcs.getAxisY();
    	
    	GeomPoint2d ptCI = new GeomPoint2d(entI.getPtIns());

    	double textWidthMcs = AppDefs.TBL_ANNOTATION_TEXT_SIZE * sclFact * 0.75;
    	double textHeightMcs = AppDefs.TBL_ANNOTATION_TEXT_SIZE * sclFact;

    	double textWidthMcs2 = textWidthMcs / 2.0;
    	double textHeightMcs2 = textHeightMcs / 2.0;
    	
    	double lineHeightMcs = 1.5 * textHeightMcs;
    	double lineHeightMcs2 = lineHeightMcs / 2.0;
    	
    	double boxWidth = 15.0 * sclFact;
    	double boxWidth2 = boxWidth / 2.0;

    	double boxHeight = 7.0 * lineHeightMcs;
    	double boxHeight2 = boxHeight / 2.0;
    	
    	double arrowSizeMcs = lineHeightMcs * 1.0;
    	
    	GeomVector2d vDir = new GeomVector2d(ptCI, ptIns);
    	
    	GeomPoint2d ptBase = ptCI.otherMoveTo(vDir, d);
    	
    	DrawUtil.drawLineMcs(v, ptBase, ptIns, g);    	

    	GeomVector2d vDir_right = vDir.otherRotateToDegrees(- 10.0);
    	
    	GeomPoint2d ptBase_right = ptBase.otherMoveTo(vDir_right, arrowSizeMcs);    	

    	DrawUtil.drawLineMcs(v, ptBase, ptBase_right, g);    	
    	
    	GeomPoint2d pt1 = ptIns.otherMoveTo(axisY, boxHeight2); 
    	GeomPoint2d pt2 = pt1.otherMoveTo(axisX, boxWidth); 
    	GeomPoint2d pt3 = pt2.otherMoveTo(axisY, - boxHeight); 
    	GeomPoint2d pt4 = pt3.otherMoveTo(axisX, - boxWidth); 
    	
    	DrawUtil.drawLineMcs(v, pt1, pt2, g);
    	DrawUtil.drawLineMcs(v, pt2, pt3, g);    	
    	DrawUtil.drawLineMcs(v, pt3, pt4, g);
    	DrawUtil.drawLineMcs(v, pt4, pt1, g);

    	GeomPoint2d pt5 = pt1.otherMoveTo(axisY, - lineHeightMcs); 
    	GeomPoint2d pt6 = pt5.otherMoveTo(axisX, boxWidth); 

    	GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - lineHeightMcs); 
    	GeomPoint2d pt8 = pt7.otherMoveTo(axisX, boxWidth); 

    	GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - lineHeightMcs); 
    	GeomPoint2d pt10 = pt9.otherMoveTo(axisX, boxWidth); 

    	GeomPoint2d pt11 = pt9.otherMoveTo(axisY, - lineHeightMcs); 
    	GeomPoint2d pt12 = pt11.otherMoveTo(axisX, boxWidth); 

    	GeomPoint2d pt13 = pt11.otherMoveTo(axisY, - lineHeightMcs); 
    	GeomPoint2d pt14 = pt13.otherMoveTo(axisX, boxWidth); 

    	GeomPoint2d pt15 = pt13.otherMoveTo(axisY, - lineHeightMcs); 
    	GeomPoint2d pt16 = pt15.otherMoveTo(axisX, boxWidth); 

    	DrawUtil.drawLineMcs(v, pt5, pt6, g);
    	DrawUtil.drawLineMcs(v, pt7, pt8, g);    	
    	DrawUtil.drawLineMcs(v, pt9, pt10, g);    	
    	DrawUtil.drawLineMcs(v, pt11, pt12, g);    	
    	DrawUtil.drawLineMcs(v, pt13, pt14, g);    	
    	DrawUtil.drawLineMcs(v, pt15, pt16, g);    	
    	
    	GeomPoint2d pt1_mid = pt1.otherMoveTo(axisX, boxWidth2);

    	GeomPoint2d ptTxt0_val = pt1_mid.otherMoveTo(axisY, 1.5 * lineHeightMcs);
    	
    	DrawUtil.drawTextMcs(v, strPV, ptTxt0_val, textHeightMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

    	GeomPoint2d ptTxt1_val = ptTxt0_val.otherMoveTo(axisY, - lineHeightMcs);
    	
    	DrawUtil.drawTextMcs(v, strEstaca, ptTxt1_val, textHeightMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);

    	GeomPoint2d pt2_lbl = pt1.otherMoveTo(axisX, textWidthMcs2);
    	GeomPoint2d pt2_val = pt1.otherMoveTo(axisX, boxWidth2 - (textWidthMcs * 3.0));
    	
    	GeomPoint2d ptTxt2_lbl = pt2_lbl.otherMoveTo(axisY, - lineHeightMcs2 - textWidthMcs2);
    	GeomPoint2d ptTxt2_val = pt2_val.otherMoveTo(axisY, - lineHeightMcs2 - textWidthMcs2);
    	DrawUtil.drawTextMcs(v, "D", ptTxt2_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, " = " + strDiametro, ptTxt2_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	
    	GeomPoint2d ptTxt3_lbl = ptTxt2_lbl.otherMoveTo(axisY, - lineHeightMcs);
    	GeomPoint2d ptTxt3_val = ptTxt2_val.otherMoveTo(axisY, - lineHeightMcs);
    	DrawUtil.drawTextMcs(v, "CT", ptTxt3_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, " = " + strCT, ptTxt3_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	
    	GeomPoint2d ptTxt4_lbl = ptTxt3_lbl.otherMoveTo(axisY, - lineHeightMcs);
    	GeomPoint2d ptTxt4_val = ptTxt3_val.otherMoveTo(axisY, - lineHeightMcs);
    	DrawUtil.drawTextMcs(v, "CB", ptTxt4_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	DrawUtil.drawTextMcs(v, " = " + strCB, ptTxt4_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	
    	GeomPoint2d ptTxt5_lbl = ptTxt4_lbl.otherMoveTo(axisY, - lineHeightMcs);
    	GeomPoint2d ptTxt5_val = ptTxt4_val.otherMoveTo(axisY, - lineHeightMcs);
    	DrawUtil.drawTextMcs(v, "Prof.", ptTxt5_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	DrawUtil.drawTextMcs(v, " = " + strProf, ptTxt5_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	
    	GeomPoint2d ptTxt6_lbl = ptTxt5_lbl.otherMoveTo(axisY, - lineHeightMcs);
    	GeomPoint2d ptTxt6_val = ptTxt5_val.otherMoveTo(axisY, - lineHeightMcs);
    	DrawUtil.drawTextMcs(v, "Ext.", ptTxt6_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	DrawUtil.drawTextMcs(v, " = " + strAreaExterna, ptTxt6_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	
    	GeomPoint2d ptTxt7_lbl = ptTxt6_lbl.otherMoveTo(axisY, - lineHeightMcs);
    	GeomPoint2d ptTxt7_val = ptTxt6_val.otherMoveTo(axisY, - lineHeightMcs);
    	DrawUtil.drawTextMcs(v, "Local", ptTxt7_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	DrawUtil.drawTextMcs(v, " = " + strAreaLocal, ptTxt7_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	
    	GeomPoint2d ptTxt8_lbl = ptTxt7_lbl.otherMoveTo(axisY, - lineHeightMcs);
    	GeomPoint2d ptTxt8_val = ptTxt7_val.otherMoveTo(axisY, - lineHeightMcs);
    	DrawUtil.drawTextMcs(v, "Total", ptTxt8_lbl, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
    	DrawUtil.drawTextMcs(v, " = " + strAreaTotal, ptTxt8_val, textHeightMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);    	
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
        
        GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.getPtIns());
        
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
		        	CadAnotacaoCaixaInspecaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		            
		            ptDestI2dMcs = new GeomPoint2d(other.getPtIns());
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadAnotacaoCaixaInspecaoDrenagem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		            
		            ptDestI2dMcs = new GeomPoint2d(other.getPtIns());
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadAnotacaoCaixaInspecaoDrenagem other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			            
			            ptDestI2dMcs = new GeomPoint2d(other.getPtIns());
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadAnotacaoCaixaInspecaoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		            
		            ptDestI2dMcs = new GeomPoint2d(other.getPtIns());
		        }
	        }
        }
        
        GeomPoint2d ptIns2d = new GeomPoint2d(ptDestI2dMcs);

        this.redraw2d_anotation(v, dist, ptBase2dMcs, pt2dMcs, ptIns2d, sclFact, bDragMode, g);

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
		
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	String strDiametro = nf0.format(entI.getDiametroMeter());
    	int szMax = strDiametro.length();

    	String strCT = nf3.format(entI.getCt());
    	if(strCT.length() > szMax)
    		szMax = strCT.length();

    	String strCB = nf3.format(entI.getCb());
    	if(strCB.length() > szMax)
    		szMax = strCB.length();

    	double chHeightMcs = 2.0 * AppDefs.MCSPLAN_SCALEFACTOR;
    	double lineHeightMcs = 1.5 * chHeightMcs;

    	double chWidthMcs = 1.5 * AppDefs.MCSPLAN_SCALEFACTOR;

    	double hMcs = 4 * (chHeightMcs + lineHeightMcs);
    	double wMcs = szMax * chWidthMcs;
    	
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distMax = boxSz / 2.0;

	    GeomPoint2d ptIns2dMcs = new GeomPoint2d(this.ptIns);
    	GeomPoint2d ptBase2dMcs = new GeomPoint2d(this.entI.getPtIns());

    	double xMinMcs = ptIns2dMcs.getX();
    	double yMinMcs = ptIns2dMcs.getY();
    	
    	double xMaxMcs = ptIns2dMcs.getX() + wMcs;
    	double yMaxMcs = ptIns2dMcs.getY() + hMcs;

    	double xMcs = pt2dMcs.getX();
    	double yMcs = pt2dMcs.getY();

	    // LIMITES
	    double xMin = xMinMcs - distMax;
	    double yMin = yMinMcs - distMax;

	    double xMax = xMaxMcs + distMax;
	    double yMax = yMaxMcs + distMax;

		if( (xMcs >= xMin) && (xMcs <= xMax) &&
			(yMcs >= yMin) && (yMcs <= yMax) ) 
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
    	if( !this.isVisible() ) return null;
        
    	return null;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;
 
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
    	return lsResult;
	}

	/* CENTROID */	
	
	@Override
	public GeomPoint3d centroid()
	{
    	//ENDPOINT
    	//
        GeomPoint3d pt3dI = new GeomPoint3d(this.entI.getPtIns());
		return pt3dI;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.add( new ItemDataVO("Numero CI", Integer.toString(this.entI.getObjectId()), false) );
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"EntI:%s;PtIns:%s", 
			this.entI.getObjectId(),
			this.ptIns.toStr());
		return str;
	}
	
	@Override
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
		
		int numeroCI = this.entI.getObjectId();
		
		Object[] arrVal = {
			new Integer( numeroCI ),
			//
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadAnotacaoCaixaInspecaoDrenagemRecord entRec = new CadAnotacaoCaixaInspecaoDrenagemRecord(this); 
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
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		lsDxfCadEntity.addAll( lsCadEntity2d );
		
		return lsDxfCadEntity;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}
	
	/* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() {
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	String strDiametro = nf0.format(entI.getDiametroMeter());
    	int szMax = strDiametro.length();

    	String strCT = nf3.format(entI.getCt());
    	if(strCT.length() > szMax)
    		szMax = strCT.length();

    	String strCB = nf3.format(entI.getCb());
    	if(strCB.length() > szMax)
    		szMax = strCB.length();

    	double chHeightMcs = 2.0 * AppDefs.MCSPLAN_SCALEFACTOR;
    	double lineHeightMcs = 1.5 * chHeightMcs;

    	double chWidthMcs = 1.5 * AppDefs.MCSPLAN_SCALEFACTOR;

    	double hMcs = 4 * (chHeightMcs + lineHeightMcs);
    	double wMcs = szMax * chWidthMcs;
    	
	    GeomPoint3d ptIns3dMcs = new GeomPoint3d(this.ptIns);
    	GeomPoint3d ptBase3dMcs = new GeomPoint3d(this.entI.getPtIns());

    	double xMinMcs = ptIns3dMcs.getX();
    	double yMinMcs = ptIns3dMcs.getY();
    	double zMinMcs = ptIns3dMcs.getZ();
    	
    	double xMaxMcs = ptIns3dMcs.getX() + wMcs;
    	double yMaxMcs = ptIns3dMcs.getY() + hMcs;
    	double zMaxMcs = ptIns3dMcs.getZ() + Math.max(wMcs, hMcs);

    	GeomPoint3d ptMin3d = new GeomPoint3d(xMinMcs, yMinMcs, zMinMcs);
    	GeomPoint3d ptMax3d = new GeomPoint3d(xMaxMcs, yMaxMcs, zMaxMcs);
    	
		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d); 
		return oDim;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() {
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	String strDiametro = nf0.format(entI.getDiametroMeter());
    	int szMax = strDiametro.length();

    	String strCT = nf3.format(entI.getCt());
    	if(strCT.length() > szMax)
    		szMax = strCT.length();

    	String strCB = nf3.format(entI.getCb());
    	if(strCB.length() > szMax)
    		szMax = strCB.length();

    	double chHeightMcs = 2.0 * AppDefs.MCSPLAN_SCALEFACTOR;
    	double lineHeightMcs = 1.5 * chHeightMcs;

    	double chWidthMcs = 1.5 * AppDefs.MCSPLAN_SCALEFACTOR;

    	double hMcs = 4 * (chHeightMcs + lineHeightMcs);
    	double wMcs = szMax * chWidthMcs;
    	
	    GeomPoint2d ptIns2dMcs = new GeomPoint2d(this.ptIns);
    	GeomPoint2d ptBase2dMcs = new GeomPoint2d(this.entI.getPtIns());

    	double xMinMcs = ptIns2dMcs.getX();
    	double yMinMcs = ptIns2dMcs.getY();
    	
    	double xMaxMcs = ptIns2dMcs.getX() + wMcs;
    	double yMaxMcs = ptIns2dMcs.getY() + hMcs;

    	GeomPoint2d ptMin2d = new GeomPoint2d(xMinMcs, yMinMcs);
    	GeomPoint2d ptMax2d = new GeomPoint2d(xMaxMcs, yMaxMcs);
    	
		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"TIPO=" + this.entI.getTipoCI() + "^" +
			"SUBTIPO=" + this.entI.getSubtipoCI() + "^" +
			"PV=" + this.entI.getPv() + "^" +
			"AREA_EXTERNA=" + Double.toString( this.entI.getAreaExterna() ) + "^" +
			"AREA_LOCAL=" + Double.toString( this.entI.getAreaLocal() ) + "^" +
			"AREA_TOTAL=" + Double.toString( this.entI.getAreaTotal() ) + "^" +
			"COTA_TERRENO=" + Double.toString( this.entI.getCt() ) + "^" +
			"COTA_FUNDO=" + Double.toString( this.entI.getCb() ) + "^" +
			"PROFUNDIDADE=" + Double.toString( this.entI.getProfundidade() ) + "^" +
			"DECLIVIDADE=" + Double.toString( this.entI.getDeclividade() ) + "^" +
			"RAIO=" + Double.toString( this.entI.getDiametroMeter() / 2.0 ) + "^" +
			"DIAMETRO=" + Double.toString( this.entI.getDiametroMeter() );
		return searchString;
	}

	public CadEntity getEntI() {
		return this.entI;
	}

	public GeomPoint3d getPtIns() {
		return this.ptIns;
	}

}
