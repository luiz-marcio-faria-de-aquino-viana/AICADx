/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadControleBacklistTransMar.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/08/2025
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
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;

public class CadControleBacklistTransMar extends CadEntity
{
//Private
	private GeomPoint3d ptIns;
	private String nome;
	private String descricao;
    private int cdAtendimento;
	private Date dtAtendimento;
	
    /* TMAR: EMBARCACAO
     */
    private int cdEmbarcacao;
    private String nmEmbarcacao;
    private String nmArmador;
    private double comprimento;
    private double largura;
    private double distVert;
    private double distHoriz;
	
	//ITEM_CONTROLE_BACKLIST
	//
	private ArrayList<CadControleBacklistItemTransMarOData> lsItem = null;
	private Hashtable mapItem = null;
	
    //FONT_SIZE
    private double fontTitleSzMili = AppDefs.FONTSZ_MEDIUM;        
    private double fontHeaderSzMili = AppDefs.FONTSZ_NORMAL;        
    private double fontCellSzMili = AppDefs.FONTSZ_SMALL;
    
    //TABLE_HEADER/TABLE_ROWS
    private ArrayList<TableHeaderVO> lsHeader = null;
    private ArrayList<TableRowVO> lsRows = null;
	
//Public
    
    public CadControleBacklistTransMar(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODTMARCONTROLEBACKLIST, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }

    /* Methodes */
    
	public void init(GeomPoint3d ptIns, String nome, String descricao, int cdAtendimento, Date dtAtendimento)
	{
		this.init(
			ptIns, 
			nome, 
			descricao, 
			cdAtendimento, 
			dtAtendimento,
			
		    /* TMAR: EMBARCACAO
		     */
		    AppDefs.DEF_TMAR_EMBARCACAO_CODIGO,
		    AppDefs.DEF_TMAR_EMBARCACAO_NOME,
		    AppDefs.DEF_TMAR_EMBARCACAO_ARMADOR,
		    AppDefs.DEF_TMAR_EMBARCACAO_COMPRIMENTO,
		    AppDefs.DEF_TMAR_EMBARCACAO_LARGURA,
		    AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT,
		    AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ);
	}
    
