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

package br.com.tlmv.aicadxapp.frm;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cmp.CmpLevel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.model.LevelListModel;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.LevelVO;

public class SetupPanel extends BasePanel
{
//Private
	private CadDocumentDef oDocDef = null;
	private CadProjectDef oCurrProject = null;

	//VARIABLES
	//
	// PROJECT_INFO
	private String codigoProjeto;
	private String tituloProjeto;
	private String descricaoProjeto;

	// PROJECT_ADDRESS
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;

	// PROJECT_REGISTER
	private String art;
	private String nomeResponsavelTecnico;
	private String registroResponsavelTecnico;
	private String telefoneResponsavelTecnico;
	private String emailResponsavelTecnico;

	// PROJECT_DEFAULT - DREANAGE_PARAMETERS
	private String pluviografo;								// local medicao volume chuva
	private double coefManning;
	private double periodoRecorrencia;

	// PROJECT_DEFAULT - OUTPUT_PARAMETERS
	private double escala;
	private double papelLargura;
	private double papelAltura;	

	// PROJECT_DEFAULT - COORDSYS
	private String espgCode;
	private GeomPoint3d ptOrigem;
	private GeomVector3d xDir;
	
	// PROJECT_LEVELS
	private ArrayList<LevelVO> lsListaNiveis = null;  
	private String nomeNivel;
	private String tituloNivel;
	private double alturaNivel;

	//TABBED_PANELS
	//
	private JTabbedPane tabMainPanel = null; 
	
	private JPanel panProjectInfo = null;
	private JPanel panProjectDefault = null;
	private JPanel panProjectLevels = null;
	
	//FORM_CONTROLS
	//
	// PROJECT_INFO
	private JLabel lblTitProjectInformation = null;
	private JLabel lblCodigoProjeto = null;
	private JLabel lblTituloProjeto = null;
	private JLabel lblDescricaoProjeto = null;
	//
	private JTextField txtCodigoProjeto = null;
	private JTextField txtTituloProjeto = null;
	private JTextField txtDescricaoProjeto = null;

	// PROJECT_ADDRESS
	private JLabel lblTitProjectAddress = null;
	private JLabel lblLogradouro = null;
	private JLabel lblNumero = null;
	private JLabel lblComplemento = null;
	private JLabel lblBairro = null;
	private JLabel lblMunicipio = null;
	private JLabel lblEstado = null;
	private JLabel lblCep = null;
	//
	private JTextField txtLogradouro = null;
	private JTextField txtNumero = null;
	private JTextField txtComplemento = null;
	private JTextField txtBairro = null;
	private JTextField txtMunicipio = null;
	private JTextField txtEstado = null;
	private JTextField txtCep = null;
	
	// PROJECT_REGISTER
	private JLabel lblTitProjectRegister = null;
	private JLabel lblArt = null;
	private JLabel lblNomeResponsavelTecnico = null;
	private JLabel lblRegistroResponsavelTecnico = null;
	private JLabel lblTelefoneResponsavelTecnico = null;
	private JLabel lblEmailResponsavelTecnico = null;
	//
	private JTextField txtArt = null;
	private JTextField txtNomeResponsavelTecnico = null;
	private JTextField txtRegistroResponsavelTecnico = null;
	private JTextField txtTelefoneResponsavelTecnico = null;
	private JTextField txtEmailResponsavelTecnico = null;
	
	// PROJECT_DEFAULT - DREANAGE_PARAMETERS
	private JLabel lblTitProjectDeafultDrenageParameters = null;
	private JLabel lblPluviografo = null;							// local medicao volume chuva
	private JLabel lblCoefManning = null;
	private JLabel lblPeriodoRecorrencia = null;
	//
	private JTextField txtPluviografo = null;						// local medicao volume chuva
	private JTextField txtCoefManning = null;
	private JTextField txtPeriodoRecorrencia = null;

	// PROJECT_DEFAULT - OUTPUT_PARAMETERS
	private JLabel lblTitProjectDefaultOutputParameters = null;
	private JLabel lblEscala = null;
	private JLabel lblPapelLargura = null;
	private JLabel lblPapelAltura = null;	
	//
	private JTextField txtEscala = null;
	private JTextField txtPapelLargura = null;
	private JTextField txtPapelAltura = null;	

	// PROJECT_DEFAULT - COORDSYS
	private JLabel lblTitProjectDefaultCoordsys = null;
	private JLabel lblEspgCode = null;
	private JLabel lblPtOrigem = null;
	private JLabel lblXDir = null;
	//
	private JTextField txtEspgCode = null;
	private JTextField txtPtOrigem = null;
	private JTextField txtXDir = null;
	//
	private JButton btnOrigem = null;

	// PROJECT_LEVELS
	private JLabel lblTitProjectLevels = null;
	private JLabel lblListaNiveis = null;
	private JLabel lblNomeNivel = null;
	private JLabel lblTituloNivel = null;
	private JLabel lblAlturaNivel = null;
	//
	private JList lstListaNiveis = null;
	private JTextField txtNomeNivel = null;
	private JTextField txtTituloNivel = null;
	private JTextField txtAlturaNivel = null;
	//
	private JButton btnAdicionaNivel = null;
	private JButton btnRemoveNivel = null;
	private JButton btnRemoveTodosNiveis = null;

