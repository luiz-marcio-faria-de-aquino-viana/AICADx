/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * MessageDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;

public class MessageDataVO 
{
//Private
	private int messageType;
	private int debugLevel;
	private Date eventDate;
	private String eventSubject;
	private String eventMessage;
	private String className;
	private String action;
		
//Public
	
	public MessageDataVO()
	{
		reset();
	}
	
	public MessageDataVO(
		int messageType,
		int debugLevel,
		Date eventDate,
		String eventSubject,
		String eventMessage,
		String className,
		String action)
	{
		this.messageType = messageType;
		this.debugLevel = debugLevel;
		this.eventDate = eventDate;
		this.eventSubject = eventSubject;
		this.eventMessage = eventMessage;
		this.className = className;
		this.action = action;
	}
	
	/* Methodes */
	
	public void reset()
	{
		messageType = AppDefs.DEF_MSGTYPE_NONE;
		debugLevel = AppDefs.DEBUG_LEVEL99;
		eventDate = new Date();
		eventSubject = "";
		eventMessage = "";
		className = "";
		action = Integer.toString(AppDefs.RSCODE_MESSAGE_NONE);		
	}

	/* Getters/Setters */

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getDebugLevel() {
		return debugLevel;
	}

	public void setDebugLevel(int debugLevel) {
		this.debugLevel = debugLevel;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventSubject() {
		return eventSubject;
	}

	public void setEventSubject(String eventSubject) {
		this.eventSubject = eventSubject;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEventMessage() {
		return eventMessage;
	}

	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}	

}
