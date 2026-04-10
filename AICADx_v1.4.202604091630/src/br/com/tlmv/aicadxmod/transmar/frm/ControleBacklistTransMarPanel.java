/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ControleBacklistTransMarPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/08/2025
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

package br.com.tlmv.aicadxmod.transmar.frm;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.transmar.cad.CadControleBacklistItemTransMarOData;
import br.com.tlmv.aicadxmod.transmar.cad.CadControleBacklistTransMar;
import br.com.tlmv.aicadxmod.transmar.frm.renderer.ControleBacklistTableCellEditor;
import br.com.tlmv.aicadxmod.transmar.frm.renderer.ControleBacklistTableCellRenderer;
import br.com.tlmv.aicadxmod.transmar.frm.renderer.ControleBacklistTableCellResultEvent;
import br.com.tlmv.aicadxmod.transmar.frm.renderer.ControleBacklistTableCellResultListener;
import br.com.tlmv.aicadxmod.transmar.model.ControleBacklistModel;

public class ControleBacklistTransMarPanel extends BasePanel implements ControleBacklistTableCellResultListener
{
//Private
	private ControleBacklistModel oModel = null;
	
	private Object[][] arrTableData = null;
	
	private CadControleBacklistTransMar oControleBacklist = null;
	private ResultListener resultListener = null;

	//BACKLIST
	private ArrayList<CadControleBacklistItemTransMarOData> lsOffload = null;
	private ArrayList<CadControleBacklistItemTransMarOData> lsBackload = null;
	private ArrayList<CadControleBacklistItemTransMarOData> lsTransbordo = null;

	/* CONTROLS */
	
	//LABELS
	private JLabel lblNome = null;
	private JLabel lblDescricao = null;
	private JLabel lblPesquisar = null;
	private JLabel lblBacklist = null;
	//TEXT
	private JTextField txtNome = null;
	private JTextField txtDescricao = null;
	private JTextField txtPesquisar = null;
	//TABBED_PANE
	private JTabbedPane tabBacklistPanel = null; 
	//CHECKBOX
	private JCheckBox chkShowOffload = null;
	private JCheckBox chkShowBackload = null;
	private JCheckBox chkShowTransbordo = null;
	//BUTTON
	private JButton btnPosicionarCarga = null;
	private JButton btnConfirmarRecebimento = null;
	private JButton btnConfirmarEntrega = null;
	private JButton btnFechar = null;

	/* RESULT_CODE */
	
	private int rscode = AppDefs.RSCODE_CONTROLE_BACKLIST_TRANSMAR_NONE;
	
	/* Methodes */
	
	private void loadAllList(ArrayList<CadControleBacklistItemTransMarOData> lsBacklist)
	{
		this.lsOffload = new ArrayList<CadControleBacklistItemTransMarOData>();
		this.lsBackload = new ArrayList<CadControleBacklistItemTransMarOData>();
		this.lsTransbordo = new ArrayList<CadControleBacklistItemTransMarOData>();
		
		for(CadControleBacklistItemTransMarOData oContentor : lsBacklist) {
			if( oContentor.getCdTipo() == AppDefs.DEF_TMAR_TIPO_OFFLOAD ) {
				this.lsOffload.add(oContentor);
			}
			else if( oContentor.getCdTipo() == AppDefs.DEF_TMAR_TIPO_BACKLOAD ) {
				this.lsBackload.add(oContentor);
			}
			else if( oContentor.getCdTipo() == AppDefs.DEF_TMAR_TIPO_TRANSBORDO ) {
				this.lsTransbordo.add(oContentor);
			}				
		}		
	}

	/* Methodes */
	
