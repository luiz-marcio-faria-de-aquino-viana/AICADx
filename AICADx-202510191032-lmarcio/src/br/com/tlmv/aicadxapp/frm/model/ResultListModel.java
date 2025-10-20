/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ResultListModel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/07/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class ResultListModel extends AbstractListModel 
{
//Private

	private static final long serialVersionUID = 202504041459L;

	private ArrayList<ItemDataVO> lsItemData = null;
	
//Public
	
	public ResultListModel(ArrayList<ItemDataVO> lsItemData)
	{
		this.lsItemData = lsItemData;
	}
	
	/* Methodes */

	@Override
	public int getSize() {
		int sz = this.lsItemData.size();
		return sz;
	}

	@Override
	public Object getElementAt(int index) {
		int sz = this.lsItemData.size();
		if(index < sz) {
			ItemDataVO oResultListItem = this.lsItemData.get(index);
			return oResultListItem;
		}
		return null;
	}
	
}
