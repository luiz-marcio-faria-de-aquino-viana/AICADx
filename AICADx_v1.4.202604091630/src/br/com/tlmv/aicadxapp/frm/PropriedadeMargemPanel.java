/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PropriedadeMargemPanel.java
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

package br.com.tlmv.aicadxapp.frm;

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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadMargem;
import br.com.tlmv.aicadxapp.cad.CadParamMargemOData;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class PropriedadeMargemPanel extends BasePanel
{
//Private
	private CadDocumentDef doc = null;	
    private CadMargem oEnt1 = null;    
    
	private CadParamMargemOData oCurrParamMargem = null;
	private int posCurrParamMargem = AppDefs.NULL_INT;

	private int rscode = AppDefs.RSCODE_PROPRIEDADE_MARGEM_NONE;
	
	/* PROPRIEDADES_PONTO_ELETRICA */

	private String tituloProjeto = "";
	private String disciplina = "";
	private String numeroDesenho = "";
	private String descricaoDesenho = "";
	private String responsavelTecnico = "";
	private String escala = "";
	private String dataEmissao = "";
	private String numeroRevisao = "";
	
    private ArrayList<ItemDataVO> lsDisciplina = null;
    private ArrayList<ItemDataVO> lsEscala = null;
    
	//LABELS
	//
    private JLabel lblTituloProjeto = null;
    private JLabel lblDisciplina = null;
    private JLabel lblNumeroDesenho = null;
    private JLabel lblDescricaoDesenho = null;
    private JLabel lblResponsavelTecnico = null;
    private JLabel lblEscala = null;
    private JLabel lblDataEmissao = null;
    private JLabel lblNumeroRevisao = null;
    //
    private JLabel lblMessage = null;
    
	//CONTROLS
	//
	private JTextField txtTituloProjeto = null;
	private JComboBox<ItemDataVO> cbxDisciplinaId = null;
	private JTextField txtNumeroDesenho = null;
	private JTextField txtDescricaoDesenho = null;
	private JTextField txtResponsavelTecnico = null;
	private JComboBox<ItemDataVO> cbxEscalaId = null;
	private JTextField txtDataEmissao = null;
	private JTextField txtNumeroRevisao = null;
	
	//BUTTON
	//
	private JButton btnGravar = null;
	private JButton btnFechar = null;
	
	/* Methodes */

	private void resetMessage() {
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		int sz = this.oEnt1.getSzLsParamMargem();
		
		int currItem = this.posCurrParamMargem + 1;
		String.format("Parametro atual disponivel em tela para atualizacao: %s de %s", 
			nf0.format( currItem ),
			nf0.format( sz ) );
		this.lblMessage.setText("");
	}
	
	private void showMessage(String msg) {
		this.lblMessage.setText(msg);
	}
	
	private CadParamMargemOData loadCurrParamMargem()
	{
		int sz = this.oEnt1.getSzLsParamMargem();
		
		CadParamMargemOData oResult = oEnt1.getParamMargemAt(0);
		return oResult;
	}

	private void loadAllDisciplina(CadDocumentDef doc)
	{
	    this.lsDisciplina = new ArrayList<ItemDataVO>();
		for(ItemDataVO o : AppDefs.ARR_DISCIPLINE) {
			this.lsDisciplina.add(o);
		}
	}

	private void loadAllEscala(CadDocumentDef doc)
	{
	    this.lsEscala = new ArrayList<ItemDataVO>();
		for(ItemDataVO o : AppDefs.ARR_PROJECT_SCALE) {
			this.lsEscala.add(o);
		}
	}

	/* INIT */
	
	private void initBasicData(CadDocumentDef doc, CadMargem oEnt1)
	{
		this.doc = doc;
		this.oEnt1 = oEnt1;

		this.posCurrParamMargem = 0;
		this.oCurrParamMargem = this.loadCurrParamMargem();
		
		this.loadAllDisciplina(this.doc);
		this.loadAllEscala(this.doc);
	}
		
	private void initData()
	{
		int sz = this.oEnt1.getSzLsParamMargem();
		
		if(this.posCurrParamMargem < 0)
			this.posCurrParamMargem = 0;

		if(this.posCurrParamMargem >= sz)
			this.posCurrParamMargem = 0;

		this.oCurrParamMargem = this.loadCurrParamMargem();

		//FORM_DATA
		//
		this.tituloProjeto = this.oCurrParamMargem.getTituloProjeto();
		this.disciplina = this.oCurrParamMargem.getDisciplina();
		this.numeroDesenho = this.oCurrParamMargem.getNumeroDesenho();
		this.descricaoDesenho = this.oCurrParamMargem.getDescricaoDesenho();
		this.responsavelTecnico = this.oCurrParamMargem.getResponsavelTecnico();
		this.escala = this.oCurrParamMargem.getEscala();
		this.dataEmissao = this.oCurrParamMargem.getDataEmissao();
		this.numeroRevisao = this.oCurrParamMargem.getNumeroRevisao();;
	}
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		//LISTA: DISCIPLINA
		//
		int szLsDisciplina = this.lsDisciplina.size();
		ItemDataVO[] arrDisciplina = this.lsDisciplina.toArray( new ItemDataVO[szLsDisciplina] ); 		

		//LISTA: ESCALA
		//
		int szLsEscala = this.lsEscala.size();
		ItemDataVO[] arrEscala = this.lsEscala.toArray( new ItemDataVO[szLsEscala] ); 		
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;

		//LABEL
		//
	    this.lblTituloProjeto = FormControlUtil.newLabel("Projeto: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblTituloProjeto);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblDisciplina = FormControlUtil.newLabel("Disciplina: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblDisciplina);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblNumeroDesenho = FormControlUtil.newLabel("Prancha: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblNumeroDesenho);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblDescricaoDesenho = FormControlUtil.newLabel("Descricao: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblDescricaoDesenho);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

	    this.lblResponsavelTecnico = FormControlUtil.newLabel("Resp.Tecnico: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblResponsavelTecnico);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
				
	    this.lblEscala = FormControlUtil.newLabel("Escala: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblEscala);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblDataEmissao = FormControlUtil.newLabel("Emissao: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblDataEmissao);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblNumeroRevisao = FormControlUtil.newLabel("Revisao: ", xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblNumeroRevisao);		
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		
	    this.lblMessage = FormControlUtil.newLabel("", xp, yp, AppDefs.LABEL_W600, AppDefs.LABEL_H20, true);
		this.add(this.lblMessage);
		
		//CONTROLS
		//
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;
		
		this.txtTituloProjeto = FormControlUtil.newTextField(this.tituloProjeto, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtTituloProjeto);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;

	    this.cbxDisciplinaId = FormControlUtil.newComboBox(arrDisciplina, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_MARGEM_DISCIPLINA_CHANGED), this);
		this.add(this.cbxDisciplinaId);
		FormControlUtil.setCbx(this.cbxDisciplinaId, this.lsDisciplina, this.disciplina);
		int pos = this.cbxDisciplinaId.getSelectedIndex();
		if(pos == -1) {
			this.cbxDisciplinaId.setSelectedIndex(0);
		}
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;
				
		this.txtNumeroDesenho = FormControlUtil.newTextField(this.numeroDesenho, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtNumeroDesenho);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtDescricaoDesenho = FormControlUtil.newTextField(this.descricaoDesenho, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDescricaoDesenho);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtResponsavelTecnico = FormControlUtil.newTextField(this.responsavelTecnico, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtResponsavelTecnico);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;

	    this.cbxEscalaId = FormControlUtil.newComboBox(arrEscala, xp, yp, AppDefs.COMBO_W600, AppDefs.COMBO_H20, true, Integer.toString(AppDefs.RSCODE_PROPRIEDADE_MARGEM_ESCALA_CHANGED), this);
		this.add(this.cbxEscalaId);
		FormControlUtil.setCbx(this.cbxEscalaId, this.lsEscala, this.escala);
		pos = this.cbxEscalaId.getSelectedIndex();
		if(pos == -1) {
			this.cbxEscalaId.setSelectedIndex(0);
		}
		yp += AppDefs.COMBO_H20 + AppDefs.SPACE_W5;
		
		this.txtDataEmissao = FormControlUtil.newTextField(this.dataEmissao, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtDataEmissao);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_W5;
		
		this.txtNumeroRevisao = FormControlUtil.newTextField(this.numeroRevisao, xp, yp, AppDefs.TEXT_W600, AppDefs.TEXT_H20, true, true);
		this.add(this.txtNumeroRevisao);

		//BUTTON
		//
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;
		xp = insets.left + AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5 + AppDefs.TEXT_W600 - AppDefs.LABEL_W100;

		this.btnFechar = FormControlUtil.newButton("Fechar", AppDefs.RSCODE_PROPRIEDADE_MARGEM_FECHAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);  
		this.add(this.btnFechar);
		xp -= (AppDefs.BUTTON_W100 + AppDefs.SPACE_W5);

		this.btnGravar = FormControlUtil.newButton("Gravar", AppDefs.RSCODE_PROPRIEDADE_MARGEM_GRAVAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);  
		this.add(this.btnGravar);

		this.resetMessage();
	}
		
	private void updateForm()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		//FORM_DATA
		//
	    this.txtTituloProjeto.setText( this.tituloProjeto );
	    this.txtNumeroDesenho.setText( this.numeroDesenho );
	    this.txtDescricaoDesenho.setText( this.descricaoDesenho );
	    this.txtResponsavelTecnico.setText( this.responsavelTecnico );
	    this.txtDataEmissao.setText( this.dataEmissao );
	    this.txtNumeroRevisao.setText( this.numeroRevisao );

		FormControlUtil.setCbx(this.cbxDisciplinaId, this.lsDisciplina, this.disciplina);
		int pos = this.cbxDisciplinaId.getSelectedIndex();
		if(pos == -1) {
			this.cbxDisciplinaId.setSelectedIndex(0);
		}
		
		FormControlUtil.setCbx(this.cbxEscalaId, this.lsEscala, this.escala);
		pos = this.cbxEscalaId.getSelectedIndex();
		if(pos == -1) {
			this.cbxEscalaId.setSelectedIndex(0);
		}

		this.resetMessage();
	}
	
