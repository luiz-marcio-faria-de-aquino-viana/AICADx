/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * PontoEletricoVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
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

package br.com.tlmv.aicadxmod.eletrica.vo;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class PontoEletricoVO 
{
//Private
    private int pontoEletricoId;
    private int categoriaPontoEletricoId;
    private String descricaoCategoriaPontoEletrico;
    private String familia;
    private String descricao;
    private String propriedades;
    private String potenciaPadrao;
    private String fasePadrao;
    private double alturaPadrao;

//Public

    public PontoEletricoVO(int pontoEletricoId, String sbuf)
    {
    	NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
    	
        String[] arr = StringUtil.split(sbuf, '^');

        this.pontoEletricoId = pontoEletricoId;
        this.categoriaPontoEletricoId = -1;

        try {
        	if(arr.length >= 7) {
	            this.descricaoCategoriaPontoEletrico = arr[0];
	            this.familia = arr[1];
	            this.descricao = arr[2];
	            this.propriedades = arr[3];
	            this.potenciaPadrao = arr[4];
	            this.fasePadrao = arr[5];
	            this.alturaPadrao = StringUtil.safeDbl(nf6, arr[6]);
        	}
        }
        catch(Exception e) { }
    }

    public PontoEletricoVO(
        int pontoEletricoId,
        int categoriaPontoEletricoId,
        String descricaoCategoriaPontoEletrico,
        String familia,
        String descricao,
        String propriedades,
        String potenciaPadrao,
        String fasePadrao,
        double alturaPadrao)
    {
    	this.pontoEletricoId = pontoEletricoId;
    	this.categoriaPontoEletricoId = categoriaPontoEletricoId;
    	this.descricaoCategoriaPontoEletrico = descricaoCategoriaPontoEletrico;
    	this.familia = familia;
    	this.descricao = descricao;
    	this.propriedades = propriedades;
    	this.potenciaPadrao = potenciaPadrao;
    	this.fasePadrao = fasePadrao;
    	this.alturaPadrao = alturaPadrao;
    }

    /* DEBUG */
    
    @Override
	public String toString() {
		return this.descricao;
	}

    /* Getters/Setters */
    
    public int getPontoEletricoId()
    {
        return this.pontoEletricoId;
    }

    public void setPontoEletricoId(int pontoEletricoId)
    {
    	this.pontoEletricoId = pontoEletricoId;
    }

    public int getCategoriaPontoEletricoId()
    {
        return this.categoriaPontoEletricoId;
    }

    public void setCategoriaPontoEletricoId(int categoriaPontoEletricoId)
    {
    	this.categoriaPontoEletricoId = categoriaPontoEletricoId;
    }

    public String getDescricaoCategoriaPontoEletrico()
    {
        return this.descricaoCategoriaPontoEletrico;
    }

    public void setDescricaoCategoriaPontoEletrico(String descricaoCategoriaPontoEletrico)
    {
    	this.descricaoCategoriaPontoEletrico = descricaoCategoriaPontoEletrico;
    }

    public String getFamilia()
    {
        return this.familia;
    }

    public void setFamilia(String familia)
    {
    	this.familia = familia;
    }

    public String getDescricao()
    {
        return this.descricao;
    }

    public void setDescricao(String descricao)
    {
    	this.descricao = descricao;
    }

    public String getPropriedades()
    {
        return this.propriedades;
    }

    public void setPropriedades(String propriedades)
    {
    	this.propriedades = propriedades;
    }

    public String getPotenciaPadrao()
    {
        return this.potenciaPadrao;
    }

    public void setPotenciaPadrao(String potenciaPadrao)
    {
    	this.potenciaPadrao = potenciaPadrao;
    }

    public String getFasePadrao()
    {
        return this.fasePadrao;
    }

    public void setFasePadrao(String fasePadrao)
    {
    	this.fasePadrao = fasePadrao;
    }

    public double getAlturaPadrao()
    {
        return this.alturaPadrao;
    }

    public void setAlturaPadrao(double alturaPadrao)
    {
    	this.alturaPadrao = alturaPadrao;
    }
	
}
