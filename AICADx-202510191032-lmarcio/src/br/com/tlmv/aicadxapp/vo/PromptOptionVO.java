/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.vo;

import br.com.tlmv.aicadxapp.utils.StringUtil;

public class PromptOptionVO 
{
//Private
	private int optionId;
	private String textOption;
	private String keyOption;
	private boolean defaultOption;
	
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
			defaultOption);
	}
	
	public PromptOptionVO(PromptOptionVO o)
	{
		this.init(o);
	}

	/* Methodes */

	public void init(int optionId, String textOption, String keyOption, boolean defaultOption)
	{
		this.optionId = optionId;
		this.textOption = textOption;
		this.keyOption = keyOption;
		this.defaultOption = defaultOption;
	}
	
	public void init(PromptOptionVO o)
	{
		this.init(
			o.optionId,
			o.textOption,
			o.keyOption,
			o.defaultOption);
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
	
}
