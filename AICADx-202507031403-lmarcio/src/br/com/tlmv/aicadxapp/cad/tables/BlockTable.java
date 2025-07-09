/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BlockTable.java
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
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;

public class BlockTable extends CadObject 
{
//Private
	private Hashtable<String,CadBlockDef> blockDefTable;
	
//Public
	
	public BlockTable() {
		super(AppDefs.OBJTYPE_BLOCK_TABLE);
		this.init();
	}
	
	private void init() {
		this.blockDefTable = new Hashtable<String,CadBlockDef>();
	}
	
	@Override
	public void reset() {
		//TODO:
	}

	/* Methodes */
	
	public synchronized CadBlockDef newBlockDef(String name) {
		CadBlockDef oResult = null;

		if( this.blockDefTable.containsKey(name) ) {
			oResult = (CadBlockDef)this.blockDefTable.get(name);
		}
		else {
			oResult = CadBlockDef.create(name);
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

	}

}
