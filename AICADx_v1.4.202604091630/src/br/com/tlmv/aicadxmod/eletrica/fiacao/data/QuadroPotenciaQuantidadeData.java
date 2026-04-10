/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * QuadroPotenciaQuantidadeData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class QuadroPotenciaQuantidadeData
{
//Private
    private Double m_potenciaCarga;
    private Integer m_quantidadeCarga;

//Public

    public QuadroPotenciaQuantidadeData(Double potenciaCarga, Integer quantidadeCarga)
    {
        m_potenciaCarga = potenciaCarga;
        m_quantidadeCarga = quantidadeCarga;
    }

    /* Getters/Setters */

    public Double getPotenciaTotal()
    {
        return m_potenciaCarga * m_quantidadeCarga;
    }

    public Double getPotenciaCarga()
    {
        return m_potenciaCarga;
    }

    public void setPotenciaCarga(Double potenciaCarga)
    {
        m_potenciaCarga = potenciaCarga;
    }

    public Integer getQuantidadeCarga()
    {
        return m_quantidadeCarga;
    }

    public void setQuantidadeCarga(Integer quantidadeCarga)
    {
        m_quantidadeCarga = quantidadeCarga;
    }

}
