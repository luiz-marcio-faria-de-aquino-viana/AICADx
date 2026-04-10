/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadDiagramaUnifilarEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 25/01/2026
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadDiagramaUnifilarEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.fiacao.FiacaoHelper;

public class CadDiagramaUnifilarEletrica extends CadEntity
{
//Private
	private CadQuadroCargasEletrica oEnt;
	private GeomPoint3d ptIns;

	private String nomeQuadro;
	private String descricaoQuadro;
	
    //FONT_SIZE
    private double fontTitleSzMili = AppDefs.FONTSZ_MEDIUM;
    private double fontCellSzMili = AppDefs.FONTSZ_SMALL;
    
//Public
    
    public CadDiagramaUnifilarEletrica(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODELDIAGRAMAUNIFILAR, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }

    /* Methodes */
    
	public void init(
		int quadroCargasId,
		//
		double ptInsX, 
		double ptInsY, 
		double ptInsZ, 
		//
		String nomeQuadro,
		String descricaoQuadro )
	{
		this.oEnt = oEnt;
		this.ptIns = ptIns;

		this.nomeQuadro = oEnt.getNomeQuadro();
		this.descricaoQuadro = oEnt.getDescricaoQuadro();
	}
    
	public void init(
		CadQuadroCargasEletrica oEnt,
		GeomPoint3d ptIns)
	{
		this.oEnt = oEnt;
		this.ptIns = ptIns;

		this.nomeQuadro = oEnt.getNomeQuadro();
		this.descricaoQuadro = oEnt.getDescricaoQuadro();
	}
	
	@Override
	public void init(ICadObject o) {
		CadDiagramaUnifilarEletrica other = (CadDiagramaUnifilarEletrica)o;
		
		this.init(
			other.oEnt,
			other.ptIns );
	}

	/* CREATE */
	
