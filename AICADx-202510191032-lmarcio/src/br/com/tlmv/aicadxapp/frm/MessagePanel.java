/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainPanel.java
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.renderer.LayerTableCellResultEvent;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class MessagePanel extends BasePanel
{
//Private
	private MessageDataVO oMessageData = null;
	
	private int rscode = AppDefs.RSCODE_MESSAGE_NONE;
	
	//FORM_CONTROL
	//
	private JLabel lblEventDate = null;
	private JLabel lblEventSubject = null;
	private JLabel lblEventMessage = null;

	private JTextField txtEventDate = null;
	private JTextField txtEventSubject = null;
	private JTextArea txtEventMessage = null;

	private JButton btnFechar = null;

	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);

		String strDtEventDate = df.format(this.oMessageData.getEventDate());
				
		Insets insets = this.getInsets();

		int xp = insets.left + AppDefs.SPACE_W5;
		int yp = insets.top + AppDefs.SPACE_H5;
		
		/* Label */

		String strEventDate = r.getString(R.LBL_DATA); 
		this.lblEventDate = FormControlUtil.newLabel(strEventDate, xp, yp, AppDefs.LABEL_W100, AppDefs.LABEL_H20, true);
		this.add(this.lblEventDate);
		yp = yp + (AppDefs.LABEL_H20 + AppDefs.SPACE_H5);		

		String strEventSubject = r.getString(R.LBL_ASSUNTO); 
		this.lblEventSubject = FormControlUtil.newLabel(strEventSubject, xp, yp, AppDefs.LABEL_W100, AppDefs.LABEL_H20, true);
		this.add(this.lblEventSubject);
		yp = yp + (AppDefs.LABEL_H20 + AppDefs.SPACE_H5);		

		String strEventMessage = r.getString(R.LBL_MENSAGEM); 
		this.lblEventMessage = FormControlUtil.newLabel(strEventMessage, xp, yp, AppDefs.LABEL_W100, AppDefs.LABEL_H20, true);
		this.add(this.lblEventMessage);
		
		/* Text */

		xp = insets.left + (AppDefs.SPACE_W5 + AppDefs.LABEL_W100 + AppDefs.SPACE_W5);
		yp = insets.top + AppDefs.SPACE_H5;

		this.txtEventDate = FormControlUtil.newTextField(strDtEventDate, xp, yp, AppDefs.TEXT_W500, AppDefs.TEXT_H20, true, false);
		this.add(this.txtEventDate);
		yp = yp + (AppDefs.TEXT_H20 + AppDefs.SPACE_H5);
							
		this.txtEventSubject = FormControlUtil.newTextField(this.oMessageData.getEventSubject(), xp, yp, AppDefs.TEXT_W500, AppDefs.TEXT_H20, true, false);
		this.add(this.txtEventSubject);
		yp = yp + (AppDefs.TEXT_H20 + AppDefs.SPACE_H5);
		
		this.txtEventMessage = FormControlUtil.newTextArea(this.oMessageData.getEventMessage(), xp, yp, AppDefs.TEXT_W500, AppDefs.TEXT_H180, true, false);
		this.add(this.txtEventMessage);
		yp = yp + (AppDefs.TEXT_H180 + AppDefs.SPACE_H5);
		
		/* Button */
		
		xp = insets.left + AppDefs.MESSAGE_FRAME_WIDTH - (AppDefs.BUTTON_W100 + AppDefs.SPACE_W30);
		
		String strFechar = r.getString(R.BTN_FECHAR); 
		this.btnFechar = FormControlUtil.newButton(strFechar, AppDefs.RSCODE_MESSAGE_FECHAR, xp, yp, AppDefs.BUTTON_W100, AppDefs.BUTTON_H20, true, this);
		this.add(btnFechar);
		
	}
	
//Public 
	
	public MessagePanel(BaseFrame parentFrame)
	{
		super(parentFrame);
	}
	
	public void init(MessageDataVO messageData)
	{
		this.oMessageData = messageData;
		
		initForm();
	}
	
	/* Methodes */
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	/* Actions */

	public void doActionOk(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_MESSAGE_OK;			
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));
			
		this.getParentFrame().dispose();
	}
		
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_MESSAGE_CANCELAR;
		this.getParentFrame().actionResultListener(new ResultEvent(rscode, null));

		this.getParentFrame().dispose();
	}
	
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
		
		if(cmdAction == AppDefs.RSCODE_MESSAGE_OK) {
			doActionOk(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_MESSAGE_CANCELAR) {
			doActionCancelar(e);						
		}
		else if(cmdAction == AppDefs.RSCODE_MESSAGE_FECHAR) {
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

	public MessageDataVO getMessageData() {
		return oMessageData;
	}

	public void setMessageData(MessageDataVO oMessageData) {
		this.oMessageData = oMessageData;
	}
	
}
