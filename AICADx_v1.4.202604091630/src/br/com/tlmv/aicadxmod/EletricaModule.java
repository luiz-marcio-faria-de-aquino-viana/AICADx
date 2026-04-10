/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * EletricaModule.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 01/02/2026
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

package br.com.tlmv.aicadxmod;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxmod.eletrica.vo.CapacidadeConducaoCorrenteVO;
import br.com.tlmv.aicadxmod.eletrica.vo.CategoriaPontoEletricoVO;
import br.com.tlmv.aicadxmod.eletrica.vo.DisjuntorProtecaoVO;
import br.com.tlmv.aicadxmod.eletrica.vo.FatorCorrecaoAgrupamentoVO;
import br.com.tlmv.aicadxmod.eletrica.vo.PontoEletricoVO;

public class EletricaModule implements IModule, ActionListener 
{
//Private Static
	private static final String rsCodeCategoriaPontosEletricosSelected = Integer.toString(AppDefs.RSCODE_CATEGORIA_PONTOS_ELETRICOS_SELECTED);
	private static final String rsCodePontosEletricosSelected = Integer.toString(AppDefs.RSCODE_PONTOS_ELETRICOS_SELECTED);
		
	private static int gSeqId = AppDefs.DEF_SEQ_INIT;
	
	private static int gTblFiaSeqNum = AppDefs.DEF_TBLFIA_SEQNUM_INIT;
	
	private static IModule gAppMod = null;
	
//Private
	private AppMain app = null;	
	private AppCadMain cad = null;
	
	private int fiamode = AppDefs.FIAMODE_OFF;

	//TABELAS
	//
	private ArrayList<CapacidadeConducaoCorrenteVO> lsCapacidadeConducaoCorrente = null;  
	private ArrayList<DisjuntorProtecaoVO> lsDisjuntorProtecao = null;  
	private ArrayList<FatorCorrecaoAgrupamentoVO> lsFatorCorrecaoAgrupamento = null;
	
	//DATALIST
	//
    private ArrayList<CategoriaPontoEletricoVO> lsCategoria220v = null;
    private ArrayList<CategoriaPontoEletricoVO> lsCategoria380v = null;
    //CURR_DATALIST
    private ArrayList<CategoriaPontoEletricoVO> currLsCategoria = null;
	
    //FORM_VARS
    //
    private CategoriaPontoEletricoVO currCategoria = null;
    private PontoEletricoVO currPontoEletrico = null;
    
    //FORM_CONTROLS
    //
	//MENUS
	//private JMenu mnu1 = null;
	private JMenu mnu2 = null;
	//TOOLBARS
	private JPanel mnuToolbar1 = null;
	private JPanel mnuToolbar2 = null;
	private JPanel mnuToolbar3 = null;
	//FORM_LABELS
	private JLabel lblPontoEletrico = null;
	//FORM_CONTROLS
	private JComboBox cbxCategoria = null;
	private JComboBox cbxPontoEletrico = null;
	
    /* Methodes */
    
    private CategoriaPontoEletricoVO findCategoriaPontoEletricoByDescricao(ArrayList<CategoriaPontoEletricoVO> lsCategoria, String descricaoCategoriaPontoEletrico)
    {
        for(CategoriaPontoEletricoVO oCategoriaPontoEletrico : lsCategoria) {
        	if( descricaoCategoriaPontoEletrico.equals(oCategoriaPontoEletrico.getDescricao()) )
        		return oCategoriaPontoEletrico;
        }
        return null;
    }
    
    private ArrayList<CategoriaPontoEletricoVO> loadDefPontoEletrico(String defPontoEletricoFile)
    {
    	ArrayList<CategoriaPontoEletricoVO> lsCategoria = new ArrayList<CategoriaPontoEletricoVO>();
    	
    	ArrayList<String> lsStr = FileUtil.readDataAsList(defPontoEletricoFile);
    	int szLsStr = lsStr.size();
        for(int i = 1; i < szLsStr; i++) {
        	String sbuf = lsStr.get(i);

        	PontoEletricoVO pontoEletrico = new PontoEletricoVO(EletricaModule.gSeqId++, sbuf);

            CategoriaPontoEletricoVO categoria = this.findCategoriaPontoEletricoByDescricao(lsCategoria, pontoEletrico.getDescricaoCategoriaPontoEletrico());
            if(categoria == null) {
                categoria = new CategoriaPontoEletricoVO(EletricaModule.gSeqId++, pontoEletrico.getDescricaoCategoriaPontoEletrico());
                lsCategoria.add(categoria);
            }
            pontoEletrico.setCategoriaPontoEletricoId(categoria.getCategoriaPontoEletricoId());

            categoria.addPontoEletrico(pontoEletrico);
        }
        return lsCategoria;
    }    
	
