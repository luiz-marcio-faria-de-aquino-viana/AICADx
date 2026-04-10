/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * LayerExplorerFrame.java
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

import java.awt.Container;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;

public class LayerExplorerFrame extends BaseFrame
{
//Private
	private LayerExplorerPanel layerPanel = null;

	private ArrayList<CadLayerDef> lsLayer = null;
	
	private CadLayerDef oDefaultLayer = null;

	private CadLayerDef oCurrLayer = null;

	private ResultListener resultListener = null;

//Public
	
	public LayerExplorerFrame(BaseFrame parentFrame)
	{
		super(parentFrame, R.TIT_LAYEREXPLORERFRAME, AppDefs.DEFAULT_FRAME_POSX, AppDefs.DEFAULT_FRAME_POSY, AppDefs.LAYEREXPLORER_FRAME_WIDTH, AppDefs.LAYEREXPLORER_FRAME_HEIGHT);
	}
	
	/* Methodes */

	public void init()
	{
		MainPanel panel = MainPanel.getMainPanel();
		CadDocumentDef doc = panel.getCurrDocumentDef();
		
		LayerTable oLayerTbl = doc.getLayerTable();
		this.lsLayer = oLayerTbl.getAllLayers();
		
		this.oDefaultLayer = doc.getDefaultLayerDef();
		this.oCurrLayer = doc.getCurrLayerDef();

		super.init();
	}

	public void init(ResultListener resultListener)
	{
		this.resultListener = resultListener;
		
		this.init();
	}

	/* Abstract */
	
	@Override
	public void createMainPanel()
	{
		this.layerPanel = new LayerExplorerPanel(this);
		this.layerPanel.init(this, this.lsLayer, oDefaultLayer, oCurrLayer, this);
		
		Container c = getContentPane();
		c.add(this.layerPanel);
		this.show();
	}

	@Override
	public void actionResultListener(ResultEvent e) 
	{
		if(resultListener != null)
			resultListener.actionResultListener(e);
	}

}
