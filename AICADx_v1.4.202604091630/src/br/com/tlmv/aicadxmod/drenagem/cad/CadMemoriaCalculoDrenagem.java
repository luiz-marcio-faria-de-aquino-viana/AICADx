/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMemoriaCalculoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

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
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadMemoriaCalculoDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadMemoriaCalculoItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;

public class CadMemoriaCalculoDrenagem extends CadEntity
{
//Private
	private GeomPoint3d ptIns;
	private String nome;
	private String descricao;
	private String nomeProjeto;
	private Date dataEmissao;
	private int iCodigoLocalMedicao;
	private String pluviografo;
	private double coefManning;
	private double periodoRecorrencia;
	private boolean bMinimized;
	private ArrayList<CadPerfilDrenagem> lsPerfilDrenagem;
	//
	private ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem = null;
	private Hashtable mapItem = null;

    //FONT_SIZE
    private double fontTitleSzMili = AppDefs.FONTSZ_MEDIUM;        
    private double fontHeaderSzMili = AppDefs.FONTSZ_NORMAL;        
    private double fontCellSzMili = AppDefs.FONTSZ_SMALL;
    
    //TABLE_HEADER/TABLE_ROWS
    private ArrayList<TableHeaderVO> lsHeader = null;
    private ArrayList<TableRowVO> lsRows = null;
	
//Public
    
