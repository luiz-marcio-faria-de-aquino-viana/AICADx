/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.utils;

import java.sql.ResultSet;

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
