/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadTabelaFiacaoEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/01/2026
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
import br.com.tlmv.aicadxapp.AppMain;
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
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadTabelaFiacaoEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.fiacao.FiacaoHelper;

public class CadTabelaFiacaoEletrica extends CadEntity 
{
//Private
    private GeomPoint3d ptIns = null;
    //
    private ArrayList<CadEntity> lsEletroduto = null;
    //
    private ArrayList<TableHeaderVO> lsHeader = null;
    private ArrayList<TableRowVO> lsRows = null;

    //FONT_SIZE
    private double fontTitleSzMili = AppDefs.FONTSZ_BIG;        
    private double fontHeaderSzMili = AppDefs.FONTSZ_MEDIUM;        
    private double fontCellSzMili = AppDefs.FONTSZ_NORMAL;
    
    /* Methodes */
    
    private ArrayList<CadEntity> loadAll()
    {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();

		CadBlockDef oCurrBlkDef = this.getBlkDef();

		int[] arrObjType = {
			AppDefs.OBJTYPE_MODELELETRODUTO,
			AppDefs.OBJTYPE_MODELELETRODUTO3D
		};
		
		CadEntity[] arrEntities = oCurrBlkDef.findAllEntityByObjType(arrObjType);
		for(CadEntity oEnt : arrEntities) {
			if( oEnt.isDeleted() ) continue;
			
			int objType = oEnt.getObjType();
			if(objType == AppDefs.OBJTYPE_MODELELETRODUTO) {
				CadEletrodutoEletrica o = (CadEletrodutoEletrica)oEnt;
				
				int tipoIndicadorFiacao = o.getTipoIndicadorFiacao();
				if(tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_TABELAFIOS) {
					lsResult.add(o);
				}
			}
			else if(objType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
				CadEletroduto3DEletrica o = (CadEletroduto3DEletrica)oEnt;
				
				int tipoIndicadorFiacao = o.getTipoIndicadorFiacao();
				if(tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_TABELAFIOS) {
					lsResult.add(o);
				}
			}

		}		
		return lsResult;
    }
    
//Public

