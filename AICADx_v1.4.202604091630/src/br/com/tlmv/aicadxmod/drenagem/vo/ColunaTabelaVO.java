/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ColunaTabelaVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 16/04/2025
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

package br.com.tlmv.aicadxmod.drenagem.vo;

import java.awt.Color;

public class ColunaTabelaVO 
{
//Private
	private int oid;
	private String columnName;
	private String titulo;
	private int width;
	private int height;
	private boolean bEditable;
	private Class dataType;
	private int dprec;
	private boolean bFinishVisible;
	
//Public
	
	public ColunaTabelaVO(
		int oid,
		String columnName,
		String titulo,
		int width,
		int height,
		boolean bEditable,
		Class dataType,
		int dprec,
		boolean bVisibleAtFoliage)
	{
		this.oid = oid;
		this.columnName = columnName;
		this.titulo = titulo;
		this.width = width;
		this.height = height;
		this.bEditable = bEditable;
		this.dataType = dataType;
		this.dprec = dprec;
		this.bFinishVisible = bVisibleAtFoliage;
	}
	
	/* Getters/Setters */

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isEditable() {
		return bEditable;
	}

	public void setEditable(boolean bEditable) {
		this.bEditable = bEditable;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Class getDataType() {
		return dataType;
	}

	public void setDataType(Class dataType) {
		this.dataType = dataType;
	}

	public Color getColor() {
		Color c = Color.orange;
		if( this.bEditable )
			c = Color.white;
		return c;
	}

	public int getDprec() {
		return dprec;
	}

	public void setDprec(int dprec) {
		this.dprec = dprec;
	}

	public boolean isFinishVisible() {
		return bFinishVisible;
	}

	public void setFnishVisible(boolean bFinishVisible) {
		this.bFinishVisible = bFinishVisible;
	}
	
}
