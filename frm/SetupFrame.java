/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SetupFrame.java
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class SetupFrame extends JFrame implements WindowListener, ResultListener
{
//Private
	private static JFrame gParent = null;
	
	private SetupPanel panel = null;

	private ResultListener resultListener = null;
	
	private CadDocumentDef doc = null;

	/* Methodes */
	
	private void createMainPanel()
	{
		CadProjectDef oCurrProject = doc.getCurrProjectDef();
		
		this.panel = new SetupPanel();
		this.panel.init(this, this.doc, oCurrProject);
		
		Container c = getContentPane();
		c.add(this.panel);
	}
	
//Public
	
	public SetupFrame(JFrame parent)
	{
		gParent = parent;
	}

	public void init(
		CadDocumentDef doc,
		ResultListener resultListener)
	{
		this.resultListener = resultListener;
		
		this.doc = doc;
		
		String title = "Project Definition";
		setTitle(title);
		setSize(AppDefs.SETUP_FRAME_WIDTH, AppDefs.SETUP_FRAME_HEIGHT);
		setLocation(AppDefs.DEFAULT_XPOS, AppDefs.DEFAULT_YPOS);
		setResizable(false);

		FormControlUtil.loadIcon(this);
		
		addWindowListener(this);

		this.createMainPanel();
	}
	
	public void resizePanel(int x_parent, int y_parent, int w_parent, int h_parent)
	{
		int x = (int)( ((double)w_parent / 2.0) - ((double)AppDefs.SETUP_FRAME_WIDTH / 2.0) );
		int y = (int)( ((double)h_parent / 2.0) - ((double)AppDefs.SETUP_FRAME_HEIGHT / 2.0) );
		
		setSize(AppDefs.SETUP_FRAME_WIDTH, AppDefs.SETUP_FRAME_HEIGHT);
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

	public static JFrame getMainParent() {
		return gParent;
	}

	public static void setMainParent(JFrame gParent) {
		SetupFrame.gParent = gParent;
	}

	public SetupPanel getPanel() {
		return panel;
	}

	public void setPanel(SetupPanel panel) {
		this.panel = panel;
	}
	
	public ResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(ResultListener resultListener) {
		this.resultListener = resultListener;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public void setDoc(CadDocumentDef doc) {
		this.doc = doc;
	}
	
}
