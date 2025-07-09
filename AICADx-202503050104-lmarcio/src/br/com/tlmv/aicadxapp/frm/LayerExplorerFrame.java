/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * LayerExplorerFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
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
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class LayerExplorerFrame extends JFrame implements WindowListener, ResultListener
{
//Private
	JFrame parent = null;

	private LayerExplorerPanel layerPanel = null;
	
	private ResultListener resultListener = null;
	
	private CadDocumentDef doc = null;

	private ArrayList<CadLayerDef> lsLayer = null;
	
	private CadLayerDef oDefaultLayer = null;

	private CadLayerDef oCurrLayer = null;

	/* Methodes */
	
	private void createMainPanel()
	{
		this.layerPanel = new LayerExplorerPanel();
		this.layerPanel.init(this, this.lsLayer, oDefaultLayer, oCurrLayer, this);
		
		Container c = getContentPane();
		c.add(this.layerPanel);
	}
		
//Public
	
	public LayerExplorerFrame(JFrame parent)
	{
		this.parent = parent;
	}

	public void init(
		CadDocumentDef doc,
		ResultListener resultListener)
	{
		this.resultListener = resultListener;
				
		this.doc = doc;
		
		LayerTable oLayerTbl = doc.getLayerTable();
		this.lsLayer = oLayerTbl.getAllLayers();
		
		this.oDefaultLayer = doc.getDefaultLayerDef();
		this.oCurrLayer = doc.getCurrLayerDef();
				
		String title = "Layer Explorer";
		setTitle(title);
		setSize(AppDefs.LAYEREXPLORER_WIDTH, AppDefs.LAYEREXPLORER_HEIGHT);
		setLocation(AppDefs.LAYEREXPLORER_XPOS, AppDefs.LAYEREXPLORER_XPOS);
		setResizable(false);

		FormControlUtil.loadIcon(this);
		
		addWindowListener(this);

		this.createMainPanel();
	}
	
	public void resizePanel(int x_parent, int y_parent, int w_parent, int h_parent)
	{
		int x = (int)((double)w_parent / 2.0) - 320;
		int y = (int)((double)h_parent / 2.0) - 155;
		
		setSize(640, 310);
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
