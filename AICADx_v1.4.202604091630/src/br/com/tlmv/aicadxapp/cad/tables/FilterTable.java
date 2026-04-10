/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * FilterTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/01/2026
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

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.FilterVO;

public class FilterTable extends CadObject 
{
//Private
	private Hashtable<String,FilterVO> filterEntityTable;
	
//Public
	
	public FilterTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_FILTER_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.filterEntityTable = new Hashtable<String,FilterVO>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,FilterVO> newFilterEntityTable = new Hashtable<String,FilterVO>();
		
		Collection col = this.filterEntityTable.values();
		Iterator iter = col.iterator();
		while( iter.hasNext() ) {
			FilterVO filter = (FilterVO)iter.next();

			String filterName = filter.getName();
			this.filterEntityTable.put(filterName, filter);
		}
		this.filterEntityTable = newFilterEntityTable;
	}

	/* Methodes */
	
	public synchronized FilterVO newFilterEntity(
		String name,
		String descricao,
		ColorVO oColor,
		BorderStrokeVO oStroke) 
	{
		FilterVO oResult = null;

		if( this.filterEntityTable.containsKey(name) ) {
			oResult = (FilterVO)this.filterEntityTable.get(name);
			
			oResult.setDescricao(descricao);
			oResult.setColor(oColor);
			oResult.setStroke(oStroke);
		}
		else {
			oResult = new FilterVO(
				name,
				descricao,
				oColor,
				oStroke);
			
			this.filterEntityTable.put(name, oResult);
		}
		return oResult;
	}
	
	public synchronized FilterVO addFilterEntity(String name, FilterVO oFilterEntity) {
		FilterVO oResult = null;
		
		if( this.filterEntityTable.containsKey(name) ) {
			oResult = (FilterVO)this.filterEntityTable.get(name);
		}
		else {
			oResult = oFilterEntity;
			this.filterEntityTable.put(name, oResult);
		}
		return oResult;
	}
	
	public synchronized boolean hasFilterEntity(String name) {
		if( this.filterEntityTable.containsKey(name) ) {
			return true;
		}
		return false;
	}

	public synchronized FilterVO getFilterEntity(String name) {
		FilterVO oResult = null;

		if( this.filterEntityTable.containsKey(name) ) {
			oResult = (FilterVO)this.filterEntityTable.get(name);
		}
		return oResult;
	}

	public synchronized ArrayList<FilterVO> getAllFilterEntity() {
		ArrayList<FilterVO> lsResult = new ArrayList<FilterVO>();

		Collection<FilterVO> colBlockDef = this.filterEntityTable.values();
		for(FilterVO oFilterEntity : colBlockDef) {
			lsResult.add(oFilterEntity);
		}
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		return null;
	}

	@Override
	public void debug(int debugLevel) {
		//TODO:
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

}
