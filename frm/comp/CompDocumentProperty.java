/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompDocumentProperty.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.comp;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.model.DocumentViewModelEx2;
import br.com.tlmv.aicadxapp.frm.model.ObjectPropertyModel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class CompDocumentProperty extends JPanel implements ActionListener, TreeSelectionListener
{
//Private Static
	private static final long serialVersionUID = -3412810584111194621L;
	
//Private
	private AppCadMain cad = null;
			
	private ArrayList<ItemDataVO> lsItemData;
	private ObjectPropertyModel objectPropertyModel;
	
	private CadDocumentDef documentView;
	private DocumentViewModelEx2 documentViewModel;
	
	//PANEL_COMPONENTS
	//
	private JLabel lblObjectProperty;
	private JTable tblObjectProperty;

	private JLabel lblDocumentView;
	private JTree treDocumentView;
	
	private ResultListener listener = null;
	
//Public
	
	public CompDocumentProperty(ResultListener listener)
	{
		this.init(listener);
	}

	/* Methodes */
	
	public void init(ResultListener listener)
	{
		this.listener = listener;
		
		this.cad = AppCadMain.getCad();

		this.documentView = this.cad.getCurrDocumentDef();

		this.lsItemData = this.documentView.toPropertyList();	
		this.objectPropertyModel = new ObjectPropertyModel(AppDefs.HDR_TBLPROPERTYEDITOR, this.lsItemData);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Views"); 
		this.documentViewModel = new DocumentViewModelEx2(root, this.documentView);
		
		Dimension d = new Dimension(200, 768);
		
		this.setSize(d);
		this.setPreferredSize(this.getSize());
		
		this.initForm();
	}
	
	public void initForm()
	{
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		this.setLayout(layout);

		//PANEL_1
		//
		JPanel panel1 = new JPanel();
		
		BorderLayout layout1 = new BorderLayout();
		panel1.setLayout(layout1);
		
		this.lblObjectProperty = FormControlUtil.newLabelEx(panel1, "Object Properties:", true, BorderLayout.NORTH);
		this.lblObjectProperty.setOpaque(true);
		this.lblObjectProperty.setBackground(Color.black);
		this.lblObjectProperty.setForeground(Color.white);
		
		this.tblObjectProperty = FormControlUtil.newTable(panel1, this.objectPropertyModel, true, BorderLayout.CENTER);

		this.add(panel1);
		
		//PANEL_2
		//
		JPanel panel2 = new JPanel();
		
		BorderLayout layout2 = new BorderLayout();
		panel2.setLayout(layout2);
		
		this.lblDocumentView = FormControlUtil.newLabelEx(panel2, "Documents:", true, BorderLayout.NORTH);
		this.lblDocumentView.setOpaque(true);
		this.lblDocumentView.setBackground(Color.black);
		this.lblDocumentView.setForeground(Color.white);
		
		this.treDocumentView = FormControlUtil.newTreeEx(panel2, this.documentViewModel, true, BorderLayout.CENTER);
		this.treDocumentView.addTreeSelectionListener(this);
		
		this.add(panel2);
	}
	
	/* UPDATE */
	
	public synchronized void updateObjectProperty(ArrayList<ItemDataVO> lsItemData)
	{
		this.lsItemData = lsItemData;
		
		this.objectPropertyModel = new ObjectPropertyModel(AppDefs.HDR_TBLPROPERTYEDITOR, this.lsItemData);
		
		this.tblObjectProperty.setModel(this.objectPropertyModel);
	}
	
	public synchronized void updateDocumentView(CadDocumentDef doc)
	{
		this.documentView = doc;
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Views"); 
		this.documentViewModel = new DocumentViewModelEx2(root, this.documentView);

		this.treDocumentView.removeAll();
		this.treDocumentView.setModel(this.documentViewModel);
	}
	
	/* EVENTS */

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		//TODO:
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
//		int[] arrSelc = this.treDocumentView.getSelectionRows();
//		if(arrSelc.length > 0) {
//			int pos = arrSelc[0] - 1;
//			if(pos >= 0) {
//				CadDocumentDef doc = this.lsDocumentView.get(pos);
//
//				this.listener.actionResultListener(new ResultEvent(AppDefs.RSCODE_COMPDOCUMENTPROPERTY_DOCUMENTO_SELECIONADO, doc.getName()));
//			}
//		}
	}
	
	/* Getters/Setters */

	public ArrayList<ItemDataVO> getLsItemData() {
		return lsItemData;
	}

	public void setLsItemData(ArrayList<ItemDataVO> lsItemData) {
		this.lsItemData = lsItemData;
	}

	public ObjectPropertyModel getModel() {
		return this.objectPropertyModel;
	}

	public void setModel(ObjectPropertyModel model) {
		this.objectPropertyModel = model;
	}

	public ResultListener getListener() {
		return listener;
	}
	
}
