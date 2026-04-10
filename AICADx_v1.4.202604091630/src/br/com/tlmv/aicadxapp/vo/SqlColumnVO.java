/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * SqlColumnVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2026
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

public class SqlColumnVO 
{
//Private	
	private Integer colNum;
	private String colName;
	private String colSqlType;
	private String strOldVal;
	private String strNewVal; 
	private Integer iOldVal;
	private Integer iNewVal; 
	private Long lOldVal;
	private Long lNewVal; 
	private Double dOldVal;
	private Double dNewVal; 
	private Boolean bOldVal;
	private Boolean bNewVal; 
	
//Public

	public SqlColumnVO(String colName, String colSqlType)
	{
		this.colName = colName;
		this.colSqlType = colSqlType;
	}
	
	/* Methodes */
	
	public void init(String strVal) 		
	{
		this.strOldVal = this.strNewVal;
		this.strNewVal = strVal;
	}
	
	public void init(Integer iVal) 		
	{
		this.iOldVal = this.iNewVal;
		this.iNewVal = iVal;
	}
	
	public void init(Long lVal) 		
	{
		this.lOldVal = this.lNewVal;
		this.lNewVal = lVal;
	}
	
	public void init(Double dVal) 		
	{
		this.dOldVal = this.dNewVal;
		this.dNewVal = dVal;
	}
	
	public void init(Boolean bVal) 		
	{
		this.bOldVal = this.bNewVal;
		this.bNewVal = bVal;
	}
	
	/* Getters/Setters */

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColSqlType() {
		return colSqlType;
	}

	public void setColSqlType(String colSqlType) {
		this.colSqlType = colSqlType;
	}

	public Double getDblOldVal() {
		return dOldVal;
	}

	public void setDblOldVal(Double dOldVal) {
		this.dOldVal = dOldVal;
	}

	public Double getDblNewVal() {
		return dNewVal;
	}

	public void setDblNewVal(Double dNewVal) {
		this.dNewVal = dNewVal;
	}

	public Long getLngOldVal() {
		return lOldVal;
	}

	public void setLngOldVal(Long lOldVal) {
		this.lOldVal = lOldVal;
	}

	public Long getLngNewVal() {
		return lNewVal;
	}

	public void setLngNewVal(Long lNewVal) {
		this.lNewVal = lNewVal;
	}

	public Integer getIntOldVal() {
		return iOldVal;
	}

	public void setIntOldVal(Integer iOldVal) {
		this.iOldVal = iOldVal;
	}

	public Integer getIntNewVal() {
		return iNewVal;
	}

	public void setIntNewVal(Integer iNewVal) {
		this.iNewVal = iNewVal;
	}

	public Boolean getBoolOldVal() {
		return bOldVal;
	}

	public void setBoolOldVal(Boolean bOldVal) {
		this.bOldVal = bOldVal;
	}

	public Boolean getBoolNewVal() {
		return bNewVal;
	}

	public void setBoolNewVal(Boolean bNewVal) {
		this.bNewVal = bNewVal;
	}

	public String getStrOldVal() {
		return strOldVal;
	}

	public void setStrOldVal(String strOldVal) {
		this.strOldVal = strOldVal;
	}

	public String getStrNewVal() {
		return strNewVal;
	}

	public void setStrNewVal(String strNewVal) {
		this.strNewVal = strNewVal;
	}
		
}
