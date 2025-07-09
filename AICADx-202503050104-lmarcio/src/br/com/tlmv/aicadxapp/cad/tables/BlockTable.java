/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BlockTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;

public class BlockTable extends CadObject 
{
//Private
	private Hashtable<String,CadBlockDef> blockDefTable;

	/* Methodes */
	
	private void init() {
		this.blockDefTable = new Hashtable<String,CadBlockDef>();
	}
	
//Public
	
	public BlockTable() {
		super(AppDefs.OBJTYPE_BLOCK_TABLE);
		this.init();
	}

	/* Methodes */
	
	public synchronized CadBlockDef newBlockDef(String name) {
		CadBlockDef oResult = null;

		if( this.blockDefTable.containsKey(name) ) {
			oResult = (CadBlockDef)this.blockDefTable.get(name);
		}
		else {
			oResult = CadBlockDef.create(name);
			this.blockDefTable.put(name, oResult);
		}
		
		return oResult;
	}

	public synchronized CadBlockDef getBlockDef(String name) {
		CadBlockDef oResult = null;

		if( this.blockDefTable.containsKey(name) ) {
			oResult = (CadBlockDef)this.blockDefTable.get(name);
		}
		
		return oResult;
	}
	
}
