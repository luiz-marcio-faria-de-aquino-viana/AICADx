/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppError.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.util.Date;

import javax.swing.JComponent;

import br.com.tlmv.aicadxapp.frm.MessageFrame;
import br.com.tlmv.aicadxapp.vo.MessageDataVO;

public class AppError
{
//Public

	//Console Output - MESSAGES
	
	public static void showCmdMessage(String msg, Class oClass)
	{
		String strmsg = String.format("MSG(%s): %s", oClass.getSimpleName(), msg);
		System.out.println(strmsg);
	}
	
	public static void showCmdWarn(int debugLevel, String msg, Class oClass)
	{
		if(debugLevel == AppDefs.DEBUG_LEVEL)
		{
			String warnmsg = String.format("WARN(%s): %s", oClass.getSimpleName(), msg);
			System.out.println(warnmsg);
		}
	}

	public static void showCmdError(String msg, Class oClass)
	{
		String errmsg = String.format("ERR(%s): %s", oClass, msg);
		System.out.println(errmsg);		
		//System.exit(1);
	}

	//MessageBox Output - MESSAGES
	
	public static void showMessageBox(
		JComponent parent,
		String eventSubject, 
		String eventMessage, 
		Class oClass)
	{
		if(parent != null)
		{
			Date eventDate = new Date();
			
			MessageDataVO messageData = new MessageDataVO(
				AppDefs.DEF_MSGTYPE_NONE,
				AppDefs.DEBUG_LEVEL00,
				eventDate,
				eventSubject,
				eventMessage,
				oClass.getSimpleName(),
				AppDefs.DEF_BTN_ACTION_MESSAGE_NONE);
			
			MessageFrame f = new MessageFrame(parent);
			f.init(messageData, null);
			f.show();
		}
		else
		{
			String errmsg = eventSubject + " - " + eventMessage;

			AppError.showCmdMessage(
				errmsg, 
				oClass);		
		}			
	}
	
	public static void showWarnBox(
		JComponent parent,
		int debugLevel,
		String eventSubject, 
		String eventMessage, 
		Class oClass)
	{
		if(parent != null)
		{
			if(debugLevel == AppDefs.DEBUG_LEVEL)
			{
				Date eventDate = new Date();
				
				MessageDataVO messageData = new MessageDataVO(
					AppDefs.DEF_MSGTYPE_WARN,
					debugLevel,
					eventDate,
					eventSubject,
					eventMessage,
					oClass.getSimpleName(),
					AppDefs.DEF_BTN_ACTION_MESSAGE_NONE);
				
				MessageFrame f = new MessageFrame(parent);
				f.init(messageData, null);
				f.show();			
			}
		}
		else
		{
			String errmsg = eventSubject + " - " + eventMessage;

			AppError.showCmdWarn(
				debugLevel,
				errmsg, 
				oClass);		
		}			
	}

	public static void showErrorBox(
		JComponent parent,
		String eventSubject, 
		String eventMessage, 
		Class oClass)
	{
		if(parent != null)
		{
			Date eventDate = new Date();
			
			MessageDataVO messageData = new MessageDataVO(
				AppDefs.DEF_MSGTYPE_ERROR,
				AppDefs.DEBUG_LEVEL00,
				eventDate,
				eventSubject,
				eventMessage,
				oClass.getSimpleName(),
				AppDefs.DEF_BTN_ACTION_MESSAGE_NONE);
		
			MessageFrame f = new MessageFrame(parent);
			f.init(messageData, null);
			f.show();			
		}
		else
		{
			String errmsg = eventSubject + " - " + eventMessage;

			AppError.showCmdError(
				errmsg,
				oClass);		
		}
		//System.exit(1);
	}
		
}
