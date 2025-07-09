/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerTable.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cmp.CmpCadLayerDef;

public class LayerTable extends CadObject 
{
//Private
	private ArrayList<CadLayerDef> lsLayerDefTable;
	//
	private Hashtable<String,CadLayerDef> mapLayerDefTableByName;
	private Hashtable<String,CadLayerDef> mapLayerDefTableByReference;

	/* Methodes */
	
	private void init() {
		this.lsLayerDefTable = new ArrayList<CadLayerDef>();
		//
		this.mapLayerDefTableByName = new Hashtable<String,CadLayerDef>();
		this.mapLayerDefTableByReference = new Hashtable<String,CadLayerDef>();
	}
	
//Public
	
	public LayerTable() {
		super(AppDefs.OBJTYPE_LAYER_TABLE);
		this.init();
	}

	/* Methodes */

	public synchronized ArrayList<CadLayerDef> toArrayList()
	{
		return this.lsLayerDefTable;
	}
	
	public synchronized CadLayerDef newLayerDef(String name, String reference, int colorIndex, int ltypeIndex) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByName.containsKey(name) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByName.get(name);
		}
		else {
			oResult = CadLayerDef.create(name, reference, colorIndex, ltypeIndex);
			this.lsLayerDefTable.add(oResult);

			this.mapLayerDefTableByName.put(name, oResult);
			this.mapLayerDefTableByReference.put(reference, oResult);
			
			CmpCadLayerDef c = new CmpCadLayerDef(true);
			this.lsLayerDefTable.sort(c);
		}		
		return oResult;
	}
	
	public synchronized CadLayerDef newLayerDef(CadLayerDef oLayer) {
		CadLayerDef oResult = null;

		if( this.mapLayerDefTableByName.containsKey(oLayer.getName()) ) {
			oResult = (CadLayerDef)this.mapLayerDefTableByName.get(oLayer.getName());
		}
		else {
			oResult = CadLayerDef.create(oLayer);
			this.lsLayerDefTable.add(oResult);

			this.mapLayerDefTableByName.put(oResult.getName(), oResult);
			this.mapLayerDefTableByReference.put(oResult.getReference(), oResult);
			
			CmpCadLayerDef c = new CmpCadLayerDef(true);
			this.lsLayerDefTable.sort(c);
		}		
		return oResult;
	}

	/* Getters/Setters */
	
	public synchronized CadLayerDef getLayerDefAt(int pos) {
		CadLayerDef oResult = null;

		if(pos < this.lsLayerDefTable.size()) {
			oResult = (CadLayerDef)this.lsLayerDefTable.get(pos);
		}		
		return oResult;
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
			CadLayerDef oNewLayerDef = new CadLayerDef();
			oNewLayerDef.init(oLayerDef);
			lsResult.add(oNewLayerDef);
		}
		return lsResult;
	}
	
}
