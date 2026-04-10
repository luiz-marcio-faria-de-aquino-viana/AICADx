/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * UndoItemVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/03/2026
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;

public class UndoItemVO 
{
//Private
	private int operType;
	private Object oldObj;
	private Object newObj;
	private long timestamp;
	
//Public
	
	public UndoItemVO(
		int operType,
		Object oldObj,
		Object newObj )
	{
		this.init(
			operType,
			oldObj,
			newObj );
	}

	/* Methodes */

	public void init(
		int operType,
		Object oldObj,
		Object newObj )
	{
		Date dataHoraAtual = new Date();
		
		this.operType = operType;
		this.oldObj = oldObj;
		this.newObj = newObj;
		
		this.timestamp = dataHoraAtual.getTime();
	}
	
	/* DEBUG */
	
	public String toStr() {
		int objType = this.getOperType();
		String objTypeStr = this.getOperTypeStr();
		String strOldObj = ( (this.oldObj != null) ? oldObj.toString() : "" ); 
		String strNewObj = ( (this.newObj != null) ? newObj.toString() : "" ); 
		
		String str = String.format( 
			"OperType:%s;OperTypeStr:%s;OldObj:%s;NewObj:%s; ", 
			objType,
			objTypeStr,
			strOldObj,
			strNewObj );
		return str;
	}
	
	public void debug(int debugLevel) {
		AppError.showCmdWarn(debugLevel, this.toStr(), this.getClass());
	}
	
	/* Getters/Setters */
	
	public String getOperTypeStr() {
		String operTypeStr = AppDefs.ARR_OPERTYPE_UNDO[0];
		int sz = AppDefs.ARR_OPERTYPE_UNDO.length;

		int pos = this.operType - AppDefs.OPERTYPE_UNDO_NONE_VAL;
		if( (pos >= 0) && (pos < sz) ) {
			operTypeStr = AppDefs.ARR_OPERTYPE_UNDO[pos];
		}
		return operTypeStr;
	}

	public int getOperType() {
		return operType;
	}

	public void setOperType(int operType) {
		this.operType = operType;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getOldObj() {
		return oldObj;
	}

	public void setOldObj(Object oldObj) {
		this.oldObj = oldObj;
	}

	public Object getNewObj() {
		return newObj;
	}

	public void setNewObj(Object newObj) {
		this.newObj = newObj;
	}

}
