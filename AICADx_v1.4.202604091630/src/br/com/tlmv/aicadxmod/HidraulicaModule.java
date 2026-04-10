/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * HidraulicaModule.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/05/2025
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

public class HidraulicaModule implements IModule 
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
	
//Private
	
	//POPUP_MENU
	//
	public int createMenu1(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu1 = new JMenu(AppDefs.MNU_HID1);
						
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID1_DEFINE_TRECHO,
				AppDefs.ACTION_HID1_DEFINE_TRECHO,
				listener) );
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID1_DEFINE_PERDA_EQUIPAMENTO,
				AppDefs.ACTION_HID1_DEFINE_PERDA_EQUIPAMENTO,
				listener) );

			this.mnu1.add(new JSeparator());

			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID1_CALCULA_PERDA_CARGA,
				AppDefs.ACTION_HID1_CALCULA_PERDA_CARGA,
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

			this.mnu2 = new JMenu(AppDefs.MNU_HID2);			

			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_CD50,
				AppDefs.ACTION_HID2_COLUNA_CD50,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_CD60,
				AppDefs.ACTION_HID2_COLUNA_CD60,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_CD75,
				AppDefs.ACTION_HID2_COLUNA_CD75,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_CD85,
				AppDefs.ACTION_HID2_COLUNA_CD85,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_CD110,
				AppDefs.ACTION_HID2_COLUNA_CD110,
				listener) );

			this.mnu2.add(new JSeparator());
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TD50,
				AppDefs.ACTION_HID2_COLUNA_TD50,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TD60,
				AppDefs.ACTION_HID2_COLUNA_TD60,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TD75,
				AppDefs.ACTION_HID2_COLUNA_TD75,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TD85,
				AppDefs.ACTION_HID2_COLUNA_TD85,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TD110,
				AppDefs.ACTION_HID2_COLUNA_TD110,
				listener) );
			
			this.mnu2.add(new JSeparator());
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TTD50,
				AppDefs.ACTION_HID2_COLUNA_TTD50,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TTD60,
				AppDefs.ACTION_HID2_COLUNA_TTD60,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TTD75,
				AppDefs.ACTION_HID2_COLUNA_TTD75,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TTD85,
				AppDefs.ACTION_HID2_COLUNA_TTD85,
				listener) );
			
			this.mnu2.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID2_COLUNA_TTD110,
				AppDefs.ACTION_HID2_COLUNA_TTD110,
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
			this.mnu3 = new JMenu(AppDefs.MNU_HID3);
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_20,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_25,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_25,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_32,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_32,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_40,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_40,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_50,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_50,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_20,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_60,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_60,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_75,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_75,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_85,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_85,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_110,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_110,
				listener) );

			this.mnu3.add(new JSeparator());
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_22,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_22,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_28,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_28,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_35,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_35,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_42,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_42,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_54,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_54,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_60,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_60,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_73,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_73,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_89,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_89,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_114,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_114,
				listener) );

			this.mnu3.add(new JSeparator());
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_PEX_20,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_PEX_25,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_25,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_FRIA_PEX_32,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_32,
				listener) );
			
			this.mnu3.add(new JSeparator());
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_20,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_25,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_32,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_32,
				listener) );
			
			this.mnu3.add(new JSeparator());
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_20,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_25,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_25,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_32,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_32,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_40,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_40,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_50,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_50,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_60,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_60,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_75,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_75,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_85,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_85,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_REUSO_110,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_110,
				listener) );
			
			this.mnu3.add(new JSeparator());
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_20,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_20,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_25,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_25,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_32,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_32,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_40,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_40,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_50,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_50,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_60,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_60,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_75,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_75,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_85,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_85,
				listener) );
			
			this.mnu3.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID3_TUBULACAO_AGUA_TRATADA_110,
				AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_110,
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
			this.mnu4 = new JMenu(AppDefs.MNU_HID4);
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID4_BUCHA_REDUCAO,
				AppDefs.ACTION_HID4_BUCHA_REDUCAO,
				listener) );
			
			this.mnu4.add(new JSeparator());
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID4_REGISTRO_AGUA_FRIA,
				AppDefs.ACTION_HID4_REGISTRO_AGUA_FRIA,
				listener) );
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID4_REGISTRO_AGUA_QUENTE,
				AppDefs.ACTION_HID4_REGISTRO_AGUA_QUENTE,
				listener) );
			
			this.mnu4.add(new JSeparator());
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID4_REGISTRO_3_4_30,
				AppDefs.ACTION_HID4_REGISTRO_3_4_30,
				listener) );
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID4_TORNEIRA_LAVAGEM,
				AppDefs.ACTION_HID4_TORNEIRA_LAVAGEM,
				listener) );
			
			this.mnu4.add(new JSeparator());
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID4_FINAL_TUBULACAO,
				AppDefs.ACTION_HID4_FINAL_TUBULACAO,
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
			this.mnu5 = new JMenu(AppDefs.MNU_HID5);
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID5_SETA_INDICADOR_DESCE,
				AppDefs.ACTION_HID5_SETA_INDICADOR_DESCE,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID5_SETA_INDICADOR_PASSA,
				AppDefs.ACTION_HID5_SETA_INDICADOR_PASSA,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID5_SETA_INDICADOR_SOBE,
				AppDefs.ACTION_HID5_SETA_INDICADOR_SOBE,
				listener) );

			this.mnu5.add(new JSeparator());
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID5_SETA_SIMPLES_DESCE,
				AppDefs.ACTION_HID5_SETA_SIMPLES_DESCE,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID5_SETA_SIMPLES_PASSA,
				AppDefs.ACTION_HID5_SETA_SIMPLES_PASSA,
				listener) );
			
			this.mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_HID5_SETA_SIMPLES_SOBE,
				AppDefs.ACTION_HID5_SETA_SIMPLES_SOBE,
				listener) );
			
			mnubar.add(this.mnu5);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//TOOLBAR_MENU
	//
	public int createToolbarMenu1(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			this.mnuToolbar1 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar1.setLayout(layout1);
			
			JButton btnDefineTrecho = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID1_DEFINE_TRECHO, AppDefs.ACTION_HID1_DEFINE_TRECHO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID1_DEFINE_TRECHO);
			this.mnuToolbar1.add(btnDefineTrecho);
			
			JButton btnDefinePerdaEquipamento = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID1_DEFINE_PERDA_EQUIPAMENTO, AppDefs.ACTION_HID1_DEFINE_PERDA_EQUIPAMENTO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID1_DEFINE_PERDA_EQUIPAMENTO);
			this.mnuToolbar1.add(btnDefinePerdaEquipamento);
			
			JButton btnCalculoPerdaCarga = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID1_CALCULA_PERDA_CARGA, AppDefs.ACTION_HID1_CALCULA_PERDA_CARGA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID1_CALCULA_PERDA_CARGA);
			this.mnuToolbar1.add(btnCalculoPerdaCarga);
			
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

			JButton btnColunaCD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_CD50, AppDefs.ACTION_HID2_COLUNA_CD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_CD50);
			this.mnuToolbar2.add(btnColunaCD50);

			JButton btnColunaCD60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_CD60, AppDefs.ACTION_HID2_COLUNA_CD60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_CD60);
			this.mnuToolbar2.add(btnColunaCD60);

			JButton btnColunaCD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_CD75, AppDefs.ACTION_HID2_COLUNA_CD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_CD75);
			this.mnuToolbar2.add(btnColunaCD75);

			JButton btnColunaCD85 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_CD85, AppDefs.ACTION_HID2_COLUNA_CD85, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_CD85);
			this.mnuToolbar2.add(btnColunaCD85);

			JButton btnColunaCD110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_CD110, AppDefs.ACTION_HID2_COLUNA_CD110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_CD85);
			this.mnuToolbar2.add(btnColunaCD110);

			JButton btnColunaTD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TD50, AppDefs.ACTION_HID2_COLUNA_TD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TD50);
			this.mnuToolbar2.add(btnColunaTD50);

			JButton btnColunaTD60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TD60, AppDefs.ACTION_HID2_COLUNA_TD60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TD60);
			this.mnuToolbar2.add(btnColunaTD60);

			JButton btnColunaTD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TD75, AppDefs.ACTION_HID2_COLUNA_TD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TD75);
			this.mnuToolbar2.add(btnColunaTD75);

			JButton btnColunaTD85 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TD85, AppDefs.ACTION_HID2_COLUNA_TD85, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TD85);
			this.mnuToolbar2.add(btnColunaTD85);

			JButton btnColunaTD110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TD110, AppDefs.ACTION_HID2_COLUNA_TD110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TD110);
			this.mnuToolbar2.add(btnColunaTD110);

			JButton btnColunaTTD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TTD50, AppDefs.ACTION_HID2_COLUNA_TTD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TTD50);
			this.mnuToolbar2.add(btnColunaTTD50);

			JButton btnColunaTTD60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TTD60, AppDefs.ACTION_HID2_COLUNA_TTD60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TTD60);
			this.mnuToolbar2.add(btnColunaTTD60);

			JButton btnColunaTTD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TTD75, AppDefs.ACTION_HID2_COLUNA_TTD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TTD75);
			this.mnuToolbar2.add(btnColunaTTD75);

			JButton btnColunaTTD85 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TTD85, AppDefs.ACTION_HID2_COLUNA_TTD85, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TTD85);
			this.mnuToolbar2.add(btnColunaTTD85);

			JButton btnColunaTTD110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID2_COLUNA_TTD110, AppDefs.ACTION_HID2_COLUNA_TTD110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID2_COLUNA_TTD110);
			this.mnuToolbar2.add(btnColunaTTD110);

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

			JButton btnAguaFria20 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_20, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_20, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_20);
			this.mnuToolbar3.add(btnAguaFria20);

			JButton btnAguaFria25 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_25, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_25, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_25);
			this.mnuToolbar3.add(btnAguaFria25);

			JButton btnAguaFria32 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_32, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_32, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_32);
			this.mnuToolbar3.add(btnAguaFria32);

			JButton btnAguaFria40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_40, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_40);
			this.mnuToolbar3.add(btnAguaFria40);

			JButton btnAguaFria50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_50, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_50);
			this.mnuToolbar3.add(btnAguaFria50);

			JButton btnAguaFria60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_60, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_60);
			this.mnuToolbar3.add(btnAguaFria60);

			JButton btnAguaFria75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_75, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_75);
			this.mnuToolbar3.add(btnAguaFria75);

			JButton btnAguaFria85 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_85, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_85, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_85);
			this.mnuToolbar3.add(btnAguaFria85);

			JButton btnAguaFria110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_110, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_110);
			this.mnuToolbar3.add(btnAguaFria110);

			JButton btnAguaQuente22 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_22, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_22, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_22);
			this.mnuToolbar3.add(btnAguaQuente22);

			JButton btnAguaQuente28 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_28, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_28, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_28);
			this.mnuToolbar3.add(btnAguaQuente28);

			JButton btnAguaQuente35 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_35, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_35, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_35);
			this.mnuToolbar3.add(btnAguaQuente35);

			JButton btnAguaQuente42 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_42, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_42, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_42);
			this.mnuToolbar3.add(btnAguaQuente42);

			JButton btnAguaQuente54 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_54, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_54, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_54);
			this.mnuToolbar3.add(btnAguaQuente54);

			JButton btnAguaQuente60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_60, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_60);
			this.mnuToolbar3.add(btnAguaQuente60);

			JButton btnAguaQuente73 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_73, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_73, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_73);
			this.mnuToolbar3.add(btnAguaQuente73);

			JButton btnAguaQuente89 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_89, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_89, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_89);
			this.mnuToolbar3.add(btnAguaQuente89);

			JButton btnAguaQuente114 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_114, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_114, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_114);
			this.mnuToolbar3.add(btnAguaQuente114);

			JButton btnAguaFriaPex20 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_20, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_20, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_20);
			this.mnuToolbar3.add(btnAguaFriaPex20);

			JButton btnAguaFriaPex25 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_25, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_25, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_25);
			this.mnuToolbar3.add(btnAguaFriaPex25);

			JButton btnAguaFriaPex32 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_32, AppDefs.ACTION_HID3_TUBULACAO_AGUA_FRIA_PEX_32, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_FRIA_PEX_32);
			this.mnuToolbar3.add(btnAguaFriaPex32);

			JButton btnAguaQuentePex20 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_20, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_20, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_20);
			this.mnuToolbar3.add(btnAguaQuentePex20);

			JButton btnAguaQuentePex25 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_25, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_25, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_25);
			this.mnuToolbar3.add(btnAguaQuentePex25);

			JButton btnAguaQuentePex32 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_32, AppDefs.ACTION_HID3_TUBULACAO_AGUA_QUENTE_PEX_32, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_QUENTE_PEX_32);
			this.mnuToolbar3.add(btnAguaQuentePex32);

			JButton btnAguaReuso20 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_20, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_20, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_20);
			this.mnuToolbar3.add(btnAguaReuso20);

			JButton btnAguaReuso25 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_25, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_25, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_25);
			this.mnuToolbar3.add(btnAguaReuso25);

			JButton btnAguaReuso32 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_32, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_32, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_32);
			this.mnuToolbar3.add(btnAguaReuso32);

			JButton btnAguaReuso40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_40, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_40);
			this.mnuToolbar3.add(btnAguaReuso40);

			JButton btnAguaReuso50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_50, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_50);
			this.mnuToolbar3.add(btnAguaReuso50);

			JButton btnAguaReuso60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_60, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_60);
			this.mnuToolbar3.add(btnAguaReuso60);

			JButton btnAguaReuso75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_75, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_75);
			this.mnuToolbar3.add(btnAguaReuso75);

			JButton btnAguaReuso85 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_85, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_85, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_85);
			this.mnuToolbar3.add(btnAguaReuso85);

			JButton btnAguaReuso110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_REUSO_110, AppDefs.ACTION_HID3_TUBULACAO_AGUA_REUSO_110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_REUSO_110);
			this.mnuToolbar3.add(btnAguaReuso110);

			JButton btnAguaTratada20 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_20, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_20, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_20);
			this.mnuToolbar3.add(btnAguaTratada20);

			JButton btnAguaTratada25 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_25, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_25, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_25);
			this.mnuToolbar3.add(btnAguaTratada25);

			JButton btnAguaTratada32 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_32, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_32, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_32);
			this.mnuToolbar3.add(btnAguaTratada32);

			JButton btnAguaTratada40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_40, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_40);
			this.mnuToolbar3.add(btnAguaTratada40);

			JButton btnAguaTratada50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_50, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_50);
			this.mnuToolbar3.add(btnAguaTratada50);

			JButton btnAguaTratada60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_60, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_60);
			this.mnuToolbar3.add(btnAguaTratada60);

			JButton btnAguaTratada75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_75, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_75);
			this.mnuToolbar3.add(btnAguaTratada75);

			JButton btnAguaTratada85 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_85, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_85, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_85);
			this.mnuToolbar3.add(btnAguaTratada85);

			JButton btnAguaTratada110 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_110, AppDefs.ACTION_HID3_TUBULACAO_AGUA_TRATADA_110, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID3_TUBULACAO_AGUA_TRATADA_110);
			this.mnuToolbar3.add(btnAguaTratada110);

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

			JButton btnBuchaReducao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID4_BUCHA_REDUCAO, AppDefs.ACTION_HID4_BUCHA_REDUCAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID4_BUCHA_REDUCAO);
			this.mnuToolbar4.add(btnBuchaReducao);

			JButton btnRegistroAguaFria = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID4_REGISTRO_AGUA_FRIA, AppDefs.ACTION_HID4_REGISTRO_AGUA_FRIA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID4_REGISTRO_AGUA_FRIA);
			this.mnuToolbar4.add(btnRegistroAguaFria);

			JButton btnRegistroAguaQuente = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID4_REGISTRO_AGUA_QUENTE, AppDefs.ACTION_HID4_REGISTRO_AGUA_QUENTE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID4_REGISTRO_AGUA_QUENTE);
			this.mnuToolbar4.add(btnRegistroAguaQuente);

			JButton btnRegistro3 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID4_REGISTRO_3_4_30, AppDefs.ACTION_HID4_REGISTRO_3_4_30, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID4_REGISTRO_3_4_30);
			this.mnuToolbar4.add(btnRegistro3);

			JButton btnTorneiraLavagem = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID4_TORNEIRA_LAVAGEM, AppDefs.ACTION_HID4_TORNEIRA_LAVAGEM, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID4_TORNEIRA_LAVAGEM);
			this.mnuToolbar4.add(btnTorneiraLavagem);

			JButton btnFinalTubulacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID4_FINAL_TUBULACAO, AppDefs.ACTION_HID4_FINAL_TUBULACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID4_FINAL_TUBULACAO);
			this.mnuToolbar4.add(btnFinalTubulacao);

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

			JButton btnIndicadorDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID5_SETA_INDICADOR_DESCE, AppDefs.ACTION_HID5_SETA_INDICADOR_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID5_SETA_INDICADOR_DESCE);
			this.mnuToolbar5.add(btnIndicadorDesce);

			JButton btnIndicadorPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID5_SETA_INDICADOR_PASSA, AppDefs.ACTION_HID5_SETA_INDICADOR_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID5_SETA_INDICADOR_PASSA);
			this.mnuToolbar5.add(btnIndicadorPassa);

			JButton btnIndicadorSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID5_SETA_INDICADOR_SOBE, AppDefs.ACTION_HID5_SETA_INDICADOR_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID5_SETA_INDICADOR_SOBE);
			this.mnuToolbar5.add(btnIndicadorSobe);

			JButton btnSetaDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID5_SETA_SIMPLES_DESCE, AppDefs.ACTION_HID5_SETA_SIMPLES_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID5_SETA_SIMPLES_DESCE);
			this.mnuToolbar5.add(btnSetaDesce);

			JButton btnSetaPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID5_SETA_SIMPLES_PASSA, AppDefs.ACTION_HID5_SETA_SIMPLES_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID5_SETA_SIMPLES_PASSA);
			this.mnuToolbar5.add(btnSetaPassa);

			JButton btnSetaSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_HID5_SETA_SIMPLES_SOBE, AppDefs.ACTION_HID5_SETA_SIMPLES_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_HID5_SETA_SIMPLES_SOBE);
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
	
	public HidraulicaModule(AppMain app, AppCadMain cad)
	{
		HidraulicaModule.gAppMod = this;
		
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
		//POPUP_MENU
		//
		this.mnu1.setVisible(bVisible);
		this.mnu2.setVisible(bVisible);
		this.mnu3.setVisible(bVisible);
		this.mnu4.setVisible(bVisible);
		this.mnu5.setVisible(bVisible);

		//TOOLBAR_MENU
		//
		this.mnuToolbar1.setVisible(bVisible);
		this.mnuToolbar2.setVisible(bVisible);
		this.mnuToolbar3.setVisible(bVisible);
		this.mnuToolbar4.setVisible(bVisible);
		this.mnuToolbar5.setVisible(bVisible);
	}
	
	/* Getters/Setters */

	public static IModule getAppModule() {
		return HidraulicaModule.gAppMod;
	}

}
