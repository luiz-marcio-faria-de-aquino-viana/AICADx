/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * EsgotoModule.java
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxmod.eletrica.vo.CategoriaPontoEletricoVO;
import br.com.tlmv.aicadxmod.eletrica.vo.PontoEletricoVO;

public class EsgotoModule implements IModule 
{
//Private Static
	private static IModule gAppMod = null;

//Private
	private AppMain app = null;
	private AppCadMain cad = null;
	
    //FORM_VARS
    //
    private CategoriaPontoEletricoVO currCategoria = null;
    private PontoEletricoVO currPontoEletrico = null;
    
    //FORM_CONTROLS
    //
	//MENUS
	//private JMenu mnu1 = null;
	private JMenu mnu1 = null;
	private JMenu mnu2 = null;
	private JMenu mnu3 = null;
	private JMenu mnu4 = null;
	private JMenu mnu5 = null;
	//TOOLBARS
	private JPanel mnuToolbar1 = null;		
	private JPanel mnuToolbar2 = null;		
	private JPanel mnuToolbar3 = null;		
	private JPanel mnuToolbar4 = null;		
	private JPanel mnuToolbar5 = null;
	//FORM_LABELS
	private JLabel lblPontoEletrico = null;
	//FORM_CONTROLS
	private JComboBox cbxCategoria = null;
	private JComboBox cbxPontoEletrico = null;
	
//Private
	
	//POPUP_MENU
	//
//	public int createMenu1(JMenuBar mnubar, ActionListener listener) 
//	{
//		int result = AppDefs.RSERR;
//		
//		try {
//
//			this.mnu1 = new JMenu(AppDefs.MNU_ES1);
//						
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_INSERE_CI,
//				AppDefs.ACTION_ES1_INSERE_CI,
//				listener) );
//
//			this.mnu1.add(new JSeparator());
//			
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_LIGACAO_CI,
//				AppDefs.ACTION_ES1_LIGACAO_CI,
//				listener) );
//
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_VERIF_LIGACAO_CI_ONOFF,
//				AppDefs.ACTION_ES1_VERIF_LIGACAO_CI_ONOFF,
//				listener) );
//
//			this.mnu1.add(new JSeparator());
//			
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_DIMENSIONA_CI,
//				AppDefs.ACTION_ES1_DIMENSIONA_CI,
//				listener) );
//			
//			this.mnu1.add(new JSeparator());
//			
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_GERAR_PLANILHA_CALCULO_CI,
//				AppDefs.ACTION_ES1_GERAR_PLANILHA_CALCULO_CI,
//				listener) );
//
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_GERAR_PLANTA_PERFIS_CI,
//				AppDefs.ACTION_ES1_GERAR_PLANTA_PERFIS_CI,
//				listener) );
//
//			this.mnu1.add(new JSeparator());
//			
//			this.mnu1.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES1_ANOTACAO_INDIVIDUAL_CI,
//				AppDefs.ACTION_ES1_ANOTACAO_INDIVIDUAL_CI,
//				listener) );
//	
//			mnubar.add(this.mnu1);
//
//			result = AppDefs.RSOK;
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	public int createMenu2(JMenuBar mnubar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			this.mnu2 = new JMenu(AppDefs.MNU_ES2);

			//COLUNA CENTRO-DIAMETRO
			//
			JMenu submnuColunaCD = FormControlUtil.newMenu(AppDefs.MNU_ES2_COLUNA_CD);
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );

