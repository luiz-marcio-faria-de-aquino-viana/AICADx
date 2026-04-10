/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TableCellVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
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

public class TableCellVO 
{
//Private
	private int rowNum;
	private int colNum;
	private String fldName;
	private String text;
	private int horizAlign;
	private int vertAlign;
	private double colWidthScr;
	private double rowHeightScr;
	private Object obj;
	
//Public
	
	public TableCellVO(
		int rowNum,
		int colNum,
		String fldName,
		String text,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr)
	{
		this.init(
			rowNum,
			colNum,
			fldName,
			text,
			horizAlign,
			vertAlign,
			colWidthScr,
			rowHeightScr,
			null);
	}
	
	public TableCellVO(
		int rowNum,
		int colNum,
		String fldName,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr,
		Object obj)
	{
		this.init(
			rowNum,
			colNum,
			fldName,
			null,
			horizAlign,
			vertAlign,
			colWidthScr,
			rowHeightScr,
			obj);
	}
		
	public TableCellVO(TableCellVO o)
	{
		this.init(o);
	}

	/* Methodes */

	public void init(
		int rowNum,
		int colNum,
		String fldName,		
		String text,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr,
		Object obj)
	{
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.fldName = fldName;
		this.text = text;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
		this.colWidthScr = colWidthScr;
		this.rowHeightScr = rowHeightScr;
		this.obj = obj;
	}

	public void init(TableCellVO o)
	{
		this.init(
			o.rowNum,
			o.colNum,
			o.fldName,
			o.text,
			o.horizAlign,
			o.vertAlign,
			o.colWidthScr,
			o.rowHeightScr,
			o.obj);
	}

	/* Getters/Setters */

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getFldName() {
		return fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getHorizAlign() {
		return horizAlign;
	}

	public void setHorizAlign(int horizAlign) {
		this.horizAlign = horizAlign;
	}

	public int getVertAlign() {
		return vertAlign;
	}

	public void setVertAlign(int vertAlign) {
		this.vertAlign = vertAlign;
	}

	public double getColWidthScr() {
		return colWidthScr;
	}

	public void setColWidthScr(double colWidthScr) {
		this.colWidthScr = colWidthScr;
	}

	public double getRowHeightScr() {
		return rowHeightScr;
	}

	public void setRowHeightScr(double rowHeightScr) {
		this.rowHeightScr = rowHeightScr;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
