/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppCadMain.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.cad.CadArc;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.CadPoint;
import br.com.tlmv.aicadxapp.cad.CadRectangle;
import br.com.tlmv.aicadxapp.cad.CadText;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.DocumentTable;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.samples.BasicSample;
import br.com.tlmv.aicadxapp.samples.Box3DSample;
import br.com.tlmv.aicadxapp.samples.Cilinder3DSample;
import br.com.tlmv.aicadxapp.samples.Cone3DSample;
import br.com.tlmv.aicadxapp.samples.DxfSample;
import br.com.tlmv.aicadxapp.samples.DrenagemSample;
import br.com.tlmv.aicadxapp.samples.PipeSample;
import br.com.tlmv.aicadxapp.samples.Sphere3DSample;
import br.com.tlmv.aicadxapp.samples.Torus3DSample;
import br.com.tlmv.aicadxapp.samples.TroncoCone3DSample;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class AppCadMain extends CadObject 
{
//Private Static
	private static AppCadMain gCad;
	
//Public
	private DocumentTable documentTable;
	private String currDocument;
	
//Public

	public AppCadMain() {
		super(AppDefs.OBJTYPE_APPCADMAIN, null);

		AppCadMain.gCad = this; 
		this.init();
	}

	/* Methodes */
	
	public synchronized CadDocumentDef init() {
		this.documentTable = new DocumentTable(); 
		
		CadDocumentDef doc = this.newCadDocumentDef();
		return doc;
	}

	public synchronized CadDocumentDef newCadDocumentDef() {
		CadDocumentDef oNewDoc = this.documentTable.newDocumentDef();
		if(oNewDoc != null) {
			this.currDocument = oNewDoc.getName();
		}
		return oNewDoc;
	}

	public synchronized int closeDocument(CadDocumentDef doc) {
		String docName = doc.getName();
		
		int sz = this.documentTable.getDocumentTableSz();
		if(sz <= 1) return AppDefs.RSERR;
		
		MainPanel panel = MainPanel.getMainPanel();
		int rscode = panel.closeAllTabbedPanel(docName);
		if(rscode == AppDefs.RSOK) {
			this.documentTable.closeDocumentDef(doc.getName()); 
		}
		return AppDefs.RSOK;
	}
	
	@Override
	public void reset() {
		//TODO:
	}

	/* DEBUG */

	@Override
	public String toStr() {
		String str = String.format(
			"CurrDocument: %s; ", 
			Integer.toString(this.documentTable.getDocumentTableSz()) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* LOAD/SAVE */
	
	@Override
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		//TODO:
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		//TODO:
	}

	/* Getters/Setters */

	public ArrayList<CadDocumentDef> getAllCadDocumentDef()
	{
		ArrayList<CadDocumentDef> lsResult = this.documentTable.findAllCadDocumentDef();
		return lsResult;
	}
	
	public CadDocumentDef getCurrDocumentDef()
	{
		CadDocumentDef oCurrDocument = this.documentTable.getDocumentDef(this.currDocument);
		return oCurrDocument;
	}

	public CadBlockDef getCurrBlockDef()
	{
		CadDocumentDef oCurrDocument = this.documentTable.getDocumentDef(this.currDocument);
		CadBlockDef oBlk = oCurrDocument.getCurrBlockDef();
		return oBlk;
	}
	
	public CadLayerDef getCurrLayerDef()
	{
		CadDocumentDef oCurrDocument = this.documentTable.getDocumentDef(this.currDocument);
		CadLayerDef oLayer = oCurrDocument.getCurrLayerDef();
		return oLayer;
	}
	
	public String getCurrDocument() {
		return this.currDocument;
	}

	public void setCurrDocument(String currDocument) {
		String docName = StringUtil.getHead(currDocument, '|');		
		this.currDocument = currDocument;
	}

	public static AppCadMain getCad() {
		return AppCadMain.gCad;
	}

}