    /* TABELAS */
	
    // Tabela: CapacidadeConducaoCorrente
    //
	private ArrayList<CapacidadeConducaoCorrenteVO> loadTableCapacidadeConducaoCorrente() {
		this.lsCapacidadeConducaoCorrente = new ArrayList<CapacidadeConducaoCorrenteVO>();  
		
		try {
			AppMain app = AppMain.getApp();
			
			AppCtx ctx = app.getCtx();

			String dataDbFileModBase = ctx.getDataDir();

			String fullFileName = dataDbFileModBase + AppDefs.DAT_FILE_001;
			
			File f = new File(fullFileName);
			if( f.exists() ) {
				ArrayList<String> lsStr = FileUtil.readDataAsList(f, AppDefs.DEF_COMMENT_MARK);
				for(String str : lsStr) {
					CapacidadeConducaoCorrenteVO o = CapacidadeConducaoCorrenteVO.createFrom(str);
					this.lsCapacidadeConducaoCorrente.add(o);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return this.lsCapacidadeConducaoCorrente;
	}

	private void debugTableCapacidadeConducaoCorrente(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		for(CapacidadeConducaoCorrenteVO o : this.lsCapacidadeConducaoCorrente) {
			o.debug(debugLevel);
		}
	}
	
    // Tabela: FatorCorrecaoAgrupamentoVO
    //
	private ArrayList<FatorCorrecaoAgrupamentoVO> loadTableFatorCorrecaoAgrupamento() {
		this.lsFatorCorrecaoAgrupamento = new ArrayList<FatorCorrecaoAgrupamentoVO>();  
		
		try {
			AppMain app = AppMain.getApp();
			
			AppCtx ctx = app.getCtx();

			String dataDbFileModBase = ctx.getDataDir();

			String fullFileName = dataDbFileModBase + AppDefs.DAT_FILE_003;
			
			File f = new File(fullFileName);
			if( f.exists() ) {
				ArrayList<String> lsStr = FileUtil.readDataAsList(f, AppDefs.DEF_COMMENT_MARK);
				for(String str : lsStr) {
					FatorCorrecaoAgrupamentoVO o = FatorCorrecaoAgrupamentoVO.createFrom(str);
					this.lsFatorCorrecaoAgrupamento.add(o);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return this.lsFatorCorrecaoAgrupamento;
	}

	private void debugTableFatorCorrecaoAgrupamento(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		for(FatorCorrecaoAgrupamentoVO o : this.lsFatorCorrecaoAgrupamento) {
			o.debug(debugLevel);
		}
	}
	
    // Tabela: DisjuntorProtecao
    //
	private ArrayList<DisjuntorProtecaoVO> loadTableDisjuntorProtecao() {
		this.lsDisjuntorProtecao = new ArrayList<DisjuntorProtecaoVO>();  
		
		try {
			AppMain app = AppMain.getApp();
			
			AppCtx ctx = app.getCtx();

			String dataDbFileModBase = ctx.getDataDir();

			String fullFileName = dataDbFileModBase + AppDefs.DAT_FILE_002;
			
			File f = new File(fullFileName);
			if( f.exists() ) {
				ArrayList<String> lsStr = FileUtil.readDataAsList(f, AppDefs.DEF_COMMENT_MARK);
				for(String str : lsStr) {
					DisjuntorProtecaoVO o = DisjuntorProtecaoVO.createFrom(str);
					this.lsDisjuntorProtecao.add(o);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return this.lsDisjuntorProtecao;
	}

	private void debugTableDisjuntorProtecao(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		for(DisjuntorProtecaoVO o : this.lsDisjuntorProtecao) {
			o.debug(debugLevel);
		}
	}

	private void loadAllTables()
	{
		// Tabela: CapacidadeConducaoCorrente
		//
		this.loadTableCapacidadeConducaoCorrente();
		this.debugTableCapacidadeConducaoCorrente(AppDefs.DEBUG_LEVEL25);

		// Tabela: DisjuntorProtecao
		//
		this.loadTableDisjuntorProtecao();
		this.debugTableDisjuntorProtecao(AppDefs.DEBUG_LEVEL25);

		// Tabela: FatorCorrecaoAgrupamentoVO
		//
		this.loadTableFatorCorrecaoAgrupamento();
		this.debugTableFatorCorrecaoAgrupamento(AppDefs.DEBUG_LEVEL25);
	}
        
	//MENU: ELETRICA
	//
	private int createMenu_ELETRICA2(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu2 = new JMenu(AppDefs.MNU_EL2);

			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL1_PROPRIEDADE_PONTOELETRICA, AppDefs.ACTION_EL1_PROPRIEDADE_PONTOELETRICA, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL1_MATCHPROP_PONTOELETRICA, AppDefs.ACTION_EL1_MATCHPROP_PONTOELETRICA, listener));
			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_PROCESSA_FIACAO, AppDefs.ACTION_EL2_PROCESSA_FIACAO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL1_FIACAOELETRICA_ONOFF, AppDefs.ACTION_EL1_FIACAOELETRICA_ONOFF, listener));
			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_ELETRODUTO_FIACAO, AppDefs.ACTION_EL2_ELETRODUTO_FIACAO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_SETA_FIACAO, AppDefs.ACTION_EL2_SETA_FIACAO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INDICADOR_TABELA_FIACAO, AppDefs.ACTION_EL2_INDICADOR_TABELA_FIACAO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_TABELA_FIACAO, AppDefs.ACTION_EL2_INSERE_TABELA_FIACAO, listener));
			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL1_QDLF_INSERE_DEFINICAO_QUADRO_DISTRIBUICAO, AppDefs.ACTION_EL1_QDLF_INSERE_DEFINICAO_QUADRO_DISTRIBUICAO, listener));
			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_LEVANTAMENTO_CARGAS, AppDefs.ACTION_EL2_LEVANTAMENTO_CARGAS, listener));
//			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_GERENCIADOR_CIRCUITOS, AppDefs.ACTION_EL2_GERENCIADOR_CIRCUITOS, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_DIAGRAMA_UNIFILAR, AppDefs.ACTION_EL2_DIAGRAMA_UNIFILAR, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_QUADRO_CARGAS, AppDefs.ACTION_EL2_QUADRO_CARGAS, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_ALTERA_QUADRO_CARGAS, AppDefs.ACTION_EL2_ALTERA_QUADRO_CARGAS, listener));
			this.mnu2.add(new JSeparator());
//			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_ANALISE_POR_QUADRO, AppDefs.ACTION_EL2_ANALISE_POR_QUADRO, listener));
//			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_ANALISE_POR_CIRCUITO, AppDefs.ACTION_EL2_ANALISE_POR_CIRCUITO, listener));
//			this.mnu2.add(new JSeparator());
//			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_COPIA_PONTOS, AppDefs.ACTION_EL2_COPIA_PONTOS, listener));				
//			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_MIRROR_PONTOS, AppDefs.ACTION_EL2_MIRROR_PONTOS, listener));
//			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_TETO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_TETO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_PAREDE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PAREDE, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_PISO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PISO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_APARENTE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_APARENTE, listener));
			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_MULT_TETO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_TETO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_MULT_PAREDE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PAREDE, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_MULT_PISO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PISO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_INSERE_ELETRODUTO_MULT_APARENTE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_APARENTE, listener));
			this.mnu2.add(new JSeparator());
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_TROCA_CIRCUITO, AppDefs.ACTION_EL2_TROCA_CIRCUITO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_TROCA_COMANDO, AppDefs.ACTION_EL2_TROCA_COMANDO, listener));
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_TROCA_ORIGEM, AppDefs.ACTION_EL2_TROCA_ORIGEM, listener));	
			this.mnu2.add(FormControlUtil.newMenuItem(AppDefs.MNU_EL2_TROCA_NOME_QUADRO, AppDefs.ACTION_EL2_TROCA_NOME_QUADRO, listener));	

			mnubar.add(this.mnu2);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//TOOLBAR_MENU: ELETRICA
	//
	private int createToolbarMenu_ELETRICA1(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			this.mnuToolbar1 = new JPanel();		

			FlowLayout layout1 = new FlowLayout();
			this.mnuToolbar1.setLayout(layout1);
			
			CategoriaPontoEletricoVO[] arrCategoria = this.currLsCategoria.toArray(new CategoriaPontoEletricoVO[this.currLsCategoria.size()]);
		    this.currCategoria = arrCategoria[0];

		    ArrayList<PontoEletricoVO> lsPontoEletrico = currCategoria.getLsPontoEletrico();

			PontoEletricoVO[] arrPontoEletrico = lsPontoEletrico.toArray(new PontoEletricoVO[lsPontoEletrico.size()]);
		    this.currPontoEletrico = arrPontoEletrico[0];
		    
			//FORM_CONTROLS
			//
			Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
			
			//this.lblPontoEletrico = FormControlUtil.newLabel("Ponto: ", 50, 16, true);
			//this.lblPontoEletrico.setFont(f);
			//this.mnuToolbar1.add(lblPontoEletrico);
			
			this.cbxCategoria = FormControlUtil.newComboBoxEx1(this.mnuToolbar1, arrCategoria, 100, 16, true, AppDefs.RSCODE_CATEGORIA_PONTOS_ELETRICOS_SELECTED, this);
			this.cbxCategoria.setSelectedIndex(0);
			this.cbxCategoria.setFont(f);
			this.mnuToolbar1.add(cbxCategoria);
			
			this.cbxPontoEletrico = FormControlUtil.newComboBoxEx1(this.mnuToolbar1, arrPontoEletrico, 100, 16, true, AppDefs.RSCODE_PONTOS_ELETRICOS_SELECTED, this);
			this.cbxPontoEletrico.setSelectedIndex(0);
			this.cbxPontoEletrico.setFont(f);
			this.mnuToolbar1.add(cbxPontoEletrico);

			JButton btnInserePontoEletrico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_INSERE_PONTO_ELETRICO, AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_INSERE_PONTO_ELETRICO);
			this.mnuToolbar1.add(btnInserePontoEletrico);

			JButton btnInserePontoEletricoRef = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_INSERE_PONTO_ELETRICO_REF, AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO_REF, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_INSERE_PONTO_ELETRICO_REF);
			this.mnuToolbar1.add(btnInserePontoEletricoRef);

			JButton btnInserePontoEletrico2Pt = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_INSERE_PONTO_ELETRICO_2PT, AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO_2PT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_INSERE_PONTO_ELETRICO_2PT);
			this.mnuToolbar1.add(btnInserePontoEletrico2Pt);

			JButton btnInserePontoEletricoMult = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_INSERE_PONTO_ELETRICO_MULT, AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO_MULT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_INSERE_PONTO_ELETRICO_MULT);
			this.mnuToolbar1.add(btnInserePontoEletricoMult);

			JButton btnInserePontoEletricoMatriz = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_INSERE_PONTO_ELETRICO_MATRIZ, AppDefs.ACTION_EL1_INSERE_PONTO_ELETRICO_MATRIZ, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_INSERE_PONTO_ELETRICO_MATRIZ);
			this.mnuToolbar1.add(btnInserePontoEletricoMatriz);

			JButton btnPropPontoEletrico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_PROPRIEDADE_PONTOELETRICA, AppDefs.ACTION_EL1_PROPRIEDADE_PONTOELETRICA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_PROPRIEDADE_PONTOELETRICA);
			this.mnuToolbar1.add(btnPropPontoEletrico);

			JButton btnMathPropPontoEletrico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_MATCHPROP_PONTOELETRICA, AppDefs.ACTION_EL1_MATCHPROP_PONTOELETRICA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_MATCHPROP_PONTOELETRICA);
			this.mnuToolbar1.add(btnMathPropPontoEletrico);
			
			JButton btnDefinicaoQuadroEletrico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_PARAMELETRICO_ONOFF, AppDefs.ACTION_EL1_PARAMELETRICO_ONOFF, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_PARAMELETRICO_ONOFF);
			this.mnuToolbar1.add(btnDefinicaoQuadroEletrico);
			
			iconmnu.add(this.mnuToolbar1);
			
			result = AppDefs.RSOK;			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private int createToolbarMenu_ELETRICA2(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			this.mnuToolbar2 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar2.setLayout(layout1);
			
			JButton btnInsereEletrodutoTeto = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_TETO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_TETO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_TETO);
			this.mnuToolbar2.add(btnInsereEletrodutoTeto);

			JButton btnInsereEletrodutoParede = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_PAREDE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PAREDE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_PAREDE);
			this.mnuToolbar2.add(btnInsereEletrodutoParede);

			JButton btnInsereEletrodutoPiso = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_PISO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_PISO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_PISO);
			this.mnuToolbar2.add(btnInsereEletrodutoPiso);

			JButton btnInsereEletrodutoAparente = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_APARENTE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_APARENTE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_APARENTE);
			this.mnuToolbar2.add(btnInsereEletrodutoAparente);

			JButton btnInsereEletrodutoMultTeto = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_MULT_TETO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_TETO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_MULT_TETO);
			this.mnuToolbar2.add(btnInsereEletrodutoMultTeto);

			JButton btnInsereEletrodutoMultParede = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_MULT_PAREDE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PAREDE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_MULT_PAREDE);
			this.mnuToolbar2.add(btnInsereEletrodutoMultParede);

			JButton btnInsereEletrodutoMultPiso = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_MULT_PISO, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PISO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_MULT_PISO);
			this.mnuToolbar2.add(btnInsereEletrodutoMultPiso);

			JButton btnInsereEletrodutoMultAparente = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_ELETRODUTO_MULT_APARENTE, AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_APARENTE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_ELETRODUTO_MULT_APARENTE);
			this.mnuToolbar2.add(btnInsereEletrodutoMultAparente);
			
			JButton btnTrocaCircuito = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_TROCA_CIRCUITO, AppDefs.ACTION_EL2_TROCA_CIRCUITO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_TROCA_CIRCUITO);
			this.mnuToolbar2.add(btnTrocaCircuito);

			JButton btnTrocaComando = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_TROCA_COMANDO, AppDefs.ACTION_EL2_TROCA_COMANDO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_TROCA_COMANDO);
			this.mnuToolbar2.add(btnTrocaComando);

			JButton btnTrocaOrigem = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_TROCA_ORIGEM, AppDefs.ACTION_EL2_TROCA_ORIGEM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_TROCA_ORIGEM);
			this.mnuToolbar2.add(btnTrocaOrigem);

			JButton btnTrocaNomeQuadro = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_TROCA_NOME_QUADRO, AppDefs.ACTION_EL2_TROCA_NOME_QUADRO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_TROCA_NOME_QUADRO);
			this.mnuToolbar2.add(btnTrocaNomeQuadro);

			//JButton btnCopiaPontos = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_COPIA_PONTOS, AppDefs.ACTION_EL2_COPIA_PONTOS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_COPIA_PONTOS);
			//this.mnuToolbar2.add(btnCopiaPontos);

			//JButton btnMirrorPontos = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_MIRROR_PONTOS, AppDefs.ACTION_EL2_MIRROR_PONTOS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_MIRROR_PONTOS);
			//this.mnuToolbar2.add(btnMirrorPontos);
			
			iconmnu.add(this.mnuToolbar2);
			
			result = AppDefs.RSOK;			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private int createToolbarMenu_ELETRICA3(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			this.mnuToolbar3 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar3.setLayout(layout1);
			
			//JButton btnFiacaoAutomaticaExportar = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_FIACAO_AUTOMATICA_EXPORTAR, AppDefs.ACTION_EL2_FIACAO_AUTOMATICA_EXPORTAR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_FIACAO_AUTOMATICA_EXPORTAR);
			//this.mnuToolbar3.add(btnFiacaoAutomaticaExportar);

			//JButton btnFiacaoAutomaticaImportar = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_FIACAO_AUTOMATICA_IMPORTAR, AppDefs.ACTION_EL2_FIACAO_AUTOMATICA_IMPORTAR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_FIACAO_AUTOMATICA_IMPORTAR);
			//this.mnuToolbar3.add(btnFiacaoAutomaticaImportar);

			JButton btnProcessaFiacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_PROCESSA_FIACAO, AppDefs.ACTION_EL2_PROCESSA_FIACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_PROCESSA_FIACAO);
			this.mnuToolbar3.add(btnProcessaFiacao);
			
			JButton btnFiacaoEletricaOnOff = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL1_FIACAOELETRICA_ONOFF, AppDefs.ACTION_EL1_FIACAOELETRICA_ONOFF, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL1_FIACAOELETRICA_ONOFF);
			this.mnuToolbar3.add(btnFiacaoEletricaOnOff);						

			JButton btnEletrodutoFiacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_ELETRODUTO_FIACAO, AppDefs.ACTION_EL2_ELETRODUTO_FIACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_ELETRODUTO_FIACAO);
			this.mnuToolbar3.add(btnEletrodutoFiacao);

			JButton btnSetaFiacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_SETA_FIACAO, AppDefs.ACTION_EL2_SETA_FIACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_SETA_FIACAO);
			this.mnuToolbar3.add(btnSetaFiacao);

			JButton btnIndicadorFiacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INDICADOR_TABELA_FIACAO, AppDefs.ACTION_EL2_INDICADOR_TABELA_FIACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INDICADOR_TABELA_FIACAO);
			this.mnuToolbar3.add(btnIndicadorFiacao);

			JButton btnInsereTabelaFiacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_INSERE_TABELA_FIACAO, AppDefs.ACTION_EL2_INSERE_TABELA_FIACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_INSERE_TABELA_FIACAO);
			this.mnuToolbar3.add(btnInsereTabelaFiacao);
			
			JButton btnLevantamentoCargas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_LEVANTAMENTO_CARGAS, AppDefs.ACTION_EL2_LEVANTAMENTO_CARGAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_LEVANTAMENTO_CARGAS);
			this.mnuToolbar3.add(btnLevantamentoCargas);

			//JButton btnGerenciadorCircuitos = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_GERENCIADOR_CIRCUITOS, AppDefs.ACTION_EL2_GERENCIADOR_CIRCUITOS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_GERENCIADOR_CIRCUITOS);
			//this.mnuToolbar3.add(btnGerenciadorCircuitos);

			JButton btnDiagramaUnifilar = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_DIAGRAMA_UNIFILAR, AppDefs.ACTION_EL2_DIAGRAMA_UNIFILAR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_DIAGRAMA_UNIFILAR);
			this.mnuToolbar3.add(btnDiagramaUnifilar);

			//JButton btnAnalisePorQuadro = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_ANALISE_POR_QUADRO, AppDefs.ACTION_EL2_ANALISE_POR_QUADRO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_ANALISE_POR_QUADRO);
			//this.mnuToolbar2.add(btnAnalisePorQuadro);

			//JButton btnAnalisePorCircuito = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_ANALISE_POR_CIRCUITO, AppDefs.ACTION_EL2_ANALISE_POR_CIRCUITO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_ANALISE_POR_CIRCUITO);
			//this.mnuToolbar2.add(btnAnalisePorCircuito);

			JButton btnQuadroCargas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_QUADRO_CARGAS, AppDefs.ACTION_EL2_QUADRO_CARGAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_QUADRO_CARGAS);
			this.mnuToolbar3.add(btnQuadroCargas);

			JButton btnAlteraQuadroCargas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_EL2_ALTERA_QUADRO_CARGAS, AppDefs.ACTION_EL2_ALTERA_QUADRO_CARGAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_EL2_ALTERA_QUADRO_CARGAS);
			this.mnuToolbar3.add(btnAlteraQuadroCargas);

			iconmnu.add(this.mnuToolbar3);
			
			result = AppDefs.RSOK;			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
