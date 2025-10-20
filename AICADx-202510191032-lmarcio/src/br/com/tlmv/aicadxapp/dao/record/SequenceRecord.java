/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SequenceRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;

public class SequenceRecord extends BaseObjectRecord
{
//Public Static

	public static final String sqlNextVal = 
		"SELECT nextval(#SCHEMA_NAME#.#SEQ_NAME#) ";

	public static final String sqlCurrVal = 
		"SELECT currval(#SCHEMA_NAME#.#SEQ_NAME#) ";
	
//Public
    
    public SequenceRecord()
    {
    	/* nothing todo! */
    }

    public SequenceRecord(ResultSet rs)
    {
    	this.init(rs);
    }    
    
    /* Methodes */
    
    public void init(ResultSet rs)
    {
    	DbUtil o = new DbUtil(rs);
    	
		this.setOid( o.getNextLng() );
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}

}
