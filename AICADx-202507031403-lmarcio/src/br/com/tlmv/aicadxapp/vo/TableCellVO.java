/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * TableCellVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

public class TableCellVO 
{
//Private
	private int rowNum;
	private int colNum;
	private String text;
	private int horizAlign;
	private int vertAlign;
	private double colWidthScr;
	private double rowHeightScr;
	
//Public
	
	public TableCellVO(
		int rowNum,
		int colNum,
		String text,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr)
	{
		this.init(
			rowNum,
			colNum,
			text,
			horizAlign,
			vertAlign,
			colWidthScr,
			rowHeightScr);
	}
	
	public TableCellVO(TableCellVO o)
	{
		this.init(o);
	}

	/* Methodes */

	public void init(
		int rowNum,
		int colNum,
		String text,
		int horizAlign,
		int vertAlign,
		double colWidthScr,
		double rowHeightScr)
	{
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.text = text;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
		this.colWidthScr = colWidthScr;
		this.rowHeightScr = rowHeightScr;
	}

	public void init(TableCellVO o)
	{
		this.init(
			o.rowNum,
			o.colNum,
			o.text,
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

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
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
	
}
