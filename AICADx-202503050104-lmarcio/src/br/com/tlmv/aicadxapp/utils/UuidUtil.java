/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * UuidUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;

public class UuidUtil 
{
//Public
	
	public static String generateUUID()
	{
		Date dataHoraAtual = new Date();		
		long valUuid = dataHoraAtual.getTime() - AppDefs.DATETIME_INITIAL_MILI;
		String strUuid = Long.toString(valUuid);
		return strUuid;
	}
	
}
