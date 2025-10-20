/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.frm;

import java.awt.Container;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
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
