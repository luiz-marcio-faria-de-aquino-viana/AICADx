/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PropriedadeCaixaInspecaoDrenagemPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/05/2025
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

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;
import br.com.tlmv.aicadxmod.drenagem.vo.CoeficienteChuvasIDFVO;
import br.com.tlmv.aicadxmod.drenagem.vo.TubulacaoDrenagemVO;

public class PropriedadeCaixaInspecaoDrenagemPanel extends BasePanel
{
//Private
    private CadCaixaInspecaoDrenagem oEnt1 = null;    

	private int rscode = AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_NONE;
	
	/* PANELS */

	private JTabbedPane tabMainPanel = null; 

	private JPanel panGeneral = null;
	private JPanel panDimensions = null;
	private JPanel panOutputPipe = null;
	
	/* PROPRIEDADES_CAIXA_INSPECAO */

    private String tipoCI;						// _ESGOTO_ / _APLUVIAL_
    private String subtipoCI;					// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
    private int numeroCI;						// [id_caixa_inspecao]
    private int proximaCI;						// [id_proxima_caixa_inspecao]
    private int numEstaca;						// NumEstaca = 2
    private double distEstaca;					// DistEstaca = 1.70 m
	private String pv;							// PV-A2.1
	private int localId;						// 1001 - RUA DR. MARIO MACHADO
	private String local;						// RUA DR. MARIO MACHADO
	private String estaca;						// 2 + 1.70 m
	private double areaExterna;					// AreaExterna = SOMA(AreaTotal_Anterior)
	private double areaLocal;					// AreaLocal = 0.220 ha
	private double areaTotal;					// AreaTotal = AreaExterna + AreaLocal
	private double areaTotalImp;				// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
	private double diametroMeter;					// Diametro = 0.60
    private double vazao;						// VazaoCalculada
    private double vazaoAcumulada;    			// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada
    private String tipoSecaoTubulacao;			// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR    
    private int categoriaTubulacaoId;			// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
    private String descricaoCategoriaTubulacao;	// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
    private int qtdTubulacao;					// QtdTubulacao = 1, 2, 3, 4...
    private double diametroTubulacaoMeter;			// DiametroTubulacao = 250 mm (0.25 m)
    private double declividade;					// Declividade = 0.00160
	private double coefImper;					// CoefImper = 0.80; Area Urbana
    private double profundidade;				// Profundidade = -0.60 m
    private double ct;							// CotaTerreno = 2.841 m
    private double cb;							// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
    private double cotaEntrada;					// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
    private double cotaSaida;					// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
    //
    private ArrayList<ItemDataVO> lsLocal = null;
    private ArrayList<ItemDataVO> lsTipoSecaoTubulacao = null;
    private ArrayList<ItemDataVO> lsCategoriaTubulacao = null;
    private ArrayList<TubulacaoDrenagemVO> lsTubulacaoDrenagem = null;
    private ArrayList<ItemDataVO> lsCoefImper = null;
    
	//LABELS
	//
    private JLabel lblTipoCI = null;
    private JLabel lblSubtipoCI = null;
    private JLabel lblNumeroCI = null;
    private JLabel lblProximaCI = null;
    private JLabel lblNumEstaca = null;
    private JLabel lblDistEstaca = null;
    private JLabel lblPV = null;
    private JLabel lblLocal = null;
    private JLabel lblAreaExterna = null;
    private JLabel lblAreaLocal = null;
    private JLabel lblAreaTotal = null;
    private JLabel lblAreaTotalImp = null;
    private JLabel lblDiametro = null;
    private JLabel lblVazao = null;
    private JLabel lblVazaoAcumulada = null;
    private JLabel lblTipoSecaoTubulacao = null;
    private JLabel lblCategoriaTubulacao = null;
    private JLabel lblDiametroTubulacao = null;
    private JLabel lblQtdTubulacao = null;
    private JLabel lblDeclividade = null;
    private JLabel lblCoefImper = null;
    private JLabel lblProfundidade = null;
    private JLabel lblCT = null;
    private JLabel lblCB = null;
    private JLabel lblCotaEntrada = null;
    private JLabel lblCotaSaida = null;

	//CONTROLS
	//
	private JTextField txtTipoCI = null;
	private JTextField txtSubtipoCI = null;
	private JTextField txtNumeroCI = null;
	private JTextField txtProximaCI = null;
	private JTextField txtNumEstaca = null;
	private JTextField txtDistEstaca = null;
	private JTextField txtPV = null;
    private JComboBox<ItemDataVO> cbxLocalId = null;
    private JTextField txtAreaExterna = null;
    private JTextField txtAreaLocal = null;
    private JTextField txtAreaTotal = null;
    private JTextField txtAreaTotalImp = null;
	private JTextField txtDiametro = null;
    private JTextField txtVazao = null;
    private JTextField txtVazaoAcumulada = null;
    private JComboBox<ItemDataVO> cbxTipoSecaoTubulacao = null;
    private JComboBox<ItemDataVO> cbxCategoriaTubulacaoId = null;
    private JComboBox<TubulacaoDrenagemVO> cbxDiametroTubulacao = null;
    private JTextField txtQtdTubulacao = null;
    private JTextField txtDeclividade = null;
    private JComboBox<ItemDataVO> cbxCoefImper = null;
    private JTextField txtProfundidade = null;
    private JTextField txtCT = null;
    private JTextField txtCB = null;
    private JTextField txtCotaEntrada = null;
    private JTextField txtCotaSaida = null;
	
