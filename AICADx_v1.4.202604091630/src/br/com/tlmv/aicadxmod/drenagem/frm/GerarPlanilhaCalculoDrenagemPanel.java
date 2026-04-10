/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GerarPlanilhaCalculoDrenagemPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/04/2025
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

package br.com.tlmv.aicadxmod.drenagem.frm;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.export.IExportData;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ODTUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.export.MemoriaCalculoExport;
import br.com.tlmv.aicadxmod.drenagem.frm.renderer.MemoriaCalculoTableCellEditor;
import br.com.tlmv.aicadxmod.drenagem.frm.renderer.MemoriaCalculoTableCellRenderer;
import br.com.tlmv.aicadxmod.drenagem.frm.renderer.MemoriaCalculoTableCellResultEvent;
import br.com.tlmv.aicadxmod.drenagem.frm.renderer.MemoriaCalculoTableCellResultListener;
import br.com.tlmv.aicadxmod.drenagem.model.MemoriaCalculoModel;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;

public class GerarPlanilhaCalculoDrenagemPanel extends BasePanel implements MemoriaCalculoTableCellResultListener
{
//Private
	private MemoriaCalculoModel oModel = null;
	
	private Object[][] arrTableData = null;
	
	private CadMemoriaCalculoDrenagem oMemoriaCalculo = null;

	private int rscode = AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_NONE;
		
	//LABELS
	//
	private JLabel lblNome = null;
	private JLabel lblDescricao = null;
	private JLabel lblNomeProjeto = null;
	private JLabel lblDataEmissao = null;
	private JLabel lblPluviografo = null;
	private JLabel lblCoefManning = null;
	private JLabel lblPeriodoRecorrencia = null;
	//
	private JLabel lblTableData = null;

	//CONTROLS
	//
	private JTextField txtNome = null;
	private JTextField txtDescricao = null;
	private JTextField txtNomeProjeto = null;
	private JTextField txtDataEmissao = null;
	private JTextField txtPluviografo = null;
	private JTextField txtCoefManning = null;
	private JTextField txtPeriodoRecorrencia = null;
	//
	private JCheckBox chkMinimized = null;
	//
	private JTable tblTableData = null;
	//
	private JScrollPane panTableScroll = null;
	
	//BUTTON
	//
	private JButton btnExportar = null;
	private JButton btnReCalcular = null;
	private JButton btnOk = null;
	private JButton btnCancelar = null;

	/* Methodes */
	
	private Object[][] loadAllTableItems()
	{
		ArrayList<Object[]> lsResult = new ArrayList<Object[]>();
		
		ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem = this.oMemoriaCalculo.getLsItem();

		int nRows = lsItem.size();
		int nCols = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO.length;
		
		for(int i = 0; i < nRows; i++)
		{
			CadMemoriaCalculoItemDrenagemOData o = (CadMemoriaCalculoItemDrenagemOData)lsItem.get(i);			

			Object[] arr = o.toObjectArray(AppDefs.ARR_TBLCOL_MEMORIA_CALCULO);
			lsResult.add(arr);
		}
		
		this.arrTableData = lsResult.toArray(new Object[nRows][nCols]);
		return this.arrTableData;
	}
	
