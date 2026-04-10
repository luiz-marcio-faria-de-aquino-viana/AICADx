/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DrenagemModule.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/09/2025
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

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class DrenagemModule implements IModule 
{
//Private Static
	private static IModule gAppMod = null;

//Private
	private AppMain app = null;
	private AppCadMain cad = null;

	private JMenu mnu = null;

	private JPanel mnuToolbar = null;		

//Public
	
	public DrenagemModule(AppMain app, AppCadMain cad)
	{
		DrenagemModule.gAppMod = this;
		
		init(app, cad);
	}
	
	/* Methodes */
	
	@Override
	public void init(AppMain app, AppCadMain cad) 
	{
		this.app = app;
		this.cad = cad;
	}

	@Override
	public void start() 
	{
		/* nothing todo! */
	}

	@Override
	public int createMenu(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnuToolbar = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar.setLayout(layout1);
			
			//MENU: REDES_PUBLICAS_DRENAGEM_COMMANDS
			//
			this.mnu = new JMenu(AppDefs.MNU_RPD1);
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_CSV_IMPORT,
				AppDefs.ACTION_RDP1_CSV_IMPORT,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_CSV_EXPORT,
				AppDefs.ACTION_RDP1_CSV_EXPORT,
				listener) );

			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_AREA_CONTRIBUICAO,
				AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_AREA_CONTRIBUICAO_BYSELECTION,
				AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYSELECTION,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_AREA_CONTRIBUICAO_BYINSIDEPOINT,
				AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYINSIDEPOINT,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_AREA_CONTRIBUICAO_BYMULTINSIDEPOINT,
				AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYMULTINSIDEPOINT,
				listener) );
			
			this.mnu.add(new JSeparator());

			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO,
				AppDefs.ACTION_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO,
				listener) );

			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_INSERE_RALO_SIMPLES,
				AppDefs.ACTION_RDP1_INSERE_RALO_SIMPLES,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_INSERE_BOCA_LOBO,
				AppDefs.ACTION_RDP1_INSERE_BOCA_LOBO,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_INSERE_RALO_COM_BOCA_LOBO,
				AppDefs.ACTION_RDP1_INSERE_RALO_COM_BOCA_LOBO,
				listener) );
			
			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_INSERE_CI,
				AppDefs.ACTION_RDP1_INSERE_CI,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_INSERE_MULT_CI,
				AppDefs.ACTION_RDP1_INSERE_MULT_CI,
				listener) );

			this.mnu.add(new JSeparator());

			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_LIGACAO_CI,
				AppDefs.ACTION_RDP1_LIGACAO_CI,
				listener) );

			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_DESLIGAMENTO_CI,
				AppDefs.ACTION_RDP1_DESLIGAMENTO_CI,
				listener) );

			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_PROPRIEDADE_CI,
				AppDefs.ACTION_RDP1_PROPRIEDADE_CI,
				listener) );

			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_VERIF_LIGACAO_CI_ONOFF,
				AppDefs.ACTION_RDP1_VERIF_LIGACAO_CI_ONOFF,
				listener) );

			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_EIXO_DRENAGEM,
				AppDefs.ACTION_RDP1_EIXO_DRENAGEM,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_ALTERAR_EIXO_DRENAGEM,
				AppDefs.ACTION_RDP1_ALTERAR_EIXO_DRENAGEM,
				listener) );
				
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_NUMERAR_ESTACAS,
				AppDefs.ACTION_RDP1_NUMERAR_ESTACAS,
				listener) );
			
			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_GERAR_PLANILHA_CALCULO_CI,
				AppDefs.ACTION_RDP1_GERAR_PLANILHA_CALCULO_CI,
				listener) );

			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_ALTERAR_PLANILHA_CALCULO_CI,
				AppDefs.ACTION_RDP1_ALTERAR_PLANILHA_CALCULO_CI,
				listener) );

