/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadObject.java
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

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
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
	private boolean bEntityObject = false;
	private boolean bDeleted = false;
	
//Public
	
	public CadObject(int objType) {
		this.init(objType);
	}

	/* Methodes */
	
	@Override
	public void init(int objType) {
		this.objectId = CadObject.nextSeqId();
		this.objType = objType;
		this.objTypeStr = AppDefs.ARR_OBJTYPE_STR[this.objType - AppDefs.OBJTYPE_NONE];
		this.bEntityObject = (objType >= AppDefs.OBJTYPE_ENTITIES);
		this.bDeleted = false;
	}
	
	@Override
	public abstract void reset();
	
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