	private void initCellEditorAndRender(JTable oTbl)
	{
		MemoriaCalculoTableCellRenderer renderer = new MemoriaCalculoTableCellRenderer(this);		
		MemoriaCalculoTableCellEditor editor = new MemoriaCalculoTableCellEditor(this, this);
		
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
	
	private void reInitMemoriaCalculo() 
	{
		MainPanel panel = MainPanel.getMainPanel();
		CadDocumentDef doc = panel.getCurrDocumentDef();

		DrenagemCalc calc = new DrenagemCalc();
		calc.reCalculaRedeDrenagem(doc, this.oMemoriaCalculo, true);
		
		this.removeAll();
		this.initForm();
	}
	
	private void initForm()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String uuid = UuidUtil.generateUUID();
		
		String strNome = String.format(AppDefs.DEF_DEFAULT_DRENAGEM_NOMEMEMORIACALCULO, uuid);
		String strDescricao = AppDefs.DEF_DEFAULT_DRENAGEM_DESCRICAOMEMORIACALCULO;
		String strNomeProjeto = this.oMemoriaCalculo.getNomeProjeto();
		String strDataEmissao = df.format(this.oMemoriaCalculo.getDataEmissao());
		String strPluviografo = this.oMemoriaCalculo.getPluviografo();
		String strCoefManning = nf6.format(this.oMemoriaCalculo.getCoefManning());
		String strPeriodoRecorrencia = nf0.format(this.oMemoriaCalculo.getPeriodoRecorrencia());
		
		int w = Math.max(this.getWidth(), AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_WIDTH);
		int h = Math.max(this.getHeight(), AppDefs.GERAR_PLANILHA_CALCULO_DRENAGEM_FRAME_HEIGHT);		
		
		this.oModel = new MemoriaCalculoModel(AppDefs.ARR_TBLCOL_MEMORIA_CALCULO, this.oMemoriaCalculo.getLsItem());
		
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
				
		//FORM_ITEM_3: NOME_PROJETO
		this.lblNomeProjeto = FormControlUtil.newLabel("Projeto:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblNomeProjeto);

		this.txtNomeProjeto = FormControlUtil.newTextField(strNomeProjeto, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.add(this.txtNomeProjeto);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		//FORM_ITEM_4: PLUVIOGRAFO (LOCAL MEDICAO)
		this.lblPluviografo = FormControlUtil.newLabel("Pluviografo:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblPluviografo);

		this.txtPluviografo = FormControlUtil.newTextField(strPluviografo, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.add(this.txtPluviografo);
		
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);

		//FORM_ITEM_5: DATA_EMISSAO
		this.lblDataEmissao = FormControlUtil.newLabel("Data de emissao:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblDataEmissao);
		
		this.txtDataEmissao = FormControlUtil.newTextField(strDataEmissao, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.add(this.txtDataEmissao);
		
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);

		//FORM_ITEM_6: COEF_MANNING
		this.lblCoefManning = FormControlUtil.newLabel("Coef. de Manning:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblCoefManning);
		
		this.txtCoefManning = FormControlUtil.newTextField(strCoefManning, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtCoefManning);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		//FORM_ITEM_7: PERIODO_RECORRENCIA
		this.lblPeriodoRecorrencia = FormControlUtil.newLabel("Periodo de recorrencia:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblPeriodoRecorrencia);
		
		this.txtPeriodoRecorrencia = FormControlUtil.newTextField(strPeriodoRecorrencia, x + AppDefs.LABEL_W200, y, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtPeriodoRecorrencia);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		//FORM_ITEM_8: PERIODO_RECORRENCIA
		boolean bMinimized = oMemoriaCalculo.isMinimized();
		this.chkMinimized = FormControlUtil.newCheckBox(bMinimized, "Apresenta tabela minimizada.", x, y, AppDefs.LABEL_W600, AppDefs.LABEL_H20, true, true);
		this.chkMinimized.setActionCommand(Integer.toString(AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_MINIMIZED));
		this.chkMinimized.addActionListener(this);
		this.add(this.chkMinimized);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		int h_top = y;
		
		/*
		 * CENTER_PANEL
		 */
		w = w - (AppDefs.SPACE_W10 + AppDefs.SPACE_W10);
		h = h - (AppDefs.SPACE_H30 + h_top + AppDefs.SPACE_W10 + AppDefs.LABEL_H20 + AppDefs.SPACE_W10 + AppDefs.SPACE_W10 + AppDefs.BUTTON_H20 + AppDefs.SPACE_W10);
		
		this.arrTableData = this.loadAllTableItems();
		
		//FORM_ITEM_9: TABELA_DADOS
		//
		this.lblTableData = FormControlUtil.newLabel("Memoria de calculo:", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblTableData);

		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		this.tblTableData = FormControlUtil.newBasicTable(AppDefs.ARR_TBLCOL_MEMORIA_CALCULO, this.arrTableData, x, y, w, h, true);
		this.panTableScroll = FormControlUtil.newScrollPane(this, this.tblTableData, x, y, w, h_top, true);		

		this.initCellEditorAndRender(this.tblTableData);

		this.tblTableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblTableData.setAutoscrolls(true);

		x = insets.left + AppDefs.SPACE_W10;
		y = y + (h + AppDefs.SPACE_H10);

		/*
		 * BOTTOM_PANEL
		 */
		this.btnExportar = FormControlUtil.newButton("Exportar", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_EXPORTAR, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnExportar);
		x = w - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnCancelar = FormControlUtil.newButton("Cancelar", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_CANCELAR, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnCancelar);
		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnOk = FormControlUtil.newButton("Ok", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_OK, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnOk);
		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnReCalcular = FormControlUtil.newButton("Calcular", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_RECALCULAR, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnReCalcular);

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
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		int h_top = y;
		
		/*
		 * CENTER_PANEL
		 */
		w = w - (AppDefs.SPACE_W10 + AppDefs.SPACE_W10);
		h = h - (AppDefs.SPACE_H30 + h_top + AppDefs.SPACE_W10 + AppDefs.LABEL_H20 + AppDefs.SPACE_W10 + AppDefs.SPACE_W10 + AppDefs.BUTTON_H20 + AppDefs.SPACE_W10);
		
		//FORM_ITEM_9: TABELA_DADOS
		//
		y = y + (AppDefs.TEXT_H20 + AppDefs.SPACE_H10);
		
		this.tblTableData.setBounds(x, y, w, h);
		this.panTableScroll.setBounds(x, y, w, h);
		
		y = y + (h + AppDefs.SPACE_H10);

		/*
		 * BOTTOM_PANEL
		 */
		
		this.btnExportar.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);
		
		x = w - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnCancelar.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);

		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnOk.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);
		
		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnReCalcular.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);
	}
		
//Public 
	
	public GerarPlanilhaCalculoDrenagemPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(CadMemoriaCalculoDrenagem oMemoriaCalculo)
	{
		String uuid = UuidUtil.generateUUID();

		String strNome = String.format(AppDefs.DEF_DEFAULT_DRENAGEM_NOMEMEMORIACALCULO, uuid);
		
		String strDescricao = AppDefs.DEF_DEFAULT_DRENAGEM_DESCRICAOMEMORIACALCULO;
		
		this.oMemoriaCalculo = oMemoriaCalculo;
		
		this.addComponentListener(this);
		
		initMemoriaCalculo(strNome, strDescricao, this.oMemoriaCalculo);		

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
		String strCoefManning = this.txtCoefManning.getText();
		String strPeriodoRecorrencia = this.txtPeriodoRecorrencia.getText();
		
		if( "".equals(strNome) )
			errmsg += "Nome; ";
		
		if( "".equals(strDescricao) )
			errmsg += "Descricao; ";
		
		if( "".equals(strCoefManning) )
			errmsg += "Coef.Manning; ";
		
		if( "".equals(strPeriodoRecorrencia) )
			errmsg += "Periodo Recorrencia; ";
				
		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}
		
		double dCoefManning = StringUtil.safeDbl(nf6, strCoefManning);
		if(dCoefManning <= AppDefs.MATHPREC_MIN) {
			errmsg = String.format("Valor do Coeficiente de Manning inferior a %s.", dCoefManning);			
			AppError.showErrorBox(this.getParentFrame(), "ERR: Valor invalido.", errmsg, this.getClass());
			return false;
		}
	
		double dPeriodoRecorrencia = StringUtil.safeDbl(nf0, strPeriodoRecorrencia);
		if(dPeriodoRecorrencia < 1.0) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Valor invalido.", "Periodo de recorrencia inferior a 1 ano.", this.getClass());
			return false;
		}
		
		//MemoriaCalculo
		//
		this.oMemoriaCalculo.setNome(strNome);
		this.oMemoriaCalculo.setDescricao(strDescricao);
		this.oMemoriaCalculo.setCoefManning(dCoefManning);
		this.oMemoriaCalculo.setPeriodoRecorrencia(dPeriodoRecorrencia);
		
		return true;
	}
    
