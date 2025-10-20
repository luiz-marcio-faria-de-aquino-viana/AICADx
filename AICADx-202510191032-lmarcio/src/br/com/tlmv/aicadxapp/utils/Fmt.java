/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Fmt.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Fmt 
{
//Public
		
	public static String bToStr(Boolean val)
	{
		if(val == null) return "N";
		
		String strval = ( val ) ? "S" : "N";
		return strval;
	}
		
	public static String intToStr(Integer val)
	{
		if(val == null) return "0";
		
		String strval = Integer.toString(val);
		return strval;
	}
	
	public static String lngToStr(Long val)
	{
		if(val == null) return "0";

		String strval = Long.toString(val);
		return strval;
	}
	
	public static String dblToStr(NumberFormat nf, Double val)
	{
		if(val == null) return nf.format(0.0);

		String strval = nf.format(val);
		return strval;
	}
	
	public static String dateToStr(DateFormat df, Date val)
	{
		if(val == null) return df.format(val);

		String strval = df.format(val);
		return strval;
	}
	
}
