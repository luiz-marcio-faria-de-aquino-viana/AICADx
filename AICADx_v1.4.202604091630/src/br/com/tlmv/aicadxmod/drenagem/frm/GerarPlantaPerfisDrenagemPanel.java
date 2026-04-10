/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GerarPlantaPerfisDrenagemPanel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/05/2025
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

package br.com.tlmv.aicadxmod.drenagem.frm;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.frm.BaseFrame;
import br.com.tlmv.aicadxapp.frm.BasePanel;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class GerarPlantaPerfisDrenagemPanel extends BasePanel
{
//Private
    private CadEntity[] arrEntity = null;
	
	private ArrayList<CadPerfilDrenagem> lsTrechoDrenagem = null;
	
	private CadPerfilDrenagem oTrechoDrenagem = null;

	private GeomPoint3d ptIns3d = null;

	private int rscode = AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_NONE;
	
	//LABELS
	//
	private JLabel lblListaTrechos = null;

	//CONTROLS
	//
	private JComboBox cbxListTrechos = null;
	
	//BUTTON
	//
	private JButton btnGerarPerfil = null;
	private JButton btnGerarTodosPerfis = null;
	private JButton btnFechar = null;
	private JButton btnZoomTo = null;

	/* Methodes */
	
	private void initGerarPerfil() {
		DrenagemCalc calc = new DrenagemCalc();
		
		MainPanel panel = MainPanel.getMainPanel();
		CadDocumentDef doc = panel.getCurrDocumentDef();

		CadBlockDef blkDef = doc.getCurrBlockDef();

		this.arrEntity = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
		this.lsTrechoDrenagem = calc.findAllTrecho(this.arrEntity, doc);
	}
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		int sz = this.lsTrechoDrenagem.size();
		CadPerfilDrenagem[] arr = this.lsTrechoDrenagem.toArray(new CadPerfilDrenagem[sz]);
		
		Insets insets = this.getInsets();
		
		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_W5;
		
		//FRM_TRECHOS
		//
		this.lblListaTrechos = FormControlUtil.newLabel("Trechos:", xp, yp, AppDefs.LABEL_W100, AppDefs.LABEL_H20, true);
		this.add(this.lblListaTrechos);
		xp += AppDefs.LABEL_W100 + AppDefs.SPACE_W5;
		yp = insets.top + AppDefs.SPACE_W5;
		
		this.cbxListTrechos = FormControlUtil.newComboBox(arr, xp, yp, AppDefs.COMBO_W300, AppDefs.COMBO_H20, true);
		this.add(this.cbxListTrechos);
		xp += AppDefs.COMBO_W300 + AppDefs.SPACE_W5;

		this.btnZoomTo = FormControlUtil.newButton("ZoomTo >", AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_ZOOMTO, xp, yp, AppDefs.BUTTON_W150, AppDefs.TEXT_H20, true, this);
		this.add(this.btnZoomTo);

		//BUTTONS
		//
		xp = insets.left + AppDefs.GERAR_PLANTA_PERFIS_DRENAGEM_FRAME_WIDTH - (AppDefs.BUTTON_W150 + AppDefs.SPACE_W5);
		yp += AppDefs.LABEL_H20 + AppDefs.SPACE_W5;

		this.btnFechar = FormControlUtil.newButton("Fechar", AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_FECHAR, xp, yp, AppDefs.BUTTON_W150, AppDefs.TEXT_H20, true, this);
		this.add(this.btnFechar);
		xp -= (AppDefs.BUTTON_W150 + AppDefs.SPACE_W5);
		
		this.btnGerarTodosPerfis = FormControlUtil.newButton("Todos Perfis", AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARTODOSPERFIS, xp, yp, AppDefs.BUTTON_W150, AppDefs.TEXT_H20, true, this);
		this.add(this.btnGerarTodosPerfis);
		xp -= (AppDefs.BUTTON_W150 + AppDefs.SPACE_W5);
		
		this.btnGerarPerfil = FormControlUtil.newButton("Gerar Perfil", AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARPERFIL, xp, yp, AppDefs.BUTTON_W150, AppDefs.TEXT_H20, true, this);
		this.add(this.btnGerarPerfil);
		xp -= (AppDefs.BUTTON_W150 + AppDefs.SPACE_W5);
	}
	
