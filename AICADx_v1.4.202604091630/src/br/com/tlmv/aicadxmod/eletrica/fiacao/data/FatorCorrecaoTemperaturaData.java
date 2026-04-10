/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * FatorCorrecaoTemperaturaData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class FatorCorrecaoTemperaturaData
{
//Private
    private double m_temperatura;
    private String m_tipoInstalacao;
    private double m_fatorCorrecao;

//Public

    public FatorCorrecaoTemperaturaData(double temperatura, String tipoInstalacao, double fatorCorrecao)
    {
        m_temperatura = temperatura;
        m_tipoInstalacao = tipoInstalacao;
        m_fatorCorrecao = fatorCorrecao;
    }

    /* Getters/Setters */

    public double getTemperatura()
    {
        return m_temperatura;
    }

    public double getFatorCorrecao()
    {
        return m_fatorCorrecao;
    }

	public String getTipoInstalacao() {
		return m_tipoInstalacao;
	}

}
