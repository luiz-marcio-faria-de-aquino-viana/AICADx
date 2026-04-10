/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * CapacidadeCorrenteData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class CapacidadeCorrenteData
{
//Private
    private double m_bitolaCondutor;
    private double m_capacidadeCondutor2;
    private double m_capacidadeCondutor3;

//Public

    public CapacidadeCorrenteData(double bitolaCondutor, double capacidadeCondutor2, double capacidadeCondutor3)
    {
        this.m_bitolaCondutor = bitolaCondutor;
        this.m_capacidadeCondutor2 = capacidadeCondutor2;
        this.m_capacidadeCondutor3 = capacidadeCondutor3;
    }

    /* Getters/Setters */

    public double getBitolaCondutor()
    {
        return m_bitolaCondutor;
    }

    public double getCapacidadeCondutor2()
    {
        return m_capacidadeCondutor2;
    }

    public double getCapacidadeCondutor3()
    {
        return m_capacidadeCondutor3;
    }

}
