/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CapacidadeConducaoCorrenteVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/10/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
package br.com.tlmv.aicadxmod.eletrica.vo;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class DisjuntorProtecaoVO 
{
//Private
	private Integer disjuntorProtecaoId;
	private String descricao;
	private Double correnteMaxima;

//Public

    public DisjuntorProtecaoVO() {
    	this.init(
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL );
    }
			
	public DisjuntorProtecaoVO(
		Integer disjuntorProtecaoId,
		String descricao,
		Double correnteMaxima )
	{
    	this.init(
			disjuntorProtecaoId,
			descricao,
			correnteMaxima );
	}

    public DisjuntorProtecaoVO(DisjuntorProtecaoVO other)
    {
        this.init(other);
    }

	/* Methodes */
		
	public void init(
		Integer disjuntorProtecaoId,
		String descricao,
		Double correnteMaxima )
	{
		this.disjuntorProtecaoId = disjuntorProtecaoId;
		this.descricao = descricao;
		this.correnteMaxima = correnteMaxima;
	}
	
    public void init(DisjuntorProtecaoVO other)
    {
    	this.init(
			other.disjuntorProtecaoId,
			other.descricao,
			other.correnteMaxima );
    }
	
    /* CREATE */
    
    public static DisjuntorProtecaoVO createFrom(String str)
    {
    	DisjuntorProtecaoVO o = new DisjuntorProtecaoVO();
    	o.fromStr(str);
    	return o;
    }
    
    /* DEBUG */
    
	public String toStr() {
		String str = String.format(
			"disjuntorProtecaoId:%s;" +
			"descricao:%s;" +
			"correnteMaxima:%s;\n",
			this.disjuntorProtecaoId,
			this.descricao,
			this.correnteMaxima );
		return str;
	}

	public void fromStr(String str) {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String[] arr = StringUtil.split(str, '|');
		if(arr.length >= 4) {
			String strDisjuntorProtecaoId = arr[0];
			//String strInstalacaoId = arr[1];
			String strDescricao = arr[2];
			String strCorrenteMaxima = arr[3];

			this.disjuntorProtecaoId = StringUtil.safeInt(strDisjuntorProtecaoId);
			this.descricao = strDescricao;
			this.correnteMaxima = StringUtil.safeDbl(nf6, strCorrenteMaxima);
		}
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		AppError.showCmdWarn(debugLevel, str, this.getClass());
	}
	    	
	/* Getters/Setters */
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDisjuntorProtecaoId() {
		return disjuntorProtecaoId;
	}

	public void setDisjuntorProtecaoId(Integer disjuntorProtecaoId) {
		this.disjuntorProtecaoId = disjuntorProtecaoId;
	}

	public Double getCorrenteMaxima() {
		return correnteMaxima;
	}

	public void setCorrenteMaxima(Double correnteMaxima) {
		this.correnteMaxima = correnteMaxima;
	}

}
