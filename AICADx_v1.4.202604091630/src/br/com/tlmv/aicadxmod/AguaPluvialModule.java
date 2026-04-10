/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * AguaPluvialModule.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/04/2025
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

public class AguaPluvialModule implements IModule 
{
//Private Static
	private static IModule gAppMod = null;
		
//Private
	private AppMain app = null;
	private AppCadMain cad = null;

	private JMenu mnu1 = null;
	private JMenu mnu2 = null;
	private JMenu mnu3 = null;
	private JMenu mnu4 = null;
	private JMenu mnu5 = null;
	
	private JPanel mnuToolbar1 = null;
	private JPanel mnuToolbar2 = null;
	private JPanel mnuToolbar3 = null;
	private JPanel mnuToolbar4 = null;
	private JPanel mnuToolbar5 = null;
	
	public int createMenu1(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu1 = new JMenu(AppDefs.MNU_AP1);
						
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP1_INSERE_CI,
				AppDefs.ACTION_AP1_INSERE_CI,
				listener) );

			this.mnu1.add(new JSeparator());

			this.mnu1.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_AP1_LIGACAO_CI,
					AppDefs.ACTION_AP1_LIGACAO_CI,
					listener) );
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP1_VERIF_LIGACAO_CI_ONOFF,
				AppDefs.ACTION_AP1_VERIF_LIGACAO_CI_ONOFF,
				listener) );

			this.mnu1.add(new JSeparator());
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP1_DIMENSIONA_CI,
				AppDefs.ACTION_AP1_DIMENSIONA_CI,
				listener) );
			
			this.mnu1.add(new JSeparator());
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP1_GERAR_PLANILHA_CALCULO_CI,
				AppDefs.ACTION_AP1_GERAR_PLANILHA_CALCULO_CI,
				listener) );

			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP1_GERAR_PLANTA_PERFIS_CI,
				AppDefs.ACTION_AP1_GERAR_PLANTA_PERFIS_CI,
				listener) );

			this.mnu1.add(new JSeparator());
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP1_ANOTACAO_INDIVIDUAL_CI,
				AppDefs.ACTION_AP1_ANOTACAO_INDIVIDUAL_CI,
				listener) );
	
			mnubar.add(this.mnu1);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createMenu2(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu2 = new JMenu(AppDefs.MNU_AP2);
						
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_CD50,
				AppDefs.ACTION_AP2_COLUNA_CD50,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_CD75,
				AppDefs.ACTION_AP2_COLUNA_CD75,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_CD100,
				AppDefs.ACTION_AP2_COLUNA_CD100,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_CD150,
				AppDefs.ACTION_AP2_COLUNA_CD150,
				listener) );

			this.mnu2.add(new JSeparator());
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TD50,
				AppDefs.ACTION_AP2_COLUNA_TD50,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TD75,
				AppDefs.ACTION_AP2_COLUNA_TD75,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TD100,
				AppDefs.ACTION_AP2_COLUNA_TD100,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TD150,
				AppDefs.ACTION_AP2_COLUNA_TD150,
				listener) );

			this.mnu2.add(new JSeparator());
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TTD50,
				AppDefs.ACTION_AP2_COLUNA_TTD50,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TTD75,
				AppDefs.ACTION_AP2_COLUNA_TTD75,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TTD100,
				AppDefs.ACTION_AP2_COLUNA_TTD100,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP2_COLUNA_TTD150,
				AppDefs.ACTION_AP2_COLUNA_TTD150,
				listener) );

			mnubar.add(this.mnu2);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createMenu3(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu3 = new JMenu(AppDefs.MNU_AP3);
						
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50,
				listener) );
				
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300,
				listener) );

			this.mnu3.add(new JSeparator());
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300,
				AppDefs.MNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300,
				listener) );

			mnubar.add(this.mnu3);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createMenu4(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu4 = new JMenu(AppDefs.MNU_AP4);
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_BUJAO40,
				AppDefs.ACTION_AP4_BUJAO40,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_BUJAO50,
				AppDefs.ACTION_AP4_BUJAO50,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_BUJAO75,
				AppDefs.ACTION_AP4_BUJAO75,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_BUJAO100,
				AppDefs.ACTION_AP4_BUJAO100,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_BUJAO150,
				AppDefs.ACTION_AP4_BUJAO150,
				listener) );

			this.mnu4.add(new JSeparator());
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_CAIXA_INSPECAO,
				AppDefs.ACTION_AP4_CAIXA_INSPECAO,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_CAIXA_PASSAGEM_60X60,
				AppDefs.ACTION_AP4_CAIXA_PASSAGEM_60X60,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_RALO_CONICO,
				AppDefs.ACTION_AP4_RALO_CONICO,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_RALO_HEMISFERICO,
				AppDefs.ACTION_AP4_RALO_HEMISFERICO,
				listener) );

			this.mnu4.add(new JSeparator());
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_TUBO_OPERCULADO_40,
				AppDefs.ACTION_AP4_TUBO_OPERCULADO_40,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_TUBO_OPERCULADO_50,
				AppDefs.ACTION_AP4_TUBO_OPERCULADO_50,
				listener) );
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_TUBO_OPERCULADO_75,
				AppDefs.ACTION_AP4_TUBO_OPERCULADO_75,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_TUBO_OPERCULADO_100,
				AppDefs.ACTION_AP4_TUBO_OPERCULADO_100,
				listener) );
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_TUBO_OPERCULADO_150,
				AppDefs.ACTION_AP4_TUBO_OPERCULADO_150,
				listener) );

			this.mnu4.add(new JSeparator());
						
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP4_FINAL_TUBULACAO,
				AppDefs.ACTION_AP4_FINAL_TUBULACAO,
				listener) );

			mnubar.add(this.mnu4);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createMenu5(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu5 = new JMenu(AppDefs.MNU_AP5);
						
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP5_SETA_INDICADOR_DESCE,
				AppDefs.ACTION_AP5_SETA_INDICADOR_DESCE,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP5_SETA_INDICADOR_PASSA,
				AppDefs.ACTION_AP5_SETA_INDICADOR_PASSA,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP5_SETA_INDICADOR_SOBE,
				AppDefs.ACTION_AP5_SETA_INDICADOR_SOBE,
				listener) );

			this.mnu5.add(new JSeparator());
						
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP5_SETA_SIMPLES_DESCE,
				AppDefs.ACTION_AP5_SETA_SIMPLES_DESCE,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_AP5_SETA_SIMPLES_PASSA,
				AppDefs.ACTION_AP5_SETA_SIMPLES_PASSA,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_AP5_SETA_SIMPLES_SOBE,
			AppDefs.ACTION_AP5_SETA_SIMPLES_SOBE,
			listener) );

			mnubar.add(this.mnu5);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int createToolbarMenu1(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
	    	MainFrame frm = MainFrame.getMainFrame();

	    	this.mnuToolbar1 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar1.setLayout(layout1);
			
			JButton btnInsereCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_INSERE_CI, AppDefs.ACTION_AP1_INSERE_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_INSERE_CI);
			this.mnuToolbar1.add(btnInsereCI);
			
			JButton btnLigacaoCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_LIGACAO_CI, AppDefs.ACTION_AP1_LIGACAO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_LIGACAO_CI);
			this.mnuToolbar1.add(btnLigacaoCI);
			
			JButton btnVerifLigacaoCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_VERIF_LIGACAO_CI_ONOFF, AppDefs.ACTION_AP1_VERIF_LIGACAO_CI_ONOFF, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_VERIF_LIGACAO_CI_ONOFF);
			this.mnuToolbar1.add(btnVerifLigacaoCI);
			
			JButton btnDimensionaCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_DIMENSIONA_CI, AppDefs.ACTION_AP1_DIMENSIONA_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_DIMENSIONA_CI);
			this.mnuToolbar1.add(btnDimensionaCI);
			
			JButton btnGeralPlanilhaCalculoCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_GERAR_PLANILHA_CALCULO_CI, AppDefs.ACTION_AP1_GERAR_PLANILHA_CALCULO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_GERAR_PLANILHA_CALCULO_CI);
			this.mnuToolbar1.add(btnGeralPlanilhaCalculoCI);
			
			JButton btnGeralPlantaPerfisCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_GERAR_PLANTA_PERFIS_CI, AppDefs.ACTION_AP1_GERAR_PLANTA_PERFIS_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_GERAR_PLANTA_PERFIS_CI);
			this.mnuToolbar1.add(btnGeralPlantaPerfisCI);
			
			JButton btnAnotacaoIndividualCI = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP1_ANOTACAO_INDIVIDUAL_CI, AppDefs.ACTION_AP1_ANOTACAO_INDIVIDUAL_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP1_ANOTACAO_INDIVIDUAL_CI);
			this.mnuToolbar1.add(btnAnotacaoIndividualCI);

			iconmnu.add(this.mnuToolbar1);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int createToolbarMenu2(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
	    	MainFrame frm = MainFrame.getMainFrame();

			this.mnuToolbar2 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar2.setLayout(layout1);
			
			JButton btnColunaCD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_CD50, AppDefs.ACTION_AP2_COLUNA_CD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_CD50);
			this.mnuToolbar2.add(btnColunaCD50);
			
			JButton btnColunaCD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_CD75, AppDefs.ACTION_AP2_COLUNA_CD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_CD75);
			this.mnuToolbar2.add(btnColunaCD75);
			
			JButton btnColunaCD100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_CD100, AppDefs.ACTION_AP2_COLUNA_CD100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_CD100);
			this.mnuToolbar2.add(btnColunaCD100);
			
			JButton btnColunaCD150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_CD150, AppDefs.ACTION_AP2_COLUNA_CD150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_CD150);
			this.mnuToolbar2.add(btnColunaCD150);
			
			JButton btnColunaTD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD50, AppDefs.ACTION_AP2_COLUNA_TD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TD50);
			this.mnuToolbar2.add(btnColunaTD50);
			
			JButton btnColunaTD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD75, AppDefs.ACTION_AP2_COLUNA_TD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TD75);
			this.mnuToolbar2.add(btnColunaTD75);
			
			JButton btnColunaTD100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD100, AppDefs.ACTION_AP2_COLUNA_TD100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TD100);
			this.mnuToolbar2.add(btnColunaTD100);
			
			JButton btnColunaTD150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD150, AppDefs.ACTION_AP2_COLUNA_TD150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TD150);
			this.mnuToolbar2.add(btnColunaTD150);
			
			JButton btnColunaTTD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD50, AppDefs.ACTION_AP2_COLUNA_TD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TTD50);
			this.mnuToolbar2.add(btnColunaTTD50);
			
			JButton btnColunaTTD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD75, AppDefs.ACTION_AP2_COLUNA_TD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TTD75);
			this.mnuToolbar2.add(btnColunaTTD75);
			
			JButton btnColunaTTD100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD100, AppDefs.ACTION_AP2_COLUNA_TD100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TTD100);
			this.mnuToolbar2.add(btnColunaTTD100);
			
			JButton btnColunaTTD150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP2_COLUNA_TD150, AppDefs.ACTION_AP2_COLUNA_TD150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP2_COLUNA_TTD150);
			this.mnuToolbar2.add(btnColunaTTD150);

			iconmnu.add(this.mnuToolbar2);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int createToolbarMenu3(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
	    	MainFrame frm = MainFrame.getMainFrame();

			this.mnuToolbar3 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar3.setLayout(layout1);
			
			JButton btnTbAguasPluviais50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_50);
			this.mnuToolbar3.add(btnTbAguasPluviais50);

			JButton btnTbAguasPluviais75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_75);
			this.mnuToolbar3.add(btnTbAguasPluviais75);

			JButton btnTbAguasPluviais100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_100);
			this.mnuToolbar3.add(btnTbAguasPluviais100);

			JButton btnTbAguasPluviais150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_150_200);
			this.mnuToolbar3.add(btnTbAguasPluviais150);

			JButton btnTbAguasPluviais250 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_250_300);
			this.mnuToolbar3.add(btnTbAguasPluviais250);
			
			JButton btnTbAguasPluviaisReuso50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_50);
			this.mnuToolbar3.add(btnTbAguasPluviaisReuso50);

			JButton btnTbAguasPluviaisReuso75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_75);
			this.mnuToolbar3.add(btnTbAguasPluviaisReuso75);

			JButton btnTbAguasPluviaisReuso100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_100);
			this.mnuToolbar3.add(btnTbAguasPluviaisReuso100);

			JButton btnTbAguasPluviaisReuso150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_150_200);
			this.mnuToolbar3.add(btnTbAguasPluviaisReuso150);

			JButton btnTbAguasPluviaisReuso250 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300, AppDefs.ACTION_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP3_TUBULACAO_AGUAS_PLUVIAIS_DE_REUSO_250_300);
			this.mnuToolbar3.add(btnTbAguasPluviaisReuso250);
			
			iconmnu.add(this.mnuToolbar3);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int createToolbarMenu4(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
	    	MainFrame frm = MainFrame.getMainFrame();

			this.mnuToolbar4 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar4.setLayout(layout1);
			
			JButton btnBujao40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_BUJAO40, AppDefs.ACTION_AP4_BUJAO40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_BUJAO40);
			this.mnuToolbar4.add(btnBujao40);
			
			JButton btnBujao50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_BUJAO50, AppDefs.ACTION_AP4_BUJAO50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_BUJAO50);
			this.mnuToolbar4.add(btnBujao50);
			
			JButton btnBujao75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_BUJAO75, AppDefs.ACTION_AP4_BUJAO75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_BUJAO75);
			this.mnuToolbar4.add(btnBujao75);
			
			JButton btnBujao100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_BUJAO100, AppDefs.ACTION_AP4_BUJAO100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_BUJAO100);
			this.mnuToolbar4.add(btnBujao100);
			
			JButton btnBujao150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_BUJAO150, AppDefs.ACTION_AP4_BUJAO150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_BUJAO150);
			this.mnuToolbar4.add(btnBujao150);
			
			JButton btnCaixaInspecao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_CAIXA_INSPECAO, AppDefs.ACTION_AP4_CAIXA_INSPECAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_CAIXA_INSPECAO);
			this.mnuToolbar4.add(btnCaixaInspecao);
			
			JButton btnCaixaPassagem60x60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_CAIXA_PASSAGEM_60X60, AppDefs.ACTION_AP4_CAIXA_PASSAGEM_60X60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_CAIXA_PASSAGEM_60X60);
			this.mnuToolbar4.add(btnCaixaPassagem60x60);
			
			JButton btnRaloConico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_RALO_CONICO, AppDefs.ACTION_AP4_RALO_CONICO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_RALO_CONICO);
			this.mnuToolbar4.add(btnRaloConico);
			
			JButton btnRaloHemisferico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_RALO_HEMISFERICO, AppDefs.ACTION_AP4_RALO_HEMISFERICO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_RALO_HEMISFERICO);
			this.mnuToolbar4.add(btnRaloHemisferico);
			
			JButton btnTuboOperculado40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_TUBO_OPERCULADO_40, AppDefs.ACTION_AP4_TUBO_OPERCULADO_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_40);
			this.mnuToolbar4.add(btnTuboOperculado40);
			
			JButton btnTuboOperculado50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_TUBO_OPERCULADO_50, AppDefs.ACTION_AP4_TUBO_OPERCULADO_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_50);
			this.mnuToolbar4.add(btnTuboOperculado50);
			
			JButton btnTuboOperculado75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_TUBO_OPERCULADO_75, AppDefs.ACTION_AP4_TUBO_OPERCULADO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_75);
			this.mnuToolbar4.add(btnTuboOperculado75);
			
			JButton btnTuboOperculado100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_TUBO_OPERCULADO_100, AppDefs.ACTION_AP4_TUBO_OPERCULADO_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_100);
			this.mnuToolbar4.add(btnTuboOperculado100);
			
			JButton btnTuboOperculado150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP4_TUBO_OPERCULADO_150, AppDefs.ACTION_AP4_TUBO_OPERCULADO_150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP4_TUBO_OPERCULADO_150);
			this.mnuToolbar4.add(btnTuboOperculado150);
			
			iconmnu.add(this.mnuToolbar4);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int createToolbarMenu5(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
	    	MainFrame frm = MainFrame.getMainFrame();

	    	this.mnuToolbar5 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar5.setLayout(layout1);
			
			JButton btnIndicadorDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP5_SETA_INDICADOR_DESCE, AppDefs.ACTION_AP5_SETA_INDICADOR_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP5_SETA_INDICADOR_DESCE);
			this.mnuToolbar5.add(btnIndicadorDesce);
			
			JButton btnIndicadorPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP5_SETA_INDICADOR_PASSA, AppDefs.ACTION_AP5_SETA_INDICADOR_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP5_SETA_INDICADOR_PASSA);
			this.mnuToolbar5.add(btnIndicadorPassa);
			
			JButton btnIndicadorSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP5_SETA_INDICADOR_SOBE, AppDefs.ACTION_AP5_SETA_INDICADOR_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP5_SETA_INDICADOR_SOBE);
			this.mnuToolbar5.add(btnIndicadorSobe);
			
			JButton btnSetaDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP5_SETA_SIMPLES_DESCE, AppDefs.ACTION_AP5_SETA_SIMPLES_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP5_SETA_SIMPLES_DESCE);
			this.mnuToolbar5.add(btnSetaDesce);
			
			JButton btnSetaPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP5_SETA_SIMPLES_PASSA, AppDefs.ACTION_AP5_SETA_SIMPLES_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP5_SETA_SIMPLES_PASSA);
			this.mnuToolbar5.add(btnSetaPassa);
			
			JButton btnSetaSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_AP5_SETA_SIMPLES_SOBE, AppDefs.ACTION_AP5_SETA_SIMPLES_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_AP5_SETA_SIMPLES_SOBE);
			this.mnuToolbar5.add(btnSetaSobe);
			
			iconmnu.add(this.mnuToolbar5);
			
			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
