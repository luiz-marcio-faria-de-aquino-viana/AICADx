/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * CondutorProtecaoData.java
 * Autor: Luiz Marcio Viana, 09/11/2017
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class CondutorProtecaoData
{
//Private
    private double bitolaCondutorFase;
    private double bitolaCondutorProtecao;

//Public

    public CondutorProtecaoData(double bitolaCondutorFase, double bitolaCondutorProtecao)
    {
        this.bitolaCondutorFase = bitolaCondutorFase;
        this.bitolaCondutorProtecao = bitolaCondutorProtecao;
    }

    /* Getters/Setters */

	public double getBitolaCondutorFase() {
		return bitolaCondutorFase;
	}

	public void setBitolaCondutorFase(double bitolaCondutorFase) {
		this.bitolaCondutorFase = bitolaCondutorFase;
	}

	public double getBitolaCondutorProtecao() {
		return bitolaCondutorProtecao;
	}

	public void setBitolaCondutorProtecao(double bitolaCondutorProtecao) {
		this.bitolaCondutorProtecao = bitolaCondutorProtecao;
	}
    
}
