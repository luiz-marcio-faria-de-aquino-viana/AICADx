/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DxfSection.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
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

package br.com.tlmv.aicadxapp.dxf.dxfentry;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class DxfCadEntity 
{
//Private
	private int dxfEntityNum;
	private long dxfLineNum;
	private int dxfCode;
	private String dxfEntityType = null;
	//
	private ArrayList<DxfEntry> lsDxfEntry = null;
	
//Public
	
	public DxfCadEntity(int dxfEntityNum, long dxfLineNum, int dxfCode, String dxfEntityType) 
	{
		this.dxfEntityNum = dxfEntityNum;
		this.dxfLineNum = dxfLineNum;
		this.dxfCode = dxfCode;
		this.dxfEntityType = dxfEntityType;
		//
		this.lsDxfEntry = new ArrayList<DxfEntry>();
	}
	
	/* Methodes */
	
	public ArrayList<String> toDxf()
	{
		ArrayList<String> lsDxfCadEntry = new ArrayList<String>();
		
		String strDxf = StringUtil.fillLeft(Integer.toString( this.getDxfCode() ), ' ', 3);
		lsDxfCadEntry.add(strDxf);
		
		strDxf = this.dxfEntityType; 
		lsDxfCadEntry.add(strDxf);

		for(DxfEntry oEnt : lsDxfEntry) {
			ArrayList<String> lsDxfEntry = oEnt.toDxf();
			lsDxfCadEntry.addAll( lsDxfEntry );
		}
		return lsDxfCadEntry;
	}
	
	/* DEBUG */
	
	public void debug(int debugLevel) 
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = String.format(
			"EntityNum:%s;LineNum:%s;DxfCode:%s;EntityType:%s;\n", 
			this.dxfEntityNum,
			this.dxfLineNum,
			this.dxfCode,
			this.dxfEntityType);
		AppError.showCmdWarn(debugLevel, str, this.getClass());
	}
	
	/* Getters/Setters */

	public long getDxfLineNum()
	{
		return this.dxfLineNum;
	}
	
	public int getDxfCode()
	{
		return this.dxfCode;
	}
	
	public String getDxfEntityType() {
		return this.dxfEntityType;
	}
	
	/* LIST */

	public synchronized long size()
	{
		long sz = this.lsDxfEntry.size();
		return sz;
	}

	public synchronized void add(DxfEntry dxfEntry)
	{
		this.lsDxfEntry.add(dxfEntry);
	}

	public synchronized DxfEntry getAt(int pos)
	{
		DxfEntry oResult = null;
		
		long sz = this.lsDxfEntry.size();
		if(pos < sz) {
			oResult = this.lsDxfEntry.get(pos);
		}
		return oResult;
	}

	public synchronized DxfEntry getByDxfCode(int dxfCode)
	{
		long sz = this.lsDxfEntry.size();
		for(int i = 0; i < sz; i++) {
			DxfEntry o = this.getAt(i);
			if(o.getDxfCode() == dxfCode)
				return o;
		}
		return null;
	}

	public int getDxfEntityNum() {
		return dxfEntityNum;
	}

	public void setDxfEntityNum(int dxfEntityNum) {
		this.dxfEntityNum = dxfEntityNum;
	}
	
}
