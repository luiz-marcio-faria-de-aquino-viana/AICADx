/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ArquiteturaModule.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/04/2025
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

public class ArquiteturaModule implements IModule 
{
//Private Static
	private static IModule gAppMod = null;

//Private
	private AppMain app = null;
	private AppCadMain cad = null;
	
	private JMenu mnu1 = null;
	private JMenu mnu2 = null;
		
	private JPanel mnuToolbar1 = null;		
	private JPanel mnuToolbar2 = null;		
	
//Public
	
	public ArquiteturaModule(AppMain app, AppCadMain cad)
	{
		ArquiteturaModule.gAppMod = this;

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

	//POPUP_MENUS
	//
	public void createMenu1(JMenuBar mnubar, ActionListener listener) 
	{
		//MENU: ARCHITECTURE MENU 1
		//
		this.mnu1 = new JMenu(AppDefs.MNU_ARQ1);
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//AppDefs.MNU_ARQ1_PILARRETANGULAR,
		//AppDefs.ACTION_ARQ1_PILARRETANGULAR,
		//listener) );
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//AppDefs.MNU_ARQ1_PILARCIRCULAR,
		//AppDefs.ACTION_ARQ1_PILARCIRCULAR,
		//listener) );
		
		//this.mnu1.add(new JSeparator());
		
		this.mnu1.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ1_PISO,
			AppDefs.ACTION_ARQ1_PISO,
			listener) );

		this.mnu1.add(new JSeparator());

