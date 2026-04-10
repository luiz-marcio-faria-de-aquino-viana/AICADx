/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * DimensionamentoQuadroData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

import java.util.ArrayList;

public class DimensionamentoQuadroData
{
//Private
    private String m_nomeQuadro;
    private double m_potenciaQuadro;
    private double m_potenciaQuadroSemReserva;
    private double m_tensaoQuadro;
    private double m_bitolaAlimentadorQuadro;
    private double m_bitolaProtecaoQuadro;
    private double m_disjuntorProtecaoQuadro;
    private String m_faseQuadro;
    private String m_sistemaFase;
    private ArrayList<DimensionamentoCircuitoData> m_lsDimensionamentoCircuito;

//Public

    public DimensionamentoQuadroData(String nomeQuadro)
    {
        m_nomeQuadro = nomeQuadro;
        m_lsDimensionamentoCircuito = new ArrayList<DimensionamentoCircuitoData>();
    }

    public DimensionamentoQuadroData(String nomeQuadro, Double potenciaQuadro, Double potenciaQuadroSemReserva, Double tensaoQuadro, Double bitolaAlimentadorQuadro, Double bitolaProtecaoQuadro, Double disjuntorProtecaoQuadro, String faseQuadro, String sistemaFase)
    {
        m_nomeQuadro = nomeQuadro;
        m_potenciaQuadro = potenciaQuadro;
        m_potenciaQuadroSemReserva = potenciaQuadroSemReserva;
        m_tensaoQuadro = tensaoQuadro;
        m_bitolaAlimentadorQuadro = bitolaAlimentadorQuadro;
        m_bitolaProtecaoQuadro = bitolaProtecaoQuadro;
        m_disjuntorProtecaoQuadro = disjuntorProtecaoQuadro;
        m_faseQuadro = faseQuadro;
        m_sistemaFase = sistemaFase;
        m_lsDimensionamentoCircuito = new ArrayList<DimensionamentoCircuitoData>();
    }

    /* Getters/Setters */

    public String getNomeQuadro()
    {
        return m_nomeQuadro;
    }

    public void setNomeQuadro(String nomeQuadro)
    {
        m_nomeQuadro = nomeQuadro;
    }

    public Double getPotenciaQuadro()
    {
        return m_potenciaQuadro;
    }

    public void setPotenciaQuadro(Double potenciaQuadro)
    {
        m_potenciaQuadro = potenciaQuadro;
    }

    public Double getPotenciaQuadroSemReserva()
    {
        return m_potenciaQuadroSemReserva;
    }

    public void setPotenciaQuadroSemReserva(Double potenciaQuadroSemReserva)
    {
        m_potenciaQuadroSemReserva = potenciaQuadroSemReserva;
    }

    public Double getTensaoQuadro()
    {
        return m_tensaoQuadro;
    }

    public void setTensaoQuadro(Double tensaoQuadro)
    {
        m_tensaoQuadro = tensaoQuadro;
    }

    public Double getBitolaAlimentadorQuadro()
    {
        return m_bitolaAlimentadorQuadro;
    }

    public void setBitolaAlimentadorQuadro(Double bitolaAlimentadorQuadro)
    {
        m_bitolaAlimentadorQuadro = bitolaAlimentadorQuadro;
    }

    public Double getBitolaProtecaoQuadro()
    {
        return m_bitolaProtecaoQuadro;
    }

    public void setBitolaProtecaoQuadro(Double bitolaProtecaoQuadro)
    {
        m_bitolaProtecaoQuadro = bitolaProtecaoQuadro;
    }

    public Double getDisjuntorProtecaoQuadro()
    {
        return m_disjuntorProtecaoQuadro;
    }

    public void setDisjuntorProtecaoQuadro(Double disjuntorProtecaoQuadro)
    {
        m_disjuntorProtecaoQuadro = disjuntorProtecaoQuadro;
    }

    public String getFaseQuadro()
    {
        return m_faseQuadro;
    }

    public void setFaseQuadro(String faseQuadro)
    {
        m_faseQuadro = faseQuadro;
    }

    public String getSistemaFase()
    {
        return m_sistemaFase;
    }

    public void setSistemaFase(String sistemaFase)
    {
        m_sistemaFase = sistemaFase;
    }

    public DimensionamentoCircuitoData getDimensionamentoCircuito(String circuito)
    {
        for(DimensionamentoCircuitoData o : m_lsDimensionamentoCircuito)
        {
            if (o.getCircuito() == circuito)
                return o;
        }
        return null;
    }

    public ArrayList<DimensionamentoCircuitoData> getLsDimensionamentoCircuito()
    {
        return m_lsDimensionamentoCircuito;
    }

    public void add(DimensionamentoCircuitoData o)
    {
        m_lsDimensionamentoCircuito.add(o);
    }

}
