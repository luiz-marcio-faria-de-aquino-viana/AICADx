/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadBlockDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cmp.CmpCadEntity;

public class CadBlockDef extends CadObject 
{
//Public
	private String name = null;
	
	private Hashtable<Integer,CadEntity> entityTable = null;
	private ArrayList<CadEntity> lsEntity = null;
	
//Public

	public CadBlockDef() {
		super(AppDefs.OBJTYPE_BLOCK_DEF);
	}
	
	public static CadBlockDef create(String name) {
		CadBlockDef o = new CadBlockDef(); 
		o.init(name);
		return o;
	}

	/* Methodes */
	
	public void init(String name) {
		this.name = name;
		
		this.entityTable = new Hashtable<Integer,CadEntity>(); 
		this.lsEntity = new ArrayList<CadEntity>();
	}
	
	/* OPERATIONS */

	public synchronized int getEntityTableSz() {
		int sz = this.lsEntity.size();
		return sz;
	}
	
	public synchronized boolean addEntity(CadEntity oEnt) {
		Integer objectId = oEnt.getObjectId();
		if( !this.entityTable.containsKey(objectId) ) {
			this.entityTable.put(objectId, oEnt);
			this.lsEntity.add(oEnt);
			return true;
		}
		return false;
	}
	
	public synchronized boolean existEntity(Integer objectId) {
		if( this.entityTable.containsKey(objectId) ) {
			return true;
		}
		return false;
	}
	
	public synchronized CadEntity getEntity(Integer objectId) {
		if( this.entityTable.containsKey(objectId) ) {
			CadEntity oEnt = this.entityTable.get(objectId);
			return oEnt;
		}
		return null;
	}
	
	public synchronized CadEntity getEntityAt(Integer pos) {
		if(pos < this.getEntityTableSz()) {
			CadEntity oEnt = this.lsEntity.get(pos);
			return oEnt;
		}
		return null;
	}
	
	public synchronized boolean removeEntity(Integer objectId) {
		if( this.entityTable.containsKey(objectId) ) {
			CadEntity oEnt = this.entityTable.get(objectId);
			oEnt.setDeleted(true);
			
			//this.entityTable.remove(objectId);
			//this.lsEntity.remove(oEnt);
			return true;
		}
		return false;
	}

	public synchronized ArrayList<CadEntity> findAllEntityByObjType(int objType) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : this.lsEntity) {
			int currObjType = oEnt.getObjType();
			if(currObjType == objType) {
				lsResult.add(oEnt);
			}
		}
		return lsResult;
	}	
	
	/* OPERATIONS */

	public synchronized void changeLayer(Integer objectId, CadLayerDef oLayer) {
		CadEntity oEnt = this.getEntity(objectId);
		if(oEnt != null) {
			oEnt.setLayer(oLayer);
		}
	}	

	public synchronized void changeAllLayers(CadLayerDef oSrcLayer, CadLayerDef oDstLayer) {
		String strSrcLayer = oSrcLayer.getName();
		
		for(CadEntity oEnt : this.lsEntity) {
			CadLayerDef oCurrLayer = oEnt.getLayer();
			String strCurrLayer = oCurrLayer.getName();
			
			if(strCurrLayer.compareToIgnoreCase(strSrcLayer) == 0) {
				oEnt.setLayer(oDstLayer);
			}
		}
	}	

	public ArrayList<CadEntity> sortAllEntities(GeomPoint3d ptObserver)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>(this.lsEntity); 
		
		CmpCadEntity c = new CmpCadEntity(ptObserver, false);  
		lsResult.sort(c);
		
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		String str = super.toStr();
		str += String.format(
			"Name: %s; NumItems: %s; ", 
			this.name, 
			Integer.toString(this.entityTable.size()) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