			submnuColunaCD.add(new JSeparator());
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );

			submnuColunaCD.add(new JSeparator());
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );

			submnuColunaCD.add(new JSeparator());
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES2_COLUNA_CD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES2_COLUNA_CD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );

			submnuColunaCD.add(new JSeparator());
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES2_COLUNA_CD50,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES2_COLUNA_CD75,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );
			
			submnuColunaCD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES2_COLUNA_CD100,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );

			this.mnu2.add(submnuColunaCD);

			//COLUNA TANGENTE-DIAMETRO
			//
			JMenu submnuColunaTD = FormControlUtil.newMenu(AppDefs.MNU_ES2_COLUNA_TD);

			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTD.add(new JSeparator());
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTD.add(new JSeparator());
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTD.add(new JSeparator());
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES2_COLUNA_TD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES2_COLUNA_TD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTD.add(new JSeparator());
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES2_COLUNA_TD50,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES2_COLUNA_TD75,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );
			
			submnuColunaTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES2_COLUNA_TD100,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );

			this.mnu2.add(submnuColunaTD);

			//COLUNA TANGENTE-TANGENTE-DIAMETRO
			//
			JMenu submnuColunaTTD = FormControlUtil.newMenu(AppDefs.MNU_ES2_COLUNA_TTD);

			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO,
				listener) );
			
			submnuColunaTTD.add(new JSeparator());

			this.mnu2.add(submnuColunaTTD);

			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );

			this.mnu2.add(submnuColunaTTD);

			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );

			this.mnu2.add(submnuColunaTTD);

			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES2_COLUNA_TTD150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES2_COLUNA_TTD200,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			submnuColunaTTD.add(new JSeparator());

			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES2_COLUNA_TTD50,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES2_COLUNA_TTD75,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
				listener) );
			
			submnuColunaTTD.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES2_COLUNA_TTD100,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO,
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
			this.mnu3 = new JMenu(AppDefs.MNU_ES3);
			
			//TUBULACAO PRIMARIO
			//
			JMenu submnuPrimario = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_PRIMARIO);
			
//			submnuPrimario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_PRIMARIO_75,
//				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_75,
//				listener) );
//			
//			submnuPrimario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_PRIMARIO_100,
//				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_100,
//				listener) );
//			
//			submnuPrimario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_PRIMARIO_150,
//				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_150,
//				listener) );
//
//			submnuPrimario.add(new JSeparator());

			submnuPrimario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_PRIMARIO_PL_75,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_PL_75,
				listener) );
			
			submnuPrimario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_PRIMARIO_PL_100,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_PL_100,
				listener) );
			
			submnuPrimario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_PRIMARIO_PL_150,
				AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_PL_150,
				listener) );

			this.mnu3.add(submnuPrimario);

			//TUBULACAO SECUNDARIO
			//
			JMenu submnuSecundario = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO);
			
//			submnuSecundario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_40,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_40,
//				listener) );
//			
//			submnuSecundario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_50,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_50,
//				listener) );
//			
//			submnuSecundario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_75,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_75,
//				listener) );
//			
//			submnuSecundario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_100,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_100,
//				listener) );
//			
//			submnuSecundario.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_150,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_150,
//				listener) );
//
//			submnuSecundario.add(new JSeparator());
			
			submnuSecundario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_PL_40,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_PL_40,
				listener) );
			
			submnuSecundario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_PL_50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_PL_50,
				listener) );
			
			submnuSecundario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_PL_75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_PL_75,
				listener) );
			
			submnuSecundario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_PL_100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_PL_100,
				listener) );
			
			submnuSecundario.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_PL_150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_PL_150,
				listener) );

			this.mnu3.add(submnuSecundario);

			//TUBULACAO SECUNDARIO-GORDURA
			//
			JMenu submnuSecundarioGordura = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA);
			
//			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_40,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_40,
//				listener) );
//			
//			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_50,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_50,
//				listener) );
//			
//			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_75,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_75,
//				listener) );
//			
//			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_100,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_100,
//				listener) );
//			
//			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_150,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_150,
//				listener) );
//
//			submnuSecundarioGordura.add(new JSeparator());
			
			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_40,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_40,
				listener) );
			
			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_50,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_50,
				listener) );
			
			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_75,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_75,
				listener) );
			
			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_100,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_100,
				listener) );
			
			submnuSecundarioGordura.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_150,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_PL_150,
				listener) );

			this.mnu3.add(submnuSecundarioGordura);

			JMenu submnuSecundarioSabao = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO);
						
//			submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_40,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_40,
//				listener) );
//			
//			submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_50,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_50,
//				listener) );
//			
//			submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_75,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_75,
//				listener) );
//			
//			submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_100,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_100,
//				listener) );
//			
//			submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_150,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_150,
//				listener) );
//
//			submnuSecundarioSabao.add(new JSeparator());
			
			submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_40,
					AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_40,
					listener) );
				
				submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_50,
					AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_50,
					listener) );
				
				submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_75,
					AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_75,
					listener) );
				
				submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_100,
					AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_100,
					listener) );
				
				submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_150,
					AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_150,
					listener) );
				
				submnuSecundarioSabao.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_200,
					AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_PL_200,
					listener) );

			this.mnu3.add(submnuSecundarioSabao);
			
			JMenu submnuVentilacao = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_VENTILACAO);
			
