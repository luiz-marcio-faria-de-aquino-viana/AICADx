/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class SetupPanel extends BasePanel
{
//Private
	private CadProjectDef oCurrProject = null;

	//VARIABLES
	//
	private String codigoProjeto;
	private String tituloProjeto;
	private String descricaoProjeto;
	//
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;
	//
	private String art;
	//
	private String nomeResponsavelTecnico;
	private String registroResponsavelTecnico;
	private String telefoneResponsavelTecnico;
	private String emailResponsavelTecnico;
	//
	private String pluviografo;								// local medicao volume chuva
	private double coefManning;
	private double periodoRecorrencia;
	//
	private double escala;
	private double papelLargura;
	private double papelAltura;	
	//
	private String espgCode;
	private GeomPoint3d ptOrigem;
	private GeomVector3d xDir;

	//TABBED_PANELS
	//
	private JTabbedPane tabMainPanel = null; 
	
	private JPanel panProjectInfo = null;
	private JPanel panProjectDefault = null;
	
	//LABELS
	//
	private JLabel lblTitProjectInformation = null;
	private JLabel lblCodigoProjeto = null;
	private JLabel lblTituloProjeto = null;
	private JLabel lblDescricaoProjeto = null;
	//
	private JLabel lblTitProjectAddress = null;
	private JLabel lblLogradouro = null;
	private JLabel lblNumero = null;
	private JLabel lblComplemento = null;
	private JLabel lblBairro = null;
	private JLabel lblMunicipio = null;
	private JLabel lblEstado = null;
	private JLabel lblCep = null;
	//
	private JLabel lblTitProjectRegister = null;
	private JLabel lblArt = null;
	private JLabel lblNomeResponsavelTecnico = null;
	private JLabel lblRegistroResponsavelTecnico = null;
	private JLabel lblTelefoneResponsavelTecnico = null;
	private JLabel lblEmailResponsavelTecnico = null;
	//
	private JLabel lblTitProjectDeafultDrenageParameters = null;
	private JLabel lblPluviografo = null;							// local medicao volume chuva
	private JLabel lblCoefManning = null;
	private JLabel lblPeriodoRecorrencia = null;
	//
	private JLabel lblTitProjectDefaultOutputParameters = null;
	private JLabel lblEscala = null;
	private JLabel lblPapelLargura = null;
	private JLabel lblPapelAltura = null;	
	//
	private JLabel lblTitProjectDefaultCoordsys = null;
	private JLabel lblEspgCode = null;
	private JLabel lblPtOrigem = null;
	private JLabel lblXDir = null;
	
	//CONTROLS
	//
	private JTextField txtCodigoProjeto = null;
	private JTextField txtTituloProjeto = null;
	private JTextField txtDescricaoProjeto = null;
	//
	private JTextField txtLogradouro = null;
	private JTextField txtNumero = null;
	private JTextField txtComplemento = null;
	private JTextField txtBairro = null;
	private JTextField txtMunicipio = null;
	private JTextField txtEstado = null;
	private JTextField txtCep = null;
	//
	private JTextField txtArt = null;
	private JTextField txtNomeResponsavelTecnico = null;
	private JTextField txtRegistroResponsavelTecnico = null;
	private JTextField txtTelefoneResponsavelTecnico = null;
	private JTextField txtEmailResponsavelTecnico = null;
	//
	private JTextField txtPluviografo = null;						// local medicao volume chuva
	private JTextField txtCoefManning = null;
	private JTextField txtPeriodoRecorrencia = null;
	//
	private JTextField txtEscala = null;
	private JTextField txtPapelLargura = null;
	private JTextField txtPapelAltura = null;	
	//
	private JTextField txtEspgCode = null;
	private JTextField txtPtOrigem = null;
	private JTextField txtXDir = null;
	//
	private JButton btnOrigem = null;
	//
	private JButton btnOk = null;
	private JButton btnCancelar = null;

	private int rscode = AppDefs.RSCODE_SETUP_NONE;

	/* Methodes */
	
	private void initFormData()
	{
		//PROJECT_INFORMATION
		this.codigoProjeto = oCurrProject.getCodigoProjeto();
		this.tituloProjeto = oCurrProject.getTituloProjeto();
		this.descricaoProjeto = oCurrProject.getDescricaoProjeto();
		//PROJECT_ADDRESS
		this.logradouro = oCurrProject.getLogradouro();
		this.numero = oCurrProject.getNumero();
		this.complemento = oCurrProject.getComplemento();
		this.bairro = oCurrProject.getBairro();
		this.municipio = oCurrProject.getMunicipio();
		this.estado = oCurrProject.getEstado();
		this.cep = oCurrProject.getCep();
		//PROJECT_REGISTER
		this.art = oCurrProject.getArt();
		this.nomeResponsavelTecnico = oCurrProject.getNomeResponsavelTecnico();
		this.registroResponsavelTecnico = oCurrProject.getRegistroResponsavelTecnico();
		this.telefoneResponsavelTecnico = oCurrProject.getTelefoneResponsavelTecnico();
		this.emailResponsavelTecnico = oCurrProject.getEmailResponsavelTecnico();
		//PROJECT_DRENAGE - DEFAULT_PARAMETERS
		this.pluviografo = oCurrProject.getPluviografo();
		this.coefManning = oCurrProject.getCoefManning();
		this.periodoRecorrencia = oCurrProject.getPeriodoRecorrencia();
		//PROJECT_OUTPUT - DEFAULT_PARAMETERS
		this.escala = oCurrProject.getEscala();
		this.papelLargura = oCurrProject.getPapelLargura();
		this.papelAltura = oCurrProject.getPapelAltura();	
		//PROJECT_COORDINATE_SYSTEM - DEFAULT_PARAMETERS
		this.espgCode = oCurrProject.getEspgCode();
		this.ptOrigem = new GeomPoint3d(oCurrProject.getPtOrigem());
		this.xDir = new GeomVector3d(oCurrProject.getXDir());
	}
	
	private void initTabPanel()
	{
		AppCadMain cad = AppCadMain.getCad();
		CadDocumentDef doc = cad.getCurrDocumentDef();
		
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
		this.tabMainPanel.setMnemonicAt(0, KeyEvent.VK_D);
	}	

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

	private void initForm()
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		this.initTabPanel();
		
		this.initForm_ProjectInformation();
		this.initForm_ProjectDefault();
		
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
	
	public void init(CadProjectDef oCurrProject)
	{
		this.oCurrProject = oCurrProject;

		this.initFormData();
		this.initForm();
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
			oCurrProject.setCodigoProjeto(this.codigoProjeto);
			oCurrProject.setTituloProjeto(this.tituloProjeto);
			oCurrProject.setDescricaoProjeto(this.descricaoProjeto);
			//
			oCurrProject.setLogradouro(this.logradouro);
			oCurrProject.setNumero(this.numero);
			oCurrProject.setComplemento(this.complemento);
			oCurrProject.setBairro(this.bairro);
			oCurrProject.setMunicipio(this.municipio);
			oCurrProject.setEstado(this.estado);
			oCurrProject.setCep(this.cep);
			//
			oCurrProject.setArt(this.art);
			//
			oCurrProject.setNomeResponsavelTecnico(this.nomeResponsavelTecnico);
			oCurrProject.setRegistroResponsavelTecnico(this.registroResponsavelTecnico);
			oCurrProject.setTelefoneResponsavelTecnico(this.telefoneResponsavelTecnico);
			oCurrProject.setEmailResponsavelTecnico(this.emailResponsavelTecnico);
			//
			oCurrProject.setPluviografo(this.pluviografo);					// local medicao volume chuva
			oCurrProject.setCoefManning(this.coefManning);
			oCurrProject.setPeriodoRecorrencia(this.periodoRecorrencia);
			//
			oCurrProject.setEscala(this.escala);
			oCurrProject.setPapelLargura(this.papelLargura);
			oCurrProject.setPapelAltura(this.papelAltura);	
			//
			oCurrProject.setEspgCode(this.espgCode);
			//
			//oNewProject.setPtOrigem(this.ptOrigem);
			//oNewProject.setXDir(this.xDir);

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
		return oCurrProject;
	}

	public void setCurrProject(CadProjectDef oCurrProject) {
		this.oCurrProject = oCurrProject;
	}
	
}