//			this.mnu.add(new JSeparator());
//			
//			this.mnu.add(FormControlUtil.newMenuItem(
//					AppDefs.MNU_RDP1_GERAR_PLANTA_PERFIS_CI,
//					AppDefs.ACTION_RDP1_GERAR_PLANTA_PERFIS_CI,
//					listener) );

			this.mnu.add(new JSeparator());
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_ANOTACAO_INDIVIDUAL_CI,
				AppDefs.ACTION_RDP1_ANOTACAO_INDIVIDUAL_CI,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_RDP1_ANOTACAO_MULTIPLA_CI,
				AppDefs.ACTION_RDP1_ANOTACAO_MULTIPLA_CI,
				listener) );
	
			mnubar.add(mnu);

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
			
			this.mnuToolbar = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar.setLayout(layout1);
			
			//MENU: FILE
			//
			JButton btnCsvImport = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_CSV_IMPORT, AppDefs.ACTION_RDP1_CSV_IMPORT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_CSV_IMPORT);
			this.mnuToolbar.add(btnCsvImport);

			JButton btnCsvExport = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_CSV_EXPORT, AppDefs.ACTION_RDP1_CSV_EXPORT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_CSV_EXPORT);
			this.mnuToolbar.add(btnCsvExport);

			JButton btnInsereRaloSimple = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_INSERE_RALO_SIMPLES, AppDefs.ACTION_RDP1_INSERE_RALO_SIMPLES, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_INSERE_RALO_SIMPLES);
			this.mnuToolbar.add(btnInsereRaloSimple);

			JButton btnbtnInsereBocaLobo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_INSERE_BOCA_LOBO, AppDefs.ACTION_RDP1_INSERE_BOCA_LOBO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_INSERE_BOCA_LOBO);
			this.mnuToolbar.add(btnbtnInsereBocaLobo);

			JButton btnbtnInsereRaloComBocaLobo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_INSERE_RALO_COM_BOCA_LOBO, AppDefs.ACTION_RDP1_INSERE_RALO_COM_BOCA_LOBO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_INSERE_RALO_COM_BOCA_LOBO);
			this.mnuToolbar.add(btnbtnInsereRaloComBocaLobo);
			
			JButton btnPlantaAreaContrib = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO, AppDefs.ACTION_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_GERAR_PLANTA_AREA_CONTRIBUICAO);
			this.mnuToolbar.add(btnPlantaAreaContrib);

			JButton btnArea = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_AREA_CONTRIBUICAO, AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_AREA_CONTRIBUICAO);
			this.mnuToolbar.add(btnArea);

			JButton btnAreaBySelc = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_AREA_CONTRIBUICAO_BYSELECTION, AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYSELECTION, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_AREA_CONTRIBUICAO_BYSELECTION);
			this.mnuToolbar.add(btnAreaBySelc);

			JButton btnAreaByInsidePoint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_AREA_CONTRIBUICAO_BYINSIDEPOINT, AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYINSIDEPOINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_AREA_CONTRIBUICAO_BYINSIDEPOINT);
			this.mnuToolbar.add(btnAreaByInsidePoint);

			JButton btnAreaByMultInsidePoint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_AREA_CONTRIBUICAO_BYMULTINSIDEPOINT, AppDefs.ACTION_RDP1_AREA_CONTRIBUICAO_BYMULTINSIDEPOINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_AREA_CONTRIBUICAO_BYMULTINSIDEPOINT);
			this.mnuToolbar.add(btnAreaByMultInsidePoint);

			JButton btnInsere = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_INSERE_CI, AppDefs.ACTION_RDP1_INSERE_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_INSERE_CI);
			this.mnuToolbar.add(btnInsere);

			JButton btnInsereMult = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_INSERE_MULT_CI, AppDefs.ACTION_RDP1_INSERE_MULT_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_INSERE_MULT_CI);
			this.mnuToolbar.add(btnInsereMult);

			JButton btnPropriedade = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_PROPRIEDADE_CI, AppDefs.ACTION_RDP1_PROPRIEDADE_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_PROPRIEDADE_CI);
			this.mnuToolbar.add(btnPropriedade);

			JButton btnLigacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_LIGACAO_CI, AppDefs.ACTION_RDP1_LIGACAO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_LIGACAO_CI);
			this.mnuToolbar.add(btnLigacao);

			JButton btnDesligamento = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_DESLIGAMENTO_CI, AppDefs.ACTION_RDP1_DESLIGAMENTO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_DESLIGAMENTO_CI);
			this.mnuToolbar.add(btnDesligamento);

			JButton btnVerifLigacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_VERIF_LIGACAO_CI_ONOFF, AppDefs.ACTION_RDP1_VERIF_LIGACAO_CI_ONOFF, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_VERIF_LIGACAO_CI_ONOFF);
			this.mnuToolbar.add(btnVerifLigacao);

			JButton btnNumerarEstacas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_NUMERAR_ESTACAS, AppDefs.ACTION_RDP1_NUMERAR_ESTACAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_NUMERAR_ESTACAS);
			this.mnuToolbar.add(btnNumerarEstacas);

			JButton btnEixoDrenagem = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_EIXO_DRENAGEM, AppDefs.ACTION_RDP1_EIXO_DRENAGEM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_EIXO_DRENAGEM);
			this.mnuToolbar.add(btnEixoDrenagem);

			JButton btnAlterarEixoDrenagem = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_ALTERAR_EIXO_DRENAGEM, AppDefs.ACTION_RDP1_ALTERAR_EIXO_DRENAGEM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_ALTERAR_EIXO_DRENAGEM);
			this.mnuToolbar.add(btnAlterarEixoDrenagem);
			
			JButton btnGerarPlanilhaCalculo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_GERAR_PLANILHA_CALCULO_CI, AppDefs.ACTION_RDP1_GERAR_PLANILHA_CALCULO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_GERAR_PLANILHA_CALCULO_CI);
			this.mnuToolbar.add(btnGerarPlanilhaCalculo);

			JButton btnAlterarPlanilhaCalculo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_ALTERAR_PLANILHA_CALCULO_CI, AppDefs.ACTION_RDP1_ALTERAR_PLANILHA_CALCULO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_ALTERAR_PLANILHA_CALCULO_CI);
			this.mnuToolbar.add(btnAlterarPlanilhaCalculo);

			//JButton btnGerarPlantaPerfis = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_GERAR_PLANTA_PERFIS_CI, AppDefs.ACTION_RDP1_GERAR_PLANTA_PERFIS_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_GERAR_PLANTA_PERFIS_CI);
			//this.mnuToolbar.add(btnGerarPlantaPerfis);

			JButton btnAnotacaoIndividual = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_ANOTACAO_INDIVIDUAL_CI, AppDefs.ACTION_RDP1_ANOTACAO_INDIVIDUAL_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_ANOTACAO_INDIVIDUAL_CI);
			this.mnuToolbar.add(btnAnotacaoIndividual);

			JButton btnAnotacaoMultipla = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_RDP1_ANOTACAO_MULTIPLA_CI, AppDefs.ACTION_RDP1_ANOTACAO_MULTIPLA_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_RDP1_ANOTACAO_MULTIPLA_CI);
			this.mnuToolbar.add(btnAnotacaoMultipla);

			iconmnu.add(this.mnuToolbar);

			result = AppDefs.RSERR;
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public void terminate() 
	{
		/* nothing todo! */
	}
	
	public boolean isVisible()
	{
		boolean bVisible = this.mnu.isVisible();
		return bVisible;
	}
	
	public void setVisible(boolean bVisible)
	{
		this.mnu.setVisible(bVisible);
		this.mnuToolbar.setVisible(bVisible);
	}
	
	/* Getters/Setters */

	public static IModule getAppModule() {
		return DrenagemModule.gAppMod;
	}

}