//Public 
	
	public GerarPlantaPerfisDrenagemPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(GeomPoint3d ptIns3d)
	{
		this.ptIns3d = new GeomPoint3d(ptIns3d);
		
		initGerarPerfil();
		
		initForm();
	}
	
	/* Methodes */
	
	public boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		int pos = this.cbxListTrechos.getSelectedIndex();
		
		if(pos == -1)
			errmsg += "Trechos; ";

		if(errmsg != "") {
			errmsg = "ERR: Campos invalidos: " + errmsg;
			
			AppError.showMessageBox(this.getParentFrame(), "Erro de validacao do formulario", errmsg, this.getClass());
			return false;
		}

		this.oTrechoDrenagem = this.lsTrechoDrenagem.get(pos);
		return true;
	}
    
    /* Event Handlers */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionGerarPerfil(ActionEvent e) 
	{
		if( this.validateForm() ) {
			MainPanel panel = MainPanel.getMainPanel();
			CadDocumentDef doc = panel.getCurrDocumentDef();

			LayerTable layTbl = doc.getLayerTable();
			
			CadLayerDef oLayer = layTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PERFIL_DRENAGEM);
			if(oLayer != null) {
				CadBlockDef blkDef = doc.getCurrBlockDef();
				
				this.oTrechoDrenagem.setLayer(oLayer);
				this.oTrechoDrenagem.setPtIns(this.ptIns3d);
				blkDef.addEntity(this.oTrechoDrenagem);
				
				GeomDimension2d oDim = this.oTrechoDrenagem.getEnvelop2d();

				//GEOMPOINT3D
				//
				GeomPoint2d ptMin2d = new GeomPoint2d(oDim.getPtMin());
				GeomPoint2d ptMax2d = new GeomPoint2d(oDim.getPtMax());
				
				//ZOOM_WINDOW
				//
				ICompView v = panel.getCurrView();
				
				ICadViewBase cv = v.getCadViewBase();
				cv.zoomWindowMcs(ptMin2d, ptMax2d);
				
				rscode = AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARPERFIL;
				this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));
	
				this.getParentFrame().dispose();
			}
		}
	}
	
	public void doActionGerarTodosPerfis(ActionEvent e) 
	{
		//LIMITS
		//
		double ptMinX = Double.MAX_VALUE;
		double ptMinY = Double.MAX_VALUE;
		
		double ptMaxX = - Double.MAX_VALUE;
		double ptMaxY = - Double.MAX_VALUE;

		if( this.validateForm() ) {
			MainPanel panel = MainPanel.getMainPanel();
			CadDocumentDef doc = panel.getCurrDocumentDef();

			LayerTable layTbl = doc.getLayerTable();
			
			CadLayerDef oLayer = layTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PERFIL_DRENAGEM);
			if(oLayer != null) {
				CadBlockDef blkDef = doc.getCurrBlockDef();
				
				GeomPoint3d ptBase3d = new GeomPoint3d(this.ptIns3d);
				
				for(CadPerfilDrenagem oTrechoDrenagem : this.lsTrechoDrenagem) {				
					oTrechoDrenagem.setLayer(oLayer);
					oTrechoDrenagem.setPtIns(this.ptIns3d);
					blkDef.addEntity(oTrechoDrenagem);
					
					double w = oTrechoDrenagem.getW();
					double h = oTrechoDrenagem.getH();
	
					double xNewBase3d = oTrechoDrenagem.getXMin() + w;
					double yNewBase3d = oTrechoDrenagem.getYMin();
									
					ptBase3d = new GeomPoint3d(xNewBase3d, yNewBase3d, 0.0);
					
					GeomDimension2d oDim = oTrechoDrenagem.getEnvelop2d();
					
					GeomPoint2d ptMin2d = new GeomPoint2d(oDim.getPtMin());
					GeomPoint2d ptMax2d = new GeomPoint2d(oDim.getPtMax());

					if(ptMin2d.getX() < ptMinX)
						ptMinX = ptMin2d.getX(); 
					if(ptMin2d.getY() < ptMinY)
						ptMinY = ptMin2d.getY(); 

					if(ptMax2d.getX() > ptMaxX)
						ptMaxX = ptMax2d.getX(); 
					if(ptMax2d.getY() > ptMaxY)
						ptMaxY = ptMax2d.getY(); 
				}

				//GEOMPOINT3D
				//
				GeomPoint2d ptMin2d = new GeomPoint2d(ptMinX, ptMinY);
				GeomPoint2d ptMax2d = new GeomPoint2d(ptMaxX, ptMaxY);
				
				//ZOOM_WINDOW
				//
				ICompView v = panel.getCurrView();
				
				ICadViewBase cv = v.getCadViewBase();
				cv.zoomWindowMcs(ptMin2d, ptMax2d);
				
				rscode = AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARTODOSPERFIS;
				this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));
	
				this.getParentFrame().dispose();
			}
		}
	}
	
	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_FECHAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
	public void doActionZoomTo(ActionEvent e) 
	{
		MainPanel panel = MainPanel.getMainPanel();
		CadDocumentDef doc = panel.getCurrDocumentDef();

		CadBlockDef blkDef = doc.getCurrBlockDef();		
		blkDef.setSelectedAllEntities(false);
		
		if( this.validateForm() ) {
			GeomDimension2d oDim = this.oTrechoDrenagem.getItensEnvelop(true);
					
			//GEOMPOINT3D
			//
			GeomPoint2d ptMin2d = new GeomPoint2d(oDim.getPtMin());
			GeomPoint2d ptMax2d = new GeomPoint2d(oDim.getPtMax());
			
			//ZOOM_WINDOW
			//
			ICompView v = panel.getCurrView();
			
			ICadViewBase cv = v.getCadViewBase();
			cv.zoomWindowMcs(ptMin2d, ptMax2d);

			v.repaintAll();
		}
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int action = Integer.parseInt(e.getActionCommand());
		
		if(action == AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARPERFIL) {
			doActionGerarPerfil(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_GERARTODOSPERFIS) {
			doActionGerarTodosPerfis(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_FECHAR) {
			doActionFechar(e);
		}
		else if(action == AppDefs.RSCODE_GERAR_PLANTA_PERFIL_DRENAGEM_ZOOMTO) {
			doActionZoomTo(e);
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
