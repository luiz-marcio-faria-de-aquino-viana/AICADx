/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * LevelTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/11/2025
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
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.vo.LevelVO;

public class LevelTable extends CadObject 
{
//Private
	private Hashtable<String,CadLevel> levelTable;
	
//Public
	
	public LevelTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_LEVEL_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.levelTable = new Hashtable<String,CadLevel>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,CadLevel> newLevelTable = new Hashtable<String,CadLevel>();
		
		Collection colLevel = this.levelTable.values();
		Iterator iterLevel = colLevel.iterator();
		while( iterLevel.hasNext() ) {
			CadLevel oLevel = (CadLevel)iterLevel.next();

			String levelName = oLevel.getLevelLocalName();
			newLevelTable.put(levelName, oLevel);
		}
		this.levelTable = newLevelTable;
	}

	/* Methodes */
	
	public synchronized CadLevel newLevel(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		String levelName,
		String levelText,
		GeomPoint2d ptI, 
		GeomPoint2d ptF, 
		double zLevel)
	{
		CadLevel oResult = null;

		if( this.levelTable.containsKey(levelName) ) {
			oResult = (CadLevel)this.levelTable.get(levelName);
		}
		else {
			oResult = CadLevel.create(
				oBlkDef,
				oLayer,
				null, 
				zLevel,
				levelName,
				levelText,
				ptI, 
				ptF );
			this.levelTable.put(levelName, oResult);
		}
		return oResult;
	}
		
	public synchronized CadLevel newLevel(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		String levelName,
		String levelText,
		GeomPoint3d ptI, 
		GeomPoint3d ptF, 
		double zLevel)
	{
		CadLevel oResult = null;

		if( this.levelTable.containsKey(levelName) ) {
			oResult = (CadLevel)this.levelTable.get(levelName);
		}
		else {
			oResult = CadLevel.create(
				oBlkDef,
				oLayer,
				null, 
				zLevel,
				levelName,
				levelText,
				ptI, 
				ptF );
			this.levelTable.put(levelName, oResult);
		}
		return oResult;
	}
		
	public synchronized CadLevel newLevel(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		String levelName,
		String levelText,
		double xI,
		double yI,
		double xF, 
		double yF, 
		double zLevel)
	{
		CadLevel oResult = null;
	
		if( this.levelTable.containsKey(levelName) ) {
			oResult = (CadLevel)this.levelTable.get(levelName);
		}
		else {
			oResult = CadLevel.create(
				oBlkDef,
				oLayer,
				null, 
				zLevel,
				levelName,
				levelText,
				xI,
				yI,
				xF, 
				yF );
			this.levelTable.put(levelName, oResult);
		}
		return oResult;
	}
	
	public synchronized CadLevel newLevel(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		LevelVO o)
	{
		CadLevel oResult = null;
	
		if( this.levelTable.containsKey( o.getLevelLocalName() ) ) {
			oResult = (CadLevel)this.levelTable.get( o.getLevelLocalName() );
		}
		else {
			String levelName = o.getLevelLocalName();
			String levelText = o.getLevelLocalText();
			GeomPoint3d ptI = new GeomPoint3d( o.getPtI() ); 
			GeomPoint3d ptF = new GeomPoint3d( o.getPtF() ); 
			double zLevel = o.getZLevel();
			
			oResult = CadLevel.create(
				oBlkDef,
				oLayer,
				null, 
				zLevel,
				levelName,
				levelText,
				ptI, 
				ptF );
			this.levelTable.put(levelName, oResult);
		}
		return oResult;
	}
		
	public synchronized CadLevel addLevel(CadLevel oLevel) {
		CadLevel oResult = null;
		
		if( this.levelTable.containsKey(oLevel.getLevelLocalName()) ) {
			oResult = (CadLevel)this.levelTable.get(oLevel.getLevelLocalName());
		}
		else {
			oResult = oLevel;
			
			this.levelTable.put(oLevel.getLevelLocalName(), oLevel);
		}
		return oResult;
	}

	public synchronized CadLevel removeLevel(String levelName) {
		if( this.levelTable.containsKey(levelName) ) {
			CadLevel oLevel = this.levelTable.remove(levelName);
			return oLevel;
		}
		return null;
	}
	
	public synchronized boolean hasLevel(String levelName) {
		if( this.levelTable.containsKey(levelName) ) {
			return true;
		}
		return false;
	}

	public synchronized CadLevel getLevel(String levelName) {
		CadLevel oResult = null;

		if( this.levelTable.containsKey(levelName) ) {
			oResult = (CadLevel)this.levelTable.get(levelName);
		}
		return oResult;
	}

	public synchronized ArrayList<CadLevel> getAllLevel() {
		ArrayList<CadLevel> lsResult = new ArrayList<CadLevel>();

		Collection<CadLevel> colLevel = this.levelTable.values();
		for(CadLevel oLevel : colLevel) {
			lsResult.add(oLevel);
		}
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		return null;
	}

	@Override
	public void debug(int debugLevel) {
		//TODO:
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
