/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MainPanel.java
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

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class MessagePanel extends JPanel implements ActionListener
{
//Private
	private static MessageFrame gParentFrame = null;
	private static MessagePanel gPanel = null;

	private JLabel lblEventDate = null;
	private JLabel lblEventSubject = null;
	private JLabel lblEventMessage = null;

	private JTextField txtEventDate = null;
	private JTextField txtEventSubject = null;
	private JTextArea txtEventMessage = null;

	private JButton btnFechar = null;

	private int rscode = AppDefs.RSCODE_MESSAGE_NONE;
	
	private void initForm()
	{
		this.setLayout(null);

		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		MessageDataVO messageData = gParentFrame.getMessageData();
		
		Insets insets = this.getInsets();

		int xp = insets.left + 5;
		int yp = insets.top + 5;
		
		//Label
		//

		//EventDate
		lblEventDate = new JLabel("Data:");
		lblEventDate.setLocation(xp, yp);
		lblEventDate.setSize(100, 20); 
		lblEventDate.setVisible(true);
		this.add(lblEventDate);
		yp = yp + 25;		
		
		//EventSubject
		lblEventSubject = new JLabel("Assunto:");
		lblEventSubject.setLocation(xp, yp);
		lblEventSubject.setSize(100, 20); 
		lblEventSubject.setVisible(true);
		this.add(lblEventSubject);
		yp = yp + 25;		
		
		//EventMessage
		lblEventMessage = new JLabel("Mensagem:");
		lblEventMessage.setLocation(xp, yp);
		lblEventMessage.setSize(100, 20); 
		lblEventMessage.setVisible(true);
		this.add(lblEventMessage);
		yp = yp + 185;		
		xp = insets.left + 640 - 30 - 100;
		
		//Button
		//
		
		//Button: Fechar
		btnFechar = new JButton("Fechar");
		btnFechar.setActionCommand(Integer.toString(AppDefs.RSCODE_MESSAGE_FECHAR));
		btnFechar.setLocation(xp, yp);
		btnFechar.setSize(100, 20); 
		btnFechar.setVisible(true);
		btnFechar.addActionListener(this);
		this.add(btnFechar);
		xp = insets.left + 5 + 100 + 5;
		yp = insets.top + 5;
		
		//Text
		//
		String strEventDate = df.format(messageData.getEventDate());
		
		txtEventDate = new JTextField(strEventDate);
		txtEventDate.setLocation(xp, yp);
		txtEventDate.setSize(500, 20); 
		txtEventDate.setVisible(true);
		txtEventDate.setEditable(false);
		this.add(txtEventDate);
		yp = yp + 25;
							
		//Text
		//
		txtEventSubject = new JTextField(messageData.getEventSubject());
		txtEventSubject.setLocation(xp, yp);
		txtEventSubject.setSize(500, 20); 
		txtEventSubject.setVisible(true);
		txtEventSubject.setEditable(false);
		this.add(txtEventSubject);
		yp = yp + 25;
		
		//Text
		//
		this.txtEventMessage = FormControlUtil.newTextArea(messageData.getEventMessage(), xp, yp, 500, 180, true, false);
		this.add(txtEventMessage);
		
	}
	
//Public 
	
	public MessagePanel()
	{
		super();
		
		MessagePanel.gPanel = this;
	}
	
	public void init(
		MessageFrame parentFrame, 
		MessageDataVO messageData,
		ResultListener resultListener)
	{
		MessagePanel.gParentFrame = parentFrame;
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
		gParentFrame.actionResultListener(new ResultEvent(rscode, null));
			
		MessagePanel.gParentFrame.dispose();
	}
		
	public void doActionCancelar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_MESSAGE_CANCELAR;
		gParentFrame.actionResultListener(new ResultEvent(rscode, null));

		MessagePanel.gParentFrame.dispose();
	}
	
	public void doActionFechar(ActionEvent e) 
	{
		rscode = AppDefs.RSCODE_MESSAGE_FECHAR;
		gParentFrame.actionResultListener(new ResultEvent(rscode, null));
	
		MessagePanel.gParentFrame.dispose();
	}
	
	/* Listeners */
	
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

	/* Getters/Setters */
	
	public static MessageFrame getParentFrame() {
		return gParentFrame;
	}

	public static MessagePanel getPanel() {
		return gPanel;
	}

	public int getRSCode() {
		return rscode;
	}

	public void setRSCode(int rscode) {
		this.rscode = rscode;
	}
	
}
