/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * SplashScreenPanel.java
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class SplashScreenPanel extends BasePanel
{
//Private
	private int rscode = AppDefs.RSCODE_SPLASHSCREEN_NONE;
	
	//FORM_CONTROL
	//
	private Image imgSplashScreen = null;
	private JButton btnSplashScreen = null;
	
	private void initForm()
	{
		int n = 0;
		while( this.imgSplashScreen == null ) {
			if(n > 10) break;

			this.imgSplashScreen = FormControlUtil.loadAnyIcon(this.getParentFrame(), AppDefs.APP_SPLASHSCREEN);
			
			try {
				Thread.sleep(10);
			}
			catch(Exception e) { }
		}
	}
	
	//LOADED_MODULES
	//
	private void showLoadedModules(Graphics g, int xp, int yp) {
		String strLoadedModules = "";
		
		if( AppDefs.MNU_ENABLED_ARQMENU ) {
			strLoadedModules += "Arquitetura; ";			
		}

		if( AppDefs.MNU_ENABLED_FORMENU ) {
			strLoadedModules += "Formas; ";			
		}

		if( AppDefs.MNU_ENABLED_FUMENU ) {
			strLoadedModules += "Furacao; ";			
		}

		if( AppDefs.MNU_ENABLED_EEMENU ) {
			strLoadedModules += "Entrada Energia; ";			
		}

		if( AppDefs.MNU_ENABLED_ELMENU ) {
			strLoadedModules += "Eletrica; ";			
		}

		if( AppDefs.MNU_ENABLED_ESMENU ) {
			strLoadedModules += "Esgoto; ";			
		}

		if( AppDefs.MNU_ENABLED_APMENU ) {
			strLoadedModules += "Aguas Pluviais; ";			
		}

		if( AppDefs.MNU_ENABLED_RPDMENU ) {
			strLoadedModules += "Redes Publicas Drenagem; ";			
		}

		if( AppDefs.MNU_ENABLED_HMENU ) {
			strLoadedModules += "Hidraulica; ";			
		}

		if( AppDefs.MNU_ENABLED_INCMENU ) {
			strLoadedModules += "Incendio; ";			
		}

		if( AppDefs.MNU_ENABLED_GMENU ) {
			strLoadedModules += "Gas; ";			
		}

		if( AppDefs.MNU_ENABLED_IEMENU ) {
			strLoadedModules += "Instalacoes Especiais; ";			
		}

		if( AppDefs.MNU_ENABLED_TEMENU ) {
			strLoadedModules += "Telecomunicacoes; ";			
		}

		if( AppDefs.MNU_ENABLED_ARMENU ) {
			strLoadedModules += "Ar Condicionado; ";			
		}

		if( AppDefs.MNU_ENABLED_TMARMENU ) {
			strLoadedModules += "Transporte Maritimo; ";			
		}

		if( AppDefs.MNU_ENABLED_TOPOMENU ) {
			strLoadedModules += "Topografia; ";			
		}

		if( !"".equals(strLoadedModules) ) {
			String str = String.format(this.getR().getString( R.TXT_SHOW_ALL_LOADED_MODULES ), strLoadedModules);
			g.drawString(str , xp, yp);
		}
	}
	
	//ENABLED_FEATURES
	//
	private void showEnabledFeatures(Graphics g, int xp, int yp) {
		String strEnabledFeatures = "";
		
		if( AppDefs.DEBUG_LEVEL > AppDefs.DEBUG_LEVEL00 ) {
			strEnabledFeatures += String.format("DEBUG-%s; ", AppDefs.DEBUG_LEVEL);
		}
		
		if( AppDefs.SMP_USE_THREADS ) {
			strEnabledFeatures += String.format("SMP-%s; ", AppDefs.SMP_MAX_NUM_THREADS);
		}
		
		if( AppDefs.ENABLE_UNDO_REDO ) {
			strEnabledFeatures += "Undo/Redo; ";
		}
		
		if( !"".equals(strEnabledFeatures) ) {
			String str = String.format(this.getR().getString( R.TXT_SHOW_ALL_ENABLED_FEATURES ), strEnabledFeatures);
			g.drawString(str , xp, yp);
		}
	}
	
//Public 
	
	public SplashScreenPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init()
	{
		initForm();
	}
	
	/* Methodes */
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	@Override
	public void paint(Graphics g) {	
		super.paint(g);

		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, AppDefs.SPLASHSCREEN_FRAME_WIDTH, AppDefs.SPLASHSCREEN_FRAME_HEIGHT);

		g.drawImage(this.imgSplashScreen, 10, 10, null);
		
		int xp = 10;
		int yp = 567;
		
		g.setColor(Color.BLACK);
		
		Font f = g.getFont();
		g.setFont( f.deriveFont(Font.BOLD, 10) );
		
		String strAppNomeVersao = String.format("%s %s", AppDefs.APP_NAME, AppDefs.APP_VERSAO);		

		g.drawString(AppDefs.APP_COPYRIGHT, xp, yp);
		yp += 15;
		
		g.drawString(strAppNomeVersao, xp, yp);		
		yp += 15;
		
		g.drawString(AppDefs.APP_AUTHOR_NAME, xp, yp);
		yp += 15;
		
		g.drawString(AppDefs.APP_AUTHOR_REGISTRO, xp, yp);
		yp += 15;
		
		g.drawString(AppDefs.APP_AUTHOR_EMAIL, xp, yp);
		yp += 15;

		g.drawString(AppDefs.APP_AUTHOR_TELEFONE, xp, yp);
		yp += 15;
		
		this.showLoadedModules(g, xp, yp);
		yp += 15;
		
		this.showEnabledFeatures(g, xp, yp);
		yp += 15;
	}

	/* Actions */

	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_MESSAGE_FECHAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));
	
		this.getParentFrame().dispose();
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmdAction = StringUtil.safeInt(e.getActionCommand());
		
		if(cmdAction == AppDefs.RSCODE_MESSAGE_FECHAR) {
			doActionFechar(e);						
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) { }

	@Override
	public void itemStateChanged(ItemEvent e) { }

	@Override
	public void actionResultListener(ResultEvent e) { }

	@Override
	public void actionLayerTableCellResultListener(LayerTableCellResultEvent e) { }

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) { }

	@Override
	public void textValueChanged(TextEvent e) { }

	/* COMPONENT_EVENT */
	
	@Override
	public void componentResized(ComponentEvent e) { }

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }

	/* CHANGE_EVENTS */

	@Override
	public void stateChanged(ChangeEvent e) { }

	/* Getters/Setters */
	
	public int getRSCode() {
		return rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}
	
}