		this.mnu1.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ1_PAREDE,
			AppDefs.ACTION_ARQ1_PAREDE,
			listener) );

		//this.mnu1.add(new JSeparator());
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ1_AMBIENTE1,
		//	AppDefs.ACTION_ARQ1_AMBIENTE1,
		//	listener) );
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ1_AMBIENTE2,
		//	AppDefs.ACTION_ARQ1_AMBIENTE2,
		//	listener) );
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ1_AMBIENTE3,
		//	AppDefs.ACTION_ARQ1_AMBIENTE3,
		//	listener) );

		//this.mnu1.add(new JSeparator());
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ1_MALHA,
		//	AppDefs.ACTION_ARQ1_MALHA,
		//	listener) );

		this.mnu1.add(new JSeparator());
		
		this.mnu1.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ1_ABERTURA,
			AppDefs.ACTION_ARQ1_ABERTURA,
			listener) );

		this.mnu1.add(new JSeparator());
		
		this.mnu1.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ1_PORTA,
			AppDefs.ACTION_ARQ1_PORTA,
			listener) );
		
		this.mnu1.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ1_PDUPLA,
			AppDefs.ACTION_ARQ1_PDUPLA,
			listener) );
		
		//this.mnu1.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ1_PCORRER,
		//	AppDefs.ACTION_ARQ1_PCORRER,
		//	listener) );

		this.mnu1.add(new JSeparator());
		
		this.mnu1.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ1_JANELA,
			AppDefs.ACTION_ARQ1_JANELA,
			listener) );

		mnubar.add(mnu1);
	}
	
	public void createMenu2(JMenuBar mnubar, ActionListener listener) 
	{
		//MENU: ARCHITECTURE MENU 2
		//
		this.mnu2 = new JMenu(AppDefs.MNU_ARQ2);
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_BIDE,
		//	AppDefs.ACTION_ARQ2_BIDE,
		//	listener) );
					
		this.mnu2.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ2_VASOSANITARIO,
			AppDefs.ACTION_ARQ2_VASOSANITARIO,
			listener) );
		
		this.mnu2.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ2_VASOCAIXAACLOPADA,
			AppDefs.ACTION_ARQ2_VASOCAIXAACLOPADA,
			listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_MICTORIO,
		//	AppDefs.ACTION_ARQ2_MICTORIO,
		//	listener) );
		
		this.mnu2.add(FormControlUtil.newMenuItem(
			AppDefs.MNU_ARQ2_LAVATORIOGRANDE,
			AppDefs.ACTION_ARQ2_LAVATORIOGRANDE,
			listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_LAVATORIOPEQUENO,
		//	AppDefs.ACTION_ARQ2_LAVATORIOPEQUENO,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_LAVATORIOBANCA,
		//	AppDefs.ACTION_ARQ2_LAVATORIOBANCA,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_CHUVEIRO,
		//	AppDefs.ACTION_ARQ2_CHUVEIRO,
		//	listener) );

		//this.mnu2.add(new JSeparator());
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_PIASIMPLES,
		//	AppDefs.ACTION_ARQ2_PIASIMPLES,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_PIADUPLA,
		//	AppDefs.ACTION_ARQ2_PIADUPLA,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_FOGAO4BOCAS,
		//	AppDefs.ACTION_ARQ2_FOGAO4BOCAS,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_FOGAO6BOCAS,
		//	AppDefs.ACTION_ARQ2_FOGAO6BOCAS,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_GELADEIRA,
		//	AppDefs.ACTION_ARQ2_GELADEIRA,
		//	listener) );
		
		//this.mnu2.add(new JSeparator());
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_LAVADOURAROUPA,
		//	AppDefs.ACTION_ARQ2_LAVADOURAROUPA,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_TANQUE,
		//	AppDefs.ACTION_ARQ2_TANQUE,
		//	listener) );
		
		//this.mnu2.add(new JSeparator());
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_AQUECEDOR,
		//	AppDefs.ACTION_ARQ2_AQUECEDOR,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_BOILER,
		//	AppDefs.ACTION_ARQ2_BOILER,
		//	listener) );
		
		//this.mnu2.add(new JSeparator());
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_BOMBARECALQUE,
		//	AppDefs.ACTION_ARQ2_BOMBARECALQUE,
		//	listener) );
		
		//this.mnu2.add(FormControlUtil.newMenuItem(
		//	AppDefs.MNU_ARQ2_BOMBAAGUASSERVIDAS,
		//	AppDefs.ACTION_ARQ2_BOMBAAGUASSERVIDAS,
		//	listener) );

		mnubar.add(this.mnu2);
	}	
		
	@Override
	public int createMenu(JMenuBar mnubar, ActionListener listener) 
	{
		this.createMenu1(mnubar, listener);
		this.createMenu2(mnubar, listener);

		return AppDefs.RSOK;
	}

	//TOOLBAR_MENUS
	//
	public void createToolbarMenu1(JPanel iconmnu, ActionListener listener) 
	{
		//MENU: TOOLBAR_ARCHITECTURE 1
		//
		MainFrame frm = MainFrame.getMainFrame();

		this.mnuToolbar1 = new JPanel();		

		GridLayout layout1 = new GridLayout(1, 0, 0, 0);
		this.mnuToolbar1.setLayout(layout1);

		//JButton btnPilarRetangular = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PILARRETANGULAR, AppDefs.ACTION_ARQ1_PILARRETANGULAR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PILARRETANGULAR);
		//this.mnuToolbar1.add(btnPilarRetangular);

		//JButton btnPilarCircular = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PILARCIRCULAR, AppDefs.ACTION_ARQ1_PILARCIRCULAR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PILARCIRCULAR);
		//this.mnuToolbar1.add(btnPilarCircular);

		JButton btnPiso = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PISO, AppDefs.ACTION_ARQ1_PISO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PISO);
		this.mnuToolbar1.add(btnPiso);
		
		JButton btnParede = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PAREDE, AppDefs.ACTION_ARQ1_PAREDE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PAREDE);
		this.mnuToolbar1.add(btnParede);
		
		//JButton btnAmbiente1 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_AMBIENTE1, AppDefs.ACTION_ARQ1_AMBIENTE1, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AMBIENTE1);
		//this.mnuToolbar1.add(btnAmbiente1);
		
		//JButton btnAmbiente2 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_AMBIENTE2, AppDefs.ACTION_ARQ1_AMBIENTE2, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AMBIENTE2);
		//this.mnuToolbar1.add(btnAmbiente2);
		
		//JButton btnAmbiente3 = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_AMBIENTE3, AppDefs.ACTION_ARQ1_AMBIENTE3, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_AMBIENTE3);
		//this.mnuToolbar1.add(btnAmbiente3);
		
		//JButton btnMalha = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_MALHA, AppDefs.ACTION_ARQ1_MALHA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_MALHA);
		//this.mnuToolbar1.add(btnMalha);
		
		JButton btnAbertura = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_ABERTURA, AppDefs.ACTION_ARQ1_ABERTURA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_ABERTURA);
		this.mnuToolbar1.add(btnAbertura);

		JButton btnPorta = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PORTA, AppDefs.ACTION_ARQ1_PORTA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PORTA);
		this.mnuToolbar1.add(btnPorta);

		JButton btnPortaDupla = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PDUPLA, AppDefs.ACTION_ARQ1_PDUPLA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PDUPLA);
		this.mnuToolbar1.add(btnPortaDupla);

		//JButton btnPortaCorrer = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_PCORRER, AppDefs.ACTION_ARQ1_PCORRER, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_PCORRER);
		//this.mnuToolbar1.add(btnPortaCorrer);
		
		JButton btnJanela = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ1_JANELA, AppDefs.ACTION_ARQ1_JANELA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ1_JANELA);
		this.mnuToolbar1.add(btnJanela);

		iconmnu.add(this.mnuToolbar1);
	}
	
	public void createToolbarMenu2(JPanel iconmnu, ActionListener listener) 
	{
		//MENU: TOOLBAR_ARCHITECTURE 2
		//
		MainFrame frm = MainFrame.getMainFrame();

		this.mnuToolbar2 = new JPanel();		

		GridLayout layout1 = new GridLayout(1, 0, 0, 0);
		mnuToolbar2.setLayout(layout1);

		//JButton btnBide = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_BIDE, AppDefs.ACTION_ARQ2_BIDE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_BIDE);
		//mnuToolbar2.add(btnBide);

		JButton btnVasoSanitario = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_VASOSANITARIO, AppDefs.ACTION_ARQ2_VASOSANITARIO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_VASOSANITARIO);
		mnuToolbar2.add(btnVasoSanitario);

		JButton btnVasoCaixaAcoplada = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_VASOCAIXAACLOPADA, AppDefs.ACTION_ARQ2_VASOCAIXAACLOPADA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_VASOCAIXAACLOPADA);
		mnuToolbar2.add(btnVasoCaixaAcoplada);

		//JButton btnMictorio = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_MICTORIO, AppDefs.ACTION_ARQ2_MICTORIO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_MICTORIO);
		//mnuToolbar2.add(btnMictorio);

		JButton btnLavatorioGrande = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_LAVATORIOGRANDE, AppDefs.ACTION_ARQ2_LAVATORIOGRANDE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_LAVATORIOGRANDE);
		mnuToolbar2.add(btnLavatorioGrande);

		//JButton btnLavatorioPequeno = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_LAVATORIOPEQUENO, AppDefs.ACTION_ARQ2_LAVATORIOPEQUENO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_LAVATORIOPEQUENO);
		//mnuToolbar2.add(btnLavatorioPequeno);

		//JButton btnLavatorioBanca = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_LAVATORIOBANCA, AppDefs.ACTION_ARQ2_LAVATORIOBANCA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_LAVATORIOBANCA);
		//mnuToolbar2.add(btnLavatorioBanca);

		//JButton btnChuveiro = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_CHUVEIRO, AppDefs.ACTION_ARQ2_CHUVEIRO, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_CHUVEIRO);
		//mnuToolbar2.add(btnChuveiro);

		//JButton btnPiaSimples = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_PIASIMPLES, AppDefs.ACTION_ARQ2_PIASIMPLES, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_PIASIMPLES);
		//mnuToolbar2.add(btnPiaSimples);

		//JButton btnPiaDupla = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_PIADUPLA, AppDefs.ACTION_ARQ2_PIADUPLA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_PIADUPLA);
		//mnuToolbar2.add(btnPiaDupla);

		//JButton btnFogao4Bocas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_FOGAO4BOCAS, AppDefs.ACTION_ARQ2_FOGAO4BOCAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_FOGAO4BOCAS);
		//mnuToolbar2.add(btnFogao4Bocas);

		//JButton btnFogao6Bocas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_FOGAO6BOCAS, AppDefs.ACTION_ARQ2_FOGAO6BOCAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_FOGAO6BOCAS);
		//mnuToolbar2.add(btnFogao6Bocas);

		//JButton btnGeladeira = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_GELADEIRA, AppDefs.ACTION_ARQ2_GELADEIRA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_GELADEIRA);
		//mnuToolbar2.add(btnGeladeira);

		//JButton btnLavadouraRoupa = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_LAVADOURAROUPA, AppDefs.ACTION_ARQ2_LAVADOURAROUPA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_LAVADOURAROUPA);
		//mnuToolbar2.add(btnLavadouraRoupa);

		//JButton btnTanque = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_TANQUE, AppDefs.ACTION_ARQ2_TANQUE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_TANQUE);
		//mnuToolbar2.add(btnTanque);

		//JButton btnAquecedor = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_AQUECEDOR, AppDefs.ACTION_ARQ2_AQUECEDOR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_AQUECEDOR);
		//mnuToolbar2.add(btnAquecedor);

		//JButton btnBoiler = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_BOILER, AppDefs.ACTION_ARQ2_BOILER, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_BOILER);
		//mnuToolbar2.add(btnBoiler);

		//JButton btnBombaRecalque = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_BOMBARECALQUE, AppDefs.ACTION_ARQ2_BOMBARECALQUE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_BOMBARECALQUE);
		//mnuToolbar2.add(btnBombaRecalque);

		//JButton btnBombaAguasServidas = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_ARQ2_BOMBAAGUASSERVIDAS, AppDefs.ACTION_ARQ2_BOMBAAGUASSERVIDAS, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_ARQ2_BOMBAAGUASSERVIDAS);
		//mnuToolbar2.add(btnBombaAguasServidas);

		iconmnu.add(mnuToolbar2);
	}

	@Override
	public int createToolbarMenu(JPanel iconmnu, ActionListener listener) 
	{
		this.createToolbarMenu1(iconmnu, listener); 
		this.createToolbarMenu2(iconmnu, listener); 

		return AppDefs.RSOK;
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
		//
		this.mnuToolbar1.setVisible(bVisible);
		this.mnuToolbar2.setVisible(bVisible);
	}
	
	/* Getters/Setters */

	public static IModule getAppModule() {
		return ArquiteturaModule.gAppMod;
	}

}