//			submnuVentilacao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_VENTILACAO_50,
//				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO_50,
//				listener) );
//			
//			submnuVentilacao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_VENTILACAO_75,
//				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO_75,
//				listener) );
//			
//			submnuVentilacao.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_VENTILACAO_100,
//				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO_100,
//				listener) );
//
//			submnuVentilacao.add(new JSeparator());
			
			submnuVentilacao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_VENTILACAO_PL_50,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO_PL_50,
				listener) );
			
			submnuVentilacao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_VENTILACAO_PL_75,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO_PL_75,
				listener) );
			
			submnuVentilacao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_VENTILACAO_PL_100,
				AppDefs.ACTION_ES3_TUBULACAO_VENTILACAO_PL_100,
				listener) );

			this.mnu3.add(submnuVentilacao);
			
			this.mnu3.add(new JSeparator());

//			JMenu submnuConexaoRS45 = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS45);
//			
//			submnuConexaoRS45.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS45,
//				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS45,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
//				listener) );
//			
//			submnuConexaoRS45.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS45,
//				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS45,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
//				listener) );
//			
//			submnuConexaoRS45.add(FormControlUtil.newMenuItem(
//				AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS45,
//				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS45,
//				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
//				listener) );
//			
//			this.mnu3.add(submnuConexaoRS45);

			JMenu submnuConexaoRS90 = FormControlUtil.newMenu(AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS90);
			
			submnuConexaoRS90.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS90,
				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS90,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO,
				listener) );
			
			submnuConexaoRS90.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS90,
				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS90,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA,
				listener) );
			
			submnuConexaoRS90.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES3_TUBULACAO_PL_PAREDE_TO_RS90,
				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS90,
				AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO,
				listener) );
			
			this.mnu3.add(submnuConexaoRS90);

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

			this.mnu4 = new JMenu(AppDefs.MNU_ES4);
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_INSERE_RS,
				AppDefs.ACTION_ES4_INSERE_RS,
				listener) );

			this.mnu4.add(new JSeparator());
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_CAIXA_INSPECAO,
				AppDefs.ACTION_ES4_CAIXA_INSPECAO,
				listener) );
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_CAIXA_PASSAGEM_60X60,
				AppDefs.ACTION_ES4_CAIXA_PASSAGEM_60X60,
				listener) );
			
			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_CAIXA_GORDURA_DUPLA,
				AppDefs.ACTION_ES4_CAIXA_GORDURA_DUPLA,
				listener) );

			this.mnu4.add(new JSeparator());
			
			JMenu submnuBujao = FormControlUtil.newMenu(AppDefs.MNU_ES4_BUJAO);
			
			submnuBujao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_BUJAO40,
				AppDefs.ACTION_ES4_BUJAO40,
				listener) );
			
			submnuBujao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_BUJAO50,
				AppDefs.ACTION_ES4_BUJAO50,
				listener) );
			
			submnuBujao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_BUJAO75,
				AppDefs.ACTION_ES4_BUJAO75,
				listener) );
			
			submnuBujao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_BUJAO100,
				AppDefs.ACTION_ES4_BUJAO100,
				listener) );
			
			submnuBujao.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_BUJAO150,
				AppDefs.ACTION_ES4_BUJAO150,
				listener) );

			this.mnu4.add(submnuBujao);

			this.mnu4.add(new JSeparator());

			JMenu submnuOperculado = FormControlUtil.newMenu(AppDefs.MNU_ES4_TUBO_OPERCULADO);
			
			submnuOperculado.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_TUBO_OPERCULADO_40,
				AppDefs.ACTION_ES4_TUBO_OPERCULADO_40,
				listener) );
			
			submnuOperculado.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_TUBO_OPERCULADO_50,
				AppDefs.ACTION_ES4_TUBO_OPERCULADO_50,
				listener) );
			
			submnuOperculado.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_TUBO_OPERCULADO_75,
				AppDefs.ACTION_ES4_TUBO_OPERCULADO_75,
				listener) );
			
			submnuOperculado.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_TUBO_OPERCULADO_100,
				AppDefs.ACTION_ES4_TUBO_OPERCULADO_100,
				listener) );
			
			submnuOperculado.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_TUBO_OPERCULADO_150,
				AppDefs.ACTION_ES4_TUBO_OPERCULADO_150,
				listener) );

			this.mnu4.add(submnuOperculado);
			
			this.mnu4.add(new JSeparator());

			this.mnu4.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES4_FINAL_TUBULACAO,
				AppDefs.ACTION_ES4_FINAL_TUBULACAO,
				listener) );
				
			mnubar.add(mnu4);

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
			
			mnu5 = new JMenu(AppDefs.MNU_ES5);

			mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES5_SETA_INDICADOR_DESCE,
				AppDefs.ACTION_ES5_SETA_INDICADOR_DESCE,
				listener) );
			
			mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES5_SETA_INDICADOR_PASSA,
				AppDefs.ACTION_ES5_SETA_INDICADOR_PASSA,
				listener) );
			
			mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES5_SETA_INDICADOR_SOBE,
				AppDefs.ACTION_ES5_SETA_INDICADOR_SOBE,
				listener) );

			mnu5.add(new JSeparator());
			
			mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES5_SETA_SIMPLES_DESCE,
				AppDefs.ACTION_ES5_SETA_SIMPLES_DESCE,
				listener) );
			
			mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES5_SETA_SIMPLES_PASSA,
				AppDefs.ACTION_ES5_SETA_SIMPLES_PASSA,
				listener) );
			
			mnu5.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_ES5_SETA_SIMPLES_SOBE,
				AppDefs.ACTION_ES5_SETA_SIMPLES_SOBE,
				listener) );
			
			mnubar.add(mnu5);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//TOOLBAR_MENU
	//
