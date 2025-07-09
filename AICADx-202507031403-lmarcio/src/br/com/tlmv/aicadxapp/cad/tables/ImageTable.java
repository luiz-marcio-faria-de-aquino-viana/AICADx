/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ImageTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/04/2025
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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;

public class ImageTable extends CadObject 
{
//Private
	private Hashtable<String,CadImageDef> imageDefTable;
	
//Public
	
	public ImageTable() {
		super(AppDefs.OBJTYPE_IMAGE_TABLE);
		this.init();
	}
	
	private void init() {
		this.imageDefTable = new Hashtable<String,CadImageDef>();
	}
	
	@Override
	public void reset() {
		//TODO:
	}

	/* Methodes */
	
	public synchronized CadImageDef newImageDef(String name, String fileName) {
		CadImageDef oResult = null;

		if( this.imageDefTable.containsKey(name) ) {
			oResult = (CadImageDef)this.imageDefTable.get(name);
		}
		else {
			oResult = CadImageDef.create(name, fileName);
			this.imageDefTable.put(name, oResult);
		}
		return oResult;
	}
	
	public synchronized CadImageDef addImageDef(String name, CadImageDef oImgDef) {
		CadImageDef oResult = null;
		
		if( this.imageDefTable.containsKey(name) ) {
			oResult = (CadImageDef)this.imageDefTable.get(name);
		}
		else {
			oResult = oImgDef;
			this.imageDefTable.put(name, oImgDef);
		}
		return oResult;
	}
	
	public synchronized boolean hasImageDef(String name) {
		if( this.imageDefTable.containsKey(name) ) {
			return true;
		}
		return false;
	}

	public synchronized CadImageDef getImageDef(String name) {
		CadImageDef oResult = null;

		if( this.imageDefTable.containsKey(name) ) {
			oResult = (CadImageDef)this.imageDefTable.get(name);
		}
		return oResult;
	}

	public synchronized ArrayList<CadImageDef> getAllImageDef() {
		ArrayList<CadImageDef> lsResult = new ArrayList<CadImageDef>();

		Collection<CadImageDef> colImageDef = this.imageDefTable.values();
		for(CadImageDef oImageDef : colImageDef) {
			lsResult.add(oImageDef);
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
