/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DataRecordUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/03/2025
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

package br.com.tlmv.aicadxapp.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;

public class DataRecordUtil 
{
//Public

	public static byte[] copyByteArray(byte[] arrDst, int posDst, byte[] arrSrc)
	{
		int arrDstSz = arrDst.length;
		
		int arrSrcSz = arrSrc.length;		
		for(int i = 0; i < arrSrcSz; i++) {
			int currPos = posDst + i;
			if(currPos >= arrDstSz) break;
			
			arrDst[currPos] = arrSrc[i];
		}
		return arrDst;
	}

	public static byte[] copyByteArray(byte[] arrDst, int posDst, byte[] arrSrc, int posSrc, int len)
	{
		int arrDstSz = arrDst.length;
		
		int arrSrcSz = arrSrc.length;		
		for(int i = 0; i < len; i++) {
			int currPosSrc = posSrc + i;
			if(currPosSrc >= arrSrcSz) break;
			
			int currPosDst = posDst + i;
			if(currPosDst >= arrDstSz) break;
			
			arrDst[currPosDst] = arrSrc[currPosSrc];
		}
		return arrDst;
	}
	
	/* BYTE_ARRAY TO LIST_OF_BYTES CONVERSION */

	public static byte[] listOfBytesToByteArray(ArrayList<Byte> lsByte) {
		int sz = lsByte.size();
		
		byte[] arr = new byte[sz];
		
		int pos = 0;
		for(Byte b : lsByte) {
			arr[pos++] = b; 
		}
		return arr;
	}

	public static ArrayList<Byte> byteArrayToListOfBytes(byte[] arr) {
		ArrayList<Byte> ls = new ArrayList<Byte>();
		
		int sz = arr.length;
		for(int i = 0; i < sz; i++) {
			ls.add( arr[i] ); 
		}
		return ls;
	}
	
	/* xxxToData() */
	
	public static byte[] strToData(String strIn, int n) {
		byte[] arrResult = null;
		 
		try {
			String strOut = StringUtil.fillRight(strIn, ' ', n);
			arrResult = strOut.getBytes("utf-8"); 
		}
		catch(Exception e) { }
		
		return arrResult;
	}
	
	public static byte[] intToData(int val, int n) {
		byte[] arrResult = null;
		 
		try {
			NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
			
			String strOut = nf0.format(val);
			arrResult = DataRecordUtil.strToData(strOut, n); 
		}
		catch(Exception e) { }
		
		return arrResult;
	}
	
	public static byte[] lngToData(long val, int n) {
		byte[] arrResult = null;
		 
		try {
			NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
			
			String strOut = nf0.format(val);
			arrResult = DataRecordUtil.strToData(strOut, n); 
		}
		catch(Exception e) { }
		
		return arrResult;
	}
	
	public static byte[] dblToData(double val, int n) {
		byte[] arrResult = null;
		 
		try {
			NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
			
			double dVal = val * AppDefs.MATHPREC_FILE;
			
			String strOut = nf0.format(dVal);
			arrResult = DataRecordUtil.strToData(strOut, n); 
		}
		catch(Exception e) { }
		
		return arrResult;
	}
	
	public static byte[] dateToData(Date val, int n) {
		byte[] arrResult = null;
		 
		try {
			DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE5_FILEFORMAT_MASC);
			
			String strOut = df.format(val);
			arrResult = DataRecordUtil.strToData(strOut, n); 
		}
		catch(Exception e) { }
		
		return arrResult;
	}
	
	/* dataToXxx() */
	
	public static String dataToStr(byte[] arr) {
		String strResult = AppDefs.NULL_STR;
		 
		try {
			String strOut = new String(arr, "utf-8");
			strResult = StringUtil.trimAll(strOut);
		}
		catch(Exception e) { }
		
		return strResult;
	}
	
	public static int dataToInt(byte[] arr) {
		int result = AppDefs.NULL_INT;
		 
		try {
			NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
			
			String strOut = dataToStr(arr);
			result = nf0.parse(strOut).intValue(); 
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public static long dataToLng(byte[] arr) {
		long result = 0;
		 
		try {
			NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
			
			String strOut = dataToStr(arr);
			result = nf0.parse(strOut).longValue(); 
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public static double dataToDbl(byte[] arr) {
		double result = AppDefs.NULL_DBL;
		 
		try {
			NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
			
			String strOut = dataToStr(arr);
			double val = nf0.parse(strOut).doubleValue();
			result = val / AppDefs.MATHPREC_FILE;
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public static Date dataToDate(byte[] arr) {
		Date dtResult = AppDefs.NULL_DATE;
		 
		try {
			DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE5_FILEFORMAT_MASC);
			
			String strOut = dataToStr(arr);
			dtResult = df.parse(strOut);
		}
		catch(Exception e) { }
		
		return dtResult;
	}
	
}
