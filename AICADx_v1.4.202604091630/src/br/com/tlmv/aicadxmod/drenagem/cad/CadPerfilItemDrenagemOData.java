/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPerfilItemDrenagemOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 01/07/2025
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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadPerfilItemDrenagemOData extends CadObject
{
//Private
	private int perfilDrenagemItemId = AppDefs.NULL_INT;
    private CadCaixaInspecaoDrenagem oCIAtual = null;
    private CadCaixaInspecaoDrenagem oCIAnterior = null;
    private double d = 0.0;
    private double zCotaTerrenoPos = 0.0;
    private double zFundoPos = 0.0;
    private double zCotaEntradaPos = 0.0;
    private double zCotaSaidaPos = 0.0;
    CadPerfilItemDrenagemOData oPerfilItem = null;
    CadMemoriaCalculoItemDrenagemOData oItemAtual = null;
    
//Public
	
    public CadPerfilItemDrenagemOData(CadDocumentDef doc) {
    	super(AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA, doc, null);

    	this.perfilDrenagemItemId = AppDefs.NULL_INT;
        this.oCIAtual = null;
        this.oCIAnterior = null;
        this.d = 0.0;
        this.zCotaTerrenoPos = 0.0;
        this.zFundoPos = 0.0;
        this.zCotaEntradaPos = 0.0;
        this.zCotaSaidaPos = 0.0;
        CadPerfilItemDrenagemOData oPerfilItem = null;
        CadMemoriaCalculoItemDrenagemOData oItemAtual = null;
    }

    public CadPerfilItemDrenagemOData(
    	CadDocumentDef doc,
    	int perfilDrenagemItemId,
	    CadCaixaInspecaoDrenagem oCIAtual,
	    CadCaixaInspecaoDrenagem oCIAnterior,
	    double d,
	    double zCotaTerrenoPos,
	    double zFundoPos,
	    double zCotaEntradaPos,
	    double zCotaSaidaPos,
	    CadPerfilItemDrenagemOData oPerfilItem,
	    CadMemoriaCalculoItemDrenagemOData oItemAtual)
    {
    	super(AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA, doc, null);

    	this.init(
			perfilDrenagemItemId,
    		oCIAtual,
    		oCIAnterior,
    		d,
    		zCotaTerrenoPos,
    		zFundoPos,
    		zCotaEntradaPos,
    		zCotaSaidaPos,
    		oPerfilItem,
    		oItemAtual);
    }
    
    public CadPerfilItemDrenagemOData(
    	CadDocumentDef doc,
    	int perfilDrenagemItemId,
		CadCaixaInspecaoDrenagem oCI,
		CadPerfilItemDrenagemOData oTrechoAnterior)
    {
    	super(AppDefs.OBJTYPE_PERFILDRENAGEMITEM_ODATA, doc, null);

    	this.init(perfilDrenagemItemId, oCI, oTrechoAnterior);
    }

    /* Methodes */

    public void init(
    	int perfilDrenagemItemId,
	    CadCaixaInspecaoDrenagem oCIAtual,
	    CadCaixaInspecaoDrenagem oCIAnterior,
	    double d,
	    double zCotaTerrenoPos,
	    double zFundoPos,
	    double zCotaEntradaPos,
	    double zCotaSaidaPos,
	    CadPerfilItemDrenagemOData oPerfilItem,
	    CadMemoriaCalculoItemDrenagemOData oItemAtual)
    {
    	this.perfilDrenagemItemId = perfilDrenagemItemId;
        this.oCIAtual = oCIAtual;
        this.oCIAnterior = oCIAnterior;
        this.d = d;
        this.zCotaTerrenoPos = zCotaTerrenoPos;
        this.zFundoPos = zFundoPos;
        this.zCotaEntradaPos = zCotaEntradaPos;
        this.zCotaSaidaPos = zCotaSaidaPos;
        this.oPerfilItem = oPerfilItem;
        this.oItemAtual = oItemAtual;
    }
    
	public void init(
    	int perfilDrenagemItemId,
		CadCaixaInspecaoDrenagem oCI,
		CadPerfilItemDrenagemOData oTrechoAnterior)
    {
    	this.oCIAtual = oCI;
    	this.oCIAnterior = null;
    	if(oTrechoAnterior != null) {
    		this.oCIAnterior = oTrechoAnterior.getCIAtual();
    	}

    	GeomPoint3d oPtInsAtual = new GeomPoint3d(this.oCIAtual.getPtIns());
    	//
    	double xF = oPtInsAtual.getX();
        double yF = oPtInsAtual.getY();
        //
    	double xI = xF;
        double yI = yF;

    	if(this.oCIAnterior != null) {
    		GeomPoint3d ptIns = new GeomPoint3d(this.oCIAnterior.getPtIns());
    		//
    		xI = ptIns.getX();
    		yI = ptIns.getY();
    	}
    	//
        double dX = Math.abs( xF - xI );
        double dY = Math.abs( yF - yI );

        this.d = Math.sqrt( (dX * dX) + (dY * dY) );
    	
        this.perfilDrenagemItemId = perfilDrenagemItemId;
        this.zCotaTerrenoPos = this.oCIAtual.getCt();
        this.zFundoPos = this.oCIAtual.getCb();
        this.zCotaEntradaPos = this.oCIAtual.getCotaEntrada();
        this.zCotaSaidaPos = this.oCIAtual.getCotaSaida();
    }
	
	@Override
	public void init(ICadObject o) {
		CadPerfilItemDrenagemOData other = (CadPerfilItemDrenagemOData)o;
		
	    this.init(
    		other.getPerfilDrenagemItemId(),
    		other.getCIAtual(),
    		other.getCIAnterior(),
    		other.getD(),
    		other.getZCotaTerrenoPos(),
    		other.getZFundoPos(),
    		other.getZCotaEntradaPos(),
    		other.getZCotaSaidaPos(),
    		other.getPerfilItem(),
    		other.getItemAtual() );
	}

	/* TO_STRING */
       
    @Override
	public String toString() {
    	String str = String.format(
    		"PerfilDrenagemItemId:%s;" +
    		"NumeroCI-Atual:%s;" +
    		"NumeroCI-Anterior:%s;" +
    		"D:%s;" +
    		"ZCotaTerreno:%s;" +
    		"ZFundo:%s;" +
    		"ZCotaEntrada:%s;" +
    		"ZCotaSaida:%s;", 
    	    this.perfilDrenagemItemId,
    	    oCIAtual.getNumeroCI(),
    	    oCIAnterior.getNumeroCI(),
    	    this.d,
    	    this.zCotaTerrenoPos,
    	    this.zFundoPos,
    	    this.zCotaEntradaPos,
    	    this.zCotaSaidaPos);
		return str;
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

	/* RESET */

	@Override
	public void reset() {
		// TODO:
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

    	String str = String.format(
    		"PerfilDrenagemItemId:%s;" +
    		"NumeroCI-Atual:%s;" +
    		"NumeroCI-Anterior:%s;" +
    		"D:%s;" +
    		"ZCotaTerreno:%s;" +
    		"ZFundo:%s;" +
    		"ZCotaEntrada:%s;" +
    		"ZCotaSaida:%s;", 
    		this.perfilDrenagemItemId,
    	    oCIAtual.getNumeroCI(),
    	    oCIAnterior.getNumeroCI(),
    	    nf6.format(this.d),
    		nf6.format(this.zCotaTerrenoPos),
			nf6.format(this.zFundoPos),
			nf6.format(this.zCotaEntradaPos),
			nf6.format(this.zCotaSaidaPos) );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* Getter/Setters */

	public double getZCotaTerrenoPos() {
		return zCotaTerrenoPos;
	}

	public void setZCotaTerrenoPos(double zCotaTerrenoPos) {
		this.zCotaTerrenoPos = zCotaTerrenoPos;
	}

	public double getZFundoPos() {
		return zFundoPos;
	}

	public void setZFundoPos(double zFundoPos) {
		this.zFundoPos = zFundoPos;
	}

	public double getZCotaEntradaPos() {
		return zCotaEntradaPos;
	}

	public void setZCotaEntradaPos(double zCotaEntradaPos) {
		this.zCotaEntradaPos = zCotaEntradaPos;
	}

	public double getZCotaSaidaPos() {
		return zCotaSaidaPos;
	}

	public void setZCotaSaidaPos(double zCotaSaidaPos) {
		this.zCotaSaidaPos = zCotaSaidaPos;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public CadCaixaInspecaoDrenagem getCIAtual() {
		return oCIAtual;
	}

	public void setoCIAtual(CadCaixaInspecaoDrenagem oCIAtual) {
		this.oCIAtual = oCIAtual;
	}

	public CadCaixaInspecaoDrenagem getCIAnterior() {
		return oCIAnterior;
	}

	public void setoCIAnterior(CadCaixaInspecaoDrenagem oCIAnterior) {
		this.oCIAnterior = oCIAnterior;
	}

	public int getPerfilDrenagemItemId() {
		return perfilDrenagemItemId;
	}

	public void setPerfilDrenagemItemId(int perfilDrenagemItemId) {
		this.perfilDrenagemItemId = perfilDrenagemItemId;
	}

	public CadMemoriaCalculoItemDrenagemOData getItemAtual() {
		return oItemAtual;
	}

	public void setItemAtual(CadMemoriaCalculoItemDrenagemOData oItemAtual) {
		this.oItemAtual = oItemAtual;
	}

	public CadPerfilItemDrenagemOData getPerfilItem() {
		return oPerfilItem;
	}

	public void setPerfilItem(CadPerfilItemDrenagemOData oPerfilItem) {
		this.oPerfilItem = oPerfilItem;
	}

}
