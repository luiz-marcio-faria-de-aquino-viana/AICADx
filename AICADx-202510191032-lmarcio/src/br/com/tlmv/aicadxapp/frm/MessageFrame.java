/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MessageFrame.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.events.ResultEvent;
import br.com.tlmv.aicadxapp.frm.events.ResultListener;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class MessageFrame extends BaseFrame
{
//Private
	private MessageDataVO messageData = null;

	private ResultListener resultListener = null;
	
//Public
	
	public MessageFrame(BaseFrame parentFrame)
	{
		super(
			parentFrame, 
			R.TIT_MESSAGEFRAME_INFORMACAO,
			AppDefs.DEFAULT_FRAME_POSX,
			AppDefs.DEFAULT_FRAME_POSY,
			AppDefs.MESSAGE_FRAME_WIDTH, 
			AppDefs.MESSAGE_FRAME_HEIGHT);
	}
	
	/* Methodes */

	public void init(MessageDataVO messageData)
	{
		this.messageData = messageData;
		
		if(this.messageData.getMessageType() == AppDefs.DEF_MSGTYPE_ERROR)
			this.frameTitle = R.TIT_MESSAGEFRAME_ERRO;
		else if(this.messageData.getMessageType() == AppDefs.DEF_MSGTYPE_WARN)
			this.frameTitle = R.TIT_MESSAGEFRAME_ATENCAO;

		this.setTitle(this.frameTitle);
		
		super.init();
		
		this.setResizable(true);
	}

	public void init(MessageDataVO messageData, ResultListener resultListener)
	{
		this.resultListener = resultListener;
		
		this.init(messageData);
	}

	/* ABSTRACT */
	
	@Override
	public void createMainPanel() 
	{
		MessagePanel messagePanel = new MessagePanel(this);
		messagePanel.init(this.messageData);

		Container c = getContentPane();
		c.add(messagePanel);
		this.show();
	}

	/* LISTENER */

	@Override
	public void actionResultListener(ResultEvent e) 
	{
		if(resultListener != null)
			resultListener.actionResultListener(e);
	}

	/* Getters/Setters */
	
	public MessageDataVO getMessageData() {
		return messageData;
	}

	public void setMessageData(MessageDataVO messageData) {
		this.messageData = messageData;
	}
	
}
