/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DimensionamentoCircuito.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/01/2026
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

import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class DimensionamentoCircuito extends ItemDataVO
{
//Private
    private String circuito;
    private double potencia;
    private double tensao;
    private double corrente;
    private double bitolaCondutor;
    private double disjuntorProtecao;
    private String fase;
    private String sistemaFase;
    private int isolacaoCondutorId;
    private int tipoCaboId;
    private String metodoReferencia;
    private String tipoInstalacao;
    private int tipoCondutorId;

//Public

    public DimensionamentoCircuito(
        String circuito, 
        double potencia, 
        double tensao, 
        double corrente,
        double bitolaCondutor, 
        double disjuntorProtecao, 
        String fase, 
        String sistemaFase)
    {
    	super( circuito, Double.toString(potencia) );
    	
        this.circuito = circuito;
        this.potencia = potencia;
        this.tensao = tensao;
        this.corrente = corrente;
        this.bitolaCondutor = bitolaCondutor;
        this.disjuntorProtecao = disjuntorProtecao;
        this.fase = fase;
        this.sistemaFase = sistemaFase;
    }

    /* Getters/Setters */

    public String getCircuito()
    {
        return this.circuito;
    }

    public void setCircuito(String circuito)
    {
        this.circuito = circuito;
    }

    public double getPotencia()
    {
        return this.potencia;
    }

    public void setPotencia(double potencia)
    {
        this.potencia = potencia;
    }

    public double getTensao()
    {
        return this.tensao;
    }

    public void setTensao(double tensao)
    {
        this.tensao = tensao;
    }

    public double getBitolaCondutor()
    {
        return this.bitolaCondutor;
    }

    public void setBitolaCondutor(double bitolaCondutor)
    {
        this.bitolaCondutor = bitolaCondutor;
    }

    public double getDisjuntorProtecao()
    {
        return this.disjuntorProtecao;
    }

    public void setDisjuntorProtecao(double disjuntorProtecao)
    {
        this.disjuntorProtecao = disjuntorProtecao;
    }

    public String getFase()
    {
        return this.fase;
    }

    public void setFase(String fase)
    {
        this.fase = fase;
    }

    public int getIsolacaoCondutorId()
    {
        return this.isolacaoCondutorId; 
    }

    public void setIsolacaoCondutorId(int isolacaoCondutorId)
    {
        this.isolacaoCondutorId = isolacaoCondutorId;
    }

    public int getTipoCaboId()
    {
        return this.tipoCaboId;
    }

    public void setTipoCaboId(int tipoCaboId)
    {
        this.tipoCaboId = tipoCaboId;
    }

    public String getMetodoReferencia()
    {
        return this.metodoReferencia;
    }

    public void setMetodoReferencia(String metodoReferencia)
    {
        this.metodoReferencia = metodoReferencia;
    }

    public String getTipoInstalacao()
    {
        return this.tipoInstalacao;
    }

    public void setTipoInstalacao(String tipoInstalacao)
    {
        this.tipoInstalacao = tipoInstalacao;
    }

    public int getTipoCondutorId()
    {
        return this.tipoCondutorId;
    }

    public void setTipoCondutorId(int tipoCondutorId)
    {
        this.tipoCondutorId = tipoCondutorId;
    }

    public String getSistemaFase()
    {
        return this.sistemaFase;
    }

    public void setSistemaFase(String sistemaFase)
    {
        this.sistemaFase = sistemaFase;
    }

	public double getCorrente() {
		return corrente;
	}

	public void setCorrente(double corrente) {
		this.corrente = corrente;
	}
    
}
