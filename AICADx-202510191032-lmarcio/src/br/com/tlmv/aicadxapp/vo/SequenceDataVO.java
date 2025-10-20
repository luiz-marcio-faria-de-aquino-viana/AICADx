/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * SequenceDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

public class SequenceDataVO 
{
//Private
	private String sequenceDataId;
	private long currVal;
	private long initVal;
	private long maxVal;
	
//Public

	public SequenceDataVO()
	{
		this.init(
			"seq",
			0,
			1,
			Long.MAX_VALUE);
	}

	public SequenceDataVO(
		String sequenceDataId,
		long currVal,
		long initVal,
		long maxVal)
	{
		this.init(
			sequenceDataId,
			currVal,
			initVal,
			maxVal);
	}
	
	/* Methodes */
		
	public void init(
		String sequenceDataId,
		long currVal,
		long initVal,
		long maxVal)
	{
		this.sequenceDataId = sequenceDataId;
		this.currVal = currVal;
		this.initVal = initVal;
		this.maxVal = maxVal;
	}
	
	/* Methodes */
	
	public synchronized void resetSeq(long val)
	{
		if( (val >= this.initVal) && (val <= this.maxVal) )
			this.currVal = val;
	}

	public synchronized long currSeq()
	{
		return this.currVal;
	}
	
	public synchronized long nextSeq()
	{
		if(this.currVal < this.maxVal)
			this.currVal = this.currVal + 1L;
		else
			this.currVal = this.initVal;
		return this.currVal;
	}
	
	/* DEBUG */
	
	public String toString()
	{
		String result = String.format("%s - Valor: %s - Val.Inicial: %s - Val.Máx.: %s", 
			this.sequenceDataId,
			Long.toString(this.currVal),
			Long.toString(this.initVal),
			Long.toString(this.maxVal));
		return result;
	}

	/* Getters/Setters */
	
	public String getSequenceDataId() {
		return sequenceDataId;
	}

	public long getCurrVal() {
		return currVal;
	}

	public long getInitVal() {
		return initVal;
	}

	public long getMaxVal() {
		return maxVal;
	}

}
