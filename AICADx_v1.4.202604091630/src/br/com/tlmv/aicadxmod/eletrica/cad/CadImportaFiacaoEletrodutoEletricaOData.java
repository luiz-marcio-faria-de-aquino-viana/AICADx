/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * CadImportaFiacaoEletrodutoEletricaOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/02/2026
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

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadImportaFiacaoEletrodutoEletricaOData extends CadObject 
{
//Private
    private int rowId = -1;
    private String hnd;
    private String qdr;
    private String cir;
    private String lbl;
    private int fia;

//Public

    public CadImportaFiacaoEletrodutoEletricaOData(CadDocumentDef doc)
    {
    	super(AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA, doc, null);    	
    }

    public CadImportaFiacaoEletrodutoEletricaOData(CadImportaFiacaoEletrodutoEletricaOData other)
    {
    	super(AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA, other.getDocument(), null);    	

    	this.init(other);
    }
    
    /* Methodes */
    
    public void init(
	    int rowId,
	    String hnd,
    	String qdr, 
    	String cir, 
    	String lbl, 
    	int fia)
    {
		this.rowId = rowId;
		this.hnd = hnd;
        this.qdr = qdr;
        this.cir = cir;
        this.lbl = lbl;
        this.fia = fia;
    }
    
	@Override
	public void init(ICadObject o) {
		CadImportaFiacaoEletrodutoEletricaOData other = (CadImportaFiacaoEletrodutoEletricaOData)o;
    	
    	this.rowId = other.getRowId();
    	this.hnd = other.getHnd();
        this.qdr = other.getQdr();
        this.cir = other.getCir();
        this.lbl = other.getLbl();
        this.fia = other.getFia();
    }
    
    /* CREATExxx */
    
    public static CadImportaFiacaoEletrodutoEletricaOData create(
		CadDocumentDef doc,
	    int rowId,
    	String hnd, 
    	String qdr, 
    	String cir, 
    	String lbl, 
    	int fia)
    {
    	CadImportaFiacaoEletrodutoEletricaOData o = new CadImportaFiacaoEletrodutoEletricaOData(doc);

    	o.init(
    		rowId,
    		hnd,
        	qdr, 
        	cir, 
        	lbl, 
        	fia );
    	return o;
    }
    
	/* TO_STRING */
       
    @Override
	public String toString() {
    	String str = this.toStr(); 
    	return str;		
	}
	
	/* DEBUG */
	
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);

		String str = String.format(
		    "RowId:%s;" +
	    	"Hnd:%s;" +
	    	"Qdr:%s;" +
	    	"Cir:%s;" +
	    	"Lbl:%s;" +
	    	"Fia:%s;",
			nf0.format( this.rowId ),
			this.hnd,
			this.qdr,
			this.cir,
			this.lbl,
			nf0.format( this.fia ) );
		return str;
	}
	
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
		return false;
	}
    
	/* RESET */

	@Override
	public void reset() {
		// TODO:
	}
    
    /* Getters/Setters */

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

    public String getHnd() {
		return hnd;
	}

	public void setHnd(String hnd) {
		this.hnd = hnd;
	}

	public String getQdr()
    {
        return this.qdr;
    }

    public String getCir()
    {
        return this.cir;
    }

    public String getLbl()
    {
        return this.lbl;
    }

    public int getFia()
    {
        return this.fia;
    }
    
}
