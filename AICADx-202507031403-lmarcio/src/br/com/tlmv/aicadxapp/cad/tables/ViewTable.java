/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ViewTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
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
	
	public ViewTable() {
		super(AppDefs.OBJTYPE_VIEW_TABLE);
		this.init();
	}
	
	private void init() {
		this.viewTable = new Hashtable<String,CompView>();
	}
	
	@Override
	public void reset() {
		//TODO:
	}

	/* Methodes */
	
	public synchronized boolean hasView(String name) {
		if( this.viewTable.containsKey(name) ) {
			return true;
		}		
		return false;
	}
	
	public synchronized CompView newPlanView(String name, int levelId) {		
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CompView oResult = null;
		if( !this.viewTable.containsKey(name) ) {
			oResult = new CompModelPlanView(name, AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL, MainPanel.getParentFrame(), doc);	
			this.viewTable.put(name, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView newSectionView(String name, GeomPoint2d ptI, GeomPoint2d ptF) {
		return null;
	}
	
	public synchronized CompView newElevationView(String name, GeomPoint2d ptOrigem, GeomVector2d vDir) {
		return null;
	}
	
	public synchronized CompView newDetailView(String name) {
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CompView oResult = null;
		if( !this.viewTable.containsKey(name) ) {
			oResult = new CompPerfilDrenagemView(name, AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL, MainPanel.getParentFrame(), doc);	
			this.viewTable.put(name, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView new3DView(String name) {
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CompView oResult = null;
		if( !this.viewTable.containsKey(name) ) {
			oResult = new CompModel3DView(name, AppDefs.DOCVIEW_GRP_3DVIEWS_VAL, MainPanel.getParentFrame(), doc);	
			this.viewTable.put(name, oResult);
		}
		return oResult;
	}

	public synchronized CompView newDrenageAreaView(String name, int levelId) {		
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		CompView oResult = null;
		if( !this.viewTable.containsKey(name) ) {
			oResult = new CompAreaContribuicaoDrenagemView(name, AppDefs.DOCVIEW_GRP_PLANVIEWS_VAL, MainPanel.getParentFrame(), doc);	
			this.viewTable.put(name, oResult);
		}
		return oResult;
	}

	public synchronized CompView getView(String name) {
		CompView oResult = null;

		if( this.viewTable.containsKey(name) ) {
			oResult = (CompView)this.viewTable.get(name);
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

	}
	
}