	public static CadDiagramaUnifilarEletrica create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		CadQuadroCargasEletrica oEnt,
		GeomPoint3d ptIns)
	{
		CadDiagramaUnifilarEletrica o = new CadDiagramaUnifilarEletrica(oBlkDef, oLayer, oLevel, 0.0, false);

		o.init(
			oEnt,
			ptIns);
    	return o;
    }
	
	public static CadDiagramaUnifilarEletrica create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		CadQuadroCargasEletrica oEnt,
		GeomPoint3d ptIns,
		boolean bLocked)
	{
		CadDiagramaUnifilarEletrica o = new CadDiagramaUnifilarEletrica(oBlkDef, oLayer, oLevel, 0.0, bLocked);

		o.init(
			oEnt,
			ptIns);
    	return o;
    }
	
	public static CadDiagramaUnifilarEletrica create(CadDiagramaUnifilarEletrica other)
	{
		CadDiagramaUnifilarEletrica o = new CadDiagramaUnifilarEletrica(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadDiagramaUnifilarEletrica create(CadBlockDef blkDef, CadDiagramaUnifilarEletrica other)
	{
		CadDiagramaUnifilarEletrica o = new CadDiagramaUnifilarEletrica(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadDiagramaUnifilarEletrica duplicate()
	{
		CadDiagramaUnifilarEletrica other = CadDiagramaUnifilarEletrica.create(this);
		return other;
	}
	
	@Override
	public CadDiagramaUnifilarEletrica duplicate(CadBlockDef blkDef)
	{
		CadDiagramaUnifilarEletrica other = CadDiagramaUnifilarEletrica.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadDiagramaUnifilarEletrica copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadDiagramaUnifilarEletrica other = CadDiagramaUnifilarEletrica.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadDiagramaUnifilarEletrica moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, this.ptIns);
    	this.ptIns = o.getPtDest();
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
    	return this;
	}
	
	@Override
	public CadDiagramaUnifilarEletrica scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadDiagramaUnifilarEletrica offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
				
		lsProperty.add( new ItemDataVO("Nome", this.nomeQuadro, true) );
		lsProperty.add( new ItemDataVO("Descricao", this.descricaoQuadro, true) );

		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String str = String.format(
			"ptIns:%s;" + 
			"nomeQuadro:%s;" + 
			"descricaoQuadro:%s;",
			this.ptIns.toStr(),
			this.nomeQuadro,
			this.descricaoQuadro );
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
	
	public GeomPoint2d redraw2d_draw_fios(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strLabel, int fios, Graphics g)
	{
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();

        double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double tickSz = AppDefs.TICKSZ_SMALL * sclFact;
		double tickDist = tickSz * 1.0;

		//DRAW_FIOS
		//
		double dist = FiacaoHelper.sizeOfFiosMcs(fios, tickDist);
		double dist2 = dist / 2.0;

		GeomPoint2d pt0 = ptIns2d.otherMoveTo(axisX, - dist2);
		
		GeomPoint2d ptResult2d = FiacaoHelper.drawFiosMcs(v, strLabel, fios, pt0, axisX, textSz, tickSz, tickDist, g);
		return ptResult2d;
	}
    
	public GeomPoint2d redraw2d_draw_barra_neutro(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, double iB, int n, Graphics g)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		GeomPlan2d planMcs = v.getPlanMcs2d();
	    
	    GeomVector2d axisX = planMcs.getAxisX();
	    GeomVector2d axisY = planMcs.getAxisY();
	    
	    double fCellSzMcs = this.fontCellSzMili * sclFact;
	    double hTextLineMcs = 1.5 * fCellSzMcs;
	    
	    double distCondutor = 1.25 * sclFact;
	    double raioCondutor = 1.25  * sclFact;	
	    double diametroCondutor = 2.0 * raioCondutor; 

	    double hBarMcs = distCondutor + diametroCondutor + distCondutor;
	    double wBarMcs = (n * (distCondutor + diametroCondutor)) + distCondutor;	    

	    // DRAW: BARRA_NEUTRO
	    //
	    double h2BarMcs = hBarMcs / 2.0;
	    //double w2BarMcs = wBarMcs / 2.0;	    

	    GeomPoint2d pt0 = ptIns2d.otherMoveTo(axisY, h2BarMcs);
	    GeomPoint2d pt1 = pt0.otherMoveTo(axisX,     wBarMcs);
	    GeomPoint2d pt2 = pt1.otherMoveTo(axisY,   - hBarMcs);
	    GeomPoint2d pt3 = pt2.otherMoveTo(axisX,   - wBarMcs);
	
	    DrawUtil.drawLineMcs(v, pt0, pt1, g);
	    DrawUtil.drawLineMcs(v, pt1, pt2, g);
	    DrawUtil.drawLineMcs(v, pt2, pt3, g);
	    DrawUtil.drawLineMcs(v, pt3, pt0, g);

	    // DRAW: CONDUTORES
	    //
	    GeomPoint2d pt4 = ptIns2d.otherMoveTo(axisX, (distCondutor + raioCondutor) );
	    for(int i = 0; i < n; i++) {
	        DrawUtil.drawCircleMcs(v, pt4, raioCondutor, g);
	        
	        if(i == n - 1 ) {
			    pt4 = pt4.otherMoveTo(axisX, (raioCondutor + distCondutor) );	        	        	
	        }
	        else {
			    pt4 = pt4.otherMoveTo(axisX, (raioCondutor + distCondutor + raioCondutor) );	        	        	
	        }
	    }
	    
	    // DRAW: TEXTOS 1
	    //
	    GeomPoint2d ptText1 = pt3.otherMoveTo(axisY, - hTextLineMcs);
	
	    String strIB = String.format("IBn = %s A", nf0.format(iB));
		DrawUtil.drawTextMcs(v, strIB, ptText1, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_TOP, g);
	    
	    // DRAW: TEXTOS 2
	    //
	    GeomPoint2d ptText2 = pt0.otherMoveTo(axisY, hTextLineMcs);
		DrawUtil.drawTextMcs(v, "BARRA DE NEUTRO", ptText2, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_BOTTOM, g);
		 
	    GeomPoint2d ptResult2d = ptIns2d.otherMoveTo(axisX, wBarMcs);
	    return ptResult2d;
	}
    
	public GeomPoint2d redraw2d_draw_barra_terra(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, double iB, int n, Graphics g)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		GeomPlan2d planMcs = v.getPlanMcs2d();
	    
	    GeomVector2d axisX = planMcs.getAxisX();
	    GeomVector2d axisY = planMcs.getAxisY();
	    
	    double fCellSzMcs = this.fontCellSzMili * sclFact;
	    double hTextLineMcs = 1.5 * fCellSzMcs;

	    double distCondutor = 1.25 * sclFact;
	    double raioCondutor = 1.25  * sclFact;	
	    double diametroCondutor = 2.0 * raioCondutor; 

	    double hBarMcs = distCondutor + raioCondutor + distCondutor;
	    double wBarMcs = (n * (distCondutor + diametroCondutor)) + distCondutor;	    

	    // DRAW: BARRA_NEUTRO
	    //
	    double h2BarMcs = hBarMcs / 2.0;
	    //double w2BarMcs = wBarMcs / 2.0;	    

	    GeomPoint2d pt0 = ptIns2d.otherMoveTo(axisY, h2BarMcs);
	    GeomPoint2d pt1 = pt0.otherMoveTo(axisX,     wBarMcs);
	    GeomPoint2d pt2 = pt1.otherMoveTo(axisY,   - hBarMcs);
	    GeomPoint2d pt3 = pt2.otherMoveTo(axisX,   - wBarMcs);
	
	    DrawUtil.drawLineMcs(v, pt0, pt1, g);
	    DrawUtil.drawLineMcs(v, pt1, pt2, g);
	    DrawUtil.drawLineMcs(v, pt2, pt3, g);
	    DrawUtil.drawLineMcs(v, pt3, pt0, g);

	    // DRAW: CONDUTORES
	    //
	    GeomPoint2d pt4 = ptIns2d.otherMoveTo(axisX, (distCondutor + raioCondutor) );
	    for(int i = 0; i < n; i++) {
	        DrawUtil.drawCircleMcs(v, pt4, raioCondutor, g);
	        
	        if(i == n - 1 ) {
			    pt4 = pt4.otherMoveTo(axisX, (raioCondutor + distCondutor) );	        	        	
	        }
	        else {
			    pt4 = pt4.otherMoveTo(axisX, (raioCondutor + distCondutor + raioCondutor) );	        	        	
	        }
	    }
	    
	    // DRAW: TEXTOS 1
	    //
	    GeomPoint2d ptText1 = pt3.otherMoveTo(axisY, - hTextLineMcs);
	
	    String strIB = String.format("IBt = %s A", nf0.format(iB));
		DrawUtil.drawTextMcs(v, strIB, ptText1, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_TOP, g);
	    
	    // DRAW: TEXTOS 2
	    //
	    GeomPoint2d ptText2 = pt0.otherMoveTo(axisY, hTextLineMcs);
		DrawUtil.drawTextMcs(v, "BARRA DE PROTECAO", ptText2, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_BOTTOM, g);
		 
	    GeomPoint2d ptResult2d = ptIns2d.otherMoveTo(axisX, wBarMcs);
	    return ptResult2d;
	}
	
    public GeomPoint2d redraw2d_draw_border(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strNome, String strDescricao, String strPotencia, double wLeftMargin, double wRightMargin, double hTopMargin, double hBottomMargin, double wMcs, double hMcs, Graphics g)
    {
        GeomVector2d axisX = GeomUtil.axisX2d();
        GeomVector2d axisY = GeomUtil.axisY2d();
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double hTextLineMcs = 1.5 * fTitleSzMcs;
        
        double w2Mcs = wMcs / 2.0;
        double h2Mcs = hMcs / 2.0;
        
        // DRAW: QUADRO_DISTRIBUICAO
        //
        GeomPoint2d pt0 = new GeomPoint2d( ptIns2d );
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX,   wMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - hMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - wMcs);

        Color oldcol = GeomUtil.setColor(g, AppDefs.ELDIAGRAMAUNIFILAR_CORBORDA_COLOR1);
    	
        DrawUtil.drawLineMcs(v, pt0, pt1, g);
        DrawUtil.drawLineMcs(v, pt1, pt2, g);
        DrawUtil.drawLineMcs(v, pt2, pt3, g);
        DrawUtil.drawLineMcs(v, pt3, pt0, g);
        
        // DRAW: TEXTOS
        //
        GeomPoint2d ptMid0 = pt0.otherMoveTo(axisX, w2Mcs);

        GeomPoint2d ptText3 = ptMid0.otherMoveTo(axisY,  - hTextLineMcs);
        GeomPoint2d ptText2 = ptText3.otherMoveTo(axisY, - hTextLineMcs);
        GeomPoint2d ptText1 = ptText2.otherMoveTo(axisY, - hTextLineMcs);

        GeomUtil.setColor(g, AppDefs.ELDIAGRAMAUNIFILAR_TEXTOTITULO_COLOR1);
    	
    	DrawUtil.drawTextMcs(v, strNome,      ptText1, fTitleSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strDescricao, ptText2, fTitleSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g); 
       	DrawUtil.drawTextMcs(v, strPotencia,  ptText3, fTitleSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
    	 
       	GeomUtil.setColor(g, oldcol);
       	
        GeomPoint2d ptResult2d = ptIns2d.otherMoveTo(axisY, - h2Mcs);
        ptResult2d.selfMoveTo(axisX, wLeftMargin);
        return ptResult2d;    	
    }
	    
	public GeomPoint2d redraw2d_draw_quadro(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strNome, String strDescricao, String strPotencia, Graphics g)
	{
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fCellSzMcs = this.fontCellSzMili * sclFact;
        double hTextLineMcs = 1.5 * fCellSzMcs;
        
        double hMcs = 15.0 * sclFact;
        double h2Mcs = hMcs / 2.0;

        double wMcs = 10.0 * sclFact; 
        double w2Mcs = wMcs / 2.0;

        // DRAW: QUADRO_DISTRIBUICAO
        //
        GeomPoint2d pt0 = ptIns2d.otherMoveTo(axisY, h2Mcs);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX,     wMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY,   - hMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX,   - wMcs);

        DrawUtil.drawLineMcs(v, pt0, pt1, g);
        DrawUtil.drawLineMcs(v, pt1, pt2, g);
        DrawUtil.drawLineMcs(v, pt2, pt3, g);
        DrawUtil.drawLineMcs(v, pt3, pt0, g);

        DrawUtil.drawLineMcs(v, pt0, pt2, g);

        // DRAW: TEXTOS
        //
        GeomPoint2d ptMid0 = pt0.otherMoveTo(axisX, w2Mcs);

        GeomPoint2d ptText3 = ptMid0.otherMoveTo(axisY, hTextLineMcs);
        GeomPoint2d ptText2 = ptText3.otherMoveTo(axisY, hTextLineMcs);
        GeomPoint2d ptText1 = ptText2.otherMoveTo(axisY, hTextLineMcs);

    	DrawUtil.drawTextMcs(v, strNome,      ptText1, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strDescricao, ptText2, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g); 
    	DrawUtil.drawTextMcs(v, strPotencia,  ptText3, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
    	 
        GeomPoint2d ptResult2d = ptIns2d.otherMoveTo(axisX, wMcs);
        return ptResult2d;
	}
    
	public GeomPoint2d redraw2d_draw_disjuntor(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strDisjuntor, String strSistemaFase, Graphics g)
	{
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fCellSzMcs = this.fontCellSzMili * sclFact;
        double hTextLineMcs = 1.5 * fCellSzMcs;
        
        double wMcs = 15.0 * sclFact; 
        double w3Mcs = wMcs / 3.0;
        double w6Mcs = w3Mcs / 2.0;

        // DRAW: DISJUNTOR_PROTECAO
        //
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, w3Mcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisX, w6Mcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, w6Mcs);
        GeomPoint2d pt4 = pt3.otherMoveTo(axisX, w3Mcs);

        DrawUtil.drawLineMcs(v, pt0, pt1, g);
        DrawUtil.drawLineMcs(v, pt3, pt4, g);
        
        GeomPoint2d pt5 = pt4.otherMoveTo(axisY, hTextLineMcs);

        DrawUtil.drawArcMcs(v, pt2, pt3, pt1, g);
        
        // DRAW: FIOS
        //
        GeomPoint2d pt6 = pt2.otherMoveTo(axisY, w6Mcs);

        int fios = 0;
        
        if( AppDefs.FIA_S_FN.equals( strSistemaFase ) || 
        	AppDefs.FIA_S_FNT.equals( strSistemaFase ) ) 
        {
            fios = AppDefs.FIA_F1;        	
        }
        else if( AppDefs.FIA_S_2F.equals( strSistemaFase ) || 
        		 AppDefs.FIA_S_2FN.equals( strSistemaFase ) || 
        		 AppDefs.FIA_S_2FT.equals( strSistemaFase ) || 
        		 AppDefs.FIA_S_2FNT.equals( strSistemaFase ) )  
        {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2;        	
        }
        else if( AppDefs.FIA_S_3F.equals( strSistemaFase ) ||
        		 AppDefs.FIA_S_3FN.equals( strSistemaFase ) ||
        		 AppDefs.FIA_S_3FT.equals( strSistemaFase ) ||
        		 AppDefs.FIA_S_3FNT.equals( strSistemaFase ) ) 
        {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_F3;        	
        }
        		
        this.redraw2d_draw_fios(v, pt6, sclFact, strDisjuntor, fios, g);
        
        GeomPoint2d ptResult2d = new GeomPoint2d(pt4);
        return ptResult2d;
	}
    
	public GeomPoint2d redraw2d_draw_condutor(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strAlimentador, String strAlimentadorProtecao, String strSistemaFase, Graphics g)
	{
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fCellSzMcs = this.fontCellSzMili * sclFact;
        double hTextLineMcs = 1.5 * fCellSzMcs;
        
        double wMcs = 15.0 * sclFact; 
        double w2Mcs = wMcs / 2.0;

        // DRAW: DISJUNTOR_PROTECAO
        //
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, w2Mcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisX, w2Mcs);

        DrawUtil.drawLineMcs(v, pt0, pt2, g);
        
        // DRAW: FIOS
        //
        int fios = 0;
        
        if( AppDefs.FIA_S_FN.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_N;        	
        }
        else if( AppDefs.FIA_S_FNT.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_N + AppDefs.FIA_TR;        	
        }
        else if( AppDefs.FIA_S_2F.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2;        	
        }
        else if( AppDefs.FIA_S_2FN.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_N;        	
        }
        else if( AppDefs.FIA_S_2FT.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_TR;        	
        }
        else if( AppDefs.FIA_S_2FNT.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_N + AppDefs.FIA_TR;        	
        }
        else if( AppDefs.FIA_S_3F.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_F3;        	
        }
        else if( AppDefs.FIA_S_3FT.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_F3 + AppDefs.FIA_TR;        	
        }
        else if( AppDefs.FIA_S_3FN.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_F3 + AppDefs.FIA_N;        	
        }
        else if( AppDefs.FIA_S_3FNT.equals( strSistemaFase ) ) {
            fios = AppDefs.FIA_F1 + AppDefs.FIA_F2 + AppDefs.FIA_F3 + AppDefs.FIA_N + AppDefs.FIA_TR;        	
        }
        		
        this.redraw2d_draw_fios(v, pt1, sclFact, strSistemaFase, fios, g);
        
        // DRAW: TEXTOS
        //
        GeomPoint2d ptText1 = pt1.otherMoveTo(axisY,     - hTextLineMcs);
        GeomPoint2d ptText2 = ptText1.otherMoveTo(axisY, - hTextLineMcs);

    	DrawUtil.drawTextMcs(v, strAlimentador,         ptText1, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strAlimentadorProtecao, ptText2, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g); 

        GeomPoint2d ptResult2d = new GeomPoint2d(pt2);
        return ptResult2d;
	}
		
	public GeomPoint2d redraw2d_draw_carga(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strCircuito, String strDescricao, String strPotencia, String strFase, Graphics g)
	{
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fCellSzMcs = this.fontCellSzMili * sclFact;
        double hTextLineMcs = 1.5 * fCellSzMcs;
        
        double wMcs = 10.0 * sclFact; 
        double w2Mcs = wMcs / 2.0;
        double w3Mcs = wMcs / 3.0;

        // DRAW: CARGA
        //
        GeomPoint2d pt0 = new GeomPoint2d( ptIns2d );
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, w2Mcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisX, w2Mcs);

        DrawUtil.drawCircleMcs(v, pt1, w2Mcs, g);
        DrawUtil.drawCircleMcs(v, pt1, w3Mcs, g);

        // DRAW: TEXTOS
        //
        GeomPoint2d ptText2 = pt2.otherMoveTo(axisX, hTextLineMcs);
        GeomPoint2d ptText1 = ptText2.otherMoveTo(axisY,   hTextLineMcs);
        GeomPoint2d ptText3 = ptText2.otherMoveTo(axisY, - hTextLineMcs);
        GeomPoint2d ptText4 = ptText3.otherMoveTo(axisY, - hTextLineMcs);
        
    	DrawUtil.drawTextMcs(v, strCircuito,  ptText1, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strDescricao, ptText2, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g); 
    	DrawUtil.drawTextMcs(v, strPotencia,  ptText3, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strFase,  	  ptText4, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	 
        GeomPoint2d ptResult2d = new GeomPoint2d( pt2 );
        return ptResult2d;
	}
    
	public void redraw2d_drawDiagramaUnifilar(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, Graphics g)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf1 = FormatUtil.newNumberFormatWithoutGroupingPtBr(1);

		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);

		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingPtBr(6);
		
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
		String strNome = this.nomeQuadro;
		String strDescricao = this.descricaoQuadro;
		String strPotencia = String.format("Carga Total: %s VA", nf3.format( this.oEnt.getPotenciaQuadro() ) );
		String strDisjuntor = String.format("%s A", nf0.format( this.oEnt.getDisjuntorQuadro() ) );
		String strSistemaFase = this.oEnt.getSistemaFase();
    	String strAlimentador = String.format("Alim. %s mm2", nf1.format( this.oEnt.getAlimentadorQuadro() ) );
    	
    	String strAlimentadorProtecao = "";
    	if(this.oEnt.getAlimentadorProtecaoQuadro() >= AppDefs.MATHPREC_MIN) {
    		strAlimentadorProtecao = String.format("Alim. Prot. %s mm2", nf1.format( this.oEnt.getAlimentadorProtecaoQuadro() ) );
    	}

    	//DIMENSOES
    	//
    	ArrayList<CadCircuitoQuadroCargasEletricaOData> lsCircuito = this.oEnt.getLsItem();
    	int numCirc = lsCircuito.size();

        double hMcs = 20.0 * sclFact;
        double h2Mcs = hMcs / 2.0;

        double wMcs = 100.0 * sclFact;
        
    	double hTopMargem = 40.0 * sclFact;
    	double hBottomMargem = 40.0 * sclFact;
    	    	
    	double wLeftMargem = 10.0 * sclFact;
    	double wRightMargem = 10.0 * sclFact;
    	    	
        double hDiagMcs = hTopMargem + (hMcs * numCirc) + hBottomMargem;
        double wDiagMcs = wLeftMargem + wMcs + wRightMargem;
            	
        double h2DiagMcs = hDiagMcs / 2.0;

        GeomPoint2d ptIns0 = this.redraw2d_draw_border(v, ptIns2d, sclFact, strNome, strDescricao, strPotencia, wLeftMargem, wRightMargem, hTopMargem, hBottomMargem, wDiagMcs, hDiagMcs, g);

        GeomPoint2d pt0 = this.redraw2d_draw_quadro(v, ptIns0, sclFact, strNome, strDescricao, strPotencia, g);

    	pt0 = this.redraw2d_draw_disjuntor(v, pt0, sclFact, strDisjuntor, strSistemaFase, g);

    	pt0 = this.redraw2d_draw_condutor(v, pt0, sclFact, strAlimentador, strAlimentadorProtecao, strSistemaFase, g);

    	double h = (hMcs * (numCirc - 1));
    	double h2 = h / 2.0;
    	
        GeomPoint2d pt1 = pt0.otherMoveTo(axisY,   h2);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - h);
        
        DrawUtil.drawLineMcs(v, pt1, pt2, g);
        
        //DRAW: CIRCUITO
        //
    	int szCircuito = numCirc;
    	if(szCircuito > 0) {
    		szCircuito = szCircuito - 1;
    	}
    	
        double qNeutro = 0.0;
        int nNeutro = 0;
        //
        double qTerra = 0.0;
        int nTerra = 0;
        
    	GeomPoint2d pt3 = new GeomPoint2d(pt1);
        for(CadCircuitoQuadroCargasEletricaOData o : lsCircuito) {
    		String strNumeroCircuito = o.getNumeroCircuito();
    		String strDescricaoCircuito = o.getDescricaoCircuito();
    		String strPotenciaCircuito = String.format("Carga: %s VA", nf3.format( o.getCargaCircuito() ) );
    		String strDisjuntorCircuito = String.format("%s A", nf0.format( o.getDisjuntorCircuito() ) );
    		String strSistemaFaseCircuito = o.getSistemaFase();
    		String strFaseCircuito = String.format("Fase: %s", o.getFaseCircuito() );
        	String strAlimentadorCircuito = String.format("Alim. %s mm2", nf1.format( o.getAlimentadorCircuito() ) );
        	
        	String strAlimentadorProtecaoCircuito = "";
        	if(o.getAlimentadorProtecaoCircuito() >= AppDefs.MATHPREC_MIN) {
        		strAlimentadorProtecaoCircuito = String.format("Alim. Prot. %s mm2", nf1.format( o.getAlimentadorProtecaoCircuito() ) );
        	}
    		
            GeomPoint2d pt4 = this.redraw2d_draw_disjuntor(v, pt3, sclFact, strDisjuntorCircuito, strSistemaFaseCircuito, g);

        	pt4 = this.redraw2d_draw_condutor(v, pt4, sclFact, strAlimentadorCircuito, strAlimentadorProtecaoCircuito, strSistemaFaseCircuito, g);

        	pt4 = this.redraw2d_draw_carga(v, pt4, sclFact, strNumeroCircuito, strDescricaoCircuito, strPotenciaCircuito, strFaseCircuito, g);
        	
        	pt3 = pt3.otherMoveTo(axisY, - hMcs);
        	
        	//IB-NEUTRO
        	//
        	int n = o.getQtdCondutorNeutroCircuito();
        	if(n > 0) {
        		qNeutro += o.getCargaCircuito();
        		nNeutro += n;
        	}
        	
        	//IB-TERRA
        	//
        	n = o.getQtdCondutorTerraCircuito();
        	if(n > 0) {
        		qTerra += o.getCargaCircuito();
        		nTerra += n;
        	}
        }
        
        //BARRA_NEUTRO
        //
        nNeutro += 1;
        
        double ibNeutro = this.oEnt.getCorrenteBarraNeutroQuadro();
        GeomPoint2d ptBarNeutro = ptIns0.otherMoveTo(axisY, - hMcs);
    	this.redraw2d_draw_barra_neutro(v, ptBarNeutro, sclFact, ibNeutro, nNeutro, g);
        
        //BARRA_TERRa
        //
        nTerra += 1;
        
        double ibTerra = this.oEnt.getCorrenteBarraProtecaoQuadro();
        GeomPoint2d ptBarTerra = ptBarNeutro.otherMoveTo(axisY, - hMcs);
    	this.redraw2d_draw_barra_terra(v, ptBarTerra, sclFact, ibTerra, nTerra, g);
        
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
		        	CadDiagramaUnifilarEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadDiagramaUnifilarEletrica other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadDiagramaUnifilarEletrica other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadDiagramaUnifilarEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }

        //DRAW_QUADRO_CARGAS
        //
        this.redraw2d_drawDiagramaUnifilar(v, ptDest2dMcs, sclFact, g);
        
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

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();
    	lsPtNodepoint.add(new GeomPoint3d(this.ptIns));    	
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return null;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();
    	lsPtNodepoint.add(new GeomPoint3d(this.ptIns));    	
    	return lsPtNodepoint;
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
		
		int quadroCargasId = oEnt.getObjectId(); 
		
		double ptInsX = ptIns.getX();
		double ptInsY = ptIns.getY();
		double ptInsZ = ptIns.getZ();
		
		Object[] arrVal = {
			new Integer( quadroCargasId ),
			//
			new Double( ptInsX ),
			new Double( ptInsY ),
			new Double( ptInsZ ),
			//
			new String( nomeQuadro ),
			new String( descricaoQuadro ) 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadDiagramaUnifilarEletricaRecord entRec = new CadDiagramaUnifilarEletricaRecord(this); 
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
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsDxfResult = new ArrayList<DxfCadEntity>();
		return lsDxfResult;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}

	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomPoint3d ptIns3d = new GeomPoint3d(this.ptIns);
		
		GeomDimension3d oDim = new GeomDimension3d(ptIns3d, ptIns3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);
		
		GeomDimension2d oDim = new GeomDimension2d(ptIns2d, ptIns2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"NOMEQUADRO=" + this.nomeQuadro;
		return searchString;
	}

	public CadQuadroCargasEletrica getEnt() {
		return oEnt;
	}

	public void setEnt(CadQuadroCargasEletrica oEnt) {
		this.oEnt = oEnt;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}
	
}
