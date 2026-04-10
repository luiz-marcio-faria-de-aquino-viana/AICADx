/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BlockTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
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
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;

public class BlockTable extends CadObject 
{
//Private
	private Hashtable<String,CadBlockDef> blockDefTable;
	
//Public
	
	public BlockTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_BLOCK_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.blockDefTable = new Hashtable<String,CadBlockDef>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,CadBlockDef> newBlockDefTable = new Hashtable<String,CadBlockDef>();
		
		Collection colBlk = this.blockDefTable.values();
		Iterator iterBlk = colBlk.iterator();
		while( iterBlk.hasNext() ) {
			CadBlockDef blkDef = (CadBlockDef)iterBlk.next();

			String blkName = blkDef.getName();
			newBlockDefTable.put(blkName, blkDef);
		}
		this.blockDefTable = newBlockDefTable;
	}

	/* Methodes */
	
	public synchronized CadBlockDef newBlockDef(int tipo, String name) {
		CadBlockDef oResult = newBlockDef(tipo, name, null);
		return oResult;
	}
	
	public synchronized CadBlockDef newBlockDef(int tipo, String name, String fullFileName) {
		CadBlockDef oResult = null;

		if( this.blockDefTable.containsKey(name) ) {
			oResult = (CadBlockDef)this.blockDefTable.get(name);
		}
		else {
			CadDocumentDef doc = this.getDocument();
			
			oResult = CadBlockDef.create(doc, tipo, name, fullFileName);
			this.blockDefTable.put(name, oResult);
		}
		return oResult;
	}
	
	public synchronized CadBlockDef addBlockDef(String name, CadBlockDef oBlkDef) {
		CadBlockDef oResult = null;
		
		if( this.blockDefTable.containsKey(name) ) {
			oResult = (CadBlockDef)this.blockDefTable.get(name);
		}
		else {
			oResult = oBlkDef;
			this.blockDefTable.put(name, oBlkDef);
		}
		return oResult;
	}
	
	public synchronized boolean hasBlockDef(String name) {
		if( this.blockDefTable.containsKey(name) ) {
			return true;
		}
		return false;
	}

	public synchronized CadBlockDef getBlockDef(String name) {
		CadBlockDef oResult = null;

		if( this.blockDefTable.containsKey(name) ) {
			oResult = (CadBlockDef)this.blockDefTable.get(name);
		}
		return oResult;
	}

	public synchronized ArrayList<CadBlockDef> getAllBlockDef() {
		ArrayList<CadBlockDef> lsResult = new ArrayList<CadBlockDef>();

		Collection<CadBlockDef> colBlockDef = this.blockDefTable.values();
		for(CadBlockDef oBlockDef : colBlockDef) {
			lsResult.add(oBlockDef);
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
