/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPerfilDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/06/2025
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
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.ICadEntity;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPerfilItemDrenagemODataRecord;
import br.com.tlmv.aicadxmod.drenagem.dao.record.CadPerfilDrenagemRecord;
import br.com.tlmv.aicadxmod.drenagem.vo.PontoCaixaInspecaoVO;

public class CadPerfilDrenagem extends CadEntity 
{
//Private Static
	private static double XMARGEM = 1.0; 
	private static double YMARGEM = 1.0; 
	
//Private
    private GeomPoint3d ptIns = null;
    private int trechoDrenagemId = -1;
    private String nomeTrechoDrenagem = "";

    //DRAW_LIMITS
    private double xMin = Double.MAX_VALUE;
    private double yMin = Double.MAX_VALUE;

    private double xMax = - Double.MAX_VALUE;
    private double yMax = - Double.MAX_VALUE;

    //DRAW_DIMENSION
    private double w = 0.0;
    private double h = 0.0;

    //LISTA_TRECHO_ITEM
    private ArrayList<CadPerfilItemDrenagemOData> lsTrechoItem = null;
    
    //FONT_SIZE
    private double fontTitleSzMili = 4.0;        
    private double fontHeaderSzMili = 2.0;        
    private double fontCellSzMili = 1.6;
    
//Public

    public CadPerfilDrenagem(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODDRPERFILDRENAGEM, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
    public void init(
		GeomPoint3d ptIns,
		int trechoDrenagemId,
        String nomeTrechoDrenagem) 
	{
		this.ptIns = new GeomPoint3d(ptIns);
		this.trechoDrenagemId = trechoDrenagemId;
		this.nomeTrechoDrenagem = nomeTrechoDrenagem; 
	    this.lsTrechoItem = new ArrayList<CadPerfilItemDrenagemOData>();
	}
	
	@Override
	public void init(ICadObject o) {
		CadPerfilDrenagem other = (CadPerfilDrenagem)o; 

		this.init(
			other.ptIns, 
			other.trechoDrenagemId,
			other.nomeTrechoDrenagem);
	}

	/* CREATE */
	
	public static CadPerfilDrenagem create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns, 
		int trechoDrenagemId,
        String nomeTrechoDrenagem) 
	{
    	CadPerfilDrenagem o = new CadPerfilDrenagem(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
    		ptIns, 
    		trechoDrenagemId, 
    		nomeTrechoDrenagem);
    	return o;
    }
	
	public static CadPerfilDrenagem create(CadPerfilDrenagem o)
	{
    	CadPerfilDrenagem other = new CadPerfilDrenagem(o.getBlkDef(), o.getLayer(), o.getLevel(), 0.0, false);
    	other.init(o);
    	return other;
    }
	
