/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * FilterEntityTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/03/2025
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

package br.com.tlmv.aicadxapp.cad.filter;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class FilterEntityTable 
{
//Private
	private CadDocumentDef doc = null;
	
	private Hashtable<Integer,CadEntity> entityTable = null;
	private ArrayList<CadEntity> lsEntity = null;
	//
	private Hashtable<Integer,Integer> mapResult = null; 
	private ArrayList<Integer> lsResult = null;

	private FilterEntityTableExecutor[] arrExecutor;
	private int maxNumExecutor = 0;
	
//Public

	public FilterEntityTable(CadDocumentDef doc)
	{
		this.doc = doc;

		this.entityTable = new Hashtable<Integer,CadEntity>();
		this.lsEntity = new ArrayList<CadEntity>();
		//
		this.mapResult = new Hashtable<Integer,Integer>(); 
		this.lsResult = new ArrayList<Integer>();
	}
	
	public FilterEntityTable(
		CadDocumentDef doc,
		Hashtable<Integer,CadEntity> entityTable,
		ArrayList<CadEntity> lsEntity)
	{
		this.doc = doc;

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

	public CadDocumentDef getDoc() {
		return doc;
	}

	public void setDoc(CadDocumentDef doc) {
		this.doc = doc;
	}
	
}
