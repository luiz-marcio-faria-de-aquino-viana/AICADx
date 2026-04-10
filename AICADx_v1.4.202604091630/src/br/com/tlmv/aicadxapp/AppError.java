/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * AppError.java
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

package br.com.tlmv.aicadxapp;

import java.util.Date;

import br.com.tlmv.aicadxapp.frm.BaseFrame;
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
		BaseFrame parent,
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
				Integer.toString(AppDefs.RSCODE_MESSAGE_NONE));
			
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
		BaseFrame parent,
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
					Integer.toString(AppDefs.RSCODE_MESSAGE_NONE));
				
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
		BaseFrame parent,
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
				Integer.toString(AppDefs.RSCODE_MESSAGE_NONE));
		
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
