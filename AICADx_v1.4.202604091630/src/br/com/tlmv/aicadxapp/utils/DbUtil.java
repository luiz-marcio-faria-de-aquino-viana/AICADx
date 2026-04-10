/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DbUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/06/2025
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

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.vo.SqlTypeVO;

public class DbUtil 
{
//Private
	private ResultSet rs = null;
	private Integer pos = 1;
	
//Public
	
	public DbUtil(ResultSet rs)
	{
		this.rs = rs;
		this.pos = 1;
	}
	
	/* Methodes */
	
	public static String newObjVer()
	{
		String objVer = UuidUtil.generateVersionNumber();
		return objVer;
	}
	
	public static String toSqlKey(String objVer, long objId)
	{
		String key = String.format("%s_%s", objVer, Long.toString(objId));
		return key;
	}
	
	public static String toSqlKey(String objVer, String strObjId)
	{
		String key = String.format("%s_%s", objVer, strObjId);
		return key;
	}
	
	public static String toSqlType(String sqlSrc, SqlTypeVO sqlType)
	{
		String sqlDst = StringUtil.replace(sqlSrc, sqlType.getTagSqlType(), sqlType.getSqlType());
		return sqlDst;
	}
	
	public static String toArrSqlType(String sqlSrc, SqlTypeVO[] arrSqlType)
	{
		String sqlDst = new String( sqlSrc );

		int sz = arrSqlType.length;
		for(int i = 0; i < sz; i++) {
			SqlTypeVO oSqlType = arrSqlType[i];
			sqlDst = toSqlType(sqlDst, oSqlType);
		}
		return sqlDst;
	}
	
	public static String toSqlParms(Object[] arrVal)
	{
		int last_pos = arrVal.length - 1;
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i <= last_pos; i++) {
			sb.append("?"); 
			if(i < last_pos)
				sb.append(", "); 
		}
		return sb.toString();
	}
	
	/* Getters */
	
	public Integer getNextInt()
	{
		Integer result = null;
		
		try {
			result = rs.getInt(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public String getNextStr()
	{
		String result = null;
		
		try {
			result = rs.getString(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public Double getNextDbl()
	{
		Double result = null;
		
		try {
			result = rs.getDouble(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public Boolean getNextBol()
	{
		Boolean result = null;
		
		try {
			result = rs.getBoolean(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public Long getNextLng()
	{
		Long result = null;
		
		try {
			result = rs.getLong(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public java.sql.Date getNextDate()
	{
		java.sql.Date result = null;
		
		try {
			result = rs.getDate(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
	
	public java.sql.Timestamp getNextTimestamp()
	{
		java.sql.Timestamp result = null;
		
		try {
			result = rs.getTimestamp(pos++);
		}
		catch(Exception e) { }
		
		return result;
	}
		
}
