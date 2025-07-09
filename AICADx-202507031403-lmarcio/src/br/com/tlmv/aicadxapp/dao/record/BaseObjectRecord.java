/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BaseObjectRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 16/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;

public abstract class BaseObjectRecord
{
//Private
	private long oid;
	private int objectId;
	private int objType;
	private String objTypeStr;
	private String isEntityObject;
    private String isDeleted;
    
//Public
    
    public BaseObjectRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT,
    		AppDefs.NULL_STR, 
    		AppDefs.DEF_VALUES_NAO,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String isEntityObject,
	    String isDeleted)
    {
    	this.oid = oid;
    	this.objectId = objectId;
		this.objType = objType;
		this.objTypeStr = objTypeStr;
		this.isEntityObject = isEntityObject;
	    this.isDeleted = isDeleted;
    }
	
	/* TO_CADxxx */

	public abstract CadObject toCadObject();
    
    /* Getters/Setters */
    
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	public int getObjType() {
		return objType;
	}
	public void setObjType(int objType) {
		this.objType = objType;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getObjTypeStr() {
		return objTypeStr;
	}

	public void setObjTypeStr(String objTypeStr) {
		this.objTypeStr = objTypeStr;
	}

	public String getIsEntityObject() {
		return isEntityObject;
	}

	public void setIsEntityObject(String isEntityObject) {
		this.isEntityObject = isEntityObject;
	}
    
}
