/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * FilterEntityTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.filter;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class FilterEntityTable 
{
//Private
	private Hashtable<Integer,CadEntity> entityTable = null;
	private ArrayList<CadEntity> lsEntity = null;
	//
	private Hashtable<Integer,Integer> mapResult = null; 
	private ArrayList<Integer> lsResult = null;

	private FilterEntityTableExecutor[] arrExecutor;
	private int maxNumExecutor = 0;
	
//Public
	
	public FilterEntityTable(
		Hashtable<Integer,CadEntity> entityTable,
		ArrayList<CadEntity> lsEntity)
	{
		this.entityTable = entityTable;
		this.lsEntity = lsEntity;
		//
		this.mapResult = new Hashtable<Integer,Integer>(); 
		this.lsResult = new ArrayList<Integer>();
	}
	
	/* Operations */
	
	public int filterAllEntityByRect(GeomPoint2d ptMinMcs, GeomPoint2d ptMaxMcs) {
		int stepSz = AppDefs.FILTERENTTBL_MAXNUMENTITIES_PER_EXECUTOR;		
		
		int sz = this.lsEntity.size();

		this.maxNumExecutor = sz / stepSz;
		
		int lastStepSz = sz % stepSz;
		if(lastStepSz != 0)
			this.maxNumExecutor = this.maxNumExecutor + 1;
		
		this.arrExecutor = new FilterEntityTableExecutor[this.maxNumExecutor];		
		int currStartPos = 0;
		int currEndPos = 0;
		
		for(int i = 0; i < this.maxNumExecutor; i++) {
			currEndPos = currStartPos + stepSz;
			if(currEndPos > sz) {
				currEndPos = sz;
			}
			
			FilterEntityTableExecutor p = new FilterEntityTableExecutor(i, this, currStartPos, currEndPos, ptMinMcs, ptMaxMcs);					
			this.arrExecutor[i] = p;

			p.startExecutor();
		}
		
		for(int i = 0; i < this.maxNumExecutor; i++) {
			FilterEntityTableExecutor p = this.arrExecutor[i];

			p.waitExecutor();
		}
		
		int szResult = this.lsResult.size();
		return szResult;
	}

	/* Methodes */
	
	
	public CadEntity getEntityAt(int pos)
	{
		CadEntity oResult = null;
		
		int sz = this.lsEntity.size();
		if(pos < sz) {
			oResult = this.lsEntity.get(pos); 
		}
		return oResult;
	}
	
	public synchronized boolean addEntity(CadEntity oEnt) {
		Integer objectId = oEnt.getObjectId();
		if( !this.mapResult.containsKey(objectId) ) {
			this.mapResult.put(objectId, objectId);
			this.lsResult.add(objectId);
			return true;
		}
		return false;
	}
	
	/* Getters/Setters */

	public Hashtable<Integer, Integer> getMapResult() {
		return mapResult;
	}

	public ArrayList<Integer> getLsResult() {
		return lsResult;
	}
	
}
