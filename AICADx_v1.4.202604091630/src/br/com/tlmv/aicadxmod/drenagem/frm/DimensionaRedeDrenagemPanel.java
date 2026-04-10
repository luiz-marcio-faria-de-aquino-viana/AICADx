/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DimensionaRedeDrenagemPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/04/2025
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
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class DimensionaRedeDrenagemPanel extends BasePanel implements ActionListener
{
//Private

	//LABELS
	//
	private JLabel lblPeriodoRecorrencia = null;
	private JLabel lblDuracaoChuva = null;
	private JLabel lblIndicePluviometrico = null;
	private JLabel lblAreaContribuicao = null;
	private JLabel lblDeclividadeMax = null;
	private JLabel lblDeclividadeMin = null;

	//CONTROLS
	//
	private JTextField txtPeriodoRecorrencia = null;
	private JTextField txtDuracaoChuva = null;
	private JTextField txtIndicePluviometrico = null;
	private JCheckBox chkUsarAreaContribuicao = null;
	private JTextField txtAreaContribuicao = null;
	private JTextField txtDeclividadeMax = null;
	private JTextField txtDeclividadeMin = null;

	//BUTTON
	//
	private JButton btnOk = null;
	private JButton btnCancel = null;

	//LOCAL - JARDIM_BOTANICO
	private double periodoRecorrencia = 50.0;			// 50 anos
	private double duracaoChuva = 10.0;					// 10 minutos
	private double indicePluviometrico = 0.0;			// a calcular: i = (a * (Tr ^ b)) / ((t + c) ^ d)
	private double areaContribuicao = 0.0;				// a informar
	private double declividadeMax = 7.0;				// 7,0 %
	private double declividadeMin = 1.0;				// 1,0 %		
	//
	private boolean bUsarAreaContribuicao = false; 

	private int rscode = AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_NONE;
	
    private ArrayList<CadEntity> lsCI = null;
	
	/* Methodes */
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		int local = DrenagemCalc.IDFLOCAL_JARDIMBOTANICO_VAL; 

		DrenagemCalc calc = new DrenagemCalc();
		
		// a calcular: i = (a * (Tr ^ b)) / ((t + c) ^ d)
		this.indicePluviometrico = calc.calcIdfIndicePluviometrico(local, this.periodoRecorrencia, this.duracaoChuva);
		
		//LOCAL - JARDIM_BOTANICO
		String strPeriodoRecorrencia = nf0.format(this.periodoRecorrencia);		// 50 anos
		String strDuracaoChuva = nf0.format(this.duracaoChuva);					// 10 minutos
		String strIndicePluviometrico = nf1.format(this.indicePluviometrico);	// a calcular: i = (a * (Tr ^ b)) / ((t + c) ^ d)
		String strAreaContribuicao = nf1.format(this.areaContribuicao);			// a informar
		String strDeclividadeMax = nf1.format(this.declividadeMax);				// 7,0 %
		String strDeclividadeMin = nf1.format(this.declividadeMin);				// 1,0 %		
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;

		//LABEL
		//
		this.lblPeriodoRecorrencia = FormControlUtil.newLabel("Periodo Recorrencia (em anos):", xp, yp, AppDefs.LABEL_W250, AppDefs.LABEL_H20, true);
		this.add(this.lblPeriodoRecorrencia);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		this.lblDuracaoChuva = FormControlUtil.newLabel("Duracao da Chuva (em minutos):", xp, yp, AppDefs.LABEL_W250, AppDefs.LABEL_H20, true);
		this.add(this.lblDuracaoChuva);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

		this.lblIndicePluviometrico = FormControlUtil.newLabel("Indice Pluviometrico (mm/h):", xp, yp, AppDefs.LABEL_W250, AppDefs.LABEL_H20, true);
		this.add(this.lblIndicePluviometrico);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		this.lblAreaContribuicao = FormControlUtil.newLabel("Area Contribuicao (ha):", xp, yp, AppDefs.LABEL_W250, AppDefs.LABEL_H20, true);
		this.add(this.lblAreaContribuicao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		this.chkUsarAreaContribuicao = FormControlUtil.newCheckBox(bUsarAreaContribuicao, "Usar area de contribuicao?", xp, yp, AppDefs.TEXT_W350, AppDefs.TEXT_H20, true, true);
		this.add(this.chkUsarAreaContribuicao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		this.lblDeclividadeMax = FormControlUtil.newLabel("Declividade Maxima (%):", xp, yp, AppDefs.LABEL_W250, AppDefs.LABEL_H20, true);
		this.add(this.lblDeclividadeMax);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
		this.lblDeclividadeMin = FormControlUtil.newLabel("Declividade Minima (%):", xp, yp, AppDefs.LABEL_W250, AppDefs.LABEL_H20, true);
		this.add(this.lblDeclividadeMin);

		//CONTROLS
		//
		xp += AppDefs.LABEL_W250 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;

		this.txtPeriodoRecorrencia = FormControlUtil.newTextField(strPeriodoRecorrencia, xp, yp, AppDefs.TEXT_W100, AppDefs.TEXT_H20, true, true);
		this.add(this.txtPeriodoRecorrencia);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

		this.txtDuracaoChuva = FormControlUtil.newTextField(strDuracaoChuva, xp, yp, AppDefs.TEXT_W100, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDuracaoChuva);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

		this.txtIndicePluviometrico = FormControlUtil.newTextField(strIndicePluviometrico, xp, yp, AppDefs.TEXT_W100, AppDefs.TEXT_H20, true, false);
		this.add(this.txtIndicePluviometrico);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

		this.txtAreaContribuicao = FormControlUtil.newTextField(strAreaContribuicao, xp, yp, AppDefs.TEXT_W100, AppDefs.TEXT_H20, true, bUsarAreaContribuicao);
		this.add(this.txtAreaContribuicao);
		yp += (AppDefs.LABEL_H20 + AppDefs.SPACE_W5) + (AppDefs.LABEL_H20 + AppDefs.SPACE_W5);

		this.txtDeclividadeMax = FormControlUtil.newTextField(strDeclividadeMax, xp, yp, AppDefs.TEXT_W100, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDeclividadeMax);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

		this.txtDeclividadeMin = FormControlUtil.newTextField(strDeclividadeMin, xp, yp, AppDefs.TEXT_W100, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDeclividadeMin);
		
		//BUTTONS
		//
		xp = insets.left + AppDefs.DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_FRAME_WIDTH - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W5);
		yp += AppDefs.BUTTON_H20 + AppDefs.SPACE_W5;

		this.btnCancel = FormControlUtil.newButton("Cancelar", AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_CANCELAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnCancel);
		xp -= (AppDefs.BUTTON_W100 + AppDefs.SPACE_W5);

		this.btnOk = FormControlUtil.newButton("Ok", AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_OK, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnOk);
		
	}
	
//Public 
	
	public DimensionaRedeDrenagemPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(DimensionaRedeDrenagemFrame parentFrame, AppMain app, MainFrame frm)
	{
		initForm();
	}
	
	/* Methodes */
	
	public boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		int local = DrenagemCalc.IDFLOCAL_JARDIMBOTANICO_VAL; 
				
		//LOCAL - JARDIM_BOTANICO
		String strPeriodoRecorrencia = this.txtPeriodoRecorrencia.getText();		// 50 anos
		String strDuracaoChuva = this.txtDuracaoChuva.getText();					// 10 minutos
		String strAreaContribuicao = this.txtAreaContribuicao.getText();			// a informar
		String strDeclividadeMax = this.txtDeclividadeMax.getText();				// 7,0 %
		String strDeclividadeMin = this.txtDeclividadeMin.getText();				// 1,0 %		

		this.bUsarAreaContribuicao = this.chkUsarAreaContribuicao.isSelected();
		
		if( "".equals(strPeriodoRecorrencia) )
			errmsg += "Periodo de recorrencia; ";

		if( "".equals(strDuracaoChuva) )
			errmsg += "Duracao da chuva; ";

		if( this.bUsarAreaContribuicao ) {
			if( "".equals(strAreaContribuicao) )
				errmsg += "Area de contribuicao; ";
		}

		if( "".equals(strDeclividadeMax) )
			errmsg += "Declividade maxima; ";

		if( "".equals(strDeclividadeMin) )
			errmsg += "Declividade minima; ";
		
		if(errmsg != "") {
			errmsg = "ERR: Campos invalidos: " + errmsg;
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;
		}
		
		//LOCAL - JARDIM_BOTANICO
		this.periodoRecorrencia = StringUtil.safeDbl(nf6, strPeriodoRecorrencia);	// 50 anos
		this.duracaoChuva = StringUtil.safeDbl(nf6, strDuracaoChuva);				// 10 minutos
		this.areaContribuicao = StringUtil.safeDbl(nf6, strAreaContribuicao);		// a informar
		this.declividadeMax = StringUtil.safeDbl(nf6, strDeclividadeMax);			// 7,0 %
		this.declividadeMin  = StringUtil.safeDbl(nf6, strDeclividadeMin);			// 1,0 %		
		
		if(this.periodoRecorrencia < 1.0) {
			errmsg = "ERR: Periodo de recorrencia deve ser maior ou igual a 1 ano";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if(this.duracaoChuva < 1.0) {
			errmsg = "ERR: Duracao da chuva deve ser maior ou igual a 1 minuto";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		if( this.bUsarAreaContribuicao ) {
			if(this.areaContribuicao < 1.0) {
				errmsg = "ERR: Area de Contribuicao deve ser maior ou igual a 1,0 m2";
				
				AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
				return false;			
			}
		}
		
		if(this.declividadeMax < this.declividadeMin) {
			errmsg = "ERR: Declividade maxima precisa ser maior ou igual ao valor minimo";
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;			
		}
		
		DrenagemCalc calc = new DrenagemCalc();
		
		// a calcular: i = (a * (Tr ^ b)) / ((t + c) ^ d)
		this.indicePluviometrico = calc.calcIdfIndicePluviometrico(local, this.periodoRecorrencia, this.duracaoChuva);
		
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
			//DrenagemCalc calc = new DrenagemCalc();
			
			//calc.removeRedeDrenagem(this.doc);
			
			//CadBlockDef blkDef = this.doc.getCurrBlockDef();
			
			//this.lsCI = calc.initRedeDrenagem(this.doc, this.bUsarAreaContribuicao, this.areaContribuicao, this.indicePluviometrico);

			//calc.buildLista(this.lsCI);			
			//calc.processaRedeDrenagem(this.lsCI);
			//calc.createRedeDrenagem(this.doc, this.lsCI);

			BaseFrame parentFrame = this.getParentFrame();
			
			rscode = AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_OK;			
			parentFrame.actionResultListener(new ResultEvent(rscode, null));
				
			this.getParentFrame().dispose();
		}
	}
		
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_CANCELAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_OK) {
			doActionOk(e);
		}
		else if(action == AppDefs.RSCODE_DIMENSIONA_CAIXA_INSPECAO_DRENAGEM_CANCELAR) {
			doActionCancelar(e);						
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

	public int getRSCode() {
		return rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}
	
}