	private Object[][] loadAllTableItems(ArrayList<CadControleBacklistItemTransMarOData> lsItem)
	{
		ArrayList<Object[]> lsResult = new ArrayList<Object[]>();

		int nRows = lsItem.size();
		int nCols = AppDefs.ARR_TBLCOL_BACKLIST.length;
		
		for(int i = 0; i < nRows; i++)
		{
			CadControleBacklistItemTransMarOData o = (CadControleBacklistItemTransMarOData)lsItem.get(i);			

			Object[] arr = o.toObjectArray(AppDefs.ARR_TBLCOL_MEMORIA_CALCULO);
			lsResult.add(arr);
		}
		
		this.arrTableData = lsResult.toArray(new Object[nRows][nCols]);
		return this.arrTableData;
	}
	
	private void initCellEditorAndRender(JTable oTbl)
	{
		ControleBacklistTableCellRenderer renderer = new ControleBacklistTableCellRenderer(this);		
		ControleBacklistTableCellEditor editor = new ControleBacklistTableCellEditor(this, this);
		
		int h = oModel.getHdrHeight();
		oTbl.setRowHeight(h);
			
		oTbl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        TableColumnModel model = oTbl.getColumnModel();
		int sz = model.getColumnCount();
		for(int i = 0; i < sz; i++) {
			ColunaTabelaVO oHeader = (ColunaTabelaVO)oModel.getHeaderAt(i);

			String titulo = oHeader.getTitulo();
			int w = oHeader.getWidth();
			
			TableColumn oCol = model.getColumn(i);
			oCol.setCellRenderer(renderer);
			oCol.setCellEditor(editor);

			oCol.setHeaderValue(titulo);
			oCol.setMinWidth(w);
			oCol.setMaxWidth(w);
			oCol.setWidth(w);
		}
	}
	
	private void initMemoriaCalculo(String nome, String descricao, CadMemoriaCalculoDrenagem oMemoriaCalculo) 
	{
		Date dataAtualHora = new Date();
		
		Date dataAtual = new Date(dataAtualHora.getYear(), dataAtualHora.getMonth(), dataAtualHora.getDate());
		
		String strNomeProjeto = oMemoriaCalculo.getNomeProjeto();
		Date dtDataEmissao = dataAtual;
	    int iCodigoLocalMedicao = oMemoriaCalculo.getCodigoLocalMedicao();
		String strPluviografo = oMemoriaCalculo.getPluviografo();
		double dCoefManning = oMemoriaCalculo.getCoefManning();
		double dPeriodoRecorrencia = oMemoriaCalculo.getPeriodoRecorrencia();
	}
	
	private void initForm()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String uuid = UuidUtil.generateUUID();
		
		String strNome = String.format(AppDefs.DEF_DEFAULT_DRENAGEM_NOMEMEMORIACALCULO, uuid);
		String strDescricao = AppDefs.DEF_DEFAULT_DRENAGEM_DESCRICAOMEMORIACALCULO;
		
		int w = Math.max(this.getWidth(), AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_WIDTH);
		int h = Math.max(this.getHeight(), AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_HEIGHT);		
		
		/*
		 * MAIN_PANEL
		 */
		this.setLayout(null);		

		Insets insets = this.getInsets();

		int x = insets.left + AppDefs.SPACE_W10;
		int y = insets.top + AppDefs.SPACE_H10;
		
		/*
		 * TOP_PANEL
		 */

		//FORM_ITEM_1: NOME
		this.lblNome = FormControlUtil.newLabel("Nome:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblNome);

		this.txtNome = FormControlUtil.newTextField(strNome, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtNome);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);

		//FORM_ITEM_2: DESCRICAO
		this.lblDescricao = FormControlUtil.newLabel("Descricao:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblDescricao);

		this.txtDescricao = FormControlUtil.newTextField(strDescricao, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDescricao);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
				
		int h_top = y;

		this.resizeForm();
	}
	
