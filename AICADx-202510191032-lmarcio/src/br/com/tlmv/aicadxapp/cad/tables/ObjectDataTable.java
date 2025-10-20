/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ObjectDataTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;

public class ObjectDataTable extends CadObject 
{
//Private
	private Hashtable<String,CadObject> objectDataTable;
	
//Public
	
	public ObjectDataTable() {
		super(AppDefs.OBJTYPE_OBJECTDATA_TABLE, null);
		this.init();
	}
	
	private void init() {
		this.objectDataTable = new Hashtable<String,CadObject>();
	}
	
	@Override
	public void reset() {
		//TODO:
	}

	/* Methodes */
	
	public synchronized CadObject newCadObject(String key, CadObject objData) {
		CadObject oResult = null;
		
		if( this.objectDataTable.containsKey(key) ) {
			oResult = (CadObject)this.objectDataTable.get(key);
		}
		else {
			oResult = objData;
			this.objectDataTable.put(key, objData);
		}
		return oResult;
	}
	
	public synchronized CadObject addCadObject(String key, CadObject objData) {
		CadObject oResult = null;
		
		if( this.objectDataTable.containsKey(key) ) {
			oResult = (CadObject)this.objectDataTable.get(key);
		}
		else {
			oResult = objData;
			this.objectDataTable.put(key, objData);
		}
		return oResult;
	}
	
	public synchronized boolean hasCadObject(String key) {
		if( this.objectDataTable.containsKey(key) ) {
			return true;
		}
		return false;
	}

	public synchronized CadObject getCadObject(String key) {
		CadObject oResult = null;

		if( this.objectDataTable.containsKey(key) ) {
			oResult = (CadObject)this.objectDataTable.get(key);
		}
		return oResult;
	}

	public synchronized ArrayList<CadObject> getAllCadObject() {
		ArrayList<CadObject> lsResult = new ArrayList<CadObject>();

		Collection<CadObject> colObjectData = this.objectDataTable.values();
		for(CadObject o : colObjectData) {
			lsResult.add(o);
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

	}
	
}
