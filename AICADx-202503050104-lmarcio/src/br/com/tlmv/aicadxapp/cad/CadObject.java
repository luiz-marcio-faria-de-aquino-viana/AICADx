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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;

public class CadObject implements ICadObject
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
	private boolean bEntityObject = false;
	private boolean bDeleted = false;
	
//Public
	
	public CadObject(int objType) {
		this.init(objType);
	}

	/* Methodes */
	
	public void init(int objType) {
		this.objectId = CadObject.nextSeqId();
		this.objType = objType;
		this.bEntityObject = (objType >= AppDefs.OBJTYPE_ENTITIES);
		this.bDeleted = false;
	}
	
	public void reset()
	{
		/* nothing todo! */		
	}
	
	/* DEBUG */
	
	public String toStr() {
		String str = String.format(
			"ObjectId:%s;ObjType:%s;EntityObject:%s;Deleted:%s;", 
			Integer.toString(this.objectId), 
			Integer.toString(this.objType), 
			( this.bEntityObject ) ? "true" : "false",
			( this.bDeleted ) ? "true" : "false" );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* Getters/Setters */
	
	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	
	public int getObjType() {
		return this.objType;
	}

	public void setObjType(int objType) {
		this.objType = objType;
	}

	public boolean isEntityObject() {
		return this.bEntityObject;
	}

	public void setEntityObject(boolean bEntityObject) {
		this.bEntityObject = bEntityObject;
	}

	public boolean isDeleted() {
		return this.bDeleted;
	}

	public void setDeleted(boolean bDeleted) {
		this.bDeleted = bDeleted;
	}	
	
}
