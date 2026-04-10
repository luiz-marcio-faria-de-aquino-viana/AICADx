/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAreaTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
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
import br.com.tlmv.aicadxapp.cad.ecache.LineStringEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.ecache.TextEntityDrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomTextPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadAreaTableRecord;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;

public class CadAreaTable extends CadEntity 
{
//Private
    private GeomPoint3d ptIns = null;
    private int areaType = AppDefs.OPT_AREATYPE_NONE;

    private ArrayList<CadEntity> lsArea = null;

    //TABLE_DEFINITION
    private ArrayList<TableHeaderVO> lsHeader = null;
    private ArrayList<TableRowVO> lsRows = null;

    //FONT_SIZE
    private double fontTitleSzMili = AppDefs.FONTSZ_BIG;        
    private double fontHeaderSzMili = AppDefs.FONTSZ_MEDIUM;        
    private double fontCellSzMili = AppDefs.FONTSZ_NORMAL;
    
    /* Methodes */
    
    private ArrayList<CadEntity> loadAll(int areaType)
    {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();

		CadBlockDef oCurrBlkDef = this.getBlkDef();

		int[] arrObjType = { 
			AppDefs.OBJTYPE_BIMAREA,
			AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO
		};
		
		CadEntity[] lsEntities = oCurrBlkDef.findAllEntityByObjType(arrObjType);
		for(CadEntity oEnt : lsEntities) {
			if( oEnt.isDeleted() ) continue;
			
			int objType = oEnt.getObjType();			

			if(objType == AppDefs.OBJTYPE_BIMAREA) {
				CadArea o = (CadArea)oEnt;
				
				if(o.getAreaType() == areaType) {
					lsResult.add(o);
				}				
			}
			else if(objType == AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO) {
				CadAreaContribuicaoDrenagem o = (CadAreaContribuicaoDrenagem)oEnt;
				
				if(o.getAreaType() == areaType) {
					lsResult.add(o);
				}				
			}			
		}		
		return lsResult;
    }
    
//Public

    public CadAreaTable(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_BIMAREATABLE, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	public void init(
		GeomPoint3d ptIns,
		int areaType) 
	{
		this.ptIns = new GeomPoint3d(ptIns);
		this.areaType = areaType;

		this.lsArea = this.loadAll(this.areaType);

		this.lsHeader = new ArrayList<TableHeaderVO>(); 
		this.lsRows = new ArrayList<TableRowVO>();

	    this.initTableHeader();
	    this.initTableRows();
		
		this.createAllDrawCache();
	}

	public void init(
		double ptInsX, 
		double ptInsY, 
		double ptInsZ,
		int areaType) 
	{
		GeomPoint3d ptIns = new GeomPoint3d(ptInsX, ptInsY, ptInsZ); 
		this.init(ptIns, areaType); 
	}
	
	@Override
	public void init(ICadObject o) {
		CadAreaTable other = (CadAreaTable)o;
		this.init(
			other.ptIns, 
			other.areaType);
	}

    private void initTableHeader()
    {
    	int col = 1;    	
    	
    	this.lsHeader.add(new TableHeaderVO(col++, "#", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25));  
    	this.lsHeader.add(new TableHeaderVO(col++, "Descricao", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25));

    	//AREA_TYPE
    	//
		int areaType = this.getAreaType();			

    	if(areaType == AppDefs.OPT_AREATYPE_DRENAGEAREA) {
        	this.lsHeader.add(new TableHeaderVO(col++, "Area (h)", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 100, 25));      		
    	}
    	else {
        	this.lsHeader.add(new TableHeaderVO(col++, "Area (m2)", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 100, 25));      		
    	}
    }

    private void initTableRows()
    {
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	int sz = this.lsArea.size();
    	for(int i = 0; i < sz; i++) {
    		CadEntity o = lsArea.get(i);

    		TableRowVO oRow = new TableRowVO(); 
    		int rowNum = i + 1;
    		
    		String strName = "";
    		double dArea = 0.0;
    		String strArea = "";
    		
        	//AREA_TYPE
        	//
    		int areaType = this.getAreaType();			

        	if(areaType == AppDefs.OPT_AREATYPE_DRENAGEAREA) {
    			CadAreaContribuicaoDrenagem oAreaContrib = (CadAreaContribuicaoDrenagem)o;
    			strName = oAreaContrib.getName();
        		dArea = oAreaContrib.getAreaHectare();    			
        		strArea = String.format("%s h", nf3.format(dArea));
    		}
        	else {
    			CadArea oArea = (CadArea)o;
    			strName = oArea.getName();
        		dArea = oArea.getArea();    			
        		strArea = String.format("%s m2", nf3.format(dArea));
    		}
    		
        	int col = 1;    	

        	oRow.addTableCell(new TableCellVO(rowNum, col++, strName, Integer.toString(rowNum), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25));
        	oRow.addTableCell(new TableCellVO(rowNum, col++, strName, strName, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25));
        	oRow.addTableCell(new TableCellVO(rowNum, col++, strName, strArea, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 100, 25));

			this.lsRows.add(oRow);
    	}
    }

	/* CREATE */
	
	public static CadAreaTable create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns, 
		int areaType) 
	{
    	CadAreaTable o = new CadAreaTable(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(ptIns, areaType);
    	return o;
    }
	
	public static CadAreaTable create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		int areaType, 
		double ptInsX, 
		double ptInsY, 
		double ptInsZ) 
	{
    	CadAreaTable o = new CadAreaTable(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		ptInsX, 
    		ptInsY, 
    		ptInsZ, 
    		areaType);
    	return o;
    }
	
