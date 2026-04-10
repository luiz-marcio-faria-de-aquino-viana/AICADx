/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * QuadroPotenciaFaseData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

import java.util.ArrayList;

public class QuadroPotenciaFaseData
{
//Private
    private Double m_potenciaQuadro;
    private Double m_potenciaQuadroSemReserva;
    private String m_sistemaFaseQuadro;
	private Integer m_tipoCondutorId; 
	private String m_metodoReferencia;
	private String m_tipoIsolacaoCondutor;    
	private String m_tipoInstalacao;
    private ArrayList<QuadroCircuitoFaseData> m_lsCircuitoFase;

//Public

    public QuadroPotenciaFaseData(
    	double potenciaQuadro, 
    	double potenciaQuadroSemReserva, 
    	String sistemaFaseQuadro,
    	Integer tipoCondutorId, 
    	String metodoReferencia,
    	String tipoIsolacaoCondutor,
    	String tipoInstalacao)
    {
        m_potenciaQuadro = potenciaQuadro;
        m_potenciaQuadroSemReserva = potenciaQuadroSemReserva;
        m_sistemaFaseQuadro = sistemaFaseQuadro;
    	m_tipoCondutorId = tipoCondutorId; 
    	m_metodoReferencia = metodoReferencia;
    	m_tipoIsolacaoCondutor = tipoIsolacaoCondutor;    
        m_lsCircuitoFase = new ArrayList<QuadroCircuitoFaseData>();
    	m_tipoInstalacao = tipoInstalacao;
    }

    /* Methodes */
    
    public double calculaPotenciaQuadro()
    {
    	double tpot = 0.0;
        for(QuadroCircuitoFaseData o : m_lsCircuitoFase)
        {
        	tpot += o.calculaPotenciaCircuito();
        }
        return tpot;
    }
    
    /* Getters/Setters */

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

    public String getSistemaFaseQuadro()
    {
        return m_sistemaFaseQuadro;
    }

    public void setSistemaFaseQuadro(String sistemaFase)
    {
        m_sistemaFaseQuadro = sistemaFase;
    }

    public QuadroCircuitoFaseData getCircuitoFase(String circuito)
    {
        for(QuadroCircuitoFaseData o : m_lsCircuitoFase)
        {
            if ( circuito.equals(o.getCircuito()) )
                return o;
        }
        return null;
    }

    public ArrayList<QuadroCircuitoFaseData> getLsCircuitoFase()
    {
        return m_lsCircuitoFase;
    }

    public void add(QuadroCircuitoFaseData o)
    {
        m_lsCircuitoFase.add(o);
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
