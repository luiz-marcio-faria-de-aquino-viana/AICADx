/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * StringUtil.java
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

import java.awt.Color;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;

public class StringUtil 
{
//Public
	
	public static boolean isEmpty(String s)
	{
		return ((s == null) || ( "".equals(s) ));
	}
	
	public static String getHead(String s, char c)
	{
		StringBuffer result = new StringBuffer();
		int pos = 0;
		while(pos < s.length()) {
			char ch = s.charAt(pos);
			if(ch == c) break;
			result.append(ch);
			pos++;
		}
		return result.toString();
	}

	public static String getTail(String s, char c)
	{
		int pos = 0;
		while(pos < s.length()) {
			char ch = s.charAt(pos);
			if(ch == c) 
				return s.substring(pos);
			pos++;
		}
		return "";
	}

	public static String trimAll(String str)
	{		
		return str.trim();
	}
	
	public static String fillRight(String strIn, char c, int n)
	{
		StringBuilder sb = new StringBuilder();

		if(strIn != null) {
			int strInSz = strIn.length();
			if(strInSz > n)
				strInSz = n;
			
			sb.append( strIn.substring(0, strInSz) );			
			for(int i = strInSz; i < n; i++) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static String fillLeft(String strIn, char c, int n)
	{
		StringBuilder sb = new StringBuilder();

		if(strIn != null) {
			int strInSz = strIn.length();
			if(strInSz > n)
				strInSz = n;
			
			for(int i = strInSz; i < n; i++) {
				sb.append(c);
			}			
			sb.append( strIn.substring(0, strInSz) );
		}
		return sb.toString();
	}
	
	public static String replace(String inStr, String oldStr, String newStr) {
		String outStr = inStr.replace(oldStr, newStr);
		return outStr;
	}
	
	public static String toStringOption(String text, String key)
	{
		StringBuilder sbDst = new StringBuilder();
		
		String keyUCase = key.toUpperCase();
		String keyLCase = key.toLowerCase();
		char chKey = keyLCase.charAt(0);
		
		String strSrc = text.toLowerCase();
		int sz = strSrc.length();
		for(int i = 0; i < sz; i++) {
			char ch = strSrc.charAt(i);
			if(ch != chKey)
				sbDst.append(ch);
			else
				sbDst.append(keyUCase);
		}
		return sbDst.toString();
	}
		
	public static String getOnlyNumbers(String s)
	{
		StringBuilder result = new StringBuilder();
		if(s != null) {
			for(int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if((c >= '0') && (c <= '9'))
					result.append(c);
			}
		}
		return result.toString();
	}

	public static String[] split(String s, char c)
	{
		ArrayList<String> result = new ArrayList<String>();
		String parseString = new String(s);
		boolean test = (parseString.length() > 0);
		while( test ) {
			String tk = StringUtil.getHead(parseString, c);
			result.add(tk);
			parseString = StringUtil.getTail(parseString, c);
			if(parseString.length() > 0)
				parseString = parseString.substring(1);
			else
				test = false;
		}
		return result.toArray(new String[result.size()]);						
	}

	public static String arrayToString(String arr[])
	{
		StringBuffer sbuf = new StringBuffer();
		for(int i = 0; i < arr.length; i++) {
			if(sbuf.length() == 0)
				sbuf.append(arr[i]);
			else
				sbuf.append(",").append(arr[i]);
		}
		return sbuf.toString();
	}

	public static int arrayFind(String arr[], String s)
	{
		for(int i = 0; i < arr.length; i++) {
			if( s.equals(arr[i]) )
				return i;
		}
		return -1;
	}

	public static String timeFormat(Double hourWithFrac)
	{
		double hour = Math.floor(hourWithFrac);
		double min = Math.floor((hourWithFrac - hour) * 60);
		return String.format("%02.0f:%02.0f", hour, min);
	}

	public static Double parseTime(String timeHourMin)
	{
		String[] arr = StringUtil.split(timeHourMin, ':');

		double hour = 0.0;
		double min = 0.0;

		if(arr.length > 1)
		{
			try {
				hour = Double.parseDouble(arr[0]);
			}
			catch(Exception e) {
				
			}

			try {
				min = Double.parseDouble(arr[1]);
			}
			catch(Exception e) {
				
			}
		}
		
		double result = (hour + min / 60.0);
		
		return result;
	}
	
	public static String safeStr(String s)
	{
		if(s != null)
		{
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				if(ch > 255) 
					ch += ' ';
				sb.append(ch);
			}
			return sb.toString();
		}
		return "";
	}
	
	public static Date safeDate(DateFormat df, String strIn)
	{
		Date result = new Date(1900, 0, 1);
		
		try
		{
			result = df.parse(strIn);
		}
		catch(Exception e)
		{
			
		}
		
		return result;
	}
	
	public static double safeDbl(NumberFormat nf, String strIn)
	{
		double result = 0.0;
		
		try
		{
			result = nf.parse(strIn).doubleValue();
		}
		catch(Exception e)
		{
			
		}
		
		return result;
	}
	
	public static int safeInt(String strIn)
	{
		int result = 0;
		
		try
		{
			result = Integer.parseInt(strIn);
		}
		catch(Exception e)
		{
			
		}
		
		return result;
	}
	
	public static long safeLng(String strIn)
	{
		long result = 0;
		
		try
		{
			result = Long.parseLong(strIn);
		}
		catch(Exception e)
		{
			
		}
		
		return result;
	}

	/* GET/SET COLOR DEFINITION */
	
	public static Color setColor(int colorIndex)
	{
		Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
		
		if(colorIndex < AppDefs.ARR_COLORNAME_TABLE.length) {
			c = AppDefs.ARR_COLORINDEX_TABLE[colorIndex];
		}
		return c;		
	}

	/* GET/SET COLOR NAME DEFINITION */
	
	public static String getColorName(int colorIndex)
	{
		String strResult = String.format("%s", Integer.toString(colorIndex));
		
		if(colorIndex < AppDefs.ARR_COLORNAME_TABLE.length) {
			strResult = AppDefs.ARR_COLORNAME_TABLE[colorIndex];
		}
		return strResult;
	}
	
	public static String getColorName(int r, int g, int b)
	{
		String strResult = String.format(
			"rgb(%s, %s, %s)", 
			Integer.toString(r),
			Integer.toString(g),
			Integer.toString(b) );
		return strResult;
	}
	
	/* GET/SET LTYPE DEFINITION */
	
	public static BorderStrokeVO getLtypeName(int ltypeIndex)
	{
		BorderStrokeVO b = AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_SOLID];
		
		if(ltypeIndex < AppDefs.ARR_LTYPE_TABLE.length) {
			b = AppDefs.ARR_LTYPE_TABLE[ltypeIndex];
		}
		return b;		
	}
	
}