    /* Event Handlers */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionOk(ActionEvent e) 
	{
		if( validateForm() ) {
			DrenagemCalc calc = new DrenagemCalc();

			MainPanel panel = MainPanel.getMainPanel();
			CadDocumentDef doc = panel.getCurrDocumentDef();

			ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem = this.oMemoriaCalculo.getLsItem();
			calc.doRemoveRedeDrenagem(doc);
			calc.doUpdateCaixaInspecao(lsItem, doc);
			calc.doCreateRedeDrenagem(this.oMemoriaCalculo, doc);
			calc.doCreatePipeFromRaloToRedeDrenagem(doc);

			this.rscode = AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_OK;
			this.actionResultListener(new ResultEvent(this.rscode, null));

			this.getParentFrame().dispose();
		}
	}
	
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_CANCELAR;
		this.actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
	public void doActionReCalcular(ActionEvent e) 
	{
		this.reInitMemoriaCalculo();
	}
	
	public void doActionExportar(ActionEvent e) 
	{
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
	
		String templateFile = ctx.getTemplateFileMemoriaCalculo();
		
		String outputFile = FileUtil.generateFileName(ctx.getOutputDir()) + "." + AppDefs.EXT_XLS;
		
		IExportData oExport = new MemoriaCalculoExport(this.oMemoriaCalculo);
		
		ODTUtil odtutil = new ODTUtil(outputFile, templateFile, oExport);
		boolean bResult = odtutil.executeXls();
		if( !bResult ) {
			String warnmsg = String.format("Nome do arquivo: %s", outputFile);
			AppError.showErrorBox(this.getParentFrame(), "ERR: Falha na exportacao do arquivo", warnmsg, getClass());
		}
		else {
			String warnmsg = String.format("Nome do arquivo: %s", outputFile);
			AppError.showErrorBox(this.getParentFrame(), "Arquivo exportado com sucesso", warnmsg, getClass());
		}
	}

	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_OK) {
			doActionOk(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_CANCELAR) {
			doActionCancelar(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_RECALCULAR) {
			doActionReCalcular(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_EXPORTAR) {
			doActionExportar(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_DRENAGEM_MINIMIZED) {
			boolean bMinimized = !this.oMemoriaCalculo.isMinimized(); 
			this.oMemoriaCalculo.setMinimized(bMinimized);
		}
	}
	
	@Override
	public void actionMemoriaCalculoTableCellResultListener(MemoriaCalculoTableCellResultEvent e) 
	{
		int rowNum = e.getRownum();
		int colNum = e.getColnum();
		Object newVal = e.getNewval();
		//Object oldVal = e.getOldval();
		
		ColunaTabelaVO oCol = AppDefs.ARR_TBLCOL_MEMORIA_CALCULO[colNum];
		String colName = oCol.getColumnName();

		ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem = this.oMemoriaCalculo.getLsItem();
		CadMemoriaCalculoItemDrenagemOData o = lsItem.get(rowNum);
		o.setValueByName(colName, newVal);
		
		this.reInitMemoriaCalculo();
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

	public CadMemoriaCalculoDrenagem getMemoriaCalculo() {
		return oMemoriaCalculo;
	}

	public void setMemoriaCalculo(CadMemoriaCalculoDrenagem oMemoriaCalculo) {
		this.oMemoriaCalculo = oMemoriaCalculo;
	}

	public MemoriaCalculoModel getMemoriaCalculoModel() {
		return oModel;
	}

	public void setMemoriaCalculoModel(MemoriaCalculoModel oModel) {
		this.oModel = oModel;
	}
	
}
