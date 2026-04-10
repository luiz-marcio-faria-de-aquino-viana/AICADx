/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * SetupPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.eletrica.vo.DefinicaoQuadroDistribuicaoVO;

public class DefinicaoQuadroDistribuicaoPanel extends BasePanel
{
//Private
	private CadDocumentDef oDocDef = null;
	private CadProjectDef oCurrProject = null;

	//VARIABLES
	//
    private double tensaoFase = 0.0;
    private double bitolaMinimaCondutor = 0.0;
    private double temperatura = 0.0;
    private double fatorReducao = 0.0;
	
	//FORM_CONTROLS
	//
	private JLabel lblTensaoFase = null;
	private JLabel lblBitolaMinimaCondutor = null;
	private JLabel lblTemperatura = null;
	private JLabel lblFatorReducao = null;

	private JTextField txtTensaoFase = null;
	private JTextField txtBitolaMinimaCondutor = null;
	private JTextField txtTemperatura = null;
	private JTextField txtFatorReducao = null;

	// CLOSE/CANCEL BUTTONS
	//
	private JButton btnOk = null;
	private JButton btnCancelar = null;

	private int rscode = AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_NONE;

	/* Methodes */
	
	private void clearFormFields()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);
		
		this.tensaoFase = AppDefs.DEF_DEFAULT_TENSAO_FASE;
		this.bitolaMinimaCondutor = AppDefs.DEF_DEFAULT_BITOLA_MINIMA_CONDUTOR;
		this.temperatura = AppDefs.DEF_DEFAULT_TEMPERATURA;
		this.fatorReducao = AppDefs.DEF_DEFAULT_FATOR_REDUCAO;
	}
	
	// INIT_FORM
	//
	private void initForm()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		this.setLayout(null);
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_H5;

		//LABELS
		//
		String resTensaoFase = r.getString(R.LBL_TENSAO_FASE);
		this.lblTensaoFase = FormControlUtil.newLabel(resTensaoFase, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.add(this.lblTensaoFase);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resBitolaMinimaCondutor = r.getString(R.LBL_BITOLA_MINIMA_CONDUTOR);
		this.lblBitolaMinimaCondutor = FormControlUtil.newLabel(resBitolaMinimaCondutor, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.add(this.lblBitolaMinimaCondutor);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resTemperatura = r.getString(R.LBL_TEMPERATURA);
		this.lblTemperatura = FormControlUtil.newLabel(resTemperatura, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.add(this.lblTemperatura);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resFatorReducao = r.getString(R.LBL_FATOR_REDUCAO);
		this.lblFatorReducao = FormControlUtil.newLabel(resFatorReducao, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.add(this.lblFatorReducao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		//CONTROLS
		//
		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5);
		yp = insets.top + AppDefs.SPACE_H5;
		
		this.txtTensaoFase = FormControlUtil.newTextField(nf3.format(this.tensaoFase), xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.add(this.txtTensaoFase);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		this.txtBitolaMinimaCondutor = FormControlUtil.newTextField(nf3.format(this.bitolaMinimaCondutor), xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.add(this.txtBitolaMinimaCondutor);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		this.txtTemperatura = FormControlUtil.newTextField(nf3.format(this.temperatura), xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.add(this.txtTemperatura);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		this.txtFatorReducao = FormControlUtil.newTextField(nf3.format(this.fatorReducao), xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.add(this.txtFatorReducao);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		//BUTTONS
		//
		xp = insets.left + AppDefs.SETUP_FRAME_WIDTH - (AppDefs.SPACE_W5 + AppDefs.BUTTON_W100);
		
		String resBtnCancelar = r.getString(R.BTN_CANCELAR);
		this.btnCancelar = FormControlUtil.newButton(resBtnCancelar, AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_CANCELAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnCancelar);		
		xp -= (AppDefs.SPACE_H5 + AppDefs.BUTTON_W100);

		String resBtnOk = r.getString(R.BTN_OK);
		this.btnOk = FormControlUtil.newButton(resBtnOk, AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_OK, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnOk);
		
	}
	
//Public 
	
	public DefinicaoQuadroDistribuicaoPanel(DefinicaoQuadroDistribuicaoFrame parentFrame)
	{
		super(parentFrame);
	}
	
	/* Methodes */
	
	public void init(CadDocumentDef oDocDef, CadProjectDef oCurrProject)
	{
		this.oDocDef = oDocDef;
		this.oCurrProject = oCurrProject;
		
		this.clearFormFields();
		this.initForm();
	}

	public boolean validateForm()
	{
		String errmsg = "";
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		String strTensaoFase = this.txtTensaoFase.getText();
		String strBitolaMinimaCondutor = this.txtBitolaMinimaCondutor.getText();
		String strTemperatura = this.txtTemperatura.getText();
		String strFatorReducao = this.txtFatorReducao.getText();
		
		//VALIDATE_FORM_DATA
		//	
		if( StringUtil.isEmpty(strTensaoFase) )
			errmsg += "Tensao de fase; ";			
		
		if( StringUtil.isEmpty(strBitolaMinimaCondutor) )
			errmsg += "Bitola minima do condutor; ";
		
		if( StringUtil.isEmpty(strTemperatura) )
			errmsg += "Temperatura";
		
		if( StringUtil.isEmpty(strFatorReducao) )
			errmsg += "Fator de reducao";

		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}

		//VALIDATE_FORM_DATA
		//		
		this.tensaoFase = StringUtil.safeDbl(nf6, strTensaoFase);
		if(this.tensaoFase < AppDefs.MATHPREC_MIN) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Valor da tensao de fase deve ser maior que zero.", this.getClass());
			return false;
		}
		
		this.bitolaMinimaCondutor = StringUtil.safeDbl(nf6, strBitolaMinimaCondutor);
		if(this.bitolaMinimaCondutor < AppDefs.MATHPREC_MIN) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Bitola minima do condutor deve ser maior que zero.", this.getClass());
			return false;
		}
		
		this.temperatura = StringUtil.safeDbl(nf6, strTemperatura);
		if(this.temperatura < AppDefs.MATHPREC_MIN) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Temperatura ambiente deve ser maior que zero.", this.getClass());
			return false;
		}

		this.fatorReducao = StringUtil.safeDbl(nf6, strFatorReducao);
		if(this.fatorReducao < AppDefs.MATHPREC_MIN) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Fator de reducao deve ser maior que zero.", this.getClass());
			return false;
		}
		return true;
	}	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionOk(ActionEvent e) 
	{
		if( validateForm() ) {
			DefinicaoQuadroDistribuicaoVO oResult = new DefinicaoQuadroDistribuicaoVO(
			    this.tensaoFase,
			    this.bitolaMinimaCondutor,
			    this.temperatura,
			    this.fatorReducao );			
			
			this.rscode = AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_OK;			
			this.parentFrame.actionResultListener(new ResultEvent(this.rscode, oResult));
			
			this.parentFrame.dispose();
		}
	}
		
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_CANCELAR;
		this.parentFrame.actionResultListener(new ResultEvent(rscode, null));

		this.parentFrame.dispose();
	}

	/* ACTION_EVENTS */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmdAction = StringUtil.safeInt( e.getActionCommand() );
		
		if(cmdAction == AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_OK) {
			doActionOk(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_DEFINICAO_QUADRO_DISTRIBUICAO_CANCELAR) {
			doActionCancelar(e);						
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) { }

	@Override
	public void itemStateChanged(ItemEvent e) {	}

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
	public void componentResized(ComponentEvent e) { };

	@Override
	public void componentMoved(ComponentEvent e) { };

	@Override
	public void componentShown(ComponentEvent e) { };

	@Override
	public void componentHidden(ComponentEvent e) { };

	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) { }

	/* Getters/Setters */

	public int getRsCode() {
		return rscode;
	}

	public void setRsCode(int rscode) {
		this.rscode = rscode;
	}

	public CadProjectDef getCurrProject() {
		return this.oCurrProject;
	}

	public void setCurrProject(CadProjectDef oCurrProject) {
		this.oCurrProject = oCurrProject;
	}

	public double getTensaoFase() {
		return tensaoFase;
	}

	public void setTensaoFase(double tensaoFase) {
		this.tensaoFase = tensaoFase;
	}

	public double getBitolaMinimaCondutor() {
		return bitolaMinimaCondutor;
	}

	public void setBitolaMinimaCondutor(double bitolaMinimaCondutor) {
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public double getFatorReducao() {
		return fatorReducao;
	}

	public void setFatorReducao(double fatorReducao) {
		this.fatorReducao = fatorReducao;
	}
	
}
