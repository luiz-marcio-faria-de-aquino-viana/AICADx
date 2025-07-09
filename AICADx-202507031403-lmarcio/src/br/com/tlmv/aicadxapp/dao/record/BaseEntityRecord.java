/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BaseEntityRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
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
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;

public abstract class BaseEntityRecord extends BaseObjectRecord
{
//Private	
	private String reference;
    
//Public
    
    public BaseEntityRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.DEF_VALUES_NAO);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
	    String reference,
	    String isDeleted)
    {
    	super.init(oid, objectId, objType, objTypeStr, AppDefs.DEF_VALUES_SIM, isDeleted);
    	
    	this.reference = reference;
    }
	
	/* TO_CADxxx */

	public abstract CadObject toCadObject();

    /* Getters/Setters */
    
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
    
}
