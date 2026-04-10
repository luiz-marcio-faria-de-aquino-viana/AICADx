/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MessageDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
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