//	public int createToolbarMenu1(JPanel iconmnu, ActionListener listener) 
//	{
//		int result = AppDefs.RSERR;
//		
//		try {
//			MainFrame frm = MainFrame.getMainFrame();
//			
//			this.mnuToolbar1 = new JPanel();		
//
//			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
//			this.mnuToolbar1.setLayout(layout1);
//			
//			JButton btnInsere = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_INSERE_CI, AppDefs.ACTION_ES1_INSERE_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_INSERE_CI);
//			this.mnuToolbar1.add(btnInsere);
//
//			JButton btnLigacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_LIGACAO_CI, AppDefs.ACTION_ES1_LIGACAO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_LIGACAO_CI);
//			this.mnuToolbar1.add(btnLigacao);
//
//			JButton btnVerifLigacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_VERIF_LIGACAO_CI_ONOFF, AppDefs.ACTION_ES1_VERIF_LIGACAO_CI_ONOFF, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_VERIF_LIGACAO_CI_ONOFF);
//			this.mnuToolbar1.add(btnVerifLigacao);
//
//			JButton btnDimensiona = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_DIMENSIONA_CI, AppDefs.ACTION_ES1_DIMENSIONA_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_DIMENSIONA_CI);
//			this.mnuToolbar1.add(btnDimensiona);
//
//			JButton btnGerarPlanilhaCalculo = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_GERAR_PLANILHA_CALCULO_CI, AppDefs.ACTION_ES1_GERAR_PLANILHA_CALCULO_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_GERAR_PLANILHA_CALCULO_CI);
//			this.mnuToolbar1.add(btnGerarPlanilhaCalculo);
//
//			JButton btnGerarPlantaPerfis = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_GERAR_PLANTA_PERFIS_CI, AppDefs.ACTION_ES1_GERAR_PLANTA_PERFIS_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_GERAR_PLANTA_PERFIS_CI);
//			this.mnuToolbar1.add(btnGerarPlantaPerfis);
//
//			JButton btnAnotacaoIndividual = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES1_ANOTACAO_INDIVIDUAL_CI, AppDefs.ACTION_ES1_ANOTACAO_INDIVIDUAL_CI, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES1_ANOTACAO_INDIVIDUAL_CI);
//			this.mnuToolbar1.add(btnAnotacaoIndividual);
//			
//			iconmnu.add(this.mnuToolbar1);
//
//			result = AppDefs.RSOK;			
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	public int createToolbarMenu2(JPanel iconmnu, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			this.mnuToolbar2 = new JPanel();		

			GridLayout layout1 = new GridLayout(1, 0, 0, 0);
			this.mnuToolbar2.setLayout(layout1);
			
			JButton btnColunaCD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_CD50, AppDefs.ACTION_ES2_COLUNA_CD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_CD50);
			this.mnuToolbar2.add(btnColunaCD50);

