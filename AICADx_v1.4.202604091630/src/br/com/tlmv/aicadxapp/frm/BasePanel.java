/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
	
	protected R r = null;

//Public
	
	public BasePanel(BaseFrame parentFrame)
	{
		this.parentFrame = parentFrame;
		this.panel = this;
		
		this.cad = AppCadMain.getCad();
				
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

	public R getR() {
		return r;
	}

}
