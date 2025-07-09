/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GroupItemDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
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
