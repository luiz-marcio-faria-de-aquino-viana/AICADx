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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class GasModule implements IModule 
{
//Private Static
	private static IModule gAppMod = null;

//Private
	private AppMain app = null;
	private AppCadMain cad = null;
	
	private JMenu mnu1 = null;
	
	private JPanel mnuToolbar = null;		
	
//Public
	
	public GasModule(AppMain app, AppCadMain cad)
	{
		GasModule.gAppMod = this;
		
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
			//MENU: GAS 1
			//
			this.mnu1 = new JMenu(AppDefs.MNU_G1);
						
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_TB_GAS_TETO,
				AppDefs.ACTION_G1_INSERE_TB_GAS_TETO,
				listener) );
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_TB_GAS_PISO,
				AppDefs.ACTION_G1_INSERE_TB_GAS_PISO,
				listener) );

			this.mnu1.add(new JSeparator());
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_PONTO_GAS,
				AppDefs.ACTION_G1_INSERE_PONTO_GAS,
				listener) );
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_SEPTO_GAS,
				AppDefs.ACTION_G1_INSERE_SEPTO_GAS,
				listener) );
						
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_CANALETA_GAS,
				AppDefs.ACTION_G1_INSERE_CANALETA_GAS,
				listener) );

			this.mnu1.add(new JSeparator());

			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_PRUMADA_GAS,
				AppDefs.ACTION_G1_INSERE_PRUMADA_GAS,
				listener) );
			
			this.mnu1.add(new JSeparator());
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_SETA_SIMPLES_SOBE,
				AppDefs.ACTION_G1_INSERE_SETA_SIMPLES_SOBE,
				listener) );
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_SETA_SIMPLES_PASSA,
				AppDefs.ACTION_G1_INSERE_SETA_SIMPLES_PASSA,
				listener) );
			
			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_SETA_SIMPLES_DESCE,
				AppDefs.ACTION_G1_INSERE_SETA_SIMPLES_DESCE,
				listener) );
			
			this.mnu1.add(new JSeparator());

			this.mnu1.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_G1_INSERE_INDICADOR_ECONOMIA,
				AppDefs.ACTION_G1_INSERE_INDICADOR_ECONOMIA,
				listener) );

			mnubar.add(this.mnu1);

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

			//MENU: GAS 1
			//
			JButton btnInsereTbGasTeto = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_TB_GAS_TETO, AppDefs.ACTION_G1_INSERE_TB_GAS_TETO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_TB_GAS_TETO);
			this.mnuToolbar.add(btnInsereTbGasTeto);

			JButton btnInsereTbGasPiso = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_TB_GAS_PISO, AppDefs.ACTION_G1_INSERE_TB_GAS_PISO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_TB_GAS_PISO);
			this.mnuToolbar.add(btnInsereTbGasPiso);
			
			JButton btnInserePontoGas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_PONTO_GAS, AppDefs.ACTION_G1_INSERE_PONTO_GAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_PONTO_GAS);
			this.mnuToolbar.add(btnInserePontoGas);

			JButton btnInsereSepto = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_SEPTO_GAS, AppDefs.ACTION_G1_INSERE_SEPTO_GAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_SEPTO_GAS);
			this.mnuToolbar.add(btnInsereSepto);
			
			JButton btnInsereCanaletaGas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_CANALETA_GAS, AppDefs.ACTION_G1_INSERE_CANALETA_GAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_CANALETA_GAS);
			this.mnuToolbar.add(btnInsereCanaletaGas);

			JButton btnInserePrumada = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_PRUMADA_GAS, AppDefs.ACTION_G1_INSERE_PRUMADA_GAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_PRUMADA_GAS);
			this.mnuToolbar.add(btnInserePrumada);

			JButton btnSetaSobe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_SETA_SIMPLES_SOBE, AppDefs.ACTION_G1_INSERE_SETA_SIMPLES_SOBE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_SETA_SIMPLES_SOBE);
			this.mnuToolbar.add(btnSetaSobe);

			JButton btnSetaPassa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_SETA_SIMPLES_PASSA, AppDefs.ACTION_G1_INSERE_SETA_SIMPLES_PASSA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_SETA_SIMPLES_PASSA);
			this.mnuToolbar.add(btnSetaPassa);

			JButton btnSetaDesce = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_SETA_SIMPLES_DESCE, AppDefs.ACTION_G1_INSERE_SETA_SIMPLES_DESCE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_SETA_SIMPLES_DESCE);
			this.mnuToolbar.add(btnSetaDesce);

			JButton btnIndicadorEconomia = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_G1_INSERE_INDICADOR_ECONOMIA, AppDefs.ACTION_G1_INSERE_INDICADOR_ECONOMIA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_G1_INSERE_INDICADOR_ECONOMIA);
			this.mnuToolbar.add(btnIndicadorEconomia);
			
			iconmnu.add(this.mnuToolbar);

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
		this.mnuToolbar.setVisible(bVisible);
	}
	
	/* Getters/Setters */

	public static IModule getAppModule() {
		return GasModule.gAppMod;
	}

}
