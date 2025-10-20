/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SchemaRecordModel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.model;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.dao.record.SchemaRecord;
import br.com.tlmv.aicadxapp.frm.view.CompView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.GroupItemDataVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class SchemaRecordModel extends DefaultListModel 
{
//Private

	private static final long serialVersionUID = 202504041459L;
	
	private ArrayList<SchemaRecord> lsSchema = null;
	
	/* Methodes */

	private void loadData()
	{
		int szLsSchema = this.lsSchema.size();
		for(int i = 0; i < szLsSchema; i++) {
			SchemaRecord oSchema = this.lsSchema.get(i);
			this.addElement(oSchema.getSchemaName());
		}
	}	
	
//Public
	
	public SchemaRecordModel(ArrayList<SchemaRecord> lsSchema)
	{
		this.init(lsSchema);		
	}
	
	/* Methodes */
	
	public void init(ArrayList<SchemaRecord> lsSchema)
	{
		this.lsSchema = lsSchema;		
		this.loadData();
	}

	/* Getters/Setters */

	public SchemaRecord getSchemaRecord(int pos)
	{
		SchemaRecord oSchema = this.lsSchema.get(pos);
		return oSchema;
	}
		
}
