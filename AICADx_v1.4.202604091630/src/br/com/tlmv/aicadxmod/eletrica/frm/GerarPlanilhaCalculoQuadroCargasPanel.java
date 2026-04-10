/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GerarPlanilhaCalculoQuadroCargasPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 16/01/2026
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

package br.com.tlmv.aicadxmod.eletrica.frm;

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
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
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
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadCircuitoQuadroCargasEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadQuadroCargasEletrica;
import br.com.tlmv.aicadxmod.eletrica.calc.EletricaCalc;
import br.com.tlmv.aicadxmod.eletrica.export.QuadroCargasExport;
import br.com.tlmv.aicadxmod.eletrica.frm.renderer.GerarPlanilhaCalculoQuadroCargasTableCellEditor;
import br.com.tlmv.aicadxmod.eletrica.frm.renderer.GerarPlanilhaCalculoQuadroCargasTableCellRenderer;
import br.com.tlmv.aicadxmod.eletrica.frm.renderer.GerarPlanilhaCalculoQuadroCargasTableCellResultEvent;
import br.com.tlmv.aicadxmod.eletrica.frm.renderer.GerarPlanilhaCalculoQuadroCargasTableCellResultListener;
import br.com.tlmv.aicadxmod.eletrica.model.QuadroCargasModel;

public class GerarPlanilhaCalculoQuadroCargasPanel extends BasePanel implements GerarPlanilhaCalculoQuadroCargasTableCellResultListener
{
//Private
	private CadDocumentDef doc = null;
	
	private CadQuadroCargasEletrica oQuadroCargasEletrica = null;

	private ArrayList<CadCircuitoQuadroCargasEletricaOData> lsItem = null;
	private Hashtable mapItem = null;

	private int rscode = AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_NONE;

	//FORM_DATA
	//
	private String 	nomeQuadro;
	private String 	descricaoQuadro;
	private double 	tensaoQuadro;
	private double 	bitolaMinimaCondutor;
	private double 	disjuntorMinimoProtecao;
	private double 	temperaturaAmbiente;
	private double 	fatorReducao;	
	private String 	sistemaFase;
	//
	private int 	qtdCargasIluminacaoQuadro;	
	private double 	cargasIluminacaoQuadro;	
	private int 	qtdCargasTomadaQuadro;	
	private double 	cargasTomadaQuadro;	
	private int 	qtdCargasMotorQuadro;	
	private double 	cargasMotorQuadro;	
	private int 	qtdCargasOutrosQuadro;	
	private double 	cargasOutrosQuadro;	
	private int 	qtdCargasPaineisQuadro;	
	private double 	cargasPaineisQuadro;	
	private double 	potenciaSemReservaQuadro;
	private double 	potenciaQuadro;	
	private double 	alimentadorQuadro;
	private double 	alimentadorProtecaoQuadro;
	private double 	disjuntorQuadro;
	private String 	faseQuadro;
	//
	private boolean bMinimized;
		
	//LABELS
	//
	private JLabel lblNomeQuadro;
	private JLabel lblDescricaoQuadro;
	private JLabel lblTensaoQuadro;
	private JLabel lblBitolaMinimaCondutor;
	private JLabel lblDisjuntorMinimoProtecao;
	private JLabel lblTemperaturaAmbiente;
	private JLabel lblFatorReducao;	
	private JLabel lblSistemaFase;
	//
	private JLabel lblQtdCargasIluminacaoQuadro;	
	private JLabel lblCargasIluminacaoQuadro;	
	private JLabel lblQtdCargasTomadaQuadro;	
	private JLabel lblCargasTomadaQuadro;	
	private JLabel lblQtdCargasMotorQuadro;	
	private JLabel lblCargasMotorQuadro;	
	private JLabel lblQtdCargasOutrosQuadro;	
	private JLabel lblCargasOutrosQuadro;	
	private JLabel lblQtdCargasPaineisQuadro;	
	private JLabel lblCargasPaineisQuadro;	
	private JLabel lblPotenciaSemReservaQuadro;
	private JLabel lblPotenciaQuadro;	
	private JLabel lblAlimentadorQuadro;
	private JLabel lblAlimentadorProtecaoQuadro;
	private JLabel lblDisjuntorQuadro;
	private JLabel lblFaseQuadro;

	//CONTROLS
	//
	private JTextField txtNomeQuadro;
	private JTextField txtDescricaoQuadro;
	private JComboBox cbxTensaoQuadro;
	private JTextField txtBitolaMinimaCondutor;
	private JTextField txtDisjuntorMinimoProtecao;
	private JTextField txtTemperaturaAmbiente;
	private JTextField txtFatorReducao;	
	private JComboBox cbxSistemaFase;
	//
	private JTextField txtQtdCargasIluminacaoQuadro;	
	private JTextField txtCargasIluminacaoQuadro;	
	private JTextField txtQtdCargasTomadaQuadro;	
	private JTextField txtCargasTomadaQuadro;	
	private JTextField txtQtdCargasMotorQuadro;	
	private JTextField txtCargasMotorQuadro;	
	private JTextField txtQtdCargasOutrosQuadro;	
	private JTextField txtCargasOutrosQuadro;	
	private JTextField txtQtdCargasPaineisQuadro;	
	private JTextField txtCargasPaineisQuadro;
	private JTextField txtPotenciaSemReservaQuadro;
	private JTextField txtPotenciaQuadro;	
	private JTextField txtAlimentadorQuadro;
	private JTextField txtAlimentadorProtecaoQuadro;
	private JTextField txtDisjuntorQuadro;
	private JTextField txtFaseQuadro;
	//
	private JCheckBox chkMinimized = null;

