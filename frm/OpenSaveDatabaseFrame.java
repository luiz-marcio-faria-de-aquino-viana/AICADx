/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * OpenDatabaseFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class OpenSaveDatabaseFrame extends JFrame implements WindowListener, ResultListener
{
//Private
	JComponent gParent = null;
	
	private ResultListener resultListener = null;
	
	private String strActiveDatabase = "";
	private boolean bActiveDatabaseEditable = false;
	
//Public
	
	public OpenSaveDatabaseFrame(JComponent parent)
	{
		gParent = parent;
	}

	public void init(
		boolean bBtnNew,
		boolean bBtnSave, 
		boolean bBtnSaveAs,
		boolean bBtnOpen,
		boolean bBtnCopy,
		boolean bBtnRename,
		boolean bBtnDrop,
		String strActiveDatabase,
		boolean bActiveDatabaseEditable,
		ResultListener resultListener)
	{
		this.strActiveDatabase = strActiveDatabase;
		this.bActiveDatabaseEditable = bActiveDatabaseEditable;
		this.resultListener = resultListener;
				
		String title = "Open Database";		
		setTitle(title);

		FormControlUtil.loadIcon(this);
		
		Point pos = gParent.getLocation();
		Dimension dim = gParent.getSize();
		
		OpenSaveDatabasePanel openDatabasePanel = new OpenSaveDatabasePanel();
		openDatabasePanel.init(
				this, 
				bBtnNew, 
				bBtnSave, 
				bBtnSaveAs, 
				bBtnOpen, 
				bBtnCopy, 
				bBtnRename,
				bBtnDrop,
				this.strActiveDatabase,
				this.bActiveDatabaseEditable,
				resultListener);
		
		resizePanel(
			pos.x,
			pos.y,
			dim.width,
			dim.height);

		Container c = getContentPane();
		c.add(openDatabasePanel);
		
		this.show();
	}
	
	public void resizePanel(int x_parent, int y_parent, int w_parent, int h_parent)
	{
		int x = (int)( ((double)w_parent / 2.0) - ((double)AppDefs.OPENSAVE_DATABASE_FRAME_WIDTH / 2.0) );
		int y = (int)( ((double)h_parent / 2.0) - ((double)AppDefs.OPENSAVE_DATABASE_FRAME_HEIGHT / 2.0) );
		
		setSize(AppDefs.OPENSAVE_DATABASE_FRAME_WIDTH, AppDefs.OPENSAVE_DATABASE_FRAME_HEIGHT);
		setLocation(x, y);
		addWindowListener(this);
	}
	
	/* Events */
	
	@Override
	public void windowClosing(WindowEvent e) 
	{
		this.dispose();
	}

	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowClosed(WindowEvent e) { }

	@Override
	public void windowDeactivated(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowOpened(WindowEvent e) { }

	/* Listeners */
	
	@Override
	public void actionResultListener(ResultEvent e) 
	{
		if(resultListener != null)
			resultListener.actionResultListener(e);
	}
	
	/* Getters/Setters */
	
	public ResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(ResultListener resultListener) {
		this.resultListener = resultListener;
	}
	
}
