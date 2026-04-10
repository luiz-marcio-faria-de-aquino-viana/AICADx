/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * DistribuicaoFaseData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class DistribuicaoFaseData implements Comparable<DistribuicaoFaseData>
{
//Private
    private Integer m_numeroFases;
    private Double m_potenciaCircuito;
    private String m_identificacaoCircuito;

//Public

    public DistribuicaoFaseData(Integer numeroFases, Double potenciaCircuito, String identificacaoCircuito)
    {
        this.m_numeroFases = numeroFases;
        this.m_potenciaCircuito = potenciaCircuito;
        this.m_identificacaoCircuito = identificacaoCircuito;
    }

    /* Comparable */

    @Override
	public int compareTo(DistribuicaoFaseData o) 
    {
        if( (this.getNumeroFases() < o.getNumeroFases()) || ( (this.getNumeroFases() == o.getNumeroFases()) && (this.getPotenciaCircuito() < o.getPotenciaCircuito()) ) )
            return -1;
        else if( (this.getNumeroFases() < o.getNumeroFases()) && (this.getPotenciaCircuito() == o.getPotenciaCircuito()) )
            return 0;
        else return 1;
	}

    /* Getters/Setters */

    public Integer getNumeroFases()
    {
        return m_numeroFases;
    }

    public Double getPotenciaCircuito()
    {
        return m_potenciaCircuito;
    }

    public String getIdentificacaoCircuito()
    {
        return m_identificacaoCircuito;
    }

}
