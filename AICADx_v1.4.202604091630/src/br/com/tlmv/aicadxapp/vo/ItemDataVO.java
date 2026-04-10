/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ItemDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/01/2026
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
	private boolean bVal;
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
		int intVal)
	{
		this.itemDataId = itemDataId;
		this.descricao = descricao;
		this.intVal = intVal;
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
	
	public ItemDataVO(
		String itemDataId,
		String descricao,
		boolean bVal)
	{
		this.itemDataId = itemDataId;
		this.descricao = descricao;
		this.bVal = bVal;
	}
	
	public ItemDataVO(
		int itemDataId,
		String descricao,
		boolean bVal)
	{
		this.itemDataId = Integer.toString(itemDataId);
		this.descricao = descricao;
		this.bVal = bVal;
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

	public boolean isVal() {
		return bVal;
	}

	public void sebVal(boolean bVal) {
		this.bVal = bVal;
	}

	public GeomDimension2d getGeomDim2d() {
		return geomDim2d;
	}

	public void setGeomDim2d(GeomDimension2d geomDim2d) {
		this.geomDim2d = geomDim2d;
	}	
	
}
