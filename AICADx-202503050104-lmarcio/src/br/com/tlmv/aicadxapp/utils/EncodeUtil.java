/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * EncodeUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeUtil 
{
	
	public static String encode(String strIn)
	{
		String outStr = URLEncoder.encode(strIn);
		return outStr;
	}
	
	public static String decode(String strIn)
	{
		String outStr = URLDecoder.decode(strIn);
		return outStr;
	}

}
