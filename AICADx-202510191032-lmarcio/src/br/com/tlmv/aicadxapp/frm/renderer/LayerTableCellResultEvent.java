/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerTableCellResultEvent.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.renderer;

public class LayerTableCellResultEvent
{
//Private
	private int rownum = -1;
	private int colnum = -1;
	private Object oldval = null;
	private Object newval = null;
	
//Public
	
	public LayerTableCellResultEvent(int rownum, int colnum, Object oldval, Object newval)
	{
		this.rownum = rownum;
		this.colnum = colnum;
		this.oldval = oldval;
		this.newval = newval;
	}

	/* Getters/Setters */

	public int getColnum() {
		return colnum;
	}

	public void setColnum(int colnum) {
		this.colnum = colnum;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public Object getOldval() {
		return oldval;
	}

	public void setOldval(Object oldval) {
		this.oldval = oldval;
	}

	public Object getNewval() {
		return newval;
	}

	public void setNewval(Object newval) {
		this.newval = newval;
	}

}
