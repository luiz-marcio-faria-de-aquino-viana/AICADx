/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * IfcDataValue.java
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

package br.com.tlmv.aicadxapp.ifc.ifcentry;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.ifc.IfcDefs;

public class IfcDataValue 
{
//Private
	private int numParm;
	private String name;
	private int type;
	private Object ptrVal;
	private String strVal;
	private Integer iVal;
	private Long lVal;
	private Double dVal;

//Public
	
	public IfcDataValue(
		int numParm, 
		String name,
		Object ptrVal )
	{
		this.type = IfcDefs.IFCTYPE_PTR;
		this.numParm = numParm;
		this.name = name;
		this.ptrVal = ptrVal;
	}
	
	public IfcDataValue(
		int numParm, 
		String name,
		String strVal )
	{
		this.type = IfcDefs.IFCTYPE_STR;
		this.numParm = numParm;
		this.name = name;
		this.strVal = strVal;
	}
	
	public IfcDataValue(
		int numParm, 
		String name,
		Integer iVal )
	{
		this.type = IfcDefs.IFCTYPE_INT;
		this.numParm = numParm;
		this.name = name;
		this.iVal = iVal;
	}
	
	public IfcDataValue(
		int numParm, 
		String name,
		Long lVal )
	{
		this.type = IfcDefs.IFCTYPE_LNG;
		this.numParm = numParm;
		this.name = name;
		this.lVal = lVal;
	}
	
	public IfcDataValue(
		int numParm, 
		String name,
		Double dVal )
	{
		this.type = IfcDefs.IFCTYPE_LNG;
		this.numParm = numParm;
		this.name = name;
		this.dVal = dVal;
	}
	
	/* DEBUG */
	
	public String toStr()
	{
		String str = String.format(
			"NumParm:%s;" +
			"Name:%s;" +
			"Type:%s;" +
			"StrVal:%s;",
			this.numParm,
			this.name,
			this.type,
			this.strVal );
		return str;
	}

	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* Getters/Setters */
	
	public int getNumParm() {
		return numParm;
	}
	public void setNumParm(int numParm) {
		this.numParm = numParm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getPtrVal() {
		return ptrVal;
	}
	public void setPtrVal(Object ptrVal) {
		this.ptrVal = ptrVal;
	}
	public String getStrVal() {
		return strVal;
	}
	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}
	public Integer getiVal() {
		return iVal;
	}
	public void setiVal(Integer iVal) {
		this.iVal = iVal;
	}
	public Long getlVal() {
		return lVal;
	}
	public void setlVal(Long lVal) {
		this.lVal = lVal;
	}
	public Double getdVal() {
		return dVal;
	}
	public void setdVal(Double dVal) {
		this.dVal = dVal;
	}
	
}
