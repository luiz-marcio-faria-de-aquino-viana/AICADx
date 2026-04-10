/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PropriedadePontoEletricaPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/01/2026
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
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class PropriedadePontoEletricaPanel extends BasePanel
{
//Private
	private CadDocumentDef doc = null;	
    private CadPontoEletrica oEnt1 = null;    

	private CadParamEletricoOData oCurrParamEletrico = null;
	private int posCurrParamEletrico = AppDefs.NULL_INT;
	
	private int rscode = AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_NONE;
	
	/* PROPRIEDADES_PONTO_ELETRICA */

    private String tipo;						// TIPO = EQUADRO, EILUMINACAO, ECARGA, ECOMANDO, ... 
    private String nomeQuadro;
    private String quadroOrigem;
    private double potencia;
    private double potenciaDemandada;
    private String sistemaFase;					// SISTEMA_FASE = F+N, F+N+T, 2F, 3F, 3F+N, 3F+N+T, ...
	private String circuito;
	private String comando;

    private ArrayList<ItemDataVO> lsQuadroOrigem = null;
    private ArrayList<ItemDataVO> lsSistemaFase = null;
    
	//LABELS
	//
    private JLabel lblTipo = null;
    private JLabel lblNomeQuadro = null;
    private JLabel lblQuadroOrigem = null;
    private JLabel lblPotencia = null;
    private JLabel lblPotenciaDemandada = null;
    private JLabel lblSistemaFase = null;
    private JLabel lblCircuito = null;
    private JLabel lblComando = null;
    //
    private JLabel lblMessage = null;
    
	//CONTROLS
	//
	private JTextField txtTipo = null;
	private JTextField txtNomeQuadro = null;
	private JComboBox<ItemDataVO> cbxQuadroOrigemId = null;
	private JTextField txtPotencia = null;
	private JTextField txtPotenciaDemandada = null;
	private JComboBox<ItemDataVO> cbxSistemaFaseId = null;
	private JTextField txtCircuito = null;
	private JTextField txtComando = null;
	
	//BUTTON
	//
	private JButton btnAnterior = null;
	private JButton btnProximo = null;
	//private JButton btnGravar = null;
	private JButton btnFechar = null;
	
	/* Methodes */

	private void resetMessage() {
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		int sz = this.oEnt1.getSzLsParamEletrico();
		
		int currItem = this.posCurrParamEletrico + 1;
		String msg = String.format("Parametro atual disponivel em tela para atualizacao: %s de %s", 
			nf0.format( currItem ),
			nf0.format( sz ) );
		this.lblMessage.setText(msg);
	}
	
	private void showMessage(String msg) {
		this.lblMessage.setText(msg);
	}
	
	private CadParamEletricoOData loadCurrParamEletrico(int pos)
	{		
		if(pos < 0) pos = 0;

		int sz = this.oEnt1.getSzLsParamEletrico();
		if(pos >= sz) pos = sz - 1;
		
		//FORM_DATA
		//
		CadParamEletricoOData oResult = oEnt1.getParamEletricoAt(pos);
		
		this.tipo = oResult.getTipo();
	    this.nomeQuadro = oResult.getNomeQuadro();
	    this.quadroOrigem = oResult.getQuadroOrigem();
	    this.potencia = oResult.getPotencia();
	    this.potenciaDemandada = oResult.getPotenciaDemandada();
	    this.sistemaFase = oResult.getSistema();
	    this.circuito = oResult.getCircuito();
	    this.comando = oResult.getComando();		
		
		return oResult;
	}

	private void loadAllQuadroOrigem(CadDocumentDef doc)
	{
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
	    this.lsQuadroOrigem = new ArrayList<ItemDataVO>();
	    
		ItemDataVO oItem = new ItemDataVO("-1", "-- Selecione --");
		this.lsQuadroOrigem.add(oItem);	    
	    
		CadEntity[] arrPontoEletrica = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODELINSEREPONTO);
		for(CadEntity ent : arrPontoEletrica) {
			CadPontoEletrica oEnt = (CadPontoEletrica)ent;
			
			int sz = oEnt.getSzLsParamEletrico();
			for(int i = 0; i < sz; i++) {
				CadParamEletricoOData oParam = oEnt.getParamEletricoAt(i);
				if(oParam != null) {
					String tip = oParam.getTipo();
					if( AppDefs.FIA_S_QUADRO.equals(tip) ) {
						String strNomeQuadro = oParam.getNomeQuadro(); 
					
						oItem = new ItemDataVO(strNomeQuadro, strNomeQuadro);
						this.lsQuadroOrigem.add(oItem);
					}
				}
			}
		}
	}

	private void loadAllSistemaFase(CadDocumentDef doc)
	{
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
	    this.lsSistemaFase = new ArrayList<ItemDataVO>();
	    for(ItemDataVO oItem : AppDefs.ARR_SISTEMA_FASE) {
			this.lsSistemaFase.add(oItem);
	    }
	}

	/* INIT */
	
	private void initBasicData(CadDocumentDef doc, CadPontoEletrica oEnt1)
	{
		this.doc = doc;
		this.oEnt1 = oEnt1;

		this.posCurrParamEletrico = 0;
		this.oCurrParamEletrico = this.loadCurrParamEletrico(this.posCurrParamEletrico);
		
		this.loadAllQuadroOrigem(this.doc);
		this.loadAllSistemaFase(this.doc);
	}
		
	private void initData()
	{
		if(this.posCurrParamEletrico < 0)
			this.posCurrParamEletrico = 0;

		int sz = this.oEnt1.getSzLsParamEletrico();
		if(this.posCurrParamEletrico >= sz)
			this.posCurrParamEletrico = sz - 1;

		this.oCurrParamEletrico = this.loadCurrParamEletrico(this.posCurrParamEletrico);
	}
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		//LISTA: QUADRO_ORIGEM
		//
		int szLsQuadroOrigem = this.lsQuadroOrigem.size();
		ItemDataVO[] arrQuadroOrigem = this.lsQuadroOrigem.toArray( new ItemDataVO[szLsQuadroOrigem] ); 		

		//LISTA: SISTEMA_FASE
		//
		int szLsSistemaFase = this.lsSistemaFase.size();
		ItemDataVO[] arrSistemaFase = this.lsSistemaFase.toArray( new ItemDataVO[szLsSistemaFase] ); 		

		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;

		//LABEL
		//
	    this.lblTipo = FormControlUtil.newLabel("Tipo: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblTipo);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblTipo = FormControlUtil.newLabel("Nome do quadro: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblTipo);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblQuadroOrigem = FormControlUtil.newLabel("Quadro origem: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblQuadroOrigem);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblPotencia = FormControlUtil.newLabel("Potencia (VA): ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblPotencia);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblPotenciaDemandada = FormControlUtil.newLabel("Potencia demandada (VA): ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblPotenciaDemandada);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblSistemaFase = FormControlUtil.newLabel("Sistema de fase: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblSistemaFase);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblCircuito = FormControlUtil.newLabel("Circuito: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblCircuito);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblComando = FormControlUtil.newLabel("Comando: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblComando);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblMessage = FormControlUtil.newLabel("", xp, yp, AppDefs.LABEL_W600, AppDefs.LABEL_H20, true);
		this.add(this.lblMessage);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		//CONTROLS
		//
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;
		
		this.txtTipo = FormControlUtil.newTextField(this.tipo, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, false);
		this.add(this.txtTipo);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;

		boolean bNomeQuadroEditable = AppDefs.FIA_S_QUADRO.equals(this.tipo);

		this.txtNomeQuadro = FormControlUtil.newTextField(this.nomeQuadro, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, bNomeQuadroEditable);
		this.add(this.txtNomeQuadro);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;

	    this.cbxQuadroOrigemId = FormControlUtil.newComboBox(arrQuadroOrigem, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_QUADROORIGEM_CHANGED), this);
		this.add(this.cbxQuadroOrigemId);
		FormControlUtil.setCbx(this.cbxQuadroOrigemId, this.lsQuadroOrigem, this.quadroOrigem);
		int pos = this.cbxQuadroOrigemId.getSelectedIndex();
		if(pos == -1) {
			this.cbxQuadroOrigemId.setSelectedIndex(0);
		}
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;
		
		String strPotencia = nf3.format(this.potencia);
		this.txtPotencia = FormControlUtil.newTextField(strPotencia, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtPotencia);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		String strPotenciaDemandada = nf3.format(this.potenciaDemandada);
		this.txtPotenciaDemandada = FormControlUtil.newTextField(strPotenciaDemandada, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtPotenciaDemandada);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
	    this.cbxSistemaFaseId = FormControlUtil.newComboBox(arrSistemaFase, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_SISTEMAFASE_CHANGED), this);
		this.add(this.cbxSistemaFaseId);
		FormControlUtil.setCbx(this.cbxSistemaFaseId, this.lsSistemaFase, this.sistemaFase);		
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;

		this.txtCircuito = FormControlUtil.newTextField(this.circuito, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtCircuito);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtComando = FormControlUtil.newTextField(this.comando, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtComando);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;

		//BUTTON
		//
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		xp = insets.left + AppDefs.SPACE_W5;

		this.btnAnterior = FormControlUtil.newButton("< Anterior", AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_ANTERIOR, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);  
		this.add(this.btnAnterior);
		xp += AppDefs.BUTTON_W150 + AppDefs.SPACE_W5;

		this.btnProximo = FormControlUtil.newButton("Proximo >", AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_PROXIMO, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);  
		this.add(this.btnProximo);
		//xp += AppDefs.BUTTON_W150 + AppDefs.SPACE_W5;

		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5 + AppDefs.TEXT_W600 - AppDefs.LABEL_W150;

		this.btnFechar = FormControlUtil.newButton("Fechar", AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_FECHAR, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);  
		this.add(this.btnFechar);
		//xp -= (AppDefs.BUTTON_W100 + AppDefs.SPACE_W5);

		//this.btnGravar = FormControlUtil.newButton("Gravar", AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_GRAVAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);  
		//this.add(this.btnGravar);

		this.resetMessage();
	}
		
	private void updateForm()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		String strPotencia = nf3.format(this.potencia);
		String strPotenciaDemandada = nf3.format(this.potenciaDemandada);
		
		//FORM_DATA
		//
	    this.txtTipo.setText( this.tipo );
	    this.txtNomeQuadro.setText( this.nomeQuadro );
	    this.txtPotencia.setText( strPotencia );
	    this.txtPotenciaDemandada.setText( strPotenciaDemandada );
	    this.txtCircuito.setText( this.circuito );
	    this.txtComando.setText( this.comando );

		FormControlUtil.setCbx(this.cbxQuadroOrigemId, this.lsQuadroOrigem, this.quadroOrigem);
		int pos = this.cbxQuadroOrigemId.getSelectedIndex();
		if(pos == -1) {
			this.cbxQuadroOrigemId.setSelectedIndex(0);
		}
		
		FormControlUtil.setCbx(this.cbxSistemaFaseId, this.lsSistemaFase, this.sistemaFase);

		this.resetMessage();
	}
	
	private boolean saveForm()
	{
		if( this.oCurrParamEletrico == null ) return false;		

		if( !this.validateForm() ) return false;
		
		this.oCurrParamEletrico.setNomeQuadro(this.nomeQuadro);
		this.oCurrParamEletrico.setQuadroOrigem(this.quadroOrigem);
		this.oCurrParamEletrico.setPotencia(this.potencia);
		this.oCurrParamEletrico.setPotenciaDemandada(this.potenciaDemandada);
		this.oCurrParamEletrico.setSistema(this.sistemaFase);
		this.oCurrParamEletrico.setCircuito(this.circuito);
		this.oCurrParamEletrico.setComando(this.comando);
		
		return true;
	}
	
