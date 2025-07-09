/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAreaTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;

public class CadAreaTable extends CadEntity 
{
//Private
    private GeomPoint3d ptIns = null;
    private int areaType = AppDefs.AREATYPE_NONE;
    private ArrayList<CadArea> lsArea = null;
    //
    private ArrayList<TableHeaderVO> lsHeader = null;
    private ArrayList<TableRowVO> lsRows = null;

    //FONT_SIZE
    private double fontTitleSzScr = AppDefs.FONTSZ_BIG * AppDefs.UNIT_FACTOR_POL_TO_MM;        
    private double fontHeaderSzScr = AppDefs.FONTSZ_MEDIUM * AppDefs.UNIT_FACTOR_POL_TO_MM;        
    private double fontCellSzScr = AppDefs.FONTSZ_NORMAL * AppDefs.UNIT_FACTOR_POL_TO_MM;
    
    /* Methodes */
    
    private ArrayList<CadArea> loadAll(int areaType)
    {
		ArrayList<CadArea> lsResult = new ArrayList<CadArea>();

		AppCadMain cad = AppCadMain.getCad();

		CadBlockDef oCurrBlkDef = cad.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = oCurrBlkDef.findAllEntityByObjType(AppDefs.OBJTYPE_BIMAREA);
		for(CadEntity oEnt : lsEntities) {
			CadArea o = (CadArea)oEnt;
			
			if(o.getAreaType() == areaType) {
				lsResult.add(o);
			}
		}		
		return lsResult;
    }
    
//Public

    public CadAreaTable(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BIMAREATABLE, oLayer);
    }
	
	/* Methodes */
	
	private void init(
		GeomPoint3d ptIns,
		int areaType) 
	{
		this.ptIns = new GeomPoint3d(ptIns);
		this.areaType = areaType;
	    this.lsArea = this.loadAll(this.areaType);
		//
		this.lsHeader = new ArrayList<TableHeaderVO>(); 
		this.lsRows = new ArrayList<TableRowVO>();

	    this.initTableHeader();
	    this.initTableRows();
	}
	
	private void init(CadAreaTable o)
	{
		this.init(
			o.ptIns, 
			o.areaType);
	}

    private void initTableHeader()
    {
    	int col = 1;    	
    	this.lsHeader.add(new TableHeaderVO(col++, "#", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25));  
    	this.lsHeader.add(new TableHeaderVO(col++, "Descricao", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25));  
    	this.lsHeader.add(new TableHeaderVO(col++, "Area", AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 100, 25));  
    }

    private void initTableRows()
    {
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	int sz = this.lsArea.size();
    	for(int i = 0; i < sz; i++) {
    		CadArea o = lsArea.get(i);

    		TableRowVO oRow = new TableRowVO(); 
    		int rowNum = i + 1;
    		
    		String strArea = String.format("%s m2", nf3.format(o.getArea()));
    		
        	int col = 1;    	
        	oRow.addTableCell(new TableCellVO(rowNum, col++, Integer.toString(rowNum), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25));
        	oRow.addTableCell(new TableCellVO(rowNum, col++, o.getName(), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25));
        	oRow.addTableCell(new TableCellVO(rowNum, col++, strArea, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 100, 25));

			this.lsRows.add(oRow);
    	}
    }

	/* CREATE */
	
	public static CadAreaTable create(
		CadLayerDef oLayer,
		GeomPoint3d ptIns, 
		int areaType) 
	{
    	CadAreaTable o = new CadAreaTable(oLayer);
    	o.init(ptIns, areaType);
    	return o;
    }
	
	public static CadAreaTable create(CadAreaTable o)
	{
    	CadAreaTable other = new CadAreaTable(o.getLayer());
    	other.init(o);
    	return other;
    }
	
	/* OPERATIONS */
	
	public CadAreaTable duplicate()
	{
		CadAreaTable other = CadAreaTable.create(this);
		return other;
	}
	
	public CadAreaTable copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadAreaTable other = CadAreaTable.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	public CadAreaTable moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, this.ptIns);
    	this.ptIns = o.getPtDest();
    	return this;
	}
	
	public CadAreaTable scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	ScaleData3dVO o = GeomUtil.scaleToPt3d(ptIMcs, ptFMcs, this.ptIns, this.ptIns);
    	this.ptIns = o.getPtDest();
    	return this;
	}
	
	public CadAreaTable offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadAreaTable oTable = copyTo(ptIMcs, ptFMcs);
		return oTable;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String str = super.toStr();		
		str += String.format(
			"PtIns:%s;",
		    ptIns.toStr());
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWING */
    
    public void redraw2d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	if(this.isDeleted()) return;

    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
        
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        if(ptBase2dMcs != null) 
        {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);
            
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	CadAreaTable oTable = this.duplicate();
	        	oTable.moveTo(ptBase3dMcs, pt3dMcs);
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	CadAreaTable oTable = this.duplicate();
	        	oTable.scaleTo(ptBase3dMcs, pt3dMcs);
	        }
        }
        
        GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);        
        
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fTitleSzMcs = v.fromScrToMcs(this.fontTitleSzScr);
        double fHeaderSzMcs = v.fromScrToMcs(this.fontHeaderSzScr);
        double fCellSzMcs = v.fromScrToMcs(this.fontCellSzScr);
    	
        double hTextLineMcs = fCellSzMcs;
        
        String strTitle1 = "Tabela de Areas";
        
        String strTitle2 = "Tipo: " + GeomUtil.getAreaTypeText(this.areaType);
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 5.0 * hTextLineMcs;
        double hTableHeaderMcs = 3.0 * hTextLineMcs;
        double hTableRowMcs = 2.0 * hTextLineMcs;

        double hTableMcs = hTableTitleMcs + hTableHeaderMcs + (szRows * hTableRowMcs);
        double wTableMcs = 0.0; 
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = v.fromScrToMcs(oHdr.getColWidthScr());
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

        int sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
            DrawUtil.drawLineMcs(v, pt8, pt9, g);        	
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = v.fromScrToMcs(oHdr.getColWidthScr());
        	
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
    	DrawUtil.drawTextMcs(v, strTitle2, ptLabelTitle2, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = v.fromScrToMcs(oHdr.getColWidthScr());
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableRowMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();
        	DrawUtil.drawTextMcs(v, strHdr, ptLabelHdr, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
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
            	
            	double colWidthMcs = v.fromScrToMcs(oCell.getColWidthScr());
                w13 = w13 + colWidthMcs;
            	
                double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
                double yPt14 = yPt13;

                GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);
            	
                String strCell = oCell.getText();
            	DrawUtil.drawTextMcs(v, strCell, ptLabelCell, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
            }
        }        
        
        GeomUtil.setColor(g, oldcol);

        GeomUtil.setLtype(g, oldltype);
    }
    
	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
		if(this.isDeleted()) return false;
		if(this.isSelected()) return true;

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distBox = boxSz / 2.0;

        GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);

		return false;
	}

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	//ArrayList<GeomPoint2d> lsPtEndpoint = new ArrayList<GeomPoint2d>();
    	//lsPtEndpoint.add(pt2dMcs);    	
		
    	//GeomPoint2d ptResult = null;
    	
    	//ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	//if(ptResult != null) return ptResult;
    	
    	return null;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptIns);
		return ptResult;
	}
    
    /* Getters/Setters */

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
