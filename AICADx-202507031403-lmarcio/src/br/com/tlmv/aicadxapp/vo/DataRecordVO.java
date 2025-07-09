/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DataRecordVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

public class DataRecordVO 
{
//Private
	private int type;
	private int datasz;
	private byte[] data;
	
//Public
	
	public DataRecordVO(int type, byte[] data) {
		this.init(type, data);
	}
	
	/* Methodes */
	
	public void init(int type, byte[] data)
	{
		this.type = type;
		
		this.datasz = data.length;
		this.data = new byte[this.datasz];		
	}

	/* Methodes */
	
	public int getType() {
		return type;
	}

	public int getDatasz() {
		return datasz;
	}

	public byte[] getData() {
		return data;
	}
	
}
