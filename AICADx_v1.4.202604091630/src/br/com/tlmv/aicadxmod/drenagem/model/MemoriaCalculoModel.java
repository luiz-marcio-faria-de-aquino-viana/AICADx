/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MamoriaCalculoModel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxmod.drenagem.model;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.model.BasicTableModel;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;

public class MemoriaCalculoModel extends BasicTableModel 
{
//Private

	private static final long serialVersionUID = 202504151126L;

	private ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItemData = null;

	private ColunaTabelaVO[] arrColumnName = null;
	
//Public
	
	public MemoriaCalculoModel(ColunaTabelaVO[] arrColumnName, ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItemData)
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
		int szArrColumnName = this.arrColumnName.length;
		if(columnIndex < szArrColumnName) {
			ColunaTabelaVO oCol = this.arrColumnName[columnIndex];
			
			String columnName = oCol.getColumnName();			
			int dprec = oCol.getDprec();

			int szItemData = this.lsItemData.size();
			if(rowIndex < szItemData) {
				CadMemoriaCalculoItemDrenagemOData o = this.lsItemData.get(rowIndex);
				Object val = o.toStrValueByName(columnName, dprec);
				return val;
			}
		}
		return null;
	}

	@Override
	public Object getHeaderAt(int columnIndex) {
		int sz = this.arrColumnName.length;
		if(columnIndex < sz) {
			ColunaTabelaVO o = this.arrColumnName[columnIndex];
			return o;
		}
		return super.getColumnName(columnIndex);
	}

	@Override
	public int getHdrHeight()
	{
		int maxHeight = AppDefs.TBLHDR_HEIGHT;
		return maxHeight;		
	}

	@Override
	public int getHdrWidth(int columnIndex)
	{
		int sz = this.arrColumnName.length;
		if(columnIndex < sz) {
			ColunaTabelaVO o = this.arrColumnName[columnIndex];
			return o.getWidth();
		}
		return AppDefs.TBLHDR_WIDTH;
	}

	@Override
	public String getColumnName(int column) {
		int sz = this.arrColumnName.length;
		if(column < sz) {
			ColunaTabelaVO o = this.arrColumnName[column];
			return o.getTitulo();
		}
		return super.getColumnName(column);
	}
	
	/* MEMORIA_CALCULO_ITEM */

	public CadMemoriaCalculoItemDrenagemOData getMemoriaCalculoItemAt(int pos) {
		if(pos < this.lsItemData.size()) {
			CadMemoriaCalculoItemDrenagemOData o = this.lsItemData.get(pos);
			return o;
		}
		return null;
	}

	public int getSzLsMemoriaCalculoItem() {
		return this.lsItemData.size();
	}
	
}
