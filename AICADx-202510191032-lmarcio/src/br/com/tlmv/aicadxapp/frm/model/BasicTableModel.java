/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BasicTableModel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.model;

import javax.swing.table.AbstractTableModel;

import br.com.tlmv.aicadxapp.AppDefs;

public abstract class BasicTableModel extends AbstractTableModel 
{
//Public
	
	@Override
	public abstract int getRowCount();

	@Override
	public abstract int getColumnCount();

	@Override
	public abstract Object getValueAt(int rowIndex, int columnIndex);

	public abstract Object getHeaderAt(int columnIndex);

	public abstract int getHdrHeight();

	public abstract int getHdrWidth(int columnIndex);

}
