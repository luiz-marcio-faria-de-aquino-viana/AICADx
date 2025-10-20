/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.CadAreaTableRecord;
import br.com.tlmv.aicadxapp.dao.record.CadCircleRecord;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAreaContribuicaoDrenagem;

public class CadAreaTable extends CadEntity 
{
//Private
    private int areaType = AppDefs.OPT_AREATYPE_NONE;
    private GeomPoint3d ptIns = null;
    //
    private ArrayList<CadEntity> lsArea = null;
    //
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

		AppCadMain cad = AppCadMain.getCad();

		CadBlockDef oCurrBlkDef = cad.getCurrBlockDef();

		ArrayList<CadEntity> lsEntities = oCurrBlkDef.findAllEntityByObjType(AppDefs.OBJTYPE_BIMAREA);
		lsEntities.addAll( oCurrBlkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO) );
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

    public CadAreaTable(CadBlockDef oBlkDef, CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BIMAREATABLE, oBlkDef, oLayer);
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

	private void init(
		double ptInsX, 
		double ptInsY, 
		double ptInsZ,
		int areaType) 
	{
		GeomPoint3d ptIns = new GeomPoint3d(ptInsX, ptInsY, ptInsZ); 
		this.init(ptIns, areaType); 
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
        	oRow.addTableCell(new TableCellVO(rowNum, col++, Integer.toString(rowNum), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 50, 25));
        	oRow.addTableCell(new TableCellVO(rowNum, col++, strName, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 150, 25));
        	oRow.addTableCell(new TableCellVO(rowNum, col++, strArea, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, 100, 25));

			this.lsRows.add(oRow);
    	}
    }

	/* CREATE */
	
	public static CadAreaTable create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		GeomPoint3d ptIns, 
		int areaType) 
	{
    	CadAreaTable o = new CadAreaTable(oBlkDef, oLayer);
    	o.init(ptIns, areaType);
    	return o;
    }
	
	public static CadAreaTable create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		int areaType, 
		double ptInsX, 
		double ptInsY, 
		double ptInsZ) 
	{
    	CadAreaTable o = new CadAreaTable(oBlkDef, oLayer);
    	o.init(
    		ptInsX, 
    		ptInsY, 
    		ptInsZ, 
    		areaType);
    	return o;
    }
		
	public static CadAreaTable create(CadAreaTable o)
	{
    	CadAreaTable other = new CadAreaTable(o.getBlkDef(), o.getLayer());
    	other.init(o);
    	return other;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadAreaTable duplicate()
	{
		CadAreaTable other = CadAreaTable.create(this);
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
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.") );

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
			"PtIns:%s;",
		    ptIns.toStr());
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
		        	CadAreaTable oTable = this.duplicate();
		        	oTable.moveTo(ptBase3dMcs, pt3dMcs);

		            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadAreaTable oTable = this.duplicate();
		        	oTable.mirror(ptBase3dMcs, pt3dMcs);

		            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadAreaTable oTable = this.duplicate();
			        	oTable.scaleTo(dist, ptBase3dMcs, pt3dMcs);

			            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadAreaTable other = this.duplicate();
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
    	DrawUtil.drawTextMcs(v, strTitle2, ptLabelTitle2, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        
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
            	DrawUtil.drawTextMcs(v, strCell, ptLabelCell, hTextLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		//TODO:
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		BaseDao dao = db.getDao();
		CadEntityBaseDao entDao = dao.create(this.getObjType()); 
		CadAreaTableRecord entRec = new CadAreaTableRecord(this); 
		entDao.insertOrUpdate(schemaName, (BaseEntityRecord) entRec);		
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
