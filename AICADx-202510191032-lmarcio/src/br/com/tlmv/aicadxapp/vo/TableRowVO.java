/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * TableRowVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import java.util.ArrayList;

public class TableRowVO 
{
//Private
	private ArrayList<TableCellVO> lsCells = null;
	
//Public
	
	public TableRowVO()
	{
		this.init();
	}
	
	/* Methodes */

	public void init()
	{
		this.lsCells = new ArrayList<TableCellVO>();
	}
	
	/* Getters/Setters */

	public synchronized void addTableCell(TableCellVO o) {
		this.lsCells.add(o);
	}
		
	public synchronized TableCellVO getTableCell(int pos) {
		int sz = this.lsCells.size();
		if( (pos >= 0) && (pos < sz) ) { 
			TableCellVO o = this.lsCells.get(pos);
			return o;
		}
		return null;
	}
	
	public synchronized int getNumTableCell() {
		int sz = this.lsCells.size();
		return sz;
	}
	
}