//Public 
	
	public PropriedadeMargemPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(CadDocumentDef doc, CadMargem oEnt1)
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
		ItemDataVO oDisciplina = (ItemDataVO)this.cbxDisciplinaId.getSelectedItem(); 
		ItemDataVO oEscala = (ItemDataVO)this.cbxEscalaId.getSelectedItem(); 

		String strTituloProjeto = this.txtTituloProjeto.getText(); 
		String strNumeroDesenho = this.txtNumeroDesenho.getText(); 
		String strDescricaoDesenho = this.txtDescricaoDesenho.getText(); 
		String strResponsavelTecnico = this.txtResponsavelTecnico.getText(); 
		String strDataEmissao = this.txtDataEmissao.getText(); 
		String strNumeroRevisao = this.txtNumeroRevisao.getText(); 

		// VALIDATE: EMPTY_FIELD
	    //
		if( StringUtil.isEmpty(strTituloProjeto) )
			errmsg += "Projeto; ";

		if(oDisciplina == null) 
			errmsg += "Disciplina; ";			
			
		if( StringUtil.isEmpty(strNumeroDesenho) )
			errmsg += "Prancha; ";

		if( StringUtil.isEmpty(strDescricaoDesenho) ) 
			errmsg += "Descricao; ";

		if( StringUtil.isEmpty(strResponsavelTecnico) ) 
			errmsg += "Resp.Tecnico; ";

		if(oEscala == null)
			errmsg += "Escala; ";

		if( StringUtil.isEmpty(strDataEmissao) ) 
			errmsg += "Emissao; ";

		if( StringUtil.isEmpty(strNumeroRevisao) ) 
			errmsg += "Revisao; ";

		if( !StringUtil.isEmpty(errmsg) ) {
			errmsg = "ERR: Campos obrigatorios nao informados: " + errmsg;
			
			this.showMessage(errmsg);
			return false;
		}
		
		// UPDATE_DATA
		//
		String strDisciplina = oDisciplina.getDescricao(); 
		String strEscala = oEscala.getDescricao(); 

		this.tituloProjeto = strTituloProjeto; 
		this.disciplina = strDisciplina; 
		this.numeroDesenho = strNumeroDesenho; 
		this.descricaoDesenho = strDescricaoDesenho; 
		this.responsavelTecnico = strResponsavelTecnico; 
		this.escala = strEscala; 
		this.dataEmissao = strDataEmissao; 
		this.numeroRevisao = strNumeroRevisao; 

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
			this.oCurrParamMargem.setTituloProjeto(this.tituloProjeto); 
			this.oCurrParamMargem.setDisciplina(this.disciplina); 
			this.oCurrParamMargem.setNumeroDesenho(this.numeroDesenho); 
			this.oCurrParamMargem.setDescricaoDesenho(this.descricaoDesenho); 
			this.oCurrParamMargem.setResponsavelTecnico(this.responsavelTecnico); 
			this.oCurrParamMargem.setEscala(this.escala);
			this.oCurrParamMargem.setDataEmissao(this.dataEmissao);  
			this.oCurrParamMargem.setNumeroRevisao(this.numeroRevisao); 

			String msg = "Dados gravados com sucesso.";
			this.showMessage(msg);
		}
	}
		
	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_PROPRIEDADE_PONTO_ELETRICA_FECHAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_PROPRIEDADE_MARGEM_GRAVAR) {
			String warnmsg = String.format("Action - GRAVAR: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

			doActionGravar(e);						
		}
		else if(action == AppDefs.RSCODE_PROPRIEDADE_MARGEM_FECHAR) {
			String warnmsg = String.format("Action - FECHAR: %s", action);
			AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

			doActionFechar(e);						
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

	public CadMargem getMargem() {
		return oEnt1;
	}

	public String getTituloProjeto() {
		return tituloProjeto;
	}

	public void setTituloProjeto(String tituloProjeto) {
		this.tituloProjeto = tituloProjeto;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getNumeroDesenho() {
		return numeroDesenho;
	}

	public void setNumeroDesenho(String numeroDesenho) {
		this.numeroDesenho = numeroDesenho;
	}

	public String getDescricaoDesenho() {
		return descricaoDesenho;
	}

	public void setDescricaoDesenho(String descricaoDesenho) {
		this.descricaoDesenho = descricaoDesenho;
	}

	public String getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public void setResponsavelTecnico(String responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}

	public String getEscala() {
		return escala;
	}

	public void setEscala(String escala) {
		this.escala = escala;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getNumeroRevisao() {
		return numeroRevisao;
	}

	public void setNumeroRevisao(String numeroRevisao) {
		this.numeroRevisao = numeroRevisao;
	}

}
