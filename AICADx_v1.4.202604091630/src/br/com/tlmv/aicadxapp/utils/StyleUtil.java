/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * StyleUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
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

import java.awt.Color;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;

public class StyleUtil 
{
//Public
	
	/* GET/SET COLOR DEFINITION */
	
	public static int getColorIndex(int val)
	{
		if(val < AppDefs.ARR_COLORNAME_TABLE.length) {
			return val;
		}
		return AppDefs.COLORINDEX_BLACK;
	}

	public static String getColorName(int val)
	{
		int colorIndex = getColorIndex(val);
		
		String strColorName = AppDefs.ARR_COLORNAME_TABLE[colorIndex];
		return strColorName;
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
	
	public static Color getColor(int colorIndex)
	{
		Color c = AppDefs.ARR_COLORINDEX_TABLE[AppDefs.COLORINDEX_BLACK];
		
		if(colorIndex < AppDefs.ARR_COLORNAME_TABLE.length) {
			c = AppDefs.ARR_COLORINDEX_TABLE[colorIndex];
		}
		return c;		
	}
	
	/* GET/SET LTYPE DEFINITION */
	
	public static int getLtypeIndex(int val)
	{
		if(val < AppDefs.ARR_LTYPE_TABLE.length) {
			return val;
		}
		return AppDefs.LTYPEINDEX_SOLID;
	}
	
	public static String getLtypeName(int val)
	{
		int ltypeIndex = getLtypeIndex(val);
		
		BorderStrokeVO b = AppDefs.ARR_LTYPE_TABLE[ltypeIndex];
		return b.getName();
	}

	public static BorderStrokeVO getLtypeByName(String name)
	{
		int sz = AppDefs.ARR_LTYPE_TABLE.length;
		for(int i = 0; i < sz; i++) {
			BorderStrokeVO b = AppDefs.ARR_LTYPE_TABLE[i];
			if(name.compareToIgnoreCase(b.getName()) == 0)
				return b;
		}
		return AppDefs.ARR_LTYPE_TABLE[AppDefs.LTYPEINDEX_SOLID];
	}
	
	public static BorderStrokeVO getLtype(int val)
	{
		int ltypeIndex = getLtypeIndex(val);
		
		BorderStrokeVO b = AppDefs.ARR_LTYPE_TABLE[ltypeIndex];
		return b;
	}
	
}
