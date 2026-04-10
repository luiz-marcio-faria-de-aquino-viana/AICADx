/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * CircuitoFaseData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class CircuitoFaseData
{
//Private
    private String m_circuito;
    private String m_fase;

//Public

    public CircuitoFaseData(String circuito, String fase)
    {
        this.m_circuito = circuito;
        this.m_fase = fase;
    }

    /* Getters/Setters */

    public String getCircuito()
    {
        return m_circuito;
    }

    public String getFase()
    {
        return m_fase;
    }

}