    public CadTabelaFiacaoEletrica(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODELTABELAFIACAO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	public void init(GeomPoint3d ptIns) 
	{
		this.ptIns = new GeomPoint3d(ptIns);
		this.lsEletroduto = this.loadAll();
		//
		this.lsHeader = new ArrayList<TableHeaderVO>(); 
		this.lsRows = new ArrayList<TableRowVO>();

	    this.initTableHeader();
	    this.initTableRows();
	}

	public void init(
		double ptInsX, 
		double ptInsY, 
		double ptInsZ ) 
	{
		GeomPoint3d ptIns = new GeomPoint3d(ptInsX, ptInsY, ptInsZ); 
		this.init(ptIns); 
	}
    
	@Override
	public void init(ICadObject o) {
		CadTabelaFiacaoEletrica other = (CadTabelaFiacaoEletrica)o;

		this.init(other.ptIns);
	}

    private void initTableHeader()
    {
    	int col = 1;    	

    	this.lsHeader.add(new TableHeaderVO(col++, "#", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25));  
    	this.lsHeader.add(new TableHeaderVO(col++, "Listagem dos Fios", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25));
    }

    private void initTableRows()
    {
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	AppMain app = AppMain.getApp();
    	
    	EletricaModule oEleMod = app.getElModule();
    	oEleMod.resetFiaSeqNum();
    	
    	int sz = this.lsEletroduto.size();
    	for(int i = 0; i < sz; i++) {
    		CadEntity ent1 = this.lsEletroduto.get(i);

    		int objType = ent1.getObjType();
			if(objType == AppDefs.OBJTYPE_MODELELETRODUTO) {
	    		CadEletrodutoEletrica oEnt1 = (CadEletrodutoEletrica)this.lsEletroduto.get(i);

	    		int numIndicadorFiacao = oEleMod.nextFiaSeqNum();
				oEnt1.setNumIndicadorFiacao(numIndicadorFiacao);

				CadImportaFiacaoEletricaOData oImportaFiacao = oEnt1.getImportaFiacao();
				
	    		TableRowVO oRow = new TableRowVO();
	    		int rowNum = i + 1;
	    		
	        	int col = 1;    	
	        	oRow.addTableCell( new TableCellVO(rowNum, col++, "numIndicadorFiacao", Integer.toString(numIndicadorFiacao), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25) );
	        	oRow.addTableCell( new TableCellVO(rowNum, col++, "listaFios", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25, oImportaFiacao) );

				this.lsRows.add(oRow);
			}
			else if(objType == AppDefs.OBJTYPE_MODELELETRODUTO3D) {
	    		CadEletroduto3DEletrica oEnt1 = (CadEletroduto3DEletrica)this.lsEletroduto.get(i);

	    		int numIndicadorFiacao = oEleMod.nextFiaSeqNum();
				oEnt1.setNumIndicadorFiacao(numIndicadorFiacao);

				CadImportaFiacaoEletricaOData oImportaFiacao = oEnt1.getImportaFiacao();
				
	    		TableRowVO oRow = new TableRowVO();
	    		int rowNum = i + 1;
	    		
	        	int col = 1;    	
	        	oRow.addTableCell( new TableCellVO(rowNum, col++, "numIndicadorFiacao", Integer.toString(numIndicadorFiacao), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25) );
	        	oRow.addTableCell( new TableCellVO(rowNum, col++, "listaFios", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25, oImportaFiacao) );

				this.lsRows.add(oRow);
			}
    	}
    }

	/* CREATE */
	
	public static CadTabelaFiacaoEletrica create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns) 
	{
    	CadTabelaFiacaoEletrica o = new CadTabelaFiacaoEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns);
    	return o;
    }
	
	public static CadTabelaFiacaoEletrica create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns,
		boolean bLocked) 
	{
    	CadTabelaFiacaoEletrica o = new CadTabelaFiacaoEletrica(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(ptIns);
    	return o;
    }
	
	public static CadTabelaFiacaoEletrica create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		double ptInsX, 
		double ptInsY, 
		double ptInsZ) 
	{
    	CadTabelaFiacaoEletrica o = new CadTabelaFiacaoEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		ptInsX, 
    		ptInsY, 
    		ptInsZ);
    	return o;
    }
		
	public static CadTabelaFiacaoEletrica create(CadTabelaFiacaoEletrica other)
	{
    	CadTabelaFiacaoEletrica o = new CadTabelaFiacaoEletrica(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadTabelaFiacaoEletrica create(CadBlockDef blkDef, CadTabelaFiacaoEletrica other)
	{
		CadTabelaFiacaoEletrica o = new CadTabelaFiacaoEletrica(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
		o.init(other);
		return o;
	}
	
	/* OPERATIONS */
	
	@Override
	public CadTabelaFiacaoEletrica duplicate()
	{
		CadTabelaFiacaoEletrica other = CadTabelaFiacaoEletrica.create(this);
		return other;
	}
	
	@Override
	public CadTabelaFiacaoEletrica duplicate(CadBlockDef blkDef)
	{
		CadTabelaFiacaoEletrica other = CadTabelaFiacaoEletrica.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadTabelaFiacaoEletrica copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadTabelaFiacaoEletrica other = CadTabelaFiacaoEletrica.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadTabelaFiacaoEletrica moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadTabelaFiacaoEletrica scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadTabelaFiacaoEletrica offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String str = String.format("PtIns:%s;", ptIns.toStr());
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
	
	public void redraw2d_insereFios_DIREITA(ICadViewBase v, double dist, GeomPoint2d ptDestI2dMcs, GeomPoint2d ptDestF2dMcs, CadImportaFiacaoEletricaOData oImportaFiacao, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(oImportaFiacao == null) return;
		
		double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double tickSz = AppDefs.TICKSZ_NORMAL * sclFact;
		double tickDist = tickSz * 1.5;

		//PT-DEST_I
		double xPtDestI = ptDestI2dMcs.getX();
		double yPtDestI = ptDestI2dMcs.getY();
		
		//PT-DEST_F
		double xPtDestF = ptDestF2dMcs.getX();
		double yPtDestF = ptDestF2dMcs.getY();

		GeomPoint2d ptDestI = null;
		GeomPoint2d ptDestF = null;		
		if(xPtDestI <= xPtDestF) {
			ptDestI = new GeomPoint2d(xPtDestI, yPtDestI);
			ptDestF = new GeomPoint2d(xPtDestF, yPtDestF);
		}
		else {
			ptDestI = new GeomPoint2d(xPtDestF, yPtDestF);
			ptDestF = new GeomPoint2d(xPtDestI, yPtDestI);
		}

		//PT-INS + V-DIR
		GeomVector2d vDirMcs = new GeomVector2d(ptDestI, ptDestF);
		GeomPoint2d ptInsMcs = ptDestI.otherMoveTo(vDirMcs, tickDist);

		//DRAW_FIOS
		//
		ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia = oImportaFiacao.getLsFia();
		for(CadImportaFiacaoEletrodutoEletricaOData oImportaFiacaoEletroduto : lsFia) {
			String label = oImportaFiacaoEletroduto.getLbl();
			int fios = oImportaFiacaoEletroduto.getFia();
			
			ptInsMcs = FiacaoHelper.drawFiosMcs(v, label, fios, ptInsMcs, vDirMcs, textSz, tickSz, tickDist, g);
		}

		//DRAW_EXTENSION_LINE
		//
		DrawUtil.drawLineMcs(v, ptDestI, ptInsMcs, g);		
	}
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;

    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
        
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

        GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);        
        
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
		        	CadTabelaFiacaoEletrica oTable = this.duplicate();
		        	oTable.moveTo(ptBase3dMcs, pt3dMcs);

		            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadTabelaFiacaoEletrica oTable = this.duplicate();
		        	oTable.mirror(ptBase3dMcs, pt3dMcs);

		            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadTabelaFiacaoEletrica oTable = this.duplicate();
			        	oTable.scaleTo(dist, ptBase3dMcs, pt3dMcs);

			            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadTabelaFiacaoEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);

		            ptIns2d = new GeomPoint2d(other.ptIns);        		            
		        }
	        }
        }
        
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double fHeaderSzMcs = this.fontHeaderSzMili * sclFact;
        double fCellSzMcs = this.fontCellSzMili * sclFact;
    	
        double hTextLineMcs = fCellSzMcs;
        
        String strTitle1 = "Tabela de Fios";

        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 5.0 * hTextLineMcs;
        double hTableHeaderMcs = 3.0 * hTextLineMcs;
        double hTableRowMcs = 3.5 * hTextLineMcs;

        double hTableMcs = hTableTitleMcs + hTableHeaderMcs + (szRows * hTableRowMcs);
        double wTableMcs = 0.0; 

        double wCellMarginMcs = 1.5 * fCellSzMcs;
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	wTableMcs += colWidthMcs;
        }        
        
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, wTableMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - hTableMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - wTableMcs);
        //
        DrawUtil.drawLineMcs(v, pt0, pt1, g);
        DrawUtil.drawLineMcs(v, pt1, pt2, g);
        DrawUtil.drawLineMcs(v, pt2, pt3, g);
        DrawUtil.drawLineMcs(v, pt3, pt0, g);
        
        GeomPoint2d pt4 = pt0.otherMoveTo(axisY, - hTableTitleMcs);
        GeomPoint2d pt5 = pt1.otherMoveTo(axisY, - hTableTitleMcs);
        //
        DrawUtil.drawLineMcs(v, pt4, pt5, g);

        GeomPoint2d pt6 = pt4.otherMoveTo(axisY, - hTableHeaderMcs);
        GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - hTableHeaderMcs);
        //
        DrawUtil.drawLineMcs(v, pt6, pt7, g);

        for(int i = 0; i < szRows; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
            DrawUtil.drawLineMcs(v, pt8, pt9, g);        	
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	
            pt10 = pt10.otherMoveTo(axisX, colWidthMcs);
            pt11 = pt11.otherMoveTo(axisX, colWidthMcs);
            //
            DrawUtil.drawLineMcs(v, pt10, pt11, g);        	
        }        
        
        /* TEXTOS */
        
        GeomPoint2d ptMid2d = GeomUtil.midPointOf(pt0, pt1);
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTextLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY, - hTextLineMcs);
        
    	DrawUtil.drawTextMcs(v, strTitle1, ptLabelTitle1, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableRowMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();
        	DrawUtil.drawTextMcs(v, strHdr, ptLabelHdr, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        }        

        for(int i = 0; i < szRows; i++) {
        	double xPt13 = pt6.getX();
        	double yPt13 = pt6.getY() - (hTableRowMcs * i) - (hTableRowMcs / 2.0);

        	double w13 = 0.0;

        	TableRowVO oRow = this.lsRows.get(i);
        	
        	int sz1 = oRow.getNumTableCell();
            for(int j = 0; j < sz1; j++) {
            	TableCellVO oCell = oRow.getTableCell(j);
            	
            	Object obj = oCell.getObj();
            	if(obj == null) {
	            	double colWidthMcs = (oCell.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
	                w13 = w13 + colWidthMcs;
	            	
	                double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
	                double yPt14 = yPt13;
	
	                GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);
	            	
	                String strCell = oCell.getText();
	                if(strCell == null) continue;
	                
	            	DrawUtil.drawTextMcs(v, strCell, ptLabelCell, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
            	}
            	else {
            		CadImportaFiacaoEletricaOData oImportaFiacao = (CadImportaFiacaoEletricaOData)obj;
            		
            		double colWidthMcs = (oCell.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
	                w13 = w13 + colWidthMcs;
	            	
	                //double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
	                double xPt14 = xPt13 + w13 - (colWidthMcs - wCellMarginMcs);
	                double yPt14 = yPt13;

	                GeomPoint2d ptFiosCell = new GeomPoint2d(xPt14, yPt14);
	                GeomPoint2d ptDirCell = new GeomPoint2d(xPt14 + 1.0, yPt14);
	            	
	            	this.redraw2d_insereFios_DIREITA(v, dist, ptFiosCell, ptDirCell, oImportaFiacao, sclFact, bDragMode, bSelEnt, g);	                
            	}
            }
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
	public  ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public  ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
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
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() )
 			
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadTabelaFiacaoEletricaRecord entRec = new CadTabelaFiacaoEletricaRecord(this); 
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
		String searchString = super.getSearchString();
		return searchString;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public ArrayList<TableHeaderVO> getLsHeader() {
		return lsHeader;
	}

	public void setLsHeader(ArrayList<TableHeaderVO> lsHeader) {
		this.lsHeader = lsHeader;
	}

	public ArrayList<TableRowVO> getLsRows() {
		return lsRows;
	}

	public void setLsRows(ArrayList<TableRowVO> lsRows) {
		this.lsRows = lsRows;
	}
    
}