//Public
	
	public EletricaModule(AppMain app, AppCadMain cad)
	{
		this.gAppMod = this;

		init(app, cad);
	}
	
	/* Methodes */
	
	@Override
	public void init(AppMain app, AppCadMain cad) 
	{
		this.app = app;
		this.cad = cad;
		
		this.start();
	}

	@Override
	public void start() 
	{
		AppCtx ctx = this.app.getCtx();
		
		String fiacaoDataFile220v = ctx.getConfDir() + AppDefs.defFiacaoDataFile220v;
	    this.lsCategoria220v = this.loadDefPontoEletrico(fiacaoDataFile220v);
		
		String fiacaoDataFile380v = ctx.getConfDir() + AppDefs.defFiacaoDataFile380v;
	    this.lsCategoria380v = this.loadDefPontoEletrico(fiacaoDataFile380v);
	    
	    this.currLsCategoria = this.lsCategoria220v; 
	    
	    this.loadAllTables();
	}

	@Override
	public void terminate() 
	{
		/* nothing todo! */
	}

	@Override
	public int createMenu(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			//this.createMenu_ELETRICA1(mnubar, listener); 

			this.createMenu_ELETRICA2(mnubar, listener); 
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int createToolbarMenu(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			this.createToolbarMenu_ELETRICA1(iconmnu, listener); 

			this.createToolbarMenu_ELETRICA2(iconmnu, listener); 

			this.createToolbarMenu_ELETRICA3(iconmnu, listener); 
			
			result = AppDefs.RSOK;			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isVisible()
	{
		//boolean bVisible = this.mnu1.isVisible();
		boolean bVisible = this.mnu2.isVisible();
		return bVisible;
	}
	
	public void setVisible(boolean bVisible)
	{
		//this.mnu1.setVisible(bVisible);
		this.mnu2.setVisible(bVisible);

		this.mnuToolbar1.setVisible(bVisible);
		this.mnuToolbar2.setVisible(bVisible);
		this.mnuToolbar3.setVisible(bVisible);
	}

	/* Listeners */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		String action = e.getActionCommand();

		if( EletricaModule.rsCodeCategoriaPontosEletricosSelected.equals(action) ) {
			if( (this.cbxCategoria == null) || (this.cbxPontoEletrico == null) ) return;

			int pos = this.cbxCategoria.getSelectedIndex();
			if(pos != -1) {
				this.currCategoria = (CategoriaPontoEletricoVO)this.cbxCategoria.getSelectedItem();

				ArrayList<PontoEletricoVO> lsPontoEletrico = currCategoria.getLsPontoEletrico();
			    PontoEletricoVO[] arrPontoEletrico = lsPontoEletrico.toArray(new PontoEletricoVO[lsPontoEletrico.size()]);
			    this.currPontoEletrico = arrPontoEletrico[0];
			    
			    DefaultComboBoxModel<PontoEletricoVO> model = new DefaultComboBoxModel<PontoEletricoVO>(arrPontoEletrico);
			    this.cbxPontoEletrico.setModel(model);
			    this.cbxPontoEletrico.setSelectedIndex(0);
			}
		}
		else if( EletricaModule.rsCodePontosEletricosSelected.equals(action) ) {
			if(this.cbxPontoEletrico == null) return;

			int pos = this.cbxPontoEletrico.getSelectedIndex();
			if(pos != -1) {
				this.currPontoEletrico = (PontoEletricoVO)this.cbxPontoEletrico.getSelectedItem();
			}
		}
	}

	/* Getters/Setters */

	public static IModule getAppModule() {
		return EletricaModule.gAppMod;
	}

	public static int currFiaSeqNum() {
		int curr = EletricaModule.gTblFiaSeqNum;
		return curr;
	}

	public static int nextFiaSeqNum() {
		int curr = EletricaModule.gTblFiaSeqNum;
		EletricaModule.gTblFiaSeqNum += 1;
		return curr;
	}

	public static int resetFiaSeqNum() {
		EletricaModule.gTblFiaSeqNum = AppDefs.DEF_TBLFIA_SEQNUM_INIT;
		return EletricaModule.gTblFiaSeqNum;
	}

	public CategoriaPontoEletricoVO getCurrCategoria() {
		return currCategoria;
	}

	public void setCurrCategoria(CategoriaPontoEletricoVO currCategoria) {
		this.currCategoria = currCategoria;
	}

	public PontoEletricoVO getCurrPontoEletrico() {
		return currPontoEletrico;
	}

	public void setCurrPontoEletrico(PontoEletricoVO currPontoEletrico) {
		this.currPontoEletrico = currPontoEletrico;
	}

	public int getFiamode() {
		return fiamode;
	}

	public void setFiamode(int fiamode) {
		this.fiamode = fiamode;
	}

}
