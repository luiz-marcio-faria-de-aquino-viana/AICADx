/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SequenceDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import org.w3c.dom.Node;

public class SequenceDataVO 
{
//Private
	private String sequenceDataId;
	private int currVal;
	private int initVal;
	private int maxVal;
	
//Public

	public SequenceDataVO()
	{
		this.sequenceDataId = "-1";
		this.currVal = 0;
		this.initVal = 0;
		this.maxVal = Integer.MAX_VALUE;
	}

	public SequenceDataVO(
		String sequenceDataId,
		int currVal,
		int initVal,
		int maxVal)
	{
		this.sequenceDataId = sequenceDataId;
		this.currVal = currVal;
		this.initVal = initVal;
		this.maxVal = maxVal;
	}
	
	/* Methodes */
		
	public void loadData(Node nSequenciaData, SequenceDataVO oDefault)
	{
		//TODO:
	}

	public String toXml()
	{
		//TODO:
		
		return null;
	}	
	
	/* Methodes */
	
	public String toString()
	{
		String result = String.format("%s - Valor: %s - Val.Inicial: %s - Val.Máx.: %s", 
			this.sequenceDataId,
			Integer.toString(this.currVal),
			Integer.toString(this.initVal),
			Integer.toString(this.maxVal));
		return result;
	}

	/* Getters/Setters */
	
	public String getSequenceDataId() {
		return sequenceDataId;
	}

	public void setSequenceDataId(String sequenceDataId) {
		this.sequenceDataId = sequenceDataId;
	}

	public int getCurrVal() {
		return currVal;
	}

	public void setCurrVal(int currVal) {
		this.currVal = currVal;
	}

	public int getInitVal() {
		return initVal;
	}

	public void setInitVal(int initVal) {
		this.initVal = initVal;
	}

	public int getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}
	
}
