/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * VazaoColetorPredialVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxmod.drenagem.vo;

public class VazaoColetorPredialVO 
{
//Private
    private double diametro = 100.0;
    private double declividade = 0.5;
    private double vazaoMax = -1.0;
    private int qtdTubulacao = 1;

    /* Methodes */
    
    private double calculaVazaoMax()
    {
        double k = 60000.0;

        double n = 0.011;

        double R = ((this.diametro / 1000.0) / 2.0);       //raio em metros

        double S = Math.pow(1.0 / 2.0, 2.0) * Math.PI * Math.pow(R, 2.0);

        double P = (1.0 / 2.0) * 2.0 * Math.PI * R;

        double Rh = S / P;                              //raio hidraulico

        double Q = (1.0 / 2.0) * k * (S / n) * Math.pow(Rh, 2.0 / 3.0) * Math.pow(this.declividade, 1.0 / 2.0);

        return Q;
    }

//Public

    public VazaoColetorPredialVO(double diametro, double declividade, double vazaoMax)
    {
        this.diametro = diametro;
        this.declividade = declividade;
        this.vazaoMax = vazaoMax;
        this.qtdTubulacao = 1;
    }

    public VazaoColetorPredialVO(double diametro, double declividade)
    {
    	this.diametro = diametro;
    	this.declividade = declividade;
    	this.vazaoMax = calculaVazaoMax();
    	this.qtdTubulacao = 1;
    }

    /* Getter/Setters */

	public int getQtdTubulacao() {
		return qtdTubulacao;
	}

	public double getDiametro() {
		return diametro;
	}

	public double getDeclividade() {
		return declividade;
	}

	public double getVazaoMax() {
		return vazaoMax;
	}
	
}