//			JButton btnColunaCD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_CD75, AppDefs.ACTION_ES2_COLUNA_CD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_CD75);
//			this.mnuToolbar2.add(btnColunaCD75);
//
//			JButton btnColunaCD100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_CD100, AppDefs.ACTION_ES2_COLUNA_CD100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_CD100);
//			this.mnuToolbar2.add(btnColunaCD100);
//
//			JButton btnColunaCD150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_CD150, AppDefs.ACTION_ES2_COLUNA_CD150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_CD150);
//			this.mnuToolbar2.add(btnColunaCD150);

			JButton btnColunaTD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TD50, AppDefs.ACTION_ES2_COLUNA_TD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TD50);
			this.mnuToolbar2.add(btnColunaTD50);

//			JButton btnColunaTD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TD75, AppDefs.ACTION_ES2_COLUNA_TD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TD75);
//			this.mnuToolbar2.add(btnColunaTD75);
//
//			JButton btnColunaTD100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TD100, AppDefs.ACTION_ES2_COLUNA_TD100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TD100);
//			this.mnuToolbar2.add(btnColunaTD100);
//
//			JButton btnColunaTD150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TD150, AppDefs.ACTION_ES2_COLUNA_TD150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TD150);
//			this.mnuToolbar2.add(btnColunaTD150);

			JButton btnColunaTTD50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TTD50, AppDefs.ACTION_ES2_COLUNA_TTD50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TTD50);
			this.mnuToolbar2.add(btnColunaTTD50);

//			JButton btnColunaTTD75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TTD75, AppDefs.ACTION_ES2_COLUNA_TTD75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TTD75);
//			this.mnuToolbar2.add(btnColunaTTD75);
//
//			JButton btnColunaTTD100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TTD100, AppDefs.ACTION_ES2_COLUNA_TTD100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TTD100);
//			this.mnuToolbar2.add(btnColunaTTD100);
//
//			JButton btnColunaTTD150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES2_COLUNA_TTD150, AppDefs.ACTION_ES2_COLUNA_TTD150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES2_COLUNA_TTD150);
//			this.mnuToolbar2.add(btnColunaTTD150);

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

			JButton btnPrimario75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_PRIMARIO_75, AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_PRIMARIO_75);
			this.mnuToolbar3.add(btnPrimario75);

//			JButton btnPrimario100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_PRIMARIO_100, AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_PRIMARIO_100);
//			this.mnuToolbar3.add(btnPrimario100);
//
//			JButton btnPrimario150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_PRIMARIO_150, AppDefs.ACTION_ES3_TUBULACAO_PRIMARIO_150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_PRIMARIO_150);
//			this.mnuToolbar3.add(btnPrimario150);

			JButton btnSecundario40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_40, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_40);
			this.mnuToolbar3.add(btnSecundario40);

//			JButton btnSecundario50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_50, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_50);
//			this.mnuToolbar3.add(btnSecundario50);
//
//			JButton btnSecundario75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_75, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_75);
//			this.mnuToolbar3.add(btnSecundario75);
//
//			JButton btnSecundario100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_100, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_100);
//			this.mnuToolbar3.add(btnSecundario100);
//
//			JButton btnSecundario150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_150, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_150);
//			this.mnuToolbar3.add(btnSecundario150);

			JButton btnSecundarioGordura40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_40, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_40);
			this.mnuToolbar3.add(btnSecundarioGordura40);

//			JButton btnSecundarioGordura50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_50, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_50);
//			this.mnuToolbar3.add(btnSecundarioGordura50);
//
//			JButton btnSecundarioGordura75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_75, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_75);
//			this.mnuToolbar3.add(btnSecundarioGordura75);
//
//			JButton btnSecundarioGordura100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_100, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_100);
//			this.mnuToolbar3.add(btnSecundarioGordura100);
//
//			JButton btnSecundarioGordura150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_150, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_GORDURA_150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_GORDURA_150);
//			this.mnuToolbar3.add(btnSecundarioGordura150);

			JButton btnSecundarioSabao40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_40, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_40);
			this.mnuToolbar3.add(btnSecundarioSabao40);

