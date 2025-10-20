/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.dxf.dxfentry;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;

public class DxfSection 
{
//Private
	private long dxfLineNum;
	private int dxfCode;
	private String dxfSectionName = null;
	//
	private ArrayList<DxfEntry> lsDxfEntry = null;
	private ArrayList<DxfCadEntity> lsDxfCadEntity = null;
	
//Public
	
	public DxfSection(long dxfLineNum, int dxfCode, String dxfSectionName) 
	{
		this.dxfLineNum = dxfLineNum;
		this.dxfCode = dxfCode;
		this.dxfSectionName = dxfSectionName;
		//
		this.lsDxfEntry = new ArrayList<DxfEntry>();
		this.lsDxfCadEntity = new ArrayList<DxfCadEntity>();
	}
	
	/* Methodes */

	public long processAllCadEntity()
	{
		this.lsDxfCadEntity = new ArrayList<DxfCadEntity>();
		int n = 0;

		DxfCadEntity currDxfCadEntity = null;
		long sz = this.szDxfEntry();

		int state = 0;
		
		int pos = 0;
		while(pos < sz) {
			if(state == 0) {
				DxfEntry oEntry1 = this.getDxfEntryAt(pos);

				if(oEntry1.getDxfCode() == AppDefs.DXFCODE_ENTITYTYPE) {
					currDxfCadEntity = new DxfCadEntity(
						n++,
						oEntry1.getDxfLineNum(),
						oEntry1.getDxfCode(),
						oEntry1.getDxfVal());
					currDxfCadEntity.debug(AppDefs.DEBUG_LEVEL06);
					
					this.lsDxfCadEntity.add(currDxfCadEntity);
					state = 1;
				}
				pos += 1;
			}
			else if(state == 1) {
				DxfEntry oEntry2 = this.getDxfEntryAt(pos);

				if(oEntry2.getDxfCode() == AppDefs.DXFCODE_ENTITYTYPE) {
					state = 0;
				}
				else {
					currDxfCadEntity.add(oEntry2);
					pos += 1;
				}
			}
		}
		
		long szCurrDxfCadEntity = currDxfCadEntity.size();
		return szCurrDxfCadEntity;
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
	
	public String getDxfSectionName() {
		return dxfSectionName;
	}
	
	/* LIST_DXF_ENTRY */

	public synchronized long szDxfEntry()
	{
		long sz = this.lsDxfEntry.size();
		return sz;
	}

	public synchronized void addDxfEntry(DxfEntry dxfEntry)
	{
		this.lsDxfEntry.add(dxfEntry);
	}

	public synchronized DxfEntry getDxfEntryAt(int pos)
	{
		DxfEntry oResult = null;
		
		long sz = this.lsDxfEntry.size();
		if(pos < sz) {
			oResult = this.lsDxfEntry.get(pos);
		}
		return oResult;
	}
	
	/* LIST_DXF_CADENTITY */

	public synchronized long szDxfCadEntity()
	{
		long sz = this.lsDxfCadEntity.size();
		return sz;
	}

	public synchronized void addDxfCadEntity(DxfCadEntity dxfCadEntity)
	{
		this.lsDxfCadEntity.add(dxfCadEntity);
	}

	public synchronized DxfCadEntity getDxfCadEntityAt(int pos)
	{
		DxfCadEntity oResult = null;
		
		long sz = this.lsDxfCadEntity.size();
		if(pos < sz) {
			oResult = this.lsDxfCadEntity.get(pos);
		}
		return oResult;
	}

	public synchronized ArrayList<DxfCadEntity> filterDxfCadEntityByEntityType(String entityType)
	{
		ArrayList<DxfCadEntity> lsResult = new ArrayList<DxfCadEntity>();
		
		long sz = this.szDxfCadEntity();
		for(int i = 0; i < sz; i++) {
			DxfCadEntity o = this.getDxfCadEntityAt(i);
			
			if( entityType.compareToIgnoreCase(o.getDxfEntityType()) == 0 ) {
				lsResult.add(o);
			}
		}
		return lsResult;
	}

	public synchronized ArrayList<DxfCadEntity> filterDxfChildCadEntityByEntityType(int startPos, String entityType, String finalEntityType)
	{
		ArrayList<DxfCadEntity> lsResult = new ArrayList<DxfCadEntity>();
		
		long sz = this.szDxfCadEntity();
		for(int i = startPos; i < sz; i++) {
			DxfCadEntity o = this.getDxfCadEntityAt(i);
			
			if( finalEntityType.compareToIgnoreCase(o.getDxfEntityType()) == 0 ) break;
			
			if( entityType.compareToIgnoreCase(o.getDxfEntityType()) == 0 ) {
				lsResult.add(o);
			}
		}
		return lsResult;
	}
	
}
