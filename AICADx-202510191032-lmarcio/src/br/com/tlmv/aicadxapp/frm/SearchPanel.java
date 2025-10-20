/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SearchPanel.java
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

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.TextEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.model.BasicTableModel;
import br.com.tlmv.aicadxapp.frm.model.ResultListModel;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class SearchPanel extends BasePanel
{
//Private
	private ArrayList<ItemDataVO> lsResultList = null;
	
	private ItemDataVO currResultListItem = null;
	
	private ResultListModel model = null;

	int iObjectType = AppDefs.OBJTYPE_NONE;
	String strObjectType = AppDefs.ARR_OBJTYPE_STR[0];
	String strSearchBy = "";
	
	private int rscode = AppDefs.RSCODE_SEARCH_NONE;
	
	//FORM_CONTROL
	//
	private JLabel lblObjectType = null;
	private JLabel lblSearchBy = null;
	private JLabel lblResultList = null;
	//
	private JComboBox cbxObjectType = null;
	private JTextField txtSearchBy = null;
	private JList lstResultList = null;
	//
	private JButton btnSearch = null;
	private JButton btnZoomToItem = null;
	private JButton btnZoomToAll = null;
	private JButton btnFechar = null;
	
	/* Methodes */
	
	private ArrayList<ItemDataVO> executeSearch(int objType, String searchBy)
	{
		AppCadMain cad = AppCadMain.getCad();
		
		CadBlockDef blkDef = cad.getCurrBlockDef();
		
		ArrayList<CadEntity> lsEntities = blkDef.findAllEntityByObjType(objType, searchBy);
		ArrayList<ItemDataVO> lsResultList = new ArrayList<ItemDataVO>();
		for(CadEntity oEnt : lsEntities) {
			ItemDataVO oItemData = new ItemDataVO(Integer.toString(oEnt.getObjectId()), oEnt.toString(), oEnt.getEnvelop2d());
			lsResultList.add(oItemData);
		}
		return lsResultList;
	}
	
	private void updateResultList(ArrayList<ItemDataVO> lsResultList)
	{
		this.lsResultList.clear();
		
		for(ItemDataVO oResultListItem : lsResultList) {
			this.lsResultList.add(oResultListItem);
		}
		
		this.model = new ResultListModel(this.lsResultList);
		this.lstResultList.setModel(this.model);
	}
	
	private boolean validateForm()
	{
		String errmsg = "";

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		int pos = this.cbxObjectType.getSelectedIndex();
		if(pos == -1) {
			errmsg += "Tipo; ";
		}

		this.strObjectType = AppDefs.ARR_OBJTYPE_STR[pos];
		this.strSearchBy = this.txtSearchBy.getText();
		//if( "".equals(strSearchBy) )
		//	errmsg += "Pesquisar por; ";
		
		if(errmsg != "") {
			AppError.showErrorBox(this.getParentFrame(), "ERR: Campos obrigatorios nao informados", errmsg, this.getClass());
			return false;
		}
		
		return true;
	}

	private void initForm()
	{
		this.setLayout(null);
				
		this.model = new ResultListModel(this.lsResultList);
		
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_H5;
		
		/* Label */

		String strObjectType = r.getString(R.LBL_SEARCH_OBJECTTYPE); 
		this.lblObjectType = FormControlUtil.newLabel(strObjectType, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblObjectType);
		yp = yp + (AppDefs.LABEL_H20 + AppDefs.SPACE_H5);		

		String strSearchBy = r.getString(R.LBL_SEARCH_SEARCHBY); 
		this.lblSearchBy = FormControlUtil.newLabel(strSearchBy, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblSearchBy);
		yp = yp + (AppDefs.LABEL_H20 + AppDefs.SPACE_H5);		

		//SEARCH_BUTTON
		yp = yp + (AppDefs.LABEL_H20 + AppDefs.SPACE_H5);		

		String strResultList = r.getString(R.LBL_SEARCH_RESULTLIST); 
		this.lblResultList = FormControlUtil.newLabel(strResultList, xp, yp, AppDefs.LABEL_W150, AppDefs.LABEL_H20, true);
		this.add(this.lblResultList);
		
		/* Text */

		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W150 + AppDefs.SPACE_W5);
		yp = insets.top + AppDefs.SPACE_H5;

		this.cbxObjectType = FormControlUtil.newComboBox(AppDefs.ARR_OBJTYPE_STR, xp, yp, AppDefs.COMBO_W350, AppDefs.COMBO_H20, true);
		this.add(this.cbxObjectType);
		yp = yp + (AppDefs.COMBO_H20 + AppDefs.SPACE_H5);
							
		this.txtSearchBy = FormControlUtil.newTextField("", xp, yp, AppDefs.TEXT_W350, AppDefs.TEXT_H20, true, true);
		this.add(this.txtSearchBy);		
		xp = insets.left + AppDefs.SPACE_W5;
		yp = yp + (AppDefs.TEXT_H20 + AppDefs.SPACE_H5);
		
		String strSearch = r.getString(R.BTN_SEARCH); 
		this.btnSearch = FormControlUtil.newButton(strSearch, AppDefs.RSCODE_SEARCH_EXECUTESEARCH, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);
		this.add(this.btnSearch);
		yp = yp + (AppDefs.BUTTON_H20 + AppDefs.SPACE_H5);

		//RESULTLIST_TEXT
		yp = yp + (AppDefs.BUTTON_H20 + AppDefs.SPACE_H5);
		
		this.lstResultList = FormControlUtil.newList(this, model, xp, yp, AppDefs.LIST_W600, AppDefs.LIST_H400, true);
		this.add(this.lstResultList);
		yp = yp + (AppDefs.LIST_H400 + AppDefs.SPACE_H5);
		
		/* Button */
		
		xp = insets.left + AppDefs.SPACE_W5;
		
		String strZoomToItem = r.getString(R.BTN_ZOOMTOITEM); 
		this.btnZoomToItem = FormControlUtil.newButton(strZoomToItem, AppDefs.RSCODE_SEARCH_ZOOMTOITEM, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);
		this.add(btnZoomToItem);
		xp += (AppDefs.BUTTON_W150 + AppDefs.SPACE_W30);
		
		String strZoomToAll = r.getString(R.BTN_ZOOMTOALL); 
		this.btnZoomToAll = FormControlUtil.newButton(strZoomToAll, AppDefs.RSCODE_SEARCH_ZOOMTOALL, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);
		this.add(btnZoomToAll);
		xp += (AppDefs.BUTTON_W150 + AppDefs.SPACE_W30);
		
		String strFechar = r.getString(R.BTN_FECHAR); 
		this.btnFechar = FormControlUtil.newButton(strFechar, AppDefs.RSCODE_SEARCH_FECHAR, xp, yp, AppDefs.BUTTON_W150, AppDefs.BUTTON_H20, true, this);
		this.add(btnFechar);		
	}
	