	//TABLE_DATA
	//
	private JLabel lblTableData = null;
	private JTable tblTableData = null;
	//
	private JScrollPane panTableScroll = null;
	
	//BUTTON
	//
	private JButton btnExportar = null;
	private JButton btnReCalcular = null;
	private JButton btnOk = null;
	private JButton btnCancelar = null;
	
	//MODEL: QUADRO_CARGAS
	//
	private QuadroCargasModel oModel = null;
	
	private Object[][] arrTableData = null;	

	/* Methodes */
	
	private Object[][] loadAllTableItems()
	{
		ArrayList<Object[]> lsResult = new ArrayList<Object[]>();
		
		ArrayList<CadCircuitoQuadroCargasEletricaOData> lsItem = this.oQuadroCargasEletrica.getLsItem();

		int nRows = lsItem.size();
		int nCols = AppDefs.ARR_TBLCOL_QUADRO_CARGAS.length;
		
		for(int i = 0; i < nRows; i++)
		{
			CadCircuitoQuadroCargasEletricaOData o = (CadCircuitoQuadroCargasEletricaOData)lsItem.get(i);			

			Object[] arr = o.toObjectArray(AppDefs.ARR_TBLCOL_QUADRO_CARGAS);
			lsResult.add(arr);
		}
		
		this.arrTableData = lsResult.toArray(new Object[nRows][nCols]);
		return this.arrTableData;
	}
	