	//BUTTON
	//
	private JButton btnProximaCI = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	
	/* Methodes */

	private void loadCoeficienteChuvasIDFVO()
	{
	    this.lsLocal = new ArrayList<ItemDataVO>();
	    
		for(CoeficienteChuvasIDFVO oCoefChuva : DrenagemCalc.ARR_COEF_CHUVAS_IDF) {
			ItemDataVO oItem = new ItemDataVO(Integer.toString( oCoefChuva.getOid() ), oCoefChuva.getLocal());
		    this.lsLocal.add(oItem);
		}
	}
	
	private void loadTipoSecaoTubulacao()
	{
	    this.lsTipoSecaoTubulacao = new ArrayList<ItemDataVO>();
	    
		for(ItemDataVO oTipoSecaoTubulacao : DrenagemCalc.ARR_TIPOSECAO_TUBULACAO) {
			ItemDataVO oItem = new ItemDataVO(oTipoSecaoTubulacao.getItemDataId(), oTipoSecaoTubulacao.getDescricao());
		    this.lsTipoSecaoTubulacao.add(oItem);
		}
	}
	
	private void loadCategoriaTubulacao(String tipoSecaoTubulacao)
	{
	    this.lsCategoriaTubulacao = new ArrayList<ItemDataVO>();
	    
		for(ItemDataVO oCategoriaTubulacao : DrenagemCalc.ARR_CATEGORIA_TUBULACAO) {
			String strSectionType = oCategoriaTubulacao.getStrVal();
			if( tipoSecaoTubulacao.equals(strSectionType) ) {
				ItemDataVO oItem = new ItemDataVO(oCategoriaTubulacao.getItemDataId(), oCategoriaTubulacao.getDescricao());
			    this.lsCategoriaTubulacao.add(oItem);
			}
		}
	}
	
	private void loadTubulacaoDrenagem(int iCategoriaTubulacaoId)
	{
	    this.lsTubulacaoDrenagem = new ArrayList<TubulacaoDrenagemVO>();
	    
		for(TubulacaoDrenagemVO oTubulacaoDrenagem : DrenagemCalc.ARR_DIAMETRO_TUBULACAO) {
			if(oTubulacaoDrenagem.getCategoriaTubulacaoId() == iCategoriaTubulacaoId) {
				TubulacaoDrenagemVO oItem = new TubulacaoDrenagemVO(oTubulacaoDrenagem);
				this.lsTubulacaoDrenagem.add(oItem);
			}
		}
	}
	
	private void loadCoefImper()
	{
	    this.lsCoefImper = new ArrayList<ItemDataVO>();
	    
		for(ItemDataVO oCoefImper : DrenagemCalc.ARR_COEFIMPER) {
			ItemDataVO oItem = new ItemDataVO(oCoefImper.getItemDataId(), oCoefImper.getDescricao());
		    this.lsCoefImper.add(oItem);
		}
	}

	/* TABBED PANNELS */
	
	private void initTabPanel()
	{
		Insets insets = this.getInsets();

		int xp = insets.left + 10;
		int yp = insets.top + 10;

		int w = 800;
		int h = 420;
		
		this.tabMainPanel = FormControlUtil.newTabPanel(xp, yp, w, h, JTabbedPane.BOTTOM);
		this.add(this.tabMainPanel);
		
		this.panGeneral = FormControlUtil.newTabPanel(this.tabMainPanel, "Geral", 0, 0, w, h);
		this.panDimensions = FormControlUtil.newTabPanel(this.tabMainPanel, "Dimensoes", 0, 0, w, h);
		this.panOutputPipe = FormControlUtil.newTabPanel(this.tabMainPanel, "Tubulacoes", 0, 0, w, h);
		
		yp += h;
		
		//BUTTONS
		//
		xp = insets.left + AppDefs.PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_FRAME_WIDTH - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W5);
		yp += AppDefs.BUTTON_H20 + AppDefs.SPACE_W5;

		this.btnCancel = FormControlUtil.newButton("Cancelar", AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_CANCELAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnCancel);
		xp -= (AppDefs.BUTTON_W100 + AppDefs.SPACE_W5);

