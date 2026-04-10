/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadFioEletricoEletricaOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
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

public class CadFioEletricoEletricaOData extends CadObject
{
//Private
    private int rowId = -1;
    private int eletrodutoId = -1;
    private String nomeQuadro = "";
    private String circuito = "";
    private String tipoCondutor = "";
    private double bitolaCondutor = 0.0;
    
//Public
    
    public CadFioEletricoEletricaOData(CadDocumentDef doc)
    {
    	super(AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA, doc, null);    	
    }

    public CadFioEletricoEletricaOData(CadFioEletricoEletricaOData other)
    {
    	super(AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA, other.getDocument(), null);    	

    	this.init(other);
    }
    
    /* Methodes */
    
    public void init(
	    int rowId,
	    int eletrodutoId,
	    String nomeQuadro,
	    String circuito,
	    String tipoCondutor,
	    double bitolaCondutor)
    {
        this.rowId = rowId;
        this.eletrodutoId = eletrodutoId;
    	this.nomeQuadro = nomeQuadro;
    	this.circuito = circuito;
        this.tipoCondutor = tipoCondutor;
        this.bitolaCondutor = bitolaCondutor;
    }
	
	@Override
	public void init(ICadObject o) {
		CadFioEletricoEletricaOData other = (CadFioEletricoEletricaOData)o;
		
		this.rowId = other.getRowId();
	    this.eletrodutoId = other.getEletrodutoId();
		this.nomeQuadro = other.getNomeQuadro();
		this.circuito = other.getCircuito();
	    this.tipoCondutor = other.getTipoCondutor();
	    this.bitolaCondutor = other.getBitolaCondutor();
	}
    
    /* CREATExxx */
    
    public static CadFioEletricoEletricaOData create(
		CadDocumentDef doc,
	    int rowId,
	    int eletrodutoId,
	    String nomeQuadro,
	    String circuito,
	    String tipoCondutor,
	    double bitolaCondutor)
    {
    	CadFioEletricoEletricaOData o = new CadFioEletricoEletricaOData(doc);

    	o.init(
    		rowId,
    		eletrodutoId,
    		nomeQuadro,
    		circuito,
    		tipoCondutor,
    		bitolaCondutor );
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
			"EletrodutoId:%s;" +
			"NomeQuadro:%s;" +
			"Circuito:%s;" +
			"TipoCondutor:%s;" +
			"BitolaCondutor:%s;",
			this.getRowId(),
			this.getEletrodutoId(),
			this.getNomeQuadro(),
			this.getCircuito(),
			this.getTipoCondutor(),
			nf1.format( this.getBitolaCondutor() ) );
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
		return this.rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getEletrodutoId() {
		return eletrodutoId;
	}

	public void setEletrodutoId(int eletrodutoId) {
		this.eletrodutoId = eletrodutoId;
	}

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public String getTipoCondutor() {
		return tipoCondutor;
	}

	public void setTipoCondutor(String tipoCondutor) {
		this.tipoCondutor = tipoCondutor;
	}

	public double getBitolaCondutor() {
		return bitolaCondutor;
	}

	public void setBitolaCondutor(double bitolaCondutor) {
		this.bitolaCondutor = bitolaCondutor;
	}
	
}
