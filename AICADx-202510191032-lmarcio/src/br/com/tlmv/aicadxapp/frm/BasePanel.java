/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BasePanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 06/07/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public abstract class BasePanel extends JPanel implements ActionListener, ItemListener, ListSelectionListener, ResultListener, LayerTableCellResultListener, TextListener, AdjustmentListener, ComponentListener, ChangeListener
{
//Private
	protected BaseFrame parentFrame = null;
	protected BasePanel panel = null;
	
	protected AppCadMain cad = null;
	
	protected CadDocumentDef doc = null;
	
	protected R r = null;

//Public
	
	public BasePanel(BaseFrame parentFrame)
	{
		this.parentFrame = parentFrame;
		this.panel = this;
		
		this.cad = AppCadMain.getCad();
				
		this.doc = this.cad.getCurrDocumentDef();

		this.r = AppMain.getResource();
	}

	/* ACTION_EVENTS */

	@Override
	public abstract void valueChanged(ListSelectionEvent e);
	
	@Override
	public abstract void actionPerformed(ActionEvent e); 

	@Override
	public abstract void itemStateChanged(ItemEvent e);
	
	@Override
	public abstract void actionResultListener(ResultEvent e);

	@Override
	public abstract void actionLayerTableCellResultListener(LayerTableCellResultEvent e);

	@Override
	public abstract void adjustmentValueChanged(AdjustmentEvent e);

	@Override
	public abstract void textValueChanged(TextEvent e);
	
	/* COMPONENT_EVENTS */
	
	@Override
	public abstract void componentResized(ComponentEvent e);

	@Override
	public abstract void componentMoved(ComponentEvent e);

	@Override
	public abstract void componentShown(ComponentEvent e);

	@Override
	public abstract void componentHidden(ComponentEvent e);
	
	/* CHANGE_EVENTS */
	
	@Override
	public abstract void stateChanged(ChangeEvent e);

	/* Getters/Setters */

	public BaseFrame getParentFrame() {
		return parentFrame;
	}

	public BasePanel getPanel() {
		return panel;
	}

	public AppCadMain getCad() {
		return cad;
	}

	public CadDocumentDef getDoc() {
		return doc;
	}

	public R getR() {
		return r;
	}

}