		this.btnOk = FormControlUtil.newButton("Ok", AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_OK, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnOk);
		
	}	
	
	/* INIT */
	
	private void initForm_GeneralPanel()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		// FORM_DATA
	    //
	    String strTipoCI = oEnt1.getTipoCI();																	// _ESGOTO_ / _APLUVIAL_
	    String strSubtipoCI = oEnt1.getSubtipoCI();																// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
	    String strNumeroCI = nf0.format( oEnt1.getNumeroCI() );													// [id_caixa_inspecao]
	    String strProximaCI = nf0.format( oEnt1.getProximaCI() );												// [id_proxima_caixa_inspecao]
	    String strNumEstaca = nf0.format( oEnt1.getNumEstaca() );												// NumEstaca = 2
	    String strDistEstaca = nf3.format( oEnt1.getNumEstaca() );												// DistEstaca = 1.70 m
		String strPV = oEnt1.getPv();																			// PV-A2.1
		int localId = oEnt1.getLocalId();																		// 1001 - RUA DR. MARIO MACHADO
		//String strLocal = oEnt1.getLocal();																	// RUA DR. MARIO MACHADO
		//String strEstaca = oEnt1.getEstaca();																	// 2 + 1.70 m
		String strAreaExterna = nf3.format( oEnt1.getAreaExterna() );											// Area = 0.220 ha
		String strAreaLocal = nf3.format( oEnt1.getAreaLocal() );												// Area = 0.220 ha
		String strAreaTotal = nf3.format( oEnt1.getAreaTotal() );												// Area = 0.220 ha
		String strAreaTotalImp = nf3.format( oEnt1.getAreaTotalImp() );											// Area = 0.220 ha
		String strCoefImper = nf3.format( oEnt1.getCoefImper() );												// CoefImper = 0.80		
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;

		//LABEL
		//
	    this.lblTipoCI = FormControlUtil.newLabel("Tipo CI:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblTipoCI);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblSubtipoCI = FormControlUtil.newLabel("Subtipo CI:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblSubtipoCI);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblNumeroCI = FormControlUtil.newLabel("Numero CI:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblNumeroCI);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblProximaCI = FormControlUtil.newLabel("Proxima CI:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblProximaCI);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblNumEstaca = FormControlUtil.newLabel("Estaca:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblNumEstaca);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblDistEstaca = FormControlUtil.newLabel("Distancia Estaca:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblDistEstaca);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblPV = FormControlUtil.newLabel("PV:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblPV);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblLocal = FormControlUtil.newLabel("Local:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblLocal);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblAreaExterna = FormControlUtil.newLabel("Area externa (ha):", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblAreaExterna);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblAreaLocal = FormControlUtil.newLabel("Area local (ha):", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblAreaLocal);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblAreaTotal = FormControlUtil.newLabel("Area total (ha):", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblAreaTotal);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblAreaTotalImp = FormControlUtil.newLabel("Imp. Area total (ha):", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblAreaTotalImp);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
				
	    this.lblCoefImper = FormControlUtil.newLabel("Coef.Imper.:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panGeneral.add(this.lblCoefImper);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		//CONTROLS
		//
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;
		
		this.txtTipoCI = FormControlUtil.newTextField(strTipoCI, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panGeneral.add(this.txtTipoCI);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;

		this.txtSubtipoCI = FormControlUtil.newTextField(strSubtipoCI, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panGeneral.add(this.txtSubtipoCI);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtNumeroCI = FormControlUtil.newTextField(strNumeroCI, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panGeneral.add(this.txtNumeroCI);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtProximaCI = FormControlUtil.newTextField(strProximaCI, xp, yp, AppDefs.TEXT_W540, AppDefs.TEXT_H20, true, false);
		this.panGeneral.add(this.txtProximaCI);
		xp += (AppDefs.TEXT_W250 + AppDefs.SPACE_W5);

		this.btnProximaCI = FormControlUtil.newButton("Prox.CI >", AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_PROXIMACI, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.panGeneral.add(this.btnProximaCI);
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5;
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtNumEstaca = FormControlUtil.newTextField(strNumEstaca, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.panGeneral.add(this.txtNumEstaca);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtDistEstaca = FormControlUtil.newTextField(strDistEstaca, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.panGeneral.add(this.txtDistEstaca);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtPV = FormControlUtil.newTextField(strPV, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.panGeneral.add(this.txtPV);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		int szLsCoefImper = this.lsCoefImper.size();
		ItemDataVO[] arrCoefImper = this.lsCoefImper.toArray( new ItemDataVO[szLsCoefImper] ); 		
	    this.cbxCoefImper = FormControlUtil.newComboBox(arrCoefImper, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_COEFIMPERTUBULACAO_SELECTED), this);
		this.panGeneral.add(this.cbxCoefImper);
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;
		
		int szLocal = this.lsLocal.size();
		ItemDataVO[] arrLocal = this.lsLocal.toArray( new ItemDataVO[szLocal] ); 
		this.cbxLocalId = FormControlUtil.newComboBox(arrLocal, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_LOCAL_SELECTED), this);
		this.panGeneral.add(this.cbxLocalId);
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;
		
		this.txtAreaExterna = FormControlUtil.newTextField(strAreaExterna, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_AREA_CHANGED), this);
		this.panGeneral.add(this.txtAreaExterna);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtAreaLocal = FormControlUtil.newTextField(strAreaLocal, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_AREA_CHANGED), this);
		this.panGeneral.add(this.txtAreaLocal);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtAreaTotal = FormControlUtil.newTextField(strAreaTotal, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_AREA_CHANGED), this);
		this.panGeneral.add(this.txtAreaTotal);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtAreaTotalImp = FormControlUtil.newTextField(strAreaTotalImp, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_AREA_CHANGED), this);
		this.panGeneral.add(this.txtAreaTotalImp);
		
	}
	
	private void initForm_DimensionsPanel()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		double dProfundidade = - Math.abs( oEnt1.getProfundidade() );		
		
		// FORM_DATA
	    //
	    String strDiametro = nf3.format( oEnt1.getDiametroMeter());													// Diametro = 0.60 (diametro_caixa_inspecao)
		String strProfundidade = nf3.format( dProfundidade );													// Profundidade = 0.60 m
	    String strCT = nf3.format( oEnt1.getCt() );																// CotaTerreno = 2.841 m
	    String strCB = nf3.format( oEnt1.getCb() );																// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
	    String strCotaEntrada = nf3.format( oEnt1.getCotaEntrada() );											// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
	    String strCotaSaida = nf3.format( oEnt1.getCotaSaida() );												// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;

		//LABEL
		//
	    this.lblDiametro = FormControlUtil.newLabel("Diametro:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panDimensions.add(this.lblDiametro);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblProfundidade = FormControlUtil.newLabel("Profundidade:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panDimensions.add(this.lblProfundidade);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblCT = FormControlUtil.newLabel("Cota Terreno:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panDimensions.add(this.lblCT);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblCB = FormControlUtil.newLabel("Cota Fundo:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panDimensions.add(this.lblCB);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblCotaEntrada = FormControlUtil.newLabel("Cota Entrada:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panDimensions.add(this.lblCotaEntrada);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblCotaSaida = FormControlUtil.newLabel("Cota Saida:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panDimensions.add(this.lblCotaSaida);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		//CONTROLS
		//
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;
		
		this.txtDiametro = FormControlUtil.newTextField(strDiametro, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_DIAMETRO_CHANGED), this);
		this.panDimensions.add(this.txtDiametro);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtProfundidade = FormControlUtil.newTextField(strProfundidade, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_PROFUNDIDADE_CHANGED), this);
		this.panDimensions.add(this.txtProfundidade);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtCT = FormControlUtil.newTextField(strCT, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_COTATERRENO_CHANGED), this);
		this.panDimensions.add(this.txtCT);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtCB = FormControlUtil.newTextField(strCB, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panDimensions.add(this.txtCB);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtCotaEntrada = FormControlUtil.newTextField(strCotaEntrada, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panDimensions.add(this.txtCotaEntrada);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtCotaSaida = FormControlUtil.newTextField(strCotaSaida, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panDimensions.add(this.txtCotaSaida);
		
	}
	
	private void initForm_PipesPanel()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		String tmpTipoSecaoTubulacao = oEnt1.getTipoSecaoTubulacao();

		int tmpCategoriaTubulacaoId = oEnt1.getCategoriaTubulacaoId();
		String tmpDescricaoCategoriaTubulacao = oEnt1.getDescricaoCategoriaTubulacao();		
		
		double dProfundidade = - Math.abs( oEnt1.getProfundidade() );
		double dVazao = oEnt1.getVazao();
		double dVazaoAcumulada = oEnt1.getVazaoAcumulada();
		
		// FORM_DATA
	    //
	    String strTipoSecaoTubulacao = tmpTipoSecaoTubulacao;													// TipoSecaoTubulacao = SECAO_CIRCULAR / SECAO_RETANGULAR
	    int categoriaTubulacaoId = tmpCategoriaTubulacaoId;														// CategoriaTubulacaoId = 1101-CONCRETO_CLASSE_PA-1 / 1102-CONCRETO_CLASSE_PA-2 / 1103-CONCRETO_CLASSE_PA-3 / 1104-PEAD
	    String strDescricaoCategoriaTubulacao = tmpDescricaoCategoriaTubulacao;									// DescricaoCategoriaTubulacao = CONCRETO_CLASSE_PA-1 / CONCRETO_CLASSE_PA-2 / CONCRETO_CLASSE_PA-3 / PEAD
	    String strDiametroTubulacao = nf0.format( oEnt1.getDiametroTubulacaoMeter() / 1000.0);						// DiametroTubulacao = 250 mm (0.25 m)
	    String strQtdTubulacao = nf0.format( oEnt1.getQtdTubulacao() );											// QtdTubulacao = 1, 2, 3, 4...
	    String strDeclividade = nf3.format( oEnt1.getDeclividade());											// Declividade = 0.00160
		String strCoefImper = nf3.format( oEnt1.getCoefImper() );												// CoefImper = 0.80
		String strProfundidade = nf3.format( dProfundidade );													// Profundidade = 0.60 m		
		String strVazao = nf3.format( dVazao );																	// Vazao = 3.0 m3/s		
		String strVazaoAcumulada = nf3.format( dVazaoAcumulada );												// VazaoAcumulada = SOMA(Vazao) = 12.0 m3/s		
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;

		//LABEL
		//
	    this.lblTipoSecaoTubulacao = FormControlUtil.newLabel("Tipo Secao:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblTipoSecaoTubulacao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblCategoriaTubulacao = FormControlUtil.newLabel("Categoria Tubulacao:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblCategoriaTubulacao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblDiametroTubulacao = FormControlUtil.newLabel("Diam.Tubulacao:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblDiametroTubulacao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblQtdTubulacao = FormControlUtil.newLabel("Qtd.Tubulacao:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblQtdTubulacao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblDeclividade = FormControlUtil.newLabel("Declividade:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblDeclividade);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblVazao = FormControlUtil.newLabel("Vazao:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblVazao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblVazaoAcumulada = FormControlUtil.newLabel("Vazao Acumulada:", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panOutputPipe.add(this.lblVazaoAcumulada);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		//CONTROLS
		//
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;

		int szLsTipoSecaoTubulacao = this.lsTipoSecaoTubulacao.size();
		ItemDataVO[] arrTipoSecaoTubulacao = this.lsTipoSecaoTubulacao.toArray( new ItemDataVO[szLsTipoSecaoTubulacao] ); 		
	    this.cbxTipoSecaoTubulacao = FormControlUtil.newComboBox(arrTipoSecaoTubulacao, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_TIPOSECAOTUBULACAO_SELECTED), this);
		this.panOutputPipe.add(this.cbxTipoSecaoTubulacao);
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;

		int szLsCategoriaTubulacao = this.lsCategoriaTubulacao.size();
		ItemDataVO[] arrCategoriaTubulacao = this.lsCategoriaTubulacao.toArray( new ItemDataVO[szLsCategoriaTubulacao] ); 		
	    this.cbxCategoriaTubulacaoId = FormControlUtil.newComboBox(arrCategoriaTubulacao, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_CATEGORIATUBULACAO_SELECTED), this);
		this.panOutputPipe.add(this.cbxCategoriaTubulacaoId);
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;

		int szLsTubulacaoDrenagem = this.lsTubulacaoDrenagem.size();
		TubulacaoDrenagemVO[] arrTubulacaoDrenagem = this.lsTubulacaoDrenagem.toArray( new TubulacaoDrenagemVO[szLsTubulacaoDrenagem] ); 		
	    this.cbxDiametroTubulacao = FormControlUtil.newComboBox(arrTubulacaoDrenagem, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_DIAMETROTUBULACAO_SELECTED), this);
		this.panOutputPipe.add(this.cbxDiametroTubulacao);
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;
		
		this.txtQtdTubulacao = FormControlUtil.newTextField(strQtdTubulacao, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panOutputPipe.add(this.txtQtdTubulacao);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtDeclividade = FormControlUtil.newTextField(strDeclividade, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panOutputPipe.add(this.txtDeclividade);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtVazao = FormControlUtil.newTextField(strVazao, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panOutputPipe.add(this.txtVazao);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtVazaoAcumulada = FormControlUtil.newTextField(strVazaoAcumulada, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.panOutputPipe.add(this.txtVazaoAcumulada);		
	}
	
	private void initForm()
	{
		this.setLayout(null);

		String tmpTipoSecaoTubulacao = oEnt1.getTipoSecaoTubulacao();
		int tmpCategoriaTubulacaoId = oEnt1.getCategoriaTubulacaoId();

		this.loadCoeficienteChuvasIDFVO();		
		this.loadTipoSecaoTubulacao();
		this.loadCoefImper();
		this.loadCategoriaTubulacao(tmpTipoSecaoTubulacao);		
		this.loadTubulacaoDrenagem(tmpCategoriaTubulacaoId);		
		
		this.initTabPanel();

		this.initForm_GeneralPanel();
		this.initForm_DimensionsPanel();
		this.initForm_PipesPanel();
	}
	
//Public 
	
	public PropriedadeCaixaInspecaoDrenagemPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(CadCaixaInspecaoDrenagem oEnt1)
	{
		this.oEnt1 = oEnt1;
		
		initForm();
	}
	
	/* Methodes */
	
	public boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		// FORM_DATA
		//
		ItemDataVO oCoefImper = (ItemDataVO)this.cbxCoefImper.getSelectedItem(); 
		ItemDataVO oLocal = (ItemDataVO)this.cbxLocalId.getSelectedItem(); 
		ItemDataVO oTipoSecaoTubulacao = (ItemDataVO)this.cbxTipoSecaoTubulacao.getSelectedItem(); 
		ItemDataVO oCategoriaTubulacao = (ItemDataVO)this.cbxCategoriaTubulacaoId.getSelectedItem(); 
		//
		TubulacaoDrenagemVO oDiametroTubulacao = (TubulacaoDrenagemVO)this.cbxDiametroTubulacao.getSelectedItem();
		
		String strLocalId = oLocal.getItemDataId();																// 1001 - RUA DR. MARIO MACHADO
		String strLocal = oLocal.getDescricao();
		//
		String strTipoSecaoTubulacao = oTipoSecaoTubulacao.getDescricao();		
		//
		String strCategoriaTubulacaoId = oCategoriaTubulacao.getItemDataId();
		String strCategoriaTubulacao = oCategoriaTubulacao.getDescricao();				
		//
		String strTubulacaoDrenagem = oDiametroTubulacao.getDescricao();
		String strQtdTubulacao = this.txtQtdTubulacao.getText();		
		//
		String strNumEstaca = this.txtNumEstaca.getText();														// 2
		String strDistEstaca = this.txtDistEstaca.getText();													// +7.50 m
		String strPV = this.txtPV.getText();																	// PV-A2.1
		String strAreaLocal = this.txtAreaLocal.getText();														// AreaLocal = 0.220 ha
		String strDiametro = this.txtDiametro.getText();														// Diametro = 0.60 m
		String strDeclividade = this.txtDeclividade.getText();													// Declividade = 0.00160
		String strCoefImper = oCoefImper.getItemDataId();														// CoefImper = 0.80
		String strProfundidade = this.txtProfundidade.getText();												// Profundidade = 0.70 m
		String strCotaTerreno = this.txtCT.getText();															// CotaTerreno = 2.841 m
		
		if( "".equals(strNumEstaca) )
			errmsg += "Numero da Estaca; ";

		if( "".equals(strDistEstaca) )
			errmsg += "Distancia da Estaca; ";

		if( "".equals(strPV) )
			errmsg += "PV; ";

		if( "".equals(strAreaLocal) )
			errmsg += "Area local; ";

		if( "".equals(strDiametro) )
			errmsg += "Diametro; ";
		
		if( "".equals(strDeclividade) )
			errmsg += "Declividade; ";
		
		if( "".equals(strCoefImper) )
			errmsg += "Coef.Imper.; ";
		
		if( "".equals(strProfundidade) )
			errmsg += "Profundidade; ";
		
		if( "".equals(strCotaTerreno) )
			errmsg += "Cota Terreno; ";
		
		if( "".equals(strQtdTubulacao) )
			errmsg += "Qtd.Tubulacao; ";
		
		if( !"".equals(errmsg) ) {
			errmsg = "ERR: Campos obigatorios nao informados: " + errmsg;
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;
		}

		// CONVERT_DATA
		//
		int iLocalId = StringUtil.safeInt(oLocal.getItemDataId());												// 1001 - RUA DR. MARIO MACHADO
		//
		int iCategoriaTubulacaoId = StringUtil.safeInt(oCategoriaTubulacao.getItemDataId());
		double dDiamNominal = oDiametroTubulacao.getDiamNominalMeter();	
		//int iQtdTubulacao = StringUtil.safeInt(strQtdTubulacao);
		int iQtdTubulacao = 1;
		//
		int iNumEstaca = StringUtil.safeInt(strNumEstaca);
		double dDistEstaca = StringUtil.safeDbl(nf6, strDistEstaca);
		double dAreaLocal = StringUtil.safeDbl(nf6, strAreaLocal);
		double dDiametroMeter = StringUtil.safeDbl(nf6, strDiametro);
	    double dDeclividade = StringUtil.safeDbl(nf6, strDeclividade);
		double dCoefImper = StringUtil.safeDbl(nf6, strCoefImper);
		double dProfundidade = Math.abs( StringUtil.safeDbl(nf6, strProfundidade) );
		double dCt = StringUtil.safeDbl(nf6, strCotaTerreno);

		if(iNumEstaca < 0.0) {
			errmsg = "ERR: Numero da estaca deve ser maior ou igual a 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if(dDistEstaca < 0.0) {
			errmsg = "ERR: Distancia ate a estaca deve ser maior ou igual a 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if(dAreaLocal < 0.0) {
			errmsg = "ERR: Area deve ser maior ou igual a 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if(dDiametroMeter <= AppDefs.MATHPREC_MIN) {
			errmsg = "ERR: Diametro deve ser maior que 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if(dDiamNominal <= AppDefs.MATHPREC_MIN) {
			errmsg = "ERR: Diametro nominal deve ser maior que 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
//		if(dDeclividade >= AppDefs.MATHPREC_MIN) {
//			errmsg = "ERR: Declividade deve ser inferior ou igual a 0";
//			
//			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
//			return false;			
//		}
		
		if(dCoefImper < 0.0) {
			errmsg = "ERR: Coeficiente de impermeabilizacao deve ser maior que 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if(dProfundidade < AppDefs.MATHPREC_MIN) {
			errmsg = "ERR: Profundidade deve ser maior que 0";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}

		// UPDATE_DATA
		//
		this.localId = iLocalId;
		this.local = oLocal.getDescricao();
		//
		this.tipoSecaoTubulacao = oTipoSecaoTubulacao.getDescricao();		
		//
		this.categoriaTubulacaoId = iCategoriaTubulacaoId;
		this.descricaoCategoriaTubulacao = strCategoriaTubulacao;				
		//
		this.diametroTubulacaoMeter =  dDiamNominal;
		this.qtdTubulacao = iQtdTubulacao;		
		//
		this.numEstaca = iNumEstaca;
		this.distEstaca = dDistEstaca;
		this.pv = strPV;
		this.localId = iLocalId;
		this.local = strLocal;
		this.estaca = String.format("%s + %s m", this.numEstaca, this.distEstaca);
		this.areaLocal = dAreaLocal;
		this.areaTotal = this.areaExterna + dAreaLocal;
		this.diametroMeter = dDiametroMeter;
	    this.declividade = dDeclividade;
		this.coefImper = dCoefImper;
		this.profundidade = - dProfundidade;
		this.ct = dCt;
		
		// NUM_PV
		//
		String strNumPv = StringUtil.getOnlyNumbers(this.pv);
		int numPv = StringUtil.safeInt(strNumPv);

		CadCaixaInspecaoDrenagem.setSeqId(numPv);		
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
		if( this.validateForm() ) {
			double dProfundidade = - Math.abs( this.profundidade );
			double dDiametroTubulacaoMeter = this.diametroTubulacaoMeter;
			double dRaioTubulacaoMeter = dDiametroTubulacaoMeter / 2.0;
			double dCt = this.ct;
	    	//double dCb = dCt - dProfundidade;
	    	double dCb = dCt - Math.abs( dProfundidade );
	    	double dCotaEntrada = dCb + dRaioTubulacaoMeter + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
	    	double dCotaSaida = dCb + dRaioTubulacaoMeter;
			
			// UPDATE_DATA
			//
	    	this.oEnt1.setTipoSecaoTubulacao(this.tipoSecaoTubulacao);		
	    	this.oEnt1.setCategoriaTubulacaoId(this.categoriaTubulacaoId);
			this.oEnt1.setDescricaoCategoriaTubulacao(this.descricaoCategoriaTubulacao);				
			this.oEnt1.setDiametroTubulacaoMeter(this.diametroTubulacaoMeter);
			this.oEnt1.setQtdTubulacao(this.qtdTubulacao);		
			//
			this.oEnt1.setNumEstaca(this.numEstaca);
			this.oEnt1.setDistEstaca(this.distEstaca);
	    	this.oEnt1.setPv(this.pv);
	    	this.oEnt1.setLocalId(this.localId);
	    	this.oEnt1.setLocal(this.local);
	    	this.oEnt1.setNumEstaca(this.numEstaca);
	    	this.oEnt1.setDistEstaca(this.distEstaca);
	    	this.oEnt1.setEstaca(this.estaca);
	    	this.oEnt1.setAreaLocal(this.areaLocal);
	    	this.oEnt1.setAreaTotal(this.areaExterna + this.areaLocal);
			this.oEnt1.setDiametroMeter(this.diametroMeter);
			this.oEnt1.setDeclividade(this.declividade);
			this.oEnt1.setCoefImper(this.coefImper);
			this.oEnt1.setProfundidade(this.profundidade);
			this.oEnt1.setCt(dCt);
			this.oEnt1.setCb(dCb);
			this.oEnt1.setCotaEntrada(dCotaEntrada);
			this.oEnt1.setCotaSaida(dCotaSaida);
						
			GeomPoint3d ptIns3d = this.oEnt1.getPtIns();
			double xPtIns = ptIns3d.getX();
			double yPtIns = ptIns3d.getY();
			
			GeomPoint3d newPtIns3d = new GeomPoint3d(xPtIns, yPtIns, this.ct);
			this.oEnt1.setPtIns(newPtIns3d);
			
			rscode = AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_OK;			
			this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));
				
			this.getParentFrame().dispose();
		}
	}
		
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_CANCELAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
	public void doActionProfundidade(ActionEvent e) {
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String strProfundidade = this.txtProfundidade.getText();
		double dProfundidade = - Math.abs( StringUtil.safeDbl(nf6, strProfundidade) );
		double dDiametroTubulacaoMeter = oEnt1.getDiametroTubulacaoMeter();
		double dRaioTubulacaoMeter = dDiametroTubulacaoMeter / 2.0;
		double dCt = oEnt1.getCt();
    	//double dCb = dCt - dProfundidade;
    	double dCb = dCt - Math.abs( dProfundidade );
    	double dCotaEntrada = dCb + dRaioTubulacaoMeter + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
    	double dCotaSaida = dCb + dRaioTubulacaoMeter;
    	//
    	this.setCb(dCb);
    	this.setCotaEntrada(dCotaEntrada);
    	this.setCotaSaida(dCotaSaida);
    	
		// FORM_DATA
	    //
	    String strCB = nf3.format( dCb );
	    String strCotaEntrada = nf3.format( dCotaEntrada );
	    String strCotaSaida = nf3.format( dCotaSaida );
	    //
	    this.txtCB.setText(strCB);
	    this.txtCotaEntrada.setText(strCotaEntrada);
	    this.txtCotaSaida.setText(strCotaSaida);
	}
	
	public void doActionCotaTerreno(ActionEvent e) {
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String strCotaTerreno = this.txtCT.getText();										// CotaTerreno = 2.841 m
		double dCt = StringUtil.safeDbl(nf6, strCotaTerreno);
		//
		double dProfundidade = - Math.abs( oEnt1.getProfundidade() );
		double dDiametroTubulacaoMeter = oEnt1.getDiametroTubulacaoMeter() / 1000.0;
		double dRaioTubulacaoMeter = dDiametroTubulacaoMeter / 2.0;
    	//double dCb = dCt - dProfundidade;
    	double dCb = dCt - Math.abs( dProfundidade );
    	double dCotaEntrada = dCb + dRaioTubulacaoMeter + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
    	double dCotaSaida = dCb + dRaioTubulacaoMeter;
    	//
    	this.setCb(dCb);
    	this.setCotaEntrada(dCotaEntrada);
    	this.setCotaSaida(dCotaSaida);
    	
		// FORM_DATA
	    //
	    String strCB = nf3.format( dCb );
	    String strCotaEntrada = nf3.format( dCotaEntrada );
	    String strCotaSaida = nf3.format( dCotaSaida );
	    //
	    this.txtCB.setText(strCB);
	    this.txtCotaEntrada.setText(strCotaEntrada);
	    this.txtCotaSaida.setText(strCotaSaida);
	    
	    GeomPoint3d ptIns = this.oEnt1.getPtIns();
	    double xPtIns = ptIns.getX();
	    double yPtIns = ptIns.getY();
	    
	    GeomPoint3d ptResult = new GeomPoint3d(xPtIns, yPtIns, dCt);
	    this.oEnt1.setPtIns(ptResult);
	}
	
	public void doActionCategoriaTubulacao(ActionEvent e) {
		ItemDataVO oCategoriaTubulacao = (ItemDataVO)this.cbxCategoriaTubulacaoId.getSelectedItem(); 
		//
		this.categoriaTubulacaoId = oCategoriaTubulacao.getItemDataIdVal();												// 1001 - RUA DR. MARIO MACHADO
		this.descricaoCategoriaTubulacao = oCategoriaTubulacao.getDescricao();
		
	    this.oEnt1.setCategoriaTubulacaoId(this.categoriaTubulacaoId);
	    this.oEnt1.setDescricaoCategoriaTubulacao(this.descricaoCategoriaTubulacao);
	}

	public void doActionDiametroTubulacao(ActionEvent e) {
		TubulacaoDrenagemVO oDiametroTubulacao = (TubulacaoDrenagemVO)this.cbxDiametroTubulacao.getSelectedItem(); 
		//
		double dDiametroTubulacaoMeter = oDiametroTubulacao.getDiamNominalMeter();
		this.oEnt1.setDiametroTubulacaoMeter(dDiametroTubulacaoMeter);
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_OK) {
			String warnmsg = String.format("Action - OK: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

			doActionOk(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_CANCELAR) {
			String warnmsg = String.format("Action - CENCELAR: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

			doActionCancelar(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_PROFUNDIDADE_CHANGED) {
			String warnmsg = String.format("Action - PROFUNDIDADE: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

			doActionProfundidade(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_COTATERRENO_CHANGED) {
			String warnmsg = String.format("Action - COTATERRENO: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

			doActionCotaTerreno(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_CATEGORIATUBULACAO_SELECTED) {
			String warnmsg = String.format("Action - CATEGORIATUBULACAO: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

			doActionCategoriaTubulacao(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_CAIXA_INSPECAO_DRENAGEM_DIAMETROTUBULACAO_SELECTED) {
			String warnmsg = String.format("Action - DIAMETROTUBULACAO: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL09, warnmsg, this.getClass());

			doActionDiametroTubulacao(e);						
		}
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

	/* COMPONENT_EVENT */
	
	@Override
	public void componentResized(ComponentEvent e) { }

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }

	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) { }

	/* Getters/Setters */

	public CadCaixaInspecaoDrenagem getCaixaInspecao() {
		return oEnt1;
	}

	public int getRSCode() {
		return rscode;
	}

	public String getTipoCI() {
		return tipoCI;
	}

	public void setTipoCI(String tipoCI) {
		this.tipoCI = tipoCI;
	}

	public String getSubtipoCI() {
		return subtipoCI;
	}

	public void setSubtipoCI(String subtipoCI) {
		this.subtipoCI = subtipoCI;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public int getProximaCI() {
		return proximaCI;
	}

	public void setProximaCI(int proximaCI) {
		this.proximaCI = proximaCI;
	}

	public int getNumEstaca() {
		return numEstaca;
	}

	public void setNumEstaca(int numEstaca) {
		this.numEstaca = numEstaca;
	}

	public double getDistEstaca() {
		return distEstaca;
	}

	public void setDistEstaca(double distEstaca) {
		this.distEstaca = distEstaca;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEstaca() {
		return estaca;
	}

	public void setEstaca(String estaca) {
		this.estaca = estaca;
	}

	public double getDiametroMeter() {
		return diametroMeter;
	}

	public void setDiametroMeter(double diametroMeter) {
		this.diametroMeter = diametroMeter;
	}

	public double getVazao() {
		return vazao;
	}

	public void setVazao(double vazao) {
		this.vazao = vazao;
	}

	public double getVazaoAcumulada() {
		return vazaoAcumulada;
	}

	public void setVazaoAcumulada(double vazaoAcumulada) {
		this.vazaoAcumulada = vazaoAcumulada;
	}

	public int getQtdTubulacao() {
		return qtdTubulacao;
	}

	public void setQtdTubulacao(int qtdTubulacao) {
		this.qtdTubulacao = qtdTubulacao;
	}

	public double getDiametroTubulacaoMeter() {
		return diametroTubulacaoMeter;
	}

	public void setDiametroTubulacaoMeter(double diametroTubulacaoMeter) {
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
	}

	public double getDeclividade() {
		return declividade;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}

	public double getCoefImper() {
		return coefImper;
	}

	public void setCoefImper(double coefImper) {
		this.coefImper = coefImper;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getCt() {
		return ct;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public double getCb() {
		return cb;
	}

	public void setCb(double cb) {
		this.cb = cb;
	}

	public double getCotaEntrada() {
		return cotaEntrada;
	}

	public void setCotaEntrada(double cotaEntrada) {
		this.cotaEntrada = cotaEntrada;
	}

	public double getCotaSaida() {
		return cotaSaida;
	}

	public void setCotaSaida(double cotaSaida) {
		this.cotaSaida = cotaSaida;
	}

	public double getAreaExterna() {
		return areaExterna;
	}

	public double getAreaLocal() {
		return areaLocal;
	}

	public double getAreaTotal() {
		return areaTotal;
	}

	public double getAreaTotalImp() {
		return areaTotalImp;
	}

	public void setAreaExterna(double areaExterna) {
		this.areaExterna = areaExterna;
	}

	public void setAreaLocal(double areaLocal) {
		this.areaLocal = areaLocal;
	}

	public void setAreaTotal(double areaTotal) {
		this.areaTotal = areaTotal;
	}

	public void setAreaTotalImp(double areaTotalImp) {
		this.areaTotalImp = areaTotalImp;
	}

	public int getCategoriaTubulacaoId() {
		return categoriaTubulacaoId;
	}

	public void setCategoriaTubulacaoId(int categoriaTubulacaoId) {
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	}

	public String getDescricaoCategoriaTubulacao() {
		return descricaoCategoriaTubulacao;
	}

	public void setDescricaoCategoriaTubulacao(String descricaoCategoriaTubulacao) {
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	}

	public ArrayList<ItemDataVO> getLsLocal() {
		return lsLocal;
	}

	public void setLsLocal(ArrayList<ItemDataVO> lsLocal) {
		this.lsLocal = lsLocal;
	}

	public ArrayList<ItemDataVO> getLsCategoriaTubulacao() {
		return lsCategoriaTubulacao;
	}

	public void setLsCategoriaTubulacao(ArrayList<ItemDataVO> lsCategoriaTubulacao) {
		this.lsCategoriaTubulacao = lsCategoriaTubulacao;
	}

	public String getTipoSecaoTubulacao() {
		return tipoSecaoTubulacao;
	}

	public void setTipoSecaoTubulacao(String tipoSecaoTubulacao) {
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
	}
	
}
