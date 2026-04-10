/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ImageTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/04/2025
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
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;

public class ImageTable extends CadObject 
{
//Private
	private Hashtable<String,CadImageDef> imageDefTable;
	
//Public
	
	public ImageTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_IMAGE_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.imageDefTable = new Hashtable<String,CadImageDef>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,CadImageDef> newImageDefTable = new Hashtable<String,CadImageDef>();
		
		Collection colImg = this.imageDefTable.values();
		Iterator iterImg = colImg.iterator();
		while( iterImg.hasNext() ) {
			CadImageDef imgDef = (CadImageDef)iterImg.next();

			String imgName = imgDef.getName();
			newImageDefTable.put(imgName, imgDef);
		}
		this.imageDefTable = newImageDefTable;
	}

	/* Methodes */
	
	public synchronized CadImageDef newImageDef(CadDocumentDef doc, int tipo, String name, String fileName) {
		CadImageDef oResult = null;

		if( this.imageDefTable.containsKey(name) ) {
			oResult = (CadImageDef)this.imageDefTable.get(name);
		}
		else {
			oResult = CadImageDef.create(doc, tipo, name, fileName);
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
