/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ObjectPropertyModel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class ObjectPropertyModel extends AbstractTableModel 
{
//Private

	private static final long serialVersionUID = 202504041459L;

	private ArrayList<ItemDataVO> lsItemData = null;

	private String[] arrColumnName = null;
	
//Public
	
	public ObjectPropertyModel(String[] arrColumnName, ArrayList<ItemDataVO> lsItemData)
	{
		this.arrColumnName = arrColumnName;
		this.lsItemData = lsItemData;
	}
	
	/* Methodes */
	
	@Override
	public int getRowCount() {
		int sz = this.lsItemData.size();
		return sz;
	}

	@Override
	public int getColumnCount() {
		int sz = this.arrColumnName.length;
		return sz;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int sz = this.lsItemData.size();
		if(rowIndex < sz) {
			ItemDataVO o = this.lsItemData.get(rowIndex);
			
			if(columnIndex == 0)
				return o.getItemDataId();
			else
				return o.getDescricao();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		int sz = this.arrColumnName.length;
		if(column < sz) {
			return arrColumnName[column];
		}
		return super.getColumnName(column);
	}
	
}