	public static CadPerfilDrenagem create(CadBlockDef blkDef, CadPerfilDrenagem o)
	{
    	CadPerfilDrenagem other = new CadPerfilDrenagem(blkDef, o.getLayer(), o.getLevel(), 0.0, false);
    	other.init(o);
    	return other;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPerfilDrenagem duplicate()
	{
		CadPerfilDrenagem other = CadPerfilDrenagem.create(this);
		return other;
	}
	
	@Override
	public CadPerfilDrenagem duplicate(CadBlockDef blkDef)
	{
		CadPerfilDrenagem other = CadPerfilDrenagem.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadPerfilDrenagem copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPerfilDrenagem other = CadPerfilDrenagem.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadPerfilDrenagem moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
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
	public CadPerfilDrenagem scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadPerfilDrenagem offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
    	
	/* LISTA_ITENS */
	
	public synchronized void loadAllItens(ArrayList<BaseObjectRecord> lsItens)
	{
		this.lsTrechoItem = new ArrayList<CadPerfilItemDrenagemOData>();
		
		CadBlockDef currBlockDef = this.getBlkDef();
		
		int pos = 1;
		for(BaseObjectRecord obj : lsItens) {
			CadPerfilItemDrenagemODataRecord oRec = (CadPerfilItemDrenagemODataRecord)obj;

			CadCaixaInspecaoDrenagem oCIAtual = (CadCaixaInspecaoDrenagem)currBlockDef.getEntity(oRec.getNumeroCIAtual()); 
			CadCaixaInspecaoDrenagem oCIAnterior = (CadCaixaInspecaoDrenagem)currBlockDef.getEntity(oRec.getNumeroCIAnterior()); 
			
			CadPerfilItemDrenagemOData oPerfilDrenagemItem = new CadPerfilItemDrenagemOData(
				this.getDocument(),
				pos++,
				oCIAtual, 
				oCIAnterior, 
				oRec.getD(), 
				oRec.getZCotaTerrenoPos(), 
				oRec.getZFundoPos(), 
				oRec.getZCotaEntradaPos(), 
				oRec.getZCotaSaidaPos(),
				null,
				null);
			oPerfilDrenagemItem.setObjectId( oRec.getObjectId() );
			this.lsTrechoItem.add(oPerfilDrenagemItem);
		}
	}
	
	public synchronized GeomDimension2d getItensEnvelop(boolean bSelected)
	{
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;

		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		
		GeomPoint2d ptMin2d = new GeomPoint2d(minX, minY);
		GeomPoint2d ptMax2d = new GeomPoint2d(maxX, maxY);

		for(CadPerfilItemDrenagemOData oItem : this.lsTrechoItem) {
			CadCaixaInspecaoDrenagem oCIAtual = oItem.getCIAtual();
			
			if( bSelected ) {
				oCIAtual.setSelected(bSelected);
				
				ArrayList<CadPipe> lsPipe = oCIAtual.findAllCadPipe();
				for(CadPipe oPipe : lsPipe) {
					oPipe.setSelected(bSelected);
				}
			}
			
			GeomPoint2d ptCI = new GeomPoint2d( oCIAtual.getPtIns() );
			double ptCI_x = ptCI.getX(); 
			double ptCI_y = ptCI.getY();
			
			if(ptCI_x < minX)
				minX = ptCI_x;
			if(ptCI_y < minY)
				minY = ptCI_y;
			
			if(ptCI_x > maxX)
				maxX = ptCI_x;
			if(ptCI_y > maxY)
				maxY = ptCI_y;
		}

		ptMin2d = new GeomPoint2d(minX, minY);
		ptMax2d = new GeomPoint2d(maxX, maxY);
		
		GeomDimension2d oGeomDim = new GeomDimension2d(ptMin2d, ptMax2d);
		return oGeomDim;
	}
	
	public synchronized int getSzLsTrechoItem() {
		return this.lsTrechoItem.size();
	}
	
	public synchronized CadPerfilItemDrenagemOData getTrechoItemAt(int pos) {
		int sz = this.lsTrechoItem.size();
		if(pos < sz) {
			CadPerfilItemDrenagemOData o = this.lsTrechoItem.get(pos);
			return o;
		}
		return null;
	}

	public synchronized void addTrechoItem(CadPerfilItemDrenagemOData oTrechoItem) {
		CadCaixaInspecaoDrenagem oCIAtual = (CadCaixaInspecaoDrenagem)oTrechoItem.getCIAtual();
		CadCaixaInspecaoDrenagem oCIAnterior = (CadCaixaInspecaoDrenagem)oTrechoItem.getCIAnterior();
		
		GeomPoint3d ptIns = new GeomPoint3d( oCIAtual.getPtIns() );
		
		double xI = ptIns.getX();
		double yI = ptIns.getY();
		
		double d = oTrechoItem.getD();

		double zCotaTerreno = oTrechoItem.getZCotaTerrenoPos();
		double zFundo = oTrechoItem.getZFundoPos();
		double zCotaEntrada = oTrechoItem.getZCotaEntradaPos();
		double zCotaSaida = oTrechoItem.getZCotaSaidaPos();

		// X - VALUES
		//		
		double xPos = xI + d;

		// Y - VALUES
		//		
		double yCotaTerreno = yI + zCotaTerreno;
		double yFundo = yI + zFundo;
		double yCotaEntrada = yI + zCotaEntrada;
		double yCotaSaida = yI + zCotaSaida;
		
	    if(xPos < this.xMin)
	    	this.xMin = xPos;
		
	    if(xPos > this.xMax)
	    	this.xMax = xPos;

	    if(yFundo < this.yMin)
	    	this.yMin = yFundo;

	    if(yCotaTerreno > this.yMax)
	    	this.yMax = yCotaTerreno;

	    this.w = Math.abs( this.xMax - this.xMin );
	    this.h = Math.abs( this.yMax - this.yMin );
	    
		this.lsTrechoItem.add(oTrechoItem);
	}

	/* TO_STRING */

	@Override
	public String toString() {
		String str = this.nomeTrechoDrenagem;
		return str;
	}

	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		int sz = this.getSzLsTrechoItem();	
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
		lsProperty.add( new ItemDataVO("Perfil", this.nomeTrechoDrenagem, false) );
		lsProperty.add( new ItemDataVO("Qtd.Caixas", nf0.format(sz), false) );
		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		int sz = this.getSzLsTrechoItem();	

		String str = String.format(
			"PtIns:%s;" +
			"Perfil:%s;" +
			"QtdCaixas:%s;",
			this.ptIns.toStr(),
    		this.nomeTrechoDrenagem,
    		nf0.format(sz) );
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
	
    public void redraw2d_CaixaInspecao_texto(ICadViewBase v, GeomPoint2d ptTopo2d, GeomPoint2d ptFundo2d, double sclFact, CadCaixaInspecaoDrenagem oCI, Graphics g) 
    {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

        Color c = AppDefs.DRPERFIL_CAIXAINSPECAOTEXTO_COLOR1;

		Color oldcol = GeomUtil.setColor(g, c);		
		
        double hTextSzMcs = this.fontCellSzMili * sclFact;
        double hTextLineMcs = 1.5 * hTextSzMcs;

    	GeomVector2d axisY = GeomUtil.axisY2d();
    	
    	GeomPoint2d ptTxt2d_topo = ptTopo2d.otherMoveTo(axisY, + hTextLineMcs);
    	GeomPoint2d ptTxt2d_fundo = ptFundo2d.otherMoveTo(axisY, - hTextLineMcs);
        
	    String strPv = oCI.getPv();
		DrawUtil.drawTextMcs(v, strPv, ptTxt2d_topo, hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        
	    String strCotaTerreno = String.format("%s m", nf3.format(oCI.getCt()));
		DrawUtil.drawTextMcs(v, strCotaTerreno, ptTxt2d_fundo, hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		
		GeomUtil.setColor(g, oldcol);
    }

    public void redraw2d_CotaTerreno(ICadViewBase v, GeomPoint2d ptCotaTerreno_I, GeomPoint2d ptCotaTerreno_F, double sclFact, Graphics g) 
    {
        Color c = AppDefs.DRPERFIL_TERRENO_COLOR1;

		Color oldcol = GeomUtil.setColor(g, c);		

        DrawUtil.drawLineMcs(v, ptCotaTerreno_I, ptCotaTerreno_F, g);

		GeomUtil.setColor(g, oldcol);		
    }

    public void redraw2d_CaixaInspecao(ICadViewBase v, GeomPoint2d ptCotaTerreno, GeomPoint2d ptFundo, double sclFact, double diametro, Graphics g) 
    {
		Color c = AppDefs.DRPERFIL_CAIXAINSPECAO_COLOR1;

		Color oldcol = GeomUtil.setColor(g, c);		

		double raio = diametro / 2.0;
    	
    	GeomVector2d axisX = GeomUtil.axisX2d();
    	
    	GeomPoint2d ptCotaTerreno_left = ptCotaTerreno.otherMoveTo(axisX, - raio);
    	GeomPoint2d ptCotaTerreno_right = ptCotaTerreno.otherMoveTo(axisX, + raio);
    	
    	GeomPoint2d ptFundo_left = ptFundo.otherMoveTo(axisX, - raio);
    	GeomPoint2d ptFundo_right = ptFundo.otherMoveTo(axisX, + raio);
    	
        DrawUtil.drawLineMcs(v, ptCotaTerreno_left, ptCotaTerreno_right, g);
        DrawUtil.drawLineMcs(v, ptCotaTerreno_right, ptFundo_right, g);
        DrawUtil.drawLineMcs(v, ptFundo_right, ptFundo_left, g);
        DrawUtil.drawLineMcs(v, ptFundo_left, ptCotaTerreno_left, g);

		GeomUtil.setColor(g, oldcol);		
    }
    
    public void redraw2d_Tubulacao(ICadViewBase v, GeomPoint2d ptCotaSaida_I, GeomPoint2d ptCotaEntrada_F, double sclFact, double diametroTubulacao, Graphics g) 
    {
		Color c = AppDefs.DRPERFIL_TUBULACAO_COLOR1;

		Color oldcol = GeomUtil.setColor(g, c);		

		double raioTubulacao = diametroTubulacao / 2.0;
    	
    	GeomVector2d axisY = GeomUtil.axisY2d();
    	
    	GeomPoint2d ptCotaSaida_I_top = ptCotaSaida_I.otherMoveTo(axisY, - raioTubulacao);
    	GeomPoint2d ptCotaSaida_I_bottom = ptCotaSaida_I.otherMoveTo(axisY, + raioTubulacao);
    	
    	GeomPoint2d ptCotaEntrada_F_top = ptCotaEntrada_F.otherMoveTo(axisY, - raioTubulacao);
    	GeomPoint2d ptCotaEntrada_F_bottom = ptCotaEntrada_F.otherMoveTo(axisY, + raioTubulacao);
    	
        DrawUtil.drawLineMcs(v, ptCotaSaida_I_top, ptCotaEntrada_F_top, g);
        DrawUtil.drawLineMcs(v, ptCotaEntrada_F_top, ptCotaEntrada_F_bottom, g);
        DrawUtil.drawLineMcs(v, ptCotaEntrada_F_bottom, ptCotaSaida_I_bottom, g);
        DrawUtil.drawLineMcs(v, ptCotaSaida_I_bottom, ptCotaSaida_I_top, g);

		GeomUtil.setColor(g, oldcol);		
    }
    
    public void redraw2d_Margin(ICadViewBase v, double xMin, double yMin, double xMax, double yMax, double sclFact, ArrayList<PontoCaixaInspecaoVO> lsPontoCaixaInspecao, Graphics g)
    {
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
        double hTextSzMcs = this.fontCellSzMili * sclFact;
        double hTextLineMcs = 1.5 * hTextSzMcs;

        Color c = AppDefs.DRPERFIL_MARGEM_COLOR1;

		Color oldcol = GeomUtil.setColor(g, c);		

		//GRID: MIN-MAX VALUES
		//
		GeomRect2d oRect = DrawUtil.drawGridLineMcs(
			v, 
			xMin - CadPerfilDrenagem.XMARGEM, 
			yMin - CadPerfilDrenagem.YMARGEM, 
			xMax + CadPerfilDrenagem.XMARGEM, 
			yMax + CadPerfilDrenagem.YMARGEM, 
			g);

		//MIN VALUES
		//
		GeomPoint2d ptMin = new GeomPoint2d( oRect.getPtMin() );
		
		xMin = ptMin.getX();
		yMin = ptMin.getY();

		//MAX VALUES
		//
		GeomPoint2d ptMax = new GeomPoint2d( oRect.getPtMax() );
		
		xMax = ptMax.getX();
		yMax = ptMax.getY();
		
		//CARD: MIN-MAX VALUES
		//
		double xCardMin = xMin - (1.5 * CadPerfilDrenagem.XMARGEM);
		double yCardMin = yMin - (3.0 * CadPerfilDrenagem.YMARGEM);
		//
		double xCardMax = xMax;
		double yCardMax = yMin - (1.0 * CadPerfilDrenagem.YMARGEM);

		DrawUtil.drawRectangleMcs(
			v, 
			xCardMin, 
			yCardMin, 
			xCardMax, 
			yCardMax, 
			g);
		
		//HORIZONTAL_POSITION
		//
		for(PontoCaixaInspecaoVO o : lsPontoCaixaInspecao) {
			GeomPoint2d ptIns2d = new GeomPoint2d( o.getPtCotaTerreno() );
			
			double xPos = ptIns2d.getX();
			double yTopoPos = yMax + (0.5 * CadPerfilDrenagem.YMARGEM);
			double yBottomPos = yCardMin - (0.5 * CadPerfilDrenagem.YMARGEM);
			
			double dDeclividade = o.getDeclividade();
			double dDiametro = o.getDiametro();
			double dComprimento = o.getComprimento();

			GeomPoint2d ptTopo = new GeomPoint2d(xPos, yTopoPos); 
			GeomPoint2d ptBottom = new GeomPoint2d(xPos, yBottomPos); 
			
			GeomUtil.setColor(g, AppDefs.DRPERFIL_CAIXAINSPECAOEIXO_COLOR1);		

	        DrawUtil.drawLineMcs(v, ptTopo, ptBottom, g);

			GeomUtil.setColor(g, AppDefs.DRPERFIL_TEXTOTITULO_COLOR1);		

			double xPtTxt1 = xPos + (0.5 * CadPerfilDrenagem.XMARGEM);
			double yPtTxt1 = yCardMax - (0.5 * CadPerfilDrenagem.YMARGEM);
			
			String strDeclividade = String.format("%s", nf3.format(dDeclividade));
			DrawUtil.drawTextMcs(v, strDeclividade, new GeomPoint2d(xPtTxt1, yPtTxt1), hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
			yPtTxt1 -= (0.5 * CadPerfilDrenagem.YMARGEM);

			String strDiametro = String.format("%s", nf3.format(dDiametro));
			DrawUtil.drawTextMcs(v, strDiametro, new GeomPoint2d(xPtTxt1, yPtTxt1), hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
			yPtTxt1 -= (0.5 * CadPerfilDrenagem.YMARGEM);

			String strComprimento = String.format("%s", nf3.format(dComprimento));
			DrawUtil.drawTextMcs(v, strComprimento, new GeomPoint2d(xPtTxt1, yPtTxt1), hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		}

		//TITLE_COLUMNS
		//
		GeomUtil.setColor(g, AppDefs.DRPERFIL_TEXTOTITULO_COLOR1);		

		double xPtTxt2 = xCardMin + (0.5 * CadPerfilDrenagem.XMARGEM);
		double yPtTxt2 = yCardMax - (0.5 * CadPerfilDrenagem.YMARGEM);
		
		String strDeclividade = "i= (m/m)";
		DrawUtil.drawTextMcs(v, strDeclividade, new GeomPoint2d(xPtTxt2, yPtTxt2), hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		yPtTxt2 -= (0.5 * CadPerfilDrenagem.YMARGEM);
		
		String strDiametro = "D= (m)";
		DrawUtil.drawTextMcs(v, strDiametro, new GeomPoint2d(xPtTxt2, yPtTxt2), hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		yPtTxt2 -= (0.5 * CadPerfilDrenagem.YMARGEM);

		String strComprimento = "L= (m)";
		DrawUtil.drawTextMcs(v, strComprimento, new GeomPoint2d(xPtTxt2, yPtTxt2), hTextSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
		
		GeomUtil.setColor(g, oldcol);		
    }
    	
    public void redraw2d_PerfilDrenagem(ICadViewBase v, GeomPoint2d ptIns, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

    	GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double fHeaderSzMcs = this.fontHeaderSzMili * sclFact;
        double fCellSzMcs = this.fontCellSzMili * sclFact;
    	
        double hTextLineMcs = 1.5 * fCellSzMcs;

		String strNomeTrechoDrenagem = this.nomeTrechoDrenagem;
		String strTitle1 = String.format("Perfil: %s", strNomeTrechoDrenagem);

		int sz = this.getSzLsTrechoItem();	

		String strQuantidadeCaixas = nf0.format(sz);
        String strTitle2 = String.format("Quantidade de Caixas: %s", strQuantidadeCaixas);
        
        double hTableTitleMcs = 5.0 * hTextLineMcs;
        double hTableHeaderMcs = 3.0 * hTextLineMcs;
        double hTableRowMcs = 2.0 * hTextLineMcs;

		double xPtIns = ptIns.getX();
		double yPtIns = ptIns.getY();
        
		double xMin = Double.MAX_VALUE;
		double yMin = Double.MAX_VALUE;
        
		double xMax = - Double.MAX_VALUE;
		double yMax = - Double.MAX_VALUE;

        /* TRECHO_DRENAGEM */
		
		ArrayList<PontoCaixaInspecaoVO> lsPontoCaixaInspecao = new ArrayList<PontoCaixaInspecaoVO>();

		if(sz > 0) {
			CadPerfilItemDrenagemOData oTrechoAtual_1 = this.getTrechoItemAt(0);
			
			CadCaixaInspecaoDrenagem oCIAtual_1 = (CadCaixaInspecaoDrenagem)oTrechoAtual_1.getCIAtual();
			int fromNumeroCI = oCIAtual_1.getNumeroCI();

			double diamCIAtual_1 = oCIAtual_1.getDiametroMeter();
			
			double pipeDiameterMeter = oCIAtual_1.getDiametroTubulacaoMeter();
			double pipeRadiusMeter = pipeDiameterMeter / 2.0;

			GeomPoint3d ptOCIAtual_1 = oCIAtual_1.getPtIns();
			
			double xCIAtual_1 = ptOCIAtual_1.getX();
			double yCIAtual_1 = ptOCIAtual_1.getY();
			
			double d_atual_1 = oTrechoAtual_1.getD();

			double zCotaTerreno_1 = oTrechoAtual_1.getZCotaTerrenoPos();
			double zFundo_1 = oTrechoAtual_1.getZFundoPos();
			double zCotaEntrada_1 = oTrechoAtual_1.getZCotaEntradaPos();
			double zCotaSaida_1 = oTrechoAtual_1.getZCotaSaidaPos();

			double dH_1 = Math.abs( zCotaSaida_1 - zCotaTerreno_1 );
			
			double dDeclividade_1 = (dH_1 / d_atual_1);
			double dDiametro_1 = pipeDiameterMeter;
			double dComprimento_1 = d_atual_1;
			
			// X - VALUES
			//
			double xPos_1 = xPtIns + (xCIAtual_1 + d_atual_1);
			double yPos_1 = yPtIns; 
			
			// Y -VALUES
			//
			double yCotaTerreno_1 = yPos_1 + zCotaTerreno_1;
			double yFundo_1 = yPos_1 + zFundo_1;
			double yCotaEntrada_1 = yPos_1 + zCotaEntrada_1;
			double yCotaSaida_1 = yPos_1 + zCotaSaida_1;
			
			GeomPoint2d ptLine2d_CotaTerreno_I = new GeomPoint2d(xPos_1, yCotaTerreno_1);
			GeomPoint2d ptLine2d_Fundo_I = new GeomPoint2d(xPos_1, yFundo_1);
			GeomPoint2d ptLine2d_CotaEntrada_I = new GeomPoint2d(xPos_1, yCotaEntrada_1);
			GeomPoint2d ptLine2d_CotaSaida_I = new GeomPoint2d(xPos_1, yCotaSaida_1);

			PontoCaixaInspecaoVO o = new PontoCaixaInspecaoVO(
				ptLine2d_CotaTerreno_I,
				ptLine2d_Fundo_I,
				ptLine2d_CotaEntrada_I,
				ptLine2d_CotaSaida_I,
				dDeclividade_1,
				dDiametro_1,
				dComprimento_1);
			lsPontoCaixaInspecao.add(o);
			
			//CAIXA_INSPECAO_I
			//
			this.redraw2d_CaixaInspecao(v, ptLine2d_CotaTerreno_I, ptLine2d_Fundo_I, sclFact, diamCIAtual_1, g);
	        this.redraw2d_CaixaInspecao_texto(v, ptLine2d_CotaTerreno_I, ptLine2d_Fundo_I, sclFact, oCIAtual_1, g);
	        
			//MIN-MAX VALUES
			//
			if(xPos_1 < xMin)
				xMin = xPos_1;
			if(xPos_1 > xMax)
				xMax = xPos_1;
			
			if(yCotaTerreno_1 > yMax)
				yMax = yCotaTerreno_1;
			if(yCotaTerreno_1 < yMin)
				yMin = yCotaTerreno_1;
			
			if(yCotaSaida_1 < yMin)
				yMin = yCotaSaida_1;
			if(yCotaSaida_1 > yMax)
				yMax = yCotaSaida_1;
			
			for(int i = 1; i < sz; i++) {
				CadPerfilItemDrenagemOData oTrechoProximo_2 = this.getTrechoItemAt(i);

				CadCaixaInspecaoDrenagem oCIProximo_2 = (CadCaixaInspecaoDrenagem)oTrechoProximo_2.getCIAtual();
				int toNumeroCI = oCIProximo_2.getNumeroCI();

				double diamCIProximo_2 = oCIProximo_2.getDiametroMeter();

				GeomPoint3d ptOCIProximo_2 = oCIProximo_2.getPtIns();
				
				double xCIProximo_2 = ptOCIProximo_2.getX();
				double yCIProximo_2 = ptOCIProximo_2.getY();
				
				double d_proximo_2 = oTrechoProximo_2.getD();

				double zCotaTerreno_2 = oTrechoProximo_2.getZCotaTerrenoPos();
				double zFundo_2 = oTrechoProximo_2.getZFundoPos();
				double zCotaEntrada_2 = oTrechoProximo_2.getZCotaEntradaPos();
				double zCotaSaida_2 = oTrechoProximo_2.getZCotaSaidaPos();

				double dH_2 = Math.abs( zCotaSaida_2 - zCotaTerreno_2 );
				
				double dDeclividade_2 = (dH_2 / d_proximo_2);
				double dDiametro_2 = pipeDiameterMeter;
				double dComprimento_2 = d_proximo_2;
				
				// X - VALUES
				//
				double xPos_2 = xPtIns + (xCIProximo_2 + d_proximo_2);
				double yPos_2 = yPtIns; 
				
				// Y -VALUES
				//
				double yCotaTerreno_2 = yPos_2 + zCotaTerreno_2;
				double yFundo_2 = yPos_2 + zFundo_2;
				double yCotaEntrada_2 = yPos_2 + zCotaEntrada_2;
				double yCotaSaida_2 = yPos_2 + zCotaSaida_2;
				
				GeomPoint2d ptLine2d_CotaTerreno_F = new GeomPoint2d(xPos_2, yCotaTerreno_2);
				GeomPoint2d ptLine2d_Fundo_F = new GeomPoint2d(xPos_2, yFundo_2);
				GeomPoint2d ptLine2d_CotaEntrada_F = new GeomPoint2d(xPos_2, yCotaEntrada_2);
				GeomPoint2d ptLine2d_CotaSaida_F = new GeomPoint2d(xPos_2, yCotaSaida_2);
				
				o = new PontoCaixaInspecaoVO(
					ptLine2d_CotaTerreno_F,
					ptLine2d_Fundo_F,
					ptLine2d_CotaEntrada_F,
					ptLine2d_CotaSaida_F,
					dDeclividade_2,
					dDiametro_2,
					dComprimento_2);
				lsPontoCaixaInspecao.add(o);
				
				//CAIXA_INSPECAO_F
				//
				this.redraw2d_CaixaInspecao(v, ptLine2d_CotaTerreno_F, ptLine2d_Fundo_F, sclFact, diamCIProximo_2, g);
		        this.redraw2d_CaixaInspecao_texto(v, ptLine2d_CotaTerreno_F, ptLine2d_Fundo_F, sclFact, oCIProximo_2, g);

				//COTA_TERRENO
		    	//
		        this.redraw2d_CotaTerreno(v, ptLine2d_CotaTerreno_I, ptLine2d_CotaTerreno_F, sclFact, g);

				//TUBULACAO: COTA_SAIDA_I - COTA_ENTRADA_F
		        //
		        this.redraw2d_Tubulacao(v, ptLine2d_CotaSaida_I, ptLine2d_CotaEntrada_F, sclFact, pipeDiameterMeter, g);

				//MIN-MAX VALUES
				//
				if(xPos_2 < xMin)
					xMin = xPos_2;
				if(xPos_2 > xMax)
					xMax = xPos_2;
				
				if(yCotaTerreno_2 > yMax)
					yMax = yCotaTerreno_2;
				if(yCotaTerreno_2 < yMin)
					yMin = yCotaTerreno_2;
				
				if(yCotaSaida_2 < yMin)
					yMin = yCotaSaida_2;
				if(yCotaSaida_2 > yMax)
					yMax = yCotaSaida_2;
		        
		        //EXCHANGE_FROM: PROXIMO -> ATUAL
		        //
				pipeDiameterMeter = oCIProximo_2.getDiametroTubulacaoMeter();
				pipeRadiusMeter = pipeDiameterMeter / 2.0;

				ptOCIAtual_1 = new GeomPoint3d( ptOCIProximo_2 );
				
				xCIAtual_1 = xCIProximo_2;
				yCIAtual_1 = yCIProximo_2;
				
				d_atual_1 = oTrechoProximo_2.getD();

				zCotaTerreno_1 = zCotaTerreno_2;
				zFundo_1 = zFundo_2;
				zCotaEntrada_1 = zCotaEntrada_2;
				zCotaSaida_1 = zCotaSaida_2;
				
				ptLine2d_CotaTerreno_I = ptLine2d_CotaTerreno_F;
				ptLine2d_Fundo_I = ptLine2d_Fundo_F;
				ptLine2d_CotaEntrada_I = ptLine2d_CotaEntrada_F;
				ptLine2d_CotaSaida_I = ptLine2d_CotaSaida_F;
			}
			
			//MARGIN_AND_GRIDLINES
			//
			redraw2d_Margin(v, xMin, yMin, xMax, yMax, sclFact, lsPontoCaixaInspecao, g);
		}
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
		        	CadPerfilDrenagem oTable = this.duplicate();
		        	oTable.moveTo(ptBase3dMcs, pt3dMcs);

		            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPerfilDrenagem oTable = this.duplicate();
		        	oTable.mirror(ptBase3dMcs, pt3dMcs);

		            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadPerfilDrenagem oTable = this.duplicate();
			        	oTable.scaleTo(dist, ptBase3dMcs, pt3dMcs);

			            ptIns2d = new GeomPoint2d(oTable.ptIns);        		            
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPerfilDrenagem other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);

		            ptIns2d = new GeomPoint2d(other.ptIns);        		            
		        }
	        }
        }
        
        this.redraw2d_PerfilDrenagem(v, ptIns2d, sclFact, bDragMode, bSelEnt, g);
        
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
		
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;

        double xPt2dMcs = pt2dMcs.getX(); 
        double yPt2dMcs = pt2dMcs.getY(); 
        
		double w = (this.xMax - this.xMin) + (2.0 * CadPerfilDrenagem.XMARGEM);
		double h = (this.yMax - this.yMin) + (2.0 * CadPerfilDrenagem.YMARGEM);

		double xMin = this.ptIns.getX() - distMax;
		double yMin = this.ptIns.getY() - distMax;
		
		double xMax = (this.ptIns.getX() + w) + distMax;
		double yMax = (this.ptIns.getY() + h) + distMax;

        if( ( (xPt2dMcs >= xMin) && (xPt2dMcs <= xMax) ) &&
        	( (yPt2dMcs >= yMin) && (yPt2dMcs <= yMax) ) ) 
        {
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
		BaseODataDao odDao = dao.createODataDao(AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA); 

		String strCadRefEntityId = Integer.toString(this.getObjectId());
		
		int sz = this.lsTrechoItem.size();
		for(int i = 0; i < sz; i++) {
			CadPerfilItemDrenagemOData oItem = (CadPerfilItemDrenagemOData)this.lsTrechoItem.get(i);
			oItem.setCadRefEntityId(strCadRefEntityId);
			oItem.setObjVer(objVer);

			Object[] arrVal = {
				new Double( this.ptIns.getX() ),
				new Double( this.ptIns.getY() ),
				new Double( this.ptIns.getZ() ),
				//
				new Integer( this.trechoDrenagemId ),
				new String( this.nomeTrechoDrenagem ),
				//
				new Double( this.xMin ),
				new Double( this.yMin ),
				new Double( this.xMax ),
				new Double( this.yMax ),
				//
				new Double( this.w ),
				new Double( this.h ) 
			};
			
			CadPerfilItemDrenagemODataRecord odataRec = new CadPerfilItemDrenagemODataRecord(strCadRefEntityId, oItem);
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
		
		Object[] arrVal = {
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			new Integer( this.trechoDrenagemId ),
			new String( this.nomeTrechoDrenagem ),
			new Double( this.xMin ),
			new Double( this.yMin ),
			new Double( this.xMax ),
			new Double( this.yMax ),
			new Double( this.w ),
			new Double( this.h )
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadPerfilDrenagemRecord entRec = new CadPerfilDrenagemRecord(this); 
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
		GeomPoint3d ptMin = new GeomPoint3d(this.ptIns); 
		GeomPoint3d ptMax = new GeomPoint3d(this.ptIns);
		
//		double minX = Double.MAX_VALUE;
//		double minY = Double.MAX_VALUE;
//		
//		double maxX = - Double.MAX_VALUE;
//		double maxY = - Double.MAX_VALUE;
//		
//		int szLsTrechoItem = this.lsTrechoItem.size();
//		for(int i = 0; i < szLsTrechoItem; i++) {
//			CadPerfilItemDrenagemOData oPerfilDrenagemItem = (CadPerfilItemDrenagemOData)this.lsTrechoItem.get(i);
//
//			oPerfilDrenagemItem.get
//			
//			CadPerfilItemDrenagemODataRecord ptRec = new CadPerfilItemDrenagemODataRecord(this.getObjectId(), oPerfilDrenagemItem);
//			odDao.insertOrUpdate(schemaName, (CadPerfilItemDrenagemODataRecord) ptRec);
//		}		

		GeomDimension3d oDim = new GeomDimension3d(ptMin, ptMax); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint2d ptMin = new GeomPoint2d(this.ptIns); 
		GeomPoint2d ptMax = new GeomPoint2d(this.ptIns);
		
//		double minX = Double.MAX_VALUE;
//		double minY = Double.MAX_VALUE;
//		
//		double maxX = - Double.MAX_VALUE;
//		double maxY = - Double.MAX_VALUE;
//		
//		int szLsTrechoItem = this.lsTrechoItem.size();
//		for(int i = 0; i < szLsTrechoItem; i++) {
//			CadPerfilItemDrenagemOData oPerfilDrenagemItem = (CadPerfilItemDrenagemOData)this.lsTrechoItem.get(i);
//
//			oPerfilDrenagemItem.get
//			
//			CadPerfilItemDrenagemODataRecord ptRec = new CadPerfilItemDrenagemODataRecord(this.getObjectId(), oPerfilDrenagemItem);
//			odDao.insertOrUpdate(schemaName, (CadPerfilItemDrenagemODataRecord) ptRec);
//		}		

		GeomDimension2d oDim = new GeomDimension2d(ptMin, ptMax); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"TRECHO=" + this.nomeTrechoDrenagem;
		return searchString;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public int getTrechoDrenagemId() {
		return trechoDrenagemId;
	}

	public void setTrechoDrenagemId(int trechoDrenagemId) {
		this.trechoDrenagemId = trechoDrenagemId;
	}

	public String getNomeTrechoDrenagem() {
		return nomeTrechoDrenagem;
	}

	public void setNomeTrechoDrenagem(String nomeTrechoDrenagem) {
		this.nomeTrechoDrenagem = nomeTrechoDrenagem;
	}

	public double getXMin() {
		return xMin;
	}

	public void setXMin(double xMin) {
		this.xMin = xMin;
	}

	public double getYMin() {
		return yMin;
	}

	public void setYMin(double yMin) {
		this.yMin = yMin;
	}

	public double getXMax() {
		return xMax;
	}

	public void setXMax(double xMax) {
		this.xMax = xMax;
	}

	public double getYMax() {
		return yMax;
	}

	public void setYMax(double yMax) {
		this.yMax = yMax;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

}
