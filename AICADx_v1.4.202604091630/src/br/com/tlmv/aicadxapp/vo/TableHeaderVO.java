/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TableHeaderVO.java
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

public class TableHeaderVO 
{
//Private
	private int colNum;
	private String title;
	private int horizAlign;
	private int vertAlign;
	private double colWidthScr;
	private double rowHeightScr;
	
//Public
	
	public TableHeaderVO(
		int colNum,
		String title,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr)
	{
		this.init(
			colNum,
			title,
			horizAlign,
			vertAlign,
			colWidthScr,
			rowHeightScr);
	}
	
	public TableHeaderVO(TableHeaderVO o)
	{
		this.init(o);
	}

	/* Methodes */

	public void init(
		int colNum,
		String title,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr)
	{
		this.colNum = colNum;
		this.title = title;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
		this.colWidthScr = colWidthScr;
		this.rowHeightScr = rowHeightScr;
	}
		
	public void init(TableHeaderVO o)
	{
		this.init(
			o.colNum,
			o.title,
			o.horizAlign,
			o.vertAlign,
			o.colWidthScr,
			o.rowHeightScr);
	}

	/* Getters/Setters */

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
}
