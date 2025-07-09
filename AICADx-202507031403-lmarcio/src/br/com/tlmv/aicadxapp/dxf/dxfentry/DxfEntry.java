/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DxfEntry.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dxf.dxfentry;

public class DxfEntry 
{
//Private
	private long dxfLineNum;
	private int dxfCode;
	private String dxfVal;
	
//Public
	
	public DxfEntry(long dxfLineNum, int dxfCode, String dxfVal) 
	{
		this.dxfLineNum = dxfLineNum;
		this.dxfCode = dxfCode;
		this.dxfVal = dxfVal;
	}

	/* Getters/Setters */

	public long getDxfLineNum()
	{
		return this.dxfLineNum;
	}
	
	public int getDxfCode()
	{
		return this.dxfCode;
	}

	public String getDxfVal()
	{
		return this.dxfVal;
	}
	
}
