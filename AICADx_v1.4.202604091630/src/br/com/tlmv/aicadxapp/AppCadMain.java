/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp;

import java.util.ArrayList;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.tables.DocumentTable;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class AppCadMain extends CadObject 
{
//Private Static
	private static AppCadMain gCad;
	
//Public
	private DocumentTable documentTable;
	
//Public

	public AppCadMain() {
		super(AppDefs.OBJTYPE_APPCADMAIN, null, null);

		AppCadMain.gCad = this; 
		this.init();
	}

	/* Methodes */
	
	public synchronized CadDocumentDef init() {
		this.documentTable = new DocumentTable(null); 
		
		CadDocumentDef doc = this.newCadDocumentDef();
		return doc;
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}

	public synchronized CadDocumentDef newCadDocumentDef() {
		CadDocumentDef oNewDoc = this.documentTable.newDocumentDef();
		if(oNewDoc != null) {
			ProjectRepoVO projectRepo = oNewDoc.getProjectRepo();
		}
		return oNewDoc;
	}

	public synchronized int closeDocument(CadDocumentDef doc) {
		ProjectRepoVO projectRepo = doc.getProjectRepo();
		String docName = projectRepo.getName();
		
		int sz = this.documentTable.getDocumentTableSz();
		if(sz <= 1) return AppDefs.RSERR;
		
		MainPanel panel = MainPanel.getMainPanel();
		int rscode = panel.closeAllTabbedPanel(docName);
		if(rscode == AppDefs.RSOK) {
			this.documentTable.closeDocumentDef(docName); 
		}
		return AppDefs.RSOK;
	}
	
	@Override
	public void reset() {
		this.documentTable.reset(); 
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
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	/* Getters/Setters */

	public CadDocumentDef getFirstCadDocumentDef()
	{
		CadDocumentDef oResult = this.documentTable.findFirstCadDocumentDef();
		return oResult;
	}

	public ArrayList<CadDocumentDef> getAllCadDocumentDef()
	{
		ArrayList<CadDocumentDef> lsResult = this.documentTable.findAllCadDocumentDef();
		return lsResult;
	}
	
	public CadDocumentDef getDocumentDefByDocName(String docName)
	{
		CadDocumentDef oCurrDocument = this.documentTable.getDocumentDef(docName);
		return oCurrDocument;
	}

	public CadBlockDef getBlockDefByDocName(String docName)
	{
		CadBlockDef oBlk = null;
		
		CadDocumentDef oCurrDocument = getDocumentDefByDocName(docName);
		if(oCurrDocument != null) {
			oBlk = oCurrDocument.getCurrBlockDef();
		}
		return oBlk;
	}
	
	public CadLayerDef getCurrLayerDefByDocName(String docName)
	{
		CadLayerDef oLayer = null;
		
		CadDocumentDef oCurrDocument = getDocumentDefByDocName(docName);
		if(oCurrDocument != null) {
			oLayer = oCurrDocument.getCurrLayerDef();
		}
		return oLayer;
	}

	public static AppCadMain getCad() {
		return AppCadMain.gCad;
	}

}
