/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ResultEvent.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.events;

public class ResultEvent 
{
//Private
	private int eventType;
	private Object eventData;
	
//Public

	public ResultEvent(
		int eventType,
		Object eventData)
	{
		this.eventType = eventType;
		this.eventData = eventData;
	}
	
	/* Getters/Setters */

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Object getEventData() {
		return eventData;
	}

	public void setEventData(Object eventData) {
		this.eventData = eventData;
	}	
	
}