	private void initCellEditorAndRender(JTable oTbl)
	{
		GerarPlanilhaCalculoQuadroCargasTableCellRenderer renderer = new GerarPlanilhaCalculoQuadroCargasTableCellRenderer(this);		
		GerarPlanilhaCalculoQuadroCargasTableCellEditor editor = new GerarPlanilhaCalculoQuadroCargasTableCellEditor(this, this);
		
		int h = this.oModel.getHdrHeight();
		oTbl.setRowHeight(h);
			
		oTbl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        TableColumnModel model = oTbl.getColumnModel();
		int sz = model.getColumnCount();
		for(int i = 0; i < sz; i++) {
			ColunaTabelaVO oHeader = (ColunaTabelaVO)this.oModel.getHeaderAt(i);

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
	
	private void initQuadroCargas() 
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		Date dataAtualHora = new Date();

		Date dataAtual = new Date(dataAtualHora.getYear(), dataAtualHora.getMonth(), dataAtualHora.getDate());

		this.nomeQuadro = this.oQuadroCargasEletrica.getNomeQuadro();
		this.descricaoQuadro = this.oQuadroCargasEletrica.getDescricaoQuadro();
		this.tensaoQuadro = this.oQuadroCargasEletrica.getTensaoQuadro();
		this.bitolaMinimaCondutor = this.oQuadroCargasEletrica.getBitolaMinimaCondutor();
		this.disjuntorMinimoProtecao = this.oQuadroCargasEletrica.getDisjuntorMinimoProtecao();
		this.temperaturaAmbiente = this.oQuadroCargasEletrica.getTemperaturaAmbiente();
		this.fatorReducao = this.oQuadroCargasEletrica.getFatorReducao();
		this.sistemaFase = this.oQuadroCargasEletrica.getSistemaFase();
		//
		this.qtdCargasIluminacaoQuadro = this.oQuadroCargasEletrica.getQtdCargasIluminacaoQuadro();
		this.cargasIluminacaoQuadro = this.oQuadroCargasEletrica.getCargasIluminacaoQuadro();
		this.qtdCargasTomadaQuadro = this.oQuadroCargasEletrica.getQtdCargasTomadaQuadro();
		this.cargasTomadaQuadro = this.oQuadroCargasEletrica.getCargasTomadaQuadro();
		this.qtdCargasMotorQuadro = this.oQuadroCargasEletrica.getQtdCargasMotorQuadro();
		this.cargasMotorQuadro = this.oQuadroCargasEletrica.getCargasMotorQuadro();
		this.qtdCargasOutrosQuadro = this.oQuadroCargasEletrica.getQtdCargasOutrosQuadro();
		this.cargasOutrosQuadro = this.oQuadroCargasEletrica.getCargasOutrosQuadro();
		this.qtdCargasPaineisQuadro = this.oQuadroCargasEletrica.getQtdCargasPaineisQuadro();
		this.cargasPaineisQuadro = this.oQuadroCargasEletrica.getCargasPaineisQuadro();
		this.potenciaSemReservaQuadro = this.oQuadroCargasEletrica.getPotenciaSemReservaQuadro();
		this.potenciaQuadro = this.oQuadroCargasEletrica.getPotenciaQuadro();
		this.alimentadorQuadro = this.oQuadroCargasEletrica.getAlimentadorQuadro();
		this.alimentadorProtecaoQuadro = this.oQuadroCargasEletrica.getAlimentadorProtecaoQuadro();
		this.disjuntorQuadro = this.oQuadroCargasEletrica.getDisjuntorQuadro();
		this.faseQuadro = this.oQuadroCargasEletrica.getFaseQuadro();
		this.bMinimized = this.oQuadroCargasEletrica.isMinimized();
	}
	
	private void reInitQuadroCargas() 
	{
		MainPanel panel = MainPanel.getMainPanel();
		CadDocumentDef doc = panel.getCurrDocumentDef();

		//TODO:
		
		this.removeAll();
		this.initForm();
	}
	
	private void initForm()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String uuid = UuidUtil.generateUUID();

		int w = Math.max(this.getWidth(), AppDefs.GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_ELETRICA_FRAME_WIDTH);
		int h = Math.max(this.getHeight(), AppDefs.GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_ELETRICA_FRAME_HEIGHT);		
		
		this.arrTableData = this.loadAllTableItems();
		
		this.oModel = new QuadroCargasModel(AppDefs.ARR_TBLCOL_QUADRO_CARGAS, this.oQuadroCargasEletrica.getLsItem());

		/*
		 * MAIN_PANEL
		 */
		this.setLayout(null);		

		Insets insets = this.getInsets();

		int x = insets.left + AppDefs.SPACE_W5;
		int y = insets.top + AppDefs.SPACE_H5;

		// FORM_LABELS
		//
		this.lblNomeQuadro = FormControlUtil.newLabel("Quadro: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblNomeQuadro);
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		this.lblDescricaoQuadro = FormControlUtil.newLabel("Descricao: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblDescricaoQuadro);
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// LINHA_1
		//
		this.lblTensaoQuadro = FormControlUtil.newLabel("Tensao(V): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblTensaoQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblBitolaMinimaCondutor = FormControlUtil.newLabel("Bitola min.condutor(mm2): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblBitolaMinimaCondutor.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblBitolaMinimaCondutor);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// LINHA_2
		//
		this.lblDisjuntorMinimoProtecao = FormControlUtil.newLabel("Disjuntor min.protecao(A): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblDisjuntorMinimoProtecao.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblDisjuntorMinimoProtecao);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblTemperaturaAmbiente = FormControlUtil.newLabel("Temperatura Ambiente(oC): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblTemperaturaAmbiente);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// LINHA_3
		//
		this.lblFatorReducao = FormControlUtil.newLabel("Fator de reducao: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblFatorReducao.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblFatorReducao);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblSistemaFase = FormControlUtil.newLabel("Sistema: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblSistemaFase);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// CARGAS_ILUMINACAO
		//
		this.lblQtdCargasIluminacaoQuadro = FormControlUtil.newLabel("Qtd.Iluminacao: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblQtdCargasIluminacaoQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblCargasIluminacaoQuadro = FormControlUtil.newLabel("Cargas Iluminacao(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblCargasIluminacaoQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblCargasIluminacaoQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// CARGAS_TOMADA
		//
		this.lblQtdCargasTomadaQuadro = FormControlUtil.newLabel("Qtd.Tomada: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblQtdCargasTomadaQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblCargasTomadaQuadro = FormControlUtil.newLabel("Cargas Tomada(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblCargasTomadaQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblCargasTomadaQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// CARGAS_MOTOR
		//
		this.lblQtdCargasMotorQuadro = FormControlUtil.newLabel("Qtd.Motor: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblQtdCargasMotorQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblCargasMotorQuadro = FormControlUtil.newLabel("Cargas Motor(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblCargasMotorQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblCargasMotorQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// OUTRAS_CARGAS
		//
		this.lblQtdCargasOutrosQuadro = FormControlUtil.newLabel("Qtd.Outros: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblQtdCargasOutrosQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblCargasOutrosQuadro = FormControlUtil.newLabel("Cargas Outros(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblCargasOutrosQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblCargasOutrosQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// CARGAS_PAINEIS
		//
		this.lblQtdCargasPaineisQuadro = FormControlUtil.newLabel("Qtd.Cargas Paineis: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblQtdCargasPaineisQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblCargasPaineisQuadro = FormControlUtil.newLabel("Cargas Paineis(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblCargasPaineisQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblCargasPaineisQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// POTENCIA / POTENCIA_SEM_RESERVA
		//
		this.lblPotenciaSemReservaQuadro = FormControlUtil.newLabel("Potencia sem Reserva(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblPotenciaSemReservaQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblPotenciaQuadro = FormControlUtil.newLabel("Potencia(VA): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblPotenciaQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblPotenciaQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// ALIMENTADORES
		//
		this.lblAlimentadorQuadro = FormControlUtil.newLabel("Condutor(mm2): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblAlimentadorQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblAlimentadorProtecaoQuadro = FormControlUtil.newLabel("Condutor Proteção(mm2): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblAlimentadorProtecaoQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblAlimentadorProtecaoQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// DISJUNTOR_QUADRO
		//
		this.lblDisjuntorQuadro = FormControlUtil.newLabel("Disjuntor(A): ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblDisjuntorQuadro);
		x += AppDefs.LABEL_W200 + AppDefs.SPACE_W5 + AppDefs.TEXT_W150 + AppDefs.SPACE_W5;

		this.lblFaseQuadro = FormControlUtil.newLabel("Fase: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.lblFaseQuadro.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(this.lblFaseQuadro);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		// FORM_CONTROLS
		//
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y = insets.top + AppDefs.SPACE_H5;

		this.txtNomeQuadro = FormControlUtil.newTextField(this.nomeQuadro, x, y, AppDefs.TEXT_W700, AppDefs.TEXT_H20, true, true);
		this.add(this.txtNomeQuadro);
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtDescricaoQuadro = FormControlUtil.newTextField(this.descricaoQuadro, x, y, AppDefs.TEXT_W700, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDescricaoQuadro);
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// LINHA_1
		//
		this.cbxTensaoQuadro = FormControlUtil.newComboBox(AppDefs.ARR_TENSAOQUADRO, x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true);
		this.add(this.cbxTensaoQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		
		this.txtBitolaMinimaCondutor = FormControlUtil.newTextField(nf1.format(this.bitolaMinimaCondutor), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtBitolaMinimaCondutor);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// LINHA_2
		//
		this.txtDisjuntorMinimoProtecao = FormControlUtil.newTextField(nf1.format(this.disjuntorMinimoProtecao), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDisjuntorMinimoProtecao);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtTemperaturaAmbiente = FormControlUtil.newTextField(nf1.format(this.temperaturaAmbiente), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtTemperaturaAmbiente);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// LINHA_3
		//
		this.txtFatorReducao = FormControlUtil.newTextField(nf3.format(this.fatorReducao), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtFatorReducao);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.cbxSistemaFase = FormControlUtil.newComboBox(AppDefs.ARR_SISTEMA_FASE, x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true);
		this.add(this.cbxSistemaFase);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		// CARGAS_ILUMINACAO
		//
		this.txtQtdCargasIluminacaoQuadro = FormControlUtil.newTextField(nf0.format(this.qtdCargasIluminacaoQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtQtdCargasIluminacaoQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtCargasIluminacaoQuadro = FormControlUtil.newTextField(nf3.format(this.cargasIluminacaoQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtCargasIluminacaoQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// CARGAS_TOMADA
		//
		this.txtQtdCargasTomadaQuadro = FormControlUtil.newTextField(nf3.format(this.qtdCargasTomadaQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtQtdCargasTomadaQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtCargasTomadaQuadro = FormControlUtil.newTextField(nf3.format(this.cargasTomadaQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtCargasTomadaQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// CARGAS_MOTOR
		//
		this.txtQtdCargasMotorQuadro = FormControlUtil.newTextField(nf3.format(this.qtdCargasMotorQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtQtdCargasMotorQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtCargasMotorQuadro = FormControlUtil.newTextField(nf3.format(this.cargasMotorQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtCargasMotorQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// OUTRAS_CARGAS
		//
		this.txtQtdCargasOutrosQuadro = FormControlUtil.newTextField(nf3.format(this.qtdCargasOutrosQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtQtdCargasOutrosQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtCargasOutrosQuadro = FormControlUtil.newTextField(nf3.format(this.cargasOutrosQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtCargasOutrosQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// CARGAS_PAINEIS
		//
		this.txtQtdCargasPaineisQuadro = FormControlUtil.newTextField(nf3.format(this.qtdCargasPaineisQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtQtdCargasPaineisQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtCargasPaineisQuadro = FormControlUtil.newTextField(nf3.format(this.cargasPaineisQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, false);
		this.add(this.txtCargasPaineisQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// POTENCIA / POTENCIA_SEM_RESERVA
		//
		this.txtPotenciaSemReservaQuadro = FormControlUtil.newTextField(nf3.format(this.potenciaSemReservaQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtPotenciaSemReservaQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;

		this.txtPotenciaQuadro = FormControlUtil.newTextField(nf3.format(this.potenciaQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtPotenciaQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		// ALIMENTADOR_QUADRO
		//
		this.txtAlimentadorQuadro = FormControlUtil.newTextField(nf1.format(this.alimentadorQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtAlimentadorQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		
		this.txtAlimentadorProtecaoQuadro = FormControlUtil.newTextField(nf1.format(this.alimentadorProtecaoQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtAlimentadorProtecaoQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		// DISJUNTOR_QUADRO
		//
		this.txtDisjuntorQuadro = FormControlUtil.newTextField(nf1.format(this.disjuntorQuadro), x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDisjuntorQuadro);
		x += AppDefs.TEXT_W150 + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		
		this.txtFaseQuadro = FormControlUtil.newTextField(this.faseQuadro, x, y, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.add(this.txtFaseQuadro);
		x = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W200 + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		// MINIMIZED
		//
		boolean bMinimized = this.oQuadroCargasEletrica.isMinimized();
		this.chkMinimized = FormControlUtil.newCheckBox(bMinimized, "Apresenta tabela minimizada.", x, y, AppDefs.LABEL_W600, AppDefs.LABEL_H20, true, true);
		this.chkMinimized.setActionCommand(Integer.toString(AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_MINIMIZED));
		this.chkMinimized.addActionListener(this);
		this.add(this.chkMinimized);
		x = insets.left + AppDefs.SPACE_W5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		// ITEM_QUADRO_CARGAS
		//
		this.lblTableData = FormControlUtil.newLabel("Lista de circuito: ", x, y, AppDefs.LABEL_W200, AppDefs.LABEL_H20, true);
		this.add(this.lblTableData);
		y += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		//TABLE_DATA
		//
		int h_top = AppDefs.GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_ELETRICA_FRAME_HEIGHT - (y + AppDefs.SPACE_W5 + AppDefs.BUTTON_H20 + AppDefs.SPACE_W5);
		
		this.tblTableData = FormControlUtil.newBasicTable(AppDefs.ARR_TBLCOL_QUADRO_CARGAS, this.arrTableData, x, y, w, h, true);
		this.panTableScroll = FormControlUtil.newScrollPane(this, this.tblTableData, x, y, w, h_top, true);

		this.initCellEditorAndRender(this.tblTableData);

		this.tblTableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblTableData.setAutoscrolls(true);
		
		//BUTTON
		//
		x = insets.left + AppDefs.SPACE_W5;
		y += h_top + AppDefs.SPACE_W5;

		this.btnExportar = FormControlUtil.newButton("Exportar", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_EXPORTAR, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnExportar);
		x = AppDefs.GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_ELETRICA_FRAME_WIDTH - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnCancelar = FormControlUtil.newButton("Cancelar", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_CANCELAR, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnCancelar);
		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnOk = FormControlUtil.newButton("Ok", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_OK, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnOk);
		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnReCalcular = FormControlUtil.newButton("Calcular", AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_RECALCULAR, x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnReCalcular);

		this.resizeForm();
	}
	
	private void resizeForm()
	{
		int w = Math.max(this.getWidth(), AppDefs.GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_ELETRICA_FRAME_WIDTH);
		int h = Math.max(this.getHeight(), AppDefs.GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_ELETRICA_FRAME_HEIGHT);		
		
		/*
		 * MAIN_PANEL
		 */
		this.setLayout(null);		

		Insets insets = this.getInsets();

		int x = insets.left + AppDefs.SPACE_W5;
		int y = insets.top + AppDefs.SPACE_H5;

		// FORM_CONTROLS
		//
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		y += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		//TABLE_DATA
		//
		int h_top = h - (y + AppDefs.SPACE_W5 + (3 * AppDefs.BUTTON_H20) + AppDefs.SPACE_W5);
		
		this.tblTableData.setBounds(x, y, w, h_top);
		this.panTableScroll.setBounds(x, y, w, h_top);
		
		//BUTTON
		//
		x = insets.left + AppDefs.SPACE_W5;
		y += h_top + AppDefs.SPACE_W5;

		this.btnExportar.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);

		x = w - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnCancelar.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);

		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnOk.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);

		x = x - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W10);

		this.btnReCalcular.setBounds(x, y, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20);

	}
	
//Public 
	
	public GerarPlanilhaCalculoQuadroCargasPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(CadQuadroCargasEletrica oQuadroCargasEletrica)
	{
		this.oQuadroCargasEletrica = oQuadroCargasEletrica;
		
		this.addComponentListener(this);

		this.initQuadroCargas();
		
		this.initForm();
	}
	
	/* Methodes */
	
	public boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		EletricaCalc calc = new EletricaCalc(this.doc);
		
		String strNomeQuadro = this.txtNomeQuadro.getText();
		String strDescricaoQuadro = this.txtDescricaoQuadro.getText();
		String strBitolaMinimaCondutor = this.txtBitolaMinimaCondutor.getText();
		String strDisjuntorMinimoProtecao = this.txtDisjuntorMinimoProtecao.getText();
		String strTemperaturaAmbiente = this.txtTemperaturaAmbiente.getText();
		String strFatorReducao = this.txtFatorReducao.getText();
		//
		String strQtdCargasIluminacaoQuadro = this.txtQtdCargasIluminacaoQuadro.getText();
		String strCargasIluminacaoQuadro = this.txtCargasIluminacaoQuadro.getText();
		String strQtdCargasTomadaQuadro = this.txtQtdCargasTomadaQuadro.getText();
		String strCargasTomadaQuadro = this.txtCargasTomadaQuadro.getText();
		String strQtdCargasMotorQuadro = this.txtQtdCargasMotorQuadro.getText();
		String strCargasMotorQuadro = this.txtCargasMotorQuadro.getText();
		String strQtdCargasOutrosQuadro = this.txtQtdCargasOutrosQuadro.getText();
		String strCargasOutrosQuadro = this.txtCargasOutrosQuadro.getText();
		String strQtdCargasPaineisQuadro = this.txtQtdCargasPaineisQuadro.getText();
		String strCargasPaineisQuadro = this.txtCargasPaineisQuadro.getText();
		String strPotenciaSemReservaQuadro = this.txtPotenciaSemReservaQuadro.getText();
		String strPotenciaQuadro = this.txtPotenciaQuadro.getText();
		String strAlimentadorQuadro = this.txtAlimentadorQuadro.getText();
		String strAlimentadorProtecaoQuadro = this.txtAlimentadorProtecaoQuadro.getText();
		String strDisjuntorQuadro = this.txtDisjuntorQuadro.getText();
		String strFaseQuadro = this.txtFaseQuadro.getText();
		
		ItemDataVO oTensaoQuadro = (ItemDataVO)this.cbxTensaoQuadro.getSelectedItem();
		ItemDataVO oSistemaFase = (ItemDataVO)this.cbxSistemaFase.getSelectedItem();

		// EMPTY_FORM_VALIDATION
		//
		if( StringUtil.isEmpty(strNomeQuadro) )
			errmsg += "Nome do quadro; ";
				
		if( StringUtil.isEmpty(strDescricaoQuadro) )
			errmsg += "Descricao do quadro; ";
		
		if( StringUtil.isEmpty(strBitolaMinimaCondutor) )
			errmsg += "Bitola minima do condutor; ";
		
		if( StringUtil.isEmpty(strDisjuntorMinimoProtecao) )
			errmsg += "Disjuntor minimo de protecao; ";
		
		if( StringUtil.isEmpty(strTemperaturaAmbiente) )
			errmsg += "Temperatura ambiente; ";
		
		if( StringUtil.isEmpty(strFatorReducao) )
			errmsg += "Fator de reducao; ";

		if( StringUtil.isEmpty(strQtdCargasIluminacaoQuadro) )
			errmsg += "Quantidade cargas de iluminacao; ";

		if( StringUtil.isEmpty(strCargasIluminacaoQuadro) )
			errmsg += "Cargas de iluminacao; ";

		if( StringUtil.isEmpty(strQtdCargasTomadaQuadro) )
			errmsg += "Quantidade cargas de tomada; ";

		if( StringUtil.isEmpty(strCargasTomadaQuadro) )
			errmsg += "Cargas de tomada; ";

		if( StringUtil.isEmpty(strQtdCargasMotorQuadro) )
			errmsg += "Quantidade cargas de motor; ";

		if( StringUtil.isEmpty(strCargasMotorQuadro) )
			errmsg += "Cargas de motor; ";

		if( StringUtil.isEmpty(strQtdCargasOutrosQuadro) )
			errmsg += "Quantidade de outras cargas; ";

		if( StringUtil.isEmpty(strCargasOutrosQuadro) )
			errmsg += "Outras cargas; ";

		if( StringUtil.isEmpty(strQtdCargasPaineisQuadro) )
			errmsg += "Quantidade de paineis; ";

		if( StringUtil.isEmpty(strCargasPaineisQuadro) )
			errmsg += "Cargas de paineis; ";

		if( StringUtil.isEmpty(strPotenciaSemReservaQuadro) )
			errmsg += "Potencia sem carga reserva; ";

		if( StringUtil.isEmpty(strPotenciaQuadro) )
			errmsg += "Potencia do quadro; ";

		if( StringUtil.isEmpty(strAlimentadorQuadro) )
			errmsg += "Alimentador do quadro; ";

		if( StringUtil.isEmpty(strAlimentadorProtecaoQuadro) )
			errmsg += "Alimentador de protecao do quadro; ";

		if( StringUtil.isEmpty(strDisjuntorQuadro) )
			errmsg += "Disjuntor do quadro; ";

		if( StringUtil.isEmpty(strFaseQuadro) )
			errmsg += "Fase do quadro; ";
		
		if( oTensaoQuadro == null )
			errmsg += "Tensao do quadro; ";
		
		if( oSistemaFase == null )
			errmsg += "Sistema de fase; ";
		
		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}
		
		//QUADRO_CARGAS
		//
		double dTensaoQuadro = oTensaoQuadro.getDblVal();
		double dBitolaMinimaCondutor = StringUtil.safeDbl(nf1, strBitolaMinimaCondutor);
		double dDisjuntorMinimoProtecao = StringUtil.safeDbl(nf1, strDisjuntorMinimoProtecao);
		double dTemperaturaAmbiente = StringUtil.safeDbl(nf1, strTemperaturaAmbiente);
		double dFatorReducao = StringUtil.safeDbl(nf3, strFatorReducao);
		//
		int    dQtdCargasIluminacaoQuadro = StringUtil.safeInt(strQtdCargasIluminacaoQuadro);
		double dCargasIluminacaoQuadro = StringUtil.safeDbl(nf1, strCargasIluminacaoQuadro);
		int    dQtdCargasTomadaQuadro = StringUtil.safeInt(strQtdCargasTomadaQuadro);
		double dCargasTomadaQuadro = StringUtil.safeDbl(nf1, strCargasTomadaQuadro);
		int    dQtdCargasMotorQuadro = StringUtil.safeInt(strQtdCargasMotorQuadro);
		double dCargasMotorQuadro = StringUtil.safeDbl(nf1, strCargasMotorQuadro);
		int    dQtdCargasOutrosQuadro = StringUtil.safeInt(strQtdCargasOutrosQuadro);
		double dCargasOutrosQuadro = StringUtil.safeDbl(nf1, strCargasOutrosQuadro);
		int    dQtdCargasPaineisQuadro = StringUtil.safeInt(strQtdCargasPaineisQuadro);
		double dCargasPaineisQuadro = StringUtil.safeDbl(nf1, strCargasPaineisQuadro);
		double dPotenciaSemReservaQuadro = StringUtil.safeDbl(nf1, strPotenciaSemReservaQuadro);
		double dPotenciaQuadro = StringUtil.safeDbl(nf1, strPotenciaQuadro);
		double dAlimentadorQuadro = StringUtil.safeDbl(nf1, strAlimentadorQuadro);
		double dAlimentadorProtecaoQuadro = StringUtil.safeDbl(nf1, strAlimentadorProtecaoQuadro);
		double dDisjuntorQuadro = StringUtil.safeDbl(nf1, strDisjuntorQuadro);
		
		if(dQtdCargasIluminacaoQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de iluminacao tem que ser maior ou igual a 0";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dCargasIluminacaoQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de iluminacao tem que ser maior ou igual a 0 VA";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dQtdCargasTomadaQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de tomada tem que ser maior ou igual a 0";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dCargasTomadaQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de tomada tem que ser maior ou igual a 0 VA";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dQtdCargasMotorQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de motor tem que ser maior ou igual a 0";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dCargasMotorQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de motor tem que ser maior ou igual a 0 VA";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dQtdCargasOutrosQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga outras tem que ser maior ou igual a 0";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dCargasOutrosQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga outras tem que ser maior ou igual a 0 VA";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dQtdCargasPaineisQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de paineis tem que ser maior ou igual a 0";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dCargasPaineisQuadro < 0.0) {
			errmsg = "ERR: Quantidade de carga de paineis tem que ser maior ou igual a 0 VA";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dPotenciaSemReservaQuadro < dPotenciaQuadro) {
			errmsg = "ERR: Quantidade de potencia sem reserva tem que ser maior ou igual a potencia total";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dPotenciaQuadro < 0.0) {
			errmsg = "ERR: Quantidade de potencia tem que ser maior ou igual a 0 VA";
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dAlimentadorQuadro < this.bitolaMinimaCondutor) {
			errmsg = String.format("ERR: Alimentador tem que ser maior ou igual a %s mm2", nf3.format(this.bitolaMinimaCondutor));
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dAlimentadorProtecaoQuadro < this.bitolaMinimaCondutor) {
			errmsg = String.format("ERR: Alimentador de protecao tem que ser maior ou igual a %s mm2", nf3.format(this.bitolaMinimaCondutor) );
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}

		if(dDisjuntorQuadro < this.disjuntorMinimoProtecao) {
			errmsg = String.format("ERR: Disjuntor de protecao tem que ser maior ou igual a %s A", nf0.format(this.disjuntorMinimoProtecao) );
			AppError.showErrorBox(this.getParentFrame(), errmsg, errmsg, this.getClass());
			return false;
		}
		
		//UPDATE_DATA
		//
		this.nomeQuadro = strNomeQuadro;
		this.descricaoQuadro = strDescricaoQuadro;
		this.bitolaMinimaCondutor = dBitolaMinimaCondutor;
		this.disjuntorMinimoProtecao = 
		this.temperaturaAmbiente = dTemperaturaAmbiente;
		this.fatorReducao = dFatorReducao;
		//
		this.qtdCargasIluminacaoQuadro = dQtdCargasIluminacaoQuadro;
		this.cargasIluminacaoQuadro = dCargasIluminacaoQuadro;
		this.qtdCargasTomadaQuadro = dQtdCargasTomadaQuadro;
		this.cargasTomadaQuadro = dCargasTomadaQuadro;
		this.qtdCargasMotorQuadro = dQtdCargasMotorQuadro;
		this.cargasMotorQuadro = dCargasMotorQuadro;
		this.qtdCargasOutrosQuadro = dQtdCargasOutrosQuadro;
		this.cargasOutrosQuadro = dCargasOutrosQuadro;
		this.qtdCargasPaineisQuadro = dQtdCargasPaineisQuadro;
		this.cargasPaineisQuadro = dCargasPaineisQuadro;
		this.potenciaSemReservaQuadro = dPotenciaSemReservaQuadro;
		this.potenciaQuadro = dPotenciaQuadro;
		this.alimentadorQuadro = dAlimentadorQuadro;
		this.alimentadorProtecaoQuadro = dAlimentadorProtecaoQuadro;
		this.disjuntorQuadro = dDisjuntorQuadro;
		this.faseQuadro = strFaseQuadro;
		//
		this.bMinimized = this.chkMinimized.isSelected();
		
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
			this.rscode = AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_OK;
			this.actionResultListener(new ResultEvent(this.rscode, null));

			this.getParentFrame().dispose();
		}
	}
	
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_CANCELAR;
		this.actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
	public void doActionExportar(ActionEvent e) 
	{
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
	
		String templateFile = ctx.getTemplateFileMemoriaCalculo();
		
		String outputFile = FileUtil.generateFileName(ctx.getOutputDir()) + "." + AppDefs.EXT_XLS;
		
		IExportData oExport = new QuadroCargasExport(this.oQuadroCargasEletrica);
		
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

		if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_OK) {
			doActionOk(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_CANCELAR) {
			doActionCancelar(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_EXPORTAR) {
			doActionExportar(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANILHA_CALCULO_QUADRO_CARGAS_MINIMIZED) {
			boolean bMinimized = !this.oQuadroCargasEletrica.isMinimized(); 
			this.oQuadroCargasEletrica.setMinimized(bMinimized);
		}
	}
	
	@Override
	public void actionCalculoQuadroCargasTableCellResultListener(GerarPlanilhaCalculoQuadroCargasTableCellResultEvent e) 
	{
		int rowNum = e.getRownum();
		int colNum = e.getColnum();
		Object newVal = e.getNewval();
		//Object oldVal = e.getOldval();
		
		ColunaTabelaVO oCol = AppDefs.ARR_TBLCOL_QUADRO_CARGAS[colNum];
		String colName = oCol.getColumnName();

		ArrayList<CadCircuitoQuadroCargasEletricaOData> lsItem = this.oQuadroCargasEletrica.getLsItem();
		CadCircuitoQuadroCargasEletricaOData o = lsItem.get(rowNum);
		o.setValueByName(colName, newVal);
		
		this.reInitQuadroCargas();
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

	public CadDocumentDef getDoc() {
		return this.doc;
	}

	public void setDoc(CadDocumentDef doc) {
		this.doc = doc;
	}

	public QuadroCargasModel getModel() {
		return this.oModel;
	}

	public void setModel(QuadroCargasModel oModel) {
		this.oModel = oModel;
	}

	public CadQuadroCargasEletrica getQuadroCargasEletrica() {
		return this.oQuadroCargasEletrica;
	}

	public void setQuadroCargasEletrica(CadQuadroCargasEletrica oQuadroCargasEletrica) {
		this.oQuadroCargasEletrica = oQuadroCargasEletrica;
	}

	public String getNomeQuadro() {
		return this.nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getDescricaoQuadro() {
		return this.descricaoQuadro;
	}

	public void setDescricaoQuadro(String descricaoQuadro) {
		this.descricaoQuadro = descricaoQuadro;
	}

	public double getTensaoQuadro() {
		return this.tensaoQuadro;
	}

	public void setTensaoQuadro(double tensaoQuadro) {
		this.tensaoQuadro = tensaoQuadro;
	}

	public double getBitolaMinimaCondutor() {
		return this.bitolaMinimaCondutor;
	}

	public void setBitolaMinimaCondutor(double bitolaMinimaCondutor) {
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
	}

	public double getTemperaturaAmbiente() {
		return this.temperaturaAmbiente;
	}

	public void setTemperaturaAmbiente(double temperaturaAmbiente) {
		this.temperaturaAmbiente = temperaturaAmbiente;
	}

	public double getFatorReducao() {
		return this.fatorReducao;
	}

	public void setFatorReducao(double fatorReducao) {
		this.fatorReducao = fatorReducao;
	}

	public String getSistemaFase() {
		return this.sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public int getQtdCargasIluminacaoQuadro() {
		return this.qtdCargasIluminacaoQuadro;
	}

	public void setQtdCargasIluminacaoQuadro(int qtdCargasIluminacaoQuadro) {
		this.qtdCargasIluminacaoQuadro = qtdCargasIluminacaoQuadro;
	}

	public double getCargasIluminacaoQuadro() {
		return this.cargasIluminacaoQuadro;
	}

	public void setCargasIluminacaoQuadro(double cargasIluminacaoQuadro) {
		this.cargasIluminacaoQuadro = cargasIluminacaoQuadro;
	}

	public int getQtdCargasTomadaQuadro() {
		return this.qtdCargasTomadaQuadro;
	}

	public void setQtdCargasTomadaQuadro(int qtdCargasTomadaQuadro) {
		this.qtdCargasTomadaQuadro = qtdCargasTomadaQuadro;
	}

	public double getCargasTomadaQuadro() {
		return this.cargasTomadaQuadro;
	}

	public void setCargasTomadaQuadro(double cargasTomadaQuadro) {
		this.cargasTomadaQuadro = cargasTomadaQuadro;
	}

	public int getQtdCargasMotorQuadro() {
		return this.qtdCargasMotorQuadro;
	}

	public void setQtdCargasMotorQuadro(int qtdCargasMotorQuadro) {
		this.qtdCargasMotorQuadro = qtdCargasMotorQuadro;
	}

	public double getCargasMotorQuadro() {
		return this.cargasMotorQuadro;
	}

	public void setCargasMotorQuadro(double cargasMotorQuadro) {
		this.cargasMotorQuadro = cargasMotorQuadro;
	}

	public int getQtdCargasOutrosQuadro() {
		return this.qtdCargasOutrosQuadro;
	}

	public void setQtdCargasOutrosQuadro(int qtdCargasOutrosQuadro) {
		this.qtdCargasOutrosQuadro = qtdCargasOutrosQuadro;
	}

	public double getCargasOutrosQuadro() {
		return this.cargasOutrosQuadro;
	}

	public void setCargasOutrosQuadro(double cargasOutrosQuadro) {
		this.cargasOutrosQuadro = cargasOutrosQuadro;
	}

	public int getQtdCargasPaineisQuadro() {
		return this.qtdCargasPaineisQuadro;
	}

	public void setQtdCargasPaineisQuadro(int qtdCargasPaineisQuadro) {
		this.qtdCargasPaineisQuadro = qtdCargasPaineisQuadro;
	}

	public double getCargasPaineisQuadro() {
		return this.cargasPaineisQuadro;
	}

	public void setCargasPaineisQuadro(double cargasPaineisQuadro) {
		this.cargasPaineisQuadro = cargasPaineisQuadro;
	}

	public double getPotenciaSemReservaQuadro() {
		return this.potenciaSemReservaQuadro;
	}

	public void setPotenciaSemReservaQuadro(double potenciaSemReservaQuadro) {
		this.potenciaSemReservaQuadro = potenciaSemReservaQuadro;
	}

	public double getPotenciaQuadro() {
		return this.potenciaQuadro;
	}

	public void setPotenciaQuadro(double potenciaQuadro) {
		this.potenciaQuadro = potenciaQuadro;
	}

	public double getAlimentadorQuadro() {
		return this.alimentadorQuadro;
	}

	public void setAlimentadorQuadro(double alimentadorQuadro) {
		this.alimentadorQuadro = alimentadorQuadro;
	}

	public double getAlimentadorProtecaoQuadro() {
		return this.alimentadorProtecaoQuadro;
	}

	public void setAlimentadorProtecaoQuadro(double alimentadorProtecaoQuadro) {
		this.alimentadorProtecaoQuadro = alimentadorProtecaoQuadro;
	}

	public double getDisjuntorQuadro() {
		return this.disjuntorQuadro;
	}

	public void setDisjuntorQuadro(double disjuntorQuadro) {
		this.disjuntorQuadro = disjuntorQuadro;
	}

	public String getFaseQuadro() {
		return this.faseQuadro;
	}

	public void setFaseQuadro(String faseQuadro) {
		this.faseQuadro = faseQuadro;
	}
	
}
