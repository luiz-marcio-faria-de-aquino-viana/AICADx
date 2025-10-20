/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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
