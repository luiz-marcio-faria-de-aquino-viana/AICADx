/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DocumentTable.java
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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class DocumentTable extends CadObject 
{
//Private
	private Hashtable<String,CadDocumentDef> documentDefTable;

//Public
	
	public DocumentTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_DOCUMENT_TABLE, doc, null);
		this.init();
	}
	
	public void init() {
		this.documentDefTable = new Hashtable<String,CadDocumentDef>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,CadDocumentDef> newDocumentDefTable = new Hashtable<String,CadDocumentDef>();
		
		Collection colDoc = this.documentDefTable.values();
		Iterator iterDoc = colDoc.iterator();
		while( iterDoc.hasNext() ) {
			CadDocumentDef docDef = (CadDocumentDef)iterDoc.next();

			ProjectRepoVO projectRepo = docDef.getProjectRepo();
			String docName = projectRepo.getName();
			newDocumentDefTable.put(docName, docDef);
		}
		this.documentDefTable = newDocumentDefTable;
	}

	/* Methodes */

	public synchronized CadDocumentDef findFirstCadDocumentDef()
	{
		ArrayList<CadDocumentDef> ls = findAllCadDocumentDef();
		if(ls.size() == 0) return null;
		
		CadDocumentDef oResult = ls.get(0);
		return oResult;
	}

	public synchronized ArrayList<CadDocumentDef> findAllCadDocumentDef()
	{
		ArrayList<CadDocumentDef> oResult = new ArrayList<CadDocumentDef>( this.documentDefTable.values() );
		return oResult;
	}
	
	public synchronized CadDocumentDef newDocumentDef() {
		CadDocumentDef oResult = CadDocumentDef.newDocument();

		ProjectRepoVO projectRepo = oResult.getProjectRepo();
		String name = projectRepo.getName();
		this.documentDefTable.put(name, oResult);
		return oResult;
	}
	
	public synchronized CadDocumentDef openDocumentDef(String name, String fileName) {
		CadDocumentDef oResult = null;

		if( this.documentDefTable.containsKey(name) ) {
			oResult = (CadDocumentDef)this.documentDefTable.get(name);
		}
		return oResult;
	}
	
	public synchronized void closeDocumentDef(String name) {
		if( this.documentDefTable.containsKey(name) ) {
			this.documentDefTable.remove(name);
		}
	}

	public synchronized CadDocumentDef getDocumentDef(String name) {
		CadDocumentDef oResult = null;

		if( this.documentDefTable.containsKey(name) ) {
			oResult = (CadDocumentDef)this.documentDefTable.get(name);
		}
		return oResult;
	}
	
	public synchronized int getDocumentTableSz()
	{
		int result = this.documentDefTable.size();
		return result;
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

	/* Getters/Setters */
	
	@Override
	public CadDocumentDef getDocument() {
		return this.findFirstCadDocumentDef();
	}
	
}
