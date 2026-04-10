/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * AppProf.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/04/2026
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

package br.com.tlmv.aicadxapp;

import java.util.Date;

public class AppProf 
{
//Private
	private int debugLevel;
	private String name;
	private int numMark;
	private long startTimeMili = AppDefs.NULL_LNG;
	private long lastTimeMili = AppDefs.NULL_LNG;
	private long ellapsedTimeMili = AppDefs.NULL_LNG;

//Public
	
	public AppProf(int debugLevel, String name)
	{
		this.debugLevel = debugLevel;
		this.name = name;
	}

	public void start()
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		Date dataHoraAtual = new Date();

		this.numMark = 0;
		this.startTimeMili = dataHoraAtual.getTime();
		this.lastTimeMili = this.startTimeMili;
		this.ellapsedTimeMili = this.lastTimeMili - this.startTimeMili;
	}

	public void mark()
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		Date dataHoraAtual = new Date();

		this.numMark += 1;
		long currTimeMili = dataHoraAtual.getTime();
		this.ellapsedTimeMili = currTimeMili - this.lastTimeMili;
		this.lastTimeMili = currTimeMili;

		String warnmsg = String.format(
			"PROF(%s): [MARK-%s] EllapsedTimeMili:%s; ",
			this.name,
			this.numMark,
			this.ellapsedTimeMili );
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	public void stop()
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		Date dataHoraAtual = new Date();

		long currTimeMili = dataHoraAtual.getTime();
		this.ellapsedTimeMili = currTimeMili - this.lastTimeMili;
		this.lastTimeMili = currTimeMili;
		
		long totalTimeMili = this.lastTimeMili - this.startTimeMili;
		
		String warnmsg = String.format(
			"PROF(%s): [STOP] EllapsedTimeMili:%s; TotalTimeMili:%s; ",
			this.name,
			this.ellapsedTimeMili,
			totalTimeMili );
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());

		this.debugLevel = AppDefs.DEBUG_LEVEL99;
	}
	
}