	// CLOSE/CANCEL BUTTONS
	//
	private JButton btnOk = null;
	private JButton btnCancelar = null;

	private int rscode = AppDefs.RSCODE_SETUP_NONE;

	/* Methodes */
	
	private void updateLstListaNiveis()
	{
		LevelListModel model = new LevelListModel( this.lsListaNiveis );
		this.lstListaNiveis.setModel(model);
	}

	private void clearFormFields()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);
		
		this.nomeNivel = AppDefs.DEF_LEVELPATTERN_NOMENIVEL;
		this.tituloNivel = AppDefs.DEF_LEVELPATTERN_TITULONIVEL;
		this.alturaNivel = AppDefs.DEF_LEVELPATTERN_ALTURANIVEL;
		
		if( (this.txtNomeNivel != null) &&
			(this.txtTituloNivel != null) &&
			(this.txtAlturaNivel != null) )
		{
			this.txtNomeNivel.setText(this.nomeNivel);
			this.txtTituloNivel.setText(this.tituloNivel);
			this.txtAlturaNivel.setText( nf3.format(this.alturaNivel) );
		}
	}
	
	// LOADALL_CADLEVELS
	//
	private ArrayList<LevelVO> loadAllCadLevels()
	{
		ArrayList<LevelVO> lsResult = new ArrayList<LevelVO>();
		
		LevelTable oTbl = this.oDocDef.getLevelTable();

		ArrayList<CadLevel> lsLevels = oTbl.getAllLevel();
		for(CadLevel oLevel : lsLevels) {
			String levelName = oLevel.getLevelLocalName();
			String levelText = oLevel.getLevelLocalText();
			double zLevel = oLevel.getZLevel();
			
			LevelVO oItemData = new LevelVO(
				levelName, 
				levelText, 
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_XI, 
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_YI,
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_XF, 
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_YF,
				zLevel);			
			lsResult.add(oItemData);
		}
		
		CmpLevel c = new CmpLevel(true);
		lsResult.sort(c);
		
		return lsResult;
	}
	
	// INIT_FORMDATA
	//
	private void initFormData()
	{
		//PROJECT_INFORMATION
		this.codigoProjeto = this.oCurrProject.getCodigoProjeto();
		this.tituloProjeto = this.oCurrProject.getTituloProjeto();
		this.descricaoProjeto = this.oCurrProject.getDescricaoProjeto();
		
		//PROJECT_ADDRESS
		this.logradouro = this.oCurrProject.getLogradouro();
		this.numero = this.oCurrProject.getNumero();
		this.complemento = this.oCurrProject.getComplemento();
		this.bairro = this.oCurrProject.getBairro();
		this.municipio = this.oCurrProject.getMunicipio();
		this.estado = this.oCurrProject.getEstado();
		this.cep = this.oCurrProject.getCep();
		
		//PROJECT_REGISTER
		this.art = this.oCurrProject.getArt();
		this.nomeResponsavelTecnico = this.oCurrProject.getNomeResponsavelTecnico();
		this.registroResponsavelTecnico = this.oCurrProject.getRegistroResponsavelTecnico();
		this.telefoneResponsavelTecnico = this.oCurrProject.getTelefoneResponsavelTecnico();
		this.emailResponsavelTecnico = this.oCurrProject.getEmailResponsavelTecnico();
		
		//PROJECT_DRENAGE - DEFAULT_PARAMETERS
		this.pluviografo = this.oCurrProject.getPluviografo();
		this.coefManning = this.oCurrProject.getCoefManning();
		this.periodoRecorrencia = this.oCurrProject.getPeriodoRecorrencia();
		
		//PROJECT_OUTPUT - DEFAULT_PARAMETERS
		this.escala = this.oCurrProject.getEscala();
		this.papelLargura = this.oCurrProject.getPapelLargura();
		this.papelAltura = this.oCurrProject.getPapelAltura();	
		
		//PROJECT_COORDINATE_SYSTEM - DEFAULT_PARAMETERS
		this.espgCode = this.oCurrProject.getEspgCode();
		this.ptOrigem = new GeomPoint3d(this.oCurrProject.getPtOrigem());
		this.xDir = new GeomVector3d(this.oCurrProject.getXDir());
		
		// PROJECT_LEVELS
		this.lsListaNiveis = this.loadAllCadLevels();  
		this.clearFormFields();
	}
	
	// INIT_TABPANEL
	//
	private void initTabPanel()
	{
		Insets insets = this.getInsets();

		int x = insets.left + AppDefs.SPACE_W5;
		int y = insets.top + AppDefs.SPACE_H5;

		int w = AppDefs.SETUP_FRAME_WIDTH - AppDefs.SPACE_W5 - AppDefs.SPACE_W5;
		int h = AppDefs.SETUP_FRAME_HEIGHT - AppDefs.SPACE_H5 - AppDefs.BUTTON_H20 - AppDefs.SPACE_H5 - AppDefs.SPACE_H50;
		
		this.tabMainPanel = new JTabbedPane();
		this.tabMainPanel.setBounds(x, y, w, h);
		this.tabMainPanel.setTabPlacement(JTabbedPane.BOTTOM);
		this.add(this.tabMainPanel, BorderLayout.CENTER);
		
		// *** ProjectInfo
		String nProjectInfo = "Project";
		this.panProjectInfo = FormControlUtil.newTabPanel(nProjectInfo);
		this.panProjectInfo.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nProjectInfo, this.panProjectInfo);
		this.tabMainPanel.setMnemonicAt(0, KeyEvent.VK_P);
		
		// *** ProjectDefault
		String nProjectDefault = "Settings";
		this.panProjectDefault = FormControlUtil.newTabPanel(nProjectDefault);
		this.panProjectDefault.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nProjectDefault, this.panProjectDefault);
		this.tabMainPanel.setMnemonicAt(1, KeyEvent.VK_D);
		
		// *** ProjectLevels
		String nProjectLevels = "Levels";
		this.panProjectLevels = FormControlUtil.newTabPanel(nProjectLevels);
		this.panProjectLevels.setBounds(x, y, w, h);
		this.tabMainPanel.addTab(nProjectLevels, this.panProjectLevels);
		this.tabMainPanel.setMnemonicAt(2, KeyEvent.VK_L);
		
	}	

	// INIT_FORM
	//
	private void initForm_ProjectInformation()
	{
		this.setLayout(null);
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_H5;

		//LABELS
		//
		//PROJECT_INFORMATION
		String resProjectInformation = r.getString(R.LBL_PROJECTINFORMATION);
		this.lblTitProjectInformation = FormControlUtil.newLabel(resProjectInformation, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblTitProjectInformation);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resProjectInformationCodigo = r.getString(R.LBL_CODIGOPROJETO);
		this.lblCodigoProjeto = FormControlUtil.newLabel(resProjectInformationCodigo, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblCodigoProjeto);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resTituloProjeto = r.getString(R.LBL_NOMEPROJETO);
		this.lblTituloProjeto = FormControlUtil.newLabel(resTituloProjeto, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblTituloProjeto);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resDescricaoProjeto = r.getString(R.LBL_DESCRICAOPROJETO);
		this.lblDescricaoProjeto = FormControlUtil.newLabel(resDescricaoProjeto, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblDescricaoProjeto);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		//PROJECT_ADDRESS
		String resProjectAddress = r.getString(R.LBL_ENDERECOPROJETO);
		this.lblTitProjectAddress = FormControlUtil.newLabel(resProjectAddress, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblTitProjectAddress);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resLogradouro = r.getString(R.LBL_LOGRADOURO);
		this.lblLogradouro = FormControlUtil.newLabel(resLogradouro, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblLogradouro);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resNumero = r.getString(R.LBL_NUMERO);
		this.lblNumero = FormControlUtil.newLabel(resNumero, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblNumero);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resComplemento = r.getString(R.LBL_COMPLEMENTO);
		this.lblComplemento = FormControlUtil.newLabel(resComplemento, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblComplemento);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resBairro = r.getString(R.LBL_BAIRRO);
		this.lblBairro = FormControlUtil.newLabel(resBairro, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblBairro);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resMunicipio = r.getString(R.LBL_MUNICIPIO);
		this.lblMunicipio = FormControlUtil.newLabel(resMunicipio, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblMunicipio);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resEstado = r.getString(R.LBL_ESTADO);
		this.lblEstado = FormControlUtil.newLabel(resEstado, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblEstado);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resCep = r.getString(R.LBL_CEP);
		this.lblCep = FormControlUtil.newLabel(resCep, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblCep);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		//PROJECT_REGISTER
		String resProjectRegister = r.getString(R.LBL_PROJECTREGISTER);
		this.lblTitProjectRegister = FormControlUtil.newLabel(resProjectRegister, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblTitProjectRegister);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resArt = r.getString(R.LBL_PROJECTREGISTER_ART);
		this.lblArt = FormControlUtil.newLabel(resArt, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblArt);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resNomeResponsavelTecnico = r.getString(R.LBL_PROJECTREGISTER_NOMERESPTECNICO);
		this.lblNomeResponsavelTecnico = FormControlUtil.newLabel(resNomeResponsavelTecnico, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblNomeResponsavelTecnico);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resRegistroResponsavelTecnico = r.getString(R.LBL_PROJECTREGISTER_REGISTRORESPTECNICO);
		this.lblRegistroResponsavelTecnico = FormControlUtil.newLabel(resRegistroResponsavelTecnico, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblRegistroResponsavelTecnico);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resTelefoneResponsavelTecnico = r.getString(R.LBL_PROJECTREGISTER_TELEFONERESPTECNICO);
		this.lblTelefoneResponsavelTecnico = FormControlUtil.newLabel(resTelefoneResponsavelTecnico, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblTelefoneResponsavelTecnico);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resEmailResponsavelTecnico = r.getString(R.LBL_PROJECTREGISTER_EMAILRESPTECNICO);
		this.lblEmailResponsavelTecnico = FormControlUtil.newLabel(resEmailResponsavelTecnico, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectInfo.add(this.lblEmailResponsavelTecnico);
		
		//CONTROLS
		//
		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5);
		yp = insets.top + AppDefs.SPACE_H5;
		
		//PROJECT_INFORMATION
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		this.txtCodigoProjeto = FormControlUtil.newTextField(this.codigoProjeto, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtCodigoProjeto);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtTituloProjeto = FormControlUtil.newTextField(this.tituloProjeto, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtTituloProjeto);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtDescricaoProjeto = FormControlUtil.newTextField(this.descricaoProjeto, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtDescricaoProjeto);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
				
		//PROJECT_ADDRESS
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
				
		this.txtLogradouro = FormControlUtil.newTextField(this.logradouro, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtLogradouro);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtNumero = FormControlUtil.newTextField(this.numero, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtNumero);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtComplemento = FormControlUtil.newTextField(this.complemento, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtComplemento);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtBairro = FormControlUtil.newTextField(this.bairro, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtBairro);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtMunicipio = FormControlUtil.newTextField(this.municipio, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtMunicipio);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtEstado = FormControlUtil.newTextField(this.estado, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtEstado);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtCep = FormControlUtil.newTextField(this.cep, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtCep);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		//PROJECT_REGISTER
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		this.txtArt = FormControlUtil.newTextField(this.art, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtArt);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtNomeResponsavelTecnico = FormControlUtil.newTextField(this.nomeResponsavelTecnico, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtNomeResponsavelTecnico);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtRegistroResponsavelTecnico = FormControlUtil.newTextField(this.registroResponsavelTecnico, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtRegistroResponsavelTecnico);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtTelefoneResponsavelTecnico = FormControlUtil.newTextField(this.telefoneResponsavelTecnico, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtTelefoneResponsavelTecnico);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtEmailResponsavelTecnico = FormControlUtil.newTextField(this.emailResponsavelTecnico, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectInfo.add(this.txtEmailResponsavelTecnico);	
		
	}

	private void initForm_ProjectDefault()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_H5;
		
		//LABELS
		//
		//PROJECT_DRENAGE - DEFAULT_PARAMETERS
		String resParametrosDrenagem = r.getString(R.LBL_PARAMETROSDRENAGEM);
		this.lblTitProjectDeafultDrenageParameters = FormControlUtil.newLabel(resParametrosDrenagem, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblTitProjectDeafultDrenageParameters);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosDrenagemPluviografo = r.getString(R.LBL_PARAMETROSDRENAGEM_PLUVIOGRAFO);
		this.lblPluviografo = FormControlUtil.newLabel(resParametrosDrenagemPluviografo, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblPluviografo);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosDrenagemCoefManning = r.getString(R.LBL_PARAMETROSDRENAGEM_COEFMANNING);
		this.lblCoefManning = FormControlUtil.newLabel(resParametrosDrenagemCoefManning, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblCoefManning);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosDrenagemPeriodoRecorrencia = r.getString(R.LBL_PARAMETROSDRENAGEM_PERIODORECORRENCIA);
		this.lblPeriodoRecorrencia = FormControlUtil.newLabel(resParametrosDrenagemPeriodoRecorrencia, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblPeriodoRecorrencia);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		//PROJECT_OUTPUT - DEFAULT_PARAMETERS
		String resParametrosImpressao = r.getString(R.LBL_PARAMETROSIMPRESSAO);
		this.lblTitProjectDefaultOutputParameters = FormControlUtil.newLabel(resParametrosImpressao, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblTitProjectDefaultOutputParameters);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosImpressaoEscala = r.getString(R.LBL_PARAMETROSIMPRESSAO_ESCALA);
		this.lblEscala = FormControlUtil.newLabel(resParametrosImpressaoEscala, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblEscala);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosImpressaoLarguraPapel = r.getString(R.LBL_PARAMETROSIMPRESSAO_LARGURAPAPEL);
		this.lblPapelLargura = FormControlUtil.newLabel(resParametrosImpressaoLarguraPapel, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblPapelLargura);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosImpressaoAlturaPapel = r.getString(R.LBL_PARAMETROSIMPRESSAO_ALTURAPAPEL);
		this.lblPapelAltura = FormControlUtil.newLabel(resParametrosImpressaoAlturaPapel, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblPapelAltura);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;

		//PROJECT_COORDINATE_SYSTEM - DEFAULT_PARAMETERS
		String resParametrosCoordSys = r.getString(R.LBL_PARAMETROSCOORDSYS);
		this.lblTitProjectDefaultCoordsys = FormControlUtil.newLabel(resParametrosCoordSys, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblTitProjectDefaultCoordsys);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosCoordSysEspg = r.getString(R.LBL_PARAMETROSCOORDSYS_ESPGCODE);
		this.lblEspgCode = FormControlUtil.newLabel(resParametrosCoordSysEspg, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblEspgCode);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosCoordSysOrigem = r.getString(R.LBL_PARAMETROSCOORDSYS_ORIGEM);
		this.lblPtOrigem = FormControlUtil.newLabel(resParametrosCoordSysOrigem, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblPtOrigem);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resParametrosCoordSysDirecaoEixoX = r.getString(R.LBL_PARAMETROSCOORDSYS_DIRECAO_EIXO_X);
		this.lblXDir = FormControlUtil.newLabel(resParametrosCoordSysDirecaoEixoX, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectDefault.add(this.lblXDir);
		
		//CONTROLS
		//
		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5);
		yp = insets.top + AppDefs.SPACE_H5;
		
		//PROJECT_DRENAGE - DEFAULT_PARAMETERS
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		this.txtPluviografo = FormControlUtil.newTextField(this.pluviografo, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtPluviografo);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		String strCoefManning = nf6.format(this.coefManning);
		this.txtCoefManning = FormControlUtil.newTextField(strCoefManning, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtCoefManning);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		String strPeriodoRecorrencia = nf0.format(this.periodoRecorrencia);
		this.txtPeriodoRecorrencia = FormControlUtil.newTextField(strPeriodoRecorrencia, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtPeriodoRecorrencia);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		//PROJECT_OUTPUT - DEFAULT_PARAMETERS
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		String strEscala = nf0.format(this.escala);
		this.txtEscala = FormControlUtil.newTextField(strEscala, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtEscala);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		String strPapelLargura = nf0.format(this.papelLargura);
		this.txtPapelLargura = FormControlUtil.newTextField(strPapelLargura, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtPapelLargura);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		String strPapelAltura = nf0.format(this.papelAltura);
		this.txtPapelAltura = FormControlUtil.newTextField(strPapelAltura, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtPapelAltura);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		//PROJECT_COORDINATE_SYSTEM - DEFAULT_PARAMETERS
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtEspgCode = FormControlUtil.newTextField(this.espgCode, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtEspgCode);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		String strPtOrigem = String.format(
			"(%s, %s, %s)",
			nf6.format(this.ptOrigem.getX()),
			nf6.format(this.ptOrigem.getY()),
			nf6.format(this.ptOrigem.getZ()) ); 
		this.txtPtOrigem = FormControlUtil.newTextField(strPtOrigem, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtPtOrigem);
		xp += (AppDefs.TEXT_W450 + AppDefs.SPACE_W5);
		
		String resBtnOrigem = r.getString(R.BTN_ORIGEM);
		this.btnOrigem = FormControlUtil.newButton(resBtnOrigem, AppDefs.RSCODE_SETUP_ORIGEM, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);
		this.panProjectDefault.add(this.btnOrigem);
		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		String strXDir = String.format(
			"(%s, %s, %s)-(%s, %s, %s)",
			nf6.format(this.xDir.getXI()),
			nf6.format(this.xDir.getYI()),
			nf6.format(this.xDir.getZI()),
			nf6.format(this.xDir.getXF()),
			nf6.format(this.xDir.getYF()),
			nf6.format(this.xDir.getZF()) );
		this.txtXDir = FormControlUtil.newTextField(strXDir, xp, yp, AppDefs.TEXT_W450, AppDefs.TEXT_H20, true, true);
		this.panProjectDefault.add(this.txtXDir);
		
	}

	private void initForm_ProjectLevels()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		LevelListModel model = new LevelListModel( this.lsListaNiveis );
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_H5;

		//LABELS
		//
		//PROJECT_LEVELS
		String resTitProjectLevels = r.getString(R.LBL_PROJECTLEVELS);
		this.lblTitProjectLevels = FormControlUtil.newLabel(resTitProjectLevels, xp, yp, AppDefs.LABEL_W500, AppDefs.LABEL_H20, true);
		this.panProjectLevels.add(this.lblTitProjectLevels);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resListaNiveis = r.getString(R.LBL_PROJECTLEVELS_LEVELLIST);
		this.lblListaNiveis = FormControlUtil.newLabel(resListaNiveis, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectLevels.add(this.lblListaNiveis);
		yp += AppDefs.LIST_H250 + AppDefs.SPACE_H5;
		
		String resNomeNivel = r.getString(R.LBL_PROJECTLEVELS_LEVELNAME);
		this.lblNomeNivel = FormControlUtil.newLabel(resNomeNivel, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectLevels.add(this.lblNomeNivel);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resTituloNivel = r.getString(R.LBL_PROJECTLEVELS_LEVELTITLE);
		this.lblTituloNivel = FormControlUtil.newLabel(resTituloNivel, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectLevels.add(this.lblTituloNivel);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		String resAlturaNivel = r.getString(R.LBL_PROJECTLEVELS_LEVELHEIGHT);
		this.lblAlturaNivel = FormControlUtil.newLabel(resAlturaNivel, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.panProjectLevels.add(this.lblAlturaNivel);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_H5;
		
		//CONTROLS
		//
		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5);
		yp = insets.top + AppDefs.SPACE_H5;
		
		//PROJECT_LEVELS
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.lstListaNiveis = FormControlUtil.newList(this.panProjectLevels, model, xp, yp, AppDefs.LIST_W650, AppDefs.LIST_H250, true);   
		yp += AppDefs.LIST_H250 + AppDefs.SPACE_H5;

		this.txtNomeNivel = FormControlUtil.newTextField(this.nomeNivel, xp, yp, AppDefs.TEXT_W650, AppDefs.TEXT_H20, true, true);
		this.panProjectLevels.add(this.txtNomeNivel);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		this.txtTituloNivel = FormControlUtil.newTextField(this.tituloNivel, xp, yp, AppDefs.TEXT_W650, AppDefs.TEXT_H20, true, true);
		this.panProjectLevels.add(this.txtTituloNivel);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;

		String strAlturaNivel = nf3.format( this.alturaNivel );
		this.txtAlturaNivel = FormControlUtil.newTextField(strAlturaNivel, xp, yp, AppDefs.TEXT_W150, AppDefs.TEXT_H20, true, true);
		this.panProjectLevels.add(this.txtAlturaNivel);
		yp += AppDefs.TEXT_H20 + AppDefs.SPACE_H5;
		
		//BUTTONS
		//
		String resBtnAdicionaNivel = r.getString(R.BTN_ADDLEVEL);
		this.btnAdicionaNivel = FormControlUtil.newButton(resBtnAdicionaNivel, AppDefs.RSCODE_SETUP_ADICIONANIVEL, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.panProjectLevels.add(this.btnAdicionaNivel);
		xp += AppDefs.LABEL_W100 + AppDefs.SPACE_W5;
		
		String resBtnRemoveNivel = r.getString(R.BTN_REMOVELEVEL);
		this.btnRemoveNivel = FormControlUtil.newButton(resBtnRemoveNivel, AppDefs.RSCODE_SETUP_REMOVENIVEL, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.panProjectLevels.add(this.btnRemoveNivel);
		xp += AppDefs.LABEL_W100 + AppDefs.SPACE_W5;
		
		String resBtnRemoveTodosNiveis = r.getString(R.BTN_REMOVEALLLEVELS);
		this.btnRemoveTodosNiveis = FormControlUtil.newButton(resBtnRemoveTodosNiveis, AppDefs.RSCODE_SETUP_REMOVETODOSNIVEIS, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.panProjectLevels.add(this.btnRemoveTodosNiveis);
		
	}

	private void initForm()
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		this.initTabPanel();
		
		this.initForm_ProjectInformation();
		this.initForm_ProjectDefault();
		this.initForm_ProjectLevels();
		
		//BUTTONS
		//
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SETUP_FRAME_WIDTH - (AppDefs.SPACE_W5 + AppDefs.BUTTON_W100);
		int yp = insets.top + AppDefs.SETUP_FRAME_HEIGHT - (AppDefs.SPACE_H5 + AppDefs.BUTTON_H20 + AppDefs.SPACE_H5 + AppDefs.SPACE_H40);
		
		String resBtnCancelar = r.getString(R.BTN_CANCELAR);
		this.btnCancelar = FormControlUtil.newButton(resBtnCancelar, AppDefs.RSCODE_SETUP_CANCELAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnCancelar);		
		xp -= (AppDefs.SPACE_H5 + AppDefs.BUTTON_W100);

		String resBtnOk = r.getString(R.BTN_OK);
		this.btnOk = FormControlUtil.newButton(resBtnOk, AppDefs.RSCODE_SETUP_OK, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnOk);
		
	}
	
//Public 
	
	public SetupPanel(SetupFrame parentFrame)
	{
		super(parentFrame);
	}
	
	/* Methodes */
	
	public void init(CadDocumentDef oDocDef, CadProjectDef oCurrProject)
	{
		this.oDocDef = oDocDef;
		this.oCurrProject = oCurrProject;

		this.lsListaNiveis = this.loadAllCadLevels();  
		
		this.initFormData();
		this.initForm();
	}

	public boolean validateForm_levels() 
	{
		String errmsg = "";
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		//PROJECT_LEVELS
		this.nomeNivel = this.txtNomeNivel.getText();
		this.tituloNivel = this.txtTituloNivel.getText();
		this.alturaNivel = StringUtil.safeDbl(nf6, this.txtAlturaNivel.getText());

		//VALIDATE_FORM_DATA
		//	
		//PROJECT_LEVELS
		if( "".equals(this.nomeNivel) )
			errmsg += "Nome do nivel";			
		
		if( "".equals(this.tituloNivel) )
			errmsg += "Titulo do nivel";
		
		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}

		return true;
	}
	
	public boolean validateForm()
	{
		String errmsg = "";
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		//TEMPORARY_FORM_DATA
		//
		String strCoefManning = this.txtCoefManning.getText();
		String strPeriodoRecorrencia = this.txtPeriodoRecorrencia.getText();
		//
		String strEscala = this.txtEscala.getText();
		String strPapelLargura = this.txtPapelLargura.getText();
		String strPapelAltura = this.txtPapelAltura.getText();

		//LOAD_FORM_DATA
		//
		this.codigoProjeto = this.txtCodigoProjeto.getText();
		this.tituloProjeto = this.txtTituloProjeto.getText();
		this.descricaoProjeto = this.txtDescricaoProjeto.getText();
		//
		this.logradouro = this.txtLogradouro.getText();
		this.numero = this.txtNumero.getText();
		this.complemento = this.txtComplemento.getText();
		this.bairro = this.txtBairro.getText();
		this.municipio = this.txtMunicipio.getText();
		this.estado = this.txtEstado.getText();
		this.cep = this.txtCep.getText();
		//
		this.art = this.txtArt.getText();
		//
		this.nomeResponsavelTecnico = this.txtNomeResponsavelTecnico.getText();
		this.registroResponsavelTecnico = this.txtRegistroResponsavelTecnico.getText();
		this.telefoneResponsavelTecnico = this.txtTelefoneResponsavelTecnico.getText();
		this.emailResponsavelTecnico = this.txtEmailResponsavelTecnico.getText();
		//
		this.pluviografo = this.txtPluviografo.getText();							// local medicao volume chuva
		//
		this.coefManning = StringUtil.safeDbl(nf6, strCoefManning);
		this.periodoRecorrencia = StringUtil.safeInt(strPeriodoRecorrencia);
		//
		this.escala = StringUtil.safeDbl(nf0, strEscala);
		this.papelLargura = StringUtil.safeDbl(nf0, strPapelLargura);
		this.papelAltura = StringUtil.safeDbl(nf0, strPapelAltura);
		//
		this.espgCode = this.txtEspgCode.getText();
		//
		this.coefManning = StringUtil.safeDbl(nf6, strCoefManning);
		this.periodoRecorrencia = StringUtil.safeInt(strPeriodoRecorrencia);
		//
		this.escala = StringUtil.safeDbl(nf0, strEscala);
		this.papelLargura = StringUtil.safeDbl(nf0, strPapelLargura);
		this.papelAltura = StringUtil.safeDbl(nf0, strPapelAltura);
		
		//VALIDATE_FORM_DATA
		//	
		
		//PROJECT_INFORMATION
		
		if( "".equals(this.codigoProjeto) )
			errmsg += "Codigo";			
		
		if( "".equals(this.tituloProjeto) )
			errmsg += "Projeto";
		
		if( "".equals(this.descricaoProjeto) )
			errmsg += "Descricao";

		//PROJECT_ADDRESS
		
		if( "".equals(this.logradouro) )
			errmsg += "Logradouro";			
		
		if( "".equals(this.numero) )
			errmsg += "Numero";			
		
		if( "".equals(this.complemento) )
			errmsg += "Complemento";			
		
		if( "".equals(this.bairro) )
			errmsg += "Bairro";			
		
		if( "".equals(this.municipio) )
			errmsg += "Municipio";			
		
		if( "".equals(this.estado) )
			errmsg += "Estado";			
		
		if( "".equals(this.cep) )
			errmsg += "CEP";			
		
		//PROJECT_REGISTER
		
		if( "".equals(this.art) )
			errmsg += "ART";			
		
		if( "".equals(this.nomeResponsavelTecnico) )
			errmsg += "Resp. Tecnico";			
		
		if( "".equals(this.registroResponsavelTecnico) )
			errmsg += "Registro";			
		
		if( "".equals(this.telefoneResponsavelTecnico) )
			errmsg += "Telefone";			
		
		if( "".equals(this.emailResponsavelTecnico) )
			errmsg += "E-mail";			

		//PROJECT_DRENAGE - DEFAULT PARAMETERS
		
		if( "".equals(this.pluviografo) )
			errmsg += "Pluviografo";			
		
		if( "".equals(strCoefManning) )
			errmsg += "Coef. Manning";			
		
		if( "".equals(strPeriodoRecorrencia) )
			errmsg += "Periodo Recorrencia";			

		//PROJECT_OUTPUT - DEFAULT PARAMETERS
		
		if( "".equals(strEscala) )
			errmsg += "Escala";			
		
		if( "".equals(strPapelLargura) )
			errmsg += "Largura Papel";			
		
		if( "".equals(strPapelAltura) )
			errmsg += "Altura Papel";			

		//PROJECT_COORDSYS - DEFAULT PARAMETERS
		
		if( "".equals(this.espgCode) )
			errmsg += "ESPG";			
		
		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}
		
		//VALIDATE_FORM_DATA
		//		

		//PROJECT_DRENAGE - DEFAULT PARAMETERS

		if(this.coefManning < AppDefs.MATHPREC_MIN) {
			String warnmsg = String.format("Valor do coeficiente de manning deve ser superior a %s.", this.coefManning);
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", warnmsg, this.getClass());
			return false;
		}
		
		if(this.periodoRecorrencia < 1) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Periodo de recorrencia deve ser igual ou superior a 1.", this.getClass());
			return false;
		}
		
		//PROJECT_OUTPUT - DEFAULT PARAMETERS

		if(this.escala < 1.0) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Valor da escala deve ser superior a 1:1.", this.getClass());
			return false;
		}

		if(this.papelLargura < 1.0) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Valor da largura do papel deve ser superior a 1 mm.", this.getClass());
			return false;
		}

		if(this.papelAltura < 1.0) {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos invalidos", "Valor da altura do papel deve ser superior a 1 mm.", this.getClass());
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
			this.oCurrProject.setCodigoProjeto(this.codigoProjeto);
			this.oCurrProject.setTituloProjeto(this.tituloProjeto);
			this.oCurrProject.setDescricaoProjeto(this.descricaoProjeto);
			//
			this.oCurrProject.setLogradouro(this.logradouro);
			this.oCurrProject.setNumero(this.numero);
			this.oCurrProject.setComplemento(this.complemento);
			this.oCurrProject.setBairro(this.bairro);
			this.oCurrProject.setMunicipio(this.municipio);
			this.oCurrProject.setEstado(this.estado);
			this.oCurrProject.setCep(this.cep);
			//
			this.oCurrProject.setArt(this.art);
			//
			this.oCurrProject.setNomeResponsavelTecnico(this.nomeResponsavelTecnico);
			this.oCurrProject.setRegistroResponsavelTecnico(this.registroResponsavelTecnico);
			this.oCurrProject.setTelefoneResponsavelTecnico(this.telefoneResponsavelTecnico);
			this.oCurrProject.setEmailResponsavelTecnico(this.emailResponsavelTecnico);
			//
			this.oCurrProject.setPluviografo(this.pluviografo);					// local medicao volume chuva
			this.oCurrProject.setCoefManning(this.coefManning);
			this.oCurrProject.setPeriodoRecorrencia(this.periodoRecorrencia);
			//
			this.oCurrProject.setEscala(this.escala);
			this.oCurrProject.setPapelLargura(this.papelLargura);
			this.oCurrProject.setPapelAltura(this.papelAltura);	
			//
			this.oCurrProject.setEspgCode(this.espgCode);
			//
			//oNewProject.setPtOrigem(this.ptOrigem);
			//oNewProject.setXDir(this.xDir);

			this.oDocDef.updateAllCadLevels(this.lsListaNiveis);
			
			//CALLBACK
			this.rscode = AppDefs.RSCODE_SETUP_OK;			
			this.parentFrame.actionResultListener(new ResultEvent(this.rscode, oCurrProject));
			
			this.parentFrame.dispose();
		}
	}
		
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_SETUP_CANCELAR;
		this.parentFrame.actionResultListener(new ResultEvent(rscode, null));

		this.parentFrame.dispose();
	}
	
	public void doActionOrigem(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_SETUP_ORIGEM;
		this.parentFrame.actionResultListener(new ResultEvent(rscode, null));
	
		this.parentFrame.dispose();
	}

	public void doActionAdicionaNivel(ActionEvent e) 
	{
		if( this.validateForm_levels() )
		{
			LevelVO o = new LevelVO(
				this.nomeNivel, 
				this.tituloNivel, 
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_XI, 
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_YI,
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_XF, 
				AppDefs.DEF_DEFAULT_PROJECT_LEVEL_YF,
				this.alturaNivel);
			this.lsListaNiveis.add(o);
			
			CmpLevel c = new CmpLevel(true);
			this.lsListaNiveis.sort(c);
			
			this.updateLstListaNiveis();
			this.clearFormFields();
		}
	}

	public void doActionRemoveNivel(ActionEvent e) 
	{
		ItemDataVO o = (ItemDataVO)this.lstListaNiveis.getSelectedValue();
		if(o == null) return;
		
		String strNomeNivel = o.getItemDataId();

		int pos = ListUtil.findPosItemDataById(strNomeNivel, this.lsListaNiveis);
		if(pos != -1) {		
			this.lsListaNiveis.remove(pos);
			
			this.updateLstListaNiveis();
			this.clearFormFields();
		}
	}

	public void doActionRemoveTodosNiveis(ActionEvent e) 
	{
		this.lsListaNiveis.clear();
		
		this.updateLstListaNiveis();
	}
	
	/* ACTION_EVENTS */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmdAction = StringUtil.safeInt( e.getActionCommand() );
		
		if(cmdAction == AppDefs.RSCODE_SETUP_OK) {
			doActionOk(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SETUP_CANCELAR) {
			doActionCancelar(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SETUP_ORIGEM) {
			doActionOrigem(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SETUP_ADICIONANIVEL) {
			doActionAdicionaNivel(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SETUP_REMOVENIVEL) {
			doActionRemoveNivel(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SETUP_REMOVETODOSNIVEIS) {
			doActionRemoveTodosNiveis(e);						
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
	
}