//			JButton btnSecundarioSabao50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_50, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_50);
//			this.mnuToolbar3.add(btnSecundarioSabao50);
//
//			JButton btnSecundarioSabao75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_75, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_75);
//			this.mnuToolbar3.add(btnSecundarioSabao75);
//
//			JButton btnSecundarioSabao100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_100, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_100);
//			this.mnuToolbar3.add(btnSecundarioSabao100);
//
//			JButton btnSecundarioSabao150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_150, AppDefs.ACTION_ES3_TUBULACAO_SECUNDARIO_SABAO_150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_SECUNDARIO_SABAO_150);
//			this.mnuToolbar3.add(btnSecundarioSabao150);

			JButton btnConexaoToRS45 = FormControlUtil.newImageButton(
				frm, 
				AppDefs.ICOMNU_ES3_TUBULACAO_PL_PAREDE_TO_RS45, 
				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS45, 
				16, 
				15, 
				true, 
				listener, 
				AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_PL_PAREDE_TO_RS45);
			this.mnuToolbar3.add(btnConexaoToRS45);

			JButton btnConexaoToRS90 = FormControlUtil.newImageButton(
				frm, 
				AppDefs.ICOMNU_ES3_TUBULACAO_PL_PAREDE_TO_RS90, 
				AppDefs.ACTION_ES3_TUBULACAO_PL_PAREDE_TO_RS90, 
				16, 
				15, 
				true, 
				listener, 
				AppDefs.TOOLTIP_ICOMNU_ES3_TUBULACAO_PL_PAREDE_TO_RS90);
			this.mnuToolbar3.add(btnConexaoToRS90);

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

			JButton btnInsereRS = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_INSERE_RS, AppDefs.ACTION_ES4_INSERE_RS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_INSERE_RS);
			this.mnuToolbar4.add(btnInsereRS);

			JButton btnCaixaInspecao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_CAIXA_INSPECAO, AppDefs.ACTION_ES4_CAIXA_INSPECAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_CAIXA_INSPECAO);
			this.mnuToolbar4.add(btnCaixaInspecao);

			JButton btnCaixaPassagem60x60 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_CAIXA_PASSAGEM_60X60, AppDefs.ACTION_ES4_CAIXA_PASSAGEM_60X60, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_CAIXA_PASSAGEM_60X60);
			this.mnuToolbar4.add(btnCaixaPassagem60x60);

			JButton btnCaixaGorduraDupla = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_CAIXA_GORDURA_DUPLA, AppDefs.ACTION_ES4_CAIXA_GORDURA_DUPLA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_CAIXA_GORDURA_DUPLA);
			this.mnuToolbar4.add(btnCaixaGorduraDupla);

			JButton btnRaloConico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_RALO_CONICO, AppDefs.ACTION_ES4_RALO_CONICO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_RALO_CONICO);
			this.mnuToolbar4.add(btnRaloConico);

			JButton btnRaloHemisferico = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_RALO_HEMISFERICO, AppDefs.ACTION_ES4_RALO_HEMISFERICO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_RALO_HEMISFERICO);
			this.mnuToolbar4.add(btnRaloHemisferico);

			JButton btnBujao40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_BUJAO40, AppDefs.ACTION_ES4_BUJAO40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_BUJAO40);
			this.mnuToolbar4.add(btnBujao40);

//			JButton btnBujao50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_BUJAO50, AppDefs.ACTION_ES4_BUJAO50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_BUJAO50);
//			this.mnuToolbar4.add(btnBujao50);
//
//			JButton btnBujao75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_BUJAO75, AppDefs.ACTION_ES4_BUJAO75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_BUJAO75);
//			this.mnuToolbar4.add(btnBujao75);
//
//			JButton btnBujao100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_BUJAO100, AppDefs.ACTION_ES4_BUJAO100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_BUJAO100);
//			this.mnuToolbar4.add(btnBujao100);
//
//			JButton btnBujao150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_BUJAO150, AppDefs.ACTION_ES4_BUJAO150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_BUJAO150);
//			this.mnuToolbar4.add(btnBujao150);

			JButton btnTuboOperculado40 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_TUBO_OPERCULADO_40, AppDefs.ACTION_ES4_TUBO_OPERCULADO_40, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_40);
			this.mnuToolbar4.add(btnTuboOperculado40);

