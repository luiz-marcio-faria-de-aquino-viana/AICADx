/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.frm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;
import sun.font.Font2D;

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
