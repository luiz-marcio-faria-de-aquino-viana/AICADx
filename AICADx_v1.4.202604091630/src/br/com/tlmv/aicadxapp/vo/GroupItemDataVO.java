/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GroupItemDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
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

package br.com.tlmv.aicadxapp.vo;

import java.util.ArrayList;
import java.util.Date;

public class GroupItemDataVO 
{
//Private
	private String groupItemDataId;
	private String descricao;
	private int intVal;
	private double dblVal;
	private long lngVal;
	private Date dateVal;
	private String strVal;
	//
	private ArrayList<ItemDataVO> lsItemData = null;
	
//Public
	
	public GroupItemDataVO(
		String groupItemDataId,
		String descricao)
	{
		this.groupItemDataId = groupItemDataId;
		this.descricao = descricao;
		//
		this.lsItemData = new ArrayList<ItemDataVO>();
	}
	
	public GroupItemDataVO(
		String groupItemDataId,
		String descricao,
		double scale)
	{
		this.groupItemDataId = groupItemDataId;
		this.descricao = descricao;
		this.dblVal = scale;
		//
		this.lsItemData = new ArrayList<ItemDataVO>();
	}
		
	public GroupItemDataVO(GroupItemDataVO o)
	{
		this.groupItemDataId = o.groupItemDataId;
		this.descricao = o.descricao;

		this.lsItemData = new ArrayList<ItemDataVO>();
		for(ItemDataVO oItemData : this.lsItemData) {
			ItemDataVO newItemData = new ItemDataVO(oItemData);
			this.lsItemData.add(newItemData);
		}
	}
	
	/* LIST_ITEM_DATA */
	
	public synchronized int szLsItemData()
	{
		int sz = this.lsItemData.size();
		return sz;
	}
	
	public synchronized void addItemData(ItemDataVO oItemData)
	{
		this.lsItemData.add(oItemData);
	}

	public synchronized ItemDataVO getItemDataAt(int pos)
	{
		ItemDataVO oResult = null;
		
		int sz = this.lsItemData.size();
		if(pos < sz) {
			oResult = this.lsItemData.get(pos);
		}
		return oResult;
	}	
	
	/* Methodes */
	
	public String toString()
	{
		return this.descricao;
	}
	
	/* Getters/Setters */
		
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getGroupItemDataId() {
		return groupItemDataId;
	}

	public void setGroupItemDataId(String groupItemDataId) {
		this.groupItemDataId = groupItemDataId;
	}

	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public double getDblVal() {
		return dblVal;
	}

	public void setDblVal(double dblVal) {
		this.dblVal = dblVal;
	}

	public long getLngVal() {
		return lngVal;
	}

	public void setLngVal(long lngVal) {
		this.lngVal = lngVal;
	}

	public Date getDateVal() {
		return dateVal;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public String getStrVal() {
		return strVal;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}

	public ArrayList<ItemDataVO> getLsItemData() {
		return lsItemData;
	}

	public void setLsItemData(ArrayList<ItemDataVO> lsItemData) {
		this.lsItemData = lsItemData;
	}	
	
}