//Public
	
	public AguaPluvialModule(AppMain app, AppCadMain cad)
	{
		AguaPluvialModule.gAppMod = this;
		
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
			this.createMenu1(mnubar, listener);
			this.createMenu2(mnubar, listener);
			this.createMenu3(mnubar, listener);
			this.createMenu4(mnubar, listener);
			this.createMenu5(mnubar, listener);

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
			this.createToolbarMenu1(iconmnu, listener); 
			this.createToolbarMenu2(iconmnu, listener); 
			this.createToolbarMenu3(iconmnu, listener); 
			this.createToolbarMenu4(iconmnu, listener); 
			this.createToolbarMenu5(iconmnu, listener); 
			
			result = AppDefs.RSOK;
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
		boolean bVisible = this.mnu1.isVisible();
		return bVisible;
	}
	
	public void setVisible(boolean bVisible)
	{
		this.mnu1.setVisible(bVisible);
		this.mnu2.setVisible(bVisible);
		this.mnu3.setVisible(bVisible);
		this.mnu4.setVisible(bVisible);
		this.mnu5.setVisible(bVisible);
		//
		this.mnuToolbar1.setVisible(bVisible);
		this.mnuToolbar2.setVisible(bVisible);
		this.mnuToolbar3.setVisible(bVisible);
		this.mnuToolbar4.setVisible(bVisible);
		this.mnuToolbar5.setVisible(bVisible);
	}
	
	/* Getters/Setters */

	public static IModule getAppModule() {
		return AguaPluvialModule.gAppMod;
	}
	
}
