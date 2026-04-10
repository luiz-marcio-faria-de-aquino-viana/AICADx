/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadBlockDef.java
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

package br.com.tlmv.aicadxapp.cad;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.filter.FilterEntityTable;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.UndoTable;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cmp.CmpCadEntity;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseObjDefDao;
import br.com.tlmv.aicadxapp.dao.record.CadBlockDefRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxapp.vo.UndoItemVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public class CadBlockDef extends CadObject
{
//Private
	private int tipo = AppDefs.OPT_BLOCKDEF_NONE;
	private String name = null;	
	private String fullFileName = null;
	private String fileName = null;

	private Hashtable<Integer,CadEntity> entityTable = null;
	private ArrayList<CadEntity> lsEntity = null;
	
	private Hashtable<Integer,Integer> visibleEntityTable = null;
	private ArrayList<Integer> lsVisibleEntity = null;

	private Hashtable<Integer,Integer> selectedEntityTable = null;
	private ArrayList<Integer> lsSelectedEntity = null;

//Public

	public CadBlockDef(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_BLOCK_DEF, doc, null);
	}
	
	public static CadBlockDef create(CadDocumentDef doc, int tipo, String name) {
		CadBlockDef o = new CadBlockDef(doc); 
		o.init(tipo, name, null);
		return o;
	}
	
	public static CadBlockDef create(CadDocumentDef doc, int tipo, String name, String fullFileName) {
		CadBlockDef o = new CadBlockDef(doc); 
		o.init(tipo, name, fullFileName);
		return o;
	}

	/* Methodes */
	
	public void init(
		int tipo,
		String name, 
		String fullFileName) 
	{
		this.tipo = tipo;
		this.name = name;
		this.fullFileName = null;
		this.fileName = null;
		
		if(fullFileName != null) {
			this.fullFileName = fullFileName;
			this.fileName = FileUtil.getFileNameEx(this.fullFileName);
		}
		
		this.entityTable = new Hashtable<Integer,CadEntity>(); 
		this.lsEntity = new ArrayList<CadEntity>();
		
		this.visibleEntityTable = new Hashtable<Integer,Integer>();
		this.lsVisibleEntity = new ArrayList<Integer>();
		
		this.selectedEntityTable = new Hashtable<Integer,Integer>();
		this.lsSelectedEntity = new ArrayList<Integer>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		/* nothing todo! */
	}

	/* OPERATIONS - ENTITY_TABLE */

	public synchronized int getEntityTableSz() {
		int sz = this.lsEntity.size();
		return sz;
	}
	
	public synchronized boolean addEntity(CadEntity oEnt) {
		Integer objectId = oEnt.getObjectId();
		if( !this.entityTable.containsKey(objectId) ) {
			this.entityTable.put(objectId, oEnt);
			this.lsEntity.add(oEnt);
			
			CadEntity newEnt = (CadEntity)oEnt;
			this.saveTrans(null, newEnt);
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
		int sz = this.lsEntity.size();
		if(pos < sz) {
			CadEntity oEnt = this.lsEntity.get(pos);
			return oEnt;
		}
		return null;
	}
	
	public synchronized boolean removeEntity(Integer objectId) {
		if( this.entityTable.containsKey(objectId) ) {
			CadEntity currEnt = this.entityTable.get(objectId);

			CadEntity oldEnt = (CadEntity)currEnt.duplicate();
			currEnt.setDeleted(true);
			
			this.saveTrans(oldEnt, currEnt);
			return true;
		}
		return false;
	}
	
	public synchronized CadEntity[] findAllEntityByObjType(int objType) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjType(this.lsEntity, objType);
		return arrResult;
	}	
	
	public synchronized CadEntity[] findAllEntityByObjType(int objType, String searchBy) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjType(this.lsEntity, objType, searchBy);
		return arrResult;
	}	
	
	public synchronized CadEntity[] findAllEntityByObjType(int[] arrObjType) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjType(this.lsEntity, arrObjType);
		return arrResult;
	}	
	
	public synchronized CadEntity[] findAllEntityByLevelName(int objType, String levelName, boolean bIncludeDeleted) {
		CadEntity[] arrResult = CadUtil.findAllEntityByLevelName(this.lsEntity, objType, levelName, bIncludeDeleted);
		return arrResult;
	}
	
	public synchronized CadEntity[] findAllEntity() {
		CadEntity[] arrResult = CadUtil.findAllEntity(this.lsEntity);
		return arrResult;
	}

	/* OPERATIONS - VISIBLE_ENTITY_TABLE */
	
	public synchronized void clearVisibleEntityTable() {
		CadUtil.clearEntityTable(this.visibleEntityTable, this.lsVisibleEntity);
	}
	
	public synchronized int getVisibleEntityTableSz() {
		int sz = CadUtil.getEntityTableSz(this.lsVisibleEntity);
		return sz;
	}

	public synchronized boolean addVisibleEntity(CadEntity oEnt) {
		boolean bResult = CadUtil.addEntity(this.visibleEntityTable, this.lsVisibleEntity, oEnt);
		return bResult;
	}
	
	public synchronized boolean existVisibleEntity(Integer objectId) {
		boolean bResult = CadUtil.existEntity(this.visibleEntityTable, objectId);
		return bResult;
	}
	
	public synchronized CadEntity getVisibleEntity(Integer objectId) {
		CadEntity oResult = CadUtil.getEntity(this.entityTable, this.visibleEntityTable, objectId);
		return oResult;
	}
	
	public synchronized CadEntity getVisibleEntityAt(Integer pos) {
		CadEntity oResult = CadUtil.getEntityAt(this.entityTable, this.lsVisibleEntity, pos);
		return oResult;
	}
	
	public synchronized CadEntity removeVisibleEntity(Integer objectId) {
		CadEntity oResult = CadUtil.removeEntity(this.entityTable, this.visibleEntityTable, objectId);
		return oResult;
	}

	public synchronized CadEntity[] findAllVisibleEntityByObjType(int objType) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjType(this.entityTable, this.lsVisibleEntity, objType);
		return arrResult;
	}	

	public synchronized CadEntity[] findAllVisibleEntityByObjType(int objType, String searchString) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjTypeAndSearchString(this.entityTable, this.lsVisibleEntity, objType, searchString);
		return arrResult;
	}	
	
	public synchronized CadEntity[] findAllVisibleHostedEntityByObjectId(int objectId) {
		CadEntity[] arrResult = CadUtil.findAllHostedEntityByObjectId(this.entityTable, this.lsVisibleEntity, objectId);
		return arrResult;
	}	
	
