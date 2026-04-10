/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TopografiaModule.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 01/11/2025
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

public class TopografiaModule implements IModule 
{
//Private Static
	private static IModule gAppMod = null;

//Private
	private AppMain app = null;
	private AppCadMain cad = null;

	private JMenu mnu = null;

	private JPanel mnuToolbar = null;		

//Public
	
	public TopografiaModule(AppMain app, AppCadMain cad)
	{
		TopografiaModule.gAppMod = this;
		
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
			
			//MENU: TOPOGRAFIA_COMMANDS
			//
			this.mnu = new JMenu(AppDefs.MNU_TOPO1);
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOPO1_INSERE_TOPOPOINT,
				AppDefs.ACTION_TOPO1_INSERE_TOPOPOINT,
				listener) );
			
			this.mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_TOPO1_INSERE_MULT_TOPOPOINT,
				AppDefs.ACTION_TOPO1_INSERE_MULT_TOPOPOINT,
				listener) );

			this.mnu.add(new JSeparator());
			
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
			
			//MENU: TOPOGRAFIA
			//
			JButton btnInsereTopoPoint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_TOPO1_INSERE_TOPOPOINT, AppDefs.ACTION_TOPO1_INSERE_TOPOPOINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_TOPO1_INSERE_TOPOPOINT);
			this.mnuToolbar.add(btnInsereTopoPoint);

			JButton btnInsereTopoPointMult = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_TOPO1_INSERE_MULT_TOPOPOINT, AppDefs.ACTION_TOPO1_INSERE_MULT_TOPOPOINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_TOPO1_INSERE_MULT_TOPOPOINT);
			this.mnuToolbar.add(btnInsereTopoPointMult);

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
		return TopografiaModule.gAppMod;
	}

}