	public static CadAreaTable create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		int areaType, 
		double ptInsX, 
		double ptInsY, 
		double ptInsZ,
		boolean bLocked) 
	{
    	CadAreaTable o = new CadAreaTable(oBlkDef, oLayer, oLevel, 0.0, bLocked);
    	o.init(
    		ptInsX, 
    		ptInsY, 
    		ptInsZ, 
    		areaType);
    	return o;
    }
		
	public static CadAreaTable create(CadAreaTable other)
	{
    	CadAreaTable o = new CadAreaTable(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadAreaTable create(CadBlockDef blkDef, CadAreaTable other)
	{
		CadAreaTable o = new CadAreaTable(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
		o.init(other);
		return o;
	}
	
	/* OPERATIONS */
	
	@Override
	public CadAreaTable duplicate()
	{
		CadAreaTable other = CadAreaTable.create(this);
		return other;
	}
	
	@Override
	public CadAreaTable duplicate(CadBlockDef blkDef)
	{
		CadAreaTable other = CadAreaTable.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadAreaTable copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadAreaTable other = CadAreaTable.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadAreaTable moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadAreaTable scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadAreaTable offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ItemDataVO oAreaType = ListUtil.findItemDataById(Integer.toString(this.areaType), AppDefs.ARR_AREATYPE);		
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		//
		lsProperty.add( new ItemDataVO("Type", oAreaType.getDescricao(), false) );

	    for(CadEntity o : lsArea) {
	    	ArrayList<ItemDataVO> lsTmpProp = o.toPropertyList();
	    	lsProperty.addAll(lsTmpProp);
	    }
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String str = String.format(
			"PtIns:%s;" +
			"TipoArea:%s;",
		    ptIns.toStr(),
		    Integer.toString(this.areaType) );
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
		DrawCache cache = new DrawCache();

		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);

		double sclFact = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
        double fTitleSzMcs = this.fontTitleSzMili;
        double fHeaderSzMcs = this.fontHeaderSzMili;
        double fCellSzMcs = this.fontCellSzMili;

        double lineHeight = 2.0 * fHeaderSzMcs * sclFact;

        double hTextLineMcs = fHeaderSzMcs * sclFact;
        
        String strTitle1 = "Tabela de Areas";
        
        String strTitle2 = "Tipo: " + GeomUtil.getAreaTypeText(this.areaType);
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 5.0 * hTextLineMcs;
        double hTableHeaderMcs = 3.0 * hTextLineMcs;
        double hTableRowMcs = 2.0 * hTextLineMcs;

        double hTableMcs = hTableTitleMcs + hTableHeaderMcs + (szRows * hTableRowMcs);
        double wTableMcs = 0.0; 
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	wTableMcs += colWidthMcs;
        }        
        
        GeomPoint2d ptIns2d = new GeomPoint2d( this.ptIns );
        
        GeomVector2d axisX = new GeomVector2d( ptIns2d.getX(), ptIns2d.getY(), ptIns2d.getX() + 1, ptIns2d.getY() ); 
        GeomVector2d axisY = new GeomVector2d( ptIns2d.getX(), ptIns2d.getY(), ptIns2d.getX(), ptIns2d.getY() + 1 ); 
        
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX,   wTableMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - hTableMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - wTableMcs);
        //
		LineStringEntityDrawCache oLine1 = new LineStringEntityDrawCache();
		oLine1.addLine2d(pt0, pt1);
		oLine1.addLine2d(pt1, pt2);
		oLine1.addLine2d(pt2, pt3);
		oLine1.addLine2d(pt3, pt0);
		cache.addItem(oLine1);			

        GeomPoint2d pt4 = pt0.otherMoveTo(axisY, - hTableTitleMcs);
        GeomPoint2d pt5 = pt1.otherMoveTo(axisY, - hTableTitleMcs);
        //
		LineStringEntityDrawCache oLine2 = new LineStringEntityDrawCache();
		oLine2.addLine2d(pt4, pt5);
		cache.addItem(oLine2);			

        GeomPoint2d pt6 = pt4.otherMoveTo(axisY, - hTableHeaderMcs);
        GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - hTableHeaderMcs);
        //
		LineStringEntityDrawCache oLine3 = new LineStringEntityDrawCache();
		oLine3.addLine2d(pt6, pt7);
		cache.addItem(oLine3);			

        int sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
    		LineStringEntityDrawCache oLine4 = new LineStringEntityDrawCache();
    		oLine4.addLine2d(pt8, pt9);
    		cache.addItem(oLine4);			
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	
            pt10 = pt10.otherMoveTo(axisX, colWidthMcs);
            pt11 = pt11.otherMoveTo(axisX, colWidthMcs);
            //
    		LineStringEntityDrawCache oLine5 = new LineStringEntityDrawCache();
    		oLine5.addLine2d(pt10, pt11);
    		cache.addItem(oLine5);			
        }        
        
        /* TEXTOS */
        
        GeomPoint2d ptMid2d = GeomUtil.midPointOf(pt0, pt1);
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTextLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY, - hTextLineMcs);
        
		TextEntityDrawCache oText = new TextEntityDrawCache();

		oText.addTextPoint2d( new GeomTextPoint2d(strTitle1, ptLabelTitle1, fHeaderSzMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
		cache.addItem(oText);

		oText.addTextPoint2d( new GeomTextPoint2d(strTitle2, ptLabelTitle2, fHeaderSzMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
		cache.addItem(oText);
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableRowMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();

    		oText.addTextPoint2d( new GeomTextPoint2d(strHdr, ptLabelHdr, fCellSzMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
    		cache.addItem(oText);
        }        

        sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double xPt13 = pt6.getX();
        	double yPt13 = pt6.getY() - (hTableRowMcs * i) - (hTableRowMcs / 2.0);

        	double w13 = 0.0;

        	TableRowVO oRow = this.lsRows.get(i);
        	
        	int sz1 = oRow.getNumTableCell();
            for(int j = 0; j < sz1; j++) {
            	TableCellVO oCell = oRow.getTableCell(j);
            	
            	double colWidthMcs = (oCell.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
                w13 = w13 + colWidthMcs;
            	
                double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
                double yPt14 = yPt13;

                GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);
            	
                String strCell = oCell.getText();

        		oText.addTextPoint2d( new GeomTextPoint2d(strCell, ptLabelCell, fCellSzMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
        		cache.addItem(oText);
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
		DrawCache osnapCache = new DrawCache();

		double sclFact = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
        double fCellSzMcs = this.fontCellSzMili * sclFact;

        double hTextLineMcs = fCellSzMcs;
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 5.0 * hTextLineMcs;
        double hTableHeaderMcs = 3.0 * hTextLineMcs;
        double hTableRowMcs = 2.0 * hTextLineMcs;

        double hTableMcs = hTableTitleMcs + hTableHeaderMcs + (szRows * hTableRowMcs);
        double wTableMcs = 0.0; 
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	wTableMcs += colWidthMcs;
        }        
        
        GeomPoint2d ptIns2d = new GeomPoint2d( this.ptIns );
        
        GeomVector2d axisX = new GeomVector2d( ptIns2d.getX(), ptIns2d.getY(), ptIns2d.getX() + 1, ptIns2d.getY() ); 
        GeomVector2d axisY = new GeomVector2d( ptIns2d.getX(), ptIns2d.getY(), ptIns2d.getX(), ptIns2d.getY() + 1 ); 
        
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX,   wTableMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - hTableMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - wTableMcs);
        //
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, pt0) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, pt1) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, pt2) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, pt3) );

        GeomPoint2d pt4 = pt0.otherMoveTo(axisY, - hTableTitleMcs);
        GeomPoint2d pt5 = pt1.otherMoveTo(axisY, - hTableTitleMcs);
        //
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt4) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt5) );

        GeomPoint2d pt6 = pt4.otherMoveTo(axisY, - hTableHeaderMcs);
        GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - hTableHeaderMcs);
        //
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt6) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt7) );

        int sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
    		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt8) );
    		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt9) );
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	
            pt10 = pt10.otherMoveTo(axisX, colWidthMcs);
            pt11 = pt11.otherMoveTo(axisX, colWidthMcs);
            //
    		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt10) );
    		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, pt11) );
        }        
        
        /* TEXTOS */
        
        GeomPoint2d ptMid2d = GeomUtil.midPointOf(pt0, pt1);
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTextLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY, - hTextLineMcs);

		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, ptLabelTitle1) );
		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, ptLabelTitle2) );
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableRowMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
    		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, ptLabelHdr) );
        }        

        sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double xPt13 = pt6.getX();
        	double yPt13 = pt6.getY() - (hTableRowMcs * i) - (hTableRowMcs / 2.0);

        	double w13 = 0.0;

        	TableRowVO oRow = this.lsRows.get(i);
        	
        	int sz1 = oRow.getNumTableCell();
            for(int j = 0; j < sz1; j++) {
            	TableCellVO oCell = oRow.getTableCell(j);
            	
            	double colWidthMcs = (oCell.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
                w13 = w13 + colWidthMcs;
            	
                double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
                double yPt14 = yPt13;

                GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);
        		osnapCache.addOsnapItem( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, ptLabelCell) );
            }
        }        
		return osnapCache;
	}
    
    /* DRAWING */
	
	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */
	
	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
		if(pt2dMcs == null) return false;
		
    	if(this.isDeleted()) return false;
    	if(this.isSelected()) return true;
    	
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
			new Double( ptIns.getX() ),
			new Double( ptIns.getY() ),
			new Double( ptIns.getZ() ),
			new Integer( areaType ),
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 

		CadAreaTableRecord entRec = new CadAreaTableRecord(this); 
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
		String searchString = super.getSearchString() + "^" +
			"TIPO=" + AppDefs.ARR_AREATYPE[this.areaType].getDescricao();
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

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}
    
}
