/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PromptOptionVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/03/2025
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

import br.com.tlmv.aicadxapp.utils.StringUtil;

public class PromptOptionVO 
{
//Private
	private int optionId;
	private String textOption;
	private String keyOption;
	private boolean defaultOption;
	
	//Extended_Params
	private Object val0;
	private Object val1;
	private Object val2;
	private Object val3;
	private Object val4;
	
//Public
	
	public PromptOptionVO(
		int optionId,
		String textOption,
		String keyOption,
		boolean defaultOption)
	{
		this.init(
			optionId,
			textOption,
			keyOption,
			defaultOption,
			null,
			null,
			null,
			null,
			null);
	}
	
	public PromptOptionVO(
		int optionId,
		String textOption,
		String keyOption,
		boolean defaultOption,
		Object val0,
		Object val1,
		Object val2,
		Object val3,
		Object val4)
	{
		this.init(
			optionId,
			textOption,
			keyOption,
			defaultOption,
			val0,
			val1,
			val2,
			val3,
			val4);
	}
		
	public PromptOptionVO(PromptOptionVO o)
	{
		this.init(o);
	}

	/* Methodes */

	public void init(
		int optionId, 
		String textOption, 
		String keyOption, 
		boolean defaultOption,
		Object val0,
		Object val1,
		Object val2,
		Object val3,
		Object val4)
	{
		this.optionId = optionId;
		this.textOption = textOption;
		this.keyOption = keyOption;
		this.defaultOption = defaultOption;
		this.val0 = val0;
		this.val1 = val1;
		this.val2 = val2;
		this.val3 = val3;
		this.val4 = val4;
	}
	
	public void init(PromptOptionVO o)
	{
		this.init(
			o.optionId,
			o.textOption,
			o.keyOption,
			o.defaultOption,
			o.val0,
			o.val1,
			o.val2,
			o.val3,
			o.val4 );
	}

	public String toStringOption()
	{
		String result = StringUtil.toStringOption(this.textOption, this.keyOption);
		return result;
	}
	
	public String toString()
	{
		return this.textOption;
	}
	
	/* Getters/Setters */
		
	public String getTextOption() {
		return textOption;
	}

	public void setTextOption(String textOption) {
		this.textOption = textOption;
	}

	public String getKeyOption() {
		return keyOption;
	}

	public void setKeyOption(String keyOption) {
		this.keyOption = keyOption;
	}

	public boolean isDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(boolean defaultOption) {
		this.defaultOption = defaultOption;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public Object getVal0() {
		return val0;
	}

	public void setVal0(Object val0) {
		this.val0 = val0;
	}

	public Object getVal1() {
		return val1;
	}

	public void setVal1(Object val1) {
		this.val1 = val1;
	}

	public Object getVal2() {
		return val2;
	}

	public void setVal2(Object val2) {
		this.val2 = val2;
	}

	public Object getVal3() {
		return val3;
	}

	public void setVal3(Object val3) {
		this.val3 = val3;
	}

	public Object getVal4() {
		return val4;
	}

	public void setVal4(Object val4) {
		this.val4 = val4;
	}
	
}
