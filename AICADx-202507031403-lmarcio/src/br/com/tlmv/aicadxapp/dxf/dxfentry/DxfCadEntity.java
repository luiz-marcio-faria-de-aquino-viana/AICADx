/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DxfSection.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dxf.dxfentry;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;

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
