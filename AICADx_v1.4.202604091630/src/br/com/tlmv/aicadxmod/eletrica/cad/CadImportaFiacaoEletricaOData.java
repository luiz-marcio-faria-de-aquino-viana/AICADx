/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * CadImportaFiacaoEletricaOData.java
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
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadImportaFiacaoEletricaOData extends CadObject 
{
//Private
    private String hnd;
    private ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia;

//Public

    public CadImportaFiacaoEletricaOData(CadDocumentDef doc)
    {
    	super(AppDefs.OBJTYPE_IMPORTAFIACAOELETRICA_ODATA, doc, null);    	
    }

    public CadImportaFiacaoEletricaOData(CadImportaFiacaoEletricaOData other)
    {
    	super(AppDefs.OBJTYPE_IMPORTAFIACAOELETRICA_ODATA, other.getDocument(), null);    	

    	this.init(other);
    }

    /* Methodes */
    
    public void init(
		String cadRefEntityId,
		String hnd )
    {
    	super.setCadRefEntityId(cadRefEntityId);

    	this.hnd = hnd;
        this.lsFia = new ArrayList<CadImportaFiacaoEletrodutoEletricaOData>();
    }

    public void init(
		String cadRefEntityId,
		String hnd,
		ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia )
    {
    	super.setCadRefEntityId( cadRefEntityId );
    	//
        this.hnd = hnd;
        this.lsFia = lsFia;
    }
    
	@Override
	public void init(ICadObject o) {
		CadImportaFiacaoEletricaOData other = (CadImportaFiacaoEletricaOData)o;
		
    	super.setCadRefEntityId( other.getCadRefEntityId() );
    	
        this.hnd = other.getHnd();
        this.lsFia = other.getLsFia();
    }
    
    /* CREATExxx */
    
    public static CadImportaFiacaoEletricaOData create(
		CadDocumentDef doc,
		String cadRefEntityId,
	    String hnd,
	    ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia )
    {
    	CadImportaFiacaoEletricaOData o = new CadImportaFiacaoEletricaOData(doc);

    	o.init(
    		cadRefEntityId,
    		hnd,
    		lsFia );
    	return o;
    }
    
    public static CadImportaFiacaoEletricaOData create(
		CadDocumentDef doc,
		String cadRefEntityId,
	    String hnd )
    {
    	CadImportaFiacaoEletricaOData o = new CadImportaFiacaoEletricaOData(doc);

    	o.init(
    		cadRefEntityId,
    		hnd );
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
			"CadRefEntityId:%s;" +
			"Hnd:%s;",
			super.getCadRefEntityId(),
			this.hnd );
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
    
    /* LIST */
    
    public ArrayList<CadImportaFiacaoEletrodutoEletricaOData> getLsFia()
    {
        return this.lsFia;
    }

    public int getSzLsFia()
    {
    	int szLsFia = this.lsFia.size();
    	return szLsFia;
    }

    public void addFia(CadImportaFiacaoEletrodutoEletricaOData o)
    {
    	this.lsFia.add(o);
    }

    /* Getters/Setters */

    public String getHnd()
    {
        return this.hnd;
    }

	public void setHnd(String hnd) {
		this.hnd = hnd;
	}

	public void setLsFia(ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia) {
		this.lsFia = lsFia;
	}

}