//			JButton btnTuboOperculado50 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_TUBO_OPERCULADO_50, AppDefs.ACTION_ES4_TUBO_OPERCULADO_50, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_50);
//			this.mnuToolbar4.add(btnTuboOperculado50);
//
//			JButton btnTuboOperculado75 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_TUBO_OPERCULADO_75, AppDefs.ACTION_ES4_TUBO_OPERCULADO_75, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_75);
//			this.mnuToolbar4.add(btnTuboOperculado75);
//
//			JButton btnTuboOperculado100 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_TUBO_OPERCULADO_100, AppDefs.ACTION_ES4_TUBO_OPERCULADO_100, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_100);
//			this.mnuToolbar4.add(btnTuboOperculado100);
//
//			JButton btnTuboOperculado150 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_TUBO_OPERCULADO_150, AppDefs.ACTION_ES4_TUBO_OPERCULADO_150, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_TUBO_OPERCULADO_150);
//			this.mnuToolbar4.add(btnTuboOperculado150);

			JButton btnFinalTubulacao = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES4_FINAL_TUBULACAO, AppDefs.ACTION_ES4_FINAL_TUBULACAO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES4_FINAL_TUBULACAO);
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

			JButton btnIndicadorDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES5_SETA_INDICADOR_DESCE, AppDefs.ACTION_ES5_SETA_INDICADOR_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES5_SETA_INDICADOR_DESCE);
			this.mnuToolbar5.add(btnIndicadorDesce);

//			JButton btnIndicadorPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES5_SETA_INDICADOR_PASSA, AppDefs.ACTION_ES5_SETA_INDICADOR_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES5_SETA_INDICADOR_PASSA);
//			this.mnuToolbar5.add(btnIndicadorPassa);
//
//			JButton btnIndicadorSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES5_SETA_INDICADOR_SOBE, AppDefs.ACTION_ES5_SETA_INDICADOR_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES5_SETA_INDICADOR_SOBE);
//			this.mnuToolbar5.add(btnIndicadorSobe);

			JButton btnSetaDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES5_SETA_SIMPLES_DESCE, AppDefs.ACTION_ES5_SETA_SIMPLES_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES5_SETA_SIMPLES_DESCE);
			this.mnuToolbar5.add(btnSetaDesce);

//			JButton btnSetaPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES5_SETA_SIMPLES_PASSA, AppDefs.ACTION_ES5_SETA_SIMPLES_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES5_SETA_SIMPLES_PASSA);
//			this.mnuToolbar5.add(btnSetaPassa);
//
//			JButton btnSetaSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ES5_SETA_SIMPLES_SOBE, AppDefs.ACTION_ES5_SETA_SIMPLES_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ES5_SETA_SIMPLES_SOBE);
//			this.mnuToolbar5.add(btnSetaSobe);

			iconmnu.add(this.mnuToolbar5);

			result = AppDefs.RSOK;			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
//Public
	
	public EsgotoModule(AppMain app, AppCadMain cad)
	{
		EsgotoModule.gAppMod = this;
		
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
			//this.createMenu1(mnubar, listener); 
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
			//this.createToolbarMenu1(iconmnu, listener);
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
		//this.mnu1.setVisible(bVisible);
		this.mnu2.setVisible(bVisible);
		this.mnu3.setVisible(bVisible);
		this.mnu4.setVisible(bVisible);
		this.mnu5.setVisible(bVisible);

		//TOOLBAR_MENU
		//
		//this.mnuToolbar1.setVisible(bVisible);
		this.mnuToolbar2.setVisible(bVisible);
		this.mnuToolbar3.setVisible(bVisible);
		this.mnuToolbar4.setVisible(bVisible);
		this.mnuToolbar5.setVisible(bVisible);
	}
	
	/* Getters/Setters */

	public static IModule getAppModule() {
		return EsgotoModule.gAppMod;
	}

}
