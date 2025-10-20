/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

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
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(o.getName());
			root.add(node);
		}
	}
	
}
