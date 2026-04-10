/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DocumentViewModel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/04/2025
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

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class DocumentViewModel extends DefaultTreeModel 
{
//Private

	private static final long serialVersionUID = 202504041459L;

	private ArrayList<CadDocumentDef> lsDocumentView = null;
	
	private DefaultMutableTreeNode root = null;
	
//Public
	
	public DocumentViewModel(DefaultMutableTreeNode root, ArrayList<CadDocumentDef> lsDocumentView)
	{
		super(root);
		
		this.init(root, lsDocumentView);		
	}
	
	/* Methodes */
	
	public void init(DefaultMutableTreeNode root, ArrayList<CadDocumentDef> lsDocumentView)
	{
		this.lsDocumentView = lsDocumentView;
		
		this.root = root;
		
		int sz = this.lsDocumentView.size();
		for(int i = 0; i < sz; i++) {
			CadDocumentDef o = this.lsDocumentView.get(i);
			
			ProjectRepoVO projectRepo = o.getProjectRepo();
			String name = projectRepo.getName();
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
			root.add(node);
		}
	}
	
}
