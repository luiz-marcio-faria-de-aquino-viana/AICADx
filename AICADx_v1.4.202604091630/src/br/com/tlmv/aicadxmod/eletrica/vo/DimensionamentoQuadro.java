/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DimensionamentoQuadro.java
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

import java.util.ArrayList;

public class DimensionamentoQuadro 
{
//Private
    private String nomeQuadro;
    private double potenciaQuadro;
    private double potenciaQuadroSemReserva;
    private double tensaoQuadro;
    private double bitolaAlimentadorQuadro;
    private double bitolaProtecaoQuadro;
    private double disjuntorProtecaoQuadro;
    private String faseQuadro;
    private String sistemaFase;

    private ArrayList<DimensionamentoCircuito> lsDimensionamentoCircuito;

//Public

    public DimensionamentoQuadro(String nomeQuadro)
    {
        this.nomeQuadro = nomeQuadro;
        this.lsDimensionamentoCircuito = new ArrayList<DimensionamentoCircuito>();
    }

    public DimensionamentoQuadro(
        String nomeQuadro, 
        double potenciaQuadro, 
        double tensaoQuadro, 
        double bitolaAlimentadorQuadro, 
        double bitolaProtecaoQuadro, 
        double disjuntorProtecaoQuadro, 
        String faseQuadro, 
        String sistemaFase,
        double potenciaQuadroSemReserva)
    {
    	this.nomeQuadro = nomeQuadro;
    	this.potenciaQuadro = potenciaQuadro;
    	this.potenciaQuadroSemReserva = potenciaQuadroSemReserva;
    	this.tensaoQuadro = tensaoQuadro;
    	this.bitolaAlimentadorQuadro = bitolaAlimentadorQuadro;
    	this.bitolaProtecaoQuadro = bitolaProtecaoQuadro;
    	this.disjuntorProtecaoQuadro = disjuntorProtecaoQuadro;
    	this.faseQuadro = faseQuadro;
    	this.sistemaFase = sistemaFase;
        
        this.lsDimensionamentoCircuito = new ArrayList<DimensionamentoCircuito>();
    }

    /* Getters/Setters */

    public String getNomeQuadro()
    {
        return this.nomeQuadro;
    }

    public void setNomeQuadro(String nomeQuadro)
    {
    	this.nomeQuadro = nomeQuadro;
    }

    public double getPotenciaQuadro()
    {
        return this.potenciaQuadro;
    }

    public void setPotenciaQuadro(double potenciaQuadro)
    {
    	this.potenciaQuadro = potenciaQuadro;
    }

    public double getPotenciaQuadroSemReserva()
    {
        return this.potenciaQuadroSemReserva;
    }

    public void setPotenciaQuadroSemReserva(double potenciaQuadroSemReserva)
    {
    	this.potenciaQuadroSemReserva = potenciaQuadroSemReserva;
    }

    public double getTensaoQuadro()
    {
        return this.tensaoQuadro;
    }

    public void setTensaoQuadro(double tensaoQuadro)
    {
    	this.tensaoQuadro = tensaoQuadro;
    }

    public double getBitolaAlimentadorQuadro()
    {
        return this.bitolaAlimentadorQuadro;
    }

    public void setBitolaAlimentadorQuadro(double bitolaAlimentadorQuadro)
    {
    	this.bitolaAlimentadorQuadro = bitolaAlimentadorQuadro;
    }

    public double getBitolaProtecaoQuadro()
    {
        return this.bitolaProtecaoQuadro;
    }

    public void setBitolaProtecaoQuadro(double bitolaProtecaoQuadro)
    {
    	this.bitolaProtecaoQuadro = bitolaProtecaoQuadro;
    }

    public double getDisjuntorProtecaoQuadro()
    {
        return this.disjuntorProtecaoQuadro;
    }

    public void setDisjuntorProtecaoQuadro(double disjuntorProtecaoQuadro)
    {
    	this.disjuntorProtecaoQuadro = disjuntorProtecaoQuadro;
    }

    public String getFaseQuadro()
    {
        return this.faseQuadro;
    }

    public void setFaseQuadro(String faseQuadro)
    {
    	this.faseQuadro = faseQuadro;
    }

    public String getSistemaFase()
    {
        return this.sistemaFase;
    }

    public void setSistemaFase(String sistemaFase)
    {
    	this.sistemaFase = sistemaFase;
    }

    public DimensionamentoCircuito getDimensionamentoCircuito(String circuito)
    {
        for(DimensionamentoCircuito o : this.lsDimensionamentoCircuito) {
            if (o.getCircuito() == circuito)
                return o;
        }
        return null;
    }

    public ArrayList<DimensionamentoCircuito> getLsDimensionamentoCircuito()
    {
        return this.lsDimensionamentoCircuito;
    }

    public void add(DimensionamentoCircuito o)
    {
        this.lsDimensionamentoCircuito.add(o);
    }
    
}
