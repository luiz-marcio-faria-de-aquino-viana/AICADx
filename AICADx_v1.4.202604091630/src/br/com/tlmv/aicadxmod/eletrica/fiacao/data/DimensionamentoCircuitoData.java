/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * DimensionamentoCircuitoData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class DimensionamentoCircuitoData
{
//Private
    private String m_circuito;
    private Double m_potencia;
    private Double m_tensao;
    private Double m_bitolaCondutor;
    private Double m_disjuntorProtecao;
    private String m_fase;
    private String m_sistemaFase;
    private Integer m_isolacaoCondutorId;
    private Integer m_tipoCaboId;
    private String m_metodoReferencia;
    private String m_tipoInstalacao;
    private Integer m_tipoCondutorId;

//Public

    public DimensionamentoCircuitoData(String circuito, Double potencia, Double tensao, Double bitolaCondutor, Double disjuntorProtecao, String fase, String sistemaFase)
    {
        this.m_circuito = circuito;
        this.m_potencia = potencia;
        this.m_tensao = tensao;
        this.m_bitolaCondutor = bitolaCondutor;
        this.m_disjuntorProtecao = disjuntorProtecao;
        this.m_fase = fase;
        this.m_sistemaFase = sistemaFase;
    }

    /* Getters/Setters */

    public String getCircuito()
    {
        return m_circuito;
    }

    public void setCircuito(String circuito)
    {
        m_circuito = circuito;
    }

    public double getPotencia()
    {
        return m_potencia;
    }

    public void setPotencia(Double potencia)
    {
        m_potencia = potencia;
    }

    public double getTensao()
    {
        return m_tensao;
    }

    public void setTensao(Double tensao)
    {
        m_tensao = tensao;
    }

    public double getBitolaCondutor()
    {
        return m_bitolaCondutor;
    }

    public void setBitolaCondutor(Double bitolaCondutor)
    {
        m_bitolaCondutor = bitolaCondutor;
    }

    public Double getDisjuntorProtecao()
    {
        return m_disjuntorProtecao;
    }

    public void setDisjuntorProtecao(Double disjuntorProtecao)
    {
        m_disjuntorProtecao = disjuntorProtecao;
    }

    public String getFase()
    {
        return m_fase;
    }

    public void setFase(String fase)
    {
        m_fase = fase;
    }

    public Integer getIsolacaoCondutorId()
    {
        return m_isolacaoCondutorId; 
    }

    public void setIsolacaoCondutorId(Integer isolacaoCondutorId)
    {
        m_isolacaoCondutorId = isolacaoCondutorId;
    }

    public Integer getTipoCaboId()
    {
        return m_tipoCaboId;
    }

    public void setTipoCaboId(Integer tipoCaboId)
    {
        m_tipoCaboId = tipoCaboId;
    }

    public String getMetodoReferencia()
    {
        return m_metodoReferencia;
    }

    public void getMetodoReferencia(String metodoReferencia)
    {
        m_metodoReferencia = metodoReferencia;
    }

    public String getTipoInstalacao()
    {
        return m_tipoInstalacao;
    }

    public void setTipoInstalacao(String tipoInstalacao)
    {
        m_tipoInstalacao = tipoInstalacao;
    }

    public int getTipoCondutorId()
    {
        return m_tipoCondutorId;
    }

    public void setTipoCondutorId(Integer tipoCondutorId)
    {
        m_tipoCondutorId = tipoCondutorId;
    }

    public String getSistemaFase()
    {
        return m_sistemaFase;
    }

    public void setSistemaFase(String sistemaFase)
    {
        m_sistemaFase = sistemaFase;
    }

}
