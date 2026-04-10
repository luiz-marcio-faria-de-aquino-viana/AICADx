/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * LayerTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
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
import java.util.Hashtable;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class LayerTable extends CadObject 
{
//Private
	private ArrayList<CadLayerDef> lsLayerDefTable;
	//
	private Hashtable<String,CadLayerDef> mapLayerDefTableByName;
	private Hashtable<String,CadLayerDef> mapLayerDefTableByReference;

//Public

	public LayerTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_LAYER_TABLE, doc, null);
		this.init();
	}
	
	public void init() {
		this.lsLayerDefTable = new ArrayList<CadLayerDef>();
		//
		this.mapLayerDefTableByName = new Hashtable<String,CadLayerDef>();
		this.mapLayerDefTableByReference = new Hashtable<String,CadLayerDef>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}

	@Override
	public void reset() {
		Hashtable<String,CadLayerDef> newMapLayerDefTableByName = new Hashtable<String,CadLayerDef>();
		Hashtable<String,CadLayerDef> newMapLayerDefTableByReference = new Hashtable<String,CadLayerDef>();

		for(CadLayerDef layDef : this.lsLayerDefTable) {
			String layName = layDef.getName();
			String refName = layDef.getReference();

			newMapLayerDefTableByName.put(layName, layDef);
			newMapLayerDefTableByReference.put(refName, layDef);
		}
		this.mapLayerDefTableByName = new Hashtable<String,CadLayerDef>();
		this.mapLayerDefTableByReference = new Hashtable<String,CadLayerDef>();
	}

	/* Methodes */

	public synchronized ArrayList<CadLayerDef> toArrayList()
	{
		return this.lsLayerDefTable;
	}
	
	public synchronized CadLayerDef newLayerDef(
		CadDocumentDef doc,			
		String name, 
		String reference, 
		int colorIndex, 
		int ltypeIndex,
		double lineWeight,
		double minDist,
		int categoriaId,
		String descricaoCategoria) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByName.containsKey(name) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByName.get(name);
		}
		else {
			oResult = CadLayerDef.create(
				doc,					
				name, 
				reference, 
				colorIndex, 
				ltypeIndex,
				lineWeight,
				minDist,
				categoriaId,
				descricaoCategoria);
			this.lsLayerDefTable.add(oResult);

			this.mapLayerDefTableByName.put(name, oResult);
			this.mapLayerDefTableByReference.put(reference, oResult);
			
			//CmpCadLayerDef c = new CmpCadLayerDef(true);
			//this.lsLayerDefTable.sort(c);
		}		
		return oResult;
	}
	
	public synchronized CadLayerDef newLayerDef(CadLayerDef oLayer) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByName.containsKey(oLayer.getName()) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByName.get(oLayer.getName());
		}
		else {
			CadDocumentDef doc = this.getDocument();
			
			oResult = CadLayerDef.create(doc, oLayer);
			this.lsLayerDefTable.add(oResult);

			this.mapLayerDefTableByName.put(oResult.getName(), oResult);
			this.mapLayerDefTableByReference.put(oResult.getReference(), oResult);
			
			//CmpCadLayerDef c = new CmpCadLayerDef(true);
			//this.lsLayerDefTable.sort(c);
		}		
		return oResult;
	}
	
	public synchronized boolean hasLayerDef(String name) {
		if( this.mapLayerDefTableByName.containsKey(name) ) {
			return true;
		}
		return false;
	}

	public synchronized CadLayerDef getLayerDef(String name) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByName.containsKey(name) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByName.get(name);
		}
		return oResult;
	}

	public synchronized CadLayerDef getLayerDefByRef(String reference) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByReference.containsKey(reference) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByReference.get(reference);
		}
		return oResult;
	}

	public synchronized ArrayList<CadLayerDef> getAllLayerDef() {
		ArrayList<CadLayerDef> lsResult = new ArrayList<CadLayerDef>();

		for(CadLayerDef oLayerDef : this.lsLayerDefTable) {
			lsResult.add(oLayerDef);
		}
		return lsResult;
	}

	public synchronized ArrayList<CadLayerDef> getAllLayersDefByPrefix(String layerPrefix) {
		ArrayList<CadLayerDef> lsResult = new ArrayList<CadLayerDef>();
		
		for(CadLayerDef oLayerDef : this.lsLayerDefTable) {
			String reference = oLayerDef.getReference();
			if( reference.startsWith(layerPrefix) ) {
				lsResult.add(oLayerDef);
			}
		}
		return lsResult;
	}
	
	public synchronized ArrayList<CadLayerDef> getAllLayersDefByPrefix(String[] arrLayerPrefix) {
		ArrayList<CadLayerDef> lsResult = new ArrayList<CadLayerDef>();
		
		for(CadLayerDef oLayerDef : this.lsLayerDefTable) {
			String reference = oLayerDef.getReference();
			if( StringUtil.startsWith(reference, arrLayerPrefix) ) {
				lsResult.add(oLayerDef);
			}
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
	
	/* Getters/Setters */
	
	public synchronized CadLayerDef getLayerDefAt(int pos) {
		CadLayerDef oResult = null;

		if(pos < this.lsLayerDefTable.size()) {
			oResult = (CadLayerDef)this.lsLayerDefTable.get(pos);
		}		
		return oResult;
	}
	
	public synchronized CadLayerDef getLayerDefByCategoriaId(int categoriaId) {
		for(CadLayerDef o : this.lsLayerDefTable) {
			if(categoriaId == o.getCategoriaId())
				return o;
		}		
		return null;
	}
	
	public synchronized CadLayerDef getLayerDefByDescricaoCategoria(String strCategoria) {
		for(CadLayerDef o : this.lsLayerDefTable) {
			if( strCategoria.compareToIgnoreCase( o.getDescricaoCategoria() ) == 0 )
				return o;
		}		
		return null;
	}

	public synchronized CadLayerDef getLayerDefByName(String layerName) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByName.containsKey(layerName) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByName.get(layerName);
		}		
		return oResult;
	}

	public synchronized CadLayerDef getLayerDefByReference(String reference) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByReference.containsKey(reference) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByReference.get(reference);
		}		
		return oResult;
	}

	public synchronized ArrayList<CadLayerDef> getAllLayers() {
		ArrayList<CadLayerDef> lsResult = new ArrayList<CadLayerDef>();
		
		for(CadLayerDef oLayerDef : this.lsLayerDefTable)
		{
			CadDocumentDef doc = this.getDocument();
			
			CadLayerDef oNewLayerDef = new CadLayerDef(doc);
			oNewLayerDef.init(oLayerDef);
			lsResult.add(oNewLayerDef);
		}
		return lsResult;
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