//Public 
	
	public SearchPanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init()
	{
		this.lsResultList = new ArrayList<ItemDataVO>();
		
		initForm();
	}
	
	/* Methodes */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_SEARCH_FECHAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}

	public void doActionExecuteSearch(ActionEvent e) 
	{
		if( this.validateForm() ) {
			ArrayList<ItemDataVO> lsResultList = this.executeSearch(this.iObjectType, this.getStrSearchBy());
			this.updateResultList(lsResultList);
		}
	}
	
	public void doActionZoomToItem(ActionEvent e) 
	{
		int sz = this.lsResultList.size();
		if(sz == 0) return;
		
		int pos = this.lstResultList.getSelectedIndex();
		if(pos == -1) return;
		
		ItemDataVO oItemData = this.lsResultList.get(pos);
		if(oItemData != null) {
			ArrayList<ItemDataVO> ls = new ArrayList<ItemDataVO>();
			ls.add(oItemData);

			GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(ls);
			if(oGeomDim != null) {
				rscode = AppDefs.RSCODE_SEARCH_ZOOMTOITEM;
				this.getParentFrame().actionResultListener(new ResultEvent(rscode, oGeomDim));
			
				//this.getParentFrame().dispose();
			}
		}
	}
	
	public void doActionZoomToAll(ActionEvent e) 
	{
		int sz = this.lsResultList.size();
		if(sz == 0) return;
		
		GeomDimension2d oGeomDim = GeomUtil.getEnvelop2d(this.lsResultList);
		if(oGeomDim != null) {
			rscode = AppDefs.RSCODE_SEARCH_ZOOMTOALL;
			this.getParentFrame().actionResultListener(new ResultEvent(rscode, oGeomDim));
		
			//this.getParentFrame().dispose();
		}
	}
	
	/* ACTION_EVENT */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int cmdAction = StringUtil.safeInt(e.getActionCommand());
		
		if(cmdAction == AppDefs.RSCODE_SEARCH_FECHAR) {
			doActionFechar(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SEARCH_EXECUTESEARCH) {
			doActionExecuteSearch(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SEARCH_ZOOMTOITEM) {
			doActionZoomToItem(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_SEARCH_ZOOMTOALL) {
			doActionZoomToAll(e);						
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

	public ArrayList<ItemDataVO> getLsResultList() {
		return lsResultList;
	}

	public void setLsResultList(ArrayList<ItemDataVO> lsResultList) {
		this.lsResultList = lsResultList;
	}

	public ItemDataVO getCurrResultListItem() {
		return currResultListItem;
	}

	public void setCurrResultListItem(ItemDataVO currResultListItem) {
		this.currResultListItem = currResultListItem;
	}

	public int getObjectType() {
		return iObjectType;
	}

	public void setObjectType(int iObjectType) {
		this.iObjectType = iObjectType;
	}

	public String getStrObjectType() {
		return strObjectType;
	}

	public void setStrObjectType(String strObjectType) {
		this.strObjectType = strObjectType;
	}

	public String getStrSearchBy() {
		return strSearchBy;
	}

	public void setStrSearchBy(String strSearchBy) {
		this.strSearchBy = strSearchBy;
	}
	
}
