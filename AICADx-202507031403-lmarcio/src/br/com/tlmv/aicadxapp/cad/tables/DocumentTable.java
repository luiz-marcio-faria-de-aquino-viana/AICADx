/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DocumentTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;

public class DocumentTable extends CadObject 
{
//Private
	private Hashtable<String,CadDocumentDef> documentDefTable;

//Public
	
	public DocumentTable() {
		super(AppDefs.OBJTYPE_DOCUMENT_TABLE);
		this.init();
	}
	
	public void init() {
		this.documentDefTable = new Hashtable<String,CadDocumentDef>();
	}

	@Override
	public void reset() {
		//TODO:
	}

	/* Methodes */

	public synchronized ArrayList<CadDocumentDef> findAllCadDocumentDef()
	{
		ArrayList<CadDocumentDef> oResult = new ArrayList<CadDocumentDef>( this.documentDefTable.values() );
		return oResult;
	}
	
	public synchronized CadDocumentDef newDocumentDef() {
		CadDocumentDef oResult = CadDocumentDef.newDocument();

		String name = oResult.getName();
		this.documentDefTable.put(name, oResult);
		return oResult;
	}
	
	public synchronized CadDocumentDef openDocumentDef(String name, String fileName) {
		CadDocumentDef oResult = null;

		if( this.documentDefTable.containsKey(name) ) {
			oResult = (CadDocumentDef)this.documentDefTable.get(name);
		}
//		else {
//			oResult = CadDocumentDef.openDocument(name, fileName);
//			this.documentDefTable.put(name, oResult);
//		}		
		return oResult;
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

	}
	
}
