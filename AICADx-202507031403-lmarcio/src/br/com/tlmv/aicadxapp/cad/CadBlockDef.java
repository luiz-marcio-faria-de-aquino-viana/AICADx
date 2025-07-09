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

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.filter.FilterEntityTable;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cmp.CmpCadEntity;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.CadEntityBaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.CadArcRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPointRecord;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public class CadBlockDef extends CadObject
{
//Private
	private String name = null;

	private Hashtable<Integer,CadEntity> entityTable = null;
	private ArrayList<CadEntity> lsEntity = null;
	
	private Hashtable<Integer,Integer> visibleEntityTable = null;
	private ArrayList<Integer> lsVisibleEntity = null;

	private Hashtable<Integer,Integer> selectedEntityTable = null;
	private ArrayList<Integer> lsSelectedEntity = null;

	/* GENERAL OPERATIONS */

	private int getEntityTableSz(ArrayList<Integer> ls) {
		int sz = ls.size();
		return sz;
	}

	private void clearEntityTable(Hashtable<Integer,Integer> map, ArrayList<Integer> ls) {
		map.clear();
		ls.clear();
	}
	
	private boolean addEntity(Hashtable<Integer,Integer> map, ArrayList<Integer> ls, CadEntity oEnt) {
		Integer objectId = oEnt.getObjectId();
		if( !map.containsKey(objectId) ) {
			map.put(objectId, objectId);
			ls.add(objectId);
			return true;
		}
		return false;
	}
	
	private boolean existEntity(Hashtable<Integer,Integer> map, Integer objectId) {
		if( map.containsKey(objectId) ) {
			return true;
		}
		return false;
	}
	
	private CadEntity getEntity(Hashtable<Integer,Integer> map, Integer objectId) {
		if( map.containsKey(objectId) ) {
			CadEntity oEnt = this.entityTable.get(objectId);
			return oEnt;
		}
		return null;
	}
	
	private CadEntity getEntityAt(ArrayList<Integer> ls, Integer pos) {
		if(pos < this.getEntityTableSz(ls)) {
			Integer objectId = ls.get(pos);
			
			CadEntity oEnt = this.entityTable.get(objectId);
			return oEnt;
		}
		return null;
	}
	
	private boolean removeEntity(Hashtable<Integer,Integer> map, Integer objectId) {
		if( map.containsKey(objectId) ) {
			CadEntity oEnt = this.entityTable.get(objectId);
			oEnt.setDeleted(true);
			return true;
		}
		return false;
	}

	private ArrayList<CadEntity> findAllEntityByObjType(ArrayList<Integer> ls, int objType) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(Integer objectId : ls) {
			CadEntity oEnt = this.entityTable.get(objectId);
			
			int currObjType = oEnt.getObjType();
			if(currObjType == objType) {
				lsResult.add(oEnt);
			}
		}
		return lsResult;
	}	

	private ArrayList<CadEntity> findAllHostedEntityByObjectId(ArrayList<Integer> ls, int objectId) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		CadParede oHost = null;
		for(Integer hostedObjectId : ls) {
			CadEntity oEnt = this.entityTable.get(hostedObjectId);

			int currObjType = oEnt.getObjType();
			if(currObjType == AppDefs.OBJTYPE_BIMJANELA) {
				CadJanela o = (CadJanela)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPORTA) {
				CadPorta o = (CadPorta)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPDUPLA) {
				CadPDupla o = (CadPDupla)oEnt;
				oHost = o.getParede();
			}

			if(oHost.getObjectId() == objectId)
				lsResult.add(oEnt);
		}
		return lsResult;
	}	
		
	private int filterAllEntityByRect(Hashtable<Integer,Integer> map, ArrayList<Integer> ls, GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs) {
		FilterEntityTable filter = new FilterEntityTable(this.entityTable, this.lsEntity);
		int sz = filter.filterAllEntityByRect(ptMinMcs, ptMaxMcs);
		this.visibleEntityTable = filter.getMapResult();
		this.lsVisibleEntity = filter.getLsResult();
		return sz;
	}	
		
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
		
		this.visibleEntityTable = new Hashtable<Integer,Integer>();
		this.lsVisibleEntity = new ArrayList<Integer>();
		
		this.selectedEntityTable = new Hashtable<Integer,Integer>();
		this.lsSelectedEntity = new ArrayList<Integer>();
	}

	@Override
	public void reset() {
		/* nothing todo! */
	}

	/* OPERATIONS - EntityTable */

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

	public synchronized ArrayList<CadEntity> findAllEntity() {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		for(CadEntity oEnt : this.lsEntity) {
			lsResult.add(oEnt);
		}
		return lsResult;
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

	public synchronized ArrayList<CadEntity> findAllHostedEntityByObjectId(int objectId) {
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();		
		CadParede oHost = null;
		for(CadEntity oEnt : this.lsEntity) {
			int currObjType = oEnt.getObjType();
			if(currObjType == AppDefs.OBJTYPE_BIMJANELA) {
				CadJanela o = (CadJanela)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPORTA) {
				CadPorta o = (CadPorta)oEnt;
				oHost = o.getParede();
			}
			else if(currObjType == AppDefs.OBJTYPE_BIMPDUPLA) {
				CadPDupla o = (CadPDupla)oEnt;
				oHost = o.getParede();
			}

			if(oHost.getObjectId() == objectId)
				lsResult.add(oEnt);
		}
		return lsResult;
	}	

	/* OPERATIONS - VisibleEntityTable */

	public synchronized int getVisibleEntityTableSz() {
		return this.getEntityTableSz(this.lsVisibleEntity);
	}

	public synchronized void clearVisibleEntityTable() {
		this.clearEntityTable(this.visibleEntityTable, this.lsVisibleEntity);
	}
	
	public synchronized boolean addVisibleEntity(CadEntity oEnt) {
		return this.addEntity(this.visibleEntityTable, this.lsVisibleEntity, oEnt);
	}
	
	public synchronized boolean existVisibleEntity(Integer objectId) {
		return this.existEntity(this.visibleEntityTable, objectId);
	}
	
	public synchronized CadEntity getVisibleEntity(Integer objectId) {
		return this.getEntity(this.visibleEntityTable, objectId);
	}
	
	public synchronized CadEntity getVisibleEntityAt(Integer pos) {
		return getEntityAt(this.lsVisibleEntity, pos);
	}
	
	public synchronized boolean removeVisibleEntity(Integer objectId) {
		return removeEntity(this.visibleEntityTable, objectId);
	}

	public synchronized ArrayList<CadEntity> findAllVisibleEntityByObjType(int objType) {
		return findAllEntityByObjType(this.lsVisibleEntity, objType);
	}	

	public synchronized ArrayList<CadEntity> findAllVisibleHostedEntityByObjectId(int objectId) {
		return findAllHostedEntityByObjectId(this.lsVisibleEntity, objectId);
	}	

	/* OPERATIONS - SelectedEntityTable */

	public synchronized int getSelectedEntityTableSz() {
		return this.getEntityTableSz(this.lsSelectedEntity);
	}

	public synchronized void clearSelectedEntityTable() {
		this.clearEntityTable(this.selectedEntityTable, this.lsVisibleEntity);
	}
	
	public synchronized boolean addSelectedEntity(CadEntity oEnt) {
		return this.addEntity(this.selectedEntityTable, this.lsVisibleEntity, oEnt);
	}
	
	public synchronized boolean existSelectedEntity(Integer objectId) {
		return this.existEntity(this.selectedEntityTable, objectId);
	}
	
	public synchronized CadEntity getSelectedEntity(Integer objectId) {
		return this.getEntity(this.selectedEntityTable, objectId);
	}
	
	public synchronized CadEntity getSelectedEntityAt(Integer pos) {
		return getEntityAt(this.lsSelectedEntity, pos);
	}
	
	public synchronized boolean removeSelectedEntity(Integer objectId) {
		return removeEntity(this.selectedEntityTable, objectId);
	}

	public synchronized void removeAllSelectedEntity() {
		this.selectedEntityTable.clear();
	}

	public synchronized ArrayList<CadEntity> findAllSelectedEntityByObjType(int objType) {
		return findAllEntityByObjType(this.lsSelectedEntity, objType);
	}	

	public synchronized ArrayList<CadEntity> findAllSelectedHostedEntityByObjectId(int objectId) {
		return findAllHostedEntityByObjectId(this.lsSelectedEntity, objectId);
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
		
		//CmpCadEntity c = new CmpCadEntity(ptObserver, false);  
		//lsResult.sort(c);
		
		return lsResult;
	}

	public ArrayList<CadEntity> sortAllEntities(GeomPoint3d ptObserver, int objType)
	{
		ArrayList<CadEntity> lsResult = findAllEntityByObjType(objType);
		
		//CmpCadEntity c = new CmpCadEntity(ptObserver, false);  
		//lsResult.sort(c);
		
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		String str = String.format(
			"Name: %s; NumItems: %s; ", 
			this.name, 
			Integer.toString(this.entityTable.size()) );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
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
	
	/* Getters/Setters */
	
	public GeomDimension2d getEnvelop() {
		//LIMITS
		//
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		//
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;
		
		int szLsEntity = this.lsEntity.size();
		for(int i = 0; i < szLsEntity; i++) {
			CadEntity oEnt = this.lsEntity.get(i);
			
			GeomDimension2d geomDim2d = oEnt.getEnvelop();
			GeomPoint2d ptMin = geomDim2d.getPtMin();
			GeomPoint2d ptMax = geomDim2d.getPtMax();
			
			if(ptMin.getX() < minX)
				minX = ptMin.getX(); 
			if(ptMin.getY() < minY)
				minY = ptMin.getY(); 
			
			if(ptMax.getX() > maxX)
				maxX = ptMax.getX(); 
			if(ptMax.getY() > maxY)
				maxY = ptMax.getY(); 
		}

		GeomPoint2d ptResMin2d = new GeomPoint2d(minX, minY);  
		GeomPoint2d ptResMax2d = new GeomPoint2d(maxX, maxY);  
		
		GeomDimension2d oDim = new GeomDimension2d(ptResMin2d, ptResMax2d); 
		return oDim;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