    public CadMemoriaCalculoDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRMEMORIACALCULO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }

    /* Methodes */
    
	public void init(
		GeomPoint3d ptIns,
		String nome,
		String descricao,
		String nomeProjeto,
		Date dataEmissao,
		int iCodigoLocalMedicao,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		boolean bMinimized,
		ArrayList<CadPerfilDrenagem> lsPerfilDrenagem)
	{
		this.ptIns = ptIns;
		this.nome = nome;
		this.descricao = descricao;
		this.nomeProjeto = nomeProjeto;
		this.dataEmissao = dataEmissao;
		this.iCodigoLocalMedicao = iCodigoLocalMedicao; 
		this.pluviografo = pluviografo;
		this.coefManning = coefManning;
		this.periodoRecorrencia = periodoRecorrencia;
		this.bMinimized = bMinimized;
		this.lsPerfilDrenagem = lsPerfilDrenagem;
		//
		this.lsItem = new ArrayList<CadMemoriaCalculoItemDrenagemOData>();
		this.mapItem = new Hashtable();
	}
    
	@Override
	public void init(ICadObject o) {
		CadMemoriaCalculoDrenagem other = (CadMemoriaCalculoDrenagem)o; 

		this.init(
			other.getPtIns(),
			other.getNome(),
			other.getDescricao(),
			other.getNomeProjeto(),
			other.getDataEmissao(),
			other.getCodigoLocalMedicao(),
			other.getPluviografo(),
			other.getCoefManning(),
			other.getPeriodoRecorrencia(),
			other.isMinimized(),
			other.getLsPerfilDrenagem() );
	}

	/* INIT_TABLE */
	
    private void initTableHeader()
    {
    	this.lsHeader = new ArrayList<TableHeaderVO>();
    	
    	for(int i = 0; i < AppDefs.ARR_TBLCOL_MEMORIA_CALCULO.length; i++) {
    		ColunaTabelaVO o = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO[i];

        	this.lsHeader.add( new TableHeaderVO(i, o.getTitulo(), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, o.getWidth(), o.getHeight()) );  
    	}
    }

    private void initTableRows()
    {
    	this.lsRows = new ArrayList<TableRowVO>();
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	int szRow = this.lsItem.size();
    	for(int j = 0; j < szRow; j++) {
    		CadMemoriaCalculoItemDrenagemOData oMemoriaCalculoItem = this.lsItem.get(j);
    		
    		TableRowVO oRow = new TableRowVO(); 
    		int rowNum = j + 1;

	    	int szCol = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO.length;
	    	for(int i = 0; i < szCol; i++) {
	    		ColunaTabelaVO o = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO[i];
	
	        	String colName = o.getColumnName();  
	        	int dprec = o.getDprec();
	        	
	    		//Object oVal = oMemoriaCalculoItem.toValueByName(colName);
	        	Object oVal = oMemoriaCalculoItem.toStrValueByName(colName, dprec);
	    		
	    		String strVal = oVal.toString();
	
	    		oRow.addTableCell(new TableCellVO(rowNum, i, colName, strVal, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, o.getWidth(), o.getHeight()) );
	    	}
			this.lsRows.add(oRow);
    	}
    }
	
	/* LISTA_ITENS */
	
	public synchronized void loadAllItens(ArrayList<BaseObjectRecord> lsObjRec)
	{
		this.lsItem = new ArrayList<CadMemoriaCalculoItemDrenagemOData>();
		
		// LOADALL_ITEM_MEMORIA_CALCULO - DRENAGEM
		//		
		for(BaseObjectRecord obj : lsObjRec) {
			CadMemoriaCalculoItemDrenagemODataRecord oRec = (CadMemoriaCalculoItemDrenagemODataRecord)obj;

			CadMemoriaCalculoItemDrenagemOData oMemoriaCalculoItem = CadMemoriaCalculoItemDrenagemOData.create(
				this.getDocument(),
				oRec.getRowId(), 
				oRec.getPos(), 
				oRec.getNumeroCI(), 
				oRec.getCodigoLocalMedicao(), 
				oRec.getCoefManning(), 
				oRec.getPv(), 
				oRec.getLocalId(), 
				oRec.getLocal(), 
				oRec.getEstaca(), 
				oRec.getCotaTerreno(), 
				oRec.getAreaLocal(), 
				oRec.getCoefImper(), 
				oRec.getDeclividade(), 
				oRec.getDimensoesMeter(), 
				oRec.getComprimento(), 
				oRec.getObservacao(), 
				oRec.getIsRoot(),
				oRec.getIsFinish(),
				oRec.getTipoSecaoTubulacao(),
				oRec.getCategoriaTubulacaoId(),
				oRec.getDescricaoCategoriaTubulacao(),
				null,
				oRec.getIsDeleted() );
			oMemoriaCalculoItem.setObjectId( oRec.getObjectId() );

			this.lsItem.add(oMemoriaCalculoItem);
		}

		// UPDATEALL_ITEM_MEMORIA_CALCULO - DRENAGEM
		//		
		for(BaseObjectRecord obj : lsObjRec) {
			CadMemoriaCalculoItemDrenagemODataRecord oRec = (CadMemoriaCalculoItemDrenagemODataRecord)obj;

			//ITEM_ATUAL
			int itemAtualId = oRec.getObjectId();
			CadMemoriaCalculoItemDrenagemOData oItemAtual = ListUtil.findItemDataById(itemAtualId, this.lsItem);

			if(oItemAtual != null) {
				//ITEM_ANTERIOR
				int itemAnteriorId = oRec.getItemAnteriorId();
				CadMemoriaCalculoItemDrenagemOData oItemAnterior = ListUtil.findItemDataById(itemAnteriorId, this.lsItem);
				
				if(oItemAnterior != null) {
					oItemAtual.setItemAnterior(oItemAtual);
				}
			}
		}
	}
	
	public synchronized ArrayList<CadMemoriaCalculoItemDrenagemOData> getLsItem()
	{
		return this.lsItem;
	}
	
	public synchronized int getSzLsItem()
	{
		int sz = this.lsItem.size();
		return sz;
	}
	
	public synchronized void addItem(CadMemoriaCalculoItemDrenagemOData o)
	{
		String key = Integer.toString( o.getNumeroCI() );
		
		if( !this.mapItem.containsKey(key) ) {
			this.mapItem.put(o.getNumeroCI(), o);
			this.lsItem.add(o);
		}
	}
	
	public synchronized CadMemoriaCalculoItemDrenagemOData getItemAt(int pos)
	{
		int sz = this.lsItem.size();
		if(pos < sz) {
			CadMemoriaCalculoItemDrenagemOData o = this.lsItem.get(pos);
			return o;
		}
		return null;
	}
	
	public synchronized CadMemoriaCalculoItemDrenagemOData getItemByKey(int numeroCI)
	{
		String key = Integer.toString(numeroCI);
		
		if( this.mapItem.containsKey(key) ) {
			CadMemoriaCalculoItemDrenagemOData o = (CadMemoriaCalculoItemDrenagemOData)this.mapItem.get(key);
			return o;
		}
		return null;
	}

	/* CREATE */
	
	public static CadMemoriaCalculoDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns,
		String nome,
		String descricao,
		String nomeProjeto,
		Date dataEmissao,
		int iCodigoLocalMedicao,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		ArrayList<CadPerfilDrenagem> lsPerfilDrenagem)
	{
		CadMemoriaCalculoDrenagem o = new CadMemoriaCalculoDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			ptIns,
			nome,
			descricao,
			nomeProjeto,
			dataEmissao,
			iCodigoLocalMedicao,
			pluviografo,
			coefManning,
			periodoRecorrencia,
			false,
			lsPerfilDrenagem);
    	return o;
    }
	
	public static CadMemoriaCalculoDrenagem create(CadMemoriaCalculoDrenagem o)
	{
		CadMemoriaCalculoDrenagem other = new CadMemoriaCalculoDrenagem(o.getBlkDef(), o.getLayer(), o.getLevel(), 0.0, false);
    	other.init(o);
    	return other;
    }
	
	public static CadMemoriaCalculoDrenagem create(CadBlockDef blkDef, CadMemoriaCalculoDrenagem other)
	{
		CadMemoriaCalculoDrenagem o = new CadMemoriaCalculoDrenagem(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), false);
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadMemoriaCalculoDrenagem duplicate()
	{
		CadMemoriaCalculoDrenagem other = CadMemoriaCalculoDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadMemoriaCalculoDrenagem duplicate(CadBlockDef blkDef)
	{
		CadMemoriaCalculoDrenagem other = CadMemoriaCalculoDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadMemoriaCalculoDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadMemoriaCalculoDrenagem other = CadMemoriaCalculoDrenagem.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadMemoriaCalculoDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadMemoriaCalculoDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadMemoriaCalculoDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String strMinimized = ( bMinimized ) ? AppDefs.DEF_TEXT_SIM : AppDefs.DEF_TEXT_NAO;
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		
		lsProperty.add( new ItemDataVO("Nome", this.nome, true) );
		lsProperty.add( new ItemDataVO("Descricao", this.descricao, true) );
		lsProperty.add( new ItemDataVO("Projeto", this.nomeProjeto, true) );
		lsProperty.add( new ItemDataVO("Emissao", df.format(this.dataEmissao), true) );
		lsProperty.add( new ItemDataVO("Pluviografo", this.pluviografo, false) );
		lsProperty.add( new ItemDataVO("CoefManning", nf6.format(this.coefManning), false) );
		lsProperty.add( new ItemDataVO("PeriodoRecorrencia", nf0.format(this.periodoRecorrencia), false) );
		lsProperty.add( new ItemDataVO("Minimized", strMinimized, false) );
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String strMinimized = ( bMinimized ) ? AppDefs.DEF_TEXT_SIM : AppDefs.DEF_TEXT_NAO;
		
		String str = String.format(
			"PtIns:%s;" + 
			"Nome:%s;" + 
			"Descricao:%s;" + 
			"NomeProjeto:%s;" + 
			"DataEmissao:%s;" +
			"Pluviografo:%s;" +
			"CoefManning:%s;" +
			"PeriodoRecorrencia:%s;" +
			"Minimized:%s;",
			ptIns.toStr(),
			nome,
			descricao,
			nomeProjeto,
			df.format(dataEmissao),
			pluviografo,
			nf6.format(coefManning),
			nf0.format(periodoRecorrencia),
			strMinimized );
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
    
	public void redraw2d_drawTable(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strNome, String strDescricao, Graphics g)
	{
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double fHeaderSzMcs = this.fontHeaderSzMili * sclFact;
        double fCellSzMcs = this.fontCellSzMili * sclFact;
    	
        double hTitleLineMcs = fTitleSzMcs;
        double hHeaderLineMcs = fHeaderSzMcs;
        double hTextLineMcs = fCellSzMcs;
        
        String strTitle1 = strNome;
        
        String strTitle2 = strDescricao;
        
        this.initTableHeader();
        
        this.initTableRows();
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 4.0 * hTitleLineMcs;
        double hTableHeaderMcs = 2.0 * hHeaderLineMcs;
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
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTitleLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY, - hTitleLineMcs);
        
    	DrawUtil.drawTextMcs(v, strTitle1, ptLabelTitle1, hTitleLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strTitle2, ptLabelTitle2, hTitleLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableHeaderMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();
        	DrawUtil.drawTextMcs(v, strHdr, ptLabelHdr, hHeaderLineMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
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
		        	CadMemoriaCalculoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadMemoriaCalculoDrenagem other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadMemoriaCalculoDrenagem other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadMemoriaCalculoDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }

        //DRAW_MEMORIA_CALCULO
        //
        String strNome = this.nome;
    	String strDescricao = this.descricao;
    	
        double ptInsX = this.ptIns.getX();
        double ptInsY = this.ptIns.getY();

        GeomPoint2d pt2d = new GeomPoint2d(ptInsX, ptInsY);

        GeomVector2d xDir2d = new GeomVector2d(ptInsX, ptInsY, ptInsX + 1, ptInsY);
        
        if( this.bMinimized ) {
        	DrawUtil.drawTableIndicatorMcs(v, ptDest2dMcs, xDir2d, strNome, strDescricao, AppDefs.TBL_SYMBOL_SIZE, AppDefs.TBL_SYMBOL_TEXT_SIZE, g);        	
        }
        else {
        	redraw2d_drawTable(v, pt2d, sclFact, strNome, strDescricao, g);
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

	public boolean save_lsdata(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odDao = dao.createODataDao(AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA); 

		String strCadRefEntityId = Integer.toString(this.getObjectId());

		int sz = this.lsItem.size();
		for(int i = 0; i < sz; i++) {
			CadMemoriaCalculoItemDrenagemOData oMemoriaCalculoItem = (CadMemoriaCalculoItemDrenagemOData)this.lsItem.get(i);

			CadMemoriaCalculoItemDrenagemODataRecord odataRec = new CadMemoriaCalculoItemDrenagemODataRecord(strCadRefEntityId, oMemoriaCalculoItem);				
			odataRec.setCadRefEntityId(strCadRefEntityId);
			odataRec.setObjVer(objVer);
			
			String strIsRoot = StringUtil.fromBoolToStr( oMemoriaCalculoItem.isRoot() );
			String strIsFinish = StringUtil.fromBoolToStr( oMemoriaCalculoItem.isFinish() );
			
			CadMemoriaCalculoItemDrenagemOData oItemAnterior = oMemoriaCalculoItem.getItemAnterior();
			
			int itemAnteriorId = AppDefs.NULL_INT;
			if(oItemAnterior != null) {
				itemAnteriorId = oItemAnterior.getObjectId();
			}
			
			Object[] arrVal = {
				new Integer( oMemoriaCalculoItem.getRowId() ),								// [automatico] 
				new Integer( oMemoriaCalculoItem.getPos() ),								// 0, 1, 2, 3, 4... 					(array position)
				//
				new Integer( oMemoriaCalculoItem.getNumeroCI() ),							// Identificador da Caixa de Inspecao (ou Poco de Visita)
				new Integer( oMemoriaCalculoItem.getCodigoLocalMedicao() ),					// = IDFLOCAL_SANTACRUZ
				new Double( oMemoriaCalculoItem.getCoefManning() ),							// = COEFMANNING_SECAO_CIRCULAR
				new String( oMemoriaCalculoItem.getPv() ),									// PV-A2.1
				new Integer( oMemoriaCalculoItem.getLocalId() ),							// 1001 - RUA DR. MARIO MACHADO
				new String( oMemoriaCalculoItem.getLocal() ),								// RUA DR. MARIO MACHADO
				new String( oMemoriaCalculoItem.getEstaca() ),								// 2 + 1.70 m
				//
				new Double( oMemoriaCalculoItem.getCotaTerreno() ),							// 2.841 m
				new Double( oMemoriaCalculoItem.getFundo() ),								// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
				new Double( oMemoriaCalculoItem.getNivelAgua() ),							// NivelAgua = Fundo + AlturaAgua
				new Double( oMemoriaCalculoItem.getAreaExterna() ),							// AreaExterna = SOMA(AreaTotal_Anterior)
				new Double( oMemoriaCalculoItem.getAreaLocal() ),							// 0.220 ha
				new Double( oMemoriaCalculoItem.getAreaTotal() ),							// AreaTotal = AreaTotal[n-1] + Area
				new Double( oMemoriaCalculoItem.getAreaTotalImp() ),						// AreaTotalImp = 0.0 ha
				new Double( oMemoriaCalculoItem.getCoefImper() ),							// 0.80
				new Double( oMemoriaCalculoItem.getCoefDistr() ),							// CoefDistr = AreaTotal ^ ( -0.15 )
				new Double( oMemoriaCalculoItem.getCoefDistrFinal() ),						// CoefDistrFinal = SOMA(CoefDistr)
				//
				new Double( oMemoriaCalculoItem.getTempoConc() ),							// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
				new Double( oMemoriaCalculoItem.getDeclividade() ),							// 0.00160
				new Double( oMemoriaCalculoItem.getDimensoesMeter() ),						// = DiametroTubulacao ( 0.60 m )
				new Double( oMemoriaCalculoItem.getComprimento() ),							// 30 m
				new String( oMemoriaCalculoItem.getObservacao() ), 
				new String( strIsRoot ),													// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
				new String( strIsFinish ),													// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
				new Integer( itemAnteriorId ),
				new Double( oMemoriaCalculoItem.getIndicePluviometrico() ),					// IndicePluviometrico[CAMPO_GRANDE] = 891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
				new Double( oMemoriaCalculoItem.getCoefDefluv() ),
				//
				new Double( oMemoriaCalculoItem.getDeflLocal() ),							// DeflLocal = Area * CoefDistr * IndicePluviometrico * CoefDefluv * 2.78
				new Double( oMemoriaCalculoItem.getDeflEscoar() ),							// DeflEscoar = DeflLocal
				new Double( oMemoriaCalculoItem.getF() ),									// F = (CoefManning * DeflEscoar / 1000.0) / (SQRT(Declividade) * (Dimensoes ^ (8 / 3))
				new Double( oMemoriaCalculoItem.getDeclividadeGreide() ),					// DeclividadeGreide = (CotaTerreno[n-1] - CotaTerreno[n]) / Comprimento
				new Double( oMemoriaCalculoItem.getAlturaAgua() ),
				new Double( oMemoriaCalculoItem.getYd() ),									// Y/D = (AlturaAgua / Dimensoes) * 100.0
				new Double( oMemoriaCalculoItem.getProfMontJus() ),							// ProfMontJus = CotaTerreno - Fundo
				new Double( oMemoriaCalculoItem.getVelocidade() ),
				new Double( oMemoriaCalculoItem.getTempoPercurso() ),						// TempoPercurso = Comprimento / (Velocidade / 60.0)
				new Double( oMemoriaCalculoItem.getTempoTotal() ),							// TempoTotal = TempoTotal[n-1] + TempoPercurso	
				//
				new Double( oMemoriaCalculoItem.getVazao() ),
				new Double( oMemoriaCalculoItem.getVazaoAcumulada() ), 
				new Double( oMemoriaCalculoItem.getCotaEntrada() ), 
				new Double( oMemoriaCalculoItem.getCotaSaida() ), 
				new String( oMemoriaCalculoItem.getTipoSecaoTubulacao() ),
				new Integer( oMemoriaCalculoItem.getCategoriaTubulacaoId() ), 
			    new String( oMemoriaCalculoItem.getDescricaoCategoriaTubulacao() ),
				new Integer( oMemoriaCalculoItem.getQtdTubulacao() ), 
				new Double( oMemoriaCalculoItem.getDiametroTubulacaoMeter() ), 
				new Double( oMemoriaCalculoItem.getDiametroMeter() ) 
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

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE5_FILEFORMAT_MASC);
		
		this.setObjVer(objVer);
		
		String strIsMinimized = StringUtil.fromBoolToStr(this.bMinimized);
		
		Object[] arrVal = {
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
			new String( this.getNome() ),
			new String( this.getDescricao() ),
			new String( this.getNomeProjeto() ),
			new String( df.format(this.getDataEmissao()) ),
			//
			new Integer( this.getCodigoLocalMedicao() ),
			new String( this.getPluviografo() ),
			new Double( this.getCoefManning() ),
			new Double( this.getPeriodoRecorrencia() ),
			new String( strIsMinimized )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadMemoriaCalculoDrenagemRecord entRec = new CadMemoriaCalculoDrenagemRecord(this); 
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
				
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
		int objectId = this.getObjectId(); 
		int subObjectId = objectId * 1000;
		
		CadLayerDef oLayer = this.getLayer();
    	
    	double sclFact = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
        GeomVector2d axisX = new GeomVector2d(1.0, 0.0);
        GeomVector2d axisY = new GeomVector2d(0.0, 1.0);
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double fHeaderSzMcs = this.fontHeaderSzMili * sclFact;
        double fCellSzMcs = this.fontCellSzMili * sclFact;
    	
        double hTitleLineMcs = fTitleSzMcs;
        double hHeaderLineMcs = fHeaderSzMcs;
        double hTextLineMcs = fCellSzMcs;
    	
        String strNome = this.nome;
    	String strDescricao = this.descricao;
    	
    	GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);
    	
        double ptInsX = ptIns2d.getX();
        double ptInsY = ptIns2d.getY();
        
        String strTitle1 = strNome;
        
        String strTitle2 = strDescricao;
        
        this.initTableHeader();
        
        this.initTableRows();
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 4.0 * hTitleLineMcs;
        double hTableHeaderMcs = 2.0 * hHeaderLineMcs;
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
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt0.getX(), pt0.getY(), 0.0, pt1.getX(), pt1.getY(), 0.0) );
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt1.getX(), pt1.getY(), 0.0, pt2.getX(), pt2.getY(), 0.0) );
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt2.getX(), pt2.getY(), 0.0, pt3.getX(), pt3.getY(), 0.0) );
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt3.getX(), pt3.getY(), 0.0, pt0.getX(), pt0.getY(), 0.0) );
        
        GeomPoint2d pt4 = pt0.otherMoveTo(axisY, - hTableTitleMcs);
        GeomPoint2d pt5 = pt1.otherMoveTo(axisY, - hTableTitleMcs);
        //
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt4.getX(), pt4.getY(), 0.0, pt5.getX(), pt5.getY(), 0.0) );

        GeomPoint2d pt6 = pt4.otherMoveTo(axisY, - hTableHeaderMcs);
        GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - hTableHeaderMcs);
        //
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt6.getX(), pt6.getY(), 0.0, pt7.getX(), pt7.getY(), 0.0) );

        int sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
            lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt8.getX(), pt8.getY(), 0.0, pt9.getX(), pt9.getY(), 0.0) );
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	
            pt10 = pt10.otherMoveTo(axisX, colWidthMcs);
            pt11 = pt11.otherMoveTo(axisX, colWidthMcs);
            //
            lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt10.getX(), pt10.getY(), 0.0, pt11.getX(), pt11.getY(), 0.0) );
        }        
        
        /* TEXTOS */
        
        GeomPoint2d ptMid2d = GeomUtil.midPointOf(pt0, pt1);
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTitleLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY, - hTitleLineMcs);

    	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strTitle1, ptLabelTitle1.getX(), ptLabelTitle1.getY(), 0.0, hTitleLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
    	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strTitle2, ptLabelTitle2.getX(), ptLabelTitle2.getY(), 0.0, hTitleLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableHeaderMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();
        	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strHdr, ptLabelHdr.getX(), ptLabelHdr.getY(), 0.0, hHeaderLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
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
            	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strCell, ptLabelCell.getX(), ptLabelCell.getY(), 0.0, hTextLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
            }

        }        				
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
			"NOME=" + this.nome + "^" +
			"PROJETO=" + this.nomeProjeto + "^" +
			"DESCRICAO=" + this.descricao + "^" +
			"CODIGOLOCALMEDICAO=" + this.iCodigoLocalMedicao + "^" +
			"PLUVIOGRAFO=" + this.pluviografo;
		return searchString;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getPluviografo() {
		return pluviografo;
	}

	public void setPluviografo(String pluviografo) {
		this.pluviografo = pluviografo;
	}

	public double getCoefManning() {
		return coefManning;
	}

	public void setCoefManning(double coefManning) {
		this.coefManning = coefManning;
	}

	public double getPeriodoRecorrencia() {
		return periodoRecorrencia;
	}

	public void setPeriodoRecorrencia(double periodoRecorrencia) {
		this.periodoRecorrencia = periodoRecorrencia;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setLsItem(ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem) {
		this.lsItem = lsItem;
	}

	public boolean isMinimized() {
		return bMinimized;
	}

	public void setMinimized(boolean bMinimized) {
		this.bMinimized = bMinimized;
	}

	public int getCodigoLocalMedicao() {
		return iCodigoLocalMedicao;
	}

	public void setCodigoLocalMedicao(int iCodigoLocalMedicao) {
		this.iCodigoLocalMedicao = iCodigoLocalMedicao;
	}

	public ArrayList<CadPerfilDrenagem> getLsPerfilDrenagem() {
		return lsPerfilDrenagem;
	}

	public void setLsPerfilDrenagem(ArrayList<CadPerfilDrenagem> lsPerfilDrenagem) {
		this.lsPerfilDrenagem = lsPerfilDrenagem;
	}
	
}
