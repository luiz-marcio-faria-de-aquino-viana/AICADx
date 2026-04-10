/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadObject.java
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

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.res.strings.R;

public abstract class CadObject implements ICadObject
{
//Private Static
	private static int gSeqId = AppDefs.DEF_SEQ_INIT;
	
	/* Methodes */
	
	private static synchronized int nextSeqId() {
		return CadObject.gSeqId++;
	}
	
	private static synchronized int currSeqId() {
		return CadObject.gSeqId;
	}
	
//Private
	private CadDocumentDef doc = null;
	private CadBlockDef oBlkDef = null;
	//
	private int objectId = AppDefs.NULL_INT;
	private int objType = AppDefs.OBJTYPE_NONE;
	private String objTypeStr = AppDefs.ARR_OBJTYPE_STR[0];
	private String objVer = AppDefs.NULL_STR;
	//
	private String cadRefEntityId = AppDefs.NULL_INTSTR;
	//
	private boolean bEntityObject = false;
	private boolean bDeleted = false;
	private boolean bChanged = false;

//Protected
	protected R r = null;
	
//Public

    public CadObject() {
    	this.initObj(AppDefs.OBJTYPE_NONE, null, null);
    }
	
	public CadObject(int objType, CadDocumentDef doc, CadBlockDef oBlkDef) {
		this.initObj(objType, doc, oBlkDef);
	}

	public CadObject(int objType, CadDocumentDef doc, CadBlockDef oBlkDef, String cadRefEntityId) {
		this.initObj(objType, doc, oBlkDef, cadRefEntityId);
	}

	/* Methodes */
	
	@Override
	public void initObj(int objType, CadDocumentDef doc, CadBlockDef blkDef) {
		this.initObj(objType, doc, blkDef, AppDefs.NULL_INTSTR);
	}
	
	@Override
	public void initObj(int objType, CadDocumentDef doc, CadBlockDef blkDef, String cadRefEntityId) {
		this.r = AppMain.getResource();
		//
		this.doc = doc;
		this.oBlkDef = blkDef;
		//
		this.objectId = CadObject.nextSeqId();
		this.objType = objType;
		this.objTypeStr = CadUtil.getObjTypeStr(this.objType);
		this.objVer = AppDefs.NULL_STR;
		//
		this.cadRefEntityId = cadRefEntityId;
		//
		this.bEntityObject = (objType >= AppDefs.OBJTYPE_ENTITIES);
		this.bDeleted = false;
		this.bChanged = false;
	}
	
	@Override
	public void initObj(BaseObjectRecord oRec, CadDocumentDef doc, CadBlockDef oBlkDef) {
		this.initObj(oRec.getObjType(), doc, oBlkDef, oRec.getCadRefEntityId());
		//
		this.setObjectId(oRec.getObjectId());
		this.setObjVer(oRec.getObjVer());
	}
	
	@Override
	public abstract void init(ICadObject other);
	
	@Override
	public abstract void reset();
	
	@Override
	public String toString() {
		String str = String.format("Codigo:%s;Tipo:%s", 
			Integer.toString( this.getObjectId() ),
			this.getObjTypeStr());
		return str;
	}
	
	/* DEBUG */
	
	@Override
	public abstract String toStr();
	
	@Override
	public abstract void debug(int debugLevel);	
	
	/* LOAD/SAVE */
	
	@Override
	public boolean loadAllPts(ArrayList<BasePointRecord> lsRec)
	{
		return false;
	}
	
	@Override
	public boolean loadAllOData(ArrayList<BaseObjectRecord> lsRec)
	{
		return false;		
	}
	
	@Override
	public abstract boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc);

	@Override
	public abstract boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc);
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
		return null;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		return null;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}

	/* Getters/Setters */
	
	@Override
	public int getObjectId() {
		return this.objectId;
	}
	
	@Override
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	@Override
	public int getObjType() {
		return this.objType;
	}

	@Override
	public void setObjType(int objType) {
		this.objType = objType;
	}

	@Override
	public String getObjTypeStr() {
		return objTypeStr;
	}

	@Override
	public void setObjTypeStr(String objTypeStr) {
		this.objTypeStr = objTypeStr;
	}
	
	@Override
	public CadDocumentDef getDocument() {
		return doc;
	}

	@Override
	public void setDocument(CadDocumentDef doc) {
		this.doc = doc;
	}

	@Override
	public CadBlockDef getBlkDef() {
		return this.oBlkDef;
	}

	@Override
	public void setBlkDef(CadBlockDef blkDef) {
		this.oBlkDef = blkDef;
	}

	@Override
	public boolean isEntityObject() {
		return this.bEntityObject;
	}

	@Override
	public void setEntityObject(boolean bEntityObject) {
		this.bEntityObject = bEntityObject;
	}

	@Override
	public boolean isDeleted() {
		return this.bDeleted;
	}

	@Override
	public void setDeleted(boolean bDeleted) {
		this.bDeleted = bDeleted;
	}

	public boolean isChanged() {
		return bChanged;
	}

	public void setChanged(boolean bChanged) {
		this.bChanged = bChanged;
	}

	public String getObjVer() {
		return objVer;
	}

	public void setObjVer(String objVer) {
		this.objVer = objVer;
	}

	public String getCadRefEntityId() {
		return cadRefEntityId;
	}

	public void setCadRefEntityId(String cadRefEntityId) {
		this.cadRefEntityId = cadRefEntityId;
	}	
	
}