//Public 
	
	public PropriedadePontoEletricaPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(CadDocumentDef doc, CadPontoEletrica oEnt1)
	{
		this.initBasicData(doc, oEnt1);
		this.initData();
		this.initForm();
	}
	
	/* Methodes */
	
	public boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		// FORM_DATA
		//
		ItemDataVO oQuadroOrigem = (ItemDataVO)this.cbxQuadroOrigemId.getSelectedItem(); 
		ItemDataVO oSistemaFase = (ItemDataVO)this.cbxSistemaFaseId.getSelectedItem(); 

		String strNomeQuadro = ""; 
		if(this.txtNomeQuadro != null) {
			strNomeQuadro = this.txtNomeQuadro.getText(); 
		}
	    String strPotencia = this.txtPotencia.getText();
	    String strPotenciaDemandada = this.txtPotenciaDemandada.getText();
	    String strCircuito = this.txtCircuito.getText();
	    String strComando = this.txtComando.getText();		

		double dPotencia = StringUtil.safeDbl(nf6, strPotencia);
		double dPotenciaDemandada = StringUtil.safeDbl(nf6, strPotenciaDemandada);
	    
		//boolean bHasQuadroOrigem = false;

		// VALIDATE: EMPTY_FIELD
	    //
		//if( AppDefs.FIA_S_QUADRO.equals(this.tipo) ) {
		//	if( StringUtil.isEmpty(strNomeQuadro) )
		//		errmsg += "Nome do quadro; ";
		//}

		//if( !AppDefs.FIA_S_QUADRO.equals(this.tipo) ) {
		//	if( ( oQuadroOrigem == null ) || ( "-1".equals(oQuadroOrigem.getItemDataId()) ) ) {
		//		errmsg += "Quadro origem; ";			
		//	}
		//	else {
		//		bHasQuadroOrigem = true;
		//	}
		//}
			
		//if( StringUtil.isEmpty(strPotencia) )
		//	errmsg += "Potencia; ";

		//if( StringUtil.isEmpty(strPotenciaDemandada) ) {
		//	strPotenciaDemandada = strPotencia;
		//}

		//if(oSistemaFase == null)
		//	errmsg += "Sistema fase; ";

		//if( !AppDefs.FIA_S_COMANDO.equals(this.tipo) &&
		//	!AppDefs.FIA_S_CAMPAINHA.equals(this.tipo) &&
		//	!AppDefs.FIA_S_CAIXA.equals(this.tipo) &&
		//	!AppDefs.FIA_S_DESVIO.equals(this.tipo) &&
		//	!AppDefs.FIA_S_CALHA.equals(this.tipo) ) 
		//{
		//	if( bHasQuadroOrigem ) {
		//		if( StringUtil.isEmpty(strCircuito) )
		//			errmsg += "Circuito; ";
		//	}
		//}

		//if( AppDefs.FIA_S_COMANDO.equals(this.tipo) ||
		//	AppDefs.FIA_S_CAMPAINHA.equals(this.tipo) ) 
		//{
		//	if( bHasQuadroOrigem ) {
		//		if( StringUtil.isEmpty(strComando) )
		//			errmsg += "Comando; ";
		//	}
		//}
		
		//if( !StringUtil.isEmpty(errmsg) ) {
		//	errmsg = "ERR: Campos obrigatorios nao informados: " + errmsg;
		//	
		//	this.showMessage(errmsg);
		//	return false;
		//}
		
	    // VALIDATE: POTENCIA / POTENCIA_DEMANDADA
	    //
		if(dPotencia < 0.0) {
			errmsg = "ERR: Potencia deve ser maior ou igual a 0";
			
			this.showMessage(errmsg);
			return false;			
		}
		
		if(dPotenciaDemandada < 0.0) {
			errmsg = "ERR: Potencia demandada deve ser maior ou igual a 0";
			
			this.showMessage(errmsg);
			return false;			
		}

		// UPDATE_DATA
		//
		String strQuadroOrigem = oQuadroOrigem.getItemDataId();
		
	    this.nomeQuadro = strNomeQuadro;
	    this.quadroOrigem = ( "-1".equals(strQuadroOrigem) ? "" : strQuadroOrigem );
	    this.potencia = dPotencia;
	    this.potenciaDemandada = dPotenciaDemandada;
	    this.sistemaFase = oSistemaFase.getItemDataId();
	    this.circuito = strCircuito;
	    this.comando = strComando;		

		return true;
	}
    
    /* Event Handlers */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionGravar(ActionEvent e) 
	{
		if( this.validateForm() ) {
			
			oCurrParamEletrico.setNomeQuadro(this.nomeQuadro);
			oCurrParamEletrico.setQuadroOrigem(this.quadroOrigem);
			oCurrParamEletrico.setPotencia(this.potencia);
			oCurrParamEletrico.setPotenciaDemandada(this.potenciaDemandada);
			oCurrParamEletrico.setSistema(this.sistemaFase);
			oCurrParamEletrico.setCircuito(this.circuito);
			oCurrParamEletrico.setComando(this.comando);

			String msg = "Dados gravados com sucesso.";
			this.showMessage(msg);
		}
	}
		
	public void doActionFechar(ActionEvent e) 
	{
		boolean bResult = this.saveForm();
		if( bResult ) {
			rscode = AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_FECHAR;
			this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

			this.getParentFrame().dispose();
		}
	}
	
	public void doActionAnterior(ActionEvent e) {
		this.posCurrParamEletrico -= 1;
		if(this.posCurrParamEletrico < 0)
			this.posCurrParamEletrico = 0;

		boolean bResult = this.saveForm();
		if( bResult ) {
			this.oCurrParamEletrico = this.loadCurrParamEletrico(this.posCurrParamEletrico);
			this.updateForm();
		}
	}
	
	public void doActionProximo(ActionEvent e) {
		int sz = this.oEnt1.getSzLsParamEletrico();
		
		this.posCurrParamEletrico += 1;
		if(this.posCurrParamEletrico >= sz)
			this.posCurrParamEletrico = sz - 1;

		boolean bResult = this.saveForm();
		if( bResult ) {
			this.oCurrParamEletrico = this.loadCurrParamEletrico(this.posCurrParamEletrico);
			this.updateForm();
		}
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_GRAVAR) {
			String warnmsg = String.format("Action - GRAVAR: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

			doActionGravar(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_FECHAR) {
			String warnmsg = String.format("Action - FECHAR: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

			doActionFechar(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_ANTERIOR) {
			String warnmsg = String.format("Action - ANTERIOR: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

			doActionAnterior(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_PROXIMO) {
			String warnmsg = String.format("Action - PROXIMO: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

			doActionProximo(e);						
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

	public CadPontoEletrica getPontoEletrica() {
		return oEnt1;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getQuadroOrigem() {
		return quadroOrigem;
	}

	public void setQuadroOrigem(String quadroOrigem) {
		this.quadroOrigem = quadroOrigem;
	}

	public double getPotencia() {
		return potencia;
	}

	public void setPotencia(double potencia) {
		this.potencia = potencia;
	}

	public double getPotenciaDemandada() {
		return potenciaDemandada;
	}

	public void setPotenciaDemandada(double potenciaDemandada) {
		this.potenciaDemandada = potenciaDemandada;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

}
