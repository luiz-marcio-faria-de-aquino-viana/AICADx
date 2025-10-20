/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ItemDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import java.util.Date;

import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class ItemDataVO 
{
//Private
	private String itemDataId;
	private String descricao;
	private int intVal;
	private double dblVal;
	private long lngVal;
	private Date dateVal;
	private String strVal;
	private GeomDimension2d geomDim2d;
	
//Public
	
	public ItemDataVO(
		int itemDataId,
		String descricao)
	{
		this.itemDataId = Integer.toString(itemDataId);
		this.descricao = descricao;
	}
	
	public ItemDataVO(
		String itemDataId,
		String descricao)
	{
		this.itemDataId = itemDataId;
		this.descricao = descricao;
	}
	
	public ItemDataVO(
		String itemDataId,
		String descricao,
		String strVal)
	{
		this.itemDataId = itemDataId;
		this.descricao = descricao;
		this.strVal = strVal;
	}
	
	public ItemDataVO(
		int itemDataId,
		String descricao,
		String strVal)
	{
		this.itemDataId = Integer.toString(itemDataId);
		this.descricao = descricao;
		this.strVal = strVal;
	}
	
	public ItemDataVO(
		String itemDataId,
		String descricao,
		double dblVal)
	{
		this.itemDataId = itemDataId;
		this.descricao = descricao;
		this.dblVal = dblVal;
	}
	
	public ItemDataVO(
		int itemDataId,
		String descricao,
		double dblVal)
	{
		this.itemDataId = Integer.toString(itemDataId);
		this.descricao = descricao;
		this.dblVal = dblVal;
	}
		
	public ItemDataVO(ItemDataVO o)
	{
		this.itemDataId = o.itemDataId;
		this.descricao = o.descricao;
	}
	
	public ItemDataVO(
		String itemDataId,
		String descricao,
		GeomDimension2d geomDim2d)
	{
		this.itemDataId = itemDataId;
		this.descricao = descricao;
		this.geomDim2d = geomDim2d;
	}

	/* Methodes */
	
	public String toString()
	{
		return this.descricao;
	}
	
	/* Getters/Setters */

	public int getItemDataIdVal() {
		return StringUtil.safeInt(itemDataId);
	}
		
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getItemDataId() {
		return itemDataId;
	}

	public void setItemDataId(String itemDataId) {
		this.itemDataId = itemDataId;
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

	public GeomDimension2d getGeomDim2d() {
		return geomDim2d;
	}

	public void setGeomDim2d(GeomDimension2d geomDim2d) {
		this.geomDim2d = geomDim2d;
	}	
	
}
