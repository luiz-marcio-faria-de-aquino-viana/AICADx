/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * QuadroCircuitoFaseData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

import java.util.ArrayList;
import java.util.List;

public class QuadroCircuitoFaseData
{
//Private
    private String m_circuito;
    private String m_sistemaFase;
	private Integer m_tipoCondutorId; 
	private String m_metodoReferencia;
	private String m_tipoIsolacaoCondutor;
    private String m_tipoInstalacao;    
    private ArrayList<QuadroTipoCargaData> m_lsTipoCarga;

//Public

    public QuadroCircuitoFaseData(
    	String circuito, 
    	String sistemaFase,
    	Integer tipoCondutorId,
    	String metodoReferencia,
    	String tipoIsolacaoCondutor,
        String tipoInstalacao)
    {
        m_circuito = circuito;
        m_sistemaFase = sistemaFase;
    	m_tipoCondutorId = tipoCondutorId; 
    	m_metodoReferencia = metodoReferencia;
    	m_tipoIsolacaoCondutor = tipoIsolacaoCondutor;
        m_tipoInstalacao = tipoInstalacao;    
        m_lsTipoCarga = new ArrayList<QuadroTipoCargaData>();
    }

    /* Methodes */
    
    public double calculaPotenciaCircuito()
    {
    	double tpot = 0.0;
        for (QuadroTipoCargaData o : m_lsTipoCarga)
        {
        	for(QuadroPotenciaQuantidadeData o1 : o.getLsPotenciaQuantidade())
        	{
        		tpot += o1.getPotenciaTotal();
        	}
        }
        return tpot;
    }

    /* Getters/Setters */

    public String getCircuito()
    {
        return m_circuito;
    }

    public String getSistemaFase()
    {
        return m_sistemaFase;
    }

    public QuadroTipoCargaData getTipoCarga(String tipoCarga)
    {
        for (QuadroTipoCargaData o : m_lsTipoCarga)
        {
            if ( tipoCarga.equals(o.getTipoCarga()) )
                return o;
        }
        return null;
    }

    public double getPotenciaCircuito()
    {
        double pot = 0.0;

        for (QuadroTipoCargaData o1 : m_lsTipoCarga)
        {
            for (QuadroPotenciaQuantidadeData o2 : o1.getLsPotenciaQuantidade())
            {
                pot = pot + o2.getPotenciaTotal();
            }
        }
        return pot;
    }

    public List<QuadroTipoCargaData> getLsTipoCarga()
    {
        return m_lsTipoCarga;
    }

    public void add(QuadroTipoCargaData o)
    {
        m_lsTipoCarga.add(o);
    }

	public Integer getTipoCondutorId() {
		return m_tipoCondutorId;
	}

	public String getMetodoReferencia() {
		return m_metodoReferencia;
	}

	public String getTipoIsolacaoCondutor() {
		return m_tipoIsolacaoCondutor;
	}

	public String getTipoInstalacao() {
		return m_tipoInstalacao;
	}

}
