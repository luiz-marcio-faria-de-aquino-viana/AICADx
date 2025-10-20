/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.view.CompModel3DView;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.frm.view.CompAreaContribuicaoDrenagemView;
import br.com.tlmv.aicadxmod.drenagem.frm.view.CompPerfilDrenagemView;

public class ViewTable extends CadObject 
{
//Private
	private Hashtable<String,CompView> viewTable;
	
//Public
	
	public ViewTable() {
		super(AppDefs.OBJTYPE_VIEW_TABLE, null);
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
	
	public synchronized boolean hasView(String fullName) {
		if( this.viewTable.containsKey(fullName) ) {
			return true;
		}		
		return false;
	}
	
	public synchronized CompView newPlanView(String fullName, int levelId) {		
		CompView oResult = null;

		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		//String name = StringUtil.getHead(fullName, '|');		
		if( !this.viewTable.containsKey(fullName) ) {
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

		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		//String name = StringUtil.getHead(fullName, '|');		
		if( !this.viewTable.containsKey(fullName) ) {
			oResult = new CompPerfilDrenagemView(fullName, AppDefs.DOCVIEW_GRP_DETAILVIEWS_VAL, MainFrame.getMainFrame(), doc);	
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView new3DView(String fullName) {
		CompView oResult = null;

		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		//String name = StringUtil.getHead(fullName, '|');		
		if( !this.viewTable.containsKey(fullName) ) {
			oResult = new CompModel3DView(fullName, AppDefs.DOCVIEW_GRP_3DVIEWS_VAL, MainFrame.getMainFrame(), doc);	
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}
	
	public synchronized CompView new3DView(String fullName, GeomPoint2d ptMin, GeomPoint2d ptMax) {
		CompModel3DView oResult = null;

		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		//String name = StringUtil.getHead(fullName, '|');		
		if( !this.viewTable.containsKey(fullName) ) {
			oResult = new CompModel3DView(fullName, AppDefs.DOCVIEW_GRP_3DVIEWS_VAL, MainFrame.getMainFrame(), doc);
			oResult.setPlanArea(ptMin, ptMax);
			
			this.viewTable.put(fullName, oResult);
		}
		return oResult;
	}

	public synchronized CompView newDrenageAreaView(String fullName, int levelId) {		
		CompView oResult = null;

		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
		//String name = StringUtil.getHead(fullName, '|');		
		if( !this.viewTable.containsKey(fullName) ) {
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

	}
	
}