	public void init(
		GeomPoint3d ptIns, 
		String nome, 
		String descricao, 
		int cdAtendimento, 
		Date dtAtendimento,
		
	    /* TMAR: EMBARCACAO
	     */
	    int cdEmbarcacao,
	    String nmEmbarcacao,
	    String nmArmador,
	    double comprimento,
	    double largura,
	    double distVert,
	    double distHoriz)
	{
		this.ptIns = new GeomPoint3d(ptIns);
		this.nome = nome;
		this.descricao = descricao;
	    this.cdAtendimento = cdAtendimento;
		this.dtAtendimento = dtAtendimento;
		
	    /* TMAR: EMBARCACAO
	     */
	    this.cdEmbarcacao = cdEmbarcacao;
	    this.nmEmbarcacao = nmEmbarcacao;
	    this.nmArmador = nmArmador;
	    this.comprimento = comprimento;
	    this.largura = largura;
	    this.distVert = distVert;
	    this.distHoriz = distHoriz;

	    /* TMAR: CONTROLE_BACKLIST
	     */
		this.lsItem = new ArrayList<CadControleBacklistItemTransMarOData>();
		
	    /* TMAR: CREATE_DRAW_CACHE
	     */
		this.createAllDrawCache();
	}
    
	
	@Override
	public void init(ICadObject o) {
		CadControleBacklistTransMar other = (CadControleBacklistTransMar)o;

		this.init(
			other.getPtIns(),
			other.getNome(),
			other.getDescricao(),
			other.getCdAtendimento(),
			other.getDtAtendimento(),
			
		    /* TMAR: EMBARCACAO
		     */
		    other.getCdEmbarcacao(),
		    other.getNmEmbarcacao(),
		    other.getNmArmador(),
		    other.getComprimento(),
		    other.getLargura(),
		    other.getDistVert(),
		    other.getDistHoriz());
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
    		CadControleBacklistItemTransMarOData oControleBacklistItem = this.lsItem.get(j);
    		
    		TableRowVO oRow = new TableRowVO(); 
    		int rowNum = j + 1;

	    	int szCol = AppDefs.ARR_TBLCOL_BACKLIST.length;
	    	for(int i = 0; i < szCol; i++) {
	    		ColunaTabelaVO o = AppDefs.ARR_TBLCOL_BACKLIST[i];
	
	        	String colName = o.getColumnName();  
	        	int dprec = o.getDprec();
	        	
	    		//Object oVal = oMemoriaCalculoItem.toValueByName(colName);
	        	Object oVal = oControleBacklistItem.toStrValueByName(colName, dprec);
	    		
	    		String strVal = oVal.toString();
	
	    		oRow.addTableCell(new TableCellVO(rowNum, i, colName, strVal, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, o.getWidth(), o.getHeight()) );
	    	}
			this.lsRows.add(oRow);
    	}
    }
	
	/* LISTA_ITENS */
	
	public synchronized void loadAllItens(ArrayList<BaseObjectRecord> lsItens)
	{
//		this.lsItem = new ArrayList<CadControleBacklistItemTransMarOData>();
//		
//		CadControleBacklistItemTransMarOData oItemAnterior = null;
//		for(BaseObjectRecord obj : lsItens) {
//			CadControleBacklistItemTransMarODataRecord oRec = (CadControleBacklistItemTransMarODataRecord)obj;
//
//			CadControleBacklistItemTransMarOData oCadControleBacklistItem = CadControleBacklistItemTransMarOData.create(
//				oRec.getCadRefEntityId(), 
//				oRec.getPos(), 
//				oRec.getNumeroCI(), 
//				oRec.getCodigoLocalMedicao(), 
//				oRec.getCoefManning(), 
//				oRec.getPv(), 
//				oRec.getLocalId(), 
//				oRec.getLocal(), 
//				oRec.getEstaca(), 
//				oRec.getCotaTerreno(), 
//				oRec.getAreaLocal(), 
//				oRec.getCoefImper(), 
//				oRec.getDeclividade(), 
//				oRec.getDimensoes(), 
//				oRec.getComprimento(), 
//				oRec.getObservacao(), 
//				oRec.getIsRoot(),
//				oRec.getIsFinish(),
//				oItemAnterior, 
//				oRec.getIsDeleted() );
//			this.lsItem.add(oCadControleBacklistItem);
//			
//			oItemAnterior = oCadControleBacklistItem;
//		}
	}
	
	public synchronized ArrayList<CadControleBacklistItemTransMarOData> getLsItem()
	{
		return this.lsItem;
	}
	
	public synchronized int getSzLsItem()
	{
		int sz = this.lsItem.size();
		return sz;
	}
	
	public synchronized void addItem(CadControleBacklistItemTransMarOData o)
	{
		String key = Integer.toString( o.getObjectId() );
		
		if( !this.mapItem.containsKey(key) ) {
			this.mapItem.put(key, o);
			this.lsItem.add(o);
		}
	}
	
	public synchronized CadControleBacklistItemTransMarOData getItemAt(int pos)
	{
		int sz = this.lsItem.size();
		if(pos < sz) {
			CadControleBacklistItemTransMarOData o = this.lsItem.get(pos);
			return o;
		}
		return null;
	}
	
	public synchronized CadControleBacklistItemTransMarOData getItemByKey(int numeroCI)
	{
		String key = Integer.toString(numeroCI);
		
		if( this.mapItem.containsKey(key) ) {
			CadControleBacklistItemTransMarOData o = (CadControleBacklistItemTransMarOData)this.mapItem.get(key);
			return o;
		}
		return null;
	}

	/* CREATE */
	
	public static CadControleBacklistTransMar create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns,
		String nome,
		String descricao,
	    int cdAtendimento,
		Date dtAtendimento)
	{
		CadControleBacklistTransMar o = new CadControleBacklistTransMar(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			ptIns,
			nome,
			descricao,
		    cdAtendimento,
			dtAtendimento);
    	return o;
    }
	
	public static CadControleBacklistTransMar create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns,
		String nome,
		String descricao,
	    int cdAtendimento,
		Date dtAtendimento,
		
	    /* TMAR: EMBARCACAO
	     */
	    int cdEmbarcacao,
	    String nmEmbarcacao,
	    String nmArmador,
	    double comprimento,
	    double largura,
	    double distVert,
	    double distHoriz)
	{
		CadControleBacklistTransMar o = new CadControleBacklistTransMar(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			ptIns,
			nome,
			descricao,
		    cdAtendimento,
			dtAtendimento,
			
		    /* TMAR: EMBARCACAO
		     */
		    cdEmbarcacao,
		    nmEmbarcacao,
		    nmArmador,
		    comprimento,
		    largura,
		    distVert,
		    distHoriz);
    	return o;
    }
	
	public static CadControleBacklistTransMar create(CadControleBacklistTransMar other)
	{
		CadControleBacklistTransMar o = new CadControleBacklistTransMar(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadControleBacklistTransMar create(CadBlockDef blkDef, CadControleBacklistTransMar other)
	{
		CadControleBacklistTransMar o = new CadControleBacklistTransMar(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadControleBacklistTransMar duplicate()
	{
		CadControleBacklistTransMar other = CadControleBacklistTransMar.create(this);
		return other;
	}
	
	@Override
	public CadControleBacklistTransMar duplicate(CadBlockDef blkDef)
	{
		CadControleBacklistTransMar other = CadControleBacklistTransMar.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadControleBacklistTransMar copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadControleBacklistTransMar other = CadControleBacklistTransMar.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadControleBacklistTransMar moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadControleBacklistTransMar scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadControleBacklistTransMar offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		
		lsProperty.add( new ItemDataVO("Nome", this.nome, true) );
		lsProperty.add( new ItemDataVO("Descricao", this.descricao, true) );
		lsProperty.add( new ItemDataVO("Atendimento", nf0.format(this.cdAtendimento), true) );
		lsProperty.add( new ItemDataVO("Dt.Atend.", df.format(this.dtAtendimento), true) );
		lsProperty.add( new ItemDataVO("Cd.Embarcacao", nf0.format(this.cdEmbarcacao), true) );
		lsProperty.add( new ItemDataVO("Embarcacao", this.nmEmbarcacao, true) );
		lsProperty.add( new ItemDataVO("Armador", this.nmArmador, true) );		
		lsProperty.add( new ItemDataVO("Comprimento", nf6.format(this.comprimento), true) );
		lsProperty.add( new ItemDataVO("Largura", nf6.format(this.largura), true) );
		lsProperty.add( new ItemDataVO("Dist.Vert.", nf6.format(this.distVert), true) );
		lsProperty.add( new ItemDataVO("Dist.Horiz.", nf6.format(this.distHoriz), true) );
		
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String str = String.format(
			"PtIns:%s;" + 
			"Nome:%s;" + 
			"Descricao:%s;" + 
			"CdAtendimento:%s;" + 
			"DtAtendimento:%s;" +
			"CdEmbarcacao:%s;" +
			"NmEmbarcacao:%s;" +
			"NmArmador:%s;" +
			"Comprimento:%s;" +
			"Largura:%s;" +
			"Dist.Vertical:%s;" +
			"Dist.Horizontal:%s;",
			ptIns.toStr(),
			nome,
			descricao,
			nf0.format( this.cdAtendimento ),
			df.format(this.dtAtendimento),
			nf0.format( this.cdEmbarcacao ),			
			this.nmEmbarcacao,
			this.nmArmador,
			nf6.format(this.comprimento),
			nf6.format(this.largura),
			nf6.format(this.distVert),
			nf6.format(this.distHoriz) );		
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWCACHE */
	
	public DrawCache drawCache_drawTextos()
	{
		DrawCache cache = new DrawCache(); 
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		        
		double xIns = this.ptIns.getX();
		double yIns = this.ptIns.getY();

        TextEntityDrawCache oText = new TextEntityDrawCache(); 
		
		//LINHAS_GRID_HORIZONTAL
		//
		double y_base = yIns;
		double y_top = yIns + this.largura;

		double x = xIns + (AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ / 2.0);
		
        int numHoriz = AppDefs.DEF_TMAR_EMBARCACAO_NUMHORIZ;
        for(int i = 0; i < numHoriz; i++) {
            GeomPoint2d ptI = new GeomPoint2d(x, y_base);
            GeomPoint2d ptF = new GeomPoint2d(x, y_top);
        	
            String strColNum = nf0.format(i);
            
        	oText = new TextEntityDrawCache();
        	oText.addTextPoint2d( new GeomTextPoint2d(strColNum, ptI, AppDefs.FONTSZ_SMALL, 0.0, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_BOTTOM) );
            cache.addItem(oText);

        	x += AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ;
        }
        
		//COLUNAS_GRID
		//
		double yA = yIns + AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
		double yB = yIns + this.largura - AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;

		double x_left = xIns;
		double x_right = xIns + this.comprimento;
		
        int numVert = AppDefs.DEF_TMAR_EMBARCACAO_NUMVERT;
        for(int i = 0; i < numVert; i++) {
            GeomPoint2d ptI_A = new GeomPoint2d(x_left,  yA);
            GeomPoint2d ptF_A = new GeomPoint2d(x_right, yA);
        	
            GeomPoint2d ptI_B = new GeomPoint2d(x_left,  yB);
            GeomPoint2d ptF_B = new GeomPoint2d(x_right, yB);
        	
            String strRowNum = nf0.format(i);
            
        	oText = new TextEntityDrawCache();
        	oText.addTextPoint2d( new GeomTextPoint2d(strRowNum, ptI_A, AppDefs.FONTSZ_SMALL, 0.0, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_BOTTOM) );
            cache.addItem(oText);

        	yA += AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
        	yB -= AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
        }
        
        return cache;
	}

	public DrawCache drawCache_drawEmbarcacao()
	{
		DrawCache cache = new DrawCache(); 
		
		//CONTORNO_EMBARCACAO
		//
		double xIns = this.ptIns.getX();
		double yIns = this.ptIns.getY();
		
        GeomVector2d axisX = new GeomVector2d(xIns, yIns, xIns + 1.0, yIns);
        GeomVector2d axisY = new GeomVector2d(xIns, yIns, xIns,       yIns + 1.0);

        GeomPoint2d pt0 = new GeomPoint2d(this.ptIns);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX,   this.comprimento);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY,   this.largura);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - this.comprimento);

        LineStringEntityDrawCache oLine = new LineStringEntityDrawCache(); 
        oLine.addPoint2d(pt0);
        oLine.addPoint2d(pt1);
        oLine.addPoint2d(pt2);
        oLine.addPoint2d(pt3);
        oLine.addPoint2d(pt0);
        cache.addItem(oLine);
        
		//LINHAS_GRID_HORIZONTAL
		//
		double y_base = yIns;
		double y_top = yIns + this.largura;

		double x = xIns + AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ;
		
        int numHoriz = AppDefs.DEF_TMAR_EMBARCACAO_NUMHORIZ;
        for(int i = 0; i < numHoriz; i++) {
            GeomPoint2d ptI = new GeomPoint2d(x, y_base);
            GeomPoint2d ptF = new GeomPoint2d(x, y_top);
        	
        	oLine = new LineStringEntityDrawCache();
        	oLine.addLine2d(ptI, ptF);
            cache.addItem(oLine);

        	x += AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ;
        }
        
		//COLUNAS_GRID
		//
		double yA = yIns + AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
		double yB = yIns + this.largura - AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;

		double x_left = xIns;
		double x_right = xIns + this.comprimento;
		
        int numVert = AppDefs.DEF_TMAR_EMBARCACAO_NUMVERT;
        for(int i = 0; i < numVert; i++) {
            GeomPoint2d ptI_A = new GeomPoint2d(x_left,  yA);
            GeomPoint2d ptF_A = new GeomPoint2d(x_right, yA);
        	
            GeomPoint2d ptI_B = new GeomPoint2d(x_left,  yB);
            GeomPoint2d ptF_B = new GeomPoint2d(x_right, yB);
        	
        	oLine = new LineStringEntityDrawCache();
        	oLine.addLine2d(ptI_A, ptF_A);
            cache.addItem(oLine);

        	oLine = new LineStringEntityDrawCache();
        	oLine.addLine2d(ptI_B, ptF_B);
            cache.addItem(oLine);

        	yA += AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
        	yB -= AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
        }
        
        return cache;
	}
	
	@Override
	public DrawCache createDrawCache2d() {
		DrawCache cache = new DrawCache(); 
		cache.addDrawCache(drawCache_drawEmbarcacao());
		cache.addDrawCache(drawCache_drawTextos());
        return cache;
	}
	
	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		DrawCache cache = new DrawCache(); 
		
		//CONTORNO_EMBARCACAO
		//
		double xIns = this.ptIns.getX();
		double yIns = this.ptIns.getY();
		
        GeomVector2d axisX = new GeomVector2d(xIns + 1.0, yIns);
        GeomVector2d axisY = new GeomVector2d(xIns, yIns + 1.0);

        GeomPoint2d pt0 = new GeomPoint2d(this.ptIns);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, this.comprimento);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, this.largura);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - this.comprimento);

        //ENDPOINT
        //
        cache.addOsnapItem(AppDefs.OSNAPMODE_ENDPOINT, new GeomPoint3d(pt0) );
        cache.addOsnapItem(AppDefs.OSNAPMODE_ENDPOINT, new GeomPoint3d(pt1) );
        cache.addOsnapItem(AppDefs.OSNAPMODE_ENDPOINT, new GeomPoint3d(pt2) );
        cache.addOsnapItem(AppDefs.OSNAPMODE_ENDPOINT, new GeomPoint3d(pt3) );
        
		//LINHAS_GRID
		//
		double y_base = yIns;
		double y_top = yIns + this.largura;

		double x = xIns + AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ;
		
        int numHoriz = AppDefs.DEF_TMAR_EMBARCACAO_NUMVERT;
        for(int i = 0; i < numHoriz; i++) {
            //ENDPOINT
            //
            GeomPoint2d ptI = new GeomPoint2d(x, y_base);
            GeomPoint2d ptF = new GeomPoint2d(x, y_top);
        	
            cache.addOsnapItem(AppDefs.OSNAPMODE_INTERPOINT, new GeomPoint3d(ptI) );
            cache.addOsnapItem(AppDefs.OSNAPMODE_INTERPOINT, new GeomPoint3d(ptF) );

        	x += AppDefs.DEF_TMAR_EMBARCACAO_DISTHORIZ;
        }

		//COLUNAS_GRID
		//
		double y = yIns;

		double x_left = xIns;
		double x_right = xIns + this.comprimento;
		
        int numVert = AppDefs.DEF_TMAR_EMBARCACAO_NUMVERT;
        for(int i = 0; i < numVert; i++) {
            //ENDPOINT
            //
        	GeomPoint2d ptI = new GeomPoint2d(x_left, y);
            GeomPoint2d ptF = new GeomPoint2d(x_right, y);
        	
            cache.addOsnapItem(AppDefs.OSNAPMODE_INTERPOINT, new GeomPoint3d(ptI) );
            cache.addOsnapItem(AppDefs.OSNAPMODE_INTERPOINT, new GeomPoint3d(ptF) );

        	y += AppDefs.DEF_TMAR_EMBARCACAO_DISTVERT;
        }
        
        return cache;
	}
	
    /* DRAWING */
    
	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
		//TODO:
	}
    
	/* SELECT */
	
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
		return false;
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
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

    	String searchString = super.getSearchString() + "^" +
			"NOME=" + this.nome + "^" +
			"DESCRICAO=" + this.descricao + "^" +
			"CDATENDIMENTO=" + nf0.format( this.cdAtendimento ) + "^" +
			"NMEMBARCACAO=" + this.nmEmbarcacao;
		return searchString;
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

	public int getCdAtendimento() {
		return cdAtendimento;
	}

	public void setCdAtendimento(int cdAtendimento) {
		this.cdAtendimento = cdAtendimento;
	}

	public Date getDtAtendimento() {
		return dtAtendimento;
	}

	public void setDtAtendimento(Date dtAtendimento) {
		this.dtAtendimento = dtAtendimento;
	}

	public int getCdEmbarcacao() {
		return cdEmbarcacao;
	}

	public void setCdEmbarcacao(int cdEmbarcacao) {
		this.cdEmbarcacao = cdEmbarcacao;
	}

	public String getNmEmbarcacao() {
		return nmEmbarcacao;
	}

	public void setNmEmbarcacao(String nmEmbarcacao) {
		this.nmEmbarcacao = nmEmbarcacao;
	}

	public String getNmArmador() {
		return nmArmador;
	}

	public void setNmArmador(String nmArmador) {
		this.nmArmador = nmArmador;
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

	public double getDistVert() {
		return distVert;
	}

	public void setDistVert(double distVert) {
		this.distVert = distVert;
	}

	public double getDistHoriz() {
		return distHoriz;
	}

	public void setDistHoriz(double distHoriz) {
		this.distHoriz = distHoriz;
	}

	public void setLsItem(ArrayList<CadControleBacklistItemTransMarOData> lsItem) {
		this.lsItem = lsItem;
	}
	
}