//	private int filterAllEntityByRect(Hashtable<Integer,Integer> map, ArrayList<Integer> ls, GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs) {
//		FilterEntityTable filter = new FilterEntityTable(this.getDocument(), this.entityTable, this.lsEntity);
//		int sz = filter.filterAllEntityByRect(ptMinMcs, ptMaxMcs);
//		this.visibleEntityTable = filter.getMapResult();
//		this.lsVisibleEntity = filter.getLsResult();
//		return sz;
//	}

	/* OPERATIONS - SELECTED_ENTITY_TABLE */

	public synchronized int getSelectedEntityTableSz() {
		int sz = CadUtil.getEntityTableSz(this.lsSelectedEntity);
		return sz;
	}

	public synchronized void clearAllSelected() {
		for(CadObject obj : this.lsEntity) {
			if( obj.isDeleted() ) continue;
			if( obj.isEntityObject() ) {
				CadEntity oEnt = (CadEntity)obj;
				if( oEnt.isSelected() )
					oEnt.setSelected(false);
			}
		}
	}
	
	public synchronized void setSelectedAllVisibleEntity(boolean bSelected)
	{
		CadUtil.clearEntityTable(this.selectedEntityTable, this.lsSelectedEntity);

		for(CadEntity oEnt : this.lsEntity) {
			if( oEnt.isDeleted() ) continue;
			if( !oEnt.isVisible() ) continue;
			oEnt.setSelected(bSelected);

			CadUtil.addEntity(this.selectedEntityTable, this.lsSelectedEntity, oEnt);
		}
	}
	
	public synchronized void setSelectedAllEntities(boolean bSelected)
	{
		CadUtil.clearEntityTable(this.selectedEntityTable, this.lsSelectedEntity);

		for(CadEntity oEnt : this.lsEntity) {
			if( oEnt.isDeleted() ) continue;
			if( !oEnt.isVisible() ) continue;
			
			oEnt.setSelected(bSelected);
			if( bSelected ) {
				CadUtil.addEntity(this.selectedEntityTable, this.lsSelectedEntity, oEnt);
			}
			else {
				int objectId = oEnt.getObjectId();
				CadUtil.removeEntity(this.entityTable, this.selectedEntityTable, objectId);
			}
		}
	}

	public synchronized void clearSelectedEntityTable() {
		CadUtil.clearEntityTable(this.selectedEntityTable, this.lsVisibleEntity);
	}
	
	public synchronized boolean addSelectedEntity(CadEntity oEnt) {
		boolean bResult = CadUtil.addEntity(this.selectedEntityTable, this.lsVisibleEntity, oEnt);
		return bResult;
	}
	
	public synchronized boolean existSelectedEntity(Integer objectId) {
		boolean bResult = CadUtil.existEntity(this.selectedEntityTable, objectId);
		return bResult;
	}
	
	public synchronized CadEntity getSelectedEntity(Integer objectId) {
		CadEntity oResult = CadUtil.getEntity(this.entityTable, this.selectedEntityTable, objectId);
		return oResult;
	}
	
	public synchronized CadEntity getSelectedEntityAt(Integer pos) {
		CadEntity oResult = CadUtil.getEntityAt(this.entityTable, this.lsSelectedEntity, pos);
		return oResult;
	}
	
	public synchronized CadEntity removeSelectedEntity(Integer objectId) {
		CadEntity oResult = CadUtil.removeEntity(this.entityTable, this.selectedEntityTable, objectId);
		return oResult;
	}

	public synchronized void removeAllSelectedEntity() {
		CadUtil.clearEntityTable(this.selectedEntityTable, this.lsSelectedEntity);
	}

	public synchronized CadEntity[] findAllSelectedEntityByObjType(int objType) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjType(this.entityTable, this.lsSelectedEntity, objType);
		return arrResult;
	}	

	public synchronized CadEntity[] findAllSelectedEntityByObjType(int objType, String searchString) {
		CadEntity[] arrResult = CadUtil.findAllEntityByObjTypeAndSearchString(this.entityTable, this.lsSelectedEntity, objType, searchString);
		return arrResult;
	}	

	public synchronized CadEntity[] findAllSelectedHostedEntityByObjectId(int objectId) {
		CadEntity[] arrResult = CadUtil.findAllHostedEntityByObjectId(this.entityTable, this.lsSelectedEntity, objectId);
		return arrResult;
	}	
	
	/* OPERATIONS */

	public synchronized void changeLayer(Integer objectId, CadLayerDef oLayer) {
		CadEntity oEnt = this.getEntity(objectId);
		if(oEnt != null) {
			if( oEnt.isDeleted() ) return;
			oEnt.setLayer(oLayer);
		}
	}	

	public synchronized void changeAllLayers(CadLayerDef oSrcLayer, CadLayerDef oDstLayer) {
		String strSrcLayer = oSrcLayer.getName();
		
		for(CadEntity oEnt : this.lsEntity) {
			if( oEnt.isDeleted() ) continue;

			CadLayerDef oCurrLayer = oEnt.getLayer();
			String strCurrLayer = oCurrLayer.getName();
			
			if(strCurrLayer.compareToIgnoreCase(strSrcLayer) == 0) {
				oEnt.setLayer(oDstLayer);
			}
		}
	}	
	
	/* SORT */

	public ArrayList<CadEntity> sortAllEntities(GeomPoint3d ptObserver)
	{
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>(this.lsEntity); 
		
		CmpCadEntity c = new CmpCadEntity(ptObserver, false);  
		lsResult.sort(c);
		
		return lsResult;
	}

	public ArrayList<CadEntity> sortAllEntities(GeomPoint3d ptObserver, int objType)
	{
		CadEntity[] arrEntity = CadUtil.findAllEntityByObjType(this.lsEntity, objType);
		
		ArrayList<CadEntity> lsResult = new ArrayList<CadEntity>();
		for(CadEntity oEnt : arrEntity) {
			lsResult.add(oEnt);
		}
		
		CmpCadEntity c = new CmpCadEntity(ptObserver, false);  
		lsResult.sort(c);
		
		return lsResult;
	}

	/* COMMIT - UNDO TABLE */

	public void beginTrans() {
		if( !AppDefs.ENABLE_UNDO_REDO ) return;
		
		CadDocumentDef doc = this.getDocument();
		UndoTable undoTable = doc.getUndoTable();

		undoTable.beginGroup();
	}
	
	public void saveTrans(Object oldEnt, Object newEnt) {
		if( !AppDefs.ENABLE_UNDO_REDO ) return;
		
		CadDocumentDef doc = this.getDocument();
		UndoTable undoTable = doc.getUndoTable();
		
		undoTable.newItem(AppDefs.OPERTYPE_UNDO_MSPACE_VAL, oldEnt, newEnt);
	}

	public void endTrans() {
		if( !AppDefs.ENABLE_UNDO_REDO ) return;
		
		CadDocumentDef doc = this.getDocument();
		UndoTable undoTable = doc.getUndoTable();

		undoTable.endGroup();
	}
	
	public ArrayList<UndoItemVO> undo() {
		if( !AppDefs.ENABLE_UNDO_REDO ) return null;
		
		CadDocumentDef doc = this.getDocument();
		UndoTable undoTable = doc.getUndoTable();
		
		ArrayList<UndoItemVO> lsResult = undoTable.undo();
		return lsResult;
	}
	
	public ArrayList<UndoItemVO> redo() {
		if( !AppDefs.ENABLE_UNDO_REDO ) return null;
		
		CadDocumentDef doc = this.getDocument();
		UndoTable undoTable = doc.getUndoTable();
		
		ArrayList<UndoItemVO> lsResult = undoTable.redo();
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		String str = String.format(
			"Tipo: %s; " +
			"Name: %s; " +
			"NumItems: %s; ",
			this.tipo,
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
	
	public boolean save_data(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		BaseObjDefDao entDao = dao.createObjDefDao(this.getObjType()); 
		CadBlockDefRecord entRec = new CadBlockDefRecord(this); 
		int rscode = entDao.insertOrUpdate(schemaName, (CadBlockDefRecord) entRec);		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	public boolean save_file(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		ProjectRepoVO projectRepo = dao.getProjectRepo();
		if(projectRepo == null) return false;

		if(this.fullFileName == null) return false;
		
		String blockDir = projectRepo.getBlkDir();
		
		String destFileName = blockDir + this.fileName;
		
		if( !destFileName.equals(this.fullFileName) ) {
			File srcFile = new File( this.fullFileName );  
			File dstFile = new File( destFileName );

			if( !srcFile.exists() ) return false;

			if( dstFile.exists() ) {
				String bkpFileName = FileUtil.generateBackupFileName(destFileName);
				File bkpFile = new File(bkpFileName);
				
				dstFile.renameTo(bkpFile);
			}
			
			FileUtil.copyFile(srcFile, dstFile);

			this.fullFileName = destFileName;
		}
		return true;
	}

	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		bResult = this.save_file(dao, schemaName, doc);
		if( !bResult ) return false;

		bResult = this.save_data(dao, schemaName, doc);
		return bResult;
	}
	
	/* UTILITIES */
	
	public synchronized CadEntity[] toArrEntities()
	{
		CadEntity[] arr = CadUtil.toArrEntities(this.lsEntity);
		return arr;
	}
	
	/* Getters/Setters */
	
	public GeomDimension3d getEnvelop3d(int objType) {
		CadEntity[] arr = CadUtil.toArrEntities(this.lsEntity);
		GeomDimension3d oGeomDim = GeomUtil.getEnvelop3d(arr, objType);
		return oGeomDim;
	}
	
	public GeomDimension2d getEnvelop2d(int objType) {
		CadEntity[] arr = CadUtil.toArrEntities(this.lsEntity);
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(arr, objType);
		return oGeomDim;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
