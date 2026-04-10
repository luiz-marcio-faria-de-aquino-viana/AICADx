/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
