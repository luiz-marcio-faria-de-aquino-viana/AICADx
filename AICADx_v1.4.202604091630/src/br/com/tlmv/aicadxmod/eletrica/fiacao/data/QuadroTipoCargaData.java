/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * QuadroTipoCargaData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

import java.util.ArrayList;
import java.util.List;

public class QuadroTipoCargaData
{
//Private
    private String m_tipoCarga;
    private ArrayList<QuadroPotenciaQuantidadeData> m_lsPotenciaQuantidade = null;

//Public

    public QuadroTipoCargaData(String tipoCarga)
    {
        m_tipoCarga = tipoCarga;
        m_lsPotenciaQuantidade = new ArrayList<QuadroPotenciaQuantidadeData>();
    }

    /* Getters/Setters */

    public String getTipoCarga()
    {
        return m_tipoCarga;
    }

    public QuadroPotenciaQuantidadeData getPotenciaQuantidade(Double potencia)
    {
        for (QuadroPotenciaQuantidadeData o : m_lsPotenciaQuantidade)
        {
            if ( potencia.equals(o.getPotenciaCarga()) )
                return o;
        }
        return null;
    }

    public List<QuadroPotenciaQuantidadeData> getLsPotenciaQuantidade()
    {
        return m_lsPotenciaQuantidade;
    }

    public void add(QuadroPotenciaQuantidadeData o)
    {
        m_lsPotenciaQuantidade.add(o);
    }

}
