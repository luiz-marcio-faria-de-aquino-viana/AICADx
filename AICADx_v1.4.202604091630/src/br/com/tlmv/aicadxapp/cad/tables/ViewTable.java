/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ViewTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.view.CompModel3DView;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxmod.drenagem.frm.view.CompAreaContribuicaoDrenagemView;
import br.com.tlmv.aicadxmod.drenagem.frm.view.CompPerfilDrenagemView;

public class ViewTable extends CadObject 
{
//Private
	private Hashtable<String,CompView> viewTable;
	
//Public
	
	public ViewTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_VIEW_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.viewTable = new Hashtable<String,CompView>();
	}

	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,CompView> newViewTable = new Hashtable<String,CompView>();
		
		Collection colView = this.viewTable.values();
		Iterator iterView = colView.iterator();
		while( iterView.hasNext() ) {
			CompView oView = (CompView)iterView.next();

			String viewName = oView.getName();
			newViewTable.put(viewName, oView);
		}
		this.viewTable = newViewTable;
	}

	/* Methodes */
	
	public synchronized boolean hasView(String fullName) {
		if( this.viewTable.containsKey(fullName) ) {
			return true;
		}		
		return false;
	}
	
	public synchronized CompView newPlanView(String fullName, int levelId) {		
		CompView oResult = null;
		
		if( !this.viewTable.containsKey(fullName) ) {
			CadDocumentDef doc = this.getDocument();

			oResult = new CompModelPlanView(fullName, AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL, MainFrame.getMainFrame(), doc);	
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView newSectionView(String fullName, GeomPoint2d ptI, GeomPoint2d ptF) {
		return null;
	}
	
	public synchronized CompView newElevationView(String fullName, GeomPoint2d ptOrigem, GeomVector2d vDir) {
		return null;
	}
	
	public synchronized CompView newDetailView(String fullName) {
		CompView oResult = null;

		if( !this.viewTable.containsKey(fullName) ) {
			CadDocumentDef doc = this.getDocument();

			oResult = new CompPerfilDrenagemView(fullName, AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL, MainFrame.getMainFrame(), doc);	
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView new3DView(String fullName) {
		CompView oResult = null;

		if( !this.viewTable.containsKey(fullName) ) {
			CadDocumentDef doc = this.getDocument();

			oResult = new CompModel3DView(fullName, AppDefs.DOCVIEW_GRP_3DVIEWS_VAL, MainFrame.getMainFrame(), doc);	
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView new3DView(String fullName, GeomPoint2d ptMin, GeomPoint2d ptMax) {
		CompModel3DView oResult = null;

		if( !this.viewTable.containsKey(fullName) ) {
			CadDocumentDef doc = this.getDocument();

			oResult = new CompModel3DView(fullName, AppDefs.DOCVIEW_GRP_3DVIEWS_VAL, MainFrame.getMainFrame(), doc);
			oResult.setPlanArea(ptMin, ptMax);
			
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}

	public synchronized CompView newDrenageAreaView(String fullName, int levelId) {		
		CompView oResult = null;

		if( !this.viewTable.containsKey(fullName) ) {
			CadDocumentDef doc = this.getDocument();

			oResult = new CompAreaContribuicaoDrenagemView(fullName, AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL, MainFrame.getMainFrame(), doc);	
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}
	
	public synchronized void closeView(String fullName) {
		//String name = StringUtil.getHead(fullName, '|');		
		if( this.viewTable.contains(fullName) )
			this.viewTable.remove(fullName);
	}

	public synchronized CompView getView(String fullName) {
		CompView oResult = null;

		//String name = StringUtil.getHead(fullName, '|');		
		if( this.viewTable.containsKey(fullName) ) {
			oResult = (CompView)this.viewTable.get(fullName);
		}		
		return oResult;
	}

	public synchronized ArrayList<CompView> getAllView() {
		ArrayList<CompView> lsResult = new ArrayList<CompView>();

		Collection<CompView> colView = this.viewTable.values();
		for(CompView oView : colView) {
			lsResult.add(oView);
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
		return false;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}
	
}