	private void resizeForm()
	{
		int w = Math.max(this.getWidth(), AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_WIDTH);
		int h = Math.max(this.getHeight(), AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_HEIGHT);		
		
		/*
		 * MAIN_PANEL
		 */
		this.setLayout(null);		

		Insets insets = this.getInsets();

		int x = insets.left + AppDefs.SPACE_W10;
		int y = insets.top + AppDefs.SPACE_H10;
		
		/*
		 * TOP_PANEL
		 */

		//FORM_ITEM_1: NOME
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);

		//FORM_ITEM_2: DESCRICAO
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
				
	}
	
//Public 
	
	public ControleBacklistTransMarPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(CadControleBacklistTransMar oControleBacklist)
	{
		String uuid = UuidUtil.generateUUID();

		String strNome = String.format(AppDefs.DEF_DEFAULT_DRENAGEM_NOMEMEMORIACALCULO, uuid);
		
		String strDescricao = AppDefs.DEF_DEFAULT_DRENAGEM_DESCRICAOMEMORIACALCULO;
		
		this.oControleBacklist = oControleBacklist;
		
		this.addComponentListener(this);
		
		//initControleBacklist(strNome, strDescricao, this.oControleBacklist);		

		initForm();
	}
	
	/* Methodes */
	
	public boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		String strNome = this.txtNome.getText();
		String strDescricao = this.txtDescricao.getText();
		
		if( "".equals(strNome) )
			errmsg += "Nome; ";
		
		if( "".equals(strDescricao) )
			errmsg += "Descricao; ";
				
		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}
		
		//MemoriaCalculo
		//
		this.oControleBacklist.setNome(strNome);
		this.oControleBacklist.setDescricao(strDescricao);
		
		return true;
	}
    
    /* Event Handlers */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionFechar(ActionEvent e) 
	{
		if( validateForm() ) {
			this.rscode = AppDefs.RSCODE_CONTROLE_BACKLIST_TRANSMAR_FECHAR;
			this.actionResultListener(new ResultEvent(this.rscode, null));

			this.getParentFrame().dispose();
		}
	}

	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_CONTROLE_BACKLIST_TRANSMAR_FECHAR) {
			doActionFechar(e);
		}
	}
	
	@Override
	public void actionControleBacklistTableCellResultListener(ControleBacklistTableCellResultEvent e) 
	{
		int rowNum = e.getRownum();
		int colNum = e.getColnum();
		Object newVal = e.getNewval();
		//Object oldVal = e.getOldval();
		
		ColunaTabelaVO oCol = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO[colNum];
		String colName = oCol.getColumnName();

		ArrayList<CadControleBacklistItemTransMarOData> lsItem = this.oControleBacklist.getLsItem();
		CadControleBacklistItemTransMarOData o = lsItem.get(rowNum);
		o.setValueByName(colName, newVal);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) { }

	@Override
	public void itemStateChanged(ItemEvent e) { }

	@Override
	public void actionResultListener(ResultEvent e) { }

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) { }

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) { }

	@Override
	public void textValueChanged(TextEvent e) { }
	
	/* COMPONENT_EVENTS */

	@Override
	public void componentResized(ComponentEvent e) {
		Component c = e.getComponent();

		double wScr = c.getWidth();
		double hScr = c.getHeight();

		if( (wScr < AppDefs.MATHPREC_MIN) && (hScr < AppDefs.MATHPREC_MIN) )
			return;

		this.resizeForm();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		/* nothing todo! */
	}

	@Override
	public void componentShown(ComponentEvent e) {
		/* nothing todo! */
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		/* nothing todo! */
	}

	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) { }

	/* Getters/Setters */

	public int getRSCode() {
		return rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}

	public CadControleBacklistTransMar getControleBacklist() {
		return oControleBacklist;
	}

	public void setControleBacklist(CadControleBacklistTransMar oControleBacklist) {
		this.oControleBacklist = oControleBacklist;
	}

	public ControleBacklistModel getControleBacklistModel() {
		return oModel;
	}

	public void setControleBacklistModel(ControleBacklistModel oModel) {
		this.oModel = oModel;
	}
	
}
