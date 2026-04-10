/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ObjectDataTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/05/2025
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

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.vo.UndoItemVO;

public class UndoTable extends CadObject 
{
//Private
	private ArrayList<UndoItemVO> lsItem;
	private int currItemPos = -1;
	private int maxItemPos = -1;
	
//Public
	
	public UndoTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_OBJECTDATA_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.lsItem = new ArrayList<UndoItemVO>();
	}

	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		//TODO:
	}

	/* Methodes */
	
	public UndoItemVO stopMark() 
	{
		UndoItemVO undoItem = this.newItem(AppDefs.OPERTYPE_UNDO_STOPMARK_VAL, null, null);
		return undoItem;
	}
	
	public UndoItemVO beginGroup() 
	{
		UndoItemVO undoItem = this.newItem(AppDefs.OPERTYPE_UNDO_BEGINGROUP_VAL, null, null);
		return undoItem;
	}
	
	public UndoItemVO endGroup() 
	{
		UndoItemVO undoItem = this.newItem(AppDefs.OPERTYPE_UNDO_ENDGROUP_VAL, null, null);
		return undoItem;
	}
	
	public synchronized int szLsItem() {
		int sz = lsItem.size();
		return sz;
	}
	
	public synchronized UndoItemVO newItem(
		int operType,
		Object oldObj,
		Object newObj ) 
	{
		UndoItemVO oItem = new UndoItemVO(operType, oldObj, newObj); 
		oItem.debug(AppDefs.DEBUG_LEVEL32);

		int lastPos = this.lsItem.size() - 1;
		if(this.currItemPos < lastPos) {
			this.currItemPos = this.currItemPos + 1;
			this.lsItem.set(this.currItemPos, oItem);
		}
		else {
			this.lsItem.add(oItem);
			
			lastPos = lsItem.size() - 1;
			this.currItemPos = lastPos;
		}
		this.maxItemPos = this.currItemPos;
		
		return oItem;
	}
	
	public synchronized UndoItemVO undoItem() {
		UndoItemVO oResult = null;
		
		int sz = this.lsItem.size();
		if( (this.currItemPos >= 0) && (this.currItemPos < sz) ) {
			oResult = this.lsItem.get( this.currItemPos );
			oResult.debug(AppDefs.DEBUG_LEVEL32);
			
			int operType = oResult.getOperType();
			if(operType == AppDefs.OPERTYPE_UNDO_STOPMARK_VAL)
				return oResult;
			
			this.currItemPos = this.currItemPos - 1;
		}
		return oResult;
	}
	
	public synchronized UndoItemVO redoItem() {
		UndoItemVO oResult = null;
		
		int sz = this.lsItem.size();
		if( (this.currItemPos >= 0) &&
			(this.currItemPos < sz) &&
			(this.currItemPos < this.maxItemPos) ) 
		{
			this.currItemPos += 1;
			
			oResult = this.lsItem.get( this.currItemPos );
			oResult.debug(AppDefs.DEBUG_LEVEL32);
		}
		return oResult;
	}

	public ArrayList<UndoItemVO> undo() {
		ArrayList<UndoItemVO> lsResult = new ArrayList<UndoItemVO>();
		boolean bGroup = false;

		UndoItemVO oItem = this.undoItem();
		while(oItem != null) {
			int operType = oItem.getOperType();
			
			if( (operType == AppDefs.OPERTYPE_UNDO_STOPMARK_VAL) ||
			    (operType == AppDefs.OPERTYPE_UNDO_BEGINGROUP_VAL) ) {
				return lsResult;
			}
			
			if( !bGroup ) {
				if(operType == AppDefs.OPERTYPE_UNDO_ENDGROUP_VAL) {
					bGroup = true;
					continue;
				}
			}
			
			lsResult.add(oItem);			
			if( !bGroup ) break;
			
			oItem = this.undoItem();
		}
		return lsResult;
	}

	public ArrayList<UndoItemVO> redo() {
		ArrayList<UndoItemVO> lsResult = new ArrayList<UndoItemVO>();
		boolean bGroup = false;

		UndoItemVO oItem = this.redoItem();
		while(oItem != null) {
			int operType = oItem.getOperType();
			
			if(operType == AppDefs.OPERTYPE_UNDO_ENDGROUP_VAL) {
				return lsResult;
			}
			
			if( !bGroup ) {
				if(operType == AppDefs.OPERTYPE_UNDO_BEGINGROUP_VAL) {
					bGroup = true;
					continue;
				}
			}
			
			lsResult.add(oItem);			
			if( !bGroup ) break;
			
			oItem = this.undoItem();
		}
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		int currSz = this.szLsItem();
		int currPos = this.currItemPos = -1;

		String str = String.format(
			"Size:%s;CurrPos:%s; ", 
			currSz,
			currPos );
		return str;
	}

	@Override
	public void debug(int debugLevel) {
		AppError.showCmdWarn(debugLevel, this.toStr(), this.getClass());
	}

	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return false;
	}
	
}
