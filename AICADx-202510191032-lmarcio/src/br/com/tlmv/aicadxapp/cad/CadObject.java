/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.DataRecordUtil;

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
	private int objectId = AppDefs.NULL_INT;
	private int objType = AppDefs.OBJTYPE_NONE;
	private String objTypeStr = AppDefs.ARR_OBJTYPE_STR[0];
	private CadBlockDef oBlkDef = null;
	private boolean bEntityObject = false;
	private boolean bDeleted = false;

//Protected
	protected R r = null;
	
//Public
	
	public CadObject(int objType, CadBlockDef oBlkDef) {
		this.init(objType, oBlkDef);
	}

	/* Methodes */
	
	@Override
	public void init(int objType, CadBlockDef oBlkDef) {
		this.r = AppMain.getResource();
		//
		this.objectId = CadObject.nextSeqId();
		this.objType = objType;
		this.objTypeStr = AppDefs.ARR_OBJTYPE_STR[this.objType - AppDefs.OBJTYPE_NONE];
		this.oBlkDef = oBlkDef;
		this.bEntityObject = (objType >= AppDefs.OBJTYPE_ENTITIES);
		this.bDeleted = false;
	}
	
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
	public abstract void load(AppDatabase db, String schemaName, CadDocumentDef doc);

	@Override
	public abstract void save(AppDatabase db, String schemaName, CadDocumentDef doc);
	
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
	
}
