/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MessageFrame.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
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

import javax.swing.JComponent;
import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class MessageFrame extends JFrame implements WindowListener, ResultListener
{
//Private
	JComponent gParent = null;
	
	private ResultListener resultListener = null;
	
	private MessageDataVO messageData = null;
	
//Public
	
	public MessageFrame(JComponent parent)
	{
		gParent = parent;
	}

	public void init(
		MessageDataVO messageData,
		ResultListener resultListener)
	{
		this.resultListener = resultListener;
				
		this.messageData = messageData;
		
		String title = "INFORMAÇÃO";
		
		if(this.messageData.getMessageType() == AppDefs.DEF_MSGTYPE_ERROR)
			title = "ERRO";
		else if(this.messageData.getMessageType() == AppDefs.DEF_MSGTYPE_WARN)
			title = "ATENÇÃO";

		setTitle(title);

		FormControlUtil.loadIcon(this);
		
		Point pos = gParent.getLocation();
		Dimension dim = gParent.getSize();
		
		MessagePanel messagePanel = new MessagePanel();
		messagePanel.init(
				this, 
				this.messageData,
				resultListener);
		
		resizePanel(
			pos.x,
			pos.y,
			dim.width,
			dim.height);

		Container c = getContentPane();
		c.add(messagePanel);
		
		this.show();
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

	public MessageDataVO getMessageData() {
		return messageData;
	}

	public void setMessageData(MessageDataVO messageData) {
		this.messageData = messageData;
	}
	
}
